/*     */ package com.sun.media.jfxmedia.track;
/*     */ 
/*     */ import com.sun.media.jfxmedia.locator.Locator;
/*     */ 
/*     */ public abstract class Track
/*     */ {
/*     */   private Locator locator;
/*     */   private String name;
/*     */   private Encoding encoding;
/*     */ 
/*     */   protected Track(Locator locator, String name, Encoding encoding)
/*     */   {
/* 115 */     if (locator == null)
/* 116 */       throw new IllegalArgumentException("trackLocator == null!");
/* 117 */     if (name == null)
/* 118 */       throw new IllegalArgumentException("name == null!");
/* 119 */     if (encoding == null) {
/* 120 */       throw new IllegalArgumentException("encoding == null!");
/*     */     }
/*     */ 
/* 123 */     this.locator = locator;
/* 124 */     this.encoding = encoding;
/* 125 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public Locator getLocator()
/*     */   {
/* 134 */     return this.locator;
/*     */   }
/*     */ 
/*     */   public Encoding getEncodingType()
/*     */   {
/* 144 */     return this.encoding;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 154 */     return this.name;
/*     */   }
/*     */ 
/*     */   public static enum Encoding
/*     */   {
/*  73 */     NONE, 
/*     */ 
/*  76 */     PCM, 
/*  77 */     MPEG1AUDIO, 
/*  78 */     MPEG1LAYER3, 
/*  79 */     AAC, 
/*     */ 
/*  82 */     H264, 
/*     */ 
/*  85 */     VP6, 
/*     */ 
/*  88 */     CUSTOM;
/*     */ 
/*     */     public static Encoding toEncoding(int ordinal) {
/*  91 */       for (Encoding value : values()) {
/*  92 */         if (value.ordinal() == ordinal) {
/*  93 */           return value;
/*     */         }
/*     */       }
/*  96 */       return NONE;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.media.jfxmedia.track.Track
 * JD-Core Version:    0.6.2
 */