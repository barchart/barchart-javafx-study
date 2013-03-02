/*    */ package com.sun.javafx.iio.bmp;
/*    */ 
/*    */ import com.sun.javafx.iio.common.ImageDescriptor;
/*    */ 
/*    */ final class BMPDescriptor extends ImageDescriptor
/*    */ {
/*    */   static final String formatName = "BMP";
/* 17 */   static final String[] extensions = { "bmp" };
/* 18 */   static final byte[][] signatures = { { 66, 77 } };
/* 19 */   static final ImageDescriptor theInstance = new BMPDescriptor();
/*    */ 
/*    */   private BMPDescriptor() {
/* 22 */     super("BMP", extensions, signatures);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.iio.bmp.BMPDescriptor
 * JD-Core Version:    0.6.2
 */