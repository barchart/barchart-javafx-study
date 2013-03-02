/*    */ package javafx.scene.input;
/*    */ 
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public final class KeyCharacterCombinationBuilder
/*    */   implements Builder<KeyCharacterCombination>
/*    */ {
/*    */   private KeyCombination.ModifierValue alt;
/*    */   private String character;
/*    */   private KeyCombination.ModifierValue control;
/*    */   private KeyCombination.ModifierValue meta;
/*    */   private KeyCombination.ModifierValue shift;
/*    */   private KeyCombination.ModifierValue shortcut;
/*    */ 
/*    */   public static KeyCharacterCombinationBuilder create()
/*    */   {
/* 15 */     return new KeyCharacterCombinationBuilder();
/*    */   }
/*    */ 
/*    */   public KeyCharacterCombinationBuilder alt(KeyCombination.ModifierValue paramModifierValue)
/*    */   {
/* 23 */     this.alt = paramModifierValue;
/* 24 */     return this;
/*    */   }
/*    */ 
/*    */   public KeyCharacterCombinationBuilder character(String paramString)
/*    */   {
/* 32 */     this.character = paramString;
/* 33 */     return this;
/*    */   }
/*    */ 
/*    */   public KeyCharacterCombinationBuilder control(KeyCombination.ModifierValue paramModifierValue)
/*    */   {
/* 41 */     this.control = paramModifierValue;
/* 42 */     return this;
/*    */   }
/*    */ 
/*    */   public KeyCharacterCombinationBuilder meta(KeyCombination.ModifierValue paramModifierValue)
/*    */   {
/* 50 */     this.meta = paramModifierValue;
/* 51 */     return this;
/*    */   }
/*    */ 
/*    */   public KeyCharacterCombinationBuilder shift(KeyCombination.ModifierValue paramModifierValue)
/*    */   {
/* 59 */     this.shift = paramModifierValue;
/* 60 */     return this;
/*    */   }
/*    */ 
/*    */   public KeyCharacterCombinationBuilder shortcut(KeyCombination.ModifierValue paramModifierValue)
/*    */   {
/* 68 */     this.shortcut = paramModifierValue;
/* 69 */     return this;
/*    */   }
/*    */ 
/*    */   public KeyCharacterCombination build()
/*    */   {
/* 76 */     KeyCharacterCombination localKeyCharacterCombination = new KeyCharacterCombination(this.character, this.shift, this.control, this.alt, this.meta, this.shortcut);
/* 77 */     return localKeyCharacterCombination;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.input.KeyCharacterCombinationBuilder
 * JD-Core Version:    0.6.2
 */