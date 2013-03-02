/*     */ package com.sun.t2k;
/*     */ 
/*     */ import com.sun.javafx.font.FontResource;
/*     */ import com.sun.javafx.runtime.NativeLibLoader;
/*     */ import java.io.File;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.net.URL;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ 
/*     */ public class MacFontFinder
/*     */ {
/*  31 */   private static MacFontFinder theInstance = new MacFontFinder();
/*     */ 
/*  34 */   private static HashMap<String, String> psNameToPathMap = null;
/*  35 */   private static String[] allAvailableFontFamilies = null;
/*  36 */   private static String[] allAvailableFonts = null;
/*     */ 
/* 499 */   private static String[] allNativeFamilyFonts = null;
/*     */ 
/*     */   public static MacFontFinder getInstance()
/*     */   {
/*  32 */     return theInstance;
/*     */   }
/*     */ 
/*     */   public String[] getFilePathsOfFontFamily(String paramString)
/*     */   {
/* 168 */     if ((paramString == null) || (paramString.isEmpty())) {
/* 169 */       return null;
/*     */     }
/*     */ 
/* 172 */     if (psNameToPathMap == null) {
/* 173 */       initPSFontNameToPathMap();
/*     */     }
/*     */ 
/* 176 */     String[] arrayOfString = availableMembersOfFontFamily(paramString);
/*     */ 
/* 178 */     if (arrayOfString == null) {
/* 179 */       return null;
/*     */     }
/*     */ 
/* 182 */     ArrayList localArrayList = new ArrayList(arrayOfString.length);
/* 183 */     String str = null;
/* 184 */     for (int i = 0; i < arrayOfString.length; i++) {
/* 185 */       str = (String)psNameToPathMap.get(arrayOfString[i]);
/* 186 */       if (str != null) {
/* 187 */         localArrayList.add(str);
/*     */       }
/*     */     }
/*     */ 
/* 191 */     if (localArrayList.isEmpty()) {
/* 192 */       return null;
/*     */     }
/* 194 */     return (String[])localArrayList.toArray(new String[localArrayList.size()]);
/*     */   }
/*     */ 
/*     */   private static boolean isValidFontFamily(String paramString)
/*     */   {
/* 200 */     String[] arrayOfString = null;
/* 201 */     if (allAvailableFontFamilies != null) {
/* 202 */       arrayOfString = allAvailableFontFamilies;
/* 203 */     } else if (allNativeFamilyFonts != null) {
/* 204 */       arrayOfString = allNativeFamilyFonts;
/*     */     } else {
/* 206 */       allNativeFamilyFonts = availableFontFamilies();
/* 207 */       Arrays.sort(allNativeFamilyFonts);
/* 208 */       arrayOfString = allNativeFamilyFonts;
/*     */     }
/* 210 */     if (paramString == null) {
/* 211 */       return false;
/*     */     }
/*     */ 
/* 215 */     int i = 0;
/* 216 */     int j = arrayOfString.length - 1;
/* 217 */     int k = (i + j) / 2;
/*     */ 
/* 220 */     while (i <= j) {
/* 221 */       int m = paramString.compareToIgnoreCase(arrayOfString[k]);
/*     */ 
/* 224 */       if (m == 0) {
/* 225 */         return true;
/*     */       }
/* 227 */       if (m < 0) {
/* 228 */         j = k - 1;
/* 229 */         k = (i + j) / 2;
/*     */       } else {
/* 231 */         i = k + 1;
/* 232 */         k = (i + j) / 2;
/*     */       }
/*     */     }
/* 235 */     return false;
/*     */   }
/*     */ 
/*     */   public String getFilePathOfFont(String paramString, float paramFloat)
/*     */   {
/* 240 */     if ((paramString == null) || (paramString.isEmpty())) {
/* 241 */       return null;
/*     */     }
/*     */ 
/* 244 */     if (psNameToPathMap == null) {
/* 245 */       initPSFontNameToPathMap();
/*     */     }
/*     */ 
/* 248 */     String str = getPSNameOfFont(paramString, paramFloat);
/*     */ 
/* 250 */     if ((str == null) || (str.isEmpty())) {
/* 251 */       return null;
/*     */     }
/* 253 */     return (String)psNameToPathMap.get(str);
/*     */   }
/*     */ 
/*     */   private void initPSFontNameToPathMap()
/*     */   {
/* 265 */     if (psNameToPathMap == null)
/* 266 */       psNameToPathMap = new HashMap();
/*     */     else {
/* 268 */       return;
/*     */     }
/*     */ 
/* 271 */     ArrayList localArrayList = new ArrayList();
/*     */ 
/* 273 */     String[] arrayOfString1 = availableFontURLs();
/*     */ 
/* 275 */     for (int i = 0; i < arrayOfString1.length; i++)
/*     */       try
/*     */       {
/* 278 */         localObject1 = new URL(arrayOfString1[i]);
/* 279 */         String str1 = ((URL)localObject1).getRef();
/*     */ 
/* 281 */         if (str1 != null)
/*     */         {
/* 286 */           localObject2 = str1.split("\\=");
/* 287 */           if ((localObject2 == null) || (localObject2.length == 2))
/*     */           {
/* 291 */             if (localObject2[0].contains("postscript-name")) {
/* 292 */               localObject3 = localObject2[1];
/* 293 */               localObject4 = ((URL)localObject1).toURI().getPath();
/* 294 */               localObject5 = ((String)localObject4).toLowerCase();
/*     */ 
/* 299 */               if ((((String)localObject5).endsWith(".otf")) && ((((String)localObject5).contains(" pro w")) || (((String)localObject5).contains(" pron w")) || (((String)localObject5).contains(" std w")) || (((String)localObject5).contains(" stdn w")) || (((String)localObject5).contains("hiragino"))))
/*     */               {
/* 306 */                 localArrayList.add(arrayOfString1[i]);
/*     */               }
/* 308 */               else psNameToPathMap.put(localObject3, localObject4); 
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       catch (URISyntaxException localURISyntaxException) {
/*     */       }
/*     */       catch (MalformedURLException localMalformedURLException) {  }
/*     */ 
/* 316 */     i = localArrayList.size();
/* 317 */     Object localObject1 = getFamilyNamesOfFontFiles((String[])localArrayList.toArray(new String[i]), i);
/*     */ 
/* 322 */     String[] arrayOfString2 = availableFontFamilies();
/*     */ 
/* 324 */     Object localObject2 = new ArrayList(200);
/*     */ 
/* 326 */     Object localObject3 = new MinStringHeap(arrayOfString2.length, true);
/* 327 */     Object localObject4 = new MinStringHeap(localObject1.length, true);
/*     */ 
/* 329 */     ((MinStringHeap)localObject4).insertAll(Arrays.asList((Object[])localObject1));
/* 330 */     for (String str3 : arrayOfString2) {
/* 331 */       ((MinStringHeap)localObject3).insert(str3);
/*     */     }
/*     */ 
/* 334 */     localObject2 = new ArrayList();
/*     */ 
/* 336 */     Object localObject5 = ((MinStringHeap)localObject4).getMin();
/* 337 */     while (!((MinStringHeap)localObject3).isEmpty()) {
/* 338 */       String str2 = ((MinStringHeap)localObject3).getMin();
/* 339 */       if (((String)localObject5).equalsIgnoreCase(str2)) {
/* 340 */         if (((MinStringHeap)localObject4).isEmpty())
/*     */         {
/* 342 */           while (!((MinStringHeap)localObject3).isEmpty())
/*     */           {
/* 344 */             ((ArrayList)localObject2).add(((MinStringHeap)localObject3).getMin());
/*     */           }
/*     */         }
/*     */ 
/* 348 */         localObject5 = ((MinStringHeap)localObject4).getMin();
/*     */       } else {
/* 350 */         ((ArrayList)localObject2).add(str2);
/*     */       }
/*     */     }
/* 353 */     allAvailableFontFamilies = (String[])((ArrayList)localObject2).toArray(new String[((ArrayList)localObject2).size()]);
/*     */   }
/*     */ 
/*     */   public String[] getAllAvailableFontFamilies()
/*     */   {
/* 358 */     if (allAvailableFontFamilies == null) {
/* 359 */       initPSFontNameToPathMap();
/*     */     }
/* 361 */     return allAvailableFontFamilies;
/*     */   }
/*     */ 
/*     */   public String[] getAllAvailableFontFiles()
/*     */   {
/* 366 */     if (psNameToPathMap == null) {
/* 367 */       initPSFontNameToPathMap();
/*     */     }
/*     */ 
/* 370 */     ArrayList localArrayList = new ArrayList(psNameToPathMap.size());
/*     */ 
/* 372 */     MinStringHeap localMinStringHeap = new MinStringHeap(psNameToPathMap.size(), true);
/*     */ 
/* 374 */     localMinStringHeap.insertAll(psNameToPathMap.values());
/*     */ 
/* 376 */     while (!localMinStringHeap.isEmpty()) {
/* 377 */       localArrayList.add(localMinStringHeap.getMin());
/*     */     }
/* 379 */     return (String[])localArrayList.toArray(new String[localArrayList.size()]);
/*     */   }
/*     */ 
/*     */   public String[] getFontNamesOfFontFamily(String paramString)
/*     */   {
/* 384 */     if ((paramString == null) || (paramString.isEmpty())) {
/* 385 */       return null;
/*     */     }
/*     */ 
/* 389 */     if (psNameToPathMap == null) {
/* 390 */       initPSFontNameToPathMap();
/*     */     }
/* 392 */     if (!isValidFontFamily(paramString)) {
/* 393 */       return null;
/*     */     }
/*     */ 
/* 396 */     String[] arrayOfString1 = availableMembersOfFontFamily(paramString);
/*     */ 
/* 398 */     if (arrayOfString1 == null) {
/* 399 */       return null;
/*     */     }
/*     */ 
/* 402 */     ArrayList localArrayList = new ArrayList(arrayOfString1.length);
/* 403 */     String str = null;
/* 404 */     for (int i = 0; i < arrayOfString1.length; i++) {
/* 405 */       str = (String)psNameToPathMap.get(arrayOfString1[i]);
/* 406 */       if (str != null) {
/* 407 */         localObject = new File(str);
/* 408 */         localArrayList.add(((File)localObject).toURI().toASCIIString());
/*     */       }
/*     */     }
/*     */ 
/* 412 */     if (localArrayList.isEmpty()) {
/* 413 */       return null;
/*     */     }
/* 415 */     i = localArrayList.size();
/* 416 */     Object localObject = (String[])localArrayList.toArray(new String[i]);
/* 417 */     String[] arrayOfString2 = getNamesOfFontFiles((String[])localObject, i);
/*     */ 
/* 419 */     return arrayOfString2;
/*     */   }
/*     */ 
/*     */   public static DFontDecoder checkDFont(String paramString1, String paramString2) {
/* 423 */     if (paramString2.endsWith(".dfont")) {
/* 424 */       DFontDecoder localDFontDecoder = new DFontDecoder();
/*     */       try {
/* 426 */         localDFontDecoder.openFile();
/* 427 */         localDFontDecoder.decode(paramString1);
/* 428 */         localDFontDecoder.closeFile();
/* 429 */         return localDFontDecoder;
/*     */       } catch (Exception localException) {
/* 431 */         localDFontDecoder.deleteFile();
/* 432 */         if (T2KFontFactory.debugFonts) {
/* 433 */           localException.printStackTrace();
/*     */         }
/*     */       }
/*     */     }
/* 437 */     return null;
/*     */   }
/*     */ 
/*     */   boolean populateFontFileNameMap(HashMap<String, String> paramHashMap1, HashMap<String, String> paramHashMap2, HashMap<String, ArrayList<String>> paramHashMap, Locale paramLocale)
/*     */   {
/* 444 */     boolean bool = false;
/* 445 */     if (paramLocale == null) {
/* 446 */       paramLocale = Locale.ENGLISH;
/*     */     }
/*     */ 
/* 449 */     if ((paramHashMap1 == null) || (paramHashMap2 == null) || (paramHashMap == null))
/*     */     {
/* 451 */       return false;
/*     */     }
/*     */ 
/* 454 */     String[] arrayOfString = populateFontFileNameMap(paramHashMap1, paramHashMap2, paramLocale);
/*     */ 
/* 457 */     Arrays.sort(arrayOfString);
/* 458 */     allAvailableFonts = arrayOfString;
/*     */ 
/* 461 */     Object localObject1 = "";
/* 462 */     ArrayList localArrayList1 = new ArrayList();
/* 463 */     for (Object localObject3 : arrayOfString) {
/* 464 */       String str1 = localObject3.toLowerCase(paramLocale);
/* 465 */       String str2 = (String)paramHashMap2.get(str1);
/* 466 */       if (str2.contentEquals((CharSequence)localObject1)) {
/* 467 */         localArrayList1.add(localObject3);
/*     */       } else {
/* 469 */         if (!localArrayList1.isEmpty()) {
/* 470 */           String str3 = ((String)localObject1).toLowerCase(paramLocale);
/* 471 */           if (paramHashMap.containsKey(str3)) {
/* 472 */             ArrayList localArrayList3 = (ArrayList)paramHashMap.get(str3);
/*     */ 
/* 474 */             localArrayList1.addAll(localArrayList3);
/*     */           }
/* 476 */           paramHashMap.put(str3, localArrayList1);
/* 477 */           localArrayList1 = new ArrayList();
/*     */         }
/* 479 */         localArrayList1.add(localObject3);
/*     */       }
/* 481 */       localObject1 = str2;
/*     */     }
/*     */ 
/* 484 */     if (!localArrayList1.isEmpty()) {
/* 485 */       bool = true;
/*     */ 
/* 487 */       ??? = ((String)localObject1).toLowerCase(paramLocale);
/* 488 */       if (paramHashMap.containsKey(???)) {
/* 489 */         ArrayList localArrayList2 = (ArrayList)paramHashMap.get(???);
/*     */ 
/* 492 */         localArrayList1.addAll(localArrayList2);
/*     */       }
/* 494 */       paramHashMap.put(???, localArrayList1);
/*     */     }
/* 496 */     return bool;
/*     */   }
/*     */ 
/*     */   static FontResource getMacFontResource(String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
/*     */   {
/* 504 */     String str1 = null;
/* 505 */     if ((paramString == null) || (paramString.equals(""))) {
/* 506 */       return null;
/*     */     }
/*     */ 
/* 509 */     String str2 = paramString.toLowerCase();
/*     */ 
/* 515 */     if (!isValidFontFamily(str2)) {
/* 516 */       return null;
/*     */     }
/*     */ 
/* 522 */     String[] arrayOfString = getFontResource(paramString, paramBoolean1, paramBoolean2);
/* 523 */     if (arrayOfString == null) {
/* 524 */       return null;
/*     */     }
/* 526 */     int i = arrayOfString.length / 4;
/*     */ 
/* 528 */     int k = (paramBoolean1 ? 2 : 0) + (paramBoolean2 ? 1 : 0);
/* 529 */     String str3 = null;
/* 530 */     Object localObject1 = null; Object localObject2 = null;
/* 531 */     ArrayList localArrayList = new ArrayList(4);
/*     */ 
/* 533 */     for (int m = 0; m < i; m++) {
/* 534 */       str3 = arrayOfString[(4 * m + 1)];
/* 535 */       str1 = arrayOfString[(4 * m + 2)];
/*     */ 
/* 537 */       String str4 = str3.toLowerCase();
/*     */ 
/* 540 */       localObject1 = T2KFontFactory.lookupResource(str4, paramBoolean3);
/* 541 */       if (localObject1 != null) {
/* 542 */         return localObject1;
/*     */       }
/* 544 */       DFontDecoder localDFontDecoder = checkDFont(str3, str1);
/* 545 */       if (localDFontDecoder != null) {
/* 546 */         str1 = localDFontDecoder.getFile().getPath();
/*     */       }
/* 548 */       localObject1 = T2KFontFile.createFontResource(str3, str1);
/* 549 */       if (localDFontDecoder != null) {
/* 550 */         if (localObject1 != null) {
/* 551 */           T2KFontFactory localT2KFontFactory = T2KFontFactory.getFontFactory();
/* 552 */           localT2KFontFactory.addDecodedFont((T2KFontFile)localObject1);
/*     */         } else {
/* 554 */           localDFontDecoder.deleteFile();
/*     */         }
/*     */       }
/* 557 */       if (localObject1 != null) {
/* 558 */         localArrayList.add(localObject1);
/* 559 */         T2KFontFactory.storeInMap(str4, (FontResource)localObject1);
/*     */ 
/* 561 */         int j = Integer.parseInt(arrayOfString[(4 * m + 3)]) & 0x3;
/* 562 */         boolean bool1 = (j & 0x1) != 0;
/* 563 */         boolean bool2 = (j & 0x2) != 0;
/* 564 */         String str5 = arrayOfString[(4 * m)].toLowerCase() + T2KFontFactory.dotStyleStr(bool2, bool1);
/*     */ 
/* 567 */         T2KFontFactory.storeInMap(str5, (FontResource)localObject1);
/*     */ 
/* 569 */         if ((localObject1 != null) && 
/* 570 */           (paramBoolean3)) {
/* 571 */           localObject1 = new T2KCompositeFontResource((FontResource)localObject1, str5);
/*     */         }
/*     */ 
/* 576 */         if ((k == j) && (localObject2 == null)) {
/* 577 */           localObject2 = localObject1;
/*     */         }
/* 579 */         localObject1 = null;
/*     */       }
/*     */     }
/* 582 */     if (localObject2 == null)
/*     */     {
/* 584 */       if (!localArrayList.isEmpty()) {
/* 585 */         return (FontResource)localArrayList.get(0);
/*     */       }
/*     */ 
/* 588 */       return null;
/*     */     }
/*     */ 
/* 592 */     return localObject2;
/*     */   }
/*     */ 
/*     */   private static native String[] availableMembersOfFontFamily(String paramString);
/*     */ 
/*     */   private static native String[] availableFontURLs();
/*     */ 
/*     */   private static native String[] availableFontFamilies();
/*     */ 
/*     */   private static native String[] getNamesOfFontFiles(String[] paramArrayOfString, int paramInt);
/*     */ 
/*     */   private static native String[] getFamilyNamesOfFontFiles(String[] paramArrayOfString, int paramInt);
/*     */ 
/*     */   private static native String[] populateFontFileNameMap(HashMap<String, String> paramHashMap1, HashMap<String, String> paramHashMap2, Locale paramLocale);
/*     */ 
/*     */   private static native String[] getFontResource(String paramString, boolean paramBoolean1, boolean paramBoolean2);
/*     */ 
/*     */   private static native String getPSNameOfFont(String paramString, float paramFloat);
/*     */ 
/*     */   static
/*     */   {
/*  39 */     AccessController.doPrivileged(new PrivilegedAction()
/*     */     {
/*     */       public Void run() {
/*  42 */         NativeLibLoader.loadLibrary("javafx-font");
/*  43 */         return null;
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   private class MinStringHeap
/*     */   {
/*     */     private int max;
/*     */     private int index;
/*  57 */     private final int STEP_SIZE = 10;
/*     */     private String[] sHeap;
/*  60 */     private boolean unique = false;
/*     */ 
/*     */     public MinStringHeap(int arg2)
/*     */     {
/*     */       int i;
/*  63 */       this.max = (i > 1 ? i : 10);
/*  64 */       this.sHeap = new String[this.max + 1];
/*  65 */       this.index = 1;
/*     */     }
/*     */ 
/*     */     public MinStringHeap(int paramBoolean, boolean arg3) {
/*  69 */       this(paramBoolean);
/*     */       boolean bool;
/*  70 */       this.unique = bool;
/*     */     }
/*     */ 
/*     */     public String getMin() {
/*  74 */       if (isEmpty()) {
/*  75 */         return null;
/*     */       }
/*  77 */       String str = this.sHeap[1];
/*  78 */       this.sHeap[1] = this.sHeap[(--this.index)];
/*  79 */       fixDown(1);
/*  80 */       if ((this.unique) && 
/*  81 */         (str.equals(this.sHeap[1]))) {
/*  82 */         getMin();
/*     */       }
/*     */ 
/*  85 */       return str;
/*     */     }
/*     */ 
/*     */     public void insert(String paramString) {
/*  89 */       if (this.index > this.max) {
/*  90 */         String[] arrayOfString = new String[this.max + 10 + 1];
/*  91 */         System.arraycopy(this.sHeap, 1, arrayOfString, 1, this.index - 1);
/*     */ 
/*  93 */         this.max += 10;
/*  94 */         this.sHeap = arrayOfString;
/*     */       }
/*     */ 
/*  97 */       this.sHeap[this.index] = paramString;
/*  98 */       fixUp(this.index++);
/*     */     }
/*     */ 
/*     */     public void insertAll(Collection<String> paramCollection) {
/* 102 */       if (paramCollection.isEmpty()) {
/* 103 */         return;
/*     */       }
/* 105 */       if (paramCollection.size() + this.index > this.max) {
/* 106 */         localObject = new String[paramCollection.size() + this.index];
/* 107 */         System.arraycopy(this.sHeap, 1, localObject, 1, this.index - 1);
/*     */ 
/* 109 */         this.max = (paramCollection.size() + this.index - 1);
/* 110 */         this.sHeap = ((String[])localObject);
/*     */       }
/*     */ 
/* 113 */       Object localObject = paramCollection.iterator();
/* 114 */       while (((Iterator)localObject).hasNext())
/* 115 */         insert((String)((Iterator)localObject).next());
/*     */     }
/*     */ 
/*     */     private void fixDown(int paramInt)
/*     */     {
/* 120 */       int i = paramInt;
/*     */ 
/* 122 */       int j = i * 2;
/* 123 */       if (j >= this.index)
/* 124 */         return;
/* 125 */       if (j + 1 == this.index) {
/* 126 */         if (this.sHeap[i].compareTo(this.sHeap[j]) > 0) {
/* 127 */           swap(i, j);
/*     */         }
/*     */ 
/*     */       }
/* 132 */       else if ((this.sHeap[i].compareTo(this.sHeap[j]) > 0) || (this.sHeap[i].compareTo(this.sHeap[(j + 1)]) > 0))
/*     */       {
/* 134 */         j = minIndex(j, j + 1);
/* 135 */         swap(i, j);
/* 136 */         fixDown(j);
/*     */       }
/*     */     }
/*     */ 
/*     */     private int minIndex(int paramInt1, int paramInt2)
/*     */     {
/* 142 */       return this.sHeap[paramInt1].compareTo(this.sHeap[paramInt2]) < 0 ? paramInt1 : paramInt2;
/*     */     }
/*     */ 
/*     */     private void fixUp(int paramInt) {
/* 146 */       int i = paramInt;
/* 147 */       if (i <= 1) {
/* 148 */         return;
/*     */       }
/* 150 */       if (this.sHeap[i].compareTo(this.sHeap[(i / 2)]) < 0) {
/* 151 */         swap(i, i / 2);
/* 152 */         fixUp(i / 2);
/*     */       }
/*     */     }
/*     */ 
/*     */     private void swap(int paramInt1, int paramInt2) {
/* 157 */       String str = this.sHeap[paramInt1];
/* 158 */       this.sHeap[paramInt1] = this.sHeap[paramInt2];
/* 159 */       this.sHeap[paramInt2] = str;
/*     */     }
/*     */ 
/*     */     public boolean isEmpty() {
/* 163 */       return this.index == 1;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.t2k.MacFontFinder
 * JD-Core Version:    0.6.2
 */