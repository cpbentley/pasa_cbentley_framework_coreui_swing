package pasa.cbentley.framework.core.ui.swing.wrapper;

import java.awt.AWTException;
import java.awt.BufferCapabilities;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.byteobjects.src4.stator.StatorReaderBO;
import pasa.cbentley.byteobjects.src4.stator.StatorWriterBO;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.stator.StatorReader;
import pasa.cbentley.core.src4.stator.StatorWriter;
import pasa.cbentley.framework.core.draw.swing.engine.GraphicsSwing;
import pasa.cbentley.framework.core.ui.src4.engine.CanvasHostAbstract;
import pasa.cbentley.framework.core.ui.src4.interfaces.IWrapperManager;
import pasa.cbentley.framework.core.ui.src4.tech.IBOFramePos;
import pasa.cbentley.framework.core.ui.swing.ctx.CoreUiSwingCtx;
import pasa.cbentley.framework.core.ui.swing.ctx.ITechStatorableCoreUiSwing;
import pasa.cbentley.framework.core.ui.swing.engine.CanvasHostSwingAbstract;
import pasa.cbentley.framework.coredraw.src4.interfaces.IGraphics;
import pasa.cbentley.swing.window.CBentleyFrame;

/**
 * Wrapper with a {@link CBentleyFrame}.
 * <br>
 * <br>
 * Controls the creation of the {@link JFrame} and the {@link BufferStrategy} if any is required.
 * <br>
 * 
 * When a repaint call is made, {@link CanvasHostSwingAbstract#icRepaint()}
 * 
 * Does not manage application level concerns.
 * 
 * @author Charles Bentley
 *
 */
public class WrapperSwingTopFrame extends WrapperAbstractSwing {

   protected BufferStrategy       bufferStrategy;

   protected final CoreUiSwingCtx cucSwing;

   protected CBentleyFrame        frame;

   private FrameComponentListener frameListener;

   private GraphicsSwing          gx;

   private boolean                isVSync = true;

   private Graphics2D             myGraphics;

   /**
    * By default the frame will have the size and position on the center
    * @param cuc
    */
   public WrapperSwingTopFrame(CoreUiSwingCtx cuc) {
      super(cuc);
      cucSwing = cuc;
      frame = new CBentleyFrame(cuc.getSwingCtx());

      //#debug
      toDLog().pCreate("", this, WrapperSwingTopFrame.class, "Created@66", LVL_04_FINER, true);

   }

   /**
    * Sets the Canvas to the frame
    */
   public void addCanvas(CanvasHostSwingAbstract ac) {
      Component component = canvas.getRealCanvas();
      frame.add(component);
      frameListener = new FrameComponentListener(cuc, ac);
      frame.addComponentListener(frameListener);

   }

   public void canvasHide() {
      frame.setVisible(false);
   }

   public void canvasShow() {
      //frame.pack();
      frame.setVisible(true);
   }

   public void flushGraphics() {
      //#debug
      toDLog().pFlow("isVSync=" + isVSync, this, WrapperSwingTopFrame.class, "flushGraphics@122", LVL_03_FINEST, true);

      //if an exception.. this must be called
      if (myGraphics != null) {
         myGraphics.dispose();
      }
      // Shows the contents of the backbuffer on the screen.
      if (bufferStrategy != null) {
         bufferStrategy.show();
      }
      if (isVSync) {
         //Tell the System to do the Drawing now, otherwise it can take a few extra ms until 
         //Drawing is done which looks very jerky
         Toolkit.getDefaultToolkit().sync();
      }
   }

   /**
    * The JFrame.
    * <br>
    * When inside a SWT component, return null
    * @return null if no frame has been created yet
    */
   public CBentleyFrame getFrame() {
      return frame;
   }

