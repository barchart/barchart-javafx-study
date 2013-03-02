/*     */ package com.sun.javafx.collections;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javafx.collections.ListChangeListener.Change;
/*     */ import javafx.collections.ObservableList;
/*     */ 
/*     */ public abstract class NonIterableChange<E> extends ListChangeListener.Change<E>
/*     */ {
/*     */   private final int from;
/*     */   private final int to;
/*  57 */   private boolean invalid = true;
/*     */ 
/*  77 */   private static final int[] EMPTY_PERM = new int[0];
/*     */ 
/*     */   protected NonIterableChange(int paramInt1, int paramInt2, ObservableList<E> paramObservableList)
/*     */   {
/*  60 */     super(paramObservableList);
/*  61 */     this.from = paramInt1;
/*  62 */     this.to = paramInt2;
/*     */   }
/*     */ 
/*     */   public int getFrom()
/*     */   {
/*  67 */     checkState();
/*  68 */     return this.from;
/*     */   }
/*     */ 
/*     */   public int getTo()
/*     */   {
/*  73 */     checkState();
/*  74 */     return this.to;
/*     */   }
/*     */ 
/*     */   protected int[] getPermutation()
/*     */   {
/*  81 */     checkState();
/*  82 */     return EMPTY_PERM;
/*     */   }
/*     */ 
/*     */   public boolean next()
/*     */   {
/*  87 */     if (this.invalid) {
/*  88 */       this.invalid = false;
/*  89 */       return true;
/*     */     }
/*  91 */     return false;
/*     */   }
/*     */ 
/*     */   public void reset()
/*     */   {
/*  96 */     this.invalid = true;
/*     */   }
/*     */ 
/*     */   public void checkState() {
/* 100 */     if (this.invalid)
/* 101 */       throw new IllegalStateException();
/*     */   }
/*     */ 
/*     */   public static class SimpleUpdateChange<E> extends NonIterableChange<E>
/*     */   {
/*     */     public SimpleUpdateChange(int paramInt, ObservableList<E> paramObservableList)
/*     */     {
/* 190 */       this(paramInt, paramInt + 1, paramObservableList);
/*     */     }
/*     */ 
/*     */     public SimpleUpdateChange(int paramInt1, int paramInt2, ObservableList<E> paramObservableList) {
/* 194 */       super(paramInt2, paramObservableList);
/*     */     }
/*     */ 
/*     */     public List<E> getRemoved()
/*     */     {
/* 199 */       return Collections.emptyList();
/*     */     }
/*     */ 
/*     */     public boolean wasUpdated()
/*     */     {
/* 204 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class SimplePermutationChange<E> extends NonIterableChange<E>
/*     */   {
/*     */     private final int[] permutation;
/*     */ 
/*     */     public SimplePermutationChange(int paramInt1, int paramInt2, int[] paramArrayOfInt, ObservableList<E> paramObservableList)
/*     */     {
/* 169 */       super(paramInt2, paramObservableList);
/* 170 */       this.permutation = paramArrayOfInt;
/*     */     }
/*     */ 
/*     */     public List<E> getRemoved()
/*     */     {
/* 176 */       checkState();
/* 177 */       return Collections.emptyList();
/*     */     }
/*     */ 
/*     */     protected int[] getPermutation()
/*     */     {
/* 182 */       checkState();
/* 183 */       return this.permutation;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class SimpleAddChange<E> extends NonIterableChange<E>
/*     */   {
/*     */     public SimpleAddChange(int paramInt1, int paramInt2, ObservableList<E> paramObservableList)
/*     */     {
/* 147 */       super(paramInt2, paramObservableList);
/*     */     }
/*     */ 
/*     */     public boolean wasRemoved()
/*     */     {
/* 152 */       checkState();
/* 153 */       return false;
/*     */     }
/*     */ 
/*     */     public List<E> getRemoved()
/*     */     {
/* 158 */       checkState();
/* 159 */       return Collections.emptyList();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class SimpleRemovedChange<E> extends NonIterableChange<E>
/*     */   {
/*     */     private final List<E> removed;
/*     */ 
/*     */     public SimpleRemovedChange(int paramInt1, int paramInt2, E paramE, ObservableList<E> paramObservableList)
/*     */     {
/* 126 */       super(paramInt2, paramObservableList);
/* 127 */       this.removed = Collections.singletonList(paramE);
/*     */     }
/*     */ 
/*     */     public boolean wasRemoved()
/*     */     {
/* 132 */       checkState();
/* 133 */       return true;
/*     */     }
/*     */ 
/*     */     public List<E> getRemoved()
/*     */     {
/* 138 */       checkState();
/* 139 */       return this.removed;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class GenericAddRemoveChange<E> extends NonIterableChange<E>
/*     */   {
/*     */     private final List<E> removed;
/*     */ 
/*     */     public GenericAddRemoveChange(int paramInt1, int paramInt2, List<E> paramList, ObservableList<E> paramObservableList)
/*     */     {
/* 110 */       super(paramInt2, paramObservableList);
/* 111 */       this.removed = paramList;
/*     */     }
/*     */ 
/*     */     public List<E> getRemoved()
/*     */     {
/* 116 */       checkState();
/* 117 */       return this.removed;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.collections.NonIterableChange
 * JD-Core Version:    0.6.2
 */