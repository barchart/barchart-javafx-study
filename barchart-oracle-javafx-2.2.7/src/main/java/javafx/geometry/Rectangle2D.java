/*     */ package javafx.geometry;
/*     */ 
/*     */ public class Rectangle2D
/*     */ {
/*  36 */   public static final Rectangle2D EMPTY = new Rectangle2D(0.0D, 0.0D, 0.0D, 0.0D);
/*     */   private double minX;
/*     */   private double minY;
/*     */   private double width;
/*     */   private double height;
/*     */   private double maxX;
/*     */   private double maxY;
/*  89 */   private int hash = 0;
/*     */ 
/*     */   public double getMinX()
/*     */   {
/*  43 */     return this.minX;
/*     */   }
/*     */ 
/*     */   public double getMinY()
/*     */   {
/*  51 */     return this.minY;
/*     */   }
/*     */ 
/*     */   public double getWidth()
/*     */   {
/*  59 */     return this.width;
/*     */   }
/*     */ 
/*     */   public double getHeight()
/*     */   {
/*  67 */     return this.height;
/*     */   }
/*     */ 
/*     */   public double getMaxX()
/*     */   {
/*  75 */     return this.maxX;
/*     */   }
/*     */ 
/*     */   public double getMaxY()
/*     */   {
/*  83 */     return this.maxY;
/*     */   }
/*     */ 
/*     */   public Rectangle2D(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*     */   {
/*  99 */     if ((paramDouble3 < 0.0D) || (paramDouble4 < 0.0D)) {
/* 100 */       throw new IllegalArgumentException("Both width and height must be >= 0");
/*     */     }
/*     */ 
/* 103 */     this.minX = paramDouble1;
/* 104 */     this.minY = paramDouble2;
/* 105 */     this.width = paramDouble3;
/* 106 */     this.height = paramDouble4;
/* 107 */     this.maxX = (paramDouble1 + paramDouble3);
/* 108 */     this.maxY = (paramDouble2 + paramDouble4);
/*     */   }
/*     */ 
/*     */   public boolean contains(Point2D paramPoint2D)
/*     */   {
/* 119 */     if (paramPoint2D == null) return false;
/* 120 */     return contains(paramPoint2D.getX(), paramPoint2D.getY());
/*     */   }
/*     */ 
/*     */   public boolean contains(double paramDouble1, double paramDouble2)
/*     */   {
/* 133 */     return (paramDouble1 >= this.minX) && (paramDouble1 <= this.maxX) && (paramDouble2 >= this.minY) && (paramDouble2 <= this.maxY);
/*     */   }
/*     */ 
/*     */   public boolean contains(Rectangle2D paramRectangle2D)
/*     */   {
/* 145 */     if (paramRectangle2D == null) return false;
/* 146 */     return (paramRectangle2D.minX >= this.minX) && (paramRectangle2D.minY >= this.minY) && (paramRectangle2D.maxX <= this.maxX) && (paramRectangle2D.maxY <= this.maxY);
/*     */   }
/*     */ 
/*     */   public boolean contains(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*     */   {
/* 163 */     return (paramDouble1 >= this.minX) && (paramDouble2 >= this.minY) && (paramDouble3 <= this.maxX - paramDouble1) && (paramDouble4 <= this.maxY - paramDouble2);
/*     */   }
/*     */ 
/*     */   public boolean intersects(Rectangle2D paramRectangle2D)
/*     */   {
/* 175 */     if (paramRectangle2D == null) return false;
/* 176 */     return (paramRectangle2D.maxX > this.minX) && (paramRectangle2D.maxY > this.minY) && (paramRectangle2D.minX < this.maxX) && (paramRectangle2D.minY < this.maxY);
/*     */   }
/*     */ 
/*     */   public boolean intersects(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*     */   {
/* 193 */     return (paramDouble1 < this.maxX) && (paramDouble2 < this.maxY) && (paramDouble1 + paramDouble3 > this.minX) && (paramDouble2 + paramDouble4 > this.minY);
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 202 */     if (paramObject == this) return true;
/* 203 */     if ((paramObject instanceof Rectangle2D)) {
/* 204 */       Rectangle2D localRectangle2D = (Rectangle2D)paramObject;
/* 205 */       return (this.minX == localRectangle2D.minX) && (this.minY == localRectangle2D.minY) && (this.width == localRectangle2D.width) && (this.height == localRectangle2D.height);
/*     */     }
/*     */ 
/* 209 */     return false;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 217 */     if (this.hash == 0) {
/* 218 */       long l = 7L;
/* 219 */       l = 31L * l + Double.doubleToLongBits(this.minX);
/* 220 */       l = 31L * l + Double.doubleToLongBits(this.minY);
/* 221 */       l = 31L * l + Double.doubleToLongBits(this.width);
/* 222 */       l = 31L * l + Double.doubleToLongBits(this.height);
/* 223 */       this.hash = ((int)(l ^ l >> 32));
/*     */     }
/* 225 */     return this.hash;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 236 */     return "Rectangle2D [minX = " + this.minX + ", minY=" + this.minY + ", maxX=" + this.maxX + ", maxY=" + this.maxY + ", width=" + this.width + ", height=" + this.height + "]";
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.geometry.Rectangle2D
 * JD-Core Version:    0.6.2
 */