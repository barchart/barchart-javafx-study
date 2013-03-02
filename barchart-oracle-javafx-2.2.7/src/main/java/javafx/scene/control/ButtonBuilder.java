/*    */ package javafx.scene.control;
/*    */ 
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class ButtonBuilder<B extends ButtonBuilder<B>> extends ButtonBaseBuilder<B>
/*    */   implements Builder<Button>
/*    */ {
/*    */   private int __set;
/*    */   private boolean cancelButton;
/*    */   private boolean defaultButton;
/*    */ 
/*    */   public static ButtonBuilder<?> create()
/*    */   {
/* 15 */     return new ButtonBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(Button paramButton)
/*    */   {
/* 20 */     super.applyTo(paramButton);
/* 21 */     int i = this.__set;
/* 22 */     if ((i & 0x1) != 0) paramButton.setCancelButton(this.cancelButton);
/* 23 */     if ((i & 0x2) != 0) paramButton.setDefaultButton(this.defaultButton);
/*    */   }
/*    */ 
/*    */   public B cancelButton(boolean paramBoolean)
/*    */   {
/* 32 */     this.cancelButton = paramBoolean;
/* 33 */     this.__set |= 1;
/* 34 */     return this;
/*    */   }
/*    */ 
/*    */   public B defaultButton(boolean paramBoolean)
/*    */   {
/* 43 */     this.defaultButton = paramBoolean;
/* 44 */     this.__set |= 2;
/* 45 */     return this;
/*    */   }
/*    */ 
/*    */   public Button build()
/*    */   {
/* 52 */     Button localButton = new Button();
/* 53 */     applyTo(localButton);
/* 54 */     return localButton;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.ButtonBuilder
 * JD-Core Version:    0.6.2
 */