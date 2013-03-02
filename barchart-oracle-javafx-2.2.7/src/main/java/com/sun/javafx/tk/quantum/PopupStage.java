/*     */ package com.sun.javafx.tk.quantum;
/*     */ 
/*     */ import com.sun.glass.ui.Application;
/*     */ import com.sun.glass.ui.Screen;
/*     */ import com.sun.glass.ui.Window;
/*     */ import com.sun.javafx.tk.TKScene;
/*     */ import javafx.stage.StageStyle;
/*     */ 
/*     */ public class PopupStage extends WindowStage
/*     */ {
/*     */   private GlassStage ownerStage;
/*     */ 
/*     */   public PopupStage(boolean paramBoolean, Object paramObject)
/*     */   {
/*  39 */     super(paramBoolean, StageStyle.TRANSPARENT);
/*  40 */     assert ((paramObject instanceof GlassStage));
/*  41 */     this.ownerStage = ((GlassStage)paramObject);
/*     */   }
/*     */ 
/*     */   protected void initPlatformWindow()
/*     */   {
/*  46 */     Application localApplication = Application.GetApplication();
/*  47 */     Window localWindow = (this.ownerStage instanceof WindowStage) ? ((WindowStage)this.ownerStage).getPlatformWindow() : null;
/*     */ 
/*  50 */     this.platformWindow = localApplication.createWindow(localWindow, Screen.getMainScreen(), 10);
/*     */ 
/*  52 */     this.platformWindow.setFocusable(false);
/*     */   }
/*     */ 
/*     */   public GlassScene getOwnerScene() {
/*  56 */     return this.ownerStage.scene;
/*     */   }
/*     */ 
/*     */   public TKScene createTKScene(boolean paramBoolean)
/*     */   {
/*  61 */     PopupScene localPopupScene = new PopupScene(this.verbose, paramBoolean);
/*  62 */     localPopupScene.setGlassStage(this);
/*     */ 
/*  64 */     return localPopupScene;
/*     */   }
/*     */ 
/*     */   public void setResizable(boolean paramBoolean) {
/*     */   }
/*     */ 
/*     */   public void setTitle(String paramString) {
/*     */   }
/*     */ 
/*     */   public void sizeToScene() {
/*     */   }
/*     */ 
/*     */   public void centerOnScreen() {
/*     */   }
/*     */ 
/*     */   public void close() {
/*  80 */     super.close();
/*     */   }
/*     */ 
/*     */   public void setBounds(float paramFloat1, float paramFloat2, boolean paramBoolean1, boolean paramBoolean2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8)
/*     */   {
/*  90 */     super.setBounds(paramFloat1, paramFloat2, paramBoolean1, paramBoolean2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8);
/*     */   }
/*     */ 
/*     */   public void setVisible(boolean paramBoolean) {
/*  94 */     if (paramBoolean) {
/*  95 */       this.ownerStage.addPopup(this);
/*     */     }
/*     */     else
/*     */     {
/* 102 */       this.ownerStage.removePopup(this);
/*     */     }
/*     */ 
/* 108 */     super.setVisible(paramBoolean);
/*     */   }
/*     */ 
/*     */   public boolean isTopLevel() {
/* 112 */     return false;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.quantum.PopupStage
 * JD-Core Version:    0.6.2
 */