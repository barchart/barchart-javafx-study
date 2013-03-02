/*     */ package com.sun.webpane.sg.prism;
/*     */ 
/*     */ import com.sun.javafx.font.FontFactory;
/*     */ import com.sun.javafx.font.FontStrike;
/*     */ import com.sun.javafx.font.FontStrike.Glyph;
/*     */ import com.sun.javafx.font.FontStrike.Metrics;
/*     */ import com.sun.javafx.font.PGFont;
/*     */ import com.sun.javafx.geom.RectBounds;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.prism.GraphicsPipeline;
/*     */ import com.sun.webpane.platform.graphics.WCFont;
/*     */ import java.util.HashMap;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ public class WCFontImpl extends WCFont
/*     */ {
/*  21 */   private static final Logger log = Logger.getLogger(WCFontImpl.class.getName());
/*     */ 
/*  24 */   private static final HashMap<String, String> FONT_MAP = new HashMap();
/*     */   private final PGFont font;
/*     */   private FontStrike strike;
/* 109 */   private double xheight = 1.7976931348623157E+308D;
/*     */ 
/*     */   static WCFont getFont(String paramString, boolean paramBoolean1, boolean paramBoolean2, float paramFloat)
/*     */   {
/*  27 */     FontFactory localFontFactory = GraphicsPipeline.getPipeline().getFontFactory();
/*  28 */     synchronized (FONT_MAP) {
/*  29 */       if (FONT_MAP.isEmpty()) {
/*  30 */         FONT_MAP.put("serif", "Serif");
/*  31 */         FONT_MAP.put("dialog", "SansSerif");
/*  32 */         FONT_MAP.put("helvetica", "SansSerif");
/*  33 */         FONT_MAP.put("sansserif", "SansSerif");
/*  34 */         FONT_MAP.put("sans-serif", "SansSerif");
/*  35 */         FONT_MAP.put("monospace", "Monospaced");
/*  36 */         FONT_MAP.put("monospaced", "Monospaced");
/*  37 */         for (Object localObject2 : localFontFactory.getFontFamilyNames()) {
/*  38 */           FONT_MAP.put(localObject2.toLowerCase(), localObject2);
/*     */         }
/*     */       }
/*     */     }
/*  42 */     ??? = (String)FONT_MAP.get(paramString.toLowerCase());
/*  43 */     if (log.isLoggable(Level.FINE)) {
/*  44 */       ??? = new StringBuilder("WCFontImpl.get(");
/*  45 */       ((StringBuilder)???).append(paramString).append(", ").append(paramFloat);
/*  46 */       if (paramBoolean1) {
/*  47 */         ((StringBuilder)???).append(", bold");
/*     */       }
/*  49 */       if (paramBoolean2) {
/*  50 */         ((StringBuilder)???).append(", italic");
/*     */       }
/*  52 */       log.fine(((StringBuilder)???).append(") = ").append((String)???).toString());
/*     */     }
/*  54 */     return ??? != null ? new WCFontImpl(localFontFactory.createFont((String)???, paramBoolean1, paramBoolean2, paramFloat)) : null;
/*     */   }
/*     */ 
/*     */   private WCFontImpl(PGFont paramPGFont)
/*     */   {
/*  62 */     this.font = paramPGFont;
/*     */   }
/*     */ 
/*     */   public int getOffsetForPosition(String paramString, int paramInt, float paramFloat1, float paramFloat2)
/*     */   {
/*  78 */     return 0;
/*     */   }
/*     */ 
/*     */   private FontStrike getFontStrike()
/*     */   {
/*  84 */     if (this.strike == null) {
/*  85 */       this.strike = this.font.getStrike(BaseTransform.IDENTITY_TRANSFORM, 1);
/*     */     }
/*  87 */     return this.strike;
/*     */   }
/*     */ 
/*     */   public double getGlyphWidth(int paramInt)
/*     */   {
/*  98 */     double d = getFontStrike().getCharAdvance((char)paramInt);
/*     */ 
/* 101 */     if (log.isLoggable(Level.FINER)) {
/* 102 */       log.log(Level.FINER, "getGlyphWidth({0}, {1}, {2})= {3}", new Object[] { this.font.getName(), Float.valueOf(this.font.getSize()), new String(Character.toChars(paramInt)), Double.valueOf(d) });
/*     */     }
/*     */ 
/* 106 */     return (float)d;
/*     */   }
/*     */ 
/*     */   public double getXHeight()
/*     */   {
/* 114 */     if (this.xheight == 1.7976931348623157E+308D) {
/* 115 */       this.xheight = getFontStrike().getGlyph('x').getBBox().getHeight();
/* 116 */       if (this.xheight == 0.0D)
/*     */       {
/* 118 */         this.xheight = (getFontStrike().getMetrics().getAscent() * 0.5600000000000001D);
/*     */       }
/*     */     }
/* 121 */     if (log.isLoggable(Level.FINER)) {
/* 122 */       log.log(Level.FINER, "getXHeight({0}, {1}) = {2}", new Object[] { this.font.getName(), Float.valueOf(this.font.getSize()), Integer.valueOf((int)this.xheight) });
/*     */     }
/*     */ 
/* 126 */     return this.xheight;
/*     */   }
/*     */ 
/*     */   public int[] getGlyphCodes(int[] paramArrayOfInt)
/*     */   {
/* 136 */     return paramArrayOfInt;
/*     */   }
/*     */ 
/*     */   public double getStringLength(String paramString, float paramFloat1, float paramFloat2)
/*     */   {
/* 145 */     if (paramString.length() == 0) {
/* 146 */       return 0.0D;
/*     */     }
/*     */ 
/* 150 */     double d = getFontStrike().getStringWidth(paramString);
/* 151 */     log.log(Level.FINER, "getStringLength({0}) = {1}", new Object[] { paramString, Double.valueOf(d) });
/*     */ 
/* 154 */     return d;
/*     */   }
/*     */ 
/*     */   public double[] getStringBounds(String paramString, int paramInt1, int paramInt2, boolean paramBoolean, float paramFloat1, float paramFloat2)
/*     */   {
/* 167 */     RectBounds localRectBounds = getFontStrike().getStringBounds(paramString.substring(paramInt1, paramInt2));
/* 168 */     log.log(Level.FINER, "getStringBounds({0}, {1}, {2}) = [{3}, {4}, {5}, {6}])", new Object[] { paramString, Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), Float.valueOf(localRectBounds.getMinX()), Float.valueOf(localRectBounds.getMinY()), Float.valueOf(localRectBounds.getWidth()), Float.valueOf(localRectBounds.getHeight()) });
/*     */ 
/* 171 */     return new double[] { localRectBounds.getMinX(), localRectBounds.getMinY(), localRectBounds.getWidth(), localRectBounds.getHeight() };
/*     */   }
/*     */ 
/*     */   public int getAscent()
/*     */   {
/* 182 */     int i = -(int)getFontStrike().getMetrics().getAscent();
/* 183 */     if (log.isLoggable(Level.FINER)) {
/* 184 */       log.log(Level.FINER, "getAscent({0}, {1}) = {2}", new Object[] { this.font.getName(), Float.valueOf(this.font.getSize()), Integer.valueOf(i) });
/*     */     }
/*     */ 
/* 188 */     return i;
/*     */   }
/*     */ 
/*     */   public int getDescent()
/*     */   {
/* 193 */     int i = (int)getFontStrike().getMetrics().getDescent();
/* 194 */     if (log.isLoggable(Level.FINER)) {
/* 195 */       log.log(Level.FINER, "getDescent({0}, {1}) = {2}", new Object[] { this.font.getName(), Float.valueOf(this.font.getSize()), Integer.valueOf(i) });
/*     */     }
/*     */ 
/* 199 */     return i;
/*     */   }
/*     */ 
/*     */   public int getHeight()
/*     */   {
/* 204 */     int i = (int)getFontStrike().getMetrics().getLineHeight();
/* 205 */     if (log.isLoggable(Level.FINER)) {
/* 206 */       log.log(Level.FINER, "getHeight({0}, {1}) = {2}", new Object[] { this.font.getName(), Float.valueOf(this.font.getSize()), Integer.valueOf(i) });
/*     */     }
/*     */ 
/* 210 */     return i;
/*     */   }
/*     */ 
/*     */   public boolean hasUniformLineMetrics() {
/* 214 */     return false;
/*     */   }
/*     */ 
/*     */   public Object getPlatformFont() {
/* 218 */     return this.font;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.sg.prism.WCFontImpl
 * JD-Core Version:    0.6.2
 */