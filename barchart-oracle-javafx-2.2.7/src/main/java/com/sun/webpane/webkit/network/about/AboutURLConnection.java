/*     */ package com.sun.webpane.webkit.network.about;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.ProtocolException;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ 
/*     */ public class AboutURLConnection extends URLConnection
/*     */ {
/*  19 */   private static String DEFAULT_CHARSET = "UTF-8";
/*  20 */   private static String DEFAULT_MIMETYPE = "text/html";
/*     */   private AboutRecord record;
/*     */ 
/*     */   public AboutURLConnection(URL paramURL)
/*     */   {
/*  25 */     super(paramURL);
/*  26 */     this.record = new AboutRecord("");
/*     */   }
/*     */ 
/*     */   public void connect() throws IOException
/*     */   {
/*  31 */     if (this.connected) {
/*  32 */       return;
/*     */     }
/*  34 */     this.connected = (this.record != null);
/*  35 */     if (this.connected) {
/*  36 */       this.record.content.reset();
/*  37 */       return;
/*     */     }
/*  39 */     throw new ProtocolException("The URL is not valid and cannot be loaded.");
/*     */   }
/*     */ 
/*     */   public InputStream getInputStream()
/*     */     throws IOException
/*     */   {
/*  45 */     connect();
/*  46 */     return this.record.content;
/*     */   }
/*     */ 
/*     */   public String getContentType()
/*     */   {
/*     */     try
/*     */     {
/*  53 */       connect();
/*  54 */       if (this.record.contentType != null)
/*  55 */         return this.record.contentType;
/*     */     }
/*     */     catch (IOException localIOException) {
/*     */     }
/*  59 */     return DEFAULT_MIMETYPE;
/*     */   }
/*     */ 
/*     */   public String getContentEncoding()
/*     */   {
/*     */     try
/*     */     {
/*  66 */       connect();
/*  67 */       if (this.record.contentEncoding != null)
/*  68 */         return this.record.contentEncoding;
/*     */     }
/*     */     catch (IOException localIOException) {
/*     */     }
/*  72 */     return DEFAULT_CHARSET;
/*     */   }
/*     */ 
/*     */   public int getContentLength()
/*     */   {
/*     */     try
/*     */     {
/*  79 */       connect();
/*  80 */       return this.record.contentLength;
/*     */     } catch (IOException localIOException) {
/*     */     }
/*  83 */     return -1;
/*     */   }
/*     */ 
/*     */   class AboutRecord
/*     */   {
/*     */     public InputStream content;
/*     */     public int contentLength;
/*     */     public String contentEncoding;
/*     */     public String contentType;
/*     */ 
/*     */     public AboutRecord(InputStream paramInt, int paramString1, String paramString2, String arg5)
/*     */     {
/*  99 */       this.content = paramInt;
/* 100 */       this.contentLength = paramString1;
/* 101 */       this.contentEncoding = paramString2;
/*     */       Object localObject;
/* 102 */       this.contentType = localObject;
/*     */     }
/*     */ 
/*     */     public AboutRecord(String arg2)
/*     */     {
/*     */       try
/*     */       {
/*     */         Object localObject;
/* 107 */         byte[] arrayOfByte = localObject.getBytes(AboutURLConnection.DEFAULT_CHARSET);
/* 108 */         this.content = new ByteArrayInputStream(arrayOfByte);
/* 109 */         this.contentLength = arrayOfByte.length;
/* 110 */         this.contentEncoding = AboutURLConnection.DEFAULT_CHARSET;
/* 111 */         this.contentType = AboutURLConnection.DEFAULT_MIMETYPE;
/*     */       }
/*     */       catch (UnsupportedEncodingException localUnsupportedEncodingException)
/*     */       {
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.network.about.AboutURLConnection
 * JD-Core Version:    0.6.2
 */