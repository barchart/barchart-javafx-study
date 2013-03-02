/*    */ package com.sun.javafx.scene.control.behavior;
/*    */ 
/*    */ import com.sun.javafx.scene.text.HitInfo;
/*    */ import javafx.scene.control.PasswordField;
/*    */ import javafx.scene.control.TextField;
/*    */ 
/*    */ public class PasswordFieldBehavior extends TextFieldBehavior
/*    */ {
/*    */   public PasswordFieldBehavior(PasswordField paramPasswordField)
/*    */   {
/* 39 */     super(paramPasswordField);
/*    */   }
/*    */   protected void deletePreviousWord() {
/*    */   }
/*    */   protected void deleteNextWord() {
/*    */   }
/*    */   protected void selectPreviousWord() {
/*    */   }
/*    */   protected void selectNextWord() {
/*    */   }
/*    */   protected void previousWord() {  } 
/*    */   protected void nextWord() {  } 
/* 51 */   protected void selectWord() { ((TextField)getControl()).selectAll(); }
/*    */ 
/*    */   protected void mouseDoubleClick(HitInfo paramHitInfo) {
/* 54 */     ((TextField)getControl()).selectAll();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.behavior.PasswordFieldBehavior
 * JD-Core Version:    0.6.2
 */