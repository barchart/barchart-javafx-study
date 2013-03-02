/*    */ package com.sun.scenario.animation;
/*    */ 
/*    */ import com.sun.scenario.animation.shared.CurrentTime;
/*    */ 
/*    */ class MilliCurrentTime
/*    */   implements CurrentTime
/*    */ {
/*    */   public long millis()
/*    */   {
/* 37 */     return System.currentTimeMillis();
/*    */   }
/*    */ 
/*    */   public long nanos()
/*    */   {
/* 42 */     return System.currentTimeMillis() * 1000000L;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.animation.MilliCurrentTime
 * JD-Core Version:    0.6.2
 */