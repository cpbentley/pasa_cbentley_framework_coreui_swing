package pasa.cbentley.framework.coreui.swing.wrapper;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.byteobjects.src4.stator.StatorReaderBO;
import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.stator.IStatorFactory;
import pasa.cbentley.core.src4.stator.StatorReader;
import pasa.cbentley.framework.coreui.src4.engine.WrapperAbstract;
import pasa.cbentley.framework.coreui.src4.interfaces.IWrapperManager;
import pasa.cbentley.framework.coreui.src4.tech.IBOCanvasHost;
import pasa.cbentley.framework.coreui.swing.ctx.CoreUiSwingCtx;

/**
 * Canvas owner creates {@link WrapperSwingTopFrame}.
 * 
 * He is unaware of life cycle.
 * 
 * @author Charles Bentley
 *
 */
public class CanvasOwnerDefaultSwing implements IWrapperManager {

   protected final CoreUiSwingCtx cuic;

   public CanvasOwnerDefaultSwing(CoreUiSwingCtx cuic) {
      this.cuic = cuic;
   }


   public WrapperAbstract createNewWrapper(ByteObject tech) {
      //find our wrapper
      int wrapperType = tech.get1(IBOCanvasHost.TCANVAS_OFFSET_02_WRAPPER_TYPE1);
      WrapperAbstract wrapper = null;
      if (wrapperType == IBOCanvasHost.TCANVAS_TYPE_0_DEFAULT) {
         //in a controlled env.. the wrapper is a panel and all new windows must be inside the 
         //semi multi. a new component is drawn over the old one.. like in android. screen size is fixed.
      } else if (wrapperType == IBOCanvasHost.TCANVAS_TYPE_1_FRAME) {

      } else if (wrapperType == IBOCanvasHost.TCANVAS_TYPE_2_CONTROLLED) {

      }
      wrapper = new WrapperSwingTopFrame(cuic);
      return wrapper;
   }


   public boolean setPosition(WrapperAbstract wrapper, int x, int y) {
      return false;
   }

   public boolean setSize(WrapperAbstract wrapper, int w, int h) {
      return false;
   }

   public void setTitle(WrapperAbstract wrapper, String title) {

   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "WrapperManagerDefaultSwing");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "WrapperManagerDefaultSwing");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return cuic.getUCtx();
   }

   private void toStringPrivate(Dctx dc) {

   }

   //#enddebug

}
