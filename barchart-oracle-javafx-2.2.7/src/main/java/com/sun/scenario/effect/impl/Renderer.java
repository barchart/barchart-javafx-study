/*     */ package com.sun.scenario.effect.impl;
/*     */ 
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.transform.Affine2D;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.scenario.effect.Effect;
/*     */ import com.sun.scenario.effect.Effect.AccelType;
/*     */ import com.sun.scenario.effect.FilterContext;
/*     */ import com.sun.scenario.effect.Filterable;
/*     */ import com.sun.scenario.effect.FloatMap;
/*     */ import com.sun.scenario.effect.ImageData;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ 
/*     */ public abstract class Renderer
/*     */ {
/*     */   public static final String rootPkg = "com.sun.scenario.effect";
/*  81 */   private static final Map<FilterContext, Renderer> rendererMap = new HashMap(1);
/*     */ 
/*  83 */   private Map<String, EffectPeer> peerCache = Collections.synchronizedMap(new HashMap(5));
/*     */   private final ImagePool imagePool;
/*     */   protected static boolean verbose;
/*     */ 
/*     */   protected Renderer()
/*     */   {
/*  96 */     this.imagePool = new ImagePool();
/*     */   }
/*     */ 
/*     */   public abstract Effect.AccelType getAccelType();
/*     */ 
/*     */   public abstract Filterable createCompatibleImage(int paramInt1, int paramInt2);
/*     */ 
/*     */   public Filterable getCompatibleImage(int paramInt1, int paramInt2)
/*     */   {
/* 118 */     return this.imagePool.checkOut(this, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   public void releaseCompatibleImage(Filterable paramFilterable) {
/* 122 */     this.imagePool.checkIn(paramFilterable);
/*     */   }
/*     */ 
/*     */   public void releasePurgatory()
/*     */   {
/* 130 */     this.imagePool.releasePurgatory();
/*     */   }
/*     */ 
/*     */   public abstract void clearImage(Filterable paramFilterable);
/*     */ 
/*     */   public abstract ImageData createImageData(FilterContext paramFilterContext, Filterable paramFilterable);
/*     */ 
/*     */   public ImageData transform(FilterContext paramFilterContext, ImageData paramImageData, int paramInt1, int paramInt2)
/*     */   {
/* 157 */     if (!paramImageData.getTransform().isIdentity()) {
/* 158 */       throw new InternalError("transform by powers of 2 requires untransformed source");
/*     */     }
/* 160 */     if ((paramInt1 | paramInt2) == 0) {
/* 161 */       return paramImageData;
/*     */     }
/* 163 */     Affine2D localAffine2D = new Affine2D();
/*     */     double d2;
/* 166 */     while ((paramInt1 < -1) || (paramInt2 < -1)) {
/* 167 */       Rectangle localRectangle1 = paramImageData.getUntransformedBounds();
/* 168 */       Rectangle localRectangle2 = new Rectangle(localRectangle1);
/* 169 */       d2 = 1.0D;
/* 170 */       double d3 = 1.0D;
/* 171 */       if (paramInt1 < 0)
/*     */       {
/* 173 */         d2 = 0.5D;
/* 174 */         localRectangle2.width = ((localRectangle1.width + 1) / 2);
/* 175 */         localRectangle2.x /= 2;
/* 176 */         paramInt1++;
/*     */       }
/* 178 */       if (paramInt2 < 0)
/*     */       {
/* 180 */         d3 = 0.5D;
/* 181 */         localRectangle2.height = ((localRectangle1.height + 1) / 2);
/* 182 */         localRectangle2.y /= 2;
/* 183 */         paramInt2++;
/*     */       }
/* 185 */       localAffine2D.setToScale(d2, d3);
/* 186 */       paramImageData = transform(paramFilterContext, paramImageData, localAffine2D, localRectangle1, localRectangle2);
/*     */     }
/* 188 */     if ((paramInt1 | paramInt2) != 0)
/*     */     {
/* 190 */       double d1 = paramInt1 < 0 ? 0.5D : 1 << paramInt1;
/* 191 */       d2 = paramInt2 < 0 ? 0.5D : 1 << paramInt2;
/* 192 */       localAffine2D.setToScale(d1, d2);
/* 193 */       paramImageData = paramImageData.transform(localAffine2D);
/*     */     }
/* 195 */     return paramImageData;
/*     */   }
/*     */ 
/*     */   public abstract Filterable transform(FilterContext paramFilterContext, Filterable paramFilterable, BaseTransform paramBaseTransform, Rectangle paramRectangle1, Rectangle paramRectangle2);
/*     */ 
/*     */   public abstract ImageData transform(FilterContext paramFilterContext, ImageData paramImageData, BaseTransform paramBaseTransform, Rectangle paramRectangle1, Rectangle paramRectangle2);
/*     */ 
/*     */   public Object createFloatTexture(int paramInt1, int paramInt2)
/*     */   {
/* 211 */     throw new InternalError();
/*     */   }
/*     */   public void updateFloatTexture(Object paramObject, FloatMap paramFloatMap) {
/* 214 */     throw new InternalError();
/*     */   }
/*     */ 
/*     */   public final synchronized EffectPeer getPeerInstance(FilterContext paramFilterContext, String paramString, int paramInt)
/*     */   {
/* 232 */     EffectPeer localEffectPeer = (EffectPeer)this.peerCache.get(paramString);
/* 233 */     if (localEffectPeer != null) {
/* 234 */       return localEffectPeer;
/*     */     }
/*     */ 
/* 238 */     if (paramInt > 0) {
/* 239 */       localEffectPeer = (EffectPeer)this.peerCache.get(paramString + "_" + paramInt);
/* 240 */       if (localEffectPeer != null) {
/* 241 */         return localEffectPeer;
/*     */       }
/*     */     }
/*     */ 
/* 245 */     localEffectPeer = createPeer(paramFilterContext, paramString, paramInt);
/* 246 */     if (localEffectPeer == null) {
/* 247 */       throw new RuntimeException("Could not create peer  " + paramString + " for renderer " + this);
/*     */     }
/*     */ 
/* 251 */     this.peerCache.put(localEffectPeer.getUniqueName(), localEffectPeer);
/*     */ 
/* 253 */     return localEffectPeer;
/*     */   }
/*     */ 
/*     */   public abstract RendererState getRendererState();
/*     */ 
/*     */   protected abstract EffectPeer createPeer(FilterContext paramFilterContext, String paramString, int paramInt);
/*     */ 
/*     */   protected Collection<EffectPeer> getPeers()
/*     */   {
/* 280 */     return this.peerCache.values();
/*     */   }
/*     */ 
/*     */   protected static Renderer getSoftwareRenderer()
/*     */   {
/* 291 */     return RendererFactory.getSoftwareRenderer();
/*     */   }
/*     */ 
/*     */   protected abstract Renderer getBackupRenderer();
/*     */ 
/*     */   protected Renderer getRendererForSize(Effect paramEffect, int paramInt1, int paramInt2)
/*     */   {
/* 316 */     return this;
/*     */   }
/*     */ 
/*     */   public static synchronized Renderer getRenderer(FilterContext paramFilterContext)
/*     */   {
/* 329 */     if (paramFilterContext == null) {
/* 330 */       throw new IllegalArgumentException("FilterContext must be non-null");
/*     */     }
/*     */ 
/* 333 */     Renderer localRenderer = (Renderer)rendererMap.get(paramFilterContext);
/* 334 */     if (localRenderer != null) {
/* 335 */       if (localRenderer.getRendererState() == RendererState.OK) {
/* 336 */         return localRenderer;
/*     */       }
/* 338 */       if (localRenderer.getRendererState() == RendererState.LOST)
/*     */       {
/* 343 */         return localRenderer.getBackupRenderer();
/*     */       }
/* 345 */       if (localRenderer.getRendererState() == RendererState.DISPOSED) {
/* 346 */         localRenderer = null;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 353 */     if (localRenderer == null)
/*     */     {
/* 355 */       Collection localCollection = rendererMap.values();
/* 356 */       for (Object localObject1 = localCollection.iterator(); ((Iterator)localObject1).hasNext(); )
/*     */       {
/* 358 */         localObject2 = (Renderer)((Iterator)localObject1).next();
/* 359 */         if (((Renderer)localObject2).getRendererState() == RendererState.DISPOSED) {
/* 360 */           ((Renderer)localObject2).imagePool.dispose();
/* 361 */           ((Iterator)localObject1).remove();
/*     */         }
/*     */       }
/*     */       Object localObject2;
/* 365 */       localRenderer = RendererFactory.createRenderer(paramFilterContext);
/* 366 */       if (localRenderer == null) {
/* 367 */         throw new RuntimeException("Error creating a Renderer");
/*     */       }
/* 369 */       if (verbose) {
/* 370 */         localObject1 = localRenderer.getClass().getName();
/* 371 */         localObject2 = ((String)localObject1).substring(((String)localObject1).lastIndexOf(".") + 1);
/* 372 */         Object localObject3 = paramFilterContext.getReferent();
/* 373 */         System.out.println("Created " + (String)localObject2 + " (AccelType=" + localRenderer.getAccelType() + ") for " + localObject3);
/*     */       }
/*     */ 
/* 378 */       rendererMap.put(paramFilterContext, localRenderer);
/*     */     }
/* 380 */     return localRenderer;
/*     */   }
/*     */ 
/*     */   public static Renderer getRenderer(FilterContext paramFilterContext, Effect paramEffect, int paramInt1, int paramInt2)
/*     */   {
/* 395 */     return getRenderer(paramFilterContext).getRendererForSize(paramEffect, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   public abstract boolean isImageDataCompatible(ImageData paramImageData);
/*     */ 
/*     */   static
/*     */   {
/*     */     try
/*     */     {
/*  90 */       verbose = Boolean.getBoolean("decora.verbose");
/*     */     }
/*     */     catch (SecurityException localSecurityException)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public static enum RendererState
/*     */   {
/*  69 */     OK, 
/*     */ 
/*  73 */     LOST, 
/*     */ 
/*  77 */     DISPOSED;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.Renderer
 * JD-Core Version:    0.6.2
 */