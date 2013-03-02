/*     */ package javafx.beans.binding;
/*     */ 
/*     */ import javafx.beans.Observable;
/*     */ import javafx.beans.value.ObservableIntegerValue;
/*     */ import javafx.collections.FXCollections;
/*     */ import javafx.collections.ObservableList;
/*     */ 
/*     */ public abstract class IntegerExpression extends NumberExpressionBase
/*     */   implements ObservableIntegerValue
/*     */ {
/*     */   public int intValue()
/*     */   {
/*  47 */     return get();
/*     */   }
/*     */ 
/*     */   public long longValue()
/*     */   {
/*  52 */     return get();
/*     */   }
/*     */ 
/*     */   public float floatValue()
/*     */   {
/*  57 */     return get();
/*     */   }
/*     */ 
/*     */   public double doubleValue()
/*     */   {
/*  62 */     return get();
/*     */   }
/*     */ 
/*     */   public Integer getValue()
/*     */   {
/*  67 */     return Integer.valueOf(get());
/*     */   }
/*     */ 
/*     */   public static IntegerExpression integerExpression(ObservableIntegerValue paramObservableIntegerValue)
/*     */   {
/*  87 */     if (paramObservableIntegerValue == null) {
/*  88 */       throw new NullPointerException("Value must be specified.");
/*     */     }
/*  90 */     return (paramObservableIntegerValue instanceof IntegerExpression) ? (IntegerExpression)paramObservableIntegerValue : new IntegerBinding()
/*     */     {
/*     */       public void dispose()
/*     */       {
/*  98 */         super.unbind(new Observable[] { this.val$value });
/*     */       }
/*     */ 
/*     */       protected int computeValue()
/*     */       {
/* 103 */         return this.val$value.get();
/*     */       }
/*     */ 
/*     */       public ObservableList<ObservableIntegerValue> getDependencies()
/*     */       {
/* 109 */         return FXCollections.singletonObservableList(this.val$value);
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public IntegerBinding negate()
/*     */   {
/* 116 */     return (IntegerBinding)Bindings.negate(this);
/*     */   }
/*     */ 
/*     */   public DoubleBinding add(double paramDouble)
/*     */   {
/* 121 */     return Bindings.add(this, paramDouble);
/*     */   }
/*     */ 
/*     */   public FloatBinding add(float paramFloat)
/*     */   {
/* 126 */     return (FloatBinding)Bindings.add(this, paramFloat);
/*     */   }
/*     */ 
/*     */   public LongBinding add(long paramLong)
/*     */   {
/* 131 */     return (LongBinding)Bindings.add(this, paramLong);
/*     */   }
/*     */ 
/*     */   public IntegerBinding add(int paramInt)
/*     */   {
/* 136 */     return (IntegerBinding)Bindings.add(this, paramInt);
/*     */   }
/*     */ 
/*     */   public DoubleBinding subtract(double paramDouble)
/*     */   {
/* 141 */     return Bindings.subtract(this, paramDouble);
/*     */   }
/*     */ 
/*     */   public FloatBinding subtract(float paramFloat)
/*     */   {
/* 146 */     return (FloatBinding)Bindings.subtract(this, paramFloat);
/*     */   }
/*     */ 
/*     */   public LongBinding subtract(long paramLong)
/*     */   {
/* 151 */     return (LongBinding)Bindings.subtract(this, paramLong);
/*     */   }
/*     */ 
/*     */   public IntegerBinding subtract(int paramInt)
/*     */   {
/* 156 */     return (IntegerBinding)Bindings.subtract(this, paramInt);
/*     */   }
/*     */ 
/*     */   public DoubleBinding multiply(double paramDouble)
/*     */   {
/* 161 */     return Bindings.multiply(this, paramDouble);
/*     */   }
/*     */ 
/*     */   public FloatBinding multiply(float paramFloat)
/*     */   {
/* 166 */     return (FloatBinding)Bindings.multiply(this, paramFloat);
/*     */   }
/*     */ 
/*     */   public LongBinding multiply(long paramLong)
/*     */   {
/* 171 */     return (LongBinding)Bindings.multiply(this, paramLong);
/*     */   }
/*     */ 
/*     */   public IntegerBinding multiply(int paramInt)
/*     */   {
/* 176 */     return (IntegerBinding)Bindings.multiply(this, paramInt);
/*     */   }
/*     */ 
/*     */   public DoubleBinding divide(double paramDouble)
/*     */   {
/* 181 */     return Bindings.divide(this, paramDouble);
/*     */   }
/*     */ 
/*     */   public FloatBinding divide(float paramFloat)
/*     */   {
/* 186 */     return (FloatBinding)Bindings.divide(this, paramFloat);
/*     */   }
/*     */ 
/*     */   public LongBinding divide(long paramLong)
/*     */   {
/* 191 */     return (LongBinding)Bindings.divide(this, paramLong);
/*     */   }
/*     */ 
/*     */   public IntegerBinding divide(int paramInt)
/*     */   {
/* 196 */     return (IntegerBinding)Bindings.divide(this, paramInt);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.binding.IntegerExpression
 * JD-Core Version:    0.6.2
 */