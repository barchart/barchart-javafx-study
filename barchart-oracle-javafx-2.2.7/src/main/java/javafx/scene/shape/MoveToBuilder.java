/*    */ package javafx.scene.shape;
/*    */ 
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class MoveToBuilder<B extends MoveToBuilder<B>> extends PathElementBuilder<B>
/*    */   implements Builder<MoveTo>
/*    */ {
/*    */   private int __set;
/*    */   private double x;
/*    */   private double y;
/*    */ 
/*    */   public static MoveToBuilder<?> create()
/*    */   {
/* 15 */     return new MoveToBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(MoveTo paramMoveTo)
/*    */   {
/* 20 */     super.applyTo(paramMoveTo);
/* 21 */     int i = this.__set;
/* 22 */     if ((i & 0x1) != 0) paramMoveTo.setX(this.x);
/* 23 */     if ((i & 0x2) != 0) paramMoveTo.setY(this.y);
/*    */   }
/*    */ 
/*    */   public B x(double paramDouble)
/*    */   {
/* 32 */     this.x = paramDouble;
/* 33 */     this.__set |= 1;
/* 34 */     return this;
/*    */   }
/*    */ 
/*    */   public B y(double paramDouble)
/*    */   {
/* 43 */     this.y = paramDouble;
/* 44 */     this.__set |= 2;
/* 45 */     return this;
/*    */   }
/*    */ 
/*    */   public MoveTo build()
/*    */   {
/* 52 */     MoveTo localMoveTo = new MoveTo();
/* 53 */     applyTo(localMoveTo);
/* 54 */     return localMoveTo;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.shape.MoveToBuilder
 * JD-Core Version:    0.6.2
 */