/*     */ package javafx.util.converter;
/*     */ 
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.DecimalFormatSymbols;
/*     */ import java.text.NumberFormat;
/*     */ import java.text.ParseException;
/*     */ import java.util.Locale;
/*     */ import javafx.util.StringConverter;
/*     */ 
/*     */ public class NumberStringConverter extends StringConverter<Number>
/*     */ {
/*     */   final Locale locale;
/*     */   final String pattern;
/*     */   final NumberFormat numberFormat;
/*     */ 
/*     */   public NumberStringConverter()
/*     */   {
/*  49 */     this(Locale.getDefault());
/*     */   }
/*     */ 
/*     */   public NumberStringConverter(Locale paramLocale) {
/*  53 */     this(paramLocale, null);
/*     */   }
/*     */ 
/*     */   public NumberStringConverter(String paramString) {
/*  57 */     this(Locale.getDefault(), paramString);
/*     */   }
/*     */ 
/*     */   public NumberStringConverter(Locale paramLocale, String paramString) {
/*  61 */     this(paramLocale, paramString, null);
/*     */   }
/*     */ 
/*     */   public NumberStringConverter(NumberFormat paramNumberFormat) {
/*  65 */     this(null, null, paramNumberFormat);
/*     */   }
/*     */ 
/*     */   NumberStringConverter(Locale paramLocale, String paramString, NumberFormat paramNumberFormat) {
/*  69 */     this.locale = paramLocale;
/*  70 */     this.pattern = paramString;
/*  71 */     this.numberFormat = paramNumberFormat;
/*     */   }
/*     */ 
/*     */   public Number fromString(String paramString)
/*     */   {
/*     */     try
/*     */     {
/*  80 */       if (paramString == null) {
/*  81 */         return null;
/*     */       }
/*     */ 
/*  84 */       paramString = paramString.trim();
/*     */ 
/*  86 */       if (paramString.length() < 1) {
/*  87 */         return null;
/*     */       }
/*     */ 
/*  91 */       NumberFormat localNumberFormat = getNumberFormat();
/*     */ 
/*  94 */       return localNumberFormat.parse(paramString);
/*     */     } catch (ParseException localParseException) {
/*  96 */       throw new RuntimeException(localParseException);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String toString(Number paramNumber)
/*     */   {
/* 103 */     if (paramNumber == null) {
/* 104 */       return "";
/*     */     }
/*     */ 
/* 108 */     NumberFormat localNumberFormat = getNumberFormat();
/*     */ 
/* 111 */     return localNumberFormat.format(paramNumber);
/*     */   }
/*     */ 
/*     */   protected NumberFormat getNumberFormat()
/*     */   {
/* 119 */     Locale localLocale = this.locale == null ? Locale.getDefault() : this.locale;
/*     */ 
/* 121 */     if (this.numberFormat != null)
/* 122 */       return this.numberFormat;
/* 123 */     if (this.pattern != null) {
/* 124 */       DecimalFormatSymbols localDecimalFormatSymbols = new DecimalFormatSymbols(localLocale);
/* 125 */       return new DecimalFormat(this.pattern, localDecimalFormatSymbols);
/*     */     }
/* 127 */     return NumberFormat.getNumberInstance(localLocale);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.util.converter.NumberStringConverter
 * JD-Core Version:    0.6.2
 */