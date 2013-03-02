/*     */ package com.sun.javafx.tk.quantum;
/*     */ 
/*     */ import com.sun.glass.ui.Application;
/*     */ import com.sun.glass.ui.MenuBar;
/*     */ import com.sun.glass.ui.Screen;
/*     */ import com.sun.glass.ui.View;
/*     */ import com.sun.glass.ui.Window;
/*     */ import com.sun.javafx.PlatformUtil;
/*     */ import com.sun.javafx.iio.common.PushbroomScaler;
/*     */ import com.sun.javafx.iio.common.ScalerFactory;
/*     */ import com.sun.javafx.tk.FocusCause;
/*     */ import com.sun.javafx.tk.TKScene;
/*     */ import com.sun.javafx.tk.TKStage;
/*     */ import com.sun.javafx.tk.TKStageListener;
/*     */ import com.sun.prism.Image;
/*     */ import com.sun.prism.PixelFormat;
/*     */ import java.io.PrintStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessControlException;
/*     */ import java.security.AccessController;
/*     */ import java.security.AllPermission;
/*     */ import java.security.Permission;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import javafx.stage.Modality;
/*     */ import javafx.stage.StageStyle;
/*     */ 
/*     */ public class WindowStage extends GlassStage
/*     */ {
/*     */   PushbroomScaler scaler;
/*     */   StageStyle style;
/*     */   Window platformWindow;
/*     */   MenuBar menubar;
/*     */   String title;
/*  43 */   int minWidth = 0;
/*  44 */   int minHeight = 0;
/*  45 */   int maxWidth = 2147483647;
/*  46 */   int maxHeight = 2147483647;
/*     */ 
/*  48 */   private OverlayWarning warning = null;
/*  49 */   private boolean transparent = false;
/*  50 */   private boolean isPrimaryStage = false;
/*  51 */   private boolean isAppletStage = false;
/*  52 */   private boolean isInFullScreen = false;
/*     */ 
/*  54 */   private TKStage owner = null;
/*     */ 
/*  56 */   private Window ownerWindow = null;
/*  57 */   private Modality modality = Modality.NONE;
/*     */ 
/*  61 */   private boolean inEventHandler = false;
/*     */ 
/*  63 */   private static final QuantumRenderer renderer = QuantumRenderer.getInstance();
/*     */ 
/*  65 */   private static WindowStage activeFullScreenWindow = null;
/*     */ 
/*  67 */   private static GlassAppletWindow appletWindow = null;
/*     */ 
/* 462 */   private boolean trustedFullScreen = false;
/*     */ 
/* 495 */   private static final Permission fullScreenPermission = new AllPermission();
/*     */ 
/* 497 */   private boolean fullScreenFromUserEvent = false;
/*     */ 
/* 598 */   private boolean platformWindowClosed = false;
/*     */ 
/*     */   static void setAppletWindow(GlassAppletWindow paramGlassAppletWindow)
/*     */   {
/*  69 */     appletWindow = paramGlassAppletWindow;
/*     */   }
/*     */   static GlassAppletWindow getAppletWindow() {
/*  72 */     return appletWindow;
/*     */   }
/*     */ 
/*     */   public WindowStage(boolean paramBoolean) {
/*  76 */     this(paramBoolean, StageStyle.DECORATED, false, Modality.NONE, null);
/*     */   }
/*     */ 
/*     */   public WindowStage(boolean paramBoolean, StageStyle paramStageStyle) {
/*  80 */     this(paramBoolean, paramStageStyle, false, Modality.NONE, null);
/*     */   }
/*     */ 
/*     */   public final WindowStage init(GlassSystemMenu paramGlassSystemMenu)
/*     */   {
/*  85 */     initPlatformWindow();
/*  86 */     this.platformWindow.setEventHandler(new GlassWindowEventHandler(this));
/*  87 */     this.platformWindow.setMinimumSize(this.minWidth, this.minHeight);
/*  88 */     this.platformWindow.setMaximumSize(this.maxWidth, this.maxHeight);
/*  89 */     if (paramGlassSystemMenu.isSupported()) {
/*  90 */       paramGlassSystemMenu.createMenuBar();
/*  91 */       this.platformWindow.setMenuBar(paramGlassSystemMenu.getMenuBar());
/*     */     }
/*  93 */     return this;
/*     */   }
/*     */ 
/*     */   public WindowStage(boolean paramBoolean1, StageStyle paramStageStyle, boolean paramBoolean2, Modality paramModality, TKStage paramTKStage)
/*     */   {
/*  98 */     super(paramBoolean1);
/*     */ 
/* 101 */     this.transparent = (paramStageStyle == StageStyle.TRANSPARENT);
/*     */ 
/* 103 */     this.style = paramStageStyle;
/* 104 */     this.isPrimaryStage = paramBoolean2;
/* 105 */     if ((null != appletWindow) && (paramBoolean2))
/*     */     {
/* 107 */       this.isAppletStage = true;
/*     */     }
/* 109 */     if (paramTKStage == null) {
/* 110 */       if (paramModality == Modality.WINDOW_MODAL) {
/* 111 */         paramModality = Modality.NONE;
/*     */       }
/*     */     }
/* 114 */     else if ((paramTKStage instanceof WindowStage)) {
/* 115 */       this.ownerWindow = ((WindowStage)paramTKStage).platformWindow;
/*     */     }
/*     */     else {
/* 118 */       System.err.println("Error: Unsupported type of owner " + paramTKStage);
/*     */     }
/*     */ 
/* 121 */     this.owner = paramTKStage;
/* 122 */     this.modality = paramModality;
/*     */   }
/*     */ 
/*     */   protected void initPlatformWindow() {
/* 126 */     int i = 0;
/*     */ 
/* 128 */     Application localApplication = Application.GetApplication();
/* 129 */     if ((this.isPrimaryStage) && (null != appletWindow)) {
/* 130 */       this.platformWindow = localApplication.createWindow(appletWindow.getGlassWindow().getNativeWindow());
/* 131 */     } else if (this.style == StageStyle.DECORATED) {
/* 132 */       i = 113;
/*     */ 
/* 134 */       this.platformWindow = localApplication.createWindow(this.ownerWindow, Screen.getMainScreen(), i);
/*     */ 
/* 136 */       this.platformWindow.setResizable(true);
/* 137 */     } else if (this.style == StageStyle.UTILITY) {
/* 138 */       i = 21;
/* 139 */       this.platformWindow = localApplication.createWindow(this.ownerWindow, Screen.getMainScreen(), i);
/*     */     }
/*     */     else {
/* 142 */       i = (this.transparent ? 2 : 0) | 0x10;
/*     */ 
/* 144 */       this.platformWindow = localApplication.createWindow(this.ownerWindow, Screen.getMainScreen(), i);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected Window getPlatformWindow()
/*     */   {
/* 150 */     return this.platformWindow;
/*     */   }
/*     */ 
/*     */   protected TKStage getOwner() {
/* 154 */     return this.owner;
/*     */   }
/*     */ 
/*     */   protected ViewScene getViewScene() {
/* 158 */     return (ViewScene)this.scene;
/*     */   }
/*     */ 
/*     */   StageStyle getStyle() {
/* 162 */     return this.style;
/*     */   }
/*     */ 
/*     */   public TKScene createTKScene(boolean paramBoolean) {
/* 166 */     return new ViewScene(this.verbose, paramBoolean);
/*     */   }
/*     */ 
/*     */   public void setScene(TKScene paramTKScene)
/*     */   {
/* 175 */     GlassScene localGlassScene = this.scene;
/* 176 */     super.setScene(paramTKScene);
/*     */     Object localObject;
/* 177 */     if (this.scene != null) {
/* 178 */       localObject = getViewScene().getPlatformView();
/* 179 */       this.platformWindow.setView((View)localObject);
/* 180 */       requestFocus();
/* 181 */       applyFullScreen();
/*     */     } else {
/* 183 */       this.platformWindow.setView(null);
/*     */     }
/* 185 */     if (localGlassScene != null) {
/* 186 */       localObject = ((ViewScene)localGlassScene).getPen();
/* 187 */       ViewPainter localViewPainter = ((PrismPen)localObject).getPainter();
/*     */ 
/* 189 */       renderer.disposePresentable(localViewPainter.presentable);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setBounds(float paramFloat1, float paramFloat2, boolean paramBoolean1, boolean paramBoolean2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8)
/*     */   {
/* 199 */     if ((this.isAppletStage) || (Float.isNaN(paramFloat1)) || (Float.isNaN(paramFloat2))) {
/* 200 */       paramBoolean1 = paramBoolean2 = 0;
/*     */     }
/* 202 */     this.platformWindow.setBounds((int)paramFloat1, (int)paramFloat2, paramBoolean1, paramBoolean2, (int)paramFloat3, (int)paramFloat4, (int)paramFloat5, (int)paramFloat6, paramFloat7, paramFloat8);
/*     */   }
/*     */ 
/*     */   public void setMinimumSize(int paramInt1, int paramInt2) {
/* 206 */     if ((this.minWidth != paramInt1) || (this.minHeight != paramInt2)) {
/* 207 */       this.minWidth = paramInt1;
/* 208 */       this.minHeight = paramInt2;
/* 209 */       if (this.platformWindow != null)
/* 210 */         this.platformWindow.setMinimumSize(paramInt1, paramInt2);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setMaximumSize(int paramInt1, int paramInt2)
/*     */   {
/* 216 */     if ((this.maxWidth != paramInt1) || (this.maxHeight != paramInt2)) {
/* 217 */       this.maxWidth = paramInt1;
/* 218 */       this.maxHeight = paramInt2;
/* 219 */       if (this.platformWindow != null)
/* 220 */         this.platformWindow.setMaximumSize(paramInt1, paramInt2);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setIcons(List paramList)
/*     */   {
/* 227 */     int i = 32;
/* 228 */     int j = 32;
/* 229 */     if (PlatformUtil.isMac()) {
/* 230 */       i = 128;
/* 231 */       j = 128;
/* 232 */     } else if (PlatformUtil.isWindows()) {
/* 233 */       i = 32;
/* 234 */       j = 32;
/* 235 */     } else if (PlatformUtil.isLinux()) {
/* 236 */       i = 128;
/* 237 */       j = 128;
/*     */     }
/*     */ 
/* 240 */     if ((paramList == null) || (paramList.size() < 1)) {
/* 241 */       return;
/*     */     }
/*     */ 
/* 244 */     int k = this.platformWindow.getWidth();
/* 245 */     int m = this.platformWindow.getHeight();
/*     */ 
/* 247 */     Object localObject1 = null;
/* 248 */     double d1 = 3.0D;
/* 249 */     for (int n = 0; n < paramList.size(); n++)
/*     */     {
/* 256 */       localObject2 = (Image)paramList.get(n);
/* 257 */       if ((localObject2 != null) && ((((Image)localObject2).getPixelFormat() == PixelFormat.BYTE_RGB) || (((Image)localObject2).getPixelFormat() == PixelFormat.BYTE_BGRA_PRE) || (((Image)localObject2).getPixelFormat() == PixelFormat.BYTE_GRAY)))
/*     */       {
/* 264 */         i1 = ((Image)localObject2).getWidth();
/* 265 */         i2 = ((Image)localObject2).getHeight();
/*     */ 
/* 267 */         if ((i1 > 0) && (i2 > 0))
/*     */         {
/* 269 */           double d2 = Math.min(j / i1, i / i2);
/*     */ 
/* 275 */           double d3 = 1.0D;
/*     */           int i3;
/*     */           int i4;
/* 276 */           if (d2 >= 2.0D)
/*     */           {
/* 279 */             d2 = Math.floor(d2);
/* 280 */             i3 = i1 * (int)d2;
/* 281 */             i4 = i2 * (int)d2;
/* 282 */             d3 = 1.0D - 0.5D / d2;
/* 283 */           } else if (d2 >= 1.0D)
/*     */           {
/* 285 */             d2 = 1.0D;
/* 286 */             i3 = i1;
/* 287 */             i4 = i2;
/* 288 */             d3 = 0.0D;
/* 289 */           } else if (d2 >= 0.75D)
/*     */           {
/* 291 */             d2 = 0.75D;
/* 292 */             i3 = i1 * 3 / 4;
/* 293 */             i4 = i2 * 3 / 4;
/* 294 */             d3 = 0.3D;
/* 295 */           } else if (d2 >= 0.6666D)
/*     */           {
/* 297 */             d2 = 0.6666D;
/* 298 */             i3 = i1 * 2 / 3;
/* 299 */             i4 = i2 * 2 / 3;
/* 300 */             d3 = 0.33D;
/*     */           }
/*     */           else
/*     */           {
/* 305 */             d4 = Math.ceil(1.0D / d2);
/* 306 */             d2 = 1.0D / d4;
/* 307 */             i3 = (int)Math.round(i1 / d4);
/* 308 */             i4 = (int)Math.round(i2 / d4);
/* 309 */             d3 = 1.0D - 1.0D / d4;
/*     */           }
/* 311 */           double d4 = (k - i3) / k + (m - i4) / m + d3;
/*     */ 
/* 314 */           if (d4 < d1) {
/* 315 */             localObject1 = localObject2;
/*     */           }
/* 317 */           if (d4 == 0.0D) break;
/*     */         }
/*     */       }
/*     */     }
/* 321 */     if (localObject1 == null)
/*     */     {
/* 323 */       return;
/*     */     }
/*     */ 
/* 326 */     this.scaler = ScalerFactory.createScaler(localObject1.getWidth(), localObject1.getHeight(), localObject1.getBytesPerPixelUnit(), j, i, true);
/*     */ 
/* 331 */     ByteBuffer localByteBuffer = (ByteBuffer)localObject1.getPixelBuffer();
/* 332 */     Object localObject2 = new byte[localByteBuffer.limit()];
/*     */ 
/* 334 */     int i1 = localObject1.getHeight();
/*     */ 
/* 338 */     for (int i2 = 0; i2 < i1; i2++) {
/* 339 */       localByteBuffer.position(i2 * localObject1.getScanlineStride());
/* 340 */       localByteBuffer.get((byte[])localObject2, 0, localObject1.getScanlineStride());
/* 341 */       if (this.scaler != null) {
/* 342 */         this.scaler.putSourceScanline((byte[])localObject2, 0);
/*     */       }
/*     */     }
/*     */ 
/* 346 */     localByteBuffer.rewind();
/*     */ 
/* 348 */     Image localImage = localObject1.iconify(this.scaler.getDestination(), j, i);
/* 349 */     this.platformWindow.setIcon(PixelUtils.imageToPixels(localImage));
/*     */   }
/*     */ 
/*     */   public void setTitle(String paramString) {
/* 353 */     if (this.platformWindow == null)
/* 354 */       this.title = paramString;
/*     */     else
/* 356 */       this.platformWindow.setTitle(paramString);
/*     */   }
/*     */ 
/*     */   public void setVisible(boolean paramBoolean)
/*     */   {
/* 361 */     if (this.platformWindow.supportsPlatformModality())
/*     */     {
/* 363 */       if (paramBoolean) {
/* 364 */         if (this.modality == Modality.WINDOW_MODAL) {
/* 365 */           assert (this.owner != null);
/* 366 */           ((WindowStage)this.owner).setEnabled(false);
/* 367 */           this.platformWindow.enterModal(this.ownerWindow);
/* 368 */         } else if (this.modality == Modality.APPLICATION_MODAL) {
/* 369 */           windowsSetEnabled(false);
/* 370 */           this.platformWindow.enterModal();
/*     */         }
/*     */       }
/* 373 */       else if (this.modality == Modality.WINDOW_MODAL) {
/* 374 */         ((WindowStage)this.owner).setEnabled(true);
/* 375 */         this.platformWindow.exitModal();
/* 376 */       } else if (this.modality == Modality.APPLICATION_MODAL) {
/* 377 */         windowsSetEnabled(true);
/* 378 */         this.platformWindow.exitModal();
/*     */       }
/* 380 */       else if (this.owner != null) {
/* 381 */         ((WindowStage)this.owner).requestToFront();
/*     */       }
/*     */ 
/*     */       try
/*     */       {
/* 386 */         AbstractPainter.renderLock.lock();
/* 387 */         this.platformWindow.setVisible(paramBoolean);
/* 388 */         super.setVisible(paramBoolean);
/*     */       } finally {
/* 390 */         AbstractPainter.renderLock.unlock();
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 395 */       if (!paramBoolean) {
/* 396 */         if (this.modality == Modality.WINDOW_MODAL) {
/* 397 */           assert (this.owner != null);
/* 398 */           ((WindowStage)this.owner).setEnabled(true);
/* 399 */         } else if (this.modality == Modality.APPLICATION_MODAL) {
/* 400 */           windowsSetEnabled(true);
/*     */         }
/* 404 */         else if (this.owner != null) {
/* 405 */           WindowStage localWindowStage = (WindowStage)this.owner;
/* 406 */           localWindowStage.requestToFront();
/*     */         }
/*     */       }
/*     */       try
/*     */       {
/* 411 */         AbstractPainter.renderLock.lock();
/* 412 */         this.platformWindow.setVisible(paramBoolean);
/* 413 */         super.setVisible(paramBoolean);
/*     */       } finally {
/* 415 */         AbstractPainter.renderLock.unlock();
/*     */       }
/*     */ 
/* 419 */       if (paramBoolean) {
/* 420 */         if (this.modality == Modality.WINDOW_MODAL) {
/* 421 */           assert (this.owner != null);
/* 422 */           ((WindowStage)this.owner).setEnabled(false);
/* 423 */         } else if (this.modality == Modality.APPLICATION_MODAL) {
/* 424 */           windowsSetEnabled(false);
/*     */         }
/* 426 */         if ((this.isAppletStage) && (null != appletWindow)) {
/* 427 */           appletWindow.assertStageOrder();
/*     */         }
/*     */       }
/*     */     }
/* 431 */     applyFullScreen();
/*     */   }
/*     */ 
/*     */   boolean isVisible() {
/* 435 */     return this.platformWindow.isVisible();
/*     */   }
/*     */ 
/*     */   public void setOpacity(float paramFloat) {
/* 439 */     this.platformWindow.setAlpha(paramFloat);
/*     */   }
/*     */ 
/*     */   public boolean needsUpdateWindow() {
/* 443 */     return (this.transparent) && (Application.GetApplication().shouldUpdateWindow());
/*     */   }
/*     */ 
/*     */   public void setIconified(boolean paramBoolean) {
/* 447 */     if (((this.platformWindow.isMinimized()) && (paramBoolean)) || ((!this.platformWindow.isMinimized()) && (!paramBoolean)))
/*     */     {
/* 450 */       return;
/*     */     }
/* 452 */     if (this.platformWindow.minimize(paramBoolean) != paramBoolean)
/* 453 */       throw new RuntimeException("WindowStage.setIconified failed");
/*     */   }
/*     */ 
/*     */   public void setResizable(boolean paramBoolean)
/*     */   {
/* 458 */     this.platformWindow.setResizable(paramBoolean);
/*     */   }
/*     */ 
/*     */   private void initTrustedFullScreen()
/*     */   {
/* 466 */     this.trustedFullScreen = hasPermission(fullScreenPermission);
/*     */   }
/*     */ 
/*     */   boolean isTrustedFullScreen()
/*     */   {
/* 473 */     return this.trustedFullScreen;
/*     */   }
/*     */ 
/*     */   void exitFullScreen()
/*     */   {
/* 478 */     setFullScreen(false);
/*     */   }
/*     */ 
/*     */   private boolean hasPermission(Permission paramPermission) {
/*     */     try {
/* 483 */       if (System.getSecurityManager() != null) {
/* 484 */         getAccessControlContext().checkPermission(paramPermission);
/*     */       }
/* 486 */       return true; } catch (AccessControlException localAccessControlException) {
/*     */     }
/* 488 */     return false;
/*     */   }
/*     */ 
/*     */   private void applyFullScreen()
/*     */   {
/* 500 */     View localView = this.platformWindow.getView();
/* 501 */     if ((isVisible()) && (localView != null) && (localView.isInFullscreen() != this.isInFullScreen)) {
/* 502 */       if (this.isInFullScreen) {
/* 503 */         initTrustedFullScreen();
/*     */ 
/* 509 */         if ((!this.trustedFullScreen) && (!this.fullScreenFromUserEvent)) {
/* 510 */           exitFullScreen();
/*     */         } else {
/* 512 */           localView.enterFullscreen(false, false, false);
/* 513 */           if ((this.warning != null) && (this.warning.inWarningTransition())) {
/* 514 */             this.warning.setView(getViewScene());
/* 515 */           } else if (this.warning == null) {
/* 516 */             this.warning = new OverlayWarning(getViewScene());
/* 517 */             this.warning.warn();
/*     */           }
/*     */         }
/*     */       } else {
/* 521 */         if (this.warning != null) {
/* 522 */           this.warning.cancel();
/* 523 */           this.warning = null;
/*     */         }
/* 525 */         localView.exitFullscreen(false);
/*     */       }
/*     */ 
/* 528 */       this.fullScreenFromUserEvent = false;
/* 529 */     } else if ((!isVisible()) && (this.warning != null))
/*     */     {
/* 531 */       this.warning.cancel();
/* 532 */       this.warning = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setFullScreen(boolean paramBoolean) {
/* 537 */     if (this.isInFullScreen == paramBoolean) {
/* 538 */       return;
/*     */     }
/*     */ 
/* 543 */     if (isInEventHandler()) {
/* 544 */       this.fullScreenFromUserEvent = true;
/*     */     }
/*     */ 
/* 547 */     if ((paramBoolean) && (activeFullScreenWindow != null)) {
/* 548 */       activeFullScreenWindow.setFullScreen(false);
/*     */     }
/* 550 */     this.isInFullScreen = paramBoolean;
/* 551 */     applyFullScreen();
/* 552 */     if (paramBoolean)
/* 553 */       activeFullScreenWindow = this;
/*     */   }
/*     */ 
/*     */   void fullscreenChanged(final boolean paramBoolean)
/*     */   {
/* 558 */     if (paramBoolean) {
/* 559 */       initTrustedFullScreen();
/*     */     }
/* 561 */     if ((!paramBoolean) && (this == activeFullScreenWindow)) {
/* 562 */       activeFullScreenWindow = null;
/*     */     }
/* 564 */     AccessController.doPrivileged(new PrivilegedAction()
/*     */     {
/*     */       public Void run() {
/* 567 */         if (WindowStage.this.stageListener != null) {
/* 568 */           WindowStage.this.stageListener.changedFullscreen(paramBoolean);
/*     */         }
/* 570 */         return null;
/*     */       }
/*     */     }
/*     */     , getAccessControlContext());
/*     */   }
/*     */ 
/*     */   public void toBack()
/*     */   {
/* 576 */     this.platformWindow.toBack();
/* 577 */     if ((this.isAppletStage) && (null != appletWindow))
/* 578 */       appletWindow.assertStageOrder();
/*     */   }
/*     */ 
/*     */   public void toFront()
/*     */   {
/* 583 */     this.platformWindow.requestFocus();
/* 584 */     this.platformWindow.toFront();
/* 585 */     if ((this.isAppletStage) && (null != appletWindow))
/* 586 */       appletWindow.assertStageOrder();
/*     */   }
/*     */ 
/*     */   public void close()
/*     */   {
/* 591 */     super.close();
/* 592 */     if (!this.platformWindowClosed)
/*     */     {
/* 594 */       this.platformWindow.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void setPlatformWindowClosed()
/*     */   {
/* 603 */     this.platformWindowClosed = true;
/*     */   }
/*     */ 
/*     */   public boolean isTopLevel()
/*     */   {
/* 608 */     return this.owner == null;
/*     */   }
/*     */ 
/*     */   public boolean grabFocus() {
/* 612 */     return this.platformWindow.grabFocus();
/*     */   }
/*     */ 
/*     */   public void ungrabFocus() {
/* 616 */     this.platformWindow.ungrabFocus();
/*     */   }
/*     */ 
/*     */   public void requestFocus(FocusCause paramFocusCause) {
/* 620 */     switch (2.$SwitchMap$com$sun$javafx$tk$FocusCause[paramFocusCause.ordinal()]) {
/*     */     case 1:
/* 622 */       this.platformWindow.requestFocus(543);
/* 623 */       break;
/*     */     case 2:
/* 625 */       this.platformWindow.requestFocus(544);
/* 626 */       break;
/*     */     case 3:
/* 628 */       this.platformWindow.requestFocus(542);
/* 629 */       break;
/*     */     case 4:
/* 631 */       this.platformWindow.requestFocus(541);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void setPlatformEnabled(boolean paramBoolean)
/*     */   {
/* 637 */     if (!this.platformWindowClosed) {
/* 638 */       this.platformWindow.setEnabled(paramBoolean);
/*     */     }
/* 640 */     if (!paramBoolean)
/* 641 */       GlassStage.removeActiveWindow(this);
/*     */   }
/*     */ 
/*     */   void setEnabled(boolean paramBoolean)
/*     */   {
/* 646 */     if ((this.owner != null) && ((this.owner instanceof WindowStage))) {
/* 647 */       ((WindowStage)this.owner).setEnabled(paramBoolean);
/*     */     }
/*     */ 
/* 654 */     if ((paramBoolean) && (this.platformWindow.getNativeWindow() == 0L)) {
/* 655 */       return;
/*     */     }
/* 657 */     setPlatformEnabled(paramBoolean);
/* 658 */     if (paramBoolean) {
/* 659 */       requestToFront();
/* 660 */       if ((this.isAppletStage) && (null != appletWindow))
/* 661 */         appletWindow.assertStageOrder();
/*     */     }
/*     */   }
/*     */ 
/*     */   void windowsSetEnabled(boolean paramBoolean)
/*     */   {
/* 671 */     for (GlassStage localGlassStage : windows)
/* 672 */       if (localGlassStage != this) {
/* 673 */         localGlassStage.setPlatformEnabled(paramBoolean);
/* 674 */         if (paramBoolean)
/* 675 */           localGlassStage.requestToFront();
/*     */       }
/*     */   }
/*     */ 
/*     */   protected void requestToFront()
/*     */   {
/* 684 */     this.platformWindow.toFront();
/* 685 */     this.platformWindow.requestFocus();
/*     */   }
/*     */ 
/*     */   public void setInEventHandler(boolean paramBoolean) {
/* 689 */     this.inEventHandler = paramBoolean;
/*     */   }
/*     */ 
/*     */   public boolean isInEventHandler() {
/* 693 */     return this.inEventHandler;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.quantum.WindowStage
 * JD-Core Version:    0.6.2
 */