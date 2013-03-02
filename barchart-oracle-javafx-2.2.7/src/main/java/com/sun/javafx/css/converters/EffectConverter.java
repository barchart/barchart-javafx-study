/*     */ package com.sun.javafx.css.converters;
/*     */ 
/*     */ import com.sun.javafx.css.ParsedValue;
/*     */ import com.sun.javafx.css.Size;
/*     */ import com.sun.javafx.css.StyleConverter;
/*     */ import javafx.scene.effect.BlurType;
/*     */ import javafx.scene.effect.DropShadow;
/*     */ import javafx.scene.effect.Effect;
/*     */ import javafx.scene.effect.InnerShadow;
/*     */ import javafx.scene.paint.Color;
/*     */ import javafx.scene.text.Font;
/*     */ 
/*     */ public class EffectConverter extends StyleConverter<ParsedValue[], Effect>
/*     */ {
/*     */   public static EffectConverter getInstance()
/*     */   {
/*  54 */     return Holder.EFFECT_CONVERTER;
/*     */   }
/*     */ 
/*     */   public Effect convert(ParsedValue<ParsedValue[], Effect> paramParsedValue, Font paramFont)
/*     */   {
/*  59 */     throw new IllegalArgumentException("Parsed value is not an Effect");
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  68 */     return "EffectConverter";
/*     */   }
/*     */ 
/*     */   public static final class InnerShadowConverter extends EffectConverter
/*     */   {
/*     */     public static InnerShadowConverter getInstance()
/*     */     {
/* 138 */       return EffectConverter.Holder.INNER_SHADOW_INSTANCE;
/*     */     }
/*     */ 
/*     */     public Effect convert(ParsedValue<ParsedValue[], Effect> paramParsedValue, Font paramFont)
/*     */     {
/* 164 */       ParsedValue[] arrayOfParsedValue = (ParsedValue[])paramParsedValue.getValue();
/* 165 */       BlurType localBlurType = (BlurType)arrayOfParsedValue[0].convert(paramFont);
/* 166 */       Color localColor = (Color)arrayOfParsedValue[1].convert(paramFont);
/* 167 */       Double localDouble1 = Double.valueOf(((Size)arrayOfParsedValue[2].convert(paramFont)).pixels(paramFont));
/* 168 */       Double localDouble2 = Double.valueOf(((Size)arrayOfParsedValue[3].convert(paramFont)).pixels(paramFont));
/* 169 */       Double localDouble3 = Double.valueOf(((Size)arrayOfParsedValue[4].convert(paramFont)).pixels(paramFont));
/* 170 */       Double localDouble4 = Double.valueOf(((Size)arrayOfParsedValue[5].convert(paramFont)).pixels(paramFont));
/* 171 */       InnerShadow localInnerShadow = new InnerShadow();
/* 172 */       if (localBlurType != null) {
/* 173 */         localInnerShadow.setBlurType(localBlurType);
/*     */       }
/* 175 */       if (localColor != null) {
/* 176 */         localInnerShadow.setColor(localColor);
/*     */       }
/* 178 */       if (localDouble1 != null) {
/* 179 */         localInnerShadow.setRadius(localDouble1.doubleValue());
/*     */       }
/* 181 */       if (localDouble2 != null) {
/* 182 */         localInnerShadow.setChoke(localDouble2.doubleValue());
/*     */       }
/* 184 */       if (localDouble3 != null) {
/* 185 */         localInnerShadow.setOffsetX(localDouble3.doubleValue());
/*     */       }
/* 187 */       if (localDouble4 != null) {
/* 188 */         localInnerShadow.setOffsetY(localDouble4.doubleValue());
/*     */       }
/* 190 */       return localInnerShadow;
/*     */     }
/*     */ 
/*     */     public String toString()
/*     */     {
/* 195 */       return "InnerShadowConverter";
/*     */     }
/*     */   }
/*     */ 
/*     */   public static final class DropShadowConverter extends EffectConverter
/*     */   {
/*     */     public static DropShadowConverter getInstance()
/*     */     {
/*  74 */       return EffectConverter.Holder.DROP_SHADOW_INSTANCE;
/*     */     }
/*     */ 
/*     */     public Effect convert(ParsedValue<ParsedValue[], Effect> paramParsedValue, Font paramFont)
/*     */     {
/* 100 */       ParsedValue[] arrayOfParsedValue = (ParsedValue[])paramParsedValue.getValue();
/* 101 */       BlurType localBlurType = (BlurType)arrayOfParsedValue[0].convert(paramFont);
/* 102 */       Color localColor = (Color)arrayOfParsedValue[1].convert(paramFont);
/* 103 */       Double localDouble1 = Double.valueOf(((Size)arrayOfParsedValue[2].convert(paramFont)).pixels(paramFont));
/* 104 */       Double localDouble2 = Double.valueOf(((Size)arrayOfParsedValue[3].convert(paramFont)).pixels(paramFont));
/* 105 */       Double localDouble3 = Double.valueOf(((Size)arrayOfParsedValue[4].convert(paramFont)).pixels(paramFont));
/* 106 */       Double localDouble4 = Double.valueOf(((Size)arrayOfParsedValue[5].convert(paramFont)).pixels(paramFont));
/* 107 */       DropShadow localDropShadow = new DropShadow();
/* 108 */       if (localBlurType != null) {
/* 109 */         localDropShadow.setBlurType(localBlurType);
/*     */       }
/* 111 */       if (localColor != null) {
/* 112 */         localDropShadow.setColor(localColor);
/*     */       }
/* 114 */       if (localDouble2 != null) {
/* 115 */         localDropShadow.setSpread(localDouble2.doubleValue());
/*     */       }
/* 117 */       if (localDouble1 != null) {
/* 118 */         localDropShadow.setRadius(localDouble1.doubleValue());
/*     */       }
/* 120 */       if (localDouble3 != null) {
/* 121 */         localDropShadow.setOffsetX(localDouble3.doubleValue());
/*     */       }
/* 123 */       if (localDouble4 != null) {
/* 124 */         localDropShadow.setOffsetY(localDouble4.doubleValue());
/*     */       }
/* 126 */       return localDropShadow;
/*     */     }
/*     */ 
/*     */     public String toString()
/*     */     {
/* 131 */       return "DropShadowConverter";
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class Holder
/*     */   {
/*  45 */     static EffectConverter EFFECT_CONVERTER = new EffectConverter();
/*     */ 
/*  47 */     static EffectConverter.DropShadowConverter DROP_SHADOW_INSTANCE = new EffectConverter.DropShadowConverter(null);
/*     */ 
/*  49 */     static EffectConverter.InnerShadowConverter INNER_SHADOW_INSTANCE = new EffectConverter.InnerShadowConverter(null);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.css.converters.EffectConverter
 * JD-Core Version:    0.6.2
 */