   /**
    * Method for those wrappers that just use the default frame positions.
    * 
    * This is only called by host implementations that have movable frames
    * 
    * @param state
    */
   protected ByteObject getFramePos() {
      //sets current pos. This is the absolute position

      //delegate to the wrapper how to set the ui state
      ByteObject framePos = cuc.createBOFrameDefault();
      CanvasHostAbstract ch = this.canvas;

      //wrapper size

      framePos.set2(IBOFramePos.FPOS_OFFSET_02_X2, frame.getX());
      framePos.set2(IBOFramePos.FPOS_OFFSET_03_Y2, frame.getY());
      framePos.set2(IBOFramePos.FPOS_OFFSET_04_W2, frame.getWidth());
      framePos.set2(IBOFramePos.FPOS_OFFSET_05_H2, frame.getHeight());

      boolean isFullScreen = ch.isCanvasFeatureEnabled(SUP_ID_27_FULLSCREEN);
      framePos.setFlag(IBOFramePos.FPOS_OFFSET_01_FLAG, IBOFramePos.FPOS_FLAG_1_FULLSCREEN, isFullScreen);

      boolean isAlwaysOnTop = ch.isCanvasFeatureEnabled(SUP_ID_28_ALWAYS_ON_TOP);
      framePos.setFlag(IBOFramePos.FPOS_OFFSET_01_FLAG, IBOFramePos.FPOS_FLAG_5_ALWAYS_ON_TOP, isAlwaysOnTop);

      return framePos;
   }

