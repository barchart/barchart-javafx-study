/*     */ package com.sun.javafx.collections;
/*     */ 
/*     */ import java.util.AbstractList;
/*     */ import java.util.List;
/*     */ import javafx.collections.ListChangeListener.Change;
/*     */ import javafx.collections.ObservableList;
/*     */ 
/*     */ public final class MappingChange<E, F> extends ListChangeListener.Change<F>
/*     */ {
/*     */   private final Map<E, F> map;
/*     */   private final ListChangeListener.Change<? extends E> original;
/*     */   private List<F> removed;
/*  58 */   public static final Map NOOP_MAP = new Map()
/*     */   {
/*     */     public Object map(Object paramAnonymousObject)
/*     */     {
/*  62 */       return paramAnonymousObject;
/*     */     }
/*  58 */   };
/*     */ 
/*     */   public MappingChange(ListChangeListener.Change<? extends E> paramChange, Map<E, F> paramMap, ObservableList<F> paramObservableList)
/*     */   {
/*  71 */     super(paramObservableList);
/*  72 */     this.original = paramChange;
/*  73 */     this.map = paramMap;
/*     */   }
/*     */ 
/*     */   public boolean next()
/*     */   {
/*  78 */     return this.original.next();
/*     */   }
/*     */ 
/*     */   public void reset()
/*     */   {
/*  83 */     this.original.reset();
/*     */   }
/*     */ 
/*     */   public int getFrom()
/*     */   {
/*  88 */     return this.original.getFrom();
/*     */   }
/*     */ 
/*     */   public int getTo()
/*     */   {
/*  93 */     return this.original.getTo();
/*     */   }
/*     */ 
/*     */   public List<F> getRemoved()
/*     */   {
/*  98 */     if (this.removed == null) {
/*  99 */       this.removed = new AbstractList()
/*     */       {
/*     */         public F get(int paramAnonymousInt)
/*     */         {
/* 103 */           return MappingChange.this.map.map(MappingChange.this.original.getRemoved().get(paramAnonymousInt));
/*     */         }
/*     */ 
/*     */         public int size()
/*     */         {
/* 108 */           return MappingChange.this.original.getRemovedSize();
/*     */         }
/*     */       };
/*     */     }
/* 112 */     return this.removed;
/*     */   }
/*     */ 
/*     */   protected int[] getPermutation()
/*     */   {
/* 117 */     return new int[0];
/*     */   }
/*     */ 
/*     */   public boolean wasPermutated()
/*     */   {
/* 122 */     return this.original.wasPermutated();
/*     */   }
/*     */ 
/*     */   public boolean wasUpdated()
/*     */   {
/* 127 */     return this.original.wasUpdated();
/*     */   }
/*     */ 
/*     */   public int getPermutation(int paramInt)
/*     */   {
/* 132 */     return this.original.getPermutation(paramInt);
/*     */   }
/*     */ 
/*     */   public static abstract interface Map<E, F>
/*     */   {
/*     */     public abstract F map(E paramE);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.collections.MappingChange
 * JD-Core Version:    0.6.2
 */