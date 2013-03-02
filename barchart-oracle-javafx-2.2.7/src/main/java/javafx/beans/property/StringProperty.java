/*     */ package javafx.beans.property;
/*     */ 
/*     */ import java.text.Format;
/*     */ import javafx.beans.binding.Bindings;
/*     */ import javafx.beans.value.WritableStringValue;
/*     */ import javafx.util.StringConverter;
/*     */ 
/*     */ public abstract class StringProperty extends ReadOnlyStringProperty
/*     */   implements Property<String>, WritableStringValue
/*     */ {
/*     */   public void setValue(String paramString)
/*     */   {
/*  84 */     set(paramString);
/*     */   }
/*     */ 
/*     */   public void bindBidirectional(Property<String> paramProperty)
/*     */   {
/*  92 */     Bindings.bindBidirectional(this, paramProperty);
/*     */   }
/*     */ 
/*     */   public void bindBidirectional(Property<?> paramProperty, Format paramFormat)
/*     */   {
/* 110 */     Bindings.bindBidirectional(this, paramProperty, paramFormat);
/*     */   }
/*     */ 
/*     */   public <T> void bindBidirectional(Property<T> paramProperty, StringConverter<T> paramStringConverter)
/*     */   {
/* 128 */     Bindings.bindBidirectional(this, paramProperty, paramStringConverter);
/*     */   }
/*     */ 
/*     */   public void unbindBidirectional(Property<String> paramProperty)
/*     */   {
/* 136 */     Bindings.unbindBidirectional(this, paramProperty);
/*     */   }
/*     */ 
/*     */   public void unbindBidirectional(Object paramObject)
/*     */   {
/* 154 */     Bindings.unbindBidirectional(this, paramObject);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 163 */     Object localObject = getBean();
/* 164 */     String str = getName();
/* 165 */     StringBuilder localStringBuilder = new StringBuilder("StringProperty [");
/*     */ 
/* 167 */     if (localObject != null) {
/* 168 */       localStringBuilder.append("bean: ").append(localObject).append(", ");
/*     */     }
/* 170 */     if ((str != null) && (!str.equals(""))) {
/* 171 */       localStringBuilder.append("name: ").append(str).append(", ");
/*     */     }
/* 173 */     localStringBuilder.append("value: ").append((String)get()).append("]");
/* 174 */     return localStringBuilder.toString();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.property.StringProperty
 * JD-Core Version:    0.6.2
 */