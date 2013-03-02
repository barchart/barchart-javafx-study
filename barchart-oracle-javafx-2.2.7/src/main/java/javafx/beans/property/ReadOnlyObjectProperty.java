/*     */ package javafx.beans.property;
/*     */ 
/*     */ import javafx.beans.binding.ObjectExpression;
/*     */ 
/*     */ public abstract class ReadOnlyObjectProperty<T> extends ObjectExpression<T>
/*     */   implements ReadOnlyProperty<T>
/*     */ {
/*     */   public boolean equals(Object paramObject)
/*     */   {
/*  77 */     if (this == paramObject) {
/*  78 */       return true;
/*     */     }
/*  80 */     Object localObject1 = getBean();
/*  81 */     String str1 = getName();
/*  82 */     if ((localObject1 == null) && ((str1 == null) || (str1.equals("")))) {
/*  83 */       return false;
/*     */     }
/*  85 */     if ((paramObject instanceof ReadOnlyObjectProperty)) {
/*  86 */       ReadOnlyObjectProperty localReadOnlyObjectProperty = (ReadOnlyObjectProperty)paramObject;
/*  87 */       Object localObject2 = localReadOnlyObjectProperty.getBean();
/*  88 */       String str2 = localReadOnlyObjectProperty.getName();
/*  89 */       return (localObject1 == null ? localObject2 == null : localObject1.equals(localObject2)) && (str1 == null ? str2 == null : str1.equals(str2));
/*     */     }
/*     */ 
/*  92 */     return false;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 101 */     Object localObject = getBean();
/* 102 */     String str = getName();
/* 103 */     if ((localObject == null) && ((str == null) || (str.equals("")))) {
/* 104 */       return super.hashCode();
/*     */     }
/* 106 */     int i = 17;
/* 107 */     i = 31 * i + (localObject == null ? 0 : localObject.hashCode());
/* 108 */     i = 31 * i + (str == null ? 0 : str.hashCode());
/* 109 */     return i;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 119 */     Object localObject = getBean();
/* 120 */     String str = getName();
/* 121 */     StringBuilder localStringBuilder = new StringBuilder("ReadOnlyObjectProperty [");
/*     */ 
/* 123 */     if (localObject != null) {
/* 124 */       localStringBuilder.append("bean: ").append(localObject).append(", ");
/*     */     }
/* 126 */     if ((str != null) && (!str.equals(""))) {
/* 127 */       localStringBuilder.append("name: ").append(str).append(", ");
/*     */     }
/* 129 */     localStringBuilder.append("value: ").append(get()).append("]");
/* 130 */     return localStringBuilder.toString();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.property.ReadOnlyObjectProperty
 * JD-Core Version:    0.6.2
 */