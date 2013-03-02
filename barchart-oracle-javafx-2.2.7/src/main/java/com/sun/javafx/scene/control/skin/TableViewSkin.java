/*     */ package com.sun.javafx.scene.control.skin;
/*     */ 
/*     */ import com.sun.javafx.scene.control.WeakListChangeListener;
/*     */ import com.sun.javafx.scene.control.behavior.TableViewBehavior;
/*     */ import com.sun.javafx.scene.control.skin.resources.ControlResources;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.Observable;
/*     */ import javafx.beans.WeakInvalidationListener;
/*     */ import javafx.beans.property.BooleanProperty;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.ReadOnlyDoubleProperty;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ import javafx.beans.value.WeakChangeListener;
/*     */ import javafx.collections.FXCollections;
/*     */ import javafx.collections.ListChangeListener;
/*     */ import javafx.collections.ListChangeListener.Change;
/*     */ import javafx.collections.MapChangeListener;
/*     */ import javafx.collections.MapChangeListener.Change;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.collections.ObservableMap;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.geometry.Bounds;
/*     */ import javafx.geometry.Insets;
/*     */ import javafx.geometry.Pos;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.control.Label;
/*     */ import javafx.scene.control.SelectionModel;
/*     */ import javafx.scene.control.TableColumn;
/*     */ import javafx.scene.control.TablePosition;
/*     */ import javafx.scene.control.TableRow;
/*     */ import javafx.scene.control.TableView;
/*     */ import javafx.scene.control.TableView.TableViewFocusModel;
/*     */ import javafx.scene.control.TableView.TableViewSelectionModel;
/*     */ import javafx.scene.input.MouseEvent;
/*     */ import javafx.scene.layout.Region;
/*     */ import javafx.scene.layout.StackPane;
/*     */ import javafx.util.Callback;
/*     */ 
/*     */ public class TableViewSkin<T> extends VirtualContainerBase<TableView<T>, TableViewBehavior<T>, TableRow<T>>
/*     */ {
/* 212 */   private ListChangeListener rowCountListener = new ListChangeListener() {
/*     */     public void onChanged(ListChangeListener.Change paramAnonymousChange) {
/* 214 */       TableViewSkin.this.rowCountDirty = true;
/* 215 */       TableViewSkin.this.requestLayout();
/*     */     }
/* 212 */   };
/*     */ 
/* 219 */   private ListChangeListener<TableColumn<T, ?>> visibleLeafColumnsListener = new ListChangeListener()
/*     */   {
/*     */     public void onChanged(ListChangeListener.Change<? extends TableColumn<T, ?>> paramAnonymousChange) {
/* 222 */       TableViewSkin.this.updateVisibleColumnCount();
/* 223 */       while (paramAnonymousChange.next())
/* 224 */         TableViewSkin.this.updateVisibleLeafColumnWidthListeners(paramAnonymousChange.getAddedSubList(), paramAnonymousChange.getRemoved());
/*     */     }
/* 219 */   };
/*     */ 
/* 229 */   private InvalidationListener widthListener = new InvalidationListener()
/*     */   {
/*     */     public void invalidated(Observable paramAnonymousObservable)
/*     */     {
/* 235 */       TableViewSkin.this.flow.reconfigureCells();
/*     */     }
/* 229 */   };
/*     */ 
/* 239 */   private ChangeListener<ObservableList<T>> itemsChangeListener = new ChangeListener()
/*     */   {
/*     */     public void changed(ObservableValue<? extends ObservableList<T>> paramAnonymousObservableValue, ObservableList<T> paramAnonymousObservableList1, ObservableList<T> paramAnonymousObservableList2)
/*     */     {
/* 243 */       TableViewSkin.this.updateTableItems(paramAnonymousObservableList1, paramAnonymousObservableList2);
/*     */     }
/* 239 */   };
/*     */ 
/* 247 */   private WeakListChangeListener<T> weakRowCountListener = new WeakListChangeListener(this.rowCountListener);
/*     */ 
/* 249 */   private WeakListChangeListener<TableColumn<T, ?>> weakVisibleLeafColumnsListener = new WeakListChangeListener(this.visibleLeafColumnsListener);
/*     */ 
/* 251 */   private WeakInvalidationListener weakWidthListener = new WeakInvalidationListener(this.widthListener);
/*     */ 
/* 253 */   private WeakChangeListener<ObservableList<T>> weakItemsChangeListener = new WeakChangeListener(this.itemsChangeListener);
/*     */   private boolean rowCountDirty;
/* 265 */   private boolean contentWidthDirty = true;
/*     */   private double scrollX;
/*     */   private Region columnReorderLine;
/*     */   private Region columnReorderOverlay;
/*     */   private TableHeaderRow tableHeaderRow;
/*     */   private Callback<TableView<T>, ? extends TableRow<T>> rowFactory;
/*     */   private StackPane placeholderRegion;
/*     */   private Label placeholderLabel;
/* 303 */   private static final String EMPTY_TABLE_TEXT = ControlResources.getString("TableView.noContent");
/* 304 */   private static final String NO_COLUMNS_TEXT = ControlResources.getString("TableView.noColumns");
/*     */   private int visibleColCount;
/*     */   private static final double GOLDEN_RATIO_MULTIPLIER = 0.618033987D;
/*     */ 
/*     */   public TableViewSkin(final TableView paramTableView)
/*     */   {
/*  60 */     super(paramTableView, new TableViewBehavior(paramTableView));
/*     */ 
/*  63 */     this.flow.setPannable(false);
/*  64 */     this.flow.setFocusTraversable(((TableView)getSkinnable()).isFocusTraversable());
/*  65 */     this.flow.setCreateCell(new Callback() {
/*     */       public TableRow call(VirtualFlow paramAnonymousVirtualFlow) {
/*  67 */         return TableViewSkin.this.createCell();
/*     */       }
/*     */     });
/*  71 */     EventHandler local2 = new EventHandler()
/*     */     {
/*     */       public void handle(MouseEvent paramAnonymousMouseEvent)
/*     */       {
/*  76 */         if (paramTableView.getEditingCell() != null) {
/*  77 */           paramTableView.edit(-1, null);
/*     */         }
/*     */ 
/*  85 */         paramTableView.requestFocus();
/*     */       }
/*     */     };
/*  88 */     this.flow.getVbar().addEventFilter(MouseEvent.MOUSE_PRESSED, local2);
/*  89 */     this.flow.getHbar().addEventFilter(MouseEvent.MOUSE_PRESSED, local2);
/*     */ 
/*  91 */     this.columnReorderLine = new Region();
/*  92 */     this.columnReorderLine.getStyleClass().setAll(new String[] { "column-resize-line" });
/*  93 */     this.columnReorderLine.setManaged(false);
/*  94 */     this.columnReorderLine.setVisible(false);
/*     */ 
/*  96 */     this.columnReorderOverlay = new Region();
/*  97 */     this.columnReorderOverlay.getStyleClass().setAll(new String[] { "column-overlay" });
/*  98 */     this.columnReorderOverlay.setVisible(false);
/*  99 */     this.columnReorderOverlay.setManaged(false);
/*     */ 
/* 101 */     this.tableHeaderRow = new TableHeaderRow(paramTableView, this.flow);
/* 102 */     this.tableHeaderRow.setColumnReorderLine(this.columnReorderLine);
/* 103 */     this.tableHeaderRow.setTablePadding(getInsets());
/* 104 */     this.tableHeaderRow.setFocusTraversable(false);
/* 105 */     paddingProperty().addListener(new InvalidationListener() {
/*     */       public void invalidated(Observable paramAnonymousObservable) {
/* 107 */         TableViewSkin.this.tableHeaderRow.setTablePadding(TableViewSkin.this.getInsets());
/*     */       }
/*     */     });
/* 111 */     getChildren().addAll(new Node[] { this.tableHeaderRow, this.flow, this.columnReorderOverlay, this.columnReorderLine });
/*     */ 
/* 113 */     updateVisibleColumnCount();
/* 114 */     updateVisibleLeafColumnWidthListeners(((TableView)getSkinnable()).getVisibleLeafColumns(), FXCollections.emptyObservableList());
/*     */ 
/* 116 */     this.tableHeaderRow.reorderingProperty().addListener(new InvalidationListener() {
/*     */       public void invalidated(Observable paramAnonymousObservable) {
/* 118 */         TableViewSkin.this.requestLayout();
/*     */       }
/*     */     });
/* 122 */     ((TableView)getSkinnable()).getVisibleLeafColumns().addListener(this.weakVisibleLeafColumnsListener);
/*     */ 
/* 124 */     updateTableItems(null, ((TableView)getSkinnable()).getItems());
/* 125 */     ((TableView)getSkinnable()).itemsProperty().addListener(this.weakItemsChangeListener);
/*     */ 
/* 127 */     ((TableView)getSkinnable()).getProperties().addListener(new MapChangeListener() {
/*     */       public void onChanged(MapChangeListener.Change<? extends Object, ? extends Object> paramAnonymousChange) {
/* 129 */         if ((paramAnonymousChange.wasAdded()) && ("TableView.refresh".equals(paramAnonymousChange.getKey()))) {
/* 130 */           TableViewSkin.this.refreshView();
/* 131 */           paramTableView.getProperties().remove("TableView.refresh");
/*     */         }
/*     */       }
/*     */     });
/* 137 */     InvalidationListener local6 = new InvalidationListener() {
/*     */       public void invalidated(Observable paramAnonymousObservable) {
/* 139 */         TableViewSkin.this.contentWidthDirty = true;
/* 140 */         TableViewSkin.this.requestLayout();
/*     */       }
/*     */     };
/* 143 */     this.flow.widthProperty().addListener(local6);
/* 144 */     this.flow.getVbar().widthProperty().addListener(local6);
/*     */ 
/* 147 */     ((TableViewBehavior)getBehavior()).setOnFocusPreviousRow(new Runnable() {
/* 148 */       public void run() { TableViewSkin.this.onFocusPreviousCell(); }
/*     */ 
/*     */     });
/* 150 */     ((TableViewBehavior)getBehavior()).setOnFocusNextRow(new Runnable() {
/* 151 */       public void run() { TableViewSkin.this.onFocusNextCell(); }
/*     */ 
/*     */     });
/* 153 */     ((TableViewBehavior)getBehavior()).setOnMoveToFirstCell(new Runnable() {
/* 154 */       public void run() { TableViewSkin.this.onMoveToFirstCell(); }
/*     */ 
/*     */     });
/* 156 */     ((TableViewBehavior)getBehavior()).setOnMoveToLastCell(new Runnable() {
/* 157 */       public void run() { TableViewSkin.this.onMoveToLastCell(); }
/*     */ 
/*     */     });
/* 159 */     ((TableViewBehavior)getBehavior()).setOnScrollPageDown(new Callback() {
/* 160 */       public Integer call(Void paramAnonymousVoid) { return Integer.valueOf(TableViewSkin.this.onScrollPageDown()); }
/*     */ 
/*     */     });
/* 162 */     ((TableViewBehavior)getBehavior()).setOnScrollPageUp(new Callback() {
/* 163 */       public Integer call(Void paramAnonymousVoid) { return Integer.valueOf(TableViewSkin.this.onScrollPageUp()); }
/*     */ 
/*     */     });
/* 165 */     ((TableViewBehavior)getBehavior()).setOnSelectPreviousRow(new Runnable() {
/* 166 */       public void run() { TableViewSkin.this.onSelectPreviousCell(); }
/*     */ 
/*     */     });
/* 168 */     ((TableViewBehavior)getBehavior()).setOnSelectNextRow(new Runnable() {
/* 169 */       public void run() { TableViewSkin.this.onSelectNextCell(); }
/*     */ 
/*     */     });
/* 171 */     ((TableViewBehavior)getBehavior()).setOnSelectLeftCell(new Runnable() {
/* 172 */       public void run() { TableViewSkin.this.onSelectLeftCell(); }
/*     */ 
/*     */     });
/* 174 */     ((TableViewBehavior)getBehavior()).setOnSelectRightCell(new Runnable() {
/* 175 */       public void run() { TableViewSkin.this.onSelectRightCell(); }
/*     */ 
/*     */     });
/* 178 */     registerChangeListener(paramTableView.rowFactoryProperty(), "ROW_FACTORY");
/* 179 */     registerChangeListener(paramTableView.placeholderProperty(), "PLACEHOLDER");
/* 180 */     registerChangeListener(paramTableView.focusTraversableProperty(), "FOCUS_TRAVERSABLE");
/* 181 */     registerChangeListener(paramTableView.widthProperty(), "WIDTH");
/*     */   }
/*     */ 
/*     */   protected void handleControlPropertyChanged(String paramString) {
/* 185 */     super.handleControlPropertyChanged(paramString);
/*     */ 
/* 187 */     if (paramString == "ROW_FACTORY") {
/* 188 */       Callback localCallback = this.rowFactory;
/* 189 */       this.rowFactory = ((TableView)getSkinnable()).getRowFactory();
/*     */ 
/* 192 */       if (localCallback != this.rowFactory)
/* 193 */         this.flow.recreateCells();
/*     */     }
/* 195 */     else if (paramString == "PLACEHOLDER") {
/* 196 */       updatePlaceholderRegionVisibility();
/* 197 */     } else if (paramString == "FOCUS_TRAVERSABLE") {
/* 198 */       this.flow.setFocusTraversable(((TableView)getSkinnable()).isFocusTraversable());
/* 199 */     } else if (paramString == "WIDTH") {
/* 200 */       this.tableHeaderRow.setTablePadding(getInsets());
/*     */     }
/*     */   }
/*     */ 
/*     */   public TableHeaderRow getTableHeaderRow()
/*     */   {
/* 320 */     return this.tableHeaderRow;
/*     */   }
/*     */ 
/*     */   public int getItemCount()
/*     */   {
/* 325 */     return ((TableView)getSkinnable()).getItems() == null ? 0 : ((TableView)getSkinnable()).getItems().size();
/*     */   }
/*     */ 
/*     */   public TableRow createCell()
/*     */   {
/*     */     TableRow localTableRow;
/* 332 */     if (((TableView)getSkinnable()).getRowFactory() != null)
/* 333 */       localTableRow = (TableRow)((TableView)getSkinnable()).getRowFactory().call(getSkinnable());
/*     */     else {
/* 335 */       localTableRow = new TableRow();
/*     */     }
/*     */ 
/* 338 */     localTableRow.updateTableView((TableView)getSkinnable());
/* 339 */     return localTableRow;
/*     */   }
/*     */ 
/*     */   public int onScrollPageDown()
/*     */   {
/* 347 */     TableRow localTableRow = (TableRow)this.flow.getLastVisibleCellWithinViewPort();
/* 348 */     if (localTableRow == null) return -1;
/*     */ 
/* 350 */     int i = (localTableRow.isSelected()) || (localTableRow.isFocused()) || (isCellSelected(localTableRow.getIndex())) || (isCellFocused(localTableRow.getIndex())) ? 1 : 0;
/*     */ 
/* 355 */     if (i != 0)
/*     */     {
/* 358 */       this.flow.showAsFirst(localTableRow);
/* 359 */       localTableRow = (TableRow)this.flow.getLastVisibleCellWithinViewPort();
/*     */     }
/*     */ 
/* 362 */     int j = localTableRow.getIndex();
/* 363 */     this.flow.show(j);
/* 364 */     return j;
/*     */   }
/*     */ 
/*     */   public int onScrollPageUp()
/*     */   {
/* 372 */     TableRow localTableRow = (TableRow)this.flow.getFirstVisibleCellWithinViewPort();
/* 373 */     if (localTableRow == null) return -1;
/*     */ 
/* 375 */     int i = (localTableRow.isSelected()) || (localTableRow.isFocused()) || (isCellSelected(localTableRow.getIndex())) || (isCellFocused(localTableRow.getIndex())) ? 1 : 0;
/*     */ 
/* 380 */     if (i != 0)
/*     */     {
/* 383 */       this.flow.showAsLast(localTableRow);
/* 384 */       localTableRow = (TableRow)this.flow.getFirstVisibleCellWithinViewPort();
/*     */     }
/*     */ 
/* 387 */     int j = localTableRow.getIndex();
/* 388 */     this.flow.show(j);
/* 389 */     return j;
/*     */   }
/*     */ 
/*     */   protected double computePrefHeight(double paramDouble)
/*     */   {
/* 403 */     return 400.0D;
/*     */   }
/*     */ 
/*     */   protected double computePrefWidth(double paramDouble)
/*     */   {
/* 408 */     double d1 = computePrefHeight(-1.0D);
/*     */ 
/* 410 */     ObservableList localObservableList = ((TableView)getSkinnable()).getVisibleLeafColumns();
/* 411 */     if ((localObservableList == null) || (localObservableList.isEmpty())) {
/* 412 */       return d1 * 0.618033987D;
/*     */     }
/*     */ 
/* 415 */     double d2 = getInsets().getLeft() + getInsets().getRight();
/* 416 */     for (TableColumn localTableColumn : localObservableList) {
/* 417 */       d2 += Math.max(localTableColumn.getPrefWidth(), localTableColumn.getMinWidth());
/*     */     }
/*     */ 
/* 420 */     return Math.max(d2, d1 * 0.618033987D);
/*     */   }
/*     */ 
/*     */   protected void layoutChildren()
/*     */   {
/* 425 */     if (this.rowCountDirty) {
/* 426 */       updateRowCount();
/* 427 */       this.rowCountDirty = false;
/*     */     }
/*     */ 
/* 430 */     double d1 = getInsets().getLeft();
/* 431 */     double d2 = getInsets().getTop();
/* 432 */     double d3 = getWidth() - (getInsets().getLeft() + getInsets().getRight());
/* 433 */     double d4 = getHeight() - (getInsets().getTop() + getInsets().getBottom());
/*     */ 
/* 435 */     double d5 = getLayoutBounds().getHeight() / 2.0D;
/*     */ 
/* 438 */     double d6 = this.tableHeaderRow.prefHeight(-1.0D);
/* 439 */     layoutInArea(this.tableHeaderRow, d1, d2, d3, d6, d5, getAlignment().getHpos(), getAlignment().getVpos());
/*     */ 
/* 441 */     d2 += d6;
/*     */ 
/* 447 */     double d7 = Math.floor(d4 - d6);
/* 448 */     if ((getItemCount() == 0) || (this.visibleColCount == 0))
/*     */     {
/* 450 */       layoutInArea(this.placeholderRegion, d1, d2, d3, d7, d5, getAlignment().getHpos(), getAlignment().getVpos());
/*     */     }
/*     */     else
/*     */     {
/* 454 */       layoutInArea(this.flow, d1, d2, d3, d7, d5, getAlignment().getHpos(), getAlignment().getVpos());
/*     */     }
/*     */ 
/* 460 */     if (this.tableHeaderRow.getReorderingRegion() != null) {
/* 461 */       TableColumnHeader localTableColumnHeader = this.tableHeaderRow.getReorderingRegion();
/* 462 */       TableColumn localTableColumn = localTableColumnHeader.getTableColumn();
/* 463 */       if (localTableColumn != null) {
/* 464 */         localObject = this.tableHeaderRow.getReorderingRegion();
/*     */ 
/* 470 */         d8 = this.tableHeaderRow.sceneToLocal(((Node)localObject).localToScene(((Node)localObject).getBoundsInLocal())).getMinX();
/* 471 */         d9 = localTableColumnHeader.getWidth();
/* 472 */         if (d8 < 0.0D) {
/* 473 */           d9 += d8;
/*     */         }
/* 475 */         d8 = d8 < 0.0D ? 0.0D : d8;
/*     */ 
/* 479 */         if (d8 + d9 > d3) {
/* 480 */           d9 = d3 - d8;
/*     */ 
/* 482 */           if (this.flow.getVbar().isVisible()) {
/* 483 */             d9 -= this.flow.getVbar().getWidth() - 1.0D;
/*     */           }
/*     */         }
/*     */ 
/* 487 */         double d10 = d7;
/* 488 */         if (this.flow.getHbar().isVisible()) {
/* 489 */           d10 -= this.flow.getHbar().getHeight();
/*     */         }
/*     */ 
/* 492 */         this.columnReorderOverlay.resize(d9, d10);
/*     */ 
/* 494 */         this.columnReorderOverlay.setLayoutX(d8);
/* 495 */         this.columnReorderOverlay.setLayoutY(this.tableHeaderRow.getHeight());
/*     */       }
/*     */ 
/* 499 */       Object localObject = this.columnReorderLine.getInsets();
/* 500 */       double d8 = ((Insets)localObject).getLeft() + ((Insets)localObject).getRight();
/* 501 */       double d9 = d4 - (this.flow.getHbar().isVisible() ? this.flow.getHbar().getHeight() - 1.0D : 0.0D);
/* 502 */       this.columnReorderLine.resizeRelocate(0.0D, ((Insets)localObject).getTop(), d8, d9);
/*     */     }
/*     */ 
/* 505 */     this.columnReorderLine.setVisible(this.tableHeaderRow.isReordering());
/* 506 */     this.columnReorderOverlay.setVisible(this.tableHeaderRow.isReordering());
/*     */ 
/* 511 */     if ((this.contentWidthDirty) || (getItemCount() == 0)) {
/* 512 */       updateContentWidth();
/* 513 */       this.contentWidthDirty = false;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void updateTableItems(ObservableList<T> paramObservableList1, ObservableList<T> paramObservableList2)
/*     */   {
/* 526 */     if (paramObservableList1 != null) {
/* 527 */       paramObservableList1.removeListener(this.weakRowCountListener);
/*     */     }
/*     */ 
/* 530 */     if (paramObservableList2 != null) {
/* 531 */       paramObservableList2.addListener(this.weakRowCountListener);
/*     */     }
/*     */ 
/* 534 */     this.rowCountDirty = true;
/* 535 */     requestLayout();
/*     */   }
/*     */ 
/*     */   private void updateVisibleColumnCount()
/*     */   {
/* 542 */     this.visibleColCount = ((TableView)getSkinnable()).getVisibleLeafColumns().size();
/*     */ 
/* 544 */     updatePlaceholderRegionVisibility();
/* 545 */     reconfigureCells();
/*     */   }
/*     */ 
/*     */   private void updateVisibleLeafColumnWidthListeners(List<? extends TableColumn<T, ?>> paramList1, List<? extends TableColumn<T, ?>> paramList2)
/*     */   {
/* 551 */     for (Iterator localIterator = paramList2.iterator(); localIterator.hasNext(); ) { localTableColumn = (TableColumn)localIterator.next();
/* 552 */       localTableColumn.widthProperty().removeListener(this.weakWidthListener);
/*     */     }
/* 554 */     TableColumn localTableColumn;
/* 554 */     for (localIterator = paramList1.iterator(); localIterator.hasNext(); ) { localTableColumn = (TableColumn)localIterator.next();
/* 555 */       localTableColumn.widthProperty().addListener(this.weakWidthListener);
/*     */     }
/* 557 */     this.flow.reconfigureCells();
/*     */   }
/*     */ 
/*     */   private void updatePlaceholderRegionVisibility() {
/* 561 */     boolean bool = (this.visibleColCount == 0) || (getItemCount() == 0);
/*     */ 
/* 563 */     if (bool) {
/* 564 */       if (this.placeholderRegion == null) {
/* 565 */         this.placeholderRegion = new StackPane();
/* 566 */         this.placeholderRegion.getStyleClass().setAll(new String[] { "placeholder" });
/* 567 */         getChildren().add(this.placeholderRegion);
/*     */       }
/*     */ 
/* 570 */       Node localNode = ((TableView)getSkinnable()).getPlaceholder();
/*     */ 
/* 572 */       if (localNode == null) {
/* 573 */         if (this.placeholderLabel == null) {
/* 574 */           this.placeholderLabel = new Label();
/*     */         }
/* 576 */         String str = this.visibleColCount == 0 ? NO_COLUMNS_TEXT : EMPTY_TABLE_TEXT;
/* 577 */         this.placeholderLabel.setText(str);
/*     */ 
/* 579 */         this.placeholderRegion.getChildren().setAll(new Node[] { this.placeholderLabel });
/*     */       } else {
/* 581 */         this.placeholderRegion.getChildren().setAll(new Node[] { localNode });
/*     */       }
/*     */     }
/*     */ 
/* 585 */     this.flow.setVisible(!bool);
/* 586 */     if (this.placeholderRegion != null)
/* 587 */       this.placeholderRegion.setVisible(bool);
/*     */   }
/*     */ 
/*     */   private void updateContentWidth()
/*     */   {
/* 597 */     double d = this.flow.getWidth();
/*     */ 
/* 599 */     if (this.flow.getVbar().isVisible()) {
/* 600 */       d -= this.flow.getVbar().getWidth();
/*     */     }
/*     */ 
/* 603 */     if (d <= 0.0D)
/*     */     {
/* 605 */       d = getWidth() - (getInsets().getLeft() + getInsets().getRight());
/*     */     }
/*     */ 
/* 610 */     ((TableView)getSkinnable()).getProperties().put("TableView.contentWidth", Double.valueOf(Math.floor(d)));
/*     */   }
/*     */ 
/*     */   private void refreshView() {
/* 614 */     this.rowCountDirty = true;
/* 615 */     requestLayout();
/*     */   }
/*     */ 
/*     */   private void reconfigureCells() {
/* 619 */     this.flow.reconfigureCells();
/*     */   }
/*     */ 
/*     */   private void updateRowCount() {
/* 623 */     updatePlaceholderRegionVisibility();
/*     */ 
/* 625 */     int i = this.flow.getCellCount();
/* 626 */     int j = getItemCount();
/*     */ 
/* 631 */     this.flow.setCellCount(j);
/*     */ 
/* 633 */     if (j != i)
/*     */     {
/* 637 */       this.flow.recreateCells();
/*     */     }
/* 639 */     else this.flow.reconfigureCells();
/*     */   }
/*     */ 
/*     */   private void onFocusPreviousCell()
/*     */   {
/* 644 */     TableView.TableViewFocusModel localTableViewFocusModel = ((TableView)getSkinnable()).getFocusModel();
/* 645 */     if (localTableViewFocusModel == null) return;
/*     */ 
/* 647 */     this.flow.show(localTableViewFocusModel.getFocusedIndex());
/*     */   }
/*     */ 
/*     */   private void onFocusNextCell() {
/* 651 */     TableView.TableViewFocusModel localTableViewFocusModel = ((TableView)getSkinnable()).getFocusModel();
/* 652 */     if (localTableViewFocusModel == null) return;
/*     */ 
/* 654 */     this.flow.show(localTableViewFocusModel.getFocusedIndex());
/*     */   }
/*     */ 
/*     */   private void onSelectPreviousCell() {
/* 658 */     TableView.TableViewSelectionModel localTableViewSelectionModel = ((TableView)getSkinnable()).getSelectionModel();
/* 659 */     if (localTableViewSelectionModel == null) return;
/*     */ 
/* 661 */     this.flow.show(localTableViewSelectionModel.getSelectedIndex());
/*     */   }
/*     */ 
/*     */   private void onSelectNextCell() {
/* 665 */     TableView.TableViewSelectionModel localTableViewSelectionModel = ((TableView)getSkinnable()).getSelectionModel();
/* 666 */     if (localTableViewSelectionModel == null) return;
/*     */ 
/* 668 */     this.flow.show(localTableViewSelectionModel.getSelectedIndex());
/*     */   }
/*     */ 
/*     */   private void onSelectLeftCell() {
/* 672 */     scrollHorizontally();
/*     */   }
/*     */ 
/*     */   private void onSelectRightCell() {
/* 676 */     scrollHorizontally();
/*     */   }
/*     */ 
/*     */   private void moveToLeftMostColumn() {
/* 680 */     scrollHorizontally(((TableView)getSkinnable()).getVisibleLeafColumn(0));
/*     */   }
/*     */ 
/*     */   private void moveToRightMostColumn() {
/* 684 */     scrollHorizontally(((TableView)getSkinnable()).getVisibleLeafColumn(((TableView)getSkinnable()).getVisibleLeafColumns().size() - 1));
/*     */   }
/*     */ 
/*     */   private void scrollHorizontally()
/*     */   {
/* 691 */     TableView.TableViewFocusModel localTableViewFocusModel = ((TableView)getSkinnable()).getFocusModel();
/* 692 */     if (localTableViewFocusModel == null) return;
/*     */ 
/* 694 */     TableColumn localTableColumn = localTableViewFocusModel.getFocusedCell().getTableColumn();
/* 695 */     scrollHorizontally(localTableColumn);
/*     */   }
/*     */ 
/*     */   private void scrollHorizontally(TableColumn paramTableColumn)
/*     */   {
/* 738 */     if ((paramTableColumn == null) || (!paramTableColumn.isVisible())) return;
/*     */ 
/* 741 */     double d1 = this.scrollX;
/* 742 */     for (TableColumn localTableColumn : ((TableView)getSkinnable()).getVisibleLeafColumns()) {
/* 743 */       if (localTableColumn.equals(paramTableColumn)) break;
/* 744 */       d1 += localTableColumn.getWidth();
/*     */     }
/* 746 */     double d2 = d1 + paramTableColumn.getWidth();
/*     */ 
/* 749 */     double d3 = ((TableView)getSkinnable()).getWidth() - getInsets().getLeft() + getInsets().getRight();
/*     */ 
/* 755 */     double d4 = this.flow.getHbar().getValue();
/* 756 */     double d5 = this.flow.getHbar().getMax();
/* 757 */     double d6 = d4;
/*     */ 
/* 759 */     if ((d1 < d4) && (d1 >= 0.0D)) {
/* 760 */       d6 = d1;
/*     */     } else {
/* 762 */       double d7 = (d1 < 0.0D) || (d2 > d3) ? d1 : 0.0D;
/* 763 */       d6 = d4 + d7 > d5 ? d5 : d4 + d7;
/*     */     }
/*     */ 
/* 770 */     this.flow.getHbar().setValue(d6);
/*     */   }
/*     */ 
/*     */   private void onMoveToFirstCell() {
/* 774 */     TableView.TableViewSelectionModel localTableViewSelectionModel = ((TableView)getSkinnable()).getSelectionModel();
/* 775 */     if (localTableViewSelectionModel == null) return;
/*     */ 
/* 777 */     this.flow.show(0);
/* 778 */     this.flow.setPosition(0.0D);
/*     */   }
/*     */ 
/*     */   private void onMoveToLastCell() {
/* 782 */     TableView.TableViewSelectionModel localTableViewSelectionModel = ((TableView)getSkinnable()).getSelectionModel();
/* 783 */     if (localTableViewSelectionModel == null) return;
/*     */ 
/* 785 */     int i = getItemCount();
/* 786 */     this.flow.show(i);
/* 787 */     this.flow.setPosition(1.0D);
/*     */   }
/*     */ 
/*     */   private boolean isCellSelected(int paramInt)
/*     */   {
/* 805 */     TableView.TableViewSelectionModel localTableViewSelectionModel = ((TableView)getSkinnable()).getSelectionModel();
/* 806 */     if (localTableViewSelectionModel == null) return false;
/* 807 */     if (!localTableViewSelectionModel.isCellSelectionEnabled()) return false;
/*     */ 
/* 809 */     int i = ((TableView)getSkinnable()).getVisibleLeafColumns().size();
/* 810 */     for (int j = 0; j < i; j++) {
/* 811 */       if (localTableViewSelectionModel.isSelected(paramInt, ((TableView)getSkinnable()).getVisibleLeafColumn(j))) {
/* 812 */         return true;
/*     */       }
/*     */     }
/* 815 */     return false;
/*     */   }
/*     */ 
/*     */   private boolean isCellFocused(int paramInt) {
/* 819 */     TableView.TableViewFocusModel localTableViewFocusModel = ((TableView)getSkinnable()).getFocusModel();
/* 820 */     if (localTableViewFocusModel == null) return false;
/*     */ 
/* 822 */     int i = ((TableView)getSkinnable()).getVisibleLeafColumns().size();
/* 823 */     for (int j = 0; j < i; j++) {
/* 824 */       if (localTableViewFocusModel.isFocused(paramInt, ((TableView)getSkinnable()).getVisibleLeafColumn(j))) {
/* 825 */         return true;
/*     */       }
/*     */     }
/* 828 */     return false;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.TableViewSkin
 * JD-Core Version:    0.6.2
 */