/*     */ package com.sun.javafx.binding;
/*     */ 
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ import javafx.beans.value.ObservableFloatValue;
/*     */ 
/*     */ public final class FloatConstant
/*     */   implements ObservableFloatValue
/*     */ {
/*     */   private final float value;
/*     */ 
/*     */   private FloatConstant(float paramFloat)
/*     */   {
/*  60 */     this.value = paramFloat;
/*     */   }
/*     */ 
/*     */   public static FloatConstant valueOf(float paramFloat) {
/*  64 */     return new FloatConstant(paramFloat);
/*     */   }
/*     */ 
/*     */   public float get()
/*     */   {
/*  69 */     return this.value;
/*     */   }
/*     */ 
/*     */   public Float getValue()
/*     */   {
/*  74 */     return Float.valueOf(this.value);
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
/* 109 */     return this.value;
/*     */   }
/*     */ 
/*     */   public double doubleValue()
/*     */   {
/* 114 */     return this.value;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.binding.FloatConstant
 * JD-Core Version:    0.6.2
 */