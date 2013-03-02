/*     */ package com.sun.scenario.effect.impl.sw.java;
/*     */ 
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.scenario.effect.ColorAdjust;
/*     */ import com.sun.scenario.effect.Effect;
/*     */ import com.sun.scenario.effect.FilterContext;
/*     */ import com.sun.scenario.effect.ImageData;
/*     */ import com.sun.scenario.effect.impl.HeapImage;
/*     */ import com.sun.scenario.effect.impl.Renderer;
/*     */ 
/*     */ public class JSWColorAdjustPeer extends JSWEffectPeer
/*     */ {
/*     */   public JSWColorAdjustPeer(FilterContext paramFilterContext, Renderer paramRenderer, String paramString)
/*     */   {
/*  47 */     super(paramFilterContext, paramRenderer, paramString);
/*     */   }
/*     */ 
/*     */   protected final ColorAdjust getEffect()
/*     */   {
/*  52 */     return (ColorAdjust)super.getEffect();
/*     */   }
/*     */ 
/*     */   private float getHue()
/*     */   {
/*  57 */     return getEffect().getHue() / 2.0F;
/*     */   }
/*     */ 
/*     */   private float getSaturation() {
/*  61 */     return getEffect().getSaturation() + 1.0F;
/*     */   }
/*     */ 
/*     */   private float getBrightness() {
/*  65 */     return getEffect().getBrightness() + 1.0F;
/*     */   }
/*     */ 
/*     */   private float getContrast() {
/*  69 */     float f = getEffect().getContrast();
/*  70 */     if (f > 0.0F) f *= 3.0F;
/*  71 */     return f + 1.0F;
/*     */   }
/*     */ 
/*     */   public ImageData filter(Effect paramEffect, BaseTransform paramBaseTransform, Rectangle paramRectangle, ImageData[] paramArrayOfImageData)
/*     */   {
/*  81 */     setEffect(paramEffect);
/*  82 */     Rectangle localRectangle1 = getResultBounds(paramBaseTransform, paramRectangle, paramArrayOfImageData);
/*  83 */     setDestBounds(localRectangle1);
/*     */ 
/*  86 */     HeapImage localHeapImage1 = (HeapImage)paramArrayOfImageData[0].getTransformedImage(localRectangle1);
/*  87 */     int i = 0;
/*  88 */     int j = 0;
/*  89 */     int k = localHeapImage1.getPhysicalWidth();
/*  90 */     int m = localHeapImage1.getPhysicalHeight();
/*  91 */     int n = localHeapImage1.getScanlineStride();
/*  92 */     int[] arrayOfInt1 = localHeapImage1.getPixelArray();
/*     */ 
/*  94 */     Rectangle localRectangle2 = new Rectangle(i, j, k, m);
/*  95 */     Rectangle localRectangle3 = paramArrayOfImageData[0].getTransformedBounds(localRectangle1);
/*  96 */     BaseTransform localBaseTransform = BaseTransform.IDENTITY_TRANSFORM;
/*  97 */     setInputBounds(0, localRectangle3);
/*  98 */     setInputNativeBounds(0, localRectangle2);
/*     */ 
/* 101 */     float[] arrayOfFloat = new float[4];
/* 102 */     getTextureCoordinates(0, arrayOfFloat, localRectangle3.x, localRectangle3.y, k, m, localRectangle1, localBaseTransform);
/*     */ 
/* 110 */     int i1 = localRectangle1.width;
/* 111 */     int i2 = localRectangle1.height;
/*     */ 
/* 113 */     HeapImage localHeapImage2 = (HeapImage)getRenderer().getCompatibleImage(i1, i2);
/* 114 */     setDestNativeBounds(localHeapImage2.getPhysicalWidth(), localHeapImage2.getPhysicalHeight());
/* 115 */     int i3 = localHeapImage2.getScanlineStride();
/* 116 */     int[] arrayOfInt2 = localHeapImage2.getPixelArray();
/*     */ 
/* 121 */     float f5 = getContrast();
/* 122 */     float f6 = getBrightness();
/* 123 */     float f7 = getSaturation();
/* 124 */     float f8 = getHue();
/*     */ 
/* 127 */     float f9 = (arrayOfFloat[2] - arrayOfFloat[0]) / i1;
/* 128 */     float f10 = (arrayOfFloat[3] - arrayOfFloat[1]) / i2;
/*     */ 
/* 131 */     float f11 = arrayOfFloat[1] + f10 * 0.5F;
/*     */ 
/* 133 */     for (int i5 = 0; i5 < 0 + i2; i5++) {
/* 134 */       float f12 = i5;
/*     */ 
/* 136 */       int i4 = i5 * i3;
/*     */ 
/* 138 */       float f13 = arrayOfFloat[0] + f9 * 0.5F;
/*     */ 
/* 140 */       for (int i6 = 0; i6 < 0 + i1; i6++) {
/* 141 */         float f14 = i6;
/*     */ 
/* 147 */         float f19 = f13;
/* 148 */         float f20 = f11;
/*     */         int i7;
/* 150 */         if ((f19 >= 0.0F) && (f20 >= 0.0F)) {
/* 151 */           int i8 = (int)(f19 * k);
/* 152 */           int i9 = (int)(f20 * m);
/* 153 */           int i10 = (i8 >= k) || (i9 >= m) ? 1 : 0;
/*     */ 
/* 156 */           i7 = i10 != 0 ? 0 : arrayOfInt1[(i9 * n + i8)];
/*     */         }
/*     */         else {
/* 159 */           i7 = 0;
/*     */         }
/* 161 */         float f15 = (i7 >> 16 & 0xFF) / 255.0F;
/* 162 */         float f16 = (i7 >> 8 & 0xFF) / 255.0F;
/* 163 */         float f17 = (i7 & 0xFF) / 255.0F;
/* 164 */         float f18 = (i7 >>> 24) / 255.0F;
/*     */ 
/* 167 */         f19 = f15;
/* 168 */         f20 = f16;
/* 169 */         float f21 = f17;
/* 170 */         float f22 = f18;
/* 171 */         if (f22 > 0.0F) {
/* 172 */           f19 /= f22;
/* 173 */           f20 /= f22;
/* 174 */           f21 /= f22;
/*     */         }
/* 176 */         f19 = (f19 - 0.5F) * f5 + 0.5F;
/* 177 */         f20 = (f20 - 0.5F) * f5 + 0.5F;
/* 178 */         f21 = (f21 - 0.5F) * f5 + 0.5F;
/*     */ 
/* 181 */         float f26 = f19;
/* 182 */         float f27 = f20;
/* 183 */         float f28 = f21;
/*     */ 
/* 190 */         float f33 = f26;
/* 191 */         float f34 = f27;
/* 192 */         float f32 = f33 > f34 ? f33 : f34;
/*     */ 
/* 195 */         f33 = f32;
/*     */ 
/* 197 */         f34 = f33;
/* 198 */         float f35 = f28;
/* 199 */         f32 = f34 > f35 ? f34 : f35;
/*     */ 
/* 202 */         f33 = f32;
/*     */ 
/* 205 */         f35 = f26;
/* 206 */         float f36 = f27;
/* 207 */         f34 = f35 < f36 ? f35 : f36;
/*     */ 
/* 210 */         f35 = f34;
/*     */ 
/* 212 */         f36 = f35;
/* 213 */         float f37 = f28;
/* 214 */         f34 = f36 < f37 ? f36 : f37;
/*     */ 
/* 217 */         f35 = f34;
/* 218 */         if (f33 > f35) {
/* 219 */           f36 = (f33 - f26) / (f33 - f35);
/* 220 */           f37 = (f33 - f27) / (f33 - f35);
/* 221 */           f38 = (f33 - f28) / (f33 - f35);
/* 222 */           if (f26 == f33) {
/* 223 */             f29 = f38 - f37;
/*     */           }
/* 225 */           else if (f27 == f33) {
/* 226 */             f29 = 2.0F + f36 - f38;
/*     */           }
/*     */           else {
/* 229 */             f29 = 4.0F + f37 - f36;
/*     */           }
/* 231 */           f29 /= 6.0F;
/* 232 */           if (f29 < 0.0F) {
/* 233 */             f29 += 1.0F;
/*     */           }
/* 235 */           f30 = (f33 - f35) / f33;
/*     */         }
/*     */         else {
/* 238 */           f29 = 0.0F;
/* 239 */           f30 = 0.0F;
/*     */         }
/* 241 */         float f31 = f33;
/* 242 */         float f23 = f29;
/* 243 */         float f24 = f30;
/* 244 */         float f25 = f31;
/*     */ 
/* 248 */         f26 = f23;
/* 249 */         f27 = f24;
/* 250 */         f28 = f25;
/* 251 */         f26 += f8;
/* 252 */         if (f26 < 0.0F) {
/* 253 */           f26 += 1.0F;
/*     */         }
/* 255 */         else if (f26 > 1.0F) {
/* 256 */           f26 -= 1.0F;
/*     */         }
/* 258 */         if (f7 > 1.0F) {
/* 259 */           f29 = f7 - 1.0F;
/* 260 */           f27 += (1.0F - f27) * f29;
/*     */         }
/*     */         else {
/* 263 */           f27 *= f7;
/*     */         }
/* 265 */         if (f6 > 1.0F) {
/* 266 */           f29 = f6 - 1.0F;
/* 267 */           f27 *= (1.0F - f29);
/* 268 */           f28 += (1.0F - f28) * f29;
/*     */         }
/*     */         else {
/* 271 */           f28 *= f6;
/*     */         }
/*     */ 
/* 275 */         f31 = f27;
/* 276 */         f32 = f28;
/* 277 */         f33 = 0.0F;
/* 278 */         f34 = 1.0F;
/* 279 */         float f29 = f31 > f34 ? f34 : f31 < f33 ? f33 : f31;
/*     */ 
/* 281 */         float f30 = f32 > f34 ? f34 : f32 < f33 ? f33 : f32;
/*     */ 
/* 285 */         f27 = f29;
/* 286 */         f28 = f30;
/*     */ 
/* 289 */         f34 = f26;
/* 290 */         f35 = f27;
/* 291 */         f36 = f28;
/*     */ 
/* 293 */         f37 = 0.0F;
/* 294 */         float f38 = 0.0F;
/* 295 */         float f39 = 0.0F;
/* 296 */         float f40 = f34;
/* 297 */         float f41 = f35;
/* 298 */         float f42 = f36;
/*     */ 
/* 301 */         float f44 = f40;
/* 302 */         float f43 = (float)Math.floor(f44);
/*     */ 
/* 305 */         f40 = (f40 - f43) * 6.0F;
/*     */ 
/* 307 */         f44 = f40;
/* 308 */         f43 = (float)Math.floor(f44);
/*     */ 
/* 311 */         f44 = f40 - f43;
/* 312 */         float f45 = f42 * (1.0F - f41);
/* 313 */         float f46 = f42 * (1.0F - f41 * f44);
/* 314 */         float f47 = f42 * (1.0F - f41 * (1.0F - f44));
/*     */ 
/* 316 */         float f48 = f40;
/* 317 */         f43 = (float)Math.floor(f48);
/*     */ 
/* 320 */         f40 = f43;
/* 321 */         if (f40 < 1.0F) {
/* 322 */           f37 = f42;
/* 323 */           f38 = f47;
/* 324 */           f39 = f45;
/*     */         }
/* 326 */         else if (f40 < 2.0F) {
/* 327 */           f37 = f46;
/* 328 */           f38 = f42;
/* 329 */           f39 = f45;
/*     */         }
/* 331 */         else if (f40 < 3.0F) {
/* 332 */           f37 = f45;
/* 333 */           f38 = f42;
/* 334 */           f39 = f47;
/*     */         }
/* 336 */         else if (f40 < 4.0F) {
/* 337 */           f37 = f45;
/* 338 */           f38 = f46;
/* 339 */           f39 = f42;
/*     */         }
/* 341 */         else if (f40 < 5.0F) {
/* 342 */           f37 = f47;
/* 343 */           f38 = f45;
/* 344 */           f39 = f42;
/*     */         }
/*     */         else {
/* 347 */           f37 = f42;
/* 348 */           f38 = f45;
/* 349 */           f39 = f46;
/*     */         }
/* 351 */         f31 = f37;
/* 352 */         f32 = f38;
/* 353 */         f33 = f39;
/*     */ 
/* 357 */         float f1 = f22 * f31;
/* 358 */         float f2 = f22 * f32;
/* 359 */         float f3 = f22 * f33;
/* 360 */         float f4 = f22;
/*     */ 
/* 364 */         if (f4 < 0.0F) f4 = 0.0F; else if (f4 > 1.0F) f4 = 1.0F;
/* 365 */         if (f1 < 0.0F) f1 = 0.0F; else if (f1 > f4) f1 = f4;
/* 366 */         if (f2 < 0.0F) f2 = 0.0F; else if (f2 > f4) f2 = f4;
/* 367 */         if (f3 < 0.0F) f3 = 0.0F; else if (f3 > f4) f3 = f4;
/* 368 */         arrayOfInt2[(i4 + i6)] = ((int)(f1 * 255.0F) << 16 | (int)(f2 * 255.0F) << 8 | (int)(f3 * 255.0F) << 0 | (int)(f4 * 255.0F) << 24);
/*     */ 
/* 374 */         f13 += f9;
/*     */       }
/*     */ 
/* 378 */       f11 += f10;
/*     */     }
/*     */ 
/* 382 */     paramArrayOfImageData[0].releaseTransformedImage(localHeapImage1);
/*     */ 
/* 385 */     return new ImageData(getFilterContext(), localHeapImage2, localRectangle1);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.sw.java.JSWColorAdjustPeer
 * JD-Core Version:    0.6.2
 */