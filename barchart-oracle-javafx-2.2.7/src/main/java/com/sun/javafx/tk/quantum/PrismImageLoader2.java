/*     */ package com.sun.javafx.tk.quantum;
/*     */ 
/*     */ import com.sun.javafx.iio.ImageFrame;
/*     */ import com.sun.javafx.iio.ImageLoadListener;
/*     */ import com.sun.javafx.iio.ImageMetadata;
/*     */ import com.sun.javafx.iio.ImageStorage;
/*     */ import com.sun.javafx.runtime.async.AbstractRemoteResource;
/*     */ import com.sun.javafx.runtime.async.AsyncOperationListener;
/*     */ import com.sun.javafx.tk.PlatformImage;
/*     */ import com.sun.prism.Image;
/*     */ import com.sun.prism.impl.PrismSettings;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ public class PrismImageLoader2
/*     */   implements com.sun.javafx.tk.ImageLoader
/*     */ {
/*     */   private Image[] images;
/*     */   private int[] delayTimes;
/*     */   private int width;
/*     */   private int height;
/*     */   private boolean error;
/*     */   private Exception exception;
/*     */ 
/*     */   public PrismImageLoader2(String paramString, int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
/*     */   {
/*  35 */     loadAll(paramString, paramInt1, paramInt2, paramBoolean1, paramBoolean2);
/*     */   }
/*     */ 
/*     */   public PrismImageLoader2(InputStream paramInputStream, int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
/*     */   {
/*  41 */     loadAll(paramInputStream, paramInt1, paramInt2, paramBoolean1, paramBoolean2);
/*     */   }
/*     */ 
/*     */   public int getWidth() {
/*  45 */     return this.width;
/*     */   }
/*     */ 
/*     */   public int getHeight() {
/*  49 */     return this.height;
/*     */   }
/*     */ 
/*     */   public int getFrameCount() {
/*  53 */     if (this.images == null) {
/*  54 */       return 0;
/*     */     }
/*  56 */     return this.images.length;
/*     */   }
/*     */ 
/*     */   public PlatformImage getFrame(int paramInt) {
/*  60 */     if (this.images == null) {
/*  61 */       return null;
/*     */     }
/*  63 */     return this.images[paramInt];
/*     */   }
/*     */ 
/*     */   public PlatformImage[] getFrames() {
/*  67 */     return this.images;
/*     */   }
/*     */ 
/*     */   public int getFrameDelay(int paramInt) {
/*  71 */     if (this.images == null) {
/*  72 */       return 0;
/*     */     }
/*  74 */     return this.delayTimes[paramInt];
/*     */   }
/*     */ 
/*     */   public boolean getError() {
/*  78 */     return this.error;
/*     */   }
/*     */ 
/*     */   public Exception getException() {
/*  82 */     return this.exception;
/*     */   }
/*     */ 
/*     */   private void loadAll(String paramString, int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
/*     */   {
/*  88 */     PrismLoadListener localPrismLoadListener = new PrismLoadListener(null);
/*     */     try {
/*  90 */       ImageFrame[] arrayOfImageFrame = ImageStorage.loadAll(paramString, localPrismLoadListener, paramInt1, paramInt2, paramBoolean1, paramBoolean2);
/*     */ 
/*  92 */       convertAll(arrayOfImageFrame);
/*     */     } catch (Exception localException) {
/*  94 */       if (PrismSettings.verbose) {
/*  95 */         localException.getCause().printStackTrace(System.err);
/*     */       }
/*  97 */       this.error = true;
/*  98 */       this.exception = localException;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void loadAll(InputStream paramInputStream, int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
/*     */   {
/* 105 */     PrismLoadListener localPrismLoadListener = new PrismLoadListener(null);
/*     */     try {
/* 107 */       ImageFrame[] arrayOfImageFrame = ImageStorage.loadAll(paramInputStream, localPrismLoadListener, paramInt1, paramInt2, paramBoolean1, paramBoolean2);
/*     */ 
/* 109 */       convertAll(arrayOfImageFrame);
/*     */     } catch (Exception localException) {
/* 111 */       if (PrismSettings.verbose) {
/* 112 */         localException.getCause().printStackTrace(System.err);
/*     */       }
/* 114 */       this.error = true;
/* 115 */       this.exception = localException;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void convertAll(ImageFrame[] paramArrayOfImageFrame) {
/* 120 */     int i = paramArrayOfImageFrame.length;
/* 121 */     this.images = new Image[i];
/* 122 */     this.delayTimes = new int[i];
/* 123 */     for (int j = 0; j < i; j++) {
/* 124 */       ImageFrame localImageFrame = paramArrayOfImageFrame[j];
/* 125 */       this.images[j] = Image.convertImageFrame(localImageFrame);
/* 126 */       ImageMetadata localImageMetadata = localImageFrame.getMetadata();
/* 127 */       if (localImageMetadata != null) {
/* 128 */         Integer localInteger = localImageMetadata.delayTime;
/* 129 */         if (localInteger != null) {
/* 130 */           this.delayTimes[j] = localInteger.intValue();
/*     */         }
/*     */       }
/* 133 */       if (j == 0) {
/* 134 */         this.width = localImageFrame.getWidth();
/* 135 */         this.height = localImageFrame.getHeight();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   static class AsyncImageLoader extends AbstractRemoteResource<PrismImageLoader2>
/*     */   {
/*     */     int width;
/*     */     int height;
/*     */     boolean preserveRatio;
/*     */     boolean smooth;
/*     */ 
/*     */     public AsyncImageLoader(AsyncOperationListener<PrismImageLoader2> paramAsyncOperationListener, String paramString, int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
/*     */     {
/* 174 */       super(paramAsyncOperationListener);
/* 175 */       this.width = paramInt1;
/* 176 */       this.height = paramInt2;
/* 177 */       this.preserveRatio = paramBoolean1;
/* 178 */       this.smooth = paramBoolean2;
/*     */     }
/*     */ 
/*     */     protected PrismImageLoader2 processStream(InputStream paramInputStream) throws IOException {
/* 182 */       return new PrismImageLoader2(paramInputStream, this.width, this.height, this.preserveRatio, this.smooth);
/*     */     }
/*     */   }
/*     */ 
/*     */   private class PrismLoadListener
/*     */     implements ImageLoadListener
/*     */   {
/*     */     private PrismLoadListener()
/*     */     {
/*     */     }
/*     */ 
/*     */     public void imageLoadWarning(com.sun.javafx.iio.ImageLoader paramImageLoader, String paramString)
/*     */     {
/* 143 */       PrismImageLoader2.this.error = true;
/*     */     }
/*     */ 
/*     */     public void imageLoadProgress(com.sun.javafx.iio.ImageLoader paramImageLoader, float paramFloat)
/*     */     {
/*     */     }
/*     */ 
/*     */     public void imageLoadMetaData(com.sun.javafx.iio.ImageLoader paramImageLoader, ImageMetadata paramImageMetadata)
/*     */     {
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.quantum.PrismImageLoader2
 * JD-Core Version:    0.6.2
 */