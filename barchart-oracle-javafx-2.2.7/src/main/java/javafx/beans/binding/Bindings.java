/*      */ package javafx.beans.binding;
/*      */ 
/*      */ import com.sun.javafx.binding.BidirectionalBinding;
/*      */ import com.sun.javafx.binding.BidirectionalContentBinding;
/*      */ import com.sun.javafx.binding.ContentBinding;
/*      */ import com.sun.javafx.binding.DoubleConstant;
/*      */ import com.sun.javafx.binding.FloatConstant;
/*      */ import com.sun.javafx.binding.IntegerConstant;
/*      */ import com.sun.javafx.binding.LongConstant;
/*      */ import com.sun.javafx.binding.ObjectConstant;
/*      */ import com.sun.javafx.binding.SelectBinding.AsBoolean;
/*      */ import com.sun.javafx.binding.SelectBinding.AsDouble;
/*      */ import com.sun.javafx.binding.SelectBinding.AsFloat;
/*      */ import com.sun.javafx.binding.SelectBinding.AsInteger;
/*      */ import com.sun.javafx.binding.SelectBinding.AsLong;
/*      */ import com.sun.javafx.binding.SelectBinding.AsObject;
/*      */ import com.sun.javafx.binding.SelectBinding.AsString;
/*      */ import com.sun.javafx.binding.StringConstant;
/*      */ import com.sun.javafx.binding.StringFormatter;
/*      */ import com.sun.javafx.collections.ImmutableObservableList;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.text.Format;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.concurrent.Callable;
/*      */ import javafx.beans.InvalidationListener;
/*      */ import javafx.beans.Observable;
/*      */ import javafx.beans.property.Property;
/*      */ import javafx.beans.value.ObservableBooleanValue;
/*      */ import javafx.beans.value.ObservableDoubleValue;
/*      */ import javafx.beans.value.ObservableFloatValue;
/*      */ import javafx.beans.value.ObservableIntegerValue;
/*      */ import javafx.beans.value.ObservableLongValue;
/*      */ import javafx.beans.value.ObservableNumberValue;
/*      */ import javafx.beans.value.ObservableObjectValue;
/*      */ import javafx.beans.value.ObservableStringValue;
/*      */ import javafx.beans.value.ObservableValue;
/*      */ import javafx.collections.FXCollections;
/*      */ import javafx.collections.ObservableList;
/*      */ import javafx.collections.ObservableMap;
/*      */ import javafx.collections.ObservableSet;
/*      */ import javafx.util.StringConverter;
/*      */ 
/*      */ public final class Bindings
/*      */ {
/*      */   public static BooleanBinding createBooleanBinding(final Callable<Boolean> paramCallable, Observable[] paramArrayOfObservable)
/*      */   {
/*  102 */     return new BooleanBinding()
/*      */     {
/*      */       protected boolean computeValue()
/*      */       {
/*      */         try
/*      */         {
/*  110 */           return ((Boolean)paramCallable.call()).booleanValue(); } catch (Exception localException) {
/*      */         }
/*  112 */         return false;
/*      */       }
/*      */ 
/*      */       public void dispose()
/*      */       {
/*  118 */         super.unbind(this.val$dependencies);
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/*  124 */         return this.val$dependencies.length == 1 ? FXCollections.singletonObservableList(this.val$dependencies[0]) : (this.val$dependencies == null) || (this.val$dependencies.length == 0) ? FXCollections.emptyObservableList() : new ImmutableObservableList(this.val$dependencies);
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static DoubleBinding createDoubleBinding(final Callable<Double> paramCallable, Observable[] paramArrayOfObservable)
/*      */   {
/*  141 */     return new DoubleBinding()
/*      */     {
/*      */       protected double computeValue()
/*      */       {
/*      */         try
/*      */         {
/*  149 */           return ((Double)paramCallable.call()).doubleValue(); } catch (Exception localException) {
/*      */         }
/*  151 */         return 0.0D;
/*      */       }
/*      */ 
/*      */       public void dispose()
/*      */       {
/*  157 */         super.unbind(this.val$dependencies);
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/*  163 */         return this.val$dependencies.length == 1 ? FXCollections.singletonObservableList(this.val$dependencies[0]) : (this.val$dependencies == null) || (this.val$dependencies.length == 0) ? FXCollections.emptyObservableList() : new ImmutableObservableList(this.val$dependencies);
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static FloatBinding createFloatBinding(final Callable<Float> paramCallable, Observable[] paramArrayOfObservable)
/*      */   {
/*  180 */     return new FloatBinding()
/*      */     {
/*      */       protected float computeValue()
/*      */       {
/*      */         try
/*      */         {
/*  188 */           return ((Float)paramCallable.call()).floatValue(); } catch (Exception localException) {
/*      */         }
/*  190 */         return 0.0F;
/*      */       }
/*      */ 
/*      */       public void dispose()
/*      */       {
/*  196 */         super.unbind(this.val$dependencies);
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/*  202 */         return this.val$dependencies.length == 1 ? FXCollections.singletonObservableList(this.val$dependencies[0]) : (this.val$dependencies == null) || (this.val$dependencies.length == 0) ? FXCollections.emptyObservableList() : new ImmutableObservableList(this.val$dependencies);
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static IntegerBinding createIntegerBinding(final Callable<Integer> paramCallable, Observable[] paramArrayOfObservable)
/*      */   {
/*  219 */     return new IntegerBinding()
/*      */     {
/*      */       protected int computeValue()
/*      */       {
/*      */         try
/*      */         {
/*  227 */           return ((Integer)paramCallable.call()).intValue(); } catch (Exception localException) {
/*      */         }
/*  229 */         return 0;
/*      */       }
/*      */ 
/*      */       public void dispose()
/*      */       {
/*  235 */         super.unbind(this.val$dependencies);
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/*  241 */         return this.val$dependencies.length == 1 ? FXCollections.singletonObservableList(this.val$dependencies[0]) : (this.val$dependencies == null) || (this.val$dependencies.length == 0) ? FXCollections.emptyObservableList() : new ImmutableObservableList(this.val$dependencies);
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static LongBinding createLongBinding(final Callable<Long> paramCallable, Observable[] paramArrayOfObservable)
/*      */   {
/*  258 */     return new LongBinding()
/*      */     {
/*      */       protected long computeValue()
/*      */       {
/*      */         try
/*      */         {
/*  266 */           return ((Long)paramCallable.call()).longValue(); } catch (Exception localException) {
/*      */         }
/*  268 */         return 0L;
/*      */       }
/*      */ 
/*      */       public void dispose()
/*      */       {
/*  274 */         super.unbind(this.val$dependencies);
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/*  280 */         return this.val$dependencies.length == 1 ? FXCollections.singletonObservableList(this.val$dependencies[0]) : (this.val$dependencies == null) || (this.val$dependencies.length == 0) ? FXCollections.emptyObservableList() : new ImmutableObservableList(this.val$dependencies);
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static <T> ObjectBinding<T> createObjectBinding(final Callable<T> paramCallable, Observable[] paramArrayOfObservable)
/*      */   {
/*  297 */     return new ObjectBinding()
/*      */     {
/*      */       protected T computeValue()
/*      */       {
/*      */         try
/*      */         {
/*  305 */           return paramCallable.call(); } catch (Exception localException) {
/*      */         }
/*  307 */         return null;
/*      */       }
/*      */ 
/*      */       public void dispose()
/*      */       {
/*  313 */         super.unbind(this.val$dependencies);
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/*  319 */         return this.val$dependencies.length == 1 ? FXCollections.singletonObservableList(this.val$dependencies[0]) : (this.val$dependencies == null) || (this.val$dependencies.length == 0) ? FXCollections.emptyObservableList() : new ImmutableObservableList(this.val$dependencies);
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static StringBinding createStringBinding(final Callable<String> paramCallable, Observable[] paramArrayOfObservable)
/*      */   {
/*  336 */     return new StringBinding()
/*      */     {
/*      */       protected String computeValue()
/*      */       {
/*      */         try
/*      */         {
/*  344 */           return (String)paramCallable.call(); } catch (Exception localException) {
/*      */         }
/*  346 */         return "";
/*      */       }
/*      */ 
/*      */       public void dispose()
/*      */       {
/*  352 */         super.unbind(this.val$dependencies);
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/*  358 */         return this.val$dependencies.length == 1 ? FXCollections.singletonObservableList(this.val$dependencies[0]) : (this.val$dependencies == null) || (this.val$dependencies.length == 0) ? FXCollections.emptyObservableList() : new ImmutableObservableList(this.val$dependencies);
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static <T> ObjectBinding<T> select(ObservableValue<?> paramObservableValue, String[] paramArrayOfString)
/*      */   {
/*  386 */     return new SelectBinding.AsObject(paramObservableValue, paramArrayOfString);
/*      */   }
/*      */ 
/*      */   public static DoubleBinding selectDouble(ObservableValue<?> paramObservableValue, String[] paramArrayOfString)
/*      */   {
/*  404 */     return new SelectBinding.AsDouble(paramObservableValue, paramArrayOfString);
/*      */   }
/*      */ 
/*      */   public static FloatBinding selectFloat(ObservableValue<?> paramObservableValue, String[] paramArrayOfString)
/*      */   {
/*  422 */     return new SelectBinding.AsFloat(paramObservableValue, paramArrayOfString);
/*      */   }
/*      */ 
/*      */   public static IntegerBinding selectInteger(ObservableValue<?> paramObservableValue, String[] paramArrayOfString)
/*      */   {
/*  440 */     return new SelectBinding.AsInteger(paramObservableValue, paramArrayOfString);
/*      */   }
/*      */ 
/*      */   public static LongBinding selectLong(ObservableValue<?> paramObservableValue, String[] paramArrayOfString)
/*      */   {
/*  458 */     return new SelectBinding.AsLong(paramObservableValue, paramArrayOfString);
/*      */   }
/*      */ 
/*      */   public static BooleanBinding selectBoolean(ObservableValue<?> paramObservableValue, String[] paramArrayOfString)
/*      */   {
/*  476 */     return new SelectBinding.AsBoolean(paramObservableValue, paramArrayOfString);
/*      */   }
/*      */ 
/*      */   public static StringBinding selectString(ObservableValue<?> paramObservableValue, String[] paramArrayOfString)
/*      */   {
/*  494 */     return new SelectBinding.AsString(paramObservableValue, paramArrayOfString);
/*      */   }
/*      */ 
/*      */   public static When when(ObservableBooleanValue paramObservableBooleanValue)
/*      */   {
/*  508 */     return new When(paramObservableBooleanValue);
/*      */   }
/*      */ 
/*      */   public static <T> void bindBidirectional(Property<T> paramProperty1, Property<T> paramProperty2)
/*      */   {
/*  546 */     BidirectionalBinding.bind(paramProperty1, paramProperty2);
/*      */   }
/*      */ 
/*      */   public static <T> void unbindBidirectional(Property<T> paramProperty1, Property<T> paramProperty2)
/*      */   {
/*  565 */     BidirectionalBinding.unbind(paramProperty1, paramProperty2);
/*      */   }
/*      */ 
/*      */   public static void unbindBidirectional(Object paramObject1, Object paramObject2)
/*      */   {
/*  583 */     BidirectionalBinding.unbind(paramObject1, paramObject2);
/*      */   }
/*      */ 
/*      */   public static void bindBidirectional(Property<String> paramProperty, Property<?> paramProperty1, Format paramFormat)
/*      */   {
/*  619 */     BidirectionalBinding.bind(paramProperty, paramProperty1, paramFormat);
/*      */   }
/*      */ 
/*      */   public static <T> void bindBidirectional(Property<String> paramProperty, Property<T> paramProperty1, StringConverter<T> paramStringConverter)
/*      */   {
/*  655 */     BidirectionalBinding.bind(paramProperty, paramProperty1, paramStringConverter);
/*      */   }
/*      */ 
/*      */   public static <E> void bindContentBidirectional(ObservableList<E> paramObservableList1, ObservableList<E> paramObservableList2)
/*      */   {
/*  693 */     BidirectionalContentBinding.bind(paramObservableList1, paramObservableList2);
/*      */   }
/*      */ 
/*      */   public static <E> void bindContentBidirectional(ObservableSet<E> paramObservableSet1, ObservableSet<E> paramObservableSet2)
/*      */   {
/*  731 */     BidirectionalContentBinding.bind(paramObservableSet1, paramObservableSet2);
/*      */   }
/*      */ 
/*      */   public static <K, V> void bindContentBidirectional(ObservableMap<K, V> paramObservableMap1, ObservableMap<K, V> paramObservableMap2)
/*      */   {
/*  767 */     BidirectionalContentBinding.bind(paramObservableMap1, paramObservableMap2);
/*      */   }
/*      */ 
/*      */   public static void unbindContentBidirectional(Object paramObject1, Object paramObject2)
/*      */   {
/*  779 */     BidirectionalContentBinding.unbind(paramObject1, paramObject2);
/*      */   }
/*      */ 
/*      */   public static <E> void bindContent(List<E> paramList, ObservableList<? extends E> paramObservableList)
/*      */   {
/*  801 */     ContentBinding.bind(paramList, paramObservableList);
/*      */   }
/*      */ 
/*      */   public static <E> void bindContent(Set<E> paramSet, ObservableSet<? extends E> paramObservableSet)
/*      */   {
/*  827 */     ContentBinding.bind(paramSet, paramObservableSet);
/*      */   }
/*      */ 
/*      */   public static <K, V> void bindContent(Map<K, V> paramMap, ObservableMap<? extends K, ? extends V> paramObservableMap)
/*      */   {
/*  855 */     ContentBinding.bind(paramMap, paramObservableMap);
/*      */   }
/*      */ 
/*      */   public static void unbindContent(Object paramObject1, Object paramObject2)
/*      */   {
/*  871 */     ContentBinding.unbind(paramObject1, paramObject2);
/*      */   }
/*      */ 
/*      */   public static NumberBinding negate(ObservableNumberValue paramObservableNumberValue)
/*      */   {
/*  890 */     if (paramObservableNumberValue == null) {
/*  891 */       throw new NullPointerException("Operand cannot be null.");
/*      */     }
/*      */ 
/*  894 */     if ((paramObservableNumberValue instanceof ObservableDoubleValue))
/*  895 */       return new DoubleBinding()
/*      */       {
/*      */         public void dispose()
/*      */         {
/*  902 */           super.unbind(new Observable[] { this.val$value });
/*      */         }
/*      */ 
/*      */         protected double computeValue()
/*      */         {
/*  907 */           return -this.val$value.doubleValue();
/*      */         }
/*      */ 
/*      */         public ObservableList<?> getDependencies()
/*      */         {
/*  913 */           return FXCollections.singletonObservableList(this.val$value);
/*      */         }
/*      */       };
/*  916 */     if ((paramObservableNumberValue instanceof ObservableFloatValue))
/*  917 */       return new FloatBinding()
/*      */       {
/*      */         public void dispose()
/*      */         {
/*  924 */           super.unbind(new Observable[] { this.val$value });
/*      */         }
/*      */ 
/*      */         protected float computeValue()
/*      */         {
/*  929 */           return -this.val$value.floatValue();
/*      */         }
/*      */ 
/*      */         public ObservableList<?> getDependencies()
/*      */         {
/*  935 */           return FXCollections.singletonObservableList(this.val$value);
/*      */         }
/*      */       };
/*  938 */     if ((paramObservableNumberValue instanceof ObservableLongValue)) {
/*  939 */       return new LongBinding()
/*      */       {
/*      */         public void dispose()
/*      */         {
/*  946 */           super.unbind(new Observable[] { this.val$value });
/*      */         }
/*      */ 
/*      */         protected long computeValue()
/*      */         {
/*  951 */           return -this.val$value.longValue();
/*      */         }
/*      */ 
/*      */         public ObservableList<?> getDependencies()
/*      */         {
/*  957 */           return FXCollections.singletonObservableList(this.val$value);
/*      */         }
/*      */       };
/*      */     }
/*  961 */     return new IntegerBinding()
/*      */     {
/*      */       public void dispose()
/*      */       {
/*  968 */         super.unbind(new Observable[] { this.val$value });
/*      */       }
/*      */ 
/*      */       protected int computeValue()
/*      */       {
/*  973 */         return -this.val$value.intValue();
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/*  979 */         return FXCollections.singletonObservableList(this.val$value);
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   private static NumberBinding add(final ObservableNumberValue paramObservableNumberValue1, final ObservableNumberValue paramObservableNumberValue2, Observable[] paramArrayOfObservable)
/*      */   {
/*  989 */     if ((paramObservableNumberValue1 == null) || (paramObservableNumberValue2 == null)) {
/*  990 */       throw new NullPointerException("Operands cannot be null.");
/*      */     }
/*  992 */     assert ((paramArrayOfObservable != null) && (paramArrayOfObservable.length > 0));
/*      */ 
/*  994 */     if (((paramObservableNumberValue1 instanceof ObservableDoubleValue)) || ((paramObservableNumberValue2 instanceof ObservableDoubleValue))) {
/*  995 */       return new DoubleBinding()
/*      */       {
/*      */         public void dispose()
/*      */         {
/* 1002 */           super.unbind(this.val$dependencies);
/*      */         }
/*      */ 
/*      */         protected double computeValue()
/*      */         {
/* 1007 */           return paramObservableNumberValue1.doubleValue() + paramObservableNumberValue2.doubleValue();
/*      */         }
/*      */ 
/*      */         public ObservableList<?> getDependencies()
/*      */         {
/* 1013 */           return this.val$dependencies.length == 1 ? FXCollections.singletonObservableList(this.val$dependencies[0]) : new ImmutableObservableList(this.val$dependencies);
/*      */         }
/*      */       };
/*      */     }
/*      */ 
/* 1018 */     if (((paramObservableNumberValue1 instanceof ObservableFloatValue)) || ((paramObservableNumberValue2 instanceof ObservableFloatValue))) {
/* 1019 */       return new FloatBinding()
/*      */       {
/*      */         public void dispose()
/*      */         {
/* 1026 */           super.unbind(this.val$dependencies);
/*      */         }
/*      */ 
/*      */         protected float computeValue()
/*      */         {
/* 1031 */           return paramObservableNumberValue1.floatValue() + paramObservableNumberValue2.floatValue();
/*      */         }
/*      */ 
/*      */         public ObservableList<?> getDependencies()
/*      */         {
/* 1037 */           return this.val$dependencies.length == 1 ? FXCollections.singletonObservableList(this.val$dependencies[0]) : new ImmutableObservableList(this.val$dependencies);
/*      */         }
/*      */       };
/*      */     }
/*      */ 
/* 1042 */     if (((paramObservableNumberValue1 instanceof ObservableLongValue)) || ((paramObservableNumberValue2 instanceof ObservableLongValue))) {
/* 1043 */       return new LongBinding()
/*      */       {
/*      */         public void dispose()
/*      */         {
/* 1050 */           super.unbind(this.val$dependencies);
/*      */         }
/*      */ 
/*      */         protected long computeValue()
/*      */         {
/* 1055 */           return paramObservableNumberValue1.longValue() + paramObservableNumberValue2.longValue();
/*      */         }
/*      */ 
/*      */         public ObservableList<?> getDependencies()
/*      */         {
/* 1061 */           return this.val$dependencies.length == 1 ? FXCollections.singletonObservableList(this.val$dependencies[0]) : new ImmutableObservableList(this.val$dependencies);
/*      */         }
/*      */ 
/*      */       };
/*      */     }
/*      */ 
/* 1067 */     return new IntegerBinding()
/*      */     {
/*      */       public void dispose()
/*      */       {
/* 1074 */         super.unbind(this.val$dependencies);
/*      */       }
/*      */ 
/*      */       protected int computeValue()
/*      */       {
/* 1079 */         return paramObservableNumberValue1.intValue() + paramObservableNumberValue2.intValue();
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/* 1085 */         return this.val$dependencies.length == 1 ? FXCollections.singletonObservableList(this.val$dependencies[0]) : new ImmutableObservableList(this.val$dependencies);
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static NumberBinding add(ObservableNumberValue paramObservableNumberValue1, ObservableNumberValue paramObservableNumberValue2)
/*      */   {
/* 1107 */     return add(paramObservableNumberValue1, paramObservableNumberValue2, new Observable[] { paramObservableNumberValue1, paramObservableNumberValue2 });
/*      */   }
/*      */ 
/*      */   public static DoubleBinding add(ObservableNumberValue paramObservableNumberValue, double paramDouble)
/*      */   {
/* 1124 */     return (DoubleBinding)add(paramObservableNumberValue, DoubleConstant.valueOf(paramDouble), new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static DoubleBinding add(double paramDouble, ObservableNumberValue paramObservableNumberValue)
/*      */   {
/* 1141 */     return (DoubleBinding)add(DoubleConstant.valueOf(paramDouble), paramObservableNumberValue, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static NumberBinding add(ObservableNumberValue paramObservableNumberValue, float paramFloat)
/*      */   {
/* 1158 */     return add(paramObservableNumberValue, FloatConstant.valueOf(paramFloat), new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static NumberBinding add(float paramFloat, ObservableNumberValue paramObservableNumberValue)
/*      */   {
/* 1175 */     return add(FloatConstant.valueOf(paramFloat), paramObservableNumberValue, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static NumberBinding add(ObservableNumberValue paramObservableNumberValue, long paramLong)
/*      */   {
/* 1192 */     return add(paramObservableNumberValue, LongConstant.valueOf(paramLong), new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static NumberBinding add(long paramLong, ObservableNumberValue paramObservableNumberValue)
/*      */   {
/* 1209 */     return add(LongConstant.valueOf(paramLong), paramObservableNumberValue, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static NumberBinding add(ObservableNumberValue paramObservableNumberValue, int paramInt)
/*      */   {
/* 1226 */     return add(paramObservableNumberValue, IntegerConstant.valueOf(paramInt), new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static NumberBinding add(int paramInt, ObservableNumberValue paramObservableNumberValue)
/*      */   {
/* 1243 */     return add(IntegerConstant.valueOf(paramInt), paramObservableNumberValue, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   private static NumberBinding subtract(final ObservableNumberValue paramObservableNumberValue1, final ObservableNumberValue paramObservableNumberValue2, Observable[] paramArrayOfObservable)
/*      */   {
/* 1250 */     if ((paramObservableNumberValue1 == null) || (paramObservableNumberValue2 == null)) {
/* 1251 */       throw new NullPointerException("Operands cannot be null.");
/*      */     }
/* 1253 */     assert ((paramArrayOfObservable != null) && (paramArrayOfObservable.length > 0));
/*      */ 
/* 1255 */     if (((paramObservableNumberValue1 instanceof ObservableDoubleValue)) || ((paramObservableNumberValue2 instanceof ObservableDoubleValue))) {
/* 1256 */       return new DoubleBinding()
/*      */       {
/*      */         public void dispose()
/*      */         {
/* 1263 */           super.unbind(this.val$dependencies);
/*      */         }
/*      */ 
/*      */         protected double computeValue()
/*      */         {
/* 1268 */           return paramObservableNumberValue1.doubleValue() - paramObservableNumberValue2.doubleValue();
/*      */         }
/*      */ 
/*      */         public ObservableList<?> getDependencies()
/*      */         {
/* 1274 */           return this.val$dependencies.length == 1 ? FXCollections.singletonObservableList(this.val$dependencies[0]) : new ImmutableObservableList(this.val$dependencies);
/*      */         }
/*      */       };
/*      */     }
/*      */ 
/* 1279 */     if (((paramObservableNumberValue1 instanceof ObservableFloatValue)) || ((paramObservableNumberValue2 instanceof ObservableFloatValue))) {
/* 1280 */       return new FloatBinding()
/*      */       {
/*      */         public void dispose()
/*      */         {
/* 1287 */           super.unbind(this.val$dependencies);
/*      */         }
/*      */ 
/*      */         protected float computeValue()
/*      */         {
/* 1292 */           return paramObservableNumberValue1.floatValue() - paramObservableNumberValue2.floatValue();
/*      */         }
/*      */ 
/*      */         public ObservableList<?> getDependencies()
/*      */         {
/* 1298 */           return this.val$dependencies.length == 1 ? FXCollections.singletonObservableList(this.val$dependencies[0]) : new ImmutableObservableList(this.val$dependencies);
/*      */         }
/*      */       };
/*      */     }
/*      */ 
/* 1303 */     if (((paramObservableNumberValue1 instanceof ObservableLongValue)) || ((paramObservableNumberValue2 instanceof ObservableLongValue))) {
/* 1304 */       return new LongBinding()
/*      */       {
/*      */         public void dispose()
/*      */         {
/* 1311 */           super.unbind(this.val$dependencies);
/*      */         }
/*      */ 
/*      */         protected long computeValue()
/*      */         {
/* 1316 */           return paramObservableNumberValue1.longValue() - paramObservableNumberValue2.longValue();
/*      */         }
/*      */ 
/*      */         public ObservableList<?> getDependencies()
/*      */         {
/* 1322 */           return this.val$dependencies.length == 1 ? FXCollections.singletonObservableList(this.val$dependencies[0]) : new ImmutableObservableList(this.val$dependencies);
/*      */         }
/*      */ 
/*      */       };
/*      */     }
/*      */ 
/* 1328 */     return new IntegerBinding()
/*      */     {
/*      */       public void dispose()
/*      */       {
/* 1335 */         super.unbind(this.val$dependencies);
/*      */       }
/*      */ 
/*      */       protected int computeValue()
/*      */       {
/* 1340 */         return paramObservableNumberValue1.intValue() - paramObservableNumberValue2.intValue();
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/* 1346 */         return this.val$dependencies.length == 1 ? FXCollections.singletonObservableList(this.val$dependencies[0]) : new ImmutableObservableList(this.val$dependencies);
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static NumberBinding subtract(ObservableNumberValue paramObservableNumberValue1, ObservableNumberValue paramObservableNumberValue2)
/*      */   {
/* 1368 */     return subtract(paramObservableNumberValue1, paramObservableNumberValue2, new Observable[] { paramObservableNumberValue1, paramObservableNumberValue2 });
/*      */   }
/*      */ 
/*      */   public static DoubleBinding subtract(ObservableNumberValue paramObservableNumberValue, double paramDouble)
/*      */   {
/* 1385 */     return (DoubleBinding)subtract(paramObservableNumberValue, DoubleConstant.valueOf(paramDouble), new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static DoubleBinding subtract(double paramDouble, ObservableNumberValue paramObservableNumberValue)
/*      */   {
/* 1402 */     return (DoubleBinding)subtract(DoubleConstant.valueOf(paramDouble), paramObservableNumberValue, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static NumberBinding subtract(ObservableNumberValue paramObservableNumberValue, float paramFloat)
/*      */   {
/* 1419 */     return subtract(paramObservableNumberValue, FloatConstant.valueOf(paramFloat), new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static NumberBinding subtract(float paramFloat, ObservableNumberValue paramObservableNumberValue)
/*      */   {
/* 1436 */     return subtract(FloatConstant.valueOf(paramFloat), paramObservableNumberValue, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static NumberBinding subtract(ObservableNumberValue paramObservableNumberValue, long paramLong)
/*      */   {
/* 1453 */     return subtract(paramObservableNumberValue, LongConstant.valueOf(paramLong), new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static NumberBinding subtract(long paramLong, ObservableNumberValue paramObservableNumberValue)
/*      */   {
/* 1470 */     return subtract(LongConstant.valueOf(paramLong), paramObservableNumberValue, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static NumberBinding subtract(ObservableNumberValue paramObservableNumberValue, int paramInt)
/*      */   {
/* 1487 */     return subtract(paramObservableNumberValue, IntegerConstant.valueOf(paramInt), new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static NumberBinding subtract(int paramInt, ObservableNumberValue paramObservableNumberValue)
/*      */   {
/* 1504 */     return subtract(IntegerConstant.valueOf(paramInt), paramObservableNumberValue, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   private static NumberBinding multiply(final ObservableNumberValue paramObservableNumberValue1, final ObservableNumberValue paramObservableNumberValue2, Observable[] paramArrayOfObservable)
/*      */   {
/* 1511 */     if ((paramObservableNumberValue1 == null) || (paramObservableNumberValue2 == null)) {
/* 1512 */       throw new NullPointerException("Operands cannot be null.");
/*      */     }
/* 1514 */     assert ((paramArrayOfObservable != null) && (paramArrayOfObservable.length > 0));
/*      */ 
/* 1516 */     if (((paramObservableNumberValue1 instanceof ObservableDoubleValue)) || ((paramObservableNumberValue2 instanceof ObservableDoubleValue))) {
/* 1517 */       return new DoubleBinding()
/*      */       {
/*      */         public void dispose()
/*      */         {
/* 1524 */           super.unbind(this.val$dependencies);
/*      */         }
/*      */ 
/*      */         protected double computeValue()
/*      */         {
/* 1529 */           return paramObservableNumberValue1.doubleValue() * paramObservableNumberValue2.doubleValue();
/*      */         }
/*      */ 
/*      */         public ObservableList<?> getDependencies()
/*      */         {
/* 1535 */           return this.val$dependencies.length == 1 ? FXCollections.singletonObservableList(this.val$dependencies[0]) : new ImmutableObservableList(this.val$dependencies);
/*      */         }
/*      */       };
/*      */     }
/*      */ 
/* 1540 */     if (((paramObservableNumberValue1 instanceof ObservableFloatValue)) || ((paramObservableNumberValue2 instanceof ObservableFloatValue))) {
/* 1541 */       return new FloatBinding()
/*      */       {
/*      */         public void dispose()
/*      */         {
/* 1548 */           super.unbind(this.val$dependencies);
/*      */         }
/*      */ 
/*      */         protected float computeValue()
/*      */         {
/* 1553 */           return paramObservableNumberValue1.floatValue() * paramObservableNumberValue2.floatValue();
/*      */         }
/*      */ 
/*      */         public ObservableList<?> getDependencies()
/*      */         {
/* 1559 */           return this.val$dependencies.length == 1 ? FXCollections.singletonObservableList(this.val$dependencies[0]) : new ImmutableObservableList(this.val$dependencies);
/*      */         }
/*      */       };
/*      */     }
/*      */ 
/* 1564 */     if (((paramObservableNumberValue1 instanceof ObservableLongValue)) || ((paramObservableNumberValue2 instanceof ObservableLongValue))) {
/* 1565 */       return new LongBinding()
/*      */       {
/*      */         public void dispose()
/*      */         {
/* 1572 */           super.unbind(this.val$dependencies);
/*      */         }
/*      */ 
/*      */         protected long computeValue()
/*      */         {
/* 1577 */           return paramObservableNumberValue1.longValue() * paramObservableNumberValue2.longValue();
/*      */         }
/*      */ 
/*      */         public ObservableList<?> getDependencies()
/*      */         {
/* 1583 */           return this.val$dependencies.length == 1 ? FXCollections.singletonObservableList(this.val$dependencies[0]) : new ImmutableObservableList(this.val$dependencies);
/*      */         }
/*      */ 
/*      */       };
/*      */     }
/*      */ 
/* 1589 */     return new IntegerBinding()
/*      */     {
/*      */       public void dispose()
/*      */       {
/* 1596 */         super.unbind(this.val$dependencies);
/*      */       }
/*      */ 
/*      */       protected int computeValue()
/*      */       {
/* 1601 */         return paramObservableNumberValue1.intValue() * paramObservableNumberValue2.intValue();
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/* 1607 */         return this.val$dependencies.length == 1 ? FXCollections.singletonObservableList(this.val$dependencies[0]) : new ImmutableObservableList(this.val$dependencies);
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static NumberBinding multiply(ObservableNumberValue paramObservableNumberValue1, ObservableNumberValue paramObservableNumberValue2)
/*      */   {
/* 1629 */     return multiply(paramObservableNumberValue1, paramObservableNumberValue2, new Observable[] { paramObservableNumberValue1, paramObservableNumberValue2 });
/*      */   }
/*      */ 
/*      */   public static DoubleBinding multiply(ObservableNumberValue paramObservableNumberValue, double paramDouble)
/*      */   {
/* 1646 */     return (DoubleBinding)multiply(paramObservableNumberValue, DoubleConstant.valueOf(paramDouble), new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static DoubleBinding multiply(double paramDouble, ObservableNumberValue paramObservableNumberValue)
/*      */   {
/* 1663 */     return (DoubleBinding)multiply(DoubleConstant.valueOf(paramDouble), paramObservableNumberValue, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static NumberBinding multiply(ObservableNumberValue paramObservableNumberValue, float paramFloat)
/*      */   {
/* 1680 */     return multiply(paramObservableNumberValue, FloatConstant.valueOf(paramFloat), new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static NumberBinding multiply(float paramFloat, ObservableNumberValue paramObservableNumberValue)
/*      */   {
/* 1697 */     return multiply(FloatConstant.valueOf(paramFloat), paramObservableNumberValue, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static NumberBinding multiply(ObservableNumberValue paramObservableNumberValue, long paramLong)
/*      */   {
/* 1714 */     return multiply(paramObservableNumberValue, LongConstant.valueOf(paramLong), new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static NumberBinding multiply(long paramLong, ObservableNumberValue paramObservableNumberValue)
/*      */   {
/* 1731 */     return multiply(LongConstant.valueOf(paramLong), paramObservableNumberValue, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static NumberBinding multiply(ObservableNumberValue paramObservableNumberValue, int paramInt)
/*      */   {
/* 1748 */     return multiply(paramObservableNumberValue, IntegerConstant.valueOf(paramInt), new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static NumberBinding multiply(int paramInt, ObservableNumberValue paramObservableNumberValue)
/*      */   {
/* 1765 */     return multiply(IntegerConstant.valueOf(paramInt), paramObservableNumberValue, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   private static NumberBinding divide(final ObservableNumberValue paramObservableNumberValue1, final ObservableNumberValue paramObservableNumberValue2, Observable[] paramArrayOfObservable)
/*      */   {
/* 1772 */     if ((paramObservableNumberValue1 == null) || (paramObservableNumberValue2 == null)) {
/* 1773 */       throw new NullPointerException("Operands cannot be null.");
/*      */     }
/* 1775 */     assert ((paramArrayOfObservable != null) && (paramArrayOfObservable.length > 0));
/*      */ 
/* 1777 */     if (((paramObservableNumberValue1 instanceof ObservableDoubleValue)) || ((paramObservableNumberValue2 instanceof ObservableDoubleValue))) {
/* 1778 */       return new DoubleBinding()
/*      */       {
/*      */         public void dispose()
/*      */         {
/* 1785 */           super.unbind(this.val$dependencies);
/*      */         }
/*      */ 
/*      */         protected double computeValue()
/*      */         {
/* 1790 */           return paramObservableNumberValue1.doubleValue() / paramObservableNumberValue2.doubleValue();
/*      */         }
/*      */ 
/*      */         public ObservableList<?> getDependencies()
/*      */         {
/* 1796 */           return this.val$dependencies.length == 1 ? FXCollections.singletonObservableList(this.val$dependencies[0]) : new ImmutableObservableList(this.val$dependencies);
/*      */         }
/*      */       };
/*      */     }
/*      */ 
/* 1801 */     if (((paramObservableNumberValue1 instanceof ObservableFloatValue)) || ((paramObservableNumberValue2 instanceof ObservableFloatValue))) {
/* 1802 */       return new FloatBinding()
/*      */       {
/*      */         public void dispose()
/*      */         {
/* 1809 */           super.unbind(this.val$dependencies);
/*      */         }
/*      */ 
/*      */         protected float computeValue()
/*      */         {
/* 1814 */           return paramObservableNumberValue1.floatValue() / paramObservableNumberValue2.floatValue();
/*      */         }
/*      */ 
/*      */         public ObservableList<?> getDependencies()
/*      */         {
/* 1820 */           return this.val$dependencies.length == 1 ? FXCollections.singletonObservableList(this.val$dependencies[0]) : new ImmutableObservableList(this.val$dependencies);
/*      */         }
/*      */       };
/*      */     }
/*      */ 
/* 1825 */     if (((paramObservableNumberValue1 instanceof ObservableLongValue)) || ((paramObservableNumberValue2 instanceof ObservableLongValue))) {
/* 1826 */       return new LongBinding()
/*      */       {
/*      */         public void dispose()
/*      */         {
/* 1833 */           super.unbind(this.val$dependencies);
/*      */         }
/*      */ 
/*      */         protected long computeValue()
/*      */         {
/* 1838 */           return paramObservableNumberValue1.longValue() / paramObservableNumberValue2.longValue();
/*      */         }
/*      */ 
/*      */         public ObservableList<?> getDependencies()
/*      */         {
/* 1844 */           return this.val$dependencies.length == 1 ? FXCollections.singletonObservableList(this.val$dependencies[0]) : new ImmutableObservableList(this.val$dependencies);
/*      */         }
/*      */ 
/*      */       };
/*      */     }
/*      */ 
/* 1850 */     return new IntegerBinding()
/*      */     {
/*      */       public void dispose()
/*      */       {
/* 1857 */         super.unbind(this.val$dependencies);
/*      */       }
/*      */ 
/*      */       protected int computeValue()
/*      */       {
/* 1862 */         return paramObservableNumberValue1.intValue() / paramObservableNumberValue2.intValue();
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/* 1868 */         return this.val$dependencies.length == 1 ? FXCollections.singletonObservableList(this.val$dependencies[0]) : new ImmutableObservableList(this.val$dependencies);
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static NumberBinding divide(ObservableNumberValue paramObservableNumberValue1, ObservableNumberValue paramObservableNumberValue2)
/*      */   {
/* 1890 */     return divide(paramObservableNumberValue1, paramObservableNumberValue2, new Observable[] { paramObservableNumberValue1, paramObservableNumberValue2 });
/*      */   }
/*      */ 
/*      */   public static DoubleBinding divide(ObservableNumberValue paramObservableNumberValue, double paramDouble)
/*      */   {
/* 1907 */     return (DoubleBinding)divide(paramObservableNumberValue, DoubleConstant.valueOf(paramDouble), new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static DoubleBinding divide(double paramDouble, ObservableNumberValue paramObservableNumberValue)
/*      */   {
/* 1924 */     return (DoubleBinding)divide(DoubleConstant.valueOf(paramDouble), paramObservableNumberValue, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static NumberBinding divide(ObservableNumberValue paramObservableNumberValue, float paramFloat)
/*      */   {
/* 1941 */     return divide(paramObservableNumberValue, FloatConstant.valueOf(paramFloat), new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static NumberBinding divide(float paramFloat, ObservableNumberValue paramObservableNumberValue)
/*      */   {
/* 1958 */     return divide(FloatConstant.valueOf(paramFloat), paramObservableNumberValue, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static NumberBinding divide(ObservableNumberValue paramObservableNumberValue, long paramLong)
/*      */   {
/* 1975 */     return divide(paramObservableNumberValue, LongConstant.valueOf(paramLong), new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static NumberBinding divide(long paramLong, ObservableNumberValue paramObservableNumberValue)
/*      */   {
/* 1992 */     return divide(LongConstant.valueOf(paramLong), paramObservableNumberValue, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static NumberBinding divide(ObservableNumberValue paramObservableNumberValue, int paramInt)
/*      */   {
/* 2009 */     return divide(paramObservableNumberValue, IntegerConstant.valueOf(paramInt), new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static NumberBinding divide(int paramInt, ObservableNumberValue paramObservableNumberValue)
/*      */   {
/* 2026 */     return divide(IntegerConstant.valueOf(paramInt), paramObservableNumberValue, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   private static BooleanBinding equal(final ObservableNumberValue paramObservableNumberValue1, final ObservableNumberValue paramObservableNumberValue2, final double paramDouble, Observable[] paramArrayOfObservable)
/*      */   {
/* 2033 */     if ((paramObservableNumberValue1 == null) || (paramObservableNumberValue2 == null)) {
/* 2034 */       throw new NullPointerException("Operands cannot be null.");
/*      */     }
/* 2036 */     assert ((paramArrayOfObservable != null) && (paramArrayOfObservable.length > 0));
/*      */ 
/* 2038 */     if (((paramObservableNumberValue1 instanceof ObservableDoubleValue)) || ((paramObservableNumberValue2 instanceof ObservableDoubleValue))) {
/* 2039 */       return new BooleanBinding()
/*      */       {
/*      */         public void dispose()
/*      */         {
/* 2046 */           super.unbind(this.val$dependencies);
/*      */         }
/*      */ 
/*      */         protected boolean computeValue()
/*      */         {
/* 2051 */           return Math.abs(paramObservableNumberValue1.doubleValue() - paramObservableNumberValue2.doubleValue()) <= paramDouble;
/*      */         }
/*      */ 
/*      */         public ObservableList<?> getDependencies()
/*      */         {
/* 2057 */           return this.val$dependencies.length == 1 ? FXCollections.singletonObservableList(this.val$dependencies[0]) : new ImmutableObservableList(this.val$dependencies);
/*      */         }
/*      */       };
/*      */     }
/*      */ 
/* 2062 */     if (((paramObservableNumberValue1 instanceof ObservableFloatValue)) || ((paramObservableNumberValue2 instanceof ObservableFloatValue))) {
/* 2063 */       return new BooleanBinding()
/*      */       {
/*      */         public void dispose()
/*      */         {
/* 2070 */           super.unbind(this.val$dependencies);
/*      */         }
/*      */ 
/*      */         protected boolean computeValue()
/*      */         {
/* 2075 */           return Math.abs(paramObservableNumberValue1.floatValue() - paramObservableNumberValue2.floatValue()) <= paramDouble;
/*      */         }
/*      */ 
/*      */         public ObservableList<?> getDependencies()
/*      */         {
/* 2081 */           return this.val$dependencies.length == 1 ? FXCollections.singletonObservableList(this.val$dependencies[0]) : new ImmutableObservableList(this.val$dependencies);
/*      */         }
/*      */       };
/*      */     }
/*      */ 
/* 2086 */     if (((paramObservableNumberValue1 instanceof ObservableLongValue)) || ((paramObservableNumberValue2 instanceof ObservableLongValue))) {
/* 2087 */       return new BooleanBinding()
/*      */       {
/*      */         public void dispose()
/*      */         {
/* 2094 */           super.unbind(this.val$dependencies);
/*      */         }
/*      */ 
/*      */         protected boolean computeValue()
/*      */         {
/* 2099 */           return Math.abs(paramObservableNumberValue1.longValue() - paramObservableNumberValue2.longValue()) <= paramDouble;
/*      */         }
/*      */ 
/*      */         public ObservableList<?> getDependencies()
/*      */         {
/* 2105 */           return this.val$dependencies.length == 1 ? FXCollections.singletonObservableList(this.val$dependencies[0]) : new ImmutableObservableList(this.val$dependencies);
/*      */         }
/*      */ 
/*      */       };
/*      */     }
/*      */ 
/* 2111 */     return new BooleanBinding()
/*      */     {
/*      */       public void dispose()
/*      */       {
/* 2118 */         super.unbind(this.val$dependencies);
/*      */       }
/*      */ 
/*      */       protected boolean computeValue()
/*      */       {
/* 2123 */         return Math.abs(paramObservableNumberValue1.intValue() - paramObservableNumberValue2.intValue()) <= paramDouble;
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/* 2129 */         return this.val$dependencies.length == 1 ? FXCollections.singletonObservableList(this.val$dependencies[0]) : new ImmutableObservableList(this.val$dependencies);
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static BooleanBinding equal(ObservableNumberValue paramObservableNumberValue1, ObservableNumberValue paramObservableNumberValue2, double paramDouble)
/*      */   {
/* 2160 */     return equal(paramObservableNumberValue1, paramObservableNumberValue2, paramDouble, new Observable[] { paramObservableNumberValue1, paramObservableNumberValue2 });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding equal(ObservableNumberValue paramObservableNumberValue1, ObservableNumberValue paramObservableNumberValue2)
/*      */   {
/* 2181 */     return equal(paramObservableNumberValue1, paramObservableNumberValue2, 0.0D, new Observable[] { paramObservableNumberValue1, paramObservableNumberValue2 });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding equal(ObservableNumberValue paramObservableNumberValue, double paramDouble1, double paramDouble2)
/*      */   {
/* 2206 */     return equal(paramObservableNumberValue, DoubleConstant.valueOf(paramDouble1), paramDouble2, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding equal(double paramDouble1, ObservableNumberValue paramObservableNumberValue, double paramDouble2)
/*      */   {
/* 2231 */     return equal(DoubleConstant.valueOf(paramDouble1), paramObservableNumberValue, paramDouble2, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding equal(ObservableNumberValue paramObservableNumberValue, float paramFloat, double paramDouble)
/*      */   {
/* 2256 */     return equal(paramObservableNumberValue, FloatConstant.valueOf(paramFloat), paramDouble, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding equal(float paramFloat, ObservableNumberValue paramObservableNumberValue, double paramDouble)
/*      */   {
/* 2281 */     return equal(FloatConstant.valueOf(paramFloat), paramObservableNumberValue, paramDouble, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding equal(ObservableNumberValue paramObservableNumberValue, long paramLong, double paramDouble)
/*      */   {
/* 2306 */     return equal(paramObservableNumberValue, LongConstant.valueOf(paramLong), paramDouble, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding equal(ObservableNumberValue paramObservableNumberValue, long paramLong)
/*      */   {
/* 2327 */     return equal(paramObservableNumberValue, LongConstant.valueOf(paramLong), 0.0D, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding equal(long paramLong, ObservableNumberValue paramObservableNumberValue, double paramDouble)
/*      */   {
/* 2352 */     return equal(LongConstant.valueOf(paramLong), paramObservableNumberValue, paramDouble, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding equal(long paramLong, ObservableNumberValue paramObservableNumberValue)
/*      */   {
/* 2373 */     return equal(LongConstant.valueOf(paramLong), paramObservableNumberValue, 0.0D, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding equal(ObservableNumberValue paramObservableNumberValue, int paramInt, double paramDouble)
/*      */   {
/* 2398 */     return equal(paramObservableNumberValue, IntegerConstant.valueOf(paramInt), paramDouble, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding equal(ObservableNumberValue paramObservableNumberValue, int paramInt)
/*      */   {
/* 2419 */     return equal(paramObservableNumberValue, IntegerConstant.valueOf(paramInt), 0.0D, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding equal(int paramInt, ObservableNumberValue paramObservableNumberValue, double paramDouble)
/*      */   {
/* 2444 */     return equal(IntegerConstant.valueOf(paramInt), paramObservableNumberValue, paramDouble, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding equal(int paramInt, ObservableNumberValue paramObservableNumberValue)
/*      */   {
/* 2465 */     return equal(IntegerConstant.valueOf(paramInt), paramObservableNumberValue, 0.0D, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   private static BooleanBinding notEqual(final ObservableNumberValue paramObservableNumberValue1, final ObservableNumberValue paramObservableNumberValue2, final double paramDouble, Observable[] paramArrayOfObservable)
/*      */   {
/* 2472 */     if ((paramObservableNumberValue1 == null) || (paramObservableNumberValue2 == null)) {
/* 2473 */       throw new NullPointerException("Operands cannot be null.");
/*      */     }
/* 2475 */     assert ((paramArrayOfObservable != null) && (paramArrayOfObservable.length > 0));
/*      */ 
/* 2477 */     if (((paramObservableNumberValue1 instanceof ObservableDoubleValue)) || ((paramObservableNumberValue2 instanceof ObservableDoubleValue))) {
/* 2478 */       return new BooleanBinding()
/*      */       {
/*      */         public void dispose()
/*      */         {
/* 2485 */           super.unbind(this.val$dependencies);
/*      */         }
/*      */ 
/*      */         protected boolean computeValue()
/*      */         {
/* 2490 */           return Math.abs(paramObservableNumberValue1.doubleValue() - paramObservableNumberValue2.doubleValue()) > paramDouble;
/*      */         }
/*      */ 
/*      */         public ObservableList<?> getDependencies()
/*      */         {
/* 2496 */           return this.val$dependencies.length == 1 ? FXCollections.singletonObservableList(this.val$dependencies[0]) : new ImmutableObservableList(this.val$dependencies);
/*      */         }
/*      */       };
/*      */     }
/*      */ 
/* 2501 */     if (((paramObservableNumberValue1 instanceof ObservableFloatValue)) || ((paramObservableNumberValue2 instanceof ObservableFloatValue))) {
/* 2502 */       return new BooleanBinding()
/*      */       {
/*      */         public void dispose()
/*      */         {
/* 2509 */           super.unbind(this.val$dependencies);
/*      */         }
/*      */ 
/*      */         protected boolean computeValue()
/*      */         {
/* 2514 */           return Math.abs(paramObservableNumberValue1.floatValue() - paramObservableNumberValue2.floatValue()) > paramDouble;
/*      */         }
/*      */ 
/*      */         public ObservableList<?> getDependencies()
/*      */         {
/* 2520 */           return this.val$dependencies.length == 1 ? FXCollections.singletonObservableList(this.val$dependencies[0]) : new ImmutableObservableList(this.val$dependencies);
/*      */         }
/*      */       };
/*      */     }
/*      */ 
/* 2525 */     if (((paramObservableNumberValue1 instanceof ObservableLongValue)) || ((paramObservableNumberValue2 instanceof ObservableLongValue))) {
/* 2526 */       return new BooleanBinding()
/*      */       {
/*      */         public void dispose()
/*      */         {
/* 2533 */           super.unbind(this.val$dependencies);
/*      */         }
/*      */ 
/*      */         protected boolean computeValue()
/*      */         {
/* 2538 */           return Math.abs(paramObservableNumberValue1.longValue() - paramObservableNumberValue2.longValue()) > paramDouble;
/*      */         }
/*      */ 
/*      */         public ObservableList<?> getDependencies()
/*      */         {
/* 2544 */           return this.val$dependencies.length == 1 ? FXCollections.singletonObservableList(this.val$dependencies[0]) : new ImmutableObservableList(this.val$dependencies);
/*      */         }
/*      */ 
/*      */       };
/*      */     }
/*      */ 
/* 2550 */     return new BooleanBinding()
/*      */     {
/*      */       public void dispose()
/*      */       {
/* 2557 */         super.unbind(this.val$dependencies);
/*      */       }
/*      */ 
/*      */       protected boolean computeValue()
/*      */       {
/* 2562 */         return Math.abs(paramObservableNumberValue1.intValue() - paramObservableNumberValue2.intValue()) > paramDouble;
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/* 2568 */         return this.val$dependencies.length == 1 ? FXCollections.singletonObservableList(this.val$dependencies[0]) : new ImmutableObservableList(this.val$dependencies);
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static BooleanBinding notEqual(ObservableNumberValue paramObservableNumberValue1, ObservableNumberValue paramObservableNumberValue2, double paramDouble)
/*      */   {
/* 2599 */     return notEqual(paramObservableNumberValue1, paramObservableNumberValue2, paramDouble, new Observable[] { paramObservableNumberValue1, paramObservableNumberValue2 });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding notEqual(ObservableNumberValue paramObservableNumberValue1, ObservableNumberValue paramObservableNumberValue2)
/*      */   {
/* 2620 */     return notEqual(paramObservableNumberValue1, paramObservableNumberValue2, 0.0D, new Observable[] { paramObservableNumberValue1, paramObservableNumberValue2 });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding notEqual(ObservableNumberValue paramObservableNumberValue, double paramDouble1, double paramDouble2)
/*      */   {
/* 2645 */     return notEqual(paramObservableNumberValue, DoubleConstant.valueOf(paramDouble1), paramDouble2, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding notEqual(double paramDouble1, ObservableNumberValue paramObservableNumberValue, double paramDouble2)
/*      */   {
/* 2670 */     return notEqual(DoubleConstant.valueOf(paramDouble1), paramObservableNumberValue, paramDouble2, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding notEqual(ObservableNumberValue paramObservableNumberValue, float paramFloat, double paramDouble)
/*      */   {
/* 2695 */     return notEqual(paramObservableNumberValue, FloatConstant.valueOf(paramFloat), paramDouble, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding notEqual(float paramFloat, ObservableNumberValue paramObservableNumberValue, double paramDouble)
/*      */   {
/* 2720 */     return notEqual(FloatConstant.valueOf(paramFloat), paramObservableNumberValue, paramDouble, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding notEqual(ObservableNumberValue paramObservableNumberValue, long paramLong, double paramDouble)
/*      */   {
/* 2745 */     return notEqual(paramObservableNumberValue, LongConstant.valueOf(paramLong), paramDouble, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding notEqual(ObservableNumberValue paramObservableNumberValue, long paramLong)
/*      */   {
/* 2766 */     return notEqual(paramObservableNumberValue, LongConstant.valueOf(paramLong), 0.0D, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding notEqual(long paramLong, ObservableNumberValue paramObservableNumberValue, double paramDouble)
/*      */   {
/* 2791 */     return notEqual(LongConstant.valueOf(paramLong), paramObservableNumberValue, paramDouble, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding notEqual(long paramLong, ObservableNumberValue paramObservableNumberValue)
/*      */   {
/* 2812 */     return notEqual(LongConstant.valueOf(paramLong), paramObservableNumberValue, 0.0D, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding notEqual(ObservableNumberValue paramObservableNumberValue, int paramInt, double paramDouble)
/*      */   {
/* 2837 */     return notEqual(paramObservableNumberValue, IntegerConstant.valueOf(paramInt), paramDouble, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding notEqual(ObservableNumberValue paramObservableNumberValue, int paramInt)
/*      */   {
/* 2858 */     return notEqual(paramObservableNumberValue, IntegerConstant.valueOf(paramInt), 0.0D, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding notEqual(int paramInt, ObservableNumberValue paramObservableNumberValue, double paramDouble)
/*      */   {
/* 2883 */     return notEqual(IntegerConstant.valueOf(paramInt), paramObservableNumberValue, paramDouble, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding notEqual(int paramInt, ObservableNumberValue paramObservableNumberValue)
/*      */   {
/* 2904 */     return notEqual(IntegerConstant.valueOf(paramInt), paramObservableNumberValue, 0.0D, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   private static BooleanBinding greaterThan(final ObservableNumberValue paramObservableNumberValue1, final ObservableNumberValue paramObservableNumberValue2, Observable[] paramArrayOfObservable)
/*      */   {
/* 2911 */     if ((paramObservableNumberValue1 == null) || (paramObservableNumberValue2 == null)) {
/* 2912 */       throw new NullPointerException("Operands cannot be null.");
/*      */     }
/* 2914 */     assert ((paramArrayOfObservable != null) && (paramArrayOfObservable.length > 0));
/*      */ 
/* 2916 */     if (((paramObservableNumberValue1 instanceof ObservableDoubleValue)) || ((paramObservableNumberValue2 instanceof ObservableDoubleValue))) {
/* 2917 */       return new BooleanBinding()
/*      */       {
/*      */         public void dispose()
/*      */         {
/* 2924 */           super.unbind(this.val$dependencies);
/*      */         }
/*      */ 
/*      */         protected boolean computeValue()
/*      */         {
/* 2929 */           return paramObservableNumberValue1.doubleValue() > paramObservableNumberValue2.doubleValue();
/*      */         }
/*      */ 
/*      */         public ObservableList<?> getDependencies()
/*      */         {
/* 2935 */           return this.val$dependencies.length == 1 ? FXCollections.singletonObservableList(this.val$dependencies[0]) : new ImmutableObservableList(this.val$dependencies);
/*      */         }
/*      */       };
/*      */     }
/*      */ 
/* 2940 */     if (((paramObservableNumberValue1 instanceof ObservableFloatValue)) || ((paramObservableNumberValue2 instanceof ObservableFloatValue))) {
/* 2941 */       return new BooleanBinding()
/*      */       {
/*      */         public void dispose()
/*      */         {
/* 2948 */           super.unbind(this.val$dependencies);
/*      */         }
/*      */ 
/*      */         protected boolean computeValue()
/*      */         {
/* 2953 */           return paramObservableNumberValue1.floatValue() > paramObservableNumberValue2.floatValue();
/*      */         }
/*      */ 
/*      */         public ObservableList<?> getDependencies()
/*      */         {
/* 2959 */           return this.val$dependencies.length == 1 ? FXCollections.singletonObservableList(this.val$dependencies[0]) : new ImmutableObservableList(this.val$dependencies);
/*      */         }
/*      */       };
/*      */     }
/*      */ 
/* 2964 */     if (((paramObservableNumberValue1 instanceof ObservableLongValue)) || ((paramObservableNumberValue2 instanceof ObservableLongValue))) {
/* 2965 */       return new BooleanBinding()
/*      */       {
/*      */         public void dispose()
/*      */         {
/* 2972 */           super.unbind(this.val$dependencies);
/*      */         }
/*      */ 
/*      */         protected boolean computeValue()
/*      */         {
/* 2977 */           return paramObservableNumberValue1.longValue() > paramObservableNumberValue2.longValue();
/*      */         }
/*      */ 
/*      */         public ObservableList<?> getDependencies()
/*      */         {
/* 2983 */           return this.val$dependencies.length == 1 ? FXCollections.singletonObservableList(this.val$dependencies[0]) : new ImmutableObservableList(this.val$dependencies);
/*      */         }
/*      */ 
/*      */       };
/*      */     }
/*      */ 
/* 2989 */     return new BooleanBinding()
/*      */     {
/*      */       public void dispose()
/*      */       {
/* 2996 */         super.unbind(this.val$dependencies);
/*      */       }
/*      */ 
/*      */       protected boolean computeValue()
/*      */       {
/* 3001 */         return paramObservableNumberValue1.intValue() > paramObservableNumberValue2.intValue();
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/* 3007 */         return this.val$dependencies.length == 1 ? FXCollections.singletonObservableList(this.val$dependencies[0]) : new ImmutableObservableList(this.val$dependencies);
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static BooleanBinding greaterThan(ObservableNumberValue paramObservableNumberValue1, ObservableNumberValue paramObservableNumberValue2)
/*      */   {
/* 3030 */     return greaterThan(paramObservableNumberValue1, paramObservableNumberValue2, new Observable[] { paramObservableNumberValue1, paramObservableNumberValue2 });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding greaterThan(ObservableNumberValue paramObservableNumberValue, double paramDouble)
/*      */   {
/* 3047 */     return greaterThan(paramObservableNumberValue, DoubleConstant.valueOf(paramDouble), new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding greaterThan(double paramDouble, ObservableNumberValue paramObservableNumberValue)
/*      */   {
/* 3064 */     return greaterThan(DoubleConstant.valueOf(paramDouble), paramObservableNumberValue, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding greaterThan(ObservableNumberValue paramObservableNumberValue, float paramFloat)
/*      */   {
/* 3081 */     return greaterThan(paramObservableNumberValue, FloatConstant.valueOf(paramFloat), new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding greaterThan(float paramFloat, ObservableNumberValue paramObservableNumberValue)
/*      */   {
/* 3098 */     return greaterThan(FloatConstant.valueOf(paramFloat), paramObservableNumberValue, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding greaterThan(ObservableNumberValue paramObservableNumberValue, long paramLong)
/*      */   {
/* 3115 */     return greaterThan(paramObservableNumberValue, LongConstant.valueOf(paramLong), new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding greaterThan(long paramLong, ObservableNumberValue paramObservableNumberValue)
/*      */   {
/* 3132 */     return greaterThan(LongConstant.valueOf(paramLong), paramObservableNumberValue, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding greaterThan(ObservableNumberValue paramObservableNumberValue, int paramInt)
/*      */   {
/* 3149 */     return greaterThan(paramObservableNumberValue, IntegerConstant.valueOf(paramInt), new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding greaterThan(int paramInt, ObservableNumberValue paramObservableNumberValue)
/*      */   {
/* 3166 */     return greaterThan(IntegerConstant.valueOf(paramInt), paramObservableNumberValue, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   private static BooleanBinding lessThan(ObservableNumberValue paramObservableNumberValue1, ObservableNumberValue paramObservableNumberValue2, Observable[] paramArrayOfObservable)
/*      */   {
/* 3173 */     return greaterThan(paramObservableNumberValue2, paramObservableNumberValue1, paramArrayOfObservable);
/*      */   }
/*      */ 
/*      */   public static BooleanBinding lessThan(ObservableNumberValue paramObservableNumberValue1, ObservableNumberValue paramObservableNumberValue2)
/*      */   {
/* 3191 */     return lessThan(paramObservableNumberValue1, paramObservableNumberValue2, new Observable[] { paramObservableNumberValue1, paramObservableNumberValue2 });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding lessThan(ObservableNumberValue paramObservableNumberValue, double paramDouble)
/*      */   {
/* 3208 */     return lessThan(paramObservableNumberValue, DoubleConstant.valueOf(paramDouble), new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding lessThan(double paramDouble, ObservableNumberValue paramObservableNumberValue)
/*      */   {
/* 3225 */     return lessThan(DoubleConstant.valueOf(paramDouble), paramObservableNumberValue, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding lessThan(ObservableNumberValue paramObservableNumberValue, float paramFloat)
/*      */   {
/* 3242 */     return lessThan(paramObservableNumberValue, FloatConstant.valueOf(paramFloat), new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding lessThan(float paramFloat, ObservableNumberValue paramObservableNumberValue)
/*      */   {
/* 3259 */     return lessThan(FloatConstant.valueOf(paramFloat), paramObservableNumberValue, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding lessThan(ObservableNumberValue paramObservableNumberValue, long paramLong)
/*      */   {
/* 3276 */     return lessThan(paramObservableNumberValue, LongConstant.valueOf(paramLong), new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding lessThan(long paramLong, ObservableNumberValue paramObservableNumberValue)
/*      */   {
/* 3293 */     return lessThan(LongConstant.valueOf(paramLong), paramObservableNumberValue, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding lessThan(ObservableNumberValue paramObservableNumberValue, int paramInt)
/*      */   {
/* 3310 */     return lessThan(paramObservableNumberValue, IntegerConstant.valueOf(paramInt), new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding lessThan(int paramInt, ObservableNumberValue paramObservableNumberValue)
/*      */   {
/* 3327 */     return lessThan(IntegerConstant.valueOf(paramInt), paramObservableNumberValue, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   private static BooleanBinding greaterThanOrEqual(final ObservableNumberValue paramObservableNumberValue1, final ObservableNumberValue paramObservableNumberValue2, Observable[] paramArrayOfObservable)
/*      */   {
/* 3334 */     if ((paramObservableNumberValue1 == null) || (paramObservableNumberValue2 == null)) {
/* 3335 */       throw new NullPointerException("Operands cannot be null.");
/*      */     }
/* 3337 */     assert ((paramArrayOfObservable != null) && (paramArrayOfObservable.length > 0));
/*      */ 
/* 3339 */     if (((paramObservableNumberValue1 instanceof ObservableDoubleValue)) || ((paramObservableNumberValue2 instanceof ObservableDoubleValue))) {
/* 3340 */       return new BooleanBinding()
/*      */       {
/*      */         public void dispose()
/*      */         {
/* 3347 */           super.unbind(this.val$dependencies);
/*      */         }
/*      */ 
/*      */         protected boolean computeValue()
/*      */         {
/* 3352 */           return paramObservableNumberValue1.doubleValue() >= paramObservableNumberValue2.doubleValue();
/*      */         }
/*      */ 
/*      */         public ObservableList<?> getDependencies()
/*      */         {
/* 3358 */           return this.val$dependencies.length == 1 ? FXCollections.singletonObservableList(this.val$dependencies[0]) : new ImmutableObservableList(this.val$dependencies);
/*      */         }
/*      */       };
/*      */     }
/*      */ 
/* 3363 */     if (((paramObservableNumberValue1 instanceof ObservableFloatValue)) || ((paramObservableNumberValue2 instanceof ObservableFloatValue))) {
/* 3364 */       return new BooleanBinding()
/*      */       {
/*      */         public void dispose()
/*      */         {
/* 3371 */           super.unbind(this.val$dependencies);
/*      */         }
/*      */ 
/*      */         protected boolean computeValue()
/*      */         {
/* 3376 */           return paramObservableNumberValue1.floatValue() >= paramObservableNumberValue2.floatValue();
/*      */         }
/*      */ 
/*      */         public ObservableList<?> getDependencies()
/*      */         {
/* 3382 */           return this.val$dependencies.length == 1 ? FXCollections.singletonObservableList(this.val$dependencies[0]) : new ImmutableObservableList(this.val$dependencies);
/*      */         }
/*      */       };
/*      */     }
/*      */ 
/* 3387 */     if (((paramObservableNumberValue1 instanceof ObservableLongValue)) || ((paramObservableNumberValue2 instanceof ObservableLongValue))) {
/* 3388 */       return new BooleanBinding()
/*      */       {
/*      */         public void dispose()
/*      */         {
/* 3395 */           super.unbind(this.val$dependencies);
/*      */         }
/*      */ 
/*      */         protected boolean computeValue()
/*      */         {
/* 3400 */           return paramObservableNumberValue1.longValue() >= paramObservableNumberValue2.longValue();
/*      */         }
/*      */ 
/*      */         public ObservableList<?> getDependencies()
/*      */         {
/* 3406 */           return this.val$dependencies.length == 1 ? FXCollections.singletonObservableList(this.val$dependencies[0]) : new ImmutableObservableList(this.val$dependencies);
/*      */         }
/*      */ 
/*      */       };
/*      */     }
/*      */ 
/* 3412 */     return new BooleanBinding()
/*      */     {
/*      */       public void dispose()
/*      */       {
/* 3419 */         super.unbind(this.val$dependencies);
/*      */       }
/*      */ 
/*      */       protected boolean computeValue()
/*      */       {
/* 3424 */         return paramObservableNumberValue1.intValue() >= paramObservableNumberValue2.intValue();
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/* 3430 */         return this.val$dependencies.length == 1 ? FXCollections.singletonObservableList(this.val$dependencies[0]) : new ImmutableObservableList(this.val$dependencies);
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static BooleanBinding greaterThanOrEqual(ObservableNumberValue paramObservableNumberValue1, ObservableNumberValue paramObservableNumberValue2)
/*      */   {
/* 3453 */     return greaterThanOrEqual(paramObservableNumberValue1, paramObservableNumberValue2, new Observable[] { paramObservableNumberValue1, paramObservableNumberValue2 });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding greaterThanOrEqual(ObservableNumberValue paramObservableNumberValue, double paramDouble)
/*      */   {
/* 3470 */     return greaterThanOrEqual(paramObservableNumberValue, DoubleConstant.valueOf(paramDouble), new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding greaterThanOrEqual(double paramDouble, ObservableNumberValue paramObservableNumberValue)
/*      */   {
/* 3487 */     return greaterThanOrEqual(DoubleConstant.valueOf(paramDouble), paramObservableNumberValue, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding greaterThanOrEqual(ObservableNumberValue paramObservableNumberValue, float paramFloat)
/*      */   {
/* 3504 */     return greaterThanOrEqual(paramObservableNumberValue, FloatConstant.valueOf(paramFloat), new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding greaterThanOrEqual(float paramFloat, ObservableNumberValue paramObservableNumberValue)
/*      */   {
/* 3521 */     return greaterThanOrEqual(FloatConstant.valueOf(paramFloat), paramObservableNumberValue, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding greaterThanOrEqual(ObservableNumberValue paramObservableNumberValue, long paramLong)
/*      */   {
/* 3538 */     return greaterThanOrEqual(paramObservableNumberValue, LongConstant.valueOf(paramLong), new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding greaterThanOrEqual(long paramLong, ObservableNumberValue paramObservableNumberValue)
/*      */   {
/* 3555 */     return greaterThanOrEqual(LongConstant.valueOf(paramLong), paramObservableNumberValue, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding greaterThanOrEqual(ObservableNumberValue paramObservableNumberValue, int paramInt)
/*      */   {
/* 3572 */     return greaterThanOrEqual(paramObservableNumberValue, IntegerConstant.valueOf(paramInt), new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding greaterThanOrEqual(int paramInt, ObservableNumberValue paramObservableNumberValue)
/*      */   {
/* 3589 */     return greaterThanOrEqual(IntegerConstant.valueOf(paramInt), paramObservableNumberValue, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   private static BooleanBinding lessThanOrEqual(ObservableNumberValue paramObservableNumberValue1, ObservableNumberValue paramObservableNumberValue2, Observable[] paramArrayOfObservable)
/*      */   {
/* 3596 */     return greaterThanOrEqual(paramObservableNumberValue2, paramObservableNumberValue1, paramArrayOfObservable);
/*      */   }
/*      */ 
/*      */   public static BooleanBinding lessThanOrEqual(ObservableNumberValue paramObservableNumberValue1, ObservableNumberValue paramObservableNumberValue2)
/*      */   {
/* 3615 */     return lessThanOrEqual(paramObservableNumberValue1, paramObservableNumberValue2, new Observable[] { paramObservableNumberValue1, paramObservableNumberValue2 });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding lessThanOrEqual(ObservableNumberValue paramObservableNumberValue, double paramDouble)
/*      */   {
/* 3632 */     return lessThanOrEqual(paramObservableNumberValue, DoubleConstant.valueOf(paramDouble), new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding lessThanOrEqual(double paramDouble, ObservableNumberValue paramObservableNumberValue)
/*      */   {
/* 3649 */     return lessThanOrEqual(DoubleConstant.valueOf(paramDouble), paramObservableNumberValue, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding lessThanOrEqual(ObservableNumberValue paramObservableNumberValue, float paramFloat)
/*      */   {
/* 3666 */     return lessThanOrEqual(paramObservableNumberValue, FloatConstant.valueOf(paramFloat), new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding lessThanOrEqual(float paramFloat, ObservableNumberValue paramObservableNumberValue)
/*      */   {
/* 3683 */     return lessThanOrEqual(FloatConstant.valueOf(paramFloat), paramObservableNumberValue, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding lessThanOrEqual(ObservableNumberValue paramObservableNumberValue, long paramLong)
/*      */   {
/* 3700 */     return lessThanOrEqual(paramObservableNumberValue, LongConstant.valueOf(paramLong), new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding lessThanOrEqual(long paramLong, ObservableNumberValue paramObservableNumberValue)
/*      */   {
/* 3717 */     return lessThanOrEqual(LongConstant.valueOf(paramLong), paramObservableNumberValue, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding lessThanOrEqual(ObservableNumberValue paramObservableNumberValue, int paramInt)
/*      */   {
/* 3734 */     return lessThanOrEqual(paramObservableNumberValue, IntegerConstant.valueOf(paramInt), new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding lessThanOrEqual(int paramInt, ObservableNumberValue paramObservableNumberValue)
/*      */   {
/* 3751 */     return lessThanOrEqual(IntegerConstant.valueOf(paramInt), paramObservableNumberValue, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   private static NumberBinding min(final ObservableNumberValue paramObservableNumberValue1, final ObservableNumberValue paramObservableNumberValue2, Observable[] paramArrayOfObservable)
/*      */   {
/* 3758 */     if ((paramObservableNumberValue1 == null) || (paramObservableNumberValue2 == null)) {
/* 3759 */       throw new NullPointerException("Operands cannot be null.");
/*      */     }
/* 3761 */     assert ((paramArrayOfObservable != null) && (paramArrayOfObservable.length > 0));
/*      */ 
/* 3763 */     if (((paramObservableNumberValue1 instanceof ObservableDoubleValue)) || ((paramObservableNumberValue2 instanceof ObservableDoubleValue))) {
/* 3764 */       return new DoubleBinding()
/*      */       {
/*      */         public void dispose()
/*      */         {
/* 3771 */           super.unbind(this.val$dependencies);
/*      */         }
/*      */ 
/*      */         protected double computeValue()
/*      */         {
/* 3776 */           return Math.min(paramObservableNumberValue1.doubleValue(), paramObservableNumberValue2.doubleValue());
/*      */         }
/*      */ 
/*      */         public ObservableList<?> getDependencies()
/*      */         {
/* 3782 */           return this.val$dependencies.length == 1 ? FXCollections.singletonObservableList(this.val$dependencies[0]) : new ImmutableObservableList(this.val$dependencies);
/*      */         }
/*      */       };
/*      */     }
/*      */ 
/* 3787 */     if (((paramObservableNumberValue1 instanceof ObservableFloatValue)) || ((paramObservableNumberValue2 instanceof ObservableFloatValue))) {
/* 3788 */       return new FloatBinding()
/*      */       {
/*      */         public void dispose()
/*      */         {
/* 3795 */           super.unbind(this.val$dependencies);
/*      */         }
/*      */ 
/*      */         protected float computeValue()
/*      */         {
/* 3800 */           return Math.min(paramObservableNumberValue1.floatValue(), paramObservableNumberValue2.floatValue());
/*      */         }
/*      */ 
/*      */         public ObservableList<?> getDependencies()
/*      */         {
/* 3806 */           return this.val$dependencies.length == 1 ? FXCollections.singletonObservableList(this.val$dependencies[0]) : new ImmutableObservableList(this.val$dependencies);
/*      */         }
/*      */       };
/*      */     }
/*      */ 
/* 3811 */     if (((paramObservableNumberValue1 instanceof ObservableLongValue)) || ((paramObservableNumberValue2 instanceof ObservableLongValue))) {
/* 3812 */       return new LongBinding()
/*      */       {
/*      */         public void dispose()
/*      */         {
/* 3819 */           super.unbind(this.val$dependencies);
/*      */         }
/*      */ 
/*      */         protected long computeValue()
/*      */         {
/* 3824 */           return Math.min(paramObservableNumberValue1.longValue(), paramObservableNumberValue2.longValue());
/*      */         }
/*      */ 
/*      */         public ObservableList<?> getDependencies()
/*      */         {
/* 3830 */           return this.val$dependencies.length == 1 ? FXCollections.singletonObservableList(this.val$dependencies[0]) : new ImmutableObservableList(this.val$dependencies);
/*      */         }
/*      */ 
/*      */       };
/*      */     }
/*      */ 
/* 3836 */     return new IntegerBinding()
/*      */     {
/*      */       public void dispose()
/*      */       {
/* 3843 */         super.unbind(this.val$dependencies);
/*      */       }
/*      */ 
/*      */       protected int computeValue()
/*      */       {
/* 3848 */         return Math.min(paramObservableNumberValue1.intValue(), paramObservableNumberValue2.intValue());
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/* 3854 */         return this.val$dependencies.length == 1 ? FXCollections.singletonObservableList(this.val$dependencies[0]) : new ImmutableObservableList(this.val$dependencies);
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static NumberBinding min(ObservableNumberValue paramObservableNumberValue1, ObservableNumberValue paramObservableNumberValue2)
/*      */   {
/* 3876 */     return min(paramObservableNumberValue1, paramObservableNumberValue2, new Observable[] { paramObservableNumberValue1, paramObservableNumberValue2 });
/*      */   }
/*      */ 
/*      */   public static DoubleBinding min(ObservableNumberValue paramObservableNumberValue, double paramDouble)
/*      */   {
/* 3893 */     return (DoubleBinding)min(paramObservableNumberValue, DoubleConstant.valueOf(paramDouble), new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static DoubleBinding min(double paramDouble, ObservableNumberValue paramObservableNumberValue)
/*      */   {
/* 3910 */     return (DoubleBinding)min(DoubleConstant.valueOf(paramDouble), paramObservableNumberValue, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static NumberBinding min(ObservableNumberValue paramObservableNumberValue, float paramFloat)
/*      */   {
/* 3927 */     return min(paramObservableNumberValue, FloatConstant.valueOf(paramFloat), new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static NumberBinding min(float paramFloat, ObservableNumberValue paramObservableNumberValue)
/*      */   {
/* 3944 */     return min(FloatConstant.valueOf(paramFloat), paramObservableNumberValue, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static NumberBinding min(ObservableNumberValue paramObservableNumberValue, long paramLong)
/*      */   {
/* 3961 */     return min(paramObservableNumberValue, LongConstant.valueOf(paramLong), new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static NumberBinding min(long paramLong, ObservableNumberValue paramObservableNumberValue)
/*      */   {
/* 3978 */     return min(LongConstant.valueOf(paramLong), paramObservableNumberValue, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static NumberBinding min(ObservableNumberValue paramObservableNumberValue, int paramInt)
/*      */   {
/* 3995 */     return min(paramObservableNumberValue, IntegerConstant.valueOf(paramInt), new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static NumberBinding min(int paramInt, ObservableNumberValue paramObservableNumberValue)
/*      */   {
/* 4012 */     return min(IntegerConstant.valueOf(paramInt), paramObservableNumberValue, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   private static NumberBinding max(final ObservableNumberValue paramObservableNumberValue1, final ObservableNumberValue paramObservableNumberValue2, Observable[] paramArrayOfObservable)
/*      */   {
/* 4019 */     if ((paramObservableNumberValue1 == null) || (paramObservableNumberValue2 == null)) {
/* 4020 */       throw new NullPointerException("Operands cannot be null.");
/*      */     }
/* 4022 */     assert ((paramArrayOfObservable != null) && (paramArrayOfObservable.length > 0));
/*      */ 
/* 4024 */     if (((paramObservableNumberValue1 instanceof ObservableDoubleValue)) || ((paramObservableNumberValue2 instanceof ObservableDoubleValue))) {
/* 4025 */       return new DoubleBinding()
/*      */       {
/*      */         public void dispose()
/*      */         {
/* 4032 */           super.unbind(this.val$dependencies);
/*      */         }
/*      */ 
/*      */         protected double computeValue()
/*      */         {
/* 4037 */           return Math.max(paramObservableNumberValue1.doubleValue(), paramObservableNumberValue2.doubleValue());
/*      */         }
/*      */ 
/*      */         public ObservableList<?> getDependencies()
/*      */         {
/* 4043 */           return this.val$dependencies.length == 1 ? FXCollections.singletonObservableList(this.val$dependencies[0]) : new ImmutableObservableList(this.val$dependencies);
/*      */         }
/*      */       };
/*      */     }
/*      */ 
/* 4048 */     if (((paramObservableNumberValue1 instanceof ObservableFloatValue)) || ((paramObservableNumberValue2 instanceof ObservableFloatValue))) {
/* 4049 */       return new FloatBinding()
/*      */       {
/*      */         public void dispose()
/*      */         {
/* 4056 */           super.unbind(this.val$dependencies);
/*      */         }
/*      */ 
/*      */         protected float computeValue()
/*      */         {
/* 4061 */           return Math.max(paramObservableNumberValue1.floatValue(), paramObservableNumberValue2.floatValue());
/*      */         }
/*      */ 
/*      */         public ObservableList<?> getDependencies()
/*      */         {
/* 4067 */           return this.val$dependencies.length == 1 ? FXCollections.singletonObservableList(this.val$dependencies[0]) : new ImmutableObservableList(this.val$dependencies);
/*      */         }
/*      */       };
/*      */     }
/*      */ 
/* 4072 */     if (((paramObservableNumberValue1 instanceof ObservableLongValue)) || ((paramObservableNumberValue2 instanceof ObservableLongValue))) {
/* 4073 */       return new LongBinding()
/*      */       {
/*      */         public void dispose()
/*      */         {
/* 4080 */           super.unbind(this.val$dependencies);
/*      */         }
/*      */ 
/*      */         protected long computeValue()
/*      */         {
/* 4085 */           return Math.max(paramObservableNumberValue1.longValue(), paramObservableNumberValue2.longValue());
/*      */         }
/*      */ 
/*      */         public ObservableList<?> getDependencies()
/*      */         {
/* 4091 */           return this.val$dependencies.length == 1 ? FXCollections.singletonObservableList(this.val$dependencies[0]) : new ImmutableObservableList(this.val$dependencies);
/*      */         }
/*      */ 
/*      */       };
/*      */     }
/*      */ 
/* 4097 */     return new IntegerBinding()
/*      */     {
/*      */       public void dispose()
/*      */       {
/* 4104 */         super.unbind(this.val$dependencies);
/*      */       }
/*      */ 
/*      */       protected int computeValue()
/*      */       {
/* 4109 */         return Math.max(paramObservableNumberValue1.intValue(), paramObservableNumberValue2.intValue());
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/* 4115 */         return this.val$dependencies.length == 1 ? FXCollections.singletonObservableList(this.val$dependencies[0]) : new ImmutableObservableList(this.val$dependencies);
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static NumberBinding max(ObservableNumberValue paramObservableNumberValue1, ObservableNumberValue paramObservableNumberValue2)
/*      */   {
/* 4137 */     return max(paramObservableNumberValue1, paramObservableNumberValue2, new Observable[] { paramObservableNumberValue1, paramObservableNumberValue2 });
/*      */   }
/*      */ 
/*      */   public static DoubleBinding max(ObservableNumberValue paramObservableNumberValue, double paramDouble)
/*      */   {
/* 4154 */     return (DoubleBinding)max(paramObservableNumberValue, DoubleConstant.valueOf(paramDouble), new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static DoubleBinding max(double paramDouble, ObservableNumberValue paramObservableNumberValue)
/*      */   {
/* 4171 */     return (DoubleBinding)max(DoubleConstant.valueOf(paramDouble), paramObservableNumberValue, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static NumberBinding max(ObservableNumberValue paramObservableNumberValue, float paramFloat)
/*      */   {
/* 4188 */     return max(paramObservableNumberValue, FloatConstant.valueOf(paramFloat), new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static NumberBinding max(float paramFloat, ObservableNumberValue paramObservableNumberValue)
/*      */   {
/* 4205 */     return max(FloatConstant.valueOf(paramFloat), paramObservableNumberValue, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static NumberBinding max(ObservableNumberValue paramObservableNumberValue, long paramLong)
/*      */   {
/* 4222 */     return max(paramObservableNumberValue, LongConstant.valueOf(paramLong), new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static NumberBinding max(long paramLong, ObservableNumberValue paramObservableNumberValue)
/*      */   {
/* 4239 */     return max(LongConstant.valueOf(paramLong), paramObservableNumberValue, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static NumberBinding max(ObservableNumberValue paramObservableNumberValue, int paramInt)
/*      */   {
/* 4256 */     return max(paramObservableNumberValue, IntegerConstant.valueOf(paramInt), new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static NumberBinding max(int paramInt, ObservableNumberValue paramObservableNumberValue)
/*      */   {
/* 4273 */     return max(IntegerConstant.valueOf(paramInt), paramObservableNumberValue, new Observable[] { paramObservableNumberValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding and(ObservableBooleanValue paramObservableBooleanValue1, final ObservableBooleanValue paramObservableBooleanValue2)
/*      */   {
/* 4321 */     if ((paramObservableBooleanValue1 == null) || (paramObservableBooleanValue2 == null)) {
/* 4322 */       throw new NullPointerException("Operands cannot be null.");
/*      */     }
/*      */ 
/* 4325 */     return new BooleanBinding()
/*      */     {
/*      */       final InvalidationListener observer;
/*      */ 
/*      */       public void dispose()
/*      */       {
/* 4334 */         this.val$op1.removeListener(this.observer);
/* 4335 */         paramObservableBooleanValue2.removeListener(this.observer);
/*      */       }
/*      */ 
/*      */       protected boolean computeValue()
/*      */       {
/* 4340 */         return (this.val$op1.get()) && (paramObservableBooleanValue2.get());
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/* 4346 */         return new ImmutableObservableList(new ObservableBooleanValue[] { this.val$op1, paramObservableBooleanValue2 });
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static BooleanBinding or(ObservableBooleanValue paramObservableBooleanValue1, final ObservableBooleanValue paramObservableBooleanValue2)
/*      */   {
/* 4393 */     if ((paramObservableBooleanValue1 == null) || (paramObservableBooleanValue2 == null)) {
/* 4394 */       throw new NullPointerException("Operands cannot be null.");
/*      */     }
/*      */ 
/* 4397 */     return new BooleanBinding()
/*      */     {
/*      */       final InvalidationListener observer;
/*      */ 
/*      */       public void dispose()
/*      */       {
/* 4406 */         this.val$op1.removeListener(this.observer);
/* 4407 */         paramObservableBooleanValue2.removeListener(this.observer);
/*      */       }
/*      */ 
/*      */       protected boolean computeValue()
/*      */       {
/* 4412 */         return (this.val$op1.get()) || (paramObservableBooleanValue2.get());
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/* 4418 */         return new ImmutableObservableList(new ObservableBooleanValue[] { this.val$op1, paramObservableBooleanValue2 });
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static BooleanBinding not(ObservableBooleanValue paramObservableBooleanValue)
/*      */   {
/* 4434 */     if (paramObservableBooleanValue == null) {
/* 4435 */       throw new NullPointerException("Operand cannot be null.");
/*      */     }
/*      */ 
/* 4438 */     return new BooleanBinding()
/*      */     {
/*      */       public void dispose()
/*      */       {
/* 4445 */         super.unbind(new Observable[] { this.val$op });
/*      */       }
/*      */ 
/*      */       protected boolean computeValue()
/*      */       {
/* 4450 */         return !this.val$op.get();
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/* 4456 */         return FXCollections.singletonObservableList(this.val$op);
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static BooleanBinding equal(ObservableBooleanValue paramObservableBooleanValue1, final ObservableBooleanValue paramObservableBooleanValue2)
/*      */   {
/* 4474 */     if ((paramObservableBooleanValue1 == null) || (paramObservableBooleanValue2 == null)) {
/* 4475 */       throw new NullPointerException("Operands cannot be null.");
/*      */     }
/*      */ 
/* 4478 */     return new BooleanBinding()
/*      */     {
/*      */       public void dispose()
/*      */       {
/* 4485 */         super.unbind(new Observable[] { this.val$op1, paramObservableBooleanValue2 });
/*      */       }
/*      */ 
/*      */       protected boolean computeValue()
/*      */       {
/* 4490 */         return this.val$op1.get() == paramObservableBooleanValue2.get();
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/* 4496 */         return new ImmutableObservableList(new ObservableBooleanValue[] { this.val$op1, paramObservableBooleanValue2 });
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static BooleanBinding notEqual(ObservableBooleanValue paramObservableBooleanValue1, final ObservableBooleanValue paramObservableBooleanValue2)
/*      */   {
/* 4515 */     if ((paramObservableBooleanValue1 == null) || (paramObservableBooleanValue2 == null)) {
/* 4516 */       throw new NullPointerException("Operands cannot be null.");
/*      */     }
/*      */ 
/* 4519 */     return new BooleanBinding()
/*      */     {
/*      */       public void dispose()
/*      */       {
/* 4526 */         super.unbind(new Observable[] { this.val$op1, paramObservableBooleanValue2 });
/*      */       }
/*      */ 
/*      */       protected boolean computeValue()
/*      */       {
/* 4531 */         return this.val$op1.get() != paramObservableBooleanValue2.get();
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/* 4537 */         return new ImmutableObservableList(new ObservableBooleanValue[] { this.val$op1, paramObservableBooleanValue2 });
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static StringExpression convert(ObservableValue<?> paramObservableValue)
/*      */   {
/* 4561 */     return StringFormatter.convert(paramObservableValue);
/*      */   }
/*      */ 
/*      */   public static StringExpression concat(Object[] paramArrayOfObject)
/*      */   {
/* 4582 */     return StringFormatter.concat(paramArrayOfObject);
/*      */   }
/*      */ 
/*      */   public static StringExpression format(String paramString, Object[] paramArrayOfObject)
/*      */   {
/* 4605 */     return StringFormatter.format(paramString, paramArrayOfObject);
/*      */   }
/*      */ 
/*      */   public static StringExpression format(Locale paramLocale, String paramString, Object[] paramArrayOfObject)
/*      */   {
/* 4632 */     return StringFormatter.format(paramLocale, paramString, paramArrayOfObject);
/*      */   }
/*      */ 
/*      */   private static String getStringSafe(String paramString) {
/* 4636 */     return paramString == null ? "" : paramString;
/*      */   }
/*      */ 
/*      */   private static BooleanBinding equal(final ObservableStringValue paramObservableStringValue1, final ObservableStringValue paramObservableStringValue2, Observable[] paramArrayOfObservable) {
/* 4640 */     if ((paramObservableStringValue1 == null) || (paramObservableStringValue2 == null)) {
/* 4641 */       throw new NullPointerException("Operands cannot be null.");
/*      */     }
/* 4643 */     assert ((paramArrayOfObservable != null) && (paramArrayOfObservable.length > 0));
/*      */ 
/* 4645 */     return new BooleanBinding()
/*      */     {
/*      */       public void dispose()
/*      */       {
/* 4652 */         super.unbind(this.val$dependencies);
/*      */       }
/*      */ 
/*      */       protected boolean computeValue()
/*      */       {
/* 4657 */         String str1 = Bindings.getStringSafe((String)paramObservableStringValue1.get());
/* 4658 */         String str2 = Bindings.getStringSafe((String)paramObservableStringValue2.get());
/* 4659 */         return str1.equals(str2);
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/* 4665 */         return this.val$dependencies.length == 1 ? FXCollections.singletonObservableList(this.val$dependencies[0]) : new ImmutableObservableList(this.val$dependencies);
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static BooleanBinding equal(ObservableStringValue paramObservableStringValue1, ObservableStringValue paramObservableStringValue2)
/*      */   {
/* 4689 */     return equal(paramObservableStringValue1, paramObservableStringValue2, new Observable[] { paramObservableStringValue1, paramObservableStringValue2 });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding equal(ObservableStringValue paramObservableStringValue, String paramString)
/*      */   {
/* 4709 */     return equal(paramObservableStringValue, StringConstant.valueOf(paramString), new Observable[] { paramObservableStringValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding equal(String paramString, ObservableStringValue paramObservableStringValue)
/*      */   {
/* 4729 */     return equal(StringConstant.valueOf(paramString), paramObservableStringValue, new Observable[] { paramObservableStringValue });
/*      */   }
/*      */ 
/*      */   private static BooleanBinding notEqual(final ObservableStringValue paramObservableStringValue1, final ObservableStringValue paramObservableStringValue2, Observable[] paramArrayOfObservable) {
/* 4733 */     if ((paramObservableStringValue1 == null) || (paramObservableStringValue2 == null)) {
/* 4734 */       throw new NullPointerException("Operands cannot be null.");
/*      */     }
/* 4736 */     assert ((paramArrayOfObservable != null) && (paramArrayOfObservable.length > 0));
/*      */ 
/* 4738 */     return new BooleanBinding()
/*      */     {
/*      */       public void dispose()
/*      */       {
/* 4745 */         super.unbind(this.val$dependencies);
/*      */       }
/*      */ 
/*      */       protected boolean computeValue()
/*      */       {
/* 4750 */         String str1 = Bindings.getStringSafe((String)paramObservableStringValue1.get());
/* 4751 */         String str2 = Bindings.getStringSafe((String)paramObservableStringValue2.get());
/* 4752 */         return !str1.equals(str2);
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/* 4758 */         return this.val$dependencies.length == 1 ? FXCollections.singletonObservableList(this.val$dependencies[0]) : new ImmutableObservableList(this.val$dependencies);
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static BooleanBinding notEqual(ObservableStringValue paramObservableStringValue1, ObservableStringValue paramObservableStringValue2)
/*      */   {
/* 4782 */     return notEqual(paramObservableStringValue1, paramObservableStringValue2, new Observable[] { paramObservableStringValue1, paramObservableStringValue2 });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding notEqual(ObservableStringValue paramObservableStringValue, String paramString)
/*      */   {
/* 4802 */     return notEqual(paramObservableStringValue, StringConstant.valueOf(paramString), new Observable[] { paramObservableStringValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding notEqual(String paramString, ObservableStringValue paramObservableStringValue)
/*      */   {
/* 4822 */     return notEqual(StringConstant.valueOf(paramString), paramObservableStringValue, new Observable[] { paramObservableStringValue });
/*      */   }
/*      */ 
/*      */   private static BooleanBinding equalIgnoreCase(final ObservableStringValue paramObservableStringValue1, final ObservableStringValue paramObservableStringValue2, Observable[] paramArrayOfObservable) {
/* 4826 */     if ((paramObservableStringValue1 == null) || (paramObservableStringValue2 == null)) {
/* 4827 */       throw new NullPointerException("Operands cannot be null.");
/*      */     }
/* 4829 */     assert ((paramArrayOfObservable != null) && (paramArrayOfObservable.length > 0));
/*      */ 
/* 4831 */     return new BooleanBinding()
/*      */     {
/*      */       public void dispose()
/*      */       {
/* 4838 */         super.unbind(this.val$dependencies);
/*      */       }
/*      */ 
/*      */       protected boolean computeValue()
/*      */       {
/* 4843 */         String str1 = Bindings.getStringSafe((String)paramObservableStringValue1.get());
/* 4844 */         String str2 = Bindings.getStringSafe((String)paramObservableStringValue2.get());
/* 4845 */         return str1.equalsIgnoreCase(str2);
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/* 4851 */         return this.val$dependencies.length == 1 ? FXCollections.singletonObservableList(this.val$dependencies[0]) : new ImmutableObservableList(this.val$dependencies);
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static BooleanBinding equalIgnoreCase(ObservableStringValue paramObservableStringValue1, ObservableStringValue paramObservableStringValue2)
/*      */   {
/* 4875 */     return equalIgnoreCase(paramObservableStringValue1, paramObservableStringValue2, new Observable[] { paramObservableStringValue1, paramObservableStringValue2 });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding equalIgnoreCase(ObservableStringValue paramObservableStringValue, String paramString)
/*      */   {
/* 4895 */     return equalIgnoreCase(paramObservableStringValue, StringConstant.valueOf(paramString), new Observable[] { paramObservableStringValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding equalIgnoreCase(String paramString, ObservableStringValue paramObservableStringValue)
/*      */   {
/* 4915 */     return equalIgnoreCase(StringConstant.valueOf(paramString), paramObservableStringValue, new Observable[] { paramObservableStringValue });
/*      */   }
/*      */ 
/*      */   private static BooleanBinding notEqualIgnoreCase(final ObservableStringValue paramObservableStringValue1, final ObservableStringValue paramObservableStringValue2, Observable[] paramArrayOfObservable) {
/* 4919 */     if ((paramObservableStringValue1 == null) || (paramObservableStringValue2 == null)) {
/* 4920 */       throw new NullPointerException("Operands cannot be null.");
/*      */     }
/* 4922 */     assert ((paramArrayOfObservable != null) && (paramArrayOfObservable.length > 0));
/*      */ 
/* 4924 */     return new BooleanBinding()
/*      */     {
/*      */       public void dispose()
/*      */       {
/* 4931 */         super.unbind(this.val$dependencies);
/*      */       }
/*      */ 
/*      */       protected boolean computeValue()
/*      */       {
/* 4936 */         String str1 = Bindings.getStringSafe((String)paramObservableStringValue1.get());
/* 4937 */         String str2 = Bindings.getStringSafe((String)paramObservableStringValue2.get());
/* 4938 */         return !str1.equalsIgnoreCase(str2);
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/* 4944 */         return this.val$dependencies.length == 1 ? FXCollections.singletonObservableList(this.val$dependencies[0]) : new ImmutableObservableList(this.val$dependencies);
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static BooleanBinding notEqualIgnoreCase(ObservableStringValue paramObservableStringValue1, ObservableStringValue paramObservableStringValue2)
/*      */   {
/* 4969 */     return notEqualIgnoreCase(paramObservableStringValue1, paramObservableStringValue2, new Observable[] { paramObservableStringValue1, paramObservableStringValue2 });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding notEqualIgnoreCase(ObservableStringValue paramObservableStringValue, String paramString)
/*      */   {
/* 4989 */     return notEqualIgnoreCase(paramObservableStringValue, StringConstant.valueOf(paramString), new Observable[] { paramObservableStringValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding notEqualIgnoreCase(String paramString, ObservableStringValue paramObservableStringValue)
/*      */   {
/* 5009 */     return notEqualIgnoreCase(StringConstant.valueOf(paramString), paramObservableStringValue, new Observable[] { paramObservableStringValue });
/*      */   }
/*      */ 
/*      */   private static BooleanBinding greaterThan(final ObservableStringValue paramObservableStringValue1, final ObservableStringValue paramObservableStringValue2, Observable[] paramArrayOfObservable) {
/* 5013 */     if ((paramObservableStringValue1 == null) || (paramObservableStringValue2 == null)) {
/* 5014 */       throw new NullPointerException("Operands cannot be null.");
/*      */     }
/* 5016 */     assert ((paramArrayOfObservable != null) && (paramArrayOfObservable.length > 0));
/*      */ 
/* 5018 */     return new BooleanBinding()
/*      */     {
/*      */       public void dispose()
/*      */       {
/* 5025 */         super.unbind(this.val$dependencies);
/*      */       }
/*      */ 
/*      */       protected boolean computeValue()
/*      */       {
/* 5030 */         String str1 = Bindings.getStringSafe((String)paramObservableStringValue1.get());
/* 5031 */         String str2 = Bindings.getStringSafe((String)paramObservableStringValue2.get());
/* 5032 */         return str1.compareTo(str2) > 0;
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/* 5038 */         return this.val$dependencies.length == 1 ? FXCollections.singletonObservableList(this.val$dependencies[0]) : new ImmutableObservableList(this.val$dependencies);
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static BooleanBinding greaterThan(ObservableStringValue paramObservableStringValue1, ObservableStringValue paramObservableStringValue2)
/*      */   {
/* 5063 */     return greaterThan(paramObservableStringValue1, paramObservableStringValue2, new Observable[] { paramObservableStringValue1, paramObservableStringValue2 });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding greaterThan(ObservableStringValue paramObservableStringValue, String paramString)
/*      */   {
/* 5083 */     return greaterThan(paramObservableStringValue, StringConstant.valueOf(paramString), new Observable[] { paramObservableStringValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding greaterThan(String paramString, ObservableStringValue paramObservableStringValue)
/*      */   {
/* 5103 */     return greaterThan(StringConstant.valueOf(paramString), paramObservableStringValue, new Observable[] { paramObservableStringValue });
/*      */   }
/*      */ 
/*      */   private static BooleanBinding lessThan(ObservableStringValue paramObservableStringValue1, ObservableStringValue paramObservableStringValue2, Observable[] paramArrayOfObservable) {
/* 5107 */     return greaterThan(paramObservableStringValue2, paramObservableStringValue1, paramArrayOfObservable);
/*      */   }
/*      */ 
/*      */   public static BooleanBinding lessThan(ObservableStringValue paramObservableStringValue1, ObservableStringValue paramObservableStringValue2)
/*      */   {
/* 5128 */     return lessThan(paramObservableStringValue1, paramObservableStringValue2, new Observable[] { paramObservableStringValue1, paramObservableStringValue2 });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding lessThan(ObservableStringValue paramObservableStringValue, String paramString)
/*      */   {
/* 5148 */     return lessThan(paramObservableStringValue, StringConstant.valueOf(paramString), new Observable[] { paramObservableStringValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding lessThan(String paramString, ObservableStringValue paramObservableStringValue)
/*      */   {
/* 5168 */     return lessThan(StringConstant.valueOf(paramString), paramObservableStringValue, new Observable[] { paramObservableStringValue });
/*      */   }
/*      */ 
/*      */   private static BooleanBinding greaterThanOrEqual(final ObservableStringValue paramObservableStringValue1, final ObservableStringValue paramObservableStringValue2, Observable[] paramArrayOfObservable) {
/* 5172 */     if ((paramObservableStringValue1 == null) || (paramObservableStringValue2 == null)) {
/* 5173 */       throw new NullPointerException("Operands cannot be null.");
/*      */     }
/* 5175 */     assert ((paramArrayOfObservable != null) && (paramArrayOfObservable.length > 0));
/*      */ 
/* 5177 */     return new BooleanBinding()
/*      */     {
/*      */       public void dispose()
/*      */       {
/* 5184 */         super.unbind(this.val$dependencies);
/*      */       }
/*      */ 
/*      */       protected boolean computeValue()
/*      */       {
/* 5189 */         String str1 = Bindings.getStringSafe((String)paramObservableStringValue1.get());
/* 5190 */         String str2 = Bindings.getStringSafe((String)paramObservableStringValue2.get());
/* 5191 */         return str1.compareTo(str2) >= 0;
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/* 5197 */         return this.val$dependencies.length == 1 ? FXCollections.singletonObservableList(this.val$dependencies[0]) : new ImmutableObservableList(this.val$dependencies);
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static BooleanBinding greaterThanOrEqual(ObservableStringValue paramObservableStringValue1, ObservableStringValue paramObservableStringValue2)
/*      */   {
/* 5222 */     return greaterThanOrEqual(paramObservableStringValue1, paramObservableStringValue2, new Observable[] { paramObservableStringValue1, paramObservableStringValue2 });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding greaterThanOrEqual(ObservableStringValue paramObservableStringValue, String paramString)
/*      */   {
/* 5242 */     return greaterThanOrEqual(paramObservableStringValue, StringConstant.valueOf(paramString), new Observable[] { paramObservableStringValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding greaterThanOrEqual(String paramString, ObservableStringValue paramObservableStringValue)
/*      */   {
/* 5262 */     return greaterThanOrEqual(StringConstant.valueOf(paramString), paramObservableStringValue, new Observable[] { paramObservableStringValue });
/*      */   }
/*      */ 
/*      */   private static BooleanBinding lessThanOrEqual(ObservableStringValue paramObservableStringValue1, ObservableStringValue paramObservableStringValue2, Observable[] paramArrayOfObservable) {
/* 5266 */     return greaterThanOrEqual(paramObservableStringValue2, paramObservableStringValue1, paramArrayOfObservable);
/*      */   }
/*      */ 
/*      */   public static BooleanBinding lessThanOrEqual(ObservableStringValue paramObservableStringValue1, ObservableStringValue paramObservableStringValue2)
/*      */   {
/* 5287 */     return lessThanOrEqual(paramObservableStringValue1, paramObservableStringValue2, new Observable[] { paramObservableStringValue1, paramObservableStringValue2 });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding lessThanOrEqual(ObservableStringValue paramObservableStringValue, String paramString)
/*      */   {
/* 5307 */     return lessThanOrEqual(paramObservableStringValue, StringConstant.valueOf(paramString), new Observable[] { paramObservableStringValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding lessThanOrEqual(String paramString, ObservableStringValue paramObservableStringValue)
/*      */   {
/* 5327 */     return lessThanOrEqual(StringConstant.valueOf(paramString), paramObservableStringValue, new Observable[] { paramObservableStringValue });
/*      */   }
/*      */ 
/*      */   private static BooleanBinding equal(final ObservableObjectValue<?> paramObservableObjectValue1, final ObservableObjectValue<?> paramObservableObjectValue2, Observable[] paramArrayOfObservable)
/*      */   {
/* 5334 */     if ((paramObservableObjectValue1 == null) || (paramObservableObjectValue2 == null)) {
/* 5335 */       throw new NullPointerException("Operands cannot be null.");
/*      */     }
/* 5337 */     assert ((paramArrayOfObservable != null) && (paramArrayOfObservable.length > 0));
/*      */ 
/* 5339 */     return new BooleanBinding()
/*      */     {
/*      */       public void dispose()
/*      */       {
/* 5346 */         super.unbind(this.val$dependencies);
/*      */       }
/*      */ 
/*      */       protected boolean computeValue()
/*      */       {
/* 5351 */         Object localObject1 = paramObservableObjectValue1.get();
/* 5352 */         Object localObject2 = paramObservableObjectValue2.get();
/* 5353 */         return localObject1 == null ? false : localObject2 == null ? true : localObject1.equals(localObject2);
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/* 5359 */         return this.val$dependencies.length == 1 ? FXCollections.singletonObservableList(this.val$dependencies[0]) : new ImmutableObservableList(this.val$dependencies);
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static BooleanBinding equal(ObservableObjectValue<?> paramObservableObjectValue1, ObservableObjectValue<?> paramObservableObjectValue2)
/*      */   {
/* 5380 */     return equal(paramObservableObjectValue1, paramObservableObjectValue2, new Observable[] { paramObservableObjectValue1, paramObservableObjectValue2 });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding equal(ObservableObjectValue<?> paramObservableObjectValue, Object paramObject)
/*      */   {
/* 5397 */     return equal(paramObservableObjectValue, ObjectConstant.valueOf(paramObject), new Observable[] { paramObservableObjectValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding equal(Object paramObject, ObservableObjectValue<?> paramObservableObjectValue)
/*      */   {
/* 5414 */     return equal(ObjectConstant.valueOf(paramObject), paramObservableObjectValue, new Observable[] { paramObservableObjectValue });
/*      */   }
/*      */ 
/*      */   private static BooleanBinding notEqual(final ObservableObjectValue<?> paramObservableObjectValue1, final ObservableObjectValue<?> paramObservableObjectValue2, Observable[] paramArrayOfObservable) {
/* 5418 */     if ((paramObservableObjectValue1 == null) || (paramObservableObjectValue2 == null)) {
/* 5419 */       throw new NullPointerException("Operands cannot be null.");
/*      */     }
/* 5421 */     assert ((paramArrayOfObservable != null) && (paramArrayOfObservable.length > 0));
/*      */ 
/* 5423 */     return new BooleanBinding()
/*      */     {
/*      */       public void dispose()
/*      */       {
/* 5430 */         super.unbind(this.val$dependencies);
/*      */       }
/*      */ 
/*      */       protected boolean computeValue()
/*      */       {
/* 5435 */         Object localObject1 = paramObservableObjectValue1.get();
/* 5436 */         Object localObject2 = paramObservableObjectValue2.get();
/* 5437 */         return localObject2 != null;
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/* 5443 */         return this.val$dependencies.length == 1 ? FXCollections.singletonObservableList(this.val$dependencies[0]) : new ImmutableObservableList(this.val$dependencies);
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static BooleanBinding notEqual(ObservableObjectValue<?> paramObservableObjectValue1, ObservableObjectValue<?> paramObservableObjectValue2)
/*      */   {
/* 5464 */     return notEqual(paramObservableObjectValue1, paramObservableObjectValue2, new Observable[] { paramObservableObjectValue1, paramObservableObjectValue2 });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding notEqual(ObservableObjectValue<?> paramObservableObjectValue, Object paramObject)
/*      */   {
/* 5481 */     return notEqual(paramObservableObjectValue, ObjectConstant.valueOf(paramObject), new Observable[] { paramObservableObjectValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding notEqual(Object paramObject, ObservableObjectValue<?> paramObservableObjectValue)
/*      */   {
/* 5498 */     return notEqual(ObjectConstant.valueOf(paramObject), paramObservableObjectValue, new Observable[] { paramObservableObjectValue });
/*      */   }
/*      */ 
/*      */   public static BooleanBinding isNull(ObservableObjectValue<?> paramObservableObjectValue)
/*      */   {
/* 5513 */     if (paramObservableObjectValue == null) {
/* 5514 */       throw new NullPointerException("Operand cannot be null.");
/*      */     }
/*      */ 
/* 5517 */     return new BooleanBinding()
/*      */     {
/*      */       public void dispose()
/*      */       {
/* 5524 */         super.unbind(new Observable[] { this.val$op });
/*      */       }
/*      */ 
/*      */       protected boolean computeValue()
/*      */       {
/* 5529 */         return this.val$op.get() == null;
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/* 5535 */         return FXCollections.singletonObservableList(this.val$op);
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static BooleanBinding isNotNull(ObservableObjectValue<?> paramObservableObjectValue)
/*      */   {
/* 5552 */     if (paramObservableObjectValue == null) {
/* 5553 */       throw new NullPointerException("Operand cannot be null.");
/*      */     }
/*      */ 
/* 5556 */     return new BooleanBinding()
/*      */     {
/*      */       public void dispose()
/*      */       {
/* 5563 */         super.unbind(new Observable[] { this.val$op });
/*      */       }
/*      */ 
/*      */       protected boolean computeValue()
/*      */       {
/* 5568 */         return this.val$op.get() != null;
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/* 5574 */         return FXCollections.singletonObservableList(this.val$op);
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static <E> IntegerBinding size(ObservableList<E> paramObservableList)
/*      */   {
/* 5594 */     if (paramObservableList == null) {
/* 5595 */       throw new NullPointerException("List cannot be null.");
/*      */     }
/*      */ 
/* 5598 */     return new IntegerBinding()
/*      */     {
/*      */       public void dispose()
/*      */       {
/* 5605 */         super.unbind(new Observable[] { this.val$op });
/*      */       }
/*      */ 
/*      */       protected int computeValue()
/*      */       {
/* 5610 */         return this.val$op.size();
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/* 5616 */         return FXCollections.singletonObservableList(this.val$op);
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static <E> BooleanBinding isEmpty(ObservableList<E> paramObservableList)
/*      */   {
/* 5634 */     if (paramObservableList == null) {
/* 5635 */       throw new NullPointerException("List cannot be null.");
/*      */     }
/*      */ 
/* 5638 */     return new BooleanBinding()
/*      */     {
/*      */       public void dispose()
/*      */       {
/* 5645 */         super.unbind(new Observable[] { this.val$op });
/*      */       }
/*      */ 
/*      */       protected boolean computeValue()
/*      */       {
/* 5650 */         return this.val$op.isEmpty();
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/* 5656 */         return FXCollections.singletonObservableList(this.val$op);
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static <E> ObjectBinding<E> valueAt(ObservableList<E> paramObservableList, final int paramInt)
/*      */   {
/* 5674 */     if (paramObservableList == null) {
/* 5675 */       throw new NullPointerException("List cannot be null.");
/*      */     }
/* 5677 */     if (paramInt < 0) {
/* 5678 */       throw new IllegalArgumentException("Index cannot be negative");
/*      */     }
/*      */ 
/* 5681 */     return new ObjectBinding()
/*      */     {
/*      */       public void dispose()
/*      */       {
/* 5688 */         super.unbind(new Observable[] { this.val$op });
/*      */       }
/*      */ 
/*      */       protected E computeValue()
/*      */       {
/* 5693 */         return paramInt >= this.val$op.size() ? null : this.val$op.get(paramInt);
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/* 5699 */         return FXCollections.singletonObservableList(this.val$op);
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static <E> ObjectBinding<E> valueAt(ObservableList<E> paramObservableList, final ObservableIntegerValue paramObservableIntegerValue)
/*      */   {
/* 5716 */     if ((paramObservableList == null) || (paramObservableIntegerValue == null)) {
/* 5717 */       throw new NullPointerException("Operands cannot be null.");
/*      */     }
/*      */ 
/* 5720 */     return new ObjectBinding()
/*      */     {
/*      */       public void dispose()
/*      */       {
/* 5727 */         super.unbind(new Observable[] { this.val$op, paramObservableIntegerValue });
/*      */       }
/*      */ 
/*      */       protected E computeValue()
/*      */       {
/* 5732 */         int i = paramObservableIntegerValue.get();
/* 5733 */         return (i < 0) || (i >= this.val$op.size()) ? null : this.val$op.get(i);
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/* 5739 */         return new ImmutableObservableList(new Observable[] { this.val$op, paramObservableIntegerValue });
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static BooleanBinding booleanValueAt(ObservableList<Boolean> paramObservableList, final int paramInt)
/*      */   {
/* 5756 */     if (paramObservableList == null) {
/* 5757 */       throw new NullPointerException("List cannot be null.");
/*      */     }
/* 5759 */     if (paramInt < 0) {
/* 5760 */       throw new IllegalArgumentException("Index cannot be negative");
/*      */     }
/*      */ 
/* 5763 */     return new BooleanBinding()
/*      */     {
/*      */       public void dispose()
/*      */       {
/* 5770 */         super.unbind(new Observable[] { this.val$op });
/*      */       }
/*      */ 
/*      */       protected boolean computeValue()
/*      */       {
/* 5775 */         return paramInt >= this.val$op.size() ? false : ((Boolean)this.val$op.get(paramInt)).booleanValue();
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/* 5781 */         return FXCollections.singletonObservableList(this.val$op);
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static BooleanBinding booleanValueAt(ObservableList<Boolean> paramObservableList, final ObservableIntegerValue paramObservableIntegerValue)
/*      */   {
/* 5797 */     if ((paramObservableList == null) || (paramObservableIntegerValue == null)) {
/* 5798 */       throw new NullPointerException("Operands cannot be null.");
/*      */     }
/*      */ 
/* 5801 */     return new BooleanBinding()
/*      */     {
/*      */       public void dispose()
/*      */       {
/* 5808 */         super.unbind(new Observable[] { this.val$op, paramObservableIntegerValue });
/*      */       }
/*      */ 
/*      */       protected boolean computeValue()
/*      */       {
/* 5813 */         int i = paramObservableIntegerValue.get();
/* 5814 */         return (i < 0) || (i >= this.val$op.size()) ? false : ((Boolean)this.val$op.get(i)).booleanValue();
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/* 5820 */         return new ImmutableObservableList(new Observable[] { this.val$op, paramObservableIntegerValue });
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static DoubleBinding doubleValueAt(ObservableList<? extends Number> paramObservableList, final int paramInt)
/*      */   {
/* 5837 */     if (paramObservableList == null) {
/* 5838 */       throw new NullPointerException("List cannot be null.");
/*      */     }
/* 5840 */     if (paramInt < 0) {
/* 5841 */       throw new IllegalArgumentException("Index cannot be negative");
/*      */     }
/*      */ 
/* 5844 */     return new DoubleBinding()
/*      */     {
/*      */       public void dispose()
/*      */       {
/* 5851 */         super.unbind(new Observable[] { this.val$op });
/*      */       }
/*      */ 
/*      */       protected double computeValue()
/*      */       {
/* 5856 */         return paramInt >= this.val$op.size() ? 0.0D : ((Number)this.val$op.get(paramInt)).doubleValue();
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/* 5862 */         return FXCollections.singletonObservableList(this.val$op);
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static DoubleBinding doubleValueAt(ObservableList<? extends Number> paramObservableList, final ObservableIntegerValue paramObservableIntegerValue)
/*      */   {
/* 5878 */     if ((paramObservableList == null) || (paramObservableIntegerValue == null)) {
/* 5879 */       throw new NullPointerException("Operands cannot be null.");
/*      */     }
/*      */ 
/* 5882 */     return new DoubleBinding()
/*      */     {
/*      */       public void dispose()
/*      */       {
/* 5889 */         super.unbind(new Observable[] { this.val$op, paramObservableIntegerValue });
/*      */       }
/*      */ 
/*      */       protected double computeValue()
/*      */       {
/* 5894 */         int i = paramObservableIntegerValue.get();
/* 5895 */         return (i < 0) || (i >= this.val$op.size()) ? 0.0D : ((Number)this.val$op.get(i)).doubleValue();
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/* 5901 */         return new ImmutableObservableList(new Observable[] { this.val$op, paramObservableIntegerValue });
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static FloatBinding floatValueAt(ObservableList<? extends Number> paramObservableList, final int paramInt)
/*      */   {
/* 5918 */     if (paramObservableList == null) {
/* 5919 */       throw new NullPointerException("List cannot be null.");
/*      */     }
/* 5921 */     if (paramInt < 0) {
/* 5922 */       throw new IllegalArgumentException("Index cannot be negative");
/*      */     }
/*      */ 
/* 5925 */     return new FloatBinding()
/*      */     {
/*      */       public void dispose()
/*      */       {
/* 5932 */         super.unbind(new Observable[] { this.val$op });
/*      */       }
/*      */ 
/*      */       protected float computeValue()
/*      */       {
/* 5937 */         return paramInt >= this.val$op.size() ? 0.0F : ((Number)this.val$op.get(paramInt)).floatValue();
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/* 5943 */         return FXCollections.singletonObservableList(this.val$op);
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static FloatBinding floatValueAt(ObservableList<? extends Number> paramObservableList, final ObservableIntegerValue paramObservableIntegerValue)
/*      */   {
/* 5959 */     if ((paramObservableList == null) || (paramObservableIntegerValue == null)) {
/* 5960 */       throw new NullPointerException("Operands cannot be null.");
/*      */     }
/*      */ 
/* 5963 */     return new FloatBinding()
/*      */     {
/*      */       public void dispose()
/*      */       {
/* 5970 */         super.unbind(new Observable[] { this.val$op, paramObservableIntegerValue });
/*      */       }
/*      */ 
/*      */       protected float computeValue()
/*      */       {
/* 5975 */         int i = paramObservableIntegerValue.get();
/* 5976 */         return (i < 0) || (i >= this.val$op.size()) ? 0.0F : ((Number)this.val$op.get(i)).floatValue();
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/* 5982 */         return new ImmutableObservableList(new Observable[] { this.val$op, paramObservableIntegerValue });
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static IntegerBinding integerValueAt(ObservableList<? extends Number> paramObservableList, final int paramInt)
/*      */   {
/* 5999 */     if (paramObservableList == null) {
/* 6000 */       throw new NullPointerException("List cannot be null.");
/*      */     }
/* 6002 */     if (paramInt < 0) {
/* 6003 */       throw new IllegalArgumentException("Index cannot be negative");
/*      */     }
/*      */ 
/* 6006 */     return new IntegerBinding()
/*      */     {
/*      */       public void dispose()
/*      */       {
/* 6013 */         super.unbind(new Observable[] { this.val$op });
/*      */       }
/*      */ 
/*      */       protected int computeValue()
/*      */       {
/* 6018 */         return paramInt >= this.val$op.size() ? 0 : ((Number)this.val$op.get(paramInt)).intValue();
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/* 6024 */         return FXCollections.singletonObservableList(this.val$op);
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static IntegerBinding integerValueAt(ObservableList<? extends Number> paramObservableList, final ObservableIntegerValue paramObservableIntegerValue)
/*      */   {
/* 6040 */     if ((paramObservableList == null) || (paramObservableIntegerValue == null)) {
/* 6041 */       throw new NullPointerException("Operands cannot be null.");
/*      */     }
/*      */ 
/* 6044 */     return new IntegerBinding()
/*      */     {
/*      */       public void dispose()
/*      */       {
/* 6051 */         super.unbind(new Observable[] { this.val$op, paramObservableIntegerValue });
/*      */       }
/*      */ 
/*      */       protected int computeValue()
/*      */       {
/* 6056 */         int i = paramObservableIntegerValue.get();
/* 6057 */         return (i < 0) || (i >= this.val$op.size()) ? 0 : ((Number)this.val$op.get(i)).intValue();
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/* 6063 */         return new ImmutableObservableList(new Observable[] { this.val$op, paramObservableIntegerValue });
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static LongBinding longValueAt(ObservableList<? extends Number> paramObservableList, final int paramInt)
/*      */   {
/* 6080 */     if (paramObservableList == null) {
/* 6081 */       throw new NullPointerException("List cannot be null.");
/*      */     }
/* 6083 */     if (paramInt < 0) {
/* 6084 */       throw new IllegalArgumentException("Index cannot be negative");
/*      */     }
/*      */ 
/* 6087 */     return new LongBinding()
/*      */     {
/*      */       public void dispose()
/*      */       {
/* 6094 */         super.unbind(new Observable[] { this.val$op });
/*      */       }
/*      */ 
/*      */       protected long computeValue()
/*      */       {
/* 6099 */         return paramInt >= this.val$op.size() ? 0L : ((Number)this.val$op.get(paramInt)).longValue();
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/* 6105 */         return FXCollections.singletonObservableList(this.val$op);
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static LongBinding longValueAt(ObservableList<? extends Number> paramObservableList, final ObservableIntegerValue paramObservableIntegerValue)
/*      */   {
/* 6121 */     if ((paramObservableList == null) || (paramObservableIntegerValue == null)) {
/* 6122 */       throw new NullPointerException("Operands cannot be null.");
/*      */     }
/*      */ 
/* 6125 */     return new LongBinding()
/*      */     {
/*      */       public void dispose()
/*      */       {
/* 6132 */         super.unbind(new Observable[] { this.val$op, paramObservableIntegerValue });
/*      */       }
/*      */ 
/*      */       protected long computeValue()
/*      */       {
/* 6137 */         int i = paramObservableIntegerValue.get();
/* 6138 */         return (i < 0) || (i >= this.val$op.size()) ? 0L : ((Number)this.val$op.get(i)).longValue();
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/* 6144 */         return new ImmutableObservableList(new Observable[] { this.val$op, paramObservableIntegerValue });
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static StringBinding stringValueAt(ObservableList<String> paramObservableList, final int paramInt)
/*      */   {
/* 6161 */     if (paramObservableList == null) {
/* 6162 */       throw new NullPointerException("List cannot be null.");
/*      */     }
/* 6164 */     if (paramInt < 0) {
/* 6165 */       throw new IllegalArgumentException("Index cannot be negative");
/*      */     }
/*      */ 
/* 6168 */     return new StringBinding()
/*      */     {
/*      */       public void dispose()
/*      */       {
/* 6175 */         super.unbind(new Observable[] { this.val$op });
/*      */       }
/*      */ 
/*      */       protected String computeValue()
/*      */       {
/* 6180 */         return paramInt >= this.val$op.size() ? null : (String)this.val$op.get(paramInt);
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/* 6186 */         return FXCollections.singletonObservableList(this.val$op);
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static StringBinding stringValueAt(ObservableList<String> paramObservableList, final ObservableIntegerValue paramObservableIntegerValue)
/*      */   {
/* 6202 */     if ((paramObservableList == null) || (paramObservableIntegerValue == null)) {
/* 6203 */       throw new NullPointerException("Operands cannot be null.");
/*      */     }
/*      */ 
/* 6206 */     return new StringBinding()
/*      */     {
/*      */       public void dispose()
/*      */       {
/* 6213 */         super.unbind(new Observable[] { this.val$op, paramObservableIntegerValue });
/*      */       }
/*      */ 
/*      */       protected String computeValue()
/*      */       {
/* 6218 */         int i = paramObservableIntegerValue.get();
/* 6219 */         return (i < 0) || (i >= this.val$op.size()) ? null : (String)this.val$op.get(i);
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/* 6225 */         return new ImmutableObservableList(new Observable[] { this.val$op, paramObservableIntegerValue });
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static <E> IntegerBinding size(ObservableSet<E> paramObservableSet)
/*      */   {
/* 6245 */     if (paramObservableSet == null) {
/* 6246 */       throw new NullPointerException("Set cannot be null.");
/*      */     }
/*      */ 
/* 6249 */     return new IntegerBinding()
/*      */     {
/*      */       public void dispose()
/*      */       {
/* 6256 */         super.unbind(new Observable[] { this.val$op });
/*      */       }
/*      */ 
/*      */       protected int computeValue()
/*      */       {
/* 6261 */         return this.val$op.size();
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/* 6267 */         return FXCollections.singletonObservableList(this.val$op);
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static <E> BooleanBinding isEmpty(ObservableSet<E> paramObservableSet)
/*      */   {
/* 6284 */     if (paramObservableSet == null) {
/* 6285 */       throw new NullPointerException("Set cannot be null.");
/*      */     }
/*      */ 
/* 6288 */     return new BooleanBinding()
/*      */     {
/*      */       public void dispose()
/*      */       {
/* 6295 */         super.unbind(new Observable[] { this.val$op });
/*      */       }
/*      */ 
/*      */       protected boolean computeValue()
/*      */       {
/* 6300 */         return this.val$op.isEmpty();
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/* 6306 */         return FXCollections.singletonObservableList(this.val$op);
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static <K, V> IntegerBinding size(ObservableMap<K, V> paramObservableMap)
/*      */   {
/* 6328 */     if (paramObservableMap == null) {
/* 6329 */       throw new NullPointerException("Map cannot be null.");
/*      */     }
/*      */ 
/* 6332 */     return new IntegerBinding()
/*      */     {
/*      */       public void dispose()
/*      */       {
/* 6339 */         super.unbind(new Observable[] { this.val$op });
/*      */       }
/*      */ 
/*      */       protected int computeValue()
/*      */       {
/* 6344 */         return this.val$op.size();
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/* 6350 */         return FXCollections.singletonObservableList(this.val$op);
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static <K, V> BooleanBinding isEmpty(ObservableMap<K, V> paramObservableMap)
/*      */   {
/* 6368 */     if (paramObservableMap == null) {
/* 6369 */       throw new NullPointerException("Map cannot be null.");
/*      */     }
/*      */ 
/* 6372 */     return new BooleanBinding()
/*      */     {
/*      */       public void dispose()
/*      */       {
/* 6379 */         super.unbind(new Observable[] { this.val$op });
/*      */       }
/*      */ 
/*      */       protected boolean computeValue()
/*      */       {
/* 6384 */         return this.val$op.isEmpty();
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/* 6390 */         return FXCollections.singletonObservableList(this.val$op);
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static <K, V> ObjectBinding<V> valueAt(ObservableMap<K, V> paramObservableMap, final K paramK)
/*      */   {
/* 6407 */     if (paramObservableMap == null) {
/* 6408 */       throw new NullPointerException("Map cannot be null.");
/*      */     }
/*      */ 
/* 6411 */     return new ObjectBinding()
/*      */     {
/*      */       public void dispose()
/*      */       {
/* 6418 */         super.unbind(new Observable[] { this.val$op });
/*      */       }
/*      */ 
/*      */       protected V computeValue()
/*      */       {
/*      */         try {
/* 6424 */           return this.val$op.get(paramK);
/*      */         }
/*      */         catch (ClassCastException localClassCastException) {
/*      */         }
/*      */         catch (NullPointerException localNullPointerException) {
/*      */         }
/* 6430 */         return null;
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/* 6436 */         return FXCollections.singletonObservableList(this.val$op);
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static <K, V> ObjectBinding<V> valueAt(ObservableMap<K, V> paramObservableMap, final ObservableValue<? extends K> paramObservableValue)
/*      */   {
/* 6453 */     if ((paramObservableMap == null) || (paramObservableValue == null)) {
/* 6454 */       throw new NullPointerException("Operands cannot be null.");
/*      */     }
/*      */ 
/* 6457 */     return new ObjectBinding()
/*      */     {
/*      */       public void dispose()
/*      */       {
/* 6464 */         super.unbind(new Observable[] { this.val$op });
/*      */       }
/*      */ 
/*      */       protected V computeValue()
/*      */       {
/*      */         try {
/* 6470 */           return this.val$op.get(paramObservableValue.getValue());
/*      */         }
/*      */         catch (ClassCastException localClassCastException) {
/*      */         }
/*      */         catch (NullPointerException localNullPointerException) {
/*      */         }
/* 6476 */         return null;
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/* 6482 */         return new ImmutableObservableList(new Observable[] { this.val$op, paramObservableValue });
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static <K> BooleanBinding booleanValueAt(ObservableMap<K, Boolean> paramObservableMap, final K paramK)
/*      */   {
/* 6499 */     if (paramObservableMap == null) {
/* 6500 */       throw new NullPointerException("Map cannot be null.");
/*      */     }
/*      */ 
/* 6503 */     return new BooleanBinding()
/*      */     {
/*      */       public void dispose()
/*      */       {
/* 6510 */         super.unbind(new Observable[] { this.val$op });
/*      */       }
/*      */ 
/*      */       protected boolean computeValue()
/*      */       {
/*      */         try {
/* 6516 */           return ((Boolean)this.val$op.get(paramK)).booleanValue();
/*      */         }
/*      */         catch (ClassCastException localClassCastException) {
/*      */         }
/*      */         catch (NullPointerException localNullPointerException) {
/*      */         }
/* 6522 */         return false;
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/* 6528 */         return FXCollections.singletonObservableList(this.val$op);
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static <K> BooleanBinding booleanValueAt(ObservableMap<K, Boolean> paramObservableMap, final ObservableValue<? extends K> paramObservableValue)
/*      */   {
/* 6545 */     if ((paramObservableMap == null) || (paramObservableValue == null)) {
/* 6546 */       throw new NullPointerException("Operands cannot be null.");
/*      */     }
/*      */ 
/* 6549 */     return new BooleanBinding()
/*      */     {
/*      */       public void dispose()
/*      */       {
/* 6556 */         super.unbind(new Observable[] { this.val$op, paramObservableValue });
/*      */       }
/*      */ 
/*      */       protected boolean computeValue()
/*      */       {
/*      */         try {
/* 6562 */           return ((Boolean)this.val$op.get(paramObservableValue.getValue())).booleanValue();
/*      */         }
/*      */         catch (ClassCastException localClassCastException) {
/*      */         }
/*      */         catch (NullPointerException localNullPointerException) {
/*      */         }
/* 6568 */         return false;
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/* 6574 */         return new ImmutableObservableList(new Observable[] { this.val$op, paramObservableValue });
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static <K> DoubleBinding doubleValueAt(ObservableMap<K, ? extends Number> paramObservableMap, final K paramK)
/*      */   {
/* 6591 */     if (paramObservableMap == null) {
/* 6592 */       throw new NullPointerException("Map cannot be null.");
/*      */     }
/*      */ 
/* 6595 */     return new DoubleBinding()
/*      */     {
/*      */       public void dispose()
/*      */       {
/* 6602 */         super.unbind(new Observable[] { this.val$op });
/*      */       }
/*      */ 
/*      */       protected double computeValue()
/*      */       {
/*      */         try {
/* 6608 */           return ((Number)this.val$op.get(paramK)).doubleValue();
/*      */         }
/*      */         catch (ClassCastException localClassCastException) {
/*      */         }
/*      */         catch (NullPointerException localNullPointerException) {
/*      */         }
/* 6614 */         return 0.0D;
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/* 6620 */         return FXCollections.singletonObservableList(this.val$op);
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static <K> DoubleBinding doubleValueAt(ObservableMap<K, ? extends Number> paramObservableMap, final ObservableValue<? extends K> paramObservableValue)
/*      */   {
/* 6637 */     if ((paramObservableMap == null) || (paramObservableValue == null)) {
/* 6638 */       throw new NullPointerException("Operands cannot be null.");
/*      */     }
/*      */ 
/* 6641 */     return new DoubleBinding()
/*      */     {
/*      */       public void dispose()
/*      */       {
/* 6648 */         super.unbind(new Observable[] { this.val$op, paramObservableValue });
/*      */       }
/*      */ 
/*      */       protected double computeValue()
/*      */       {
/*      */         try {
/* 6654 */           return ((Number)this.val$op.get(paramObservableValue.getValue())).doubleValue();
/*      */         }
/*      */         catch (ClassCastException localClassCastException) {
/*      */         }
/*      */         catch (NullPointerException localNullPointerException) {
/*      */         }
/* 6660 */         return 0.0D;
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/* 6666 */         return new ImmutableObservableList(new Observable[] { this.val$op, paramObservableValue });
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static <K> FloatBinding floatValueAt(ObservableMap<K, ? extends Number> paramObservableMap, final K paramK)
/*      */   {
/* 6683 */     if (paramObservableMap == null) {
/* 6684 */       throw new NullPointerException("Map cannot be null.");
/*      */     }
/*      */ 
/* 6687 */     return new FloatBinding()
/*      */     {
/*      */       public void dispose()
/*      */       {
/* 6694 */         super.unbind(new Observable[] { this.val$op });
/*      */       }
/*      */ 
/*      */       protected float computeValue()
/*      */       {
/*      */         try {
/* 6700 */           return ((Number)this.val$op.get(paramK)).floatValue();
/*      */         }
/*      */         catch (ClassCastException localClassCastException) {
/*      */         }
/*      */         catch (NullPointerException localNullPointerException) {
/*      */         }
/* 6706 */         return 0.0F;
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/* 6712 */         return FXCollections.singletonObservableList(this.val$op);
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static <K> FloatBinding floatValueAt(ObservableMap<K, ? extends Number> paramObservableMap, final ObservableValue<? extends K> paramObservableValue)
/*      */   {
/* 6729 */     if ((paramObservableMap == null) || (paramObservableValue == null)) {
/* 6730 */       throw new NullPointerException("Operands cannot be null.");
/*      */     }
/*      */ 
/* 6733 */     return new FloatBinding()
/*      */     {
/*      */       public void dispose()
/*      */       {
/* 6740 */         super.unbind(new Observable[] { this.val$op, paramObservableValue });
/*      */       }
/*      */ 
/*      */       protected float computeValue()
/*      */       {
/*      */         try {
/* 6746 */           return ((Number)this.val$op.get(paramObservableValue.getValue())).floatValue();
/*      */         }
/*      */         catch (ClassCastException localClassCastException) {
/*      */         }
/*      */         catch (NullPointerException localNullPointerException) {
/*      */         }
/* 6752 */         return 0.0F;
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/* 6758 */         return new ImmutableObservableList(new Observable[] { this.val$op, paramObservableValue });
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static <K> IntegerBinding integerValueAt(ObservableMap<K, ? extends Number> paramObservableMap, final K paramK)
/*      */   {
/* 6775 */     if (paramObservableMap == null) {
/* 6776 */       throw new NullPointerException("Map cannot be null.");
/*      */     }
/*      */ 
/* 6779 */     return new IntegerBinding()
/*      */     {
/*      */       public void dispose()
/*      */       {
/* 6786 */         super.unbind(new Observable[] { this.val$op });
/*      */       }
/*      */ 
/*      */       protected int computeValue()
/*      */       {
/*      */         try {
/* 6792 */           return ((Number)this.val$op.get(paramK)).intValue();
/*      */         }
/*      */         catch (ClassCastException localClassCastException) {
/*      */         }
/*      */         catch (NullPointerException localNullPointerException) {
/*      */         }
/* 6798 */         return 0;
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/* 6804 */         return FXCollections.singletonObservableList(this.val$op);
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static <K> IntegerBinding integerValueAt(ObservableMap<K, ? extends Number> paramObservableMap, final ObservableValue<? extends K> paramObservableValue)
/*      */   {
/* 6821 */     if ((paramObservableMap == null) || (paramObservableValue == null)) {
/* 6822 */       throw new NullPointerException("Operands cannot be null.");
/*      */     }
/*      */ 
/* 6825 */     return new IntegerBinding()
/*      */     {
/*      */       public void dispose()
/*      */       {
/* 6832 */         super.unbind(new Observable[] { this.val$op, paramObservableValue });
/*      */       }
/*      */ 
/*      */       protected int computeValue()
/*      */       {
/*      */         try {
/* 6838 */           return ((Number)this.val$op.get(paramObservableValue.getValue())).intValue();
/*      */         }
/*      */         catch (ClassCastException localClassCastException) {
/*      */         }
/*      */         catch (NullPointerException localNullPointerException) {
/*      */         }
/* 6844 */         return 0;
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/* 6850 */         return new ImmutableObservableList(new Observable[] { this.val$op, paramObservableValue });
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static <K> LongBinding longValueAt(ObservableMap<K, ? extends Number> paramObservableMap, final K paramK)
/*      */   {
/* 6867 */     if (paramObservableMap == null) {
/* 6868 */       throw new NullPointerException("Map cannot be null.");
/*      */     }
/*      */ 
/* 6871 */     return new LongBinding()
/*      */     {
/*      */       public void dispose()
/*      */       {
/* 6878 */         super.unbind(new Observable[] { this.val$op });
/*      */       }
/*      */ 
/*      */       protected long computeValue()
/*      */       {
/*      */         try {
/* 6884 */           return ((Number)this.val$op.get(paramK)).longValue();
/*      */         }
/*      */         catch (ClassCastException localClassCastException) {
/*      */         }
/*      */         catch (NullPointerException localNullPointerException) {
/*      */         }
/* 6890 */         return 0L;
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/* 6896 */         return FXCollections.singletonObservableList(this.val$op);
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static <K> LongBinding longValueAt(ObservableMap<K, ? extends Number> paramObservableMap, final ObservableValue<? extends K> paramObservableValue)
/*      */   {
/* 6913 */     if ((paramObservableMap == null) || (paramObservableValue == null)) {
/* 6914 */       throw new NullPointerException("Operands cannot be null.");
/*      */     }
/*      */ 
/* 6917 */     return new LongBinding()
/*      */     {
/*      */       public void dispose()
/*      */       {
/* 6924 */         super.unbind(new Observable[] { this.val$op, paramObservableValue });
/*      */       }
/*      */ 
/*      */       protected long computeValue()
/*      */       {
/*      */         try {
/* 6930 */           return ((Number)this.val$op.get(paramObservableValue.getValue())).longValue();
/*      */         }
/*      */         catch (ClassCastException localClassCastException) {
/*      */         }
/*      */         catch (NullPointerException localNullPointerException) {
/*      */         }
/* 6936 */         return 0L;
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/* 6942 */         return new ImmutableObservableList(new Observable[] { this.val$op, paramObservableValue });
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static <K> StringBinding stringValueAt(ObservableMap<K, String> paramObservableMap, final K paramK)
/*      */   {
/* 6959 */     if (paramObservableMap == null) {
/* 6960 */       throw new NullPointerException("Map cannot be null.");
/*      */     }
/*      */ 
/* 6963 */     return new StringBinding()
/*      */     {
/*      */       public void dispose()
/*      */       {
/* 6970 */         super.unbind(new Observable[] { this.val$op });
/*      */       }
/*      */ 
/*      */       protected String computeValue()
/*      */       {
/*      */         try {
/* 6976 */           return (String)this.val$op.get(paramK);
/*      */         }
/*      */         catch (ClassCastException localClassCastException) {
/*      */         }
/*      */         catch (NullPointerException localNullPointerException) {
/*      */         }
/* 6982 */         return null;
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/* 6988 */         return FXCollections.singletonObservableList(this.val$op);
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static <K> StringBinding stringValueAt(ObservableMap<K, String> paramObservableMap, final ObservableValue<? extends K> paramObservableValue)
/*      */   {
/* 7005 */     if ((paramObservableMap == null) || (paramObservableValue == null)) {
/* 7006 */       throw new NullPointerException("Operands cannot be null.");
/*      */     }
/*      */ 
/* 7009 */     return new StringBinding()
/*      */     {
/*      */       public void dispose()
/*      */       {
/* 7016 */         super.unbind(new Observable[] { this.val$op, paramObservableValue });
/*      */       }
/*      */ 
/*      */       protected String computeValue()
/*      */       {
/*      */         try {
/* 7022 */           return (String)this.val$op.get(paramObservableValue.getValue());
/*      */         }
/*      */         catch (ClassCastException localClassCastException) {
/*      */         }
/*      */         catch (NullPointerException localNullPointerException) {
/*      */         }
/* 7028 */         return null;
/*      */       }
/*      */ 
/*      */       public ObservableList<?> getDependencies()
/*      */       {
/* 7034 */         return new ImmutableObservableList(new Observable[] { this.val$op, paramObservableValue });
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   private static class ShortCircuitOrInvalidator
/*      */     implements InvalidationListener
/*      */   {
/*      */     private final WeakReference<Binding<?>> ref;
/*      */     private final ObservableBooleanValue op1;
/*      */ 
/*      */     private ShortCircuitOrInvalidator(ObservableBooleanValue paramObservableBooleanValue, Binding<?> paramBinding)
/*      */     {
/* 4357 */       assert (paramBinding != null);
/* 4358 */       this.op1 = paramObservableBooleanValue;
/* 4359 */       this.ref = new WeakReference(paramBinding);
/*      */     }
/*      */ 
/*      */     public void invalidated(Observable paramObservable)
/*      */     {
/* 4364 */       Binding localBinding = (Binding)this.ref.get();
/* 4365 */       if (localBinding == null) {
/* 4366 */         paramObservable.removeListener(this);
/*      */       }
/* 4371 */       else if ((this.op1.equals(paramObservable)) || ((localBinding.isValid()) && (!this.op1.get())))
/* 4372 */         localBinding.invalidate();
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class ShortCircuitAndInvalidator
/*      */     implements InvalidationListener
/*      */   {
/*      */     private final WeakReference<Binding<?>> ref;
/*      */     private final ObservableBooleanValue op1;
/*      */ 
/*      */     private ShortCircuitAndInvalidator(ObservableBooleanValue paramObservableBooleanValue, Binding<?> paramBinding)
/*      */     {
/* 4285 */       assert (paramBinding != null);
/* 4286 */       this.op1 = paramObservableBooleanValue;
/* 4287 */       this.ref = new WeakReference(paramBinding);
/*      */     }
/*      */ 
/*      */     public void invalidated(Observable paramObservable)
/*      */     {
/* 4292 */       Binding localBinding = (Binding)this.ref.get();
/* 4293 */       if (localBinding == null) {
/* 4294 */         paramObservable.removeListener(this);
/*      */       }
/* 4299 */       else if ((this.op1.equals(paramObservable)) || ((localBinding.isValid()) && (this.op1.get())))
/* 4300 */         localBinding.invalidate();
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.binding.Bindings
 * JD-Core Version:    0.6.2
 */