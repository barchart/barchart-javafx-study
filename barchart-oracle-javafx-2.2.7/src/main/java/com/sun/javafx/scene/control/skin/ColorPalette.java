/*     */ package com.sun.javafx.scene.control.skin;
/*     */ 
/*     */ import com.sun.javafx.css.StyleManager;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.ReadOnlyBooleanProperty;
/*     */ import javafx.beans.property.ReadOnlyBooleanWrapper;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ import javafx.collections.FXCollections;
/*     */ import javafx.collections.ListChangeListener;
/*     */ import javafx.collections.ListChangeListener.Change;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.event.ActionEvent;
/*     */ import javafx.event.Event;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.geometry.Bounds;
/*     */ import javafx.geometry.Insets;
/*     */ import javafx.geometry.Pos;
/*     */ import javafx.geometry.Side;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.Scene;
/*     */ import javafx.scene.control.Button;
/*     */ import javafx.scene.control.ColorPicker;
/*     */ import javafx.scene.control.ContextMenu;
/*     */ import javafx.scene.control.Hyperlink;
/*     */ import javafx.scene.control.Label;
/*     */ import javafx.scene.control.MenuItem;
/*     */ import javafx.scene.control.PopupControl;
/*     */ import javafx.scene.control.Separator;
/*     */ import javafx.scene.control.Tooltip;
/*     */ import javafx.scene.input.KeyEvent;
/*     */ import javafx.scene.input.MouseButton;
/*     */ import javafx.scene.input.MouseEvent;
/*     */ import javafx.scene.layout.GridPane;
/*     */ import javafx.scene.layout.StackPane;
/*     */ import javafx.scene.paint.Color;
/*     */ import javafx.scene.shape.Rectangle;
/*     */ import javafx.scene.shape.StrokeType;
/*     */ import javafx.stage.Window;
/*     */ 
/*     */ public class ColorPalette extends StackPane
/*     */ {
/*     */   private static final int GAP = 15;
/*     */   private static final int SQUARE_SIZE = 15;
/*     */   private static final int NUM_OF_COLUMNS = 12;
/*     */   private static final int NUM_OF_ROWS = 10;
/*     */   private static final int MAX_CUSTOM_ROWS = 3;
/*     */   private static final int LABEL_GAP = 2;
/*  44 */   private boolean customColorAdded = false;
/*     */   ColorPickerGrid colorPickerGrid;
/*     */   ColorPicker colorPicker;
/*  47 */   GridPane customColorGrid = new GridPane();
/*  48 */   Hyperlink customColorLink = new Hyperlink("Custom Color..");
/*  49 */   Separator separator = new Separator();
/*     */   Window owner;
/*  51 */   Label customColorLabel = new Label("Custom Colors");
/*  52 */   CustomColorDialog customColorDialog = null;
/*  53 */   private final List<ColorSquare> customSquares = FXCollections.observableArrayList();
/*     */   private double x;
/*     */   private double y;
/*     */   private PopupControl popupControl;
/*     */   private ColorSquare focusedSquare;
/*  59 */   private ContextMenu contextMenu = null;
/*     */ 
/*  61 */   private Color mouseDragColor = null;
/*  62 */   private boolean dragDetected = false;
/*     */ 
/*     */   public ColorPalette(Color paramColor, final ColorPicker paramColorPicker) {
/*  65 */     getStyleClass().add("color-palette");
/*  66 */     this.colorPicker = paramColorPicker;
/*  67 */     this.owner = paramColorPicker.getScene().getWindow();
/*  68 */     this.colorPickerGrid = new ColorPickerGrid(paramColor);
/*  69 */     this.colorPickerGrid.requestFocus();
/*  70 */     this.colorPickerGrid.setFocusTraversable(true);
/*  71 */     this.customColorLabel.setAlignment(Pos.CENTER_LEFT);
/*  72 */     this.customColorLink.setPrefWidth(this.colorPickerGrid.prefWidth(-1.0D));
/*  73 */     this.customColorLink.setAlignment(Pos.CENTER);
/*  74 */     this.customColorLink.setFocusTraversable(true);
/*  75 */     this.customColorLink.setVisited(true);
/*     */ 
/*  77 */     final EventHandler local1 = new EventHandler() {
/*     */       public void handle(ActionEvent paramAnonymousActionEvent) {
/*  79 */         paramColorPicker.hide();
/*     */       }
/*     */     };
/*  82 */     this.customColorLink.setOnAction(new EventHandler() {
/*     */       public void handle(ActionEvent paramAnonymousActionEvent) {
/*  84 */         if (ColorPalette.this.customColorDialog == null) {
/*  85 */           ColorPalette.this.customColorDialog = new CustomColorDialog(ColorPalette.this.owner);
/*  86 */           ColorPalette.this.customColorDialog.customColorProperty.addListener(new ChangeListener()
/*     */           {
/*     */             public void changed(ObservableValue<? extends Color> paramAnonymous2ObservableValue, Color paramAnonymous2Color1, Color paramAnonymous2Color2) {
/*  89 */               Color localColor = (Color)ColorPalette.this.customColorDialog.customColorProperty.get();
/*  90 */               if (ColorPalette.this.customColorDialog.saveCustomColor) {
/*  91 */                 ColorPalette.ColorSquare localColorSquare = new ColorPalette.ColorSquare(ColorPalette.this, localColor, true);
/*  92 */                 ColorPalette.this.customSquares.add(localColorSquare);
/*  93 */                 ColorPalette.this.buildCustomColors();
/*  94 */                 ColorPalette.2.this.val$colorPicker.getCustomColors().add(localColor);
/*  95 */                 ColorPalette.this.updateSelection(localColor);
/*     */               }
/*  97 */               if ((ColorPalette.this.customColorDialog.saveCustomColor) || (ColorPalette.this.customColorDialog.useCustomColor)) {
/*  98 */                 Event.fireEvent(ColorPalette.2.this.val$colorPicker, new ActionEvent());
/*     */               }
/* 100 */               ColorPalette.2.this.val$colorPicker.setValue(localColor);
/*     */             }
/*     */           });
/* 103 */           ColorPalette.this.customColorDialog.saveButton.addEventHandler(ActionEvent.ACTION, local1);
/* 104 */           ColorPalette.this.customColorDialog.useButton.addEventHandler(ActionEvent.ACTION, local1);
/*     */         }
/* 106 */         ColorPalette.this.customColorDialog.setCurrentColor((Color)paramColorPicker.valueProperty().get());
/* 107 */         if (ColorPalette.this.popupControl != null) ColorPalette.this.popupControl.setAutoHide(false);
/* 108 */         ColorPalette.this.customColorDialog.show(ColorPalette.this.x, ColorPalette.this.y);
/* 109 */         if (ColorPalette.this.popupControl != null) ColorPalette.this.popupControl.setAutoHide(true);
/*     */       }
/*     */     });
/* 113 */     initNavigation();
/* 114 */     this.customColorGrid.setVisible(false);
/* 115 */     this.customColorGrid.setFocusTraversable(true);
/* 116 */     for (Color localColor : paramColorPicker.getCustomColors()) {
/* 117 */       this.customSquares.add(new ColorSquare(localColor, true));
/*     */     }
/* 119 */     buildCustomColors();
/* 120 */     paramColorPicker.getCustomColors().addListener(new ListChangeListener() {
/*     */       public void onChanged(ListChangeListener.Change<? extends Color> paramAnonymousChange) {
/* 122 */         ColorPalette.this.customSquares.clear();
/* 123 */         for (Color localColor : paramColorPicker.getCustomColors()) {
/* 124 */           ColorPalette.this.customSquares.add(new ColorPalette.ColorSquare(ColorPalette.this, localColor, true));
/*     */         }
/* 126 */         ColorPalette.this.buildCustomColors();
/*     */       }
/*     */     });
/* 129 */     getChildren().addAll(new Node[] { this.colorPickerGrid, this.customColorLabel, this.customColorGrid, this.separator, this.customColorLink });
/*     */   }
/*     */ 
/*     */   private void buildCustomColors() {
/* 133 */     int i = 0;
/* 134 */     int j = 0;
/* 135 */     int k = this.customSquares.size() % 12;
/* 136 */     int m = k == 0 ? 0 : 12 - k;
/*     */ 
/* 138 */     this.customColorGrid.getChildren().clear();
/* 139 */     if (this.customSquares.isEmpty()) {
/* 140 */       this.customColorAdded = false;
/* 141 */       this.customColorLabel.setVisible(false);
/* 142 */       this.customColorGrid.setVisible(false);
/* 143 */       return;
/*     */     }
/* 145 */     if (!this.customColorAdded) {
/* 146 */       this.customColorAdded = true;
/* 147 */       this.customColorLabel.setVisible(true);
/* 148 */       this.customColorGrid.setVisible(true);
/* 149 */       if (this.contextMenu == null) {
/* 150 */         localObject = new MenuItem("Remove Color");
/* 151 */         ((MenuItem)localObject).setOnAction(new EventHandler() {
/*     */           public void handle(ActionEvent paramAnonymousActionEvent) {
/* 153 */             ColorPalette.ColorSquare localColorSquare = (ColorPalette.ColorSquare)ColorPalette.this.contextMenu.getOwnerNode();
/* 154 */             ColorPalette.this.colorPicker.getCustomColors().remove(localColorSquare.rectangle.getFill());
/* 155 */             ColorPalette.this.customSquares.remove(localColorSquare);
/* 156 */             ColorPalette.this.buildCustomColors();
/*     */           }
/*     */         });
/* 159 */         this.contextMenu = new ContextMenu(new MenuItem[] { localObject });
/*     */       }
/*     */     }
/* 162 */     for (Object localObject = this.customSquares.iterator(); ((Iterator)localObject).hasNext(); ) { localColorSquare = (ColorSquare)((Iterator)localObject).next();
/* 163 */       this.customColorGrid.add(localColorSquare, i, j);
/* 164 */       i++;
/* 165 */       if (i == 12) {
/* 166 */         i = 0;
/* 167 */         j++;
/*     */       }
/*     */     }
/*     */     ColorSquare localColorSquare;
/* 170 */     for (int n = 0; n < m; n++) {
/* 171 */       localColorSquare = new ColorSquare(null);
/* 172 */       this.customColorGrid.add(localColorSquare, i, j);
/* 173 */       i++;
/*     */     }
/* 175 */     requestLayout();
/*     */   }
/*     */ 
/*     */   private void initNavigation() {
/* 179 */     setOnKeyPressed(new EventHandler() {
/*     */       public void handle(KeyEvent paramAnonymousKeyEvent) {
/* 181 */         switch (ColorPalette.6.$SwitchMap$javafx$scene$input$KeyCode[paramAnonymousKeyEvent.getCode().ordinal()]) {
/*     */         case 1:
/* 183 */           ColorPalette.this.processLeftKey(paramAnonymousKeyEvent);
/* 184 */           break;
/*     */         case 2:
/* 186 */           ColorPalette.this.processRightKey(paramAnonymousKeyEvent);
/* 187 */           break;
/*     */         case 3:
/* 189 */           ColorPalette.this.processUpKey(paramAnonymousKeyEvent);
/* 190 */           break;
/*     */         case 4:
/* 192 */           ColorPalette.this.processDownKey(paramAnonymousKeyEvent);
/* 193 */           break;
/*     */         case 5:
/* 195 */           ColorPalette.this.processSelectKey(paramAnonymousKeyEvent);
/*     */         }
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   private void processSelectKey(KeyEvent paramKeyEvent)
/*     */   {
/* 203 */     if (this.focusedSquare != null) this.focusedSquare.selectColor(paramKeyEvent);
/*     */   }
/*     */ 
/*     */   private void processLeftKey(KeyEvent paramKeyEvent)
/*     */   {
/*     */     ColorSquare localColorSquare2;
/* 208 */     for (int i = 119; i >= 0; i--) {
/* 209 */       ColorSquare localColorSquare1 = (ColorSquare)this.colorPickerGrid.getSquares().get(i);
/* 210 */       if (localColorSquare1 == this.focusedSquare) {
/* 211 */         localColorSquare2 = (ColorSquare)this.colorPickerGrid.getSquares().get(i != 0 ? i - 1 : 119);
/*     */ 
/* 213 */         localColorSquare2.requestFocus();
/* 214 */         this.focusedSquare = localColorSquare2;
/* 215 */         return;
/*     */       }
/*     */     }
/*     */ 
/* 219 */     int j = this.customColorGrid.getChildren().size();
/* 220 */     for (i = j - 1; i >= 0; i--) {
/* 221 */       localColorSquare2 = (ColorSquare)this.customColorGrid.getChildren().get(i);
/* 222 */       if (localColorSquare2 == this.focusedSquare) {
/* 223 */         ColorSquare localColorSquare3 = (ColorSquare)this.customColorGrid.getChildren().get(i != 0 ? i - 1 : j - 1);
/*     */ 
/* 225 */         localColorSquare3.requestFocus();
/* 226 */         this.focusedSquare = localColorSquare3;
/* 227 */         return;
/*     */       }
/*     */     }
/* 230 */     if (i == -1) {
/* 231 */       localColorSquare2 = (ColorSquare)this.colorPickerGrid.getSquares().get(119);
/* 232 */       this.focusedSquare = localColorSquare2;
/* 233 */       localColorSquare2.requestFocus();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void processUpKey(KeyEvent paramKeyEvent)
/*     */   {
/*     */     ColorSquare localColorSquare2;
/* 239 */     for (int i = 119; i >= 0; i--) {
/* 240 */       ColorSquare localColorSquare1 = (ColorSquare)this.colorPickerGrid.getSquares().get(i);
/* 241 */       if (localColorSquare1 == this.focusedSquare) {
/* 242 */         localColorSquare2 = (ColorSquare)this.colorPickerGrid.getSquares().get(i - 12 >= 0 ? i - 12 : 108 + i);
/*     */ 
/* 244 */         localColorSquare2.requestFocus();
/* 245 */         this.focusedSquare = localColorSquare2;
/* 246 */         return;
/*     */       }
/*     */     }
/* 249 */     int j = this.customColorGrid.getChildren().size();
/* 250 */     for (i = j - 1; i >= 0; i--)
/*     */     {
/* 252 */       localColorSquare2 = (ColorSquare)this.customColorGrid.getChildren().get(i);
/* 253 */       ColorSquare localColorSquare3 = null;
/* 254 */       if (localColorSquare2 == this.focusedSquare) {
/* 255 */         if (i - 12 >= 0) {
/* 256 */           localColorSquare3 = (ColorSquare)this.customColorGrid.getChildren().get(i - 12);
/*     */         } else {
/* 258 */           int k = GridPane.getRowIndex((Node)this.customColorGrid.getChildren().get(j - 1)).intValue();
/* 259 */           localColorSquare3 = (ColorSquare)this.customColorGrid.getChildren().get(k * 12 + i);
/*     */         }
/* 261 */         localColorSquare3.requestFocus();
/* 262 */         this.focusedSquare = localColorSquare3;
/* 263 */         return;
/*     */       }
/*     */     }
/* 266 */     if (i == -1) {
/* 267 */       localColorSquare2 = (ColorSquare)this.colorPickerGrid.getSquares().get(119);
/* 268 */       this.focusedSquare = localColorSquare2;
/* 269 */       this.focusedSquare.requestFocus();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void processRightKey(KeyEvent paramKeyEvent)
/*     */   {
/*     */     ColorSquare localColorSquare2;
/* 275 */     for (int i = 0; i < 120; i++) {
/* 276 */       ColorSquare localColorSquare1 = (ColorSquare)this.colorPickerGrid.getSquares().get(i);
/* 277 */       if (localColorSquare1 == this.focusedSquare) {
/* 278 */         localColorSquare2 = (ColorSquare)this.colorPickerGrid.getSquares().get(i != 119 ? i + 1 : 0);
/*     */ 
/* 280 */         localColorSquare2.requestFocus();
/* 281 */         this.focusedSquare = localColorSquare2;
/* 282 */         return;
/*     */       }
/*     */     }
/*     */ 
/* 286 */     int j = this.customColorGrid.getChildren().size();
/* 287 */     for (i = 0; i < j; i++) {
/* 288 */       localColorSquare2 = (ColorSquare)this.customColorGrid.getChildren().get(i);
/* 289 */       if (localColorSquare2 == this.focusedSquare) {
/* 290 */         ColorSquare localColorSquare3 = (ColorSquare)this.customColorGrid.getChildren().get(i != j - 1 ? i + 1 : 0);
/*     */ 
/* 292 */         localColorSquare3.requestFocus();
/* 293 */         this.focusedSquare = localColorSquare3;
/* 294 */         return;
/*     */       }
/*     */     }
/* 297 */     if (i == j) {
/* 298 */       localColorSquare2 = (ColorSquare)this.colorPickerGrid.getSquares().get(0);
/* 299 */       this.focusedSquare = localColorSquare2;
/* 300 */       this.focusedSquare.requestFocus();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void processDownKey(KeyEvent paramKeyEvent)
/*     */   {
/*     */     ColorSquare localColorSquare2;
/* 306 */     for (int i = 0; i < 120; i++) {
/* 307 */       ColorSquare localColorSquare1 = (ColorSquare)this.colorPickerGrid.getSquares().get(i);
/* 308 */       if (localColorSquare1 == this.focusedSquare) {
/* 309 */         localColorSquare2 = (ColorSquare)this.colorPickerGrid.getSquares().get(i + 12 < 120 ? i + 12 : i - 108);
/*     */ 
/* 311 */         localColorSquare2.requestFocus();
/* 312 */         this.focusedSquare = localColorSquare2;
/* 313 */         return;
/*     */       }
/*     */     }
/*     */ 
/* 317 */     int j = this.customColorGrid.getChildren().size();
/* 318 */     for (i = 0; i < j; i++) {
/* 319 */       localColorSquare2 = (ColorSquare)this.customColorGrid.getChildren().get(i);
/* 320 */       ColorSquare localColorSquare3 = null;
/* 321 */       if (localColorSquare2 == this.focusedSquare) {
/* 322 */         if (i + 12 < j) {
/* 323 */           localColorSquare3 = (ColorSquare)this.customColorGrid.getChildren().get(i + 12);
/*     */         } else {
/* 325 */           int k = GridPane.getRowIndex((Node)this.customColorGrid.getChildren().get(j - 1)).intValue();
/* 326 */           localColorSquare3 = (ColorSquare)this.customColorGrid.getChildren().get(i - k * 12);
/*     */         }
/* 328 */         localColorSquare3.requestFocus();
/* 329 */         this.focusedSquare = localColorSquare3;
/* 330 */         return;
/*     */       }
/*     */     }
/* 333 */     if (i == j) {
/* 334 */       localColorSquare2 = (ColorSquare)this.colorPickerGrid.getSquares().get(0);
/* 335 */       this.focusedSquare.requestFocus();
/* 336 */       this.focusedSquare = localColorSquare2;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setPopupControl(PopupControl paramPopupControl) {
/* 341 */     this.popupControl = paramPopupControl;
/*     */   }
/*     */ 
/*     */   public void setDialogLocation(double paramDouble1, double paramDouble2) {
/* 345 */     this.x = paramDouble1;
/* 346 */     this.y = paramDouble2;
/*     */   }
/*     */ 
/*     */   public ColorPickerGrid getColorGrid() {
/* 350 */     return this.colorPickerGrid;
/*     */   }
/*     */ 
/*     */   protected void layoutChildren() {
/* 354 */     double d1 = getInsets().getLeft();
/* 355 */     double d2 = getInsets().getTop();
/*     */ 
/* 358 */     this.colorPickerGrid.relocate(d1, d2);
/* 359 */     d2 = d2 + this.colorPickerGrid.prefHeight(-1.0D) + 15.0D;
/* 360 */     if (this.customColorAdded) {
/* 361 */       if (this.customColorLabel.isVisible()) {
/* 362 */         this.customColorLabel.resizeRelocate(d1, d2, this.colorPickerGrid.prefWidth(-1.0D), this.customColorLabel.prefHeight(d2));
/* 363 */         d2 = d2 + this.customColorLabel.prefHeight(-1.0D) + 2.0D;
/*     */       }
/* 365 */       this.customColorGrid.relocate(d1, d2);
/* 366 */       d2 = d2 + this.customColorGrid.prefHeight(-1.0D) + 15.0D;
/*     */     }
/* 368 */     this.separator.resizeRelocate(d1, d2, this.colorPickerGrid.prefWidth(-1.0D), this.separator.prefHeight(-1.0D));
/* 369 */     d2 = d2 + this.separator.prefHeight(-1.0D) + 15.0D;
/* 370 */     this.customColorLink.resizeRelocate(d1, d2, this.colorPickerGrid.prefWidth(-1.0D), this.customColorLink.prefHeight(-1.0D));
/*     */   }
/*     */ 
/*     */   protected double computePrefWidth(double paramDouble) {
/* 374 */     return getInsets().getLeft() + this.colorPickerGrid.prefWidth(-1.0D) + getInsets().getRight();
/*     */   }
/*     */ 
/*     */   protected double computePrefHeight(double paramDouble) {
/* 378 */     double d = this.colorPickerGrid.prefHeight(-1.0D) + 15.0D + (this.customColorAdded ? this.customColorGrid.prefHeight(-1.0D) + this.customColorLabel.prefHeight(-1.0D) + 2.0D + 15.0D : 0.0D) + this.separator.prefHeight(-1.0D) + 15.0D + this.customColorLink.prefHeight(-1.0D);
/*     */ 
/* 382 */     return getInsets().getTop() + d + getInsets().getBottom();
/*     */   }
/*     */ 
/*     */   public boolean isCustomColorDialogShowing() {
/* 386 */     if (this.customColorDialog != null) return this.customColorDialog.isVisible();
/* 387 */     return false;
/*     */   }
/*     */ 
/*     */   public void clearFocus()
/*     */   {
/* 511 */     this.colorPickerGrid.requestFocus();
/*     */   }
/*     */ 
/*     */   public void updateSelection(Color paramColor)
/*     */   {
/* 516 */     for (Iterator localIterator = this.colorPickerGrid.getSquares().iterator(); localIterator.hasNext(); ) { localColorSquare = (ColorSquare)localIterator.next();
/* 517 */       localColorSquare.setSelected(localColorSquare.rectangle.getFill().equals(paramColor));
/*     */     }
/* 520 */     ColorSquare localColorSquare;
/* 520 */     for (localIterator = this.customSquares.iterator(); localIterator.hasNext(); ) { localColorSquare = (ColorSquare)localIterator.next();
/* 521 */       localColorSquare.setSelected(localColorSquare.rectangle.getFill().equals(paramColor));
/*     */     }
/*     */   }
/*     */ 
/*     */   class ColorPickerGrid extends GridPane
/*     */   {
/*     */     private final List<ColorPalette.ColorSquare> squares;
/*     */     Window owner;
/* 593 */     double[] rawValues = { 255.0D, 255.0D, 255.0D, 242.0D, 242.0D, 242.0D, 230.0D, 230.0D, 230.0D, 204.0D, 204.0D, 204.0D, 179.0D, 179.0D, 179.0D, 153.0D, 153.0D, 153.0D, 128.0D, 128.0D, 128.0D, 102.0D, 102.0D, 102.0D, 77.0D, 77.0D, 77.0D, 51.0D, 51.0D, 51.0D, 26.0D, 26.0D, 26.0D, 0.0D, 0.0D, 0.0D, 0.0D, 51.0D, 51.0D, 0.0D, 26.0D, 128.0D, 26.0D, 0.0D, 104.0D, 51.0D, 0.0D, 51.0D, 77.0D, 0.0D, 26.0D, 153.0D, 0.0D, 0.0D, 153.0D, 51.0D, 0.0D, 153.0D, 77.0D, 0.0D, 153.0D, 102.0D, 0.0D, 153.0D, 153.0D, 0.0D, 102.0D, 102.0D, 0.0D, 0.0D, 51.0D, 0.0D, 26.0D, 77.0D, 77.0D, 26.0D, 51.0D, 153.0D, 51.0D, 26.0D, 128.0D, 77.0D, 26.0D, 77.0D, 102.0D, 26.0D, 51.0D, 179.0D, 26.0D, 26.0D, 179.0D, 77.0D, 26.0D, 179.0D, 102.0D, 26.0D, 179.0D, 128.0D, 26.0D, 179.0D, 179.0D, 26.0D, 128.0D, 128.0D, 26.0D, 26.0D, 77.0D, 26.0D, 51.0D, 102.0D, 102.0D, 51.0D, 77.0D, 179.0D, 77.0D, 51.0D, 153.0D, 102.0D, 51.0D, 102.0D, 128.0D, 51.0D, 77.0D, 204.0D, 51.0D, 51.0D, 204.0D, 102.0D, 51.0D, 204.0D, 128.0D, 51.0D, 204.0D, 153.0D, 51.0D, 204.0D, 204.0D, 51.0D, 153.0D, 153.0D, 51.0D, 51.0D, 102.0D, 51.0D, 77.0D, 128.0D, 128.0D, 77.0D, 102.0D, 204.0D, 102.0D, 77.0D, 179.0D, 128.0D, 77.0D, 128.0D, 153.0D, 77.0D, 102.0D, 230.0D, 77.0D, 77.0D, 230.0D, 128.0D, 77.0D, 230.0D, 153.0D, 77.0D, 230.0D, 179.0D, 77.0D, 230.0D, 230.0D, 77.0D, 179.0D, 179.0D, 77.0D, 77.0D, 128.0D, 77.0D, 102.0D, 153.0D, 153.0D, 102.0D, 128.0D, 230.0D, 128.0D, 102.0D, 204.0D, 153.0D, 102.0D, 153.0D, 179.0D, 102.0D, 128.0D, 255.0D, 102.0D, 102.0D, 255.0D, 153.0D, 102.0D, 255.0D, 179.0D, 102.0D, 255.0D, 204.0D, 102.0D, 255.0D, 255.0D, 77.0D, 204.0D, 204.0D, 102.0D, 102.0D, 153.0D, 102.0D, 128.0D, 179.0D, 179.0D, 128.0D, 153.0D, 255.0D, 153.0D, 128.0D, 230.0D, 179.0D, 128.0D, 179.0D, 204.0D, 128.0D, 153.0D, 255.0D, 128.0D, 128.0D, 255.0D, 153.0D, 128.0D, 255.0D, 204.0D, 128.0D, 255.0D, 230.0D, 102.0D, 255.0D, 255.0D, 102.0D, 230.0D, 230.0D, 128.0D, 128.0D, 179.0D, 128.0D, 153.0D, 204.0D, 204.0D, 153.0D, 179.0D, 255.0D, 179.0D, 153.0D, 255.0D, 204.0D, 153.0D, 204.0D, 230.0D, 153.0D, 179.0D, 255.0D, 153.0D, 153.0D, 255.0D, 179.0D, 128.0D, 255.0D, 204.0D, 153.0D, 255.0D, 230.0D, 128.0D, 255.0D, 255.0D, 128.0D, 230.0D, 230.0D, 153.0D, 153.0D, 204.0D, 153.0D, 179.0D, 230.0D, 230.0D, 179.0D, 204.0D, 255.0D, 204.0D, 179.0D, 255.0D, 230.0D, 179.0D, 230.0D, 230.0D, 179.0D, 204.0D, 255.0D, 179.0D, 179.0D, 255.0D, 179.0D, 153.0D, 255.0D, 230.0D, 179.0D, 255.0D, 230.0D, 153.0D, 255.0D, 255.0D, 153.0D, 230.0D, 230.0D, 179.0D, 179.0D, 230.0D, 179.0D, 204.0D, 255.0D, 255.0D, 204.0D, 230.0D, 255.0D, 230.0D, 204.0D, 255.0D, 255.0D, 204.0D, 255.0D, 255.0D, 204.0D, 230.0D, 255.0D, 204.0D, 204.0D, 255.0D, 204.0D, 179.0D, 255.0D, 230.0D, 204.0D, 255.0D, 255.0D, 179.0D, 255.0D, 255.0D, 204.0D, 230.0D, 230.0D, 204.0D, 204.0D, 255.0D, 204.0D };
/*     */ 
/*     */     public ColorPickerGrid(Color arg2)
/*     */     {
/* 531 */       getStyleClass().add("color-picker-grid");
/* 532 */       setId("ColorCustomizerColorGrid");
/* 533 */       int i = 0; int j = 0;
/* 534 */       setFocusTraversable(true);
/* 535 */       this.squares = FXCollections.observableArrayList();
/* 536 */       int k = this.rawValues.length / 3;
/* 537 */       Color[] arrayOfColor = new Color[k];
/*     */       ColorPalette.ColorSquare localColorSquare;
/* 538 */       for (int m = 0; m < k; m++) {
/* 539 */         arrayOfColor[m] = new Color(this.rawValues[(m * 3)] / 255.0D, this.rawValues[(m * 3 + 1)] / 255.0D, this.rawValues[(m * 3 + 2)] / 255.0D, 1.0D);
/*     */ 
/* 542 */         localColorSquare = new ColorPalette.ColorSquare(ColorPalette.this, arrayOfColor[m]);
/* 543 */         this.squares.add(localColorSquare);
/*     */       }
/*     */ 
/* 546 */       for (Iterator localIterator = this.squares.iterator(); localIterator.hasNext(); ) { localColorSquare = (ColorPalette.ColorSquare)localIterator.next();
/* 547 */         add(localColorSquare, i, j);
/* 548 */         i++;
/* 549 */         if (i == 12) {
/* 550 */           i = 0;
/* 551 */           j++;
/*     */         }
/*     */       }
/* 554 */       setOnMouseDragged(new EventHandler()
/*     */       {
/*     */         public void handle(MouseEvent paramAnonymousMouseEvent) {
/* 557 */           if (!ColorPalette.this.dragDetected) {
/* 558 */             ColorPalette.this.dragDetected = true;
/* 559 */             ColorPalette.this.mouseDragColor = ((Color)ColorPalette.this.colorPicker.getValue());
/*     */           }
/* 561 */           int i = (int)(paramAnonymousMouseEvent.getX() / 16.0D);
/* 562 */           int j = (int)(paramAnonymousMouseEvent.getY() / 16.0D);
/* 563 */           int k = i + j * 12;
/* 564 */           if (k < 120) {
/* 565 */             ColorPalette.this.colorPicker.setValue((Color)((ColorPalette.ColorSquare)ColorPalette.ColorPickerGrid.this.squares.get(k)).rectangle.getFill());
/* 566 */             ColorPalette.this.updateSelection((Color)ColorPalette.this.colorPicker.getValue());
/*     */           }
/*     */         }
/*     */       });
/* 570 */       addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler()
/*     */       {
/*     */         public void handle(MouseEvent paramAnonymousMouseEvent) {
/* 573 */           if (ColorPalette.this.colorPickerGrid.getBoundsInLocal().contains(paramAnonymousMouseEvent.getX(), paramAnonymousMouseEvent.getY())) {
/* 574 */             ColorPalette.this.updateSelection((Color)ColorPalette.this.colorPicker.getValue());
/* 575 */             ColorPalette.this.colorPicker.fireEvent(new ActionEvent());
/* 576 */             ColorPalette.this.colorPicker.hide();
/*     */           }
/* 579 */           else if (ColorPalette.this.mouseDragColor != null) {
/* 580 */             ColorPalette.this.colorPicker.setValue(ColorPalette.this.mouseDragColor);
/* 581 */             ColorPalette.this.updateSelection(ColorPalette.this.mouseDragColor);
/*     */           }
/*     */ 
/* 584 */           ColorPalette.this.dragDetected = false;
/*     */         }
/*     */       });
/*     */     }
/*     */ 
/*     */     public List<ColorPalette.ColorSquare> getSquares() {
/* 590 */       return this.squares;
/*     */     }
/*     */ 
/*     */     protected double computePrefWidth(double paramDouble)
/*     */     {
/* 717 */       return 192.0D;
/*     */     }
/*     */ 
/*     */     protected double computePrefHeight(double paramDouble) {
/* 721 */       return 160.0D;
/*     */     }
/*     */   }
/*     */ 
/*     */   class ColorSquare extends StackPane
/*     */   {
/*     */     Rectangle rectangle;
/* 392 */     boolean isCustom = false;
/* 393 */     boolean isEmpty = false;
/*     */     private ReadOnlyBooleanWrapper selected;
/* 501 */     private final long SELECTED_PSEUDOCLASS_STATE = StyleManager.getInstance().getPseudoclassMask("selected");
/*     */ 
/*     */     public ColorSquare(Color arg2)
/*     */     {
/* 395 */       this(localColor, false);
/*     */     }
/*     */ 
/*     */     public ColorSquare(Color paramBoolean, boolean arg3) {
/* 399 */       getStyleClass().add("color-square");
/* 400 */       setFocusTraversable(true);
/*     */       boolean bool;
/* 401 */       this.isCustom = bool;
/* 402 */       this.rectangle = new Rectangle(15.0D, 15.0D);
/* 403 */       setFocusTraversable(true);
/* 404 */       if (paramBoolean == null) {
/* 405 */         this.rectangle.setFill(Color.WHITE);
/* 406 */         this.isEmpty = true;
/*     */       }
/*     */       else {
/* 409 */         this.rectangle.setFill(paramBoolean);
/*     */       }
/*     */ 
/* 412 */       this.rectangle.setSmooth(false);
/*     */ 
/* 415 */       this.rectangle.setStrokeType(StrokeType.INSIDE);
/* 416 */       String str = ColorPickerSkin.colorValueToWeb(paramBoolean);
/* 417 */       Tooltip.install(this, new Tooltip(str == null ? "" : str));
/*     */ 
/* 419 */       this.rectangle.getStyleClass().add("color-rect");
/*     */ 
/* 421 */       addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler() {
/*     */         public void handle(MouseEvent paramAnonymousMouseEvent) {
/* 423 */           if ((!ColorPalette.this.dragDetected) && (paramAnonymousMouseEvent.getButton() == MouseButton.PRIMARY) && (paramAnonymousMouseEvent.getClickCount() == 1)) {
/* 424 */             if (!ColorPalette.ColorSquare.this.isEmpty) {
/* 425 */               Color localColor = (Color)ColorPalette.ColorSquare.this.rectangle.getFill();
/* 426 */               ColorPalette.this.colorPicker.setValue(localColor);
/* 427 */               ColorPalette.this.colorPicker.fireEvent(new ActionEvent());
/* 428 */               ColorPalette.this.updateSelection(localColor);
/* 429 */               paramAnonymousMouseEvent.consume();
/*     */             }
/* 431 */             ColorPalette.this.colorPicker.hide();
/* 432 */           } else if ((paramAnonymousMouseEvent.getButton() == MouseButton.SECONDARY) || (paramAnonymousMouseEvent.getButton() == MouseButton.MIDDLE))
/*     */           {
/* 434 */             if ((ColorPalette.ColorSquare.this.isCustom) && (ColorPalette.this.contextMenu != null))
/* 435 */               if (!ColorPalette.this.contextMenu.isShowing()) {
/* 436 */                 ColorPalette.this.contextMenu.show(ColorPalette.ColorSquare.this, Side.RIGHT, 0.0D, 0.0D);
/* 437 */                 Utils.addMnemonics(ColorPalette.this.contextMenu, ColorPalette.ColorSquare.this.getScene());
/*     */               }
/*     */               else {
/* 440 */                 ColorPalette.this.contextMenu.hide();
/* 441 */                 Utils.removeMnemonics(ColorPalette.this.contextMenu, ColorPalette.ColorSquare.this.getScene());
/*     */               }
/*     */           }
/*     */         }
/*     */       });
/* 447 */       focusedProperty().addListener(new ChangeListener()
/*     */       {
/*     */         public void changed(ObservableValue<? extends Boolean> paramAnonymousObservableValue, Boolean paramAnonymousBoolean1, Boolean paramAnonymousBoolean2)
/*     */         {
/*     */         }
/*     */       });
/* 452 */       addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler() {
/*     */         public void handle(MouseEvent paramAnonymousMouseEvent) {
/* 454 */           ColorPalette.this.focusedSquare = ColorPalette.ColorSquare.this;
/* 455 */           ColorPalette.this.focusedSquare.requestFocus();
/*     */         }
/*     */       });
/* 458 */       getChildren().add(this.rectangle);
/*     */     }
/*     */ 
/*     */     public void selectColor(KeyEvent paramKeyEvent) {
/* 462 */       if (this.rectangle.getFill() != null) {
/* 463 */         if ((this.rectangle.getFill() instanceof Color)) {
/* 464 */           ColorPalette.this.colorPicker.setValue((Color)this.rectangle.getFill());
/* 465 */           ColorPalette.this.colorPicker.fireEvent(new ActionEvent());
/*     */         }
/* 467 */         paramKeyEvent.consume();
/*     */       }
/* 469 */       ColorPalette.this.colorPicker.hide();
/*     */     }
/*     */ 
/*     */     protected final void setSelected(boolean paramBoolean)
/*     */     {
/* 474 */       selectedPropertyImpl().set(paramBoolean);
/*     */     }
/* 476 */     public final boolean isSelected() { return this.selected == null ? false : this.selected.get(); }
/*     */ 
/*     */     public ReadOnlyBooleanProperty selectedProperty() {
/* 479 */       return selectedPropertyImpl().getReadOnlyProperty();
/*     */     }
/*     */     private ReadOnlyBooleanWrapper selectedPropertyImpl() {
/* 482 */       if (this.selected == null) {
/* 483 */         this.selected = new ReadOnlyBooleanWrapper() {
/*     */           protected void invalidated() {
/* 485 */             ColorPalette.ColorSquare.this.impl_pseudoClassStateChanged("selected");
/*     */           }
/*     */ 
/*     */           public Object getBean()
/*     */           {
/* 490 */             return ColorPalette.ColorSquare.this;
/*     */           }
/*     */ 
/*     */           public String getName()
/*     */           {
/* 495 */             return "selected";
/*     */           }
/*     */         };
/*     */       }
/* 499 */       return this.selected;
/*     */     }
/*     */ 
/*     */     public long impl_getPseudoClassState()
/*     */     {
/* 505 */       return super.impl_getPseudoClassState() | (isSelected() ? this.SELECTED_PSEUDOCLASS_STATE : 0L);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.ColorPalette
 * JD-Core Version:    0.6.2
 */