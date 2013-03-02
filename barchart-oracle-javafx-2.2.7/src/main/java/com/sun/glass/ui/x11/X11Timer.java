/*    */ package com.sun.glass.ui.x11;
/*    */ 
/*    */ import com.sun.glass.ui.Timer;
/*    */ 
/*    */ final class X11Timer extends Timer
/*    */ {
/* 26 */   private static final int minPeriod = _getMinPeriod();
/* 27 */   private static final int maxPeriod = _getMaxPeriod();
/*    */ 
/*    */   protected X11Timer(Runnable runnable)
/*    */   {
/* 16 */     super(runnable);
/*    */   }
/*    */ 
/*    */   private static native int _getMinPeriod();
/*    */ 
/*    */   private static native int _getMaxPeriod();
/*    */ 
/*    */   static int getMinPeriod_impl()
/*    */   {
/* 31 */     return minPeriod;
/*    */   }
/*    */ 
/*    */   static int getMaxPeriod_impl() {
/* 35 */     return maxPeriod;
/*    */   }
/*    */ 
/*    */   protected native long _start(Runnable paramRunnable, int paramInt);
/*    */ 
/*    */   protected native void _stop(long paramLong);
/*    */ 
/*    */   static
/*    */   {
/* 25 */     X11Application.initLibrary();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.x11.X11Timer
 * JD-Core Version:    0.6.2
 */