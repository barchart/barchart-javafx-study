/*      */ package com.sun.prism.impl.ps;
/*      */ 
/*      */ import com.sun.javafx.font.FontResource;
/*      */ import com.sun.javafx.font.FontStrike;
/*      */ import com.sun.javafx.font.PrismFontUtils;
/*      */ import com.sun.javafx.geom.Ellipse2D;
/*      */ import com.sun.javafx.geom.Line2D;
/*      */ import com.sun.javafx.geom.Point2D;
/*      */ import com.sun.javafx.geom.RectBounds;
/*      */ import com.sun.javafx.geom.Rectangle;
/*      */ import com.sun.javafx.geom.RoundRectangle2D;
/*      */ import com.sun.javafx.geom.Shape;
/*      */ import com.sun.javafx.geom.transform.Affine2D;
/*      */ import com.sun.javafx.geom.transform.Affine3D;
/*      */ import com.sun.javafx.geom.transform.BaseTransform;
/*      */ import com.sun.javafx.geom.transform.NoninvertibleTransformException;
/*      */ import com.sun.prism.BasicStroke;
/*      */ import com.sun.prism.CompositeMode;
/*      */ import com.sun.prism.MultiTexture;
/*      */ import com.sun.prism.PixelFormat;
/*      */ import com.sun.prism.RTTexture;
/*      */ import com.sun.prism.ReadbackGraphics;
/*      */ import com.sun.prism.ReadbackRenderTarget;
/*      */ import com.sun.prism.RenderTarget;
/*      */ import com.sun.prism.Texture;
/*      */ import com.sun.prism.impl.BaseGraphics;
/*      */ import com.sun.prism.impl.GlyphCache;
/*      */ import com.sun.prism.impl.VertexBuffer;
/*      */ import com.sun.prism.impl.shape.MaskData;
/*      */ import com.sun.prism.impl.shape.ShapeUtil;
/*      */ import com.sun.prism.paint.Color;
/*      */ import com.sun.prism.paint.Gradient;
/*      */ import com.sun.prism.paint.Paint;
/*      */ import com.sun.prism.paint.Paint.Type;
/*      */ import com.sun.prism.ps.Shader;
/*      */ import com.sun.prism.ps.ShaderGraphics;
/*      */ import java.io.PrintStream;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ 
/*      */ public abstract class BaseShaderGraphics extends BaseGraphics
/*      */   implements ShaderGraphics, ReadbackGraphics
/*      */ {
/*   47 */   private static Affine2D TEMP_TX2D = new Affine2D();
/*   48 */   private static Affine3D TEMP_TX3D = new Affine3D();
/*      */   private final BaseShaderContext context;
/*      */   private Shader externalShader;
/*      */   private boolean isComplexPaint;
/*      */   private static final float FRINGE_FACTOR;
/*  839 */   private static final double SQRT_2 = Math.sqrt(2.0D);
/*      */ 
/* 1044 */   private boolean lcdSampleInvalid = false;
/*      */ 
/*      */   protected BaseShaderGraphics(BaseShaderContext paramBaseShaderContext, RenderTarget paramRenderTarget)
/*      */   {
/*   57 */     super(paramBaseShaderContext, paramRenderTarget);
/*   58 */     this.context = paramBaseShaderContext;
/*      */   }
/*      */ 
/*      */   BaseShaderContext getContext() {
/*   62 */     return this.context;
/*      */   }
/*      */ 
/*      */   boolean isComplexPaint() {
/*   66 */     return this.isComplexPaint;
/*      */   }
/*      */ 
/*      */   public void getPaintShaderTransform(Affine3D paramAffine3D) {
/*   70 */     paramAffine3D.setTransform(getTransformNoClone());
/*      */   }
/*      */ 
/*      */   public Shader getExternalShader() {
/*   74 */     return this.externalShader;
/*      */   }
/*      */ 
/*      */   public void setExternalShader(Shader paramShader) {
/*   78 */     this.externalShader = paramShader;
/*   79 */     this.context.setExternalShader(this, paramShader);
/*      */   }
/*      */ 
/*      */   public void setPaint(Paint paramPaint)
/*      */   {
/*   84 */     if (paramPaint.getType().isGradient()) {
/*   85 */       Gradient localGradient = (Gradient)paramPaint;
/*   86 */       this.isComplexPaint = (localGradient.getNumStops() > 12);
/*      */     } else {
/*   88 */       this.isComplexPaint = false;
/*      */     }
/*   90 */     super.setPaint(paramPaint);
/*      */   }
/*      */ 
/*      */   public void drawTexture(Texture paramTexture, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8)
/*      */   {
/*  101 */     if ((paramTexture instanceof MultiTexture))
/*  102 */       drawMultiTexture((MultiTexture)paramTexture, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8);
/*      */     else
/*  104 */       super.drawTexture(paramTexture, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8);
/*      */   }
/*      */ 
/*      */   private static float calculateScaleFactor(float paramFloat1, float paramFloat2)
/*      */   {
/*  109 */     if (paramFloat1 == paramFloat2) {
/*  110 */       return 1.0F;
/*      */     }
/*      */ 
/*  113 */     return (paramFloat1 - 1.0F) / paramFloat2;
/*      */   }
/*      */ 
/*      */   protected void drawMultiTexture(MultiTexture paramMultiTexture, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8)
/*      */   {
/*  121 */     BaseTransform localBaseTransform = getTransformNoClone();
/*  122 */     if (this.isSimpleTranslate) {
/*  123 */       localBaseTransform = IDENT;
/*  124 */       paramFloat1 += this.transX;
/*  125 */       paramFloat2 += this.transY;
/*  126 */       paramFloat3 += this.transX;
/*  127 */       paramFloat4 += this.transY;
/*      */     }
/*      */ 
/*  130 */     Texture[] arrayOfTexture = paramMultiTexture.getTextures();
/*  131 */     Shader localShader = this.context.validateTextureOp(this, localBaseTransform, arrayOfTexture, paramMultiTexture.getPixelFormat());
/*      */ 
/*  133 */     if (null == localShader)
/*      */     {
/*  135 */       return;
/*      */     }
/*      */ 
/*  138 */     if (paramMultiTexture.getPixelFormat() == PixelFormat.MULTI_YCbCr_420) {
/*  139 */       Texture localTexture1 = arrayOfTexture[0];
/*  140 */       Texture localTexture2 = arrayOfTexture[2];
/*  141 */       Texture localTexture3 = arrayOfTexture[1];
/*      */ 
/*  144 */       float f1 = paramMultiTexture.getContentWidth();
/*  145 */       float f2 = paramMultiTexture.getContentHeight();
/*      */ 
/*  151 */       float f3 = calculateScaleFactor(f1, localTexture1.getPhysicalWidth());
/*  152 */       float f4 = calculateScaleFactor(f2, localTexture1.getPhysicalHeight());
/*      */       float f5;
/*      */       float f6;
/*  154 */       if (arrayOfTexture.length > 3) {
/*  155 */         Texture localTexture4 = arrayOfTexture[3];
/*  156 */         f5 = calculateScaleFactor(f1, localTexture4.getPhysicalWidth());
/*  157 */         f6 = calculateScaleFactor(f2, localTexture4.getPhysicalHeight());
/*      */       } else {
/*  159 */         f5 = f6 = 0.0F;
/*      */       }
/*      */ 
/*  162 */       float f11 = (float)Math.floor(f1 / 2.0D);
/*  163 */       float f12 = (float)Math.floor(f2 / 2.0D);
/*      */ 
/*  165 */       float f7 = calculateScaleFactor(f11, localTexture2.getPhysicalWidth());
/*  166 */       float f8 = calculateScaleFactor(f12, localTexture2.getPhysicalHeight());
/*  167 */       float f9 = calculateScaleFactor(f11, localTexture3.getPhysicalWidth());
/*  168 */       float f10 = calculateScaleFactor(f12, localTexture3.getPhysicalHeight());
/*      */ 
/*  170 */       localShader.setConstant("lumaAlphaScale", f3, f4, f5, f6);
/*  171 */       localShader.setConstant("cbCrScale", f7, f8, f9, f10);
/*      */ 
/*  173 */       float f13 = paramFloat5 / f1;
/*  174 */       float f14 = paramFloat6 / f2;
/*  175 */       float f15 = paramFloat7 / f1;
/*  176 */       float f16 = paramFloat8 / f2;
/*      */ 
/*  178 */       VertexBuffer localVertexBuffer = this.context.getVertexBuffer();
/*  179 */       localVertexBuffer.addQuad(paramFloat1, paramFloat2, paramFloat3, paramFloat4, f13, f14, f15, f16);
/*      */     }
/*      */     else {
/*  182 */       throw new UnsupportedOperationException("Unsupported multitexture format " + paramMultiTexture.getPixelFormat());
/*      */     }
/*      */   }
/*      */ 
/*      */   public void drawTextureRaw2(Texture paramTexture1, Texture paramTexture2, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float paramFloat11, float paramFloat12)
/*      */   {
/*  192 */     BaseTransform localBaseTransform = getTransformNoClone();
/*  193 */     if (this.isSimpleTranslate) {
/*  194 */       localBaseTransform = IDENT;
/*  195 */       paramFloat1 += this.transX;
/*  196 */       paramFloat2 += this.transY;
/*  197 */       paramFloat3 += this.transX;
/*  198 */       paramFloat4 += this.transY;
/*      */     }
/*  200 */     this.context.validateTextureOp(this, localBaseTransform, paramTexture1, paramTexture2, PixelFormat.INT_ARGB_PRE);
/*      */ 
/*  203 */     VertexBuffer localVertexBuffer = this.context.getVertexBuffer();
/*  204 */     localVertexBuffer.addQuad(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8, paramFloat9, paramFloat10, paramFloat11, paramFloat12);
/*      */   }
/*      */ 
/*      */   public void drawMappedTextureRaw2(Texture paramTexture1, Texture paramTexture2, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float paramFloat11, float paramFloat12, float paramFloat13, float paramFloat14, float paramFloat15, float paramFloat16, float paramFloat17, float paramFloat18, float paramFloat19, float paramFloat20)
/*      */   {
/*  217 */     BaseTransform localBaseTransform = getTransformNoClone();
/*  218 */     if (this.isSimpleTranslate) {
/*  219 */       localBaseTransform = IDENT;
/*  220 */       paramFloat1 += this.transX;
/*  221 */       paramFloat2 += this.transY;
/*  222 */       paramFloat3 += this.transX;
/*  223 */       paramFloat4 += this.transY;
/*      */     }
/*  225 */     this.context.validateTextureOp(this, localBaseTransform, paramTexture1, paramTexture2, PixelFormat.INT_ARGB_PRE);
/*      */ 
/*  228 */     VertexBuffer localVertexBuffer = this.context.getVertexBuffer();
/*  229 */     localVertexBuffer.addMappedQuad(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8, paramFloat9, paramFloat10, paramFloat11, paramFloat12, paramFloat13, paramFloat14, paramFloat15, paramFloat16, paramFloat17, paramFloat18, paramFloat19, paramFloat20);
/*      */   }
/*      */ 
/*      */   private void renderWithComplexPaint(Shape paramShape, BasicStroke paramBasicStroke, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*      */   {
/*  241 */     this.context.flushVertexBuffer();
/*      */ 
/*  244 */     BaseTransform localBaseTransform = getTransformNoClone();
/*  245 */     MaskData localMaskData = ShapeUtil.rasterizeShape(paramShape, paramBasicStroke, getFinalClipNoClone(), localBaseTransform, true);
/*      */ 
/*  247 */     int i = localMaskData.getWidth();
/*  248 */     int j = localMaskData.getHeight();
/*      */ 
/*  250 */     float f1 = localMaskData.getOriginX();
/*  251 */     float f2 = localMaskData.getOriginY();
/*  252 */     float f3 = f1 + i;
/*  253 */     float f4 = f2 + j;
/*      */ 
/*  260 */     Gradient localGradient = (Gradient)this.paint;
/*  261 */     TEMP_TX2D.setToTranslation(-f1, -f2);
/*  262 */     TEMP_TX2D.concatenate(localBaseTransform);
/*  263 */     Texture localTexture = this.context.getGradientTexture(localGradient, TEMP_TX2D, i, j, localMaskData, paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/*      */ 
/*  267 */     float f5 = 0.0F;
/*  268 */     float f6 = 0.0F;
/*  269 */     float f7 = f5 + i / localTexture.getPhysicalWidth();
/*  270 */     float f8 = f6 + j / localTexture.getPhysicalHeight();
/*      */ 
/*  274 */     VertexBuffer localVertexBuffer = this.context.getVertexBuffer();
/*  275 */     this.context.validateTextureOp(this, IDENT, localTexture, null, localTexture.getPixelFormat());
/*  276 */     localVertexBuffer.addQuad(f1, f2, f3, f4, f5, f6, f7, f8);
/*      */   }
/*      */ 
/*      */   protected void renderShape(Shape paramShape, BasicStroke paramBasicStroke, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*      */   {
/*  283 */     if (this.isComplexPaint) {
/*  284 */       renderWithComplexPaint(paramShape, paramBasicStroke, paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/*  285 */       return;
/*      */     }
/*      */ 
/*  290 */     this.context.flushVertexBuffer();
/*      */ 
/*  293 */     BaseTransform localBaseTransform = getTransformNoClone();
/*  294 */     MaskData localMaskData = ShapeUtil.rasterizeShape(paramShape, paramBasicStroke, getFinalClipNoClone(), localBaseTransform, true);
/*      */ 
/*  296 */     Texture localTexture = this.context.getMaskTexture(localMaskData);
/*  297 */     int i = localMaskData.getWidth();
/*  298 */     int j = localMaskData.getHeight();
/*      */ 
/*  300 */     float f1 = localMaskData.getOriginX();
/*  301 */     float f2 = localMaskData.getOriginY();
/*  302 */     float f3 = f1 + i;
/*  303 */     float f4 = f2 + j;
/*  304 */     float f5 = 0.0F;
/*  305 */     float f6 = 0.0F;
/*  306 */     float f7 = f5 + i / localTexture.getPhysicalWidth();
/*  307 */     float f8 = f6 + j / localTexture.getPhysicalHeight();
/*      */ 
/*  311 */     this.context.validatePaintOp(this, IDENT, localTexture, paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/*      */ 
/*  313 */     VertexBuffer localVertexBuffer = this.context.getVertexBuffer();
/*  314 */     localVertexBuffer.addQuad(f1, f2, f3, f4, f5, f6, f7, f8);
/*      */   }
/*      */ 
/*      */   private static float getStrokeExpansionFactor(BasicStroke paramBasicStroke) {
/*  318 */     if (paramBasicStroke.getType() == 2)
/*  319 */       return 1.0F;
/*  320 */     if (paramBasicStroke.getType() == 0) {
/*  321 */       return 0.5F;
/*      */     }
/*  323 */     return 0.0F;
/*      */   }
/*      */ 
/*      */   private BaseTransform extract3Dremainder(BaseTransform paramBaseTransform)
/*      */   {
/*  343 */     if (paramBaseTransform.is2D()) {
/*  344 */       return IDENT;
/*      */     }
/*  346 */     TEMP_TX3D.setTransform(paramBaseTransform);
/*  347 */     TEMP_TX2D.setTransform(paramBaseTransform.getMxx(), paramBaseTransform.getMyx(), paramBaseTransform.getMxy(), paramBaseTransform.getMyy(), paramBaseTransform.getMxt(), paramBaseTransform.getMyt());
/*      */     try
/*      */     {
/*  351 */       TEMP_TX2D.invert();
/*  352 */       TEMP_TX3D.concatenate(TEMP_TX2D);
/*      */     } catch (NoninvertibleTransformException localNoninvertibleTransformException) {
/*      */     }
/*  355 */     return TEMP_TX3D;
/*      */   }
/*      */ 
/*      */   private void renderGeneralRoundedRect(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, BaseShaderContext.MaskType paramMaskType, BasicStroke paramBasicStroke)
/*      */   {
/*      */     float f1;
/*      */     float f2;
/*      */     float f3;
/*      */     float f4;
/*      */     float f6;
/*      */     float f5;
/*  365 */     if (paramBasicStroke == null) {
/*  366 */       f1 = paramFloat1;
/*  367 */       f2 = paramFloat2;
/*  368 */       f3 = paramFloat3;
/*  369 */       f4 = paramFloat4;
/*  370 */       f5 = f6 = 0.0F;
/*      */     } else {
/*  372 */       float f13 = paramBasicStroke.getLineWidth();
/*  373 */       float f14 = getStrokeExpansionFactor(paramBasicStroke) * f13;
/*  374 */       f1 = paramFloat1 - f14;
/*  375 */       f2 = paramFloat2 - f14;
/*  376 */       f14 *= 2.0F;
/*  377 */       f3 = paramFloat3 + f14;
/*  378 */       f4 = paramFloat4 + f14;
/*  379 */       if ((paramFloat5 > 0.0F) && (paramFloat6 > 0.0F)) {
/*  380 */         paramFloat5 += f14;
/*  381 */         paramFloat6 += f14;
/*      */       }
/*  383 */       else if (paramBasicStroke.getLineJoin() == 1) {
/*  384 */         paramFloat5 = paramFloat6 = f14;
/*      */       } else {
/*  386 */         paramFloat5 = paramFloat6 = 0.0F;
/*      */       }
/*      */ 
/*  389 */       f5 = (f3 - f13 * 2.0F) / f3;
/*  390 */       f6 = (f4 - f13 * 2.0F) / f4;
/*  391 */       if ((f5 <= 0.0F) || (f6 <= 0.0F)) {
/*  392 */         paramMaskType = paramMaskType.getFillType();
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  397 */     BaseTransform localBaseTransform1 = getTransformNoClone();
/*      */     float f12;
/*      */     float f9;
/*      */     float f11;
/*      */     float f10;
/*      */     float f7;
/*      */     float f8;
/*      */     BaseTransform localBaseTransform2;
/*  399 */     if (this.isSimpleTranslate) {
/*  400 */       f9 = f12 = 1.0F;
/*  401 */       f10 = f11 = 0.0F;
/*  402 */       f7 = f1 + this.transX;
/*  403 */       f8 = f2 + this.transY;
/*  404 */       localBaseTransform2 = IDENT;
/*      */     } else {
/*  406 */       localBaseTransform2 = extract3Dremainder(localBaseTransform1);
/*  407 */       f9 = (float)localBaseTransform1.getMxx();
/*  408 */       f11 = (float)localBaseTransform1.getMxy();
/*  409 */       f10 = (float)localBaseTransform1.getMyx();
/*  410 */       f12 = (float)localBaseTransform1.getMyy();
/*  411 */       f7 = f1 * f9 + f2 * f11 + (float)localBaseTransform1.getMxt();
/*  412 */       f8 = f1 * f10 + f2 * f12 + (float)localBaseTransform1.getMyt();
/*      */     }
/*      */ 
/*  415 */     f9 *= f3;
/*  416 */     f10 *= f3;
/*  417 */     f11 *= f4;
/*  418 */     f12 *= f4;
/*      */ 
/*  420 */     float f15 = paramFloat5 / f3;
/*  421 */     float f16 = paramFloat6 / f4;
/*  422 */     renderGeneralRoundedPgram(f7, f8, f9, f10, f11, f12, f15, f16, f5, f6, localBaseTransform2, paramMaskType, paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/*      */   }
/*      */ 
/*      */   private void renderGeneralRoundedPgram(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, BaseTransform paramBaseTransform, BaseShaderContext.MaskType paramMaskType, float paramFloat11, float paramFloat12, float paramFloat13, float paramFloat14)
/*      */   {
/*  435 */     float f1 = len(paramFloat3, paramFloat4);
/*  436 */     float f2 = len(paramFloat5, paramFloat6);
/*  437 */     if ((f1 == 0.0F) || (f2 == 0.0F))
/*      */     {
/*  439 */       return;
/*      */     }
/*      */ 
/*  446 */     float f3 = paramFloat1;
/*  447 */     float f4 = paramFloat2;
/*  448 */     float f5 = paramFloat1 + paramFloat3;
/*  449 */     float f6 = paramFloat2 + paramFloat4;
/*  450 */     float f7 = paramFloat1 + paramFloat5;
/*  451 */     float f8 = paramFloat2 + paramFloat6;
/*  452 */     float f9 = f5 + paramFloat5;
/*  453 */     float f10 = f6 + paramFloat6;
/*      */ 
/*  486 */     float f11 = (paramFloat3 * paramFloat6 - paramFloat4 * paramFloat5) * 0.5F;
/*  487 */     float f12 = f11 / f2;
/*  488 */     float f13 = f11 / f1;
/*  489 */     if (f12 < 0.0F) f12 = -f12;
/*  490 */     if (f13 < 0.0F) f13 = -f13;
/*      */ 
/*  493 */     float f14 = paramFloat3 / f1;
/*  494 */     float f15 = paramFloat4 / f1;
/*  495 */     float f16 = paramFloat5 / f2;
/*  496 */     float f17 = paramFloat6 / f2;
/*      */ 
/*  552 */     float f18 = -paramFloat5 * (f14 + f16) - paramFloat6 * (f15 + f17);
/*  553 */     float f19 = paramFloat6 * paramFloat3 - paramFloat5 * paramFloat4;
/*  554 */     float f20 = f18 / f19;
/*      */ 
/*  557 */     float f21 = FRINGE_FACTOR * Math.signum(f20);
/*  558 */     float f22 = (f20 * paramFloat3 + f15) * f21;
/*  559 */     float f23 = (f20 * paramFloat4 - f14) * f21;
/*  560 */     f3 += f22; f4 += f23;
/*      */ 
/*  562 */     f9 -= f22; f10 -= f23;
/*      */ 
/*  575 */     f18 = paramFloat4 * (f17 - f15) - paramFloat3 * (f14 - f16);
/*  576 */     f20 = f18 / f19;
/*  577 */     f21 = FRINGE_FACTOR * Math.signum(f20);
/*  578 */     f22 = (f20 * paramFloat5 + f17) * f21;
/*  579 */     f23 = (f20 * paramFloat6 - f16) * f21;
/*  580 */     f5 += f22; f6 += f23;
/*  581 */     f7 -= f22; f8 -= f23;
/*      */ 
/*  597 */     float f24 = (f3 + f9) * 0.5F;
/*  598 */     float f25 = (f4 + f10) * 0.5F;
/*  599 */     float f26 = f24 * f17 - f25 * f16;
/*  600 */     float f27 = f24 * f15 - f25 * f14;
/*      */ 
/*  602 */     float f28 = f3 * f17 - f4 * f16 - f26;
/*  603 */     float f29 = f3 * f15 - f4 * f14 - f27;
/*  604 */     float f30 = f5 * f17 - f6 * f16 - f26;
/*  605 */     float f31 = f5 * f15 - f6 * f14 - f27;
/*  606 */     float f32 = f7 * f17 - f8 * f16 - f26;
/*  607 */     float f33 = f7 * f15 - f8 * f14 - f27;
/*  608 */     float f34 = f9 * f17 - f10 * f16 - f26;
/*  609 */     float f35 = f9 * f15 - f10 * f14 - f27;
/*      */     float f36;
/*      */     float f37;
/*  613 */     if ((paramMaskType == BaseShaderContext.MaskType.DRAW_ROUNDRECT) || (paramMaskType == BaseShaderContext.MaskType.FILL_ROUNDRECT)) {
/*  614 */       f36 = f12 * paramFloat7;
/*  615 */       f37 = f13 * paramFloat8;
/*  616 */       if ((f36 < 0.5D) || (f37 < 0.5D))
/*      */       {
/*  627 */         paramMaskType = paramMaskType == BaseShaderContext.MaskType.DRAW_ROUNDRECT ? BaseShaderContext.MaskType.DRAW_PGRAM : BaseShaderContext.MaskType.FILL_PGRAM;
/*      */       }
/*      */       else {
/*  630 */         float f38 = f12 - f36;
/*  631 */         float f39 = f13 - f37;
/*      */         float f40;
/*      */         float f41;
/*  633 */         if (paramMaskType == BaseShaderContext.MaskType.DRAW_ROUNDRECT) {
/*  634 */           float f42 = f12 * paramFloat9;
/*  635 */           float f43 = f13 * paramFloat10;
/*      */ 
/*  638 */           f40 = f42 - f38;
/*  639 */           f41 = f43 - f39;
/*      */ 
/*  644 */           if ((f40 < 0.5F) || (f41 < 0.5F))
/*      */           {
/*  646 */             f40 = f42;
/*  647 */             f41 = f43;
/*  648 */             paramMaskType = BaseShaderContext.MaskType.DRAW_SEMIROUNDRECT;
/*      */           }
/*      */           else {
/*  651 */             f40 = 1.0F / f40;
/*  652 */             f41 = 1.0F / f41;
/*      */           }
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*  658 */           f40 = f41 = 0.0F;
/*      */         }
/*  660 */         f36 = 1.0F / f36;
/*  661 */         f37 = 1.0F / f37;
/*  662 */         Shader localShader2 = this.context.validatePaintOp(this, paramBaseTransform, paramMaskType, paramFloat11, paramFloat12, paramFloat13, paramFloat14, f36, f37, f40, f41, 0.0F, 0.0F);
/*      */ 
/*  667 */         localShader2.setConstant("oinvarcradii", f36, f37);
/*  668 */         if (paramMaskType == BaseShaderContext.MaskType.DRAW_ROUNDRECT)
/*  669 */           localShader2.setConstant("iinvarcradii", f40, f41);
/*  670 */         else if (paramMaskType == BaseShaderContext.MaskType.DRAW_SEMIROUNDRECT) {
/*  671 */           localShader2.setConstant("idim", f40, f41);
/*      */         }
/*  673 */         f12 = f38;
/*  674 */         f13 = f39;
/*      */       }
/*      */     }
/*  677 */     if ((paramMaskType == BaseShaderContext.MaskType.DRAW_PGRAM) || (paramMaskType == BaseShaderContext.MaskType.DRAW_ELLIPSE)) {
/*  678 */       f36 = f12 * paramFloat9;
/*  679 */       f37 = f13 * paramFloat10;
/*  680 */       if (paramMaskType == BaseShaderContext.MaskType.DRAW_ELLIPSE) {
/*  681 */         if (Math.abs(f12 - f13) < 0.01D) {
/*  682 */           paramMaskType = BaseShaderContext.MaskType.DRAW_CIRCLE;
/*      */ 
/*  686 */           f13 = (float)Math.min(1.0D, f13 * f13 * 3.141592653589793D);
/*  687 */           f37 = (float)Math.min(1.0D, f37 * f37 * 3.141592653589793D);
/*      */         }
/*      */         else
/*      */         {
/*  691 */           f12 = 1.0F / f12;
/*  692 */           f13 = 1.0F / f13;
/*  693 */           f36 = 1.0F / f36;
/*  694 */           f37 = 1.0F / f37;
/*      */         }
/*      */       }
/*  697 */       Shader localShader1 = this.context.validatePaintOp(this, paramBaseTransform, paramMaskType, paramFloat11, paramFloat12, paramFloat13, paramFloat14, f36, f37, 0.0F, 0.0F, 0.0F, 0.0F);
/*      */ 
/*  701 */       localShader1.setConstant("idim", f36, f37);
/*  702 */     } else if (paramMaskType == BaseShaderContext.MaskType.FILL_ELLIPSE) {
/*  703 */       if (Math.abs(f12 - f13) < 0.01D) {
/*  704 */         paramMaskType = BaseShaderContext.MaskType.FILL_CIRCLE;
/*      */ 
/*  708 */         f13 = (float)Math.min(1.0D, f13 * f13 * 3.141592653589793D);
/*      */       }
/*      */       else
/*      */       {
/*  712 */         f12 = 1.0F / f12;
/*  713 */         f13 = 1.0F / f13;
/*  714 */         f28 *= f12; f29 *= f13;
/*  715 */         f30 *= f12; f31 *= f13;
/*  716 */         f32 *= f12; f33 *= f13;
/*  717 */         f34 *= f12; f35 *= f13;
/*      */       }
/*  719 */       this.context.validatePaintOp(this, paramBaseTransform, paramMaskType, paramFloat11, paramFloat12, paramFloat13, paramFloat14);
/*  720 */     } else if (paramMaskType == BaseShaderContext.MaskType.FILL_PGRAM) {
/*  721 */       this.context.validatePaintOp(this, paramBaseTransform, paramMaskType, paramFloat11, paramFloat12, paramFloat13, paramFloat14);
/*      */     }
/*      */ 
/*  724 */     this.context.getVertexBuffer().addMappedPgram(f3, f4, f5, f6, f7, f8, f9, f10, f28, f29, f30, f31, f32, f33, f34, f35, f12, f13);
/*      */   }
/*      */ 
/*      */   public void fillRect(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*      */   {
/*  732 */     if ((paramFloat3 <= 0.0F) || (paramFloat4 <= 0.0F)) {
/*  733 */       return;
/*      */     }
/*  735 */     if (this.isComplexPaint) {
/*  736 */       scratchRRect.setRoundRect(paramFloat1, paramFloat2, paramFloat3, paramFloat4, 0.0F, 0.0F);
/*  737 */       renderWithComplexPaint(scratchRRect, null, paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/*  738 */       return;
/*      */     }
/*  740 */     renderGeneralRoundedRect(paramFloat1, paramFloat2, paramFloat3, paramFloat4, 0.0F, 0.0F, BaseShaderContext.MaskType.FILL_PGRAM, null);
/*      */   }
/*      */ 
/*      */   public void fillEllipse(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*      */   {
/*  745 */     if ((paramFloat3 <= 0.0F) || (paramFloat4 <= 0.0F)) {
/*  746 */       return;
/*      */     }
/*  748 */     if (this.isComplexPaint) {
/*  749 */       scratchEllipse.setFrame(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/*  750 */       renderWithComplexPaint(scratchEllipse, null, paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/*  751 */       return;
/*      */     }
/*  753 */     renderGeneralRoundedRect(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat3, paramFloat4, BaseShaderContext.MaskType.FILL_ELLIPSE, null);
/*      */   }
/*      */ 
/*      */   public void fillRoundRect(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
/*      */   {
/*  760 */     paramFloat5 = Math.min(Math.abs(paramFloat5), paramFloat3);
/*  761 */     paramFloat6 = Math.min(Math.abs(paramFloat6), paramFloat4);
/*      */ 
/*  763 */     if ((paramFloat3 <= 0.0F) || (paramFloat4 <= 0.0F)) {
/*  764 */       return;
/*      */     }
/*  766 */     if (this.isComplexPaint) {
/*  767 */       scratchRRect.setRoundRect(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6);
/*  768 */       renderWithComplexPaint(scratchRRect, null, paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/*  769 */       return;
/*      */     }
/*  771 */     renderGeneralRoundedRect(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, BaseShaderContext.MaskType.FILL_ROUNDRECT, null);
/*      */   }
/*      */ 
/*      */   public void fillQuad(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*      */   {
/*      */     float f1;
/*      */     float f3;
/*  777 */     if (paramFloat1 <= paramFloat3) {
/*  778 */       f1 = paramFloat1;
/*  779 */       f3 = paramFloat3 - paramFloat1;
/*      */     } else {
/*  781 */       f1 = paramFloat3;
/*  782 */       f3 = paramFloat1 - paramFloat3;
/*      */     }
/*      */     float f2;
/*      */     float f4;
/*  784 */     if (paramFloat2 <= paramFloat4) {
/*  785 */       f2 = paramFloat2;
/*  786 */       f4 = paramFloat4 - paramFloat2;
/*      */     } else {
/*  788 */       f2 = paramFloat4;
/*  789 */       f4 = paramFloat2 - paramFloat4;
/*      */     }
/*      */ 
/*  792 */     if (this.isComplexPaint) {
/*  793 */       scratchRRect.setRoundRect(f1, f2, f3, f4, 0.0F, 0.0F);
/*  794 */       renderWithComplexPaint(scratchRRect, null, f1, f2, f3, f4);
/*  795 */       return;
/*      */     }
/*      */ 
/*  798 */     BaseTransform localBaseTransform = getTransformNoClone();
/*  799 */     if (this.isSimpleTranslate) {
/*  800 */       localBaseTransform = IDENT;
/*  801 */       f1 += this.transX;
/*  802 */       f2 += this.transY;
/*      */     }
/*  804 */     this.context.validatePaintOp(this, localBaseTransform, BaseShaderContext.MaskType.SOLID, f1, f2, f3, f4);
/*      */ 
/*  806 */     VertexBuffer localVertexBuffer = this.context.getVertexBuffer();
/*  807 */     localVertexBuffer.addQuad(f1, f2, f1 + f3, f2 + f4);
/*      */   }
/*      */ 
/*      */   public void fillTriangles(VertexBuffer paramVertexBuffer, int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*      */   {
/*  814 */     if (this.isComplexPaint) {
/*  815 */       throw new AssertionError("fillTriangles() not supported for complex paints");
/*      */     }
/*      */ 
/*  818 */     BaseTransform localBaseTransform = getTransformNoClone();
/*  819 */     this.context.validatePaintOp(this, localBaseTransform, BaseShaderContext.MaskType.SOLID, paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/*      */ 
/*  821 */     VertexBuffer localVertexBuffer = this.context.getVertexBuffer();
/*  822 */     localVertexBuffer.addVerts(paramVertexBuffer, paramInt);
/*      */   }
/*      */ 
/*      */   void fillCubicCurves(VertexBuffer paramVertexBuffer, int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*      */   {
/*  828 */     if (this.isComplexPaint) {
/*  829 */       throw new AssertionError("fillCubicCurves() not supported for complex paints");
/*      */     }
/*      */ 
/*  832 */     BaseTransform localBaseTransform = getTransformNoClone();
/*  833 */     this.context.validatePaintOp(this, localBaseTransform, BaseShaderContext.MaskType.FILL_CUBICCURVE, paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/*      */ 
/*  835 */     VertexBuffer localVertexBuffer = this.context.getVertexBuffer();
/*  836 */     localVertexBuffer.addVerts(paramVertexBuffer, paramInt);
/*      */   }
/*      */ 
/*      */   private static boolean canUseStrokeShader(BasicStroke paramBasicStroke)
/*      */   {
/*  842 */     return (!paramBasicStroke.isDashed()) && ((paramBasicStroke.getType() == 1) || (paramBasicStroke.getLineJoin() == 1) || ((paramBasicStroke.getLineJoin() == 0) && (paramBasicStroke.getMiterLimit() >= SQRT_2)));
/*      */   }
/*      */ 
/*      */   public void drawRect(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*      */   {
/*  850 */     if ((paramFloat3 < 0.0F) || (paramFloat4 < 0.0F)) {
/*  851 */       return;
/*      */     }
/*  853 */     if ((paramFloat3 == 0.0F) || (paramFloat4 == 0.0F)) {
/*  854 */       drawLine(paramFloat1, paramFloat2, paramFloat1 + paramFloat3, paramFloat2 + paramFloat4);
/*  855 */       return;
/*      */     }
/*  857 */     if (this.isComplexPaint) {
/*  858 */       scratchRRect.setRoundRect(paramFloat1, paramFloat2, paramFloat3, paramFloat4, 0.0F, 0.0F);
/*  859 */       renderWithComplexPaint(scratchRRect, this.stroke, paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/*  860 */       return;
/*      */     }
/*  862 */     if (canUseStrokeShader(this.stroke)) {
/*  863 */       renderGeneralRoundedRect(paramFloat1, paramFloat2, paramFloat3, paramFloat4, 0.0F, 0.0F, BaseShaderContext.MaskType.DRAW_PGRAM, this.stroke);
/*      */ 
/*  865 */       return;
/*      */     }
/*  867 */     scratchRRect.setRoundRect(paramFloat1, paramFloat2, paramFloat3, paramFloat4, 0.0F, 0.0F);
/*  868 */     renderShape(scratchRRect, this.stroke, paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/*      */   }
/*      */ 
/*      */   private boolean checkInnerCurvature(float paramFloat1, float paramFloat2)
/*      */   {
/*  876 */     float f = this.stroke.getLineWidth() * (1.0F - getStrokeExpansionFactor(this.stroke));
/*      */ 
/*  878 */     paramFloat1 -= f;
/*  879 */     paramFloat2 -= f;
/*      */ 
/*  883 */     return (paramFloat1 <= 0.0F) || (paramFloat2 <= 0.0F) || ((paramFloat1 * 2.0F > paramFloat2) && (paramFloat2 * 2.0F > paramFloat1));
/*      */   }
/*      */ 
/*      */   public void drawEllipse(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*      */   {
/*  888 */     if ((paramFloat3 < 0.0F) || (paramFloat4 < 0.0F)) {
/*  889 */       return;
/*      */     }
/*  891 */     if ((!this.isComplexPaint) && (!this.stroke.isDashed()) && (checkInnerCurvature(paramFloat3, paramFloat4)))
/*      */     {
/*  894 */       renderGeneralRoundedRect(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat3, paramFloat4, BaseShaderContext.MaskType.DRAW_ELLIPSE, this.stroke);
/*      */ 
/*  896 */       return;
/*      */     }
/*  898 */     scratchEllipse.setFrame(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/*  899 */     renderShape(scratchEllipse, this.stroke, paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/*      */   }
/*      */ 
/*      */   public void drawRoundRect(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6)
/*      */   {
/*  905 */     paramFloat5 = Math.min(Math.abs(paramFloat5), paramFloat3);
/*  906 */     paramFloat6 = Math.min(Math.abs(paramFloat6), paramFloat4);
/*      */ 
/*  908 */     if ((paramFloat3 < 0.0F) || (paramFloat4 < 0.0F)) {
/*  909 */       return;
/*      */     }
/*  911 */     if ((!this.isComplexPaint) && (!this.stroke.isDashed()) && (checkInnerCurvature(paramFloat5, paramFloat6)))
/*      */     {
/*  914 */       renderGeneralRoundedRect(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, BaseShaderContext.MaskType.DRAW_ROUNDRECT, this.stroke);
/*      */ 
/*  916 */       return;
/*      */     }
/*  918 */     scratchRRect.setRoundRect(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6);
/*  919 */     renderShape(scratchRRect, this.stroke, paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/*      */   }
/*      */ 
/*      */   public void drawLine(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*      */   {
/*      */     float f1;
/*      */     float f3;
/*  924 */     if (paramFloat1 <= paramFloat3) {
/*  925 */       f1 = paramFloat1;
/*  926 */       f3 = paramFloat3 - paramFloat1;
/*      */     } else {
/*  928 */       f1 = paramFloat3;
/*  929 */       f3 = paramFloat1 - paramFloat3;
/*      */     }
/*      */     float f2;
/*      */     float f4;
/*  931 */     if (paramFloat2 <= paramFloat4) {
/*  932 */       f2 = paramFloat2;
/*  933 */       f4 = paramFloat4 - paramFloat2;
/*      */     } else {
/*  935 */       f2 = paramFloat4;
/*  936 */       f4 = paramFloat2 - paramFloat4;
/*      */     }
/*      */ 
/*  942 */     if (this.stroke.getType() == 1) {
/*  943 */       return;
/*      */     }
/*  945 */     if (this.isComplexPaint) {
/*  946 */       scratchLine.setLine(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/*  947 */       renderWithComplexPaint(scratchLine, this.stroke, f1, f2, f3, f4);
/*  948 */       return;
/*      */     }
/*  950 */     int i = this.stroke.getEndCap();
/*  951 */     if (this.stroke.isDashed())
/*      */     {
/*  955 */       scratchLine.setLine(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/*  956 */       renderShape(scratchLine, this.stroke, f1, f2, f3, f4);
/*  957 */       return;
/*      */     }
/*  959 */     float f5 = this.stroke.getLineWidth();
/*  960 */     if (this.stroke.getType() == 2) {
/*  961 */       f5 *= 2.0F;
/*      */     }
/*  963 */     float f6 = paramFloat3 - paramFloat1;
/*  964 */     float f7 = paramFloat4 - paramFloat2;
/*  965 */     float f8 = len(f6, f7);
/*      */     float f9;
/*      */     float f10;
/*  967 */     if (f8 == 0.0F) {
/*  968 */       if (i == 0) {
/*  969 */         return;
/*      */       }
/*  971 */       f9 = f5;
/*  972 */       f10 = 0.0F;
/*      */     } else {
/*  974 */       f9 = f5 * f6 / f8;
/*  975 */       f10 = f5 * f7 / f8;
/*      */     }
/*      */ 
/*  978 */     BaseTransform localBaseTransform1 = getTransformNoClone();
/*      */     float f11;
/*      */     float f12;
/*      */     BaseTransform localBaseTransform2;
/*  981 */     if (this.isSimpleTranslate) {
/*  982 */       double d1 = localBaseTransform1.getMxt();
/*  983 */       double d2 = localBaseTransform1.getMyt();
/*  984 */       paramFloat1 = (float)(paramFloat1 + d1);
/*  985 */       paramFloat2 = (float)(paramFloat2 + d2);
/*  986 */       paramFloat3 = (float)(paramFloat3 + d1);
/*  987 */       paramFloat4 = (float)(paramFloat4 + d2);
/*  988 */       f11 = f10;
/*  989 */       f12 = -f9;
/*  990 */       localBaseTransform2 = IDENT;
/*      */     } else {
/*  992 */       localBaseTransform2 = extract3Dremainder(localBaseTransform1);
/*  993 */       double[] arrayOfDouble = { paramFloat1, paramFloat2, paramFloat3, paramFloat4 };
/*  994 */       localBaseTransform1.transform(arrayOfDouble, 0, arrayOfDouble, 0, 2);
/*  995 */       paramFloat1 = (float)arrayOfDouble[0];
/*  996 */       paramFloat2 = (float)arrayOfDouble[1];
/*  997 */       paramFloat3 = (float)arrayOfDouble[2];
/*  998 */       paramFloat4 = (float)arrayOfDouble[3];
/*  999 */       f6 = paramFloat3 - paramFloat1;
/* 1000 */       f7 = paramFloat4 - paramFloat2;
/* 1001 */       arrayOfDouble[0] = f9;
/* 1002 */       arrayOfDouble[1] = f10;
/* 1003 */       arrayOfDouble[2] = f10;
/* 1004 */       arrayOfDouble[3] = (-f9);
/* 1005 */       localBaseTransform1.deltaTransform(arrayOfDouble, 0, arrayOfDouble, 0, 2);
/* 1006 */       f9 = (float)arrayOfDouble[0];
/* 1007 */       f10 = (float)arrayOfDouble[1];
/* 1008 */       f11 = (float)arrayOfDouble[2];
/* 1009 */       f12 = (float)arrayOfDouble[3];
/*      */     }
/* 1011 */     float f13 = paramFloat1 - f11 / 2.0F;
/* 1012 */     float f14 = paramFloat2 - f12 / 2.0F;
/*      */     float f15;
/*      */     float f16;
/*      */     BaseShaderContext.MaskType localMaskType;
/* 1015 */     if (i != 0) {
/* 1016 */       f13 -= f9 / 2.0F;
/* 1017 */       f14 -= f10 / 2.0F;
/* 1018 */       f6 += f9;
/* 1019 */       f7 += f10;
/* 1020 */       if (i == 1) {
/* 1021 */         f15 = len(f9, f10) / len(f6, f7);
/* 1022 */         f16 = 1.0F;
/* 1023 */         localMaskType = BaseShaderContext.MaskType.FILL_ROUNDRECT;
/*      */       } else {
/* 1025 */         f15 = f16 = 0.0F;
/* 1026 */         localMaskType = BaseShaderContext.MaskType.FILL_PGRAM;
/*      */       }
/*      */     } else {
/* 1029 */       f15 = f16 = 0.0F;
/* 1030 */       localMaskType = BaseShaderContext.MaskType.FILL_PGRAM;
/*      */     }
/* 1032 */     renderGeneralRoundedPgram(f13, f14, f6, f7, f11, f12, f15, f16, 0.0F, 0.0F, localBaseTransform2, localMaskType, f1, f2, f3, f4);
/*      */   }
/*      */ 
/*      */   private static float len(float paramFloat1, float paramFloat2)
/*      */   {
/* 1039 */     return paramFloat2 == 0.0F ? Math.abs(paramFloat1) : paramFloat1 == 0.0F ? Math.abs(paramFloat2) : (float)Math.sqrt(paramFloat1 * paramFloat1 + paramFloat2 * paramFloat2);
/*      */   }
/*      */ 
/*      */   public void setNodeBounds(RectBounds paramRectBounds)
/*      */   {
/* 1047 */     this.nodeBounds = paramRectBounds;
/* 1048 */     this.lcdSampleInvalid = (paramRectBounds != null);
/*      */   }
/*      */ 
/*      */   private void initLCDSampleRT() {
/* 1052 */     if (this.lcdSampleInvalid) {
/* 1053 */       RectBounds localRectBounds = new RectBounds();
/* 1054 */       getTransformNoClone().transform(this.nodeBounds, localRectBounds);
/* 1055 */       Rectangle localRectangle = getClipRectNoClone();
/* 1056 */       if ((localRectangle != null) && (!localRectangle.isEmpty()))
/*      */       {
/* 1058 */         localRectBounds.intersectWith(localRectangle);
/*      */       }
/*      */ 
/* 1062 */       float f1 = localRectBounds.getMinX() - 1.0F;
/* 1063 */       float f2 = localRectBounds.getMinY();
/* 1064 */       float f3 = localRectBounds.getWidth() + 2.0F;
/* 1065 */       float f4 = localRectBounds.getHeight() + 1.0F;
/*      */ 
/* 1067 */       this.context.validateLCDBuffer(getRenderTarget());
/*      */ 
/* 1071 */       BaseShaderGraphics localBaseShaderGraphics = (BaseShaderGraphics)this.context.getLCDBuffer().createGraphics();
/* 1072 */       localBaseShaderGraphics.setCompositeMode(CompositeMode.SRC);
/* 1073 */       this.context.validateLCDOp(localBaseShaderGraphics, IDENT, (Texture)getRenderTarget(), null, true, null);
/*      */ 
/* 1075 */       int i = getRenderTarget().getPhysicalHeight();
/* 1076 */       int j = getRenderTarget().getPhysicalWidth();
/* 1077 */       float f5 = f1 / j;
/* 1078 */       float f6 = f2 / i;
/* 1079 */       float f7 = (f1 + f3) / j;
/* 1080 */       float f8 = (f2 + f4) / i;
/*      */ 
/* 1083 */       localBaseShaderGraphics.drawLCDBuffer(f1, f2, f3, f4, f5, f6, f7, f8);
/* 1084 */       this.context.setRenderTarget(this);
/*      */     }
/* 1086 */     this.lcdSampleInvalid = false;
/*      */   }
/*      */ 
/*      */   public void drawString(String paramString, FontStrike paramFontStrike, float paramFloat1, float paramFloat2) {
/* 1090 */     drawString(paramString, paramFontStrike, paramFloat1, paramFloat2, null, 0, 0);
/*      */   }
/*      */ 
/*      */   public void drawString(String paramString, FontStrike paramFontStrike, float paramFloat1, float paramFloat2, Color paramColor, int paramInt1, int paramInt2)
/*      */   {
/* 1095 */     if ((this.isComplexPaint) || (this.paint.getType().isImagePattern()) || (paramFontStrike.drawAsShapes()))
/*      */     {
/* 1108 */       localBaseTransform1 = BaseTransform.getTranslateInstance(paramFloat1, paramFloat2);
/* 1109 */       localObject1 = paramFontStrike.getOutline(paramString, localBaseTransform1);
/* 1110 */       fill((Shape)localObject1);
/* 1111 */       return;
/*      */     }
/*      */ 
/* 1114 */     BaseTransform localBaseTransform1 = getTransformNoClone();
/*      */ 
/* 1116 */     Object localObject1 = getPaint();
/* 1117 */     CompositeMode localCompositeMode = getCompositeMode();
/*      */ 
/* 1120 */     int i = (localCompositeMode == CompositeMode.SRC_OVER) && (((Paint)localObject1).getType() == Paint.Type.COLOR) && (localBaseTransform1.is2D()) ? 1 : 0;
/*      */ 
/* 1127 */     if ((paramFontStrike.getAAMode() == 1) && (i == 0)) {
/* 1128 */       FontResource localFontResource = paramFontStrike.getFontResource();
/* 1129 */       f2 = paramFontStrike.getSize();
/* 1130 */       BaseTransform localBaseTransform2 = paramFontStrike.getTransform();
/* 1131 */       paramFontStrike = localFontResource.getStrike(f2, localBaseTransform2, 0);
/*      */     }
/*      */ 
/* 1134 */     float f1 = 0.0F; float f2 = 0.0F; float f3 = 0.0F; float f4 = 0.0F;
/* 1135 */     if ((this.paint.getType().isGradient()) && (((Gradient)this.paint).isProportional()))
/*      */     {
/* 1139 */       localObject2 = this.nodeBounds;
/* 1140 */       if (localObject2 == null) {
/* 1141 */         localObject2 = paramFontStrike.getStringBounds(paramString);
/* 1142 */         f1 = paramFloat1;
/* 1143 */         f2 = paramFloat2;
/*      */       }
/*      */ 
/* 1146 */       f1 += ((RectBounds)localObject2).getMinX();
/* 1147 */       f2 += ((RectBounds)localObject2).getMinY();
/* 1148 */       f3 = ((RectBounds)localObject2).getWidth();
/* 1149 */       f4 = ((RectBounds)localObject2).getHeight();
/*      */     }
/*      */ 
/* 1153 */     Object localObject2 = new Point2D(paramFloat1, paramFloat2);
/* 1154 */     if (this.isSimpleTranslate) {
/* 1155 */       localBaseTransform1 = IDENT;
/* 1156 */       localObject2.x += this.transX;
/* 1157 */       localObject2.y += this.transY;
/*      */     }
/*      */     else
/*      */     {
/* 1161 */       localBaseTransform1.transform((Point2D)localObject2, (Point2D)localObject2);
/*      */     }
/*      */ 
/* 1170 */     GlyphCache localGlyphCache = this.context.getGlyphCache(paramFontStrike);
/* 1171 */     Texture localTexture = localGlyphCache.getBackingStore();
/*      */ 
/* 1175 */     if ((paramFontStrike.getAAMode() == 1) && (i != 0)) {
/* 1176 */       if (this.nodeBounds == null)
/*      */       {
/* 1181 */         RectBounds localRectBounds = paramFontStrike.getStringBounds(paramString);
/*      */ 
/* 1183 */         localRectBounds.setBounds(localRectBounds.getMinX() + paramFloat1 - 2.0F, localRectBounds.getMinY() + paramFloat2, localRectBounds.getMaxX() + paramFloat1 + 2.0F, localRectBounds.getMaxY() + paramFloat2 + 1.0F);
/*      */ 
/* 1187 */         setNodeBounds(localRectBounds);
/* 1188 */         initLCDSampleRT();
/* 1189 */         setNodeBounds(null);
/*      */       } else {
/* 1191 */         initLCDSampleRT();
/*      */       }
/*      */ 
/* 1194 */       float f5 = PrismFontUtils.getLCDContrast();
/* 1195 */       float f6 = 1.0F / f5;
/* 1196 */       Color localColor = (Color)localObject1;
/* 1197 */       localObject1 = new Color((float)Math.pow(localColor.getRed(), f5), (float)Math.pow(localColor.getGreen(), f5), (float)Math.pow(localColor.getBlue(), f5), (float)Math.pow(localColor.getAlpha(), f5));
/*      */ 
/* 1201 */       if (paramColor != null) {
/* 1202 */         paramColor = new Color((float)Math.pow(paramColor.getRed(), f5), (float)Math.pow(paramColor.getGreen(), f5), (float)Math.pow(paramColor.getBlue(), f5), (float)Math.pow(paramColor.getAlpha(), f5));
/*      */       }
/*      */ 
/* 1212 */       setCompositeMode(CompositeMode.SRC);
/*      */ 
/* 1215 */       Shader localShader = this.context.validateLCDOp(this, IDENT, this.context.getLCDBuffer(), localTexture, false, (Paint)localObject1);
/*      */ 
/* 1219 */       float f7 = 1.0F / localTexture.getPhysicalWidth();
/* 1220 */       localShader.setConstant("gamma", f6, f5, f7);
/* 1221 */       setCompositeMode(localCompositeMode);
/* 1222 */       if (this.isSimpleTranslate)
/*      */       {
/* 1225 */         ((Point2D)localObject2).x = ((float)Math.round(3.0D * ((Point2D)localObject2).x) / 3.0F);
/* 1226 */         ((Point2D)localObject2).y = Math.round(((Point2D)localObject2).y);
/*      */       }
/*      */     } else {
/* 1229 */       if (this.isSimpleTranslate)
/*      */       {
/* 1234 */         ((Point2D)localObject2).x = Math.round(((Point2D)localObject2).x);
/* 1235 */         ((Point2D)localObject2).y = Math.round(((Point2D)localObject2).y);
/*      */       }
/* 1237 */       this.context.validatePaintOp(this, IDENT, localTexture, f1, f2, f3, f4);
/*      */     }
/* 1239 */     if ((paramColor != null) && (paramInt1 != paramInt2) && ((localObject1 instanceof Color)))
/*      */     {
/* 1243 */       localGlyphCache.renderWithColorRange(this.context, paramString, ((Point2D)localObject2).x, ((Point2D)localObject2).y, paramInt1, paramInt2, paramColor, (Color)localObject1);
/*      */     }
/*      */     else
/*      */     {
/* 1247 */       localGlyphCache.render(this.context, paramString, ((Point2D)localObject2).x, ((Point2D)localObject2).y);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void drawLCDBuffer(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8)
/*      */   {
/* 1258 */     this.context.setRenderTarget(this);
/* 1259 */     this.context.getVertexBuffer().addQuad(paramFloat1, paramFloat2, paramFloat1 + paramFloat3, paramFloat2 + paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8);
/*      */   }
/*      */ 
/*      */   public boolean canReadBack() {
/* 1263 */     RenderTarget localRenderTarget = getRenderTarget();
/* 1264 */     return ((localRenderTarget instanceof ReadbackRenderTarget)) && (((ReadbackRenderTarget)localRenderTarget).getBackBuffer() != null);
/*      */   }
/*      */ 
/*      */   public RTTexture readBack(Rectangle paramRectangle)
/*      */   {
/* 1269 */     RenderTarget localRenderTarget = getRenderTarget();
/* 1270 */     this.context.flushVertexBuffer();
/* 1271 */     this.context.validateLCDBuffer(localRenderTarget);
/* 1272 */     RTTexture localRTTexture = this.context.getLCDBuffer();
/* 1273 */     Texture localTexture = ((ReadbackRenderTarget)localRenderTarget).getBackBuffer();
/*      */ 
/* 1275 */     float f1 = paramRectangle.x;
/* 1276 */     float f2 = paramRectangle.y;
/* 1277 */     float f3 = f1 + paramRectangle.width;
/* 1278 */     float f4 = f2 + paramRectangle.height;
/*      */ 
/* 1282 */     BaseShaderGraphics localBaseShaderGraphics = (BaseShaderGraphics)localRTTexture.createGraphics();
/* 1283 */     localBaseShaderGraphics.setCompositeMode(CompositeMode.SRC);
/* 1284 */     this.context.validateTextureOp(localBaseShaderGraphics, IDENT, localTexture, localTexture.getPixelFormat());
/*      */ 
/* 1287 */     localBaseShaderGraphics.drawTexture(localTexture, 0.0F, 0.0F, paramRectangle.width, paramRectangle.height, f1, f2, f3, f4);
/* 1288 */     this.context.flushVertexBuffer();
/*      */ 
/* 1291 */     this.context.setRenderTarget(this);
/* 1292 */     return localRTTexture;
/*      */   }
/*      */ 
/*      */   public void releaseReadBackBuffer(RTTexture paramRTTexture)
/*      */   {
/*      */   }
/*      */ 
/*      */   static
/*      */   {
/*  329 */     String str = (String)AccessController.doPrivileged(new PrivilegedAction() {
/*      */       public Object run() {
/*  331 */         return System.getProperty("prism.primshaderpad");
/*      */       }
/*      */     });
/*  334 */     if (str == null) {
/*  335 */       FRINGE_FACTOR = -0.5F;
/*      */     } else {
/*  337 */       FRINGE_FACTOR = -Float.valueOf(str).floatValue();
/*  338 */       System.out.println("Prism ShaderGraphics primitive shader pad = " + FRINGE_FACTOR);
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.impl.ps.BaseShaderGraphics
 * JD-Core Version:    0.6.2
 */