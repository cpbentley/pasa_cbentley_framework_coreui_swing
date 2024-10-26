package pasa.cbentley.framework.core.ui.swing.engine;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.core.src4.stator.StatorReader;
import pasa.cbentley.core.src4.stator.StatorWriter;
import pasa.cbentley.framework.core.draw.swing.engine.GraphicsSwing;
import pasa.cbentley.framework.core.ui.j2se.engine.CanvasHostJ2se;
import pasa.cbentley.framework.core.ui.src4.ctx.IBOCtxSettingsCoreUi;
import pasa.cbentley.framework.core.ui.src4.input.InputState;
import pasa.cbentley.framework.core.ui.src4.interfaces.ICanvasHost;
import pasa.cbentley.framework.core.ui.src4.tech.IBOCanvasHost;
import pasa.cbentley.framework.core.ui.src4.tech.ITechCodes;
import pasa.cbentley.framework.core.ui.src4.utils.KeyRepeatBlock;
import pasa.cbentley.framework.core.ui.src4.wrapper.WrapperAbstract;
import pasa.cbentley.framework.core.ui.swing.ctx.CoreUiSwingCtx;
import pasa.cbentley.framework.core.ui.swing.wrapper.WrapperAbstractSwing;
import pasa.cbentley.framework.coredraw.src4.interfaces.IGraphics;
import pasa.cbentley.framework.coredraw.src4.interfaces.ITechGraphics;
import pasa.cbentley.swing.data.FileDrop;

/**
 * The Swing component (extends {@link JComponent}) to display {@link IAppli} in a Swing context.
 * <br>
 * <br>
 * It encapsulates a {@link Canvas}.
 * <br>
 * It forwards all events (paint, keys and pointers) to {@link Canvas}.
 * <li> {@link CanvasHostSwingAbstract#mouseDown(java.awt.Event, int, int)}
 * <li> {@link CanvasHostSwingAbstract#mouseDragged(MouseEvent)}
 * <li> {@link CanvasHostSwingAbstract#mouseReleased(MouseEvent)}
 * <li> {@link CanvasHostSwingAbstract#keyDown(java.awt.Event, int)}
 * <li> {@link CanvasHostSwingAbstract#keyReleased(KeyEvent)}
 * <br>
 * <br>
 * Manages the MasterCanvas as the {@link DisplayableAWT}. <br>
 * <br>
 * Uses {@link DeviceDriver} to convert key to the Bentley framework codes of {@link ITechCodes}.
 * <br>
 * For example, if letter switch, forward letter key event to the MasterCanvas framework.
 * <br>
 * Creates and uses an {@link Image} for painting.
 * <br>
 * <br>
 * In the reverse situation, a Swing application may create a SwingCanvasBridge and set it a MasterCanvas or just a Canvas to it.
 * <br>
 * <br>
 * @author Charles-Philip Bentley
 *
 */
public abstract class CanvasHostSwingAbstract extends CanvasHostJ2se implements ICanvasHost, IBOCanvasHost, KeyListener, ComponentListener, MouseListener, MouseMotionListener, FocusListener, MouseWheelListener {

   /**
    * 
    */
   private static final long      serialVersionUID = 1L;

   private GraphicsSwing          graphicsSwing;

   private KeyRepeatBlock         keyreapeat;

   private MouseEvent             lastMove;

   /**
    * The {@link JComponent} is drawn inside a Wrapper
    */
   protected Component            realComponent;

   protected final CoreUiSwingCtx scc;

   protected WrapperAbstractSwing wrapperSwing;

   private boolean isStopped;

