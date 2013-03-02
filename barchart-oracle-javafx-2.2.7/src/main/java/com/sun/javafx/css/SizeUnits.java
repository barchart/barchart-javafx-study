/*     */ package com.sun.javafx.css;
/*     */ 
/*     */ import javafx.scene.text.Font;
/*     */ 
/*     */ public enum SizeUnits
/*     */ {
/*  35 */   PERCENT(false), 
/*     */ 
/*  51 */   IN(true), 
/*     */ 
/*  67 */   CM(true), 
/*     */ 
/*  83 */   MM(true), 
/*     */ 
/*  99 */   EM(false), 
/*     */ 
/* 115 */   EX(false), 
/*     */ 
/* 133 */   PT(true), 
/*     */ 
/* 148 */   PC(true), 
/*     */ 
/* 163 */   PX(true), 
/*     */ 
/* 179 */   DEG(true), 
/*     */ 
/* 195 */   GRAD(true), 
/*     */ 
/* 214 */   RAD(true), 
/*     */ 
/* 233 */   TURN(true);
/*     */ 
/*     */   private final boolean absolute;
/*     */   private static final int DOTS_PER_INCH = 96;
/*     */   private static final int POINTS_PER_INCH = 72;
/*     */   private static final double CM_PER_INCH = 2.54D;
/*     */   private static final double MM_PER_INCH = 25.399999999999999D;
/*     */   private static final int POINTS_PER_PICA = 12;
/*     */ 
/*     */   abstract double points(double paramDouble1, double paramDouble2, Font paramFont);
/*     */ 
/*     */   abstract double pixels(double paramDouble1, double paramDouble2, Font paramFont);
/*     */ 
/*     */   private SizeUnits(boolean paramBoolean)
/*     */   {
/* 255 */     this.absolute = paramBoolean;
/*     */   }
/*     */ 
/*     */   public boolean isAbsolute()
/*     */   {
/* 260 */     return this.absolute;
/*     */   }
/*     */ 
/*     */   private static double pointSize(Font paramFont)
/*     */   {
/* 273 */     return pixelSize(paramFont) * 0.0D;
/*     */   }
/*     */ 
/*     */   private static double pixelSize(Font paramFont)
/*     */   {
/* 278 */     return paramFont != null ? paramFont.getSize() : Font.getDefault().getSize();
/*     */   }
/*     */ 
/*     */   private static double round(double paramDouble)
/*     */   {
/* 284 */     if (paramDouble == 0.0D) return paramDouble;
/*     */ 
/* 286 */     double d = paramDouble < 0.0D ? -0.05D : 0.05D;
/* 287 */     return ()((paramDouble + d) * 10.0D) / 10.0D;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.css.SizeUnits
 * JD-Core Version:    0.6.2
 */