/*     */ package com.sun.javafx.geom;
/*     */ 
/*     */ import java.util.NoSuchElementException;
/*     */ 
/*     */ public class FlatteningPathIterator
/*     */   implements PathIterator
/*     */ {
/*     */   static final int GROW_SIZE = 24;
/*     */   PathIterator src;
/*     */   float squareflat;
/*     */   int limit;
/*  50 */   volatile float[] hold = new float[14];
/*     */   float curx;
/*     */   float cury;
/*     */   float movx;
/*     */   float movy;
/*     */   int holdType;
/*     */   int holdEnd;
/*     */   int holdIndex;
/*     */   int[] levels;
/*     */   int levelIndex;
/*     */   boolean done;
/*     */ 
/*     */   public FlatteningPathIterator(PathIterator paramPathIterator, float paramFloat)
/*     */   {
/*  97 */     this(paramPathIterator, paramFloat, 10);
/*     */   }
/*     */ 
/*     */   public FlatteningPathIterator(PathIterator paramPathIterator, float paramFloat, int paramInt)
/*     */   {
/* 120 */     if (paramFloat < 0.0F) {
/* 121 */       throw new IllegalArgumentException("flatness must be >= 0");
/*     */     }
/* 123 */     if (paramInt < 0) {
/* 124 */       throw new IllegalArgumentException("limit must be >= 0");
/*     */     }
/* 126 */     this.src = paramPathIterator;
/* 127 */     this.squareflat = (paramFloat * paramFloat);
/* 128 */     this.limit = paramInt;
/* 129 */     this.levels = new int[paramInt + 1];
/*     */ 
/* 131 */     next(false);
/*     */   }
/*     */ 
/*     */   public float getFlatness()
/*     */   {
/* 139 */     return (float)Math.sqrt(this.squareflat);
/*     */   }
/*     */ 
/*     */   public int getRecursionLimit()
/*     */   {
/* 148 */     return this.limit;
/*     */   }
/*     */ 
/*     */   public int getWindingRule()
/*     */   {
/* 160 */     return this.src.getWindingRule();
/*     */   }
/*     */ 
/*     */   public boolean isDone()
/*     */   {
/* 169 */     return this.done;
/*     */   }
/*     */ 
/*     */   void ensureHoldCapacity(int paramInt)
/*     */   {
/* 177 */     if (this.holdIndex - paramInt < 0) {
/* 178 */       int i = this.hold.length - this.holdIndex;
/* 179 */       int j = this.hold.length + 24;
/* 180 */       float[] arrayOfFloat = new float[j];
/* 181 */       System.arraycopy(this.hold, this.holdIndex, arrayOfFloat, this.holdIndex + 24, i);
/*     */ 
/* 184 */       this.hold = arrayOfFloat;
/* 185 */       this.holdIndex += 24;
/* 186 */       this.holdEnd += 24;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void next()
/*     */   {
/* 196 */     next(true);
/*     */   }
/*     */ 
/*     */   private void next(boolean paramBoolean)
/*     */   {
/* 202 */     if (this.holdIndex >= this.holdEnd) {
/* 203 */       if (paramBoolean) {
/* 204 */         this.src.next();
/*     */       }
/* 206 */       if (this.src.isDone()) {
/* 207 */         this.done = true;
/* 208 */         return;
/*     */       }
/* 210 */       this.holdType = this.src.currentSegment(this.hold);
/* 211 */       this.levelIndex = 0;
/* 212 */       this.levels[0] = 0;
/*     */     }
/*     */     int i;
/* 215 */     switch (this.holdType) {
/*     */     case 0:
/*     */     case 1:
/* 218 */       this.curx = this.hold[0];
/* 219 */       this.cury = this.hold[1];
/* 220 */       if (this.holdType == 0) {
/* 221 */         this.movx = this.curx;
/* 222 */         this.movy = this.cury;
/*     */       }
/* 224 */       this.holdIndex = 0;
/* 225 */       this.holdEnd = 0;
/* 226 */       break;
/*     */     case 4:
/* 228 */       this.curx = this.movx;
/* 229 */       this.cury = this.movy;
/* 230 */       this.holdIndex = 0;
/* 231 */       this.holdEnd = 0;
/* 232 */       break;
/*     */     case 2:
/* 234 */       if (this.holdIndex >= this.holdEnd)
/*     */       {
/* 236 */         this.holdIndex = (this.hold.length - 6);
/* 237 */         this.holdEnd = (this.hold.length - 2);
/* 238 */         this.hold[(this.holdIndex + 0)] = this.curx;
/* 239 */         this.hold[(this.holdIndex + 1)] = this.cury;
/* 240 */         this.hold[(this.holdIndex + 2)] = this.hold[0];
/* 241 */         this.hold[(this.holdIndex + 3)] = this.hold[1];
/*     */         float tmp308_307 = this.hold[2]; this.curx = tmp308_307; this.hold[(this.holdIndex + 4)] = tmp308_307;
/*     */         float tmp330_329 = this.hold[3]; this.cury = tmp330_329; this.hold[(this.holdIndex + 5)] = tmp330_329;
/*     */       }
/*     */ 
/* 246 */       i = this.levels[this.levelIndex];
/* 247 */       while ((i < this.limit) && 
/* 248 */         (QuadCurve2D.getFlatnessSq(this.hold, this.holdIndex) >= this.squareflat))
/*     */       {
/* 252 */         ensureHoldCapacity(4);
/* 253 */         QuadCurve2D.subdivide(this.hold, this.holdIndex, this.hold, this.holdIndex - 4, this.hold, this.holdIndex);
/*     */ 
/* 256 */         this.holdIndex -= 4;
/*     */ 
/* 264 */         i++;
/* 265 */         this.levels[this.levelIndex] = i;
/* 266 */         this.levelIndex += 1;
/* 267 */         this.levels[this.levelIndex] = i;
/*     */       }
/*     */ 
/* 275 */       this.holdIndex += 4;
/* 276 */       this.levelIndex -= 1;
/* 277 */       break;
/*     */     case 3:
/* 279 */       if (this.holdIndex >= this.holdEnd)
/*     */       {
/* 281 */         this.holdIndex = (this.hold.length - 8);
/* 282 */         this.holdEnd = (this.hold.length - 2);
/* 283 */         this.hold[(this.holdIndex + 0)] = this.curx;
/* 284 */         this.hold[(this.holdIndex + 1)] = this.cury;
/* 285 */         this.hold[(this.holdIndex + 2)] = this.hold[0];
/* 286 */         this.hold[(this.holdIndex + 3)] = this.hold[1];
/* 287 */         this.hold[(this.holdIndex + 4)] = this.hold[2];
/* 288 */         this.hold[(this.holdIndex + 5)] = this.hold[3];
/*     */         float tmp628_627 = this.hold[4]; this.curx = tmp628_627; this.hold[(this.holdIndex + 6)] = tmp628_627;
/*     */         float tmp651_650 = this.hold[5]; this.cury = tmp651_650; this.hold[(this.holdIndex + 7)] = tmp651_650;
/*     */       }
/*     */ 
/* 293 */       i = this.levels[this.levelIndex];
/* 294 */       while ((i < this.limit) && 
/* 295 */         (CubicCurve2D.getFlatnessSq(this.hold, this.holdIndex) >= this.squareflat))
/*     */       {
/* 299 */         ensureHoldCapacity(6);
/* 300 */         CubicCurve2D.subdivide(this.hold, this.holdIndex, this.hold, this.holdIndex - 6, this.hold, this.holdIndex);
/*     */ 
/* 303 */         this.holdIndex -= 6;
/*     */ 
/* 311 */         i++;
/* 312 */         this.levels[this.levelIndex] = i;
/* 313 */         this.levelIndex += 1;
/* 314 */         this.levels[this.levelIndex] = i;
/*     */       }
/*     */ 
/* 322 */       this.holdIndex += 6;
/* 323 */       this.levelIndex -= 1;
/*     */     }
/*     */   }
/*     */ 
/*     */   public int currentSegment(float[] paramArrayOfFloat)
/*     */   {
/* 349 */     if (isDone()) {
/* 350 */       throw new NoSuchElementException("flattening iterator out of bounds");
/*     */     }
/* 352 */     int i = this.holdType;
/* 353 */     if (i != 4) {
/* 354 */       paramArrayOfFloat[0] = this.hold[(this.holdIndex + 0)];
/* 355 */       paramArrayOfFloat[1] = this.hold[(this.holdIndex + 1)];
/* 356 */       if (i != 0) {
/* 357 */         i = 1;
/*     */       }
/*     */     }
/* 360 */     return i;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.geom.FlatteningPathIterator
 * JD-Core Version:    0.6.2
 */