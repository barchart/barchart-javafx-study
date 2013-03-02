/*    */ package com.sun.javafx.scene.web.skin;
/*    */ 
/*    */ import com.sun.javafx.scene.control.skin.PopupControlSkin;
/*    */ import javafx.collections.ObservableList;
/*    */ import javafx.scene.Node;
/*    */ import javafx.scene.control.PopupControl;
/*    */ import javafx.scene.layout.StackPane;
/*    */ 
/*    */ public class PopupButtonPopupControlSkin extends PopupControlSkin<PopupControl>
/*    */ {
/*    */   protected final PopupControl popup;
/*    */   StackPane root;
/*    */ 
/*    */   public PopupButtonPopupControlSkin(PopupControl paramPopupControl)
/*    */   {
/* 21 */     this.popup = paramPopupControl;
/*    */   }
/*    */ 
/*    */   public Node getNode() {
/* 25 */     if (this.root == null) {
/* 26 */       this.root = new StackPane();
/* 27 */       this.root.getStyleClass().addAll(new String[] { "context-menu", "popup-button-popup" });
/*    */     }
/* 29 */     return this.root;
/*    */   }
/*    */ 
/*    */   public PopupControl getSkinnable() {
/* 33 */     return super.getSkinnable();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.web.skin.PopupButtonPopupControlSkin
 * JD-Core Version:    0.6.2
 */