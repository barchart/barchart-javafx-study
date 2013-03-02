/*     */ package com.sun.javafx.tk.quantum;
/*     */ 
/*     */ import com.sun.glass.ui.Application;
/*     */ import com.sun.glass.ui.Pixels;
/*     */ import com.sun.javafx.iio.ImageFormatDescription;
/*     */ import com.sun.javafx.iio.ImageStorage;
/*     */ import com.sun.javafx.image.ByteToBytePixelConverter;
/*     */ import com.sun.javafx.image.impl.ByteRgb;
/*     */ import com.sun.prism.Image;
/*     */ import com.sun.prism.PixelFormat;
/*     */ import com.sun.prism.PixelFormat.DataType;
/*     */ import java.nio.Buffer;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.IntBuffer;
/*     */ 
/*     */ public class PixelUtils
/*     */ {
/*  27 */   private static ImageFormatDescription[] supportedFormats = ImageStorage.getSupportedDescriptions();
/*     */ 
/*     */   protected static boolean supportedFormatType(String paramString)
/*     */   {
/*  31 */     for (ImageFormatDescription localImageFormatDescription : supportedFormats) {
/*  32 */       for (String str : localImageFormatDescription.getExtensions()) {
/*  33 */         if (paramString.endsWith(str)) {
/*  34 */           return true;
/*     */         }
/*     */       }
/*     */     }
/*  38 */     return false;
/*     */   }
/*     */ 
/*     */   public static Pixels imageToPixels(Image paramImage) {
/*  42 */     PixelFormat.DataType localDataType = paramImage.getDataType();
/*  43 */     Application localApplication = Application.GetApplication();
/*  44 */     int i = Pixels.getNativeFormat();
/*     */     Object localObject;
/*     */     Pixels localPixels;
/*  47 */     if (localDataType == PixelFormat.DataType.BYTE) {
/*  48 */       localObject = (ByteBuffer)paramImage.getPixelBuffer();
/*  49 */       int j = paramImage.getWidth();
/*  50 */       int k = paramImage.getHeight();
/*  51 */       int m = paramImage.getScanlineStride();
/*     */ 
/*  53 */       if (paramImage.getBytesPerPixelUnit() == 3)
/*     */       {
/*     */         byte[] arrayOfByte;
/*  54 */         switch (i)
/*     */         {
/*     */         case 2:
/*  57 */           arrayOfByte = new byte[j * k * 4];
/*  58 */           ByteRgb.ToByteArgbConverter.convert((ByteBuffer)localObject, 0, m, arrayOfByte, 0, j * 4, j, k);
/*     */ 
/*  61 */           localObject = ByteBuffer.wrap(arrayOfByte);
/*  62 */           break;
/*     */         case 1:
/*  66 */           arrayOfByte = new byte[j * k * 4];
/*  67 */           ByteRgb.ToByteBgraPreConverter.convert((ByteBuffer)localObject, 0, m, arrayOfByte, 0, j * 4, j, k);
/*     */ 
/*  70 */           localObject = ByteBuffer.wrap(arrayOfByte);
/*  71 */           break;
/*     */         default:
/*  74 */           throw new IllegalArgumentException("unhandled native format: " + i);
/*     */         }
/*  76 */       } else if (paramImage.getPixelFormat() != PixelFormat.BYTE_BGRA_PRE) {
/*  77 */         throw new IllegalArgumentException("non-RGB image format");
/*     */       }
/*  79 */       localPixels = localApplication.createPixels(paramImage.getWidth(), paramImage.getHeight(), (ByteBuffer)localObject);
/*     */ 
/*  81 */       return localPixels;
/*  82 */     }if (localDataType == PixelFormat.DataType.INT) {
/*  83 */       if (ByteOrder.nativeOrder() != ByteOrder.LITTLE_ENDIAN)
/*     */       {
/*  85 */         throw new UnsupportedOperationException("INT_ARGB_PRE only supported for LITTLE_ENDIAN machines");
/*     */       }
/*     */ 
/*  91 */       localObject = (IntBuffer)paramImage.getPixelBuffer();
/*  92 */       localPixels = localApplication.createPixels(paramImage.getWidth(), paramImage.getHeight(), (IntBuffer)localObject);
/*     */ 
/*  94 */       return localPixels;
/*     */     }
/*  96 */     throw new IllegalArgumentException("unhandled image type: " + localDataType);
/*     */   }
/*     */ 
/*     */   public static Image pixelsToImage(Pixels paramPixels)
/*     */   {
/* 101 */     Buffer localBuffer = paramPixels.getPixels();
/*     */     Object localObject;
/* 102 */     if (paramPixels.getBytesPerComponent() == 1) {
/* 103 */       localObject = ByteBuffer.allocateDirect(localBuffer.capacity());
/* 104 */       ((ByteBuffer)localObject).put((ByteBuffer)localBuffer);
/* 105 */       ((ByteBuffer)localObject).rewind();
/* 106 */       return Image.fromByteBgraPreData((ByteBuffer)localObject, paramPixels.getWidth(), paramPixels.getHeight());
/* 107 */     }if (paramPixels.getBytesPerComponent() == 4) {
/* 108 */       localObject = IntBuffer.allocate(localBuffer.capacity());
/* 109 */       ((IntBuffer)localObject).put((IntBuffer)localBuffer);
/* 110 */       ((IntBuffer)localObject).rewind();
/* 111 */       return Image.fromIntArgbPreData((IntBuffer)localBuffer, paramPixels.getWidth(), paramPixels.getHeight());
/*     */     }
/*     */ 
/* 114 */     throw new IllegalArgumentException("unhandled pixel buffer: " + localBuffer.getClass().getName());
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.quantum.PixelUtils
 * JD-Core Version:    0.6.2
 */