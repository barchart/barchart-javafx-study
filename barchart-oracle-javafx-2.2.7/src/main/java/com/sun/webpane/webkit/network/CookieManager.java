/*     */ package com.sun.webpane.webkit.network;
/*     */ 
/*     */ import java.net.CookieHandler;
/*     */ import java.net.URI;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ public final class CookieManager extends CookieHandler
/*     */ {
/*  22 */   private static final Logger logger = Logger.getLogger(CookieManager.class.getName());
/*     */ 
/*  26 */   private final CookieStore store = new CookieStore();
/*     */ 
/*     */   public Map<String, List<String>> get(URI paramURI, Map<String, List<String>> paramMap)
/*     */   {
/*  43 */     if (logger.isLoggable(Level.FINEST)) {
/*  44 */       logger.log(Level.FINEST, "uri: [{0}], requestHeaders: {1}", new Object[] { paramURI, toLogString(paramMap) });
/*     */     }
/*     */ 
/*  48 */     if (paramURI == null) {
/*  49 */       throw new IllegalArgumentException("uri is null");
/*     */     }
/*  51 */     if (paramMap == null) {
/*  52 */       throw new IllegalArgumentException("requestHeaders is null");
/*     */     }
/*     */ 
/*  55 */     String str = get(paramURI);
/*     */     Object localObject;
/*  58 */     if (str != null) {
/*  59 */       localObject = new HashMap();
/*  60 */       ((Map)localObject).put("Cookie", Arrays.asList(new String[] { str }));
/*     */     } else {
/*  62 */       localObject = Collections.emptyMap();
/*     */     }
/*  64 */     if (logger.isLoggable(Level.FINEST)) {
/*  65 */       logger.log(Level.FINEST, "result: {0}", toLogString((Map)localObject));
/*     */     }
/*  67 */     return localObject;
/*     */   }
/*     */ 
/*     */   private String get(URI paramURI)
/*     */   {
/*  74 */     String str1 = paramURI.getHost();
/*  75 */     if ((str1 == null) || (str1.length() == 0)) {
/*  76 */       logger.log(Level.FINEST, "Null or empty URI host, returning null");
/*  77 */       return null;
/*     */     }
/*  79 */     str1 = canonicalize(str1);
/*     */ 
/*  81 */     String str2 = paramURI.getScheme();
/*  82 */     boolean bool1 = "https".equalsIgnoreCase(str2);
/*  83 */     boolean bool2 = ("http".equalsIgnoreCase(str2)) || ("https".equalsIgnoreCase(str2));
/*     */     List localList;
/*  87 */     synchronized (this.store) {
/*  88 */       localList = this.store.get(str1, paramURI.getPath(), bool1, bool2);
/*     */     }
/*     */ 
/*  92 */     ??? = new StringBuilder();
/*  93 */     for (Cookie localCookie : localList) {
/*  94 */       if (((StringBuilder)???).length() > 0) {
/*  95 */         ((StringBuilder)???).append("; ");
/*     */       }
/*  97 */       ((StringBuilder)???).append(localCookie.getName());
/*  98 */       ((StringBuilder)???).append('=');
/*  99 */       ((StringBuilder)???).append(localCookie.getValue());
/*     */     }
/*     */ 
/* 102 */     return ((StringBuilder)???).length() > 0 ? ((StringBuilder)???).toString() : null;
/*     */   }
/*     */ 
/*     */   public void put(URI paramURI, Map<String, List<String>> paramMap)
/*     */   {
/* 110 */     if (logger.isLoggable(Level.FINEST)) {
/* 111 */       logger.log(Level.FINEST, "uri: [{0}], responseHeaders: {1}", new Object[] { paramURI, toLogString(paramMap) });
/*     */     }
/*     */ 
/* 115 */     if (paramURI == null) {
/* 116 */       throw new IllegalArgumentException("uri is null");
/*     */     }
/* 118 */     if (paramMap == null) {
/* 119 */       throw new IllegalArgumentException("responseHeaders is null");
/*     */     }
/*     */ 
/* 122 */     for (Map.Entry localEntry : paramMap.entrySet())
/*     */     {
/* 124 */       String str = (String)localEntry.getKey();
/* 125 */       if ("Set-Cookie".equalsIgnoreCase(str))
/*     */       {
/* 128 */         ExtendedTime localExtendedTime = ExtendedTime.currentTime();
/*     */ 
/* 133 */         ListIterator localListIterator = ((List)localEntry.getValue()).listIterator(((List)localEntry.getValue()).size());
/*     */ 
/* 135 */         while (localListIterator.hasPrevious()) {
/* 136 */           Cookie localCookie = Cookie.parse((String)localListIterator.previous(), localExtendedTime);
/* 137 */           if (localCookie != null) {
/* 138 */             put(paramURI, localCookie);
/* 139 */             localExtendedTime = localExtendedTime.incrementSubtime();
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void put(URI paramURI, Cookie paramCookie)
/*     */   {
/* 149 */     logger.log(Level.FINEST, "cookie: {0}", paramCookie);
/*     */ 
/* 151 */     String str = paramURI.getHost();
/* 152 */     if ((str == null) || (str.length() == 0)) {
/* 153 */       logger.log(Level.FINEST, "Null or empty URI host, ignoring cookie");
/* 154 */       return;
/*     */     }
/* 156 */     str = canonicalize(str);
/*     */ 
/* 158 */     if (PublicSuffixes.isPublicSuffix(paramCookie.getDomain())) {
/* 159 */       if (paramCookie.getDomain().equals(str)) {
/* 160 */         paramCookie.setDomain("");
/*     */       } else {
/* 162 */         logger.log(Level.FINEST, "Domain is public suffix, ignoring cookie");
/*     */ 
/* 164 */         return;
/*     */       }
/*     */     }
/*     */ 
/* 168 */     if (paramCookie.getDomain().length() > 0) {
/* 169 */       if (!Cookie.domainMatches(str, paramCookie.getDomain())) {
/* 170 */         logger.log(Level.FINEST, "Hostname does not match domain, ignoring cookie");
/*     */ 
/* 172 */         return;
/*     */       }
/* 174 */       paramCookie.setHostOnly(false);
/*     */     }
/*     */     else {
/* 177 */       paramCookie.setHostOnly(true);
/* 178 */       paramCookie.setDomain(str);
/*     */     }
/*     */ 
/* 181 */     if (paramCookie.getPath() == null) {
/* 182 */       paramCookie.setPath(Cookie.defaultPath(paramURI));
/*     */     }
/*     */ 
/* 185 */     int i = ("http".equalsIgnoreCase(paramURI.getScheme())) || ("https".equalsIgnoreCase(paramURI.getScheme())) ? 1 : 0;
/*     */ 
/* 187 */     if ((paramCookie.getHttpOnly()) && (i == 0)) {
/* 188 */       logger.log(Level.FINEST, "HttpOnly cookie received from non-HTTP API, ignoring cookie");
/*     */ 
/* 190 */       return;
/*     */     }
/*     */ 
/* 193 */     synchronized (this.store) {
/* 194 */       Cookie localCookie = this.store.get(paramCookie);
/* 195 */       if (localCookie != null) {
/* 196 */         if ((localCookie.getHttpOnly()) && (i == 0)) {
/* 197 */           logger.log(Level.FINEST, "Non-HTTP API attempts to overwrite HttpOnly cookie, blocked");
/*     */ 
/* 199 */           return;
/*     */         }
/* 201 */         paramCookie.setCreationTime(localCookie.getCreationTime());
/*     */       }
/*     */ 
/* 204 */       this.store.put(paramCookie);
/*     */     }
/*     */ 
/* 207 */     logger.log(Level.FINEST, "Stored: {0}", paramCookie);
/*     */   }
/*     */ 
/*     */   private static String toLogString(Map<String, List<String>> paramMap)
/*     */   {
/* 215 */     if (paramMap == null) {
/* 216 */       return null;
/*     */     }
/* 218 */     if (paramMap.isEmpty()) {
/* 219 */       return "{}";
/*     */     }
/* 221 */     StringBuilder localStringBuilder = new StringBuilder();
/* 222 */     for (Map.Entry localEntry : paramMap.entrySet()) {
/* 223 */       str1 = (String)localEntry.getKey();
/* 224 */       for (String str2 : (List)localEntry.getValue()) {
/* 225 */         localStringBuilder.append(String.format("%n    ", new Object[0]));
/* 226 */         localStringBuilder.append(str1);
/* 227 */         localStringBuilder.append(": ");
/* 228 */         localStringBuilder.append(str2);
/*     */       }
/*     */     }
/*     */     String str1;
/* 231 */     return localStringBuilder.toString();
/*     */   }
/*     */ 
/*     */   private static String canonicalize(String paramString)
/*     */   {
/* 239 */     return paramString.toLowerCase();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.network.CookieManager
 * JD-Core Version:    0.6.2
 */