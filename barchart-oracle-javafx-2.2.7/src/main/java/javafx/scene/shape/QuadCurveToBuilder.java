/*    */ package javafx.scene.shape;
/*    */ 
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class QuadCurveToBuilder<B extends QuadCurveToBuilder<B>> extends PathElementBuilder<B>
/*    */   implements Builder<QuadCurveTo>
/*    */ {
/*    */   private int __set;
/*    */   private double controlX;
/*    */   private double controlY;
/*    */   private double x;
/*    */   private double y;
/*    */ 
/*    */   public static QuadCurveToBuilder<?> create()
/*    */   {
/* 15 */     return new QuadCurveToBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(QuadCurveTo paramQuadCurveTo)
/*    */   {
/* 20 */     super.applyTo(paramQuadCurveTo);
/* 21 */     int i = this.__set;
/* 22 */     if ((i & 0x1) != 0) paramQuadCurveTo.setControlX(this.controlX);
/* 23 */     if ((i & 0x2) != 0) paramQuadCurveTo.setControlY(this.controlY);
/* 24 */     if ((i & 0x4) != 0) paramQuadCurveTo.setX(this.x);
/* 25 */     if ((i & 0x8) != 0) paramQuadCurveTo.setY(this.y);
/*    */   }
/*    */ 
/*    */   public B controlX(double paramDouble)
/*    */   {
/* 34 */     this.controlX = paramDouble;
/* 35 */     this.__set |= 1;
/* 36 */     return this;
/*    */   }
/*    */ 
/*    */   public B controlY(double paramDouble)
/*    */   {
/* 45 */     this.controlY = paramDouble;
/* 46 */     this.__set |= 2;
/* 47 */     return this;
/*    */   }
/*    */ 
/*    */   public B x(double paramDouble)
/*    */   {
/* 56 */     this.x = paramDouble;
/* 57 */     this.__set |= 4;
/* 58 */     return this;
/*    */   }
/*    */ 
/*    */   public B y(double paramDouble)
/*    */   {
/* 67 */     this.y = paramDouble;
/* 68 */     this.__set |= 8;
/* 69 */     return this;
/*    */   }
/*    */ 
/*    */   public QuadCurveTo build()
/*    */   {
/* 76 */     QuadCurveTo localQuadCurveTo = new QuadCurveTo();
/* 77 */     applyTo(localQuadCurveTo);
/* 78 */     return localQuadCurveTo;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.shape.QuadCurveToBuilder
 * JD-Core Version:    0.6.2
 */