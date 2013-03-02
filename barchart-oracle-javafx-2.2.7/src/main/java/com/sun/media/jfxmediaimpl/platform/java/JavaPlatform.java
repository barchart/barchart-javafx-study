/*    */ package com.sun.media.jfxmediaimpl.platform.java;
/*    */ 
/*    */ import com.sun.media.jfxmedia.Media;
/*    */ import com.sun.media.jfxmedia.MediaPlayer;
/*    */ import com.sun.media.jfxmedia.MetadataParser;
/*    */ import com.sun.media.jfxmedia.locator.Locator;
/*    */ import com.sun.media.jfxmediaimpl.platform.Platform;
/*    */ 
/*    */ public class JavaPlatform extends Platform
/*    */ {
/* 18 */   private static JavaPlatform globalInstance = null;
/*    */ 
/*    */   public static synchronized Platform getPlatformInstance() {
/* 21 */     if (null == globalInstance) {
/* 22 */       globalInstance = new JavaPlatform();
/*    */     }
/*    */ 
/* 25 */     return globalInstance;
/*    */   }
/*    */ 
/*    */   public boolean loadPlatform()
/*    */   {
/* 31 */     return true;
/*    */   }
/*    */ 
/*    */   public MetadataParser createMetadataParser(Locator source)
/*    */   {
/* 37 */     String contentType = source.getContentType();
/* 38 */     if ((contentType.equals("video/x-javafx")) || (contentType.equals("video/x-flv")))
/* 39 */       return new FLVMetadataParser(source);
/* 40 */     if ((contentType.equals("audio/mpeg")) || (contentType.equals("audio/mp3")))
/*    */     {
/* 42 */       return new ID3MetadataParser(source);
/*    */     }
/*    */ 
/* 45 */     return null;
/*    */   }
/*    */ 
/*    */   public Media createMedia(Locator source)
/*    */   {
/* 50 */     return null;
/*    */   }
/*    */ 
/*    */   public Object prerollMediaPlayer(Locator source)
/*    */   {
/* 55 */     return null;
/*    */   }
/*    */ 
/*    */   public MediaPlayer createMediaPlayer(Locator source, Object cookie)
/*    */   {
/* 60 */     return null;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.media.jfxmediaimpl.platform.java.JavaPlatform
 * JD-Core Version:    0.6.2
 */