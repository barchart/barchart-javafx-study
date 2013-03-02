/*     */ package com.sun.javafx.sg;
/*     */ 
/*     */ import com.sun.javafx.geom.RectBounds;
/*     */ 
/*     */ public final class DirtyRegionContainer
/*     */ {
/*     */   public static final int DTR_OK = 1;
/*     */   public static final int DTR_INVALID = 0;
/*     */   private RectBounds[] dirtyRegions;
/*     */   private int emptyIndex;
/*     */   private int[][] heap;
/*     */   private int heapSize;
/*     */   private long invalidMask;
/*     */ 
/*     */   DirtyRegionContainer(int paramInt)
/*     */   {
/*  26 */     this.dirtyRegions = new RectBounds[paramInt];
/*  27 */     for (int i = 0; i < paramInt; i++) {
/*  28 */       this.dirtyRegions[i] = new RectBounds();
/*     */     }
/*  30 */     this.emptyIndex = 0;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/*  35 */     if ((paramObject instanceof DirtyRegionContainer)) {
/*  36 */       DirtyRegionContainer localDirtyRegionContainer = (DirtyRegionContainer)paramObject;
/*  37 */       if (size() != localDirtyRegionContainer.size()) return false;
/*  38 */       for (int i = 0; i < this.emptyIndex; i++) {
/*  39 */         if (!getDirtyRegion(i).equals(localDirtyRegionContainer.getDirtyRegion(i))) return false;
/*     */       }
/*  41 */       return true;
/*     */     }
/*  43 */     return false;
/*     */   }
/*     */ 
/*     */   public DirtyRegionContainer deriveWithNewRegions(RectBounds[] paramArrayOfRectBounds)
/*     */   {
/*  49 */     if ((paramArrayOfRectBounds == null) || (paramArrayOfRectBounds.length == 0))
/*     */     {
/*  52 */       return this;
/*     */     }
/*  54 */     if (paramArrayOfRectBounds.length > maxSpace()) {
/*  55 */       this.dirtyRegions = new RectBounds[paramArrayOfRectBounds.length];
/*     */     }
/*     */ 
/*  58 */     regioncopy(paramArrayOfRectBounds, 0, this.dirtyRegions, 0, paramArrayOfRectBounds.length);
/*  59 */     this.emptyIndex = paramArrayOfRectBounds.length;
/*  60 */     return this;
/*     */   }
/*     */ 
/*     */   public DirtyRegionContainer deriveWithNewContainer(DirtyRegionContainer paramDirtyRegionContainer) {
/*  64 */     if ((paramDirtyRegionContainer == null) || (paramDirtyRegionContainer.maxSpace() == 0))
/*     */     {
/*  67 */       return this;
/*     */     }
/*     */ 
/*  70 */     if (paramDirtyRegionContainer.maxSpace() > maxSpace()) {
/*  71 */       this.dirtyRegions = new RectBounds[paramDirtyRegionContainer.maxSpace()];
/*     */     }
/*     */ 
/*  74 */     regioncopy(paramDirtyRegionContainer.dirtyRegions, 0, this.dirtyRegions, 0, paramDirtyRegionContainer.emptyIndex);
/*  75 */     this.emptyIndex = paramDirtyRegionContainer.emptyIndex;
/*  76 */     return this;
/*     */   }
/*     */ 
/*     */   public DirtyRegionContainer copy() {
/*  80 */     DirtyRegionContainer localDirtyRegionContainer = new DirtyRegionContainer(maxSpace());
/*  81 */     regioncopy(this.dirtyRegions, 0, localDirtyRegionContainer.dirtyRegions, 0, this.emptyIndex);
/*  82 */     localDirtyRegionContainer.emptyIndex = this.emptyIndex;
/*  83 */     return localDirtyRegionContainer;
/*     */   }
/*     */ 
/*     */   public int maxSpace() {
/*  87 */     return this.dirtyRegions.length;
/*     */   }
/*     */ 
/*     */   public RectBounds getDirtyRegion(int paramInt)
/*     */   {
/*  96 */     return this.dirtyRegions[paramInt];
/*     */   }
/*     */ 
/*     */   public RectBounds addDirtyRegion(RectBounds paramRectBounds)
/*     */   {
/* 105 */     int i = 0;
/* 106 */     int j = this.emptyIndex;
/*     */     RectBounds localRectBounds1;
/* 108 */     for (int k = 0; k < j; k++)
/*     */     {
/* 110 */       localRectBounds1 = this.dirtyRegions[i];
/* 111 */       if (paramRectBounds.intersects(localRectBounds1)) {
/* 112 */         paramRectBounds.unionWith(localRectBounds1);
/* 113 */         RectBounds localRectBounds2 = this.dirtyRegions[i];
/* 114 */         this.dirtyRegions[i] = this.dirtyRegions[(this.emptyIndex - 1)];
/* 115 */         this.dirtyRegions[(this.emptyIndex - 1)] = localRectBounds2;
/* 116 */         this.emptyIndex -= 1;
/*     */       } else {
/* 118 */         i++;
/*     */       }
/*     */     }
/* 121 */     if (hasSpace()) {
/* 122 */       localRectBounds1 = this.dirtyRegions[this.emptyIndex];
/* 123 */       localRectBounds1.deriveWithNewBounds(paramRectBounds);
/* 124 */       this.emptyIndex += 1;
/* 125 */       return localRectBounds1;
/*     */     }
/*     */ 
/* 128 */     if (this.dirtyRegions.length == 1) {
/* 129 */       return (RectBounds)this.dirtyRegions[0].deriveWithUnion(paramRectBounds);
/*     */     }
/* 131 */     return compress(paramRectBounds);
/*     */   }
/*     */ 
/*     */   public void merge(DirtyRegionContainer paramDirtyRegionContainer) {
/* 135 */     int i = paramDirtyRegionContainer.size();
/* 136 */     for (int j = 0; j < i; j++)
/* 137 */       addDirtyRegion(paramDirtyRegionContainer.getDirtyRegion(j));
/*     */   }
/*     */ 
/*     */   public int size()
/*     */   {
/* 142 */     return this.emptyIndex;
/*     */   }
/*     */ 
/*     */   public void reset() {
/* 146 */     this.emptyIndex = 0;
/*     */   }
/*     */ 
/*     */   private RectBounds compress(RectBounds paramRectBounds) {
/* 150 */     compress_heap();
/* 151 */     addDirtyRegion(paramRectBounds);
/* 152 */     return paramRectBounds;
/*     */   }
/*     */ 
/*     */   private boolean hasSpace()
/*     */   {
/* 160 */     return this.emptyIndex < this.dirtyRegions.length;
/*     */   }
/*     */ 
/*     */   private void regioncopy(RectBounds[] paramArrayOfRectBounds1, int paramInt1, RectBounds[] paramArrayOfRectBounds2, int paramInt2, int paramInt3)
/*     */   {
/* 165 */     for (int i = 0; i < paramInt3; i++) {
/* 166 */       RectBounds localRectBounds = paramArrayOfRectBounds1[(paramInt1++)];
/* 167 */       if (localRectBounds == null)
/* 168 */         paramArrayOfRectBounds2[(paramInt2++)] = null;
/*     */       else
/* 170 */         paramArrayOfRectBounds2[(paramInt2++)].deriveWithNewBounds(localRectBounds);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 177 */     StringBuilder localStringBuilder = new StringBuilder();
/* 178 */     for (int i = 0; i < this.emptyIndex; i++) {
/* 179 */       localStringBuilder.append(this.dirtyRegions[i]);
/* 180 */       localStringBuilder.append('\n');
/*     */     }
/* 182 */     return localStringBuilder.toString();
/*     */   }
/*     */ 
/*     */   private void heapCompress()
/*     */   {
/* 193 */     this.invalidMask = 0L;
/* 194 */     int[] arrayOfInt1 = new int[this.dirtyRegions.length];
/* 195 */     for (int i = 0; i < arrayOfInt1.length; i++) {
/* 196 */       arrayOfInt1[i] = i;
/*     */     }
/*     */ 
/* 200 */     for (int j = 0; j < this.dirtyRegions.length / 2; j++) {
/* 201 */       int[] arrayOfInt2 = takeMinWithMap(arrayOfInt1);
/* 202 */       k = resolveMap(arrayOfInt1, arrayOfInt2[1]);
/* 203 */       int m = resolveMap(arrayOfInt1, arrayOfInt2[2]);
/* 204 */       if (k != m) {
/* 205 */         this.dirtyRegions[k].deriveWithUnion(this.dirtyRegions[m]);
/* 206 */         arrayOfInt1[m] = k;
/* 207 */         this.invalidMask |= 1 << k;
/* 208 */         this.invalidMask |= 1 << m;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 214 */     for (int k = 0; k < this.emptyIndex; k++)
/* 215 */       if (arrayOfInt1[k] != k) {
/* 216 */         while (arrayOfInt1[(this.emptyIndex - 1)] != this.emptyIndex - 1) this.emptyIndex -= 1;
/* 217 */         if (k < this.emptyIndex - 1) {
/* 218 */           RectBounds localRectBounds = this.dirtyRegions[(this.emptyIndex - 1)];
/* 219 */           this.dirtyRegions[(this.emptyIndex - 1)] = this.dirtyRegions[k];
/* 220 */           this.dirtyRegions[k] = localRectBounds;
/* 221 */           arrayOfInt1[k] = k;
/* 222 */           this.emptyIndex -= 1;
/*     */         }
/*     */       }
/*     */   }
/*     */ 
/*     */   private void heapify()
/*     */   {
/* 229 */     for (int i = this.heapSize / 2; i >= 0; i--)
/* 230 */       siftDown(i);
/*     */   }
/*     */ 
/*     */   private void siftDown(int paramInt)
/*     */   {
/* 235 */     int i = this.heapSize >> 1;
/*     */ 
/* 237 */     while (paramInt < i) {
/* 238 */       int j = (paramInt << 1) + 1;
/* 239 */       int[] arrayOfInt2 = this.heap[j];
/* 240 */       if ((j + 1 < this.heapSize) && (this.heap[(j + 1)][0] < arrayOfInt2[0])) {
/* 241 */         j += 1;
/*     */       }
/* 243 */       if (this.heap[j][0] >= this.heap[paramInt][0]) {
/*     */         break;
/*     */       }
/* 246 */       int[] arrayOfInt1 = this.heap[j];
/* 247 */       this.heap[j] = this.heap[paramInt];
/* 248 */       this.heap[paramInt] = arrayOfInt1;
/* 249 */       paramInt = j;
/*     */     }
/*     */   }
/*     */ 
/*     */   private int[] takeMinWithMap(int[] paramArrayOfInt) {
/* 254 */     int[] arrayOfInt = this.heap[0];
/*     */ 
/* 259 */     while (((1 << arrayOfInt[1] | 1 << arrayOfInt[2]) & this.invalidMask) > 0L) {
/* 260 */       arrayOfInt[0] = unifiedRegionArea(resolveMap(paramArrayOfInt, arrayOfInt[1]), resolveMap(paramArrayOfInt, arrayOfInt[2]));
/* 261 */       siftDown(0);
/* 262 */       if (this.heap[0] == arrayOfInt) {
/*     */         break;
/*     */       }
/* 265 */       arrayOfInt = this.heap[0];
/*     */     }
/*     */ 
/* 268 */     this.heap[(this.heapSize - 1)] = arrayOfInt;
/* 269 */     siftDown(0);
/* 270 */     this.heapSize -= 1;
/* 271 */     return arrayOfInt;
/*     */   }
/*     */ 
/*     */   private int[] takeMin() {
/* 275 */     int[] arrayOfInt = this.heap[0];
/* 276 */     this.heap[0] = this.heap[(this.heapSize - 1)];
/* 277 */     this.heap[(this.heapSize - 1)] = arrayOfInt;
/* 278 */     siftDown(0);
/* 279 */     this.heapSize -= 1;
/* 280 */     return arrayOfInt;
/*     */   }
/*     */ 
/*     */   private int resolveMap(int[] paramArrayOfInt, int paramInt) {
/* 284 */     while (paramArrayOfInt[paramInt] != paramInt) paramInt = paramArrayOfInt[paramInt];
/* 285 */     return paramInt;
/*     */   }
/*     */ 
/*     */   private int unifiedRegionArea(int paramInt1, int paramInt2) {
/* 289 */     RectBounds localRectBounds1 = this.dirtyRegions[paramInt1];
/* 290 */     RectBounds localRectBounds2 = this.dirtyRegions[paramInt2];
/*     */ 
/* 292 */     float f1 = localRectBounds1.getMinX() < localRectBounds2.getMinX() ? localRectBounds1.getMinX() : localRectBounds2.getMinX();
/* 293 */     float f2 = localRectBounds1.getMinY() < localRectBounds2.getMinY() ? localRectBounds1.getMinY() : localRectBounds2.getMinY();
/* 294 */     float f3 = localRectBounds1.getMaxX() > localRectBounds2.getMaxX() ? localRectBounds1.getMaxX() : localRectBounds2.getMaxX();
/* 295 */     float f4 = localRectBounds1.getMaxY() > localRectBounds2.getMaxY() ? localRectBounds1.getMaxY() : localRectBounds2.getMaxY();
/*     */ 
/* 297 */     return (int)((f3 - f1) * (f4 - f2));
/*     */   }
/*     */ 
/*     */   private void compress_heap()
/*     */   {
/* 304 */     assert (this.dirtyRegions.length == this.emptyIndex);
/* 305 */     if (this.heap == null) {
/* 306 */       i = this.dirtyRegions.length;
/* 307 */       this.heap = new int[i * (i - 1) / 2][3];
/*     */     }
/* 309 */     this.heapSize = this.heap.length;
/* 310 */     int i = 0;
/* 311 */     for (int j = 0; j < this.dirtyRegions.length - 1; j++) {
/* 312 */       for (int k = j + 1; k < this.dirtyRegions.length; k++) {
/* 313 */         this.heap[i][0] = unifiedRegionArea(j, k);
/* 314 */         this.heap[i][1] = j;
/* 315 */         this.heap[(i++)][2] = k;
/*     */       }
/*     */     }
/* 318 */     heapify();
/* 319 */     heapCompress();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.sg.DirtyRegionContainer
 * JD-Core Version:    0.6.2
 */