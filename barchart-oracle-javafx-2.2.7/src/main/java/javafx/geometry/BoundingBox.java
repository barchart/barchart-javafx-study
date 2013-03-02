/*     */ package javafx.geometry;
/*     */ 
/*     */ public class BoundingBox extends Bounds
/*     */ {
/*  36 */   private int hash = 0;
/*     */ 
/*     */   public BoundingBox(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6)
/*     */   {
/*  48 */     super(paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6);
/*     */   }
/*     */ 
/*     */   public BoundingBox(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*     */   {
/*  59 */     super(paramDouble1, paramDouble2, 0.0D, paramDouble3, paramDouble4, 0.0D);
/*     */   }
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/*  67 */     return (getMaxX() < getMinX()) || (getMaxY() < getMinY()) || (getMaxZ() < getMinZ());
/*     */   }
/*     */ 
/*     */   public boolean contains(Point2D paramPoint2D)
/*     */   {
/*  75 */     if (paramPoint2D == null) return false;
/*  76 */     return contains(paramPoint2D.getX(), paramPoint2D.getY(), 0.0D);
/*     */   }
/*     */ 
/*     */   public boolean contains(Point3D paramPoint3D)
/*     */   {
/*  84 */     if (paramPoint3D == null) return false;
/*  85 */     return contains(paramPoint3D.getX(), paramPoint3D.getY(), paramPoint3D.getZ());
/*     */   }
/*     */ 
/*     */   public boolean contains(double paramDouble1, double paramDouble2)
/*     */   {
/*  93 */     return contains(paramDouble1, paramDouble2, 0.0D);
/*     */   }
/*     */ 
/*     */   public boolean contains(double paramDouble1, double paramDouble2, double paramDouble3)
/*     */   {
/* 101 */     if (isEmpty()) return false;
/* 102 */     return (paramDouble1 >= getMinX()) && (paramDouble1 <= getMaxX()) && (paramDouble2 >= getMinY()) && (paramDouble2 <= getMaxY()) && (paramDouble3 >= getMinZ()) && (paramDouble3 <= getMaxZ());
/*     */   }
/*     */ 
/*     */   public boolean contains(Bounds paramBounds)
/*     */   {
/* 111 */     if ((paramBounds == null) || (paramBounds.isEmpty())) return false;
/* 112 */     return contains(paramBounds.getMinX(), paramBounds.getMinY(), paramBounds.getMinZ(), paramBounds.getWidth(), paramBounds.getHeight(), paramBounds.getDepth());
/*     */   }
/*     */ 
/*     */   public boolean contains(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*     */   {
/* 121 */     return (contains(paramDouble1, paramDouble2)) && (contains(paramDouble1 + paramDouble3, paramDouble2 + paramDouble4));
/*     */   }
/*     */ 
/*     */   public boolean contains(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6)
/*     */   {
/* 131 */     return (contains(paramDouble1, paramDouble2, paramDouble3)) && (contains(paramDouble1 + paramDouble4, paramDouble2 + paramDouble5, paramDouble3 + paramDouble6));
/*     */   }
/*     */ 
/*     */   public boolean intersects(Bounds paramBounds)
/*     */   {
/* 138 */     if ((paramBounds == null) || (paramBounds.isEmpty())) return false;
/* 139 */     return intersects(paramBounds.getMinX(), paramBounds.getMinY(), paramBounds.getMinZ(), paramBounds.getWidth(), paramBounds.getHeight(), paramBounds.getDepth());
/*     */   }
/*     */ 
/*     */   public boolean intersects(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*     */   {
/* 147 */     return intersects(paramDouble1, paramDouble2, 0.0D, paramDouble3, paramDouble4, 0.0D);
/*     */   }
/*     */ 
/*     */   public boolean intersects(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6)
/*     */   {
/* 155 */     if ((isEmpty()) || (paramDouble4 < 0.0D) || (paramDouble5 < 0.0D) || (paramDouble6 < 0.0D)) return false;
/* 156 */     return (paramDouble1 + paramDouble4 >= getMinX()) && (paramDouble2 + paramDouble5 >= getMinY()) && (paramDouble3 + paramDouble6 >= getMinZ()) && (paramDouble1 <= getMaxX()) && (paramDouble2 <= getMaxY()) && (paramDouble3 <= getMaxZ());
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 171 */     if (paramObject == this) return true;
/* 172 */     if ((paramObject instanceof BoundingBox)) {
/* 173 */       BoundingBox localBoundingBox = (BoundingBox)paramObject;
/* 174 */       return (getMinX() == localBoundingBox.getMinX()) && (getMinY() == localBoundingBox.getMinY()) && (getMinZ() == localBoundingBox.getMinZ()) && (getWidth() == localBoundingBox.getWidth()) && (getHeight() == localBoundingBox.getHeight()) && (getDepth() == localBoundingBox.getDepth());
/*     */     }
/*     */ 
/* 180 */     return false;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 188 */     if (this.hash == 0) {
/* 189 */       long l = 7L;
/* 190 */       l = 31L * l + Double.doubleToLongBits(getMinX());
/* 191 */       l = 31L * l + Double.doubleToLongBits(getMinY());
/* 192 */       l = 31L * l + Double.doubleToLongBits(getMinZ());
/* 193 */       l = 31L * l + Double.doubleToLongBits(getWidth());
/* 194 */       l = 31L * l + Double.doubleToLongBits(getHeight());
/* 195 */       l = 31L * l + Double.doubleToLongBits(getDepth());
/* 196 */       this.hash = ((int)(l ^ l >> 32));
/*     */     }
/* 198 */     return this.hash;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 209 */     return "BoundingBox [minX:" + getMinX() + ", minY:" + getMinY() + ", minZ:" + getMinZ() + ", width:" + getWidth() + ", height:" + getHeight() + ", depth:" + getDepth() + ", maxX:" + getMaxX() + ", maxY:" + getMaxY() + ", maxZ:" + getMaxZ() + "]";
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.geometry.BoundingBox
 * JD-Core Version:    0.6.2
 */