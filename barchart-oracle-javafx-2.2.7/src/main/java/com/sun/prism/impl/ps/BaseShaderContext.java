/*     */ package com.sun.prism.impl.ps;
/*     */ 
/*     */ import com.sun.glass.ui.Screen;
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.transform.Affine3D;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.prism.CompositeMode;
/*     */ import com.sun.prism.PixelFormat;
/*     */ import com.sun.prism.RTTexture;
/*     */ import com.sun.prism.RenderTarget;
/*     */ import com.sun.prism.ResourceFactory;
/*     */ import com.sun.prism.Texture;
/*     */ import com.sun.prism.Texture.WrapMode;
/*     */ import com.sun.prism.camera.PrismCameraImpl;
/*     */ import com.sun.prism.impl.BaseContext;
/*     */ import com.sun.prism.impl.BaseGraphics;
/*     */ import com.sun.prism.impl.VertexBuffer;
/*     */ import com.sun.prism.paint.Color;
/*     */ import com.sun.prism.paint.Gradient;
/*     */ import com.sun.prism.paint.ImagePattern;
/*     */ import com.sun.prism.paint.LinearGradient;
/*     */ import com.sun.prism.paint.Paint;
/*     */ import com.sun.prism.paint.Paint.Type;
/*     */ import com.sun.prism.paint.RadialGradient;
/*     */ import com.sun.prism.ps.Shader;
/*     */ import com.sun.prism.ps.ShaderFactory;
/*     */ 
/*     */ public abstract class BaseShaderContext extends BaseContext
/*     */ {
/*  89 */   private static final int NUM_STOCK_SHADER_SLOTS = MaskType.values().length << 4;
/*     */ 
/*  92 */   private final Shader[] stockShaders = new Shader[NUM_STOCK_SHADER_SLOTS];
/*     */   private Shader textureRGBShader;
/*     */   private Shader textureYV12Shader;
/*     */   private Shader textureFirstLCDShader;
/*     */   private Shader textureSecondLCDShader;
/*     */   private Shader externalShader;
/*     */   private RTTexture lcdBuffer;
/*     */   private final ShaderFactory factory;
/*     */   private State state;
/*     */ 
/*     */   protected BaseShaderContext(Screen paramScreen, ShaderFactory paramShaderFactory, VertexBuffer paramVertexBuffer)
/*     */   {
/* 105 */     super(paramScreen, paramShaderFactory, paramVertexBuffer);
/* 106 */     this.factory = paramShaderFactory;
/* 107 */     init();
/*     */   }
/*     */ 
/*     */   protected void init() {
/* 111 */     this.state = null;
/* 112 */     if ((this.externalShader != null) && (!this.externalShader.isValid())) {
/* 113 */       this.externalShader.dispose();
/* 114 */       this.externalShader = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   protected abstract State updateRenderTarget(RenderTarget paramRenderTarget, PrismCameraImpl paramPrismCameraImpl, boolean paramBoolean);
/*     */ 
/*     */   protected abstract void updateTexture(int paramInt, Texture paramTexture);
/*     */ 
/*     */   protected abstract void updateShaderTransform(Shader paramShader, BaseTransform paramBaseTransform);
/*     */ 
/*     */   protected abstract void updateClipRect(Rectangle paramRectangle);
/*     */ 
/*     */   protected abstract void updateCompositeMode(CompositeMode paramCompositeMode);
/*     */ 
/*     */   private static int getStockShaderIndex(MaskType paramMaskType, Paint paramPaint)
/*     */   {
/*     */     int i;
/*     */     int j;
/* 152 */     if (paramPaint == null) {
/* 153 */       i = 0;
/* 154 */       j = 0;
/*     */     } else {
/* 156 */       i = paramPaint.getType().ordinal();
/* 157 */       if (paramPaint.getType().isGradient())
/* 158 */         j = ((Gradient)paramPaint).getSpreadMethod();
/*     */       else {
/* 160 */         j = 0;
/*     */       }
/*     */     }
/* 163 */     return paramMaskType.ordinal() << 4 | i << 2 | j << 0;
/*     */   }
/*     */ 
/*     */   private Shader getPaintShader(MaskType paramMaskType, Paint paramPaint) {
/* 167 */     int i = getStockShaderIndex(paramMaskType, paramPaint);
/* 168 */     Shader localShader = this.stockShaders[i];
/* 169 */     if ((localShader != null) && (!localShader.isValid())) {
/* 170 */       localShader.dispose();
/* 171 */       localShader = null;
/*     */     }
/* 173 */     if (localShader == null) {
/* 174 */       String str = paramMaskType.getName() + "_" + paramPaint.getType().getName();
/*     */ 
/* 176 */       if (paramPaint.getType().isGradient()) {
/* 177 */         Gradient localGradient = (Gradient)paramPaint;
/* 178 */         int j = localGradient.getSpreadMethod();
/* 179 */         if (j == 0)
/* 180 */           str = str + "_PAD";
/* 181 */         else if (j == 1)
/* 182 */           str = str + "_REFLECT";
/* 183 */         else if (j == 2) {
/* 184 */           str = str + "_REPEAT";
/*     */         }
/*     */       }
/* 187 */       localShader = this.stockShaders[i] =  = this.factory.createStockShader(str);
/*     */     }
/* 189 */     return localShader;
/*     */   }
/*     */ 
/*     */   private void updatePaintShader(BaseShaderGraphics paramBaseShaderGraphics, Shader paramShader, Paint paramPaint, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/* 195 */     Paint.Type localType = paramPaint.getType();
/* 196 */     if (localType == Paint.Type.COLOR)
/*     */       return;
/*     */     float f1;
/*     */     float f2;
/*     */     float f3;
/*     */     float f4;
/* 201 */     if (paramPaint.isProportional()) {
/* 202 */       f1 = paramFloat1; f2 = paramFloat2; f3 = paramFloat3; f4 = paramFloat4;
/*     */     } else {
/* 204 */       f1 = 0.0F; f2 = 0.0F; f3 = 1.0F; f4 = 1.0F;
/*     */     }
/*     */ 
/* 207 */     switch (1.$SwitchMap$com$sun$prism$paint$Paint$Type[localType.ordinal()]) {
/*     */     case 1:
/* 209 */       PaintHelper.setLinearGradient(paramBaseShaderGraphics, paramShader, (LinearGradient)paramPaint, f1, f2, f3, f4);
/*     */ 
/* 212 */       break;
/*     */     case 2:
/* 214 */       PaintHelper.setRadialGradient(paramBaseShaderGraphics, paramShader, (RadialGradient)paramPaint, f1, f2, f3, f4);
/*     */ 
/* 217 */       break;
/*     */     case 3:
/* 219 */       PaintHelper.setImagePattern(paramBaseShaderGraphics, paramShader, (ImagePattern)paramPaint, f1, f2, f3, f4);
/*     */     }
/*     */   }
/*     */ 
/*     */   private Shader getTextureRGBShader()
/*     */   {
/* 228 */     if ((this.textureRGBShader != null) && (!this.textureRGBShader.isValid())) {
/* 229 */       this.textureRGBShader.dispose();
/* 230 */       this.textureRGBShader = null;
/*     */     }
/* 232 */     if (this.textureRGBShader == null) {
/* 233 */       this.textureRGBShader = this.factory.createStockShader("Solid_TextureRGB");
/*     */     }
/* 235 */     return this.textureRGBShader;
/*     */   }
/*     */ 
/*     */   private Shader getTextureYV12Shader() {
/* 239 */     if ((this.textureYV12Shader != null) && (!this.textureYV12Shader.isValid())) {
/* 240 */       this.textureYV12Shader.dispose();
/* 241 */       this.textureYV12Shader = null;
/*     */     }
/* 243 */     if (this.textureYV12Shader == null) {
/* 244 */       this.textureYV12Shader = this.factory.createStockShader("Solid_TextureYV12");
/*     */     }
/* 246 */     return this.textureYV12Shader;
/*     */   }
/*     */ 
/*     */   private Shader getTextureFirstPassLCDShader() {
/* 250 */     if ((this.textureFirstLCDShader != null) && (!this.textureFirstLCDShader.isValid())) {
/* 251 */       this.textureFirstLCDShader.dispose();
/* 252 */       this.textureFirstLCDShader = null;
/*     */     }
/* 254 */     if (this.textureFirstLCDShader == null) {
/* 255 */       this.textureFirstLCDShader = this.factory.createStockShader("Solid_TextureFirstPassLCD");
/*     */     }
/* 257 */     return this.textureFirstLCDShader;
/*     */   }
/*     */ 
/*     */   private Shader getTextureSecondPassLCDShader() {
/* 261 */     if ((this.textureSecondLCDShader != null) && (!this.textureSecondLCDShader.isValid())) {
/* 262 */       this.textureSecondLCDShader.dispose();
/* 263 */       this.textureSecondLCDShader = null;
/*     */     }
/* 265 */     if (this.textureSecondLCDShader == null) {
/* 266 */       this.textureSecondLCDShader = this.factory.createStockShader("Solid_TextureSecondPassLCD");
/*     */     }
/* 268 */     return this.textureSecondLCDShader;
/*     */   }
/*     */ 
/*     */   private void updatePerVertexColor(Paint paramPaint, float paramFloat)
/*     */   {
/* 273 */     if ((paramPaint != null) && (paramPaint.getType() == Paint.Type.COLOR))
/* 274 */       getVertexBuffer().setPerVertexColor((Color)paramPaint, paramFloat);
/*     */     else
/* 276 */       getVertexBuffer().setPerVertexColor(paramFloat);
/*     */   }
/*     */ 
/*     */   public void validatePaintOp(BaseGraphics paramBaseGraphics, BaseTransform paramBaseTransform, Texture paramTexture, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/* 285 */     validatePaintOp((BaseShaderGraphics)paramBaseGraphics, paramBaseTransform, paramTexture, paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/*     */   }
/*     */ 
/*     */   Shader validatePaintOp(BaseShaderGraphics paramBaseShaderGraphics, BaseTransform paramBaseTransform, MaskType paramMaskType, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/* 293 */     return validatePaintOp(paramBaseShaderGraphics, paramBaseTransform, paramMaskType, null, paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/*     */   }
/*     */ 
/*     */   Shader validatePaintOp(BaseShaderGraphics paramBaseShaderGraphics, BaseTransform paramBaseTransform, MaskType paramMaskType, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10)
/*     */   {
/* 305 */     if ((this.state.lastConst1 != paramFloat5) || (this.state.lastConst2 != paramFloat6) || (this.state.lastConst3 != paramFloat7) || (this.state.lastConst4 != paramFloat8) || (this.state.lastConst5 != paramFloat9) || (this.state.lastConst6 != paramFloat10))
/*     */     {
/* 309 */       flushVertexBuffer();
/*     */ 
/* 311 */       this.state.lastConst1 = paramFloat5;
/* 312 */       this.state.lastConst2 = paramFloat6;
/* 313 */       this.state.lastConst3 = paramFloat7;
/* 314 */       this.state.lastConst4 = paramFloat8;
/* 315 */       this.state.lastConst5 = paramFloat9;
/* 316 */       this.state.lastConst6 = paramFloat10;
/*     */     }
/*     */ 
/* 319 */     return validatePaintOp(paramBaseShaderGraphics, paramBaseTransform, paramMaskType, null, paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/*     */   }
/*     */ 
/*     */   Shader validatePaintOp(BaseShaderGraphics paramBaseShaderGraphics, BaseTransform paramBaseTransform, Texture paramTexture, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/* 326 */     return validatePaintOp(paramBaseShaderGraphics, paramBaseTransform, MaskType.TEXTURE, paramTexture, paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/*     */   }
/*     */ 
/*     */   private Shader validatePaintOp(BaseShaderGraphics paramBaseShaderGraphics, BaseTransform paramBaseTransform, MaskType paramMaskType, Texture paramTexture, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/* 334 */     if (paramMaskType == null) {
/* 335 */       throw new InternalError("maskType must be non-null");
/*     */     }
/*     */ 
/* 338 */     if (this.externalShader == null) {
/* 339 */       Paint localPaint = paramBaseShaderGraphics.getPaint();
/* 340 */       Texture localTexture1 = null;
/* 341 */       Texture localTexture2 = null;
/* 342 */       Texture localTexture3 = null;
/* 343 */       if (localPaint.getType().isGradient())
/*     */       {
/* 351 */         flushVertexBuffer();
/*     */ 
/* 355 */         localTexture1 = PaintHelper.getGradientTexture(paramBaseShaderGraphics, (Gradient)localPaint);
/* 356 */       } else if (localPaint.getType() == Paint.Type.IMAGE_PATTERN) {
/* 357 */         localObject = (ImagePattern)localPaint;
/* 358 */         localTexture1 = paramBaseShaderGraphics.getResourceFactory().getCachedTexture(((ImagePattern)localObject).getImage());
/* 359 */         localTexture1.setWrapMode(Texture.WrapMode.REPEAT);
/*     */       }
/*     */ 
/* 370 */       if (paramTexture != null) {
/* 371 */         localTexture2 = paramTexture;
/* 372 */         localTexture3 = localTexture1;
/*     */       } else {
/* 374 */         localTexture2 = localTexture1;
/* 375 */         localTexture3 = null;
/*     */       }
/* 377 */       Object localObject = getPaintShader(paramMaskType, localPaint);
/* 378 */       checkState(paramBaseShaderGraphics, paramBaseTransform, (Shader)localObject, localTexture2, localTexture3);
/* 379 */       updatePaintShader(paramBaseShaderGraphics, (Shader)localObject, localPaint, paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/* 380 */       updatePerVertexColor(localPaint, paramBaseShaderGraphics.getExtraAlpha());
/* 381 */       return localObject;
/*     */     }
/*     */ 
/* 384 */     checkState(paramBaseShaderGraphics, paramBaseTransform, this.externalShader, paramTexture, null);
/* 385 */     updatePerVertexColor(null, paramBaseShaderGraphics.getExtraAlpha());
/* 386 */     return this.externalShader;
/*     */   }
/*     */ 
/*     */   public void validateTextureOp(BaseGraphics paramBaseGraphics, BaseTransform paramBaseTransform, Texture paramTexture, PixelFormat paramPixelFormat)
/*     */   {
/* 394 */     validateTextureOp((BaseShaderGraphics)paramBaseGraphics, paramBaseTransform, paramTexture, null, paramPixelFormat);
/*     */   }
/*     */ 
/*     */   public Shader validateLCDOp(BaseShaderGraphics paramBaseShaderGraphics, BaseTransform paramBaseTransform, Texture paramTexture1, Texture paramTexture2, boolean paramBoolean, Paint paramPaint)
/*     */   {
/* 403 */     Shader localShader = paramBoolean ? getTextureFirstPassLCDShader() : getTextureSecondPassLCDShader();
/*     */ 
/* 406 */     checkState(paramBaseShaderGraphics, paramBaseTransform, localShader, paramTexture1, paramTexture2);
/* 407 */     updatePerVertexColor(paramPaint, paramBaseShaderGraphics.getExtraAlpha());
/* 408 */     return localShader;
/*     */   }
/*     */ 
/*     */   Shader validateTextureOp(BaseShaderGraphics paramBaseShaderGraphics, BaseTransform paramBaseTransform, Texture[] paramArrayOfTexture, PixelFormat paramPixelFormat)
/*     */   {
/* 414 */     Shader localShader = null;
/*     */ 
/* 416 */     if (paramPixelFormat == PixelFormat.MULTI_YCbCr_420)
/*     */     {
/* 418 */       if (paramArrayOfTexture.length < 3) {
/* 419 */         return null;
/*     */       }
/*     */ 
/* 422 */       if (this.externalShader == null)
/* 423 */         localShader = getTextureYV12Shader();
/*     */       else
/* 425 */         localShader = this.externalShader;
/*     */     }
/*     */     else {
/* 428 */       return null;
/*     */     }
/*     */ 
/* 431 */     if (null != localShader) {
/* 432 */       checkState(paramBaseShaderGraphics, paramBaseTransform, localShader, paramArrayOfTexture);
/* 433 */       updatePerVertexColor(null, paramBaseShaderGraphics.getExtraAlpha());
/*     */     }
/* 435 */     return localShader;
/*     */   }
/*     */ 
/*     */   Shader validateTextureOp(BaseShaderGraphics paramBaseShaderGraphics, BaseTransform paramBaseTransform, Texture paramTexture1, Texture paramTexture2, PixelFormat paramPixelFormat)
/*     */   {
/*     */     Shader localShader;
/* 442 */     if (this.externalShader == null)
/* 443 */       switch (1.$SwitchMap$com$sun$prism$PixelFormat[paramPixelFormat.ordinal()]) {
/*     */       case 1:
/*     */       case 2:
/*     */       case 3:
/*     */       case 4:
/*     */       case 5:
/* 449 */         localShader = getTextureRGBShader();
/* 450 */         break;
/*     */       case 6:
/*     */       case 7:
/*     */       default:
/* 454 */         throw new InternalError("Pixel format not supported: " + paramPixelFormat);
/*     */       }
/*     */     else {
/* 457 */       localShader = this.externalShader;
/*     */     }
/* 459 */     checkState(paramBaseShaderGraphics, paramBaseTransform, localShader, paramTexture1, paramTexture2);
/* 460 */     updatePerVertexColor(null, paramBaseShaderGraphics.getExtraAlpha());
/* 461 */     return localShader;
/*     */   }
/*     */ 
/*     */   void setExternalShader(BaseShaderGraphics paramBaseShaderGraphics, Shader paramShader)
/*     */   {
/* 478 */     flushVertexBuffer();
/* 479 */     if (paramShader != null) {
/* 480 */       paramShader.enable();
/*     */     }
/* 482 */     this.externalShader = paramShader;
/*     */   }
/*     */ 
/*     */   private void checkState(BaseShaderGraphics paramBaseShaderGraphics, BaseTransform paramBaseTransform, Shader paramShader, Texture paramTexture1, Texture paramTexture2)
/*     */   {
/* 490 */     setRenderTarget(paramBaseShaderGraphics);
/*     */ 
/* 492 */     setTexture(0, paramTexture1);
/* 493 */     setTexture(1, paramTexture2);
/*     */ 
/* 495 */     if (paramShader != this.state.lastShader) {
/* 496 */       flushVertexBuffer();
/* 497 */       paramShader.enable();
/* 498 */       this.state.lastShader = paramShader;
/*     */ 
/* 502 */       this.state.isXformValid = false;
/*     */     }
/*     */ 
/* 505 */     if ((!this.state.isXformValid) || (!paramBaseTransform.equals(this.state.lastTransform))) {
/* 506 */       flushVertexBuffer();
/* 507 */       updateShaderTransform(paramShader, paramBaseTransform);
/* 508 */       this.state.lastTransform.setTransform(paramBaseTransform);
/* 509 */       this.state.isXformValid = true;
/*     */     }
/*     */ 
/* 512 */     Rectangle localRectangle = paramBaseShaderGraphics.getClipRectNoClone();
/* 513 */     if (localRectangle != this.state.lastClip) {
/* 514 */       flushVertexBuffer();
/* 515 */       updateClipRect(localRectangle);
/* 516 */       this.state.lastClip = localRectangle;
/*     */     }
/*     */ 
/* 519 */     CompositeMode localCompositeMode = paramBaseShaderGraphics.getCompositeMode();
/* 520 */     if (localCompositeMode != this.state.lastComp) {
/* 521 */       flushVertexBuffer();
/* 522 */       updateCompositeMode(localCompositeMode);
/* 523 */       this.state.lastComp = localCompositeMode;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void checkState(BaseShaderGraphics paramBaseShaderGraphics, BaseTransform paramBaseTransform, Shader paramShader, Texture[] paramArrayOfTexture)
/*     */   {
/* 532 */     setRenderTarget(paramBaseShaderGraphics);
/*     */ 
/* 535 */     int i = Math.max(0, Math.min(paramArrayOfTexture.length, 4));
/* 536 */     for (int j = 0; j < i; j++) {
/* 537 */       setTexture(j, paramArrayOfTexture[j]);
/*     */     }
/*     */ 
/* 540 */     if (paramShader != this.state.lastShader) {
/* 541 */       flushVertexBuffer();
/* 542 */       paramShader.enable();
/* 543 */       this.state.lastShader = paramShader;
/*     */ 
/* 547 */       this.state.isXformValid = false;
/*     */     }
/*     */ 
/* 550 */     if ((!this.state.isXformValid) || (!paramBaseTransform.equals(this.state.lastTransform))) {
/* 551 */       flushVertexBuffer();
/* 552 */       updateShaderTransform(paramShader, paramBaseTransform);
/* 553 */       this.state.lastTransform.setTransform(paramBaseTransform);
/* 554 */       this.state.isXformValid = true;
/*     */     }
/*     */ 
/* 557 */     Rectangle localRectangle = paramBaseShaderGraphics.getClipRectNoClone();
/* 558 */     if (localRectangle != this.state.lastClip) {
/* 559 */       flushVertexBuffer();
/* 560 */       updateClipRect(localRectangle);
/* 561 */       this.state.lastClip = localRectangle;
/*     */     }
/*     */ 
/* 564 */     CompositeMode localCompositeMode = paramBaseShaderGraphics.getCompositeMode();
/* 565 */     if (localCompositeMode != this.state.lastComp) {
/* 566 */       flushVertexBuffer();
/* 567 */       updateCompositeMode(localCompositeMode);
/* 568 */       this.state.lastComp = localCompositeMode;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void setTexture(int paramInt, Texture paramTexture) {
/* 573 */     if (paramTexture != this.state.lastTextures[paramInt]) {
/* 574 */       flushVertexBuffer();
/* 575 */       updateTexture(paramInt, paramTexture);
/* 576 */       this.state.lastTextures[paramInt] = paramTexture;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void initLCDBuffer(int paramInt1, int paramInt2)
/*     */   {
/* 582 */     this.lcdBuffer = this.factory.createRTTexture(paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   public void disposeLCDBuffer() {
/* 586 */     if (this.lcdBuffer != null) {
/* 587 */       this.lcdBuffer.dispose();
/* 588 */       this.lcdBuffer = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public RTTexture getLCDBuffer() {
/* 593 */     return this.lcdBuffer;
/*     */   }
/*     */ 
/*     */   public void validateLCDBuffer(RenderTarget paramRenderTarget)
/*     */   {
/* 598 */     if ((this.lcdBuffer == null) || (this.lcdBuffer.getPhysicalWidth() < paramRenderTarget.getPhysicalWidth()) || (this.lcdBuffer.getPhysicalHeight() < paramRenderTarget.getPhysicalHeight()))
/*     */     {
/* 602 */       disposeLCDBuffer();
/* 603 */       initLCDBuffer(paramRenderTarget.getPhysicalWidth(), paramRenderTarget.getPhysicalHeight());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void setRenderTarget(RenderTarget paramRenderTarget, PrismCameraImpl paramPrismCameraImpl, boolean paramBoolean)
/*     */   {
/* 611 */     if ((this.state == null) || (paramRenderTarget != this.state.lastRenderTarget) || (paramPrismCameraImpl != this.state.lastCamera) || (paramBoolean != this.state.lastDepthTest))
/*     */     {
/* 616 */       flushVertexBuffer();
/* 617 */       this.state = updateRenderTarget(paramRenderTarget, paramPrismCameraImpl, paramBoolean);
/* 618 */       this.state.lastRenderTarget = paramRenderTarget;
/* 619 */       this.state.lastCamera = paramPrismCameraImpl;
/* 620 */       this.state.lastDepthTest = paramBoolean;
/*     */ 
/* 625 */       this.state.isXformValid = false;
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void releaseRenderTarget()
/*     */   {
/* 632 */     if (this.state != null) {
/* 633 */       this.state.lastRenderTarget = null;
/* 634 */       for (int i = 0; i < this.state.lastTextures.length; i++)
/* 635 */         this.state.lastTextures[i] = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class State
/*     */   {
/*     */     private Shader lastShader;
/*     */     private RenderTarget lastRenderTarget;
/*     */     private PrismCameraImpl lastCamera;
/*     */     private boolean lastDepthTest;
/* 124 */     private BaseTransform lastTransform = new Affine3D();
/*     */     private Rectangle lastClip;
/*     */     private CompositeMode lastComp;
/* 127 */     private Texture[] lastTextures = new Texture[4];
/*     */     private boolean isXformValid;
/*     */     private float lastConst1;
/*     */     private float lastConst2;
/*     */     private float lastConst3;
/*     */     private float lastConst4;
/*     */     private float lastConst5;
/*     */     private float lastConst6;
/*     */   }
/*     */ 
/*     */   public static enum MaskType
/*     */   {
/*  56 */     SOLID("Solid"), 
/*  57 */     TEXTURE("Texture"), 
/*  58 */     FILL_PGRAM("FillPgram"), 
/*  59 */     DRAW_PGRAM("DrawPgram", FILL_PGRAM), 
/*  60 */     FILL_CIRCLE("FillCircle"), 
/*  61 */     DRAW_CIRCLE("DrawCircle", FILL_CIRCLE), 
/*  62 */     FILL_ELLIPSE("FillEllipse"), 
/*  63 */     DRAW_ELLIPSE("DrawEllipse", FILL_ELLIPSE), 
/*  64 */     FILL_ROUNDRECT("FillRoundRect"), 
/*  65 */     DRAW_ROUNDRECT("DrawRoundRect", FILL_ROUNDRECT), 
/*  66 */     DRAW_SEMIROUNDRECT("DrawSemiRoundRect"), 
/*  67 */     FILL_CUBICCURVE("FillCubicCurve");
/*     */ 
/*     */     private String name;
/*     */     private MaskType filltype;
/*     */ 
/*  72 */     private MaskType(String paramString) { this.name = paramString; }
/*     */ 
/*     */     private MaskType(String paramString, MaskType paramMaskType) {
/*  75 */       this.name = paramString;
/*  76 */       this.filltype = paramMaskType;
/*     */     }
/*     */     public String getName() {
/*  79 */       return this.name;
/*     */     }
/*     */     public MaskType getFillType() {
/*  82 */       return this.filltype;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.impl.ps.BaseShaderContext
 * JD-Core Version:    0.6.2
 */