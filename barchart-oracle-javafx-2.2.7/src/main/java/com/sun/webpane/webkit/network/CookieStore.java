/*     */ package com.sun.webpane.webkit.network;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.PriorityQueue;
/*     */ import java.util.Queue;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ final class CookieStore
/*     */ {
/*  24 */   private static final Logger logger = Logger.getLogger(CookieStore.class.getName());
/*     */   private static final int MAX_BUCKET_SIZE = 50;
/*     */   private static final int TOTAL_COUNT_LOWER_THRESHOLD = 3000;
/*     */   private static final int TOTAL_COUNT_UPPER_THRESHOLD = 4000;
/*  39 */   private final Map<String, Map<Cookie, Cookie>> buckets = new HashMap();
/*     */ 
/*  45 */   private int totalCount = 0;
/*     */ 
/*     */   Cookie get(Cookie paramCookie)
/*     */   {
/*  60 */     Map localMap = (Map)this.buckets.get(paramCookie.getDomain());
/*  61 */     if (localMap == null) {
/*  62 */       return null;
/*     */     }
/*  64 */     Cookie localCookie = (Cookie)localMap.get(paramCookie);
/*  65 */     if (localCookie == null) {
/*  66 */       return null;
/*     */     }
/*  68 */     if (localCookie.hasExpired()) {
/*  69 */       localMap.remove(localCookie);
/*  70 */       this.totalCount -= 1;
/*  71 */       log("Expired cookie removed by get", localCookie, localMap);
/*  72 */       return null;
/*     */     }
/*  74 */     return localCookie;
/*     */   }
/*     */ 
/*     */   List<Cookie> get(String paramString1, String paramString2, boolean paramBoolean1, boolean paramBoolean2)
/*     */   {
/*  84 */     if (logger.isLoggable(Level.FINEST)) {
/*  85 */       logger.log(Level.FINEST, "hostname: [{0}], path: [{1}], secureProtocol: [{2}], httpApi: [{3}]", new Object[] { paramString1, paramString2, Boolean.valueOf(paramBoolean1), Boolean.valueOf(paramBoolean2) });
/*     */     }
/*     */ 
/*  90 */     ArrayList localArrayList = new ArrayList();
/*     */ 
/*  92 */     String str = paramString1;
/*  93 */     while (str.length() > 0) {
/*  94 */       Map localMap = (Map)this.buckets.get(str);
/*  95 */       if (localMap != null) {
/*  96 */         find(localArrayList, localMap, paramString1, paramString2, paramBoolean1, paramBoolean2);
/*     */       }
/*  98 */       int i = str.indexOf('.');
/*  99 */       if (i == -1) break;
/* 100 */       str = str.substring(i + 1);
/*     */     }
/*     */ 
/* 106 */     Collections.sort(localArrayList, new GetComparator(null));
/*     */ 
/* 108 */     long l = System.currentTimeMillis();
/* 109 */     for (Cookie localCookie : localArrayList) {
/* 110 */       localCookie.setLastAccessTime(l);
/*     */     }
/*     */ 
/* 113 */     logger.log(Level.FINEST, "result: {0}", localArrayList);
/* 114 */     return localArrayList;
/*     */   }
/*     */ 
/*     */   private void find(List<Cookie> paramList, Map<Cookie, Cookie> paramMap, String paramString1, String paramString2, boolean paramBoolean1, boolean paramBoolean2)
/*     */   {
/* 125 */     Iterator localIterator = paramMap.values().iterator();
/* 126 */     while (localIterator.hasNext()) {
/* 127 */       Cookie localCookie = (Cookie)localIterator.next();
/* 128 */       if (localCookie.hasExpired()) {
/* 129 */         localIterator.remove();
/* 130 */         this.totalCount -= 1;
/* 131 */         log("Expired cookie removed by find", localCookie, paramMap);
/*     */       }
/* 135 */       else if (localCookie.getHostOnly() ? 
/* 136 */         paramString1.equalsIgnoreCase(localCookie.getDomain()) : 
/* 140 */         Cookie.domainMatches(paramString1, localCookie.getDomain()))
/*     */       {
/* 145 */         if ((Cookie.pathMatches(paramString2, localCookie.getPath())) && 
/* 149 */           ((!localCookie.getSecureOnly()) || (paramBoolean1)) && (
/* 153 */           (!localCookie.getHttpOnly()) || (paramBoolean2)))
/*     */         {
/* 157 */           paramList.add(localCookie);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   void put(Cookie paramCookie)
/*     */   {
/* 176 */     Object localObject = (Map)this.buckets.get(paramCookie.getDomain());
/* 177 */     if (localObject == null) {
/* 178 */       localObject = new LinkedHashMap(20);
/* 179 */       this.buckets.put(paramCookie.getDomain(), localObject);
/*     */     }
/* 181 */     if (paramCookie.hasExpired()) {
/* 182 */       log("Cookie expired", paramCookie, (Map)localObject);
/* 183 */       if (((Map)localObject).remove(paramCookie) != null) {
/* 184 */         this.totalCount -= 1;
/* 185 */         log("Expired cookie removed by put", paramCookie, (Map)localObject);
/*     */       }
/*     */     }
/* 188 */     else if (((Map)localObject).put(paramCookie, paramCookie) == null) {
/* 189 */       this.totalCount += 1;
/* 190 */       log("Cookie added", paramCookie, (Map)localObject);
/* 191 */       if (((Map)localObject).size() > 50) {
/* 192 */         purge((Map)localObject);
/*     */       }
/* 194 */       if (this.totalCount > 4000)
/* 195 */         purge();
/*     */     }
/*     */     else {
/* 198 */       log("Cookie updated", paramCookie, (Map)localObject);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void purge(Map<Cookie, Cookie> paramMap)
/*     */   {
/* 207 */     logger.log(Level.FINEST, "Purging bucket: {0}", paramMap.values());
/*     */ 
/* 209 */     Object localObject = null;
/* 210 */     Iterator localIterator = paramMap.values().iterator();
/* 211 */     while (localIterator.hasNext()) {
/* 212 */       Cookie localCookie = (Cookie)localIterator.next();
/* 213 */       if (localCookie.hasExpired()) {
/* 214 */         localIterator.remove();
/* 215 */         this.totalCount -= 1;
/* 216 */         log("Expired cookie removed", localCookie, paramMap);
/*     */       }
/* 218 */       else if ((localObject == null) || (localCookie.getLastAccessTime() < localObject.getLastAccessTime()))
/*     */       {
/* 221 */         localObject = localCookie;
/*     */       }
/*     */     }
/*     */ 
/* 225 */     if (paramMap.size() > 50) {
/* 226 */       paramMap.remove(localObject);
/* 227 */       this.totalCount -= 1;
/* 228 */       log("Excess cookie removed", localObject, paramMap);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void purge()
/*     */   {
/* 236 */     logger.log(Level.FINEST, "Purging store");
/*     */ 
/* 238 */     PriorityQueue localPriorityQueue = new PriorityQueue(this.totalCount / 2, new RemovalComparator(null));
/*     */ 
/* 241 */     for (Object localObject1 = this.buckets.entrySet().iterator(); ((Iterator)localObject1).hasNext(); ) { localObject2 = (Map.Entry)((Iterator)localObject1).next();
/* 242 */       Map localMap = (Map)((Map.Entry)localObject2).getValue();
/* 243 */       Iterator localIterator = localMap.values().iterator();
/* 244 */       while (localIterator.hasNext()) {
/* 245 */         Cookie localCookie = (Cookie)localIterator.next();
/* 246 */         if (localCookie.hasExpired()) {
/* 247 */           localIterator.remove();
/* 248 */           this.totalCount -= 1;
/* 249 */           log("Expired cookie removed", localCookie, localMap);
/*     */         } else {
/* 251 */           localPriorityQueue.add(localCookie);
/*     */         }
/*     */       }
/*     */     }
/*     */     Object localObject2;
/* 256 */     while (this.totalCount > 3000) {
/* 257 */       localObject1 = (Cookie)localPriorityQueue.remove();
/* 258 */       localObject2 = (Map)this.buckets.get(((Cookie)localObject1).getDomain());
/* 259 */       if (localObject2 != null) {
/* 260 */         ((Map)localObject2).remove(localObject1);
/* 261 */         this.totalCount -= 1;
/* 262 */         log("Excess cookie removed", (Cookie)localObject1, (Map)localObject2);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void log(String paramString, Cookie paramCookie, Map<Cookie, Cookie> paramMap)
/*     */   {
/* 280 */     if (logger.isLoggable(Level.FINEST))
/* 281 */       logger.log(Level.FINEST, "{0}: {1}, bucket size: {2}, total count: {3}", new Object[] { paramString, paramCookie, Integer.valueOf(paramMap.size()), Integer.valueOf(this.totalCount) });
/*     */   }
/*     */ 
/*     */   private static class RemovalComparator
/*     */     implements Comparator<Cookie>
/*     */   {
/*     */     public int compare(Cookie paramCookie1, Cookie paramCookie2)
/*     */     {
/* 270 */       return (int)(paramCookie1.getLastAccessTime() - paramCookie2.getLastAccessTime());
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class GetComparator
/*     */     implements Comparator<Cookie>
/*     */   {
/*     */     public int compare(Cookie paramCookie1, Cookie paramCookie2)
/*     */     {
/* 164 */       int i = paramCookie2.getPath().length() - paramCookie1.getPath().length();
/* 165 */       if (i != 0) {
/* 166 */         return i;
/*     */       }
/* 168 */       return paramCookie1.getCreationTime().compareTo(paramCookie2.getCreationTime());
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.network.CookieStore
 * JD-Core Version:    0.6.2
 */