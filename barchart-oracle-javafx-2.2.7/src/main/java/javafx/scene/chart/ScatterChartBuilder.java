/*    */ package javafx.scene.chart;
/*    */ 
/*    */ public class ScatterChartBuilder<X, Y, B extends ScatterChartBuilder<X, Y, B>> extends XYChartBuilder<X, Y, B>
/*    */ {
/*    */   private Axis<X> XAxis;
/*    */   private Axis<Y> YAxis;
/*    */ 
/*    */   public static <X, Y> ScatterChartBuilder<X, Y, ?> create()
/*    */   {
/* 15 */     return new ScatterChartBuilder();
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
/*    */   public ScatterChart<X, Y> build()
/*    */   {
/* 42 */     ScatterChart localScatterChart = new ScatterChart(this.XAxis, this.YAxis);
/* 43 */     applyTo(localScatterChart);
/* 44 */     return localScatterChart;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.chart.ScatterChartBuilder
 * JD-Core Version:    0.6.2
 */