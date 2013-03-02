/*    */ package com.sun.javafx.collections.transformation;
/*    */ 
/*    */ import java.util.Comparator;
/*    */ import java.util.List;
/*    */ 
/*    */ public abstract interface SortableList<E> extends List<E>
/*    */ {
/*    */   public abstract void sort();
/*    */ 
/*    */   public abstract void setMode(SortMode paramSortMode);
/*    */ 
/*    */   public abstract SortMode getMode();
/*    */ 
/*    */   public abstract void setComparator(Comparator<? super E> paramComparator);
/*    */ 
/*    */   public abstract Comparator<? super E> getComparator();
/*    */ 
/*    */   public static enum SortMode
/*    */   {
/* 53 */     BATCH, 
/*    */ 
/* 58 */     LIVE;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.collections.transformation.SortableList
 * JD-Core Version:    0.6.2
 */