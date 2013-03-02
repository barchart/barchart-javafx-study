/*     */ package com.sun.prism;
/*     */ 
/*     */ import com.sun.javafx.geom.Area;
/*     */ import com.sun.javafx.geom.GeneralShapePair;
/*     */ import com.sun.javafx.geom.Path2D;
/*     */ import com.sun.javafx.geom.PathConsumer2D;
/*     */ import com.sun.javafx.geom.PathIterator;
/*     */ import com.sun.javafx.geom.RoundRectangle2D;
/*     */ import com.sun.javafx.geom.Shape;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.openpisces.Dasher;
/*     */ import com.sun.openpisces.Stroker;
/*     */ import com.sun.prism.impl.shape.OpenPiscesPrismUtils;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ public final class BasicStroke
/*     */ {
/*     */   public static final int CAP_BUTT = 0;
/*     */   public static final int CAP_ROUND = 1;
/*     */   public static final int CAP_SQUARE = 2;
/*     */   public static final int JOIN_BEVEL = 2;
/*     */   public static final int JOIN_MITER = 0;
/*     */   public static final int JOIN_ROUND = 1;
/*     */   public static final int TYPE_CENTERED = 0;
/*     */   public static final int TYPE_INNER = 1;
/*     */   public static final int TYPE_OUTER = 2;
/*     */   float width;
/*     */   int type;
/*     */   int cap;
/*     */   int join;
/*     */   float miterLimit;
/*     */   float[] dash;
/*     */   float dashPhase;
/* 573 */   private float[] tmpMiter = new float[2];
/*     */ 
/* 619 */   static final float SQRT_2 = (float)Math.sqrt(2.0D);
/*     */ 
/*     */   public BasicStroke()
/*     */   {
/*  41 */     set(0, 1.0F, 2, 0, 10.0F);
/*     */   }
/*     */ 
/*     */   public BasicStroke(float paramFloat1, int paramInt1, int paramInt2, float paramFloat2) {
/*  45 */     set(0, paramFloat1, paramInt1, paramInt2, paramFloat2);
/*     */   }
/*     */ 
/*     */   public BasicStroke(int paramInt1, float paramFloat1, int paramInt2, int paramInt3, float paramFloat2)
/*     */   {
/*  51 */     set(paramInt1, paramFloat1, paramInt2, paramInt3, paramFloat2);
/*     */   }
/*     */ 
/*     */   public BasicStroke(float paramFloat1, int paramInt1, int paramInt2, float paramFloat2, float[] paramArrayOfFloat, float paramFloat3)
/*     */   {
/*  57 */     set(0, paramFloat1, paramInt1, paramInt2, paramFloat2);
/*  58 */     set(paramArrayOfFloat, paramFloat3);
/*     */   }
/*     */ 
/*     */   public BasicStroke(int paramInt1, float paramFloat1, int paramInt2, int paramInt3, float paramFloat2, float[] paramArrayOfFloat, float paramFloat3)
/*     */   {
/*  64 */     set(paramInt1, paramFloat1, paramInt2, paramInt3, paramFloat2);
/*  65 */     set(paramArrayOfFloat, paramFloat3);
/*     */   }
/*     */ 
/*     */   public void set(int paramInt1, float paramFloat1, int paramInt2, int paramInt3, float paramFloat2)
/*     */   {
/*  71 */     if ((paramInt1 != 0) && (paramInt1 != 1) && (paramInt1 != 2)) {
/*  72 */       throw new IllegalArgumentException("illegal type");
/*     */     }
/*  74 */     if (paramFloat1 < 0.0F) {
/*  75 */       throw new IllegalArgumentException("negative width");
/*     */     }
/*  77 */     if ((paramInt2 != 0) && (paramInt2 != 1) && (paramInt2 != 2)) {
/*  78 */       throw new IllegalArgumentException("illegal end cap value");
/*     */     }
/*  80 */     if (paramInt3 == 0) {
/*  81 */       if (paramFloat2 < 1.0F)
/*  82 */         throw new IllegalArgumentException("miter limit < 1");
/*     */     }
/*  84 */     else if ((paramInt3 != 1) && (paramInt3 != 2)) {
/*  85 */       throw new IllegalArgumentException("illegal line join value");
/*     */     }
/*  87 */     this.type = paramInt1;
/*  88 */     this.width = paramFloat1;
/*  89 */     this.cap = paramInt2;
/*  90 */     this.join = paramInt3;
/*  91 */     this.miterLimit = paramFloat2;
/*     */   }
/*     */ 
/*     */   public void set(float[] paramArrayOfFloat, float paramFloat) {
/*  95 */     if (paramArrayOfFloat != null) {
/*  96 */       if (paramFloat < 0.0F) {
/*  97 */         throw new IllegalArgumentException("negative dash phase");
/*     */       }
/*  99 */       int i = 1;
/* 100 */       for (int j = 0; j < paramArrayOfFloat.length; j++) {
/* 101 */         float f = paramArrayOfFloat[j];
/* 102 */         if (f > 0.0D)
/* 103 */           i = 0;
/* 104 */         else if (f < 0.0D) {
/* 105 */           throw new IllegalArgumentException("negative dash length");
/*     */         }
/*     */       }
/* 108 */       if (i != 0) {
/* 109 */         throw new IllegalArgumentException("dash lengths all zero");
/*     */       }
/*     */     }
/* 112 */     this.dash = paramArrayOfFloat;
/* 113 */     this.dashPhase = paramFloat;
/*     */   }
/*     */ 
/*     */   public int getType()
/*     */   {
/* 122 */     return this.type;
/*     */   }
/*     */ 
/*     */   public float getLineWidth()
/*     */   {
/* 133 */     return this.width;
/*     */   }
/*     */ 
/*     */   public int getEndCap()
/*     */   {
/* 143 */     return this.cap;
/*     */   }
/*     */ 
/*     */   public int getLineJoin()
/*     */   {
/* 153 */     return this.join;
/*     */   }
/*     */ 
/*     */   public float getMiterLimit()
/*     */   {
/* 161 */     return this.miterLimit;
/*     */   }
/*     */ 
/*     */   public boolean isDashed()
/*     */   {
/* 170 */     return this.dash != null;
/*     */   }
/*     */ 
/*     */   public float[] getDashArray()
/*     */   {
/* 185 */     return this.dash;
/*     */   }
/*     */ 
/*     */   public float getDashPhase()
/*     */   {
/* 197 */     return this.dashPhase;
/*     */   }
/*     */ 
/*     */   public Shape createStrokedShape(Shape paramShape)
/*     */   {
/* 202 */     if ((paramShape instanceof RoundRectangle2D))
/* 203 */       localShape = strokeRoundRectangle((RoundRectangle2D)paramShape);
/*     */     else {
/* 205 */       localShape = null;
/*     */     }
/* 207 */     if (localShape != null) {
/* 208 */       return localShape;
/*     */     }
/*     */ 
/* 211 */     Shape localShape = createCenteredStrokedShape(paramShape);
/*     */ 
/* 213 */     if (this.type == 1)
/* 214 */       localShape = makeIntersectedShape(localShape, paramShape);
/* 215 */     else if (this.type == 2) {
/* 216 */       localShape = makeSubtractedShape(localShape, paramShape);
/*     */     }
/* 218 */     return localShape;
/*     */   }
/*     */ 
/*     */   private boolean isCW(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/* 224 */     return paramFloat1 * paramFloat4 <= paramFloat2 * paramFloat3;
/*     */   }
/*     */ 
/*     */   private void computeOffset(float paramFloat1, float paramFloat2, float paramFloat3, float[] paramArrayOfFloat, int paramInt)
/*     */   {
/* 229 */     float f = (float)Math.sqrt(paramFloat1 * paramFloat1 + paramFloat2 * paramFloat2);
/* 230 */     if (f == 0.0F)
/*     */     {
/*     */       float tmp34_33 = 0.0F; paramArrayOfFloat[(paramInt + 1)] = tmp34_33; paramArrayOfFloat[(paramInt + 0)] = tmp34_33;
/*     */     } else {
/* 233 */       paramArrayOfFloat[(paramInt + 0)] = (paramFloat2 * paramFloat3 / f);
/* 234 */       paramArrayOfFloat[(paramInt + 1)] = (-(paramFloat1 * paramFloat3) / f);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void computeMiter(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float[] paramArrayOfFloat, int paramInt)
/*     */   {
/* 244 */     float f1 = paramFloat3 - paramFloat1;
/* 245 */     float f2 = paramFloat4 - paramFloat2;
/* 246 */     float f3 = paramFloat7 - paramFloat5;
/* 247 */     float f4 = paramFloat8 - paramFloat6;
/*     */ 
/* 258 */     float f5 = f1 * f4 - f3 * f2;
/* 259 */     float f6 = f3 * (paramFloat2 - paramFloat6) - f4 * (paramFloat1 - paramFloat5);
/* 260 */     f6 /= f5;
/* 261 */     paramArrayOfFloat[(paramInt++)] = (paramFloat1 + f6 * f1);
/* 262 */     paramArrayOfFloat[paramInt] = (paramFloat2 + f6 * f2);
/*     */   }
/*     */ 
/*     */   private void accumulateQuad(float[] paramArrayOfFloat, int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/* 282 */     float f1 = paramFloat1 - paramFloat2;
/* 283 */     float f2 = paramFloat3 - paramFloat2 + f1;
/* 284 */     if (f2 != 0.0F) {
/* 285 */       float f3 = f1 / f2;
/* 286 */       if ((f3 > 0.0F) && (f3 < 1.0F)) {
/* 287 */         float f4 = 1.0F - f3;
/* 288 */         float f5 = paramFloat1 * f4 * f4 + 2.0F * paramFloat2 * f3 * f4 + paramFloat3 * f3 * f3;
/* 289 */         if (paramArrayOfFloat[paramInt] > f5 - paramFloat4) paramArrayOfFloat[paramInt] = (f5 - paramFloat4);
/* 290 */         if (paramArrayOfFloat[(paramInt + 2)] < f5 + paramFloat4) paramArrayOfFloat[(paramInt + 2)] = (f5 + paramFloat4);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void accumulateCubic(float[] paramArrayOfFloat, int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
/*     */   {
/* 299 */     if ((paramFloat1 > 0.0F) && (paramFloat1 < 1.0F)) {
/* 300 */       float f1 = 1.0F - paramFloat1;
/* 301 */       float f2 = paramFloat2 * f1 * f1 * f1 + 3.0F * paramFloat3 * paramFloat1 * f1 * f1 + 3.0F * paramFloat4 * paramFloat1 * paramFloat1 * f1 + paramFloat5 * paramFloat1 * paramFloat1 * paramFloat1;
/*     */ 
/* 305 */       if (paramArrayOfFloat[paramInt] > f2 - paramFloat6) paramArrayOfFloat[paramInt] = (f2 - paramFloat6);
/* 306 */       if (paramArrayOfFloat[(paramInt + 2)] < f2 + paramFloat6) paramArrayOfFloat[(paramInt + 2)] = (f2 + paramFloat6);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void accumulateCubic(float[] paramArrayOfFloat, int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5)
/*     */   {
/* 327 */     float f1 = paramFloat2 - paramFloat1;
/* 328 */     float f2 = 2.0F * (paramFloat3 - paramFloat2 - f1);
/* 329 */     float f3 = paramFloat4 - paramFloat3 - f2 - f1;
/* 330 */     if (f3 == 0.0F)
/*     */     {
/* 332 */       if (f2 == 0.0F)
/*     */       {
/* 334 */         return;
/*     */       }
/* 336 */       accumulateCubic(paramArrayOfFloat, paramInt, -f1 / f2, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5);
/*     */     }
/*     */     else {
/* 339 */       float f4 = f2 * f2 - 4.0F * f3 * f1;
/* 340 */       if (f4 < 0.0F)
/*     */       {
/* 342 */         return;
/*     */       }
/* 344 */       f4 = (float)Math.sqrt(f4);
/*     */ 
/* 350 */       if (f2 < 0.0F) {
/* 351 */         f4 = -f4;
/*     */       }
/* 353 */       float f5 = (f2 + f4) / -2.0F;
/*     */ 
/* 355 */       accumulateCubic(paramArrayOfFloat, paramInt, f5 / f3, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5);
/* 356 */       if (f5 != 0.0F)
/* 357 */         accumulateCubic(paramArrayOfFloat, paramInt, f1 / f5, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void accumulateShapeBounds(float[] paramArrayOfFloat, Shape paramShape, BaseTransform paramBaseTransform)
/*     */   {
/* 364 */     PathIterator localPathIterator = paramShape.getPathIterator(paramBaseTransform);
/* 365 */     int i = 1;
/* 366 */     float[] arrayOfFloat1 = new float[6];
/* 367 */     if (this.type == 1) {
/* 368 */       Shape.accumulate(paramArrayOfFloat, paramShape, paramBaseTransform);
/* 369 */       return;
/*     */     }
/* 371 */     float f1 = this.type == 0 ? getLineWidth() / 2.0F : getLineWidth();
/*     */ 
/* 373 */     float f2 = 0.0F; float f3 = 0.0F; float f4 = 0.0F; float f5 = 0.0F;
/*     */ 
/* 375 */     float f8 = 0.0F; float f9 = 0.0F; float f12 = 0.0F; float f13 = 0.0F;
/*     */ 
/* 377 */     float[] arrayOfFloat2 = new float[4];
/*     */ 
/* 379 */     float f14 = 0.0F; float f15 = 0.0F; float f16 = 0.0F; float f17 = 0.0F;
/*     */ 
/* 381 */     while (!localPathIterator.isDone()) {
/* 382 */       int j = localPathIterator.currentSegment(arrayOfFloat1);
/*     */       float f6;
/*     */       float f7;
/*     */       float f10;
/*     */       float f11;
/* 383 */       switch (j) {
/*     */       case 0:
/* 385 */         if (i == 0) {
/* 386 */           accumulateCap(f12, f13, f4, f5, f14, f15, paramArrayOfFloat, f1);
/* 387 */           accumulateCap(-f8, -f9, f2, f3, -f16, -f17, paramArrayOfFloat, f1);
/*     */         }
/*     */ 
/* 390 */         f4 = f2 = arrayOfFloat1[0];
/* 391 */         f5 = f3 = arrayOfFloat1[1];
/* 392 */         break;
/*     */       case 1:
/* 394 */         f6 = arrayOfFloat1[0];
/* 395 */         f7 = arrayOfFloat1[1];
/* 396 */         f10 = f6 - f4;
/* 397 */         f11 = f7 - f5;
/* 398 */         if ((f10 == 0.0F) && (f11 == 0.0F))
/*     */         {
/* 402 */           f10 = 1.0F;
/*     */         }
/*     */ 
/* 405 */         computeOffset(f10, f11, f1, arrayOfFloat2, 0);
/*     */ 
/* 407 */         if (i == 0) {
/* 408 */           accumulateJoin(f12, f13, f10, f11, f4, f5, f14, f15, arrayOfFloat2[0], arrayOfFloat2[1], paramArrayOfFloat, f1);
/*     */         }
/*     */ 
/* 411 */         f4 = f6;
/* 412 */         f5 = f7;
/* 413 */         f12 = f10;
/* 414 */         f13 = f11;
/* 415 */         f14 = arrayOfFloat2[0];
/* 416 */         f15 = arrayOfFloat2[1];
/* 417 */         if (i != 0) {
/* 418 */           f8 = f12;
/* 419 */           f9 = f13;
/* 420 */           f16 = f14;
/* 421 */           f17 = f15; } break;
/*     */       case 2:
/* 425 */         f6 = arrayOfFloat1[2];
/* 426 */         f7 = arrayOfFloat1[3];
/* 427 */         f10 = arrayOfFloat1[0] - f4;
/* 428 */         f11 = arrayOfFloat1[1] - f5;
/*     */ 
/* 430 */         computeOffset(f10, f11, f1, arrayOfFloat2, 0);
/* 431 */         if (i == 0) {
/* 432 */           accumulateJoin(f12, f13, f10, f11, f4, f5, f14, f15, arrayOfFloat2[0], arrayOfFloat2[1], paramArrayOfFloat, f1);
/*     */         }
/*     */ 
/* 435 */         if ((paramArrayOfFloat[0] > arrayOfFloat1[0] - f1) || (paramArrayOfFloat[2] < arrayOfFloat1[0] + f1)) {
/* 436 */           accumulateQuad(paramArrayOfFloat, 0, f4, arrayOfFloat1[0], f6, f1);
/*     */         }
/* 438 */         if ((paramArrayOfFloat[1] > arrayOfFloat1[1] - f1) || (paramArrayOfFloat[3] < arrayOfFloat1[1] + f1)) {
/* 439 */           accumulateQuad(paramArrayOfFloat, 1, f5, arrayOfFloat1[1], f7, f1);
/*     */         }
/* 441 */         f4 = f6;
/* 442 */         f5 = f7;
/* 443 */         if (i != 0) {
/* 444 */           f8 = f10;
/* 445 */           f9 = f11;
/* 446 */           f16 = arrayOfFloat2[0];
/* 447 */           f17 = arrayOfFloat2[1];
/*     */         }
/* 449 */         f12 = f6 - arrayOfFloat1[0];
/* 450 */         f13 = f7 - arrayOfFloat1[1];
/* 451 */         computeOffset(f12, f13, f1, arrayOfFloat2, 0);
/* 452 */         f14 = arrayOfFloat2[0];
/* 453 */         f15 = arrayOfFloat2[1];
/* 454 */         break;
/*     */       case 3:
/* 456 */         f6 = arrayOfFloat1[4];
/* 457 */         f7 = arrayOfFloat1[5];
/* 458 */         f10 = arrayOfFloat1[0] - f4;
/* 459 */         f11 = arrayOfFloat1[1] - f5;
/*     */ 
/* 461 */         computeOffset(f10, f11, f1, arrayOfFloat2, 0);
/* 462 */         if (i == 0) {
/* 463 */           accumulateJoin(f12, f13, f10, f11, f4, f5, f14, f15, arrayOfFloat2[0], arrayOfFloat2[1], paramArrayOfFloat, f1);
/*     */         }
/*     */ 
/* 466 */         if ((paramArrayOfFloat[0] > arrayOfFloat1[0] - f1) || (paramArrayOfFloat[2] < arrayOfFloat1[0] + f1) || (paramArrayOfFloat[0] > arrayOfFloat1[2] - f1) || (paramArrayOfFloat[2] < arrayOfFloat1[2] + f1))
/*     */         {
/* 469 */           accumulateCubic(paramArrayOfFloat, 0, f4, arrayOfFloat1[0], arrayOfFloat1[2], f6, f1);
/*     */         }
/* 471 */         if ((paramArrayOfFloat[1] > arrayOfFloat1[1] - f1) || (paramArrayOfFloat[3] < arrayOfFloat1[1] + f1) || (paramArrayOfFloat[1] > arrayOfFloat1[3] - f1) || (paramArrayOfFloat[3] < arrayOfFloat1[3] + f1))
/*     */         {
/* 474 */           accumulateCubic(paramArrayOfFloat, 1, f5, arrayOfFloat1[1], arrayOfFloat1[3], f7, f1);
/*     */         }
/* 476 */         f4 = f6;
/* 477 */         f5 = f7;
/* 478 */         if (i != 0) {
/* 479 */           f8 = f10;
/* 480 */           f9 = f11;
/* 481 */           f16 = arrayOfFloat2[0];
/* 482 */           f17 = arrayOfFloat2[1];
/*     */         }
/* 484 */         f12 = f6 - arrayOfFloat1[2];
/* 485 */         f13 = f7 - arrayOfFloat1[3];
/* 486 */         computeOffset(f12, f13, f1, arrayOfFloat2, 0);
/* 487 */         f14 = arrayOfFloat2[0];
/* 488 */         f15 = arrayOfFloat2[1];
/* 489 */         break;
/*     */       case 4:
/* 491 */         f10 = f2 - f4;
/* 492 */         f11 = f3 - f5;
/* 493 */         f6 = f2;
/* 494 */         f7 = f3;
/*     */ 
/* 496 */         if (i == 0) {
/* 497 */           computeOffset(f8, f9, f1, arrayOfFloat2, 2);
/* 498 */           if ((f10 == 0.0F) && (f11 == 0.0F)) {
/* 499 */             accumulateJoin(f12, f13, f8, f9, f2, f3, f14, f15, arrayOfFloat2[2], arrayOfFloat2[3], paramArrayOfFloat, f1);
/*     */           } else {
/* 501 */             computeOffset(f10, f11, f1, arrayOfFloat2, 0);
/* 502 */             accumulateJoin(f12, f13, f10, f11, f4, f5, f14, f15, arrayOfFloat2[0], arrayOfFloat2[1], paramArrayOfFloat, f1);
/* 503 */             accumulateJoin(f10, f11, f8, f9, f6, f7, arrayOfFloat2[0], arrayOfFloat2[1], arrayOfFloat2[2], arrayOfFloat2[3], paramArrayOfFloat, f1);
/*     */           }
/*     */         }
/* 506 */         f4 = f6;
/* 507 */         f5 = f7;
/*     */       }
/*     */ 
/* 512 */       i = (j == 0) || (j == 4) ? 1 : 0;
/* 513 */       localPathIterator.next();
/*     */     }
/*     */ 
/* 516 */     if (i == 0) {
/* 517 */       accumulateCap(f12, f13, f4, f5, f14, f15, paramArrayOfFloat, f1);
/* 518 */       accumulateCap(-f8, -f9, f2, f3, -f16, -f17, paramArrayOfFloat, f1);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void accumulate(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float[] paramArrayOfFloat) {
/* 523 */     if (paramFloat1 <= paramFloat3) {
/* 524 */       if (paramFloat1 < paramArrayOfFloat[0]) paramArrayOfFloat[0] = paramFloat1;
/* 525 */       if (paramFloat3 > paramArrayOfFloat[2]) paramArrayOfFloat[2] = paramFloat3; 
/*     */     }
/* 527 */     else { if (paramFloat3 < paramArrayOfFloat[0]) paramArrayOfFloat[0] = paramFloat3;
/* 528 */       if (paramFloat1 > paramArrayOfFloat[2]) paramArrayOfFloat[2] = paramFloat1;
/*     */     }
/* 530 */     if (paramFloat2 <= paramFloat4) {
/* 531 */       if (paramFloat2 < paramArrayOfFloat[1]) paramArrayOfFloat[1] = paramFloat2;
/* 532 */       if (paramFloat4 > paramArrayOfFloat[3]) paramArrayOfFloat[3] = paramFloat4; 
/*     */     }
/* 534 */     else { if (paramFloat4 < paramArrayOfFloat[1]) paramArrayOfFloat[1] = paramFloat4;
/* 535 */       if (paramFloat2 > paramArrayOfFloat[3]) paramArrayOfFloat[3] = paramFloat2; 
/*     */     }
/*     */   }
/*     */ 
/*     */   private void accumulateOrdered(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float[] paramArrayOfFloat)
/*     */   {
/* 540 */     if (paramFloat1 < paramArrayOfFloat[0]) paramArrayOfFloat[0] = paramFloat1;
/* 541 */     if (paramFloat3 > paramArrayOfFloat[2]) paramArrayOfFloat[2] = paramFloat3;
/* 542 */     if (paramFloat2 < paramArrayOfFloat[1]) paramArrayOfFloat[1] = paramFloat2;
/* 543 */     if (paramFloat4 > paramArrayOfFloat[3]) paramArrayOfFloat[3] = paramFloat4;
/*     */   }
/*     */ 
/*     */   private void accumulateJoin(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float[] paramArrayOfFloat, float paramFloat11)
/*     */   {
/* 550 */     if (this.join == 2)
/* 551 */       accumulateBevel(paramFloat5, paramFloat6, paramFloat7, paramFloat8, paramFloat9, paramFloat10, paramArrayOfFloat);
/* 552 */     else if (this.join == 0)
/* 553 */       accumulateMiter(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat7, paramFloat8, paramFloat9, paramFloat10, paramFloat5, paramFloat6, paramArrayOfFloat, paramFloat11);
/*     */     else
/* 555 */       accumulateOrdered(paramFloat5 - paramFloat11, paramFloat6 - paramFloat11, paramFloat5 + paramFloat11, paramFloat6 + paramFloat11, paramArrayOfFloat);
/*     */   }
/*     */ 
/*     */   private void accumulateCap(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float[] paramArrayOfFloat, float paramFloat7)
/*     */   {
/* 563 */     if (this.cap == 2)
/* 564 */       accumulate(paramFloat3 + paramFloat5 - paramFloat6, paramFloat4 + paramFloat6 + paramFloat5, paramFloat3 - paramFloat5 - paramFloat6, paramFloat4 - paramFloat6 + paramFloat5, paramArrayOfFloat);
/* 565 */     else if (this.cap == 0)
/* 566 */       accumulate(paramFloat3 + paramFloat5, paramFloat4 + paramFloat6, paramFloat3 - paramFloat5, paramFloat4 - paramFloat6, paramArrayOfFloat);
/*     */     else
/* 568 */       accumulateOrdered(paramFloat3 - paramFloat7, paramFloat4 - paramFloat7, paramFloat3 + paramFloat7, paramFloat4 + paramFloat7, paramArrayOfFloat);
/*     */   }
/*     */ 
/*     */   private void accumulateMiter(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float[] paramArrayOfFloat, float paramFloat11)
/*     */   {
/* 579 */     accumulateBevel(paramFloat9, paramFloat10, paramFloat5, paramFloat6, paramFloat7, paramFloat8, paramArrayOfFloat);
/*     */ 
/* 581 */     boolean bool = isCW(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/*     */ 
/* 583 */     if (bool) {
/* 584 */       paramFloat5 = -paramFloat5;
/* 585 */       paramFloat6 = -paramFloat6;
/* 586 */       paramFloat7 = -paramFloat7;
/* 587 */       paramFloat8 = -paramFloat8;
/*     */     }
/*     */ 
/* 590 */     computeMiter(paramFloat9 - paramFloat1 + paramFloat5, paramFloat10 - paramFloat2 + paramFloat6, paramFloat9 + paramFloat5, paramFloat10 + paramFloat6, paramFloat9 + paramFloat3 + paramFloat7, paramFloat10 + paramFloat4 + paramFloat8, paramFloat9 + paramFloat7, paramFloat10 + paramFloat8, this.tmpMiter, 0);
/*     */ 
/* 593 */     float f1 = (this.tmpMiter[0] - paramFloat9) * (this.tmpMiter[0] - paramFloat9) + (this.tmpMiter[1] - paramFloat10) * (this.tmpMiter[1] - paramFloat10);
/*     */ 
/* 595 */     float f2 = this.miterLimit * paramFloat11;
/* 596 */     if (f1 < f2 * f2)
/* 597 */       accumulateOrdered(this.tmpMiter[0], this.tmpMiter[1], this.tmpMiter[0], this.tmpMiter[1], paramArrayOfFloat);
/*     */   }
/*     */ 
/*     */   private void accumulateBevel(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float[] paramArrayOfFloat)
/*     */   {
/* 603 */     accumulate(paramFloat1 + paramFloat3, paramFloat2 + paramFloat4, paramFloat1 - paramFloat3, paramFloat2 - paramFloat4, paramArrayOfFloat);
/* 604 */     accumulate(paramFloat1 + paramFloat5, paramFloat2 + paramFloat6, paramFloat1 - paramFloat5, paramFloat2 - paramFloat6, paramArrayOfFloat);
/*     */   }
/*     */ 
/*     */   public Shape createCenteredStrokedShape(Shape paramShape) {
/* 608 */     Path2D localPath2D = new Path2D(1);
/* 609 */     float f = this.type == 0 ? this.width : this.width * 2.0F;
/* 610 */     Object localObject = new Stroker(localPath2D, f, this.cap, this.join, this.miterLimit);
/*     */ 
/* 612 */     if (this.dash != null) {
/* 613 */       localObject = new Dasher((PathConsumer2D)localObject, this.dash, this.dashPhase);
/*     */     }
/* 615 */     OpenPiscesPrismUtils.feedConsumer(paramShape.getPathIterator(null), (PathConsumer2D)localObject);
/* 616 */     return localPath2D;
/*     */   }
/*     */ 
/*     */   Shape strokeRoundRectangle(RoundRectangle2D paramRoundRectangle2D)
/*     */   {
/* 622 */     if ((paramRoundRectangle2D.width < 0.0F) || (paramRoundRectangle2D.height < 0.0F)) {
/* 623 */       return new Path2D();
/*     */     }
/* 625 */     if (isDashed()) {
/* 626 */       return null;
/*     */     }
/*     */ 
/* 629 */     float f1 = paramRoundRectangle2D.arcWidth;
/* 630 */     float f2 = paramRoundRectangle2D.arcHeight;
/*     */     int i;
/* 631 */     if ((f1 <= 0.0F) || (f2 <= 0.0F)) {
/* 632 */       f1 = f2 = 0.0F;
/* 633 */       if (this.type == 1) {
/* 634 */         i = 0;
/*     */       } else {
/* 636 */         i = this.join;
/* 637 */         if ((i == 0) && (this.miterLimit < SQRT_2))
/* 638 */           i = 2;
/*     */       }
/*     */     }
/*     */     else {
/* 642 */       if ((f1 < f2 * 0.9F) || (f2 < f1 * 0.9F))
/*     */       {
/* 645 */         return null;
/*     */       }
/* 647 */       i = 1;
/*     */     }
/*     */     float f4;
/*     */     float f3;
/* 650 */     if (this.type == 1) {
/* 651 */       f4 = 0.0F;
/* 652 */       f3 = this.width;
/* 653 */     } else if (this.type == 2) {
/* 654 */       f4 = this.width;
/* 655 */       f3 = 0.0F;
/*     */     } else {
/* 657 */       f4 = f3 = this.width / 2.0F;
/*     */     }
/*     */     Object localObject;
/* 660 */     switch (i) {
/*     */     case 0:
/* 662 */       localObject = new RoundRectangle2D(paramRoundRectangle2D.x - f4, paramRoundRectangle2D.y - f4, paramRoundRectangle2D.width + f4 * 2.0F, paramRoundRectangle2D.height + f4 * 2.0F, 0.0F, 0.0F);
/*     */ 
/* 665 */       break;
/*     */     case 2:
/* 667 */       localObject = makeBeveledRect(paramRoundRectangle2D.x, paramRoundRectangle2D.y, paramRoundRectangle2D.width, paramRoundRectangle2D.height, f4);
/* 668 */       break;
/*     */     case 1:
/* 670 */       localObject = new RoundRectangle2D(paramRoundRectangle2D.x - f4, paramRoundRectangle2D.y - f4, paramRoundRectangle2D.width + f4 * 2.0F, paramRoundRectangle2D.height + f4 * 2.0F, f1 + f4 * 2.0F, f2 + f4 * 2.0F);
/*     */ 
/* 673 */       break;
/*     */     default:
/* 675 */       throw new InternalError("Unrecognized line join style");
/*     */     }
/* 677 */     if ((paramRoundRectangle2D.width <= f3 * 2.0F) || (paramRoundRectangle2D.height <= f3 * 2.0F)) {
/* 678 */       return localObject;
/*     */     }
/* 680 */     f1 -= f3 * 2.0F;
/* 681 */     f2 -= f3 * 2.0F;
/* 682 */     if ((f1 <= 0.0F) || (f2 <= 0.0F)) {
/* 683 */       f1 = f2 = 0.0F;
/*     */     }
/* 685 */     RoundRectangle2D localRoundRectangle2D = new RoundRectangle2D(paramRoundRectangle2D.x + f3, paramRoundRectangle2D.y + f3, paramRoundRectangle2D.width - f3 * 2.0F, paramRoundRectangle2D.height - f3 * 2.0F, f1, f2);
/*     */ 
/* 688 */     Path2D localPath2D = (localObject instanceof Path2D) ? (Path2D)localObject : new Path2D((Shape)localObject);
/*     */ 
/* 690 */     localPath2D.setWindingRule(0);
/* 691 */     localPath2D.append(localRoundRectangle2D, false);
/* 692 */     return localPath2D;
/*     */   }
/*     */ 
/*     */   static Shape makeBeveledRect(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5)
/*     */   {
/* 699 */     float f1 = paramFloat1;
/* 700 */     float f2 = paramFloat2;
/* 701 */     float f3 = paramFloat1 + paramFloat3;
/* 702 */     float f4 = paramFloat2 + paramFloat4;
/* 703 */     Path2D localPath2D = new Path2D();
/* 704 */     localPath2D.moveTo(f1, f2 - paramFloat5);
/* 705 */     localPath2D.lineTo(f3, f2 - paramFloat5);
/* 706 */     localPath2D.lineTo(f3 + paramFloat5, f2);
/* 707 */     localPath2D.lineTo(f3 + paramFloat5, f4);
/* 708 */     localPath2D.lineTo(f3, f4 + paramFloat5);
/* 709 */     localPath2D.lineTo(f1, f4 + paramFloat5);
/* 710 */     localPath2D.lineTo(f1 - paramFloat5, f4);
/* 711 */     localPath2D.lineTo(f1 - paramFloat5, f2);
/* 712 */     localPath2D.closePath();
/* 713 */     return localPath2D;
/*     */   }
/*     */ 
/*     */   protected Shape makeIntersectedShape(Shape paramShape1, Shape paramShape2) {
/* 717 */     return new CAGShapePair(paramShape1, paramShape2, 4);
/*     */   }
/*     */ 
/*     */   protected Shape makeSubtractedShape(Shape paramShape1, Shape paramShape2) {
/* 721 */     return new CAGShapePair(paramShape1, paramShape2, 1);
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 753 */     int i = Float.floatToIntBits(this.width);
/* 754 */     i = i * 31 + this.join;
/* 755 */     i = i * 31 + this.cap;
/* 756 */     i = i * 31 + Float.floatToIntBits(this.miterLimit);
/* 757 */     if (this.dash != null) {
/* 758 */       i = i * 31 + Float.floatToIntBits(this.dashPhase);
/* 759 */       for (int j = 0; j < this.dash.length; j++) {
/* 760 */         i = i * 31 + Float.floatToIntBits(this.dash[j]);
/*     */       }
/*     */     }
/* 763 */     return i;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 779 */     if (!(paramObject instanceof BasicStroke)) {
/* 780 */       return false;
/*     */     }
/* 782 */     BasicStroke localBasicStroke = (BasicStroke)paramObject;
/* 783 */     if (this.width != localBasicStroke.width) {
/* 784 */       return false;
/*     */     }
/* 786 */     if (this.join != localBasicStroke.join) {
/* 787 */       return false;
/*     */     }
/* 789 */     if (this.cap != localBasicStroke.cap) {
/* 790 */       return false;
/*     */     }
/* 792 */     if (this.miterLimit != localBasicStroke.miterLimit) {
/* 793 */       return false;
/*     */     }
/* 795 */     if (this.dash != null) {
/* 796 */       if (this.dashPhase != localBasicStroke.dashPhase) {
/* 797 */         return false;
/*     */       }
/* 799 */       if (!Arrays.equals(this.dash, localBasicStroke.dash)) {
/* 800 */         return false;
/*     */       }
/*     */     }
/* 803 */     else if (localBasicStroke.dash != null) {
/* 804 */       return false;
/*     */     }
/*     */ 
/* 807 */     return true;
/*     */   }
/*     */ 
/*     */   static class CAGShapePair extends GeneralShapePair
/*     */   {
/*     */     private Shape cagshape;
/*     */ 
/*     */     public CAGShapePair(Shape paramShape1, Shape paramShape2, int paramInt)
/*     */     {
/* 728 */       super(paramShape2, paramInt);
/*     */     }
/*     */ 
/*     */     public PathIterator getPathIterator(BaseTransform paramBaseTransform)
/*     */     {
/* 733 */       if (this.cagshape == null) {
/* 734 */         Area localArea1 = new Area(getOuterShape());
/* 735 */         Area localArea2 = new Area(getInnerShape());
/* 736 */         if (getCombinationType() == 4)
/* 737 */           localArea1.intersect(localArea2);
/*     */         else {
/* 739 */           localArea1.subtract(localArea2);
/*     */         }
/* 741 */         this.cagshape = localArea1;
/*     */       }
/* 743 */       return this.cagshape.getPathIterator(paramBaseTransform);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.BasicStroke
 * JD-Core Version:    0.6.2
 */