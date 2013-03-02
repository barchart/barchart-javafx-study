/*      */ package com.sun.t2k;
/*      */ 
/*      */ import com.sun.javafx.PlatformUtil;
/*      */ import com.sun.javafx.font.FontConfigManager;
/*      */ import com.sun.javafx.font.FontFactory;
/*      */ import com.sun.javafx.font.FontResource;
/*      */ import com.sun.javafx.font.PGFont;
/*      */ import com.sun.javafx.runtime.NativeLibLoader;
/*      */ import java.io.File;
/*      */ import java.io.FilenameFilter;
/*      */ import java.io.InputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.security.PrivilegedExceptionAction;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.Locale;
/*      */ import java.util.Set;
/*      */ 
/*      */ public class T2KFontFactory
/*      */   implements FontFactory
/*      */ {
/*   27 */   static boolean debugFonts = false;
/*      */   static boolean isWindows;
/*      */   static boolean isLinux;
/*      */   static boolean isMacOSX;
/*      */   private static String jreFontDir;
/*      */   private static final String jreDefaultFont = "Lucida Sans Regular";
/*      */   private static final String jreDefaultFontLC = "lucida sans regular";
/*      */   private static final String jreDefaultFontFamily = "Lucida Sans";
/*      */   private static final String jreDefaultFontFile = "LucidaSansRegular.ttf";
/*   37 */   private static MacFontFinder macFontFinder = null;
/*      */ 
/*   45 */   static HashMap<String, FontResource> fontResourceMap = new HashMap();
/*      */ 
/*   49 */   static HashMap<String, CompositeFontResource> compResourceMap = new HashMap();
/*      */ 
/*   76 */   static T2KFontFactory theT2KFontFactory = null;
/*      */   ArrayList<WeakReference<T2KFontFile>> tmpFonts;
/*  667 */   private static final String[] STR_ARRAY = new String[0];
/*      */ 
/*  674 */   private static volatile HashMap<String, String> fontToFileMap = null;
/*      */ 
/*  677 */   private static HashMap<String, String> fileToFontMap = null;
/*      */ 
/*  683 */   private static HashMap<String, String> fontToFamilyNameMap = null;
/*      */ 
/*  690 */   private static HashMap<String, ArrayList<String>> familyToFontListMap = null;
/*      */ 
/*  694 */   static String sysFontDir = null;
/*  695 */   static String userFontDir = null;
/*      */   private static ArrayList<String> allFamilyNames;
/*      */   private static ArrayList<String> allFontNames;
/* 1141 */   private static Thread fileCloser = null;
/*      */   HashMap<String, T2KFontFile> embeddedFonts;
/* 1564 */   private static float systemFontSize = -1.0F;
/* 1565 */   private static String systemFontFamily = null;
/*      */ 
/*      */   public static synchronized T2KFontFactory getFontFactory()
/*      */   {
/*   78 */     if (theT2KFontFactory == null) {
/*   79 */       theT2KFontFactory = new T2KFontFactory();
/*      */     }
/*   81 */     return theT2KFontFactory;
/*      */   }
/*      */ 
/*      */   static String dotStyleStr(boolean paramBoolean1, boolean paramBoolean2)
/*      */   {
/*   88 */     if (!paramBoolean1) {
/*   89 */       if (!paramBoolean2) {
/*   90 */         return "";
/*      */       }
/*      */ 
/*   93 */       return ".italic";
/*      */     }
/*      */ 
/*   96 */     if (!paramBoolean2) {
/*   97 */       return ".bold";
/*      */     }
/*      */ 
/*  100 */     return ".bolditalic";
/*      */   }
/*      */ 
/*      */   static void storeInMap(String paramString, FontResource paramFontResource)
/*      */   {
/*  106 */     if ((paramString == null) || (paramFontResource == null)) {
/*  107 */       return;
/*      */     }
/*  109 */     if ((paramFontResource instanceof T2KCompositeFontResource)) {
/*  110 */       System.err.println(paramString + " is a composite " + paramFontResource);
/*      */ 
/*  112 */       Thread.dumpStack();
/*  113 */       return;
/*      */     }
/*  115 */     fontResourceMap.put(paramString.toLowerCase(), paramFontResource);
/*      */   }
/*      */ 
/*      */   synchronized void addDecodedFont(T2KFontFile paramT2KFontFile)
/*      */   {
/*  120 */     paramT2KFontFile.setIsDecoded(true);
/*  121 */     addTmpFont(paramT2KFontFile);
/*      */   }
/*      */ 
/*      */   synchronized void addTmpFont(T2KFontFile paramT2KFontFile) {
/*  125 */     if (this.tmpFonts == null)
/*  126 */       this.tmpFonts = new ArrayList();
/*      */     WeakReference localWeakReference;
/*  129 */     if (paramT2KFontFile.isRegistered())
/*  130 */       localWeakReference = new WeakReference(paramT2KFontFile);
/*      */     else {
/*  132 */       localWeakReference = paramT2KFontFile.createFileDisposer(this);
/*      */     }
/*  134 */     this.tmpFonts.add(localWeakReference);
/*  135 */     addFileCloserHook();
/*      */   }
/*      */ 
/*      */   synchronized void removeTmpFont(WeakReference<T2KFontFile> paramWeakReference) {
/*  139 */     if (this.tmpFonts != null)
/*  140 */       this.tmpFonts.remove(paramWeakReference);
/*      */   }
/*      */ 
/*      */   synchronized FontResource getFontResource(String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
/*      */   {
/*  151 */     if ((paramString == null) || (paramString.isEmpty())) {
/*  152 */       return null;
/*      */     }
/*      */ 
/*  155 */     String str1 = paramString.toLowerCase();
/*  156 */     String str2 = dotStyleStr(paramBoolean1, paramBoolean2);
/*      */ 
/*  159 */     Object localObject1 = lookupResource(str1 + str2, paramBoolean3);
/*  160 */     if (localObject1 != null)
/*  161 */       return localObject1;
/*      */     Iterator localIterator1;
/*  174 */     if ((this.embeddedFonts != null) && (paramBoolean3)) {
/*  175 */       localObject1 = lookupResource(str1 + str2, false);
/*  176 */       if (localObject1 != null) {
/*  177 */         return new T2KCompositeFontResource((FontResource)localObject1, str1 + str2);
/*      */       }
/*  179 */       for (localIterator1 = this.embeddedFonts.values().iterator(); localIterator1.hasNext(); ) { localObject2 = (T2KFontFile)localIterator1.next();
/*  180 */         localObject3 = ((T2KFontFile)localObject2).getFamilyName().toLowerCase();
/*  181 */         if (((String)localObject3).equals(str1)) {
/*  182 */           return new T2KCompositeFontResource((FontResource)localObject2, str1 + str2);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  191 */     if (isWindows) {
/*  192 */       int i = (paramBoolean1 ? 1 : 0) + (paramBoolean2 ? 2 : 0);
/*  193 */       localObject2 = WindowsFontMap.findFontFile(str1, i);
/*  194 */       if (localObject2 != null) {
/*  195 */         localObject1 = T2KFontFile.createFontResource(null, (String)localObject2);
/*  196 */         if (localObject1 != null) {
/*  197 */           if ((paramBoolean1 == ((FontResource)localObject1).isBold()) && (paramBoolean2 == ((FontResource)localObject1).isItalic()) && (!str2.isEmpty()))
/*      */           {
/*  200 */             storeInMap(str1 + str2, (FontResource)localObject1);
/*      */           }
/*  202 */           if (paramBoolean3) {
/*  203 */             localObject1 = new T2KCompositeFontResource((FontResource)localObject1, str1 + str2);
/*      */           }
/*      */ 
/*  206 */           return localObject1;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  211 */     ArrayList localArrayList = null;
/*  212 */     if (isMacOSX)
/*      */     {
/*  214 */       localObject1 = MacFontFinder.getMacFontResource(paramString, paramBoolean1, paramBoolean2, paramBoolean3);
/*  215 */       if (localObject1 != null) {
/*  216 */         return localObject1;
/*      */       }
/*      */ 
/*  226 */       if (familyToFontListMap != null)
/*  227 */         localArrayList = (ArrayList)familyToFontListMap.get(str1);
/*      */       else {
/*  229 */         familyToFontListMap = new HashMap(10);
/*      */       }
/*  231 */       if (localArrayList == null) {
/*  232 */         localObject2 = macFontFinder.getFontNamesOfFontFamily(paramString);
/*  233 */         if (localObject2 != null) {
/*  234 */           localArrayList = new ArrayList(localObject2.length);
/*  235 */           Collections.addAll(localArrayList, (Object[])localObject2);
/*  236 */           familyToFontListMap.put(str1, localArrayList);
/*      */         }
/*      */       }
/*      */     } else {
/*  240 */       getFullNameToFileMap();
/*  241 */       localArrayList = (ArrayList)familyToFontListMap.get(str1);
/*      */     }
/*      */ 
/*  244 */     if (localArrayList == null) {
/*  245 */       return null;
/*      */     }
/*      */ 
/*  248 */     Object localObject2 = null; Object localObject3 = null;
/*  249 */     Object localObject4 = null; Object localObject5 = null;
/*  250 */     for (String str3 : localArrayList) {
/*  251 */       String str4 = str3.toLowerCase();
/*  252 */       localObject1 = (FontResource)fontResourceMap.get(str4);
/*  253 */       if (localObject1 == null) {
/*  254 */         String str5 = findFile(str4);
/*  255 */         localObject1 = T2KFontFile.createFontResource(str3, str5);
/*  256 */         if (localObject1 != null)
/*      */         {
/*  259 */           storeInMap(str4, (FontResource)localObject1);
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  265 */         if ((paramBoolean1 == ((FontResource)localObject1).isBold()) && (paramBoolean2 == ((FontResource)localObject1).isItalic())) {
/*  266 */           storeInMap(str1 + str2, (FontResource)localObject1);
/*  267 */           if (paramBoolean3) {
/*  268 */             localObject1 = new T2KCompositeFontResource((FontResource)localObject1, str1 + str2);
/*      */           }
/*      */ 
/*  271 */           return localObject1;
/*      */         }
/*  273 */         if (!((FontResource)localObject1).isBold()) {
/*  274 */           if (!((FontResource)localObject1).isItalic())
/*  275 */             localObject2 = localObject1;
/*      */           else {
/*  277 */             localObject4 = localObject1;
/*      */           }
/*      */         }
/*  280 */         else if (!((FontResource)localObject1).isItalic())
/*  281 */           localObject3 = localObject1;
/*      */         else {
/*  283 */           localObject5 = localObject1;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  291 */     if ((!paramBoolean1) && (!paramBoolean2)) {
/*  292 */       if (localObject3 != null)
/*  293 */         localObject1 = localObject3;
/*  294 */       else if (localObject4 != null)
/*  295 */         localObject1 = localObject4;
/*      */       else
/*  297 */         localObject1 = localObject5;
/*      */     }
/*  299 */     else if ((paramBoolean1) && (!paramBoolean2)) {
/*  300 */       if (localObject2 != null)
/*  301 */         localObject1 = localObject2;
/*  302 */       else if (localObject5 != null)
/*  303 */         localObject1 = localObject5;
/*      */       else
/*  305 */         localObject1 = localObject4;
/*      */     }
/*  307 */     else if ((!paramBoolean1) && (paramBoolean2)) {
/*  308 */       if (localObject5 != null)
/*  309 */         localObject1 = localObject5;
/*  310 */       else if (localObject2 != null)
/*  311 */         localObject1 = localObject2;
/*      */       else {
/*  313 */         localObject1 = localObject3;
/*      */       }
/*      */     }
/*  316 */     else if (localObject4 != null)
/*  317 */       localObject1 = localObject4;
/*  318 */     else if (localObject3 != null)
/*  319 */       localObject1 = localObject3;
/*      */     else {
/*  321 */       localObject1 = localObject2;
/*      */     }
/*      */ 
/*  324 */     if (localObject1 != null) {
/*  325 */       storeInMap(str1 + str2, (FontResource)localObject1);
/*  326 */       if (paramBoolean3) {
/*  327 */         localObject1 = new T2KCompositeFontResource((FontResource)localObject1, str1 + str2);
/*      */       }
/*      */     }
/*  330 */     return localObject1;
/*      */   }
/*      */ 
/*      */   public synchronized PGFont createFont(String paramString, boolean paramBoolean1, boolean paramBoolean2, float paramFloat)
/*      */   {
/*  335 */     FontResource localFontResource = null;
/*  336 */     if ((paramString != null) && (!paramString.isEmpty())) {
/*  337 */       PGFont localPGFont = LogicalFont.getLogicalFont(paramString, paramBoolean1, paramBoolean2, paramFloat);
/*      */ 
/*  339 */       if (localPGFont != null) {
/*  340 */         return localPGFont;
/*      */       }
/*  342 */       localFontResource = getFontResource(paramString, paramBoolean1, paramBoolean2, true);
/*      */     }
/*      */ 
/*  345 */     if (localFontResource == null)
/*      */     {
/*  347 */       return LogicalFont.getLogicalFont("System", paramBoolean1, paramBoolean2, paramFloat);
/*      */     }
/*  349 */     return new T2KFont(localFontResource, localFontResource.getFullName(), paramFloat);
/*      */   }
/*      */ 
/*      */   public synchronized PGFont createFont(String paramString, float paramFloat)
/*      */   {
/*  354 */     FontResource localFontResource = null;
/*  355 */     if ((paramString != null) && (!paramString.isEmpty())) {
/*  356 */       PGFont localPGFont = LogicalFont.getLogicalFont(paramString, paramFloat);
/*      */ 
/*  358 */       if (localPGFont != null) {
/*  359 */         return localPGFont;
/*      */       }
/*      */ 
/*  362 */       localFontResource = getFontResource(paramString, null, true);
/*      */     }
/*  364 */     if (localFontResource == null) {
/*  365 */       return LogicalFont.getLogicalFont("System Regular", paramFloat);
/*      */     }
/*  367 */     return new T2KFont(localFontResource, localFontResource.getFullName(), paramFloat);
/*      */   }
/*      */ 
/*      */   public synchronized PGFont deriveFont(PGFont paramPGFont, boolean paramBoolean1, boolean paramBoolean2, float paramFloat)
/*      */   {
/*  372 */     FontResource localFontResource = paramPGFont.getFontResource();
/*      */ 
/*  374 */     return new T2KFont(localFontResource, localFontResource.getFullName(), paramFloat);
/*      */   }
/*      */ 
/*      */   static FontResource lookupResource(String paramString, boolean paramBoolean) {
/*  378 */     if (paramBoolean) {
/*  379 */       return (FontResource)compResourceMap.get(paramString);
/*      */     }
/*  381 */     return (FontResource)fontResourceMap.get(paramString);
/*      */   }
/*      */ 
/*      */   synchronized FontResource getFontResource(String paramString1, String paramString2, boolean paramBoolean)
/*      */   {
/*  387 */     Object localObject1 = null;
/*      */     String str;
/*      */     Object localObject2;
/*  390 */     if (paramString1 != null) {
/*  391 */       str = paramString1.toLowerCase();
/*      */ 
/*  395 */       localObject2 = lookupResource(str, paramBoolean);
/*  396 */       if (localObject2 != null) {
/*  397 */         return localObject2;
/*      */       }
/*      */ 
/*  404 */       if ((this.embeddedFonts != null) && (paramBoolean)) {
/*  405 */         localObject1 = lookupResource(str, false);
/*  406 */         if (localObject1 != null) {
/*  407 */           localObject1 = new T2KCompositeFontResource((FontResource)localObject1, str);
/*      */         }
/*  409 */         if (localObject1 != null) {
/*  410 */           return localObject1;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  418 */     if ((isWindows) && (paramString1 != null)) {
/*  419 */       str = paramString1.toLowerCase();
/*  420 */       localObject2 = WindowsFontMap.findFontFile(str, -1);
/*  421 */       if (localObject2 != null) {
/*  422 */         localObject1 = T2KFontFile.createFontResource(null, (String)localObject2);
/*  423 */         if (localObject1 != null) {
/*  424 */           if (paramBoolean) {
/*  425 */             localObject1 = new T2KCompositeFontResource((FontResource)localObject1, str);
/*      */           }
/*  427 */           return localObject1;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  432 */     getFullNameToFileMap();
/*      */ 
/*  434 */     if ((paramString1 != null) && (paramString2 != null))
/*      */     {
/*  438 */       localObject1 = T2KFontFile.createFontResource(paramString1, paramString2);
/*  439 */       if (localObject1 != null) {
/*  440 */         if (paramBoolean) {
/*  441 */           localObject1 = new T2KCompositeFontResource((FontResource)localObject1, paramString1.toLowerCase());
/*      */         }
/*  443 */         return localObject1;
/*      */       }
/*      */     }
/*      */ 
/*  447 */     if (paramString1 != null) {
/*  448 */       localObject1 = getFontResourceByFullName(paramString1, paramBoolean);
/*  449 */       if (localObject1 != null) {
/*  450 */         return localObject1;
/*      */       }
/*      */     }
/*      */ 
/*  454 */     if (paramString2 != null) {
/*  455 */       localObject1 = getFontResourceByFileName(paramString2, paramBoolean);
/*  456 */       if (localObject1 != null) {
/*  457 */         return localObject1;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  462 */     return null;
/*      */   }
/*      */ 
/*      */   static boolean isInstalledFont(String paramString)
/*      */   {
/*      */     File localFile;
/*      */     String str;
/*  469 */     if (isWindows) {
/*  470 */       if (paramString.toLowerCase().contains("\\windows\\fonts")) {
/*  471 */         return true;
/*      */       }
/*  473 */       localFile = new File(paramString);
/*  474 */       str = localFile.getName();
/*      */     } else {
/*  476 */       if ((isMacOSX) && (paramString.toLowerCase().contains("/library/fonts")))
/*      */       {
/*  479 */         return true;
/*      */       }
/*  481 */       localFile = new File(paramString);
/*      */ 
/*  483 */       str = localFile.getPath();
/*      */     }
/*      */ 
/*  486 */     getFullNameToFileMap();
/*  487 */     return fileToFontMap.get(str.toLowerCase()) != null;
/*      */   }
/*      */ 
/*      */   private synchronized FontResource getFontResourceByFileName(String paramString, boolean paramBoolean)
/*      */   {
/*  495 */     if (fontToFileMap.size() <= 1) {
/*  496 */       return null;
/*      */     }
/*      */ 
/*  506 */     String str1 = (String)fileToFontMap.get(paramString.toLowerCase());
/*  507 */     Object localObject = null;
/*      */     String str2;
/*  508 */     if (str1 == null)
/*      */     {
/*  513 */       localObject = T2KFontFile.createFontResource(paramString, 0);
/*      */ 
/*  515 */       if (localObject != null) {
/*  516 */         str2 = ((FontResource)localObject).getFullName().toLowerCase();
/*  517 */         storeInMap(str2, (FontResource)localObject);
/*      */ 
/*  520 */         if (paramBoolean)
/*  521 */           localObject = new T2KCompositeFontResource((FontResource)localObject, str2);
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*  526 */       str2 = str1.toLowerCase();
/*  527 */       localObject = lookupResource(str2, paramBoolean);
/*      */ 
/*  529 */       if (localObject == null) {
/*  530 */         String str3 = findFile(str2);
/*  531 */         if (str3 != null) {
/*  532 */           localObject = T2KFontFile.createFontResource(str1, str3);
/*  533 */           if (localObject != null) {
/*  534 */             storeInMap(str2, (FontResource)localObject);
/*      */           }
/*  536 */           if (paramBoolean)
/*      */           {
/*  538 */             localObject = new T2KCompositeFontResource((FontResource)localObject, str2);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  544 */     return localObject;
/*      */   }
/*      */ 
/*      */   private synchronized FontResource getFontResourceByFullName(String paramString, boolean paramBoolean)
/*      */   {
/*  553 */     String str1 = paramString.toLowerCase();
/*      */ 
/*  555 */     if (fontToFileMap.size() <= 1)
/*      */     {
/*  558 */       paramString = "Lucida Sans Regular";
/*      */     }
/*      */ 
/*  561 */     Object localObject = null;
/*  562 */     String str2 = findFile(str1);
/*  563 */     if (str2 != null) {
/*  564 */       DFontDecoder localDFontDecoder = null;
/*  565 */       if (isMacOSX) {
/*  566 */         localDFontDecoder = MacFontFinder.checkDFont(paramString, str2);
/*  567 */         if (localDFontDecoder != null) {
/*  568 */           str2 = localDFontDecoder.getFile().getPath();
/*      */         }
/*      */       }
/*  571 */       localObject = T2KFontFile.createFontResource(paramString, str2);
/*  572 */       if (localDFontDecoder != null) {
/*  573 */         if (localObject != null)
/*  574 */           addDecodedFont((T2KFontFile)localObject);
/*      */         else {
/*  576 */           localDFontDecoder.deleteFile();
/*      */         }
/*      */       }
/*  579 */       if (localObject != null) {
/*  580 */         storeInMap(str1, (FontResource)localObject);
/*  581 */         if (paramBoolean)
/*      */         {
/*  583 */           localObject = new T2KCompositeFontResource((FontResource)localObject, str1);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  588 */     return localObject;
/*      */   }
/*      */ 
/*      */   static FontResource getDefaultFontResource(boolean paramBoolean) {
/*  592 */     Object localObject1 = lookupResource("lucida sans regular", paramBoolean);
/*  593 */     if (localObject1 == null) {
/*  594 */       localObject1 = T2KFontFile.createFontResource("Lucida Sans Regular", jreFontDir + "LucidaSansRegular.ttf");
/*      */ 
/*  597 */       if (localObject1 == null)
/*      */       {
/*  600 */         for (Object localObject2 = fontToFileMap.keySet().iterator(); ((Iterator)localObject2).hasNext(); ) { String str1 = (String)((Iterator)localObject2).next();
/*  601 */           String str2 = findFile(str1);
/*  602 */           localObject1 = T2KFontFile.createFontResource("lucida sans regular", str2);
/*      */ 
/*  604 */           if (localObject1 != null) {
/*      */             break;
/*      */           }
/*      */         }
/*  608 */         if ((localObject1 == null) && (isLinux)) {
/*  609 */           localObject2 = FontConfigManager.getDefaultFontPath();
/*  610 */           if (localObject2 != null) {
/*  611 */             localObject1 = T2KFontFile.createFontResource("lucida sans regular", (String)localObject2);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  616 */         if (localObject1 == null) {
/*  617 */           return null;
/*      */         }
/*      */       }
/*  620 */       storeInMap("lucida sans regular", (FontResource)localObject1);
/*  621 */       if (paramBoolean) {
/*  622 */         localObject1 = new T2KCompositeFontResource((FontResource)localObject1, "lucida sans regular");
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  627 */     return localObject1;
/*      */   }
/*      */ 
/*      */   static String findFile(String paramString)
/*      */   {
/*  632 */     if (paramString.equals("Lucida Sans Regular".toLowerCase())) {
/*  633 */       return jreFontDir + "LucidaSansRegular.ttf";
/*      */     }
/*  635 */     String str = null;
/*  636 */     if (isWindows) {
/*  637 */       getFullNameToFileMap();
/*  638 */       str = findFileWindows(paramString);
/*  639 */     } else if (isMacOSX)
/*      */     {
/*  642 */       if (fontToFileMap != null) {
/*  643 */         str = (String)fontToFileMap.get(paramString);
/*      */       }
/*      */ 
/*  646 */       if (str == null) {
/*  647 */         str = macFontFinder.getFilePathOfFont(paramString, 12.0F);
/*      */       }
/*  649 */       if (str != null) {
/*  650 */         File localFile = new File(str);
/*  651 */         if (localFile.isAbsolute())
/*  652 */           return str;
/*      */       }
/*      */     }
/*  655 */     else if (isLinux) {
/*  656 */       str = (String)fontToFileMap.get(paramString.toLowerCase());
/*      */     }
/*      */ 
/*  663 */     return str;
/*      */   }
/*      */ 
/*      */   private static native byte[] getFontPath();
/*      */ 
/*      */   private static native String regReadFontLink(String paramString);
/*      */ 
/*      */   private static native String getEUDCFontFile();
/*      */ 
/*      */   static void getPlatformFontDirs()
/*      */   {
/*  703 */     if ((userFontDir != null) || (sysFontDir != null)) {
/*  704 */       return;
/*      */     }
/*  706 */     byte[] arrayOfByte = getFontPath();
/*  707 */     String str = new String(arrayOfByte);
/*      */ 
/*  709 */     int i = str.indexOf(59);
/*  710 */     if (i < 0) {
/*  711 */       sysFontDir = str;
/*      */     } else {
/*  713 */       sysFontDir = str.substring(0, i);
/*  714 */       userFontDir = str.substring(i + 1, str.length());
/*      */     }
/*      */   }
/*      */ 
/*      */   static ArrayList<String>[] getLinkedFonts(String paramString, boolean paramBoolean)
/*      */   {
/*  732 */     ArrayList[] arrayOfArrayList = new ArrayList[2];
/*      */ 
/*  735 */     arrayOfArrayList[0] = new ArrayList();
/*  736 */     arrayOfArrayList[1] = new ArrayList();
/*      */ 
/*  738 */     if (isMacOSX)
/*      */     {
/*  740 */       arrayOfArrayList[0].add("/Library/Fonts/Arial Unicode.ttf");
/*  741 */       arrayOfArrayList[1].add("Arial Unicode MS");
/*      */ 
/*  744 */       arrayOfArrayList[0].add(jreFontDir + "LucidaSansRegular.ttf");
/*  745 */       arrayOfArrayList[1].add("Lucida Sans Regular");
/*      */ 
/*  748 */       arrayOfArrayList[0].add("/System/Library/Fonts/Apple Symbols.ttf");
/*  749 */       arrayOfArrayList[1].add("Apple Symbols");
/*      */ 
/*  752 */       arrayOfArrayList[0].add("/System/Library/Fonts/STHeiti Light.ttf");
/*  753 */       arrayOfArrayList[1].add("Heiti SC Light");
/*      */ 
/*  755 */       return arrayOfArrayList;
/*      */     }
/*  757 */     if (!isWindows) {
/*  758 */       return arrayOfArrayList;
/*      */     }
/*      */ 
/*  761 */     if (paramBoolean) {
/*  762 */       arrayOfArrayList[0].add(null);
/*  763 */       arrayOfArrayList[1].add(paramString);
/*      */     }
/*      */ 
/*  766 */     String str1 = regReadFontLink(paramString);
/*  767 */     if ((str1 != null) && (str1.length() > 0))
/*      */     {
/*  769 */       localObject1 = str1.split("");
/*  770 */       int i = localObject1.length;
/*  771 */       for (int j = 0; j < i; j++) {
/*  772 */         String[] arrayOfString = localObject1[j].split(",");
/*  773 */         int k = arrayOfString.length;
/*  774 */         String str2 = arrayOfString[0];
/*  775 */         Object localObject2 = k > 1 ? arrayOfString[1] : null;
/*  776 */         if ((localObject2 == null) || (!arrayOfArrayList[1].contains(localObject2)))
/*      */         {
/*  778 */           if ((localObject2 != null) || (!arrayOfArrayList[0].contains(str2)))
/*      */           {
/*  781 */             arrayOfArrayList[0].add(str2);
/*  782 */             arrayOfArrayList[1].add(localObject2);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  786 */     Object localObject1 = getEUDCFontFile();
/*  787 */     if (localObject1 != null) {
/*  788 */       arrayOfArrayList[0].add(localObject1);
/*  789 */       arrayOfArrayList[1].add(null);
/*      */     }
/*      */ 
/*  793 */     arrayOfArrayList[0].add(jreFontDir + "LucidaSansRegular.ttf");
/*  794 */     arrayOfArrayList[1].add("Lucida Sans Regular");
/*      */ 
/*  796 */     if (PlatformUtil.isWinVistaOrLater())
/*      */     {
/*  798 */       arrayOfArrayList[0].add("mingliub.ttc");
/*  799 */       arrayOfArrayList[1].add("MingLiU-ExtB");
/*      */ 
/*  801 */       if (PlatformUtil.isWin7OrLater())
/*      */       {
/*  803 */         arrayOfArrayList[0].add("seguisym.ttf");
/*  804 */         arrayOfArrayList[1].add("Segoe UI Symbol");
/*      */       }
/*      */       else {
/*  807 */         arrayOfArrayList[0].add("cambria.ttc");
/*  808 */         arrayOfArrayList[1].add("Cambria Math");
/*      */       }
/*      */     }
/*  811 */     return arrayOfArrayList;
/*      */   }
/*      */ 
/*      */   private static void resolveWindowsFonts(HashMap<String, String> paramHashMap1, HashMap<String, String> paramHashMap2, HashMap<String, ArrayList<String>> paramHashMap)
/*      */   {
/*  839 */     ArrayList localArrayList1 = null;
/*  840 */     for (Object localObject1 = paramHashMap2.keySet().iterator(); ((Iterator)localObject1).hasNext(); ) { localObject2 = (String)((Iterator)localObject1).next();
/*  841 */       localObject3 = (String)paramHashMap1.get(localObject2);
/*  842 */       if (localObject3 == null) {
/*  843 */         int j = ((String)localObject2).indexOf("  ");
/*  844 */         if (j > 0) {
/*  845 */           str2 = ((String)localObject2).substring(0, j);
/*  846 */           str2 = str2.concat(((String)localObject2).substring(j + 1));
/*  847 */           localObject3 = (String)paramHashMap1.get(str2);
/*      */ 
/*  851 */           if ((localObject3 != null) && (!paramHashMap2.containsKey(str2)))
/*      */           {
/*  853 */             paramHashMap1.remove(str2);
/*  854 */             paramHashMap1.put(localObject2, localObject3);
/*      */           }
/*  856 */         } else if (((String)localObject2).equals("marlett")) {
/*  857 */           paramHashMap1.put(localObject2, "marlett.ttf");
/*  858 */         } else if (((String)localObject2).equals("david")) {
/*  859 */           localObject3 = (String)paramHashMap1.get("david regular");
/*  860 */           if (localObject3 != null) {
/*  861 */             paramHashMap1.remove("david regular");
/*  862 */             paramHashMap1.put("david", localObject3);
/*      */           }
/*      */         } else {
/*  865 */           if (localArrayList1 == null) {
/*  866 */             localArrayList1 = new ArrayList();
/*      */           }
/*  868 */           localArrayList1.add(localObject2);
/*      */         }
/*      */       }
/*      */     }
/*      */     Object localObject2;
/*      */     Object localObject3;
/*      */     String str2;
/*  873 */     if (localArrayList1 != null) {
/*  874 */       localObject1 = new HashSet();
/*      */ 
/*  877 */       localObject2 = new HashMap();
/*  878 */       ((HashMap)localObject2).putAll(paramHashMap1);
/*  879 */       for (localObject3 = paramHashMap2.keySet().iterator(); ((Iterator)localObject3).hasNext(); ) { str1 = (String)((Iterator)localObject3).next();
/*  880 */         ((HashMap)localObject2).remove(str1);
/*      */       }
/*  882 */       String str1;
/*  882 */       for (localObject3 = ((HashMap)localObject2).keySet().iterator(); ((Iterator)localObject3).hasNext(); ) { str1 = (String)((Iterator)localObject3).next();
/*  883 */         ((HashSet)localObject1).add(((HashMap)localObject2).get(str1));
/*  884 */         paramHashMap1.remove(str1);
/*      */       }
/*  886 */       resolveFontFiles((HashSet)localObject1, localArrayList1, paramHashMap1, paramHashMap2, paramHashMap);
/*      */ 
/*  895 */       if (localArrayList1.size() > 0) {
/*  896 */         int i = localArrayList1.size();
/*  897 */         for (int k = 0; k < i; k++) {
/*  898 */           str2 = (String)localArrayList1.get(k);
/*  899 */           String str3 = (String)paramHashMap2.get(str2);
/*  900 */           if (str3 != null) {
/*  901 */             ArrayList localArrayList2 = (ArrayList)paramHashMap.get(str3);
/*  902 */             if ((localArrayList2 != null) && 
/*  903 */               (localArrayList2.size() <= 1)) {
/*  904 */               paramHashMap.remove(str3);
/*      */             }
/*      */           }
/*      */ 
/*  908 */           paramHashMap2.remove(str2);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   static void resolveFontFiles(HashSet<String> paramHashSet, ArrayList<String> paramArrayList, HashMap<String, String> paramHashMap1, HashMap<String, String> paramHashMap2, HashMap<String, ArrayList<String>> paramHashMap)
/*      */   {
/*  921 */     for (String str1 : paramHashSet) try { int i = 0;
/*      */ 
/*  925 */         String str2 = getPathNameWindows(str1);
/*      */         T2KFontFile localT2KFontFile;
/*      */         do { localT2KFontFile = T2KFontFile.createFontResource(str2, i++);
/*  928 */           if (localT2KFontFile == null) {
/*      */             break;
/*      */           }
/*  931 */           String str3 = localT2KFontFile.getFullName().toLowerCase();
/*  932 */           String str4 = localT2KFontFile.getLocaleFullName().toLowerCase();
/*  933 */           if ((paramArrayList.contains(str3)) || (paramArrayList.contains(str4)))
/*      */           {
/*  935 */             paramHashMap1.put(str3, str1);
/*  936 */             paramArrayList.remove(str3);
/*      */ 
/*  946 */             if (paramArrayList.contains(str4)) {
/*  947 */               paramArrayList.remove(str4);
/*  948 */               String str5 = localT2KFontFile.getFamilyName();
/*  949 */               String str6 = str5.toLowerCase();
/*  950 */               paramHashMap2.remove(str4);
/*  951 */               paramHashMap2.put(str3, str5);
/*  952 */               ArrayList localArrayList = (ArrayList)paramHashMap.get(str6);
/*      */ 
/*  954 */               if (localArrayList != null) {
/*  955 */                 localArrayList.remove(localT2KFontFile.getLocaleFullName());
/*      */               }
/*      */               else
/*      */               {
/*  961 */                 String str7 = localT2KFontFile.getLocaleFamilyName().toLowerCase();
/*      */ 
/*  963 */                 localArrayList = (ArrayList)paramHashMap.get(str7);
/*      */ 
/*  965 */                 if (localArrayList != null) {
/*  966 */                   paramHashMap.remove(str7);
/*      */                 }
/*  968 */                 localArrayList = new ArrayList();
/*  969 */                 paramHashMap.put(str6, localArrayList);
/*      */               }
/*  971 */               localArrayList.add(localT2KFontFile.getFullName());
/*      */             }
/*      */           }
/*      */         }
/*      */ 
/*  976 */         while (i < localT2KFontFile.getFontCount());
/*      */       } catch (Exception localException) {
/*  978 */         if (debugFonts)
/*  979 */           localException.printStackTrace();
/*      */       }
/*      */   }
/*      */ 
/*      */   static native void populateFontFileNameMap(HashMap<String, String> paramHashMap1, HashMap<String, String> paramHashMap2, HashMap<String, ArrayList<String>> paramHashMap, Locale paramLocale);
/*      */ 
/*      */   static String findFileWindows(String paramString)
/*      */   {
/*  994 */     String str = (String)fontToFileMap.get(paramString);
/*      */ 
/*  996 */     return getPathNameWindows(str);
/*      */   }
/*      */ 
/*      */   static String getPathNameWindows(String paramString) {
/* 1000 */     if (paramString == null) {
/* 1001 */       return null;
/*      */     }
/*      */ 
/* 1004 */     getPlatformFontDirs();
/* 1005 */     File localFile = new File(paramString);
/* 1006 */     if (localFile.isAbsolute()) {
/* 1007 */       return paramString;
/*      */     }
/* 1009 */     if (userFontDir == null) {
/* 1010 */       return sysFontDir + "\\" + paramString;
/*      */     }
/*      */ 
/* 1013 */     String str = (String)AccessController.doPrivileged(new PrivilegedAction()
/*      */     {
/*      */       public String run() {
/* 1016 */         File localFile = new File(T2KFontFactory.sysFontDir + "\\" + this.val$filename);
/* 1017 */         if (localFile.exists()) {
/* 1018 */           return localFile.getAbsolutePath();
/*      */         }
/*      */ 
/* 1021 */         return T2KFontFactory.userFontDir + "\\" + this.val$filename;
/*      */       }
/*      */     });
/* 1026 */     if (str != null) {
/* 1027 */       return str;
/*      */     }
/* 1029 */     return null;
/*      */   }
/*      */ 
/*      */   public String[] getFontFamilyNames()
/*      */   {
/* 1034 */     if (allFamilyNames == null)
/*      */     {
/* 1041 */       ArrayList localArrayList = new ArrayList();
/* 1042 */       LogicalFont.addFamilies(localArrayList);
/* 1043 */       localArrayList.add("Lucida Sans");
/*      */       Object localObject1;
/* 1049 */       if (this.embeddedFonts != null)
/* 1050 */         for (localObject1 = this.embeddedFonts.values().iterator(); ((Iterator)localObject1).hasNext(); ) { localObject2 = (T2KFontFile)((Iterator)localObject1).next();
/* 1051 */           if (!localArrayList.contains(((T2KFontFile)localObject2).getFamilyName()))
/* 1052 */             localArrayList.add(((T2KFontFile)localObject2).getFamilyName());
/*      */         }
/*      */       Object localObject2;
/* 1055 */       if (isMacOSX) {
/* 1056 */         localObject1 = macFontFinder.getAllAvailableFontFamilies();
/* 1057 */         for (Object localObject3 : localObject1)
/* 1058 */           if (!localArrayList.contains(localObject3))
/* 1059 */             localArrayList.add(localObject3);
/*      */       }
/*      */       else
/*      */       {
/* 1063 */         getFullNameToFileMap();
/* 1064 */         for (localObject1 = fontToFamilyNameMap.values().iterator(); ((Iterator)localObject1).hasNext(); ) { localObject2 = (String)((Iterator)localObject1).next();
/* 1065 */           if (!localArrayList.contains(localObject2)) {
/* 1066 */             localArrayList.add(localObject2);
/*      */           }
/*      */         }
/*      */       }
/* 1070 */       Collections.sort(localArrayList);
/* 1071 */       allFamilyNames = new ArrayList(localArrayList);
/*      */     }
/* 1073 */     return (String[])allFamilyNames.toArray(STR_ARRAY);
/*      */   }
/*      */ 
/*      */   public String[] getFontFullNames()
/*      */   {
/* 1078 */     if (allFontNames == null)
/*      */     {
/* 1085 */       ArrayList localArrayList = new ArrayList();
/* 1086 */       LogicalFont.addFullNames(localArrayList);
/* 1087 */       localArrayList.add("Lucida Sans Regular");
/* 1088 */       if (this.embeddedFonts != null)
/* 1089 */         for (localIterator1 = this.embeddedFonts.values().iterator(); localIterator1.hasNext(); ) { localObject = (T2KFontFile)localIterator1.next();
/* 1090 */           if (!localArrayList.contains(((T2KFontFile)localObject).getFullName()))
/* 1091 */             localArrayList.add(((T2KFontFile)localObject).getFullName());
/*      */         }
/* 1096 */       Object localObject;
/* 1095 */       getFullNameToFileMap();
/* 1096 */       for (Iterator localIterator1 = familyToFontListMap.values().iterator(); localIterator1.hasNext(); ) { localObject = (ArrayList)localIterator1.next();
/* 1097 */         for (String str : (ArrayList)localObject) {
/* 1098 */           localArrayList.add(str);
/*      */         }
/*      */       }
/* 1101 */       Collections.sort(localArrayList);
/* 1102 */       allFontNames = localArrayList;
/*      */     }
/* 1104 */     return (String[])allFontNames.toArray(STR_ARRAY);
/*      */   }
/*      */ 
/*      */   public String[] getFontFullNames(String paramString)
/*      */   {
/* 1110 */     String[] arrayOfString = LogicalFont.getFontsInFamily(paramString);
/* 1111 */     if (arrayOfString != null)
/*      */     {
/* 1113 */       return arrayOfString;
/*      */     }
/*      */ 
/* 1116 */     if (this.embeddedFonts != null) {
/* 1117 */       localArrayList = null;
/* 1118 */       for (T2KFontFile localT2KFontFile : this.embeddedFonts.values()) {
/* 1119 */         if (localT2KFontFile.getFamilyName().equalsIgnoreCase(paramString)) {
/* 1120 */           if (localArrayList == null) {
/* 1121 */             localArrayList = new ArrayList();
/*      */           }
/* 1123 */           localArrayList.add(localT2KFontFile.getFullName());
/*      */         }
/*      */       }
/* 1126 */       if (localArrayList != null) {
/* 1127 */         return (String[])localArrayList.toArray(STR_ARRAY);
/*      */       }
/*      */     }
/*      */ 
/* 1131 */     getFullNameToFileMap();
/* 1132 */     paramString = paramString.toLowerCase();
/* 1133 */     ArrayList localArrayList = (ArrayList)familyToFontListMap.get(paramString);
/* 1134 */     if (localArrayList != null) {
/* 1135 */       return (String[])localArrayList.toArray(STR_ARRAY);
/*      */     }
/* 1137 */     return STR_ARRAY;
/*      */   }
/*      */ 
/*      */   private synchronized void addFileCloserHook()
/*      */   {
/* 1144 */     if (fileCloser == null) {
/* 1145 */       final Runnable local3 = new Runnable()
/*      */       {
/*      */         public void run()
/*      */         {
/*      */           Iterator localIterator;
/* 1147 */           if (T2KFontFactory.this.embeddedFonts != null)
/* 1148 */             for (localIterator = T2KFontFactory.this.embeddedFonts.values().iterator(); localIterator.hasNext(); ) { localObject = (T2KFontFile)localIterator.next();
/* 1149 */               ((T2KFontFile)localObject).disposeOnShutdown();
/*      */             }
/*      */           Object localObject;
/* 1152 */           if (T2KFontFactory.this.tmpFonts != null)
/* 1153 */             for (localIterator = T2KFontFactory.this.tmpFonts.iterator(); localIterator.hasNext(); ) { localObject = (WeakReference)localIterator.next();
/* 1154 */               T2KFontFile localT2KFontFile = (T2KFontFile)((WeakReference)localObject).get();
/* 1155 */               if (localT2KFontFile != null)
/* 1156 */                 localT2KFontFile.disposeOnShutdown();
/*      */             }
/*      */         }
/*      */       };
/* 1162 */       AccessController.doPrivileged(new PrivilegedAction()
/*      */       {
/*      */         public Object run()
/*      */         {
/* 1169 */           Object localObject1 = Thread.currentThread().getThreadGroup();
/* 1170 */           for (Object localObject2 = localObject1; 
/* 1171 */             localObject2 != null; localObject2 = ((ThreadGroup)localObject1).getParent()) localObject1 = localObject2;
/* 1172 */           T2KFontFactory.access$102(new Thread((ThreadGroup)localObject1, local3));
/* 1173 */           T2KFontFactory.fileCloser.setContextClassLoader(null);
/* 1174 */           Runtime.getRuntime().addShutdownHook(T2KFontFactory.fileCloser);
/* 1175 */           return null;
/*      */         }
/*      */       });
/*      */     }
/*      */   }
/*      */ 
/*      */   public PGFont loadEmbeddedFont(String paramString, InputStream paramInputStream, float paramFloat, boolean paramBoolean)
/*      */   {
/* 1185 */     T2KFontFile localT2KFontFile = null;
/* 1186 */     FontFileWriter localFontFileWriter = new FontFileWriter();
/*      */     try
/*      */     {
/* 1190 */       File localFile = localFontFileWriter.openFile();
/* 1191 */       byte[] arrayOfByte = new byte[8192];
/*      */       while (true) {
/* 1193 */         int i = paramInputStream.read(arrayOfByte);
/* 1194 */         if (i < 0) {
/*      */           break;
/*      */         }
/* 1197 */         localFontFileWriter.writeBytes(arrayOfByte, 0, i);
/*      */       }
/* 1199 */       localFontFileWriter.closeFile();
/*      */ 
/* 1201 */       localT2KFontFile = loadEmbeddedFont(paramString, localFile.getPath(), paramBoolean, true, localFontFileWriter.isTracking());
/*      */ 
/* 1204 */       if (localT2KFontFile != null)
/*      */       {
/* 1207 */         if (localT2KFontFile.isDecoded()) {
/* 1208 */           localFontFileWriter.deleteFile();
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1224 */       addFileCloserHook();
/*      */     } catch (Exception localException) {
/* 1226 */       localFontFileWriter.deleteFile();
/*      */     }
/*      */     finally
/*      */     {
/* 1233 */       if (localT2KFontFile == null) {
/* 1234 */         localFontFileWriter.deleteFile();
/*      */       }
/*      */     }
/* 1237 */     if (localT2KFontFile != null) {
/* 1238 */       if (paramFloat <= 0.0F) paramFloat = getSystemFontSize();
/* 1239 */       return new T2KFont(localT2KFontFile, localT2KFontFile.getFullName(), paramFloat);
/*      */     }
/* 1241 */     return null;
/*      */   }
/*      */ 
/*      */   public PGFont loadEmbeddedFont(String paramString1, String paramString2, float paramFloat, boolean paramBoolean)
/*      */   {
/* 1258 */     addFileCloserHook();
/* 1259 */     T2KFontFile localT2KFontFile = loadEmbeddedFont(paramString1, paramString2, paramBoolean, false, false);
/* 1260 */     if (localT2KFontFile != null) {
/* 1261 */       if (paramFloat <= 0.0F) paramFloat = getSystemFontSize();
/* 1262 */       return new T2KFont(localT2KFontFile, localT2KFontFile.getFullName(), paramFloat);
/*      */     }
/* 1264 */     return null;
/*      */   }
/*      */ 
/*      */   private void removeEmbeddedFont(String paramString)
/*      */   {
/* 1272 */     T2KFontFile localT2KFontFile = (T2KFontFile)this.embeddedFonts.get(paramString);
/* 1273 */     if (localT2KFontFile == null) {
/* 1274 */       return;
/*      */     }
/* 1276 */     this.embeddedFonts.remove(paramString);
/* 1277 */     String str1 = paramString.toLowerCase();
/* 1278 */     fontResourceMap.remove(str1);
/* 1279 */     compResourceMap.remove(str1);
/*      */ 
/* 1284 */     for (String str2 : compResourceMap.keySet()) {
/* 1285 */       CompositeFontResource localCompositeFontResource = (CompositeFontResource)compResourceMap.get(str2);
/* 1286 */       if (localCompositeFontResource.getSlotResource(0) == localT2KFontFile)
/* 1287 */         compResourceMap.remove(str2);
/*      */     }
/*      */   }
/*      */ 
/*      */   private T2KFontFile loadEmbeddedFont(String paramString1, String paramString2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
/*      */   {
/* 1296 */     T2KFontFile localT2KFontFile = T2KFontFile.createFontResource(paramString1, paramString2, paramBoolean1, paramBoolean2, paramBoolean3);
/*      */ 
/* 1298 */     if (localT2KFontFile == null) {
/* 1299 */       return null;
/*      */     }
/*      */ 
/* 1302 */     if (!paramBoolean1)
/*      */     {
/* 1309 */       if ((paramBoolean2) && (!localT2KFontFile.isDecoded())) {
/* 1310 */         addTmpFont(localT2KFontFile);
/*      */       }
/* 1312 */       return localT2KFontFile;
/*      */     }
/*      */ 
/* 1315 */     if (this.embeddedFonts == null) {
/* 1316 */       this.embeddedFonts = new HashMap();
/*      */     }
/*      */ 
/* 1327 */     if ((paramString1 != null) && (!paramString1.isEmpty())) {
/* 1328 */       this.embeddedFonts.put(paramString1, localT2KFontFile);
/* 1329 */       storeInMap(paramString1, localT2KFontFile);
/*      */     }
/*      */ 
/* 1332 */     String str1 = localT2KFontFile.getFullName();
/* 1333 */     removeEmbeddedFont(str1);
/* 1334 */     this.embeddedFonts.put(str1, localT2KFontFile);
/* 1335 */     storeInMap(str1, localT2KFontFile);
/* 1336 */     String str2 = localT2KFontFile.getFamilyName() + dotStyleStr(localT2KFontFile.isBold(), localT2KFontFile.isItalic());
/*      */ 
/* 1339 */     storeInMap(str2, localT2KFontFile);
/*      */ 
/* 1347 */     compResourceMap.remove(str2.toLowerCase());
/* 1348 */     return localT2KFontFile;
/*      */   }
/*      */ 
/*      */   private static void logFontInfo(String paramString, HashMap<String, String> paramHashMap1, HashMap<String, String> paramHashMap2, HashMap<String, ArrayList<String>> paramHashMap)
/*      */   {
/* 1357 */     System.err.println(paramString);
/* 1358 */     for (Iterator localIterator = paramHashMap1.keySet().iterator(); localIterator.hasNext(); ) { str = (String)localIterator.next();
/* 1359 */       System.err.println("font=" + str + " file=" + (String)paramHashMap1.get(str));
/*      */     }
/* 1362 */     String str;
/* 1362 */     for (localIterator = paramHashMap2.keySet().iterator(); localIterator.hasNext(); ) { str = (String)localIterator.next();
/* 1363 */       System.err.println("font=" + str + " family=" + (String)paramHashMap2.get(str));
/*      */     }
/*      */ 
/* 1366 */     for (localIterator = paramHashMap.keySet().iterator(); localIterator.hasNext(); ) { str = (String)localIterator.next();
/* 1367 */       System.err.println("family=" + str + " fonts=" + paramHashMap.get(str));
/*      */     }
/*      */   }
/*      */ 
/*      */   private static synchronized HashMap<String, String> getFullNameToFileMap()
/*      */   {
/* 1373 */     if (fontToFileMap == null)
/*      */     {
/* 1376 */       HashMap localHashMap = new HashMap(100);
/* 1377 */       fontToFamilyNameMap = new HashMap(100);
/* 1378 */       familyToFontListMap = new HashMap(50);
/* 1379 */       fileToFontMap = new HashMap(100);
/*      */ 
/* 1381 */       if (isWindows) {
/* 1382 */         getPlatformFontDirs();
/* 1383 */         populateFontFileNameMap(localHashMap, fontToFamilyNameMap, familyToFontListMap, Locale.ENGLISH);
/*      */ 
/* 1388 */         if (debugFonts) {
/* 1389 */           System.err.println("Windows Locale ID=" + getSystemLCID());
/* 1390 */           logFontInfo(" *** WINDOWS FONTS BEFORE RESOLVING", localHashMap, fontToFamilyNameMap, familyToFontListMap);
/*      */         }
/*      */ 
/* 1396 */         resolveWindowsFonts(localHashMap, fontToFamilyNameMap, familyToFontListMap);
/*      */ 
/* 1400 */         if (debugFonts) {
/* 1401 */           logFontInfo(" *** WINDOWS FONTS AFTER RESOLVING", localHashMap, fontToFamilyNameMap, familyToFontListMap);
/*      */         }
/*      */ 
/*      */       }
/* 1407 */       else if (isMacOSX) {
/* 1408 */         macFontFinder.populateFontFileNameMap(localHashMap, fontToFamilyNameMap, familyToFontListMap, Locale.ENGLISH);
/*      */       }
/* 1413 */       else if (isLinux) {
/* 1414 */         FontConfigManager.populateMaps(localHashMap, fontToFamilyNameMap, familyToFontListMap, Locale.getDefault());
/*      */ 
/* 1418 */         if (debugFonts) {
/* 1419 */           logFontInfo(" *** FONTCONFIG LOCATED FONTS:", localHashMap, fontToFamilyNameMap, familyToFontListMap);
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1425 */         fontToFileMap = localHashMap;
/* 1426 */         return fontToFileMap;
/*      */       }
/*      */       try
/*      */       {
/* 1430 */         AccessController.doPrivileged(new PrivilegedExceptionAction()
/*      */         {
/*      */           public Void run() {
/* 1433 */             T2KFontFactory.populateFontFileNameMapGeneric(T2KFontFactory.jreFontDir);
/* 1434 */             return null;
/*      */           }
/*      */ 
/*      */         });
/*      */       }
/*      */       catch (Exception localException)
/*      */       {
/*      */       }
/*      */ 
/* 1444 */       for (String str1 : localHashMap.keySet()) {
/* 1445 */         String str2 = (String)localHashMap.get(str1);
/* 1446 */         fileToFontMap.put(str2.toLowerCase(), str1);
/*      */       }
/*      */ 
/* 1449 */       fontToFileMap = localHashMap;
/*      */     }
/*      */ 
/* 1459 */     return fontToFileMap;
/*      */   }
/*      */ 
/*      */   static void addToMaps(T2KFontFile paramT2KFontFile)
/*      */   {
/* 1492 */     if (paramT2KFontFile == null) {
/* 1493 */       return;
/*      */     }
/*      */ 
/* 1496 */     String str1 = paramT2KFontFile.getFullName();
/* 1497 */     String str2 = paramT2KFontFile.getFamilyName();
/*      */ 
/* 1499 */     if ((str1 == null) || (str2 == null)) {
/* 1500 */       return;
/*      */     }
/*      */ 
/* 1503 */     String str3 = str1.toLowerCase();
/* 1504 */     String str4 = str2.toLowerCase();
/*      */ 
/* 1506 */     fontToFileMap.put(str3, paramT2KFontFile.getFileName());
/* 1507 */     fontToFamilyNameMap.put(str3, str2);
/* 1508 */     ArrayList localArrayList = (ArrayList)familyToFontListMap.get(str4);
/* 1509 */     if (localArrayList == null) {
/* 1510 */       localArrayList = new ArrayList();
/* 1511 */       familyToFontListMap.put(str4, localArrayList);
/*      */     }
/* 1513 */     localArrayList.add(str1);
/*      */   }
/*      */ 
/*      */   static void populateFontFileNameMapGeneric(String paramString)
/*      */   {
/* 1533 */     File localFile = new File(paramString);
/* 1534 */     String[] arrayOfString = localFile.list(TTFilter.getInstance());
/*      */ 
/* 1536 */     if (arrayOfString == null) {
/* 1537 */       return;
/*      */     }
/*      */ 
/* 1540 */     for (int i = 0; i < arrayOfString.length; i++)
/*      */       try {
/* 1542 */         String str = paramString + File.separator + arrayOfString[i];
/* 1543 */         int j = 0;
/* 1544 */         T2KFontFile localT2KFontFile = T2KFontFile.createFontResource(str, j++);
/* 1545 */         if (localT2KFontFile != null)
/*      */         {
/* 1548 */           addToMaps(localT2KFontFile);
/* 1549 */           while (j < localT2KFontFile.getFontCount()) {
/* 1550 */             localT2KFontFile = T2KFontFile.createFontResource(str, j++);
/* 1551 */             if (localT2KFontFile == null) {
/*      */               break;
/*      */             }
/* 1554 */             addToMaps(localT2KFontFile);
/*      */           }
/*      */         }
/*      */       }
/*      */       catch (Exception localException)
/*      */       {
/*      */       }
/*      */   }
/*      */ 
/*      */   private static native int getSystemFontSizeNative();
/*      */ 
/*      */   private static native String getSystemFontNative();
/*      */ 
/*      */   public static float getSystemFontSize() {
/* 1568 */     if (systemFontSize == -1.0F) {
/* 1569 */       if (isWindows)
/* 1570 */         systemFontSize = getSystemFontSizeNative();
/* 1571 */       else if (isMacOSX)
/* 1572 */         systemFontSize = 13.0F;
/*      */       else {
/* 1574 */         systemFontSize = 13.0F;
/*      */       }
/*      */     }
/* 1577 */     return systemFontSize;
/*      */   }
/*      */ 
/*      */   public static String getSystemFont() {
/* 1581 */     if (systemFontFamily == null) {
/* 1582 */       if (isWindows) {
/* 1583 */         systemFontFamily = getSystemFontNative();
/* 1584 */         if (systemFontFamily == null)
/* 1585 */           systemFontFamily = "Arial";
/*      */       }
/* 1587 */       else if (isMacOSX) {
/* 1588 */         systemFontFamily = "Lucida Grande";
/*      */       } else {
/* 1590 */         systemFontFamily = "Lucida Sans";
/*      */       }
/*      */     }
/* 1593 */     return systemFontFamily;
/*      */   }
/*      */ 
/*      */   static native short getSystemLCID();
/*      */ 
/*      */   static
/*      */   {
/*   55 */     AccessController.doPrivileged(new PrivilegedAction()
/*      */     {
/*      */       public Object run() {
/*   58 */         NativeLibLoader.loadLibrary("javafx-font");
/*   59 */         String str = System.getProperty("prism.debugfonts", "");
/*   60 */         T2KFontFactory.debugFonts = "true".equals(str);
/*   61 */         T2KFontFactory.access$002(System.getProperty("java.home", "") + File.separator + "lib" + File.separator + "fonts" + File.separator);
/*      */ 
/*   65 */         return null;
/*      */       }
/*      */     });
/*   68 */     isWindows = PlatformUtil.isWindows();
/*   69 */     isMacOSX = PlatformUtil.isMac();
/*   70 */     isLinux = PlatformUtil.isLinux();
/*   71 */     if (isMacOSX)
/*   72 */       macFontFinder = MacFontFinder.getInstance();
/*      */   }
/*      */ 
/*      */   private static class TTFilter
/*      */     implements FilenameFilter
/*      */   {
/*      */     static TTFilter ttFilter;
/*      */ 
/*      */     public boolean accept(File paramFile, String paramString)
/*      */     {
/* 1465 */       int i = paramString.length() - 4;
/* 1466 */       if (i <= 0) {
/* 1467 */         return false;
/*      */       }
/* 1469 */       return (paramString.startsWith(".ttf", i)) || (paramString.startsWith(".TTF", i)) || (paramString.startsWith(".ttc", i)) || (paramString.startsWith(".TTC", i)) || (paramString.startsWith(".otf", i)) || (paramString.startsWith(".OTF", i));
/*      */     }
/*      */ 
/*      */     static TTFilter getInstance()
/*      */     {
/* 1483 */       if (ttFilter == null) {
/* 1484 */         ttFilter = new TTFilter();
/*      */       }
/* 1486 */       return ttFilter;
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.t2k.T2KFontFactory
 * JD-Core Version:    0.6.2
 */