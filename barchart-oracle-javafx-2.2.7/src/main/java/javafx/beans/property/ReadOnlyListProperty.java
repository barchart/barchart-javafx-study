/*     */ package javafx.beans.property;
/*     */ 
/*     */ import javafx.beans.binding.Bindings;
/*     */ import javafx.beans.binding.ListExpression;
/*     */ import javafx.collections.ObservableList;
/*     */ 
/*     */ public abstract class ReadOnlyListProperty<E> extends ListExpression<E>
/*     */   implements ReadOnlyProperty<ObservableList<E>>
/*     */ {
/*     */   public void bindContentBidirectional(ObservableList<E> paramObservableList)
/*     */   {
/*  83 */     Bindings.bindContentBidirectional(this, paramObservableList);
/*     */   }
/*     */ 
/*     */   public void unbindContentBidirectional(Object paramObject)
/*     */   {
/*  95 */     Bindings.unbindContentBidirectional(this, paramObject);
/*     */   }
/*     */ 
/*     */   public void bindContent(ObservableList<E> paramObservableList)
/*     */   {
/* 111 */     Bindings.bindContent(this, paramObservableList);
/*     */   }
/*     */ 
/*     */   public void unbindContent(Object paramObject)
/*     */   {
/* 123 */     Bindings.unbindContent(this, paramObject);
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 133 */     if (this == paramObject) {
/* 134 */       return true;
/*     */     }
/* 136 */     Object localObject1 = getBean();
/* 137 */     String str1 = getName();
/* 138 */     if ((localObject1 == null) && ((str1 == null) || (str1.equals("")))) {
/* 139 */       return false;
/*     */     }
/* 141 */     if ((paramObject instanceof ReadOnlyListProperty)) {
/* 142 */       ReadOnlyListProperty localReadOnlyListProperty = (ReadOnlyListProperty)paramObject;
/* 143 */       Object localObject2 = localReadOnlyListProperty.getBean();
/* 144 */       String str2 = localReadOnlyListProperty.getName();
/* 145 */       return (localObject1 == null ? localObject2 == null : localObject1.equals(localObject2)) && (str1 == null ? str2 == null : str1.equals(str2));
/*     */     }
/*     */ 
/* 148 */     return false;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 157 */     Object localObject = getBean();
/* 158 */     String str = getName();
/* 159 */     if ((localObject == null) && ((str == null) || (str.equals("")))) {
/* 160 */       return super.hashCode();
/*     */     }
/* 162 */     int i = 17;
/* 163 */     i = 31 * i + (localObject == null ? 0 : localObject.hashCode());
/* 164 */     i = 31 * i + (str == null ? 0 : str.hashCode());
/* 165 */     return i;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 175 */     Object localObject = getBean();
/* 176 */     String str = getName();
/* 177 */     StringBuilder localStringBuilder = new StringBuilder("ReadOnlyListProperty [");
/*     */ 
/* 179 */     if (localObject != null) {
/* 180 */       localStringBuilder.append("bean: ").append(localObject).append(", ");
/*     */     }
/* 182 */     if ((str != null) && (!str.equals(""))) {
/* 183 */       localStringBuilder.append("name: ").append(str).append(", ");
/*     */     }
/* 185 */     localStringBuilder.append("value: ").append(get()).append("]");
/* 186 */     return localStringBuilder.toString();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.property.ReadOnlyListProperty
 * JD-Core Version:    0.6.2
 */