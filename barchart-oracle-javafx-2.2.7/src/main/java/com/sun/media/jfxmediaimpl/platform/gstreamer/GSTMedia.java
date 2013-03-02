/*    */ package com.sun.media.jfxmediaimpl.platform.gstreamer;
/*    */ 
/*    */ import com.sun.media.jfxmedia.MediaError;
/*    */ import com.sun.media.jfxmedia.locator.Locator;
/*    */ import com.sun.media.jfxmediaimpl.MediaUtils;
/*    */ import com.sun.media.jfxmediaimpl.NativeMedia;
/*    */ import com.sun.media.jfxmediaimpl.platform.Platform;
/*    */ import java.util.Map;
/*    */ 
/*    */ public final class GSTMedia extends NativeMedia
/*    */ {
/* 23 */   private final Object markerMutex = new Object();
/*    */   protected long refNativeMedia;
/*    */ 
/*    */   GSTMedia(Locator locator)
/*    */   {
/* 31 */     super(locator);
/*    */ 
/* 33 */     init();
/*    */   }
/*    */ 
/*    */   public Platform getPlatform()
/*    */   {
/* 38 */     return GSTPlatform.getPlatformInstance();
/*    */   }
/*    */ 
/*    */   private void init()
/*    */   {
/* 43 */     long[] nativeMediaHandle = new long[1];
/*    */ 
/* 45 */     Locator loc = getLocator();
/* 46 */     MediaError ret = MediaError.getFromCode(gstInitNativeMedia(loc, loc.getContentType(), loc.getContentLength(), nativeMediaHandle));
/*    */ 
/* 49 */     if (ret != MediaError.ERROR_NONE) {
/* 50 */       MediaUtils.nativeError(this, ret);
/*    */     }
/* 52 */     this.refNativeMedia = nativeMediaHandle[0];
/*    */   }
/*    */ 
/*    */   long getNativeMediaRef() {
/* 56 */     return this.refNativeMedia;
/*    */   }
/*    */ 
/*    */   public synchronized void dispose()
/*    */   {
/* 61 */     if (0L != this.refNativeMedia) {
/* 62 */       gstDispose(this.refNativeMedia);
/* 63 */       this.refNativeMedia = 0L;
/*    */     }
/*    */   }
/*    */ 
/*    */   private native int gstInitNativeMedia(Locator paramLocator, String paramString, long paramLong, long[] paramArrayOfLong);
/*    */ 
/*    */   private native int gstAddMarker(long paramLong, String paramString, double paramDouble);
/*    */ 
/*    */   private native int gstRemoveMarker(long paramLong, String paramString, double[] paramArrayOfDouble);
/*    */ 
/*    */   private native int gstRemoveAllMarkers(long paramLong);
/*    */ 
/*    */   private native int gstGetMarkers(long paramLong, Map<String, Double> paramMap);
/*    */ 
/*    */   private native void gstDispose(long paramLong);
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.media.jfxmediaimpl.platform.gstreamer.GSTMedia
 * JD-Core Version:    0.6.2
 */