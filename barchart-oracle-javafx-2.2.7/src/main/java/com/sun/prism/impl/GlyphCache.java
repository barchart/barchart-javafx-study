/*     */ package com.sun.prism.impl;
/*     */ 
/*     */ import com.sun.javafx.font.FontResource;
/*     */ import com.sun.javafx.font.FontStrike;
/*     */ import com.sun.javafx.font.FontStrike.Glyph;
/*     */ import com.sun.javafx.geom.RectBounds;
/*     */ import com.sun.prism.PixelFormat;
/*     */ import com.sun.prism.RTTexture;
/*     */ import com.sun.prism.ResourceFactory;
/*     */ import com.sun.prism.Texture;
/*     */ import com.sun.prism.impl.packrect.BackingStoreManager;
/*     */ import com.sun.prism.impl.packrect.Rect;
/*     */ import com.sun.prism.impl.packrect.RectanglePacker;
/*     */ import com.sun.prism.impl.shape.MaskData;
/*     */ import com.sun.prism.impl.shape.ShapeUtil;
/*     */ import com.sun.prism.paint.Color;
/*     */ import com.sun.t2k.CharToGlyphMapper;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.HashMap;
/*     */ import java.util.WeakHashMap;
/*     */ 
/*     */ public class GlyphCache
/*     */ {
/*     */   private static final int WIDTH = 1024;
/*     */   private static final int HEIGHT = 1024;
/*     */   private static ByteBuffer emptyMask;
/*     */   private final BaseContext context;
/*     */   private final FontStrike strike;
/*     */   private static final int SEGSHIFT = 5;
/*     */   private static final int SEGSIZE = 32;
/*  43 */   HashMap<Integer, GlyphData[]> glyphDataMap = new HashMap();
/*     */   private RectanglePacker packer;
/*     */   private boolean isLCDCache;
/*  52 */   private int numAllocatedGlyphs = 0;
/*     */ 
/*  57 */   static WeakHashMap<BaseContext, RectanglePacker> greyPackerMap = new WeakHashMap();
/*     */ 
/*  60 */   static WeakHashMap<BaseContext, RectanglePacker> lcdPackerMap = new WeakHashMap();
/*     */ 
/*  80 */   private char[] charData = null;
/*  81 */   private int[] glyphData = null;
/*     */ 
/*     */   public GlyphCache(BaseContext paramBaseContext, FontStrike paramFontStrike)
/*     */   {
/*  64 */     this.context = paramBaseContext;
/*  65 */     this.strike = paramFontStrike;
/*     */ 
/*  69 */     this.isLCDCache = (paramFontStrike.getAAMode() == 1);
/*     */ 
/*  71 */     WeakHashMap localWeakHashMap = this.isLCDCache ? lcdPackerMap : greyPackerMap;
/*  72 */     this.packer = ((RectanglePacker)localWeakHashMap.get(paramBaseContext));
/*  73 */     if (this.packer == null) {
/*  74 */       GlyphManager localGlyphManager = new GlyphManager();
/*  75 */       this.packer = new RectanglePacker(localGlyphManager, 1024, 1024);
/*  76 */       localWeakHashMap.put(paramBaseContext, this.packer);
/*     */     }
/*     */   }
/*     */ 
/*     */   int[] getGlyphCodes(String paramString)
/*     */   {
/*  84 */     int i = paramString.length();
/*  85 */     if ((this.glyphData == null) || (this.glyphData.length < i)) {
/*  86 */       this.charData = new char[i];
/*  87 */       this.glyphData = new int[i];
/*     */     }
/*  89 */     paramString.getChars(0, i, this.charData, 0);
/*  90 */     CharToGlyphMapper localCharToGlyphMapper = this.strike.getFontResource().getGlyphMapper();
/*  91 */     localCharToGlyphMapper.charsToGlyphs(i, this.charData, this.glyphData);
/*  92 */     return this.glyphData;
/*     */   }
/*     */ 
/*     */   public boolean isLCDCache() {
/*  96 */     return this.isLCDCache;
/*     */   }
/*     */ 
/*     */   public void render(BaseContext paramBaseContext, String paramString, float paramFloat1, float paramFloat2)
/*     */   {
/*     */     int i;
/*     */     int j;
/* 103 */     if (this.isLCDCache) {
/* 104 */       i = paramBaseContext.getLCDBuffer().getPhysicalWidth();
/* 105 */       j = paramBaseContext.getLCDBuffer().getPhysicalHeight();
/*     */     } else {
/* 107 */       i = 1;
/* 108 */       j = 1;
/*     */     }
/* 110 */     int k = paramString.length();
/* 111 */     float f1 = paramFloat1; float f2 = paramFloat2;
/* 112 */     Texture localTexture = getBackingStore();
/* 113 */     VertexBuffer localVertexBuffer = paramBaseContext.getVertexBuffer();
/* 114 */     int[] arrayOfInt = getGlyphCodes(paramString);
/* 115 */     for (int m = 0; m < k; m++)
/*     */     {
/* 119 */       if (arrayOfInt[m] != 65535)
/*     */       {
/* 122 */         GlyphData localGlyphData = getCachedGlyph(arrayOfInt[m]);
/* 123 */         if (localGlyphData != null) {
/* 124 */           addDataToQuad(localGlyphData, localVertexBuffer, localTexture, f1, f2, i, j);
/*     */ 
/* 126 */           f1 += localGlyphData.getXAdvance();
/* 127 */           f2 += localGlyphData.getYAdvance();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void renderWithColorRange(BaseContext paramBaseContext, String paramString, float paramFloat1, float paramFloat2, int paramInt1, int paramInt2, Color paramColor1, Color paramColor2)
/*     */   {
/*     */     int i;
/*     */     int j;
/* 147 */     if (this.isLCDCache) {
/* 148 */       i = paramBaseContext.getLCDBuffer().getPhysicalWidth();
/* 149 */       j = paramBaseContext.getLCDBuffer().getPhysicalHeight();
/*     */     } else {
/* 151 */       i = 1;
/* 152 */       j = 1;
/*     */     }
/* 154 */     int k = paramString.length();
/* 155 */     float f1 = paramFloat1; float f2 = paramFloat2;
/* 156 */     Texture localTexture = getBackingStore();
/* 157 */     VertexBuffer localVertexBuffer = paramBaseContext.getVertexBuffer();
/* 158 */     int[] arrayOfInt = getGlyphCodes(paramString);
/* 159 */     if ((paramInt1 < 0) && (0 < paramInt2)) {
/* 160 */       localVertexBuffer.setPerVertexColor(paramColor1, 1.0F);
/*     */     }
/* 162 */     for (int m = 0; m < k; m++)
/*     */     {
/* 164 */       if (paramInt1 == m)
/* 165 */         localVertexBuffer.setPerVertexColor(paramColor1, 1.0F);
/* 166 */       else if ((paramColor2 != null) && (m == paramInt2)) {
/* 167 */         localVertexBuffer.setPerVertexColor(paramColor2, 1.0F);
/*     */       }
/*     */ 
/* 173 */       if (arrayOfInt[m] != 65535)
/*     */       {
/* 176 */         GlyphData localGlyphData = getCachedGlyph(arrayOfInt[m]);
/* 177 */         if (localGlyphData != null) {
/* 178 */           addDataToQuad(localGlyphData, localVertexBuffer, localTexture, f1, f2, i, j);
/*     */ 
/* 180 */           f1 += localGlyphData.getXAdvance();
/* 181 */           f2 += localGlyphData.getYAdvance();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void addDataToQuad(GlyphData paramGlyphData, VertexBuffer paramVertexBuffer, Texture paramTexture, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
/*     */   {
/* 195 */     paramFloat2 = Math.round(paramFloat2);
/* 196 */     Rect localRect = paramGlyphData.getRect();
/* 197 */     if (localRect == null)
/*     */     {
/* 199 */       return;
/*     */     }
/* 201 */     int i = paramGlyphData.getBlankBoundary();
/* 202 */     float f1 = localRect.w() - i * 2;
/* 203 */     float f2 = localRect.h() - i * 2;
/* 204 */     float f3 = paramGlyphData.getOriginX() + paramFloat1;
/* 205 */     float f4 = paramGlyphData.getOriginY() + paramFloat2;
/*     */ 
/* 207 */     float f6 = f4 + f2;
/* 208 */     float f7 = paramTexture.getPhysicalWidth();
/* 209 */     float f8 = paramTexture.getPhysicalHeight();
/* 210 */     float f9 = (localRect.x() + i) / f7;
/* 211 */     float f10 = (localRect.y() + i) / f8;
/* 212 */     float f11 = f9 + f1 / f7;
/* 213 */     float f12 = f10 + f2 / f8;
/*     */     float f5;
/* 214 */     if (this.isLCDCache) {
/* 215 */       f3 = Math.round(f3 * 3.0F) / 3.0F;
/* 216 */       f5 = f3 + f1 / 3.0F;
/* 217 */       float f13 = f3 / paramFloat3;
/* 218 */       float f14 = f5 / paramFloat3;
/* 219 */       float f15 = f4 / paramFloat4;
/* 220 */       float f16 = f6 / paramFloat4;
/* 221 */       paramVertexBuffer.addQuad(f3, f4, f5, f6, f9, f10, f11, f12, f13, f15, f14, f16);
/*     */     } else {
/* 223 */       f3 = Math.round(f3);
/* 224 */       f5 = f3 + f1;
/* 225 */       paramVertexBuffer.addQuad(f3, f4, f5, f6, f9, f10, f11, f12);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Texture getBackingStore() {
/* 230 */     return (Texture)this.packer.getBackingStore();
/*     */   }
/*     */ 
/*     */   public void clear() {
/* 234 */     this.glyphDataMap.clear();
/* 235 */     this.numAllocatedGlyphs = 0;
/*     */   }
/*     */ 
/*     */   public int getNumAllocatedGlyphs() {
/* 239 */     return this.numAllocatedGlyphs;
/*     */   }
/*     */ 
/*     */   private void clearAll()
/*     */   {
/* 245 */     this.context.flushVertexBuffer();
/* 246 */     this.context.clearGlyphCaches();
/* 247 */     this.packer.clear();
/*     */   }
/*     */ 
/*     */   private boolean isEmptyGlyph(FontStrike.Glyph paramGlyph) {
/* 251 */     if (!this.strike.supportsGlyphImages()) {
/* 252 */       return paramGlyph.getBBox().isEmpty();
/*     */     }
/* 254 */     return (paramGlyph.getWidth() == 0) || (paramGlyph.getHeight() == 0);
/*     */   }
/*     */ 
/*     */   private GlyphData getCachedGlyph(int paramInt)
/*     */   {
/* 259 */     int i = paramInt >> 5;
/* 260 */     int j = paramInt % 32;
/* 261 */     GlyphData[] arrayOfGlyphData = (GlyphData[])this.glyphDataMap.get(Integer.valueOf(i));
/* 262 */     if (arrayOfGlyphData != null) {
/* 263 */       if (arrayOfGlyphData[j] != null)
/* 264 */         return arrayOfGlyphData[j];
/*     */     }
/*     */     else {
/* 267 */       arrayOfGlyphData = new GlyphData[32];
/* 268 */       this.glyphDataMap.put(Integer.valueOf(i), arrayOfGlyphData);
/*     */     }
/*     */ 
/* 272 */     GlyphData localGlyphData = null;
/* 273 */     FontStrike.Glyph localGlyph = this.strike.getGlyph(paramInt);
/* 274 */     if (localGlyph != null) {
/* 275 */       if (isEmptyGlyph(localGlyph)) {
/* 276 */         localGlyphData = new GlyphData(0, 0, 0, localGlyph.getPixelXAdvance(), localGlyph.getPixelYAdvance(), null);
/*     */       }
/*     */       else
/*     */       {
/*     */         MaskData localMaskData;
/* 287 */         if (this.strike.supportsGlyphImages()) {
/* 288 */           localMaskData = MaskData.create(localGlyph.getPixelData(), localGlyph.getOriginX(), localGlyph.getOriginY(), localGlyph.getWidth(), localGlyph.getHeight());
/*     */         }
/*     */         else
/*     */         {
/* 294 */           localMaskData = ShapeUtil.rasterizeGlyphOutline(localGlyph.getShape());
/*     */         }
/*     */ 
/* 298 */         int k = 1;
/* 299 */         int m = localMaskData.getWidth() + 2 * k;
/* 300 */         int n = localMaskData.getHeight() + 2 * k;
/* 301 */         int i1 = localMaskData.getOriginX();
/* 302 */         int i2 = localMaskData.getOriginY();
/* 303 */         Rect localRect = new Rect(0, 0, m, n);
/* 304 */         localGlyphData = new GlyphData(i1, i2, k, localGlyph.getPixelXAdvance(), localGlyph.getPixelYAdvance(), localRect);
/*     */ 
/* 309 */         if (!this.packer.add(localRect))
/*     */         {
/* 311 */           clearAll();
/* 312 */           this.packer.add(localRect);
/*     */         }
/*     */ 
/* 319 */         boolean bool = true;
/*     */ 
/* 324 */         Texture localTexture = getBackingStore();
/* 325 */         int i3 = localRect.w();
/* 326 */         int i4 = localRect.h();
/* 327 */         int i5 = localTexture.getPixelFormat().getBytesPerPixelUnit();
/* 328 */         int i6 = i3 * i5;
/* 329 */         int i7 = i6 * i4;
/* 330 */         if ((emptyMask == null) || (i7 > emptyMask.capacity())) {
/* 331 */           emptyMask = BufferUtil.newByteBuffer(i7);
/*     */         }
/*     */         try
/*     */         {
/* 335 */           localTexture.update(emptyMask, localTexture.getPixelFormat(), localRect.x(), localRect.y(), 0, 0, i3, i4, i6, bool);
/*     */         }
/*     */         catch (Exception localException)
/*     */         {
/* 341 */           localException.printStackTrace();
/* 342 */           return null;
/*     */         }
/*     */ 
/* 345 */         localMaskData.uploadToTexture(localTexture, k + localRect.x(), k + localRect.y(), bool);
/*     */       }
/*     */ 
/* 351 */       arrayOfGlyphData[j] = localGlyphData;
/* 352 */       this.numAllocatedGlyphs += 1;
/*     */     }
/*     */ 
/* 355 */     return localGlyphData;
/*     */   }
/*     */ 
/*     */   class GlyphManager
/*     */     implements BackingStoreManager
/*     */   {
/*     */     GlyphManager()
/*     */     {
/*     */     }
/*     */ 
/*     */     public Texture allocateBackingStore(int paramInt1, int paramInt2)
/*     */     {
/* 416 */       Texture localTexture = GlyphCache.this.context.getResourceFactory().createMaskTexture(paramInt1, paramInt2);
/* 417 */       localTexture.setLinearFiltering(false);
/* 418 */       return localTexture;
/*     */     }
/*     */ 
/*     */     public void deleteBackingStore(Object paramObject) {
/* 422 */       if (paramObject != null)
/* 423 */         ((Texture)paramObject).dispose();
/*     */     }
/*     */   }
/*     */ 
/*     */   static class GlyphData
/*     */   {
/*     */     private final int originX;
/*     */     private final int originY;
/*     */     private final int blankBoundary;
/*     */     private final float xAdvance;
/*     */     private final float yAdvance;
/*     */     private final Rect rect;
/*     */ 
/*     */     GlyphData(int paramInt1, int paramInt2, int paramInt3, float paramFloat1, float paramFloat2, Rect paramRect)
/*     */     {
/* 380 */       this.originX = paramInt1;
/* 381 */       this.originY = paramInt2;
/* 382 */       this.blankBoundary = paramInt3;
/* 383 */       this.xAdvance = paramFloat1;
/* 384 */       this.yAdvance = paramFloat2;
/* 385 */       this.rect = paramRect;
/*     */     }
/*     */ 
/*     */     int getOriginX() {
/* 389 */       return this.originX;
/*     */     }
/*     */ 
/*     */     int getOriginY() {
/* 393 */       return this.originY;
/*     */     }
/*     */ 
/*     */     int getBlankBoundary() {
/* 397 */       return this.blankBoundary;
/*     */     }
/*     */ 
/*     */     float getXAdvance() {
/* 401 */       return this.xAdvance;
/*     */     }
/*     */ 
/*     */     float getYAdvance() {
/* 405 */       return this.yAdvance;
/*     */     }
/*     */ 
/*     */     Rect getRect() {
/* 409 */       return this.rect;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.impl.GlyphCache
 * JD-Core Version:    0.6.2
 */