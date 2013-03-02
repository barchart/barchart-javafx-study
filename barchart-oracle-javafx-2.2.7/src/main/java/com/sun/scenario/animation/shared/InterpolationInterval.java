/*     */ package com.sun.scenario.animation.shared;
/*     */ 
/*     */ import com.sun.scenario.animation.NumberTangentInterpolator;
/*     */ import javafx.animation.Interpolator;
/*     */ import javafx.animation.KeyValue;
/*     */ import javafx.beans.value.WritableBooleanValue;
/*     */ import javafx.beans.value.WritableDoubleValue;
/*     */ import javafx.beans.value.WritableFloatValue;
/*     */ import javafx.beans.value.WritableIntegerValue;
/*     */ import javafx.beans.value.WritableLongValue;
/*     */ import javafx.beans.value.WritableValue;
/*     */ 
/*     */ public abstract class InterpolationInterval
/*     */ {
/*     */   protected final double millis;
/*     */   protected final Interpolator rightInterpolator;
/*     */ 
/*     */   protected InterpolationInterval(double paramDouble, Interpolator paramInterpolator)
/*     */   {
/*  67 */     this.millis = paramDouble;
/*  68 */     this.rightInterpolator = paramInterpolator;
/*     */   }
/*     */ 
/*     */   public abstract void interpolate(double paramDouble);
/*     */ 
/*     */   public abstract void recalculateStartValue();
/*     */ 
/*     */   public static InterpolationInterval create(KeyValue paramKeyValue1, double paramDouble1, KeyValue paramKeyValue2, double paramDouble2)
/*     */   {
/*  77 */     switch (1.$SwitchMap$javafx$animation$KeyValue$Type[paramKeyValue1.getType().ordinal()]) {
/*     */     case 1:
/*  79 */       return new BooleanInterpolationInterval(paramKeyValue1, paramDouble1, paramKeyValue2.getEndValue(), null);
/*     */     case 2:
/*  82 */       return ((paramKeyValue2.getInterpolator() instanceof NumberTangentInterpolator)) || ((paramKeyValue1.getInterpolator() instanceof NumberTangentInterpolator)) ? new TangentDoubleInterpolationInterval(paramKeyValue1, paramDouble1, paramKeyValue2, paramDouble2, null) : new DoubleInterpolationInterval(paramKeyValue1, paramDouble1, paramKeyValue2.getEndValue(), null);
/*     */     case 3:
/*  88 */       return ((paramKeyValue2.getInterpolator() instanceof NumberTangentInterpolator)) || ((paramKeyValue1.getInterpolator() instanceof NumberTangentInterpolator)) ? new TangentFloatInterpolationInterval(paramKeyValue1, paramDouble1, paramKeyValue2, paramDouble2, null) : new FloatInterpolationInterval(paramKeyValue1, paramDouble1, paramKeyValue2.getEndValue(), null);
/*     */     case 4:
/*  94 */       return ((paramKeyValue2.getInterpolator() instanceof NumberTangentInterpolator)) || ((paramKeyValue1.getInterpolator() instanceof NumberTangentInterpolator)) ? new TangentIntegerInterpolationInterval(paramKeyValue1, paramDouble1, paramKeyValue2, paramDouble2, null) : new IntegerInterpolationInterval(paramKeyValue1, paramDouble1, paramKeyValue2.getEndValue(), null);
/*     */     case 5:
/* 100 */       return ((paramKeyValue2.getInterpolator() instanceof NumberTangentInterpolator)) || ((paramKeyValue1.getInterpolator() instanceof NumberTangentInterpolator)) ? new TangentLongInterpolationInterval(paramKeyValue1, paramDouble1, paramKeyValue2, paramDouble2, null) : new LongInterpolationInterval(paramKeyValue1, paramDouble1, paramKeyValue2.getEndValue(), null);
/*     */     case 6:
/* 106 */       return new ObjectInterpolationInterval(paramKeyValue1, paramDouble1, paramKeyValue2.getEndValue(), null);
/*     */     }
/*     */ 
/* 109 */     throw new RuntimeException("Should not reach here");
/*     */   }
/*     */ 
/*     */   public static InterpolationInterval create(KeyValue paramKeyValue, double paramDouble)
/*     */   {
/* 114 */     switch (1.$SwitchMap$javafx$animation$KeyValue$Type[paramKeyValue.getType().ordinal()]) {
/*     */     case 1:
/* 116 */       return new BooleanInterpolationInterval(paramKeyValue, paramDouble, null);
/*     */     case 2:
/* 118 */       return (paramKeyValue.getInterpolator() instanceof NumberTangentInterpolator) ? new TangentDoubleInterpolationInterval(paramKeyValue, paramDouble, null) : new DoubleInterpolationInterval(paramKeyValue, paramDouble, null);
/*     */     case 3:
/* 122 */       return (paramKeyValue.getInterpolator() instanceof NumberTangentInterpolator) ? new TangentFloatInterpolationInterval(paramKeyValue, paramDouble, null) : new FloatInterpolationInterval(paramKeyValue, paramDouble, null);
/*     */     case 4:
/* 126 */       return (paramKeyValue.getInterpolator() instanceof NumberTangentInterpolator) ? new TangentIntegerInterpolationInterval(paramKeyValue, paramDouble, null) : new IntegerInterpolationInterval(paramKeyValue, paramDouble, null);
/*     */     case 5:
/* 131 */       return (paramKeyValue.getInterpolator() instanceof NumberTangentInterpolator) ? new TangentLongInterpolationInterval(paramKeyValue, paramDouble, null) : new LongInterpolationInterval(paramKeyValue, paramDouble, null);
/*     */     case 6:
/* 135 */       return new ObjectInterpolationInterval(paramKeyValue, paramDouble, null);
/*     */     }
/* 137 */     throw new RuntimeException("Should not reach here");
/*     */   }
/*     */ 
/*     */   private static class ObjectInterpolationInterval extends InterpolationInterval
/*     */   {
/*     */     private final WritableValue target;
/*     */     private Object leftValue;
/*     */     private final Object rightValue;
/*     */ 
/*     */     private ObjectInterpolationInterval(KeyValue paramKeyValue, double paramDouble, Object paramObject)
/*     */     {
/* 547 */       super(paramKeyValue.getInterpolator());
/* 548 */       this.target = paramKeyValue.getTarget();
/* 549 */       this.rightValue = paramKeyValue.getEndValue();
/* 550 */       this.leftValue = paramObject;
/*     */     }
/*     */ 
/*     */     private ObjectInterpolationInterval(KeyValue paramKeyValue, double paramDouble) {
/* 554 */       super(paramKeyValue.getInterpolator());
/* 555 */       this.target = paramKeyValue.getTarget();
/* 556 */       this.rightValue = paramKeyValue.getEndValue();
/* 557 */       this.leftValue = this.target.getValue();
/*     */     }
/*     */ 
/*     */     public void interpolate(double paramDouble)
/*     */     {
/* 563 */       Object localObject = this.rightInterpolator.interpolate(this.leftValue, this.rightValue, paramDouble);
/*     */ 
/* 565 */       this.target.setValue(localObject);
/*     */     }
/*     */ 
/*     */     public void recalculateStartValue()
/*     */     {
/* 570 */       this.leftValue = this.target.getValue();
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class TangentLongInterpolationInterval extends InterpolationInterval.TangentInterpolationInterval
/*     */   {
/*     */     private final WritableLongValue target;
/*     */ 
/*     */     private TangentLongInterpolationInterval(KeyValue paramKeyValue1, double paramDouble1, KeyValue paramKeyValue2, double paramDouble2)
/*     */     {
/* 513 */       super(paramDouble1, paramKeyValue2, paramDouble2, null);
/* 514 */       assert ((paramKeyValue1.getTarget() instanceof WritableLongValue));
/* 515 */       this.target = ((WritableLongValue)paramKeyValue1.getTarget());
/*     */     }
/*     */ 
/*     */     private TangentLongInterpolationInterval(KeyValue paramKeyValue, double paramDouble)
/*     */     {
/* 520 */       super(paramDouble, null);
/* 521 */       assert ((paramKeyValue.getTarget() instanceof WritableLongValue));
/* 522 */       this.target = ((WritableLongValue)paramKeyValue.getTarget());
/* 523 */       recalculateStartValue(this.target.get());
/*     */     }
/*     */ 
/*     */     public void interpolate(double paramDouble)
/*     */     {
/* 528 */       this.target.set(Math.round(calculate(paramDouble)));
/*     */     }
/*     */ 
/*     */     public void recalculateStartValue()
/*     */     {
/* 533 */       recalculateStartValue(this.target.get());
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class LongInterpolationInterval extends InterpolationInterval
/*     */   {
/*     */     private final WritableLongValue target;
/*     */     private long leftValue;
/*     */     private final long rightValue;
/*     */ 
/*     */     private LongInterpolationInterval(KeyValue paramKeyValue, double paramDouble, Object paramObject)
/*     */     {
/* 475 */       super(paramKeyValue.getInterpolator());
/* 476 */       assert (((paramKeyValue.getTarget() instanceof WritableLongValue)) && ((paramKeyValue.getEndValue() instanceof Number)) && ((paramObject instanceof Number)));
/*     */ 
/* 479 */       this.target = ((WritableLongValue)paramKeyValue.getTarget());
/* 480 */       this.rightValue = ((Number)paramKeyValue.getEndValue()).longValue();
/* 481 */       this.leftValue = ((Number)paramObject).longValue();
/*     */     }
/*     */ 
/*     */     private LongInterpolationInterval(KeyValue paramKeyValue, double paramDouble) {
/* 485 */       super(paramKeyValue.getInterpolator());
/* 486 */       assert (((paramKeyValue.getTarget() instanceof WritableLongValue)) && ((paramKeyValue.getEndValue() instanceof Number)));
/*     */ 
/* 488 */       this.target = ((WritableLongValue)paramKeyValue.getTarget());
/* 489 */       this.rightValue = ((Number)paramKeyValue.getEndValue()).longValue();
/* 490 */       this.leftValue = this.target.get();
/*     */     }
/*     */ 
/*     */     public void interpolate(double paramDouble)
/*     */     {
/* 495 */       long l = this.rightInterpolator.interpolate(this.leftValue, this.rightValue, paramDouble);
/*     */ 
/* 497 */       this.target.set(l);
/*     */     }
/*     */ 
/*     */     public void recalculateStartValue()
/*     */     {
/* 502 */       this.leftValue = this.target.get();
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class TangentIntegerInterpolationInterval extends InterpolationInterval.TangentInterpolationInterval
/*     */   {
/*     */     private final WritableIntegerValue target;
/*     */ 
/*     */     private TangentIntegerInterpolationInterval(KeyValue paramKeyValue1, double paramDouble1, KeyValue paramKeyValue2, double paramDouble2)
/*     */     {
/* 442 */       super(paramDouble1, paramKeyValue2, paramDouble2, null);
/* 443 */       assert ((paramKeyValue1.getTarget() instanceof WritableIntegerValue));
/* 444 */       this.target = ((WritableIntegerValue)paramKeyValue1.getTarget());
/*     */     }
/*     */ 
/*     */     private TangentIntegerInterpolationInterval(KeyValue paramKeyValue, double paramDouble)
/*     */     {
/* 449 */       super(paramDouble, null);
/* 450 */       assert ((paramKeyValue.getTarget() instanceof WritableIntegerValue));
/* 451 */       this.target = ((WritableIntegerValue)paramKeyValue.getTarget());
/* 452 */       recalculateStartValue(this.target.get());
/*     */     }
/*     */ 
/*     */     public void interpolate(double paramDouble)
/*     */     {
/* 457 */       this.target.set((int)Math.round(calculate(paramDouble)));
/*     */     }
/*     */ 
/*     */     public void recalculateStartValue()
/*     */     {
/* 462 */       recalculateStartValue(this.target.get());
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class IntegerInterpolationInterval extends InterpolationInterval
/*     */   {
/*     */     private final WritableIntegerValue target;
/*     */     private int leftValue;
/*     */     private final int rightValue;
/*     */ 
/*     */     private IntegerInterpolationInterval(KeyValue paramKeyValue, double paramDouble, Object paramObject)
/*     */     {
/* 404 */       super(paramKeyValue.getInterpolator());
/* 405 */       assert (((paramKeyValue.getTarget() instanceof WritableIntegerValue)) && ((paramKeyValue.getEndValue() instanceof Number)) && ((paramObject instanceof Number)));
/*     */ 
/* 408 */       this.target = ((WritableIntegerValue)paramKeyValue.getTarget());
/* 409 */       this.rightValue = ((Number)paramKeyValue.getEndValue()).intValue();
/* 410 */       this.leftValue = ((Number)paramObject).intValue();
/*     */     }
/*     */ 
/*     */     private IntegerInterpolationInterval(KeyValue paramKeyValue, double paramDouble) {
/* 414 */       super(paramKeyValue.getInterpolator());
/* 415 */       assert (((paramKeyValue.getTarget() instanceof WritableIntegerValue)) && ((paramKeyValue.getEndValue() instanceof Number)));
/*     */ 
/* 417 */       this.target = ((WritableIntegerValue)paramKeyValue.getTarget());
/* 418 */       this.rightValue = ((Number)paramKeyValue.getEndValue()).intValue();
/* 419 */       this.leftValue = this.target.get();
/*     */     }
/*     */ 
/*     */     public void interpolate(double paramDouble)
/*     */     {
/* 424 */       int i = this.rightInterpolator.interpolate(this.leftValue, this.rightValue, paramDouble);
/*     */ 
/* 426 */       this.target.set(i);
/*     */     }
/*     */ 
/*     */     public void recalculateStartValue()
/*     */     {
/* 431 */       this.leftValue = this.target.get();
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class TangentFloatInterpolationInterval extends InterpolationInterval.TangentInterpolationInterval
/*     */   {
/*     */     private final WritableFloatValue target;
/*     */ 
/*     */     private TangentFloatInterpolationInterval(KeyValue paramKeyValue1, double paramDouble1, KeyValue paramKeyValue2, double paramDouble2)
/*     */     {
/* 371 */       super(paramDouble1, paramKeyValue2, paramDouble2, null);
/* 372 */       assert ((paramKeyValue1.getTarget() instanceof WritableFloatValue));
/* 373 */       this.target = ((WritableFloatValue)paramKeyValue1.getTarget());
/*     */     }
/*     */ 
/*     */     private TangentFloatInterpolationInterval(KeyValue paramKeyValue, double paramDouble)
/*     */     {
/* 378 */       super(paramDouble, null);
/* 379 */       assert ((paramKeyValue.getTarget() instanceof WritableFloatValue));
/* 380 */       this.target = ((WritableFloatValue)paramKeyValue.getTarget());
/* 381 */       recalculateStartValue(this.target.get());
/*     */     }
/*     */ 
/*     */     public void interpolate(double paramDouble)
/*     */     {
/* 386 */       this.target.set((float)calculate(paramDouble));
/*     */     }
/*     */ 
/*     */     public void recalculateStartValue()
/*     */     {
/* 391 */       recalculateStartValue(this.target.get());
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class FloatInterpolationInterval extends InterpolationInterval
/*     */   {
/*     */     private final WritableFloatValue target;
/*     */     private float leftValue;
/*     */     private final float rightValue;
/*     */ 
/*     */     private FloatInterpolationInterval(KeyValue paramKeyValue, double paramDouble, Object paramObject)
/*     */     {
/* 333 */       super(paramKeyValue.getInterpolator());
/* 334 */       assert (((paramKeyValue.getTarget() instanceof WritableFloatValue)) && ((paramKeyValue.getEndValue() instanceof Number)) && ((paramObject instanceof Number)));
/*     */ 
/* 337 */       this.target = ((WritableFloatValue)paramKeyValue.getTarget());
/* 338 */       this.rightValue = ((Number)paramKeyValue.getEndValue()).floatValue();
/* 339 */       this.leftValue = ((Number)paramObject).floatValue();
/*     */     }
/*     */ 
/*     */     private FloatInterpolationInterval(KeyValue paramKeyValue, double paramDouble) {
/* 343 */       super(paramKeyValue.getInterpolator());
/* 344 */       assert (((paramKeyValue.getTarget() instanceof WritableFloatValue)) && ((paramKeyValue.getEndValue() instanceof Number)));
/*     */ 
/* 346 */       this.target = ((WritableFloatValue)paramKeyValue.getTarget());
/* 347 */       this.rightValue = ((Number)paramKeyValue.getEndValue()).floatValue();
/* 348 */       this.leftValue = this.target.get();
/*     */     }
/*     */ 
/*     */     public void interpolate(double paramDouble)
/*     */     {
/* 353 */       float f = (float)this.rightInterpolator.interpolate(this.leftValue, this.rightValue, paramDouble);
/*     */ 
/* 355 */       this.target.set(f);
/*     */     }
/*     */ 
/*     */     public void recalculateStartValue()
/*     */     {
/* 360 */       this.leftValue = this.target.get();
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class TangentDoubleInterpolationInterval extends InterpolationInterval.TangentInterpolationInterval
/*     */   {
/*     */     private final WritableDoubleValue target;
/*     */ 
/*     */     private TangentDoubleInterpolationInterval(KeyValue paramKeyValue1, double paramDouble1, KeyValue paramKeyValue2, double paramDouble2)
/*     */     {
/* 300 */       super(paramDouble1, paramKeyValue2, paramDouble2, null);
/* 301 */       assert ((paramKeyValue1.getTarget() instanceof WritableDoubleValue));
/* 302 */       this.target = ((WritableDoubleValue)paramKeyValue1.getTarget());
/*     */     }
/*     */ 
/*     */     private TangentDoubleInterpolationInterval(KeyValue paramKeyValue, double paramDouble)
/*     */     {
/* 307 */       super(paramDouble, null);
/* 308 */       assert ((paramKeyValue.getTarget() instanceof WritableDoubleValue));
/* 309 */       this.target = ((WritableDoubleValue)paramKeyValue.getTarget());
/* 310 */       recalculateStartValue(this.target.get());
/*     */     }
/*     */ 
/*     */     public void interpolate(double paramDouble)
/*     */     {
/* 315 */       this.target.set(calculate(paramDouble));
/*     */     }
/*     */ 
/*     */     public void recalculateStartValue()
/*     */     {
/* 320 */       recalculateStartValue(this.target.get());
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class DoubleInterpolationInterval extends InterpolationInterval
/*     */   {
/*     */     private final WritableDoubleValue target;
/*     */     private double leftValue;
/*     */     private final double rightValue;
/*     */ 
/*     */     private DoubleInterpolationInterval(KeyValue paramKeyValue, double paramDouble, Object paramObject)
/*     */     {
/* 262 */       super(paramKeyValue.getInterpolator());
/* 263 */       assert (((paramKeyValue.getTarget() instanceof WritableDoubleValue)) && ((paramKeyValue.getEndValue() instanceof Number)) && ((paramObject instanceof Number)));
/*     */ 
/* 266 */       this.target = ((WritableDoubleValue)paramKeyValue.getTarget());
/* 267 */       this.rightValue = ((Number)paramKeyValue.getEndValue()).doubleValue();
/* 268 */       this.leftValue = ((Number)paramObject).doubleValue();
/*     */     }
/*     */ 
/*     */     private DoubleInterpolationInterval(KeyValue paramKeyValue, double paramDouble) {
/* 272 */       super(paramKeyValue.getInterpolator());
/* 273 */       assert (((paramKeyValue.getTarget() instanceof WritableDoubleValue)) && ((paramKeyValue.getEndValue() instanceof Number)));
/*     */ 
/* 275 */       this.target = ((WritableDoubleValue)paramKeyValue.getTarget());
/* 276 */       this.rightValue = ((Number)paramKeyValue.getEndValue()).doubleValue();
/* 277 */       this.leftValue = this.target.get();
/*     */     }
/*     */ 
/*     */     public void interpolate(double paramDouble)
/*     */     {
/* 282 */       double d = this.rightInterpolator.interpolate(this.leftValue, this.rightValue, paramDouble);
/*     */ 
/* 284 */       this.target.set(d);
/*     */     }
/*     */ 
/*     */     public void recalculateStartValue()
/*     */     {
/* 289 */       this.leftValue = this.target.get();
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class BooleanInterpolationInterval extends InterpolationInterval
/*     */   {
/*     */     private final WritableBooleanValue target;
/*     */     private boolean leftValue;
/*     */     private final boolean rightValue;
/*     */ 
/*     */     private BooleanInterpolationInterval(KeyValue paramKeyValue, double paramDouble, Object paramObject)
/*     */     {
/* 222 */       super(paramKeyValue.getInterpolator());
/* 223 */       assert (((paramKeyValue.getTarget() instanceof WritableBooleanValue)) && ((paramKeyValue.getEndValue() instanceof Boolean)) && ((paramObject instanceof Boolean)));
/*     */ 
/* 226 */       this.target = ((WritableBooleanValue)paramKeyValue.getTarget());
/* 227 */       this.rightValue = ((Boolean)paramKeyValue.getEndValue()).booleanValue();
/* 228 */       this.leftValue = ((Boolean)paramObject).booleanValue();
/*     */     }
/*     */ 
/*     */     private BooleanInterpolationInterval(KeyValue paramKeyValue, double paramDouble) {
/* 232 */       super(paramKeyValue.getInterpolator());
/* 233 */       assert (((paramKeyValue.getTarget() instanceof WritableBooleanValue)) && ((paramKeyValue.getEndValue() instanceof Boolean)));
/*     */ 
/* 235 */       this.target = ((WritableBooleanValue)paramKeyValue.getTarget());
/* 236 */       this.rightValue = ((Boolean)paramKeyValue.getEndValue()).booleanValue();
/* 237 */       this.leftValue = this.target.get();
/*     */     }
/*     */ 
/*     */     public void interpolate(double paramDouble)
/*     */     {
/* 242 */       boolean bool = this.rightInterpolator.interpolate(this.leftValue, this.rightValue, paramDouble);
/*     */ 
/* 244 */       this.target.set(bool);
/*     */     }
/*     */ 
/*     */     public void recalculateStartValue()
/*     */     {
/* 249 */       this.leftValue = this.target.get();
/*     */     }
/*     */   }
/*     */ 
/*     */   private static abstract class TangentInterpolationInterval extends InterpolationInterval
/*     */   {
/*     */     private final double duration;
/*     */     private final double p2;
/*     */     protected final double p3;
/*     */     private final NumberTangentInterpolator leftInterpolator;
/*     */     protected double p0;
/*     */     private double p1;
/*     */ 
/*     */     private TangentInterpolationInterval(KeyValue paramKeyValue1, double paramDouble1, KeyValue paramKeyValue2, double paramDouble2)
/*     */     {
/* 153 */       super(paramKeyValue1.getInterpolator());
/* 154 */       assert (((paramKeyValue1.getEndValue() instanceof Number)) && ((paramKeyValue2.getEndValue() instanceof Number)));
/*     */ 
/* 157 */       this.duration = paramDouble2;
/* 158 */       Interpolator localInterpolator = paramKeyValue2.getInterpolator();
/*     */ 
/* 160 */       this.leftInterpolator = ((localInterpolator instanceof NumberTangentInterpolator) ? (NumberTangentInterpolator)localInterpolator : null);
/*     */ 
/* 162 */       recalculateStartValue(((Number)paramKeyValue2.getEndValue()).doubleValue());
/*     */ 
/* 165 */       Object localObject = (this.rightInterpolator instanceof NumberTangentInterpolator) ? (NumberTangentInterpolator)this.rightInterpolator : null;
/*     */ 
/* 167 */       this.p3 = ((Number)paramKeyValue1.getEndValue()).doubleValue();
/* 168 */       double d = localObject == null ? 0.0D : (localObject.getInValue() - this.p3) * paramDouble2 / localObject.getInMillis() / 3.0D;
/*     */ 
/* 173 */       this.p2 = (this.p3 + d);
/*     */     }
/*     */ 
/*     */     private TangentInterpolationInterval(KeyValue paramKeyValue, double paramDouble)
/*     */     {
/* 178 */       super(paramKeyValue.getInterpolator());
/* 179 */       assert ((paramKeyValue.getEndValue() instanceof Number));
/*     */ 
/* 181 */       this.duration = paramDouble;
/* 182 */       this.leftInterpolator = null;
/*     */ 
/* 184 */       Object localObject = (this.rightInterpolator instanceof NumberTangentInterpolator) ? (NumberTangentInterpolator)this.rightInterpolator : null;
/*     */ 
/* 186 */       this.p3 = ((Number)paramKeyValue.getEndValue()).doubleValue();
/* 187 */       double d = localObject == null ? 0.0D : (localObject.getInValue() - this.p3) * this.duration / localObject.getInMillis() / 3.0D;
/*     */ 
/* 192 */       this.p2 = (this.p3 + d);
/*     */     }
/*     */ 
/*     */     protected double calculate(double paramDouble) {
/* 196 */       double d1 = 1.0D - paramDouble;
/* 197 */       double d2 = paramDouble * paramDouble;
/* 198 */       double d3 = d1 * d1;
/*     */ 
/* 200 */       return d3 * d1 * this.p0 + 3.0D * d3 * paramDouble * this.p1 + 3.0D * d1 * d2 * this.p2 + d2 * paramDouble * this.p3;
/*     */     }
/*     */ 
/*     */     protected final void recalculateStartValue(double paramDouble)
/*     */     {
/* 205 */       this.p0 = paramDouble;
/* 206 */       double d = this.leftInterpolator == null ? 0.0D : (this.leftInterpolator.getOutValue() - this.p0) * this.duration / this.leftInterpolator.getOutMillis() / 3.0D;
/*     */ 
/* 209 */       this.p1 = (this.p0 + d);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.animation.shared.InterpolationInterval
 * JD-Core Version:    0.6.2
 */