/*     */ package com.sun.javafx.binding;
/*     */ 
/*     */ import com.sun.javafx.property.PropertyReference;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.Observable;
/*     */ import javafx.beans.WeakInvalidationListener;
/*     */ import javafx.beans.binding.Binding;
/*     */ import javafx.beans.binding.BooleanBinding;
/*     */ import javafx.beans.binding.DoubleBinding;
/*     */ import javafx.beans.binding.FloatBinding;
/*     */ import javafx.beans.binding.IntegerBinding;
/*     */ import javafx.beans.binding.LongBinding;
/*     */ import javafx.beans.binding.ObjectBinding;
/*     */ import javafx.beans.binding.StringBinding;
/*     */ import javafx.beans.value.ObservableBooleanValue;
/*     */ import javafx.beans.value.ObservableDoubleValue;
/*     */ import javafx.beans.value.ObservableFloatValue;
/*     */ import javafx.beans.value.ObservableIntegerValue;
/*     */ import javafx.beans.value.ObservableLongValue;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ import javafx.collections.FXCollections;
/*     */ import javafx.collections.ObservableList;
/*     */ 
/*     */ public class SelectBinding
/*     */ {
/*     */   private static class SelectBindingHelper
/*     */     implements InvalidationListener
/*     */   {
/*     */     private final Binding<?> binding;
/*     */     private final String[] propertyNames;
/*     */     private final ObservableValue<?>[] properties;
/*     */     private final PropertyReference<?>[] propRefs;
/*     */     private final WeakInvalidationListener observer;
/*     */     private ObservableList<ObservableValue<?>> dependencies;
/*     */ 
/*     */     private SelectBindingHelper(Binding<?> paramBinding, ObservableValue<?> paramObservableValue, String[] paramArrayOfString)
/*     */     {
/* 383 */       if (paramObservableValue == null)
/* 384 */         throw new NullPointerException("Must specify the root");
/* 385 */       if (paramArrayOfString == null) {
/* 386 */         paramArrayOfString = new String[0];
/*     */       }
/*     */ 
/* 389 */       this.binding = paramBinding;
/*     */ 
/* 391 */       int i = paramArrayOfString.length;
/* 392 */       for (int j = 0; j < i; j++) {
/* 393 */         if (paramArrayOfString[j] == null) {
/* 394 */           throw new NullPointerException("all steps must be specified");
/*     */         }
/*     */       }
/*     */ 
/* 398 */       this.propertyNames = new String[i];
/* 399 */       System.arraycopy(paramArrayOfString, 0, this.propertyNames, 0, i);
/* 400 */       this.properties = new ObservableValue[i + 1];
/* 401 */       this.properties[0] = paramObservableValue;
/* 402 */       this.propRefs = new PropertyReference[i];
/* 403 */       this.observer = new WeakInvalidationListener(this);
/* 404 */       paramObservableValue.addListener(this.observer);
/*     */     }
/*     */ 
/*     */     public void invalidated(Observable paramObservable)
/*     */     {
/* 409 */       this.binding.invalidate();
/*     */     }
/*     */ 
/*     */     private ObservableValue<?> getObservableValue()
/*     */     {
/* 415 */       int i = this.properties.length;
/* 416 */       for (int j = 0; j < i - 1; j++) {
/* 417 */         Object localObject = this.properties[j].getValue();
/*     */         try {
/* 419 */           if ((this.propRefs[j] == null) || (!localObject.getClass().equals(this.propRefs[j].getContainingClass())))
/*     */           {
/* 422 */             this.propRefs[j] = new PropertyReference(localObject.getClass(), this.propertyNames[j]);
/*     */           }
/*     */ 
/* 425 */           this.properties[(j + 1)] = this.propRefs[j].getProperty(localObject);
/*     */         } catch (RuntimeException localRuntimeException) {
/* 427 */           updateDependencies();
/* 428 */           return null;
/*     */         }
/* 430 */         this.properties[(j + 1)].addListener(this.observer);
/*     */       }
/* 432 */       updateDependencies();
/* 433 */       return this.properties[(i - 1)];
/*     */     }
/*     */ 
/*     */     private void unregisterListener() {
/* 437 */       int i = this.properties.length;
/* 438 */       for (int j = 1; (j < i) && 
/* 439 */         (this.properties[j] != null); j++)
/*     */       {
/* 442 */         this.properties[j].removeListener(this.observer);
/* 443 */         this.properties[j] = null;
/*     */       }
/* 445 */       updateDependencies();
/*     */     }
/*     */ 
/*     */     private void updateDependencies() {
/* 449 */       if (this.dependencies != null) {
/* 450 */         this.dependencies.clear();
/* 451 */         int i = this.properties.length;
/* 452 */         for (int j = 0; (j < i) && 
/* 453 */           (this.properties[j] != null); j++)
/*     */         {
/* 456 */           this.dependencies.add(this.properties[j]);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     public ObservableList<ObservableValue<?>> getDependencies()
/*     */     {
/* 463 */       if (this.dependencies == null) {
/* 464 */         this.dependencies = FXCollections.observableArrayList();
/* 465 */         updateDependencies();
/*     */       }
/*     */ 
/* 468 */       return FXCollections.unmodifiableObservableList(this.dependencies);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class AsString extends StringBinding
/*     */   {
/*     */     private final SelectBinding.SelectBindingHelper helper;
/*     */ 
/*     */     public AsString(ObservableValue<?> paramObservableValue, String[] paramArrayOfString)
/*     */     {
/* 342 */       this.helper = new SelectBinding.SelectBindingHelper(this, paramObservableValue, paramArrayOfString, null);
/*     */     }
/*     */ 
/*     */     public void dispose()
/*     */     {
/* 347 */       this.helper.unregisterListener();
/*     */     }
/*     */ 
/*     */     protected void onInvalidating()
/*     */     {
/* 352 */       this.helper.unregisterListener();
/*     */     }
/*     */ 
/*     */     protected String computeValue()
/*     */     {
/*     */       try {
/* 358 */         return (String)this.helper.getObservableValue().getValue(); } catch (RuntimeException localRuntimeException) {
/*     */       }
/* 360 */       return null;
/*     */     }
/*     */ 
/*     */     public ObservableList<ObservableValue<?>> getDependencies()
/*     */     {
/* 367 */       return this.helper.getDependencies();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class AsLong extends LongBinding
/*     */   {
/*     */     private static final long DEFAULT_VALUE = 0L;
/*     */     private final SelectBinding.SelectBindingHelper helper;
/*     */ 
/*     */     public AsLong(ObservableValue<?> paramObservableValue, String[] paramArrayOfString)
/*     */     {
/* 297 */       this.helper = new SelectBinding.SelectBindingHelper(this, paramObservableValue, paramArrayOfString, null);
/*     */     }
/*     */ 
/*     */     public void dispose()
/*     */     {
/* 302 */       this.helper.unregisterListener();
/*     */     }
/*     */ 
/*     */     protected void onInvalidating()
/*     */     {
/* 307 */       this.helper.unregisterListener();
/*     */     }
/*     */ 
/*     */     protected long computeValue()
/*     */     {
/* 312 */       ObservableValue localObservableValue = this.helper.getObservableValue();
/* 313 */       if (localObservableValue == null)
/* 314 */         return 0L;
/*     */       try
/*     */       {
/* 317 */         return ((ObservableLongValue)localObservableValue).get();
/*     */       }
/*     */       catch (ClassCastException localClassCastException1)
/*     */       {
/*     */         try {
/* 322 */           return ((Number)localObservableValue.getValue()).longValue(); } catch (ClassCastException localClassCastException2) {
/*     */         }
/*     */       }
/* 325 */       return 0L;
/*     */     }
/*     */ 
/*     */     public ObservableList<ObservableValue<?>> getDependencies()
/*     */     {
/* 332 */       return this.helper.getDependencies();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class AsInteger extends IntegerBinding
/*     */   {
/*     */     private static final int DEFAULT_VALUE = 0;
/*     */     private final SelectBinding.SelectBindingHelper helper;
/*     */ 
/*     */     public AsInteger(ObservableValue<?> paramObservableValue, String[] paramArrayOfString)
/*     */     {
/* 250 */       this.helper = new SelectBinding.SelectBindingHelper(this, paramObservableValue, paramArrayOfString, null);
/*     */     }
/*     */ 
/*     */     public void dispose()
/*     */     {
/* 255 */       this.helper.unregisterListener();
/*     */     }
/*     */ 
/*     */     protected void onInvalidating()
/*     */     {
/* 260 */       this.helper.unregisterListener();
/*     */     }
/*     */ 
/*     */     protected int computeValue()
/*     */     {
/* 265 */       ObservableValue localObservableValue = this.helper.getObservableValue();
/* 266 */       if (localObservableValue == null)
/* 267 */         return 0;
/*     */       try
/*     */       {
/* 270 */         return ((ObservableIntegerValue)localObservableValue).get();
/*     */       }
/*     */       catch (ClassCastException localClassCastException1)
/*     */       {
/*     */         try {
/* 275 */           return ((Number)localObservableValue.getValue()).intValue(); } catch (ClassCastException localClassCastException2) {
/*     */         }
/*     */       }
/* 278 */       return 0;
/*     */     }
/*     */ 
/*     */     public ObservableList<ObservableValue<?>> getDependencies()
/*     */     {
/* 285 */       return this.helper.getDependencies();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class AsFloat extends FloatBinding
/*     */   {
/*     */     private static final float DEFAULT_VALUE = 0.0F;
/*     */     private final SelectBinding.SelectBindingHelper helper;
/*     */ 
/*     */     public AsFloat(ObservableValue<?> paramObservableValue, String[] paramArrayOfString)
/*     */     {
/* 203 */       this.helper = new SelectBinding.SelectBindingHelper(this, paramObservableValue, paramArrayOfString, null);
/*     */     }
/*     */ 
/*     */     public void dispose()
/*     */     {
/* 208 */       this.helper.unregisterListener();
/*     */     }
/*     */ 
/*     */     protected void onInvalidating()
/*     */     {
/* 213 */       this.helper.unregisterListener();
/*     */     }
/*     */ 
/*     */     protected float computeValue()
/*     */     {
/* 218 */       ObservableValue localObservableValue = this.helper.getObservableValue();
/* 219 */       if (localObservableValue == null)
/* 220 */         return 0.0F;
/*     */       try
/*     */       {
/* 223 */         return ((ObservableFloatValue)localObservableValue).get();
/*     */       }
/*     */       catch (ClassCastException localClassCastException1)
/*     */       {
/*     */         try {
/* 228 */           return ((Number)localObservableValue.getValue()).floatValue(); } catch (ClassCastException localClassCastException2) {
/*     */         }
/*     */       }
/* 231 */       return 0.0F;
/*     */     }
/*     */ 
/*     */     public ObservableList<ObservableValue<?>> getDependencies()
/*     */     {
/* 238 */       return this.helper.getDependencies();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class AsDouble extends DoubleBinding
/*     */   {
/*     */     private static final double DEFAULT_VALUE = 0.0D;
/*     */     private final SelectBinding.SelectBindingHelper helper;
/*     */ 
/*     */     public AsDouble(ObservableValue<?> paramObservableValue, String[] paramArrayOfString)
/*     */     {
/* 156 */       this.helper = new SelectBinding.SelectBindingHelper(this, paramObservableValue, paramArrayOfString, null);
/*     */     }
/*     */ 
/*     */     public void dispose()
/*     */     {
/* 161 */       this.helper.unregisterListener();
/*     */     }
/*     */ 
/*     */     protected void onInvalidating()
/*     */     {
/* 166 */       this.helper.unregisterListener();
/*     */     }
/*     */ 
/*     */     protected double computeValue()
/*     */     {
/* 171 */       ObservableValue localObservableValue = this.helper.getObservableValue();
/* 172 */       if (localObservableValue == null)
/* 173 */         return 0.0D;
/*     */       try
/*     */       {
/* 176 */         return ((ObservableDoubleValue)localObservableValue).get();
/*     */       }
/*     */       catch (ClassCastException localClassCastException1)
/*     */       {
/*     */         try {
/* 181 */           return ((Number)localObservableValue.getValue()).doubleValue(); } catch (ClassCastException localClassCastException2) {
/*     */         }
/*     */       }
/* 184 */       return 0.0D;
/*     */     }
/*     */ 
/*     */     public ObservableList<ObservableValue<?>> getDependencies()
/*     */     {
/* 191 */       return this.helper.getDependencies();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class AsBoolean extends BooleanBinding
/*     */   {
/*     */     private static final boolean DEFAULT_VALUE = false;
/*     */     private final SelectBinding.SelectBindingHelper helper;
/*     */ 
/*     */     public AsBoolean(ObservableValue<?> paramObservableValue, String[] paramArrayOfString)
/*     */     {
/* 110 */       this.helper = new SelectBinding.SelectBindingHelper(this, paramObservableValue, paramArrayOfString, null);
/*     */     }
/*     */ 
/*     */     public void dispose()
/*     */     {
/* 115 */       this.helper.unregisterListener();
/*     */     }
/*     */ 
/*     */     protected void onInvalidating()
/*     */     {
/* 120 */       this.helper.unregisterListener();
/*     */     }
/*     */ 
/*     */     protected boolean computeValue()
/*     */     {
/* 125 */       ObservableValue localObservableValue = this.helper.getObservableValue();
/* 126 */       if (localObservableValue == null)
/* 127 */         return false;
/*     */       try
/*     */       {
/* 130 */         return ((ObservableBooleanValue)localObservableValue).get();
/*     */       }
/*     */       catch (ClassCastException localClassCastException1)
/*     */       {
/*     */         try {
/* 135 */           return ((Boolean)localObservableValue.getValue()).booleanValue(); } catch (ClassCastException localClassCastException2) {  }
/*     */       }
/* 137 */       return false;
/*     */     }
/*     */ 
/*     */     public ObservableList<ObservableValue<?>> getDependencies()
/*     */     {
/* 144 */       return this.helper.getDependencies();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class AsObject<T> extends ObjectBinding<T>
/*     */   {
/*     */     private final SelectBinding.SelectBindingHelper helper;
/*     */ 
/*     */     public AsObject(ObservableValue<?> paramObservableValue, String[] paramArrayOfString)
/*     */     {
/*  71 */       this.helper = new SelectBinding.SelectBindingHelper(this, paramObservableValue, paramArrayOfString, null);
/*     */     }
/*     */ 
/*     */     public void dispose()
/*     */     {
/*  76 */       this.helper.unregisterListener();
/*     */     }
/*     */ 
/*     */     protected void onInvalidating()
/*     */     {
/*  81 */       this.helper.unregisterListener();
/*     */     }
/*     */ 
/*     */     protected T computeValue()
/*     */     {
/*     */       try
/*     */       {
/*  88 */         return this.helper.getObservableValue().getValue(); } catch (RuntimeException localRuntimeException) {
/*     */       }
/*  90 */       return null;
/*     */     }
/*     */ 
/*     */     public ObservableList<ObservableValue<?>> getDependencies()
/*     */     {
/*  98 */       return this.helper.getDependencies();
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.binding.SelectBinding
 * JD-Core Version:    0.6.2
 */