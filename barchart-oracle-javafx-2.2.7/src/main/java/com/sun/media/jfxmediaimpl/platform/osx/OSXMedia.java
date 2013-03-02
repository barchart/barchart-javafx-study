/*    */ package com.sun.media.jfxmediaimpl.platform.osx;
/*    */ 
/*    */ import com.sun.media.jfxmedia.locator.Locator;
/*    */ import com.sun.media.jfxmediaimpl.NativeMedia;
/*    */ import com.sun.media.jfxmediaimpl.platform.Platform;
/*    */ 
/*    */ final class OSXMedia extends NativeMedia
/*    */ {
/*    */   OSXMedia(Locator source)
/*    */   {
/* 17 */     super(source);
/*    */   }
/*    */ 
/*    */   public Platform getPlatform()
/*    */   {
/* 22 */     return OSXPlatform.getPlatformInstance();
/*    */   }
/*    */ 
/*    */   public void dispose()
/*    */   {
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.media.jfxmediaimpl.platform.osx.OSXMedia
 * JD-Core Version:    0.6.2
 */