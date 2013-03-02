/*     */ package com.sun.t2k;
/*     */ 
/*     */ import com.sun.javafx.font.FontResource;
/*     */ import com.sun.javafx.font.FontStrike;
/*     */ import com.sun.javafx.font.FontStrike.Glyph;
/*     */ import com.sun.javafx.font.FontStrike.Metrics;
/*     */ import com.sun.javafx.font.PrismFontLoader;
/*     */ import com.sun.javafx.geom.Path2D;
/*     */ import com.sun.javafx.geom.RectBounds;
/*     */ import com.sun.javafx.geom.Shape;
/*     */ import com.sun.javafx.geom.transform.Affine2D;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class CompositeStrike
/*     */   implements FontStrike
/*     */ {
/*     */   private CompositeFontResource fontResource;
/*     */   private float size;
/*     */   private int aaMode;
/*     */   BaseTransform transform;
/*     */   private FontStrike slot0Strike;
/*     */   private FontStrike[] strikeSlots;
/*     */   private T2KFontStrikeDesc desc;
/*     */   T2KStrikeDisposer disposer;
/*     */   private T2KMetrics metrics;
/*     */ 
/*     */   public void clearDesc()
/*     */   {
/*  30 */     this.fontResource.getStrikeMap().remove(this.desc);
/*     */ 
/*  36 */     if (this.slot0Strike != null) {
/*  37 */       this.slot0Strike.clearDesc();
/*     */     }
/*  39 */     if (this.strikeSlots != null)
/*  40 */       for (int i = 1; i < this.strikeSlots.length; i++)
/*  41 */         if (this.strikeSlots[i] != null)
/*  42 */           this.strikeSlots[i].clearDesc();
/*     */   }
/*     */ 
/*     */   CompositeStrike(CompositeFontResource paramCompositeFontResource, float paramFloat, BaseTransform paramBaseTransform, T2KFontStrikeDesc paramT2KFontStrikeDesc)
/*     */   {
/*  52 */     this.fontResource = paramCompositeFontResource;
/*  53 */     this.size = paramFloat;
/*  54 */     if (paramBaseTransform.isTranslateOrIdentity())
/*  55 */       this.transform = BaseTransform.IDENTITY_TRANSFORM;
/*     */     else {
/*  57 */       this.transform = paramBaseTransform.copy();
/*     */     }
/*  59 */     this.desc = paramT2KFontStrikeDesc;
/*  60 */     this.aaMode = paramT2KFontStrikeDesc.aaMode;
/*     */ 
/*  64 */     this.disposer = new T2KStrikeDisposer(paramCompositeFontResource, paramT2KFontStrikeDesc, 0L);
/*     */   }
/*     */ 
/*     */   public int getAAMode() {
/*  68 */     if (PrismFontLoader.isLCDTextSupported()) {
/*  69 */       return this.aaMode;
/*     */     }
/*  71 */     return 0;
/*     */   }
/*     */ 
/*     */   public BaseTransform getTransform()
/*     */   {
/*  80 */     return this.transform;
/*     */   }
/*     */ 
/*     */   public FontStrike getStrikeSlot(int paramInt)
/*     */   {
/*     */     FontResource localFontResource;
/*  84 */     if (paramInt == 0) {
/*  85 */       if (this.slot0Strike == null) {
/*  86 */         localFontResource = this.fontResource.getSlotResource(0);
/*  87 */         this.slot0Strike = localFontResource.getStrike(this.size, this.transform, getAAMode());
/*     */       }
/*     */ 
/*  90 */       return this.slot0Strike;
/*     */     }
/*  92 */     if (this.strikeSlots == null) {
/*  93 */       this.strikeSlots = new FontStrike[this.fontResource.getNumSlots()];
/*     */     }
/*  95 */     if (this.strikeSlots[paramInt] == null) {
/*  96 */       localFontResource = this.fontResource.getSlotResource(paramInt);
/*  97 */       this.strikeSlots[paramInt] = localFontResource.getStrike(this.size, this.transform, getAAMode());
/*     */     }
/*     */ 
/* 100 */     return this.strikeSlots[paramInt];
/*     */   }
/*     */ 
/*     */   public FontResource getFontResource()
/*     */   {
/* 105 */     return this.fontResource;
/*     */   }
/*     */ 
/*     */   public int getStrikeSlotForGlyph(int paramInt) {
/* 109 */     return paramInt >>> 24;
/*     */   }
/*     */ 
/*     */   public float getSize() {
/* 113 */     return this.size;
/*     */   }
/*     */ 
/*     */   public boolean drawAsShapes() {
/* 117 */     return getStrikeSlot(0).drawAsShapes();
/*     */   }
/*     */ 
/*     */   public int getNumGlyphs() {
/* 121 */     return getStrikeSlot(0).getNumGlyphs();
/*     */   }
/*     */ 
/*     */   public boolean supportsGlyphImages() {
/* 125 */     return getStrikeSlot(0).supportsGlyphImages();
/*     */   }
/*     */ 
/*     */   public FontStrike.Metrics getMetrics()
/*     */   {
/* 131 */     if (this.metrics == null) {
/* 132 */       T2KMetrics localT2KMetrics1 = (T2KMetrics)getStrikeSlot(0).getMetrics();
/*     */ 
/* 135 */       if (localT2KMetrics1.getXHeight() == 0.0F)
/*     */       {
/* 137 */         float f = 0.0F;
/* 138 */         for (int i = 1; i < this.fontResource.getNumSlots(); i++) {
/* 139 */           T2KMetrics localT2KMetrics2 = (T2KMetrics)getStrikeSlot(i).getMetrics();
/* 140 */           f = localT2KMetrics2.getXHeight();
/* 141 */           if (f != 0.0F) {
/*     */             break;
/*     */           }
/*     */         }
/* 145 */         this.metrics = new T2KMetrics(localT2KMetrics1.getAscent(), localT2KMetrics1.getDescent(), localT2KMetrics1.getLineGap(), f);
/*     */       }
/*     */       else
/*     */       {
/* 150 */         this.metrics = localT2KMetrics1;
/*     */       }
/*     */     }
/* 153 */     return this.metrics;
/*     */   }
/*     */ 
/*     */   public FontStrike.Glyph getGlyph(char paramChar) {
/* 157 */     CharToGlyphMapper localCharToGlyphMapper = this.fontResource.getGlyphMapper();
/* 158 */     int i = localCharToGlyphMapper.charToGlyph(paramChar);
/* 159 */     int j = i >>> 24;
/* 160 */     int k = i & 0xFFFFFF;
/* 161 */     return getStrikeSlot(j).getGlyph(k);
/*     */   }
/*     */ 
/*     */   public FontStrike.Glyph getGlyph(int paramInt) {
/* 165 */     int i = paramInt >>> 24;
/* 166 */     int j = paramInt & 0xFFFFFF;
/* 167 */     return getStrikeSlot(i).getGlyph(j);
/*     */   }
/*     */ 
/*     */   public float getCharAdvance(char paramChar)
/*     */   {
/* 178 */     int i = this.fontResource.getGlyphMapper().charToGlyph(paramChar);
/* 179 */     return this.fontResource.getAdvance(i, this.size);
/*     */   }
/*     */ 
/*     */   public RectBounds getStringVisualBounds(String paramString) {
/* 183 */     if ((paramString == null) || (paramString.length() == 0)) {
/* 184 */       return new RectBounds();
/*     */     }
/* 186 */     float[] arrayOfFloat = new float[4];
/* 187 */     float f1 = 0.0F; float f2 = 0.0F; float f3 = 0.0F; float f4 = 0.0F;
/* 188 */     CharToGlyphMapper localCharToGlyphMapper = this.fontResource.getGlyphMapper();
/*     */ 
/* 190 */     int i = localCharToGlyphMapper.charToGlyph(paramString.charAt(0));
/* 191 */     this.fontResource.getGlyphBoundingBox(i, this.size, arrayOfFloat);
/* 192 */     f1 = arrayOfFloat[0];
/* 193 */     f2 = arrayOfFloat[1];
/* 194 */     f3 = arrayOfFloat[2];
/* 195 */     f4 = arrayOfFloat[3];
/*     */ 
/* 197 */     int j = paramString.length();
/* 198 */     if (j == 1) {
/* 199 */       return new RectBounds(f1, f2, f3, f4);
/*     */     }
/* 201 */     float f5 = this.fontResource.getAdvance(i, this.size);
/* 202 */     for (int k = 1; k < j; k++) {
/* 203 */       i = localCharToGlyphMapper.charToGlyph(paramString.charAt(k));
/* 204 */       this.fontResource.getGlyphBoundingBox(i, this.size, arrayOfFloat);
/*     */ 
/* 209 */       if (arrayOfFloat[0] + f5 < f1) {
/* 210 */         f1 = arrayOfFloat[0] + f5;
/*     */       }
/* 212 */       if (arrayOfFloat[1] < f2) {
/* 213 */         f2 = arrayOfFloat[1];
/*     */       }
/*     */ 
/* 216 */       if (arrayOfFloat[2] + f5 > f3) {
/* 217 */         f3 = arrayOfFloat[2] + f5;
/*     */       }
/* 219 */       if (arrayOfFloat[3] > f4) {
/* 220 */         f4 = arrayOfFloat[3];
/*     */       }
/* 222 */       if (k < j - 1) {
/* 223 */         f5 += this.fontResource.getAdvance(i, this.size);
/*     */       }
/*     */     }
/* 226 */     return new RectBounds(f1, f2, f3, f4);
/*     */   }
/*     */ 
/*     */   public float getStringWidth(String paramString)
/*     */   {
/* 232 */     float f = 0.0F;
/* 233 */     for (int i = 0; i < paramString.length(); i++) {
/* 234 */       f += getCharAdvance(paramString.charAt(i));
/*     */     }
/* 236 */     return f;
/*     */   }
/*     */ 
/*     */   public float getStringHeight(String paramString) {
/* 240 */     return getStrikeSlot(0).getStringHeight(paramString);
/*     */   }
/*     */ 
/*     */   public RectBounds getStringBounds(String paramString)
/*     */   {
/* 253 */     if (paramString == null) {
/* 254 */       return new RectBounds();
/*     */     }
/* 256 */     FontStrike.Metrics localMetrics = getMetrics();
/* 257 */     float f = 0.0F;
/* 258 */     for (int i = 0; i < paramString.length(); i++) {
/* 259 */       char c = paramString.charAt(i);
/* 260 */       f += getCharAdvance(c);
/*     */     }
/* 262 */     RectBounds localRectBounds = new RectBounds(0.0F, localMetrics.getAscent(), f, localMetrics.getDescent() + localMetrics.getLineGap());
/*     */ 
/* 264 */     return localRectBounds;
/*     */   }
/*     */ 
/*     */   public Shape getOutline(String paramString, BaseTransform paramBaseTransform) {
/* 268 */     Path2D localPath2D = new Path2D();
/* 269 */     getOutline(paramString, paramBaseTransform, localPath2D);
/* 270 */     return localPath2D;
/*     */   }
/*     */ 
/*     */   public void getOutline(String paramString, BaseTransform paramBaseTransform, Path2D paramPath2D)
/*     */   {
/* 276 */     paramPath2D.reset();
/* 277 */     if (paramString == null) {
/* 278 */       return;
/*     */     }
/* 280 */     float f1 = 0.0F;
/* 281 */     float f2 = getMetrics().getLineHeight();
/* 282 */     if (paramBaseTransform == null) {
/* 283 */       paramBaseTransform = BaseTransform.IDENTITY_TRANSFORM;
/*     */     }
/* 285 */     Affine2D localAffine2D = new Affine2D();
/* 286 */     float f3 = 0.0F;
/* 287 */     for (int i = 0; i < paramString.length(); i++) {
/* 288 */       localAffine2D.setTransform(paramBaseTransform);
/* 289 */       char c = paramString.charAt(i);
/* 290 */       if (c == '\n') {
/* 291 */         f3 -= f2;
/* 292 */         f1 = 0.0F;
/*     */       }
/*     */       else {
/* 295 */         FontStrike.Glyph localGlyph = getGlyph(c);
/* 296 */         Shape localShape = localGlyph.getShape();
/* 297 */         localAffine2D.translate(f1, f3);
/* 298 */         paramPath2D.append(localShape.getPathIterator(localAffine2D), false);
/* 299 */         f1 += localGlyph.getAdvance();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/* 304 */   public float getStrikeThroughOffset() { return getStrikeSlot(0).getStrikeThroughOffset(); }
/*     */ 
/*     */   public float getStrikeThroughThickness() {
/* 307 */     return getStrikeSlot(0).getStrikeThroughThickness();
/*     */   }
/*     */ 
/*     */   public float getUnderLineOffset()
/*     */   {
/* 312 */     return getStrikeSlot(0).getUnderLineOffset();
/*     */   }
/*     */ 
/*     */   public float getUnderLineThickness() {
/* 316 */     return getStrikeSlot(0).getUnderLineThickness();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.t2k.CompositeStrike
 * JD-Core Version:    0.6.2
 */