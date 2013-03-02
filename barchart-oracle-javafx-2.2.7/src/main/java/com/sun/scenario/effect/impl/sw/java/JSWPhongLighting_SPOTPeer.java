/*     */ package com.sun.scenario.effect.impl.sw.java;
/*     */ 
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.scenario.effect.Color4f;
/*     */ import com.sun.scenario.effect.Effect;
/*     */ import com.sun.scenario.effect.FilterContext;
/*     */ import com.sun.scenario.effect.ImageData;
/*     */ import com.sun.scenario.effect.PhongLighting;
/*     */ import com.sun.scenario.effect.impl.BufferUtil;
/*     */ import com.sun.scenario.effect.impl.HeapImage;
/*     */ import com.sun.scenario.effect.impl.Renderer;
/*     */ import com.sun.scenario.effect.light.Light;
/*     */ import com.sun.scenario.effect.light.PointLight;
/*     */ import com.sun.scenario.effect.light.SpotLight;
/*     */ import java.nio.FloatBuffer;
/*     */ 
/*     */ public class JSWPhongLighting_SPOTPeer extends JSWEffectPeer
/*     */ {
/*     */   private FloatBuffer kvals;
/*     */ 
/*     */   public JSWPhongLighting_SPOTPeer(FilterContext paramFilterContext, Renderer paramRenderer, String paramString)
/*     */   {
/*  47 */     super(paramFilterContext, paramRenderer, paramString);
/*     */   }
/*     */ 
/*     */   protected final PhongLighting getEffect()
/*     */   {
/*  52 */     return (PhongLighting)super.getEffect();
/*     */   }
/*     */ 
/*     */   private float getSurfaceScale()
/*     */   {
/*  59 */     return getEffect().getSurfaceScale();
/*     */   }
/*     */ 
/*     */   private float getDiffuseConstant() {
/*  63 */     return getEffect().getDiffuseConstant();
/*     */   }
/*     */ 
/*     */   private float getSpecularConstant() {
/*  67 */     return getEffect().getSpecularConstant();
/*     */   }
/*     */ 
/*     */   private float getSpecularExponent() {
/*  71 */     return getEffect().getSpecularExponent();
/*     */   }
/*     */ 
/*     */   private float[] getNormalizedLightPosition() {
/*  75 */     return getEffect().getLight().getNormalizedLightPosition();
/*     */   }
/*     */ 
/*     */   private float[] getLightPosition() {
/*  79 */     PointLight localPointLight = (PointLight)getEffect().getLight();
/*     */ 
/*  81 */     return new float[] { localPointLight.getX(), localPointLight.getY(), localPointLight.getZ() };
/*     */   }
/*     */ 
/*     */   private float[] getLightColor() {
/*  85 */     return getEffect().getLight().getColor().getPremultipliedRGBComponents();
/*     */   }
/*     */ 
/*     */   private float getLightSpecularExponent() {
/*  89 */     return ((SpotLight)getEffect().getLight()).getSpecularExponent();
/*     */   }
/*     */ 
/*     */   private float[] getNormalizedLightDirection() {
/*  93 */     return ((SpotLight)getEffect().getLight()).getNormalizedLightDirection();
/*     */   }
/*     */ 
/*     */   private FloatBuffer getKvals() {
/*  97 */     Rectangle localRectangle = getInputNativeBounds(0);
/*  98 */     float f1 = 1.0F / localRectangle.width;
/*  99 */     float f2 = 1.0F / localRectangle.height;
/*     */ 
/* 101 */     float[] arrayOfFloat1 = { -1.0F, 0.0F, 1.0F, -2.0F, 0.0F, 2.0F, -1.0F, 0.0F, 1.0F };
/*     */ 
/* 106 */     float[] arrayOfFloat2 = { -1.0F, -2.0F, -1.0F, 0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F };
/*     */ 
/* 111 */     if (this.kvals == null) {
/* 112 */       this.kvals = BufferUtil.newFloatBuffer(32);
/*     */     }
/* 114 */     this.kvals.clear();
/* 115 */     int i = 0;
/* 116 */     float f3 = -getSurfaceScale() * 0.25F;
/* 117 */     for (int j = -1; j <= 1; j++) {
/* 118 */       for (int k = -1; k <= 1; k++) {
/* 119 */         if ((j != 0) || (k != 0)) {
/* 120 */           this.kvals.put(k * f1);
/* 121 */           this.kvals.put(j * f2);
/* 122 */           this.kvals.put(arrayOfFloat1[i] * f3);
/* 123 */           this.kvals.put(arrayOfFloat2[i] * f3);
/*     */         }
/* 125 */         i++;
/*     */       }
/*     */     }
/* 128 */     this.kvals.rewind();
/* 129 */     return this.kvals;
/*     */   }
/*     */ 
/*     */   private int getKvalsArrayLength() {
/* 133 */     return 8;
/*     */   }
/*     */ 
/*     */   public ImageData filter(Effect paramEffect, BaseTransform paramBaseTransform, Rectangle paramRectangle, ImageData[] paramArrayOfImageData)
/*     */   {
/* 143 */     setEffect(paramEffect);
/* 144 */     Rectangle localRectangle1 = getResultBounds(paramBaseTransform, paramRectangle, paramArrayOfImageData);
/* 145 */     setDestBounds(localRectangle1);
/*     */ 
/* 148 */     HeapImage localHeapImage1 = (HeapImage)paramArrayOfImageData[0].getTransformedImage(localRectangle1);
/* 149 */     int i = 0;
/* 150 */     int j = 0;
/* 151 */     int k = localHeapImage1.getPhysicalWidth();
/* 152 */     int m = localHeapImage1.getPhysicalHeight();
/* 153 */     int n = localHeapImage1.getScanlineStride();
/* 154 */     int[] arrayOfInt1 = localHeapImage1.getPixelArray();
/*     */ 
/* 156 */     Rectangle localRectangle2 = new Rectangle(i, j, k, m);
/* 157 */     Rectangle localRectangle3 = paramArrayOfImageData[0].getTransformedBounds(localRectangle1);
/* 158 */     BaseTransform localBaseTransform1 = BaseTransform.IDENTITY_TRANSFORM;
/* 159 */     setInputBounds(0, localRectangle3);
/* 160 */     setInputNativeBounds(0, localRectangle2);
/* 161 */     HeapImage localHeapImage2 = (HeapImage)paramArrayOfImageData[1].getTransformedImage(localRectangle1);
/* 162 */     int i1 = 0;
/* 163 */     int i2 = 0;
/* 164 */     int i3 = localHeapImage2.getPhysicalWidth();
/* 165 */     int i4 = localHeapImage2.getPhysicalHeight();
/* 166 */     int i5 = localHeapImage2.getScanlineStride();
/* 167 */     int[] arrayOfInt2 = localHeapImage2.getPixelArray();
/*     */ 
/* 169 */     Rectangle localRectangle4 = new Rectangle(i1, i2, i3, i4);
/* 170 */     Rectangle localRectangle5 = paramArrayOfImageData[1].getTransformedBounds(localRectangle1);
/* 171 */     BaseTransform localBaseTransform2 = BaseTransform.IDENTITY_TRANSFORM;
/* 172 */     setInputBounds(1, localRectangle5);
/* 173 */     setInputNativeBounds(1, localRectangle4);
/*     */ 
/* 176 */     float[] arrayOfFloat1 = new float[4];
/* 177 */     getTextureCoordinates(0, arrayOfFloat1, localRectangle3.x, localRectangle3.y, k, m, localRectangle1, localBaseTransform1);
/*     */ 
/* 181 */     float[] arrayOfFloat2 = new float[4];
/* 182 */     getTextureCoordinates(1, arrayOfFloat2, localRectangle5.x, localRectangle5.y, i3, i4, localRectangle1, localBaseTransform2);
/*     */ 
/* 190 */     int i6 = localRectangle1.width;
/* 191 */     int i7 = localRectangle1.height;
/*     */ 
/* 193 */     HeapImage localHeapImage3 = (HeapImage)getRenderer().getCompatibleImage(i6, i7);
/* 194 */     setDestNativeBounds(localHeapImage3.getPhysicalWidth(), localHeapImage3.getPhysicalHeight());
/* 195 */     int i8 = localHeapImage3.getScanlineStride();
/* 196 */     int[] arrayOfInt3 = localHeapImage3.getPixelArray();
/*     */ 
/* 201 */     float f5 = getLightSpecularExponent();
/* 202 */     float f6 = getDiffuseConstant();
/* 203 */     float f7 = getSurfaceScale();
/* 204 */     float[] arrayOfFloat3 = getNormalizedLightDirection();
/* 205 */     float f8 = arrayOfFloat3[0]; float f9 = arrayOfFloat3[1]; float f10 = arrayOfFloat3[2];
/* 206 */     float[] arrayOfFloat4 = getLightColor();
/* 207 */     float f11 = arrayOfFloat4[0]; float f12 = arrayOfFloat4[1]; float f13 = arrayOfFloat4[2];
/* 208 */     FloatBuffer localFloatBuffer = getKvals();
/* 209 */     float[] arrayOfFloat5 = new float[localFloatBuffer.capacity()];
/* 210 */     localFloatBuffer.get(arrayOfFloat5);
/* 211 */     float[] arrayOfFloat6 = getLightPosition();
/* 212 */     float f14 = arrayOfFloat6[0]; float f15 = arrayOfFloat6[1]; float f16 = arrayOfFloat6[2];
/* 213 */     float f17 = getSpecularConstant();
/* 214 */     float f18 = getSpecularExponent();
/*     */ 
/* 217 */     float f19 = (arrayOfFloat1[2] - arrayOfFloat1[0]) / i6;
/* 218 */     float f20 = (arrayOfFloat1[3] - arrayOfFloat1[1]) / i7;
/* 219 */     float f21 = (arrayOfFloat2[2] - arrayOfFloat2[0]) / i6;
/* 220 */     float f22 = (arrayOfFloat2[3] - arrayOfFloat2[1]) / i7;
/*     */ 
/* 223 */     float f23 = arrayOfFloat1[1] + f20 * 0.5F;
/* 224 */     float f24 = arrayOfFloat2[1] + f22 * 0.5F;
/*     */ 
/* 226 */     for (int i10 = 0; i10 < 0 + i7; i10++) {
/* 227 */       float f25 = i10;
/*     */ 
/* 229 */       int i9 = i10 * i8;
/*     */ 
/* 231 */       float f26 = arrayOfFloat1[0] + f19 * 0.5F;
/* 232 */       float f27 = arrayOfFloat2[0] + f21 * 0.5F;
/*     */ 
/* 234 */       for (int i11 = 0; i11 < 0 + i6; i11++) {
/* 235 */         float f28 = i11;
/*     */ 
/* 241 */         float f33 = f27;
/* 242 */         float f34 = f24;
/*     */         int i12;
/* 244 */         if ((f33 >= 0.0F) && (f34 >= 0.0F)) {
/* 245 */           int i13 = (int)(f33 * i3);
/* 246 */           i14 = (int)(f34 * i4);
/* 247 */           int i15 = (i13 >= i3) || (i14 >= i4) ? 1 : 0;
/*     */ 
/* 250 */           i12 = i15 != 0 ? 0 : arrayOfInt2[(i14 * i5 + i13)];
/*     */         }
/*     */         else {
/* 253 */           i12 = 0;
/*     */         }
/* 255 */         float f29 = (i12 >> 16 & 0xFF) / 255.0F;
/* 256 */         float f30 = (i12 >> 8 & 0xFF) / 255.0F;
/* 257 */         float f31 = (i12 & 0xFF) / 255.0F;
/* 258 */         float f32 = (i12 >>> 24) / 255.0F;
/*     */ 
/* 261 */         f33 = f29;
/* 262 */         f34 = f30;
/* 263 */         float f35 = f31;
/* 264 */         float f36 = f32;
/*     */ 
/* 266 */         float f37 = 0.0F;
/* 267 */         float f38 = 0.0F;
/* 268 */         float f39 = 1.0F;
/* 269 */         for (int i14 = 0; 
/* 270 */           i14 < 8; i14++)
/*     */         {
/* 272 */           f40 = f26 + arrayOfFloat5[(i14 * 4 + 0)];
/* 273 */           f41 = f23 + arrayOfFloat5[(i14 * 4 + 1)];
/*     */           int i16;
/* 275 */           if ((f40 >= 0.0F) && (f41 >= 0.0F)) {
/* 276 */             int i17 = (int)(f40 * k);
/* 277 */             int i18 = (int)(f41 * m);
/* 278 */             int i19 = (i17 >= k) || (i18 >= m) ? 1 : 0;
/*     */ 
/* 281 */             i16 = i19 != 0 ? 0 : arrayOfInt1[(i18 * n + i17)];
/*     */           }
/*     */           else {
/* 284 */             i16 = 0;
/*     */           }
/* 286 */           f32 = (i16 >>> 24) / 255.0F;
/*     */ 
/* 289 */           f37 += arrayOfFloat5[(i14 * 4 + 2)] * f32;
/* 290 */           f38 += arrayOfFloat5[(i14 * 4 + 3)] * f32;
/*     */         }
/*     */ 
/* 294 */         float f43 = f37;
/* 295 */         float f44 = f38;
/* 296 */         float f45 = f39;
/* 297 */         float f46 = (float)Math.sqrt(f43 * f43 + f44 * f44 + f45 * f45);
/*     */ 
/* 300 */         float f40 = f43 / f46;
/* 301 */         float f41 = f44 / f46;
/* 302 */         float f42 = f45 / f46;
/*     */ 
/* 305 */         f43 = f40;
/* 306 */         f44 = f41;
/* 307 */         f45 = f42;
/*     */ 
/* 309 */         f46 = f26;
/* 310 */         float f47 = f23;
/*     */         int i20;
/* 312 */         if ((f46 >= 0.0F) && (f47 >= 0.0F)) {
/* 313 */           int i21 = (int)(f46 * k);
/* 314 */           int i22 = (int)(f47 * m);
/* 315 */           int i23 = (i21 >= k) || (i22 >= m) ? 1 : 0;
/*     */ 
/* 318 */           i20 = i23 != 0 ? 0 : arrayOfInt1[(i22 * n + i21)];
/*     */         }
/*     */         else {
/* 321 */           i20 = 0;
/*     */         }
/* 323 */         f32 = (i20 >>> 24) / 255.0F;
/*     */ 
/* 326 */         f46 = f32;
/* 327 */         f47 = f28;
/* 328 */         float f48 = f25;
/* 329 */         float f49 = f7 * f46;
/*     */ 
/* 331 */         float f50 = f14 - f47;
/* 332 */         float f51 = f15 - f48;
/* 333 */         float f52 = f16 - f49;
/* 334 */         float f53 = (float)Math.sqrt(f50 * f50 + f51 * f51 + f52 * f52);
/*     */ 
/* 337 */         f40 = f50 / f53;
/* 338 */         f41 = f51 / f53;
/* 339 */         f42 = f52 / f53;
/*     */ 
/* 342 */         f50 = f40;
/* 343 */         f51 = f41;
/* 344 */         f52 = f42;
/*     */ 
/* 347 */         float f54 = f50;
/* 348 */         float f55 = f51;
/* 349 */         float f56 = f52;
/* 350 */         float f57 = f8;
/* 351 */         float f58 = f9;
/* 352 */         float f59 = f10;
/* 353 */         f53 = f54 * f57 + f55 * f58 + f56 * f59;
/*     */ 
/* 358 */         f54 = f53;
/*     */ 
/* 361 */         f56 = f54;
/* 362 */         f57 = 0.0F;
/* 363 */         f55 = f56 < f57 ? f56 : f57;
/*     */ 
/* 366 */         f54 = f55;
/*     */ 
/* 369 */         f57 = -f54;
/* 370 */         f58 = f5;
/* 371 */         f56 = (float)Math.pow(f57, f58);
/*     */ 
/* 374 */         f57 = f11 * f56;
/* 375 */         f58 = f12 * f56;
/* 376 */         f59 = f13 * f56;
/* 377 */         float f60 = 0.0F;
/* 378 */         float f61 = 0.0F;
/* 379 */         float f62 = 1.0F;
/*     */ 
/* 381 */         float f63 = f50 + f60;
/* 382 */         float f64 = f51 + f61;
/* 383 */         float f65 = f52 + f62;
/* 384 */         float f66 = (float)Math.sqrt(f63 * f63 + f64 * f64 + f65 * f65);
/*     */ 
/* 387 */         f40 = f63 / f66;
/* 388 */         f41 = f64 / f66;
/* 389 */         f42 = f65 / f66;
/*     */ 
/* 392 */         f63 = f40;
/* 393 */         f64 = f41;
/* 394 */         f65 = f42;
/*     */ 
/* 400 */         float f70 = f43;
/* 401 */         float f71 = f44;
/* 402 */         float f72 = f45;
/* 403 */         float f73 = f50;
/* 404 */         float f74 = f51;
/* 405 */         float f75 = f52;
/* 406 */         f53 = f70 * f73 + f71 * f74 + f72 * f75;
/*     */ 
/* 411 */         f66 = f6 * f53 * f57;
/* 412 */         float f67 = f6 * f53 * f58;
/* 413 */         float f68 = f6 * f53 * f59;
/*     */ 
/* 416 */         f73 = f66;
/* 417 */         f74 = f67;
/* 418 */         f75 = f68;
/* 419 */         float f76 = 0.0F;
/* 420 */         float f77 = 1.0F;
/* 421 */         f70 = f73 > f77 ? f77 : f73 < f76 ? f76 : f73;
/*     */ 
/* 423 */         f71 = f74 > f77 ? f77 : f74 < f76 ? f76 : f74;
/*     */ 
/* 425 */         f72 = f75 > f77 ? f77 : f75 < f76 ? f76 : f75;
/*     */ 
/* 429 */         f66 = f70;
/* 430 */         f67 = f71;
/* 431 */         f68 = f72;
/* 432 */         float f69 = 1.0F;
/*     */ 
/* 438 */         f77 = f43;
/* 439 */         float f78 = f44;
/* 440 */         float f79 = f45;
/* 441 */         float f80 = f63;
/* 442 */         float f81 = f64;
/* 443 */         float f82 = f65;
/* 444 */         f53 = f77 * f80 + f78 * f81 + f79 * f82;
/*     */ 
/* 449 */         f77 = f53;
/*     */ 
/* 451 */         f78 = f77;
/* 452 */         f79 = f18;
/* 453 */         f56 = (float)Math.pow(f78, f79);
/*     */ 
/* 456 */         f73 = f17 * f56 * f57;
/* 457 */         f74 = f17 * f56 * f58;
/* 458 */         f75 = f17 * f56 * f59;
/*     */ 
/* 461 */         f79 = f73;
/* 462 */         f80 = f74;
/* 463 */         f78 = f79 > f80 ? f79 : f80;
/*     */ 
/* 466 */         f76 = f78;
/*     */ 
/* 468 */         f79 = f76;
/* 469 */         f80 = f75;
/* 470 */         f78 = f79 > f80 ? f79 : f80;
/*     */ 
/* 473 */         f76 = f78;
/* 474 */         f33 *= f66;
/* 475 */         f34 *= f67;
/* 476 */         f35 *= f68;
/* 477 */         f36 *= f69;
/* 478 */         f73 *= f36;
/* 479 */         f74 *= f36;
/* 480 */         f75 *= f36;
/* 481 */         f76 *= f36;
/* 482 */         float f1 = f73 + f33 * (1.0F - f76);
/* 483 */         float f2 = f74 + f34 * (1.0F - f76);
/* 484 */         float f3 = f75 + f35 * (1.0F - f76);
/* 485 */         float f4 = f76 + f36 * (1.0F - f76);
/*     */ 
/* 489 */         if (f4 < 0.0F) f4 = 0.0F; else if (f4 > 1.0F) f4 = 1.0F;
/* 490 */         if (f1 < 0.0F) f1 = 0.0F; else if (f1 > f4) f1 = f4;
/* 491 */         if (f2 < 0.0F) f2 = 0.0F; else if (f2 > f4) f2 = f4;
/* 492 */         if (f3 < 0.0F) f3 = 0.0F; else if (f3 > f4) f3 = f4;
/* 493 */         arrayOfInt3[(i9 + i11)] = ((int)(f1 * 255.0F) << 16 | (int)(f2 * 255.0F) << 8 | (int)(f3 * 255.0F) << 0 | (int)(f4 * 255.0F) << 24);
/*     */ 
/* 499 */         f26 += f19;
/* 500 */         f27 += f21;
/*     */       }
/*     */ 
/* 504 */       f23 += f20;
/* 505 */       f24 += f22;
/*     */     }
/*     */ 
/* 509 */     paramArrayOfImageData[0].releaseTransformedImage(localHeapImage1);
/* 510 */     paramArrayOfImageData[1].releaseTransformedImage(localHeapImage2);
/*     */ 
/* 513 */     return new ImageData(getFilterContext(), localHeapImage3, localRectangle1);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.sw.java.JSWPhongLighting_SPOTPeer
 * JD-Core Version:    0.6.2
 */