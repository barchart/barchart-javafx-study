/*     */ package com.sun.t2k;
/*     */ 
/*     */ import com.sun.javafx.font.FontConfigManager;
/*     */ import com.sun.javafx.font.FontConfigManager.FcCompFont;
/*     */ import com.sun.javafx.font.FontConfigManager.FontConfigFont;
/*     */ import com.sun.javafx.font.FontResource;
/*     */ import com.sun.javafx.font.FontStrike;
/*     */ import com.sun.javafx.font.FontStrikeDesc;
/*     */ import com.sun.javafx.font.PGFont;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ 
/*     */ public class LogicalFont
/*     */   implements CompositeFontResource
/*     */ {
/*     */   public static final String SYSTEM = "System";
/*     */   public static final String SERIF = "Serif";
/*     */   public static final String SANS_SERIF = "SansSerif";
/*     */   public static final String MONOSPACED = "Monospaced";
/*     */   public static final String STYLE_REGULAR = "Regular";
/*     */   public static final String STYLE_BOLD = "Bold";
/*     */   public static final String STYLE_ITALIC = "Italic";
/*     */   public static final String STYLE_BOLD_ITALIC = "Bold Italic";
/*  38 */   static final HashMap<String, String> canonicalFamilyMap = new HashMap();
/*     */ 
/*  75 */   static LogicalFont[] logicalFonts = new LogicalFont[16];
/*     */   boolean isBold;
/*     */   boolean isItalic;
/*     */   private String fullName;
/*     */   private String familyName;
/*     */   private String styleName;
/*     */   private String physicalFamily;
/*     */   private String physicalFullName;
/*     */   private String physicalFileName;
/*     */   private FontResource slot0FontResource;
/*     */   private ArrayList<String> linkedFontFiles;
/*     */   private ArrayList<String> linkedFontNames;
/*     */   private FontResource[] fallbacks;
/*     */   CompositeGlyphMapper mapper;
/* 327 */   Map<FontStrikeDesc, WeakReference<FontStrike>> strikeMap = new ConcurrentHashMap();
/*     */   private static final int SANS_SERIF_INDEX = 0;
/*     */   private static final int SERIF_INDEX = 1;
/*     */   private static final int MONOSPACED_INDEX = 2;
/*     */   private static final int SYSTEM_INDEX = 3;
/* 378 */   static String[][] logFamilies = (String[][])null;
/*     */   private int hash;
/*     */ 
/*     */   private static String getCanonicalFamilyName(String paramString)
/*     */   {
/*  55 */     if (paramString == null) {
/*  56 */       return "SansSerif";
/*     */     }
/*  58 */     String str = paramString.toLowerCase();
/*  59 */     return (String)canonicalFamilyMap.get(str);
/*     */   }
/*     */ 
/*     */   private static String mapLogicalToFontFamilyName(String paramString)
/*     */   {
/*  64 */     if (paramString.equals("System"))
/*  65 */       return T2KFontFactory.getSystemFont();
/*  66 */     if (paramString.equals("SansSerif"))
/*  67 */       return "Arial";
/*  68 */     if (paramString.equals("Serif")) {
/*  69 */       return "Times New Roman";
/*     */     }
/*  71 */     return "Courier New";
/*     */   }
/*     */ 
/*     */   static PGFont getLogicalFont(String paramString, boolean paramBoolean1, boolean paramBoolean2, float paramFloat)
/*     */   {
/*  80 */     String str = getCanonicalFamilyName(paramString);
/*  81 */     if (str == null) {
/*  82 */       return null;
/*     */     }
/*     */ 
/*  85 */     int i = 0;
/*  86 */     if (str.equals("SansSerif"))
/*  87 */       i = 0;
/*  88 */     else if (str.equals("Serif"))
/*  89 */       i = 4;
/*  90 */     else if (str.equals("Monospaced"))
/*  91 */       i = 8;
/*     */     else {
/*  93 */       i = 12;
/*     */     }
/*  95 */     if (paramBoolean1) {
/*  96 */       i++;
/*     */     }
/*  98 */     if (paramBoolean2) {
/*  99 */       i += 2;
/*     */     }
/*     */ 
/* 102 */     LogicalFont localLogicalFont = logicalFonts[i];
/* 103 */     if (localLogicalFont == null) {
/* 104 */       localLogicalFont = new LogicalFont(str, paramBoolean1, paramBoolean2);
/* 105 */       logicalFonts[i] = localLogicalFont;
/*     */     }
/* 107 */     return new T2KFont(localLogicalFont, localLogicalFont.getFullName(), paramFloat);
/*     */   }
/*     */ 
/*     */   static PGFont getLogicalFont(String paramString, float paramFloat)
/*     */   {
/* 121 */     int i = paramString.indexOf(32);
/* 122 */     if ((i == -1) || (i == paramString.length() - 1)) {
/* 123 */       return null;
/*     */     }
/* 125 */     String str1 = paramString.substring(0, i);
/* 126 */     String str2 = getCanonicalFamilyName(str1);
/* 127 */     if (str2 == null) {
/* 128 */       return null;
/*     */     }
/* 130 */     String str3 = paramString.substring(i + 1).toLowerCase();
/* 131 */     boolean bool1 = false; boolean bool2 = false;
/* 132 */     if (!str3.equals("regular"))
/*     */     {
/* 134 */       if (str3.equals("bold")) {
/* 135 */         bool1 = true;
/* 136 */       } else if (str3.equals("italic")) {
/* 137 */         bool2 = true;
/* 138 */       } else if (str3.equals("bold italic")) {
/* 139 */         bool1 = true;
/* 140 */         bool2 = true;
/*     */       } else {
/* 142 */         return null;
/*     */       }
/*     */     }
/* 144 */     return getLogicalFont(str2, bool1, bool2, paramFloat);
/*     */   }
/*     */ 
/*     */   private LogicalFont(String paramString, boolean paramBoolean1, boolean paramBoolean2)
/*     */   {
/* 155 */     this.familyName = paramString;
/* 156 */     this.isBold = paramBoolean1;
/* 157 */     this.isItalic = paramBoolean2;
/*     */ 
/* 159 */     if ((!paramBoolean1) && (!paramBoolean2))
/* 160 */       this.styleName = "Regular";
/* 161 */     else if ((paramBoolean1) && (!paramBoolean2))
/* 162 */       this.styleName = "Bold";
/* 163 */     else if ((!paramBoolean1) && (paramBoolean2))
/* 164 */       this.styleName = "Italic";
/*     */     else {
/* 166 */       this.styleName = "Bold Italic";
/*     */     }
/* 168 */     this.fullName = (this.familyName + " " + this.styleName);
/* 169 */     if (T2KFontFactory.isLinux) {
/* 170 */       FontConfigManager.FcCompFont localFcCompFont = FontConfigManager.getFontConfigFont(paramString, paramBoolean1, paramBoolean2);
/*     */ 
/* 172 */       this.physicalFullName = localFcCompFont.firstFont.fullName;
/* 173 */       this.physicalFileName = localFcCompFont.firstFont.fontFile;
/*     */     } else {
/* 175 */       this.physicalFamily = mapLogicalToFontFamilyName(this.familyName);
/*     */     }
/*     */   }
/*     */ 
/*     */   private FontResource getSlot0Resource()
/*     */   {
/* 182 */     if (this.slot0FontResource == null) {
/* 183 */       if (this.physicalFamily != null) {
/* 184 */         this.slot0FontResource = T2KFontFactory.getFontFactory().getFontResource(this.physicalFamily, this.isBold, this.isItalic, false);
/*     */       }
/*     */       else {
/* 187 */         this.slot0FontResource = T2KFontFactory.getFontFactory().getFontResource(this.physicalFullName, this.physicalFileName, false);
/*     */       }
/*     */ 
/* 191 */       if (this.slot0FontResource == null) {
/* 192 */         this.slot0FontResource = T2KFontFactory.getDefaultFontResource(false);
/*     */       }
/*     */     }
/*     */ 
/* 196 */     return this.slot0FontResource;
/*     */   }
/*     */ 
/*     */   private void getLinkedFonts()
/*     */   {
/* 204 */     if (this.fallbacks == null)
/*     */     {
/* 206 */       if (T2KFontFactory.isLinux) {
/* 207 */         FontConfigManager.FcCompFont localFcCompFont = FontConfigManager.getFontConfigFont(this.familyName, this.isBold, this.isItalic);
/*     */ 
/* 210 */         this.linkedFontFiles = FontConfigManager.getFileNames(localFcCompFont, true);
/* 211 */         this.linkedFontNames = FontConfigManager.getFontNames(localFcCompFont, true);
/*     */       } else {
/* 213 */         ArrayList[] arrayOfArrayList = T2KFontFactory.getLinkedFonts("Tahoma", true);
/* 214 */         this.linkedFontFiles = arrayOfArrayList[0];
/* 215 */         this.linkedFontNames = arrayOfArrayList[1];
/*     */       }
/* 217 */       this.fallbacks = new FontResource[this.linkedFontFiles.size()];
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getNumSlots() {
/* 222 */     getLinkedFonts();
/* 223 */     return this.linkedFontFiles.size() + 1;
/*     */   }
/*     */ 
/*     */   public FontResource getSlotResource(int paramInt) {
/* 227 */     if (paramInt == 0) {
/* 228 */       return getSlot0Resource();
/*     */     }
/* 230 */     getLinkedFonts();
/* 231 */     paramInt -= 1;
/* 232 */     if (this.fallbacks[paramInt] == null) {
/* 233 */       String str1 = (String)this.linkedFontFiles.get(paramInt);
/* 234 */       String str2 = (String)this.linkedFontNames.get(paramInt);
/* 235 */       this.fallbacks[paramInt] = T2KFontFactory.getFontFactory().getFontResource(str2, str1, false);
/*     */ 
/* 238 */       if (this.fallbacks[paramInt] == null) {
/* 239 */         this.fallbacks[paramInt] = getSlot0Resource();
/*     */       }
/*     */     }
/* 242 */     return this.fallbacks[paramInt];
/*     */   }
/*     */ 
/*     */   public String getFullName()
/*     */   {
/* 247 */     return this.fullName;
/*     */   }
/*     */ 
/*     */   public String getPSName() {
/* 251 */     return this.fullName;
/*     */   }
/*     */ 
/*     */   public String getFamilyName() {
/* 255 */     return this.familyName;
/*     */   }
/*     */ 
/*     */   public String getStyleName() {
/* 259 */     return this.styleName;
/*     */   }
/*     */ 
/*     */   public String getLocaleFullName()
/*     */   {
/* 264 */     return this.fullName;
/*     */   }
/*     */ 
/*     */   public String getLocaleFamilyName() {
/* 268 */     return this.familyName;
/*     */   }
/*     */ 
/*     */   public String getLocaleStyleName() {
/* 272 */     return this.styleName;
/*     */   }
/*     */ 
/*     */   public boolean isBold() {
/* 276 */     return getSlotResource(0).isBold();
/*     */   }
/*     */ 
/*     */   public boolean isItalic() {
/* 280 */     return getSlotResource(0).isItalic();
/*     */   }
/*     */ 
/*     */   public int getNumGlyphs() {
/* 284 */     return getSlotResource(0).getNumGlyphs();
/*     */   }
/*     */ 
/*     */   public String getFileName() {
/* 288 */     return getSlotResource(0).getFileName();
/*     */   }
/*     */ 
/*     */   public Object getPeer() {
/* 292 */     return null;
/*     */   }
/*     */ 
/*     */   public void setPeer(Object paramObject) {
/* 296 */     throw new UnsupportedOperationException("Not supported");
/*     */   }
/*     */ 
/*     */   public boolean supportsGlyphImages() {
/* 300 */     return true;
/*     */   }
/*     */ 
/*     */   public float[] getGlyphBoundingBox(int paramInt, float paramFloat, float[] paramArrayOfFloat)
/*     */   {
/* 305 */     int i = paramInt >>> 24;
/* 306 */     int j = paramInt & 0xFFFFFF;
/* 307 */     FontResource localFontResource = getSlotResource(i);
/* 308 */     return localFontResource.getGlyphBoundingBox(j, paramFloat, paramArrayOfFloat);
/*     */   }
/*     */ 
/*     */   public float getAdvance(int paramInt, float paramFloat) {
/* 312 */     int i = paramInt >>> 24;
/* 313 */     int j = paramInt & 0xFFFFFF;
/* 314 */     FontResource localFontResource = getSlotResource(i);
/* 315 */     return localFontResource.getAdvance(j, paramFloat);
/*     */   }
/*     */ 
/*     */   public CharToGlyphMapper getGlyphMapper()
/*     */   {
/* 321 */     if (this.mapper == null) {
/* 322 */       this.mapper = new CompositeGlyphMapper(this);
/*     */     }
/* 324 */     return this.mapper;
/*     */   }
/*     */ 
/*     */   public Map<FontStrikeDesc, WeakReference<FontStrike>> getStrikeMap()
/*     */   {
/* 331 */     return this.strikeMap;
/*     */   }
/*     */ 
/*     */   public int getDefaultAAMode() {
/* 335 */     return getSlot0Resource().getDefaultAAMode();
/*     */   }
/*     */ 
/*     */   public FontStrike getStrike(PGFont paramPGFont, BaseTransform paramBaseTransform) {
/* 339 */     return getStrike(paramPGFont.getSize(), paramBaseTransform, getDefaultAAMode());
/*     */   }
/*     */ 
/*     */   public FontStrike getStrike(float paramFloat, BaseTransform paramBaseTransform) {
/* 343 */     return getStrike(paramFloat, paramBaseTransform, getDefaultAAMode());
/*     */   }
/*     */ 
/*     */   public FontStrike getStrike(PGFont paramPGFont, BaseTransform paramBaseTransform, int paramInt)
/*     */   {
/* 348 */     return getStrike(paramPGFont.getSize(), paramBaseTransform, paramInt);
/*     */   }
/*     */ 
/*     */   public FontStrike getStrike(float paramFloat, BaseTransform paramBaseTransform, int paramInt)
/*     */   {
/* 353 */     T2KFontStrikeDesc localT2KFontStrikeDesc = new T2KFontStrikeDesc(paramFloat, paramBaseTransform, paramInt);
/* 354 */     WeakReference localWeakReference = (WeakReference)this.strikeMap.get(localT2KFontStrikeDesc);
/* 355 */     CompositeStrike localCompositeStrike = null;
/*     */ 
/* 357 */     if (localWeakReference != null) {
/* 358 */       localCompositeStrike = (CompositeStrike)localWeakReference.get();
/*     */     }
/* 360 */     if (localCompositeStrike == null) {
/* 361 */       localCompositeStrike = new CompositeStrike(this, paramFloat, paramBaseTransform, localT2KFontStrikeDesc);
/* 362 */       if (localCompositeStrike.disposer != null)
/* 363 */         localWeakReference = Disposer.addRecord(localCompositeStrike, localCompositeStrike.disposer);
/*     */       else {
/* 365 */         localWeakReference = new WeakReference(localCompositeStrike);
/*     */       }
/* 367 */       this.strikeMap.put(localT2KFontStrikeDesc, localWeakReference);
/*     */     }
/* 369 */     return localCompositeStrike;
/*     */   }
/*     */ 
/*     */   private static void buildFamily(String[] paramArrayOfString, String paramString)
/*     */   {
/* 381 */     paramArrayOfString[0] = (paramString + " " + "Regular");
/* 382 */     paramArrayOfString[1] = (paramString + " " + "Bold");
/* 383 */     paramArrayOfString[2] = (paramString + " " + "Italic");
/* 384 */     paramArrayOfString[3] = (paramString + " " + "Bold Italic");
/*     */   }
/*     */ 
/*     */   private static void buildFamilies() {
/* 388 */     if (logFamilies == null) {
/* 389 */       String[][] arrayOfString = new String[4][4];
/* 390 */       buildFamily(arrayOfString[0], "SansSerif");
/* 391 */       buildFamily(arrayOfString[1], "Serif");
/* 392 */       buildFamily(arrayOfString[2], "Monospaced");
/* 393 */       buildFamily(arrayOfString[3], "System");
/* 394 */       logFamilies = arrayOfString;
/*     */     }
/*     */   }
/*     */ 
/*     */   static void addFamilies(ArrayList<String> paramArrayList) {
/* 399 */     paramArrayList.add("SansSerif");
/* 400 */     paramArrayList.add("Serif");
/* 401 */     paramArrayList.add("Monospaced");
/* 402 */     paramArrayList.add("System");
/*     */   }
/*     */ 
/*     */   static void addFullNames(ArrayList<String> paramArrayList) {
/* 406 */     buildFamilies();
/* 407 */     for (int i = 0; i < logFamilies.length; i++)
/* 408 */       for (int j = 0; j < logFamilies[i].length; j++)
/* 409 */         paramArrayList.add(logFamilies[i][j]);
/*     */   }
/*     */ 
/*     */   static String[] getFontsInFamily(String paramString)
/*     */   {
/* 415 */     String str = getCanonicalFamilyName(paramString);
/* 416 */     if (str == null) {
/* 417 */       return null;
/*     */     }
/* 419 */     buildFamilies();
/* 420 */     if (str.equals("SansSerif"))
/* 421 */       return logFamilies[0];
/* 422 */     if (str.equals("Serif"))
/* 423 */       return logFamilies[1];
/* 424 */     if (str.equals("Monospaced")) {
/* 425 */       return logFamilies[2];
/*     */     }
/* 427 */     return logFamilies[3];
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 433 */     if (paramObject == null) {
/* 434 */       return false;
/*     */     }
/* 436 */     if (!(paramObject instanceof LogicalFont)) {
/* 437 */       return false;
/*     */     }
/* 439 */     LogicalFont localLogicalFont = (LogicalFont)paramObject;
/*     */ 
/* 441 */     return this.fullName.equals(localLogicalFont.fullName);
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 447 */     if (this.hash != 0) {
/* 448 */       return this.hash;
/*     */     }
/*     */ 
/* 451 */     this.hash = this.fullName.hashCode();
/* 452 */     return this.hash;
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  40 */     canonicalFamilyMap.put("system", "System");
/*     */ 
/*  42 */     canonicalFamilyMap.put("serif", "Serif");
/*     */ 
/*  44 */     canonicalFamilyMap.put("sansserif", "SansSerif");
/*  45 */     canonicalFamilyMap.put("sans-serif", "SansSerif");
/*  46 */     canonicalFamilyMap.put("dialog", "SansSerif");
/*  47 */     canonicalFamilyMap.put("default", "SansSerif");
/*     */ 
/*  49 */     canonicalFamilyMap.put("monospaced", "Monospaced");
/*  50 */     canonicalFamilyMap.put("monospace", "Monospaced");
/*  51 */     canonicalFamilyMap.put("dialoginput", "Monospaced");
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.t2k.LogicalFont
 * JD-Core Version:    0.6.2
 */