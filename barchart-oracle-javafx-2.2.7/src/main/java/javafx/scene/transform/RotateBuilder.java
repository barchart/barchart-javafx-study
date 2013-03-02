/*    */ package javafx.scene.transform;
/*    */ 
/*    */ import javafx.geometry.Point3D;
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class RotateBuilder<B extends RotateBuilder<B>>
/*    */   implements Builder<Rotate>
/*    */ {
/*    */   private int __set;
/*    */   private double angle;
/*    */   private Point3D axis;
/*    */   private double pivotX;
/*    */   private double pivotY;
/*    */   private double pivotZ;
/*    */ 
/*    */   public static RotateBuilder<?> create()
/*    */   {
/* 15 */     return new RotateBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(Rotate paramRotate)
/*    */   {
/* 20 */     int i = this.__set;
/* 21 */     if ((i & 0x1) != 0) paramRotate.setAngle(this.angle);
/* 22 */     if ((i & 0x2) != 0) paramRotate.setAxis(this.axis);
/* 23 */     if ((i & 0x4) != 0) paramRotate.setPivotX(this.pivotX);
/* 24 */     if ((i & 0x8) != 0) paramRotate.setPivotY(this.pivotY);
/* 25 */     if ((i & 0x10) != 0) paramRotate.setPivotZ(this.pivotZ);
/*    */   }
/*    */ 
/*    */   public B angle(double paramDouble)
/*    */   {
/* 34 */     this.angle = paramDouble;
/* 35 */     this.__set |= 1;
/* 36 */     return this;
/*    */   }
/*    */ 
/*    */   public B axis(Point3D paramPoint3D)
/*    */   {
/* 45 */     this.axis = paramPoint3D;
/* 46 */     this.__set |= 2;
/* 47 */     return this;
/*    */   }
/*    */ 
/*    */   public B pivotX(double paramDouble)
/*    */   {
/* 56 */     this.pivotX = paramDouble;
/* 57 */     this.__set |= 4;
/* 58 */     return this;
/*    */   }
/*    */ 
/*    */   public B pivotY(double paramDouble)
/*    */   {
/* 67 */     this.pivotY = paramDouble;
/* 68 */     this.__set |= 8;
/* 69 */     return this;
/*    */   }
/*    */ 
/*    */   public B pivotZ(double paramDouble)
/*    */   {
/* 78 */     this.pivotZ = paramDouble;
/* 79 */     this.__set |= 16;
/* 80 */     return this;
/*    */   }
/*    */ 
/*    */   public Rotate build()
/*    */   {
/* 87 */     Rotate localRotate = new Rotate();
/* 88 */     applyTo(localRotate);
/* 89 */     return localRotate;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.transform.RotateBuilder
 * JD-Core Version:    0.6.2
 */