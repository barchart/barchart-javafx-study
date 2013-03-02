/*    */ package javafx.geometry;
/*    */ 
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class Point3DBuilder<B extends Point3DBuilder<B>>
/*    */   implements Builder<Point3D>
/*    */ {
/*    */   private double x;
/*    */   private double y;
/*    */   private double z;
/*    */ 
/*    */   public static Point3DBuilder<?> create()
/*    */   {
/* 15 */     return new Point3DBuilder();
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
/*    */   public B z(double paramDouble)
/*    */   {
/* 44 */     this.z = paramDouble;
/* 45 */     return this;
/*    */   }
/*    */ 
/*    */   public Point3D build()
/*    */   {
/* 52 */     Point3D localPoint3D = new Point3D(this.x, this.y, this.z);
/* 53 */     return localPoint3D;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.geometry.Point3DBuilder
 * JD-Core Version:    0.6.2
 */