package pasa.cbentley.framework.core.ui.swing.ctx;

import pasa.cbentley.byteobjects.src4.core.ByteObject;
import pasa.cbentley.byteobjects.src4.ctx.IConfigBO;
import pasa.cbentley.core.src4.interfaces.IExecutor;
import pasa.cbentley.core.src4.logging.Dctx;
import pasa.cbentley.core.src4.stator.IStatorFactory;
import pasa.cbentley.framework.core.draw.swing.ctx.CoreDrawSwingCtx;
import pasa.cbentley.framework.core.io.src5.ctx.CoreIO5Ctx;
import pasa.cbentley.framework.core.ui.j2se.ctx.CoreUiJ2seCtx;
import pasa.cbentley.framework.core.ui.j2se.engine.HostDataUiJ2se;
import pasa.cbentley.framework.core.ui.j2se.engine.HostFeatureUiJ2se;
import pasa.cbentley.framework.core.ui.j2se.engine.HostServiceUiJ2se;
import pasa.cbentley.framework.core.ui.src4.ctx.IBOCtxSettingsCoreUi;
import pasa.cbentley.framework.core.ui.src4.engine.CanvasHostAbstract;
import pasa.cbentley.framework.core.ui.src4.engine.KeyMapAbstract;
import pasa.cbentley.framework.core.ui.src4.engine.WrapperAbstract;
import pasa.cbentley.framework.core.ui.src4.interfaces.IWrapperManager;
import pasa.cbentley.framework.core.ui.swing.engine.CanvasHostSwingAbstract;
import pasa.cbentley.framework.core.ui.swing.engine.CanvasHostSwing;
import pasa.cbentley.framework.core.ui.swing.engine.HostDataUiSwing;
import pasa.cbentley.framework.core.ui.swing.engine.HostFeatureUiSwing;
import pasa.cbentley.framework.core.ui.swing.engine.HostServiceUiSwing;
import pasa.cbentley.framework.core.ui.swing.engine.KeyMapSwing;
import pasa.cbentley.framework.core.ui.swing.wrapper.WrapperManagerDefaultSwing;
import pasa.cbentley.swing.ctx.SwingCtx;

public class CoreUiSwingCtx extends CoreUiJ2seCtx {

   private static final int         CTX_ID = 498;

   protected final CoreDrawSwingCtx cdc;

   protected final CoreIO5Ctx       cio5c;

   private HostDataUiSwing          hostDataUISwing;

   private HostFeatureUiSwing       hostFeatureUiSwing;

   private HostServiceUiSwing       hostServiceUiSwing;

   private KeyMapSwing              keyMap;

   protected final SwingCtx         sc;

   private StatorFactoryCoreUiSwing statorFactory;

   public CoreUiSwingCtx(CoreDrawSwingCtx cdc, SwingCtx sc, CoreIO5Ctx cio5c) {
      this(null, cdc, sc, cio5c);
   }

   /**
    * Core UI deals with file connections for drag and drop so we need a {@link CoreIO5Ctx}
    * @param cdc
    * @param sc
    * @param cio5c
    */
   public CoreUiSwingCtx(IConfigCoreUiSwing configUI, CoreDrawSwingCtx cdc, SwingCtx sc, CoreIO5Ctx cio5c) {
      super(configUI == null ? new ConfigCoreUiSwingDef(cdc.getUC()) : configUI, cdc);
      this.cdc = cdc;
      this.sc = sc;
      this.cio5c = cio5c;
      keyMap = new KeyMapSwing(uc);

      hostDataUISwing = new HostDataUiSwing(this);
      hostFeatureUiSwing = new HostFeatureUiSwing(this);
      hostServiceUiSwing = new HostServiceUiSwing(this);
      if (this.getClass() == CoreUiSwingCtx.class) {
         a_Init();
      }

      //#debug
      toDLog().pCreate("", this, CoreUiSwingCtx.class, "Created@73", LVL_04_FINER, true);

   }

   protected void applySettings(ByteObject settingsNew, ByteObject settingsOld) {
      super.applySettings(settingsNew, settingsOld);
      //#debug
      toDLog().pFlow("", null, CoreUiSwingCtx.class, "applySettings@85", LVL_04_FINER, true);

      if (settingsNew.hasFlag(IBOCtxSettingsCoreUi.CTX_COREUI_OFFSET_01_FLAG1, IBOCtxSettingsCoreUi.CTX_COREUI_FLAG_2_DRAG_DROP)) {
         CanvasHostAbstract[] canvases2 = getCanvases();
         for (int i = 0; i < canvases2.length; i++) {
            if (canvases2[i] instanceof CanvasHostSwingAbstract) {
               ((CanvasHostSwingAbstract) canvases2[i]).enableFileDrop();
            }
         }
      } else {
         CanvasHostAbstract[] canvases2 = getCanvases();
         for (int i = 0; i < canvases2.length; i++) {
            if (canvases2[i] instanceof CanvasHostSwingAbstract) {
               ((CanvasHostSwingAbstract) canvases2[i]).fileDropDisable();
            }
         }
      }
   }

   public CanvasHostAbstract createCanvasHost(WrapperAbstract wrapper, ByteObject canvasTech) {
      CanvasHostAbstract cha = getWrapperManager().createCanvasHost(wrapper, canvasTech);
      if (cha != null) {
         return cha;
      }
      CanvasHostSwing canvasHost = new CanvasHostSwing(this, canvasTech);
      canvasHost.setWrapper(wrapper);
      return canvasHost;
   }

   public IWrapperManager createCanvasOwnerDefault() {
      return new WrapperManagerDefaultSwing(this);
   }

   public CoreDrawSwingCtx getCoreDrawSwingCtx() {
      return cdc;
   }

   public CoreIO5Ctx getCoreIO5Ctx() {
      return cio5c;
   }

   public int getCtxID() {
      return CTX_ID;
   }

 

   public HostDataUiJ2se getHostDataUIJ2se() {
      return hostDataUISwing;
   }

   public HostDataUiSwing getHostDataUiSwing() {
      return hostDataUISwing;
   }

   public HostFeatureUiJ2se getHostFeatureUiJ2se() {
      return hostFeatureUiSwing;
   }

   public HostServiceUiJ2se getHostServiceUiJ2se() {
      return hostServiceUiSwing;
   }

   public KeyMapAbstract getKeyMap() {
      return keyMap;
   }

   public IStatorFactory getStatorFactory() {
      if (statorFactory == null) {
         statorFactory = new StatorFactoryCoreUiSwing(this);
      }
      return statorFactory;
   }

   public SwingCtx getSwingCtx() {
      return sc;
   }

   protected void matchConfig(IConfigBO config, ByteObject settings) {
      super.matchConfig(config, settings);
   }

   //#mdebug
   public void toString(Dctx dc) {
      dc.root(this, CoreUiSwingCtx.class, 138);
      toStringPrivate(dc);
      super.toString(dc.sup());

      dc.nlLvl(keyMap, "keyMap");
   }

   public void toString1Line(Dctx dc) {
      dc.root1Line(this, CoreUiSwingCtx.class);
      toStringPrivate(dc);
      super.toString1Line(dc.sup1Line());
   }

   private void toStringPrivate(Dctx dc) {

   }

   //#enddebug

}
