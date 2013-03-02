/*     */ package com.sun.t2k;
/*     */ 
/*     */ import com.sun.javafx.font.FontResource;
/*     */ import com.sun.javafx.font.FontStrike;
/*     */ import com.sun.javafx.font.FontStrike.Glyph;
/*     */ import com.sun.javafx.font.FontStrike.Metrics;
/*     */ import com.sun.javafx.font.PrismFontLoader;
/*     */ import com.sun.javafx.geom.Path2D;
/*     */ import com.sun.javafx.geom.Point2D;
/*     */ import com.sun.javafx.geom.RectBounds;
/*     */ import com.sun.javafx.geom.Shape;
/*     */ import com.sun.javafx.geom.transform.Affine2D;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.javafx.geom.transform.NoninvertibleTransformException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class T2KFontStrike
/*     */   implements FontStrike
/*     */ {
/*  23 */   private long pScalerContext = 0L;
/*     */   private T2KFontFile fontResource;
/*     */   T2KStrikeDisposer disposer;
/*  28 */   Map<Integer, FontStrike.Glyph> glyphMap = new HashMap();
/*     */   float size;
/*     */   private BaseTransform transform;
/*     */   public double[] matrix;
/*     */   T2KMetrics metrics;
/*  34 */   boolean drawShapes = false;
/*  35 */   Affine2D invTx = null;
/*     */   int aaMode;
/*  37 */   boolean gdiLCDGlyphs = false;
/*     */   int gdiSize;
/*     */   private T2KFontStrikeDesc desc;
/*     */   private float[] styleMetrics;
/*     */   private int hash;
/*     */ 
/*     */   public synchronized void clearDesc()
/*     */   {
/*  43 */     this.fontResource.strikeMap.remove(this.desc);
/*     */   }
/*     */ 
/*     */   T2KFontStrike(T2KFontFile paramT2KFontFile, float paramFloat, BaseTransform paramBaseTransform, T2KFontStrikeDesc paramT2KFontStrikeDesc)
/*     */   {
/*  55 */     this.fontResource = paramT2KFontFile;
/*  56 */     this.size = paramFloat;
/*  57 */     this.desc = paramT2KFontStrikeDesc;
/*  58 */     boolean bool1 = PrismFontLoader.isLCDTextSupported();
/*  59 */     this.aaMode = (bool1 ? paramT2KFontStrikeDesc.aaMode : 0);
/*  60 */     int i = 0;
/*  61 */     boolean bool2 = false;
/*  62 */     float f1 = 1.0F;
/*  63 */     float f2 = 0.0F;
/*  64 */     this.matrix = new double[4];
/*  65 */     if (paramBaseTransform.isTranslateOrIdentity()) {
/*  66 */       this.transform = BaseTransform.IDENTITY_TRANSFORM;
/*     */       double tmp119_118 = paramFloat; this.matrix[3] = tmp119_118; this.matrix[0] = tmp119_118;
/*     */     } else {
/*  69 */       BaseTransform localBaseTransform = paramBaseTransform;
/*  70 */       Affine2D localAffine2D = new Affine2D(localBaseTransform.getMxx(), localBaseTransform.getMyx(), localBaseTransform.getMxy(), localBaseTransform.getMyy(), 0.0D, 0.0D);
/*     */ 
/*  73 */       this.transform = localAffine2D;
/*  74 */       this.invTx = new Affine2D(localAffine2D);
/*     */       try {
/*  76 */         this.invTx.invert();
/*     */       } catch (NoninvertibleTransformException localNoninvertibleTransformException) {
/*  78 */         this.invTx = null;
/*     */       }
/*  80 */       this.matrix[0] = (localAffine2D.getMxx() * paramFloat);
/*  81 */       this.matrix[1] = (localAffine2D.getMyx() * paramFloat);
/*  82 */       this.matrix[2] = (localAffine2D.getMxy() * paramFloat);
/*  83 */       this.matrix[3] = (localAffine2D.getMyy() * paramFloat);
/*     */     }
/*  85 */     float f3 = 80.0F;
/*  86 */     if ((Math.abs(this.matrix[0]) > f3) || (Math.abs(this.matrix[1]) > f3) || (Math.abs(this.matrix[2]) > f3) || (Math.abs(this.matrix[3]) > f3))
/*     */     {
/*  88 */       this.drawShapes = true;
/*     */     }
/*  90 */     else if ((T2KFontFactory.isWindows) && (this.aaMode == 1) && (paramT2KFontFile.isInstalledFont()) && (this.matrix[0] > 0.0D) && (this.matrix[0] == this.matrix[3]) && (this.matrix[1] == 0.0D) && (this.matrix[2] == 0.0D))
/*     */     {
/*  97 */       this.gdiLCDGlyphs = true;
/*  98 */       this.gdiSize = ((int)(this.matrix[0] + 0.5D));
/*     */     }
/*     */ 
/* 103 */     int j = 2;
/* 104 */     if (this.aaMode == 1) {
/* 105 */       j = 4;
/* 106 */       bool2 = true;
/*     */     }
/* 108 */     this.pScalerContext = paramT2KFontFile.createScalerContext(this.matrix, j, i, bool2, f1, f2);
/*     */ 
/* 118 */     this.disposer = new T2KStrikeDisposer(paramT2KFontFile, paramT2KFontStrikeDesc, this.pScalerContext);
/*     */   }
/*     */ 
/*     */   long getScalerContext()
/*     */   {
/* 123 */     return this.pScalerContext;
/*     */   }
/*     */ 
/*     */   public FontStrike.Metrics getMetrics()
/*     */   {
/* 136 */     if (this.metrics == null) {
/* 137 */       this.metrics = this.fontResource.getFontMetrics(this.pScalerContext, this.size);
/*     */     }
/* 139 */     return this.metrics;
/*     */   }
/*     */ 
/*     */   public FontResource getFontResource() {
/* 143 */     return this.fontResource;
/*     */   }
/*     */ 
/*     */   public float getSize()
/*     */   {
/* 156 */     return this.size;
/*     */   }
/*     */ 
/*     */   public boolean drawAsShapes() {
/* 160 */     return this.drawShapes;
/*     */   }
/*     */ 
/*     */   public int getAAMode() {
/* 164 */     return this.aaMode;
/*     */   }
/*     */ 
/*     */   public BaseTransform getTransform() {
/* 168 */     return this.transform;
/*     */   }
/*     */ 
/*     */   public boolean supportsGlyphImages() {
/* 172 */     return true;
/*     */   }
/*     */ 
/*     */   public int getNumGlyphs() {
/* 176 */     return this.fontResource.getNumGlyphs();
/*     */   }
/*     */ 
/*     */   public CharToGlyphMapper getGlyphMapper() {
/* 180 */     return this.fontResource.getGlyphMapper();
/*     */   }
/*     */ 
/*     */   public FontStrike.Glyph getGlyph(char paramChar)
/*     */   {
/* 185 */     int i = this.fontResource.getGlyphMapper().charToGlyph(paramChar);
/* 186 */     return getGlyph(i);
/*     */   }
/*     */ 
/*     */   private native long getLCDGlyphFromWindows(String paramString, boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2, boolean paramBoolean3);
/*     */ 
/*     */   public static native int getLCDContrast();
/*     */ 
/*     */   private long getGlyphFromWindows(int paramInt)
/*     */   {
/* 199 */     String str = this.fontResource.getFamilyName();
/* 200 */     boolean bool1 = this.fontResource.isBold();
/* 201 */     boolean bool2 = this.fontResource.isItalic();
/*     */ 
/* 203 */     return getLCDGlyphFromWindows(str, bool1, bool2, this.gdiSize, paramInt, true);
/*     */   }
/*     */ 
/*     */   public FontStrike.Glyph getGlyph(int paramInt)
/*     */   {
/* 209 */     Object localObject = (FontStrike.Glyph)this.glyphMap.get(Integer.valueOf(paramInt));
/* 210 */     if (localObject == null) {
/* 211 */       if (drawAsShapes()) {
/* 212 */         float f1 = this.fontResource.getAdvance(paramInt, this.size);
/* 213 */         localObject = new T2KGlyph(this, paramInt, f1);
/*     */       } else {
/* 215 */         long l1 = 0L; long l2 = 0L;
/* 216 */         if (this.gdiLCDGlyphs) {
/* 217 */           l1 = l2 = getGlyphFromWindows(paramInt);
/*     */         }
/* 219 */         if (l1 == 0L) {
/* 220 */           l1 = this.fontResource.getGlyphImage(this.pScalerContext, paramInt);
/*     */         }
/*     */ 
/* 223 */         localObject = new T2KGlyph(this, paramInt, l1);
/* 224 */         if (l2 != 0L) {
/* 225 */           float f2 = this.fontResource.getAdvance(paramInt, this.size);
/* 226 */           float f3 = f2; float f4 = 0.0F;
/* 227 */           if (this.invTx != null) {
/* 228 */             Point2D localPoint2D = new Point2D(f2, 0.0F);
/* 229 */             this.transform.transform(localPoint2D, localPoint2D);
/* 230 */             f3 = localPoint2D.x;
/* 231 */             f4 = localPoint2D.y;
/*     */           }
/* 233 */           ((T2KGlyph)localObject).setAdvance(f2, f3, f4);
/*     */         }
/*     */       }
/* 236 */       this.glyphMap.put(Integer.valueOf(paramInt), localObject);
/*     */     }
/* 238 */     return localObject;
/*     */   }
/*     */ 
/*     */   public RectBounds getStringVisualBounds(String paramString) {
/* 242 */     if ((paramString == null) || (paramString.length() == 0)) {
/* 243 */       return new RectBounds();
/*     */     }
/* 245 */     float[] arrayOfFloat = new float[4];
/* 246 */     float f1 = 0.0F; float f2 = 0.0F; float f3 = 0.0F; float f4 = 0.0F;
/* 247 */     CharToGlyphMapper localCharToGlyphMapper = this.fontResource.getGlyphMapper();
/*     */ 
/* 249 */     int i = localCharToGlyphMapper.charToGlyph(paramString.charAt(0));
/* 250 */     this.fontResource.getGlyphBoundingBox(i, this.size, arrayOfFloat);
/* 251 */     f1 = arrayOfFloat[0];
/* 252 */     f2 = arrayOfFloat[1];
/* 253 */     f3 = arrayOfFloat[2];
/* 254 */     f4 = arrayOfFloat[3];
/*     */ 
/* 256 */     int j = paramString.length();
/* 257 */     if (j == 1) {
/* 258 */       return new RectBounds(f1, f2, f3, f4);
/*     */     }
/* 260 */     float f5 = this.fontResource.getAdvance(i, this.size);
/* 261 */     for (int k = 1; k < j; k++) {
/* 262 */       i = localCharToGlyphMapper.charToGlyph(paramString.charAt(k));
/* 263 */       this.fontResource.getGlyphBoundingBox(i, this.size, arrayOfFloat);
/*     */ 
/* 268 */       if (arrayOfFloat[0] + f5 < f1) {
/* 269 */         f1 = arrayOfFloat[0] + f5;
/*     */       }
/* 271 */       if (arrayOfFloat[1] < f2) {
/* 272 */         f2 = arrayOfFloat[1];
/*     */       }
/*     */ 
/* 275 */       if (arrayOfFloat[2] + f5 > f3) {
/* 276 */         f3 = arrayOfFloat[2] + f5;
/*     */       }
/* 278 */       if (arrayOfFloat[3] > f4) {
/* 279 */         f4 = arrayOfFloat[3];
/*     */       }
/* 281 */       if (k < j - 1) {
/* 282 */         float f6 = this.fontResource.getAdvance(i, this.size);
/* 283 */         f5 += f6;
/*     */       }
/*     */     }
/* 286 */     return new RectBounds(f1, f2, f3, f4);
/*     */   }
/*     */ 
/*     */   public float getStringWidth(String paramString)
/*     */   {
/* 306 */     float f = 0.0F;
/* 307 */     for (int i = 0; i < paramString.length(); i++)
/*     */     {
/* 310 */       f += getCharAdvance(paramString.charAt(i));
/*     */     }
/* 312 */     return f;
/*     */   }
/*     */ 
/*     */   public float getStringHeight(String paramString) {
/* 316 */     return getMetrics().getLineHeight();
/*     */   }
/*     */ 
/*     */   public RectBounds getStringBounds(String paramString)
/*     */   {
/* 327 */     if (paramString == null) {
/* 328 */       return new RectBounds();
/*     */     }
/* 330 */     float f = getStringWidth(paramString);
/*     */ 
/* 332 */     FontStrike.Metrics localMetrics = getMetrics();
/* 333 */     RectBounds localRectBounds = new RectBounds(0.0F, localMetrics.getAscent(), f, localMetrics.getDescent() + localMetrics.getLineGap());
/*     */ 
/* 335 */     return localRectBounds;
/*     */   }
/*     */ 
/*     */   public Shape getOutline(String paramString, BaseTransform paramBaseTransform)
/*     */   {
/* 340 */     Path2D localPath2D = new Path2D();
/* 341 */     getOutline(paramString, paramBaseTransform, localPath2D);
/*     */ 
/* 343 */     return localPath2D;
/*     */   }
/*     */ 
/*     */   public void getOutline(String paramString, BaseTransform paramBaseTransform, Path2D paramPath2D)
/*     */   {
/* 349 */     paramPath2D.reset();
/* 350 */     if (paramString == null) {
/* 351 */       return;
/*     */     }
/* 353 */     float f1 = 0.0F;
/* 354 */     float f2 = getMetrics().getLineHeight();
/* 355 */     if (paramBaseTransform == null) {
/* 356 */       paramBaseTransform = BaseTransform.IDENTITY_TRANSFORM;
/*     */     }
/* 358 */     Affine2D localAffine2D = new Affine2D();
/* 359 */     float f3 = 0.0F;
/* 360 */     for (int i = 0; i < paramString.length(); i++) {
/* 361 */       localAffine2D.setTransform(paramBaseTransform);
/* 362 */       char c = paramString.charAt(i);
/* 363 */       if (c == '\n') {
/* 364 */         f3 -= f2;
/* 365 */         f1 = 0.0F;
/*     */       }
/*     */       else {
/* 368 */         FontStrike.Glyph localGlyph = getGlyph(c);
/* 369 */         Shape localShape = localGlyph.getShape();
/* 370 */         localAffine2D.translate(f1, f3);
/* 371 */         paramPath2D.append(localShape.getPathIterator(localAffine2D), false);
/* 372 */         f1 += localGlyph.getAdvance();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   Path2D getGlyphOutline(T2KGlyph paramT2KGlyph)
/*     */   {
/* 379 */     return getGlyphOutline(paramT2KGlyph.getGlyphCode());
/*     */   }
/*     */ 
/*     */   Path2D getGlyphOutline(int paramInt)
/*     */   {
/* 413 */     Path2D localPath2D = this.fontResource.getGlyphOutline(this.pScalerContext, paramInt);
/* 414 */     if (this.invTx != null) {
/* 415 */       localPath2D.transform(this.invTx);
/*     */     }
/*     */ 
/* 418 */     return localPath2D;
/*     */   }
/*     */ 
/*     */   RectBounds getGlyphBounds(T2KGlyph paramT2KGlyph) {
/* 422 */     return this.fontResource.getGlyphBounds(this.pScalerContext, paramT2KGlyph.getGlyphCode());
/*     */   }
/*     */ 
/*     */   float getGlyphUserAdvance(float paramFloat1, float paramFloat2) {
/* 426 */     if (this.invTx != null) {
/* 427 */       Point2D localPoint2D = new Point2D(paramFloat1, paramFloat2);
/* 428 */       this.invTx.transform(localPoint2D, localPoint2D);
/* 429 */       return localPoint2D.x;
/*     */     }
/* 431 */     return paramFloat1;
/*     */   }
/*     */ 
/*     */   public float getCharAdvance(char paramChar)
/*     */   {
/* 443 */     int i = this.fontResource.getGlyphMapper().charToGlyph(paramChar);
/* 444 */     return this.fontResource.getAdvance(i, this.size);
/*     */   }
/*     */ 
/*     */   private void getStyleMetrics()
/*     */   {
/* 451 */     if (this.styleMetrics == null)
/* 452 */       this.styleMetrics = this.fontResource.getStyleMetrics(this.size);
/*     */   }
/*     */ 
/*     */   public float getStrikeThroughOffset() {
/* 456 */     getStyleMetrics();
/* 457 */     return this.styleMetrics[0];
/*     */   }
/*     */ 
/*     */   public float getStrikeThroughThickness() {
/* 461 */     getStyleMetrics();
/* 462 */     return this.styleMetrics[1];
/*     */   }
/*     */   public float getUnderLineOffset() {
/* 465 */     getStyleMetrics();
/* 466 */     return this.styleMetrics[2];
/*     */   }
/*     */   public float getUnderLineThickness() {
/* 469 */     getStyleMetrics();
/* 470 */     return this.styleMetrics[3];
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 475 */     if (paramObject == null) {
/* 476 */       return false;
/*     */     }
/* 478 */     if (!(paramObject instanceof T2KFontStrike)) {
/* 479 */       return false;
/*     */     }
/* 481 */     T2KFontStrike localT2KFontStrike = (T2KFontStrike)paramObject;
/*     */ 
/* 486 */     return (this.size == localT2KFontStrike.size) && (this.matrix[0] == localT2KFontStrike.matrix[0]) && (this.matrix[1] == localT2KFontStrike.matrix[1]) && (this.matrix[2] == localT2KFontStrike.matrix[2]) && (this.matrix[3] == localT2KFontStrike.matrix[3]) && (this.fontResource.equals(localT2KFontStrike.fontResource));
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 498 */     if (this.hash != 0) {
/* 499 */       return this.hash;
/*     */     }
/*     */ 
/* 502 */     this.hash = (Float.floatToIntBits(this.size) + Float.floatToIntBits((float)this.matrix[0]) + Float.floatToIntBits((float)this.matrix[1]) + Float.floatToIntBits((float)this.matrix[2]) + Float.floatToIntBits((float)this.matrix[3]));
/*     */ 
/* 508 */     this.hash = (71 * this.hash + this.fontResource.hashCode());
/* 509 */     return this.hash;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 514 */     return "FontStrike: " + super.toString() + " font resource = " + this.fontResource + " size = " + this.size + " matrix = {" + this.matrix[0] + ", " + this.matrix[1] + ", " + this.matrix[2] + ", " + this.matrix[3] + "}";
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.t2k.T2KFontStrike
 * JD-Core Version:    0.6.2
 */