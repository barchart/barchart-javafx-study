/*     */ package com.sun.javafx;
/*     */ 
/*     */ import java.lang.ref.ReferenceQueue;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ public class WeakReferenceQueue<E>
/*     */ {
/*     */   private final ReferenceQueue garbage;
/*     */   private Object strongRef;
/*     */   private ListEntry head;
/*     */   int size;
/*     */ 
/*     */   public WeakReferenceQueue()
/*     */   {
/*  47 */     this.garbage = new ReferenceQueue();
/*     */ 
/*  52 */     this.strongRef = new Object();
/*  53 */     this.head = new ListEntry(this.strongRef, this.garbage);
/*     */ 
/*  58 */     this.size = 0;
/*     */   }
/*     */ 
/*     */   public void add(E paramE) {
/*  62 */     cleanup();
/*  63 */     this.size += 1;
/*  64 */     new ListEntry(paramE, this.garbage).insert(this.head.prev);
/*     */   }
/*     */ 
/*     */   public void remove(E paramE) {
/*  68 */     cleanup();
/*     */ 
/*  70 */     ListEntry localListEntry = this.head.next;
/*  71 */     while (localListEntry != this.head) {
/*  72 */       Object localObject = localListEntry.get();
/*  73 */       if (localObject == paramE) {
/*  74 */         this.size -= 1;
/*  75 */         localListEntry.remove();
/*  76 */         return;
/*     */       }
/*  78 */       localListEntry = localListEntry.next;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void cleanup()
/*     */   {
/*     */     ListEntry localListEntry;
/*  84 */     while ((localListEntry = (ListEntry)this.garbage.poll()) != null) {
/*  85 */       this.size -= 1;
/*  86 */       localListEntry.remove();
/*     */     }
/*     */   }
/*     */ 
/*     */   public Iterator<? super E> iterator() {
/*  91 */     return new Iterator() {
/*  92 */       private WeakReferenceQueue.ListEntry index = WeakReferenceQueue.this.head;
/*  93 */       private Object next = null;
/*     */ 
/*     */       public boolean hasNext() {
/*  96 */         this.next = null;
/*  97 */         while (this.next == null) {
/*  98 */           WeakReferenceQueue.ListEntry localListEntry = this.index.prev;
/*  99 */           if (localListEntry == WeakReferenceQueue.this.head) {
/*     */             break;
/*     */           }
/* 102 */           this.next = localListEntry.get();
/* 103 */           if (this.next == null) {
/* 104 */             WeakReferenceQueue.this.size -= 1;
/* 105 */             localListEntry.remove();
/*     */           }
/*     */         }
/*     */ 
/* 109 */         return this.next != null;
/*     */       }
/*     */ 
/*     */       public Object next() {
/* 113 */         hasNext();
/*     */ 
/* 115 */         this.index = this.index.prev;
/* 116 */         return this.next;
/*     */       }
/*     */ 
/*     */       public void remove() {
/* 120 */         if (this.index != WeakReferenceQueue.this.head) {
/* 121 */           WeakReferenceQueue.ListEntry localListEntry = this.index.next;
/* 122 */           WeakReferenceQueue.this.size -= 1;
/* 123 */           this.index.remove();
/* 124 */           this.index = localListEntry;
/*     */         }
/*     */       } } ;
/*     */   }
/*     */ 
/*     */   private static class ListEntry extends WeakReference {
/*     */     ListEntry prev;
/*     */     ListEntry next;
/*     */ 
/* 134 */     public ListEntry(Object paramObject, ReferenceQueue paramReferenceQueue) { super(paramReferenceQueue);
/* 135 */       this.prev = this;
/* 136 */       this.next = this; }
/*     */ 
/*     */     public void insert(ListEntry paramListEntry)
/*     */     {
/* 140 */       this.prev = paramListEntry;
/* 141 */       this.next = paramListEntry.next;
/* 142 */       paramListEntry.next = this;
/* 143 */       this.next.prev = this;
/*     */     }
/*     */ 
/*     */     public void remove() {
/* 147 */       this.prev.next = this.next;
/* 148 */       this.next.prev = this.prev;
/* 149 */       this.next = this;
/* 150 */       this.prev = this;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.WeakReferenceQueue
 * JD-Core Version:    0.6.2
 */