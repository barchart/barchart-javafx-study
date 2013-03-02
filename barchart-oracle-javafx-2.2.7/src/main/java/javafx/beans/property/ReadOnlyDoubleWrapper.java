/*     */ package javafx.beans.property;
/*     */ 
/*     */ import com.sun.javafx.binding.ExpressionHelper;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ 
/*     */ public class ReadOnlyDoubleWrapper extends SimpleDoubleProperty
/*     */ {
/*     */   private ReadOnlyPropertyImpl readOnlyProperty;
/*     */ 
/*     */   public ReadOnlyDoubleWrapper()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ReadOnlyDoubleWrapper(double paramDouble)
/*     */   {
/*  77 */     super(paramDouble);
/*     */   }
/*     */ 
/*     */   public ReadOnlyDoubleWrapper(Object paramObject, String paramString)
/*     */   {
/*  89 */     super(paramObject, paramString);
/*     */   }
/*     */ 
/*     */   public ReadOnlyDoubleWrapper(Object paramObject, String paramString, double paramDouble)
/*     */   {
/* 104 */     super(paramObject, paramString, paramDouble);
/*     */   }
/*     */ 
/*     */   public ReadOnlyDoubleProperty getReadOnlyProperty()
/*     */   {
/* 114 */     if (this.readOnlyProperty == null) {
/* 115 */       this.readOnlyProperty = new ReadOnlyPropertyImpl(null);
/*     */     }
/* 117 */     return this.readOnlyProperty;
/*     */   }
/*     */ 
/*     */   public void addListener(InvalidationListener paramInvalidationListener)
/*     */   {
/* 125 */     getReadOnlyProperty().addListener(paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(InvalidationListener paramInvalidationListener)
/*     */   {
/* 133 */     if (this.readOnlyProperty != null)
/* 134 */       this.readOnlyProperty.removeListener(paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public void addListener(ChangeListener<? super Number> paramChangeListener)
/*     */   {
/* 143 */     getReadOnlyProperty().addListener(paramChangeListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(ChangeListener<? super Number> paramChangeListener)
/*     */   {
/* 151 */     if (this.readOnlyProperty != null)
/* 152 */       this.readOnlyProperty.removeListener(paramChangeListener);
/*     */   }
/*     */ 
/*     */   protected void fireValueChangedEvent()
/*     */   {
/* 161 */     if (this.readOnlyProperty != null)
/* 162 */       this.readOnlyProperty.fireValueChangedEvent();
/*     */   }
/*     */ 
/*     */   private class ReadOnlyPropertyImpl extends ReadOnlyDoubleProperty
/*     */   {
/* 168 */     private ExpressionHelper<Number> helper = null;
/*     */ 
/*     */     private ReadOnlyPropertyImpl() {
/*     */     }
/* 172 */     public double get() { return ReadOnlyDoubleWrapper.this.get(); }
/*     */ 
/*     */ 
/*     */     public void addListener(InvalidationListener paramInvalidationListener)
/*     */     {
/* 177 */       this.helper = ExpressionHelper.addListener(this.helper, this, paramInvalidationListener);
/*     */     }
/*     */ 
/*     */     public void removeListener(InvalidationListener paramInvalidationListener)
/*     */     {
/* 182 */       this.helper = ExpressionHelper.removeListener(this.helper, paramInvalidationListener);
/*     */     }
/*     */ 
/*     */     public void addListener(ChangeListener<? super Number> paramChangeListener)
/*     */     {
/* 187 */       this.helper = ExpressionHelper.addListener(this.helper, this, paramChangeListener);
/*     */     }
/*     */ 
/*     */     public void removeListener(ChangeListener<? super Number> paramChangeListener)
/*     */     {
/* 192 */       this.helper = ExpressionHelper.removeListener(this.helper, paramChangeListener);
/*     */     }
/*     */ 
/*     */     protected void fireValueChangedEvent() {
/* 196 */       ExpressionHelper.fireValueChangedEvent(this.helper);
/*     */     }
/*     */ 
/*     */     public Object getBean()
/*     */     {
/* 201 */       return ReadOnlyDoubleWrapper.this.getBean();
/*     */     }
/*     */ 
/*     */     public String getName()
/*     */     {
/* 206 */       return ReadOnlyDoubleWrapper.this.getName();
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.property.ReadOnlyDoubleWrapper
 * JD-Core Version:    0.6.2
 */