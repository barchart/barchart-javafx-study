/*     */ package com.sun.prism.j2d;
/*     */ 
/*     */ import com.sun.glass.ui.Screen;
/*     */ import com.sun.glass.ui.View;
/*     */ import com.sun.prism.MediaFrame;
/*     */ import com.sun.prism.PixelFormat;
/*     */ import com.sun.prism.Presentable;
/*     */ import com.sun.prism.RTTexture;
/*     */ import com.sun.prism.RenderingContext;
/*     */ import com.sun.prism.ResourceFactory;
/*     */ import com.sun.prism.Texture;
/*     */ import com.sun.prism.Texture.Usage;
/*     */ import com.sun.prism.impl.BaseRenderingContext;
/*     */ import com.sun.prism.impl.BaseResourceFactory;
/*     */ import com.sun.prism.impl.VertexBuffer;
/*     */ import com.sun.prism.impl.shape.BasicShapeRep;
/*     */ import com.sun.prism.shape.ShapeRep;
/*     */ 
/*     */ public class J2DResourceFactory extends BaseResourceFactory
/*     */   implements ResourceFactory
/*     */ {
/*     */   private Screen screen;
/*  41 */   private static ShapeRep theRep = new BasicShapeRep();
/*     */ 
/*     */   public J2DResourceFactory(Screen paramScreen)
/*     */   {
/*  34 */     this.screen = paramScreen;
/*     */   }
/*     */ 
/*     */   public Screen getScreen() {
/*  38 */     return this.screen;
/*     */   }
/*     */ 
/*     */   public ShapeRep createArcRep(boolean paramBoolean)
/*     */   {
/*  44 */     return theRep;
/*     */   }
/*     */ 
/*     */   public ShapeRep createEllipseRep(boolean paramBoolean) {
/*  48 */     return theRep;
/*     */   }
/*     */ 
/*     */   public ShapeRep createRoundRectRep(boolean paramBoolean) {
/*  52 */     return theRep;
/*     */   }
/*     */ 
/*     */   public ShapeRep createPathRep(boolean paramBoolean) {
/*  56 */     return theRep;
/*     */   }
/*     */ 
/*     */   public Presentable createPresentable(View paramView) {
/*  60 */     return J2DPresentable.create(paramView, this);
/*     */   }
/*     */ 
/*     */   public RTTexture createRTTexture(int paramInt1, int paramInt2) {
/*  64 */     return new J2DRTTexture(paramInt1, paramInt2, this);
/*     */   }
/*     */ 
/*     */   public Texture createTexture(PixelFormat paramPixelFormat, Texture.Usage paramUsage, int paramInt1, int paramInt2, boolean paramBoolean)
/*     */   {
/*  72 */     return new J2DTexture(paramPixelFormat, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   public Texture createTexture(MediaFrame paramMediaFrame)
/*     */   {
/*  78 */     paramMediaFrame.holdFrame();
/*     */ 
/*  80 */     if (paramMediaFrame.getPixelFormat() != PixelFormat.INT_ARGB_PRE) {
/*  81 */       MediaFrame localMediaFrame = paramMediaFrame.convertToFormat(PixelFormat.INT_ARGB_PRE);
/*  82 */       paramMediaFrame.releaseFrame();
/*  83 */       paramMediaFrame = localMediaFrame;
/*  84 */       if (null == paramMediaFrame)
/*     */       {
/*  86 */         return null;
/*     */       }
/*     */     }
/*     */ 
/*  90 */     J2DTexture localJ2DTexture = new J2DTexture(paramMediaFrame.getPixelFormat(), paramMediaFrame.getWidth(), paramMediaFrame.getHeight());
/*  91 */     paramMediaFrame.releaseFrame();
/*  92 */     return localJ2DTexture;
/*     */   }
/*     */ 
/*     */   public int getMaximumTextureSize() {
/*  96 */     return 2147483647;
/*     */   }
/*     */ 
/*     */   public boolean isFormatSupported(PixelFormat paramPixelFormat) {
/* 100 */     switch (1.$SwitchMap$com$sun$prism$PixelFormat[paramPixelFormat.ordinal()]) {
/*     */     case 1:
/*     */     case 2:
/*     */     case 3:
/*     */     case 4:
/* 105 */       return true;
/*     */     case 5:
/*     */     case 6:
/*     */     case 7:
/*     */     case 8:
/*     */     }
/* 111 */     return false;
/*     */   }
/*     */ 
/*     */   public VertexBuffer createVertexBuffer(int paramInt)
/*     */   {
/* 118 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public RenderingContext createRenderingContext(View paramView)
/*     */   {
/* 129 */     return new BaseRenderingContext();
/*     */   }
/*     */ 
/*     */   public void dispose()
/*     */   {
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.j2d.J2DResourceFactory
 * JD-Core Version:    0.6.2
 */