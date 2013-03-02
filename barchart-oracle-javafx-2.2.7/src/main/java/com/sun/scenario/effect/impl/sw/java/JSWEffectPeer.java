/*     */ package com.sun.scenario.effect.impl.sw.java;
/*     */ 
/*     */ import com.sun.scenario.effect.FilterContext;
/*     */ import com.sun.scenario.effect.impl.EffectPeer;
/*     */ import com.sun.scenario.effect.impl.Renderer;
/*     */ 
/*     */ public abstract class JSWEffectPeer extends EffectPeer
/*     */ {
/*     */   protected static final int FVALS_A = 3;
/*     */   protected static final int FVALS_R = 0;
/*     */   protected static final int FVALS_G = 1;
/*     */   protected static final int FVALS_B = 2;
/*     */ 
/*     */   protected JSWEffectPeer(FilterContext paramFilterContext, Renderer paramRenderer, String paramString)
/*     */   {
/*  36 */     super(paramFilterContext, paramRenderer, paramString);
/*     */   }
/*     */ 
/*     */   protected final void laccum(int paramInt, float paramFloat, float[] paramArrayOfFloat)
/*     */   {
/*  45 */     paramFloat /= 255.0F;
/*  46 */     paramArrayOfFloat[0] += (paramInt >> 16 & 0xFF) * paramFloat;
/*  47 */     paramArrayOfFloat[1] += (paramInt >> 8 & 0xFF) * paramFloat;
/*  48 */     paramArrayOfFloat[2] += (paramInt & 0xFF) * paramFloat;
/*  49 */     paramArrayOfFloat[3] += (paramInt >>> 24) * paramFloat;
/*     */   }
/*     */ 
/*     */   protected final void lsample(int[] paramArrayOfInt, float paramFloat1, float paramFloat2, int paramInt1, int paramInt2, int paramInt3, float[] paramArrayOfFloat)
/*     */   {
/*  57 */     paramArrayOfFloat[0] = 0.0F;
/*  58 */     paramArrayOfFloat[1] = 0.0F;
/*  59 */     paramArrayOfFloat[2] = 0.0F;
/*  60 */     paramArrayOfFloat[3] = 0.0F;
/*  61 */     if ((paramFloat1 >= 0.0F) && (paramFloat2 >= 0.0F) && (paramFloat1 < 1.0F) && (paramFloat2 < 1.0F))
/*     */     {
/*  65 */       paramFloat1 = paramFloat1 * paramInt1 + 0.5F;
/*  66 */       paramFloat2 = paramFloat2 * paramInt2 + 0.5F;
/*  67 */       int i = (int)paramFloat1;
/*  68 */       int j = (int)paramFloat2;
/*  69 */       paramFloat1 -= i;
/*  70 */       paramFloat2 -= j;
/*     */ 
/*  72 */       int k = j * paramInt3 + i;
/*  73 */       float f = paramFloat1 * paramFloat2;
/*  74 */       if (j < paramInt2) {
/*  75 */         if (i < paramInt1) {
/*  76 */           laccum(paramArrayOfInt[k], f, paramArrayOfFloat);
/*     */         }
/*  78 */         if (i > 0) {
/*  79 */           laccum(paramArrayOfInt[(k - 1)], paramFloat2 - f, paramArrayOfFloat);
/*     */         }
/*     */       }
/*  82 */       if (j > 0) {
/*  83 */         if (i < paramInt1) {
/*  84 */           laccum(paramArrayOfInt[(k - paramInt3)], paramFloat1 - f, paramArrayOfFloat);
/*     */         }
/*  86 */         if (i > 0)
/*  87 */           laccum(paramArrayOfInt[(k - paramInt3 - 1)], 1.0F - paramFloat1 - paramFloat2 + f, paramArrayOfFloat);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected final void laccumsample(int[] paramArrayOfInt, float paramFloat1, float paramFloat2, int paramInt1, int paramInt2, int paramInt3, float paramFloat3, float[] paramArrayOfFloat)
/*     */   {
/*  98 */     paramFloat3 *= 255.0F;
/*  99 */     if ((paramFloat1 >= 0.0F) && (paramFloat2 >= 0.0F) && (paramFloat1 < paramInt1) && (paramFloat2 < paramInt2))
/*     */     {
/* 103 */       paramFloat1 += 0.5F;
/* 104 */       paramFloat2 += 0.5F;
/* 105 */       int i = (int)paramFloat1;
/* 106 */       int j = (int)paramFloat2;
/* 107 */       paramFloat1 -= i;
/* 108 */       paramFloat2 -= j;
/*     */ 
/* 110 */       int k = j * paramInt3 + i;
/* 111 */       float f = paramFloat1 * paramFloat2;
/* 112 */       if (j < paramInt2) {
/* 113 */         if (i < paramInt1) {
/* 114 */           laccum(paramArrayOfInt[k], f * paramFloat3, paramArrayOfFloat);
/*     */         }
/* 116 */         if (i > 0) {
/* 117 */           laccum(paramArrayOfInt[(k - 1)], (paramFloat2 - f) * paramFloat3, paramArrayOfFloat);
/*     */         }
/*     */       }
/* 120 */       if (j > 0) {
/* 121 */         if (i < paramInt1) {
/* 122 */           laccum(paramArrayOfInt[(k - paramInt3)], (paramFloat1 - f) * paramFloat3, paramArrayOfFloat);
/*     */         }
/* 124 */         if (i > 0)
/* 125 */           laccum(paramArrayOfInt[(k - paramInt3 - 1)], (1.0F - paramFloat1 - paramFloat2 + f) * paramFloat3, paramArrayOfFloat);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected final void faccum(float[] paramArrayOfFloat1, int paramInt, float paramFloat, float[] paramArrayOfFloat2)
/*     */   {
/* 134 */     paramArrayOfFloat2[0] += paramArrayOfFloat1[paramInt] * paramFloat;
/* 135 */     paramArrayOfFloat2[1] += paramArrayOfFloat1[(paramInt + 1)] * paramFloat;
/* 136 */     paramArrayOfFloat2[2] += paramArrayOfFloat1[(paramInt + 2)] * paramFloat;
/* 137 */     paramArrayOfFloat2[3] += paramArrayOfFloat1[(paramInt + 3)] * paramFloat;
/*     */   }
/*     */ 
/*     */   protected final void fsample(float[] paramArrayOfFloat1, float paramFloat1, float paramFloat2, int paramInt1, int paramInt2, int paramInt3, float[] paramArrayOfFloat2)
/*     */   {
/* 145 */     paramArrayOfFloat2[0] = 0.0F;
/* 146 */     paramArrayOfFloat2[1] = 0.0F;
/* 147 */     paramArrayOfFloat2[2] = 0.0F;
/* 148 */     paramArrayOfFloat2[3] = 0.0F;
/* 149 */     if ((paramFloat1 >= 0.0F) && (paramFloat2 >= 0.0F) && (paramFloat1 < 1.0F) && (paramFloat2 < 1.0F))
/*     */     {
/* 153 */       paramFloat1 = paramFloat1 * paramInt1 + 0.5F;
/* 154 */       paramFloat2 = paramFloat2 * paramInt2 + 0.5F;
/* 155 */       int i = (int)paramFloat1;
/* 156 */       int j = (int)paramFloat2;
/* 157 */       paramFloat1 -= i;
/* 158 */       paramFloat2 -= j;
/*     */ 
/* 160 */       int k = 4 * (j * paramInt3 + i);
/* 161 */       float f = paramFloat1 * paramFloat2;
/* 162 */       if (j < paramInt2) {
/* 163 */         if (i < paramInt1) {
/* 164 */           faccum(paramArrayOfFloat1, k, f, paramArrayOfFloat2);
/*     */         }
/* 166 */         if (i > 0) {
/* 167 */           faccum(paramArrayOfFloat1, k - 4, paramFloat2 - f, paramArrayOfFloat2);
/*     */         }
/*     */       }
/* 170 */       if (j > 0) {
/* 171 */         if (i < paramInt1) {
/* 172 */           faccum(paramArrayOfFloat1, k - paramInt3 * 4, paramFloat1 - f, paramArrayOfFloat2);
/*     */         }
/* 174 */         if (i > 0)
/* 175 */           faccum(paramArrayOfFloat1, k - paramInt3 * 4 - 4, 1.0F - paramFloat1 - paramFloat2 + f, paramArrayOfFloat2);
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.sw.java.JSWEffectPeer
 * JD-Core Version:    0.6.2
 */