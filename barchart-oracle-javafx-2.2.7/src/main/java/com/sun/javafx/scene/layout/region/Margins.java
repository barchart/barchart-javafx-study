/*     */ package com.sun.javafx.scene.layout.region;
/*     */ 
/*     */ import com.sun.javafx.css.ParsedValue;
/*     */ import com.sun.javafx.css.Size;
/*     */ import com.sun.javafx.css.SizeUnits;
/*     */ import com.sun.javafx.css.StyleConverter;
/*     */ import java.io.PrintStream;
/*     */ import javafx.scene.text.Font;
/*     */ 
/*     */ public class Margins
/*     */ {
/*     */   private final double top;
/*     */   private final double right;
/*     */   private final double bottom;
/*     */   private final double left;
/*     */   private final boolean proportional;
/*     */ 
/*     */   public final double getTop()
/*     */   {
/*  48 */     return this.top;
/*     */   }
/*     */   public final double getRight() {
/*  51 */     return this.right;
/*     */   }
/*     */   public final double getBottom() {
/*  54 */     return this.bottom;
/*     */   }
/*     */   public final double getLeft() {
/*  57 */     return this.left;
/*     */   }
/*     */   public final boolean isProportional() {
/*  60 */     return this.proportional;
/*     */   }
/*     */   public Margins(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, boolean paramBoolean) {
/*  63 */     this.top = paramDouble1;
/*  64 */     this.right = paramDouble2;
/*  65 */     this.bottom = paramDouble3;
/*  66 */     this.left = paramDouble4;
/*  67 */     this.proportional = paramBoolean;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  72 */     return "top: " + this.top + "\nright: " + this.right + "\nbottom: " + this.bottom + "\nleft: " + this.left;
/*     */   }
/*     */ 
/*     */   public static final class SequenceConverter extends StyleConverter<ParsedValue<ParsedValue<?, Size>[], Margins>[], Margins[]>
/*     */   {
/*     */     public static SequenceConverter getInstance()
/*     */     {
/* 120 */       return Margins.Holder.SEQUENCE_CONVERTER_INSTANCE;
/*     */     }
/*     */ 
/*     */     public Margins[] convert(ParsedValue<ParsedValue<ParsedValue<?, Size>[], Margins>[], Margins[]> paramParsedValue, Font paramFont)
/*     */     {
/* 129 */       ParsedValue[] arrayOfParsedValue = (ParsedValue[])paramParsedValue.getValue();
/* 130 */       Margins[] arrayOfMargins = new Margins[arrayOfParsedValue.length];
/* 131 */       for (int i = 0; i < arrayOfParsedValue.length; i++) {
/* 132 */         arrayOfMargins[i] = Margins.Converter.getInstance().convert(arrayOfParsedValue[i], paramFont);
/*     */       }
/* 134 */       return arrayOfMargins;
/*     */     }
/*     */ 
/*     */     public String toString()
/*     */     {
/* 139 */       return "MarginsSequenceConverter";
/*     */     }
/*     */   }
/*     */ 
/*     */   public static final class Converter extends StyleConverter<ParsedValue<?, Size>[], Margins>
/*     */   {
/*     */     public static Converter getInstance()
/*     */     {
/*  81 */       return Margins.Holder.CONVERTER_INSTANCE;
/*     */     }
/*     */ 
/*     */     public Margins convert(ParsedValue<ParsedValue<?, Size>[], Margins> paramParsedValue, Font paramFont)
/*     */     {
/*  90 */       ParsedValue[] arrayOfParsedValue = (ParsedValue[])paramParsedValue.getValue();
/*  91 */       Size localSize1 = arrayOfParsedValue.length > 0 ? (Size)arrayOfParsedValue[0].convert(paramFont) : new Size(0.0D, SizeUnits.PX);
/*  92 */       Size localSize2 = arrayOfParsedValue.length > 1 ? (Size)arrayOfParsedValue[1].convert(paramFont) : localSize1;
/*  93 */       Size localSize3 = arrayOfParsedValue.length > 2 ? (Size)arrayOfParsedValue[2].convert(paramFont) : localSize1;
/*  94 */       Size localSize4 = arrayOfParsedValue.length > 3 ? (Size)arrayOfParsedValue[3].convert(paramFont) : localSize2;
/*  95 */       boolean bool = false;
/*  96 */       if ((localSize1.getUnits() == localSize2.getUnits()) && (localSize1.getUnits() == localSize3.getUnits()) && (localSize1.getUnits() == localSize4.getUnits()))
/*  97 */         bool = localSize1.getUnits() == SizeUnits.PERCENT;
/*     */       else {
/*  99 */         System.err.println("units do not match");
/*     */       }
/* 101 */       double d1 = localSize1.pixels(paramFont);
/* 102 */       double d2 = localSize2.pixels(paramFont);
/* 103 */       double d3 = localSize3.pixels(paramFont);
/* 104 */       double d4 = localSize4.pixels(paramFont);
/* 105 */       return new Margins(d1, d2, d3, d4, bool);
/*     */     }
/*     */ 
/*     */     public String toString()
/*     */     {
/* 110 */       return "MarginsConverter";
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class Holder
/*     */   {
/*  43 */     static Margins.Converter CONVERTER_INSTANCE = new Margins.Converter(null);
/*  44 */     static Margins.SequenceConverter SEQUENCE_CONVERTER_INSTANCE = new Margins.SequenceConverter(null);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.layout.region.Margins
 * JD-Core Version:    0.6.2
 */