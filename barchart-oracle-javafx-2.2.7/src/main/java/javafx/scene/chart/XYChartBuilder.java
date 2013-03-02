/*     */ package javafx.scene.chart;
/*     */ 
/*     */ import javafx.collections.ObservableList;
/*     */ 
/*     */ public abstract class XYChartBuilder<X, Y, B extends XYChartBuilder<X, Y, B>> extends ChartBuilder<B>
/*     */ {
/*     */   private int __set;
/*     */   private boolean alternativeColumnFillVisible;
/*     */   private boolean alternativeRowFillVisible;
/*     */   private ObservableList<XYChart.Series<X, Y>> data;
/*     */   private boolean horizontalGridLinesVisible;
/*     */   private boolean horizontalZeroLineVisible;
/*     */   private boolean verticalGridLinesVisible;
/*     */   private boolean verticalZeroLineVisible;
/*     */   private Axis<X> XAxis;
/*     */   private Axis<Y> YAxis;
/*     */ 
/*     */   public void applyTo(XYChart<X, Y> paramXYChart)
/*     */   {
/*  15 */     super.applyTo(paramXYChart);
/*  16 */     int i = this.__set;
/*  17 */     if ((i & 0x1) != 0) paramXYChart.setAlternativeColumnFillVisible(this.alternativeColumnFillVisible);
/*  18 */     if ((i & 0x2) != 0) paramXYChart.setAlternativeRowFillVisible(this.alternativeRowFillVisible);
/*  19 */     if ((i & 0x4) != 0) paramXYChart.setData(this.data);
/*  20 */     if ((i & 0x8) != 0) paramXYChart.setHorizontalGridLinesVisible(this.horizontalGridLinesVisible);
/*  21 */     if ((i & 0x10) != 0) paramXYChart.setHorizontalZeroLineVisible(this.horizontalZeroLineVisible);
/*  22 */     if ((i & 0x20) != 0) paramXYChart.setVerticalGridLinesVisible(this.verticalGridLinesVisible);
/*  23 */     if ((i & 0x40) != 0) paramXYChart.setVerticalZeroLineVisible(this.verticalZeroLineVisible);
/*     */   }
/*     */ 
/*     */   public B alternativeColumnFillVisible(boolean paramBoolean)
/*     */   {
/*  32 */     this.alternativeColumnFillVisible = paramBoolean;
/*  33 */     this.__set |= 1;
/*  34 */     return this;
/*     */   }
/*     */ 
/*     */   public B alternativeRowFillVisible(boolean paramBoolean)
/*     */   {
/*  43 */     this.alternativeRowFillVisible = paramBoolean;
/*  44 */     this.__set |= 2;
/*  45 */     return this;
/*     */   }
/*     */ 
/*     */   public B data(ObservableList<XYChart.Series<X, Y>> paramObservableList)
/*     */   {
/*  54 */     this.data = paramObservableList;
/*  55 */     this.__set |= 4;
/*  56 */     return this;
/*     */   }
/*     */ 
/*     */   public B horizontalGridLinesVisible(boolean paramBoolean)
/*     */   {
/*  65 */     this.horizontalGridLinesVisible = paramBoolean;
/*  66 */     this.__set |= 8;
/*  67 */     return this;
/*     */   }
/*     */ 
/*     */   public B horizontalZeroLineVisible(boolean paramBoolean)
/*     */   {
/*  76 */     this.horizontalZeroLineVisible = paramBoolean;
/*  77 */     this.__set |= 16;
/*  78 */     return this;
/*     */   }
/*     */ 
/*     */   public B verticalGridLinesVisible(boolean paramBoolean)
/*     */   {
/*  87 */     this.verticalGridLinesVisible = paramBoolean;
/*  88 */     this.__set |= 32;
/*  89 */     return this;
/*     */   }
/*     */ 
/*     */   public B verticalZeroLineVisible(boolean paramBoolean)
/*     */   {
/*  98 */     this.verticalZeroLineVisible = paramBoolean;
/*  99 */     this.__set |= 64;
/* 100 */     return this;
/*     */   }
/*     */ 
/*     */   public B XAxis(Axis<X> paramAxis)
/*     */   {
/* 109 */     this.XAxis = paramAxis;
/* 110 */     return this;
/*     */   }
/*     */ 
/*     */   public B YAxis(Axis<Y> paramAxis)
/*     */   {
/* 119 */     this.YAxis = paramAxis;
/* 120 */     return this;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.chart.XYChartBuilder
 * JD-Core Version:    0.6.2
 */