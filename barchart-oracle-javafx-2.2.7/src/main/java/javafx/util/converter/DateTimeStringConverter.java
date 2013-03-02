/*     */ package javafx.util.converter;
/*     */ 
/*     */ import java.text.DateFormat;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.Locale;
/*     */ import javafx.util.StringConverter;
/*     */ 
/*     */ public class DateTimeStringConverter extends StringConverter<Date>
/*     */ {
/*     */   protected final Locale locale;
/*     */   protected final String pattern;
/*     */   protected final DateFormat dateFormat;
/*     */ 
/*     */   public DateTimeStringConverter()
/*     */   {
/*  53 */     this(Locale.getDefault());
/*     */   }
/*     */ 
/*     */   public DateTimeStringConverter(Locale paramLocale) {
/*  57 */     this(paramLocale, null);
/*     */   }
/*     */ 
/*     */   public DateTimeStringConverter(String paramString) {
/*  61 */     this(Locale.getDefault(), paramString, null);
/*     */   }
/*     */ 
/*     */   public DateTimeStringConverter(Locale paramLocale, String paramString) {
/*  65 */     this(paramLocale, paramString, null);
/*     */   }
/*     */ 
/*     */   public DateTimeStringConverter(DateFormat paramDateFormat) {
/*  69 */     this(null, null, paramDateFormat);
/*     */   }
/*     */ 
/*     */   DateTimeStringConverter(Locale paramLocale, String paramString, DateFormat paramDateFormat) {
/*  73 */     this.locale = paramLocale;
/*  74 */     this.pattern = paramString;
/*  75 */     this.dateFormat = paramDateFormat;
/*     */   }
/*     */ 
/*     */   public Date fromString(String paramString)
/*     */   {
/*     */     try
/*     */     {
/*  85 */       if (paramString == null) {
/*  86 */         return null;
/*     */       }
/*     */ 
/*  89 */       paramString = paramString.trim();
/*     */ 
/*  91 */       if (paramString.length() < 1) {
/*  92 */         return null;
/*     */       }
/*     */ 
/*  96 */       DateFormat localDateFormat = getDateFormat();
/*     */ 
/*  99 */       return localDateFormat.parse(paramString);
/*     */     } catch (ParseException localParseException) {
/* 101 */       throw new RuntimeException(localParseException);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String toString(Date paramDate)
/*     */   {
/* 108 */     if (paramDate == null) {
/* 109 */       return "";
/*     */     }
/*     */ 
/* 113 */     DateFormat localDateFormat = getDateFormat();
/*     */ 
/* 116 */     return localDateFormat.format(paramDate);
/*     */   }
/*     */ 
/*     */   protected DateFormat getDateFormat()
/*     */   {
/* 126 */     Locale localLocale = this.locale == null ? Locale.getDefault() : this.locale;
/*     */ 
/* 128 */     Object localObject = null;
/*     */ 
/* 130 */     if (this.dateFormat != null)
/* 131 */       return this.dateFormat;
/* 132 */     if (this.pattern != null)
/* 133 */       localObject = new SimpleDateFormat(this.pattern, localLocale);
/*     */     else {
/* 135 */       localObject = DateFormat.getDateTimeInstance();
/*     */     }
/*     */ 
/* 138 */     ((DateFormat)localObject).setLenient(false);
/*     */ 
/* 140 */     return localObject;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.util.converter.DateTimeStringConverter
 * JD-Core Version:    0.6.2
 */