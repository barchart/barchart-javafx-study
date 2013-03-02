/*     */ package com.sun.webpane.webkit.network.data;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.ProtocolException;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import sun.misc.BASE64Decoder;
/*     */ 
/*     */ public class DataURLConnection extends URLConnection
/*     */ {
/*  28 */   private static final Charset US_ASCII = Charset.forName("US-ASCII");
/*     */   private final String mediaType;
/*     */   private final byte[] data;
/*     */   private final InputStream inputStream;
/*     */ 
/*     */   protected DataURLConnection(URL paramURL)
/*     */     throws IOException
/*     */   {
/*  37 */     super(paramURL);
/*     */ 
/*  39 */     String str1 = paramURL.toString();
/*  40 */     str1 = str1.substring(str1.indexOf(58) + 1);
/*     */ 
/*  42 */     int i = str1.indexOf(44);
/*  43 */     if (i < 0) {
/*  44 */       throw new ProtocolException(new StringBuilder().append("Invalid URL, ',' not found in: ").append(getURL()).toString());
/*     */     }
/*     */ 
/*  48 */     String str2 = str1.substring(0, i);
/*  49 */     String str3 = str1.substring(i + 1);
/*     */ 
/*  51 */     Object localObject1 = null;
/*  52 */     LinkedList localLinkedList = new LinkedList();
/*  53 */     Charset localCharset = null;
/*  54 */     int j = 0;
/*     */ 
/*  56 */     String[] arrayOfString = str2.split(";", -1);
/*  57 */     for (int k = 0; k < arrayOfString.length; k++) {
/*  58 */       localObject2 = arrayOfString[k];
/*  59 */       if (((String)localObject2).equalsIgnoreCase("base64")) {
/*  60 */         j = 1;
/*     */       }
/*  62 */       else if ((k == 0) && (!((String)localObject2).contains("="))) {
/*  63 */         localObject1 = localObject2;
/*     */       } else {
/*  65 */         localLinkedList.add(localObject2);
/*  66 */         if (((String)localObject2).toLowerCase().startsWith("charset=")) {
/*     */           try {
/*  68 */             localCharset = Charset.forName(((String)localObject2).substring(8));
/*     */           } catch (IllegalArgumentException localIllegalArgumentException) {
/*  70 */             UnsupportedEncodingException localUnsupportedEncodingException = new UnsupportedEncodingException();
/*     */ 
/*  72 */             localUnsupportedEncodingException.initCause(localIllegalArgumentException);
/*  73 */             throw localUnsupportedEncodingException;
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  80 */     if ((localObject1 == null) || (((String)localObject1).isEmpty())) {
/*  81 */       localObject1 = "text/plain";
/*     */     }
/*     */ 
/*  84 */     if (localCharset == null) {
/*  85 */       localCharset = US_ASCII;
/*  86 */       if (((String)localObject1).toLowerCase().startsWith("text/")) {
/*  87 */         localLinkedList.addFirst(new StringBuilder().append("charset=").append(localCharset.name()).toString());
/*     */       }
/*     */     }
/*     */ 
/*  91 */     StringBuilder localStringBuilder = new StringBuilder();
/*  92 */     localStringBuilder.append((String)localObject1);
/*  93 */     for (Object localObject2 = localLinkedList.iterator(); ((Iterator)localObject2).hasNext(); ) { String str4 = (String)((Iterator)localObject2).next();
/*  94 */       localStringBuilder.append(';').append(str4);
/*     */     }
/*  96 */     this.mediaType = localStringBuilder.toString();
/*     */ 
/*  98 */     if (j != 0) {
/*  99 */       localObject2 = urlDecode(str3, US_ASCII);
/* 100 */       localObject2 = ((String)localObject2).replaceAll("\\s+", "");
/* 101 */       this.data = new BASE64Decoder().decodeBuffer((String)localObject2);
/*     */     } else {
/* 103 */       localObject2 = urlDecode(str3, localCharset);
/* 104 */       this.data = ((String)localObject2).getBytes(localCharset);
/*     */     }
/*     */ 
/* 107 */     this.inputStream = new ByteArrayInputStream(this.data);
/*     */   }
/*     */ 
/*     */   public void connect()
/*     */   {
/* 113 */     this.connected = true;
/*     */   }
/*     */ 
/*     */   public InputStream getInputStream()
/*     */   {
/* 118 */     return this.inputStream;
/*     */   }
/*     */ 
/*     */   public String getContentType()
/*     */   {
/* 123 */     return this.mediaType;
/*     */   }
/*     */ 
/*     */   public String getContentEncoding()
/*     */   {
/* 128 */     return null;
/*     */   }
/*     */ 
/*     */   public int getContentLength()
/*     */   {
/* 134 */     return this.data != null ? this.data.length : -1;
/*     */   }
/*     */ 
/*     */   private static String urlDecode(String paramString, Charset paramCharset) {
/* 138 */     int i = paramString.length();
/* 139 */     StringBuilder localStringBuilder = new StringBuilder(i);
/* 140 */     byte[] arrayOfByte = null;
/*     */ 
/* 142 */     int j = 0;
/* 143 */     while (j < i) {
/* 144 */       char c = paramString.charAt(j);
/* 145 */       if (c == '%')
/*     */       {
/* 147 */         if (arrayOfByte == null) {
/* 148 */           arrayOfByte = new byte[(i - j) / 3];
/*     */         }
/* 150 */         int k = 0;
/* 151 */         int m = j;
/* 152 */         for (; j < i; j += 3) {
/* 153 */           c = paramString.charAt(j);
/* 154 */           if (c != '%')
/*     */           {
/*     */             break;
/*     */           }
/* 158 */           if (j + 2 >= i)
/*     */           {
/* 162 */             m = i;
/* 163 */             break;
/*     */           }
/*     */           int n;
/*     */           try {
/* 167 */             n = (byte)Integer.parseInt(paramString.substring(j + 1, j + 3), 16);
/*     */           }
/*     */           catch (NumberFormatException localNumberFormatException)
/*     */           {
/* 172 */             m = j + 3;
/* 173 */             break;
/*     */           }
/* 175 */           arrayOfByte[(k++)] = n;
/*     */         }
/* 177 */         if (k > 0) {
/* 178 */           localStringBuilder.append(new String(arrayOfByte, 0, k, paramCharset));
/*     */         }
/* 180 */         while (j < m)
/* 181 */           localStringBuilder.append(paramString.charAt(j++));
/*     */       }
/*     */       else {
/* 184 */         localStringBuilder.append(c);
/* 185 */         j++;
/*     */       }
/*     */     }
/*     */ 
/* 189 */     return localStringBuilder.toString();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.network.data.DataURLConnection
 * JD-Core Version:    0.6.2
 */