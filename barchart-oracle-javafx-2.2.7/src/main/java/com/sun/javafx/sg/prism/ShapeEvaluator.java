/*     */ package com.sun.javafx.sg.prism;
/*     */ 
/*     */ import com.sun.javafx.geom.FlatteningPathIterator;
/*     */ import com.sun.javafx.geom.IllegalPathStateException;
/*     */ import com.sun.javafx.geom.Path2D;
/*     */ import com.sun.javafx.geom.PathIterator;
/*     */ import com.sun.javafx.geom.Point2D;
/*     */ import com.sun.javafx.geom.RectBounds;
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.Shape;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import java.util.Vector;
/*     */ 
/*     */ class ShapeEvaluator
/*     */ {
/*     */   private Shape savedv0;
/*     */   private Shape savedv1;
/*     */   private Geometry geom0;
/*     */   private Geometry geom1;
/*     */ 
/*     */   public Shape evaluate(Shape paramShape1, Shape paramShape2, float paramFloat)
/*     */   {
/*  85 */     if ((this.savedv0 != paramShape1) || (this.savedv1 != paramShape2)) {
/*  86 */       if ((this.savedv0 == paramShape2) && (this.savedv1 == paramShape1))
/*     */       {
/*  88 */         Geometry localGeometry = this.geom0;
/*  89 */         this.geom0 = this.geom1;
/*  90 */         this.geom1 = localGeometry;
/*     */       } else {
/*  92 */         recalculate(paramShape1, paramShape2);
/*     */       }
/*  94 */       this.savedv0 = paramShape1;
/*  95 */       this.savedv1 = paramShape2;
/*     */     }
/*  97 */     return getShape(paramFloat);
/*     */   }
/*     */ 
/*     */   private void recalculate(Shape paramShape1, Shape paramShape2) {
/* 101 */     this.geom0 = new Geometry(paramShape1);
/* 102 */     this.geom1 = new Geometry(paramShape2);
/* 103 */     float[] arrayOfFloat1 = this.geom0.getTvals();
/* 104 */     float[] arrayOfFloat2 = this.geom1.getTvals();
/* 105 */     float[] arrayOfFloat3 = mergeTvals(arrayOfFloat1, arrayOfFloat2);
/* 106 */     this.geom0.setTvals(arrayOfFloat3);
/* 107 */     this.geom1.setTvals(arrayOfFloat3);
/*     */   }
/*     */ 
/*     */   private Shape getShape(float paramFloat) {
/* 111 */     return new MorphedShape(this.geom0, this.geom1, paramFloat);
/*     */   }
/*     */ 
/*     */   private static float[] mergeTvals(float[] paramArrayOfFloat1, float[] paramArrayOfFloat2) {
/* 115 */     int i = sortTvals(paramArrayOfFloat1, paramArrayOfFloat2, null);
/* 116 */     float[] arrayOfFloat = new float[i];
/* 117 */     sortTvals(paramArrayOfFloat1, paramArrayOfFloat2, arrayOfFloat);
/* 118 */     return arrayOfFloat;
/*     */   }
/*     */ 
/*     */   private static int sortTvals(float[] paramArrayOfFloat1, float[] paramArrayOfFloat2, float[] paramArrayOfFloat3)
/*     */   {
/* 125 */     int i = 0;
/* 126 */     int j = 0;
/* 127 */     int k = 0;
/* 128 */     while ((i < paramArrayOfFloat1.length) && (j < paramArrayOfFloat2.length)) {
/* 129 */       float f1 = paramArrayOfFloat1[i];
/* 130 */       float f2 = paramArrayOfFloat2[j];
/* 131 */       if (f1 <= f2) {
/* 132 */         if (paramArrayOfFloat3 != null) paramArrayOfFloat3[k] = f1;
/* 133 */         i++;
/*     */       }
/* 135 */       if (f2 <= f1) {
/* 136 */         if (paramArrayOfFloat3 != null) paramArrayOfFloat3[k] = f2;
/* 137 */         j++;
/*     */       }
/* 139 */       k++;
/*     */     }
/* 141 */     return k;
/*     */   }
/*     */ 
/*     */   private static float interp(float paramFloat1, float paramFloat2, float paramFloat3) {
/* 145 */     return paramFloat1 + (paramFloat2 - paramFloat1) * paramFloat3;
/*     */   }
/*     */ 
/*     */   private static class Iterator
/*     */     implements PathIterator
/*     */   {
/*     */     BaseTransform at;
/*     */     ShapeEvaluator.Geometry g0;
/*     */     ShapeEvaluator.Geometry g1;
/*     */     float t;
/*     */     int cindex;
/*     */ 
/*     */     public Iterator(BaseTransform paramBaseTransform, ShapeEvaluator.Geometry paramGeometry1, ShapeEvaluator.Geometry paramGeometry2, float paramFloat)
/*     */     {
/* 623 */       this.at = paramBaseTransform;
/* 624 */       this.g0 = paramGeometry1;
/* 625 */       this.g1 = paramGeometry2;
/* 626 */       this.t = paramFloat;
/*     */     }
/*     */ 
/*     */     public int getWindingRule()
/*     */     {
/* 633 */       return this.t < 0.5D ? this.g0.getWindingRule() : this.g1.getWindingRule();
/*     */     }
/*     */ 
/*     */     public boolean isDone()
/*     */     {
/* 640 */       return this.cindex > this.g0.getNumCoords();
/*     */     }
/*     */ 
/*     */     public void next()
/*     */     {
/* 647 */       if (this.cindex == 0)
/* 648 */         this.cindex = 2;
/*     */       else
/* 650 */         this.cindex += 6;
/*     */     }
/*     */ 
/*     */     public int currentSegment(float[] paramArrayOfFloat)
/*     */     {
/*     */       int i;
/*     */       int j;
/* 660 */       if (this.cindex == 0) {
/* 661 */         i = 0;
/* 662 */         j = 2;
/* 663 */       } else if (this.cindex >= this.g0.getNumCoords()) {
/* 664 */         i = 4;
/* 665 */         j = 0;
/*     */       } else {
/* 667 */         i = 3;
/* 668 */         j = 6;
/*     */       }
/* 670 */       if (j > 0) {
/* 671 */         for (int k = 0; k < j; k++) {
/* 672 */           paramArrayOfFloat[k] = ShapeEvaluator.interp(this.g0.getCoord(this.cindex + k), this.g1.getCoord(this.cindex + k), this.t);
/*     */         }
/*     */ 
/* 676 */         if (this.at != null) {
/* 677 */           this.at.transform(paramArrayOfFloat, 0, paramArrayOfFloat, 0, j / 2);
/*     */         }
/*     */       }
/* 680 */       return i;
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class MorphedShape extends Shape
/*     */   {
/*     */     ShapeEvaluator.Geometry geom0;
/*     */     ShapeEvaluator.Geometry geom1;
/*     */     float t;
/*     */ 
/*     */     MorphedShape(ShapeEvaluator.Geometry paramGeometry1, ShapeEvaluator.Geometry paramGeometry2, float paramFloat)
/*     */     {
/* 555 */       this.geom0 = paramGeometry1;
/* 556 */       this.geom1 = paramGeometry2;
/* 557 */       this.t = paramFloat;
/*     */     }
/*     */ 
/*     */     public Rectangle getRectangle() {
/* 561 */       return new Rectangle(getBounds());
/*     */     }
/*     */ 
/*     */     public RectBounds getBounds() {
/* 565 */       int i = this.geom0.getNumCoords();
/*     */       float f3;
/* 567 */       float f1 = f3 = ShapeEvaluator.interp(this.geom0.getCoord(0), this.geom1.getCoord(0), this.t);
/*     */       float f4;
/* 568 */       float f2 = f4 = ShapeEvaluator.interp(this.geom0.getCoord(1), this.geom1.getCoord(1), this.t);
/* 569 */       for (int j = 2; j < i; j += 2) {
/* 570 */         float f5 = ShapeEvaluator.interp(this.geom0.getCoord(j), this.geom1.getCoord(j), this.t);
/* 571 */         float f6 = ShapeEvaluator.interp(this.geom0.getCoord(j + 1), this.geom1.getCoord(j + 1), this.t);
/* 572 */         if (f1 > f5) {
/* 573 */           f1 = f5;
/*     */         }
/* 575 */         if (f2 > f6) {
/* 576 */           f2 = f6;
/*     */         }
/* 578 */         if (f3 < f5) {
/* 579 */           f3 = f5;
/*     */         }
/* 581 */         if (f4 < f6) {
/* 582 */           f4 = f6;
/*     */         }
/*     */       }
/* 585 */       return new RectBounds(f1, f2, f3, f4);
/*     */     }
/*     */ 
/*     */     public boolean contains(float paramFloat1, float paramFloat2) {
/* 589 */       return Path2D.contains(getPathIterator(null), paramFloat1, paramFloat2);
/*     */     }
/*     */ 
/*     */     public boolean intersects(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
/* 593 */       return Path2D.intersects(getPathIterator(null), paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/*     */     }
/*     */ 
/*     */     public boolean contains(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
/* 597 */       return Path2D.contains(getPathIterator(null), paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/*     */     }
/*     */ 
/*     */     public PathIterator getPathIterator(BaseTransform paramBaseTransform) {
/* 601 */       return new ShapeEvaluator.Iterator(paramBaseTransform, this.geom0, this.geom1, this.t);
/*     */     }
/*     */ 
/*     */     public PathIterator getPathIterator(BaseTransform paramBaseTransform, float paramFloat) {
/* 605 */       return new FlatteningPathIterator(getPathIterator(paramBaseTransform), paramFloat);
/*     */     }
/*     */ 
/*     */     public Shape copy() {
/* 609 */       return new Path2D(this);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class Geometry
/*     */   {
/*     */     static final float THIRD = 0.3333333F;
/*     */     static final float MIN_LEN = 0.001F;
/*     */     float[] bezierCoords;
/*     */     int numCoords;
/*     */     int windingrule;
/*     */     float[] myTvals;
/*     */ 
/*     */     public Geometry(Shape paramShape)
/*     */     {
/* 158 */       this.bezierCoords = new float[20];
/* 159 */       PathIterator localPathIterator = paramShape.getPathIterator(null);
/* 160 */       this.windingrule = localPathIterator.getWindingRule();
/* 161 */       if (localPathIterator.isDone())
/*     */       {
/* 164 */         this.numCoords = 8;
/*     */       }
/* 166 */       float[] arrayOfFloat1 = new float[6];
/* 167 */       int i = localPathIterator.currentSegment(arrayOfFloat1);
/* 168 */       localPathIterator.next();
/* 169 */       if (i != 0)
/* 170 */         throw new IllegalPathStateException("missing initial moveto");
/*     */       float f3;
/*     */       float tmp89_87 = (f3 = arrayOfFloat1[0]); float f1 = tmp89_87; this.bezierCoords[0] = tmp89_87;
/*     */       float f4;
/*     */       float tmp104_102 = (f4 = arrayOfFloat1[1]); float f2 = tmp104_102; this.bezierCoords[1] = tmp104_102;
/*     */ 
/* 176 */       Vector localVector = new Vector();
/* 177 */       this.numCoords = 2;
/*     */       float f5;
/*     */       float f6;
/* 178 */       while (!localPathIterator.isDone()) {
/* 179 */         switch (localPathIterator.currentSegment(arrayOfFloat1)) {
/*     */         case 0:
/* 181 */           if ((f1 != f3) || (f2 != f4)) {
/* 182 */             appendLineTo(f1, f2, f3, f4);
/* 183 */             f1 = f3;
/* 184 */             f2 = f4;
/*     */           }
/* 186 */           f5 = arrayOfFloat1[0];
/* 187 */           f6 = arrayOfFloat1[1];
/* 188 */           if ((f1 != f5) || (f2 != f6)) {
/* 189 */             localVector.add(new Point2D(f3, f4));
/* 190 */             appendLineTo(f1, f2, f5, f6);
/* 191 */             f1 = f3 = f5;
/* 192 */             f2 = f4 = f6; } break;
/*     */         case 4:
/* 196 */           if ((f1 != f3) || (f2 != f4)) {
/* 197 */             appendLineTo(f1, f2, f3, f4);
/* 198 */             f1 = f3;
/* 199 */             f2 = f4; } break;
/*     */         case 1:
/* 203 */           f5 = arrayOfFloat1[0];
/* 204 */           f6 = arrayOfFloat1[1];
/* 205 */           appendLineTo(f1, f2, f5, f6);
/* 206 */           f1 = f5;
/* 207 */           f2 = f6;
/* 208 */           break;
/*     */         case 2:
/* 210 */           float f7 = arrayOfFloat1[0];
/* 211 */           float f8 = arrayOfFloat1[1];
/* 212 */           f5 = arrayOfFloat1[2];
/* 213 */           f6 = arrayOfFloat1[3];
/* 214 */           appendQuadTo(f1, f2, f7, f8, f5, f6);
/* 215 */           f1 = f5;
/* 216 */           f2 = f6;
/* 217 */           break;
/*     */         case 3:
/* 219 */           appendCubicTo(arrayOfFloat1[0], arrayOfFloat1[1], arrayOfFloat1[2], arrayOfFloat1[3], f1 = arrayOfFloat1[4], f2 = arrayOfFloat1[5]);
/*     */         }
/*     */ 
/* 224 */         localPathIterator.next();
/*     */       }
/*     */ 
/* 229 */       if ((this.numCoords < 8) || (f1 != f3) || (f2 != f4)) {
/* 230 */         appendLineTo(f1, f2, f3, f4);
/* 231 */         f1 = f3;
/* 232 */         f2 = f4;
/*     */       }
/*     */ 
/* 236 */       for (int j = localVector.size() - 1; j >= 0; j--) {
/* 237 */         Point2D localPoint2D = (Point2D)localVector.get(j);
/* 238 */         f5 = localPoint2D.x;
/* 239 */         f6 = localPoint2D.y;
/* 240 */         if ((f1 != f5) || (f2 != f6)) {
/* 241 */           appendLineTo(f1, f2, f5, f6);
/* 242 */           f1 = f5;
/* 243 */           f2 = f6;
/*     */         }
/*     */       }
/*     */ 
/* 247 */       j = 0;
/* 248 */       float f9 = this.bezierCoords[0];
/* 249 */       float f10 = this.bezierCoords[1];
/* 250 */       for (int k = 6; k < this.numCoords; k += 6) {
/* 251 */         float f12 = this.bezierCoords[k];
/* 252 */         float f13 = this.bezierCoords[(k + 1)];
/* 253 */         if ((f13 < f10) || ((f13 == f10) && (f12 < f9))) {
/* 254 */           j = k;
/* 255 */           f9 = f12;
/* 256 */           f10 = f13;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 261 */       if (j > 0)
/*     */       {
/* 263 */         float[] arrayOfFloat2 = new float[this.numCoords];
/*     */ 
/* 266 */         System.arraycopy(this.bezierCoords, j, arrayOfFloat2, 0, this.numCoords - j);
/*     */ 
/* 276 */         System.arraycopy(this.bezierCoords, 2, arrayOfFloat2, this.numCoords - j, j);
/*     */ 
/* 279 */         this.bezierCoords = arrayOfFloat2;
/*     */       }
/*     */ 
/* 309 */       float f11 = 0.0F;
/*     */ 
/* 312 */       f1 = this.bezierCoords[0];
/* 313 */       f2 = this.bezierCoords[1];
/* 314 */       for (int m = 2; m < this.numCoords; m += 2) {
/* 315 */         f5 = this.bezierCoords[m];
/* 316 */         f6 = this.bezierCoords[(m + 1)];
/* 317 */         f11 += f1 * f6 - f5 * f2;
/* 318 */         f1 = f5;
/* 319 */         f2 = f6;
/*     */       }
/* 321 */       if (f11 < 0.0F)
/*     */       {
/* 336 */         m = 2;
/* 337 */         int n = this.numCoords - 4;
/* 338 */         while (m < n) {
/* 339 */           f1 = this.bezierCoords[m];
/* 340 */           f2 = this.bezierCoords[(m + 1)];
/* 341 */           this.bezierCoords[m] = this.bezierCoords[n];
/* 342 */           this.bezierCoords[(m + 1)] = this.bezierCoords[(n + 1)];
/* 343 */           this.bezierCoords[n] = f1;
/* 344 */           this.bezierCoords[(n + 1)] = f2;
/* 345 */           m += 2;
/* 346 */           n -= 2;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     private void appendLineTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */     {
/* 354 */       appendCubicTo(ShapeEvaluator.interp(paramFloat1, paramFloat3, 0.3333333F), ShapeEvaluator.interp(paramFloat2, paramFloat4, 0.3333333F), ShapeEvaluator.interp(paramFloat3, paramFloat1, 0.3333333F), ShapeEvaluator.interp(paramFloat4, paramFloat2, 0.3333333F), paramFloat3, paramFloat4);
/*     */     }
/*     */ 
/*     */     private void appendQuadTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
/*     */     {
/* 367 */       appendCubicTo(ShapeEvaluator.interp(paramFloat3, paramFloat1, 0.3333333F), ShapeEvaluator.interp(paramFloat4, paramFloat2, 0.3333333F), ShapeEvaluator.interp(paramFloat3, paramFloat5, 0.3333333F), ShapeEvaluator.interp(paramFloat4, paramFloat6, 0.3333333F), paramFloat5, paramFloat6);
/*     */     }
/*     */ 
/*     */     private void appendCubicTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
/*     */     {
/* 380 */       if (this.numCoords + 6 > this.bezierCoords.length)
/*     */       {
/* 382 */         int i = (this.numCoords - 2) * 2 + 2;
/* 383 */         float[] arrayOfFloat = new float[i];
/* 384 */         System.arraycopy(this.bezierCoords, 0, arrayOfFloat, 0, this.numCoords);
/* 385 */         this.bezierCoords = arrayOfFloat;
/*     */       }
/* 387 */       this.bezierCoords[(this.numCoords++)] = paramFloat1;
/* 388 */       this.bezierCoords[(this.numCoords++)] = paramFloat2;
/* 389 */       this.bezierCoords[(this.numCoords++)] = paramFloat3;
/* 390 */       this.bezierCoords[(this.numCoords++)] = paramFloat4;
/* 391 */       this.bezierCoords[(this.numCoords++)] = paramFloat5;
/* 392 */       this.bezierCoords[(this.numCoords++)] = paramFloat6;
/*     */     }
/*     */ 
/*     */     public int getWindingRule() {
/* 396 */       return this.windingrule;
/*     */     }
/*     */ 
/*     */     public int getNumCoords() {
/* 400 */       return this.numCoords;
/*     */     }
/*     */ 
/*     */     public float getCoord(int paramInt) {
/* 404 */       return this.bezierCoords[paramInt];
/*     */     }
/*     */ 
/*     */     public float[] getTvals() {
/* 408 */       if (this.myTvals != null) {
/* 409 */         return this.myTvals;
/*     */       }
/*     */ 
/* 414 */       float[] arrayOfFloat = new float[(this.numCoords - 2) / 6 + 1];
/*     */ 
/* 420 */       float f1 = this.bezierCoords[0];
/* 421 */       float f2 = this.bezierCoords[1];
/* 422 */       float f3 = 0.0F;
/* 423 */       int i = 2;
/* 424 */       int j = 0;
/*     */       float f5;
/* 425 */       while (i < this.numCoords)
/*     */       {
/* 427 */         f4 = f1;
/* 428 */         f5 = f2;
/* 429 */         float f6 = this.bezierCoords[(i++)];
/* 430 */         float f7 = this.bezierCoords[(i++)];
/* 431 */         f4 -= f6;
/* 432 */         f5 -= f7;
/* 433 */         float f8 = (float)Math.sqrt(f4 * f4 + f5 * f5);
/* 434 */         f4 = f6;
/* 435 */         f5 = f7;
/* 436 */         f6 = this.bezierCoords[(i++)];
/* 437 */         f7 = this.bezierCoords[(i++)];
/* 438 */         f4 -= f6;
/* 439 */         f5 -= f7;
/* 440 */         f8 += (float)Math.sqrt(f4 * f4 + f5 * f5);
/* 441 */         f4 = f6;
/* 442 */         f5 = f7;
/* 443 */         f6 = this.bezierCoords[(i++)];
/* 444 */         f7 = this.bezierCoords[(i++)];
/* 445 */         f4 -= f6;
/* 446 */         f5 -= f7;
/* 447 */         f8 += (float)Math.sqrt(f4 * f4 + f5 * f5);
/*     */ 
/* 449 */         f1 -= f6;
/* 450 */         f2 -= f7;
/* 451 */         f8 += (float)Math.sqrt(f1 * f1 + f2 * f2);
/*     */ 
/* 453 */         f8 /= 2.0F;
/*     */ 
/* 466 */         if (f8 < 0.001F) {
/* 467 */           f8 = 0.001F;
/*     */         }
/* 469 */         f3 += f8;
/* 470 */         arrayOfFloat[(j++)] = f3;
/* 471 */         f1 = f6;
/* 472 */         f2 = f7;
/*     */       }
/*     */ 
/* 477 */       float f4 = arrayOfFloat[0];
/* 478 */       arrayOfFloat[0] = 0.0F;
/* 479 */       for (j = 1; j < arrayOfFloat.length - 1; j++) {
/* 480 */         f5 = arrayOfFloat[j];
/* 481 */         arrayOfFloat[j] = (f4 / f3);
/* 482 */         f4 = f5;
/*     */       }
/* 484 */       arrayOfFloat[j] = 1.0F;
/* 485 */       return this.myTvals = arrayOfFloat;
/*     */     }
/*     */ 
/*     */     public void setTvals(float[] paramArrayOfFloat) {
/* 489 */       float[] arrayOfFloat1 = this.bezierCoords;
/* 490 */       float[] arrayOfFloat2 = new float[2 + (paramArrayOfFloat.length - 1) * 6];
/* 491 */       float[] arrayOfFloat3 = getTvals();
/* 492 */       int i = 0;
/*     */       float f4;
/*     */       float f3;
/*     */       float f2;
/* 495 */       float f1 = f2 = f3 = f4 = arrayOfFloat1[(i++)];
/*     */       float f8;
/*     */       float f7;
/*     */       float f6;
/* 496 */       float f5 = f6 = f7 = f8 = arrayOfFloat1[(i++)];
/* 497 */       int j = 0;
/* 498 */       arrayOfFloat2[(j++)] = f1;
/* 499 */       arrayOfFloat2[(j++)] = f5;
/* 500 */       float f9 = 0.0F;
/* 501 */       float f10 = 0.0F;
/* 502 */       int k = 1;
/* 503 */       int m = 1;
/* 504 */       while (m < paramArrayOfFloat.length) {
/* 505 */         if (f9 >= f10) {
/* 506 */           f1 = f4;
/* 507 */           f5 = f8;
/* 508 */           f2 = arrayOfFloat1[(i++)];
/* 509 */           f6 = arrayOfFloat1[(i++)];
/* 510 */           f3 = arrayOfFloat1[(i++)];
/* 511 */           f7 = arrayOfFloat1[(i++)];
/* 512 */           f4 = arrayOfFloat1[(i++)];
/* 513 */           f8 = arrayOfFloat1[(i++)];
/* 514 */           f10 = arrayOfFloat3[(k++)];
/*     */         }
/* 516 */         float f11 = paramArrayOfFloat[(m++)];
/*     */ 
/* 518 */         if (f11 < f10)
/*     */         {
/* 520 */           float f12 = (f11 - f9) / (f10 - f9);
/*     */           float tmp227_224 = ShapeEvaluator.interp(f1, f2, f12); f1 = tmp227_224; arrayOfFloat2[(j++)] = tmp227_224;
/*     */           float tmp246_243 = ShapeEvaluator.interp(f5, f6, f12); f5 = tmp246_243; arrayOfFloat2[(j++)] = tmp246_243;
/* 523 */           f2 = ShapeEvaluator.interp(f2, f3, f12);
/* 524 */           f6 = ShapeEvaluator.interp(f6, f7, f12);
/* 525 */           f3 = ShapeEvaluator.interp(f3, f4, f12);
/* 526 */           f7 = ShapeEvaluator.interp(f7, f8, f12);
/*     */           float tmp309_306 = ShapeEvaluator.interp(f1, f2, f12); f1 = tmp309_306; arrayOfFloat2[(j++)] = tmp309_306;
/*     */           float tmp328_325 = ShapeEvaluator.interp(f5, f6, f12); f5 = tmp328_325; arrayOfFloat2[(j++)] = tmp328_325;
/* 529 */           f2 = ShapeEvaluator.interp(f2, f3, f12);
/* 530 */           f6 = ShapeEvaluator.interp(f6, f7, f12);
/*     */           float tmp369_366 = ShapeEvaluator.interp(f1, f2, f12); f1 = tmp369_366; arrayOfFloat2[(j++)] = tmp369_366;
/*     */           float tmp388_385 = ShapeEvaluator.interp(f5, f6, f12); f5 = tmp388_385; arrayOfFloat2[(j++)] = tmp388_385;
/*     */         } else {
/* 534 */           arrayOfFloat2[(j++)] = f2;
/* 535 */           arrayOfFloat2[(j++)] = f6;
/* 536 */           arrayOfFloat2[(j++)] = f3;
/* 537 */           arrayOfFloat2[(j++)] = f7;
/* 538 */           arrayOfFloat2[(j++)] = f4;
/* 539 */           arrayOfFloat2[(j++)] = f8;
/*     */         }
/* 541 */         f9 = f11;
/*     */       }
/* 543 */       this.bezierCoords = arrayOfFloat2;
/* 544 */       this.numCoords = arrayOfFloat2.length;
/* 545 */       this.myTvals = paramArrayOfFloat;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.sg.prism.ShapeEvaluator
 * JD-Core Version:    0.6.2
 */