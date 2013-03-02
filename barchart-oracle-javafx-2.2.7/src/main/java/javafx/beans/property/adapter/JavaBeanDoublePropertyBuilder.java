/*     */ package javafx.beans.property.adapter;
/*     */ 
/*     */ import com.sun.javafx.property.adapter.JavaBeanPropertyBuilderHelper;
/*     */ import com.sun.javafx.property.adapter.PropertyDescriptor;
/*     */ import java.lang.reflect.Method;
/*     */ 
/*     */ public final class JavaBeanDoublePropertyBuilder
/*     */ {
/*  81 */   private final JavaBeanPropertyBuilderHelper helper = new JavaBeanPropertyBuilderHelper();
/*     */ 
/*     */   public static JavaBeanDoublePropertyBuilder create()
/*     */   {
/*  89 */     return new JavaBeanDoublePropertyBuilder();
/*     */   }
/*     */ 
/*     */   public JavaBeanDoubleProperty build()
/*     */     throws NoSuchMethodException
/*     */   {
/* 102 */     PropertyDescriptor localPropertyDescriptor = this.helper.getDescriptor();
/* 103 */     if ((!Double.TYPE.equals(localPropertyDescriptor.getType())) && (!Number.class.isAssignableFrom(localPropertyDescriptor.getType()))) {
/* 104 */       throw new IllegalArgumentException("Not a double property");
/*     */     }
/* 106 */     return new JavaBeanDoubleProperty(localPropertyDescriptor, this.helper.getBean());
/*     */   }
/*     */ 
/*     */   public JavaBeanDoublePropertyBuilder name(String paramString)
/*     */   {
/* 116 */     this.helper.name(paramString);
/* 117 */     return this;
/*     */   }
/*     */ 
/*     */   public JavaBeanDoublePropertyBuilder bean(Object paramObject)
/*     */   {
/* 127 */     this.helper.bean(paramObject);
/* 128 */     return this;
/*     */   }
/*     */ 
/*     */   public JavaBeanDoublePropertyBuilder beanClass(Class<?> paramClass)
/*     */   {
/* 140 */     this.helper.beanClass(paramClass);
/* 141 */     return this;
/*     */   }
/*     */ 
/*     */   public JavaBeanDoublePropertyBuilder getter(String paramString)
/*     */   {
/* 152 */     this.helper.getterName(paramString);
/* 153 */     return this;
/*     */   }
/*     */ 
/*     */   public JavaBeanDoublePropertyBuilder setter(String paramString)
/*     */   {
/* 164 */     this.helper.setterName(paramString);
/* 165 */     return this;
/*     */   }
/*     */ 
/*     */   public JavaBeanDoublePropertyBuilder getter(Method paramMethod)
/*     */   {
/* 176 */     this.helper.getter(paramMethod);
/* 177 */     return this;
/*     */   }
/*     */ 
/*     */   public JavaBeanDoublePropertyBuilder setter(Method paramMethod)
/*     */   {
/* 188 */     this.helper.setter(paramMethod);
/* 189 */     return this;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.property.adapter.JavaBeanDoublePropertyBuilder
 * JD-Core Version:    0.6.2
 */