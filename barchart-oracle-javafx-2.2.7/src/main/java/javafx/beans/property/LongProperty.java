/*     */ package javafx.beans.property;
/*     */ 
/*     */ import javafx.beans.binding.Bindings;
/*     */ import javafx.beans.value.WritableLongValue;
/*     */ 
/*     */ public abstract class LongProperty extends ReadOnlyLongProperty
/*     */   implements Property<Number>, WritableLongValue
/*     */ {
/*     */   public void setValue(Number paramNumber)
/*     */   {
/*  80 */     set(paramNumber == null ? 0L : paramNumber.longValue());
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
/* 107 */     StringBuilder localStringBuilder = new StringBuilder("LongProperty [");
/* 108 */     if (localObject != null) {
/* 109 */       localStringBuilder.append("bean: ").append(localObject).append(", ");
/*     */     }
/* 111 */     if ((str != null) && (!str.equals(""))) {
/* 112 */       localStringBuilder.append("name: ").append(str).append(", ");
/*     */     }
/* 114 */     localStringBuilder.append("value: ").append(get()).append("]");
/* 115 */     return localStringBuilder.toString();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.property.LongProperty
 * JD-Core Version:    0.6.2
 */