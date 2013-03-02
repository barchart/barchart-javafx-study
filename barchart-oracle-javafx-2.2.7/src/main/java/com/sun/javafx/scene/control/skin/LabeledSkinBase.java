/*      */ package com.sun.javafx.scene.control.skin;
/*      */ 
/*      */ import com.sun.javafx.PlatformUtil;
/*      */ import com.sun.javafx.scene.control.behavior.BehaviorBase;
/*      */ import com.sun.javafx.scene.control.behavior.TextBinding;
/*      */ import com.sun.javafx.tk.FontLoader;
/*      */ import com.sun.javafx.tk.FontMetrics;
/*      */ import com.sun.javafx.tk.Toolkit;
/*      */ import javafx.beans.InvalidationListener;
/*      */ import javafx.beans.Observable;
/*      */ import javafx.beans.property.ReadOnlyObjectProperty;
/*      */ import javafx.collections.ObservableList;
/*      */ import javafx.geometry.Bounds;
/*      */ import javafx.geometry.HPos;
/*      */ import javafx.geometry.Insets;
/*      */ import javafx.geometry.Orientation;
/*      */ import javafx.geometry.Pos;
/*      */ import javafx.geometry.VPos;
/*      */ import javafx.scene.Node;
/*      */ import javafx.scene.Parent;
/*      */ import javafx.scene.Scene;
/*      */ import javafx.scene.control.ContentDisplay;
/*      */ import javafx.scene.control.Label;
/*      */ import javafx.scene.control.Labeled;
/*      */ import javafx.scene.control.OverrunStyle;
/*      */ import javafx.scene.image.ImageView;
/*      */ import javafx.scene.input.KeyCode;
/*      */ import javafx.scene.input.KeyCodeCombination;
/*      */ import javafx.scene.input.KeyCombination;
/*      */ import javafx.scene.input.KeyCombination.Modifier;
/*      */ import javafx.scene.input.Mnemonic;
/*      */ import javafx.scene.shape.Line;
/*      */ import javafx.scene.shape.Rectangle;
/*      */ import javafx.scene.text.Font;
/*      */ 
/*      */ public abstract class LabeledSkinBase<C extends Labeled, B extends BehaviorBase<C>> extends SkinBase<C, B>
/*      */ {
/*      */   LabeledText text;
/*   74 */   boolean invalidText = true;
/*      */   Node graphic;
/*   87 */   double textWidth = (-1.0D / 0.0D);
/*      */ 
/*   94 */   double ellipsisWidth = (-1.0D / 0.0D);
/*      */ 
/*  107 */   final InvalidationListener graphicPropertyChangedListener = new InvalidationListener() {
/*      */     public void invalidated(Observable paramAnonymousObservable) {
/*  109 */       LabeledSkinBase.this.invalidText = true;
/*  110 */       LabeledSkinBase.this.requestLayout();
/*      */     }
/*  107 */   };
/*      */   private Rectangle textClip;
/*      */   public TextBinding bindings;
/*      */   Line mnemonic_underscore;
/*  834 */   private boolean containsMnemonic = false;
/*  835 */   private Scene mnemonicScene = null;
/*      */   private KeyCode mnemonicCode;
/*  838 */   private Node labeledNode = null;
/*      */ 
/*      */   public LabeledSkinBase(C paramC, B paramB)
/*      */   {
/*  126 */     super(paramC, paramB);
/*      */ 
/*  130 */     this.text = new LabeledText(paramC);
/*      */ 
/*  132 */     this.textClip = new Rectangle();
/*  133 */     this.text.setClip(this.textClip);
/*      */ 
/*  135 */     updateChildren();
/*      */ 
/*  141 */     registerChangeListener(paramC.widthProperty(), "WIDTH");
/*  142 */     registerChangeListener(paramC.heightProperty(), "HEIGHT");
/*  143 */     registerChangeListener(paramC.textFillProperty(), "TEXT_FILL");
/*  144 */     registerChangeListener(paramC.fontProperty(), "FONT");
/*  145 */     registerChangeListener(paramC.graphicProperty(), "GRAPHIC");
/*  146 */     registerChangeListener(paramC.contentDisplayProperty(), "CONTENT_DISPLAY");
/*  147 */     registerChangeListener(paramC.labelPaddingProperty(), "LABEL_PADDING");
/*  148 */     registerChangeListener(paramC.graphicTextGapProperty(), "GRAPHIC_TEXT_GAP");
/*  149 */     registerChangeListener(paramC.alignmentProperty(), "ALIGNMENT");
/*  150 */     registerChangeListener(paramC.mnemonicParsingProperty(), "MNEMONIC_PARSING");
/*  151 */     registerChangeListener(paramC.textProperty(), "TEXT");
/*  152 */     registerChangeListener(paramC.textAlignmentProperty(), "TEXT_ALIGNMENT");
/*  153 */     registerChangeListener(paramC.textOverrunProperty(), "TEXT_OVERRUN");
/*  154 */     registerChangeListener(paramC.wrapTextProperty(), "WRAP_TEXT");
/*  155 */     registerChangeListener(paramC.underlineProperty(), "UNDERLINE");
/*  156 */     registerChangeListener(paramC.parentProperty(), "PARENT");
/*      */   }
/*      */ 
/*      */   protected void handleControlPropertyChanged(String paramString)
/*      */   {
/*  166 */     super.handleControlPropertyChanged(paramString);
/*      */ 
/*  175 */     if (paramString == "WIDTH") {
/*  176 */       updateWrappingWidth();
/*  177 */       this.invalidText = true;
/*      */     }
/*  179 */     else if (paramString == "HEIGHT") {
/*  180 */       this.invalidText = true;
/*      */     }
/*  182 */     else if (paramString == "FONT") {
/*  183 */       textMetricsChanged();
/*  184 */       invalidateWidths();
/*  185 */       this.ellipsisWidth = (-1.0D / 0.0D);
/*  186 */     } else if (paramString == "GRAPHIC") {
/*  187 */       updateChildren();
/*  188 */       textMetricsChanged();
/*  189 */     } else if (paramString == "CONTENT_DISPLAY") {
/*  190 */       updateChildren();
/*  191 */       textMetricsChanged();
/*  192 */     } else if (paramString == "LABEL_PADDING") {
/*  193 */       textMetricsChanged();
/*  194 */     } else if (paramString == "GRAPHIC_TEXT_GAP") {
/*  195 */       textMetricsChanged();
/*  196 */     } else if (paramString == "ALIGNMENT")
/*      */     {
/*  205 */       requestLayout();
/*  206 */     } else if (paramString == "MNEMONIC_PARSING") {
/*  207 */       textMetricsChanged();
/*  208 */     } else if (paramString == "TEXT") {
/*  209 */       updateChildren();
/*  210 */       textMetricsChanged();
/*  211 */       invalidateWidths();
/*  212 */     } else if (paramString != "TEXT_ALIGNMENT")
/*      */     {
/*  214 */       if (paramString == "TEXT_OVERRUN") {
/*  215 */         textMetricsChanged();
/*  216 */       } else if (paramString == "ELLIPSIS_STRING") {
/*  217 */         textMetricsChanged();
/*  218 */         invalidateWidths();
/*  219 */         this.ellipsisWidth = (-1.0D / 0.0D);
/*  220 */       } else if (paramString == "WRAP_TEXT") {
/*  221 */         updateWrappingWidth();
/*  222 */         textMetricsChanged();
/*  223 */       } else if (paramString == "UNDERLINE") {
/*  224 */         textMetricsChanged();
/*  225 */       } else if (paramString == "PARENT") {
/*  226 */         parentChanged();
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*  231 */   protected double topPadding() { return snapSpace(getInsets().getTop()); }
/*      */ 
/*      */   protected double topLabelPadding()
/*      */   {
/*  235 */     return snapSpace(((Labeled)getSkinnable()).getLabelPadding().getTop());
/*      */   }
/*      */ 
/*      */   protected double bottomPadding() {
/*  239 */     return snapSpace(getInsets().getBottom());
/*      */   }
/*      */ 
/*      */   protected double bottomLabelPadding() {
/*  243 */     return snapSpace(((Labeled)getSkinnable()).getLabelPadding().getBottom());
/*      */   }
/*      */ 
/*      */   protected double leftPadding() {
/*  247 */     return snapSpace(getInsets().getLeft());
/*      */   }
/*      */ 
/*      */   protected double leftLabelPadding() {
/*  251 */     return snapSpace(((Labeled)getSkinnable()).getLabelPadding().getLeft());
/*      */   }
/*      */ 
/*      */   protected double rightPadding() {
/*  255 */     return snapSpace(getInsets().getRight());
/*      */   }
/*      */ 
/*      */   protected double rightLabelPadding() {
/*  259 */     return snapSpace(((Labeled)getSkinnable()).getLabelPadding().getRight());
/*      */   }
/*      */ 
/*      */   private void textMetricsChanged()
/*      */   {
/*  269 */     this.invalidText = true;
/*  270 */     requestLayout();
/*      */   }
/*      */ 
/*      */   private void parentChanged()
/*      */   {
/*  280 */     Labeled localLabeled = (Labeled)getSkinnable();
/*  281 */     Parent localParent = localLabeled.getParent();
/*      */     KeyCodeCombination localKeyCodeCombination;
/*      */     Mnemonic localMnemonic;
/*  283 */     if (localParent == null)
/*      */     {
/*  289 */       if (this.mnemonicScene != null) {
/*  290 */         localKeyCodeCombination = new KeyCodeCombination(this.mnemonicCode, new KeyCombination.Modifier[] { PlatformUtil.isMac() ? KeyCombination.META_DOWN : KeyCombination.ALT_DOWN });
/*      */ 
/*  297 */         localMnemonic = new Mnemonic(this.labeledNode, localKeyCodeCombination);
/*  298 */         this.mnemonicScene.removeMnemonic(localMnemonic);
/*  299 */         this.mnemonicScene = null;
/*      */       }
/*      */ 
/*      */     }
/*  307 */     else if (this.containsMnemonic == true) {
/*  308 */       localKeyCodeCombination = new KeyCodeCombination(this.mnemonicCode, new KeyCombination.Modifier[] { PlatformUtil.isMac() ? KeyCombination.META_DOWN : KeyCombination.ALT_DOWN });
/*      */ 
/*  315 */       if (this.labeledNode != null) {
/*  316 */         localMnemonic = new Mnemonic(this.labeledNode, localKeyCodeCombination);
/*  317 */         this.mnemonicScene = this.labeledNode.getParent().getScene();
/*  318 */         this.mnemonicScene.addMnemonic(localMnemonic);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void invalidateWidths()
/*      */   {
/*  328 */     this.textWidth = (-1.0D / 0.0D);
/*      */   }
/*      */ 
/*      */   void updateDisplayedText()
/*      */   {
/*  339 */     updateDisplayedText(-1.0D, -1.0D);
/*      */   }
/*      */ 
/*      */   private void updateDisplayedText(double paramDouble1, double paramDouble2) {
/*  343 */     if (this.invalidText) {
/*  344 */       Labeled localLabeled = (Labeled)getSkinnable();
/*  345 */       String str1 = localLabeled.getText();
/*      */ 
/*  347 */       int i = -1;
/*      */ 
/*  352 */       if ((str1 != null) && (str1.length() > 0)) {
/*  353 */         this.bindings = new TextBinding(str1);
/*      */ 
/*  355 */         if ((!PlatformUtil.isMac()) && (((Labeled)getSkinnable()).isMnemonicParsing() == true))
/*      */         {
/*  361 */           if ((localLabeled instanceof Label))
/*      */           {
/*  363 */             this.labeledNode = ((Label)localLabeled).getLabelFor();
/*      */           }
/*  365 */           else this.labeledNode = localLabeled;
/*      */ 
/*  368 */           i = this.bindings.getMnemonicIndex();
/*      */         }
/*      */       }
/*      */       KeyCodeCombination localKeyCodeCombination;
/*      */       Mnemonic localMnemonic;
/*  375 */       if (this.containsMnemonic == true)
/*      */       {
/*  379 */         if ((i == -1) || ((this.bindings != null) && (this.bindings.getMnemonic() != this.mnemonicCode))) {
/*  380 */           localKeyCodeCombination = new KeyCodeCombination(this.mnemonicCode, new KeyCombination.Modifier[] { PlatformUtil.isMac() ? KeyCombination.META_DOWN : KeyCombination.ALT_DOWN });
/*      */ 
/*  387 */           localMnemonic = new Mnemonic(this.labeledNode, localKeyCodeCombination);
/*  388 */           this.mnemonicScene.removeMnemonic(localMnemonic);
/*  389 */           this.containsMnemonic = false;
/*  390 */           this.mnemonicScene = null;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  397 */       if ((str1 != null) && (str1.length() > 0) && 
/*  398 */         (i >= 0) && (!this.containsMnemonic)) {
/*  399 */         this.containsMnemonic = true;
/*  400 */         this.mnemonicCode = this.bindings.getMnemonic();
/*      */ 
/*  402 */         localKeyCodeCombination = new KeyCodeCombination(this.mnemonicCode, new KeyCombination.Modifier[] { PlatformUtil.isMac() ? KeyCombination.META_DOWN : KeyCombination.ALT_DOWN });
/*      */ 
/*  409 */         if (this.labeledNode != null) {
/*  410 */           localMnemonic = new Mnemonic(this.labeledNode, localKeyCodeCombination);
/*  411 */           if (this.labeledNode.getParent() != null) {
/*  412 */             this.mnemonicScene = this.labeledNode.getParent().getScene();
/*  413 */             this.mnemonicScene.addMnemonic(localMnemonic);
/*      */           }
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  419 */       if (this.containsMnemonic == true) {
/*  420 */         str1 = this.bindings.getText();
/*  421 */         if (this.mnemonic_underscore == null) {
/*  422 */           this.mnemonic_underscore = new Line();
/*  423 */           this.mnemonic_underscore.setStartX(0.0D);
/*  424 */           this.mnemonic_underscore.setStartY(0.0D);
/*  425 */           this.mnemonic_underscore.setEndY(0.0D);
/*  426 */           this.mnemonic_underscore.getStyleClass().clear();
/*  427 */           this.mnemonic_underscore.getStyleClass().setAll(new String[] { "mnemonic-underline" });
/*      */         }
/*  429 */         if (!getChildren().contains(this.mnemonic_underscore)) {
/*  430 */           getChildren().add(this.mnemonic_underscore);
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  436 */         if ((((Labeled)getSkinnable()).isMnemonicParsing() == true) && (PlatformUtil.isMac()) && (this.bindings != null)) {
/*  437 */           str1 = this.bindings.getText();
/*      */         }
/*      */         else {
/*  440 */           str1 = localLabeled.getText();
/*      */         }
/*  442 */         if ((this.mnemonic_underscore != null) && 
/*  443 */           (getChildren().contains(this.mnemonic_underscore))) {
/*  444 */           getChildren().remove(this.mnemonic_underscore);
/*  445 */           this.mnemonic_underscore = null;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  450 */       int j = str1 != null ? str1.length() : 0;
/*  451 */       int k = 0;
/*      */ 
/*  453 */       if ((str1 != null) && (j > 0)) {
/*  454 */         int m = str1.indexOf(10);
/*  455 */         if ((m > -1) && (m < j - 1))
/*      */         {
/*  458 */           k = 1;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  463 */       int n = (localLabeled.getContentDisplay() == ContentDisplay.LEFT) || (localLabeled.getContentDisplay() == ContentDisplay.RIGHT) ? 1 : 0;
/*      */ 
/*  467 */       double d1 = localLabeled.getWidth() - leftPadding() - leftLabelPadding() - rightPadding() - rightLabelPadding();
/*      */ 
/*  469 */       d1 = Math.max(d1, 0.0D);
/*      */ 
/*  471 */       if (paramDouble1 == -1.0D) {
/*  472 */         paramDouble1 = d1;
/*      */       }
/*  474 */       double d2 = Math.min(minWidth(-1.0D), d1);
/*  475 */       if ((n != 0) && (!isIgnoreGraphic())) {
/*  476 */         double d3 = localLabeled.getGraphic().getLayoutBounds().getWidth() + localLabeled.getGraphicTextGap();
/*  477 */         paramDouble1 -= d3;
/*  478 */         d2 -= d3;
/*      */       }
/*  480 */       paramDouble1 = Math.max(d2, paramDouble1);
/*      */ 
/*  482 */       int i1 = (localLabeled.getContentDisplay() == ContentDisplay.TOP) || (localLabeled.getContentDisplay() == ContentDisplay.BOTTOM) ? 1 : 0;
/*      */ 
/*  486 */       double d4 = localLabeled.getHeight() - topPadding() - topLabelPadding() - bottomPadding() - bottomLabelPadding();
/*      */ 
/*  488 */       d4 = Math.max(d4, 0.0D);
/*      */ 
/*  490 */       if (paramDouble2 == -1.0D) {
/*  491 */         paramDouble2 = d4;
/*      */       }
/*  493 */       double d5 = Math.min(minHeight(paramDouble1), d4);
/*  494 */       if ((i1 != 0) && (localLabeled.getGraphic() != null)) {
/*  495 */         double d6 = localLabeled.getGraphic().getLayoutBounds().getHeight() + localLabeled.getGraphicTextGap();
/*  496 */         paramDouble2 -= d6;
/*  497 */         d5 -= d6;
/*      */       }
/*  499 */       paramDouble2 = Math.max(d5, paramDouble2);
/*      */ 
/*  501 */       this.textClip.setWidth(paramDouble1);
/*  502 */       this.textClip.setHeight(paramDouble2);
/*  503 */       updateWrappingWidth();
/*      */ 
/*  505 */       Font localFont = this.text.getFont();
/*  506 */       OverrunStyle localOverrunStyle = localLabeled.getTextOverrun();
/*  507 */       String str3 = localLabeled.getEllipsisString();
/*      */       String str2;
/*  509 */       if (localLabeled.isWrapText()) {
/*  510 */         str2 = Utils.computeClippedWrappedText(localFont, str1, paramDouble1, paramDouble2, localOverrunStyle, str3);
/*  511 */       } else if (k != 0) {
/*  512 */         StringBuilder localStringBuilder = new StringBuilder();
/*      */ 
/*  514 */         String[] arrayOfString = str1.split("\n");
/*  515 */         for (int i2 = 0; i2 < arrayOfString.length; i2++) {
/*  516 */           localStringBuilder.append(Utils.computeClippedText(localFont, arrayOfString[i2], paramDouble1, localOverrunStyle, str3));
/*  517 */           if (i2 < arrayOfString.length - 1) {
/*  518 */             localStringBuilder.append('\n');
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  540 */         str2 = localStringBuilder.toString();
/*      */       } else {
/*  542 */         str2 = Utils.computeClippedText(localFont, str1, paramDouble1, localOverrunStyle, str3);
/*      */       }
/*      */ 
/*  545 */       if ((str2 != null) && (str2.endsWith("\n")))
/*      */       {
/*  547 */         str2 = str2.substring(0, str2.length() - 1);
/*      */       }
/*      */ 
/*  550 */       this.text.setText(str2);
/*  551 */       updateWrappingWidth();
/*  552 */       this.invalidText = false;
/*      */     }
/*      */   }
/*      */ 
/*      */   private void updateWrappingWidth()
/*      */   {
/*  563 */     Labeled localLabeled = (Labeled)getSkinnable();
/*  564 */     this.text.setWrappingWidth(0.0D);
/*  565 */     if (localLabeled.isWrapText())
/*      */     {
/*  568 */       double d = Math.min(this.text.prefWidth(-1.0D), this.textClip.getWidth());
/*  569 */       this.text.setWrappingWidth(d);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void updateChildren()
/*      */   {
/*  581 */     Labeled localLabeled = (Labeled)getSkinnable();
/*      */ 
/*  585 */     if (this.graphic != null) {
/*  586 */       this.graphic.layoutBoundsProperty().removeListener(this.graphicPropertyChangedListener);
/*      */     }
/*      */ 
/*  589 */     this.graphic = localLabeled.getGraphic();
/*      */ 
/*  593 */     if ((this.graphic instanceof ImageView)) {
/*  594 */       this.graphic.setMouseTransparent(true);
/*      */     }
/*      */ 
/*  598 */     if (isIgnoreGraphic()) {
/*  599 */       if (localLabeled.getContentDisplay() == ContentDisplay.GRAPHIC_ONLY)
/*  600 */         getChildren().clear();
/*      */       else
/*  602 */         getChildren().setAll(new Node[] { this.text });
/*      */     }
/*      */     else {
/*  605 */       this.graphic.layoutBoundsProperty().addListener(this.graphicPropertyChangedListener);
/*  606 */       if (isIgnoreText())
/*  607 */         getChildren().setAll(new Node[] { this.graphic });
/*      */       else
/*  609 */         getChildren().setAll(new Node[] { this.graphic, this.text });
/*      */     }
/*      */   }
/*      */ 
/*      */   protected boolean isIgnoreGraphic()
/*      */   {
/*  620 */     return (this.graphic == null) || (!this.graphic.isManaged()) || (((Labeled)getSkinnable()).getContentDisplay() == ContentDisplay.TEXT_ONLY);
/*      */   }
/*      */ 
/*      */   protected boolean isIgnoreText()
/*      */   {
/*  630 */     Labeled localLabeled = (Labeled)getSkinnable();
/*  631 */     String str = localLabeled.getText();
/*  632 */     return (str == null) || (str.equals("")) || (localLabeled.getContentDisplay() == ContentDisplay.GRAPHIC_ONLY);
/*      */   }
/*      */ 
/*      */   protected double computeMinWidth(double paramDouble)
/*      */   {
/*  658 */     Labeled localLabeled = (Labeled)getSkinnable();
/*  659 */     Font localFont = this.text.getFont();
/*  660 */     OverrunStyle localOverrunStyle = localLabeled.getTextOverrun();
/*  661 */     String str1 = localLabeled.getEllipsisString();
/*  662 */     String str2 = localLabeled.getText();
/*  663 */     int i = (str2 == null) || (str2.isEmpty()) ? 1 : 0;
/*  664 */     ContentDisplay localContentDisplay = localLabeled.getContentDisplay();
/*  665 */     double d1 = localLabeled.getGraphicTextGap();
/*  666 */     double d2 = leftPadding() + leftLabelPadding() + rightPadding() + rightLabelPadding();
/*      */ 
/*  669 */     double d3 = 0.0D;
/*  670 */     if (i == 0)
/*      */     {
/*  672 */       if (localOverrunStyle == OverrunStyle.CLIP) {
/*  673 */         if (this.textWidth == (-1.0D / 0.0D))
/*      */         {
/*  675 */           this.textWidth = Utils.computeTextWidth(localFont, str2.substring(0, 1), 0.0D);
/*      */         }
/*  677 */         d3 = this.textWidth;
/*      */       } else {
/*  679 */         if (this.textWidth == (-1.0D / 0.0D)) {
/*  680 */           this.textWidth = Utils.computeTextWidth(localFont, str2, 0.0D);
/*      */         }
/*      */ 
/*  683 */         if (this.ellipsisWidth == (-1.0D / 0.0D)) {
/*  684 */           this.ellipsisWidth = Utils.computeTextWidth(localFont, str1, 0.0D);
/*      */         }
/*  686 */         d3 = Math.min(this.textWidth, this.ellipsisWidth);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  691 */     Node localNode = localLabeled.getGraphic();
/*      */     double d4;
/*  693 */     if (isIgnoreGraphic())
/*  694 */       d4 = d3;
/*  695 */     else if (isIgnoreText())
/*  696 */       d4 = localNode.minWidth(-1.0D);
/*  697 */     else if ((localContentDisplay == ContentDisplay.LEFT) || (localContentDisplay == ContentDisplay.RIGHT))
/*  698 */       d4 = d3 + localNode.minWidth(-1.0D) + d1;
/*      */     else {
/*  700 */       d4 = Math.max(d3, localNode.minWidth(-1.0D));
/*      */     }
/*      */ 
/*  703 */     return d4 + d2;
/*      */   }
/*      */ 
/*      */   protected double computeMinHeight(double paramDouble) {
/*  707 */     Labeled localLabeled = (Labeled)getSkinnable();
/*  708 */     Font localFont = this.text.getFont();
/*      */ 
/*  710 */     String str = localLabeled.getText();
/*  711 */     if ((str != null) && (str.length() > 0)) {
/*  712 */       int i = str.indexOf(10);
/*  713 */       if (i >= 0) {
/*  714 */         str = str.substring(0, i);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  720 */     double d1 = Utils.computeTextHeight(localFont, str, 0.0D);
/*      */ 
/*  722 */     double d2 = d1;
/*      */ 
/*  725 */     if (!isIgnoreGraphic()) {
/*  726 */       Node localNode = localLabeled.getGraphic();
/*  727 */       if ((localLabeled.getContentDisplay() == ContentDisplay.TOP) || (localLabeled.getContentDisplay() == ContentDisplay.BOTTOM))
/*      */       {
/*  729 */         d2 = localNode.minHeight(-1.0D) + localLabeled.getGraphicTextGap() + d1;
/*      */       }
/*  731 */       else d2 = Math.max(d1, localNode.minHeight(-1.0D));
/*      */ 
/*      */     }
/*      */ 
/*  735 */     return topPadding() + d2 + bottomPadding() + topLabelPadding() - bottomLabelPadding();
/*      */   }
/*      */ 
/*      */   protected double computePrefWidth(double paramDouble)
/*      */   {
/*  740 */     Labeled localLabeled = (Labeled)getSkinnable();
/*  741 */     Font localFont = this.text.getFont();
/*  742 */     String str = localLabeled.getText();
/*  743 */     int i = (str == null) || (str.isEmpty()) ? 1 : 0;
/*  744 */     double d1 = leftPadding() + leftLabelPadding() + rightPadding() + rightLabelPadding();
/*      */ 
/*  746 */     double d2 = topPadding() + topLabelPadding() + bottomPadding() + bottomLabelPadding();
/*      */ 
/*  749 */     double d3 = i != 0 ? 0.0D : Utils.computeTextWidth(localFont, str, 0.0D);
/*      */ 
/*  752 */     Node localNode = localLabeled.getGraphic();
/*  753 */     if (isIgnoreGraphic())
/*  754 */       return d3 + d1;
/*  755 */     if (isIgnoreText())
/*  756 */       return localNode.prefWidth(-1.0D) + d1;
/*  757 */     if ((localLabeled.getContentDisplay() == ContentDisplay.LEFT) || (localLabeled.getContentDisplay() == ContentDisplay.RIGHT))
/*      */     {
/*  759 */       return d3 + localLabeled.getGraphicTextGap() + localNode.prefWidth(-1.0D) + d1;
/*      */     }
/*  761 */     return Math.max(d3, localNode.prefWidth(-1.0D)) + d1;
/*      */   }
/*      */ 
/*      */   protected double computePrefHeight(double paramDouble)
/*      */   {
/*  766 */     Labeled localLabeled = (Labeled)getSkinnable();
/*  767 */     Font localFont = this.text.getFont();
/*  768 */     ContentDisplay localContentDisplay = localLabeled.getContentDisplay();
/*  769 */     double d1 = localLabeled.getGraphicTextGap();
/*  770 */     double d2 = leftPadding() + leftLabelPadding() + rightPadding() + rightLabelPadding();
/*      */ 
/*  773 */     String str = localLabeled.getText();
/*  774 */     if ((str != null) && (str.endsWith("\n")))
/*      */     {
/*  776 */       str = str.substring(0, str.length() - 1);
/*      */     }
/*      */ 
/*  779 */     if ((!isIgnoreGraphic()) && ((localContentDisplay == ContentDisplay.LEFT) || (localContentDisplay == ContentDisplay.RIGHT)))
/*      */     {
/*  781 */       paramDouble -= this.graphic.prefWidth(-1.0D) + d1;
/*      */     }
/*      */ 
/*  784 */     paramDouble -= d2;
/*      */ 
/*  787 */     double d3 = Utils.computeTextHeight(localFont, str, localLabeled.isWrapText() ? paramDouble : 0.0D);
/*      */ 
/*  791 */     double d4 = d3;
/*  792 */     if (!isIgnoreGraphic()) {
/*  793 */       Node localNode = localLabeled.getGraphic();
/*  794 */       if ((localContentDisplay == ContentDisplay.TOP) || (localContentDisplay == ContentDisplay.BOTTOM))
/*  795 */         d4 = localNode.prefHeight(-1.0D) + d1 + d3;
/*      */       else {
/*  797 */         d4 = Math.max(d3, localNode.prefHeight(-1.0D));
/*      */       }
/*      */     }
/*      */ 
/*  801 */     return topPadding() + d4 + bottomPadding() + topLabelPadding() + bottomLabelPadding();
/*      */   }
/*      */ 
/*      */   protected double computeMaxWidth(double paramDouble) {
/*  805 */     return ((Labeled)getSkinnable()).prefWidth(paramDouble);
/*      */   }
/*      */ 
/*      */   protected double computeMaxHeight(double paramDouble) {
/*  809 */     return ((Labeled)getSkinnable()).prefHeight(paramDouble);
/*      */   }
/*      */ 
/*      */   public double getBaselineOffset() {
/*  813 */     Font localFont = this.text.getFont();
/*  814 */     FontMetrics localFontMetrics = Toolkit.getToolkit().getFontLoader().getFontMetrics(localFont);
/*  815 */     float f = localFontMetrics.getAscent();
/*  816 */     double d = f;
/*  817 */     Labeled localLabeled = (Labeled)getSkinnable();
/*  818 */     Node localNode = localLabeled.getGraphic();
/*  819 */     if (!isIgnoreGraphic()) {
/*  820 */       if ((localLabeled.getContentDisplay() == ContentDisplay.TOP) || (localLabeled.getContentDisplay() == ContentDisplay.BOTTOM))
/*      */       {
/*  822 */         d = localNode.prefHeight(-1.0D) + localLabeled.getGraphicTextGap() + f;
/*      */       }
/*  824 */       else d = Math.max(f, localNode.prefHeight(-1.0D));
/*      */ 
/*      */     }
/*      */ 
/*  828 */     return topPadding() + topLabelPadding() + d;
/*      */   }
/*      */ 
/*      */   protected void layoutChildren()
/*      */   {
/*  853 */     double d1 = leftPadding();
/*  854 */     double d2 = topPadding();
/*  855 */     double d3 = getWidth() - (leftPadding() + rightPadding());
/*  856 */     double d4 = getHeight() - (topPadding() + bottomPadding());
/*      */ 
/*  858 */     layoutLabelInArea(d1, d2, d3, d4);
/*      */   }
/*      */ 
/*      */   protected void layoutLabelInArea(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*      */   {
/*  874 */     layoutLabelInArea(paramDouble1, paramDouble2, paramDouble3, paramDouble4, null);
/*      */   }
/*      */ 
/*      */   protected void layoutLabelInArea(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, Pos paramPos)
/*      */   {
/*  893 */     Labeled localLabeled = (Labeled)getSkinnable();
/*  894 */     ContentDisplay localContentDisplay = localLabeled.getContentDisplay();
/*      */ 
/*  896 */     if (paramPos == null) {
/*  897 */       paramPos = localLabeled.getAlignment();
/*      */     }
/*      */ 
/*  900 */     HPos localHPos = paramPos.getHpos();
/*  901 */     VPos localVPos = paramPos.getVpos();
/*      */ 
/*  905 */     boolean bool1 = isIgnoreGraphic();
/*  906 */     boolean bool2 = isIgnoreText();
/*      */ 
/*  908 */     paramDouble1 += leftLabelPadding();
/*  909 */     paramDouble2 += topLabelPadding();
/*  910 */     paramDouble3 -= leftLabelPadding() + rightLabelPadding();
/*  911 */     paramDouble4 -= topLabelPadding() + bottomLabelPadding();
/*      */     double d2;
/*      */     double d1;
/*  919 */     if (bool1) {
/*  920 */       d1 = d2 = 0.0D;
/*  921 */     } else if (bool2) {
/*  922 */       if (this.graphic.isResizable()) {
/*  923 */         Orientation localOrientation = this.graphic.getContentBias();
/*  924 */         if (localOrientation == Orientation.HORIZONTAL) {
/*  925 */           d1 = Utils.boundedSize(paramDouble3, this.graphic.minWidth(-1.0D), this.graphic.maxWidth(-1.0D));
/*  926 */           d2 = Utils.boundedSize(paramDouble4, this.graphic.minHeight(d1), this.graphic.maxHeight(d1));
/*  927 */         } else if (localOrientation == Orientation.VERTICAL) {
/*  928 */           d2 = Utils.boundedSize(paramDouble4, this.graphic.minHeight(-1.0D), this.graphic.maxHeight(-1.0D));
/*  929 */           d1 = Utils.boundedSize(paramDouble3, this.graphic.minWidth(d2), this.graphic.maxWidth(d2));
/*      */         } else {
/*  931 */           d1 = Utils.boundedSize(paramDouble3, this.graphic.minWidth(-1.0D), this.graphic.maxWidth(-1.0D));
/*  932 */           d2 = Utils.boundedSize(paramDouble4, this.graphic.minHeight(-1.0D), this.graphic.maxHeight(-1.0D));
/*      */         }
/*  934 */         this.graphic.resize(d1, d2);
/*      */       } else {
/*  936 */         d1 = this.graphic.getLayoutBounds().getWidth();
/*  937 */         d2 = this.graphic.getLayoutBounds().getHeight();
/*      */       }
/*      */     } else {
/*  940 */       this.graphic.autosize();
/*  941 */       d1 = this.graphic.getLayoutBounds().getWidth();
/*  942 */       d2 = this.graphic.getLayoutBounds().getHeight();
/*      */     }
/*      */     double d4;
/*      */     double d3;
/*  945 */     if (bool2) {
/*  946 */       d3 = d4 = 0.0D;
/*  947 */       this.text.setText("");
/*      */     } else {
/*  949 */       updateDisplayedText(paramDouble3, paramDouble4);
/*  950 */       d3 = Math.min(this.text.getLayoutBounds().getWidth(), this.textClip.getWidth());
/*  951 */       d4 = Math.min(this.text.getLayoutBounds().getHeight(), this.textClip.getHeight());
/*      */     }
/*      */ 
/*  954 */     double d5 = (bool2) || (bool1) ? 0.0D : localLabeled.getGraphicTextGap();
/*      */ 
/*  959 */     double d6 = Math.max(d1, d3);
/*  960 */     double d7 = Math.max(d2, d4);
/*  961 */     if ((localContentDisplay == ContentDisplay.TOP) || (localContentDisplay == ContentDisplay.BOTTOM))
/*  962 */       d7 = d2 + d5 + d4;
/*  963 */     else if ((localContentDisplay == ContentDisplay.LEFT) || (localContentDisplay == ContentDisplay.RIGHT))
/*  964 */       d6 = d1 + d5 + d3;
/*      */     double d8;
/*  971 */     if (localHPos == HPos.LEFT)
/*  972 */       d8 = paramDouble1;
/*  973 */     else if (localHPos == HPos.RIGHT) {
/*  974 */       d8 = paramDouble1 + (paramDouble3 - d6);
/*      */     }
/*      */     else
/*      */     {
/*  978 */       d8 = paramDouble1 + (paramDouble3 - d6) / 2.0D;
/*      */     }
/*      */     double d9;
/*  983 */     if (localVPos == VPos.TOP)
/*  984 */       d9 = paramDouble2;
/*  985 */     else if (localVPos == VPos.BOTTOM) {
/*  986 */       d9 = paramDouble2 + (paramDouble4 - d7);
/*      */     }
/*      */     else
/*      */     {
/*  990 */       d9 = paramDouble2 + (paramDouble4 - d7) / 2.0D;
/*      */     }
/*      */ 
/*  993 */     double d10 = 0.0D;
/*  994 */     double d11 = 0.0D;
/*  995 */     double d12 = 0.0D;
/*  996 */     if (this.containsMnemonic) {
/*  997 */       Font localFont = this.text.getFont();
/*  998 */       String str = this.bindings.getText();
/*  999 */       d10 = Utils.computeTextWidth(localFont, str.substring(0, this.bindings.getMnemonicIndex()), 0.0D);
/* 1000 */       d11 = Utils.computeTextWidth(localFont, str.substring(this.bindings.getMnemonicIndex(), this.bindings.getMnemonicIndex() + 1), 0.0D);
/* 1001 */       d12 = Utils.computeTextHeight(localFont, "_", 0.0D);
/*      */     }
/*      */ 
/* 1010 */     this.text.setManaged(true);
/* 1011 */     if ((bool1) && (bool2))
/*      */     {
/* 1019 */       this.text.setManaged(false);
/* 1020 */       this.text.relocate(snapPosition(d8), snapPosition(d9));
/* 1021 */     } else if (bool1)
/*      */     {
/* 1026 */       this.text.relocate(snapPosition(d8), snapPosition(d9));
/* 1027 */       if (this.containsMnemonic) {
/* 1028 */         this.mnemonic_underscore.setEndX(d11 - 2.0D);
/* 1029 */         this.mnemonic_underscore.relocate(d8 + d10, d9 + d12 - 1.0D);
/*      */       }
/*      */     }
/* 1032 */     else if (bool2)
/*      */     {
/* 1036 */       this.text.relocate(snapPosition(d8), snapPosition(d9));
/* 1037 */       this.graphic.relocate(snapPosition(d8), snapPosition(d9));
/* 1038 */       if (this.containsMnemonic) {
/* 1039 */         this.mnemonic_underscore.setEndX(d11);
/* 1040 */         this.mnemonic_underscore.setStrokeWidth(d12 / 10.0D);
/* 1041 */         this.mnemonic_underscore.relocate(d8 + d10, d9 + d12 - 1.0D);
/*      */       }
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/* 1047 */       double d13 = 0.0D;
/* 1048 */       double d14 = 0.0D;
/* 1049 */       double d15 = 0.0D;
/* 1050 */       double d16 = 0.0D;
/*      */ 
/* 1052 */       if (localContentDisplay == ContentDisplay.TOP) {
/* 1053 */         d13 = d8 + (d6 - d1) / 2.0D;
/* 1054 */         d15 = d8 + (d6 - d3) / 2.0D;
/*      */ 
/* 1057 */         d14 = d9;
/* 1058 */         d16 = d14 + d2 + d5;
/* 1059 */       } else if (localContentDisplay == ContentDisplay.RIGHT)
/*      */       {
/* 1061 */         d15 = d8;
/* 1062 */         d13 = d15 + d3 + d5;
/* 1063 */         d14 = d9 + (d7 - d2) / 2.0D;
/* 1064 */         d16 = d9 + (d7 - d4) / 2.0D;
/* 1065 */       } else if (localContentDisplay == ContentDisplay.BOTTOM) {
/* 1066 */         d13 = d8 + (d6 - d1) / 2.0D;
/* 1067 */         d15 = d8 + (d6 - d3) / 2.0D;
/*      */ 
/* 1069 */         d16 = d9;
/* 1070 */         d14 = d16 + d4 + d5;
/* 1071 */       } else if (localContentDisplay == ContentDisplay.LEFT)
/*      */       {
/* 1074 */         d13 = d8;
/* 1075 */         d15 = d13 + d1 + d5;
/* 1076 */         d14 = d9 + (d7 - d2) / 2.0D;
/* 1077 */         d16 = d9 + (d7 - d4) / 2.0D;
/* 1078 */       } else if (localContentDisplay == ContentDisplay.CENTER) {
/* 1079 */         d13 = d8 + (d6 - d1) / 2.0D;
/* 1080 */         d15 = d8 + (d6 - d3) / 2.0D;
/* 1081 */         d14 = d9 + (d7 - d2) / 2.0D;
/* 1082 */         d16 = d9 + (d7 - d4) / 2.0D;
/*      */       }
/* 1084 */       this.text.relocate(snapPosition(d15), snapPosition(d16));
/* 1085 */       if (this.containsMnemonic) {
/* 1086 */         this.mnemonic_underscore.setEndX(d11);
/* 1087 */         this.mnemonic_underscore.setStrokeWidth(d12 / 10.0D);
/* 1088 */         this.mnemonic_underscore.relocate(snapPosition(d15 + d10), snapPosition(d16 + d12 - 1.0D));
/*      */       }
/* 1090 */       this.graphic.relocate(snapPosition(d13), snapPosition(d14));
/*      */     }
/*      */ 
/* 1093 */     this.textClip.setX(this.text.getLayoutBounds().getMinX());
/* 1094 */     this.textClip.setY(this.text.getLayoutBounds().getMinY());
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.LabeledSkinBase
 * JD-Core Version:    0.6.2
 */