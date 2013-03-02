/*     */ package javafx.beans.property;
/*     */ 
/*     */ import javafx.beans.binding.Bindings;
/*     */ import javafx.beans.value.WritableMapValue;
/*     */ import javafx.collections.ObservableMap;
/*     */ 
/*     */ public abstract class MapProperty<K, V> extends ReadOnlyMapProperty<K, V>
/*     */   implements Property<ObservableMap<K, V>>, WritableMapValue<K, V>
/*     */ {
/*     */   public void setValue(ObservableMap<K, V> paramObservableMap)
/*     */   {
/*  83 */     set(paramObservableMap);
/*     */   }
/*     */ 
/*     */   public void bindBidirectional(Property<ObservableMap<K, V>> paramProperty)
/*     */   {
/*  91 */     Bindings.bindBidirectional(this, paramProperty);
/*     */   }
/*     */ 
/*     */   public void unbindBidirectional(Property<ObservableMap<K, V>> paramProperty)
/*     */   {
/*  99 */     Bindings.unbindBidirectional(this, paramProperty);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 108 */     Object localObject = getBean();
/* 109 */     String str = getName();
/* 110 */     StringBuilder localStringBuilder = new StringBuilder("MapProperty [");
/*     */ 
/* 112 */     if (localObject != null) {
/* 113 */       localStringBuilder.append("bean: ").append(localObject).append(", ");
/*     */     }
/* 115 */     if ((str != null) && (!str.equals(""))) {
/* 116 */       localStringBuilder.append("name: ").append(str).append(", ");
/*     */     }
/* 118 */     localStringBuilder.append("value: ").append(get()).append("]");
/* 119 */     return localStringBuilder.toString();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.property.MapProperty
 * JD-Core Version:    0.6.2
 */