/*     */ package com.sun.javafx.binding;
/*     */ 
/*     */ import java.lang.ref.WeakReference;
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
/*     */ public class BidirectionalContentBinding
/*     */ {
/*     */   private static void checkParameters(Object paramObject1, Object paramObject2)
/*     */   {
/*  60 */     if ((paramObject1 == null) || (paramObject2 == null)) {
/*  61 */       throw new NullPointerException("Both parameters must be specified.");
/*     */     }
/*  63 */     if (paramObject1 == paramObject2)
/*  64 */       throw new IllegalArgumentException("Cannot bind object to itself");
/*     */   }
/*     */ 
/*     */   public static <E> Object bind(ObservableList<E> paramObservableList1, ObservableList<E> paramObservableList2)
/*     */   {
/*  69 */     checkParameters(paramObservableList1, paramObservableList2);
/*  70 */     ListContentBinding localListContentBinding = new ListContentBinding(paramObservableList1, paramObservableList2);
/*  71 */     paramObservableList1.setAll(paramObservableList2);
/*  72 */     paramObservableList1.addListener(localListContentBinding);
/*  73 */     paramObservableList2.addListener(localListContentBinding);
/*  74 */     return localListContentBinding;
/*     */   }
/*     */ 
/*     */   public static <E> Object bind(ObservableSet<E> paramObservableSet1, ObservableSet<E> paramObservableSet2) {
/*  78 */     checkParameters(paramObservableSet1, paramObservableSet2);
/*  79 */     SetContentBinding localSetContentBinding = new SetContentBinding(paramObservableSet1, paramObservableSet2);
/*  80 */     paramObservableSet1.clear();
/*  81 */     paramObservableSet1.addAll(paramObservableSet2);
/*  82 */     paramObservableSet1.addListener(localSetContentBinding);
/*  83 */     paramObservableSet2.addListener(localSetContentBinding);
/*  84 */     return localSetContentBinding;
/*     */   }
/*     */ 
/*     */   public static <K, V> Object bind(ObservableMap<K, V> paramObservableMap1, ObservableMap<K, V> paramObservableMap2) {
/*  88 */     checkParameters(paramObservableMap1, paramObservableMap2);
/*  89 */     MapContentBinding localMapContentBinding = new MapContentBinding(paramObservableMap1, paramObservableMap2);
/*  90 */     paramObservableMap1.clear();
/*  91 */     paramObservableMap1.putAll(paramObservableMap2);
/*  92 */     paramObservableMap1.addListener(localMapContentBinding);
/*  93 */     paramObservableMap2.addListener(localMapContentBinding);
/*  94 */     return localMapContentBinding;
/*     */   }
/*     */ 
/*     */   public static void unbind(Object paramObject1, Object paramObject2) {
/*  98 */     checkParameters(paramObject1, paramObject2);
/*     */     Object localObject1;
/*     */     Object localObject2;
/*     */     Object localObject3;
/*  99 */     if (((paramObject1 instanceof ObservableList)) && ((paramObject2 instanceof ObservableList))) {
/* 100 */       localObject1 = (ObservableList)paramObject1;
/* 101 */       localObject2 = (ObservableList)paramObject2;
/* 102 */       localObject3 = new ListContentBinding((ObservableList)localObject1, (ObservableList)localObject2);
/* 103 */       ((ObservableList)localObject1).removeListener((ListChangeListener)localObject3);
/* 104 */       ((ObservableList)localObject2).removeListener((ListChangeListener)localObject3);
/* 105 */     } else if (((paramObject1 instanceof ObservableSet)) && ((paramObject2 instanceof ObservableSet))) {
/* 106 */       localObject1 = (ObservableSet)paramObject1;
/* 107 */       localObject2 = (ObservableSet)paramObject2;
/* 108 */       localObject3 = new SetContentBinding((ObservableSet)localObject1, (ObservableSet)localObject2);
/* 109 */       ((ObservableSet)localObject1).removeListener((SetChangeListener)localObject3);
/* 110 */       ((ObservableSet)localObject2).removeListener((SetChangeListener)localObject3);
/* 111 */     } else if (((paramObject1 instanceof ObservableMap)) && ((paramObject2 instanceof ObservableMap))) {
/* 112 */       localObject1 = (ObservableMap)paramObject1;
/* 113 */       localObject2 = (ObservableMap)paramObject2;
/* 114 */       localObject3 = new MapContentBinding((ObservableMap)localObject1, (ObservableMap)localObject2);
/* 115 */       ((ObservableMap)localObject1).removeListener((MapChangeListener)localObject3);
/* 116 */       ((ObservableMap)localObject2).removeListener((MapChangeListener)localObject3);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class MapContentBinding<K, V>
/*     */     implements MapChangeListener<K, V>, WeakListener
/*     */   {
/*     */     private final WeakReference<ObservableMap<K, V>> propertyRef1;
/*     */     private final WeakReference<ObservableMap<K, V>> propertyRef2;
/* 305 */     private boolean updating = false;
/*     */ 
/*     */     public MapContentBinding(ObservableMap<K, V> paramObservableMap1, ObservableMap<K, V> paramObservableMap2)
/*     */     {
/* 309 */       this.propertyRef1 = new WeakReference(paramObservableMap1);
/* 310 */       this.propertyRef2 = new WeakReference(paramObservableMap2);
/*     */     }
/*     */ 
/*     */     public void onChanged(MapChangeListener.Change<? extends K, ? extends V> paramChange)
/*     */     {
/* 315 */       if (!this.updating) {
/* 316 */         ObservableMap localObservableMap1 = (ObservableMap)this.propertyRef1.get();
/* 317 */         ObservableMap localObservableMap2 = (ObservableMap)this.propertyRef2.get();
/* 318 */         if ((localObservableMap1 == null) || (localObservableMap2 == null)) {
/* 319 */           if (localObservableMap1 != null) {
/* 320 */             localObservableMap1.removeListener(this);
/*     */           }
/* 322 */           if (localObservableMap2 != null)
/* 323 */             localObservableMap2.removeListener(this);
/*     */         }
/*     */         else {
/*     */           try {
/* 327 */             this.updating = true;
/* 328 */             ObservableMap localObservableMap3 = localObservableMap1 == paramChange.getMap() ? localObservableMap2 : localObservableMap1;
/* 329 */             if (paramChange.wasRemoved())
/* 330 */               localObservableMap3.remove(paramChange.getKey());
/*     */             else
/* 332 */               localObservableMap3.put(paramChange.getKey(), paramChange.getValueAdded());
/*     */           }
/*     */           finally {
/* 335 */             this.updating = false;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     public boolean wasGarbageCollected()
/*     */     {
/* 343 */       return (this.propertyRef1.get() == null) || (this.propertyRef2.get() == null);
/*     */     }
/*     */ 
/*     */     public int hashCode()
/*     */     {
/* 348 */       ObservableMap localObservableMap1 = (ObservableMap)this.propertyRef1.get();
/* 349 */       ObservableMap localObservableMap2 = (ObservableMap)this.propertyRef2.get();
/* 350 */       int i = localObservableMap1 == null ? 0 : localObservableMap1.hashCode();
/* 351 */       int j = localObservableMap2 == null ? 0 : localObservableMap2.hashCode();
/* 352 */       return i * j;
/*     */     }
/*     */ 
/*     */     public boolean equals(Object paramObject)
/*     */     {
/* 357 */       if (this == paramObject) {
/* 358 */         return true;
/*     */       }
/*     */ 
/* 361 */       Object localObject1 = this.propertyRef1.get();
/* 362 */       Object localObject2 = this.propertyRef2.get();
/* 363 */       if ((localObject1 == null) || (localObject2 == null)) {
/* 364 */         return false;
/*     */       }
/*     */ 
/* 367 */       if ((paramObject instanceof MapContentBinding)) {
/* 368 */         MapContentBinding localMapContentBinding = (MapContentBinding)paramObject;
/* 369 */         Object localObject3 = localMapContentBinding.propertyRef1.get();
/* 370 */         Object localObject4 = localMapContentBinding.propertyRef2.get();
/* 371 */         if ((localObject3 == null) || (localObject4 == null)) {
/* 372 */           return false;
/*     */         }
/*     */ 
/* 375 */         if ((localObject1 == localObject3) && (localObject2 == localObject4)) {
/* 376 */           return true;
/*     */         }
/* 378 */         if ((localObject1 == localObject4) && (localObject2 == localObject3)) {
/* 379 */           return true;
/*     */         }
/*     */       }
/* 382 */       return false;
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class SetContentBinding<E>
/*     */     implements SetChangeListener<E>, WeakListener
/*     */   {
/*     */     private final WeakReference<ObservableSet<E>> propertyRef1;
/*     */     private final WeakReference<ObservableSet<E>> propertyRef2;
/* 219 */     private boolean updating = false;
/*     */ 
/*     */     public SetContentBinding(ObservableSet<E> paramObservableSet1, ObservableSet<E> paramObservableSet2)
/*     */     {
/* 223 */       this.propertyRef1 = new WeakReference(paramObservableSet1);
/* 224 */       this.propertyRef2 = new WeakReference(paramObservableSet2);
/*     */     }
/*     */ 
/*     */     public void onChanged(SetChangeListener.Change<? extends E> paramChange)
/*     */     {
/* 229 */       if (!this.updating) {
/* 230 */         ObservableSet localObservableSet1 = (ObservableSet)this.propertyRef1.get();
/* 231 */         ObservableSet localObservableSet2 = (ObservableSet)this.propertyRef2.get();
/* 232 */         if ((localObservableSet1 == null) || (localObservableSet2 == null)) {
/* 233 */           if (localObservableSet1 != null) {
/* 234 */             localObservableSet1.removeListener(this);
/*     */           }
/* 236 */           if (localObservableSet2 != null)
/* 237 */             localObservableSet2.removeListener(this);
/*     */         }
/*     */         else {
/*     */           try {
/* 241 */             this.updating = true;
/* 242 */             ObservableSet localObservableSet3 = localObservableSet1 == paramChange.getSet() ? localObservableSet2 : localObservableSet1;
/* 243 */             if (paramChange.wasRemoved())
/* 244 */               localObservableSet3.remove(paramChange.getElementRemoved());
/*     */             else
/* 246 */               localObservableSet3.add(paramChange.getElementAdded());
/*     */           }
/*     */           finally {
/* 249 */             this.updating = false;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     public boolean wasGarbageCollected()
/*     */     {
/* 257 */       return (this.propertyRef1.get() == null) || (this.propertyRef2.get() == null);
/*     */     }
/*     */ 
/*     */     public int hashCode()
/*     */     {
/* 262 */       ObservableSet localObservableSet1 = (ObservableSet)this.propertyRef1.get();
/* 263 */       ObservableSet localObservableSet2 = (ObservableSet)this.propertyRef2.get();
/* 264 */       int i = localObservableSet1 == null ? 0 : localObservableSet1.hashCode();
/* 265 */       int j = localObservableSet2 == null ? 0 : localObservableSet2.hashCode();
/* 266 */       return i * j;
/*     */     }
/*     */ 
/*     */     public boolean equals(Object paramObject)
/*     */     {
/* 271 */       if (this == paramObject) {
/* 272 */         return true;
/*     */       }
/*     */ 
/* 275 */       Object localObject1 = this.propertyRef1.get();
/* 276 */       Object localObject2 = this.propertyRef2.get();
/* 277 */       if ((localObject1 == null) || (localObject2 == null)) {
/* 278 */         return false;
/*     */       }
/*     */ 
/* 281 */       if ((paramObject instanceof SetContentBinding)) {
/* 282 */         SetContentBinding localSetContentBinding = (SetContentBinding)paramObject;
/* 283 */         Object localObject3 = localSetContentBinding.propertyRef1.get();
/* 284 */         Object localObject4 = localSetContentBinding.propertyRef2.get();
/* 285 */         if ((localObject3 == null) || (localObject4 == null)) {
/* 286 */           return false;
/*     */         }
/*     */ 
/* 289 */         if ((localObject1 == localObject3) && (localObject2 == localObject4)) {
/* 290 */           return true;
/*     */         }
/* 292 */         if ((localObject1 == localObject4) && (localObject2 == localObject3)) {
/* 293 */           return true;
/*     */         }
/*     */       }
/* 296 */       return false;
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class ListContentBinding<E>
/*     */     implements ListChangeListener<E>, WeakListener
/*     */   {
/*     */     private final WeakReference<ObservableList<E>> propertyRef1;
/*     */     private final WeakReference<ObservableList<E>> propertyRef2;
/* 125 */     private boolean updating = false;
/*     */ 
/*     */     public ListContentBinding(ObservableList<E> paramObservableList1, ObservableList<E> paramObservableList2)
/*     */     {
/* 129 */       this.propertyRef1 = new WeakReference(paramObservableList1);
/* 130 */       this.propertyRef2 = new WeakReference(paramObservableList2);
/*     */     }
/*     */ 
/*     */     public void onChanged(ListChangeListener.Change<? extends E> paramChange)
/*     */     {
/* 135 */       if (!this.updating) {
/* 136 */         ObservableList localObservableList1 = (ObservableList)this.propertyRef1.get();
/* 137 */         ObservableList localObservableList2 = (ObservableList)this.propertyRef2.get();
/* 138 */         if ((localObservableList1 == null) || (localObservableList2 == null)) {
/* 139 */           if (localObservableList1 != null) {
/* 140 */             localObservableList1.removeListener(this);
/*     */           }
/* 142 */           if (localObservableList2 != null)
/* 143 */             localObservableList2.removeListener(this);
/*     */         }
/*     */         else {
/*     */           try {
/* 147 */             this.updating = true;
/* 148 */             ObservableList localObservableList3 = localObservableList1 == paramChange.getList() ? localObservableList2 : localObservableList1;
/* 149 */             while (paramChange.next())
/* 150 */               if (paramChange.wasPermutated()) {
/* 151 */                 localObservableList3.remove(paramChange.getFrom(), paramChange.getTo());
/* 152 */                 localObservableList3.addAll(paramChange.getFrom(), paramChange.getList().subList(paramChange.getFrom(), paramChange.getTo()));
/*     */               } else {
/* 154 */                 if (paramChange.wasRemoved()) {
/* 155 */                   localObservableList3.remove(paramChange.getFrom(), paramChange.getFrom() + paramChange.getRemovedSize());
/*     */                 }
/* 157 */                 if (paramChange.wasAdded())
/* 158 */                   localObservableList3.addAll(paramChange.getFrom(), paramChange.getAddedSubList());
/*     */               }
/*     */           }
/*     */           finally
/*     */           {
/* 163 */             this.updating = false;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     public boolean wasGarbageCollected()
/*     */     {
/* 171 */       return (this.propertyRef1.get() == null) || (this.propertyRef2.get() == null);
/*     */     }
/*     */ 
/*     */     public int hashCode()
/*     */     {
/* 176 */       ObservableList localObservableList1 = (ObservableList)this.propertyRef1.get();
/* 177 */       ObservableList localObservableList2 = (ObservableList)this.propertyRef2.get();
/* 178 */       int i = localObservableList1 == null ? 0 : localObservableList1.hashCode();
/* 179 */       int j = localObservableList2 == null ? 0 : localObservableList2.hashCode();
/* 180 */       return i * j;
/*     */     }
/*     */ 
/*     */     public boolean equals(Object paramObject)
/*     */     {
/* 185 */       if (this == paramObject) {
/* 186 */         return true;
/*     */       }
/*     */ 
/* 189 */       Object localObject1 = this.propertyRef1.get();
/* 190 */       Object localObject2 = this.propertyRef2.get();
/* 191 */       if ((localObject1 == null) || (localObject2 == null)) {
/* 192 */         return false;
/*     */       }
/*     */ 
/* 195 */       if ((paramObject instanceof ListContentBinding)) {
/* 196 */         ListContentBinding localListContentBinding = (ListContentBinding)paramObject;
/* 197 */         Object localObject3 = localListContentBinding.propertyRef1.get();
/* 198 */         Object localObject4 = localListContentBinding.propertyRef2.get();
/* 199 */         if ((localObject3 == null) || (localObject4 == null)) {
/* 200 */           return false;
/*     */         }
/*     */ 
/* 203 */         if ((localObject1 == localObject3) && (localObject2 == localObject4)) {
/* 204 */           return true;
/*     */         }
/* 206 */         if ((localObject1 == localObject4) && (localObject2 == localObject3)) {
/* 207 */           return true;
/*     */         }
/*     */       }
/* 210 */       return false;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.binding.BidirectionalContentBinding
 * JD-Core Version:    0.6.2
 */