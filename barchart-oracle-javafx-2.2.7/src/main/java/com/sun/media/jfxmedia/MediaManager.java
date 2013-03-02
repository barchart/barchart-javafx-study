/*     */ package com.sun.media.jfxmedia;
/*     */ 
/*     */ import com.sun.media.jfxmedia.events.MediaErrorListener;
/*     */ import com.sun.media.jfxmedia.locator.Locator;
/*     */ import com.sun.media.jfxmediaimpl.NativeMediaManager;
/*     */ import java.util.List;
/*     */ 
/*     */ public class MediaManager
/*     */ {
/*     */   public static String[] getSupportedContentTypes()
/*     */   {
/*  85 */     return NativeMediaManager.getDefaultInstance().getSupportedContentTypes();
/*     */   }
/*     */ 
/*     */   public static boolean canPlayContentType(String contentType)
/*     */   {
/*  96 */     if (contentType == null) {
/*  97 */       throw new IllegalArgumentException("contentType == null!");
/*     */     }
/*  99 */     return NativeMediaManager.getDefaultInstance().canPlayContentType(contentType);
/*     */   }
/*     */ 
/*     */   public static MetadataParser getMetadataParser(Locator locator)
/*     */   {
/* 105 */     if (locator == null) {
/* 106 */       throw new IllegalArgumentException("locator == null!");
/*     */     }
/* 108 */     NativeMediaManager.getDefaultInstance(); return NativeMediaManager.getMetadataParser(locator);
/*     */   }
/*     */ 
/*     */   public static Media getMedia(Locator locator)
/*     */   {
/* 121 */     if (locator == null) {
/* 122 */       throw new IllegalArgumentException("locator == null!");
/*     */     }
/* 124 */     return NativeMediaManager.getDefaultInstance().getMedia(locator);
/*     */   }
/*     */ 
/*     */   public static MediaPlayer getPlayer(Locator locator)
/*     */   {
/* 136 */     if (locator == null) {
/* 137 */       throw new IllegalArgumentException("locator == null!");
/*     */     }
/* 139 */     return NativeMediaManager.getDefaultInstance().getPlayer(locator);
/*     */   }
/*     */ 
/*     */   public static void addMediaErrorListener(MediaErrorListener listener)
/*     */   {
/* 151 */     if (listener == null) {
/* 152 */       throw new IllegalArgumentException("listener == null!");
/*     */     }
/* 154 */     NativeMediaManager.getDefaultInstance().addMediaErrorListener(listener);
/*     */   }
/*     */ 
/*     */   public static void removeMediaErrorListener(MediaErrorListener listener)
/*     */   {
/* 165 */     if (listener == null) {
/* 166 */       throw new IllegalArgumentException("listener == null!");
/*     */     }
/* 168 */     NativeMediaManager.getDefaultInstance().removeMediaErrorListener(listener);
/*     */   }
/*     */ 
/*     */   public static void registerMediaPlayerForDispose(Object obj, MediaPlayer player)
/*     */   {
/* 179 */     NativeMediaManager.registerMediaPlayerForDispose(obj, player);
/*     */   }
/*     */ 
/*     */   public static List<MediaPlayer> getAllMediaPlayers()
/*     */   {
/* 187 */     return NativeMediaManager.getDefaultInstance().getAllMediaPlayers();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.media.jfxmedia.MediaManager
 * JD-Core Version:    0.6.2
 */