/*     */ package com.sun.javafx.tk;
/*     */ 
/*     */ import javafx.scene.text.Font;
/*     */ 
/*     */ public class FontMetrics
/*     */ {
/*     */   private float maxAscent;
/*     */   private float ascent;
/*     */   private float xheight;
/*     */   private int baseline;
/*     */   private float descent;
/*     */   private float maxDescent;
/*     */   private float leading;
/*     */   private float lineHeight;
/*     */   private Font font;
/*     */ 
/*     */   public static FontMetrics impl_createFontMetrics(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, Font paramFont)
/*     */   {
/*  40 */     return new FontMetrics(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFont);
/*     */   }
/*     */ 
/*     */   public final float getMaxAscent()
/*     */   {
/*  50 */     return this.maxAscent;
/*     */   }
/*     */ 
/*     */   public final float getAscent()
/*     */   {
/*  60 */     return this.ascent;
/*     */   }
/*     */ 
/*     */   public final float getXheight()
/*     */   {
/*  69 */     return this.xheight;
/*     */   }
/*     */ 
/*     */   public final int getBaseline()
/*     */   {
/*  81 */     return this.baseline;
/*     */   }
/*     */ 
/*     */   public final float getDescent()
/*     */   {
/*  91 */     return this.descent;
/*     */   }
/*     */ 
/*     */   public final float getMaxDescent()
/*     */   {
/* 101 */     return this.maxDescent;
/*     */   }
/*     */ 
/*     */   public final float getLeading()
/*     */   {
/* 112 */     return this.leading;
/*     */   }
/*     */ 
/*     */   public final float getLineHeight()
/*     */   {
/* 121 */     return this.lineHeight;
/*     */   }
/*     */ 
/*     */   public final Font getFont()
/*     */   {
/* 130 */     if (this.font == null) {
/* 131 */       this.font = Font.getDefault();
/*     */     }
/* 133 */     return this.font;
/*     */   }
/*     */ 
/*     */   public FontMetrics(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, Font paramFont)
/*     */   {
/* 139 */     this.maxAscent = paramFloat1;
/* 140 */     this.ascent = paramFloat2;
/* 141 */     this.xheight = paramFloat3;
/* 142 */     this.descent = paramFloat4;
/* 143 */     this.maxDescent = paramFloat5;
/* 144 */     this.leading = paramFloat6;
/* 145 */     this.font = paramFont;
/* 146 */     this.lineHeight = (paramFloat1 + paramFloat5 + paramFloat6);
/*     */   }
/*     */ 
/*     */   public float computeStringWidth(String paramString)
/*     */   {
/* 154 */     return Toolkit.getToolkit().getFontLoader().computeStringWidth(paramString, getFont());
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 158 */     return "FontMetrics: [maxAscent=" + getMaxAscent() + ", ascent=" + getAscent() + ", xheight=" + getXheight() + ", baseline=" + getBaseline() + ", descent=" + getDescent() + ", maxDescent=" + getMaxDescent() + ", leading=" + getLeading() + ", lineHeight=" + getLineHeight() + ", font=" + getFont() + "]";
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.tk.FontMetrics
 * JD-Core Version:    0.6.2
 */