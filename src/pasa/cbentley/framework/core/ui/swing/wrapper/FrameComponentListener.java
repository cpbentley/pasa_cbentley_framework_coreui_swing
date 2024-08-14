package pasa.cbentley.framework.core.ui.swing.wrapper;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.framework.core.ui.src4.ctx.ObjectCUC;
import pasa.cbentley.framework.core.ui.swing.ctx.CoreUiSwingCtx;
import pasa.cbentley.framework.core.ui.swing.engine.CanvasHostSwing;

public class FrameComponentListener extends ObjectCUC implements ComponentListener, IStringable {

   private CanvasHostSwing canvasHost;

   public FrameComponentListener(CoreUiSwingCtx cuc, CanvasHostSwing ac) {
      super(cuc);
      this.canvasHost = ac;
   }

   public void componentHidden(ComponentEvent e) {
   }

   public void componentMoved(ComponentEvent e) {
      canvasHost.componentMoved(e);
   }

   public void componentResized(ComponentEvent e) {
   }

   public void componentShown(ComponentEvent e) {
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, FrameComponentListener.class, 40);
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, FrameComponentListener.class, 40);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {

   }
   //#enddebug

}
