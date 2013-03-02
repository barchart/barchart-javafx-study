/*     */ package com.sun.scenario.effect.impl.prism.ps;
/*     */ 
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.prism.Texture;
/*     */ import com.sun.prism.ps.Shader;
/*     */ import com.sun.prism.ps.ShaderGraphics;
/*     */ import com.sun.scenario.effect.FilterContext;
/*     */ import com.sun.scenario.effect.Filterable;
/*     */ import com.sun.scenario.effect.FloatMap;
/*     */ import com.sun.scenario.effect.ImageData;
/*     */ import com.sun.scenario.effect.impl.Renderer;
/*     */ import com.sun.scenario.effect.impl.prism.PrTexture;
/*     */ 
/*     */ public abstract class PPSTwoSamplerPeer extends PPSEffectPeer
/*     */ {
/*     */   private Shader shader;
/*     */ 
/*     */   protected PPSTwoSamplerPeer(FilterContext paramFilterContext, Renderer paramRenderer, String paramString)
/*     */   {
/*  46 */     super(paramFilterContext, paramRenderer, paramString);
/*     */   }
/*     */ 
/*     */   public void dispose()
/*     */   {
/*  51 */     if (this.shader != null)
/*  52 */       this.shader.dispose();
/*     */   }
/*     */ 
/*     */   ImageData filterImpl(ImageData[] paramArrayOfImageData)
/*     */   {
/*  58 */     Rectangle localRectangle1 = getDestBounds();
/*  59 */     int i = localRectangle1.width;
/*  60 */     int j = localRectangle1.height;
/*     */ 
/*  62 */     PPSRenderer localPPSRenderer = getRenderer();
/*  63 */     PPSDrawable localPPSDrawable = localPPSRenderer.getCompatibleImage(i, j);
/*  64 */     if (localPPSDrawable == null) {
/*  65 */       localPPSRenderer.markLost();
/*  66 */       return new ImageData(getFilterContext(), localPPSDrawable, localRectangle1);
/*     */     }
/*  68 */     setDestNativeBounds(localPPSDrawable.getPhysicalWidth(), localPPSDrawable.getPhysicalHeight());
/*     */ 
/*  70 */     Filterable localFilterable = paramArrayOfImageData[0].getUntransformedImage();
/*  71 */     PrTexture localPrTexture1 = (PrTexture)localFilterable;
/*  72 */     Rectangle localRectangle2 = paramArrayOfImageData[0].getUntransformedBounds();
/*  73 */     BaseTransform localBaseTransform = paramArrayOfImageData[0].getTransform();
/*  74 */     setInputBounds(0, localRectangle2);
/*  75 */     setInputTransform(0, localBaseTransform);
/*  76 */     setInputNativeBounds(0, localPrTexture1.getNativeBounds());
/*     */ 
/*  82 */     float[] arrayOfFloat = new float[8];
/*     */     PrTexture localPrTexture2;
/*     */     Rectangle localRectangle3;
/*     */     int k;
/*  84 */     if (paramArrayOfImageData.length > 1)
/*     */     {
/*  86 */       localObject1 = paramArrayOfImageData[1].getUntransformedImage();
/*  87 */       localPrTexture2 = (PrTexture)localObject1;
/*  88 */       if (localPrTexture2 == null) {
/*  89 */         localPPSRenderer.markLost();
/*  90 */         return new ImageData(getFilterContext(), localPPSDrawable, localRectangle1);
/*     */       }
/*  92 */       localRectangle3 = paramArrayOfImageData[1].getUntransformedBounds();
/*  93 */       localObject2 = paramArrayOfImageData[1].getTransform();
/*  94 */       setInputBounds(1, localRectangle3);
/*  95 */       setInputTransform(1, (BaseTransform)localObject2);
/*  96 */       setInputNativeBounds(1, localPrTexture2.getNativeBounds());
/*  97 */       k = getTextureCoordinates(1, arrayOfFloat, localRectangle3.x, localRectangle3.y, ((Filterable)localObject1).getPhysicalWidth(), ((Filterable)localObject1).getPhysicalHeight(), localRectangle1, (BaseTransform)localObject2);
/*     */     }
/*     */     else
/*     */     {
/* 105 */       localObject1 = (FloatMap)getSamplerData(1);
/* 106 */       localPrTexture2 = (PrTexture)((FloatMap)localObject1).getAccelData(getFilterContext());
/* 107 */       if (localPrTexture2 == null) {
/* 108 */         localPPSRenderer.markLost();
/* 109 */         return new ImageData(getFilterContext(), localPPSDrawable, localRectangle1);
/*     */       }
/* 111 */       localRectangle3 = new Rectangle(((FloatMap)localObject1).getWidth(), ((FloatMap)localObject1).getHeight());
/* 112 */       localObject2 = localPrTexture2.getNativeBounds();
/* 113 */       setInputBounds(1, localRectangle3);
/* 114 */       setInputNativeBounds(1, (Rectangle)localObject2);
/*     */       float tmp357_356 = 0.0F; arrayOfFloat[1] = tmp357_356; arrayOfFloat[0] = tmp357_356;
/* 118 */       arrayOfFloat[2] = (localRectangle3.width / ((Rectangle)localObject2).width);
/* 119 */       arrayOfFloat[3] = (localRectangle3.height / ((Rectangle)localObject2).height);
/* 120 */       k = 4;
/*     */     }
/*     */ 
/* 125 */     Object localObject1 = new float[8];
/* 126 */     int m = getTextureCoordinates(0, (float[])localObject1, localRectangle2.x, localRectangle2.y, localFilterable.getPhysicalWidth(), localFilterable.getPhysicalHeight(), localRectangle1, localBaseTransform);
/*     */ 
/* 133 */     Object localObject2 = localPPSDrawable.createGraphics();
/* 134 */     if (localObject2 == null) {
/* 135 */       localPPSRenderer.markLost();
/* 136 */       return new ImageData(getFilterContext(), localPPSDrawable, localRectangle1);
/*     */     }
/* 138 */     if (this.shader == null) {
/* 139 */       this.shader = createShader();
/*     */     }
/* 141 */     if ((this.shader == null) || (!this.shader.isValid())) {
/* 142 */       localPPSRenderer.markLost();
/* 143 */       return new ImageData(getFilterContext(), localPPSDrawable, localRectangle1);
/*     */     }
/* 145 */     ((ShaderGraphics)localObject2).setExternalShader(this.shader);
/* 146 */     updateShader(this.shader);
/*     */ 
/* 148 */     float f1 = 0.0F;
/* 149 */     float f2 = 0.0F;
/* 150 */     float f3 = i;
/* 151 */     float f4 = j;
/*     */ 
/* 153 */     Texture localTexture1 = localPrTexture1.getTextureObject();
/* 154 */     if (localTexture1 == null) {
/* 155 */       localPPSRenderer.markLost();
/* 156 */       return new ImageData(getFilterContext(), localPPSDrawable, localRectangle1);
/*     */     }
/* 158 */     Texture localTexture2 = localPrTexture2.getTextureObject();
/* 159 */     if (localTexture2 == null) {
/* 160 */       localPPSRenderer.markLost();
/* 161 */       return new ImageData(getFilterContext(), localPPSDrawable, localRectangle1);
/*     */     }
/*     */ 
/* 165 */     float f5 = localTexture1.getContentX() / localTexture1.getPhysicalWidth();
/* 166 */     float f6 = localTexture1.getContentY() / localTexture1.getPhysicalHeight();
/* 167 */     float f7 = f5 + localObject1[0];
/* 168 */     float f8 = f6 + localObject1[1];
/* 169 */     float f9 = f5 + localObject1[2];
/* 170 */     float f10 = f6 + localObject1[3];
/*     */ 
/* 173 */     float f11 = localTexture2.getContentX() / localTexture2.getPhysicalWidth();
/* 174 */     float f12 = localTexture2.getContentY() / localTexture2.getPhysicalHeight();
/* 175 */     float f13 = f11 + arrayOfFloat[0];
/* 176 */     float f14 = f12 + arrayOfFloat[1];
/* 177 */     float f15 = f11 + arrayOfFloat[2];
/* 178 */     float f16 = f12 + arrayOfFloat[3];
/*     */ 
/* 180 */     if ((m < 8) && (k < 8)) {
/* 181 */       ((ShaderGraphics)localObject2).drawTextureRaw2(localTexture1, localTexture2, f1, f2, f3, f4, f7, f8, f9, f10, f13, f14, f15, f16);
/*     */     }
/*     */     else
/*     */     {
/*     */       float f17;
/*     */       float f18;
/*     */       float f19;
/*     */       float f20;
/* 189 */       if (m < 8) {
/* 190 */         f17 = f9;
/* 191 */         f18 = f8;
/* 192 */         f19 = f7;
/* 193 */         f20 = f10;
/*     */       } else {
/* 195 */         f17 = f5 + localObject1[4];
/* 196 */         f18 = f6 + localObject1[5];
/* 197 */         f19 = f5 + localObject1[6];
/* 198 */         f20 = f6 + localObject1[7];
/*     */       }
/*     */       float f21;
/*     */       float f22;
/*     */       float f23;
/*     */       float f24;
/* 201 */       if (k < 8) {
/* 202 */         f21 = f15;
/* 203 */         f22 = f14;
/* 204 */         f23 = f13;
/* 205 */         f24 = f16;
/*     */       } else {
/* 207 */         f21 = f11 + arrayOfFloat[4];
/* 208 */         f22 = f12 + arrayOfFloat[5];
/* 209 */         f23 = f11 + arrayOfFloat[6];
/* 210 */         f24 = f12 + arrayOfFloat[7];
/*     */       }
/*     */ 
/* 213 */       ((ShaderGraphics)localObject2).drawMappedTextureRaw2(localTexture1, localTexture2, f1, f2, f3, f4, f7, f8, f17, f18, f19, f20, f9, f10, f13, f14, f21, f22, f23, f24, f15, f16);
/*     */     }
/*     */ 
/* 221 */     ((ShaderGraphics)localObject2).setExternalShader(null);
/*     */ 
/* 223 */     return new ImageData(getFilterContext(), localPPSDrawable, localRectangle1);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.prism.ps.PPSTwoSamplerPeer
 * JD-Core Version:    0.6.2
 */