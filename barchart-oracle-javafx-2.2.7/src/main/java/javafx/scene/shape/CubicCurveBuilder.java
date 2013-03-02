/*     */ package javafx.scene.shape;
/*     */ 
/*     */ import javafx.util.Builder;
/*     */ 
/*     */ public class CubicCurveBuilder<B extends CubicCurveBuilder<B>> extends ShapeBuilder<B>
/*     */   implements Builder<CubicCurve>
/*     */ {
/*     */   private int __set;
/*     */   private double controlX1;
/*     */   private double controlX2;
/*     */   private double controlY1;
/*     */   private double controlY2;
/*     */   private double endX;
/*     */   private double endY;
/*     */   private double startX;
/*     */   private double startY;
/*     */ 
/*     */   public static CubicCurveBuilder<?> create()
/*     */   {
/*  15 */     return new CubicCurveBuilder();
/*     */   }
/*     */ 
/*     */   private void __set(int paramInt)
/*     */   {
/*  20 */     this.__set |= 1 << paramInt;
/*     */   }
/*     */   public void applyTo(CubicCurve paramCubicCurve) {
/*  23 */     super.applyTo(paramCubicCurve);
/*  24 */     int i = this.__set;
/*  25 */     while (i != 0) {
/*  26 */       int j = Integer.numberOfTrailingZeros(i);
/*  27 */       i &= (1 << j ^ 0xFFFFFFFF);
/*  28 */       switch (j) { case 0:
/*  29 */         paramCubicCurve.setControlX1(this.controlX1); break;
/*     */       case 1:
/*  30 */         paramCubicCurve.setControlX2(this.controlX2); break;
/*     */       case 2:
/*  31 */         paramCubicCurve.setControlY1(this.controlY1); break;
/*     */       case 3:
/*  32 */         paramCubicCurve.setControlY2(this.controlY2); break;
/*     */       case 4:
/*  33 */         paramCubicCurve.setEndX(this.endX); break;
/*     */       case 5:
/*  34 */         paramCubicCurve.setEndY(this.endY); break;
/*     */       case 6:
/*  35 */         paramCubicCurve.setStartX(this.startX); break;
/*     */       case 7:
/*  36 */         paramCubicCurve.setStartY(this.startY);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public B controlX1(double paramDouble)
/*     */   {
/*  47 */     this.controlX1 = paramDouble;
/*  48 */     __set(0);
/*  49 */     return this;
/*     */   }
/*     */ 
/*     */   public B controlX2(double paramDouble)
/*     */   {
/*  58 */     this.controlX2 = paramDouble;
/*  59 */     __set(1);
/*  60 */     return this;
/*     */   }
/*     */ 
/*     */   public B controlY1(double paramDouble)
/*     */   {
/*  69 */     this.controlY1 = paramDouble;
/*  70 */     __set(2);
/*  71 */     return this;
/*     */   }
/*     */ 
/*     */   public B controlY2(double paramDouble)
/*     */   {
/*  80 */     this.controlY2 = paramDouble;
/*  81 */     __set(3);
/*  82 */     return this;
/*     */   }
/*     */ 
/*     */   public B endX(double paramDouble)
/*     */   {
/*  91 */     this.endX = paramDouble;
/*  92 */     __set(4);
/*  93 */     return this;
/*     */   }
/*     */ 
/*     */   public B endY(double paramDouble)
/*     */   {
/* 102 */     this.endY = paramDouble;
/* 103 */     __set(5);
/* 104 */     return this;
/*     */   }
/*     */ 
/*     */   public B startX(double paramDouble)
/*     */   {
/* 113 */     this.startX = paramDouble;
/* 114 */     __set(6);
/* 115 */     return this;
/*     */   }
/*     */ 
/*     */   public B startY(double paramDouble)
/*     */   {
/* 124 */     this.startY = paramDouble;
/* 125 */     __set(7);
/* 126 */     return this;
/*     */   }
/*     */ 
/*     */   public CubicCurve build()
/*     */   {
/* 133 */     CubicCurve localCubicCurve = new CubicCurve();
/* 134 */     applyTo(localCubicCurve);
/* 135 */     return localCubicCurve;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.shape.CubicCurveBuilder
 * JD-Core Version:    0.6.2
 */