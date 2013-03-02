/*     */ package javafx.beans.binding;
/*     */ 
/*     */ import com.sun.javafx.binding.StringFormatter;
/*     */ import javafx.beans.Observable;
/*     */ import javafx.beans.value.ObservableBooleanValue;
/*     */ import javafx.collections.FXCollections;
/*     */ import javafx.collections.ObservableList;
/*     */ 
/*     */ public abstract class BooleanExpression
/*     */   implements ObservableBooleanValue
/*     */ {
/*     */   public Boolean getValue()
/*     */   {
/*  48 */     return Boolean.valueOf(get());
/*     */   }
/*     */ 
/*     */   public static BooleanExpression booleanExpression(ObservableBooleanValue paramObservableBooleanValue)
/*     */   {
/*  68 */     if (paramObservableBooleanValue == null) {
/*  69 */       throw new NullPointerException("Value must be specified.");
/*     */     }
/*  71 */     return (paramObservableBooleanValue instanceof BooleanExpression) ? (BooleanExpression)paramObservableBooleanValue : new BooleanBinding()
/*     */     {
/*     */       public void dispose()
/*     */       {
/*  79 */         super.unbind(new Observable[] { this.val$value });
/*     */       }
/*     */ 
/*     */       protected boolean computeValue()
/*     */       {
/*  84 */         return this.val$value.get();
/*     */       }
/*     */ 
/*     */       public ObservableList<ObservableBooleanValue> getDependencies()
/*     */       {
/*  90 */         return FXCollections.singletonObservableList(this.val$value);
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public BooleanBinding and(ObservableBooleanValue paramObservableBooleanValue)
/*     */   {
/* 107 */     return Bindings.and(this, paramObservableBooleanValue);
/*     */   }
/*     */ 
/*     */   public BooleanBinding or(ObservableBooleanValue paramObservableBooleanValue)
/*     */   {
/* 122 */     return Bindings.or(this, paramObservableBooleanValue);
/*     */   }
/*     */ 
/*     */   public BooleanBinding not()
/*     */   {
/* 132 */     return Bindings.not(this);
/*     */   }
/*     */ 
/*     */   public BooleanBinding isEqualTo(ObservableBooleanValue paramObservableBooleanValue)
/*     */   {
/* 146 */     return Bindings.equal(this, paramObservableBooleanValue);
/*     */   }
/*     */ 
/*     */   public BooleanBinding isNotEqualTo(ObservableBooleanValue paramObservableBooleanValue)
/*     */   {
/* 160 */     return Bindings.notEqual(this, paramObservableBooleanValue);
/*     */   }
/*     */ 
/*     */   public StringBinding asString()
/*     */   {
/* 172 */     return (StringBinding)StringFormatter.convert(this);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.binding.BooleanExpression
 * JD-Core Version:    0.6.2
 */