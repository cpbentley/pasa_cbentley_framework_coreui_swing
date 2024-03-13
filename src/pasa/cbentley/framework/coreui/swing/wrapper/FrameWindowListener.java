package pasa.cbentley.framework.coreui.swing.wrapper;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.framework.coreui.src4.event.CanvasHostEvent;
import pasa.cbentley.framework.coreui.src4.interfaces.ITechEventHost;
import pasa.cbentley.framework.coreui.swing.ctx.CoreUiSwingCtx;
import pasa.cbentley.framework.coreui.swing.engine.CanvasHostSwing;

public class FrameWindowListener implements WindowListener, WindowFocusListener, IStringable {

   protected final CoreUiSwingCtx scc;

   private CanvasHostSwing    canvas;

   public FrameWindowListener(CoreUiSwingCtx scc, CanvasHostSwing canvas) {
      this.scc = scc;
      this.canvas = canvas;
   }

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

      CanvasHostEvent ge = new CanvasHostEvent(scc, ITechEventHost.ACTION_1_CLOSE, canvas);
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

   public void windowIconified(WindowEvent e) {
      //#debug
      toDLog().pFlow("", this, FrameWindowListener.class, "windowIconified", LVL_03_FINEST, true);
   }

   public void windowOpened(WindowEvent e) {
      //#debug
      toDLog().pFlow("", this, FrameWindowListener.class, "windowOpened", LVL_03_FINEST, true);

   }
   
   public void windowGainedFocus(WindowEvent e) {
      //#debug
      toDLog().pFlow("", this, FrameWindowListener.class, "windowGainedFocus", LVL_03_FINEST, true);

      
   }

   public void windowLostFocus(WindowEvent e) {
      //#debug
      toDLog().pFlow("", this, FrameWindowListener.class, "windowLostFocus", LVL_03_FINEST, true);
      
   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, "WrapperWindowListener");
      toStringPrivate(dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "WrapperWindowListener");
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return scc.getUC();
   }


   //#enddebug

}
