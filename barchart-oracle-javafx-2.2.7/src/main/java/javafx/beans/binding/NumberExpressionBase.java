/*     */ package javafx.beans.binding;
/*     */ 
/*     */ import com.sun.javafx.binding.StringFormatter;
/*     */ import java.util.Locale;
/*     */ import javafx.beans.value.ObservableDoubleValue;
/*     */ import javafx.beans.value.ObservableFloatValue;
/*     */ import javafx.beans.value.ObservableIntegerValue;
/*     */ import javafx.beans.value.ObservableLongValue;
/*     */ import javafx.beans.value.ObservableNumberValue;
/*     */ 
/*     */ public abstract class NumberExpressionBase
/*     */   implements NumberExpression
/*     */ {
/*     */   public static <S extends Number> NumberExpressionBase numberExpression(ObservableNumberValue paramObservableNumberValue)
/*     */   {
/*  57 */     if (paramObservableNumberValue == null) {
/*  58 */       throw new NullPointerException("Value must be specified.");
/*     */     }
/*  60 */     NumberExpressionBase localNumberExpressionBase = (NumberExpressionBase)((paramObservableNumberValue instanceof ObservableLongValue) ? LongExpression.longExpression((ObservableLongValue)paramObservableNumberValue) : (paramObservableNumberValue instanceof ObservableFloatValue) ? FloatExpression.floatExpression((ObservableFloatValue)paramObservableNumberValue) : (paramObservableNumberValue instanceof ObservableDoubleValue) ? DoubleExpression.doubleExpression((ObservableDoubleValue)paramObservableNumberValue) : (paramObservableNumberValue instanceof ObservableIntegerValue) ? IntegerExpression.integerExpression((ObservableIntegerValue)paramObservableNumberValue) : (paramObservableNumberValue instanceof NumberExpressionBase) ? paramObservableNumberValue : null);
/*     */ 
/*  70 */     if (localNumberExpressionBase != null) {
/*  71 */       return localNumberExpressionBase;
/*     */     }
/*  73 */     throw new IllegalArgumentException("Unsupported Type");
/*     */   }
/*     */ 
/*     */   public NumberBinding add(ObservableNumberValue paramObservableNumberValue)
/*     */   {
/*  79 */     return Bindings.add(this, paramObservableNumberValue);
/*     */   }
/*     */ 
/*     */   public NumberBinding subtract(ObservableNumberValue paramObservableNumberValue)
/*     */   {
/*  84 */     return Bindings.subtract(this, paramObservableNumberValue);
/*     */   }
/*     */ 
/*     */   public NumberBinding multiply(ObservableNumberValue paramObservableNumberValue)
/*     */   {
/*  89 */     return Bindings.multiply(this, paramObservableNumberValue);
/*     */   }
/*     */ 
/*     */   public NumberBinding divide(ObservableNumberValue paramObservableNumberValue)
/*     */   {
/*  94 */     return Bindings.divide(this, paramObservableNumberValue);
/*     */   }
/*     */ 
/*     */   public BooleanBinding isEqualTo(ObservableNumberValue paramObservableNumberValue)
/*     */   {
/* 102 */     return Bindings.equal(this, paramObservableNumberValue);
/*     */   }
/*     */ 
/*     */   public BooleanBinding isEqualTo(ObservableNumberValue paramObservableNumberValue, double paramDouble)
/*     */   {
/* 108 */     return Bindings.equal(this, paramObservableNumberValue, paramDouble);
/*     */   }
/*     */ 
/*     */   public BooleanBinding isEqualTo(double paramDouble1, double paramDouble2)
/*     */   {
/* 113 */     return Bindings.equal(this, paramDouble1, paramDouble2);
/*     */   }
/*     */ 
/*     */   public BooleanBinding isEqualTo(float paramFloat, double paramDouble)
/*     */   {
/* 118 */     return Bindings.equal(this, paramFloat, paramDouble);
/*     */   }
/*     */ 
/*     */   public BooleanBinding isEqualTo(long paramLong)
/*     */   {
/* 123 */     return Bindings.equal(this, paramLong);
/*     */   }
/*     */ 
/*     */   public BooleanBinding isEqualTo(long paramLong, double paramDouble)
/*     */   {
/* 128 */     return Bindings.equal(this, paramLong, paramDouble);
/*     */   }
/*     */ 
/*     */   public BooleanBinding isEqualTo(int paramInt)
/*     */   {
/* 133 */     return Bindings.equal(this, paramInt);
/*     */   }
/*     */ 
/*     */   public BooleanBinding isEqualTo(int paramInt, double paramDouble)
/*     */   {
/* 138 */     return Bindings.equal(this, paramInt, paramDouble);
/*     */   }
/*     */ 
/*     */   public BooleanBinding isNotEqualTo(ObservableNumberValue paramObservableNumberValue)
/*     */   {
/* 146 */     return Bindings.notEqual(this, paramObservableNumberValue);
/*     */   }
/*     */ 
/*     */   public BooleanBinding isNotEqualTo(ObservableNumberValue paramObservableNumberValue, double paramDouble)
/*     */   {
/* 152 */     return Bindings.notEqual(this, paramObservableNumberValue, paramDouble);
/*     */   }
/*     */ 
/*     */   public BooleanBinding isNotEqualTo(double paramDouble1, double paramDouble2)
/*     */   {
/* 157 */     return Bindings.notEqual(this, paramDouble1, paramDouble2);
/*     */   }
/*     */ 
/*     */   public BooleanBinding isNotEqualTo(float paramFloat, double paramDouble)
/*     */   {
/* 162 */     return Bindings.notEqual(this, paramFloat, paramDouble);
/*     */   }
/*     */ 
/*     */   public BooleanBinding isNotEqualTo(long paramLong)
/*     */   {
/* 167 */     return Bindings.notEqual(this, paramLong);
/*     */   }
/*     */ 
/*     */   public BooleanBinding isNotEqualTo(long paramLong, double paramDouble)
/*     */   {
/* 172 */     return Bindings.notEqual(this, paramLong, paramDouble);
/*     */   }
/*     */ 
/*     */   public BooleanBinding isNotEqualTo(int paramInt)
/*     */   {
/* 177 */     return Bindings.notEqual(this, paramInt);
/*     */   }
/*     */ 
/*     */   public BooleanBinding isNotEqualTo(int paramInt, double paramDouble)
/*     */   {
/* 182 */     return Bindings.notEqual(this, paramInt, paramDouble);
/*     */   }
/*     */ 
/*     */   public BooleanBinding greaterThan(ObservableNumberValue paramObservableNumberValue)
/*     */   {
/* 190 */     return Bindings.greaterThan(this, paramObservableNumberValue);
/*     */   }
/*     */ 
/*     */   public BooleanBinding greaterThan(double paramDouble)
/*     */   {
/* 195 */     return Bindings.greaterThan(this, paramDouble);
/*     */   }
/*     */ 
/*     */   public BooleanBinding greaterThan(float paramFloat)
/*     */   {
/* 200 */     return Bindings.greaterThan(this, paramFloat);
/*     */   }
/*     */ 
/*     */   public BooleanBinding greaterThan(long paramLong)
/*     */   {
/* 205 */     return Bindings.greaterThan(this, paramLong);
/*     */   }
/*     */ 
/*     */   public BooleanBinding greaterThan(int paramInt)
/*     */   {
/* 210 */     return Bindings.greaterThan(this, paramInt);
/*     */   }
/*     */ 
/*     */   public BooleanBinding lessThan(ObservableNumberValue paramObservableNumberValue)
/*     */   {
/* 218 */     return Bindings.lessThan(this, paramObservableNumberValue);
/*     */   }
/*     */ 
/*     */   public BooleanBinding lessThan(double paramDouble)
/*     */   {
/* 223 */     return Bindings.lessThan(this, paramDouble);
/*     */   }
/*     */ 
/*     */   public BooleanBinding lessThan(float paramFloat)
/*     */   {
/* 228 */     return Bindings.lessThan(this, paramFloat);
/*     */   }
/*     */ 
/*     */   public BooleanBinding lessThan(long paramLong)
/*     */   {
/* 233 */     return Bindings.lessThan(this, paramLong);
/*     */   }
/*     */ 
/*     */   public BooleanBinding lessThan(int paramInt)
/*     */   {
/* 238 */     return Bindings.lessThan(this, paramInt);
/*     */   }
/*     */ 
/*     */   public BooleanBinding greaterThanOrEqualTo(ObservableNumberValue paramObservableNumberValue)
/*     */   {
/* 246 */     return Bindings.greaterThanOrEqual(this, paramObservableNumberValue);
/*     */   }
/*     */ 
/*     */   public BooleanBinding greaterThanOrEqualTo(double paramDouble)
/*     */   {
/* 251 */     return Bindings.greaterThanOrEqual(this, paramDouble);
/*     */   }
/*     */ 
/*     */   public BooleanBinding greaterThanOrEqualTo(float paramFloat)
/*     */   {
/* 256 */     return Bindings.greaterThanOrEqual(this, paramFloat);
/*     */   }
/*     */ 
/*     */   public BooleanBinding greaterThanOrEqualTo(long paramLong)
/*     */   {
/* 261 */     return Bindings.greaterThanOrEqual(this, paramLong);
/*     */   }
/*     */ 
/*     */   public BooleanBinding greaterThanOrEqualTo(int paramInt)
/*     */   {
/* 266 */     return Bindings.greaterThanOrEqual(this, paramInt);
/*     */   }
/*     */ 
/*     */   public BooleanBinding lessThanOrEqualTo(ObservableNumberValue paramObservableNumberValue)
/*     */   {
/* 274 */     return Bindings.lessThanOrEqual(this, paramObservableNumberValue);
/*     */   }
/*     */ 
/*     */   public BooleanBinding lessThanOrEqualTo(double paramDouble)
/*     */   {
/* 279 */     return Bindings.lessThanOrEqual(this, paramDouble);
/*     */   }
/*     */ 
/*     */   public BooleanBinding lessThanOrEqualTo(float paramFloat)
/*     */   {
/* 284 */     return Bindings.lessThanOrEqual(this, paramFloat);
/*     */   }
/*     */ 
/*     */   public BooleanBinding lessThanOrEqualTo(long paramLong)
/*     */   {
/* 289 */     return Bindings.lessThanOrEqual(this, paramLong);
/*     */   }
/*     */ 
/*     */   public BooleanBinding lessThanOrEqualTo(int paramInt)
/*     */   {
/* 294 */     return Bindings.lessThanOrEqual(this, paramInt);
/*     */   }
/*     */ 
/*     */   public StringBinding asString()
/*     */   {
/* 302 */     return (StringBinding)StringFormatter.convert(this);
/*     */   }
/*     */ 
/*     */   public StringBinding asString(String paramString)
/*     */   {
/* 307 */     return (StringBinding)Bindings.format(paramString, new Object[] { this });
/*     */   }
/*     */ 
/*     */   public StringBinding asString(Locale paramLocale, String paramString)
/*     */   {
/* 312 */     return (StringBinding)Bindings.format(paramLocale, paramString, new Object[] { this });
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.binding.NumberExpressionBase
 * JD-Core Version:    0.6.2
 */