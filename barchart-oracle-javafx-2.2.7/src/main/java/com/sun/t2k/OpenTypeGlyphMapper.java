/*    */ package com.sun.t2k;
/*    */ 
/*    */ public class OpenTypeGlyphMapper extends CharToGlyphMapper
/*    */ {
/*    */   T2KFontFile font;
/*    */   CMap cmap;
/*    */   int numGlyphs;
/*    */ 
/*    */   public OpenTypeGlyphMapper(T2KFontFile paramT2KFontFile)
/*    */   {
/* 13 */     this.font = paramT2KFontFile;
/*    */     try {
/* 15 */       this.cmap = CMap.initialize(paramT2KFontFile);
/*    */     } catch (Exception localException) {
/* 17 */       this.cmap = null;
/*    */     }
/* 19 */     if (this.cmap == null) {
/* 20 */       handleBadCMAP();
/*    */     }
/* 22 */     this.missingGlyph = 0;
/* 23 */     this.numGlyphs = paramT2KFontFile.getNumGlyphs();
/*    */   }
/*    */ 
/*    */   public int getNumGlyphs() {
/* 27 */     return this.numGlyphs;
/*    */   }
/*    */ 
/*    */   public int getGlyphCode(int paramInt) {
/*    */     try {
/* 32 */       return this.cmap.getGlyph(paramInt);
/*    */     } catch (Exception localException) {
/* 34 */       handleBadCMAP();
/* 35 */     }return this.missingGlyph;
/*    */   }
/*    */ 
/*    */   private void handleBadCMAP()
/*    */   {
/* 41 */     this.cmap = CMap.theNullCmap;
/*    */   }
/*    */ 
/*    */   boolean hasSupplementaryChars()
/*    */   {
/* 48 */     return ((this.cmap instanceof CMap.CMapFormat8)) || ((this.cmap instanceof CMap.CMapFormat10)) || ((this.cmap instanceof CMap.CMapFormat12));
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.t2k.OpenTypeGlyphMapper
 * JD-Core Version:    0.6.2
 */