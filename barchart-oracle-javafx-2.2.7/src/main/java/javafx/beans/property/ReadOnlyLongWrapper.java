/*     */ package javafx.beans.property;
/*     */ 
/*     */ import com.sun.javafx.binding.ExpressionHelper;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ 
/*     */ public class ReadOnlyLongWrapper extends SimpleLongProperty
/*     */ {
/*     */   private ReadOnlyPropertyImpl readOnlyProperty;
/*     */ 
/*     */   public ReadOnlyLongWrapper()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ReadOnlyLongWrapper(long paramLong)
/*     */   {
/*  77 */     super(paramLong);
/*     */   }
/*     */ 
/*     */   public ReadOnlyLongWrapper(Object paramObject, String paramString)
/*     */   {
/*  89 */     super(paramObject, paramString);
/*     */   }
/*     */ 
/*     */   public ReadOnlyLongWrapper(Object paramObject, String paramString, long paramLong)
/*     */   {
/* 103 */     super(paramObject, paramString, paramLong);
/*     */   }
/*     */ 
/*     */   public ReadOnlyLongProperty getReadOnlyProperty()
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
/*     */   public void addListener(ChangeListener<? super Number> paramChangeListener)
/*     */   {
/* 142 */     getReadOnlyProperty().addListener(paramChangeListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(ChangeListener<? super Number> paramChangeListener)
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
/*     */   private class ReadOnlyPropertyImpl extends ReadOnlyLongProperty
/*     */   {
/* 167 */     private ExpressionHelper<Number> helper = null;
/*     */ 
/*     */     private ReadOnlyPropertyImpl() {
/*     */     }
/* 171 */     public long get() { return ReadOnlyLongWrapper.this.get(); }
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
/*     */     public void addListener(ChangeListener<? super Number> paramChangeListener)
/*     */     {
/* 186 */       this.helper = ExpressionHelper.addListener(this.helper, this, paramChangeListener);
/*     */     }
/*     */ 
/*     */     public void removeListener(ChangeListener<? super Number> paramChangeListener)
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
/* 200 */       return ReadOnlyLongWrapper.this.getBean();
/*     */     }
/*     */ 
/*     */     public String getName()
/*     */     {
/* 205 */       return ReadOnlyLongWrapper.this.getName();
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.property.ReadOnlyLongWrapper
 * JD-Core Version:    0.6.2
 */