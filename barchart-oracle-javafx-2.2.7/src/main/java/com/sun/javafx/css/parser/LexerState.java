/*    */ package com.sun.javafx.css.parser;
/*    */ 
/*    */ class LexerState
/*    */ {
/*    */   private final int type;
/*    */   private final String name;
/*    */   private final Recognizer[] recognizers;
/*    */ 
/*    */   public boolean accepts(int paramInt)
/*    */   {
/* 38 */     int i = this.recognizers != null ? this.recognizers.length : 0;
/* 39 */     for (int j = 0; j < i; j++) {
/* 40 */       if (this.recognizers[j].recognize(paramInt)) return true;
/*    */     }
/* 42 */     return false;
/*    */   }
/*    */ 
/*    */   public int getType() {
/* 46 */     return this.type;
/*    */   }
/*    */ 
/*    */   public LexerState(int paramInt, String paramString, Recognizer paramRecognizer, Recognizer[] paramArrayOfRecognizer) {
/* 50 */     assert (paramString != null);
/* 51 */     this.type = paramInt;
/* 52 */     this.name = paramString;
/* 53 */     if (paramRecognizer != null) {
/* 54 */       int i = 1 + (paramArrayOfRecognizer != null ? paramArrayOfRecognizer.length : 0);
/* 55 */       this.recognizers = new Recognizer[i];
/* 56 */       this.recognizers[0] = paramRecognizer;
/* 57 */       for (int j = 1; j < this.recognizers.length; j++)
/* 58 */         this.recognizers[j] = paramArrayOfRecognizer[(j - 1)];
/*    */     }
/*    */     else {
/* 61 */       this.recognizers = null;
/*    */     }
/*    */   }
/*    */ 
/*    */   public LexerState(String paramString, Recognizer paramRecognizer, Recognizer[] paramArrayOfRecognizer) {
/* 66 */     this(0, paramString, paramRecognizer, paramArrayOfRecognizer);
/*    */   }
/*    */ 
/*    */   private LexerState() {
/* 70 */     this(0, "invalid", null, new Recognizer[0]);
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 78 */     return this.name;
/*    */   }
/*    */ 
/*    */   public boolean equals(Object paramObject) {
/* 82 */     if (this == paramObject) return true;
/* 83 */     return (paramObject instanceof LexerState) ? this.name.equals(((LexerState)paramObject).name) : false;
/*    */   }
/*    */ 
/*    */   public int hashCode()
/*    */   {
/* 88 */     return this.name.hashCode();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.css.parser.LexerState
 * JD-Core Version:    0.6.2
 */