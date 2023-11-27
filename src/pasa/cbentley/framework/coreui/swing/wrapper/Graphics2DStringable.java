package pasa.cbentley.framework.coreui.swing.wrapper;

import java.awt.Graphics2D;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.swing.ctx.SwingCtx;

public class Graphics2DStringable implements IStringable {

   protected final UCtx       uc;

   protected final Graphics2D g;

   protected final SwingCtx sc;

   public Graphics2DStringable(SwingCtx sc, Graphics2D g) {
      this.sc = sc;
      this.uc = sc.getUCtx();
      this.g = g;

   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, Graphics2DStringable.class, "@line5");
      toStringPrivate(dc);
      dc.nl();
      dc.append(sc.toSD().d(g));
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, Graphics2DStringable.class);
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return uc;
   }

   //#enddebug

}
