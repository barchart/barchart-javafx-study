/*     */ package com.sun.prism.impl.ps;
/*     */ 
/*     */ import com.sun.javafx.geom.PickRay;
/*     */ import com.sun.javafx.geom.Point2D;
/*     */ import com.sun.javafx.geom.Vec3d;
/*     */ import com.sun.javafx.geom.transform.Affine3D;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.javafx.geom.transform.NoninvertibleTransformException;
/*     */ import com.sun.prism.Image;
/*     */ import com.sun.prism.PixelFormat;
/*     */ import com.sun.prism.ResourceFactory;
/*     */ import com.sun.prism.Texture;
/*     */ import com.sun.prism.Texture.Usage;
/*     */ import com.sun.prism.Texture.WrapMode;
/*     */ import com.sun.prism.camera.PrismCameraImpl;
/*     */ import com.sun.prism.camera.PrismPerspectiveCameraImpl;
/*     */ import com.sun.prism.impl.BufferUtil;
/*     */ import com.sun.prism.paint.Color;
/*     */ import com.sun.prism.paint.Gradient;
/*     */ import com.sun.prism.paint.ImagePattern;
/*     */ import com.sun.prism.paint.LinearGradient;
/*     */ import com.sun.prism.paint.RadialGradient;
/*     */ import com.sun.prism.paint.Stop;
/*     */ import com.sun.prism.ps.Shader;
/*     */ import com.sun.prism.ps.ShaderGraphics;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.util.List;
/*     */ 
/*     */ class PaintHelper
/*     */ {
/*     */   static final int MULTI_MAX_FRACTIONS = 12;
/*     */   private static final int MULTI_TEXTURE_SIZE = 16;
/*     */   private static final int MULTI_CACHE_SIZE = 256;
/*     */   private static final float FULL_TEXEL_Y = 0.0039063F;
/*     */   private static final float HALF_TEXEL_Y = 0.00195313F;
/*  66 */   private static final FloatBuffer stopVals = BufferUtil.newFloatBuffer(48);
/*     */ 
/*  68 */   private static final ByteBuffer bgraColors = BufferUtil.newByteBuffer(64);
/*     */ 
/*  70 */   private static final Image colorsImg = Image.fromByteBgraPreData(bgraColors, 16, 1);
/*     */ 
/*  72 */   private static final int[] previousColors = new int[16];
/*     */ 
/*  74 */   private static long cacheOffset = -1L;
/*     */ 
/*  76 */   private static Texture gradientCacheTexture = null;
/*     */ 
/*  78 */   private static final Affine3D scratchXform3D = new Affine3D();
/*     */ 
/*     */   static Texture getGradientTexture(ShaderGraphics paramShaderGraphics, Gradient paramGradient) {
/*  81 */     if (gradientCacheTexture == null) {
/*  82 */       gradientCacheTexture = paramShaderGraphics.getResourceFactory().createTexture(PixelFormat.BYTE_BGRA_PRE, Texture.Usage.DEFAULT, 16, 256);
/*     */ 
/*  84 */       gradientCacheTexture.setWrapMode(Texture.WrapMode.CLAMP_TO_EDGE);
/*  85 */       gradientCacheTexture.setLinearFiltering(true);
/*     */     }
/*     */ 
/*  88 */     return gradientCacheTexture;
/*     */   }
/*     */ 
/*     */   private static void stopsToImage(List<Stop> paramList, int paramInt)
/*     */   {
/*  93 */     if (paramInt > 12) {
/*  94 */       throw new RuntimeException("Maximum number of gradient stops exceeded (paint uses " + paramInt + " stops, but max is " + 12 + ")");
/*     */     }
/*     */ 
/* 100 */     bgraColors.clear();
/* 101 */     Object localObject1 = null;
/* 102 */     for (int i = 0; i < 16; i++)
/*     */     {
/*     */       Object localObject2;
/* 104 */       if (i < paramInt) {
/* 105 */         localObject2 = ((Stop)paramList.get(i)).getColor();
/* 106 */         localObject1 = localObject2;
/*     */       }
/*     */       else
/*     */       {
/* 111 */         localObject2 = localObject1;
/*     */       }
/* 113 */       ((Color)localObject2).putBgraPreBytes(bgraColors);
/*     */ 
/* 118 */       int j = ((Color)localObject2).getIntArgbPre();
/* 119 */       if (j != previousColors[i]) {
/* 120 */         previousColors[i] = j;
/*     */       }
/*     */     }
/* 123 */     bgraColors.rewind();
/*     */   }
/*     */ 
/*     */   private static int initGradient(Gradient paramGradient)
/*     */   {
/* 131 */     long l1 = paramGradient.getGradientOffset();
/* 132 */     if ((l1 >= 0L) && (l1 > cacheOffset - 256L)) {
/* 133 */       return (int)(l1 % 256L);
/*     */     }
/* 135 */     List localList = paramGradient.getStops();
/* 136 */     int i = paramGradient.getNumStops();
/* 137 */     stopsToImage(localList, i);
/* 138 */     long l2 = ++cacheOffset;
/* 139 */     paramGradient.setGradientOffset(l2);
/* 140 */     int j = (int)(l2 % 256L);
/* 141 */     gradientCacheTexture.update(colorsImg, 0, j);
/* 142 */     return j;
/*     */   }
/*     */ 
/*     */   private static void setMultiGradient(Shader paramShader, Gradient paramGradient)
/*     */   {
/* 149 */     List localList = paramGradient.getStops();
/* 150 */     int i = paramGradient.getNumStops();
/*     */ 
/* 152 */     stopVals.clear();
/* 153 */     for (int j = 0; j < 12; j++)
/*     */     {
/* 155 */       stopVals.put(j < i ? ((Stop)localList.get(j)).getOffset() : 0.0F);
/*     */ 
/* 157 */       stopVals.put(j < i - 1 ? 1.0F / (((Stop)localList.get(j + 1)).getOffset() - ((Stop)localList.get(j)).getOffset()) : 0.0F);
/*     */ 
/* 159 */       stopVals.put(0.0F);
/* 160 */       stopVals.put(0.0F);
/*     */     }
/* 162 */     stopVals.rewind();
/* 163 */     paramShader.setConstants("fractions", stopVals, 0, 12);
/* 164 */     float f = initGradient(paramGradient);
/* 165 */     paramShader.setConstant("offset", f / 256.0F + 0.00195313F);
/*     */   }
/*     */ 
/*     */   static void setLinearGradient(ShaderGraphics paramShaderGraphics, Shader paramShader, LinearGradient paramLinearGradient, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/* 199 */     BaseTransform localBaseTransform1 = paramLinearGradient.getGradientTransformNoClone();
/* 200 */     Affine3D localAffine3D = scratchXform3D;
/* 201 */     paramShaderGraphics.getPaintShaderTransform(localAffine3D);
/*     */ 
/* 203 */     if (localBaseTransform1 != null) {
/* 204 */       localAffine3D.concatenate(localBaseTransform1);
/*     */     }
/*     */ 
/* 207 */     float f1 = paramFloat1 + paramLinearGradient.getX1() * paramFloat3;
/* 208 */     float f2 = paramFloat2 + paramLinearGradient.getY1() * paramFloat4;
/* 209 */     float f3 = paramFloat1 + paramLinearGradient.getX2() * paramFloat3;
/* 210 */     float f4 = paramFloat2 + paramLinearGradient.getY2() * paramFloat4;
/*     */ 
/* 213 */     float f5 = f1;
/* 214 */     float f6 = f2;
/* 215 */     localAffine3D.translate(f5, f6);
/*     */ 
/* 217 */     f5 = f3 - f5;
/* 218 */     f6 = f4 - f6;
/* 219 */     double d1 = Math.sqrt(f5 * f5 + f6 * f6);
/*     */ 
/* 221 */     localAffine3D.rotate(Math.atan2(f6, f5));
/*     */ 
/* 223 */     localAffine3D.scale(d1, 1.0D);
/*     */     double d4;
/*     */     double d3;
/*     */     double d2;
/* 228 */     if (!localAffine3D.is2D()) {
/* 229 */       BaseTransform localBaseTransform2 = BaseTransform.IDENTITY_TRANSFORM;
/*     */       try {
/* 231 */         localBaseTransform2 = localAffine3D.createInverse();
/*     */       } catch (NoninvertibleTransformException localNoninvertibleTransformException2) {
/* 233 */         d2 = d3 = d4 = 0.0D;
/*     */       }
/*     */ 
/* 236 */       PrismCameraImpl localPrismCameraImpl = paramShaderGraphics.getCameraNoClone();
/* 237 */       Vec3d localVec3d = new Vec3d();
/* 238 */       PickRay localPickRay1 = new PickRay();
/*     */ 
/* 240 */       PickRay localPickRay2 = project(0.0F, 0.0F, localPrismCameraImpl, localBaseTransform2, localPickRay1, localVec3d, null);
/* 241 */       PickRay localPickRay3 = project(1.0F, 0.0F, localPrismCameraImpl, localBaseTransform2, localPickRay1, localVec3d, null);
/* 242 */       PickRay localPickRay4 = project(0.0F, 1.0F, localPrismCameraImpl, localBaseTransform2, localPickRay1, localVec3d, null);
/*     */ 
/* 244 */       d2 = localPickRay3.getDirectionNoClone().x - localPickRay2.getDirectionNoClone().x;
/* 245 */       d3 = localPickRay4.getDirectionNoClone().x - localPickRay2.getDirectionNoClone().x;
/* 246 */       d4 = localPickRay2.getDirectionNoClone().x;
/*     */ 
/* 248 */       d2 *= -localPickRay2.getOriginNoClone().z;
/* 249 */       d3 *= -localPickRay2.getOriginNoClone().z;
/* 250 */       d4 *= -localPickRay2.getOriginNoClone().z;
/*     */ 
/* 252 */       double d5 = localPickRay3.getDirectionNoClone().z - localPickRay2.getDirectionNoClone().z;
/* 253 */       double d6 = localPickRay4.getDirectionNoClone().z - localPickRay2.getDirectionNoClone().z;
/* 254 */       double d7 = localPickRay2.getDirectionNoClone().z;
/*     */ 
/* 256 */       paramShader.setConstant("gradParams", (float)d2, (float)d3, (float)d4, (float)localPickRay2.getOriginNoClone().x);
/* 257 */       paramShader.setConstant("perspVec", (float)d5, (float)d6, (float)d7);
/*     */     } else {
/*     */       try {
/* 260 */         localAffine3D.invert();
/*     */       } catch (NoninvertibleTransformException localNoninvertibleTransformException1) {
/* 262 */         d2 = d3 = d4 = 0.0D;
/*     */       }
/* 264 */       d2 = (float)localAffine3D.getMxx();
/* 265 */       d3 = (float)localAffine3D.getMxy();
/* 266 */       d4 = (float)localAffine3D.getMxt();
/* 267 */       paramShader.setConstant("gradParams", (float)d2, (float)d3, (float)d4, 0.0F);
/* 268 */       paramShader.setConstant("perspVec", 0.0F, 0.0F, 1.0F);
/*     */     }
/*     */ 
/* 271 */     setMultiGradient(paramShader, paramLinearGradient);
/*     */   }
/*     */ 
/*     */   static void setRadialGradient(ShaderGraphics paramShaderGraphics, Shader paramShader, RadialGradient paramRadialGradient, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/* 293 */     Affine3D localAffine3D = scratchXform3D;
/* 294 */     paramShaderGraphics.getPaintShaderTransform(localAffine3D);
/*     */ 
/* 298 */     float f1 = paramRadialGradient.getRadius();
/* 299 */     float f2 = paramRadialGradient.getCenterX();
/* 300 */     float f3 = paramRadialGradient.getCenterY();
/* 301 */     float f4 = paramRadialGradient.getFocusAngle();
/* 302 */     float f5 = paramRadialGradient.getFocusDistance();
/* 303 */     if (f5 < 0.0F) {
/* 304 */       f5 = -f5;
/* 305 */       f4 += 180.0F;
/*     */     }
/* 307 */     f4 = (float)Math.toRadians(f4);
/* 308 */     if (paramRadialGradient.isProportional()) {
/* 309 */       float f6 = paramFloat1 + paramFloat3 / 2.0F;
/* 310 */       float f7 = paramFloat2 + paramFloat4 / 2.0F;
/* 311 */       float f9 = Math.min(paramFloat3, paramFloat4);
/* 312 */       f2 = (f2 - 0.5F) * f9 + f6;
/* 313 */       f3 = (f3 - 0.5F) * f9 + f7;
/* 314 */       if ((paramFloat3 != paramFloat4) && (paramFloat3 != 0.0F) && (paramFloat4 != 0.0F)) {
/* 315 */         localAffine3D.translate(f6, f7);
/* 316 */         localAffine3D.scale(paramFloat3 / f9, paramFloat4 / f9);
/* 317 */         localAffine3D.translate(-f6, -f7);
/*     */       }
/* 319 */       f1 *= f9;
/*     */     }
/*     */ 
/* 323 */     BaseTransform localBaseTransform = paramRadialGradient.getGradientTransformNoClone();
/* 324 */     if (localBaseTransform != null) {
/* 325 */       localAffine3D.concatenate(localBaseTransform);
/*     */     }
/*     */ 
/* 331 */     localAffine3D.translate(f2, f3);
/* 332 */     localAffine3D.rotate(f4);
/* 333 */     localAffine3D.scale(f1, f1);
/*     */     try
/*     */     {
/* 337 */       localAffine3D.invert();
/*     */     } catch (Exception localException) {
/* 339 */       localAffine3D.setToScale(0.0D, 0.0D, 0.0D);
/*     */     }
/*     */ 
/* 342 */     if (!localAffine3D.is2D()) {
/* 343 */       PrismCameraImpl localPrismCameraImpl = paramShaderGraphics.getCameraNoClone();
/* 344 */       Vec3d localVec3d = new Vec3d();
/* 345 */       PickRay localPickRay1 = new PickRay();
/*     */ 
/* 347 */       PickRay localPickRay2 = project(0.0F, 0.0F, localPrismCameraImpl, localAffine3D, localPickRay1, localVec3d, null);
/* 348 */       PickRay localPickRay3 = project(1.0F, 0.0F, localPrismCameraImpl, localAffine3D, localPickRay1, localVec3d, null);
/* 349 */       PickRay localPickRay4 = project(0.0F, 1.0F, localPrismCameraImpl, localAffine3D, localPickRay1, localVec3d, null);
/*     */ 
/* 351 */       double d1 = localPickRay3.getDirectionNoClone().x - localPickRay2.getDirectionNoClone().x;
/* 352 */       double d2 = localPickRay4.getDirectionNoClone().x - localPickRay2.getDirectionNoClone().x;
/* 353 */       double d3 = localPickRay2.getDirectionNoClone().x;
/*     */ 
/* 355 */       double d4 = localPickRay3.getDirectionNoClone().y - localPickRay2.getDirectionNoClone().y;
/* 356 */       double d5 = localPickRay4.getDirectionNoClone().y - localPickRay2.getDirectionNoClone().y;
/* 357 */       double d6 = localPickRay2.getDirectionNoClone().y;
/*     */ 
/* 359 */       d1 *= -localPickRay2.getOriginNoClone().z;
/* 360 */       d2 *= -localPickRay2.getOriginNoClone().z;
/* 361 */       d3 *= -localPickRay2.getOriginNoClone().z;
/*     */ 
/* 363 */       d4 *= -localPickRay2.getOriginNoClone().z;
/* 364 */       d5 *= -localPickRay2.getOriginNoClone().z;
/* 365 */       d6 *= -localPickRay2.getOriginNoClone().z;
/*     */ 
/* 367 */       double d7 = localPickRay3.getDirectionNoClone().z - localPickRay2.getDirectionNoClone().z;
/* 368 */       double d8 = localPickRay4.getDirectionNoClone().z - localPickRay2.getDirectionNoClone().z;
/* 369 */       double d9 = localPickRay2.getDirectionNoClone().z;
/*     */ 
/* 371 */       paramShader.setConstant("perspVec", (float)d7, (float)d8, (float)d9);
/* 372 */       paramShader.setConstant("m0", (float)d1, (float)d2, (float)d3, (float)localPickRay2.getOriginNoClone().x);
/* 373 */       paramShader.setConstant("m1", (float)d4, (float)d5, (float)d6, (float)localPickRay2.getOriginNoClone().y);
/*     */     } else {
/* 375 */       f8 = (float)localAffine3D.getMxx();
/* 376 */       f10 = (float)localAffine3D.getMxy();
/* 377 */       float f11 = (float)localAffine3D.getMxt();
/* 378 */       paramShader.setConstant("m0", f8, f10, f11, 0.0F);
/*     */ 
/* 380 */       float f12 = (float)localAffine3D.getMyx();
/* 381 */       float f13 = (float)localAffine3D.getMyy();
/* 382 */       float f14 = (float)localAffine3D.getMyt();
/* 383 */       paramShader.setConstant("m1", f12, f13, f14, 0.0F);
/*     */ 
/* 385 */       paramShader.setConstant("perspVec", 0.0F, 0.0F, 1.0F);
/*     */     }
/*     */ 
/* 390 */     f5 = Math.min(f5, 0.99F);
/*     */ 
/* 393 */     float f8 = 1.0F - f5 * f5;
/* 394 */     float f10 = 1.0F / f8;
/* 395 */     paramShader.setConstant("precalc", f5, f8, f10);
/*     */ 
/* 397 */     setMultiGradient(paramShader, paramRadialGradient);
/*     */   }
/*     */ 
/*     */   static void setImagePattern(ShaderGraphics paramShaderGraphics, Shader paramShader, ImagePattern paramImagePattern, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/* 436 */     float f1 = paramFloat1 + paramImagePattern.getX() * paramFloat3;
/* 437 */     float f2 = paramFloat2 + paramImagePattern.getY() * paramFloat4;
/* 438 */     float f3 = f1 + paramImagePattern.getWidth() * paramFloat3;
/* 439 */     float f4 = f2 + paramImagePattern.getHeight() * paramFloat4;
/*     */ 
/* 442 */     Affine3D localAffine3D = scratchXform3D;
/* 443 */     paramShaderGraphics.getPaintShaderTransform(localAffine3D);
/*     */ 
/* 445 */     localAffine3D.translate(f1, f2);
/* 446 */     localAffine3D.scale(f3 - f1, f4 - f2);
/*     */     try
/*     */     {
/* 449 */       localAffine3D.invert();
/*     */     } catch (Exception localException) {
/* 451 */       localAffine3D.setToScale(0.0D, 0.0D, 0.0D);
/*     */     }
/*     */ 
/* 454 */     if (!localAffine3D.is2D()) {
/* 455 */       PrismCameraImpl localPrismCameraImpl = paramShaderGraphics.getCameraNoClone();
/* 456 */       Vec3d localVec3d = new Vec3d();
/* 457 */       PickRay localPickRay1 = new PickRay();
/* 458 */       PickRay localPickRay2 = project(0.0F, 0.0F, localPrismCameraImpl, localAffine3D, localPickRay1, localVec3d, null);
/* 459 */       PickRay localPickRay3 = project(1.0F, 0.0F, localPrismCameraImpl, localAffine3D, localPickRay1, localVec3d, null);
/* 460 */       PickRay localPickRay4 = project(0.0F, 1.0F, localPrismCameraImpl, localAffine3D, localPickRay1, localVec3d, null);
/*     */ 
/* 462 */       double d1 = localPickRay3.getDirectionNoClone().x - localPickRay2.getDirectionNoClone().x;
/* 463 */       double d2 = localPickRay4.getDirectionNoClone().x - localPickRay2.getDirectionNoClone().x;
/* 464 */       double d3 = localPickRay2.getDirectionNoClone().x;
/*     */ 
/* 466 */       double d4 = localPickRay3.getDirectionNoClone().y - localPickRay2.getDirectionNoClone().y;
/* 467 */       double d5 = localPickRay4.getDirectionNoClone().y - localPickRay2.getDirectionNoClone().y;
/* 468 */       double d6 = localPickRay2.getDirectionNoClone().y;
/*     */ 
/* 470 */       d1 *= -localPickRay2.getOriginNoClone().z;
/* 471 */       d2 *= -localPickRay2.getOriginNoClone().z;
/* 472 */       d3 *= -localPickRay2.getOriginNoClone().z;
/*     */ 
/* 474 */       d4 *= -localPickRay2.getOriginNoClone().z;
/* 475 */       d5 *= -localPickRay2.getOriginNoClone().z;
/* 476 */       d6 *= -localPickRay2.getOriginNoClone().z;
/*     */ 
/* 478 */       double d7 = localPickRay3.getDirectionNoClone().z - localPickRay2.getDirectionNoClone().z;
/* 479 */       double d8 = localPickRay4.getDirectionNoClone().z - localPickRay2.getDirectionNoClone().z;
/* 480 */       double d9 = localPickRay2.getDirectionNoClone().z;
/*     */ 
/* 482 */       paramShader.setConstant("perspVec", (float)d7, (float)d8, (float)d9);
/* 483 */       paramShader.setConstant("xParams", (float)d1, (float)d2, (float)d3, (float)localPickRay2.getOriginNoClone().x);
/* 484 */       paramShader.setConstant("yParams", (float)d4, (float)d5, (float)d6, (float)localPickRay2.getOriginNoClone().y);
/*     */     } else {
/* 486 */       float f5 = (float)localAffine3D.getMxx();
/* 487 */       f6 = (float)localAffine3D.getMxy();
/* 488 */       f7 = (float)localAffine3D.getMxt();
/* 489 */       paramShader.setConstant("xParams", f5, f6, f7, 0.0F);
/*     */ 
/* 491 */       f8 = (float)localAffine3D.getMyx();
/* 492 */       f9 = (float)localAffine3D.getMyy();
/* 493 */       f10 = (float)localAffine3D.getMyt();
/* 494 */       paramShader.setConstant("yParams", f8, f9, f10, 0.0F);
/* 495 */       paramShader.setConstant("perspVec", 0.0F, 0.0F, 1.0F);
/*     */     }
/*     */ 
/* 498 */     Texture localTexture = paramShaderGraphics.getResourceFactory().getCachedTexture(paramImagePattern.getImage());
/* 499 */     float f6 = localTexture.getPhysicalWidth();
/* 500 */     float f7 = localTexture.getPhysicalHeight();
/* 501 */     float f8 = localTexture.getContentX() / f6;
/* 502 */     float f9 = localTexture.getContentY() / f7;
/* 503 */     float f10 = localTexture.getContentWidth() / f6;
/* 504 */     float f11 = localTexture.getContentHeight() / f7;
/* 505 */     paramShader.setConstant("content", f8, f9, f10, f11);
/*     */   }
/*     */ 
/*     */   static PickRay project(float paramFloat1, float paramFloat2, PrismCameraImpl paramPrismCameraImpl, BaseTransform paramBaseTransform, PickRay paramPickRay, Vec3d paramVec3d, Point2D paramPoint2D)
/*     */   {
/* 512 */     paramPickRay = paramPrismCameraImpl.computePickRay(paramFloat1, paramFloat2, paramPickRay);
/* 513 */     return paramPickRay.project(paramBaseTransform, paramPrismCameraImpl instanceof PrismPerspectiveCameraImpl, paramVec3d, paramPoint2D);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.impl.ps.PaintHelper
 * JD-Core Version:    0.6.2
 */