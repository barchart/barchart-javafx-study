/*    */ package com.sun.webpane.platform;
/*    */ 
/*    */ import java.io.File;
/*    */ 
/*    */ final class FileSystem
/*    */ {
/*    */   private FileSystem()
/*    */   {
/* 17 */     throw new AssertionError();
/*    */   }
/*    */ 
/*    */   private static String fwkPathGetFileName(String paramString)
/*    */   {
/* 25 */     return new File(paramString).getName();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.platform.FileSystem
 * JD-Core Version:    0.6.2
 */