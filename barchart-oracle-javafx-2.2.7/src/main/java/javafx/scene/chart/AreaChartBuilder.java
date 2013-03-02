/*    */ package javafx.scene.chart;
/*    */ 
/*    */ public class AreaChartBuilder<X, Y, B extends AreaChartBuilder<X, Y, B>> extends XYChartBuilder<X, Y, B>
/*    */ {
/*    */   private Axis<X> XAxis;
/*    */   private Axis<Y> YAxis;
/*    */ 
/*    */   public static <X, Y> AreaChartBuilder<X, Y, ?> create()
/*    */   {
/* 15 */     return new AreaChartBuilder();
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
/*    */   public AreaChart<X, Y> build()
/*    */   {
/* 42 */     AreaChart localAreaChart = new AreaChart(this.XAxis, this.YAxis);
/* 43 */     applyTo(localAreaChart);
/* 44 */     return localAreaChart;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.chart.AreaChartBuilder
 * JD-Core Version:    0.6.2
 */