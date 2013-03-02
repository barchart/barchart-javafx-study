/*    */ package com.sun.javafx.iio.common;
/*    */ 
/*    */ import com.sun.javafx.iio.ImageFormatDescription;
/*    */ import com.sun.javafx.iio.ImageLoadListener;
/*    */ import com.sun.javafx.iio.ImageLoader;
/*    */ import com.sun.javafx.iio.ImageMetadata;
/*    */ import java.util.HashSet;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ public abstract class ImageLoaderImpl
/*    */   implements ImageLoader
/*    */ {
/*    */   protected ImageFormatDescription formatDescription;
/*    */   protected HashSet<ImageLoadListener> listeners;
/* 25 */   protected int lastPercentDone = -1;
/*    */ 
/*    */   protected ImageLoaderImpl(ImageFormatDescription paramImageFormatDescription) {
/* 28 */     if (paramImageFormatDescription == null) {
/* 29 */       throw new IllegalArgumentException("formatDescription == null!");
/*    */     }
/*    */ 
/* 32 */     this.formatDescription = paramImageFormatDescription;
/*    */   }
/*    */ 
/*    */   public final ImageFormatDescription getFormatDescription() {
/* 36 */     return this.formatDescription;
/*    */   }
/*    */ 
/*    */   public final void addListener(ImageLoadListener paramImageLoadListener) {
/* 40 */     if (this.listeners == null) {
/* 41 */       this.listeners = new HashSet();
/*    */     }
/* 43 */     this.listeners.add(paramImageLoadListener);
/*    */   }
/*    */ 
/*    */   public final void removeListener(ImageLoadListener paramImageLoadListener) {
/* 47 */     if (this.listeners != null)
/* 48 */       this.listeners.remove(paramImageLoadListener);
/*    */   }
/*    */ 
/*    */   protected void emitWarning(String paramString)
/*    */   {
/* 53 */     if ((this.listeners != null) && (!this.listeners.isEmpty())) {
/* 54 */       Iterator localIterator = this.listeners.iterator();
/* 55 */       while (localIterator.hasNext()) {
/* 56 */         ImageLoadListener localImageLoadListener = (ImageLoadListener)localIterator.next();
/* 57 */         localImageLoadListener.imageLoadWarning(this, paramString);
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   protected void updateImageProgress(float paramFloat) {
/* 63 */     if ((this.listeners != null) && (!this.listeners.isEmpty())) {
/* 64 */       int i = (int)paramFloat;
/* 65 */       int j = 5;
/* 66 */       if ((j * i / j % j == 0) && (i != this.lastPercentDone)) {
/* 67 */         this.lastPercentDone = i;
/* 68 */         Iterator localIterator = this.listeners.iterator();
/* 69 */         while (localIterator.hasNext()) {
/* 70 */           ImageLoadListener localImageLoadListener = (ImageLoadListener)localIterator.next();
/* 71 */           localImageLoadListener.imageLoadProgress(this, i);
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   protected void updateImageMetadata(ImageMetadata paramImageMetadata) {
/* 78 */     if ((this.listeners != null) && (!this.listeners.isEmpty())) {
/* 79 */       Iterator localIterator = this.listeners.iterator();
/* 80 */       while (localIterator.hasNext()) {
/* 81 */         ImageLoadListener localImageLoadListener = (ImageLoadListener)localIterator.next();
/* 82 */         localImageLoadListener.imageLoadMetaData(this, paramImageMetadata);
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.iio.common.ImageLoaderImpl
 * JD-Core Version:    0.6.2
 */