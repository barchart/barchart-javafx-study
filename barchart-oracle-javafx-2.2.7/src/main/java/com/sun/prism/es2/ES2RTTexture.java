/*     */ package com.sun.prism.es2;
/*     */ 
/*     */ import com.sun.glass.ui.Screen;
/*     */ import com.sun.prism.Graphics;
/*     */ import com.sun.prism.Image;
/*     */ import com.sun.prism.PixelFormat;
/*     */ import com.sun.prism.RTTexture;
/*     */ import com.sun.prism.ReadbackRenderTarget;
/*     */ import com.sun.prism.Texture;
/*     */ import com.sun.prism.es2.gl.GLContext;
/*     */ import com.sun.prism.es2.gl.GLFactory;
/*     */ import com.sun.prism.impl.PrismSettings;
/*     */ import com.sun.prism.impl.PrismTrace;
/*     */ import java.nio.Buffer;
/*     */ 
/*     */ class ES2RTTexture extends ES2Texture
/*     */   implements RTTexture, ReadbackRenderTarget
/*     */ {
/*     */   private int fboID;
/*     */   private int dbID;
/*     */   private boolean opaque;
/*     */ 
/*     */   private ES2RTTexture(ES2Context paramES2Context, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8)
/*     */   {
/*  36 */     super(paramES2Context, PixelFormat.BYTE_BGRA_PRE, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, new ES2RTTextureDisposerRecord(paramES2Context, paramInt2, paramInt1));
/*     */ 
/*  41 */     this.fboID = paramInt1;
/*  42 */     this.dbID = 0;
/*  43 */     this.opaque = false;
/*     */   }
/*     */ 
/*     */   void attachDepthBuffer(ES2Context paramES2Context)
/*     */   {
/*  52 */     if (this.dbID != 0) {
/*  53 */       return;
/*     */     }
/*  55 */     this.dbID = paramES2Context.getGLContext().createDepthBuffer(getPhysicalWidth(), getPhysicalHeight());
/*     */ 
/*  60 */     ((ES2RTTextureDisposerRecord)this.disposerRecord).setDepthBufferID(this.dbID);
/*     */   }
/*     */ 
/*     */   static ES2RTTexture create(ES2Context paramES2Context, int paramInt1, int paramInt2)
/*     */   {
/*  76 */     int i = !ES2Pipeline.glFactory.isGL2() ? 1 : 0;
/*     */     int j;
/*     */     int k;
/*     */     int m;
/*     */     int n;
/*  79 */     if (i != 0) {
/*  80 */       j = 2;
/*  81 */       k = 2;
/*  82 */       m = paramInt1 + 4;
/*  83 */       n = paramInt2 + 4;
/*     */     } else {
/*  85 */       j = 0;
/*  86 */       k = 0;
/*  87 */       m = paramInt1;
/*  88 */       n = paramInt2;
/*     */     }
/*     */ 
/*  91 */     GLContext localGLContext = paramES2Context.getGLContext();
/*  92 */     int i1 = localGLContext.getMaxTextureSize();
/*     */ 
/*  94 */     if (localGLContext.canCreateNonPowTwoTextures()) {
/*  95 */       i2 = m <= i1 ? m : 0;
/*  96 */       i3 = n <= i1 ? n : 0;
/*     */     } else {
/*  98 */       i2 = nextPowerOfTwo(m, i1);
/*  99 */       i3 = nextPowerOfTwo(n, i1);
/*     */     }
/*     */ 
/* 102 */     if ((i2 == 0) || (i3 == 0)) {
/* 103 */       throw new RuntimeException("Requested texture dimensions (" + paramInt1 + "x" + paramInt2 + ") " + "require dimensions (" + i2 + "x" + i3 + ") " + "that exceed maximum texture size (" + i1 + ")");
/*     */     }
/*     */ 
/* 110 */     int i4 = PrismSettings.minTextureSize;
/* 111 */     int i2 = Math.max(i2, i4);
/* 112 */     int i3 = Math.max(i3, i4);
/*     */     int i5;
/*     */     int i6;
/* 127 */     if (i != 0) {
/* 128 */       i5 = i2 - 4;
/* 129 */       i6 = i3 - 4;
/*     */     } else {
/* 131 */       i5 = i2;
/* 132 */       i6 = i3;
/*     */     }
/*     */ 
/* 136 */     localGLContext.setActiveTextureUnit(0);
/* 137 */     int i7 = localGLContext.getBoundTexture();
/*     */ 
/* 139 */     int i8 = localGLContext.createTexture(i2, i3);
/*     */ 
/* 142 */     int i9 = localGLContext.createFBO(i8, i2, i3);
/*     */ 
/* 145 */     localGLContext.setBoundTexture(i7);
/*     */ 
/* 147 */     return new ES2RTTexture(paramES2Context, i9, i8, i2, i3, j, k, i5, i6);
/*     */   }
/*     */ 
/*     */   public Texture getBackBuffer()
/*     */   {
/* 154 */     return this;
/*     */   }
/*     */ 
/*     */   public Graphics createGraphics() {
/* 158 */     return ES2Graphics.create(this.context, this);
/*     */   }
/*     */ 
/*     */   public int[] getPixels() {
/* 162 */     return null;
/*     */   }
/*     */ 
/*     */   public boolean readPixels(Buffer paramBuffer) {
/* 166 */     this.context.flushVertexBuffer();
/* 167 */     GLContext localGLContext = this.context.getGLContext();
/* 168 */     return localGLContext.readPixels(paramBuffer, getContentX(), getContentY(), getContentWidth(), getContentHeight());
/*     */   }
/*     */ 
/*     */   public long getNativeDestHandle()
/*     */   {
/* 173 */     return this.fboID;
/*     */   }
/*     */ 
/*     */   public Screen getAssociatedScreen() {
/* 177 */     return this.context.getAssociatedScreen();
/*     */   }
/*     */ 
/*     */   public void update(Image paramImage)
/*     */   {
/* 182 */     throw new UnsupportedOperationException("update() not supported for RTTextures");
/*     */   }
/*     */ 
/*     */   public void update(Image paramImage, int paramInt1, int paramInt2)
/*     */   {
/* 187 */     throw new UnsupportedOperationException("update() not supported for RTTextures");
/*     */   }
/*     */ 
/*     */   public void update(Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */   {
/* 192 */     throw new UnsupportedOperationException("update() not supported for RTTextures");
/*     */   }
/*     */ 
/*     */   public void update(Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean)
/*     */   {
/* 197 */     throw new UnsupportedOperationException("update() not supported for RTTextures");
/*     */   }
/*     */ 
/*     */   public void update(Buffer paramBuffer, PixelFormat paramPixelFormat, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, boolean paramBoolean)
/*     */   {
/* 205 */     throw new UnsupportedOperationException("update() not supported for RTTextures");
/*     */   }
/*     */ 
/*     */   public boolean isOpaque() {
/* 209 */     return this.opaque;
/*     */   }
/*     */ 
/*     */   public void setOpaque(boolean paramBoolean) {
/* 213 */     this.opaque = paramBoolean;
/*     */   }
/*     */ 
/*     */   public boolean isVolatile() {
/* 217 */     return false;
/*     */   }
/*     */ 
/*     */   static class ES2RTTextureDisposerRecord extends ES2Texture.ES2TextureDisposerRecord {
/*     */     private int fboID;
/*     */     private int dbID;
/*     */ 
/*     */     ES2RTTextureDisposerRecord(ES2Context paramES2Context, int paramInt1, int paramInt2) {
/* 226 */       super(paramInt1);
/* 227 */       this.fboID = paramInt2;
/*     */     }
/*     */ 
/*     */     void traceDispose()
/*     */     {
/* 232 */       PrismTrace.rttDisposed(this.fboID);
/*     */     }
/*     */ 
/*     */     public void dispose()
/*     */     {
/* 237 */       super.dispose();
/* 238 */       if (this.fboID != 0) {
/* 239 */         this.context.getGLContext().deleteFBO(this.fboID);
/* 240 */         if (this.dbID != 0) {
/* 241 */           this.context.getGLContext().deleteRenderBuffer(this.dbID);
/* 242 */           this.dbID = 0;
/*     */         }
/* 244 */         this.fboID = 0;
/*     */       }
/*     */     }
/*     */ 
/*     */     void setDepthBufferID(int paramInt) {
/* 249 */       this.dbID = paramInt;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.es2.ES2RTTexture
 * JD-Core Version:    0.6.2
 */