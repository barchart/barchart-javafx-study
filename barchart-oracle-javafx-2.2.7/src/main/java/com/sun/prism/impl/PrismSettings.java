/*     */ package com.sun.prism.impl;
/*     */ 
/*     */ import com.sun.javafx.PlatformUtil;
/*     */ import java.io.PrintStream;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ public class PrismSettings
/*     */ {
/*     */   public static boolean verbose;
/*     */   public static boolean debug;
/*     */   public static boolean trace;
/*     */   public static boolean printAllocs;
/*     */   public static boolean isVsyncEnabled;
/*     */   public static boolean dirtyOptsEnabled;
/*     */   public static boolean threadCheck;
/*     */   public static boolean cacheSimpleShapes;
/*     */   public static boolean cacheComplexShapes;
/*     */   public static boolean tessShapes;
/*     */   public static boolean tessShapesAA;
/*     */   public static boolean useNewImageLoader;
/*     */   public static String[] tryOrder;
/*     */   public static int numSamples;
/*     */   public static int prismStatFrequency;
/*     */   public static boolean doPiscesText;
/*     */   public static boolean doOpenPiscesText;
/*     */   public static boolean doT2KText;
/*     */   public static String refType;
/*     */   public static boolean forceRepaint;
/*     */   public static boolean isEmbededDevice;
/*     */   public static boolean noFallback;
/*     */   public static boolean showDirtyRegions;
/*     */   public static boolean showCull;
/*     */   public static boolean shutdownHook;
/*     */   public static int minTextureSize;
/*     */   public static int dirtyRegionCount;
/*     */   public static boolean disableBadDriverWarning;
/*     */   public static boolean forceGPU;
/*     */   public static int maxTextureSize;
/*     */ 
/*     */   public static int parseInt(String paramString1, int paramInt1, int paramInt2, String paramString2)
/*     */   {
/*  60 */     if (paramString1 != null) {
/*  61 */       if ("true".equals(paramString1))
/*  62 */         return paramInt2;
/*     */       try
/*     */       {
/*  65 */         return Integer.parseInt(paramString1);
/*     */       } catch (Exception localException) {
/*  67 */         if (paramString2 != null) {
/*  68 */           System.err.println(paramString2);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*  73 */     return paramInt1;
/*     */   }
/*     */ 
/*     */   private static void checkSettings()
/*     */   {
/*  78 */     isVsyncEnabled = ("true".equals(System.getProperty("prism.vsync", "true"))) && (!Boolean.getBoolean("javafx.animation.fullspeed"));
/*     */ 
/*  83 */     dirtyOptsEnabled = "true".equals(System.getProperty("prism.dirtyopts", "true"));
/*     */ 
/*  86 */     Integer localInteger = Integer.getInteger("prism.dirtyregioncount");
/*  87 */     if (localInteger != null)
/*  88 */       dirtyRegionCount = localInteger.intValue();
/*     */     else {
/*  90 */       dirtyRegionCount = 6;
/*     */     }
/*     */ 
/*  94 */     threadCheck = "true".equals(System.getProperty("prism.threadcheck", "false"));
/*     */ 
/*  97 */     showDirtyRegions = Boolean.getBoolean("prism.showdirty");
/*     */ 
/* 100 */     showCull = Boolean.getBoolean("prism.showcull");
/*     */ 
/* 103 */     forceRepaint = Boolean.getBoolean("prism.forcerepaint");
/*     */ 
/* 106 */     noFallback = Boolean.getBoolean("prism.noFallback");
/*     */ 
/* 109 */     String str1 = System.getProperty("prism.cacheshapes", "complex");
/* 110 */     if (("all".equals(str1)) || ("true".equals(str1))) {
/* 111 */       cacheSimpleShapes = true;
/* 112 */       cacheComplexShapes = true;
/* 113 */     } else if ("complex".equals(str1)) {
/* 114 */       cacheSimpleShapes = false;
/* 115 */       cacheComplexShapes = true;
/*     */     }
/*     */ 
/* 119 */     tessShapes = Boolean.getBoolean("prism.tess");
/* 120 */     tessShapesAA = Boolean.getBoolean("prism.tessaa");
/* 121 */     if (tessShapesAA) {
/* 122 */       tessShapes = true;
/*     */     }
/*     */ 
/* 126 */     String str2 = System.getProperty("prism.newiio", "true");
/* 127 */     useNewImageLoader = "true".equals(str2);
/*     */ 
/* 130 */     verbose = Boolean.getBoolean("prism.verbose");
/*     */ 
/* 133 */     prismStatFrequency = parseInt(System.getProperty("prism.printStats"), 0, 1, "Try -Dprism.printStats=<true or number>");
/*     */ 
/* 137 */     debug = Boolean.getBoolean("prism.debug");
/*     */ 
/* 140 */     trace = Boolean.getBoolean("prism.trace");
/*     */ 
/* 143 */     printAllocs = Boolean.getBoolean("prism.printallocs");
/*     */ 
/* 146 */     disableBadDriverWarning = Boolean.getBoolean("prism.disableBadDriverWarning");
/*     */ 
/* 149 */     forceGPU = Boolean.getBoolean("prism.forceGPU");
/*     */ 
/* 152 */     numSamples = 0;
/*     */ 
/* 154 */     String str3 = System.getProperty("prism.order");
/* 155 */     if (str3 != null) {
/* 156 */       tryOrder = split(str3, ",");
/*     */     }
/* 158 */     else if (PlatformUtil.isWindows())
/* 159 */       tryOrder = new String[] { "d3d", "j2d" };
/* 160 */     else if (PlatformUtil.isMac())
/* 161 */       tryOrder = new String[] { "es2", "j2d" };
/* 162 */     else if (PlatformUtil.isLinux())
/* 163 */       tryOrder = new String[] { "es2", "j2d" };
/*     */     else {
/* 165 */       tryOrder = new String[] { "j2d" };
/*     */     }
/*     */ 
/* 169 */     String str4 = System.getProperty("prism.multisample");
/* 170 */     if (str4 != null) {
/* 171 */       if ("true".equals(str4))
/* 172 */         numSamples = 2;
/*     */       else {
/*     */         try {
/* 175 */           numSamples = Integer.parseInt(str4);
/*     */         } catch (Exception localException1) {
/* 177 */           System.err.println("Try -Dprism.multisample=<true|2|4|8>");
/*     */         }
/*     */       }
/* 180 */       if (numSamples > 0) {
/* 181 */         System.out.println("Enabling multisampling with " + numSamples + " samples per pixel");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 188 */     String str5 = System.getProperty("prism.text", "t2k");
/* 189 */     doPiscesText = "pisces".equals(str5);
/* 190 */     doOpenPiscesText = "openpisces".equals(str5);
/* 191 */     doT2KText = (!doPiscesText) && (!doOpenPiscesText);
/* 192 */     if (doT2KText) {
/* 193 */       str5 = "t2k";
/*     */     }
/*     */ 
/* 197 */     refType = System.getProperty("prism.reftype");
/*     */ 
/* 200 */     if (PlatformUtil.isUnix()) {
/* 201 */       localObject = System.getProperty("prism.shutdownHook");
/* 202 */       if (localObject == null)
/* 203 */         shutdownHook = true;
/*     */       else
/* 205 */         shutdownHook = Boolean.getBoolean("prism.shutdownHook");
/*     */     }
/*     */     else {
/* 208 */       shutdownHook = Boolean.getBoolean("prism.shutdownHook");
/*     */     }
/*     */ 
/* 211 */     isEmbededDevice = Boolean.getBoolean("prism.device");
/*     */ 
/* 213 */     if (verbose) {
/* 214 */       System.out.print("Prism pipeline init order: ");
/* 215 */       for (String str7 : tryOrder) {
/* 216 */         System.out.print(str7 + " ");
/*     */       }
/* 218 */       System.out.println("");
/* 219 */       System.out.println("Using " + str5 + " for text rasterization");
/* 220 */       if (dirtyOptsEnabled)
/* 221 */         System.out.println("Using dirty region optimizations");
/*     */       else {
/* 223 */         System.out.println("Not using dirty region optimizations");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 237 */     maxTextureSize = 4096;
/* 238 */     Object localObject = System.getProperty("prism.maxTextureSize");
/* 239 */     if (localObject != null) {
/*     */       try {
/* 241 */         ??? = Integer.parseInt((String)localObject);
/* 242 */         if (??? <= 0) {
/* 243 */           ??? = 2147483647;
/*     */         }
/* 245 */         maxTextureSize = ???;
/*     */       } catch (Exception localException2) {
/* 247 */         System.err.println("Try -Dprism.maxTextureSize=<number>");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 256 */     if (isEmbededDevice) {
/* 257 */       minTextureSize = 0;
/* 258 */       String str6 = System.getProperty("prism.mintexturesize");
/* 259 */       if (str6 != null)
/*     */         try {
/* 261 */           minTextureSize = Integer.parseInt(str6);
/*     */         } catch (Exception localException3) {
/* 263 */           System.err.println("Try -Dprism.mintexturesize=<number>");
/*     */         }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static String[] split(String paramString1, String paramString2)
/*     */   {
/* 271 */     StringTokenizer localStringTokenizer = new StringTokenizer(paramString1, paramString2);
/* 272 */     String[] arrayOfString = new String[localStringTokenizer.countTokens()];
/* 273 */     int i = 0;
/* 274 */     while (localStringTokenizer.hasMoreTokens()) {
/* 275 */       arrayOfString[(i++)] = localStringTokenizer.nextToken();
/*     */     }
/* 277 */     return arrayOfString;
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  51 */     AccessController.doPrivileged(new PrivilegedAction() {
/*     */       public Object run() {
/*  53 */         PrismSettings.access$000();
/*  54 */         return null;
/*     */       }
/*     */     });
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.impl.PrismSettings
 * JD-Core Version:    0.6.2
 */