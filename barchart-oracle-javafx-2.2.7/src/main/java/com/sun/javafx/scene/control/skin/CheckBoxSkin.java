/*    */ package com.sun.javafx.scene.control.skin;
/*    */ 
/*    */ import com.sun.javafx.scene.control.behavior.ButtonBehavior;
/*    */ import javafx.collections.ObservableList;
/*    */ import javafx.geometry.HPos;
/*    */ import javafx.geometry.Insets;
/*    */ import javafx.geometry.Pos;
/*    */ import javafx.geometry.VPos;
/*    */ import javafx.scene.control.CheckBox;
/*    */ import javafx.scene.layout.StackPane;
/*    */ 
/*    */ public class CheckBoxSkin extends LabeledSkinBase<CheckBox, ButtonBehavior<CheckBox>>
/*    */ {
/*    */   private StackPane box;
/*    */   private StackPane innerbox;
/*    */ 
/*    */   public CheckBoxSkin(CheckBox paramCheckBox)
/*    */   {
/* 46 */     super(paramCheckBox, new ButtonBehavior(paramCheckBox));
/*    */ 
/* 48 */     this.box = new StackPane();
/* 49 */     this.box.getStyleClass().setAll(new String[] { "box" });
/* 50 */     this.innerbox = new StackPane();
/* 51 */     this.innerbox.getStyleClass().setAll(new String[] { "mark" });
/* 52 */     this.box.getChildren().add(this.innerbox);
/* 53 */     updateChildren();
/*    */   }
/*    */ 
/*    */   protected void updateChildren() {
/* 57 */     super.updateChildren();
/* 58 */     if (this.box != null)
/* 59 */       getChildren().add(this.box);
/*    */   }
/*    */ 
/*    */   protected double computePrefWidth(double paramDouble)
/*    */   {
/* 64 */     return super.computePrefWidth(paramDouble) + snapSize(this.box.prefWidth(-1.0D));
/*    */   }
/*    */ 
/*    */   protected double computePrefHeight(double paramDouble) {
/* 68 */     return Math.max(super.computePrefHeight(paramDouble - this.box.prefWidth(-1.0D)), getInsets().getTop() + this.box.prefHeight(-1.0D) + getInsets().getBottom());
/*    */   }
/*    */ 
/*    */   protected void layoutChildren()
/*    */   {
/* 74 */     Insets localInsets = getInsets();
/*    */ 
/* 76 */     double d1 = getWidth() - localInsets.getLeft() - localInsets.getRight();
/* 77 */     double d2 = getHeight() - localInsets.getTop() - localInsets.getBottom();
/* 78 */     double d3 = this.box.prefWidth(-1.0D);
/* 79 */     double d4 = this.box.prefHeight(-1.0D);
/* 80 */     double d5 = Math.min(prefWidth(-1.0D) - d3, d1 - snapSize(d3));
/* 81 */     double d6 = Math.min(prefHeight(d5), d2);
/* 82 */     double d7 = Math.max(d4, d6);
/* 83 */     double d8 = Utils.computeXOffset(d1, d5 + d3, ((CheckBox)getSkinnable()).getAlignment().getHpos()) + localInsets.getLeft();
/* 84 */     double d9 = Utils.computeYOffset(d2, d7, ((CheckBox)getSkinnable()).getAlignment().getVpos()) + localInsets.getTop();
/*    */ 
/* 86 */     layoutLabelInArea(d8 + d3, d9, d5, d7, Pos.CENTER_LEFT);
/* 87 */     this.box.resize(snapSize(d3), snapSize(d4));
/* 88 */     positionInArea(this.box, d8, d9, d3, d7, getBaselineOffset(), HPos.CENTER, VPos.CENTER);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.CheckBoxSkin
 * JD-Core Version:    0.6.2
 */