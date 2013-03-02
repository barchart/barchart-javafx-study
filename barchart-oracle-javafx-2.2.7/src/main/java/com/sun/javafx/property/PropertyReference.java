/*     */ package com.sun.javafx.property;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import javafx.beans.property.ReadOnlyProperty;
/*     */ import sun.reflect.misc.MethodUtil;
/*     */ import sun.reflect.misc.ReflectUtil;
/*     */ 
/*     */ public final class PropertyReference<T>
/*     */ {
/*     */   private String name;
/*     */   private Method getter;
/*     */   private Method setter;
/*     */   private Method propertyGetter;
/*     */   private Class<?> clazz;
/*     */   private Class<?> type;
/*  49 */   private boolean reflected = false;
/*     */ 
/*     */   public PropertyReference(Class<?> paramClass, String paramString)
/*     */   {
/*  65 */     if (paramString == null)
/*  66 */       throw new NullPointerException("Name must be specified");
/*  67 */     if (paramString.trim().length() == 0)
/*  68 */       throw new IllegalArgumentException("Name must be specified");
/*  69 */     if (paramClass == null)
/*  70 */       throw new NullPointerException("Class must be specified");
/*  71 */     ReflectUtil.checkPackageAccess(paramClass);
/*  72 */     this.name = paramString;
/*  73 */     this.clazz = paramClass;
/*     */   }
/*     */ 
/*     */   public boolean isWritable()
/*     */   {
/*  82 */     reflect();
/*  83 */     return this.setter != null;
/*     */   }
/*     */ 
/*     */   public boolean isReadable()
/*     */   {
/*  92 */     reflect();
/*  93 */     return this.getter != null;
/*     */   }
/*     */ 
/*     */   public boolean hasProperty()
/*     */   {
/* 103 */     reflect();
/* 104 */     return this.propertyGetter != null;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 113 */     return this.name;
/*     */   }
/*     */ 
/*     */   public Class<?> getContainingClass()
/*     */   {
/* 122 */     return this.clazz;
/*     */   }
/*     */ 
/*     */   public Class<?> getType()
/*     */   {
/* 132 */     reflect();
/* 133 */     return this.type;
/*     */   }
/*     */ 
/*     */   public void set(Object paramObject, T paramT)
/*     */   {
/* 147 */     if (!isWritable()) {
/* 148 */       throw new IllegalStateException("Cannot write to readonly property " + this.name);
/*     */     }
/* 150 */     assert (this.setter != null);
/*     */     try {
/* 152 */       MethodUtil.invoke(this.setter, paramObject, new Object[] { paramT });
/*     */     } catch (Exception localException) {
/* 154 */       throw new RuntimeException(localException);
/*     */     }
/*     */   }
/*     */ 
/*     */   public T get(Object paramObject)
/*     */   {
/* 170 */     if (!isReadable()) {
/* 171 */       throw new IllegalStateException("Cannot read from unreadable property " + this.name);
/*     */     }
/* 173 */     assert (this.getter != null);
/*     */     try {
/* 175 */       return MethodUtil.invoke(this.getter, paramObject, (Object[])null);
/*     */     } catch (Exception localException) {
/* 177 */       throw new RuntimeException(localException);
/*     */     }
/*     */   }
/*     */ 
/*     */   public ReadOnlyProperty<T> getProperty(Object paramObject)
/*     */   {
/* 194 */     if (!hasProperty())
/* 195 */       throw new IllegalStateException("Cannot get property " + this.name);
/* 196 */     assert (this.propertyGetter != null);
/*     */     try {
/* 198 */       return (ReadOnlyProperty)MethodUtil.invoke(this.propertyGetter, paramObject, (Object[])null);
/*     */     } catch (Exception localException) {
/* 200 */       throw new RuntimeException(localException);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 209 */     return this.name;
/*     */   }
/*     */ 
/*     */   private void reflect()
/*     */   {
/* 215 */     if (!this.reflected) {
/* 216 */       this.reflected = true;
/*     */       try
/*     */       {
/* 220 */         String str1 = Character.toUpperCase(this.name.charAt(0)) + this.name.substring(1);
/*     */ 
/* 231 */         this.type = null;
/*     */ 
/* 233 */         String str2 = "get" + str1;
/*     */         try {
/* 235 */           Method localMethod1 = this.clazz.getMethod(str2, new Class[0]);
/* 236 */           if (Modifier.isPublic(localMethod1.getModifiers())) {
/* 237 */             this.getter = localMethod1;
/*     */           }
/*     */         }
/*     */         catch (NoSuchMethodException localNoSuchMethodException1)
/*     */         {
/*     */         }
/*     */ 
/* 244 */         if (this.getter == null) {
/* 245 */           str2 = "is" + str1;
/*     */           try {
/* 247 */             Method localMethod2 = this.clazz.getMethod(str2, new Class[0]);
/* 248 */             if (Modifier.isPublic(localMethod2.getModifiers())) {
/* 249 */               this.getter = localMethod2;
/*     */             }
/*     */ 
/*     */           }
/*     */           catch (NoSuchMethodException localNoSuchMethodException2)
/*     */           {
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 259 */         String str3 = "set" + str1;
/*     */ 
/* 263 */         if (this.getter != null) {
/* 264 */           this.type = this.getter.getReturnType();
/*     */           try {
/* 266 */             Method localMethod3 = this.clazz.getMethod(str3, new Class[] { this.type });
/* 267 */             if (Modifier.isPublic(localMethod3.getModifiers()))
/* 268 */               this.setter = localMethod3;
/*     */           }
/*     */           catch (NoSuchMethodException localNoSuchMethodException3) {
/*     */           }
/*     */         }
/*     */         else {
/* 274 */           localObject1 = this.clazz.getMethods();
/* 275 */           for (Object localObject3 : localObject1) {
/* 276 */             Class[] arrayOfClass = localObject3.getParameterTypes();
/* 277 */             if ((str3.equals(localObject3.getName())) && (arrayOfClass.length == 1) && (Modifier.isPublic(localObject3.getModifiers())))
/*     */             {
/* 281 */               this.setter = localObject3;
/* 282 */               this.type = arrayOfClass[0];
/* 283 */               break;
/*     */             }
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 289 */         Object localObject1 = this.name + "Property";
/*     */         try {
/* 291 */           ??? = this.clazz.getMethod((String)localObject1, new Class[0]);
/* 292 */           if (Modifier.isPublic(((Method)???).getModifiers()))
/* 293 */             this.propertyGetter = ((Method)???);
/*     */           else
/* 295 */             this.propertyGetter = null;
/*     */         } catch (NoSuchMethodException localNoSuchMethodException4) {
/*     */         }
/*     */       }
/*     */       catch (RuntimeException localRuntimeException) {
/* 300 */         System.err.println("Failed to introspect property " + this.name);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 310 */     if (this == paramObject) {
/* 311 */       return true;
/*     */     }
/* 313 */     if (!(paramObject instanceof PropertyReference)) {
/* 314 */       return false;
/*     */     }
/* 316 */     PropertyReference localPropertyReference = (PropertyReference)paramObject;
/* 317 */     if ((this.name != localPropertyReference.name) && ((this.name == null) || (!this.name.equals(localPropertyReference.name))))
/*     */     {
/* 319 */       return false;
/*     */     }
/* 321 */     if ((this.clazz != localPropertyReference.clazz) && ((this.clazz == null) || (!this.clazz.equals(localPropertyReference.clazz))))
/*     */     {
/* 323 */       return false;
/*     */     }
/* 325 */     return true;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 333 */     int i = 5;
/* 334 */     i = 97 * i + (this.name != null ? this.name.hashCode() : 0);
/* 335 */     i = 97 * i + (this.clazz != null ? this.clazz.hashCode() : 0);
/* 336 */     return i;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.property.PropertyReference
 * JD-Core Version:    0.6.2
 */