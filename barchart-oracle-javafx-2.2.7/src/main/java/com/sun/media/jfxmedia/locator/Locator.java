/*     */ package com.sun.media.jfxmedia.locator;
/*     */ 
/*     */ import com.sun.media.jfxmedia.MediaException;
/*     */ import com.sun.media.jfxmedia.MediaManager;
/*     */ import com.sun.media.jfxmedia.logging.Logger;
/*     */ import com.sun.media.jfxmediaimpl.MediaUtils;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.Map;
/*     */ import java.util.TreeMap;
/*     */ import java.util.concurrent.CountDownLatch;
/*     */ 
/*     */ public class Locator
/*     */ {
/*     */   public static final String DEFAULT_CONTENT_TYPE = "application/octet-stream";
/*     */   private static final int MAX_CONNECTION_ATTEMPTS = 5;
/*     */   private static final long CONNECTION_RETRY_INTERVAL = 1000L;
/* 107 */   protected String contentType = "application/octet-stream";
/*     */ 
/* 111 */   protected long contentLength = -1L;
/*     */   protected URI uri;
/*     */   private Map<String, Object> connectionProperties;
/* 125 */   private final Object propertyLock = new Object();
/*     */ 
/* 130 */   private String uriString = null;
/* 131 */   private String scheme = null;
/* 132 */   private String protocol = null;
/*     */ 
/* 137 */   private LocatorCache.CacheReference cacheEntry = null;
/*     */ 
/* 143 */   private boolean canBlock = false;
/*     */ 
/* 148 */   private CountDownLatch readySignal = new CountDownLatch(1);
/*     */ 
/*     */   private LocatorConnection getConnection(URI uri, final String requestMethod)
/*     */     throws MalformedURLException, IOException
/*     */   {
/* 162 */     final URI uri2 = uri;
/* 163 */     LocatorConnection locatorConnection = (LocatorConnection)AccessController.doPrivileged(new PrivilegedAction()
/*     */     {
/*     */       public Locator.LocatorConnection run() {
/*     */         try {
/* 167 */           Locator.LocatorConnection locatorConnection = new Locator.LocatorConnection(null);
/* 168 */           HttpURLConnection connection = (HttpURLConnection)uri2.toURL().openConnection();
/* 169 */           connection.setRequestMethod(requestMethod);
/*     */ 
/* 172 */           synchronized (Locator.this.propertyLock) {
/* 173 */             if (Locator.this.connectionProperties != null) {
/* 174 */               for (String key : Locator.this.connectionProperties.keySet()) {
/* 175 */                 Object value = Locator.this.connectionProperties.get(key);
/* 176 */                 if ((value instanceof String)) {
/* 177 */                   connection.setRequestProperty(key, (String)value);
/*     */                 }
/*     */               }
/*     */ 
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/* 185 */           locatorConnection.responseCode = connection.getResponseCode();
/* 186 */           if (connection.getResponseCode() == 200) {
/* 187 */             locatorConnection.connection = connection;
/* 188 */             return locatorConnection;
/*     */           }
/* 190 */           connection.disconnect();
/* 191 */           locatorConnection.connection = null;
/* 192 */           return locatorConnection;
/*     */         } catch (Exception e) {
/*     */         }
/* 195 */         return null;
/*     */       }
/*     */     });
/* 200 */     return locatorConnection;
/*     */   }
/*     */ 
/*     */   public Locator(URI uri)
/*     */     throws URISyntaxException
/*     */   {
/* 224 */     if (uri == null) {
/* 225 */       throw new NullPointerException("uri == null!");
/*     */     }
/*     */ 
/* 229 */     this.uriString = uri.toASCIIString();
/* 230 */     this.scheme = uri.getScheme();
/* 231 */     if (this.scheme == null) {
/* 232 */       throw new IllegalArgumentException("uri.getScheme() == null!");
/*     */     }
/* 234 */     this.scheme = this.scheme.toLowerCase();
/*     */ 
/* 237 */     if (this.scheme.equals("jar")) {
/* 238 */       URI subURI = new URI(this.uriString.substring(4));
/* 239 */       this.protocol = subURI.getScheme();
/* 240 */       if (this.protocol == null) {
/* 241 */         throw new IllegalArgumentException("uri.getScheme() == null!");
/*     */       }
/* 243 */       this.protocol = this.protocol.toLowerCase();
/*     */     } else {
/* 245 */       this.protocol = this.scheme;
/*     */     }
/*     */ 
/* 249 */     if ((!this.protocol.equals("file")) && (!this.protocol.equals("http"))) {
/* 250 */       throw new UnsupportedOperationException("Unsupported protocol \"" + this.protocol + "\"");
/*     */     }
/*     */ 
/* 254 */     if (this.protocol.equals("http")) {
/* 255 */       this.canBlock = true;
/*     */     }
/*     */ 
/* 259 */     this.uri = uri;
/*     */   }
/*     */ 
/*     */   private InputStream getInputStream(URI uri) throws MalformedURLException, IOException
/*     */   {
/* 264 */     final URL url = uri.toURL();
/* 265 */     final URLConnection connection = url.openConnection();
/*     */ 
/* 268 */     synchronized (this.propertyLock) {
/* 269 */       if (this.connectionProperties != null) {
/* 270 */         for (String key : this.connectionProperties.keySet()) {
/* 271 */           Object value = this.connectionProperties.get(key);
/* 272 */           if ((value instanceof String)) {
/* 273 */             connection.setRequestProperty(key, (String)value);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 279 */     InputStream inputStream = null;
/*     */     try {
/* 281 */       inputStream = (InputStream)AccessController.doPrivileged(new PrivilegedExceptionAction()
/*     */       {
/*     */         public InputStream run() throws IOException {
/* 284 */           Locator.this.contentLength = connection.getContentLength();
/* 285 */           return url.openStream();
/*     */         } } );
/*     */     }
/*     */     catch (PrivilegedActionException e) {
/* 289 */       throw ((IOException)e.getException());
/*     */     }
/*     */ 
/* 292 */     return inputStream;
/*     */   }
/*     */ 
/*     */   public void cacheMedia()
/*     */   {
/* 300 */     LocatorCache.CacheReference ref = LocatorCache.locatorCache().fetchURICache(this.uri);
/* 301 */     if (null == ref)
/*     */     {
/*     */       InputStream is;
/*     */       try
/*     */       {
/* 307 */         is = getInputStream(this.uri);
/*     */       } catch (Throwable t) {
/* 309 */         return;
/*     */       }
/*     */ 
/* 313 */       ByteBuffer cacheBuffer = ByteBuffer.allocateDirect((int)this.contentLength);
/* 314 */       byte[] readBuf = new byte[8192];
/* 315 */       int total = 0;
/*     */ 
/* 317 */       while (total < this.contentLength) {
/*     */         int count;
/*     */         try { count = is.read(readBuf);
/*     */         } catch (IOException ioe) {
/*     */           try {
/* 322 */             is.close();
/*     */           } catch (Throwable t) {
/*     */           }
/* 325 */           if (Logger.canLog(1)) {
/* 326 */             Logger.logMsg(1, "IOException trying to preload media: " + ioe);
/*     */           }
/* 328 */           return;
/*     */         }
/*     */ 
/* 331 */         if (count == -1)
/*     */         {
/*     */           break;
/*     */         }
/* 335 */         cacheBuffer.put(readBuf, 0, count);
/*     */       }
/*     */       try
/*     */       {
/* 339 */         is.close();
/*     */       }
/*     */       catch (Throwable t) {
/*     */       }
/* 343 */       this.cacheEntry = LocatorCache.locatorCache().registerURICache(this.uri, cacheBuffer, this.contentType);
/* 344 */       this.canBlock = false;
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean canBlock()
/*     */   {
/* 352 */     return this.canBlock;
/*     */   }
/*     */ 
/*     */   public void init()
/*     */     throws URISyntaxException, IOException, FileNotFoundException
/*     */   {
/*     */     try
/*     */     {
/* 368 */       int firstSlash = this.uriString.indexOf("/");
/* 369 */       if ((firstSlash != -1) && (this.uriString.charAt(firstSlash + 1) != '/'))
/*     */       {
/* 371 */         if (this.protocol.equals("file"))
/*     */         {
/* 373 */           this.uriString = this.uriString.replaceFirst("/", "///");
/* 374 */         } else if (this.protocol.equals("http"))
/*     */         {
/* 376 */           this.uriString = this.uriString.replaceFirst("/", "//");
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 381 */       if ((System.getProperty("os.name").toLowerCase().indexOf("win") == -1) && (this.protocol.equals("file")))
/*     */       {
/* 383 */         int index = this.uriString.indexOf("/~/");
/* 384 */         if (index != -1) {
/* 385 */           this.uriString = (this.uriString.substring(0, index) + System.getProperty("user.home") + this.uriString.substring(index + 2));
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 392 */       this.uri = new URI(this.uriString);
/*     */ 
/* 395 */       this.cacheEntry = LocatorCache.locatorCache().fetchURICache(this.uri);
/* 396 */       if (null != this.cacheEntry)
/*     */       {
/* 398 */         this.contentType = this.cacheEntry.getMIMEType();
/* 399 */         this.contentLength = this.cacheEntry.getBuffer().capacity();
/* 400 */         if (Logger.canLog(1)) {
/* 401 */           Logger.logMsg(1, "Locator init cache hit:\n    uri " + this.uri + "\n    type " + this.contentType + "\n    length " + this.contentLength);
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 410 */         boolean isConnected = false;
/* 411 */         boolean isMediaUnAvailable = false;
/* 412 */         boolean isMediaSupported = true;
/* 413 */         for (int numConnectionAttempts = 0; numConnectionAttempts < 5; numConnectionAttempts++)
/*     */         {
/*     */           try {
/* 416 */             if (this.scheme.equals("http"))
/*     */             {
/* 418 */               LocatorConnection locatorConnection = getConnection(this.uri, "HEAD");
/* 419 */               if ((locatorConnection == null) || (locatorConnection.connection == null)) {
/* 420 */                 locatorConnection = getConnection(this.uri, "GET");
/*     */               }
/*     */ 
/* 423 */               if ((locatorConnection != null) && (locatorConnection.connection != null)) {
/* 424 */                 isConnected = true;
/*     */ 
/* 427 */                 this.contentType = locatorConnection.connection.getContentType();
/* 428 */                 this.contentLength = locatorConnection.connection.getContentLength();
/*     */ 
/* 431 */                 locatorConnection.connection.disconnect();
/* 432 */               } else if ((locatorConnection != null) && 
/* 433 */                 (locatorConnection.responseCode == 404)) {
/* 434 */                 isMediaUnAvailable = true;
/*     */               }
/*     */ 
/*     */             }
/* 439 */             else if ((this.scheme.equals("file")) || (this.scheme.equals("jar"))) {
/* 440 */               InputStream stream = getInputStream(this.uri);
/* 441 */               stream.close();
/* 442 */               isConnected = true;
/* 443 */               this.contentType = MediaUtils.filenameToContentType(this.uriString);
/*     */             }
/*     */ 
/* 446 */             if (isConnected)
/*     */             {
/* 451 */               if (this.contentType.equals("audio/x-wav")) {
/* 452 */                 this.contentType = getContentTypeFromFileSignature(this.uri);
/* 453 */                 if (!MediaManager.canPlayContentType(this.contentType)) {
/* 454 */                   isMediaSupported = false;
/*     */                 }
/*     */               }
/* 457 */               else if (!MediaManager.canPlayContentType(this.contentType))
/*     */               {
/* 459 */                 this.contentType = MediaUtils.filenameToContentType(this.uriString);
/*     */ 
/* 461 */                 if (this.contentType.equals("application/octet-stream"))
/*     */                 {
/* 463 */                   this.contentType = getContentTypeFromFileSignature(this.uri);
/*     */                 }
/*     */ 
/* 466 */                 if (!MediaManager.canPlayContentType(this.contentType)) {
/* 467 */                   isMediaSupported = false;
/*     */                 }
/*     */ 
/*     */               }
/*     */ 
/* 473 */               break;
/*     */             }
/*     */           } catch (IOException ioe) {
/* 476 */             if (numConnectionAttempts + 1 >= 5) {
/* 477 */               throw ioe;
/*     */             }
/*     */           }
/*     */           try
/*     */           {
/* 482 */             Thread.sleep(1000L);
/*     */           }
/*     */           catch (InterruptedException ie)
/*     */           {
/*     */           }
/*     */         }
/*     */ 
/* 489 */         if (!isConnected) {
/* 490 */           if (isMediaUnAvailable) {
/* 491 */             throw new FileNotFoundException("media is unavailable (" + this.uri.toString() + ")");
/*     */           }
/* 493 */           throw new IOException("could not connect to media (" + this.uri.toString() + ")");
/*     */         }
/* 495 */         if (!isMediaSupported)
/* 496 */           throw new MediaException("media type not supported (" + this.uri.toString() + ")");
/*     */       }
/*     */     } catch (FileNotFoundException e) {
/* 499 */       throw e;
/*     */     } catch (IOException e) {
/* 501 */       throw e;
/*     */     } catch (MediaException e) {
/* 503 */       throw e;
/*     */     } finally {
/* 505 */       this.readySignal.countDown();
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getContentType()
/*     */   {
/*     */     try
/*     */     {
/* 516 */       this.readySignal.await();
/*     */     } catch (Exception e) {
/*     */     }
/* 519 */     return this.contentType;
/*     */   }
/*     */ 
/*     */   public long getContentLength()
/*     */   {
/*     */     try
/*     */     {
/* 530 */       this.readySignal.await();
/*     */     } catch (Exception e) {
/*     */     }
/* 533 */     return this.contentLength;
/*     */   }
/*     */ 
/*     */   public URI getURI()
/*     */   {
/* 543 */     return this.uri;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 556 */     if (LocatorCache.locatorCache().isCached(this.uri)) {
/* 557 */       return "{LocatorURI uri: " + this.uri.toString() + " (media cached)}";
/*     */     }
/* 559 */     return "{LocatorURI uri: " + this.uri.toString() + "}";
/*     */   }
/*     */ 
/*     */   public String getStringLocation() {
/* 563 */     return this.uri.toString();
/*     */   }
/*     */ 
/*     */   public void setConnectionProperty(String property, Object value)
/*     */   {
/* 576 */     synchronized (this.propertyLock) {
/* 577 */       if (this.connectionProperties == null) {
/* 578 */         this.connectionProperties = new TreeMap();
/*     */       }
/*     */ 
/* 581 */       this.connectionProperties.put(property, value);
/*     */     }
/*     */   }
/*     */ 
/*     */   public ConnectionHolder createConnectionHolder() throws IOException
/*     */   {
/* 587 */     if (null != this.cacheEntry) {
/* 588 */       if (Logger.canLog(1)) {
/* 589 */         Logger.logMsg(1, "Locator.createConnectionHolder: media cached, creating memory connection holder");
/*     */       }
/* 591 */       return ConnectionHolder.createMemoryConnectionHolder(this.cacheEntry.getBuffer());
/*     */     }
/*     */     ConnectionHolder holder;
/*     */     ConnectionHolder holder;
/* 596 */     if ("file".equals(this.scheme)) {
/* 597 */       holder = ConnectionHolder.createFileConnectionHolder(this.uri);
/*     */     }
/*     */     else
/*     */     {
/*     */       ConnectionHolder holder;
/* 598 */       if ((this.uri.toString().endsWith(".m3u8")) || (this.uri.toString().endsWith(".m3u")))
/* 599 */         holder = ConnectionHolder.createHLSConnectionHolder(this.uri);
/*     */       else {
/* 601 */         synchronized (this.propertyLock) {
/* 602 */           holder = ConnectionHolder.createURIConnectionHolder(this.uri, this.connectionProperties);
/*     */         }
/*     */       }
/*     */     }
/* 606 */     return holder;
/*     */   }
/*     */ 
/*     */   private String getContentTypeFromFileSignature(URI uri) throws MalformedURLException, IOException {
/* 610 */     InputStream stream = getInputStream(uri);
/* 611 */     byte[] signature = new byte[22];
/* 612 */     stream.read(signature);
/* 613 */     stream.close();
/*     */ 
/* 615 */     return MediaUtils.fileSignatureToContentType(signature);
/*     */   }
/*     */ 
/*     */   private static class LocatorConnection
/*     */   {
/* 155 */     public HttpURLConnection connection = null;
/* 156 */     public int responseCode = 200;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.media.jfxmedia.locator.Locator
 * JD-Core Version:    0.6.2
 */