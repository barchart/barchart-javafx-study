/*     */ package com.sun.javafx.collections.transformation;
/*     */ 
/*     */ import com.sun.javafx.collections.NonIterableChange.GenericAddRemoveChange;
/*     */ import com.sun.javafx.collections.NonIterableChange.SimpleAddChange;
/*     */ import com.sun.javafx.collections.NonIterableChange.SimplePermutationChange;
/*     */ import com.sun.javafx.collections.NonIterableChange.SimpleRemovedChange;
/*     */ import com.sun.javafx.collections.SortHelper;
/*     */ import java.lang.reflect.Array;
/*     */ import java.text.Collator;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import javafx.collections.ListChangeListener.Change;
/*     */ import javafx.collections.ObservableList;
/*     */ 
/*     */ public final class SortedList<E> extends TransformationList<E, E>
/*     */   implements SortableList<E>
/*     */ {
/*     */   private SortableList.SortMode mode;
/*     */   private ElementComparator<E> comparator;
/*     */   private Element<E>[] sorted;
/*     */   private int size;
/*     */   private boolean comparisonFailed;
/*     */   private SortHelper helper;
/*  70 */   private Element<E> tempElement = new Element(null, -1);
/*     */ 
/*     */   private SortHelper getSortHelper()
/*     */   {
/*  74 */     if (this.helper == null) {
/*  75 */       this.helper = new SortHelper();
/*     */     }
/*  77 */     return this.helper;
/*     */   }
/*     */ 
/*     */   private void updatePermutationIndexes(ListChangeListener.Change<? extends E> paramChange) {
/*  81 */     for (int i = 0; i < this.size; i++)
/*  82 */       this.sorted[i].index = paramChange.getPermutation(this.sorted[i].index);
/*     */   }
/*     */ 
/*     */   private void ensureSize(int paramInt)
/*     */   {
/* 133 */     if (this.sorted.length < paramInt) {
/* 134 */       Element[] arrayOfElement = new Element[paramInt * 3 / 2 + 1];
/* 135 */       System.arraycopy(this.sorted, 0, arrayOfElement, 0, this.size);
/* 136 */       this.sorted = ((Element[])arrayOfElement);
/*     */     }
/*     */   }
/*     */ 
/*     */   private int findPosition(E paramE) {
/* 141 */     if (this.sorted.length == 0) {
/* 142 */       return 0;
/*     */     }
/* 144 */     this.tempElement.e = paramE;
/* 145 */     int i = Arrays.binarySearch(this.sorted, 0, this.size, this.tempElement, this.comparator);
/* 146 */     return i;
/*     */   }
/*     */ 
/*     */   private int compare(E paramE1, E paramE2) {
/* 150 */     return this.comparator.comparator == null ? ((Comparable)paramE1).compareTo(paramE2) : this.comparator.comparator.compare(paramE1, paramE2);
/*     */   }
/*     */ 
/*     */   private int findPosition(int paramInt, E paramE)
/*     */   {
/* 155 */     if (this.mode == SortableList.SortMode.BATCH) {
/* 156 */       for (i = 0; i < this.size; i++) {
/* 157 */         if (this.sorted[i].index == paramInt) {
/* 158 */           return i;
/*     */         }
/*     */       }
/* 161 */       return -1;
/*     */     }
/* 163 */     int i = findPosition(paramE);
/* 164 */     if (this.sorted[i].index == paramInt) {
/* 165 */       return i;
/*     */     }
/* 167 */     int j = i;
/* 168 */     while ((this.sorted[(--j)].index != paramInt) && (compare(this.sorted[j].e, paramE) == 0));
/* 169 */     if (this.sorted[j].index == paramInt) {
/* 170 */       return j;
/*     */     }
/* 172 */     j = i;
/* 173 */     while ((this.sorted[(++j)].index != paramInt) && (compare(this.sorted[j].e, paramE) == 0));
/* 174 */     if (this.sorted[j].index == paramInt) {
/* 175 */       return j;
/*     */     }
/* 177 */     return -1;
/*     */   }
/*     */ 
/*     */   private void insertUnsorted(int paramInt1, int paramInt2) {
/* 181 */     ensureSize(this.size + paramInt2 - paramInt1);
/* 182 */     updateIndices(paramInt1, paramInt2 - paramInt1);
/* 183 */     for (int i = paramInt1; i < paramInt2; i++) {
/* 184 */       this.sorted[(this.size + i - paramInt1)] = new Element(this.source.get(i), i);
/*     */     }
/* 186 */     this.size += paramInt2 - paramInt1;
/* 187 */     fireChange(new NonIterableChange.SimpleAddChange(this.size - paramInt2 + paramInt1, this.size, this));
/*     */   }
/*     */ 
/*     */   private void insertOneSorted(E paramE, int paramInt) {
/* 191 */     int i = findPosition(paramE);
/* 192 */     if (i < 0) {
/* 193 */       i ^= -1;
/*     */     }
/* 195 */     ensureSize(this.size + 1);
/* 196 */     updateIndices(paramInt, 1);
/* 197 */     System.arraycopy(this.sorted, i, this.sorted, i + 1, this.size - i);
/* 198 */     this.sorted[i] = new Element(paramE, paramInt);
/* 199 */     this.size += 1;
/* 200 */     fireChange(new NonIterableChange.SimpleAddChange(i, i + 1, this));
/*     */   }
/*     */ 
/*     */   private void removeOne(int paramInt, E paramE)
/*     */   {
/* 205 */     int i = findPosition(paramInt, paramE);
/* 206 */     System.arraycopy(this.sorted, i + 1, this.sorted, i, this.size - i - 1);
/* 207 */     this.size -= 1;
/* 208 */     updateIndices(paramInt + 1, -1);
/* 209 */     fireChange(new NonIterableChange.SimpleRemovedChange(i, i, paramE, this));
/*     */   }
/*     */ 
/*     */   private void updateUnsorted(ListChangeListener.Change<? extends E> paramChange)
/*     */   {
/* 214 */     ArrayList localArrayList = new ArrayList();
/* 215 */     List localList = paramChange.getRemoved();
/* 216 */     int i = 2147483647; int j = -1;
/* 217 */     for (int k = 0; k < paramChange.getRemovedSize(); k++) {
/* 218 */       Object localObject = localList.get(k);
/* 219 */       int m = findPosition(paramChange.getFrom() + k, localObject);
/* 220 */       if (m < i) {
/* 221 */         i = m;
/*     */       }
/* 223 */       if (m + 1 > j) {
/* 224 */         j = m + 1;
/*     */       }
/* 226 */       this.sorted[m].removeFlag = true;
/*     */     }
/* 228 */     if (j == -1) {
/* 229 */       i = j = 0;
/*     */     }
/*     */ 
/* 232 */     for (k = i; k < j; k++) {
/* 233 */       localArrayList.add(this.sorted[k].e);
/*     */     }
/*     */ 
/* 237 */     for (k = i; k < j; k++) {
/* 238 */       if (this.sorted[k].removeFlag) {
/* 239 */         System.arraycopy(this.sorted, k + 1, this.sorted, k, this.size - k - 1);
/* 240 */         this.size -= 1;
/* 241 */         j--;
/* 242 */         k--;
/*     */       }
/*     */     }
/*     */ 
/* 246 */     updateIndices(paramChange.getFrom() + paramChange.getRemovedSize(), paramChange.getAddedSize() - paramChange.getRemovedSize());
/* 247 */     if (paramChange.wasAdded()) {
/* 248 */       ensureSize(this.size + paramChange.getAddedSize());
/*     */ 
/* 250 */       System.arraycopy(this.sorted, j, this.sorted, j + paramChange.getAddedSize(), this.size - j);
/* 251 */       this.size += paramChange.getAddedSize();
/* 252 */       for (k = paramChange.getFrom(); k < paramChange.getTo(); k++) {
/* 253 */         this.sorted[(j++)] = new Element(paramChange.getList().get(k), k);
/*     */       }
/*     */     }
/*     */ 
/* 257 */     fireChange(new NonIterableChange.GenericAddRemoveChange(i, j, Collections.unmodifiableList(localArrayList), this));
/*     */   }
/*     */ 
/*     */   private void updateSorted(ListChangeListener.Change<? extends E> paramChange)
/*     */   {
/* 262 */     ArrayList localArrayList = new ArrayList();
/* 263 */     List localList = paramChange.getRemoved();
/* 264 */     int i = 2147483647; int j = -1;
/* 265 */     for (int k = 0; k < paramChange.getRemovedSize(); k++) {
/* 266 */       Object localObject = localList.get(k);
/* 267 */       int n = findPosition(paramChange.getFrom() + k, localObject);
/* 268 */       if (n < i) {
/* 269 */         i = n;
/*     */       }
/* 271 */       if (n + 1 > j) {
/* 272 */         j = n + 1;
/*     */       }
/* 274 */       this.sorted[n].removeFlag = true;
/*     */     }
/*     */ 
/* 277 */     if (j == -1)
/* 278 */       i = j = 0;
/*     */     int m;
/* 281 */     for (k = paramChange.getFrom(); k < paramChange.getTo(); k++) {
/* 282 */       m = findPosition(paramChange.getList().get(k));
/* 283 */       if (m < 0) {
/* 284 */         m ^= -1;
/*     */       }
/* 286 */       if (m < i) {
/* 287 */         i = m;
/*     */       }
/* 289 */       if (m > j) {
/* 290 */         j = m;
/*     */       }
/*     */     }
/*     */ 
/* 294 */     for (k = i; k < j; k++) {
/* 295 */       localArrayList.add(this.sorted[k].e);
/*     */     }
/*     */ 
/* 299 */     for (k = i; k < j; k++) {
/* 300 */       if (this.sorted[k].removeFlag) {
/* 301 */         System.arraycopy(this.sorted, k + 1, this.sorted, k, this.size - k - 1);
/* 302 */         this.size -= 1;
/* 303 */         j--;
/* 304 */         k--;
/*     */       }
/*     */     }
/*     */ 
/* 308 */     updateIndices(paramChange.getFrom() + paramChange.getRemovedSize(), paramChange.getAddedSize() - paramChange.getRemovedSize());
/* 309 */     if (paramChange.wasAdded()) {
/* 310 */       ensureSize(this.size + paramChange.getAddedSize());
/*     */ 
/* 312 */       for (k = paramChange.getFrom(); k < paramChange.getTo(); k++) {
/* 313 */         m = findPosition(paramChange.getList().get(k));
/* 314 */         if (m < 0) {
/* 315 */           m ^= -1;
/*     */         }
/* 317 */         System.arraycopy(this.sorted, m, this.sorted, m + 1, this.size - m);
/* 318 */         this.sorted[m] = new Element(paramChange.getList().get(k), k);
/* 319 */         this.size += 1;
/* 320 */         j++;
/*     */       }
/*     */     }
/* 323 */     fireChange(new NonIterableChange.GenericAddRemoveChange(i, j, Collections.unmodifiableList(localArrayList), this));
/*     */   }
/*     */ 
/*     */   private void updateIndices(int paramInt1, int paramInt2) {
/* 327 */     for (int i = 0; i < this.size; i++)
/* 328 */       if (this.sorted[i].index >= paramInt1)
/* 329 */         Element.access$012(this.sorted[i], paramInt2);
/*     */   }
/*     */ 
/*     */   public SortedList(List<? extends E> paramList, Comparator<? super E> paramComparator, SortableList.SortMode paramSortMode)
/*     */   {
/* 347 */     super(paramList);
/* 348 */     if ((paramSortMode == SortableList.SortMode.LIVE) && (!this.observable)) {
/* 349 */       throw new IllegalArgumentException("Cannot create live mode SortedList with list that is not an ObservableList");
/*     */     }
/* 351 */     this.comparator = new ElementComparator(paramComparator);
/* 352 */     this.mode = paramSortMode;
/* 353 */     this.sorted = ((Element[])new Element[paramList.size() * 3 / 2 + 1]);
/* 354 */     this.size = paramList.size();
/* 355 */     for (int i = 0; i < this.size; i++) {
/* 356 */       this.sorted[i] = new Element(paramList.get(i), i);
/*     */     }
/* 358 */     if (paramSortMode == SortableList.SortMode.LIVE)
/*     */       try {
/* 360 */         doArraysSort();
/*     */       } catch (ClassCastException localClassCastException) {
/* 362 */         this.comparisonFailed = true;
/*     */       }
/*     */   }
/*     */ 
/*     */   public SortedList(List<E> paramList, Comparator<? super E> paramComparator)
/*     */   {
/* 376 */     this(paramList, paramComparator, SortableList.SortMode.LIVE);
/*     */   }
/*     */ 
/*     */   public SortedList(List<E> paramList)
/*     */   {
/* 386 */     this(paramList, null, SortableList.SortMode.LIVE);
/*     */   }
/*     */ 
/*     */   protected void onSourceChanged(ListChangeListener.Change<? extends E> paramChange)
/*     */   {
/* 392 */     if (this.comparisonFailed) {
/* 393 */       if (this.mode == SortableList.SortMode.LIVE)
/*     */         try {
/* 395 */           resort();
/*     */         }
/*     */         catch (ClassCastException localClassCastException1)
/*     */         {
/*     */         }
/* 400 */       return;
/*     */     }
/* 402 */     if (paramChange.wasPermutated()) {
/* 403 */       updatePermutationIndexes(paramChange);
/* 404 */       return;
/*     */     }
/*     */ 
/* 407 */     if (this.mode == SortableList.SortMode.BATCH) {
/* 408 */       if ((paramChange.wasAdded()) && (!paramChange.wasRemoved()))
/* 409 */         insertUnsorted(paramChange.getFrom(), paramChange.getTo());
/*     */       else
/* 411 */         updateUnsorted(paramChange);
/*     */     }
/*     */     else
/*     */       try {
/* 415 */         if ((paramChange.wasAdded()) && (!paramChange.wasRemoved()) && (paramChange.getAddedSize() == 1))
/* 416 */           insertOneSorted(paramChange.getList().get(paramChange.getFrom()), paramChange.getFrom());
/* 417 */         else if ((paramChange.wasRemoved()) && (paramChange.getRemovedSize() == 1))
/* 418 */           removeOne(paramChange.getFrom(), paramChange.getRemoved().get(0));
/*     */         else
/* 420 */           updateSorted(paramChange);
/*     */       }
/*     */       catch (ClassCastException localClassCastException2) {
/* 423 */         this.comparisonFailed = true;
/* 424 */         throw localClassCastException2;
/*     */       }
/*     */   }
/*     */ 
/*     */   public E get(int paramInt)
/*     */   {
/* 438 */     if (paramInt >= this.size) {
/* 439 */       throw new IndexOutOfBoundsException();
/*     */     }
/* 441 */     if (this.comparisonFailed) {
/* 442 */       throw new ClassCastException("Cannot use natural comparison as underlying list contains element(s) that are not Comparable");
/*     */     }
/* 444 */     return this.sorted[paramInt].e;
/*     */   }
/*     */ 
/*     */   public int size()
/*     */   {
/* 454 */     if (this.comparisonFailed) {
/* 455 */       throw new ClassCastException("Cannot use natural comparison as underlying list contains element(s) that are not Comparable");
/*     */     }
/* 457 */     return this.size;
/*     */   }
/*     */ 
/*     */   public boolean addAll(E[] paramArrayOfE)
/*     */   {
/* 462 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   public boolean setAll(E[] paramArrayOfE)
/*     */   {
/* 467 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   public boolean setAll(Collection<? extends E> paramCollection)
/*     */   {
/* 472 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   public boolean removeAll(E[] paramArrayOfE)
/*     */   {
/* 477 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   public boolean retainAll(E[] paramArrayOfE)
/*     */   {
/* 482 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   public void remove(int paramInt1, int paramInt2)
/*     */   {
/* 487 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   public int indexOf(Object paramObject)
/*     */   {
/* 499 */     if (this.comparisonFailed) {
/* 500 */       throw new ClassCastException("Cannot use natural comparison as underlying list contains element(s) that are not Comparable");
/*     */     }
/* 502 */     for (int i = 0; i < this.size; i++) {
/* 503 */       if (((paramObject == null) && (get(i) == null)) || ((paramObject != null) && (paramObject.equals(get(i)))))
/*     */       {
/* 505 */         return i;
/*     */       }
/*     */     }
/* 508 */     return -1;
/*     */   }
/*     */ 
/*     */   public int lastIndexOf(Object paramObject)
/*     */   {
/* 520 */     if (this.comparisonFailed) {
/* 521 */       throw new ClassCastException("Cannot use natural comparison as underlying list contains element(s) that are not Comparable");
/*     */     }
/* 523 */     for (int i = size() - 1; i >= 0; i--) {
/* 524 */       if (((paramObject == null) && (get(i) == null)) || ((paramObject != null) && (paramObject.equals(get(i)))))
/*     */       {
/* 526 */         return i;
/*     */       }
/*     */     }
/* 529 */     return -1;
/*     */   }
/*     */ 
/*     */   public Object[] toArray()
/*     */   {
/* 548 */     if (this.comparisonFailed) {
/* 549 */       throw new ClassCastException("Cannot use natural comparison as underlying list contains element(s) that are not Comparable");
/*     */     }
/* 551 */     Object[] arrayOfObject = new Object[this.size];
/* 552 */     for (int i = 0; i < this.size; i++) {
/* 553 */       arrayOfObject[i] = get(i);
/*     */     }
/* 555 */     return arrayOfObject;
/*     */   }
/*     */ 
/*     */   public <T> T[] toArray(T[] paramArrayOfT)
/*     */   {
/* 585 */     if (this.comparisonFailed)
/* 586 */       throw new ClassCastException("Cannot use natural comparison as underlying list contains element(s) that are not Comparable");
/*     */     Object localObject;
/* 589 */     if (paramArrayOfT.length < this.size) {
/* 590 */       localObject = (Object[])Array.newInstance(paramArrayOfT.getClass().getComponentType(), this.size);
/*     */     }
/*     */     else {
/* 593 */       localObject = paramArrayOfT;
/*     */     }
/* 595 */     for (int i = 0; i < this.size; i++) {
/* 596 */       localObject[i] = get(i);
/*     */     }
/* 598 */     return (Object[])localObject;
/*     */   }
/*     */ 
/*     */   public void sort()
/*     */   {
/* 603 */     if (this.comparisonFailed)
/* 604 */       resort();
/* 605 */     else if (this.mode == SortableList.SortMode.BATCH)
/*     */       try {
/* 607 */         doSortWithPermutationChange();
/*     */       } catch (ClassCastException localClassCastException) {
/* 609 */         this.comparisonFailed = true;
/* 610 */         throw localClassCastException;
/*     */       }
/*     */   }
/*     */ 
/*     */   private void doArraysSort()
/*     */   {
/* 617 */     Arrays.sort(this.sorted, 0, this.size, this.comparator);
/*     */   }
/*     */ 
/*     */   private void doSortWithPermutationChange() {
/* 621 */     int[] arrayOfInt = getSortHelper().sort(this.sorted, 0, this.size, this.comparator);
/* 622 */     fireChange(new NonIterableChange.SimplePermutationChange(0, this.size, arrayOfInt, this));
/*     */   }
/*     */ 
/*     */   public void setMode(SortableList.SortMode paramSortMode)
/*     */   {
/* 627 */     if (this.mode != paramSortMode) {
/* 628 */       if ((paramSortMode == SortableList.SortMode.LIVE) && (!this.observable)) {
/* 629 */         throw new IllegalArgumentException("Cannot switch to LIVE mode. A source list is not an ObservableList");
/*     */       }
/* 631 */       this.mode = paramSortMode;
/* 632 */       if (paramSortMode == SortableList.SortMode.LIVE)
/* 633 */         sort();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void resort()
/*     */   {
/* 640 */     ensureSize(this.source.size());
/* 641 */     this.source.toArray(this.sorted);
/* 642 */     this.size = this.source.size();
/* 643 */     doArraysSort();
/* 644 */     this.comparisonFailed = false;
/*     */   }
/*     */ 
/*     */   public SortableList.SortMode getMode()
/*     */   {
/* 649 */     return this.mode;
/*     */   }
/*     */ 
/*     */   public void setComparator(Comparator<? super E> paramComparator)
/*     */   {
/* 663 */     this.comparator = new ElementComparator(paramComparator);
/* 664 */     if (this.mode == SortableList.SortMode.LIVE)
/*     */       try {
/* 666 */         if (this.comparisonFailed)
/* 667 */           resort();
/*     */         else
/* 669 */           doSortWithPermutationChange();
/*     */       }
/*     */       catch (ClassCastException localClassCastException) {
/* 672 */         this.comparisonFailed = true;
/*     */       }
/*     */   }
/*     */ 
/*     */   public Comparator<? super E> getComparator()
/*     */   {
/* 679 */     return this.comparator.comparator;
/*     */   }
/*     */ 
/*     */   public int getSourceIndex(int paramInt)
/*     */   {
/* 684 */     return this.sorted[paramInt].index;
/*     */   }
/*     */ 
/*     */   private static class ElementComparator<E>
/*     */     implements Comparator<SortedList.Element<E>>
/*     */   {
/*     */     private Comparator<? super E> comparator;
/*     */ 
/*     */     public ElementComparator(Comparator<? super E> paramComparator)
/*     */     {
/* 103 */       this.comparator = paramComparator;
/*     */     }
/*     */ 
/*     */     public int compare(SortedList.Element<E> paramElement1, SortedList.Element<E> paramElement2)
/*     */     {
/* 109 */       if (this.comparator == null) {
/* 110 */         if ((SortedList.Element.access$100(paramElement1) == null) && (SortedList.Element.access$100(paramElement2) == null)) {
/* 111 */           return 0;
/*     */         }
/* 113 */         if (SortedList.Element.access$100(paramElement1) == null) {
/* 114 */           return -1;
/*     */         }
/* 116 */         if (SortedList.Element.access$100(paramElement2) == null) {
/* 117 */           return 1;
/*     */         }
/*     */ 
/* 120 */         if ((SortedList.Element.access$100(paramElement1) instanceof Comparable)) {
/* 121 */           return ((Comparable)SortedList.Element.access$100(paramElement1)).compareTo(SortedList.Element.access$100(paramElement2));
/*     */         }
/*     */ 
/* 124 */         return Collator.getInstance().compare(SortedList.Element.access$100(paramElement1).toString(), SortedList.Element.access$100(paramElement2).toString());
/*     */       }
/* 126 */       return this.comparator.compare(SortedList.Element.access$100(paramElement1), SortedList.Element.access$100(paramElement2));
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class Element<E>
/*     */   {
/*     */     private E e;
/*     */     private int index;
/*     */     private boolean removeFlag;
/*     */ 
/*     */     public Element(E paramE, int paramInt)
/*     */     {
/*  89 */       this.e = paramE;
/*  90 */       this.index = paramInt;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.collections.transformation.SortedList
 * JD-Core Version:    0.6.2
 */