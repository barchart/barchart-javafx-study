/*     */ package com.sun.t2k;
/*     */ 
/*     */ import com.sun.javafx.font.FontResource;
/*     */ import com.sun.javafx.font.FontStrike;
/*     */ import com.sun.javafx.font.PGFont;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ 
/*     */ public class T2KFont
/*     */   implements PGFont
/*     */ {
/*     */   private String name;
/*     */   private float fontSize;
/*     */   protected FontResource fontResource;
/*     */   private int hash;
/*     */ 
/*     */   T2KFont(FontResource paramFontResource, String paramString, float paramFloat)
/*     */   {
/*  20 */     this.fontResource = paramFontResource;
/*  21 */     this.name = paramString;
/*  22 */     this.fontSize = paramFloat;
/*     */   }
/*     */ 
/*     */   public String getFullName() {
/*  26 */     return this.fontResource.getFullName();
/*     */   }
/*     */ 
/*     */   public String getFamilyName() {
/*  30 */     return this.fontResource.getFamilyName();
/*     */   }
/*     */ 
/*     */   public String getStyleName() {
/*  34 */     return this.fontResource.getStyleName();
/*     */   }
/*     */ 
/*     */   public String getLocaleFullName() {
/*  38 */     return this.fontResource.getLocaleFullName();
/*     */   }
/*     */ 
/*     */   public String getLocaleFamilyName() {
/*  42 */     return this.fontResource.getLocaleFamilyName();
/*     */   }
/*     */ 
/*     */   public String getLocaleStyleName() {
/*  46 */     return this.fontResource.getLocaleStyleName();
/*     */   }
/*     */ 
/*     */   public String getName() {
/*  50 */     return this.name;
/*     */   }
/*     */ 
/*     */   public float getSize() {
/*  54 */     return this.fontSize;
/*     */   }
/*     */ 
/*     */   public int getNumGlyphs() {
/*  58 */     return this.fontResource.getNumGlyphs();
/*     */   }
/*     */ 
/*     */   public boolean supportsGlyphImages() {
/*  62 */     return true;
/*     */   }
/*     */ 
/*     */   public CharToGlyphMapper getGlyphMapper() {
/*  66 */     return this.fontResource.getGlyphMapper();
/*     */   }
/*     */ 
/*     */   public FontStrike getStrike(BaseTransform paramBaseTransform, int paramInt)
/*     */   {
/*  71 */     return this.fontResource.getStrike(this, paramBaseTransform, paramInt);
/*     */   }
/*     */ 
/*     */   public FontResource getFontResource() {
/*  75 */     return this.fontResource;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/*  80 */     if (paramObject == null) {
/*  81 */       return false;
/*     */     }
/*  83 */     if (!(paramObject instanceof T2KFont)) {
/*  84 */       return false;
/*     */     }
/*  86 */     T2KFont localT2KFont = (T2KFont)paramObject;
/*     */ 
/*  91 */     return (this.fontSize == localT2KFont.fontSize) && (this.fontResource.equals(localT2KFont.fontResource));
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  99 */     if (this.hash != 0) {
/* 100 */       return this.hash;
/*     */     }
/*     */ 
/* 103 */     this.hash = (497 + Float.floatToIntBits(this.fontSize));
/* 104 */     this.hash = (71 * this.hash + this.fontResource.hashCode());
/* 105 */     return this.hash;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.t2k.T2KFont
 * JD-Core Version:    0.6.2
 */