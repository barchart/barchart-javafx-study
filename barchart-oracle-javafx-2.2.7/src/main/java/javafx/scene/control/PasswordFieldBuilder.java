/*    */ package javafx.scene.control;
/*    */ 
/*    */ public class PasswordFieldBuilder<B extends PasswordFieldBuilder<B>> extends TextFieldBuilder<B>
/*    */ {
/*    */   private boolean __set;
/*    */   private String promptText;
/*    */ 
/*    */   public static PasswordFieldBuilder<?> create()
/*    */   {
/* 15 */     return new PasswordFieldBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(PasswordField paramPasswordField)
/*    */   {
/* 20 */     super.applyTo(paramPasswordField);
/* 21 */     if (this.__set) paramPasswordField.setPromptText(this.promptText);
/*    */   }
/*    */ 
/*    */   public B promptText(String paramString)
/*    */   {
/* 30 */     this.promptText = paramString;
/* 31 */     this.__set = true;
/* 32 */     return this;
/*    */   }
/*    */ 
/*    */   public PasswordField build()
/*    */   {
/* 39 */     PasswordField localPasswordField = new PasswordField();
/* 40 */     applyTo(localPasswordField);
/* 41 */     return localPasswordField;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.PasswordFieldBuilder
 * JD-Core Version:    0.6.2
 */