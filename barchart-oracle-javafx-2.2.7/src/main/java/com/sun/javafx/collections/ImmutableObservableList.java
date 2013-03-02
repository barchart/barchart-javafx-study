/*     */ package com.sun.javafx.collections;
/*     */ 
/*     */ import java.util.AbstractList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.collections.ListChangeListener;
/*     */ import javafx.collections.ObservableList;
/*     */ 
/*     */ public class ImmutableObservableList<E> extends AbstractList<E>
/*     */   implements ObservableList<E>
/*     */ {
/*     */   private final E[] elements;
/*     */ 
/*     */   public ImmutableObservableList(E[] paramArrayOfE)
/*     */   {
/*  61 */     this.elements = ((paramArrayOfE == null) || (paramArrayOfE.length == 0) ? null : Arrays.copyOf(paramArrayOfE, paramArrayOfE.length));
/*     */   }
/*     */ 
/*     */   public void addListener(InvalidationListener paramInvalidationListener)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void removeListener(InvalidationListener paramInvalidationListener)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void addListener(ListChangeListener<? super E> paramListChangeListener)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void removeListener(ListChangeListener<? super E> paramListChangeListener)
/*     */   {
/*     */   }
/*     */ 
/*     */   public boolean addAll(E[] paramArrayOfE)
/*     */   {
/*  87 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   public boolean setAll(E[] paramArrayOfE)
/*     */   {
/*  92 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   public boolean setAll(Collection<? extends E> paramCollection)
/*     */   {
/*  97 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   public boolean removeAll(E[] paramArrayOfE)
/*     */   {
/* 102 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   public boolean retainAll(E[] paramArrayOfE)
/*     */   {
/* 107 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   public void remove(int paramInt1, int paramInt2)
/*     */   {
/* 112 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   public E get(int paramInt)
/*     */   {
/* 117 */     if ((paramInt < 0) || (paramInt >= size())) {
/* 118 */       throw new IndexOutOfBoundsException();
/*     */     }
/* 120 */     return this.elements[paramInt];
/*     */   }
/*     */ 
/*     */   public int size()
/*     */   {
/* 125 */     return this.elements == null ? 0 : this.elements.length;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.collections.ImmutableObservableList
 * JD-Core Version:    0.6.2
 */