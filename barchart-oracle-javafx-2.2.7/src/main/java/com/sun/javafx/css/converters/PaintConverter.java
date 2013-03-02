/*     */ package com.sun.javafx.css.converters;
/*     */ 
/*     */ import com.sun.javafx.css.ParsedValue;
/*     */ import com.sun.javafx.css.Size;
/*     */ import com.sun.javafx.css.SizeUnits;
/*     */ import com.sun.javafx.css.StyleConverter;
/*     */ import javafx.scene.paint.CycleMethod;
/*     */ import javafx.scene.paint.LinearGradient;
/*     */ import javafx.scene.paint.Paint;
/*     */ import javafx.scene.paint.RadialGradient;
/*     */ import javafx.scene.paint.Stop;
/*     */ import javafx.scene.text.Font;
/*     */ 
/*     */ public final class PaintConverter extends StyleConverter<ParsedValue<?, Paint>, Paint>
/*     */ {
/*     */   public static PaintConverter getInstance()
/*     */   {
/*  51 */     return Holder.INSTANCE;
/*     */   }
/*     */ 
/*     */   public Paint convert(ParsedValue<ParsedValue<?, Paint>, Paint> paramParsedValue, Font paramFont)
/*     */   {
/*  60 */     Object localObject = paramParsedValue.getValue();
/*  61 */     if ((localObject instanceof Paint)) {
/*  62 */       return (Paint)localObject;
/*     */     }
/*  64 */     return (Paint)((ParsedValue)paramParsedValue.getValue()).convert(paramFont);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  69 */     return "PaintConverter";
/*     */   }
/*     */ 
/*     */   public static final class RadialGradientConverter extends StyleConverter<ParsedValue[], Paint>
/*     */   {
/*     */     public static RadialGradientConverter getInstance()
/*     */     {
/* 138 */       return PaintConverter.Holder.RADIAL_GRADIENT_INSTANCE;
/*     */     }
/*     */ 
/*     */     public Paint convert(ParsedValue<ParsedValue[], Paint> paramParsedValue, Font paramFont)
/*     */     {
/* 147 */       ParsedValue[] arrayOfParsedValue = (ParsedValue[])paramParsedValue.getValue();
/* 148 */       int i = 0;
/*     */ 
/* 154 */       Object localObject1 = arrayOfParsedValue[(i++)] != null ? (Size)arrayOfParsedValue[(i - 1)].convert(paramFont) : null;
/* 155 */       Object localObject2 = arrayOfParsedValue[(i++)] != null ? (Size)arrayOfParsedValue[(i - 1)].convert(paramFont) : null;
/* 156 */       Object localObject3 = arrayOfParsedValue[(i++)] != null ? (Size)arrayOfParsedValue[(i - 1)].convert(paramFont) : null;
/* 157 */       Object localObject4 = arrayOfParsedValue[(i++)] != null ? (Size)arrayOfParsedValue[(i - 1)].convert(paramFont) : null;
/* 158 */       Size localSize = (Size)arrayOfParsedValue[(i++)].convert(paramFont);
/* 159 */       boolean bool = localSize.getUnits().equals(SizeUnits.PERCENT);
/* 160 */       int j = localObject3 != null ? 0 : bool == localObject3.getUnits().equals(SizeUnits.PERCENT) ? 1 : 1;
/* 161 */       j = (j != 0) && (localObject4 != null) ? 0 : bool == localObject4.getUnits().equals(SizeUnits.PERCENT) ? 1 : 1;
/* 162 */       if (j == 0) {
/* 163 */         throw new IllegalArgumentException("units do not agree");
/*     */       }
/* 165 */       CycleMethod localCycleMethod = (CycleMethod)arrayOfParsedValue[(i++)].convert(paramFont);
/* 166 */       Stop[] arrayOfStop = new Stop[arrayOfParsedValue.length - i];
/* 167 */       for (int k = i; k < arrayOfParsedValue.length; k++) {
/* 168 */         arrayOfStop[(k - i)] = ((Stop)arrayOfParsedValue[k].convert(paramFont));
/*     */       }
/*     */ 
/* 172 */       double d = 0.0D;
/* 173 */       if (localObject1 != null) {
/* 174 */         d = localObject1.pixels(paramFont);
/* 175 */         if (localObject1.getUnits().equals(SizeUnits.PERCENT)) {
/* 176 */           d = d * 360.0D % 360.0D;
/*     */         }
/*     */       }
/* 179 */       return new RadialGradient(d, localObject2 != null ? localObject2.pixels() : 0.0D, localObject3 != null ? localObject3.pixels() : 0.0D, localObject4 != null ? localObject4.pixels() : 0.0D, localSize != null ? localSize.pixels() : 1.0D, bool, localCycleMethod, arrayOfStop);
/*     */     }
/*     */ 
/*     */     public String toString()
/*     */     {
/* 184 */       return "RadialGradientConverter";
/*     */     }
/*     */   }
/*     */ 
/*     */   public static final class LinearGradientConverter extends StyleConverter<ParsedValue[], Paint>
/*     */   {
/*     */     public static LinearGradientConverter getInstance()
/*     */     {
/* 105 */       return PaintConverter.Holder.LINEAR_GRADIENT_INSTANCE;
/*     */     }
/*     */ 
/*     */     public Paint convert(ParsedValue<ParsedValue[], Paint> paramParsedValue, Font paramFont)
/*     */     {
/* 114 */       ParsedValue[] arrayOfParsedValue = (ParsedValue[])paramParsedValue.getValue();
/* 115 */       int i = 0;
/* 116 */       Size localSize1 = (Size)arrayOfParsedValue[(i++)].convert(paramFont);
/* 117 */       Size localSize2 = (Size)arrayOfParsedValue[(i++)].convert(paramFont);
/* 118 */       Size localSize3 = (Size)arrayOfParsedValue[(i++)].convert(paramFont);
/* 119 */       Size localSize4 = (Size)arrayOfParsedValue[(i++)].convert(paramFont);
/* 120 */       boolean bool = (localSize1.getUnits() == SizeUnits.PERCENT) && (localSize1.getUnits() == localSize2.getUnits()) && (localSize1.getUnits() == localSize3.getUnits()) && (localSize1.getUnits() == localSize4.getUnits());
/* 121 */       CycleMethod localCycleMethod = (CycleMethod)arrayOfParsedValue[(i++)].convert(paramFont);
/* 122 */       Stop[] arrayOfStop = new Stop[arrayOfParsedValue.length - i];
/* 123 */       for (int j = i; j < arrayOfParsedValue.length; j++) {
/* 124 */         arrayOfStop[(j - i)] = ((Stop)arrayOfParsedValue[j].convert(paramFont));
/*     */       }
/* 126 */       return new LinearGradient(localSize1.pixels(paramFont), localSize2.pixels(paramFont), localSize3.pixels(paramFont), localSize4.pixels(paramFont), bool, localCycleMethod, arrayOfStop);
/*     */     }
/*     */ 
/*     */     public String toString()
/*     */     {
/* 131 */       return "LinearGradientConverter";
/*     */     }
/*     */   }
/*     */ 
/*     */   public static final class SequenceConverter extends StyleConverter<ParsedValue<?, Paint>[], Paint[]>
/*     */   {
/*     */     public static SequenceConverter getInstance()
/*     */     {
/*  78 */       return PaintConverter.Holder.SEQUENCE_INSTANCE;
/*     */     }
/*     */ 
/*     */     public Paint[] convert(ParsedValue<ParsedValue<?, Paint>[], Paint[]> paramParsedValue, Font paramFont)
/*     */     {
/*  87 */       ParsedValue[] arrayOfParsedValue = (ParsedValue[])paramParsedValue.getValue();
/*  88 */       Paint[] arrayOfPaint = new Paint[arrayOfParsedValue.length];
/*  89 */       for (int i = 0; i < arrayOfParsedValue.length; i++) {
/*  90 */         arrayOfPaint[i] = ((Paint)arrayOfParsedValue[i].convert(paramFont));
/*     */       }
/*  92 */       return arrayOfPaint;
/*     */     }
/*     */ 
/*     */     public String toString()
/*     */     {
/*  97 */       return "Paint.SequenceConverter";
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class Holder
/*     */   {
/*  44 */     static PaintConverter INSTANCE = new PaintConverter(null);
/*  45 */     static PaintConverter.SequenceConverter SEQUENCE_INSTANCE = new PaintConverter.SequenceConverter(null);
/*  46 */     static PaintConverter.LinearGradientConverter LINEAR_GRADIENT_INSTANCE = new PaintConverter.LinearGradientConverter(null);
/*  47 */     static PaintConverter.RadialGradientConverter RADIAL_GRADIENT_INSTANCE = new PaintConverter.RadialGradientConverter(null);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.css.converters.PaintConverter
 * JD-Core Version:    0.6.2
 */