/*     */ package com.sun.webpane.webkit.network;
/*     */ 
/*     */ import com.sun.webpane.platform.WebPage;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Arrays;
/*     */ import java.util.concurrent.LinkedBlockingQueue;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.ThreadPoolExecutor;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ public class NetworkContext
/*     */ {
/*  24 */   private static final Logger logger = Logger.getLogger(NetworkContext.class.getName());
/*     */   private static final int THREAD_POOL_SIZE = 20;
/*     */   private static final long THREAD_POOL_KEEP_ALIVE_TIME = 10000L;
/*     */   private static final int DEFAULT_HTTP_MAX_CONNECTIONS = 5;
/*     */   private static final int BYTE_BUFFER_SIZE = 40960;
/*  52 */   private static final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(20, 20, 10000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(), new URLLoaderThreadFactory(null));
/*     */ 
/*  65 */   private static final ByteBufferPool byteBufferPool = ByteBufferPool.newInstance(40960);
/*     */ 
/*     */   private NetworkContext()
/*     */   {
/*  73 */     throw new AssertionError();
/*     */   }
/*     */ 
/*     */   public static boolean canHandleURL(String paramString)
/*     */   {
/*  86 */     URL localURL = null;
/*     */     try {
/*  88 */       localURL = URLs.newURL(paramString);
/*     */     } catch (MalformedURLException localMalformedURLException) {
/*     */     }
/*  91 */     return localURL != null;
/*     */   }
/*     */ 
/*     */   private static URLLoader fwkLoad(WebPage paramWebPage, boolean paramBoolean, String paramString1, String paramString2, String paramString3, FormDataElement[] paramArrayOfFormDataElement, long paramLong)
/*     */   {
/* 105 */     if (logger.isLoggable(Level.FINEST)) {
/* 106 */       logger.log(Level.FINEST, String.format("webPage: [%s], asynchronous: [%s], url: [%s], method: [%s], formDataElements: %s, data: [0x%016X], headers:%n%s", new Object[] { paramWebPage, Boolean.valueOf(paramBoolean), paramString1, paramString2, paramArrayOfFormDataElement != null ? Arrays.asList(paramArrayOfFormDataElement) : "[null]", Long.valueOf(paramLong), paramString3.trim().replaceAll("(?m)^", "    ") }));
/*     */     }
/*     */ 
/* 123 */     URLLoader localURLLoader = new URLLoader(paramWebPage, byteBufferPool, paramBoolean, paramString1, paramString2, paramString3, paramArrayOfFormDataElement, paramLong);
/*     */ 
/* 132 */     if (paramBoolean) {
/* 133 */       threadPool.submit(localURLLoader);
/* 134 */       if (logger.isLoggable(Level.FINEST)) {
/* 135 */         logger.log(Level.FINEST, "active count: [{0}], pool size: [{1}], max pool size: [{2}], task count: [{3}], completed task count: [{4}]", new Object[] { Integer.valueOf(threadPool.getActiveCount()), Integer.valueOf(threadPool.getPoolSize()), Integer.valueOf(threadPool.getMaximumPoolSize()), Long.valueOf(threadPool.getTaskCount()), Long.valueOf(threadPool.getCompletedTaskCount()) });
/*     */       }
/*     */ 
/* 148 */       return localURLLoader;
/*     */     }
/* 150 */     localURLLoader.run();
/* 151 */     return null;
/*     */   }
/*     */ 
/*     */   private static int fwkGetMaximumHTTPConnectionCountPerHost()
/*     */   {
/* 162 */     int i = ((Integer)AccessController.doPrivileged(new PrivilegedAction()
/*     */     {
/*     */       public Integer run()
/*     */       {
/* 166 */         return Integer.getInteger("http.maxConnections", -1);
/*     */       }
/*     */     })).intValue();
/*     */ 
/* 169 */     return i >= 0 ? i : 5;
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  59 */     threadPool.allowCoreThreadTimeOut(true);
/*     */   }
/*     */ 
/*     */   private static class URLLoaderThreadFactory
/*     */     implements ThreadFactory
/*     */   {
/*     */     private final ThreadGroup group;
/* 177 */     private final AtomicInteger index = new AtomicInteger(1);
/*     */ 
/*     */     private URLLoaderThreadFactory() {
/* 180 */       SecurityManager localSecurityManager = System.getSecurityManager();
/* 181 */       this.group = (localSecurityManager != null ? localSecurityManager.getThreadGroup() : Thread.currentThread().getThreadGroup());
/*     */     }
/*     */ 
/*     */     public Thread newThread(Runnable paramRunnable)
/*     */     {
/* 187 */       Thread localThread = new Thread(this.group, paramRunnable, "URL-Loader-" + this.index.getAndIncrement());
/*     */ 
/* 189 */       localThread.setDaemon(true);
/* 190 */       if (localThread.getPriority() != 5) {
/* 191 */         localThread.setPriority(5);
/*     */       }
/* 193 */       return localThread;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.network.NetworkContext
 * JD-Core Version:    0.6.2
 */