/*     */ package com.sun.scenario.effect.impl.sw.java;
/*     */ 
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.scenario.effect.Effect;
/*     */ import com.sun.scenario.effect.FilterContext;
/*     */ import com.sun.scenario.effect.ImageData;
/*     */ import com.sun.scenario.effect.InvertMask;
/*     */ import com.sun.scenario.effect.impl.HeapImage;
/*     */ import com.sun.scenario.effect.impl.Renderer;
/*     */ 
/*     */ public class JSWInvertMaskPeer extends JSWEffectPeer
/*     */ {
/*     */   public JSWInvertMaskPeer(FilterContext paramFilterContext, Renderer paramRenderer, String paramString)
/*     */   {
/*  47 */     super(paramFilterContext, paramRenderer, paramString);
/*     */   }
/*     */ 
/*     */   protected final InvertMask getEffect()
/*     */   {
/*  52 */     return (InvertMask)super.getEffect();
/*     */   }
/*     */ 
/*     */   private float[] getOffset()
/*     */   {
/*  57 */     float f1 = getEffect().getOffsetX();
/*  58 */     float f2 = getEffect().getOffsetY();
/*  59 */     return new float[] { f1 / getInputNativeBounds(0).width, f2 / getInputNativeBounds(0).height };
/*     */   }
/*     */ 
/*     */   public ImageData filter(Effect paramEffect, BaseTransform paramBaseTransform, Rectangle paramRectangle, ImageData[] paramArrayOfImageData)
/*     */   {
/*  72 */     setEffect(paramEffect);
/*  73 */     Rectangle localRectangle1 = getResultBounds(paramBaseTransform, paramRectangle, paramArrayOfImageData);
/*  74 */     setDestBounds(localRectangle1);
/*     */ 
/*  77 */     HeapImage localHeapImage1 = (HeapImage)paramArrayOfImageData[0].getTransformedImage(localRectangle1);
/*  78 */     int i = 0;
/*  79 */     int j = 0;
/*  80 */     int k = localHeapImage1.getPhysicalWidth();
/*  81 */     int m = localHeapImage1.getPhysicalHeight();
/*  82 */     int n = localHeapImage1.getScanlineStride();
/*  83 */     int[] arrayOfInt1 = localHeapImage1.getPixelArray();
/*     */ 
/*  85 */     Rectangle localRectangle2 = new Rectangle(i, j, k, m);
/*  86 */     Rectangle localRectangle3 = paramArrayOfImageData[0].getTransformedBounds(localRectangle1);
/*  87 */     BaseTransform localBaseTransform = BaseTransform.IDENTITY_TRANSFORM;
/*  88 */     setInputBounds(0, localRectangle3);
/*  89 */     setInputNativeBounds(0, localRectangle2);
/*     */ 
/*  92 */     float[] arrayOfFloat1 = new float[4];
/*  93 */     getTextureCoordinates(0, arrayOfFloat1, localRectangle3.x, localRectangle3.y, k, m, localRectangle1, localBaseTransform);
/*     */ 
/* 101 */     int i1 = localRectangle1.width;
/* 102 */     int i2 = localRectangle1.height;
/*     */ 
/* 104 */     HeapImage localHeapImage2 = (HeapImage)getRenderer().getCompatibleImage(i1, i2);
/* 105 */     setDestNativeBounds(localHeapImage2.getPhysicalWidth(), localHeapImage2.getPhysicalHeight());
/* 106 */     int i3 = localHeapImage2.getScanlineStride();
/* 107 */     int[] arrayOfInt2 = localHeapImage2.getPixelArray();
/*     */ 
/* 112 */     float[] arrayOfFloat2 = getOffset();
/* 113 */     float f5 = arrayOfFloat2[0]; float f6 = arrayOfFloat2[1];
/*     */ 
/* 116 */     float f7 = (arrayOfFloat1[2] - arrayOfFloat1[0]) / i1;
/* 117 */     float f8 = (arrayOfFloat1[3] - arrayOfFloat1[1]) / i2;
/*     */ 
/* 120 */     float f9 = arrayOfFloat1[1] + f8 * 0.5F;
/*     */ 
/* 122 */     for (int i5 = 0; i5 < 0 + i2; i5++) {
/* 123 */       float f10 = i5;
/*     */ 
/* 125 */       int i4 = i5 * i3;
/*     */ 
/* 127 */       float f11 = arrayOfFloat1[0] + f7 * 0.5F;
/*     */ 
/* 129 */       for (int i6 = 0; i6 < 0 + i1; i6++) {
/* 130 */         float f12 = i6;
/*     */ 
/* 136 */         float f14 = f11 - f5;
/* 137 */         float f15 = f9 - f6;
/*     */         int i7;
/* 139 */         if ((f14 >= 0.0F) && (f15 >= 0.0F)) {
/* 140 */           int i8 = (int)(f14 * k);
/* 141 */           int i9 = (int)(f15 * m);
/* 142 */           int i10 = (i8 >= k) || (i9 >= m) ? 1 : 0;
/*     */ 
/* 145 */           i7 = i10 != 0 ? 0 : arrayOfInt1[(i9 * n + i8)];
/*     */         }
/*     */         else {
/* 148 */           i7 = 0;
/*     */         }
/* 150 */         float f13 = (i7 >>> 24) / 255.0F;
/*     */ 
/* 153 */         f14 = f13;
/* 154 */         f15 = 1.0F - f14;
/* 155 */         float f1 = f15;
/* 156 */         float f2 = f15;
/* 157 */         float f3 = f15;
/* 158 */         float f4 = f15;
/*     */ 
/* 162 */         if (f4 < 0.0F) f4 = 0.0F; else if (f4 > 1.0F) f4 = 1.0F;
/* 163 */         if (f1 < 0.0F) f1 = 0.0F; else if (f1 > f4) f1 = f4;
/* 164 */         if (f2 < 0.0F) f2 = 0.0F; else if (f2 > f4) f2 = f4;
/* 165 */         if (f3 < 0.0F) f3 = 0.0F; else if (f3 > f4) f3 = f4;
/* 166 */         arrayOfInt2[(i4 + i6)] = ((int)(f1 * 255.0F) << 16 | (int)(f2 * 255.0F) << 8 | (int)(f3 * 255.0F) << 0 | (int)(f4 * 255.0F) << 24);
/*     */ 
/* 172 */         f11 += f7;
/*     */       }
/*     */ 
/* 176 */       f9 += f8;
/*     */     }
/*     */ 
/* 180 */     paramArrayOfImageData[0].releaseTransformedImage(localHeapImage1);
/*     */ 
/* 183 */     return new ImageData(getFilterContext(), localHeapImage2, localRectangle1);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.sw.java.JSWInvertMaskPeer
 * JD-Core Version:    0.6.2
 */