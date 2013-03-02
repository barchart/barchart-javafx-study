/*    */ package com.sun.webpane.webkit.unicode;
/*    */ 
/*    */ import java.text.Normalizer;
/*    */ import java.text.Normalizer.Form;
/*    */ 
/*    */ public class TextNormalizer
/*    */ {
/*    */   public static final int FORM_NFC = 0;
/*    */   public static final int FORM_NFD = 1;
/*    */   public static final int FORM_NFKC = 2;
/*    */   public static final int FORM_NFKD = 3;
/*    */ 
/*    */   public static String normalize(String paramString, int paramInt)
/*    */   {
/*    */     Normalizer.Form localForm;
/* 18 */     switch (paramInt) { case 0:
/* 19 */       localForm = Normalizer.Form.NFC; break;
/*    */     case 1:
/* 20 */       localForm = Normalizer.Form.NFD; break;
/*    */     case 2:
/* 21 */       localForm = Normalizer.Form.NFKC; break;
/*    */     case 3:
/* 22 */       localForm = Normalizer.Form.NFKD; break;
/*    */     default:
/* 24 */       throw new IllegalArgumentException("invalid type: " + paramInt);
/*    */     }
/*    */ 
/* 27 */     return Normalizer.normalize(paramString, localForm);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.unicode.TextNormalizer
 * JD-Core Version:    0.6.2
 */