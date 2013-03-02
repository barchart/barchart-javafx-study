/*     */ package com.sun.javafx.geom;
/*     */ 
/*     */ public class BoxBounds extends BaseBounds
/*     */ {
/*     */   private float minX;
/*     */   private float maxX;
/*     */   private float minY;
/*     */   private float maxY;
/*     */   private float minZ;
/*     */   private float maxZ;
/*     */ 
/*     */   public BoxBounds()
/*     */   {
/*  34 */     this.minX = (this.minY = this.minZ = 0.0F);
/*  35 */     this.maxX = (this.maxY = this.maxZ = -1.0F);
/*     */   }
/*     */ 
/*     */   public BaseBounds copy() {
/*  39 */     return new BoxBounds(this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ);
/*     */   }
/*     */ 
/*     */   public BoxBounds(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
/*     */   {
/*  47 */     setBounds(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6);
/*     */   }
/*     */ 
/*     */   public BoxBounds(BoxBounds paramBoxBounds)
/*     */   {
/*  55 */     setBounds(paramBoxBounds);
/*     */   }
/*     */ 
/*     */   public BaseBounds.BoundsType getBoundsType() {
/*  59 */     return BaseBounds.BoundsType.BOX;
/*     */   }
/*     */ 
/*     */   public boolean is2D() {
/*  63 */     return false;
/*     */   }
/*     */ 
/*     */   public float getWidth()
/*     */   {
/*  71 */     return this.maxX - this.minX;
/*     */   }
/*     */ 
/*     */   public float getHeight()
/*     */   {
/*  79 */     return this.maxY - this.minY;
/*     */   }
/*     */ 
/*     */   public float getDepth()
/*     */   {
/*  87 */     return this.maxZ - this.minZ;
/*     */   }
/*     */ 
/*     */   public float getMinX() {
/*  91 */     return this.minX;
/*     */   }
/*     */ 
/*     */   public void setMinX(float paramFloat) {
/*  95 */     this.minX = paramFloat;
/*     */   }
/*     */ 
/*     */   public float getMinY() {
/*  99 */     return this.minY;
/*     */   }
/*     */ 
/*     */   public void setMinY(float paramFloat) {
/* 103 */     this.minY = paramFloat;
/*     */   }
/*     */ 
/*     */   public float getMinZ() {
/* 107 */     return this.minZ;
/*     */   }
/*     */ 
/*     */   public void setMinZ(float paramFloat) {
/* 111 */     this.minZ = paramFloat;
/*     */   }
/*     */ 
/*     */   public float getMaxX() {
/* 115 */     return this.maxX;
/*     */   }
/*     */ 
/*     */   public void setMaxX(float paramFloat) {
/* 119 */     this.maxX = paramFloat;
/*     */   }
/*     */ 
/*     */   public float getMaxY() {
/* 123 */     return this.maxY;
/*     */   }
/*     */ 
/*     */   public void setMaxY(float paramFloat) {
/* 127 */     this.maxY = paramFloat;
/*     */   }
/*     */ 
/*     */   public float getMaxZ() {
/* 131 */     return this.maxZ;
/*     */   }
/*     */ 
/*     */   public void setMaxZ(float paramFloat) {
/* 135 */     this.maxZ = paramFloat;
/*     */   }
/*     */ 
/*     */   public Vec2f getMin(Vec2f paramVec2f) {
/* 139 */     if (paramVec2f == null) {
/* 140 */       paramVec2f = new Vec2f();
/*     */     }
/* 142 */     paramVec2f.x = this.minX;
/* 143 */     paramVec2f.y = this.minY;
/* 144 */     return paramVec2f;
/*     */   }
/*     */ 
/*     */   public Vec2f getMax(Vec2f paramVec2f) {
/* 148 */     if (paramVec2f == null) {
/* 149 */       paramVec2f = new Vec2f();
/*     */     }
/* 151 */     paramVec2f.x = this.maxX;
/* 152 */     paramVec2f.y = this.maxY;
/* 153 */     return paramVec2f;
/*     */   }
/*     */ 
/*     */   public Vec3f getMin(Vec3f paramVec3f) {
/* 157 */     if (paramVec3f == null) {
/* 158 */       paramVec3f = new Vec3f();
/*     */     }
/* 160 */     paramVec3f.x = this.minX;
/* 161 */     paramVec3f.y = this.minY;
/* 162 */     paramVec3f.z = this.minZ;
/* 163 */     return paramVec3f;
/*     */   }
/*     */ 
/*     */   public Vec3f getMax(Vec3f paramVec3f)
/*     */   {
/* 168 */     if (paramVec3f == null) {
/* 169 */       paramVec3f = new Vec3f();
/*     */     }
/* 171 */     paramVec3f.x = this.maxX;
/* 172 */     paramVec3f.y = this.maxY;
/* 173 */     paramVec3f.z = this.maxZ;
/* 174 */     return paramVec3f;
/*     */   }
/*     */ 
/*     */   public BaseBounds deriveWithUnion(BaseBounds paramBaseBounds)
/*     */   {
/* 179 */     if ((paramBaseBounds.getBoundsType() == BaseBounds.BoundsType.RECTANGLE) || (paramBaseBounds.getBoundsType() == BaseBounds.BoundsType.BOX))
/*     */     {
/* 181 */       unionWith(paramBaseBounds);
/*     */     }
/* 183 */     else throw new UnsupportedOperationException("Unknown BoundsType");
/*     */ 
/* 185 */     return this;
/*     */   }
/*     */ 
/*     */   public BaseBounds deriveWithNewBounds(Rectangle paramRectangle) {
/* 189 */     if ((paramRectangle.width < 0) || (paramRectangle.height < 0)) return makeEmpty();
/* 190 */     setBounds(paramRectangle.x, paramRectangle.y, 0.0F, paramRectangle.x + paramRectangle.width, paramRectangle.y + paramRectangle.height, 0.0F);
/*     */ 
/* 192 */     return this;
/*     */   }
/*     */ 
/*     */   public BaseBounds deriveWithNewBounds(BaseBounds paramBaseBounds) {
/* 196 */     if (paramBaseBounds.isEmpty()) return makeEmpty();
/* 197 */     if ((paramBaseBounds.getBoundsType() == BaseBounds.BoundsType.RECTANGLE) || (paramBaseBounds.getBoundsType() == BaseBounds.BoundsType.BOX))
/*     */     {
/* 199 */       this.minX = paramBaseBounds.getMinX();
/* 200 */       this.minY = paramBaseBounds.getMinY();
/* 201 */       this.minZ = paramBaseBounds.getMinZ();
/* 202 */       this.maxX = paramBaseBounds.getMaxX();
/* 203 */       this.maxY = paramBaseBounds.getMaxY();
/* 204 */       this.maxZ = paramBaseBounds.getMaxZ();
/*     */     } else {
/* 206 */       throw new UnsupportedOperationException("Unknown BoundsType");
/*     */     }
/* 208 */     return this;
/*     */   }
/*     */ 
/*     */   public BaseBounds deriveWithNewBounds(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
/*     */   {
/* 213 */     if ((paramFloat4 < paramFloat1) || (paramFloat5 < paramFloat2) || (paramFloat6 < paramFloat3)) return makeEmpty();
/* 214 */     this.minX = paramFloat1;
/* 215 */     this.minY = paramFloat2;
/* 216 */     this.minZ = paramFloat3;
/* 217 */     this.maxX = paramFloat4;
/* 218 */     this.maxY = paramFloat5;
/* 219 */     this.maxZ = paramFloat6;
/* 220 */     return this;
/*     */   }
/*     */ 
/*     */   public BaseBounds deriveWithNewBoundsAndSort(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
/*     */   {
/* 225 */     setBoundsAndSort(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6);
/* 226 */     return this;
/*     */   }
/*     */ 
/*     */   public final void setBounds(BaseBounds paramBaseBounds)
/*     */   {
/* 234 */     this.minX = paramBaseBounds.getMinX();
/* 235 */     this.minY = paramBaseBounds.getMinY();
/* 236 */     this.minZ = paramBaseBounds.getMinZ();
/* 237 */     this.maxX = paramBaseBounds.getMaxX();
/* 238 */     this.maxY = paramBaseBounds.getMaxY();
/* 239 */     this.maxZ = paramBaseBounds.getMaxZ();
/*     */   }
/*     */ 
/*     */   public final void setBounds(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
/*     */   {
/* 247 */     this.minX = paramFloat1;
/* 248 */     this.minY = paramFloat2;
/* 249 */     this.minZ = paramFloat3;
/* 250 */     this.maxX = paramFloat4;
/* 251 */     this.maxY = paramFloat5;
/* 252 */     this.maxZ = paramFloat6;
/*     */   }
/*     */ 
/*     */   public void setBoundsAndSort(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
/*     */   {
/* 257 */     setBounds(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6);
/* 258 */     sortMinMax();
/*     */   }
/*     */ 
/*     */   public void setBoundsAndSort(Point2D paramPoint2D1, Point2D paramPoint2D2) {
/* 262 */     setBoundsAndSort(paramPoint2D1.x, paramPoint2D1.y, 0.0F, paramPoint2D2.x, paramPoint2D2.y, 0.0F);
/*     */   }
/*     */ 
/*     */   public void unionWith(BaseBounds paramBaseBounds)
/*     */   {
/* 267 */     if (paramBaseBounds.isEmpty()) return;
/* 268 */     if (isEmpty()) {
/* 269 */       setBounds(paramBaseBounds);
/* 270 */       return;
/*     */     }
/*     */ 
/* 273 */     this.minX = Math.min(this.minX, paramBaseBounds.getMinX());
/* 274 */     this.minY = Math.min(this.minY, paramBaseBounds.getMinY());
/* 275 */     this.minZ = Math.min(this.minZ, paramBaseBounds.getMinZ());
/* 276 */     this.maxX = Math.max(this.maxX, paramBaseBounds.getMaxX());
/* 277 */     this.maxY = Math.max(this.maxY, paramBaseBounds.getMaxY());
/* 278 */     this.maxZ = Math.max(this.maxZ, paramBaseBounds.getMaxZ());
/*     */   }
/*     */ 
/*     */   public void unionWith(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
/*     */   {
/* 285 */     if ((paramFloat4 < paramFloat1) || (paramFloat5 < paramFloat2) || (paramFloat6 < paramFloat3)) return;
/* 286 */     if (isEmpty()) {
/* 287 */       setBounds(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6);
/* 288 */       return;
/*     */     }
/*     */ 
/* 291 */     this.minX = Math.min(this.minX, paramFloat1);
/* 292 */     this.minY = Math.min(this.minY, paramFloat2);
/* 293 */     this.minZ = Math.min(this.minZ, paramFloat3);
/* 294 */     this.maxX = Math.max(this.maxX, paramFloat4);
/* 295 */     this.maxY = Math.max(this.maxY, paramFloat5);
/* 296 */     this.maxZ = Math.max(this.maxZ, paramFloat6);
/*     */   }
/*     */ 
/*     */   public void add(float paramFloat1, float paramFloat2, float paramFloat3) {
/* 300 */     unionWith(paramFloat1, paramFloat2, paramFloat3, paramFloat1, paramFloat2, paramFloat3);
/*     */   }
/*     */ 
/*     */   public void add(Point2D paramPoint2D) {
/* 304 */     add(paramPoint2D.x, paramPoint2D.y, 0.0F);
/*     */   }
/*     */ 
/*     */   public void intersectWith(Rectangle paramRectangle) {
/* 308 */     float f1 = paramRectangle.x;
/* 309 */     float f2 = paramRectangle.y;
/* 310 */     intersectWith(f1, f2, 0.0F, f1 + paramRectangle.width, f2 + paramRectangle.height, 0.0F);
/*     */   }
/*     */ 
/*     */   public void intersectWith(BaseBounds paramBaseBounds)
/*     */   {
/* 316 */     if (isEmpty()) return;
/* 317 */     if (paramBaseBounds.isEmpty()) {
/* 318 */       makeEmpty();
/* 319 */       return;
/*     */     }
/*     */ 
/* 322 */     this.minX = Math.max(this.minX, paramBaseBounds.getMinX());
/* 323 */     this.minY = Math.max(this.minY, paramBaseBounds.getMinY());
/* 324 */     this.minZ = Math.max(this.minZ, paramBaseBounds.getMinZ());
/* 325 */     this.maxX = Math.min(this.maxX, paramBaseBounds.getMaxX());
/* 326 */     this.maxY = Math.min(this.maxY, paramBaseBounds.getMaxY());
/* 327 */     this.maxZ = Math.min(this.maxZ, paramBaseBounds.getMaxZ());
/*     */   }
/*     */ 
/*     */   public void intersectWith(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
/*     */   {
/* 333 */     if (isEmpty()) return;
/* 334 */     if ((paramFloat4 < paramFloat1) || (paramFloat5 < paramFloat2) || (paramFloat6 < paramFloat3)) {
/* 335 */       makeEmpty();
/* 336 */       return;
/*     */     }
/*     */ 
/* 339 */     this.minX = Math.max(this.minX, paramFloat1);
/* 340 */     this.minY = Math.max(this.minY, paramFloat2);
/* 341 */     this.minZ = Math.max(this.minZ, paramFloat3);
/* 342 */     this.maxX = Math.min(this.maxX, paramFloat4);
/* 343 */     this.maxY = Math.min(this.maxY, paramFloat5);
/* 344 */     this.maxZ = Math.min(this.maxZ, paramFloat6);
/*     */   }
/*     */ 
/*     */   public boolean contains(Point2D paramPoint2D) {
/* 348 */     if ((paramPoint2D == null) || (isEmpty())) return false;
/* 349 */     return contains(paramPoint2D.x, paramPoint2D.y, 0.0F);
/*     */   }
/*     */ 
/*     */   public boolean contains(float paramFloat1, float paramFloat2) {
/* 353 */     if (isEmpty()) return false;
/* 354 */     return contains(paramFloat1, paramFloat2, 0.0F);
/*     */   }
/*     */ 
/*     */   public boolean contains(float paramFloat1, float paramFloat2, float paramFloat3) {
/* 358 */     if (isEmpty()) return false;
/* 359 */     return (paramFloat1 >= this.minX) && (paramFloat1 <= this.maxX) && (paramFloat2 >= this.minY) && (paramFloat2 <= this.maxY) && (paramFloat3 >= this.minZ) && (paramFloat3 <= this.maxZ);
/*     */   }
/*     */ 
/*     */   public boolean contains(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
/*     */   {
/* 365 */     if (isEmpty()) return false;
/* 366 */     return (contains(paramFloat1, paramFloat2, paramFloat3)) && (contains(paramFloat1 + paramFloat4, paramFloat2 + paramFloat5, paramFloat3 + paramFloat6));
/*     */   }
/*     */ 
/*     */   public boolean intersects(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
/* 370 */     return intersects(paramFloat1, paramFloat2, 0.0F, paramFloat3, paramFloat4, 0.0F);
/*     */   }
/*     */ 
/*     */   public boolean intersects(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
/*     */   {
/* 375 */     if (isEmpty()) return false;
/* 376 */     return (paramFloat1 + paramFloat4 >= this.minX) && (paramFloat2 + paramFloat5 >= this.minY) && (paramFloat3 + paramFloat6 >= this.minZ) && (paramFloat1 <= this.maxX) && (paramFloat2 <= this.maxY) && (paramFloat3 <= this.maxZ);
/*     */   }
/*     */ 
/*     */   public boolean intersects(BaseBounds paramBaseBounds)
/*     */   {
/* 385 */     if ((paramBaseBounds == null) || (paramBaseBounds.isEmpty()) || (isEmpty())) {
/* 386 */       return false;
/*     */     }
/* 388 */     return (paramBaseBounds.getMaxX() >= this.minX) && (paramBaseBounds.getMaxY() >= this.minY) && (paramBaseBounds.getMaxZ() >= this.minZ) && (paramBaseBounds.getMinX() <= this.maxX) && (paramBaseBounds.getMinY() <= this.maxY) && (paramBaseBounds.getMinZ() <= this.maxZ);
/*     */   }
/*     */ 
/*     */   public boolean disjoint(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/* 397 */     return disjoint(paramFloat1, paramFloat2, 0.0F, paramFloat3, paramFloat4, 0.0F);
/*     */   }
/*     */ 
/*     */   public boolean disjoint(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
/*     */   {
/* 402 */     if (isEmpty()) return true;
/* 403 */     return (paramFloat1 + paramFloat4 < this.minX) || (paramFloat2 + paramFloat5 < this.minY) || (paramFloat3 + paramFloat6 < this.minZ) || (paramFloat1 > this.maxX) || (paramFloat2 > this.maxY) || (paramFloat3 > this.maxZ);
/*     */   }
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/* 412 */     return (this.maxX < this.minX) || (this.maxY < this.minY) || (this.maxZ < this.minZ);
/*     */   }
/*     */ 
/*     */   public void roundOut()
/*     */   {
/* 421 */     this.minX = ((float)Math.floor(this.minX));
/* 422 */     this.minY = ((float)Math.floor(this.minY));
/* 423 */     this.minZ = ((float)Math.floor(this.minZ));
/* 424 */     this.maxX = ((float)Math.ceil(this.maxX));
/* 425 */     this.maxY = ((float)Math.ceil(this.maxY));
/* 426 */     this.maxZ = ((float)Math.ceil(this.maxZ));
/*     */   }
/*     */ 
/*     */   public void grow(float paramFloat1, float paramFloat2, float paramFloat3) {
/* 430 */     this.minX -= paramFloat1;
/* 431 */     this.maxX += paramFloat1;
/* 432 */     this.minY -= paramFloat2;
/* 433 */     this.maxY += paramFloat2;
/* 434 */     this.minZ -= paramFloat3;
/* 435 */     this.maxZ += paramFloat3;
/*     */   }
/*     */ 
/*     */   public BaseBounds deriveWithPadding(float paramFloat1, float paramFloat2, float paramFloat3) {
/* 439 */     grow(paramFloat1, paramFloat2, paramFloat3);
/* 440 */     return this;
/*     */   }
/*     */ 
/*     */   public BoxBounds makeEmpty()
/*     */   {
/* 448 */     this.minX = (this.minY = this.minZ = 0.0F);
/* 449 */     this.maxX = (this.maxY = this.maxZ = -1.0F);
/* 450 */     return this;
/*     */   }
/*     */ 
/*     */   protected void sortMinMax()
/*     */   {
/*     */     float f;
/* 454 */     if (this.minX > this.maxX) {
/* 455 */       f = this.maxX;
/* 456 */       this.maxX = this.minX;
/* 457 */       this.minX = f;
/*     */     }
/* 459 */     if (this.minY > this.maxY) {
/* 460 */       f = this.maxY;
/* 461 */       this.maxY = this.minY;
/* 462 */       this.minY = f;
/*     */     }
/* 464 */     if (this.minZ > this.maxZ) {
/* 465 */       f = this.maxZ;
/* 466 */       this.maxZ = this.minZ;
/* 467 */       this.minZ = f;
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 473 */     if (paramObject == null) return false;
/* 474 */     if (getClass() != paramObject.getClass()) return false;
/*     */ 
/* 476 */     BoxBounds localBoxBounds = (BoxBounds)paramObject;
/* 477 */     if (this.minX != localBoxBounds.getMinX()) return false;
/* 478 */     if (this.minY != localBoxBounds.getMinY()) return false;
/* 479 */     if (this.minZ != localBoxBounds.getMinZ()) return false;
/* 480 */     if (this.maxX != localBoxBounds.getMaxX()) return false;
/* 481 */     if (this.maxY != localBoxBounds.getMaxY()) return false;
/* 482 */     if (this.maxZ != localBoxBounds.getMaxZ()) return false;
/* 483 */     return true;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 488 */     int i = 7;
/* 489 */     i = 79 * i + Float.floatToIntBits(this.minX);
/* 490 */     i = 79 * i + Float.floatToIntBits(this.minY);
/* 491 */     i = 79 * i + Float.floatToIntBits(this.minZ);
/* 492 */     i = 79 * i + Float.floatToIntBits(this.maxX);
/* 493 */     i = 79 * i + Float.floatToIntBits(this.maxY);
/* 494 */     i = 79 * i + Float.floatToIntBits(this.maxZ);
/*     */ 
/* 496 */     return i;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 501 */     return "BoxBounds { minX:" + this.minX + ", minY:" + this.minY + ", minZ:" + this.minZ + ", maxX:" + this.maxX + ", maxY:" + this.maxY + ", maxZ:" + this.maxZ + "}";
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.geom.BoxBounds
 * JD-Core Version:    0.6.2
 */