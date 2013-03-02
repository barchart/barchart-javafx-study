/*     */ package com.sun.javafx.scene.control.skin;
/*     */ 
/*     */ import com.sun.javafx.PlatformUtil;
/*     */ import com.sun.javafx.scene.control.behavior.TextFieldBehavior;
/*     */ import com.sun.javafx.scene.text.HitInfo;
/*     */ import com.sun.javafx.tk.FontMetrics;
/*     */ import java.util.List;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.Observable;
/*     */ import javafx.beans.binding.BooleanBinding;
/*     */ import javafx.beans.binding.DoubleBinding;
/*     */ import javafx.beans.binding.IntegerBinding;
/*     */ import javafx.beans.binding.ObjectBinding;
/*     */ import javafx.beans.binding.StringBinding;
/*     */ import javafx.beans.property.BooleanProperty;
/*     */ import javafx.beans.property.BooleanPropertyBase;
/*     */ import javafx.beans.property.DoubleProperty;
/*     */ import javafx.beans.property.IntegerProperty;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.ReadOnlyIntegerProperty;
/*     */ import javafx.beans.property.ReadOnlyObjectProperty;
/*     */ import javafx.beans.property.SimpleDoubleProperty;
/*     */ import javafx.beans.property.StringProperty;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ import javafx.beans.value.ObservableBooleanValue;
/*     */ import javafx.beans.value.ObservableDoubleValue;
/*     */ import javafx.beans.value.ObservableIntegerValue;
/*     */ import javafx.beans.value.ObservableObjectValue;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.geometry.Bounds;
/*     */ import javafx.geometry.HPos;
/*     */ import javafx.geometry.Insets;
/*     */ import javafx.geometry.Point2D;
/*     */ import javafx.geometry.Rectangle2D;
/*     */ import javafx.geometry.VPos;
/*     */ import javafx.scene.Group;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.control.IndexRange;
/*     */ import javafx.scene.control.TextField;
/*     */ import javafx.scene.input.MouseEvent;
/*     */ import javafx.scene.layout.StackPane;
/*     */ import javafx.scene.paint.Paint;
/*     */ import javafx.scene.shape.Path;
/*     */ import javafx.scene.shape.PathElement;
/*     */ import javafx.scene.shape.Rectangle;
/*     */ import javafx.scene.text.Text;
/*     */ 
/*     */ public class TextFieldSkin extends TextInputControlSkin<TextField, TextFieldBehavior>
/*     */ {
/*  76 */   public Group textGroup = new Group();
/*     */ 
/*  81 */   private Rectangle clip = new Rectangle();
/*     */ 
/*  88 */   private Text textNode = new Text();
/*     */   private Text promptNode;
/* 101 */   private Path selectionHighlightPath = new Path();
/*     */ 
/* 103 */   private Path characterBoundingPath = new Path();
/*     */   private ObservableBooleanValue usePromptText;
/* 105 */   private DoubleProperty textTranslateX = new SimpleDoubleProperty(this, "textTranslateX");
/* 106 */   private DoubleProperty textTranslateY = new SimpleDoubleProperty(this, "textTranslateY");
/*     */   private ObservableIntegerValue caretPosition;
/*     */   private double caretWidth;
/* 109 */   private BooleanProperty forwardBias = new BooleanPropertyBase() {
/*     */     public Object getBean() {
/* 111 */       return TextFieldSkin.this;
/*     */     }
/*     */ 
/*     */     public String getName() {
/* 115 */       return "forwardBias";
/*     */     }
/*     */ 
/*     */     protected void invalidated() {
/* 119 */       if (TextFieldSkin.this.getWidth() > 0.0D) {
/* 120 */         TextFieldSkin.this.textNode.impl_caretBiasProperty().set(get());
/* 121 */         TextFieldSkin.this.updateCaretOff(); } 
/*     */     } } ;
/*     */   protected ObservableDoubleValue textLeft;
/*     */   protected ObservableDoubleValue textRight;
/*     */   private double pressX;
/*     */   private double pressY;
/*     */ 
/* 132 */   protected int translateCaretPosition(int paramInt) { return paramInt; } 
/* 133 */   protected Point2D translateCaretPosition(Point2D paramPoint2D) { return paramPoint2D; }
/*     */ 
/*     */ 
/*     */   public TextFieldSkin(TextField paramTextField)
/*     */   {
/* 152 */     this(paramTextField, new TextFieldBehavior(paramTextField));
/*     */   }
/*     */ 
/*     */   public TextFieldSkin(final TextField paramTextField, TextFieldBehavior paramTextFieldBehavior) {
/* 156 */     super(paramTextField, paramTextFieldBehavior);
/* 157 */     paramTextFieldBehavior.setTextFieldSkin(this);
/*     */ 
/* 160 */     this.caretPosition = new IntegerBinding()
/*     */     {
/*     */       protected int computeValue() {
/* 163 */         return paramTextField.getCaretPosition();
/*     */       }
/*     */     };
/* 166 */     this.caretPosition.addListener(new ChangeListener()
/*     */     {
/*     */       public void changed(ObservableValue<? extends Number> paramAnonymousObservableValue, Number paramAnonymousNumber1, Number paramAnonymousNumber2) {
/* 169 */         if (TextFieldSkin.this.getWidth() > 0.0D) {
/* 170 */           TextFieldSkin.this.textNode.impl_caretPositionProperty().set(TextFieldSkin.this.translateCaretPosition(TextFieldSkin.this.caretPosition.get()));
/* 171 */           if (!TextFieldSkin.this.forwardBias.get()) {
/* 172 */             TextFieldSkin.this.forwardBias.set(true);
/*     */           }
/* 174 */           TextFieldSkin.this.updateCaretOff();
/*     */         }
/*     */       }
/*     */     });
/* 178 */     this.textLeft = new DoubleBinding()
/*     */     {
/*     */       protected double computeValue() {
/* 181 */         return TextFieldSkin.this.getInsets().getLeft();
/*     */       }
/*     */     };
/* 184 */     ChangeListener local5 = new ChangeListener()
/*     */     {
/*     */       public void changed(ObservableValue<? extends Number> paramAnonymousObservableValue, Number paramAnonymousNumber1, Number paramAnonymousNumber2) {
/* 187 */         if (TextFieldSkin.this.getWidth() > 0.0D) {
/* 188 */           TextFieldSkin.this.updateTextPos();
/* 189 */           TextFieldSkin.this.updateCaretOff();
/*     */         }
/*     */       }
/*     */     };
/* 193 */     this.textLeft.addListener(local5);
/* 194 */     this.textRight = new DoubleBinding()
/*     */     {
/*     */       protected double computeValue() {
/* 197 */         return TextFieldSkin.this.getWidth() - TextFieldSkin.this.getInsets().getRight();
/*     */       }
/*     */     };
/* 200 */     this.textRight.addListener(local5);
/*     */ 
/* 204 */     setManaged(false);
/*     */ 
/* 207 */     this.clip.setSmooth(false);
/* 208 */     this.clip.xProperty().bind(new DoubleBinding()
/*     */     {
/*     */       protected double computeValue() {
/* 211 */         return TextFieldSkin.this.getInsets().getLeft();
/*     */       }
/*     */     });
/* 214 */     this.clip.yProperty().bind(new DoubleBinding()
/*     */     {
/*     */       protected double computeValue() {
/* 217 */         return TextFieldSkin.this.getInsets().getTop();
/*     */       }
/*     */     });
/* 220 */     this.clip.widthProperty().bind(new DoubleBinding()
/*     */     {
/*     */       protected double computeValue() {
/* 223 */         return TextFieldSkin.this.getWidth() - TextFieldSkin.this.getInsets().getRight() - TextFieldSkin.this.getInsets().getLeft();
/*     */       }
/*     */     });
/* 226 */     this.clip.heightProperty().bind(new DoubleBinding()
/*     */     {
/*     */       protected double computeValue() {
/* 229 */         return TextFieldSkin.this.getHeight() - TextFieldSkin.this.getInsets().getTop() - TextFieldSkin.this.getInsets().getBottom();
/*     */       }
/*     */     });
/* 234 */     this.textGroup.setManaged(false);
/* 235 */     this.textGroup.setClip(this.clip);
/* 236 */     this.textGroup.getChildren().addAll(new Node[] { this.selectionHighlightPath, this.textNode, this.caretPath });
/* 237 */     getChildren().add(this.textGroup);
/* 238 */     if (PlatformUtil.isEmbedded()) {
/* 239 */       getChildren().addAll(new Node[] { this.caretHandle, this.selectionHandle1, this.selectionHandle2 });
/*     */     }
/*     */ 
/* 243 */     this.textNode.setManaged(false);
/* 244 */     this.textNode.getStyleClass().add("text");
/* 245 */     this.textNode.fontProperty().bind(this.font);
/* 246 */     this.textNode.xProperty().bind(this.textLeft);
/* 247 */     this.textNode.layoutXProperty().bind(this.textTranslateX);
/* 248 */     this.textNode.textProperty().bind(new StringBinding()
/*     */     {
/*     */       protected String computeValue() {
/* 251 */         String str = TextFieldSkin.this.maskText(paramTextField.getText());
/* 252 */         return str == null ? "" : str;
/*     */       }
/*     */     });
/* 255 */     this.textNode.fillProperty().bind(this.textFill);
/* 256 */     this.textNode.impl_selectionFillProperty().bind(new ObjectBinding()
/*     */     {
/*     */       protected Paint computeValue() {
/* 259 */         return paramTextField.isFocused() ? (Paint)TextFieldSkin.this.highlightTextFill.get() : (Paint)TextFieldSkin.this.textFill.get();
/*     */       }
/*     */     });
/* 263 */     this.textNode.impl_caretPositionProperty().set(paramTextField.getCaretPosition());
/* 264 */     paramTextField.selectionProperty().addListener(new InvalidationListener() {
/*     */       public void invalidated(Observable paramAnonymousObservable) {
/* 266 */         TextFieldSkin.this.updateSelection();
/*     */       }
/*     */     });
/* 271 */     this.selectionHighlightPath.setManaged(false);
/* 272 */     this.selectionHighlightPath.setStroke(null);
/* 273 */     this.selectionHighlightPath.layoutXProperty().bind(this.textTranslateX);
/* 274 */     this.selectionHighlightPath.layoutYProperty().bind(this.textTranslateY);
/* 275 */     this.selectionHighlightPath.visibleProperty().bind(paramTextField.anchorProperty().isNotEqualTo(this.caretPosition).and(paramTextField.focusedProperty()));
/* 276 */     this.selectionHighlightPath.fillProperty().bind(this.highlightFill);
/* 277 */     this.textNode.impl_selectionShapeProperty().addListener(new InvalidationListener() {
/*     */       public void invalidated(Observable paramAnonymousObservable) {
/* 279 */         TextFieldSkin.this.updateSelection();
/*     */       }
/*     */     });
/* 284 */     this.caretPath.setManaged(false);
/* 285 */     this.caretPath.setStrokeWidth(1.0D);
/* 286 */     this.caretPath.fillProperty().bind(this.textFill);
/* 287 */     this.caretPath.strokeProperty().bind(this.textFill);
/* 288 */     this.caretPath.visibleProperty().bind(this.caretVisible);
/* 289 */     this.caretPath.layoutXProperty().bind(this.textTranslateX);
/* 290 */     this.caretPath.layoutYProperty().bind(this.textTranslateY);
/* 291 */     this.textNode.impl_caretShapeProperty().addListener(new InvalidationListener() {
/*     */       public void invalidated(Observable paramAnonymousObservable) {
/* 293 */         TextFieldSkin.this.caretPath.getElements().setAll((Object[])TextFieldSkin.this.textNode.impl_caretShapeProperty().get());
/* 294 */         TextFieldSkin.this.caretWidth = Math.round(TextFieldSkin.this.caretPath.getLayoutBounds().getWidth());
/*     */       }
/*     */     });
/* 300 */     this.font.addListener(new InvalidationListener()
/*     */     {
/*     */       public void invalidated(Observable paramAnonymousObservable)
/*     */       {
/* 305 */         TextFieldSkin.this.requestLayout();
/* 306 */         ((TextField)TextFieldSkin.this.getSkinnable()).requestLayout();
/*     */       }
/*     */     });
/* 310 */     registerChangeListener(paramTextField.prefColumnCountProperty(), "prefColumnCount");
/* 311 */     if (paramTextField.isFocused()) setCaretAnimating(true);
/*     */ 
/* 313 */     paramTextField.alignmentProperty().addListener(new InvalidationListener() {
/*     */       public void invalidated(Observable paramAnonymousObservable) {
/* 315 */         if (TextFieldSkin.this.getWidth() > 0.0D) {
/* 316 */           TextFieldSkin.this.updateTextPos();
/* 317 */           TextFieldSkin.this.updateCaretOff();
/* 318 */           TextFieldSkin.this.requestLayout();
/*     */         }
/*     */       }
/*     */     });
/* 323 */     this.usePromptText = new BooleanBinding()
/*     */     {
/*     */       protected boolean computeValue() {
/* 326 */         String str1 = paramTextField.getText();
/* 327 */         String str2 = paramTextField.getPromptText();
/* 328 */         return ((str1 == null) || (str1.isEmpty())) && (str2 != null) && (!str2.isEmpty());
/*     */       }
/*     */     };
/* 333 */     paramTextField.textProperty().addListener(new InvalidationListener() {
/*     */       public void invalidated(Observable paramAnonymousObservable) {
/* 335 */         if (!((TextFieldBehavior)TextFieldSkin.this.getBehavior()).isEditing())
/*     */         {
/* 337 */           TextFieldSkin.this.updateTextPos();
/*     */         }
/*     */       }
/*     */     });
/* 342 */     if (this.usePromptText.get()) {
/* 343 */       createPromptNode();
/*     */     }
/*     */ 
/* 346 */     this.usePromptText.addListener(new InvalidationListener() {
/*     */       public void invalidated(Observable paramAnonymousObservable) {
/* 348 */         TextFieldSkin.this.createPromptNode();
/* 349 */         TextFieldSkin.this.requestLayout();
/*     */       }
/*     */     });
/* 353 */     if (PlatformUtil.isEmbedded()) {
/* 354 */       this.selectionHandle1.setRotate(180.0D);
/*     */ 
/* 356 */       EventHandler local21 = new EventHandler() {
/*     */         public void handle(MouseEvent paramAnonymousMouseEvent) {
/* 358 */           TextFieldSkin.this.pressX = paramAnonymousMouseEvent.getX();
/* 359 */           TextFieldSkin.this.pressY = paramAnonymousMouseEvent.getY();
/* 360 */           paramAnonymousMouseEvent.consume();
/*     */         }
/*     */       };
/* 364 */       this.caretHandle.setOnMousePressed(local21);
/* 365 */       this.selectionHandle1.setOnMousePressed(local21);
/* 366 */       this.selectionHandle2.setOnMousePressed(local21);
/*     */ 
/* 368 */       this.caretHandle.setOnMouseDragged(new EventHandler() {
/*     */         public void handle(MouseEvent paramAnonymousMouseEvent) {
/* 370 */           Point2D localPoint2D = new Point2D(TextFieldSkin.this.caretHandle.getLayoutX() + paramAnonymousMouseEvent.getX() + TextFieldSkin.this.pressX - TextFieldSkin.this.textNode.getLayoutX(), TextFieldSkin.this.caretHandle.getLayoutY() + paramAnonymousMouseEvent.getY() - TextFieldSkin.this.pressY - 6.0D);
/*     */ 
/* 372 */           HitInfo localHitInfo = TextFieldSkin.this.textNode.impl_hitTestChar(TextFieldSkin.this.translateCaretPosition(localPoint2D));
/* 373 */           int i = localHitInfo.getCharIndex();
/* 374 */           TextFieldSkin.this.positionCaret(localHitInfo, false);
/* 375 */           paramAnonymousMouseEvent.consume();
/*     */         }
/*     */       });
/* 379 */       this.selectionHandle1.setOnMouseDragged(new EventHandler() {
/*     */         public void handle(MouseEvent paramAnonymousMouseEvent) {
/* 381 */           TextField localTextField = (TextField)TextFieldSkin.this.getSkinnable();
/* 382 */           Point2D localPoint2D1 = TextFieldSkin.this.textNode.localToScene(0.0D, 0.0D);
/* 383 */           Point2D localPoint2D2 = new Point2D(paramAnonymousMouseEvent.getSceneX() - localPoint2D1.getX() + 10.0D - TextFieldSkin.this.pressX + TextFieldSkin.this.selectionHandle1.getWidth() / 2.0D, paramAnonymousMouseEvent.getSceneY() - localPoint2D1.getY() - TextFieldSkin.this.pressY - 6.0D);
/*     */ 
/* 385 */           HitInfo localHitInfo = TextFieldSkin.this.textNode.impl_hitTestChar(TextFieldSkin.this.translateCaretPosition(localPoint2D2));
/* 386 */           int i = localHitInfo.getCharIndex();
/* 387 */           if (localTextField.getAnchor() < localTextField.getCaretPosition())
/*     */           {
/* 389 */             localTextField.selectRange(localTextField.getCaretPosition(), localTextField.getAnchor());
/*     */           }
/* 391 */           if (i >= 0) {
/* 392 */             if (i >= localTextField.getAnchor() - 1) {
/* 393 */               localHitInfo.setCharIndex(Math.max(0, localTextField.getAnchor() - 1));
/*     */             }
/* 395 */             TextFieldSkin.this.positionCaret(localHitInfo, true);
/*     */           }
/* 397 */           paramAnonymousMouseEvent.consume();
/*     */         }
/*     */       });
/* 401 */       this.selectionHandle2.setOnMouseDragged(new EventHandler() {
/*     */         public void handle(MouseEvent paramAnonymousMouseEvent) {
/* 403 */           TextField localTextField = (TextField)TextFieldSkin.this.getSkinnable();
/* 404 */           Point2D localPoint2D1 = TextFieldSkin.this.textNode.localToScene(0.0D, 0.0D);
/* 405 */           Point2D localPoint2D2 = new Point2D(paramAnonymousMouseEvent.getSceneX() - localPoint2D1.getX() + 10.0D - TextFieldSkin.this.pressX + TextFieldSkin.this.selectionHandle2.getWidth() / 2.0D, paramAnonymousMouseEvent.getSceneY() - localPoint2D1.getY() - TextFieldSkin.this.pressY - 6.0D);
/*     */ 
/* 407 */           HitInfo localHitInfo = TextFieldSkin.this.textNode.impl_hitTestChar(TextFieldSkin.this.translateCaretPosition(localPoint2D2));
/* 408 */           int i = localHitInfo.getCharIndex();
/* 409 */           if (localTextField.getAnchor() > localTextField.getCaretPosition())
/*     */           {
/* 411 */             localTextField.selectRange(localTextField.getCaretPosition(), localTextField.getAnchor());
/*     */           }
/* 413 */           if (i > 0) {
/* 414 */             if (i <= localTextField.getAnchor()) {
/* 415 */               localHitInfo.setCharIndex(Math.min(localTextField.getAnchor() + 1, localTextField.getLength()));
/*     */             }
/* 417 */             TextFieldSkin.this.positionCaret(localHitInfo, true);
/*     */           }
/* 419 */           paramAnonymousMouseEvent.consume();
/*     */         }
/*     */       });
/*     */     }
/*     */   }
/*     */ 
/*     */   private void createPromptNode() {
/* 426 */     if ((this.promptNode != null) || (!this.usePromptText.get())) return;
/*     */ 
/* 428 */     this.promptNode = new Text();
/* 429 */     this.textGroup.getChildren().add(0, this.promptNode);
/* 430 */     this.promptNode.setManaged(false);
/* 431 */     this.promptNode.getStyleClass().add("text");
/* 432 */     this.promptNode.visibleProperty().bind(this.usePromptText);
/* 433 */     this.promptNode.fontProperty().bind(this.font);
/* 434 */     this.promptNode.xProperty().bind(this.textLeft);
/* 435 */     this.promptNode.layoutXProperty().set(0.0D);
/* 436 */     this.promptNode.textProperty().bind(((TextField)getSkinnable()).promptTextProperty());
/* 437 */     this.promptNode.fillProperty().bind(this.promptTextFill);
/* 438 */     updateSelection();
/*     */   }
/*     */ 
/*     */   private void updateSelection() {
/* 442 */     IndexRange localIndexRange = ((TextField)getSkinnable()).getSelection();
/* 443 */     if ((localIndexRange == null) || (localIndexRange.getLength() == 0)) {
/* 444 */       this.textNode.impl_selectionStartProperty().set(-1);
/* 445 */       this.textNode.impl_selectionEndProperty().set(-1);
/*     */     } else {
/* 447 */       this.textNode.impl_selectionStartProperty().set(localIndexRange.getStart());
/*     */ 
/* 449 */       this.textNode.impl_selectionEndProperty().set(localIndexRange.getStart());
/* 450 */       this.textNode.impl_selectionEndProperty().set(localIndexRange.getEnd());
/*     */     }
/*     */ 
/* 453 */     PathElement[] arrayOfPathElement = (PathElement[])this.textNode.impl_selectionShapeProperty().get();
/* 454 */     if (arrayOfPathElement == null)
/* 455 */       this.selectionHighlightPath.getElements().clear();
/*     */     else {
/* 457 */       this.selectionHighlightPath.getElements().setAll(arrayOfPathElement);
/*     */     }
/*     */ 
/* 460 */     if ((PlatformUtil.isEmbedded()) && (localIndexRange != null) && (localIndexRange.getLength() > 0)) {
/* 461 */       Bounds localBounds = this.selectionHighlightPath.getBoundsInParent();
/* 462 */       this.selectionHandle1.setLayoutX(localBounds.getMinX() - this.selectionHandle1.getWidth() / 2.0D);
/* 463 */       this.selectionHandle2.setLayoutX(localBounds.getMaxX() - this.selectionHandle2.getWidth() / 2.0D);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void handleControlPropertyChanged(String paramString) {
/* 468 */     if ("prefColumnCount".equals(paramString)) {
/* 469 */       requestLayout();
/* 470 */       ((TextField)getSkinnable()).requestLayout();
/*     */     } else {
/* 472 */       super.handleControlPropertyChanged(paramString);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected double computePrefWidth(double paramDouble)
/*     */   {
/* 478 */     TextField localTextField = (TextField)getSkinnable();
/*     */ 
/* 480 */     double d = ((FontMetrics)this.fontMetrics.get()).computeStringWidth("W");
/*     */ 
/* 482 */     int i = localTextField.getPrefColumnCount();
/* 483 */     Insets localInsets = getInsets();
/*     */ 
/* 485 */     return i * d + (localInsets.getLeft() + localInsets.getRight());
/*     */   }
/*     */ 
/*     */   protected double computePrefHeight(double paramDouble)
/*     */   {
/* 490 */     double d = ((FontMetrics)this.fontMetrics.get()).getLineHeight();
/* 491 */     Insets localInsets = getInsets();
/*     */ 
/* 493 */     return d + (localInsets.getTop() + localInsets.getBottom());
/*     */   }
/*     */ 
/*     */   protected double computeMaxHeight(double paramDouble) {
/* 497 */     return ((TextField)getSkinnable()).prefHeight(paramDouble);
/*     */   }
/*     */ 
/*     */   public double getBaselineOffset()
/*     */   {
/* 502 */     FontMetrics localFontMetrics = (FontMetrics)this.fontMetrics.get();
/* 503 */     return getInsets().getTop() + localFontMetrics.getAscent();
/*     */   }
/*     */ 
/*     */   private void updateTextPos() {
/* 507 */     switch (25.$SwitchMap$javafx$geometry$HPos[((TextField)getSkinnable()).getAlignment().getHpos().ordinal()]) {
/*     */     case 1:
/* 509 */       double d = (this.textRight.get() - this.textLeft.get()) / 2.0D;
/* 510 */       this.textTranslateX.set(d - this.textNode.getLayoutBounds().getWidth() / 2.0D);
/* 511 */       break;
/*     */     case 2:
/* 514 */       this.textTranslateX.set(this.textRight.get() - this.textNode.getLayoutBounds().getWidth() - this.caretWidth / 2.0D - 5.0D);
/*     */ 
/* 516 */       break;
/*     */     case 3:
/*     */     default:
/* 520 */       this.textTranslateX.set(this.caretWidth / 2.0D);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void updateCaretOff()
/*     */   {
/* 527 */     double d1 = 0.0D;
/* 528 */     double d2 = this.caretPath.getLayoutBounds().getMinX() + this.textTranslateX.get();
/*     */ 
/* 533 */     if (d2 < this.textLeft.get())
/*     */     {
/* 535 */       d1 = d2 - this.textLeft.get();
/* 536 */     } else if (d2 > this.textRight.get() - this.caretWidth)
/*     */     {
/* 538 */       d1 = d2 - (this.textRight.get() - this.caretWidth);
/*     */     }
/*     */ 
/* 543 */     switch (25.$SwitchMap$javafx$geometry$HPos[((TextField)getSkinnable()).getAlignment().getHpos().ordinal()]) {
/*     */     case 1:
/* 545 */       this.textTranslateX.set(this.textTranslateX.get() - d1);
/* 546 */       break;
/*     */     case 2:
/* 549 */       this.textTranslateX.set(Math.max(this.textTranslateX.get() - d1, this.textRight.get() - this.textNode.getLayoutBounds().getWidth() - this.caretWidth / 2.0D - 5.0D));
/*     */ 
/* 552 */       break;
/*     */     case 3:
/*     */     default:
/* 556 */       this.textTranslateX.set(Math.min(this.textTranslateX.get() - d1, this.caretWidth / 2.0D));
/*     */     }
/*     */ 
/* 559 */     if (PlatformUtil.isEmbedded())
/* 560 */       this.caretHandle.setLayoutX(d2 - this.caretHandle.getWidth() / 2.0D + 1.0D);
/*     */   }
/*     */ 
/*     */   public void replaceText(int paramInt1, int paramInt2, String paramString)
/*     */   {
/* 570 */     double d1 = this.textNode.getBoundsInParent().getMaxX();
/* 571 */     double d2 = this.caretPath.getLayoutBounds().getMaxX() + this.textTranslateX.get();
/* 572 */     ((TextField)getSkinnable()).replaceText(paramInt1, paramInt2, paramString);
/* 573 */     scrollAfterDelete(d1, d2);
/*     */   }
/*     */ 
/*     */   public void deleteChar(boolean paramBoolean)
/*     */   {
/* 582 */     double d1 = this.textNode.getBoundsInParent().getMaxX();
/* 583 */     double d2 = this.caretPath.getLayoutBounds().getMaxX() + this.textTranslateX.get();
/* 584 */     int i = !((TextField)getSkinnable()).deleteNextChar() ? 1 : paramBoolean ? 0 : !((TextField)getSkinnable()).deletePreviousChar() ? 1 : 0;
/*     */ 
/* 588 */     if (i == 0)
/*     */     {
/* 591 */       scrollAfterDelete(d1, d2);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void scrollAfterDelete(double paramDouble1, double paramDouble2) {
/* 596 */     Bounds localBounds1 = this.textNode.getLayoutBounds();
/* 597 */     Bounds localBounds2 = this.textNode.localToParent(localBounds1);
/* 598 */     Bounds localBounds3 = this.clip.getBoundsInParent();
/* 599 */     Bounds localBounds4 = this.caretPath.getLayoutBounds();
/*     */     double d;
/* 601 */     switch (25.$SwitchMap$javafx$geometry$HPos[((TextField)getSkinnable()).getAlignment().getHpos().ordinal()]) {
/*     */     case 1:
/* 603 */       updateTextPos();
/* 604 */       break;
/*     */     case 2:
/* 607 */       if (localBounds2.getMaxX() > localBounds3.getMaxX()) {
/* 608 */         d = paramDouble2 - localBounds4.getMaxX() - this.textTranslateX.get();
/* 609 */         if (localBounds2.getMaxX() + d < localBounds3.getMaxX()) {
/* 610 */           if (paramDouble1 <= localBounds3.getMaxX())
/* 611 */             d = paramDouble1 - localBounds2.getMaxX();
/*     */           else {
/* 613 */             d = localBounds3.getMaxX() - localBounds2.getMaxX();
/*     */           }
/*     */         }
/* 616 */         this.textTranslateX.set(this.textTranslateX.get() + d);
/*     */       } else {
/* 618 */         updateTextPos();
/*     */       }
/* 620 */       break;
/*     */     case 3:
/*     */     default:
/* 624 */       if ((localBounds2.getMinX() < localBounds3.getMinX() + this.caretWidth / 2.0D) && (localBounds2.getMaxX() <= localBounds3.getMaxX()))
/*     */       {
/* 626 */         d = paramDouble2 - localBounds4.getMaxX() - this.textTranslateX.get();
/* 627 */         if (localBounds2.getMaxX() + d < localBounds3.getMaxX()) {
/* 628 */           if (paramDouble1 <= localBounds3.getMaxX())
/* 629 */             d = paramDouble1 - localBounds2.getMaxX();
/*     */           else {
/* 631 */             d = localBounds3.getMaxX() - localBounds2.getMaxX();
/*     */           }
/*     */         }
/* 634 */         this.textTranslateX.set(this.textTranslateX.get() + d);
/*     */       }
/*     */       break;
/*     */     }
/* 638 */     updateCaretOff();
/*     */   }
/*     */ 
/*     */   public HitInfo getIndex(MouseEvent paramMouseEvent)
/*     */   {
/* 644 */     Point2D localPoint2D = new Point2D(paramMouseEvent.getX() - this.textNode.getLayoutX(), paramMouseEvent.getY() - this.textTranslateY.get());
/* 645 */     return this.textNode.impl_hitTestChar(translateCaretPosition(localPoint2D));
/*     */   }
/*     */ 
/*     */   public void setForwardBias(boolean paramBoolean) {
/* 649 */     this.forwardBias.set(paramBoolean);
/*     */   }
/*     */ 
/*     */   public void positionCaret(HitInfo paramHitInfo, boolean paramBoolean) {
/* 653 */     int i = paramHitInfo.getInsertionIndex();
/* 654 */     int j = (i > 0) && (i < ((TextField)getSkinnable()).getLength()) && (maskText(((TextField)getSkinnable()).getText()).codePointAt(i - 1) == 10) ? 1 : 0;
/*     */ 
/* 660 */     if ((!paramHitInfo.isLeading()) && (j != 0)) {
/* 661 */       paramHitInfo.setLeading(true);
/* 662 */       i--;
/*     */     }
/*     */ 
/* 665 */     if (paramBoolean)
/* 666 */       ((TextField)getSkinnable()).selectPositionCaret(i);
/*     */     else {
/* 668 */       ((TextField)getSkinnable()).positionCaret(i);
/*     */     }
/*     */ 
/* 671 */     setForwardBias(paramHitInfo.isLeading());
/*     */   }
/*     */ 
/*     */   public Rectangle2D getCharacterBounds(int paramInt)
/*     */   {
/*     */     double d1;
/*     */     double d2;
/*     */     double d3;
/*     */     double d4;
/* 677 */     if (paramInt == this.textNode.getText().length()) {
/* 678 */       localBounds = this.textNode.getBoundsInLocal();
/* 679 */       d1 = localBounds.getMaxX();
/* 680 */       d2 = 0.0D;
/* 681 */       d3 = 0.0D;
/* 682 */       d4 = localBounds.getMaxY();
/*     */     } else {
/* 684 */       this.characterBoundingPath.getElements().clear();
/* 685 */       this.characterBoundingPath.getElements().addAll(this.textNode.impl_getRangeShape(paramInt, paramInt + 1));
/* 686 */       this.characterBoundingPath.setLayoutX(this.textNode.getLayoutX());
/* 687 */       this.characterBoundingPath.setLayoutY(this.textNode.getLayoutY());
/*     */ 
/* 689 */       localBounds = this.characterBoundingPath.getBoundsInLocal();
/*     */ 
/* 691 */       d1 = localBounds.getMinX();
/* 692 */       d2 = localBounds.getMinY();
/*     */ 
/* 694 */       d3 = localBounds.isEmpty() ? 0.0D : localBounds.getWidth();
/* 695 */       d4 = localBounds.isEmpty() ? 0.0D : localBounds.getHeight();
/*     */     }
/*     */ 
/* 698 */     Bounds localBounds = this.textGroup.getBoundsInParent();
/*     */ 
/* 700 */     return new Rectangle2D(d1 + localBounds.getMinX(), d2 + localBounds.getMinY(), d3, d4);
/*     */   }
/*     */ 
/*     */   protected PathElement[] getUnderlineShape(int paramInt1, int paramInt2)
/*     */   {
/* 705 */     return this.textNode.impl_getUnderlineShape(paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   protected PathElement[] getRangeShape(int paramInt1, int paramInt2) {
/* 709 */     return this.textNode.impl_getRangeShape(paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   protected void addHighlight(List<? extends Node> paramList, int paramInt) {
/* 713 */     this.textGroup.getChildren().addAll(paramList);
/*     */   }
/*     */ 
/*     */   protected void removeHighlight(List<? extends Node> paramList) {
/* 717 */     this.textGroup.getChildren().removeAll(paramList);
/*     */   }
/*     */ 
/*     */   protected void layoutChildren() {
/* 721 */     super.layoutChildren();
/*     */     Object localObject;
/* 723 */     if (this.textNode != null)
/*     */     {
/* 725 */       localObject = getInsets();
/* 726 */       FontMetrics localFontMetrics = (FontMetrics)this.fontMetrics.get();
/*     */       double d;
/* 727 */       switch (25.$SwitchMap$javafx$geometry$VPos[((TextField)getSkinnable()).getAlignment().getVpos().ordinal()]) {
/*     */       case 1:
/* 729 */         d = ((Insets)localObject).getTop() + localFontMetrics.getMaxAscent();
/* 730 */         break;
/*     */       case 2:
/* 733 */         d = (((Insets)localObject).getTop() + localFontMetrics.getMaxAscent() + getHeight() - ((Insets)localObject).getBottom() - localFontMetrics.getMaxDescent()) / 2.0D;
/*     */ 
/* 735 */         break;
/*     */       case 3:
/*     */       default:
/* 739 */         d = getHeight() - ((Insets)localObject).getBottom() - localFontMetrics.getMaxDescent();
/*     */       }
/* 741 */       this.textNode.setY(d);
/* 742 */       if (this.promptNode != null) {
/* 743 */         this.promptNode.setY(d);
/*     */       }
/*     */     }
/*     */ 
/* 747 */     if (PlatformUtil.isEmbedded()) {
/* 748 */       TextField localTextField = (TextField)getSkinnable();
/*     */ 
/* 751 */       IndexRange localIndexRange = localTextField.getSelection();
/* 752 */       this.selectionHandle1.resize(this.selectionHandle1.prefWidth(-1.0D), this.selectionHandle1.prefHeight(-1.0D));
/*     */ 
/* 754 */       this.selectionHandle2.resize(this.selectionHandle2.prefWidth(-1.0D), this.selectionHandle2.prefHeight(-1.0D));
/*     */ 
/* 756 */       this.caretHandle.resize(this.caretHandle.prefWidth(-1.0D), this.caretHandle.prefHeight(-1.0D));
/*     */ 
/* 759 */       localObject = this.caretPath.getBoundsInParent();
/* 760 */       this.caretHandle.setLayoutY(((Bounds)localObject).getMaxY() - 1.0D);
/*     */ 
/* 762 */       this.selectionHandle1.setLayoutY(((Bounds)localObject).getMinY() - this.selectionHandle1.getHeight() + 1.0D);
/* 763 */       this.selectionHandle2.setLayoutY(((Bounds)localObject).getMaxY() - 1.0D);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Point2D getMenuPosition() {
/* 768 */     Point2D localPoint2D = super.getMenuPosition();
/* 769 */     if (localPoint2D != null) {
/* 770 */       Insets localInsets = getInsets();
/* 771 */       localPoint2D = new Point2D(Math.max(0.0D, localPoint2D.getX() - this.textNode.getLayoutX() - localInsets.getLeft() + this.textTranslateX.get()), Math.max(0.0D, localPoint2D.getY() - this.textNode.getLayoutY() - localInsets.getTop()));
/*     */     }
/*     */ 
/* 774 */     return localPoint2D;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.TextFieldSkin
 * JD-Core Version:    0.6.2
 */