/*     */ package javafx.geometry;
/*     */ 
/*     */ public class Point2D
/*     */ {
/*     */   private double x;
/*     */   private double y;
/*  65 */   private int hash = 0;
/*     */ 
/*     */   public final double getX()
/*     */   {
/*  44 */     return this.x;
/*     */   }
/*     */ 
/*     */   public final double getY()
/*     */   {
/*  59 */     return this.y;
/*     */   }
/*     */ 
/*     */   public Point2D(double paramDouble1, double paramDouble2)
/*     */   {
/*  73 */     this.x = paramDouble1;
/*  74 */     this.y = paramDouble2;
/*     */   }
/*     */ 
/*     */   public double distance(double paramDouble1, double paramDouble2)
/*     */   {
/*  85 */     double d1 = getX() - paramDouble1;
/*  86 */     double d2 = getY() - paramDouble2;
/*  87 */     return Math.sqrt(d1 * d1 + d2 * d2);
/*     */   }
/*     */ 
/*     */   public double distance(Point2D paramPoint2D)
/*     */   {
/*  97 */     return distance(paramPoint2D.getX(), paramPoint2D.getY());
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 107 */     if (paramObject == this) return true;
/* 108 */     if ((paramObject instanceof Point2D)) {
/* 109 */       Point2D localPoint2D = (Point2D)paramObject;
/* 110 */       return (getX() == localPoint2D.getX()) && (getY() == localPoint2D.getY());
/* 111 */     }return false;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 119 */     if (this.hash == 0) {
/* 120 */       long l = 7L;
/* 121 */       l = 31L * l + Double.doubleToLongBits(getX());
/* 122 */       l = 31L * l + Double.doubleToLongBits(getY());
/* 123 */       this.hash = ((int)(l ^ l >> 32));
/*     */     }
/* 125 */     return this.hash;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 136 */     return "Point2D [x = " + getX() + ", y = " + getY() + "]";
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.geometry.Point2D
 * JD-Core Version:    0.6.2
 */