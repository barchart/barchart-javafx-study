/*    */ package com.sun.media.jfxmediaimpl.platform.osx;
/*    */ 
/*    */ import com.sun.media.jfxmedia.Media;
/*    */ import com.sun.media.jfxmedia.MediaPlayer;
/*    */ import com.sun.media.jfxmedia.locator.Locator;
/*    */ import com.sun.media.jfxmedia.logging.Logger;
/*    */ import com.sun.media.jfxmediaimpl.HostUtils;
/*    */ import com.sun.media.jfxmediaimpl.platform.Platform;
/*    */ 
/*    */ public final class OSXPlatform extends Platform
/*    */ {
/* 21 */   private static final String[] CONTENT_TYPES = { "video/mp4", "audio/x-m4a", "video/x-m4v", "application/vnd.apple.mpegurl", "audio/mpegurl" };
/*    */ 
/*    */   public static Platform getPlatformInstance()
/*    */   {
/* 34 */     return OSXPlatformInitializer.globalInstance;
/*    */   }
/*    */ 
/*    */   public void preloadPlatform()
/*    */   {
/*    */   }
/*    */ 
/*    */   public boolean loadPlatform()
/*    */   {
/* 48 */     if (!HostUtils.isMacOSX()) {
/* 49 */       return false;
/*    */     }
/*    */     try
/*    */     {
/* 53 */       osxPlatformInit();
/*    */     } catch (UnsatisfiedLinkError ule) {
/* 55 */       if (Logger.canLog(1)) {
/* 56 */         Logger.logMsg(1, "Unable to load OSX platform.");
/*    */       }
/*    */ 
/* 59 */       return false;
/*    */     }
/* 61 */     return true;
/*    */   }
/*    */ 
/*    */   public String[] getSupportedContentTypes()
/*    */   {
/* 66 */     return CONTENT_TYPES;
/*    */   }
/*    */ 
/*    */   public Media createMedia(Locator source)
/*    */   {
/* 71 */     return new OSXMedia(source);
/*    */   }
/*    */ 
/*    */   public Object prerollMediaPlayer(Locator source)
/*    */   {
/* 78 */     return new OSXMediaPlayer(source);
/*    */   }
/*    */ 
/*    */   public MediaPlayer createMediaPlayer(Locator source, Object cookie)
/*    */   {
/* 83 */     if ((cookie instanceof OSXMediaPlayer)) {
/* 84 */       OSXMediaPlayer player = (OSXMediaPlayer)cookie;
/*    */ 
/* 86 */       player.initializePlayer();
/* 87 */       return player;
/*    */     }
/* 89 */     return null;
/*    */   }
/*    */ 
/*    */   private static native void osxPlatformInit();
/*    */ 
/*    */   private static final class OSXPlatformInitializer
/*    */   {
/* 30 */     private static final OSXPlatform globalInstance = new OSXPlatform(null);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.media.jfxmediaimpl.platform.osx.OSXPlatform
 * JD-Core Version:    0.6.2
 */