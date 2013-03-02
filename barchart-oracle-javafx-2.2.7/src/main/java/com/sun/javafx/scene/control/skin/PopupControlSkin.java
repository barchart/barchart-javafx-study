/*     */ package com.sun.javafx.scene.control.skin;
/*     */ 
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.event.Event;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.Parent;
/*     */ import javafx.scene.Scene;
/*     */ import javafx.scene.control.PopupControl;
/*     */ import javafx.scene.control.Skin;
/*     */ import javafx.stage.Popup;
/*     */ import javafx.stage.Window;
/*     */ 
/*     */ public class PopupControlSkin<C extends PopupControl>
/*     */   implements Skin<C>
/*     */ {
/*  72 */   protected static int visiblePopupControlCount = 0;
/*     */   protected Node content;
/*     */   protected Node popupContent;
/*  91 */   protected boolean showing = false;
/*     */   protected Popup popup;
/*     */   protected double screenX;
/*     */   protected double screenY;
/*     */ 
/*     */   protected void setShowing(boolean paramBoolean)
/*     */   {
/*  94 */     boolean bool = this.showing;
/*  95 */     this.showing = paramBoolean;
/*  96 */     showHidePopup(bool);
/*     */   }
/*     */ 
/*     */   protected void showHidePopup(boolean paramBoolean) {
/* 100 */     if (this.showing) {
/* 101 */       visiblePopupControlCount += 1;
/* 102 */       showPopup();
/* 103 */     } else if (paramBoolean) {
/* 104 */       visiblePopupControlCount -= 1;
/* 105 */       hidePopup();
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void onAutoHide()
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void showPopup()
/*     */   {
/* 130 */     if (this.popup == null) {
/* 131 */       this.popup = new Popup();
/* 132 */       this.popup.setAutoHide(true);
/* 133 */       this.popup.setAutoFix(true);
/* 134 */       this.popup.setOnAutoHide(new EventHandler() {
/*     */         public void handle(Event paramAnonymousEvent) {
/* 136 */           PopupControlSkin.this.onAutoHide();
/*     */         }
/*     */ 
/*     */       });
/*     */     }
/*     */ 
/* 144 */     Scene localScene = this.content == null ? null : this.content.getScene();
/* 145 */     Window localWindow = localScene == null ? null : localScene.getWindow();
/*     */ 
/* 147 */     if (localWindow == null) return;
/* 148 */     this.popup.show(this.content, this.screenX, this.screenY);
/*     */ 
/* 151 */     this.popup.getContent().setAll(new Node[] { this.popupContent });
/*     */ 
/* 153 */     if ((this.popupContent instanceof Parent)) {
/* 154 */       ((Parent)this.popupContent).requestLayout();
/*     */     }
/*     */ 
/* 157 */     this.popupContent.autosize();
/*     */   }
/*     */ 
/*     */   protected void hidePopup() {
/* 161 */     if (this.popup != null) {
/* 162 */       this.popup.hide();
/*     */ 
/* 164 */       this.popup.getContent().clear();
/* 165 */       this.popup = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Node getNode()
/*     */   {
/* 175 */     return null;
/*     */   }
/*     */ 
/*     */   public void dispose()
/*     */   {
/*     */   }
/*     */ 
/*     */   public C getSkinnable()
/*     */   {
/* 184 */     return null;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.PopupControlSkin
 * JD-Core Version:    0.6.2
 */