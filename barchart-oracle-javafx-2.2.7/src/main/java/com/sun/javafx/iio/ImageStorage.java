/*     */ package com.sun.javafx.iio;
/*     */ 
/*     */ import com.sun.javafx.iio.bmp.BMPImageLoaderFactory;
/*     */ import com.sun.javafx.iio.common.ImageTools;
/*     */ import com.sun.javafx.iio.gif.GIFImageLoaderFactory;
/*     */ import com.sun.javafx.iio.jpeg.JPEGImageLoaderFactory;
/*     */ import com.sun.javafx.iio.png.PNGImageLoaderFactory;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.SequenceInputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class ImageStorage
/*     */ {
/*     */   private static HashMap<byte[], ImageLoaderFactory> loaderFactoriesBySignature;
/* 100 */   private static int maxSignatureLength = 0;
/*     */ 
/* 104 */   private static ImageLoaderFactory[] loaderFactories = { GIFImageLoaderFactory.getInstance(), JPEGImageLoaderFactory.getInstance(), PNGImageLoaderFactory.getInstance(), BMPImageLoaderFactory.getInstance() };
/*     */ 
/*     */   public static ImageFormatDescription[] getSupportedDescriptions()
/*     */   {
/* 132 */     ImageFormatDescription[] arrayOfImageFormatDescription = new ImageFormatDescription[loaderFactories.length];
/* 133 */     for (int i = 0; i < loaderFactories.length; i++) {
/* 134 */       arrayOfImageFormatDescription[i] = loaderFactories[i].getFormatDescription();
/*     */     }
/* 136 */     return arrayOfImageFormatDescription;
/*     */   }
/*     */ 
/*     */   public static int getNumBands(ImageType paramImageType)
/*     */   {
/* 146 */     int i = -1;
/* 147 */     switch (1.$SwitchMap$com$sun$javafx$iio$ImageStorage$ImageType[paramImageType.ordinal()]) {
/*     */     case 1:
/*     */     case 2:
/*     */     case 3:
/*     */     case 4:
/*     */     case 5:
/* 153 */       i = 1;
/* 154 */       break;
/*     */     case 6:
/*     */     case 7:
/* 157 */       i = 2;
/* 158 */       break;
/*     */     case 8:
/* 160 */       i = 3;
/* 161 */       break;
/*     */     case 9:
/*     */     case 10:
/* 164 */       i = 4;
/* 165 */       break;
/*     */     default:
/* 167 */       throw new IllegalArgumentException("Unknown ImageType " + paramImageType);
/*     */     }
/* 169 */     return i;
/*     */   }
/*     */ 
/*     */   public static void addImageLoaderFactory(ImageLoaderFactory paramImageLoaderFactory)
/*     */   {
/* 180 */     ImageFormatDescription localImageFormatDescription = paramImageLoaderFactory.getFormatDescription();
/*     */ 
/* 185 */     byte[][] arrayOfByte = localImageFormatDescription.getSignatures();
/* 186 */     for (int i = 0; i < arrayOfByte.length; i++)
/* 187 */       loaderFactoriesBySignature.put(arrayOfByte[i], paramImageLoaderFactory);
/*     */   }
/*     */ 
/*     */   public static ImageFrame[] loadAll(InputStream paramInputStream, ImageLoadListener paramImageLoadListener, int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
/*     */     throws ImageStorageException
/*     */   {
/* 235 */     ImageLoader localImageLoader = null;
/*     */     try {
/* 237 */       localImageLoader = getLoaderBySignature(paramInputStream, paramImageLoadListener);
/*     */     } catch (IOException localIOException) {
/* 239 */       throw new ImageStorageException(localIOException.getMessage(), localIOException);
/*     */     }
/*     */ 
/* 242 */     ImageFrame[] arrayOfImageFrame = null;
/* 243 */     if (localImageLoader != null) {
/* 244 */       arrayOfImageFrame = loadAll(localImageLoader, paramInt1, paramInt2, paramBoolean1, paramBoolean2);
/*     */     }
/*     */ 
/* 247 */     return arrayOfImageFrame;
/*     */   }
/*     */ 
/*     */   public static ImageFrame[] loadAll(String paramString, ImageLoadListener paramImageLoadListener, int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
/*     */     throws ImageStorageException
/*     */   {
/* 258 */     ImageFrame[] arrayOfImageFrame = null;
/* 259 */     InputStream localInputStream = null;
/* 260 */     ImageLoader localImageLoader = null;
/*     */     try
/*     */     {
/*     */       try {
/* 264 */         localInputStream = ImageTools.createInputStream(paramString);
/* 265 */         localImageLoader = getLoaderBySignature(localInputStream, paramImageLoadListener);
/*     */       } catch (IOException localIOException1) {
/* 267 */         throw new ImageStorageException(localIOException1.getMessage(), localIOException1);
/*     */       }
/*     */ 
/* 270 */       if (localImageLoader != null)
/* 271 */         arrayOfImageFrame = loadAll(localImageLoader, paramInt1, paramInt2, paramBoolean1, paramBoolean2);
/*     */     }
/*     */     finally {
/*     */       try {
/* 275 */         if (localInputStream != null)
/* 276 */           localInputStream.close();
/*     */       }
/*     */       catch (IOException localIOException3)
/*     */       {
/*     */       }
/*     */     }
/* 282 */     return arrayOfImageFrame;
/*     */   }
/*     */ 
/*     */   private static ImageFrame[] loadAll(ImageLoader paramImageLoader, int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
/*     */     throws ImageStorageException
/*     */   {
/* 288 */     ImageFrame[] arrayOfImageFrame = null;
/* 289 */     ArrayList localArrayList = new ArrayList();
/* 290 */     int i = 0;
/* 291 */     ImageFrame localImageFrame = null;
/*     */     while (true) {
/*     */       try {
/* 294 */         localImageFrame = paramImageLoader.load(i++, paramInt1, paramInt2, paramBoolean1, paramBoolean2);
/*     */       } catch (Exception localException) {
/* 296 */         throw new ImageStorageException(localException.getMessage(), localException);
/*     */       }
/* 298 */       if (localImageFrame == null) break;
/* 299 */       localArrayList.add(localImageFrame);
/*     */     }
/*     */ 
/* 304 */     int j = localArrayList.size();
/* 305 */     if (j > 0) {
/* 306 */       arrayOfImageFrame = new ImageFrame[j];
/* 307 */       localArrayList.toArray(arrayOfImageFrame);
/*     */     }
/* 309 */     return arrayOfImageFrame;
/*     */   }
/*     */ 
/*     */   private static ImageLoader getLoaderBySignature(InputStream paramInputStream, ImageLoadListener paramImageLoadListener)
/*     */     throws IOException
/*     */   {
/* 335 */     ImageLoader localImageLoader = null;
/*     */ 
/* 337 */     byte[] arrayOfByte = new byte[maxSignatureLength];
/* 338 */     paramInputStream.read(arrayOfByte);
/*     */ 
/* 340 */     Iterator localIterator = loaderFactoriesBySignature.keySet().iterator();
/* 341 */     ImageLoaderFactory localImageLoaderFactory = null;
/*     */     Object localObject;
/* 342 */     while (localIterator.hasNext()) {
/* 343 */       localObject = (byte[])localIterator.next();
/* 344 */       int i = localObject.length;
/* 345 */       int j = 1;
/* 346 */       for (int k = 0; k < i; k++) {
/* 347 */         if (arrayOfByte[k] != localObject[k]) {
/* 348 */           j = 0;
/* 349 */           break;
/*     */         }
/*     */       }
/* 352 */       if (j != 0) {
/* 353 */         localImageLoaderFactory = (ImageLoaderFactory)loaderFactoriesBySignature.get(localObject);
/* 354 */         break;
/*     */       }
/*     */     }
/*     */ 
/* 358 */     if (localImageLoaderFactory != null) {
/* 359 */       localObject = new ByteArrayInputStream(arrayOfByte);
/* 360 */       SequenceInputStream localSequenceInputStream = new SequenceInputStream((InputStream)localObject, paramInputStream);
/* 361 */       localImageLoader = localImageLoaderFactory.createImageLoader(localSequenceInputStream);
/* 362 */       if (paramImageLoadListener != null) {
/* 363 */         localImageLoader.addListener(paramImageLoadListener);
/*     */       }
/*     */     }
/*     */ 
/* 367 */     return localImageLoader;
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/* 112 */     int i = 0;
/* 113 */     int j = loaderFactories.length;
/* 114 */     for (int k = 0; k < j; k++) {
/* 115 */       ImageFormatDescription localImageFormatDescription = loaderFactories[k].getFormatDescription();
/* 116 */       i += localImageFormatDescription.getExtensions().length;
/* 117 */       byte[][] arrayOfByte = localImageFormatDescription.getSignatures();
/* 118 */       for (int m = 0; m < arrayOfByte.length; m++) {
/* 119 */         maxSignatureLength = Math.max(arrayOfByte[m].length, maxSignatureLength);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 124 */     loaderFactoriesBySignature = new HashMap(loaderFactories.length);
/*     */ 
/* 126 */     for (k = 0; k < j; k++)
/* 127 */       addImageLoaderFactory(loaderFactories[k]);
/*     */   }
/*     */ 
/*     */   public static enum ImageType
/*     */   {
/*  39 */     GRAY, 
/*     */ 
/*  44 */     GRAY_ALPHA, 
/*     */ 
/*  49 */     GRAY_ALPHA_PRE, 
/*     */ 
/*  54 */     PALETTE, 
/*     */ 
/*  60 */     PALETTE_ALPHA, 
/*     */ 
/*  66 */     PALETTE_ALPHA_PRE, 
/*     */ 
/*  73 */     PALETTE_TRANS, 
/*     */ 
/*  78 */     RGB, 
/*     */ 
/*  84 */     RGBA, 
/*     */ 
/*  90 */     RGBA_PRE;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.iio.ImageStorage
 * JD-Core Version:    0.6.2
 */