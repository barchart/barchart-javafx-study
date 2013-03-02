/*     */ package javafx.beans.binding;
/*     */ 
/*     */ import com.sun.javafx.binding.StringFormatter;
/*     */ import javafx.beans.value.ObservableStringValue;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ 
/*     */ public abstract class StringExpression
/*     */   implements ObservableStringValue
/*     */ {
/*     */   public String getValue()
/*     */   {
/*  50 */     return (String)get();
/*     */   }
/*     */ 
/*     */   public final String getValueSafe()
/*     */   {
/*  61 */     String str = (String)get();
/*  62 */     return str == null ? "" : str;
/*     */   }
/*     */ 
/*     */   public static StringExpression stringExpression(ObservableValue<?> paramObservableValue)
/*     */   {
/*  82 */     if (paramObservableValue == null) {
/*  83 */       throw new NullPointerException("Value must be specified.");
/*     */     }
/*  85 */     return StringFormatter.convert(paramObservableValue);
/*     */   }
/*     */ 
/*     */   public StringExpression concat(Object paramObject)
/*     */   {
/* 104 */     return Bindings.concat(new Object[] { this, paramObject });
/*     */   }
/*     */ 
/*     */   public BooleanBinding isEqualTo(ObservableStringValue paramObservableStringValue)
/*     */   {
/* 120 */     return Bindings.equal(this, paramObservableStringValue);
/*     */   }
/*     */ 
/*     */   public BooleanBinding isEqualTo(String paramString)
/*     */   {
/* 135 */     return Bindings.equal(this, paramString);
/*     */   }
/*     */ 
/*     */   public BooleanBinding isNotEqualTo(ObservableStringValue paramObservableStringValue)
/*     */   {
/* 151 */     return Bindings.notEqual(this, paramObservableStringValue);
/*     */   }
/*     */ 
/*     */   public BooleanBinding isNotEqualTo(String paramString)
/*     */   {
/* 166 */     return Bindings.notEqual(this, paramString);
/*     */   }
/*     */ 
/*     */   public BooleanBinding isEqualToIgnoreCase(ObservableStringValue paramObservableStringValue)
/*     */   {
/* 182 */     return Bindings.equalIgnoreCase(this, paramObservableStringValue);
/*     */   }
/*     */ 
/*     */   public BooleanBinding isEqualToIgnoreCase(String paramString)
/*     */   {
/* 198 */     return Bindings.equalIgnoreCase(this, paramString);
/*     */   }
/*     */ 
/*     */   public BooleanBinding isNotEqualToIgnoreCase(ObservableStringValue paramObservableStringValue)
/*     */   {
/* 215 */     return Bindings.notEqualIgnoreCase(this, paramObservableStringValue);
/*     */   }
/*     */ 
/*     */   public BooleanBinding isNotEqualToIgnoreCase(String paramString)
/*     */   {
/* 231 */     return Bindings.notEqualIgnoreCase(this, paramString);
/*     */   }
/*     */ 
/*     */   public BooleanBinding greaterThan(ObservableStringValue paramObservableStringValue)
/*     */   {
/* 247 */     return Bindings.greaterThan(this, paramObservableStringValue);
/*     */   }
/*     */ 
/*     */   public BooleanBinding greaterThan(String paramString)
/*     */   {
/* 262 */     return Bindings.greaterThan(this, paramString);
/*     */   }
/*     */ 
/*     */   public BooleanBinding lessThan(ObservableStringValue paramObservableStringValue)
/*     */   {
/* 278 */     return Bindings.lessThan(this, paramObservableStringValue);
/*     */   }
/*     */ 
/*     */   public BooleanBinding lessThan(String paramString)
/*     */   {
/* 293 */     return Bindings.lessThan(this, paramString);
/*     */   }
/*     */ 
/*     */   public BooleanBinding greaterThanOrEqualTo(ObservableStringValue paramObservableStringValue)
/*     */   {
/* 309 */     return Bindings.greaterThanOrEqual(this, paramObservableStringValue);
/*     */   }
/*     */ 
/*     */   public BooleanBinding greaterThanOrEqualTo(String paramString)
/*     */   {
/* 325 */     return Bindings.greaterThanOrEqual(this, paramString);
/*     */   }
/*     */ 
/*     */   public BooleanBinding lessThanOrEqualTo(ObservableStringValue paramObservableStringValue)
/*     */   {
/* 341 */     return Bindings.lessThanOrEqual(this, paramObservableStringValue);
/*     */   }
/*     */ 
/*     */   public BooleanBinding lessThanOrEqualTo(String paramString)
/*     */   {
/* 357 */     return Bindings.lessThanOrEqual(this, paramString);
/*     */   }
/*     */ 
/*     */   public BooleanBinding isNull()
/*     */   {
/* 367 */     return Bindings.isNull(this);
/*     */   }
/*     */ 
/*     */   public BooleanBinding isNotNull()
/*     */   {
/* 377 */     return Bindings.isNotNull(this);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.binding.StringExpression
 * JD-Core Version:    0.6.2
 */