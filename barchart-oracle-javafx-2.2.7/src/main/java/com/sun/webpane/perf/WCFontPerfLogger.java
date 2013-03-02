/*     */ package com.sun.webpane.perf;
/*     */ 
/*     */ import com.sun.webpane.platform.graphics.WCFont;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ public class WCFontPerfLogger extends WCFont
/*     */ {
/*  11 */   private static Logger log = Logger.getLogger(WCFontPerfLogger.class.getName());
/*     */ 
/*  13 */   private static PerfLogger logger = PerfLogger.getLogger(log);
/*     */   private WCFont fnt;
/*     */ 
/*     */   public WCFontPerfLogger(WCFont paramWCFont)
/*     */   {
/*  18 */     this.fnt = paramWCFont;
/*     */   }
/*     */ 
/*     */   public static synchronized boolean isEnabled() {
/*  22 */     return logger.isEnabled();
/*     */   }
/*     */ 
/*     */   public static void log() {
/*  26 */     logger.log();
/*     */   }
/*     */ 
/*     */   public static void reset() {
/*  30 */     logger.reset();
/*     */   }
/*     */ 
/*     */   public Object getPlatformFont() {
/*  34 */     return this.fnt.getPlatformFont();
/*     */   }
/*     */ 
/*     */   public int getOffsetForPosition(String paramString, int paramInt, float paramFloat1, float paramFloat2)
/*     */   {
/*  40 */     logger.resumeCount("GETOFFSETFORPOSITION");
/*  41 */     int i = this.fnt.getOffsetForPosition(paramString, paramInt, paramFloat1, paramFloat2);
/*  42 */     logger.suspendCount("GETOFFSETFORPOSITION");
/*  43 */     return i;
/*     */   }
/*     */ 
/*     */   public int[] getGlyphCodes(int[] paramArrayOfInt)
/*     */   {
/*  48 */     logger.resumeCount("GETGLYPHCODES");
/*  49 */     int[] arrayOfInt = this.fnt.getGlyphCodes(paramArrayOfInt);
/*  50 */     logger.suspendCount("GETGLYPHCODES");
/*  51 */     return arrayOfInt;
/*     */   }
/*     */ 
/*     */   public double getXHeight() {
/*  55 */     logger.resumeCount("GETXHEIGHT");
/*  56 */     double d = this.fnt.getXHeight();
/*  57 */     logger.suspendCount("GETXHEIGHT");
/*  58 */     return d;
/*     */   }
/*     */ 
/*     */   public double getGlyphWidth(int paramInt) {
/*  62 */     logger.resumeCount("GETGLYPHWIDTH");
/*  63 */     double d = this.fnt.getGlyphWidth(paramInt);
/*  64 */     logger.suspendCount("GETGLYPHWIDTH");
/*  65 */     return d;
/*     */   }
/*     */ 
/*     */   public double getStringLength(String paramString, float paramFloat1, float paramFloat2) {
/*  69 */     logger.resumeCount("GETSTRINGLENGTH");
/*  70 */     double d = this.fnt.getStringLength(paramString, paramFloat1, paramFloat2);
/*  71 */     logger.suspendCount("GETSTRINGLENGTH");
/*  72 */     return d;
/*     */   }
/*     */ 
/*     */   public double[] getStringBounds(String paramString, int paramInt1, int paramInt2, boolean paramBoolean, float paramFloat1, float paramFloat2)
/*     */   {
/*  78 */     logger.resumeCount("GETSTRINGBOUNDS");
/*  79 */     double[] arrayOfDouble = this.fnt.getStringBounds(paramString, paramInt1, paramInt2, paramBoolean, paramFloat1, paramFloat2);
/*  80 */     logger.suspendCount("GETSTRINGBOUNDS");
/*  81 */     return arrayOfDouble;
/*     */   }
/*     */ 
/*     */   public int hashCode() {
/*  85 */     logger.resumeCount("HASH");
/*  86 */     int i = this.fnt.hashCode();
/*  87 */     logger.suspendCount("HASH");
/*  88 */     return i;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject) {
/*  92 */     logger.resumeCount("COMPARE");
/*  93 */     boolean bool = this.fnt.equals(paramObject);
/*  94 */     logger.suspendCount("COMPARE");
/*  95 */     return bool;
/*     */   }
/*     */ 
/*     */   public int getAscent() {
/*  99 */     logger.resumeCount("GETASCENT");
/* 100 */     int i = this.fnt.getAscent();
/* 101 */     logger.suspendCount("GETASCENT");
/* 102 */     return i;
/*     */   }
/*     */ 
/*     */   public int getDescent() {
/* 106 */     logger.resumeCount("GETDESCENT");
/* 107 */     int i = this.fnt.getDescent();
/* 108 */     logger.suspendCount("GETDESCENT");
/* 109 */     return i;
/*     */   }
/*     */ 
/*     */   public int getHeight() {
/* 113 */     logger.resumeCount("GETHEIGHT");
/* 114 */     int i = this.fnt.getHeight();
/* 115 */     logger.suspendCount("GETHEIGHT");
/* 116 */     return i;
/*     */   }
/*     */ 
/*     */   public boolean hasUniformLineMetrics() {
/* 120 */     logger.resumeCount("HASUNIFORMLINEMETRICS");
/* 121 */     boolean bool = this.fnt.hasUniformLineMetrics();
/* 122 */     logger.suspendCount("HASUNIFORMLINEMETRICS");
/* 123 */     return bool;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.perf.WCFontPerfLogger
 * JD-Core Version:    0.6.2
 */