   /**
    * Tips for creating buffer strategy
    * http://docs.oracle.com/javase/tutorial/extra/fullscreen/bufferstrategy.html
    * 
    * 
    * ----
    *  So far hardware acceleration is never enabled by default, and to my knowledge it has not changed yet.
    *   To activate rendering acceleration pass this arg 
    *   (-Dsun.java2d.opengl=true) to the Java launcher at program start up, 
    *   or set it before using any rendering libraries. System.setProperty("sun.java2d.opengl", "true");
    *    It is an optional parameter.
    *  
    *  Tips from
    *  http://stackoverflow.com/questions/4627320/java-hardware-acceleration
    * ------
    * 
    * Graphics depends on the Wrapper.
    */
   public IGraphics getGraphics() {
      //frame.setIgnoreRepaint(true);
      //#debug
      toDLog().pFlow("", this, WrapperSwingTopFrame.class, "getGraphics@170", LVL_03_FINEST, true);
      if (bufferStrategy == null) {

         BufferCapabilities bc = frame.getGraphicsConfiguration().getBufferCapabilities();
         //how to decide number of buffers? from tech param
         try {
            //#debug
            toDLog().pInit("Creating new BufferStrategy", this, WrapperSwingTopFrame.class, "getGraphics", LVL_05_FINE, false);

            frame.createBufferStrategy(2, bc);
         } catch (AWTException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
         bufferStrategy = frame.getBufferStrategy();
         //#debug
         toDLog().pInit("BufferStrategy created", this, WrapperSwingTopFrame.class, "getGraphics", LVL_05_FINE, false);

      }
      myGraphics = (Graphics2D) bufferStrategy.getDrawGraphics();

      //translate of top insets
      int y = this.frame.getInsets().top;
      int x = this.frame.getInsets().left;
      myGraphics.translate(x, y);

      //#debug
      toDLog().pInit("getDrawGraphics", new Graphics2DStringable(cucSwing.getSwingCtx(), myGraphics), WrapperSwingTopFrame.class, "getGraphics", LVL_05_FINE, false);

      if (gx == null) {
         gx = new GraphicsSwing(cuc.getCoreDrawSwingCtx(), myGraphics);
      } else {
         gx.setGraphics2D(myGraphics);
      }

      //#debug
      gx.toStringSetNameDebug("WrapperSwingTopFrame getGraphics");
      return gx;
   }

   public int getStatorableClassID() {
      return ITechStatorableCoreUiSwing.CLASSID_2_WRAPPER_SWING_TOP_FRAME;
   }

   public String getTitle() {
      return frame.getTitle();
   }

   public int getX() {
      return frame.getX();
   }

   public int getY() {
      return frame.getY();
   }

   public boolean hasFeatureSupport(int featureID) {
      switch (featureID) {
         case SUP_ID_16_CUSTOM_CURSORS:
         case SUP_ID_26_CANVAS_RESIZE_MOVE:
         case SUP_ID_27_FULLSCREEN:
         case SUP_ID_28_ALWAYS_ON_TOP:
         case SUP_ID_29_UNDECORATED:
         case SUP_ID_30_MINIMIZE:
            return true;
         default:
            return false;
      }
   }

   public boolean isContained() {
      return false;
   }

   public boolean isFeatureEnabled(int feature) {
      if (feature == SUP_ID_27_FULLSCREEN) {
         return frame.isFullScreen();
      } else if (feature == SUP_ID_28_ALWAYS_ON_TOP) {
         return frame.isAlwaysOnTop();
      } else if (feature == SUP_ID_29_UNDECORATED) {
         return frame.isUndecorated();
      } else if (feature == SUP_ID_30_MINIMIZE) {
         if (frame.getState() == Frame.ICONIFIED) {
            return true;
         } else {
            return false;
         }
      } else if (feature == SUP_ID_31_ACTIVATE_FRONT) {
      }
      return false;
   }

   public void onExit() {
      //#debug
      toDLog().pFlow("", frame, WrapperSwingTopFrame.class, "onExit@72", LVL_05_FINE, DEV_4_THREAD);
      frame.setVisible(false);
      frame.dispose();
   }

   public boolean setAlwaysOnTop(boolean mode) {
      frame.setAlwaysOnTop(mode);
      return true;
   }

   public void setDefaultStartPosition() {
      frame.setFramePositionCenter();
   }

   public boolean setFeature(int feature, boolean mode) {
      if (feature == SUP_ID_27_FULLSCREEN) {
         //TODO the set full screen mode is bad. it forces frame as visible
         setFullScreenMode(mode);
         //send a resize event
         return true;
      } else if (feature == SUP_ID_28_ALWAYS_ON_TOP) {
         frame.setAlwaysOnTop(mode);
         return true;
      } else if (feature == SUP_ID_29_UNDECORATED) {
         //https://stackoverflow.com/questions/875132/how-to-call-setundecorated-after-a-frame-is-made-visible
         //must be invisible. dispose it
         frame.dispose();
         frame.setUndecorated(mode); //only then can you change os elements
         frame.setVisible(true);
         return true;
      } else if (feature == SUP_ID_30_MINIMIZE) {
         if (mode) {
            frame.setState(Frame.ICONIFIED);
         } else {
            frame.setState(Frame.NORMAL);
         }
         return true;
      } else if (feature == SUP_ID_31_ACTIVATE_FRONT) {
      }
      return false;
   }

   /**
    * How to set full screen mode in Swing?
    * That's the job of the {@link DeviceDriver}. Similarly for vibration and light uses.
    * <br>
    * When Canvas is the sole owner of the JFrame.
    * <br>
    * In the case of a Mosaic, the fullscreen call does nothing
    * @param mode
    */
   public void setFullScreenMode(boolean mode) {
      //driver will return null, 
      if (frame != null) {
         if (mode) {
            frame.setFullScreenTrue();
         } else {
            frame.setFullScreenFalse();
         }
      }
   }

   public void setIcon(String str) {
      //#debug
      toDLog().pBridge("str=" + str, this, WrapperSwingTopFrame.class, "setIcon", LVL_05_FINE, true);
      if (frame != null && str != null) {
         if (!str.startsWith("/")) {
            str = "/" + str;
         }
         Image image = cuc.getSwingCtx().createImage(str, "");
         if (image == null) {
            toDLog().pBridge("Null Image for " + str, this, WrapperSwingTopFrame.class, "setIcon", LVL_09_WARNING, true);
         }
         frame.setIconImage(image);
      }
   }

   public void setPosition(int x, int y) {
      //#debug
      toDLog().pBridge1("x=" + x + ", y=" + y, this, WrapperSwingTopFrame.class, "setPosition");
      frame.setLocation(x, y);
   }

   /**
    * Wrapper is responsible to account for Frame insets.
    */
   public void setSize(int w, int h) {
      Insets insets = frame.getInsets();
      int frameWidth = (int) (w) + insets.left + insets.right;
      int frameHeight = (int) (h) + insets.top + insets.bottom;

      //#debug
      String msg = "w=" + w + ", h=" + h + " ActuFrame=" + frame.getBounds().getWidth() + "," + frame.getBounds().getHeight();
      //#debug
      toDLog().pBridge1(msg, this, WrapperSwingTopFrame.class, "setSize@line238");

      frame.setSize(frameWidth, frameHeight);
      frame.pack(); //this line force the correct sizing.. why?
   }

   public void setTitle(String str) {
      frame.setTitle(str);
   }

   public boolean setUndecorated(boolean mode) {
      frame.setUndecorated(mode);
      return true;
   }

   public void stateReadFrom(StatorReader state) {
      super.stateReadFrom(state);

      ByteObject fp = ((StatorReaderBO) state).readByteObject();

      int x = fp.get2Signed(IBOFramePos.FPOS_OFFSET_02_X2);
      int y = fp.get2Signed(IBOFramePos.FPOS_OFFSET_03_Y2);

      //#debug
      toDLog().pStator("Reading x=" + x + "\t y=" + y + "\t", this, WrapperSwingTopFrame.class, "stateReadFrom@386", LVL_05_FINE, true);

      //this.setPosition(x, y);

      int w = fp.get2Signed(IBOFramePos.FPOS_OFFSET_04_W2);
      int h = fp.get2Signed(IBOFramePos.FPOS_OFFSET_05_H2);

      //#debug
      toDLog().pStator("Reading w=" + w + "\t h=" + h + "\t", this, WrapperSwingTopFrame.class, "stateReadFrom@365", LVL_05_FINE, true);

      //this.setSize(w, h);

      setAlwaysOnTop(fp.hasFlag(IBOFramePos.FPOS_OFFSET_01_FLAG, IBOFramePos.FPOS_FLAG_5_ALWAYS_ON_TOP));
   }

   public void stateWriteTo(StatorWriter state) {
      super.stateWriteTo(state);

      ByteObject fp = getFramePos();
      ((StatorWriterBO) state).dataWriteByteObject(fp);
      
      int x = fp.get2Signed(IBOFramePos.FPOS_OFFSET_02_X2);
      int y = fp.get2Signed(IBOFramePos.FPOS_OFFSET_03_Y2);

      //#debug
      toDLog().pStator("Writing x=" + x + "\t y=" + y + "\t", this, WrapperSwingTopFrame.class, "stateWriteTo@386", LVL_05_FINE, true);

      //this.setPosition(x, y);

      int w = fp.get2Signed(IBOFramePos.FPOS_OFFSET_04_W2);
      int h = fp.get2Signed(IBOFramePos.FPOS_OFFSET_05_H2);

      //#debug
      toDLog().pStator("Writing w=" + w + "\t h=" + h + "\t", this, WrapperSwingTopFrame.class, "stateWriteTo@419", LVL_05_FINE, true);

   
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, WrapperSwingTopFrame.class, 350);
      toStringPrivate(dc);
      super.toString(dc.sup());
      dc.nl();
      if (frame != null) {
         dc.appendVarWithSpace("isDoubleBuffered", frame.isDoubleBuffered());
         dc.appendVarWithSpace("isAlwaysOnTop", frame.isAlwaysOnTop());
      }
      dc.nlLvl(frame, CBentleyFrame.class);
      dc.nlLvl(frameListener, FrameComponentListener.class);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, WrapperSwingTopFrame.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }
   //#enddebug

   private void toStringPrivate(Dctx dc) {
      if (frame != null) {
         dc.appendVarWithSpace("Title", frame.getTitle());
         dc.appendVarWithSpace("Position", frame.getX() + "," + frame.getY());
         dc.appendVarWithSpace("Size", frame.getWidth() + "," + frame.getHeight());
      }
   }

}
