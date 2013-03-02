/*     */ package com.sun.javafx.binding;
/*     */ 
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javafx.beans.WeakListener;
/*     */ import javafx.collections.ListChangeListener;
/*     */ import javafx.collections.ListChangeListener.Change;
/*     */ import javafx.collections.MapChangeListener;
/*     */ import javafx.collections.MapChangeListener.Change;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.collections.ObservableMap;
/*     */ import javafx.collections.ObservableSet;
/*     */ import javafx.collections.SetChangeListener;
/*     */ import javafx.collections.SetChangeListener.Change;
/*     */ 
/*     */ public class ContentBinding
/*     */ {
/*     */   private static void checkParameters(Object paramObject1, Object paramObject2)
/*     */   {
/*  61 */     if ((paramObject1 == null) || (paramObject2 == null)) {
/*  62 */       throw new NullPointerException("Both parameters must be specified.");
/*     */     }
/*  64 */     if (paramObject1 == paramObject2)
/*  65 */       throw new IllegalArgumentException("Cannot bind object to itself");
/*     */   }
/*     */ 
/*     */   public static <E> Object bind(List<E> paramList, ObservableList<? extends E> paramObservableList)
/*     */   {
/*  70 */     checkParameters(paramList, paramObservableList);
/*  71 */     ListContentBinding localListContentBinding = new ListContentBinding(paramList);
/*  72 */     if ((paramList instanceof ObservableList)) {
/*  73 */       ((ObservableList)paramList).setAll(paramObservableList);
/*     */     } else {
/*  75 */       paramList.clear();
/*  76 */       paramList.addAll(paramObservableList);
/*     */     }
/*  78 */     paramObservableList.addListener(localListContentBinding);
/*  79 */     return localListContentBinding;
/*     */   }
/*     */ 
/*     */   public static <E> Object bind(Set<E> paramSet, ObservableSet<? extends E> paramObservableSet) {
/*  83 */     checkParameters(paramSet, paramObservableSet);
/*  84 */     SetContentBinding localSetContentBinding = new SetContentBinding(paramSet);
/*  85 */     paramSet.clear();
/*  86 */     paramSet.addAll(paramObservableSet);
/*  87 */     paramObservableSet.addListener(localSetContentBinding);
/*  88 */     return localSetContentBinding;
/*     */   }
/*     */ 
/*     */   public static <K, V> Object bind(Map<K, V> paramMap, ObservableMap<? extends K, ? extends V> paramObservableMap) {
/*  92 */     checkParameters(paramMap, paramObservableMap);
/*  93 */     MapContentBinding localMapContentBinding = new MapContentBinding(paramMap);
/*  94 */     paramMap.clear();
/*  95 */     paramMap.putAll(paramObservableMap);
/*  96 */     paramObservableMap.addListener(localMapContentBinding);
/*  97 */     return localMapContentBinding;
/*     */   }
/*     */ 
/*     */   public static void unbind(Object paramObject1, Object paramObject2) {
/* 101 */     checkParameters(paramObject1, paramObject2);
/* 102 */     if (((paramObject1 instanceof List)) && ((paramObject2 instanceof ObservableList)))
/* 103 */       ((ObservableList)paramObject2).removeListener(new ListContentBinding((List)paramObject1));
/* 104 */     else if (((paramObject1 instanceof Set)) && ((paramObject2 instanceof ObservableSet)))
/* 105 */       ((ObservableSet)paramObject2).removeListener(new SetContentBinding((Set)paramObject1));
/* 106 */     else if (((paramObject1 instanceof Map)) && ((paramObject2 instanceof ObservableMap)))
/* 107 */       ((ObservableMap)paramObject2).removeListener(new MapContentBinding((Map)paramObject1));
/*     */   }
/*     */ 
/*     */   private static class MapContentBinding<K, V>
/*     */     implements MapChangeListener<K, V>, WeakListener
/*     */   {
/*     */     private final WeakReference<Map<K, V>> mapRef;
/*     */ 
/*     */     public MapContentBinding(Map<K, V> paramMap)
/*     */     {
/* 230 */       this.mapRef = new WeakReference(paramMap);
/*     */     }
/*     */ 
/*     */     public void onChanged(MapChangeListener.Change<? extends K, ? extends V> paramChange)
/*     */     {
/* 235 */       Map localMap = (Map)this.mapRef.get();
/* 236 */       if (localMap == null) {
/* 237 */         paramChange.getMap().removeListener(this);
/*     */       }
/* 239 */       else if (paramChange.wasRemoved())
/* 240 */         localMap.remove(paramChange.getKey());
/*     */       else
/* 242 */         localMap.put(paramChange.getKey(), paramChange.getValueAdded());
/*     */     }
/*     */ 
/*     */     public boolean wasGarbageCollected()
/*     */     {
/* 249 */       return this.mapRef.get() == null;
/*     */     }
/*     */ 
/*     */     public int hashCode()
/*     */     {
/* 254 */       Map localMap = (Map)this.mapRef.get();
/* 255 */       return localMap == null ? 0 : localMap.hashCode();
/*     */     }
/*     */ 
/*     */     public boolean equals(Object paramObject)
/*     */     {
/* 260 */       if (this == paramObject) {
/* 261 */         return true;
/*     */       }
/*     */ 
/* 264 */       Map localMap1 = (Map)this.mapRef.get();
/* 265 */       if (localMap1 == null) {
/* 266 */         return false;
/*     */       }
/*     */ 
/* 269 */       if ((paramObject instanceof MapContentBinding)) {
/* 270 */         MapContentBinding localMapContentBinding = (MapContentBinding)paramObject;
/* 271 */         Map localMap2 = (Map)localMapContentBinding.mapRef.get();
/* 272 */         return localMap1 == localMap2;
/*     */       }
/* 274 */       return false;
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class SetContentBinding<E>
/*     */     implements SetChangeListener<E>, WeakListener
/*     */   {
/*     */     private final WeakReference<Set<E>> setRef;
/*     */ 
/*     */     public SetContentBinding(Set<E> paramSet)
/*     */     {
/* 177 */       this.setRef = new WeakReference(paramSet);
/*     */     }
/*     */ 
/*     */     public void onChanged(SetChangeListener.Change<? extends E> paramChange)
/*     */     {
/* 182 */       Set localSet = (Set)this.setRef.get();
/* 183 */       if (localSet == null) {
/* 184 */         paramChange.getSet().removeListener(this);
/*     */       }
/* 186 */       else if (paramChange.wasRemoved())
/* 187 */         localSet.remove(paramChange.getElementRemoved());
/*     */       else
/* 189 */         localSet.add(paramChange.getElementAdded());
/*     */     }
/*     */ 
/*     */     public boolean wasGarbageCollected()
/*     */     {
/* 196 */       return this.setRef.get() == null;
/*     */     }
/*     */ 
/*     */     public int hashCode()
/*     */     {
/* 201 */       Set localSet = (Set)this.setRef.get();
/* 202 */       return localSet == null ? 0 : localSet.hashCode();
/*     */     }
/*     */ 
/*     */     public boolean equals(Object paramObject)
/*     */     {
/* 207 */       if (this == paramObject) {
/* 208 */         return true;
/*     */       }
/*     */ 
/* 211 */       Set localSet1 = (Set)this.setRef.get();
/* 212 */       if (localSet1 == null) {
/* 213 */         return false;
/*     */       }
/*     */ 
/* 216 */       if ((paramObject instanceof SetContentBinding)) {
/* 217 */         SetContentBinding localSetContentBinding = (SetContentBinding)paramObject;
/* 218 */         Set localSet2 = (Set)localSetContentBinding.setRef.get();
/* 219 */         return localSet1 == localSet2;
/*     */       }
/* 221 */       return false;
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class ListContentBinding<E>
/*     */     implements ListChangeListener<E>, WeakListener
/*     */   {
/*     */     private final WeakReference<List<E>> listRef;
/*     */ 
/*     */     public ListContentBinding(List<E> paramList)
/*     */     {
/* 116 */       this.listRef = new WeakReference(paramList);
/*     */     }
/*     */ 
/*     */     public void onChanged(ListChangeListener.Change<? extends E> paramChange)
/*     */     {
/* 121 */       List localList = (List)this.listRef.get();
/* 122 */       if (localList == null)
/* 123 */         paramChange.getList().removeListener(this);
/*     */       else
/* 125 */         while (paramChange.next())
/* 126 */           if (paramChange.wasPermutated()) {
/* 127 */             localList.subList(paramChange.getFrom(), paramChange.getTo()).clear();
/* 128 */             localList.addAll(paramChange.getFrom(), paramChange.getList().subList(paramChange.getFrom(), paramChange.getTo()));
/*     */           } else {
/* 130 */             if (paramChange.wasRemoved()) {
/* 131 */               localList.subList(paramChange.getFrom(), paramChange.getFrom() + paramChange.getRemovedSize()).clear();
/*     */             }
/* 133 */             if (paramChange.wasAdded())
/* 134 */               localList.addAll(paramChange.getFrom(), paramChange.getAddedSubList());
/*     */           }
/*     */     }
/*     */ 
/*     */     public boolean wasGarbageCollected()
/*     */     {
/* 143 */       return this.listRef.get() == null;
/*     */     }
/*     */ 
/*     */     public int hashCode()
/*     */     {
/* 148 */       List localList = (List)this.listRef.get();
/* 149 */       return localList == null ? 0 : localList.hashCode();
/*     */     }
/*     */ 
/*     */     public boolean equals(Object paramObject)
/*     */     {
/* 154 */       if (this == paramObject) {
/* 155 */         return true;
/*     */       }
/*     */ 
/* 158 */       List localList1 = (List)this.listRef.get();
/* 159 */       if (localList1 == null) {
/* 160 */         return false;
/*     */       }
/*     */ 
/* 163 */       if ((paramObject instanceof ListContentBinding)) {
/* 164 */         ListContentBinding localListContentBinding = (ListContentBinding)paramObject;
/* 165 */         List localList2 = (List)localListContentBinding.listRef.get();
/* 166 */         return localList1 == localList2;
/*     */       }
/* 168 */       return false;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.binding.ContentBinding
 * JD-Core Version:    0.6.2
 */