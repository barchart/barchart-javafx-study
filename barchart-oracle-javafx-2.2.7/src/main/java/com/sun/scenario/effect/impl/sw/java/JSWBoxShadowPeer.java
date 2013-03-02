/*     */ package com.sun.scenario.effect.impl.sw.java;
/*     */ 
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.scenario.effect.BoxShadow;
/*     */ import com.sun.scenario.effect.Color4f;
/*     */ import com.sun.scenario.effect.Effect;
/*     */ import com.sun.scenario.effect.FilterContext;
/*     */ import com.sun.scenario.effect.Filterable;
/*     */ import com.sun.scenario.effect.ImageData;
/*     */ import com.sun.scenario.effect.impl.HeapImage;
/*     */ import com.sun.scenario.effect.impl.Renderer;
/*     */ 
/*     */ public class JSWBoxShadowPeer extends JSWEffectPeer
/*     */ {
/*     */   public JSWBoxShadowPeer(FilterContext paramFilterContext, Renderer paramRenderer, String paramString)
/*     */   {
/*  43 */     super(paramFilterContext, paramRenderer, paramString);
/*     */   }
/*     */ 
/*     */   protected final BoxShadow getEffect()
/*     */   {
/*  48 */     return (BoxShadow)super.getEffect();
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
/*  65 */     if (j < 0) j = 0;
/*  66 */     if (k < 0) k = 0;
/*  67 */     int m = getEffect().getPasses();
/*  68 */     float f = getEffect().getSpread();
/*  69 */     if ((i != 0) && ((m < 1) || ((j < 1) && (k < 1)))) {
/*  70 */       paramArrayOfImageData[0].addref();
/*  71 */       return paramArrayOfImageData[0];
/*     */     }
/*     */ 
/*  75 */     int n = j * m + 1 & 0xFFFFFFFE;
/*  76 */     int i1 = k * m + 1 & 0xFFFFFFFE;
/*     */ 
/*  80 */     HeapImage localHeapImage1 = (HeapImage)paramArrayOfImageData[0].getUntransformedImage();
/*  81 */     Rectangle localRectangle2 = paramArrayOfImageData[0].getUntransformedBounds();
/*     */ 
/*  83 */     Object localObject1 = localHeapImage1;
/*  84 */     int i2 = localRectangle2.width;
/*  85 */     int i3 = localRectangle2.height;
/*  86 */     int i4 = ((HeapImage)localObject1).getScanlineStride();
/*  87 */     Object localObject2 = ((HeapImage)localObject1).getPixelArray();
/*     */ 
/*  89 */     int i5 = i2 + n;
/*  90 */     int i6 = i3 + i1;
/*  91 */     int i7 = i == 0 ? 1 : 0;
/*  92 */     while ((i7 != 0) || (i2 < i5) || (i3 < i6)) {
/*  93 */       int i8 = i2 + j;
/*  94 */       int i9 = i3 + k;
/*  95 */       if (i8 > i5) i8 = i5;
/*  96 */       if (i9 > i6) i9 = i6;
/*  97 */       HeapImage localHeapImage2 = (HeapImage)getRenderer().getCompatibleImage(i8, i9);
/*  98 */       int i10 = localHeapImage2.getScanlineStride();
/*  99 */       int[] arrayOfInt = localHeapImage2.getPixelArray();
/* 100 */       if (m == 0)
/*     */       {
/* 102 */         f = 0.0F;
/*     */       }
/* 104 */       if (i != 0) {
/* 105 */         filterHorizontalBlack(arrayOfInt, i8, i9, i10, (int[])localObject2, i2, i3, i4, f);
/*     */       }
/* 108 */       else if ((i8 < i5) || (i9 < i6))
/*     */       {
/* 110 */         filterVerticalBlack(arrayOfInt, i8, i9, i10, (int[])localObject2, i2, i3, i4, f);
/*     */       }
/*     */       else
/*     */       {
/* 114 */         float[] arrayOfFloat = getEffect().getColor().getPremultipliedRGBComponents();
/*     */ 
/* 116 */         if ((arrayOfFloat[3] == 1.0F) && (arrayOfFloat[0] == 0.0F) && (arrayOfFloat[1] == 0.0F) && (arrayOfFloat[2] == 0.0F))
/*     */         {
/* 121 */           filterVerticalBlack(arrayOfInt, i8, i9, i10, (int[])localObject2, i2, i3, i4, f);
/*     */         }
/*     */         else
/*     */         {
/* 125 */           filterVertical(arrayOfInt, i8, i9, i10, (int[])localObject2, i2, i3, i4, f, arrayOfFloat);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 130 */       if (localObject1 != localHeapImage1) {
/* 131 */         getRenderer().releaseCompatibleImage((Filterable)localObject1);
/*     */       }
/* 133 */       m--;
/* 134 */       i7 = 0;
/* 135 */       localObject1 = localHeapImage2;
/* 136 */       i2 = i8;
/* 137 */       i3 = i9;
/* 138 */       localObject2 = arrayOfInt;
/* 139 */       i4 = i10;
/*     */     }
/*     */ 
/* 142 */     Rectangle localRectangle3 = new Rectangle(localRectangle2.x - n / 2, localRectangle2.y - i1 / 2, i2, i3);
/*     */ 
/* 144 */     return new ImageData(getFilterContext(), (Filterable)localObject1, localRectangle3);
/*     */   }
/*     */ 
/*     */   protected void filterHorizontalBlack(int[] paramArrayOfInt1, int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt2, int paramInt4, int paramInt5, int paramInt6, float paramFloat)
/*     */   {
/* 151 */     int i = paramInt1 - paramInt4 + 1;
/*     */ 
/* 153 */     int j = i * 255;
/* 154 */     j = (int)(j + (255 - j) * paramFloat);
/* 155 */     int k = 2147483647 / j;
/* 156 */     int m = j / 255;
/* 157 */     int n = 0;
/* 158 */     int i1 = 0;
/* 159 */     for (int i2 = 0; i2 < paramInt2; i2++) {
/* 160 */       int i3 = 0;
/* 161 */       for (int i4 = 0; i4 < paramInt1; i4++)
/*     */       {
/* 164 */         int i5 = i4 >= i ? paramArrayOfInt2[(n + i4 - i)] : 0;
/* 165 */         i3 -= (i5 >>> 24);
/*     */ 
/* 167 */         i5 = i4 < paramInt4 ? paramArrayOfInt2[(n + i4)] : 0;
/* 168 */         i3 += (i5 >>> 24);
/*     */ 
/* 170 */         paramArrayOfInt1[(i1 + i4)] = (i3 >= j ? -16777216 : i3 < m ? 0 : i3 * k >> 23 << 24);
/*     */       }
/*     */ 
/* 175 */       n += paramInt6;
/* 176 */       i1 += paramInt3;
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void filterVerticalBlack(int[] paramArrayOfInt1, int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt2, int paramInt4, int paramInt5, int paramInt6, float paramFloat)
/*     */   {
/* 184 */     int i = paramInt2 - paramInt5 + 1;
/*     */ 
/* 186 */     int j = i * 255;
/* 187 */     j = (int)(j + (255 - j) * paramFloat);
/* 188 */     int k = 2147483647 / j;
/* 189 */     int m = j / 255;
/* 190 */     int n = i * paramInt6;
/* 191 */     for (int i1 = 0; i1 < paramInt1; i1++) {
/* 192 */       int i2 = 0;
/* 193 */       int i3 = i1;
/* 194 */       int i4 = i1;
/* 195 */       for (int i5 = 0; i5 < paramInt2; i5++)
/*     */       {
/* 198 */         int i6 = i3 >= n ? paramArrayOfInt2[(i3 - n)] : 0;
/* 199 */         i2 -= (i6 >>> 24);
/*     */ 
/* 201 */         i6 = i5 < paramInt5 ? paramArrayOfInt2[i3] : 0;
/* 202 */         i2 += (i6 >>> 24);
/*     */ 
/* 204 */         paramArrayOfInt1[i4] = (i2 >= j ? -16777216 : i2 < m ? 0 : i2 * k >> 23 << 24);
/*     */ 
/* 208 */         i3 += paramInt6;
/* 209 */         i4 += paramInt3;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void filterVertical(int[] paramArrayOfInt1, int paramInt1, int paramInt2, int paramInt3, int[] paramArrayOfInt2, int paramInt4, int paramInt5, int paramInt6, float paramFloat, float[] paramArrayOfFloat)
/*     */   {
/* 218 */     int i = paramInt2 - paramInt5 + 1;
/*     */ 
/* 220 */     int j = i * 255;
/* 221 */     j = (int)(j + (255 - j) * paramFloat);
/* 222 */     int k = 2147483647 / j;
/* 223 */     int m = (int)(k * paramArrayOfFloat[0]);
/* 224 */     int n = (int)(k * paramArrayOfFloat[1]);
/* 225 */     int i1 = (int)(k * paramArrayOfFloat[2]);
/* 226 */     k = (int)(k * paramArrayOfFloat[3]);
/* 227 */     int i2 = j / 255;
/* 228 */     int i3 = i * paramInt6;
/* 229 */     int i4 = (int)(paramArrayOfFloat[0] * 255.0F) << 16 | (int)(paramArrayOfFloat[1] * 255.0F) << 8 | (int)(paramArrayOfFloat[2] * 255.0F) | (int)(paramArrayOfFloat[3] * 255.0F) << 24;
/*     */ 
/* 234 */     for (int i5 = 0; i5 < paramInt1; i5++) {
/* 235 */       int i6 = 0;
/* 236 */       int i7 = i5;
/* 237 */       int i8 = i5;
/* 238 */       for (int i9 = 0; i9 < paramInt2; i9++)
/*     */       {
/* 241 */         int i10 = i7 >= i3 ? paramArrayOfInt2[(i7 - i3)] : 0;
/* 242 */         i6 -= (i10 >>> 24);
/*     */ 
/* 244 */         i10 = i9 < paramInt5 ? paramArrayOfInt2[i7] : 0;
/* 245 */         i6 += (i10 >>> 24);
/*     */ 
/* 247 */         paramArrayOfInt1[i8] = (i6 >= j ? i4 : i6 < i2 ? 0 : i6 * k >> 23 << 24 | i6 * m >> 23 << 16 | i6 * n >> 23 << 8 | i6 * i1 >> 23);
/*     */ 
/* 254 */         i7 += paramInt6;
/* 255 */         i8 += paramInt3;
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.sw.java.JSWBoxShadowPeer
 * JD-Core Version:    0.6.2
 */