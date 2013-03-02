/*    */ package javafx.util.converter;
/*    */ 
/*    */ import java.text.DateFormat;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Locale;
/*    */ 
/*    */ public class TimeStringConverter extends DateTimeStringConverter
/*    */ {
/*    */   public TimeStringConverter()
/*    */   {
/* 46 */     this(Locale.getDefault());
/*    */   }
/*    */ 
/*    */   public TimeStringConverter(Locale paramLocale) {
/* 50 */     this(paramLocale, null);
/*    */   }
/*    */ 
/*    */   public TimeStringConverter(String paramString) {
/* 54 */     this(Locale.getDefault(), paramString, null);
/*    */   }
/*    */ 
/*    */   public TimeStringConverter(Locale paramLocale, String paramString) {
/* 58 */     this(paramLocale, paramString, null);
/*    */   }
/*    */ 
/*    */   public TimeStringConverter(DateFormat paramDateFormat) {
/* 62 */     this(null, null, paramDateFormat);
/*    */   }
/*    */ 
/*    */   private TimeStringConverter(Locale paramLocale, String paramString, DateFormat paramDateFormat) {
/* 66 */     super(paramLocale, paramString, paramDateFormat);
/*    */   }
/*    */ 
/*    */   protected DateFormat getDateFormat()
/*    */   {
/* 74 */     Locale localLocale = this.locale == null ? Locale.getDefault() : this.locale;
/*    */ 
/* 76 */     Object localObject = null;
/*    */ 
/* 78 */     if (this.dateFormat != null)
/* 79 */       return this.dateFormat;
/* 80 */     if (this.pattern != null)
/* 81 */       localObject = new SimpleDateFormat(this.pattern, localLocale);
/*    */     else {
/* 83 */       localObject = DateFormat.getTimeInstance();
/*    */     }
/*    */ 
/* 86 */     ((DateFormat)localObject).setLenient(false);
/*    */ 
/* 88 */     return localObject;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.util.converter.TimeStringConverter
 * JD-Core Version:    0.6.2
 */