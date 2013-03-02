/*     */ package javafx.beans.binding;
/*     */ 
/*     */ import javafx.beans.Observable;
/*     */ import javafx.beans.value.ObservableLongValue;
/*     */ import javafx.collections.FXCollections;
/*     */ import javafx.collections.ObservableList;
/*     */ 
/*     */ public abstract class LongExpression extends NumberExpressionBase
/*     */   implements ObservableLongValue
/*     */ {
/*     */   public int intValue()
/*     */   {
/*  46 */     return (int)get();
/*     */   }
/*     */ 
/*     */   public long longValue()
/*     */   {
/*  51 */     return get();
/*     */   }
/*     */ 
/*     */   public float floatValue()
/*     */   {
/*  56 */     return (float)get();
/*     */   }
/*     */ 
/*     */   public double doubleValue()
/*     */   {
/*  61 */     return get();
/*     */   }
/*     */ 
/*     */   public Long getValue()
/*     */   {
/*  66 */     return Long.valueOf(get());
/*     */   }
/*     */ 
/*     */   public static LongExpression longExpression(ObservableLongValue paramObservableLongValue)
/*     */   {
/*  84 */     if (paramObservableLongValue == null) {
/*  85 */       throw new NullPointerException("Value must be specified.");
/*     */     }
/*  87 */     return (paramObservableLongValue instanceof LongExpression) ? (LongExpression)paramObservableLongValue : new LongBinding()
/*     */     {
/*     */       public void dispose()
/*     */       {
/*  95 */         super.unbind(new Observable[] { this.val$value });
/*     */       }
/*     */ 
/*     */       protected long computeValue()
/*     */       {
/* 100 */         return this.val$value.get();
/*     */       }
/*     */ 
/*     */       public ObservableList<ObservableLongValue> getDependencies()
/*     */       {
/* 106 */         return FXCollections.singletonObservableList(this.val$value);
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public LongBinding negate()
/*     */   {
/* 113 */     return (LongBinding)Bindings.negate(this);
/*     */   }
/*     */ 
/*     */   public DoubleBinding add(double paramDouble)
/*     */   {
/* 118 */     return Bindings.add(this, paramDouble);
/*     */   }
/*     */ 
/*     */   public FloatBinding add(float paramFloat)
/*     */   {
/* 123 */     return (FloatBinding)Bindings.add(this, paramFloat);
/*     */   }
/*     */ 
/*     */   public LongBinding add(long paramLong)
/*     */   {
/* 128 */     return (LongBinding)Bindings.add(this, paramLong);
/*     */   }
/*     */ 
/*     */   public LongBinding add(int paramInt)
/*     */   {
/* 133 */     return (LongBinding)Bindings.add(this, paramInt);
/*     */   }
/*     */ 
/*     */   public DoubleBinding subtract(double paramDouble)
/*     */   {
/* 138 */     return Bindings.subtract(this, paramDouble);
/*     */   }
/*     */ 
/*     */   public FloatBinding subtract(float paramFloat)
/*     */   {
/* 143 */     return (FloatBinding)Bindings.subtract(this, paramFloat);
/*     */   }
/*     */ 
/*     */   public LongBinding subtract(long paramLong)
/*     */   {
/* 148 */     return (LongBinding)Bindings.subtract(this, paramLong);
/*     */   }
/*     */ 
/*     */   public LongBinding subtract(int paramInt)
/*     */   {
/* 153 */     return (LongBinding)Bindings.subtract(this, paramInt);
/*     */   }
/*     */ 
/*     */   public DoubleBinding multiply(double paramDouble)
/*     */   {
/* 158 */     return Bindings.multiply(this, paramDouble);
/*     */   }
/*     */ 
/*     */   public FloatBinding multiply(float paramFloat)
/*     */   {
/* 163 */     return (FloatBinding)Bindings.multiply(this, paramFloat);
/*     */   }
/*     */ 
/*     */   public LongBinding multiply(long paramLong)
/*     */   {
/* 168 */     return (LongBinding)Bindings.multiply(this, paramLong);
/*     */   }
/*     */ 
/*     */   public LongBinding multiply(int paramInt)
/*     */   {
/* 173 */     return (LongBinding)Bindings.multiply(this, paramInt);
/*     */   }
/*     */ 
/*     */   public DoubleBinding divide(double paramDouble)
/*     */   {
/* 178 */     return Bindings.divide(this, paramDouble);
/*     */   }
/*     */ 
/*     */   public FloatBinding divide(float paramFloat)
/*     */   {
/* 183 */     return (FloatBinding)Bindings.divide(this, paramFloat);
/*     */   }
/*     */ 
/*     */   public LongBinding divide(long paramLong)
/*     */   {
/* 188 */     return (LongBinding)Bindings.divide(this, paramLong);
/*     */   }
/*     */ 
/*     */   public LongBinding divide(int paramInt)
/*     */   {
/* 193 */     return (LongBinding)Bindings.divide(this, paramInt);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.binding.LongExpression
 * JD-Core Version:    0.6.2
 */