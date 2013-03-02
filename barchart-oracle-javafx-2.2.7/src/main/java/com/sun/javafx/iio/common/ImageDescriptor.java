/*    */ package com.sun.javafx.iio.common;
/*    */ 
/*    */ import com.sun.javafx.iio.ImageFormatDescription;
/*    */ 
/*    */ public class ImageDescriptor
/*    */   implements ImageFormatDescription
/*    */ {
/*    */   private String formatName;
/*    */   private String[] extensions;
/*    */   private byte[][] signatures;
/*    */ 
/*    */   protected ImageDescriptor()
/*    */   {
/*    */   }
/*    */ 
/*    */   public ImageDescriptor(String paramString, String[] paramArrayOfString, byte[][] paramArrayOfByte)
/*    */   {
/* 26 */     this.formatName = paramString;
/* 27 */     this.extensions = paramArrayOfString;
/* 28 */     this.signatures = paramArrayOfByte;
/*    */   }
/*    */ 
/*    */   public String getFormatName()
/*    */   {
/* 33 */     return this.formatName;
/*    */   }
/*    */ 
/*    */   public String[] getExtensions() {
/* 37 */     return this.extensions;
/*    */   }
/*    */ 
/*    */   public byte[][] getSignatures() {
/* 41 */     return this.signatures;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.iio.common.ImageDescriptor
 * JD-Core Version:    0.6.2
 */