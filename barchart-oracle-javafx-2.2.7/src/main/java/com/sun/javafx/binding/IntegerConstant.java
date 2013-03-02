/*    */ package com.sun.javafx.binding;
/*    */ 
/*    */ import javafx.beans.InvalidationListener;
/*    */ import javafx.beans.value.ChangeListener;
/*    */ import javafx.beans.value.ObservableIntegerValue;
/*    */ 
/*    */ public final class IntegerConstant
/*    */   implements ObservableIntegerValue
/*    */ {
/*    */   private final int value;
/*    */ 
/*    */   private IntegerConstant(int paramInt)
/*    */   {
/* 40 */     this.value = paramInt;
/*    */   }
/*    */ 
/*    */   public static IntegerConstant valueOf(int paramInt) {
/* 44 */     return new IntegerConstant(paramInt);
/*    */   }
/*    */ 
/*    */   public int get()
/*    */   {
/* 49 */     return this.value;
/*    */   }
/*    */ 
/*    */   public Integer getValue()
/*    */   {
/* 54 */     return Integer.valueOf(this.value);
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
/* 79 */     return this.value;
/*    */   }
/*    */ 
/*    */   public long longValue()
/*    */   {
/* 84 */     return this.value;
/*    */   }
/*    */ 
/*    */   public float floatValue()
/*    */   {
/* 89 */     return this.value;
/*    */   }
/*    */ 
/*    */   public double doubleValue()
/*    */   {
/* 94 */     return this.value;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.binding.IntegerConstant
 * JD-Core Version:    0.6.2
 */