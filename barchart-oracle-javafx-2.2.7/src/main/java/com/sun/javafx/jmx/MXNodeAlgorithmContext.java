/*    */ package com.sun.javafx.jmx;
/*    */ 
/*    */ public class MXNodeAlgorithmContext
/*    */ {
/*    */   private int counter;
/*    */ 
/*    */   public MXNodeAlgorithmContext()
/*    */   {
/* 40 */     this(0);
/*    */   }
/*    */ 
/*    */   public MXNodeAlgorithmContext(int paramInt)
/*    */   {
/* 50 */     this.counter = paramInt;
/*    */   }
/*    */ 
/*    */   public int getNextInt()
/*    */   {
/* 60 */     return ++this.counter;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.jmx.MXNodeAlgorithmContext
 * JD-Core Version:    0.6.2
 */