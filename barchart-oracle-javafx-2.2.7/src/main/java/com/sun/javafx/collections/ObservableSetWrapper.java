/*     */ package com.sun.javafx.collections;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.collections.ObservableSet;
/*     */ import javafx.collections.SetChangeListener;
/*     */ import javafx.collections.SetChangeListener.Change;
/*     */ 
/*     */ public class ObservableSetWrapper<E>
/*     */   implements ObservableSet<E>
/*     */ {
/*     */   private final Set<E> backingSet;
/*     */   private SetListenerHelper<E> listenerHelper;
/*     */ 
/*     */   public ObservableSetWrapper(Set<E> paramSet)
/*     */   {
/*  51 */     this.backingSet = paramSet;
/*     */   }
/*     */ 
/*     */   private void callObservers(SetChangeListener.Change<E> paramChange)
/*     */   {
/*  87 */     SetListenerHelper.fireValueChangedEvent(this.listenerHelper, paramChange);
/*     */   }
/*     */ 
/*     */   public void addListener(InvalidationListener paramInvalidationListener)
/*     */   {
/*  95 */     this.listenerHelper = SetListenerHelper.addListener(this.listenerHelper, paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(InvalidationListener paramInvalidationListener)
/*     */   {
/* 103 */     this.listenerHelper = SetListenerHelper.removeListener(this.listenerHelper, paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public void addListener(SetChangeListener<? super E> paramSetChangeListener)
/*     */   {
/* 111 */     this.listenerHelper = SetListenerHelper.addListener(this.listenerHelper, paramSetChangeListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(SetChangeListener<? super E> paramSetChangeListener)
/*     */   {
/* 119 */     this.listenerHelper = SetListenerHelper.removeListener(this.listenerHelper, paramSetChangeListener);
/*     */   }
/*     */ 
/*     */   public int size()
/*     */   {
/* 130 */     return this.backingSet.size();
/*     */   }
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/* 141 */     return this.backingSet.isEmpty();
/*     */   }
/*     */ 
/*     */   public boolean contains(Object paramObject)
/*     */   {
/* 153 */     return this.backingSet.contains(paramObject);
/*     */   }
/*     */ 
/*     */   public Iterator iterator()
/*     */   {
/* 166 */     return new Iterator()
/*     */     {
/* 168 */       private final Iterator<E> backingIt = ObservableSetWrapper.this.backingSet.iterator();
/*     */       private E lastElement;
/*     */ 
/*     */       public boolean hasNext() {
/* 173 */         return this.backingIt.hasNext();
/*     */       }
/*     */ 
/*     */       public E next()
/*     */       {
/* 178 */         this.lastElement = this.backingIt.next();
/* 179 */         return this.lastElement;
/*     */       }
/*     */ 
/*     */       public void remove()
/*     */       {
/* 184 */         this.backingIt.remove();
/* 185 */         ObservableSetWrapper.this.callObservers(new ObservableSetWrapper.SimpleChange(ObservableSetWrapper.this, this.lastElement, null));
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public Object[] toArray()
/*     */   {
/* 198 */     return this.backingSet.toArray();
/*     */   }
/*     */ 
/*     */   public <T> T[] toArray(T[] paramArrayOfT)
/*     */   {
/* 212 */     return this.backingSet.toArray(paramArrayOfT);
/*     */   }
/*     */ 
/*     */   public boolean add(E paramE)
/*     */   {
/* 226 */     boolean bool = this.backingSet.add(paramE);
/* 227 */     if (bool) {
/* 228 */       callObservers(new SimpleChange(null, paramE));
/*     */     }
/* 230 */     return bool;
/*     */   }
/*     */ 
/*     */   public boolean remove(Object paramObject)
/*     */   {
/* 244 */     boolean bool = this.backingSet.remove(paramObject);
/* 245 */     if (bool) {
/* 246 */       callObservers(new SimpleChange(paramObject, null));
/*     */     }
/* 248 */     return bool;
/*     */   }
/*     */ 
/*     */   public boolean containsAll(Collection<?> paramCollection)
/*     */   {
/* 261 */     return this.backingSet.containsAll(paramCollection);
/*     */   }
/*     */ 
/*     */   public boolean addAll(Collection<? extends E> paramCollection)
/*     */   {
/* 275 */     boolean bool = false;
/* 276 */     for (Iterator localIterator = paramCollection.iterator(); localIterator.hasNext(); ) { Object localObject = localIterator.next();
/* 277 */       bool |= add(localObject);
/*     */     }
/* 279 */     return bool;
/*     */   }
/*     */ 
/*     */   public boolean retainAll(Collection<?> paramCollection)
/*     */   {
/* 293 */     return removeRetain(paramCollection, false);
/*     */   }
/*     */ 
/*     */   public boolean removeAll(Collection<?> paramCollection)
/*     */   {
/* 306 */     return removeRetain(paramCollection, true);
/*     */   }
/*     */ 
/*     */   private boolean removeRetain(Collection<?> paramCollection, boolean paramBoolean) {
/* 310 */     boolean bool = false;
/* 311 */     for (Iterator localIterator = this.backingSet.iterator(); localIterator.hasNext(); ) {
/* 312 */       Object localObject = localIterator.next();
/* 313 */       if (paramBoolean == paramCollection.contains(localObject)) {
/* 314 */         bool = true;
/* 315 */         localIterator.remove();
/* 316 */         callObservers(new SimpleChange(localObject, null));
/*     */       }
/*     */     }
/* 319 */     return bool;
/*     */   }
/*     */ 
/*     */   public void clear()
/*     */   {
/* 329 */     for (Iterator localIterator = this.backingSet.iterator(); localIterator.hasNext(); ) {
/* 330 */       Object localObject = localIterator.next();
/* 331 */       localIterator.remove();
/* 332 */       callObservers(new SimpleChange(localObject, null));
/*     */     }
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 343 */     return this.backingSet.toString();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 354 */     return this.backingSet.equals(paramObject);
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 364 */     return this.backingSet.hashCode();
/*     */   }
/*     */ 
/*     */   private class SimpleChange extends SetChangeListener.Change<E>
/*     */   {
/*     */     private E old;
/*     */     private E added;
/*     */ 
/*     */     public SimpleChange(E arg2)
/*     */     {
/*  60 */       super();
/*     */       Object localObject1;
/*  61 */       this.old = localObject1;
/*     */       Object localObject2;
/*  62 */       this.added = localObject2;
/*     */     }
/*     */ 
/*     */     public boolean wasAdded()
/*     */     {
/*  67 */       return this.added != null;
/*     */     }
/*     */ 
/*     */     public boolean wasRemoved()
/*     */     {
/*  72 */       return this.old != null;
/*     */     }
/*     */ 
/*     */     public E getElementAdded()
/*     */     {
/*  77 */       return this.added;
/*     */     }
/*     */ 
/*     */     public E getElementRemoved()
/*     */     {
/*  82 */       return this.old;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.collections.ObservableSetWrapper
 * JD-Core Version:    0.6.2
 */