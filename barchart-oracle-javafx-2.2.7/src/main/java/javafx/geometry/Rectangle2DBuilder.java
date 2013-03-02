/*    */ package javafx.geometry;
/*    */ 
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class Rectangle2DBuilder<B extends Rectangle2DBuilder<B>>
/*    */   implements Builder<Rectangle2D>
/*    */ {
/*    */   private double height;
/*    */   private double minX;
/*    */   private double minY;
/*    */   private double width;
/*    */ 
/*    */   public static Rectangle2DBuilder<?> create()
/*    */   {
/* 15 */     return new Rectangle2DBuilder();
/*    */   }
/*    */ 
/*    */   public B height(double paramDouble)
/*    */   {
/* 24 */     this.height = paramDouble;
/* 25 */     return this;
/*    */   }
/*    */ 
/*    */   public B minX(double paramDouble)
/*    */   {
/* 34 */     this.minX = paramDouble;
/* 35 */     return this;
/*    */   }
/*    */ 
/*    */   public B minY(double paramDouble)
/*    */   {
/* 44 */     this.minY = paramDouble;
/* 45 */     return this;
/*    */   }
/*    */ 
/*    */   public B width(double paramDouble)
/*    */   {
/* 54 */     this.width = paramDouble;
/* 55 */     return this;
/*    */   }
/*    */ 
/*    */   public Rectangle2D build()
/*    */   {
/* 62 */     Rectangle2D localRectangle2D = new Rectangle2D(this.minX, this.minY, this.width, this.height);
/* 63 */     return localRectangle2D;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.geometry.Rectangle2DBuilder
 * JD-Core Version:    0.6.2
 */