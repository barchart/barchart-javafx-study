/*     */ package com.sun.prism.util.tess.impl.tess;
/*     */ 
/*     */ class PriorityQHeap extends PriorityQ
/*     */ {
/*     */   PriorityQ.PQnode[] nodes;
/*     */   PriorityQ.PQhandleElem[] handles;
/*     */   int size;
/*     */   int max;
/*     */   int freeList;
/*     */   boolean initialized;
/*     */   PriorityQ.Leq leq;
/*     */ 
/*     */   public PriorityQHeap(PriorityQ.Leq paramLeq)
/*     */   {
/*  65 */     this.size = 0;
/*  66 */     this.max = 32;
/*  67 */     this.nodes = new PriorityQ.PQnode[33];
/*  68 */     for (int i = 0; i < this.nodes.length; i++) {
/*  69 */       this.nodes[i] = new PriorityQ.PQnode();
/*     */     }
/*  71 */     this.handles = new PriorityQ.PQhandleElem[33];
/*  72 */     for (i = 0; i < this.handles.length; i++) {
/*  73 */       this.handles[i] = new PriorityQ.PQhandleElem();
/*     */     }
/*  75 */     this.initialized = false;
/*  76 */     this.freeList = 0;
/*  77 */     this.leq = paramLeq;
/*     */ 
/*  79 */     this.nodes[1].handle = 1;
/*  80 */     this.handles[1].key = null;
/*     */   }
/*     */ 
/*     */   void pqDeletePriorityQ()
/*     */   {
/*  85 */     this.handles = null;
/*  86 */     this.nodes = null;
/*     */   }
/*     */ 
/*     */   void FloatDown(int paramInt) {
/*  90 */     PriorityQ.PQnode[] arrayOfPQnode = this.nodes;
/*  91 */     PriorityQ.PQhandleElem[] arrayOfPQhandleElem = this.handles;
/*     */ 
/*  95 */     int i = arrayOfPQnode[paramInt].handle;
/*     */     while (true) {
/*  97 */       int k = paramInt << 1;
/*  98 */       if ((k < this.size) && (LEQ(this.leq, arrayOfPQhandleElem[arrayOfPQnode[(k + 1)].handle].key, arrayOfPQhandleElem[arrayOfPQnode[k].handle].key)))
/*     */       {
/* 100 */         k++;
/*     */       }
/*     */ 
/* 103 */       assert (k <= this.max);
/*     */ 
/* 105 */       int j = arrayOfPQnode[k].handle;
/* 106 */       if ((k > this.size) || (LEQ(this.leq, arrayOfPQhandleElem[i].key, arrayOfPQhandleElem[j].key))) {
/* 107 */         arrayOfPQnode[paramInt].handle = i;
/* 108 */         arrayOfPQhandleElem[i].node = paramInt;
/* 109 */         break;
/*     */       }
/* 111 */       arrayOfPQnode[paramInt].handle = j;
/* 112 */       arrayOfPQhandleElem[j].node = paramInt;
/* 113 */       paramInt = k;
/*     */     }
/*     */   }
/*     */ 
/*     */   void FloatUp(int paramInt)
/*     */   {
/* 119 */     PriorityQ.PQnode[] arrayOfPQnode = this.nodes;
/* 120 */     PriorityQ.PQhandleElem[] arrayOfPQhandleElem = this.handles;
/*     */ 
/* 124 */     int i = arrayOfPQnode[paramInt].handle;
/*     */     while (true) {
/* 126 */       int k = paramInt >> 1;
/* 127 */       int j = arrayOfPQnode[k].handle;
/* 128 */       if ((k == 0) || (LEQ(this.leq, arrayOfPQhandleElem[j].key, arrayOfPQhandleElem[i].key))) {
/* 129 */         arrayOfPQnode[paramInt].handle = i;
/* 130 */         arrayOfPQhandleElem[i].node = paramInt;
/* 131 */         break;
/*     */       }
/* 133 */       arrayOfPQnode[paramInt].handle = j;
/* 134 */       arrayOfPQhandleElem[j].node = paramInt;
/* 135 */       paramInt = k;
/*     */     }
/*     */   }
/*     */ 
/*     */   boolean pqInit()
/*     */   {
/* 145 */     for (int i = this.size; i >= 1; i--) {
/* 146 */       FloatDown(i);
/*     */     }
/* 148 */     this.initialized = true;
/*     */ 
/* 150 */     return true;
/*     */   }
/*     */ 
/*     */   int pqInsert(Object paramObject)
/*     */   {
/* 159 */     int i = ++this.size;
/* 160 */     if (i * 2 > this.max) {
/* 161 */       PriorityQ.PQnode[] arrayOfPQnode1 = this.nodes;
/* 162 */       PriorityQ.PQhandleElem[] arrayOfPQhandleElem1 = this.handles;
/*     */ 
/* 165 */       this.max <<= 1;
/*     */ 
/* 167 */       PriorityQ.PQnode[] arrayOfPQnode2 = new PriorityQ.PQnode[this.max + 1];
/* 168 */       System.arraycopy(this.nodes, 0, arrayOfPQnode2, 0, this.nodes.length);
/* 169 */       for (int k = this.nodes.length; k < arrayOfPQnode2.length; k++) {
/* 170 */         arrayOfPQnode2[k] = new PriorityQ.PQnode();
/*     */       }
/* 172 */       this.nodes = arrayOfPQnode2;
/* 173 */       if (this.nodes == null) {
/* 174 */         this.nodes = arrayOfPQnode1;
/* 175 */         return 2147483647;
/*     */       }
/*     */ 
/* 179 */       PriorityQ.PQhandleElem[] arrayOfPQhandleElem2 = new PriorityQ.PQhandleElem[this.max + 1];
/* 180 */       System.arraycopy(this.handles, 0, arrayOfPQhandleElem2, 0, this.handles.length);
/* 181 */       for (int m = this.handles.length; m < arrayOfPQhandleElem2.length; m++) {
/* 182 */         arrayOfPQhandleElem2[m] = new PriorityQ.PQhandleElem();
/*     */       }
/* 184 */       this.handles = arrayOfPQhandleElem2;
/* 185 */       if (this.handles == null) {
/* 186 */         this.handles = arrayOfPQhandleElem1;
/* 187 */         return 2147483647;
/*     */       }
/*     */     }
/*     */     int j;
/* 191 */     if (this.freeList == 0) {
/* 192 */       j = i;
/*     */     } else {
/* 194 */       j = this.freeList;
/* 195 */       this.freeList = this.handles[j].node;
/*     */     }
/*     */ 
/* 198 */     this.nodes[i].handle = j;
/* 199 */     this.handles[j].node = i;
/* 200 */     this.handles[j].key = paramObject;
/*     */ 
/* 202 */     if (this.initialized) {
/* 203 */       FloatUp(i);
/*     */     }
/* 205 */     assert (j != 2147483647);
/* 206 */     return j;
/*     */   }
/*     */ 
/*     */   Object pqExtractMin()
/*     */   {
/* 211 */     PriorityQ.PQnode[] arrayOfPQnode = this.nodes;
/* 212 */     PriorityQ.PQhandleElem[] arrayOfPQhandleElem = this.handles;
/* 213 */     int i = arrayOfPQnode[1].handle;
/* 214 */     Object localObject = arrayOfPQhandleElem[i].key;
/*     */ 
/* 216 */     if (this.size > 0) {
/* 217 */       arrayOfPQnode[1].handle = arrayOfPQnode[this.size].handle;
/* 218 */       arrayOfPQhandleElem[arrayOfPQnode[1].handle].node = 1;
/*     */ 
/* 220 */       arrayOfPQhandleElem[i].key = null;
/* 221 */       arrayOfPQhandleElem[i].node = this.freeList;
/* 222 */       this.freeList = i;
/*     */ 
/* 224 */       if (--this.size > 0) {
/* 225 */         FloatDown(1);
/*     */       }
/*     */     }
/* 228 */     return localObject;
/*     */   }
/*     */ 
/*     */   void pqDelete(int paramInt)
/*     */   {
/* 233 */     PriorityQ.PQnode[] arrayOfPQnode = this.nodes;
/* 234 */     PriorityQ.PQhandleElem[] arrayOfPQhandleElem = this.handles;
/*     */ 
/* 237 */     assert ((paramInt >= 1) && (paramInt <= this.max) && (arrayOfPQhandleElem[paramInt].key != null));
/*     */ 
/* 239 */     int i = arrayOfPQhandleElem[paramInt].node;
/* 240 */     arrayOfPQnode[i].handle = arrayOfPQnode[this.size].handle;
/* 241 */     arrayOfPQhandleElem[arrayOfPQnode[i].handle].node = i;
/*     */ 
/* 243 */     if (i <= --this.size) {
/* 244 */       if ((i <= 1) || (LEQ(this.leq, arrayOfPQhandleElem[arrayOfPQnode[(i >> 1)].handle].key, arrayOfPQhandleElem[arrayOfPQnode[i].handle].key)))
/* 245 */         FloatDown(i);
/*     */       else {
/* 247 */         FloatUp(i);
/*     */       }
/*     */     }
/* 250 */     arrayOfPQhandleElem[paramInt].key = null;
/* 251 */     arrayOfPQhandleElem[paramInt].node = this.freeList;
/* 252 */     this.freeList = paramInt;
/*     */   }
/*     */ 
/*     */   Object pqMinimum() {
/* 256 */     return this.handles[this.nodes[1].handle].key;
/*     */   }
/*     */ 
/*     */   boolean pqIsEmpty() {
/* 260 */     return this.size == 0;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.util.tess.impl.tess.PriorityQHeap
 * JD-Core Version:    0.6.2
 */