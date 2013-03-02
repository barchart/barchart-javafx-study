/*     */ package javafx.beans.property.adapter;
/*     */ 
/*     */ import com.sun.javafx.property.adapter.ReadOnlyJavaBeanPropertyBuilderHelper;
/*     */ import com.sun.javafx.property.adapter.ReadOnlyPropertyDescriptor;
/*     */ import java.lang.reflect.Method;
/*     */ 
/*     */ public final class ReadOnlyJavaBeanIntegerPropertyBuilder
/*     */ {
/*  80 */   private final ReadOnlyJavaBeanPropertyBuilderHelper helper = new ReadOnlyJavaBeanPropertyBuilderHelper();
/*     */ 
/*     */   public static ReadOnlyJavaBeanIntegerPropertyBuilder create()
/*     */   {
/*  88 */     return new ReadOnlyJavaBeanIntegerPropertyBuilder();
/*     */   }
/*     */ 
/*     */   public ReadOnlyJavaBeanIntegerProperty build()
/*     */     throws NoSuchMethodException
/*     */   {
/* 101 */     ReadOnlyPropertyDescriptor localReadOnlyPropertyDescriptor = this.helper.getDescriptor();
/* 102 */     if ((!Integer.TYPE.equals(localReadOnlyPropertyDescriptor.getType())) && (!Number.class.isAssignableFrom(localReadOnlyPropertyDescriptor.getType()))) {
/* 103 */       throw new IllegalArgumentException("Not an int property");
/*     */     }
/* 105 */     return new ReadOnlyJavaBeanIntegerProperty(localReadOnlyPropertyDescriptor, this.helper.getBean());
/*     */   }
/*     */ 
/*     */   public ReadOnlyJavaBeanIntegerPropertyBuilder name(String paramString)
/*     */   {
/* 115 */     this.helper.name(paramString);
/* 116 */     return this;
/*     */   }
/*     */ 
/*     */   public ReadOnlyJavaBeanIntegerPropertyBuilder bean(Object paramObject)
/*     */   {
/* 126 */     this.helper.bean(paramObject);
/* 127 */     return this;
/*     */   }
/*     */ 
/*     */   public ReadOnlyJavaBeanIntegerPropertyBuilder beanClass(Class<?> paramClass)
/*     */   {
/* 139 */     this.helper.beanClass(paramClass);
/* 140 */     return this;
/*     */   }
/*     */ 
/*     */   public ReadOnlyJavaBeanIntegerPropertyBuilder getter(String paramString)
/*     */   {
/* 151 */     this.helper.getterName(paramString);
/* 152 */     return this;
/*     */   }
/*     */ 
/*     */   public ReadOnlyJavaBeanIntegerPropertyBuilder getter(Method paramMethod)
/*     */   {
/* 163 */     this.helper.getter(paramMethod);
/* 164 */     return this;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.property.adapter.ReadOnlyJavaBeanIntegerPropertyBuilder
 * JD-Core Version:    0.6.2
 */