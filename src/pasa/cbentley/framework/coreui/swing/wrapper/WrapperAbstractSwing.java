package pasa.cbentley.framework.coreui.swing.wrapper;

import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.framework.coredraw.src4.interfaces.IGraphics;
import pasa.cbentley.framework.coreui.j2se.engine.WrapperAbstractJ2SE;
import pasa.cbentley.framework.coreui.src4.engine.CanvasHostAbstract;
import pasa.cbentley.framework.coreui.src4.interfaces.IWrapperManager;
import pasa.cbentley.framework.coreui.swing.ctx.CoreUiSwingCtx;
import pasa.cbentley.framework.coreui.swing.engine.CanvasHostSwing;

/**
 * Handles the wrapping of {@link CanvasHostSwing} in something else.
 * <br>
 * <br>
 * At this level the wrapper is not aware of the framework lifecycle.
 * 
 * Simply provides base UI skeletons.
 * 
 * @author Charles Bentley
 *
 */
public abstract class WrapperAbstractSwing extends WrapperAbstractJ2SE {

   protected CanvasHostSwing      canvas;

   protected final CoreUiSwingCtx cuc;

   protected WrapperAbstractSwing(CoreUiSwingCtx cuc) {
      super(cuc);
      this.cuc = cuc;
   }

   /**
    * Put the {@link CanvasHostSwing} into the metal of a real Swing compo.
    * @param canvasHostSwing
    */
   protected abstract void addCanvas(CanvasHostSwing canvasHostSwing);


   /**
    * null if none was set
    * @return
    */
   public CanvasHostSwing getCanvasSwing() {
      return canvas;
   }

   public CoreUiSwingCtx getCUC() {
      return cuc;
   }

   public IGraphics getGraphics() {
      return null;
   }

   public int getX() {
      return 0;
   }

   public int getY() {
      return 0;
   }

   public boolean isFeatureEnabled(int feature) {
      return false;
   }

   public void repaint() {
      // TODO Auto-generated method stub

   }

   public boolean setAlwaysOnTop(boolean mode) {
      return false;
   }

   /** 
    * Initialize the wrapper with the Canvas.
    * <br><br>
    * The {@link SwingManager} when requested to create a new canvas does the following
    * <li> Asks its {@link IWrapperManager} which wrapper to create.
    * <li> Creates a {@link CanvasHostSwing} with requested capabilities (OpenGL, Active Rendering etc)
    * <li> Link the Wrapper with the {@link CanvasHostSwing}
    * <li> Link the {@link CanvasHostSwing} with the wrapper.
    * <br>
    * The method is responsible to "add" the Canvas to the wrapper structure.
    * Defines the kernel and everything
    * @param can {@link CanvasHostAbstract}
    */
   public void setCanvasHost(CanvasHostAbstract can) {
      if (can instanceof CanvasHostSwing) {
         canvas = (CanvasHostSwing) can;
         addCanvas(canvas);
      } else {
         throw new IllegalArgumentException();
      }
   }

   public void setFullScreenMode(boolean mode) {

   }

   public void setIcon(String str) {

   }

   public void setPosition(int x, int y) {

   }

   public void setSize(int w, int h) {

   }

   /**
    * a wrapper able to manage a title overrides this method
    */
   public void setTitle(String str) {

   }

   public void flushGraphics() {

   }

   public boolean setUndecorated(boolean mode) {
      return false;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, WrapperAbstractSwing.class);
      toStringPrivate(dc);
      super.toString(dc.sup());
      dc.nlLvl(canvas);
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, WrapperAbstractSwing.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {

   }

   //#enddebug

}
