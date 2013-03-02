/*    */ package com.sun.javafx.scene.control.skin;
/*    */ 
/*    */ import com.sun.javafx.scene.control.behavior.MenuButtonBehavior;
/*    */ import javafx.collections.ObservableMap;
/*    */ import javafx.event.ActionEvent;
/*    */ import javafx.event.Event;
/*    */ import javafx.event.EventHandler;
/*    */ import javafx.scene.control.ContextMenu;
/*    */ import javafx.scene.control.MenuButton;
/*    */ import javafx.scene.control.Skin;
/*    */ import javafx.stage.WindowEvent;
/*    */ 
/*    */ public class MenuButtonSkin extends MenuButtonSkinBase<MenuButton, MenuButtonBehavior>
/*    */ {
/*    */   static final String AUTOHIDE = "autoHide";
/*    */ 
/*    */   public MenuButtonSkin(final MenuButton paramMenuButton)
/*    */   {
/* 54 */     super(paramMenuButton, new MenuButtonBehavior(paramMenuButton));
/*    */ 
/* 58 */     this.popup.setOnAutoHide(new EventHandler() {
/*    */       public void handle(Event paramAnonymousEvent) {
/* 60 */         MenuButton localMenuButton = (MenuButton)MenuButtonSkin.this.getSkinnable();
/*    */ 
/* 63 */         if (!localMenuButton.getProperties().containsKey("autoHide"))
/* 64 */           localMenuButton.getProperties().put("autoHide", Boolean.TRUE);
/*    */       }
/*    */     });
/* 69 */     this.popup.setOnShown(new EventHandler() {
/*    */       public void handle(WindowEvent paramAnonymousWindowEvent) {
/* 71 */         ContextMenuContent localContextMenuContent = (ContextMenuContent)MenuButtonSkin.this.popup.getSkin().getNode();
/* 72 */         if (localContextMenuContent != null) localContextMenuContent.requestFocus();
/*    */       }
/*    */     });
/* 76 */     if (paramMenuButton.getOnAction() == null) {
/* 77 */       paramMenuButton.setOnAction(new EventHandler() {
/*    */         public void handle(ActionEvent paramAnonymousActionEvent) {
/* 79 */           paramMenuButton.show();
/*    */         }
/*    */       });
/*    */     }
/*    */ 
/* 84 */     this.label.setLabelFor(paramMenuButton);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.MenuButtonSkin
 * JD-Core Version:    0.6.2
 */