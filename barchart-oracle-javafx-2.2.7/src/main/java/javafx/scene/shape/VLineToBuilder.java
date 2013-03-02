/*    */ package javafx.scene.shape;
/*    */ 
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class VLineToBuilder<B extends VLineToBuilder<B>> extends PathElementBuilder<B>
/*    */   implements Builder<VLineTo>
/*    */ {
/*    */   private boolean __set;
/*    */   private double y;
/*    */ 
/*    */   public static VLineToBuilder<?> create()
/*    */   {
/* 15 */     return new VLineToBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(VLineTo paramVLineTo)
/*    */   {
/* 20 */     super.applyTo(paramVLineTo);
/* 21 */     if (this.__set) paramVLineTo.setY(this.y);
/*    */   }
/*    */ 
/*    */   public B y(double paramDouble)
/*    */   {
/* 30 */     this.y = paramDouble;
/* 31 */     this.__set = true;
/* 32 */     return this;
/*    */   }
/*    */ 
/*    */   public VLineTo build()
/*    */   {
/* 39 */     VLineTo localVLineTo = new VLineTo();
/* 40 */     applyTo(localVLineTo);
/* 41 */     return localVLineTo;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.shape.VLineToBuilder
 * JD-Core Version:    0.6.2
 */