/*     */ package com.sun.prism;
/*     */ 
/*     */ import java.awt.AlphaComposite;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.DataBuffer;
/*     */ import java.awt.image.DataBufferByte;
/*     */ import java.awt.image.DataBufferInt;
/*     */ import java.awt.image.Raster;
/*     */ import java.awt.image.WritableRaster;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.ByteOrder;
/*     */ import java.nio.IntBuffer;
/*     */ 
/*     */ public class BufferedImageTools
/*     */ {
/*     */   public static Image load_INT_ARGB_Raster(Raster paramRaster, boolean paramBoolean)
/*     */   {
/*  18 */     assert (paramRaster.getDataBuffer().getDataType() == 3);
/*  19 */     DataBufferInt localDataBufferInt = (DataBufferInt)paramRaster.getDataBuffer();
/*  20 */     int[] arrayOfInt = paramBoolean ? (int[])localDataBufferInt.getData().clone() : localDataBufferInt.getData();
/*  21 */     return Image.fromIntArgbPreData(arrayOfInt, paramRaster.getWidth(), paramRaster.getHeight());
/*     */   }
/*     */ 
/*     */   public static Image load_BYTE_LUMINANCE_Raster(Raster paramRaster, boolean paramBoolean) {
/*  25 */     assert (paramRaster.getDataBuffer().getDataType() == 0);
/*  26 */     DataBufferByte localDataBufferByte = (DataBufferByte)paramRaster.getDataBuffer();
/*  27 */     byte[] arrayOfByte = paramBoolean ? (byte[])localDataBufferByte.getData().clone() : localDataBufferByte.getData();
/*  28 */     return Image.fromByteGrayData(arrayOfByte, paramRaster.getWidth(), paramRaster.getHeight());
/*     */   }
/*     */ 
/*     */   public static BufferedImage convertFormat(BufferedImage paramBufferedImage, int paramInt) {
/*  32 */     BufferedImage localBufferedImage = new BufferedImage(paramBufferedImage.getWidth(), paramBufferedImage.getHeight(), paramInt);
/*     */ 
/*  34 */     Graphics2D localGraphics2D = localBufferedImage.createGraphics();
/*  35 */     localGraphics2D.setComposite(AlphaComposite.Src);
/*  36 */     localGraphics2D.drawImage(paramBufferedImage, 0, 0, null);
/*  37 */     localGraphics2D.dispose();
/*  38 */     return localBufferedImage;
/*     */   }
/*     */ 
/*     */   public static Image importBufferedImage(Object paramObject) {
/*  42 */     assert ((paramObject instanceof BufferedImage));
/*  43 */     BufferedImage localBufferedImage1 = (BufferedImage)paramObject;
/*     */ 
/*  45 */     switch (localBufferedImage1.getType()) {
/*     */     case 2:
/*     */     case 3:
/*  48 */       return load_INT_ARGB_Raster(localBufferedImage1.getRaster(), true);
/*     */     case 10:
/*  50 */       return load_BYTE_LUMINANCE_Raster(localBufferedImage1.getRaster(), true);
/*     */     }
/*  52 */     BufferedImage localBufferedImage2 = convertFormat(localBufferedImage1, 2);
/*  53 */     return load_INT_ARGB_Raster(localBufferedImage2.getRaster(), false);
/*     */   }
/*     */ 
/*     */   private static BufferedImage export_INT_ARGB_PRE(Image paramImage)
/*     */   {
/*  59 */     BufferedImage localBufferedImage = new BufferedImage(paramImage.getWidth(), paramImage.getHeight(), 2);
/*     */ 
/*  62 */     assert (localBufferedImage.getRaster().getDataBuffer().getDataType() == 3);
/*  63 */     DataBufferInt localDataBufferInt = (DataBufferInt)localBufferedImage.getRaster().getDataBuffer();
/*     */ 
/*  65 */     IntBuffer localIntBuffer = (IntBuffer)paramImage.getPixelBuffer();
/*  66 */     int i = localIntBuffer.position();
/*  67 */     localIntBuffer.rewind();
/*  68 */     localIntBuffer.get(localDataBufferInt.getData());
/*  69 */     localIntBuffer.position(i);
/*  70 */     return localBufferedImage;
/*     */   }
/*     */ 
/*     */   private static BufferedImage export_BYTE_GRAY(Image paramImage) {
/*  74 */     BufferedImage localBufferedImage = new BufferedImage(paramImage.getWidth(), paramImage.getHeight(), 10);
/*     */ 
/*  77 */     assert (localBufferedImage.getRaster().getDataBuffer().getDataType() == 0);
/*  78 */     DataBufferByte localDataBufferByte = (DataBufferByte)localBufferedImage.getRaster().getDataBuffer();
/*     */ 
/*  80 */     ByteBuffer localByteBuffer = (ByteBuffer)paramImage.getPixelBuffer();
/*  81 */     int i = localByteBuffer.position();
/*  82 */     localByteBuffer.rewind();
/*  83 */     localByteBuffer.get(localDataBufferByte.getData());
/*  84 */     localByteBuffer.position(i);
/*  85 */     return localBufferedImage;
/*     */   }
/*     */ 
/*     */   private static BufferedImage export_BYTE_BGRA_PRE(Image paramImage) {
/*  89 */     BufferedImage localBufferedImage = new BufferedImage(paramImage.getWidth(), paramImage.getHeight(), 2);
/*     */ 
/*  92 */     assert (localBufferedImage.getRaster().getDataBuffer().getDataType() == 3);
/*  93 */     DataBufferInt localDataBufferInt = (DataBufferInt)localBufferedImage.getRaster().getDataBuffer();
/*     */ 
/*  95 */     ByteBuffer localByteBuffer = (ByteBuffer)paramImage.getPixelBuffer();
/*  96 */     int i = localByteBuffer.position();
/*  97 */     ByteOrder localByteOrder = localByteBuffer.order();
/*     */ 
/*  99 */     localByteBuffer.rewind();
/* 100 */     localByteBuffer.order(ByteOrder.nativeOrder());
/* 101 */     localByteBuffer.asIntBuffer().get(localDataBufferInt.getData());
/* 102 */     localByteBuffer.position(i);
/* 103 */     localByteBuffer.order(localByteOrder);
/* 104 */     return localBufferedImage;
/*     */   }
/*     */ 
/*     */   private static BufferedImage export_BYTE_RGB(Image paramImage) {
/* 108 */     BufferedImage localBufferedImage = new BufferedImage(paramImage.getWidth(), paramImage.getHeight(), 2);
/*     */ 
/* 111 */     assert (localBufferedImage.getRaster().getDataBuffer().getDataType() == 3);
/* 112 */     DataBufferInt localDataBufferInt = (DataBufferInt)localBufferedImage.getRaster().getDataBuffer();
/* 113 */     int[] arrayOfInt = localDataBufferInt.getData();
/*     */ 
/* 115 */     ByteBuffer localByteBuffer = (ByteBuffer)paramImage.getPixelBuffer();
/*     */ 
/* 117 */     for (int i = 0; i != paramImage.getHeight(); i++) {
/* 118 */       int j = paramImage.getScanlineStride() * i; int k = paramImage.getWidth() * i;
/* 119 */       for (int m = 0; m != paramImage.getWidth(); m++) {
/* 120 */         int n = localByteBuffer.get(j);
/* 121 */         int i1 = localByteBuffer.get(j + 1);
/* 122 */         int i2 = localByteBuffer.get(j + 2);
/* 123 */         arrayOfInt[(k + m)] = (-16777216 + n * 65536 + i1 * 256 + i2);
/*     */ 
/* 119 */         j += 3;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 127 */     return localBufferedImage;
/*     */   }
/*     */ 
/*     */   public static BufferedImage exportBufferedImage(Image paramImage, Object paramObject)
/*     */   {
/* 132 */     BufferedImage localBufferedImage = null;
/*     */ 
/* 134 */     if (paramImage.getPixelFormat() == PixelFormat.INT_ARGB_PRE)
/* 135 */       localBufferedImage = export_INT_ARGB_PRE(paramImage);
/* 136 */     else if (paramImage.getPixelFormat() == PixelFormat.BYTE_BGRA_PRE)
/* 137 */       localBufferedImage = export_BYTE_BGRA_PRE(paramImage);
/* 138 */     else if (paramImage.getPixelFormat() == PixelFormat.BYTE_RGB)
/* 139 */       localBufferedImage = export_BYTE_RGB(paramImage);
/* 140 */     else if (paramImage.getPixelFormat() == PixelFormat.BYTE_GRAY) {
/* 141 */       localBufferedImage = export_BYTE_GRAY(paramImage);
/*     */     }
/*     */ 
/* 144 */     if ((localBufferedImage != null) && (paramObject != null) && ((paramObject instanceof BufferedImage))) {
/* 145 */       int i = ((BufferedImage)paramObject).getType();
/* 146 */       if (i != localBufferedImage.getType()) {
/* 147 */         return convertFormat(localBufferedImage, i);
/*     */       }
/*     */     }
/*     */ 
/* 151 */     return localBufferedImage;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.BufferedImageTools
 * JD-Core Version:    0.6.2
 */