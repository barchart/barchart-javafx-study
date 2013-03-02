/*    */ package javafx.scene.shape;
/*    */ 
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class EllipseBuilder<B extends EllipseBuilder<B>> extends ShapeBuilder<B>
/*    */   implements Builder<Ellipse>
/*    */ {
/*    */   private int __set;
/*    */   private double centerX;
/*    */   private double centerY;
/*    */   private double radiusX;
/*    */   private double radiusY;
/*    */ 
/*    */   public static EllipseBuilder<?> create()
/*    */   {
/* 15 */     return new EllipseBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(Ellipse paramEllipse)
/*    */   {
/* 20 */     super.applyTo(paramEllipse);
/* 21 */     int i = this.__set;
/* 22 */     if ((i & 0x1) != 0) paramEllipse.setCenterX(this.centerX);
/* 23 */     if ((i & 0x2) != 0) paramEllipse.setCenterY(this.centerY);
/* 24 */     if ((i & 0x4) != 0) paramEllipse.setRadiusX(this.radiusX);
/* 25 */     if ((i & 0x8) != 0) paramEllipse.setRadiusY(this.radiusY);
/*    */   }
/*    */ 
/*    */   public B centerX(double paramDouble)
/*    */   {
/* 34 */     this.centerX = paramDouble;
/* 35 */     this.__set |= 1;
/* 36 */     return this;
/*    */   }
/*    */ 
/*    */   public B centerY(double paramDouble)
/*    */   {
/* 45 */     this.centerY = paramDouble;
/* 46 */     this.__set |= 2;
/* 47 */     return this;
/*    */   }
/*    */ 
/*    */   public B radiusX(double paramDouble)
/*    */   {
/* 56 */     this.radiusX = paramDouble;
/* 57 */     this.__set |= 4;
/* 58 */     return this;
/*    */   }
/*    */ 
/*    */   public B radiusY(double paramDouble)
/*    */   {
/* 67 */     this.radiusY = paramDouble;
/* 68 */     this.__set |= 8;
/* 69 */     return this;
/*    */   }
/*    */ 
/*    */   public Ellipse build()
/*    */   {
/* 76 */     Ellipse localEllipse = new Ellipse();
/* 77 */     applyTo(localEllipse);
/* 78 */     return localEllipse;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.shape.EllipseBuilder
 * JD-Core Version:    0.6.2
 */