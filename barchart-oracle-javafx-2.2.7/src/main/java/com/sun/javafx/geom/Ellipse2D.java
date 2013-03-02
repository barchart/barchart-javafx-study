/*     */ package com.sun.javafx.geom;
/*     */ 
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ 
/*     */ public class Ellipse2D extends RectangularShape
/*     */ {
/*     */   public float x;
/*     */   public float y;
/*     */   public float width;
/*     */   public float height;
/*     */ 
/*     */   public Ellipse2D()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Ellipse2D(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/*  79 */     setFrame(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/*     */   }
/*     */ 
/*     */   public float getX()
/*     */   {
/*  88 */     return this.x;
/*     */   }
/*     */ 
/*     */   public float getY()
/*     */   {
/*  96 */     return this.y;
/*     */   }
/*     */ 
/*     */   public float getWidth()
/*     */   {
/* 104 */     return this.width;
/*     */   }
/*     */ 
/*     */   public float getHeight()
/*     */   {
/* 112 */     return this.height;
/*     */   }
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/* 120 */     return (this.width <= 0.0F) || (this.height <= 0.0F);
/*     */   }
/*     */ 
/*     */   public void setFrame(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/* 136 */     this.x = paramFloat1;
/* 137 */     this.y = paramFloat2;
/* 138 */     this.width = paramFloat3;
/* 139 */     this.height = paramFloat4;
/*     */   }
/*     */ 
/*     */   public RectBounds getBounds()
/*     */   {
/* 146 */     return new RectBounds(this.x, this.y, this.x + this.width, this.y + this.height);
/*     */   }
/*     */ 
/*     */   public boolean contains(float paramFloat1, float paramFloat2)
/*     */   {
/* 155 */     float f1 = this.width;
/* 156 */     if (f1 <= 0.0F) {
/* 157 */       return false;
/*     */     }
/* 159 */     float f2 = (paramFloat1 - this.x) / f1 - 0.5F;
/* 160 */     float f3 = this.height;
/* 161 */     if (f3 <= 0.0F) {
/* 162 */       return false;
/*     */     }
/* 164 */     float f4 = (paramFloat2 - this.y) / f3 - 0.5F;
/* 165 */     return f2 * f2 + f4 * f4 < 0.25F;
/*     */   }
/*     */ 
/*     */   public boolean intersects(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/* 172 */     if ((paramFloat3 <= 0.0F) || (paramFloat4 <= 0.0F)) {
/* 173 */       return false;
/*     */     }
/*     */ 
/* 177 */     float f1 = this.width;
/* 178 */     if (f1 <= 0.0F) {
/* 179 */       return false;
/*     */     }
/* 181 */     float f2 = (paramFloat1 - this.x) / f1 - 0.5F;
/* 182 */     float f3 = f2 + paramFloat3 / f1;
/* 183 */     float f4 = this.height;
/* 184 */     if (f4 <= 0.0F) {
/* 185 */       return false;
/*     */     }
/* 187 */     float f5 = (paramFloat2 - this.y) / f4 - 0.5F;
/* 188 */     float f6 = f5 + paramFloat4 / f4;
/*     */     float f7;
/* 193 */     if (f2 > 0.0F)
/*     */     {
/* 195 */       f7 = f2;
/* 196 */     } else if (f3 < 0.0F)
/*     */     {
/* 198 */       f7 = f3;
/*     */     }
/* 200 */     else f7 = 0.0F;
/*     */     float f8;
/* 202 */     if (f5 > 0.0F)
/*     */     {
/* 204 */       f8 = f5;
/* 205 */     } else if (f6 < 0.0F)
/*     */     {
/* 207 */       f8 = f6;
/*     */     }
/* 209 */     else f8 = 0.0F;
/*     */ 
/* 211 */     return f7 * f7 + f8 * f8 < 0.25F;
/*     */   }
/*     */ 
/*     */   public boolean contains(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/* 219 */     return (contains(paramFloat1, paramFloat2)) && (contains(paramFloat1 + paramFloat3, paramFloat2)) && (contains(paramFloat1, paramFloat2 + paramFloat4)) && (contains(paramFloat1 + paramFloat3, paramFloat2 + paramFloat4));
/*     */   }
/*     */ 
/*     */   public PathIterator getPathIterator(BaseTransform paramBaseTransform)
/*     */   {
/* 242 */     return new EllipseIterator(this, paramBaseTransform);
/*     */   }
/*     */ 
/*     */   public Ellipse2D copy()
/*     */   {
/* 247 */     return new Ellipse2D(this.x, this.y, this.width, this.height);
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 257 */     int i = Float.floatToIntBits(this.x);
/* 258 */     i += Float.floatToIntBits(this.y) * 37;
/* 259 */     i += Float.floatToIntBits(this.width) * 43;
/* 260 */     i += Float.floatToIntBits(this.height) * 47;
/* 261 */     return i ^ i >> 32;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 279 */     if (paramObject == this) {
/* 280 */       return true;
/*     */     }
/* 282 */     if ((paramObject instanceof Ellipse2D)) {
/* 283 */       Ellipse2D localEllipse2D = (Ellipse2D)paramObject;
/* 284 */       return (this.x == localEllipse2D.x) && (this.y == localEllipse2D.y) && (this.width == localEllipse2D.width) && (this.height == localEllipse2D.height);
/*     */     }
/*     */ 
/* 289 */     return false;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.geom.Ellipse2D
 * JD-Core Version:    0.6.2
 */