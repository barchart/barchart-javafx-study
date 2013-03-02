/*    */ package javafx.scene.chart;
/*    */ 
/*    */ import javafx.util.StringConverter;
/*    */ 
/*    */ public abstract class ValueAxisBuilder<T extends Number, B extends ValueAxisBuilder<T, B>> extends AxisBuilder<T, B>
/*    */ {
/*    */   private int __set;
/*    */   private double lowerBound;
/*    */   private int minorTickCount;
/*    */   private double minorTickLength;
/*    */   private boolean minorTickVisible;
/*    */   private StringConverter<T> tickLabelFormatter;
/*    */   private double upperBound;
/*    */ 
/*    */   public void applyTo(ValueAxis<T> paramValueAxis)
/*    */   {
/* 15 */     super.applyTo(paramValueAxis);
/* 16 */     int i = this.__set;
/* 17 */     if ((i & 0x1) != 0) paramValueAxis.setLowerBound(this.lowerBound);
/* 18 */     if ((i & 0x2) != 0) paramValueAxis.setMinorTickCount(this.minorTickCount);
/* 19 */     if ((i & 0x4) != 0) paramValueAxis.setMinorTickLength(this.minorTickLength);
/* 20 */     if ((i & 0x8) != 0) paramValueAxis.setMinorTickVisible(this.minorTickVisible);
/* 21 */     if ((i & 0x10) != 0) paramValueAxis.setTickLabelFormatter(this.tickLabelFormatter);
/* 22 */     if ((i & 0x20) != 0) paramValueAxis.setUpperBound(this.upperBound);
/*    */   }
/*    */ 
/*    */   public B lowerBound(double paramDouble)
/*    */   {
/* 31 */     this.lowerBound = paramDouble;
/* 32 */     this.__set |= 1;
/* 33 */     return this;
/*    */   }
/*    */ 
/*    */   public B minorTickCount(int paramInt)
/*    */   {
/* 42 */     this.minorTickCount = paramInt;
/* 43 */     this.__set |= 2;
/* 44 */     return this;
/*    */   }
/*    */ 
/*    */   public B minorTickLength(double paramDouble)
/*    */   {
/* 53 */     this.minorTickLength = paramDouble;
/* 54 */     this.__set |= 4;
/* 55 */     return this;
/*    */   }
/*    */ 
/*    */   public B minorTickVisible(boolean paramBoolean)
/*    */   {
/* 64 */     this.minorTickVisible = paramBoolean;
/* 65 */     this.__set |= 8;
/* 66 */     return this;
/*    */   }
/*    */ 
/*    */   public B tickLabelFormatter(StringConverter<T> paramStringConverter)
/*    */   {
/* 75 */     this.tickLabelFormatter = paramStringConverter;
/* 76 */     this.__set |= 16;
/* 77 */     return this;
/*    */   }
/*    */ 
/*    */   public B upperBound(double paramDouble)
/*    */   {
/* 86 */     this.upperBound = paramDouble;
/* 87 */     this.__set |= 32;
/* 88 */     return this;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.chart.ValueAxisBuilder
 * JD-Core Version:    0.6.2
 */