/*     */ package javafx.beans.binding;
/*     */ 
/*     */ import com.sun.javafx.binding.StringFormatter;
/*     */ import java.util.AbstractMap;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.Observable;
/*     */ import javafx.beans.property.ReadOnlyBooleanProperty;
/*     */ import javafx.beans.property.ReadOnlyIntegerProperty;
/*     */ import javafx.beans.value.ObservableMapValue;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ import javafx.collections.FXCollections;
/*     */ import javafx.collections.MapChangeListener;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.collections.ObservableMap;
/*     */ 
/*     */ public abstract class MapExpression<K, V>
/*     */   implements ObservableMapValue<K, V>
/*     */ {
/*  79 */   private static final ObservableMap EMPTY_MAP = new EmptyObservableMap(null);
/*     */ 
/*     */   public ObservableMap<K, V> getValue()
/*     */   {
/* 111 */     return (ObservableMap)get();
/*     */   }
/*     */ 
/*     */   public static <K, V> MapExpression<K, V> mapExpression(ObservableMapValue<K, V> paramObservableMapValue)
/*     */   {
/* 130 */     if (paramObservableMapValue == null) {
/* 131 */       throw new NullPointerException("Map must be specified.");
/*     */     }
/* 133 */     return (paramObservableMapValue instanceof MapExpression) ? (MapExpression)paramObservableMapValue : new MapBinding()
/*     */     {
/*     */       public void dispose()
/*     */       {
/* 141 */         super.unbind(new Observable[] { this.val$value });
/*     */       }
/*     */ 
/*     */       protected ObservableMap<K, V> computeValue()
/*     */       {
/* 146 */         return (ObservableMap)this.val$value.get();
/*     */       }
/*     */ 
/*     */       public ObservableList<?> getDependencies()
/*     */       {
/* 152 */         return FXCollections.singletonObservableList(this.val$value);
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public int getSize()
/*     */   {
/* 161 */     return size();
/*     */   }
/*     */ 
/*     */   public abstract ReadOnlyIntegerProperty sizeProperty();
/*     */ 
/*     */   public abstract ReadOnlyBooleanProperty emptyProperty();
/*     */ 
/*     */   public ObjectBinding<V> valueAt(K paramK)
/*     */   {
/* 178 */     return Bindings.valueAt(this, paramK);
/*     */   }
/*     */ 
/*     */   public ObjectBinding<V> valueAt(ObservableValue<K> paramObservableValue)
/*     */   {
/* 189 */     return Bindings.valueAt(this, paramObservableValue);
/*     */   }
/*     */ 
/*     */   public BooleanBinding isEqualTo(ObservableMap<?, ?> paramObservableMap)
/*     */   {
/* 203 */     return Bindings.equal(this, paramObservableMap);
/*     */   }
/*     */ 
/*     */   public BooleanBinding isNotEqualTo(ObservableMap<?, ?> paramObservableMap)
/*     */   {
/* 217 */     return Bindings.notEqual(this, paramObservableMap);
/*     */   }
/*     */ 
/*     */   public BooleanBinding isNull()
/*     */   {
/* 226 */     return Bindings.isNull(this);
/*     */   }
/*     */ 
/*     */   public BooleanBinding isNotNull()
/*     */   {
/* 235 */     return Bindings.isNotNull(this);
/*     */   }
/*     */ 
/*     */   public StringBinding asString()
/*     */   {
/* 247 */     return (StringBinding)StringFormatter.convert(this);
/*     */   }
/*     */ 
/*     */   public int size()
/*     */   {
/* 252 */     ObservableMap localObservableMap = (ObservableMap)get();
/* 253 */     return localObservableMap == null ? EMPTY_MAP.size() : localObservableMap.size();
/*     */   }
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/* 258 */     ObservableMap localObservableMap = (ObservableMap)get();
/* 259 */     return localObservableMap == null ? EMPTY_MAP.isEmpty() : localObservableMap.isEmpty();
/*     */   }
/*     */ 
/*     */   public boolean containsKey(Object paramObject)
/*     */   {
/* 264 */     ObservableMap localObservableMap = (ObservableMap)get();
/* 265 */     return localObservableMap == null ? EMPTY_MAP.containsKey(paramObject) : localObservableMap.containsKey(paramObject);
/*     */   }
/*     */ 
/*     */   public boolean containsValue(Object paramObject)
/*     */   {
/* 270 */     ObservableMap localObservableMap = (ObservableMap)get();
/* 271 */     return localObservableMap == null ? EMPTY_MAP.containsValue(paramObject) : localObservableMap.containsValue(paramObject);
/*     */   }
/*     */ 
/*     */   public V put(K paramK, V paramV)
/*     */   {
/* 276 */     ObservableMap localObservableMap = (ObservableMap)get();
/* 277 */     return localObservableMap == null ? EMPTY_MAP.put(paramK, paramV) : localObservableMap.put(paramK, paramV);
/*     */   }
/*     */ 
/*     */   public V remove(Object paramObject)
/*     */   {
/* 282 */     ObservableMap localObservableMap = (ObservableMap)get();
/* 283 */     return localObservableMap == null ? EMPTY_MAP.remove(paramObject) : localObservableMap.remove(paramObject);
/*     */   }
/*     */ 
/*     */   public void putAll(Map<? extends K, ? extends V> paramMap)
/*     */   {
/* 288 */     ObservableMap localObservableMap = (ObservableMap)get();
/* 289 */     if (localObservableMap == null)
/* 290 */       EMPTY_MAP.putAll(paramMap);
/*     */     else
/* 292 */       localObservableMap.putAll(paramMap);
/*     */   }
/*     */ 
/*     */   public void clear()
/*     */   {
/* 298 */     ObservableMap localObservableMap = (ObservableMap)get();
/* 299 */     if (localObservableMap == null)
/* 300 */       EMPTY_MAP.clear();
/*     */     else
/* 302 */       localObservableMap.clear();
/*     */   }
/*     */ 
/*     */   public Set<K> keySet()
/*     */   {
/* 308 */     ObservableMap localObservableMap = (ObservableMap)get();
/* 309 */     return localObservableMap == null ? EMPTY_MAP.keySet() : localObservableMap.keySet();
/*     */   }
/*     */ 
/*     */   public Collection<V> values()
/*     */   {
/* 314 */     ObservableMap localObservableMap = (ObservableMap)get();
/* 315 */     return localObservableMap == null ? EMPTY_MAP.values() : localObservableMap.values();
/*     */   }
/*     */ 
/*     */   public Set<Map.Entry<K, V>> entrySet()
/*     */   {
/* 320 */     ObservableMap localObservableMap = (ObservableMap)get();
/* 321 */     return localObservableMap == null ? EMPTY_MAP.entrySet() : localObservableMap.entrySet();
/*     */   }
/*     */ 
/*     */   public V get(Object paramObject)
/*     */   {
/* 326 */     ObservableMap localObservableMap = (ObservableMap)get();
/* 327 */     return localObservableMap == null ? EMPTY_MAP.get(paramObject) : localObservableMap.get(paramObject);
/*     */   }
/*     */ 
/*     */   private static class EmptyObservableMap<K, V> extends AbstractMap<K, V>
/*     */     implements ObservableMap<K, V>
/*     */   {
/*     */     public Set<Map.Entry<K, V>> entrySet()
/*     */     {
/*  85 */       return Collections.emptySet();
/*     */     }
/*     */ 
/*     */     public void addListener(MapChangeListener<? super K, ? super V> paramMapChangeListener)
/*     */     {
/*     */     }
/*     */ 
/*     */     public void removeListener(MapChangeListener<? super K, ? super V> paramMapChangeListener)
/*     */     {
/*     */     }
/*     */ 
/*     */     public void addListener(InvalidationListener paramInvalidationListener)
/*     */     {
/*     */     }
/*     */ 
/*     */     public void removeListener(InvalidationListener paramInvalidationListener)
/*     */     {
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.binding.MapExpression
 * JD-Core Version:    0.6.2
 */