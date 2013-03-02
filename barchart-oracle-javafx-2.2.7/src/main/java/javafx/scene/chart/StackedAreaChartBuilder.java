/*    */ package javafx.scene.chart;
/*    */ 
/*    */ public class StackedAreaChartBuilder<X, Y, B extends StackedAreaChartBuilder<X, Y, B>> extends XYChartBuilder<X, Y, B>
/*    */ {
/*    */   private Axis<X> XAxis;
/*    */   private Axis<Y> YAxis;
/*    */ 
/*    */   public static <X, Y> StackedAreaChartBuilder<X, Y, ?> create()
/*    */   {
/* 15 */     return new StackedAreaChartBuilder();
/*    */   }
/*    */ 
/*    */   public B XAxis(Axis<X> paramAxis)
/*    */   {
/* 24 */     this.XAxis = paramAxis;
/* 25 */     return this;
/*    */   }
/*    */ 
/*    */   public B YAxis(Axis<Y> paramAxis)
/*    */   {
/* 34 */     this.YAxis = paramAxis;
/* 35 */     return this;
/*    */   }
/*    */ 
/*    */   public StackedAreaChart<X, Y> build()
/*    */   {
/* 42 */     StackedAreaChart localStackedAreaChart = new StackedAreaChart(this.XAxis, this.YAxis);
/* 43 */     applyTo(localStackedAreaChart);
/* 44 */     return localStackedAreaChart;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.chart.StackedAreaChartBuilder
 * JD-Core Version:    0.6.2
 */