/*     */ package javafx.beans.property;
/*     */ 
/*     */ import javafx.beans.binding.Bindings;
/*     */ import javafx.beans.value.WritableSetValue;
/*     */ import javafx.collections.ObservableSet;
/*     */ 
/*     */ public abstract class SetProperty<E> extends ReadOnlySetProperty<E>
/*     */   implements Property<ObservableSet<E>>, WritableSetValue<E>
/*     */ {
/*     */   public void setValue(ObservableSet<E> paramObservableSet)
/*     */   {
/*  84 */     set(paramObservableSet);
/*     */   }
/*     */ 
/*     */   public void bindBidirectional(Property<ObservableSet<E>> paramProperty)
/*     */   {
/*  92 */     Bindings.bindBidirectional(this, paramProperty);
/*     */   }
/*     */ 
/*     */   public void unbindBidirectional(Property<ObservableSet<E>> paramProperty)
/*     */   {
/* 100 */     Bindings.unbindBidirectional(this, paramProperty);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 109 */     Object localObject = getBean();
/* 110 */     String str = getName();
/* 111 */     StringBuilder localStringBuilder = new StringBuilder("SetProperty [");
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
 * Qualified Name:     javafx.beans.property.SetProperty
 * JD-Core Version:    0.6.2
 */