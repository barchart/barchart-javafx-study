/*     */ package javafx.beans.property.adapter;
/*     */ 
/*     */ import com.sun.javafx.property.adapter.JavaBeanPropertyBuilderHelper;
/*     */ import com.sun.javafx.property.adapter.PropertyDescriptor;
/*     */ import java.lang.reflect.Method;
/*     */ 
/*     */ public final class JavaBeanLongPropertyBuilder
/*     */ {
/*  81 */   private JavaBeanPropertyBuilderHelper helper = new JavaBeanPropertyBuilderHelper();
/*     */ 
/*     */   public static JavaBeanLongPropertyBuilder create()
/*     */   {
/*  89 */     return new JavaBeanLongPropertyBuilder();
/*     */   }
/*     */ 
/*     */   public JavaBeanLongProperty build()
/*     */     throws NoSuchMethodException
/*     */   {
/* 102 */     PropertyDescriptor localPropertyDescriptor = this.helper.getDescriptor();
/* 103 */     if ((!Long.TYPE.equals(localPropertyDescriptor.getType())) && (!Number.class.isAssignableFrom(localPropertyDescriptor.getType()))) {
/* 104 */       throw new IllegalArgumentException("Not a long property");
/*     */     }
/* 106 */     return new JavaBeanLongProperty(localPropertyDescriptor, this.helper.getBean());
/*     */   }
/*     */ 
/*     */   public JavaBeanLongPropertyBuilder name(String paramString)
/*     */   {
/* 116 */     this.helper.name(paramString);
/* 117 */     return this;
/*     */   }
/*     */ 
/*     */   public JavaBeanLongPropertyBuilder bean(Object paramObject)
/*     */   {
/* 127 */     this.helper.bean(paramObject);
/* 128 */     return this;
/*     */   }
/*     */ 
/*     */   public JavaBeanLongPropertyBuilder beanClass(Class<?> paramClass)
/*     */   {
/* 140 */     this.helper.beanClass(paramClass);
/* 141 */     return this;
/*     */   }
/*     */ 
/*     */   public JavaBeanLongPropertyBuilder getter(String paramString)
/*     */   {
/* 152 */     this.helper.getterName(paramString);
/* 153 */     return this;
/*     */   }
/*     */ 
/*     */   public JavaBeanLongPropertyBuilder setter(String paramString)
/*     */   {
/* 164 */     this.helper.setterName(paramString);
/* 165 */     return this;
/*     */   }
/*     */ 
/*     */   public JavaBeanLongPropertyBuilder getter(Method paramMethod)
/*     */   {
/* 176 */     this.helper.getter(paramMethod);
/* 177 */     return this;
/*     */   }
/*     */ 
/*     */   public JavaBeanLongPropertyBuilder setter(Method paramMethod)
/*     */   {
/* 188 */     this.helper.setter(paramMethod);
/* 189 */     return this;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.property.adapter.JavaBeanLongPropertyBuilder
 * JD-Core Version:    0.6.2
 */