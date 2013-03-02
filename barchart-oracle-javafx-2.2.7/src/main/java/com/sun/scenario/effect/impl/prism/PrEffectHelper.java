/*     */ package com.sun.scenario.effect.impl.prism;
/*     */ 
/*     */ import com.sun.glass.ui.Screen;
/*     */ import com.sun.javafx.geom.PickRay;
/*     */ import com.sun.javafx.geom.Point2D;
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.Vec3d;
/*     */ import com.sun.javafx.geom.transform.Affine2D;
/*     */ import com.sun.javafx.geom.transform.Affine3D;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.javafx.geom.transform.NoninvertibleTransformException;
/*     */ import com.sun.prism.Graphics;
/*     */ import com.sun.prism.RenderTarget;
/*     */ import com.sun.prism.Texture;
/*     */ import com.sun.prism.camera.PrismCameraImpl;
/*     */ import com.sun.prism.camera.PrismPerspectiveCameraImpl;
/*     */ import com.sun.scenario.effect.Effect;
/*     */ import com.sun.scenario.effect.ImageData;
/*     */ import com.sun.scenario.effect.impl.EffectPeer;
/*     */ import com.sun.scenario.effect.impl.ImagePool;
/*     */ 
/*     */ public class PrEffectHelper
/*     */ {
/*     */   public static void render(Effect paramEffect1, Graphics paramGraphics, float paramFloat1, float paramFloat2, Effect paramEffect2)
/*     */   {
/*  75 */     Rectangle localRectangle1 = getGraphicsClipNoClone(paramGraphics);
/*  76 */     BaseTransform localBaseTransform1 = paramGraphics.getTransformNoClone().copy();
/*     */     Object localObject1;
/*     */     Object localObject2;
/*     */     Object localObject3;
/*  78 */     if (localBaseTransform1.is2D())
/*     */     {
/*  81 */       if ((paramFloat1 != 0.0F) || (paramFloat2 != 0.0F) || (!localBaseTransform1.isIdentity())) {
/*  82 */         localObject1 = new Affine2D(localBaseTransform1);
/*  83 */         ((Affine2D)localObject1).translate(paramFloat1, paramFloat2);
/*     */       } else {
/*  85 */         localObject1 = BaseTransform.IDENTITY_TRANSFORM;
/*     */       }
/*  87 */       paramGraphics.setTransform(null);
/*  88 */       localObject2 = null;
/*     */     }
/*     */     else
/*     */     {
/*  97 */       double d1 = Math.hypot(localBaseTransform1.getMxx(), localBaseTransform1.getMyx());
/*  98 */       double d2 = Math.hypot(localBaseTransform1.getMxy(), localBaseTransform1.getMyy());
/*  99 */       double d3 = Math.max(d1, d2);
/* 100 */       if (d3 <= 1.0D) {
/* 101 */         localObject1 = BaseTransform.IDENTITY_TRANSFORM;
/* 102 */         localObject2 = localBaseTransform1;
/*     */       } else {
/* 104 */         localObject1 = BaseTransform.getScaleInstance(d3, d3);
/* 105 */         localObject2 = new Affine3D(localBaseTransform1);
/* 106 */         d3 = 1.0D / d3;
/* 107 */         ((Affine3D)localObject2).scale(d3, d3);
/* 109 */       }localObject3 = paramGraphics.getCameraNoClone();
/*     */       BaseTransform localBaseTransform2;
/*     */       try {
/* 112 */         localBaseTransform2 = ((BaseTransform)localObject2).createInverse();
/*     */       } catch (NoninvertibleTransformException localNoninvertibleTransformException) {
/* 114 */         return;
/*     */       }
/* 116 */       PickRay localPickRay = new PickRay();
/* 117 */       Vec3d localVec3d = new Vec3d();
/*     */ 
/* 120 */       float f1 = localRectangle1.x + 0.5F;
/* 121 */       float f2 = localRectangle1.y + 0.5F;
/* 122 */       float f3 = localRectangle1.x + localRectangle1.width - 0.5F;
/* 123 */       float f4 = localRectangle1.y + localRectangle1.height - 0.5F;
/* 124 */       Point2D localPoint2D1 = project(f1, f2, (PrismCameraImpl)localObject3, localBaseTransform2, localPickRay, localVec3d, null);
/* 125 */       Point2D localPoint2D2 = project(f3, f2, (PrismCameraImpl)localObject3, localBaseTransform2, localPickRay, localVec3d, null);
/* 126 */       Point2D localPoint2D3 = project(f1, f4, (PrismCameraImpl)localObject3, localBaseTransform2, localPickRay, localVec3d, null);
/* 127 */       Point2D localPoint2D4 = project(f3, f4, (PrismCameraImpl)localObject3, localBaseTransform2, localPickRay, localVec3d, null);
/* 128 */       localRectangle1 = clipbounds(localPoint2D1, localPoint2D2, localPoint2D3, localPoint2D4);
/*     */     }
/*     */ 
/* 131 */     Screen localScreen = paramGraphics.getAssociatedScreen();
/* 132 */     PrFilterContext localPrFilterContext = PrFilterContext.getInstance(localScreen);
/*     */     PrRenderInfo localPrRenderInfo;
/* 136 */     if (localObject2 != null)
/*     */     {
/* 139 */       localPrRenderInfo = null;
/* 140 */     } else if ((paramGraphics.isDepthBuffer()) && (paramGraphics.isDepthTest()))
/*     */     {
/* 145 */       localPrRenderInfo = null;
/*     */     }
/*     */     else
/*     */     {
/* 150 */       localPrRenderInfo = new PrRenderInfo(paramGraphics);
/* 153 */     }
/*     */ ImagePool.numEffects += 1L;
/*     */     boolean bool;
/*     */     do {
/* 156 */       ImageData localImageData = paramEffect1.filter(localPrFilterContext, (BaseTransform)localObject1, localRectangle1, localPrRenderInfo, paramEffect2);
/* 157 */       if (localImageData == null) return;
/* 158 */       if ((bool = localImageData.validate(localPrFilterContext))) {
/* 159 */         Rectangle localRectangle2 = localImageData.getUntransformedBounds();
/*     */ 
/* 163 */         localObject3 = ((PrTexture)localImageData.getUntransformedImage()).getTextureObject();
/* 164 */         paramGraphics.setTransform((BaseTransform)localObject2);
/* 165 */         paramGraphics.transform(localImageData.getTransform());
/* 166 */         paramGraphics.drawTexture((Texture)localObject3, localRectangle2.x, localRectangle2.y, localRectangle2.width, localRectangle2.height);
/*     */       }
/* 168 */       localImageData.unref();
/* 169 */     }while (!bool);
/* 170 */     paramGraphics.setTransform(localBaseTransform1);
/*     */   }
/*     */ 
/*     */   static Point2D project(float paramFloat1, float paramFloat2, PrismCameraImpl paramPrismCameraImpl, BaseTransform paramBaseTransform, PickRay paramPickRay, Vec3d paramVec3d, Point2D paramPoint2D)
/*     */   {
/* 177 */     paramPickRay = paramPrismCameraImpl.computePickRay(paramFloat1, paramFloat2, paramPickRay);
/* 178 */     return paramPickRay.projectToZeroPlane(paramBaseTransform, paramPrismCameraImpl instanceof PrismPerspectiveCameraImpl, paramVec3d, paramPoint2D);
/*     */   }
/*     */ 
/*     */   static Rectangle clipbounds(Point2D paramPoint2D1, Point2D paramPoint2D2, Point2D paramPoint2D3, Point2D paramPoint2D4)
/*     */   {
/* 192 */     if ((paramPoint2D1 != null) && (paramPoint2D2 != null) && (paramPoint2D3 != null) && (paramPoint2D4 != null))
/*     */     {
/* 194 */       if (paramPoint2D1.x < paramPoint2D2.x) {
/* 195 */         d1 = paramPoint2D1.x; d3 = paramPoint2D2.x;
/*     */       } else {
/* 197 */         d1 = paramPoint2D2.x; d3 = paramPoint2D1.x;
/*     */       }
/* 199 */       if (paramPoint2D1.y < paramPoint2D2.y) {
/* 200 */         d2 = paramPoint2D1.y; d4 = paramPoint2D2.y;
/*     */       } else {
/* 202 */         d2 = paramPoint2D2.y; d4 = paramPoint2D1.y;
/*     */       }
/* 204 */       if (paramPoint2D3.x < paramPoint2D4.x) {
/* 205 */         d1 = Math.min(d1, paramPoint2D3.x); d3 = Math.max(d3, paramPoint2D4.x);
/*     */       } else {
/* 207 */         d1 = Math.min(d1, paramPoint2D4.x); d3 = Math.max(d3, paramPoint2D3.x);
/*     */       }
/* 209 */       if (paramPoint2D3.y < paramPoint2D4.y) {
/* 210 */         d2 = Math.min(d2, paramPoint2D3.y); d4 = Math.max(d4, paramPoint2D4.y);
/*     */       } else {
/* 212 */         d2 = Math.min(d2, paramPoint2D4.y); d4 = Math.max(d4, paramPoint2D3.y);
/*     */       }
/*     */ 
/* 216 */       double d1 = Math.floor(d1 - 0.5D);
/* 217 */       double d2 = Math.floor(d2 - 0.5D);
/* 218 */       double d3 = Math.ceil(d3 + 0.5D) - d1;
/* 219 */       double d4 = Math.ceil(d4 + 0.5D) - d2;
/* 220 */       int i = (int)d1;
/* 221 */       int j = (int)d2;
/* 222 */       int k = (int)d3;
/* 223 */       int m = (int)d4;
/* 224 */       if ((i == d1) && (j == d2) && (k == d3) && (m == d4))
/*     */       {
/* 228 */         return new Rectangle(i, j, k, m);
/*     */       }
/*     */     }
/* 231 */     return null;
/*     */   }
/*     */ 
/*     */   public static Rectangle getGraphicsClipNoClone(Graphics paramGraphics) {
/* 235 */     Rectangle localRectangle = paramGraphics.getClipRectNoClone();
/* 236 */     if (localRectangle == null) {
/* 237 */       RenderTarget localRenderTarget = paramGraphics.getRenderTarget();
/* 238 */       localRectangle = new Rectangle(localRenderTarget.getContentWidth(), localRenderTarget.getContentHeight());
/*     */     }
/* 240 */     return localRectangle;
/*     */   }
/*     */ 
/*     */   public static void renderImageData(Graphics paramGraphics, ImageData paramImageData, Rectangle paramRectangle)
/*     */   {
/* 247 */     int i = paramRectangle.width;
/* 248 */     int j = paramRectangle.height;
/* 249 */     PrDrawable localPrDrawable = (PrDrawable)paramImageData.getUntransformedImage();
/* 250 */     BaseTransform localBaseTransform = paramImageData.getTransform();
/* 251 */     Rectangle localRectangle = paramImageData.getUntransformedBounds();
/* 252 */     float f1 = 0.0F;
/* 253 */     float f2 = 0.0F;
/* 254 */     float f3 = f1 + i;
/* 255 */     float f4 = f2 + j;
/* 256 */     if (localBaseTransform.isTranslateOrIdentity()) {
/* 257 */       float f5 = (float)localBaseTransform.getMxt();
/* 258 */       float f6 = (float)localBaseTransform.getMyt();
/* 259 */       float f7 = paramRectangle.x - (localRectangle.x + f5);
/* 260 */       float f8 = paramRectangle.y - (localRectangle.y + f6);
/* 261 */       float f9 = f7 + i;
/* 262 */       float f10 = f8 + j;
/* 263 */       paramGraphics.drawTexture(localPrDrawable.getTextureObject(), f1, f2, f3, f4, f7, f8, f9, f10);
/*     */     }
/*     */     else
/*     */     {
/* 267 */       float[] arrayOfFloat = new float[8];
/* 268 */       int k = EffectPeer.getTextureCoordinates(arrayOfFloat, localRectangle.x, localRectangle.y, localPrDrawable.getPhysicalWidth(), localPrDrawable.getPhysicalHeight(), paramRectangle, localBaseTransform);
/*     */ 
/* 274 */       if (k < 8) {
/* 275 */         paramGraphics.drawTextureRaw(localPrDrawable.getTextureObject(), f1, f2, f3, f4, arrayOfFloat[0], arrayOfFloat[1], arrayOfFloat[2], arrayOfFloat[3]);
/*     */       }
/*     */       else
/*     */       {
/* 280 */         paramGraphics.drawMappedTextureRaw(localPrDrawable.getTextureObject(), f1, f2, f3, f4, arrayOfFloat[0], arrayOfFloat[1], arrayOfFloat[4], arrayOfFloat[5], arrayOfFloat[6], arrayOfFloat[7], arrayOfFloat[2], arrayOfFloat[3]);
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.prism.PrEffectHelper
 * JD-Core Version:    0.6.2
 */