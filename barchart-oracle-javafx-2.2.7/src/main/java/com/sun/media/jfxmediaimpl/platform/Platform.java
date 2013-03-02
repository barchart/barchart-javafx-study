/*    */ package com.sun.media.jfxmediaimpl.platform;
/*    */ 
/*    */ import com.sun.media.jfxmedia.Media;
/*    */ import com.sun.media.jfxmedia.MediaPlayer;
/*    */ import com.sun.media.jfxmedia.MetadataParser;
/*    */ import com.sun.media.jfxmedia.locator.Locator;
/*    */ 
/*    */ public abstract class Platform
/*    */ {
/*    */   public static Platform getPlatformInstance()
/*    */   {
/* 19 */     throw new UnsupportedOperationException("Invalid platform class.");
/*    */   }
/*    */ 
/*    */   public void preloadPlatform()
/*    */   {
/*    */   }
/*    */ 
/*    */   public boolean loadPlatform()
/*    */   {
/* 35 */     return false;
/*    */   }
/*    */ 
/*    */   public boolean canPlayContentType(String contentType) {
/* 39 */     String[] contentTypes = getSupportedContentTypes();
/* 40 */     if (contentTypes != null) {
/* 41 */       for (String type : contentTypes) {
/* 42 */         if (type.equalsIgnoreCase(contentType)) {
/* 43 */           return true;
/*    */         }
/*    */       }
/*    */     }
/* 47 */     return false;
/*    */   }
/*    */ 
/*    */   public String[] getSupportedContentTypes()
/*    */   {
/* 54 */     return null;
/*    */   }
/*    */ 
/*    */   public MetadataParser createMetadataParser(Locator source)
/*    */   {
/* 59 */     return null;
/*    */   }
/*    */ 
/*    */   public abstract Media createMedia(Locator paramLocator);
/*    */ 
/*    */   public abstract Object prerollMediaPlayer(Locator paramLocator);
/*    */ 
/*    */   public abstract MediaPlayer createMediaPlayer(Locator paramLocator, Object paramObject);
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.media.jfxmediaimpl.platform.Platform
 * JD-Core Version:    0.6.2
 */