/*     */ package com.sun.javafx.property.adapter;
/*     */ 
/*     */ import java.beans.PropertyChangeEvent;
/*     */ import java.beans.PropertyVetoException;
/*     */ import java.beans.VetoableChangeListener;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import javafx.beans.property.Property;
/*     */ import javafx.beans.property.adapter.ReadOnlyJavaBeanProperty;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ import sun.reflect.misc.MethodUtil;
/*     */ 
/*     */ public class PropertyDescriptor extends ReadOnlyPropertyDescriptor
/*     */ {
/*     */   private static final String ADD_VETOABLE_LISTENER_METHOD_NAME = "addVetoableChangeListener";
/*     */   private static final String REMOVE_VETOABLE_LISTENER_METHOD_NAME = "removeVetoableChangeListener";
/*     */   private static final String ADD_PREFIX = "add";
/*     */   private static final String REMOVE_PREFIX = "remove";
/*     */   private static final String SUFFIX = "Listener";
/*     */   private static final int ADD_VETOABLE_LISTENER_TAKES_NAME = 1;
/*     */   private static final int REMOVE_VETOABLE_LISTENER_TAKES_NAME = 2;
/*     */   private final Method setter;
/*     */   private final Method addVetoListener;
/*     */   private final Method removeVetoListener;
/*     */   private final int flags;
/*     */ 
/*     */   public Method getSetter()
/*     */   {
/*  79 */     return this.setter;
/*     */   }
/*     */   public PropertyDescriptor(String paramString, Class<?> paramClass, Method paramMethod1, Method paramMethod2) {
/*  82 */     super(paramString, paramClass, paramMethod1);
/*  83 */     this.setter = paramMethod2;
/*     */ 
/*  85 */     Method localMethod1 = null;
/*  86 */     Method localMethod2 = null;
/*  87 */     int i = 0;
/*     */ 
/*  90 */     String str1 = "add" + capitalizedName(this.name) + "Listener";
/*     */     try {
/*  92 */       localMethod1 = paramClass.getMethod(str1, new Class[] { VetoableChangeListener.class });
/*     */     } catch (NoSuchMethodException localNoSuchMethodException1) {
/*     */       try {
/*  95 */         localMethod1 = paramClass.getMethod("addVetoableChangeListener", new Class[] { String.class, VetoableChangeListener.class });
/*  96 */         i |= 1;
/*     */       } catch (NoSuchMethodException localNoSuchMethodException2) {
/*     */         try {
/*  99 */           localMethod1 = paramClass.getMethod("addVetoableChangeListener", new Class[] { VetoableChangeListener.class });
/*     */         }
/*     */         catch (NoSuchMethodException localNoSuchMethodException4)
/*     */         {
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 107 */     String str2 = "remove" + capitalizedName(this.name) + "Listener";
/*     */     try {
/* 109 */       localMethod2 = paramClass.getMethod(str2, new Class[] { VetoableChangeListener.class });
/*     */     } catch (NoSuchMethodException localNoSuchMethodException3) {
/*     */       try {
/* 112 */         localMethod2 = paramClass.getMethod("removeVetoableChangeListener", new Class[] { String.class, VetoableChangeListener.class });
/* 113 */         i |= 2;
/*     */       } catch (NoSuchMethodException localNoSuchMethodException5) {
/*     */         try {
/* 116 */           localMethod2 = paramClass.getMethod("removeVetoableChangeListener", new Class[] { VetoableChangeListener.class });
/*     */         }
/*     */         catch (NoSuchMethodException localNoSuchMethodException6)
/*     */         {
/*     */         }
/*     */       }
/*     */     }
/* 123 */     this.addVetoListener = localMethod1;
/* 124 */     this.removeVetoListener = localMethod2;
/* 125 */     this.flags = i;
/*     */   }
/*     */ 
/*     */   public void addListener(ReadOnlyPropertyDescriptor.ReadOnlyListener paramReadOnlyListener)
/*     */   {
/* 130 */     super.addListener(paramReadOnlyListener);
/* 131 */     if (this.addVetoListener != null)
/*     */       try {
/* 133 */         if ((this.flags & 0x1) > 0)
/* 134 */           this.addVetoListener.invoke(paramReadOnlyListener.getBean(), new Object[] { this.name, paramReadOnlyListener });
/*     */         else
/* 136 */           this.addVetoListener.invoke(paramReadOnlyListener.getBean(), new Object[] { paramReadOnlyListener });
/*     */       }
/*     */       catch (IllegalAccessException localIllegalAccessException)
/*     */       {
/*     */       }
/*     */       catch (InvocationTargetException localInvocationTargetException)
/*     */       {
/*     */       }
/*     */   }
/*     */ 
/*     */   public void removeListener(ReadOnlyPropertyDescriptor.ReadOnlyListener paramReadOnlyListener)
/*     */   {
/* 150 */     super.removeListener(paramReadOnlyListener);
/* 151 */     if (this.removeVetoListener != null)
/*     */       try {
/* 153 */         if ((this.flags & 0x2) > 0)
/* 154 */           this.removeVetoListener.invoke(paramReadOnlyListener.getBean(), new Object[] { this.name, paramReadOnlyListener });
/*     */         else
/* 156 */           this.removeVetoListener.invoke(paramReadOnlyListener.getBean(), new Object[] { paramReadOnlyListener });
/*     */       }
/*     */       catch (IllegalAccessException localIllegalAccessException)
/*     */       {
/*     */       }
/*     */       catch (InvocationTargetException localInvocationTargetException)
/*     */       {
/*     */       }
/*     */   }
/*     */ 
/*     */   public class Listener<T> extends ReadOnlyPropertyDescriptor.ReadOnlyListener<T> implements ChangeListener<T>, VetoableChangeListener
/*     */   {
/*     */     private boolean updating;
/*     */ 
/*     */     public Listener(ReadOnlyJavaBeanProperty<T> arg2) {
/* 171 */       super(localObject, localReadOnlyJavaBeanProperty);
/*     */     }
/*     */ 
/*     */     public void changed(ObservableValue<? extends T> paramObservableValue, T paramT1, T paramT2)
/*     */     {
/* 176 */       ReadOnlyJavaBeanProperty localReadOnlyJavaBeanProperty = checkRef();
/* 177 */       if (localReadOnlyJavaBeanProperty == null) {
/* 178 */         paramObservableValue.removeListener(this);
/* 179 */       } else if (!this.updating) {
/* 180 */         this.updating = true;
/*     */         try {
/* 182 */           MethodUtil.invoke(PropertyDescriptor.this.setter, this.bean, new Object[] { paramT2 });
/* 183 */           localReadOnlyJavaBeanProperty.fireValueChangedEvent();
/*     */         } catch (Exception localException) {
/*     */         }
/*     */         finally {
/* 187 */           this.updating = false;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     public void vetoableChange(PropertyChangeEvent paramPropertyChangeEvent) throws PropertyVetoException
/*     */     {
/* 194 */       if ((this.bean.equals(paramPropertyChangeEvent.getSource())) && (PropertyDescriptor.this.name.equals(paramPropertyChangeEvent.getPropertyName()))) {
/* 195 */         ReadOnlyJavaBeanProperty localReadOnlyJavaBeanProperty = checkRef();
/* 196 */         if (((localReadOnlyJavaBeanProperty instanceof Property)) && (((Property)localReadOnlyJavaBeanProperty).isBound()) && (!this.updating))
/* 197 */           throw new PropertyVetoException("A bound value cannot be set.", paramPropertyChangeEvent);
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.property.adapter.PropertyDescriptor
 * JD-Core Version:    0.6.2
 */