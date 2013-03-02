/*     */ package javafx.beans.binding;
/*     */ 
/*     */ import com.sun.javafx.binding.BindingHelperObserver;
/*     */ import com.sun.javafx.binding.SetExpressionHelper;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.Observable;
/*     */ import javafx.beans.property.ReadOnlyBooleanProperty;
/*     */ import javafx.beans.property.ReadOnlyBooleanPropertyBase;
/*     */ import javafx.beans.property.ReadOnlyIntegerProperty;
/*     */ import javafx.beans.property.ReadOnlyIntegerPropertyBase;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ import javafx.collections.FXCollections;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.collections.ObservableSet;
/*     */ import javafx.collections.SetChangeListener;
/*     */ import javafx.collections.SetChangeListener.Change;
/*     */ 
/*     */ public abstract class SetBinding<E> extends SetExpression<E>
/*     */   implements Binding<ObservableSet<E>>
/*     */ {
/*     */   private final SetChangeListener<E> setChangeListener;
/*     */   private ObservableSet<E> value;
/*     */   private boolean valid;
/*     */   private BindingHelperObserver observer;
/*     */   private SetExpressionHelper<E> helper;
/*     */   private SetBinding<E>.SizeProperty size0;
/*     */   private SetBinding<E>.EmptyProperty empty0;
/*     */ 
/*     */   public SetBinding()
/*     */   {
/*  88 */     this.setChangeListener = new SetChangeListener()
/*     */     {
/*     */       public void onChanged(SetChangeListener.Change<? extends E> paramAnonymousChange) {
/*  91 */         SetBinding.this.invalidateProperties();
/*  92 */         SetBinding.this.onInvalidating();
/*  93 */         SetExpressionHelper.fireValueChangedEvent(SetBinding.this.helper, paramAnonymousChange);
/*     */       }
/*     */     };
/*  98 */     this.valid = false;
/*     */ 
/* 100 */     this.helper = null;
/*     */   }
/*     */ 
/*     */   public ReadOnlyIntegerProperty sizeProperty()
/*     */   {
/* 107 */     if (this.size0 == null) {
/* 108 */       this.size0 = new SizeProperty(null);
/*     */     }
/* 110 */     return this.size0;
/*     */   }
/*     */ 
/*     */   public ReadOnlyBooleanProperty emptyProperty()
/*     */   {
/* 136 */     if (this.empty0 == null) {
/* 137 */       this.empty0 = new EmptyProperty(null);
/*     */     }
/* 139 */     return this.empty0;
/*     */   }
/*     */ 
/*     */   public void addListener(InvalidationListener paramInvalidationListener)
/*     */   {
/* 166 */     this.helper = SetExpressionHelper.addListener(this.helper, this, paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(InvalidationListener paramInvalidationListener)
/*     */   {
/* 171 */     this.helper = SetExpressionHelper.removeListener(this.helper, paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public void addListener(ChangeListener<? super ObservableSet<E>> paramChangeListener)
/*     */   {
/* 176 */     this.helper = SetExpressionHelper.addListener(this.helper, this, paramChangeListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(ChangeListener<? super ObservableSet<E>> paramChangeListener)
/*     */   {
/* 181 */     this.helper = SetExpressionHelper.removeListener(this.helper, paramChangeListener);
/*     */   }
/*     */ 
/*     */   public void addListener(SetChangeListener<? super E> paramSetChangeListener)
/*     */   {
/* 186 */     this.helper = SetExpressionHelper.addListener(this.helper, this, paramSetChangeListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(SetChangeListener<? super E> paramSetChangeListener)
/*     */   {
/* 191 */     this.helper = SetExpressionHelper.removeListener(this.helper, paramSetChangeListener);
/*     */   }
/*     */ 
/*     */   protected final void bind(Observable[] paramArrayOfObservable)
/*     */   {
/* 202 */     if ((paramArrayOfObservable != null) && (paramArrayOfObservable.length > 0)) {
/* 203 */       if (this.observer == null) {
/* 204 */         this.observer = new BindingHelperObserver(this);
/*     */       }
/* 206 */       for (Observable localObservable : paramArrayOfObservable)
/* 207 */         if (localObservable != null)
/* 208 */           localObservable.addListener(this.observer);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected final void unbind(Observable[] paramArrayOfObservable)
/*     */   {
/* 221 */     if (this.observer != null) {
/* 222 */       for (Observable localObservable : paramArrayOfObservable) {
/* 223 */         if (localObservable != null) {
/* 224 */           localObservable.removeListener(this.observer);
/*     */         }
/*     */       }
/* 227 */       this.observer = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void dispose()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ObservableList<?> getDependencies()
/*     */   {
/* 247 */     return FXCollections.emptyObservableList();
/*     */   }
/*     */ 
/*     */   public final ObservableSet<E> get()
/*     */   {
/* 260 */     if (!this.valid) {
/* 261 */       this.value = computeValue();
/* 262 */       this.valid = true;
/* 263 */       if (this.value != null) {
/* 264 */         this.value.addListener(this.setChangeListener);
/*     */       }
/*     */     }
/* 267 */     return this.value;
/*     */   }
/*     */ 
/*     */   protected void onInvalidating()
/*     */   {
/*     */   }
/*     */ 
/*     */   private void invalidateProperties()
/*     */   {
/* 279 */     if (this.size0 != null) {
/* 280 */       this.size0.fireValueChangedEvent();
/*     */     }
/* 282 */     if (this.empty0 != null)
/* 283 */       this.empty0.fireValueChangedEvent();
/*     */   }
/*     */ 
/*     */   public final void invalidate()
/*     */   {
/* 289 */     if (this.valid) {
/* 290 */       if (this.value != null) {
/* 291 */         this.value.removeListener(this.setChangeListener);
/*     */       }
/* 293 */       this.valid = false;
/* 294 */       invalidateProperties();
/* 295 */       onInvalidating();
/* 296 */       SetExpressionHelper.fireValueChangedEvent(this.helper);
/*     */     }
/*     */   }
/*     */ 
/*     */   public final boolean isValid()
/*     */   {
/* 302 */     return this.valid;
/*     */   }
/*     */ 
/*     */   protected abstract ObservableSet<E> computeValue();
/*     */ 
/*     */   public String toString()
/*     */   {
/* 321 */     return this.valid ? "SetBinding [value: " + get() + "]" : "SetBinding [invalid]";
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
/* 146 */       return SetBinding.this.isEmpty();
/*     */     }
/*     */ 
/*     */     public Object getBean()
/*     */     {
/* 151 */       return SetBinding.this;
/*     */     }
/*     */ 
/*     */     public String getName()
/*     */     {
/* 156 */       return "empty";
/*     */     }
/*     */ 
/*     */     protected void fireValueChangedEvent() {
/* 160 */       super.fireValueChangedEvent();
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
/* 116 */       return SetBinding.this.size();
/*     */     }
/*     */ 
/*     */     public Object getBean()
/*     */     {
/* 121 */       return SetBinding.this;
/*     */     }
/*     */ 
/*     */     public String getName()
/*     */     {
/* 126 */       return "size";
/*     */     }
/*     */ 
/*     */     protected void fireValueChangedEvent() {
/* 130 */       super.fireValueChangedEvent();
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.binding.SetBinding
 * JD-Core Version:    0.6.2
 */