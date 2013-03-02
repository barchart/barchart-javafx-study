/*    */ package com.sun.javafx.font;
/*    */ 
/*    */ import com.sun.javafx.PlatformUtil;
/*    */ import com.sun.javafx.geom.transform.BaseTransform;
/*    */ import com.sun.t2k.T2KFontStrike;
/*    */ 
/*    */ public class PrismFontUtils
/*    */ {
/* 15 */   private static float lcdContrast = -1.0F;
/*    */ 
/*    */   public static float getLCDContrast() {
/* 18 */     if (lcdContrast == -1.0F) {
/* 19 */       if (PlatformUtil.isWindows())
/* 20 */         lcdContrast = T2KFontStrike.getLCDContrast() / 1000.0F;
/*    */       else {
/* 22 */         lcdContrast = 1.3F;
/*    */       }
/*    */     }
/* 25 */     return lcdContrast;
/*    */   }
/*    */ 
/*    */   static FontStrike.Metrics getFontMetrics(PGFont paramPGFont) {
/* 29 */     FontStrike localFontStrike = paramPGFont.getStrike(BaseTransform.IDENTITY_TRANSFORM, 0);
/*    */ 
/* 31 */     return localFontStrike.getMetrics();
/*    */   }
/*    */ 
/*    */   static double computeStringWidth(PGFont paramPGFont, String paramString) {
/* 35 */     if ((paramString == null) || (paramString.equals(""))) return 0.0D;
/* 36 */     FontStrike localFontStrike = paramPGFont.getStrike(BaseTransform.IDENTITY_TRANSFORM, 0);
/*    */ 
/* 38 */     return localFontStrike.getStringWidth(paramString);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.font.PrismFontUtils
 * JD-Core Version:    0.6.2
 */