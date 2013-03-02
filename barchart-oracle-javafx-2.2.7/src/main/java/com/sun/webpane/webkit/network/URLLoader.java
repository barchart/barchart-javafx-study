/*     */ package com.sun.webpane.webkit.network;
/*     */ 
/*     */ import com.sun.webpane.platform.Invoker;
/*     */ import com.sun.webpane.platform.WebPage;
/*     */ import java.io.EOFException;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.ConnectException;
/*     */ import java.net.HttpRetryException;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.NoRouteToHostException;
/*     */ import java.net.SocketException;
/*     */ import java.net.SocketTimeoutException;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.net.UnknownHostException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.security.AccessControlException;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CountDownLatch;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import java.util.zip.GZIPInputStream;
/*     */ import java.util.zip.InflaterInputStream;
/*     */ import javax.net.ssl.SSLHandshakeException;
/*     */ import sun.net.www.ParseUtil;
/*     */ 
/*     */ public class URLLoader
/*     */   implements Runnable
/*     */ {
/*  46 */   private static final Logger logger = Logger.getLogger(URLLoader.class.getName());
/*     */   private static final int MAX_REDIRECTS = 10;
/*     */   private static final int MAX_BUF_COUNT = 3;
/*     */   private static final String GET = "GET";
/*     */   private static final String HEAD = "HEAD";
/*     */   private static final String DELETE = "DELETE";
/*     */   private final WebPage webPage;
/*     */   private final ByteBufferPool byteBufferPool;
/*     */   private final boolean asynchronous;
/*     */   private String url;
/*     */   private String method;
/*     */   private final String headers;
/*     */   private FormDataElement[] formDataElements;
/*     */   private final long data;
/*  63 */   private volatile boolean canceled = false;
/*     */ 
/*     */   URLLoader(WebPage paramWebPage, ByteBufferPool paramByteBufferPool, boolean paramBoolean, String paramString1, String paramString2, String paramString3, FormDataElement[] paramArrayOfFormDataElement, long paramLong)
/*     */   {
/*  78 */     this.webPage = paramWebPage;
/*  79 */     this.byteBufferPool = paramByteBufferPool;
/*  80 */     this.asynchronous = paramBoolean;
/*  81 */     this.url = paramString1;
/*  82 */     this.method = paramString2;
/*  83 */     this.headers = paramString3;
/*  84 */     this.formDataElements = paramArrayOfFormDataElement;
/*  85 */     this.data = paramLong;
/*     */   }
/*     */ 
/*     */   private void fwkCancel()
/*     */   {
/*  93 */     logger.log(Level.FINEST, "Canceling: [{0}]", this.url);
/*  94 */     this.canceled = true;
/*     */   }
/*     */ 
/*     */   public void run()
/*     */   {
/* 103 */     AccessController.doPrivileged(new PrivilegedAction()
/*     */     {
/*     */       public Void run() {
/* 106 */         URLLoader.this.doRun();
/* 107 */         return null;
/*     */       }
/*     */     }
/*     */     , this.webPage.getAccessControlContext());
/*     */   }
/*     */ 
/*     */   private void doRun()
/*     */   {
/* 116 */     Object localObject1 = null;
/* 117 */     int i = 0;
/*     */     try {
/* 119 */       int j = 0;
/* 120 */       boolean bool = true;
/*     */       while (true)
/*     */       {
/* 123 */         String str1 = this.url;
/* 124 */         if (this.url.startsWith("file:")) {
/* 125 */           int k = this.url.indexOf(63);
/* 126 */           if (k != -1) {
/* 127 */             str1 = this.url.substring(0, k);
/*     */           }
/*     */         }
/*     */ 
/* 131 */         URL localURL = URLs.newURL(str1);
/*     */ 
/* 134 */         workaround7177996(localURL);
/*     */ 
/* 136 */         URLConnection localURLConnection = localURL.openConnection();
/* 137 */         prepareConnection(localURLConnection);
/*     */ 
/* 139 */         Redirect localRedirect = null;
/*     */         try {
/* 141 */           sendRequest(localURLConnection, bool);
/* 142 */           localRedirect = receiveResponse(localURLConnection);
/*     */         }
/*     */         catch (HttpRetryException localHttpRetryException) {
/* 145 */           if (bool) {
/* 146 */             bool = false;
/*     */ 
/* 152 */             close(localURLConnection); continue;
/*     */           }
/* 149 */           throw localHttpRetryException;
/*     */         }
/*     */         finally {
/* 152 */           close(localURLConnection);
/*     */         }
/*     */ 
/* 155 */         if (localRedirect == null) break;
/* 156 */         if (j++ >= 10) {
/* 157 */           throw new TooManyRedirectsException(null);
/*     */         }
/* 159 */         int m = (!localRedirect.preserveRequest) && (!this.method.equals("GET")) && (!this.method.equals("HEAD")) ? 1 : 0;
/*     */ 
/* 161 */         String str2 = m != 0 ? "GET" : this.method;
/* 162 */         willSendRequest(localRedirect.url, str2, localURLConnection);
/*     */ 
/* 164 */         if (this.canceled) {
/*     */           break;
/*     */         }
/* 167 */         this.url = localRedirect.url;
/* 168 */         this.method = str2;
/* 169 */         this.formDataElements = (m != 0 ? null : this.formDataElements);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (MalformedURLException localMalformedURLException)
/*     */     {
/* 175 */       localObject1 = localMalformedURLException;
/* 176 */       i = 2;
/*     */     } catch (AccessControlException localAccessControlException) {
/* 178 */       localObject1 = localAccessControlException;
/* 179 */       i = 8;
/*     */     } catch (UnknownHostException localUnknownHostException) {
/* 181 */       localObject1 = localUnknownHostException;
/* 182 */       i = 1;
/*     */     } catch (NoRouteToHostException localNoRouteToHostException) {
/* 184 */       localObject1 = localNoRouteToHostException;
/* 185 */       i = 6;
/*     */     } catch (ConnectException localConnectException) {
/* 187 */       localObject1 = localConnectException;
/* 188 */       i = 4;
/*     */     } catch (SocketException localSocketException) {
/* 190 */       localObject1 = localSocketException;
/* 191 */       i = 5;
/*     */     } catch (SSLHandshakeException localSSLHandshakeException) {
/* 193 */       localObject1 = localSSLHandshakeException;
/* 194 */       i = 3;
/*     */     } catch (SocketTimeoutException localSocketTimeoutException) {
/* 196 */       localObject1 = localSocketTimeoutException;
/* 197 */       i = 7;
/*     */     } catch (InvalidResponseException localInvalidResponseException) {
/* 199 */       localObject1 = localInvalidResponseException;
/* 200 */       i = 9;
/*     */     } catch (TooManyRedirectsException localTooManyRedirectsException) {
/* 202 */       localObject1 = localTooManyRedirectsException;
/* 203 */       i = 10;
/*     */     } catch (FileNotFoundException localFileNotFoundException) {
/* 205 */       localObject1 = localFileNotFoundException;
/* 206 */       i = 11;
/*     */     } catch (Throwable localThrowable) {
/* 208 */       localObject1 = localThrowable;
/* 209 */       i = 99;
/*     */     }
/*     */ 
/* 212 */     if (localObject1 != null) {
/* 213 */       if (i == 99)
/* 214 */         logger.log(Level.WARNING, "Unexpected error", localObject1);
/*     */       else {
/* 216 */         logger.log(Level.FINEST, "Load error", localObject1);
/*     */       }
/* 218 */       didFail(i, localObject1.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void workaround7177996(URL paramURL)
/*     */     throws FileNotFoundException
/*     */   {
/* 225 */     if (!paramURL.getProtocol().equals("file")) {
/* 226 */       return;
/*     */     }
/*     */ 
/* 229 */     String str1 = paramURL.getHost();
/* 230 */     if ((str1 == null) || (str1.equals("")) || (str1.equals("~")) || (str1.equalsIgnoreCase("localhost")))
/*     */     {
/* 233 */       return;
/*     */     }
/*     */ 
/* 236 */     if (System.getProperty("os.name").startsWith("Windows")) {
/* 237 */       String str2 = ParseUtil.decode(paramURL.getPath());
/* 238 */       str2 = str2.replace('/', '\\');
/* 239 */       str2 = str2.replace('|', ':');
/* 240 */       File localFile = new File(new StringBuilder().append("\\\\").append(str1).append(str2).toString());
/* 241 */       if (!localFile.exists())
/* 242 */         throw new FileNotFoundException(new StringBuilder().append("File not found: ").append(paramURL).toString());
/*     */     }
/*     */     else {
/* 245 */       throw new FileNotFoundException(new StringBuilder().append("File not found: ").append(paramURL).toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   private void prepareConnection(URLConnection paramURLConnection)
/*     */     throws IOException
/*     */   {
/* 255 */     paramURLConnection.setConnectTimeout(30000);
/* 256 */     paramURLConnection.setReadTimeout(3600000);
/*     */ 
/* 263 */     paramURLConnection.setUseCaches(false);
/*     */ 
/* 265 */     paramURLConnection.setRequestProperty("Accept-Language", "en-us,en;q=0.5");
/* 266 */     paramURLConnection.setRequestProperty("Accept-Encoding", "gzip");
/* 267 */     paramURLConnection.setRequestProperty("Accept-Charset", "ISO-8859-1,utf-8;q=0.7,*;q=0.7");
/*     */ 
/* 269 */     if ((this.headers != null) && (this.headers.length() > 0)) {
/* 270 */       for (Object localObject2 : this.headers.split("\n")) {
/* 271 */         int k = localObject2.indexOf(58);
/* 272 */         if (k > 0) {
/* 273 */           paramURLConnection.addRequestProperty(localObject2.substring(0, k), localObject2.substring(k + 2));
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 278 */     if ((paramURLConnection instanceof HttpURLConnection)) {
/* 279 */       ??? = (HttpURLConnection)paramURLConnection;
/* 280 */       ((HttpURLConnection)???).setRequestMethod(this.method);
/*     */ 
/* 283 */       ((HttpURLConnection)???).setInstanceFollowRedirects(false);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void sendRequest(URLConnection paramURLConnection, boolean paramBoolean)
/*     */     throws IOException
/*     */   {
/* 293 */     OutputStream localOutputStream = null;
/*     */     try {
/* 295 */       long l1 = 0L;
/* 296 */       j = (this.formDataElements != null) && ((paramURLConnection instanceof HttpURLConnection)) && (!this.method.equals("DELETE")) ? 1 : 0;
/*     */ 
/* 299 */       if (j != 0) {
/* 300 */         paramURLConnection.setDoOutput(true);
/*     */ 
/* 302 */         for (Object localObject2 : this.formDataElements) {
/* 303 */           localObject2.open();
/* 304 */           l1 += localObject2.getSize();
/*     */         }
/*     */ 
/* 307 */         if (paramBoolean) {
/* 308 */           ??? = (HttpURLConnection)paramURLConnection;
/* 309 */           if (l1 <= 2147483647L)
/* 310 */             ((HttpURLConnection)???).setFixedLengthStreamingMode((int)l1);
/*     */           else {
/* 312 */             ((HttpURLConnection)???).setChunkedStreamingMode(0);
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 317 */       int k = (this.method.equals("GET")) || (this.method.equals("HEAD")) ? 3 : 1;
/* 318 */       paramURLConnection.setConnectTimeout(paramURLConnection.getConnectTimeout() / k);
/* 319 */       ??? = 0;
/* 320 */       while (!this.canceled) {
/*     */         try {
/* 322 */           paramURLConnection.connect();
/*     */         }
/*     */         catch (SocketTimeoutException localSocketTimeoutException) {
/* 325 */           ???++; if (??? >= k)
/* 326 */             throw localSocketTimeoutException;
/*     */         }
/*     */         catch (IllegalArgumentException localIllegalArgumentException)
/*     */         {
/* 330 */           throw new MalformedURLException(this.url);
/*     */         }
/*     */       }
/*     */ 
/* 334 */       if (j != 0) {
/* 335 */         localOutputStream = paramURLConnection.getOutputStream();
/* 336 */         byte[] arrayOfByte = new byte[4096];
/* 337 */         long l2 = 0L;
/* 338 */         for (FormDataElement localFormDataElement2 : this.formDataElements) {
/* 339 */           InputStream localInputStream = localFormDataElement2.getInputStream();
/*     */           int i3;
/* 341 */           while ((i3 = localInputStream.read(arrayOfByte)) > 0) {
/* 342 */             localOutputStream.write(arrayOfByte, 0, i3);
/* 343 */             l2 += i3;
/* 344 */             didSendData(l2, l1);
/*     */           }
/* 346 */           localFormDataElement2.close();
/*     */         }
/* 348 */         localOutputStream.flush();
/* 349 */         localOutputStream.close();
/* 350 */         localOutputStream = null;
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/*     */       int j;
/*     */       FormDataElement[] arrayOfFormDataElement1;
/*     */       int i;
/*     */       FormDataElement localFormDataElement1;
/* 353 */       if (localOutputStream != null)
/*     */         try {
/* 355 */           localOutputStream.close();
/*     */         } catch (IOException localIOException3) {
/*     */         }
/* 358 */       if ((this.formDataElements != null) && ((paramURLConnection instanceof HttpURLConnection)))
/* 359 */         for (FormDataElement localFormDataElement3 : this.formDataElements)
/*     */           try {
/* 361 */             localFormDataElement3.close();
/*     */           }
/*     */           catch (IOException localIOException4)
/*     */           {
/*     */           }
/*     */     }
/*     */   }
/*     */ 
/*     */   private Redirect receiveResponse(URLConnection paramURLConnection)
/*     */     throws IOException, InterruptedException
/*     */   {
/* 374 */     if (this.canceled) {
/* 375 */       return null;
/*     */     }
/*     */ 
/* 378 */     InputStream localInputStream = null;
/*     */     int j;
/* 380 */     if ((paramURLConnection instanceof HttpURLConnection)) {
/* 381 */       HttpURLConnection localHttpURLConnection = (HttpURLConnection)paramURLConnection;
/*     */ 
/* 383 */       j = localHttpURLConnection.getResponseCode();
/* 384 */       if (j == -1) {
/* 385 */         throw new InvalidResponseException(null);
/*     */       }
/*     */ 
/* 388 */       if (this.canceled) {
/* 389 */         return null;
/*     */       }
/*     */ 
/* 393 */       switch (j) {
/*     */       case 301:
/*     */       case 302:
/*     */       case 303:
/*     */       case 307:
/* 398 */         localObject2 = localHttpURLConnection.getHeaderField("Location");
/* 399 */         if (localObject2 != null)
/*     */         {
/*     */           try {
/* 402 */             localObject3 = URLs.newURL((String)localObject2);
/*     */           }
/*     */           catch (MalformedURLException localMalformedURLException)
/*     */           {
/* 406 */             localObject3 = URLs.newURL(paramURLConnection.getURL(), (String)localObject2);
/*     */           }
/* 408 */           return new Redirect(((URL)localObject3).toExternalForm(), j == 307, null);
/*     */         }
/*     */ 
/*     */         break;
/*     */       case 304:
/* 414 */         didReceiveResponse(paramURLConnection);
/* 415 */         didFinishLoading();
/* 416 */         return null;
/*     */       case 305:
/*     */       case 306:
/* 419 */       }if ((j >= 400) && (!this.method.equals("HEAD"))) {
/* 420 */         localInputStream = localHttpURLConnection.getErrorStream();
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 426 */     if ((this.url.startsWith("ftp:")) || (this.url.startsWith("ftps:"))) {
/* 427 */       int i = 0;
/* 428 */       j = 0;
/*     */ 
/* 431 */       localObject2 = paramURLConnection.getURL().getPath();
/* 432 */       if ((localObject2 == null) || (((String)localObject2).isEmpty()) || (((String)localObject2).endsWith("/")) || (((String)localObject2).contains(";type=d")))
/*     */       {
/* 435 */         i = 1;
/*     */       } else {
/* 437 */         localObject3 = paramURLConnection.getContentType();
/* 438 */         if (("text/plain".equalsIgnoreCase((String)localObject3)) || ("text/html".equalsIgnoreCase((String)localObject3)))
/*     */         {
/* 441 */           i = 1;
/* 442 */           j = 1;
/*     */         }
/*     */       }
/* 445 */       if (i != 0) {
/* 446 */         paramURLConnection = new DirectoryURLConnection(paramURLConnection, j);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 451 */     if ((this.url.startsWith("file:")) && 
/* 452 */       ("text/plain".equals(paramURLConnection.getContentType())) && (paramURLConnection.getHeaderField("content-length") == null))
/*     */     {
/* 456 */       paramURLConnection = new DirectoryURLConnection(paramURLConnection);
/*     */     }
/*     */ 
/* 460 */     didReceiveResponse(paramURLConnection);
/*     */ 
/* 462 */     if (this.method.equals("HEAD")) {
/* 463 */       didFinishLoading();
/* 464 */       return null;
/*     */     }
/*     */ 
/* 467 */     Object localObject1 = localInputStream == null ? paramURLConnection.getInputStream() : localInputStream;
/*     */ 
/* 469 */     String str = paramURLConnection.getContentEncoding();
/* 470 */     if ("gzip".equalsIgnoreCase(str))
/* 471 */       localObject1 = new GZIPInputStream((InputStream)localObject1);
/* 472 */     else if ("deflate".equalsIgnoreCase(str)) {
/* 473 */       localObject1 = new InflaterInputStream((InputStream)localObject1);
/*     */     }
/*     */ 
/* 476 */     Object localObject2 = this.byteBufferPool.newAllocator(3);
/*     */ 
/* 478 */     Object localObject3 = null;
/*     */     try
/*     */     {
/* 483 */       byte[] arrayOfByte = new byte[8192];
/* 484 */       while (!this.canceled) {
/*     */         int k;
/*     */         try {
/* 487 */           k = ((InputStream)localObject1).read(arrayOfByte);
/*     */         }
/*     */         catch (EOFException localEOFException)
/*     */         {
/* 491 */           k = -1;
/*     */         }
/*     */ 
/* 494 */         if (k == -1)
/*     */         {
/*     */           break;
/*     */         }
/* 498 */         if (localObject3 == null) {
/* 499 */           localObject3 = ((ByteBufferAllocator)localObject2).allocate();
/*     */         }
/*     */ 
/* 502 */         int m = ((ByteBuffer)localObject3).remaining();
/* 503 */         if (k < m) {
/* 504 */           ((ByteBuffer)localObject3).put(arrayOfByte, 0, k);
/*     */         } else {
/* 506 */           ((ByteBuffer)localObject3).put(arrayOfByte, 0, m);
/*     */ 
/* 508 */           ((ByteBuffer)localObject3).flip();
/* 509 */           didReceiveData((ByteBuffer)localObject3, (ByteBufferAllocator)localObject2);
/* 510 */           localObject3 = null;
/*     */ 
/* 512 */           int n = k - m;
/* 513 */           if (n > 0) {
/* 514 */             localObject3 = ((ByteBufferAllocator)localObject2).allocate();
/* 515 */             ((ByteBuffer)localObject3).put(arrayOfByte, m, n);
/*     */           }
/*     */         }
/*     */       }
/* 519 */       if (!this.canceled) {
/* 520 */         if ((localObject3 != null) && (((ByteBuffer)localObject3).position() > 0)) {
/* 521 */           ((ByteBuffer)localObject3).flip();
/* 522 */           didReceiveData((ByteBuffer)localObject3, (ByteBufferAllocator)localObject2);
/* 523 */           localObject3 = null;
/*     */         }
/* 525 */         didFinishLoading();
/*     */       }
/*     */     } finally {
/* 528 */       if (localObject3 != null) {
/* 529 */         ((ByteBuffer)localObject3).clear();
/* 530 */         ((ByteBufferAllocator)localObject2).release((ByteBuffer)localObject3);
/*     */       }
/*     */     }
/* 533 */     return null;
/*     */   }
/*     */ 
/*     */   private static void close(URLConnection paramURLConnection)
/*     */   {
/* 540 */     if ((paramURLConnection instanceof HttpURLConnection)) {
/* 541 */       InputStream localInputStream = ((HttpURLConnection)paramURLConnection).getErrorStream();
/* 542 */       if (localInputStream != null)
/*     */         try {
/* 544 */           localInputStream.close();
/*     */         } catch (IOException localIOException2) {
/*     */         }
/*     */     }
/*     */     try {
/* 549 */       paramURLConnection.getInputStream().close();
/*     */     }
/*     */     catch (IOException localIOException1)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   private void didSendData(final long paramLong1, long paramLong2)
/*     */   {
/* 589 */     callBack(new Runnable()
/*     */     {
/*     */       public void run() {
/* 592 */         if (!URLLoader.this.canceled)
/* 593 */           URLLoader.twkDidSendData(paramLong1, this.val$totalBytesToBeSent, URLLoader.this.data);
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   private void willSendRequest(String paramString1, final String paramString2, URLConnection paramURLConnection)
/*     */     throws InterruptedException
/*     */   {
/* 603 */     final String str1 = adjustUrlForWebKit(paramString1);
/* 604 */     final int i = extractStatus(paramURLConnection);
/* 605 */     final String str2 = paramURLConnection.getContentType();
/* 606 */     final String str3 = extractContentEncoding(paramURLConnection);
/* 607 */     final long l = extractContentLength(paramURLConnection);
/* 608 */     String str4 = extractHeaders(paramURLConnection);
/* 609 */     final String str5 = adjustUrlForWebKit(this.url);
/* 610 */     final CountDownLatch localCountDownLatch = this.asynchronous ? new CountDownLatch(1) : null;
/*     */ 
/* 612 */     callBack(new Runnable()
/*     */     {
/*     */       public void run() {
/*     */         try {
/* 616 */           if (!URLLoader.this.canceled) {
/* 617 */             boolean bool = URLLoader.twkWillSendRequest(str1, paramString2, i, str2, str3, l, str5, localCountDownLatch, URLLoader.this.data);
/*     */ 
/* 627 */             if (!bool)
/* 628 */               URLLoader.this.fwkCancel();
/*     */           }
/*     */         }
/*     */         finally {
/* 632 */           if (this.val$latch != null)
/* 633 */             this.val$latch.countDown();
/*     */         }
/*     */       }
/*     */     });
/* 638 */     if (localCountDownLatch != null)
/* 639 */       localCountDownLatch.await();
/*     */   }
/*     */ 
/*     */   private void didReceiveResponse(URLConnection paramURLConnection)
/*     */   {
/* 644 */     final int i = extractStatus(paramURLConnection);
/* 645 */     final String str1 = paramURLConnection.getContentType();
/* 646 */     final String str2 = extractContentEncoding(paramURLConnection);
/* 647 */     final long l = extractContentLength(paramURLConnection);
/* 648 */     String str3 = extractHeaders(paramURLConnection);
/* 649 */     final String str4 = adjustUrlForWebKit(this.url);
/* 650 */     callBack(new Runnable()
/*     */     {
/*     */       public void run() {
/* 653 */         if (!URLLoader.this.canceled)
/* 654 */           URLLoader.twkDidReceiveResponse(i, str1, str2, l, str4, this.val$adjustedUrl, URLLoader.this.data);
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   private void didReceiveData(final ByteBuffer paramByteBuffer, final ByteBufferAllocator paramByteBufferAllocator)
/*     */   {
/* 670 */     callBack(new Runnable()
/*     */     {
/*     */       public void run() {
/* 673 */         if (!URLLoader.this.canceled) {
/* 674 */           URLLoader.twkDidReceiveData(paramByteBuffer, paramByteBuffer.position(), paramByteBuffer.remaining(), URLLoader.this.data);
/*     */         }
/*     */ 
/* 680 */         paramByteBuffer.clear();
/* 681 */         paramByteBufferAllocator.release(paramByteBuffer);
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   private void didFinishLoading() {
/* 687 */     callBack(new Runnable()
/*     */     {
/*     */       public void run() {
/* 690 */         if (!URLLoader.this.canceled)
/* 691 */           URLLoader.twkDidFinishLoading(URLLoader.this.data);
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   private void didFail(final int paramInt, final String paramString)
/*     */   {
/* 698 */     final String str = adjustUrlForWebKit(this.url);
/* 699 */     callBack(new Runnable()
/*     */     {
/*     */       public void run() {
/* 702 */         if (!URLLoader.this.canceled)
/* 703 */           URLLoader.twkDidFail(paramInt, str, paramString, URLLoader.this.data);
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   private void callBack(Runnable paramRunnable)
/*     */   {
/* 710 */     if (this.asynchronous)
/* 711 */       Invoker.getInvoker().invokeOnEventThread(paramRunnable);
/*     */     else
/* 713 */       paramRunnable.run();
/*     */   }
/*     */ 
/*     */   private static native void twkDidSendData(long paramLong1, long paramLong2, long paramLong3);
/*     */ 
/*     */   private static native boolean twkWillSendRequest(String paramString1, String paramString2, int paramInt, String paramString3, String paramString4, long paramLong1, String paramString5, String paramString6, long paramLong2);
/*     */ 
/*     */   private static native void twkDidReceiveResponse(int paramInt, String paramString1, String paramString2, long paramLong1, String paramString3, String paramString4, long paramLong2);
/*     */ 
/*     */   private static native void twkDidReceiveData(ByteBuffer paramByteBuffer, int paramInt1, int paramInt2, long paramLong);
/*     */ 
/*     */   private static native void twkDidFinishLoading(long paramLong);
/*     */ 
/*     */   private static native void twkDidFail(int paramInt, String paramString1, String paramString2, long paramLong);
/*     */ 
/*     */   private static int extractStatus(URLConnection paramURLConnection)
/*     */   {
/* 756 */     int i = 0;
/* 757 */     if ((paramURLConnection instanceof HttpURLConnection))
/*     */       try {
/* 759 */         i = ((HttpURLConnection)paramURLConnection).getResponseCode();
/*     */       } catch (IOException localIOException) {
/*     */       }
/* 762 */     return i;
/*     */   }
/*     */ 
/*     */   private static String extractContentEncoding(URLConnection paramURLConnection)
/*     */   {
/* 770 */     String str1 = paramURLConnection.getContentEncoding();
/*     */ 
/* 772 */     if (("gzip".equalsIgnoreCase(str1)) || ("deflate".equalsIgnoreCase(str1)))
/*     */     {
/* 775 */       String str2 = paramURLConnection.getContentType();
/* 776 */       if (str2 != null) {
/* 777 */         int i = str2.indexOf("charset=");
/* 778 */         if (i >= 0) {
/* 779 */           str1 = str2.substring(i + 8);
/* 780 */           i = str1.indexOf(";");
/* 781 */           if (i > 0) {
/* 782 */             str1 = str1.substring(0, i);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 787 */     return str1;
/*     */   }
/*     */ 
/*     */   private static long extractContentLength(URLConnection paramURLConnection)
/*     */   {
/*     */     try
/*     */     {
/* 798 */       return Long.parseLong(paramURLConnection.getHeaderField("content-length")); } catch (Exception localException) {
/*     */     }
/* 800 */     return -1L;
/*     */   }
/*     */ 
/*     */   private static String extractHeaders(URLConnection paramURLConnection)
/*     */   {
/* 809 */     StringBuilder localStringBuilder = new StringBuilder();
/* 810 */     Map localMap = paramURLConnection.getHeaderFields();
/* 811 */     Set localSet = localMap.keySet();
/* 812 */     for (Iterator localIterator1 = localSet.iterator(); localIterator1.hasNext(); ) { str1 = (String)localIterator1.next();
/* 813 */       List localList = (List)localMap.get(str1);
/* 814 */       for (String str2 : localList) {
/* 815 */         localStringBuilder.append(str1 != null ? str1 : "");
/* 816 */         localStringBuilder.append(':').append(str2).append('\n');
/*     */       }
/*     */     }
/*     */     String str1;
/* 819 */     return localStringBuilder.toString();
/*     */   }
/*     */ 
/*     */   private static String adjustUrlForWebKit(String paramString)
/*     */   {
/*     */     try
/*     */     {
/* 827 */       paramString = Util.adjustUrlForWebKit(paramString);
/*     */     } catch (Exception localException) {
/*     */     }
/* 830 */     return paramString;
/*     */   }
/*     */ 
/*     */   private static class TooManyRedirectsException extends IOException
/*     */   {
/*     */     private TooManyRedirectsException()
/*     */     {
/* 582 */       super();
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class InvalidResponseException extends IOException
/*     */   {
/*     */     private InvalidResponseException()
/*     */     {
/* 572 */       super();
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class Redirect
/*     */   {
/*     */     private final String url;
/*     */     private final boolean preserveRequest;
/*     */ 
/*     */     private Redirect(String paramString, boolean paramBoolean)
/*     */     {
/* 562 */       this.url = paramString;
/* 563 */       this.preserveRequest = paramBoolean;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.network.URLLoader
 * JD-Core Version:    0.6.2
 */