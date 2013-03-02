/*     */ package com.sun.javafx.iio.bmp;
/*     */ 
/*     */ import java.io.IOException;
/*     */ 
/*     */ final class BitmapInfoHeader
/*     */ {
/*     */   static final int BIH_SIZE = 40;
/*     */   static final int BIH4_SIZE = 108;
/*     */   static final int BIH5_SIZE = 124;
/*     */   final int biSize;
/*     */   final int biWidth;
/*     */   final int biHeight;
/*     */   final short biPlanes;
/*     */   final short biBitCount;
/*     */   final int biCompression;
/*     */   final int biSizeImage;
/*     */   final int biXPelsPerMeter;
/*     */   final int biYPelsPerMeter;
/*     */   final int biClrUsed;
/*     */   final int biClrImportant;
/*     */ 
/*     */   BitmapInfoHeader(LEInputStream paramLEInputStream)
/*     */     throws IOException
/*     */   {
/*  82 */     this.biSize = paramLEInputStream.readInt();
/*  83 */     this.biWidth = paramLEInputStream.readInt();
/*  84 */     this.biHeight = paramLEInputStream.readInt();
/*  85 */     this.biPlanes = paramLEInputStream.readShort();
/*  86 */     this.biBitCount = paramLEInputStream.readShort();
/*  87 */     this.biCompression = paramLEInputStream.readInt();
/*  88 */     this.biSizeImage = paramLEInputStream.readInt();
/*  89 */     this.biXPelsPerMeter = paramLEInputStream.readInt();
/*  90 */     this.biYPelsPerMeter = paramLEInputStream.readInt();
/*  91 */     this.biClrUsed = paramLEInputStream.readInt();
/*  92 */     this.biClrImportant = paramLEInputStream.readInt();
/*     */ 
/*  94 */     if (this.biSize > 40) {
/*  95 */       if ((this.biSize == 108) || (this.biSize == 124))
/*  96 */         paramLEInputStream.skipBytes(this.biSize - 40);
/*     */       else {
/*  98 */         throw new IOException("BitmapInfoHeader is corrupt");
/*     */       }
/*     */     }
/* 101 */     validate();
/*     */   }
/*     */ 
/*     */   void validate() {
/* 105 */     if ((this.biCompression != 0) || (this.biPlanes != 1) || (this.biBitCount != 24))
/* 106 */       throw new RuntimeException("Unsupported BMP image: only 24 bit uncompressed BMP`s is supported");
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.iio.bmp.BitmapInfoHeader
 * JD-Core Version:    0.6.2
 */