/*    */ package javafx.scene.chart;
/*    */ 
/*    */ public class StackedBarChartBuilder<X, Y, B extends StackedBarChartBuilder<X, Y, B>> extends XYChartBuilder<X, Y, B>
/*    */ {
/*    */   private boolean __set;
/*    */   private double categoryGap;
/*    */   private Axis<X> XAxis;
/*    */   private Axis<Y> YAxis;
/*    */ 
/*    */   public static <X, Y> StackedBarChartBuilder<X, Y, ?> create()
/*    */   {
/* 15 */     return new StackedBarChartBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(StackedBarChart<X, Y> paramStackedBarChart)
/*    */   {
/* 20 */     super.applyTo(paramStackedBarChart);
/* 21 */     if (this.__set) paramStackedBarChart.setCategoryGap(this.categoryGap);
/*    */   }
/*    */ 
/*    */   public B categoryGap(double paramDouble)
/*    */   {
/* 30 */     this.categoryGap = paramDouble;
/* 31 */     this.__set = true;
/* 32 */     return this;
/*    */   }
/*    */ 
/*    */   public B XAxis(Axis<X> paramAxis)
/*    */   {
/* 41 */     this.XAxis = paramAxis;
/* 42 */     return this;
/*    */   }
/*    */ 
/*    */   public B YAxis(Axis<Y> paramAxis)
/*    */   {
/* 51 */     this.YAxis = paramAxis;
/* 52 */     return this;
/*    */   }
/*    */ 
/*    */   public StackedBarChart<X, Y> build()
/*    */   {
/* 59 */     StackedBarChart localStackedBarChart = new StackedBarChart(this.XAxis, this.YAxis);
/* 60 */     applyTo(localStackedBarChart);
/* 61 */     return localStackedBarChart;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.chart.StackedBarChartBuilder
 * JD-Core Version:    0.6.2
 */