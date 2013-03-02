/*     */ package javafx.beans.property;
/*     */ 
/*     */ import javafx.beans.binding.BooleanExpression;
/*     */ 
/*     */ public abstract class ReadOnlyBooleanProperty extends BooleanExpression
/*     */   implements ReadOnlyProperty<Boolean>
/*     */ {
/*     */   public boolean equals(Object paramObject)
/*     */   {
/*  74 */     if (this == paramObject) {
/*  75 */       return true;
/*     */     }
/*  77 */     Object localObject1 = getBean();
/*  78 */     String str1 = getName();
/*  79 */     if ((localObject1 == null) && ((str1 == null) || (str1.equals("")))) {
/*  80 */       return false;
/*     */     }
/*  82 */     if ((paramObject instanceof ReadOnlyBooleanProperty)) {
/*  83 */       ReadOnlyBooleanProperty localReadOnlyBooleanProperty = (ReadOnlyBooleanProperty)paramObject;
/*  84 */       Object localObject2 = localReadOnlyBooleanProperty.getBean();
/*  85 */       String str2 = localReadOnlyBooleanProperty.getName();
/*  86 */       return (localObject1 == null ? localObject2 == null : localObject1.equals(localObject2)) && (str1 == null ? str2 == null : str1.equals(str2));
/*     */     }
/*     */ 
/*  89 */     return false;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  98 */     Object localObject = getBean();
/*  99 */     String str = getName();
/* 100 */     if ((localObject == null) && ((str == null) || (str.equals("")))) {
/* 101 */       return super.hashCode();
/*     */     }
/* 103 */     int i = 17;
/* 104 */     i = 31 * i + (localObject == null ? 0 : localObject.hashCode());
/* 105 */     i = 31 * i + (str == null ? 0 : str.hashCode());
/* 106 */     return i;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 116 */     Object localObject = getBean();
/* 117 */     String str = getName();
/* 118 */     StringBuilder localStringBuilder = new StringBuilder("ReadOnlyBooleanProperty [");
/*     */ 
/* 120 */     if (localObject != null) {
/* 121 */       localStringBuilder.append("bean: ").append(localObject).append(", ");
/*     */     }
/* 123 */     if ((str != null) && (!str.equals(""))) {
/* 124 */       localStringBuilder.append("name: ").append(str).append(", ");
/*     */     }
/* 126 */     localStringBuilder.append("value: ").append(get()).append("]");
/* 127 */     return localStringBuilder.toString();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.property.ReadOnlyBooleanProperty
 * JD-Core Version:    0.6.2
 */