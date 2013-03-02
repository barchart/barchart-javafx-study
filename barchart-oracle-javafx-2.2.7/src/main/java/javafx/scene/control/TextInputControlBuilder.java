/*    */ package javafx.scene.control;
/*    */ 
/*    */ public abstract class TextInputControlBuilder<B extends TextInputControlBuilder<B>> extends ControlBuilder<B>
/*    */ {
/*    */   private int __set;
/*    */   private boolean editable;
/*    */   private String promptText;
/*    */   private String text;
/*    */ 
/*    */   public void applyTo(TextInputControl paramTextInputControl)
/*    */   {
/* 15 */     super.applyTo(paramTextInputControl);
/* 16 */     int i = this.__set;
/* 17 */     if ((i & 0x1) != 0) paramTextInputControl.setEditable(this.editable);
/* 18 */     if ((i & 0x2) != 0) paramTextInputControl.setPromptText(this.promptText);
/* 19 */     if ((i & 0x4) != 0) paramTextInputControl.setText(this.text);
/*    */   }
/*    */ 
/*    */   public B editable(boolean paramBoolean)
/*    */   {
/* 28 */     this.editable = paramBoolean;
/* 29 */     this.__set |= 1;
/* 30 */     return this;
/*    */   }
/*    */ 
/*    */   public B promptText(String paramString)
/*    */   {
/* 39 */     this.promptText = paramString;
/* 40 */     this.__set |= 2;
/* 41 */     return this;
/*    */   }
/*    */ 
/*    */   public B text(String paramString)
/*    */   {
/* 50 */     this.text = paramString;
/* 51 */     this.__set |= 4;
/* 52 */     return this;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.TextInputControlBuilder
 * JD-Core Version:    0.6.2
 */