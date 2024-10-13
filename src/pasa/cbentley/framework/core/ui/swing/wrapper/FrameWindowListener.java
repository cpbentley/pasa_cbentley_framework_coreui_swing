package pasa.cbentley.framework.core.ui.swing.wrapper;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.framework.core.ui.src4.ctx.ObjectCUC;
import pasa.cbentley.framework.core.ui.src4.event.CanvasHostEvent;
import pasa.cbentley.framework.core.ui.src4.interfaces.ITechEventHost;
import pasa.cbentley.framework.core.ui.swing.ctx.CoreUiSwingCtx;
import pasa.cbentley.framework.core.ui.swing.engine.CanvasHostSwingAbstract;

public class FrameWindowListener extends ObjectCUC implements WindowListener, WindowFocusListener, IStringable {

   private CanvasHostSwingAbstract canvas;

   public FrameWindowListener(CoreUiSwingCtx cuc, CanvasHostSwingAbstract canvas) {
      super(cuc);
      this.canvas = canvas;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, FrameWindowListener.class, 80);
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, FrameWindowListener.class, 80);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {

   }
   //#enddebug

   public void windowActivated(WindowEvent e) {
      //#debug
      toDLog().pFlow("", this, FrameWindowListener.class, "windowActivated", LVL_03_FINEST, true);
   }

   public void windowClosed(WindowEvent e) {
      //#debug
      toDLog().pFlow("", this, FrameWindowListener.class, "windowClosed", LVL_03_FINEST, true);
   }

   public void windowClosing(WindowEvent e) {
      //#debug
      toDLog().pFlow("", this, FrameWindowListener.class, "windowClosing", LVL_03_FINEST, true);

      CanvasHostEvent ge = new CanvasHostEvent(cuc, ITechEventHost.ACTION_01_CLOSE, canvas);
      canvas.eventBridge(ge);
   }

   public void windowDeactivated(WindowEvent e) {
      //#debug
      toDLog().pFlow("", this, FrameWindowListener.class, "windowDeactivated", LVL_03_FINEST, true);
   }

   public void windowDeiconified(WindowEvent e) {
      //#debug
      toDLog().pFlow("", this, FrameWindowListener.class, "windowDeiconified", LVL_03_FINEST, true);
   }

   public void windowGainedFocus(WindowEvent e) {
      //#debug
      toDLog().pFlow("", this, FrameWindowListener.class, "windowGainedFocus", LVL_03_FINEST, true);

   }

   public void windowIconified(WindowEvent e) {
      //#debug
      toDLog().pFlow("", this, FrameWindowListener.class, "windowIconified", LVL_03_FINEST, true);
   }

   public void windowLostFocus(WindowEvent e) {
      //#debug
      toDLog().pFlow("", this, FrameWindowListener.class, "windowLostFocus", LVL_03_FINEST, true);

   }

   public void windowOpened(WindowEvent e) {
      //#debug
      toDLog().pFlow("", this, FrameWindowListener.class, "windowOpened", LVL_03_FINEST, true);

   }

}
