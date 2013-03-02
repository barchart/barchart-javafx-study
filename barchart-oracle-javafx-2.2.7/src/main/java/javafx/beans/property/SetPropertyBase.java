/*     */ package javafx.beans.property;
/*     */ 
/*     */ import com.sun.javafx.binding.SetExpressionHelper;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.Observable;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ import javafx.collections.ObservableSet;
/*     */ import javafx.collections.SetChangeListener;
/*     */ import javafx.collections.SetChangeListener.Change;
/*     */ 
/*     */ public abstract class SetPropertyBase<E> extends SetProperty<E>
/*     */ {
/*  71 */   private final SetChangeListener<E> setChangeListener = new SetChangeListener()
/*     */   {
/*     */     public void onChanged(SetChangeListener.Change<? extends E> paramAnonymousChange) {
/*  74 */       SetPropertyBase.this.invalidateProperties();
/*  75 */       SetPropertyBase.this.invalidated();
/*  76 */       SetPropertyBase.this.fireValueChangedEvent(paramAnonymousChange);
/*     */     }
/*  71 */   };
/*     */   private ObservableSet<E> value;
/*  81 */   private ObservableValue<? extends ObservableSet<E>> observable = null;
/*  82 */   private InvalidationListener listener = null;
/*  83 */   private boolean valid = true;
/*  84 */   private SetExpressionHelper<E> helper = null;
/*     */   private SetPropertyBase<E>.SizeProperty size0;
/*     */   private SetPropertyBase<E>.EmptyProperty empty0;
/*     */ 
/*     */   public SetPropertyBase()
/*     */   {
/*     */   }
/*     */ 
/*     */   public SetPropertyBase(ObservableSet<E> paramObservableSet)
/*     */   {
/* 101 */     this.value = paramObservableSet;
/* 102 */     if (paramObservableSet != null)
/* 103 */       paramObservableSet.addListener(this.setChangeListener);
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
/* 168 */     this.helper = SetExpressionHelper.addListener(this.helper, this, paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(InvalidationListener paramInvalidationListener)
/*     */   {
/* 173 */     this.helper = SetExpressionHelper.removeListener(this.helper, paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public void addListener(ChangeListener<? super ObservableSet<E>> paramChangeListener)
/*     */   {
/* 178 */     this.helper = SetExpressionHelper.addListener(this.helper, this, paramChangeListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(ChangeListener<? super ObservableSet<E>> paramChangeListener)
/*     */   {
/* 183 */     this.helper = SetExpressionHelper.removeListener(this.helper, paramChangeListener);
/*     */   }
/*     */ 
/*     */   public void addListener(SetChangeListener<? super E> paramSetChangeListener)
/*     */   {
/* 188 */     this.helper = SetExpressionHelper.addListener(this.helper, this, paramSetChangeListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(SetChangeListener<? super E> paramSetChangeListener)
/*     */   {
/* 193 */     this.helper = SetExpressionHelper.removeListener(this.helper, paramSetChangeListener);
/*     */   }
/*     */ 
/*     */   protected void fireValueChangedEvent()
/*     */   {
/* 207 */     SetExpressionHelper.fireValueChangedEvent(this.helper);
/*     */   }
/*     */ 
/*     */   protected void fireValueChangedEvent(SetChangeListener.Change<? extends E> paramChange)
/*     */   {
/* 221 */     SetExpressionHelper.fireValueChangedEvent(this.helper, paramChange);
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
/*     */   private void markInvalid(ObservableSet<E> paramObservableSet)
/*     */   {
/* 234 */     if (this.valid) {
/* 235 */       if (paramObservableSet != null) {
/* 236 */         paramObservableSet.removeListener(this.setChangeListener);
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
/*     */   public ObservableSet<E> get()
/*     */   {
/* 259 */     if (!this.valid) {
/* 260 */       this.value = (this.observable == null ? this.value : (ObservableSet)this.observable.getValue());
/* 261 */       this.valid = true;
/* 262 */       if (this.value != null) {
/* 263 */         this.value.addListener(this.setChangeListener);
/*     */       }
/*     */     }
/* 266 */     return this.value;
/*     */   }
/*     */ 
/*     */   public void set(ObservableSet<E> paramObservableSet)
/*     */   {
/* 271 */     if (isBound()) {
/* 272 */       throw new RuntimeException("A bound value cannot be set.");
/*     */     }
/* 274 */     if (this.value != paramObservableSet) {
/* 275 */       ObservableSet localObservableSet = this.value;
/* 276 */       this.value = paramObservableSet;
/* 277 */       markInvalid(localObservableSet);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isBound()
/*     */   {
/* 283 */     return this.observable != null;
/*     */   }
/*     */ 
/*     */   public void bind(ObservableValue<? extends ObservableSet<E>> paramObservableValue)
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
/* 305 */       this.value = ((ObservableSet)this.observable.getValue());
/* 306 */       this.observable.removeListener(this.listener);
/* 307 */       this.observable = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 317 */     Object localObject = getBean();
/* 318 */     String str = getName();
/* 319 */     StringBuilder localStringBuilder = new StringBuilder("SetProperty [");
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
/* 343 */       SetPropertyBase.this.markInvalid(SetPropertyBase.this.value);
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
/* 148 */       return SetPropertyBase.this.isEmpty();
/*     */     }
/*     */ 
/*     */     public Object getBean()
/*     */     {
/* 153 */       return SetPropertyBase.this;
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
/* 118 */       return SetPropertyBase.this.size();
/*     */     }
/*     */ 
/*     */     public Object getBean()
/*     */     {
/* 123 */       return SetPropertyBase.this;
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
 * Qualified Name:     javafx.beans.property.SetPropertyBase
 * JD-Core Version:    0.6.2
 */