/*     */ package javafx.beans.property;
/*     */ 
/*     */ import javafx.beans.binding.Bindings;
/*     */ import javafx.beans.value.WritableListValue;
/*     */ import javafx.collections.ObservableList;
/*     */ 
/*     */ public abstract class ListProperty<E> extends ReadOnlyListProperty<E>
/*     */   implements Property<ObservableList<E>>, WritableListValue<E>
/*     */ {
/*     */   public void setValue(ObservableList<E> paramObservableList)
/*     */   {
/*  82 */     set(paramObservableList);
/*     */   }
/*     */ 
/*     */   public void bindBidirectional(Property<ObservableList<E>> paramProperty)
/*     */   {
/*  90 */     Bindings.bindBidirectional(this, paramProperty);
/*     */   }
/*     */ 
/*     */   public void unbindBidirectional(Property<ObservableList<E>> paramProperty)
/*     */   {
/*  98 */     Bindings.unbindBidirectional(this, paramProperty);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 107 */     Object localObject = getBean();
/* 108 */     String str = getName();
/* 109 */     StringBuilder localStringBuilder = new StringBuilder("ListProperty [");
/*     */ 
/* 111 */     if (localObject != null) {
/* 112 */       localStringBuilder.append("bean: ").append(localObject).append(", ");
/*     */     }
/* 114 */     if ((str != null) && (!str.equals(""))) {
/* 115 */       localStringBuilder.append("name: ").append(str).append(", ");
/*     */     }
/* 117 */     localStringBuilder.append("value: ").append(get()).append("]");
/* 118 */     return localStringBuilder.toString();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.property.ListProperty
 * JD-Core Version:    0.6.2
 */