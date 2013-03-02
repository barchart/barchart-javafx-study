/*     */ package com.sun.javafx.scene.control.skin;
/*     */ 
/*     */ import com.sun.javafx.scene.control.behavior.ComboBoxBaseBehavior;
/*     */ import javafx.beans.property.ReadOnlyBooleanProperty;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.geometry.HPos;
/*     */ import javafx.geometry.Insets;
/*     */ import javafx.geometry.VPos;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.control.ComboBoxBase;
/*     */ import javafx.scene.layout.StackPane;
/*     */ 
/*     */ public abstract class ComboBoxBaseSkin<T> extends SkinBase<ComboBoxBase<T>, ComboBoxBaseBehavior<T>>
/*     */ {
/*     */   private Node displayNode;
/*     */   protected StackPane arrowButton;
/*     */   protected StackPane arrow;
/*  46 */   private ComboBoxMode mode = ComboBoxMode.COMBOBOX;
/*     */ 
/*  47 */   protected final ComboBoxMode getMode() { return this.mode; } 
/*  48 */   protected final void setMode(ComboBoxMode paramComboBoxMode) { this.mode = paramComboBoxMode; }
/*     */ 
/*     */ 
/*     */   public ComboBoxBaseSkin(ComboBoxBase<T> paramComboBoxBase, ComboBoxBaseBehavior paramComboBoxBaseBehavior)
/*     */   {
/*  53 */     super(paramComboBoxBase, paramComboBoxBaseBehavior);
/*     */ 
/*  56 */     this.arrow = new StackPane();
/*  57 */     this.arrow.setFocusTraversable(false);
/*  58 */     this.arrow.getStyleClass().setAll(new String[] { "arrow" });
/*  59 */     this.arrow.setMaxWidth((-1.0D / 0.0D));
/*  60 */     this.arrow.setMaxHeight((-1.0D / 0.0D));
/*  61 */     this.arrowButton = new StackPane();
/*  62 */     this.arrowButton.setFocusTraversable(false);
/*  63 */     this.arrowButton.setId("arrow-button");
/*  64 */     this.arrowButton.getStyleClass().setAll(new String[] { "arrow-button" });
/*  65 */     this.arrowButton.getChildren().add(this.arrow);
/*  66 */     getChildren().add(this.arrowButton);
/*     */ 
/*  69 */     ((ComboBoxBase)getSkinnable()).focusedProperty().addListener(new ChangeListener() {
/*     */       public void changed(ObservableValue<? extends Boolean> paramAnonymousObservableValue, Boolean paramAnonymousBoolean1, Boolean paramAnonymousBoolean2) {
/*  71 */         if (!paramAnonymousBoolean2.booleanValue())
/*  72 */           ComboBoxBaseSkin.this.focusLost();
/*     */       }
/*     */     });
/*  78 */     registerChangeListener(paramComboBoxBase.editableProperty(), "EDITABLE");
/*  79 */     registerChangeListener(paramComboBoxBase.showingProperty(), "SHOWING");
/*  80 */     registerChangeListener(paramComboBoxBase.focusedProperty(), "FOCUSED");
/*  81 */     registerChangeListener(paramComboBoxBase.valueProperty(), "VALUE");
/*     */   }
/*     */ 
/*     */   protected void focusLost() {
/*  85 */     ((ComboBoxBase)getSkinnable()).hide();
/*     */   }
/*     */ 
/*     */   public abstract Node getDisplayNode();
/*     */ 
/*     */   public abstract void show();
/*     */ 
/*     */   public abstract void hide();
/*     */ 
/*     */   protected void handleControlPropertyChanged(String paramString)
/*     */   {
/* 109 */     super.handleControlPropertyChanged(paramString);
/*     */ 
/* 111 */     if (paramString == "SHOWING") {
/* 112 */       if (((ComboBoxBase)getSkinnable()).isShowing())
/* 113 */         show();
/*     */       else
/* 115 */         hide();
/*     */     }
/* 117 */     else if (paramString == "EDITABLE")
/* 118 */       updateDisplayArea();
/* 119 */     else if (paramString == "VALUE")
/* 120 */       updateDisplayArea();
/*     */   }
/*     */ 
/*     */   private void updateDisplayArea()
/*     */   {
/* 125 */     this.displayNode = getDisplayNode();
/* 126 */     getChildren().setAll(new Node[] { this.displayNode, this.arrowButton });
/*     */   }
/*     */ 
/*     */   private boolean isButton() {
/* 130 */     return getMode() == ComboBoxMode.BUTTON;
/*     */   }
/*     */ 
/*     */   protected void layoutChildren() {
/* 134 */     if (this.displayNode == null) {
/* 135 */       updateDisplayArea();
/*     */     }
/*     */ 
/* 138 */     Insets localInsets1 = getInsets();
/* 139 */     Insets localInsets2 = this.arrowButton.getInsets();
/*     */ 
/* 142 */     double d1 = localInsets1.getLeft();
/* 143 */     double d2 = localInsets1.getTop();
/* 144 */     double d3 = ((ComboBoxBase)getSkinnable()).getWidth() - (localInsets1.getLeft() + localInsets1.getRight());
/* 145 */     double d4 = ((ComboBoxBase)getSkinnable()).getHeight() - (localInsets1.getTop() + localInsets1.getBottom());
/*     */ 
/* 147 */     double d5 = snapSize(this.arrow.prefWidth(-1.0D));
/* 148 */     double d6 = isButton() ? 0.0D : snapSpace(localInsets2.getLeft()) + d5 + snapSpace(localInsets2.getRight());
/*     */ 
/* 152 */     if (this.displayNode != null) {
/* 153 */       this.displayNode.resizeRelocate(d1, d2, d3, d4);
/*     */     }
/*     */ 
/* 156 */     if (isButton()) return;
/*     */ 
/* 158 */     this.arrowButton.resize(d6, getHeight() - localInsets1.getTop() - localInsets1.getBottom());
/* 159 */     positionInArea(this.arrowButton, getWidth() - localInsets1.getRight() - d6, 0.0D, d6, getHeight(), 0.0D, HPos.CENTER, VPos.CENTER);
/*     */   }
/*     */ 
/*     */   protected double computePrefWidth(double paramDouble)
/*     */   {
/* 164 */     if (this.displayNode == null) updateDisplayArea();
/* 165 */     Insets localInsets = this.arrowButton.getInsets();
/* 166 */     double d1 = snapSize(this.arrow.prefWidth(-1.0D));
/* 167 */     double d2 = isButton() ? 0.0D : snapSpace(localInsets.getLeft()) + d1 + snapSpace(localInsets.getRight());
/*     */ 
/* 172 */     double d3 = this.displayNode == null ? 0.0D : this.displayNode.prefWidth(paramDouble) + d2;
/*     */ 
/* 174 */     return getInsets().getLeft() + d3 + getInsets().getRight();
/*     */   }
/*     */ 
/*     */   protected double computePrefHeight(double paramDouble)
/*     */   {
/* 179 */     if (this.displayNode == null) {
/* 180 */       updateDisplayArea();
/*     */ 
/* 187 */       this.displayNode.impl_processCSS(true);
/*     */     }
/*     */     double d1;
/* 191 */     if (this.displayNode == null)
/*     */     {
/* 193 */       Insets localInsets2 = this.arrowButton.getInsets();
/* 194 */       double d2 = isButton() ? 0.0D : localInsets2.getTop() + this.arrow.prefHeight(-1.0D) + localInsets2.getBottom();
/*     */ 
/* 196 */       d1 = Math.max(21.0D, d2);
/*     */     } else {
/* 198 */       d1 = this.displayNode.prefHeight(paramDouble);
/*     */     }
/*     */ 
/* 201 */     Insets localInsets1 = getInsets();
/* 202 */     return localInsets1.getTop() + d1 + localInsets1.getBottom();
/*     */   }
/*     */ 
/*     */   protected double computeMaxWidth(double paramDouble) {
/* 206 */     return ((ComboBoxBase)getSkinnable()).prefWidth(paramDouble);
/*     */   }
/*     */ 
/*     */   protected double computeMaxHeight(double paramDouble) {
/* 210 */     return ((ComboBoxBase)getSkinnable()).prefHeight(paramDouble);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.ComboBoxBaseSkin
 * JD-Core Version:    0.6.2
 */