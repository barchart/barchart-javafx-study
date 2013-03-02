/*    */ package javafx.geometry;
/*    */ 
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class BoundingBoxBuilder<B extends BoundingBoxBuilder<B>>
/*    */   implements Builder<BoundingBox>
/*    */ {
/*    */   private double depth;
/*    */   private double height;
/*    */   private double minX;
/*    */   private double minY;
/*    */   private double minZ;
/*    */   private double width;
/*    */ 
/*    */   public static BoundingBoxBuilder<?> create()
/*    */   {
/* 15 */     return new BoundingBoxBuilder();
/*    */   }
/*    */ 
/*    */   public B depth(double paramDouble)
/*    */   {
/* 24 */     this.depth = paramDouble;
/* 25 */     return this;
/*    */   }
/*    */ 
/*    */   public B height(double paramDouble)
/*    */   {
/* 34 */     this.height = paramDouble;
/* 35 */     return this;
/*    */   }
/*    */ 
/*    */   public B minX(double paramDouble)
/*    */   {
/* 44 */     this.minX = paramDouble;
/* 45 */     return this;
/*    */   }
/*    */ 
/*    */   public B minY(double paramDouble)
/*    */   {
/* 54 */     this.minY = paramDouble;
/* 55 */     return this;
/*    */   }
/*    */ 
/*    */   public B minZ(double paramDouble)
/*    */   {
/* 64 */     this.minZ = paramDouble;
/* 65 */     return this;
/*    */   }
/*    */ 
/*    */   public B width(double paramDouble)
/*    */   {
/* 74 */     this.width = paramDouble;
/* 75 */     return this;
/*    */   }
/*    */ 
/*    */   public BoundingBox build()
/*    */   {
/* 82 */     BoundingBox localBoundingBox = new BoundingBox(this.minX, this.minY, this.minZ, this.width, this.height, this.depth);
/* 83 */     return localBoundingBox;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.geometry.BoundingBoxBuilder
 * JD-Core Version:    0.6.2
 */