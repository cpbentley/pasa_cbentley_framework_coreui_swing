package pasa.cbentley.framework.core.ui.swing.wrapper;

import java.awt.Component;

import javax.swing.JPanel;

import pasa.cbentley.framework.core.ui.src4.engine.CanvasHostAbstract;
import pasa.cbentley.framework.core.ui.swing.ctx.CoreUiSwingCtx;
import pasa.cbentley.framework.core.ui.swing.engine.CanvasHostSwingAbstract;

/**
 * A Panel with undefined owner type.
 * 
 * Wrapper manager will handle icon and title.
 * 
 * ie. in a tab
 * 
 * @author Charles Bentley
 *
 */
public class WrapperSwingPanel extends WrapperAbstractSwing {

   private JPanel panel;

   /**
    * A Panel Wrapper must have a non null parent
    */
   public WrapperSwingPanel(CoreUiSwingCtx scc) {
      super(scc);
      panel = new JPanel();
   }

   public boolean isContained() {
      return true;
   }

   public void setIcon(String str) {
   }

   public void addCanvas(CanvasHostSwingAbstract ac) {
      Component cc = canvas.getRealCanvas();
      panel.add(cc);
   }

   public void setFullScreenMode(boolean mode) {
   }

   /**
    * 
    */
   public void repaint() {
      panel.repaint();
   }

   /**
    * If this wrapper is wrapped, outside.. ask the parent
    */
   public void setTitle(String title) {
      cuc.getWrapperManager().setTitle(this, title);
   }

   public void setSize(int w, int h) {
      cuc.getWrapperManager().setSize(this, w, h);
   }

   public void setPosition(int x, int y) {
      //TODO what if we want to move tab in a different tab!
      cuc.getWrapperManager().setPosition(this, x, y);
   }

   public boolean hasFeatureSupport(int feature) {
      if (parent != null) {
         //TODO ask manager?
         return parent.hasFeatureSupport(feature);
      } else {
         if (feature == SUP_ID_26_CANVAS_RESIZE_MOVE) {
            return false;
         } else if (feature == SUP_ID_16_CUSTOM_CURSORS) {
            return true;
         } else if (feature == SUP_ID_28_ALWAYS_ON_TOP) {
            return false;
         } else if (feature == SUP_ID_29_UNDECORATED) {
            return false;
         }
         return false;
      }
   }

   public void canvasShow() {
      // TODO Auto-generated method stub

   }

   public void canvasHide() {
      // TODO Auto-generated method stub

   }

   public void setDefaultStartPosition() {
      if(parent != null) {
         parent.setDefaultStartPosition();
      }
   }

}
