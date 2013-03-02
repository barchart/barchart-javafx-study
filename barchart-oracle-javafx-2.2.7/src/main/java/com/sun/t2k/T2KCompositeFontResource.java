/*     */ package com.sun.t2k;
/*     */ 
/*     */ import com.sun.javafx.font.FontResource;
/*     */ import com.sun.javafx.font.FontStrike;
/*     */ import com.sun.javafx.font.FontStrikeDesc;
/*     */ import com.sun.javafx.font.PGFont;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ 
/*     */ class T2KCompositeFontResource
/*     */   implements CompositeFontResource
/*     */ {
/*     */   private FontResource primaryResource;
/*     */   private FallbackResource fallbackResource;
/*     */   CompositeGlyphMapper mapper;
/* 141 */   Map<FontStrikeDesc, WeakReference<FontStrike>> strikeMap = new ConcurrentHashMap();
/*     */   private int hash;
/*     */ 
/*     */   T2KCompositeFontResource(FontResource paramFontResource, String paramString)
/*     */   {
/*  30 */     if (!(paramFontResource instanceof T2KFontFile)) {
/*  31 */       Thread.dumpStack();
/*  32 */       throw new IllegalStateException("wrong resource type");
/*     */     }
/*  34 */     if (paramString != null) {
/*  35 */       T2KFontFactory.compResourceMap.put(paramString, this);
/*     */     }
/*  37 */     this.primaryResource = paramFontResource;
/*  38 */     int i = paramFontResource.getDefaultAAMode();
/*  39 */     boolean bool1 = paramFontResource.isBold();
/*  40 */     boolean bool2 = paramFontResource.isItalic();
/*  41 */     this.fallbackResource = FallbackResource.getFallbackResource(bool1, bool2, i);
/*     */   }
/*     */ 
/*     */   public int getNumSlots()
/*     */   {
/*  46 */     return this.fallbackResource.getNumSlots() + 1;
/*     */   }
/*     */ 
/*     */   public FontResource getSlotResource(int paramInt) {
/*  50 */     if (paramInt == 0) {
/*  51 */       return this.primaryResource;
/*     */     }
/*  53 */     FontResource localFontResource = this.fallbackResource.getSlotResource(paramInt - 1);
/*  54 */     if (localFontResource != null) {
/*  55 */       return localFontResource;
/*     */     }
/*  57 */     return this.primaryResource;
/*     */   }
/*     */ 
/*     */   public String getFullName()
/*     */   {
/*  63 */     return this.primaryResource.getFullName();
/*     */   }
/*     */ 
/*     */   public String getPSName() {
/*  67 */     return this.primaryResource.getPSName();
/*     */   }
/*     */ 
/*     */   public String getFamilyName() {
/*  71 */     return this.primaryResource.getFamilyName();
/*     */   }
/*     */ 
/*     */   public String getStyleName() {
/*  75 */     return this.primaryResource.getStyleName();
/*     */   }
/*     */ 
/*     */   public String getLocaleFullName() {
/*  79 */     return this.primaryResource.getLocaleFullName();
/*     */   }
/*     */ 
/*     */   public String getLocaleFamilyName() {
/*  83 */     return this.primaryResource.getLocaleFamilyName();
/*     */   }
/*     */ 
/*     */   public String getLocaleStyleName() {
/*  87 */     return this.primaryResource.getLocaleStyleName();
/*     */   }
/*     */ 
/*     */   public int getNumGlyphs() {
/*  91 */     return this.primaryResource.getNumGlyphs();
/*     */   }
/*     */ 
/*     */   public String getFileName() {
/*  95 */     return this.primaryResource.getFileName();
/*     */   }
/*     */ 
/*     */   public Object getPeer() {
/*  99 */     return this.primaryResource.getPeer();
/*     */   }
/*     */ 
/*     */   public void setPeer(Object paramObject) {
/* 103 */     throw new UnsupportedOperationException("Not supported");
/*     */   }
/*     */ 
/*     */   public boolean isBold() {
/* 107 */     return this.primaryResource.isBold();
/*     */   }
/*     */ 
/*     */   public boolean isItalic() {
/* 111 */     return this.primaryResource.isItalic();
/*     */   }
/*     */ 
/*     */   public boolean supportsGlyphImages() {
/* 115 */     return true;
/*     */   }
/*     */ 
/*     */   public CharToGlyphMapper getGlyphMapper()
/*     */   {
/* 120 */     if (this.mapper == null) {
/* 121 */       this.mapper = new CompositeGlyphMapper(this);
/*     */     }
/* 123 */     return this.mapper;
/*     */   }
/*     */ 
/*     */   public float[] getGlyphBoundingBox(int paramInt, float paramFloat, float[] paramArrayOfFloat)
/*     */   {
/* 128 */     int i = paramInt >>> 24;
/* 129 */     int j = paramInt & 0xFFFFFF;
/* 130 */     FontResource localFontResource = getSlotResource(i);
/* 131 */     return localFontResource.getGlyphBoundingBox(j, paramFloat, paramArrayOfFloat);
/*     */   }
/*     */ 
/*     */   public float getAdvance(int paramInt, float paramFloat) {
/* 135 */     int i = paramInt >>> 24;
/* 136 */     int j = paramInt & 0xFFFFFF;
/* 137 */     FontResource localFontResource = getSlotResource(i);
/* 138 */     return localFontResource.getAdvance(j, paramFloat);
/*     */   }
/*     */ 
/*     */   public Map<FontStrikeDesc, WeakReference<FontStrike>> getStrikeMap()
/*     */   {
/* 145 */     return this.strikeMap;
/*     */   }
/*     */ 
/*     */   public int getDefaultAAMode() {
/* 149 */     return getSlotResource(0).getDefaultAAMode();
/*     */   }
/*     */ 
/*     */   public FontStrike getStrike(PGFont paramPGFont, BaseTransform paramBaseTransform) {
/* 153 */     return getStrike(paramPGFont.getSize(), paramBaseTransform, getDefaultAAMode());
/*     */   }
/*     */ 
/*     */   public FontStrike getStrike(float paramFloat, BaseTransform paramBaseTransform) {
/* 157 */     return getStrike(paramFloat, paramBaseTransform, getDefaultAAMode());
/*     */   }
/*     */ 
/*     */   public FontStrike getStrike(PGFont paramPGFont, BaseTransform paramBaseTransform, int paramInt)
/*     */   {
/* 162 */     return getStrike(paramPGFont.getSize(), paramBaseTransform, paramInt);
/*     */   }
/*     */ 
/*     */   public FontStrike getStrike(float paramFloat, BaseTransform paramBaseTransform, int paramInt)
/*     */   {
/* 167 */     T2KFontStrikeDesc localT2KFontStrikeDesc = new T2KFontStrikeDesc(paramFloat, paramBaseTransform, paramInt);
/*     */ 
/* 169 */     WeakReference localWeakReference = (WeakReference)this.strikeMap.get(localT2KFontStrikeDesc);
/* 170 */     CompositeStrike localCompositeStrike = null;
/* 171 */     if (localWeakReference != null) {
/* 172 */       localCompositeStrike = (CompositeStrike)localWeakReference.get();
/*     */     }
/* 174 */     if (localCompositeStrike == null) {
/* 175 */       localCompositeStrike = new CompositeStrike(this, paramFloat, paramBaseTransform, localT2KFontStrikeDesc);
/* 176 */       if (localCompositeStrike.disposer != null)
/* 177 */         localWeakReference = Disposer.addRecord(localCompositeStrike, localCompositeStrike.disposer);
/*     */       else {
/* 179 */         localWeakReference = new WeakReference(localCompositeStrike);
/*     */       }
/* 181 */       this.strikeMap.put(localT2KFontStrikeDesc, localWeakReference);
/*     */     }
/* 183 */     return localCompositeStrike;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 188 */     if (paramObject == null) {
/* 189 */       return false;
/*     */     }
/* 191 */     if (!(paramObject instanceof T2KCompositeFontResource)) {
/* 192 */       return false;
/*     */     }
/* 194 */     T2KCompositeFontResource localT2KCompositeFontResource = (T2KCompositeFontResource)paramObject;
/*     */ 
/* 196 */     return this.primaryResource.getFullName().equals(localT2KCompositeFontResource.getFullName());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 202 */     if (this.hash != 0) {
/* 203 */       return this.hash;
/*     */     }
/*     */ 
/* 206 */     this.hash = this.primaryResource.getFullName().hashCode();
/* 207 */     return this.hash;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.t2k.T2KCompositeFontResource
 * JD-Core Version:    0.6.2
 */