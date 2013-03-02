/*     */ package com.sun.javafx.scene.control.skin;
/*     */ 
/*     */ import com.sun.javafx.Utils;
/*     */ import javafx.beans.property.DoubleProperty;
/*     */ import javafx.beans.property.IntegerProperty;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.SimpleDoubleProperty;
/*     */ import javafx.beans.property.SimpleIntegerProperty;
/*     */ import javafx.beans.property.SimpleObjectProperty;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.event.ActionEvent;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.geometry.Bounds;
/*     */ import javafx.geometry.HPos;
/*     */ import javafx.geometry.Insets;
/*     */ import javafx.geometry.VPos;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.Scene;
/*     */ import javafx.scene.control.Button;
/*     */ import javafx.scene.control.Label;
/*     */ import javafx.scene.control.Slider;
/*     */ import javafx.scene.control.ToggleButton;
/*     */ import javafx.scene.control.ToggleGroup;
/*     */ import javafx.scene.effect.DropShadow;
/*     */ import javafx.scene.input.KeyCode;
/*     */ import javafx.scene.input.KeyEvent;
/*     */ import javafx.scene.input.MouseEvent;
/*     */ import javafx.scene.layout.GridPane;
/*     */ import javafx.scene.layout.HBox;
/*     */ import javafx.scene.layout.Region;
/*     */ import javafx.scene.layout.StackPane;
/*     */ import javafx.scene.paint.Color;
/*     */ import javafx.scene.paint.CycleMethod;
/*     */ import javafx.scene.paint.LinearGradient;
/*     */ import javafx.scene.paint.Stop;
/*     */ import javafx.scene.shape.Circle;
/*     */ import javafx.scene.shape.Rectangle;
/*     */ import javafx.stage.Modality;
/*     */ import javafx.stage.Stage;
/*     */ import javafx.stage.StageStyle;
/*     */ import javafx.stage.Window;
/*     */ 
/*     */ public class CustomColorDialog extends StackPane
/*     */ {
/*     */   private static final int CONTENT_PADDING = 10;
/*     */   private static final int RECT_SIZE = 200;
/*     */   private static final int CONTROLS_WIDTH = 256;
/*     */   private static final int COLORBAR_GAP = 9;
/*     */   private static final int LABEL_GAP = 2;
/*  62 */   final Stage dialog = new Stage();
/*     */   ColorRectPane colorRectPane;
/*     */   ControlsPane controlsPane;
/*     */   Circle colorRectIndicator;
/*     */   Rectangle colorRect;
/*     */   Rectangle colorRectOverlayOne;
/*     */   Rectangle colorRectOverlayTwo;
/*     */   Rectangle colorBar;
/*     */   Rectangle colorBarIndicator;
/*  73 */   private Color currentColor = Color.WHITE;
/*  74 */   ObjectProperty<Color> customColorProperty = new SimpleObjectProperty(Color.TRANSPARENT);
/*  75 */   boolean saveCustomColor = false;
/*  76 */   boolean useCustomColor = false;
/*     */   Button saveButton;
/*     */   Button useButton;
/*  80 */   private WebColorField webField = null;
/*     */   private Scene customScene;
/*  99 */   private final EventHandler<KeyEvent> keyEventListener = new EventHandler() {
/*     */     public void handle(KeyEvent paramAnonymousKeyEvent) {
/* 101 */       switch (CustomColorDialog.2.$SwitchMap$javafx$scene$input$KeyCode[paramAnonymousKeyEvent.getCode().ordinal()]) {
/*     */       case 1:
/* 103 */         CustomColorDialog.this.dialog.setScene(null);
/* 104 */         CustomColorDialog.this.dialog.close();
/*     */       }
/*     */     }
/*  99 */   };
/*     */ 
/*     */   public CustomColorDialog(Window paramWindow)
/*     */   {
/*  84 */     getStyleClass().add("custom-color-dialog");
/*  85 */     if (paramWindow != null) this.dialog.initOwner(paramWindow);
/*  86 */     this.dialog.setTitle("Custom Colors..");
/*  87 */     this.dialog.initModality(Modality.APPLICATION_MODAL);
/*  88 */     this.dialog.initStyle(StageStyle.UTILITY);
/*  89 */     this.colorRectPane = new ColorRectPane();
/*  90 */     this.controlsPane = new ControlsPane();
/*     */ 
/*  92 */     this.customScene = new Scene(this);
/*  93 */     getChildren().addAll(new Node[] { this.colorRectPane, this.controlsPane });
/*     */ 
/*  95 */     this.dialog.setScene(this.customScene);
/*  96 */     this.dialog.addEventHandler(KeyEvent.ANY, this.keyEventListener);
/*     */   }
/*     */ 
/*     */   public void setCurrentColor(Color paramColor)
/*     */   {
/* 110 */     this.currentColor = paramColor;
/* 111 */     this.controlsPane.currentColorRect.setFill(paramColor);
/*     */   }
/*     */ 
/*     */   public void show(double paramDouble1, double paramDouble2) {
/* 115 */     if ((paramDouble1 != 0.0D) && (paramDouble2 != 0.0D)) {
/* 116 */       this.dialog.setX(paramDouble1);
/* 117 */       this.dialog.setY(paramDouble2);
/*     */     }
/* 119 */     if (this.dialog.getScene() == null) this.dialog.setScene(this.customScene);
/* 120 */     this.colorRectPane.updateValues();
/* 121 */     this.dialog.show();
/*     */   }
/*     */ 
/*     */   public void layoutChildren() {
/* 125 */     double d1 = getInsets().getLeft();
/* 126 */     double d2 = getInsets().getTop();
/* 127 */     this.controlsPane.relocate(d1 + this.colorRectPane.prefWidth(-1.0D), 0.0D);
/*     */   }
/*     */ 
/*     */   public double computePrefWidth(double paramDouble) {
/* 131 */     return getInsets().getLeft() + this.colorRectPane.prefWidth(paramDouble) + this.controlsPane.prefWidth(paramDouble) + getInsets().getRight();
/*     */   }
/*     */ 
/*     */   public double computePrefHeight(double paramDouble)
/*     */   {
/* 136 */     return getInsets().getTop() + Math.max(this.colorRectPane.prefHeight(paramDouble), this.controlsPane.prefHeight(paramDouble) + getInsets().getBottom());
/*     */   }
/*     */ 
/*     */   static double computeXOffset(double paramDouble1, double paramDouble2, HPos paramHPos)
/*     */   {
/* 141 */     switch (2.$SwitchMap$javafx$geometry$HPos[paramHPos.ordinal()]) {
/*     */     case 1:
/* 143 */       return 0.0D;
/*     */     case 2:
/* 145 */       return (paramDouble1 - paramDouble2) / 2.0D;
/*     */     case 3:
/* 147 */       return paramDouble1 - paramDouble2;
/*     */     }
/* 149 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   static double computeYOffset(double paramDouble1, double paramDouble2, VPos paramVPos) {
/* 153 */     switch (2.$SwitchMap$javafx$geometry$VPos[paramVPos.ordinal()]) {
/*     */     case 1:
/* 155 */       return 0.0D;
/*     */     case 2:
/* 157 */       return (paramDouble1 - paramDouble2) / 2.0D;
/*     */     case 3:
/* 159 */       return paramDouble1 - paramDouble2;
/*     */     }
/* 161 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   static double clamp(double paramDouble)
/*     */   {
/* 853 */     return paramDouble > 1.0D ? 1.0D : paramDouble < 0.0D ? 0.0D : paramDouble;
/*     */   }
/*     */ 
/*     */   private static LinearGradient createHueGradient()
/*     */   {
/* 858 */     Stop[] arrayOfStop = new Stop['ÿ'];
/* 859 */     for (int i = 0; i < 255; i++) {
/* 860 */       double d = 1.0D - 0.00392156862745098D * i;
/* 861 */       int j = (int)(i / 255.0D * 360.0D);
/* 862 */       arrayOfStop[i] = new Stop(d, Color.hsb(j, 1.0D, 1.0D));
/*     */     }
/* 864 */     return new LinearGradient(0.0D, 1.0D, 1.0D, 0.0D, true, CycleMethod.NO_CYCLE, arrayOfStop);
/*     */   }
/*     */ 
/*     */   private static int doubleToInt(double paramDouble) {
/* 868 */     return new Double(paramDouble * 255.0D).intValue();
/*     */   }
/*     */ 
/*     */   class ControlsPane extends StackPane
/*     */   {
/*     */     Label currentColorLabel;
/*     */     Label newColorLabel;
/*     */     Rectangle currentColorRect;
/*     */     Rectangle newColorRect;
/*     */     StackPane currentTransparent;
/*     */     StackPane newTransparent;
/*     */     GridPane currentAndNewColor;
/*     */     Rectangle currentNewColorBorder;
/*     */     ToggleButton hsbButton;
/*     */     ToggleButton rgbButton;
/*     */     ToggleButton webButton;
/*     */     HBox hBox;
/*     */     GridPane hsbSettings;
/*     */     GridPane rgbSettings;
/*     */     GridPane webSettings;
/*     */     GridPane alphaSettings;
/*     */     HBox buttonBox;
/*     */     StackPane whiteBox;
/* 436 */     CustomColorDialog.ColorSettingsMode colorSettingsMode = CustomColorDialog.ColorSettingsMode.HSB;
/*     */ 
/* 438 */     StackPane settingsPane = new StackPane();
/*     */ 
/*     */     public ControlsPane() {
/* 441 */       getStyleClass().add("controls-pane");
/*     */ 
/* 443 */       this.currentNewColorBorder = new Rectangle(256.0D, 18.0D, null);
/* 444 */       this.currentNewColorBorder.setStroke(Utils.deriveColor(Color.web("#d0d0d0"), 0.0D));
/* 445 */       this.currentNewColorBorder.setStrokeWidth(2.0D);
/*     */ 
/* 447 */       this.currentTransparent = new StackPane();
/* 448 */       this.currentTransparent.setPrefSize(128.0D, 18.0D);
/* 449 */       this.currentTransparent.setId("transparent-current");
/*     */ 
/* 451 */       this.newTransparent = new StackPane();
/* 452 */       this.newTransparent.setPrefSize(128.0D, 18.0D);
/* 453 */       this.newTransparent.setId("transparent-new");
/*     */ 
/* 455 */       this.currentColorRect = new Rectangle(128.0D, 18.0D);
/* 456 */       this.currentColorRect.setFill(CustomColorDialog.this.currentColor);
/*     */ 
/* 458 */       this.newColorRect = new Rectangle(128.0D, 18.0D);
/*     */ 
/* 460 */       updateNewColorFill();
/* 461 */       CustomColorDialog.ColorRectPane.access$900(CustomColorDialog.this.colorRectPane).addListener(new ChangeListener()
/*     */       {
/*     */         public void changed(ObservableValue<? extends Color> paramAnonymousObservableValue, Color paramAnonymousColor1, Color paramAnonymousColor2) {
/* 464 */           CustomColorDialog.ControlsPane.this.updateNewColorFill();
/* 465 */           CustomColorDialog.this.customColorProperty.set(Color.hsb(CustomColorDialog.this.colorRectPane.hue.getValue().doubleValue(), CustomColorDialog.clamp(CustomColorDialog.this.colorRectPane.sat.getValue().doubleValue() / 100.0D), CustomColorDialog.clamp(CustomColorDialog.this.colorRectPane.bright.getValue().doubleValue() / 100.0D), CustomColorDialog.clamp(CustomColorDialog.this.colorRectPane.alpha.getValue().doubleValue() / 100.0D)));
/*     */         }
/*     */       });
/* 472 */       this.currentColorLabel = new Label("Current Color");
/* 473 */       this.newColorLabel = new Label("New Color");
/* 474 */       Rectangle localRectangle1 = new Rectangle(0.0D, 12.0D);
/*     */ 
/* 476 */       this.whiteBox = new StackPane();
/* 477 */       this.whiteBox.getStyleClass().add("customcolor-controls-background");
/*     */ 
/* 479 */       this.hsbButton = new ToggleButton("HSB");
/* 480 */       this.hsbButton.setId("toggle-button-left");
/* 481 */       this.rgbButton = new ToggleButton("RGB");
/* 482 */       this.rgbButton.setId("toggle-button-center");
/* 483 */       this.webButton = new ToggleButton("Web");
/* 484 */       this.webButton.setId("toggle-button-right");
/* 485 */       ToggleGroup localToggleGroup = new ToggleGroup();
/* 486 */       this.hsbButton.setToggleGroup(localToggleGroup);
/* 487 */       this.rgbButton.setToggleGroup(localToggleGroup);
/* 488 */       this.webButton.setToggleGroup(localToggleGroup);
/* 489 */       localToggleGroup.selectToggle(this.hsbButton);
/*     */ 
/* 491 */       showHSBSettings();
/*     */ 
/* 493 */       this.hsbButton.setOnAction(new EventHandler() {
/*     */         public void handle(ActionEvent paramAnonymousActionEvent) {
/* 495 */           if (CustomColorDialog.ControlsPane.this.colorSettingsMode != CustomColorDialog.ColorSettingsMode.HSB) {
/* 496 */             CustomColorDialog.ControlsPane.this.colorSettingsMode = CustomColorDialog.ColorSettingsMode.HSB;
/* 497 */             CustomColorDialog.ControlsPane.this.showHSBSettings();
/* 498 */             CustomColorDialog.ControlsPane.this.requestLayout();
/*     */           }
/*     */         }
/*     */       });
/* 502 */       this.rgbButton.setOnAction(new EventHandler() {
/*     */         public void handle(ActionEvent paramAnonymousActionEvent) {
/* 504 */           if (CustomColorDialog.ControlsPane.this.colorSettingsMode != CustomColorDialog.ColorSettingsMode.RGB) {
/* 505 */             CustomColorDialog.ControlsPane.this.colorSettingsMode = CustomColorDialog.ColorSettingsMode.RGB;
/* 506 */             CustomColorDialog.ControlsPane.this.showRGBSettings();
/* 507 */             CustomColorDialog.ControlsPane.this.requestLayout();
/*     */           }
/*     */         }
/*     */       });
/* 511 */       this.webButton.setOnAction(new EventHandler() {
/*     */         public void handle(ActionEvent paramAnonymousActionEvent) {
/* 513 */           if (CustomColorDialog.ControlsPane.this.colorSettingsMode != CustomColorDialog.ColorSettingsMode.WEB) {
/* 514 */             CustomColorDialog.ControlsPane.this.colorSettingsMode = CustomColorDialog.ColorSettingsMode.WEB;
/* 515 */             CustomColorDialog.ControlsPane.this.showWebSettings();
/* 516 */             CustomColorDialog.ControlsPane.this.requestLayout();
/*     */           }
/*     */         }
/*     */       });
/* 521 */       this.hBox = new HBox();
/* 522 */       this.hBox.getChildren().addAll(new Node[] { this.hsbButton, this.rgbButton, this.webButton });
/*     */ 
/* 524 */       this.currentAndNewColor = new GridPane();
/* 525 */       this.currentAndNewColor.getStyleClass().add("current-new-color-grid");
/* 526 */       this.currentAndNewColor.add(this.currentColorLabel, 0, 0, 2, 1);
/* 527 */       this.currentAndNewColor.add(this.newColorLabel, 2, 0, 2, 1);
/* 528 */       Region localRegion = new Region();
/* 529 */       localRegion.setPadding(new Insets(1.0D, 128.0D, 1.0D, 128.0D));
/* 530 */       this.currentAndNewColor.add(localRegion, 0, 1, 4, 1);
/* 531 */       this.currentAndNewColor.add(this.currentTransparent, 0, 2, 2, 1);
/* 532 */       this.currentAndNewColor.add(this.currentColorRect, 0, 2, 2, 1);
/* 533 */       this.currentAndNewColor.add(this.newTransparent, 2, 2, 2, 1);
/* 534 */       this.currentAndNewColor.add(this.newColorRect, 2, 2, 2, 1);
/* 535 */       this.currentAndNewColor.add(localRectangle1, 0, 3, 4, 1);
/*     */ 
/* 538 */       this.alphaSettings = new GridPane();
/* 539 */       this.alphaSettings.setHgap(5.0D);
/* 540 */       this.alphaSettings.setVgap(0.0D);
/* 541 */       this.alphaSettings.setManaged(false);
/* 542 */       this.alphaSettings.getStyleClass().add("alpha-settings");
/*     */ 
/* 545 */       Rectangle localRectangle2 = new Rectangle(0.0D, 12.0D);
/* 546 */       this.alphaSettings.add(localRectangle2, 0, 0, 3, 1);
/*     */ 
/* 548 */       Label localLabel = new Label("Opacity:");
/* 549 */       localLabel.setPrefWidth(68.0D);
/* 550 */       this.alphaSettings.add(localLabel, 0, 1);
/*     */ 
/* 552 */       Slider localSlider = new Slider(0.0D, 100.0D, 50.0D);
/* 553 */       localSlider.setPrefWidth(100.0D);
/* 554 */       this.alphaSettings.add(localSlider, 1, 1);
/*     */ 
/* 556 */       IntegerField localIntegerField = new IntegerField(100);
/* 557 */       localIntegerField.setSkin(new IntegerFieldSkin(localIntegerField));
/* 558 */       localIntegerField.setPrefColumnCount(3);
/* 559 */       localIntegerField.setMaxWidth(38.0D);
/* 560 */       this.alphaSettings.add(localIntegerField, 2, 1);
/*     */ 
/* 563 */       localIntegerField.valueProperty().bindBidirectional(CustomColorDialog.this.colorRectPane.alpha);
/* 564 */       localSlider.valueProperty().bindBidirectional(CustomColorDialog.this.colorRectPane.alpha);
/*     */ 
/* 566 */       Rectangle localRectangle3 = new Rectangle(0.0D, 15.0D);
/* 567 */       this.alphaSettings.add(localRectangle3, 0, 2, 3, 1);
/*     */ 
/* 569 */       this.buttonBox = new HBox(4.0D);
/*     */ 
/* 571 */       CustomColorDialog.this.saveButton = new Button("Save");
/* 572 */       CustomColorDialog.this.saveButton.setOnAction(new EventHandler() {
/*     */         public void handle(ActionEvent paramAnonymousActionEvent) {
/* 574 */           CustomColorDialog.this.saveCustomColor = true;
/* 575 */           if (CustomColorDialog.ControlsPane.this.colorSettingsMode == CustomColorDialog.ColorSettingsMode.WEB)
/* 576 */             CustomColorDialog.this.customColorProperty.set(CustomColorDialog.this.webField.valueProperty().get());
/*     */           else {
/* 578 */             CustomColorDialog.this.customColorProperty.set(Color.rgb(CustomColorDialog.this.colorRectPane.red.get(), CustomColorDialog.this.colorRectPane.green.get(), CustomColorDialog.this.colorRectPane.blue.get(), CustomColorDialog.clamp(CustomColorDialog.this.colorRectPane.alpha.get() / 100.0D)));
/*     */           }
/*     */ 
/* 582 */           CustomColorDialog.this.dialog.hide();
/* 583 */           CustomColorDialog.this.saveCustomColor = false;
/*     */         }
/*     */       });
/* 587 */       CustomColorDialog.this.useButton = new Button("Use");
/* 588 */       CustomColorDialog.this.useButton.setOnAction(new EventHandler() {
/*     */         public void handle(ActionEvent paramAnonymousActionEvent) {
/* 590 */           CustomColorDialog.this.useCustomColor = true;
/* 591 */           CustomColorDialog.this.customColorProperty.set(Color.rgb(CustomColorDialog.this.colorRectPane.red.get(), CustomColorDialog.this.colorRectPane.green.get(), CustomColorDialog.this.colorRectPane.blue.get(), CustomColorDialog.clamp(CustomColorDialog.this.colorRectPane.alpha.get() / 100.0D)));
/*     */ 
/* 594 */           CustomColorDialog.this.dialog.hide();
/* 595 */           CustomColorDialog.this.useCustomColor = false;
/*     */         }
/*     */       });
/* 599 */       Button localButton = new Button("Cancel");
/* 600 */       localButton.setOnAction(new EventHandler() {
/*     */         public void handle(ActionEvent paramAnonymousActionEvent) {
/* 602 */           CustomColorDialog.this.customColorProperty.set(CustomColorDialog.this.currentColor);
/* 603 */           CustomColorDialog.this.dialog.hide();
/*     */         }
/*     */       });
/* 606 */       this.buttonBox.getChildren().addAll(new Node[] { CustomColorDialog.this.saveButton, CustomColorDialog.this.useButton, localButton });
/*     */ 
/* 608 */       getChildren().addAll(new Node[] { this.currentAndNewColor, this.currentNewColorBorder, this.whiteBox, this.hBox, this.settingsPane, this.alphaSettings, this.buttonBox });
/*     */     }
/*     */ 
/*     */     private void updateNewColorFill()
/*     */     {
/* 613 */       this.newColorRect.setFill(Color.hsb(CustomColorDialog.this.colorRectPane.hue.getValue().doubleValue(), CustomColorDialog.clamp(CustomColorDialog.this.colorRectPane.sat.getValue().doubleValue() / 100.0D), CustomColorDialog.clamp(CustomColorDialog.this.colorRectPane.bright.getValue().doubleValue() / 100.0D), CustomColorDialog.clamp(CustomColorDialog.this.colorRectPane.alpha.getValue().doubleValue() / 100.0D)));
/*     */     }
/*     */ 
/*     */     private void showHSBSettings()
/*     */     {
/* 619 */       if (this.hsbSettings == null) {
/* 620 */         this.hsbSettings = new GridPane();
/* 621 */         this.hsbSettings.setHgap(5.0D);
/* 622 */         this.hsbSettings.setVgap(4.0D);
/* 623 */         this.hsbSettings.setManaged(false);
/*     */ 
/* 625 */         Region localRegion1 = new Region();
/* 626 */         localRegion1.setPrefHeight(3.0D);
/* 627 */         this.hsbSettings.add(localRegion1, 0, 0, 3, 1);
/*     */ 
/* 630 */         Label localLabel1 = new Label("Hue:");
/* 631 */         localLabel1.setMinWidth(68.0D);
/* 632 */         this.hsbSettings.add(localLabel1, 0, 1);
/*     */ 
/* 634 */         Slider localSlider1 = new Slider(0.0D, 360.0D, 100.0D);
/* 635 */         localSlider1.setPrefWidth(100.0D);
/* 636 */         this.hsbSettings.add(localSlider1, 1, 1);
/*     */ 
/* 638 */         IntegerField localIntegerField1 = new IntegerField(360);
/* 639 */         localIntegerField1.setSkin(new IntegerFieldSkin(localIntegerField1));
/* 640 */         localIntegerField1.setPrefColumnCount(3);
/* 641 */         localIntegerField1.setMaxWidth(38.0D);
/* 642 */         this.hsbSettings.add(localIntegerField1, 2, 1);
/* 643 */         localIntegerField1.valueProperty().bindBidirectional(CustomColorDialog.this.colorRectPane.hue);
/* 644 */         localSlider1.valueProperty().bindBidirectional(CustomColorDialog.this.colorRectPane.hue);
/*     */ 
/* 647 */         Label localLabel2 = new Label("Saturation:");
/* 648 */         localLabel2.setMinWidth(68.0D);
/* 649 */         this.hsbSettings.add(localLabel2, 0, 2);
/*     */ 
/* 651 */         Slider localSlider2 = new Slider(0.0D, 100.0D, 50.0D);
/* 652 */         localSlider2.setPrefWidth(100.0D);
/* 653 */         this.hsbSettings.add(localSlider2, 1, 2);
/*     */ 
/* 655 */         IntegerField localIntegerField2 = new IntegerField(100);
/* 656 */         localIntegerField2.setSkin(new IntegerFieldSkin(localIntegerField2));
/* 657 */         localIntegerField2.setPrefColumnCount(3);
/* 658 */         localIntegerField2.setMaxWidth(38.0D);
/* 659 */         this.hsbSettings.add(localIntegerField2, 2, 2);
/* 660 */         localIntegerField2.valueProperty().bindBidirectional(CustomColorDialog.this.colorRectPane.sat);
/* 661 */         localSlider2.valueProperty().bindBidirectional(CustomColorDialog.this.colorRectPane.sat);
/*     */ 
/* 664 */         Label localLabel3 = new Label("Brightness:");
/* 665 */         localLabel3.setMinWidth(68.0D);
/* 666 */         this.hsbSettings.add(localLabel3, 0, 3);
/*     */ 
/* 668 */         Slider localSlider3 = new Slider(0.0D, 100.0D, 50.0D);
/* 669 */         localSlider3.setPrefWidth(100.0D);
/* 670 */         this.hsbSettings.add(localSlider3, 1, 3);
/*     */ 
/* 672 */         IntegerField localIntegerField3 = new IntegerField(100);
/* 673 */         localIntegerField3.setSkin(new IntegerFieldSkin(localIntegerField3));
/* 674 */         localIntegerField3.setPrefColumnCount(3);
/* 675 */         localIntegerField3.setMaxWidth(38.0D);
/* 676 */         this.hsbSettings.add(localIntegerField3, 2, 3);
/*     */ 
/* 680 */         localIntegerField3.valueProperty().bindBidirectional(CustomColorDialog.this.colorRectPane.bright);
/* 681 */         localSlider3.valueProperty().bindBidirectional(CustomColorDialog.this.colorRectPane.bright);
/*     */ 
/* 683 */         Region localRegion2 = new Region();
/* 684 */         localRegion2.setPrefHeight(4.0D);
/* 685 */         this.hsbSettings.add(localRegion2, 0, 4, 3, 1);
/*     */       }
/* 687 */       this.settingsPane.getChildren().setAll(new Node[] { this.hsbSettings });
/*     */     }
/*     */ 
/*     */     private void showRGBSettings()
/*     */     {
/* 692 */       if (this.rgbSettings == null) {
/* 693 */         this.rgbSettings = new GridPane();
/* 694 */         this.rgbSettings.setHgap(5.0D);
/* 695 */         this.rgbSettings.setVgap(4.0D);
/* 696 */         this.rgbSettings.setManaged(false);
/*     */ 
/* 698 */         Region localRegion1 = new Region();
/* 699 */         localRegion1.setPrefHeight(3.0D);
/* 700 */         this.rgbSettings.add(localRegion1, 0, 0, 3, 1);
/*     */ 
/* 702 */         Label localLabel1 = new Label("Red:");
/* 703 */         localLabel1.setMinWidth(68.0D);
/* 704 */         this.rgbSettings.add(localLabel1, 0, 1);
/*     */ 
/* 707 */         Slider localSlider1 = new Slider(0.0D, 255.0D, 100.0D);
/* 708 */         localSlider1.setPrefWidth(100.0D);
/* 709 */         this.rgbSettings.add(localSlider1, 1, 1);
/*     */ 
/* 711 */         IntegerField localIntegerField1 = new IntegerField(255);
/* 712 */         localIntegerField1.setSkin(new IntegerFieldSkin(localIntegerField1));
/*     */ 
/* 714 */         localIntegerField1.setMaxWidth(38.0D);
/* 715 */         this.rgbSettings.add(localIntegerField1, 2, 1);
/*     */ 
/* 717 */         localIntegerField1.valueProperty().bindBidirectional(CustomColorDialog.this.colorRectPane.red);
/* 718 */         localSlider1.valueProperty().bindBidirectional(CustomColorDialog.this.colorRectPane.red);
/*     */ 
/* 721 */         Label localLabel2 = new Label("Green:     ");
/* 722 */         localLabel2.setMinWidth(68.0D);
/* 723 */         this.rgbSettings.add(localLabel2, 0, 2);
/*     */ 
/* 725 */         Slider localSlider2 = new Slider(0.0D, 255.0D, 100.0D);
/* 726 */         localSlider2.setPrefWidth(100.0D);
/* 727 */         this.rgbSettings.add(localSlider2, 1, 2);
/*     */ 
/* 729 */         IntegerField localIntegerField2 = new IntegerField(255);
/* 730 */         localIntegerField2.setSkin(new IntegerFieldSkin(localIntegerField2));
/*     */ 
/* 732 */         localIntegerField2.setMaxWidth(38.0D);
/* 733 */         this.rgbSettings.add(localIntegerField2, 2, 2);
/*     */ 
/* 735 */         localIntegerField2.valueProperty().bindBidirectional(CustomColorDialog.this.colorRectPane.green);
/* 736 */         localSlider2.valueProperty().bindBidirectional(CustomColorDialog.this.colorRectPane.green);
/*     */ 
/* 739 */         Label localLabel3 = new Label("Blue:      ");
/*     */ 
/* 741 */         localLabel3.setMinWidth(68.0D);
/* 742 */         this.rgbSettings.add(localLabel3, 0, 3);
/*     */ 
/* 744 */         Slider localSlider3 = new Slider(0.0D, 255.0D, 100.0D);
/* 745 */         localSlider3.setPrefWidth(100.0D);
/* 746 */         this.rgbSettings.add(localSlider3, 1, 3);
/*     */ 
/* 748 */         IntegerField localIntegerField3 = new IntegerField(255);
/* 749 */         localIntegerField3.setSkin(new IntegerFieldSkin(localIntegerField3));
/*     */ 
/* 751 */         localIntegerField3.setMaxWidth(38.0D);
/* 752 */         this.rgbSettings.add(localIntegerField3, 2, 3);
/*     */ 
/* 754 */         Region localRegion2 = new Region();
/* 755 */         localRegion2.setPrefHeight(4.0D);
/* 756 */         this.rgbSettings.add(localRegion2, 0, 4, 3, 1);
/*     */ 
/* 758 */         localIntegerField3.valueProperty().bindBidirectional(CustomColorDialog.this.colorRectPane.blue);
/* 759 */         localSlider3.valueProperty().bindBidirectional(CustomColorDialog.this.colorRectPane.blue);
/*     */       }
/* 761 */       this.settingsPane.getChildren().setAll(new Node[] { this.rgbSettings });
/*     */     }
/*     */ 
/*     */     private void showWebSettings() {
/* 765 */       if (this.webSettings == null) {
/* 766 */         this.webSettings = new GridPane();
/* 767 */         this.webSettings.setHgap(5.0D);
/* 768 */         this.webSettings.setVgap(4.0D);
/* 769 */         this.webSettings.setManaged(false);
/*     */ 
/* 771 */         Region localRegion1 = new Region();
/* 772 */         localRegion1.setPrefHeight(3.0D);
/* 773 */         this.webSettings.add(localRegion1, 0, 0, 3, 1);
/*     */ 
/* 775 */         Label localLabel = new Label("Web:        ");
/* 776 */         localLabel.setMinWidth((-1.0D / 0.0D));
/* 777 */         this.webSettings.add(localLabel, 0, 1);
/*     */ 
/* 779 */         CustomColorDialog.this.webField = new WebColorField();
/* 780 */         CustomColorDialog.this.webField.setSkin(new WebColorFieldSkin(CustomColorDialog.this.webField));
/* 781 */         CustomColorDialog.this.webField.valueProperty().bindBidirectional(CustomColorDialog.this.colorRectPane.colorProperty());
/* 782 */         CustomColorDialog.this.webField.setPrefColumnCount(6);
/* 783 */         this.webSettings.add(CustomColorDialog.this.webField, 1, 1);
/*     */ 
/* 785 */         Region localRegion2 = new Region();
/* 786 */         localRegion2.setPrefHeight(22.0D);
/* 787 */         this.webSettings.add(localRegion2, 0, 2, 3, 1);
/*     */ 
/* 789 */         Region localRegion3 = new Region();
/* 790 */         localRegion3.setPrefHeight(22.0D);
/* 791 */         this.webSettings.add(localRegion3, 0, 3, 3, 1);
/*     */ 
/* 793 */         Region localRegion4 = new Region();
/* 794 */         localRegion4.setPrefHeight(4.0D);
/* 795 */         this.webSettings.add(localRegion4, 0, 4, 3, 1);
/*     */       }
/* 797 */       this.settingsPane.getChildren().setAll(new Node[] { this.webSettings });
/*     */     }
/*     */ 
/*     */     public Label getCurrentColorLabel() {
/* 801 */       return this.currentColorLabel;
/*     */     }
/*     */ 
/*     */     public void layoutChildren() {
/* 805 */       double d1 = getInsets().getLeft();
/* 806 */       double d2 = getInsets().getTop();
/*     */ 
/* 809 */       this.currentAndNewColor.resizeRelocate(d1, d2, 256.0D, 18.0D);
/*     */ 
/* 811 */       this.currentNewColorBorder.relocate(d1, d2 + CustomColorDialog.this.controlsPane.currentColorLabel.prefHeight(-1.0D) + 2.0D);
/*     */ 
/* 813 */       double d3 = CustomColorDialog.computeXOffset(this.currentAndNewColor.prefWidth(-1.0D), this.hBox.prefWidth(-1.0D), HPos.CENTER);
/*     */ 
/* 815 */       GridPane localGridPane = (GridPane)this.settingsPane.getChildren().get(0);
/* 816 */       localGridPane.resize(228.0D, localGridPane.prefHeight(-1.0D));
/*     */ 
/* 818 */       double d4 = ((Node)this.settingsPane.getChildren().get(0)).prefHeight(-1.0D);
/*     */ 
/* 820 */       this.whiteBox.resizeRelocate(d1, d2 + this.currentAndNewColor.prefHeight(-1.0D) + this.hBox.prefHeight(-1.0D) / 2.0D, 256.0D, d4 + this.hBox.prefHeight(-1.0D) / 2.0D);
/*     */ 
/* 823 */       this.hBox.resizeRelocate(d1 + d3, d2 + this.currentAndNewColor.prefHeight(-1.0D), this.hBox.prefWidth(-1.0D), this.hBox.prefHeight(-1.0D));
/*     */ 
/* 826 */       this.settingsPane.resizeRelocate(d1 + 10.0D, d2 + this.currentAndNewColor.prefHeight(-1.0D) + this.hBox.prefHeight(-1.0D), 228.0D, d4);
/*     */ 
/* 829 */       this.alphaSettings.resizeRelocate(d1 + 10.0D, d2 + this.currentAndNewColor.prefHeight(-1.0D) + this.hBox.prefHeight(-1.0D) + d4, 228.0D, this.alphaSettings.prefHeight(-1.0D));
/*     */ 
/* 833 */       double d5 = CustomColorDialog.computeXOffset(this.currentAndNewColor.prefWidth(-1.0D), this.buttonBox.prefWidth(-1.0D), HPos.RIGHT);
/* 834 */       this.buttonBox.resizeRelocate(d1 + d5, d2 + this.currentAndNewColor.prefHeight(-1.0D) + this.hBox.prefHeight(-1.0D) + d4 + this.alphaSettings.prefHeight(-1.0D), this.buttonBox.prefWidth(-1.0D), this.buttonBox.prefHeight(-1.0D));
/*     */     }
/*     */ 
/*     */     public double computePrefHeight(double paramDouble)
/*     */     {
/* 839 */       double d = ((Node)this.settingsPane.getChildren().get(0)).prefHeight(-1.0D);
/* 840 */       return getInsets().getTop() + this.currentAndNewColor.prefHeight(-1.0D) + this.hBox.prefHeight(-1.0D) + d + this.alphaSettings.prefHeight(-1.0D) + this.buttonBox.prefHeight(-1.0D) + getInsets().getBottom();
/*     */     }
/*     */ 
/*     */     public double computePrefWidth(double paramDouble)
/*     */     {
/* 848 */       return getInsets().getLeft() + 256.0D + getInsets().getRight();
/*     */     }
/*     */   }
/*     */ 
/*     */   static enum ColorSettingsMode
/*     */   {
/* 410 */     HSB, 
/* 411 */     RGB, 
/* 412 */     WEB;
/*     */   }
/*     */ 
/*     */   class ColorRectPane extends StackPane
/*     */   {
/* 168 */     private boolean changeIsLocal = false;
/* 169 */     DoubleProperty hue = new SimpleDoubleProperty(-1.0D) {
/*     */       protected void invalidated() {
/* 171 */         if (!CustomColorDialog.ColorRectPane.this.changeIsLocal) {
/* 172 */           CustomColorDialog.ColorRectPane.this.changeIsLocal = true;
/* 173 */           CustomColorDialog.ColorRectPane.this.updateHSBColor();
/* 174 */           CustomColorDialog.ColorRectPane.this.changeIsLocal = false;
/*     */         }
/*     */       }
/* 169 */     };
/*     */ 
/* 178 */     DoubleProperty sat = new SimpleDoubleProperty(-1.0D) {
/*     */       protected void invalidated() {
/* 180 */         if (!CustomColorDialog.ColorRectPane.this.changeIsLocal) {
/* 181 */           CustomColorDialog.ColorRectPane.this.changeIsLocal = true;
/* 182 */           CustomColorDialog.ColorRectPane.this.updateHSBColor();
/* 183 */           CustomColorDialog.ColorRectPane.this.changeIsLocal = false;
/*     */         }
/*     */       }
/* 178 */     };
/*     */ 
/* 187 */     DoubleProperty bright = new SimpleDoubleProperty(-1.0D) {
/*     */       protected void invalidated() {
/* 189 */         if (!CustomColorDialog.ColorRectPane.this.changeIsLocal) {
/* 190 */           CustomColorDialog.ColorRectPane.this.changeIsLocal = true;
/* 191 */           CustomColorDialog.ColorRectPane.this.updateHSBColor();
/* 192 */           CustomColorDialog.ColorRectPane.this.changeIsLocal = false;
/*     */         }
/*     */       }
/* 187 */     };
/*     */ 
/* 196 */     private ObjectProperty<Color> color = new SimpleObjectProperty();
/*     */ 
/* 201 */     IntegerProperty red = new SimpleIntegerProperty(-1) {
/*     */       protected void invalidated() {
/* 203 */         if (!CustomColorDialog.ColorRectPane.this.changeIsLocal) {
/* 204 */           CustomColorDialog.ColorRectPane.this.changeIsLocal = true;
/* 205 */           CustomColorDialog.ColorRectPane.this.updateRGBColor();
/* 206 */           CustomColorDialog.ColorRectPane.this.changeIsLocal = false;
/*     */         }
/*     */       }
/* 201 */     };
/*     */ 
/* 211 */     IntegerProperty green = new SimpleIntegerProperty(-1) {
/*     */       protected void invalidated() {
/* 213 */         if (!CustomColorDialog.ColorRectPane.this.changeIsLocal) {
/* 214 */           CustomColorDialog.ColorRectPane.this.changeIsLocal = true;
/* 215 */           CustomColorDialog.ColorRectPane.this.updateRGBColor();
/* 216 */           CustomColorDialog.ColorRectPane.this.changeIsLocal = false;
/*     */         }
/*     */       }
/* 211 */     };
/*     */ 
/* 221 */     IntegerProperty blue = new SimpleIntegerProperty(-1) {
/*     */       protected void invalidated() {
/* 223 */         if (!CustomColorDialog.ColorRectPane.this.changeIsLocal) {
/* 224 */           CustomColorDialog.ColorRectPane.this.changeIsLocal = true;
/* 225 */           CustomColorDialog.ColorRectPane.this.updateRGBColor();
/* 226 */           CustomColorDialog.ColorRectPane.this.changeIsLocal = false;
/*     */         }
/*     */       }
/* 221 */     };
/*     */ 
/* 231 */     DoubleProperty alpha = new SimpleDoubleProperty(100.0D) {
/*     */       protected void invalidated() {
/* 233 */         if (!CustomColorDialog.ColorRectPane.this.changeIsLocal) {
/* 234 */           CustomColorDialog.ColorRectPane.this.changeIsLocal = true;
/* 235 */           switch (CustomColorDialog.2.$SwitchMap$com$sun$javafx$scene$control$skin$CustomColorDialog$ColorSettingsMode[CustomColorDialog.this.controlsPane.colorSettingsMode.ordinal()]) {
/*     */           case 1:
/* 237 */             CustomColorDialog.ColorRectPane.this.updateHSBColor();
/* 238 */             break;
/*     */           case 2:
/* 240 */             CustomColorDialog.ColorRectPane.this.updateRGBColor();
/* 241 */             break;
/*     */           case 3:
/*     */           }
/*     */ 
/* 245 */           CustomColorDialog.ColorRectPane.this.changeIsLocal = false;
/*     */         }
/*     */       }
/* 231 */     };
/*     */ 
/*     */     public ObjectProperty<Color> colorProperty()
/*     */     {
/* 197 */       return this.color; } 
/* 198 */     public Color getColor() { return (Color)this.color.get(); } 
/* 199 */     public void setColor(Color paramColor) { this.color.set(paramColor); }
/*     */ 
/*     */ 
/*     */     private void updateRGBColor()
/*     */     {
/* 251 */       setColor(Color.rgb(this.red.get(), this.green.get(), this.blue.get(), CustomColorDialog.clamp(this.alpha.get() / 100.0D)));
/* 252 */       this.hue.set(getColor().getHue());
/* 253 */       this.sat.set(getColor().getSaturation() * 100.0D);
/* 254 */       this.bright.set(getColor().getBrightness() * 100.0D);
/*     */     }
/*     */ 
/*     */     private void updateHSBColor() {
/* 258 */       setColor(Color.hsb(this.hue.get(), CustomColorDialog.clamp(this.sat.get() / 100.0D), CustomColorDialog.clamp(this.bright.get() / 100.0D), CustomColorDialog.clamp(this.alpha.get() / 100.0D)));
/*     */ 
/* 260 */       this.red.set(CustomColorDialog.doubleToInt(getColor().getRed()));
/* 261 */       this.green.set(CustomColorDialog.doubleToInt(getColor().getGreen()));
/* 262 */       this.blue.set(CustomColorDialog.doubleToInt(getColor().getBlue()));
/*     */     }
/*     */ 
/*     */     private void colorChanged() {
/* 266 */       if (!this.changeIsLocal) {
/* 267 */         this.changeIsLocal = true;
/* 268 */         this.hue.set(getColor().getHue());
/* 269 */         this.sat.set(getColor().getSaturation() * 100.0D);
/* 270 */         this.bright.set(getColor().getBrightness() * 100.0D);
/* 271 */         this.red.set(CustomColorDialog.doubleToInt(getColor().getRed()));
/* 272 */         this.green.set(CustomColorDialog.doubleToInt(getColor().getGreen()));
/* 273 */         this.blue.set(CustomColorDialog.doubleToInt(getColor().getBlue()));
/* 274 */         this.changeIsLocal = false;
/*     */       }
/*     */     }
/*     */ 
/*     */     public ColorRectPane()
/*     */     {
/* 280 */       getStyleClass().add("color-rect-pane");
/*     */ 
/* 282 */       this.color.addListener(new ChangeListener()
/*     */       {
/*     */         public void changed(ObservableValue<? extends Color> paramAnonymousObservableValue, Color paramAnonymousColor1, Color paramAnonymousColor2)
/*     */         {
/* 286 */           CustomColorDialog.ColorRectPane.this.colorChanged();
/*     */         }
/*     */       });
/* 290 */       CustomColorDialog.this.colorRectIndicator = new Circle(60.0D, 60.0D, 5.0D, null);
/* 291 */       CustomColorDialog.this.colorRectIndicator.setStroke(Color.WHITE);
/* 292 */       CustomColorDialog.this.colorRectIndicator.setEffect(new DropShadow(2.0D, 0.0D, 1.0D, Color.BLACK));
/*     */ 
/* 294 */       CustomColorDialog.this.colorRect = new Rectangle(200.0D, 200.0D);
/* 295 */       colorProperty().addListener(new ChangeListener()
/*     */       {
/*     */         public void changed(ObservableValue<? extends Color> paramAnonymousObservableValue, Color paramAnonymousColor1, Color paramAnonymousColor2) {
/* 298 */           CustomColorDialog.this.colorRect.setFill(Color.hsb(CustomColorDialog.ColorRectPane.this.hue.getValue().doubleValue(), 1.0D, 1.0D, CustomColorDialog.clamp(CustomColorDialog.ColorRectPane.this.alpha.get() / 100.0D)));
/*     */         }
/*     */       });
/* 302 */       CustomColorDialog.this.colorRectOverlayOne = new Rectangle(200.0D, 200.0D);
/* 303 */       CustomColorDialog.this.colorRectOverlayOne.setFill(new LinearGradient(0.0D, 0.0D, 1.0D, 0.0D, true, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.rgb(255, 255, 255, 1.0D)), new Stop(1.0D, Color.rgb(255, 255, 255, 0.0D)) }));
/*     */ 
/* 306 */       CustomColorDialog.this.colorRectOverlayOne.setStroke(Utils.deriveColor(Color.web("#d0d0d0"), 0.0D));
/*     */ 
/* 308 */       EventHandler local10 = new EventHandler() {
/*     */         public void handle(MouseEvent paramAnonymousMouseEvent) {
/* 310 */           double d1 = paramAnonymousMouseEvent.getX();
/* 311 */           double d2 = paramAnonymousMouseEvent.getY();
/* 312 */           CustomColorDialog.ColorRectPane.this.sat.set(CustomColorDialog.clamp(d1 / 200.0D) * 100.0D);
/* 313 */           CustomColorDialog.ColorRectPane.this.bright.set(100.0D - CustomColorDialog.clamp(d2 / 200.0D) * 100.0D);
/*     */         }
/*     */       };
/* 317 */       CustomColorDialog.this.colorRectOverlayTwo = new Rectangle(200.0D, 200.0D);
/* 318 */       CustomColorDialog.this.colorRectOverlayTwo.setFill(new LinearGradient(0.0D, 0.0D, 0.0D, 1.0D, true, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.rgb(0, 0, 0, 0.0D)), new Stop(1.0D, Color.rgb(0, 0, 0, 1.0D)) }));
/*     */ 
/* 320 */       CustomColorDialog.this.colorRectOverlayTwo.setOnMouseDragged(local10);
/* 321 */       CustomColorDialog.this.colorRectOverlayTwo.setOnMouseClicked(local10);
/*     */ 
/* 323 */       CustomColorDialog.this.colorBar = new Rectangle(20.0D, 200.0D);
/* 324 */       CustomColorDialog.this.colorBar.setFill(CustomColorDialog.access$600());
/* 325 */       CustomColorDialog.this.colorBar.setStroke(Utils.deriveColor(Color.web("#d0d0d0"), 0.0D));
/*     */ 
/* 327 */       CustomColorDialog.this.colorBarIndicator = new Rectangle(24.0D, 10.0D, null);
/* 328 */       CustomColorDialog.this.colorBarIndicator.setLayoutX(10.0D + CustomColorDialog.this.colorRect.getWidth() + 13.0D);
/* 329 */       CustomColorDialog.this.colorBarIndicator.setLayoutY(10.0D + CustomColorDialog.this.colorBar.getHeight() * (this.hue.get() / 360.0D));
/* 330 */       CustomColorDialog.this.colorBarIndicator.setArcWidth(4.0D);
/* 331 */       CustomColorDialog.this.colorBarIndicator.setArcHeight(4.0D);
/* 332 */       CustomColorDialog.this.colorBarIndicator.setStroke(Color.WHITE);
/* 333 */       CustomColorDialog.this.colorBarIndicator.setEffect(new DropShadow(2.0D, 0.0D, 1.0D, Color.BLACK));
/*     */ 
/* 336 */       this.hue.addListener(new ChangeListener() {
/*     */         public void changed(ObservableValue<? extends Number> paramAnonymousObservableValue, Number paramAnonymousNumber1, Number paramAnonymousNumber2) {
/* 338 */           CustomColorDialog.this.colorBarIndicator.setLayoutY(10.0D + 200.0D * (CustomColorDialog.ColorRectPane.this.hue.get() / 360.0D));
/*     */         }
/*     */       });
/* 341 */       this.sat.addListener(new ChangeListener() {
/*     */         public void changed(ObservableValue<? extends Number> paramAnonymousObservableValue, Number paramAnonymousNumber1, Number paramAnonymousNumber2) {
/* 343 */           CustomColorDialog.this.colorRectIndicator.setCenterX(10.0D + CustomColorDialog.this.colorRectIndicator.getRadius() + 200.0D * (CustomColorDialog.ColorRectPane.this.sat.get() / 100.0D));
/*     */         }
/*     */       });
/* 347 */       this.bright.addListener(new ChangeListener() {
/*     */         public void changed(ObservableValue<? extends Number> paramAnonymousObservableValue, Number paramAnonymousNumber1, Number paramAnonymousNumber2) {
/* 349 */           CustomColorDialog.this.colorRectIndicator.setCenterY(10.0D + CustomColorDialog.this.colorRectIndicator.getRadius() + 200.0D * (1.0D - CustomColorDialog.ColorRectPane.this.bright.get() / 100.0D));
/*     */         }
/*     */       });
/* 353 */       this.alpha.addListener(new ChangeListener()
/*     */       {
/*     */         public void changed(ObservableValue<? extends Number> paramAnonymousObservableValue, Number paramAnonymousNumber1, Number paramAnonymousNumber2)
/*     */         {
/*     */         }
/*     */       });
/* 358 */       EventHandler local15 = new EventHandler() {
/*     */         public void handle(MouseEvent paramAnonymousMouseEvent) {
/* 360 */           double d = paramAnonymousMouseEvent.getY();
/* 361 */           CustomColorDialog.ColorRectPane.this.hue.set(CustomColorDialog.clamp(d / 200.0D) * 360.0D);
/*     */         }
/*     */       };
/* 365 */       CustomColorDialog.this.colorBar.setOnMouseDragged(local15);
/* 366 */       CustomColorDialog.this.colorBar.setOnMouseClicked(local15);
/*     */ 
/* 369 */       getChildren().addAll(new Node[] { CustomColorDialog.this.colorRect, CustomColorDialog.this.colorRectOverlayOne, CustomColorDialog.this.colorRectOverlayTwo, CustomColorDialog.this.colorBar, CustomColorDialog.this.colorRectIndicator, CustomColorDialog.this.colorBarIndicator });
/*     */     }
/*     */ 
/*     */     private void updateValues()
/*     */     {
/* 375 */       this.changeIsLocal = true;
/*     */ 
/* 377 */       this.hue.set(CustomColorDialog.this.currentColor.getHue());
/* 378 */       this.sat.set(CustomColorDialog.this.currentColor.getSaturation() * 100.0D);
/* 379 */       this.bright.set(CustomColorDialog.this.currentColor.getBrightness() * 100.0D);
/* 380 */       setColor(Color.hsb(this.hue.get(), CustomColorDialog.clamp(this.sat.get() / 100.0D), CustomColorDialog.clamp(this.bright.get() / 100.0D), CustomColorDialog.clamp(this.alpha.get() / 100.0D)));
/*     */ 
/* 382 */       this.red.set(CustomColorDialog.doubleToInt(getColor().getRed()));
/* 383 */       this.green.set(CustomColorDialog.doubleToInt(getColor().getGreen()));
/* 384 */       this.blue.set(CustomColorDialog.doubleToInt(getColor().getBlue()));
/* 385 */       this.changeIsLocal = false;
/*     */     }
/*     */ 
/*     */     public void layoutChildren() {
/* 389 */       double d1 = getInsets().getLeft();
/* 390 */       double d2 = getInsets().getTop();
/*     */ 
/* 393 */       CustomColorDialog.this.colorRect.relocate(d1, d2);
/* 394 */       CustomColorDialog.this.colorRectOverlayOne.relocate(d1, d2);
/* 395 */       CustomColorDialog.this.colorRectOverlayTwo.relocate(d1, d2);
/*     */ 
/* 397 */       CustomColorDialog.this.colorBar.relocate(d1 + CustomColorDialog.this.colorRect.prefWidth(-1.0D) + 9.0D, d2);
/*     */     }
/*     */ 
/*     */     public double computePrefWidth(double paramDouble) {
/* 401 */       return getInsets().getLeft() + CustomColorDialog.this.colorRect.prefWidth(-1.0D) + 9.0D + CustomColorDialog.this.colorBar.prefWidth(-1.0D) + (CustomColorDialog.this.colorBarIndicator.getBoundsInParent().getWidth() - CustomColorDialog.this.colorBar.prefWidth(-1.0D)) + getInsets().getRight();
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.CustomColorDialog
 * JD-Core Version:    0.6.2
 */