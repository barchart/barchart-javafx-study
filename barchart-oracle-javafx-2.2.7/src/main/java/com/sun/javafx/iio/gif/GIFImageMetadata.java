/*    */ package com.sun.javafx.iio.gif;
/*    */ 
/*    */ import java.util.List;
/*    */ 
/*    */ class GIFImageMetadata
/*    */ {
/*    */   static final String nativeMetadataFormatName = "javax_imageio_gif_image_1.0";
/* 23 */   static final String[] disposalMethodNames = { "none", "doNotDispose", "restoreToBackgroundColor", "restoreToPrevious", "undefinedDisposalMethod4", "undefinedDisposalMethod5", "undefinedDisposalMethod6", "undefinedDisposalMethod7" };
/*    */   int imageLeftPosition;
/*    */   int imageTopPosition;
/*    */   int imageWidth;
/*    */   int imageHeight;
/* 39 */   boolean interlaceFlag = false;
/* 40 */   boolean sortFlag = false;
/* 41 */   byte[] localColorTable = null;
/*    */ 
/* 44 */   int disposalMethod = 0;
/* 45 */   boolean userInputFlag = false;
/* 46 */   boolean transparentColorFlag = false;
/* 47 */   int delayTime = 0;
/* 48 */   int transparentColorIndex = 0;
/*    */ 
/* 51 */   boolean hasPlainTextExtension = false;
/*    */   int textGridLeft;
/*    */   int textGridTop;
/*    */   int textGridWidth;
/*    */   int textGridHeight;
/*    */   int characterCellWidth;
/*    */   int characterCellHeight;
/*    */   int textForegroundColor;
/*    */   int textBackgroundColor;
/*    */   byte[] text;
/* 64 */   List<byte[]> applicationIDs = null;
/*    */ 
/* 67 */   List<byte[]> authenticationCodes = null;
/*    */ 
/* 70 */   List<byte[]> applicationData = null;
/*    */ 
/* 74 */   List<byte[]> comments = null;
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.iio.gif.GIFImageMetadata
 * JD-Core Version:    0.6.2
 */