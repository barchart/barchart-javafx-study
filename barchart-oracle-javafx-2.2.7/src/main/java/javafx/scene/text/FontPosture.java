/*    */ package javafx.scene.text;
/*    */ 
/*    */ public enum FontPosture
/*    */ {
/* 54 */   REGULAR(new String[] { "", "regular" }), 
/*    */ 
/* 58 */   ITALIC(new String[] { "italic" });
/*    */ 
/*    */   private final String[] names;
/*    */ 
/*    */   private FontPosture(String[] paramArrayOfString) {
/* 63 */     this.names = paramArrayOfString;
/*    */   }
/*    */ 
/*    */   public static FontPosture findByName(String paramString)
/*    */   {
/* 72 */     if (paramString == null) return null;
/*    */ 
/* 74 */     for (FontPosture localFontPosture : values()) {
/* 75 */       for (String str : localFontPosture.names) {
/* 76 */         if (str.equalsIgnoreCase(paramString)) return localFontPosture;
/*    */       }
/*    */     }
/*    */ 
/* 80 */     return null;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.text.FontPosture
 * JD-Core Version:    0.6.2
 */