/*     */ package com.sun.javafx.scene.text;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.net.URL;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Properties;
/*     */ 
/*     */ public class FontManager
/*     */ {
/*     */   private static FontManager instance;
/*  65 */   private Properties map = new Properties();
/*     */ 
/*     */   public static FontManager getInstance()
/*     */   {
/*  61 */     if (instance == null) instance = new FontManager();
/*  62 */     return instance;
/*     */   }
/*     */ 
/*     */   private FontManager()
/*     */   {
/*  68 */     loadEmbeddedFontDefinitions();
/*     */   }
/*     */ 
/*     */   void loadEmbeddedFontDefinitions()
/*     */   {
/*  74 */     URL localURL = FontManager.class.getResource("/META-INF/fonts.mf");
/*  75 */     if (localURL == null) return;
/*     */ 
/*     */     try
/*     */     {
/*  79 */       InputStream localInputStream = localURL.openStream();
/*  80 */       this.map.load(localInputStream);
/*  81 */       localInputStream.close();
/*     */     } catch (Exception localException) {
/*  83 */       localException.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public String findPathByName(String paramString)
/*     */   {
/*  96 */     return this.map.getProperty(paramString);
/*     */   }
/*     */ 
/*     */   public String[] getAllPaths()
/*     */   {
/* 103 */     if (this.map.size() == 0) return new String[0];
/*     */ 
/* 105 */     String[] arrayOfString = new String[this.map.size()];
/* 106 */     Enumeration localEnumeration = this.map.elements();
/* 107 */     int i = 0;
/* 108 */     while (localEnumeration.hasMoreElements()) {
/* 109 */       arrayOfString[(i++)] = ((String)localEnumeration.nextElement());
/*     */     }
/* 111 */     return arrayOfString;
/*     */   }
/*     */ 
/*     */   public String[] getAllNames() {
/* 115 */     if (this.map.size() == 0) return new String[0];
/*     */ 
/* 117 */     String[] arrayOfString = new String[this.map.size()];
/* 118 */     Enumeration localEnumeration = this.map.keys();
/* 119 */     int i = 0;
/* 120 */     while (localEnumeration.hasMoreElements()) {
/* 121 */       arrayOfString[(i++)] = ((String)localEnumeration.nextElement());
/*     */     }
/* 123 */     return arrayOfString;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.text.FontManager
 * JD-Core Version:    0.6.2
 */