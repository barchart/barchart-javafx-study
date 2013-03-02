/*    */ package javafx.scene.shape;
/*    */ 
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class CircleBuilder<B extends CircleBuilder<B>> extends ShapeBuilder<B>
/*    */   implements Builder<Circle>
/*    */ {
/*    */   private int __set;
/*    */   private double centerX;
/*    */   private double centerY;
/*    */   private double radius;
/*    */ 
/*    */   public static CircleBuilder<?> create()
/*    */   {
/* 15 */     return new CircleBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(Circle paramCircle)
/*    */   {
/* 20 */     super.applyTo(paramCircle);
/* 21 */     int i = this.__set;
/* 22 */     if ((i & 0x1) != 0) paramCircle.setCenterX(this.centerX);
/* 23 */     if ((i & 0x2) != 0) paramCircle.setCenterY(this.centerY);
/* 24 */     if ((i & 0x4) != 0) paramCircle.setRadius(this.radius);
/*    */   }
/*    */ 
/*    */   public B centerX(double paramDouble)
/*    */   {
/* 33 */     this.centerX = paramDouble;
/* 34 */     this.__set |= 1;
/* 35 */     return this;
/*    */   }
/*    */ 
/*    */   public B centerY(double paramDouble)
/*    */   {
/* 44 */     this.centerY = paramDouble;
/* 45 */     this.__set |= 2;
/* 46 */     return this;
/*    */   }
/*    */ 
/*    */   public B radius(double paramDouble)
/*    */   {
/* 55 */     this.radius = paramDouble;
/* 56 */     this.__set |= 4;
/* 57 */     return this;
/*    */   }
/*    */ 
/*    */   public Circle build()
/*    */   {
/* 64 */     Circle localCircle = new Circle();
/* 65 */     applyTo(localCircle);
/* 66 */     return localCircle;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.shape.CircleBuilder
 * JD-Core Version:    0.6.2
 */