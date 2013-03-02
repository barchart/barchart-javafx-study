/*     */ package com.sun.javafx.scene.control;
/*     */ 
/*     */ import com.sun.javafx.collections.ListListenerHelper;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.collections.ListChangeListener;
/*     */ import javafx.collections.ListChangeListener.Change;
/*     */ import javafx.collections.ObservableList;
/*     */ 
/*     */ public abstract class ReadOnlyUnbackedObservableList<T>
/*     */   implements ObservableList<T>
/*     */ {
/*     */   private ListListenerHelper<T> listenerHelper;
/*     */ 
/*     */   public abstract T get(int paramInt);
/*     */ 
/*     */   public abstract int size();
/*     */ 
/*     */   public void addListener(InvalidationListener paramInvalidationListener)
/*     */   {
/*  58 */     this.listenerHelper = ListListenerHelper.addListener(this.listenerHelper, paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(InvalidationListener paramInvalidationListener) {
/*  62 */     this.listenerHelper = ListListenerHelper.removeListener(this.listenerHelper, paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public void addListener(ListChangeListener<? super T> paramListChangeListener) {
/*  66 */     this.listenerHelper = ListListenerHelper.addListener(this.listenerHelper, paramListChangeListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(ListChangeListener<? super T> paramListChangeListener) {
/*  70 */     this.listenerHelper = ListListenerHelper.removeListener(this.listenerHelper, paramListChangeListener);
/*     */   }
/*     */ 
/*     */   public void callObservers(ListChangeListener.Change<T> paramChange) {
/*  74 */     ListListenerHelper.fireValueChangedEvent(this.listenerHelper, paramChange);
/*     */   }
/*     */ 
/*     */   public int indexOf(Object paramObject) {
/*  78 */     if (paramObject == null) return -1;
/*     */ 
/*  80 */     for (int i = 0; i < size(); i++) {
/*  81 */       Object localObject = get(i);
/*  82 */       if (paramObject.equals(localObject)) return i;
/*     */     }
/*     */ 
/*  85 */     return -1;
/*     */   }
/*     */ 
/*     */   public int lastIndexOf(Object paramObject) {
/*  89 */     if (paramObject == null) return -1;
/*     */ 
/*  91 */     for (int i = size() - 1; i >= 0; i--) {
/*  92 */       Object localObject = get(i);
/*  93 */       if (paramObject.equals(localObject)) return i;
/*     */     }
/*     */ 
/*  96 */     return -1;
/*     */   }
/*     */ 
/*     */   public boolean contains(Object paramObject) {
/* 100 */     return indexOf(paramObject) != -1;
/*     */   }
/*     */ 
/*     */   public boolean containsAll(Collection<?> paramCollection) {
/* 104 */     for (Iterator localIterator = paramCollection.iterator(); localIterator.hasNext(); ) { Object localObject = localIterator.next();
/* 105 */       if (!contains(localObject)) {
/* 106 */         return false;
/*     */       }
/*     */     }
/* 109 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean isEmpty() {
/* 113 */     return size() == 0;
/*     */   }
/*     */ 
/*     */   public ListIterator<T> listIterator() {
/* 117 */     return new SelectionListIterator(this);
/*     */   }
/*     */ 
/*     */   public ListIterator<T> listIterator(int paramInt) {
/* 121 */     return new SelectionListIterator(this, paramInt);
/*     */   }
/*     */ 
/*     */   public Iterator<T> iterator()
/*     */   {
/* 126 */     return new SelectionListIterator(this);
/*     */   }
/*     */ 
/*     */   public List<T> subList(final int paramInt1, final int paramInt2)
/*     */   {
/* 134 */     if (paramInt1 >= paramInt2) return Collections.emptyList();
/* 135 */     final ReadOnlyUnbackedObservableList localReadOnlyUnbackedObservableList = this;
/* 136 */     return new ReadOnlyUnbackedObservableList() {
/*     */       public T get(int paramAnonymousInt) {
/* 138 */         return localReadOnlyUnbackedObservableList.get(paramAnonymousInt + paramInt1);
/*     */       }
/*     */ 
/*     */       public int size() {
/* 142 */         return paramInt2 - paramInt1;
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public Object[] toArray()
/*     */   {
/* 149 */     Object[] arrayOfObject = new Object[size()];
/* 150 */     for (int i = 0; i < size(); i++) {
/* 151 */       arrayOfObject[i] = get(i);
/*     */     }
/* 153 */     return arrayOfObject;
/*     */   }
/*     */ 
/*     */   public <T> T[] toArray(T[] paramArrayOfT)
/*     */   {
/* 158 */     Object localObject = paramArrayOfT;
/* 159 */     if (localObject.length < size()) {
/* 160 */       localObject = (Object[])new Object[size()];
/*     */     }
/*     */ 
/* 163 */     for (int i = 0; i < size(); i++) {
/* 164 */       localObject[i] = get(i);
/*     */     }
/*     */ 
/* 167 */     return localObject;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 173 */     Iterator localIterator = iterator();
/* 174 */     if (!localIterator.hasNext()) {
/* 175 */       return "[]";
/*     */     }
/* 177 */     StringBuilder localStringBuilder = new StringBuilder();
/* 178 */     localStringBuilder.append('[');
/*     */     while (true) {
/* 180 */       Object localObject = localIterator.next();
/* 181 */       localStringBuilder.append(localObject == this ? "(this Collection)" : localObject);
/* 182 */       if (!localIterator.hasNext())
/* 183 */         return localStringBuilder.append(']').toString();
/* 184 */       localStringBuilder.append(", ");
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean add(T paramT) {
/* 189 */     throw new UnsupportedOperationException("Not supported.");
/*     */   }
/*     */ 
/*     */   public void add(int paramInt, T paramT) {
/* 193 */     throw new UnsupportedOperationException("Not supported.");
/*     */   }
/*     */ 
/*     */   public boolean addAll(Collection<? extends T> paramCollection) {
/* 197 */     throw new UnsupportedOperationException("Not supported.");
/*     */   }
/*     */ 
/*     */   public boolean addAll(int paramInt, Collection<? extends T> paramCollection) {
/* 201 */     throw new UnsupportedOperationException("Not supported.");
/*     */   }
/*     */ 
/*     */   public boolean addAll(T[] paramArrayOfT) {
/* 205 */     throw new UnsupportedOperationException("Not supported.");
/*     */   }
/*     */ 
/*     */   public T set(int paramInt, T paramT) {
/* 209 */     throw new UnsupportedOperationException("Not supported.");
/*     */   }
/*     */ 
/*     */   public boolean setAll(Collection<? extends T> paramCollection) {
/* 213 */     throw new UnsupportedOperationException("Not supported.");
/*     */   }
/*     */ 
/*     */   public boolean setAll(T[] paramArrayOfT) {
/* 217 */     throw new UnsupportedOperationException("Not supported.");
/*     */   }
/*     */ 
/*     */   public void clear() {
/* 221 */     throw new UnsupportedOperationException("Not supported.");
/*     */   }
/*     */ 
/*     */   public T remove(int paramInt) {
/* 225 */     throw new UnsupportedOperationException("Not supported.");
/*     */   }
/*     */ 
/*     */   public boolean remove(Object paramObject) {
/* 229 */     throw new UnsupportedOperationException("Not supported.");
/*     */   }
/*     */ 
/*     */   public boolean removeAll(Collection<?> paramCollection) {
/* 233 */     throw new UnsupportedOperationException("Not supported.");
/*     */   }
/*     */ 
/*     */   public boolean retainAll(Collection<?> paramCollection) {
/* 237 */     throw new UnsupportedOperationException("Not supported.");
/*     */   }
/*     */ 
/*     */   public void remove(int paramInt1, int paramInt2) {
/* 241 */     throw new UnsupportedOperationException("Not supported.");
/*     */   }
/*     */ 
/*     */   public boolean removeAll(T[] paramArrayOfT) {
/* 245 */     throw new UnsupportedOperationException("Not supported.");
/*     */   }
/*     */ 
/*     */   public boolean retainAll(T[] paramArrayOfT) {
/* 249 */     throw new UnsupportedOperationException("Not supported.");
/*     */   }
/*     */   private static class SelectionListIterator<T> implements ListIterator<T> {
/*     */     private int pos;
/*     */     private final ReadOnlyUnbackedObservableList<T> list;
/*     */ 
/*     */     public SelectionListIterator(ReadOnlyUnbackedObservableList<T> paramReadOnlyUnbackedObservableList) {
/* 257 */       this(paramReadOnlyUnbackedObservableList, 0);
/*     */     }
/*     */ 
/*     */     public SelectionListIterator(ReadOnlyUnbackedObservableList<T> paramReadOnlyUnbackedObservableList, int paramInt) {
/* 261 */       this.list = paramReadOnlyUnbackedObservableList;
/* 262 */       this.pos = paramInt;
/*     */     }
/*     */ 
/*     */     public boolean hasNext() {
/* 266 */       return this.pos < this.list.size();
/*     */     }
/*     */ 
/*     */     public T next() {
/* 270 */       return this.list.get(this.pos++);
/*     */     }
/*     */ 
/*     */     public boolean hasPrevious() {
/* 274 */       return this.pos > 0;
/*     */     }
/*     */ 
/*     */     public T previous() {
/* 278 */       return this.list.get(this.pos--);
/*     */     }
/*     */ 
/*     */     public int nextIndex() {
/* 282 */       return this.pos + 1;
/*     */     }
/*     */ 
/*     */     public int previousIndex() {
/* 286 */       return this.pos - 1;
/*     */     }
/*     */ 
/*     */     public void remove() {
/* 290 */       throw new UnsupportedOperationException("Not supported.");
/*     */     }
/*     */ 
/*     */     public void set(T paramT) {
/* 294 */       throw new UnsupportedOperationException("Not supported.");
/*     */     }
/*     */ 
/*     */     public void add(T paramT) {
/* 298 */       throw new UnsupportedOperationException("Not supported.");
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.ReadOnlyUnbackedObservableList
 * JD-Core Version:    0.6.2
 */