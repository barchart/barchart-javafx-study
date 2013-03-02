/*     */ package javafx.beans.property;
/*     */ 
/*     */ import com.sun.javafx.binding.SetExpressionHelper;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ import javafx.collections.ObservableSet;
/*     */ import javafx.collections.SetChangeListener;
/*     */ import javafx.collections.SetChangeListener.Change;
/*     */ 
/*     */ public class ReadOnlySetWrapper<E> extends SimpleSetProperty<E>
/*     */ {
/*     */   private ReadOnlySetWrapper<E>.ReadOnlyPropertyImpl readOnlyProperty;
/*     */ 
/*     */   public ReadOnlySetWrapper()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ReadOnlySetWrapper(ObservableSet<E> paramObservableSet)
/*     */   {
/*  80 */     super(paramObservableSet);
/*     */   }
/*     */ 
/*     */   public ReadOnlySetWrapper(Object paramObject, String paramString)
/*     */   {
/*  92 */     super(paramObject, paramString);
/*     */   }
/*     */ 
/*     */   public ReadOnlySetWrapper(Object paramObject, String paramString, ObservableSet<E> paramObservableSet)
/*     */   {
/* 107 */     super(paramObject, paramString, paramObservableSet);
/*     */   }
/*     */ 
/*     */   public ReadOnlySetProperty<E> getReadOnlyProperty()
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
/*     */   public void addListener(ChangeListener<? super ObservableSet<E>> paramChangeListener)
/*     */   {
/* 146 */     getReadOnlyProperty().addListener(paramChangeListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(ChangeListener<? super ObservableSet<E>> paramChangeListener)
/*     */   {
/* 154 */     if (this.readOnlyProperty != null)
/* 155 */       this.readOnlyProperty.removeListener(paramChangeListener);
/*     */   }
/*     */ 
/*     */   public void addListener(SetChangeListener<? super E> paramSetChangeListener)
/*     */   {
/* 164 */     getReadOnlyProperty().addListener(paramSetChangeListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(SetChangeListener<? super E> paramSetChangeListener)
/*     */   {
/* 172 */     if (this.readOnlyProperty != null)
/* 173 */       this.readOnlyProperty.removeListener(paramSetChangeListener);
/*     */   }
/*     */ 
/*     */   protected void fireValueChangedEvent()
/*     */   {
/* 182 */     if (this.readOnlyProperty != null)
/* 183 */       this.readOnlyProperty.fireValueChangedEvent();
/*     */   }
/*     */ 
/*     */   protected void fireValueChangedEvent(SetChangeListener.Change<? extends E> paramChange)
/*     */   {
/* 192 */     if (this.readOnlyProperty != null)
/* 193 */       this.readOnlyProperty.fireValueChangedEvent(paramChange);
/*     */   }
/*     */ 
/*     */   private class ReadOnlyPropertyImpl extends ReadOnlySetProperty<E>
/*     */   {
/* 199 */     private SetExpressionHelper<E> helper = null;
/*     */ 
/*     */     private ReadOnlyPropertyImpl() {
/*     */     }
/* 203 */     public ObservableSet<E> get() { return ReadOnlySetWrapper.this.get(); }
/*     */ 
/*     */ 
/*     */     public void addListener(InvalidationListener paramInvalidationListener)
/*     */     {
/* 208 */       this.helper = SetExpressionHelper.addListener(this.helper, this, paramInvalidationListener);
/*     */     }
/*     */ 
/*     */     public void removeListener(InvalidationListener paramInvalidationListener)
/*     */     {
/* 213 */       this.helper = SetExpressionHelper.removeListener(this.helper, paramInvalidationListener);
/*     */     }
/*     */ 
/*     */     public void addListener(ChangeListener<? super ObservableSet<E>> paramChangeListener)
/*     */     {
/* 218 */       this.helper = SetExpressionHelper.addListener(this.helper, this, paramChangeListener);
/*     */     }
/*     */ 
/*     */     public void removeListener(ChangeListener<? super ObservableSet<E>> paramChangeListener)
/*     */     {
/* 223 */       this.helper = SetExpressionHelper.removeListener(this.helper, paramChangeListener);
/*     */     }
/*     */ 
/*     */     public void addListener(SetChangeListener<? super E> paramSetChangeListener)
/*     */     {
/* 228 */       this.helper = SetExpressionHelper.addListener(this.helper, this, paramSetChangeListener);
/*     */     }
/*     */ 
/*     */     public void removeListener(SetChangeListener<? super E> paramSetChangeListener)
/*     */     {
/* 233 */       this.helper = SetExpressionHelper.removeListener(this.helper, paramSetChangeListener);
/*     */     }
/*     */ 
/*     */     private void fireValueChangedEvent() {
/* 237 */       SetExpressionHelper.fireValueChangedEvent(this.helper);
/*     */     }
/*     */ 
/*     */     private void fireValueChangedEvent(SetChangeListener.Change<? extends E> paramChange) {
/* 241 */       SetExpressionHelper.fireValueChangedEvent(this.helper, paramChange);
/*     */     }
/*     */ 
/*     */     public Object getBean()
/*     */     {
/* 246 */       return ReadOnlySetWrapper.this.getBean();
/*     */     }
/*     */ 
/*     */     public String getName()
/*     */     {
/* 251 */       return ReadOnlySetWrapper.this.getName();
/*     */     }
/*     */ 
/*     */     public ReadOnlyIntegerProperty sizeProperty()
/*     */     {
/* 256 */       return ReadOnlySetWrapper.this.sizeProperty();
/*     */     }
/*     */ 
/*     */     public ReadOnlyBooleanProperty emptyProperty()
/*     */     {
/* 261 */       return ReadOnlySetWrapper.this.emptyProperty();
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.property.ReadOnlySetWrapper
 * JD-Core Version:    0.6.2
 */