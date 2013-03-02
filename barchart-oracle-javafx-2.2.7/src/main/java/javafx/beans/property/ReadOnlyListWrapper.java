/*     */ package javafx.beans.property;
/*     */ 
/*     */ import com.sun.javafx.binding.ListExpressionHelper;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ import javafx.collections.ListChangeListener;
/*     */ import javafx.collections.ListChangeListener.Change;
/*     */ import javafx.collections.ObservableList;
/*     */ 
/*     */ public class ReadOnlyListWrapper<E> extends SimpleListProperty<E>
/*     */ {
/*     */   private ReadOnlyListWrapper<E>.ReadOnlyPropertyImpl readOnlyProperty;
/*     */ 
/*     */   public ReadOnlyListWrapper()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ReadOnlyListWrapper(ObservableList<E> paramObservableList)
/*     */   {
/*  80 */     super(paramObservableList);
/*     */   }
/*     */ 
/*     */   public ReadOnlyListWrapper(Object paramObject, String paramString)
/*     */   {
/*  92 */     super(paramObject, paramString);
/*     */   }
/*     */ 
/*     */   public ReadOnlyListWrapper(Object paramObject, String paramString, ObservableList<E> paramObservableList)
/*     */   {
/* 107 */     super(paramObject, paramString, paramObservableList);
/*     */   }
/*     */ 
/*     */   public ReadOnlyListProperty<E> getReadOnlyProperty()
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
/*     */   public void addListener(ChangeListener<? super ObservableList<E>> paramChangeListener)
/*     */   {
/* 146 */     getReadOnlyProperty().addListener(paramChangeListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(ChangeListener<? super ObservableList<E>> paramChangeListener)
/*     */   {
/* 154 */     if (this.readOnlyProperty != null)
/* 155 */       this.readOnlyProperty.removeListener(paramChangeListener);
/*     */   }
/*     */ 
/*     */   public void addListener(ListChangeListener<? super E> paramListChangeListener)
/*     */   {
/* 164 */     getReadOnlyProperty().addListener(paramListChangeListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(ListChangeListener<? super E> paramListChangeListener)
/*     */   {
/* 172 */     if (this.readOnlyProperty != null)
/* 173 */       this.readOnlyProperty.removeListener(paramListChangeListener);
/*     */   }
/*     */ 
/*     */   protected void fireValueChangedEvent()
/*     */   {
/* 182 */     if (this.readOnlyProperty != null)
/* 183 */       this.readOnlyProperty.fireValueChangedEvent();
/*     */   }
/*     */ 
/*     */   protected void fireValueChangedEvent(ListChangeListener.Change<? extends E> paramChange)
/*     */   {
/* 192 */     if (this.readOnlyProperty != null)
/* 193 */       this.readOnlyProperty.fireValueChangedEvent(paramChange);
/*     */   }
/*     */ 
/*     */   private class ReadOnlyPropertyImpl extends ReadOnlyListProperty<E>
/*     */   {
/* 199 */     private ListExpressionHelper<E> helper = null;
/*     */ 
/*     */     private ReadOnlyPropertyImpl() {
/*     */     }
/* 203 */     public ObservableList<E> get() { return ReadOnlyListWrapper.this.get(); }
/*     */ 
/*     */ 
/*     */     public void addListener(InvalidationListener paramInvalidationListener)
/*     */     {
/* 208 */       this.helper = ListExpressionHelper.addListener(this.helper, this, paramInvalidationListener);
/*     */     }
/*     */ 
/*     */     public void removeListener(InvalidationListener paramInvalidationListener)
/*     */     {
/* 213 */       this.helper = ListExpressionHelper.removeListener(this.helper, paramInvalidationListener);
/*     */     }
/*     */ 
/*     */     public void addListener(ChangeListener<? super ObservableList<E>> paramChangeListener)
/*     */     {
/* 218 */       this.helper = ListExpressionHelper.addListener(this.helper, this, paramChangeListener);
/*     */     }
/*     */ 
/*     */     public void removeListener(ChangeListener<? super ObservableList<E>> paramChangeListener)
/*     */     {
/* 223 */       this.helper = ListExpressionHelper.removeListener(this.helper, paramChangeListener);
/*     */     }
/*     */ 
/*     */     public void addListener(ListChangeListener<? super E> paramListChangeListener)
/*     */     {
/* 228 */       this.helper = ListExpressionHelper.addListener(this.helper, this, paramListChangeListener);
/*     */     }
/*     */ 
/*     */     public void removeListener(ListChangeListener<? super E> paramListChangeListener)
/*     */     {
/* 233 */       this.helper = ListExpressionHelper.removeListener(this.helper, paramListChangeListener);
/*     */     }
/*     */ 
/*     */     private void fireValueChangedEvent() {
/* 237 */       ListExpressionHelper.fireValueChangedEvent(this.helper);
/*     */     }
/*     */ 
/*     */     private void fireValueChangedEvent(ListChangeListener.Change<? extends E> paramChange) {
/* 241 */       ListExpressionHelper.fireValueChangedEvent(this.helper, paramChange);
/*     */     }
/*     */ 
/*     */     public Object getBean()
/*     */     {
/* 246 */       return ReadOnlyListWrapper.this.getBean();
/*     */     }
/*     */ 
/*     */     public String getName()
/*     */     {
/* 251 */       return ReadOnlyListWrapper.this.getName();
/*     */     }
/*     */ 
/*     */     public ReadOnlyIntegerProperty sizeProperty()
/*     */     {
/* 256 */       return ReadOnlyListWrapper.this.sizeProperty();
/*     */     }
/*     */ 
/*     */     public ReadOnlyBooleanProperty emptyProperty()
/*     */     {
/* 261 */       return ReadOnlyListWrapper.this.emptyProperty();
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.property.ReadOnlyListWrapper
 * JD-Core Version:    0.6.2
 */