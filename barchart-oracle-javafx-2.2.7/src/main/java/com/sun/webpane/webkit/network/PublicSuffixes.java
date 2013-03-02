/*     */ package com.sun.webpane.webkit.network;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.net.IDN;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ final class PublicSuffixes
/*     */ {
/*  22 */   private static final Logger logger = Logger.getLogger(PublicSuffixes.class.getName());
/*     */ 
/*  39 */   private static final Map<String, Rule> RULES = loadRules("effective_tld_names.dat");
/*     */ 
/*     */   private PublicSuffixes()
/*     */   {
/*  47 */     throw new AssertionError();
/*     */   }
/*     */ 
/*     */   static boolean isPublicSuffix(String paramString)
/*     */   {
/*  55 */     if (paramString.length() == 0) {
/*  56 */       return false;
/*     */     }
/*  58 */     Rule localRule = (Rule)RULES.get(paramString);
/*  59 */     if (localRule == Rule.EXCEPTION_RULE)
/*  60 */       return false;
/*  61 */     if ((localRule == Rule.SIMPLE_RULE) || (localRule == Rule.WILDCARD_RULE)) {
/*  62 */       return true;
/*     */     }
/*  64 */     int i = paramString.indexOf(46) + 1;
/*  65 */     if (i == 0) {
/*  66 */       i = paramString.length();
/*     */     }
/*  68 */     String str = paramString.substring(i);
/*  69 */     return RULES.get(str) == Rule.WILDCARD_RULE;
/*     */   }
/*     */ 
/*     */   private static Map<String, Rule> loadRules(String paramString)
/*     */   {
/*  77 */     logger.log(Level.FINEST, "resourceName: [{0}]", paramString);
/*  78 */     Map localMap = null;
/*     */ 
/*  80 */     InputStream localInputStream = PublicSuffixes.class.getResourceAsStream(paramString);
/*  81 */     if (localInputStream != null) {
/*  82 */       BufferedReader localBufferedReader = null;
/*     */       try {
/*  84 */         localBufferedReader = new BufferedReader(new InputStreamReader(localInputStream, "UTF-8"));
/*  85 */         localMap = loadRules(localBufferedReader);
/*     */       } catch (IOException localIOException2) {
/*  87 */         logger.log(Level.WARNING, "Unexpected error", localIOException2);
/*     */       } finally {
/*     */         try {
/*  90 */           if (localBufferedReader != null)
/*  91 */             localBufferedReader.close();
/*     */         }
/*     */         catch (IOException localIOException4) {
/*  94 */           logger.log(Level.WARNING, "Unexpected error", localIOException4);
/*     */         }
/*     */       }
/*     */     } else {
/*  98 */       logger.log(Level.WARNING, "Resource not found: [{0}]", paramString);
/*     */     }
/*     */ 
/* 102 */     localMap = localMap != null ? Collections.unmodifiableMap(localMap) : Collections.emptyMap();
/*     */ 
/* 105 */     if (logger.isLoggable(Level.FINEST)) {
/* 106 */       logger.log(Level.FINEST, "result: {0}", toLogString(localMap));
/*     */     }
/* 108 */     return localMap;
/*     */   }
/*     */ 
/*     */   private static Map<String, Rule> loadRules(BufferedReader paramBufferedReader)
/*     */     throws IOException
/*     */   {
/* 117 */     LinkedHashMap localLinkedHashMap = new LinkedHashMap();
/*     */     String str;
/* 119 */     while ((str = paramBufferedReader.readLine()) != null) {
/* 120 */       str = str.split("\\s+", 2)[0];
/* 121 */       if ((str.length() != 0) && 
/* 124 */         (!str.startsWith("//")))
/*     */       {
/*     */         Rule localRule;
/* 128 */         if (str.startsWith("!")) {
/* 129 */           str = str.substring(1);
/* 130 */           localRule = Rule.EXCEPTION_RULE;
/* 131 */         } else if (str.startsWith("*.")) {
/* 132 */           str = str.substring(2);
/* 133 */           localRule = Rule.WILDCARD_RULE;
/*     */         } else {
/* 135 */           localRule = Rule.SIMPLE_RULE;
/*     */         }
/*     */         try {
/* 138 */           str = IDN.toASCII(str, 1);
/*     */         } catch (Exception localException) {
/* 140 */           logger.log(Level.WARNING, String.format("Error parsing rule: [%s]", new Object[] { str }), localException);
/*     */         }
/* 142 */         continue;
/*     */ 
/* 144 */         localLinkedHashMap.put(str, localRule);
/*     */       }
/*     */     }
/* 146 */     return localLinkedHashMap;
/*     */   }
/*     */ 
/*     */   private static String toLogString(Map<String, Rule> paramMap)
/*     */   {
/* 154 */     if (paramMap.isEmpty()) {
/* 155 */       return "{}";
/*     */     }
/* 157 */     StringBuilder localStringBuilder = new StringBuilder();
/* 158 */     for (Map.Entry localEntry : paramMap.entrySet()) {
/* 159 */       localStringBuilder.append(String.format("%n    ", new Object[0]));
/* 160 */       localStringBuilder.append((String)localEntry.getKey());
/* 161 */       localStringBuilder.append(": ");
/* 162 */       localStringBuilder.append(localEntry.getValue());
/*     */     }
/* 164 */     return localStringBuilder.toString();
/*     */   }
/*     */ 
/*     */   private static enum Rule
/*     */   {
/*  30 */     SIMPLE_RULE, 
/*  31 */     WILDCARD_RULE, 
/*  32 */     EXCEPTION_RULE;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.network.PublicSuffixes
 * JD-Core Version:    0.6.2
 */