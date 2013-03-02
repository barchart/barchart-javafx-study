/*    */ package com.sun.javafx.animation;
/*    */ 
/*    */ import javafx.util.Duration;
/*    */ 
/*    */ public class TickCalculation
/*    */ {
/*    */   public static long add(long paramLong1, long paramLong2)
/*    */   {
/* 55 */     assert ((paramLong1 >= 0L) && (paramLong2 >= 0L));
/*    */ 
/* 57 */     long l = paramLong1 + paramLong2;
/* 58 */     return l < 0L ? 9223372036854775807L : l;
/*    */   }
/*    */ 
/*    */   public static long sub(long paramLong1, long paramLong2) {
/* 62 */     assert ((paramLong1 >= 0L) && (paramLong2 >= 0L));
/*    */ 
/* 64 */     return paramLong1 == 9223372036854775807L ? 9223372036854775807L : paramLong2 == 9223372036854775807L ? 0L : Math.max(0L, paramLong1 - paramLong2);
/*    */   }
/*    */ 
/*    */   public static long fromDuration(Duration paramDuration) {
/* 68 */     return Math.round(6.0D * paramDuration.toMillis());
/*    */   }
/*    */ 
/*    */   public static long fromDuration(Duration paramDuration, double paramDouble) {
/* 72 */     return Math.round(6.0D * paramDuration.toMillis() / Math.abs(paramDouble));
/*    */   }
/*    */ 
/*    */   public static Duration toDuration(long paramLong) {
/* 76 */     return Duration.millis(paramLong / 6.0D);
/*    */   }
/*    */ 
/*    */   public static Duration toDuration(long paramLong, double paramDouble) {
/* 80 */     return Duration.millis(paramLong * Math.abs(paramDouble) / 6.0D);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.animation.TickCalculation
 * JD-Core Version:    0.6.2
 */