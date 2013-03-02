/*    */ package com.sun.scenario.animation;
/*    */ 
/*    */ import com.sun.scenario.animation.shared.CurrentTime;
/*    */ 
/*    */ class NanoCurrentTime
/*    */   implements CurrentTime
/*    */ {
/*    */   public long millis()
/*    */   {
/* 37 */     return System.nanoTime() / 1000L / 1000L;
/*    */   }
/*    */ 
/*    */   public long nanos()
/*    */   {
/* 42 */     return System.nanoTime();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.animation.NanoCurrentTime
 * JD-Core Version:    0.6.2
 */