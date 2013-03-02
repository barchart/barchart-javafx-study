/*     */ package com.sun.javafx.iio.bmp;
/*     */ 
/*     */ import com.sun.javafx.iio.ImageFormatDescription;
/*     */ import com.sun.javafx.iio.ImageLoader;
/*     */ import com.sun.javafx.iio.ImageLoaderFactory;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ public final class BMPImageLoaderFactory
/*     */   implements ImageLoaderFactory
/*     */ {
/* 206 */   private static final BMPImageLoaderFactory theInstance = new BMPImageLoaderFactory();
/*     */ 
/*     */   public static ImageLoaderFactory getInstance()
/*     */   {
/* 210 */     return theInstance;
/*     */   }
/*     */ 
/*     */   public ImageFormatDescription getFormatDescription() {
/* 214 */     return BMPDescriptor.theInstance;
/*     */   }
/*     */ 
/*     */   public ImageLoader createImageLoader(InputStream paramInputStream) throws IOException {
/* 218 */     return new BMPImageLoader(paramInputStream);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.iio.bmp.BMPImageLoaderFactory
 * JD-Core Version:    0.6.2
 */