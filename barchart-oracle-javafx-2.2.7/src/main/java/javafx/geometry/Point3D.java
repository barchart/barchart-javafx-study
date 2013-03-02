/*     */ package javafx.geometry;
/*     */ 
/*     */ public class Point3D
/*     */ {
/*     */   private double x;
/*     */   private double y;
/*     */   private double z;
/*  83 */   private int hash = 0;
/*     */ 
/*     */   public final double getX()
/*     */   {
/*  47 */     return this.x;
/*     */   }
/*     */ 
/*     */   public final double getY()
/*     */   {
/*  62 */     return this.y;
/*     */   }
/*     */ 
/*     */   public final double getZ()
/*     */   {
/*  77 */     return this.z;
/*     */   }
/*     */ 
/*     */   public Point3D(double paramDouble1, double paramDouble2, double paramDouble3)
/*     */   {
/*  92 */     this.x = paramDouble1;
/*  93 */     this.y = paramDouble2;
/*  94 */     this.z = paramDouble3;
/*     */   }
/*     */ 
/*     */   public double distance(double paramDouble1, double paramDouble2, double paramDouble3)
/*     */   {
/* 106 */     double d1 = getX() - paramDouble1;
/* 107 */     double d2 = getY() - paramDouble2;
/* 108 */     double d3 = getZ() - paramDouble3;
/* 109 */     return Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
/*     */   }
/*     */ 
/*     */   public double distance(Point3D paramPoint3D)
/*     */   {
/* 119 */     return distance(paramPoint3D.getX(), paramPoint3D.getY(), paramPoint3D.getZ());
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 127 */     if (paramObject == this) return true;
/* 128 */     if ((paramObject instanceof Point3D)) {
/* 129 */       Point3D localPoint3D = (Point3D)paramObject;
/* 130 */       return (getX() == localPoint3D.getX()) && (getY() == localPoint3D.getY()) && (getZ() == localPoint3D.getZ());
/* 131 */     }return false;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 139 */     if (this.hash == 0) {
/* 140 */       long l = 7L;
/* 141 */       l = 31L * l + Double.doubleToLongBits(getX());
/* 142 */       l = 31L * l + Double.doubleToLongBits(getY());
/* 143 */       l = 31L * l + Double.doubleToLongBits(getZ());
/* 144 */       this.hash = ((int)(l ^ l >> 32));
/*     */     }
/* 146 */     return this.hash;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 157 */     return "Point3D [x = " + getX() + ", y = " + getY() + ", z = " + getZ() + "]";
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.geometry.Point3D
 * JD-Core Version:    0.6.2
 */