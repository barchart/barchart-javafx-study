/*    */ package javafx.geometry;
/*    */ 
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class Dimension2DBuilder<B extends Dimension2DBuilder<B>>
/*    */   implements Builder<Dimension2D>
/*    */ {
/*    */   private double height;
/*    */   private double width;
/*    */ 
/*    */   public static Dimension2DBuilder<?> create()
/*    */   {
/* 15 */     return new Dimension2DBuilder();
/*    */   }
/*    */ 
/*    */   public B height(double paramDouble)
/*    */   {
/* 24 */     this.height = paramDouble;
/* 25 */     return this;
/*    */   }
/*    */ 
/*    */   public B width(double paramDouble)
/*    */   {
/* 34 */     this.width = paramDouble;
/* 35 */     return this;
/*    */   }
/*    */ 
/*    */   public Dimension2D build()
/*    */   {
/* 42 */     Dimension2D localDimension2D = new Dimension2D(this.width, this.height);
/* 43 */     return localDimension2D;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.geometry.Dimension2DBuilder
 * JD-Core Version:    0.6.2
 */