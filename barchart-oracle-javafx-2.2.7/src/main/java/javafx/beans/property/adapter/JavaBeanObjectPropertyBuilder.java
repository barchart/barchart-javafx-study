/*     */ package javafx.beans.property.adapter;
/*     */ 
/*     */ import com.sun.javafx.property.adapter.JavaBeanPropertyBuilderHelper;
/*     */ import com.sun.javafx.property.adapter.PropertyDescriptor;
/*     */ import java.lang.reflect.Method;
/*     */ 
/*     */ public final class JavaBeanObjectPropertyBuilder<T>
/*     */ {
/*  83 */   private JavaBeanPropertyBuilderHelper helper = new JavaBeanPropertyBuilderHelper();
/*     */ 
/*     */   public static JavaBeanObjectPropertyBuilder create()
/*     */   {
/*  91 */     return new JavaBeanObjectPropertyBuilder();
/*     */   }
/*     */ 
/*     */   public JavaBeanObjectProperty<T> build()
/*     */     throws NoSuchMethodException
/*     */   {
/* 102 */     PropertyDescriptor localPropertyDescriptor = this.helper.getDescriptor();
/* 103 */     return new JavaBeanObjectProperty(localPropertyDescriptor, this.helper.getBean());
/*     */   }
/*     */ 
/*     */   public JavaBeanObjectPropertyBuilder name(String paramString)
/*     */   {
/* 113 */     this.helper.name(paramString);
/* 114 */     return this;
/*     */   }
/*     */ 
/*     */   public JavaBeanObjectPropertyBuilder bean(Object paramObject)
/*     */   {
/* 124 */     this.helper.bean(paramObject);
/* 125 */     return this;
/*     */   }
/*     */ 
/*     */   public JavaBeanObjectPropertyBuilder beanClass(Class<?> paramClass)
/*     */   {
/* 137 */     this.helper.beanClass(paramClass);
/* 138 */     return this;
/*     */   }
/*     */ 
/*     */   public JavaBeanObjectPropertyBuilder getter(String paramString)
/*     */   {
/* 149 */     this.helper.getterName(paramString);
/* 150 */     return this;
/*     */   }
/*     */ 
/*     */   public JavaBeanObjectPropertyBuilder setter(String paramString)
/*     */   {
/* 161 */     this.helper.setterName(paramString);
/* 162 */     return this;
/*     */   }
/*     */ 
/*     */   public JavaBeanObjectPropertyBuilder getter(Method paramMethod)
/*     */   {
/* 173 */     this.helper.getter(paramMethod);
/* 174 */     return this;
/*     */   }
/*     */ 
/*     */   public JavaBeanObjectPropertyBuilder setter(Method paramMethod)
/*     */   {
/* 185 */     this.helper.setter(paramMethod);
/* 186 */     return this;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.property.adapter.JavaBeanObjectPropertyBuilder
 * JD-Core Version:    0.6.2
 */