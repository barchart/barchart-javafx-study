/*     */ package com.sun.scenario.effect.impl.prism.ps;
/*     */ 
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.prism.Texture;
/*     */ import com.sun.prism.ps.Shader;
/*     */ import com.sun.prism.ps.ShaderGraphics;
/*     */ import com.sun.scenario.effect.FilterContext;
/*     */ import com.sun.scenario.effect.Filterable;
/*     */ import com.sun.scenario.effect.ImageData;
/*     */ import com.sun.scenario.effect.impl.Renderer;
/*     */ import com.sun.scenario.effect.impl.prism.PrTexture;
/*     */ 
/*     */ public abstract class PPSOneSamplerPeer extends PPSEffectPeer
/*     */ {
/*     */   private Shader shader;
/*     */ 
/*     */   protected PPSOneSamplerPeer(FilterContext paramFilterContext, Renderer paramRenderer, String paramString)
/*     */   {
/*  45 */     super(paramFilterContext, paramRenderer, paramString);
/*     */   }
/*     */ 
/*     */   public void dispose()
/*     */   {
/*  50 */     if (this.shader != null)
/*  51 */       this.shader.dispose();
/*     */   }
/*     */ 
/*     */   ImageData filterImpl(ImageData[] paramArrayOfImageData)
/*     */   {
/*  57 */     Filterable localFilterable = paramArrayOfImageData[0].getUntransformedImage();
/*  58 */     PrTexture localPrTexture = (PrTexture)localFilterable;
/*  59 */     Rectangle localRectangle1 = paramArrayOfImageData[0].getUntransformedBounds();
/*  60 */     Rectangle localRectangle2 = getDestBounds();
/*  61 */     int i = localRectangle2.width;
/*  62 */     int j = localRectangle2.height;
/*     */ 
/*  64 */     PPSRenderer localPPSRenderer = getRenderer();
/*  65 */     PPSDrawable localPPSDrawable = localPPSRenderer.getCompatibleImage(i, j);
/*  66 */     if (localPPSDrawable == null) {
/*  67 */       localPPSRenderer.markLost();
/*  68 */       return new ImageData(getFilterContext(), localPPSDrawable, localRectangle2);
/*     */     }
/*  70 */     setDestNativeBounds(localPPSDrawable.getPhysicalWidth(), localPPSDrawable.getPhysicalHeight());
/*     */ 
/*  72 */     BaseTransform localBaseTransform = paramArrayOfImageData[0].getTransform();
/*  73 */     setInputBounds(0, localRectangle1);
/*  74 */     setInputTransform(0, localBaseTransform);
/*  75 */     setInputNativeBounds(0, localPrTexture.getNativeBounds());
/*  76 */     float[] arrayOfFloat = new float[8];
/*  77 */     int k = getTextureCoordinates(0, arrayOfFloat, localRectangle1.x, localRectangle1.y, localFilterable.getPhysicalWidth(), localFilterable.getPhysicalHeight(), localRectangle2, localBaseTransform);
/*     */ 
/*  84 */     ShaderGraphics localShaderGraphics = localPPSDrawable.createGraphics();
/*  85 */     if (localShaderGraphics == null) {
/*  86 */       localPPSRenderer.markLost();
/*  87 */       return new ImageData(getFilterContext(), localPPSDrawable, localRectangle2);
/*     */     }
/*  89 */     if (this.shader == null) {
/*  90 */       this.shader = createShader();
/*     */     }
/*  92 */     if ((this.shader == null) || (!this.shader.isValid())) {
/*  93 */       localPPSRenderer.markLost();
/*  94 */       return new ImageData(getFilterContext(), localPPSDrawable, localRectangle2);
/*     */     }
/*  96 */     localShaderGraphics.setExternalShader(this.shader);
/*  97 */     updateShader(this.shader);
/*     */ 
/*  99 */     float f1 = 0.0F;
/* 100 */     float f2 = 0.0F;
/* 101 */     float f3 = i;
/* 102 */     float f4 = j;
/*     */ 
/* 105 */     Texture localTexture = localPrTexture.getTextureObject();
/* 106 */     if (localTexture == null) {
/* 107 */       localPPSRenderer.markLost();
/* 108 */       return new ImageData(getFilterContext(), localPPSDrawable, localRectangle2);
/*     */     }
/* 110 */     float f5 = localTexture.getContentX() / localTexture.getPhysicalWidth();
/* 111 */     float f6 = localTexture.getContentY() / localTexture.getPhysicalHeight();
/* 112 */     float f7 = f5 + arrayOfFloat[0];
/* 113 */     float f8 = f6 + arrayOfFloat[1];
/* 114 */     float f9 = f5 + arrayOfFloat[2];
/* 115 */     float f10 = f6 + arrayOfFloat[3];
/* 116 */     if (k < 8) {
/* 117 */       localShaderGraphics.drawTextureRaw(localTexture, f1, f2, f3, f4, f7, f8, f9, f10);
/*     */     }
/*     */     else
/*     */     {
/* 121 */       float f11 = f5 + arrayOfFloat[4];
/* 122 */       float f12 = f6 + arrayOfFloat[5];
/* 123 */       float f13 = f5 + arrayOfFloat[6];
/* 124 */       float f14 = f6 + arrayOfFloat[7];
/*     */ 
/* 126 */       localShaderGraphics.drawMappedTextureRaw(localTexture, f1, f2, f3, f4, f7, f8, f11, f12, f13, f14, f9, f10);
/*     */     }
/*     */ 
/* 131 */     localShaderGraphics.setExternalShader(null);
/*     */ 
/* 133 */     return new ImageData(getFilterContext(), localPPSDrawable, localRectangle2);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.prism.ps.PPSOneSamplerPeer
 * JD-Core Version:    0.6.2
 */