/*    */ package com.sun.glass.ui.gtk;
/*    */ 
/*    */ import com.sun.glass.ui.Timer;
/*    */ 
/*    */ public class GtkTimer extends Timer
/*    */ {
/*    */   public GtkTimer(Runnable runnable)
/*    */   {
/* 16 */     super(runnable);
/*    */   }
/*    */ 
/*    */   protected native long _start(Runnable paramRunnable, int paramInt);
/*    */ 
/*    */   protected native void _stop(long paramLong);
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.ui.gtk.GtkTimer
 * JD-Core Version:    0.6.2
 */