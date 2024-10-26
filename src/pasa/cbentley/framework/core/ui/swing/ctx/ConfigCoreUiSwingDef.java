package pasa.cbentley.framework.core.ui.swing.ctx;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.framework.core.ui.j2se.ctx.ConfigCoreUiJ2seDefault;

public class ConfigCoreUiSwingDef extends ConfigCoreUiJ2seDefault implements IConfigCoreUiSwing {

   public ConfigCoreUiSwingDef(UCtx uc) {
      super(uc);
   }


   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, ConfigCoreUiSwingDef.class, toStringGetLine(15));
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, ConfigCoreUiSwingDef.class, toStringGetLine(15));
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {
      
   }
   //#enddebug
   

   

}
