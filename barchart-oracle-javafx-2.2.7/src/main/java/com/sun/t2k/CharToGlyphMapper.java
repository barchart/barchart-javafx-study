/*     */ package com.sun.t2k;
/*     */ 
/*     */ public abstract class CharToGlyphMapper
/*     */ {
/*     */   public static final int HI_SURROGATE_START = 55296;
/*     */   public static final int HI_SURROGATE_END = 56319;
/*     */   public static final int LO_SURROGATE_START = 56320;
/*     */   public static final int LO_SURROGATE_END = 57343;
/*     */   public static final int MISSING_GLYPH = 0;
/*     */   public static final int INVISIBLE_GLYPH_ID = 65535;
/*     */   public static final int INVISIBLE_GLYPHS = 65534;
/*     */   public static final int MIN_LAYOUT_CHARCODE = 768;
/*     */   public static final int MAX_LAYOUT_CHARCODE = 8303;
/* 101 */   protected int missingGlyph = 0;
/*     */ 
/*     */   public static boolean isComplexCharCode(int paramInt)
/*     */   {
/*  46 */     if ((paramInt < 768) || (paramInt > 8303)) {
/*  47 */       return false;
/*     */     }
/*  49 */     if (paramInt <= 879)
/*     */     {
/*  51 */       return true;
/*     */     }
/*  53 */     if (paramInt < 1424)
/*     */     {
/*  55 */       return false;
/*     */     }
/*  57 */     if (paramInt <= 1791)
/*     */     {
/*  60 */       return true;
/*     */     }
/*  62 */     if (paramInt < 2304) {
/*  63 */       return false;
/*     */     }
/*  65 */     if (paramInt <= 3711)
/*     */     {
/*  78 */       return true;
/*     */     }
/*  80 */     if (paramInt < 6016) {
/*  81 */       return false;
/*     */     }
/*  83 */     if (paramInt <= 6143) {
/*  84 */       return true;
/*     */     }
/*  86 */     if (paramInt < 8204) {
/*  87 */       return false;
/*     */     }
/*  89 */     if (paramInt <= 8205) {
/*  90 */       return true;
/*     */     }
/*  92 */     if ((paramInt >= 8234) && (paramInt <= 8238)) {
/*  93 */       return true;
/*     */     }
/*  95 */     if ((paramInt >= 8298) && (paramInt <= 8303)) {
/*  96 */       return true;
/*     */     }
/*  98 */     return false;
/*     */   }
/*     */ 
/*     */   public int getMissingGlyphCode()
/*     */   {
/* 104 */     return this.missingGlyph;
/*     */   }
/*     */ 
/*     */   public abstract int getNumGlyphs();
/*     */ 
/*     */   public abstract int getGlyphCode(int paramInt);
/*     */ 
/*     */   public int charToGlyph(char paramChar) {
/* 112 */     return getGlyphCode(paramChar);
/*     */   }
/*     */ 
/*     */   public int charToGlyph(int paramInt) {
/* 116 */     return getGlyphCode(paramInt);
/*     */   }
/*     */ 
/*     */   public void charsToGlyphs(int paramInt, int[] paramArrayOfInt1, int[] paramArrayOfInt2) {
/* 120 */     for (int i = 0; i < paramInt; i++)
/* 121 */       paramArrayOfInt2[i] = getGlyphCode(paramArrayOfInt1[i]);
/*     */   }
/*     */ 
/*     */   public void charsToGlyphs(int paramInt, char[] paramArrayOfChar, int[] paramArrayOfInt)
/*     */   {
/* 127 */     for (int i = 0; i < paramInt; i++) {
/* 128 */       int j = paramArrayOfChar[i];
/* 129 */       if ((j >= 55296) && (j <= 56319) && (i < paramInt - 1))
/*     */       {
/* 131 */         int k = paramArrayOfChar[(i + 1)];
/*     */ 
/* 133 */         if ((k >= 56320) && (k <= 57343))
/*     */         {
/* 135 */           j = (j - 55296) * 1024 + k - 56320 + 65536;
/*     */ 
/* 138 */           paramArrayOfInt[i] = getGlyphCode(j);
/* 139 */           i++;
/* 140 */           paramArrayOfInt[i] = 65535;
/* 141 */           continue;
/*     */         }
/*     */       }
/* 144 */       paramArrayOfInt[i] = getGlyphCode(j);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean charsToGlyphsNS(int paramInt, char[] paramArrayOfChar, int[] paramArrayOfInt)
/*     */   {
/* 155 */     for (int i = 0; i < paramInt; i++) {
/* 156 */       int j = paramArrayOfChar[i];
/* 157 */       if ((j >= 55296) && (j <= 56319) && (i < paramInt - 1))
/*     */       {
/* 159 */         int k = paramArrayOfChar[(i + 1)];
/*     */ 
/* 161 */         if ((k >= 56320) && (k <= 57343))
/*     */         {
/* 163 */           j = (j - 55296) * 1024 + k - 56320 + 65536;
/*     */ 
/* 165 */           paramArrayOfInt[(i + 1)] = 65535;
/*     */         }
/*     */       }
/*     */ 
/* 169 */       paramArrayOfInt[i] = getGlyphCode(j);
/* 170 */       if (j >= 768)
/*     */       {
/* 173 */         if (isComplexCharCode(j)) {
/* 174 */           return true;
/*     */         }
/* 176 */         if (j >= 65536) {
/* 177 */           i++;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 182 */     return false;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.t2k.CharToGlyphMapper
 * JD-Core Version:    0.6.2
 */