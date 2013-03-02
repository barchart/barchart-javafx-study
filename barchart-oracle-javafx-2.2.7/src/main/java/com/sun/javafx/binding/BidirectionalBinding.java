/*     */ package com.sun.javafx.binding;
/*     */ 
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.text.Format;
/*     */ import java.text.ParseException;
/*     */ import javafx.beans.Observable;
/*     */ import javafx.beans.WeakListener;
/*     */ import javafx.beans.property.BooleanProperty;
/*     */ import javafx.beans.property.DoubleProperty;
/*     */ import javafx.beans.property.FloatProperty;
/*     */ import javafx.beans.property.IntegerProperty;
/*     */ import javafx.beans.property.LongProperty;
/*     */ import javafx.beans.property.Property;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ import javafx.util.StringConverter;
/*     */ 
/*     */ public abstract class BidirectionalBinding<T>
/*     */   implements ChangeListener<T>, WeakListener
/*     */ {
/*     */   private final int cachedHashCode;
/*     */ 
/*     */   private static void checkParameters(Object paramObject1, Object paramObject2)
/*     */   {
/*  62 */     if ((paramObject1 == null) || (paramObject2 == null)) {
/*  63 */       throw new NullPointerException("Both properties must be specified.");
/*     */     }
/*  65 */     if (paramObject1.equals(paramObject2))
/*  66 */       throw new IllegalArgumentException("Cannot bind property to itself");
/*     */   }
/*     */ 
/*     */   public static <T> BidirectionalBinding bind(Property<T> paramProperty1, Property<T> paramProperty2)
/*     */   {
/*  71 */     checkParameters(paramProperty1, paramProperty2);
/*  72 */     TypedGenericBidirectionalBinding localTypedGenericBidirectionalBinding = ((paramProperty1 instanceof BooleanProperty)) && ((paramProperty2 instanceof BooleanProperty)) ? new BidirectionalBooleanBinding((BooleanProperty)paramProperty1, (BooleanProperty)paramProperty2, null) : ((paramProperty1 instanceof LongProperty)) && ((paramProperty2 instanceof LongProperty)) ? new BidirectionalLongBinding((LongProperty)paramProperty1, (LongProperty)paramProperty2, null) : ((paramProperty1 instanceof IntegerProperty)) && ((paramProperty2 instanceof IntegerProperty)) ? new BidirectionalIntegerBinding((IntegerProperty)paramProperty1, (IntegerProperty)paramProperty2, null) : ((paramProperty1 instanceof FloatProperty)) && ((paramProperty2 instanceof FloatProperty)) ? new BidirectionalFloatBinding((FloatProperty)paramProperty1, (FloatProperty)paramProperty2, null) : ((paramProperty1 instanceof DoubleProperty)) && ((paramProperty2 instanceof DoubleProperty)) ? new BidirectionalDoubleBinding((DoubleProperty)paramProperty1, (DoubleProperty)paramProperty2, null) : new TypedGenericBidirectionalBinding(paramProperty1, paramProperty2, null);
/*     */ 
/*  84 */     paramProperty1.setValue(paramProperty2.getValue());
/*  85 */     paramProperty1.addListener(localTypedGenericBidirectionalBinding);
/*  86 */     paramProperty2.addListener(localTypedGenericBidirectionalBinding);
/*  87 */     return localTypedGenericBidirectionalBinding;
/*     */   }
/*     */ 
/*     */   public static Object bind(Property<String> paramProperty, Property<?> paramProperty1, Format paramFormat) {
/*  91 */     checkParameters(paramProperty, paramProperty1);
/*  92 */     if (paramFormat == null) {
/*  93 */       throw new NullPointerException("Format cannot be null");
/*     */     }
/*  95 */     StringFormatBidirectionalBinding localStringFormatBidirectionalBinding = new StringFormatBidirectionalBinding(paramProperty, paramProperty1, paramFormat);
/*  96 */     paramProperty.setValue(paramFormat.format(paramProperty1.getValue()));
/*  97 */     paramProperty.addListener(localStringFormatBidirectionalBinding);
/*  98 */     paramProperty1.addListener(localStringFormatBidirectionalBinding);
/*  99 */     return localStringFormatBidirectionalBinding;
/*     */   }
/*     */ 
/*     */   public static <T> Object bind(Property<String> paramProperty, Property<T> paramProperty1, StringConverter<T> paramStringConverter) {
/* 103 */     checkParameters(paramProperty, paramProperty1);
/* 104 */     if (paramStringConverter == null) {
/* 105 */       throw new NullPointerException("Converter cannot be null");
/*     */     }
/* 107 */     StringConverterBidirectionalBinding localStringConverterBidirectionalBinding = new StringConverterBidirectionalBinding(paramProperty, paramProperty1, paramStringConverter);
/* 108 */     paramProperty.setValue(paramStringConverter.toString(paramProperty1.getValue()));
/* 109 */     paramProperty.addListener(localStringConverterBidirectionalBinding);
/* 110 */     paramProperty1.addListener(localStringConverterBidirectionalBinding);
/* 111 */     return localStringConverterBidirectionalBinding;
/*     */   }
/*     */ 
/*     */   public static <T> void unbind(Property<T> paramProperty1, Property<T> paramProperty2) {
/* 115 */     checkParameters(paramProperty1, paramProperty2);
/* 116 */     UntypedGenericBidirectionalBinding localUntypedGenericBidirectionalBinding = new UntypedGenericBidirectionalBinding(paramProperty1, paramProperty2);
/* 117 */     paramProperty1.removeListener(localUntypedGenericBidirectionalBinding);
/* 118 */     paramProperty2.removeListener(localUntypedGenericBidirectionalBinding);
/*     */   }
/*     */ 
/*     */   public static void unbind(Object paramObject1, Object paramObject2) {
/* 122 */     checkParameters(paramObject1, paramObject2);
/* 123 */     UntypedGenericBidirectionalBinding localUntypedGenericBidirectionalBinding = new UntypedGenericBidirectionalBinding(paramObject1, paramObject2);
/* 124 */     if ((paramObject1 instanceof ObservableValue)) {
/* 125 */       ((ObservableValue)paramObject1).removeListener(localUntypedGenericBidirectionalBinding);
/*     */     }
/* 127 */     if ((paramObject2 instanceof Observable))
/* 128 */       ((ObservableValue)paramObject2).removeListener(localUntypedGenericBidirectionalBinding);
/*     */   }
/*     */ 
/*     */   private BidirectionalBinding(Object paramObject1, Object paramObject2)
/*     */   {
/* 135 */     this.cachedHashCode = (paramObject1.hashCode() * paramObject2.hashCode());
/*     */   }
/*     */ 
/*     */   protected abstract Object getProperty1();
/*     */ 
/*     */   protected abstract Object getProperty2();
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 144 */     return this.cachedHashCode;
/*     */   }
/*     */ 
/*     */   public boolean wasGarbageCollected()
/*     */   {
/* 149 */     return (getProperty1() == null) || (getProperty2() == null);
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 154 */     if (this == paramObject) {
/* 155 */       return true;
/*     */     }
/*     */ 
/* 158 */     Object localObject1 = getProperty1();
/* 159 */     Object localObject2 = getProperty2();
/* 160 */     if ((localObject1 == null) || (localObject2 == null)) {
/* 161 */       return false;
/*     */     }
/*     */ 
/* 164 */     if ((paramObject instanceof BidirectionalBinding)) {
/* 165 */       BidirectionalBinding localBidirectionalBinding = (BidirectionalBinding)paramObject;
/* 166 */       Object localObject3 = localBidirectionalBinding.getProperty1();
/* 167 */       Object localObject4 = localBidirectionalBinding.getProperty2();
/* 168 */       if ((localObject3 == null) || (localObject4 == null)) {
/* 169 */         return false;
/*     */       }
/*     */ 
/* 172 */       if ((localObject1.equals(localObject3)) && (localObject2.equals(localObject4)))
/*     */       {
/* 174 */         return true;
/*     */       }
/* 176 */       if ((localObject1.equals(localObject4)) && (localObject2.equals(localObject3)))
/*     */       {
/* 178 */         return true;
/*     */       }
/*     */     }
/* 181 */     return false;
/*     */   }
/*     */ 
/*     */   private static class StringConverterBidirectionalBinding<T> extends BidirectionalBinding.StringConversionBidirectionalBinding<T>
/*     */   {
/*     */     private final StringConverter<T> converter;
/*     */ 
/*     */     public StringConverterBidirectionalBinding(Property<String> paramProperty, Property<T> paramProperty1, StringConverter<T> paramStringConverter)
/*     */     {
/* 593 */       super(paramProperty1);
/* 594 */       this.converter = paramStringConverter;
/*     */     }
/*     */ 
/*     */     protected String toString(T paramT)
/*     */     {
/* 599 */       return this.converter.toString(paramT);
/*     */     }
/*     */ 
/*     */     protected T fromString(String paramString) throws ParseException
/*     */     {
/* 604 */       return this.converter.fromString(paramString);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class StringFormatBidirectionalBinding extends BidirectionalBinding.StringConversionBidirectionalBinding
/*     */   {
/*     */     private final Format format;
/*     */ 
/*     */     public StringFormatBidirectionalBinding(Property<String> paramProperty, Property<?> paramProperty1, Format paramFormat)
/*     */     {
/* 573 */       super(paramProperty1);
/* 574 */       this.format = paramFormat;
/*     */     }
/*     */ 
/*     */     protected String toString(Object paramObject)
/*     */     {
/* 579 */       return this.format.format(paramObject);
/*     */     }
/*     */ 
/*     */     protected Object fromString(String paramString) throws ParseException
/*     */     {
/* 584 */       return this.format.parseObject(paramString);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static abstract class StringConversionBidirectionalBinding<T> extends BidirectionalBinding<Object>
/*     */   {
/*     */     private final WeakReference<Property<String>> stringPropertyRef;
/*     */     private final WeakReference<Property<T>> otherPropertyRef;
/*     */     private boolean updating;
/*     */ 
/*     */     public StringConversionBidirectionalBinding(Property<String> paramProperty, Property<T> paramProperty1)
/*     */     {
/* 512 */       super(paramProperty1, null);
/* 513 */       this.stringPropertyRef = new WeakReference(paramProperty);
/* 514 */       this.otherPropertyRef = new WeakReference(paramProperty1);
/*     */     }
/*     */ 
/*     */     protected abstract String toString(T paramT);
/*     */ 
/*     */     protected abstract T fromString(String paramString) throws ParseException;
/*     */ 
/*     */     protected Object getProperty1()
/*     */     {
/* 523 */       return this.stringPropertyRef.get();
/*     */     }
/*     */ 
/*     */     protected Object getProperty2()
/*     */     {
/* 528 */       return this.otherPropertyRef.get();
/*     */     }
/*     */ 
/*     */     public void changed(ObservableValue<? extends Object> paramObservableValue, Object paramObject1, Object paramObject2)
/*     */     {
/* 533 */       if (!this.updating) {
/* 534 */         Property localProperty1 = (Property)this.stringPropertyRef.get();
/* 535 */         Property localProperty2 = (Property)this.otherPropertyRef.get();
/* 536 */         if ((localProperty1 == null) || (localProperty2 == null)) {
/* 537 */           if (localProperty1 != null) {
/* 538 */             localProperty1.removeListener(this);
/*     */           }
/* 540 */           if (localProperty2 != null)
/* 541 */             localProperty2.removeListener(this);
/*     */         }
/*     */         else {
/*     */           try {
/* 545 */             this.updating = true;
/* 546 */             if (localProperty1.equals(paramObservableValue))
/*     */               try {
/* 548 */                 localProperty2.setValue(fromString((String)localProperty1.getValue()));
/*     */               } catch (Exception localException1) {
/* 550 */                 localProperty2.setValue(null);
/*     */               }
/*     */             else
/*     */               try {
/* 554 */                 localProperty1.setValue(toString(localProperty2.getValue()));
/*     */               } catch (Exception localException2) {
/* 556 */                 localProperty1.setValue("");
/*     */               }
/*     */           }
/*     */           finally {
/* 560 */             this.updating = false;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class UntypedGenericBidirectionalBinding extends BidirectionalBinding<Object>
/*     */   {
/*     */     private final Object property1;
/*     */     private final Object property2;
/*     */ 
/*     */     public UntypedGenericBidirectionalBinding(Object paramObject1, Object paramObject2)
/*     */     {
/* 484 */       super(paramObject2, null);
/* 485 */       this.property1 = paramObject1;
/* 486 */       this.property2 = paramObject2;
/*     */     }
/*     */ 
/*     */     protected Object getProperty1()
/*     */     {
/* 491 */       return this.property1;
/*     */     }
/*     */ 
/*     */     protected Object getProperty2()
/*     */     {
/* 496 */       return this.property2;
/*     */     }
/*     */ 
/*     */     public void changed(ObservableValue<? extends Object> paramObservableValue, Object paramObject1, Object paramObject2)
/*     */     {
/* 501 */       throw new RuntimeException("Should not reach here");
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class TypedGenericBidirectionalBinding<T> extends BidirectionalBinding<T>
/*     */   {
/*     */     private final WeakReference<Property<T>> propertyRef1;
/*     */     private final WeakReference<Property<T>> propertyRef2;
/* 432 */     private boolean updating = false;
/*     */ 
/*     */     private TypedGenericBidirectionalBinding(Property<T> paramProperty1, Property<T> paramProperty2) {
/* 435 */       super(paramProperty2, null);
/* 436 */       this.propertyRef1 = new WeakReference(paramProperty1);
/* 437 */       this.propertyRef2 = new WeakReference(paramProperty2);
/*     */     }
/*     */ 
/*     */     protected Property<T> getProperty1()
/*     */     {
/* 442 */       return (Property)this.propertyRef1.get();
/*     */     }
/*     */ 
/*     */     protected Property<T> getProperty2()
/*     */     {
/* 447 */       return (Property)this.propertyRef2.get();
/*     */     }
/*     */ 
/*     */     public void changed(ObservableValue<? extends T> paramObservableValue, T paramT1, T paramT2)
/*     */     {
/* 452 */       if (!this.updating) {
/* 453 */         Property localProperty1 = (Property)this.propertyRef1.get();
/* 454 */         Property localProperty2 = (Property)this.propertyRef2.get();
/* 455 */         if ((localProperty1 == null) || (localProperty2 == null)) {
/* 456 */           if (localProperty1 != null) {
/* 457 */             localProperty1.removeListener(this);
/*     */           }
/* 459 */           if (localProperty2 != null)
/* 460 */             localProperty2.removeListener(this);
/*     */         }
/*     */         else {
/*     */           try {
/* 464 */             this.updating = true;
/* 465 */             if (localProperty1.equals(paramObservableValue))
/* 466 */               localProperty2.setValue(localProperty1.getValue());
/*     */             else
/* 468 */               localProperty1.setValue(localProperty2.getValue());
/*     */           }
/*     */           finally {
/* 471 */             this.updating = false;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class BidirectionalLongBinding extends BidirectionalBinding<Number>
/*     */   {
/*     */     private final WeakReference<LongProperty> propertyRef1;
/*     */     private final WeakReference<LongProperty> propertyRef2;
/* 383 */     private boolean updating = false;
/*     */ 
/*     */     private BidirectionalLongBinding(LongProperty paramLongProperty1, LongProperty paramLongProperty2) {
/* 386 */       super(paramLongProperty2, null);
/* 387 */       this.propertyRef1 = new WeakReference(paramLongProperty1);
/* 388 */       this.propertyRef2 = new WeakReference(paramLongProperty2);
/*     */     }
/*     */ 
/*     */     protected Property<Number> getProperty1()
/*     */     {
/* 393 */       return (Property)this.propertyRef1.get();
/*     */     }
/*     */ 
/*     */     protected Property<Number> getProperty2()
/*     */     {
/* 398 */       return (Property)this.propertyRef2.get();
/*     */     }
/*     */ 
/*     */     public void changed(ObservableValue<? extends Number> paramObservableValue, Number paramNumber1, Number paramNumber2)
/*     */     {
/* 403 */       if (!this.updating) {
/* 404 */         LongProperty localLongProperty1 = (LongProperty)this.propertyRef1.get();
/* 405 */         LongProperty localLongProperty2 = (LongProperty)this.propertyRef2.get();
/* 406 */         if ((localLongProperty1 == null) || (localLongProperty2 == null)) {
/* 407 */           if (localLongProperty1 != null) {
/* 408 */             localLongProperty1.removeListener(this);
/*     */           }
/* 410 */           if (localLongProperty2 != null)
/* 411 */             localLongProperty2.removeListener(this);
/*     */         }
/*     */         else {
/*     */           try {
/* 415 */             this.updating = true;
/* 416 */             if (localLongProperty1.equals(paramObservableValue))
/* 417 */               localLongProperty2.set(paramNumber2.longValue());
/*     */             else
/* 419 */               localLongProperty1.set(paramNumber2.longValue());
/*     */           }
/*     */           finally {
/* 422 */             this.updating = false;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class BidirectionalIntegerBinding extends BidirectionalBinding<Number>
/*     */   {
/*     */     private final WeakReference<IntegerProperty> propertyRef1;
/*     */     private final WeakReference<IntegerProperty> propertyRef2;
/* 334 */     private boolean updating = false;
/*     */ 
/*     */     private BidirectionalIntegerBinding(IntegerProperty paramIntegerProperty1, IntegerProperty paramIntegerProperty2) {
/* 337 */       super(paramIntegerProperty2, null);
/* 338 */       this.propertyRef1 = new WeakReference(paramIntegerProperty1);
/* 339 */       this.propertyRef2 = new WeakReference(paramIntegerProperty2);
/*     */     }
/*     */ 
/*     */     protected Property<Number> getProperty1()
/*     */     {
/* 344 */       return (Property)this.propertyRef1.get();
/*     */     }
/*     */ 
/*     */     protected Property<Number> getProperty2()
/*     */     {
/* 349 */       return (Property)this.propertyRef2.get();
/*     */     }
/*     */ 
/*     */     public void changed(ObservableValue<? extends Number> paramObservableValue, Number paramNumber1, Number paramNumber2)
/*     */     {
/* 354 */       if (!this.updating) {
/* 355 */         IntegerProperty localIntegerProperty1 = (IntegerProperty)this.propertyRef1.get();
/* 356 */         IntegerProperty localIntegerProperty2 = (IntegerProperty)this.propertyRef2.get();
/* 357 */         if ((localIntegerProperty1 == null) || (localIntegerProperty2 == null)) {
/* 358 */           if (localIntegerProperty1 != null) {
/* 359 */             localIntegerProperty1.removeListener(this);
/*     */           }
/* 361 */           if (localIntegerProperty2 != null)
/* 362 */             localIntegerProperty2.removeListener(this);
/*     */         }
/*     */         else {
/*     */           try {
/* 366 */             this.updating = true;
/* 367 */             if (localIntegerProperty1.equals(paramObservableValue))
/* 368 */               localIntegerProperty2.set(paramNumber2.intValue());
/*     */             else
/* 370 */               localIntegerProperty1.set(paramNumber2.intValue());
/*     */           }
/*     */           finally {
/* 373 */             this.updating = false;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class BidirectionalFloatBinding extends BidirectionalBinding<Number>
/*     */   {
/*     */     private final WeakReference<FloatProperty> propertyRef1;
/*     */     private final WeakReference<FloatProperty> propertyRef2;
/* 285 */     private boolean updating = false;
/*     */ 
/*     */     private BidirectionalFloatBinding(FloatProperty paramFloatProperty1, FloatProperty paramFloatProperty2) {
/* 288 */       super(paramFloatProperty2, null);
/* 289 */       this.propertyRef1 = new WeakReference(paramFloatProperty1);
/* 290 */       this.propertyRef2 = new WeakReference(paramFloatProperty2);
/*     */     }
/*     */ 
/*     */     protected Property<Number> getProperty1()
/*     */     {
/* 295 */       return (Property)this.propertyRef1.get();
/*     */     }
/*     */ 
/*     */     protected Property<Number> getProperty2()
/*     */     {
/* 300 */       return (Property)this.propertyRef2.get();
/*     */     }
/*     */ 
/*     */     public void changed(ObservableValue<? extends Number> paramObservableValue, Number paramNumber1, Number paramNumber2)
/*     */     {
/* 305 */       if (!this.updating) {
/* 306 */         FloatProperty localFloatProperty1 = (FloatProperty)this.propertyRef1.get();
/* 307 */         FloatProperty localFloatProperty2 = (FloatProperty)this.propertyRef2.get();
/* 308 */         if ((localFloatProperty1 == null) || (localFloatProperty2 == null)) {
/* 309 */           if (localFloatProperty1 != null) {
/* 310 */             localFloatProperty1.removeListener(this);
/*     */           }
/* 312 */           if (localFloatProperty2 != null)
/* 313 */             localFloatProperty2.removeListener(this);
/*     */         }
/*     */         else {
/*     */           try {
/* 317 */             this.updating = true;
/* 318 */             if (localFloatProperty1.equals(paramObservableValue))
/* 319 */               localFloatProperty2.set(paramNumber2.floatValue());
/*     */             else
/* 321 */               localFloatProperty1.set(paramNumber2.floatValue());
/*     */           }
/*     */           finally {
/* 324 */             this.updating = false;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class BidirectionalDoubleBinding extends BidirectionalBinding<Number>
/*     */   {
/*     */     private final WeakReference<DoubleProperty> propertyRef1;
/*     */     private final WeakReference<DoubleProperty> propertyRef2;
/* 236 */     private boolean updating = false;
/*     */ 
/*     */     private BidirectionalDoubleBinding(DoubleProperty paramDoubleProperty1, DoubleProperty paramDoubleProperty2) {
/* 239 */       super(paramDoubleProperty2, null);
/* 240 */       this.propertyRef1 = new WeakReference(paramDoubleProperty1);
/* 241 */       this.propertyRef2 = new WeakReference(paramDoubleProperty2);
/*     */     }
/*     */ 
/*     */     protected Property<Number> getProperty1()
/*     */     {
/* 246 */       return (Property)this.propertyRef1.get();
/*     */     }
/*     */ 
/*     */     protected Property<Number> getProperty2()
/*     */     {
/* 251 */       return (Property)this.propertyRef2.get();
/*     */     }
/*     */ 
/*     */     public void changed(ObservableValue<? extends Number> paramObservableValue, Number paramNumber1, Number paramNumber2)
/*     */     {
/* 256 */       if (!this.updating) {
/* 257 */         DoubleProperty localDoubleProperty1 = (DoubleProperty)this.propertyRef1.get();
/* 258 */         DoubleProperty localDoubleProperty2 = (DoubleProperty)this.propertyRef2.get();
/* 259 */         if ((localDoubleProperty1 == null) || (localDoubleProperty2 == null)) {
/* 260 */           if (localDoubleProperty1 != null) {
/* 261 */             localDoubleProperty1.removeListener(this);
/*     */           }
/* 263 */           if (localDoubleProperty2 != null)
/* 264 */             localDoubleProperty2.removeListener(this);
/*     */         }
/*     */         else {
/*     */           try {
/* 268 */             this.updating = true;
/* 269 */             if (localDoubleProperty1.equals(paramObservableValue))
/* 270 */               localDoubleProperty2.set(paramNumber2.doubleValue());
/*     */             else
/* 272 */               localDoubleProperty1.set(paramNumber2.doubleValue());
/*     */           }
/*     */           finally {
/* 275 */             this.updating = false;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class BidirectionalBooleanBinding extends BidirectionalBinding<Boolean>
/*     */   {
/*     */     private final WeakReference<BooleanProperty> propertyRef1;
/*     */     private final WeakReference<BooleanProperty> propertyRef2;
/* 187 */     private boolean updating = false;
/*     */ 
/*     */     private BidirectionalBooleanBinding(BooleanProperty paramBooleanProperty1, BooleanProperty paramBooleanProperty2) {
/* 190 */       super(paramBooleanProperty2, null);
/* 191 */       this.propertyRef1 = new WeakReference(paramBooleanProperty1);
/* 192 */       this.propertyRef2 = new WeakReference(paramBooleanProperty2);
/*     */     }
/*     */ 
/*     */     protected Property<Boolean> getProperty1()
/*     */     {
/* 197 */       return (Property)this.propertyRef1.get();
/*     */     }
/*     */ 
/*     */     protected Property<Boolean> getProperty2()
/*     */     {
/* 202 */       return (Property)this.propertyRef2.get();
/*     */     }
/*     */ 
/*     */     public void changed(ObservableValue<? extends Boolean> paramObservableValue, Boolean paramBoolean1, Boolean paramBoolean2)
/*     */     {
/* 207 */       if (!this.updating) {
/* 208 */         BooleanProperty localBooleanProperty1 = (BooleanProperty)this.propertyRef1.get();
/* 209 */         BooleanProperty localBooleanProperty2 = (BooleanProperty)this.propertyRef2.get();
/* 210 */         if ((localBooleanProperty1 == null) || (localBooleanProperty2 == null)) {
/* 211 */           if (localBooleanProperty1 != null) {
/* 212 */             localBooleanProperty1.removeListener(this);
/*     */           }
/* 214 */           if (localBooleanProperty2 != null)
/* 215 */             localBooleanProperty2.removeListener(this);
/*     */         }
/*     */         else {
/*     */           try {
/* 219 */             this.updating = true;
/* 220 */             if (localBooleanProperty1.equals(paramObservableValue))
/* 221 */               localBooleanProperty2.set(paramBoolean2.booleanValue());
/*     */             else
/* 223 */               localBooleanProperty1.set(paramBoolean2.booleanValue());
/*     */           }
/*     */           finally {
/* 226 */             this.updating = false;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.binding.BidirectionalBinding
 * JD-Core Version:    0.6.2
 */