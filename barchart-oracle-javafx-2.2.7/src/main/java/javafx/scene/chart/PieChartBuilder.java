/*    */ package javafx.scene.chart;
/*    */ 
/*    */ import javafx.collections.ObservableList;
/*    */ 
/*    */ public class PieChartBuilder<B extends PieChartBuilder<B>> extends ChartBuilder<B>
/*    */ {
/*    */   private int __set;
/*    */   private boolean clockwise;
/*    */   private ObservableList<PieChart.Data> data;
/*    */   private double labelLineLength;
/*    */   private boolean labelsVisible;
/*    */   private double startAngle;
/*    */ 
/*    */   public static PieChartBuilder<?> create()
/*    */   {
/* 15 */     return new PieChartBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(PieChart paramPieChart)
/*    */   {
/* 20 */     super.applyTo(paramPieChart);
/* 21 */     int i = this.__set;
/* 22 */     if ((i & 0x1) != 0) paramPieChart.setClockwise(this.clockwise);
/* 23 */     if ((i & 0x2) != 0) paramPieChart.setData(this.data);
/* 24 */     if ((i & 0x4) != 0) paramPieChart.setLabelLineLength(this.labelLineLength);
/* 25 */     if ((i & 0x8) != 0) paramPieChart.setLabelsVisible(this.labelsVisible);
/* 26 */     if ((i & 0x10) != 0) paramPieChart.setStartAngle(this.startAngle);
/*    */   }
/*    */ 
/*    */   public B clockwise(boolean paramBoolean)
/*    */   {
/* 35 */     this.clockwise = paramBoolean;
/* 36 */     this.__set |= 1;
/* 37 */     return this;
/*    */   }
/*    */ 
/*    */   public B data(ObservableList<PieChart.Data> paramObservableList)
/*    */   {
/* 46 */     this.data = paramObservableList;
/* 47 */     this.__set |= 2;
/* 48 */     return this;
/*    */   }
/*    */ 
/*    */   public B labelLineLength(double paramDouble)
/*    */   {
/* 57 */     this.labelLineLength = paramDouble;
/* 58 */     this.__set |= 4;
/* 59 */     return this;
/*    */   }
/*    */ 
/*    */   public B labelsVisible(boolean paramBoolean)
/*    */   {
/* 68 */     this.labelsVisible = paramBoolean;
/* 69 */     this.__set |= 8;
/* 70 */     return this;
/*    */   }
/*    */ 
/*    */   public B startAngle(double paramDouble)
/*    */   {
/* 79 */     this.startAngle = paramDouble;
/* 80 */     this.__set |= 16;
/* 81 */     return this;
/*    */   }
/*    */ 
/*    */   public PieChart build()
/*    */   {
/* 88 */     PieChart localPieChart = new PieChart();
/* 89 */     applyTo(localPieChart);
/* 90 */     return localPieChart;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.chart.PieChartBuilder
 * JD-Core Version:    0.6.2
 */