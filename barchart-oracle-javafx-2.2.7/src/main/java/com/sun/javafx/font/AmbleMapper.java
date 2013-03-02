/*     */ package com.sun.javafx.font;
/*     */ 
/*     */ import com.sun.javafx.PlatformUtil;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedActionException;
/*     */ import java.security.PrivilegedExceptionAction;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class AmbleMapper
/*     */ {
/*  57 */   static FontNameInfo[] fontInfo = { new FontNameInfo("Amble-Regular.ttf", "Amble-Regular", "Amble", "Amble Regular"), new FontNameInfo("Amble-Bold.ttf", "Amble-Bold", "Amble", "Amble Bold"), new FontNameInfo("Amble-Italic.ttf", "Amble-Italic", "Amble", "Amble Italic"), new FontNameInfo("Amble-BoldItalic.ttf", "Amble-BoldItalic", "Amble", "Amble Bold Italic"), new FontNameInfo("Amble-Condensed.ttf", "Amble-Condensed", "Amble Cn", "Amble Condensed"), new FontNameInfo("Amble-BoldCondensed.ttf", "Amble-BoldCondensed", "Amble Cn", "Amble Bold Condensed"), new FontNameInfo("Amble-CondensedItalic.ttf", "Amble-CondensedItalic", "Amble Cn", "Amble Condensed Italic"), new FontNameInfo("Amble-BoldCondensedItalic.ttf", "Amble-BoldCondensedItalic", "Amble Cn", "Amble Bold Condensed Italic"), new FontNameInfo("Amble-Light.ttf", "Amble-Light", "Amble Lt", "Amble Light"), new FontNameInfo("Amble-LightItalic.ttf", "Amble-LightItalic", "Amble Lt", "Amble Light Italic"), new FontNameInfo("Amble-LightCondensed.ttf", "Amble-LightCondensed", "Amble LtCn", "Amble Light Condensed"), new FontNameInfo("Amble-LightCondensedItalic.ttf", "Amble-LightCondensedItalic", "Amble LtCn", "Amble Light Condensed Italic") };
/*     */ 
/*     */   static String[] getAllFullNames()
/*     */   {
/* 113 */     String[] arrayOfString = new String[fontInfo.length];
/* 114 */     for (int i = 0; i < arrayOfString.length; i++) {
/* 115 */       arrayOfString[i] = fontInfo[i].fullname;
/*     */     }
/* 117 */     return arrayOfString;
/*     */   }
/*     */ 
/*     */   private static InputStream localFileFromRuntimeJar(String paramString)
/*     */   {
/*     */     try
/*     */     {
/* 128 */       return AmbleMapper.class.getResourceAsStream("/com/sun/fonts/" + paramString);
/*     */     } catch (Exception localException) {
/*     */     }
/* 131 */     return null;
/*     */   }
/*     */ 
/*     */   private static File extractFileFromRuntimeJar(String paramString)
/*     */   {
/*     */     try
/*     */     {
/* 139 */       Object localObject = localFileFromRuntimeJar(paramString);
/* 140 */       if (localObject == null) {
/* 141 */         return null;
/*     */       }
/*     */ 
/* 144 */       localObject = new BufferedInputStream((InputStream)localObject);
/*     */ 
/* 146 */       File localFile1 = new File(getUserFontCacheDir());
/* 147 */       if (!localFile1.exists()) {
/* 148 */         localFile1.mkdirs();
/*     */       }
/*     */ 
/* 154 */       File localFile2 = File.createTempFile("font", "tmp", localFile1);
/* 155 */       BufferedOutputStream localBufferedOutputStream = new BufferedOutputStream(new FileOutputStream(localFile2));
/* 156 */       byte[] arrayOfByte = new byte[8192];
/*     */       int i;
/* 158 */       while ((i = ((InputStream)localObject).read(arrayOfByte)) > 0) {
/* 159 */         localBufferedOutputStream.write(arrayOfByte, 0, i);
/*     */       }
/* 161 */       localBufferedOutputStream.flush();
/* 162 */       localBufferedOutputStream.close();
/* 163 */       File localFile3 = new File(localFile1, paramString);
/* 164 */       if (!localFile3.exists()) {
/* 165 */         localFile2.renameTo(localFile3);
/*     */       }
/* 167 */       return localFile3; } catch (Exception localException) {
/*     */     }
/* 169 */     return null;
/*     */   }
/*     */ 
/*     */   private static String getUserFontCacheDir()
/*     */   {
/* 174 */     if (PlatformUtil.isWindows()) {
/* 175 */       return System.getProperty("user.home") + File.separator + "Sun" + File.separator + "JavaFX" + File.separator + "fonts";
/*     */     }
/*     */ 
/* 178 */     return System.getProperty("user.home") + File.separator + ".javafx" + File.separator + "fonts";
/*     */   }
/*     */ 
/*     */   static File getFile(String paramString)
/*     */   {
/* 196 */     FontNameInfo localFontNameInfo = (FontNameInfo)FontNameInfo.nameMap.get(paramString.toLowerCase());
/*     */ 
/* 198 */     if (localFontNameInfo == null) {
/* 199 */       return null;
/*     */     }
/*     */ 
/* 202 */     String str = localFontNameInfo.filename;
/*     */     File localFile;
/*     */     try
/*     */     {
/* 209 */       localFile = (File)AccessController.doPrivileged(new PrivilegedExceptionAction()
/*     */       {
/*     */         public File run() throws IOException {
/* 212 */           File localFile = AmbleMapper.locateFile(this.val$fName);
/* 213 */           if (localFile == null) {
/* 214 */             localFile = AmbleMapper.extractFileFromRuntimeJar(this.val$fName);
/*     */           }
/* 216 */           return localFile;
/*     */         }
/*     */       });
/*     */     }
/*     */     catch (PrivilegedActionException localPrivilegedActionException) {
/* 221 */       return null;
/*     */     }
/*     */ 
/* 224 */     return localFile;
/*     */   }
/*     */ 
/*     */   static String getFilePath(String paramString)
/*     */   {
/* 234 */     File localFile = getFile(paramString);
/* 235 */     if (localFile == null) {
/* 236 */       return null;
/*     */     }
/* 238 */     return localFile.getPath();
/*     */   }
/*     */ 
/*     */   private static File locateFile(String paramString)
/*     */   {
/* 247 */     int i = 0;
/*     */     String[] arrayOfString;
/*     */     Object localObject;
/* 249 */     if (PlatformUtil.isMac()) {
/* 250 */       arrayOfString = new String[5];
/* 251 */       i = 3;
/*     */ 
/* 253 */       arrayOfString[0] = getUserFontCacheDir();
/* 254 */       arrayOfString[1] = "/Library/Fonts/";
/* 255 */       arrayOfString[2] = "/System/Library/Fonts/";
/* 256 */       String str = System.getProperty("java.home");
/* 257 */       if (str != null) {
/* 258 */         arrayOfString[(i++)] = (str + "/lib/fonts/");
/*     */       }
/* 260 */       localObject = System.getProperty("user.home");
/* 261 */       if (localObject != null)
/* 262 */         arrayOfString[(i++)] = ((String)localObject + "/Library/Fonts/");
/*     */     }
/*     */     else {
/* 265 */       arrayOfString = new String[1];
/* 266 */       i = 1;
/* 267 */       arrayOfString[0] = getUserFontCacheDir();
/*     */     }
/*     */ 
/* 270 */     for (int j = 0; j < i; j++) {
/* 271 */       localObject = new File(arrayOfString[j], paramString);
/* 272 */       if (((File)localObject).exists()) {
/* 273 */         return localObject;
/*     */       }
/*     */     }
/* 276 */     return null;
/*     */   }
/*     */ 
/*     */   static class FontNameInfo
/*     */   {
/*  34 */     boolean failed = false;
/*     */     String filename;
/*     */     String psName;
/*     */     String macFamily;
/*     */     String winFamily;
/*     */     String fullname;
/*  53 */     static Map<String, FontNameInfo> nameMap = new HashMap();
/*     */ 
/*     */     FontNameInfo(String paramString1, String paramString2, String paramString3, String paramString4)
/*     */     {
/*  43 */       this.filename = paramString1;
/*  44 */       this.psName = paramString2;
/*  45 */       this.macFamily = "Amble";
/*  46 */       this.winFamily = paramString3;
/*  47 */       this.fullname = paramString4;
/*     */ 
/*  49 */       nameMap.put(this.psName.toLowerCase(), this);
/*  50 */       nameMap.put(this.fullname.toLowerCase(), this);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.font.AmbleMapper
 * JD-Core Version:    0.6.2
 */