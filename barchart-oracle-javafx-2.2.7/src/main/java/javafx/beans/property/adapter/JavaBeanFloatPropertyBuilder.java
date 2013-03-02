/*     */ package javafx.beans.property.adapter;
/*     */ 
/*     */ import com.sun.javafx.property.adapter.JavaBeanPropertyBuilderHelper;
/*     */ import com.sun.javafx.property.adapter.PropertyDescriptor;
/*     */ import java.lang.reflect.Method;
/*     */ 
/*     */ public final class JavaBeanFloatPropertyBuilder
/*     */ {
/*  81 */   private JavaBeanPropertyBuilderHelper helper = new JavaBeanPropertyBuilderHelper();
/*     */ 
/*     */   public static JavaBeanFloatPropertyBuilder create()
/*     */   {
/*  89 */     return new JavaBeanFloatPropertyBuilder();
/*     */   }
/*     */ 
/*     */   public JavaBeanFloatProperty build()
/*     */     throws NoSuchMethodException
/*     */   {
/* 102 */     PropertyDescriptor localPropertyDescriptor = this.helper.getDescriptor();
/* 103 */     if ((!Float.TYPE.equals(localPropertyDescriptor.getType())) && (!Number.class.isAssignableFrom(localPropertyDescriptor.getType()))) {
/* 104 */       throw new IllegalArgumentException("Not a float property");
/*     */     }
/* 106 */     return new JavaBeanFloatProperty(localPropertyDescriptor, this.helper.getBean());
/*     */   }
/*     */ 
/*     */   public JavaBeanFloatPropertyBuilder name(String paramString)
/*     */   {
/* 116 */     this.helper.name(paramString);
/* 117 */     return this;
/*     */   }
/*     */ 
/*     */   public JavaBeanFloatPropertyBuilder bean(Object paramObject)
/*     */   {
/* 127 */     this.helper.bean(paramObject);
/* 128 */     return this;
/*     */   }
/*     */ 
/*     */   public JavaBeanFloatPropertyBuilder beanClass(Class<?> paramClass)
/*     */   {
/* 140 */     this.helper.beanClass(paramClass);
/* 141 */     return this;
/*     */   }
/*     */ 
/*     */   public JavaBeanFloatPropertyBuilder getter(String paramString)
/*     */   {
/* 152 */     this.helper.getterName(paramString);
/* 153 */     return this;
/*     */   }
/*     */ 
/*     */   public JavaBeanFloatPropertyBuilder setter(String paramString)
/*     */   {
/* 164 */     this.helper.setterName(paramString);
/* 165 */     return this;
/*     */   }
/*     */ 
/*     */   public JavaBeanFloatPropertyBuilder getter(Method paramMethod)
/*     */   {
/* 176 */     this.helper.getter(paramMethod);
/* 177 */     return this;
/*     */   }
/*     */ 
/*     */   public JavaBeanFloatPropertyBuilder setter(Method paramMethod)
/*     */   {
/* 188 */     this.helper.setter(paramMethod);
/* 189 */     return this;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.property.adapter.JavaBeanFloatPropertyBuilder
 * JD-Core Version:    0.6.2
 */