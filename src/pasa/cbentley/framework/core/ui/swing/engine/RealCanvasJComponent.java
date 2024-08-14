package pasa.cbentley.framework.core.ui.swing.engine;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

import pasa.cbentley.core.src4.ctx.UCtx;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.logging.IDLog;
import pasa.cbentley.core.src4.logging.IStringable;
import pasa.cbentley.framework.core.ui.swing.ctx.CoreUiSwingCtx;

public class RealCanvasJComponent extends JComponent implements IStringable {

   /**
    * 
    */
   private static final long       serialVersionUID = 3629668552412508682L;

   private CanvasHostSwingAbstract canvasHost;

   protected final CoreUiSwingCtx  csc;

   public RealCanvasJComponent(CoreUiSwingCtx csc, CanvasHostSwingAbstract canvas) {
      this.csc = csc;
      this.canvasHost = canvas;
   }

   public void paint(Graphics g) {
      //this should not be called in active rendering TODO
      Graphics2D g2 = (Graphics2D) g;
      try {
         canvasHost.paint(g2);
      } catch (Exception e) {
         String msg = e.getMessage();

         String print = e.getClass().getName() + " " + msg;
         g2.setColor(Color.BLACK);
         g2.fillRect(0, 0, getWidth(), getHeight());
         g2.setColor(Color.RED);
         g2.drawString(print, 5, 25);

         e.printStackTrace();
      }
   }

   //#mdebug
   public IDLog toDLog() {
      return toStringGetUCtx().toDLog();
   }

   public String toString() {
      return Dctx.toString(this);
   }

   public void toString(Dctx dc) {
      dc.root(this, RealCanvasJComponent.class, 60);
      toStringPrivate(dc);
      dc.nlLvl(canvasHost, "canvasHost");
      dc.nl();
      csc.getSwingCtx().toSD().d((JComponent) this, dc);
   }

   public String toString1Line() {
      return Dctx.toString1Line(this);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, RealCanvasJComponent.class);
      toStringPrivate(dc);
   }

   public UCtx toStringGetUCtx() {
      return csc.getUC();
   }

   private void toStringPrivate(Dctx dc) {

   }

   //#enddebug

}
