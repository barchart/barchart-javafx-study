/*     */ package com.sun.javafx.css.converters;
/*     */ 
/*     */ import com.sun.javafx.Utils;
/*     */ import com.sun.javafx.css.FontUnits.Style;
/*     */ import com.sun.javafx.css.FontUnits.Weight;
/*     */ import com.sun.javafx.css.ParsedValue;
/*     */ import com.sun.javafx.css.Size;
/*     */ import com.sun.javafx.css.StyleConverter;
/*     */ import com.sun.javafx.css.StyleableProperty;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import javafx.scene.text.Font;
/*     */ import javafx.scene.text.FontPosture;
/*     */ import javafx.scene.text.FontWeight;
/*     */ 
/*     */ public final class FontConverter extends StyleConverter<ParsedValue[], Font>
/*     */ {
/*     */   public static FontConverter getInstance()
/*     */   {
/*  50 */     return Holder.INSTANCE;
/*     */   }
/*     */ 
/*     */   public Font convert(ParsedValue<ParsedValue[], Font> paramParsedValue, Font paramFont)
/*     */   {
/*  59 */     ParsedValue[] arrayOfParsedValue = (ParsedValue[])paramParsedValue.getValue();
/*  60 */     Font localFont1 = paramFont != null ? paramFont : Font.getDefault();
/*  61 */     String str = arrayOfParsedValue[0] != null ? Utils.stripQuotes((String)arrayOfParsedValue[0].convert(localFont1)) : localFont1.getFamily();
/*     */ 
/*  64 */     double d = localFont1.getSize();
/*  65 */     if (arrayOfParsedValue[1] != null) {
/*  66 */       localObject1 = (ParsedValue)arrayOfParsedValue[1].getValue();
/*  67 */       localObject2 = (Size)((ParsedValue)localObject1).convert(localFont1);
/*  68 */       d = ((Size)localObject2).pixels(localFont1.getSize(), localFont1);
/*     */     }
/*  70 */     Object localObject1 = arrayOfParsedValue[2] != null ? (FontWeight)arrayOfParsedValue[2].convert(localFont1) : FontWeight.NORMAL;
/*  71 */     Object localObject2 = arrayOfParsedValue[3] != null ? (FontPosture)arrayOfParsedValue[3].convert(localFont1) : FontPosture.REGULAR;
/*  72 */     Font localFont2 = Font.font(str, (FontWeight)localObject1, (FontPosture)localObject2, d);
/*  73 */     return localFont2;
/*     */   }
/*     */ 
/*     */   public Font convert(Map<StyleableProperty, Object> paramMap)
/*     */   {
/*  78 */     Font localFont = Font.getDefault();
/*  79 */     double d = localFont.getSize();
/*  80 */     String str1 = localFont.getFamily();
/*  81 */     FontWeight localFontWeight = FontWeight.NORMAL;
/*  82 */     FontPosture localFontPosture = FontPosture.REGULAR;
/*     */ 
/*  84 */     for (Object localObject1 = paramMap.entrySet().iterator(); ((Iterator)localObject1).hasNext(); ) { Map.Entry localEntry = (Map.Entry)((Iterator)localObject1).next();
/*     */ 
/*  86 */       Object localObject2 = localEntry.getValue();
/*  87 */       if (localObject2 != null)
/*     */       {
/*  90 */         String str2 = ((StyleableProperty)localEntry.getKey()).getProperty();
/*  91 */         if (str2.endsWith("font-size"))
/*  92 */           d = ((Number)localObject2).doubleValue();
/*  93 */         else if (str2.endsWith("font-family"))
/*  94 */           str1 = Utils.stripQuotes((String)localObject2);
/*  95 */         else if (str2.endsWith("font-weight"))
/*  96 */           localFontWeight = (FontWeight)localObject2;
/*  97 */         else if (str2.endsWith("font-style"))
/*  98 */           localFontPosture = (FontPosture)localObject2;
/*     */       }
/*     */     }
/* 101 */     localObject1 = Font.font(str1, localFontWeight, localFontPosture, d);
/* 102 */     return localObject1;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 107 */     return "FontConverter";
/*     */   }
/*     */ 
/*     */   public static final class SizeConverter extends StyleConverter<ParsedValue<?, Size>, Double>
/*     */   {
/*     */     public static SizeConverter getInstance()
/*     */     {
/* 204 */       return Holder.INSTANCE;
/*     */     }
/*     */ 
/*     */     public Double convert(ParsedValue<ParsedValue<?, Size>, Double> paramParsedValue, Font paramFont)
/*     */     {
/* 213 */       ParsedValue localParsedValue = (ParsedValue)paramParsedValue.getValue();
/* 214 */       return Double.valueOf(((Size)localParsedValue.convert(paramFont)).pixels(paramFont.getSize(), paramFont));
/*     */     }
/*     */ 
/*     */     public String toString()
/*     */     {
/* 219 */       return "Font.SizeConverter";
/*     */     }
/*     */ 
/*     */     private static class Holder
/*     */     {
/* 200 */       static FontConverter.SizeConverter INSTANCE = new FontConverter.SizeConverter(null);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static final class WeightConverter extends StyleConverter<FontUnits.Weight, FontWeight>
/*     */   {
/*     */     public static WeightConverter getInstance()
/*     */     {
/* 156 */       return Holder.INSTANCE;
/*     */     }
/*     */ 
/*     */     public FontWeight convert(ParsedValue<FontUnits.Weight, FontWeight> paramParsedValue, Font paramFont)
/*     */     {
/* 166 */       FontUnits.Weight localWeight = (FontUnits.Weight)paramParsedValue.getValue();
/*     */ 
/* 168 */       if ((FontUnits.Weight.INHERIT != localWeight) && (FontUnits.Weight.BOLDER != localWeight) && (FontUnits.Weight.LIGHTER != localWeight))
/*     */       {
/* 171 */         return localWeight.toFontWeight();
/* 172 */       }if (paramFont != null) {
/* 173 */         FontWeight localFontWeight = FontWeight.NORMAL;
/*     */ 
/* 175 */         if (FontUnits.Weight.INHERIT == localWeight)
/* 176 */           return localFontWeight;
/* 177 */         if (FontUnits.Weight.BOLDER == localWeight)
/*     */         {
/* 179 */           return FontUnits.Weight.toWeight(localFontWeight).bolder().toFontWeight();
/* 180 */         }if (FontUnits.Weight.LIGHTER == localWeight) {
/* 181 */           return FontUnits.Weight.toWeight(localFontWeight).lighter().toFontWeight();
/*     */         }
/* 183 */         return localWeight.toFontWeight();
/*     */       }
/*     */ 
/* 186 */       return localWeight.toFontWeight();
/*     */     }
/*     */ 
/*     */     public String toString()
/*     */     {
/* 192 */       return "Font.WeightConverter";
/*     */     }
/*     */ 
/*     */     private static class Holder
/*     */     {
/* 152 */       static FontConverter.WeightConverter INSTANCE = new FontConverter.WeightConverter(null);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static final class StyleConverter extends StyleConverter<FontUnits.Style, FontPosture>
/*     */   {
/*     */     public static StyleConverter getInstance()
/*     */     {
/* 118 */       return Holder.INSTANCE;
/*     */     }
/*     */ 
/*     */     public FontPosture convert(ParsedValue<FontUnits.Style, FontPosture> paramParsedValue, Font paramFont)
/*     */     {
/* 128 */       FontUnits.Style localStyle = (FontUnits.Style)paramParsedValue.getValue();
/*     */ 
/* 130 */       if (FontUnits.Style.INHERIT != localStyle) {
/* 131 */         return localStyle.toFontPosture();
/*     */       }
/* 133 */       if (paramFont != null)
/*     */       {
/* 136 */         return FontPosture.REGULAR;
/*     */       }
/* 138 */       return FontPosture.REGULAR;
/*     */     }
/*     */ 
/*     */     public String toString()
/*     */     {
/* 144 */       return "Font.StyleConverter";
/*     */     }
/*     */ 
/*     */     private static class Holder
/*     */     {
/* 114 */       static FontConverter.StyleConverter INSTANCE = new FontConverter.StyleConverter(null);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class Holder
/*     */   {
/*  46 */     static FontConverter INSTANCE = new FontConverter(null);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.css.converters.FontConverter
 * JD-Core Version:    0.6.2
 */