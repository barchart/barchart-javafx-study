/*    */ package com.sun.javafx.iio.gif;
/*    */ 
/*    */ class GIFStreamMetadata
/*    */ {
/* 20 */   static final String[] versionStrings = { "87a", "89a" };
/*    */   String version;
/*    */   int logicalScreenWidth;
/*    */   int logicalScreenHeight;
/*    */   int colorResolution;
/*    */   int pixelAspectRatio;
/*    */   int backgroundColorIndex;
/*    */   boolean sortFlag;
/* 31 */   static final String[] colorTableSizes = { "2", "4", "8", "16", "32", "64", "128", "256" };
/*    */ 
/* 36 */   byte[] globalColorTable = null;
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.iio.gif.GIFStreamMetadata
 * JD-Core Version:    0.6.2
 */