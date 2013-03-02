/*     */ package javafx.beans.binding;
/*     */ 
/*     */ import com.sun.javafx.binding.DoubleConstant;
/*     */ import com.sun.javafx.binding.FloatConstant;
/*     */ import com.sun.javafx.binding.IntegerConstant;
/*     */ import com.sun.javafx.binding.LongConstant;
/*     */ import java.lang.ref.WeakReference;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.Observable;
/*     */ import javafx.beans.value.ObservableBooleanValue;
/*     */ import javafx.beans.value.ObservableDoubleValue;
/*     */ import javafx.beans.value.ObservableFloatValue;
/*     */ import javafx.beans.value.ObservableLongValue;
/*     */ import javafx.beans.value.ObservableNumberValue;
/*     */ import javafx.beans.value.ObservableObjectValue;
/*     */ import javafx.beans.value.ObservableStringValue;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ import javafx.collections.FXCollections;
/*     */ import javafx.collections.ObservableList;
/*     */ 
/*     */ public class When
/*     */ {
/*     */   private final ObservableBooleanValue condition;
/*     */ 
/*     */   public When(ObservableBooleanValue paramObservableBooleanValue)
/*     */   {
/*  71 */     if (paramObservableBooleanValue == null) {
/*  72 */       throw new NullPointerException("Condition must be specified.");
/*     */     }
/*  74 */     this.condition = paramObservableBooleanValue;
/*     */   }
/*     */ 
/*     */   private static NumberBinding createNumberCondition(ObservableBooleanValue paramObservableBooleanValue, final ObservableNumberValue paramObservableNumberValue1, final ObservableNumberValue paramObservableNumberValue2)
/*     */   {
/* 119 */     if (((paramObservableNumberValue1 instanceof ObservableDoubleValue)) || ((paramObservableNumberValue2 instanceof ObservableDoubleValue)))
/*     */     {
/* 121 */       return new DoubleBinding()
/*     */       {
/*     */         final InvalidationListener observer;
/*     */ 
/*     */         public void dispose()
/*     */         {
/* 131 */           this.val$condition.removeListener(this.observer);
/* 132 */           paramObservableNumberValue1.removeListener(this.observer);
/* 133 */           paramObservableNumberValue2.removeListener(this.observer);
/*     */         }
/*     */ 
/*     */         protected double computeValue()
/*     */         {
/* 138 */           return this.val$condition.get() ? paramObservableNumberValue1.doubleValue() : paramObservableNumberValue2.doubleValue();
/*     */         }
/*     */ 
/*     */         public ObservableList<ObservableValue<?>> getDependencies()
/*     */         {
/* 145 */           return FXCollections.unmodifiableObservableList(FXCollections.observableArrayList(new ObservableValue[] { this.val$condition, paramObservableNumberValue1, paramObservableNumberValue2 }));
/*     */         }
/*     */ 
/*     */       };
/*     */     }
/*     */ 
/* 152 */     if (((paramObservableNumberValue1 instanceof ObservableFloatValue)) || ((paramObservableNumberValue2 instanceof ObservableFloatValue)))
/*     */     {
/* 154 */       return new FloatBinding()
/*     */       {
/*     */         final InvalidationListener observer;
/*     */ 
/*     */         public void dispose()
/*     */         {
/* 164 */           this.val$condition.removeListener(this.observer);
/* 165 */           paramObservableNumberValue1.removeListener(this.observer);
/* 166 */           paramObservableNumberValue2.removeListener(this.observer);
/*     */         }
/*     */ 
/*     */         protected float computeValue()
/*     */         {
/* 171 */           return this.val$condition.get() ? paramObservableNumberValue1.floatValue() : paramObservableNumberValue2.floatValue();
/*     */         }
/*     */ 
/*     */         public ObservableList<ObservableValue<?>> getDependencies()
/*     */         {
/* 178 */           return FXCollections.unmodifiableObservableList(FXCollections.observableArrayList(new ObservableValue[] { this.val$condition, paramObservableNumberValue1, paramObservableNumberValue2 }));
/*     */         }
/*     */ 
/*     */       };
/*     */     }
/*     */ 
/* 185 */     if (((paramObservableNumberValue1 instanceof ObservableLongValue)) || ((paramObservableNumberValue2 instanceof ObservableLongValue)))
/*     */     {
/* 187 */       return new LongBinding()
/*     */       {
/*     */         final InvalidationListener observer;
/*     */ 
/*     */         public void dispose()
/*     */         {
/* 197 */           this.val$condition.removeListener(this.observer);
/* 198 */           paramObservableNumberValue1.removeListener(this.observer);
/* 199 */           paramObservableNumberValue2.removeListener(this.observer);
/*     */         }
/*     */ 
/*     */         protected long computeValue()
/*     */         {
/* 204 */           return this.val$condition.get() ? paramObservableNumberValue1.longValue() : paramObservableNumberValue2.longValue();
/*     */         }
/*     */ 
/*     */         public ObservableList<ObservableValue<?>> getDependencies()
/*     */         {
/* 211 */           return FXCollections.unmodifiableObservableList(FXCollections.observableArrayList(new ObservableValue[] { this.val$condition, paramObservableNumberValue1, paramObservableNumberValue2 }));
/*     */         }
/*     */ 
/*     */       };
/*     */     }
/*     */ 
/* 219 */     return new IntegerBinding()
/*     */     {
/*     */       final InvalidationListener observer;
/*     */ 
/*     */       public void dispose()
/*     */       {
/* 229 */         this.val$condition.removeListener(this.observer);
/* 230 */         paramObservableNumberValue1.removeListener(this.observer);
/* 231 */         paramObservableNumberValue2.removeListener(this.observer);
/*     */       }
/*     */ 
/*     */       protected int computeValue()
/*     */       {
/* 236 */         return this.val$condition.get() ? paramObservableNumberValue1.intValue() : paramObservableNumberValue2.intValue();
/*     */       }
/*     */ 
/*     */       public ObservableList<ObservableValue<?>> getDependencies()
/*     */       {
/* 243 */         return FXCollections.unmodifiableObservableList(FXCollections.observableArrayList(new ObservableValue[] { this.val$condition, paramObservableNumberValue1, paramObservableNumberValue2 }));
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public NumberConditionBuilder then(ObservableNumberValue paramObservableNumberValue)
/*     */   {
/* 340 */     if (paramObservableNumberValue == null) {
/* 341 */       throw new NullPointerException("Value needs to be specified");
/*     */     }
/* 343 */     return new NumberConditionBuilder(paramObservableNumberValue, null);
/*     */   }
/*     */ 
/*     */   public NumberConditionBuilder then(double paramDouble)
/*     */   {
/* 355 */     return new NumberConditionBuilder(DoubleConstant.valueOf(paramDouble), null);
/*     */   }
/*     */ 
/*     */   public NumberConditionBuilder then(float paramFloat)
/*     */   {
/* 367 */     return new NumberConditionBuilder(FloatConstant.valueOf(paramFloat), null);
/*     */   }
/*     */ 
/*     */   public NumberConditionBuilder then(long paramLong)
/*     */   {
/* 379 */     return new NumberConditionBuilder(LongConstant.valueOf(paramLong), null);
/*     */   }
/*     */ 
/*     */   public NumberConditionBuilder then(int paramInt)
/*     */   {
/* 391 */     return new NumberConditionBuilder(IntegerConstant.valueOf(paramInt), null);
/*     */   }
/*     */ 
/*     */   public BooleanConditionBuilder then(ObservableBooleanValue paramObservableBooleanValue)
/*     */   {
/* 549 */     if (paramObservableBooleanValue == null) {
/* 550 */       throw new NullPointerException("Value needs to be specified");
/*     */     }
/* 552 */     return new BooleanConditionBuilder(paramObservableBooleanValue, null);
/*     */   }
/*     */ 
/*     */   public BooleanConditionBuilder then(boolean paramBoolean)
/*     */   {
/* 564 */     return new BooleanConditionBuilder(paramBoolean, null);
/*     */   }
/*     */ 
/*     */   public StringConditionBuilder then(ObservableStringValue paramObservableStringValue)
/*     */   {
/* 721 */     if (paramObservableStringValue == null) {
/* 722 */       throw new NullPointerException("Value needs to be specified");
/*     */     }
/* 724 */     return new StringConditionBuilder(paramObservableStringValue, null);
/*     */   }
/*     */ 
/*     */   public StringConditionBuilder then(String paramString)
/*     */   {
/* 736 */     return new StringConditionBuilder(paramString, null);
/*     */   }
/*     */ 
/*     */   public <T> ObjectConditionBuilder<T> then(ObservableObjectValue<T> paramObservableObjectValue)
/*     */   {
/* 897 */     if (paramObservableObjectValue == null) {
/* 898 */       throw new NullPointerException("Value needs to be specified");
/*     */     }
/* 900 */     return new ObjectConditionBuilder(paramObservableObjectValue, null);
/*     */   }
/*     */ 
/*     */   public <T> ObjectConditionBuilder<T> then(T paramT)
/*     */   {
/* 912 */     return new ObjectConditionBuilder(paramT, null);
/*     */   }
/*     */ 
/*     */   public class ObjectConditionBuilder<T>
/*     */   {
/*     */     private ObservableObjectValue<T> trueResult;
/*     */     private T trueResultValue;
/*     */ 
/*     */     private ObjectConditionBuilder()
/*     */     {
/*     */       Object localObject;
/* 844 */       this.trueResult = localObject;
/*     */     }
/*     */ 
/*     */     private ObjectConditionBuilder()
/*     */     {
/*     */       Object localObject;
/* 848 */       this.trueResultValue = localObject;
/*     */     }
/*     */ 
/*     */     public ObjectBinding<T> otherwise(ObservableObjectValue<T> paramObservableObjectValue)
/*     */     {
/* 862 */       if (paramObservableObjectValue == null) {
/* 863 */         throw new NullPointerException("Value needs to be specified");
/*     */       }
/* 865 */       if (this.trueResult != null) {
/* 866 */         return new When.ObjectCondition(When.this, this.trueResult, paramObservableObjectValue, null);
/*     */       }
/* 868 */       return new When.ObjectCondition(When.this, this.trueResultValue, paramObservableObjectValue, null);
/*     */     }
/*     */ 
/*     */     public ObjectBinding<T> otherwise(T paramT)
/*     */     {
/* 880 */       if (this.trueResult != null) {
/* 881 */         return new When.ObjectCondition(When.this, this.trueResult, paramT, null);
/*     */       }
/* 883 */       return new When.ObjectCondition(When.this, this.trueResultValue, paramT, null);
/*     */     }
/*     */   }
/*     */ 
/*     */   private class ObjectCondition<T> extends ObjectBinding<T>
/*     */   {
/*     */     private final ObservableObjectValue<T> trueResult;
/*     */     private final T trueResultValue;
/*     */     private final ObservableObjectValue<T> falseResult;
/*     */     private final T falseResultValue;
/*     */     private final InvalidationListener observer;
/*     */ 
/*     */     private ObjectCondition(ObservableObjectValue<T> arg2)
/*     */     {
/*     */       ObservableValue localObservableValue1;
/* 754 */       this.trueResult = localObservableValue1;
/* 755 */       this.trueResultValue = null;
/*     */       ObservableValue localObservableValue2;
/* 756 */       this.falseResult = localObservableValue2;
/* 757 */       this.falseResultValue = null;
/* 758 */       this.observer = new When.WhenListener(this, When.this.condition, localObservableValue1, localObservableValue2, null);
/* 759 */       When.this.condition.addListener(this.observer);
/* 760 */       localObservableValue1.addListener(this.observer);
/* 761 */       localObservableValue2.addListener(this.observer);
/*     */     }
/*     */ 
/*     */     private ObjectCondition(ObservableObjectValue<T> arg2)
/*     */     {
/* 766 */       this.trueResult = null;
/*     */       Object localObject;
/* 767 */       this.trueResultValue = localObject;
/*     */       ObservableValue localObservableValue;
/* 768 */       this.falseResult = localObservableValue;
/* 769 */       this.falseResultValue = null;
/* 770 */       this.observer = new When.WhenListener(this, When.this.condition, null, localObservableValue, null);
/* 771 */       When.this.condition.addListener(this.observer);
/* 772 */       localObservableValue.addListener(this.observer);
/*     */     }
/*     */ 
/*     */     private ObjectCondition(T arg2)
/*     */     {
/*     */       ObservableValue localObservableValue;
/* 777 */       this.trueResult = localObservableValue;
/* 778 */       this.trueResultValue = null;
/* 779 */       this.falseResult = null;
/*     */       Object localObject;
/* 780 */       this.falseResultValue = localObject;
/* 781 */       this.observer = new When.WhenListener(this, When.this.condition, localObservableValue, null, null);
/* 782 */       When.this.condition.addListener(this.observer);
/* 783 */       localObservableValue.addListener(this.observer);
/*     */     }
/*     */ 
/*     */     private ObjectCondition(T arg2) {
/* 787 */       this.trueResult = null;
/*     */       Object localObject1;
/* 788 */       this.trueResultValue = localObject1;
/* 789 */       this.falseResult = null;
/*     */       Object localObject2;
/* 790 */       this.falseResultValue = localObject2;
/* 791 */       this.observer = null;
/* 792 */       super.bind(new Observable[] { When.this.condition });
/*     */     }
/*     */ 
/*     */     protected T computeValue()
/*     */     {
/* 797 */       return this.falseResult != null ? this.falseResult.get() : When.this.condition.get() ? this.trueResultValue : this.trueResult != null ? this.trueResult.get() : this.falseResultValue;
/*     */     }
/*     */ 
/*     */     public void dispose()
/*     */     {
/* 804 */       if (this.observer == null) {
/* 805 */         super.unbind(new Observable[] { When.this.condition });
/*     */       } else {
/* 807 */         When.this.condition.removeListener(this.observer);
/* 808 */         if (this.trueResult != null) {
/* 809 */           this.trueResult.removeListener(this.observer);
/*     */         }
/* 811 */         if (this.falseResult != null)
/* 812 */           this.falseResult.removeListener(this.observer);
/*     */       }
/*     */     }
/*     */ 
/*     */     public ObservableList<ObservableValue<?>> getDependencies()
/*     */     {
/* 821 */       assert (When.this.condition != null);
/* 822 */       ObservableList localObservableList = FXCollections.observableArrayList(new ObservableValue[] { When.this.condition });
/*     */ 
/* 824 */       if (this.trueResult != null) {
/* 825 */         localObservableList.add(this.trueResult);
/*     */       }
/* 827 */       if (this.falseResult != null) {
/* 828 */         localObservableList.add(this.falseResult);
/*     */       }
/* 830 */       return FXCollections.unmodifiableObservableList(localObservableList);
/*     */     }
/*     */   }
/*     */ 
/*     */   public class StringConditionBuilder
/*     */   {
/*     */     private ObservableStringValue trueResult;
/*     */     private String trueResultValue;
/*     */ 
/*     */     private StringConditionBuilder(ObservableStringValue arg2)
/*     */     {
/*     */       Object localObject;
/* 672 */       this.trueResult = localObject;
/*     */     }
/*     */ 
/*     */     private StringConditionBuilder(String arg2)
/*     */     {
/*     */       Object localObject;
/* 676 */       this.trueResultValue = localObject;
/*     */     }
/*     */ 
/*     */     public StringBinding otherwise(ObservableStringValue paramObservableStringValue)
/*     */     {
/* 690 */       if (this.trueResult != null) {
/* 691 */         return new When.StringCondition(When.this, this.trueResult, paramObservableStringValue, null);
/*     */       }
/* 693 */       return new When.StringCondition(When.this, this.trueResultValue, paramObservableStringValue, null);
/*     */     }
/*     */ 
/*     */     public StringBinding otherwise(String paramString)
/*     */     {
/* 705 */       if (this.trueResult != null) {
/* 706 */         return new When.StringCondition(When.this, this.trueResult, paramString, null);
/*     */       }
/* 708 */       return new When.StringCondition(When.this, this.trueResultValue, paramString, null);
/*     */     }
/*     */   }
/*     */ 
/*     */   private class StringCondition extends StringBinding
/*     */   {
/*     */     private final ObservableStringValue trueResult;
/*     */     private final String trueResultValue;
/*     */     private final ObservableStringValue falseResult;
/*     */     private final String falseResultValue;
/*     */     private final InvalidationListener observer;
/*     */ 
/*     */     private StringCondition(ObservableStringValue paramObservableStringValue1, ObservableStringValue arg3)
/*     */     {
/* 582 */       this.trueResult = paramObservableStringValue1;
/* 583 */       this.trueResultValue = "";
/*     */       ObservableValue localObservableValue;
/* 584 */       this.falseResult = localObservableValue;
/* 585 */       this.falseResultValue = "";
/* 586 */       this.observer = new When.WhenListener(this, When.this.condition, paramObservableStringValue1, localObservableValue, null);
/* 587 */       When.this.condition.addListener(this.observer);
/* 588 */       paramObservableStringValue1.addListener(this.observer);
/* 589 */       localObservableValue.addListener(this.observer);
/*     */     }
/*     */ 
/*     */     private StringCondition(String paramObservableStringValue, ObservableStringValue arg3)
/*     */     {
/* 594 */       this.trueResult = null;
/* 595 */       this.trueResultValue = paramObservableStringValue;
/*     */       ObservableValue localObservableValue;
/* 596 */       this.falseResult = localObservableValue;
/* 597 */       this.falseResultValue = "";
/* 598 */       this.observer = new When.WhenListener(this, When.this.condition, null, localObservableValue, null);
/* 599 */       When.this.condition.addListener(this.observer);
/* 600 */       localObservableValue.addListener(this.observer);
/*     */     }
/*     */ 
/*     */     private StringCondition(ObservableStringValue paramString, String arg3)
/*     */     {
/* 605 */       this.trueResult = paramString;
/* 606 */       this.trueResultValue = "";
/* 607 */       this.falseResult = null;
/*     */       Object localObject;
/* 608 */       this.falseResultValue = localObject;
/* 609 */       this.observer = new When.WhenListener(this, When.this.condition, paramString, null, null);
/* 610 */       When.this.condition.addListener(this.observer);
/* 611 */       paramString.addListener(this.observer);
/*     */     }
/*     */ 
/*     */     private StringCondition(String paramString1, String arg3) {
/* 615 */       this.trueResult = null;
/* 616 */       this.trueResultValue = paramString1;
/* 617 */       this.falseResult = null;
/*     */       Object localObject;
/* 618 */       this.falseResultValue = localObject;
/* 619 */       this.observer = null;
/* 620 */       super.bind(new Observable[] { When.this.condition });
/*     */     }
/*     */ 
/*     */     protected String computeValue()
/*     */     {
/* 625 */       return this.falseResult != null ? (String)this.falseResult.get() : When.this.condition.get() ? this.trueResultValue : this.trueResult != null ? (String)this.trueResult.get() : this.falseResultValue;
/*     */     }
/*     */ 
/*     */     public void dispose()
/*     */     {
/* 632 */       if (this.observer == null) {
/* 633 */         super.unbind(new Observable[] { When.this.condition });
/*     */       } else {
/* 635 */         When.this.condition.removeListener(this.observer);
/* 636 */         if (this.trueResult != null) {
/* 637 */           this.trueResult.removeListener(this.observer);
/*     */         }
/* 639 */         if (this.falseResult != null)
/* 640 */           this.falseResult.removeListener(this.observer);
/*     */       }
/*     */     }
/*     */ 
/*     */     public ObservableList<ObservableValue<?>> getDependencies()
/*     */     {
/* 649 */       assert (When.this.condition != null);
/* 650 */       ObservableList localObservableList = FXCollections.observableArrayList(new ObservableValue[] { When.this.condition });
/*     */ 
/* 652 */       if (this.trueResult != null) {
/* 653 */         localObservableList.add(this.trueResult);
/*     */       }
/* 655 */       if (this.falseResult != null) {
/* 656 */         localObservableList.add(this.falseResult);
/*     */       }
/* 658 */       return FXCollections.unmodifiableObservableList(localObservableList);
/*     */     }
/*     */   }
/*     */ 
/*     */   public class BooleanConditionBuilder
/*     */   {
/*     */     private ObservableBooleanValue trueResult;
/*     */     private boolean trueResultValue;
/*     */ 
/*     */     private BooleanConditionBuilder(ObservableBooleanValue arg2)
/*     */     {
/*     */       Object localObject;
/* 497 */       this.trueResult = localObject;
/*     */     }
/*     */ 
/*     */     private BooleanConditionBuilder(boolean arg2)
/*     */     {
/*     */       boolean bool;
/* 501 */       this.trueResultValue = bool;
/*     */     }
/*     */ 
/*     */     public BooleanBinding otherwise(ObservableBooleanValue paramObservableBooleanValue)
/*     */     {
/* 515 */       if (paramObservableBooleanValue == null) {
/* 516 */         throw new NullPointerException("Value needs to be specified");
/*     */       }
/* 518 */       if (this.trueResult != null) {
/* 519 */         return new When.BooleanCondition(When.this, this.trueResult, paramObservableBooleanValue, null);
/*     */       }
/* 521 */       return new When.BooleanCondition(When.this, this.trueResultValue, paramObservableBooleanValue, null);
/*     */     }
/*     */ 
/*     */     public BooleanBinding otherwise(boolean paramBoolean)
/*     */     {
/* 533 */       if (this.trueResult != null) {
/* 534 */         return new When.BooleanCondition(When.this, this.trueResult, paramBoolean, null);
/*     */       }
/* 536 */       return new When.BooleanCondition(When.this, this.trueResultValue, paramBoolean, null);
/*     */     }
/*     */   }
/*     */ 
/*     */   private class BooleanCondition extends BooleanBinding
/*     */   {
/*     */     private final ObservableBooleanValue trueResult;
/*     */     private final boolean trueResultValue;
/*     */     private final ObservableBooleanValue falseResult;
/*     */     private final boolean falseResultValue;
/*     */     private final InvalidationListener observer;
/*     */ 
/*     */     private BooleanCondition(ObservableBooleanValue paramObservableBooleanValue1, ObservableBooleanValue arg3)
/*     */     {
/* 408 */       this.trueResult = paramObservableBooleanValue1;
/* 409 */       this.trueResultValue = false;
/*     */       ObservableValue localObservableValue;
/* 410 */       this.falseResult = localObservableValue;
/* 411 */       this.falseResultValue = false;
/* 412 */       this.observer = new When.WhenListener(this, When.this.condition, paramObservableBooleanValue1, localObservableValue, null);
/* 413 */       When.this.condition.addListener(this.observer);
/* 414 */       paramObservableBooleanValue1.addListener(this.observer);
/* 415 */       localObservableValue.addListener(this.observer);
/*     */     }
/*     */ 
/*     */     private BooleanCondition(boolean paramObservableBooleanValue, ObservableBooleanValue arg3)
/*     */     {
/* 420 */       this.trueResult = null;
/* 421 */       this.trueResultValue = paramObservableBooleanValue;
/*     */       ObservableValue localObservableValue;
/* 422 */       this.falseResult = localObservableValue;
/* 423 */       this.falseResultValue = false;
/* 424 */       this.observer = new When.WhenListener(this, When.this.condition, null, localObservableValue, null);
/* 425 */       When.this.condition.addListener(this.observer);
/* 426 */       localObservableValue.addListener(this.observer);
/*     */     }
/*     */ 
/*     */     private BooleanCondition(ObservableBooleanValue paramBoolean, boolean arg3)
/*     */     {
/* 431 */       this.trueResult = paramBoolean;
/* 432 */       this.trueResultValue = false;
/* 433 */       this.falseResult = null;
/*     */       boolean bool;
/* 434 */       this.falseResultValue = bool;
/* 435 */       this.observer = new When.WhenListener(this, When.this.condition, paramBoolean, null, null);
/* 436 */       When.this.condition.addListener(this.observer);
/* 437 */       paramBoolean.addListener(this.observer);
/*     */     }
/*     */ 
/*     */     private BooleanCondition(boolean paramBoolean1, boolean arg3) {
/* 441 */       this.trueResult = null;
/* 442 */       this.trueResultValue = paramBoolean1;
/* 443 */       this.falseResult = null;
/*     */       boolean bool;
/* 444 */       this.falseResultValue = bool;
/* 445 */       this.observer = null;
/* 446 */       super.bind(new Observable[] { When.this.condition });
/*     */     }
/*     */ 
/*     */     protected boolean computeValue()
/*     */     {
/* 451 */       return this.falseResult != null ? this.falseResult.get() : When.this.condition.get() ? this.trueResultValue : this.trueResult != null ? this.trueResult.get() : this.falseResultValue;
/*     */     }
/*     */ 
/*     */     public void dispose()
/*     */     {
/* 458 */       if (this.observer == null) {
/* 459 */         super.unbind(new Observable[] { When.this.condition });
/*     */       } else {
/* 461 */         When.this.condition.removeListener(this.observer);
/* 462 */         if (this.trueResult != null) {
/* 463 */           this.trueResult.removeListener(this.observer);
/*     */         }
/* 465 */         if (this.falseResult != null)
/* 466 */           this.falseResult.removeListener(this.observer);
/*     */       }
/*     */     }
/*     */ 
/*     */     public ObservableList<ObservableValue<?>> getDependencies()
/*     */     {
/* 474 */       assert (When.this.condition != null);
/* 475 */       ObservableList localObservableList = FXCollections.observableArrayList(new ObservableValue[] { When.this.condition });
/*     */ 
/* 477 */       if (this.trueResult != null) {
/* 478 */         localObservableList.add(this.trueResult);
/*     */       }
/* 480 */       if (this.falseResult != null) {
/* 481 */         localObservableList.add(this.falseResult);
/*     */       }
/* 483 */       return FXCollections.unmodifiableObservableList(localObservableList);
/*     */     }
/*     */   }
/*     */ 
/*     */   public class NumberConditionBuilder
/*     */   {
/*     */     private ObservableNumberValue thenValue;
/*     */ 
/*     */     private NumberConditionBuilder(ObservableNumberValue arg2)
/*     */     {
/*     */       Object localObject;
/* 261 */       this.thenValue = localObject;
/*     */     }
/*     */ 
/*     */     public NumberBinding otherwise(ObservableNumberValue paramObservableNumberValue)
/*     */     {
/* 274 */       if (paramObservableNumberValue == null) {
/* 275 */         throw new NullPointerException("Value needs to be specified");
/*     */       }
/* 277 */       return When.createNumberCondition(When.this.condition, this.thenValue, paramObservableNumberValue);
/*     */     }
/*     */ 
/*     */     public DoubleBinding otherwise(double paramDouble)
/*     */     {
/* 290 */       return (DoubleBinding)otherwise(DoubleConstant.valueOf(paramDouble));
/*     */     }
/*     */ 
/*     */     public NumberBinding otherwise(float paramFloat)
/*     */     {
/* 303 */       return otherwise(FloatConstant.valueOf(paramFloat));
/*     */     }
/*     */ 
/*     */     public NumberBinding otherwise(long paramLong)
/*     */     {
/* 315 */       return otherwise(LongConstant.valueOf(paramLong));
/*     */     }
/*     */ 
/*     */     public NumberBinding otherwise(int paramInt)
/*     */     {
/* 327 */       return otherwise(IntegerConstant.valueOf(paramInt));
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class WhenListener
/*     */     implements InvalidationListener
/*     */   {
/*     */     private final ObservableBooleanValue condition;
/*     */     private final ObservableValue<?> thenValue;
/*     */     private final ObservableValue<?> otherwiseValue;
/*     */     private final WeakReference<Binding<?>> ref;
/*     */ 
/*     */     private WhenListener(Binding<?> paramBinding, ObservableBooleanValue paramObservableBooleanValue, ObservableValue<?> paramObservableValue1, ObservableValue<?> paramObservableValue2)
/*     */     {
/*  85 */       this.ref = new WeakReference(paramBinding);
/*  86 */       this.condition = paramObservableBooleanValue;
/*  87 */       this.thenValue = paramObservableValue1;
/*  88 */       this.otherwiseValue = paramObservableValue2;
/*     */     }
/*     */ 
/*     */     public void invalidated(Observable paramObservable)
/*     */     {
/*  93 */       Binding localBinding = (Binding)this.ref.get();
/*  94 */       if (localBinding == null) {
/*  95 */         this.condition.removeListener(this);
/*  96 */         if (this.thenValue != null) {
/*  97 */           this.thenValue.removeListener(this);
/*     */         }
/*  99 */         if (this.otherwiseValue != null) {
/* 100 */           this.otherwiseValue.removeListener(this);
/*     */         }
/*     */ 
/*     */       }
/* 106 */       else if ((this.condition.equals(paramObservable)) || ((localBinding.isValid()) && (this.condition.get() == paramObservable.equals(this.thenValue))))
/*     */       {
/* 108 */         localBinding.invalidate();
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.beans.binding.When
 * JD-Core Version:    0.6.2
 */