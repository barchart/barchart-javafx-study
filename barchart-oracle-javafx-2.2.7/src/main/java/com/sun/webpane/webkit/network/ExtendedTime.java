/*    */ package com.sun.webpane.webkit.network;
/*    */ 
/*    */ final class ExtendedTime
/*    */   implements Comparable<ExtendedTime>
/*    */ {
/*    */   private final long baseTime;
/*    */   private final int subtime;
/*    */ 
/*    */   ExtendedTime(long paramLong, int paramInt)
/*    */   {
/* 20 */     this.baseTime = paramLong;
/* 21 */     this.subtime = paramInt;
/*    */   }
/*    */ 
/*    */   static ExtendedTime currentTime()
/*    */   {
/* 30 */     return new ExtendedTime(System.currentTimeMillis(), 0);
/*    */   }
/*    */ 
/*    */   long baseTime()
/*    */   {
/* 38 */     return this.baseTime;
/*    */   }
/*    */ 
/*    */   int subtime()
/*    */   {
/* 45 */     return this.subtime;
/*    */   }
/*    */ 
/*    */   ExtendedTime incrementSubtime()
/*    */   {
/* 52 */     return new ExtendedTime(this.baseTime, this.subtime + 1);
/*    */   }
/*    */ 
/*    */   public int compareTo(ExtendedTime paramExtendedTime)
/*    */   {
/* 60 */     int i = (int)(this.baseTime - paramExtendedTime.baseTime);
/* 61 */     if (i != 0) {
/* 62 */       return i;
/*    */     }
/* 64 */     return this.subtime - paramExtendedTime.subtime;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 72 */     return "[baseTime=" + this.baseTime + ", subtime=" + this.subtime + "]";
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.network.ExtendedTime
 * JD-Core Version:    0.6.2
 */