/*     */ package com.sun.scenario.animation.shared;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javafx.animation.KeyFrame;
/*     */ import javafx.animation.KeyValue;
/*     */ import javafx.beans.value.WritableValue;
/*     */ import javafx.util.Duration;
/*     */ 
/*     */ class SimpleClipInterpolator extends ClipInterpolator
/*     */ {
/*  45 */   private static final KeyFrame ZERO_FRAME = new KeyFrame(Duration.ZERO, new KeyValue[0]);
/*     */   private KeyFrame startKeyFrame;
/*     */   private KeyFrame endKeyFrame;
/*     */   private InterpolationInterval[] interval;
/*     */   private int undefinedStartValueCount;
/*     */   private double millis;
/*  55 */   private boolean invalid = true;
/*     */ 
/*     */   SimpleClipInterpolator(KeyFrame paramKeyFrame1, KeyFrame paramKeyFrame2) {
/*  58 */     this.startKeyFrame = paramKeyFrame1;
/*  59 */     this.endKeyFrame = paramKeyFrame2;
/*     */   }
/*     */ 
/*     */   SimpleClipInterpolator(KeyFrame paramKeyFrame) {
/*  63 */     this.startKeyFrame = ZERO_FRAME;
/*  64 */     this.endKeyFrame = paramKeyFrame;
/*     */   }
/*     */ 
/*     */   ClipInterpolator setKeyFrames(KeyFrame[] paramArrayOfKeyFrame)
/*     */   {
/*  70 */     if (ClipInterpolator.getRealKeyFrameCount(paramArrayOfKeyFrame) != 2) {
/*  71 */       return ClipInterpolator.create(paramArrayOfKeyFrame);
/*     */     }
/*  73 */     if (paramArrayOfKeyFrame.length == 1) {
/*  74 */       this.startKeyFrame = ZERO_FRAME;
/*  75 */       this.endKeyFrame = paramArrayOfKeyFrame[0];
/*     */     } else {
/*  77 */       this.startKeyFrame = paramArrayOfKeyFrame[0];
/*  78 */       this.endKeyFrame = paramArrayOfKeyFrame[1];
/*     */     }
/*  80 */     this.invalid = true;
/*  81 */     return this;
/*     */   }
/*     */ 
/*     */   void validate(boolean paramBoolean)
/*     */   {
/*     */     int j;
/*  86 */     if (this.invalid) {
/*  87 */       this.millis = this.endKeyFrame.getTime().toMillis();
/*     */ 
/*  89 */       HashMap localHashMap = new HashMap();
/*     */ 
/*  91 */       for (KeyValue localKeyValue1 : this.endKeyFrame.getValues()) {
/*  92 */         localHashMap.put(localKeyValue1.getTarget(), localKeyValue1);
/*     */       }
/*     */ 
/*  95 */       j = localHashMap.size();
/*  96 */       this.interval = new InterpolationInterval[j];
/*     */ 
/* 101 */       int k = 0;
/* 102 */       for (Iterator localIterator2 = this.startKeyFrame.getValues().iterator(); localIterator2.hasNext(); ) { localKeyValue2 = (KeyValue)localIterator2.next();
/* 103 */         WritableValue localWritableValue = localKeyValue2.getTarget();
/* 104 */         KeyValue localKeyValue3 = (KeyValue)localHashMap.get(localWritableValue);
/* 105 */         if (localKeyValue3 != null) {
/* 106 */           this.interval[(k++)] = InterpolationInterval.create(localKeyValue3, this.millis, localKeyValue2, this.millis);
/*     */ 
/* 108 */           localHashMap.remove(localWritableValue);
/*     */         }
/*     */       }
/* 114 */       KeyValue localKeyValue2;
/* 113 */       this.undefinedStartValueCount = localHashMap.values().size();
/* 114 */       for (localIterator2 = localHashMap.values().iterator(); localIterator2.hasNext(); ) { localKeyValue2 = (KeyValue)localIterator2.next();
/* 115 */         this.interval[(k++)] = InterpolationInterval.create(localKeyValue2, this.millis);
/*     */       }
/*     */ 
/* 119 */       this.invalid = false;
/* 120 */     } else if (paramBoolean)
/*     */     {
/* 122 */       int i = this.interval.length;
/* 123 */       for (j = i - this.undefinedStartValueCount; j < i; j++)
/* 124 */         this.interval[j].recalculateStartValue();
/*     */     }
/*     */   }
/*     */ 
/*     */   void interpolate(double paramDouble)
/*     */   {
/* 131 */     double d = paramDouble / this.millis;
/* 132 */     int i = this.interval.length;
/* 133 */     for (int j = 0; j < i; j++)
/* 134 */       this.interval[j].interpolate(d);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.animation.shared.SimpleClipInterpolator
 * JD-Core Version:    0.6.2
 */