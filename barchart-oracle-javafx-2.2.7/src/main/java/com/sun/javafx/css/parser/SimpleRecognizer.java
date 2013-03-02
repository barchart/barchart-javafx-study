/*    */ package com.sun.javafx.css.parser;
/*    */ 
/*    */ class SimpleRecognizer
/*    */   implements Recognizer
/*    */ {
/*    */   private final int[] recognizedChars;
/*    */ 
/*    */   public boolean recognize(int paramInt)
/*    */   {
/* 32 */     for (int i = 0; i < this.recognizedChars.length; i++)
/* 33 */       if (this.recognizedChars[i] == paramInt) return true;
/* 34 */     return false;
/*    */   }
/*    */ 
/*    */   public SimpleRecognizer(int paramInt, int[] paramArrayOfInt) {
/* 38 */     int i = 1 + (paramArrayOfInt != null ? paramArrayOfInt.length : 0);
/* 39 */     this.recognizedChars = new int[i];
/* 40 */     this.recognizedChars[0] = paramInt;
/* 41 */     for (int j = 1; j < i; j++) this.recognizedChars[j] = paramArrayOfInt[(j - 1)]; 
/*    */   }
/*    */ 
/*    */   private SimpleRecognizer()
/*    */   {
/* 45 */     this.recognizedChars = null;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.css.parser.SimpleRecognizer
 * JD-Core Version:    0.6.2
 */