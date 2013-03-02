/*     */ package com.sun.scenario.animation;
/*     */ 
/*     */ import javafx.animation.Interpolator;
/*     */ import javafx.util.Duration;
/*     */ 
/*     */ public abstract class NumberTangentInterpolator extends Interpolator
/*     */ {
/*     */   public abstract double getInValue();
/*     */ 
/*     */   public abstract double getOutValue();
/*     */ 
/*     */   public abstract double getInMillis();
/*     */ 
/*     */   public abstract double getOutMillis();
/*     */ 
/*     */   public static NumberTangentInterpolator create(double paramDouble1, final Duration paramDuration1, double paramDouble2, Duration paramDuration2)
/*     */   {
/*  64 */     return new NumberTangentInterpolator()
/*     */     {
/*     */       public double getInValue() {
/*  67 */         return this.val$inValue;
/*     */       }
/*     */ 
/*     */       public double getOutValue()
/*     */       {
/*  72 */         return paramDuration1;
/*     */       }
/*     */ 
/*     */       public double getInMillis()
/*     */       {
/*  77 */         return this.val$inDuration.toMillis();
/*     */       }
/*     */ 
/*     */       public double getOutMillis()
/*     */       {
/*  82 */         return this.val$outDuration.toMillis();
/*     */       }
/*     */ 
/*     */       public String toString()
/*     */       {
/*  87 */         return "NumberTangentInterpolator [inValue=" + this.val$inValue + ", inDuration=" + this.val$inDuration + ", outValue=" + paramDuration1 + ", outDuration=" + this.val$outDuration + "]";
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static NumberTangentInterpolator create(double paramDouble, Duration paramDuration)
/*     */   {
/*  96 */     return new NumberTangentInterpolator()
/*     */     {
/*     */       public double getInValue() {
/*  99 */         return this.val$value;
/*     */       }
/*     */ 
/*     */       public double getOutValue()
/*     */       {
/* 104 */         return this.val$value;
/*     */       }
/*     */ 
/*     */       public double getInMillis()
/*     */       {
/* 109 */         return this.val$duration.toMillis();
/*     */       }
/*     */ 
/*     */       public double getOutMillis()
/*     */       {
/* 114 */         return this.val$duration.toMillis();
/*     */       }
/*     */ 
/*     */       public String toString()
/*     */       {
/* 119 */         return "NumberTangentInterpolator [inValue=" + this.val$value + ", inDuration=" + this.val$duration + ", outValue=" + this.val$value + ", outDuration=" + this.val$duration + "]";
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   protected double curve(double paramDouble)
/*     */   {
/* 131 */     return paramDouble;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.animation.NumberTangentInterpolator
 * JD-Core Version:    0.6.2
 */