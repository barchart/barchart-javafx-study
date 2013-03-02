/*     */ package javafx.scene.input;
/*     */ 
/*     */ import com.sun.javafx.tk.Toolkit;
/*     */ 
/*     */ public final class KeyCharacterCombination extends KeyCombination
/*     */ {
/*  39 */   private String character = "";
/*     */ 
/*     */   public final String getCharacter()
/*     */   {
/*  46 */     return this.character;
/*     */   }
/*     */ 
/*     */   public KeyCharacterCombination(String paramString, KeyCombination.ModifierValue paramModifierValue1, KeyCombination.ModifierValue paramModifierValue2, KeyCombination.ModifierValue paramModifierValue3, KeyCombination.ModifierValue paramModifierValue4, KeyCombination.ModifierValue paramModifierValue5)
/*     */   {
/*  68 */     super(paramModifierValue1, paramModifierValue2, paramModifierValue3, paramModifierValue4, paramModifierValue5);
/*     */ 
/*  70 */     validateKeyCharacter(paramString);
/*  71 */     this.character = paramString;
/*     */   }
/*     */ 
/*     */   public KeyCharacterCombination(String paramString, KeyCombination.Modifier[] paramArrayOfModifier)
/*     */   {
/*  87 */     super(paramArrayOfModifier);
/*     */ 
/*  89 */     validateKeyCharacter(paramString);
/*  90 */     this.character = paramString;
/*     */   }
/*     */ 
/*     */   public boolean match(KeyEvent paramKeyEvent)
/*     */   {
/* 110 */     return (paramKeyEvent.getCode().impl_getCode() == Toolkit.getToolkit().getKeyCodeForChar(getCharacter())) && (super.match(paramKeyEvent));
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 134 */     StringBuilder localStringBuilder = new StringBuilder();
/*     */ 
/* 136 */     localStringBuilder.append(super.getName());
/*     */ 
/* 138 */     if (localStringBuilder.length() > 0) {
/* 139 */       localStringBuilder.append("+");
/*     */     }
/*     */ 
/* 142 */     return localStringBuilder.append('\'').append(this.character.replace("'", "\\'")).append('\'').toString();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 155 */     if (this == paramObject) {
/* 156 */       return true;
/*     */     }
/*     */ 
/* 159 */     if (!(paramObject instanceof KeyCharacterCombination)) {
/* 160 */       return false;
/*     */     }
/*     */ 
/* 163 */     return (this.character.equals(((KeyCharacterCombination)paramObject).getCharacter())) && (super.equals(paramObject));
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 174 */     return 23 * super.hashCode() + this.character.hashCode();
/*     */   }
/*     */ 
/*     */   private static void validateKeyCharacter(String paramString) {
/* 178 */     if (paramString == null)
/* 179 */       throw new NullPointerException("Key character must not be null!");
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.input.KeyCharacterCombination
 * JD-Core Version:    0.6.2
 */