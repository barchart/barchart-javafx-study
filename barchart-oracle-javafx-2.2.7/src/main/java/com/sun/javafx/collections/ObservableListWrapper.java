/*     */ package com.sun.javafx.collections;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.Observable;
/*     */ import javafx.collections.ListChangeListener;
/*     */ import javafx.collections.ListChangeListener.Change;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.util.Callback;
/*     */ 
/*     */ public class ObservableListWrapper<E>
/*     */   implements ObservableList<E>, SortableList<E>
/*     */ {
/*     */   private ListListenerHelper<E> listenerHelper;
/*     */   List<E> backingList;
/*     */   Callback<E, Observable[]> extractor;
/*     */   private IdentityHashMap<E, ElementsMapElement> elementsMap;
/*     */   private SortHelper helper;
/*     */ 
/*     */   public ObservableListWrapper(List<E> paramList)
/*     */   {
/*  67 */     this.backingList = paramList;
/*     */   }
/*     */ 
/*     */   public ObservableListWrapper(List<E> paramList, Callback<E, Observable[]> paramCallback) {
/*  71 */     this(paramList);
/*  72 */     this.extractor = paramCallback;
/*  73 */     this.elementsMap = new IdentityHashMap();
/*     */   }
/*     */ 
/*     */   public void addListener(InvalidationListener paramInvalidationListener)
/*     */   {
/*  78 */     this.listenerHelper = ListListenerHelper.addListener(this.listenerHelper, paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(InvalidationListener paramInvalidationListener)
/*     */   {
/*  83 */     this.listenerHelper = ListListenerHelper.removeListener(this.listenerHelper, paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public void addListener(ListChangeListener<? super E> paramListChangeListener)
/*     */   {
/*  88 */     this.listenerHelper = ListListenerHelper.addListener(this.listenerHelper, paramListChangeListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(ListChangeListener<? super E> paramListChangeListener)
/*     */   {
/*  93 */     this.listenerHelper = ListListenerHelper.removeListener(this.listenerHelper, paramListChangeListener);
/*     */   }
/*     */ 
/*     */   protected void callObservers(ListChangeListener.Change<E> paramChange) {
/*  97 */     ListListenerHelper.fireValueChangedEvent(this.listenerHelper, paramChange);
/*     */   }
/*     */ 
/*     */   private void attachListener(final E paramE) {
/* 101 */     if (paramE != null)
/* 102 */       if (this.elementsMap.containsKey(paramE)) {
/* 103 */         ((ElementsMapElement)this.elementsMap.get(paramE)).increment();
/*     */       } else {
/* 105 */         InvalidationListener local1 = new InvalidationListener()
/*     */         {
/*     */           public void invalidated(Observable paramAnonymousObservable)
/*     */           {
/* 109 */             IterableChangeBuilder localIterableChangeBuilder = new IterableChangeBuilder(ObservableListWrapper.this);
/* 110 */             int i = ObservableListWrapper.this.size();
/* 111 */             for (int j = 0; j < i; j++) {
/* 112 */               if (ObservableListWrapper.this.get(j) == paramE) {
/* 113 */                 localIterableChangeBuilder.nextUpdate(j);
/*     */               }
/*     */             }
/* 116 */             ObservableListWrapper.this.callObservers(localIterableChangeBuilder.build());
/*     */           }
/*     */         };
/* 119 */         for (Observable localObservable : (Observable[])this.extractor.call(paramE)) {
/* 120 */           localObservable.addListener(local1);
/*     */         }
/* 122 */         this.elementsMap.put(paramE, new ElementsMapElement(local1));
/*     */       }
/*     */   }
/*     */ 
/*     */   private void detachListener(E paramE)
/*     */   {
/* 128 */     if (paramE != null) {
/* 129 */       ElementsMapElement localElementsMapElement = (ElementsMapElement)this.elementsMap.get(paramE);
/* 130 */       for (Observable localObservable : (Observable[])this.extractor.call(paramE)) {
/* 131 */         localObservable.removeListener(localElementsMapElement.getListener());
/*     */       }
/* 133 */       if (localElementsMapElement.decrement() == 0)
/* 134 */         this.elementsMap.remove(paramE);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean add(E paramE)
/*     */   {
/* 144 */     add(size(), paramE);
/* 145 */     return true;
/*     */   }
/*     */ 
/*     */   public void add(int paramInt, E paramE)
/*     */   {
/* 150 */     this.backingList.add(paramInt, paramE);
/* 151 */     if (this.extractor != null) {
/* 152 */       attachListener(paramE);
/*     */     }
/* 154 */     callObservers(new NonIterableChange.SimpleAddChange(paramInt, paramInt + 1, this));
/*     */   }
/*     */ 
/*     */   public boolean addAll(Collection<? extends E> paramCollection)
/*     */   {
/* 159 */     int i = size();
/* 160 */     return addAll(i, paramCollection);
/*     */   }
/*     */ 
/*     */   public boolean addAll(int paramInt, Collection<? extends E> paramCollection)
/*     */   {
/* 165 */     boolean bool = this.backingList.addAll(paramInt, paramCollection);
/*     */     Iterator localIterator;
/* 166 */     if (this.extractor != null) {
/* 167 */       for (localIterator = paramCollection.iterator(); localIterator.hasNext(); ) { Object localObject = localIterator.next();
/* 168 */         attachListener(localObject);
/*     */       }
/*     */     }
/* 171 */     callObservers(new NonIterableChange.SimpleAddChange(paramInt, paramInt + paramCollection.size(), this));
/* 172 */     return bool;
/*     */   }
/*     */ 
/*     */   public void clear()
/*     */   {
/* 177 */     List localList = (hasObserver()) || (this.extractor != null) ? Collections.unmodifiableList(new ArrayList(this.backingList)) : null;
/* 178 */     this.backingList.clear();
/*     */     Iterator localIterator;
/* 179 */     if (this.extractor != null) {
/* 180 */       for (localIterator = localList.iterator(); localIterator.hasNext(); ) { Object localObject = localIterator.next();
/* 181 */         detachListener(localObject);
/*     */       }
/*     */     }
/* 184 */     callObservers(new NonIterableChange.GenericAddRemoveChange(0, 0, localList, this));
/*     */   }
/*     */ 
/*     */   protected boolean hasObserver() {
/* 188 */     return ListListenerHelper.hasListeners(this.listenerHelper);
/*     */   }
/*     */ 
/*     */   public boolean contains(Object paramObject)
/*     */   {
/* 193 */     return this.backingList.contains(paramObject);
/*     */   }
/*     */ 
/*     */   public boolean containsAll(Collection<?> paramCollection)
/*     */   {
/* 198 */     return this.backingList.containsAll(paramCollection);
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 203 */     return this.backingList.equals(paramObject);
/*     */   }
/*     */ 
/*     */   public E get(int paramInt)
/*     */   {
/* 208 */     return this.backingList.get(paramInt);
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 213 */     return this.backingList.hashCode();
/*     */   }
/*     */ 
/*     */   public int indexOf(Object paramObject)
/*     */   {
/* 218 */     return this.backingList.indexOf(paramObject);
/*     */   }
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/* 223 */     return this.backingList.isEmpty();
/*     */   }
/*     */ 
/*     */   public Iterator<E> iterator()
/*     */   {
/* 228 */     return new ObservableListIterator(this.backingList.iterator(), 0, 0);
/*     */   }
/*     */ 
/*     */   public int lastIndexOf(Object paramObject)
/*     */   {
/* 233 */     return this.backingList.lastIndexOf(paramObject);
/*     */   }
/*     */ 
/*     */   public ListIterator<E> listIterator()
/*     */   {
/* 238 */     return new ObservableListIterator(this.backingList.listIterator(), 0, 0);
/*     */   }
/*     */ 
/*     */   public ListIterator<E> listIterator(int paramInt)
/*     */   {
/* 243 */     if ((paramInt < 0) || (paramInt > this.backingList.size())) {
/* 244 */       throw new IndexOutOfBoundsException();
/*     */     }
/* 246 */     return new ObservableListIterator(this.backingList.listIterator(paramInt), 0, paramInt);
/*     */   }
/*     */ 
/*     */   public E remove(int paramInt)
/*     */   {
/* 251 */     Object localObject = this.backingList.remove(paramInt);
/* 252 */     if (this.extractor != null) {
/* 253 */       detachListener(localObject);
/*     */     }
/* 255 */     callObservers(new NonIterableChange.SimpleRemovedChange(paramInt, paramInt, localObject, this));
/* 256 */     return localObject;
/*     */   }
/*     */ 
/*     */   public boolean remove(Object paramObject)
/*     */   {
/* 261 */     int i = indexOf(paramObject);
/* 262 */     if (i != -1) {
/* 263 */       remove(i);
/* 264 */       return true;
/*     */     }
/* 266 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean removeAll(Collection<?> paramCollection)
/*     */   {
/* 271 */     return removeFromList(this.backingList, 0, paramCollection, false);
/*     */   }
/*     */ 
/*     */   public boolean retainAll(Collection<?> paramCollection)
/*     */   {
/* 276 */     return removeFromList(this.backingList, 0, paramCollection, true);
/*     */   }
/*     */ 
/*     */   public E set(int paramInt, E paramE)
/*     */   {
/* 281 */     Object localObject = this.backingList.set(paramInt, paramE);
/* 282 */     if (this.extractor != null) {
/* 283 */       detachListener(localObject);
/* 284 */       attachListener(paramE);
/*     */     }
/* 286 */     callObservers(new NonIterableChange.SimpleRemovedChange(paramInt, paramInt + 1, localObject, this));
/* 287 */     return localObject;
/*     */   }
/*     */ 
/*     */   public boolean setAll(Collection<? extends E> paramCollection)
/*     */   {
/* 292 */     List localList = Collections.unmodifiableList(new ArrayList(this.backingList));
/* 293 */     this.backingList.clear();
/* 294 */     this.backingList.addAll(paramCollection);
/*     */     Iterator localIterator;
/*     */     Object localObject;
/* 295 */     if (this.extractor != null) {
/* 296 */       for (localIterator = localList.iterator(); localIterator.hasNext(); ) { localObject = localIterator.next();
/* 297 */         detachListener(localObject);
/*     */       }
/* 299 */       for (localIterator = paramCollection.iterator(); localIterator.hasNext(); ) { localObject = localIterator.next();
/* 300 */         attachListener(localObject);
/*     */       }
/*     */     }
/* 303 */     callObservers(new NonIterableChange.GenericAddRemoveChange(0, paramCollection.size(), localList, this));
/* 304 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean addAll(E[] paramArrayOfE)
/*     */   {
/* 309 */     return addAll(Arrays.asList(paramArrayOfE));
/*     */   }
/*     */ 
/*     */   public boolean setAll(E[] paramArrayOfE)
/*     */   {
/* 314 */     return setAll(Arrays.asList(paramArrayOfE));
/*     */   }
/*     */ 
/*     */   public boolean removeAll(E[] paramArrayOfE)
/*     */   {
/* 319 */     return removeAll(Arrays.asList(paramArrayOfE));
/*     */   }
/*     */ 
/*     */   public boolean retainAll(E[] paramArrayOfE)
/*     */   {
/* 324 */     return retainAll(Arrays.asList(paramArrayOfE));
/*     */   }
/*     */ 
/*     */   public void remove(int paramInt1, int paramInt2)
/*     */   {
/* 329 */     subList(paramInt1, paramInt2).clear();
/*     */   }
/*     */ 
/*     */   public int size()
/*     */   {
/* 335 */     return this.backingList.size();
/*     */   }
/*     */ 
/*     */   public List<E> subList(int paramInt1, int paramInt2)
/*     */   {
/* 340 */     return new ObservableSubList(paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   public Object[] toArray()
/*     */   {
/* 345 */     return this.backingList.toArray();
/*     */   }
/*     */ 
/*     */   public <T> T[] toArray(T[] paramArrayOfT)
/*     */   {
/* 350 */     return this.backingList.toArray(paramArrayOfT);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 355 */     return this.backingList.toString();
/*     */   }
/*     */ 
/*     */   protected boolean removeFromList(List<E> paramList, int paramInt, Collection<?> paramCollection, boolean paramBoolean)
/*     */   {
/* 368 */     IterableChangeBuilder localIterableChangeBuilder = new IterableChangeBuilder(this);
/* 369 */     for (int i = 0; i < paramList.size(); i++) {
/* 370 */       Object localObject1 = paramList.get(i);
/* 371 */       if ((paramCollection.contains(localObject1) ^ paramBoolean)) {
/* 372 */         localIterableChangeBuilder.nextRemove(paramInt + i, localObject1);
/* 373 */         Object localObject2 = paramList.remove(i);
/* 374 */         if (this.extractor != null) {
/* 375 */           detachListener(localObject2);
/*     */         }
/* 377 */         i--;
/*     */       }
/*     */     }
/* 380 */     if (localIterableChangeBuilder.isEmpty()) {
/* 381 */       return false;
/*     */     }
/* 383 */     callObservers(localIterableChangeBuilder.buildAndReset());
/* 384 */     return true;
/*     */   }
/*     */ 
/*     */   public void sort()
/*     */   {
/* 390 */     if (this.backingList.isEmpty()) {
/* 391 */       return;
/*     */     }
/* 393 */     int[] arrayOfInt = getSortHelper().sort(this.backingList);
/* 394 */     callObservers(new NonIterableChange.SimplePermutationChange(0, size(), arrayOfInt, this));
/*     */   }
/*     */ 
/*     */   public void sort(Comparator<? super E> paramComparator)
/*     */   {
/* 399 */     if (this.backingList.isEmpty()) {
/* 400 */       return;
/*     */     }
/* 402 */     int[] arrayOfInt = getSortHelper().sort(this.backingList, paramComparator);
/* 403 */     callObservers(new NonIterableChange.SimplePermutationChange(0, size(), arrayOfInt, this));
/*     */   }
/*     */ 
/*     */   private SortHelper getSortHelper() {
/* 407 */     if (this.helper == null) {
/* 408 */       this.helper = new SortHelper();
/*     */     }
/* 410 */     return this.helper;
/*     */   }
/*     */ 
/*     */   class ObservableListIterator
/*     */     implements ListIterator<E>
/*     */   {
/*     */     Iterator<E> backingIt;
/*     */     ListIterator<E> backingListIt;
/*     */     int cursor;
/*     */     E current;
/* 640 */     boolean forward = true;
/*     */ 
/*     */     ObservableListIterator(int paramInt1, int arg3)
/*     */     {
/* 650 */       this.backingIt = paramInt1;
/*     */       int i;
/*     */       int j;
/* 651 */       this.cursor = (i + j);
/*     */     }
/*     */ 
/*     */     ObservableListIterator(int paramInt1, int arg3) {
/* 655 */       this(paramInt1, i, j);
/* 656 */       this.backingListIt = paramInt1;
/*     */     }
/*     */ 
/*     */     public void add(E paramE)
/*     */     {
/* 661 */       this.backingListIt.add(paramE);
/* 662 */       this.cursor += 1;
/* 663 */       if (ObservableListWrapper.this.extractor != null) {
/* 664 */         ObservableListWrapper.this.attachListener(paramE);
/*     */       }
/* 666 */       ObservableListWrapper.this.callObservers(new NonIterableChange.SimpleAddChange(this.cursor - 1, this.cursor, ObservableListWrapper.this));
/*     */     }
/*     */ 
/*     */     public boolean hasNext()
/*     */     {
/* 671 */       return this.backingIt.hasNext();
/*     */     }
/*     */ 
/*     */     public boolean hasPrevious()
/*     */     {
/* 676 */       return this.backingListIt.hasPrevious();
/*     */     }
/*     */ 
/*     */     public E next()
/*     */     {
/* 681 */       this.current = this.backingIt.next();
/* 682 */       this.cursor += 1;
/* 683 */       this.forward = true;
/* 684 */       return this.current;
/*     */     }
/*     */ 
/*     */     public int nextIndex()
/*     */     {
/* 689 */       return this.backingListIt.nextIndex();
/*     */     }
/*     */ 
/*     */     public E previous()
/*     */     {
/* 694 */       this.current = this.backingListIt.previous();
/* 695 */       this.cursor -= 1;
/* 696 */       this.forward = false;
/* 697 */       return this.current;
/*     */     }
/*     */ 
/*     */     public int previousIndex()
/*     */     {
/* 702 */       return this.backingListIt.previousIndex();
/*     */     }
/*     */ 
/*     */     public void remove()
/*     */     {
/* 707 */       this.backingIt.remove();
/* 708 */       if (this.forward) {
/* 709 */         this.cursor -= 1;
/*     */       }
/* 711 */       if (ObservableListWrapper.this.extractor != null) {
/* 712 */         ObservableListWrapper.this.detachListener(this.current);
/*     */       }
/* 714 */       ObservableListWrapper.this.callObservers(new NonIterableChange.SimpleRemovedChange(this.cursor, this.cursor, this.current, ObservableListWrapper.this));
/*     */     }
/*     */ 
/*     */     public void set(E paramE)
/*     */     {
/* 720 */       this.backingListIt.set(paramE);
/* 721 */       if (ObservableListWrapper.this.extractor != null) {
/* 722 */         ObservableListWrapper.this.detachListener(this.current);
/* 723 */         ObservableListWrapper.this.attachListener(paramE);
/*     */       }
/* 725 */       if (this.forward)
/* 726 */         ObservableListWrapper.this.callObservers(new NonIterableChange.SimpleRemovedChange(this.cursor - 1, this.cursor, this.current, ObservableListWrapper.this));
/*     */       else
/* 728 */         ObservableListWrapper.this.callObservers(new NonIterableChange.SimpleRemovedChange(this.cursor, this.cursor + 1, this.current, ObservableListWrapper.this));
/*     */     }
/*     */   }
/*     */ 
/*     */   class ObservableSubList
/*     */     implements List<E>
/*     */   {
/*     */     int offset;
/*     */     int sz;
/*     */     List<E> backingSubList;
/*     */ 
/*     */     ObservableSubList(int paramInt1, int arg3)
/*     */     {
/*     */       int i;
/* 421 */       if ((paramInt1 < 0) || (i > ObservableListWrapper.this.size())) {
/* 422 */         throw new IndexOutOfBoundsException();
/*     */       }
/* 424 */       if (paramInt1 > i) {
/* 425 */         throw new IllegalArgumentException();
/*     */       }
/* 427 */       this.offset = paramInt1;
/* 428 */       this.sz = (i - paramInt1);
/* 429 */       this.backingSubList = ObservableListWrapper.this.backingList.subList(this.offset, this.offset + this.sz);
/*     */     }
/*     */ 
/*     */     public boolean add(E paramE)
/*     */     {
/* 435 */       add(this.sz, paramE);
/* 436 */       return true;
/*     */     }
/*     */ 
/*     */     public void add(int paramInt, E paramE)
/*     */     {
/* 441 */       this.backingSubList.add(paramInt, paramE);
/* 442 */       this.sz += 1;
/* 443 */       if (ObservableListWrapper.this.extractor != null) {
/* 444 */         ObservableListWrapper.this.attachListener(paramE);
/*     */       }
/* 446 */       ObservableListWrapper.this.callObservers(new NonIterableChange.SimpleAddChange(this.offset + paramInt, this.offset + paramInt + 1, ObservableListWrapper.this));
/*     */     }
/*     */ 
/*     */     public boolean addAll(Collection<? extends E> paramCollection)
/*     */     {
/* 451 */       return addAll(this.sz, paramCollection);
/*     */     }
/*     */ 
/*     */     public boolean addAll(int paramInt, Collection<? extends E> paramCollection)
/*     */     {
/* 456 */       boolean bool = this.backingSubList.addAll(paramInt, paramCollection);
/* 457 */       if (bool) {
/* 458 */         this.sz += paramCollection.size();
/*     */         Iterator localIterator;
/* 459 */         if (ObservableListWrapper.this.extractor != null) {
/* 460 */           for (localIterator = paramCollection.iterator(); localIterator.hasNext(); ) { Object localObject = localIterator.next();
/* 461 */             ObservableListWrapper.this.attachListener(localObject);
/*     */           }
/*     */         }
/* 464 */         ObservableListWrapper.this.callObservers(new NonIterableChange.SimpleAddChange(this.offset + paramInt, this.offset + paramInt + paramCollection.size(), ObservableListWrapper.this));
/*     */       }
/* 466 */       return bool;
/*     */     }
/*     */ 
/*     */     public void clear()
/*     */     {
/* 471 */       List localList = (ObservableListWrapper.this.hasObserver()) || (ObservableListWrapper.this.extractor != null) ? Collections.unmodifiableList(new ArrayList(this.backingSubList)) : null;
/* 472 */       this.backingSubList.clear();
/* 473 */       this.sz = 0;
/*     */       Iterator localIterator;
/* 474 */       if (ObservableListWrapper.this.extractor != null) {
/* 475 */         for (localIterator = localList.iterator(); localIterator.hasNext(); ) { Object localObject = localIterator.next();
/* 476 */           ObservableListWrapper.this.detachListener(localObject);
/*     */         }
/*     */       }
/* 479 */       ObservableListWrapper.this.callObservers(new NonIterableChange.GenericAddRemoveChange(this.offset, this.offset, localList, ObservableListWrapper.this));
/*     */     }
/*     */ 
/*     */     public boolean contains(Object paramObject)
/*     */     {
/* 484 */       return indexOf(paramObject) >= 0;
/*     */     }
/*     */ 
/*     */     public boolean containsAll(Collection<?> paramCollection)
/*     */     {
/* 489 */       return this.backingSubList.containsAll(paramCollection);
/*     */     }
/*     */ 
/*     */     public boolean equals(Object paramObject)
/*     */     {
/* 494 */       return this.backingSubList.equals(paramObject);
/*     */     }
/*     */ 
/*     */     public E get(int paramInt)
/*     */     {
/* 499 */       return this.backingSubList.get(paramInt);
/*     */     }
/*     */ 
/*     */     public int hashCode()
/*     */     {
/* 504 */       return this.backingSubList.hashCode();
/*     */     }
/*     */ 
/*     */     public int indexOf(Object paramObject)
/*     */     {
/* 509 */       return this.backingSubList.indexOf(paramObject);
/*     */     }
/*     */ 
/*     */     public boolean isEmpty()
/*     */     {
/* 514 */       return this.backingSubList.isEmpty();
/*     */     }
/*     */ 
/*     */     public Iterator<E> iterator()
/*     */     {
/* 519 */       return new ObservableListWrapper.ObservableListIterator(ObservableListWrapper.this, this.backingSubList.iterator(), this.offset, 0);
/*     */     }
/*     */ 
/*     */     public int lastIndexOf(Object paramObject)
/*     */     {
/* 524 */       return this.backingSubList.lastIndexOf(paramObject);
/*     */     }
/*     */ 
/*     */     public ListIterator<E> listIterator()
/*     */     {
/* 529 */       return new ObservableListWrapper.ObservableListIterator(ObservableListWrapper.this, this.backingSubList.listIterator(), this.offset, 0);
/*     */     }
/*     */ 
/*     */     public ListIterator<E> listIterator(int paramInt)
/*     */     {
/* 534 */       return new ObservableListWrapper.ObservableListIterator(ObservableListWrapper.this, this.backingSubList.listIterator(paramInt), this.offset, paramInt);
/*     */     }
/*     */ 
/*     */     public E remove(int paramInt)
/*     */     {
/* 539 */       Object localObject = this.backingSubList.remove(paramInt);
/* 540 */       if (localObject != null) {
/* 541 */         this.sz -= 1;
/* 542 */         if (ObservableListWrapper.this.extractor != null) {
/* 543 */           ObservableListWrapper.this.detachListener(localObject);
/*     */         }
/* 545 */         ObservableListWrapper.this.callObservers(new NonIterableChange.SimpleRemovedChange(this.offset + paramInt, this.offset + paramInt, localObject, ObservableListWrapper.this));
/*     */       }
/* 547 */       return localObject;
/*     */     }
/*     */ 
/*     */     public boolean remove(Object paramObject)
/*     */     {
/* 552 */       for (int i = 0; i < this.sz; i++) {
/* 553 */         Object localObject = get(i);
/* 554 */         if (((localObject == null) && (paramObject == null)) || ((localObject != null) && (localObject.equals(paramObject))))
/*     */         {
/* 556 */           remove(i);
/* 557 */           return true;
/*     */         }
/*     */       }
/* 560 */       return false;
/*     */     }
/*     */ 
/*     */     public boolean removeAll(Collection<?> paramCollection)
/*     */     {
/* 565 */       boolean bool = ObservableListWrapper.this.removeFromList(this.backingSubList, this.offset, paramCollection, false);
/* 566 */       this.sz = this.backingSubList.size();
/* 567 */       return bool;
/*     */     }
/*     */ 
/*     */     public boolean retainAll(Collection<?> paramCollection)
/*     */     {
/* 572 */       boolean bool = ObservableListWrapper.this.removeFromList(this.backingSubList, this.offset, paramCollection, true);
/* 573 */       this.sz = this.backingSubList.size();
/* 574 */       return bool;
/*     */     }
/*     */ 
/*     */     public E set(int paramInt, E paramE)
/*     */     {
/* 579 */       Object localObject = this.backingSubList.set(paramInt, paramE);
/* 580 */       if (ObservableListWrapper.this.extractor != null) {
/* 581 */         ObservableListWrapper.this.detachListener(localObject);
/* 582 */         ObservableListWrapper.this.attachListener(paramE);
/*     */       }
/* 584 */       ObservableListWrapper.this.callObservers(new NonIterableChange.SimpleRemovedChange(this.offset + paramInt, this.offset + paramInt + 1, localObject, ObservableListWrapper.this));
/* 585 */       return localObject;
/*     */     }
/*     */ 
/*     */     public int size()
/*     */     {
/* 590 */       return this.backingSubList.size();
/*     */     }
/*     */ 
/*     */     public List<E> subList(int paramInt1, int paramInt2)
/*     */     {
/* 595 */       rangeCheck(paramInt1);
/* 596 */       if (paramInt2 < paramInt1) {
/* 597 */         throw new IllegalArgumentException();
/*     */       }
/* 599 */       return ObservableListWrapper.this.subList(this.offset + paramInt1, this.offset + paramInt2);
/*     */     }
/*     */ 
/*     */     public Object[] toArray()
/*     */     {
/* 604 */       return this.backingSubList.toArray();
/*     */     }
/*     */ 
/*     */     public <T> T[] toArray(T[] paramArrayOfT)
/*     */     {
/* 609 */       return this.backingSubList.toArray(paramArrayOfT);
/*     */     }
/*     */ 
/*     */     public String toString()
/*     */     {
/* 614 */       return this.backingSubList.toString();
/*     */     }
/*     */ 
/*     */     private void rangeCheck(int paramInt)
/*     */     {
/* 619 */       if ((paramInt < 0) || (paramInt >= this.sz))
/* 620 */         throw new IndexOutOfBoundsException(outOfBoundsMsg(paramInt));
/*     */     }
/*     */ 
/*     */     private String outOfBoundsMsg(int paramInt)
/*     */     {
/* 625 */       return "Index: " + paramInt + ", Size: " + this.sz;
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class ElementsMapElement
/*     */   {
/*     */     InvalidationListener listener;
/*     */     int counter;
/*     */ 
/*     */     public ElementsMapElement(InvalidationListener paramInvalidationListener)
/*     */     {
/*  40 */       this.listener = paramInvalidationListener;
/*  41 */       this.counter = 1;
/*     */     }
/*     */ 
/*     */     public void increment() {
/*  45 */       this.counter += 1;
/*     */     }
/*     */ 
/*     */     public int decrement() {
/*  49 */       return --this.counter;
/*     */     }
/*     */ 
/*     */     private InvalidationListener getListener() {
/*  53 */       return this.listener;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.collections.ObservableListWrapper
 * JD-Core Version:    0.6.2
 */