/*     */ package javafx.beans.property;
/*     */ 
/*     */ import com.sun.javafx.binding.ExpressionHelper;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ 
/*     */ public class ReadOnlyFloatWrapper extends SimpleFloatProperty
/*     */ {
/*     */   private ReadOnlyPropertyImpl readOnlyProperty;
/*     */ 
/*     */   public ReadOnlyFloatWrapper()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ReadOnlyFloatWrapper(float paramFloat)
/*     */   {
/*  77 */     super(paramFloat);
/*     */   }
/*     */ 
/*     */   public ReadOnlyFloatWrapper(Object paramObject, String paramString)
/*     */   {
/*  89 */     super(paramObject, paramString);
/*     */   }
/*     */ 
/*     */   public ReadOnlyFloatWrapper(Object paramObject, String paramString, float paramFloat)
/*     */   {
/* 103 */     super(paramObject, paramString, paramFloat);
/*     */   }
/*     */ 
/*     */   public ReadOnlyFloatProperty getReadOnlyProperty()
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
/*     */   private class ReadOnlyPropertyImpl extends ReadOnlyFloatProperty
/*     */   {
/* 167 */     private ExpressionHelper<Number> helper = null;
/*     */ 
/*     */     private ReadOnlyPropertyImpl() {
/*     */     }
/* 171 */     public float get() { return ReadOnlyFloatWrapper.this.get(); }
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
/* 200 */       return ReadOnlyFloatWrapper.this.getBean();
/*     */     }
/*     */ 
/*     */     public String getName()
/*     */     {
/* 205 */       return ReadOnlyFloatWrapper.this.getName();
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.property.ReadOnlyFloatWrapper
 * JD-Core Version:    0.6.2
 */