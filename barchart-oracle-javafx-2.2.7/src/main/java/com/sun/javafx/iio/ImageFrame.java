/*    */ package com.sun.javafx.iio;
/*    */ 
/*    */ import java.nio.Buffer;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ public class ImageFrame
/*    */ {
/*    */   private ImageStorage.ImageType imageType;
/*    */   private ByteBuffer imageData;
/*    */   private int width;
/*    */   private int height;
/*    */   private int stride;
/*    */   private byte[][] palette;
/*    */   private ImageMetadata metadata;
/*    */ 
/*    */   public ImageFrame(ImageStorage.ImageType paramImageType, ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, int paramInt3, byte[][] paramArrayOfByte, ImageMetadata paramImageMetadata)
/*    */   {
/* 46 */     this.imageType = paramImageType;
/* 47 */     this.imageData = paramByteBuffer;
/* 48 */     this.width = paramInt1;
/* 49 */     this.height = paramInt2;
/* 50 */     this.stride = paramInt3;
/* 51 */     this.palette = paramArrayOfByte;
/* 52 */     this.metadata = paramImageMetadata;
/*    */   }
/*    */ 
/*    */   public ImageStorage.ImageType getImageType() {
/* 56 */     return this.imageType;
/*    */   }
/*    */ 
/*    */   public Buffer getImageData() {
/* 60 */     return this.imageData;
/*    */   }
/*    */ 
/*    */   public int getWidth() {
/* 64 */     return this.width;
/*    */   }
/*    */ 
/*    */   public int getHeight() {
/* 68 */     return this.height;
/*    */   }
/*    */ 
/*    */   public int getStride() {
/* 72 */     return this.stride;
/*    */   }
/*    */ 
/*    */   public byte[][] getPalette() {
/* 76 */     return this.palette;
/*    */   }
/*    */ 
/*    */   public ImageMetadata getMetadata() {
/* 80 */     return this.metadata;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.iio.ImageFrame
 * JD-Core Version:    0.6.2
 */