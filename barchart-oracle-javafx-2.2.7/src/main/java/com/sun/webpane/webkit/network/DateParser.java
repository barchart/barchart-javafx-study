/*     */ package com.sun.webpane.webkit.network;
/*     */ 
/*     */ import java.text.ParseException;
/*     */ import java.util.Calendar;
/*     */ import java.util.Collections;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.TimeZone;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ final class DateParser
/*     */ {
/*  24 */   private static final Logger logger = Logger.getLogger(DateParser.class.getName());
/*     */ 
/*  27 */   private static final Pattern DELIMITER_PATTERN = Pattern.compile("[\\x09\\x20-\\x2F\\x3B-\\x40\\x5B-\\x60\\x7B-\\x7E]+");
/*     */ 
/*  29 */   private static final Pattern TIME_PATTERN = Pattern.compile("(\\d{1,2}):(\\d{1,2}):(\\d{1,2})(?:[^\\d].*)*");
/*     */ 
/*  31 */   private static final Pattern DAY_OF_MONTH_PATTERN = Pattern.compile("(\\d{1,2})(?:[^\\d].*)*");
/*     */ 
/*  33 */   private static final Pattern YEAR_PATTERN = Pattern.compile("(\\d{2,4})(?:[^\\d].*)*");
/*     */ 
/*  50 */   private static final Map<String, Integer> MONTH_MAP = Collections.unmodifiableMap(localHashMap);
/*     */ 
/*     */   private DateParser()
/*     */   {
/*  58 */     throw new AssertionError();
/*     */   }
/*     */ 
/*     */   static long parse(String paramString)
/*     */     throws ParseException
/*     */   {
/*  70 */     logger.log(Level.FINEST, "date: [{0}]", paramString);
/*     */ 
/*  72 */     Object localObject1 = null;
/*  73 */     Object localObject2 = null;
/*  74 */     Object localObject3 = null;
/*  75 */     Object localObject4 = null;
/*  76 */     String[] arrayOfString = DELIMITER_PATTERN.split(paramString, 0);
/*  77 */     for (String str : arrayOfString)
/*  78 */       if (str.length() != 0)
/*     */       {
/*     */         Time localTime;
/*  83 */         if ((localObject1 == null) && ((localTime = parseTime(str)) != null)) {
/*  84 */           localObject1 = localTime;
/*     */         }
/*     */         else
/*     */         {
/*     */           Integer localInteger1;
/*  89 */           if ((localObject2 == null) && ((localInteger1 = parseDayOfMonth(str)) != null))
/*     */           {
/*  92 */             localObject2 = localInteger1;
/*     */           }
/*     */           else
/*     */           {
/*     */             Integer localInteger2;
/*  97 */             if ((localObject3 == null) && ((localInteger2 = parseMonth(str)) != null)) {
/*  98 */               localObject3 = localInteger2;
/*     */             }
/*     */             else
/*     */             {
/*     */               Integer localInteger3;
/* 103 */               if ((localObject4 == null) && ((localInteger3 = parseYear(str)) != null))
/* 104 */                 localObject4 = localInteger3;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 109 */     if (localObject4 != null) {
/* 110 */       if ((((Integer)localObject4).intValue() >= 70) && (((Integer)localObject4).intValue() <= 99))
/* 111 */         localObject4 = Integer.valueOf(((Integer)localObject4).intValue() + 1900);
/* 112 */       else if ((((Integer)localObject4).intValue() >= 0) && (((Integer)localObject4).intValue() <= 69)) {
/* 113 */         localObject4 = Integer.valueOf(((Integer)localObject4).intValue() + 2000);
/*     */       }
/*     */     }
/*     */ 
/* 117 */     if ((localObject1 == null) || (localObject2 == null) || (localObject3 == null) || (localObject4 == null) || (localObject2.intValue() < 1) || (localObject2.intValue() > 31) || (((Integer)localObject4).intValue() < 1601) || (localObject1.hour > 23) || (localObject1.minute > 59) || (localObject1.second > 59))
/*     */     {
/* 124 */       throw new ParseException("Error parsing date", 0);
/*     */     }
/*     */ 
/* 127 */     ??? = Calendar.getInstance(TimeZone.getTimeZone("UTC"), Locale.US);
/*     */ 
/* 129 */     ((Calendar)???).setLenient(false);
/* 130 */     ((Calendar)???).clear();
/* 131 */     ((Calendar)???).set(((Integer)localObject4).intValue(), localObject3.intValue(), localObject2.intValue(), localObject1.hour, localObject1.minute, localObject1.second);
/*     */     try
/*     */     {
/* 135 */       long l = ((Calendar)???).getTimeInMillis();
/* 136 */       if (logger.isLoggable(Level.FINEST)) {
/* 137 */         logger.log(Level.FINEST, "result: [{0}]", new Date(l).toString());
/*     */       }
/*     */ 
/* 140 */       return l;
/*     */     } catch (Exception localException) {
/* 142 */       ParseException localParseException = new ParseException("Error parsing date", 0);
/* 143 */       localParseException.initCause(localException);
/* 144 */       throw localParseException;
/*     */     }
/*     */   }
/*     */ 
/*     */   private static Time parseTime(String paramString)
/*     */   {
/* 152 */     Matcher localMatcher = TIME_PATTERN.matcher(paramString);
/* 153 */     if (localMatcher.matches()) {
/* 154 */       return new Time(Integer.parseInt(localMatcher.group(1)), Integer.parseInt(localMatcher.group(2)), Integer.parseInt(localMatcher.group(3)));
/*     */     }
/*     */ 
/* 159 */     return null;
/*     */   }
/*     */ 
/*     */   private static Integer parseDayOfMonth(String paramString)
/*     */   {
/* 182 */     Matcher localMatcher = DAY_OF_MONTH_PATTERN.matcher(paramString);
/* 183 */     if (localMatcher.matches()) {
/* 184 */       return Integer.valueOf(Integer.parseInt(localMatcher.group(1)));
/*     */     }
/* 186 */     return null;
/*     */   }
/*     */ 
/*     */   private static Integer parseMonth(String paramString)
/*     */   {
/* 194 */     if (paramString.length() >= 3) {
/* 195 */       return (Integer)MONTH_MAP.get(paramString.substring(0, 3).toLowerCase());
/*     */     }
/* 197 */     return null;
/*     */   }
/*     */ 
/*     */   private static Integer parseYear(String paramString)
/*     */   {
/* 205 */     Matcher localMatcher = YEAR_PATTERN.matcher(paramString);
/* 206 */     if (localMatcher.matches()) {
/* 207 */       return Integer.valueOf(Integer.parseInt(localMatcher.group(1)));
/*     */     }
/* 209 */     return null;
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  37 */     HashMap localHashMap = new HashMap(12);
/*  38 */     localHashMap.put("jan", Integer.valueOf(0));
/*  39 */     localHashMap.put("feb", Integer.valueOf(1));
/*  40 */     localHashMap.put("mar", Integer.valueOf(2));
/*  41 */     localHashMap.put("apr", Integer.valueOf(3));
/*  42 */     localHashMap.put("may", Integer.valueOf(4));
/*  43 */     localHashMap.put("jun", Integer.valueOf(5));
/*  44 */     localHashMap.put("jul", Integer.valueOf(6));
/*  45 */     localHashMap.put("aug", Integer.valueOf(7));
/*  46 */     localHashMap.put("sep", Integer.valueOf(8));
/*  47 */     localHashMap.put("oct", Integer.valueOf(9));
/*  48 */     localHashMap.put("nov", Integer.valueOf(10));
/*  49 */     localHashMap.put("dec", Integer.valueOf(11));
/*     */   }
/*     */ 
/*     */   private static class Time
/*     */   {
/*     */     private final int hour;
/*     */     private final int minute;
/*     */     private final int second;
/*     */ 
/*     */     public Time(int paramInt1, int paramInt2, int paramInt3)
/*     */     {
/* 172 */       this.hour = paramInt1;
/* 173 */       this.minute = paramInt2;
/* 174 */       this.second = paramInt3;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.network.DateParser
 * JD-Core Version:    0.6.2
 */