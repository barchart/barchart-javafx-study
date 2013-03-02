/*     */ package com.sun.javafx.collections;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.collections.MapChangeListener;
/*     */ import javafx.collections.MapChangeListener.Change;
/*     */ import javafx.collections.ObservableMap;
/*     */ 
/*     */ public class ObservableMapWrapper<K, V>
/*     */   implements ObservableMap<K, V>
/*     */ {
/*     */   private ObservableMapWrapper<K, V>.ObservableEntrySet entrySet;
/*     */   private ObservableMapWrapper<K, V>.ObservableKeySet keySet;
/*     */   private ObservableMapWrapper<K, V>.ObservableValues values;
/*     */   private MapListenerHelper<K, V> listenerHelper;
/*     */   private final Map<K, V> backingMap;
/*     */ 
/*     */   public ObservableMapWrapper(Map<K, V> paramMap)
/*     */   {
/*  50 */     this.backingMap = paramMap;
/*     */   }
/*     */ 
/*     */   protected void callObservers(MapChangeListener.Change<K, V> paramChange)
/*     */   {
/*  94 */     MapListenerHelper.fireValueChangedEvent(this.listenerHelper, paramChange);
/*     */   }
/*     */ 
/*     */   public void addListener(InvalidationListener paramInvalidationListener)
/*     */   {
/*  99 */     this.listenerHelper = MapListenerHelper.addListener(this.listenerHelper, paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(InvalidationListener paramInvalidationListener)
/*     */   {
/* 104 */     this.listenerHelper = MapListenerHelper.removeListener(this.listenerHelper, paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public void addListener(MapChangeListener<? super K, ? super V> paramMapChangeListener)
/*     */   {
/* 109 */     this.listenerHelper = MapListenerHelper.addListener(this.listenerHelper, paramMapChangeListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(MapChangeListener<? super K, ? super V> paramMapChangeListener)
/*     */   {
/* 114 */     this.listenerHelper = MapListenerHelper.removeListener(this.listenerHelper, paramMapChangeListener);
/*     */   }
/*     */ 
/*     */   public int size()
/*     */   {
/* 119 */     return this.backingMap.size();
/*     */   }
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/* 124 */     return this.backingMap.isEmpty();
/*     */   }
/*     */ 
/*     */   public boolean containsKey(Object paramObject)
/*     */   {
/* 129 */     return this.backingMap.containsKey(paramObject);
/*     */   }
/*     */ 
/*     */   public boolean containsValue(Object paramObject)
/*     */   {
/* 134 */     return this.backingMap.containsValue(paramObject);
/*     */   }
/*     */ 
/*     */   public V get(Object paramObject)
/*     */   {
/* 139 */     return this.backingMap.get(paramObject);
/*     */   }
/*     */ 
/*     */   public V put(K paramK, V paramV)
/*     */   {
/* 144 */     Object localObject = this.backingMap.put(paramK, paramV);
/* 145 */     if ((localObject == null) || (!localObject.equals(paramV))) {
/* 146 */       callObservers(new SimpleChange(paramK, localObject, paramV));
/*     */     }
/* 148 */     return localObject;
/*     */   }
/*     */ 
/*     */   public V remove(Object paramObject)
/*     */   {
/* 154 */     Object localObject = this.backingMap.remove(paramObject);
/* 155 */     if (localObject != null) {
/* 156 */       callObservers(new SimpleChange(paramObject, localObject, null));
/*     */     }
/* 158 */     return localObject;
/*     */   }
/*     */ 
/*     */   public void putAll(Map<? extends K, ? extends V> paramMap)
/*     */   {
/* 163 */     for (Map.Entry localEntry : paramMap.entrySet())
/* 164 */       put(localEntry.getKey(), localEntry.getValue());
/*     */   }
/*     */ 
/*     */   public void clear()
/*     */   {
/* 170 */     for (Iterator localIterator = this.backingMap.entrySet().iterator(); localIterator.hasNext(); ) {
/* 171 */       Map.Entry localEntry = (Map.Entry)localIterator.next();
/* 172 */       Object localObject1 = localEntry.getKey();
/* 173 */       Object localObject2 = localEntry.getValue();
/* 174 */       localIterator.remove();
/* 175 */       callObservers(new SimpleChange(localObject1, localObject2, null));
/*     */     }
/*     */   }
/*     */ 
/*     */   public Set<K> keySet()
/*     */   {
/* 181 */     if (this.keySet == null) {
/* 182 */       this.keySet = new ObservableKeySet(null);
/*     */     }
/* 184 */     return this.keySet;
/*     */   }
/*     */ 
/*     */   public Collection<V> values()
/*     */   {
/* 189 */     if (this.values == null) {
/* 190 */       this.values = new ObservableValues(null);
/*     */     }
/* 192 */     return this.values;
/*     */   }
/*     */ 
/*     */   public Set<Map.Entry<K, V>> entrySet()
/*     */   {
/* 197 */     if (this.entrySet == null) {
/* 198 */       this.entrySet = new ObservableEntrySet(null);
/*     */     }
/* 200 */     return this.entrySet;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 205 */     return this.backingMap.toString();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 210 */     return this.backingMap.equals(paramObject);
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 215 */     return this.backingMap.hashCode();
/*     */   }
/*     */ 
/*     */   private class ObservableEntrySet
/*     */     implements Set<Map.Entry<K, V>>
/*     */   {
/*     */     private ObservableEntrySet()
/*     */     {
/*     */     }
/*     */ 
/*     */     public int size()
/*     */     {
/* 533 */       return ObservableMapWrapper.this.backingMap.size();
/*     */     }
/*     */ 
/*     */     public boolean isEmpty()
/*     */     {
/* 538 */       return ObservableMapWrapper.this.backingMap.isEmpty();
/*     */     }
/*     */ 
/*     */     public boolean contains(Object paramObject)
/*     */     {
/* 543 */       return ObservableMapWrapper.this.backingMap.entrySet().contains(paramObject);
/*     */     }
/*     */ 
/*     */     public Iterator<Map.Entry<K, V>> iterator()
/*     */     {
/* 548 */       return new Iterator() {
/* 550 */         private Iterator<Map.Entry<K, V>> backingIt = ObservableMapWrapper.this.backingMap.entrySet().iterator();
/*     */         private K lastKey;
/*     */         private V lastValue;
/*     */ 
/* 555 */         public boolean hasNext() { return this.backingIt.hasNext(); }
/*     */ 
/*     */ 
/*     */         public Map.Entry<K, V> next()
/*     */         {
/* 560 */           Map.Entry localEntry = (Map.Entry)this.backingIt.next();
/* 561 */           this.lastKey = localEntry.getKey();
/* 562 */           this.lastValue = localEntry.getValue();
/* 563 */           return new ObservableMapWrapper.ObservableEntry(ObservableMapWrapper.this, localEntry);
/*     */         }
/*     */ 
/*     */         public void remove()
/*     */         {
/* 568 */           this.backingIt.remove();
/* 569 */           ObservableMapWrapper.this.callObservers(new ObservableMapWrapper.SimpleChange(ObservableMapWrapper.this, this.lastKey, this.lastValue, null));
/*     */         }
/*     */       };
/*     */     }
/*     */ 
/*     */     public Object[] toArray()
/*     */     {
/* 577 */       Object[] arrayOfObject = ObservableMapWrapper.this.backingMap.entrySet().toArray();
/* 578 */       for (int i = 0; i < arrayOfObject.length; i++) {
/* 579 */         arrayOfObject[i] = new ObservableMapWrapper.ObservableEntry(ObservableMapWrapper.this, (Map.Entry)arrayOfObject[i]);
/*     */       }
/* 581 */       return arrayOfObject;
/*     */     }
/*     */ 
/*     */     public <T> T[] toArray(T[] paramArrayOfT)
/*     */     {
/* 587 */       Object[] arrayOfObject = ObservableMapWrapper.this.backingMap.entrySet().toArray(paramArrayOfT);
/* 588 */       for (int i = 0; i < arrayOfObject.length; i++) {
/* 589 */         arrayOfObject[i] = new ObservableMapWrapper.ObservableEntry(ObservableMapWrapper.this, (Map.Entry)arrayOfObject[i]);
/*     */       }
/* 591 */       return arrayOfObject;
/*     */     }
/*     */ 
/*     */     public boolean add(Map.Entry<K, V> paramEntry)
/*     */     {
/* 596 */       throw new UnsupportedOperationException("Not supported.");
/*     */     }
/*     */ 
/*     */     public boolean remove(Object paramObject)
/*     */     {
/* 602 */       boolean bool = ObservableMapWrapper.this.backingMap.entrySet().remove(paramObject);
/* 603 */       if (bool) {
/* 604 */         Map.Entry localEntry = (Map.Entry)paramObject;
/* 605 */         ObservableMapWrapper.this.callObservers(new ObservableMapWrapper.SimpleChange(ObservableMapWrapper.this, localEntry.getKey(), localEntry.getValue(), null));
/*     */       }
/* 607 */       return bool;
/*     */     }
/*     */ 
/*     */     public boolean containsAll(Collection<?> paramCollection)
/*     */     {
/* 612 */       return ObservableMapWrapper.this.backingMap.entrySet().containsAll(paramCollection);
/*     */     }
/*     */ 
/*     */     public boolean addAll(Collection<? extends Map.Entry<K, V>> paramCollection)
/*     */     {
/* 617 */       throw new UnsupportedOperationException("Not supported.");
/*     */     }
/*     */ 
/*     */     public boolean retainAll(Collection<?> paramCollection)
/*     */     {
/* 622 */       return removeRetain(paramCollection, false);
/*     */     }
/*     */ 
/*     */     private boolean removeRetain(Collection<?> paramCollection, boolean paramBoolean) {
/* 626 */       boolean bool = false;
/* 627 */       for (Iterator localIterator = ObservableMapWrapper.this.backingMap.entrySet().iterator(); localIterator.hasNext(); ) {
/* 628 */         Map.Entry localEntry = (Map.Entry)localIterator.next();
/* 629 */         if (paramBoolean == paramCollection.contains(localEntry)) {
/* 630 */           bool = true;
/* 631 */           Object localObject1 = localEntry.getKey();
/* 632 */           Object localObject2 = localEntry.getValue();
/* 633 */           localIterator.remove();
/* 634 */           ObservableMapWrapper.this.callObservers(new ObservableMapWrapper.SimpleChange(ObservableMapWrapper.this, localObject1, localObject2, null));
/*     */         }
/*     */       }
/* 637 */       return bool;
/*     */     }
/*     */ 
/*     */     public boolean removeAll(Collection<?> paramCollection)
/*     */     {
/* 642 */       return removeRetain(paramCollection, true);
/*     */     }
/*     */ 
/*     */     public void clear()
/*     */     {
/* 647 */       ObservableMapWrapper.this.clear();
/*     */     }
/*     */ 
/*     */     public String toString()
/*     */     {
/* 652 */       return ObservableMapWrapper.this.backingMap.entrySet().toString();
/*     */     }
/*     */ 
/*     */     public boolean equals(Object paramObject)
/*     */     {
/* 657 */       return ObservableMapWrapper.this.backingMap.entrySet().equals(paramObject);
/*     */     }
/*     */ 
/*     */     public int hashCode()
/*     */     {
/* 662 */       return ObservableMapWrapper.this.backingMap.entrySet().hashCode();
/*     */     }
/*     */   }
/*     */ 
/*     */   private class ObservableEntry
/*     */     implements Map.Entry<K, V>
/*     */   {
/*     */     private final Map.Entry<K, V> backingEntry;
/*     */ 
/*     */     public ObservableEntry()
/*     */     {
/*     */       Object localObject;
/* 478 */       this.backingEntry = localObject;
/*     */     }
/*     */ 
/*     */     public K getKey()
/*     */     {
/* 483 */       return this.backingEntry.getKey();
/*     */     }
/*     */ 
/*     */     public V getValue()
/*     */     {
/* 488 */       return this.backingEntry.getValue();
/*     */     }
/*     */ 
/*     */     public V setValue(V paramV)
/*     */     {
/* 493 */       Object localObject = this.backingEntry.setValue(paramV);
/* 494 */       ObservableMapWrapper.this.callObservers(new ObservableMapWrapper.SimpleChange(ObservableMapWrapper.this, getKey(), localObject, paramV));
/* 495 */       return localObject;
/*     */     }
/*     */ 
/*     */     public final boolean equals(Object paramObject)
/*     */     {
/* 500 */       if (!(paramObject instanceof Map.Entry)) {
/* 501 */         return false;
/*     */       }
/* 503 */       Map.Entry localEntry = (Map.Entry)paramObject;
/* 504 */       Object localObject1 = getKey();
/* 505 */       Object localObject2 = localEntry.getKey();
/* 506 */       if ((localObject1 == localObject2) || ((localObject1 != null) && (localObject1.equals(localObject2)))) {
/* 507 */         Object localObject3 = getValue();
/* 508 */         Object localObject4 = localEntry.getValue();
/* 509 */         if ((localObject3 == localObject4) || ((localObject3 != null) && (localObject3.equals(localObject4)))) {
/* 510 */           return true;
/*     */         }
/*     */       }
/* 513 */       return false;
/*     */     }
/*     */ 
/*     */     public final int hashCode()
/*     */     {
/* 518 */       return (getKey() == null ? 0 : getKey().hashCode()) ^ (getValue() == null ? 0 : getValue().hashCode());
/*     */     }
/*     */ 
/*     */     public final String toString()
/*     */     {
/* 524 */       return getKey() + "=" + getValue();
/*     */     }
/*     */   }
/*     */ 
/*     */   private class ObservableValues
/*     */     implements Collection<V>
/*     */   {
/*     */     private ObservableValues()
/*     */     {
/*     */     }
/*     */ 
/*     */     public int size()
/*     */     {
/* 345 */       return ObservableMapWrapper.this.backingMap.size();
/*     */     }
/*     */ 
/*     */     public boolean isEmpty()
/*     */     {
/* 350 */       return ObservableMapWrapper.this.backingMap.isEmpty();
/*     */     }
/*     */ 
/*     */     public boolean contains(Object paramObject)
/*     */     {
/* 355 */       return ObservableMapWrapper.this.backingMap.values().contains(paramObject);
/*     */     }
/*     */ 
/*     */     public Iterator<V> iterator()
/*     */     {
/* 360 */       return new Iterator() {
/* 362 */         private Iterator<Map.Entry<K, V>> entryIt = ObservableMapWrapper.this.backingMap.entrySet().iterator();
/*     */         private K lastKey;
/*     */         private V lastValue;
/*     */ 
/* 367 */         public boolean hasNext() { return this.entryIt.hasNext(); }
/*     */ 
/*     */ 
/*     */         public V next()
/*     */         {
/* 372 */           Map.Entry localEntry = (Map.Entry)this.entryIt.next();
/* 373 */           this.lastKey = localEntry.getKey();
/* 374 */           this.lastValue = localEntry.getValue();
/* 375 */           return this.lastValue;
/*     */         }
/*     */ 
/*     */         public void remove()
/*     */         {
/* 380 */           this.entryIt.remove();
/* 381 */           ObservableMapWrapper.this.callObservers(new ObservableMapWrapper.SimpleChange(ObservableMapWrapper.this, this.lastKey, this.lastValue, null));
/*     */         }
/*     */       };
/*     */     }
/*     */ 
/*     */     public Object[] toArray()
/*     */     {
/* 389 */       return ObservableMapWrapper.this.backingMap.values().toArray();
/*     */     }
/*     */ 
/*     */     public <T> T[] toArray(T[] paramArrayOfT)
/*     */     {
/* 394 */       return ObservableMapWrapper.this.backingMap.values().toArray(paramArrayOfT);
/*     */     }
/*     */ 
/*     */     public boolean add(V paramV)
/*     */     {
/* 399 */       throw new UnsupportedOperationException("Not supported.");
/*     */     }
/*     */ 
/*     */     public boolean remove(Object paramObject)
/*     */     {
/* 404 */       for (Iterator localIterator = iterator(); localIterator.hasNext(); ) {
/* 405 */         if (localIterator.next().equals(paramObject)) {
/* 406 */           localIterator.remove();
/* 407 */           return true;
/*     */         }
/*     */       }
/* 410 */       return false;
/*     */     }
/*     */ 
/*     */     public boolean containsAll(Collection<?> paramCollection)
/*     */     {
/* 415 */       return ObservableMapWrapper.this.backingMap.values().containsAll(paramCollection);
/*     */     }
/*     */ 
/*     */     public boolean addAll(Collection<? extends V> paramCollection)
/*     */     {
/* 420 */       throw new UnsupportedOperationException("Not supported.");
/*     */     }
/*     */ 
/*     */     public boolean removeAll(Collection<?> paramCollection)
/*     */     {
/* 425 */       return removeRetain(paramCollection, true);
/*     */     }
/*     */ 
/*     */     private boolean removeRetain(Collection<?> paramCollection, boolean paramBoolean) {
/* 429 */       boolean bool = false;
/* 430 */       for (Iterator localIterator = ObservableMapWrapper.this.backingMap.entrySet().iterator(); localIterator.hasNext(); ) {
/* 431 */         Map.Entry localEntry = (Map.Entry)localIterator.next();
/* 432 */         if (paramBoolean == paramCollection.contains(localEntry.getValue())) {
/* 433 */           bool = true;
/* 434 */           Object localObject1 = localEntry.getKey();
/* 435 */           Object localObject2 = localEntry.getValue();
/* 436 */           localIterator.remove();
/* 437 */           ObservableMapWrapper.this.callObservers(new ObservableMapWrapper.SimpleChange(ObservableMapWrapper.this, localObject1, localObject2, null));
/*     */         }
/*     */       }
/* 440 */       return bool;
/*     */     }
/*     */ 
/*     */     public boolean retainAll(Collection<?> paramCollection)
/*     */     {
/* 445 */       return removeRetain(paramCollection, false);
/*     */     }
/*     */ 
/*     */     public void clear()
/*     */     {
/* 450 */       ObservableMapWrapper.this.clear();
/*     */     }
/*     */ 
/*     */     public String toString()
/*     */     {
/* 455 */       return ObservableMapWrapper.this.backingMap.values().toString();
/*     */     }
/*     */ 
/*     */     public boolean equals(Object paramObject)
/*     */     {
/* 460 */       return ObservableMapWrapper.this.backingMap.values().equals(paramObject);
/*     */     }
/*     */ 
/*     */     public int hashCode()
/*     */     {
/* 465 */       return ObservableMapWrapper.this.backingMap.values().hashCode();
/*     */     }
/*     */   }
/*     */ 
/*     */   private class ObservableKeySet
/*     */     implements Set<K>
/*     */   {
/*     */     private ObservableKeySet()
/*     */     {
/*     */     }
/*     */ 
/*     */     public int size()
/*     */     {
/* 222 */       return ObservableMapWrapper.this.backingMap.size();
/*     */     }
/*     */ 
/*     */     public boolean isEmpty()
/*     */     {
/* 227 */       return ObservableMapWrapper.this.backingMap.isEmpty();
/*     */     }
/*     */ 
/*     */     public boolean contains(Object paramObject)
/*     */     {
/* 232 */       return ObservableMapWrapper.this.backingMap.keySet().contains(paramObject);
/*     */     }
/*     */ 
/*     */     public Iterator<K> iterator()
/*     */     {
/* 237 */       return new Iterator() {
/* 239 */         private Iterator<Map.Entry<K, V>> entryIt = ObservableMapWrapper.this.backingMap.entrySet().iterator();
/*     */         private K lastKey;
/*     */         private V lastValue;
/*     */ 
/* 244 */         public boolean hasNext() { return this.entryIt.hasNext(); }
/*     */ 
/*     */ 
/*     */         public K next()
/*     */         {
/* 249 */           Map.Entry localEntry = (Map.Entry)this.entryIt.next();
/* 250 */           this.lastKey = localEntry.getKey();
/* 251 */           this.lastValue = localEntry.getValue();
/* 252 */           return localEntry.getKey();
/*     */         }
/*     */ 
/*     */         public void remove()
/*     */         {
/* 257 */           this.entryIt.remove();
/* 258 */           ObservableMapWrapper.this.callObservers(new ObservableMapWrapper.SimpleChange(ObservableMapWrapper.this, this.lastKey, this.lastValue, null));
/*     */         }
/*     */       };
/*     */     }
/*     */ 
/*     */     public Object[] toArray()
/*     */     {
/* 266 */       return ObservableMapWrapper.this.backingMap.keySet().toArray();
/*     */     }
/*     */ 
/*     */     public <T> T[] toArray(T[] paramArrayOfT)
/*     */     {
/* 271 */       return ObservableMapWrapper.this.backingMap.keySet().toArray(paramArrayOfT);
/*     */     }
/*     */ 
/*     */     public boolean add(K paramK)
/*     */     {
/* 276 */       throw new UnsupportedOperationException("Not supported.");
/*     */     }
/*     */ 
/*     */     public boolean remove(Object paramObject)
/*     */     {
/* 281 */       return ObservableMapWrapper.this.remove(paramObject) != null;
/*     */     }
/*     */ 
/*     */     public boolean containsAll(Collection<?> paramCollection)
/*     */     {
/* 286 */       return ObservableMapWrapper.this.backingMap.keySet().containsAll(paramCollection);
/*     */     }
/*     */ 
/*     */     public boolean addAll(Collection<? extends K> paramCollection)
/*     */     {
/* 291 */       throw new UnsupportedOperationException("Not supported.");
/*     */     }
/*     */ 
/*     */     public boolean retainAll(Collection<?> paramCollection)
/*     */     {
/* 296 */       return removeRetain(paramCollection, false);
/*     */     }
/*     */ 
/*     */     private boolean removeRetain(Collection<?> paramCollection, boolean paramBoolean) {
/* 300 */       boolean bool = false;
/* 301 */       for (Iterator localIterator = ObservableMapWrapper.this.backingMap.entrySet().iterator(); localIterator.hasNext(); ) {
/* 302 */         Map.Entry localEntry = (Map.Entry)localIterator.next();
/* 303 */         if (paramBoolean == paramCollection.contains(localEntry.getKey())) {
/* 304 */           bool = true;
/* 305 */           Object localObject1 = localEntry.getKey();
/* 306 */           Object localObject2 = localEntry.getValue();
/* 307 */           localIterator.remove();
/* 308 */           ObservableMapWrapper.this.callObservers(new ObservableMapWrapper.SimpleChange(ObservableMapWrapper.this, localObject1, localObject2, null));
/*     */         }
/*     */       }
/* 311 */       return bool;
/*     */     }
/*     */ 
/*     */     public boolean removeAll(Collection<?> paramCollection)
/*     */     {
/* 316 */       return removeRetain(paramCollection, true);
/*     */     }
/*     */ 
/*     */     public void clear()
/*     */     {
/* 321 */       ObservableMapWrapper.this.clear();
/*     */     }
/*     */ 
/*     */     public String toString()
/*     */     {
/* 326 */       return ObservableMapWrapper.this.backingMap.keySet().toString();
/*     */     }
/*     */ 
/*     */     public boolean equals(Object paramObject)
/*     */     {
/* 331 */       return ObservableMapWrapper.this.backingMap.keySet().equals(paramObject);
/*     */     }
/*     */ 
/*     */     public int hashCode()
/*     */     {
/* 336 */       return ObservableMapWrapper.this.backingMap.keySet().hashCode();
/*     */     }
/*     */   }
/*     */ 
/*     */   private class SimpleChange extends MapChangeListener.Change<K, V>
/*     */   {
/*     */     private final K key;
/*     */     private final V old;
/*     */     private final V added;
/*     */ 
/*     */     public SimpleChange(V paramV1, V arg3)
/*     */     {
/*  60 */       super();
/*  61 */       this.key = paramV1;
/*     */       Object localObject1;
/*  62 */       this.old = localObject1;
/*     */       Object localObject2;
/*  63 */       this.added = localObject2;
/*     */     }
/*     */ 
/*     */     public boolean wasAdded()
/*     */     {
/*  68 */       return this.added != null;
/*     */     }
/*     */ 
/*     */     public boolean wasRemoved()
/*     */     {
/*  73 */       return this.old != null;
/*     */     }
/*     */ 
/*     */     public K getKey()
/*     */     {
/*  78 */       return this.key;
/*     */     }
/*     */ 
/*     */     public V getValueAdded()
/*     */     {
/*  83 */       return this.added;
/*     */     }
/*     */ 
/*     */     public V getValueRemoved()
/*     */     {
/*  88 */       return this.old;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.collections.ObservableMapWrapper
 * JD-Core Version:    0.6.2
 */