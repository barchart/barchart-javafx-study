/*     */ package javafx.fxml;
/*     */ 
/*     */ import com.sun.javafx.fxml.BeanAdapter;
/*     */ import java.lang.reflect.Array;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.AbstractMap;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javafx.collections.FXCollections;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.collections.ObservableMap;
/*     */ import javafx.scene.Node;
/*     */ import javafx.util.Builder;
/*     */ import sun.reflect.misc.MethodUtil;
/*     */ 
/*     */ final class JavaFXBuilder
/*     */ {
/* 179 */   private static final Object[] NO_ARGS = new Object[0];
/* 180 */   private static final Class<?>[] NO_SIG = new Class[0];
/*     */   private final Class<?> builderClass;
/*     */   private final Method createMethod;
/*     */   private final Method buildMethod;
/* 185 */   private final Map<String, Method> methods = new HashMap();
/* 186 */   private final Map<String, Method> getters = new HashMap();
/* 187 */   private final Map<String, Method> setters = new HashMap();
/*     */ 
/*     */   JavaFXBuilder()
/*     */   {
/* 409 */     this.builderClass = null;
/* 410 */     this.createMethod = null;
/* 411 */     this.buildMethod = null;
/*     */   }
/*     */ 
/*     */   JavaFXBuilder(Class<?> paramClass) throws NoSuchMethodException, InstantiationException, IllegalAccessException {
/* 415 */     this.builderClass = paramClass;
/* 416 */     this.createMethod = MethodUtil.getMethod(paramClass, "create", NO_SIG);
/* 417 */     this.buildMethod = MethodUtil.getMethod(paramClass, "build", NO_SIG);
/* 418 */     assert (Modifier.isStatic(this.createMethod.getModifiers()));
/* 419 */     assert (!Modifier.isStatic(this.buildMethod.getModifiers()));
/*     */   }
/*     */ 
/*     */   Builder<Object> createBuilder() {
/* 423 */     return new ObjectBuilder(null);
/*     */   }
/*     */ 
/*     */   private Method findMethod(String paramString) {
/* 427 */     if ((paramString.length() > 1) && (Character.isUpperCase(paramString.charAt(1))))
/*     */     {
/* 429 */       paramString = Character.toUpperCase(paramString.charAt(0)) + paramString.substring(1);
/*     */     }
/*     */ 
/* 432 */     for (Method localMethod : MethodUtil.getMethods(this.builderClass)) {
/* 433 */       if (localMethod.getName().equals(paramString)) {
/* 434 */         return localMethod;
/*     */       }
/*     */     }
/* 437 */     throw new IllegalArgumentException("Method " + paramString + " could not be found at class " + this.builderClass.getName());
/*     */   }
/*     */ 
/*     */   public Class<?> getTargetClass()
/*     */   {
/* 445 */     return this.buildMethod.getReturnType();
/*     */   }
/*     */ 
/*     */   final class ObjectBuilder extends AbstractMap<String, Object>
/*     */     implements Builder<Object>
/*     */   {
/* 190 */     private final Map<String, Object> containers = new HashMap();
/* 191 */     private Object builder = null;
/*     */     private Map<Object, Object> properties;
/*     */ 
/*     */     private ObjectBuilder()
/*     */     {
/*     */       try
/*     */       {
/* 196 */         this.builder = MethodUtil.invoke(JavaFXBuilder.this.createMethod, null, JavaFXBuilder.NO_ARGS);
/*     */       }
/*     */       catch (Exception localException) {
/* 199 */         throw new RuntimeException("Creation of the builder " + JavaFXBuilder.this.builderClass.getName() + " failed.", localException);
/*     */       }
/*     */     }
/*     */ 
/*     */     public Object build()
/*     */     {
/* 205 */       for (Object localObject1 = this.containers.entrySet().iterator(); ((Iterator)localObject1).hasNext(); ) {
/* 206 */         localObject2 = (Map.Entry)((Iterator)localObject1).next();
/*     */ 
/* 208 */         put((String)((Map.Entry)localObject2).getKey(), ((Map.Entry)localObject2).getValue());
/*     */       }
/*     */       try
/*     */       {
/* 212 */         localObject1 = MethodUtil.invoke(JavaFXBuilder.this.buildMethod, this.builder, JavaFXBuilder.NO_ARGS);
/*     */ 
/* 216 */         if ((this.properties != null) && ((localObject1 instanceof Node))) {
/* 217 */           ((Node)localObject1).getProperties().putAll(this.properties);
/*     */         }
/* 219 */         return localObject1;
/*     */       }
/*     */       catch (Exception localException)
/*     */       {
/*     */         Object localObject2;
/* 221 */         Logger.getLogger(JavaFXBuilder.class.getName()).log(Level.WARNING, "Failed to build instance of " + JavaFXBuilder.this.getTargetClass() + " using " + JavaFXBuilder.this.builderClass, localException);
/*     */ 
/* 223 */         return null;
/*     */       } finally {
/* 225 */         this.builder = null;
/*     */       }
/*     */     }
/*     */ 
/*     */     public int size()
/*     */     {
/* 231 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     public boolean isEmpty()
/*     */     {
/* 236 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     public boolean containsKey(Object paramObject)
/*     */     {
/* 241 */       return getTemporaryContainer(paramObject.toString()) != null;
/*     */     }
/*     */ 
/*     */     public boolean containsValue(Object paramObject)
/*     */     {
/* 246 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     public Object get(Object paramObject)
/*     */     {
/* 251 */       return getTemporaryContainer(paramObject.toString());
/*     */     }
/*     */ 
/*     */     public Object put(String paramString, Object paramObject)
/*     */     {
/* 259 */       if ((Node.class.isAssignableFrom(JavaFXBuilder.this.getTargetClass())) && ("properties".equals(paramString))) {
/* 260 */         this.properties = ((Map)paramObject);
/* 261 */         return null;
/*     */       }
/*     */       try {
/* 264 */         Method localMethod = (Method)JavaFXBuilder.this.methods.get(paramString);
/* 265 */         if (localMethod == null) {
/* 266 */           localMethod = JavaFXBuilder.this.findMethod(paramString);
/* 267 */           JavaFXBuilder.this.methods.put(paramString, localMethod);
/*     */         }
/*     */         try {
/* 270 */           Class localClass1 = localMethod.getParameterTypes()[0];
/*     */ 
/* 276 */           if (localClass1.isArray())
/*     */           {
/*     */             List localList;
/* 278 */             if ((paramObject instanceof List))
/* 279 */               localList = (List)paramObject;
/*     */             else {
/* 281 */               localList = Arrays.asList(paramObject.toString().split(","));
/*     */             }
/*     */ 
/* 284 */             Class localClass2 = localClass1.getComponentType();
/* 285 */             Object localObject = Array.newInstance(localClass2, localList.size());
/* 286 */             for (int i = 0; i < localList.size(); i++) {
/* 287 */               Array.set(localObject, i, BeanAdapter.coerce(localList.get(i), localClass2));
/*     */             }
/* 289 */             paramObject = localObject;
/*     */           }
/*     */ 
/* 292 */           MethodUtil.invoke(localMethod, this.builder, new Object[] { BeanAdapter.coerce(paramObject, localClass1) });
/*     */         } catch (Exception localException2) {
/* 294 */           Logger.getLogger(JavaFXBuilder.class.getName()).log(Level.WARNING, "Method " + localMethod.getName() + " failed", localException2);
/*     */         }
/*     */ 
/* 298 */         return null;
/*     */       }
/*     */       catch (Exception localException1) {
/* 301 */         Logger.getLogger(JavaFXBuilder.class.getName()).log(Level.WARNING, "Failed to set " + JavaFXBuilder.this.getTargetClass() + "." + paramString + " using " + JavaFXBuilder.this.builderClass, localException1);
/*     */       }
/* 303 */       return null;
/*     */     }
/*     */ 
/*     */     Object getReadOnlyProperty(String paramString)
/*     */     {
/* 313 */       if (JavaFXBuilder.this.setters.get(paramString) != null) return null;
/* 314 */       Method localMethod = (Method)JavaFXBuilder.this.getters.get(paramString);
/*     */       Object localObject1;
/*     */       Object localObject2;
/* 315 */       if (localMethod == null) {
/* 316 */         localObject1 = null;
/* 317 */         localObject2 = JavaFXBuilder.this.getTargetClass();
/* 318 */         String str = Character.toUpperCase(paramString.charAt(0)) + paramString.substring(1);
/*     */         try {
/* 320 */           localMethod = MethodUtil.getMethod((Class)localObject2, "get" + str, JavaFXBuilder.NO_SIG);
/* 321 */           localObject1 = MethodUtil.getMethod((Class)localObject2, "set" + str, new Class[] { localMethod.getReturnType() });
/*     */         } catch (Exception localException) {
/*     */         }
/* 324 */         if (localMethod != null) {
/* 325 */           JavaFXBuilder.this.getters.put(paramString, localMethod);
/* 326 */           JavaFXBuilder.this.setters.put(paramString, localObject1);
/*     */         }
/* 328 */         if (localObject1 != null) return null;
/*     */ 
/*     */       }
/*     */ 
/* 332 */       if (localMethod == null)
/*     */       {
/* 335 */         localObject2 = JavaFXBuilder.this.findMethod(paramString);
/* 336 */         if (localObject2 == null) {
/* 337 */           return null;
/*     */         }
/* 339 */         localObject1 = localObject2.getParameterTypes()[0];
/* 340 */         if (((Class)localObject1).isArray()) localObject1 = List.class; 
/*     */       }
/* 342 */       else { localObject1 = localMethod.getReturnType(); }
/*     */ 
/*     */ 
/* 345 */       if (ObservableMap.class.isAssignableFrom((Class)localObject1))
/* 346 */         return FXCollections.observableMap(new HashMap());
/* 347 */       if (Map.class.isAssignableFrom((Class)localObject1))
/* 348 */         return new HashMap();
/* 349 */       if (ObservableList.class.isAssignableFrom((Class)localObject1))
/* 350 */         return FXCollections.observableArrayList();
/* 351 */       if (List.class.isAssignableFrom((Class)localObject1))
/* 352 */         return new ArrayList();
/* 353 */       if (Set.class.isAssignableFrom((Class)localObject1)) {
/* 354 */         return new HashSet();
/*     */       }
/* 356 */       return null;
/*     */     }
/*     */ 
/*     */     public Object getTemporaryContainer(String paramString)
/*     */     {
/* 366 */       Object localObject = this.containers.get(paramString);
/* 367 */       if (localObject == null) {
/* 368 */         localObject = getReadOnlyProperty(paramString);
/* 369 */         if (localObject != null) {
/* 370 */           this.containers.put(paramString, localObject);
/*     */         }
/*     */       }
/*     */ 
/* 374 */       return localObject;
/*     */     }
/*     */ 
/*     */     public Object remove(Object paramObject)
/*     */     {
/* 379 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     public void putAll(Map<? extends String, ? extends Object> paramMap)
/*     */     {
/* 384 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     public void clear()
/*     */     {
/* 389 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     public Set<String> keySet()
/*     */     {
/* 394 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     public Collection<Object> values()
/*     */     {
/* 399 */       throw new UnsupportedOperationException();
/*     */     }
/*     */ 
/*     */     public Set<Map.Entry<String, Object>> entrySet()
/*     */     {
/* 404 */       throw new UnsupportedOperationException();
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.fxml.JavaFXBuilder
 * JD-Core Version:    0.6.2
 */