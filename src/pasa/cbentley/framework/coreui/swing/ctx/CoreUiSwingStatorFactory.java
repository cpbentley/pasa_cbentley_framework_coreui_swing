package pasa.cbentley.framework.coreui.swing.ctx;

import pasa.cbentley.core.src4.ctx.ICtx;
import pasa.cbentley.core.src4.stator.IStatorFactory;
import pasa.cbentley.framework.coreui.swing.engine.CanvasSwing;

public class CoreUiSwingStatorFactory implements IStatorFactory, ITechStatorableCoreUiSwing {

   private CoreUiSwingCtx coreUiSwingCtx;

   public CoreUiSwingStatorFactory(CoreUiSwingCtx coreUiSwingCtx) {
      this.coreUiSwingCtx = coreUiSwingCtx;
   }

   public Object[] createArray(int classID, int size) {
      switch (classID) {
         case CLASSID_1_CANVASSWING:
            return new CanvasSwing[size];

         default:
            break;
      }
      return null;
   }

   public boolean isSupported(int classID) {
      switch (classID) {
         case CLASSID_1_CANVASSWING:
            return true;

         default:
            break;
      }
      return false;
   }

   public ICtx getCtx() {
      return coreUiSwingCtx;
   }

   public Object createObject(int classID) {
      switch (classID) {
         case CLASSID_1_CANVASSWING:
            return new CanvasSwing(coreUiSwingCtx, null);

         default:
            break;
      }
      return null;
   }

}
