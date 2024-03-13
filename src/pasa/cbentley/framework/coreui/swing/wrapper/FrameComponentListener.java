package pasa.cbentley.framework.coreui.swing.wrapper;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.framework.coreui.swing.ctx.CoreUiSwingCtx;
import pasa.cbentley.framework.coreui.swing.engine.CanvasHostSwing;

public class FrameComponentListener implements ComponentListener, IStringable {

   private CanvasHostSwing ac;
   protected final CoreUiSwingCtx scc;

   public FrameComponentListener(CoreUiSwingCtx scc, CanvasHostSwing ac) {
      this.scc = scc;
      this.ac = ac;
      
   }
   public void componentHidden(ComponentEvent e) {
   }

   public void componentMoved(ComponentEvent e) {
      ac.componentMoved(e);
   }

   public void componentResized(ComponentEvent e) {
   }

   public void componentShown(ComponentEvent e) {
   }

   
   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, FrameComponentListener.class);
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   private void toStringPrivate(Dctx dc) {
      dc.appendWithSpace("componentMoved");
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, FrameComponentListener.class);
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return scc.getUC();
   }

   //#enddebug
   

}
