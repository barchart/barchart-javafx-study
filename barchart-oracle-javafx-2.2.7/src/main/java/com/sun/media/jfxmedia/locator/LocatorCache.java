/*     */ package com.sun.media.jfxmedia.locator;
/*     */ 
/*     */ import com.sun.media.jfxmedia.logging.Logger;
/*     */ import com.sun.media.jfxmediaimpl.MediaDisposer;
/*     */ import com.sun.media.jfxmediaimpl.MediaDisposer.ResourceDisposer;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.net.URI;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class LocatorCache
/*     */ {
/*     */   private final Map<URI, WeakReference<CacheReference>> uriCache;
/*     */   private final CacheDisposer cacheDisposer;
/*     */ 
/*     */   public static LocatorCache locatorCache()
/*     */   {
/*  24 */     return CacheInitializer.globalInstance;
/*     */   }
/*     */ 
/*     */   private LocatorCache()
/*     */   {
/*  31 */     this.uriCache = new HashMap();
/*  32 */     this.cacheDisposer = new CacheDisposer(null);
/*     */   }
/*     */ 
/*     */   public CacheReference registerURICache(URI sourceURI, ByteBuffer data, String mimeType)
/*     */   {
/*  43 */     if (Logger.canLog(1)) {
/*  44 */       Logger.logMsg(1, "New cache entry: URI " + sourceURI + ", buffer " + data + ", MIME type " + mimeType);
/*     */     }
/*     */ 
/*  49 */     if (!data.isDirect()) {
/*  50 */       data.rewind();
/*  51 */       ByteBuffer newData = ByteBuffer.allocateDirect(data.capacity());
/*  52 */       newData.put(data);
/*  53 */       data = newData;
/*     */     }
/*     */ 
/*  56 */     CacheReference ref = new CacheReference(data, mimeType);
/*  57 */     synchronized (this.uriCache) {
/*  58 */       this.uriCache.put(sourceURI, new WeakReference(ref));
/*     */     }
/*     */ 
/*  63 */     MediaDisposer.addResourceDisposer(ref, sourceURI, this.cacheDisposer);
/*  64 */     return ref;
/*     */   }
/*     */ 
/*     */   public CacheReference fetchURICache(URI sourceURI) {
/*  68 */     synchronized (this.uriCache) {
/*  69 */       WeakReference ref = (WeakReference)this.uriCache.get(sourceURI);
/*  70 */       if (null == ref) {
/*  71 */         return null;
/*     */       }
/*     */ 
/*  74 */       CacheReference cacheData = (CacheReference)ref.get();
/*  75 */       if (null != cacheData) {
/*  76 */         if (Logger.canLog(1)) {
/*  77 */           Logger.logMsg(1, "Fetched cache entry: URI " + sourceURI + ", buffer " + cacheData.getBuffer() + ", MIME type " + cacheData.getMIMEType());
/*     */         }
/*     */ 
/*  82 */         return cacheData;
/*     */       }
/*     */     }
/*  85 */     return null;
/*     */   }
/*     */ 
/*     */   public boolean isCached(URI sourceURI) {
/*  89 */     synchronized (this.uriCache) {
/*  90 */       return this.uriCache.containsKey(sourceURI);
/*     */     }
/*     */   }
/*     */ 
/*     */   private class CacheDisposer
/*     */     implements MediaDisposer.ResourceDisposer
/*     */   {
/*     */     private CacheDisposer()
/*     */     {
/*     */     }
/*     */ 
/*     */     public void disposeResource(Object resource)
/*     */     {
/* 117 */       if ((resource instanceof URI))
/* 118 */         synchronized (LocatorCache.this.uriCache) {
/* 119 */           LocatorCache.this.uriCache.remove((URI)resource);
/*     */         }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class CacheReference
/*     */   {
/*     */     private final ByteBuffer buffer;
/*     */     private String mimeType;
/*     */ 
/*     */     public CacheReference(ByteBuffer buf, String mimeType)
/*     */     {
/*  99 */       this.buffer = buf;
/* 100 */       this.mimeType = mimeType;
/*     */     }
/*     */ 
/*     */     public ByteBuffer getBuffer() {
/* 104 */       return this.buffer;
/*     */     }
/*     */ 
/*     */     public String getMIMEType() {
/* 108 */       return this.mimeType;
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class CacheInitializer
/*     */   {
/*  20 */     private static final LocatorCache globalInstance = new LocatorCache(null);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.media.jfxmedia.locator.LocatorCache
 * JD-Core Version:    0.6.2
 */