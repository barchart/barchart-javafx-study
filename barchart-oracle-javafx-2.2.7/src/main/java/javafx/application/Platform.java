/*     */ package javafx.application;
/*     */ 
/*     */ import com.sun.javafx.application.PlatformImpl;
/*     */ 
/*     */ public final class Platform
/*     */ {
/*     */   public static void runLater(Runnable paramRunnable)
/*     */   {
/*  52 */     PlatformImpl.runLater(paramRunnable);
/*     */   }
/*     */ 
/*     */   public static boolean isFxApplicationThread()
/*     */   {
/*  68 */     return PlatformImpl.isFxApplicationThread();
/*     */   }
/*     */ 
/*     */   public static void exit()
/*     */   {
/*  84 */     PlatformImpl.exit();
/*     */   }
/*     */ 
/*     */   public static void setImplicitExit(boolean paramBoolean)
/*     */   {
/* 103 */     PlatformImpl.setImplicitExit(paramBoolean);
/*     */   }
/*     */ 
/*     */   public static boolean isImplicitExit()
/*     */   {
/* 113 */     return PlatformImpl.isImplicitExit();
/*     */   }
/*     */ 
/*     */   public static boolean isSupported(ConditionalFeature paramConditionalFeature)
/*     */   {
/* 131 */     return PlatformImpl.isSupported(paramConditionalFeature);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.application.Platform
 * JD-Core Version:    0.6.2
 */