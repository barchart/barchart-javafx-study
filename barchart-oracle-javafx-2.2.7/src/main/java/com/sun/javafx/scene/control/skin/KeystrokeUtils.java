/*    */ package com.sun.javafx.scene.control.skin;
/*    */ 
/*    */ import com.sun.javafx.PlatformUtil;
/*    */ import javafx.scene.input.KeyCharacterCombination;
/*    */ import javafx.scene.input.KeyCodeCombination;
/*    */ import javafx.scene.input.KeyCombination;
/*    */ import javafx.scene.input.KeyCombination.ModifierValue;
/*    */ 
/*    */ public class KeystrokeUtils
/*    */ {
/*    */   public static String toString(KeyCombination paramKeyCombination)
/*    */   {
/* 35 */     if (paramKeyCombination == null) {
/* 36 */       return "";
/*    */     }
/*    */ 
/* 39 */     StringBuilder localStringBuilder = new StringBuilder();
/* 40 */     if (PlatformUtil.isMac())
/*    */     {
/* 44 */       if (paramKeyCombination.getControl() == KeyCombination.ModifierValue.DOWN) {
/* 45 */         localStringBuilder.append("⌃");
/*    */       }
/* 47 */       if (paramKeyCombination.getAlt() == KeyCombination.ModifierValue.DOWN) {
/* 48 */         localStringBuilder.append("⌥");
/*    */       }
/* 50 */       if (paramKeyCombination.getShift() == KeyCombination.ModifierValue.DOWN) {
/* 51 */         localStringBuilder.append("⇧");
/*    */       }
/* 53 */       if ((paramKeyCombination.getMeta() == KeyCombination.ModifierValue.DOWN) || (paramKeyCombination.getShortcut() == KeyCombination.ModifierValue.DOWN)) {
/* 54 */         localStringBuilder.append("⌘");
/*    */       }
/*    */     }
/*    */     else
/*    */     {
/* 59 */       if ((paramKeyCombination.getControl() == KeyCombination.ModifierValue.DOWN) || (paramKeyCombination.getShortcut() == KeyCombination.ModifierValue.DOWN)) {
/* 60 */         localStringBuilder.append("Ctrl+");
/*    */       }
/* 62 */       if (paramKeyCombination.getAlt() == KeyCombination.ModifierValue.DOWN) {
/* 63 */         localStringBuilder.append("Alt+");
/*    */       }
/* 65 */       if (paramKeyCombination.getShift() == KeyCombination.ModifierValue.DOWN) {
/* 66 */         localStringBuilder.append("Shift+");
/*    */       }
/* 68 */       if (paramKeyCombination.getMeta() == KeyCombination.ModifierValue.DOWN) {
/* 69 */         localStringBuilder.append("Meta+");
/*    */       }
/*    */     }
/*    */ 
/* 73 */     if ((paramKeyCombination instanceof KeyCodeCombination))
/* 74 */       localStringBuilder.append(KeyCodeUtils.getAccelerator(((KeyCodeCombination)paramKeyCombination).getCode()));
/* 75 */     else if ((paramKeyCombination instanceof KeyCharacterCombination)) {
/* 76 */       localStringBuilder.append(((KeyCharacterCombination)paramKeyCombination).getCharacter());
/*    */     }
/*    */ 
/* 79 */     return localStringBuilder.toString();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.KeystrokeUtils
 * JD-Core Version:    0.6.2
 */