/*     */ package javafx.scene.input;
/*     */ 
/*     */ public final class KeyCodeCombination extends KeyCombination
/*     */ {
/*     */   private KeyCode code;
/*     */ 
/*     */   public final KeyCode getCode()
/*     */   {
/*  44 */     return this.code;
/*     */   }
/*     */ 
/*     */   public KeyCodeCombination(KeyCode paramKeyCode, KeyCombination.ModifierValue paramModifierValue1, KeyCombination.ModifierValue paramModifierValue2, KeyCombination.ModifierValue paramModifierValue3, KeyCombination.ModifierValue paramModifierValue4, KeyCombination.ModifierValue paramModifierValue5)
/*     */   {
/*  65 */     super(paramModifierValue1, paramModifierValue2, paramModifierValue3, paramModifierValue4, paramModifierValue5);
/*     */ 
/*  67 */     validateKeyCode(paramKeyCode);
/*  68 */     this.code = paramKeyCode;
/*     */   }
/*     */ 
/*     */   public KeyCodeCombination(KeyCode paramKeyCode, KeyCombination.Modifier[] paramArrayOfModifier)
/*     */   {
/*  84 */     super(paramArrayOfModifier);
/*     */ 
/*  86 */     validateKeyCode(paramKeyCode);
/*  87 */     this.code = paramKeyCode;
/*     */   }
/*     */ 
/*     */   public boolean match(KeyEvent paramKeyEvent)
/*     */   {
/* 104 */     return (paramKeyEvent.getCode() == getCode()) && (super.match(paramKeyEvent));
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 126 */     StringBuilder localStringBuilder = new StringBuilder();
/*     */ 
/* 128 */     localStringBuilder.append(super.getName());
/*     */ 
/* 130 */     if (localStringBuilder.length() > 0) {
/* 131 */       localStringBuilder.append("+");
/*     */     }
/*     */ 
/* 134 */     return localStringBuilder.append(this.code.getName()).toString();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 146 */     if (this == paramObject) {
/* 147 */       return true;
/*     */     }
/*     */ 
/* 150 */     if (!(paramObject instanceof KeyCodeCombination)) {
/* 151 */       return false;
/*     */     }
/*     */ 
/* 154 */     return (getCode() == ((KeyCodeCombination)paramObject).getCode()) && (super.equals(paramObject));
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 165 */     return 23 * super.hashCode() + this.code.hashCode();
/*     */   }
/*     */ 
/*     */   private static void validateKeyCode(KeyCode paramKeyCode) {
/* 169 */     if (paramKeyCode == null) {
/* 170 */       throw new NullPointerException("Key code must not be null!");
/*     */     }
/*     */ 
/* 173 */     if (getModifier(paramKeyCode.getName()) != null) {
/* 174 */       throw new IllegalArgumentException("Key code must not match modifier key!");
/*     */     }
/*     */ 
/* 178 */     if (paramKeyCode == KeyCode.UNDEFINED)
/* 179 */       throw new IllegalArgumentException("Key code must differ from undefined value!");
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.input.KeyCodeCombination
 * JD-Core Version:    0.6.2
 */