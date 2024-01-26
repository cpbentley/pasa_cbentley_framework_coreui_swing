package pasa.cbentley.framework.coreui.swing.ctx;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.byteobjects.src4.stator.StatorReaderBO;
import pasa.cbentley.core.src4.ctx.ICtx;
import pasa.cbentley.core.src4.stator.IStatorFactory;
import pasa.cbentley.core.src4.stator.IStatorable;
import pasa.cbentley.core.src4.stator.ITechStator;
import pasa.cbentley.core.src4.stator.StatorReader;
import pasa.cbentley.framework.coreui.swing.engine.CanvasSwing;
import pasa.cbentley.framework.coreui.swing.wrapper.WrapperSwingTopFrame;

public class StatorFactoryCoreUiSwing implements IStatorFactory, ITechStatorableCoreUiSwing {

   private CoreUiSwingCtx cuc;

   public StatorFactoryCoreUiSwing(CoreUiSwingCtx cuc) {
      this.cuc = cuc;
   }

   public Object[] createArray(int classID, int size) {
      switch (classID) {
         case CLASSID_1_CANVASSWING:
            return new CanvasSwing[size];
         case CLASSID_2_WRAPPER_SWING_TOP_FRAME:
            return new WrapperSwingTopFrame[size];

         default:
            break;
      }
      return null;
   }

   public boolean isSupported(IStatorable statorable) {
      if (statorable.getClass() == CanvasSwing.class) {
         return true;
      } else if (statorable.getClass() == WrapperSwingTopFrame.class) {
         return true;
      }
      return false;
   }

   public ICtx getCtx() {
      return cuc;
   }

   public Object createObject(StatorReader reader, int classID) {
      switch (classID) {
         case CLASSID_1_CANVASSWING:
            return createCanvasSwing(reader);
         case CLASSID_2_WRAPPER_SWING_TOP_FRAME:
            return new WrapperSwingTopFrame(cuc);

         default:
            break;
      }
      return null;
   }

   private Object createCanvasSwing(StatorReader reader) {
      StatorReaderBO srbo = (StatorReaderBO) reader;
      srbo.checkInt(ITechStator.MAGIC_WORD_OBJECT_PARAM);
      ByteObject boCanvasHost = srbo.readByteObject();
      return new CanvasSwing(cuc, boCanvasHost);
   }

}
