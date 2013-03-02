/*     */ package com.sun.scenario.animation.shared;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import javafx.animation.KeyFrame;
/*     */ import javafx.animation.KeyValue;
/*     */ import javafx.util.Duration;
/*     */ 
/*     */ class GeneralClipInterpolator extends ClipInterpolator
/*     */ {
/*     */   private static final double EPSILON = 1.0E-12D;
/*     */   private KeyFrame[] keyFrames;
/*  58 */   private InterpolationInterval[][] interval = new InterpolationInterval[0][];
/*     */ 
/*  61 */   private int[] undefinedStartValues = new int[0];
/*     */ 
/*  63 */   private boolean invalid = true;
/*     */ 
/*     */   GeneralClipInterpolator(KeyFrame[] paramArrayOfKeyFrame) {
/*  66 */     this.keyFrames = paramArrayOfKeyFrame;
/*     */   }
/*     */ 
/*     */   ClipInterpolator setKeyFrames(KeyFrame[] paramArrayOfKeyFrame)
/*     */   {
/*  72 */     if (ClipInterpolator.getRealKeyFrameCount(paramArrayOfKeyFrame) == 2) {
/*  73 */       return ClipInterpolator.create(paramArrayOfKeyFrame);
/*     */     }
/*  75 */     this.keyFrames = paramArrayOfKeyFrame;
/*  76 */     this.invalid = true;
/*  77 */     return this;
/*     */   }
/*     */ 
/*     */   void validate(boolean paramBoolean)
/*     */   {
/*     */     int j;
/*     */     int k;
/*  82 */     if (this.invalid) {
/*  83 */       HashMap localHashMap = new HashMap();
/*  84 */       j = this.keyFrames.length;
/*  85 */       k = 0;
/*  86 */       for (k = 0; k < j; k++) {
/*  87 */         localObject1 = this.keyFrames[k];
/*  88 */         double d1 = ((KeyFrame)localObject1).getTime().toMillis();
/*  89 */         if (Math.abs(d1) >= 1.0E-12D) break;
/*  90 */         for (KeyValue localKeyValue1 : ((KeyFrame)localObject1).getValues()) {
/*  91 */           localHashMap.put(localKeyValue1.getTarget(), localKeyValue1);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*  98 */       Object localObject1 = new HashMap();
/*  99 */       HashSet localHashSet = new HashSet();
/*     */       double d2;
/*     */       Object localObject2;
/* 101 */       for (; k < j; k++) {
/* 102 */         KeyFrame localKeyFrame = this.keyFrames[k];
/* 103 */         d2 = localKeyFrame.getTime().toMillis();
/*     */ 
/* 105 */         for (KeyValue localKeyValue2 : localKeyFrame.getValues()) {
/* 106 */           localObject2 = localKeyValue2.getTarget();
/* 107 */           Object localObject3 = (List)((Map)localObject1).get(localObject2);
/* 108 */           KeyValue localKeyValue3 = (KeyValue)localHashMap.get(localObject2);
/* 109 */           if (localObject3 == null)
/*     */           {
/* 112 */             localObject3 = new ArrayList();
/* 113 */             ((Map)localObject1).put(localObject2, localObject3);
/* 114 */             if (localKeyValue3 == null) {
/* 115 */               ((List)localObject3).add(InterpolationInterval.create(localKeyValue2, d2));
/*     */ 
/* 117 */               localHashSet.add(localObject2);
/*     */             } else {
/* 119 */               ((List)localObject3).add(InterpolationInterval.create(localKeyValue2, d2, localKeyValue3, d2));
/*     */             }
/*     */           }
/*     */           else
/*     */           {
/* 124 */             assert (localKeyValue3 != null);
/* 125 */             ((List)localObject3).add(InterpolationInterval.create(localKeyValue2, d2, localKeyValue3, d2 - ((InterpolationInterval)((List)localObject3).get(((List)localObject3).size() - 1)).millis));
/*     */           }
/*     */ 
/* 129 */           localHashMap.put(localObject2, localKeyValue2);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 134 */       int m = ((Map)localObject1).size();
/* 135 */       if (this.interval.length != m) {
/* 136 */         this.interval = new InterpolationInterval[m][];
/*     */       }
/* 138 */       int n = localHashSet.size();
/* 139 */       if (this.undefinedStartValues.length != n) {
/* 140 */         this.undefinedStartValues = new int[n];
/*     */       }
/* 142 */       int i1 = 0;
/* 143 */       ??? = ((Map)localObject1).entrySet().iterator();
/*     */ 
/* 145 */       for (int i2 = 0; i2 < m; i2++) {
/* 146 */         localObject2 = (Map.Entry)???.next();
/*     */ 
/* 148 */         this.interval[i2] = new InterpolationInterval[((List)((Map.Entry)localObject2).getValue()).size()];
/* 149 */         ((List)((Map.Entry)localObject2).getValue()).toArray(this.interval[i2]);
/* 150 */         if (localHashSet.contains(((Map.Entry)localObject2).getKey())) {
/* 151 */           this.undefinedStartValues[(i1++)] = i2;
/*     */         }
/*     */       }
/* 154 */       this.invalid = false;
/* 155 */     } else if (paramBoolean) {
/* 156 */       int i = this.undefinedStartValues.length;
/* 157 */       for (j = 0; j < i; j++) {
/* 158 */         k = this.undefinedStartValues[j];
/* 159 */         this.interval[k][0].recalculateStartValue();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   void interpolate(double paramDouble)
/*     */   {
/* 166 */     int i = this.interval.length;
/*     */ 
/* 168 */     label132: for (int j = 0; j < i; j++) {
/* 169 */       InterpolationInterval[] arrayOfInterpolationInterval = this.interval[j];
/* 170 */       int k = arrayOfInterpolationInterval.length;
/*     */ 
/* 172 */       double d1 = 0.0D;
/*     */ 
/* 174 */       for (int m = 0; m < k - 1; m++) {
/* 175 */         InterpolationInterval localInterpolationInterval2 = arrayOfInterpolationInterval[m];
/* 176 */         double d3 = localInterpolationInterval2.millis;
/* 177 */         if (paramDouble <= d3)
/*     */         {
/* 179 */           double d4 = (paramDouble - d1) / (d3 - d1);
/*     */ 
/* 181 */           localInterpolationInterval2.interpolate(d4);
/* 182 */           break label132;
/*     */         }
/* 184 */         d1 = d3;
/*     */       }
/*     */ 
/* 187 */       InterpolationInterval localInterpolationInterval1 = arrayOfInterpolationInterval[(k - 1)];
/*     */ 
/* 190 */       double d2 = Math.min(1.0D, (paramDouble - d1) / (localInterpolationInterval1.millis - d1));
/*     */ 
/* 192 */       localInterpolationInterval1.interpolate(d2);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.animation.shared.GeneralClipInterpolator
 * JD-Core Version:    0.6.2
 */