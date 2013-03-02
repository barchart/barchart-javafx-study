/*     */ package com.sun.javafx.scene.control.skin;
/*     */ 
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.geometry.Insets;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.layout.StackPane;
/*     */ 
/*     */ class EndButton extends StackPane
/*     */ {
/*     */   private StackPane arrow;
/*     */ 
/*     */   public EndButton(String paramString1, String paramString2)
/*     */   {
/* 565 */     getStyleClass().setAll(new String[] { paramString1 });
/* 566 */     this.arrow = new StackPane();
/* 567 */     this.arrow.getStyleClass().setAll(new String[] { paramString2 });
/* 568 */     getChildren().clear();
/* 569 */     getChildren().addAll(new Node[] { this.arrow });
/* 570 */     requestLayout();
/*     */   }
/*     */ 
/*     */   protected void layoutChildren() {
/* 574 */     double d1 = this.arrow.prefWidth(-1.0D);
/* 575 */     double d2 = this.arrow.prefHeight(-1.0D);
/*     */ 
/* 577 */     double d3 = (getHeight() - (getInsets().getTop() + getInsets().getBottom() + d2)) / 2.0D;
/* 578 */     double d4 = (getWidth() - (getInsets().getLeft() + getInsets().getRight() + d1)) / 2.0D;
/*     */ 
/* 580 */     this.arrow.resizeRelocate(d4 + getInsets().getLeft(), d3 + getInsets().getBottom(), d1, d2);
/*     */   }
/*     */ 
/*     */   protected double computeMinHeight(double paramDouble) {
/* 584 */     return prefHeight(-1.0D);
/*     */   }
/*     */ 
/*     */   protected double computeMinWidth(double paramDouble) {
/* 588 */     return prefWidth(-1.0D);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.EndButton
 * JD-Core Version:    0.6.2
 */