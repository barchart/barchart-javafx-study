/*     */ package com.sun.media.jfxmediaimpl;
/*     */ 
/*     */ import com.sun.media.jfxmedia.Media;
/*     */ import com.sun.media.jfxmedia.MediaError;
/*     */ import com.sun.media.jfxmedia.MediaException;
/*     */ import com.sun.media.jfxmedia.MediaPlayer;
/*     */ import com.sun.media.jfxmedia.MetadataParser;
/*     */ import com.sun.media.jfxmedia.events.MediaErrorListener;
/*     */ import com.sun.media.jfxmedia.locator.Locator;
/*     */ import com.sun.media.jfxmedia.logging.Logger;
/*     */ import com.sun.media.jfxmediaimpl.platform.PlatformManager;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.Map;
/*     */ import java.util.WeakHashMap;
/*     */ 
/*     */ public class NativeMediaManager
/*     */ {
/*  84 */   private static NativeMediaManager theInstance = null;
/*     */ 
/*  88 */   private static boolean isNativeLayerInitialized = false;
/*     */ 
/*  93 */   private List<WeakReference<MediaErrorListener>> errorListeners = new ArrayList();
/*     */ 
/*  95 */   private static NativeMediaPlayerDisposer playerDisposer = new NativeMediaPlayerDisposer(null);
/*     */ 
/*  99 */   private static Map<MediaPlayer, Boolean> allMediaPlayers = new WeakHashMap();
/*     */ 
/* 104 */   private final List<String> supportedContentTypes = new ArrayList();
/*     */ 
/*     */   public static synchronized NativeMediaManager getDefaultInstance()
/*     */   {
/* 115 */     if (theInstance == null) {
/* 116 */       theInstance = new NativeMediaManager();
/*     */     }
/* 118 */     return theInstance;
/*     */   }
/*     */ 
/*     */   static synchronized void initNativeLayer()
/*     */   {
/* 138 */     if (!isNativeLayerInitialized)
/*     */     {
/* 142 */       PlatformManager.getManager().preloadPlatforms();
/*     */       try
/*     */       {
/* 146 */         AccessController.doPrivileged(new PrivilegedExceptionAction() {
/*     */           public Object run() throws Exception {
/* 148 */             if (HostUtils.isWindows())
/*     */             {
/* 150 */               NativeLibLoader.loadLibrary("glib-lite");
/* 151 */               NativeLibLoader.loadLibrary("gstreamer-lite");
/*     */             }
/* 153 */             NativeLibLoader.loadLibrary("jfxmedia");
/* 154 */             return null;
/*     */           } } );
/*     */       }
/*     */       catch (Exception e) {
/* 158 */         MediaUtils.error(null, MediaError.ERROR_MANAGER_ENGINEINIT_FAIL.code(), "Unable to load one or more dependent libraries.", e);
/*     */ 
/* 160 */         return;
/*     */       }
/*     */ 
/* 164 */       Logger.initNative();
/*     */ 
/* 168 */       PlatformManager.getManager().loadPlatforms();
/*     */ 
/* 171 */       isNativeLayerInitialized = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   private synchronized void loadContentTypes()
/*     */   {
/* 180 */     if (!this.supportedContentTypes.isEmpty())
/*     */     {
/* 182 */       return;
/*     */     }
/*     */ 
/* 185 */     List npt = PlatformManager.getManager().getSupportedContentTypes();
/* 186 */     if ((null != npt) && (!npt.isEmpty())) {
/* 187 */       this.supportedContentTypes.addAll(npt);
/*     */     }
/*     */ 
/* 190 */     if (Logger.canLog(1)) {
/* 191 */       StringBuilder sb = new StringBuilder("JFXMedia supported content types:\n");
/* 192 */       for (String type : this.supportedContentTypes) {
/* 193 */         sb.append("    ");
/* 194 */         sb.append(type);
/* 195 */         sb.append("\n");
/*     */       }
/* 197 */       Logger.logMsg(1, sb.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean canPlayContentType(String contentType)
/*     */   {
/* 213 */     if (contentType == null) {
/* 214 */       throw new IllegalArgumentException("contentType == null!");
/*     */     }
/*     */ 
/* 217 */     if (this.supportedContentTypes.isEmpty()) {
/* 218 */       loadContentTypes();
/*     */     }
/*     */ 
/* 225 */     for (String type : this.supportedContentTypes) {
/* 226 */       if (contentType.equalsIgnoreCase(type)) {
/* 227 */         return true;
/*     */       }
/*     */     }
/*     */ 
/* 231 */     return false;
/*     */   }
/*     */ 
/*     */   public String[] getSupportedContentTypes()
/*     */   {
/* 242 */     if (this.supportedContentTypes.isEmpty()) {
/* 243 */       loadContentTypes();
/*     */     }
/*     */ 
/* 246 */     return (String[])this.supportedContentTypes.toArray(new String[1]);
/*     */   }
/*     */ 
/*     */   public static MetadataParser getMetadataParser(Locator locator) {
/* 250 */     return PlatformManager.getManager().createMetadataParser(locator);
/*     */   }
/*     */ 
/*     */   public MediaPlayer getPlayer(Locator locator)
/*     */   {
/* 258 */     initNativeLayer();
/*     */ 
/* 260 */     MediaPlayer player = PlatformManager.getManager().createMediaPlayer(locator);
/* 261 */     if (null == player) {
/* 262 */       throw new MediaException("Could not create player!");
/*     */     }
/*     */ 
/* 266 */     allMediaPlayers.put(player, Boolean.TRUE);
/*     */ 
/* 268 */     return player;
/*     */   }
/*     */ 
/*     */   public Media getMedia(Locator locator)
/*     */   {
/* 282 */     initNativeLayer();
/* 283 */     return PlatformManager.getManager().createMedia(locator);
/*     */   }
/*     */ 
/*     */   public void addMediaErrorListener(MediaErrorListener listener)
/*     */   {
/* 291 */     if (listener != null)
/*     */     {
/* 296 */       for (ListIterator it = this.errorListeners.listIterator(); it.hasNext(); ) {
/* 297 */         MediaErrorListener l = (MediaErrorListener)((WeakReference)it.next()).get();
/* 298 */         if (l == null) {
/* 299 */           it.remove();
/*     */         }
/*     */       }
/*     */ 
/* 303 */       this.errorListeners.add(new WeakReference(listener));
/*     */     }
/*     */   }
/*     */ 
/*     */   public void removeMediaErrorListener(MediaErrorListener listener)
/*     */   {
/*     */     ListIterator it;
/* 312 */     if (listener != null)
/*     */     {
/* 314 */       for (it = this.errorListeners.listIterator(); it.hasNext(); ) {
/* 315 */         MediaErrorListener l = (MediaErrorListener)((WeakReference)it.next()).get();
/* 316 */         if ((l == null) || (l == listener))
/* 317 */           it.remove();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void registerMediaPlayerForDispose(Object obj, MediaPlayer player)
/*     */   {
/* 333 */     MediaDisposer.addResourceDisposer(obj, player, playerDisposer);
/*     */   }
/*     */ 
/*     */   public List<MediaPlayer> getAllMediaPlayers()
/*     */   {
/* 343 */     List allPlayers = null;
/*     */ 
/* 345 */     if (!allMediaPlayers.isEmpty()) {
/* 346 */       allPlayers = new ArrayList(allMediaPlayers.keySet());
/*     */     }
/*     */ 
/* 349 */     return allPlayers;
/*     */   }
/*     */ 
/*     */   List<WeakReference<MediaErrorListener>> getMediaErrorListeners()
/*     */   {
/* 356 */     return this.errorListeners;
/*     */   }
/*     */ 
/*     */   private static class NativeMediaPlayerDisposer implements MediaDisposer.ResourceDisposer
/*     */   {
/*     */     public void disposeResource(Object resource)
/*     */     {
/* 363 */       MediaPlayer player = (MediaPlayer)resource;
/* 364 */       if (player != null)
/* 365 */         player.dispose();
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.media.jfxmediaimpl.NativeMediaManager
 * JD-Core Version:    0.6.2
 */