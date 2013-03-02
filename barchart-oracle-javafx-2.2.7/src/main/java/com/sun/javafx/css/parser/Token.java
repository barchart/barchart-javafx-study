/*     */ package com.sun.javafx.css.parser;
/*     */ 
/*     */ final class Token
/*     */ {
/*     */   static final int EOF = -1;
/*     */   static final int INVALID = 0;
/*     */   static final int SKIP = 1;
/*  35 */   static final Token EOF_TOKEN = new Token(-1, "EOF");
/*  36 */   static final Token INVALID_TOKEN = new Token(0, "INVALID");
/*  37 */   static final Token SKIP_TOKEN = new Token(1, "SKIP");
/*     */   private final String text;
/*     */   private int offset;
/*     */   private int line;
/*     */   private final int type;
/*     */ 
/*     */   Token(int paramInt1, String paramString, int paramInt2, int paramInt3)
/*     */   {
/*  40 */     this.type = paramInt1;
/*  41 */     this.text = paramString;
/*  42 */     this.line = paramInt2;
/*  43 */     this.offset = paramInt3;
/*     */   }
/*     */ 
/*     */   Token(int paramInt, String paramString) {
/*  47 */     this(paramInt, paramString, -1, -1);
/*     */   }
/*     */ 
/*     */   Token(int paramInt) {
/*  51 */     this(paramInt, null);
/*     */   }
/*     */ 
/*     */   private Token() {
/*  55 */     this(0, "INVALID");
/*     */   }
/*     */ 
/*     */   String getText() {
/*  59 */     return this.text;
/*     */   }
/*     */ 
/*     */   int getType() {
/*  63 */     return this.type;
/*     */   }
/*     */ 
/*     */   int getLine() {
/*  67 */     return this.line;
/*     */   }
/*     */ 
/*     */   void setLine(int paramInt) {
/*  71 */     this.line = paramInt;
/*     */   }
/*     */ 
/*     */   int getOffset() {
/*  75 */     return this.offset;
/*     */   }
/*     */ 
/*     */   void setOffset(int paramInt) {
/*  79 */     this.offset = paramInt;
/*     */   }
/*     */ 
/*     */   public String toString() {
/*  83 */     StringBuilder localStringBuilder = new StringBuilder();
/*  84 */     localStringBuilder.append('[').append(this.line).append(',').append(this.offset).append(']').append(',').append(this.text).append(",<").append(this.type).append('>');
/*     */ 
/*  86 */     return localStringBuilder.toString();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/*  91 */     if (this == paramObject) return true;
/*  92 */     if ((paramObject == null) || (getClass() != paramObject.getClass()))
/*     */     {
/*  94 */       return false;
/*     */     }
/*  96 */     Token localToken = (Token)paramObject;
/*  97 */     if (this.type != localToken.type) {
/*  98 */       return false;
/*     */     }
/* 100 */     if (this.text == null ? localToken.text != null : !this.text.equals(localToken.text)) {
/* 101 */       return false;
/*     */     }
/* 103 */     return true;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 108 */     int i = 7;
/* 109 */     i = 67 * i + this.type;
/* 110 */     i = 67 * i + (this.text != null ? this.text.hashCode() : 0);
/* 111 */     return i;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.css.parser.Token
 * JD-Core Version:    0.6.2
 */