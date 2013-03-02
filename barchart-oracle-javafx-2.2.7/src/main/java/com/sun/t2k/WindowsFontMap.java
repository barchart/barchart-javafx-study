/*     */ package com.sun.t2k;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ 
/*     */ class WindowsFontMap
/*     */ {
/*     */   static HashMap<String, FamilyDescription> platformFontMap;
/*     */ 
/*     */   static HashMap<String, FamilyDescription> populateHardcodedFileNameMap()
/*     */   {
/*  29 */     HashMap localHashMap = new HashMap();
/*     */ 
/*  33 */     FamilyDescription localFamilyDescription = new FamilyDescription();
/*  34 */     localFamilyDescription.familyName = "Segoe UI";
/*  35 */     localFamilyDescription.plainFullName = "Segoe UI";
/*  36 */     localFamilyDescription.plainFileName = "segoeui.ttf";
/*  37 */     localFamilyDescription.boldFullName = "Segoe UI Bold";
/*  38 */     localFamilyDescription.boldFileName = "segoeuib.ttf";
/*  39 */     localFamilyDescription.italicFullName = "Segoe UI Italic";
/*  40 */     localFamilyDescription.italicFileName = "segoeuii.ttf";
/*  41 */     localFamilyDescription.boldItalicFullName = "Segoe UI Bold Italic";
/*  42 */     localFamilyDescription.boldItalicFileName = "segoeuiz.ttf";
/*  43 */     localHashMap.put("segoe", localFamilyDescription);
/*     */ 
/*  45 */     localFamilyDescription = new FamilyDescription();
/*  46 */     localFamilyDescription.familyName = "Tahoma";
/*  47 */     localFamilyDescription.plainFullName = "Tahoma";
/*  48 */     localFamilyDescription.plainFileName = "tahoma.ttf";
/*  49 */     localFamilyDescription.boldFullName = "Tahoma Bold";
/*  50 */     localFamilyDescription.boldFileName = "tahomabd.ttf";
/*  51 */     localHashMap.put("tahoma", localFamilyDescription);
/*     */ 
/*  53 */     localFamilyDescription = new FamilyDescription();
/*  54 */     localFamilyDescription.familyName = "Verdana";
/*  55 */     localFamilyDescription.plainFullName = "Verdana";
/*  56 */     localFamilyDescription.plainFileName = "verdana.TTF";
/*  57 */     localFamilyDescription.boldFullName = "Verdana Bold";
/*  58 */     localFamilyDescription.boldFileName = "verdanab.TTF";
/*  59 */     localFamilyDescription.italicFullName = "Verdana Italic";
/*  60 */     localFamilyDescription.italicFileName = "verdanai.TTF";
/*  61 */     localFamilyDescription.boldItalicFullName = "Verdana Bold Italic";
/*  62 */     localFamilyDescription.boldItalicFileName = "verdanaz.TTF";
/*  63 */     localHashMap.put("verdana", localFamilyDescription);
/*     */ 
/*  65 */     localFamilyDescription = new FamilyDescription();
/*  66 */     localFamilyDescription.familyName = "Arial";
/*  67 */     localFamilyDescription.plainFullName = "Arial";
/*  68 */     localFamilyDescription.plainFileName = "ARIAL.TTF";
/*  69 */     localFamilyDescription.boldFullName = "Arial Bold";
/*  70 */     localFamilyDescription.boldFileName = "ARIALBD.TTF";
/*  71 */     localFamilyDescription.italicFullName = "Arial Italic";
/*  72 */     localFamilyDescription.italicFileName = "ARIALI.TTF";
/*  73 */     localFamilyDescription.boldItalicFullName = "Arial Bold Italic";
/*  74 */     localFamilyDescription.boldItalicFileName = "ARIALBI.TTF";
/*  75 */     localHashMap.put("arial", localFamilyDescription);
/*     */ 
/*  77 */     localFamilyDescription = new FamilyDescription();
/*  78 */     localFamilyDescription.familyName = "Times New Roman";
/*  79 */     localFamilyDescription.plainFullName = "Times New Roman";
/*  80 */     localFamilyDescription.plainFileName = "times.ttf";
/*  81 */     localFamilyDescription.boldFullName = "Times New Roman Bold";
/*  82 */     localFamilyDescription.boldFileName = "timesbd.ttf";
/*  83 */     localFamilyDescription.italicFullName = "Times New Roman Italic";
/*  84 */     localFamilyDescription.italicFileName = "timesi.ttf";
/*  85 */     localFamilyDescription.boldItalicFullName = "Times New Roman Bold Italic";
/*  86 */     localFamilyDescription.boldItalicFileName = "timesbi.ttf";
/*  87 */     localHashMap.put("times", localFamilyDescription);
/*     */ 
/*  90 */     localFamilyDescription = new FamilyDescription();
/*  91 */     localFamilyDescription.familyName = "Courier New";
/*  92 */     localFamilyDescription.plainFullName = "Courier New";
/*  93 */     localFamilyDescription.plainFileName = "cour.ttf";
/*  94 */     localFamilyDescription.boldFullName = "Courier New Bold";
/*  95 */     localFamilyDescription.boldFileName = "courbd.ttf";
/*  96 */     localFamilyDescription.italicFullName = "Courier New Italic";
/*  97 */     localFamilyDescription.italicFileName = "couri.ttf";
/*  98 */     localFamilyDescription.boldItalicFullName = "Courier New Bold Italic";
/*  99 */     localFamilyDescription.boldItalicFileName = "courbi.ttf";
/* 100 */     localHashMap.put("courier", localFamilyDescription);
/*     */ 
/* 102 */     return localHashMap;
/*     */   }
/*     */ 
/*     */   static String getPathName(String paramString) {
/* 106 */     return T2KFontFactory.getPathNameWindows(paramString);
/*     */   }
/*     */ 
/*     */   static String findFontFile(String paramString, int paramInt)
/*     */   {
/* 116 */     if (platformFontMap == null) {
/* 117 */       platformFontMap = populateHardcodedFileNameMap();
/*     */     }
/*     */ 
/* 120 */     if ((platformFontMap == null) || (platformFontMap.size() == 0)) {
/* 121 */       return null;
/*     */     }
/*     */ 
/* 124 */     int i = paramString.indexOf(' ');
/* 125 */     String str1 = paramString;
/* 126 */     if (i > 0) {
/* 127 */       str1 = paramString.substring(0, i);
/*     */     }
/*     */ 
/* 130 */     FamilyDescription localFamilyDescription = (FamilyDescription)platformFontMap.get(str1);
/* 131 */     if (localFamilyDescription == null) {
/* 132 */       return null;
/*     */     }
/*     */ 
/* 144 */     String str2 = null;
/*     */ 
/* 146 */     if (paramInt < 0) {
/* 147 */       if (paramString.equalsIgnoreCase(localFamilyDescription.plainFullName))
/* 148 */         str2 = localFamilyDescription.plainFileName;
/* 149 */       else if (paramString.equalsIgnoreCase(localFamilyDescription.boldFullName))
/* 150 */         str2 = localFamilyDescription.boldFileName;
/* 151 */       else if (paramString.equalsIgnoreCase(localFamilyDescription.italicFullName))
/* 152 */         str2 = localFamilyDescription.italicFileName;
/* 153 */       else if (paramString.equalsIgnoreCase(localFamilyDescription.boldItalicFullName)) {
/* 154 */         str2 = localFamilyDescription.boldItalicFileName;
/*     */       }
/* 156 */       if (str2 != null) {
/* 157 */         return getPathName(str2);
/*     */       }
/* 159 */       return null;
/*     */     }
/* 161 */     if (!paramString.equalsIgnoreCase(localFamilyDescription.familyName)) {
/* 162 */       return null;
/*     */     }
/*     */ 
/* 167 */     switch (paramInt) {
/*     */     case 0:
/* 169 */       str2 = localFamilyDescription.plainFileName;
/* 170 */       break;
/*     */     case 1:
/* 172 */       str2 = localFamilyDescription.boldFileName;
/* 173 */       if (str2 == null)
/* 174 */         str2 = localFamilyDescription.plainFileName; break;
/*     */     case 2:
/* 178 */       str2 = localFamilyDescription.italicFileName;
/* 179 */       if (str2 == null)
/* 180 */         str2 = localFamilyDescription.plainFileName; break;
/*     */     case 3:
/* 184 */       str2 = localFamilyDescription.boldItalicFileName;
/* 185 */       if (str2 == null) {
/* 186 */         str2 = localFamilyDescription.italicFileName;
/*     */       }
/* 188 */       if (str2 == null) {
/* 189 */         str2 = localFamilyDescription.boldFileName;
/*     */       }
/* 191 */       if (str2 == null) {
/* 192 */         str2 = localFamilyDescription.plainFileName;
/*     */       }
/*     */       break;
/*     */     }
/*     */ 
/* 197 */     if (str2 != null) {
/* 198 */       return getPathName(str2);
/*     */     }
/* 200 */     return null;
/*     */   }
/*     */ 
/*     */   static class FamilyDescription
/*     */   {
/*     */     public String familyName;
/*     */     public String plainFullName;
/*     */     public String boldFullName;
/*     */     public String italicFullName;
/*     */     public String boldItalicFullName;
/*     */     public String plainFileName;
/*     */     public String boldFileName;
/*     */     public String italicFileName;
/*     */     public String boldItalicFileName;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.t2k.WindowsFontMap
 * JD-Core Version:    0.6.2
 */