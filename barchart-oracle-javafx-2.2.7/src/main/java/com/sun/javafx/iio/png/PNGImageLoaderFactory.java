/*    */ package com.sun.javafx.iio.png;
/*    */ 
/*    */ import com.sun.javafx.iio.ImageFormatDescription;
/*    */ import com.sun.javafx.iio.ImageLoader;
/*    */ import com.sun.javafx.iio.ImageLoaderFactory;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.security.AccessController;
/*    */ import java.security.PrivilegedAction;
/*    */ 
/*    */ public class PNGImageLoaderFactory
/*    */   implements ImageLoaderFactory
/*    */ {
/*    */   private static final String selectorPropertyName = "prism.useOldPNG";
/* 25 */   private static boolean useOldPngLoader = ((Boolean)AccessController.doPrivileged(new PrivilegedAction()
/*    */   {
/*    */     public Boolean run() {
/* 28 */       return Boolean.valueOf(Boolean.getBoolean("prism.useOldPNG"));
/*    */     }
/*    */   })).booleanValue();
/*    */ 
/* 32 */   private static final PNGImageLoaderFactory theInstance = new PNGImageLoaderFactory();
/*    */ 
/*    */   public static final ImageLoaderFactory getInstance()
/*    */   {
/* 38 */     return theInstance;
/*    */   }
/*    */ 
/*    */   public ImageFormatDescription getFormatDescription() {
/* 42 */     return PNGDescriptor.getInstance();
/*    */   }
/*    */ 
/*    */   public ImageLoader createImageLoader(InputStream paramInputStream) throws IOException {
/* 46 */     return useOldPngLoader ? new PNGImageLoader(paramInputStream) : new PNGImageLoader2(paramInputStream);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.iio.png.PNGImageLoaderFactory
 * JD-Core Version:    0.6.2
 */