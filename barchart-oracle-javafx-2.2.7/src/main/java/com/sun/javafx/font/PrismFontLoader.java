/*     */ package com.sun.javafx.font;
/*     */ 
/*     */ import com.sun.javafx.PlatformUtil;
/*     */ import com.sun.javafx.scene.text.FontManager;
/*     */ import com.sun.javafx.tk.FontLoader;
/*     */ import com.sun.javafx.tk.FontMetrics;
/*     */ import com.sun.t2k.T2KFontFactory;
/*     */ import java.io.InputStream;
/*     */ import java.lang.reflect.Method;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javafx.scene.text.Font;
/*     */ import javafx.scene.text.FontPosture;
/*     */ import javafx.scene.text.FontWeight;
/*     */ 
/*     */ public class PrismFontLoader extends FontLoader
/*     */ {
/*  22 */   private static PrismFontLoader theInstance = new PrismFontLoader();
/*     */   private static boolean lcdEnabled;
/*  45 */   private boolean embeddedFontsLoaded = false;
/*  46 */   private boolean runtimeEmbeddedFontsLoaded = false;
/*  47 */   private boolean runtimeEmbeddedFontsInstalled = false;
/*     */ 
/*  51 */   static Map<String, String> cacheRTFontNameToPathMap = new HashMap();
/*     */   private static boolean lcdTextSupported;
/* 484 */   FontFactory installedFontFactory = null;
/*     */ 
/*     */   public static PrismFontLoader getInstance()
/*     */   {
/*  23 */     return theInstance;
/*     */   }
/*     */ 
/*     */   private void loadEmbeddedFonts()
/*     */   {
/*  55 */     if (!this.embeddedFontsLoaded) {
/*  56 */       String[] arrayOfString1 = FontManager.getInstance().getAllNames();
/*  57 */       FontFactory localFontFactory = getFontFactoryFromPipeline();
/*  58 */       for (String str1 : arrayOfString1) {
/*  59 */         String str2 = FontManager.getInstance().findPathByName(str1);
/*  60 */         InputStream localInputStream = null;
/*     */         try {
/*  62 */           localInputStream = getClass().getResourceAsStream(str2);
/*  63 */           localFontFactory.loadEmbeddedFont(str1, localInputStream, 0.0F, true);
/*     */         }
/*     */         catch (Exception localException2)
/*     */         {
/*     */         }
/*     */         finally
/*     */         {
/*  71 */           if (localInputStream != null)
/*     */             try {
/*  73 */               localInputStream.close();
/*     */             }
/*     */             catch (Exception localException4) {
/*     */             }
/*     */         }
/*     */       }
/*  79 */       this.embeddedFontsLoaded = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void loadRuntimeEmbeddedFonts()
/*     */   {
/*  88 */     if (!this.runtimeEmbeddedFontsInstalled) {
/*  89 */       return;
/*     */     }
/*  91 */     if (!this.runtimeEmbeddedFontsLoaded)
/*     */     {
/*  94 */       String[] arrayOfString1 = AmbleMapper.getAllFullNames();
/*     */ 
/*  96 */       FontFactory localFontFactory = getFontFactoryFromPipeline();
/*     */ 
/* 100 */       for (String str3 : arrayOfString1) {
/* 101 */         String str1 = null;
/* 102 */         String str2 = str3.toLowerCase();
/* 103 */         if (!cacheRTFontNameToPathMap.containsKey(str2)) {
/* 104 */           str1 = AmbleMapper.getFilePath(str3);
/* 105 */           cacheRTFontNameToPathMap.put(str2, str1);
/* 106 */           if (str1 != null)
/*     */           {
/* 110 */             localFontFactory.loadEmbeddedFont(str3, str1, 0.0F, true);
/*     */           }
/*     */         }
/*     */       }
/* 113 */       this.runtimeEmbeddedFontsLoaded = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Font font(Object paramObject, float paramFloat) {
/* 118 */     FontFactory localFontFactory = getFontFactoryFromPipeline();
/* 119 */     InputStream localInputStream = (InputStream)paramObject;
/* 120 */     PGFont localPGFont = localFontFactory.loadEmbeddedFont(null, localInputStream, paramFloat, true);
/* 121 */     if (localPGFont != null) return createFont(localPGFont);
/* 122 */     return new Font("System Regular", paramFloat);
/*     */   }
/*     */ 
/*     */   public Font loadFont(InputStream paramInputStream, double paramDouble) {
/* 126 */     FontFactory localFontFactory = getFontFactoryFromPipeline();
/* 127 */     PGFont localPGFont = localFontFactory.loadEmbeddedFont(null, paramInputStream, (float)paramDouble, true);
/* 128 */     if (localPGFont != null) return createFont(localPGFont);
/* 129 */     return null;
/*     */   }
/*     */ 
/*     */   public Font loadFont(String paramString, double paramDouble) {
/* 133 */     FontFactory localFontFactory = getFontFactoryFromPipeline();
/* 134 */     PGFont localPGFont = localFontFactory.loadEmbeddedFont(null, paramString, (float)paramDouble, true);
/* 135 */     if (localPGFont != null) return createFont(localPGFont);
/* 136 */     return null;
/*     */   }
/*     */ 
/*     */   private Font createFont(PGFont paramPGFont)
/*     */   {
/* 141 */     return Font.impl_NativeFont(paramPGFont, paramPGFont.getName(), paramPGFont.getFamilyName(), paramPGFont.getStyleName(), paramPGFont.getSize());
/*     */   }
/*     */ 
/*     */   public List<String> getFamilies()
/*     */   {
/* 155 */     loadEmbeddedFonts();
/* 156 */     loadRuntimeEmbeddedFonts();
/* 157 */     return Arrays.asList(getFontFactoryFromPipeline().getFontFamilyNames());
/*     */   }
/*     */ 
/*     */   public List<String> getFontNames()
/*     */   {
/* 168 */     loadEmbeddedFonts();
/* 169 */     loadRuntimeEmbeddedFonts();
/* 170 */     return Arrays.asList(getFontFactoryFromPipeline().getFontFullNames());
/*     */   }
/*     */ 
/*     */   public List<String> getFontNames(String paramString)
/*     */   {
/* 181 */     loadEmbeddedFonts();
/* 182 */     loadRuntimeEmbeddedFonts();
/* 183 */     return Arrays.asList(getFontFactoryFromPipeline().getFontFullNames(paramString));
/*     */   }
/*     */ 
/*     */   public Font font(String paramString, FontWeight paramFontWeight, FontPosture paramFontPosture, float paramFloat)
/*     */   {
/* 211 */     PGFont localPGFont = null;
/*     */ 
/* 213 */     if (paramFontWeight == null) {
/* 214 */       paramFontWeight = FontWeight.NORMAL;
/*     */     }
/* 216 */     if (paramFontPosture == null)
/* 217 */       paramFontPosture = FontPosture.REGULAR;
/*     */     boolean bool1;
/*     */     boolean bool2;
/* 221 */     if ((this.runtimeEmbeddedFontsInstalled) && (paramString.toLowerCase().startsWith("amble"))) {
/* 222 */       String str1 = paramString.toLowerCase();
/* 223 */       String str2 = "Amble";
/* 224 */       FontFactory localFontFactory = getFontFactoryFromPipeline();
/*     */ 
/* 228 */       int i = 0;
/*     */ 
/* 230 */       boolean bool3 = str1.contains("lt");
/*     */ 
/* 239 */       switch (2.$SwitchMap$javafx$scene$text$FontWeight[paramFontWeight.ordinal()]) {
/*     */       case 1:
/*     */       case 2:
/*     */       case 3:
/* 243 */         str2 = str2 + " Light";
/* 244 */         break;
/*     */       case 4:
/*     */       case 5:
/*     */       case 6:
/* 248 */         if (bool3)
/*     */         {
/* 250 */           str2 = str2 + " Light";
/*     */         }
/* 252 */         else i = 1;
/*     */ 
/* 254 */         break;
/*     */       case 7:
/*     */       case 8:
/*     */       case 9:
/* 260 */         if (bool3) {
/* 261 */           i = 1;
/*     */         } else {
/* 263 */           str2 = str2 + " Bold";
/* 264 */           bool1 = true;
/*     */         }
/* 266 */         break;
/*     */       default:
/* 269 */         i = 1;
/*     */       }
/*     */ 
/* 272 */       if (str1.contains("cn")) {
/* 273 */         str2 = str2 + " Condensed";
/* 274 */         i = 0;
/*     */       }
/* 276 */       switch (paramFontPosture) {
/*     */       case ITALIC:
/* 278 */         bool2 = true;
/* 279 */         str2 = str2 + " Italic";
/* 280 */         break;
/*     */       case REGULAR:
/*     */       default:
/* 283 */         if (i != 0) {
/* 284 */           str2 = "Amble Regular";
/*     */         }
/*     */ 
/*     */         break;
/*     */       }
/*     */ 
/* 290 */       String str4 = str2.toLowerCase();
/*     */       String str3;
/* 309 */       if (!cacheRTFontNameToPathMap.containsKey(str4)) {
/* 310 */         str3 = AmbleMapper.getFilePath(str2);
/* 311 */         cacheRTFontNameToPathMap.put(str4, str3);
/* 312 */         if ((str3 != null) && (!this.runtimeEmbeddedFontsLoaded)) {
/* 313 */           localPGFont = localFontFactory.loadEmbeddedFont(str2, str3, paramFloat, true);
/*     */ 
/* 315 */           str2 = localPGFont != null ? localPGFont.getFullName() : null;
/*     */         }
/*     */       } else {
/* 318 */         str3 = (String)cacheRTFontNameToPathMap.get(str4);
/*     */       }
/*     */ 
/* 321 */       if (localPGFont == null)
/* 322 */         localPGFont = localFontFactory.createFont(str2, paramFloat);
/*     */     }
/*     */     else {
/* 325 */       if ((paramFontWeight == null) || (paramFontWeight.ordinal() < FontWeight.BOLD.ordinal()))
/*     */       {
/* 327 */         bool1 = false;
/*     */       }
/* 329 */       else bool1 = true;
/*     */ 
/* 331 */       bool2 = paramFontPosture == FontPosture.ITALIC;
/* 332 */       float f = paramFloat <= 0.0F ? 1.0F : paramFloat;
/*     */ 
/* 334 */       localPGFont = getFontFactoryFromPipeline().createFont(paramString, bool1, bool2, f);
/*     */     }
/*     */ 
/* 338 */     Font localFont = Font.impl_NativeFont(localPGFont, localPGFont.getName(), localPGFont.getFamilyName(), localPGFont.getStyleName(), paramFloat);
/*     */ 
/* 341 */     return localFont;
/*     */   }
/*     */ 
/*     */   public void loadFont(Font paramFont)
/*     */   {
/* 349 */     loadEmbeddedFonts();
/* 350 */     int i = 0;
/*     */ 
/* 352 */     PGFont localPGFont = null;
/* 353 */     FontFactory localFontFactory = getFontFactoryFromPipeline();
/*     */ 
/* 355 */     String str1 = paramFont.getName();
/* 356 */     String str2 = str1.toLowerCase();
/*     */     String str3;
/* 362 */     if ((this.runtimeEmbeddedFontsInstalled) && (str2.startsWith("amble")) && (!this.runtimeEmbeddedFontsLoaded))
/*     */     {
/* 383 */       if (!cacheRTFontNameToPathMap.containsKey(str2)) {
/* 384 */         str3 = AmbleMapper.getFilePath(str1);
/* 385 */         cacheRTFontNameToPathMap.put(str2, str3);
/* 386 */         if (str3 != null) {
/* 387 */           localPGFont = localFontFactory.loadEmbeddedFont(str1, str3, (float)paramFont.getSize(), true);
/*     */ 
/* 390 */           str1 = localPGFont != null ? localPGFont.getFullName() : null;
/*     */         } else {
/* 392 */           i = 1;
/*     */         }
/*     */       } else {
/* 395 */         i = 1;
/*     */       }
/*     */     }
/* 398 */     if (localPGFont == null) {
/* 399 */       localPGFont = localFontFactory.createFont(str1, (float)paramFont.getSize());
/*     */     }
/*     */ 
/* 436 */     if (i != 0)
/*     */     {
/* 438 */       paramFont.impl_setNativeFont(localPGFont, localPGFont.getName(), "", "");
/*     */     }
/*     */     else {
/* 441 */       str3 = localPGFont.getName();
/* 442 */       String str4 = localPGFont.getFamilyName();
/* 443 */       String str5 = localPGFont.getStyleName();
/* 444 */       paramFont.impl_setNativeFont(localPGFont, str3, str4, str5);
/*     */     }
/*     */   }
/*     */ 
/*     */   public FontMetrics getFontMetrics(Font paramFont) {
/* 449 */     if (paramFont != null) {
/* 450 */       PGFont localPGFont = (PGFont)paramFont.impl_getNativeFont();
/* 451 */       FontStrike.Metrics localMetrics = PrismFontUtils.getFontMetrics(localPGFont);
/*     */ 
/* 453 */       float f1 = -localMetrics.getAscent();
/* 454 */       float f2 = -localMetrics.getAscent();
/* 455 */       float f3 = localMetrics.getXHeight();
/* 456 */       float f4 = localMetrics.getDescent();
/*     */ 
/* 458 */       float f5 = localMetrics.getDescent();
/* 459 */       float f6 = localMetrics.getLineGap();
/* 460 */       return FontMetrics.impl_createFontMetrics(f1, f2, f3, f4, f5, f6, paramFont);
/*     */     }
/* 462 */     return null;
/*     */   }
/*     */ 
/*     */   public float computeStringWidth(String paramString, Font paramFont)
/*     */   {
/* 467 */     PGFont localPGFont = (PGFont)paramFont.impl_getNativeFont();
/* 468 */     return (float)PrismFontUtils.computeStringWidth(localPGFont, paramString);
/*     */   }
/*     */ 
/*     */   public float getSystemFontSize()
/*     */   {
/* 474 */     return T2KFontFactory.getSystemFontSize();
/*     */   }
/*     */ 
/*     */   public static boolean isLCDTextSupported()
/*     */   {
/* 481 */     return (lcdEnabled) && (lcdTextSupported);
/*     */   }
/*     */ 
/*     */   public FontFactory getFontFactoryFromPipeline()
/*     */   {
/* 486 */     if (this.installedFontFactory != null)
/* 487 */       return this.installedFontFactory;
/*     */     try
/*     */     {
/* 490 */       Class localClass = Class.forName("com.sun.prism.GraphicsPipeline");
/* 491 */       Method localMethod1 = localClass.getMethod("getPipeline", (Class[])null);
/* 492 */       Object localObject1 = localMethod1.invoke(null, new Object[0]);
/* 493 */       Method localMethod2 = localClass.getMethod("getFontFactory", (Class[])null);
/* 494 */       Object localObject2 = localMethod2.invoke(localObject1, new Object[0]);
/* 495 */       this.installedFontFactory = ((FontFactory)localObject2);
/* 496 */       String str = localObject1.getClass().getName();
/*     */ 
/* 498 */       lcdTextSupported = str.indexOf("ES1Pipe") == -1;
/*     */     } catch (Exception localException) {
/*     */     }
/* 501 */     return this.installedFontFactory;
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  28 */     AccessController.doPrivileged(new PrivilegedAction()
/*     */     {
/*     */       public Object run()
/*     */       {
/*  33 */         String str1 = PlatformUtil.isMac() ? "false" : "true";
/*  34 */         String str2 = System.getProperty("prism.lcdtext", str1);
/*  35 */         PrismFontLoader.access$002(str2.equals("true"));
/*  36 */         return null;
/*     */       }
/*     */     });
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.font.PrismFontLoader
 * JD-Core Version:    0.6.2
 */