/*     */ package javafx.beans.binding;
/*     */ 
/*     */ import javafx.beans.Observable;
/*     */ import javafx.beans.value.ObservableDoubleValue;
/*     */ import javafx.beans.value.ObservableNumberValue;
/*     */ import javafx.collections.FXCollections;
/*     */ import javafx.collections.ObservableList;
/*     */ 
/*     */ public abstract class DoubleExpression extends NumberExpressionBase
/*     */   implements ObservableDoubleValue
/*     */ {
/*     */   public int intValue()
/*     */   {
/*  48 */     return (int)get();
/*     */   }
/*     */ 
/*     */   public long longValue()
/*     */   {
/*  53 */     return ()get();
/*     */   }
/*     */ 
/*     */   public float floatValue()
/*     */   {
/*  58 */     return (float)get();
/*     */   }
/*     */ 
/*     */   public double doubleValue()
/*     */   {
/*  63 */     return get();
/*     */   }
/*     */ 
/*     */   public Double getValue()
/*     */   {
/*  68 */     return Double.valueOf(get());
/*     */   }
/*     */ 
/*     */   public static DoubleExpression doubleExpression(ObservableDoubleValue paramObservableDoubleValue)
/*     */   {
/*  88 */     if (paramObservableDoubleValue == null) {
/*  89 */       throw new NullPointerException("Value must be specified.");
/*     */     }
/*  91 */     return (paramObservableDoubleValue instanceof DoubleExpression) ? (DoubleExpression)paramObservableDoubleValue : new DoubleBinding()
/*     */     {
/*     */       public void dispose()
/*     */       {
/*  99 */         super.unbind(new Observable[] { this.val$value });
/*     */       }
/*     */ 
/*     */       protected double computeValue()
/*     */       {
/* 104 */         return this.val$value.get();
/*     */       }
/*     */ 
/*     */       public ObservableList<ObservableDoubleValue> getDependencies()
/*     */       {
/* 110 */         return FXCollections.singletonObservableList(this.val$value);
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public DoubleBinding negate()
/*     */   {
/* 117 */     return (DoubleBinding)Bindings.negate(this);
/*     */   }
/*     */ 
/*     */   public DoubleBinding add(ObservableNumberValue paramObservableNumberValue)
/*     */   {
/* 122 */     return (DoubleBinding)Bindings.add(this, paramObservableNumberValue);
/*     */   }
/*     */ 
/*     */   public DoubleBinding add(double paramDouble)
/*     */   {
/* 127 */     return Bindings.add(this, paramDouble);
/*     */   }
/*     */ 
/*     */   public DoubleBinding add(float paramFloat)
/*     */   {
/* 132 */     return (DoubleBinding)Bindings.add(this, paramFloat);
/*     */   }
/*     */ 
/*     */   public DoubleBinding add(long paramLong)
/*     */   {
/* 137 */     return (DoubleBinding)Bindings.add(this, paramLong);
/*     */   }
/*     */ 
/*     */   public DoubleBinding add(int paramInt)
/*     */   {
/* 142 */     return (DoubleBinding)Bindings.add(this, paramInt);
/*     */   }
/*     */ 
/*     */   public DoubleBinding subtract(ObservableNumberValue paramObservableNumberValue)
/*     */   {
/* 147 */     return (DoubleBinding)Bindings.subtract(this, paramObservableNumberValue);
/*     */   }
/*     */ 
/*     */   public DoubleBinding subtract(double paramDouble)
/*     */   {
/* 152 */     return Bindings.subtract(this, paramDouble);
/*     */   }
/*     */ 
/*     */   public DoubleBinding subtract(float paramFloat)
/*     */   {
/* 157 */     return (DoubleBinding)Bindings.subtract(this, paramFloat);
/*     */   }
/*     */ 
/*     */   public DoubleBinding subtract(long paramLong)
/*     */   {
/* 162 */     return (DoubleBinding)Bindings.subtract(this, paramLong);
/*     */   }
/*     */ 
/*     */   public DoubleBinding subtract(int paramInt)
/*     */   {
/* 167 */     return (DoubleBinding)Bindings.subtract(this, paramInt);
/*     */   }
/*     */ 
/*     */   public DoubleBinding multiply(ObservableNumberValue paramObservableNumberValue)
/*     */   {
/* 172 */     return (DoubleBinding)Bindings.multiply(this, paramObservableNumberValue);
/*     */   }
/*     */ 
/*     */   public DoubleBinding multiply(double paramDouble)
/*     */   {
/* 177 */     return Bindings.multiply(this, paramDouble);
/*     */   }
/*     */ 
/*     */   public DoubleBinding multiply(float paramFloat)
/*     */   {
/* 182 */     return (DoubleBinding)Bindings.multiply(this, paramFloat);
/*     */   }
/*     */ 
/*     */   public DoubleBinding multiply(long paramLong)
/*     */   {
/* 187 */     return (DoubleBinding)Bindings.multiply(this, paramLong);
/*     */   }
/*     */ 
/*     */   public DoubleBinding multiply(int paramInt)
/*     */   {
/* 192 */     return (DoubleBinding)Bindings.multiply(this, paramInt);
/*     */   }
/*     */ 
/*     */   public DoubleBinding divide(ObservableNumberValue paramObservableNumberValue)
/*     */   {
/* 197 */     return (DoubleBinding)Bindings.divide(this, paramObservableNumberValue);
/*     */   }
/*     */ 
/*     */   public DoubleBinding divide(double paramDouble)
/*     */   {
/* 202 */     return Bindings.divide(this, paramDouble);
/*     */   }
/*     */ 
/*     */   public DoubleBinding divide(float paramFloat)
/*     */   {
/* 207 */     return (DoubleBinding)Bindings.divide(this, paramFloat);
/*     */   }
/*     */ 
/*     */   public DoubleBinding divide(long paramLong)
/*     */   {
/* 212 */     return (DoubleBinding)Bindings.divide(this, paramLong);
/*     */   }
/*     */ 
/*     */   public DoubleBinding divide(int paramInt)
/*     */   {
/* 217 */     return (DoubleBinding)Bindings.divide(this, paramInt);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.binding.DoubleExpression
 * JD-Core Version:    0.6.2
 */