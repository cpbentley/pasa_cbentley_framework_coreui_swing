package pasa.cbentley.framework.coreui.swing.engine;

import pasa.cbentley.core.src4.event.BusEvent;
import pasa.cbentley.framework.coreio.src5.engine.FileConnectionSrc5;
import pasa.cbentley.framework.coreui.src4.ctx.IEventsCoreUI;
import pasa.cbentley.framework.coreui.src4.event.CanvasHostEvent;
import pasa.cbentley.framework.coreui.src4.interfaces.ITechEventHost;
import pasa.cbentley.framework.coreui.swing.ctx.CoreUiSwingCtx;
import pasa.cbentley.swing.data.Listener;

public class FileDropListener implements Listener {

   protected final CoreUiSwingCtx cuc;
   protected final CanvasHostSwing canvas;

   public FileDropListener(CoreUiSwingCtx cuc, CanvasHostSwing canvas) {
      this.cuc = cuc;
      this.canvas = canvas;
   }
   
   public void filesDropped(java.io.File[] files) {
      //convert File to Bentleys framework file class
      for (int j = 0; j < files.length; j++) {
         FileConnectionSrc5 fci = new FileConnectionSrc5(cuc.getCoreIO5Ctx(), files[j]);
        
         //send it as action to the canvas.
         CanvasHostEvent be = new CanvasHostEvent(cuc, ITechEventHost.ACTION_10_DRAG_DROP, canvas);
         be.setSource(fci);
         //then on the bus
         int eventID = IEventsCoreUI.EVENT_ID_04_DRAG_DROP;
         int producerID = IEventsCoreUI.PID_01_DRAG_DROP;
         Object producer = canvas;
         BusEvent me = cuc.getEventBus().createEvent(producerID, eventID, producer);
         me.setParamO1(fci);
         cuc.getEventBus().putOnBus(me);
      }
   } 
}
