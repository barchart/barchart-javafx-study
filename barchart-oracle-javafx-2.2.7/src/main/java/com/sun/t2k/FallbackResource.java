/*     */ package com.sun.t2k;
/*     */ 
/*     */ import com.sun.javafx.font.FontConfigManager;
/*     */ import com.sun.javafx.font.FontConfigManager.FcCompFont;
/*     */ import com.sun.javafx.font.FontResource;
/*     */ import com.sun.javafx.font.FontStrike;
/*     */ import com.sun.javafx.font.FontStrikeDesc;
/*     */ import com.sun.javafx.font.PGFont;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ 
/*     */ class FallbackResource
/*     */   implements CompositeFontResource
/*     */ {
/*     */   private ArrayList<String> linkedFontFiles;
/*     */   private ArrayList<String> linkedFontNames;
/*     */   private FontResource[] fallbacks;
/*     */   private boolean isBold;
/*     */   private boolean isItalic;
/*     */   private int aaMode;
/*     */   private CompositeGlyphMapper mapper;
/*  36 */   Map<FontStrikeDesc, WeakReference<FontStrike>> strikeMap = new ConcurrentHashMap();
/*     */ 
/*  50 */   static FallbackResource[] greyFallBackResource = new FallbackResource[4];
/*  51 */   static FallbackResource[] lcdFallBackResource = new FallbackResource[4];
/*     */ 
/*     */   public Map<FontStrikeDesc, WeakReference<FontStrike>> getStrikeMap()
/*     */   {
/*  41 */     return this.strikeMap;
/*     */   }
/*     */ 
/*     */   FallbackResource(boolean paramBoolean1, boolean paramBoolean2, int paramInt) {
/*  45 */     this.isBold = paramBoolean1;
/*  46 */     this.isItalic = paramBoolean2;
/*  47 */     this.aaMode = paramInt;
/*     */   }
/*     */ 
/*     */   static FallbackResource getFallbackResource(boolean paramBoolean1, boolean paramBoolean2, int paramInt)
/*     */   {
/*  55 */     FallbackResource[] arrayOfFallbackResource = paramInt == 0 ? greyFallBackResource : lcdFallBackResource;
/*     */ 
/*  58 */     int i = paramBoolean1 ? 1 : 0;
/*  59 */     if (paramBoolean2) {
/*  60 */       i += 2;
/*     */     }
/*  62 */     FallbackResource localFallbackResource = arrayOfFallbackResource[i];
/*  63 */     if (localFallbackResource == null) {
/*  64 */       localFallbackResource = new FallbackResource(paramBoolean1, paramBoolean2, paramInt);
/*  65 */       arrayOfFallbackResource[i] = localFallbackResource;
/*     */     }
/*  67 */     return localFallbackResource;
/*     */   }
/*     */ 
/*     */   public int getDefaultAAMode() {
/*  71 */     return this.aaMode;
/*     */   }
/*     */ 
/*     */   private String throwException()
/*     */   {
/*  80 */     throw new UnsupportedOperationException("Not supported");
/*     */   }
/*     */ 
/*     */   public String getFullName() {
/*  84 */     return throwException();
/*     */   }
/*     */ 
/*     */   public String getPSName() {
/*  88 */     return throwException();
/*     */   }
/*     */ 
/*     */   public String getFamilyName() {
/*  92 */     return throwException();
/*     */   }
/*     */ 
/*     */   public String getStyleName() {
/*  96 */     return throwException();
/*     */   }
/*     */ 
/*     */   public String getLocaleFullName() {
/* 100 */     return throwException();
/*     */   }
/*     */ 
/*     */   public String getLocaleFamilyName() {
/* 104 */     return throwException();
/*     */   }
/*     */ 
/*     */   public String getLocaleStyleName() {
/* 108 */     return throwException();
/*     */   }
/*     */ 
/*     */   public boolean isBold()
/*     */   {
/* 113 */     throw new UnsupportedOperationException("Not supported");
/*     */   }
/*     */ 
/*     */   public boolean isItalic() {
/* 117 */     throw new UnsupportedOperationException("Not supported");
/*     */   }
/*     */ 
/*     */   public int getNumGlyphs()
/*     */   {
/* 122 */     throw new UnsupportedOperationException("Not supported");
/*     */   }
/*     */ 
/*     */   public String getFileName() {
/* 126 */     return throwException();
/*     */   }
/*     */ 
/*     */   public Object getPeer() {
/* 130 */     return null;
/*     */   }
/*     */ 
/*     */   public void setPeer(Object paramObject) {
/* 134 */     throwException();
/*     */   }
/*     */ 
/*     */   public CharToGlyphMapper getGlyphMapper() {
/* 138 */     if (this.mapper == null) {
/* 139 */       this.mapper = new CompositeGlyphMapper(this);
/*     */     }
/* 141 */     return this.mapper;
/*     */   }
/*     */ 
/*     */   private void getLinkedFonts()
/*     */   {
/* 148 */     if (this.fallbacks == null)
/*     */     {
/*     */       Object localObject;
/* 149 */       if (T2KFontFactory.isLinux) {
/* 150 */         localObject = FontConfigManager.getFontConfigFont("sans", this.isBold, this.isItalic);
/*     */ 
/* 153 */         this.linkedFontFiles = FontConfigManager.getFileNames((FontConfigManager.FcCompFont)localObject, false);
/* 154 */         this.linkedFontNames = FontConfigManager.getFontNames((FontConfigManager.FcCompFont)localObject, false);
/* 155 */         this.fallbacks = new FontResource[this.linkedFontFiles.size()];
/*     */       }
/*     */       else {
/* 158 */         if (T2KFontFactory.isMacOSX) {
/* 159 */           localObject = T2KFontFactory.getLinkedFonts("Arial Unicode MS", true);
/*     */         }
/*     */         else {
/* 162 */           localObject = T2KFontFactory.getLinkedFonts("Tahoma", true);
/*     */         }
/*     */ 
/* 165 */         this.linkedFontFiles = localObject[0];
/* 166 */         this.linkedFontNames = localObject[1];
/* 167 */         this.fallbacks = new FontResource[this.linkedFontFiles.size()];
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getNumSlots() {
/* 173 */     getLinkedFonts();
/* 174 */     return this.linkedFontFiles.size();
/*     */   }
/*     */ 
/*     */   public float[] getGlyphBoundingBox(int paramInt, float paramFloat, float[] paramArrayOfFloat)
/*     */   {
/* 179 */     int i = paramInt >>> 24;
/* 180 */     int j = paramInt & 0xFFFFFF;
/* 181 */     FontResource localFontResource = getSlotResource(i);
/* 182 */     return localFontResource.getGlyphBoundingBox(j, paramFloat, paramArrayOfFloat);
/*     */   }
/*     */ 
/*     */   public float getAdvance(int paramInt, float paramFloat) {
/* 186 */     int i = paramInt >>> 24;
/* 187 */     int j = paramInt & 0xFFFFFF;
/* 188 */     FontResource localFontResource = getSlotResource(i);
/* 189 */     return localFontResource.getAdvance(j, paramFloat);
/*     */   }
/*     */ 
/*     */   public synchronized FontResource getSlotResource(int paramInt) {
/* 193 */     getLinkedFonts();
/* 194 */     if (this.fallbacks[paramInt] == null) {
/* 195 */       String str1 = (String)this.linkedFontFiles.get(paramInt);
/* 196 */       String str2 = (String)this.linkedFontNames.get(paramInt);
/* 197 */       this.fallbacks[paramInt] = T2KFontFactory.getFontFactory().getFontResource(str2, str1, false);
/*     */     }
/*     */ 
/* 201 */     return this.fallbacks[paramInt];
/*     */   }
/*     */ 
/*     */   public FontStrike getStrike(PGFont paramPGFont, BaseTransform paramBaseTransform, int paramInt)
/*     */   {
/* 206 */     return getStrike(paramPGFont.getSize(), paramBaseTransform, getDefaultAAMode());
/*     */   }
/*     */ 
/*     */   public FontStrike getStrike(float paramFloat, BaseTransform paramBaseTransform) {
/* 210 */     return getStrike(paramFloat, paramBaseTransform, getDefaultAAMode());
/*     */   }
/*     */ 
/*     */   public FontStrike getStrike(PGFont paramPGFont, BaseTransform paramBaseTransform) {
/* 214 */     return getStrike(paramPGFont.getSize(), paramBaseTransform);
/*     */   }
/*     */ 
/*     */   public FontStrike getStrike(float paramFloat, BaseTransform paramBaseTransform, int paramInt)
/*     */   {
/* 220 */     T2KFontStrikeDesc localT2KFontStrikeDesc = new T2KFontStrikeDesc(paramFloat, paramBaseTransform, paramInt);
/*     */ 
/* 222 */     WeakReference localWeakReference = (WeakReference)this.strikeMap.get(localT2KFontStrikeDesc);
/* 223 */     CompositeStrike localCompositeStrike = null;
/*     */ 
/* 225 */     if (localWeakReference != null) {
/* 226 */       localCompositeStrike = (CompositeStrike)localWeakReference.get();
/*     */     }
/* 228 */     if (localCompositeStrike == null) {
/* 229 */       localCompositeStrike = new CompositeStrike(this, paramFloat, paramBaseTransform, localT2KFontStrikeDesc);
/* 230 */       if (localCompositeStrike.disposer != null)
/* 231 */         localWeakReference = Disposer.addRecord(localCompositeStrike, localCompositeStrike.disposer);
/*     */       else {
/* 233 */         localWeakReference = new WeakReference(localCompositeStrike);
/*     */       }
/* 235 */       this.strikeMap.put(localT2KFontStrikeDesc, localWeakReference);
/*     */     }
/* 237 */     return localCompositeStrike;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.t2k.FallbackResource
 * JD-Core Version:    0.6.2
 */