/*    */ package javafx.scene.paint;
/*    */ 
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public final class StopBuilder
/*    */   implements Builder<Stop>
/*    */ {
/* 18 */   private Color color = Color.BLACK;
/*    */   private double offset;
/*    */ 
/*    */   public static StopBuilder create()
/*    */   {
/* 15 */     return new StopBuilder();
/*    */   }
/*    */ 
/*    */   public StopBuilder color(Color paramColor)
/*    */   {
/* 23 */     this.color = paramColor;
/* 24 */     return this;
/*    */   }
/*    */ 
/*    */   public StopBuilder offset(double paramDouble)
/*    */   {
/* 32 */     this.offset = paramDouble;
/* 33 */     return this;
/*    */   }
/*    */ 
/*    */   public Stop build()
/*    */   {
/* 40 */     Stop localStop = new Stop(this.offset, this.color);
/* 41 */     return localStop;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.paint.StopBuilder
 * JD-Core Version:    0.6.2
 */