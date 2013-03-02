/*     */ package com.sun.javafx.geom;
/*     */ 
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ 
/*     */ public class PickRay
/*     */ {
/*  32 */   private Vec3d origin = new Vec3d();
/*  33 */   private Vec3d direction = new Vec3d();
/*     */   static final double EPS = 9.999999747378752E-06D;
/*     */   private static final double EPSILON_ABSOLUTE = 1.E-05D;
/* 164 */   private final Vec3d tempV3d = new Vec3d();
/* 165 */   private final Vec3d vec0 = new Vec3d();
/* 166 */   private final Vec3d vec1 = new Vec3d();
/* 167 */   private final Vec3d pNrm = new Vec3d();
/*     */ 
/* 439 */   private final Vec3d endPt = new Vec3d();
/*     */ 
/*     */   public PickRay()
/*     */   {
/*     */   }
/*     */ 
/*     */   public PickRay(Vec3d paramVec3d1, Vec3d paramVec3d2)
/*     */   {
/*  41 */     setOrigin(paramVec3d1);
/*  42 */     setDirection(paramVec3d2);
/*     */   }
/*     */ 
/*     */   public PickRay copy() {
/*  46 */     return new PickRay(this.origin, this.direction);
/*     */   }
/*     */ 
/*     */   public void setOrigin(Vec3d paramVec3d)
/*     */   {
/*  55 */     this.origin.set(paramVec3d);
/*     */   }
/*     */ 
/*     */   public Vec3d getOrigin(Vec3d paramVec3d) {
/*  59 */     if (paramVec3d == null) {
/*  60 */       paramVec3d = new Vec3d();
/*     */     }
/*  62 */     paramVec3d.set(this.origin);
/*  63 */     return paramVec3d;
/*     */   }
/*     */ 
/*     */   public Vec3d getOriginNoClone() {
/*  67 */     return this.origin;
/*     */   }
/*     */ 
/*     */   public void setDirection(Vec3d paramVec3d)
/*     */   {
/*  77 */     this.direction.set(paramVec3d);
/*     */   }
/*     */ 
/*     */   public Vec3d getDirection(Vec3d paramVec3d) {
/*  81 */     if (paramVec3d == null) {
/*  82 */       paramVec3d = new Vec3d();
/*     */     }
/*  84 */     paramVec3d.set(this.direction);
/*  85 */     return paramVec3d;
/*     */   }
/*     */ 
/*     */   public Vec3d getDirectionNoClone() {
/*  89 */     return this.direction;
/*     */   }
/*     */ 
/*     */   public double distance(Vec3d paramVec3d) {
/*  93 */     double d1 = paramVec3d.x - this.origin.x;
/*  94 */     double d2 = paramVec3d.y - this.origin.y;
/*  95 */     double d3 = paramVec3d.z - this.origin.z;
/*  96 */     return Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
/*     */   }
/*     */ 
/*     */   public boolean intersect(Vec3f[] paramArrayOfVec3f, double[] paramArrayOfDouble, Vec3d paramVec3d) {
/* 100 */     return intersectRayOrSegment(paramArrayOfVec3f, this.origin, this.direction, paramArrayOfDouble, paramVec3d, false);
/*     */   }
/*     */ 
/*     */   public Point2D projectToZeroPlane(BaseTransform paramBaseTransform, boolean paramBoolean, Vec3d paramVec3d, Point2D paramPoint2D)
/*     */   {
/* 126 */     if (paramVec3d == null) {
/* 127 */       paramVec3d = new Vec3d();
/*     */     }
/* 129 */     paramBaseTransform.transform(this.origin, paramVec3d);
/* 130 */     double d1 = paramVec3d.x;
/* 131 */     double d2 = paramVec3d.y;
/* 132 */     double d3 = paramVec3d.z;
/* 133 */     paramVec3d.add(this.origin, this.direction);
/* 134 */     paramBaseTransform.transform(paramVec3d, paramVec3d);
/* 135 */     double d4 = paramVec3d.x - d1;
/* 136 */     double d5 = paramVec3d.y - d2;
/* 137 */     double d6 = paramVec3d.z - d3;
/*     */ 
/* 139 */     if (almostZero(d6)) {
/* 140 */       return null;
/*     */     }
/* 142 */     double d7 = -d3 / d6;
/* 143 */     if ((paramBoolean) && (d7 < 0.0D))
/*     */     {
/* 145 */       return null;
/*     */     }
/* 147 */     if (paramPoint2D == null) {
/* 148 */       paramPoint2D = new Point2D();
/*     */     }
/* 150 */     paramPoint2D.setLocation((float)(d1 + d4 * d7), (float)(d2 + d5 * d7));
/*     */ 
/* 152 */     return paramPoint2D;
/*     */   }
/*     */ 
/*     */   static boolean almostZero(double paramDouble)
/*     */   {
/* 161 */     return (paramDouble < 1.E-05D) && (paramDouble > -1.E-05D);
/*     */   }
/*     */ 
/*     */   boolean intersectRayOrSegment(Vec3f[] paramArrayOfVec3f, Vec3d paramVec3d1, Vec3d paramVec3d2, double[] paramArrayOfDouble, Vec3d paramVec3d3, boolean paramBoolean)
/*     */   {
/* 177 */     double d4 = 0.0D;
/* 178 */     double d5 = 0.0D;
/*     */ 
/* 180 */     boolean bool = false;
/* 181 */     int k = 0; int m = 0;
/*     */ 
/* 184 */     for (int i = 0; i < paramArrayOfVec3f.length; i++) {
/* 185 */       if (i != paramArrayOfVec3f.length - 1)
/* 186 */         m = i + 1;
/*     */       else {
/* 188 */         m = 0;
/*     */       }
/* 190 */       this.vec0.sub(paramArrayOfVec3f[m], paramArrayOfVec3f[i]);
/* 191 */       if (this.vec0.length() > 0.0D)
/*     */       {
/*     */         break;
/*     */       }
/*     */     }
/* 196 */     for (int j = m; j < paramArrayOfVec3f.length; j++) {
/* 197 */       if (j != paramArrayOfVec3f.length - 1)
/* 198 */         k = j + 1;
/*     */       else {
/* 200 */         k = 0;
/*     */       }
/* 202 */       this.vec1.sub(paramArrayOfVec3f[k], paramArrayOfVec3f[j]);
/* 203 */       if (this.vec1.length() > 0.0D)
/*     */       {
/*     */         break;
/*     */       }
/*     */     }
/* 208 */     this.pNrm.cross(this.vec0, this.vec1);
/*     */ 
/* 210 */     if ((this.vec1.length() == 0.0F) || (this.pNrm.length() == 0.0F)) {
/* 211 */       return false;
/*     */     }
/*     */ 
/* 229 */     d5 = this.pNrm.dot(paramVec3d2);
/*     */ 
/* 232 */     if (d5 == 0.0D) {
/* 233 */       return false;
/*     */     }
/*     */ 
/* 256 */     this.tempV3d.set(paramArrayOfVec3f[0]);
/* 257 */     d4 = this.pNrm.dot(this.tempV3d);
/* 258 */     this.tempV3d.set(paramVec3d1);
/*     */ 
/* 264 */     paramArrayOfDouble[0] = ((d4 - this.pNrm.dot(this.tempV3d)) / d5);
/*     */ 
/* 267 */     if ((paramArrayOfDouble[0] < -9.999999747378752E-06D) || ((paramBoolean) && (paramArrayOfDouble[0] > 1.000009999999747D)))
/*     */     {
/* 271 */       return false;
/*     */     }
/*     */ 
/* 276 */     if (paramVec3d3 == null) {
/* 277 */       paramVec3d3 = new Vec3d();
/*     */     }
/* 279 */     paramVec3d1.x += paramVec3d2.x * paramArrayOfDouble[0];
/* 280 */     paramVec3d1.y += paramVec3d2.y * paramArrayOfDouble[0];
/* 281 */     paramVec3d1.z += paramVec3d2.z * paramArrayOfDouble[0];
/*     */ 
/* 285 */     double d1 = Math.abs(this.pNrm.x);
/* 286 */     double d2 = Math.abs(this.pNrm.y);
/* 287 */     double d3 = Math.abs(this.pNrm.z);
/*     */ 
/* 291 */     double d8 = 0.0D;
/* 292 */     Vec3f localVec3f1 = paramArrayOfVec3f[(paramArrayOfVec3f.length - 1)];
/* 293 */     Vec3f localVec3f2 = paramArrayOfVec3f[0];
/*     */ 
/* 295 */     bool = true;
/*     */     double d6;
/*     */     double d7;
/* 297 */     if (d1 > d2) {
/* 298 */       if (d1 < d3) {
/* 299 */         for (i = 0; i < paramArrayOfVec3f.length; i++) {
/* 300 */           localVec3f1 = paramArrayOfVec3f[i];
/* 301 */           localVec3f2 = i != paramArrayOfVec3f.length - 1 ? paramArrayOfVec3f[(i + 1)] : paramArrayOfVec3f[0];
/* 302 */           d6 = (paramVec3d3.y - localVec3f1.y) * (localVec3f2.x - localVec3f1.x) - (paramVec3d3.x - localVec3f1.x) * (localVec3f2.y - localVec3f1.y);
/*     */ 
/* 304 */           if (isNonZero(d6)) {
/* 305 */             if (d6 * d8 < 0.0D) {
/* 306 */               bool = false;
/* 307 */               break;
/*     */             }
/* 309 */             d8 = d6;
/*     */           } else {
/* 311 */             d7 = localVec3f2.y - localVec3f1.y;
/* 312 */             if (isNonZero(d7)) {
/* 313 */               d7 = (paramVec3d3.y - localVec3f1.y) / d7;
/* 314 */               bool = (d7 > -9.999999747378752E-06D) && (d7 < 1.000009999999747D);
/* 315 */               break;
/*     */             }
/* 317 */             d7 = localVec3f2.x - localVec3f1.x;
/* 318 */             if (isNonZero(d7)) {
/* 319 */               d7 = (paramVec3d3.x - localVec3f1.x) / d7;
/* 320 */               bool = (d7 > -9.999999747378752E-06D) && (d7 < 1.000009999999747D);
/* 321 */               break;
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 332 */       for (i = 0; i < paramArrayOfVec3f.length; i++) {
/* 333 */         localVec3f1 = paramArrayOfVec3f[i];
/* 334 */         localVec3f2 = i != paramArrayOfVec3f.length - 1 ? paramArrayOfVec3f[(i + 1)] : paramArrayOfVec3f[0];
/* 335 */         d6 = (paramVec3d3.y - localVec3f1.y) * (localVec3f2.z - localVec3f1.z) - (paramVec3d3.z - localVec3f1.z) * (localVec3f2.y - localVec3f1.y);
/*     */ 
/* 337 */         if (isNonZero(d6)) {
/* 338 */           if (d6 * d8 < 0.0D) {
/* 339 */             bool = false;
/* 340 */             break;
/*     */           }
/* 342 */           d8 = d6;
/*     */         } else {
/* 344 */           d7 = localVec3f2.y - localVec3f1.y;
/*     */ 
/* 346 */           if (isNonZero(d7)) {
/* 347 */             d7 = (paramVec3d3.y - localVec3f1.y) / d7;
/* 348 */             bool = (d7 > -9.999999747378752E-06D) && (d7 < 1.000009999999747D);
/* 349 */             break;
/*     */           }
/*     */ 
/* 352 */           d7 = localVec3f2.z - localVec3f1.z;
/* 353 */           if (isNonZero(d7)) {
/* 354 */             d7 = (paramVec3d3.z - localVec3f1.z) / d7;
/* 355 */             bool = (d7 > -9.999999747378752E-06D) && (d7 < 1.000009999999747D);
/* 356 */             break;
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 365 */     if (d2 < d3) {
/* 366 */       for (i = 0; i < paramArrayOfVec3f.length; i++) {
/* 367 */         localVec3f1 = paramArrayOfVec3f[i];
/* 368 */         localVec3f2 = i != paramArrayOfVec3f.length - 1 ? paramArrayOfVec3f[(i + 1)] : paramArrayOfVec3f[0];
/* 369 */         d6 = (paramVec3d3.y - localVec3f1.y) * (localVec3f2.x - localVec3f1.x) - (paramVec3d3.x - localVec3f1.x) * (localVec3f2.y - localVec3f1.y);
/*     */ 
/* 371 */         if (isNonZero(d6)) {
/* 372 */           if (d6 * d8 < 0.0D) {
/* 373 */             bool = false;
/* 374 */             break;
/*     */           }
/* 376 */           d8 = d6;
/*     */         } else {
/* 378 */           d7 = localVec3f2.y - localVec3f1.y;
/* 379 */           if (isNonZero(d7)) {
/* 380 */             d7 = (paramVec3d3.y - localVec3f1.y) / d7;
/* 381 */             bool = (d7 > -9.999999747378752E-06D) && (d7 < 1.000009999999747D);
/* 382 */             break;
/*     */           }
/* 384 */           d7 = localVec3f2.x - localVec3f1.x;
/* 385 */           if (isNonZero(d7)) {
/* 386 */             d7 = (paramVec3d3.x - localVec3f1.x) / d7;
/* 387 */             bool = (d7 > -9.999999747378752E-06D) && (d7 < 1.000009999999747D);
/* 388 */             break;
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 396 */     for (i = 0; i < paramArrayOfVec3f.length; i++) {
/* 397 */       localVec3f1 = paramArrayOfVec3f[i];
/* 398 */       localVec3f2 = i != paramArrayOfVec3f.length - 1 ? paramArrayOfVec3f[(i + 1)] : paramArrayOfVec3f[0];
/* 399 */       d6 = (paramVec3d3.x - localVec3f1.x) * (localVec3f2.z - localVec3f1.z) - (paramVec3d3.z - localVec3f1.z) * (localVec3f2.x - localVec3f1.x);
/*     */ 
/* 401 */       if (isNonZero(d6)) {
/* 402 */         if (d6 * d8 < 0.0D) {
/* 403 */           bool = false;
/* 404 */           break;
/*     */         }
/* 406 */         d8 = d6;
/*     */       } else {
/* 408 */         d7 = localVec3f2.x - localVec3f1.x;
/* 409 */         if (isNonZero(d7)) {
/* 410 */           d7 = (paramVec3d3.x - localVec3f1.x) / d7;
/* 411 */           bool = (d7 > -9.999999747378752E-06D) && (d7 < 1.000009999999747D);
/* 412 */           break;
/*     */         }
/* 414 */         d7 = localVec3f2.z - localVec3f1.z;
/* 415 */         if (isNonZero(d7)) {
/* 416 */           d7 = (paramVec3d3.z - localVec3f1.z) / d7;
/* 417 */           bool = (d7 > -9.999999747378752E-06D) && (d7 < 1.000009999999747D);
/* 418 */           break;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 428 */     if (bool) {
/* 429 */       paramArrayOfDouble[0] *= paramVec3d2.length();
/*     */     }
/* 431 */     return bool;
/*     */   }
/*     */ 
/*     */   private static boolean isNonZero(double paramDouble) {
/* 435 */     return (paramDouble > 9.999999747378752E-06D) || (paramDouble < -9.999999747378752E-06D);
/*     */   }
/*     */ 
/*     */   public void transform(BaseTransform paramBaseTransform)
/*     */   {
/* 442 */     this.endPt.add(this.origin, this.direction);
/* 443 */     paramBaseTransform.transform(this.origin, this.origin);
/* 444 */     paramBaseTransform.transform(this.endPt, this.endPt);
/* 445 */     this.direction.sub(this.endPt, this.origin);
/* 446 */     this.direction.normalize();
/*     */   }
/*     */ 
/*     */   public PickRay project(BaseTransform paramBaseTransform, boolean paramBoolean, Vec3d paramVec3d, Point2D paramPoint2D)
/*     */   {
/* 453 */     if (paramVec3d == null) {
/* 454 */       paramVec3d = new Vec3d();
/*     */     }
/* 456 */     paramBaseTransform.transform(this.origin, paramVec3d);
/* 457 */     double d1 = paramVec3d.x;
/* 458 */     double d2 = paramVec3d.y;
/* 459 */     double d3 = paramVec3d.z;
/* 460 */     paramVec3d.add(this.origin, this.direction);
/* 461 */     paramBaseTransform.transform(paramVec3d, paramVec3d);
/* 462 */     double d4 = paramVec3d.x - d1;
/* 463 */     double d5 = paramVec3d.y - d2;
/* 464 */     double d6 = paramVec3d.z - d3;
/*     */ 
/* 466 */     PickRay localPickRay = new PickRay();
/* 467 */     localPickRay.origin.x = d1;
/* 468 */     localPickRay.origin.y = d2;
/* 469 */     localPickRay.origin.z = d3;
/*     */ 
/* 471 */     localPickRay.direction.x = d4;
/* 472 */     localPickRay.direction.y = d5;
/* 473 */     localPickRay.direction.z = d6;
/*     */ 
/* 475 */     return localPickRay;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 480 */     return "origin: " + this.origin + "  direction: " + this.direction;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.geom.PickRay
 * JD-Core Version:    0.6.2
 */