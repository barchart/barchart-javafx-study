/*     */ package com.sun.javafx.iio.bmp;
/*     */ 
/*     */ import com.sun.javafx.iio.ImageFrame;
/*     */ import com.sun.javafx.iio.ImageMetadata;
/*     */ import com.sun.javafx.iio.ImageStorage.ImageType;
/*     */ import com.sun.javafx.iio.common.ImageLoaderImpl;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ 
/*     */ final class BMPImageLoader extends ImageLoaderImpl
/*     */ {
/*     */   static final short BM = 19778;
/*     */   static final int BFH_SIZE = 14;
/*     */   final LEInputStream data;
/*     */   short bfType;
/*     */   int bfSize;
/*     */   int bfOffBits;
/*     */   int[] bgra_palette;
/*     */   BitmapInfoHeader bih;
/*     */ 
/*     */   BMPImageLoader(InputStream paramInputStream)
/*     */     throws IOException
/*     */   {
/* 127 */     super(BMPDescriptor.theInstance);
/* 128 */     this.data = new LEInputStream(paramInputStream);
/* 129 */     this.bfType = this.data.readShort();
/* 130 */     if (isValid())
/* 131 */       readHeader();
/*     */   }
/*     */ 
/*     */   private void readHeader() throws IOException
/*     */   {
/* 136 */     this.bfSize = this.data.readInt();
/* 137 */     this.data.skipBytes(4);
/* 138 */     this.bfOffBits = this.data.readInt();
/* 139 */     this.bih = new BitmapInfoHeader(this.data);
/* 140 */     if (this.bih.biSize + 14 != this.bfOffBits)
/* 141 */       this.data.skipBytes(this.bfOffBits - this.bih.biSize - 14);
/*     */   }
/*     */ 
/*     */   private boolean isValid()
/*     */   {
/* 146 */     return this.bfType == 19778;
/*     */   }
/*     */   public void dispose() {
/*     */   }
/*     */ 
/*     */   static void GBRtoRGB(byte[] paramArrayOfByte, int paramInt1, int paramInt2) {
/* 152 */     for (int i = paramInt2 / 3; i != 0; i--) {
/* 153 */       int j = paramArrayOfByte[paramInt1]; int k = paramArrayOfByte[(paramInt1 + 2)];
/* 154 */       paramArrayOfByte[(paramInt1 + 2)] = j; paramArrayOfByte[paramInt1] = k;
/* 155 */       paramInt1 += 3;
/*     */     }
/*     */   }
/*     */ 
/*     */   public ImageFrame load(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean1, boolean paramBoolean2)
/*     */     throws IOException
/*     */   {
/* 162 */     if (0 != paramInt1) {
/* 163 */       return null;
/*     */     }
/*     */ 
/* 166 */     if (((paramInt2 > 0) && (paramInt2 != this.bih.biWidth)) || ((paramInt3 > 0) && (paramInt3 != this.bih.biHeight)))
/*     */     {
/* 169 */       throw new RuntimeException("scaling for BMP is not supported");
/*     */     }
/*     */ 
/* 173 */     ImageMetadata localImageMetadata = new ImageMetadata(null, Boolean.TRUE, null, null, null, null, Integer.valueOf(this.bih.biWidth), Integer.valueOf(this.bih.biHeight), null, null, null);
/*     */ 
/* 176 */     updateImageMetadata(localImageMetadata);
/*     */ 
/* 178 */     int i = this.bih.biBitCount * this.bih.biWidth / 8 + 3 & 0xFFFFFFFC;
/* 179 */     int j = this.bih.biBitCount / 8 * this.bih.biWidth;
/*     */ 
/* 181 */     int k = Math.abs(this.bih.biHeight);
/*     */ 
/* 183 */     byte[] arrayOfByte = new byte[j * k];
/*     */ 
/* 185 */     for (int m = 0; m != k; m++) {
/* 186 */       int n = this.bih.biHeight < 0 ? m : k - m - 1;
/* 187 */       int i1 = this.data.in.read(arrayOfByte, n * j, j);
/* 188 */       GBRtoRGB(arrayOfByte, n * j, i1);
/*     */ 
/* 190 */       if (i1 != j)
/*     */       {
/*     */         break;
/*     */       }
/* 194 */       if (i1 < i) {
/* 195 */         this.data.skipBytes(i - i1);
/*     */       }
/*     */     }
/*     */ 
/* 199 */     return new ImageFrame(ImageStorage.ImageType.RGB, ByteBuffer.wrap(arrayOfByte), this.bih.biWidth, k, j, (byte[][])null, null);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.iio.bmp.BMPImageLoader
 * JD-Core Version:    0.6.2
 */