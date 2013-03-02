/*     */ package com.sun.scenario.effect.impl.sw.java;
/*     */ 
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.scenario.effect.Effect;
/*     */ import com.sun.scenario.effect.FilterContext;
/*     */ import com.sun.scenario.effect.ImageData;
/*     */ import com.sun.scenario.effect.PerspectiveTransform;
/*     */ import com.sun.scenario.effect.impl.HeapImage;
/*     */ import com.sun.scenario.effect.impl.Renderer;
/*     */ import com.sun.scenario.effect.impl.state.AccessHelper;
/*     */ import com.sun.scenario.effect.impl.state.PerspectiveTransformState;
/*     */ 
/*     */ public class JSWPerspectiveTransformPeer extends JSWEffectPeer
/*     */ {
/*     */   public JSWPerspectiveTransformPeer(FilterContext paramFilterContext, Renderer paramRenderer, String paramString)
/*     */   {
/*  47 */     super(paramFilterContext, paramRenderer, paramString);
/*     */   }
/*     */ 
/*     */   protected final PerspectiveTransform getEffect()
/*     */   {
/*  52 */     return (PerspectiveTransform)super.getEffect();
/*     */   }
/*     */ 
/*     */   private float[][] getITX()
/*     */   {
/*  57 */     PerspectiveTransformState localPerspectiveTransformState = (PerspectiveTransformState)AccessHelper.getState(getEffect());
/*     */ 
/*  59 */     return localPerspectiveTransformState.getITX();
/*     */   }
/*     */ 
/*     */   private float[] getTx0() {
/*  63 */     Rectangle localRectangle1 = getInputBounds(0);
/*  64 */     Rectangle localRectangle2 = getInputNativeBounds(0);
/*  65 */     float f = localRectangle1.width / localRectangle2.width;
/*  66 */     float[] arrayOfFloat = getITX()[0];
/*  67 */     return new float[] { arrayOfFloat[0] * f, arrayOfFloat[1] * f, arrayOfFloat[2] * f };
/*     */   }
/*     */   private float[] getTx1() {
/*  70 */     Rectangle localRectangle1 = getInputBounds(0);
/*  71 */     Rectangle localRectangle2 = getInputNativeBounds(0);
/*  72 */     float f = localRectangle1.height / localRectangle2.height;
/*  73 */     float[] arrayOfFloat = getITX()[1];
/*  74 */     return new float[] { arrayOfFloat[0] * f, arrayOfFloat[1] * f, arrayOfFloat[2] * f };
/*     */   }
/*     */   private float[] getTx2() {
/*  77 */     return getITX()[2];
/*     */   }
/*     */ 
/*     */   public int getTextureCoordinates(int paramInt, float[] paramArrayOfFloat, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, Rectangle paramRectangle, BaseTransform paramBaseTransform)
/*     */   {
/*  88 */     paramArrayOfFloat[0] = paramRectangle.x;
/*  89 */     paramArrayOfFloat[1] = paramRectangle.y;
/*  90 */     paramArrayOfFloat[2] = (paramRectangle.x + paramRectangle.width);
/*  91 */     paramArrayOfFloat[3] = (paramRectangle.y + paramRectangle.height);
/*  92 */     return 4;
/*     */   }
/*     */ 
/*     */   public ImageData filter(Effect paramEffect, BaseTransform paramBaseTransform, Rectangle paramRectangle, ImageData[] paramArrayOfImageData)
/*     */   {
/* 102 */     setEffect(paramEffect);
/* 103 */     Rectangle localRectangle1 = getResultBounds(paramBaseTransform, paramRectangle, paramArrayOfImageData);
/* 104 */     setDestBounds(localRectangle1);
/*     */ 
/* 107 */     HeapImage localHeapImage1 = (HeapImage)paramArrayOfImageData[0].getUntransformedImage();
/* 108 */     int i = 0;
/* 109 */     int j = 0;
/* 110 */     int k = localHeapImage1.getPhysicalWidth();
/* 111 */     int m = localHeapImage1.getPhysicalHeight();
/* 112 */     int n = localHeapImage1.getScanlineStride();
/* 113 */     int[] arrayOfInt1 = localHeapImage1.getPixelArray();
/*     */ 
/* 115 */     Rectangle localRectangle2 = new Rectangle(i, j, k, m);
/* 116 */     Rectangle localRectangle3 = paramArrayOfImageData[0].getUntransformedBounds();
/* 117 */     BaseTransform localBaseTransform = paramArrayOfImageData[0].getTransform();
/* 118 */     setInputBounds(0, localRectangle3);
/* 119 */     setInputNativeBounds(0, localRectangle2);
/* 120 */     float[] arrayOfFloat1 = new float[4];
/*     */ 
/* 123 */     float[] arrayOfFloat2 = new float[4];
/* 124 */     getTextureCoordinates(0, arrayOfFloat2, localRectangle3.x, localRectangle3.y, k, m, localRectangle1, localBaseTransform);
/*     */ 
/* 132 */     int i1 = localRectangle1.width;
/* 133 */     int i2 = localRectangle1.height;
/*     */ 
/* 135 */     HeapImage localHeapImage2 = (HeapImage)getRenderer().getCompatibleImage(i1, i2);
/* 136 */     setDestNativeBounds(localHeapImage2.getPhysicalWidth(), localHeapImage2.getPhysicalHeight());
/* 137 */     int i3 = localHeapImage2.getScanlineStride();
/* 138 */     int[] arrayOfInt2 = localHeapImage2.getPixelArray();
/*     */ 
/* 143 */     float[] arrayOfFloat3 = getTx2();
/* 144 */     float f5 = arrayOfFloat3[0]; float f6 = arrayOfFloat3[1]; float f7 = arrayOfFloat3[2];
/* 145 */     float[] arrayOfFloat4 = getTx1();
/* 146 */     float f8 = arrayOfFloat4[0]; float f9 = arrayOfFloat4[1]; float f10 = arrayOfFloat4[2];
/* 147 */     float[] arrayOfFloat5 = getTx0();
/* 148 */     float f11 = arrayOfFloat5[0]; float f12 = arrayOfFloat5[1]; float f13 = arrayOfFloat5[2];
/*     */ 
/* 151 */     float f14 = (arrayOfFloat2[2] - arrayOfFloat2[0]) / i1;
/* 152 */     float f15 = (arrayOfFloat2[3] - arrayOfFloat2[1]) / i2;
/*     */ 
/* 155 */     float f16 = arrayOfFloat2[1] + f15 * 0.5F;
/*     */ 
/* 157 */     for (int i5 = 0; i5 < 0 + i2; i5++) {
/* 158 */       float f17 = i5;
/*     */ 
/* 160 */       int i4 = i5 * i3;
/*     */ 
/* 162 */       float f18 = arrayOfFloat2[0] + f14 * 0.5F;
/*     */ 
/* 164 */       for (int i6 = 0; i6 < 0 + i1; i6++) {
/* 165 */         float f19 = i6;
/*     */ 
/* 175 */         float f20 = f18;
/* 176 */         float f21 = f16;
/* 177 */         float f22 = 1.0F;
/*     */ 
/* 180 */         float f27 = f20;
/* 181 */         float f28 = f21;
/* 182 */         float f29 = f22;
/* 183 */         float f30 = f5;
/* 184 */         float f31 = f6;
/* 185 */         float f32 = f7;
/* 186 */         float f26 = f27 * f30 + f28 * f31 + f29 * f32;
/*     */ 
/* 191 */         float f25 = f26;
/*     */ 
/* 193 */         f27 = f20;
/* 194 */         f28 = f21;
/* 195 */         f29 = f22;
/* 196 */         f30 = f11;
/* 197 */         f31 = f12;
/* 198 */         f32 = f13;
/* 199 */         f26 = f27 * f30 + f28 * f31 + f29 * f32;
/*     */ 
/* 204 */         float f23 = f26 / f25;
/*     */ 
/* 206 */         f27 = f20;
/* 207 */         f28 = f21;
/* 208 */         f29 = f22;
/* 209 */         f30 = f8;
/* 210 */         f31 = f9;
/* 211 */         f32 = f10;
/* 212 */         f26 = f27 * f30 + f28 * f31 + f29 * f32;
/*     */ 
/* 217 */         float f24 = f26 / f25;
/*     */ 
/* 220 */         f31 = f23;
/* 221 */         f32 = f24;
/* 222 */         lsample(arrayOfInt1, f31, f32, k, m, n, arrayOfFloat1);
/*     */ 
/* 225 */         f27 = arrayOfFloat1[0];
/* 226 */         f28 = arrayOfFloat1[1];
/* 227 */         f29 = arrayOfFloat1[2];
/* 228 */         f30 = arrayOfFloat1[3];
/*     */ 
/* 231 */         float f1 = f27;
/* 232 */         float f2 = f28;
/* 233 */         float f3 = f29;
/* 234 */         float f4 = f30;
/*     */ 
/* 238 */         if (f4 < 0.0F) f4 = 0.0F; else if (f4 > 1.0F) f4 = 1.0F;
/* 239 */         if (f1 < 0.0F) f1 = 0.0F; else if (f1 > f4) f1 = f4;
/* 240 */         if (f2 < 0.0F) f2 = 0.0F; else if (f2 > f4) f2 = f4;
/* 241 */         if (f3 < 0.0F) f3 = 0.0F; else if (f3 > f4) f3 = f4;
/* 242 */         arrayOfInt2[(i4 + i6)] = ((int)(f1 * 255.0F) << 16 | (int)(f2 * 255.0F) << 8 | (int)(f3 * 255.0F) << 0 | (int)(f4 * 255.0F) << 24);
/*     */ 
/* 248 */         f18 += f14;
/*     */       }
/*     */ 
/* 252 */       f16 += f15;
/*     */     }
/*     */ 
/* 257 */     return new ImageData(getFilterContext(), localHeapImage2, localRectangle1);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.sw.java.JSWPerspectiveTransformPeer
 * JD-Core Version:    0.6.2
 */