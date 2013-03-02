/*     */ package com.sun.javafx.property.adapter;
/*     */ 
/*     */ import java.lang.reflect.Method;
/*     */ import sun.reflect.misc.ReflectUtil;
/*     */ 
/*     */ public class JavaBeanPropertyBuilderHelper
/*     */ {
/*     */   private static final String IS_PREFIX = "is";
/*     */   private static final String GET_PREFIX = "get";
/*     */   private static final String SET_PREFIX = "set";
/*     */   private String propertyName;
/*     */   private Class<?> beanClass;
/*     */   private Object bean;
/*     */   private String getterName;
/*     */   private String setterName;
/*     */   private Method getter;
/*     */   private Method setter;
/*     */   private PropertyDescriptor descriptor;
/*     */ 
/*     */   public void name(String paramString)
/*     */   {
/*  72 */     if (paramString == null ? this.propertyName != null : !paramString.equals(this.propertyName)) {
/*  73 */       this.propertyName = paramString;
/*  74 */       this.descriptor = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void beanClass(Class<?> paramClass) {
/*  79 */     if (paramClass == null ? this.beanClass != null : !paramClass.equals(this.beanClass)) {
/*  80 */       ReflectUtil.checkPackageAccess(paramClass);
/*  81 */       this.beanClass = paramClass;
/*  82 */       this.descriptor = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void bean(Object paramObject) {
/*  87 */     this.bean = paramObject;
/*  88 */     if (paramObject != null) {
/*  89 */       Class localClass = paramObject.getClass();
/*  90 */       if ((this.beanClass == null) || (!this.beanClass.isAssignableFrom(localClass))) {
/*  91 */         ReflectUtil.checkPackageAccess(localClass);
/*  92 */         this.beanClass = localClass;
/*  93 */         this.descriptor = null;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public Object getBean() {
/*  99 */     return this.bean;
/*     */   }
/*     */ 
/*     */   public void getterName(String paramString) {
/* 103 */     if (paramString == null ? this.getterName != null : !paramString.equals(this.getterName)) {
/* 104 */       this.getterName = paramString;
/* 105 */       this.descriptor = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setterName(String paramString) {
/* 110 */     if (paramString == null ? this.setterName != null : !paramString.equals(this.setterName)) {
/* 111 */       this.setterName = paramString;
/* 112 */       this.descriptor = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void getter(Method paramMethod) {
/* 117 */     if (paramMethod == null ? this.getter != null : !paramMethod.equals(this.getter)) {
/* 118 */       this.getter = paramMethod;
/* 119 */       this.descriptor = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setter(Method paramMethod) {
/* 124 */     if (paramMethod == null ? this.setter != null : !paramMethod.equals(this.setter)) {
/* 125 */       this.setter = paramMethod;
/* 126 */       this.descriptor = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public PropertyDescriptor getDescriptor() throws NoSuchMethodException {
/* 131 */     if (this.descriptor == null) {
/* 132 */       if (this.propertyName == null) {
/* 133 */         throw new NullPointerException("Property name has to be specified");
/*     */       }
/* 135 */       if (this.propertyName.isEmpty()) {
/* 136 */         throw new IllegalArgumentException("Property name cannot be empty");
/*     */       }
/* 138 */       String str = ReadOnlyPropertyDescriptor.capitalizedName(this.propertyName);
/* 139 */       if (this.getter == null) {
/* 140 */         if ((this.getterName != null) && (!this.getterName.isEmpty()))
/* 141 */           this.getter = this.beanClass.getMethod(this.getterName, new Class[0]);
/*     */         else {
/*     */           try {
/* 144 */             this.getter = this.beanClass.getMethod("is" + str, new Class[0]);
/*     */           } catch (NoSuchMethodException localNoSuchMethodException) {
/* 146 */             this.getter = this.beanClass.getMethod("get" + str, new Class[0]);
/*     */           }
/*     */         }
/*     */       }
/* 150 */       if (this.setter == null) {
/* 151 */         Class localClass = this.getter.getReturnType();
/* 152 */         if ((this.setterName != null) && (!this.setterName.isEmpty()))
/* 153 */           this.setter = this.beanClass.getMethod(this.setterName, new Class[] { localClass });
/*     */         else {
/* 155 */           this.setter = this.beanClass.getMethod("set" + str, new Class[] { localClass });
/*     */         }
/*     */       }
/* 158 */       this.descriptor = new PropertyDescriptor(this.propertyName, this.beanClass, this.getter, this.setter);
/*     */     }
/* 160 */     return this.descriptor;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.property.adapter.JavaBeanPropertyBuilderHelper
 * JD-Core Version:    0.6.2
 */