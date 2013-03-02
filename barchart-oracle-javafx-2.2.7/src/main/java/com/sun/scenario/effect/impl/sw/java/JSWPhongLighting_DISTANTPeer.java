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
/*     */ public class JSWPhongLighting_DISTANTPeer extends JSWEffectPeer
/*     */ {
/*     */   private FloatBuffer kvals;
/*     */ 
/*     */   public JSWPhongLighting_DISTANTPeer(FilterContext paramFilterContext, Renderer paramRenderer, String paramString)
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
/* 201 */     float[] arrayOfFloat3 = getNormalizedLightPosition();
/* 202 */     float f5 = arrayOfFloat3[0]; float f6 = arrayOfFloat3[1]; float f7 = arrayOfFloat3[2];
/* 203 */     float f8 = getDiffuseConstant();
/* 204 */     float[] arrayOfFloat4 = getLightColor();
/* 205 */     float f9 = arrayOfFloat4[0]; float f10 = arrayOfFloat4[1]; float f11 = arrayOfFloat4[2];
/* 206 */     FloatBuffer localFloatBuffer = getKvals();
/* 207 */     float[] arrayOfFloat5 = new float[localFloatBuffer.capacity()];
/* 208 */     localFloatBuffer.get(arrayOfFloat5);
/* 209 */     float f12 = getSpecularConstant();
/* 210 */     float f13 = getSpecularExponent();
/*     */ 
/* 213 */     float f14 = (arrayOfFloat1[2] - arrayOfFloat1[0]) / i6;
/* 214 */     float f15 = (arrayOfFloat1[3] - arrayOfFloat1[1]) / i7;
/* 215 */     float f16 = (arrayOfFloat2[2] - arrayOfFloat2[0]) / i6;
/* 216 */     float f17 = (arrayOfFloat2[3] - arrayOfFloat2[1]) / i7;
/*     */ 
/* 219 */     float f18 = arrayOfFloat1[1] + f15 * 0.5F;
/* 220 */     float f19 = arrayOfFloat2[1] + f17 * 0.5F;
/*     */ 
/* 222 */     for (int i10 = 0; i10 < 0 + i7; i10++) {
/* 223 */       float f20 = i10;
/*     */ 
/* 225 */       int i9 = i10 * i8;
/*     */ 
/* 227 */       float f21 = arrayOfFloat1[0] + f14 * 0.5F;
/* 228 */       float f22 = arrayOfFloat2[0] + f16 * 0.5F;
/*     */ 
/* 230 */       for (int i11 = 0; i11 < 0 + i6; i11++) {
/* 231 */         float f23 = i11;
/*     */ 
/* 237 */         float f28 = f22;
/* 238 */         float f29 = f19;
/*     */         int i12;
/* 240 */         if ((f28 >= 0.0F) && (f29 >= 0.0F)) {
/* 241 */           int i13 = (int)(f28 * i3);
/* 242 */           i14 = (int)(f29 * i4);
/* 243 */           int i15 = (i13 >= i3) || (i14 >= i4) ? 1 : 0;
/*     */ 
/* 246 */           i12 = i15 != 0 ? 0 : arrayOfInt2[(i14 * i5 + i13)];
/*     */         }
/*     */         else {
/* 249 */           i12 = 0;
/*     */         }
/* 251 */         float f24 = (i12 >> 16 & 0xFF) / 255.0F;
/* 252 */         float f25 = (i12 >> 8 & 0xFF) / 255.0F;
/* 253 */         float f26 = (i12 & 0xFF) / 255.0F;
/* 254 */         float f27 = (i12 >>> 24) / 255.0F;
/*     */ 
/* 257 */         f28 = f24;
/* 258 */         f29 = f25;
/* 259 */         float f30 = f26;
/* 260 */         float f31 = f27;
/*     */ 
/* 262 */         float f32 = 0.0F;
/* 263 */         float f33 = 0.0F;
/* 264 */         float f34 = 1.0F;
/* 265 */         for (int i14 = 0; 
/* 266 */           i14 < 8; i14++)
/*     */         {
/* 268 */           f35 = f21 + arrayOfFloat5[(i14 * 4 + 0)];
/* 269 */           f36 = f18 + arrayOfFloat5[(i14 * 4 + 1)];
/*     */           int i16;
/* 271 */           if ((f35 >= 0.0F) && (f36 >= 0.0F)) {
/* 272 */             int i17 = (int)(f35 * k);
/* 273 */             int i18 = (int)(f36 * m);
/* 274 */             int i19 = (i17 >= k) || (i18 >= m) ? 1 : 0;
/*     */ 
/* 277 */             i16 = i19 != 0 ? 0 : arrayOfInt1[(i18 * n + i17)];
/*     */           }
/*     */           else {
/* 280 */             i16 = 0;
/*     */           }
/* 282 */           f27 = (i16 >>> 24) / 255.0F;
/*     */ 
/* 285 */           f32 += arrayOfFloat5[(i14 * 4 + 2)] * f27;
/* 286 */           f33 += arrayOfFloat5[(i14 * 4 + 3)] * f27;
/*     */         }
/*     */ 
/* 290 */         float f38 = f32;
/* 291 */         float f39 = f33;
/* 292 */         float f40 = f34;
/* 293 */         float f41 = (float)Math.sqrt(f38 * f38 + f39 * f39 + f40 * f40);
/*     */ 
/* 296 */         float f35 = f38 / f41;
/* 297 */         float f36 = f39 / f41;
/* 298 */         float f37 = f40 / f41;
/*     */ 
/* 301 */         f38 = f35;
/* 302 */         f39 = f36;
/* 303 */         f40 = f37;
/* 304 */         f41 = f5;
/* 305 */         float f42 = f6;
/* 306 */         float f43 = f7;
/* 307 */         float f44 = f9;
/* 308 */         float f45 = f10;
/* 309 */         float f46 = f11;
/* 310 */         float f47 = 0.0F;
/* 311 */         float f48 = 0.0F;
/* 312 */         float f49 = 1.0F;
/*     */ 
/* 314 */         float f50 = f41 + f47;
/* 315 */         float f51 = f42 + f48;
/* 316 */         float f52 = f43 + f49;
/* 317 */         float f53 = (float)Math.sqrt(f50 * f50 + f51 * f51 + f52 * f52);
/*     */ 
/* 320 */         f35 = f50 / f53;
/* 321 */         f36 = f51 / f53;
/* 322 */         f37 = f52 / f53;
/*     */ 
/* 325 */         f50 = f35;
/* 326 */         f51 = f36;
/* 327 */         f52 = f37;
/*     */ 
/* 334 */         float f58 = f38;
/* 335 */         float f59 = f39;
/* 336 */         float f60 = f40;
/* 337 */         float f61 = f41;
/* 338 */         float f62 = f42;
/* 339 */         float f63 = f43;
/* 340 */         float f57 = f58 * f61 + f59 * f62 + f60 * f63;
/*     */ 
/* 345 */         f53 = f8 * f57 * f44;
/* 346 */         float f54 = f8 * f57 * f45;
/* 347 */         float f55 = f8 * f57 * f46;
/*     */ 
/* 350 */         f61 = f53;
/* 351 */         f62 = f54;
/* 352 */         f63 = f55;
/* 353 */         float f64 = 0.0F;
/* 354 */         float f65 = 1.0F;
/* 355 */         f58 = f61 > f65 ? f65 : f61 < f64 ? f64 : f61;
/*     */ 
/* 357 */         f59 = f62 > f65 ? f65 : f62 < f64 ? f64 : f62;
/*     */ 
/* 359 */         f60 = f63 > f65 ? f65 : f63 < f64 ? f64 : f63;
/*     */ 
/* 363 */         f53 = f58;
/* 364 */         f54 = f59;
/* 365 */         f55 = f60;
/* 366 */         float f56 = 1.0F;
/*     */ 
/* 372 */         f65 = f38;
/* 373 */         float f66 = f39;
/* 374 */         float f67 = f40;
/* 375 */         float f68 = f50;
/* 376 */         float f69 = f51;
/* 377 */         float f70 = f52;
/* 378 */         f57 = f65 * f68 + f66 * f69 + f67 * f70;
/*     */ 
/* 383 */         f65 = f57;
/*     */ 
/* 386 */         f67 = f65;
/* 387 */         f68 = f13;
/* 388 */         f66 = (float)Math.pow(f67, f68);
/*     */ 
/* 391 */         f61 = f12 * f66 * f44;
/* 392 */         f62 = f12 * f66 * f45;
/* 393 */         f63 = f12 * f66 * f46;
/*     */ 
/* 396 */         f68 = f61;
/* 397 */         f69 = f62;
/* 398 */         f67 = f68 > f69 ? f68 : f69;
/*     */ 
/* 401 */         f64 = f67;
/*     */ 
/* 403 */         f68 = f64;
/* 404 */         f69 = f63;
/* 405 */         f67 = f68 > f69 ? f68 : f69;
/*     */ 
/* 408 */         f64 = f67;
/* 409 */         f28 *= f53;
/* 410 */         f29 *= f54;
/* 411 */         f30 *= f55;
/* 412 */         f31 *= f56;
/* 413 */         f61 *= f31;
/* 414 */         f62 *= f31;
/* 415 */         f63 *= f31;
/* 416 */         f64 *= f31;
/* 417 */         float f1 = f61 + f28 * (1.0F - f64);
/* 418 */         float f2 = f62 + f29 * (1.0F - f64);
/* 419 */         float f3 = f63 + f30 * (1.0F - f64);
/* 420 */         float f4 = f64 + f31 * (1.0F - f64);
/*     */ 
/* 424 */         if (f4 < 0.0F) f4 = 0.0F; else if (f4 > 1.0F) f4 = 1.0F;
/* 425 */         if (f1 < 0.0F) f1 = 0.0F; else if (f1 > f4) f1 = f4;
/* 426 */         if (f2 < 0.0F) f2 = 0.0F; else if (f2 > f4) f2 = f4;
/* 427 */         if (f3 < 0.0F) f3 = 0.0F; else if (f3 > f4) f3 = f4;
/* 428 */         arrayOfInt3[(i9 + i11)] = ((int)(f1 * 255.0F) << 16 | (int)(f2 * 255.0F) << 8 | (int)(f3 * 255.0F) << 0 | (int)(f4 * 255.0F) << 24);
/*     */ 
/* 434 */         f21 += f14;
/* 435 */         f22 += f16;
/*     */       }
/*     */ 
/* 439 */       f18 += f15;
/* 440 */       f19 += f17;
/*     */     }
/*     */ 
/* 444 */     paramArrayOfImageData[0].releaseTransformedImage(localHeapImage1);
/* 445 */     paramArrayOfImageData[1].releaseTransformedImage(localHeapImage2);
/*     */ 
/* 448 */     return new ImageData(getFilterContext(), localHeapImage3, localRectangle1);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.sw.java.JSWPhongLighting_DISTANTPeer
 * JD-Core Version:    0.6.2
 */