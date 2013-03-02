/*     */ package com.sun.prism.impl;
/*     */ 
/*     */ import com.sun.glass.ui.Screen;
/*     */ import com.sun.javafx.font.FontStrike;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.prism.PixelFormat;
/*     */ import com.sun.prism.RTTexture;
/*     */ import com.sun.prism.RenderTarget;
/*     */ import com.sun.prism.ResourceFactory;
/*     */ import com.sun.prism.Texture;
/*     */ import com.sun.prism.Texture.Usage;
/*     */ import com.sun.prism.camera.PrismCameraImpl;
/*     */ import com.sun.prism.impl.paint.PaintUtil;
/*     */ import com.sun.prism.impl.shape.MaskData;
/*     */ import com.sun.prism.paint.Gradient;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ public abstract class BaseContext
/*     */ {
/*     */   private final Screen screen;
/*     */   private final ResourceFactory factory;
/*     */   private final VertexBuffer vertexBuffer;
/*     */   private Texture maskTex;
/*     */   private Texture paintTex;
/*     */   private int[] paintPixels;
/*     */   private ByteBuffer paintBuffer;
/*  40 */   private final Map<FontStrike, GlyphCache> greyGlyphCaches = new HashMap();
/*     */ 
/*  42 */   private final Map<FontStrike, GlyphCache> lcdGlyphCaches = new HashMap();
/*     */ 
/*  44 */   GlyphCache[] recentCaches = new GlyphCache[8];
/*  45 */   int recentCacheIndex = 0;
/*     */ 
/*     */   protected BaseContext(Screen paramScreen, ResourceFactory paramResourceFactory, VertexBuffer paramVertexBuffer) {
/*  48 */     this.screen = paramScreen;
/*  49 */     this.factory = paramResourceFactory;
/*  50 */     this.vertexBuffer = paramVertexBuffer;
/*     */   }
/*     */ 
/*     */   public Screen getAssociatedScreen() {
/*  54 */     return this.screen;
/*     */   }
/*     */ 
/*     */   public ResourceFactory getResourceFactory() {
/*  58 */     return this.factory;
/*     */   }
/*     */ 
/*     */   public VertexBuffer getVertexBuffer() {
/*  62 */     return this.vertexBuffer;
/*     */   }
/*     */ 
/*     */   public void flushVertexBuffer() {
/*  66 */     this.vertexBuffer.flush();
/*     */   }
/*     */ 
/*     */   public void setRenderTarget(BaseGraphics paramBaseGraphics)
/*     */   {
/*  75 */     if (paramBaseGraphics != null) {
/*  76 */       setRenderTarget(paramBaseGraphics.getRenderTarget(), paramBaseGraphics.getCameraNoClone(), (paramBaseGraphics.isDepthTest()) && (paramBaseGraphics.isDepthBuffer()));
/*     */     }
/*     */     else
/*  79 */       releaseRenderTarget();
/*     */   }
/*     */ 
/*     */   protected void releaseRenderTarget()
/*     */   {
/*     */   }
/*     */ 
/*     */   protected abstract void setRenderTarget(RenderTarget paramRenderTarget, PrismCameraImpl paramPrismCameraImpl, boolean paramBoolean);
/*     */ 
/*     */   public void validateClearOp(BaseGraphics paramBaseGraphics)
/*     */   {
/*  91 */     validatePaintOp(paramBaseGraphics, BaseTransform.IDENTITY_TRANSFORM, null, 0.0F, 0.0F, 0.0F, 0.0F);
/*     */   }
/*     */ 
/*     */   public abstract void validatePaintOp(BaseGraphics paramBaseGraphics, BaseTransform paramBaseTransform, Texture paramTexture, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);
/*     */ 
/*     */   public abstract void validateTextureOp(BaseGraphics paramBaseGraphics, BaseTransform paramBaseTransform, Texture paramTexture, PixelFormat paramPixelFormat);
/*     */ 
/*     */   public void clearGlyphCaches()
/*     */   {
/* 102 */     clearCaches(this.greyGlyphCaches);
/* 103 */     clearCaches(this.lcdGlyphCaches);
/*     */   }
/*     */ 
/*     */   private void clearCaches(Map<FontStrike, GlyphCache> paramMap) {
/* 107 */     for (Iterator localIterator = paramMap.keySet().iterator(); localIterator.hasNext(); ) {
/* 108 */       ((FontStrike)localIterator.next()).clearDesc();
/*     */     }
/*     */ 
/* 111 */     for (GlyphCache localGlyphCache : paramMap.values()) {
/* 112 */       if (localGlyphCache != null) {
/* 113 */         localGlyphCache.clear();
/*     */       }
/*     */     }
/* 116 */     for (int i = 0; i < this.recentCaches.length; i++) {
/* 117 */       this.recentCaches[i] = null;
/*     */     }
/* 119 */     this.recentCacheIndex = 0;
/* 120 */     paramMap.clear();
/*     */   }
/*     */ 
/*     */   public abstract RTTexture getLCDBuffer();
/*     */ 
/*     */   public GlyphCache getGlyphCache(FontStrike paramFontStrike) {
/* 126 */     Map localMap = paramFontStrike.getAAMode() == 1 ? this.lcdGlyphCaches : this.greyGlyphCaches;
/*     */ 
/* 129 */     return getGlyphCache(paramFontStrike, localMap);
/*     */   }
/*     */ 
/*     */   private GlyphCache getGlyphCache(FontStrike paramFontStrike, Map<FontStrike, GlyphCache> paramMap)
/*     */   {
/* 134 */     GlyphCache localGlyphCache = (GlyphCache)paramMap.get(paramFontStrike);
/* 135 */     int i = 0;
/* 136 */     if (localGlyphCache == null) {
/* 137 */       localGlyphCache = new GlyphCache(this, paramFontStrike);
/* 138 */       paramMap.put(paramFontStrike, localGlyphCache);
/*     */     } else {
/* 140 */       for (int j = 0; j < this.recentCaches.length; j++) {
/* 141 */         if (this.recentCaches[j] == localGlyphCache) {
/* 142 */           i = 1;
/* 143 */           break;
/*     */         }
/*     */       }
/* 146 */       if (i == 0) {
/* 147 */         this.recentCaches[this.recentCacheIndex] = localGlyphCache;
/* 148 */         this.recentCacheIndex += 1;
/* 149 */         if (this.recentCacheIndex == this.recentCaches.length) {
/* 150 */           this.recentCacheIndex = 0;
/*     */         }
/*     */       }
/*     */     }
/* 154 */     return localGlyphCache;
/*     */   }
/*     */ 
/*     */   private boolean notRecentlyUsed(GlyphCache paramGlyphCache) {
/* 158 */     for (int i = 0; i < this.recentCaches.length; i++) {
/* 159 */       if (paramGlyphCache == this.recentCaches[i]) {
/* 160 */         return false;
/*     */       }
/*     */     }
/* 163 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean isEdgeSmoothingSupported(PixelFormat paramPixelFormat)
/*     */   {
/* 173 */     return true;
/*     */   }
/*     */ 
/*     */   public Texture getMaskTexture(MaskData paramMaskData) {
/* 177 */     int i = paramMaskData.getWidth();
/* 178 */     int j = paramMaskData.getHeight();
/* 179 */     if ((this.maskTex == null) || (this.maskTex.getContentWidth() < i) || (this.maskTex.getContentHeight() < j))
/*     */     {
/* 183 */       int k = i;
/* 184 */       int m = j;
/* 185 */       if (this.maskTex != null)
/*     */       {
/* 190 */         k = Math.max(i, this.maskTex.getContentWidth());
/* 191 */         m = Math.max(j, this.maskTex.getContentHeight());
/* 192 */         this.maskTex.dispose();
/*     */       }
/* 194 */       this.maskTex = getResourceFactory().createMaskTexture(k, m);
/*     */     }
/*     */ 
/* 198 */     paramMaskData.uploadToTexture(this.maskTex, 0, 0, false);
/*     */ 
/* 200 */     return this.maskTex;
/*     */   }
/*     */ 
/*     */   public Texture getGradientTexture(Gradient paramGradient, BaseTransform paramBaseTransform, int paramInt1, int paramInt2, MaskData paramMaskData, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/* 208 */     int i = paramInt1 * paramInt2;
/* 209 */     int j = i * 4;
/* 210 */     if ((this.paintBuffer == null) || (this.paintBuffer.capacity() < j)) {
/* 211 */       this.paintPixels = new int[i];
/* 212 */       this.paintBuffer = ByteBuffer.wrap(new byte[j]);
/*     */     }
/*     */ 
/* 215 */     if ((this.paintTex == null) || (this.paintTex.getContentWidth() < paramInt1) || (this.paintTex.getContentHeight() < paramInt2))
/*     */     {
/* 219 */       int k = paramInt1;
/* 220 */       int m = paramInt2;
/* 221 */       if (this.paintTex != null)
/*     */       {
/* 226 */         k = Math.max(paramInt1, this.paintTex.getContentWidth());
/* 227 */         m = Math.max(paramInt2, this.paintTex.getContentHeight());
/* 228 */         this.paintTex.dispose();
/*     */       }
/* 230 */       this.paintTex = getResourceFactory().createTexture(PixelFormat.BYTE_BGRA_PRE, Texture.Usage.DEFAULT, k, m, false);
/*     */     }
/*     */ 
/* 238 */     PaintUtil.fillImageWithGradient(this.paintPixels, paramGradient, paramBaseTransform, 0, 0, paramInt1, paramInt2, paramFloat1, paramFloat2, paramFloat3, paramFloat4);
/*     */ 
/* 244 */     byte[] arrayOfByte1 = this.paintBuffer.array();
/*     */     int i1;
/*     */     int i2;
/* 245 */     if (paramMaskData != null)
/*     */     {
/* 248 */       byte[] arrayOfByte2 = paramMaskData.getMaskBuffer().array();
/* 249 */       i1 = 0;
/* 250 */       for (i2 = 0; i2 < i; i2++) {
/* 251 */         int i3 = this.paintPixels[i2];
/* 252 */         int i4 = arrayOfByte2[i2] & 0xFF;
/* 253 */         arrayOfByte1[(i1++)] = ((byte)((i3 & 0xFF) * i4 / 255));
/* 254 */         arrayOfByte1[(i1++)] = ((byte)((i3 >> 8 & 0xFF) * i4 / 255));
/* 255 */         arrayOfByte1[(i1++)] = ((byte)((i3 >> 16 & 0xFF) * i4 / 255));
/* 256 */         arrayOfByte1[(i1++)] = ((byte)((i3 >>> 24) * i4 / 255));
/*     */       }
/*     */     }
/*     */     else {
/* 260 */       int n = 0;
/* 261 */       for (i1 = 0; i1 < i; i1++) {
/* 262 */         i2 = this.paintPixels[i1];
/* 263 */         arrayOfByte1[(n++)] = ((byte)(i2 & 0xFF));
/* 264 */         arrayOfByte1[(n++)] = ((byte)(i2 >> 8 & 0xFF));
/* 265 */         arrayOfByte1[(n++)] = ((byte)(i2 >> 16 & 0xFF));
/* 266 */         arrayOfByte1[(n++)] = ((byte)(i2 >>> 24));
/*     */       }
/*     */     }
/*     */ 
/* 270 */     this.paintTex.update(this.paintBuffer, PixelFormat.BYTE_BGRA_PRE, 0, 0, 0, 0, paramInt1, paramInt2, paramInt1 * 4, false);
/*     */ 
/* 273 */     return this.paintTex;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.impl.BaseContext
 * JD-Core Version:    0.6.2
 */