/*     */ package javafx.beans.property;
/*     */ 
/*     */ import com.sun.javafx.binding.ExpressionHelper;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ 
/*     */ public class ReadOnlyStringWrapper extends SimpleStringProperty
/*     */ {
/*     */   private ReadOnlyPropertyImpl readOnlyProperty;
/*     */ 
/*     */   public ReadOnlyStringWrapper()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ReadOnlyStringWrapper(String paramString)
/*     */   {
/*  77 */     super(paramString);
/*     */   }
/*     */ 
/*     */   public ReadOnlyStringWrapper(Object paramObject, String paramString)
/*     */   {
/*  89 */     super(paramObject, paramString);
/*     */   }
/*     */ 
/*     */   public ReadOnlyStringWrapper(Object paramObject, String paramString1, String paramString2)
/*     */   {
/* 104 */     super(paramObject, paramString1, paramString2);
/*     */   }
/*     */ 
/*     */   public ReadOnlyStringProperty getReadOnlyProperty()
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
/* 126 */     get();
/*     */   }
/*     */ 
/*     */   public void removeListener(InvalidationListener paramInvalidationListener)
/*     */   {
/* 134 */     if (this.readOnlyProperty != null)
/* 135 */       this.readOnlyProperty.removeListener(paramInvalidationListener);
/*     */   }
/*     */ 
/*     */   public void addListener(ChangeListener<? super String> paramChangeListener)
/*     */   {
/* 144 */     getReadOnlyProperty().addListener(paramChangeListener);
/*     */   }
/*     */ 
/*     */   public void removeListener(ChangeListener<? super String> paramChangeListener)
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
/*     */   private class ReadOnlyPropertyImpl extends ReadOnlyStringProperty
/*     */   {
/* 169 */     private ExpressionHelper<String> helper = null;
/*     */ 
/*     */     private ReadOnlyPropertyImpl() {
/*     */     }
/* 173 */     public String get() { return ReadOnlyStringWrapper.this.get(); }
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
/*     */     public void addListener(ChangeListener<? super String> paramChangeListener)
/*     */     {
/* 188 */       this.helper = ExpressionHelper.addListener(this.helper, this, paramChangeListener);
/*     */     }
/*     */ 
/*     */     public void removeListener(ChangeListener<? super String> paramChangeListener)
/*     */     {
/* 193 */       this.helper = ExpressionHelper.removeListener(this.helper, paramChangeListener);
/*     */     }
/*     */ 
/*     */     protected void fireValueChangedEvent() {
/* 197 */       ExpressionHelper.fireValueChangedEvent(this.helper);
/*     */     }
/*     */ 
/*     */     public Object getBean()
/*     */     {
/* 202 */       return ReadOnlyStringWrapper.this.getBean();
/*     */     }
/*     */ 
/*     */     public String getName()
/*     */     {
/* 207 */       return ReadOnlyStringWrapper.this.getName();
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.property.ReadOnlyStringWrapper
 * JD-Core Version:    0.6.2
 */