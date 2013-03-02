/*     */ package com.sun.scenario.effect.impl.sw.java;
/*     */ 
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.scenario.effect.Brightpass;
/*     */ import com.sun.scenario.effect.Effect;
/*     */ import com.sun.scenario.effect.FilterContext;
/*     */ import com.sun.scenario.effect.ImageData;
/*     */ import com.sun.scenario.effect.impl.HeapImage;
/*     */ import com.sun.scenario.effect.impl.Renderer;
/*     */ 
/*     */ public class JSWBrightpassPeer extends JSWEffectPeer
/*     */ {
/*     */   public JSWBrightpassPeer(FilterContext paramFilterContext, Renderer paramRenderer, String paramString)
/*     */   {
/*  47 */     super(paramFilterContext, paramRenderer, paramString);
/*     */   }
/*     */ 
/*     */   protected final Brightpass getEffect()
/*     */   {
/*  52 */     return (Brightpass)super.getEffect();
/*     */   }
/*     */ 
/*     */   private float getThreshold()
/*     */   {
/*  57 */     return getEffect().getThreshold();
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
/* 107 */     float f5 = getThreshold();
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
/* 128 */         float f12 = 0.2125F;
/* 129 */         float f13 = 0.7154F;
/* 130 */         float f14 = 0.0721F;
/*     */ 
/* 133 */         float f19 = f10;
/* 134 */         float f20 = f8;
/*     */         int i7;
/* 136 */         if ((f19 >= 0.0F) && (f20 >= 0.0F)) {
/* 137 */           int i8 = (int)(f19 * k);
/* 138 */           int i9 = (int)(f20 * m);
/* 139 */           int i10 = (i8 >= k) || (i9 >= m) ? 1 : 0;
/*     */ 
/* 142 */           i7 = i10 != 0 ? 0 : arrayOfInt1[(i9 * n + i8)];
/*     */         }
/*     */         else {
/* 145 */           i7 = 0;
/*     */         }
/* 147 */         float f15 = (i7 >> 16 & 0xFF) / 255.0F;
/* 148 */         float f16 = (i7 >> 8 & 0xFF) / 255.0F;
/* 149 */         float f17 = (i7 & 0xFF) / 255.0F;
/* 150 */         float f18 = (i7 >>> 24) / 255.0F;
/*     */ 
/* 153 */         f19 = f15;
/* 154 */         f20 = f16;
/* 155 */         float f21 = f17;
/* 156 */         float f22 = f18;
/*     */ 
/* 159 */         float f24 = f12;
/* 160 */         float f25 = f13;
/* 161 */         float f26 = f14;
/* 162 */         float f27 = f19;
/* 163 */         float f28 = f20;
/* 164 */         float f29 = f21;
/* 165 */         float f23 = f24 * f27 + f25 * f28 + f26 * f29;
/*     */ 
/* 170 */         f24 = f23;
/*     */ 
/* 173 */         f26 = 0.0F;
/* 174 */         f27 = f24 - f22 * f5;
/* 175 */         f25 = f26 > f27 ? f26 : f27;
/*     */ 
/* 178 */         f24 = f25;
/*     */ 
/* 181 */         f27 = f24;
/* 182 */         f26 = Math.signum(f27);
/*     */ 
/* 185 */         float f1 = f19 * f26;
/* 186 */         float f2 = f20 * f26;
/* 187 */         float f3 = f21 * f26;
/* 188 */         float f4 = f22 * f26;
/*     */ 
/* 192 */         if (f4 < 0.0F) f4 = 0.0F; else if (f4 > 1.0F) f4 = 1.0F;
/* 193 */         if (f1 < 0.0F) f1 = 0.0F; else if (f1 > f4) f1 = f4;
/* 194 */         if (f2 < 0.0F) f2 = 0.0F; else if (f2 > f4) f2 = f4;
/* 195 */         if (f3 < 0.0F) f3 = 0.0F; else if (f3 > f4) f3 = f4;
/* 196 */         arrayOfInt2[(i4 + i6)] = ((int)(f1 * 255.0F) << 16 | (int)(f2 * 255.0F) << 8 | (int)(f3 * 255.0F) << 0 | (int)(f4 * 255.0F) << 24);
/*     */ 
/* 202 */         f10 += f6;
/*     */       }
/*     */ 
/* 206 */       f8 += f7;
/*     */     }
/*     */ 
/* 210 */     paramArrayOfImageData[0].releaseTransformedImage(localHeapImage1);
/*     */ 
/* 213 */     return new ImageData(getFilterContext(), localHeapImage2, localRectangle1);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.sw.java.JSWBrightpassPeer
 * JD-Core Version:    0.6.2
 */