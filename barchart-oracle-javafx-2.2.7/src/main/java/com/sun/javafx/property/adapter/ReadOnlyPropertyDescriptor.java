/*     */ package com.sun.javafx.property.adapter;
/*     */ 
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyChangeListener;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.Locale;
/*     */ import javafx.beans.WeakListener;
/*     */ import javafx.beans.property.adapter.ReadOnlyJavaBeanProperty;
/*     */ import sun.reflect.misc.ReflectUtil;
/*     */ 
/*     */ public class ReadOnlyPropertyDescriptor
/*     */ {
/*     */   private static final String ADD_LISTENER_METHOD_NAME = "addPropertyChangeListener";
/*     */   private static final String REMOVE_LISTENER_METHOD_NAME = "removePropertyChangeListener";
/*     */   private static final String ADD_PREFIX = "add";
/*     */   private static final String REMOVE_PREFIX = "remove";
/*     */   private static final String SUFFIX = "Listener";
/*     */   private static final int ADD_LISTENER_TAKES_NAME = 1;
/*     */   private static final int REMOVE_LISTENER_TAKES_NAME = 2;
/*     */   protected final String name;
/*     */   protected final Class<?> beanClass;
/*     */   private final Method getter;
/*     */   private final Class<?> type;
/*     */   private final Method addChangeListener;
/*     */   private final Method removeChangeListener;
/*     */   private final int flags;
/*     */ 
/*     */   public String getName()
/*     */   {
/*  83 */     return this.name; } 
/*  84 */   public Method getGetter() { return this.getter; } 
/*  85 */   public Class<?> getType() { return this.type; }
/*     */ 
/*     */   public ReadOnlyPropertyDescriptor(String paramString, Class<?> paramClass, Method paramMethod) {
/*  88 */     ReflectUtil.checkPackageAccess(paramClass);
/*     */ 
/*  90 */     this.name = paramString;
/*  91 */     this.beanClass = paramClass;
/*  92 */     this.getter = paramMethod;
/*  93 */     this.type = paramMethod.getReturnType();
/*     */ 
/*  95 */     Method localMethod1 = null;
/*  96 */     Method localMethod2 = null;
/*  97 */     int i = 0;
/*     */     try
/*     */     {
/* 100 */       String str1 = "add" + capitalizedName(this.name) + "Listener";
/* 101 */       localMethod1 = paramClass.getMethod(str1, new Class[] { PropertyChangeListener.class });
/*     */     } catch (NoSuchMethodException localNoSuchMethodException1) {
/*     */       try {
/* 104 */         localMethod1 = paramClass.getMethod("addPropertyChangeListener", new Class[] { String.class, PropertyChangeListener.class });
/* 105 */         i |= 1;
/*     */       } catch (NoSuchMethodException localNoSuchMethodException3) {
/*     */         try {
/* 108 */           localMethod1 = paramClass.getMethod("addPropertyChangeListener", new Class[] { PropertyChangeListener.class });
/*     */         }
/*     */         catch (NoSuchMethodException localNoSuchMethodException5)
/*     */         {
/*     */         }
/*     */       }
/*     */     }
/*     */     try {
/* 116 */       String str2 = "remove" + capitalizedName(this.name) + "Listener";
/* 117 */       localMethod2 = paramClass.getMethod(str2, new Class[] { PropertyChangeListener.class });
/*     */     } catch (NoSuchMethodException localNoSuchMethodException2) {
/*     */       try {
/* 120 */         localMethod2 = paramClass.getMethod("removePropertyChangeListener", new Class[] { String.class, PropertyChangeListener.class });
/* 121 */         i |= 2;
/*     */       } catch (NoSuchMethodException localNoSuchMethodException4) {
/*     */         try {
/* 124 */           localMethod2 = paramClass.getMethod("removePropertyChangeListener", new Class[] { PropertyChangeListener.class });
/*     */         }
/*     */         catch (NoSuchMethodException localNoSuchMethodException6)
/*     */         {
/*     */         }
/*     */       }
/*     */     }
/* 131 */     this.addChangeListener = localMethod1;
/* 132 */     this.removeChangeListener = localMethod2;
/* 133 */     this.flags = i;
/*     */   }
/*     */ 
/*     */   public static String capitalizedName(String paramString) {
/* 137 */     return paramString.substring(0, 1).toUpperCase(Locale.ENGLISH) + paramString.substring(1);
/*     */   }
/*     */ 
/*     */   public void addListener(ReadOnlyListener paramReadOnlyListener) {
/* 141 */     if (this.addChangeListener != null)
/*     */       try {
/* 143 */         if ((this.flags & 0x1) > 0)
/* 144 */           this.addChangeListener.invoke(paramReadOnlyListener.getBean(), new Object[] { this.name, paramReadOnlyListener });
/*     */         else
/* 146 */           this.addChangeListener.invoke(paramReadOnlyListener.getBean(), new Object[] { paramReadOnlyListener });
/*     */       }
/*     */       catch (IllegalAccessException localIllegalAccessException)
/*     */       {
/*     */       }
/*     */       catch (InvocationTargetException localInvocationTargetException)
/*     */       {
/*     */       }
/*     */   }
/*     */ 
/*     */   public void removeListener(ReadOnlyListener paramReadOnlyListener) {
/* 157 */     if (this.removeChangeListener != null)
/*     */       try {
/* 159 */         if ((this.flags & 0x2) > 0)
/* 160 */           this.removeChangeListener.invoke(paramReadOnlyListener.getBean(), new Object[] { this.name, paramReadOnlyListener });
/*     */         else
/* 162 */           this.removeChangeListener.invoke(paramReadOnlyListener.getBean(), new Object[] { paramReadOnlyListener });
/*     */       }
/*     */       catch (IllegalAccessException localIllegalAccessException)
/*     */       {
/*     */       }
/*     */       catch (InvocationTargetException localInvocationTargetException) {
/*     */       }
/*     */   }
/*     */ 
/*     */   public class ReadOnlyListener<T> implements PropertyChangeListener, WeakListener {
/*     */     protected final Object bean;
/*     */     private final WeakReference<ReadOnlyJavaBeanProperty<T>> propertyRef;
/*     */ 
/*     */     public Object getBean() {
/* 177 */       return this.bean;
/*     */     }
/*     */ 
/*     */     public ReadOnlyListener(ReadOnlyJavaBeanProperty<T> arg2)
/*     */     {
/*     */       Object localObject1;
/* 180 */       this.bean = localObject1;
/*     */       Object localObject2;
/* 181 */       this.propertyRef = new WeakReference(localObject2);
/*     */     }
/*     */ 
/*     */     protected ReadOnlyJavaBeanProperty<T> checkRef() {
/* 185 */       ReadOnlyJavaBeanProperty localReadOnlyJavaBeanProperty = (ReadOnlyJavaBeanProperty)this.propertyRef.get();
/* 186 */       if (localReadOnlyJavaBeanProperty == null) {
/* 187 */         ReadOnlyPropertyDescriptor.this.removeListener(this);
/*     */       }
/* 189 */       return localReadOnlyJavaBeanProperty;
/*     */     }
/*     */ 
/*     */     public void propertyChange(PropertyChangeEvent paramPropertyChangeEvent)
/*     */     {
/* 194 */       if ((this.bean.equals(paramPropertyChangeEvent.getSource())) && (ReadOnlyPropertyDescriptor.this.name.equals(paramPropertyChangeEvent.getPropertyName()))) {
/* 195 */         ReadOnlyJavaBeanProperty localReadOnlyJavaBeanProperty = checkRef();
/* 196 */         if (localReadOnlyJavaBeanProperty != null)
/* 197 */           localReadOnlyJavaBeanProperty.fireValueChangedEvent();
/*     */       }
/*     */     }
/*     */ 
/*     */     public boolean wasGarbageCollected()
/*     */     {
/* 204 */       return checkRef() == null;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.property.adapter.ReadOnlyPropertyDescriptor
 * JD-Core Version:    0.6.2
 */