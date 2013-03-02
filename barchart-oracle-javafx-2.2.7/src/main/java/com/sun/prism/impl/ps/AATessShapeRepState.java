/*     */ package com.sun.prism.impl.ps;
/*     */ 
/*     */ import com.sun.javafx.geom.RectBounds;
/*     */ import com.sun.javafx.geom.Shape;
/*     */ import com.sun.prism.BasicStroke;
/*     */ import com.sun.prism.Graphics;
/*     */ import com.sun.prism.ResourceFactory;
/*     */ import com.sun.prism.impl.Disposer;
/*     */ import com.sun.prism.impl.Disposer.Record;
/*     */ import com.sun.prism.impl.VertexBuffer;
/*     */ import com.sun.prism.paint.Color;
/*     */ import com.sun.prism.paint.Gradient;
/*     */ import com.sun.prism.paint.Paint;
/*     */ import com.sun.prism.paint.Paint.Type;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ class AATessShapeRepState
/*     */ {
/* 231 */   private static final AATesselatorImpl tess = new AATesselatorImpl();
/* 232 */   private static final GeomCache geomCache = new GeomCache(null);
/*     */   private final GeomData geomData;
/* 235 */   private Boolean useGeom = null;
/*     */ 
/* 237 */   private final Object disposerReferent = new Object();
/*     */   private final Disposer.Record disposerRecord;
/*     */ 
/*     */   AATessShapeRepState()
/*     */   {
/* 241 */     this.geomData = new GeomData(null);
/* 242 */     this.disposerRecord = new AATessDisposerRecord(this.geomData, null);
/* 243 */     Disposer.addRecord(this.disposerReferent, this.disposerRecord);
/*     */   }
/*     */ 
/*     */   void invalidate()
/*     */   {
/* 248 */     geomCache.unref(this.geomData);
/* 249 */     this.useGeom = null;
/*     */   }
/*     */ 
/*     */   void render(Graphics paramGraphics, Shape paramShape, BasicStroke paramBasicStroke, float paramFloat1, float paramFloat2) {
/* 253 */     BaseShaderGraphics localBaseShaderGraphics = (BaseShaderGraphics)paramGraphics;
/* 254 */     Paint localPaint = localBaseShaderGraphics.getPaint();
/* 255 */     float f1 = localBaseShaderGraphics.getExtraAlpha();
/*     */ 
/* 257 */     int i = 0;
/* 258 */     if (this.useGeom == null) {
/* 259 */       if (paramBasicStroke != null) {
/* 260 */         paramShape = paramBasicStroke.createStrokedShape(paramShape);
/*     */       }
/* 262 */       localObject1 = localBaseShaderGraphics.getContext();
/* 263 */       localObject2 = paramShape.getBounds();
/* 264 */       geomCache.get((BaseShaderContext)localObject1, this.geomData, paramShape, (RectBounds)localObject2);
/* 265 */       if (this.geomData.vbSolid != null) {
/* 266 */         this.useGeom = Boolean.TRUE;
/* 267 */         i = 1;
/*     */       } else {
/* 269 */         this.useGeom = Boolean.FALSE;
/*     */       }
/*     */     }
/* 272 */     if (this.useGeom == Boolean.FALSE)
/*     */     {
/* 277 */       paramGraphics.translate(paramFloat1, paramFloat2);
/* 278 */       paramGraphics.fill(paramShape);
/* 279 */       paramGraphics.translate(-paramFloat1, -paramFloat2);
/* 280 */       return;
/*     */     }
/*     */ 
/* 283 */     Object localObject1 = this.geomData.vbSolid;
/* 284 */     Object localObject2 = this.geomData.vbCurve;
/* 285 */     int j = this.geomData.numSolidVerts;
/* 286 */     int k = this.geomData.numCurveVerts;
/*     */ 
/* 288 */     if ((i != 0) || (localPaint != this.geomData.cacheEntry.lastPaint) || (f1 != this.geomData.cacheEntry.lastExtraAlpha))
/*     */     {
/* 292 */       if (localPaint.getType() == Paint.Type.COLOR) {
/* 293 */         ((VertexBuffer)localObject1).setPerVertexColor((Color)localPaint, f1);
/* 294 */         ((VertexBuffer)localObject1).updateVertexColors(j);
/* 295 */         ((VertexBuffer)localObject2).setPerVertexColor((Color)localPaint, f1);
/* 296 */         ((VertexBuffer)localObject2).updateVertexColors(k);
/*     */       } else {
/* 298 */         ((VertexBuffer)localObject1).setPerVertexColor(f1);
/* 299 */         ((VertexBuffer)localObject1).updateVertexColors(j);
/* 300 */         ((VertexBuffer)localObject2).setPerVertexColor(f1);
/* 301 */         ((VertexBuffer)localObject2).updateVertexColors(k);
/*     */       }
/* 303 */       this.geomData.cacheEntry.lastPaint = localPaint;
/* 304 */       this.geomData.cacheEntry.lastExtraAlpha = f1;
/*     */     }
/*     */ 
/* 307 */     float f2 = 0.0F; float f3 = 0.0F; float f4 = 0.0F; float f5 = 0.0F;
/* 308 */     if ((localPaint.getType().isGradient()) && (((Gradient)localPaint).isProportional())) {
/* 309 */       RectBounds localRectBounds = paramShape.getBounds();
/* 310 */       f2 = localRectBounds.getMinX();
/* 311 */       f3 = localRectBounds.getMinY();
/* 312 */       f4 = localRectBounds.getWidth();
/* 313 */       f5 = localRectBounds.getHeight();
/*     */     }
/*     */ 
/* 318 */     localBaseShaderGraphics.translate(paramFloat1, paramFloat2);
/* 319 */     if (j > 0) {
/* 320 */       localBaseShaderGraphics.fillTriangles((VertexBuffer)localObject1, j, f2, f3, f4, f5);
/*     */     }
/* 322 */     if (k > 0) {
/* 323 */       localBaseShaderGraphics.fillCubicCurves((VertexBuffer)localObject2, k, f2, f3, f4, f5);
/*     */     }
/* 325 */     localBaseShaderGraphics.translate(-paramFloat1, -paramFloat2);
/*     */   }
/*     */ 
/*     */   private static class AATessDisposerRecord implements Disposer.Record {
/*     */     private AATessShapeRepState.GeomData geomData;
/*     */ 
/*     */     private AATessDisposerRecord(AATessShapeRepState.GeomData paramGeomData) {
/* 332 */       this.geomData = paramGeomData;
/*     */     }
/*     */ 
/*     */     public void dispose()
/*     */     {
/* 337 */       if (this.geomData != null) {
/* 338 */         AATessShapeRepState.geomCache.unref(this.geomData);
/* 339 */         this.geomData = null;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class GeomCache
/*     */   {
/* 129 */     private final List<AATessShapeRepState.CacheEntry> entries = new ArrayList();
/*     */ 
/*     */     void get(BaseShaderContext paramBaseShaderContext, AATessShapeRepState.GeomData paramGeomData, Shape paramShape, RectBounds paramRectBounds)
/*     */     {
/* 135 */       if (paramGeomData == null) {
/* 136 */         throw new InternalError("GeomData must be non-null");
/*     */       }
/* 138 */       if (paramGeomData.cacheEntry != null) {
/* 139 */         throw new InternalError("CacheEntry should already be null");
/*     */       }
/*     */ 
/* 143 */       for (int i = 0; i < this.entries.size(); i++) {
/* 144 */         localObject = (AATessShapeRepState.CacheEntry)this.entries.get(i);
/*     */ 
/* 147 */         if (dimensionsAreSimilar(paramRectBounds, ((AATessShapeRepState.CacheEntry)localObject).shapeBounds))
/*     */         {
/* 150 */           if (((AATessShapeRepState.CacheEntry)localObject).shape.equals(paramShape))
/*     */           {
/* 153 */             localObject.refCount += 1;
/* 154 */             ((AATessShapeRepState.CacheEntry)localObject).geomData.copyInto(paramGeomData);
/* 155 */             paramGeomData.cacheEntry = ((AATessShapeRepState.CacheEntry)localObject);
/* 156 */             return;
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 162 */       VertexBuffer localVertexBuffer = paramBaseShaderContext.getResourceFactory().createVertexBuffer(16);
/* 163 */       Object localObject = paramBaseShaderContext.getResourceFactory().createVertexBuffer(16);
/* 164 */       int[] arrayOfInt = AATessShapeRepState.tess.generate(paramShape, localVertexBuffer, (VertexBuffer)localObject);
/* 165 */       if (arrayOfInt != null)
/*     */       {
/* 167 */         paramGeomData.vbSolid = localVertexBuffer;
/* 168 */         paramGeomData.vbCurve = ((VertexBuffer)localObject);
/* 169 */         paramGeomData.numSolidVerts = arrayOfInt[0];
/* 170 */         paramGeomData.numCurveVerts = arrayOfInt[1];
/*     */       }
/*     */       else
/*     */       {
/* 176 */         paramGeomData.vbSolid = null;
/* 177 */         paramGeomData.vbCurve = null;
/* 178 */         paramGeomData.numSolidVerts = 0;
/* 179 */         paramGeomData.numCurveVerts = 0;
/*     */       }
/*     */ 
/* 186 */       AATessShapeRepState.CacheEntry localCacheEntry = new AATessShapeRepState.CacheEntry(null);
/*     */ 
/* 189 */       localCacheEntry.shape = paramShape.copy();
/* 190 */       localCacheEntry.shapeBounds = new RectBounds(paramRectBounds);
/* 191 */       localCacheEntry.geomData = paramGeomData.copy();
/* 192 */       localCacheEntry.refCount = 1;
/* 193 */       paramGeomData.cacheEntry = localCacheEntry;
/* 194 */       this.entries.add(localCacheEntry);
/*     */     }
/*     */ 
/*     */     void unref(AATessShapeRepState.GeomData paramGeomData) {
/* 198 */       if (paramGeomData == null) {
/* 199 */         throw new InternalError("GeomData must be non-null");
/*     */       }
/* 201 */       AATessShapeRepState.CacheEntry localCacheEntry = paramGeomData.cacheEntry;
/* 202 */       if (localCacheEntry == null) {
/* 203 */         return;
/*     */       }
/* 205 */       paramGeomData.cacheEntry = null;
/* 206 */       paramGeomData.vbSolid = null;
/* 207 */       paramGeomData.vbCurve = null;
/* 208 */       localCacheEntry.refCount -= 1;
/* 209 */       if (localCacheEntry.refCount <= 0) {
/* 210 */         localCacheEntry.shape = null;
/* 211 */         localCacheEntry.shapeBounds = null;
/* 212 */         localCacheEntry.geomData.vbSolid = null;
/* 213 */         localCacheEntry.geomData.vbCurve = null;
/* 214 */         localCacheEntry.geomData = null;
/* 215 */         this.entries.remove(localCacheEntry);
/*     */       }
/*     */     }
/*     */ 
/*     */     private static boolean dimensionsAreSimilar(RectBounds paramRectBounds1, RectBounds paramRectBounds2)
/*     */     {
/* 225 */       return (Math.abs(paramRectBounds1.getWidth() - paramRectBounds2.getWidth()) < 0.001D) && (Math.abs(paramRectBounds1.getHeight() - paramRectBounds2.getHeight()) < 0.001D);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class CacheEntry
/*     */   {
/*     */     Shape shape;
/*     */     RectBounds shapeBounds;
/*     */     AATessShapeRepState.GeomData geomData;
/*     */     int refCount;
/*     */     Paint lastPaint;
/* 125 */     float lastExtraAlpha = -1.0F;
/*     */   }
/*     */ 
/*     */   private static class GeomData
/*     */   {
/*     */     AATessShapeRepState.CacheEntry cacheEntry;
/*     */     VertexBuffer vbSolid;
/*     */     VertexBuffer vbCurve;
/*     */     int numSolidVerts;
/*     */     int numCurveVerts;
/*     */ 
/*     */     GeomData copy()
/*     */     {
/*  97 */       GeomData localGeomData = new GeomData();
/*  98 */       localGeomData.cacheEntry = this.cacheEntry;
/*  99 */       localGeomData.vbSolid = this.vbSolid;
/* 100 */       localGeomData.vbCurve = this.vbCurve;
/* 101 */       localGeomData.numSolidVerts = this.numSolidVerts;
/* 102 */       localGeomData.numCurveVerts = this.numCurveVerts;
/* 103 */       return localGeomData;
/*     */     }
/*     */ 
/*     */     void copyInto(GeomData paramGeomData) {
/* 107 */       if (paramGeomData == null) {
/* 108 */         throw new InternalError("MaskTexData must be non-null");
/*     */       }
/* 110 */       paramGeomData.cacheEntry = this.cacheEntry;
/* 111 */       paramGeomData.vbSolid = this.vbSolid;
/* 112 */       paramGeomData.vbCurve = this.vbCurve;
/* 113 */       paramGeomData.numSolidVerts = this.numSolidVerts;
/* 114 */       paramGeomData.numCurveVerts = this.numCurveVerts;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.impl.ps.AATessShapeRepState
 * JD-Core Version:    0.6.2
 */