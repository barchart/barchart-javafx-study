/*     */ package com.sun.scenario.effect.impl.sw.java;
/*     */ 
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.scenario.effect.Effect;
/*     */ import com.sun.scenario.effect.FilterContext;
/*     */ import com.sun.scenario.effect.ImageData;
/*     */ import com.sun.scenario.effect.impl.HeapImage;
/*     */ import com.sun.scenario.effect.impl.Renderer;
/*     */ import com.sun.scenario.effect.impl.state.AccessHelper;
/*     */ import com.sun.scenario.effect.impl.state.LinearConvolveKernel;
/*     */ import com.sun.scenario.effect.impl.state.LinearConvolveKernel.PassType;
/*     */ import com.sun.scenario.effect.impl.state.LinearConvolvePeer;
/*     */ import java.nio.FloatBuffer;
/*     */ 
/*     */ public class JSWLinearConvolvePeer extends JSWEffectPeer
/*     */   implements LinearConvolvePeer
/*     */ {
/*     */   private static final float cmin = 1.0F;
/*     */   private static final float cmax = 254.9375F;
/*     */ 
/*     */   public JSWLinearConvolvePeer(FilterContext paramFilterContext, Renderer paramRenderer, String paramString)
/*     */   {
/*  47 */     super(paramFilterContext, paramRenderer, paramString);
/*     */   }
/*     */ 
/*     */   protected final Effect getEffect()
/*     */   {
/*  52 */     return super.getEffect();
/*     */   }
/*     */ 
/*     */   protected LinearConvolveKernel getKernel() {
/*  56 */     return (LinearConvolveKernel)AccessHelper.getState(getEffect());
/*     */   }
/*     */ 
/*     */   public int getPow2ScaleX(LinearConvolveKernel paramLinearConvolveKernel) {
/*  60 */     return paramLinearConvolveKernel.getPow2ScaleX();
/*     */   }
/*     */ 
/*     */   public int getPow2ScaleY(LinearConvolveKernel paramLinearConvolveKernel) {
/*  64 */     return paramLinearConvolveKernel.getPow2ScaleY();
/*     */   }
/*     */ 
/*     */   public Rectangle getResultBounds(BaseTransform paramBaseTransform, Rectangle paramRectangle, ImageData[] paramArrayOfImageData)
/*     */   {
/*  72 */     Rectangle localRectangle = paramArrayOfImageData[0].getTransformedBounds(null);
/*  73 */     localRectangle = getKernel().getScaledResultBounds(localRectangle, getPass());
/*  74 */     localRectangle.intersectWith(paramRectangle);
/*  75 */     return localRectangle;
/*     */   }
/*     */ 
/*     */   private int getCount() {
/*  79 */     return getKernel().getScaledKernelSize(getPass());
/*     */   }
/*     */ 
/*     */   private float[] getOffset() {
/*  83 */     return getKernel().getVector(getInputNativeBounds(0), getInputTransform(0), getPass());
/*     */   }
/*     */ 
/*     */   private FloatBuffer getWeights()
/*     */   {
/*  89 */     return getKernel().getWeights(getPass());
/*     */   }
/*     */ 
/*     */   public ImageData filter(Effect paramEffect, BaseTransform paramBaseTransform, Rectangle paramRectangle, ImageData[] paramArrayOfImageData)
/*     */   {
/*  98 */     setEffect(paramEffect);
/*  99 */     Rectangle localRectangle1 = getResultBounds(paramBaseTransform, null, paramArrayOfImageData);
/* 100 */     Rectangle localRectangle2 = new Rectangle(localRectangle1);
/* 101 */     localRectangle2.intersectWith(paramRectangle);
/* 102 */     setDestBounds(localRectangle2);
/* 103 */     int i = localRectangle2.width;
/* 104 */     int j = localRectangle2.height;
/*     */ 
/* 107 */     HeapImage localHeapImage1 = (HeapImage)paramArrayOfImageData[0].getUntransformedImage();
/* 108 */     int k = localHeapImage1.getPhysicalWidth();
/* 109 */     int m = localHeapImage1.getPhysicalHeight();
/* 110 */     int n = localHeapImage1.getScanlineStride();
/* 111 */     int[] arrayOfInt1 = localHeapImage1.getPixelArray();
/*     */ 
/* 113 */     Rectangle localRectangle3 = paramArrayOfImageData[0].getUntransformedBounds();
/* 114 */     BaseTransform localBaseTransform = paramArrayOfImageData[0].getTransform();
/* 115 */     Rectangle localRectangle4 = new Rectangle(0, 0, k, m);
/*     */ 
/* 117 */     setInputBounds(0, localRectangle3);
/* 118 */     setInputTransform(0, localBaseTransform);
/* 119 */     setInputNativeBounds(0, localRectangle4);
/*     */ 
/* 121 */     HeapImage localHeapImage2 = (HeapImage)getRenderer().getCompatibleImage(i, j);
/* 122 */     setDestNativeBounds(localHeapImage2.getPhysicalWidth(), localHeapImage2.getPhysicalHeight());
/* 123 */     int i1 = localHeapImage2.getScanlineStride();
/* 124 */     int[] arrayOfInt2 = localHeapImage2.getPixelArray();
/*     */ 
/* 126 */     int i2 = getCount();
/* 127 */     FloatBuffer localFloatBuffer = getWeights();
/*     */ 
/* 129 */     LinearConvolveKernel.PassType localPassType = getKernel().getPassType(getPass());
/* 130 */     if ((!localBaseTransform.isIdentity()) || (!localRectangle2.contains(localRectangle1.x, localRectangle1.y)))
/*     */     {
/* 135 */       localPassType = LinearConvolveKernel.PassType.GENERAL_VECTOR;
/*     */     }
/* 137 */     localPassType = LinearConvolveKernel.PassType.GENERAL_VECTOR;
/*     */     float[] arrayOfFloat1;
/* 138 */     if (localPassType == LinearConvolveKernel.PassType.HORIZONTAL_CENTERED) {
/* 139 */       arrayOfFloat1 = new float[i2 * 2];
/* 140 */       localFloatBuffer.get(arrayOfFloat1, 0, i2);
/* 141 */       localFloatBuffer.rewind();
/* 142 */       localFloatBuffer.get(arrayOfFloat1, i2, i2);
/* 143 */       filterHV(arrayOfInt2, i, j, 1, i1, arrayOfInt1, k, m, 1, n, arrayOfFloat1);
/*     */     }
/* 146 */     else if (localPassType == LinearConvolveKernel.PassType.VERTICAL_CENTERED) {
/* 147 */       arrayOfFloat1 = new float[i2 * 2];
/* 148 */       localFloatBuffer.get(arrayOfFloat1, 0, i2);
/* 149 */       localFloatBuffer.rewind();
/* 150 */       localFloatBuffer.get(arrayOfFloat1, i2, i2);
/* 151 */       filterHV(arrayOfInt2, j, i, i1, 1, arrayOfInt1, m, k, n, 1, arrayOfFloat1);
/*     */     }
/*     */     else
/*     */     {
/* 155 */       arrayOfFloat1 = new float[i2];
/* 156 */       localFloatBuffer.get(arrayOfFloat1, 0, i2);
/*     */ 
/* 158 */       float[] arrayOfFloat2 = new float[8];
/* 159 */       int i3 = getTextureCoordinates(0, arrayOfFloat2, localRectangle3.x, localRectangle3.y, localRectangle4.width, localRectangle4.height, localRectangle2, localBaseTransform);
/*     */ 
/* 164 */       float f1 = arrayOfFloat2[0] * k;
/* 165 */       float f2 = arrayOfFloat2[1] * m;
/*     */       float f3;
/*     */       float f4;
/*     */       float f5;
/*     */       float f6;
/* 167 */       if (i3 < 8) {
/* 168 */         f3 = (arrayOfFloat2[2] - arrayOfFloat2[0]) * k / localRectangle2.width;
/* 169 */         f4 = 0.0F;
/* 170 */         f5 = 0.0F;
/* 171 */         f6 = (arrayOfFloat2[3] - arrayOfFloat2[1]) * m / localRectangle2.height;
/*     */       } else {
/* 173 */         f3 = (arrayOfFloat2[4] - arrayOfFloat2[0]) * k / localRectangle2.width;
/* 174 */         f4 = (arrayOfFloat2[5] - arrayOfFloat2[1]) * m / localRectangle2.height;
/* 175 */         f5 = (arrayOfFloat2[6] - arrayOfFloat2[0]) * k / localRectangle2.width;
/* 176 */         f6 = (arrayOfFloat2[7] - arrayOfFloat2[1]) * m / localRectangle2.height;
/*     */       }
/*     */ 
/* 179 */       float[] arrayOfFloat3 = getOffset();
/* 180 */       float f7 = arrayOfFloat3[0] * k;
/* 181 */       float f8 = arrayOfFloat3[1] * m;
/* 182 */       float f9 = arrayOfFloat3[2] * k;
/* 183 */       float f10 = arrayOfFloat3[3] * m;
/*     */ 
/* 185 */       filterVector(arrayOfInt2, i, j, i1, arrayOfInt1, k, m, n, arrayOfFloat1, i2, f1, f2, f9, f10, f7, f8, f3, f4, f5, f6);
/*     */     }
/*     */ 
/* 194 */     return new ImageData(getFilterContext(), localHeapImage2, localRectangle2);
/*     */   }
/*     */ 
/*     */   protected void filterVector(int[] paramArrayOfInt1, int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt2, int paramInt4, int paramInt5, int paramInt6, float[] paramArrayOfFloat, int paramInt7, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10)
/*     */   {
/* 208 */     int i = 0;
/* 209 */     float[] arrayOfFloat = new float[4];
/*     */ 
/* 211 */     paramFloat1 += (paramFloat9 + paramFloat7) * 0.5F;
/* 212 */     paramFloat2 += (paramFloat10 + paramFloat8) * 0.5F;
/* 213 */     for (int j = 0; j < paramInt2; j++) {
/* 214 */       float f1 = paramFloat1;
/* 215 */       float f2 = paramFloat2;
/* 216 */       for (int k = 0; k < paramInt1; k++)
/*     */       {
/*     */         float tmp77_76 = (arrayOfFloat[2] = arrayOfFloat[3] = 0.0F); arrayOfFloat[1] = tmp77_76; arrayOfFloat[0] = tmp77_76;
/* 218 */         float f3 = f1 + paramFloat3;
/* 219 */         float f4 = f2 + paramFloat4;
/* 220 */         for (int m = 0; m < paramInt7; m++) {
/* 221 */           laccumsample(paramArrayOfInt2, f3, f4, paramInt4, paramInt5, paramInt6, paramArrayOfFloat[m], arrayOfFloat);
/*     */ 
/* 224 */           f3 += paramFloat5;
/* 225 */           f4 += paramFloat6;
/*     */         }
/* 227 */         paramArrayOfInt1[(i + k)] = (((arrayOfFloat[3] > 254.9375F ? 'ÿ' : arrayOfFloat[3] < 1.0F ? 0 : (int)arrayOfFloat[3]) << 24) + ((arrayOfFloat[0] > 254.9375F ? 'ÿ' : arrayOfFloat[0] < 1.0F ? 0 : (int)arrayOfFloat[0]) << 16) + ((arrayOfFloat[1] > 254.9375F ? 'ÿ' : arrayOfFloat[1] < 1.0F ? 0 : (int)arrayOfFloat[1]) << 8) + (arrayOfFloat[2] > 254.9375F ? 'ÿ' : arrayOfFloat[2] < 1.0F ? 0 : (int)arrayOfFloat[2]));
/*     */ 
/* 232 */         f1 += paramFloat7;
/* 233 */         f2 += paramFloat8;
/*     */       }
/* 235 */       paramFloat1 += paramFloat9;
/* 236 */       paramFloat2 += paramFloat10;
/* 237 */       i += paramInt3;
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void filterHV(int[] paramArrayOfInt1, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfInt2, int paramInt5, int paramInt6, int paramInt7, int paramInt8, float[] paramArrayOfFloat)
/*     */   {
/* 255 */     int i = paramArrayOfFloat.length / 2;
/* 256 */     float[] arrayOfFloat = new float[i * 4];
/* 257 */     int j = 0;
/* 258 */     int k = 0;
/* 259 */     for (int m = 0; m < paramInt2; m++) {
/* 260 */       int n = j;
/* 261 */       int i1 = k;
/*     */ 
/* 265 */       for (int i2 = 0; i2 < arrayOfFloat.length; i2++) {
/* 266 */         arrayOfFloat[i2] = 0.0F;
/*     */       }
/* 268 */       i2 = i;
/* 269 */       for (int i3 = 0; i3 < paramInt1; i3++)
/*     */       {
/* 271 */         int i4 = (i - i2) * 4;
/* 272 */         int i5 = i3 < paramInt5 ? paramArrayOfInt2[i1] : 0;
/* 273 */         arrayOfFloat[(i4 + 0)] = (i5 >>> 24);
/* 274 */         arrayOfFloat[(i4 + 1)] = (i5 >> 16 & 0xFF);
/* 275 */         arrayOfFloat[(i4 + 2)] = (i5 >> 8 & 0xFF);
/* 276 */         arrayOfFloat[(i4 + 3)] = (i5 & 0xFF);
/*     */ 
/* 278 */         i2--; if (i2 <= 0) {
/* 279 */           i2 += i;
/*     */         }
/* 281 */         float f1 = 0.0F;
/* 282 */         float f2 = 0.0F;
/* 283 */         float f3 = 0.0F;
/* 284 */         float f4 = 0.0F;
/* 285 */         for (i4 = 0; i4 < arrayOfFloat.length; i4 += 4) {
/* 286 */           float f5 = paramArrayOfFloat[(i2 + (i4 >> 2))];
/* 287 */           f1 += arrayOfFloat[(i4 + 0)] * f5;
/* 288 */           f2 += arrayOfFloat[(i4 + 1)] * f5;
/* 289 */           f3 += arrayOfFloat[(i4 + 2)] * f5;
/* 290 */           f4 += arrayOfFloat[(i4 + 3)] * f5;
/*     */         }
/* 292 */         paramArrayOfInt1[n] = (((f1 > 254.9375F ? 'ÿ' : f1 < 1.0F ? 0 : (int)f1) << 24) + ((f2 > 254.9375F ? 'ÿ' : f2 < 1.0F ? 0 : (int)f2) << 16) + ((f3 > 254.9375F ? 'ÿ' : f3 < 1.0F ? 0 : (int)f3) << 8) + (f4 > 254.9375F ? 'ÿ' : f4 < 1.0F ? 0 : (int)f4));
/*     */ 
/* 297 */         n += paramInt3;
/* 298 */         i1 += paramInt7;
/*     */       }
/* 300 */       j += paramInt4;
/* 301 */       k += paramInt8;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.sw.java.JSWLinearConvolvePeer
 * JD-Core Version:    0.6.2
 */