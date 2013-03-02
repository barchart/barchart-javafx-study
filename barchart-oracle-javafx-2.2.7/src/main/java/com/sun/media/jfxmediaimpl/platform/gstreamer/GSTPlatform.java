/*     */ package com.sun.media.jfxmediaimpl.platform.gstreamer;
/*     */ 
/*     */ import com.sun.media.jfxmedia.Media;
/*     */ import com.sun.media.jfxmedia.MediaError;
/*     */ import com.sun.media.jfxmedia.MediaPlayer;
/*     */ import com.sun.media.jfxmedia.events.PlayerStateEvent.PlayerState;
/*     */ import com.sun.media.jfxmedia.locator.Locator;
/*     */ import com.sun.media.jfxmediaimpl.HostUtils;
/*     */ import com.sun.media.jfxmediaimpl.MediaUtils;
/*     */ import com.sun.media.jfxmediaimpl.platform.Platform;
/*     */ import java.net.URI;
/*     */ 
/*     */ public final class GSTPlatform extends Platform
/*     */ {
/*  23 */   private static final String[] CONTENT_TYPES = { "audio/x-aiff", "audio/mp3", "audio/mpeg", "audio/x-wav", "video/x-javafx", "video/x-flv", "video/x-fxm", "video/mp4", "audio/x-m4a", "video/x-m4v" };
/*     */ 
/*  36 */   private static final String[] NON_OSX_CONTENT_TYPES = { "application/vnd.apple.mpegurl", "audio/mpegurl" };
/*     */ 
/*  42 */   private static GSTPlatform globalInstance = null;
/*     */   private static final String[] contentTypes;
/*     */ 
/*     */   public boolean loadPlatform()
/*     */   {
/*     */     MediaError ret;
/*     */     try
/*     */     {
/*  65 */       ret = MediaError.getFromCode(gstInitPlatform());
/*     */     } catch (UnsatisfiedLinkError ule) {
/*  67 */       ret = MediaError.ERROR_MANAGER_ENGINEINIT_FAIL;
/*     */     }
/*     */ 
/*  70 */     if (ret != MediaError.ERROR_NONE) {
/*  71 */       MediaUtils.nativeError(GSTPlatform.class, ret);
/*     */     }
/*  73 */     return true;
/*     */   }
/*     */ 
/*     */   public static synchronized Platform getPlatformInstance()
/*     */   {
/*  80 */     if (null == globalInstance) {
/*  81 */       globalInstance = new GSTPlatform();
/*     */     }
/*     */ 
/*  84 */     return globalInstance;
/*     */   }
/*     */ 
/*     */   public String[] getSupportedContentTypes()
/*     */   {
/*  91 */     return contentTypes;
/*     */   }
/*     */ 
/*     */   public Media createMedia(Locator source) {
/*  95 */     return new GSTMedia(source);
/*     */   }
/*     */ 
/*     */   public Object prerollMediaPlayer(Locator source)
/*     */   {
/* 100 */     GSTMediaPlayer player = new GSTMediaPlayer(source);
/*     */ 
/* 103 */     if ((player != null) && (HostUtils.isMacOSX())) {
/* 104 */       String contentType = source.getContentType();
/* 105 */       if (("video/mp4".equals(contentType)) || ("video/x-m4v".equals(contentType)))
/*     */       {
/* 109 */         long timeout = source.getURI().getScheme().equals("http") ? 60000L : 5000L;
/*     */ 
/* 111 */         long iterationTime = 50L;
/* 112 */         long timeWaited = 0L;
/*     */ 
/* 114 */         Object lock = new Object();
/* 115 */         PlayerStateEvent.PlayerState state = player.getState();
/*     */ 
/* 117 */         while ((timeWaited < timeout) && ((state == PlayerStateEvent.PlayerState.UNKNOWN) || (state == PlayerStateEvent.PlayerState.STALLED)))
/*     */         {
/*     */           try {
/* 120 */             synchronized (lock) {
/* 121 */               lock.wait(50L);
/* 122 */               timeWaited += 50L;
/*     */             }
/*     */           }
/*     */           catch (InterruptedException ex)
/*     */           {
/*     */           }
/* 128 */           state = player.getState();
/*     */         }
/*     */ 
/* 132 */         if (player.getState() != PlayerStateEvent.PlayerState.READY) {
/* 133 */           player = null;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 138 */     return player;
/*     */   }
/*     */ 
/*     */   public MediaPlayer createMediaPlayer(Locator source, Object cookie) {
/* 142 */     if (cookie == null)
/* 143 */       throw new NullPointerException("null player!");
/* 144 */     if (!(cookie instanceof GSTMediaPlayer)) {
/* 145 */       throw new IllegalArgumentException("!(cookie instanceof GSTMediaPlayer)");
/*     */     }
/*     */ 
/* 148 */     return (MediaPlayer)cookie;
/*     */   }
/*     */ 
/*     */   private static native int gstInitPlatform();
/*     */ 
/*     */   static
/*     */   {
/*  49 */     if (!HostUtils.isMacOSX()) {
/*  50 */       contentTypes = new String[CONTENT_TYPES.length + NON_OSX_CONTENT_TYPES.length];
/*  51 */       System.arraycopy(CONTENT_TYPES, 0, contentTypes, 0, CONTENT_TYPES.length);
/*     */ 
/*  53 */       System.arraycopy(NON_OSX_CONTENT_TYPES, 0, contentTypes, CONTENT_TYPES.length, NON_OSX_CONTENT_TYPES.length);
/*     */     }
/*     */     else {
/*  56 */       contentTypes = CONTENT_TYPES;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.media.jfxmediaimpl.platform.gstreamer.GSTPlatform
 * JD-Core Version:    0.6.2
 */