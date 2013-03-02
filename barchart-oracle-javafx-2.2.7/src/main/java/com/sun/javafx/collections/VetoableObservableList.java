/*     */ package com.sun.javafx.collections;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.ConcurrentModificationException;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import javafx.collections.ListChangeListener.Change;
/*     */ import javafx.collections.ObservableList;
/*     */ 
/*     */ public abstract class VetoableObservableList<T> extends ObservableListWrapper<T>
/*     */   implements ObservableList<T>
/*     */ {
/*     */   private int modCount;
/*     */ 
/*     */   protected abstract void onProposedChange(List<T> paramList, int[] paramArrayOfInt);
/*     */ 
/*     */   protected void onChanged(ListChangeListener.Change<T> paramChange)
/*     */   {
/*     */   }
/*     */ 
/*     */   public VetoableObservableList(List<T> paramList)
/*     */   {
/*  74 */     super(paramList);
/*     */   }
/*     */ 
/*     */   public VetoableObservableList() {
/*  78 */     super(new ArrayList());
/*     */   }
/*     */ 
/*     */   protected void callObservers(ListChangeListener.Change<T> paramChange)
/*     */   {
/*  83 */     onChanged(paramChange);
/*  84 */     paramChange.reset();
/*  85 */     super.callObservers(paramChange);
/*     */   }
/*     */ 
/*     */   public boolean setAll(Collection<? extends T> paramCollection)
/*     */   {
/*  90 */     onProposedChange(new ArrayList(paramCollection), new int[] { 0, this.backingList.size() });
/*  91 */     this.modCount += 1;
/*  92 */     super.setAll(paramCollection);
/*  93 */     return true;
/*     */   }
/*     */ 
/*     */   public Iterator<T> iterator()
/*     */   {
/*  98 */     return new VetoableObservableListIterator(this.backingList.iterator(), 0, 0);
/*     */   }
/*     */ 
/*     */   public boolean addAll(int paramInt, Collection<? extends T> paramCollection)
/*     */   {
/* 103 */     if ((paramInt < 0) || (paramInt > size())) {
/* 104 */       throw new IndexOutOfBoundsException();
/*     */     }
/* 106 */     onProposedChange(new ArrayList(paramCollection), new int[] { paramInt, paramInt });
/* 107 */     this.modCount += 1;
/* 108 */     return super.addAll(paramInt, paramCollection);
/*     */   }
/*     */ 
/*     */   protected boolean removeFromList(List<T> paramList, int paramInt, Collection<?> paramCollection, boolean paramBoolean)
/*     */   {
/* 114 */     Object localObject1 = new int[2];
/* 115 */     int i = -1;
/* 116 */     for (int j = 0; j < paramList.size(); j++) {
/* 117 */       Object localObject2 = paramList.get(j);
/* 118 */       if ((paramCollection.contains(localObject2) ^ paramBoolean)) {
/* 119 */         if (i == -1) {
/* 120 */           localObject1[(i + 1)] = (paramInt + j);
/* 121 */           localObject1[(i + 2)] = (paramInt + j + 1);
/* 122 */           i += 2;
/*     */         }
/* 124 */         else if (localObject1[(i - 1)] == paramInt + j) {
/* 125 */           localObject1[(i - 1)] = (paramInt + j + 1);
/*     */         } else {
/* 127 */           int[] arrayOfInt = new int[localObject1.length + 2];
/* 128 */           System.arraycopy(localObject1, 0, arrayOfInt, 0, localObject1.length);
/* 129 */           localObject1 = arrayOfInt;
/* 130 */           localObject1[(i + 1)] = (paramInt + j);
/* 131 */           localObject1[(i + 2)] = (paramInt + j + 1);
/* 132 */           i += 2;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 137 */     if (i == -1) {
/* 138 */       return false;
/*     */     }
/* 140 */     onProposedChange(Collections.emptyList(), (int[])localObject1);
/* 141 */     return super.removeFromList(paramList, paramInt, paramCollection, paramBoolean);
/*     */   }
/*     */ 
/*     */   public void clear()
/*     */   {
/* 146 */     onProposedChange(Collections.emptyList(), new int[] { 0, size() });
/* 147 */     this.modCount += 1;
/* 148 */     super.clear();
/*     */   }
/*     */ 
/*     */   public T set(int paramInt, T paramT)
/*     */   {
/* 153 */     if ((paramInt < 0) || (paramInt >= size())) {
/* 154 */       throw new IndexOutOfBoundsException();
/*     */     }
/* 156 */     onProposedChange(Collections.singletonList(paramT), new int[] { paramInt, paramInt + 1 });
/* 157 */     return super.set(paramInt, paramT);
/*     */   }
/*     */ 
/*     */   public void add(int paramInt, T paramT)
/*     */   {
/* 162 */     if ((paramInt < 0) || (paramInt > size())) {
/* 163 */       throw new IndexOutOfBoundsException();
/*     */     }
/* 165 */     onProposedChange(Collections.singletonList(paramT), new int[] { paramInt, paramInt });
/* 166 */     this.modCount += 1;
/* 167 */     super.add(paramInt, paramT);
/*     */   }
/*     */ 
/*     */   public T remove(int paramInt)
/*     */   {
/* 172 */     onProposedChange(Collections.emptyList(), new int[] { paramInt, paramInt + 1 });
/* 173 */     this.modCount += 1;
/* 174 */     return super.remove(paramInt);
/*     */   }
/*     */ 
/*     */   public boolean remove(Object paramObject)
/*     */   {
/* 179 */     return super.remove(paramObject);
/*     */   }
/*     */ 
/*     */   protected boolean hasObserver()
/*     */   {
/* 184 */     return true;
/*     */   }
/*     */ 
/*     */   public ListIterator<T> listIterator()
/*     */   {
/* 189 */     return new VetoableObservableListIterator(this.backingList.listIterator(), 0, 0);
/*     */   }
/*     */ 
/*     */   public ListIterator<T> listIterator(int paramInt)
/*     */   {
/* 194 */     if ((paramInt < 0) || (paramInt > size())) {
/* 195 */       throw new IndexOutOfBoundsException();
/*     */     }
/* 197 */     return new VetoableObservableListIterator(this.backingList.listIterator(paramInt), 0, paramInt);
/*     */   }
/*     */ 
/*     */   public List<T> subList(int paramInt1, int paramInt2)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: new 34	com/sun/javafx/collections/VetoableObservableList$1
/*     */     //   3: dup
/*     */     //   4: aload_0
/*     */     //   5: iload_1
/*     */     //   6: iload_2
/*     */     //   7: invokespecial 35	com/sun/javafx/collections/VetoableObservableList$1:<init>	(Lcom/sun/javafx/collections/VetoableObservableList;II)V
/*     */     //   10: areturn
/*     */   }
/*     */ 
/*     */   private class VetoableObservableListIterator extends ObservableListWrapper.ObservableListIterator
/*     */   {
/* 275 */     boolean requireNextPrev = true;
/* 276 */     private int expectedModCount = VetoableObservableList.this.modCount;
/*     */ 
/*     */     public VetoableObservableListIterator(int paramInt1, int arg3) {
/* 279 */       super(paramInt1, i, j);
/*     */     }
/*     */ 
/*     */     public VetoableObservableListIterator(int paramInt1, int arg3) {
/* 283 */       super(paramInt1, i, j);
/*     */     }
/*     */ 
/*     */     public void add(T paramT)
/*     */     {
/* 288 */       checkForComodification();
/* 289 */       VetoableObservableList.this.onProposedChange(Collections.singletonList(paramT), new int[] { this.cursor, this.cursor });
/* 290 */       this.requireNextPrev = true;
/* 291 */       super.add(paramT);
/*     */     }
/*     */ 
/*     */     public void set(T paramT)
/*     */     {
/* 296 */       if (this.requireNextPrev) {
/* 297 */         throw new IllegalStateException();
/*     */       }
/* 299 */       checkForComodification();
/* 300 */       if (this.forward)
/* 301 */         VetoableObservableList.this.onProposedChange(Collections.singletonList(paramT), new int[] { this.cursor - 1, this.cursor });
/*     */       else {
/* 303 */         VetoableObservableList.this.onProposedChange(Collections.singletonList(paramT), new int[] { this.cursor, this.cursor + 1 });
/*     */       }
/* 305 */       this.expectedModCount = VetoableObservableList.access$004(VetoableObservableList.this);
/* 306 */       super.set(paramT);
/*     */     }
/*     */ 
/*     */     public void remove()
/*     */     {
/* 311 */       if (this.requireNextPrev) {
/* 312 */         throw new IllegalStateException();
/*     */       }
/* 314 */       checkForComodification();
/* 315 */       if (this.forward)
/* 316 */         VetoableObservableList.this.onProposedChange(Collections.emptyList(), new int[] { this.cursor - 1, this.cursor });
/*     */       else {
/* 318 */         VetoableObservableList.this.onProposedChange(Collections.emptyList(), new int[] { this.cursor, this.cursor + 1 });
/*     */       }
/* 320 */       this.expectedModCount = VetoableObservableList.access$004(VetoableObservableList.this);
/* 321 */       this.requireNextPrev = true;
/* 322 */       super.remove();
/*     */     }
/*     */ 
/*     */     public T next()
/*     */     {
/* 327 */       this.requireNextPrev = false;
/* 328 */       return super.next();
/*     */     }
/*     */ 
/*     */     public T previous()
/*     */     {
/* 333 */       this.requireNextPrev = false;
/* 334 */       return super.previous();
/*     */     }
/*     */ 
/*     */     private void checkForComodification() {
/* 338 */       if (VetoableObservableList.this.modCount != this.expectedModCount)
/* 339 */         throw new ConcurrentModificationException();
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.collections.VetoableObservableList
 * JD-Core Version:    0.6.2
 */