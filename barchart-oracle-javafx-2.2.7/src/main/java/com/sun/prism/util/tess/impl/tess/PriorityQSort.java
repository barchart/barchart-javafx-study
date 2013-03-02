/*     */ package com.sun.prism.util.tess.impl.tess;
/*     */ 
/*     */ class PriorityQSort extends PriorityQ
/*     */ {
/*     */   PriorityQHeap heap;
/*     */   Object[] keys;
/*     */   int[] order;
/*     */   int size;
/*     */   int max;
/*     */   boolean initialized;
/*     */   PriorityQ.Leq leq;
/*     */ 
/*     */   public PriorityQSort(PriorityQ.Leq paramLeq)
/*     */   {
/*  63 */     this.heap = new PriorityQHeap(paramLeq);
/*     */ 
/*  65 */     this.keys = new Object[32];
/*     */ 
/*  67 */     this.size = 0;
/*  68 */     this.max = 32;
/*  69 */     this.initialized = false;
/*  70 */     this.leq = paramLeq;
/*     */   }
/*     */ 
/*     */   void pqDeletePriorityQ()
/*     */   {
/*  75 */     if (this.heap != null) this.heap.pqDeletePriorityQ();
/*  76 */     this.order = null;
/*  77 */     this.keys = null;
/*     */   }
/*     */ 
/*     */   private static boolean LT(PriorityQ.Leq paramLeq, Object paramObject1, Object paramObject2) {
/*  81 */     return !PriorityQHeap.LEQ(paramLeq, paramObject2, paramObject1);
/*     */   }
/*     */ 
/*     */   private static boolean GT(PriorityQ.Leq paramLeq, Object paramObject1, Object paramObject2) {
/*  85 */     return !PriorityQHeap.LEQ(paramLeq, paramObject1, paramObject2);
/*     */   }
/*     */ 
/*     */   private static void Swap(int[] paramArrayOfInt, int paramInt1, int paramInt2)
/*     */   {
/*  90 */     int i = paramArrayOfInt[paramInt1];
/*  91 */     paramArrayOfInt[paramInt1] = paramArrayOfInt[paramInt2];
/*  92 */     paramArrayOfInt[paramInt2] = i;
/*     */   }
/*     */ 
/*     */   boolean pqInit()
/*     */   {
/* 106 */     Stack[] arrayOfStack = new Stack[50];
/* 107 */     for (int i1 = 0; i1 < arrayOfStack.length; i1++) {
/* 108 */       arrayOfStack[i1] = new Stack(null);
/*     */     }
/* 110 */     i1 = 0;
/*     */ 
/* 112 */     int i2 = 2016473283;
/*     */ 
/* 117 */     this.order = new int[this.size + 1];
/*     */ 
/* 122 */     int i = 0;
/* 123 */     int j = this.size - 1;
/* 124 */     int n = 0; for (int k = i; k <= j; k++)
/*     */     {
/* 126 */       this.order[k] = n;
/*     */ 
/* 124 */       n++;
/*     */     }
/*     */ 
/* 132 */     arrayOfStack[i1].p = i;
/* 133 */     arrayOfStack[i1].r = j;
/* 134 */     i1++;
/*     */     while (true) { i1--; if (i1 < 0) break;
/* 136 */       i = arrayOfStack[i1].p;
/* 137 */       j = arrayOfStack[i1].r;
/*     */       int m;
/* 138 */       while (j > i + 10) {
/* 139 */         i2 = Math.abs(i2 * 1539415821 + 1);
/* 140 */         k = i + i2 % (j - i + 1);
/* 141 */         n = this.order[k];
/* 142 */         this.order[k] = this.order[i];
/* 143 */         this.order[i] = n;
/* 144 */         k = i - 1;
/* 145 */         m = j + 1;
/*     */         do {
/*     */           do
/* 148 */             k++;
/* 149 */           while (GT(this.leq, this.keys[this.order[k]], this.keys[n]));
/*     */           do
/* 151 */             m--;
/* 152 */           while (LT(this.leq, this.keys[this.order[m]], this.keys[n]));
/* 153 */           Swap(this.order, k, m);
/* 154 */         }while (k < m);
/* 155 */         Swap(this.order, k, m);
/* 156 */         if (k - i < j - m) {
/* 157 */           arrayOfStack[i1].p = (m + 1);
/* 158 */           arrayOfStack[i1].r = j;
/* 159 */           i1++;
/* 160 */           j = k - 1;
/*     */         } else {
/* 162 */           arrayOfStack[i1].p = i;
/* 163 */           arrayOfStack[i1].r = (k - 1);
/* 164 */           i1++;
/* 165 */           i = m + 1;
/*     */         }
/*     */       }
/*     */ 
/* 169 */       for (k = i + 1; k <= j; k++) {
/* 170 */         n = this.order[k];
/* 171 */         for (m = k; (m > i) && (LT(this.leq, this.keys[this.order[(m - 1)]], this.keys[n])); m--) {
/* 172 */           this.order[m] = this.order[(m - 1)];
/*     */         }
/* 174 */         this.order[m] = n;
/*     */       }
/*     */     }
/* 177 */     this.max = this.size;
/* 178 */     this.initialized = true;
/* 179 */     this.heap.pqInit();
/*     */ 
/* 189 */     return true;
/*     */   }
/*     */ 
/*     */   int pqInsert(Object paramObject)
/*     */   {
/* 197 */     if (this.initialized) {
/* 198 */       return this.heap.pqInsert(paramObject);
/*     */     }
/* 200 */     int i = this.size;
/* 201 */     if (++this.size >= this.max) {
/* 202 */       Object[] arrayOfObject1 = this.keys;
/*     */ 
/* 205 */       this.max <<= 1;
/*     */ 
/* 207 */       Object[] arrayOfObject2 = new Object[this.max];
/* 208 */       System.arraycopy(this.keys, 0, arrayOfObject2, 0, this.keys.length);
/* 209 */       this.keys = arrayOfObject2;
/* 210 */       if (this.keys == null) {
/* 211 */         this.keys = arrayOfObject1;
/* 212 */         return 2147483647;
/*     */       }
/*     */     }
/* 215 */     assert (i != 2147483647);
/* 216 */     this.keys[i] = paramObject;
/*     */ 
/* 219 */     return -(i + 1);
/*     */   }
/*     */ 
/*     */   Object pqExtractMin()
/*     */   {
/* 226 */     if (this.size == 0) {
/* 227 */       return this.heap.pqExtractMin();
/*     */     }
/* 229 */     Object localObject1 = this.keys[this.order[(this.size - 1)]];
/* 230 */     if (!this.heap.pqIsEmpty()) {
/* 231 */       Object localObject2 = this.heap.pqMinimum();
/* 232 */       if (LEQ(this.leq, localObject2, localObject1)) {
/* 233 */         return this.heap.pqExtractMin();
/*     */       }
/*     */     }
/*     */     do
/* 237 */       this.size -= 1;
/* 238 */     while ((this.size > 0) && (this.keys[this.order[(this.size - 1)]] == null));
/* 239 */     return localObject1;
/*     */   }
/*     */ 
/*     */   Object pqMinimum()
/*     */   {
/* 246 */     if (this.size == 0) {
/* 247 */       return this.heap.pqMinimum();
/*     */     }
/* 249 */     Object localObject1 = this.keys[this.order[(this.size - 1)]];
/* 250 */     if (!this.heap.pqIsEmpty()) {
/* 251 */       Object localObject2 = this.heap.pqMinimum();
/* 252 */       if (PriorityQHeap.LEQ(this.leq, localObject2, localObject1)) {
/* 253 */         return localObject2;
/*     */       }
/*     */     }
/* 256 */     return localObject1;
/*     */   }
/*     */ 
/*     */   boolean pqIsEmpty()
/*     */   {
/* 261 */     return (this.size == 0) && (this.heap.pqIsEmpty());
/*     */   }
/*     */ 
/*     */   void pqDelete(int paramInt)
/*     */   {
/* 266 */     if (paramInt >= 0) {
/* 267 */       this.heap.pqDelete(paramInt);
/* 268 */       return;
/*     */     }
/* 270 */     paramInt = -(paramInt + 1);
/* 271 */     assert ((paramInt < this.max) && (this.keys[paramInt] != null));
/*     */ 
/* 273 */     this.keys[paramInt] = null;
/* 274 */     while ((this.size > 0) && (this.keys[this.order[(this.size - 1)]] == null))
/* 275 */       this.size -= 1;
/*     */   }
/*     */ 
/*     */   private static class Stack
/*     */   {
/*     */     int p;
/*     */     int r;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.util.tess.impl.tess.PriorityQSort
 * JD-Core Version:    0.6.2
 */