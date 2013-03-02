/*    */ package com.sun.webpane.platform;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStreamReader;
/*    */ import java.net.URL;
/*    */ import java.util.Locale;
/*    */ import java.util.PropertyResourceBundle;
/*    */ import java.util.ResourceBundle;
/*    */ import java.util.ResourceBundle.Control;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
/*    */ 
/*    */ public final class LocalizedStrings
/*    */ {
/* 16 */   private static final Logger log = Logger.getLogger(LocalizedStrings.class.getName());
/*    */ 
/* 19 */   private static ResourceBundle.Control utfResourceBundleControl = new EncodingResourceBundleControl("utf-8");
/*    */   private static String baseName;
/*    */ 
/*    */   public static void setResourceBundleBaseName(String paramString)
/*    */   {
/* 30 */     baseName = paramString;
/*    */   }
/*    */ 
/*    */   public static String getLocalizedProperty(String paramString)
/*    */   {
/* 35 */     log.log(Level.FINE, "Get property: " + paramString);
/*    */     try
/*    */     {
/* 38 */       ResourceBundle localResourceBundle = ResourceBundle.getBundle(baseName, Locale.getDefault(), utfResourceBundleControl);
/*    */ 
/* 41 */       if (localResourceBundle != null)
/*    */       {
/* 43 */         String str = localResourceBundle.getString(paramString);
/* 44 */         if ((str != null) && (str.trim().length() > 0))
/*    */         {
/* 46 */           log.log(Level.FINE, "Property value: " + str);
/* 47 */           return str.trim();
/*    */         }
/*    */       }
/*    */     }
/*    */     catch (Exception localException)
/*    */     {
/* 53 */       log.log(Level.FINE, "exception thrown during bundle initialization", localException);
/*    */     }
/*    */ 
/* 56 */     log.log(Level.FINE, "Unknown property value");
/* 57 */     return null;
/*    */   }
/*    */ 
/*    */   private static class EncodingResourceBundleControl extends ResourceBundle.Control
/*    */   {
/*    */     private String encoding;
/*    */ 
/*    */     public EncodingResourceBundleControl(String paramString)
/*    */     {
/* 66 */       this.encoding = paramString;
/*    */     }
/*    */ 
/*    */     public ResourceBundle newBundle(String paramString1, Locale paramLocale, String paramString2, ClassLoader paramClassLoader, boolean paramBoolean)
/*    */       throws IllegalAccessException, InstantiationException, IOException
/*    */     {
/* 75 */       String str1 = toBundleName(paramString1, paramLocale);
/* 76 */       String str2 = toResourceName(str1, "properties");
/* 77 */       URL localURL = paramClassLoader.getResource(str2);
/* 78 */       if (localURL != null)
/*    */       {
/*    */         try
/*    */         {
/* 82 */           return new PropertyResourceBundle(new InputStreamReader(localURL.openStream(), this.encoding));
/*    */         }
/*    */         catch (Exception localException)
/*    */         {
/* 86 */           LocalizedStrings.log.log(Level.FINE, "exception thrown during bundle initialization", localException);
/*    */         }
/*    */       }
/*    */ 
/* 90 */       return super.newBundle(paramString1, paramLocale, paramString2, paramClassLoader, paramBoolean);
/*    */     }
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.platform.LocalizedStrings
 * JD-Core Version:    0.6.2
 */