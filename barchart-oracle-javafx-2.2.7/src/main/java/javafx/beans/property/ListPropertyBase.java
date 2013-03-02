/*     */ package javafx.beans.property;
/*     */ 
/*     */ import com.sun.javafx.binding.ListExpressionHelper;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.Observable;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ import javafx.collections.ListChangeListener;
/*     */ import javafx.collections.ListChangeListener.Change;
/*     */ import javafx.collections.ObservableList;
/*     */ 
/*     */ public abstract class ListPropertyBase<E> extends ListProperty<E>
/*     */ {
/*  71 */   private final ListChangeListener<E> listChangeListener = new ListChangeListener()
/*     */   {
/*     */     public void onChanged(ListChangeListener.Change<? extends E> paramAnonymousChange) {
/*  74 */       ListPropertyBase.this.invalidateProperties();
/*  75 */       ListPropertyBase.this.invalidated();
/*  76 */       ListPropertyBase.this.fireValueChangedEvent(paramAnonymousChange);
/*     */     }
/*  71 */   };
/*     */   private ObservableList<E> value;
/*  81 */   private ObservableValue<? extends ObservableList<E>> observable = null;
/*  82 */   private InvalidationListener listener = null;
/*  83 */   private boolean valid = true;
/*  84 */   private ListExpressionHelper<E> helper = null;
/*     */   private ListPropertyBase<E>.SizeProperty size0;
/*     */   private ListPropertyBase<E>.EmptyProperty empty0;
/*     */ 
/*     */   public ListPropertyBase()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ListPropertyBase(ObservableList<E> paramObservableList)
/*     */   {
/* 101 */     this.value = paramObservableList;
/* 102 */     if (paramObservableList != null)
/* 103 */       paramObservableList.addListener(this.listChangeListener);
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
/* 168 */     this.helper = ListExpressionHelper.addListener(this.helper, this, paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(InvalidationListener paramInvalidationListener)
/*     */   {
/* 173 */     this.helper = ListExpressionHelper.removeListener(this.helper, paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public void addListener(ChangeListener<? super ObservableList<E>> paramChangeListener)
/*     */   {
/* 178 */     this.helper = ListExpressionHelper.addListener(this.helper, this, paramChangeListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(ChangeListener<? super ObservableList<E>> paramChangeListener)
/*     */   {
/* 183 */     this.helper = ListExpressionHelper.removeListener(this.helper, paramChangeListener);
/*     */   }
/*     */ 
/*     */   public void addListener(ListChangeListener<? super E> paramListChangeListener)
/*     */   {
/* 188 */     this.helper = ListExpressionHelper.addListener(this.helper, this, paramListChangeListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(ListChangeListener<? super E> paramListChangeListener)
/*     */   {
/* 193 */     this.helper = ListExpressionHelper.removeListener(this.helper, paramListChangeListener);
/*     */   }
/*     */ 
/*     */   protected void fireValueChangedEvent()
/*     */   {
/* 207 */     ListExpressionHelper.fireValueChangedEvent(this.helper);
/*     */   }
/*     */ 
/*     */   protected void fireValueChangedEvent(ListChangeListener.Change<? extends E> paramChange)
/*     */   {
/* 221 */     ListExpressionHelper.fireValueChangedEvent(this.helper, paramChange);
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
/*     */   private void markInvalid(ObservableList<E> paramObservableList)
/*     */   {
/* 234 */     if (this.valid) {
/* 235 */       if (paramObservableList != null) {
/* 236 */         paramObservableList.removeListener(this.listChangeListener);
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
/*     */   public ObservableList<E> get()
/*     */   {
/* 259 */     if (!this.valid) {
/* 260 */       this.value = (this.observable == null ? this.value : (ObservableList)this.observable.getValue());
/* 261 */       this.valid = true;
/* 262 */       if (this.value != null) {
/* 263 */         this.value.addListener(this.listChangeListener);
/*     */       }
/*     */     }
/* 266 */     return this.value;
/*     */   }
/*     */ 
/*     */   public void set(ObservableList<E> paramObservableList)
/*     */   {
/* 271 */     if (isBound()) {
/* 272 */       throw new RuntimeException("A bound value cannot be set.");
/*     */     }
/* 274 */     if (this.value != paramObservableList) {
/* 275 */       ObservableList localObservableList = this.value;
/* 276 */       this.value = paramObservableList;
/* 277 */       markInvalid(localObservableList);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isBound()
/*     */   {
/* 283 */     return this.observable != null;
/*     */   }
/*     */ 
/*     */   public void bind(ObservableValue<? extends ObservableList<E>> paramObservableValue)
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
/* 305 */       this.value = ((ObservableList)this.observable.getValue());
/* 306 */       this.observable.removeListener(this.listener);
/* 307 */       this.observable = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 317 */     Object localObject = getBean();
/* 318 */     String str = getName();
/* 319 */     StringBuilder localStringBuilder = new StringBuilder("ListProperty [");
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
/* 343 */       ListPropertyBase.this.markInvalid(ListPropertyBase.this.value);
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
/* 148 */       return ListPropertyBase.this.isEmpty();
/*     */     }
/*     */ 
/*     */     public Object getBean()
/*     */     {
/* 153 */       return ListPropertyBase.this;
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
/* 118 */       return ListPropertyBase.this.size();
/*     */     }
/*     */ 
/*     */     public Object getBean()
/*     */     {
/* 123 */       return ListPropertyBase.this;
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
 * Qualified Name:     javafx.beans.property.ListPropertyBase
 * JD-Core Version:    0.6.2
 */