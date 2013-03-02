/*    */ package com.sun.glass.ui.mac;
/*    */ 
/*    */ import com.sun.glass.ui.Application;
/*    */ import com.sun.glass.ui.Timer;
/*    */ import java.security.AccessController;
/*    */ import java.security.PrivilegedAction;
/*    */ 
/*    */ final class MacTimer extends Timer
/*    */ {
/* 34 */   private static final int minPeriod = _getMinPeriod();
/* 35 */   private static final int maxPeriod = _getMaxPeriod();
/*    */ 
/*    */   protected MacTimer(Runnable runnable)
/*    */   {
/* 18 */     super(runnable);
/*    */   }
/*    */ 
/*    */   private static native int _getMinPeriod();
/*    */ 
/*    */   private static native int _getMaxPeriod();
/*    */ 
/*    */   static int getMinPeriod_impl()
/*    */   {
/* 39 */     return minPeriod;
/*    */   }
/*    */ 
/*    */   static int getMaxPeriod_impl() {
/* 43 */     return maxPeriod;
/*    */   }
/*    */ 
/*    */   protected native long _start(Runnable paramRunnable, int paramInt);
/*    */ 
/*    */   protected native void _stop(long paramLong);
/*    */ 
/*    */   static
/*    */   {
/* 27 */     AccessController.doPrivileged(new PrivilegedAction()
/*    */     {
/*    */       public Void run() {
/* 30 */         Application.loadNativeLibrary();
/* 31 */         return null;
/*    */       }
/*    */     });
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.mac.MacTimer
 * JD-Core Version:    0.6.2
 */