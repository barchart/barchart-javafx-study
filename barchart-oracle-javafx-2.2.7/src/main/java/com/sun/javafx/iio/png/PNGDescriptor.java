/*    */ package com.sun.javafx.iio.png;
/*    */ 
/*    */ import com.sun.javafx.iio.common.ImageDescriptor;
/*    */ 
/*    */ public class PNGDescriptor extends ImageDescriptor
/*    */ {
/*    */   private static final String formatName = "PNG";
/* 20 */   private static final String[] extensions = { "png" };
/*    */ 
/* 22 */   private static final byte[][] signatures = { { -119, 80, 78, 71, 13, 10, 26, 10 } };
/*    */ 
/* 29 */   private static ImageDescriptor theInstance = null;
/*    */ 
/*    */   private PNGDescriptor() {
/* 32 */     super("PNG", extensions, signatures);
/*    */   }
/*    */ 
/*    */   public static final synchronized ImageDescriptor getInstance() {
/* 36 */     if (theInstance == null) {
/* 37 */       theInstance = new PNGDescriptor();
/*    */     }
/* 39 */     return theInstance;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.iio.png.PNGDescriptor
 * JD-Core Version:    0.6.2
 */