/*     */ package com.sun.javafx;
/*     */ 
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class WeakReferenceMap
/*     */   implements Map
/*     */ {
/*     */   ArrayList<Entry> queue;
/*     */ 
/*     */   public WeakReferenceMap()
/*     */   {
/*  45 */     this.queue = new ArrayList();
/*     */   }
/*     */ 
/*     */   public Object put(Object paramObject1, Object paramObject2)
/*     */   {
/*  54 */     remove(paramObject1);
/*  55 */     Entry localEntry = new Entry(null);
/*  56 */     localEntry.weakKey = new WeakReference(paramObject1);
/*  57 */     localEntry.value = paramObject2;
/*  58 */     this.queue.add(localEntry);
/*  59 */     return paramObject2;
/*     */   }
/*     */ 
/*     */   public int size() {
/*  63 */     cleanup();
/*  64 */     return this.queue.size();
/*     */   }
/*     */ 
/*     */   public boolean isEmpty() {
/*  68 */     cleanup();
/*  69 */     return this.queue.isEmpty();
/*     */   }
/*     */ 
/*     */   public Object remove(Object paramObject) {
/*  73 */     for (int i = this.queue.size() - 1; i >= 0; i--) {
/*  74 */       Entry localEntry = (Entry)this.queue.get(i);
/*  75 */       Object localObject = localEntry.weakKey.get();
/*  76 */       if ((localObject == null) || (localObject == paramObject)) this.queue.remove(i);
/*     */     }
/*  78 */     return paramObject;
/*     */   }
/*     */ 
/*     */   void cleanup() {
/*  82 */     remove(null);
/*     */   }
/*     */ 
/*     */   public Object get(Object paramObject) {
/*  86 */     for (int i = this.queue.size() - 1; i >= 0; i--) {
/*  87 */       Entry localEntry = (Entry)this.queue.get(i);
/*  88 */       Object localObject = localEntry.weakKey.get();
/*  89 */       if (localObject == null) {
/*  90 */         this.queue.remove(i);
/*     */       }
/*  92 */       else if (localObject.equals(paramObject)) {
/*  93 */         return localEntry.value;
/*     */       }
/*     */     }
/*     */ 
/*  97 */     return null;
/*     */   }
/*     */ 
/*     */   public void clear() {
/* 101 */     this.queue.clear();
/*     */   }
/*     */ 
/*     */   public boolean containsKey(Object paramObject) {
/* 105 */     throw new UnsupportedOperationException("Not implemented");
/*     */   }
/*     */ 
/*     */   public boolean containsValue(Object paramObject) {
/* 109 */     throw new UnsupportedOperationException("Not implemented");
/*     */   }
/*     */ 
/*     */   public Set keySet() {
/* 113 */     throw new UnsupportedOperationException("Not implemented");
/*     */   }
/*     */ 
/*     */   public Collection values() {
/* 117 */     throw new UnsupportedOperationException("Not implemented");
/*     */   }
/*     */ 
/*     */   public Set entrySet() {
/* 121 */     throw new UnsupportedOperationException("Not implemented");
/*     */   }
/*     */ 
/*     */   public void putAll(Map paramMap) {
/* 125 */     throw new UnsupportedOperationException("Not implemented");
/*     */   }
/*     */ 
/*     */   private static final class Entry
/*     */   {
/*     */     WeakReference weakKey;
/*     */     Object value;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.WeakReferenceMap
 * JD-Core Version:    0.6.2
 */