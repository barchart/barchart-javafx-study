/*    */ package javafx.scene.chart;
/*    */ 
/*    */ public class BubbleChartBuilder<X, Y, B extends BubbleChartBuilder<X, Y, B>> extends XYChartBuilder<X, Y, B>
/*    */ {
/*    */   private Axis<X> XAxis;
/*    */   private Axis<Y> YAxis;
/*    */ 
/*    */   public static <X, Y> BubbleChartBuilder<X, Y, ?> create()
/*    */   {
/* 15 */     return new BubbleChartBuilder();
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
/*    */   public BubbleChart<X, Y> build()
/*    */   {
/* 42 */     BubbleChart localBubbleChart = new BubbleChart(this.XAxis, this.YAxis);
/* 43 */     applyTo(localBubbleChart);
/* 44 */     return localBubbleChart;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.chart.BubbleChartBuilder
 * JD-Core Version:    0.6.2
 */