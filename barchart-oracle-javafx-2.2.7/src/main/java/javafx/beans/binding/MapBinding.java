/*     */ package javafx.beans.binding;
/*     */ 
/*     */ import com.sun.javafx.binding.BindingHelperObserver;
/*     */ import com.sun.javafx.binding.MapExpressionHelper;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.Observable;
/*     */ import javafx.beans.property.ReadOnlyBooleanProperty;
/*     */ import javafx.beans.property.ReadOnlyBooleanPropertyBase;
/*     */ import javafx.beans.property.ReadOnlyIntegerProperty;
/*     */ import javafx.beans.property.ReadOnlyIntegerPropertyBase;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ import javafx.collections.FXCollections;
/*     */ import javafx.collections.MapChangeListener;
/*     */ import javafx.collections.MapChangeListener.Change;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.collections.ObservableMap;
/*     */ 
/*     */ public abstract class MapBinding<K, V> extends MapExpression<K, V>
/*     */   implements Binding<ObservableMap<K, V>>
/*     */ {
/*     */   private final MapChangeListener<K, V> mapChangeListener;
/*     */   private ObservableMap<K, V> value;
/*     */   private boolean valid;
/*     */   private BindingHelperObserver observer;
/*     */   private MapExpressionHelper<K, V> helper;
/*     */   private MapBinding<K, V>.SizeProperty size0;
/*     */   private MapBinding<K, V>.EmptyProperty empty0;
/*     */ 
/*     */   public MapBinding()
/*     */   {
/*  90 */     this.mapChangeListener = new MapChangeListener()
/*     */     {
/*     */       public void onChanged(MapChangeListener.Change<? extends K, ? extends V> paramAnonymousChange) {
/*  93 */         MapBinding.this.invalidateProperties();
/*  94 */         MapBinding.this.onInvalidating();
/*  95 */         MapExpressionHelper.fireValueChangedEvent(MapBinding.this.helper, paramAnonymousChange);
/*     */       }
/*     */     };
/* 100 */     this.valid = false;
/*     */ 
/* 102 */     this.helper = null;
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
/*     */   protected final void bind(Observable[] paramArrayOfObservable)
/*     */   {
/* 204 */     if ((paramArrayOfObservable != null) && (paramArrayOfObservable.length > 0)) {
/* 205 */       if (this.observer == null) {
/* 206 */         this.observer = new BindingHelperObserver(this);
/*     */       }
/* 208 */       for (Observable localObservable : paramArrayOfObservable)
/* 209 */         if (localObservable != null)
/* 210 */           localObservable.addListener(this.observer);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected final void unbind(Observable[] paramArrayOfObservable)
/*     */   {
/* 223 */     if (this.observer != null) {
/* 224 */       for (Observable localObservable : paramArrayOfObservable) {
/* 225 */         if (localObservable != null) {
/* 226 */           localObservable.removeListener(this.observer);
/*     */         }
/*     */       }
/* 229 */       this.observer = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void dispose()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ObservableList<?> getDependencies()
/*     */   {
/* 249 */     return FXCollections.emptyObservableList();
/*     */   }
/*     */ 
/*     */   public final ObservableMap<K, V> get()
/*     */   {
/* 262 */     if (!this.valid) {
/* 263 */       this.value = computeValue();
/* 264 */       this.valid = true;
/* 265 */       if (this.value != null) {
/* 266 */         this.value.addListener(this.mapChangeListener);
/*     */       }
/*     */     }
/* 269 */     return this.value;
/*     */   }
/*     */ 
/*     */   protected void onInvalidating()
/*     */   {
/*     */   }
/*     */ 
/*     */   private void invalidateProperties()
/*     */   {
/* 281 */     if (this.size0 != null) {
/* 282 */       this.size0.fireValueChangedEvent();
/*     */     }
/* 284 */     if (this.empty0 != null)
/* 285 */       this.empty0.fireValueChangedEvent();
/*     */   }
/*     */ 
/*     */   public final void invalidate()
/*     */   {
/* 291 */     if (this.valid) {
/* 292 */       if (this.value != null) {
/* 293 */         this.value.removeListener(this.mapChangeListener);
/*     */       }
/* 295 */       this.valid = false;
/* 296 */       invalidateProperties();
/* 297 */       onInvalidating();
/* 298 */       MapExpressionHelper.fireValueChangedEvent(this.helper);
/*     */     }
/*     */   }
/*     */ 
/*     */   public final boolean isValid()
/*     */   {
/* 304 */     return this.valid;
/*     */   }
/*     */ 
/*     */   protected abstract ObservableMap<K, V> computeValue();
/*     */ 
/*     */   public String toString()
/*     */   {
/* 323 */     return this.valid ? "MapBinding [value: " + get() + "]" : "MapBinding [invalid]";
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
/* 148 */       return MapBinding.this.isEmpty();
/*     */     }
/*     */ 
/*     */     public Object getBean()
/*     */     {
/* 153 */       return MapBinding.this;
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
/* 118 */       return MapBinding.this.size();
/*     */     }
/*     */ 
/*     */     public Object getBean()
/*     */     {
/* 123 */       return MapBinding.this;
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
 * Qualified Name:     javafx.beans.binding.MapBinding
 * JD-Core Version:    0.6.2
 */