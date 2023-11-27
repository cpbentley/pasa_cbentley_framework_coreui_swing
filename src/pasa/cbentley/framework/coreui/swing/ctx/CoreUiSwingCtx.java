package pasa.cbentley.framework.coreui.swing.ctx;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.byteobjects.src4.ctx.IConfigBO;
import pasa.cbentley.core.src4.interfaces.IExecutor;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.framework.coredraw.swing.ctx.CoreDrawSwingCtx;
import pasa.cbentley.framework.coreio.src5.ctx.CoreIO5Ctx;
import pasa.cbentley.framework.coreui.j2se.ctx.CoreUiJ2seCtx;
import pasa.cbentley.framework.coreui.j2se.ctx.IConfigCoreUiJ2se;
import pasa.cbentley.framework.coreui.src4.ctx.ITechCtxSettingsCoreUI;
import pasa.cbentley.framework.coreui.src4.engine.CanvasHostAbstract;
import pasa.cbentley.framework.coreui.src4.engine.KeyMapAbstract;
import pasa.cbentley.framework.coreui.src4.engine.WrapperAbstract;
import pasa.cbentley.framework.coreui.src4.interfaces.ICanvasOwner;
import pasa.cbentley.framework.coreui.swing.engine.CanvasHostSwing;
import pasa.cbentley.framework.coreui.swing.engine.CanvasSwing;
import pasa.cbentley.framework.coreui.swing.engine.CoreSwingExecutor;
import pasa.cbentley.framework.coreui.swing.engine.KeyMapSwing;
import pasa.cbentley.framework.coreui.swing.wrapper.CanvasOwnerDefaultSwing;
import pasa.cbentley.swing.ctx.SwingCtx;

public class CoreUiSwingCtx extends CoreUiJ2seCtx {

   private static final int         CTX_ID = 498;

   protected final CoreDrawSwingCtx cdc;

   protected final SwingCtx         sc;

   protected final CoreIO5Ctx       cio5c;

   private CoreSwingExecutor        executor;

   private KeyMapSwing              keyMap;

   public CoreUiSwingCtx(CoreDrawSwingCtx cdc, SwingCtx sc, CoreIO5Ctx cio5c) {
      this(new ConfigCoreUISwingDef(cdc.getUCtx()), cdc, sc, cio5c);
   }

   /**
    * Core UI deals with file connections for drag and drop so we need a {@link CoreIO5Ctx}
    * @param cdc
    * @param sc
    * @param cio5c
    */
   public CoreUiSwingCtx(IConfigCoreUiJ2se configUI, CoreDrawSwingCtx cdc, SwingCtx sc, CoreIO5Ctx cio5c) {
      super(configUI, cdc);
      this.cdc = cdc;
      this.sc = sc;
      this.cio5c = cio5c;
      executor = new CoreSwingExecutor(this);
      keyMap = new KeyMapSwing(uc);
      
      if (this.getClass() == CoreUiSwingCtx.class) {
         a_Init();
      }
   }

   public CoreIO5Ctx getCoreIO5Ctx() {
      return cio5c;
   }

   protected void applySettings(ByteObject settingsNew, ByteObject settingsOld) {
      super.applySettings(settingsNew, settingsOld);
      //#debug
      toDLog().pFlow("", null, CoreUiSwingCtx.class, "applySettings", LVL_04_FINER, true);

      if (settingsNew.hasFlag(ITechCtxSettingsCoreUI.CTX_COREUI_OFFSET_01_FLAG1, ITechCtxSettingsCoreUI.CTX_COREUI_FLAG_2_DRAG_DROP)) {
         CanvasHostAbstract[] canvases2 = getCanvases();
         for (int i = 0; i < canvases2.length; i++) {
            if (canvases2[i] instanceof CanvasHostSwing) {
               ((CanvasHostSwing) canvases2[i]).enableFileDrop();
            }
         }
      } else {
         CanvasHostAbstract[] canvases2 = getCanvases();
         for (int i = 0; i < canvases2.length; i++) {
            if (canvases2[i] instanceof CanvasHostSwing) {
               ((CanvasHostSwing) canvases2[i]).fileDropDisable();
            }
         }
      }
   }

   protected void matchConfig(IConfigBO config, ByteObject settings) {
      super.matchConfig(config, settings);
   }
   
   public CoreDrawSwingCtx getCoreDrawSwingCtx() {
      return cdc;
   }

   public int getCtxID() {
      return CTX_ID;
   }

   public SwingCtx getSwingCtx() {
      return sc;
   }


   public CanvasHostAbstract createCanvasClass(WrapperAbstract wrapper, ByteObject canvasTech) {
      CanvasSwing canvasHost = new CanvasSwing(this, canvasTech);
      canvasHost.setWrapper(wrapper);
      return canvasHost;
   }

   public int getHostInt(int id) {
      return super.getHostInt(id);
   }

   public void runGUI(Runnable run) {
      sc.executeLaterInUIThread(run);
   }

   public ICanvasOwner createCanvasOwnerDefault() {
      return new CanvasOwnerDefaultSwing(this);
   }

   public IExecutor getExecutor() {
      return executor;
   }

   public KeyMapAbstract getKeyMap() {
      return keyMap;
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, "CoreUiSwingCtx");
      toStringPrivate(dc);
      super.toString(dc.sup());
   }

   private void toStringPrivate(Dctx dc) {

   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, "CoreUiSwingCtx");
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   //#enddebug

}
