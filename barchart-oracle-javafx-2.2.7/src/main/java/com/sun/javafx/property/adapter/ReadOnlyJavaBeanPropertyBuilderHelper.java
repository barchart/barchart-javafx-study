/*     */ package com.sun.javafx.property.adapter;
/*     */ 
/*     */ import java.lang.reflect.Method;
/*     */ import sun.reflect.misc.ReflectUtil;
/*     */ 
/*     */ public class ReadOnlyJavaBeanPropertyBuilderHelper
/*     */ {
/*     */   private static final String IS_PREFIX = "is";
/*     */   private static final String GET_PREFIX = "get";
/*     */   private String propertyName;
/*     */   private Class<?> beanClass;
/*     */   private Object bean;
/*     */   private String getterName;
/*     */   private Method getter;
/*     */   private ReadOnlyPropertyDescriptor descriptor;
/*     */ 
/*     */   public void name(String paramString)
/*     */   {
/*  67 */     if (paramString == null ? this.propertyName != null : !paramString.equals(this.propertyName)) {
/*  68 */       this.propertyName = paramString;
/*  69 */       this.descriptor = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void beanClass(Class<?> paramClass) {
/*  74 */     if (paramClass == null ? this.beanClass != null : !paramClass.equals(this.beanClass)) {
/*  75 */       ReflectUtil.checkPackageAccess(paramClass);
/*  76 */       this.beanClass = paramClass;
/*  77 */       this.descriptor = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void bean(Object paramObject) {
/*  82 */     this.bean = paramObject;
/*  83 */     if (paramObject != null) {
/*  84 */       Class localClass = paramObject.getClass();
/*  85 */       if ((this.beanClass == null) || (!this.beanClass.isAssignableFrom(localClass))) {
/*  86 */         ReflectUtil.checkPackageAccess(localClass);
/*  87 */         this.beanClass = paramObject.getClass();
/*  88 */         this.descriptor = null;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public Object getBean() {
/*  94 */     return this.bean;
/*     */   }
/*     */ 
/*     */   public void getterName(String paramString) {
/*  98 */     if (paramString == null ? this.getterName != null : !paramString.equals(this.getterName)) {
/*  99 */       this.getterName = paramString;
/* 100 */       this.descriptor = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void getter(Method paramMethod) {
/* 105 */     if (paramMethod == null ? this.getter != null : !paramMethod.equals(this.getter)) {
/* 106 */       this.getter = paramMethod;
/* 107 */       this.descriptor = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public ReadOnlyPropertyDescriptor getDescriptor() throws NoSuchMethodException {
/* 112 */     if (this.descriptor == null) {
/* 113 */       if ((this.propertyName == null) || (this.bean == null)) {
/* 114 */         throw new NullPointerException("Bean and property name have to be specified");
/*     */       }
/* 116 */       if (this.propertyName.isEmpty()) {
/* 117 */         throw new IllegalArgumentException("Property name cannot be empty");
/*     */       }
/* 119 */       String str = ReadOnlyPropertyDescriptor.capitalizedName(this.propertyName);
/* 120 */       if (this.getter == null) {
/* 121 */         if ((this.getterName != null) && (!this.getterName.isEmpty()))
/* 122 */           this.getter = this.beanClass.getMethod(this.getterName, new Class[0]);
/*     */         else {
/*     */           try {
/* 125 */             this.getter = this.beanClass.getMethod("is" + str, new Class[0]);
/*     */           } catch (NoSuchMethodException localNoSuchMethodException) {
/* 127 */             this.getter = this.beanClass.getMethod("get" + str, new Class[0]);
/*     */           }
/*     */         }
/*     */       }
/* 131 */       this.descriptor = new ReadOnlyPropertyDescriptor(this.propertyName, this.beanClass, this.getter);
/*     */     }
/* 133 */     return this.descriptor;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.property.adapter.ReadOnlyJavaBeanPropertyBuilderHelper
 * JD-Core Version:    0.6.2
 */