/*     */ package com.sun.scenario.effect.impl.sw.java;
/*     */ 
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.scenario.effect.DisplacementMap;
/*     */ import com.sun.scenario.effect.Effect;
/*     */ import com.sun.scenario.effect.FilterContext;
/*     */ import com.sun.scenario.effect.FloatMap;
/*     */ import com.sun.scenario.effect.ImageData;
/*     */ import com.sun.scenario.effect.impl.HeapImage;
/*     */ import com.sun.scenario.effect.impl.Renderer;
/*     */ 
/*     */ public class JSWDisplacementMapPeer extends JSWEffectPeer
/*     */ {
/*     */   public JSWDisplacementMapPeer(FilterContext paramFilterContext, Renderer paramRenderer, String paramString)
/*     */   {
/*  47 */     super(paramFilterContext, paramRenderer, paramString);
/*     */   }
/*     */ 
/*     */   protected final DisplacementMap getEffect()
/*     */   {
/*  52 */     return (DisplacementMap)super.getEffect();
/*     */   }
/*     */ 
/*     */   private float[] getSampletx()
/*     */   {
/*  57 */     return new float[] { getEffect().getOffsetX(), getEffect().getOffsetY(), getEffect().getScaleX(), getEffect().getScaleY() };
/*     */   }
/*     */ 
/*     */   private float[] getImagetx()
/*     */   {
/*  65 */     float f = getEffect().getWrap() ? 0.5F : 0.0F;
/*  66 */     return new float[] { f / getInputNativeBounds(0).width, f / getInputNativeBounds(0).height, (getInputBounds(0).width - 2.0F * f) / getInputNativeBounds(0).width, (getInputBounds(0).height - 2.0F * f) / getInputNativeBounds(0).height };
/*     */   }
/*     */ 
/*     */   private float getWrap()
/*     */   {
/*  74 */     return getEffect().getWrap() ? 1.0F : 0.0F;
/*     */   }
/*     */ 
/*     */   protected Object getSamplerData(int paramInt)
/*     */   {
/*  79 */     return getEffect().getMapData();
/*     */   }
/*     */ 
/*     */   public int getTextureCoordinates(int paramInt, float[] paramArrayOfFloat, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, Rectangle paramRectangle, BaseTransform paramBaseTransform)
/*     */   {
/*     */     float tmp5_4 = 0.0F; paramArrayOfFloat[1] = tmp5_4; paramArrayOfFloat[0] = tmp5_4;
/*     */     float tmp13_12 = 1.0F; paramArrayOfFloat[3] = tmp13_12; paramArrayOfFloat[2] = tmp13_12;
/*  92 */     return 4;
/*     */   }
/*     */ 
/*     */   public ImageData filter(Effect paramEffect, BaseTransform paramBaseTransform, Rectangle paramRectangle, ImageData[] paramArrayOfImageData)
/*     */   {
/* 102 */     setEffect(paramEffect);
/* 103 */     Rectangle localRectangle1 = getResultBounds(paramBaseTransform, paramRectangle, paramArrayOfImageData);
/* 104 */     setDestBounds(localRectangle1);
/*     */ 
/* 107 */     FloatMap localFloatMap = (FloatMap)getSamplerData(1);
/* 108 */     int i = 0;
/* 109 */     int j = 0;
/* 110 */     int k = localFloatMap.getWidth();
/* 111 */     int m = localFloatMap.getHeight();
/* 112 */     int n = localFloatMap.getWidth();
/* 113 */     float[] arrayOfFloat1 = localFloatMap.getData();
/* 114 */     float[] arrayOfFloat2 = new float[4];
/* 115 */     HeapImage localHeapImage1 = (HeapImage)paramArrayOfImageData[0].getUntransformedImage();
/* 116 */     int i1 = 0;
/* 117 */     int i2 = 0;
/* 118 */     int i3 = localHeapImage1.getPhysicalWidth();
/* 119 */     int i4 = localHeapImage1.getPhysicalHeight();
/* 120 */     int i5 = localHeapImage1.getScanlineStride();
/* 121 */     int[] arrayOfInt1 = localHeapImage1.getPixelArray();
/*     */ 
/* 123 */     Rectangle localRectangle2 = new Rectangle(i1, i2, i3, i4);
/* 124 */     Rectangle localRectangle3 = paramArrayOfImageData[0].getUntransformedBounds();
/* 125 */     BaseTransform localBaseTransform = paramArrayOfImageData[0].getTransform();
/* 126 */     setInputBounds(0, localRectangle3);
/* 127 */     setInputNativeBounds(0, localRectangle2);
/* 128 */     float[] arrayOfFloat3 = new float[4];
/*     */ 
/* 131 */     float[] arrayOfFloat4 = { 0.0F, 0.0F, 1.0F, 1.0F };
/* 132 */     float[] arrayOfFloat5 = new float[4];
/* 133 */     getTextureCoordinates(0, arrayOfFloat5, localRectangle3.x, localRectangle3.y, i3, i4, localRectangle1, localBaseTransform);
/*     */ 
/* 141 */     int i6 = localRectangle1.width;
/* 142 */     int i7 = localRectangle1.height;
/*     */ 
/* 144 */     HeapImage localHeapImage2 = (HeapImage)getRenderer().getCompatibleImage(i6, i7);
/* 145 */     setDestNativeBounds(localHeapImage2.getPhysicalWidth(), localHeapImage2.getPhysicalHeight());
/* 146 */     int i8 = localHeapImage2.getScanlineStride();
/* 147 */     int[] arrayOfInt2 = localHeapImage2.getPixelArray();
/*     */ 
/* 152 */     float f5 = getWrap();
/* 153 */     float[] arrayOfFloat6 = getImagetx();
/* 154 */     float f6 = arrayOfFloat6[0]; float f7 = arrayOfFloat6[1]; float f8 = arrayOfFloat6[2]; float f9 = arrayOfFloat6[3];
/* 155 */     float[] arrayOfFloat7 = getSampletx();
/* 156 */     float f10 = arrayOfFloat7[0]; float f11 = arrayOfFloat7[1]; float f12 = arrayOfFloat7[2]; float f13 = arrayOfFloat7[3];
/*     */ 
/* 159 */     float f14 = (arrayOfFloat4[2] - arrayOfFloat4[0]) / i6;
/* 160 */     float f15 = (arrayOfFloat4[3] - arrayOfFloat4[1]) / i7;
/* 161 */     float f16 = (arrayOfFloat5[2] - arrayOfFloat5[0]) / i6;
/* 162 */     float f17 = (arrayOfFloat5[3] - arrayOfFloat5[1]) / i7;
/*     */ 
/* 165 */     float f18 = arrayOfFloat4[1] + f15 * 0.5F;
/* 166 */     float f19 = arrayOfFloat5[1] + f17 * 0.5F;
/*     */ 
/* 168 */     for (int i10 = 0; i10 < 0 + i7; i10++) {
/* 169 */       float f20 = i10;
/*     */ 
/* 171 */       int i9 = i10 * i8;
/*     */ 
/* 173 */       float f21 = arrayOfFloat4[0] + f14 * 0.5F;
/* 174 */       float f22 = arrayOfFloat5[0] + f16 * 0.5F;
/*     */ 
/* 176 */       for (int i11 = 0; i11 < 0 + i6; i11++) {
/* 177 */         float f23 = i11;
/*     */ 
/* 183 */         float f28 = f21;
/* 184 */         float f29 = f18;
/* 185 */         fsample(arrayOfFloat1, f28, f29, k, m, n, arrayOfFloat2);
/*     */ 
/* 188 */         float f24 = arrayOfFloat2[0];
/* 189 */         float f25 = arrayOfFloat2[1];
/* 190 */         float f26 = arrayOfFloat2[2];
/* 191 */         float f27 = arrayOfFloat2[3];
/*     */ 
/* 194 */         f28 = f24;
/* 195 */         f29 = f25;
/* 196 */         float f30 = f26;
/* 197 */         float f31 = f27;
/* 198 */         float f32 = f22 + f12 * (f28 + f10);
/* 199 */         float f33 = f19 + f13 * (f29 + f11);
/*     */ 
/* 202 */         float f36 = f32;
/* 203 */         float f37 = f33;
/* 204 */         float f34 = (float)Math.floor(f36);
/* 205 */         float f35 = (float)Math.floor(f37);
/*     */ 
/* 208 */         f32 -= f5 * f34;
/* 209 */         f33 -= f5 * f35;
/* 210 */         f32 = f6 + f32 * f8;
/* 211 */         f33 = f7 + f33 * f9;
/*     */ 
/* 213 */         f36 = f32;
/* 214 */         f37 = f33;
/* 215 */         lsample(arrayOfInt1, f36, f37, i3, i4, i5, arrayOfFloat3);
/*     */ 
/* 218 */         f24 = arrayOfFloat3[0];
/* 219 */         f25 = arrayOfFloat3[1];
/* 220 */         f26 = arrayOfFloat3[2];
/* 221 */         f27 = arrayOfFloat3[3];
/*     */ 
/* 224 */         float f1 = f24;
/* 225 */         float f2 = f25;
/* 226 */         float f3 = f26;
/* 227 */         float f4 = f27;
/*     */ 
/* 231 */         if (f4 < 0.0F) f4 = 0.0F; else if (f4 > 1.0F) f4 = 1.0F;
/* 232 */         if (f1 < 0.0F) f1 = 0.0F; else if (f1 > f4) f1 = f4;
/* 233 */         if (f2 < 0.0F) f2 = 0.0F; else if (f2 > f4) f2 = f4;
/* 234 */         if (f3 < 0.0F) f3 = 0.0F; else if (f3 > f4) f3 = f4;
/* 235 */         arrayOfInt2[(i9 + i11)] = ((int)(f1 * 255.0F) << 16 | (int)(f2 * 255.0F) << 8 | (int)(f3 * 255.0F) << 0 | (int)(f4 * 255.0F) << 24);
/*     */ 
/* 241 */         f21 += f14;
/* 242 */         f22 += f16;
/*     */       }
/*     */ 
/* 246 */       f18 += f15;
/* 247 */       f19 += f17;
/*     */     }
/*     */ 
/* 252 */     return new ImageData(getFilterContext(), localHeapImage2, localRectangle1);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.sw.java.JSWDisplacementMapPeer
 * JD-Core Version:    0.6.2
 */