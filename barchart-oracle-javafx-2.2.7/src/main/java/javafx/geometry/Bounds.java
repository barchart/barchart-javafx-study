/*     */ package javafx.geometry;
/*     */ 
/*     */ public abstract class Bounds
/*     */ {
/*     */   private double minX;
/*     */   private double minY;
/*     */   private double minZ;
/*     */   private double width;
/*     */   private double height;
/*     */   private double depth;
/*     */   private double maxX;
/*     */   private double maxY;
/*     */   private double maxZ;
/*     */ 
/*     */   public final double getMinX()
/*     */   {
/*  38 */     return this.minX;
/*     */   }
/*     */ 
/*     */   public final double getMinY()
/*     */   {
/*  46 */     return this.minY;
/*     */   }
/*     */ 
/*     */   public final double getMinZ()
/*     */   {
/*  54 */     return this.minZ;
/*     */   }
/*     */ 
/*     */   public final double getWidth()
/*     */   {
/*  61 */     return this.width;
/*     */   }
/*     */ 
/*     */   public final double getHeight()
/*     */   {
/*  68 */     return this.height;
/*     */   }
/*     */ 
/*     */   public final double getDepth()
/*     */   {
/*  76 */     return this.depth;
/*     */   }
/*     */ 
/*     */   public final double getMaxX()
/*     */   {
/*  83 */     return this.maxX;
/*     */   }
/*     */ 
/*     */   public final double getMaxY()
/*     */   {
/*  90 */     return this.maxY;
/*     */   }
/*     */ 
/*     */   public final double getMaxZ()
/*     */   {
/*  98 */     return this.maxZ;
/*     */   }
/*     */ 
/*     */   public abstract boolean isEmpty();
/*     */ 
/*     */   public abstract boolean contains(Point2D paramPoint2D);
/*     */ 
/*     */   public abstract boolean contains(Point3D paramPoint3D);
/*     */ 
/*     */   public abstract boolean contains(double paramDouble1, double paramDouble2);
/*     */ 
/*     */   public abstract boolean contains(double paramDouble1, double paramDouble2, double paramDouble3);
/*     */ 
/*     */   public abstract boolean contains(Bounds paramBounds);
/*     */ 
/*     */   public abstract boolean contains(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4);
/*     */ 
/*     */   public abstract boolean contains(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6);
/*     */ 
/*     */   public abstract boolean intersects(Bounds paramBounds);
/*     */ 
/*     */   public abstract boolean intersects(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4);
/*     */ 
/*     */   public abstract boolean intersects(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6);
/*     */ 
/*     */   protected Bounds(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6)
/*     */   {
/* 247 */     this.minX = paramDouble1;
/* 248 */     this.minY = paramDouble2;
/* 249 */     this.minZ = paramDouble3;
/* 250 */     this.width = paramDouble4;
/* 251 */     this.height = paramDouble5;
/* 252 */     this.depth = paramDouble6;
/* 253 */     this.maxX = (paramDouble1 + paramDouble4);
/* 254 */     this.maxY = (paramDouble2 + paramDouble5);
/* 255 */     this.maxZ = (paramDouble3 + paramDouble6);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.geometry.Bounds
 * JD-Core Version:    0.6.2
 */