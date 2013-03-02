/*     */ package com.sun.media.jfxmediaimpl.platform;
/*     */ 
/*     */ import com.sun.media.jfxmedia.Media;
/*     */ import com.sun.media.jfxmedia.MediaPlayer;
/*     */ import com.sun.media.jfxmedia.MetadataParser;
/*     */ import com.sun.media.jfxmedia.locator.Locator;
/*     */ import com.sun.media.jfxmedia.logging.Logger;
/*     */ import com.sun.media.jfxmediaimpl.HostUtils;
/*     */ import com.sun.media.jfxmediaimpl.platform.gstreamer.GSTPlatform;
/*     */ import com.sun.media.jfxmediaimpl.platform.java.JavaPlatform;
/*     */ import com.sun.media.jfxmediaimpl.platform.osx.OSXPlatform;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ public final class PlatformManager
/*     */ {
/*     */   private static String enabledPlatforms;
/*     */   private final List<Platform> platforms;
/*     */ 
/*     */   private static void getPlatformSettings()
/*     */   {
/*  38 */     enabledPlatforms = System.getProperty("jfxmedia.platforms", "").toLowerCase();
/*     */   }
/*     */ 
/*     */   private static boolean isPlatformEnabled(String name)
/*     */   {
/*  44 */     if ((null == enabledPlatforms) || (enabledPlatforms.length() == 0))
/*     */     {
/*  46 */       return true;
/*     */     }
/*  48 */     return enabledPlatforms.indexOf(name.toLowerCase()) != -1;
/*     */   }
/*     */ 
/*     */   public static PlatformManager getManager()
/*     */   {
/*  56 */     return PlatformManagerInitializer.globalInstance;
/*     */   }
/*     */ 
/*     */   private PlatformManager()
/*     */   {
/*  62 */     this.platforms = new ArrayList();
/*     */ 
/*  67 */     if (isPlatformEnabled("JavaPlatform")) {
/*  68 */       Platform platty = JavaPlatform.getPlatformInstance();
/*  69 */       if (null != platty) {
/*  70 */         this.platforms.add(platty);
/*     */       }
/*     */     }
/*     */ 
/*  74 */     if (isPlatformEnabled("GSTPlatform")) {
/*  75 */       Platform platty = GSTPlatform.getPlatformInstance();
/*  76 */       if (null != platty) {
/*  77 */         this.platforms.add(platty);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  82 */     if ((HostUtils.isMacOSX()) && (isPlatformEnabled("OSXPlatform"))) {
/*  83 */       Platform platty = OSXPlatform.getPlatformInstance();
/*  84 */       if (null != platty) {
/*  85 */         this.platforms.add(platty);
/*     */       }
/*     */     }
/*     */ 
/*  89 */     if (Logger.canLog(1)) {
/*  90 */       StringBuilder sb = new StringBuilder("Enabled JFXMedia platforms: ");
/*  91 */       for (Platform p : this.platforms) {
/*  92 */         sb.append("\n   - ");
/*  93 */         sb.append(p.getClass().getName());
/*     */       }
/*  95 */       Logger.logMsg(1, sb.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   public synchronized void preloadPlatforms() {
/* 100 */     for (Platform platty : this.platforms)
/* 101 */       platty.preloadPlatform();
/*     */   }
/*     */ 
/*     */   public synchronized void loadPlatforms()
/*     */   {
/* 107 */     Iterator iter = this.platforms.iterator();
/* 108 */     while (iter.hasNext()) {
/* 109 */       Platform platty = (Platform)iter.next();
/* 110 */       if (!platty.loadPlatform()) {
/* 111 */         if (Logger.canLog(1)) {
/* 112 */           Logger.logMsg(1, "Failed to load platform: " + platty);
/*     */         }
/*     */ 
/* 115 */         iter.remove();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public List<String> getSupportedContentTypes() {
/* 121 */     ArrayList outTypes = new ArrayList();
/*     */ 
/* 123 */     if (!this.platforms.isEmpty()) {
/* 124 */       for (Platform platty : this.platforms) {
/* 125 */         String[] npt = platty.getSupportedContentTypes();
/* 126 */         if (npt != null) {
/* 127 */           for (String type : npt) {
/* 128 */             if (!outTypes.contains(type)) {
/* 129 */               outTypes.add(type);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 136 */     return outTypes;
/*     */   }
/*     */ 
/*     */   public MetadataParser createMetadataParser(Locator source)
/*     */   {
/* 141 */     for (Platform platty : this.platforms) {
/* 142 */       MetadataParser parser = platty.createMetadataParser(source);
/* 143 */       if (parser != null) {
/* 144 */         return parser;
/*     */       }
/*     */     }
/*     */ 
/* 148 */     return null;
/*     */   }
/*     */ 
/*     */   public Media createMedia(Locator source)
/*     */   {
/* 153 */     String mimeType = source.getContentType();
/*     */ 
/* 155 */     for (Platform platty : this.platforms) {
/* 156 */       if (platty.canPlayContentType(mimeType)) {
/* 157 */         Media outMedia = platty.createMedia(source);
/* 158 */         if (null != outMedia) {
/* 159 */           return outMedia;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 164 */     return null;
/*     */   }
/*     */ 
/*     */   public MediaPlayer createMediaPlayer(Locator source) {
/* 168 */     String mimeType = source.getContentType();
/*     */ 
/* 170 */     for (Platform platty : this.platforms) {
/* 171 */       if (platty.canPlayContentType(mimeType))
/*     */       {
/* 173 */         Object cookie = platty.prerollMediaPlayer(source);
/* 174 */         if (null != cookie)
/*     */         {
/* 176 */           MediaPlayer outPlayer = platty.createMediaPlayer(source, cookie);
/* 177 */           if (null != outPlayer) {
/* 178 */             return outPlayer;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 184 */     return null;
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  28 */     AccessController.doPrivileged(new PrivilegedAction() {
/*     */       public Object run() {
/*  30 */         PlatformManager.access$000();
/*  31 */         return null;
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   private static final class PlatformManagerInitializer
/*     */   {
/*  52 */     private static final PlatformManager globalInstance = new PlatformManager(null);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.media.jfxmediaimpl.platform.PlatformManager
 * JD-Core Version:    0.6.2
 */