/*     */ package javafx.scene.shape;
/*     */ 
/*     */ import javafx.util.Builder;
/*     */ 
/*     */ public class QuadCurveBuilder<B extends QuadCurveBuilder<B>> extends ShapeBuilder<B>
/*     */   implements Builder<QuadCurve>
/*     */ {
/*     */   private int __set;
/*     */   private double controlX;
/*     */   private double controlY;
/*     */   private double endX;
/*     */   private double endY;
/*     */   private double startX;
/*     */   private double startY;
/*     */ 
/*     */   public static QuadCurveBuilder<?> create()
/*     */   {
/*  15 */     return new QuadCurveBuilder();
/*     */   }
/*     */ 
/*     */   public void applyTo(QuadCurve paramQuadCurve)
/*     */   {
/*  20 */     super.applyTo(paramQuadCurve);
/*  21 */     int i = this.__set;
/*  22 */     if ((i & 0x1) != 0) paramQuadCurve.setControlX(this.controlX);
/*  23 */     if ((i & 0x2) != 0) paramQuadCurve.setControlY(this.controlY);
/*  24 */     if ((i & 0x4) != 0) paramQuadCurve.setEndX(this.endX);
/*  25 */     if ((i & 0x8) != 0) paramQuadCurve.setEndY(this.endY);
/*  26 */     if ((i & 0x10) != 0) paramQuadCurve.setStartX(this.startX);
/*  27 */     if ((i & 0x20) != 0) paramQuadCurve.setStartY(this.startY);
/*     */   }
/*     */ 
/*     */   public B controlX(double paramDouble)
/*     */   {
/*  36 */     this.controlX = paramDouble;
/*  37 */     this.__set |= 1;
/*  38 */     return this;
/*     */   }
/*     */ 
/*     */   public B controlY(double paramDouble)
/*     */   {
/*  47 */     this.controlY = paramDouble;
/*  48 */     this.__set |= 2;
/*  49 */     return this;
/*     */   }
/*     */ 
/*     */   public B endX(double paramDouble)
/*     */   {
/*  58 */     this.endX = paramDouble;
/*  59 */     this.__set |= 4;
/*  60 */     return this;
/*     */   }
/*     */ 
/*     */   public B endY(double paramDouble)
/*     */   {
/*  69 */     this.endY = paramDouble;
/*  70 */     this.__set |= 8;
/*  71 */     return this;
/*     */   }
/*     */ 
/*     */   public B startX(double paramDouble)
/*     */   {
/*  80 */     this.startX = paramDouble;
/*  81 */     this.__set |= 16;
/*  82 */     return this;
/*     */   }
/*     */ 
/*     */   public B startY(double paramDouble)
/*     */   {
/*  91 */     this.startY = paramDouble;
/*  92 */     this.__set |= 32;
/*  93 */     return this;
/*     */   }
/*     */ 
/*     */   public QuadCurve build()
/*     */   {
/* 100 */     QuadCurve localQuadCurve = new QuadCurve();
/* 101 */     applyTo(localQuadCurve);
/* 102 */     return localQuadCurve;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.shape.QuadCurveBuilder
 * JD-Core Version:    0.6.2
 */