/*    */ package com.sun.scenario.effect.impl.prism.ps;
/*    */ 
/*    */ import com.sun.javafx.geom.Rectangle;
/*    */ import com.sun.prism.paint.Color;
/*    */ import com.sun.prism.ps.Shader;
/*    */ import com.sun.prism.ps.ShaderGraphics;
/*    */ import com.sun.scenario.effect.FilterContext;
/*    */ import com.sun.scenario.effect.ImageData;
/*    */ import com.sun.scenario.effect.impl.Renderer;
/*    */ 
/*    */ public abstract class PPSZeroSamplerPeer extends PPSEffectPeer
/*    */ {
/*    */   private Shader shader;
/*    */ 
/*    */   protected PPSZeroSamplerPeer(FilterContext paramFilterContext, Renderer paramRenderer, String paramString)
/*    */   {
/* 42 */     super(paramFilterContext, paramRenderer, paramString);
/*    */   }
/*    */ 
/*    */   public void dispose()
/*    */   {
/* 47 */     if (this.shader != null)
/* 48 */       this.shader.dispose();
/*    */   }
/*    */ 
/*    */   ImageData filterImpl(ImageData[] paramArrayOfImageData)
/*    */   {
/* 54 */     Rectangle localRectangle = getDestBounds();
/* 55 */     int i = localRectangle.width;
/* 56 */     int j = localRectangle.height;
/*    */ 
/* 58 */     PPSRenderer localPPSRenderer = getRenderer();
/* 59 */     PPSDrawable localPPSDrawable = localPPSRenderer.getCompatibleImage(i, j);
/* 60 */     if (localPPSDrawable == null) {
/* 61 */       localPPSRenderer.markLost();
/* 62 */       return new ImageData(getFilterContext(), localPPSDrawable, localRectangle);
/*    */     }
/* 64 */     setDestNativeBounds(localPPSDrawable.getPhysicalWidth(), localPPSDrawable.getPhysicalHeight());
/*    */ 
/* 66 */     ShaderGraphics localShaderGraphics = localPPSDrawable.createGraphics();
/* 67 */     if (localShaderGraphics == null) {
/* 68 */       localPPSRenderer.markLost();
/* 69 */       return new ImageData(getFilterContext(), localPPSDrawable, localRectangle);
/*    */     }
/* 71 */     if (this.shader == null) {
/* 72 */       this.shader = createShader();
/*    */     }
/* 74 */     if ((this.shader == null) || (!this.shader.isValid())) {
/* 75 */       localPPSRenderer.markLost();
/* 76 */       return new ImageData(getFilterContext(), localPPSDrawable, localRectangle);
/*    */     }
/* 78 */     localShaderGraphics.setExternalShader(this.shader);
/* 79 */     updateShader(this.shader);
/*    */ 
/* 81 */     float f1 = 0.0F;
/* 82 */     float f2 = 0.0F;
/* 83 */     float f3 = i;
/* 84 */     float f4 = j;
/*    */ 
/* 86 */     localShaderGraphics.setPaint(Color.WHITE);
/* 87 */     localShaderGraphics.fillQuad(f1, f2, f3, f4);
/* 88 */     localShaderGraphics.setExternalShader(null);
/*    */ 
/* 90 */     return new ImageData(getFilterContext(), localPPSDrawable, localRectangle);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.prism.ps.PPSZeroSamplerPeer
 * JD-Core Version:    0.6.2
 */