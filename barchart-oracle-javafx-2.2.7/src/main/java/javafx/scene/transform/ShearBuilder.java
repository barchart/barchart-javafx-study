/*    */ package javafx.scene.transform;
/*    */ 
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class ShearBuilder<B extends ShearBuilder<B>>
/*    */   implements Builder<Shear>
/*    */ {
/*    */   private int __set;
/*    */   private double pivotX;
/*    */   private double pivotY;
/*    */   private double x;
/*    */   private double y;
/*    */ 
/*    */   public static ShearBuilder<?> create()
/*    */   {
/* 15 */     return new ShearBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(Shear paramShear)
/*    */   {
/* 20 */     int i = this.__set;
/* 21 */     if ((i & 0x1) != 0) paramShear.setPivotX(this.pivotX);
/* 22 */     if ((i & 0x2) != 0) paramShear.setPivotY(this.pivotY);
/* 23 */     if ((i & 0x4) != 0) paramShear.setX(this.x);
/* 24 */     if ((i & 0x8) != 0) paramShear.setY(this.y);
/*    */   }
/*    */ 
/*    */   public B pivotX(double paramDouble)
/*    */   {
/* 33 */     this.pivotX = paramDouble;
/* 34 */     this.__set |= 1;
/* 35 */     return this;
/*    */   }
/*    */ 
/*    */   public B pivotY(double paramDouble)
/*    */   {
/* 44 */     this.pivotY = paramDouble;
/* 45 */     this.__set |= 2;
/* 46 */     return this;
/*    */   }
/*    */ 
/*    */   public B x(double paramDouble)
/*    */   {
/* 55 */     this.x = paramDouble;
/* 56 */     this.__set |= 4;
/* 57 */     return this;
/*    */   }
/*    */ 
/*    */   public B y(double paramDouble)
/*    */   {
/* 66 */     this.y = paramDouble;
/* 67 */     this.__set |= 8;
/* 68 */     return this;
/*    */   }
/*    */ 
/*    */   public Shear build()
/*    */   {
/* 75 */     Shear localShear = new Shear();
/* 76 */     applyTo(localShear);
/* 77 */     return localShear;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.transform.ShearBuilder
 * JD-Core Version:    0.6.2
 */