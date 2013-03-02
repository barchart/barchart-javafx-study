/*     */ package javafx.beans.property;
/*     */ 
/*     */ import javafx.beans.binding.Bindings;
/*     */ import javafx.beans.value.WritableDoubleValue;
/*     */ 
/*     */ public abstract class DoubleProperty extends ReadOnlyDoubleProperty
/*     */   implements Property<Number>, WritableDoubleValue
/*     */ {
/*     */   public void setValue(Number paramNumber)
/*     */   {
/*  80 */     set(paramNumber == null ? 0.0D : paramNumber.doubleValue());
/*     */   }
/*     */ 
/*     */   public void bindBidirectional(Property<Number> paramProperty)
/*     */   {
/*  88 */     Bindings.bindBidirectional(this, paramProperty);
/*     */   }
/*     */ 
/*     */   public void unbindBidirectional(Property<Number> paramProperty)
/*     */   {
/*  96 */     Bindings.unbindBidirectional(this, paramProperty);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 105 */     Object localObject = getBean();
/* 106 */     String str = getName();
/* 107 */     StringBuilder localStringBuilder = new StringBuilder("DoubleProperty [");
/*     */ 
/* 109 */     if (localObject != null) {
/* 110 */       localStringBuilder.append("bean: ").append(localObject).append(", ");
/*     */     }
/* 112 */     if ((str != null) && (!str.equals(""))) {
/* 113 */       localStringBuilder.append("name: ").append(str).append(", ");
/*     */     }
/* 115 */     localStringBuilder.append("value: ").append(get()).append("]");
/* 116 */     return localStringBuilder.toString();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.property.DoubleProperty
 * JD-Core Version:    0.6.2
 */