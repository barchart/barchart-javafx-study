/*     */ package javafx.beans.binding;
/*     */ 
/*     */ import javafx.beans.Observable;
/*     */ import javafx.beans.value.ObservableObjectValue;
/*     */ import javafx.collections.FXCollections;
/*     */ import javafx.collections.ObservableList;
/*     */ 
/*     */ public abstract class ObjectExpression<T>
/*     */   implements ObservableObjectValue<T>
/*     */ {
/*     */   public T getValue()
/*     */   {
/*  47 */     return get();
/*     */   }
/*     */ 
/*     */   public static <T> ObjectExpression<T> objectExpression(ObservableObjectValue<T> paramObservableObjectValue)
/*     */   {
/*  67 */     if (paramObservableObjectValue == null) {
/*  68 */       throw new NullPointerException("Value must be specified.");
/*     */     }
/*  70 */     return (paramObservableObjectValue instanceof ObjectExpression) ? (ObjectExpression)paramObservableObjectValue : new ObjectBinding()
/*     */     {
/*     */       public void dispose()
/*     */       {
/*  78 */         super.unbind(new Observable[] { this.val$value });
/*     */       }
/*     */ 
/*     */       protected T computeValue()
/*     */       {
/*  83 */         return this.val$value.get();
/*     */       }
/*     */ 
/*     */       public ObservableList<ObservableObjectValue<T>> getDependencies()
/*     */       {
/*  89 */         return FXCollections.singletonObservableList(this.val$value);
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public BooleanBinding isEqualTo(ObservableObjectValue<?> paramObservableObjectValue)
/*     */   {
/* 105 */     return Bindings.equal(this, paramObservableObjectValue);
/*     */   }
/*     */ 
/*     */   public BooleanBinding isEqualTo(Object paramObject)
/*     */   {
/* 117 */     return Bindings.equal(this, paramObject);
/*     */   }
/*     */ 
/*     */   public BooleanBinding isNotEqualTo(ObservableObjectValue<?> paramObservableObjectValue)
/*     */   {
/* 131 */     return Bindings.notEqual(this, paramObservableObjectValue);
/*     */   }
/*     */ 
/*     */   public BooleanBinding isNotEqualTo(Object paramObject)
/*     */   {
/* 143 */     return Bindings.notEqual(this, paramObject);
/*     */   }
/*     */ 
/*     */   public BooleanBinding isNull()
/*     */   {
/* 153 */     return Bindings.isNull(this);
/*     */   }
/*     */ 
/*     */   public BooleanBinding isNotNull()
/*     */   {
/* 163 */     return Bindings.isNotNull(this);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.binding.ObjectExpression
 * JD-Core Version:    0.6.2
 */