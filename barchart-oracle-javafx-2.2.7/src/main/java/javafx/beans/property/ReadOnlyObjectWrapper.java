/*     */ package javafx.beans.property;
/*     */ 
/*     */ import com.sun.javafx.binding.ExpressionHelper;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ 
/*     */ public class ReadOnlyObjectWrapper<T> extends SimpleObjectProperty<T>
/*     */ {
/*     */   private ReadOnlyObjectWrapper<T>.ReadOnlyPropertyImpl readOnlyProperty;
/*     */ 
/*     */   public ReadOnlyObjectWrapper()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ReadOnlyObjectWrapper(T paramT)
/*     */   {
/*  77 */     super(paramT);
/*     */   }
/*     */ 
/*     */   public ReadOnlyObjectWrapper(Object paramObject, String paramString)
/*     */   {
/*  89 */     super(paramObject, paramString);
/*     */   }
/*     */ 
/*     */   public ReadOnlyObjectWrapper(Object paramObject, String paramString, T paramT)
/*     */   {
/* 103 */     super(paramObject, paramString, paramT);
/*     */   }
/*     */ 
/*     */   public ReadOnlyObjectProperty<T> getReadOnlyProperty()
/*     */   {
/* 113 */     if (this.readOnlyProperty == null) {
/* 114 */       this.readOnlyProperty = new ReadOnlyPropertyImpl(null);
/*     */     }
/* 116 */     return this.readOnlyProperty;
/*     */   }
/*     */ 
/*     */   public void addListener(InvalidationListener paramInvalidationListener)
/*     */   {
/* 124 */     getReadOnlyProperty().addListener(paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(InvalidationListener paramInvalidationListener)
/*     */   {
/* 132 */     if (this.readOnlyProperty != null)
/* 133 */       this.readOnlyProperty.removeListener(paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public void addListener(ChangeListener<? super T> paramChangeListener)
/*     */   {
/* 142 */     getReadOnlyProperty().addListener(paramChangeListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(ChangeListener<? super T> paramChangeListener)
/*     */   {
/* 150 */     if (this.readOnlyProperty != null)
/* 151 */       this.readOnlyProperty.removeListener(paramChangeListener);
/*     */   }
/*     */ 
/*     */   protected void fireValueChangedEvent()
/*     */   {
/* 160 */     if (this.readOnlyProperty != null)
/* 161 */       this.readOnlyProperty.fireValueChangedEvent();
/*     */   }
/*     */ 
/*     */   private class ReadOnlyPropertyImpl extends ReadOnlyObjectProperty<T>
/*     */   {
/* 167 */     private ExpressionHelper<T> helper = null;
/*     */ 
/*     */     private ReadOnlyPropertyImpl() {
/*     */     }
/* 171 */     public T get() { return ReadOnlyObjectWrapper.this.get(); }
/*     */ 
/*     */ 
/*     */     public void addListener(InvalidationListener paramInvalidationListener)
/*     */     {
/* 176 */       this.helper = ExpressionHelper.addListener(this.helper, this, paramInvalidationListener);
/*     */     }
/*     */ 
/*     */     public void removeListener(InvalidationListener paramInvalidationListener)
/*     */     {
/* 181 */       this.helper = ExpressionHelper.removeListener(this.helper, paramInvalidationListener);
/*     */     }
/*     */ 
/*     */     public void addListener(ChangeListener<? super T> paramChangeListener)
/*     */     {
/* 186 */       this.helper = ExpressionHelper.addListener(this.helper, this, paramChangeListener);
/*     */     }
/*     */ 
/*     */     public void removeListener(ChangeListener<? super T> paramChangeListener)
/*     */     {
/* 191 */       this.helper = ExpressionHelper.removeListener(this.helper, paramChangeListener);
/*     */     }
/*     */ 
/*     */     protected void fireValueChangedEvent() {
/* 195 */       ExpressionHelper.fireValueChangedEvent(this.helper);
/*     */     }
/*     */ 
/*     */     public Object getBean()
/*     */     {
/* 200 */       return ReadOnlyObjectWrapper.this.getBean();
/*     */     }
/*     */ 
/*     */     public String getName()
/*     */     {
/* 205 */       return ReadOnlyObjectWrapper.this.getName();
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.property.ReadOnlyObjectWrapper
 * JD-Core Version:    0.6.2
 */