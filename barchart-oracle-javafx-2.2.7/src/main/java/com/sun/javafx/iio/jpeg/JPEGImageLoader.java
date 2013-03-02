/*     */ package com.sun.javafx.iio.jpeg;
/*     */ 
/*     */ import com.sun.javafx.iio.ImageFrame;
/*     */ import com.sun.javafx.iio.ImageMetadata;
/*     */ import com.sun.javafx.iio.ImageStorage.ImageType;
/*     */ import com.sun.javafx.iio.common.ImageLoaderImpl;
/*     */ import com.sun.javafx.iio.common.ImageTools;
/*     */ import com.sun.javafx.iio.common.PushbroomScaler;
/*     */ import com.sun.javafx.iio.common.ScalerFactory;
/*     */ import com.sun.javafx.runtime.NativeLibLoader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.HashSet;
/*     */ 
/*     */ public class JPEGImageLoader extends ImageLoaderImpl
/*     */ {
/*     */   public static final int JCS_UNKNOWN = 0;
/*     */   public static final int JCS_GRAYSCALE = 1;
/*     */   public static final int JCS_RGB = 2;
/*     */   public static final int JCS_YCbCr = 3;
/*     */   public static final int JCS_CMYK = 4;
/*     */   public static final int JCS_YCC = 5;
/*     */   public static final int JCS_RGBA = 6;
/*     */   public static final int JCS_YCbCrA = 7;
/*     */   public static final int JCS_YCCA = 10;
/*     */   public static final int JCS_YCCK = 11;
/*  49 */   private long structPointer = 0L;
/*     */   private int inWidth;
/*     */   private int inHeight;
/*     */   private int inColorSpaceCode;
/*     */   private int outColorSpaceCode;
/*     */   private int inNumComponents;
/*     */   private byte[] iccData;
/*     */   private int outWidth;
/*     */   private int outHeight;
/*     */   private ImageStorage.ImageType outImageType;
/*  74 */   private boolean isDisposed = false;
/*     */ 
/*     */   private static native void initJPEGMethodIDs(Class paramClass);
/*     */ 
/*     */   private static native void disposeNative(long paramLong);
/*     */ 
/*     */   private native long initDecompressor(InputStream paramInputStream)
/*     */     throws IOException;
/*     */ 
/*     */   private native void startDecompression(long paramLong, int paramInt1, int paramInt2, int paramInt3);
/*     */ 
/*     */   private native boolean decompressIndirect(long paramLong, boolean paramBoolean, byte[] paramArrayOfByte)
/*     */     throws IOException;
/*     */ 
/*     */   private void setInputAttributes(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, byte[] paramArrayOfByte)
/*     */   {
/* 112 */     this.inWidth = paramInt1;
/* 113 */     this.inHeight = paramInt2;
/* 114 */     this.inColorSpaceCode = paramInt3;
/* 115 */     this.outColorSpaceCode = paramInt4;
/* 116 */     this.inNumComponents = paramInt5;
/* 117 */     this.iccData = paramArrayOfByte;
/*     */ 
/* 120 */     switch (paramInt4) {
/*     */     case 1:
/* 122 */       this.outImageType = ImageStorage.ImageType.GRAY;
/* 123 */       break;
/*     */     case 2:
/*     */     case 3:
/*     */     case 5:
/* 127 */       this.outImageType = ImageStorage.ImageType.RGB;
/* 128 */       break;
/*     */     case 4:
/*     */     case 6:
/*     */     case 7:
/*     */     case 10:
/*     */     case 11:
/* 134 */       this.outImageType = ImageStorage.ImageType.RGBA_PRE;
/* 135 */       break;
/*     */     case 0:
/* 137 */       switch (paramInt5) {
/*     */       case 1:
/* 139 */         this.outImageType = ImageStorage.ImageType.GRAY;
/* 140 */         break;
/*     */       case 3:
/* 142 */         this.outImageType = ImageStorage.ImageType.RGB;
/* 143 */         break;
/*     */       case 4:
/* 145 */         this.outImageType = ImageStorage.ImageType.RGBA_PRE;
/* 146 */         break;
/*     */       case 2:
/*     */       default:
/* 148 */         if (!$assertionsDisabled) throw new AssertionError(); break; } break;
/*     */     case 8:
/*     */     case 9:
/*     */     default:
/* 152 */       if (!$assertionsDisabled) throw new AssertionError();
/*     */       break;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void setOutputAttributes(int paramInt1, int paramInt2)
/*     */   {
/* 161 */     this.outWidth = paramInt1;
/* 162 */     this.outHeight = paramInt2;
/*     */   }
/*     */ 
/*     */   private void updateImageProgress(int paramInt) {
/* 166 */     updateImageProgress(100.0F * paramInt / this.outHeight);
/*     */   }
/*     */ 
/*     */   JPEGImageLoader(InputStream paramInputStream) throws IOException {
/* 170 */     super(JPEGDescriptor.getInstance());
/* 171 */     if (paramInputStream == null) {
/* 172 */       throw new IllegalArgumentException("input == null!");
/*     */     }
/*     */     try
/*     */     {
/* 176 */       this.structPointer = initDecompressor(paramInputStream);
/*     */     } catch (IOException localIOException) {
/* 178 */       dispose();
/* 179 */       throw localIOException;
/*     */     }
/*     */ 
/* 182 */     if (this.structPointer == 0L)
/* 183 */       throw new IOException("Unable to initialize JPEG decompressor");
/*     */   }
/*     */ 
/*     */   public synchronized void dispose()
/*     */   {
/* 188 */     if ((!this.isDisposed) && (this.structPointer != 0L)) {
/* 189 */       this.isDisposed = true;
/* 190 */       disposeNative(this.structPointer);
/* 191 */       this.structPointer = 0L;
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void finalize() {
/* 196 */     dispose();
/*     */   }
/*     */ 
/*     */   public ImageFrame load(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean1, boolean paramBoolean2) throws IOException {
/* 200 */     if (paramInt1 != 0) {
/* 201 */       return null;
/*     */     }
/*     */ 
/* 205 */     int[] arrayOfInt = ImageTools.computeDimensions(this.inWidth, this.inHeight, paramInt2, paramInt3, paramBoolean1);
/* 206 */     paramInt2 = arrayOfInt[0];
/* 207 */     paramInt3 = arrayOfInt[1];
/*     */ 
/* 209 */     ImageMetadata localImageMetadata = new ImageMetadata(null, Boolean.valueOf(true), null, null, null, null, Integer.valueOf(this.inWidth), Integer.valueOf(this.inHeight), null, null, null);
/*     */ 
/* 213 */     updateImageMetadata(localImageMetadata);
/*     */ 
/* 215 */     ByteBuffer localByteBuffer = null;
/*     */     try {
/* 217 */       startDecompression(this.structPointer, this.outColorSpaceCode, paramInt2, paramInt3);
/*     */ 
/* 221 */       byte[] arrayOfByte1 = new byte[this.outWidth * this.outHeight * this.inNumComponents];
/* 222 */       localByteBuffer = ByteBuffer.wrap(arrayOfByte1);
/* 223 */       decompressIndirect(this.structPointer, (this.listeners != null) && (!this.listeners.isEmpty()), localByteBuffer.array());
/*     */     } catch (IOException localIOException) {
/* 225 */       throw localIOException;
/*     */     } finally {
/* 227 */       dispose();
/*     */     }
/*     */ 
/* 230 */     if (localByteBuffer == null) {
/* 231 */       throw new IOException("Error decompressing JPEG stream!");
/*     */     }
/*     */ 
/* 234 */     ImageFrame localImageFrame = null;
/*     */ 
/* 241 */     if ((this.outWidth != paramInt2) || (this.outHeight != paramInt3))
/*     */     {
/*     */       byte[] arrayOfByte2;
/* 246 */       if (localByteBuffer.hasArray()) {
/* 247 */         arrayOfByte2 = localByteBuffer.array();
/*     */       } else {
/* 249 */         arrayOfByte2 = new byte[localByteBuffer.capacity()];
/* 250 */         localByteBuffer.get(arrayOfByte2);
/*     */       }
/*     */ 
/* 253 */       PushbroomScaler localPushbroomScaler = ScalerFactory.createScaler(this.outWidth, this.outHeight, this.inNumComponents, paramInt2, paramInt3, paramBoolean2);
/*     */ 
/* 255 */       int i = this.outWidth * this.inNumComponents;
/* 256 */       int j = 0;
/* 257 */       for (int k = 0; (k < this.outHeight) && 
/* 258 */         (!localPushbroomScaler.putSourceScanline(arrayOfByte2, j)); k++)
/*     */       {
/* 261 */         j += i;
/*     */       }
/* 263 */       localByteBuffer = localPushbroomScaler.getDestination();
/* 264 */       localImageFrame = new ImageFrame(this.outImageType, localByteBuffer, paramInt2, paramInt3, paramInt2 * this.inNumComponents, (byte[][])null, localImageMetadata);
/*     */     }
/*     */     else
/*     */     {
/* 268 */       localImageFrame = new ImageFrame(this.outImageType, localByteBuffer, this.outWidth, this.outHeight, this.outWidth * this.inNumComponents, (byte[][])null, localImageMetadata);
/*     */     }
/*     */ 
/* 272 */     return localImageFrame;
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  93 */     AccessController.doPrivileged(new PrivilegedAction()
/*     */     {
/*     */       public Object run() {
/*  96 */         NativeLibLoader.loadLibrary("javafx-iio");
/*  97 */         return null;
/*     */       }
/*     */     });
/* 100 */     initJPEGMethodIDs(InputStream.class);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.iio.jpeg.JPEGImageLoader
 * JD-Core Version:    0.6.2
 */