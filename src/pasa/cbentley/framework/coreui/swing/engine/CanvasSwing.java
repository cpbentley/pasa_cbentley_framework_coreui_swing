package pasa.cbentley.framework.coreui.swing.engine;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferStrategy;

import javax.swing.SwingUtilities;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.framework.coredraw.src4.interfaces.IGraphics;
import pasa.cbentley.framework.coreui.src4.interfaces.ICanvasHost;
import pasa.cbentley.framework.coreui.src4.tech.ITechFeaturesCanvas;
import pasa.cbentley.framework.coreui.swing.ctx.CoreUiSwingCtx;
import pasa.cbentley.framework.coreui.swing.ctx.ITechStatorableCoreUiSwing;
import pasa.cbentley.swing.image.CursorSwing;

/**
 * Implements {@link ICanvasHost} methods specifically for Swing.
 * <br>
 * See {@link CanvasBridgeX} for implementation of generic methods of {@link ICanvasHost}.
 * <br>
 * @author Charles Bentley
 *
 */
public class CanvasSwing extends CanvasHostSwing implements ICanvasHost {

   /**
    * Init size?
    * @param dd
    * @param container
    * @param tech
    */
   public CanvasSwing(CoreUiSwingCtx scc, ByteObject tech) {
      super(scc, tech);

   }

   public void flushGraphics() {
      wrapperSwing.flushGraphics();
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
      //some wrapper can't access the graphics object. they will throw an exception
      if (realComponent instanceof RealCanvasAWTCanvas) {
         RealCanvasAWTCanvas canvas = (RealCanvasAWTCanvas) realComponent;
         //return canvas.getGraphics();
      }
      return wrapperSwing.getGraphics();
   }

   public int getICHeight() {
      return realComponent.getHeight();
   }

   public int getICWidth() {
      return realComponent.getWidth();
   }

   public void icRepaint() {
      //we must assume this method can be called outside the UI thread.
      realComponent.repaint();
      wrapperSwing.repaint();
   }

   public void icRepaint(int x, int y, int w, int h) {
      realComponent.repaint(x, y, w, h);
   }

   public int getStatorableClassID() {
      return ITechStatorableCoreUiSwing.CLASSID_1_CANVASSWING;
   }

   /**
    * This invalidates the Graphics Object.
    * <br>
    * When Wrapper prevent size set from Appli calls.
    * this method does nothing. externally 
    */
   public void icSetSize(int w, int h) {
      //we want this size overall with wrapper?
      Dimension dim = new Dimension(w, h);
      realComponent.setPreferredSize(dim); //NOTE: added to frame then pack
      realComponent.setSize(dim); //NOTE: added to frame then pack
      wrapper.setSize(w, h);
   }

   /**
    * The position is entirely decide by the {@link IAppli} layouting.
    * <br>
    * Host just position 
    */
   public void icSetXY(int x, int y) {
      wrapper.setPosition(x, y);
   }

   public int getScreenY(int y) {
      Point p = new Point(0, y);
      SwingUtilities.convertPointToScreen(p, realComponent);
      return p.y;
   }

   public int getScreenX(int x) {
      Point p = new Point(x, 0);
      SwingUtilities.convertPointToScreen(p, realComponent);
      return p.x;
   }

   public boolean isCanvasFeatureEnabled(int feature) {
      if (feature == SUP_ID_27_FULLSCREEN) {
         return wrapper.isFeatureEnabled(feature);
      } else if (feature == SUP_ID_27_FULLSCREEN) {
         return wrapper.isFeatureEnabled(feature);
      } else if (feature == SUP_ID_28_ALWAYS_ON_TOP) {
         return wrapper.isFeatureEnabled(feature);
      } else if (feature == SUP_ID_29_UNDECORATED) {
         return wrapper.isFeatureEnabled(feature);
      } else if (feature == SUP_ID_30_MINIMIZE) {
         return wrapper.isFeatureEnabled(feature);
      } else if (feature == SUP_ID_31_ACTIVATE_FRONT) {
         return wrapper.isFeatureEnabled(feature);
      }
      //ask j2se driver
      return super.isCanvasFeatureEnabled(feature);
   }

   /**
    * When a Swing Panel is in its own JFrame, some features become available.
    * <br>
    * Which panel owns the JFrame? The first one ? or the defined owner.
    * 
    * By design, there is one {@link SwingManager#getCanvasSwing()}
    * only returns one canvas.
    * The container is master
    */
   public boolean isCanvasFeatureSupported(int feature) {
      if (feature == SUP_ID_26_CANVAS_RESIZE_MOVE) {
         return wrapper.hasFeatureSupport(feature);
      } else if (feature == SUP_ID_16_CUSTOM_CURSORS) {
         return true;
      } else if (feature == SUP_ID_27_FULLSCREEN) {
         return wrapper.hasFeatureSupport(feature);
      } else if (feature == SUP_ID_28_ALWAYS_ON_TOP) {
         return wrapper.hasFeatureSupport(feature);
      } else if (feature == SUP_ID_29_UNDECORATED) {
         return wrapper.hasFeatureSupport(feature);
      } else if (feature == SUP_ID_30_MINIMIZE) {
         return wrapper.hasFeatureSupport(feature);
      } else if (feature == SUP_ID_31_ACTIVATE_FRONT) {
         return wrapper.hasFeatureSupport(feature);
      }
      return super.isCanvasFeatureSupported(feature);
   }

   public boolean setCanvasFeature(int feature, boolean mode) {
      if (feature == SUP_ID_27_FULLSCREEN) {
         return wrapper.setFeature(feature, mode);
      } else if (feature == SUP_ID_27_FULLSCREEN) {
         return wrapper.setFeature(feature, mode);
      } else if (feature == SUP_ID_28_ALWAYS_ON_TOP) {
         return wrapper.setFeature(feature, mode);
      } else if (feature == SUP_ID_29_UNDECORATED) {
         return wrapper.setFeature(feature, mode);
      } else if (feature == SUP_ID_30_MINIMIZE) {
         return wrapper.setFeature(feature, mode);
      } else if (feature == SUP_ID_31_ACTIVATE_FRONT) {
         return wrapper.setFeature(feature, mode);
      } else if (feature == SUP_ID_04_ALIAS) {
         toggleAlias();
         return true;
      }
      return super.setCanvasFeature(feature, mode);
   }

   public boolean setCanvasFeature(int feature, Object mode) {
      if (feature == ITechFeaturesCanvas.SUP_ID_16_CUSTOM_CURSORS) {
         if (mode == null || mode instanceof String) {
            CursorSwing cs = new CursorSwing(scc.getSwingCtx(), realComponent);
            boolean b = cs.updateCursor(mode);
            return b;
         }
         return false;
      }
      return false;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, CanvasSwing.class, "@line5");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   private void toStringPrivate(Dctx dc) {
      if (realComponent != null) {
         dc.append('[');
         dc.append(realComponent.getX());
         dc.append(',');
         dc.append(realComponent.getY());
         dc.append(' ');
         dc.append(realComponent.getWidth());
         dc.append('-');
         dc.append(realComponent.getHeight());
         dc.append(']');
      }
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, CanvasSwing.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug

}
