/*     */ package com.sun.javafx.scene.control.skin;
/*     */ 
/*     */ import com.sun.javafx.scene.control.FocusableTextField;
/*     */ import com.sun.javafx.scene.control.WeakListChangeListener;
/*     */ import com.sun.javafx.scene.control.behavior.ComboBoxListViewBehavior;
/*     */ import java.util.List;
/*     */ import javafx.application.Platform;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.Observable;
/*     */ import javafx.beans.property.ReadOnlyBooleanProperty;
/*     */ import javafx.beans.property.ReadOnlyIntegerProperty;
/*     */ import javafx.beans.property.ReadOnlyObjectProperty;
/*     */ import javafx.beans.property.StringProperty;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ import javafx.collections.ListChangeListener;
/*     */ import javafx.collections.ListChangeListener.Change;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.collections.ObservableMap;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.event.EventTarget;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.Parent;
/*     */ import javafx.scene.Scene;
/*     */ import javafx.scene.control.ComboBox;
/*     */ import javafx.scene.control.ComboBoxBase;
/*     */ import javafx.scene.control.ListCell;
/*     */ import javafx.scene.control.ListView;
/*     */ import javafx.scene.control.MultipleSelectionModel;
/*     */ import javafx.scene.control.PopupControl;
/*     */ import javafx.scene.control.SelectionMode;
/*     */ import javafx.scene.control.SelectionModel;
/*     */ import javafx.scene.control.SingleSelectionModel;
/*     */ import javafx.scene.control.TextField;
/*     */ import javafx.scene.input.InputEvent;
/*     */ import javafx.scene.input.KeyCode;
/*     */ import javafx.scene.input.KeyEvent;
/*     */ import javafx.scene.input.MouseEvent;
/*     */ import javafx.util.Callback;
/*     */ import javafx.util.StringConverter;
/*     */ 
/*     */ public class ComboBoxListViewSkin<T> extends ComboBoxPopupControl<T>
/*     */ {
/*     */   private static final String COMBO_BOX_ROWS_TO_MEASURE_WIDTH_KEY = "comboBoxRowsToMeasureWidth";
/*     */   private final ComboBox<T> comboBox;
/*     */   private ListCell<T> buttonCell;
/*     */   private Callback<ListView<T>, ListCell<T>> cellFactory;
/*     */   private TextField textField;
/*     */   private final ListView<T> listView;
/*     */   private ObservableList<T> listViewItems;
/*  79 */   private boolean listSelectionLock = false;
/*  80 */   private boolean listViewSelectionDirty = false;
/*     */   private boolean itemCountDirty;
/*  91 */   private final ListChangeListener listViewItemsListener = new ListChangeListener() {
/*     */     public void onChanged(ListChangeListener.Change paramAnonymousChange) {
/*  93 */       ComboBoxListViewSkin.this.itemCountDirty = true;
/*  94 */       ComboBoxListViewSkin.this.requestLayout();
/*     */     }
/*  91 */   };
/*     */ 
/*  98 */   private final WeakListChangeListener weakListViewItemsListener = new WeakListChangeListener(this.listViewItemsListener);
/*     */ 
/*     */   public ComboBoxListViewSkin(final ComboBox<T> paramComboBox)
/*     */   {
/* 110 */     super(paramComboBox, new ComboBoxListViewBehavior(paramComboBox));
/* 111 */     this.comboBox = paramComboBox;
/* 112 */     this.listView = createListView();
/* 113 */     this.textField = getEditableInputNode();
/*     */ 
/* 116 */     this.listView.setManaged(false);
/* 117 */     getChildren().add(this.listView);
/*     */ 
/* 120 */     updateListViewItems();
/* 121 */     updateCellFactory();
/*     */ 
/* 123 */     updateButtonCell();
/*     */ 
/* 126 */     paramComboBox.focusedProperty().addListener(new ChangeListener() {
/*     */       public void changed(ObservableValue<? extends Boolean> paramAnonymousObservableValue, Boolean paramAnonymousBoolean1, Boolean paramAnonymousBoolean2) {
/* 128 */         ComboBoxListViewSkin.this.updateFakeFocus(paramComboBox.isFocused());
/*     */       }
/*     */     });
/* 132 */     paramComboBox.addEventFilter(InputEvent.ANY, new EventHandler() {
/*     */       public void handle(InputEvent paramAnonymousInputEvent) {
/* 134 */         if (ComboBoxListViewSkin.this.textField == null) return;
/*     */ 
/* 138 */         if ((paramAnonymousInputEvent instanceof KeyEvent)) {
/* 139 */           KeyEvent localKeyEvent = (KeyEvent)paramAnonymousInputEvent;
/*     */ 
/* 141 */           if (localKeyEvent.getCode() == KeyCode.ENTER) {
/* 142 */             ComboBoxListViewSkin.this.setTextFromTextFieldIntoComboBoxValue();
/* 143 */             paramAnonymousInputEvent.consume();
/* 144 */             return;
/* 145 */           }if ((localKeyEvent.getCode() == KeyCode.F4) && (localKeyEvent.getEventType() == KeyEvent.KEY_RELEASED)) {
/* 146 */             if (paramComboBox.isShowing()) paramComboBox.hide(); else
/* 147 */               paramComboBox.show();
/* 148 */             paramAnonymousInputEvent.consume();
/* 149 */             return;
/* 150 */           }if ((localKeyEvent.getCode() == KeyCode.F10) || (localKeyEvent.getCode() == KeyCode.ESCAPE))
/*     */           {
/* 156 */             paramAnonymousInputEvent.consume();
/* 157 */             return;
/*     */           }
/*     */         }
/*     */ 
/* 161 */         ComboBoxListViewSkin.this.textField.fireEvent(paramAnonymousInputEvent);
/*     */       }
/*     */     });
/* 166 */     updateValue();
/*     */ 
/* 168 */     registerChangeListener(paramComboBox.itemsProperty(), "ITEMS");
/* 169 */     registerChangeListener(paramComboBox.promptTextProperty(), "PROMPT_TEXT");
/* 170 */     registerChangeListener(paramComboBox.cellFactoryProperty(), "CELL_FACTORY");
/* 171 */     registerChangeListener(paramComboBox.visibleRowCountProperty(), "VISIBLE_ROW_COUNT");
/* 172 */     registerChangeListener(paramComboBox.converterProperty(), "CONVERTER");
/* 173 */     registerChangeListener(paramComboBox.editorProperty(), "EDITOR");
/* 174 */     registerChangeListener(paramComboBox.buttonCellProperty(), "BUTTON_CELL");
/* 175 */     registerChangeListener(paramComboBox.valueProperty(), "VALUE");
/*     */   }
/*     */ 
/*     */   protected void handleControlPropertyChanged(String paramString)
/*     */   {
/* 189 */     if (paramString == "SHOWING") {
/* 190 */       if (((ComboBoxBase)getSkinnable()).isShowing())
/* 191 */         this.listView.setManaged(true);
/*     */       else {
/* 193 */         this.listView.setManaged(false);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 198 */     super.handleControlPropertyChanged(paramString);
/*     */ 
/* 200 */     if (paramString == "ITEMS") {
/* 201 */       updateListViewItems();
/* 202 */     } else if ("PROMPT_TEXT".equals(paramString)) {
/* 203 */       updateDisplayNode();
/* 204 */     } else if ("CELL_FACTORY".equals(paramString)) {
/* 205 */       updateCellFactory();
/* 206 */     } else if ("VISIBLE_ROW_COUNT".equals(paramString)) {
/* 207 */       if (this.listView == null) return;
/* 208 */       this.listView.setPrefHeight(getListViewPrefHeight());
/* 209 */     } else if ("CONVERTER".equals(paramString)) {
/* 210 */       updateListViewItems();
/* 211 */     } else if ("EDITOR".equals(paramString)) {
/* 212 */       getEditableInputNode();
/* 213 */     } else if ("BUTTON_CELL".equals(paramString)) {
/* 214 */       updateButtonCell();
/* 215 */     } else if ("VALUE".equals(paramString)) {
/* 216 */       updateValue();
/*     */     }
/*     */   }
/*     */ 
/*     */   public Node getDisplayNode()
/*     */   {
/*     */     Object localObject;
/* 223 */     if (this.comboBox.isEditable())
/* 224 */       localObject = this.textField;
/*     */     else {
/* 226 */       localObject = this.buttonCell;
/*     */     }
/*     */ 
/* 229 */     updateDisplayNode();
/*     */ 
/* 231 */     return localObject;
/*     */   }
/*     */ 
/*     */   public void updateListViewItems()
/*     */   {
/* 238 */     if (this.listViewItems != null) {
/* 239 */       this.listViewItems.removeListener(this.weakListViewItemsListener);
/*     */     }
/*     */ 
/* 242 */     this.listViewItems = this.comboBox.getItems();
/* 243 */     this.listView.setItems(null);
/* 244 */     this.listView.setItems(this.listViewItems);
/*     */ 
/* 246 */     if (this.listViewItems != null) {
/* 247 */       this.listViewItems.addListener(this.weakListViewItemsListener);
/*     */     }
/*     */ 
/* 250 */     this.itemCountDirty = true;
/* 251 */     requestLayout();
/*     */   }
/*     */ 
/*     */   public Node getPopupContent() {
/* 255 */     return this.listView;
/*     */   }
/*     */ 
/*     */   protected double computePrefWidth(double paramDouble) {
/* 259 */     double d = this.listView.prefWidth(paramDouble);
/*     */ 
/* 261 */     reconfigurePopup();
/*     */ 
/* 263 */     return d;
/*     */   }
/*     */ 
/*     */   protected double computeMinWidth(double paramDouble) {
/* 267 */     return 50.0D;
/*     */   }
/*     */ 
/*     */   protected void layoutChildren() {
/* 271 */     if (this.listViewSelectionDirty) {
/*     */       try {
/* 273 */         this.listSelectionLock = true;
/* 274 */         Object localObject1 = this.comboBox.getSelectionModel().getSelectedItem();
/* 275 */         this.listView.getSelectionModel().clearSelection();
/* 276 */         this.listView.getSelectionModel().select(localObject1);
/*     */       } finally {
/* 278 */         this.listSelectionLock = false;
/* 279 */         this.listViewSelectionDirty = false;
/*     */       }
/*     */     }
/*     */ 
/* 283 */     super.layoutChildren();
/*     */   }
/*     */ 
/*     */   private void updateValue()
/*     */   {
/* 295 */     Object localObject1 = this.comboBox.getValue();
/*     */ 
/* 297 */     MultipleSelectionModel localMultipleSelectionModel = this.listView.getSelectionModel();
/*     */ 
/* 299 */     if (localObject1 == null) {
/* 300 */       localMultipleSelectionModel.clearSelection();
/*     */     }
/*     */     else
/*     */     {
/* 305 */       int i = getIndexOfComboBoxValueInItemsList();
/* 306 */       if (i == -1) {
/* 307 */         this.listSelectionLock = true;
/* 308 */         localMultipleSelectionModel.clearSelection();
/* 309 */         this.listSelectionLock = false;
/*     */       } else {
/* 311 */         int j = this.comboBox.getSelectionModel().getSelectedIndex();
/* 312 */         if ((j >= 0) && (j < this.comboBox.getItems().size())) {
/* 313 */           Object localObject2 = this.comboBox.getItems().get(j);
/* 314 */           if ((localObject2 != null) && (localObject2.equals(localObject1)))
/* 315 */             localMultipleSelectionModel.select(j);
/*     */           else
/* 317 */             localMultipleSelectionModel.select(localObject1);
/*     */         }
/*     */         else
/*     */         {
/* 321 */           int k = this.listView.getItems().indexOf(localObject1);
/* 322 */           if (k == -1)
/*     */           {
/* 325 */             updateDisplayNode();
/*     */           }
/* 327 */           else localMultipleSelectionModel.select(k);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private TextField getEditableInputNode()
/*     */   {
/* 335 */     if (this.textField != null) return this.textField;
/*     */ 
/* 337 */     this.textField = this.comboBox.getEditor();
/* 338 */     this.textField.setFocusTraversable(true);
/* 339 */     this.textField.promptTextProperty().bind(this.comboBox.promptTextProperty());
/*     */ 
/* 346 */     this.textField.focusedProperty().addListener(new ChangeListener() {
/*     */       public void changed(ObservableValue<? extends Boolean> paramAnonymousObservableValue, Boolean paramAnonymousBoolean1, Boolean paramAnonymousBoolean2) {
/* 348 */         if (paramAnonymousBoolean2.booleanValue())
/*     */         {
/* 354 */           Platform.runLater(new Runnable() {
/*     */             public void run() {
/* 356 */               ComboBoxListViewSkin.this.comboBox.requestFocus();
/*     */             }
/*     */           });
/*     */         }
/*     */ 
/* 361 */         ComboBoxListViewSkin.this.updateFakeFocus(paramAnonymousBoolean2.booleanValue());
/*     */ 
/* 364 */         if (!paramAnonymousBoolean2.booleanValue())
/* 365 */           ComboBoxListViewSkin.this.setTextFromTextFieldIntoComboBoxValue();
/*     */       }
/*     */     });
/* 370 */     return this.textField;
/*     */   }
/*     */ 
/*     */   private void updateFakeFocus(boolean paramBoolean) {
/* 374 */     if (this.textField == null) return;
/* 375 */     if (!(this.textField instanceof FocusableTextField)) return;
/* 376 */     ((FocusableTextField)this.textField).setFakeFocus(paramBoolean);
/*     */   }
/*     */ 
/*     */   private void updateDisplayNode() {
/* 380 */     StringConverter localStringConverter = this.comboBox.getConverter();
/* 381 */     if (localStringConverter == null) return;
/*     */ 
/* 383 */     Object localObject = this.comboBox.getValue();
/* 384 */     if (this.comboBox.isEditable()) {
/* 385 */       String str = localStringConverter.toString(localObject);
/* 386 */       if ((localObject == null) || (str == null))
/* 387 */         this.textField.setText("");
/* 388 */       else if (!str.equals(this.textField.getText()))
/* 389 */         this.textField.setText(str);
/*     */     }
/*     */     else {
/* 392 */       int i = getIndexOfComboBoxValueInItemsList();
/* 393 */       if (i > -1) {
/* 394 */         this.buttonCell.updateListView(this.listView);
/* 395 */         this.buttonCell.updateIndex(i);
/*     */       }
/*     */       else
/*     */       {
/* 399 */         updateDisplayText(this.buttonCell, localObject, false);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void updateDisplayText(ListCell<T> paramListCell, T paramT, boolean paramBoolean) {
/* 405 */     if (paramBoolean) {
/* 406 */       if (this.buttonCell == null) return;
/* 407 */       paramListCell.setGraphic(null);
/* 408 */       paramListCell.setText(this.comboBox.getPromptText() == null ? null : this.comboBox.getPromptText());
/*     */     }
/*     */     else
/*     */     {
/*     */       Object localObject1;
/*     */       Object localObject2;
/* 409 */       if ((paramT instanceof Node)) {
/* 410 */         localObject1 = this.buttonCell.getGraphic();
/* 411 */         localObject2 = (Node)paramT;
/* 412 */         if ((localObject1 == null) || (!localObject1.equals(localObject2))) {
/* 413 */           paramListCell.setText(null);
/* 414 */           paramListCell.setGraphic((Node)localObject2);
/*     */         }
/*     */       }
/*     */       else {
/* 418 */         localObject1 = this.comboBox.getConverter();
/* 419 */         localObject2 = localObject1 == null ? paramT.toString() : paramT == null ? this.comboBox.getPromptText() : ((StringConverter)localObject1).toString(paramT);
/* 420 */         paramListCell.setText((String)localObject2);
/* 421 */         paramListCell.setGraphic(null);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/* 426 */   private void setTextFromTextFieldIntoComboBoxValue() { if (!this.comboBox.isEditable()) return;
/*     */ 
/* 428 */     StringConverter localStringConverter = this.comboBox.getConverter();
/* 429 */     if (localStringConverter == null) return;
/*     */ 
/* 431 */     Object localObject1 = this.comboBox.getValue();
/* 432 */     Object localObject2 = localStringConverter.fromString(this.textField.getText());
/*     */ 
/* 434 */     if (((localObject2 == null) && (localObject1 == null)) || ((localObject2 != null) && (localObject2.equals(localObject1))))
/*     */     {
/* 436 */       return;
/*     */     }
/*     */ 
/* 439 */     this.comboBox.setValue(localObject2); }
/*     */ 
/*     */   private int getIndexOfComboBoxValueInItemsList()
/*     */   {
/* 443 */     Object localObject = this.comboBox.getValue();
/* 444 */     int i = this.comboBox.getItems().indexOf(localObject);
/* 445 */     return i;
/*     */   }
/*     */ 
/*     */   private void updateButtonCell() {
/* 449 */     this.buttonCell = (this.comboBox.getButtonCell() != null ? this.comboBox.getButtonCell() : (ListCell)getDefaultCellFactory().call(this.listView));
/*     */ 
/* 451 */     this.buttonCell.setMouseTransparent(true);
/*     */   }
/*     */ 
/*     */   private void updateCellFactory() {
/* 455 */     Callback localCallback = this.comboBox.getCellFactory();
/* 456 */     this.cellFactory = (localCallback != null ? localCallback : getDefaultCellFactory());
/* 457 */     this.listView.setCellFactory(this.cellFactory);
/*     */   }
/*     */ 
/*     */   private Callback<ListView<T>, ListCell<T>> getDefaultCellFactory() {
/* 461 */     return new Callback() {
/*     */       public ListCell<T> call(ListView<T> paramAnonymousListView) {
/* 463 */         return new ListCell() {
/*     */           public void updateItem(T paramAnonymous2T, boolean paramAnonymous2Boolean) {
/* 465 */             super.updateItem(paramAnonymous2T, paramAnonymous2Boolean);
/* 466 */             ComboBoxListViewSkin.this.updateDisplayText(this, paramAnonymous2T, paramAnonymous2Boolean);
/*     */           }
/*     */         };
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   private ListView<T> createListView() {
/* 474 */     final ListView local6 = new ListView() {
/* 475 */       private boolean isFirstSizeCalculation = true;
/*     */ 
/*     */       protected double computeMinHeight(double paramAnonymousDouble) {
/* 478 */         return 30.0D;
/*     */       }
/*     */ 
/*     */       protected double computePrefWidth(double paramAnonymousDouble) {
/* 482 */         doCSSCheck();
/*     */         double d;
/* 485 */         if ((getSkin() instanceof ListViewSkin)) {
/* 486 */           ListViewSkin localListViewSkin = (ListViewSkin)getSkin();
/* 487 */           if (ComboBoxListViewSkin.this.itemCountDirty) {
/* 488 */             localListViewSkin.updateCellCount();
/* 489 */             ComboBoxListViewSkin.this.itemCountDirty = false;
/*     */           }
/*     */ 
/* 492 */           int i = -1;
/* 493 */           if (ComboBoxListViewSkin.this.comboBox.getProperties().containsKey("comboBoxRowsToMeasureWidth")) {
/* 494 */             i = ((Integer)ComboBoxListViewSkin.this.comboBox.getProperties().get("comboBoxRowsToMeasureWidth")).intValue();
/*     */           }
/*     */ 
/* 497 */           d = Math.max(ComboBoxListViewSkin.this.comboBox.getWidth(), localListViewSkin.getMaxCellWidth(i) + 30.0D);
/*     */         } else {
/* 499 */           d = Math.max(100.0D, ComboBoxListViewSkin.this.comboBox.getWidth());
/*     */         }
/*     */ 
/* 502 */         return Math.max(50.0D, d);
/*     */       }
/*     */ 
/*     */       protected double computePrefHeight(double paramAnonymousDouble) {
/* 506 */         doCSSCheck();
/*     */ 
/* 508 */         return ComboBoxListViewSkin.this.getListViewPrefHeight();
/*     */       }
/*     */ 
/*     */       private void doCSSCheck() {
/* 512 */         Parent localParent = ComboBoxListViewSkin.this.getPopup().getScene().getRoot();
/* 513 */         if (((this.isFirstSizeCalculation) || (getSkin() == null)) && (localParent.getScene() != null))
/*     */         {
/* 518 */           localParent.impl_processCSS(true);
/* 519 */           this.isFirstSizeCalculation = false;
/*     */         }
/*     */       }
/*     */     };
/* 524 */     local6.setId("list-view");
/* 525 */     local6.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
/*     */ 
/* 527 */     local6.getSelectionModel().selectedIndexProperty().addListener(new InvalidationListener() {
/*     */       public void invalidated(Observable paramAnonymousObservable) {
/* 529 */         if (ComboBoxListViewSkin.this.listSelectionLock) return;
/* 530 */         int i = local6.getSelectionModel().getSelectedIndex();
/* 531 */         ComboBoxListViewSkin.this.comboBox.getSelectionModel().select(i);
/* 532 */         ComboBoxListViewSkin.this.updateDisplayNode();
/*     */       }
/*     */     });
/* 536 */     this.comboBox.getSelectionModel().selectedItemProperty().addListener(new InvalidationListener() {
/*     */       public void invalidated(Observable paramAnonymousObservable) {
/* 538 */         ComboBoxListViewSkin.this.listViewSelectionDirty = true;
/*     */       }
/*     */     });
/* 542 */     local6.addEventFilter(MouseEvent.MOUSE_RELEASED, new EventHandler()
/*     */     {
/*     */       public void handle(MouseEvent paramAnonymousMouseEvent)
/*     */       {
/* 547 */         EventTarget localEventTarget = paramAnonymousMouseEvent.getTarget();
/* 548 */         if ((localEventTarget instanceof Parent)) {
/* 549 */           ObservableList localObservableList = ((Parent)localEventTarget).getStyleClass();
/* 550 */           if ((localObservableList.contains("thumb")) || (localObservableList.contains("track")) || (localObservableList.contains("decrement-arrow")) || (localObservableList.contains("increment-arrow")))
/*     */           {
/* 554 */             return;
/*     */           }
/*     */         }
/*     */ 
/* 558 */         ComboBoxListViewSkin.this.comboBox.hide();
/*     */       }
/*     */     });
/* 562 */     local6.setOnKeyPressed(new EventHandler()
/*     */     {
/*     */       public void handle(KeyEvent paramAnonymousKeyEvent) {
/* 565 */         if ((paramAnonymousKeyEvent.getCode() == KeyCode.ENTER) || (paramAnonymousKeyEvent.getCode() == KeyCode.SPACE) || (paramAnonymousKeyEvent.getCode() == KeyCode.ESCAPE))
/*     */         {
/* 568 */           ComboBoxListViewSkin.this.comboBox.hide();
/*     */         }
/*     */       }
/*     */     });
/* 573 */     return local6;
/*     */   }
/*     */ 
/*     */   private double getListViewPrefHeight()
/*     */   {
/*     */     double d1;
/* 578 */     if ((this.listView.getSkin() instanceof VirtualContainerBase)) {
/* 579 */       int i = this.comboBox.getVisibleRowCount();
/* 580 */       VirtualContainerBase localVirtualContainerBase = (VirtualContainerBase)this.listView.getSkin();
/* 581 */       d1 = localVirtualContainerBase.getVirtualFlowPreferredHeight(i);
/*     */     } else {
/* 583 */       double d2 = this.comboBox.getItems().size() * 25;
/* 584 */       d1 = Math.min(d2, 200.0D);
/*     */     }
/*     */ 
/* 587 */     return d1;
/*     */   }
/*     */ 
/*     */   public ListView<T> getListView()
/*     */   {
/* 599 */     return this.listView;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.ComboBoxListViewSkin
 * JD-Core Version:    0.6.2
 */