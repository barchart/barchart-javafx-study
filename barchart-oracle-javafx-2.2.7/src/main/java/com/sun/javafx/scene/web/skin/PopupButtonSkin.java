/*     */ package com.sun.javafx.scene.web.skin;
/*     */ 
/*     */ import com.sun.javafx.Utils;
/*     */ import com.sun.javafx.scene.control.skin.LabeledSkinBase;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.geometry.HPos;
/*     */ import javafx.geometry.Insets;
/*     */ import javafx.geometry.Point2D;
/*     */ import javafx.geometry.VPos;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.Scene;
/*     */ import javafx.scene.control.PopupControl;
/*     */ import javafx.scene.layout.StackPane;
/*     */ 
/*     */ public class PopupButtonSkin extends LabeledSkinBase<PopupButton, PopupButtonBehavior>
/*     */ {
/*     */   protected final StackPane arrow;
/*     */   protected final StackPane arrowButton;
/*     */   protected final PopupControl popup;
/*     */ 
/*     */   public PopupButtonSkin(PopupButton paramPopupButton)
/*     */   {
/*  22 */     super(paramPopupButton, new PopupButtonBehavior(paramPopupButton));
/*     */ 
/*  24 */     this.arrow = new StackPane();
/*  25 */     this.arrow.getStyleClass().setAll(new String[] { "arrow" });
/*  26 */     this.arrow.setMaxWidth((-1.0D / 0.0D));
/*  27 */     this.arrow.setMaxHeight((-1.0D / 0.0D));
/*     */ 
/*  29 */     this.arrowButton = new StackPane();
/*  30 */     this.arrowButton.getStyleClass().setAll(new String[] { "arrow-button" });
/*  31 */     this.arrowButton.getChildren().add(this.arrow);
/*     */ 
/*  33 */     this.popup = new PopupControl();
/*  34 */     this.popup.setAutoHide(true);
/*  35 */     this.popup.getStyleClass().setAll(new String[] { "popup-control" });
/*     */ 
/*  37 */     updateChildren();
/*     */ 
/*  39 */     registerChangeListener(paramPopupButton.showingProperty(), "SHOWING");
/*  40 */     registerChangeListener(paramPopupButton.focusedProperty(), "FOCUSED");
/*  41 */     registerChangeListener(paramPopupButton.contentProperty(), "CONTENT");
/*  42 */     registerChangeListener(this.popup.showingProperty(), "POPUP_VISIBLE");
/*     */   }
/*     */ 
/*     */   protected void updateChildren()
/*     */   {
/*  47 */     super.updateChildren();
/*     */ 
/*  49 */     if (this.arrowButton != null)
/*  50 */       getChildren().addAll(new Node[] { this.arrowButton });
/*     */   }
/*     */ 
/*     */   private void updatePopupButtonContent()
/*     */   {
/*  55 */     if (((PopupButton)getSkinnable()).getContent() != null)
/*  56 */       ((PopupButtonPopupControlSkin)this.popup.getSkin()).root.getChildren().setAll(new Node[] { ((PopupButton)getSkinnable()).getContent() });
/*     */   }
/*     */ 
/*     */   private void show()
/*     */   {
/*  61 */     if (!this.popup.isShowing()) {
/*  62 */       Point2D localPoint2D = Utils.pointRelativeTo(getSkinnable(), this.popup.prefWidth(-1.0D), this.popup.prefHeight(-1.0D), HPos.CENTER, VPos.BOTTOM, 0.0D, 0.0D, true);
/*     */ 
/*  65 */       updatePopupButtonContent();
/*  66 */       this.popup.show(((PopupButton)getSkinnable()).getScene().getWindow(), localPoint2D.getX(), localPoint2D.getY());
/*     */     }
/*     */   }
/*     */ 
/*     */   private void hide()
/*     */   {
/*  85 */     if (this.popup.isShowing())
/*  86 */       this.popup.hide();
/*     */   }
/*     */ 
/*     */   protected void handleControlPropertyChanged(String paramString)
/*     */   {
/*  92 */     super.handleControlPropertyChanged(paramString);
/*     */ 
/*  94 */     if (paramString == "CONTENT")
/*  95 */       updatePopupButtonContent();
/*  96 */     else if (paramString == "SHOWING") {
/*  97 */       if (((PopupButton)getSkinnable()).isShowing())
/*  98 */         show();
/*     */       else
/* 100 */         hide();
/*     */     }
/* 102 */     else if (paramString == "FOCUSED")
/*     */     {
/* 104 */       if ((!((PopupButton)getSkinnable()).isFocused()) && (((PopupButton)getSkinnable()).isShowing()))
/* 105 */         hide();
/*     */     }
/* 107 */     else if ((paramString == "POPUP_VISIBLE") && 
/* 108 */       (!this.popup.isShowing()) && (((PopupButton)getSkinnable()).isShowing()))
/*     */     {
/* 111 */       ((PopupButton)getSkinnable()).hide();
/*     */     }
/*     */   }
/*     */ 
/*     */   protected double computePrefWidth(double paramDouble)
/*     */   {
/* 117 */     Insets localInsets = this.arrowButton.getInsets();
/* 118 */     return super.computePrefWidth(paramDouble) + localInsets.getLeft() + this.arrow.prefWidth(-1.0D) + localInsets.getRight();
/*     */   }
/*     */ 
/*     */   protected double computePrefHeight(double paramDouble)
/*     */   {
/* 125 */     Insets localInsets1 = getInsets();
/* 126 */     Insets localInsets2 = this.arrowButton.getInsets();
/* 127 */     return Math.max(super.computePrefHeight(paramDouble), localInsets1.getTop() + localInsets2.getTop() + this.arrow.prefHeight(-1.0D) + localInsets2.getBottom());
/*     */   }
/*     */ 
/*     */   protected void layoutChildren()
/*     */   {
/* 133 */     Insets localInsets1 = getInsets();
/* 134 */     Insets localInsets2 = this.arrowButton.getInsets();
/* 135 */     double d1 = this.arrow.prefWidth(-1.0D);
/* 136 */     double d2 = localInsets2.getLeft() + d1 + localInsets2.getRight();
/*     */ 
/* 138 */     double d3 = getWidth();
/* 139 */     double d4 = getHeight();
/*     */ 
/* 141 */     layoutLabelInArea(0.0D, 0.0D, d3 - d2, d4);
/* 142 */     this.arrowButton.resize(d2, d4 - (localInsets1.getTop() + localInsets1.getBottom()));
/* 143 */     positionInArea(this.arrowButton, d3 - localInsets1.getRight() - d2, localInsets1.getTop(), d2, d4, 0.0D, HPos.CENTER, VPos.CENTER);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.web.skin.PopupButtonSkin
 * JD-Core Version:    0.6.2
 */