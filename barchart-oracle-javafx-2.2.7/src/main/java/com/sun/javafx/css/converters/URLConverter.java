/*     */ package com.sun.javafx.css.converters;
/*     */ 
/*     */ import com.sun.javafx.Utils;
/*     */ import com.sun.javafx.css.ParsedValue;
/*     */ import com.sun.javafx.css.StyleConverter;
/*     */ import java.io.PrintStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import javafx.scene.text.Font;
/*     */ 
/*     */ public final class URLConverter extends StyleConverter<ParsedValue[], String>
/*     */ {
/*     */   public static URLConverter getInstance()
/*     */   {
/*  47 */     return Holder.INSTANCE;
/*     */   }
/*     */ 
/*     */   public String convert(ParsedValue<ParsedValue[], String> paramParsedValue, Font paramFont)
/*     */   {
/*  56 */     String str1 = null;
/*     */     try {
/*  58 */       ParsedValue[] arrayOfParsedValue = (ParsedValue[])paramParsedValue.getValue();
/*     */ 
/*  60 */       String str2 = StringConverter.getInstance().convert(arrayOfParsedValue[0], paramFont);
/*  61 */       if (str2.startsWith("url("))
/*  62 */         str2 = Utils.stripQuotes(str2.substring(4, str2.length() - 1));
/*     */       else {
/*  64 */         str2 = Utils.stripQuotes(str2);
/*     */       }
/*  66 */       URL localURL1 = (URL)arrayOfParsedValue[1].getValue();
/*  67 */       URL localURL2 = null;
/*  68 */       if (localURL1 == null) {
/*     */         try {
/*  70 */           localURL2 = new URL(str2);
/*     */         }
/*     */         catch (MalformedURLException localMalformedURLException2)
/*     */         {
/*  74 */           ClassLoader localClassLoader = Thread.currentThread().getContextClassLoader();
/*  75 */           localURL2 = localClassLoader.getResource(str2);
/*     */         }
/*     */       }
/*     */       else {
/*  79 */         localURL2 = new URL(localURL1, str2);
/*     */       }
/*  81 */       if (localURL2 != null) str1 = localURL2.toExternalForm(); 
/*     */     }
/*  83 */     catch (MalformedURLException localMalformedURLException1) { System.err.println("caught " + localMalformedURLException1 + " in 'URLType.convert'"); }
/*     */ 
/*  85 */     return str1;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  90 */     return "URLType";
/*     */   }
/*     */ 
/*     */   public static final class SequenceConverter extends StyleConverter<ParsedValue<ParsedValue[], String>[], String[]>
/*     */   {
/*     */     public static SequenceConverter getInstance() {
/*  96 */       return URLConverter.Holder.SEQUENCE_INSTANCE;
/*     */     }
/*     */ 
/*     */     public String[] convert(ParsedValue<ParsedValue<ParsedValue[], String>[], String[]> paramParsedValue, Font paramFont)
/*     */     {
/* 105 */       ParsedValue[] arrayOfParsedValue = (ParsedValue[])paramParsedValue.getValue();
/* 106 */       String[] arrayOfString = new String[arrayOfParsedValue.length];
/* 107 */       for (int i = 0; i < arrayOfParsedValue.length; i++) {
/* 108 */         arrayOfString[i] = URLConverter.getInstance().convert(arrayOfParsedValue[i], paramFont);
/*     */       }
/* 110 */       return arrayOfString;
/*     */     }
/*     */ 
/*     */     public String toString()
/*     */     {
/* 115 */       return "URLSeqType";
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class Holder
/*     */   {
/*  42 */     static URLConverter INSTANCE = new URLConverter(null);
/*  43 */     static URLConverter.SequenceConverter SEQUENCE_INSTANCE = new URLConverter.SequenceConverter(null);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.css.converters.URLConverter
 * JD-Core Version:    0.6.2
 */