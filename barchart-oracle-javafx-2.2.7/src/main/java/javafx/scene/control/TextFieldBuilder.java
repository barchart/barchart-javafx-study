/*    */ package javafx.scene.control;
/*    */ 
/*    */ import javafx.event.ActionEvent;
/*    */ import javafx.event.EventHandler;
/*    */ import javafx.geometry.Pos;
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class TextFieldBuilder<B extends TextFieldBuilder<B>> extends TextInputControlBuilder<B>
/*    */   implements Builder<TextField>
/*    */ {
/*    */   private int __set;
/*    */   private Pos alignment;
/*    */   private EventHandler<ActionEvent> onAction;
/*    */   private int prefColumnCount;
/*    */   private String promptText;
/*    */ 
/*    */   public static TextFieldBuilder<?> create()
/*    */   {
/* 15 */     return new TextFieldBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(TextField paramTextField)
/*    */   {
/* 20 */     super.applyTo(paramTextField);
/* 21 */     int i = this.__set;
/* 22 */     if ((i & 0x1) != 0) paramTextField.setAlignment(this.alignment);
/* 23 */     if ((i & 0x2) != 0) paramTextField.setOnAction(this.onAction);
/* 24 */     if ((i & 0x4) != 0) paramTextField.setPrefColumnCount(this.prefColumnCount);
/* 25 */     if ((i & 0x8) != 0) paramTextField.setPromptText(this.promptText);
/*    */   }
/*    */ 
/*    */   public B alignment(Pos paramPos)
/*    */   {
/* 34 */     this.alignment = paramPos;
/* 35 */     this.__set |= 1;
/* 36 */     return this;
/*    */   }
/*    */ 
/*    */   public B onAction(EventHandler<ActionEvent> paramEventHandler)
/*    */   {
/* 45 */     this.onAction = paramEventHandler;
/* 46 */     this.__set |= 2;
/* 47 */     return this;
/*    */   }
/*    */ 
/*    */   public B prefColumnCount(int paramInt)
/*    */   {
/* 56 */     this.prefColumnCount = paramInt;
/* 57 */     this.__set |= 4;
/* 58 */     return this;
/*    */   }
/*    */ 
/*    */   public B promptText(String paramString)
/*    */   {
/* 67 */     this.promptText = paramString;
/* 68 */     this.__set |= 8;
/* 69 */     return this;
/*    */   }
/*    */ 
/*    */   public TextField build()
/*    */   {
/* 76 */     TextField localTextField = new TextField();
/* 77 */     applyTo(localTextField);
/* 78 */     return localTextField;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.TextFieldBuilder
 * JD-Core Version:    0.6.2
 */