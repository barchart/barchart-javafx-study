/*    */ package com.sun.media.jfxmediaimpl;
/*    */ 
/*    */ import java.security.AccessController;
/*    */ import java.security.PrivilegedAction;
/*    */ 
/*    */ public class HostUtils
/*    */ {
/*    */   private static String osName;
/*    */   private static String osArch;
/* 20 */   private static boolean embedded = ((Boolean)AccessController.doPrivileged(new PrivilegedAction() {
/*    */     public Boolean run() {
/* 22 */       HostUtils.access$002(System.getProperty("os.name").toLowerCase());
/* 23 */       HostUtils.access$102(System.getProperty("os.arch").toLowerCase());
/*    */ 
/* 25 */       HostUtils.access$202((HostUtils.osArch.equals("x64")) || (HostUtils.osArch.equals("x86_64")) || (HostUtils.osArch.equals("ia64")));
/*    */ 
/* 29 */       return Boolean.valueOf(Boolean.getBoolean("com.sun.javafx.isEmbedded"));
/*    */     }
/*    */   })).booleanValue();
/*    */ 
/* 17 */   private static boolean is64bit = false;
/*    */ 
/*    */   public static boolean is64Bit()
/*    */   {
/* 35 */     return is64bit;
/*    */   }
/*    */ 
/*    */   public static boolean isWindows() {
/* 39 */     return osName.startsWith("windows");
/*    */   }
/*    */ 
/*    */   public static boolean isMacOSX()
/*    */   {
/* 45 */     return osName.startsWith("mac os x");
/*    */   }
/*    */ 
/*    */   public static boolean isLinux()
/*    */   {
/* 50 */     return osName.startsWith("linux");
/*    */   }
/*    */ 
/*    */   public static boolean isIOS()
/*    */   {
/* 55 */     return osName.startsWith("ios");
/*    */   }
/*    */ 
/*    */   public static boolean isEmbedded()
/*    */   {
/* 62 */     return embedded;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.media.jfxmediaimpl.HostUtils
 * JD-Core Version:    0.6.2
 */