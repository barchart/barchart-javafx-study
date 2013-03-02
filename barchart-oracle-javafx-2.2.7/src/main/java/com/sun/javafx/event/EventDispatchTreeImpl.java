/*     */ package com.sun.javafx.event;
/*     */ 
/*     */ import javafx.event.Event;
/*     */ import javafx.event.EventDispatcher;
/*     */ 
/*     */ public final class EventDispatchTreeImpl
/*     */   implements EventDispatchTree
/*     */ {
/*     */   private static final int CAPACITY_GROWTH_FACTOR = 8;
/*     */   private static final int NULL_INDEX = -1;
/*     */   private EventDispatcher[] dispatchers;
/*     */   private int[] nextChildren;
/*     */   private int[] nextSiblings;
/*     */   private int reservedCount;
/*     */   private int rootIndex;
/*     */   private int tailFirstIndex;
/*     */   private int tailLastIndex;
/*     */   private boolean expandTailFirstPath;
/*     */ 
/*     */   public EventDispatchTreeImpl()
/*     */   {
/*  27 */     this.rootIndex = -1;
/*  28 */     this.tailFirstIndex = -1;
/*  29 */     this.tailLastIndex = -1;
/*     */   }
/*     */ 
/*     */   public void reset()
/*     */   {
/*  34 */     for (int i = 0; i < this.reservedCount; i++) {
/*  35 */       this.dispatchers[i] = null;
/*     */     }
/*     */ 
/*  38 */     this.reservedCount = 0;
/*  39 */     this.rootIndex = -1;
/*  40 */     this.tailFirstIndex = -1;
/*  41 */     this.tailLastIndex = -1;
/*     */   }
/*     */ 
/*     */   public EventDispatchTree createTree()
/*     */   {
/*  46 */     return new EventDispatchTreeImpl();
/*     */   }
/*     */ 
/*     */   public EventDispatchTree mergeTree(EventDispatchTree paramEventDispatchTree)
/*     */   {
/*  53 */     if (this.tailFirstIndex != -1) {
/*  54 */       if (this.rootIndex != -1) {
/*  55 */         this.expandTailFirstPath = true;
/*  56 */         expandTail(this.rootIndex);
/*     */       } else {
/*  58 */         this.rootIndex = this.tailFirstIndex;
/*     */       }
/*     */ 
/*  61 */       this.tailFirstIndex = -1;
/*  62 */       this.tailLastIndex = -1;
/*     */     }
/*     */ 
/*  65 */     EventDispatchTreeImpl localEventDispatchTreeImpl = (EventDispatchTreeImpl)paramEventDispatchTree;
/*  66 */     int i = localEventDispatchTreeImpl.rootIndex != -1 ? localEventDispatchTreeImpl.rootIndex : localEventDispatchTreeImpl.tailFirstIndex;
/*     */ 
/*  70 */     if (this.rootIndex == -1)
/*  71 */       this.rootIndex = copyTreeLevel(localEventDispatchTreeImpl, i);
/*     */     else {
/*  73 */       mergeTreeLevel(localEventDispatchTreeImpl, this.rootIndex, i);
/*     */     }
/*     */ 
/*  76 */     return this;
/*     */   }
/*     */ 
/*     */   public EventDispatchTree append(EventDispatcher paramEventDispatcher)
/*     */   {
/*  81 */     ensureCapacity(this.reservedCount + 1);
/*     */ 
/*  83 */     this.dispatchers[this.reservedCount] = paramEventDispatcher;
/*  84 */     this.nextSiblings[this.reservedCount] = -1;
/*  85 */     this.nextChildren[this.reservedCount] = -1;
/*  86 */     if (this.tailFirstIndex == -1)
/*  87 */       this.tailFirstIndex = this.reservedCount;
/*     */     else {
/*  89 */       this.nextChildren[this.tailLastIndex] = this.reservedCount;
/*     */     }
/*     */ 
/*  92 */     this.tailLastIndex = this.reservedCount;
/*  93 */     this.reservedCount += 1;
/*     */ 
/*  95 */     return this;
/*     */   }
/*     */ 
/*     */   public EventDispatchTree prepend(EventDispatcher paramEventDispatcher)
/*     */   {
/* 100 */     ensureCapacity(this.reservedCount + 1);
/*     */ 
/* 102 */     this.dispatchers[this.reservedCount] = paramEventDispatcher;
/* 103 */     this.nextSiblings[this.reservedCount] = -1;
/* 104 */     this.nextChildren[this.reservedCount] = this.rootIndex;
/*     */ 
/* 106 */     this.rootIndex = this.reservedCount;
/* 107 */     this.reservedCount += 1;
/*     */ 
/* 109 */     return this;
/*     */   }
/*     */ 
/*     */   public Event dispatchEvent(Event paramEvent)
/*     */   {
/* 114 */     if (this.rootIndex == -1) {
/* 115 */       if (this.tailFirstIndex == -1) {
/* 116 */         return paramEvent;
/*     */       }
/*     */ 
/* 119 */       this.rootIndex = this.tailFirstIndex;
/* 120 */       this.tailFirstIndex = -1;
/* 121 */       this.tailLastIndex = -1;
/*     */     }
/*     */ 
/* 125 */     int i = this.reservedCount;
/* 126 */     int j = this.rootIndex;
/* 127 */     int k = this.tailFirstIndex;
/* 128 */     int m = this.tailLastIndex;
/*     */ 
/* 130 */     Object localObject = null;
/* 131 */     int n = this.rootIndex;
/*     */     do {
/* 133 */       this.rootIndex = this.nextChildren[n];
/* 134 */       Event localEvent = this.dispatchers[n].dispatchEvent(paramEvent, this);
/*     */ 
/* 136 */       if (localEvent != null) {
/* 137 */         localObject = localObject != null ? paramEvent : localEvent;
/*     */       }
/*     */ 
/* 141 */       n = this.nextSiblings[n];
/* 142 */     }while (n != -1);
/*     */ 
/* 145 */     this.reservedCount = i;
/* 146 */     this.rootIndex = j;
/* 147 */     this.tailFirstIndex = k;
/* 148 */     this.tailLastIndex = m;
/*     */ 
/* 150 */     return localObject;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 155 */     int i = this.rootIndex != -1 ? this.rootIndex : this.tailFirstIndex;
/* 156 */     if (i == -1) {
/* 157 */       return "()";
/*     */     }
/*     */ 
/* 160 */     StringBuilder localStringBuilder = new StringBuilder();
/* 161 */     appendTreeLevel(localStringBuilder, i);
/*     */ 
/* 163 */     return localStringBuilder.toString();
/*     */   }
/*     */ 
/*     */   private void ensureCapacity(int paramInt) {
/* 167 */     int i = paramInt + 8 - 1 & 0xFFFFFFF8;
/*     */ 
/* 169 */     if (i == 0) {
/* 170 */       return;
/*     */     }
/*     */ 
/* 173 */     if ((this.dispatchers == null) || (this.dispatchers.length < i)) {
/* 174 */       EventDispatcher[] arrayOfEventDispatcher = new EventDispatcher[i];
/*     */ 
/* 176 */       int[] arrayOfInt1 = new int[i];
/* 177 */       int[] arrayOfInt2 = new int[i];
/*     */ 
/* 179 */       if (this.reservedCount > 0) {
/* 180 */         System.arraycopy(this.dispatchers, 0, arrayOfEventDispatcher, 0, this.reservedCount);
/*     */ 
/* 182 */         System.arraycopy(this.nextChildren, 0, arrayOfInt1, 0, this.reservedCount);
/*     */ 
/* 184 */         System.arraycopy(this.nextSiblings, 0, arrayOfInt2, 0, this.reservedCount);
/*     */       }
/*     */ 
/* 188 */       this.dispatchers = arrayOfEventDispatcher;
/* 189 */       this.nextChildren = arrayOfInt1;
/* 190 */       this.nextSiblings = arrayOfInt2;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void expandTail(int paramInt) {
/* 195 */     int i = paramInt;
/* 196 */     while (i != -1) {
/* 197 */       if (this.nextChildren[i] != -1) {
/* 198 */         expandTail(this.nextChildren[i]);
/*     */       }
/* 200 */       else if (this.expandTailFirstPath) {
/* 201 */         this.nextChildren[i] = this.tailFirstIndex;
/* 202 */         this.expandTailFirstPath = false;
/*     */       } else {
/* 204 */         int j = copyTreeLevel(this, this.tailFirstIndex);
/*     */ 
/* 206 */         this.nextChildren[i] = j;
/*     */       }
/*     */ 
/* 210 */       i = this.nextSiblings[i];
/*     */     }
/*     */   }
/*     */ 
/*     */   private void mergeTreeLevel(EventDispatchTreeImpl paramEventDispatchTreeImpl, int paramInt1, int paramInt2)
/*     */   {
/* 217 */     int i = paramInt2;
/* 218 */     while (i != -1) {
/* 219 */       EventDispatcher localEventDispatcher = paramEventDispatchTreeImpl.dispatchers[i];
/* 220 */       int j = paramInt1;
/* 221 */       int k = paramInt1;
/*     */ 
/* 224 */       while ((j != -1) && (localEventDispatcher != this.dispatchers[j])) {
/* 225 */         k = j;
/* 226 */         j = this.nextSiblings[j];
/*     */       }
/*     */       int m;
/* 229 */       if (j == -1) {
/* 230 */         m = copySubtree(paramEventDispatchTreeImpl, i);
/* 231 */         this.nextSiblings[k] = m;
/* 232 */         this.nextSiblings[m] = -1;
/*     */       } else {
/* 234 */         m = this.nextChildren[j];
/* 235 */         int n = getChildIndex(paramEventDispatchTreeImpl, i);
/* 236 */         if (m != -1) {
/* 237 */           mergeTreeLevel(paramEventDispatchTreeImpl, m, n);
/*     */         }
/*     */         else
/*     */         {
/* 241 */           m = copyTreeLevel(paramEventDispatchTreeImpl, n);
/*     */ 
/* 243 */           this.nextChildren[j] = m;
/*     */         }
/*     */       }
/*     */ 
/* 247 */       i = paramEventDispatchTreeImpl.nextSiblings[i];
/*     */     }
/*     */   }
/*     */ 
/*     */   private int copyTreeLevel(EventDispatchTreeImpl paramEventDispatchTreeImpl, int paramInt)
/*     */   {
/* 253 */     if (paramInt == -1) {
/* 254 */       return -1;
/*     */     }
/*     */ 
/* 257 */     int i = paramInt;
/* 258 */     int j = copySubtree(paramEventDispatchTreeImpl, i);
/* 259 */     int k = j;
/*     */ 
/* 261 */     i = paramEventDispatchTreeImpl.nextSiblings[i];
/* 262 */     while (i != -1) {
/* 263 */       int m = copySubtree(paramEventDispatchTreeImpl, i);
/* 264 */       this.nextSiblings[k] = m;
/*     */ 
/* 266 */       k = m;
/* 267 */       i = paramEventDispatchTreeImpl.nextSiblings[i];
/*     */     }
/*     */ 
/* 270 */     this.nextSiblings[k] = -1;
/* 271 */     return j;
/*     */   }
/*     */ 
/*     */   private int copySubtree(EventDispatchTreeImpl paramEventDispatchTreeImpl, int paramInt)
/*     */   {
/* 276 */     ensureCapacity(this.reservedCount + 1);
/* 277 */     int i = this.reservedCount++;
/*     */ 
/* 279 */     int j = copyTreeLevel(paramEventDispatchTreeImpl, getChildIndex(paramEventDispatchTreeImpl, paramInt));
/*     */ 
/* 281 */     this.dispatchers[i] = paramEventDispatchTreeImpl.dispatchers[paramInt];
/* 282 */     this.nextChildren[i] = j;
/*     */ 
/* 284 */     return i;
/*     */   }
/*     */ 
/*     */   private void appendTreeLevel(StringBuilder paramStringBuilder, int paramInt)
/*     */   {
/* 289 */     paramStringBuilder.append('(');
/*     */ 
/* 291 */     int i = paramInt;
/* 292 */     appendSubtree(paramStringBuilder, i);
/*     */ 
/* 294 */     i = this.nextSiblings[i];
/* 295 */     while (i != -1) {
/* 296 */       paramStringBuilder.append(",");
/* 297 */       appendSubtree(paramStringBuilder, i);
/* 298 */       i = this.nextSiblings[i];
/*     */     }
/*     */ 
/* 301 */     paramStringBuilder.append(')');
/*     */   }
/*     */ 
/*     */   private void appendSubtree(StringBuilder paramStringBuilder, int paramInt)
/*     */   {
/* 306 */     paramStringBuilder.append(this.dispatchers[paramInt]);
/*     */ 
/* 308 */     int i = getChildIndex(this, paramInt);
/* 309 */     if (i != -1) {
/* 310 */       paramStringBuilder.append("->");
/* 311 */       appendTreeLevel(paramStringBuilder, i);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static int getChildIndex(EventDispatchTreeImpl paramEventDispatchTreeImpl, int paramInt)
/*     */   {
/* 317 */     int i = paramEventDispatchTreeImpl.nextChildren[paramInt];
/* 318 */     if ((i == -1) && (paramInt != paramEventDispatchTreeImpl.tailLastIndex))
/*     */     {
/* 320 */       i = paramEventDispatchTreeImpl.tailFirstIndex;
/*     */     }
/*     */ 
/* 323 */     return i;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.event.EventDispatchTreeImpl
 * JD-Core Version:    0.6.2
 */