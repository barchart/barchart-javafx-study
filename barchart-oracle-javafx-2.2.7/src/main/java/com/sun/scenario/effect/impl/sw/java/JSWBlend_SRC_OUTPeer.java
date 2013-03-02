/*     */ package com.sun.scenario.effect.impl.sw.java;
/*     */ 
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.scenario.effect.Blend;
/*     */ import com.sun.scenario.effect.Effect;
/*     */ import com.sun.scenario.effect.FilterContext;
/*     */ import com.sun.scenario.effect.ImageData;
/*     */ import com.sun.scenario.effect.impl.HeapImage;
/*     */ import com.sun.scenario.effect.impl.Renderer;
/*     */ 
/*     */ public class JSWBlend_SRC_OUTPeer extends JSWEffectPeer
/*     */ {
/*     */   public JSWBlend_SRC_OUTPeer(FilterContext paramFilterContext, Renderer paramRenderer, String paramString)
/*     */   {
/*  47 */     super(paramFilterContext, paramRenderer, paramString);
/*     */   }
/*     */ 
/*     */   protected final Blend getEffect()
/*     */   {
/*  52 */     return (Blend)super.getEffect();
/*     */   }
/*     */ 
/*     */   private float getOpacity()
/*     */   {
/*  57 */     return getEffect().getOpacity();
/*     */   }
/*     */ 
/*     */   public ImageData filter(Effect paramEffect, BaseTransform paramBaseTransform, Rectangle paramRectangle, ImageData[] paramArrayOfImageData)
/*     */   {
/*  67 */     setEffect(paramEffect);
/*  68 */     Rectangle localRectangle1 = getResultBounds(paramBaseTransform, paramRectangle, paramArrayOfImageData);
/*  69 */     setDestBounds(localRectangle1);
/*     */ 
/*  72 */     HeapImage localHeapImage1 = (HeapImage)paramArrayOfImageData[1].getTransformedImage(localRectangle1);
/*  73 */     int i = 0;
/*  74 */     int j = 0;
/*  75 */     int k = localHeapImage1.getPhysicalWidth();
/*  76 */     int m = localHeapImage1.getPhysicalHeight();
/*  77 */     int n = localHeapImage1.getScanlineStride();
/*  78 */     int[] arrayOfInt1 = localHeapImage1.getPixelArray();
/*     */ 
/*  80 */     Rectangle localRectangle2 = new Rectangle(i, j, k, m);
/*  81 */     Rectangle localRectangle3 = paramArrayOfImageData[1].getTransformedBounds(localRectangle1);
/*  82 */     BaseTransform localBaseTransform1 = BaseTransform.IDENTITY_TRANSFORM;
/*  83 */     setInputBounds(1, localRectangle3);
/*  84 */     setInputNativeBounds(1, localRectangle2);
/*  85 */     HeapImage localHeapImage2 = (HeapImage)paramArrayOfImageData[0].getTransformedImage(localRectangle1);
/*  86 */     int i1 = 0;
/*  87 */     int i2 = 0;
/*  88 */     int i3 = localHeapImage2.getPhysicalWidth();
/*  89 */     int i4 = localHeapImage2.getPhysicalHeight();
/*  90 */     int i5 = localHeapImage2.getScanlineStride();
/*  91 */     int[] arrayOfInt2 = localHeapImage2.getPixelArray();
/*     */ 
/*  93 */     Rectangle localRectangle4 = new Rectangle(i1, i2, i3, i4);
/*  94 */     Rectangle localRectangle5 = paramArrayOfImageData[0].getTransformedBounds(localRectangle1);
/*  95 */     BaseTransform localBaseTransform2 = BaseTransform.IDENTITY_TRANSFORM;
/*  96 */     setInputBounds(0, localRectangle5);
/*  97 */     setInputNativeBounds(0, localRectangle4);
/*     */ 
/* 100 */     float[] arrayOfFloat1 = new float[4];
/* 101 */     getTextureCoordinates(1, arrayOfFloat1, localRectangle3.x, localRectangle3.y, k, m, localRectangle1, localBaseTransform1);
/*     */ 
/* 105 */     float[] arrayOfFloat2 = new float[4];
/* 106 */     getTextureCoordinates(0, arrayOfFloat2, localRectangle5.x, localRectangle5.y, i3, i4, localRectangle1, localBaseTransform2);
/*     */ 
/* 114 */     int i6 = localRectangle1.width;
/* 115 */     int i7 = localRectangle1.height;
/*     */ 
/* 117 */     HeapImage localHeapImage3 = (HeapImage)getRenderer().getCompatibleImage(i6, i7);
/* 118 */     setDestNativeBounds(localHeapImage3.getPhysicalWidth(), localHeapImage3.getPhysicalHeight());
/* 119 */     int i8 = localHeapImage3.getScanlineStride();
/* 120 */     int[] arrayOfInt3 = localHeapImage3.getPixelArray();
/*     */ 
/* 125 */     float f5 = getOpacity();
/*     */ 
/* 128 */     float f6 = (arrayOfFloat1[2] - arrayOfFloat1[0]) / i6;
/* 129 */     float f7 = (arrayOfFloat1[3] - arrayOfFloat1[1]) / i7;
/* 130 */     float f8 = (arrayOfFloat2[2] - arrayOfFloat2[0]) / i6;
/* 131 */     float f9 = (arrayOfFloat2[3] - arrayOfFloat2[1]) / i7;
/*     */ 
/* 134 */     float f10 = arrayOfFloat1[1] + f7 * 0.5F;
/* 135 */     float f11 = arrayOfFloat2[1] + f9 * 0.5F;
/*     */ 
/* 137 */     for (int i10 = 0; i10 < 0 + i7; i10++) {
/* 138 */       float f12 = i10;
/*     */ 
/* 140 */       int i9 = i10 * i8;
/*     */ 
/* 142 */       float f13 = arrayOfFloat1[0] + f6 * 0.5F;
/* 143 */       float f14 = arrayOfFloat2[0] + f8 * 0.5F;
/*     */ 
/* 145 */       for (int i11 = 0; i11 < 0 + i6; i11++) {
/* 146 */         float f15 = i11;
/*     */ 
/* 152 */         float f20 = f14;
/* 153 */         float f21 = f11;
/*     */         int i12;
/* 155 */         if ((f20 >= 0.0F) && (f21 >= 0.0F)) {
/* 156 */           int i13 = (int)(f20 * i3);
/* 157 */           int i14 = (int)(f21 * i4);
/* 158 */           int i15 = (i13 >= i3) || (i14 >= i4) ? 1 : 0;
/*     */ 
/* 161 */           i12 = i15 != 0 ? 0 : arrayOfInt2[(i14 * i5 + i13)];
/*     */         }
/*     */         else {
/* 164 */           i12 = 0;
/*     */         }
/* 166 */         float f16 = (i12 >> 16 & 0xFF) / 255.0F;
/* 167 */         float f17 = (i12 >> 8 & 0xFF) / 255.0F;
/* 168 */         float f18 = (i12 & 0xFF) / 255.0F;
/* 169 */         float f19 = (i12 >>> 24) / 255.0F;
/*     */ 
/* 172 */         f20 = f16;
/* 173 */         f21 = f17;
/* 174 */         float f22 = f18;
/* 175 */         float f23 = f19;
/*     */ 
/* 177 */         float f24 = f13;
/* 178 */         float f25 = f10;
/*     */         int i16;
/* 180 */         if ((f24 >= 0.0F) && (f25 >= 0.0F)) {
/* 181 */           int i17 = (int)(f24 * k);
/* 182 */           int i18 = (int)(f25 * m);
/* 183 */           int i19 = (i17 >= k) || (i18 >= m) ? 1 : 0;
/*     */ 
/* 186 */           i16 = i19 != 0 ? 0 : arrayOfInt1[(i18 * n + i17)];
/*     */         }
/*     */         else {
/* 189 */           i16 = 0;
/*     */         }
/* 191 */         f16 = (i16 >> 16 & 0xFF) / 255.0F;
/* 192 */         f17 = (i16 >> 8 & 0xFF) / 255.0F;
/* 193 */         f18 = (i16 & 0xFF) / 255.0F;
/* 194 */         f19 = (i16 >>> 24) / 255.0F;
/*     */ 
/* 197 */         f24 = f16 * f5;
/* 198 */         f25 = f17 * f5;
/* 199 */         float f26 = f18 * f5;
/* 200 */         float f27 = f19 * f5;
/*     */ 
/* 203 */         float f32 = f20;
/* 204 */         float f33 = f21;
/* 205 */         float f34 = f22;
/* 206 */         float f35 = f23;
/* 207 */         float f36 = f24;
/* 208 */         float f37 = f25;
/* 209 */         float f38 = f26;
/* 210 */         float f39 = f27;
/*     */ 
/* 212 */         float f28 = f36 * (1.0F - f35);
/* 213 */         float f29 = f37 * (1.0F - f35);
/* 214 */         float f30 = f38 * (1.0F - f35);
/* 215 */         float f31 = f39 * (1.0F - f35);
/*     */ 
/* 219 */         float f1 = f28;
/* 220 */         float f2 = f29;
/* 221 */         float f3 = f30;
/* 222 */         float f4 = f31;
/*     */ 
/* 226 */         if (f4 < 0.0F) f4 = 0.0F; else if (f4 > 1.0F) f4 = 1.0F;
/* 227 */         if (f1 < 0.0F) f1 = 0.0F; else if (f1 > f4) f1 = f4;
/* 228 */         if (f2 < 0.0F) f2 = 0.0F; else if (f2 > f4) f2 = f4;
/* 229 */         if (f3 < 0.0F) f3 = 0.0F; else if (f3 > f4) f3 = f4;
/* 230 */         arrayOfInt3[(i9 + i11)] = ((int)(f1 * 255.0F) << 16 | (int)(f2 * 255.0F) << 8 | (int)(f3 * 255.0F) << 0 | (int)(f4 * 255.0F) << 24);
/*     */ 
/* 236 */         f13 += f6;
/* 237 */         f14 += f8;
/*     */       }
/*     */ 
/* 241 */       f10 += f7;
/* 242 */       f11 += f9;
/*     */     }
/*     */ 
/* 246 */     paramArrayOfImageData[1].releaseTransformedImage(localHeapImage1);
/* 247 */     paramArrayOfImageData[0].releaseTransformedImage(localHeapImage2);
/*     */ 
/* 250 */     return new ImageData(getFilterContext(), localHeapImage3, localRectangle1);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.sw.java.JSWBlend_SRC_OUTPeer
 * JD-Core Version:    0.6.2
 */