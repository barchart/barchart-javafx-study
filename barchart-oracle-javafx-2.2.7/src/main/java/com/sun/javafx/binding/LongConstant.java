/*    */ package com.sun.javafx.binding;
/*    */ 
/*    */ import javafx.beans.InvalidationListener;
/*    */ import javafx.beans.value.ChangeListener;
/*    */ import javafx.beans.value.ObservableLongValue;
/*    */ 
/*    */ public final class LongConstant
/*    */   implements ObservableLongValue
/*    */ {
/*    */   private final long value;
/*    */ 
/*    */   private LongConstant(long paramLong)
/*    */   {
/* 40 */     this.value = paramLong;
/*    */   }
/*    */ 
/*    */   public static LongConstant valueOf(long paramLong) {
/* 44 */     return new LongConstant(paramLong);
/*    */   }
/*    */ 
/*    */   public long get()
/*    */   {
/* 49 */     return this.value;
/*    */   }
/*    */ 
/*    */   public Long getValue()
/*    */   {
/* 54 */     return Long.valueOf(this.value);
/*    */   }
/*    */ 
/*    */   public void addListener(InvalidationListener paramInvalidationListener)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void addListener(ChangeListener<? super Number> paramChangeListener)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void removeListener(InvalidationListener paramInvalidationListener)
/*    */   {
/*    */   }
/*    */ 
/*    */   public void removeListener(ChangeListener<? super Number> paramChangeListener)
/*    */   {
/*    */   }
/*    */ 
/*    */   public int intValue()
/*    */   {
/* 79 */     return (int)this.value;
/*    */   }
/*    */ 
/*    */   public long longValue()
/*    */   {
/* 84 */     return this.value;
/*    */   }
/*    */ 
/*    */   public float floatValue()
/*    */   {
/* 89 */     return (float)this.value;
/*    */   }
/*    */ 
/*    */   public double doubleValue()
/*    */   {
/* 94 */     return this.value;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.binding.LongConstant
 * JD-Core Version:    0.6.2
 */