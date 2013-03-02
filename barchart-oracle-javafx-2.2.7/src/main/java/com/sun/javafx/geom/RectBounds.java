/*     */ package com.sun.javafx.geom;
/*     */ 
/*     */ public class RectBounds extends BaseBounds
/*     */ {
/*     */   private float minX;
/*     */   private float maxX;
/*     */   private float minY;
/*     */   private float maxY;
/*     */ 
/*     */   public RectBounds()
/*     */   {
/*  49 */     this.minX = (this.minY = 0.0F);
/*  50 */     this.maxX = (this.maxY = -1.0F);
/*     */   }
/*     */ 
/*     */   public BaseBounds copy() {
/*  54 */     return new RectBounds(this.minX, this.minY, this.maxX, this.maxY);
/*     */   }
/*     */ 
/*     */   public RectBounds(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/*  61 */     setBounds(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/*     */   }
/*     */ 
/*     */   public RectBounds(RectBounds paramRectBounds)
/*     */   {
/*  68 */     setBounds(paramRectBounds);
/*     */   }
/*     */ 
/*     */   public RectBounds(Rectangle paramRectangle)
/*     */   {
/*  75 */     setBounds(paramRectangle.x, paramRectangle.y, paramRectangle.x + paramRectangle.width, paramRectangle.y + paramRectangle.height);
/*     */   }
/*     */ 
/*     */   public BaseBounds.BoundsType getBoundsType()
/*     */   {
/*  80 */     return BaseBounds.BoundsType.RECTANGLE;
/*     */   }
/*     */ 
/*     */   public boolean is2D() {
/*  84 */     return true;
/*     */   }
/*     */ 
/*     */   public float getWidth()
/*     */   {
/*  92 */     return this.maxX - this.minX;
/*     */   }
/*     */ 
/*     */   public float getHeight()
/*     */   {
/* 100 */     return this.maxY - this.minY;
/*     */   }
/*     */ 
/*     */   public float getDepth()
/*     */   {
/* 109 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   public float getMinX() {
/* 113 */     return this.minX;
/*     */   }
/*     */ 
/*     */   public void setMinX(float paramFloat) {
/* 117 */     this.minX = paramFloat;
/*     */   }
/*     */ 
/*     */   public float getMinY() {
/* 121 */     return this.minY;
/*     */   }
/*     */ 
/*     */   public void setMinY(float paramFloat) {
/* 125 */     this.minY = paramFloat;
/*     */   }
/*     */ 
/*     */   public float getMinZ() {
/* 129 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   public float getMaxX() {
/* 133 */     return this.maxX;
/*     */   }
/*     */ 
/*     */   public void setMaxX(float paramFloat) {
/* 137 */     this.maxX = paramFloat;
/*     */   }
/*     */ 
/*     */   public float getMaxY() {
/* 141 */     return this.maxY;
/*     */   }
/*     */ 
/*     */   public void setMaxY(float paramFloat) {
/* 145 */     this.maxY = paramFloat;
/*     */   }
/*     */ 
/*     */   public float getMaxZ() {
/* 149 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   public Vec2f getMin(Vec2f paramVec2f) {
/* 153 */     if (paramVec2f == null) {
/* 154 */       paramVec2f = new Vec2f();
/*     */     }
/* 156 */     paramVec2f.x = this.minX;
/* 157 */     paramVec2f.y = this.minY;
/* 158 */     return paramVec2f;
/*     */   }
/*     */ 
/*     */   public Vec2f getMax(Vec2f paramVec2f) {
/* 162 */     if (paramVec2f == null) {
/* 163 */       paramVec2f = new Vec2f();
/*     */     }
/* 165 */     paramVec2f.x = this.maxX;
/* 166 */     paramVec2f.y = this.maxY;
/* 167 */     return paramVec2f;
/*     */   }
/*     */ 
/*     */   public Vec3f getMin(Vec3f paramVec3f) {
/* 171 */     if (paramVec3f == null) {
/* 172 */       paramVec3f = new Vec3f();
/*     */     }
/* 174 */     paramVec3f.x = this.minX;
/* 175 */     paramVec3f.y = this.minY;
/* 176 */     paramVec3f.z = 0.0F;
/* 177 */     return paramVec3f;
/*     */   }
/*     */ 
/*     */   public Vec3f getMax(Vec3f paramVec3f)
/*     */   {
/* 182 */     if (paramVec3f == null) {
/* 183 */       paramVec3f = new Vec3f();
/*     */     }
/* 185 */     paramVec3f.x = this.maxX;
/* 186 */     paramVec3f.y = this.maxY;
/* 187 */     paramVec3f.z = 0.0F;
/* 188 */     return paramVec3f;
/*     */   }
/*     */ 
/*     */   public BaseBounds deriveWithUnion(BaseBounds paramBaseBounds)
/*     */   {
/*     */     Object localObject;
/* 193 */     if (paramBaseBounds.getBoundsType() == BaseBounds.BoundsType.RECTANGLE) {
/* 194 */       localObject = (RectBounds)paramBaseBounds;
/* 195 */       unionWith((RectBounds)localObject); } else {
/* 196 */       if (paramBaseBounds.getBoundsType() == BaseBounds.BoundsType.BOX) {
/* 197 */         localObject = new BoxBounds((BoxBounds)paramBaseBounds);
/* 198 */         ((BoxBounds)localObject).unionWith(this);
/* 199 */         return localObject;
/*     */       }
/* 201 */       throw new UnsupportedOperationException("Unknown BoundsType");
/*     */     }
/* 203 */     return this;
/*     */   }
/*     */ 
/*     */   public BaseBounds deriveWithNewBounds(Rectangle paramRectangle) {
/* 207 */     if ((paramRectangle.width < 0) || (paramRectangle.height < 0)) return makeEmpty();
/* 208 */     setBounds(paramRectangle.x, paramRectangle.y, paramRectangle.x + paramRectangle.width, paramRectangle.y + paramRectangle.height);
/*     */ 
/* 210 */     return this;
/*     */   }
/*     */ 
/*     */   public BaseBounds deriveWithNewBounds(BaseBounds paramBaseBounds) {
/* 214 */     if (paramBaseBounds.isEmpty()) return makeEmpty();
/* 215 */     if (paramBaseBounds.getBoundsType() == BaseBounds.BoundsType.RECTANGLE) {
/* 216 */       RectBounds localRectBounds = (RectBounds)paramBaseBounds;
/* 217 */       this.minX = localRectBounds.getMinX();
/* 218 */       this.minY = localRectBounds.getMinY();
/* 219 */       this.maxX = localRectBounds.getMaxX();
/* 220 */       this.maxY = localRectBounds.getMaxY(); } else {
/* 221 */       if (paramBaseBounds.getBoundsType() == BaseBounds.BoundsType.BOX) {
/* 222 */         return new BoxBounds((BoxBounds)paramBaseBounds);
/*     */       }
/* 224 */       throw new UnsupportedOperationException("Unknown BoundsType");
/*     */     }
/* 226 */     return this;
/*     */   }
/*     */ 
/*     */   public BaseBounds deriveWithNewBounds(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
/*     */   {
/* 231 */     if ((paramFloat4 < paramFloat1) || (paramFloat5 < paramFloat2) || (paramFloat6 < paramFloat3)) return makeEmpty();
/* 232 */     if ((paramFloat3 == 0.0F) && (paramFloat6 == 0.0F)) {
/* 233 */       this.minX = paramFloat1;
/* 234 */       this.minY = paramFloat2;
/* 235 */       this.maxX = paramFloat4;
/* 236 */       this.maxY = paramFloat5;
/* 237 */       return this;
/*     */     }
/* 239 */     return new BoxBounds(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6);
/*     */   }
/*     */ 
/*     */   public BaseBounds deriveWithNewBoundsAndSort(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
/*     */   {
/* 244 */     if ((paramFloat3 == 0.0F) && (paramFloat6 == 0.0F)) {
/* 245 */       setBoundsAndSort(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6);
/* 246 */       return this;
/*     */     }
/*     */ 
/* 249 */     BoxBounds localBoxBounds = new BoxBounds();
/* 250 */     localBoxBounds.setBoundsAndSort(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6);
/* 251 */     return localBoxBounds;
/*     */   }
/*     */ 
/*     */   public final void setBounds(RectBounds paramRectBounds)
/*     */   {
/* 259 */     this.minX = paramRectBounds.getMinX();
/* 260 */     this.minY = paramRectBounds.getMinY();
/* 261 */     this.maxX = paramRectBounds.getMaxX();
/* 262 */     this.maxY = paramRectBounds.getMaxY();
/*     */   }
/*     */ 
/*     */   public final void setBounds(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/* 269 */     this.minX = paramFloat1;
/* 270 */     this.minY = paramFloat2;
/* 271 */     this.maxX = paramFloat3;
/* 272 */     this.maxY = paramFloat4;
/*     */   }
/*     */ 
/*     */   public void setBoundsAndSort(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/* 280 */     setBounds(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/* 281 */     sortMinMax();
/*     */   }
/*     */ 
/*     */   public void setBoundsAndSort(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
/*     */   {
/* 286 */     if ((paramFloat3 != 0.0F) || (paramFloat6 != 0.0F)) {
/* 287 */       throw new UnsupportedOperationException("Unknown BoundsType");
/*     */     }
/* 289 */     setBounds(paramFloat1, paramFloat2, paramFloat4, paramFloat5);
/* 290 */     sortMinMax();
/*     */   }
/*     */ 
/*     */   public void setBoundsAndSort(Point2D paramPoint2D1, Point2D paramPoint2D2) {
/* 294 */     setBoundsAndSort(paramPoint2D1.x, paramPoint2D1.y, paramPoint2D2.x, paramPoint2D2.y);
/*     */   }
/*     */ 
/*     */   public void unionWith(RectBounds paramRectBounds)
/*     */   {
/* 299 */     if (paramRectBounds.isEmpty()) return;
/* 300 */     if (isEmpty()) {
/* 301 */       setBounds(paramRectBounds);
/* 302 */       return;
/*     */     }
/*     */ 
/* 305 */     this.minX = Math.min(this.minX, paramRectBounds.getMinX());
/* 306 */     this.minY = Math.min(this.minY, paramRectBounds.getMinY());
/* 307 */     this.maxX = Math.max(this.maxX, paramRectBounds.getMaxX());
/* 308 */     this.maxY = Math.max(this.maxY, paramRectBounds.getMaxY());
/*     */   }
/*     */ 
/*     */   public void unionWith(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/* 313 */     if ((paramFloat3 < paramFloat1) || (paramFloat4 < paramFloat2)) return;
/* 314 */     if (isEmpty()) {
/* 315 */       setBounds(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/* 316 */       return;
/*     */     }
/*     */ 
/* 319 */     this.minX = Math.min(this.minX, paramFloat1);
/* 320 */     this.minY = Math.min(this.minY, paramFloat2);
/* 321 */     this.maxX = Math.max(this.maxX, paramFloat3);
/* 322 */     this.maxY = Math.max(this.maxY, paramFloat4);
/*     */   }
/*     */ 
/*     */   public void add(float paramFloat1, float paramFloat2, float paramFloat3) {
/* 326 */     if (paramFloat3 != 0.0F) {
/* 327 */       throw new UnsupportedOperationException("Unknown BoundsType");
/*     */     }
/* 329 */     unionWith(paramFloat1, paramFloat2, paramFloat1, paramFloat2);
/*     */   }
/*     */ 
/*     */   public void add(float paramFloat1, float paramFloat2) {
/* 333 */     unionWith(paramFloat1, paramFloat2, paramFloat1, paramFloat2);
/*     */   }
/*     */ 
/*     */   public void add(Point2D paramPoint2D) {
/* 337 */     add(paramPoint2D.x, paramPoint2D.y);
/*     */   }
/*     */ 
/*     */   public void intersectWith(BaseBounds paramBaseBounds)
/*     */   {
/* 342 */     if (isEmpty()) return;
/* 343 */     if (paramBaseBounds.isEmpty()) {
/* 344 */       makeEmpty();
/* 345 */       return;
/*     */     }
/*     */ 
/* 348 */     this.minX = Math.max(this.minX, paramBaseBounds.getMinX());
/* 349 */     this.minY = Math.max(this.minY, paramBaseBounds.getMinY());
/* 350 */     this.maxX = Math.min(this.maxX, paramBaseBounds.getMaxX());
/* 351 */     this.maxY = Math.min(this.maxY, paramBaseBounds.getMaxY());
/*     */   }
/*     */ 
/*     */   public void intersectWith(Rectangle paramRectangle) {
/* 355 */     float f1 = paramRectangle.x;
/* 356 */     float f2 = paramRectangle.y;
/* 357 */     intersectWith(f1, f2, f1 + paramRectangle.width, f2 + paramRectangle.height);
/*     */   }
/*     */ 
/*     */   public void intersectWith(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/* 362 */     if (isEmpty()) return;
/* 363 */     if ((paramFloat3 < paramFloat1) || (paramFloat4 < paramFloat2)) {
/* 364 */       makeEmpty();
/* 365 */       return;
/*     */     }
/*     */ 
/* 368 */     this.minX = Math.max(this.minX, paramFloat1);
/* 369 */     this.minY = Math.max(this.minY, paramFloat2);
/* 370 */     this.maxX = Math.min(this.maxX, paramFloat3);
/* 371 */     this.maxY = Math.min(this.maxY, paramFloat4);
/*     */   }
/*     */ 
/*     */   public void intersectWith(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
/*     */   {
/* 377 */     if (isEmpty()) return;
/* 378 */     if ((paramFloat4 < paramFloat1) || (paramFloat5 < paramFloat2) || (paramFloat6 < paramFloat3)) {
/* 379 */       makeEmpty();
/* 380 */       return;
/*     */     }
/*     */ 
/* 383 */     this.minX = Math.max(this.minX, paramFloat1);
/* 384 */     this.minY = Math.max(this.minY, paramFloat2);
/* 385 */     this.maxX = Math.min(this.maxX, paramFloat4);
/* 386 */     this.maxY = Math.min(this.maxY, paramFloat5);
/*     */   }
/*     */ 
/*     */   public boolean contains(Point2D paramPoint2D) {
/* 390 */     if ((paramPoint2D == null) || (isEmpty())) return false;
/* 391 */     return (paramPoint2D.x >= this.minX) && (paramPoint2D.x <= this.maxX) && (paramPoint2D.y >= this.minY) && (paramPoint2D.y <= this.maxY);
/*     */   }
/*     */ 
/*     */   public boolean contains(float paramFloat1, float paramFloat2) {
/* 395 */     if (isEmpty()) return false;
/* 396 */     return (paramFloat1 >= this.minX) && (paramFloat1 <= this.maxX) && (paramFloat2 >= this.minY) && (paramFloat2 <= this.maxY);
/*     */   }
/*     */ 
/*     */   public boolean contains(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
/* 400 */     if (isEmpty()) return false;
/* 401 */     return (contains(paramFloat1, paramFloat2)) && (contains(paramFloat1 + paramFloat3, paramFloat2 + paramFloat4));
/*     */   }
/*     */ 
/*     */   public boolean intersects(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
/* 405 */     if (isEmpty()) return false;
/* 406 */     return (paramFloat1 + paramFloat3 >= this.minX) && (paramFloat2 + paramFloat4 >= this.minY) && (paramFloat1 <= this.maxX) && (paramFloat2 <= this.maxY);
/*     */   }
/*     */ 
/*     */   public boolean intersects(BaseBounds paramBaseBounds)
/*     */   {
/* 413 */     if ((paramBaseBounds == null) || (paramBaseBounds.isEmpty()) || (isEmpty())) {
/* 414 */       return false;
/*     */     }
/* 416 */     return (paramBaseBounds.getMaxX() >= this.minX) && (paramBaseBounds.getMaxY() >= this.minY) && (paramBaseBounds.getMaxZ() >= getMinZ()) && (paramBaseBounds.getMinX() <= this.maxX) && (paramBaseBounds.getMinY() <= this.maxY) && (paramBaseBounds.getMinZ() <= getMaxZ());
/*     */   }
/*     */ 
/*     */   public boolean disjoint(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/* 425 */     if (isEmpty()) return true;
/* 426 */     return (paramFloat1 + paramFloat3 < this.minX) || (paramFloat2 + paramFloat4 < this.minY) || (paramFloat1 > this.maxX) || (paramFloat2 > this.maxY);
/*     */   }
/*     */ 
/*     */   public boolean disjoint(RectBounds paramRectBounds)
/*     */   {
/* 433 */     if ((paramRectBounds == null) || (paramRectBounds.isEmpty()) || (isEmpty())) {
/* 434 */       return true;
/*     */     }
/* 436 */     return (paramRectBounds.getMaxX() < this.minX) || (paramRectBounds.getMaxY() < this.minY) || (paramRectBounds.getMinX() > this.maxX) || (paramRectBounds.getMinY() > this.maxY);
/*     */   }
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/* 443 */     return (this.maxX < this.minX) || (this.maxY < this.minY);
/*     */   }
/*     */ 
/*     */   public void roundOut()
/*     */   {
/* 452 */     this.minX = ((float)Math.floor(this.minX));
/* 453 */     this.minY = ((float)Math.floor(this.minY));
/* 454 */     this.maxX = ((float)Math.ceil(this.maxX));
/* 455 */     this.maxY = ((float)Math.ceil(this.maxY));
/*     */   }
/*     */ 
/*     */   public void grow(float paramFloat1, float paramFloat2) {
/* 459 */     this.minX -= paramFloat1;
/* 460 */     this.maxX += paramFloat1;
/* 461 */     this.minY -= paramFloat2;
/* 462 */     this.maxY += paramFloat2;
/*     */   }
/*     */ 
/*     */   public BaseBounds deriveWithPadding(float paramFloat1, float paramFloat2, float paramFloat3) {
/* 466 */     if (paramFloat3 == 0.0F) {
/* 467 */       grow(paramFloat1, paramFloat2);
/* 468 */       return this;
/*     */     }
/* 470 */     BoxBounds localBoxBounds = new BoxBounds(this.minX, this.minY, 0.0F, this.maxX, this.maxY, 0.0F);
/* 471 */     localBoxBounds.grow(paramFloat1, paramFloat2, paramFloat3);
/* 472 */     return localBoxBounds;
/*     */   }
/*     */ 
/*     */   public RectBounds makeEmpty()
/*     */   {
/* 479 */     this.minX = (this.minY = 0.0F);
/* 480 */     this.maxX = (this.maxY = -1.0F);
/* 481 */     return this;
/*     */   }
/*     */ 
/*     */   protected void sortMinMax()
/*     */   {
/*     */     float f;
/* 485 */     if (this.minX > this.maxX) {
/* 486 */       f = this.maxX;
/* 487 */       this.maxX = this.minX;
/* 488 */       this.minX = f;
/*     */     }
/* 490 */     if (this.minY > this.maxY) {
/* 491 */       f = this.maxY;
/* 492 */       this.maxY = this.minY;
/* 493 */       this.minY = f;
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 499 */     if (paramObject == null) return false;
/* 500 */     if (getClass() != paramObject.getClass()) return false;
/*     */ 
/* 502 */     RectBounds localRectBounds = (RectBounds)paramObject;
/* 503 */     if (this.minX != localRectBounds.getMinX()) return false;
/* 504 */     if (this.minY != localRectBounds.getMinY()) return false;
/* 505 */     if (this.maxX != localRectBounds.getMaxX()) return false;
/* 506 */     if (this.maxY != localRectBounds.getMaxY()) return false;
/* 507 */     return true;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 512 */     int i = 7;
/* 513 */     i = 79 * i + Float.floatToIntBits(this.minX);
/* 514 */     i = 79 * i + Float.floatToIntBits(this.minY);
/* 515 */     i = 79 * i + Float.floatToIntBits(this.maxX);
/* 516 */     i = 79 * i + Float.floatToIntBits(this.maxY);
/* 517 */     return i;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 522 */     return "RectBounds { minX:" + this.minX + ", minY:" + this.minY + ", maxX:" + this.maxX + ", maxY:" + this.maxY + "}";
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.geom.RectBounds
 * JD-Core Version:    0.6.2
 */