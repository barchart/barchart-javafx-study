/*    */ package javafx.scene.shape;
/*    */ 
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class HLineToBuilder<B extends HLineToBuilder<B>> extends PathElementBuilder<B>
/*    */   implements Builder<HLineTo>
/*    */ {
/*    */   private boolean __set;
/*    */   private double x;
/*    */ 
/*    */   public static HLineToBuilder<?> create()
/*    */   {
/* 15 */     return new HLineToBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(HLineTo paramHLineTo)
/*    */   {
/* 20 */     super.applyTo(paramHLineTo);
/* 21 */     if (this.__set) paramHLineTo.setX(this.x);
/*    */   }
/*    */ 
/*    */   public B x(double paramDouble)
/*    */   {
/* 30 */     this.x = paramDouble;
/* 31 */     this.__set = true;
/* 32 */     return this;
/*    */   }
/*    */ 
/*    */   public HLineTo build()
/*    */   {
/* 39 */     HLineTo localHLineTo = new HLineTo();
/* 40 */     applyTo(localHLineTo);
/* 41 */     return localHLineTo;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.shape.HLineToBuilder
 * JD-Core Version:    0.6.2
 */