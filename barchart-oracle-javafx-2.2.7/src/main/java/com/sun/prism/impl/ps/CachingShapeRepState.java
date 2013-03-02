/*     */ package com.sun.prism.impl.ps;
/*     */ 
/*     */ import com.sun.javafx.geom.RectBounds;
/*     */ import com.sun.javafx.geom.Shape;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.prism.BasicStroke;
/*     */ import com.sun.prism.Graphics;
/*     */ import com.sun.prism.ResourceFactory;
/*     */ import com.sun.prism.Texture;
/*     */ import com.sun.prism.impl.Disposer;
/*     */ import com.sun.prism.impl.Disposer.Record;
/*     */ import com.sun.prism.impl.VertexBuffer;
/*     */ import com.sun.prism.impl.shape.MaskData;
/*     */ import com.sun.prism.impl.shape.ShapeUtil;
/*     */ import com.sun.prism.paint.Gradient;
/*     */ import com.sun.prism.paint.Paint;
/*     */ import com.sun.prism.paint.Paint.Type;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ class CachingShapeRepState
/*     */ {
/* 289 */   private static final BaseTransform IDENT = BaseTransform.IDENTITY_TRANSFORM;
/*     */ 
/* 291 */   private static final MaskCache maskCache = new MaskCache(null);
/*     */   private int renderCount;
/*     */   private Boolean tryCache;
/*     */   private BaseTransform lastXform;
/*     */   private final MaskTexData texData;
/* 298 */   private final Object disposerReferent = new Object();
/*     */   private final Disposer.Record disposerRecord;
/*     */ 
/*     */   private static boolean equalsIgnoreTranslation(BaseTransform paramBaseTransform1, BaseTransform paramBaseTransform2)
/*     */   {
/* 266 */     if (paramBaseTransform1 == paramBaseTransform2) {
/* 267 */       return true;
/*     */     }
/*     */ 
/* 271 */     return (paramBaseTransform1.getMxx() == paramBaseTransform2.getMxx()) && (paramBaseTransform1.getMxy() == paramBaseTransform2.getMxy()) && (paramBaseTransform1.getMyx() == paramBaseTransform2.getMyx()) && (paramBaseTransform1.getMyy() == paramBaseTransform2.getMyy());
/*     */   }
/*     */ 
/*     */   private static boolean dimensionsAreSimilar(RectBounds paramRectBounds1, RectBounds paramRectBounds2)
/*     */   {
/* 284 */     return (Math.abs(paramRectBounds1.getWidth() - paramRectBounds2.getWidth()) < 0.001D) && (Math.abs(paramRectBounds1.getHeight() - paramRectBounds2.getHeight()) < 0.001D);
/*     */   }
/*     */ 
/*     */   CachingShapeRepState()
/*     */   {
/* 302 */     this.texData = new MaskTexData(null);
/* 303 */     this.disposerRecord = new CSRDisposerRecord(this.texData, null);
/* 304 */     Disposer.addRecord(this.disposerReferent, this.disposerRecord);
/*     */   }
/*     */ 
/*     */   void fillNoCache(Graphics paramGraphics, Shape paramShape)
/*     */   {
/* 309 */     paramGraphics.fill(paramShape);
/*     */   }
/*     */ 
/*     */   void drawNoCache(Graphics paramGraphics, Shape paramShape)
/*     */   {
/* 314 */     paramGraphics.draw(paramShape);
/*     */   }
/*     */ 
/*     */   void invalidate()
/*     */   {
/* 321 */     this.renderCount = 0;
/* 322 */     this.tryCache = null;
/* 323 */     this.lastXform = null;
/*     */   }
/*     */ 
/*     */   private void invalidateMaskTexData()
/*     */   {
/* 329 */     this.tryCache = null;
/* 330 */     this.lastXform = null;
/* 331 */     maskCache.unref(this.texData);
/*     */   }
/*     */ 
/*     */   void render(Graphics paramGraphics, Shape paramShape, BasicStroke paramBasicStroke)
/*     */   {
/* 336 */     BaseTransform localBaseTransform = paramGraphics.getTransformNoClone();
/*     */ 
/* 342 */     if ((this.lastXform == null) || (!equalsIgnoreTranslation(localBaseTransform, this.lastXform))) {
/* 343 */       invalidateMaskTexData();
/* 344 */       if (this.lastXform != null) {
/* 345 */         this.renderCount = 0;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 351 */     RectBounds localRectBounds1 = null;
/* 352 */     RectBounds localRectBounds2 = null;
/*     */ 
/* 354 */     if (this.tryCache == null)
/*     */     {
/* 356 */       localRectBounds1 = paramShape.getBounds();
/* 357 */       if (localBaseTransform.isIdentity()) {
/* 358 */         localRectBounds2 = localRectBounds1;
/*     */       } else {
/* 360 */         localRectBounds2 = new RectBounds();
/*     */ 
/* 362 */         localRectBounds2 = (RectBounds)localBaseTransform.transform(localRectBounds1, localRectBounds2);
/*     */       }
/* 364 */       this.tryCache = Boolean.valueOf(maskCache.hasRoom(localRectBounds2));
/*     */     }
/*     */ 
/* 367 */     BaseShaderGraphics localBaseShaderGraphics = (BaseShaderGraphics)paramGraphics;
/* 368 */     BaseShaderContext localBaseShaderContext = localBaseShaderGraphics.getContext();
/* 369 */     this.renderCount += 1;
/* 370 */     if ((this.tryCache == Boolean.FALSE) || (this.renderCount <= 1) || (localBaseShaderGraphics.isComplexPaint()))
/*     */     {
/* 382 */       if (paramBasicStroke == null)
/* 383 */         fillNoCache(paramGraphics, paramShape);
/*     */       else {
/* 385 */         drawNoCache(paramGraphics, paramShape);
/*     */       }
/* 387 */       return;
/*     */     }
/*     */ 
/* 390 */     if ((this.lastXform == null) || (!this.lastXform.equals(localBaseTransform)))
/*     */     {
/* 392 */       if (localRectBounds1 == null) {
/* 393 */         localRectBounds1 = paramShape.getBounds();
/*     */       }
/* 395 */       if (localRectBounds2 == null) {
/* 396 */         if (localBaseTransform.isIdentity()) {
/* 397 */           localRectBounds2 = localRectBounds1;
/*     */         } else {
/* 399 */           localRectBounds2 = new RectBounds();
/*     */ 
/* 401 */           localRectBounds2 = (RectBounds)localBaseTransform.transform(localRectBounds1, localRectBounds2);
/*     */         }
/*     */       }
/*     */ 
/* 405 */       if (this.texData.cacheEntry != null)
/*     */       {
/* 409 */         this.texData.adjustOrigin(localBaseTransform);
/*     */       }
/*     */       else
/*     */       {
/* 414 */         if (paramBasicStroke != null)
/*     */         {
/* 416 */           paramShape = paramBasicStroke.createStrokedShape(paramShape);
/* 417 */           localRectBounds1 = paramShape.getBounds();
/* 418 */           if (localBaseTransform.isIdentity()) {
/* 419 */             localRectBounds2 = localRectBounds1;
/*     */           }
/*     */           else {
/* 422 */             localRectBounds2 = (RectBounds)localBaseTransform.transform(localRectBounds1, localRectBounds2);
/*     */           }
/*     */         }
/* 425 */         maskCache.get(localBaseShaderContext, this.texData, paramShape, localBaseTransform, localRectBounds2);
/*     */       }
/*     */ 
/* 428 */       if (this.lastXform == null)
/* 429 */         this.lastXform = localBaseTransform.copy();
/*     */       else {
/* 431 */         this.lastXform.setTransform(localBaseTransform);
/*     */       }
/*     */     }
/*     */ 
/* 435 */     Paint localPaint = localBaseShaderGraphics.getPaint();
/* 436 */     float f1 = 0.0F; float f2 = 0.0F; float f3 = 0.0F; float f4 = 0.0F;
/* 437 */     if ((localPaint.getType().isGradient()) && (((Gradient)localPaint).isProportional())) {
/* 438 */       if (localRectBounds1 == null) {
/* 439 */         localRectBounds1 = paramShape.getBounds();
/*     */       }
/* 441 */       f1 = localRectBounds1.getMinX();
/* 442 */       f2 = localRectBounds1.getMinY();
/* 443 */       f3 = localRectBounds1.getWidth();
/* 444 */       f4 = localRectBounds1.getHeight();
/*     */     }
/*     */ 
/* 449 */     localBaseShaderContext.validatePaintOp(localBaseShaderGraphics, IDENT, this.texData.maskTex, f1, f2, f3, f4);
/*     */ 
/* 451 */     int i = this.texData.maskW;
/* 452 */     int j = this.texData.maskH;
/* 453 */     float f5 = this.texData.maskX;
/* 454 */     float f6 = this.texData.maskY;
/* 455 */     float f7 = f5 + i;
/* 456 */     float f8 = f6 + j;
/* 457 */     float f9 = 0.0F;
/* 458 */     float f10 = 0.0F;
/* 459 */     float f11 = f9 + i / this.texData.maskTex.getPhysicalWidth();
/* 460 */     float f12 = f10 + j / this.texData.maskTex.getPhysicalHeight();
/*     */ 
/* 462 */     VertexBuffer localVertexBuffer = localBaseShaderContext.getVertexBuffer();
/* 463 */     localVertexBuffer.addQuad(f5, f6, f7, f8, f9, f10, f11, f12);
/*     */   }
/*     */ 
/*     */   void dispose()
/*     */   {
/* 471 */     invalidate();
/*     */   }
/*     */ 
/*     */   private static class CSRDisposerRecord implements Disposer.Record {
/*     */     private CachingShapeRepState.MaskTexData texData;
/*     */ 
/*     */     private CSRDisposerRecord(CachingShapeRepState.MaskTexData paramMaskTexData) {
/* 478 */       this.texData = paramMaskTexData;
/*     */     }
/*     */ 
/*     */     public void dispose()
/*     */     {
/* 483 */       if (this.texData != null) {
/* 484 */         CachingShapeRepState.maskCache.unref(this.texData);
/* 485 */         this.texData = null;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class MaskCache
/*     */   {
/*     */     private static final int MAX_MASK_DIM = 512;
/*     */     private static final int MAX_SIZE_IN_PIXELS = 4194304;
/* 158 */     private final List<CachingShapeRepState.CacheEntry> entries = new ArrayList();
/*     */     private int totalPixels;
/*     */ 
/*     */     boolean hasRoom(RectBounds paramRectBounds)
/*     */     {
/* 162 */       int i = (int)(paramRectBounds.getWidth() + 0.5F);
/* 163 */       int j = (int)(paramRectBounds.getHeight() + 0.5F);
/* 164 */       int k = i * j;
/* 165 */       return (i <= 512) && (j <= 512) && (this.totalPixels + k <= 4194304);
/*     */     }
/*     */ 
/*     */     void get(BaseShaderContext paramBaseShaderContext, CachingShapeRepState.MaskTexData paramMaskTexData, Shape paramShape, BaseTransform paramBaseTransform, RectBounds paramRectBounds)
/*     */     {
/* 176 */       if (paramMaskTexData == null) {
/* 177 */         throw new InternalError("MaskTexData must be non-null");
/*     */       }
/* 179 */       if (CachingShapeRepState.MaskTexData.access$000(paramMaskTexData) != null) {
/* 180 */         throw new InternalError("CacheEntry should already be null");
/*     */       }
/*     */ 
/* 184 */       for (int i = 0; i < this.entries.size(); i++) {
/* 185 */         CachingShapeRepState.CacheEntry localCacheEntry1 = (CachingShapeRepState.CacheEntry)this.entries.get(i);
/*     */ 
/* 188 */         if ((CachingShapeRepState.dimensionsAreSimilar(paramRectBounds, localCacheEntry1.xformBounds)) && (CachingShapeRepState.equalsIgnoreTranslation(paramBaseTransform, localCacheEntry1.xform)))
/*     */         {
/* 193 */           if (localCacheEntry1.shape.equals(paramShape))
/*     */           {
/* 196 */             localCacheEntry1.refCount += 1;
/* 197 */             localCacheEntry1.texData.copyInto(paramMaskTexData);
/* 198 */             CachingShapeRepState.MaskTexData.access$002(paramMaskTexData, localCacheEntry1);
/*     */ 
/* 201 */             paramMaskTexData.adjustOrigin(paramBaseTransform);
/* 202 */             return;
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 208 */       MaskData localMaskData = ShapeUtil.rasterizeShape(paramShape, null, paramRectBounds, paramBaseTransform, true);
/*     */ 
/* 210 */       int j = localMaskData.getWidth();
/* 211 */       int k = localMaskData.getHeight();
/* 212 */       CachingShapeRepState.MaskTexData.access$302(paramMaskTexData, localMaskData.getOriginX());
/* 213 */       CachingShapeRepState.MaskTexData.access$402(paramMaskTexData, localMaskData.getOriginY());
/* 214 */       CachingShapeRepState.MaskTexData.access$502(paramMaskTexData, j);
/* 215 */       CachingShapeRepState.MaskTexData.access$602(paramMaskTexData, k);
/* 216 */       CachingShapeRepState.MaskTexData.access$702(paramMaskTexData, paramBaseShaderContext.getResourceFactory().createMaskTexture(j, k));
/* 217 */       localMaskData.uploadToTexture(CachingShapeRepState.MaskTexData.access$700(paramMaskTexData), 0, 0, false);
/*     */ 
/* 223 */       CachingShapeRepState.CacheEntry localCacheEntry2 = new CachingShapeRepState.CacheEntry(null);
/*     */ 
/* 226 */       localCacheEntry2.shape = paramShape.copy();
/* 227 */       localCacheEntry2.xform = paramBaseTransform.copy();
/* 228 */       localCacheEntry2.xformBounds = paramRectBounds;
/* 229 */       localCacheEntry2.texData = paramMaskTexData.copy();
/* 230 */       localCacheEntry2.refCount = 1;
/* 231 */       CachingShapeRepState.MaskTexData.access$002(paramMaskTexData, localCacheEntry2);
/* 232 */       this.entries.add(localCacheEntry2);
/* 233 */       this.totalPixels += j * k;
/*     */     }
/*     */ 
/*     */     void unref(CachingShapeRepState.MaskTexData paramMaskTexData) {
/* 237 */       if (paramMaskTexData == null) {
/* 238 */         throw new InternalError("MaskTexData must be non-null");
/*     */       }
/* 240 */       CachingShapeRepState.CacheEntry localCacheEntry = CachingShapeRepState.MaskTexData.access$000(paramMaskTexData);
/* 241 */       if (localCacheEntry == null) {
/* 242 */         return;
/*     */       }
/* 244 */       CachingShapeRepState.MaskTexData.access$002(paramMaskTexData, null);
/* 245 */       CachingShapeRepState.MaskTexData.access$702(paramMaskTexData, null);
/* 246 */       localCacheEntry.refCount -= 1;
/* 247 */       if (localCacheEntry.refCount <= 0) {
/* 248 */         localCacheEntry.shape = null;
/* 249 */         localCacheEntry.xform = null;
/* 250 */         localCacheEntry.xformBounds = null;
/* 251 */         CachingShapeRepState.MaskTexData.access$700(localCacheEntry.texData).dispose();
/* 252 */         localCacheEntry.texData = null;
/* 253 */         this.entries.remove(localCacheEntry);
/* 254 */         this.totalPixels -= CachingShapeRepState.MaskTexData.access$500(paramMaskTexData) * CachingShapeRepState.MaskTexData.access$600(paramMaskTexData);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class CacheEntry
/*     */   {
/*     */     Shape shape;
/*     */     BaseTransform xform;
/*     */     RectBounds xformBounds;
/*     */     CachingShapeRepState.MaskTexData texData;
/*     */     int refCount;
/*     */   }
/*     */ 
/*     */   private static class MaskTexData
/*     */   {
/*     */     private CachingShapeRepState.CacheEntry cacheEntry;
/*     */     private Texture maskTex;
/*     */     private float maskX;
/*     */     private float maskY;
/*     */     private int maskW;
/*     */     private int maskH;
/*     */ 
/*     */     void adjustOrigin(BaseTransform paramBaseTransform)
/*     */     {
/* 116 */       float f1 = (float)(paramBaseTransform.getMxt() - this.cacheEntry.xform.getMxt());
/* 117 */       float f2 = (float)(paramBaseTransform.getMyt() - this.cacheEntry.xform.getMyt());
/* 118 */       this.maskX = (this.cacheEntry.texData.maskX + f1);
/* 119 */       this.maskY = (this.cacheEntry.texData.maskY + f2);
/*     */     }
/*     */ 
/*     */     MaskTexData copy() {
/* 123 */       MaskTexData localMaskTexData = new MaskTexData();
/* 124 */       localMaskTexData.cacheEntry = this.cacheEntry;
/* 125 */       localMaskTexData.maskTex = this.maskTex;
/* 126 */       localMaskTexData.maskX = this.maskX;
/* 127 */       localMaskTexData.maskY = this.maskY;
/* 128 */       localMaskTexData.maskW = this.maskW;
/* 129 */       localMaskTexData.maskH = this.maskH;
/* 130 */       return localMaskTexData;
/*     */     }
/*     */ 
/*     */     void copyInto(MaskTexData paramMaskTexData) {
/* 134 */       if (paramMaskTexData == null) {
/* 135 */         throw new InternalError("MaskTexData must be non-null");
/*     */       }
/* 137 */       paramMaskTexData.cacheEntry = this.cacheEntry;
/* 138 */       paramMaskTexData.maskTex = this.maskTex;
/* 139 */       paramMaskTexData.maskX = this.maskX;
/* 140 */       paramMaskTexData.maskY = this.maskY;
/* 141 */       paramMaskTexData.maskW = this.maskW;
/* 142 */       paramMaskTexData.maskH = this.maskH;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.impl.ps.CachingShapeRepState
 * JD-Core Version:    0.6.2
 */