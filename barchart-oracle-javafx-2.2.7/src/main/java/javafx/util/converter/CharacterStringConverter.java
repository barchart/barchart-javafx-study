/*    */ package javafx.util.converter;
/*    */ 
/*    */ import javafx.util.StringConverter;
/*    */ 
/*    */ public class CharacterStringConverter extends StringConverter<Character>
/*    */ {
/*    */   public Character fromString(String paramString)
/*    */   {
/* 39 */     if (paramString == null) {
/* 40 */       return null;
/*    */     }
/*    */ 
/* 43 */     paramString = paramString.trim();
/*    */ 
/* 45 */     if (paramString.length() < 1) {
/* 46 */       return null;
/*    */     }
/*    */ 
/* 49 */     return new Character(paramString.charAt(0));
/*    */   }
/*    */ 
/*    */   public String toString(Character paramCharacter)
/*    */   {
/* 55 */     if (paramCharacter == null) {
/* 56 */       return "";
/*    */     }
/*    */ 
/* 59 */     return paramCharacter.toString();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.util.converter.CharacterStringConverter
 * JD-Core Version:    0.6.2
 */