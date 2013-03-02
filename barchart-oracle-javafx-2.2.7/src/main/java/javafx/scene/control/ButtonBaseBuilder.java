/*    */ package javafx.scene.control;
/*    */ 
/*    */ import javafx.event.ActionEvent;
/*    */ import javafx.event.EventHandler;
/*    */ 
/*    */ public abstract class ButtonBaseBuilder<B extends ButtonBaseBuilder<B>> extends LabeledBuilder<B>
/*    */ {
/*    */   private boolean __set;
/*    */   private EventHandler<ActionEvent> onAction;
/*    */ 
/*    */   public void applyTo(ButtonBase paramButtonBase)
/*    */   {
/* 15 */     super.applyTo(paramButtonBase);
/* 16 */     if (this.__set) paramButtonBase.setOnAction(this.onAction);
/*    */   }
/*    */ 
/*    */   public B onAction(EventHandler<ActionEvent> paramEventHandler)
/*    */   {
/* 25 */     this.onAction = paramEventHandler;
/* 26 */     this.__set = true;
/* 27 */     return this;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.ButtonBaseBuilder
 * JD-Core Version:    0.6.2
 */