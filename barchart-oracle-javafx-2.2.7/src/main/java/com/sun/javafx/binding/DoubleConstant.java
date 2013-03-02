/*     */ package com.sun.javafx.binding;
/*     */ 
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ import javafx.beans.value.ObservableDoubleValue;
/*     */ 
/*     */ public final class DoubleConstant
/*     */   implements ObservableDoubleValue
/*     */ {
/*     */   private final double value;
/*     */ 
/*     */   private DoubleConstant(double paramDouble)
/*     */   {
/*  60 */     this.value = paramDouble;
/*     */   }
/*     */ 
/*     */   public static DoubleConstant valueOf(double paramDouble) {
/*  64 */     return new DoubleConstant(paramDouble);
/*     */   }
/*     */ 
/*     */   public double get()
/*     */   {
/*  69 */     return this.value;
/*     */   }
/*     */ 
/*     */   public Double getValue()
/*     */   {
/*  74 */     return Double.valueOf(this.value);
/*     */   }
/*     */ 
/*     */   public void addListener(InvalidationListener paramInvalidationListener)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void addListener(ChangeListener<? super Number> paramChangeListener)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void removeListener(InvalidationListener paramInvalidationListener)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void removeListener(ChangeListener<? super Number> paramChangeListener)
/*     */   {
/*     */   }
/*     */ 
/*     */   public int intValue()
/*     */   {
/*  99 */     return (int)this.value;
/*     */   }
/*     */ 
/*     */   public long longValue()
/*     */   {
/* 104 */     return ()this.value;
/*     */   }
/*     */ 
/*     */   public float floatValue()
/*     */   {
/* 109 */     return (float)this.value;
/*     */   }
/*     */ 
/*     */   public double doubleValue()
/*     */   {
/* 114 */     return this.value;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.binding.DoubleConstant
 * JD-Core Version:    0.6.2
 */