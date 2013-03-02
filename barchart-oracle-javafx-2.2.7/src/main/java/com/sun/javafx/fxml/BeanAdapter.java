/*      */ package com.sun.javafx.fxml;
/*      */ 
/*      */ import com.sun.javafx.collections.MapListenerHelper;
/*      */ import java.lang.reflect.Field;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.lang.reflect.Method;
/*      */ import java.lang.reflect.ParameterizedType;
/*      */ import java.lang.reflect.Type;
/*      */ import java.lang.reflect.TypeVariable;
/*      */ import java.math.BigDecimal;
/*      */ import java.math.BigInteger;
/*      */ import java.util.AbstractMap;
/*      */ import java.util.AbstractSet;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Map.Entry;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Set;
/*      */ import javafx.beans.InvalidationListener;
/*      */ import javafx.beans.Observable;
/*      */ import javafx.beans.property.Property;
/*      */ import javafx.beans.value.ObservableValue;
/*      */ import javafx.collections.MapChangeListener;
/*      */ import javafx.collections.MapChangeListener.Change;
/*      */ import javafx.collections.ObservableMap;
/*      */ import sun.reflect.misc.FieldUtil;
/*      */ import sun.reflect.misc.MethodUtil;
/*      */ import sun.reflect.misc.ReflectUtil;
/*      */ 
/*      */ public class BeanAdapter extends AbstractMap<String, Object>
/*      */   implements ObservableMap<String, Object>
/*      */ {
/*      */   private Object bean;
/*  180 */   private HashMap<String, Method> getterMethods = new HashMap();
/*  181 */   private HashMap<String, Method> setterMethods = new HashMap();
/*  182 */   private HashMap<String, Method> propertyModelMethods = new HashMap();
/*  183 */   private HashMap<String, String> aliases = new HashMap();
/*      */ 
/*  185 */   private PropertyEntrySet entrySet = new PropertyEntrySet(null);
/*      */ 
/*  187 */   private LinkedList<PropertyInvalidationListener> propertyInvalidationListeners = null;
/*      */   private MapListenerHelper<String, Object> listenerHelper;
/*      */   public static final String GET_PREFIX = "get";
/*      */   public static final String IS_PREFIX = "is";
/*      */   public static final String SET_PREFIX = "set";
/*      */   public static final String PROPERTY_MODEL_SUFFIX = "Property";
/*      */   public static final String VALUE_OF_METHOD_NAME = "valueOf";
/*      */   public static final String CHANGE_LISTENERS_SUFFIX = "ChangeListeners";
/*      */ 
/*      */   public BeanAdapter()
/*      */   {
/*  203 */     this(null);
/*      */   }
/*      */ 
/*      */   public BeanAdapter(Object paramObject)
/*      */   {
/*  213 */     setBean(paramObject);
/*      */   }
/*      */ 
/*      */   public Object getBean()
/*      */   {
/*  223 */     return this.bean;
/*      */   }
/*      */ 
/*      */   public void setBean(Object paramObject)
/*      */   {
/*  233 */     if ((paramObject instanceof Map)) {
/*  234 */       throw new IllegalArgumentException("Invalid bean.");
/*      */     }
/*      */ 
/*  237 */     Object localObject = this.bean;
/*      */ 
/*  239 */     if (localObject != paramObject) {
/*  240 */       if (MapListenerHelper.hasListeners(this.listenerHelper)) {
/*  241 */         unregisterPropertyChangeHandlers();
/*      */       }
/*      */ 
/*  244 */       this.bean = paramObject;
/*      */ 
/*  246 */       Class localClass1 = localObject == null ? null : localObject.getClass();
/*  247 */       Class localClass2 = paramObject == null ? null : paramObject.getClass();
/*      */ 
/*  249 */       if (localClass1 != localClass2) {
/*  250 */         updateMethodCache();
/*      */       }
/*      */ 
/*  253 */       if (MapListenerHelper.hasListeners(this.listenerHelper))
/*  254 */         registerPropertyChangeHandlers();
/*      */     }
/*      */   }
/*      */ 
/*      */   private void updateMethodCache()
/*      */   {
/*  260 */     this.getterMethods.clear();
/*  261 */     this.setterMethods.clear();
/*      */ 
/*  263 */     if (this.bean != null) {
/*  264 */       Class localClass = this.bean.getClass();
/*  265 */       Method[] arrayOfMethod = MethodUtil.getMethods(localClass);
/*      */       Method localMethod;
/*      */       int j;
/*      */       String str;
/*      */       Object localObject1;
/*      */       Object localObject2;
/*      */       Object localObject3;
/*  268 */       for (int i = 0; i < arrayOfMethod.length; i++) {
/*  269 */         localMethod = arrayOfMethod[i];
/*  270 */         j = localMethod.getModifiers();
/*      */ 
/*  272 */         if ((j & 0x8) == 0) {
/*  273 */           str = localMethod.getName();
/*      */ 
/*  276 */           if (str.startsWith("get"))
/*  277 */             localObject1 = "get";
/*  278 */           else if (str.startsWith("is"))
/*  279 */             localObject1 = "is";
/*      */           else {
/*  281 */             localObject1 = null;
/*      */           }
/*      */ 
/*  284 */           if (localObject1 != null) {
/*  285 */             localObject2 = localMethod.getParameterTypes();
/*  286 */             if (localObject2.length == 0) {
/*  287 */               localObject3 = getKey(str, (String)localObject1);
/*      */ 
/*  289 */               PropertyName localPropertyName = (PropertyName)localMethod.getAnnotation(PropertyName.class);
/*  290 */               if (localPropertyName != null) {
/*  291 */                 this.aliases.put(localObject3, localPropertyName.value());
/*  292 */                 localObject3 = (String)this.aliases.get(localObject3);
/*      */               }
/*      */ 
/*  295 */               this.getterMethods.put(localObject3, localMethod);
/*      */             }
/*      */           }
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  302 */       for (i = 0; i < arrayOfMethod.length; i++) {
/*  303 */         localMethod = arrayOfMethod[i];
/*  304 */         j = localMethod.getModifiers();
/*      */ 
/*  306 */         if ((j & 0x8) == 0) {
/*  307 */           str = localMethod.getName();
/*      */ 
/*  309 */           if (str.startsWith("set")) {
/*  310 */             localObject1 = localMethod.getParameterTypes();
/*  311 */             if (localObject1.length == 1) {
/*  312 */               localObject2 = getKey(str, "set");
/*      */ 
/*  315 */               localObject3 = (Method)this.getterMethods.get(localObject2);
/*      */ 
/*  317 */               if (localObject3 == null) {
/*  318 */                 localObject2 = (String)this.aliases.get(localObject2);
/*  319 */                 localObject3 = (Method)this.getterMethods.get(localObject2);
/*      */               }
/*      */ 
/*  322 */               if ((localObject3 != null) && (localObject1[0] == ((Method)localObject3).getReturnType()))
/*      */               {
/*  324 */                 this.setterMethods.put(localObject2, localMethod);
/*      */               }
/*      */             }
/*  327 */           } else if (str.endsWith("Property")) {
/*  328 */             localObject1 = localMethod.getParameterTypes();
/*  329 */             if (localObject1.length == 0) {
/*  330 */               localObject2 = str.substring(0, str.length() - "Property".length());
/*      */ 
/*  334 */               localObject3 = (Method)this.getterMethods.get(localObject2);
/*      */ 
/*  336 */               if (localObject3 == null) {
/*  337 */                 localObject2 = (String)this.aliases.get(localObject2);
/*  338 */                 localObject3 = (Method)this.getterMethods.get(localObject2);
/*      */               }
/*      */ 
/*  341 */               if (localObject3 != null)
/*  342 */                 this.propertyModelMethods.put(localObject2, localMethod);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private static String getKey(String paramString1, String paramString2)
/*      */   {
/*  352 */     return new StringBuilder().append(Character.toLowerCase(paramString1.charAt(paramString2.length()))).append(paramString1.substring(paramString2.length() + 1)).toString();
/*      */   }
/*      */ 
/*      */   public Object get(Object paramObject)
/*      */   {
/*  368 */     if (paramObject == null) {
/*  369 */       throw new NullPointerException();
/*      */     }
/*      */ 
/*  372 */     Object localObject = null;
/*      */ 
/*  374 */     if (this.bean != null) {
/*  375 */       Method localMethod = (Method)this.getterMethods.get(paramObject.toString());
/*      */ 
/*  377 */       if (localMethod != null) {
/*      */         try {
/*  379 */           localObject = MethodUtil.invoke(localMethod, this.bean, (Object[])null);
/*      */         } catch (IllegalAccessException localIllegalAccessException) {
/*  381 */           throw new RuntimeException(localIllegalAccessException);
/*      */         } catch (InvocationTargetException localInvocationTargetException) {
/*  383 */           throw new RuntimeException(localInvocationTargetException);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  388 */     return localObject;
/*      */   }
/*      */ 
/*      */   public Object put(String paramString, Object paramObject)
/*      */   {
/*  412 */     if (paramString == null) {
/*  413 */       throw new NullPointerException();
/*      */     }
/*      */ 
/*  416 */     if (this.bean == null) {
/*  417 */       throw new IllegalStateException();
/*      */     }
/*      */ 
/*  420 */     Method localMethod = (Method)this.setterMethods.get(paramString);
/*      */ 
/*  422 */     if (localMethod == null) {
/*  423 */       throw new PropertyNotFoundException(new StringBuilder().append("Property \"").append(paramString).append("\" does not exist").append(" or is read-only.").toString());
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  428 */       MethodUtil.invoke(localMethod, this.bean, new Object[] { coerce(paramObject, getType(paramString)) });
/*      */     } catch (IllegalAccessException localIllegalAccessException) {
/*  430 */       throw new RuntimeException(localIllegalAccessException);
/*      */     } catch (InvocationTargetException localInvocationTargetException) {
/*  432 */       throw new RuntimeException(localInvocationTargetException);
/*      */     }
/*      */ 
/*  435 */     return null;
/*      */   }
/*      */ 
/*      */   public boolean containsKey(Object paramObject)
/*      */   {
/*  449 */     return this.getterMethods.containsKey(paramObject);
/*      */   }
/*      */ 
/*      */   public Set<Map.Entry<String, Object>> entrySet()
/*      */   {
/*  455 */     return this.bean == null ? Collections.EMPTY_SET : this.entrySet;
/*      */   }
/*      */ 
/*      */   public boolean isReadOnly(String paramString)
/*      */   {
/*  468 */     return !this.setterMethods.containsKey(paramString);
/*      */   }
/*      */ 
/*      */   public Class<?> getType(String paramString)
/*      */   {
/*  478 */     Method localMethod = (Method)this.getterMethods.get(paramString);
/*      */ 
/*  480 */     return localMethod == null ? null : localMethod.getReturnType();
/*      */   }
/*      */ 
/*      */   public Type getGenericType(String paramString)
/*      */   {
/*  490 */     Method localMethod = (Method)this.getterMethods.get(paramString);
/*      */ 
/*  492 */     return localMethod == null ? null : localMethod.getGenericReturnType();
/*      */   }
/*      */ 
/*      */   public Property<Object> getPropertyModel(String paramString)
/*      */   {
/*  497 */     Method localMethod = (Method)this.propertyModelMethods.get(paramString);
/*      */     Property localProperty;
/*  500 */     if (localMethod != null)
/*      */       try {
/*  502 */         localProperty = (Property)MethodUtil.invoke(localMethod, this.bean, new Object[0]);
/*      */       } catch (IllegalAccessException localIllegalAccessException) {
/*  504 */         throw new RuntimeException(localIllegalAccessException);
/*      */       } catch (InvocationTargetException localInvocationTargetException) {
/*  506 */         throw new RuntimeException(localInvocationTargetException);
/*      */       }
/*      */     else {
/*  509 */       localProperty = null;
/*      */     }
/*      */ 
/*  512 */     return localProperty;
/*      */   }
/*      */ 
/*      */   public void addListener(InvalidationListener paramInvalidationListener)
/*      */   {
/*  517 */     if (!MapListenerHelper.hasListeners(this.listenerHelper)) {
/*  518 */       registerPropertyChangeHandlers();
/*      */     }
/*      */ 
/*  521 */     this.listenerHelper = MapListenerHelper.addListener(this.listenerHelper, paramInvalidationListener);
/*      */   }
/*      */ 
/*      */   public void addListener(MapChangeListener<? super String, ? super Object> paramMapChangeListener)
/*      */   {
/*  526 */     if (!MapListenerHelper.hasListeners(this.listenerHelper)) {
/*  527 */       registerPropertyChangeHandlers();
/*      */     }
/*      */ 
/*  530 */     this.listenerHelper = MapListenerHelper.addListener(this.listenerHelper, paramMapChangeListener);
/*      */   }
/*      */ 
/*      */   public void removeListener(InvalidationListener paramInvalidationListener)
/*      */   {
/*  535 */     this.listenerHelper = MapListenerHelper.removeListener(this.listenerHelper, paramInvalidationListener);
/*      */ 
/*  537 */     if (!MapListenerHelper.hasListeners(this.listenerHelper))
/*  538 */       unregisterPropertyChangeHandlers();
/*      */   }
/*      */ 
/*      */   public void removeListener(MapChangeListener<? super String, ? super Object> paramMapChangeListener)
/*      */   {
/*  544 */     this.listenerHelper = MapListenerHelper.removeListener(this.listenerHelper, paramMapChangeListener);
/*      */ 
/*  546 */     if (!MapListenerHelper.hasListeners(this.listenerHelper))
/*  547 */       unregisterPropertyChangeHandlers();
/*      */   }
/*      */ 
/*      */   private void registerPropertyChangeHandlers()
/*      */   {
/*  554 */     this.propertyInvalidationListeners = new LinkedList();
/*      */ 
/*  556 */     for (String str : keySet())
/*      */     {
/*  558 */       Property localProperty = getPropertyModel(str);
/*      */ 
/*  560 */       if (localProperty != null) {
/*  561 */         PropertyInvalidationListener localPropertyInvalidationListener = new PropertyInvalidationListener(str);
/*      */ 
/*  563 */         localProperty.addListener(localPropertyInvalidationListener);
/*  564 */         this.propertyInvalidationListeners.add(localPropertyInvalidationListener);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void unregisterPropertyChangeHandlers()
/*      */   {
/*  571 */     for (PropertyInvalidationListener localPropertyInvalidationListener : this.propertyInvalidationListeners)
/*      */     {
/*  573 */       Property localProperty = getPropertyModel(localPropertyInvalidationListener.key);
/*  574 */       localProperty.removeListener(localPropertyInvalidationListener);
/*      */     }
/*      */ 
/*  578 */     this.propertyInvalidationListeners = null;
/*      */   }
/*      */ 
/*      */   public boolean equals(Object paramObject)
/*      */   {
/*  583 */     boolean bool = false;
/*      */ 
/*  585 */     if ((paramObject instanceof BeanAdapter)) {
/*  586 */       BeanAdapter localBeanAdapter = (BeanAdapter)paramObject;
/*  587 */       bool = this.bean == localBeanAdapter.bean;
/*      */     }
/*      */ 
/*  590 */     return bool;
/*      */   }
/*      */ 
/*      */   public int hashCode()
/*      */   {
/*  595 */     return this.bean == null ? -1 : this.bean.hashCode();
/*      */   }
/*      */ 
/*      */   public static <T> T coerce(Object paramObject, Class<? extends T> paramClass)
/*      */   {
/*  609 */     if (paramClass == null) {
/*  610 */       throw new NullPointerException();
/*      */     }
/*      */ 
/*  613 */     Object localObject = null;
/*      */ 
/*  615 */     if (paramObject == null)
/*      */     {
/*  617 */       localObject = null;
/*  618 */     } else if (paramClass.isAssignableFrom(paramObject.getClass()))
/*      */     {
/*  620 */       localObject = paramObject;
/*  621 */     } else if ((paramClass == Boolean.class) || (paramClass == Boolean.TYPE))
/*      */     {
/*  623 */       localObject = Boolean.valueOf(paramObject.toString());
/*  624 */     } else if ((paramClass == Character.class) || (paramClass == Character.TYPE))
/*      */     {
/*  626 */       localObject = Character.valueOf(paramObject.toString().charAt(0));
/*  627 */     } else if ((paramClass == Byte.class) || (paramClass == Byte.TYPE))
/*      */     {
/*  629 */       if ((paramObject instanceof Number))
/*  630 */         localObject = Byte.valueOf(((Number)paramObject).byteValue());
/*      */       else
/*  632 */         localObject = Byte.valueOf(paramObject.toString());
/*      */     }
/*  634 */     else if ((paramClass == Short.class) || (paramClass == Short.TYPE))
/*      */     {
/*  636 */       if ((paramObject instanceof Number))
/*  637 */         localObject = Short.valueOf(((Number)paramObject).shortValue());
/*      */       else
/*  639 */         localObject = Short.valueOf(paramObject.toString());
/*      */     }
/*  641 */     else if ((paramClass == Integer.class) || (paramClass == Integer.TYPE))
/*      */     {
/*  643 */       if ((paramObject instanceof Number))
/*  644 */         localObject = Integer.valueOf(((Number)paramObject).intValue());
/*      */       else
/*  646 */         localObject = Integer.valueOf(paramObject.toString());
/*      */     }
/*  648 */     else if ((paramClass == Long.class) || (paramClass == Long.TYPE))
/*      */     {
/*  650 */       if ((paramObject instanceof Number))
/*  651 */         localObject = Long.valueOf(((Number)paramObject).longValue());
/*      */       else
/*  653 */         localObject = Long.valueOf(paramObject.toString());
/*      */     }
/*  655 */     else if (paramClass == BigInteger.class) {
/*  656 */       if ((paramObject instanceof Number))
/*  657 */         localObject = BigInteger.valueOf(((Number)paramObject).longValue());
/*      */       else
/*  659 */         localObject = new BigInteger(paramObject.toString());
/*      */     }
/*  661 */     else if ((paramClass == Float.class) || (paramClass == Float.TYPE))
/*      */     {
/*  663 */       if ((paramObject instanceof Number))
/*  664 */         localObject = Float.valueOf(((Number)paramObject).floatValue());
/*      */       else
/*  666 */         localObject = Float.valueOf(paramObject.toString());
/*      */     }
/*  668 */     else if ((paramClass == Double.class) || (paramClass == Double.TYPE))
/*      */     {
/*  670 */       if ((paramObject instanceof Number))
/*  671 */         localObject = Double.valueOf(((Number)paramObject).doubleValue());
/*      */       else
/*  673 */         localObject = Double.valueOf(paramObject.toString());
/*      */     }
/*  675 */     else if (paramClass == Number.class) {
/*  676 */       String str = paramObject.toString();
/*  677 */       if (str.contains("."))
/*  678 */         localObject = Double.valueOf(str);
/*      */       else
/*  680 */         localObject = Long.valueOf(str);
/*      */     }
/*  682 */     else if (paramClass == BigDecimal.class) {
/*  683 */       if ((paramObject instanceof Number))
/*  684 */         localObject = BigDecimal.valueOf(((Number)paramObject).doubleValue());
/*      */       else
/*  686 */         localObject = new BigDecimal(paramObject.toString());
/*      */     }
/*  688 */     else if (paramClass == Class.class) {
/*      */       try {
/*  690 */         localObject = ReflectUtil.forName(paramObject.toString());
/*      */       } catch (ClassNotFoundException localClassNotFoundException) {
/*  692 */         throw new IllegalArgumentException(localClassNotFoundException);
/*      */       }
/*      */     } else {
/*  695 */       Class localClass = paramObject.getClass();
/*  696 */       Method localMethod = null;
/*      */ 
/*  699 */       while ((localMethod == null) && (localClass != null)) {
/*      */         try {
/*  701 */           ReflectUtil.checkPackageAccess(paramClass);
/*  702 */           localMethod = paramClass.getDeclaredMethod("valueOf", new Class[] { localClass });
/*      */         }
/*      */         catch (NoSuchMethodException localNoSuchMethodException)
/*      */         {
/*      */         }
/*  707 */         if (localMethod == null) {
/*  708 */           localClass = localClass.getSuperclass();
/*      */         }
/*      */       }
/*      */ 
/*  712 */       if (localMethod == null) {
/*  713 */         throw new IllegalArgumentException(new StringBuilder().append("Unable to coerce ").append(paramObject).append(" to ").append(paramClass).append(".").toString());
/*      */       }
/*      */ 
/*  716 */       if ((paramClass.isEnum()) && ((paramObject instanceof String)) && (Character.isLowerCase(((String)paramObject).charAt(0))))
/*      */       {
/*  719 */         paramObject = toAllCaps((String)paramObject);
/*      */       }
/*      */       try
/*      */       {
/*  723 */         localObject = MethodUtil.invoke(localMethod, null, new Object[] { paramObject });
/*      */       } catch (IllegalAccessException localIllegalAccessException) {
/*  725 */         throw new RuntimeException(localIllegalAccessException);
/*      */       } catch (InvocationTargetException localInvocationTargetException) {
/*  727 */         throw new RuntimeException(localInvocationTargetException);
/*      */       } catch (SecurityException localSecurityException) {
/*  729 */         throw new RuntimeException(localSecurityException);
/*      */       }
/*      */     }
/*      */ 
/*  733 */     return localObject;
/*      */   }
/*      */ 
/*      */   public static <T> T get(Object paramObject, Class<?> paramClass, String paramString)
/*      */   {
/*  754 */     Object localObject = null;
/*      */ 
/*  756 */     Class localClass = paramObject.getClass();
/*  757 */     Method localMethod = getStaticGetterMethod(paramClass, paramString, localClass);
/*      */ 
/*  759 */     if (localMethod != null) {
/*      */       try {
/*  761 */         localObject = MethodUtil.invoke(localMethod, null, new Object[] { paramObject });
/*      */       } catch (InvocationTargetException localInvocationTargetException) {
/*  763 */         throw new RuntimeException(localInvocationTargetException);
/*      */       } catch (IllegalAccessException localIllegalAccessException) {
/*  765 */         throw new RuntimeException(localIllegalAccessException);
/*      */       }
/*      */     }
/*      */ 
/*  769 */     return localObject;
/*      */   }
/*      */ 
/*      */   public static void put(Object paramObject1, Class<?> paramClass, String paramString, Object paramObject2)
/*      */   {
/*  795 */     Class localClass1 = paramObject1.getClass();
/*      */ 
/*  797 */     Method localMethod = null;
/*  798 */     if (paramObject2 != null) {
/*  799 */       localMethod = getStaticSetterMethod(paramClass, paramString, paramObject2.getClass(), localClass1);
/*      */     }
/*      */ 
/*  802 */     if (localMethod == null)
/*      */     {
/*  804 */       Class localClass2 = getType(paramClass, paramString, localClass1);
/*      */ 
/*  806 */       if (localClass2 != null) {
/*  807 */         localMethod = getStaticSetterMethod(paramClass, paramString, localClass2, localClass1);
/*  808 */         paramObject2 = coerce(paramObject2, localClass2);
/*      */       }
/*      */     }
/*      */ 
/*  812 */     if (localMethod == null) {
/*  813 */       throw new PropertyNotFoundException(new StringBuilder().append("Static property \"").append(paramString).append("\" does not exist").append(" or is read-only.").toString());
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  819 */       MethodUtil.invoke(localMethod, null, new Object[] { paramObject1, paramObject2 });
/*      */     } catch (InvocationTargetException localInvocationTargetException) {
/*  821 */       throw new RuntimeException(localInvocationTargetException);
/*      */     } catch (IllegalAccessException localIllegalAccessException) {
/*  823 */       throw new RuntimeException(localIllegalAccessException);
/*      */     }
/*      */   }
/*      */ 
/*      */   public static boolean isDefined(Class<?> paramClass1, String paramString, Class<?> paramClass2)
/*      */   {
/*  843 */     return getStaticGetterMethod(paramClass1, paramString, paramClass2) != null;
/*      */   }
/*      */ 
/*      */   public static Class<?> getType(Class<?> paramClass1, String paramString, Class<?> paramClass2)
/*      */   {
/*  859 */     Method localMethod = getStaticGetterMethod(paramClass1, paramString, paramClass2);
/*  860 */     return localMethod == null ? null : localMethod.getReturnType();
/*      */   }
/*      */ 
/*      */   public static Type getGenericType(Class<?> paramClass1, String paramString, Class<?> paramClass2)
/*      */   {
/*  876 */     Method localMethod = getStaticGetterMethod(paramClass1, paramString, paramClass2);
/*  877 */     return localMethod == null ? null : localMethod.getGenericReturnType();
/*      */   }
/*      */ 
/*      */   public static Class<?> getListItemType(Type paramType)
/*      */   {
/*  886 */     Type localType = getGenericListItemType(paramType);
/*      */ 
/*  888 */     if ((localType instanceof ParameterizedType)) {
/*  889 */       localType = ((ParameterizedType)localType).getRawType();
/*      */     }
/*      */ 
/*  892 */     return (Class)localType;
/*      */   }
/*      */ 
/*      */   public static Class<?> getMapValueType(Type paramType)
/*      */   {
/*  901 */     Type localType = getGenericMapValueType(paramType);
/*      */ 
/*  903 */     if ((localType instanceof ParameterizedType)) {
/*  904 */       localType = ((ParameterizedType)localType).getRawType();
/*      */     }
/*      */ 
/*  907 */     return (Class)localType;
/*      */   }
/*      */ 
/*      */   public static Type getGenericListItemType(Type paramType)
/*      */   {
/*  916 */     Object localObject1 = null;
/*      */ 
/*  918 */     Type localType = paramType;
/*  919 */     while (localType != null) {
/*  920 */       if ((localType instanceof ParameterizedType)) {
/*  921 */         localObject2 = (ParameterizedType)localType;
/*  922 */         localObject3 = (Class)((ParameterizedType)localObject2).getRawType();
/*      */ 
/*  924 */         if (!List.class.isAssignableFrom((Class)localObject3)) break;
/*  925 */         localObject1 = localObject2.getActualTypeArguments()[0]; break;
/*      */       }
/*      */ 
/*  931 */       Object localObject2 = (Class)localType;
/*  932 */       Object localObject3 = ((Class)localObject2).getGenericInterfaces();
/*      */ 
/*  934 */       for (int i = 0; i < localObject3.length; i++) {
/*  935 */         Object localObject4 = localObject3[i];
/*      */ 
/*  937 */         if ((localObject4 instanceof ParameterizedType)) {
/*  938 */           ParameterizedType localParameterizedType = (ParameterizedType)localObject4;
/*  939 */           Class localClass = (Class)localParameterizedType.getRawType();
/*      */ 
/*  941 */           if (List.class.isAssignableFrom(localClass)) {
/*  942 */             localObject1 = localParameterizedType.getActualTypeArguments()[0];
/*  943 */             break;
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*  948 */       if (localObject1 != null)
/*      */       {
/*      */         break;
/*      */       }
/*  952 */       localType = ((Class)localObject2).getGenericSuperclass();
/*      */     }
/*      */ 
/*  955 */     if ((localObject1 != null) && ((localObject1 instanceof TypeVariable))) {
/*  956 */       localObject1 = Object.class;
/*      */     }
/*      */ 
/*  959 */     return localObject1;
/*      */   }
/*      */ 
/*      */   public static Type getGenericMapValueType(Type paramType)
/*      */   {
/*  968 */     Object localObject1 = null;
/*      */ 
/*  970 */     Type localType = paramType;
/*  971 */     while (localType != null) {
/*  972 */       if ((localType instanceof ParameterizedType)) {
/*  973 */         localObject2 = (ParameterizedType)localType;
/*  974 */         localObject3 = (Class)((ParameterizedType)localObject2).getRawType();
/*      */ 
/*  976 */         if (!Map.class.isAssignableFrom((Class)localObject3)) break;
/*  977 */         localObject1 = localObject2.getActualTypeArguments()[1]; break;
/*      */       }
/*      */ 
/*  983 */       Object localObject2 = (Class)localType;
/*  984 */       Object localObject3 = ((Class)localObject2).getGenericInterfaces();
/*      */ 
/*  986 */       for (int i = 0; i < localObject3.length; i++) {
/*  987 */         Object localObject4 = localObject3[i];
/*      */ 
/*  989 */         if ((localObject4 instanceof ParameterizedType)) {
/*  990 */           ParameterizedType localParameterizedType = (ParameterizedType)localObject4;
/*  991 */           Class localClass = (Class)localParameterizedType.getRawType();
/*      */ 
/*  993 */           if (Map.class.isAssignableFrom(localClass)) {
/*  994 */             localObject1 = localParameterizedType.getActualTypeArguments()[1];
/*  995 */             break;
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/* 1000 */       if (localObject1 != null)
/*      */       {
/*      */         break;
/*      */       }
/* 1004 */       localType = ((Class)localObject2).getGenericSuperclass();
/*      */     }
/*      */ 
/* 1007 */     if ((localObject1 != null) && ((localObject1 instanceof TypeVariable))) {
/* 1008 */       localObject1 = Object.class;
/*      */     }
/*      */ 
/* 1011 */     return localObject1;
/*      */   }
/*      */ 
/*      */   public static Object getConstantValue(Class<?> paramClass, String paramString)
/*      */   {
/* 1024 */     if (paramClass == null) {
/* 1025 */       throw new IllegalArgumentException();
/*      */     }
/*      */ 
/* 1028 */     if (paramString == null) {
/* 1029 */       throw new IllegalArgumentException();
/*      */     }
/*      */     Field localField;
/*      */     try
/*      */     {
/* 1034 */       localField = FieldUtil.getField(paramClass, paramString);
/*      */     } catch (NoSuchFieldException localNoSuchFieldException) {
/* 1036 */       throw new IllegalArgumentException(localNoSuchFieldException);
/*      */     }
/*      */ 
/* 1039 */     int i = localField.getModifiers();
/* 1040 */     if (((i & 0x8) == 0) || ((i & 0x10) == 0))
/*      */     {
/* 1042 */       throw new IllegalArgumentException("Field is not a constant.");
/*      */     }
/*      */     Object localObject;
/*      */     try
/*      */     {
/* 1047 */       localObject = localField.get(null);
/*      */     } catch (IllegalAccessException localIllegalAccessException) {
/* 1049 */       throw new IllegalArgumentException(localIllegalAccessException);
/*      */     }
/*      */ 
/* 1052 */     return localObject;
/*      */   }
/*      */ 
/*      */   private static Method getStaticGetterMethod(Class<?> paramClass1, String paramString, Class<?> paramClass2)
/*      */   {
/* 1057 */     if (paramClass1 == null) {
/* 1058 */       throw new NullPointerException();
/*      */     }
/*      */ 
/* 1061 */     if (paramString == null) {
/* 1062 */       throw new NullPointerException();
/*      */     }
/*      */ 
/* 1065 */     Method localMethod = null;
/*      */ 
/* 1067 */     if (paramClass2 != null) {
/* 1068 */       paramString = new StringBuilder().append(Character.toUpperCase(paramString.charAt(0))).append(paramString.substring(1)).toString();
/*      */ 
/* 1070 */       String str1 = new StringBuilder().append("get").append(paramString).toString();
/* 1071 */       String str2 = new StringBuilder().append("is").append(paramString).toString();
/*      */       try
/*      */       {
/* 1074 */         localMethod = MethodUtil.getMethod(paramClass1, str1, new Class[] { paramClass2 });
/*      */       }
/*      */       catch (NoSuchMethodException localNoSuchMethodException1)
/*      */       {
/*      */       }
/* 1079 */       if (localMethod == null) {
/*      */         try {
/* 1081 */           localMethod = MethodUtil.getMethod(paramClass1, str2, new Class[] { paramClass2 });
/*      */         }
/*      */         catch (NoSuchMethodException localNoSuchMethodException2)
/*      */         {
/*      */         }
/*      */       }
/*      */ 
/* 1088 */       if (localMethod == null) {
/* 1089 */         Class[] arrayOfClass = paramClass2.getInterfaces();
/* 1090 */         for (int i = 0; i < arrayOfClass.length; i++) {
/*      */           try {
/* 1092 */             localMethod = MethodUtil.getMethod(paramClass1, str1, new Class[] { arrayOfClass[i] });
/*      */           }
/*      */           catch (NoSuchMethodException localNoSuchMethodException3)
/*      */           {
/*      */           }
/* 1097 */           if (localMethod == null) {
/*      */             try {
/* 1099 */               localMethod = MethodUtil.getMethod(paramClass1, str2, new Class[] { arrayOfClass[i] });
/*      */             }
/*      */             catch (NoSuchMethodException localNoSuchMethodException4)
/*      */             {
/*      */             }
/*      */           }
/* 1105 */           if (localMethod != null)
/*      */           {
/*      */             break;
/*      */           }
/*      */         }
/*      */       }
/* 1111 */       if (localMethod == null) {
/* 1112 */         localMethod = getStaticGetterMethod(paramClass1, paramString, paramClass2.getSuperclass());
/*      */       }
/*      */     }
/*      */ 
/* 1116 */     return localMethod;
/*      */   }
/*      */ 
/*      */   private static Method getStaticSetterMethod(Class<?> paramClass1, String paramString, Class<?> paramClass2, Class<?> paramClass3)
/*      */   {
/* 1121 */     if (paramClass1 == null) {
/* 1122 */       throw new NullPointerException();
/*      */     }
/*      */ 
/* 1125 */     if (paramString == null) {
/* 1126 */       throw new NullPointerException();
/*      */     }
/*      */ 
/* 1129 */     if (paramClass2 == null) {
/* 1130 */       throw new NullPointerException();
/*      */     }
/*      */ 
/* 1133 */     Method localMethod = null;
/*      */ 
/* 1135 */     if (paramClass3 != null) {
/* 1136 */       paramString = new StringBuilder().append(Character.toUpperCase(paramString.charAt(0))).append(paramString.substring(1)).toString();
/*      */ 
/* 1138 */       String str = new StringBuilder().append("set").append(paramString).toString();
/*      */       try {
/* 1140 */         localMethod = MethodUtil.getMethod(paramClass1, str, new Class[] { paramClass3, paramClass2 });
/*      */       }
/*      */       catch (NoSuchMethodException localNoSuchMethodException1)
/*      */       {
/*      */       }
/*      */ 
/* 1146 */       if (localMethod == null) {
/* 1147 */         Class[] arrayOfClass = paramClass3.getInterfaces();
/* 1148 */         for (int i = 0; i < arrayOfClass.length; i++) {
/*      */           try {
/* 1150 */             localMethod = MethodUtil.getMethod(paramClass1, str, new Class[] { arrayOfClass[i], paramClass2 });
/*      */           }
/*      */           catch (NoSuchMethodException localNoSuchMethodException2)
/*      */           {
/*      */           }
/* 1155 */           if (localMethod != null)
/*      */           {
/*      */             break;
/*      */           }
/*      */         }
/*      */       }
/* 1161 */       if (localMethod == null) {
/* 1162 */         localMethod = getStaticSetterMethod(paramClass1, paramString, paramClass2, paramClass3.getSuperclass());
/*      */       }
/*      */     }
/*      */ 
/* 1166 */     return localMethod;
/*      */   }
/*      */ 
/*      */   private static String toAllCaps(String paramString) {
/* 1170 */     if (paramString == null) {
/* 1171 */       throw new NullPointerException();
/*      */     }
/*      */ 
/* 1174 */     StringBuilder localStringBuilder = new StringBuilder();
/*      */ 
/* 1176 */     int i = 0; for (int j = paramString.length(); i < j; i++) {
/* 1177 */       char c = paramString.charAt(i);
/*      */ 
/* 1179 */       if (Character.isUpperCase(c)) {
/* 1180 */         localStringBuilder.append('_');
/*      */       }
/*      */ 
/* 1183 */       localStringBuilder.append(Character.toUpperCase(c));
/*      */     }
/*      */ 
/* 1186 */     return localStringBuilder.toString();
/*      */   }
/*      */ 
/*      */   private static class BeanAdapterChangeEvent extends MapChangeListener.Change<String, Object>
/*      */   {
/*      */     private String key;
/*      */ 
/*      */     public BeanAdapterChangeEvent(BeanAdapter paramBeanAdapter, String paramString)
/*      */     {
/*  147 */       super();
/*      */ 
/*  149 */       this.key = paramString;
/*      */     }
/*      */ 
/*      */     public boolean wasAdded()
/*      */     {
/*  154 */       return false;
/*      */     }
/*      */ 
/*      */     public boolean wasRemoved()
/*      */     {
/*  159 */       return true;
/*      */     }
/*      */ 
/*      */     public String getKey()
/*      */     {
/*  164 */       return this.key;
/*      */     }
/*      */ 
/*      */     public Object getValueAdded()
/*      */     {
/*  169 */       return null;
/*      */     }
/*      */ 
/*      */     public Object getValueRemoved()
/*      */     {
/*  174 */       return null;
/*      */     }
/*      */   }
/*      */ 
/*      */   private class PropertyInvalidationListener
/*      */     implements InvalidationListener
/*      */   {
/*      */     public String key;
/*      */ 
/*      */     public PropertyInvalidationListener(String arg2)
/*      */     {
/*      */       Object localObject;
/*  133 */       this.key = localObject;
/*      */     }
/*      */ 
/*      */     public void invalidated(Observable paramObservable)
/*      */     {
/*  138 */       MapListenerHelper.fireValueChangedEvent(BeanAdapter.this.listenerHelper, new BeanAdapter.BeanAdapterChangeEvent(BeanAdapter.this, this.key));
/*      */     }
/*      */   }
/*      */ 
/*      */   private class PropertyEntry
/*      */     implements Map.Entry<String, Object>
/*      */   {
/*      */     private String key;
/*      */ 
/*      */     public PropertyEntry(String arg2)
/*      */     {
/*      */       Object localObject;
/*  109 */       this.key = localObject;
/*      */     }
/*      */ 
/*      */     public String getKey()
/*      */     {
/*  114 */       return this.key;
/*      */     }
/*      */ 
/*      */     public Object getValue()
/*      */     {
/*  119 */       return BeanAdapter.this.get(this.key);
/*      */     }
/*      */ 
/*      */     public Object setValue(Object paramObject)
/*      */     {
/*  124 */       return BeanAdapter.this.put(this.key, paramObject);
/*      */     }
/*      */   }
/*      */ 
/*      */   private class PropertyEntrySetIterator
/*      */     implements Iterator<Map.Entry<String, Object>>
/*      */   {
/*   53 */     private Iterator<Map.Entry<String, Method>> getterMethodIterator = BeanAdapter.this.getterMethods.entrySet().iterator();
/*   54 */     private String nextKey = null;
/*      */ 
/*      */     public PropertyEntrySetIterator() {
/*   57 */       nextKey();
/*      */     }
/*      */ 
/*      */     public boolean hasNext()
/*      */     {
/*   62 */       return this.nextKey != null;
/*      */     }
/*      */ 
/*      */     public Map.Entry<String, Object> next()
/*      */     {
/*   67 */       if (!hasNext()) {
/*   68 */         throw new NoSuchElementException();
/*      */       }
/*      */ 
/*   71 */       String str = this.nextKey;
/*   72 */       nextKey();
/*      */ 
/*   74 */       return new BeanAdapter.PropertyEntry(BeanAdapter.this, str);
/*      */     }
/*      */ 
/*      */     private void nextKey() {
/*   78 */       this.nextKey = null;
/*      */ 
/*   81 */       while ((this.getterMethodIterator.hasNext()) && (this.nextKey == null)) {
/*   82 */         Map.Entry localEntry = (Map.Entry)this.getterMethodIterator.next();
/*      */ 
/*   84 */         String str = (String)localEntry.getKey();
/*   85 */         Method localMethod = (Method)localEntry.getValue();
/*   86 */         Class localClass = localMethod.getReturnType();
/*      */ 
/*   90 */         if ((BeanAdapter.this.setterMethods.keySet().contains(str)) || (List.class.isAssignableFrom(localClass)) || (Map.class.isAssignableFrom(localClass)))
/*      */         {
/*   93 */           this.nextKey = str;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*      */     public void remove()
/*      */     {
/*  100 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */ 
/*      */   private class PropertyEntrySet extends AbstractSet<Map.Entry<String, Object>>
/*      */   {
/*      */     private PropertyEntrySet()
/*      */     {
/*      */     }
/*      */ 
/*      */     public int size()
/*      */     {
/*   42 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public Iterator<Map.Entry<String, Object>> iterator()
/*      */     {
/*   47 */       return new BeanAdapter.PropertyEntrySetIterator(BeanAdapter.this);
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.fxml.BeanAdapter
 * JD-Core Version:    0.6.2
 */