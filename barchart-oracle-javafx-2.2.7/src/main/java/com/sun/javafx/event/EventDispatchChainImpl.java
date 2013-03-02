/*     */ package com.sun.javafx.event;
/*     */ 
/*     */ import javafx.event.Event;
/*     */ import javafx.event.EventDispatchChain;
/*     */ import javafx.event.EventDispatcher;
/*     */ 
/*     */ public class EventDispatchChainImpl
/*     */   implements EventDispatchChain
/*     */ {
/*     */   private static final int CAPACITY_GROWTH_FACTOR = 8;
/*     */   private EventDispatcher[] dispatchers;
/*     */   private int[] nextLinks;
/*     */   private int reservedCount;
/*     */   private int activeCount;
/*     */   private int headIndex;
/*     */   private int tailIndex;
/*     */ 
/*     */   public void reset()
/*     */   {
/*  29 */     for (int i = 0; i < this.reservedCount; i++) {
/*  30 */       this.dispatchers[i] = null;
/*     */     }
/*     */ 
/*  33 */     this.reservedCount = 0;
/*  34 */     this.activeCount = 0;
/*  35 */     this.headIndex = 0;
/*  36 */     this.tailIndex = 0;
/*     */   }
/*     */ 
/*     */   public EventDispatchChain append(EventDispatcher paramEventDispatcher)
/*     */   {
/*  41 */     ensureCapacity(this.reservedCount + 1);
/*     */ 
/*  43 */     if (this.activeCount == 0) {
/*  44 */       insertFirst(paramEventDispatcher);
/*  45 */       return this;
/*     */     }
/*     */ 
/*  48 */     this.dispatchers[this.reservedCount] = paramEventDispatcher;
/*  49 */     this.nextLinks[this.tailIndex] = this.reservedCount;
/*  50 */     this.tailIndex = this.reservedCount;
/*     */ 
/*  52 */     this.activeCount += 1;
/*  53 */     this.reservedCount += 1;
/*     */ 
/*  55 */     return this;
/*     */   }
/*     */ 
/*     */   public EventDispatchChain prepend(EventDispatcher paramEventDispatcher)
/*     */   {
/*  60 */     ensureCapacity(this.reservedCount + 1);
/*     */ 
/*  62 */     if (this.activeCount == 0) {
/*  63 */       insertFirst(paramEventDispatcher);
/*  64 */       return this;
/*     */     }
/*     */ 
/*  67 */     this.dispatchers[this.reservedCount] = paramEventDispatcher;
/*  68 */     this.nextLinks[this.reservedCount] = this.headIndex;
/*  69 */     this.headIndex = this.reservedCount;
/*     */ 
/*  71 */     this.activeCount += 1;
/*  72 */     this.reservedCount += 1;
/*     */ 
/*  74 */     return this;
/*     */   }
/*     */ 
/*     */   public Event dispatchEvent(Event paramEvent)
/*     */   {
/*  79 */     if (this.activeCount == 0) {
/*  80 */       return paramEvent;
/*     */     }
/*     */ 
/*  84 */     int i = this.headIndex;
/*  85 */     int j = this.tailIndex;
/*  86 */     int k = this.activeCount;
/*  87 */     int m = this.reservedCount;
/*     */ 
/*  89 */     EventDispatcher localEventDispatcher = this.dispatchers[this.headIndex];
/*  90 */     this.headIndex = this.nextLinks[this.headIndex];
/*  91 */     this.activeCount -= 1;
/*  92 */     Event localEvent = localEventDispatcher.dispatchEvent(paramEvent, this);
/*     */ 
/*  96 */     this.headIndex = i;
/*  97 */     this.tailIndex = j;
/*  98 */     this.activeCount = k;
/*  99 */     this.reservedCount = m;
/*     */ 
/* 101 */     return localEvent;
/*     */   }
/*     */ 
/*     */   private void insertFirst(EventDispatcher paramEventDispatcher) {
/* 105 */     this.dispatchers[this.reservedCount] = paramEventDispatcher;
/* 106 */     this.headIndex = this.reservedCount;
/* 107 */     this.tailIndex = this.reservedCount;
/*     */ 
/* 109 */     this.activeCount = 1;
/* 110 */     this.reservedCount += 1;
/*     */   }
/*     */ 
/*     */   private void ensureCapacity(int paramInt) {
/* 114 */     int i = paramInt + 8 - 1 & 0xFFFFFFF8;
/*     */ 
/* 116 */     if (i == 0) {
/* 117 */       return;
/*     */     }
/*     */ 
/* 120 */     if ((this.dispatchers == null) || (this.dispatchers.length < i)) {
/* 121 */       EventDispatcher[] arrayOfEventDispatcher = new EventDispatcher[i];
/*     */ 
/* 123 */       int[] arrayOfInt = new int[i];
/*     */ 
/* 125 */       if (this.reservedCount > 0) {
/* 126 */         System.arraycopy(this.dispatchers, 0, arrayOfEventDispatcher, 0, this.reservedCount);
/*     */ 
/* 128 */         System.arraycopy(this.nextLinks, 0, arrayOfInt, 0, this.reservedCount);
/*     */       }
/*     */ 
/* 131 */       this.dispatchers = arrayOfEventDispatcher;
/* 132 */       this.nextLinks = arrayOfInt;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.event.EventDispatchChainImpl
 * JD-Core Version:    0.6.2
 */