/*    */ package com.sun.javafx.iio.jpeg;
/*    */ 
/*    */ import com.sun.javafx.iio.ImageFormatDescription;
/*    */ import com.sun.javafx.iio.ImageLoader;
/*    */ import com.sun.javafx.iio.ImageLoaderFactory;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ 
/*    */ public class JPEGImageLoaderFactory
/*    */   implements ImageLoaderFactory
/*    */ {
/* 22 */   private static final JPEGImageLoaderFactory theInstance = new JPEGImageLoaderFactory();
/*    */ 
/*    */   public static final ImageLoaderFactory getInstance()
/*    */   {
/* 28 */     return theInstance;
/*    */   }
/*    */ 
/*    */   public ImageFormatDescription getFormatDescription() {
/* 32 */     return JPEGDescriptor.getInstance();
/*    */   }
/*    */ 
/*    */   public ImageLoader createImageLoader(InputStream paramInputStream) throws IOException {
/* 36 */     return new JPEGImageLoader(paramInputStream);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.iio.jpeg.JPEGImageLoaderFactory
 * JD-Core Version:    0.6.2
 */