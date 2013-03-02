/*     */ package com.sun.t2k;
/*     */ 
/*     */ import com.sun.javafx.font.FontStrike.Glyph;
/*     */ import com.sun.javafx.geom.RectBounds;
/*     */ import com.sun.javafx.geom.Shape;
/*     */ 
/*     */ public class T2KGlyph
/*     */   implements FontStrike.Glyph
/*     */ {
/*     */   private T2KFontStrike strike;
/*     */   private int gc;
/*     */   private float userAdvance;
/*     */   private float deviceXAdvance;
/*     */   private float deviceYAdvance;
/*     */   byte[] pixelData;
/*     */   private int width;
/*     */   private int height;
/*     */   private int originX;
/*     */   private int originY;
/*     */   private boolean isLCDGlyph;
/*     */   private RectBounds b2d;
/*     */ 
/*     */   public T2KGlyph(T2KFontStrike paramT2KFontStrike, int paramInt, float paramFloat)
/*     */   {
/*  26 */     this.strike = paramT2KFontStrike;
/*  27 */     this.gc = paramInt;
/*  28 */     this.userAdvance = paramFloat;
/*     */   }
/*     */ 
/*     */   T2KGlyph(T2KFontStrike paramT2KFontStrike, int paramInt, long paramLong) {
/*  32 */     this.strike = paramT2KFontStrike;
/*  33 */     this.gc = paramInt;
/*     */ 
/*  35 */     int[] arrayOfInt = getGlyphInfo(paramLong);
/*  36 */     this.width = arrayOfInt[0];
/*  37 */     this.height = arrayOfInt[1];
/*  38 */     this.originX = arrayOfInt[2];
/*  39 */     this.originY = arrayOfInt[3];
/*  40 */     int i = arrayOfInt[4];
/*  41 */     this.isLCDGlyph = false;
/*  42 */     if (i > this.width)
/*     */     {
/*  45 */       this.width = i;
/*  46 */       this.isLCDGlyph = true;
/*     */     }
/*     */ 
/*  64 */     this.deviceXAdvance = getGlyphPixelXAdvance(paramLong);
/*  65 */     this.deviceYAdvance = getGlyphPixelYAdvance(paramLong);
/*  66 */     this.userAdvance = paramT2KFontStrike.getGlyphUserAdvance(this.deviceXAdvance, this.deviceYAdvance);
/*     */ 
/*  69 */     this.pixelData = getGlyphPixelData(paramLong);
/*  70 */     freeGlyph(paramLong);
/*     */   }
/*     */ 
/*     */   public int getGlyphCode() {
/*  74 */     return this.gc;
/*     */   }
/*     */ 
/*     */   public RectBounds getBBox()
/*     */   {
/*  79 */     if (this.b2d == null) {
/*  80 */       this.b2d = this.strike.getGlyphBounds(this);
/*     */     }
/*  82 */     return this.b2d; } 
/*     */   private native int[] getGlyphInfo(long paramLong);
/*     */ 
/*     */   private native byte[] getGlyphPixelData(long paramLong);
/*     */ 
/*     */   private native float getGlyphPixelXAdvance(long paramLong);
/*     */ 
/*     */   private native float getGlyphPixelYAdvance(long paramLong);
/*     */ 
/*     */   private native void freeGlyph(long paramLong);
/*     */ 
/*  93 */   void setAdvance(float paramFloat1, float paramFloat2, float paramFloat3) { this.userAdvance = paramFloat1;
/*  94 */     this.deviceXAdvance = paramFloat2;
/*  95 */     this.deviceYAdvance = paramFloat3; }
/*     */ 
/*     */   public float getAdvance()
/*     */   {
/*  99 */     return this.userAdvance;
/*     */   }
/*     */ 
/*     */   public Shape getShape() {
/* 103 */     return this.strike.getGlyphOutline(this);
/*     */   }
/*     */ 
/*     */   public float getPixelXAdvance() {
/* 107 */     return this.deviceXAdvance;
/*     */   }
/*     */ 
/*     */   public float getPixelYAdvance() {
/* 111 */     return this.deviceYAdvance;
/*     */   }
/*     */ 
/*     */   public int getWidth()
/*     */   {
/* 116 */     return this.width;
/*     */   }
/*     */ 
/*     */   public int getHeight() {
/* 120 */     return this.height;
/*     */   }
/*     */ 
/*     */   public int getOriginX() {
/* 124 */     return this.originX;
/*     */   }
/*     */ 
/*     */   public int getOriginY() {
/* 128 */     return this.originY;
/*     */   }
/*     */ 
/*     */   public byte[] getPixelData() {
/* 132 */     return this.pixelData;
/*     */   }
/*     */ 
/*     */   public boolean isLCDGlyph()
/*     */   {
/* 141 */     return this.isLCDGlyph;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.t2k.T2KGlyph
 * JD-Core Version:    0.6.2
 */