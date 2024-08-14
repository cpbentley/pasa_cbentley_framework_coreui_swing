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
import pasa.cbentley.framework.core.ui.src4.engine.WrapperAbstract;
import pasa.cbentley.framework.core.ui.src4.interfaces.ICanvasHost;
import pasa.cbentley.framework.core.ui.src4.tech.IBOCanvasHost;
import pasa.cbentley.framework.core.ui.src4.tech.ITechCodes;
import pasa.cbentley.framework.core.ui.src4.utils.KeyRepeatBlock;
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
 * <li> {@link CanvasHostSwing#mouseDown(java.awt.Event, int, int)}
 * <li> {@link CanvasHostSwing#mouseDragged(MouseEvent)}
 * <li> {@link CanvasHostSwing#mouseReleased(MouseEvent)}
 * <li> {@link CanvasHostSwing#keyDown(java.awt.Event, int)}
 * <li> {@link CanvasHostSwing#keyReleased(KeyEvent)}
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
public abstract class CanvasHostSwing extends CanvasHostJ2se implements ICanvasHost, IBOCanvasHost, KeyListener, ComponentListener, MouseListener, MouseMotionListener, FocusListener, MouseWheelListener {

   /**
    * 
    */
   private static final long      serialVersionUID = 1L;

   private GraphicsSwing          graphicsSwing;

   private KeyRepeatBlock        keyreapeat;

   private MouseEvent             lastMove;

   /**
    * The {@link JComponent} is drawn inside a Wrapper
    */
   protected Component            realComponent;

   protected final CoreUiSwingCtx scc;

   protected WrapperAbstractSwing wrapperSwing;

   /**
    * 
    * @param realComponent when null, the Canvas will be set externallyO
    * @param w
    * @param h
    */
   public CanvasHostSwing(CoreUiSwingCtx scc, ByteObject tech) {
      super(scc, tech);
      this.scc = scc;

      keyreapeat = new KeyRepeatBlock(scc);
      graphicsSwing = new GraphicsSwing(scc.getCoreDrawSwingCtx());

      //#debug
      graphicsSwing.toStringSetNameDebug("Main");
      // LaunchValues lv = dd.getLaunchValues();
      // int iw = lv.w;
      // int ih = lv.h;

      //#debug
      //toLog().printBridge("#SwingCanvasBridge#constructor CanvasComp Size = " + iw + "," + ih);
      // Allocate the midlet rendering target.  getGraphics() is used to pass a Graphics object
      //
      boolean isOpenGL = tech != null ? tech.hasFlag(TCANVAS_OFFSET_01_FLAG, TCANVAS_FLAG_3_OPEN_GL) : false;

      //could also be an AWT Canvas
      if (isOpenGL) {
         //         CanvasComp cc = new CanvasComp(this);
         //         c = new GLG2DCanvas(cc);
      } else {
         if (tech.hasFlag(TCANVAS_OFFSET_01_FLAG, TCANVAS_FLAG_6_AWT_CANVAS)) {
            realComponent = new RealCanvasAWTCanvas(scc, this);
         } else {
            realComponent = new RealCanvasJComponent(scc, this);
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

   }

   @Override
   public void componentHidden(ComponentEvent e) {
      //#debug
      toDLog().pBridge1("", this, CanvasHostSwing.class, "componentHidden");
   }

   /**
    * We don't do anything here because the component is actually the Launcher that decided
    * if we are in a JFrame or a JComponent along side other Swing componenents.
    */
   public void componentMoved(ComponentEvent e) {
      //#debug
      toDLog().pBridge1("[" + e.getComponent().getWidth() + ":" + e.getComponent().getHeight() + "]", this, CanvasHostSwing.class, "componentMoved");

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
      //we already are in the AWT Event Queue.

      //#debug
      toDLog().pBridge1("[" + e.getComponent().getWidth() + ":" + e.getComponent().getHeight() + "]", this, CanvasHostSwing.class, "componentResized");

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

   @Override
   public void componentShown(ComponentEvent e) {

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
      toDLog().pBridge1(message, this, CanvasHostSwing.class, method);
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
      toDLog().pFlow("msg", this, CanvasHostSwing.class, "fileDropDisable", LVL_05_FINE, true);
   }

   @Override
   public void focusGained(FocusEvent e) {
      //#debug
      toDLog().pBridge1("", this, CanvasHostSwing.class, "focusGained");

      focusGainedBridge();
   }

   @Override
   public void focusLost(FocusEvent e) {
      //#debug
      toDLog().pBridge1("", this, CanvasHostSwing.class, "focusLost");

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

   public Component getRealCanvas() {
      return realComponent;
   }

   @Override
   public boolean isShown() {
      return realComponent.isVisible();
   }

   /**
    * Method from the KeyListener interface.
    * <br>
    * <b>Notes</b>:
    * <li>Also, as long as the key is pressed, it generates an event 
    * <li>Swing generates a new event when A press B press A released, Swing generated a Pressed event.
    * <br>
    * <br>
    * 
    */
   public void keyPressed(KeyEvent ev) {

      //THIS PREVENTS alt LOSE FOCUS
      //      if(ev.getKeyCode() == KeyEvent.VK_ALT) {
      //         ev.consume();
      //      }
      int j2seKeyCode = ev.getKeyCode();

      //pinch key emulates the pinching while moving the mouse
      int finalCode = scc.getKeyMap().getKeyMappedToFramework(j2seKeyCode);
      if (keyreapeat.isKeyRepeat(finalCode)) {
         return;
      }
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
      keyreapeat.keyReleaseRepeat(finalCode);
      debugKeyEvent(ev, "keyReleased");
      keyReleasedBridge(finalCode);
   }

   @Override
   public void keyTyped(KeyEvent arg0) {
      // TODO Auto-generated method stub

   }

   @Override
   public void mouseClicked(MouseEvent arg0) {
      // TODO Auto-generated method stub

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
      toDLog().pBridge1("[" + e.getX() + " " + e.getY() + "]", this, CanvasHostSwing.class, "mouseEntered");

      realComponent.requestFocusInWindow();
      //in swing, enter side will be 0
      mouseEnteredBridge(e.getX(), e.getY());

   }

   /**
    * When the mouse exits the {@link CanvasHostSwing}, it might be in the pressed state.
    * <br>
    * There is no certainty that the released event outside the {@link CanvasHostSwing} will be forwarded here.
    * <br>
    * Therefore we need a flag when the {@link CanvasHostSwing#mouseEntered(MouseEvent)}
    * <br>
    * 
    */
   public void mouseExited(MouseEvent e) {
      //#debug
      toDLog().pBridge1("[" + e.getX() + " " + e.getY() + "]", this, CanvasHostSwing.class, "mouseExited");
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
         toDLog().pBridge1("#SwingCanvasBridge#mousePinched [" + lastMove.getX() + " " + lastMove.getY() + "]", this, CanvasHostSwing.class, "mousePinched");
         //mousePinchedBridge(lastMove.getX(), lastMove.getY(), 0);
      }
   }

   /**
    * Invokes with the button ID.
    */
   public void mousePressed(MouseEvent e) {
      int bid = getButtonID(e);

      //#debug
      toDLog().pBridge1("[" + e.getX() + " " + e.getY() + "]" + " butID=" + bid, this, CanvasHostSwing.class, "mousePressed");

      mousePressedBridge(e.getX(), e.getY(), pointerID, bid);
   }

   public void mouseReleased(MouseEvent e) {
      int bid = getButtonID(e);

      //#debug
      toDLog().pBridge1("[" + e.getX() + " " + e.getY() + "]" + " butID=" + bid, this, CanvasHostSwing.class, "mouseReleased");

      mouseReleasedBridge(e.getX(), e.getY(), pointerID, bid);
   }

   @Override
   public void mouseWheelMoved(MouseWheelEvent e) {

      //#debug
      toDLog().pBridge1("ScrollAmount=" + e.getScrollAmount() + ":" + e.getScrollType() + " WheelRoation=" + e.getWheelRotation(), this, CanvasHostSwing.class, "mouseWheelMoved");

      mouseWheeledBridge(e.getScrollAmount(), e.getWheelRotation());
   }

   /**
    * Called by the Real compo.
    */
   public void paint(Graphics2D g) {
      int w = getICWidth();
      int h = getICHeight();
      //#debug
      toDLog().pBridge("area [" + w + " " + h + "]", this, CanvasHostSwing.class, "paint", LVL_03_FINEST, true);

      //toDLog().pBridge(scc.getSwingCtx().toSD().d(g), null, CanvasHostSwing.class, "paint", LVL_03_FINEST, false);

      if (canvasAppli == null) {
         //System.out.println("Null Current Displayable. Nothing to draw");
         g.setColor(Color.BLACK);
         g.fillRect(0, 0, w, h);
         g.setColor(Color.LIGHT_GRAY);
         g.drawString("Nothing to Draw", 15, 35);

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
      g.translate(-g.getTranslateX(), -g.getTranslateY());
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

   public void stateWriteTo(StatorWriter state) {
      super.stateWriteTo(state);
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, CanvasHostSwing.class);
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
      dc.root1Line(this, CanvasHostSwing.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {

   }

   //#enddebug

}
