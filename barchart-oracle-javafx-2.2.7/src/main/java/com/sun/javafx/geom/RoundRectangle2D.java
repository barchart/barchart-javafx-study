/*     */ package com.sun.javafx.geom;
/*     */ 
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ 
/*     */ public class RoundRectangle2D extends RectangularShape
/*     */ {
/*     */   public float x;
/*     */   public float y;
/*     */   public float width;
/*     */   public float height;
/*     */   public float arcWidth;
/*     */   public float arcHeight;
/*     */ 
/*     */   public RoundRectangle2D()
/*     */   {
/*     */   }
/*     */ 
/*     */   public RoundRectangle2D(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
/*     */   {
/*  99 */     setRoundRect(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6);
/*     */   }
/*     */ 
/*     */   public float getX()
/*     */   {
/* 107 */     return this.x;
/*     */   }
/*     */ 
/*     */   public float getY()
/*     */   {
/* 115 */     return this.y;
/*     */   }
/*     */ 
/*     */   public float getWidth()
/*     */   {
/* 123 */     return this.width;
/*     */   }
/*     */ 
/*     */   public float getHeight()
/*     */   {
/* 131 */     return this.height;
/*     */   }
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/* 140 */     return (this.width <= 0.0F) || (this.height <= 0.0F);
/*     */   }
/*     */ 
/*     */   public void setRoundRect(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
/*     */   {
/* 165 */     this.x = paramFloat1;
/* 166 */     this.y = paramFloat2;
/* 167 */     this.width = paramFloat3;
/* 168 */     this.height = paramFloat4;
/* 169 */     this.arcWidth = paramFloat5;
/* 170 */     this.arcHeight = paramFloat6;
/*     */   }
/*     */ 
/*     */   public RectBounds getBounds()
/*     */   {
/* 178 */     return new RectBounds(this.x, this.y, this.x + this.width, this.y + this.height);
/*     */   }
/*     */ 
/*     */   public void setRoundRect(RoundRectangle2D paramRoundRectangle2D)
/*     */   {
/* 187 */     setRoundRect(paramRoundRectangle2D.x, paramRoundRectangle2D.y, paramRoundRectangle2D.width, paramRoundRectangle2D.height, paramRoundRectangle2D.arcWidth, paramRoundRectangle2D.arcHeight);
/*     */   }
/*     */ 
/*     */   public void setFrame(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/* 195 */     setRoundRect(paramFloat1, paramFloat2, paramFloat3, paramFloat4, this.arcWidth, this.arcHeight);
/*     */   }
/*     */ 
/*     */   public boolean contains(float paramFloat1, float paramFloat2)
/*     */   {
/* 204 */     if (isEmpty()) return false;
/* 205 */     float f1 = this.x;
/* 206 */     float f2 = this.y;
/* 207 */     float f3 = f1 + this.width;
/* 208 */     float f4 = f2 + this.height;
/*     */ 
/* 210 */     if ((paramFloat1 < f1) || (paramFloat2 < f2) || (paramFloat1 >= f3) || (paramFloat2 >= f4)) {
/* 211 */       return false;
/*     */     }
/* 213 */     float f5 = Math.min(this.width, Math.abs(this.arcWidth)) / 2.0F;
/* 214 */     float f6 = Math.min(this.height, Math.abs(this.arcHeight)) / 2.0F;
/*     */ 
/* 217 */     if ((paramFloat1 >= f1 += f5) && (paramFloat1 < (f1 = f3 - f5))) {
/* 218 */       return true;
/*     */     }
/* 220 */     if ((paramFloat2 >= f2 += f6) && (paramFloat2 < (f2 = f4 - f6))) {
/* 221 */       return true;
/*     */     }
/* 223 */     paramFloat1 = (paramFloat1 - f1) / f5;
/* 224 */     paramFloat2 = (paramFloat2 - f2) / f6;
/* 225 */     return paramFloat1 * paramFloat1 + paramFloat2 * paramFloat2 <= 1.0D;
/*     */   }
/*     */ 
/*     */   private int classify(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
/* 229 */     if (paramFloat1 < paramFloat2)
/* 230 */       return 0;
/* 231 */     if (paramFloat1 < paramFloat2 + paramFloat4)
/* 232 */       return 1;
/* 233 */     if (paramFloat1 < paramFloat3 - paramFloat4)
/* 234 */       return 2;
/* 235 */     if (paramFloat1 < paramFloat3) {
/* 236 */       return 3;
/*     */     }
/* 238 */     return 4;
/*     */   }
/*     */ 
/*     */   public boolean intersects(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/* 247 */     if ((isEmpty()) || (paramFloat3 <= 0.0F) || (paramFloat4 <= 0.0F)) {
/* 248 */       return false;
/*     */     }
/* 250 */     float f1 = this.x;
/* 251 */     float f2 = this.y;
/* 252 */     float f3 = f1 + this.width;
/* 253 */     float f4 = f2 + this.height;
/*     */ 
/* 255 */     if ((paramFloat1 + paramFloat3 <= f1) || (paramFloat1 >= f3) || (paramFloat2 + paramFloat4 <= f2) || (paramFloat2 >= f4)) {
/* 256 */       return false;
/*     */     }
/* 258 */     float f5 = Math.min(this.width, Math.abs(this.arcWidth)) / 2.0F;
/* 259 */     float f6 = Math.min(this.height, Math.abs(this.arcHeight)) / 2.0F;
/* 260 */     int i = classify(paramFloat1, f1, f3, f5);
/* 261 */     int j = classify(paramFloat1 + paramFloat3, f1, f3, f5);
/* 262 */     int k = classify(paramFloat2, f2, f4, f6);
/* 263 */     int m = classify(paramFloat2 + paramFloat4, f2, f4, f6);
/*     */ 
/* 265 */     if ((i == 2) || (j == 2) || (k == 2) || (m == 2)) {
/* 266 */       return true;
/*     */     }
/*     */ 
/* 269 */     if (((i < 2) && (j > 2)) || ((k < 2) && (m > 2))) {
/* 270 */       return true;
/*     */     }
/*     */ 
/* 278 */     paramFloat1 = paramFloat1 -= f3 - f5;
/* 279 */     paramFloat2 = paramFloat2 -= f4 - f6;
/* 280 */     paramFloat1 /= f5;
/* 281 */     paramFloat2 /= f6;
/* 282 */     return paramFloat1 * paramFloat1 + paramFloat2 * paramFloat2 <= 1.0F;
/*     */   }
/*     */ 
/*     */   public boolean contains(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/* 290 */     if ((isEmpty()) || (paramFloat3 <= 0.0F) || (paramFloat4 <= 0.0F)) {
/* 291 */       return false;
/*     */     }
/* 293 */     return (contains(paramFloat1, paramFloat2)) && (contains(paramFloat1 + paramFloat3, paramFloat2)) && (contains(paramFloat1, paramFloat2 + paramFloat4)) && (contains(paramFloat1 + paramFloat3, paramFloat2 + paramFloat4));
/*     */   }
/*     */ 
/*     */   public PathIterator getPathIterator(BaseTransform paramBaseTransform)
/*     */   {
/* 316 */     return new RoundRectIterator(this, paramBaseTransform);
/*     */   }
/*     */ 
/*     */   public RoundRectangle2D copy()
/*     */   {
/* 321 */     return new RoundRectangle2D(this.x, this.y, this.width, this.height, this.arcWidth, this.arcHeight);
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 331 */     int i = Float.floatToIntBits(this.x);
/* 332 */     i += Float.floatToIntBits(this.y) * 37;
/* 333 */     i += Float.floatToIntBits(this.width) * 43;
/* 334 */     i += Float.floatToIntBits(this.height) * 47;
/* 335 */     i += Float.floatToIntBits(this.arcWidth) * 53;
/* 336 */     i += Float.floatToIntBits(this.arcHeight) * 59;
/* 337 */     return i ^ i >> 32;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 356 */     if (paramObject == this) {
/* 357 */       return true;
/*     */     }
/* 359 */     if ((paramObject instanceof RoundRectangle2D)) {
/* 360 */       RoundRectangle2D localRoundRectangle2D = (RoundRectangle2D)paramObject;
/* 361 */       return (this.x == localRoundRectangle2D.x) && (this.y == localRoundRectangle2D.y) && (this.width == localRoundRectangle2D.width) && (this.height == localRoundRectangle2D.height) && (this.arcWidth == localRoundRectangle2D.arcWidth) && (this.arcHeight == localRoundRectangle2D.arcHeight);
/*     */     }
/*     */ 
/* 368 */     return false;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.geom.RoundRectangle2D
 * JD-Core Version:    0.6.2
 */