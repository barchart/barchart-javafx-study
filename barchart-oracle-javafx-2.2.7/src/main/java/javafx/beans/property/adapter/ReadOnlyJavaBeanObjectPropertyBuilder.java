/*     */ package javafx.beans.property.adapter;
/*     */ 
/*     */ import com.sun.javafx.property.adapter.ReadOnlyJavaBeanPropertyBuilderHelper;
/*     */ import com.sun.javafx.property.adapter.ReadOnlyPropertyDescriptor;
/*     */ import java.lang.reflect.Method;
/*     */ 
/*     */ public final class ReadOnlyJavaBeanObjectPropertyBuilder<T>
/*     */ {
/*  82 */   private final ReadOnlyJavaBeanPropertyBuilderHelper helper = new ReadOnlyJavaBeanPropertyBuilderHelper();
/*     */ 
/*     */   public static <T> ReadOnlyJavaBeanObjectPropertyBuilder<T> create()
/*     */   {
/*  90 */     return new ReadOnlyJavaBeanObjectPropertyBuilder();
/*     */   }
/*     */ 
/*     */   public ReadOnlyJavaBeanObjectProperty<T> build()
/*     */     throws NoSuchMethodException
/*     */   {
/* 101 */     ReadOnlyPropertyDescriptor localReadOnlyPropertyDescriptor = this.helper.getDescriptor();
/* 102 */     return new ReadOnlyJavaBeanObjectProperty(localReadOnlyPropertyDescriptor, this.helper.getBean());
/*     */   }
/*     */ 
/*     */   public ReadOnlyJavaBeanObjectPropertyBuilder<T> name(String paramString)
/*     */   {
/* 112 */     this.helper.name(paramString);
/* 113 */     return this;
/*     */   }
/*     */ 
/*     */   public ReadOnlyJavaBeanObjectPropertyBuilder<T> bean(Object paramObject)
/*     */   {
/* 123 */     this.helper.bean(paramObject);
/* 124 */     return this;
/*     */   }
/*     */ 
/*     */   public ReadOnlyJavaBeanObjectPropertyBuilder<T> beanClass(Class<?> paramClass)
/*     */   {
/* 136 */     this.helper.beanClass(paramClass);
/* 137 */     return this;
/*     */   }
/*     */ 
/*     */   public ReadOnlyJavaBeanObjectPropertyBuilder<T> getter(String paramString)
/*     */   {
/* 148 */     this.helper.getterName(paramString);
/* 149 */     return this;
/*     */   }
/*     */ 
/*     */   public ReadOnlyJavaBeanObjectPropertyBuilder<T> getter(Method paramMethod)
/*     */   {
/* 160 */     this.helper.getter(paramMethod);
/* 161 */     return this;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.property.adapter.ReadOnlyJavaBeanObjectPropertyBuilder
 * JD-Core Version:    0.6.2
 */