/*    */ package javafx.scene.input;
/*    */ 
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public final class KeyCodeCombinationBuilder
/*    */   implements Builder<KeyCodeCombination>
/*    */ {
/*    */   private KeyCombination.ModifierValue alt;
/*    */   private KeyCode code;
/*    */   private KeyCombination.ModifierValue control;
/*    */   private KeyCombination.ModifierValue meta;
/*    */   private KeyCombination.ModifierValue shift;
/*    */   private KeyCombination.ModifierValue shortcut;
/*    */ 
/*    */   public static KeyCodeCombinationBuilder create()
/*    */   {
/* 15 */     return new KeyCodeCombinationBuilder();
/*    */   }
/*    */ 
/*    */   public KeyCodeCombinationBuilder alt(KeyCombination.ModifierValue paramModifierValue)
/*    */   {
/* 23 */     this.alt = paramModifierValue;
/* 24 */     return this;
/*    */   }
/*    */ 
/*    */   public KeyCodeCombinationBuilder code(KeyCode paramKeyCode)
/*    */   {
/* 32 */     this.code = paramKeyCode;
/* 33 */     return this;
/*    */   }
/*    */ 
/*    */   public KeyCodeCombinationBuilder control(KeyCombination.ModifierValue paramModifierValue)
/*    */   {
/* 41 */     this.control = paramModifierValue;
/* 42 */     return this;
/*    */   }
/*    */ 
/*    */   public KeyCodeCombinationBuilder meta(KeyCombination.ModifierValue paramModifierValue)
/*    */   {
/* 50 */     this.meta = paramModifierValue;
/* 51 */     return this;
/*    */   }
/*    */ 
/*    */   public KeyCodeCombinationBuilder shift(KeyCombination.ModifierValue paramModifierValue)
/*    */   {
/* 59 */     this.shift = paramModifierValue;
/* 60 */     return this;
/*    */   }
/*    */ 
/*    */   public KeyCodeCombinationBuilder shortcut(KeyCombination.ModifierValue paramModifierValue)
/*    */   {
/* 68 */     this.shortcut = paramModifierValue;
/* 69 */     return this;
/*    */   }
/*    */ 
/*    */   public KeyCodeCombination build()
/*    */   {
/* 76 */     KeyCodeCombination localKeyCodeCombination = new KeyCodeCombination(this.code, this.shift, this.control, this.alt, this.meta, this.shortcut);
/* 77 */     return localKeyCodeCombination;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.input.KeyCodeCombinationBuilder
 * JD-Core Version:    0.6.2
 */