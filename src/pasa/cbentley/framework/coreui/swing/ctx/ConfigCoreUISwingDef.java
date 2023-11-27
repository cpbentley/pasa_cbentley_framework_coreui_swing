package pasa.cbentley.framework.coreui.swing.ctx;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.framework.coreui.j2se.ctx.ConfigCoreUIJ2SEDefault;
import pasa.cbentley.framework.coreui.j2se.ctx.IConfigCoreUiJ2se;

public class ConfigCoreUISwingDef extends ConfigCoreUIJ2SEDefault implements IConfigCoreUiJ2se {

   public ConfigCoreUISwingDef(UCtx uc) {
      super(uc);
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, ConfigCoreUISwingDef.class, "@line5");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   private void toStringPrivate(Dctx dc) {
      
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, ConfigCoreUISwingDef.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug
   

}
