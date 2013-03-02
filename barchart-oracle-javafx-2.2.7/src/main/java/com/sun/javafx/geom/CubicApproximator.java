/*     */ package com.sun.javafx.geom;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ 
/*     */ public class CubicApproximator
/*     */ {
/*     */   private float accuracy;
/*     */   private float maxCubicSize;
/*     */ 
/*     */   public CubicApproximator(float paramFloat1, float paramFloat2)
/*     */   {
/*  18 */     this.accuracy = paramFloat1;
/*  19 */     this.maxCubicSize = paramFloat2;
/*     */   }
/*     */ 
/*     */   public void setAccuracy(float paramFloat) {
/*  23 */     this.accuracy = paramFloat;
/*     */   }
/*     */ 
/*     */   public float getAccuracy() {
/*  27 */     return this.accuracy;
/*     */   }
/*     */ 
/*     */   public void setMaxCubicSize(float paramFloat) {
/*  31 */     this.maxCubicSize = paramFloat;
/*     */   }
/*     */ 
/*     */   public float getMaxCubicSize() {
/*  35 */     return this.maxCubicSize;
/*     */   }
/*     */ 
/*     */   public float approximate(List<QuadCurve2D> paramList, List<CubicCurve2D> paramList1, CubicCurve2D paramCubicCurve2D)
/*     */   {
/*  40 */     float f = getApproxError(paramCubicCurve2D);
/*  41 */     if (f < this.accuracy) {
/*  42 */       paramList1.add(paramCubicCurve2D);
/*  43 */       paramList.add(approximate(paramCubicCurve2D));
/*  44 */       return f;
/*     */     }
/*  46 */     SplitCubic(paramList1, new float[] { paramCubicCurve2D.x1, paramCubicCurve2D.y1, paramCubicCurve2D.ctrlx1, paramCubicCurve2D.ctrly1, paramCubicCurve2D.ctrlx2, paramCubicCurve2D.ctrly2, paramCubicCurve2D.x2, paramCubicCurve2D.y2 });
/*     */ 
/*  50 */     return approximate(paramList1, paramList);
/*     */   }
/*     */ 
/*     */   public float approximate(List<QuadCurve2D> paramList, CubicCurve2D paramCubicCurve2D)
/*     */   {
/*  55 */     ArrayList localArrayList = new ArrayList();
/*  56 */     return approximate(paramList, localArrayList, paramCubicCurve2D);
/*     */   }
/*     */ 
/*     */   private QuadCurve2D approximate(CubicCurve2D paramCubicCurve2D)
/*     */   {
/*  61 */     return new QuadCurve2D(paramCubicCurve2D.x1, paramCubicCurve2D.y1, (3.0F * paramCubicCurve2D.ctrlx1 - paramCubicCurve2D.x1 + 3.0F * paramCubicCurve2D.ctrlx2 - paramCubicCurve2D.x2) / 4.0F, (3.0F * paramCubicCurve2D.ctrly1 - paramCubicCurve2D.y1 + 3.0F * paramCubicCurve2D.ctrly2 - paramCubicCurve2D.y2) / 4.0F, paramCubicCurve2D.x2, paramCubicCurve2D.y2);
/*     */   }
/*     */ 
/*     */   private float approximate(List<CubicCurve2D> paramList, List<QuadCurve2D> paramList1)
/*     */   {
/*  70 */     QuadCurve2D localQuadCurve2D = approximate((CubicCurve2D)paramList.get(0));
/*  71 */     float f1 = compareCPs((CubicCurve2D)paramList.get(0), elevate(localQuadCurve2D));
/*     */ 
/*  74 */     paramList1.add(localQuadCurve2D);
/*     */ 
/*  76 */     for (int i = 1; i < paramList.size(); i++) {
/*  77 */       localQuadCurve2D = approximate((CubicCurve2D)paramList.get(i));
/*  78 */       float f2 = compareCPs((CubicCurve2D)paramList.get(i), elevate(localQuadCurve2D));
/*     */ 
/*  80 */       if (f2 > f1) {
/*  81 */         f1 = f2;
/*     */       }
/*  83 */       paramList1.add(localQuadCurve2D);
/*     */     }
/*  85 */     return f1;
/*     */   }
/*     */ 
/*     */   private static CubicCurve2D elevate(QuadCurve2D paramQuadCurve2D) {
/*  89 */     return new CubicCurve2D(paramQuadCurve2D.x1, paramQuadCurve2D.y1, (paramQuadCurve2D.x1 + 2.0F * paramQuadCurve2D.ctrlx) / 3.0F, (paramQuadCurve2D.y1 + 2.0F * paramQuadCurve2D.ctrly) / 3.0F, (2.0F * paramQuadCurve2D.ctrlx + paramQuadCurve2D.x2) / 3.0F, (2.0F * paramQuadCurve2D.ctrly + paramQuadCurve2D.y2) / 3.0F, paramQuadCurve2D.x2, paramQuadCurve2D.y2);
/*     */   }
/*     */ 
/*     */   private static float compare(CubicCurve2D paramCubicCurve2D1, CubicCurve2D paramCubicCurve2D2)
/*     */   {
/*  98 */     float f1 = Math.abs(paramCubicCurve2D1.x1 - paramCubicCurve2D2.x1);
/*  99 */     float f2 = Math.abs(paramCubicCurve2D1.y1 - paramCubicCurve2D2.y1);
/* 100 */     if (f1 < f2) f1 = f2;
/* 101 */     f2 = Math.abs(paramCubicCurve2D1.ctrlx1 - paramCubicCurve2D2.ctrlx1);
/* 102 */     if (f1 < f2) f1 = f2;
/* 103 */     f2 = Math.abs(paramCubicCurve2D1.ctrly1 - paramCubicCurve2D2.ctrly1);
/* 104 */     if (f1 < f2) f1 = f2;
/* 105 */     f2 = Math.abs(paramCubicCurve2D1.ctrlx2 - paramCubicCurve2D2.ctrlx2);
/* 106 */     if (f1 < f2) f1 = f2;
/* 107 */     f2 = Math.abs(paramCubicCurve2D1.ctrly2 - paramCubicCurve2D2.ctrly2);
/* 108 */     if (f1 < f2) f1 = f2;
/* 109 */     f2 = Math.abs(paramCubicCurve2D1.x2 - paramCubicCurve2D2.x2);
/* 110 */     if (f1 < f2) f1 = f2;
/* 111 */     f2 = Math.abs(paramCubicCurve2D1.y2 - paramCubicCurve2D2.y2);
/* 112 */     if (f1 < f2) f1 = f2;
/*     */ 
/* 114 */     return f1;
/*     */   }
/*     */ 
/*     */   private static float getApproxError(float[] paramArrayOfFloat)
/*     */   {
/* 121 */     float f1 = (-3.0F * paramArrayOfFloat[2] + paramArrayOfFloat[0] + 3.0F * paramArrayOfFloat[4] - paramArrayOfFloat[6]) / 6.0F;
/*     */ 
/* 123 */     float f2 = (-3.0F * paramArrayOfFloat[3] + paramArrayOfFloat[1] + 3.0F * paramArrayOfFloat[5] - paramArrayOfFloat[7]) / 6.0F;
/* 124 */     if (f1 < f2) f1 = f2;
/* 125 */     f2 = (3.0F * paramArrayOfFloat[2] - paramArrayOfFloat[0] - 3.0F * paramArrayOfFloat[4] + paramArrayOfFloat[6]) / 6.0F;
/* 126 */     if (f1 < f2) f1 = f2;
/* 127 */     f2 = (3.0F * paramArrayOfFloat[3] - paramArrayOfFloat[1] - 3.0F * paramArrayOfFloat[5] + paramArrayOfFloat[7]) / 6.0F;
/* 128 */     if (f1 < f2) f1 = f2;
/* 129 */     return f1;
/*     */   }
/*     */ 
/*     */   public static float getApproxError(CubicCurve2D paramCubicCurve2D) {
/* 133 */     return getApproxError(new float[] { paramCubicCurve2D.x1, paramCubicCurve2D.y1, paramCubicCurve2D.ctrlx1, paramCubicCurve2D.ctrly1, paramCubicCurve2D.ctrlx2, paramCubicCurve2D.ctrly2, paramCubicCurve2D.x2, paramCubicCurve2D.y2 });
/*     */   }
/*     */ 
/*     */   private static float compareCPs(CubicCurve2D paramCubicCurve2D1, CubicCurve2D paramCubicCurve2D2)
/*     */   {
/* 140 */     float f1 = Math.abs(paramCubicCurve2D1.ctrlx1 - paramCubicCurve2D2.ctrlx1);
/* 141 */     float f2 = Math.abs(paramCubicCurve2D1.ctrly1 - paramCubicCurve2D2.ctrly1);
/* 142 */     if (f1 < f2) f1 = f2;
/* 143 */     f2 = Math.abs(paramCubicCurve2D1.ctrlx2 - paramCubicCurve2D2.ctrlx2);
/* 144 */     if (f1 < f2) f1 = f2;
/* 145 */     f2 = Math.abs(paramCubicCurve2D1.ctrly2 - paramCubicCurve2D2.ctrly2);
/* 146 */     if (f1 < f2) f1 = f2;
/* 147 */     return f1;
/*     */   }
/*     */ 
/*     */   private void ProcessMonotonicCubic(List<CubicCurve2D> paramList, float[] paramArrayOfFloat)
/*     */   {
/* 160 */     float[] arrayOfFloat = new float[8];
/*     */     float f4;
/* 165 */     float f3 = f4 = paramArrayOfFloat[0];
/*     */     float f6;
/* 166 */     float f5 = f6 = paramArrayOfFloat[1];
/*     */ 
/* 168 */     for (int i = 2; i < 8; i += 2) {
/* 169 */       f3 = f3 > paramArrayOfFloat[i] ? paramArrayOfFloat[i] : f3;
/* 170 */       f4 = f4 < paramArrayOfFloat[i] ? paramArrayOfFloat[i] : f4;
/* 171 */       f5 = f5 > paramArrayOfFloat[(i + 1)] ? paramArrayOfFloat[(i + 1)] : f5;
/* 172 */       f6 = f6 < paramArrayOfFloat[(i + 1)] ? paramArrayOfFloat[(i + 1)] : f6;
/*     */     }
/*     */ 
/* 175 */     if ((f4 - f3 > this.maxCubicSize) || (f6 - f5 > this.maxCubicSize) || (getApproxError(paramArrayOfFloat) > this.accuracy))
/*     */     {
/* 177 */       arrayOfFloat[6] = paramArrayOfFloat[6];
/* 178 */       arrayOfFloat[7] = paramArrayOfFloat[7];
/* 179 */       arrayOfFloat[4] = ((paramArrayOfFloat[4] + paramArrayOfFloat[6]) / 2.0F);
/* 180 */       arrayOfFloat[5] = ((paramArrayOfFloat[5] + paramArrayOfFloat[7]) / 2.0F);
/* 181 */       float f1 = (paramArrayOfFloat[2] + paramArrayOfFloat[4]) / 2.0F;
/* 182 */       float f2 = (paramArrayOfFloat[3] + paramArrayOfFloat[5]) / 2.0F;
/* 183 */       arrayOfFloat[2] = ((f1 + arrayOfFloat[4]) / 2.0F);
/* 184 */       arrayOfFloat[3] = ((f2 + arrayOfFloat[5]) / 2.0F);
/* 185 */       paramArrayOfFloat[2] = ((paramArrayOfFloat[0] + paramArrayOfFloat[2]) / 2.0F);
/* 186 */       paramArrayOfFloat[3] = ((paramArrayOfFloat[1] + paramArrayOfFloat[3]) / 2.0F);
/* 187 */       paramArrayOfFloat[4] = ((paramArrayOfFloat[2] + f1) / 2.0F);
/* 188 */       paramArrayOfFloat[5] = ((paramArrayOfFloat[3] + f2) / 2.0F);
/*     */       float tmp313_312 = ((paramArrayOfFloat[4] + arrayOfFloat[2]) / 2.0F); arrayOfFloat[0] = tmp313_312; paramArrayOfFloat[6] = tmp313_312;
/*     */       float tmp330_329 = ((paramArrayOfFloat[5] + arrayOfFloat[3]) / 2.0F); arrayOfFloat[1] = tmp330_329; paramArrayOfFloat[7] = tmp330_329;
/*     */ 
/* 192 */       ProcessMonotonicCubic(paramList, paramArrayOfFloat);
/*     */ 
/* 194 */       ProcessMonotonicCubic(paramList, arrayOfFloat);
/*     */     } else {
/* 196 */       paramList.add(new CubicCurve2D(paramArrayOfFloat[0], paramArrayOfFloat[1], paramArrayOfFloat[2], paramArrayOfFloat[3], paramArrayOfFloat[4], paramArrayOfFloat[5], paramArrayOfFloat[6], paramArrayOfFloat[7]));
/*     */     }
/*     */   }
/*     */ 
/*     */   public void SplitCubic(List<CubicCurve2D> paramList, float[] paramArrayOfFloat)
/*     */   {
/* 213 */     float[] arrayOfFloat1 = new float[4];
/* 214 */     float[] arrayOfFloat2 = new float[3];
/* 215 */     float[] arrayOfFloat3 = new float[2];
/* 216 */     int i = 0;
/*     */     int j;
/*     */     int k;
/* 223 */     if (((paramArrayOfFloat[0] > paramArrayOfFloat[2]) || (paramArrayOfFloat[2] > paramArrayOfFloat[4]) || (paramArrayOfFloat[4] > paramArrayOfFloat[6])) && ((paramArrayOfFloat[0] < paramArrayOfFloat[2]) || (paramArrayOfFloat[2] < paramArrayOfFloat[4]) || (paramArrayOfFloat[4] < paramArrayOfFloat[6])))
/*     */     {
/* 233 */       arrayOfFloat2[2] = (-paramArrayOfFloat[0] + 3.0F * paramArrayOfFloat[2] - 3.0F * paramArrayOfFloat[4] + paramArrayOfFloat[6]);
/* 234 */       arrayOfFloat2[1] = (2.0F * (paramArrayOfFloat[0] - 2.0F * paramArrayOfFloat[2] + paramArrayOfFloat[4]));
/* 235 */       arrayOfFloat2[0] = (-paramArrayOfFloat[0] + paramArrayOfFloat[2]);
/*     */ 
/* 237 */       j = QuadCurve2D.solveQuadratic(arrayOfFloat2, arrayOfFloat3);
/*     */ 
/* 243 */       for (k = 0; k < j; k++) {
/* 244 */         if ((arrayOfFloat3[k] > 0.0F) && (arrayOfFloat3[k] < 1.0F)) {
/* 245 */           arrayOfFloat1[(i++)] = arrayOfFloat3[k];
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 255 */     if (((paramArrayOfFloat[1] > paramArrayOfFloat[3]) || (paramArrayOfFloat[3] > paramArrayOfFloat[5]) || (paramArrayOfFloat[5] > paramArrayOfFloat[7])) && ((paramArrayOfFloat[1] < paramArrayOfFloat[3]) || (paramArrayOfFloat[3] < paramArrayOfFloat[5]) || (paramArrayOfFloat[5] < paramArrayOfFloat[7])))
/*     */     {
/* 265 */       arrayOfFloat2[2] = (-paramArrayOfFloat[1] + 3.0F * paramArrayOfFloat[3] - 3.0F * paramArrayOfFloat[5] + paramArrayOfFloat[7]);
/* 266 */       arrayOfFloat2[1] = (2.0F * (paramArrayOfFloat[1] - 2.0F * paramArrayOfFloat[3] + paramArrayOfFloat[5]));
/* 267 */       arrayOfFloat2[0] = (-paramArrayOfFloat[1] + paramArrayOfFloat[3]);
/*     */ 
/* 269 */       j = QuadCurve2D.solveQuadratic(arrayOfFloat2, arrayOfFloat3);
/*     */ 
/* 275 */       for (k = 0; k < j; k++) {
/* 276 */         if ((arrayOfFloat3[k] > 0.0F) && (arrayOfFloat3[k] < 1.0F)) {
/* 277 */           arrayOfFloat1[(i++)] = arrayOfFloat3[k];
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 282 */     if (i > 0)
/*     */     {
/* 286 */       Arrays.sort(arrayOfFloat1, 0, i);
/*     */ 
/* 289 */       ProcessFirstMonotonicPartOfCubic(paramList, paramArrayOfFloat, arrayOfFloat1[0]);
/* 290 */       for (j = 1; j < i; j++) {
/* 291 */         float f = arrayOfFloat1[j] - arrayOfFloat1[(j - 1)];
/* 292 */         if (f > 0.0F) {
/* 293 */           ProcessFirstMonotonicPartOfCubic(paramList, paramArrayOfFloat, f / (1.0F - arrayOfFloat1[(j - 1)]));
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 300 */     ProcessMonotonicCubic(paramList, paramArrayOfFloat);
/*     */   }
/*     */ 
/*     */   private void ProcessFirstMonotonicPartOfCubic(List<CubicCurve2D> paramList, float[] paramArrayOfFloat, float paramFloat)
/*     */   {
/* 312 */     float[] arrayOfFloat = new float[8];
/*     */ 
/* 315 */     arrayOfFloat[0] = paramArrayOfFloat[0];
/* 316 */     arrayOfFloat[1] = paramArrayOfFloat[1];
/* 317 */     float f1 = paramArrayOfFloat[2] + paramFloat * (paramArrayOfFloat[4] - paramArrayOfFloat[2]);
/* 318 */     float f2 = paramArrayOfFloat[3] + paramFloat * (paramArrayOfFloat[5] - paramArrayOfFloat[3]);
/* 319 */     arrayOfFloat[2] = (paramArrayOfFloat[0] + paramFloat * (paramArrayOfFloat[2] - paramArrayOfFloat[0]));
/* 320 */     arrayOfFloat[3] = (paramArrayOfFloat[1] + paramFloat * (paramArrayOfFloat[3] - paramArrayOfFloat[1]));
/* 321 */     arrayOfFloat[4] = (arrayOfFloat[2] + paramFloat * (f1 - arrayOfFloat[2]));
/* 322 */     arrayOfFloat[5] = (arrayOfFloat[3] + paramFloat * (f2 - arrayOfFloat[3]));
/* 323 */     paramArrayOfFloat[4] += paramFloat * (paramArrayOfFloat[6] - paramArrayOfFloat[4]);
/* 324 */     paramArrayOfFloat[5] += paramFloat * (paramArrayOfFloat[7] - paramArrayOfFloat[5]);
/* 325 */     paramArrayOfFloat[2] = (f1 + paramFloat * (paramArrayOfFloat[4] - f1));
/* 326 */     paramArrayOfFloat[3] = (f2 + paramFloat * (paramArrayOfFloat[5] - f2));
/*     */     float tmp203_202 = (arrayOfFloat[4] + paramFloat * (paramArrayOfFloat[2] - arrayOfFloat[4])); arrayOfFloat[6] = tmp203_202; paramArrayOfFloat[0] = tmp203_202;
/*     */     float tmp227_226 = (arrayOfFloat[5] + paramFloat * (paramArrayOfFloat[3] - arrayOfFloat[5])); arrayOfFloat[7] = tmp227_226; paramArrayOfFloat[1] = tmp227_226;
/*     */ 
/* 330 */     ProcessMonotonicCubic(paramList, arrayOfFloat);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.geom.CubicApproximator
 * JD-Core Version:    0.6.2
 */