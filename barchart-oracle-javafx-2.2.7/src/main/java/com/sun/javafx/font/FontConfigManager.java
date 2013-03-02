/*     */ package com.sun.javafx.font;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.net.URL;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
/*     */ import java.util.Properties;
/*     */ 
/*     */ public class FontConfigManager
/*     */ {
/*  41 */   static boolean debugFonts = false;
/*  42 */   static boolean fontConfigFailed = false;
/*  43 */   static boolean useEmbeddedFontSupport = false;
/*     */ 
/*  87 */   private static final String[] fontConfigNames = { "sans:regular:roman", "sans:bold:roman", "sans:regular:italic", "sans:bold:italic", "serif:regular:roman", "serif:bold:roman", "serif:regular:italic", "serif:bold:italic", "monospace:regular:roman", "monospace:bold:roman", "monospace:regular:italic", "monospace:bold:italic" };
/*     */   private static FcCompFont[] fontConfigFonts;
/*     */   private static String defaultFontFile;
/*     */ 
/*     */   private static String[] getFontConfigNames()
/*     */   {
/* 113 */     return fontConfigNames;
/*     */   }
/*     */ 
/*     */   private static String getFCLocaleStr() {
/* 117 */     Locale localLocale = Locale.getDefault();
/* 118 */     String str1 = localLocale.getLanguage();
/* 119 */     String str2 = localLocale.getCountry();
/* 120 */     if (!str2.equals("")) {
/* 121 */       str1 = str1 + "-" + str2;
/*     */     }
/* 123 */     return str1;
/*     */   }
/*     */ 
/*     */   private static native boolean getFontConfig(String paramString, FcCompFont[] paramArrayOfFcCompFont, boolean paramBoolean);
/*     */ 
/*     */   private static synchronized void initFontConfigLogFonts()
/*     */   {
/* 135 */     if ((fontConfigFonts != null) || (fontConfigFailed)) {
/* 136 */       return;
/*     */     }
/*     */ 
/* 139 */     long l1 = 0L;
/* 140 */     if (debugFonts) {
/* 141 */       l1 = System.nanoTime();
/*     */     }
/*     */ 
/* 144 */     String[] arrayOfString = getFontConfigNames();
/* 145 */     FcCompFont[] arrayOfFcCompFont = new FcCompFont[arrayOfString.length];
/*     */ 
/* 147 */     for (int i = 0; i < arrayOfFcCompFont.length; i++) {
/* 148 */       arrayOfFcCompFont[i] = new FcCompFont();
/* 149 */       arrayOfFcCompFont[i].fcName = arrayOfString[i];
/* 150 */       j = arrayOfFcCompFont[i].fcName.indexOf(58);
/* 151 */       arrayOfFcCompFont[i].fcFamily = arrayOfFcCompFont[i].fcName.substring(0, j);
/* 152 */       arrayOfFcCompFont[i].style = (i % 4);
/*     */     }
/* 154 */     if ((useEmbeddedFontSupport) || (!getFontConfig(getFCLocaleStr(), arrayOfFcCompFont, true)))
/*     */     {
/* 157 */       EmbeddedFontSupport.initLogicalFonts(arrayOfFcCompFont);
/*     */     }
/* 159 */     FontConfigFont localFontConfigFont1 = null;
/*     */ 
/* 161 */     for (int j = 0; j < arrayOfFcCompFont.length; j++) {
/* 162 */       FcCompFont localFcCompFont1 = arrayOfFcCompFont[j];
/* 163 */       if (localFcCompFont1.firstFont == null) {
/* 164 */         if (debugFonts) {
/* 165 */           System.err.println("Fontconfig returned no font for " + arrayOfFcCompFont[j].fcName);
/*     */         }
/*     */ 
/* 168 */         fontConfigFailed = true;
/* 169 */       } else if (localFontConfigFont1 == null) {
/* 170 */         localFontConfigFont1 = localFcCompFont1.firstFont;
/* 171 */         defaultFontFile = localFontConfigFont1.fontFile;
/*     */       }
/*     */     }
/*     */ 
/* 175 */     if (localFontConfigFont1 == null) {
/* 176 */       if (debugFonts) {
/* 177 */         System.err.println("Fontconfig returned no fonts at all.");
/*     */       }
/* 179 */       fontConfigFailed = true;
/* 180 */       return;
/* 181 */     }if (fontConfigFailed) {
/* 182 */       for (j = 0; j < arrayOfFcCompFont.length; j++) {
/* 183 */         if (arrayOfFcCompFont[j].firstFont == null) {
/* 184 */           arrayOfFcCompFont[j].firstFont = localFontConfigFont1;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 189 */     fontConfigFonts = arrayOfFcCompFont;
/*     */ 
/* 191 */     if (debugFonts)
/*     */     {
/* 193 */       long l2 = System.nanoTime();
/* 194 */       System.err.println("Time spent accessing fontconfig=" + (l2 - l1) / 1000000L + "ms.");
/*     */ 
/* 197 */       for (int k = 0; k < fontConfigFonts.length; k++) {
/* 198 */         FcCompFont localFcCompFont2 = fontConfigFonts[k];
/* 199 */         System.err.println("FC font " + localFcCompFont2.fcName + " maps to " + localFcCompFont2.firstFont.fullName + " in file " + localFcCompFont2.firstFont.fontFile);
/*     */ 
/* 202 */         if (localFcCompFont2.allFonts != null)
/* 203 */           for (int m = 0; m < localFcCompFont2.allFonts.length; m++) {
/* 204 */             FontConfigFont localFontConfigFont2 = localFcCompFont2.allFonts[m];
/* 205 */             System.err.println(" " + m + ") Family=" + localFontConfigFont2.familyName + ", Style=" + localFontConfigFont2.styleStr + ", Fullname=" + localFontConfigFont2.fullName + ", File=" + localFontConfigFont2.fontFile);
/*     */           }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static native boolean populateMapsNative(HashMap<String, String> paramHashMap1, HashMap<String, String> paramHashMap2, HashMap<String, ArrayList<String>> paramHashMap, Locale paramLocale);
/*     */ 
/*     */   public static void populateMaps(HashMap<String, String> paramHashMap1, HashMap<String, String> paramHashMap2, HashMap<String, ArrayList<String>> paramHashMap, Locale paramLocale)
/*     */   {
/* 228 */     if ((fontConfigFailed) || (useEmbeddedFontSupport) || (!populateMapsNative(paramHashMap1, paramHashMap2, paramHashMap, paramLocale)))
/*     */     {
/* 232 */       EmbeddedFontSupport.populateMaps(paramHashMap1, paramHashMap2, paramHashMap, paramLocale);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static String mapFxToFcLogicalFamilyName(String paramString)
/*     */   {
/* 239 */     if (paramString.equals("serif"))
/* 240 */       return "serif";
/* 241 */     if (paramString.equals("monospaced")) {
/* 242 */       return "monospace";
/*     */     }
/* 244 */     return "sans";
/*     */   }
/*     */ 
/*     */   public static FcCompFont getFontConfigFont(String paramString, boolean paramBoolean1, boolean paramBoolean2)
/*     */   {
/* 251 */     initFontConfigLogFonts();
/*     */ 
/* 253 */     if (fontConfigFonts == null) {
/* 254 */       return null;
/*     */     }
/*     */ 
/* 257 */     String str = mapFxToFcLogicalFamilyName(paramString.toLowerCase());
/* 258 */     int i = paramBoolean1 ? 1 : 0;
/* 259 */     if (paramBoolean2) {
/* 260 */       i += 2;
/*     */     }
/*     */ 
/* 263 */     FcCompFont localFcCompFont = null;
/* 264 */     for (int j = 0; j < fontConfigFonts.length; j++) {
/* 265 */       if ((str.equals(fontConfigFonts[j].fcFamily)) && (i == fontConfigFonts[j].style))
/*     */       {
/* 267 */         localFcCompFont = fontConfigFonts[j];
/* 268 */         break;
/*     */       }
/*     */     }
/* 271 */     if (localFcCompFont == null) {
/* 272 */       localFcCompFont = fontConfigFonts[0];
/*     */     }
/*     */ 
/* 275 */     if (debugFonts) {
/* 276 */       System.err.println("FC name=" + str + " style=" + i + " uses " + localFcCompFont.firstFont.fullName + " in file: " + localFcCompFont.firstFont.fontFile);
/*     */     }
/*     */ 
/* 280 */     return localFcCompFont;
/*     */   }
/*     */ 
/*     */   public static String getDefaultFontPath()
/*     */   {
/* 285 */     if ((fontConfigFonts == null) && (!fontConfigFailed))
/*     */     {
/* 287 */       getFontConfigFont("System", false, false);
/*     */     }
/* 289 */     return defaultFontFile;
/*     */   }
/*     */ 
/*     */   public static ArrayList<String> getFileNames(FcCompFont paramFcCompFont, boolean paramBoolean)
/*     */   {
/* 295 */     ArrayList localArrayList = new ArrayList();
/*     */ 
/* 297 */     if (paramFcCompFont.allFonts != null) {
/* 298 */       int i = paramBoolean ? 1 : 0;
/* 299 */       for (int j = i; j < paramFcCompFont.allFonts.length; j++) {
/* 300 */         localArrayList.add(paramFcCompFont.allFonts[j].fontFile);
/*     */       }
/*     */     }
/* 303 */     return localArrayList;
/*     */   }
/*     */ 
/*     */   public static ArrayList<String> getFontNames(FcCompFont paramFcCompFont, boolean paramBoolean)
/*     */   {
/* 309 */     ArrayList localArrayList = new ArrayList();
/*     */ 
/* 311 */     if (paramFcCompFont.allFonts != null) {
/* 312 */       int i = paramBoolean ? 1 : 0;
/* 313 */       for (int j = i; j < paramFcCompFont.allFonts.length; j++) {
/* 314 */         localArrayList.add(paramFcCompFont.allFonts[j].fullName);
/*     */       }
/*     */     }
/* 317 */     return localArrayList;
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  46 */     AccessController.doPrivileged(new PrivilegedAction()
/*     */     {
/*     */       public Void run() {
/*  49 */         String str1 = System.getProperty("prism.debugfonts", "");
/*  50 */         FontConfigManager.debugFonts = "true".equals(str1);
/*  51 */         String str2 = System.getProperty("prism.embeddedfonts", "");
/*  52 */         FontConfigManager.useEmbeddedFontSupport = "true".equals(str2);
/*  53 */         return null;
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   private static class EmbeddedFontSupport
/*     */   {
/* 329 */     private static String fontDirProp = null;
/*     */     private static String fontDir;
/*     */ 
/*     */     private static void initEmbeddedFonts()
/*     */     {
/* 344 */       fontDirProp = System.getProperty("prism.fontdir");
/* 345 */       if (fontDirProp != null) {
/* 346 */         fontDir = fontDirProp;
/*     */       }
/*     */       else
/*     */       {
/*     */         try
/*     */         {
/* 352 */           FontConfigManager localFontConfigManager = FontConfigManager.class;
/* 353 */           String str1 = "FontConfigManager.class";
/* 354 */           String str2 = localFontConfigManager.getResource(str1).toString();
/* 355 */           if (FontConfigManager.debugFonts) {
/* 356 */             System.err.println("Class URL is " + str2);
/*     */           }
/* 358 */           if (str2 == null) {
/* 359 */             return;
/*     */           }
/* 361 */           int i = str2.indexOf("!");
/* 362 */           if ((str2.startsWith("jar:file:")) || (i != -1)) {
/* 363 */             str2 = str2.substring(9, i);
/* 364 */             int j = Math.max(str2.lastIndexOf("/"), str2.lastIndexOf("\\"));
/*     */ 
/* 366 */             str2 = str2.substring(0, j);
/*     */           }
/* 368 */           File localFile = new File(str2, "fonts");
/* 369 */           fontDir = localFile.getPath();
/* 370 */           if (FontConfigManager.debugFonts) {
/* 371 */             System.err.println("Font Dir is " + localFile + " exists = " + localFile.exists());
/*     */           }
/*     */         }
/*     */         catch (Exception localException)
/*     */         {
/* 376 */           if (FontConfigManager.debugFonts) {
/* 377 */             localException.printStackTrace();
/*     */           }
/* 379 */           fontDir = "/";
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     private static String getStyleStr(int paramInt) {
/* 385 */       switch (paramInt) { case 0:
/* 386 */         return "regular";
/*     */       case 1:
/* 387 */         return "bold";
/*     */       case 2:
/* 388 */         return "italic";
/*     */       case 3:
/* 389 */         return "bolditalic"; }
/* 390 */       return "regular";
/*     */     }
/*     */ 
/*     */     private static boolean exists(File paramFile)
/*     */     {
/* 396 */       return ((Boolean)AccessController.doPrivileged(new PrivilegedAction()
/*     */       {
/*     */         public Boolean run() {
/* 399 */           return Boolean.valueOf(this.val$f.exists());
/*     */         }
/*     */       })).booleanValue();
/*     */     }
/*     */ 
/*     */     static void initLogicalFonts(FontConfigManager.FcCompFont[] paramArrayOfFcCompFont)
/*     */     {
/* 428 */       Properties localProperties = new Properties();
/*     */       Object localObject;
/*     */       try
/*     */       {
/* 430 */         String str1 = fontDir + "/logicalfonts.properties";
/* 431 */         localObject = new FileInputStream(str1);
/* 432 */         localProperties.load((InputStream)localObject);
/* 433 */         ((FileInputStream)localObject).close();
/*     */       } catch (IOException localIOException) {
/* 435 */         if (FontConfigManager.debugFonts) {
/* 436 */           System.err.println(localIOException);
/* 437 */           return;
/*     */         }
/*     */       }
/* 440 */       for (int i = 0; i < paramArrayOfFcCompFont.length; i++) {
/* 441 */         localObject = paramArrayOfFcCompFont[i].fcFamily;
/* 442 */         String str2 = getStyleStr(paramArrayOfFcCompFont[i].style);
/* 443 */         String str3 = (String)localObject + "." + str2 + ".";
/* 444 */         ArrayList localArrayList = new ArrayList();
/*     */ 
/* 446 */         int j = 0;
/*     */         while (true) {
/* 448 */           String str4 = localProperties.getProperty(str3 + j + ".file");
/* 449 */           String str5 = localProperties.getProperty(str3 + j + ".font");
/* 450 */           j++;
/* 451 */           if (str4 == null) {
/*     */             break;
/*     */           }
/* 454 */           File localFile = new File(fontDir, str4);
/* 455 */           if (exists(localFile))
/*     */           {
/* 458 */             FontConfigManager.FontConfigFont localFontConfigFont = new FontConfigManager.FontConfigFont();
/* 459 */             localFontConfigFont.fontFile = localFile.getPath();
/* 460 */             localFontConfigFont.fullName = str5;
/* 461 */             localFontConfigFont.familyName = null;
/* 462 */             localFontConfigFont.styleStr = null;
/* 463 */             if (paramArrayOfFcCompFont[i].firstFont == null) {
/* 464 */               paramArrayOfFcCompFont[i].firstFont = localFontConfigFont;
/*     */             }
/* 466 */             localArrayList.add(localFontConfigFont);
/*     */           }
/*     */         }
/* 468 */         if (localArrayList.size() > 0) {
/* 469 */           paramArrayOfFcCompFont[i].allFonts = new FontConfigManager.FontConfigFont[localArrayList.size()];
/* 470 */           localArrayList.toArray(paramArrayOfFcCompFont[i].allFonts);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     static void populateMaps(HashMap<String, String> paramHashMap1, HashMap<String, String> paramHashMap2, HashMap<String, ArrayList<String>> paramHashMap, Locale paramLocale)
/*     */     {
/* 507 */       Properties localProperties = new Properties();
/* 508 */       int i = 0;
/*     */       try {
/* 510 */         String str1 = fontDir + "/allfonts.properties";
/* 511 */         FileInputStream localFileInputStream = new FileInputStream(str1);
/* 512 */         localProperties.load(localFileInputStream);
/* 513 */         i = 1;
/* 514 */         localFileInputStream.close();
/*     */       } catch (IOException localIOException) {
/* 516 */         if (FontConfigManager.debugFonts) {
/* 517 */           System.err.println(localIOException);
/* 518 */           System.err.println("Fall back to opening the files");
/*     */         }
/*     */       }
/*     */ 
/* 522 */       if (i != 0) {
/* 523 */         int j = 2147483647;
/*     */         try {
/* 525 */           j = Integer.parseInt(localProperties.getProperty("maxFont", ""));
/*     */         } catch (NumberFormatException localNumberFormatException) {
/*     */         }
/* 528 */         if (j <= 0) {
/* 529 */           j = 2147483647;
/*     */         }
/* 531 */         for (int k = 0; k < j; k++) {
/* 532 */           String str2 = localProperties.getProperty("family." + k);
/* 533 */           String str3 = localProperties.getProperty("font." + k);
/* 534 */           String str4 = localProperties.getProperty("file." + k);
/* 535 */           if (str4 == null) {
/*     */             break;
/*     */           }
/* 538 */           File localFile = new File(fontDir, str4);
/* 539 */           if (exists(localFile))
/*     */           {
/* 542 */             if ((str2 != null) && (str3 != null))
/*     */             {
/* 545 */               String str5 = str3.toLowerCase(Locale.ENGLISH);
/* 546 */               String str6 = str2.toLowerCase(Locale.ENGLISH);
/* 547 */               paramHashMap1.put(str5, localFile.getPath());
/* 548 */               paramHashMap2.put(str5, str2);
/* 549 */               ArrayList localArrayList = (ArrayList)paramHashMap.get(str6);
/*     */ 
/* 551 */               if (localArrayList == null) {
/* 552 */                 localArrayList = new ArrayList(4);
/* 553 */                 paramHashMap.put(str6, localArrayList);
/*     */               }
/* 555 */               localArrayList.add(str3);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     static
/*     */     {
/* 333 */       AccessController.doPrivileged(new PrivilegedAction()
/*     */       {
/*     */         public Void run() {
/* 336 */           FontConfigManager.EmbeddedFontSupport.access$000();
/* 337 */           return null;
/*     */         }
/*     */       });
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class FcCompFont
/*     */   {
/*     */     public String fcName;
/*     */     public String fcFamily;
/*     */     public int style;
/*     */     public FontConfigManager.FontConfigFont firstFont;
/*     */     public FontConfigManager.FontConfigFont[] allFonts;
/*     */   }
/*     */ 
/*     */   public static class FontConfigFont
/*     */   {
/*     */     public String familyName;
/*     */     public String styleStr;
/*     */     public String fullName;
/*     */     public String fontFile;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.font.FontConfigManager
 * JD-Core Version:    0.6.2
 */