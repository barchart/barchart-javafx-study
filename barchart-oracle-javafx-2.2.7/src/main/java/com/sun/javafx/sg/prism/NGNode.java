/*     */ package com.sun.javafx.sg.prism;
/*     */ 
/*     */ import com.sun.javafx.geom.BaseBounds;
/*     */ import com.sun.javafx.geom.RectBounds;
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.Shape;
/*     */ import com.sun.javafx.geom.transform.Affine2D;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.javafx.geom.transform.GeneralTransform3D;
/*     */ import com.sun.javafx.sg.BaseCacheFilter;
/*     */ import com.sun.javafx.sg.BaseEffectFilter;
/*     */ import com.sun.javafx.sg.BaseNode;
/*     */ import com.sun.javafx.sg.BaseNodeEffectInput;
/*     */ import com.sun.javafx.sg.DirtyRegionContainer;
/*     */ import com.sun.javafx.sg.PGNode.CacheHint;
/*     */ import com.sun.prism.CompositeMode;
/*     */ import com.sun.prism.Graphics;
/*     */ import com.sun.prism.GraphicsPipeline;
/*     */ import com.sun.prism.RTTexture;
/*     */ import com.sun.prism.ReadbackGraphics;
/*     */ import com.sun.prism.Texture;
/*     */ import com.sun.prism.impl.PrismSettings;
/*     */ import com.sun.prism.paint.Color;
/*     */ import com.sun.scenario.effect.Blend;
/*     */ import com.sun.scenario.effect.Blend.Mode;
/*     */ import com.sun.scenario.effect.Effect;
/*     */ import com.sun.scenario.effect.Effect.AccelType;
/*     */ import com.sun.scenario.effect.FilterContext;
/*     */ import com.sun.scenario.effect.Filterable;
/*     */ import com.sun.scenario.effect.ImageData;
/*     */ import com.sun.scenario.effect.impl.prism.PrDrawable;
/*     */ import com.sun.scenario.effect.impl.prism.PrEffectHelper;
/*     */ import com.sun.scenario.effect.impl.prism.PrFilterContext;
/*     */ import java.io.PrintStream;
/*     */ 
/*     */ public abstract class NGNode extends BaseNode<Graphics>
/*     */ {
/*  41 */   private static final GraphicsPipeline pipeline = GraphicsPipeline.getPipeline();
/*     */ 
/*  44 */   private static final Boolean effectsSupported = Boolean.valueOf(pipeline == null ? false : pipeline.isEffectSupported());
/*     */ 
/*     */   protected void markCullRegions(DirtyRegionContainer paramDirtyRegionContainer, int paramInt, BaseTransform paramBaseTransform, GeneralTransform3D paramGeneralTransform3D)
/*     */   {
/*  60 */     if (paramBaseTransform.isIdentity())
/*  61 */       TEMP_BOUNDS.deriveWithNewBounds(this.transformedBounds);
/*     */     else {
/*  63 */       paramBaseTransform.transform(this.transformedBounds, TEMP_BOUNDS);
/*     */     }
/*     */ 
/*  66 */     if ((paramGeneralTransform3D != null) && 
/*  67 */       (!paramGeneralTransform3D.isIdentity())) {
/*  68 */       paramGeneralTransform3D.transform(TEMP_BOUNDS, TEMP_BOUNDS);
/*     */     }
/*     */ 
/*  71 */     TEMP_BOUNDS.deriveWithNewBounds(TEMP_BOUNDS.getMinX(), TEMP_BOUNDS.getMinY(), 0.0F, TEMP_BOUNDS.getMaxX(), TEMP_BOUNDS.getMaxY(), 0.0F);
/*     */ 
/*  78 */     this.cullingBits = 0;
/*     */ 
/*  80 */     int i = 1;
/*  81 */     for (int j = 0; j < paramDirtyRegionContainer.size(); j++) {
/*  82 */       RectBounds localRectBounds = paramDirtyRegionContainer.getDirtyRegion(j);
/*  83 */       if ((localRectBounds == null) || (localRectBounds.isEmpty())) {
/*     */         break;
/*     */       }
/*  86 */       if ((paramInt == -1) || ((paramInt & i) != 0))
/*     */       {
/*  88 */         setCullBits(TEMP_BOUNDS, j, localRectBounds);
/*     */       }
/*  90 */       i <<= 2;
/*     */     }
/*     */ 
/*  95 */     if ((this.cullingBits == 0) && ((this.dirty) || (this.childDirty))) {
/*  96 */       clearDirtyTree();
/*     */     }
/*     */ 
/*  99 */     if (this.debug)
/* 100 */       System.out.printf("%s bits: %s bounds: %s\n", new Object[] { this, Integer.toBinaryString(this.cullingBits), TEMP_BOUNDS });
/*     */   }
/*     */ 
/*     */   public void drawCullBits(Graphics paramGraphics)
/*     */   {
/* 107 */     if (this.cullingBits == 0)
/* 108 */       paramGraphics.setPaint(new Color(0.0F, 0.0F, 0.0F, 0.3F));
/*     */     else {
/* 110 */       paramGraphics.setPaint(new Color(0.0F, 1.0F, 0.0F, 0.3F));
/*     */     }
/* 112 */     paramGraphics.fillRect(this.transformedBounds.getMinX(), this.transformedBounds.getMinY(), this.transformedBounds.getWidth(), this.transformedBounds.getHeight());
/*     */   }
/*     */ 
/*     */   protected void doRender(Graphics paramGraphics)
/*     */   {
/* 127 */     int i = 0;
/* 128 */     if ((PrismSettings.dirtyOptsEnabled) && 
/* 129 */       (paramGraphics.hasPreCullingBits()))
/*     */     {
/* 131 */       if ((this.cullingBits & 3 << paramGraphics.getClipRectIndex() * 2) == 0)
/*     */       {
/* 134 */         return;
/* 135 */       }if ((this.cullingBits & 2 << paramGraphics.getClipRectIndex() * 2) != 0)
/*     */       {
/* 139 */         paramGraphics.setHasPreCullingBits(false);
/* 140 */         i = 1;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 148 */     paramGraphics.setDepthTest(isDepthTest());
/*     */ 
/* 151 */     BaseTransform localBaseTransform = paramGraphics.getTransformNoClone();
/*     */ 
/* 153 */     double d1 = localBaseTransform.getMxx();
/* 154 */     double d2 = localBaseTransform.getMxy();
/* 155 */     double d3 = localBaseTransform.getMxz();
/* 156 */     double d4 = localBaseTransform.getMxt();
/*     */ 
/* 158 */     double d5 = localBaseTransform.getMyx();
/* 159 */     double d6 = localBaseTransform.getMyy();
/* 160 */     double d7 = localBaseTransform.getMyz();
/* 161 */     double d8 = localBaseTransform.getMyt();
/*     */ 
/* 163 */     double d9 = localBaseTransform.getMzx();
/* 164 */     double d10 = localBaseTransform.getMzy();
/* 165 */     double d11 = localBaseTransform.getMzz();
/* 166 */     double d12 = localBaseTransform.getMzt();
/*     */ 
/* 175 */     paramGraphics.transform(getTransform());
/* 176 */     if (((paramGraphics instanceof ReadbackGraphics)) && (needsBlending()))
/* 177 */       renderNodeBlendMode(paramGraphics);
/* 178 */     else if (getClipNode() != null)
/* 179 */       renderClip(paramGraphics);
/* 180 */     else if (getOpacity() < 1.0F)
/* 181 */       renderOpacity(paramGraphics);
/* 182 */     else if (getCacheFilter() != null)
/* 183 */       renderCached(paramGraphics);
/* 184 */     else if ((getEffectFilter() != null) && (effectsSupported.booleanValue()))
/* 185 */       renderEffect(paramGraphics);
/*     */     else {
/* 187 */       renderContent(paramGraphics);
/*     */     }
/*     */ 
/* 190 */     if (i != 0) {
/* 191 */       paramGraphics.setHasPreCullingBits(true);
/*     */     }
/*     */ 
/* 195 */     paramGraphics.setTransform3D(d1, d2, d3, d4, d5, d6, d7, d8, d9, d10, d11, d12);
/*     */   }
/*     */ 
/*     */   private void renderContent3d(Graphics paramGraphics)
/*     */   {
/* 201 */     FilterContext localFilterContext = getFilterContext(paramGraphics);
/* 202 */     BaseBounds localBaseBounds = getContentBounds(new RectBounds(), BaseTransform.IDENTITY_TRANSFORM);
/*     */ 
/* 204 */     Rectangle localRectangle = new Rectangle(localBaseBounds);
/* 205 */     PrDrawable localPrDrawable = (PrDrawable)Effect.getCompatibleImage(localFilterContext, localRectangle.width, localRectangle.height);
/*     */ 
/* 207 */     if (localPrDrawable == null) {
/* 208 */       return;
/*     */     }
/*     */ 
/* 211 */     Graphics localGraphics = localPrDrawable.createGraphics();
/* 212 */     localGraphics.translate(-localRectangle.x, -localRectangle.y);
/* 213 */     renderContent(localGraphics);
/* 214 */     paramGraphics.drawTexture(localPrDrawable.getTextureObject(), localRectangle.x, localRectangle.y, localRectangle.width, localRectangle.height);
/*     */ 
/* 217 */     Effect.releaseCompatibleImage(localFilterContext, localPrDrawable);
/*     */   }
/*     */ 
/*     */   protected boolean needsBlending()
/*     */   {
/* 230 */     Blend.Mode localMode = getNodeBlendMode();
/* 231 */     return (localMode != null) && (localMode != Blend.Mode.SRC_OVER);
/*     */   }
/*     */ 
/*     */   private void renderNodeBlendMode(Graphics paramGraphics)
/*     */   {
/* 236 */     BaseTransform localBaseTransform = paramGraphics.getTransformNoClone();
/*     */ 
/* 238 */     BaseBounds localBaseBounds = getClippedBounds(new RectBounds(), localBaseTransform);
/* 239 */     if (localBaseBounds.isEmpty()) {
/* 240 */       clearDirtyTree();
/* 241 */       return;
/*     */     }
/*     */ 
/* 244 */     if (!isReadbackSupported(paramGraphics)) {
/* 245 */       if (getClipNode() != null)
/* 246 */         renderClip(paramGraphics);
/* 247 */       else if (getOpacity() < 1.0F)
/* 248 */         renderOpacity(paramGraphics);
/*     */       else {
/* 250 */         renderContent(paramGraphics);
/*     */       }
/* 252 */       return;
/*     */     }
/*     */ 
/* 257 */     Rectangle localRectangle = new Rectangle(localBaseBounds);
/* 258 */     localRectangle.intersectWith(PrEffectHelper.getGraphicsClipNoClone(paramGraphics));
/*     */ 
/* 261 */     FilterContext localFilterContext = getFilterContext(paramGraphics);
/* 262 */     PrDrawable localPrDrawable1 = (PrDrawable)Effect.getCompatibleImage(localFilterContext, localRectangle.width, localRectangle.height);
/*     */ 
/* 264 */     if (localPrDrawable1 == null) {
/* 265 */       clearDirtyTree();
/* 266 */       return;
/*     */     }
/* 268 */     Graphics localGraphics = localPrDrawable1.createGraphics();
/* 269 */     localGraphics.setHasPreCullingBits(paramGraphics.hasPreCullingBits());
/* 270 */     localGraphics.setClipRectIndex(paramGraphics.getClipRectIndex());
/* 271 */     localGraphics.translate(-localRectangle.x, -localRectangle.y);
/* 272 */     localGraphics.transform(localBaseTransform);
/* 273 */     if (getClipNode() != null)
/* 274 */       renderClip(paramGraphics);
/* 275 */     else if (getOpacity() < 1.0F)
/* 276 */       renderOpacity(localGraphics);
/* 277 */     else if (getCacheFilter() != null)
/* 278 */       renderCached(localGraphics);
/* 279 */     else if (getEffectFilter() != null)
/* 280 */       renderEffect(localGraphics);
/*     */     else {
/* 282 */       renderContent(localGraphics);
/*     */     }
/*     */ 
/* 287 */     RTTexture localRTTexture = ((ReadbackGraphics)paramGraphics).readBack(localRectangle);
/* 288 */     PrDrawable localPrDrawable2 = PrDrawable.create(localFilterContext, localRTTexture);
/* 289 */     Blend localBlend = new Blend(getNodeBlendMode(), new PassThrough(localPrDrawable2, localRectangle), new PassThrough(localPrDrawable1, localRectangle));
/*     */ 
/* 292 */     CompositeMode localCompositeMode = paramGraphics.getCompositeMode();
/* 293 */     paramGraphics.setTransform(null);
/* 294 */     paramGraphics.setCompositeMode(CompositeMode.SRC);
/* 295 */     PrEffectHelper.render(localBlend, paramGraphics, 0.0F, 0.0F, null);
/* 296 */     paramGraphics.setCompositeMode(localCompositeMode);
/*     */ 
/* 299 */     Effect.releaseCompatibleImage(localFilterContext, localPrDrawable1);
/* 300 */     ((ReadbackGraphics)paramGraphics).releaseReadBackBuffer(localRTTexture);
/*     */   }
/*     */ 
/*     */   private void renderRectClip(Graphics paramGraphics, NGRectangle paramNGRectangle) {
/* 304 */     Object localObject = paramNGRectangle.getShape().getBounds();
/* 305 */     if (!paramNGRectangle.getTransform().isIdentity()) {
/* 306 */       localObject = paramNGRectangle.getTransform().transform((BaseBounds)localObject, (BaseBounds)localObject);
/*     */     }
/* 308 */     BaseTransform localBaseTransform = paramGraphics.getTransformNoClone();
/* 309 */     Rectangle localRectangle = paramGraphics.getClipRectNoClone();
/* 310 */     localObject = localBaseTransform.transform((BaseBounds)localObject, (BaseBounds)localObject);
/* 311 */     ((BaseBounds)localObject).intersectWith(PrEffectHelper.getGraphicsClipNoClone(paramGraphics));
/* 312 */     if ((((BaseBounds)localObject).isEmpty()) || (((BaseBounds)localObject).getWidth() == 0.0F) || (((BaseBounds)localObject).getHeight() == 0.0F))
/*     */     {
/* 315 */       clearDirtyTree();
/* 316 */       return;
/*     */     }
/*     */ 
/* 319 */     paramGraphics.setClipRect(new Rectangle((BaseBounds)localObject));
/* 320 */     renderForClip(paramGraphics);
/* 321 */     paramGraphics.setClipRect(localRectangle);
/* 322 */     paramNGRectangle.clearDirty();
/*     */   }
/*     */ 
/*     */   private void renderClip(Graphics paramGraphics)
/*     */   {
/* 328 */     if (getClipNode().getOpacity() == 0.0D) {
/* 329 */       clearDirtyTree();
/* 330 */       return;
/*     */     }
/*     */ 
/* 334 */     BaseTransform localBaseTransform = paramGraphics.getTransformNoClone();
/*     */ 
/* 336 */     BaseBounds localBaseBounds = getClippedBounds(new RectBounds(), localBaseTransform);
/* 337 */     if (localBaseBounds.isEmpty()) {
/* 338 */       clearDirtyTree();
/* 339 */       return;
/*     */     }
/*     */ 
/* 342 */     if ((getClipNode() instanceof NGRectangle))
/*     */     {
/* 344 */       localObject1 = (NGRectangle)getClipNode();
/* 345 */       if (((NGRectangle)localObject1).isRectClip(localBaseTransform)) {
/* 346 */         renderRectClip(paramGraphics, (NGRectangle)localObject1);
/* 347 */         return;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 353 */     Object localObject1 = new Rectangle(localBaseBounds);
/* 354 */     ((Rectangle)localObject1).intersectWith(PrEffectHelper.getGraphicsClipNoClone(paramGraphics));
/*     */ 
/* 356 */     if (!localBaseTransform.is2D()) {
/* 357 */       localObject2 = paramGraphics.getClipRect();
/* 358 */       paramGraphics.setClipRect((Rectangle)localObject1);
/* 359 */       localObject3 = new NodeEffectInput((NGNode)getClipNode(), NodeEffectInput.RenderType.FULL_CONTENT);
/*     */ 
/* 362 */       localObject4 = new NodeEffectInput(this, NodeEffectInput.RenderType.CLIPPED_CONTENT);
/*     */ 
/* 365 */       localObject5 = new Blend(Blend.Mode.SRC_IN, (Effect)localObject3, (Effect)localObject4);
/* 366 */       PrEffectHelper.render((Effect)localObject5, paramGraphics, 0.0F, 0.0F, null);
/* 367 */       ((NodeEffectInput)localObject3).flush();
/* 368 */       ((NodeEffectInput)localObject4).flush();
/* 369 */       paramGraphics.setClipRect((Rectangle)localObject2);
/*     */ 
/* 376 */       clearDirtyTree();
/* 377 */       return;
/*     */     }
/*     */ 
/* 381 */     Object localObject2 = getFilterContext(paramGraphics);
/* 382 */     Object localObject3 = (PrDrawable)Effect.getCompatibleImage((FilterContext)localObject2, ((Rectangle)localObject1).width, ((Rectangle)localObject1).height);
/*     */ 
/* 384 */     if (localObject3 == null) {
/* 385 */       clearDirtyTree();
/* 386 */       return;
/*     */     }
/* 388 */     Object localObject4 = ((PrDrawable)localObject3).createGraphics();
/* 389 */     ((Graphics)localObject4).setHasPreCullingBits(paramGraphics.hasPreCullingBits());
/* 390 */     ((Graphics)localObject4).setClipRectIndex(paramGraphics.getClipRectIndex());
/* 391 */     ((Graphics)localObject4).translate(-((Rectangle)localObject1).x, -((Rectangle)localObject1).y);
/* 392 */     ((Graphics)localObject4).transform(localBaseTransform);
/* 393 */     renderForClip((Graphics)localObject4);
/*     */ 
/* 396 */     Object localObject5 = (PrDrawable)Effect.getCompatibleImage((FilterContext)localObject2, ((Rectangle)localObject1).width, ((Rectangle)localObject1).height);
/*     */ 
/* 398 */     if (localObject5 == null) {
/* 399 */       getClipNode().clearDirtyTree();
/* 400 */       Effect.releaseCompatibleImage((FilterContext)localObject2, (Filterable)localObject3);
/* 401 */       return;
/*     */     }
/* 403 */     Graphics localGraphics = ((PrDrawable)localObject5).createGraphics();
/* 404 */     localGraphics.translate(-((Rectangle)localObject1).x, -((Rectangle)localObject1).y);
/* 405 */     localGraphics.transform(localBaseTransform);
/* 406 */     getClipNode().render(localGraphics);
/*     */ 
/* 410 */     paramGraphics.setTransform(null);
/* 411 */     Blend localBlend = new Blend(Blend.Mode.SRC_IN, new PassThrough((PrDrawable)localObject5, (Rectangle)localObject1), new PassThrough((PrDrawable)localObject3, (Rectangle)localObject1));
/*     */ 
/* 414 */     PrEffectHelper.render(localBlend, paramGraphics, 0.0F, 0.0F, null);
/*     */ 
/* 417 */     Effect.releaseCompatibleImage((FilterContext)localObject2, (Filterable)localObject3);
/* 418 */     Effect.releaseCompatibleImage((FilterContext)localObject2, (Filterable)localObject5);
/*     */   }
/*     */ 
/*     */   void renderForClip(Graphics paramGraphics) {
/* 422 */     if (getOpacity() < 1.0F)
/* 423 */       renderOpacity(paramGraphics);
/* 424 */     else if (getCacheFilter() != null)
/* 425 */       renderCached(paramGraphics);
/* 426 */     else if (getEffectFilter() != null)
/* 427 */       renderEffect(paramGraphics);
/*     */     else
/* 429 */       renderContent(paramGraphics);
/*     */   }
/*     */ 
/*     */   private void renderOpacity(Graphics paramGraphics)
/*     */   {
/* 434 */     if ((getEffectFilter() != null) || (getCacheFilter() != null) || (!hasOverlappingContents()))
/*     */     {
/* 442 */       float f1 = paramGraphics.getExtraAlpha();
/* 443 */       paramGraphics.setExtraAlpha(f1 * getOpacity());
/* 444 */       if (getCacheFilter() != null)
/* 445 */         renderCached(paramGraphics);
/* 446 */       else if (getEffectFilter() != null)
/* 447 */         renderEffect(paramGraphics);
/*     */       else {
/* 449 */         renderContent(paramGraphics);
/*     */       }
/* 451 */       paramGraphics.setExtraAlpha(f1);
/* 452 */       return;
/*     */     }
/*     */ 
/* 455 */     FilterContext localFilterContext = getFilterContext(paramGraphics);
/* 456 */     BaseTransform localBaseTransform = paramGraphics.getTransformNoClone();
/* 457 */     BaseBounds localBaseBounds = getContentBounds(new RectBounds(), localBaseTransform);
/* 458 */     Rectangle localRectangle = new Rectangle(localBaseBounds);
/* 459 */     localRectangle.intersectWith(PrEffectHelper.getGraphicsClipNoClone(paramGraphics));
/* 460 */     PrDrawable localPrDrawable = (PrDrawable)Effect.getCompatibleImage(localFilterContext, localRectangle.width, localRectangle.height);
/*     */ 
/* 462 */     if (localPrDrawable == null) {
/* 463 */       return;
/*     */     }
/* 465 */     Graphics localGraphics = localPrDrawable.createGraphics();
/* 466 */     localGraphics.setHasPreCullingBits(paramGraphics.hasPreCullingBits());
/* 467 */     localGraphics.setClipRectIndex(paramGraphics.getClipRectIndex());
/* 468 */     localGraphics.translate(-localRectangle.x, -localRectangle.y);
/* 469 */     localGraphics.transform(localBaseTransform);
/* 470 */     renderContent(localGraphics);
/*     */ 
/* 473 */     paramGraphics.setTransform(null);
/* 474 */     float f2 = paramGraphics.getExtraAlpha();
/* 475 */     paramGraphics.setExtraAlpha(getOpacity() * f2);
/* 476 */     paramGraphics.drawTexture(localPrDrawable.getTextureObject(), localRectangle.x, localRectangle.y, localRectangle.width, localRectangle.height);
/* 477 */     paramGraphics.setExtraAlpha(f2);
/*     */ 
/* 479 */     Effect.releaseCompatibleImage(localFilterContext, localPrDrawable);
/*     */   }
/*     */ 
/*     */   private void renderCached(Graphics paramGraphics)
/*     */   {
/* 486 */     if ((isContentBounds2D()) && (paramGraphics.getTransformNoClone().is2D()))
/* 487 */       ((CacheFilter)getCacheFilter()).render(paramGraphics);
/*     */     else
/* 489 */       renderContent(paramGraphics);
/*     */   }
/*     */ 
/*     */   protected void renderEffect(Graphics paramGraphics)
/*     */   {
/* 494 */     ((EffectFilter)getEffectFilter()).render(paramGraphics);
/*     */   }
/*     */ 
/*     */   protected abstract void renderContent(Graphics paramGraphics);
/*     */ 
/*     */   protected abstract boolean hasOverlappingContents();
/*     */ 
/*     */   protected boolean cull(Rectangle paramRectangle, BaseTransform paramBaseTransform, GeneralTransform3D paramGeneralTransform3D)
/*     */   {
/* 503 */     if (paramRectangle == null)
/*     */     {
/* 505 */       return false;
/*     */     }
/*     */ 
/* 512 */     if (this.transformedBounds.isEmpty()) {
/* 513 */       return true;
/*     */     }
/*     */ 
/* 517 */     if (paramBaseTransform.isIdentity())
/* 518 */       TEMP_BOUNDS.deriveWithNewBounds(this.transformedBounds);
/*     */     else {
/* 520 */       paramBaseTransform.transform(this.transformedBounds, TEMP_BOUNDS);
/*     */     }
/* 522 */     if ((paramGeneralTransform3D != null) && 
/* 523 */       (!paramGeneralTransform3D.isIdentity())) {
/* 524 */       paramGeneralTransform3D.transform(TEMP_BOUNDS, TEMP_BOUNDS);
/*     */     }
/*     */ 
/* 527 */     TEMP_BOUNDS.deriveWithNewBounds(TEMP_BOUNDS.getMinX(), TEMP_BOUNDS.getMinY(), 0.0F, TEMP_BOUNDS.getMaxX(), TEMP_BOUNDS.getMaxY(), 0.0F);
/*     */ 
/* 534 */     if (TEMP_BOUNDS.disjoint(paramRectangle.x, paramRectangle.y, paramRectangle.width, paramRectangle.height)) {
/* 535 */       return true;
/*     */     }
/*     */ 
/* 539 */     return false;
/*     */   }
/*     */ 
/*     */   static RectBounds accumulate(RectBounds paramRectBounds1, RectBounds paramRectBounds2)
/*     */   {
/* 574 */     return accumulate(paramRectBounds1, paramRectBounds2, false);
/*     */   }
/*     */ 
/*     */   static RectBounds accumulate(RectBounds paramRectBounds1, RectBounds paramRectBounds2, boolean paramBoolean)
/*     */   {
/* 609 */     if ((paramRectBounds2 != null) && (paramRectBounds2.getWidth() > 0.0F) && (paramRectBounds2.getHeight() > 0.0F))
/*     */     {
/* 613 */       if (paramRectBounds1 == null) {
/* 614 */         paramRectBounds1 = paramBoolean ? paramRectBounds2 : new RectBounds(paramRectBounds2);
/*     */       }
/*     */       else
/*     */       {
/* 618 */         paramRectBounds1.unionWith(paramRectBounds2);
/*     */       }
/*     */     }
/* 621 */     return paramRectBounds1;
/*     */   }
/*     */ 
/*     */   boolean isReadbackSupported(Graphics paramGraphics) {
/* 625 */     return ((paramGraphics instanceof ReadbackGraphics)) && (((ReadbackGraphics)paramGraphics).canReadBack());
/*     */   }
/*     */ 
/*     */   protected BaseCacheFilter createCacheFilter(BaseNode paramBaseNode, PGNode.CacheHint paramCacheHint)
/*     */   {
/* 635 */     return new CacheFilter(paramBaseNode, paramCacheHint);
/*     */   }
/*     */ 
/*     */   protected BaseEffectFilter createEffectFilter(Effect paramEffect) {
/* 639 */     return new EffectFilter(paramEffect, this);
/*     */   }
/*     */ 
/*     */   static FilterContext getFilterContext(Graphics paramGraphics) {
/* 643 */     return PrFilterContext.getInstance(paramGraphics.getAssociatedScreen());
/*     */   }
/*     */ 
/*     */   private static class PassThrough extends Effect
/*     */   {
/*     */     private PrDrawable img;
/*     */     private Rectangle bounds;
/*     */ 
/*     */     PassThrough(PrDrawable paramPrDrawable, Rectangle paramRectangle)
/*     */     {
/* 769 */       this.img = paramPrDrawable;
/* 770 */       this.bounds = paramRectangle;
/*     */     }
/*     */ 
/*     */     public ImageData filter(FilterContext paramFilterContext, BaseTransform paramBaseTransform, Rectangle paramRectangle, Object paramObject, Effect paramEffect)
/*     */     {
/* 780 */       return new ImageData(paramFilterContext, this.img, new Rectangle(this.bounds));
/*     */     }
/*     */ 
/*     */     public RectBounds getBounds(BaseTransform paramBaseTransform, Effect paramEffect)
/*     */     {
/* 787 */       return new RectBounds(this.bounds);
/*     */     }
/*     */ 
/*     */     public Effect.AccelType getAccelType(FilterContext paramFilterContext)
/*     */     {
/* 792 */       return Effect.AccelType.INTRINSIC;
/*     */     }
/*     */   }
/*     */ 
/*     */   protected static class EffectFilter extends BaseEffectFilter
/*     */   {
/*     */     EffectFilter(Effect paramEffect, NGNode paramNGNode)
/*     */     {
/* 743 */       super(paramNGNode);
/*     */     }
/*     */ 
/*     */     void render(Graphics paramGraphics) {
/* 747 */       NodeEffectInput localNodeEffectInput = (NodeEffectInput)getNodeInput();
/* 748 */       PrEffectHelper.render(getEffect(), paramGraphics, 0.0F, 0.0F, localNodeEffectInput);
/* 749 */       localNodeEffectInput.flush();
/*     */     }
/*     */ 
/*     */     protected BaseNodeEffectInput createNodeEffectInput(BaseNode paramBaseNode) {
/* 753 */       return new NodeEffectInput((NGNode)paramBaseNode);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class CacheFilter extends BaseCacheFilter
/*     */   {
/* 648 */     private final RectBounds TEMP_BOUNDS = new RectBounds();
/* 649 */     private final Rectangle TEMP_RECT = new Rectangle();
/*     */ 
/*     */     CacheFilter(BaseNode paramBaseNode, PGNode.CacheHint paramCacheHint) {
/* 652 */       super(paramCacheHint);
/*     */     }
/*     */ 
/*     */     void render(Graphics paramGraphics)
/*     */     {
/* 657 */       BaseTransform localBaseTransform = paramGraphics.getTransformNoClone();
/* 658 */       FilterContext localFilterContext = NGNode.getFilterContext(paramGraphics);
/*     */ 
/* 660 */       super.render(paramGraphics, localBaseTransform, localFilterContext);
/*     */     }
/*     */ 
/*     */     protected ImageData impl_createImageData(FilterContext paramFilterContext, BaseTransform paramBaseTransform)
/*     */     {
/* 670 */       BaseBounds localBaseBounds = ((NGNode)this.node).getEffectBounds(this.TEMP_BOUNDS, paramBaseTransform);
/*     */ 
/* 672 */       this.TEMP_RECT.setBounds(localBaseBounds);
/*     */       PrDrawable localPrDrawable;
/*     */       try
/*     */       {
/* 676 */         localPrDrawable = (PrDrawable)Effect.getCompatibleImage(paramFilterContext, this.TEMP_RECT.width, this.TEMP_RECT.height);
/*     */       }
/*     */       catch (Throwable localThrowable)
/*     */       {
/* 680 */         localPrDrawable = null;
/*     */       }
/* 682 */       if (localPrDrawable != null) {
/* 683 */         Graphics localGraphics = localPrDrawable.createGraphics();
/* 684 */         localGraphics.translate(-this.TEMP_RECT.x, -this.TEMP_RECT.y);
/* 685 */         if (paramBaseTransform != null) {
/* 686 */           localGraphics.transform(paramBaseTransform);
/*     */         }
/* 688 */         if (((NGNode)this.node).getEffectFilter() != null)
/* 689 */           ((NGNode)this.node).renderEffect(localGraphics);
/*     */         else {
/* 691 */           ((NGNode)this.node).renderContent(localGraphics);
/*     */         }
/*     */       }
/* 694 */       return new ImageData(paramFilterContext, localPrDrawable, this.TEMP_RECT);
/*     */     }
/*     */ 
/*     */     protected void impl_renderNodeToScreen(Object paramObject, BaseTransform paramBaseTransform)
/*     */     {
/* 706 */       Graphics localGraphics = (Graphics)paramObject;
/* 707 */       NGNode localNGNode = (NGNode)this.node;
/*     */ 
/* 709 */       if (localNGNode.getEffectFilter() != null)
/* 710 */         localNGNode.renderEffect(localGraphics);
/*     */       else
/* 712 */         localNGNode.renderContent(localGraphics);
/*     */     }
/*     */ 
/*     */     protected void impl_renderCacheToScreen(Object paramObject, Filterable paramFilterable, double paramDouble1, double paramDouble2)
/*     */     {
/* 724 */       Graphics localGraphics = (Graphics)paramObject;
/*     */ 
/* 726 */       localGraphics.setTransform(this.screenXform.getMxx(), this.screenXform.getMyx(), this.screenXform.getMxy(), this.screenXform.getMyy(), paramDouble1, paramDouble2);
/*     */ 
/* 731 */       localGraphics.translate((float)this.cachedX, (float)this.cachedY);
/* 732 */       Texture localTexture = ((PrDrawable)paramFilterable).getTextureObject();
/* 733 */       Rectangle localRectangle = this.cachedImageData.getUntransformedBounds();
/* 734 */       localGraphics.drawTexture(localTexture, 0.0F, 0.0F, localRectangle.width, localRectangle.height);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.sg.prism.NGNode
 * JD-Core Version:    0.6.2
 */