/*    */ package com.sun.javafx.scene.control.skin;
/*    */ 
/*    */ import com.sun.javafx.scene.control.behavior.SplitMenuButtonBehavior;
/*    */ import javafx.event.EventHandler;
/*    */ import javafx.scene.control.SplitMenuButton;
/*    */ import javafx.scene.input.MouseEvent;
/*    */ import javafx.scene.layout.StackPane;
/*    */ 
/*    */ public class SplitMenuButtonSkin extends MenuButtonSkinBase<SplitMenuButton, SplitMenuButtonBehavior>
/*    */ {
/*    */   public SplitMenuButtonSkin(SplitMenuButton paramSplitMenuButton)
/*    */   {
/* 53 */     super(paramSplitMenuButton, new SplitMenuButtonBehavior(paramSplitMenuButton));
/*    */ 
/* 59 */     this.behaveLikeButton = true;
/*    */ 
/* 62 */     this.arrowButton.addEventHandler(MouseEvent.ANY, new EventHandler()
/*    */     {
/*    */       public void handle(MouseEvent paramAnonymousMouseEvent) {
/* 65 */         paramAnonymousMouseEvent.consume();
/*    */       }
/*    */     });
/* 68 */     this.arrowButton.setOnMousePressed(new EventHandler() {
/*    */       public void handle(MouseEvent paramAnonymousMouseEvent) {
/* 70 */         ((SplitMenuButtonBehavior)SplitMenuButtonSkin.this.getBehavior()).mousePressed(paramAnonymousMouseEvent, false);
/* 71 */         paramAnonymousMouseEvent.consume();
/*    */       }
/*    */     });
/* 74 */     this.arrowButton.setOnMouseReleased(new EventHandler()
/*    */     {
/*    */       public void handle(MouseEvent paramAnonymousMouseEvent) {
/* 77 */         ((SplitMenuButtonBehavior)SplitMenuButtonSkin.this.getBehavior()).mouseReleased(paramAnonymousMouseEvent, false);
/* 78 */         paramAnonymousMouseEvent.consume();
/*    */       }
/*    */     });
/* 82 */     this.label.setLabelFor(paramSplitMenuButton);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.SplitMenuButtonSkin
 * JD-Core Version:    0.6.2
 */