/*     */ package com.sun.javafx.collections;
/*     */ 
/*     */ import java.util.AbstractMap;
/*     */ import java.util.AbstractMap.SimpleImmutableEntry;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.collections.MapChangeListener;
/*     */ import javafx.collections.MapChangeListener.Change;
/*     */ import javafx.collections.ObservableMap;
/*     */ 
/*     */ public class UnmodifiableObservableMap<K, V> extends AbstractMap<K, V>
/*     */   implements ObservableMap<K, V>
/*     */ {
/*     */   private MapListenerHelper<K, V> listenerHelper;
/*     */   private final ObservableMap<K, V> backingMap;
/*     */ 
/*     */   public UnmodifiableObservableMap(ObservableMap<K, V> paramObservableMap)
/*     */   {
/*  51 */     this.backingMap = paramObservableMap;
/*  52 */     paramObservableMap.addListener(new MapChangeListener()
/*     */     {
/*     */       public void onChanged(MapChangeListener.Change<? extends K, ? extends V> paramAnonymousChange)
/*     */       {
/*  56 */         if (paramAnonymousChange.getMap() == UnmodifiableObservableMap.this.backingMap)
/*  57 */           UnmodifiableObservableMap.this.callObservers(paramAnonymousChange);
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   private void callObservers(MapChangeListener.Change<? extends K, ? extends V> paramChange)
/*     */   {
/*  65 */     MapListenerHelper.fireValueChangedEvent(this.listenerHelper, paramChange);
/*     */   }
/*     */ 
/*     */   public void addListener(InvalidationListener paramInvalidationListener)
/*     */   {
/*  70 */     this.listenerHelper = MapListenerHelper.addListener(this.listenerHelper, paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(InvalidationListener paramInvalidationListener)
/*     */   {
/*  75 */     this.listenerHelper = MapListenerHelper.removeListener(this.listenerHelper, paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public void addListener(MapChangeListener<? super K, ? super V> paramMapChangeListener)
/*     */   {
/*  80 */     this.listenerHelper = MapListenerHelper.addListener(this.listenerHelper, paramMapChangeListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(MapChangeListener<? super K, ? super V> paramMapChangeListener)
/*     */   {
/*  85 */     this.listenerHelper = MapListenerHelper.removeListener(this.listenerHelper, paramMapChangeListener);
/*     */   }
/*     */ 
/*     */   public int size()
/*     */   {
/*  90 */     return this.backingMap.size();
/*     */   }
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/*  95 */     return this.backingMap.isEmpty();
/*     */   }
/*     */ 
/*     */   public boolean containsKey(Object paramObject)
/*     */   {
/* 100 */     return this.backingMap.containsKey(paramObject);
/*     */   }
/*     */ 
/*     */   public boolean containsValue(Object paramObject)
/*     */   {
/* 105 */     return this.backingMap.containsValue(paramObject);
/*     */   }
/*     */ 
/*     */   public V get(Object paramObject)
/*     */   {
/* 110 */     return this.backingMap.get(paramObject);
/*     */   }
/*     */ 
/*     */   public Set<K> keySet()
/*     */   {
/* 115 */     return Collections.unmodifiableSet(this.backingMap.keySet());
/*     */   }
/*     */ 
/*     */   public Collection<V> values()
/*     */   {
/* 120 */     return Collections.unmodifiableCollection(this.backingMap.values());
/*     */   }
/*     */ 
/*     */   public Set<Map.Entry<K, V>> entrySet()
/*     */   {
/* 126 */     Set localSet = this.backingMap.entrySet();
/* 127 */     HashSet localHashSet = new HashSet();
/* 128 */     for (Map.Entry localEntry : localSet) {
/* 129 */       localHashSet.add(new AbstractMap.SimpleImmutableEntry(localEntry.getKey(), localEntry.getValue()));
/*     */     }
/* 131 */     return Collections.unmodifiableSet(localHashSet);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.collections.UnmodifiableObservableMap
 * JD-Core Version:    0.6.2
 */