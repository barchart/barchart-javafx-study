/*     */ package com.sun.prism.impl;
/*     */ 
/*     */ import com.sun.prism.Image;
/*     */ import com.sun.prism.PixelFormat;
/*     */ import com.sun.prism.PixelFormat.DataType;
/*     */ import com.sun.prism.Texture;
/*     */ import com.sun.prism.Texture.WrapMode;
/*     */ import java.nio.Buffer;
/*     */ 
/*     */ public abstract class BaseTexture extends BaseGraphicsResource
/*     */   implements Texture
/*     */ {
/*     */   private final PixelFormat format;
/*     */   private final int physicalWidth;
/*     */   private final int physicalHeight;
/*     */   private final int contentX;
/*     */   private final int contentY;
/*     */   private final int contentWidth;
/*     */   private final int contentHeight;
/*  29 */   private Texture.WrapMode wrapMode = Texture.WrapMode.CLAMP_TO_EDGE;
/*  30 */   private boolean linearFiltering = true;
/*     */   private int lastImageSerial;
/*     */ 
/*     */   protected BaseTexture(PixelFormat paramPixelFormat, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, Disposer.Record paramRecord)
/*     */   {
/*  39 */     super(paramRecord);
/*  40 */     this.format = paramPixelFormat;
/*  41 */     this.physicalWidth = paramInt1;
/*  42 */     this.physicalHeight = paramInt2;
/*  43 */     this.contentX = paramInt3;
/*  44 */     this.contentY = paramInt4;
/*  45 */     this.contentWidth = paramInt5;
/*  46 */     this.contentHeight = paramInt6;
/*     */   }
/*     */ 
/*     */   public PixelFormat getPixelFormat() {
/*  50 */     return this.format;
/*     */   }
/*     */ 
/*     */   public int getPhysicalWidth() {
/*  54 */     return this.physicalWidth;
/*     */   }
/*     */ 
/*     */   public int getPhysicalHeight() {
/*  58 */     return this.physicalHeight;
/*     */   }
/*     */ 
/*     */   public int getContentX() {
/*  62 */     return this.contentX;
/*     */   }
/*     */ 
/*     */   public int getContentY() {
/*  66 */     return this.contentY;
/*     */   }
/*     */ 
/*     */   public int getContentWidth() {
/*  70 */     return this.contentWidth;
/*     */   }
/*     */ 
/*     */   public int getContentHeight() {
/*  74 */     return this.contentHeight;
/*     */   }
/*     */ 
/*     */   public Texture.WrapMode getWrapMode() {
/*  78 */     return this.wrapMode;
/*     */   }
/*     */ 
/*     */   public void setWrapMode(Texture.WrapMode paramWrapMode) {
/*  82 */     this.wrapMode = paramWrapMode;
/*     */   }
/*     */ 
/*     */   public boolean getLinearFiltering() {
/*  86 */     return this.linearFiltering;
/*     */   }
/*     */ 
/*     */   public void setLinearFiltering(boolean paramBoolean) {
/*  90 */     this.linearFiltering = paramBoolean;
/*     */   }
/*     */ 
/*     */   public int getLastImageSerial()
/*     */   {
/*  95 */     return this.lastImageSerial;
/*     */   }
/*     */ 
/*     */   public void setLastImageSerial(int paramInt)
/*     */   {
/* 100 */     this.lastImageSerial = paramInt;
/*     */   }
/*     */ 
/*     */   public void update(Image paramImage) {
/* 104 */     update(paramImage, 0, 0);
/*     */   }
/*     */ 
/*     */   public void update(Image paramImage, int paramInt1, int paramInt2) {
/* 108 */     update(paramImage, paramInt1, paramInt2, paramImage.getWidth(), paramImage.getHeight());
/*     */   }
/*     */ 
/*     */   public void update(Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 112 */     update(paramImage, paramInt1, paramInt2, paramInt3, paramInt4, false);
/*     */   }
/*     */ 
/*     */   public void update(Image paramImage, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean)
/*     */   {
/* 118 */     Buffer localBuffer = paramImage.getPixelBuffer();
/* 119 */     int i = localBuffer.position();
/* 120 */     update(localBuffer, paramImage.getPixelFormat(), paramInt1, paramInt2, paramImage.getMinX(), paramImage.getMinY(), paramInt3, paramInt4, paramImage.getScanlineStride(), paramBoolean);
/*     */ 
/* 124 */     localBuffer.position(i);
/*     */   }
/*     */ 
/*     */   protected void checkUpdateParams(Buffer paramBuffer, PixelFormat paramPixelFormat, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7)
/*     */   {
/* 133 */     if (this.format == PixelFormat.MULTI_YCbCr_420) {
/* 134 */       throw new IllegalArgumentException("MULTI_YCbCr_420 requires multitexturing");
/*     */     }
/* 136 */     if (paramBuffer == null) {
/* 137 */       throw new IllegalArgumentException("Pixel buffer must be non-null");
/*     */     }
/* 139 */     if (paramPixelFormat != this.format) {
/* 140 */       throw new IllegalArgumentException("Image format (" + paramPixelFormat + ") " + "must match texture format (" + this.format + ")");
/*     */     }
/*     */ 
/* 144 */     if ((paramInt1 < 0) || (paramInt2 < 0)) {
/* 145 */       throw new IllegalArgumentException("dstx (" + paramInt1 + ") and dsty (" + paramInt2 + ") must be >= 0");
/*     */     }
/*     */ 
/* 148 */     if ((paramInt3 < 0) || (paramInt4 < 0)) {
/* 149 */       throw new IllegalArgumentException("srcx (" + paramInt3 + ") and srcy (" + paramInt4 + ") must be >= 0");
/*     */     }
/*     */ 
/* 152 */     if ((paramInt5 <= 0) || (paramInt6 <= 0)) {
/* 153 */       throw new IllegalArgumentException("srcw (" + paramInt5 + ") and srch (" + paramInt6 + ") must be > 0");
/*     */     }
/*     */ 
/* 156 */     int i = paramPixelFormat.getBytesPerPixelUnit();
/* 157 */     if (paramInt7 % i != 0) {
/* 158 */       throw new IllegalArgumentException("srcscan (" + paramInt7 + ") " + "must be a multiple of the pixel stride (" + i + ")");
/*     */     }
/*     */ 
/* 162 */     if (paramInt5 > paramInt7 / i) {
/* 163 */       throw new IllegalArgumentException("srcw (" + paramInt5 + ") " + "must be <= srcscan/bytesPerPixel (" + paramInt7 / i + ")");
/*     */     }
/*     */ 
/* 168 */     if ((paramInt1 + paramInt5 > this.contentWidth) || (paramInt2 + paramInt6 > this.contentHeight)) {
/* 169 */       throw new IllegalArgumentException("Destination region (x=" + paramInt1 + ", y=" + paramInt2 + ", w=" + paramInt5 + ", h=" + paramInt6 + ") " + "must fit within texture content bounds " + "(contentWidth=" + this.contentWidth + ", contentHeight=" + this.contentHeight + ")");
/*     */     }
/*     */ 
/* 177 */     int j = paramInt3 * i + paramInt4 * paramInt7 + (paramInt6 - 1) * paramInt7 + paramInt5 * i;
/*     */ 
/* 180 */     int k = j / this.format.getDataType().getSizeInBytes();
/* 181 */     if (k > paramBuffer.remaining())
/* 182 */       throw new IllegalArgumentException("Upload requires " + k + " elements, but only " + paramBuffer.remaining() + " elements remain in the buffer");
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 190 */     return super.toString() + " [format=" + this.format + " physicalWidth=" + this.physicalWidth + " physicalHeight=" + this.physicalHeight + " contentX=" + this.contentX + " contentY=" + this.contentY + " contentWidth=" + this.contentWidth + " contentHeight=" + this.contentHeight + " wrapMode=" + this.wrapMode + " linearFiltering=" + this.linearFiltering + "]";
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.impl.BaseTexture
 * JD-Core Version:    0.6.2
 */