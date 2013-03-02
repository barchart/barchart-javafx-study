/*     */ package com.sun.prism.j2d;
/*     */ 
/*     */ import com.sun.glass.ui.Screen;
/*     */ import com.sun.prism.Graphics;
/*     */ import com.sun.prism.Image;
/*     */ import com.sun.prism.PixelFormat;
/*     */ import com.sun.prism.RTTexture;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.DataBuffer;
/*     */ import java.awt.image.DataBufferInt;
/*     */ import java.awt.image.WritableRaster;
/*     */ import java.nio.Buffer;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ 
/*     */ public class J2DRTTexture extends J2DTexture
/*     */   implements RTTexture
/*     */ {
/*     */   protected J2DResourceFactory factory;
/*     */   private boolean opaque;
/*     */ 
/*     */   public J2DRTTexture(int paramInt1, int paramInt2, J2DResourceFactory paramJ2DResourceFactory)
/*     */   {
/*  25 */     super(PixelFormat.INT_ARGB_PRE, paramInt1, paramInt2);
/*  26 */     this.factory = paramJ2DResourceFactory;
/*  27 */     this.opaque = false;
/*     */   }
/*     */ 
/*     */   public int[] getPixels() {
/*  31 */     DataBuffer localDataBuffer = this.bimg.getRaster().getDataBuffer();
/*  32 */     if ((localDataBuffer instanceof DataBufferInt)) {
/*  33 */       return ((DataBufferInt)localDataBuffer).getData();
/*     */     }
/*  35 */     return null;
/*     */   }
/*     */ 
/*     */   public boolean readPixels(Buffer paramBuffer)
/*     */   {
/*  41 */     int i = getContentWidth();
/*  42 */     int j = getContentHeight();
/*  43 */     int[] arrayOfInt = getPixels();
/*     */ 
/*  45 */     paramBuffer.clear();
/*     */ 
/*  48 */     for (int k = 0; k < i * j; k++) {
/*  49 */       int m = arrayOfInt[k];
/*  50 */       if ((paramBuffer instanceof IntBuffer)) {
/*  51 */         ((IntBuffer)paramBuffer).put(m);
/*  52 */       } else if ((paramBuffer instanceof ByteBuffer)) {
/*  53 */         byte b1 = (byte)(m >> 24);
/*  54 */         byte b2 = (byte)(m >> 16);
/*  55 */         byte b3 = (byte)(m >> 8);
/*  56 */         byte b4 = (byte)m;
/*  57 */         ((ByteBuffer)paramBuffer).put(b4);
/*  58 */         ((ByteBuffer)paramBuffer).put(b3);
/*  59 */         ((ByteBuffer)paramBuffer).put(b2);
/*  60 */         ((ByteBuffer)paramBuffer).put(b1);
/*     */       }
/*     */     }
/*  63 */     return true;
/*     */   }
/*     */ 
/*     */   public Graphics createGraphics() {
/*  67 */     J2DPresentable localJ2DPresentable = J2DPresentable.create(this.bimg, this.factory);
/*  68 */     Graphics2D localGraphics2D = this.bimg.createGraphics();
/*  69 */     return new J2DPrismGraphics(localJ2DPresentable, localGraphics2D);
/*     */   }
/*     */ 
/*     */   public Graphics2D createAWTGraphics2D() {
/*  73 */     return this.bimg.createGraphics();
/*     */   }
/*     */ 
/*     */   public Screen getAssociatedScreen() {
/*  77 */     return this.factory.getScreen();
/*     */   }
/*     */ 
/*     */   public long getNativeDestHandle() {
/*  81 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public void update(Image paramImage)
/*     */   {
/*  86 */     throw new UnsupportedOperationException("update() not supported for RTTextures");
/*     */   }
/*     */ 
/*     */   public void update(Image paramImage, int paramInt1, int paramInt2)
/*     */   {
/*  91 */     throw new UnsupportedOperationException("update() not supported for RTTextures");
/*     */   }
/*     */ 
/*     */   public void update(Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */   {
/*  96 */     throw new UnsupportedOperationException("update() not supported for RTTextures");
/*     */   }
/*     */ 
/*     */   public void update(Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean)
/*     */   {
/* 103 */     throw new UnsupportedOperationException("update() not supported for RTTextures");
/*     */   }
/*     */ 
/*     */   public void update(Buffer paramBuffer, PixelFormat paramPixelFormat, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, boolean paramBoolean)
/*     */   {
/* 112 */     throw new UnsupportedOperationException("update() not supported for RTTextures");
/*     */   }
/*     */ 
/*     */   public boolean isOpaque() {
/* 116 */     return this.opaque;
/*     */   }
/*     */ 
/*     */   public void setOpaque(boolean paramBoolean) {
/* 120 */     this.opaque = paramBoolean;
/*     */   }
/*     */ 
/*     */   public boolean isVolatile() {
/* 124 */     return false;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.j2d.J2DRTTexture
 * JD-Core Version:    0.6.2
 */