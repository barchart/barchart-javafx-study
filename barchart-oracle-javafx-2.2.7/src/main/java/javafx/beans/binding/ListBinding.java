/*     */ package javafx.beans.binding;
/*     */ 
/*     */ import com.sun.javafx.binding.BindingHelperObserver;
/*     */ import com.sun.javafx.binding.ListExpressionHelper;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.Observable;
/*     */ import javafx.beans.property.ReadOnlyBooleanProperty;
/*     */ import javafx.beans.property.ReadOnlyBooleanPropertyBase;
/*     */ import javafx.beans.property.ReadOnlyIntegerProperty;
/*     */ import javafx.beans.property.ReadOnlyIntegerPropertyBase;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ import javafx.collections.FXCollections;
/*     */ import javafx.collections.ListChangeListener;
/*     */ import javafx.collections.ListChangeListener.Change;
/*     */ import javafx.collections.ObservableList;
/*     */ 
/*     */ public abstract class ListBinding<E> extends ListExpression<E>
/*     */   implements Binding<ObservableList<E>>
/*     */ {
/*     */   private final ListChangeListener<E> listChangeListener;
/*     */   private ObservableList<E> value;
/*     */   private boolean valid;
/*     */   private BindingHelperObserver observer;
/*     */   private ListExpressionHelper<E> helper;
/*     */   private ListBinding<E>.SizeProperty size0;
/*     */   private ListBinding<E>.EmptyProperty empty0;
/*     */ 
/*     */   public ListBinding()
/*     */   {
/*  87 */     this.listChangeListener = new ListChangeListener()
/*     */     {
/*     */       public void onChanged(ListChangeListener.Change<? extends E> paramAnonymousChange) {
/*  90 */         ListBinding.this.invalidateProperties();
/*  91 */         ListBinding.this.onInvalidating();
/*  92 */         ListExpressionHelper.fireValueChangedEvent(ListBinding.this.helper, paramAnonymousChange);
/*     */       }
/*     */     };
/*  97 */     this.valid = false;
/*     */ 
/*  99 */     this.helper = null;
/*     */   }
/*     */ 
/*     */   public ReadOnlyIntegerProperty sizeProperty()
/*     */   {
/* 106 */     if (this.size0 == null) {
/* 107 */       this.size0 = new SizeProperty(null);
/*     */     }
/* 109 */     return this.size0;
/*     */   }
/*     */ 
/*     */   public ReadOnlyBooleanProperty emptyProperty()
/*     */   {
/* 135 */     if (this.empty0 == null) {
/* 136 */       this.empty0 = new EmptyProperty(null);
/*     */     }
/* 138 */     return this.empty0;
/*     */   }
/*     */ 
/*     */   public void addListener(InvalidationListener paramInvalidationListener)
/*     */   {
/* 165 */     this.helper = ListExpressionHelper.addListener(this.helper, this, paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(InvalidationListener paramInvalidationListener)
/*     */   {
/* 170 */     this.helper = ListExpressionHelper.removeListener(this.helper, paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public void addListener(ChangeListener<? super ObservableList<E>> paramChangeListener)
/*     */   {
/* 175 */     this.helper = ListExpressionHelper.addListener(this.helper, this, paramChangeListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(ChangeListener<? super ObservableList<E>> paramChangeListener)
/*     */   {
/* 180 */     this.helper = ListExpressionHelper.removeListener(this.helper, paramChangeListener);
/*     */   }
/*     */ 
/*     */   public void addListener(ListChangeListener<? super E> paramListChangeListener)
/*     */   {
/* 185 */     this.helper = ListExpressionHelper.addListener(this.helper, this, paramListChangeListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(ListChangeListener<? super E> paramListChangeListener)
/*     */   {
/* 190 */     this.helper = ListExpressionHelper.removeListener(this.helper, paramListChangeListener);
/*     */   }
/*     */ 
/*     */   protected final void bind(Observable[] paramArrayOfObservable)
/*     */   {
/* 201 */     if ((paramArrayOfObservable != null) && (paramArrayOfObservable.length > 0)) {
/* 202 */       if (this.observer == null) {
/* 203 */         this.observer = new BindingHelperObserver(this);
/*     */       }
/* 205 */       for (Observable localObservable : paramArrayOfObservable)
/* 206 */         if (localObservable != null)
/* 207 */           localObservable.addListener(this.observer);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected final void unbind(Observable[] paramArrayOfObservable)
/*     */   {
/* 220 */     if (this.observer != null) {
/* 221 */       for (Observable localObservable : paramArrayOfObservable) {
/* 222 */         if (localObservable != null) {
/* 223 */           localObservable.removeListener(this.observer);
/*     */         }
/*     */       }
/* 226 */       this.observer = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void dispose()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ObservableList<?> getDependencies()
/*     */   {
/* 246 */     return FXCollections.emptyObservableList();
/*     */   }
/*     */ 
/*     */   public final ObservableList<E> get()
/*     */   {
/* 259 */     if (!this.valid) {
/* 260 */       this.value = computeValue();
/* 261 */       this.valid = true;
/* 262 */       if (this.value != null) {
/* 263 */         this.value.addListener(this.listChangeListener);
/*     */       }
/*     */     }
/* 266 */     return this.value;
/*     */   }
/*     */ 
/*     */   protected void onInvalidating()
/*     */   {
/*     */   }
/*     */ 
/*     */   private void invalidateProperties()
/*     */   {
/* 278 */     if (this.size0 != null) {
/* 279 */       this.size0.fireValueChangedEvent();
/*     */     }
/* 281 */     if (this.empty0 != null)
/* 282 */       this.empty0.fireValueChangedEvent();
/*     */   }
/*     */ 
/*     */   public final void invalidate()
/*     */   {
/* 288 */     if (this.valid) {
/* 289 */       if (this.value != null) {
/* 290 */         this.value.removeListener(this.listChangeListener);
/*     */       }
/* 292 */       this.valid = false;
/* 293 */       invalidateProperties();
/* 294 */       onInvalidating();
/* 295 */       ListExpressionHelper.fireValueChangedEvent(this.helper);
/*     */     }
/*     */   }
/*     */ 
/*     */   public final boolean isValid()
/*     */   {
/* 301 */     return this.valid;
/*     */   }
/*     */ 
/*     */   protected abstract ObservableList<E> computeValue();
/*     */ 
/*     */   public String toString()
/*     */   {
/* 320 */     return this.valid ? "ListBinding [value: " + get() + "]" : "ListBinding [invalid]";
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
/* 145 */       return ListBinding.this.isEmpty();
/*     */     }
/*     */ 
/*     */     public Object getBean()
/*     */     {
/* 150 */       return ListBinding.this;
/*     */     }
/*     */ 
/*     */     public String getName()
/*     */     {
/* 155 */       return "empty";
/*     */     }
/*     */ 
/*     */     protected void fireValueChangedEvent() {
/* 159 */       super.fireValueChangedEvent();
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
/* 115 */       return ListBinding.this.size();
/*     */     }
/*     */ 
/*     */     public Object getBean()
/*     */     {
/* 120 */       return ListBinding.this;
/*     */     }
/*     */ 
/*     */     public String getName()
/*     */     {
/* 125 */       return "size";
/*     */     }
/*     */ 
/*     */     protected void fireValueChangedEvent() {
/* 129 */       super.fireValueChangedEvent();
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.binding.ListBinding
 * JD-Core Version:    0.6.2
 */