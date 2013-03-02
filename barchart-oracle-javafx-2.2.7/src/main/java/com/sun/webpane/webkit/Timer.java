/*    */ package com.sun.webpane.webkit;
/*    */ 
/*    */ import com.sun.webpane.platform.WebPage;
/*    */ import java.security.AccessController;
/*    */ import java.security.PrivilegedAction;
/*    */ 
/*    */ public class Timer
/*    */ {
/*    */   private static Timer instance;
/*    */   private static Mode mode;
/*    */   long fireTime;
/*    */ 
/*    */   public static synchronized Mode getMode()
/*    */   {
/* 24 */     if (mode == null) {
/* 25 */       mode = Boolean.valueOf((String)AccessController.doPrivileged(new PrivilegedAction()
/*    */       {
/*    */         public String run()
/*    */         {
/* 29 */           return System.getProperty("com.sun.webpane.platformticks", "true");
/*    */         }
/*    */       })).booleanValue() ? Mode.PLATFORM_TICKS : Mode.SEPARATE_THREAD;
/*    */     }
/*    */ 
/* 34 */     return mode;
/*    */   }
/*    */ 
/*    */   public static synchronized Timer getTimer() {
/* 38 */     if (instance == null) {
/* 39 */       instance = getMode() == Mode.PLATFORM_TICKS ? new Timer() : new SeparateThreadTimer();
/*    */     }
/*    */ 
/* 42 */     return instance;
/*    */   }
/*    */ 
/*    */   public synchronized void notifyTick() {
/* 46 */     if ((this.fireTime > 0L) && (this.fireTime <= System.currentTimeMillis()))
/* 47 */       fireTimerEvent(this.fireTime);
/*    */   }
/*    */ 
/*    */   void fireTimerEvent(long paramLong)
/*    */   {
/* 52 */     int i = 0;
/* 53 */     synchronized (this)
/*    */     {
/* 57 */       if (paramLong == this.fireTime) {
/* 58 */         i = 1;
/* 59 */         this.fireTime = 0L;
/*    */       }
/*    */     }
/* 62 */     if (i != 0) {
/* 63 */       WebPage.lockPage();
/*    */       try
/*    */       {
/* 66 */         twkFireTimerEvent();
/*    */       }
/*    */       finally {
/* 69 */         WebPage.unlockPage();
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   synchronized void setFireTime(long paramLong) {
/* 75 */     this.fireTime = paramLong;
/*    */   }
/*    */ 
/*    */   private static void fwkSetFireTime(double paramDouble)
/*    */   {
/* 82 */     getTimer().setFireTime(()Math.ceil(paramDouble * 1000.0D));
/*    */   }
/*    */ 
/*    */   private static void fwkStopTimer() {
/* 86 */     getTimer().setFireTime(0L);
/*    */   }
/*    */ 
/*    */   private static native void twkFireTimerEvent();
/*    */ 
/*    */   public static enum Mode
/*    */   {
/* 19 */     PLATFORM_TICKS, 
/* 20 */     SEPARATE_THREAD;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.Timer
 * JD-Core Version:    0.6.2
 */