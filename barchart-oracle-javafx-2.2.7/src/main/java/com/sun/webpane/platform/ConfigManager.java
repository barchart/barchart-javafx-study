/*    */ package com.sun.webpane.platform;
/*    */ 
/*    */ import java.util.Locale;
/*    */ import java.util.ResourceBundle;
/*    */ import java.util.logging.Logger;
/*    */ 
/*    */ public class ConfigManager
/*    */ {
/* 11 */   public static final Logger log = Logger.getLogger(ConfigManager.class.getName());
/*    */ 
/* 13 */   private static String RESOURCE_FILE = "com.sun.webpane.platform.build";
/*    */ 
/* 18 */   private static ResourceBundle bundle = ResourceBundle.getBundle(RESOURCE_FILE, Locale.getDefault());
/*    */ 
/*    */   public static String getProperty(String paramString)
/*    */   {
/* 23 */     return getProperty(paramString, null);
/*    */   }
/*    */ 
/*    */   public static String getProperty(String paramString1, String paramString2) {
/* 27 */     return (bundle == null) || (!bundle.containsKey(paramString1)) ? paramString2 : bundle.getString(paramString1);
/*    */   }
/*    */ 
/*    */   static
/*    */   {
/* 19 */     log.finest("configuration: " + bundle.keySet());
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.platform.ConfigManager
 * JD-Core Version:    0.6.2
 */