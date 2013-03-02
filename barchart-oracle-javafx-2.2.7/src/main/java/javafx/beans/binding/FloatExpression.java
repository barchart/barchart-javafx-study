/*     */ package javafx.beans.binding;
/*     */ 
/*     */ import javafx.beans.Observable;
/*     */ import javafx.beans.value.ObservableFloatValue;
/*     */ import javafx.collections.FXCollections;
/*     */ import javafx.collections.ObservableList;
/*     */ 
/*     */ public abstract class FloatExpression extends NumberExpressionBase
/*     */   implements ObservableFloatValue
/*     */ {
/*     */   public int intValue()
/*     */   {
/*  47 */     return (int)get();
/*     */   }
/*     */ 
/*     */   public long longValue()
/*     */   {
/*  52 */     return ()get();
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
/*     */   public Float getValue()
/*     */   {
/*  67 */     return Float.valueOf(get());
/*     */   }
/*     */ 
/*     */   public static FloatExpression floatExpression(ObservableFloatValue paramObservableFloatValue)
/*     */   {
/*  87 */     if (paramObservableFloatValue == null) {
/*  88 */       throw new NullPointerException("Value must be specified.");
/*     */     }
/*  90 */     return (paramObservableFloatValue instanceof FloatExpression) ? (FloatExpression)paramObservableFloatValue : new FloatBinding()
/*     */     {
/*     */       public void dispose()
/*     */       {
/*  98 */         super.unbind(new Observable[] { this.val$value });
/*     */       }
/*     */ 
/*     */       protected float computeValue()
/*     */       {
/* 103 */         return this.val$value.get();
/*     */       }
/*     */ 
/*     */       public ObservableList<ObservableFloatValue> getDependencies()
/*     */       {
/* 109 */         return FXCollections.singletonObservableList(this.val$value);
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public FloatBinding negate()
/*     */   {
/* 116 */     return (FloatBinding)Bindings.negate(this);
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
/*     */   public FloatBinding add(long paramLong)
/*     */   {
/* 131 */     return (FloatBinding)Bindings.add(this, paramLong);
/*     */   }
/*     */ 
/*     */   public FloatBinding add(int paramInt)
/*     */   {
/* 136 */     return (FloatBinding)Bindings.add(this, paramInt);
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
/*     */   public FloatBinding subtract(long paramLong)
/*     */   {
/* 151 */     return (FloatBinding)Bindings.subtract(this, paramLong);
/*     */   }
/*     */ 
/*     */   public FloatBinding subtract(int paramInt)
/*     */   {
/* 156 */     return (FloatBinding)Bindings.subtract(this, paramInt);
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
/*     */   public FloatBinding multiply(long paramLong)
/*     */   {
/* 171 */     return (FloatBinding)Bindings.multiply(this, paramLong);
/*     */   }
/*     */ 
/*     */   public FloatBinding multiply(int paramInt)
/*     */   {
/* 176 */     return (FloatBinding)Bindings.multiply(this, paramInt);
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
/*     */   public FloatBinding divide(long paramLong)
/*     */   {
/* 191 */     return (FloatBinding)Bindings.divide(this, paramLong);
/*     */   }
/*     */ 
/*     */   public FloatBinding divide(int paramInt)
/*     */   {
/* 196 */     return (FloatBinding)Bindings.divide(this, paramInt);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.binding.FloatExpression
 * JD-Core Version:    0.6.2
 */