/*     */ package com.sun.webpane.webkit.unicode;
/*     */ 
/*     */ import java.text.BreakIterator;
/*     */ import java.text.CharacterIterator;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.WeakHashMap;
/*     */ 
/*     */ public class TextBreakIterator
/*     */ {
/*     */   public static final int CHARACTER_ITERATOR = 0;
/*     */   public static final int WORD_ITERATOR = 1;
/*     */   public static final int LINE_ITERATOR = 2;
/*     */   public static final int SENTENCE_ITERATOR = 3;
/*     */   public static final int TEXT_BREAK_FIRST = 0;
/*     */   public static final int TEXT_BREAK_LAST = 1;
/*     */   public static final int TEXT_BREAK_NEXT = 2;
/*     */   public static final int TEXT_BREAK_PREVIOUS = 3;
/*     */   public static final int TEXT_BREAK_CURRENT = 4;
/*     */   public static final int TEXT_BREAK_PRECEDING = 5;
/*     */   public static final int TEXT_BREAK_FOLLOWING = 6;
/*     */   public static final int IS_TEXT_BREAK = 7;
/*  55 */   private static final Map<CacheKey, BreakIterator> iteratorCache = new WeakHashMap();
/*     */ 
/*     */   public static BreakIterator getIterator(int paramInt, String paramString1, String paramString2)
/*     */   {
/*  60 */     String[] arrayOfString = paramString1.split("-");
/*     */     String str1;
/*  62 */     switch (arrayOfString.length) { case 1:
/*  63 */       str1 = null; break;
/*     */     case 2:
/*  64 */       str1 = arrayOfString[1]; break;
/*     */     default:
/*  65 */       str1 = arrayOfString[2];
/*     */     }
/*  67 */     String str2 = arrayOfString[0].toLowerCase();
/*  68 */     Locale localLocale = str1 == null ? new Locale(str2) : new Locale(str2, str1.toUpperCase());
/*     */ 
/*  71 */     CacheKey localCacheKey = new CacheKey(paramInt, localLocale);
/*  72 */     BreakIterator localBreakIterator = (BreakIterator)iteratorCache.get(localCacheKey);
/*  73 */     if (localBreakIterator == null) {
/*  74 */       localBreakIterator = createIterator(paramInt, localLocale);
/*  75 */       iteratorCache.put(localCacheKey, localBreakIterator);
/*     */     }
/*  77 */     localBreakIterator.setText(paramString2);
/*  78 */     return localBreakIterator;
/*     */   }
/*     */ 
/*     */   private static BreakIterator createIterator(int paramInt, Locale paramLocale) {
/*  82 */     switch (paramInt) {
/*     */     case 0:
/*  84 */       return BreakIterator.getCharacterInstance(paramLocale);
/*     */     case 1:
/*  86 */       return BreakIterator.getWordInstance(paramLocale);
/*     */     case 2:
/*  88 */       return BreakIterator.getLineInstance(paramLocale);
/*     */     case 3:
/*  90 */       return BreakIterator.getSentenceInstance(paramLocale);
/*     */     }
/*  92 */     throw new IllegalArgumentException("invalid type: " + paramInt);
/*     */   }
/*     */ 
/*     */   public static int invokeMethod(BreakIterator paramBreakIterator, int paramInt1, int paramInt2)
/*     */   {
/*  99 */     CharacterIterator localCharacterIterator = paramBreakIterator.getText();
/* 100 */     int i = localCharacterIterator.getEndIndex() - localCharacterIterator.getBeginIndex();
/* 101 */     if ((paramInt1 == 5) && (paramInt2 > i))
/*     */     {
/* 103 */       return i;
/*     */     }
/* 105 */     if ((paramInt2 < 0) || (paramInt2 > i)) {
/* 106 */       paramInt2 = paramInt2 < 0 ? 0 : i;
/*     */     }
/*     */ 
/* 109 */     switch (paramInt1) { case 0:
/* 110 */       return paramBreakIterator.first();
/*     */     case 1:
/* 111 */       return paramBreakIterator.last();
/*     */     case 2:
/* 112 */       return paramBreakIterator.next();
/*     */     case 3:
/* 113 */       return paramBreakIterator.previous();
/*     */     case 4:
/* 114 */       return paramBreakIterator.current();
/*     */     case 5:
/* 115 */       return paramBreakIterator.preceding(paramInt2);
/*     */     case 6:
/* 116 */       return paramBreakIterator.following(paramInt2);
/*     */     case 7:
/* 117 */       return paramBreakIterator.isBoundary(paramInt2) ? 1 : 0;
/*     */     }
/* 119 */     throw new IllegalArgumentException("invalid method: " + paramInt1);
/*     */   }
/*     */ 
/*     */   private static final class CacheKey
/*     */   {
/*     */     private final int type;
/*     */     private final Locale locale;
/*     */     private final int hashCode;
/*     */ 
/*     */     CacheKey(int paramInt, Locale paramLocale)
/*     */     {
/*  37 */       this.type = paramInt;
/*  38 */       this.locale = paramLocale;
/*  39 */       this.hashCode = (paramLocale.hashCode() + paramInt);
/*     */     }
/*     */ 
/*     */     public boolean equals(Object paramObject) {
/*  43 */       if (!(paramObject instanceof CacheKey)) {
/*  44 */         return false;
/*     */       }
/*  46 */       CacheKey localCacheKey = (CacheKey)paramObject;
/*  47 */       return (localCacheKey.type == this.type) && (localCacheKey.locale.equals(this.locale));
/*     */     }
/*     */ 
/*     */     public int hashCode() {
/*  51 */       return this.hashCode;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.unicode.TextBreakIterator
 * JD-Core Version:    0.6.2
 */