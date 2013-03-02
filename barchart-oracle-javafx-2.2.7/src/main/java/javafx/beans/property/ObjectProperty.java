/*     */ package javafx.beans.property;
/*     */ 
/*     */ import javafx.beans.binding.Bindings;
/*     */ import javafx.beans.value.WritableObjectValue;
/*     */ 
/*     */ public abstract class ObjectProperty<T> extends ReadOnlyObjectProperty<T>
/*     */   implements Property<T>, WritableObjectValue<T>
/*     */ {
/*     */   public void setValue(T paramT)
/*     */   {
/*  84 */     set(paramT);
/*     */   }
/*     */ 
/*     */   public void bindBidirectional(Property<T> paramProperty)
/*     */   {
/*  92 */     Bindings.bindBidirectional(this, paramProperty);
/*     */   }
/*     */ 
/*     */   public void unbindBidirectional(Property<T> paramProperty)
/*     */   {
/* 100 */     Bindings.unbindBidirectional(this, paramProperty);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 109 */     Object localObject = getBean();
/* 110 */     String str = getName();
/* 111 */     StringBuilder localStringBuilder = new StringBuilder("ObjectProperty [");
/*     */ 
/* 113 */     if (localObject != null) {
/* 114 */       localStringBuilder.append("bean: ").append(localObject).append(", ");
/*     */     }
/* 116 */     if ((str != null) && (!str.equals(""))) {
/* 117 */       localStringBuilder.append("name: ").append(str).append(", ");
/*     */     }
/* 119 */     localStringBuilder.append("value: ").append(get()).append("]");
/* 120 */     return localStringBuilder.toString();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.property.ObjectProperty
 * JD-Core Version:    0.6.2
 */