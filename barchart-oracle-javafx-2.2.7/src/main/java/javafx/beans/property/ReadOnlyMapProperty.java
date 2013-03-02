/*     */ package javafx.beans.property;
/*     */ 
/*     */ import javafx.beans.binding.Bindings;
/*     */ import javafx.beans.binding.MapExpression;
/*     */ import javafx.collections.ObservableMap;
/*     */ 
/*     */ public abstract class ReadOnlyMapProperty<K, V> extends MapExpression<K, V>
/*     */   implements ReadOnlyProperty<ObservableMap<K, V>>
/*     */ {
/*     */   public void bindContentBidirectional(ObservableMap<K, V> paramObservableMap)
/*     */   {
/*  84 */     Bindings.bindContentBidirectional(this, paramObservableMap);
/*     */   }
/*     */ 
/*     */   public void unbindContentBidirectional(Object paramObject)
/*     */   {
/*  96 */     Bindings.unbindContentBidirectional(this, paramObject);
/*     */   }
/*     */ 
/*     */   public void bindContent(ObservableMap<K, V> paramObservableMap)
/*     */   {
/* 112 */     Bindings.bindContent(this, paramObservableMap);
/*     */   }
/*     */ 
/*     */   public void unbindContent(Object paramObject)
/*     */   {
/* 124 */     Bindings.unbindContent(this, paramObject);
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 134 */     if (this == paramObject) {
/* 135 */       return true;
/*     */     }
/* 137 */     Object localObject1 = getBean();
/* 138 */     String str1 = getName();
/* 139 */     if ((localObject1 == null) && ((str1 == null) || (str1.equals("")))) {
/* 140 */       return false;
/*     */     }
/* 142 */     if ((paramObject instanceof ReadOnlyMapProperty)) {
/* 143 */       ReadOnlyMapProperty localReadOnlyMapProperty = (ReadOnlyMapProperty)paramObject;
/* 144 */       Object localObject2 = localReadOnlyMapProperty.getBean();
/* 145 */       String str2 = localReadOnlyMapProperty.getName();
/* 146 */       return (localObject1 == null ? localObject2 == null : localObject1.equals(localObject2)) && (str1 == null ? str2 == null : str1.equals(str2));
/*     */     }
/*     */ 
/* 149 */     return false;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 158 */     Object localObject = getBean();
/* 159 */     String str = getName();
/* 160 */     if ((localObject == null) && ((str == null) || (str.equals("")))) {
/* 161 */       return super.hashCode();
/*     */     }
/* 163 */     int i = 17;
/* 164 */     i = 31 * i + (localObject == null ? 0 : localObject.hashCode());
/* 165 */     i = 31 * i + (str == null ? 0 : str.hashCode());
/* 166 */     return i;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 176 */     Object localObject = getBean();
/* 177 */     String str = getName();
/* 178 */     StringBuilder localStringBuilder = new StringBuilder("ReadOnlyMapProperty [");
/*     */ 
/* 180 */     if (localObject != null) {
/* 181 */       localStringBuilder.append("bean: ").append(localObject).append(", ");
/*     */     }
/* 183 */     if ((str != null) && (!str.equals(""))) {
/* 184 */       localStringBuilder.append("name: ").append(str).append(", ");
/*     */     }
/* 186 */     localStringBuilder.append("value: ").append(get()).append("]");
/* 187 */     return localStringBuilder.toString();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.property.ReadOnlyMapProperty
 * JD-Core Version:    0.6.2
 */