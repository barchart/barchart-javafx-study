/*     */ package com.sun.openpisces;
/*     */ 
/*     */ import com.sun.javafx.geom.PathConsumer2D;
/*     */ 
/*     */ public final class Dasher
/*     */   implements PathConsumer2D
/*     */ {
/*     */   private final PathConsumer2D out;
/*     */   private float[] dash;
/*     */   private float startPhase;
/*     */   private boolean startDashOn;
/*     */   private int startIdx;
/*     */   private boolean starting;
/*     */   private boolean needsMoveTo;
/*     */   private int idx;
/*     */   private boolean dashOn;
/*     */   private float phase;
/*     */   private float sx;
/*     */   private float sy;
/*     */   private float x0;
/*     */   private float y0;
/*     */   private float[] curCurvepts;
/* 146 */   private float[] firstSegmentsBuffer = new float[7];
/* 147 */   private int firstSegidx = 0;
/*     */ 
/* 228 */   private LengthIterator li = null;
/*     */ 
/*     */   public Dasher(PathConsumer2D paramPathConsumer2D, float[] paramArrayOfFloat, float paramFloat)
/*     */   {
/*  70 */     this(paramPathConsumer2D);
/*  71 */     reset(paramArrayOfFloat, paramFloat);
/*     */   }
/*     */ 
/*     */   public Dasher(PathConsumer2D paramPathConsumer2D) {
/*  75 */     this.out = paramPathConsumer2D;
/*     */ 
/*  79 */     this.curCurvepts = new float[16];
/*     */   }
/*     */ 
/*     */   public void reset(float[] paramArrayOfFloat, float paramFloat) {
/*  83 */     if (paramFloat < 0.0F) {
/*  84 */       throw new IllegalArgumentException("phase < 0 !");
/*     */     }
/*     */ 
/*  88 */     int i = 0;
/*  89 */     this.dashOn = true;
/*     */     float f;
/*  91 */     while (paramFloat >= (f = paramArrayOfFloat[i])) {
/*  92 */       paramFloat -= f;
/*  93 */       i = (i + 1) % paramArrayOfFloat.length;
/*  94 */       this.dashOn = (!this.dashOn);
/*     */     }
/*     */ 
/*  97 */     this.dash = paramArrayOfFloat;
/*  98 */     this.startPhase = (this.phase = paramFloat);
/*  99 */     this.startDashOn = this.dashOn;
/* 100 */     this.startIdx = i;
/* 101 */     this.starting = true;
/*     */   }
/*     */ 
/*     */   public void moveTo(float paramFloat1, float paramFloat2) {
/* 105 */     if (this.firstSegidx > 0) {
/* 106 */       this.out.moveTo(this.sx, this.sy);
/* 107 */       emitFirstSegments();
/*     */     }
/* 109 */     this.needsMoveTo = true;
/* 110 */     this.idx = this.startIdx;
/* 111 */     this.dashOn = this.startDashOn;
/* 112 */     this.phase = this.startPhase;
/* 113 */     this.sx = (this.x0 = paramFloat1);
/* 114 */     this.sy = (this.y0 = paramFloat2);
/* 115 */     this.starting = true;
/*     */   }
/*     */ 
/*     */   private void emitSeg(float[] paramArrayOfFloat, int paramInt1, int paramInt2) {
/* 119 */     switch (paramInt2) {
/*     */     case 8:
/* 121 */       this.out.curveTo(paramArrayOfFloat[(paramInt1 + 0)], paramArrayOfFloat[(paramInt1 + 1)], paramArrayOfFloat[(paramInt1 + 2)], paramArrayOfFloat[(paramInt1 + 3)], paramArrayOfFloat[(paramInt1 + 4)], paramArrayOfFloat[(paramInt1 + 5)]);
/*     */ 
/* 124 */       break;
/*     */     case 6:
/* 126 */       this.out.quadTo(paramArrayOfFloat[(paramInt1 + 0)], paramArrayOfFloat[(paramInt1 + 1)], paramArrayOfFloat[(paramInt1 + 2)], paramArrayOfFloat[(paramInt1 + 3)]);
/*     */ 
/* 128 */       break;
/*     */     case 4:
/* 130 */       this.out.lineTo(paramArrayOfFloat[paramInt1], paramArrayOfFloat[(paramInt1 + 1)]);
/*     */     case 5:
/*     */     case 7:
/*     */     }
/*     */   }
/* 135 */   private void emitFirstSegments() { for (int i = 0; i < this.firstSegidx; ) {
/* 136 */       emitSeg(this.firstSegmentsBuffer, i + 1, (int)this.firstSegmentsBuffer[i]);
/* 137 */       i += (int)this.firstSegmentsBuffer[i] - 1;
/*     */     }
/* 139 */     this.firstSegidx = 0;
/*     */   }
/*     */ 
/*     */   private void goTo(float[] paramArrayOfFloat, int paramInt1, int paramInt2)
/*     */   {
/* 151 */     float f1 = paramArrayOfFloat[(paramInt1 + paramInt2 - 4)];
/* 152 */     float f2 = paramArrayOfFloat[(paramInt1 + paramInt2 - 3)];
/* 153 */     if (this.dashOn) {
/* 154 */       if (this.starting) {
/* 155 */         this.firstSegmentsBuffer = Helpers.widenArray(this.firstSegmentsBuffer, this.firstSegidx, paramInt2 - 2);
/*     */ 
/* 158 */         this.firstSegmentsBuffer[(this.firstSegidx++)] = paramInt2;
/* 159 */         System.arraycopy(paramArrayOfFloat, paramInt1, this.firstSegmentsBuffer, this.firstSegidx, paramInt2 - 2);
/* 160 */         this.firstSegidx += paramInt2 - 2;
/*     */       } else {
/* 162 */         if (this.needsMoveTo) {
/* 163 */           this.out.moveTo(this.x0, this.y0);
/* 164 */           this.needsMoveTo = false;
/*     */         }
/* 166 */         emitSeg(paramArrayOfFloat, paramInt1, paramInt2);
/*     */       }
/*     */     } else {
/* 169 */       this.starting = false;
/* 170 */       this.needsMoveTo = true;
/*     */     }
/* 172 */     this.x0 = f1;
/* 173 */     this.y0 = f2;
/*     */   }
/*     */ 
/*     */   public void lineTo(float paramFloat1, float paramFloat2) {
/* 177 */     float f1 = paramFloat1 - this.x0;
/* 178 */     float f2 = paramFloat2 - this.y0;
/*     */ 
/* 180 */     float f3 = (float)Math.sqrt(f1 * f1 + f2 * f2);
/*     */ 
/* 182 */     if (f3 == 0.0F) {
/* 183 */       return;
/*     */     }
/*     */ 
/* 188 */     float f4 = f1 / f3;
/* 189 */     float f5 = f2 / f3;
/*     */     while (true)
/*     */     {
/* 192 */       float f6 = this.dash[this.idx] - this.phase;
/* 193 */       if (f3 <= f6) {
/* 194 */         this.curCurvepts[0] = paramFloat1;
/* 195 */         this.curCurvepts[1] = paramFloat2;
/* 196 */         goTo(this.curCurvepts, 0, 4);
/*     */ 
/* 198 */         this.phase += f3;
/* 199 */         if (f3 == f6) {
/* 200 */           this.phase = 0.0F;
/* 201 */           this.idx = ((this.idx + 1) % this.dash.length);
/* 202 */           this.dashOn = (!this.dashOn);
/*     */         }
/* 204 */         return;
/*     */       }
/*     */ 
/* 207 */       float f7 = this.dash[this.idx] * f4;
/* 208 */       float f8 = this.dash[this.idx] * f5;
/* 209 */       if (this.phase == 0.0F) {
/* 210 */         this.curCurvepts[0] = (this.x0 + f7);
/* 211 */         this.curCurvepts[1] = (this.y0 + f8);
/*     */       } else {
/* 213 */         float f9 = f6 / this.dash[this.idx];
/* 214 */         this.curCurvepts[0] = (this.x0 + f9 * f7);
/* 215 */         this.curCurvepts[1] = (this.y0 + f9 * f8);
/*     */       }
/*     */ 
/* 218 */       goTo(this.curCurvepts, 0, 4);
/*     */ 
/* 220 */       f3 -= f6;
/*     */ 
/* 222 */       this.idx = ((this.idx + 1) % this.dash.length);
/* 223 */       this.dashOn = (!this.dashOn);
/* 224 */       this.phase = 0.0F;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void somethingTo(int paramInt)
/*     */   {
/* 233 */     if (pointCurve(this.curCurvepts, paramInt)) {
/* 234 */       return;
/*     */     }
/* 236 */     if (this.li == null) {
/* 237 */       this.li = new LengthIterator(4, 0.01F);
/*     */     }
/* 239 */     this.li.initializeIterationOnCurve(this.curCurvepts, paramInt);
/*     */ 
/* 241 */     int i = 0;
/* 242 */     float f1 = 0.0F;
/* 243 */     float f2 = 0.0F;
/* 244 */     float f3 = this.dash[this.idx] - this.phase;
/* 245 */     while ((f2 = this.li.next(f3)) < 1.0F) {
/* 246 */       if (f2 != 0.0F) {
/* 247 */         Helpers.subdivideAt((f2 - f1) / (1.0F - f1), this.curCurvepts, i, this.curCurvepts, 0, this.curCurvepts, paramInt, paramInt);
/*     */ 
/* 251 */         f1 = f2;
/* 252 */         goTo(this.curCurvepts, 2, paramInt);
/* 253 */         i = paramInt;
/*     */       }
/*     */ 
/* 256 */       this.idx = ((this.idx + 1) % this.dash.length);
/* 257 */       this.dashOn = (!this.dashOn);
/* 258 */       this.phase = 0.0F;
/* 259 */       f3 = this.dash[this.idx];
/*     */     }
/* 261 */     goTo(this.curCurvepts, i + 2, paramInt);
/* 262 */     this.phase += this.li.lastSegLen();
/* 263 */     if (this.phase >= this.dash[this.idx]) {
/* 264 */       this.phase = 0.0F;
/* 265 */       this.idx = ((this.idx + 1) % this.dash.length);
/* 266 */       this.dashOn = (!this.dashOn);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static boolean pointCurve(float[] paramArrayOfFloat, int paramInt) {
/* 271 */     for (int i = 2; i < paramInt; i++) {
/* 272 */       if (paramArrayOfFloat[i] != paramArrayOfFloat[(i - 2)]) {
/* 273 */         return false;
/*     */       }
/*     */     }
/* 276 */     return true;
/*     */   }
/*     */ 
/*     */   public void curveTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
/*     */   {
/* 546 */     this.curCurvepts[0] = this.x0; this.curCurvepts[1] = this.y0;
/* 547 */     this.curCurvepts[2] = paramFloat1; this.curCurvepts[3] = paramFloat2;
/* 548 */     this.curCurvepts[4] = paramFloat3; this.curCurvepts[5] = paramFloat4;
/* 549 */     this.curCurvepts[6] = paramFloat5; this.curCurvepts[7] = paramFloat6;
/* 550 */     somethingTo(8);
/*     */   }
/*     */ 
/*     */   public void quadTo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/* 555 */     this.curCurvepts[0] = this.x0; this.curCurvepts[1] = this.y0;
/* 556 */     this.curCurvepts[2] = paramFloat1; this.curCurvepts[3] = paramFloat2;
/* 557 */     this.curCurvepts[4] = paramFloat3; this.curCurvepts[5] = paramFloat4;
/* 558 */     somethingTo(6);
/*     */   }
/*     */ 
/*     */   public void closePath() {
/* 562 */     lineTo(this.sx, this.sy);
/* 563 */     if (this.firstSegidx > 0) {
/* 564 */       if ((!this.dashOn) || (this.needsMoveTo)) {
/* 565 */         this.out.moveTo(this.sx, this.sy);
/*     */       }
/* 567 */       emitFirstSegments();
/*     */     }
/* 569 */     moveTo(this.sx, this.sy);
/*     */   }
/*     */ 
/*     */   public void pathDone() {
/* 573 */     if (this.firstSegidx > 0) {
/* 574 */       this.out.moveTo(this.sx, this.sy);
/* 575 */       emitFirstSegments();
/*     */     }
/* 577 */     this.out.pathDone();
/*     */   }
/*     */ 
/*     */   private static class LengthIterator
/*     */   {
/*     */     private float[][] recCurveStack;
/*     */     private Side[] sides;
/*     */     private int curveType;
/*     */     private final int limit;
/*     */     private final float ERR;
/*     */     private final float minTincrement;
/*     */     private float nextT;
/*     */     private float lenAtNextT;
/*     */     private float lastT;
/*     */     private float lenAtLastT;
/*     */     private float lenAtLastSplit;
/*     */     private float lastSegLen;
/*     */     private int recLevel;
/*     */     private boolean done;
/* 321 */     private float[] curLeafCtrlPolyLengths = new float[3];
/*     */ 
/* 361 */     private int cachedHaveLowAcceleration = -1;
/*     */ 
/* 394 */     private float[] nextRoots = new float[4];
/*     */ 
/* 400 */     private float[] flatLeafCoefCache = { 0.0F, 0.0F, -1.0F, 0.0F };
/*     */ 
/*     */     public LengthIterator(int paramInt, float paramFloat)
/*     */     {
/* 324 */       this.limit = paramInt;
/* 325 */       this.minTincrement = (1.0F / (1 << this.limit));
/* 326 */       this.ERR = paramFloat;
/* 327 */       this.recCurveStack = new float[paramInt + 1][8];
/* 328 */       this.sides = new Side[paramInt];
/*     */ 
/* 331 */       this.nextT = 3.4028235E+38F;
/* 332 */       this.lenAtNextT = 3.4028235E+38F;
/* 333 */       this.lenAtLastSplit = 1.4E-45F;
/* 334 */       this.recLevel = -2147483648;
/* 335 */       this.lastSegLen = 3.4028235E+38F;
/* 336 */       this.done = true;
/*     */     }
/*     */ 
/*     */     public void initializeIterationOnCurve(float[] paramArrayOfFloat, int paramInt) {
/* 340 */       System.arraycopy(paramArrayOfFloat, 0, this.recCurveStack[0], 0, paramInt);
/* 341 */       this.curveType = paramInt;
/* 342 */       this.recLevel = 0;
/* 343 */       this.lastT = 0.0F;
/* 344 */       this.lenAtLastT = 0.0F;
/* 345 */       this.nextT = 0.0F;
/* 346 */       this.lenAtNextT = 0.0F;
/* 347 */       goLeft();
/* 348 */       this.lenAtLastSplit = 0.0F;
/* 349 */       if (this.recLevel > 0) {
/* 350 */         this.sides[0] = Side.LEFT;
/* 351 */         this.done = false;
/*     */       }
/*     */       else {
/* 354 */         this.sides[0] = Side.RIGHT;
/* 355 */         this.done = true;
/*     */       }
/* 357 */       this.lastSegLen = 0.0F;
/*     */     }
/*     */ 
/*     */     private boolean haveLowAcceleration(float paramFloat)
/*     */     {
/* 364 */       if (this.cachedHaveLowAcceleration == -1) {
/* 365 */         float f1 = this.curLeafCtrlPolyLengths[0];
/* 366 */         float f2 = this.curLeafCtrlPolyLengths[1];
/*     */ 
/* 370 */         if (!Helpers.within(f1, f2, paramFloat * f2)) {
/* 371 */           this.cachedHaveLowAcceleration = 0;
/* 372 */           return false;
/*     */         }
/* 374 */         if (this.curveType == 8) {
/* 375 */           float f3 = this.curLeafCtrlPolyLengths[2];
/*     */ 
/* 379 */           if ((!Helpers.within(f2, f3, paramFloat * f3)) || (!Helpers.within(f1, f3, paramFloat * f3)))
/*     */           {
/* 381 */             this.cachedHaveLowAcceleration = 0;
/* 382 */             return false;
/*     */           }
/*     */         }
/* 385 */         this.cachedHaveLowAcceleration = 1;
/* 386 */         return true;
/*     */       }
/*     */ 
/* 389 */       return this.cachedHaveLowAcceleration == 1;
/*     */     }
/*     */ 
/*     */     public float next(float paramFloat)
/*     */     {
/* 405 */       float f1 = this.lenAtLastSplit + paramFloat;
/* 406 */       while (this.lenAtNextT < f1) {
/* 407 */         if (this.done) {
/* 408 */           this.lastSegLen = (this.lenAtNextT - this.lenAtLastSplit);
/* 409 */           return 1.0F;
/*     */         }
/* 411 */         goToNextLeaf();
/*     */       }
/* 413 */       this.lenAtLastSplit = f1;
/* 414 */       float f2 = this.lenAtNextT - this.lenAtLastT;
/* 415 */       float f3 = (f1 - this.lenAtLastT) / f2;
/*     */ 
/* 419 */       if (!haveLowAcceleration(0.05F))
/*     */       {
/* 425 */         if (this.flatLeafCoefCache[2] < 0.0F) {
/* 426 */           f4 = 0.0F + this.curLeafCtrlPolyLengths[0];
/* 427 */           f5 = f4 + this.curLeafCtrlPolyLengths[1];
/* 428 */           if (this.curveType == 8) {
/* 429 */             f6 = f5 + this.curLeafCtrlPolyLengths[2];
/* 430 */             this.flatLeafCoefCache[0] = (3.0F * (f4 - f5) + f6);
/* 431 */             this.flatLeafCoefCache[1] = (3.0F * (f5 - 2.0F * f4));
/* 432 */             this.flatLeafCoefCache[2] = (3.0F * f4);
/* 433 */             this.flatLeafCoefCache[3] = (-f6);
/* 434 */           } else if (this.curveType == 6) {
/* 435 */             this.flatLeafCoefCache[0] = 0.0F;
/* 436 */             this.flatLeafCoefCache[1] = (f5 - 2.0F * f4);
/* 437 */             this.flatLeafCoefCache[2] = (2.0F * f4);
/* 438 */             this.flatLeafCoefCache[3] = (-f5);
/*     */           }
/*     */         }
/* 441 */         float f4 = this.flatLeafCoefCache[0];
/* 442 */         float f5 = this.flatLeafCoefCache[1];
/* 443 */         float f6 = this.flatLeafCoefCache[2];
/* 444 */         float f7 = f3 * this.flatLeafCoefCache[3];
/*     */ 
/* 449 */         int i = Helpers.cubicRootsInAB(f4, f5, f6, f7, this.nextRoots, 0, 0.0F, 1.0F);
/* 450 */         if ((i == 1) && (!Float.isNaN(this.nextRoots[0]))) {
/* 451 */           f3 = this.nextRoots[0];
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 456 */       f3 = f3 * (this.nextT - this.lastT) + this.lastT;
/* 457 */       if (f3 >= 1.0F) {
/* 458 */         f3 = 1.0F;
/* 459 */         this.done = true;
/*     */       }
/*     */ 
/* 466 */       this.lastSegLen = paramFloat;
/* 467 */       return f3;
/*     */     }
/*     */ 
/*     */     public float lastSegLen() {
/* 471 */       return this.lastSegLen;
/*     */     }
/*     */ 
/*     */     private void goToNextLeaf()
/*     */     {
/* 479 */       this.recLevel -= 1;
/* 480 */       while (this.sides[this.recLevel] == Side.RIGHT) {
/* 481 */         if (this.recLevel == 0) {
/* 482 */           this.done = true;
/* 483 */           return;
/*     */         }
/* 485 */         this.recLevel -= 1;
/*     */       }
/*     */ 
/* 488 */       this.sides[this.recLevel] = Side.RIGHT;
/* 489 */       System.arraycopy(this.recCurveStack[this.recLevel], 0, this.recCurveStack[(this.recLevel + 1)], 0, this.curveType);
/*     */ 
/* 491 */       this.recLevel += 1;
/* 492 */       goLeft();
/*     */     }
/*     */ 
/*     */     private void goLeft()
/*     */     {
/* 497 */       float f = onLeaf();
/* 498 */       if (f >= 0.0F) {
/* 499 */         this.lastT = this.nextT;
/* 500 */         this.lenAtLastT = this.lenAtNextT;
/* 501 */         this.nextT += (1 << this.limit - this.recLevel) * this.minTincrement;
/* 502 */         this.lenAtNextT += f;
/*     */ 
/* 504 */         this.flatLeafCoefCache[2] = -1.0F;
/* 505 */         this.cachedHaveLowAcceleration = -1;
/*     */       } else {
/* 507 */         Helpers.subdivide(this.recCurveStack[this.recLevel], 0, this.recCurveStack[(this.recLevel + 1)], 0, this.recCurveStack[this.recLevel], 0, this.curveType);
/*     */ 
/* 510 */         this.sides[this.recLevel] = Side.LEFT;
/* 511 */         this.recLevel += 1;
/* 512 */         goLeft();
/*     */       }
/*     */     }
/*     */ 
/*     */     private float onLeaf()
/*     */     {
/* 519 */       float[] arrayOfFloat = this.recCurveStack[this.recLevel];
/* 520 */       float f1 = 0.0F;
/*     */ 
/* 522 */       float f2 = arrayOfFloat[0]; float f3 = arrayOfFloat[1];
/* 523 */       for (int i = 2; i < this.curveType; i += 2) {
/* 524 */         float f5 = arrayOfFloat[i]; float f6 = arrayOfFloat[(i + 1)];
/* 525 */         float f7 = Helpers.linelen(f2, f3, f5, f6);
/* 526 */         f1 += f7;
/* 527 */         this.curLeafCtrlPolyLengths[(i / 2 - 1)] = f7;
/* 528 */         f2 = f5;
/* 529 */         f3 = f6;
/*     */       }
/*     */ 
/* 532 */       float f4 = Helpers.linelen(arrayOfFloat[0], arrayOfFloat[1], arrayOfFloat[(this.curveType - 2)], arrayOfFloat[(this.curveType - 1)]);
/*     */ 
/* 534 */       if ((f1 - f4 < this.ERR) || (this.recLevel == this.limit)) {
/* 535 */         return (f1 + f4) / 2.0F;
/*     */       }
/* 537 */       return -1.0F;
/*     */     }
/*     */ 
/*     */     private static enum Side
/*     */     {
/* 293 */       LEFT, RIGHT;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.openpisces.Dasher
 * JD-Core Version:    0.6.2
 */