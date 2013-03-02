/*    */ package javafx.scene.paint;
/*    */ 
/*    */ public abstract class Paint
/*    */ {
/*    */   @Deprecated
/*    */   public abstract Object impl_getPlatformPaint();
/*    */ 
/*    */   public static Paint valueOf(String paramString)
/*    */   {
/* 57 */     if (paramString == null) {
/* 58 */       throw new NullPointerException("paint must be specified");
/*    */     }
/*    */ 
/* 61 */     if (paramString.startsWith("linear-gradient("))
/* 62 */       return LinearGradient.valueOf(paramString);
/* 63 */     if (paramString.startsWith("radial-gradient(")) {
/* 64 */       return RadialGradient.valueOf(paramString);
/*    */     }
/* 66 */     return Color.valueOf(paramString);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.paint.Paint
 * JD-Core Version:    0.6.2
 */