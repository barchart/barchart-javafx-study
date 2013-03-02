/*     */ package com.sun.javafx.scene.control.skin;
/*     */ 
/*     */ import javafx.beans.property.BooleanProperty;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.StringProperty;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.geometry.Insets;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.control.Label;
/*     */ import javafx.scene.control.Skin;
/*     */ import javafx.scene.control.Tooltip;
/*     */ import javafx.scene.layout.StackPane;
/*     */ 
/*     */ public class TooltipSkin
/*     */   implements Skin<Tooltip>
/*     */ {
/*     */   private StackPane root;
/*     */   private Label tipLabel;
/*     */   private StackPane pageCorner;
/*     */   private Tooltip tooltip;
/*     */ 
/*     */   public TooltipSkin(Tooltip paramTooltip)
/*     */   {
/*  52 */     this.tooltip = paramTooltip;
/*     */ 
/*  54 */     this.tipLabel = new Label();
/*  55 */     this.tipLabel.contentDisplayProperty().bind(paramTooltip.contentDisplayProperty());
/*  56 */     this.tipLabel.fontProperty().bind(paramTooltip.fontProperty());
/*  57 */     this.tipLabel.graphicProperty().bind(paramTooltip.graphicProperty());
/*  58 */     this.tipLabel.textAlignmentProperty().bind(paramTooltip.textAlignmentProperty());
/*  59 */     this.tipLabel.textOverrunProperty().bind(paramTooltip.textOverrunProperty());
/*  60 */     this.tipLabel.textProperty().bind(paramTooltip.textProperty());
/*  61 */     this.tipLabel.wrapTextProperty().bind(paramTooltip.wrapTextProperty());
/*  62 */     this.pageCorner = new StackPane();
/*  63 */     this.pageCorner.getStyleClass().setAll(new String[] { "page-corner" });
/*     */ 
/*  65 */     this.root = new StackPane() {
/*     */       protected void layoutChildren() {
/*  67 */         TooltipSkin.this.tipLabel.resizeRelocate(getInsets().getLeft(), getInsets().getTop(), getWidth() - getInsets().getLeft() - getInsets().getRight(), getHeight() - getInsets().getTop() - getInsets().getBottom());
/*     */ 
/*  70 */         double d1 = TooltipSkin.this.pageCorner.prefWidth(-1.0D);
/*  71 */         double d2 = TooltipSkin.this.pageCorner.prefHeight(-1.0D);
/*  72 */         TooltipSkin.this.pageCorner.resizeRelocate(getWidth() - d1, getHeight() - d2, d1, d2);
/*     */       }
/*     */ 
/*     */       protected double computeMinWidth(double paramAnonymousDouble) {
/*  76 */         return TooltipSkin.this.tooltip.getMinWidth() != -1.0D ? TooltipSkin.this.tooltip.getMinWidth() : computePrefWidth(paramAnonymousDouble);
/*     */       }
/*     */ 
/*     */       protected double computeMinHeight(double paramAnonymousDouble) {
/*  80 */         return TooltipSkin.this.tooltip.getMinHeight() != -1.0D ? TooltipSkin.this.tooltip.getMinHeight() : computePrefHeight(paramAnonymousDouble);
/*     */       }
/*     */ 
/*     */       protected double computePrefWidth(double paramAnonymousDouble) {
/*  84 */         if (TooltipSkin.this.tooltip.getPrefWidth() != -1.0D) {
/*  85 */           return TooltipSkin.this.tooltip.getPrefWidth();
/*     */         }
/*  87 */         return TooltipSkin.this.tooltip.isWrapText() ? TooltipSkin.this.tipLabel.prefWidth(paramAnonymousDouble) : getInsets().getLeft() + TooltipSkin.this.tipLabel.prefWidth(-1.0D) + getInsets().getRight();
/*     */       }
/*     */ 
/*     */       protected double computePrefHeight(double paramAnonymousDouble)
/*     */       {
/*  93 */         if (TooltipSkin.this.tooltip.getPrefWidth() != -1.0D) {
/*  94 */           return getInsets().getTop() + TooltipSkin.this.tipLabel.prefHeight(TooltipSkin.this.tooltip.getPrefWidth() - getInsets().getLeft() - getInsets().getRight()) + getInsets().getBottom();
/*     */         }
/*     */ 
/*  98 */         return getInsets().getTop() + TooltipSkin.this.tipLabel.prefHeight(-1.0D) + getInsets().getBottom();
/*     */       }
/*     */ 
/*     */       protected double computeMaxWidth(double paramAnonymousDouble)
/*     */       {
/* 103 */         return TooltipSkin.this.tooltip.getMaxWidth() != -1.0D ? TooltipSkin.this.tooltip.getMaxWidth() : computePrefWidth(paramAnonymousDouble);
/*     */       }
/*     */ 
/*     */       protected double computeMaxHeight(double paramAnonymousDouble) {
/* 107 */         return TooltipSkin.this.tooltip.getMaxHeight() != -1.0D ? TooltipSkin.this.tooltip.getMaxHeight() : computePrefHeight(paramAnonymousDouble);
/*     */       }
/*     */     };
/* 112 */     this.root.getChildren().addAll(new Node[] { this.tipLabel, this.pageCorner });
/*     */ 
/* 116 */     this.root.getStyleClass().setAll(paramTooltip.getStyleClass());
/* 117 */     this.root.setStyle(paramTooltip.getStyle());
/* 118 */     this.root.setId(paramTooltip.getId());
/*     */   }
/*     */ 
/*     */   public Tooltip getSkinnable()
/*     */   {
/* 123 */     return this.tooltip;
/*     */   }
/*     */ 
/*     */   public Node getNode() {
/* 127 */     return this.root;
/*     */   }
/*     */ 
/*     */   public void dispose() {
/* 131 */     this.tooltip = null;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.TooltipSkin
 * JD-Core Version:    0.6.2
 */