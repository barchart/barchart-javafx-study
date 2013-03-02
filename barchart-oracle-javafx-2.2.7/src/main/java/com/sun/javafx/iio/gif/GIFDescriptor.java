/*    */ package com.sun.javafx.iio.gif;
/*    */ 
/*    */ import com.sun.javafx.iio.common.ImageDescriptor;
/*    */ 
/*    */ public class GIFDescriptor extends ImageDescriptor
/*    */ {
/*    */   private static final String formatName = "GIF";
/* 20 */   private static final String[] extensions = { "gif" };
/*    */ 
/* 22 */   private static final byte[][] signatures = { { 71, 73, 70, 56, 55, 97 }, { 71, 73, 70, 56, 57, 97 } };
/*    */ 
/* 29 */   private static ImageDescriptor theInstance = null;
/*    */ 
/*    */   private GIFDescriptor() {
/* 32 */     super("GIF", extensions, signatures);
/*    */   }
/*    */ 
/*    */   public static final synchronized ImageDescriptor getInstance() {
/* 36 */     if (theInstance == null) {
/* 37 */       theInstance = new GIFDescriptor();
/*    */     }
/* 39 */     return theInstance;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.iio.gif.GIFDescriptor
 * JD-Core Version:    0.6.2
 */