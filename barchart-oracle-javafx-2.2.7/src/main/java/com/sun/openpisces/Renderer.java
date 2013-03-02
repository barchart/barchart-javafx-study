/*     */ package com.sun.openpisces;
/*     */ 
/*     */ import com.sun.javafx.geom.PathConsumer2D;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ public final class Renderer
/*     */   implements PathConsumer2D
/*     */ {
/*     */   private static final int YMAX = 0;
/*     */   private static final int CURX = 1;
/*     */   private static final int OR = 2;
/*     */   private static final int SLOPE = 3;
/*     */   private static final int NEXT = 4;
/*     */   private static final int SIZEOF_EDGE = 5;
/*     */   private int sampleRowMin;
/*     */   private int sampleRowMax;
/*     */   private float edgeMinX;
/*     */   private float edgeMaxX;
/*     */   private float[] edges;
/*     */   private int[] edgeBuckets;
/*     */   private int numEdges;
/*     */   private static final float DEC_BND = 20.0F;
/*     */   private static final float INC_BND = 8.0F;
/*     */   public static final int WIND_EVEN_ODD = 0;
/*     */   public static final int WIND_NON_ZERO = 1;
/*     */   private final int SUBPIXEL_LG_POSITIONS_X;
/*     */   private final int SUBPIXEL_LG_POSITIONS_Y;
/*     */   private final int SUBPIXEL_POSITIONS_X;
/*     */   private final int SUBPIXEL_POSITIONS_Y;
/*     */   private final int SUBPIXEL_MASK_X;
/*     */   private final int SUBPIXEL_MASK_Y;
/*     */   final int MAX_AA_ALPHA;
/*     */   private int boundsMinX;
/*     */   private int boundsMinY;
/*     */   private int boundsMaxX;
/*     */   private int boundsMaxY;
/*     */   private int windingRule;
/*     */   private float x0;
/*     */   private float y0;
/*     */   private float pix_sx0;
/*     */   private float pix_sy0;
/* 415 */   private Curve c = new Curve();
/*     */   private int[] savedAlpha;
/*     */   private ScanlineIterator savedIterator;
/*     */ 
/*     */   private void addEdgeToBucket(int paramInt1, int paramInt2)
/*     */   {
/* 165 */     this.edges[(paramInt1 + 4)] = this.edgeBuckets[(paramInt2 * 2)];
/* 166 */     this.edgeBuckets[(paramInt2 * 2)] = (paramInt1 + 1);
/* 167 */     this.edgeBuckets[(paramInt2 * 2 + 1)] += 2;
/*     */   }
/*     */ 
/*     */   private void quadBreakIntoLinesAndAdd(float paramFloat1, float paramFloat2, Curve paramCurve, float paramFloat3, float paramFloat4)
/*     */   {
/* 179 */     int i = 16;
/* 180 */     int j = i * i;
/* 181 */     float f1 = Math.max(paramCurve.dbx / j, paramCurve.dby / j);
/* 182 */     while (f1 > 32.0F) {
/* 183 */       f1 /= 4.0F;
/* 184 */       i <<= 1;
/*     */     }
/*     */ 
/* 187 */     j = i * i;
/* 188 */     float f2 = paramCurve.dbx / j;
/* 189 */     float f3 = paramCurve.dby / j;
/* 190 */     float f4 = paramCurve.bx / j + paramCurve.cx / i;
/* 191 */     float f5 = paramCurve.by / j + paramCurve.cy / i;
/*     */ 
/* 193 */     while (i-- > 1) {
/* 194 */       float f6 = paramFloat1 + f4;
/* 195 */       f4 += f2;
/* 196 */       float f7 = paramFloat2 + f5;
/* 197 */       f5 += f3;
/* 198 */       addLine(paramFloat1, paramFloat2, f6, f7);
/* 199 */       paramFloat1 = f6;
/* 200 */       paramFloat2 = f7;
/*     */     }
/* 202 */     addLine(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/*     */   }
/*     */ 
/*     */   private void curveBreakIntoLinesAndAdd(float paramFloat1, float paramFloat2, Curve paramCurve, float paramFloat3, float paramFloat4)
/*     */   {
/* 215 */     int i = 8;
/*     */ 
/* 220 */     float f1 = 2.0F * paramCurve.dax / 512.0F;
/* 221 */     float f2 = 2.0F * paramCurve.day / 512.0F;
/*     */ 
/* 223 */     float f3 = f1 + paramCurve.dbx / 64.0F;
/* 224 */     float f4 = f2 + paramCurve.dby / 64.0F;
/* 225 */     float f5 = paramCurve.ax / 512.0F + paramCurve.bx / 64.0F + paramCurve.cx / 8.0F;
/* 226 */     float f6 = paramCurve.ay / 512.0F + paramCurve.by / 64.0F + paramCurve.cy / 8.0F;
/*     */ 
/* 229 */     float f7 = paramFloat1; float f8 = paramFloat2;
/* 230 */     while (i > 0) {
/* 231 */       while ((Math.abs(f3) > 20.0F) || (Math.abs(f4) > 20.0F)) {
/* 232 */         f1 /= 8.0F;
/* 233 */         f2 /= 8.0F;
/* 234 */         f3 = f3 / 4.0F - f1;
/* 235 */         f4 = f4 / 4.0F - f2;
/* 236 */         f5 = (f5 - f3) / 2.0F;
/* 237 */         f6 = (f6 - f4) / 2.0F;
/* 238 */         i <<= 1;
/*     */       }
/*     */ 
/* 241 */       while ((i % 2 == 0) && (Math.abs(f5) <= 8.0F) && (Math.abs(f6) <= 8.0F)) {
/* 242 */         f5 = 2.0F * f5 + f3;
/* 243 */         f6 = 2.0F * f6 + f4;
/* 244 */         f3 = 4.0F * (f3 + f1);
/* 245 */         f4 = 4.0F * (f4 + f2);
/* 246 */         f1 = 8.0F * f1;
/* 247 */         f2 = 8.0F * f2;
/* 248 */         i >>= 1;
/*     */       }
/* 250 */       i--;
/* 251 */       if (i > 0) {
/* 252 */         f7 += f5;
/* 253 */         f5 += f3;
/* 254 */         f3 += f1;
/* 255 */         f8 += f6;
/* 256 */         f6 += f4;
/* 257 */         f4 += f2;
/*     */       } else {
/* 259 */         f7 = paramFloat3;
/* 260 */         f8 = paramFloat4;
/*     */       }
/* 262 */       addLine(paramFloat1, paramFloat2, f7, f8);
/* 263 */       paramFloat1 = f7;
/* 264 */       paramFloat2 = f8;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void addLine(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
/* 269 */     float f1 = 1.0F;
/* 270 */     if (paramFloat4 < paramFloat2) {
/* 271 */       f1 = paramFloat4;
/* 272 */       paramFloat4 = paramFloat2;
/* 273 */       paramFloat2 = f1;
/* 274 */       f1 = paramFloat3;
/* 275 */       paramFloat3 = paramFloat1;
/* 276 */       paramFloat1 = f1;
/* 277 */       f1 = 0.0F;
/*     */     }
/* 279 */     int i = Math.max((int)Math.ceil(paramFloat2), this.boundsMinY);
/* 280 */     int j = Math.min((int)Math.ceil(paramFloat4), this.boundsMaxY);
/* 281 */     if (i >= j) {
/* 282 */       return;
/*     */     }
/* 284 */     if (i < this.sampleRowMin) this.sampleRowMin = i;
/* 285 */     if (j > this.sampleRowMax) this.sampleRowMax = j;
/*     */ 
/* 287 */     float f2 = (paramFloat3 - paramFloat1) / (paramFloat4 - paramFloat2);
/*     */ 
/* 289 */     if (f2 > 0.0F) {
/* 290 */       if (paramFloat1 < this.edgeMinX) this.edgeMinX = paramFloat1;
/* 291 */       if (paramFloat3 > this.edgeMaxX) this.edgeMaxX = paramFloat3; 
/*     */     }
/* 293 */     else { if (paramFloat3 < this.edgeMinX) this.edgeMinX = paramFloat3;
/* 294 */       if (paramFloat1 > this.edgeMaxX) this.edgeMaxX = paramFloat1;
/*     */     }
/*     */ 
/* 297 */     int k = this.numEdges * 5;
/* 298 */     this.edges = Helpers.widenArray(this.edges, k, 5);
/* 299 */     this.numEdges += 1;
/* 300 */     this.edges[(k + 2)] = f1;
/* 301 */     this.edges[(k + 1)] = (paramFloat1 + (i - paramFloat2) * f2);
/* 302 */     this.edges[(k + 3)] = f2;
/* 303 */     this.edges[(k + 0)] = j;
/* 304 */     int m = i - this.boundsMinY;
/* 305 */     addEdgeToBucket(k, m);
/* 306 */     this.edgeBuckets[((j - this.boundsMinY) * 2 + 1)] |= 1;
/*     */   }
/*     */ 
/*     */   public Renderer(int paramInt1, int paramInt2)
/*     */   {
/* 339 */     this.SUBPIXEL_LG_POSITIONS_X = paramInt1;
/* 340 */     this.SUBPIXEL_LG_POSITIONS_Y = paramInt2;
/* 341 */     this.SUBPIXEL_POSITIONS_X = (1 << this.SUBPIXEL_LG_POSITIONS_X);
/* 342 */     this.SUBPIXEL_POSITIONS_Y = (1 << this.SUBPIXEL_LG_POSITIONS_Y);
/* 343 */     this.SUBPIXEL_MASK_X = (this.SUBPIXEL_POSITIONS_X - 1);
/* 344 */     this.SUBPIXEL_MASK_Y = (this.SUBPIXEL_POSITIONS_Y - 1);
/* 345 */     this.MAX_AA_ALPHA = (this.SUBPIXEL_POSITIONS_X * this.SUBPIXEL_POSITIONS_Y);
/*     */   }
/*     */ 
/*     */   public Renderer(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7)
/*     */   {
/* 353 */     this(paramInt1, paramInt2);
/* 354 */     reset(paramInt3, paramInt4, paramInt5, paramInt6, paramInt7);
/*     */   }
/*     */ 
/*     */   public void reset(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
/*     */   {
/* 362 */     this.windingRule = paramInt5;
/*     */ 
/* 364 */     this.boundsMinX = (paramInt1 * this.SUBPIXEL_POSITIONS_X);
/* 365 */     this.boundsMinY = (paramInt2 * this.SUBPIXEL_POSITIONS_Y);
/* 366 */     this.boundsMaxX = ((paramInt1 + paramInt3) * this.SUBPIXEL_POSITIONS_X);
/* 367 */     this.boundsMaxY = ((paramInt2 + paramInt4) * this.SUBPIXEL_POSITIONS_Y);
/*     */ 
/* 369 */     this.edgeMinX = (1.0F / 1.0F);
/* 370 */     this.edgeMaxX = (1.0F / -1.0F);
/* 371 */     this.sampleRowMax = this.boundsMinY;
/* 372 */     this.sampleRowMin = this.boundsMaxY;
/*     */ 
/* 374 */     int i = this.boundsMaxY - this.boundsMinY;
/* 375 */     if ((this.edgeBuckets == null) || (this.edgeBuckets.length < i * 2 + 2))
/*     */     {
/* 379 */       this.edgeBuckets = new int[i * 2 + 2];
/*     */     }
/*     */     else
/*     */     {
/* 383 */       Arrays.fill(this.edgeBuckets, 0, i * 2, 0);
/*     */     }
/* 385 */     if (this.edges == null) {
/* 386 */       this.edges = new float[' '];
/*     */     }
/* 388 */     this.numEdges = 0;
/* 389 */     this.pix_sx0 = (this.pix_sy0 = this.x0 = this.y0 = 0.0F);
/*     */   }
/*     */ 
/*     */   private float tosubpixx(float paramFloat) {
/* 393 */     return paramFloat * this.SUBPIXEL_POSITIONS_X;
/*     */   }
/*     */   private float tosubpixy(float paramFloat) {
/* 396 */     return paramFloat * this.SUBPIXEL_POSITIONS_Y;
/*     */   }
/*     */ 
/*     */   public void moveTo(float paramFloat1, float paramFloat2) {
/* 400 */     closePath();
/* 401 */     this.pix_sx0 = paramFloat1;
/* 402 */     this.pix_sy0 = paramFloat2;
/* 403 */     this.y0 = tosubpixy(paramFloat2);
/* 404 */     this.x0 = tosubpixx(paramFloat1);
/*     */   }
/*     */ 
/*     */   public void lineTo(float paramFloat1, float paramFloat2) {
/* 408 */     float f1 = tosubpixx(paramFloat1);
/* 409 */     float f2 = tosubpixy(paramFloat2);
/* 410 */     addLine(this.x0, this.y0, f1, f2);
/* 411 */     this.x0 = f1;
/* 412 */     this.y0 = f2;
/*     */   }
/*     */ 
/*     */   public void curveTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
/*     */   {
/* 420 */     float f1 = tosubpixx(paramFloat5);
/* 421 */     float f2 = tosubpixy(paramFloat6);
/* 422 */     this.c.set(this.x0, this.y0, tosubpixx(paramFloat1), tosubpixy(paramFloat2), tosubpixx(paramFloat3), tosubpixy(paramFloat4), f1, f2);
/* 423 */     curveBreakIntoLinesAndAdd(this.x0, this.y0, this.c, f1, f2);
/* 424 */     this.x0 = f1;
/* 425 */     this.y0 = f2;
/*     */   }
/*     */ 
/*     */   public void quadTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
/* 429 */     float f1 = tosubpixx(paramFloat3);
/* 430 */     float f2 = tosubpixy(paramFloat4);
/* 431 */     this.c.set(this.x0, this.y0, tosubpixx(paramFloat1), tosubpixy(paramFloat2), f1, f2);
/* 432 */     quadBreakIntoLinesAndAdd(this.x0, this.y0, this.c, f1, f2);
/* 433 */     this.x0 = f1;
/* 434 */     this.y0 = f2;
/*     */   }
/*     */ 
/*     */   public void closePath()
/*     */   {
/* 439 */     lineTo(this.pix_sx0, this.pix_sy0);
/*     */   }
/*     */ 
/*     */   public void pathDone() {
/* 443 */     closePath();
/*     */   }
/*     */ 
/*     */   public void produceAlphas(AlphaConsumer paramAlphaConsumer)
/*     */   {
/* 449 */     paramAlphaConsumer.setMaxAlpha(this.MAX_AA_ALPHA);
/*     */ 
/* 453 */     int i = this.windingRule == 0 ? 1 : -1;
/*     */ 
/* 456 */     int j = paramAlphaConsumer.getWidth();
/* 457 */     int[] arrayOfInt1 = this.savedAlpha;
/* 458 */     if ((arrayOfInt1 == null) || (arrayOfInt1.length < j + 2))
/* 459 */       this.savedAlpha = (arrayOfInt1 = new int[j + 2]);
/*     */     else {
/* 461 */       Arrays.fill(arrayOfInt1, 0, j + 2, 0);
/*     */     }
/*     */ 
/* 464 */     int k = paramAlphaConsumer.getOriginX() << this.SUBPIXEL_LG_POSITIONS_X;
/* 465 */     int m = k + (j << this.SUBPIXEL_LG_POSITIONS_X);
/*     */ 
/* 473 */     int n = m >> this.SUBPIXEL_LG_POSITIONS_X;
/* 474 */     int i1 = k >> this.SUBPIXEL_LG_POSITIONS_Y;
/*     */ 
/* 476 */     int i2 = this.boundsMinY;
/* 477 */     ScanlineIterator localScanlineIterator = this.savedIterator;
/* 478 */     if (localScanlineIterator == null)
/* 479 */       this.savedIterator = (localScanlineIterator = new ScanlineIterator(null));
/*     */     else {
/* 481 */       localScanlineIterator.reset();
/*     */     }
/* 483 */     while (localScanlineIterator.hasNext()) {
/* 484 */       int i3 = localScanlineIterator.next();
/* 485 */       int[] arrayOfInt2 = localScanlineIterator.crossings;
/* 486 */       i2 = localScanlineIterator.curY();
/*     */       int i7;
/* 488 */       if (i3 > 0) {
/* 489 */         i4 = arrayOfInt2[0] >> 1;
/* 490 */         i5 = arrayOfInt2[(i3 - 1)] >> 1;
/* 491 */         i6 = Math.max(i4, k);
/* 492 */         i7 = Math.min(i5, m);
/*     */ 
/* 494 */         i1 = Math.min(i1, i6 >> this.SUBPIXEL_LG_POSITIONS_X);
/* 495 */         n = Math.max(n, i7 >> this.SUBPIXEL_LG_POSITIONS_X);
/*     */       }
/*     */ 
/* 498 */       int i4 = 0;
/* 499 */       int i5 = k;
/* 500 */       for (int i6 = 0; i6 < i3; i6++) {
/* 501 */         i7 = arrayOfInt2[i6];
/* 502 */         int i8 = i7 >> 1;
/* 503 */         int i9 = ((i7 & 0x1) << 1) - 1;
/* 504 */         if ((i4 & i) != 0) {
/* 505 */           int i10 = Math.max(i5, k);
/* 506 */           int i11 = Math.min(i8, m);
/* 507 */           if (i10 < i11) {
/* 508 */             i10 -= k;
/* 509 */             i11 -= k;
/*     */ 
/* 511 */             int i12 = i10 >> this.SUBPIXEL_LG_POSITIONS_X;
/* 512 */             int i13 = i11 - 1 >> this.SUBPIXEL_LG_POSITIONS_X;
/*     */ 
/* 514 */             if (i12 == i13)
/*     */             {
/* 516 */               arrayOfInt1[i12] += i11 - i10;
/* 517 */               arrayOfInt1[(i12 + 1)] -= i11 - i10;
/*     */             } else {
/* 519 */               int i14 = i11 >> this.SUBPIXEL_LG_POSITIONS_X;
/* 520 */               arrayOfInt1[i12] += this.SUBPIXEL_POSITIONS_X - (i10 & this.SUBPIXEL_MASK_X);
/* 521 */               arrayOfInt1[(i12 + 1)] += (i10 & this.SUBPIXEL_MASK_X);
/* 522 */               arrayOfInt1[i14] -= this.SUBPIXEL_POSITIONS_X - (i11 & this.SUBPIXEL_MASK_X);
/* 523 */               arrayOfInt1[(i14 + 1)] -= (i11 & this.SUBPIXEL_MASK_X);
/*     */             }
/*     */           }
/*     */         }
/* 527 */         i4 += i9;
/* 528 */         i5 = i8;
/*     */       }
/*     */ 
/* 534 */       if ((i2 & this.SUBPIXEL_MASK_Y) == this.SUBPIXEL_MASK_Y) {
/* 535 */         paramAlphaConsumer.setAndClearRelativeAlphas(arrayOfInt1, i2 >> this.SUBPIXEL_LG_POSITIONS_Y, i1, n);
/*     */ 
/* 537 */         n = m >> this.SUBPIXEL_LG_POSITIONS_X;
/* 538 */         i1 = k >> this.SUBPIXEL_LG_POSITIONS_Y;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 544 */     if ((i2 & this.SUBPIXEL_MASK_Y) < this.SUBPIXEL_MASK_Y)
/* 545 */       paramAlphaConsumer.setAndClearRelativeAlphas(arrayOfInt1, i2 >> this.SUBPIXEL_LG_POSITIONS_Y, i1, n);
/*     */   }
/*     */ 
/*     */   public int getSubpixMinX()
/*     */   {
/* 551 */     int i = (int)Math.ceil(this.edgeMinX);
/* 552 */     if (i < this.boundsMinX) i = this.boundsMinX;
/* 553 */     return i;
/*     */   }
/*     */ 
/*     */   public int getSubpixMaxX() {
/* 557 */     int i = (int)Math.ceil(this.edgeMaxX);
/* 558 */     if (i > this.boundsMaxX) i = this.boundsMaxX;
/* 559 */     return i;
/*     */   }
/*     */ 
/*     */   public int getSubpixMinY() {
/* 563 */     return this.sampleRowMin;
/*     */   }
/*     */ 
/*     */   public int getSubpixMaxY() {
/* 567 */     return this.sampleRowMax;
/*     */   }
/*     */ 
/*     */   public int getOutpixMinX() {
/* 571 */     return getSubpixMinX() >> this.SUBPIXEL_LG_POSITIONS_X;
/*     */   }
/*     */ 
/*     */   public int getOutpixMaxX() {
/* 575 */     return getSubpixMaxX() + this.SUBPIXEL_MASK_X >> this.SUBPIXEL_LG_POSITIONS_X;
/*     */   }
/*     */ 
/*     */   public int getOutpixMinY() {
/* 579 */     return this.sampleRowMin >> this.SUBPIXEL_LG_POSITIONS_Y;
/*     */   }
/*     */ 
/*     */   public int getOutpixMaxY() {
/* 583 */     return this.sampleRowMax + this.SUBPIXEL_MASK_Y >> this.SUBPIXEL_LG_POSITIONS_Y;
/*     */   }
/*     */ 
/*     */   private final class ScanlineIterator
/*     */   {
/*     */     private int[] crossings;
/*     */     private int[] edgePtrs;
/*     */     private int edgeCount;
/*     */     private int nextY;
/*     */     private static final int INIT_CROSSINGS_SIZE = 10;
/*     */ 
/*     */     private ScanlineIterator()
/*     */     {
/*  50 */       this.crossings = new int[10];
/*  51 */       this.edgePtrs = new int[10];
/*  52 */       reset();
/*     */     }
/*     */ 
/*     */     public void reset()
/*     */     {
/*  59 */       this.nextY = Renderer.this.sampleRowMin;
/*  60 */       this.edgeCount = 0;
/*     */     }
/*     */ 
/*     */     private int next()
/*     */     {
/*  65 */       int i = this.nextY++;
/*  66 */       int j = i - Renderer.this.boundsMinY;
/*  67 */       int k = this.edgeCount;
/*  68 */       int[] arrayOfInt1 = this.edgePtrs;
/*  69 */       float[] arrayOfFloat = Renderer.this.edges;
/*  70 */       int m = Renderer.this.edgeBuckets[(j * 2 + 1)];
/*     */       int i2;
/*  71 */       if ((m & 0x1) != 0) {
/*  72 */         n = 0;
/*  73 */         for (i1 = 0; i1 < k; i1++) {
/*  74 */           i2 = arrayOfInt1[i1];
/*  75 */           if (arrayOfFloat[(i2 + 0)] > i) {
/*  76 */             arrayOfInt1[(n++)] = i2;
/*     */           }
/*     */         }
/*  79 */         k = n;
/*     */       }
/*  81 */       arrayOfInt1 = Helpers.widenArray(arrayOfInt1, k, m >> 1);
/*  82 */       for (int n = Renderer.this.edgeBuckets[(j * 2)]; 
/*  83 */         n != 0; 
/*  84 */         n = (int)arrayOfFloat[(n + 4)])
/*     */       {
/*  86 */         arrayOfInt1[(k++)] = (--n);
/*     */       }
/*     */ 
/*  89 */       this.edgePtrs = arrayOfInt1;
/*  90 */       this.edgeCount = k;
/*     */ 
/*  94 */       int[] arrayOfInt2 = this.crossings;
/*  95 */       if (arrayOfInt2.length < k) {
/*  96 */         this.crossings = (arrayOfInt2 = new int[arrayOfInt1.length]);
/*     */       }
/*  98 */       for (int i1 = 0; i1 < k; i1++) {
/*  99 */         i2 = arrayOfInt1[i1];
/* 100 */         float f = arrayOfFloat[(i2 + 1)];
/* 101 */         int i3 = (int)f << 1;
/* 102 */         arrayOfFloat[(i2 + 1)] = (f + arrayOfFloat[(i2 + 3)]);
/* 103 */         if (arrayOfFloat[(i2 + 2)] > 0.0F) {
/* 104 */           i3 |= 1;
/*     */         }
/* 106 */         int i4 = i1;
/*     */         while (true) { i4--; if (i4 < 0) break;
/* 108 */           int i5 = arrayOfInt2[i4];
/* 109 */           if (i5 <= i3) {
/*     */             break;
/*     */           }
/* 112 */           arrayOfInt2[(i4 + 1)] = i5;
/* 113 */           arrayOfInt1[(i4 + 1)] = arrayOfInt1[i4];
/*     */         }
/* 115 */         arrayOfInt2[(i4 + 1)] = i3;
/* 116 */         arrayOfInt1[(i4 + 1)] = i2;
/*     */       }
/* 118 */       return k;
/*     */     }
/*     */ 
/*     */     private boolean hasNext() {
/* 122 */       return this.nextY < Renderer.this.sampleRowMax;
/*     */     }
/*     */ 
/*     */     private int curY() {
/* 126 */       return this.nextY - 1;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.openpisces.Renderer
 * JD-Core Version:    0.6.2
 */