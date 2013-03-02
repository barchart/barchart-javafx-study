/*     */ package javafx.scene.chart;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.geometry.Side;
/*     */ import javafx.scene.layout.RegionBuilder;
/*     */ import javafx.scene.paint.Paint;
/*     */ import javafx.scene.text.Font;
/*     */ 
/*     */ public abstract class AxisBuilder<T, B extends AxisBuilder<T, B>> extends RegionBuilder<B>
/*     */ {
/*     */   private int __set;
/*     */   private boolean animated;
/*     */   private boolean autoRanging;
/*     */   private String label;
/*     */   private Side side;
/*     */   private Paint tickLabelFill;
/*     */   private Font tickLabelFont;
/*     */   private double tickLabelGap;
/*     */   private double tickLabelRotation;
/*     */   private boolean tickLabelsVisible;
/*     */   private double tickLength;
/*     */   private Collection<? extends Axis.TickMark<T>> tickMarks;
/*     */   private boolean tickMarkVisible;
/*     */ 
/*     */   private void __set(int paramInt)
/*     */   {
/*  15 */     this.__set |= 1 << paramInt;
/*     */   }
/*     */   public void applyTo(Axis<T> paramAxis) {
/*  18 */     super.applyTo(paramAxis);
/*  19 */     int i = this.__set;
/*  20 */     while (i != 0) {
/*  21 */       int j = Integer.numberOfTrailingZeros(i);
/*  22 */       i &= (1 << j ^ 0xFFFFFFFF);
/*  23 */       switch (j) { case 0:
/*  24 */         paramAxis.setAnimated(this.animated); break;
/*     */       case 1:
/*  25 */         paramAxis.setAutoRanging(this.autoRanging); break;
/*     */       case 2:
/*  26 */         paramAxis.setLabel(this.label); break;
/*     */       case 3:
/*  27 */         paramAxis.setSide(this.side); break;
/*     */       case 4:
/*  28 */         paramAxis.setTickLabelFill(this.tickLabelFill); break;
/*     */       case 5:
/*  29 */         paramAxis.setTickLabelFont(this.tickLabelFont); break;
/*     */       case 6:
/*  30 */         paramAxis.setTickLabelGap(this.tickLabelGap); break;
/*     */       case 7:
/*  31 */         paramAxis.setTickLabelRotation(this.tickLabelRotation); break;
/*     */       case 8:
/*  32 */         paramAxis.setTickLabelsVisible(this.tickLabelsVisible); break;
/*     */       case 9:
/*  33 */         paramAxis.setTickLength(this.tickLength); break;
/*     */       case 10:
/*  34 */         paramAxis.getTickMarks().addAll(this.tickMarks); break;
/*     */       case 11:
/*  35 */         paramAxis.setTickMarkVisible(this.tickMarkVisible);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public B animated(boolean paramBoolean)
/*     */   {
/*  46 */     this.animated = paramBoolean;
/*  47 */     __set(0);
/*  48 */     return this;
/*     */   }
/*     */ 
/*     */   public B autoRanging(boolean paramBoolean)
/*     */   {
/*  57 */     this.autoRanging = paramBoolean;
/*  58 */     __set(1);
/*  59 */     return this;
/*     */   }
/*     */ 
/*     */   public B label(String paramString)
/*     */   {
/*  68 */     this.label = paramString;
/*  69 */     __set(2);
/*  70 */     return this;
/*     */   }
/*     */ 
/*     */   public B side(Side paramSide)
/*     */   {
/*  79 */     this.side = paramSide;
/*  80 */     __set(3);
/*  81 */     return this;
/*     */   }
/*     */ 
/*     */   public B tickLabelFill(Paint paramPaint)
/*     */   {
/*  90 */     this.tickLabelFill = paramPaint;
/*  91 */     __set(4);
/*  92 */     return this;
/*     */   }
/*     */ 
/*     */   public B tickLabelFont(Font paramFont)
/*     */   {
/* 101 */     this.tickLabelFont = paramFont;
/* 102 */     __set(5);
/* 103 */     return this;
/*     */   }
/*     */ 
/*     */   public B tickLabelGap(double paramDouble)
/*     */   {
/* 112 */     this.tickLabelGap = paramDouble;
/* 113 */     __set(6);
/* 114 */     return this;
/*     */   }
/*     */ 
/*     */   public B tickLabelRotation(double paramDouble)
/*     */   {
/* 123 */     this.tickLabelRotation = paramDouble;
/* 124 */     __set(7);
/* 125 */     return this;
/*     */   }
/*     */ 
/*     */   public B tickLabelsVisible(boolean paramBoolean)
/*     */   {
/* 134 */     this.tickLabelsVisible = paramBoolean;
/* 135 */     __set(8);
/* 136 */     return this;
/*     */   }
/*     */ 
/*     */   public B tickLength(double paramDouble)
/*     */   {
/* 145 */     this.tickLength = paramDouble;
/* 146 */     __set(9);
/* 147 */     return this;
/*     */   }
/*     */ 
/*     */   public B tickMarks(Collection<? extends Axis.TickMark<T>> paramCollection)
/*     */   {
/* 156 */     this.tickMarks = paramCollection;
/* 157 */     __set(10);
/* 158 */     return this;
/*     */   }
/*     */ 
/*     */   public B tickMarks(Axis.TickMark<T>[] paramArrayOfTickMark)
/*     */   {
/* 165 */     return tickMarks(Arrays.asList(paramArrayOfTickMark));
/*     */   }
/*     */ 
/*     */   public B tickMarkVisible(boolean paramBoolean)
/*     */   {
/* 174 */     this.tickMarkVisible = paramBoolean;
/* 175 */     __set(11);
/* 176 */     return this;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.chart.AxisBuilder
 * JD-Core Version:    0.6.2
 */