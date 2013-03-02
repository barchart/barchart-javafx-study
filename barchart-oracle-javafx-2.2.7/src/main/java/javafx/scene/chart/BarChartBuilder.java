/*    */ package javafx.scene.chart;
/*    */ 
/*    */ public class BarChartBuilder<X, Y, B extends BarChartBuilder<X, Y, B>> extends XYChartBuilder<X, Y, B>
/*    */ {
/*    */   private int __set;
/*    */   private double barGap;
/*    */   private double categoryGap;
/*    */   private Axis<X> XAxis;
/*    */   private Axis<Y> YAxis;
/*    */ 
/*    */   public static <X, Y> BarChartBuilder<X, Y, ?> create()
/*    */   {
/* 15 */     return new BarChartBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(BarChart<X, Y> paramBarChart)
/*    */   {
/* 20 */     super.applyTo(paramBarChart);
/* 21 */     int i = this.__set;
/* 22 */     if ((i & 0x1) != 0) paramBarChart.setBarGap(this.barGap);
/* 23 */     if ((i & 0x2) != 0) paramBarChart.setCategoryGap(this.categoryGap);
/*    */   }
/*    */ 
/*    */   public B barGap(double paramDouble)
/*    */   {
/* 32 */     this.barGap = paramDouble;
/* 33 */     this.__set |= 1;
/* 34 */     return this;
/*    */   }
/*    */ 
/*    */   public B categoryGap(double paramDouble)
/*    */   {
/* 43 */     this.categoryGap = paramDouble;
/* 44 */     this.__set |= 2;
/* 45 */     return this;
/*    */   }
/*    */ 
/*    */   public B XAxis(Axis<X> paramAxis)
/*    */   {
/* 54 */     this.XAxis = paramAxis;
/* 55 */     return this;
/*    */   }
/*    */ 
/*    */   public B YAxis(Axis<Y> paramAxis)
/*    */   {
/* 64 */     this.YAxis = paramAxis;
/* 65 */     return this;
/*    */   }
/*    */ 
/*    */   public BarChart<X, Y> build()
/*    */   {
/* 72 */     BarChart localBarChart = new BarChart(this.XAxis, this.YAxis);
/* 73 */     applyTo(localBarChart);
/* 74 */     return localBarChart;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.chart.BarChartBuilder
 * JD-Core Version:    0.6.2
 */