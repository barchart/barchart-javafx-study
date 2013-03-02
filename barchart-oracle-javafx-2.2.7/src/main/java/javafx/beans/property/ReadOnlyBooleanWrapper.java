/*     */ package javafx.beans.property;
/*     */ 
/*     */ import com.sun.javafx.binding.ExpressionHelper;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ 
/*     */ public class ReadOnlyBooleanWrapper extends SimpleBooleanProperty
/*     */ {
/*     */   private ReadOnlyPropertyImpl readOnlyProperty;
/*     */ 
/*     */   public ReadOnlyBooleanWrapper()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ReadOnlyBooleanWrapper(boolean paramBoolean)
/*     */   {
/*  77 */     super(paramBoolean);
/*     */   }
/*     */ 
/*     */   public ReadOnlyBooleanWrapper(Object paramObject, String paramString)
/*     */   {
/*  89 */     super(paramObject, paramString);
/*     */   }
/*     */ 
/*     */   public ReadOnlyBooleanWrapper(Object paramObject, String paramString, boolean paramBoolean)
/*     */   {
/* 104 */     super(paramObject, paramString, paramBoolean);
/*     */   }
/*     */ 
/*     */   public ReadOnlyBooleanProperty getReadOnlyProperty()
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
/* 134 */     if (this.readOnlyProperty != null)
/* 135 */       this.readOnlyProperty.removeListener(paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public void addListener(ChangeListener<? super Boolean> paramChangeListener)
/*     */   {
/* 144 */     getReadOnlyProperty().addListener(paramChangeListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(ChangeListener<? super Boolean> paramChangeListener)
/*     */   {
/* 152 */     if (this.readOnlyProperty != null)
/* 153 */       this.readOnlyProperty.removeListener(paramChangeListener);
/*     */   }
/*     */ 
/*     */   protected void fireValueChangedEvent()
/*     */   {
/* 162 */     if (this.readOnlyProperty != null)
/* 163 */       this.readOnlyProperty.fireValueChangedEvent();
/*     */   }
/*     */ 
/*     */   private class ReadOnlyPropertyImpl extends ReadOnlyBooleanProperty
/*     */   {
/* 169 */     private ExpressionHelper<Boolean> helper = null;
/*     */ 
/*     */     private ReadOnlyPropertyImpl() {
/*     */     }
/* 173 */     public boolean get() { return ReadOnlyBooleanWrapper.this.get(); }
/*     */ 
/*     */ 
/*     */     public void addListener(InvalidationListener paramInvalidationListener)
/*     */     {
/* 178 */       this.helper = ExpressionHelper.addListener(this.helper, this, paramInvalidationListener);
/*     */     }
/*     */ 
/*     */     public void removeListener(InvalidationListener paramInvalidationListener)
/*     */     {
/* 183 */       this.helper = ExpressionHelper.removeListener(this.helper, paramInvalidationListener);
/*     */     }
/*     */ 
/*     */     public void addListener(ChangeListener<? super Boolean> paramChangeListener)
/*     */     {
/* 188 */       this.helper = ExpressionHelper.addListener(this.helper, this, paramChangeListener);
/*     */     }
/*     */ 
/*     */     public void removeListener(ChangeListener<? super Boolean> paramChangeListener)
/*     */     {
/* 193 */       this.helper = ExpressionHelper.removeListener(this.helper, paramChangeListener);
/*     */     }
/*     */ 
/*     */     private void fireValueChangedEvent() {
/* 197 */       ExpressionHelper.fireValueChangedEvent(this.helper);
/*     */     }
/*     */ 
/*     */     public Object getBean()
/*     */     {
/* 202 */       return ReadOnlyBooleanWrapper.this.getBean();
/*     */     }
/*     */ 
/*     */     public String getName()
/*     */     {
/* 207 */       return ReadOnlyBooleanWrapper.this.getName();
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.property.ReadOnlyBooleanWrapper
 * JD-Core Version:    0.6.2
 */