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
/*     */ public class JSWPhongLighting_POINTPeer extends JSWEffectPeer
/*     */ {
/*     */   private FloatBuffer kvals;
/*     */ 
/*     */   public JSWPhongLighting_POINTPeer(FilterContext paramFilterContext, Renderer paramRenderer, String paramString)
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
/* 201 */     float f5 = getDiffuseConstant();
/* 202 */     float f6 = getSurfaceScale();
/* 203 */     float[] arrayOfFloat3 = getLightColor();
/* 204 */     float f7 = arrayOfFloat3[0]; float f8 = arrayOfFloat3[1]; float f9 = arrayOfFloat3[2];
/* 205 */     FloatBuffer localFloatBuffer = getKvals();
/* 206 */     float[] arrayOfFloat4 = new float[localFloatBuffer.capacity()];
/* 207 */     localFloatBuffer.get(arrayOfFloat4);
/* 208 */     float[] arrayOfFloat5 = getLightPosition();
/* 209 */     float f10 = arrayOfFloat5[0]; float f11 = arrayOfFloat5[1]; float f12 = arrayOfFloat5[2];
/* 210 */     float f13 = getSpecularConstant();
/* 211 */     float f14 = getSpecularExponent();
/*     */ 
/* 214 */     float f15 = (arrayOfFloat1[2] - arrayOfFloat1[0]) / i6;
/* 215 */     float f16 = (arrayOfFloat1[3] - arrayOfFloat1[1]) / i7;
/* 216 */     float f17 = (arrayOfFloat2[2] - arrayOfFloat2[0]) / i6;
/* 217 */     float f18 = (arrayOfFloat2[3] - arrayOfFloat2[1]) / i7;
/*     */ 
/* 220 */     float f19 = arrayOfFloat1[1] + f16 * 0.5F;
/* 221 */     float f20 = arrayOfFloat2[1] + f18 * 0.5F;
/*     */ 
/* 223 */     for (int i10 = 0; i10 < 0 + i7; i10++) {
/* 224 */       float f21 = i10;
/*     */ 
/* 226 */       int i9 = i10 * i8;
/*     */ 
/* 228 */       float f22 = arrayOfFloat1[0] + f15 * 0.5F;
/* 229 */       float f23 = arrayOfFloat2[0] + f17 * 0.5F;
/*     */ 
/* 231 */       for (int i11 = 0; i11 < 0 + i6; i11++) {
/* 232 */         float f24 = i11;
/*     */ 
/* 238 */         float f29 = f23;
/* 239 */         float f30 = f20;
/*     */         int i12;
/* 241 */         if ((f29 >= 0.0F) && (f30 >= 0.0F)) {
/* 242 */           int i13 = (int)(f29 * i3);
/* 243 */           i14 = (int)(f30 * i4);
/* 244 */           int i15 = (i13 >= i3) || (i14 >= i4) ? 1 : 0;
/*     */ 
/* 247 */           i12 = i15 != 0 ? 0 : arrayOfInt2[(i14 * i5 + i13)];
/*     */         }
/*     */         else {
/* 250 */           i12 = 0;
/*     */         }
/* 252 */         float f25 = (i12 >> 16 & 0xFF) / 255.0F;
/* 253 */         float f26 = (i12 >> 8 & 0xFF) / 255.0F;
/* 254 */         float f27 = (i12 & 0xFF) / 255.0F;
/* 255 */         float f28 = (i12 >>> 24) / 255.0F;
/*     */ 
/* 258 */         f29 = f25;
/* 259 */         f30 = f26;
/* 260 */         float f31 = f27;
/* 261 */         float f32 = f28;
/*     */ 
/* 263 */         float f33 = 0.0F;
/* 264 */         float f34 = 0.0F;
/* 265 */         float f35 = 1.0F;
/* 266 */         for (int i14 = 0; 
/* 267 */           i14 < 8; i14++)
/*     */         {
/* 269 */           f36 = f22 + arrayOfFloat4[(i14 * 4 + 0)];
/* 270 */           f37 = f19 + arrayOfFloat4[(i14 * 4 + 1)];
/*     */           int i16;
/* 272 */           if ((f36 >= 0.0F) && (f37 >= 0.0F)) {
/* 273 */             int i17 = (int)(f36 * k);
/* 274 */             int i18 = (int)(f37 * m);
/* 275 */             int i19 = (i17 >= k) || (i18 >= m) ? 1 : 0;
/*     */ 
/* 278 */             i16 = i19 != 0 ? 0 : arrayOfInt1[(i18 * n + i17)];
/*     */           }
/*     */           else {
/* 281 */             i16 = 0;
/*     */           }
/* 283 */           f28 = (i16 >>> 24) / 255.0F;
/*     */ 
/* 286 */           f33 += arrayOfFloat4[(i14 * 4 + 2)] * f28;
/* 287 */           f34 += arrayOfFloat4[(i14 * 4 + 3)] * f28;
/*     */         }
/*     */ 
/* 291 */         float f39 = f33;
/* 292 */         float f40 = f34;
/* 293 */         float f41 = f35;
/* 294 */         float f42 = (float)Math.sqrt(f39 * f39 + f40 * f40 + f41 * f41);
/*     */ 
/* 297 */         float f36 = f39 / f42;
/* 298 */         float f37 = f40 / f42;
/* 299 */         float f38 = f41 / f42;
/*     */ 
/* 302 */         f39 = f36;
/* 303 */         f40 = f37;
/* 304 */         f41 = f38;
/*     */ 
/* 306 */         f42 = f22;
/* 307 */         float f43 = f19;
/*     */         int i20;
/* 309 */         if ((f42 >= 0.0F) && (f43 >= 0.0F)) {
/* 310 */           int i21 = (int)(f42 * k);
/* 311 */           int i22 = (int)(f43 * m);
/* 312 */           int i23 = (i21 >= k) || (i22 >= m) ? 1 : 0;
/*     */ 
/* 315 */           i20 = i23 != 0 ? 0 : arrayOfInt1[(i22 * n + i21)];
/*     */         }
/*     */         else {
/* 318 */           i20 = 0;
/*     */         }
/* 320 */         f28 = (i20 >>> 24) / 255.0F;
/*     */ 
/* 323 */         f42 = f28;
/* 324 */         f43 = f24;
/* 325 */         float f44 = f21;
/* 326 */         float f45 = f6 * f42;
/*     */ 
/* 328 */         float f46 = f10 - f43;
/* 329 */         float f47 = f11 - f44;
/* 330 */         float f48 = f12 - f45;
/* 331 */         float f49 = (float)Math.sqrt(f46 * f46 + f47 * f47 + f48 * f48);
/*     */ 
/* 334 */         f36 = f46 / f49;
/* 335 */         f37 = f47 / f49;
/* 336 */         f38 = f48 / f49;
/*     */ 
/* 339 */         f46 = f36;
/* 340 */         f47 = f37;
/* 341 */         f48 = f38;
/* 342 */         f49 = f7;
/* 343 */         float f50 = f8;
/* 344 */         float f51 = f9;
/* 345 */         float f52 = 0.0F;
/* 346 */         float f53 = 0.0F;
/* 347 */         float f54 = 1.0F;
/*     */ 
/* 349 */         float f55 = f46 + f52;
/* 350 */         float f56 = f47 + f53;
/* 351 */         float f57 = f48 + f54;
/* 352 */         float f58 = (float)Math.sqrt(f55 * f55 + f56 * f56 + f57 * f57);
/*     */ 
/* 355 */         f36 = f55 / f58;
/* 356 */         f37 = f56 / f58;
/* 357 */         f38 = f57 / f58;
/*     */ 
/* 360 */         f55 = f36;
/* 361 */         f56 = f37;
/* 362 */         f57 = f38;
/*     */ 
/* 369 */         float f63 = f39;
/* 370 */         float f64 = f40;
/* 371 */         float f65 = f41;
/* 372 */         float f66 = f46;
/* 373 */         float f67 = f47;
/* 374 */         float f68 = f48;
/* 375 */         float f62 = f63 * f66 + f64 * f67 + f65 * f68;
/*     */ 
/* 380 */         f58 = f5 * f62 * f49;
/* 381 */         float f59 = f5 * f62 * f50;
/* 382 */         float f60 = f5 * f62 * f51;
/*     */ 
/* 385 */         f66 = f58;
/* 386 */         f67 = f59;
/* 387 */         f68 = f60;
/* 388 */         float f69 = 0.0F;
/* 389 */         float f70 = 1.0F;
/* 390 */         f63 = f66 > f70 ? f70 : f66 < f69 ? f69 : f66;
/*     */ 
/* 392 */         f64 = f67 > f70 ? f70 : f67 < f69 ? f69 : f67;
/*     */ 
/* 394 */         f65 = f68 > f70 ? f70 : f68 < f69 ? f69 : f68;
/*     */ 
/* 398 */         f58 = f63;
/* 399 */         f59 = f64;
/* 400 */         f60 = f65;
/* 401 */         float f61 = 1.0F;
/*     */ 
/* 407 */         f70 = f39;
/* 408 */         float f71 = f40;
/* 409 */         float f72 = f41;
/* 410 */         float f73 = f55;
/* 411 */         float f74 = f56;
/* 412 */         float f75 = f57;
/* 413 */         f62 = f70 * f73 + f71 * f74 + f72 * f75;
/*     */ 
/* 418 */         f70 = f62;
/*     */ 
/* 421 */         f72 = f70;
/* 422 */         f73 = f14;
/* 423 */         f71 = (float)Math.pow(f72, f73);
/*     */ 
/* 426 */         f66 = f13 * f71 * f49;
/* 427 */         f67 = f13 * f71 * f50;
/* 428 */         f68 = f13 * f71 * f51;
/*     */ 
/* 431 */         f73 = f66;
/* 432 */         f74 = f67;
/* 433 */         f72 = f73 > f74 ? f73 : f74;
/*     */ 
/* 436 */         f69 = f72;
/*     */ 
/* 438 */         f73 = f69;
/* 439 */         f74 = f68;
/* 440 */         f72 = f73 > f74 ? f73 : f74;
/*     */ 
/* 443 */         f69 = f72;
/* 444 */         f29 *= f58;
/* 445 */         f30 *= f59;
/* 446 */         f31 *= f60;
/* 447 */         f32 *= f61;
/* 448 */         f66 *= f32;
/* 449 */         f67 *= f32;
/* 450 */         f68 *= f32;
/* 451 */         f69 *= f32;
/* 452 */         float f1 = f66 + f29 * (1.0F - f69);
/* 453 */         float f2 = f67 + f30 * (1.0F - f69);
/* 454 */         float f3 = f68 + f31 * (1.0F - f69);
/* 455 */         float f4 = f69 + f32 * (1.0F - f69);
/*     */ 
/* 459 */         if (f4 < 0.0F) f4 = 0.0F; else if (f4 > 1.0F) f4 = 1.0F;
/* 460 */         if (f1 < 0.0F) f1 = 0.0F; else if (f1 > f4) f1 = f4;
/* 461 */         if (f2 < 0.0F) f2 = 0.0F; else if (f2 > f4) f2 = f4;
/* 462 */         if (f3 < 0.0F) f3 = 0.0F; else if (f3 > f4) f3 = f4;
/* 463 */         arrayOfInt3[(i9 + i11)] = ((int)(f1 * 255.0F) << 16 | (int)(f2 * 255.0F) << 8 | (int)(f3 * 255.0F) << 0 | (int)(f4 * 255.0F) << 24);
/*     */ 
/* 469 */         f22 += f15;
/* 470 */         f23 += f17;
/*     */       }
/*     */ 
/* 474 */       f19 += f16;
/* 475 */       f20 += f18;
/*     */     }
/*     */ 
/* 479 */     paramArrayOfImageData[0].releaseTransformedImage(localHeapImage1);
/* 480 */     paramArrayOfImageData[1].releaseTransformedImage(localHeapImage2);
/*     */ 
/* 483 */     return new ImageData(getFilterContext(), localHeapImage3, localRectangle1);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.sw.java.JSWPhongLighting_POINTPeer
 * JD-Core Version:    0.6.2
 */