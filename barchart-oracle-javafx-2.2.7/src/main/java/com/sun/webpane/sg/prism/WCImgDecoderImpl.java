/*     */ package com.sun.webpane.sg.prism;
/*     */ 
/*     */ import com.sun.javafx.iio.ImageFrame;
/*     */ import com.sun.javafx.iio.ImageLoadListener;
/*     */ import com.sun.javafx.iio.ImageLoader;
/*     */ import com.sun.javafx.iio.ImageMetadata;
/*     */ import com.sun.javafx.iio.ImageStorage;
/*     */ import com.sun.javafx.iio.ImageStorageException;
/*     */ import com.sun.webpane.platform.graphics.WCGraphicsManager;
/*     */ import com.sun.webpane.platform.graphics.WCImage;
/*     */ import com.sun.webpane.platform.graphics.WCImageFrame;
/*     */ import com.sun.webpane.platform.graphics.WCImgDecoder;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javafx.beans.property.ReadOnlyObjectProperty;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ import javafx.concurrent.Service;
/*     */ import javafx.concurrent.Task;
/*     */ 
/*     */ public class WCImgDecoderImpl extends WCImgDecoder
/*     */ {
/*  42 */   private static final Logger log = Logger.getLogger(WCImgDecoderImpl.class.getName());
/*     */   private Service<ImageFrame[]> loader;
/*     */   private int imageWidth;
/*     */   private int imageHeight;
/*     */   private ImageFrame[] frames;
/*     */   private PrismImage[] images;
/*     */   private volatile byte[] data;
/*     */   private volatile int dataSize;
/*     */   ImageLoadListener readerListener;
/*     */ 
/*     */   public WCImgDecoderImpl()
/*     */   {
/*  34 */     this.imageWidth = 0;
/*  35 */     this.imageHeight = 0;
/*     */ 
/* 153 */     this.readerListener = new ImageLoadListener() {
/*     */       public void imageLoadProgress(ImageLoader paramAnonymousImageLoader, float paramAnonymousFloat) {
/*     */       }
/*     */       public void imageLoadWarning(ImageLoader paramAnonymousImageLoader, String paramAnonymousString) {
/*     */       }
/*     */       public void imageLoadMetaData(ImageLoader paramAnonymousImageLoader, ImageMetadata paramAnonymousImageMetadata) {
/* 159 */         if (WCImgDecoderImpl.log.isLoggable(Level.FINE)) {
/* 160 */           WCImgDecoderImpl.log.fine(String.format("%s Image size %dx%d", new Object[] { this, paramAnonymousImageMetadata.imageWidth, paramAnonymousImageMetadata.imageHeight }));
/*     */         }
/*     */ 
/* 165 */         if (WCImgDecoderImpl.this.imageWidth < paramAnonymousImageMetadata.imageWidth.intValue()) {
/* 166 */           WCImgDecoderImpl.this.imageWidth = paramAnonymousImageMetadata.imageWidth.intValue();
/*     */         }
/* 168 */         if (WCImgDecoderImpl.this.imageHeight < paramAnonymousImageMetadata.imageHeight.intValue())
/* 169 */           WCImgDecoderImpl.this.imageHeight = paramAnonymousImageMetadata.imageHeight.intValue();
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public void destroy()
/*     */   {
/*  51 */     if (log.isLoggable(Level.FINE)) {
/*  52 */       log.fine(String.format("%s Destroy image decoder", new Object[] { this }));
/*     */     }
/*     */ 
/*  55 */     super.destroy();
/*     */ 
/*  57 */     if (this.loader != null) {
/*  58 */       this.loader.cancel();
/*  59 */       this.loader = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getFilenameExtension()
/*     */   {
/*  65 */     return ".img";
/*     */   }
/*     */ 
/*     */   public void addImageData(byte[] paramArrayOfByte) {
/*  69 */     if (paramArrayOfByte != null) {
/*  70 */       if (this.data == null) {
/*  71 */         this.data = paramArrayOfByte;
/*  72 */         this.dataSize = paramArrayOfByte.length;
/*     */       }
/*     */       else {
/*  75 */         int i = this.dataSize + paramArrayOfByte.length;
/*  76 */         if (i > this.data.length) {
/*  77 */           byte[] arrayOfByte = new byte[Math.max(i, this.data.length * 2)];
/*  78 */           System.arraycopy(this.data, 0, arrayOfByte, 0, this.dataSize);
/*  79 */           this.data = arrayOfByte;
/*     */         }
/*  81 */         System.arraycopy(paramArrayOfByte, 0, this.data, this.dataSize, paramArrayOfByte.length);
/*  82 */         this.dataSize = i;
/*     */       }
/*     */     }
/*  85 */     if (this.loader == null) {
/*  86 */       this.loader = new Service() {
/*     */         protected Task<ImageFrame[]> createTask() {
/*  88 */           return new Task() {
/*     */             protected ImageFrame[] call() throws Exception {
/*  90 */               return WCImgDecoderImpl.this.loadFrames();
/*     */             }
/*     */           };
/*     */         }
/*     */       };
/*  95 */       this.loader.valueProperty().addListener(new ChangeListener() {
/*     */         public void changed(ObservableValue<? extends ImageFrame[]> paramAnonymousObservableValue, ImageFrame[] paramAnonymousArrayOfImageFrame1, ImageFrame[] paramAnonymousArrayOfImageFrame2) {
/*  97 */           if ((paramAnonymousArrayOfImageFrame2 != null) && (WCImgDecoderImpl.this.loader != null)) {
/*  98 */             WCImgDecoderImpl.this.setFrames(paramAnonymousArrayOfImageFrame2);
/*     */           }
/*     */         }
/*     */       });
/*     */     }
/* 103 */     if (paramArrayOfByte == null) {
/* 104 */       this.loader.cancel();
/* 105 */       this.loader = null;
/* 106 */       setFrames(loadFrames());
/*     */     }
/* 108 */     else if (!this.loader.isRunning()) {
/* 109 */       this.loader.restart();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void loadFromResource(String paramString) {
/* 114 */     if (log.isLoggable(Level.FINE)) {
/* 115 */       log.fine(String.format("%s Load image from resource '%s'", new Object[] { this, paramString }));
/*     */     }
/*     */ 
/* 119 */     String str = WCGraphicsManager.getResourceName(paramString);
/* 120 */     InputStream localInputStream = getClass().getResourceAsStream(str);
/* 121 */     if (localInputStream == null) {
/* 122 */       if (log.isLoggable(Level.FINE)) {
/* 123 */         log.fine(String.format("%s Unable to open resource '%s'", new Object[] { this, str }));
/*     */       }
/*     */ 
/* 126 */       return;
/*     */     }
/*     */ 
/* 129 */     setFrames(loadFrames(localInputStream));
/*     */   }
/*     */ 
/*     */   private ImageFrame[] loadFrames(InputStream paramInputStream) {
/* 133 */     if (log.isLoggable(Level.FINE))
/* 134 */       log.fine(String.format("%s Decoding frames", new Object[] { this }));
/*     */     try
/*     */     {
/* 137 */       return ImageStorage.loadAll(paramInputStream, this.readerListener, 0, 0, true, false);
/*     */     } catch (ImageStorageException localImageStorageException) {
/* 139 */       return null;
/*     */     } finally {
/*     */       try {
/* 142 */         paramInputStream.close();
/*     */       }
/*     */       catch (IOException localIOException3) {
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private ImageFrame[] loadFrames() {
/* 150 */     return loadFrames(new ByteArrayInputStream(this.data, 0, this.dataSize));
/*     */   }
/*     */ 
/*     */   public void getImageSize(int[] paramArrayOfInt)
/*     */   {
/* 175 */     paramArrayOfInt[0] = this.imageWidth;
/* 176 */     paramArrayOfInt[1] = this.imageHeight;
/* 177 */     if (log.isLoggable(Level.FINE))
/* 178 */       log.fine(String.format("%s image size = %dx%d", new Object[] { this, Integer.valueOf(paramArrayOfInt[0]), Integer.valueOf(paramArrayOfInt[1]) }));
/*     */   }
/*     */ 
/*     */   private void setFrames(ImageFrame[] paramArrayOfImageFrame)
/*     */   {
/* 199 */     this.frames = paramArrayOfImageFrame;
/* 200 */     this.images = null;
/* 201 */     if (paramArrayOfImageFrame != null)
/* 202 */       notifyObserver();
/*     */   }
/*     */ 
/*     */   public int getFrameCount()
/*     */   {
/* 207 */     return this.frames != null ? this.frames.length : 0;
/*     */   }
/*     */ 
/*     */   public WCImageFrame getFrame(int paramInt, int[] paramArrayOfInt)
/*     */   {
/* 213 */     ImageFrame localImageFrame = getImageFrame(paramInt);
/* 214 */     if (localImageFrame != null) {
/* 215 */       if (log.isLoggable(Level.FINE)) {
/* 216 */         localObject = localImageFrame.getImageType();
/* 217 */         log.fine(String.format("%s getFrame(%d): image type = %s", new Object[] { this, Integer.valueOf(paramInt), localObject }));
/*     */       }
/*     */ 
/* 220 */       Object localObject = getPrismImage(paramInt, localImageFrame);
/*     */ 
/* 222 */       if (paramArrayOfInt != null) {
/* 223 */         ImageMetadata localImageMetadata = localImageFrame.getMetadata();
/* 224 */         int i = (localImageMetadata == null) || (localImageMetadata.delayTime == null) ? 0 : localImageMetadata.delayTime.intValue();
/*     */ 
/* 227 */         if (i < 11) i = 100;
/*     */ 
/* 229 */         paramArrayOfInt[0] = (paramInt < this.frames.length - 1 ? 1 : 0);
/* 230 */         paramArrayOfInt[1] = ((PrismImage)localObject).getWidth();
/* 231 */         paramArrayOfInt[2] = ((PrismImage)localObject).getHeight();
/* 232 */         paramArrayOfInt[3] = i;
/* 233 */         paramArrayOfInt[4] = 1;
/*     */ 
/* 235 */         if (log.isLoggable(Level.FINE)) {
/* 236 */           log.fine(String.format("%s getFrame(%d): complete=%d, size=%dx%d, duration=%d, hasAlpha=%d", new Object[] { this, Integer.valueOf(paramInt), Integer.valueOf(paramArrayOfInt[0]), Integer.valueOf(paramArrayOfInt[1]), Integer.valueOf(paramArrayOfInt[2]), Integer.valueOf(paramArrayOfInt[3]), Integer.valueOf(paramArrayOfInt[4]) }));
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 241 */       return new Frame((WCImage)localObject);
/*     */     }
/* 243 */     if (log.isLoggable(Level.FINE)) {
/* 244 */       log.fine(String.format("%s FAILED getFrame(%d)", new Object[] { this, Integer.valueOf(paramInt) }));
/*     */     }
/* 246 */     return null;
/*     */   }
/*     */ 
/*     */   private ImageFrame getImageFrame(int paramInt) {
/* 250 */     return (paramInt >= 0) && (this.frames != null) && (this.frames.length > paramInt) ? this.frames[paramInt] : null;
/*     */   }
/*     */ 
/*     */   private PrismImage getPrismImage(int paramInt, ImageFrame paramImageFrame)
/*     */   {
/* 256 */     if (this.images == null) {
/* 257 */       this.images = new PrismImage[this.frames.length];
/*     */     }
/* 259 */     if (this.images[paramInt] == null) {
/* 260 */       this.images[paramInt] = new WCImageImpl(paramImageFrame);
/*     */     }
/* 262 */     return this.images[paramInt];
/*     */   }
/*     */ 
/*     */   static class Frame extends WCImageFrame
/*     */   {
/*     */     WCImage image;
/*     */ 
/*     */     public Frame(WCImage paramWCImage)
/*     */     {
/* 186 */       this.image = paramWCImage;
/*     */     }
/*     */ 
/*     */     public WCImage getFrame() {
/* 190 */       return this.image;
/*     */     }
/*     */ 
/*     */     public void destroyDecodedData() {
/* 194 */       this.image = null;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.sg.prism.WCImgDecoderImpl
 * JD-Core Version:    0.6.2
 */