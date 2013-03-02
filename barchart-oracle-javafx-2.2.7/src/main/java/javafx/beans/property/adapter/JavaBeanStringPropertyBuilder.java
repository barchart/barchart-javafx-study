/*     */ package javafx.beans.property.adapter;
/*     */ 
/*     */ import com.sun.javafx.property.adapter.JavaBeanPropertyBuilderHelper;
/*     */ import com.sun.javafx.property.adapter.PropertyDescriptor;
/*     */ import java.lang.reflect.Method;
/*     */ 
/*     */ public final class JavaBeanStringPropertyBuilder
/*     */ {
/*  81 */   private JavaBeanPropertyBuilderHelper helper = new JavaBeanPropertyBuilderHelper();
/*     */ 
/*     */   public static JavaBeanStringPropertyBuilder create()
/*     */   {
/*  89 */     return new JavaBeanStringPropertyBuilder();
/*     */   }
/*     */ 
/*     */   public JavaBeanStringProperty build()
/*     */     throws NoSuchMethodException
/*     */   {
/* 102 */     PropertyDescriptor localPropertyDescriptor = this.helper.getDescriptor();
/* 103 */     if (!String.class.equals(localPropertyDescriptor.getType())) {
/* 104 */       throw new IllegalArgumentException("Not a String property");
/*     */     }
/* 106 */     return new JavaBeanStringProperty(localPropertyDescriptor, this.helper.getBean());
/*     */   }
/*     */ 
/*     */   public JavaBeanStringPropertyBuilder name(String paramString)
/*     */   {
/* 116 */     this.helper.name(paramString);
/* 117 */     return this;
/*     */   }
/*     */ 
/*     */   public JavaBeanStringPropertyBuilder bean(Object paramObject)
/*     */   {
/* 127 */     this.helper.bean(paramObject);
/* 128 */     return this;
/*     */   }
/*     */ 
/*     */   public JavaBeanStringPropertyBuilder beanClass(Class<?> paramClass)
/*     */   {
/* 140 */     this.helper.beanClass(paramClass);
/* 141 */     return this;
/*     */   }
/*     */ 
/*     */   public JavaBeanStringPropertyBuilder getter(String paramString)
/*     */   {
/* 152 */     this.helper.getterName(paramString);
/* 153 */     return this;
/*     */   }
/*     */ 
/*     */   public JavaBeanStringPropertyBuilder setter(String paramString)
/*     */   {
/* 164 */     this.helper.setterName(paramString);
/* 165 */     return this;
/*     */   }
/*     */ 
/*     */   public JavaBeanStringPropertyBuilder getter(Method paramMethod)
/*     */   {
/* 176 */     this.helper.getter(paramMethod);
/* 177 */     return this;
/*     */   }
/*     */ 
/*     */   public JavaBeanStringPropertyBuilder setter(Method paramMethod)
/*     */   {
/* 188 */     this.helper.setter(paramMethod);
/* 189 */     return this;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.property.adapter.JavaBeanStringPropertyBuilder
 * JD-Core Version:    0.6.2
 */