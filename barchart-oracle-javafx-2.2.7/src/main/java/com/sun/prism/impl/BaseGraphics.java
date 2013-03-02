/*     */ package com.sun.prism.impl;
/*     */ 
/*     */ import com.sun.glass.ui.Screen;
/*     */ import com.sun.javafx.geom.Ellipse2D;
/*     */ import com.sun.javafx.geom.Line2D;
/*     */ import com.sun.javafx.geom.RectBounds;
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.RoundRectangle2D;
/*     */ import com.sun.javafx.geom.Shape;
/*     */ import com.sun.javafx.geom.transform.Affine3D;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.javafx.geom.transform.GeneralTransform3D;
/*     */ import com.sun.prism.BasicStroke;
/*     */ import com.sun.prism.CompositeMode;
/*     */ import com.sun.prism.PixelFormat;
/*     */ import com.sun.prism.RectShadowGraphics;
/*     */ import com.sun.prism.RenderTarget;
/*     */ import com.sun.prism.ResourceFactory;
/*     */ import com.sun.prism.Texture;
/*     */ import com.sun.prism.Texture.WrapMode;
/*     */ import com.sun.prism.camera.PrismCameraImpl;
/*     */ import com.sun.prism.camera.PrismParallelCameraImpl;
/*     */ import com.sun.prism.paint.Color;
/*     */ import com.sun.prism.paint.Paint;
/*     */ import com.sun.prism.paint.Paint.Type;
/*     */ 
/*     */ public abstract class BaseGraphics
/*     */   implements RectShadowGraphics
/*     */ {
/*  33 */   private static final PrismCameraImpl DEFAULT_CAMERA = PrismParallelCameraImpl.getInstance();
/*  34 */   private static final BasicStroke DEFAULT_STROKE = new BasicStroke(1.0F, 2, 0, 10.0F);
/*     */ 
/*  36 */   private static final Paint DEFAULT_PAINT = Color.WHITE;
/*     */ 
/*  38 */   protected static final RoundRectangle2D scratchRRect = new RoundRectangle2D();
/*  39 */   protected static final Ellipse2D scratchEllipse = new Ellipse2D();
/*  40 */   protected static final Line2D scratchLine = new Line2D();
/*  41 */   protected static final BaseTransform IDENT = BaseTransform.IDENTITY_TRANSFORM;
/*     */ 
/*  44 */   private final Affine3D transform3D = new Affine3D();
/*  45 */   private PrismCameraImpl camera = DEFAULT_CAMERA;
/*     */   private RectBounds devClipRect;
/*     */   private RectBounds finalClipRect;
/*  48 */   protected RectBounds nodeBounds = null;
/*     */   private Rectangle clipRect;
/*     */   private int clipRectIndex;
/*  51 */   private boolean hasPreCullingBits = false;
/*  52 */   private float extraAlpha = 1.0F;
/*     */   private CompositeMode compMode;
/*  55 */   private boolean depthBuffer = false;
/*  56 */   private boolean depthTest = false;
/*  57 */   protected Paint paint = DEFAULT_PAINT;
/*  58 */   protected BasicStroke stroke = DEFAULT_STROKE;
/*     */ 
/*  60 */   protected boolean isSimpleTranslate = true;
/*     */   protected float transX;
/*     */   protected float transY;
/*     */   private final BaseContext context;
/*     */   private final RenderTarget renderTarget;
/*     */   private GeneralTransform3D pvTx;
/*     */   private static final double samplingEpsilon = 0.00390625D;
/*     */ 
/*     */   protected BaseGraphics(BaseContext paramBaseContext, RenderTarget paramRenderTarget)
/*     */   {
/*  69 */     this.context = paramBaseContext;
/*  70 */     this.renderTarget = paramRenderTarget;
/*  71 */     int i = paramRenderTarget.getContentX();
/*  72 */     int j = paramRenderTarget.getContentY();
/*  73 */     this.devClipRect = new RectBounds(i, j, paramRenderTarget.getContentWidth() - i, paramRenderTarget.getContentHeight() - j);
/*     */ 
/*  76 */     this.finalClipRect = new RectBounds(this.devClipRect);
/*  77 */     this.compMode = CompositeMode.SRC_OVER;
/*  78 */     if (paramBaseContext != null)
/*     */     {
/*  88 */       paramBaseContext.setRenderTarget(this);
/*     */     }
/*     */   }
/*     */ 
/*     */   public RenderTarget getRenderTarget() {
/*  93 */     return this.renderTarget;
/*     */   }
/*     */ 
/*     */   public Screen getAssociatedScreen() {
/*  97 */     return this.context.getAssociatedScreen();
/*     */   }
/*     */ 
/*     */   public ResourceFactory getResourceFactory() {
/* 101 */     return this.context.getResourceFactory();
/*     */   }
/*     */ 
/*     */   public BaseTransform getTransformNoClone() {
/* 105 */     return this.transform3D;
/*     */   }
/*     */ 
/*     */   public void setTransform(BaseTransform paramBaseTransform) {
/* 109 */     if (paramBaseTransform == null)
/* 110 */       this.transform3D.setToIdentity();
/*     */     else {
/* 112 */       this.transform3D.setTransform(paramBaseTransform);
/*     */     }
/* 114 */     validateTransformAndPaint();
/*     */   }
/*     */ 
/*     */   public void setTransform(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6)
/*     */   {
/* 121 */     this.transform3D.setTransform(paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6);
/* 122 */     validateTransformAndPaint();
/*     */   }
/*     */ 
/*     */   public void setTransform3D(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, double paramDouble9, double paramDouble10, double paramDouble11, double paramDouble12)
/*     */   {
/* 129 */     this.transform3D.setTransform(paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6, paramDouble7, paramDouble8, paramDouble9, paramDouble10, paramDouble11, paramDouble12);
/*     */ 
/* 132 */     validateTransformAndPaint();
/*     */   }
/*     */ 
/*     */   public void transform(BaseTransform paramBaseTransform) {
/* 136 */     this.transform3D.concatenate(paramBaseTransform);
/* 137 */     validateTransformAndPaint();
/*     */   }
/*     */ 
/*     */   public void translate(float paramFloat1, float paramFloat2) {
/* 141 */     if ((paramFloat1 != 0.0F) || (paramFloat2 != 0.0F)) {
/* 142 */       this.transform3D.translate(paramFloat1, paramFloat2);
/* 143 */       validateTransformAndPaint();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void translate(float paramFloat1, float paramFloat2, float paramFloat3) {
/* 148 */     if ((paramFloat1 != 0.0F) || (paramFloat2 != 0.0F) || (paramFloat3 != 0.0F)) {
/* 149 */       this.transform3D.translate(paramFloat1, paramFloat2, paramFloat3);
/* 150 */       validateTransformAndPaint();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void scale(float paramFloat1, float paramFloat2) {
/* 155 */     if ((paramFloat1 != 1.0F) || (paramFloat2 != 1.0F)) {
/* 156 */       this.transform3D.scale(paramFloat1, paramFloat2);
/* 157 */       validateTransformAndPaint();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void scale(float paramFloat1, float paramFloat2, float paramFloat3) {
/* 162 */     if ((paramFloat1 != 1.0F) || (paramFloat2 != 1.0F) || (paramFloat3 != 1.0F)) {
/* 163 */       this.transform3D.scale(paramFloat1, paramFloat2, paramFloat3);
/* 164 */       validateTransformAndPaint();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setWindowProjViewTx(GeneralTransform3D paramGeneralTransform3D) {
/* 169 */     this.pvTx = paramGeneralTransform3D;
/*     */   }
/*     */ 
/*     */   public GeneralTransform3D getWindowProjViewTxNoClone() {
/* 173 */     return this.pvTx;
/*     */   }
/*     */ 
/*     */   public void setClipRectIndex(int paramInt) {
/* 177 */     this.clipRectIndex = paramInt;
/*     */   }
/*     */   public int getClipRectIndex() {
/* 180 */     return this.clipRectIndex;
/*     */   }
/*     */ 
/*     */   public void setHasPreCullingBits(boolean paramBoolean) {
/* 184 */     this.hasPreCullingBits = paramBoolean;
/*     */   }
/*     */ 
/*     */   public boolean hasPreCullingBits() {
/* 188 */     return this.hasPreCullingBits;
/*     */   }
/*     */ 
/*     */   private void validateTransformAndPaint() {
/* 192 */     if ((this.transform3D.isTranslateOrIdentity()) && (this.paint.getType() == Paint.Type.COLOR))
/*     */     {
/* 197 */       this.isSimpleTranslate = true;
/* 198 */       this.transX = ((float)this.transform3D.getMxt());
/* 199 */       this.transY = ((float)this.transform3D.getMyt());
/*     */     } else {
/* 201 */       this.isSimpleTranslate = false;
/* 202 */       this.transX = 0.0F;
/* 203 */       this.transY = 0.0F;
/*     */     }
/*     */   }
/*     */ 
/*     */   public PrismCameraImpl getCameraNoClone() {
/* 208 */     return this.camera;
/*     */   }
/*     */ 
/*     */   public boolean hasOrthoCamera() {
/* 212 */     return this.camera == DEFAULT_CAMERA;
/*     */   }
/*     */ 
/*     */   public void setDepthTest(boolean paramBoolean) {
/* 216 */     this.depthTest = paramBoolean;
/*     */   }
/*     */ 
/*     */   public boolean isDepthTest() {
/* 220 */     return this.depthTest;
/*     */   }
/*     */ 
/*     */   public void setDepthBuffer(boolean paramBoolean) {
/* 224 */     this.depthBuffer = paramBoolean;
/*     */   }
/*     */ 
/*     */   public boolean isDepthBuffer() {
/* 228 */     return this.depthBuffer;
/*     */   }
/*     */ 
/*     */   public void setCamera(PrismCameraImpl paramPrismCameraImpl) {
/* 232 */     this.camera = paramPrismCameraImpl;
/*     */   }
/*     */ 
/*     */   public Rectangle getClipRect() {
/* 236 */     return this.clipRect != null ? new Rectangle(this.clipRect) : null;
/*     */   }
/*     */ 
/*     */   public Rectangle getClipRectNoClone() {
/* 240 */     return this.clipRect;
/*     */   }
/*     */ 
/*     */   public RectBounds getFinalClipNoClone() {
/* 244 */     return this.finalClipRect;
/*     */   }
/*     */ 
/*     */   public void setClipRect(Rectangle paramRectangle) {
/* 248 */     this.finalClipRect.setBounds(this.devClipRect);
/* 249 */     if (paramRectangle == null) {
/* 250 */       this.clipRect = null;
/*     */     } else {
/* 252 */       this.clipRect = new Rectangle(paramRectangle);
/* 253 */       this.finalClipRect.intersectWith(paramRectangle);
/*     */     }
/*     */   }
/*     */ 
/*     */   public float getExtraAlpha() {
/* 258 */     return this.extraAlpha;
/*     */   }
/*     */ 
/*     */   public void setExtraAlpha(float paramFloat) {
/* 262 */     this.extraAlpha = paramFloat;
/*     */   }
/*     */ 
/*     */   public CompositeMode getCompositeMode() {
/* 266 */     return this.compMode;
/*     */   }
/*     */ 
/*     */   public void setCompositeMode(CompositeMode paramCompositeMode) {
/* 270 */     this.compMode = paramCompositeMode;
/*     */   }
/*     */ 
/*     */   public Paint getPaint() {
/* 274 */     return this.paint;
/*     */   }
/*     */ 
/*     */   public void setPaint(Paint paramPaint) {
/* 278 */     this.paint = paramPaint;
/* 279 */     validateTransformAndPaint();
/*     */   }
/*     */ 
/*     */   public BasicStroke getStroke() {
/* 283 */     return this.stroke;
/*     */   }
/*     */ 
/*     */   public void setStroke(BasicStroke paramBasicStroke) {
/* 287 */     this.stroke = paramBasicStroke;
/*     */   }
/*     */ 
/*     */   public void clear() {
/* 291 */     clear(Color.TRANSPARENT);
/*     */   }
/*     */ 
/*     */   public abstract void fillTriangles(VertexBuffer paramVertexBuffer, int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);
/*     */ 
/*     */   protected abstract void renderShape(Shape paramShape, BasicStroke paramBasicStroke, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);
/*     */ 
/*     */   public void fill(Shape paramShape)
/*     */   {
/* 301 */     float f1 = 0.0F; float f2 = 0.0F; float f3 = 0.0F; float f4 = 0.0F;
/* 302 */     if (this.paint.isProportional()) {
/* 303 */       RectBounds localRectBounds = this.nodeBounds;
/* 304 */       if (localRectBounds == null) {
/* 305 */         localRectBounds = paramShape.getBounds();
/*     */       }
/* 307 */       f1 = localRectBounds.getMinX();
/* 308 */       f2 = localRectBounds.getMinY();
/* 309 */       f3 = localRectBounds.getWidth();
/* 310 */       f4 = localRectBounds.getHeight();
/*     */     }
/* 312 */     renderShape(paramShape, null, f1, f2, f3, f4);
/*     */   }
/*     */ 
/*     */   public void draw(Shape paramShape) {
/* 316 */     float f1 = 0.0F; float f2 = 0.0F; float f3 = 0.0F; float f4 = 0.0F;
/* 317 */     if (this.paint.isProportional()) {
/* 318 */       RectBounds localRectBounds = this.nodeBounds;
/* 319 */       if (localRectBounds == null) {
/* 320 */         localRectBounds = paramShape.getBounds();
/*     */       }
/* 322 */       f1 = localRectBounds.getMinX();
/* 323 */       f2 = localRectBounds.getMinY();
/* 324 */       f3 = localRectBounds.getWidth();
/* 325 */       f4 = localRectBounds.getHeight();
/*     */     }
/* 327 */     renderShape(paramShape, this.stroke, f1, f2, f3, f4);
/*     */   }
/*     */ 
/*     */   public void drawTexture(Texture paramTexture, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4) {
/* 331 */     drawTexture(paramTexture, paramFloat1, paramFloat2, paramFloat1 + paramFloat3, paramFloat2 + paramFloat4, 0.0F, 0.0F, paramFloat3, paramFloat4);
/*     */   }
/*     */ 
/*     */   public void drawTexture(Texture paramTexture, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8)
/*     */   {
/* 340 */     drawTextureVO(paramTexture, 1.0F, 1.0F, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8, 15);
/*     */   }
/*     */ 
/*     */   public void drawTexture(Texture paramTexture, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, int paramInt)
/*     */   {
/* 351 */     drawTextureVO(paramTexture, 1.0F, 1.0F, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8, paramInt);
/*     */   }
/*     */ 
/*     */   private boolean pixelMatchingTextureOp(BaseTransform paramBaseTransform, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8)
/*     */   {
/* 375 */     if (!paramBaseTransform.isTranslateOrIdentity()) return false;
/*     */ 
/* 379 */     if ((paramFloat1 != paramFloat3) || (paramFloat2 != paramFloat4)) return false;
/*     */ 
/* 385 */     double d1 = paramFloat5 + paramBaseTransform.getMxt() - paramFloat7;
/* 386 */     double d2 = paramFloat6 + paramBaseTransform.getMyt() - paramFloat8;
/* 387 */     return (Math.abs(d1 - Math.round(d1)) < 0.00390625D) && (Math.abs(d2 - Math.round(d2)) < 0.00390625D);
/*     */   }
/*     */ 
/*     */   public void drawTextureVO(Texture paramTexture, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, int paramInt)
/*     */   {
/* 403 */     float f1 = paramFloat3;
/* 404 */     float f2 = paramFloat4;
/* 405 */     float f3 = paramFloat5 - paramFloat3;
/* 406 */     float f4 = paramFloat6 - paramFloat4;
/*     */ 
/* 409 */     BaseTransform localBaseTransform = getTransformNoClone();
/* 410 */     if (this.isSimpleTranslate) {
/* 411 */       localBaseTransform = IDENT;
/* 412 */       paramFloat3 += this.transX;
/* 413 */       paramFloat4 += this.transY;
/* 414 */       paramFloat5 += this.transX;
/* 415 */       paramFloat6 += this.transY;
/*     */     }
/*     */ 
/* 418 */     PixelFormat localPixelFormat = paramTexture.getPixelFormat();
/* 419 */     if (localPixelFormat == PixelFormat.BYTE_ALPHA)
/*     */     {
/* 424 */       this.context.validatePaintOp(this, localBaseTransform, paramTexture, f1, f2, f3, f4);
/*     */     }
/* 426 */     else this.context.validateTextureOp(this, localBaseTransform, paramTexture, localPixelFormat);
/*     */ 
/* 429 */     float f5 = paramTexture.getPhysicalWidth();
/* 430 */     float f6 = paramTexture.getPhysicalHeight();
/* 431 */     float f7 = paramTexture.getContentX();
/* 432 */     float f8 = paramTexture.getContentY();
/* 433 */     float f9 = paramFloat9 - paramFloat7;
/* 434 */     float f10 = paramFloat10 - paramFloat8;
/* 435 */     float f11 = (f7 + paramFloat7) / f5;
/* 436 */     float f12 = (f8 + paramFloat8) / f6;
/* 437 */     float f13 = (f7 + paramFloat9) / f5;
/* 438 */     float f14 = (f8 + paramFloat10) / f6;
/*     */ 
/* 480 */     Texture.WrapMode localWrapMode = paramTexture.getWrapMode();
/* 481 */     paramTexture.setWrapMode(Texture.WrapMode.CLAMP_TO_EDGE);
/*     */ 
/* 492 */     if (f7 + paramFloat7 <= 0.00390625D) paramInt &= -3;
/* 493 */     if (f8 + paramFloat8 <= 0.00390625D) paramInt &= -2;
/* 494 */     if (f7 + paramFloat9 + 0.00390625D >= f5) paramInt &= -5;
/* 495 */     if (f8 + paramFloat10 + 0.00390625D >= f6) paramInt &= -9;
/*     */ 
/* 497 */     VertexBuffer localVertexBuffer = this.context.getVertexBuffer();
/* 498 */     if ((paramInt == 0) || (!paramTexture.getLinearFiltering()) || (pixelMatchingTextureOp(localBaseTransform, f3, f4, f9, f10, paramFloat3, paramFloat4, paramFloat7, paramFloat8)))
/*     */     {
/* 520 */       if ((paramFloat1 == 1.0F) && (paramFloat2 == 1.0F)) {
/* 521 */         localVertexBuffer.addQuad(paramFloat3, paramFloat4, paramFloat5, paramFloat6, f11, f12, f13, f14);
/*     */       }
/*     */       else {
/* 524 */         paramFloat1 *= getExtraAlpha();
/* 525 */         paramFloat2 *= getExtraAlpha();
/* 526 */         localVertexBuffer.addQuadVO(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, f11, f12, f13, f14);
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 536 */       float f15 = 0.5F / f5;
/* 537 */       float f16 = 0.5F / f6;
/*     */ 
/* 540 */       float f17 = f11 + f15;
/* 541 */       float f18 = f12 + f16;
/* 542 */       float f19 = f13 - f15;
/* 543 */       float f20 = f14 - f16;
/*     */ 
/* 548 */       float f21 = 0.5F * f3 / f9;
/* 549 */       float f22 = 0.5F * f4 / f10;
/* 550 */       float f23 = paramFloat3 + f21;
/* 551 */       float f24 = paramFloat4 + f22;
/* 552 */       float f25 = paramFloat5 - f21;
/* 553 */       float f26 = paramFloat6 - f22;
/*     */ 
/* 555 */       paramFloat1 *= getExtraAlpha();
/* 556 */       paramFloat2 *= getExtraAlpha();
/*     */       float f27;
/*     */       float f28;
/*     */       float f29;
/*     */       float f30;
/* 558 */       if ((paramInt & 0x1) != 0) {
/* 559 */         f27 = paramFloat3;
/* 560 */         f28 = paramFloat5;
/* 561 */         f29 = f11;
/* 562 */         f30 = f13;
/* 563 */         if ((paramInt & 0x2) != 0)
/*     */         {
/* 565 */           localVertexBuffer.addQuadVO(paramFloat1, paramFloat1, paramFloat3, paramFloat4, f23, f24, f17, f18, f17, f18);
/*     */ 
/* 568 */           f27 = f23;
/* 569 */           f29 = f17;
/*     */         }
/* 571 */         if ((paramInt & 0x4) != 0)
/*     */         {
/* 573 */           localVertexBuffer.addQuadVO(paramFloat1, paramFloat1, f25, paramFloat4, paramFloat5, f24, f19, f18, f19, f18);
/*     */ 
/* 576 */           f28 = f25;
/* 577 */           f30 = f19;
/*     */         }
/*     */ 
/* 580 */         localVertexBuffer.addQuadVO(paramFloat1, paramFloat1, f27, paramFloat4, f28, f24, f29, f18, f30, f18);
/*     */ 
/* 583 */         paramFloat4 = f24;
/* 584 */         f12 = f18;
/*     */       }
/* 586 */       if ((paramInt & 0x8) != 0) {
/* 587 */         f27 = paramFloat3;
/* 588 */         f28 = paramFloat5;
/* 589 */         f29 = f11;
/* 590 */         f30 = f13;
/* 591 */         if ((paramInt & 0x2) != 0)
/*     */         {
/* 593 */           localVertexBuffer.addQuadVO(paramFloat2, paramFloat2, paramFloat3, f26, f23, paramFloat6, f17, f20, f17, f20);
/*     */ 
/* 596 */           f27 = f23;
/* 597 */           f29 = f17;
/*     */         }
/* 599 */         if ((paramInt & 0x4) != 0)
/*     */         {
/* 601 */           localVertexBuffer.addQuadVO(paramFloat2, paramFloat2, f25, f26, paramFloat5, paramFloat6, f19, f20, f19, f20);
/*     */ 
/* 604 */           f28 = f25;
/* 605 */           f30 = f19;
/*     */         }
/*     */ 
/* 608 */         localVertexBuffer.addQuadVO(paramFloat2, paramFloat2, f27, f26, f28, paramFloat6, f29, f20, f30, f20);
/*     */ 
/* 611 */         paramFloat6 = f26;
/* 612 */         f14 = f20;
/*     */       }
/* 614 */       if ((paramInt & 0x2) != 0)
/*     */       {
/* 616 */         localVertexBuffer.addQuadVO(paramFloat1, paramFloat2, paramFloat3, paramFloat4, f23, paramFloat6, f17, f12, f17, f14);
/*     */ 
/* 619 */         paramFloat3 = f23;
/* 620 */         f11 = f17;
/*     */       }
/* 622 */       if ((paramInt & 0x4) != 0)
/*     */       {
/* 624 */         localVertexBuffer.addQuadVO(paramFloat1, paramFloat2, f25, paramFloat4, paramFloat5, paramFloat6, f19, f12, f19, f14);
/*     */ 
/* 627 */         paramFloat5 = f25;
/* 628 */         f13 = f19;
/*     */       }
/*     */ 
/* 633 */       localVertexBuffer.addQuadVO(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, f11, f12, f13, f14);
/*     */     }
/*     */ 
/* 639 */     paramTexture.setWrapMode(localWrapMode);
/*     */   }
/*     */ 
/*     */   public void drawTextureRaw(Texture paramTexture, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8)
/*     */   {
/* 652 */     float f1 = paramFloat1;
/* 653 */     float f2 = paramFloat2;
/* 654 */     float f3 = paramFloat3 - paramFloat1;
/* 655 */     float f4 = paramFloat4 - paramFloat2;
/*     */ 
/* 658 */     BaseTransform localBaseTransform = getTransformNoClone();
/* 659 */     if (this.isSimpleTranslate) {
/* 660 */       localBaseTransform = IDENT;
/* 661 */       paramFloat1 += this.transX;
/* 662 */       paramFloat2 += this.transY;
/* 663 */       paramFloat3 += this.transX;
/* 664 */       paramFloat4 += this.transY;
/*     */     }
/*     */ 
/* 667 */     PixelFormat localPixelFormat = paramTexture.getPixelFormat();
/* 668 */     if (localPixelFormat == PixelFormat.BYTE_ALPHA)
/*     */     {
/* 673 */       this.context.validatePaintOp(this, localBaseTransform, paramTexture, f1, f2, f3, f4);
/*     */     }
/* 675 */     else this.context.validateTextureOp(this, localBaseTransform, paramTexture, localPixelFormat);
/*     */ 
/* 678 */     VertexBuffer localVertexBuffer = this.context.getVertexBuffer();
/* 679 */     localVertexBuffer.addQuad(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8);
/*     */   }
/*     */ 
/*     */   public void drawMappedTextureRaw(Texture paramTexture, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float paramFloat11, float paramFloat12)
/*     */   {
/* 694 */     float f1 = paramFloat1;
/* 695 */     float f2 = paramFloat2;
/* 696 */     float f3 = paramFloat3 - paramFloat1;
/* 697 */     float f4 = paramFloat4 - paramFloat2;
/*     */ 
/* 700 */     BaseTransform localBaseTransform = getTransformNoClone();
/* 701 */     if (this.isSimpleTranslate) {
/* 702 */       localBaseTransform = IDENT;
/* 703 */       paramFloat1 += this.transX;
/* 704 */       paramFloat2 += this.transY;
/* 705 */       paramFloat3 += this.transX;
/* 706 */       paramFloat4 += this.transY;
/*     */     }
/*     */ 
/* 709 */     PixelFormat localPixelFormat = paramTexture.getPixelFormat();
/* 710 */     if (localPixelFormat == PixelFormat.BYTE_ALPHA)
/*     */     {
/* 715 */       this.context.validatePaintOp(this, localBaseTransform, paramTexture, f1, f2, f3, f4);
/*     */     }
/* 717 */     else this.context.validateTextureOp(this, localBaseTransform, paramTexture, localPixelFormat);
/*     */ 
/* 720 */     VertexBuffer localVertexBuffer = this.context.getVertexBuffer();
/* 721 */     localVertexBuffer.addMappedQuad(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8, paramFloat9, paramFloat10, paramFloat11, paramFloat12);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.impl.BaseGraphics
 * JD-Core Version:    0.6.2
 */