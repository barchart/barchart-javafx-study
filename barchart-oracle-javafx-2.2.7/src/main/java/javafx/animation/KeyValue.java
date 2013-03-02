/*     */ package javafx.animation;
/*     */ 
/*     */ import javafx.beans.value.WritableBooleanValue;
/*     */ import javafx.beans.value.WritableDoubleValue;
/*     */ import javafx.beans.value.WritableFloatValue;
/*     */ import javafx.beans.value.WritableIntegerValue;
/*     */ import javafx.beans.value.WritableLongValue;
/*     */ import javafx.beans.value.WritableNumberValue;
/*     */ import javafx.beans.value.WritableValue;
/*     */ 
/*     */ public final class KeyValue
/*     */ {
/*  63 */   private static final Interpolator DEFAULT_INTERPOLATOR = Interpolator.LINEAR;
/*     */   private final Type type;
/*     */   private final WritableValue<?> target;
/*     */   private final Object endValue;
/*     */   private final Interpolator interpolator;
/*     */ 
/*     */   @Deprecated
/*     */   public Type getType()
/*     */   {
/*  80 */     return this.type;
/*     */   }
/*     */ 
/*     */   public WritableValue<?> getTarget()
/*     */   {
/*  91 */     return this.target;
/*     */   }
/*     */ 
/*     */   public Object getEndValue()
/*     */   {
/* 102 */     return this.endValue;
/*     */   }
/*     */ 
/*     */   public Interpolator getInterpolator()
/*     */   {
/* 112 */     return this.interpolator;
/*     */   }
/*     */ 
/*     */   public <T> KeyValue(WritableValue<T> paramWritableValue, T paramT, Interpolator paramInterpolator)
/*     */   {
/* 131 */     if (paramWritableValue == null) {
/* 132 */       throw new NullPointerException("Target needs to be specified");
/*     */     }
/* 134 */     if (paramInterpolator == null) {
/* 135 */       throw new NullPointerException("Interpolator needs to be specified");
/*     */     }
/*     */ 
/* 138 */     this.target = paramWritableValue;
/* 139 */     this.endValue = paramT;
/* 140 */     this.interpolator = paramInterpolator;
/* 141 */     this.type = ((paramWritableValue instanceof WritableBooleanValue) ? Type.BOOLEAN : (paramWritableValue instanceof WritableNumberValue) ? Type.OBJECT : (paramWritableValue instanceof WritableLongValue) ? Type.LONG : (paramWritableValue instanceof WritableFloatValue) ? Type.FLOAT : (paramWritableValue instanceof WritableIntegerValue) ? Type.INTEGER : (paramWritableValue instanceof WritableDoubleValue) ? Type.DOUBLE : Type.OBJECT);
/*     */   }
/*     */ 
/*     */   public <T> KeyValue(WritableValue<T> paramWritableValue, T paramT)
/*     */   {
/* 161 */     this(paramWritableValue, paramT, DEFAULT_INTERPOLATOR);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 170 */     return "KeyValue [target=" + this.target + ", endValue=" + this.endValue + ", interpolator=" + this.interpolator + "]";
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 180 */     assert ((this.target != null) && (this.interpolator != null));
/*     */ 
/* 182 */     int i = 1;
/* 183 */     i = 31 * i + this.target.hashCode();
/* 184 */     i = 31 * i + (this.endValue == null ? 0 : this.endValue.hashCode());
/*     */ 
/* 186 */     i = 31 * i + this.interpolator.hashCode();
/* 187 */     return i;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 198 */     if (this == paramObject) {
/* 199 */       return true;
/*     */     }
/* 201 */     if ((paramObject instanceof KeyValue)) {
/* 202 */       KeyValue localKeyValue = (KeyValue)paramObject;
/* 203 */       assert ((this.target != null) && (this.interpolator != null) && (localKeyValue.target != null) && (localKeyValue.interpolator != null));
/*     */ 
/* 206 */       return (this.target.equals(localKeyValue.target)) && (this.endValue == null ? localKeyValue.endValue == null : this.endValue.equals(localKeyValue.endValue)) && (this.interpolator.equals(localKeyValue.interpolator));
/*     */     }
/*     */ 
/* 211 */     return false;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public static enum Type
/*     */   {
/*  71 */     BOOLEAN, DOUBLE, FLOAT, INTEGER, LONG, OBJECT;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.animation.KeyValue
 * JD-Core Version:    0.6.2
 */