/*    */ package com.sun.javafx.iio.jpeg;
/*    */ 
/*    */ import com.sun.javafx.iio.common.ImageDescriptor;
/*    */ 
/*    */ public class JPEGDescriptor extends ImageDescriptor
/*    */ {
/*    */   public static final int SOI = 216;
/*    */   private static final String formatName = "JPEG";
/* 22 */   private static final String[] extensions = { "jpg", "jpeg" };
/*    */ 
/* 25 */   private static final byte[][] signatures = { { -1, -40 } };
/*    */ 
/* 29 */   private static ImageDescriptor theInstance = null;
/*    */ 
/*    */   private JPEGDescriptor() {
/* 32 */     super("JPEG", extensions, signatures);
/*    */   }
/*    */ 
/*    */   public static final synchronized ImageDescriptor getInstance() {
/* 36 */     if (theInstance == null) {
/* 37 */       theInstance = new JPEGDescriptor();
/*    */     }
/* 39 */     return theInstance;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.iio.jpeg.JPEGDescriptor
 * JD-Core Version:    0.6.2
 */