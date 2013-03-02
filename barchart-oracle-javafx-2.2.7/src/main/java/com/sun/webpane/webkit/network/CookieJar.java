/*    */ package com.sun.webpane.webkit.network;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.net.CookieHandler;
/*    */ import java.net.URI;
/*    */ import java.net.URISyntaxException;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ final class CookieJar
/*    */ {
/*    */   private static void fwkPut(String paramString1, String paramString2)
/*    */   {
/* 21 */     CookieHandler localCookieHandler = CookieHandler.getDefault();
/* 22 */     if (localCookieHandler != null) {
/* 23 */       URI localURI = null;
/*    */       try {
/* 25 */         localURI = new URI(paramString1);
/*    */ 
/* 28 */         localURI = new URI("javascript", localURI.getRawSchemeSpecificPart(), localURI.getRawFragment());
/*    */       }
/*    */       catch (URISyntaxException localURISyntaxException) {
/* 31 */         return;
/*    */       }
/*    */ 
/* 34 */       HashMap localHashMap = new HashMap();
/* 35 */       ArrayList localArrayList = new ArrayList();
/* 36 */       localArrayList.add(paramString2);
/* 37 */       localHashMap.put("Set-Cookie", localArrayList);
/*    */       try {
/* 39 */         localCookieHandler.put(localURI, localHashMap);
/*    */       } catch (IOException localIOException) {
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   private static String fwkGet(String paramString) {
/* 46 */     CookieHandler localCookieHandler = CookieHandler.getDefault();
/* 47 */     if (localCookieHandler != null) {
/* 48 */       URI localURI = null;
/*    */       try {
/* 50 */         localURI = new URI(paramString);
/*    */ 
/* 53 */         localURI = new URI("javascript", localURI.getRawSchemeSpecificPart(), localURI.getRawFragment());
/*    */       }
/*    */       catch (URISyntaxException localURISyntaxException) {
/* 56 */         return null;
/*    */       }
/*    */ 
/* 59 */       HashMap localHashMap = new HashMap();
/* 60 */       Map localMap = null;
/*    */       try {
/* 62 */         localMap = localCookieHandler.get(localURI, localHashMap);
/*    */       } catch (IOException localIOException) {
/* 64 */         return null;
/*    */       }
/* 66 */       if (localMap != null) {
/* 67 */         StringBuilder localStringBuilder = new StringBuilder();
/* 68 */         for (String str1 : localMap.keySet()) {
/* 69 */           if ("Cookie".equalsIgnoreCase(str1)) {
/* 70 */             for (String str2 : (List)localMap.get(str1)) {
/* 71 */               if (localStringBuilder.length() > 0) {
/* 72 */                 localStringBuilder.append("; ");
/*    */               }
/* 74 */               localStringBuilder.append(str2);
/*    */             }
/*    */           }
/*    */         }
/* 78 */         return localStringBuilder.toString();
/*    */       }
/*    */     }
/* 81 */     return null;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.network.CookieJar
 * JD-Core Version:    0.6.2
 */