/*    */ package javafx.scene.chart;
/*    */ 
/*    */ public class LineChartBuilder<X, Y, B extends LineChartBuilder<X, Y, B>> extends XYChartBuilder<X, Y, B>
/*    */ {
/*    */   private boolean __set;
/*    */   private boolean createSymbols;
/*    */   private Axis<X> XAxis;
/*    */   private Axis<Y> YAxis;
/*    */ 
/*    */   public static <X, Y> LineChartBuilder<X, Y, ?> create()
/*    */   {
/* 15 */     return new LineChartBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(LineChart<X, Y> paramLineChart)
/*    */   {
/* 20 */     super.applyTo(paramLineChart);
/* 21 */     if (this.__set) paramLineChart.setCreateSymbols(this.createSymbols);
/*    */   }
/*    */ 
/*    */   public B createSymbols(boolean paramBoolean)
/*    */   {
/* 30 */     this.createSymbols = paramBoolean;
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
/*    */   public LineChart<X, Y> build()
/*    */   {
/* 59 */     LineChart localLineChart = new LineChart(this.XAxis, this.YAxis);
/* 60 */     applyTo(localLineChart);
/* 61 */     return localLineChart;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.chart.LineChartBuilder
 * JD-Core Version:    0.6.2
 */