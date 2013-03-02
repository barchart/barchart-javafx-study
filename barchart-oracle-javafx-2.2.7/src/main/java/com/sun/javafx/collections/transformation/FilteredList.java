/*     */ package com.sun.javafx.collections.transformation;
/*     */ 
/*     */ import com.sun.javafx.collections.NonIterableChange.GenericAddRemoveChange;
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javafx.collections.ListChangeListener.Change;
/*     */ 
/*     */ public final class FilteredList<E> extends TransformationList<E, E>
/*     */   implements FilterableList<E>
/*     */ {
/*     */   private FilterableList.FilterMode mode;
/*     */   private Matcher<? super E> matcher;
/*     */   private int[] filtered;
/*     */   private int size;
/*     */   private boolean matcherChanged;
/*     */ 
/*     */   private int findPosition(int paramInt)
/*     */   {
/*  86 */     if (this.filtered.length == 0) {
/*  87 */       return 0;
/*     */     }
/*  89 */     int i = Arrays.binarySearch(this.filtered, 0, this.size, paramInt);
/*  90 */     if (i < 0) {
/*  91 */       i ^= -1;
/*     */     }
/*  93 */     return i;
/*     */   }
/*     */ 
/*     */   private void ensureSize(int paramInt)
/*     */   {
/*  99 */     if (this.filtered.length < paramInt) {
/* 100 */       int[] arrayOfInt = new int[paramInt * 3 / 2 + 1];
/* 101 */       System.arraycopy(this.filtered, 0, arrayOfInt, 0, this.size);
/* 102 */       this.filtered = arrayOfInt;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void removeFilteredRange(int paramInt1, int paramInt2, List<? extends E> paramList, List<E> paramList1) {
/* 107 */     int i = paramInt1 + paramList.size();
/* 108 */     int j = findPosition(i);
/* 109 */     int k = j - paramInt2;
/* 110 */     if (paramInt2 != j) {
/* 111 */       for (int m = paramInt2; m < j; m++) {
/* 112 */         paramList1.add(paramList.get(this.filtered[m] - paramInt1));
/*     */       }
/* 114 */       System.arraycopy(this.filtered, j, this.filtered, paramInt2, this.size - j);
/* 115 */       this.size -= k;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void updateIndexes(int paramInt1, int paramInt2) {
/* 120 */     for (int i = paramInt1; i < this.size; i++)
/* 121 */       this.filtered[i] += paramInt2;
/*     */   }
/*     */ 
/*     */   private void update(int paramInt1, List<? extends E> paramList, int[] paramArrayOfInt, int paramInt2)
/*     */   {
/* 126 */     if ((paramInt2 | paramList.size()) == 0) {
/* 127 */       return;
/*     */     }
/* 129 */     int i = findPosition(paramInt1);
/* 130 */     ArrayList localArrayList = new ArrayList();
/* 131 */     removeFilteredRange(paramInt1, i, paramList, localArrayList);
/* 132 */     ensureSize(this.size + paramInt2 - localArrayList.size());
/* 133 */     System.arraycopy(this.filtered, i, this.filtered, i + paramInt2, this.size - i);
/* 134 */     for (int j = 0; j < paramInt2; j++) {
/* 135 */       this.filtered[(i + j)] = paramArrayOfInt[j];
/*     */     }
/* 137 */     this.size += paramInt2;
/* 138 */     updateIndexes(i + paramInt2, paramInt2 - paramList.size());
/* 139 */     fireChange(new NonIterableChange.GenericAddRemoveChange(i, i + paramInt2, Collections.unmodifiableList(localArrayList), this));
/*     */   }
/*     */ 
/*     */   public FilteredList(List<E> paramList, Matcher<? super E> paramMatcher, FilterableList.FilterMode paramFilterMode)
/*     */   {
/* 153 */     super(paramList);
/* 154 */     if (paramMatcher == null) {
/* 155 */       throw new NullPointerException();
/*     */     }
/* 157 */     this.matcher = paramMatcher;
/* 158 */     this.mode = paramFilterMode;
/* 159 */     this.filtered = new int[paramList.size() * 3 / 2 + 1];
/* 160 */     if (paramFilterMode == FilterableList.FilterMode.LIVE) {
/* 161 */       if (!this.observable) {
/* 162 */         throw new IllegalArgumentException("Cannot use LIVE mode with list that is not an ObservableList");
/*     */       }
/* 164 */       refilter();
/*     */     } else {
/* 166 */       this.size = paramList.size();
/* 167 */       for (int i = 0; i < this.size; i++)
/* 168 */         this.filtered[i] = i;
/*     */     }
/*     */   }
/*     */ 
/*     */   public FilteredList(List<E> paramList, Matcher<? super E> paramMatcher)
/*     */   {
/* 181 */     this(paramList, paramMatcher, FilterableList.FilterMode.LIVE);
/*     */   }
/*     */ 
/*     */   protected void onSourceChanged(ListChangeListener.Change<? extends E> paramChange)
/*     */   {
/* 186 */     if (this.mode == FilterableList.FilterMode.BATCH) {
/* 187 */       int[] arrayOfInt1 = new int[paramChange.getAddedSize()];
/* 188 */       for (int j = 0; j < arrayOfInt1.length; j++) {
/* 189 */         arrayOfInt1[j] = (paramChange.getFrom() + j);
/*     */       }
/* 191 */       update(paramChange.getFrom(), paramChange.getRemoved(), arrayOfInt1, arrayOfInt1.length);
/*     */     } else {
/* 193 */       if (paramChange.wasPermutated()) {
/* 194 */         refilter();
/*     */       }
/* 196 */       int i = 0;
/* 197 */       int[] arrayOfInt2 = new int[paramChange.getAddedSize()];
/* 198 */       for (int k = paramChange.getFrom(); k < paramChange.getTo(); k++) {
/* 199 */         if (this.matcher.matches(this.source.get(k))) {
/* 200 */           arrayOfInt2[(i++)] = k;
/*     */         }
/*     */       }
/* 203 */       update(paramChange.getFrom(), paramChange.getRemoved(), arrayOfInt2, i);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean addAll(E[] paramArrayOfE)
/*     */   {
/* 210 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   public boolean setAll(E[] paramArrayOfE)
/*     */   {
/* 215 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   public boolean setAll(Collection<? extends E> paramCollection)
/*     */   {
/* 220 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   public boolean removeAll(E[] paramArrayOfE)
/*     */   {
/* 225 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   public boolean retainAll(E[] paramArrayOfE)
/*     */   {
/* 230 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   public void remove(int paramInt1, int paramInt2)
/*     */   {
/* 235 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   public int size()
/*     */   {
/* 245 */     return this.size;
/*     */   }
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/* 255 */     return this.size == 0;
/*     */   }
/*     */ 
/*     */   public boolean contains(Object paramObject)
/*     */   {
/* 269 */     return indexOf(paramObject) != -1;
/*     */   }
/*     */ 
/*     */   public Object[] toArray()
/*     */   {
/* 288 */     Object[] arrayOfObject = new Object[this.size];
/* 289 */     for (int i = 0; i < this.size; i++) {
/* 290 */       arrayOfObject[i] = get(i);
/*     */     }
/* 292 */     return arrayOfObject;
/*     */   }
/*     */ 
/*     */   public <T> T[] toArray(T[] paramArrayOfT)
/*     */   {
/*     */     Object localObject;
/* 323 */     if (paramArrayOfT.length < this.size) {
/* 324 */       localObject = (Object[])Array.newInstance(paramArrayOfT.getClass().getComponentType(), this.size);
/*     */     }
/*     */     else {
/* 327 */       localObject = paramArrayOfT;
/*     */     }
/* 329 */     for (int i = 0; i < this.size; i++) {
/* 330 */       localObject[i] = get(i);
/*     */     }
/* 332 */     return (Object[])localObject;
/*     */   }
/*     */ 
/*     */   public E get(int paramInt)
/*     */   {
/* 345 */     if (paramInt >= this.size) {
/* 346 */       throw new IndexOutOfBoundsException();
/*     */     }
/* 348 */     return this.source.get(this.filtered[paramInt]);
/*     */   }
/*     */ 
/*     */   public int indexOf(Object paramObject)
/*     */   {
/* 360 */     for (int i = 0; i < size(); i++) {
/* 361 */       if (((paramObject == null) && (get(i) == null)) || ((paramObject != null) && (paramObject.equals(get(i)))))
/*     */       {
/* 363 */         return i;
/*     */       }
/*     */     }
/* 366 */     return -1;
/*     */   }
/*     */ 
/*     */   public int lastIndexOf(Object paramObject)
/*     */   {
/* 378 */     for (int i = size() - 1; i >= 0; i--) {
/* 379 */       if (((paramObject == null) && (get(i) == null)) || ((paramObject != null) && (paramObject.equals(get(i)))))
/*     */       {
/* 381 */         return i;
/*     */       }
/*     */     }
/* 384 */     return -1;
/*     */   }
/*     */ 
/*     */   private void refilter()
/*     */   {
/* 389 */     ensureSize(this.source.size());
/* 390 */     ArrayList localArrayList = new ArrayList(this);
/* 391 */     this.size = 0;
/* 392 */     int i = 0;
/* 393 */     for (Iterator localIterator = this.source.iterator(); localIterator.hasNext(); ) {
/* 394 */       Object localObject = localIterator.next();
/* 395 */       if (this.matcher.matches(localObject)) {
/* 396 */         this.filtered[(this.size++)] = i;
/*     */       }
/* 398 */       i++;
/*     */     }
/* 400 */     fireChange(new NonIterableChange.GenericAddRemoveChange(0, this.size, localArrayList, this));
/*     */   }
/*     */ 
/*     */   public void filter()
/*     */   {
/* 405 */     if (this.mode == FilterableList.FilterMode.BATCH) {
/* 406 */       if (this.matcherChanged) {
/* 407 */         refilter();
/* 408 */         this.matcherChanged = false;
/* 409 */         return;
/*     */       }
/*     */ 
/* 412 */       for (int i = 0; i < this.size; i++) {
/* 413 */         for (j = i == 0 ? 0 : this.filtered[(i - 1)] + 1; j < this.filtered[i]; j++) {
/* 414 */           if (this.matcher.matches(this.source.get(j))) {
/* 415 */             refilter();
/* 416 */             return;
/*     */           }
/*     */         }
/*     */       }
/* 420 */       for (i = this.filtered[(this.size - 1)] + 1; i < this.source.size(); i++) {
/* 421 */         if (this.matcher.matches(this.source.get(i))) {
/* 422 */           refilter();
/* 423 */           return;
/*     */         }
/*     */       }
/*     */ 
/* 427 */       i = -1; int j = -1;
/* 428 */       ArrayList localArrayList = new ArrayList();
/* 429 */       for (int k = 0; k < this.size; k++) {
/* 430 */         if (!this.matcher.matches(this.source.get(this.filtered[k]))) {
/* 431 */           if (i == -1) {
/* 432 */             i = k;
/*     */           }
/* 434 */           this.filtered[k] ^= -1;
/* 435 */           j = k;
/*     */         }
/*     */       }
/* 438 */       j++;
/*     */ 
/* 440 */       if (i == -1) {
/* 441 */         return;
/*     */       }
/*     */ 
/* 444 */       for (k = i; k < j; k++) {
/* 445 */         localArrayList.add(this.source.get(this.filtered[k] < 0 ? this.filtered[k] ^ 0xFFFFFFFF : this.filtered[k]));
/*     */       }
/*     */ 
/* 448 */       for (k = i; k < j; k++) {
/* 449 */         if (this.filtered[k] < 0) {
/* 450 */           System.arraycopy(this.filtered, k + 1, this.filtered, k, this.size - k - 1);
/* 451 */           this.size -= 1;
/* 452 */           j--;
/* 453 */           k--;
/*     */         }
/*     */       }
/*     */ 
/* 457 */       fireChange(new NonIterableChange.GenericAddRemoveChange(i, j, Collections.unmodifiableList(localArrayList), this));
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setMode(FilterableList.FilterMode paramFilterMode)
/*     */   {
/* 463 */     if (this.mode != paramFilterMode) {
/* 464 */       this.mode = paramFilterMode;
/* 465 */       if (paramFilterMode == FilterableList.FilterMode.LIVE)
/* 466 */         filter();
/*     */     }
/*     */   }
/*     */ 
/*     */   public FilterableList.FilterMode getMode()
/*     */   {
/* 473 */     return this.mode;
/*     */   }
/*     */ 
/*     */   public void setMatcher(Matcher<? super E> paramMatcher)
/*     */   {
/* 478 */     if (this.matcher != paramMatcher) {
/* 479 */       this.matcher = paramMatcher;
/* 480 */       if (this.mode == FilterableList.FilterMode.LIVE)
/* 481 */         refilter();
/*     */       else
/* 483 */         this.matcherChanged = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Matcher<? super E> getMatcher()
/*     */   {
/* 490 */     return this.matcher;
/*     */   }
/*     */ 
/*     */   public int getSourceIndex(int paramInt)
/*     */   {
/* 495 */     if (paramInt >= this.size) {
/* 496 */       throw new IndexOutOfBoundsException();
/*     */     }
/* 498 */     return this.filtered[paramInt];
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.collections.transformation.FilteredList
 * JD-Core Version:    0.6.2
 */