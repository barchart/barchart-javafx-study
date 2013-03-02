/*      */ package com.sun.javafx.scene.control.skin;
/*      */ 
/*      */ import com.sun.javafx.PlatformUtil;
/*      */ import com.sun.javafx.scene.control.behavior.TextAreaBehavior;
/*      */ import com.sun.javafx.scene.text.HitInfo;
/*      */ import com.sun.javafx.tk.FontMetrics;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import javafx.animation.KeyFrame;
/*      */ import javafx.animation.KeyValue;
/*      */ import javafx.animation.Timeline;
/*      */ import javafx.application.Platform;
/*      */ import javafx.beans.InvalidationListener;
/*      */ import javafx.beans.Observable;
/*      */ import javafx.beans.binding.BooleanBinding;
/*      */ import javafx.beans.binding.IntegerBinding;
/*      */ import javafx.beans.property.BooleanProperty;
/*      */ import javafx.beans.property.DoubleProperty;
/*      */ import javafx.beans.property.IntegerProperty;
/*      */ import javafx.beans.property.ObjectProperty;
/*      */ import javafx.beans.property.ReadOnlyObjectProperty;
/*      */ import javafx.beans.property.StringProperty;
/*      */ import javafx.beans.value.ChangeListener;
/*      */ import javafx.beans.value.ObservableBooleanValue;
/*      */ import javafx.beans.value.ObservableIntegerValue;
/*      */ import javafx.beans.value.ObservableObjectValue;
/*      */ import javafx.beans.value.ObservableValue;
/*      */ import javafx.collections.ObservableList;
/*      */ import javafx.event.ActionEvent;
/*      */ import javafx.event.EventHandler;
/*      */ import javafx.geometry.Bounds;
/*      */ import javafx.geometry.Insets;
/*      */ import javafx.geometry.Orientation;
/*      */ import javafx.geometry.Point2D;
/*      */ import javafx.geometry.Rectangle2D;
/*      */ import javafx.geometry.VPos;
/*      */ import javafx.geometry.VerticalDirection;
/*      */ import javafx.scene.Group;
/*      */ import javafx.scene.Node;
/*      */ import javafx.scene.Parent;
/*      */ import javafx.scene.control.IndexRange;
/*      */ import javafx.scene.control.ScrollPane;
/*      */ import javafx.scene.control.ScrollPane.ScrollBarPolicy;
/*      */ import javafx.scene.control.TextArea;
/*      */ import javafx.scene.input.MouseEvent;
/*      */ import javafx.scene.input.ScrollEvent;
/*      */ import javafx.scene.layout.Region;
/*      */ import javafx.scene.layout.StackPane;
/*      */ import javafx.scene.paint.Paint;
/*      */ import javafx.scene.shape.MoveTo;
/*      */ import javafx.scene.shape.Path;
/*      */ import javafx.scene.shape.PathElement;
/*      */ import javafx.scene.text.Text;
/*      */ import javafx.util.Duration;
/*      */ 
/*      */ public class TextAreaSkin extends TextInputControlSkin<TextArea, TextAreaBehavior>
/*      */ {
/*   77 */   private final boolean USE_MULTIPLE_NODES = false;
/*      */ 
/*   79 */   private double computedMinWidth = (-1.0D / 0.0D);
/*   80 */   private double computedMinHeight = (-1.0D / 0.0D);
/*   81 */   private double computedPrefWidth = (-1.0D / 0.0D);
/*   82 */   private double computedPrefHeight = (-1.0D / 0.0D);
/*   83 */   private double widthForComputedPrefHeight = (-1.0D / 0.0D);
/*      */   private double characterWidth;
/*      */   private double lineHeight;
/*  369 */   private ContentView contentView = new ContentView(null);
/*  370 */   private Group paragraphNodes = new Group();
/*      */   private Text promptNode;
/*      */   private ObservableBooleanValue usePromptText;
/*      */   private ObservableIntegerValue caretPosition;
/*  376 */   private Group selectionHighlightGroup = new Group();
/*      */   private ScrollPane scrollPane;
/*      */   private Bounds oldViewportBounds;
/*  381 */   private VerticalDirection scrollDirection = null;
/*      */ 
/*  383 */   private Path characterBoundingPath = new Path();
/*      */ 
/*  385 */   private Timeline scrollSelectionTimeline = new Timeline();
/*  386 */   private EventHandler<ActionEvent> scrollSelectionHandler = new EventHandler()
/*      */   {
/*      */     public void handle(ActionEvent paramAnonymousActionEvent) {
/*  389 */       switch (TextAreaSkin.25.$SwitchMap$javafx$geometry$VerticalDirection[TextAreaSkin.this.scrollDirection.ordinal()])
/*      */       {
/*      */       case 1:
/*  392 */         break;
/*      */       case 2:
/*      */       }
/*      */     }
/*  386 */   };
/*      */   public static final int SCROLL_RATE = 30;
/*      */   private double pressX;
/*      */   private double pressY;
/* 1120 */   double targetCaretX = -1.0D;
/*      */ 
/*      */   protected void invalidateMetrics()
/*      */   {
/*   88 */     this.computedMinWidth = (-1.0D / 0.0D);
/*   89 */     this.computedMinHeight = (-1.0D / 0.0D);
/*   90 */     this.computedPrefWidth = (-1.0D / 0.0D);
/*   91 */     this.computedPrefHeight = (-1.0D / 0.0D);
/*      */   }
/*      */ 
/*      */   public TextAreaSkin(final TextArea paramTextArea)
/*      */   {
/*  408 */     super(paramTextArea, new TextAreaBehavior(paramTextArea));
/*  409 */     ((TextAreaBehavior)getBehavior()).setTextAreaSkin(this);
/*      */ 
/*  411 */     this.caretPosition = new IntegerBinding()
/*      */     {
/*      */       protected int computeValue() {
/*  414 */         return paramTextArea.getCaretPosition();
/*      */       }
/*      */     };
/*  417 */     this.caretPosition.addListener(new ChangeListener() {
/*      */       public void changed(ObservableValue<? extends Number> paramAnonymousObservableValue, Number paramAnonymousNumber1, Number paramAnonymousNumber2) {
/*  419 */         TextAreaSkin.this.targetCaretX = -1.0D;
/*      */       }
/*      */     });
/*  423 */     setManaged(false);
/*      */ 
/*  426 */     this.scrollPane = new ScrollPane();
/*  427 */     this.scrollPane.setMinWidth(0.0D);
/*  428 */     this.scrollPane.setMinHeight(0.0D);
/*  429 */     this.scrollPane.setFitToWidth(paramTextArea.isWrapText());
/*  430 */     this.scrollPane.setContent(this.contentView);
/*  431 */     getChildren().add(this.scrollPane);
/*      */ 
/*  435 */     if (!PlatformUtil.isEmbedded()) {
/*  436 */       this.scrollPane.addEventFilter(ScrollEvent.ANY, new EventHandler() {
/*      */         public void handle(ScrollEvent paramAnonymousScrollEvent) {
/*  438 */           if (paramAnonymousScrollEvent.isDirect()) {
/*  439 */             paramAnonymousScrollEvent.consume();
/*      */           }
/*      */         }
/*      */ 
/*      */       });
/*      */     }
/*      */ 
/*  446 */     this.selectionHighlightGroup.setManaged(false);
/*  447 */     this.selectionHighlightGroup.setVisible(false);
/*  448 */     this.contentView.getChildren().add(this.selectionHighlightGroup);
/*      */ 
/*  451 */     this.paragraphNodes.setManaged(false);
/*  452 */     this.contentView.getChildren().add(this.paragraphNodes);
/*      */ 
/*  455 */     this.caretPath.setManaged(false);
/*  456 */     this.caretPath.setStrokeWidth(1.0D);
/*  457 */     this.caretPath.fillProperty().bind(this.textFill);
/*  458 */     this.caretPath.strokeProperty().bind(this.textFill);
/*  459 */     this.caretPath.visibleProperty().bind(this.caretVisible);
/*  460 */     this.contentView.getChildren().add(this.caretPath);
/*      */ 
/*  462 */     if (PlatformUtil.isEmbedded()) {
/*  463 */       this.contentView.getChildren().addAll(new Node[] { this.caretHandle, this.selectionHandle1, this.selectionHandle2 });
/*      */     }
/*      */ 
/*  466 */     this.scrollPane.hvalueProperty().addListener(new ChangeListener()
/*      */     {
/*      */       public void changed(ObservableValue<? extends Number> paramAnonymousObservableValue, Number paramAnonymousNumber1, Number paramAnonymousNumber2) {
/*  469 */         ((TextArea)TextAreaSkin.this.getSkinnable()).setScrollLeft(paramAnonymousNumber2.doubleValue() * TextAreaSkin.this.getScrollLeftMax());
/*      */       }
/*      */     });
/*  473 */     this.scrollPane.vvalueProperty().addListener(new ChangeListener()
/*      */     {
/*      */       public void changed(ObservableValue<? extends Number> paramAnonymousObservableValue, Number paramAnonymousNumber1, Number paramAnonymousNumber2) {
/*  476 */         ((TextArea)TextAreaSkin.this.getSkinnable()).setScrollTop(paramAnonymousNumber2.doubleValue() * TextAreaSkin.this.getScrollTopMax());
/*      */       }
/*      */     });
/*  481 */     this.scrollSelectionTimeline.setCycleCount(-1);
/*  482 */     ObservableList localObservableList = this.scrollSelectionTimeline.getKeyFrames();
/*  483 */     localObservableList.clear();
/*  484 */     localObservableList.add(new KeyFrame(Duration.millis(350.0D), this.scrollSelectionHandler, new KeyValue[0]));
/*      */ 
/*  487 */     int i = 0; for (int j = 1; i < j; i++) {
/*  488 */       CharSequence localCharSequence = j == 1 ? paramTextArea.textProperty().getValueSafe() : (CharSequence)paramTextArea.getParagraphs().get(i);
/*  489 */       addParagraphNode(i, localCharSequence.toString());
/*      */     }
/*      */ 
/*  492 */     paramTextArea.selectionProperty().addListener(new ChangeListener()
/*      */     {
/*      */       public void changed(ObservableValue<? extends IndexRange> paramAnonymousObservableValue, IndexRange paramAnonymousIndexRange1, IndexRange paramAnonymousIndexRange2)
/*      */       {
/*  496 */         TextAreaSkin.this.requestLayout();
/*  497 */         TextAreaSkin.this.contentView.requestLayout();
/*      */       }
/*      */     });
/*  501 */     paramTextArea.wrapTextProperty().addListener(new ChangeListener()
/*      */     {
/*      */       public void changed(ObservableValue<? extends Boolean> paramAnonymousObservableValue, Boolean paramAnonymousBoolean1, Boolean paramAnonymousBoolean2) {
/*  504 */         TextAreaSkin.this.invalidateMetrics();
/*  505 */         TextAreaSkin.this.scrollPane.setFitToWidth(paramAnonymousBoolean2.booleanValue());
/*      */       }
/*      */     });
/*  509 */     paramTextArea.prefColumnCountProperty().addListener(new ChangeListener()
/*      */     {
/*      */       public void changed(ObservableValue<? extends Number> paramAnonymousObservableValue, Number paramAnonymousNumber1, Number paramAnonymousNumber2) {
/*  512 */         TextAreaSkin.this.invalidateMetrics();
/*  513 */         TextAreaSkin.this.updatePrefViewportWidth();
/*      */       }
/*      */     });
/*  517 */     paramTextArea.prefRowCountProperty().addListener(new ChangeListener()
/*      */     {
/*      */       public void changed(ObservableValue<? extends Number> paramAnonymousObservableValue, Number paramAnonymousNumber1, Number paramAnonymousNumber2) {
/*  520 */         TextAreaSkin.this.invalidateMetrics();
/*  521 */         TextAreaSkin.this.updatePrefViewportHeight();
/*      */       }
/*      */     });
/*  525 */     updateFontMetrics();
/*  526 */     this.fontMetrics.addListener(new InvalidationListener() {
/*      */       public void invalidated(Observable paramAnonymousObservable) {
/*  528 */         TextAreaSkin.this.updateFontMetrics();
/*      */       }
/*      */     });
/*  532 */     this.contentView.paddingProperty().addListener(new InvalidationListener() {
/*      */       public void invalidated(Observable paramAnonymousObservable) {
/*  534 */         TextAreaSkin.this.updatePrefViewportWidth();
/*  535 */         TextAreaSkin.this.updatePrefViewportHeight();
/*      */       }
/*      */     });
/*  539 */     this.scrollPane.viewportBoundsProperty().addListener(new InvalidationListener() {
/*      */       public void invalidated(Observable paramAnonymousObservable) {
/*  541 */         if (TextAreaSkin.this.scrollPane.getViewportBounds() != null)
/*      */         {
/*  545 */           Bounds localBounds = TextAreaSkin.this.scrollPane.getViewportBounds();
/*  546 */           if ((TextAreaSkin.this.oldViewportBounds == null) || (TextAreaSkin.this.oldViewportBounds.getWidth() != localBounds.getWidth()) || (TextAreaSkin.this.oldViewportBounds.getHeight() != localBounds.getHeight()))
/*      */           {
/*  550 */             TextAreaSkin.this.invalidateMetrics();
/*  551 */             TextAreaSkin.this.oldViewportBounds = localBounds;
/*  552 */             TextAreaSkin.this.contentView.requestLayout();
/*      */           }
/*      */         }
/*      */       }
/*      */     });
/*  558 */     paramTextArea.scrollTopProperty().addListener(new ChangeListener()
/*      */     {
/*      */       public void changed(ObservableValue<? extends Number> paramAnonymousObservableValue, Number paramAnonymousNumber1, Number paramAnonymousNumber2) {
/*  561 */         double d = paramAnonymousNumber2.doubleValue() < TextAreaSkin.this.getScrollTopMax() ? paramAnonymousNumber2.doubleValue() / TextAreaSkin.this.getScrollTopMax() : 1.0D;
/*      */ 
/*  563 */         TextAreaSkin.this.scrollPane.setVvalue(d);
/*      */       }
/*      */     });
/*  567 */     paramTextArea.scrollLeftProperty().addListener(new ChangeListener()
/*      */     {
/*      */       public void changed(ObservableValue<? extends Number> paramAnonymousObservableValue, Number paramAnonymousNumber1, Number paramAnonymousNumber2) {
/*  570 */         double d = paramAnonymousNumber2.doubleValue() < TextAreaSkin.this.getScrollLeftMax() ? paramAnonymousNumber2.doubleValue() / TextAreaSkin.this.getScrollLeftMax() : 1.0D;
/*      */ 
/*  572 */         TextAreaSkin.this.scrollPane.setHvalue(d);
/*      */       }
/*      */     });
/*  607 */     paramTextArea.textProperty().addListener(new InvalidationListener() {
/*      */       public void invalidated(Observable paramAnonymousObservable) {
/*  609 */         TextAreaSkin.this.invalidateMetrics();
/*  610 */         ((Text)TextAreaSkin.this.paragraphNodes.getChildren().get(0)).setText(paramTextArea.textProperty().getValueSafe());
/*  611 */         TextAreaSkin.this.contentView.requestLayout();
/*      */       }
/*      */     });
/*  616 */     this.usePromptText = new BooleanBinding()
/*      */     {
/*      */       protected boolean computeValue() {
/*  619 */         String str1 = paramTextArea.getText();
/*  620 */         String str2 = paramTextArea.getPromptText();
/*  621 */         return ((str1 == null) || (str1.isEmpty())) && (str2 != null) && (!str2.isEmpty());
/*      */       }
/*      */     };
/*  626 */     if (this.usePromptText.get()) {
/*  627 */       createPromptNode();
/*      */     }
/*      */ 
/*  630 */     this.usePromptText.addListener(new InvalidationListener() {
/*      */       public void invalidated(Observable paramAnonymousObservable) {
/*  632 */         TextAreaSkin.this.createPromptNode();
/*  633 */         TextAreaSkin.this.requestLayout();
/*      */       }
/*      */     });
/*  637 */     updateHighlightFill();
/*  638 */     updatePrefViewportWidth();
/*  639 */     updatePrefViewportHeight();
/*  640 */     if (paramTextArea.isFocused()) setCaretAnimating(true);
/*      */ 
/*  642 */     if (PlatformUtil.isEmbedded()) {
/*  643 */       this.selectionHandle1.setRotate(180.0D);
/*      */ 
/*  645 */       EventHandler local20 = new EventHandler() {
/*      */         public void handle(MouseEvent paramAnonymousMouseEvent) {
/*  647 */           TextAreaSkin.this.pressX = paramAnonymousMouseEvent.getX();
/*  648 */           TextAreaSkin.this.pressY = paramAnonymousMouseEvent.getY();
/*  649 */           paramAnonymousMouseEvent.consume();
/*      */         }
/*      */       };
/*  653 */       this.caretHandle.setOnMousePressed(local20);
/*  654 */       this.selectionHandle1.setOnMousePressed(local20);
/*  655 */       this.selectionHandle2.setOnMousePressed(local20);
/*      */ 
/*  657 */       this.caretHandle.setOnMouseDragged(new EventHandler() {
/*      */         public void handle(MouseEvent paramAnonymousMouseEvent) {
/*  659 */           Text localText = TextAreaSkin.this.getTextNode();
/*  660 */           Point2D localPoint2D1 = localText.localToScene(0.0D, 0.0D);
/*  661 */           Point2D localPoint2D2 = new Point2D(paramAnonymousMouseEvent.getSceneX() - localPoint2D1.getX() + 10.0D - TextAreaSkin.this.pressX + TextAreaSkin.this.caretHandle.getWidth() / 2.0D, paramAnonymousMouseEvent.getSceneY() - localPoint2D1.getY() - TextAreaSkin.this.pressY - 6.0D);
/*      */ 
/*  663 */           HitInfo localHitInfo = localText.impl_hitTestChar(TextAreaSkin.this.translateCaretPosition(localPoint2D2));
/*  664 */           int i = localHitInfo.getCharIndex();
/*  665 */           if (i > 0) {
/*  666 */             int j = localText.getImpl_caretPosition();
/*  667 */             localText.setImpl_caretPosition(i);
/*  668 */             PathElement localPathElement = localText.getImpl_caretShape()[0];
/*  669 */             if (((localPathElement instanceof MoveTo)) && (((MoveTo)localPathElement).getY() > paramAnonymousMouseEvent.getY() - TextAreaSkin.this.getTextTranslateY())) {
/*  670 */               localHitInfo.setCharIndex(i - 1);
/*      */             }
/*  672 */             localText.setImpl_caretPosition(j);
/*      */           }
/*  674 */           TextAreaSkin.this.positionCaret(localHitInfo, false, false);
/*  675 */           paramAnonymousMouseEvent.consume();
/*      */         }
/*      */       });
/*  679 */       this.selectionHandle1.setOnMouseDragged(new EventHandler() {
/*      */         public void handle(MouseEvent paramAnonymousMouseEvent) {
/*  681 */           TextArea localTextArea = (TextArea)TextAreaSkin.this.getSkinnable();
/*  682 */           Text localText = TextAreaSkin.this.getTextNode();
/*  683 */           Point2D localPoint2D1 = localText.localToScene(0.0D, 0.0D);
/*  684 */           Point2D localPoint2D2 = new Point2D(paramAnonymousMouseEvent.getSceneX() - localPoint2D1.getX() + 10.0D - TextAreaSkin.this.pressX + TextAreaSkin.this.selectionHandle1.getWidth() / 2.0D, paramAnonymousMouseEvent.getSceneY() - localPoint2D1.getY() - TextAreaSkin.this.pressY + TextAreaSkin.this.selectionHandle1.getHeight() + 5.0D);
/*      */ 
/*  686 */           HitInfo localHitInfo = localText.impl_hitTestChar(TextAreaSkin.this.translateCaretPosition(localPoint2D2));
/*  687 */           int i = localHitInfo.getCharIndex();
/*  688 */           if (localTextArea.getAnchor() < localTextArea.getCaretPosition())
/*      */           {
/*  690 */             localTextArea.selectRange(localTextArea.getCaretPosition(), localTextArea.getAnchor());
/*      */           }
/*  692 */           if (i >= 0) {
/*  693 */             if (i >= localTextArea.getAnchor()) {
/*  694 */               i = localTextArea.getAnchor();
/*      */             }
/*  696 */             int j = localText.getImpl_caretPosition();
/*  697 */             localText.setImpl_caretPosition(i);
/*  698 */             PathElement localPathElement = localText.getImpl_caretShape()[0];
/*  699 */             if (((localPathElement instanceof MoveTo)) && (((MoveTo)localPathElement).getY() > paramAnonymousMouseEvent.getY() - TextAreaSkin.this.getTextTranslateY())) {
/*  700 */               localHitInfo.setCharIndex(i - 1);
/*      */             }
/*  702 */             localText.setImpl_caretPosition(j);
/*  703 */             TextAreaSkin.this.positionCaret(localHitInfo, true, false);
/*      */           }
/*  705 */           paramAnonymousMouseEvent.consume();
/*      */         }
/*      */       });
/*  709 */       this.selectionHandle2.setOnMouseDragged(new EventHandler() {
/*      */         public void handle(MouseEvent paramAnonymousMouseEvent) {
/*  711 */           TextArea localTextArea = (TextArea)TextAreaSkin.this.getSkinnable();
/*  712 */           Text localText = TextAreaSkin.this.getTextNode();
/*  713 */           Point2D localPoint2D1 = localText.localToScene(0.0D, 0.0D);
/*  714 */           Point2D localPoint2D2 = new Point2D(paramAnonymousMouseEvent.getSceneX() - localPoint2D1.getX() + 10.0D - TextAreaSkin.this.pressX + TextAreaSkin.this.selectionHandle2.getWidth() / 2.0D, paramAnonymousMouseEvent.getSceneY() - localPoint2D1.getY() - TextAreaSkin.this.pressY - 6.0D);
/*      */ 
/*  716 */           HitInfo localHitInfo = localText.impl_hitTestChar(TextAreaSkin.this.translateCaretPosition(localPoint2D2));
/*  717 */           int i = localHitInfo.getCharIndex();
/*  718 */           if (localTextArea.getAnchor() > localTextArea.getCaretPosition())
/*      */           {
/*  720 */             localTextArea.selectRange(localTextArea.getCaretPosition(), localTextArea.getAnchor());
/*      */           }
/*  722 */           if (i > 0) {
/*  723 */             if (i <= localTextArea.getAnchor() + 1) {
/*  724 */               i = Math.min(localTextArea.getAnchor() + 2, localTextArea.getLength());
/*      */             }
/*  726 */             int j = localText.getImpl_caretPosition();
/*  727 */             localText.setImpl_caretPosition(i);
/*  728 */             PathElement localPathElement = localText.getImpl_caretShape()[0];
/*  729 */             if (((localPathElement instanceof MoveTo)) && (((MoveTo)localPathElement).getY() > paramAnonymousMouseEvent.getY() - TextAreaSkin.this.getTextTranslateY())) {
/*  730 */               localHitInfo.setCharIndex(i - 1);
/*      */             }
/*  732 */             localText.setImpl_caretPosition(j);
/*  733 */             TextAreaSkin.this.positionCaret(localHitInfo, true, false);
/*      */           }
/*  735 */           paramAnonymousMouseEvent.consume();
/*      */         }
/*      */       });
/*      */     }
/*      */   }
/*      */ 
/*      */   private void createPromptNode() {
/*  742 */     if ((this.promptNode == null) && (this.usePromptText.get())) {
/*  743 */       this.promptNode = new Text();
/*  744 */       this.contentView.getChildren().add(0, this.promptNode);
/*  745 */       this.promptNode.setManaged(false);
/*  746 */       this.promptNode.getStyleClass().add("text");
/*  747 */       this.promptNode.visibleProperty().bind(this.usePromptText);
/*  748 */       this.promptNode.fontProperty().bind(this.font);
/*  749 */       this.promptNode.textProperty().bind(((TextArea)getSkinnable()).promptTextProperty());
/*  750 */       this.promptNode.fillProperty().bind(this.promptTextFill);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void addParagraphNode(int paramInt, String paramString) {
/*  755 */     TextArea localTextArea = (TextArea)getSkinnable();
/*  756 */     Text localText = new Text(paramString);
/*  757 */     localText.setTextOrigin(VPos.TOP);
/*  758 */     localText.setManaged(false);
/*  759 */     localText.getStyleClass().add("text");
/*  760 */     this.paragraphNodes.getChildren().add(paramInt, localText);
/*      */ 
/*  762 */     localText.fontProperty().bind(this.font);
/*  763 */     localText.fillProperty().bind(this.textFill);
/*  764 */     localText.impl_selectionFillProperty().bind(this.highlightTextFill);
/*      */   }
/*      */ 
/*      */   public void layoutChildren() {
/*  768 */     this.scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
/*  769 */     this.scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
/*      */ 
/*  771 */     super.layoutChildren();
/*      */ 
/*  773 */     Bounds localBounds = this.scrollPane.getViewportBounds();
/*  774 */     if ((localBounds != null) && ((localBounds.getWidth() < this.contentView.minWidth(-1.0D)) || (localBounds.getHeight() < this.contentView.minHeight(-1.0D))))
/*      */     {
/*  776 */       this.scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
/*  777 */       this.scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void dispose()
/*      */   {
/*  784 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */   public double getBaselineOffset()
/*      */   {
/*  789 */     return ((FontMetrics)this.fontMetrics.get()).getAscent() + getInsets().getTop();
/*      */   }
/*      */ 
/*      */   public char getCharacter(int paramInt)
/*      */   {
/*  794 */     int i = this.paragraphNodes.getChildren().size();
/*      */ 
/*  796 */     int j = 0;
/*  797 */     int k = paramInt;
/*      */ 
/*  799 */     String str = null;
/*  800 */     while (j < i) {
/*  801 */       Text localText = (Text)this.paragraphNodes.getChildren().get(j);
/*  802 */       str = localText.getText();
/*  803 */       int m = str.length() + 1;
/*      */ 
/*  805 */       if (k < m)
/*      */       {
/*      */         break;
/*      */       }
/*  809 */       k -= m;
/*  810 */       j++;
/*      */     }
/*      */ 
/*  813 */     return k == str.length() ? '\n' : str.charAt(k);
/*      */   }
/*      */ 
/*      */   public int getInsertionPoint(double paramDouble1, double paramDouble2)
/*      */   {
/*  818 */     TextArea localTextArea = (TextArea)getSkinnable();
/*      */ 
/*  820 */     int i = this.paragraphNodes.getChildren().size();
/*  821 */     int j = -1;
/*      */ 
/*  823 */     if (i > 0) {
/*  824 */       Insets localInsets = this.contentView.getInsets();
/*      */ 
/*  826 */       if (paramDouble2 < localInsets.getTop())
/*      */       {
/*  828 */         Text localText1 = (Text)this.paragraphNodes.getChildren().get(0);
/*  829 */         j = getNextInsertionPoint(localText1, paramDouble1, -1, VerticalDirection.DOWN);
/*      */       }
/*      */       else
/*      */       {
/*      */         int k;
/*  830 */         if (paramDouble2 > localInsets.getTop() + this.contentView.getHeight())
/*      */         {
/*  832 */           k = i - 1;
/*  833 */           Text localText2 = (Text)this.paragraphNodes.getChildren().get(k);
/*      */ 
/*  835 */           j = getNextInsertionPoint(localText2, paramDouble1, -1, VerticalDirection.UP) + (localTextArea.getLength() - localText2.getText().length());
/*      */         }
/*      */         else
/*      */         {
/*  839 */           k = 0;
/*  840 */           for (int m = 0; m < i; m++) {
/*  841 */             Text localText3 = (Text)this.paragraphNodes.getChildren().get(m);
/*      */ 
/*  843 */             Bounds localBounds = localText3.getBoundsInLocal();
/*  844 */             double d = localText3.getLayoutY() + localBounds.getMinY();
/*  845 */             if ((paramDouble2 >= d) && (paramDouble2 < d + localText3.getBoundsInLocal().getHeight()))
/*      */             {
/*  847 */               j = getInsertionPoint(localText3, paramDouble1 - localText3.getLayoutX(), paramDouble2 - localText3.getLayoutY()) + k;
/*      */ 
/*  850 */               break;
/*      */             }
/*      */ 
/*  853 */             k += localText3.getText().length() + 1;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  858 */     return j;
/*      */   }
/*      */ 
/*      */   public void positionCaret(HitInfo paramHitInfo, boolean paramBoolean1, boolean paramBoolean2) {
/*  862 */     int i = paramHitInfo.getInsertionIndex();
/*  863 */     int j = (i > 0) && (i < ((TextArea)getSkinnable()).getLength()) && (((TextArea)getSkinnable()).getText().codePointAt(i - 1) == 10) ? 1 : 0;
/*      */ 
/*  869 */     if ((!paramHitInfo.isLeading()) && (j != 0)) {
/*  870 */       paramHitInfo.setLeading(true);
/*  871 */       i--;
/*      */     }
/*      */ 
/*  874 */     if (paramBoolean1) {
/*  875 */       if (paramBoolean2)
/*  876 */         ((TextArea)getSkinnable()).extendSelection(i);
/*      */       else
/*  878 */         ((TextArea)getSkinnable()).selectPositionCaret(i);
/*      */     }
/*      */     else
/*  881 */       ((TextArea)getSkinnable()).positionCaret(i);
/*      */   }
/*      */ 
/*      */   private double getScrollTopMax()
/*      */   {
/*  888 */     return Math.max(0.0D, this.contentView.getHeight() - this.scrollPane.getViewportBounds().getHeight());
/*      */   }
/*      */ 
/*      */   private double getScrollLeftMax() {
/*  892 */     return Math.max(0.0D, this.contentView.getWidth() - this.scrollPane.getViewportBounds().getWidth());
/*      */   }
/*      */ 
/*      */   private int getInsertionPoint(Text paramText, double paramDouble1, double paramDouble2) {
/*  896 */     HitInfo localHitInfo = paramText.impl_hitTestChar(new Point2D(paramDouble1, paramDouble2));
/*  897 */     return localHitInfo.getInsertionIndex();
/*      */   }
/*      */ 
/*      */   public int getNextInsertionPoint(double paramDouble, int paramInt, VerticalDirection paramVerticalDirection)
/*      */   {
/*  902 */     return 0;
/*      */   }
/*      */ 
/*      */   private int getNextInsertionPoint(Text paramText, double paramDouble, int paramInt, VerticalDirection paramVerticalDirection)
/*      */   {
/*  908 */     return 0;
/*      */   }
/*      */ 
/*      */   public Rectangle2D getCharacterBounds(int paramInt)
/*      */   {
/*  913 */     TextArea localTextArea = (TextArea)getSkinnable();
/*      */ 
/*  915 */     int i = this.paragraphNodes.getChildren().size();
/*  916 */     int j = localTextArea.getLength() + 1;
/*      */ 
/*  918 */     Text localText = null;
/*      */     do {
/*  920 */       localText = (Text)this.paragraphNodes.getChildren().get(--i);
/*  921 */       j -= localText.getText().length() + 1;
/*  922 */     }while (paramInt < j);
/*      */ 
/*  924 */     int k = paramInt - j;
/*  925 */     int m = 0;
/*      */ 
/*  927 */     if (k == localText.getText().length()) {
/*  928 */       k--;
/*  929 */       m = 1;
/*      */     }
/*      */ 
/*  932 */     this.characterBoundingPath.getElements().clear();
/*  933 */     this.characterBoundingPath.getElements().addAll(localText.impl_getRangeShape(k, k + 1));
/*  934 */     this.characterBoundingPath.setLayoutX(localText.getLayoutX());
/*  935 */     this.characterBoundingPath.setLayoutY(localText.getLayoutY());
/*      */ 
/*  937 */     Bounds localBounds = this.characterBoundingPath.getBoundsInLocal();
/*      */ 
/*  939 */     double d1 = localBounds.getMinX() + localText.getLayoutX() - localTextArea.getScrollLeft();
/*  940 */     double d2 = localBounds.getMinY() + localText.getLayoutY() - localTextArea.getScrollTop();
/*      */ 
/*  943 */     double d3 = localBounds.isEmpty() ? 0.0D : localBounds.getWidth();
/*  944 */     double d4 = localBounds.isEmpty() ? 0.0D : localBounds.getHeight();
/*      */ 
/*  946 */     if (m != 0) {
/*  947 */       d1 += d3;
/*  948 */       d3 = 0.0D;
/*      */     }
/*      */ 
/*  951 */     return new Rectangle2D(d1, d2, d3, d4);
/*      */   }
/*      */ 
/*      */   public void scrollCharacterToVisible(final int paramInt)
/*      */   {
/*  959 */     Platform.runLater(new Runnable()
/*      */     {
/*      */       public void run() {
/*  962 */         if (((TextArea)TextAreaSkin.this.getSkinnable()).getLength() == 0) {
/*  963 */           return;
/*      */         }
/*  965 */         Rectangle2D localRectangle2D = TextAreaSkin.this.getCharacterBounds(paramInt);
/*  966 */         TextAreaSkin.this.scrollBoundsToVisible(localRectangle2D);
/*      */       }
/*      */     });
/*      */   }
/*      */ 
/*      */   private void scrollCaretToVisible() {
/*  972 */     TextArea localTextArea = (TextArea)getSkinnable();
/*  973 */     Bounds localBounds = this.caretPath.getLayoutBounds();
/*  974 */     double d1 = localBounds.getMinX() - localTextArea.getScrollLeft();
/*  975 */     double d2 = localBounds.getMinY() - localTextArea.getScrollTop();
/*  976 */     double d3 = localBounds.getWidth();
/*  977 */     double d4 = localBounds.getHeight();
/*      */ 
/*  979 */     if (PlatformUtil.isEmbedded()) {
/*  980 */       if (this.caretHandle.isVisible()) {
/*  981 */         d4 += this.caretHandle.getHeight();
/*  982 */       } else if ((this.selectionHandle1.isVisible()) && (this.selectionHandle2.isVisible())) {
/*  983 */         d1 -= this.selectionHandle1.getWidth() / 2.0D;
/*  984 */         d2 -= this.selectionHandle1.getHeight();
/*  985 */         d3 += this.selectionHandle1.getWidth() / 2.0D + this.selectionHandle2.getWidth() / 2.0D;
/*  986 */         d4 += this.selectionHandle1.getHeight() + this.selectionHandle2.getHeight();
/*      */       }
/*      */     }
/*      */ 
/*  990 */     scrollBoundsToVisible(new Rectangle2D(d1, d2, d3, d4));
/*      */   }
/*      */ 
/*      */   private void scrollBoundsToVisible(Rectangle2D paramRectangle2D) {
/*  994 */     TextArea localTextArea = (TextArea)getSkinnable();
/*  995 */     Bounds localBounds = this.scrollPane.getViewportBounds();
/*  996 */     Insets localInsets = this.contentView.getInsets();
/*      */ 
/*  998 */     double d1 = localBounds.getWidth();
/*  999 */     double d2 = localBounds.getHeight();
/* 1000 */     double d3 = localTextArea.getScrollTop();
/* 1001 */     double d4 = localTextArea.getScrollLeft();
/* 1002 */     double d5 = 6.0D;
/*      */     double d6;
/* 1004 */     if (paramRectangle2D.getMinY() < 0.0D) {
/* 1005 */       d6 = d3 + paramRectangle2D.getMinY();
/* 1006 */       if (d6 <= localInsets.getTop()) {
/* 1007 */         d6 = 0.0D;
/*      */       }
/* 1009 */       localTextArea.setScrollTop(d6);
/* 1010 */     } else if (localInsets.getTop() + paramRectangle2D.getMaxY() > d2) {
/* 1011 */       d6 = d3 + localInsets.getTop() + paramRectangle2D.getMaxY() - d2;
/* 1012 */       if (d6 >= getScrollTopMax() - localInsets.getBottom()) {
/* 1013 */         d6 = getScrollTopMax();
/*      */       }
/* 1015 */       localTextArea.setScrollTop(d6);
/*      */     }
/*      */ 
/* 1019 */     if (paramRectangle2D.getMinX() < 0.0D) {
/* 1020 */       d6 = d4 + paramRectangle2D.getMinX() - d5;
/* 1021 */       if (d6 <= localInsets.getLeft() + d5) {
/* 1022 */         d6 = 0.0D;
/*      */       }
/* 1024 */       localTextArea.setScrollLeft(d6);
/* 1025 */     } else if (localInsets.getLeft() + paramRectangle2D.getMaxX() > d1) {
/* 1026 */       d6 = d4 + localInsets.getLeft() + paramRectangle2D.getMaxX() - d1 + d5;
/* 1027 */       if (d6 >= getScrollLeftMax() - localInsets.getRight() - d5) {
/* 1028 */         d6 = getScrollLeftMax();
/*      */       }
/* 1030 */       localTextArea.setScrollLeft(d6);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void updatePrefViewportWidth() {
/* 1035 */     int i = ((TextArea)getSkinnable()).getPrefColumnCount();
/* 1036 */     Insets localInsets = this.contentView.getInsets();
/* 1037 */     this.scrollPane.setPrefViewportWidth(i * this.characterWidth + localInsets.getLeft() + localInsets.getRight());
/*      */   }
/*      */ 
/*      */   private void updatePrefViewportHeight() {
/* 1041 */     int i = ((TextArea)getSkinnable()).getPrefRowCount();
/* 1042 */     Insets localInsets = this.contentView.getInsets();
/* 1043 */     this.scrollPane.setPrefViewportHeight(i * this.lineHeight + localInsets.getTop() + localInsets.getBottom());
/*      */   }
/*      */ 
/*      */   private void updateFontMetrics() {
/* 1047 */     this.lineHeight = ((FontMetrics)this.fontMetrics.get()).getLineHeight();
/* 1048 */     this.characterWidth = ((FontMetrics)this.fontMetrics.get()).computeStringWidth("W");
/*      */   }
/*      */ 
/*      */   protected void updateHighlightFill()
/*      */   {
/* 1053 */     for (Node localNode : this.selectionHighlightGroup.getChildren()) {
/* 1054 */       Path localPath = (Path)localNode;
/* 1055 */       localPath.setFill((Paint)this.highlightFill.get());
/*      */     }
/*      */   }
/*      */ 
/*      */   private double getTextTranslateX()
/*      */   {
/* 1076 */     return this.contentView.getInsets().getLeft();
/*      */   }
/*      */ 
/*      */   private double getTextTranslateY() {
/* 1080 */     return this.contentView.getInsets().getTop();
/*      */   }
/*      */ 
/*      */   private double getTextLeft() {
/* 1084 */     return 0.0D;
/*      */   }
/*      */ 
/*      */   private Point2D translateCaretPosition(Point2D paramPoint2D) {
/* 1088 */     return paramPoint2D;
/*      */   }
/*      */ 
/*      */   private Text getTextNode()
/*      */   {
/* 1095 */     return (Text)this.paragraphNodes.getChildren().get(0);
/*      */   }
/*      */ 
/*      */   public HitInfo getIndex(MouseEvent paramMouseEvent)
/*      */   {
/* 1101 */     Text localText = getTextNode();
/* 1102 */     Point2D localPoint2D = new Point2D(paramMouseEvent.getX() - localText.getLayoutX(), paramMouseEvent.getY() - getTextTranslateY());
/* 1103 */     HitInfo localHitInfo = localText.impl_hitTestChar(translateCaretPosition(localPoint2D));
/* 1104 */     int i = localHitInfo.getCharIndex();
/* 1105 */     if (i > 0) {
/* 1106 */       int j = localText.getImpl_caretPosition();
/* 1107 */       localText.setImpl_caretPosition(i);
/* 1108 */       PathElement localPathElement = localText.getImpl_caretShape()[0];
/* 1109 */       if (((localPathElement instanceof MoveTo)) && (((MoveTo)localPathElement).getY() > paramMouseEvent.getY() - getTextTranslateY())) {
/* 1110 */         localHitInfo.setCharIndex(i - 1);
/*      */       }
/* 1112 */       localText.setImpl_caretPosition(j);
/*      */     }
/* 1114 */     return localHitInfo;
/*      */   }
/*      */ 
/*      */   protected void downLines(int paramInt, boolean paramBoolean1, boolean paramBoolean2)
/*      */   {
/* 1123 */     Text localText = getTextNode();
/* 1124 */     Bounds localBounds = this.caretPath.getLayoutBounds();
/* 1125 */     double d1 = (localBounds.getMinY() + localBounds.getMaxY()) / 2.0D + paramInt * this.lineHeight;
/* 1126 */     if (d1 < 0.0D) {
/* 1127 */       d1 = 0.0D;
/*      */     }
/* 1129 */     double d2 = this.targetCaretX >= 0.0D ? this.targetCaretX : localBounds.getMaxX() + getTextTranslateX();
/* 1130 */     HitInfo localHitInfo = localText.impl_hitTestChar(translateCaretPosition(new Point2D(d2, d1)));
/* 1131 */     int i = localHitInfo.getCharIndex();
/* 1132 */     if (i > 0) {
/* 1133 */       localText.setImpl_caretPosition(i);
/* 1134 */       PathElement localPathElement = localText.getImpl_caretShape()[0];
/* 1135 */       if (((localPathElement instanceof MoveTo)) && (((MoveTo)localPathElement).getY() > d1)) {
/* 1136 */         localHitInfo.setCharIndex(i - 1);
/*      */       }
/*      */     }
/* 1139 */     positionCaret(localHitInfo, paramBoolean1, paramBoolean2);
/* 1140 */     this.targetCaretX = d2;
/*      */   }
/*      */ 
/*      */   public void previousLine(boolean paramBoolean) {
/* 1144 */     downLines(-1, paramBoolean, false);
/*      */   }
/*      */ 
/*      */   public void nextLine(boolean paramBoolean) {
/* 1148 */     downLines(1, paramBoolean, false);
/*      */   }
/*      */ 
/*      */   public void previousPage(boolean paramBoolean) {
/* 1152 */     downLines(-(int)(this.scrollPane.getViewportBounds().getHeight() / this.lineHeight), paramBoolean, false);
/*      */   }
/*      */ 
/*      */   public void nextPage(boolean paramBoolean)
/*      */   {
/* 1157 */     downLines((int)(this.scrollPane.getViewportBounds().getHeight() / this.lineHeight), paramBoolean, false);
/*      */   }
/*      */ 
/*      */   public void lineStart(boolean paramBoolean1, boolean paramBoolean2)
/*      */   {
/* 1162 */     Bounds localBounds = this.caretPath.getLayoutBounds();
/* 1163 */     double d = (localBounds.getMinY() + localBounds.getMaxY()) / 2.0D;
/* 1164 */     HitInfo localHitInfo = getTextNode().impl_hitTestChar(translateCaretPosition(new Point2D(getTextLeft(), d)));
/* 1165 */     positionCaret(localHitInfo, paramBoolean1, paramBoolean2);
/*      */   }
/*      */ 
/*      */   public void lineEnd(boolean paramBoolean1, boolean paramBoolean2) {
/* 1169 */     this.targetCaretX = 1.7976931348623157E+308D;
/* 1170 */     downLines(0, paramBoolean1, paramBoolean2);
/* 1171 */     this.targetCaretX = -1.0D;
/*      */   }
/*      */ 
/*      */   public void paragraphStart(boolean paramBoolean1, boolean paramBoolean2)
/*      */   {
/* 1176 */     TextArea localTextArea = (TextArea)getSkinnable();
/* 1177 */     String str = localTextArea.textProperty().getValueSafe();
/* 1178 */     int i = localTextArea.getCaretPosition();
/*      */ 
/* 1180 */     if (i > 0) {
/* 1181 */       if ((paramBoolean1) && (str.codePointAt(i - 1) == 10))
/*      */       {
/* 1184 */         i--;
/*      */       }
/*      */ 
/* 1187 */       while ((i > 0) && (str.codePointAt(i - 1) != 10)) {
/* 1188 */         i--;
/*      */       }
/* 1190 */       if (paramBoolean2)
/* 1191 */         localTextArea.selectPositionCaret(i);
/*      */       else
/* 1193 */         localTextArea.positionCaret(i);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void paragraphEnd(boolean paramBoolean1, boolean paramBoolean2)
/*      */   {
/* 1199 */     TextArea localTextArea = (TextArea)getSkinnable();
/* 1200 */     String str = localTextArea.textProperty().getValueSafe();
/* 1201 */     int i = localTextArea.getCaretPosition();
/* 1202 */     int j = str.length();
/*      */ 
/* 1204 */     if (i < j - 1) {
/* 1205 */       if ((paramBoolean1) && (str.codePointAt(i) == 10))
/*      */       {
/* 1208 */         i++;
/*      */       }
/*      */ 
/* 1211 */       while ((i < j - 1) && (str.codePointAt(i) != 10)) {
/* 1212 */         i++;
/*      */       }
/* 1214 */       if (paramBoolean2)
/* 1215 */         localTextArea.selectPositionCaret(i);
/*      */       else
/* 1217 */         localTextArea.positionCaret(i);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected PathElement[] getUnderlineShape(int paramInt1, int paramInt2)
/*      */   {
/* 1223 */     int i = 0;
/* 1224 */     for (Node localNode : this.paragraphNodes.getChildren()) {
/* 1225 */       Text localText = (Text)localNode;
/* 1226 */       int j = i + localText.textProperty().getValueSafe().length();
/* 1227 */       if (j >= paramInt1) {
/* 1228 */         return localText.impl_getUnderlineShape(paramInt1 - i, paramInt2 - i);
/*      */       }
/* 1230 */       i = j + 1;
/*      */     }
/* 1232 */     return null;
/*      */   }
/*      */ 
/*      */   protected PathElement[] getRangeShape(int paramInt1, int paramInt2) {
/* 1236 */     int i = 0;
/* 1237 */     for (Node localNode : this.paragraphNodes.getChildren()) {
/* 1238 */       Text localText = (Text)localNode;
/* 1239 */       int j = i + localText.textProperty().getValueSafe().length();
/* 1240 */       if (j >= paramInt1) {
/* 1241 */         return localText.impl_getRangeShape(paramInt1 - i, paramInt2 - i);
/*      */       }
/* 1243 */       i = j + 1;
/*      */     }
/* 1245 */     return null;
/*      */   }
/*      */ 
/*      */   protected void addHighlight(List<? extends Node> paramList, int paramInt) {
/* 1249 */     int i = 0;
/* 1250 */     Object localObject = null;
/* 1251 */     for (Iterator localIterator = this.paragraphNodes.getChildren().iterator(); localIterator.hasNext(); ) { localNode = (Node)localIterator.next();
/* 1252 */       Text localText = (Text)localNode;
/* 1253 */       int j = i + localText.textProperty().getValueSafe().length();
/* 1254 */       if (j >= paramInt) {
/* 1255 */         localObject = localText;
/* 1256 */         break;
/*      */       }
/* 1258 */       i = j + 1;
/*      */     }
/*      */     Node localNode;
/* 1261 */     if (localObject != null) {
/* 1262 */       for (localIterator = paramList.iterator(); localIterator.hasNext(); ) { localNode = (Node)localIterator.next();
/* 1263 */         localNode.setLayoutX(localObject.getLayoutX());
/* 1264 */         localNode.setLayoutY(localObject.getLayoutY());
/*      */       }
/*      */     }
/* 1267 */     this.contentView.getChildren().addAll(paramList);
/*      */   }
/*      */ 
/*      */   protected void removeHighlight(List<? extends Node> paramList) {
/* 1271 */     this.contentView.getChildren().removeAll(paramList);
/*      */   }
/*      */ 
/*      */   public void deleteChar(boolean paramBoolean)
/*      */   {
/* 1282 */     int i = !((TextArea)getSkinnable()).deleteNextChar() ? 1 : paramBoolean ? 0 : !((TextArea)getSkinnable()).deletePreviousChar() ? 1 : 0;
/*      */ 
/* 1286 */     if (i != 0);
/*      */   }
/*      */ 
/*      */   public Point2D getMenuPosition()
/*      */   {
/* 1294 */     this.contentView.layoutChildren();
/* 1295 */     Point2D localPoint2D = super.getMenuPosition();
/* 1296 */     if (localPoint2D != null) {
/* 1297 */       Insets localInsets = this.contentView.getInsets();
/* 1298 */       localPoint2D = new Point2D(Math.max(0.0D, localPoint2D.getX() - localInsets.getLeft() - ((TextArea)getSkinnable()).getScrollLeft()), Math.max(0.0D, localPoint2D.getY() - localInsets.getTop() - ((TextArea)getSkinnable()).getScrollTop()));
/*      */     }
/*      */ 
/* 1301 */     return localPoint2D;
/*      */   }
/*      */ 
/*      */   private class ContentView extends Region
/*      */   {
/*      */     private ContentView()
/*      */     {
/*   97 */       getStyleClass().add("content");
/*      */ 
/*   99 */       addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler() {
/*      */         public void handle(MouseEvent paramAnonymousMouseEvent) {
/*  101 */           ((TextAreaBehavior)TextAreaSkin.this.getBehavior()).mousePressed(paramAnonymousMouseEvent);
/*  102 */           paramAnonymousMouseEvent.consume();
/*      */         }
/*      */       });
/*  106 */       addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler() {
/*      */         public void handle(MouseEvent paramAnonymousMouseEvent) {
/*  108 */           ((TextAreaBehavior)TextAreaSkin.this.getBehavior()).mouseReleased(paramAnonymousMouseEvent);
/*  109 */           paramAnonymousMouseEvent.consume();
/*      */         }
/*      */       });
/*  113 */       addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler() {
/*      */         public void handle(MouseEvent paramAnonymousMouseEvent) {
/*  115 */           ((TextAreaBehavior)TextAreaSkin.this.getBehavior()).mouseDragged(paramAnonymousMouseEvent);
/*  116 */           paramAnonymousMouseEvent.consume();
/*      */         }
/*      */       });
/*      */     }
/*      */ 
/*      */     protected ObservableList<Node> getChildren()
/*      */     {
/*  123 */       return super.getChildren();
/*      */     }
/*      */ 
/*      */     public Orientation getContentBias() {
/*  127 */       return Orientation.HORIZONTAL;
/*      */     }
/*      */ 
/*      */     protected double computePrefWidth(double paramDouble)
/*      */     {
/*  132 */       if (TextAreaSkin.this.computedPrefWidth < 0.0D)
/*      */       {
/*  134 */         Insets localInsets = getInsets();
/*      */ 
/*  136 */         double d = 0.0D;
/*      */ 
/*  138 */         for (Object localObject = TextAreaSkin.this.paragraphNodes.getChildren().iterator(); ((Iterator)localObject).hasNext(); ) { Node localNode = (Node)((Iterator)localObject).next();
/*  139 */           Text localText = (Text)localNode;
/*  140 */           d = Math.max(d, Utils.computeTextWidth(localText.getFont(), localText.getText(), 0.0D));
/*      */         }
/*      */ 
/*  145 */         d += localInsets.getLeft() + localInsets.getRight();
/*      */ 
/*  147 */         localObject = TextAreaSkin.this.scrollPane.getViewportBounds();
/*  148 */         TextAreaSkin.this.computedPrefWidth = Math.max(d, localObject != null ? ((Bounds)localObject).getWidth() : 0.0D);
/*      */       }
/*  150 */       return TextAreaSkin.this.computedPrefWidth;
/*      */     }
/*      */ 
/*      */     protected double computePrefHeight(double paramDouble)
/*      */     {
/*  155 */       if (paramDouble != TextAreaSkin.this.widthForComputedPrefHeight) {
/*  156 */         TextAreaSkin.this.invalidateMetrics();
/*  157 */         TextAreaSkin.this.widthForComputedPrefHeight = paramDouble;
/*      */       }
/*      */ 
/*  160 */       if (TextAreaSkin.this.computedPrefHeight < 0.0D)
/*      */       {
/*  162 */         Insets localInsets = getInsets();
/*      */         double d1;
/*  165 */         if (paramDouble == -1.0D)
/*  166 */           d1 = 0.0D;
/*      */         else {
/*  168 */           d1 = Math.max(paramDouble - (localInsets.getLeft() + localInsets.getRight()), 0.0D);
/*      */         }
/*      */ 
/*  171 */         double d2 = 0.0D;
/*      */ 
/*  173 */         int i = 0;
/*  174 */         for (Object localObject = TextAreaSkin.this.paragraphNodes.getChildren().iterator(); ((Iterator)localObject).hasNext(); ) { Node localNode = (Node)((Iterator)localObject).next();
/*  175 */           Text localText = (Text)localNode;
/*  176 */           d2 += Utils.computeTextHeight(localText.getFont(), localText.getText(), d1);
/*      */ 
/*  179 */           i++;
/*      */         }
/*      */ 
/*  182 */         d2 += localInsets.getTop() + localInsets.getBottom();
/*      */ 
/*  184 */         localObject = TextAreaSkin.this.scrollPane.getViewportBounds();
/*  185 */         TextAreaSkin.this.computedPrefHeight = Math.max(d2, localObject != null ? ((Bounds)localObject).getHeight() : 0.0D);
/*      */       }
/*  187 */       return TextAreaSkin.this.computedPrefHeight;
/*      */     }
/*      */ 
/*      */     protected double computeMinWidth(double paramDouble) {
/*  191 */       if (TextAreaSkin.this.computedMinWidth < 0.0D) {
/*  192 */         double d = getInsets().getLeft() + getInsets().getRight();
/*  193 */         TextAreaSkin.this.computedMinWidth = Math.min(TextAreaSkin.this.characterWidth + d, computePrefWidth(paramDouble));
/*      */       }
/*  195 */       return TextAreaSkin.this.computedMinWidth;
/*      */     }
/*      */ 
/*      */     protected double computeMinHeight(double paramDouble) {
/*  199 */       if (TextAreaSkin.this.computedMinHeight < 0.0D) {
/*  200 */         double d = getInsets().getTop() + getInsets().getBottom();
/*  201 */         TextAreaSkin.this.computedMinHeight = Math.min(TextAreaSkin.this.lineHeight + d, computePrefHeight(paramDouble));
/*      */       }
/*  203 */       return TextAreaSkin.this.computedMinHeight;
/*      */     }
/*      */ 
/*      */     public void layoutChildren()
/*      */     {
/*  208 */       TextArea localTextArea = (TextArea)TextAreaSkin.this.getSkinnable();
/*  209 */       double d1 = getWidth();
/*      */ 
/*  212 */       Insets localInsets = getInsets();
/*      */ 
/*  214 */       double d2 = Math.max(d1 - (localInsets.getLeft() + localInsets.getRight()), 0.0D);
/*      */ 
/*  216 */       double d3 = localInsets.getTop();
/*      */ 
/*  218 */       for (Object localObject1 = TextAreaSkin.this.paragraphNodes.getChildren().iterator(); ((Iterator)localObject1).hasNext(); ) { localObject2 = (Node)((Iterator)localObject1).next();
/*  219 */         Text localText1 = (Text)localObject2;
/*  220 */         localText1.setWrappingWidth(d2);
/*      */ 
/*  222 */         Bounds localBounds = localText1.getBoundsInLocal();
/*  223 */         localText1.setLayoutX(localInsets.getLeft());
/*  224 */         localText1.setLayoutY(d3);
/*      */ 
/*  226 */         d3 += localBounds.getHeight();
/*      */       }
/*      */ 
/*  229 */       if (TextAreaSkin.this.promptNode != null) {
/*  230 */         TextAreaSkin.this.promptNode.setLayoutX(localInsets.getLeft());
/*  231 */         TextAreaSkin.this.promptNode.setLayoutY(localInsets.getTop() + ((FontMetrics)TextAreaSkin.this.fontMetrics.get()).getAscent());
/*  232 */         TextAreaSkin.this.promptNode.setWrappingWidth(d2);
/*      */       }
/*      */ 
/*  236 */       localObject1 = localTextArea.getSelection();
/*  237 */       Object localObject2 = TextAreaSkin.this.caretPath.getBoundsInParent();
/*      */ 
/*  239 */       TextAreaSkin.this.selectionHighlightGroup.getChildren().clear();
/*      */ 
/*  241 */       int i = localTextArea.getCaretPosition();
/*  242 */       int j = localTextArea.getAnchor();
/*      */       Object localObject4;
/*  244 */       if (PlatformUtil.isEmbedded())
/*      */       {
/*  246 */         if (((IndexRange)localObject1).getLength() > 0) {
/*  247 */           TextAreaSkin.this.selectionHandle1.resize(TextAreaSkin.this.selectionHandle1.prefWidth(-1.0D), TextAreaSkin.this.selectionHandle1.prefHeight(-1.0D));
/*      */ 
/*  249 */           TextAreaSkin.this.selectionHandle2.resize(TextAreaSkin.this.selectionHandle2.prefWidth(-1.0D), TextAreaSkin.this.selectionHandle2.prefHeight(-1.0D));
/*      */         }
/*      */         else {
/*  252 */           TextAreaSkin.this.caretHandle.resize(TextAreaSkin.this.caretHandle.prefWidth(-1.0D), TextAreaSkin.this.caretHandle.prefHeight(-1.0D));
/*      */         }
/*      */ 
/*  258 */         if (((IndexRange)localObject1).getLength() > 0) {
/*  259 */           k = TextAreaSkin.this.paragraphNodes.getChildren().size();
/*  260 */           m = localTextArea.getLength() + 1;
/*  261 */           localObject3 = null;
/*      */           do {
/*  263 */             localObject3 = (Text)TextAreaSkin.this.paragraphNodes.getChildren().get(--k);
/*  264 */             m -= ((Text)localObject3).getText().length() + 1;
/*  265 */           }while (j < m);
/*      */ 
/*  267 */           ((Text)localObject3).setImpl_caretPosition(j - m);
/*  268 */           TextAreaSkin.this.caretPath.getElements().clear();
/*  269 */           TextAreaSkin.this.caretPath.getElements().addAll(((Text)localObject3).getImpl_caretShape());
/*  270 */           TextAreaSkin.this.caretPath.setLayoutX(((Text)localObject3).getLayoutX());
/*  271 */           TextAreaSkin.this.caretPath.setLayoutY(((Text)localObject3).getLayoutY());
/*      */ 
/*  273 */           localObject4 = TextAreaSkin.this.caretPath.getBoundsInParent();
/*  274 */           if (i < j) {
/*  275 */             TextAreaSkin.this.selectionHandle2.setLayoutX(((Bounds)localObject4).getMinX() - TextAreaSkin.this.selectionHandle2.getWidth() / 2.0D);
/*  276 */             TextAreaSkin.this.selectionHandle2.setLayoutY(((Bounds)localObject4).getMaxY() - 1.0D);
/*      */           } else {
/*  278 */             TextAreaSkin.this.selectionHandle1.setLayoutX(((Bounds)localObject4).getMinX() - TextAreaSkin.this.selectionHandle1.getWidth() / 2.0D);
/*  279 */             TextAreaSkin.this.selectionHandle1.setLayoutY(((Bounds)localObject4).getMinY() - TextAreaSkin.this.selectionHandle1.getHeight() + 1.0D);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  286 */       int k = TextAreaSkin.this.paragraphNodes.getChildren().size();
/*  287 */       int m = localTextArea.getLength() + 1;
/*      */ 
/*  289 */       Object localObject3 = null;
/*      */       do {
/*  291 */         localObject3 = (Text)TextAreaSkin.this.paragraphNodes.getChildren().get(--k);
/*  292 */         m -= ((Text)localObject3).getText().length() + 1;
/*  293 */       }while (i < m);
/*      */ 
/*  295 */       ((Text)localObject3).setImpl_caretPosition(i - m);
/*      */ 
/*  297 */       TextAreaSkin.this.caretPath.getElements().clear();
/*  298 */       TextAreaSkin.this.caretPath.getElements().addAll(((Text)localObject3).getImpl_caretShape());
/*      */ 
/*  300 */       TextAreaSkin.this.caretPath.setLayoutX(((Text)localObject3).getLayoutX());
/*  301 */       TextAreaSkin.this.caretPath.setLayoutY(((Text)localObject3).getLayoutY());
/*  302 */       if ((localObject2 == null) || (!localObject2.equals(TextAreaSkin.this.caretPath.getBoundsInParent()))) {
/*  303 */         TextAreaSkin.this.scrollCaretToVisible();
/*      */       }
/*      */ 
/*  308 */       k = ((IndexRange)localObject1).getStart();
/*  309 */       m = ((IndexRange)localObject1).getEnd();
/*  310 */       for (localObject3 = TextAreaSkin.this.paragraphNodes.getChildren().iterator(); ((Iterator)localObject3).hasNext(); ) { localObject4 = (Node)((Iterator)localObject3).next();
/*  311 */         Text localText2 = (Text)localObject4;
/*  312 */         int n = localText2.getText().length() + 1;
/*  313 */         if ((m > k) && (k < n)) {
/*  314 */           localText2.setImpl_selectionStart(k);
/*  315 */           localText2.setImpl_selectionEnd(Math.min(m, n));
/*      */ 
/*  317 */           Path localPath = new Path();
/*  318 */           localPath.setManaged(false);
/*  319 */           localPath.setStroke(null);
/*  320 */           PathElement[] arrayOfPathElement = localText2.getImpl_selectionShape();
/*  321 */           if (arrayOfPathElement != null) {
/*  322 */             localPath.getElements().addAll(arrayOfPathElement);
/*      */           }
/*  324 */           TextAreaSkin.this.selectionHighlightGroup.getChildren().add(localPath);
/*  325 */           TextAreaSkin.this.selectionHighlightGroup.setVisible(true);
/*  326 */           localPath.setLayoutX(localText2.getLayoutX());
/*  327 */           localPath.setLayoutY(localText2.getLayoutY());
/*  328 */           TextAreaSkin.this.updateHighlightFill();
/*      */         } else {
/*  330 */           localText2.setImpl_selectionStart(-1);
/*  331 */           localText2.setImpl_selectionEnd(-1);
/*  332 */           TextAreaSkin.this.selectionHighlightGroup.setVisible(false);
/*      */         }
/*  334 */         k = Math.max(0, k - n);
/*  335 */         m = Math.max(0, m - n);
/*      */       }
/*      */ 
/*  338 */       if (PlatformUtil.isEmbedded())
/*      */       {
/*  341 */         localObject3 = TextAreaSkin.this.caretPath.getBoundsInParent();
/*  342 */         if (((IndexRange)localObject1).getLength() > 0) {
/*  343 */           if (i < j) {
/*  344 */             TextAreaSkin.this.selectionHandle1.setLayoutX(((Bounds)localObject3).getMinX() - TextAreaSkin.this.selectionHandle1.getWidth() / 2.0D);
/*  345 */             TextAreaSkin.this.selectionHandle1.setLayoutY(((Bounds)localObject3).getMinY() - TextAreaSkin.this.selectionHandle1.getHeight() + 1.0D);
/*      */           } else {
/*  347 */             TextAreaSkin.this.selectionHandle2.setLayoutX(((Bounds)localObject3).getMinX() - TextAreaSkin.this.selectionHandle2.getWidth() / 2.0D);
/*  348 */             TextAreaSkin.this.selectionHandle2.setLayoutY(((Bounds)localObject3).getMaxY() - 1.0D);
/*      */           }
/*      */         } else {
/*  351 */           TextAreaSkin.this.caretHandle.setLayoutX(((Bounds)localObject3).getMinX() - TextAreaSkin.this.caretHandle.getWidth() / 2.0D + 1.0D);
/*  352 */           TextAreaSkin.this.caretHandle.setLayoutY(((Bounds)localObject3).getMaxY());
/*      */         }
/*      */       }
/*      */ 
/*  356 */       if ((TextAreaSkin.this.scrollPane.getPrefViewportWidth() == 0.0D) || (TextAreaSkin.this.scrollPane.getPrefViewportHeight() == 0.0D))
/*      */       {
/*  358 */         TextAreaSkin.this.updatePrefViewportWidth();
/*  359 */         TextAreaSkin.this.updatePrefViewportHeight();
/*  360 */         if (((getParent() != null) && (TextAreaSkin.this.scrollPane.getPrefViewportWidth() > 0.0D)) || (TextAreaSkin.this.scrollPane.getPrefViewportHeight() > 0.0D))
/*      */         {
/*  363 */           getParent().requestLayout();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.TextAreaSkin
 * JD-Core Version:    0.6.2
 */