package pasa.cbentley.framework.coreui.swing.engine;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Graphics2D;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.framework.coreui.swing.ctx.CoreUiSwingCtx;

/**
 * Used by Video playback.
 * <br>
 * We want to paint the video in background and in overlay paint stuff above using the Bridge
 * @author Charles Bentley
 *
 */
public class RealCanvasAWTCanvas extends Canvas implements IStringable {

   /**
    * 
    */
   private static final long serialVersionUID = 3629668552412508682L;

   private CanvasHostSwing   bridge;

   private CoreUiSwingCtx    csc;

   public RealCanvasAWTCanvas(CoreUiSwingCtx csc, CanvasHostSwing b) {
      this.csc = csc;
      this.bridge = b;
   }

   @Override
   public void paint(Graphics g) {
      Graphics2D g2 = (Graphics2D) g;
      bridge.paint(g2);
   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, RealCanvasAWTCanvas.class);
      toStringPrivate(dc);
      dc.nl();
      csc.getSwingCtx().toSD().d((Canvas)this,dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, RealCanvasAWTCanvas.class);
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return csc.getUC();
   }

   //#enddebug

}
