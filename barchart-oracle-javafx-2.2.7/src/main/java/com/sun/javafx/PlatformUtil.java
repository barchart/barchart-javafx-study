/*     */ package com.sun.javafx;
/*     */ 
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ 
/*     */ public class PlatformUtil
/*     */ {
/*  13 */   private static final String os = System.getProperty("os.name");
/*  14 */   private static final String version = System.getProperty("os.version");
/*     */ 
/*  19 */   private static final boolean embedded = ((Boolean)AccessController.doPrivileged(new PrivilegedAction() {
/*     */     public Boolean run() {
/*  21 */       return Boolean.valueOf(Boolean.getBoolean("com.sun.javafx.isEmbedded"));
/*     */     }
/*     */   })).booleanValue();
/*     */ 
/*  24 */   private static final String embeddedType = (String)AccessController.doPrivileged(new PrivilegedAction() {
/*     */     public String run() {
/*  26 */       return System.getProperty("embedded");
/*     */     }
/*     */   });
/*     */ 
/*  31 */   private static final boolean WINDOWS = os.startsWith("Windows");
/*  32 */   private static final boolean WINDOWS_VISTA_OR_LATER = (WINDOWS) && (versionNumberGreaterThanOrEqualTo(6.0F));
/*  33 */   private static final boolean WINDOWS_7_OR_LATER = (WINDOWS) && (versionNumberGreaterThanOrEqualTo(6.1F));
/*  34 */   private static final boolean MAC = os.startsWith("Mac");
/*  35 */   private static final boolean LINUX = os.startsWith("Linux");
/*  36 */   private static final boolean SOLARIS = os.startsWith("SunOS");
/*     */ 
/*     */   private static boolean versionNumberGreaterThanOrEqualTo(float paramFloat)
/*     */   {
/*     */     try
/*     */     {
/*  49 */       return Float.parseFloat(version) >= paramFloat; } catch (Exception localException) {
/*     */     }
/*  51 */     return false;
/*     */   }
/*     */ 
/*     */   public static boolean isWindows()
/*     */   {
/*  59 */     return WINDOWS;
/*     */   }
/*     */ 
/*     */   public static boolean isWinVistaOrLater()
/*     */   {
/*  66 */     return WINDOWS_VISTA_OR_LATER;
/*     */   }
/*     */ 
/*     */   public static boolean isWin7OrLater()
/*     */   {
/*  73 */     return WINDOWS_7_OR_LATER;
/*     */   }
/*     */ 
/*     */   public static boolean isMac()
/*     */   {
/*  80 */     return MAC;
/*     */   }
/*     */ 
/*     */   public static boolean isLinux()
/*     */   {
/*  87 */     return LINUX;
/*     */   }
/*     */ 
/*     */   public static boolean isSolaris()
/*     */   {
/*  94 */     return SOLARIS;
/*     */   }
/*     */ 
/*     */   public static boolean isUnix()
/*     */   {
/* 101 */     return (LINUX) || (SOLARIS);
/*     */   }
/*     */ 
/*     */   public static boolean isEmbedded()
/*     */   {
/* 108 */     return embedded | embeddedType != null;
/*     */   }
/*     */ 
/*     */   public static String getEmbeddedType()
/*     */   {
/* 115 */     return embeddedType;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.PlatformUtil
 * JD-Core Version:    0.6.2
 */