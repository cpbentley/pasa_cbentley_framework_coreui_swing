package pasa.cbentley.framework.core.ui.swing.ctx;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.byteobjects.src4.stator.StatorReaderBO;
import pasa.cbentley.core.src4.ctx.ICtx;
import pasa.cbentley.core.src4.stator.IStatorFactory;
import pasa.cbentley.core.src4.stator.IStatorable;
import pasa.cbentley.core.src4.stator.ITechStator;
import pasa.cbentley.core.src4.stator.StatorReader;
import pasa.cbentley.framework.core.ui.src4.ctx.ObjectCUC;
import pasa.cbentley.framework.core.ui.src4.interfaces.IWrapperManager;
import pasa.cbentley.framework.core.ui.swing.engine.CanvasHostSwing;
import pasa.cbentley.framework.core.ui.swing.wrapper.WrapperSwingTopFrame;

public class StatorFactoryCoreUiSwing extends ObjectCUC implements IStatorFactory, ITechStatorableCoreUiSwing {

   private CoreUiSwingCtx cuc;

   public StatorFactoryCoreUiSwing(CoreUiSwingCtx cuc) {
      super(cuc);
      this.cuc = cuc;
   }

   public Object[] createArray(int classID, int size) {
      switch (classID) {
         case CLASSID_1_CANVASSWING:
            return new CanvasHostSwing[size];
         case CLASSID_2_WRAPPER_SWING_TOP_FRAME:
            return new WrapperSwingTopFrame[size];

         default:
            break;
      }
      return null;
   }

   public boolean isSupported(IStatorable statorable) {
      if (statorable.getClass() == CanvasHostSwing.class) {
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
            return createWrapperSwingTopFrame(reader);
         default:
            break;
      }
      return null;
   }

   private Object createWrapperSwingTopFrame(StatorReader reader) {
      //#debug
      toDLog().pStator("", reader, StatorFactoryCoreUiSwing.class, "createWrapperSwingTopFrame@64", LVL_04_FINER, true);
      return new WrapperSwingTopFrame(cuc);

   }

   private Object createCanvasSwing(StatorReader reader) {
      //#debug
      toDLog().pStator("", reader, StatorFactoryCoreUiSwing.class, "createCanvasSwing@64", LVL_04_FINER, true);
      StatorReaderBO srbo = (StatorReaderBO) reader;
      srbo.checkInt(ITechStator.MAGIC_WORD_OBJECT_PARAM);
      ByteObject boCanvasHost = srbo.readByteObject();
      return new CanvasHostSwing(cuc, boCanvasHost);
   }

}
