/*     */ package javafx.beans.property;
/*     */ 
/*     */ import com.sun.javafx.binding.MapExpressionHelper;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ import javafx.collections.MapChangeListener;
/*     */ import javafx.collections.MapChangeListener.Change;
/*     */ import javafx.collections.ObservableMap;
/*     */ 
/*     */ public class ReadOnlyMapWrapper<K, V> extends SimpleMapProperty<K, V>
/*     */ {
/*     */   private ReadOnlyMapWrapper<K, V>.ReadOnlyPropertyImpl readOnlyProperty;
/*     */ 
/*     */   public ReadOnlyMapWrapper()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ReadOnlyMapWrapper(ObservableMap<K, V> paramObservableMap)
/*     */   {
/*  80 */     super(paramObservableMap);
/*     */   }
/*     */ 
/*     */   public ReadOnlyMapWrapper(Object paramObject, String paramString)
/*     */   {
/*  92 */     super(paramObject, paramString);
/*     */   }
/*     */ 
/*     */   public ReadOnlyMapWrapper(Object paramObject, String paramString, ObservableMap<K, V> paramObservableMap)
/*     */   {
/* 107 */     super(paramObject, paramString, paramObservableMap);
/*     */   }
/*     */ 
/*     */   public ReadOnlyMapProperty<K, V> getReadOnlyProperty()
/*     */   {
/* 117 */     if (this.readOnlyProperty == null) {
/* 118 */       this.readOnlyProperty = new ReadOnlyPropertyImpl(null);
/*     */     }
/* 120 */     return this.readOnlyProperty;
/*     */   }
/*     */ 
/*     */   public void addListener(InvalidationListener paramInvalidationListener)
/*     */   {
/* 128 */     getReadOnlyProperty().addListener(paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(InvalidationListener paramInvalidationListener)
/*     */   {
/* 136 */     if (this.readOnlyProperty != null)
/* 137 */       this.readOnlyProperty.removeListener(paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public void addListener(ChangeListener<? super ObservableMap<K, V>> paramChangeListener)
/*     */   {
/* 146 */     getReadOnlyProperty().addListener(paramChangeListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(ChangeListener<? super ObservableMap<K, V>> paramChangeListener)
/*     */   {
/* 154 */     if (this.readOnlyProperty != null)
/* 155 */       this.readOnlyProperty.removeListener(paramChangeListener);
/*     */   }
/*     */ 
/*     */   public void addListener(MapChangeListener<? super K, ? super V> paramMapChangeListener)
/*     */   {
/* 164 */     getReadOnlyProperty().addListener(paramMapChangeListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(MapChangeListener<? super K, ? super V> paramMapChangeListener)
/*     */   {
/* 172 */     if (this.readOnlyProperty != null)
/* 173 */       this.readOnlyProperty.removeListener(paramMapChangeListener);
/*     */   }
/*     */ 
/*     */   protected void fireValueChangedEvent()
/*     */   {
/* 182 */     if (this.readOnlyProperty != null)
/* 183 */       this.readOnlyProperty.fireValueChangedEvent();
/*     */   }
/*     */ 
/*     */   protected void fireValueChangedEvent(MapChangeListener.Change<? extends K, ? extends V> paramChange)
/*     */   {
/* 192 */     if (this.readOnlyProperty != null)
/* 193 */       this.readOnlyProperty.fireValueChangedEvent(paramChange);
/*     */   }
/*     */ 
/*     */   private class ReadOnlyPropertyImpl extends ReadOnlyMapProperty<K, V>
/*     */   {
/* 199 */     private MapExpressionHelper<K, V> helper = null;
/*     */ 
/*     */     private ReadOnlyPropertyImpl() {
/*     */     }
/* 203 */     public ObservableMap<K, V> get() { return ReadOnlyMapWrapper.this.get(); }
/*     */ 
/*     */ 
/*     */     public void addListener(InvalidationListener paramInvalidationListener)
/*     */     {
/* 208 */       this.helper = MapExpressionHelper.addListener(this.helper, this, paramInvalidationListener);
/*     */     }
/*     */ 
/*     */     public void removeListener(InvalidationListener paramInvalidationListener)
/*     */     {
/* 213 */       this.helper = MapExpressionHelper.removeListener(this.helper, paramInvalidationListener);
/*     */     }
/*     */ 
/*     */     public void addListener(ChangeListener<? super ObservableMap<K, V>> paramChangeListener)
/*     */     {
/* 218 */       this.helper = MapExpressionHelper.addListener(this.helper, this, paramChangeListener);
/*     */     }
/*     */ 
/*     */     public void removeListener(ChangeListener<? super ObservableMap<K, V>> paramChangeListener)
/*     */     {
/* 223 */       this.helper = MapExpressionHelper.removeListener(this.helper, paramChangeListener);
/*     */     }
/*     */ 
/*     */     public void addListener(MapChangeListener<? super K, ? super V> paramMapChangeListener)
/*     */     {
/* 228 */       this.helper = MapExpressionHelper.addListener(this.helper, this, paramMapChangeListener);
/*     */     }
/*     */ 
/*     */     public void removeListener(MapChangeListener<? super K, ? super V> paramMapChangeListener)
/*     */     {
/* 233 */       this.helper = MapExpressionHelper.removeListener(this.helper, paramMapChangeListener);
/*     */     }
/*     */ 
/*     */     private void fireValueChangedEvent() {
/* 237 */       MapExpressionHelper.fireValueChangedEvent(this.helper);
/*     */     }
/*     */ 
/*     */     private void fireValueChangedEvent(MapChangeListener.Change<? extends K, ? extends V> paramChange) {
/* 241 */       MapExpressionHelper.fireValueChangedEvent(this.helper, paramChange);
/*     */     }
/*     */ 
/*     */     public Object getBean()
/*     */     {
/* 246 */       return ReadOnlyMapWrapper.this.getBean();
/*     */     }
/*     */ 
/*     */     public String getName()
/*     */     {
/* 251 */       return ReadOnlyMapWrapper.this.getName();
/*     */     }
/*     */ 
/*     */     public ReadOnlyIntegerProperty sizeProperty()
/*     */     {
/* 256 */       return ReadOnlyMapWrapper.this.sizeProperty();
/*     */     }
/*     */ 
/*     */     public ReadOnlyBooleanProperty emptyProperty()
/*     */     {
/* 261 */       return ReadOnlyMapWrapper.this.emptyProperty();
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.property.ReadOnlyMapWrapper
 * JD-Core Version:    0.6.2
 */