package pasa.cbentley.framework.core.ui.swing.wrapper;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.framework.core.ui.j2se.wrapper.WrapperManagerDefaultJ2se;
import pasa.cbentley.framework.core.ui.src4.engine.CanvasHostAbstract;
import pasa.cbentley.framework.core.ui.src4.interfaces.IWrapperManager;
import pasa.cbentley.framework.core.ui.src4.tech.IBOCanvasHost;
import pasa.cbentley.framework.core.ui.src4.tech.ITechWrapper;
import pasa.cbentley.framework.core.ui.src4.wrapper.WrapperAbstract;
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
public class WrapperManagerDefaultSwing extends WrapperManagerDefaultJ2se implements IWrapperManager {

   protected final CoreUiSwingCtx cuic;

   public WrapperManagerDefaultSwing(CoreUiSwingCtx cuic) {
      super(cuic);
      this.cuic = cuic;
      
      //#debug
      toDLog().pCreate("", this, WrapperManagerDefaultSwing.class, "Created@30", LVL_04_FINER, true);

   }

   public WrapperAbstract createNewWrapper(ByteObject tech) {
      int wrapperType = tech.get1(IBOCanvasHost.CANVAS_HOST_OFFSET_10_WRAPPER_TYPE1);
      return new WrapperSwingTopFrame(cuic);
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
