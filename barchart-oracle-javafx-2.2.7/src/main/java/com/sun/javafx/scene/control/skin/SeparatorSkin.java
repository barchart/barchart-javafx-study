/*     */ package com.sun.javafx.scene.control.skin;
/*     */ 
/*     */ import com.sun.javafx.scene.control.behavior.BehaviorBase;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.geometry.Insets;
/*     */ import javafx.geometry.Orientation;
/*     */ import javafx.scene.control.Separator;
/*     */ import javafx.scene.layout.Region;
/*     */ 
/*     */ public class SeparatorSkin extends SkinBase<Separator, BehaviorBase<Separator>>
/*     */ {
/*     */   private static final double DEFAULT_LENGTH = 10.0D;
/*     */   private final Region line;
/*     */ 
/*     */   public SeparatorSkin(Separator paramSeparator)
/*     */   {
/*  62 */     super(paramSeparator, new BehaviorBase(paramSeparator));
/*     */ 
/*  64 */     this.line = new Region();
/*  65 */     this.line.getStyleClass().setAll(new String[] { "line" });
/*     */ 
/*  67 */     getChildren().add(this.line);
/*  68 */     registerChangeListener(paramSeparator.orientationProperty(), "ORIENTATION");
/*  69 */     registerChangeListener(paramSeparator.halignmentProperty(), "HALIGNMENT");
/*  70 */     registerChangeListener(paramSeparator.valignmentProperty(), "VALIGNMENT");
/*     */   }
/*     */ 
/*     */   protected void handleControlPropertyChanged(String paramString) {
/*  74 */     super.handleControlPropertyChanged(paramString);
/*  75 */     if (("ORIENTATION".equals(paramString)) || ("HALIGNMENT".equals(paramString)) || ("VALIGNMENT".equals(paramString)))
/*  76 */       requestLayout();
/*     */   }
/*     */ 
/*     */   protected void layoutChildren()
/*     */   {
/*  92 */     Separator localSeparator = (Separator)getSkinnable();
/*  93 */     Insets localInsets = getInsets();
/*     */ 
/*  96 */     double d1 = getWidth() - (localInsets.getLeft() + localInsets.getRight());
/*  97 */     double d2 = getHeight() - (localInsets.getTop() + localInsets.getBottom());
/*     */ 
/*  99 */     if (localSeparator.getOrientation() == Orientation.HORIZONTAL)
/*     */     {
/* 101 */       this.line.resize(d1, this.line.prefHeight(-1.0D));
/*     */     }
/*     */     else {
/* 104 */       this.line.resize(this.line.prefWidth(-1.0D), d2);
/*     */     }
/*     */ 
/* 108 */     positionInArea(this.line, localInsets.getLeft(), localInsets.getTop(), d1, d2, 0.0D, localSeparator.getHalignment(), localSeparator.getValignment());
/*     */   }
/*     */ 
/*     */   protected double computePrefWidth(double paramDouble) {
/* 112 */     double d = ((Separator)getSkinnable()).getOrientation() == Orientation.VERTICAL ? this.line.prefWidth(-1.0D) : 10.0D;
/* 113 */     return d + getInsets().getLeft() + getInsets().getRight();
/*     */   }
/*     */ 
/*     */   protected double computePrefHeight(double paramDouble) {
/* 117 */     double d = ((Separator)getSkinnable()).getOrientation() == Orientation.VERTICAL ? 10.0D : this.line.prefHeight(-1.0D);
/* 118 */     return d + getInsets().getTop() + getInsets().getBottom();
/*     */   }
/*     */ 
/*     */   protected double computeMaxWidth(double paramDouble) {
/* 122 */     return ((Separator)getSkinnable()).getOrientation() == Orientation.VERTICAL ? ((Separator)getSkinnable()).prefWidth(paramDouble) : 1.7976931348623157E+308D;
/*     */   }
/*     */ 
/*     */   protected double computeMaxHeight(double paramDouble) {
/* 126 */     return ((Separator)getSkinnable()).getOrientation() == Orientation.VERTICAL ? 1.7976931348623157E+308D : ((Separator)getSkinnable()).prefHeight(paramDouble);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.SeparatorSkin
 * JD-Core Version:    0.6.2
 */