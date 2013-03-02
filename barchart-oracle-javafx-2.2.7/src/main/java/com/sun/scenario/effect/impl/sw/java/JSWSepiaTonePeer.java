/*     */ package com.sun.scenario.effect.impl.sw.java;
/*     */ 
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.scenario.effect.Effect;
/*     */ import com.sun.scenario.effect.FilterContext;
/*     */ import com.sun.scenario.effect.ImageData;
/*     */ import com.sun.scenario.effect.SepiaTone;
/*     */ import com.sun.scenario.effect.impl.HeapImage;
/*     */ import com.sun.scenario.effect.impl.Renderer;
/*     */ 
/*     */ public class JSWSepiaTonePeer extends JSWEffectPeer
/*     */ {
/*     */   public JSWSepiaTonePeer(FilterContext paramFilterContext, Renderer paramRenderer, String paramString)
/*     */   {
/*  47 */     super(paramFilterContext, paramRenderer, paramString);
/*     */   }
/*     */ 
/*     */   protected final SepiaTone getEffect()
/*     */   {
/*  52 */     return (SepiaTone)super.getEffect();
/*     */   }
/*     */ 
/*     */   private float getLevel()
/*     */   {
/*  57 */     return getEffect().getLevel();
/*     */   }
/*     */ 
/*     */   public ImageData filter(Effect paramEffect, BaseTransform paramBaseTransform, Rectangle paramRectangle, ImageData[] paramArrayOfImageData)
/*     */   {
/*  67 */     setEffect(paramEffect);
/*  68 */     Rectangle localRectangle1 = getResultBounds(paramBaseTransform, paramRectangle, paramArrayOfImageData);
/*  69 */     setDestBounds(localRectangle1);
/*     */ 
/*  72 */     HeapImage localHeapImage1 = (HeapImage)paramArrayOfImageData[0].getTransformedImage(localRectangle1);
/*  73 */     int i = 0;
/*  74 */     int j = 0;
/*  75 */     int k = localHeapImage1.getPhysicalWidth();
/*  76 */     int m = localHeapImage1.getPhysicalHeight();
/*  77 */     int n = localHeapImage1.getScanlineStride();
/*  78 */     int[] arrayOfInt1 = localHeapImage1.getPixelArray();
/*     */ 
/*  80 */     Rectangle localRectangle2 = new Rectangle(i, j, k, m);
/*  81 */     Rectangle localRectangle3 = paramArrayOfImageData[0].getTransformedBounds(localRectangle1);
/*  82 */     BaseTransform localBaseTransform = BaseTransform.IDENTITY_TRANSFORM;
/*  83 */     setInputBounds(0, localRectangle3);
/*  84 */     setInputNativeBounds(0, localRectangle2);
/*     */ 
/*  87 */     float[] arrayOfFloat = new float[4];
/*  88 */     getTextureCoordinates(0, arrayOfFloat, localRectangle3.x, localRectangle3.y, k, m, localRectangle1, localBaseTransform);
/*     */ 
/*  96 */     int i1 = localRectangle1.width;
/*  97 */     int i2 = localRectangle1.height;
/*     */ 
/*  99 */     HeapImage localHeapImage2 = (HeapImage)getRenderer().getCompatibleImage(i1, i2);
/* 100 */     setDestNativeBounds(localHeapImage2.getPhysicalWidth(), localHeapImage2.getPhysicalHeight());
/* 101 */     int i3 = localHeapImage2.getScanlineStride();
/* 102 */     int[] arrayOfInt2 = localHeapImage2.getPixelArray();
/*     */ 
/* 107 */     float f5 = getLevel();
/*     */ 
/* 110 */     float f6 = (arrayOfFloat[2] - arrayOfFloat[0]) / i1;
/* 111 */     float f7 = (arrayOfFloat[3] - arrayOfFloat[1]) / i2;
/*     */ 
/* 114 */     float f8 = arrayOfFloat[1] + f7 * 0.5F;
/*     */ 
/* 116 */     for (int i5 = 0; i5 < 0 + i2; i5++) {
/* 117 */       float f9 = i5;
/*     */ 
/* 119 */       int i4 = i5 * i3;
/*     */ 
/* 121 */       float f10 = arrayOfFloat[0] + f6 * 0.5F;
/*     */ 
/* 123 */       for (int i6 = 0; i6 < 0 + i1; i6++) {
/* 124 */         float f11 = i6;
/*     */ 
/* 128 */         float f12 = 0.3F;
/* 129 */         float f13 = 0.59F;
/* 130 */         float f14 = 0.11F;
/* 131 */         float f15 = 1.6F;
/* 132 */         float f16 = 1.2F;
/* 133 */         float f17 = 0.9F;
/*     */ 
/* 136 */         float f22 = f10;
/* 137 */         float f23 = f8;
/*     */         int i7;
/* 139 */         if ((f22 >= 0.0F) && (f23 >= 0.0F)) {
/* 140 */           int i8 = (int)(f22 * k);
/* 141 */           int i9 = (int)(f23 * m);
/* 142 */           int i10 = (i8 >= k) || (i9 >= m) ? 1 : 0;
/*     */ 
/* 145 */           i7 = i10 != 0 ? 0 : arrayOfInt1[(i9 * n + i8)];
/*     */         }
/*     */         else {
/* 148 */           i7 = 0;
/*     */         }
/* 150 */         float f18 = (i7 >> 16 & 0xFF) / 255.0F;
/* 151 */         float f19 = (i7 >> 8 & 0xFF) / 255.0F;
/* 152 */         float f20 = (i7 & 0xFF) / 255.0F;
/* 153 */         float f21 = (i7 >>> 24) / 255.0F;
/*     */ 
/* 156 */         f22 = f18;
/* 157 */         f23 = f19;
/* 158 */         float f24 = f20;
/* 159 */         float f25 = f21;
/*     */ 
/* 162 */         float f27 = f22;
/* 163 */         float f28 = f23;
/* 164 */         float f29 = f24;
/* 165 */         float f30 = f12;
/* 166 */         float f31 = f13;
/* 167 */         float f32 = f14;
/* 168 */         float f26 = f27 * f30 + f28 * f31 + f29 * f32;
/*     */ 
/* 173 */         f27 = f26;
/* 174 */         f28 = f27;
/* 175 */         f29 = f27;
/* 176 */         f30 = f27;
/* 177 */         f31 = f28 * f15;
/* 178 */         f32 = f29 * f16;
/* 179 */         float f33 = f30 * f17;
/*     */ 
/* 182 */         float f37 = f31;
/* 183 */         float f38 = f32;
/* 184 */         float f39 = f33;
/* 185 */         float f40 = f22;
/* 186 */         float f41 = f23;
/* 187 */         float f42 = f24;
/* 188 */         float f43 = 1.0F - f5;
/* 189 */         float f34 = f37 * (1.0F - f43) + f40 * f43;
/* 190 */         float f35 = f38 * (1.0F - f43) + f41 * f43;
/* 191 */         float f36 = f39 * (1.0F - f43) + f42 * f43;
/*     */ 
/* 194 */         float f1 = f34;
/* 195 */         float f2 = f35;
/* 196 */         float f3 = f36;
/* 197 */         float f4 = f25;
/*     */ 
/* 201 */         if (f4 < 0.0F) f4 = 0.0F; else if (f4 > 1.0F) f4 = 1.0F;
/* 202 */         if (f1 < 0.0F) f1 = 0.0F; else if (f1 > f4) f1 = f4;
/* 203 */         if (f2 < 0.0F) f2 = 0.0F; else if (f2 > f4) f2 = f4;
/* 204 */         if (f3 < 0.0F) f3 = 0.0F; else if (f3 > f4) f3 = f4;
/* 205 */         arrayOfInt2[(i4 + i6)] = ((int)(f1 * 255.0F) << 16 | (int)(f2 * 255.0F) << 8 | (int)(f3 * 255.0F) << 0 | (int)(f4 * 255.0F) << 24);
/*     */ 
/* 211 */         f10 += f6;
/*     */       }
/*     */ 
/* 215 */       f8 += f7;
/*     */     }
/*     */ 
/* 219 */     paramArrayOfImageData[0].releaseTransformedImage(localHeapImage1);
/*     */ 
/* 222 */     return new ImageData(getFilterContext(), localHeapImage2, localRectangle1);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.sw.java.JSWSepiaTonePeer
 * JD-Core Version:    0.6.2
 */