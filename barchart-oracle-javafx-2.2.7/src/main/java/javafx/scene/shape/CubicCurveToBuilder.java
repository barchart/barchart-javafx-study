/*     */ package javafx.scene.shape;
/*     */ 
/*     */ import javafx.util.Builder;
/*     */ 
/*     */ public class CubicCurveToBuilder<B extends CubicCurveToBuilder<B>> extends PathElementBuilder<B>
/*     */   implements Builder<CubicCurveTo>
/*     */ {
/*     */   private int __set;
/*     */   private double controlX1;
/*     */   private double controlX2;
/*     */   private double controlY1;
/*     */   private double controlY2;
/*     */   private double x;
/*     */   private double y;
/*     */ 
/*     */   public static CubicCurveToBuilder<?> create()
/*     */   {
/*  15 */     return new CubicCurveToBuilder();
/*     */   }
/*     */ 
/*     */   public void applyTo(CubicCurveTo paramCubicCurveTo)
/*     */   {
/*  20 */     super.applyTo(paramCubicCurveTo);
/*  21 */     int i = this.__set;
/*  22 */     if ((i & 0x1) != 0) paramCubicCurveTo.setControlX1(this.controlX1);
/*  23 */     if ((i & 0x2) != 0) paramCubicCurveTo.setControlX2(this.controlX2);
/*  24 */     if ((i & 0x4) != 0) paramCubicCurveTo.setControlY1(this.controlY1);
/*  25 */     if ((i & 0x8) != 0) paramCubicCurveTo.setControlY2(this.controlY2);
/*  26 */     if ((i & 0x10) != 0) paramCubicCurveTo.setX(this.x);
/*  27 */     if ((i & 0x20) != 0) paramCubicCurveTo.setY(this.y);
/*     */   }
/*     */ 
/*     */   public B controlX1(double paramDouble)
/*     */   {
/*  36 */     this.controlX1 = paramDouble;
/*  37 */     this.__set |= 1;
/*  38 */     return this;
/*     */   }
/*     */ 
/*     */   public B controlX2(double paramDouble)
/*     */   {
/*  47 */     this.controlX2 = paramDouble;
/*  48 */     this.__set |= 2;
/*  49 */     return this;
/*     */   }
/*     */ 
/*     */   public B controlY1(double paramDouble)
/*     */   {
/*  58 */     this.controlY1 = paramDouble;
/*  59 */     this.__set |= 4;
/*  60 */     return this;
/*     */   }
/*     */ 
/*     */   public B controlY2(double paramDouble)
/*     */   {
/*  69 */     this.controlY2 = paramDouble;
/*  70 */     this.__set |= 8;
/*  71 */     return this;
/*     */   }
/*     */ 
/*     */   public B x(double paramDouble)
/*     */   {
/*  80 */     this.x = paramDouble;
/*  81 */     this.__set |= 16;
/*  82 */     return this;
/*     */   }
/*     */ 
/*     */   public B y(double paramDouble)
/*     */   {
/*  91 */     this.y = paramDouble;
/*  92 */     this.__set |= 32;
/*  93 */     return this;
/*     */   }
/*     */ 
/*     */   public CubicCurveTo build()
/*     */   {
/* 100 */     CubicCurveTo localCubicCurveTo = new CubicCurveTo();
/* 101 */     applyTo(localCubicCurveTo);
/* 102 */     return localCubicCurveTo;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.shape.CubicCurveToBuilder
 * JD-Core Version:    0.6.2
 */