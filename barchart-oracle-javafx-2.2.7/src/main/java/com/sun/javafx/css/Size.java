/*     */ package com.sun.javafx.css;
/*     */ 
/*     */ import javafx.scene.text.Font;
/*     */ 
/*     */ public final class Size
/*     */ {
/*     */   private final double value;
/*     */   private final SizeUnits units;
/*     */ 
/*     */   public Size(double paramDouble, SizeUnits paramSizeUnits)
/*     */   {
/*  38 */     this.value = paramDouble;
/*  39 */     this.units = (paramSizeUnits != null ? paramSizeUnits : SizeUnits.PX);
/*     */   }
/*     */ 
/*     */   public double getValue()
/*     */   {
/*  44 */     return this.value;
/*     */   }
/*     */ 
/*     */   public SizeUnits getUnits()
/*     */   {
/*  49 */     return this.units;
/*     */   }
/*     */ 
/*     */   public boolean isAbsolute()
/*     */   {
/*  56 */     return this.units.isAbsolute();
/*     */   }
/*     */ 
/*     */   public double points(Font paramFont)
/*     */   {
/*  61 */     return points(1.0D, paramFont);
/*     */   }
/*     */ 
/*     */   public double points(double paramDouble, Font paramFont)
/*     */   {
/*  71 */     return this.units.points(this.value, paramDouble, paramFont);
/*     */   }
/*     */ 
/*     */   public double pixels(double paramDouble, Font paramFont)
/*     */   {
/*  81 */     return this.units.pixels(this.value, paramDouble, paramFont);
/*     */   }
/*     */ 
/*     */   public double pixels(Font paramFont)
/*     */   {
/*  89 */     return pixels(1.0D, paramFont);
/*     */   }
/*     */ 
/*     */   public double pixels(double paramDouble)
/*     */   {
/*  97 */     return pixels(paramDouble, null);
/*     */   }
/*     */ 
/*     */   public double pixels()
/*     */   {
/* 104 */     return pixels(1.0D, null);
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 108 */     return Double.toString(this.value) + this.units.toString();
/*     */   }
/*     */ 
/*     */   public int hashCode() {
/* 112 */     long l = 17L;
/* 113 */     l = 37L * l + Double.doubleToLongBits(this.value);
/* 114 */     l = 37L * l + this.units.hashCode();
/* 115 */     return (int)(l ^ l >> 32);
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject) {
/* 119 */     if (this == paramObject) return true;
/* 120 */     if ((paramObject instanceof Size)) {
/* 121 */       Size localSize = (Size)paramObject;
/* 122 */       if ((this.units == localSize.units) && (this.value > 0.0D) ? localSize.value > 0.0D : localSize.value < 0.0D)
/*     */       {
/* 132 */         double d = Math.abs(this.value) - Math.abs(localSize.value);
/* 133 */         return Math.abs(d) < 1.0E-06D;
/*     */       }
/*     */     }
/* 136 */     return false;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.css.Size
 * JD-Core Version:    0.6.2
 */