package pasa.cbentley.framework.core.ui.swing.engine;

import pasa.cbentley.framework.core.ui.j2se.engine.HostServiceUiJ2se;
import pasa.cbentley.framework.core.ui.swing.ctx.CoreUiSwingCtx;

public class HostServiceUiSwing extends HostServiceUiJ2se {

   public HostServiceUiSwing(CoreUiSwingCtx cuc) {
      super(cuc);
   }

   public boolean isHostServiceActive(int serviceID) {
      switch (serviceID) {
         default:
            return super.isHostServiceActive(serviceID);
      }
   }

   public boolean isHostServiceSupported(int serviceID) {
      switch (serviceID) {
         default:
            return isHostServiceSupported(serviceID);
      }
   }

   public boolean setHostServiceActive(int serviceID, boolean isActive) {
      switch (serviceID) {
         default:
            return super.setHostServiceActive(serviceID, isActive);
      }
   }
}
