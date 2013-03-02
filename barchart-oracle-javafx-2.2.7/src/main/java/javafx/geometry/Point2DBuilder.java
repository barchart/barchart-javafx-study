/*    */ package javafx.geometry;
/*    */ 
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class Point2DBuilder<B extends Point2DBuilder<B>>
/*    */   implements Builder<Point2D>
/*    */ {
/*    */   private double x;
/*    */   private double y;
/*    */ 
/*    */   public static Point2DBuilder<?> create()
/*    */   {
/* 15 */     return new Point2DBuilder();
/*    */   }
/*    */ 
/*    */   public B x(double paramDouble)
/*    */   {
/* 24 */     this.x = paramDouble;
/* 25 */     return this;
/*    */   }
/*    */ 
/*    */   public B y(double paramDouble)
/*    */   {
/* 34 */     this.y = paramDouble;
/* 35 */     return this;
/*    */   }
/*    */ 
/*    */   public Point2D build()
/*    */   {
/* 42 */     Point2D localPoint2D = new Point2D(this.x, this.y);
/* 43 */     return localPoint2D;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.geometry.Point2DBuilder
 * JD-Core Version:    0.6.2
 */