/*    */ package javafx.util.converter;
/*    */ 
/*    */ import java.text.DateFormat;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Locale;
/*    */ 
/*    */ public class DateStringConverter extends DateTimeStringConverter
/*    */ {
/*    */   public DateStringConverter()
/*    */   {
/* 45 */     this(Locale.getDefault());
/*    */   }
/*    */ 
/*    */   public DateStringConverter(Locale paramLocale) {
/* 49 */     this(paramLocale, null);
/*    */   }
/*    */ 
/*    */   public DateStringConverter(String paramString) {
/* 53 */     this(Locale.getDefault(), paramString, null);
/*    */   }
/*    */ 
/*    */   public DateStringConverter(Locale paramLocale, String paramString) {
/* 57 */     this(paramLocale, paramString, null);
/*    */   }
/*    */ 
/*    */   public DateStringConverter(DateFormat paramDateFormat) {
/* 61 */     this(null, null, paramDateFormat);
/*    */   }
/*    */ 
/*    */   private DateStringConverter(Locale paramLocale, String paramString, DateFormat paramDateFormat) {
/* 65 */     super(paramLocale, paramString, paramDateFormat);
/*    */   }
/*    */ 
/*    */   protected DateFormat getDateFormat()
/*    */   {
/* 73 */     Locale localLocale = this.locale == null ? Locale.getDefault() : this.locale;
/*    */ 
/* 75 */     Object localObject = null;
/*    */ 
/* 77 */     if (this.dateFormat != null)
/* 78 */       return this.dateFormat;
/* 79 */     if (this.pattern != null)
/* 80 */       localObject = new SimpleDateFormat(this.pattern, localLocale);
/*    */     else {
/* 82 */       localObject = DateFormat.getDateInstance();
/*    */     }
/*    */ 
/* 85 */     ((DateFormat)localObject).setLenient(false);
/*    */ 
/* 87 */     return localObject;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.util.converter.DateStringConverter
 * JD-Core Version:    0.6.2
 */