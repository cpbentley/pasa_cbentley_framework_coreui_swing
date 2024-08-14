package pasa.cbentley.framework.core.ui.swing.ctx;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.framework.core.ui.j2se.ctx.ConfigCoreUiJ2seSettable;

public class ConfigCoreUISwingSettable extends ConfigCoreUiJ2seSettable  implements IConfigCoreUiSwing {

   public ConfigCoreUISwingSettable(UCtx uc) {
      super(uc);
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, ConfigCoreUISwingSettable.class, 20);
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, ConfigCoreUISwingSettable.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug
   

   
}
