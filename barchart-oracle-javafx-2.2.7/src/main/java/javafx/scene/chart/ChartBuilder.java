/*    */ package javafx.scene.chart;
/*    */ 
/*    */ import javafx.geometry.Side;
/*    */ import javafx.scene.layout.RegionBuilder;
/*    */ 
/*    */ public abstract class ChartBuilder<B extends ChartBuilder<B>> extends RegionBuilder<B>
/*    */ {
/*    */   private int __set;
/*    */   private boolean animated;
/*    */   private Side legendSide;
/*    */   private boolean legendVisible;
/*    */   private String title;
/*    */   private Side titleSide;
/*    */ 
/*    */   public void applyTo(Chart paramChart)
/*    */   {
/* 15 */     super.applyTo(paramChart);
/* 16 */     int i = this.__set;
/* 17 */     if ((i & 0x1) != 0) paramChart.setAnimated(this.animated);
/* 18 */     if ((i & 0x2) != 0) paramChart.setLegendSide(this.legendSide);
/* 19 */     if ((i & 0x4) != 0) paramChart.setLegendVisible(this.legendVisible);
/* 20 */     if ((i & 0x8) != 0) paramChart.setTitle(this.title);
/* 21 */     if ((i & 0x10) != 0) paramChart.setTitleSide(this.titleSide);
/*    */   }
/*    */ 
/*    */   public B animated(boolean paramBoolean)
/*    */   {
/* 30 */     this.animated = paramBoolean;
/* 31 */     this.__set |= 1;
/* 32 */     return this;
/*    */   }
/*    */ 
/*    */   public B legendSide(Side paramSide)
/*    */   {
/* 41 */     this.legendSide = paramSide;
/* 42 */     this.__set |= 2;
/* 43 */     return this;
/*    */   }
/*    */ 
/*    */   public B legendVisible(boolean paramBoolean)
/*    */   {
/* 52 */     this.legendVisible = paramBoolean;
/* 53 */     this.__set |= 4;
/* 54 */     return this;
/*    */   }
/*    */ 
/*    */   public B title(String paramString)
/*    */   {
/* 63 */     this.title = paramString;
/* 64 */     this.__set |= 8;
/* 65 */     return this;
/*    */   }
/*    */ 
/*    */   public B titleSide(Side paramSide)
/*    */   {
/* 74 */     this.titleSide = paramSide;
/* 75 */     this.__set |= 16;
/* 76 */     return this;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.chart.ChartBuilder
 * JD-Core Version:    0.6.2
 */