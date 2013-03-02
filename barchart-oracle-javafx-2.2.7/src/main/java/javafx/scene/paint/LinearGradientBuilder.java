/*    */ package javafx.scene.paint;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public final class LinearGradientBuilder
/*    */   implements Builder<LinearGradient>
/*    */ {
/*    */   private CycleMethod cycleMethod;
/* 27 */   private double endX = 1.0D;
/*    */ 
/* 36 */   private double endY = 1.0D;
/*    */ 
/* 45 */   private boolean proportional = true;
/*    */   private double startX;
/*    */   private double startY;
/*    */   private List<Stop> stops;
/*    */ 
/*    */   public static LinearGradientBuilder create()
/*    */   {
/* 15 */     return new LinearGradientBuilder();
/*    */   }
/*    */ 
/*    */   public LinearGradientBuilder cycleMethod(CycleMethod paramCycleMethod)
/*    */   {
/* 23 */     this.cycleMethod = paramCycleMethod;
/* 24 */     return this;
/*    */   }
/*    */ 
/*    */   public LinearGradientBuilder endX(double paramDouble)
/*    */   {
/* 32 */     this.endX = paramDouble;
/* 33 */     return this;
/*    */   }
/*    */ 
/*    */   public LinearGradientBuilder endY(double paramDouble)
/*    */   {
/* 41 */     this.endY = paramDouble;
/* 42 */     return this;
/*    */   }
/*    */ 
/*    */   public LinearGradientBuilder proportional(boolean paramBoolean)
/*    */   {
/* 50 */     this.proportional = paramBoolean;
/* 51 */     return this;
/*    */   }
/*    */ 
/*    */   public LinearGradientBuilder startX(double paramDouble)
/*    */   {
/* 59 */     this.startX = paramDouble;
/* 60 */     return this;
/*    */   }
/*    */ 
/*    */   public LinearGradientBuilder startY(double paramDouble)
/*    */   {
/* 68 */     this.startY = paramDouble;
/* 69 */     return this;
/*    */   }
/*    */ 
/*    */   public LinearGradientBuilder stops(List<Stop> paramList)
/*    */   {
/* 77 */     this.stops = paramList;
/* 78 */     return this;
/*    */   }
/*    */ 
/*    */   public LinearGradientBuilder stops(Stop[] paramArrayOfStop)
/*    */   {
/* 85 */     return stops(Arrays.asList(paramArrayOfStop));
/*    */   }
/*    */ 
/*    */   public LinearGradient build()
/*    */   {
/* 92 */     LinearGradient localLinearGradient = new LinearGradient(this.startX, this.startY, this.endX, this.endY, this.proportional, this.cycleMethod, this.stops);
/* 93 */     return localLinearGradient;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.paint.LinearGradientBuilder
 * JD-Core Version:    0.6.2
 */