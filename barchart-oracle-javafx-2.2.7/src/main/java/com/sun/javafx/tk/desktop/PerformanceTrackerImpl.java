/*    */ package com.sun.javafx.tk.desktop;
/*    */ 
/*    */ import com.sun.javafx.perf.PerformanceTracker;
/*    */ 
/*    */ public class PerformanceTrackerImpl extends PerformanceTracker
/*    */ {
/* 33 */   final PerformanceTrackerHelper helper = PerformanceTrackerHelper.getInstance();
/*    */ 
/*    */   public PerformanceTrackerImpl()
/*    */   {
/* 37 */     setPerfLoggingEnabled(this.helper.isPerfLoggingEnabled());
/*    */   }
/*    */ 
/*    */   public void doLogEvent(String paramString) {
/* 41 */     this.helper.logEvent(paramString);
/*    */   }
/*    */ 
/*    */   public void doOutputLog() {
/* 45 */     this.helper.outputLog();
/*    */   }
/*    */ 
/*    */   public long nanoTime() {
/* 49 */     return this.helper.nanoTime();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.desktop.PerformanceTrackerImpl
 * JD-Core Version:    0.6.2
 */