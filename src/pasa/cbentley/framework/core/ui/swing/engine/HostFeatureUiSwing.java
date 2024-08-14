package pasa.cbentley.framework.core.ui.swing.engine;

import pasa.cbentley.core.src4.interfaces.IHostFeature;
import pasa.cbentley.framework.core.ui.j2se.engine.HostFeatureUiJ2se;
import pasa.cbentley.framework.core.ui.src4.tech.ITechHostFeatureDrawUI;
import pasa.cbentley.framework.core.ui.swing.ctx.CoreUiSwingCtx;
import pasa.cbentley.framework.coredraw.src4.interfaces.ITechHostFeatureDraw;

/**
 * This class deals with all HostData, from Draw, UI and Core.
 * 
 * If you want a class limited to Draw, you 
 * @author Charles Bentley
 *
 */
public class HostFeatureUiSwing extends HostFeatureUiJ2se implements IHostFeature, ITechHostFeatureDraw {

   public HostFeatureUiSwing(CoreUiSwingCtx cuc) {
      super(cuc);
   }

   public boolean setHostFeatureEnabled(int featureID, boolean b) {
      switch (featureID) {
         default:
            return super.setHostFeatureEnabled(featureID, b);
      }
   }

   public boolean setHostFeatureEnabledFactory(int featureID, boolean b) {
      switch (featureID) {
         case ITechHostFeatureDrawUI.SUP_ID_50_SENSOR_ACCELEROMETER:
            return false;
         default:
            return super.setHostFeatureEnabledFactory(featureID, b);
      }
   }

   public boolean isHostFeatureSupported(int featureID) {
      switch (featureID) {
         case ITechHostFeatureDrawUI.SUP_ID_50_SENSOR_ACCELEROMETER:
            return false;
         default:
            return super.isHostFeatureSupported(featureID);
      }
   }

   public boolean isHostFeatureEnabled(int featureID) {
      switch (featureID) {
         case ITechHostFeatureDrawUI.SUP_ID_50_SENSOR_ACCELEROMETER:
            return false;
         default:
            return super.isHostFeatureEnabled(featureID);
      }
   }

   public boolean isHostFeatureFactoryEnabled(int featureID) {
      switch (featureID) {
         default:
            return super.isHostFeatureFactoryEnabled(featureID);
      }
   }
}
