/*     */ package com.sun.javafx.fxml.builder;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.util.AbstractMap;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import java.util.StringTokenizer;
/*     */ import javafx.scene.text.Font;
/*     */ import javafx.scene.text.FontPosture;
/*     */ import javafx.scene.text.FontWeight;
/*     */ import javafx.util.Builder;
/*     */ 
/*     */ public final class JavaFXFontBuilder extends AbstractMap<String, Object>
/*     */   implements Builder<Font>
/*     */ {
/*  24 */   private String name = null;
/*  25 */   private double size = 12.0D;
/*  26 */   private FontWeight weight = null;
/*  27 */   private FontPosture posture = null;
/*  28 */   private URL url = null;
/*     */ 
/*     */   public Font build()
/*     */   {
/*     */     Font localFont;
/*  34 */     if (this.url != null)
/*     */     {
/*  37 */       InputStream localInputStream = null;
/*     */       try {
/*  39 */         localInputStream = this.url.openStream();
/*  40 */         localFont = Font.loadFont(localInputStream, this.size);
/*     */       }
/*     */       catch (Exception localException2) {
/*  43 */         throw new RuntimeException("Load of font file failed from " + this.url, localException2);
/*     */       } finally {
/*     */         try {
/*  46 */           if (localInputStream != null)
/*  47 */             localInputStream.close();
/*     */         }
/*     */         catch (Exception localException3)
/*     */         {
/*  51 */           localException3.printStackTrace();
/*     */         }
/*     */       }
/*     */     }
/*  55 */     else if ((this.weight == null) && (this.posture == null)) {
/*  56 */       localFont = new Font(this.name, this.size);
/*     */     } else {
/*  58 */       if (this.weight == null) this.weight = FontWeight.NORMAL;
/*  59 */       if (this.posture == null) this.posture = FontPosture.REGULAR;
/*  60 */       localFont = Font.font(this.name, this.weight, this.posture, this.size);
/*     */     }
/*     */ 
/*  63 */     return localFont;
/*     */   }
/*     */ 
/*     */   public Object put(String paramString, Object paramObject)
/*     */   {
/*  68 */     if ("name".equals(paramString)) {
/*  69 */       if ((paramObject instanceof URL))
/*  70 */         this.url = ((URL)paramObject);
/*     */       else
/*  72 */         this.name = ((String)paramObject);
/*     */     }
/*  74 */     else if ("size".equals(paramString)) {
/*  75 */       this.size = Double.parseDouble((String)paramObject);
/*     */     }
/*     */     else
/*     */     {
/*     */       int i;
/*     */       StringTokenizer localStringTokenizer;
/*  76 */       if ("style".equals(paramString)) {
/*  77 */         String str1 = (String)paramObject;
/*  78 */         if ((str1 != null) && (str1.length() > 0)) {
/*  79 */           i = 0;
/*  80 */           for (localStringTokenizer = new StringTokenizer(str1, " "); localStringTokenizer.hasMoreTokens(); ) {
/*  81 */             String str2 = localStringTokenizer.nextToken();
/*     */             FontWeight localFontWeight;
/*  83 */             if ((i == 0) && ((localFontWeight = FontWeight.findByName(str2)) != null)) {
/*  84 */               this.weight = localFontWeight;
/*  85 */               i = 1;
/*     */             }
/*     */             else
/*     */             {
/*     */               FontPosture localFontPosture;
/*  89 */               if ((localFontPosture = FontPosture.findByName(str2)) != null)
/*  90 */                 this.posture = localFontPosture;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*  95 */       else if ("url".equals(paramString)) {
/*  96 */         if ((paramObject instanceof URL))
/*  97 */           this.url = ((URL)paramObject);
/*     */         else
/*     */           try {
/* 100 */             this.url = new URL(paramObject.toString());
/*     */           }
/*     */           catch (MalformedURLException localMalformedURLException) {
/* 103 */             throw new IllegalArgumentException("Invalid url " + paramObject.toString(), localMalformedURLException);
/*     */           }
/*     */       }
/*     */       else {
/* 107 */         throw new IllegalArgumentException("Unknown Font property: " + paramString);
/*     */       }
/*     */     }
/* 109 */     return null;
/*     */   }
/*     */ 
/*     */   public Set<Map.Entry<String, Object>> entrySet()
/*     */   {
/* 114 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.fxml.builder.JavaFXFontBuilder
 * JD-Core Version:    0.6.2
 */