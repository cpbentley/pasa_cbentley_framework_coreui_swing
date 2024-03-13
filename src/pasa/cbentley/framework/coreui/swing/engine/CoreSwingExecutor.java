package pasa.cbentley.framework.coreui.swing.engine;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.framework.coreui.j2se.ctx.CoreUiJ2seCtx;
import pasa.cbentley.framework.coreui.j2se.engine.J2SEThreader;
import pasa.cbentley.framework.coreui.swing.ctx.CoreUiSwingCtx;
import pasa.cbentley.swing.ctx.SwingExecutor;

public class CoreSwingExecutor extends J2SEThreader {

   protected final CoreUiSwingCtx cusc;
   protected final SwingExecutor swingExecutor;

   public CoreSwingExecutor(CoreUiSwingCtx cuc) {
      super(cuc);
      cusc = cuc;
      swingExecutor = new SwingExecutor(cuc.getSwingCtx());
   }

   public void executeWorker(Runnable run) {
      swingExecutor.executeWorker(run);
   }

   public void executeMainNow(Runnable run) {
      swingExecutor.executeMainNow(run);
   }

   public void executeMainLater(Runnable run) {
      swingExecutor.executeMainLater(run);
   }

   public void callSerially(Runnable run) {
      swingExecutor.executeMainLater(run);
   }
   
   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "CoreSwingExecutor");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "CoreSwingExecutor");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return cusc.getUC();
   }

   //#enddebug
   


}
