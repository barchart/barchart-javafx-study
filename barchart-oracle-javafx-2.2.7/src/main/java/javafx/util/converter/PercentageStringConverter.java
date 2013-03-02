/*    */ package javafx.util.converter;
/*    */ 
/*    */ import java.text.NumberFormat;
/*    */ import java.util.Locale;
/*    */ 
/*    */ public class PercentageStringConverter extends NumberStringConverter
/*    */ {
/*    */   public PercentageStringConverter()
/*    */   {
/* 46 */     this(Locale.getDefault());
/*    */   }
/*    */ 
/*    */   public PercentageStringConverter(Locale paramLocale) {
/* 50 */     super(paramLocale, null, null);
/*    */   }
/*    */ 
/*    */   public PercentageStringConverter(NumberFormat paramNumberFormat) {
/* 54 */     super(null, null, paramNumberFormat);
/*    */   }
/*    */ 
/*    */   public NumberFormat getNumberFormat()
/*    */   {
/* 61 */     Locale localLocale = this.locale == null ? Locale.getDefault() : this.locale;
/*    */ 
/* 63 */     if (this.numberFormat != null) {
/* 64 */       return this.numberFormat;
/*    */     }
/* 66 */     return NumberFormat.getPercentInstance(localLocale);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.util.converter.PercentageStringConverter
 * JD-Core Version:    0.6.2
 */