   /**
    * 
    * @param realComponent when null, the Canvas will be set externallyO
    * @param w
    * @param h
    */
   public CanvasHostSwingAbstract(CoreUiSwingCtx cuc, ByteObject boCanvasHost) {
      super(cuc, boCanvasHost);
      this.scc = cuc;

      keyreapeat = new KeyRepeatBlock(cuc);
      graphicsSwing = new GraphicsSwing(cuc.getCoreDrawSwingCtx());

      //#debug
      graphicsSwing.toStringSetNameDebug("Main");

      boolean isOpenGL = boCanvasHost != null ? boCanvasHost.hasFlag(CANVAS_HOST_OFFSET_01_FLAG, CANVAS_HOST_FLAG_3_OPEN_GL) : false;

      //could also be an AWT Canvas
      if (isOpenGL) {
         //         CanvasComp cc = new CanvasComp(this);
         //         c = new GLG2DCanvas(cc);
      } else {
         if (boCanvasHost.hasFlag(CANVAS_HOST_OFFSET_01_FLAG, CANVAS_HOST_FLAG_6_AWT_CANVAS)) {
            realComponent = new RealCanvasAWTCanvas(cuc, this);
         } else {
            realComponent = new RealCanvasJComponent(cuc, this);
         }
      }
      //c.setSize(iw, ih);
      realComponent.setFocusTraversalKeysEnabled(false);
      if (realComponent instanceof JComponent) {
         ((JComponent) realComponent).setDoubleBuffered(true);
      }

      //c.setPreferredSize(new Dimension(iw, ih));

      // Setup for keyboard events
      realComponent.addKeyListener(this);
      realComponent.addComponentListener(this);
      realComponent.addMouseListener(this);
      realComponent.addMouseMotionListener(this);
      realComponent.addFocusListener(this);
      realComponent.addMouseWheelListener(this);

      //various ways to deal with special Swing ALT behavior
      KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
         @Override
         public boolean dispatchKeyEvent(KeyEvent e) {
            //System.out.println("Got Event " + e.getKeyCode());
            if (e.getKeyCode() == KeyEvent.VK_ALT) {
               if (KeyEvent.KEY_PRESSED == e.getID()) {
                  keyPressed(e);
               } else if (KeyEvent.KEY_RELEASED == e.getID()) {
                  keyReleased(e);
               }
               return true;
            }
            return false;
         }
      });

      //#debug
      toDLog().pCreate("", this, CanvasHostSwingAbstract.class, "Created@165", LVL_04_FINER, true);

   }

   @Override
   public void componentHidden(ComponentEvent e) {
      //#debug
      toDLog().pBridge("", this, CanvasHostSwingAbstract.class, "componentHidden@169", LVL_05_FINE, true);
   }

   /**
    * We don't do anything here because the component is actually the Launcher that decided
    * if we are in a JFrame or a JComponent along side other Swing componenents.
    */
   public void componentMoved(ComponentEvent e) {
      //#debug
      toDLog().pBridge("", e, CanvasHostSwingAbstract.class, "componentMoved@178", LVL_05_FINE, true);

      int x = e.getComponent().getX();
      int y = e.getComponent().getY();
      //compute screen id
      int screenID = 0;
      canvasPositionChangedBridge(screenID, x, y);
   }

   /**
    * Listens when {@link RealCanvasJComponent} is resized.
    */
   public void componentResized(ComponentEvent e) {
      //#debug
      toDLog().pBridge("", e, CanvasHostSwingAbstract.class, "componentResized@194", LVL_05_FINE, true);

      int w = getICWidth();
      int h = getICHeight();
      if (w <= 0) {
         //toLog().printBridge("#SwingCanvasBridge componentResized w<=0 : " + w);
         w = 1;
      }
      if (h <= 0) {
         //toLog().printBridge("#SwingCanvasBridge componentResized h<=0 : " + h);
         h = 1;
      }
      canvasSizeChangedBridge(w, h);
      //c.repaint();
   }

   public void componentShown(ComponentEvent e) {
      //#debug
      toDLog().pBridge("", e, CanvasHostSwingAbstract.class, "componentShown@206", LVL_05_FINE, true);

   }

   private void debugKeyEvent(KeyEvent ev, String method) {
      int location = ev.getKeyLocation();
      int mods = ev.getModifiers();
      String loc = "";
      if (location == KeyEvent.KEY_LOCATION_NUMPAD) {
         loc += "NumPad";
      }
      String modss = "";
      if (ev.isShiftDown()) {
         modss += "Shift";
      }
      if (ev.isControlDown()) {
         modss += "Ctrl";
      }
      if (ev.isAltDown()) {
         modss += "Alt";
      }
      if (ev.isAltGraphDown()) {
         modss += "AltGr";
      }
      int j2seKeyCode = ev.getKeyCode();
      char c = ev.getKeyChar();
      String text = KeyEvent.getKeyText(j2seKeyCode);
      int fcode = scc.getKeyMap().getKeyMappedToFramework(j2seKeyCode);

      String message = text + " KeyChar=" + c + " KeyCode=" + j2seKeyCode + "-> [" + fcode + "] " + modss + " " + loc;
      //#debug
      toDLog().pBridge(message, this, CanvasHostSwingAbstract.class, "debugKeyEvent@237", LVL_04_FINER, true);
   }

   /**
    * Enables drops from {@link IBOCtxSettingsCoreUi}
    */
   public void enableFileDrop() {
      FileDropListener listnere = new FileDropListener(scc, this);
      //TODO reimplement
      FileDrop fp = new FileDrop(realComponent, listnere);
   }

   public void fileDropDisable() {
      //#debug
      toDLog().pFlow("msg", this, CanvasHostSwingAbstract.class, "fileDropDisable@258", LVL_05_FINE, true);
   }

   @Override
   public void focusGained(FocusEvent e) {
      //#debug
      toDLog().pBridge("", this, CanvasHostSwingAbstract.class, "focusGained@264", LVL_05_FINE, true);

      focusGainedBridge();
   }

   @Override
   public void focusLost(FocusEvent e) {
      //#debug
      toDLog().pBridge("", this, CanvasHostSwingAbstract.class, "focusLost@272", LVL_05_FINE, true);

      focusLostBridge();
   }

   /**
    * Maps the {@link MouseEvent} to the Bentley's framework {@link ITechCodes} IDs.
    * <br>
    * <br>
    * 
    * @param e
    * @return
    */
   protected int getButtonID(MouseEvent e) {
      int but = e.getButton();
      if (but == MouseEvent.BUTTON1) {
         return ITechCodes.PBUTTON_0_DEFAULT; // written 11/8/2017 
      }
      //When you press the right mouse button and the left mouse button together.. the left mouse button is detected as the right button
      int butID = ITechCodes.PBUTTON_0_DEFAULT;
      if (SwingUtilities.isRightMouseButton(e)) {
         butID = ITechCodes.PBUTTON_1_RIGHT;
      } else if (SwingUtilities.isMiddleMouseButton(e)) {
         butID = ITechCodes.PBUTTON_2_MIDDLE;
      }
      return butID;
   }

   private String getCodeDebugString(int j2seKeyCode, int j2meCode) {
      return "J2SECode[" + j2seKeyCode + " " + KeyEvent.getKeyText(j2seKeyCode) + "] translates to J2ME[" + j2meCode + "]";
   }

   public Component getComponentOfCanvas() {
      return realComponent;
   }

   public boolean isShown() {
      return realComponent.isVisible();
   }

   /**
    * Method from the KeyListener interface.
    * <br>
    * <b>Notes</b>:
    * <li>Also, as long as the key is pressed, it generates an event 
    * <li>Swing generates a new event when A press B press A released, Swing generated a Pressed event.
    * 
    */
   public void keyPressed(KeyEvent ev) {

      //THIS PREVENTS alt LOSE FOCUS
      //      if(ev.getKeyCode() == KeyEvent.VK_ALT) {
      //         ev.consume();
      //      }
      int j2seKeyCode = ev.getKeyCode();
      int finalCode = scc.getKeyMap().getKeyMappedToFramework(j2seKeyCode);

      //pinch key emulates the pinching while moving the mouse

     
      if (keyreapeat.isKeyRepeat(finalCode)) {
         //#debug
         toDLog().pBridge("Repeat Event of a key " + j2seKeyCode + ". It is Canceled!", keyreapeat, CanvasHostSwingAbstract.class, "keyPressed@"+toStringGetLine(333), LVL_05_FINE, true);
         return;
      }
      
      //#debug
      toDLog().pBridge("J2se code " + j2seKeyCode + " is mapped to " + finalCode + " Bentley code", ev, CanvasHostSwingAbstract.class, "keyPressed@"+toStringGetLine(333), LVL_05_FINE, true);

      simulationKeys(finalCode);
      
      //#debug
      debugKeyEvent(ev, "keyPressed");

      keyPressedBridge(finalCode);

   }

   /**
    * Method from the KeyListener interface.
    */
   public void keyReleased(KeyEvent ev) {

      int j2seKeyCode = ev.getKeyCode();
      int finalCode = scc.getKeyMap().getKeyMappedToFramework(j2seKeyCode);

      //#debug
      toDLog().pBridge(j2seKeyCode + " is mapped to " + finalCode, ev, CanvasHostSwingAbstract.class, "keyReleased@351", LVL_05_FINE, true);

      keyreapeat.keyReleaseRepeat(finalCode);
      debugKeyEvent(ev, "keyReleased");
      keyReleasedBridge(finalCode);
   }

   /**
    * Typed events are handled by {@link InputState}
    */
   public void keyTyped(KeyEvent arg0) {

   }
   /**
    * Mouse Clicked events are handled by {@link InputState}
    */
   public void mouseClicked(MouseEvent arg0) {

   }

   public void mouseDragged(MouseEvent e) {
      //#debug
      //toLog().pBridge1("[" + e.getX() + " " + e.getY() + "]", this, CanvasAbstractSwing.class, "mouseDragged");
      mouseDraggedBridge(e.getX(), e.getY(), pointerID);
   }

   /**
    * When the mouse enters, it sends a release event if the mouse is not being dragged.
    * <br>
    * This class must track the state of mouse buttons and send release events when those are released
    * outside
    */
   public void mouseEntered(MouseEvent e) {
      //#debug
      toDLog().pBridge1("[" + e.getX() + " " + e.getY() + "]", this, CanvasHostSwingAbstract.class, "mouseEntered@386");

      realComponent.requestFocusInWindow();
      //in swing, enter side will be 0
      mouseEnteredBridge(e.getX(), e.getY());

   }

   /**
    * When the mouse exits the {@link CanvasHostSwingAbstract}, it might be in the pressed state.
    * <br>
    * There is no certainty that the released event outside the {@link CanvasHostSwingAbstract} will be forwarded here.
    * <br>
    * Therefore we need a flag when the {@link CanvasHostSwingAbstract#mouseEntered(MouseEvent)}
    * <br>
    * 
    */
   public void mouseExited(MouseEvent e) {
      //#debug
      toDLog().pBridge1("[" + e.getX() + " " + e.getY() + "]", this, CanvasHostSwingAbstract.class, "mouseExited@405");
      //in swing exit will be -1
      mouseExitedBridge(e.getX(), e.getY());
   }

   public void mouseMoved(MouseEvent e) {
      lastMove = e;
      //#debug
      //toLog().pBridge1("[" + e.getX() + " " + e.getY() + "]", this, SwingCanvasBridge.class, "mouseMoved");
      mouseMovedBridge(e.getX(), e.getY(), pointerID);
   }

   public void mousePinched() {
      if (lastMove != null) {
         toDLog().pBridge1("#SwingCanvasBridge#mousePinched [" + lastMove.getX() + " " + lastMove.getY() + "]", this, CanvasHostSwingAbstract.class, "mousePinched");
         //mousePinchedBridge(lastMove.getX(), lastMove.getY(), 0);
      }
   }

   /**
    * Invokes with the button ID.
    */
   public void mousePressed(MouseEvent e) {
      int bid = getButtonID(e);

      //#debug
      toDLog().pBridge1("[" + e.getX() + " " + e.getY() + "]" + " butID=" + bid, this, CanvasHostSwingAbstract.class, "mousePressed");

      mousePressedBridge(e.getX(), e.getY(), pointerID, bid);
   }

   public void mouseReleased(MouseEvent e) {
      int bid = getButtonID(e);

      //#debug
      toDLog().pBridge1("[" + e.getX() + " " + e.getY() + "]" + " butID=" + bid, this, CanvasHostSwingAbstract.class, "mouseReleased");

      mouseReleasedBridge(e.getX(), e.getY(), pointerID, bid);
   }

   @Override
   public void mouseWheelMoved(MouseWheelEvent e) {
      //#debug
      toDLog().pBridge("ScrollAmount=" + e.getScrollAmount() + ":" + e.getScrollType() + " WheelRoation=" + e.getWheelRotation(), this, CanvasHostSwingAbstract.class, "mouseWheelMoved@448", LVL_04_FINER, true);

      mouseWheeledBridge(e.getScrollAmount(), e.getWheelRotation());
   }

   public void setStopped() {
      isStopped = true;
      canvasAppli = null;
   }
   /**
    * Called by the Real compo.
    */
   public void paint(Graphics2D g) {
      int w = getICWidth();
      int h = getICHeight();
      //#debug
      toDLog().pBridge("area [" + w + " " + h + "]", g, CanvasHostSwingAbstract.class, "paint@460", LVL_02_FINE_EXTRA, true);

      if (canvasAppli == null) {
         //System.out.println("Null Current Displayable. Nothing to draw");
         String message = "Nothing to Draw";
         if(isStopped) {
            message = "Application is Stopped";
         }
         g.setColor(Color.BLACK);
         g.fillRect(0, 0, w, h);
         g.setColor(Color.LIGHT_GRAY);
         g.drawString(message, 15, 35);

      } else {
         graphicsSwing.setGraphics2D(g);
         //IGraphics gx = new GraphicsSwing(scc.getCoreDrawSwingCtx(), g);
         paint(graphicsSwing);
      }
   }

   /**
    * 
    * @param g
    */
   public void paint(IGraphics g) {
      // Setup the graphics object as per the spec.
      g.setTranslate(-g.getTranslateX(), -g.getTranslateY());
      g.setClip(0, 0, getICWidth(), getICHeight());
      g.setColor(0xff000000); // black
      g.setFont(scc.getFontFactory().getDefaultFont());
      g.setStrokeStyle(ITechGraphics.SOLID);
      paintBridge(g);
   }

   public void setWrapper(WrapperAbstract wrapper) {
      if (wrapper instanceof WrapperAbstractSwing) {
         this.wrapperSwing = (WrapperAbstractSwing) wrapper;
         super.setWrapper(wrapper);
      } else {
         throw new IllegalArgumentException();
      }
   }

   public void stateReadFrom(StatorReader state) {
      super.stateReadFrom(state);

      //#debug
      toDLog().pStator("Reading", this, CanvasHostSwingAbstract.class, "stateReadFrom@503", LVL_04_FINER, true);
   }

   public void stateWriteTo(StatorWriter state) {
      super.stateWriteTo(state);

      //#debug
      toDLog().pStator("Writing", this, CanvasHostSwingAbstract.class, "stateWriteTo@505", LVL_04_FINER, true);
   }

   //   public void icServiceRepaint() {
   //
   //      synchronized (lockPaint) {
   //         if (isShown()) {
   //            //inside we are sure, method is not painting
   //         }
   //
   //      }
   //      //check if there are any pending repaints. otherwise returns early
   //      boolean isPendingRepaints = false;
   //      int c = 0;
   //      AWTEvent e = null;
   //      while ((e = Toolkit.getDefaultToolkit().getSystemEventQueue().peekEvent(c)) != null) {
   //         if (e instanceof PaintEvent) {
   //            isPendingRepaints = true;
   //            break;
   //         }
   //         c++;
   //      }
   //      if (isPendingRepaints) {
   //
   //         // Do the service repaints thing.  This is somewhat awkward, as there isn't an equivalent
   //         // to serviceRepaints in AWT.  You could do something like loop until the AWT event
   //         // queue is empty, using java.awt.Toolkit.getDefaultToolkit().getSystemEventQueue()
   //         // We decided to use a lock and once 
   //         try {
   //            synchronized (lockService) {
   //               //#debug
   //               toLog().printBridge("swing.Canvas#serviceRepaints Locking Thread " + Thread.currentThread().toString());
   //               isServiceRepaint = true;
   //               lockService.wait();
   //               isServiceRepaint = false;
   //            }
   //         } catch (Exception ex) {
   //            ex.printStackTrace();
   //         }
   //      }
   //   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, CanvasHostSwingAbstract.class, 549);
      toStringPrivate(dc);
      super.toString(dc.sup());

      if (realComponent instanceof IStringable) {
         dc.nlLvl((IStringable) realComponent, "realComponent");
      } else {
         dc.nl();
         dc.append("realComponent");
         scc.getSwingCtx().toSD().d((Component) realComponent, dc);
      }

      dc.nl();
      scc.getSwingCtx().toSD().d((MouseEvent) lastMove, dc);

      dc.nlLvl(wrapperSwing, "WrapperSwing");
      dc.nlLvl(keyreapeat, "KeyRepeat");

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, CanvasHostSwingAbstract.class, 570);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {

   }

   //#enddebug

}
