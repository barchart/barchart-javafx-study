/*     */ package javafx.beans.property;
/*     */ 
/*     */ import com.sun.javafx.binding.MapExpressionHelper;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.Observable;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ import javafx.collections.MapChangeListener;
/*     */ import javafx.collections.MapChangeListener.Change;
/*     */ import javafx.collections.ObservableMap;
/*     */ 
/*     */ public abstract class MapPropertyBase<K, V> extends MapProperty<K, V>
/*     */ {
/*  71 */   private final MapChangeListener<K, V> mapChangeListener = new MapChangeListener()
/*     */   {
/*     */     public void onChanged(MapChangeListener.Change<? extends K, ? extends V> paramAnonymousChange) {
/*  74 */       MapPropertyBase.this.invalidateProperties();
/*  75 */       MapPropertyBase.this.invalidated();
/*  76 */       MapPropertyBase.this.fireValueChangedEvent(paramAnonymousChange);
/*     */     }
/*  71 */   };
/*     */   private ObservableMap<K, V> value;
/*  81 */   private ObservableValue<? extends ObservableMap<K, V>> observable = null;
/*  82 */   private InvalidationListener listener = null;
/*  83 */   private boolean valid = true;
/*  84 */   private MapExpressionHelper<K, V> helper = null;
/*     */   private MapPropertyBase<K, V>.SizeProperty size0;
/*     */   private MapPropertyBase<K, V>.EmptyProperty empty0;
/*     */ 
/*     */   public MapPropertyBase()
/*     */   {
/*     */   }
/*     */ 
/*     */   public MapPropertyBase(ObservableMap<K, V> paramObservableMap)
/*     */   {
/* 101 */     this.value = paramObservableMap;
/* 102 */     if (paramObservableMap != null)
/* 103 */       paramObservableMap.addListener(this.mapChangeListener);
/*     */   }
/*     */ 
/*     */   public ReadOnlyIntegerProperty sizeProperty()
/*     */   {
/* 109 */     if (this.size0 == null) {
/* 110 */       this.size0 = new SizeProperty(null);
/*     */     }
/* 112 */     return this.size0;
/*     */   }
/*     */ 
/*     */   public ReadOnlyBooleanProperty emptyProperty()
/*     */   {
/* 138 */     if (this.empty0 == null) {
/* 139 */       this.empty0 = new EmptyProperty(null);
/*     */     }
/* 141 */     return this.empty0;
/*     */   }
/*     */ 
/*     */   public void addListener(InvalidationListener paramInvalidationListener)
/*     */   {
/* 168 */     this.helper = MapExpressionHelper.addListener(this.helper, this, paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(InvalidationListener paramInvalidationListener)
/*     */   {
/* 173 */     this.helper = MapExpressionHelper.removeListener(this.helper, paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public void addListener(ChangeListener<? super ObservableMap<K, V>> paramChangeListener)
/*     */   {
/* 178 */     this.helper = MapExpressionHelper.addListener(this.helper, this, paramChangeListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(ChangeListener<? super ObservableMap<K, V>> paramChangeListener)
/*     */   {
/* 183 */     this.helper = MapExpressionHelper.removeListener(this.helper, paramChangeListener);
/*     */   }
/*     */ 
/*     */   public void addListener(MapChangeListener<? super K, ? super V> paramMapChangeListener)
/*     */   {
/* 188 */     this.helper = MapExpressionHelper.addListener(this.helper, this, paramMapChangeListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(MapChangeListener<? super K, ? super V> paramMapChangeListener)
/*     */   {
/* 193 */     this.helper = MapExpressionHelper.removeListener(this.helper, paramMapChangeListener);
/*     */   }
/*     */ 
/*     */   protected void fireValueChangedEvent()
/*     */   {
/* 207 */     MapExpressionHelper.fireValueChangedEvent(this.helper);
/*     */   }
/*     */ 
/*     */   protected void fireValueChangedEvent(MapChangeListener.Change<? extends K, ? extends V> paramChange)
/*     */   {
/* 221 */     MapExpressionHelper.fireValueChangedEvent(this.helper, paramChange);
/*     */   }
/*     */ 
/*     */   private void invalidateProperties() {
/* 225 */     if (this.size0 != null) {
/* 226 */       this.size0.fireValueChangedEvent();
/*     */     }
/* 228 */     if (this.empty0 != null)
/* 229 */       this.empty0.fireValueChangedEvent();
/*     */   }
/*     */ 
/*     */   private void markInvalid(ObservableMap<K, V> paramObservableMap)
/*     */   {
/* 234 */     if (this.valid) {
/* 235 */       if (paramObservableMap != null) {
/* 236 */         paramObservableMap.removeListener(this.mapChangeListener);
/*     */       }
/* 238 */       this.valid = false;
/* 239 */       invalidateProperties();
/* 240 */       invalidated();
/* 241 */       fireValueChangedEvent();
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void invalidated()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ObservableMap<K, V> get()
/*     */   {
/* 259 */     if (!this.valid) {
/* 260 */       this.value = (this.observable == null ? this.value : (ObservableMap)this.observable.getValue());
/* 261 */       this.valid = true;
/* 262 */       if (this.value != null) {
/* 263 */         this.value.addListener(this.mapChangeListener);
/*     */       }
/*     */     }
/* 266 */     return this.value;
/*     */   }
/*     */ 
/*     */   public void set(ObservableMap<K, V> paramObservableMap)
/*     */   {
/* 271 */     if (isBound()) {
/* 272 */       throw new RuntimeException("A bound value cannot be set.");
/*     */     }
/* 274 */     if (this.value != paramObservableMap) {
/* 275 */       ObservableMap localObservableMap = this.value;
/* 276 */       this.value = paramObservableMap;
/* 277 */       markInvalid(localObservableMap);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isBound()
/*     */   {
/* 283 */     return this.observable != null;
/*     */   }
/*     */ 
/*     */   public void bind(ObservableValue<? extends ObservableMap<K, V>> paramObservableValue)
/*     */   {
/* 288 */     if (paramObservableValue == null) {
/* 289 */       throw new NullPointerException("Cannot bind to null");
/*     */     }
/* 291 */     if (!paramObservableValue.equals(this.observable)) {
/* 292 */       unbind();
/* 293 */       this.observable = paramObservableValue;
/* 294 */       if (this.listener == null) {
/* 295 */         this.listener = new Listener(null);
/*     */       }
/* 297 */       this.observable.addListener(this.listener);
/* 298 */       markInvalid(this.value);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void unbind()
/*     */   {
/* 304 */     if (this.observable != null) {
/* 305 */       this.value = ((ObservableMap)this.observable.getValue());
/* 306 */       this.observable.removeListener(this.listener);
/* 307 */       this.observable = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 317 */     Object localObject = getBean();
/* 318 */     String str = getName();
/* 319 */     StringBuilder localStringBuilder = new StringBuilder("MapProperty [");
/* 320 */     if (localObject != null) {
/* 321 */       localStringBuilder.append("bean: ").append(localObject).append(", ");
/*     */     }
/* 323 */     if ((str != null) && (!str.equals(""))) {
/* 324 */       localStringBuilder.append("name: ").append(str).append(", ");
/*     */     }
/* 326 */     if (isBound()) {
/* 327 */       localStringBuilder.append("bound, ");
/* 328 */       if (this.valid)
/* 329 */         localStringBuilder.append("value: ").append(get());
/*     */       else
/* 331 */         localStringBuilder.append("invalid");
/*     */     }
/*     */     else {
/* 334 */       localStringBuilder.append("value: ").append(get());
/*     */     }
/* 336 */     localStringBuilder.append("]");
/* 337 */     return localStringBuilder.toString();
/*     */   }
/*     */   private class Listener implements InvalidationListener {
/*     */     private Listener() {
/*     */     }
/*     */     public void invalidated(Observable paramObservable) {
/* 343 */       MapPropertyBase.this.markInvalid(MapPropertyBase.this.value);
/*     */     }
/*     */   }
/*     */ 
/*     */   private class EmptyProperty extends ReadOnlyBooleanPropertyBase
/*     */   {
/*     */     private EmptyProperty()
/*     */     {
/*     */     }
/*     */ 
/*     */     public boolean get()
/*     */     {
/* 148 */       return MapPropertyBase.this.isEmpty();
/*     */     }
/*     */ 
/*     */     public Object getBean()
/*     */     {
/* 153 */       return MapPropertyBase.this;
/*     */     }
/*     */ 
/*     */     public String getName()
/*     */     {
/* 158 */       return "empty";
/*     */     }
/*     */ 
/*     */     protected void fireValueChangedEvent() {
/* 162 */       super.fireValueChangedEvent();
/*     */     }
/*     */   }
/*     */ 
/*     */   private class SizeProperty extends ReadOnlyIntegerPropertyBase
/*     */   {
/*     */     private SizeProperty()
/*     */     {
/*     */     }
/*     */ 
/*     */     public int get()
/*     */     {
/* 118 */       return MapPropertyBase.this.size();
/*     */     }
/*     */ 
/*     */     public Object getBean()
/*     */     {
/* 123 */       return MapPropertyBase.this;
/*     */     }
/*     */ 
/*     */     public String getName()
/*     */     {
/* 128 */       return "size";
/*     */     }
/*     */ 
/*     */     protected void fireValueChangedEvent() {
/* 132 */       super.fireValueChangedEvent();
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.property.MapPropertyBase
 * JD-Core Version:    0.6.2
 */