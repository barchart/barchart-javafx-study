/*     */ package com.sun.javafx.collections;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javafx.collections.ListChangeListener.Change;
/*     */ import javafx.collections.ObservableList;
/*     */ 
/*     */ public final class IterableChangeBuilder<E>
/*     */ {
/*     */   private static final int DEFAULT_CHANGE_SIZE = 3;
/*  58 */   private static final int[] EMPTY_PERM = new int[0];
/*     */   private final ObservableList<E> list;
/*     */   private SubChange<E>[] changes;
/*     */   private int size;
/*     */ 
/*     */   private void addSubChange(int paramInt1, int paramInt2, int paramInt3, ArrayList<E> paramArrayList, int[] paramArrayOfInt, boolean paramBoolean)
/*     */   {
/*  65 */     ensureSizePlusOne();
/*  66 */     this.changes[(this.size++)] = new SubChange(paramInt1, paramInt2, paramInt3, paramArrayList, paramArrayOfInt, paramBoolean);
/*     */   }
/*     */ 
/*     */   private void ensureSizePlusOne() {
/*  70 */     if (this.changes.length == this.size) {
/*  71 */       SubChange[] arrayOfSubChange = new SubChange[this.size * 3 / 2 + 1];
/*  72 */       System.arraycopy(this.changes, 0, arrayOfSubChange, 0, this.size);
/*  73 */       this.changes = arrayOfSubChange;
/*     */     }
/*     */   }
/*     */ 
/*     */   public IterableChangeBuilder(ObservableList<E> paramObservableList)
/*     */   {
/* 100 */     this.list = paramObservableList;
/* 101 */     reset();
/*     */   }
/*     */ 
/*     */   public void nextRemove(int paramInt, E paramE) {
/* 105 */     SubChange localSubChange = this.size == 0 ? null : this.changes[(this.size - 1)];
/* 106 */     if ((localSubChange != null) && ((localSubChange.type & 0x2) != 0) && (localSubChange.from == paramInt)) {
/* 107 */       localSubChange.removed.add(paramE);
/*     */     } else {
/* 109 */       ArrayList localArrayList = new ArrayList();
/* 110 */       localArrayList.add(paramE);
/* 111 */       addSubChange(2, paramInt, paramInt, localArrayList, null, false);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void nextRemove(int paramInt, List<E> paramList) {
/* 116 */     SubChange localSubChange = this.size == 0 ? null : this.changes[(this.size - 1)];
/* 117 */     if ((localSubChange != null) && ((localSubChange.type & 0x2) != 0) && (localSubChange.from == paramInt))
/* 118 */       localSubChange.removed.addAll(paramList);
/*     */     else
/* 120 */       addSubChange(2, paramInt, paramInt, new ArrayList(paramList), null, false);
/*     */   }
/*     */ 
/*     */   public void nextAdd(int paramInt1, int paramInt2)
/*     */   {
/* 125 */     SubChange localSubChange = this.size == 0 ? null : this.changes[(this.size - 1)];
/* 126 */     if ((localSubChange != null) && ((localSubChange.type & 0x1) != 0) && (localSubChange.to == paramInt1)) {
/* 127 */       localSubChange.to = paramInt2;
/* 128 */     } else if ((localSubChange != null) && ((localSubChange.type & 0x2) != 0) && (localSubChange.from == paramInt1)) {
/* 129 */       localSubChange.type |= 1;
/* 130 */       localSubChange.to = paramInt2;
/*     */     } else {
/* 132 */       addSubChange(1, paramInt1, paramInt2, null, null, false);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void nextPermutation(int paramInt1, int paramInt2, int[] paramArrayOfInt) {
/* 137 */     addSubChange(4, paramInt1, paramInt2, null, paramArrayOfInt, false);
/*     */   }
/*     */ 
/*     */   public void nextReplace(int paramInt1, int paramInt2, ArrayList<E> paramArrayList) {
/* 141 */     addSubChange(3, paramInt1, paramInt2, paramArrayList, null, false);
/*     */   }
/*     */ 
/*     */   public void nextSet(int paramInt, E paramE) {
/* 145 */     addSubChange(3, paramInt, paramInt + 1, new ArrayList(Arrays.asList(new Object[] { paramE })), null, false);
/*     */   }
/*     */ 
/*     */   public void nextUpdate(int paramInt) {
/* 149 */     SubChange localSubChange = this.size == 0 ? null : this.changes[(this.size - 1)];
/* 150 */     if ((localSubChange != null) && ((localSubChange.type & 0x8) != 0) && (localSubChange.to == paramInt))
/* 151 */       localSubChange.to = (paramInt + 1);
/*     */     else
/* 153 */       addSubChange(8, paramInt, paramInt + 1, null, null, true);
/*     */   }
/*     */ 
/*     */   public ListChangeListener.Change<E> build()
/*     */   {
/* 158 */     return new IterableChange(this.changes, this.size, this.list, null);
/*     */   }
/*     */ 
/*     */   public ListChangeListener.Change<E> buildAndReset() {
/* 162 */     ListChangeListener.Change localChange = build();
/* 163 */     reset();
/* 164 */     return localChange;
/*     */   }
/*     */ 
/*     */   public boolean isEmpty() {
/* 168 */     return this.size == 0;
/*     */   }
/*     */ 
/*     */   public void reset() {
/* 172 */     this.changes = new SubChange[3];
/* 173 */     this.size = 0;
/*     */   }
/*     */   private class IterableChange<E> extends ListChangeListener.Change<E> {
/*     */     private IterableChangeBuilder.SubChange[] changes;
/* 179 */     private int cursor = -1;
/*     */     private int size;
/*     */ 
/*     */     private IterableChange(int paramObservableList, ObservableList<E> arg3) {
/* 183 */       super();
/* 184 */       this.changes = paramObservableList;
/*     */       int i;
/* 185 */       this.size = i;
/*     */     }
/*     */ 
/*     */     public boolean next()
/*     */     {
/* 190 */       if (this.cursor + 1 < this.size) {
/* 191 */         this.cursor += 1;
/* 192 */         return true;
/*     */       }
/* 194 */       return false;
/*     */     }
/*     */ 
/*     */     public void reset()
/*     */     {
/* 199 */       this.cursor = -1;
/*     */     }
/*     */ 
/*     */     public int getFrom()
/*     */     {
/* 204 */       checkState();
/* 205 */       return this.changes[this.cursor].from;
/*     */     }
/*     */ 
/*     */     public int getTo()
/*     */     {
/* 210 */       checkState();
/* 211 */       return this.changes[this.cursor].to;
/*     */     }
/*     */ 
/*     */     public List<E> getRemoved()
/*     */     {
/* 216 */       checkState();
/* 217 */       return this.changes[this.cursor].removed == null ? Collections.emptyList() : Collections.unmodifiableList(this.changes[this.cursor].removed);
/*     */     }
/*     */ 
/*     */     protected int[] getPermutation()
/*     */     {
/* 222 */       checkState();
/* 223 */       return this.changes[this.cursor].perm == null ? IterableChangeBuilder.EMPTY_PERM : this.changes[this.cursor].perm;
/*     */     }
/*     */ 
/*     */     public boolean wasUpdated()
/*     */     {
/* 228 */       checkState();
/* 229 */       return this.changes[this.cursor].updated;
/*     */     }
/*     */ 
/*     */     private void checkState() {
/* 233 */       if (this.cursor == -1)
/* 234 */         throw new IllegalStateException();
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class SubChange<E>
/*     */   {
/*     */     private static final int TYPE_ADD = 1;
/*     */     private static final int TYPE_REMOVE = 2;
/*     */     private static final int TYPE_PERM = 4;
/*     */     private static final int TYPE_UPDATE = 8;
/*     */     int type;
/*     */     int from;
/*     */     int to;
/*     */     ArrayList<E> removed;
/*     */     int[] perm;
/*     */     boolean updated;
/*     */ 
/*     */     public SubChange(int paramInt1, int paramInt2, int paramInt3, ArrayList<E> paramArrayList, int[] paramArrayOfInt, boolean paramBoolean)
/*     */     {
/*  90 */       this.type = paramInt1;
/*  91 */       this.from = paramInt2;
/*  92 */       this.to = paramInt3;
/*  93 */       this.removed = paramArrayList;
/*  94 */       this.perm = paramArrayOfInt;
/*  95 */       this.updated = paramBoolean;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.collections.IterableChangeBuilder
 * JD-Core Version:    0.6.2
 */