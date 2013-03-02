/*     */ package com.sun.javafx.scene.control.skin;
/*     */ 
/*     */ import com.sun.javafx.scene.control.behavior.ButtonBehavior;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.geometry.HPos;
/*     */ import javafx.geometry.Insets;
/*     */ import javafx.geometry.Pos;
/*     */ import javafx.geometry.VPos;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.control.RadioButton;
/*     */ import javafx.scene.layout.StackPane;
/*     */ 
/*     */ public class RadioButtonSkin extends LabeledSkinBase<RadioButton, ButtonBehavior<RadioButton>>
/*     */ {
/*     */   private StackPane radio;
/*     */ 
/*     */   public RadioButtonSkin(RadioButton paramRadioButton)
/*     */   {
/*  50 */     super(paramRadioButton, new ButtonBehavior(paramRadioButton));
/*     */ 
/*  52 */     this.radio = createRadio();
/*  53 */     updateChildren();
/*     */   }
/*     */ 
/*     */   protected void updateChildren() {
/*  57 */     super.updateChildren();
/*  58 */     if (this.radio != null)
/*  59 */       getChildren().add(this.radio);
/*     */   }
/*     */ 
/*     */   private static StackPane createRadio()
/*     */   {
/*  64 */     StackPane localStackPane1 = new StackPane();
/*  65 */     localStackPane1.getStyleClass().setAll(new String[] { "radio" });
/*  66 */     localStackPane1.setSnapToPixel(false);
/*  67 */     StackPane localStackPane2 = new StackPane();
/*  68 */     localStackPane2.getStyleClass().setAll(new String[] { "dot" });
/*  69 */     localStackPane1.getChildren().clear();
/*  70 */     localStackPane1.getChildren().addAll(new Node[] { localStackPane2 });
/*  71 */     return localStackPane1;
/*     */   }
/*     */ 
/*     */   protected double computePrefWidth(double paramDouble)
/*     */   {
/*  82 */     return super.computePrefWidth(paramDouble) + snapSize(this.radio.prefWidth(-1.0D));
/*     */   }
/*     */ 
/*     */   protected double computePrefHeight(double paramDouble) {
/*  86 */     return Math.max(snapSize(super.computePrefHeight(paramDouble - this.radio.prefWidth(-1.0D))), getInsets().getTop() + this.radio.prefHeight(-1.0D) + getInsets().getBottom());
/*     */   }
/*     */ 
/*     */   protected void layoutChildren()
/*     */   {
/*  91 */     Insets localInsets = getInsets();
/*     */ 
/*  93 */     double d1 = getWidth() - localInsets.getLeft() - localInsets.getRight();
/*  94 */     double d2 = getHeight() - localInsets.getTop() - localInsets.getBottom();
/*  95 */     double d3 = this.radio.prefWidth(-1.0D);
/*  96 */     double d4 = this.radio.prefHeight(-1.0D);
/*  97 */     double d5 = Math.min(prefWidth(-1.0D) - d3, d1 - snapSize(d3));
/*  98 */     double d6 = Math.min(prefHeight(d5), d2);
/*  99 */     double d7 = Math.max(d4, d6);
/* 100 */     double d8 = Utils.computeXOffset(d1, d5 + d3, ((RadioButton)getSkinnable()).getAlignment().getHpos()) + localInsets.getLeft();
/* 101 */     double d9 = Utils.computeYOffset(d2, d7, ((RadioButton)getSkinnable()).getAlignment().getVpos()) + localInsets.getTop();
/*     */ 
/* 103 */     layoutLabelInArea(d8 + d3, d9, d5, d7, Pos.CENTER_LEFT);
/* 104 */     this.radio.resize(snapSize(d3), snapSize(d4));
/* 105 */     positionInArea(this.radio, d8, d9, d3, d7, getBaselineOffset(), HPos.CENTER, VPos.CENTER);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.RadioButtonSkin
 * JD-Core Version:    0.6.2
 */