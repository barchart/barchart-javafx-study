/*     */ package com.sun.javafx.tk.quantum;
/*     */ 
/*     */ import com.sun.javafx.tk.FocusCause;
/*     */ import com.sun.javafx.tk.TKScene;
/*     */ import com.sun.javafx.tk.TKStage;
/*     */ import com.sun.javafx.tk.TKStageListener;
/*     */ import com.sun.javafx.tk.Toolkit;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ 
/*     */ public abstract class GlassStage
/*     */   implements TKStage
/*     */ {
/*  26 */   protected static List<GlassStage> windows = new ArrayList();
/*     */ 
/*  29 */   private static List<TKStage> topLevelWindows = new ArrayList();
/*     */ 
/*  31 */   private List<PopupStage> popups = new LinkedList();
/*     */ 
/*  37 */   private static List<GlassStage> activeWindows = new LinkedList();
/*     */ 
/*  40 */   private static boolean firstWindowVisible = false;
/*     */   protected boolean verbose;
/*     */   protected GlassScene scene;
/*     */   protected TKStageListener stageListener;
/*     */   private boolean visible;
/*  50 */   private boolean important = true;
/*     */ 
/*  52 */   private AccessControlContext accessCtrlCtx = null;
/*     */ 
/*     */   protected GlassStage(boolean paramBoolean) {
/*  55 */     this.verbose = paramBoolean;
/*  56 */     windows.add(this);
/*     */   }
/*     */ 
/*     */   public void setTKStageListener(TKStageListener paramTKStageListener)
/*     */   {
/*  65 */     this.stageListener = paramTKStageListener;
/*     */   }
/*     */ 
/*     */   public void setScene(TKScene paramTKScene) {
/*  69 */     if (this.scene != null) {
/*  70 */       this.scene.setGlassStage(null);
/*     */     }
/*  72 */     this.scene = ((GlassScene)paramTKScene);
/*  73 */     if (this.scene != null)
/*  74 */       this.scene.setGlassStage(this);
/*     */   }
/*     */ 
/*     */   protected void setPlatformWindowClosed()
/*     */   {
/*     */   }
/*     */ 
/*     */   final AccessControlContext getAccessControlContext()
/*     */   {
/*  83 */     if (this.accessCtrlCtx == null) {
/*  84 */       throw new RuntimeException("Stage security context has not been set!");
/*     */     }
/*  86 */     return this.accessCtrlCtx;
/*     */   }
/*     */ 
/*     */   public final void setSecurityContext(AccessControlContext paramAccessControlContext) {
/*  90 */     if (this.accessCtrlCtx != null) {
/*  91 */       throw new RuntimeException("Stage security context has been already set!");
/*     */     }
/*  93 */     this.accessCtrlCtx = paramAccessControlContext;
/*     */   }
/*     */ 
/*     */   public void requestFocus()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void requestFocus(FocusCause paramFocusCause) {
/*     */   }
/*     */ 
/*     */   static void addActiveWindow(GlassStage paramGlassStage) {
/* 104 */     activeWindows.remove(paramGlassStage);
/* 105 */     activeWindows.add(paramGlassStage);
/*     */   }
/*     */ 
/*     */   static void removeActiveWindow(GlassStage paramGlassStage) {
/* 109 */     activeWindows.remove(paramGlassStage);
/*     */   }
/*     */ 
/*     */   final void handleFocusDisabled() {
/* 113 */     if (activeWindows.isEmpty()) {
/* 114 */       return;
/*     */     }
/* 116 */     GlassStage localGlassStage = (GlassStage)activeWindows.get(activeWindows.size() - 1);
/*     */ 
/* 118 */     localGlassStage.setIconified(false);
/* 119 */     localGlassStage.requestToFront();
/* 120 */     localGlassStage.requestFocus();
/*     */   }
/*     */ 
/*     */   public void setVisible(boolean paramBoolean)
/*     */   {
/* 129 */     int i = (isImportant()) && (isTopLevel()) ? 1 : 0;
/* 130 */     int j = this.visible != paramBoolean ? 1 : 0;
/*     */ 
/* 132 */     this.visible = paramBoolean;
/* 133 */     if (paramBoolean) {
/* 134 */       firstWindowVisible = true;
/* 135 */       if ((j != 0) && (i != 0)) {
/* 136 */         topLevelWindows.add(this);
/* 137 */         notifyWindowListeners();
/*     */       }
/*     */     }
/* 140 */     if (!paramBoolean) {
/* 141 */       removeActiveWindow(this);
/* 142 */       if ((j != 0) && (i != 0)) {
/* 143 */         topLevelWindows.remove(this);
/* 144 */         notifyWindowListeners();
/*     */       }
/*     */     }
/* 147 */     if (this.scene != null)
/* 148 */       this.scene.stageVisible(paramBoolean);
/*     */   }
/*     */ 
/*     */   boolean isVisible()
/*     */   {
/* 153 */     return this.visible;
/*     */   }
/*     */ 
/*     */   protected abstract void setPlatformEnabled(boolean paramBoolean);
/*     */ 
/*     */   protected abstract void requestToFront();
/*     */ 
/*     */   public void close()
/*     */   {
/* 162 */     windows.remove(this);
/* 163 */     topLevelWindows.remove(this);
/* 164 */     notifyWindowListeners();
/*     */   }
/*     */ 
/*     */   static boolean windowsAreOpen() {
/* 168 */     for (GlassStage localGlassStage : windows) {
/* 169 */       if (localGlassStage.isVisible()) {
/* 170 */         return true;
/*     */       }
/*     */     }
/* 173 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean isTopLevel()
/*     */   {
/* 178 */     return true;
/*     */   }
/*     */ 
/*     */   public void setImportant(boolean paramBoolean) {
/* 182 */     this.important = paramBoolean;
/*     */   }
/*     */ 
/*     */   public boolean isImportant() {
/* 186 */     return this.important;
/*     */   }
/*     */ 
/*     */   private static void notifyWindowListeners() {
/* 190 */     Toolkit.getToolkit().notifyWindowListeners(topLevelWindows);
/*     */   }
/*     */ 
/*     */   protected void addPopup(PopupStage paramPopupStage) {
/* 194 */     this.popups.add(paramPopupStage);
/*     */   }
/*     */ 
/*     */   protected void removePopup(PopupStage paramPopupStage) {
/* 198 */     this.popups.remove(paramPopupStage);
/*     */   }
/*     */ 
/*     */   static void requestClosingAllWindows()
/*     */   {
/* 203 */     for (GlassStage localGlassStage : (GlassStage[])windows.toArray(new GlassStage[windows.size()]))
/* 204 */       if ((localGlassStage.isVisible()) && (localGlassStage.stageListener != null))
/* 205 */         AccessController.doPrivileged(new PrivilegedAction()
/*     */         {
/*     */           public Void run() {
/* 208 */             this.val$window.stageListener.closing();
/* 209 */             return null;
/*     */           }
/*     */         }
/*     */         , localGlassStage.getAccessControlContext());
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.quantum.GlassStage
 * JD-Core Version:    0.6.2
 */