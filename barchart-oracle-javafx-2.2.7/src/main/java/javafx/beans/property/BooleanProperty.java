/*     */ package javafx.beans.property;
/*     */ 
/*     */ import javafx.beans.binding.Bindings;
/*     */ import javafx.beans.value.WritableBooleanValue;
/*     */ 
/*     */ public abstract class BooleanProperty extends ReadOnlyBooleanProperty
/*     */   implements Property<Boolean>, WritableBooleanValue
/*     */ {
/*     */   public void setValue(Boolean paramBoolean)
/*     */   {
/*  81 */     set(paramBoolean == null ? false : paramBoolean.booleanValue());
/*     */   }
/*     */ 
/*     */   public void bindBidirectional(Property<Boolean> paramProperty)
/*     */   {
/*  89 */     Bindings.bindBidirectional(this, paramProperty);
/*     */   }
/*     */ 
/*     */   public void unbindBidirectional(Property<Boolean> paramProperty)
/*     */   {
/*  97 */     Bindings.unbindBidirectional(this, paramProperty);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 106 */     Object localObject = getBean();
/* 107 */     String str = getName();
/* 108 */     StringBuilder localStringBuilder = new StringBuilder("BooleanProperty [");
/*     */ 
/* 110 */     if (localObject != null) {
/* 111 */       localStringBuilder.append("bean: ").append(localObject).append(", ");
/*     */     }
/* 113 */     if ((str != null) && (!str.equals(""))) {
/* 114 */       localStringBuilder.append("name: ").append(str).append(", ");
/*     */     }
/* 116 */     localStringBuilder.append("value: ").append(get()).append("]");
/* 117 */     return localStringBuilder.toString();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.property.BooleanProperty
 * JD-Core Version:    0.6.2
 */