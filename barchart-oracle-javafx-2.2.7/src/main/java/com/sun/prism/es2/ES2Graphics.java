/*     */ package com.sun.prism.es2;
/*     */ 
/*     */ import com.sun.prism.RenderTarget;
/*     */ import com.sun.prism.Texture;
/*     */ import com.sun.prism.es2.gl.GLContext;
/*     */ import com.sun.prism.impl.ps.BaseShaderGraphics;
/*     */ import com.sun.prism.paint.Color;
/*     */ import com.sun.prism.paint.Paint;
/*     */ 
/*     */ public class ES2Graphics extends BaseShaderGraphics
/*     */ {
/*     */   private final ES2Context context;
/*     */ 
/*     */   private ES2Graphics(ES2Context paramES2Context, RenderTarget paramRenderTarget)
/*     */   {
/*  21 */     super(paramES2Context, paramRenderTarget);
/*  22 */     this.context = paramES2Context;
/*     */   }
/*     */ 
/*     */   static ES2Graphics create(ES2Context paramES2Context, RenderTarget paramRenderTarget) {
/*  26 */     if (paramRenderTarget == null)
/*     */     {
/*  28 */       return null;
/*     */     }
/*  30 */     return new ES2Graphics(paramES2Context, paramRenderTarget);
/*     */   }
/*     */ 
/*     */   static void clearBuffers(ES2Context paramES2Context, Color paramColor, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
/*     */   {
/*  35 */     paramES2Context.getGLContext().clearBuffers(paramColor, paramBoolean1, paramBoolean2, paramBoolean3);
/*     */   }
/*     */ 
/*     */   public void copyTexture(Texture paramTexture, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8)
/*     */   {
/*  46 */     GLContext localGLContext = this.context.getGLContext();
/*  47 */     localGLContext.blendFunc(1, 0);
/*  48 */     drawTexture(paramTexture, paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8);
/*     */ 
/*  51 */     this.context.flushVertexBuffer();
/*     */ 
/*  53 */     localGLContext.blendFunc(1, 7);
/*     */   }
/*     */ 
/*     */   public void clearQuad(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/*  59 */     this.context.setRenderTarget(this);
/*  60 */     this.context.flushVertexBuffer();
/*  61 */     GLContext localGLContext = this.context.getGLContext();
/*     */ 
/*  63 */     localGLContext.blendFunc(0, 0);
/*  64 */     Paint localPaint = getPaint();
/*  65 */     setPaint(Color.BLACK);
/*  66 */     fillQuad(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/*  67 */     this.context.flushVertexBuffer();
/*  68 */     setPaint(localPaint);
/*     */ 
/*  70 */     localGLContext.blendFunc(1, 7);
/*     */   }
/*     */ 
/*     */   public void clear(Color paramColor) {
/*  74 */     this.context.validateClearOp(this);
/*  75 */     getRenderTarget().setOpaque(paramColor.isOpaque());
/*  76 */     clearBuffers(this.context, paramColor, true, isDepthBuffer(), false);
/*     */   }
/*     */ 
/*     */   public void sync()
/*     */   {
/*  81 */     this.context.flushVertexBuffer();
/*  82 */     this.context.getGLContext().finish();
/*     */   }
/*     */ 
/*     */   public void reset()
/*     */   {
/*  91 */     forceRenderTarget();
/*     */   }
/*     */ 
/*     */   void forceRenderTarget()
/*     */   {
/* 100 */     this.context.forceRenderTarget(this);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.es2.ES2Graphics
 * JD-Core Version:    0.6.2
 */