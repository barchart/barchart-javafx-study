/*     */ package com.sun.javafx.tk.quantum;
/*     */ 
/*     */ import com.sun.javafx.embed.AbstractEvents;
/*     */ import com.sun.javafx.embed.EmbeddedStageInterface;
/*     */ import com.sun.javafx.embed.HostInterface;
/*     */ import com.sun.javafx.tk.TKScene;
/*     */ import com.sun.javafx.tk.TKStageListener;
/*     */ import com.sun.javafx.tk.Toolkit;
/*     */ import com.sun.prism.impl.PrismSettings;
/*     */ import java.io.PrintStream;
/*     */ import java.security.AccessControlContext;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.List;
/*     */ import javafx.application.Platform;
/*     */ 
/*     */ public class EmbeddedStage extends GlassStage
/*     */   implements EmbeddedStageInterface
/*     */ {
/*     */   private HostInterface host;
/*  30 */   private boolean visible = false;
/*     */ 
/*     */   public EmbeddedStage(HostInterface paramHostInterface) {
/*  33 */     super(PrismSettings.verbose);
/*     */ 
/*  35 */     this.host = paramHostInterface;
/*     */   }
/*     */ 
/*     */   public TKScene createTKScene(boolean paramBoolean)
/*     */   {
/*  42 */     return new EmbeddedScene(this.host, paramBoolean);
/*     */   }
/*     */ 
/*     */   public void setScene(TKScene paramTKScene)
/*     */   {
/*  47 */     if ((paramTKScene != null) && 
/*  48 */       (!$assertionsDisabled) && (!(paramTKScene instanceof EmbeddedScene))) throw new AssertionError();
/*     */ 
/*  50 */     super.setScene(paramTKScene);
/*     */   }
/*     */ 
/*     */   public void setTKStageListener(TKStageListener paramTKStageListener)
/*     */   {
/*  55 */     this.stageListener = paramTKStageListener;
/*     */   }
/*     */ 
/*     */   public void setBounds(float paramFloat1, float paramFloat2, boolean paramBoolean1, boolean paramBoolean2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8)
/*     */   {
/*  63 */     if (this.verbose) {
/*  64 */       System.err.println("EmbeddedStage.setBounds: x=" + paramFloat1 + " y=" + paramFloat2 + " xSet=" + paramBoolean1 + " ySet=" + paramBoolean2 + " w=" + paramFloat3 + " h=" + " cw=" + paramFloat5 + " ch=" + paramFloat6);
/*     */     }
/*     */ 
/*  67 */     float f1 = paramFloat3 > 0.0F ? paramFloat3 : paramFloat5;
/*  68 */     float f2 = paramFloat4 > 0.0F ? paramFloat4 : paramFloat6;
/*  69 */     if ((f1 > 0.0F) && (f2 > 0.0F))
/*  70 */       this.host.setPreferredSize((int)f1, (int)f2);
/*     */   }
/*     */ 
/*     */   public void setMinimumSize(int paramInt1, int paramInt2)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void setMaximumSize(int paramInt1, int paramInt2)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void setPlatformEnabled(boolean paramBoolean)
/*     */   {
/*  84 */     this.host.setEnabled(paramBoolean);
/*     */   }
/*     */ 
/*     */   protected void requestToFront()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void setIcons(List paramList) {
/*  92 */     if (this.verbose)
/*  93 */       System.err.println("EmbeddedStage.setIcons");
/*     */   }
/*     */ 
/*     */   public void setTitle(String paramString)
/*     */   {
/*  99 */     if (this.verbose)
/* 100 */       System.err.println("EmbeddedStage.setTitle " + paramString);
/*     */   }
/*     */ 
/*     */   public void setVisible(boolean paramBoolean)
/*     */   {
/* 106 */     this.visible = paramBoolean;
/* 107 */     this.host.setEmbeddedStage(paramBoolean ? this : null);
/* 108 */     super.setVisible(paramBoolean);
/*     */   }
/*     */ 
/*     */   public void setOpacity(float paramFloat)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void setIconified(boolean paramBoolean)
/*     */   {
/* 118 */     if (this.verbose)
/* 119 */       System.err.println("EmbeddedScene.setIconified " + paramBoolean);
/*     */   }
/*     */ 
/*     */   public void setResizable(boolean paramBoolean)
/*     */   {
/* 125 */     if (this.verbose)
/* 126 */       System.err.println("EmbeddedStage.setResizable " + paramBoolean);
/*     */   }
/*     */ 
/*     */   public void setFullScreen(boolean paramBoolean)
/*     */   {
/* 132 */     if (this.verbose)
/* 133 */       System.err.println("EmbeddedStage.setFullScreen " + paramBoolean);
/*     */   }
/*     */ 
/*     */   public void requestFocus()
/*     */   {
/* 139 */     if (!this.host.requestFocus()) {
/* 140 */       return;
/*     */     }
/* 142 */     super.requestFocus();
/*     */   }
/*     */ 
/*     */   public void toBack()
/*     */   {
/* 147 */     if (this.verbose)
/* 148 */       System.err.println("EmbeddedStage.toBack");
/*     */   }
/*     */ 
/*     */   public void toFront()
/*     */   {
/* 154 */     if (this.verbose)
/* 155 */       System.err.println("EmbeddedStage.toFront");
/*     */   }
/*     */ 
/*     */   public boolean grabFocus()
/*     */   {
/* 161 */     return false;
/*     */   }
/*     */ 
/*     */   public void ungrabFocus() {
/*     */   }
/*     */ 
/*     */   private void notifyStageListener(final Runnable paramRunnable) {
/* 168 */     AccessControlContext localAccessControlContext = getAccessControlContext();
/* 169 */     AccessController.doPrivileged(new PrivilegedAction()
/*     */     {
/*     */       public Void run() {
/* 172 */         paramRunnable.run();
/* 173 */         return null;
/*     */       }
/*     */     }
/*     */     , localAccessControlContext);
/*     */   }
/*     */ 
/*     */   private void notifyStageListenerLater(final Runnable paramRunnable)
/*     */   {
/* 178 */     Platform.runLater(new Runnable()
/*     */     {
/*     */       public void run() {
/* 181 */         EmbeddedStage.this.notifyStageListener(paramRunnable);
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public void setLocation(final int paramInt1, final int paramInt2)
/*     */   {
/* 190 */     Runnable local3 = new Runnable()
/*     */     {
/*     */       public void run() {
/* 193 */         if (EmbeddedStage.this.stageListener != null)
/* 194 */           EmbeddedStage.this.stageListener.changedLocation(paramInt1, paramInt2);
/*     */       }
/*     */     };
/* 199 */     if (Toolkit.getToolkit().isFxUserThread())
/* 200 */       notifyStageListener(local3);
/*     */     else
/* 202 */       notifyStageListenerLater(local3);
/*     */   }
/*     */ 
/*     */   public void setSize(final int paramInt1, final int paramInt2)
/*     */   {
/* 208 */     Runnable local4 = new Runnable()
/*     */     {
/*     */       public void run() {
/* 211 */         if (EmbeddedStage.this.stageListener != null)
/* 212 */           EmbeddedStage.this.stageListener.changedSize(paramInt1, paramInt2);
/*     */       }
/*     */     };
/* 217 */     if (Toolkit.getToolkit().isFxUserThread())
/* 218 */       notifyStageListener(local4);
/*     */     else
/* 220 */       notifyStageListenerLater(local4);
/*     */   }
/*     */ 
/*     */   public void setFocused(final boolean paramBoolean, final int paramInt)
/*     */   {
/* 226 */     Runnable local5 = new Runnable()
/*     */     {
/*     */       public void run() {
/* 229 */         if (EmbeddedStage.this.stageListener != null)
/* 230 */           EmbeddedStage.this.stageListener.changedFocused(paramBoolean, AbstractEvents.focusCauseToPeerFocusCause(paramInt));
/*     */       }
/*     */     };
/* 236 */     if (Toolkit.getToolkit().isFxUserThread())
/* 237 */       notifyStageListener(local5);
/*     */     else
/* 239 */       notifyStageListenerLater(local5);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.quantum.EmbeddedStage
 * JD-Core Version:    0.6.2
 */