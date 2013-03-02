/*    */ package javafx.scene.chart;
/*    */ 
/*    */ public final class NumberAxisBuilder extends ValueAxisBuilder<Number, NumberAxisBuilder>
/*    */ {
/*    */   private int __set;
/*    */   private boolean forceZeroInRange;
/*    */   private double tickUnit;
/*    */ 
/*    */   public static NumberAxisBuilder create()
/*    */   {
/* 15 */     return new NumberAxisBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(NumberAxis paramNumberAxis)
/*    */   {
/* 20 */     super.applyTo(paramNumberAxis);
/* 21 */     int i = this.__set;
/* 22 */     if ((i & 0x1) != 0) paramNumberAxis.setForceZeroInRange(this.forceZeroInRange);
/* 23 */     if ((i & 0x2) != 0) paramNumberAxis.setTickUnit(this.tickUnit);
/*    */   }
/*    */ 
/*    */   public NumberAxisBuilder forceZeroInRange(boolean paramBoolean)
/*    */   {
/* 31 */     this.forceZeroInRange = paramBoolean;
/* 32 */     this.__set |= 1;
/* 33 */     return this;
/*    */   }
/*    */ 
/*    */   public NumberAxisBuilder tickUnit(double paramDouble)
/*    */   {
/* 41 */     this.tickUnit = paramDouble;
/* 42 */     this.__set |= 2;
/* 43 */     return this;
/*    */   }
/*    */ 
/*    */   public NumberAxis build()
/*    */   {
/* 50 */     NumberAxis localNumberAxis = new NumberAxis();
/* 51 */     applyTo(localNumberAxis);
/* 52 */     return localNumberAxis;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.chart.NumberAxisBuilder
 * JD-Core Version:    0.6.2
 */