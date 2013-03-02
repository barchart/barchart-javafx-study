/*     */ package javafx.beans.property.adapter;
/*     */ 
/*     */ import com.sun.javafx.property.adapter.ReadOnlyJavaBeanPropertyBuilderHelper;
/*     */ import com.sun.javafx.property.adapter.ReadOnlyPropertyDescriptor;
/*     */ import java.lang.reflect.Method;
/*     */ 
/*     */ public final class ReadOnlyJavaBeanBooleanPropertyBuilder
/*     */ {
/*  80 */   private final ReadOnlyJavaBeanPropertyBuilderHelper helper = new ReadOnlyJavaBeanPropertyBuilderHelper();
/*     */ 
/*     */   public static ReadOnlyJavaBeanBooleanPropertyBuilder create()
/*     */   {
/*  88 */     return new ReadOnlyJavaBeanBooleanPropertyBuilder();
/*     */   }
/*     */ 
/*     */   public ReadOnlyJavaBeanBooleanProperty build()
/*     */     throws NoSuchMethodException
/*     */   {
/* 101 */     ReadOnlyPropertyDescriptor localReadOnlyPropertyDescriptor = this.helper.getDescriptor();
/* 102 */     if ((!Boolean.TYPE.equals(localReadOnlyPropertyDescriptor.getType())) && (!Boolean.class.equals(localReadOnlyPropertyDescriptor.getType()))) {
/* 103 */       throw new IllegalArgumentException("Not a boolean property");
/*     */     }
/* 105 */     return new ReadOnlyJavaBeanBooleanProperty(localReadOnlyPropertyDescriptor, this.helper.getBean());
/*     */   }
/*     */ 
/*     */   public ReadOnlyJavaBeanBooleanPropertyBuilder name(String paramString)
/*     */   {
/* 115 */     this.helper.name(paramString);
/* 116 */     return this;
/*     */   }
/*     */ 
/*     */   public ReadOnlyJavaBeanBooleanPropertyBuilder bean(Object paramObject)
/*     */   {
/* 126 */     this.helper.bean(paramObject);
/* 127 */     return this;
/*     */   }
/*     */ 
/*     */   public ReadOnlyJavaBeanBooleanPropertyBuilder beanClass(Class<?> paramClass)
/*     */   {
/* 139 */     this.helper.beanClass(paramClass);
/* 140 */     return this;
/*     */   }
/*     */ 
/*     */   public ReadOnlyJavaBeanBooleanPropertyBuilder getter(String paramString)
/*     */   {
/* 151 */     this.helper.getterName(paramString);
/* 152 */     return this;
/*     */   }
/*     */ 
/*     */   public ReadOnlyJavaBeanBooleanPropertyBuilder getter(Method paramMethod)
/*     */   {
/* 163 */     this.helper.getter(paramMethod);
/* 164 */     return this;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.property.adapter.ReadOnlyJavaBeanBooleanPropertyBuilder
 * JD-Core Version:    0.6.2
 */