/*     */ package com.sun.javafx.runtime;
/*     */ 
/*     */ public class VersionInfo
/*     */ {
/*     */   private static final String BUILD_TIMESTAMP = "2013/02/04 11:39:44";
/*     */   private static final String HUDSON_JOB_NAME = "2.2.7";
/*     */   private static final String HUDSON_BUILD_NUMBER = "9";
/*     */   private static final String PROMOTED_BUILD_NUMBER = "01";
/*     */   private static final String PRODUCT_NAME = "javafx";
/*     */   private static final String RAW_VERSION = "2.2.7";
/*     */   private static final String RELEASE_MILESTONE = "fcs";
/*     */   private static final String RELEASE_NAME = "2.2.7";
/*     */   private static final String VERSION;
/* 135 */   private static final String RUNTIME_VERSION = str;
/*     */ 
/*     */   public static synchronized void setupSystemProperties()
/*     */   {
/* 146 */     if (System.getProperty("javafx.version") == null) {
/* 147 */       System.setProperty("javafx.version", getVersion());
/* 148 */       System.setProperty("javafx.runtime.version", getRuntimeVersion());
/*     */     }
/*     */   }
/*     */ 
/*     */   public static String getBuildTimestamp()
/*     */   {
/* 157 */     return "2013/02/04 11:39:44";
/*     */   }
/*     */ 
/*     */   public static String getHudsonJobName()
/*     */   {
/* 166 */     if ("2.2.7".equals("not_hudson")) {
/* 167 */       return "";
/*     */     }
/* 169 */     return "2.2.7";
/*     */   }
/*     */ 
/*     */   public static String getHudsonBuildNumber()
/*     */   {
/* 177 */     return "9";
/*     */   }
/*     */ 
/*     */   public static String getReleaseMilestone()
/*     */   {
/* 186 */     if ("fcs".equals("fcs")) {
/* 187 */       return "";
/*     */     }
/* 189 */     return "fcs";
/*     */   }
/*     */ 
/*     */   public static String getVersion()
/*     */   {
/* 197 */     return VERSION;
/*     */   }
/*     */ 
/*     */   public static String getRuntimeVersion()
/*     */   {
/* 205 */     return RUNTIME_VERSION;
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/* 118 */     String str = "2.2.7";
/*     */ 
/* 123 */     if (getReleaseMilestone().length() > 0) {
/* 124 */       str = str + "-fcs";
/*     */     }
/* 126 */     VERSION = str;
/*     */ 
/* 129 */     if (getHudsonJobName().length() > 0) {
/* 130 */       str = str + "-b01";
/*     */     }
/*     */     else
/* 133 */       str = str + " (2013/02/04 11:39:44)";
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.runtime.VersionInfo
 * JD-Core Version:    0.6.2
 */