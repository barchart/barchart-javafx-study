/*     */ package com.sun.javafx.runtime.async;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InterruptedIOException;
/*     */ import java.io.OutputStream;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ 
/*     */ public abstract class AbstractRemoteResource<T> extends AbstractAsyncOperation<T>
/*     */ {
/*     */   protected final String url;
/*     */   protected final String method;
/*     */   protected final String outboundContent;
/*     */   protected int fileSize;
/*  53 */   private Map<String, String> headers = new HashMap();
/*  54 */   private Map<String, List<String>> responseHeaders = new HashMap();
/*     */ 
/*     */   protected AbstractRemoteResource(String paramString, AsyncOperationListener<T> paramAsyncOperationListener) {
/*  57 */     this(paramString, "GET", paramAsyncOperationListener);
/*     */   }
/*     */ 
/*     */   protected AbstractRemoteResource(String paramString1, String paramString2, AsyncOperationListener<T> paramAsyncOperationListener) {
/*  61 */     this(paramString1, paramString2, null, paramAsyncOperationListener);
/*     */   }
/*     */ 
/*     */   protected AbstractRemoteResource(String paramString1, String paramString2, String paramString3, AsyncOperationListener<T> paramAsyncOperationListener) {
/*  65 */     super(paramAsyncOperationListener);
/*  66 */     this.url = paramString1;
/*  67 */     this.method = paramString2;
/*  68 */     this.outboundContent = paramString3;
/*     */   }
/*     */ 
/*     */   protected abstract T processStream(InputStream paramInputStream) throws IOException;
/*     */ 
/*     */   public T call() throws IOException {
/*  74 */     URL localURL = new URL(this.url);
/*  75 */     ProgressInputStream localProgressInputStream = null;
/*  76 */     String str1 = localURL.getProtocol();
/*     */     Object localObject1;
/*  77 */     if ((str1.equals("http")) || (str1.equals("https"))) {
/*  78 */       localObject1 = (HttpURLConnection)localURL.openConnection();
/*  79 */       ((HttpURLConnection)localObject1).setRequestMethod(this.method);
/*  80 */       ((HttpURLConnection)localObject1).setDoInput(true);
/*     */ 
/*  82 */       for (Object localObject2 = this.headers.entrySet().iterator(); ((Iterator)localObject2).hasNext(); ) { localObject3 = (Map.Entry)((Iterator)localObject2).next();
/*  83 */         String str2 = (String)((Map.Entry)localObject3).getKey();
/*  84 */         String str3 = (String)((Map.Entry)localObject3).getValue();
/*  85 */         if ((str3 != null) && (!str3.equals("")))
/*  86 */           ((HttpURLConnection)localObject1).setRequestProperty(str2, str3);
/*     */       }
/*     */       Object localObject3;
/*  88 */       if ((this.outboundContent != null) && (this.method.equals("POST"))) {
/*  89 */         ((HttpURLConnection)localObject1).setDoOutput(true);
/*  90 */         localObject2 = this.outboundContent.getBytes("utf-8");
/*  91 */         ((HttpURLConnection)localObject1).setRequestProperty("Content-Length", String.valueOf(localObject2.length));
/*  92 */         localObject3 = ((HttpURLConnection)localObject1).getOutputStream();
/*  93 */         ((OutputStream)localObject3).write((byte[])localObject2);
/*  94 */         ((OutputStream)localObject3).close();
/*     */       }
/*  96 */       ((HttpURLConnection)localObject1).connect();
/*  97 */       this.fileSize = ((HttpURLConnection)localObject1).getContentLength();
/*  98 */       setProgressMax(this.fileSize);
/*  99 */       this.responseHeaders = ((HttpURLConnection)localObject1).getHeaderFields();
/*     */ 
/* 101 */       localProgressInputStream = new ProgressInputStream(((HttpURLConnection)localObject1).getInputStream());
/*     */     } else {
/* 103 */       localObject1 = localURL.openConnection();
/* 104 */       setProgressMax(((URLConnection)localObject1).getContentLength());
/* 105 */       localProgressInputStream = new ProgressInputStream(((URLConnection)localObject1).getInputStream());
/*     */     }
/*     */     try
/*     */     {
/* 109 */       return processStream(localProgressInputStream);
/*     */     }
/*     */     finally {
/* 112 */       localProgressInputStream.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setHeader(String paramString1, String paramString2)
/*     */   {
/* 150 */     this.headers.put(paramString1, paramString2);
/*     */   }
/*     */ 
/*     */   public String getResponseHeader(String paramString) {
/* 154 */     String str = null;
/* 155 */     List localList = (List)this.responseHeaders.get(paramString);
/*     */ 
/* 157 */     if (localList != null) {
/* 158 */       StringBuilder localStringBuilder = new StringBuilder();
/* 159 */       Iterator localIterator = localList.iterator();
/* 160 */       while (localIterator.hasNext()) {
/* 161 */         localStringBuilder.append(localIterator.next());
/* 162 */         if (localIterator.hasNext()) {
/* 163 */           localStringBuilder.append(',');
/*     */         }
/*     */       }
/* 166 */       str = localStringBuilder.toString();
/*     */     }
/* 168 */     return str;
/*     */   }
/*     */ 
/*     */   protected class ProgressInputStream extends BufferedInputStream
/*     */   {
/*     */     public ProgressInputStream(InputStream arg2)
/*     */     {
/* 118 */       super();
/*     */     }
/*     */ 
/*     */     public synchronized int read() throws IOException
/*     */     {
/* 123 */       if (Thread.currentThread().isInterrupted())
/* 124 */         throw new InterruptedIOException();
/* 125 */       int i = super.read();
/* 126 */       AbstractRemoteResource.this.addProgress(1);
/* 127 */       return i;
/*     */     }
/*     */ 
/*     */     public synchronized int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException
/*     */     {
/* 132 */       if (Thread.currentThread().isInterrupted())
/* 133 */         throw new InterruptedIOException();
/* 134 */       int i = super.read(paramArrayOfByte, paramInt1, paramInt2);
/* 135 */       AbstractRemoteResource.this.addProgress(i);
/* 136 */       return i;
/*     */     }
/*     */ 
/*     */     public int read(byte[] paramArrayOfByte) throws IOException
/*     */     {
/* 141 */       if (Thread.currentThread().isInterrupted())
/* 142 */         throw new InterruptedIOException();
/* 143 */       int i = super.read(paramArrayOfByte);
/* 144 */       AbstractRemoteResource.this.addProgress(i);
/* 145 */       return i;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.runtime.async.AbstractRemoteResource
 * JD-Core Version:    0.6.2
 */