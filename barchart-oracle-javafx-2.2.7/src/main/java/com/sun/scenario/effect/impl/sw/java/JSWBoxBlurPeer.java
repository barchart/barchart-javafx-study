/*     */ package com.sun.scenario.effect.impl.sw.java;
/*     */ 
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.scenario.effect.BoxBlur;
/*     */ import com.sun.scenario.effect.Effect;
/*     */ import com.sun.scenario.effect.FilterContext;
/*     */ import com.sun.scenario.effect.Filterable;
/*     */ import com.sun.scenario.effect.ImageData;
/*     */ import com.sun.scenario.effect.impl.HeapImage;
/*     */ import com.sun.scenario.effect.impl.Renderer;
/*     */ 
/*     */ public class JSWBoxBlurPeer extends JSWEffectPeer
/*     */ {
/*     */   public JSWBoxBlurPeer(FilterContext paramFilterContext, Renderer paramRenderer, String paramString)
/*     */   {
/*  43 */     super(paramFilterContext, paramRenderer, paramString);
/*     */   }
/*     */ 
/*     */   protected final BoxBlur getEffect()
/*     */   {
/*  48 */     return (BoxBlur)super.getEffect();
/*     */   }
/*     */ 
/*     */   public ImageData filter(Effect paramEffect, BaseTransform paramBaseTransform, Rectangle paramRectangle, ImageData[] paramArrayOfImageData)
/*     */   {
/*  58 */     setEffect(paramEffect);
/*  59 */     Rectangle localRectangle1 = getResultBounds(paramBaseTransform, paramRectangle, paramArrayOfImageData);
/*     */ 
/*  62 */     int i = getPass() == 0 ? 1 : 0;
/*  63 */     int j = i != 0 ? getEffect().getHorizontalSize() - 1 : 0;
/*  64 */     int k = i != 0 ? 0 : getEffect().getVerticalSize() - 1;
/*  65 */     int m = getEffect().getPasses();
/*  66 */     if ((m < 1) || ((j < 1) && (k < 1))) {
/*  67 */       paramArrayOfImageData[0].addref();
/*  68 */       return paramArrayOfImageData[0];
/*     */     }
/*     */ 
/*  72 */     int n = j * m + 1 & 0xFFFFFFFE;
/*  73 */     int i1 = k * m + 1 & 0xFFFFFFFE;
/*     */ 
/*  77 */     HeapImage localHeapImage1 = (HeapImage)paramArrayOfImageData[0].getUntransformedImage();
/*  78 */     Rectangle localRectangle2 = paramArrayOfImageData[0].getUntransformedBounds();
/*     */ 
/*  80 */     Object localObject1 = localHeapImage1;
/*  81 */     int i2 = localRectangle2.width;
/*  82 */     int i3 = localRectangle2.height;
/*  83 */     int i4 = ((HeapImage)localObject1).getScanlineStride();
/*  84 */     Object localObject2 = ((HeapImage)localObject1).getPixelArray();
/*     */ 
/*  86 */     int i5 = i2 + n;
/*  87 */     int i6 = i3 + i1;
/*  88 */     while ((i2 < i5) || (i3 < i6)) {
/*  89 */       int i7 = i2 + j;
/*  90 */       int i8 = i3 + k;
/*  91 */       if (i7 > i5) i7 = i5;
/*  92 */       if (i8 > i6) i8 = i6;
/*  93 */       HeapImage localHeapImage2 = (HeapImage)getRenderer().getCompatibleImage(i7, i8);
/*  94 */       int i9 = localHeapImage2.getScanlineStride();
/*  95 */       int[] arrayOfInt = localHeapImage2.getPixelArray();
/*  96 */       if (i != 0) {
/*  97 */         filterHorizontal(arrayOfInt, i7, i8, i9, (int[])localObject2, i2, i3, i4);
/*     */       }
/*     */       else {
/* 100 */         filterVertical(arrayOfInt, i7, i8, i9, (int[])localObject2, i2, i3, i4);
/*     */       }
/*     */ 
/* 103 */       if (localObject1 != localHeapImage1) {
/* 104 */         getRenderer().releaseCompatibleImage((Filterable)localObject1);
/*     */       }
/* 106 */       localObject1 = localHeapImage2;
/* 107 */       i2 = i7;
/* 108 */       i3 = i8;
/* 109 */       localObject2 = arrayOfInt;
/* 110 */       i4 = i9;
/*     */     }
/*     */ 
/* 113 */     Rectangle localRectangle3 = new Rectangle(localRectangle2.x - n / 2, localRectangle2.y - i1 / 2, i2, i3);
/*     */ 
/* 115 */     return new ImageData(getFilterContext(), (Filterable)localObject1, localRectangle3);
/*     */   }
/*     */ 
/*     */   protected void filterHorizontal(int[] paramArrayOfInt1, int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt2, int paramInt4, int paramInt5, int paramInt6)
/*     */   {
/* 121 */     int i = paramInt1 - paramInt4 + 1;
/* 122 */     int j = 2147483647 / (i * 255);
/* 123 */     int k = 0;
/* 124 */     int m = 0;
/* 125 */     for (int n = 0; n < paramInt2; n++) {
/* 126 */       int i1 = 0;
/* 127 */       int i2 = 0;
/* 128 */       int i3 = 0;
/* 129 */       int i4 = 0;
/* 130 */       for (int i5 = 0; i5 < paramInt1; i5++)
/*     */       {
/* 133 */         int i6 = i5 >= i ? paramArrayOfInt2[(k + i5 - i)] : 0;
/* 134 */         i1 -= (i6 >>> 24);
/* 135 */         i2 -= (i6 >> 16 & 0xFF);
/* 136 */         i3 -= (i6 >> 8 & 0xFF);
/* 137 */         i4 -= (i6 & 0xFF);
/*     */ 
/* 139 */         i6 = i5 < paramInt4 ? paramArrayOfInt2[(k + i5)] : 0;
/* 140 */         i1 += (i6 >>> 24);
/* 141 */         i2 += (i6 >> 16 & 0xFF);
/* 142 */         i3 += (i6 >> 8 & 0xFF);
/* 143 */         i4 += (i6 & 0xFF);
/* 144 */         paramArrayOfInt1[(m + i5)] = ((i1 * j >> 23 << 24) + (i2 * j >> 23 << 16) + (i3 * j >> 23 << 8) + (i4 * j >> 23));
/*     */       }
/*     */ 
/* 150 */       k += paramInt6;
/* 151 */       m += paramInt3;
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void filterVertical(int[] paramArrayOfInt1, int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt2, int paramInt4, int paramInt5, int paramInt6)
/*     */   {
/* 158 */     int i = paramInt2 - paramInt5 + 1;
/* 159 */     int j = 2147483647 / (i * 255);
/* 160 */     int k = i * paramInt6;
/* 161 */     for (int m = 0; m < paramInt1; m++) {
/* 162 */       int n = 0;
/* 163 */       int i1 = 0;
/* 164 */       int i2 = 0;
/* 165 */       int i3 = 0;
/* 166 */       int i4 = m;
/* 167 */       int i5 = m;
/* 168 */       for (int i6 = 0; i6 < paramInt2; i6++)
/*     */       {
/* 171 */         int i7 = i4 >= k ? paramArrayOfInt2[(i4 - k)] : 0;
/* 172 */         n -= (i7 >>> 24);
/* 173 */         i1 -= (i7 >> 16 & 0xFF);
/* 174 */         i2 -= (i7 >> 8 & 0xFF);
/* 175 */         i3 -= (i7 & 0xFF);
/*     */ 
/* 177 */         i7 = i6 < paramInt5 ? paramArrayOfInt2[i4] : 0;
/* 178 */         n += (i7 >>> 24);
/* 179 */         i1 += (i7 >> 16 & 0xFF);
/* 180 */         i2 += (i7 >> 8 & 0xFF);
/* 181 */         i3 += (i7 & 0xFF);
/* 182 */         paramArrayOfInt1[i5] = ((n * j >> 23 << 24) + (i1 * j >> 23 << 16) + (i2 * j >> 23 << 8) + (i3 * j >> 23));
/*     */ 
/* 187 */         i4 += paramInt6;
/* 188 */         i5 += paramInt3;
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.sw.java.JSWBoxBlurPeer
 * JD-Core Version:    0.6.2
 */