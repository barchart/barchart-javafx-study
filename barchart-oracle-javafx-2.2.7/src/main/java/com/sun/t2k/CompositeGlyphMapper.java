/*     */ package com.sun.t2k;
/*     */ 
/*     */ import com.sun.javafx.font.FontResource;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ class CompositeGlyphMapper extends CharToGlyphMapper
/*     */ {
/*     */   public static final int SLOTMASK = -16777216;
/*     */   public static final int GLYPHMASK = 16777215;
/*     */   public static final int NBLOCKS = 216;
/*     */   public static final int BLOCKSZ = 256;
/*     */   public static final int MAXUNICODE = 55296;
/*     */   private static final int SIMPLE_ASCII_MASK_START = 32;
/*     */   private static final int SIMPLE_ASCII_MASK_END = 126;
/*     */   private static final int ASCII_COUNT = 95;
/*     */   private boolean asciiCacheOK;
/*     */   private char[] charToGlyph;
/*     */   CompositeFontResource font;
/*     */   CharToGlyphMapper[] slotMappers;
/*     */   HashMap<Integer, Integer> glyphMap;
/*     */ 
/*     */   public CompositeGlyphMapper(CompositeFontResource paramCompositeFontResource)
/*     */   {
/*  38 */     this.font = paramCompositeFontResource;
/*  39 */     this.missingGlyph = 0;
/*  40 */     this.glyphMap = new HashMap();
/*  41 */     this.slotMappers = new CharToGlyphMapper[paramCompositeFontResource.getNumSlots()];
/*  42 */     this.asciiCacheOK = true;
/*     */   }
/*     */ 
/*     */   public int getNumGlyphs() {
/*  46 */     return getSlotMapper(0).getNumGlyphs();
/*     */   }
/*     */ 
/*     */   private final CharToGlyphMapper getSlotMapper(int paramInt) {
/*  50 */     CharToGlyphMapper localCharToGlyphMapper = this.slotMappers[paramInt];
/*  51 */     if (localCharToGlyphMapper == null) {
/*  52 */       localCharToGlyphMapper = this.font.getSlotResource(paramInt).getGlyphMapper();
/*  53 */       this.slotMappers[paramInt] = localCharToGlyphMapper;
/*     */     }
/*  55 */     return localCharToGlyphMapper;
/*     */   }
/*     */ 
/*     */   public int getMissingGlyphCode() {
/*  59 */     return this.missingGlyph;
/*     */   }
/*     */ 
/*     */   public final int compositeGlyphCode(int paramInt1, int paramInt2)
/*     */   {
/*  68 */     return paramInt1 << 24 | paramInt2 & 0xFFFFFF;
/*     */   }
/*     */ 
/*     */   private final int convertToGlyph(int paramInt) {
/*  72 */     for (int i = 0; i < this.font.getNumSlots(); i++) {
/*  73 */       CharToGlyphMapper localCharToGlyphMapper = getSlotMapper(i);
/*  74 */       int j = localCharToGlyphMapper.charToGlyph(paramInt);
/*  75 */       if (j != localCharToGlyphMapper.getMissingGlyphCode()) {
/*  76 */         j = compositeGlyphCode(i, j);
/*  77 */         this.glyphMap.put(Integer.valueOf(paramInt), Integer.valueOf(j));
/*  78 */         return j;
/*     */       }
/*     */     }
/*  81 */     this.glyphMap.put(Integer.valueOf(paramInt), Integer.valueOf(this.missingGlyph));
/*  82 */     return this.missingGlyph;
/*     */   }
/*     */ 
/*     */   private int getAsciiGlyphCode(int paramInt)
/*     */   {
/*  88 */     if ((!this.asciiCacheOK) || (paramInt > 126) || (paramInt < 32))
/*     */     {
/*  91 */       return -1;
/*     */     }
/*     */ 
/*  95 */     if (this.charToGlyph == null) {
/*  96 */       char[] arrayOfChar = new char[95];
/*  97 */       CharToGlyphMapper localCharToGlyphMapper = getSlotMapper(0);
/*  98 */       int j = localCharToGlyphMapper.getMissingGlyphCode();
/*  99 */       for (int k = 0; k < 95; k++) {
/* 100 */         int m = localCharToGlyphMapper.charToGlyph(32 + k);
/* 101 */         if (m == j)
/*     */         {
/* 104 */           this.charToGlyph = null;
/* 105 */           this.asciiCacheOK = false;
/* 106 */           return -1;
/*     */         }
/*     */ 
/* 109 */         arrayOfChar[k] = ((char)m);
/*     */       }
/* 111 */       this.charToGlyph = arrayOfChar;
/*     */     }
/*     */ 
/* 114 */     int i = paramInt - 32;
/* 115 */     return this.charToGlyph[i];
/*     */   }
/*     */ 
/*     */   public int getGlyphCode(int paramInt)
/*     */   {
/* 120 */     int i = getAsciiGlyphCode(paramInt);
/* 121 */     if (i >= 0) {
/* 122 */       return i;
/*     */     }
/*     */ 
/* 125 */     Integer localInteger = (Integer)this.glyphMap.get(Integer.valueOf(paramInt));
/* 126 */     if (localInteger != null) {
/* 127 */       return localInteger.intValue();
/*     */     }
/* 129 */     return convertToGlyph(paramInt);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.t2k.CompositeGlyphMapper
 * JD-Core Version:    0.6.2
 */