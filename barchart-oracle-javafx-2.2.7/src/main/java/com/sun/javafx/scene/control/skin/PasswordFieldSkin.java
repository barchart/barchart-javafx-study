/*    */ package com.sun.javafx.scene.control.skin;
/*    */ 
/*    */ import com.sun.javafx.scene.control.behavior.PasswordFieldBehavior;
/*    */ import javafx.scene.control.PasswordField;
/*    */ import javafx.scene.control.TextField;
/*    */ 
/*    */ public class PasswordFieldSkin extends TextFieldSkin
/*    */ {
/*    */   public static final char BULLET = '•';
/*    */ 
/*    */   public PasswordFieldSkin(PasswordField paramPasswordField)
/*    */   {
/* 40 */     super(paramPasswordField, new PasswordFieldBehavior(paramPasswordField));
/*    */   }
/*    */ 
/*    */   protected String maskText(String paramString) {
/* 44 */     TextField localTextField = (TextField)getSkinnable();
/*    */ 
/* 46 */     int i = localTextField.getLength();
/* 47 */     StringBuilder localStringBuilder = new StringBuilder(i);
/* 48 */     for (int j = 0; j < i; j++) {
/* 49 */       localStringBuilder.append('•');
/*    */     }
/*    */ 
/* 52 */     return localStringBuilder.toString();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.PasswordFieldSkin
 * JD-Core Version:    0.6.2
 */