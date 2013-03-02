/*     */ package com.sun.javafx.runtime;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Hashtable;
/*     */ 
/*     */ public class SystemProperties
/*     */ {
/*  39 */   private static final String[] sysprop_table = { "application.codebase", "jfx_specific", "debug", "javafx.debug" };
/*     */ 
/*  49 */   private static final String[] jfxprop_table = { "application.codebase", "" };
/*     */ 
/*  53 */   private static final Hashtable sysprop_list = new Hashtable();
/*  54 */   private static final Hashtable jfxprop_list = new Hashtable();
/*     */   private static final String versionResourceName = "/com/sun/javafx/runtime/resources/version.properties";
/*     */   private static boolean isDebug;
/*     */   private static String codebase_value;
/*     */   public static final String codebase = "javafx.application.codebase";
/*     */ 
/*     */   private static void setVersions()
/*     */   {
/*  78 */     InputStream localInputStream = SystemProperties.class.getResourceAsStream("/com/sun/javafx/runtime/resources/version.properties");
/*     */     try
/*     */     {
/*  81 */       int i = localInputStream.available();
/*     */ 
/*  83 */       byte[] arrayOfByte = new byte[i];
/*  84 */       int j = localInputStream.read(arrayOfByte);
/*  85 */       String str = new String(arrayOfByte, "utf-8");
/*  86 */       setFXProperty("javafx.version", getValue(str, "release="));
/*     */ 
/*  89 */       setFXProperty("javafx.runtime.version", getValue(str, "full="));
/*     */     }
/*     */     catch (Exception localException)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   private static String getValue(String paramString1, String paramString2)
/*     */   {
/*  99 */     String str = paramString1;
/*     */     int i;
/* 101 */     if ((i = str.indexOf(paramString2)) != -1) {
/* 102 */       str = str.substring(i);
/* 103 */       if ((i = str.indexOf(10)) != -1) {
/* 104 */         return str.substring(paramString2.length(), i).trim();
/*     */       }
/* 106 */       return str.substring(paramString2.length(), str.length()).trim();
/*     */     }
/* 108 */     return "unknown";
/*     */   }
/*     */ 
/*     */   public static void addProperties(String[] paramArrayOfString, boolean paramBoolean)
/*     */   {
/* 138 */     if (paramArrayOfString == null)
/*     */       return;
/*     */     Hashtable localHashtable;
/* 143 */     if (paramBoolean)
/* 144 */       localHashtable = jfxprop_list;
/*     */     else {
/* 146 */       localHashtable = sysprop_list;
/*     */     }
/*     */ 
/* 149 */     for (int i = 0; i < paramArrayOfString.length; i += 2)
/* 150 */       localHashtable.put(paramArrayOfString[i], paramArrayOfString[(i + 1)]);
/*     */   }
/*     */ 
/*     */   public static String getProperty(String paramString)
/*     */   {
/* 155 */     Hashtable localHashtable = sysprop_list;
/*     */ 
/* 158 */     if (paramString == null) {
/* 159 */       return null;
/*     */     }
/* 161 */     if (paramString.startsWith("javafx."))
/* 162 */       paramString = paramString.substring("javafx.".length());
/*     */     else {
/* 164 */       return null;
/*     */     }
/* 166 */     String str = (String)localHashtable.get(paramString);
/* 167 */     if ((str == null) || (str.equals("")))
/*     */     {
/* 169 */       return null;
/*     */     }
/*     */ 
/* 174 */     if (str.equals("jfx_specific")) {
/* 175 */       localHashtable = jfxprop_list;
/* 176 */       return (String)localHashtable.get(paramString);
/*     */     }
/* 178 */     return System.getProperty(str);
/*     */   }
/*     */ 
/*     */   public static void clearProperty(String paramString)
/*     */   {
/* 187 */     if (paramString == null) {
/* 188 */       return;
/*     */     }
/* 190 */     Hashtable localHashtable = sysprop_list;
/*     */ 
/* 194 */     if (paramString.startsWith("javafx.".toString()))
/* 195 */       paramString = paramString.substring("javafx.".length());
/*     */     else {
/* 197 */       return;
/*     */     }
/*     */ 
/* 200 */     String str = (String)localHashtable.get(paramString);
/* 201 */     if (str == null) {
/* 202 */       return;
/*     */     }
/* 204 */     localHashtable.remove(paramString);
/*     */ 
/* 207 */     if (str.equals("jfx_specific")) {
/* 208 */       localHashtable = jfxprop_list;
/* 209 */       localHashtable.remove(paramString);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void setFXProperty(String paramString1, String paramString2)
/*     */   {
/* 223 */     Hashtable localHashtable = sysprop_list;
/*     */ 
/* 227 */     if (paramString1.startsWith("javafx.")) {
/* 228 */       paramString1 = paramString1.substring("javafx.".length());
/*     */ 
/* 230 */       String str = (String)localHashtable.get(paramString1);
/*     */ 
/* 232 */       if (str == null) {
/* 233 */         localHashtable.put(paramString1, "jfx_specific");
/* 234 */         localHashtable = jfxprop_list;
/* 235 */         localHashtable.put(paramString1, paramString2);
/* 236 */       } else if (str.equals("jfx_specific"))
/*     */       {
/* 238 */         localHashtable = jfxprop_list;
/* 239 */         localHashtable.put(paramString1, paramString2);
/* 240 */         if ("javafx.application.codebase".equals("javafx." + paramString1))
/* 241 */           codebase_value = paramString2;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static boolean isDebug() {
/* 247 */     return isDebug;
/*     */   }
/*     */ 
/*     */   public static String getCodebase() {
/* 251 */     return codebase_value;
/*     */   }
/*     */ 
/*     */   public static void setCodebase(String paramString) {
/* 255 */     if (paramString == null)
/* 256 */       paramString = "";
/* 257 */     codebase_value = paramString;
/* 258 */     setFXProperty("javafx.application.codebase", paramString);
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  62 */     AccessController.doPrivileged(new PrivilegedAction() {
/*     */       public Object run() {
/*  64 */         SystemProperties.addProperties(SystemProperties.sysprop_table, false);
/*  65 */         SystemProperties.addProperties(SystemProperties.jfxprop_table, true);
/*  66 */         SystemProperties.access$200();
/*  67 */         SystemProperties.access$302("true".equalsIgnoreCase(SystemProperties.getProperty("javafx.debug")));
/*  68 */         return null;
/*     */       }
/*     */     });
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.runtime.SystemProperties
 * JD-Core Version:    0.6.2
 */