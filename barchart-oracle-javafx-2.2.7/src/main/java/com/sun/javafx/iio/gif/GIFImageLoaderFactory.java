/*    */ package com.sun.javafx.iio.gif;
/*    */ 
/*    */ import com.sun.javafx.iio.ImageFormatDescription;
/*    */ import com.sun.javafx.iio.ImageLoader;
/*    */ import com.sun.javafx.iio.ImageLoaderFactory;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.security.AccessController;
/*    */ import java.security.PrivilegedAction;
/*    */ 
/*    */ public class GIFImageLoaderFactory
/*    */   implements ImageLoaderFactory
/*    */ {
/*    */   private static final String selectorPropertyName = "prism.useOldGIF";
/* 26 */   private static boolean useOldGIF = ((Boolean)AccessController.doPrivileged(new PrivilegedAction()
/*    */   {
/*    */     public Boolean run() {
/* 29 */       return Boolean.valueOf(Boolean.getBoolean("prism.useOldGIF"));
/*    */     }
/*    */   })).booleanValue();
/*    */ 
/* 33 */   private static final GIFImageLoaderFactory theInstance = new GIFImageLoaderFactory();
/*    */ 
/*    */   public static final ImageLoaderFactory getInstance()
/*    */   {
/* 39 */     return theInstance;
/*    */   }
/*    */ 
/*    */   public ImageFormatDescription getFormatDescription() {
/* 43 */     return GIFDescriptor.getInstance();
/*    */   }
/*    */ 
/*    */   public ImageLoader createImageLoader(InputStream paramInputStream) throws IOException {
/* 47 */     return useOldGIF ? new GIFImageLoader(paramInputStream) : new GIFImageLoader2(paramInputStream);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.iio.gif.GIFImageLoaderFactory
 * JD-Core Version:    0.6.2
 */