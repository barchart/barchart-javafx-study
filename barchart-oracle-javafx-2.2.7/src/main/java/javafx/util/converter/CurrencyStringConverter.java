/*    */ package javafx.util.converter;
/*    */ 
/*    */ import java.text.DecimalFormat;
/*    */ import java.text.DecimalFormatSymbols;
/*    */ import java.text.NumberFormat;
/*    */ import java.util.Locale;
/*    */ 
/*    */ public class CurrencyStringConverter extends NumberStringConverter
/*    */ {
/*    */   public CurrencyStringConverter()
/*    */   {
/* 47 */     this(Locale.getDefault());
/*    */   }
/*    */ 
/*    */   public CurrencyStringConverter(Locale paramLocale) {
/* 51 */     this(paramLocale, null);
/*    */   }
/*    */ 
/*    */   public CurrencyStringConverter(String paramString) {
/* 55 */     this(Locale.getDefault(), paramString);
/*    */   }
/*    */ 
/*    */   public CurrencyStringConverter(Locale paramLocale, String paramString) {
/* 59 */     super(paramLocale, paramString, null);
/*    */   }
/*    */ 
/*    */   public CurrencyStringConverter(NumberFormat paramNumberFormat) {
/* 63 */     super(null, null, paramNumberFormat);
/*    */   }
/*    */ 
/*    */   protected NumberFormat getNumberFormat()
/*    */   {
/* 71 */     Locale localLocale = this.locale == null ? Locale.getDefault() : this.locale;
/*    */ 
/* 73 */     if (this.numberFormat != null)
/* 74 */       return this.numberFormat;
/* 75 */     if (this.pattern != null) {
/* 76 */       DecimalFormatSymbols localDecimalFormatSymbols = new DecimalFormatSymbols(localLocale);
/* 77 */       return new DecimalFormat(this.pattern, localDecimalFormatSymbols);
/*    */     }
/* 79 */     return NumberFormat.getCurrencyInstance(localLocale);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.util.converter.CurrencyStringConverter
 * JD-Core Version:    0.6.2
 */