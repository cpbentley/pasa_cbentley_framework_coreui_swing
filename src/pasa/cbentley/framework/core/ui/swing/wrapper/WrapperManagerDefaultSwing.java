package pasa.cbentley.framework.core.ui.swing.wrapper;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.framework.core.ui.src4.ctx.ObjectCUC;
import pasa.cbentley.framework.core.ui.src4.engine.CanvasHostAbstract;
import pasa.cbentley.framework.core.ui.src4.engine.WrapperAbstract;
import pasa.cbentley.framework.core.ui.src4.interfaces.IWrapperManager;
import pasa.cbentley.framework.core.ui.src4.tech.IBOCanvasHost;
import pasa.cbentley.framework.core.ui.swing.ctx.CoreUiSwingCtx;
import pasa.cbentley.framework.core.ui.swing.engine.CanvasHostSwing;

/**
 * Canvas owner creates {@link WrapperSwingTopFrame}.
 * 
 * He is unaware of life cycle.
 * 
 * @author Charles Bentley
 *
 */
public class WrapperManagerDefaultSwing extends ObjectCUC implements IWrapperManager {

   protected final CoreUiSwingCtx cuic;

   public WrapperManagerDefaultSwing(CoreUiSwingCtx cuic) {
      super(cuic);
      this.cuic = cuic;
      
      //#debug
      toDLog().pCreate("", this, WrapperManagerDefaultSwing.class, "Created@30", LVL_04_FINER, true);

   }

   public CanvasHostAbstract createCanvasHost(WrapperAbstract wrapper, ByteObject canvasTech) {
      CanvasHostSwing canvasHost = new CanvasHostSwing(cuic, canvasTech);
      canvasHost.setWrapper(wrapper);
      return canvasHost;
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
   public void toString(Dctx dc) {
      dc.root(this, WrapperManagerDefaultSwing.class, 65);
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, WrapperManagerDefaultSwing.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {

   }

   //#enddebug

}
