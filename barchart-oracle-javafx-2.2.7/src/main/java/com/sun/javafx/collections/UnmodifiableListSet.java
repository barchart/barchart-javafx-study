/*    */ package com.sun.javafx.collections;
/*    */ 
/*    */ import java.util.AbstractSet;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ 
/*    */ public final class UnmodifiableListSet<E> extends AbstractSet<E>
/*    */ {
/*    */   private List<E> backingList;
/*    */ 
/*    */   public UnmodifiableListSet(List<E> paramList)
/*    */   {
/* 65 */     if (paramList == null) throw new NullPointerException();
/* 66 */     this.backingList = paramList;
/*    */   }
/*    */ 
/*    */   public Iterator<E> iterator()
/*    */   {
/* 77 */     final Iterator localIterator = this.backingList.iterator();
/* 78 */     return new Iterator() {
/*    */       public boolean hasNext() {
/* 80 */         return localIterator.hasNext();
/*    */       }
/*    */ 
/*    */       public E next() {
/* 84 */         return localIterator.next();
/*    */       }
/*    */ 
/*    */       public void remove() {
/* 88 */         throw new UnsupportedOperationException();
/*    */       }
/*    */     };
/*    */   }
/*    */ 
/*    */   public int size() {
/* 94 */     return this.backingList.size();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.collections.UnmodifiableListSet
 * JD-Core Version:    0.6.2
 */