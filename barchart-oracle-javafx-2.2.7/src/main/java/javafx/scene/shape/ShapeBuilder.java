/*     */ package javafx.scene.shape;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.scene.NodeBuilder;
/*     */ import javafx.scene.paint.Paint;
/*     */ 
/*     */ public abstract class ShapeBuilder<B extends ShapeBuilder<B>> extends NodeBuilder<B>
/*     */ {
/*     */   private int __set;
/*     */   private Paint fill;
/*     */   private boolean smooth;
/*     */   private Paint stroke;
/*     */   private Collection<? extends Double> strokeDashArray;
/*     */   private double strokeDashOffset;
/*     */   private StrokeLineCap strokeLineCap;
/*     */   private StrokeLineJoin strokeLineJoin;
/*     */   private double strokeMiterLimit;
/*     */   private StrokeType strokeType;
/*     */   private double strokeWidth;
/*     */ 
/*     */   private void __set(int paramInt)
/*     */   {
/*  15 */     this.__set |= 1 << paramInt;
/*     */   }
/*     */   public void applyTo(Shape paramShape) {
/*  18 */     super.applyTo(paramShape);
/*  19 */     int i = this.__set;
/*  20 */     while (i != 0) {
/*  21 */       int j = Integer.numberOfTrailingZeros(i);
/*  22 */       i &= (1 << j ^ 0xFFFFFFFF);
/*  23 */       switch (j) { case 0:
/*  24 */         paramShape.setFill(this.fill); break;
/*     */       case 1:
/*  25 */         paramShape.setSmooth(this.smooth); break;
/*     */       case 2:
/*  26 */         paramShape.setStroke(this.stroke); break;
/*     */       case 3:
/*  27 */         paramShape.getStrokeDashArray().addAll(this.strokeDashArray); break;
/*     */       case 4:
/*  28 */         paramShape.setStrokeDashOffset(this.strokeDashOffset); break;
/*     */       case 5:
/*  29 */         paramShape.setStrokeLineCap(this.strokeLineCap); break;
/*     */       case 6:
/*  30 */         paramShape.setStrokeLineJoin(this.strokeLineJoin); break;
/*     */       case 7:
/*  31 */         paramShape.setStrokeMiterLimit(this.strokeMiterLimit); break;
/*     */       case 8:
/*  32 */         paramShape.setStrokeType(this.strokeType); break;
/*     */       case 9:
/*  33 */         paramShape.setStrokeWidth(this.strokeWidth);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public B fill(Paint paramPaint)
/*     */   {
/*  44 */     this.fill = paramPaint;
/*  45 */     __set(0);
/*  46 */     return this;
/*     */   }
/*     */ 
/*     */   public B smooth(boolean paramBoolean)
/*     */   {
/*  55 */     this.smooth = paramBoolean;
/*  56 */     __set(1);
/*  57 */     return this;
/*     */   }
/*     */ 
/*     */   public B stroke(Paint paramPaint)
/*     */   {
/*  66 */     this.stroke = paramPaint;
/*  67 */     __set(2);
/*  68 */     return this;
/*     */   }
/*     */ 
/*     */   public B strokeDashArray(Collection<? extends Double> paramCollection)
/*     */   {
/*  77 */     this.strokeDashArray = paramCollection;
/*  78 */     __set(3);
/*  79 */     return this;
/*     */   }
/*     */ 
/*     */   public B strokeDashArray(Double[] paramArrayOfDouble)
/*     */   {
/*  86 */     return strokeDashArray(Arrays.asList(paramArrayOfDouble));
/*     */   }
/*     */ 
/*     */   public B strokeDashOffset(double paramDouble)
/*     */   {
/*  95 */     this.strokeDashOffset = paramDouble;
/*  96 */     __set(4);
/*  97 */     return this;
/*     */   }
/*     */ 
/*     */   public B strokeLineCap(StrokeLineCap paramStrokeLineCap)
/*     */   {
/* 106 */     this.strokeLineCap = paramStrokeLineCap;
/* 107 */     __set(5);
/* 108 */     return this;
/*     */   }
/*     */ 
/*     */   public B strokeLineJoin(StrokeLineJoin paramStrokeLineJoin)
/*     */   {
/* 117 */     this.strokeLineJoin = paramStrokeLineJoin;
/* 118 */     __set(6);
/* 119 */     return this;
/*     */   }
/*     */ 
/*     */   public B strokeMiterLimit(double paramDouble)
/*     */   {
/* 128 */     this.strokeMiterLimit = paramDouble;
/* 129 */     __set(7);
/* 130 */     return this;
/*     */   }
/*     */ 
/*     */   public B strokeType(StrokeType paramStrokeType)
/*     */   {
/* 139 */     this.strokeType = paramStrokeType;
/* 140 */     __set(8);
/* 141 */     return this;
/*     */   }
/*     */ 
/*     */   public B strokeWidth(double paramDouble)
/*     */   {
/* 150 */     this.strokeWidth = paramDouble;
/* 151 */     __set(9);
/* 152 */     return this;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.shape.ShapeBuilder
 * JD-Core Version:    0.6.2
 */