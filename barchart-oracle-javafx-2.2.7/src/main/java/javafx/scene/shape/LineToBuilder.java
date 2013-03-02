/*    */ package javafx.scene.shape;
/*    */ 
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class LineToBuilder<B extends LineToBuilder<B>> extends PathElementBuilder<B>
/*    */   implements Builder<LineTo>
/*    */ {
/*    */   private int __set;
/*    */   private double x;
/*    */   private double y;
/*    */ 
/*    */   public static LineToBuilder<?> create()
/*    */   {
/* 15 */     return new LineToBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(LineTo paramLineTo)
/*    */   {
/* 20 */     super.applyTo(paramLineTo);
/* 21 */     int i = this.__set;
/* 22 */     if ((i & 0x1) != 0) paramLineTo.setX(this.x);
/* 23 */     if ((i & 0x2) != 0) paramLineTo.setY(this.y);
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
/*    */   public LineTo build()
/*    */   {
/* 52 */     LineTo localLineTo = new LineTo();
/* 53 */     applyTo(localLineTo);
/* 54 */     return localLineTo;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.shape.LineToBuilder
 * JD-Core Version:    0.6.2
 */