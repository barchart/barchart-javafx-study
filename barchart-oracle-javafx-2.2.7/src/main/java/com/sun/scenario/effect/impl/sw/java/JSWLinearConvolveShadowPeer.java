/*     */ package com.sun.scenario.effect.impl.sw.java;
/*     */ 
/*     */ import com.sun.scenario.effect.FilterContext;
/*     */ import com.sun.scenario.effect.impl.Renderer;
/*     */ import com.sun.scenario.effect.impl.state.LinearConvolveKernel;
/*     */ 
/*     */ public class JSWLinearConvolveShadowPeer extends JSWLinearConvolvePeer
/*     */ {
/*     */   public JSWLinearConvolveShadowPeer(FilterContext paramFilterContext, Renderer paramRenderer, String paramString)
/*     */   {
/*  36 */     super(paramFilterContext, paramRenderer, paramString);
/*     */   }
/*     */ 
/*     */   private float[] getShadowColor() {
/*  40 */     return getKernel().getShadowColorComponents(getPass());
/*     */   }
/*     */ 
/*     */   protected void filterVector(int[] paramArrayOfInt1, int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt2, int paramInt4, int paramInt5, int paramInt6, float[] paramArrayOfFloat, int paramInt7, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10)
/*     */   {
/*  52 */     float[] arrayOfFloat = getShadowColor();
/*     */ 
/*  54 */     int i = 0;
/*     */ 
/*  56 */     paramFloat1 += (paramFloat9 + paramFloat7) * 0.5F;
/*  57 */     paramFloat2 += (paramFloat10 + paramFloat8) * 0.5F;
/*  58 */     for (int j = 0; j < paramInt2; j++) {
/*  59 */       float f1 = paramFloat1;
/*  60 */       float f2 = paramFloat2;
/*  61 */       for (int k = 0; k < paramInt1; k++) {
/*  62 */         float f3 = 0.0F;
/*  63 */         float f4 = f1 + paramFloat3;
/*  64 */         float f5 = f2 + paramFloat4;
/*  65 */         for (int m = 0; m < paramInt7; m++) {
/*  66 */           if ((f4 >= 0.0F) && (f5 >= 0.0F)) {
/*  67 */             int n = (int)f4;
/*  68 */             int i1 = (int)f5;
/*  69 */             if ((n < paramInt4) && (i1 < paramInt5))
/*     */             {
/*  71 */               int i2 = paramArrayOfInt2[(i1 * paramInt6 + n)];
/*  72 */               f3 += (i2 >>> 24) * paramArrayOfFloat[m];
/*     */             }
/*     */           }
/*  75 */           f4 += paramFloat5;
/*  76 */           f5 += paramFloat6;
/*     */         }
/*  78 */         f3 = f3 > 255.0F ? 255.0F : f3 < 0.0F ? 0.0F : f3;
/*  79 */         paramArrayOfInt1[(i + k)] = ((int)(arrayOfFloat[0] * f3) << 16 | (int)(arrayOfFloat[1] * f3) << 8 | (int)(arrayOfFloat[2] * f3) | (int)(arrayOfFloat[3] * f3) << 24);
/*     */ 
/*  83 */         f1 += paramFloat7;
/*  84 */         f2 += paramFloat8;
/*     */       }
/*  86 */       paramFloat1 += paramFloat9;
/*  87 */       paramFloat2 += paramFloat10;
/*  88 */       i += paramInt3;
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void filterHV(int[] paramArrayOfInt1, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfInt2, int paramInt5, int paramInt6, int paramInt7, int paramInt8, float[] paramArrayOfFloat)
/*     */   {
/* 105 */     float[] arrayOfFloat1 = getShadowColor();
/*     */ 
/* 109 */     int i = paramArrayOfFloat.length / 2;
/* 110 */     float[] arrayOfFloat2 = new float[i];
/* 111 */     int j = 0;
/* 112 */     int k = 0;
/* 113 */     int[] arrayOfInt = new int[256];
/* 114 */     for (int m = 0; m < arrayOfInt.length; m++) {
/* 115 */       arrayOfInt[m] = ((int)(arrayOfFloat1[0] * m) << 16 | (int)(arrayOfFloat1[1] * m) << 8 | (int)(arrayOfFloat1[2] * m) | (int)(arrayOfFloat1[3] * m) << 24);
/*     */     }
/*     */ 
/* 120 */     for (m = 0; m < paramInt2; m++) {
/* 121 */       int n = j;
/* 122 */       int i1 = k;
/*     */ 
/* 126 */       for (int i2 = 0; i2 < arrayOfFloat2.length; i2++) {
/* 127 */         arrayOfFloat2[i2] = 0.0F;
/*     */       }
/* 129 */       i2 = i;
/* 130 */       for (int i3 = 0; i3 < paramInt1; i3++)
/*     */       {
/* 132 */         arrayOfFloat2[(i - i2)] = ((i3 < paramInt5 ? paramArrayOfInt2[i1] : 0) >>> 24);
/*     */ 
/* 135 */         i2--; if (i2 <= 0) {
/* 136 */           i2 += i;
/*     */         }
/* 138 */         float f = -0.5F;
/* 139 */         for (int i4 = 0; i4 < arrayOfFloat2.length; i4++) {
/* 140 */           f += arrayOfFloat2[i4] * paramArrayOfFloat[(i2 + i4)];
/*     */         }
/* 142 */         paramArrayOfInt1[n] = (f >= 254.0F ? arrayOfInt['ÿ'] : f < 0.0F ? 0 : arrayOfInt[((int)f + 1)]);
/*     */ 
/* 146 */         n += paramInt3;
/* 147 */         i1 += paramInt7;
/*     */       }
/* 149 */       j += paramInt4;
/* 150 */       k += paramInt8;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.sw.java.JSWLinearConvolveShadowPeer
 * JD-Core Version:    0.6.2
 */