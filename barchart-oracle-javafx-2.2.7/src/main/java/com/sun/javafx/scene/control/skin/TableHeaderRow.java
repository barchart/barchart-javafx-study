/*     */ package com.sun.javafx.scene.control.skin;
/*     */ 
/*     */ import com.sun.javafx.scene.control.WeakListChangeListener;
/*     */ import com.sun.javafx.scene.control.skin.resources.ControlResources;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.Observable;
/*     */ import javafx.beans.WeakInvalidationListener;
/*     */ import javafx.beans.binding.StringBinding;
/*     */ import javafx.beans.property.BooleanProperty;
/*     */ import javafx.beans.property.BooleanPropertyBase;
/*     */ import javafx.beans.property.DoubleProperty;
/*     */ import javafx.beans.property.ReadOnlyDoubleProperty;
/*     */ import javafx.beans.property.StringProperty;
/*     */ import javafx.collections.ListChangeListener;
/*     */ import javafx.collections.ListChangeListener.Change;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.geometry.Bounds;
/*     */ import javafx.geometry.HPos;
/*     */ import javafx.geometry.Insets;
/*     */ import javafx.geometry.Side;
/*     */ import javafx.geometry.VPos;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.control.CheckMenuItem;
/*     */ import javafx.scene.control.ContextMenu;
/*     */ import javafx.scene.control.Label;
/*     */ import javafx.scene.control.TableColumn;
/*     */ import javafx.scene.control.TableView;
/*     */ import javafx.scene.input.MouseEvent;
/*     */ import javafx.scene.layout.Pane;
/*     */ import javafx.scene.layout.Region;
/*     */ import javafx.scene.layout.StackPane;
/*     */ import javafx.scene.shape.Rectangle;
/*     */ 
/*     */ public class TableHeaderRow extends StackPane
/*     */ {
/*  63 */   private static final String MENU_SEPARATOR = ControlResources.getString("TableView.nestedColumnControlMenuSeparator");
/*     */   private final VirtualFlow flow;
/*     */   private final TableView<?> table;
/*     */   private Insets tablePadding;
/*     */   private Region columnReorderLine;
/*     */   private double scrollX;
/*     */   private double tableWidth;
/*     */   private Rectangle clip;
/*  97 */   private BooleanProperty reorderingProperty = new BooleanPropertyBase() {
/*     */     protected void invalidated() {
/*  99 */       TableColumnHeader localTableColumnHeader = TableHeaderRow.this.getReorderingRegion();
/* 100 */       if (localTableColumnHeader != null) {
/* 101 */         double d = localTableColumnHeader.getNestedColumnHeader() != null ? localTableColumnHeader.getNestedColumnHeader().getHeight() : TableHeaderRow.this.getReorderingRegion().getHeight();
/*     */ 
/* 105 */         TableHeaderRow.this.dragHeader.resize(TableHeaderRow.this.dragHeader.getWidth(), d);
/* 106 */         TableHeaderRow.this.dragHeader.setTranslateY(TableHeaderRow.this.getHeight() - d);
/*     */       }
/* 108 */       TableHeaderRow.this.dragHeader.setVisible(TableHeaderRow.this.isReordering());
/*     */     }
/*     */ 
/*     */     public Object getBean()
/*     */     {
/* 113 */       return TableHeaderRow.this;
/*     */     }
/*     */ 
/*     */     public String getName()
/*     */     {
/* 118 */       return "reordering";
/*     */     }
/*  97 */   };
/*     */   private TableColumnHeader reorderingRegion;
/*     */   private StackPane dragHeader;
/* 149 */   private final Label dragHeaderLabel = new Label();
/*     */   private final NestedTableColumnHeader header;
/*     */   private Region filler;
/*     */   private Pane cornerRegion;
/*     */   private ContextMenu columnPopupMenu;
/* 294 */   private InvalidationListener tableWidthListener = new InvalidationListener() {
/*     */     public void invalidated(Observable paramAnonymousObservable) {
/* 296 */       TableHeaderRow.this.updateTableWidth();
/*     */     }
/* 294 */   };
/*     */ 
/* 300 */   private ListChangeListener visibleLeafColumnsListener = new ListChangeListener()
/*     */   {
/*     */     public void onChanged(ListChangeListener.Change<? extends TableColumn<?, ?>> paramAnonymousChange) {
/* 303 */       TableHeaderRow.this.header.updateTableColumnHeaders();
/*     */     }
/* 300 */   };
/*     */ 
/* 307 */   private final ListChangeListener tableColumnsListener = new ListChangeListener() {
/*     */     public void onChanged(ListChangeListener.Change<? extends TableColumn<?, ?>> paramAnonymousChange) {
/* 309 */       while (paramAnonymousChange.next())
/* 310 */         TableHeaderRow.this.updateTableColumnListeners(paramAnonymousChange.getAddedSubList(), paramAnonymousChange.getRemoved());
/*     */     }
/* 307 */   };
/*     */ 
/* 315 */   private final WeakInvalidationListener weakTableWidthListener = new WeakInvalidationListener(this.tableWidthListener);
/*     */ 
/* 318 */   private final WeakListChangeListener weakVisibleLeafColumnsListener = new WeakListChangeListener(this.visibleLeafColumnsListener);
/*     */ 
/* 321 */   private final WeakListChangeListener weakTableColumnsListener = new WeakListChangeListener(this.tableColumnsListener);
/*     */ 
/* 325 */   private Map<TableColumn, CheckMenuItem> columnMenuItems = new HashMap();
/*     */ 
/*     */   VirtualFlow getVirtualFlow()
/*     */   {
/*  67 */     return this.flow;
/*     */   }
/*     */ 
/*     */   public void setTablePadding(Insets paramInsets)
/*     */   {
/*  72 */     this.tablePadding = paramInsets;
/*  73 */     updateTableWidth();
/*     */   }
/*     */   public Insets getTablePadding() {
/*  76 */     return this.tablePadding == null ? Insets.EMPTY : this.tablePadding;
/*     */   }
/*     */ 
/*     */   public Region getColumnReorderLine()
/*     */   {
/*  81 */     return this.columnReorderLine; } 
/*  82 */   public void setColumnReorderLine(Region paramRegion) { this.columnReorderLine = paramRegion; }
/*     */ 
/*     */ 
/*     */   public double getTableWidth()
/*     */   {
/*  87 */     return this.tableWidth;
/*     */   }
/*     */   private void updateTableWidth() {
/*  90 */     double d = snapSpace(getTablePadding().getLeft()) + snapSpace(getTablePadding().getRight());
/*  91 */     this.tableWidth = (snapSize(this.table.getWidth()) - d);
/*  92 */     this.clip.setWidth(this.tableWidth + 1.0D);
/*     */   }
/*     */ 
/*     */   public final void setReordering(boolean paramBoolean)
/*     */   {
/* 121 */     reorderingProperty().set(paramBoolean); } 
/* 122 */   public final boolean isReordering() { return this.reorderingProperty.get(); } 
/* 123 */   public final BooleanProperty reorderingProperty() { return this.reorderingProperty; }
/*     */ 
/*     */   public TableColumnHeader getReorderingRegion() {
/* 126 */     return this.reorderingRegion;
/*     */   }
/*     */   public void setReorderingColumn(TableColumn paramTableColumn) {
/* 129 */     this.dragHeaderLabel.setText(paramTableColumn == null ? "" : paramTableColumn.getText());
/*     */   }
/*     */ 
/*     */   public void setReorderingRegion(TableColumnHeader paramTableColumnHeader) {
/* 133 */     this.reorderingRegion = paramTableColumnHeader;
/*     */ 
/* 135 */     if (paramTableColumnHeader != null)
/* 136 */       this.dragHeader.resize(paramTableColumnHeader.getWidth(), this.dragHeader.getHeight());
/*     */   }
/*     */ 
/*     */   public void setDragHeaderX(double paramDouble)
/*     */   {
/* 141 */     this.dragHeader.setTranslateX(paramDouble);
/*     */   }
/*     */ 
/*     */   public NestedTableColumnHeader getRootHeader()
/*     */   {
/* 162 */     return this.header;
/*     */   }
/*     */ 
/*     */   public TableHeaderRow(final TableView<?> paramTableView, VirtualFlow paramVirtualFlow)
/*     */   {
/* 188 */     this.table = paramTableView;
/* 189 */     this.flow = paramVirtualFlow;
/*     */ 
/* 191 */     getStyleClass().setAll(new String[] { "column-header-background" });
/*     */ 
/* 200 */     InvalidationListener local2 = new InvalidationListener() {
/*     */       public void invalidated(Observable paramAnonymousObservable) {
/* 202 */         TableHeaderRow.this.updateScrollX();
/*     */       }
/*     */     };
/* 205 */     paramVirtualFlow.getHbar().valueProperty().addListener(local2);
/*     */ 
/* 207 */     this.clip = new Rectangle();
/* 208 */     this.clip.setSmooth(false);
/* 209 */     this.clip.heightProperty().bind(heightProperty());
/* 210 */     setClip(this.clip);
/*     */ 
/* 212 */     updateTableWidth();
/* 213 */     paramTableView.widthProperty().addListener(this.weakTableWidthListener);
/* 214 */     paramTableView.getVisibleLeafColumns().addListener(this.weakVisibleLeafColumnsListener);
/*     */ 
/* 217 */     this.columnPopupMenu = new ContextMenu();
/*     */ 
/* 219 */     updateTableColumnListeners(paramTableView.getColumns(), Collections.emptyList());
/* 220 */     paramTableView.getColumns().addListener(this.weakTableColumnsListener);
/*     */ 
/* 224 */     this.dragHeader = new StackPane();
/* 225 */     this.dragHeader.setVisible(false);
/* 226 */     this.dragHeader.getStyleClass().setAll(new String[] { "column-drag-header" });
/* 227 */     this.dragHeader.setManaged(false);
/* 228 */     this.dragHeader.getChildren().add(this.dragHeaderLabel);
/*     */ 
/* 231 */     this.header = new NestedTableColumnHeader(paramTableView, null);
/* 232 */     this.header.setFocusTraversable(false);
/* 233 */     this.header.setTableHeaderRow(this);
/*     */ 
/* 237 */     this.filler = new Region();
/* 238 */     this.filler.getStyleClass().setAll(new String[] { "filler" });
/*     */ 
/* 242 */     setOnMousePressed(new EventHandler() {
/*     */       public void handle(MouseEvent paramAnonymousMouseEvent) {
/* 244 */         paramTableView.requestFocus();
/*     */       }
/*     */     });
/* 248 */     final StackPane localStackPane = new StackPane();
/* 249 */     localStackPane.setSnapToPixel(false);
/* 250 */     localStackPane.getStyleClass().setAll(new String[] { "show-hide-column-image" });
/* 251 */     this.cornerRegion = new StackPane() {
/*     */       protected void layoutChildren() {
/* 253 */         Insets localInsets = localStackPane.getInsets();
/* 254 */         double d1 = localInsets.getLeft() + localInsets.getRight();
/* 255 */         double d2 = localInsets.getTop() + localInsets.getBottom();
/*     */ 
/* 257 */         localStackPane.resize(d1, d2);
/* 258 */         positionInArea(localStackPane, 0.0D, 0.0D, getWidth(), getHeight() - 3.0D, 0.0D, HPos.CENTER, VPos.CENTER);
/*     */       }
/*     */     };
/* 262 */     this.cornerRegion.getStyleClass().setAll(new String[] { "show-hide-columns-button" });
/* 263 */     this.cornerRegion.getChildren().addAll(new Node[] { localStackPane });
/* 264 */     this.cornerRegion.setVisible(paramTableView.isTableMenuButtonVisible());
/* 265 */     paramTableView.tableMenuButtonVisibleProperty().addListener(new InvalidationListener() {
/*     */       public void invalidated(Observable paramAnonymousObservable) {
/* 267 */         TableHeaderRow.this.cornerRegion.setVisible(paramTableView.isTableMenuButtonVisible());
/* 268 */         TableHeaderRow.this.requestLayout();
/*     */       }
/*     */     });
/* 271 */     this.cornerRegion.setOnMousePressed(new EventHandler()
/*     */     {
/*     */       public void handle(MouseEvent paramAnonymousMouseEvent) {
/* 274 */         TableHeaderRow.this.columnPopupMenu.show(TableHeaderRow.this.cornerRegion, Side.BOTTOM, 0.0D, 0.0D);
/* 275 */         paramAnonymousMouseEvent.consume();
/*     */       }
/*     */     });
/* 283 */     getChildren().addAll(new Node[] { this.filler, this.header, this.cornerRegion, this.dragHeader });
/*     */   }
/*     */ 
/*     */   private void updateTableColumnListeners(List<? extends TableColumn<?, ?>> paramList1, List<? extends TableColumn<?, ?>> paramList2)
/*     */   {
/* 328 */     for (Iterator localIterator = paramList2.iterator(); localIterator.hasNext(); ) { localTableColumn = (TableColumn)localIterator.next();
/* 329 */       remove(localTableColumn);
/*     */     }
/* 333 */     TableColumn localTableColumn;
/* 333 */     for (localIterator = paramList1.iterator(); localIterator.hasNext(); ) { localTableColumn = (TableColumn)localIterator.next();
/* 334 */       add(localTableColumn); }
/*     */   }
/*     */ 
/*     */   private void remove(TableColumn<?, ?> paramTableColumn)
/*     */   {
/* 339 */     if (paramTableColumn == null)
/*     */       return;
/*     */     Object localObject;
/* 341 */     if (paramTableColumn.getColumns().isEmpty()) {
/* 342 */       localObject = (CheckMenuItem)this.columnMenuItems.remove(paramTableColumn);
/* 343 */       if (localObject == null) return;
/*     */ 
/* 345 */       ((CheckMenuItem)localObject).textProperty().unbind();
/* 346 */       ((CheckMenuItem)localObject).selectedProperty().unbindBidirectional(paramTableColumn.visibleProperty());
/*     */ 
/* 348 */       this.columnPopupMenu.getItems().remove(localObject);
/*     */     } else {
/* 350 */       for (localObject = paramTableColumn.getColumns().iterator(); ((Iterator)localObject).hasNext(); ) { TableColumn localTableColumn = (TableColumn)((Iterator)localObject).next();
/* 351 */         remove(localTableColumn); }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void add(final TableColumn<?, ?> paramTableColumn)
/*     */   {
/* 357 */     if (paramTableColumn == null)
/*     */       return;
/*     */     Object localObject;
/* 359 */     if (paramTableColumn.getColumns().isEmpty()) {
/* 360 */       localObject = (CheckMenuItem)this.columnMenuItems.get(paramTableColumn);
/* 361 */       if (localObject == null) {
/* 362 */         localObject = new CheckMenuItem();
/* 363 */         this.columnMenuItems.put(paramTableColumn, localObject);
/*     */       }
/*     */ 
/* 367 */       ((CheckMenuItem)localObject).textProperty().bind(new StringBinding()
/*     */       {
/*     */         protected String computeValue()
/*     */         {
/* 371 */           return TableHeaderRow.this.getText(paramTableColumn.getText(), paramTableColumn);
/*     */         }
/*     */       });
/* 374 */       ((CheckMenuItem)localObject).selectedProperty().bindBidirectional(paramTableColumn.visibleProperty());
/*     */ 
/* 376 */       this.columnPopupMenu.getItems().add(localObject);
/*     */     } else {
/* 378 */       for (localObject = paramTableColumn.getColumns().iterator(); ((Iterator)localObject).hasNext(); ) { TableColumn localTableColumn = (TableColumn)((Iterator)localObject).next();
/* 379 */         add(localTableColumn); }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void updateScrollX()
/*     */   {
/* 385 */     this.scrollX = (this.flow.getHbar().isVisible() ? -this.flow.getHbar().getValue() : 0.0D);
/* 386 */     requestLayout();
/*     */   }
/*     */ 
/*     */   protected void layoutChildren() {
/* 390 */     double d1 = this.scrollX;
/* 391 */     double d2 = snapSize(this.header.prefWidth(-1.0D));
/* 392 */     double d3 = getHeight() - getInsets().getTop() - getInsets().getBottom();
/* 393 */     double d4 = snapSize(this.flow.getVbar().prefWidth(-1.0D));
/*     */ 
/* 396 */     this.header.resizeRelocate(d1, getInsets().getTop(), d2, d3);
/*     */ 
/* 399 */     double d5 = this.filler.getBoundsInLocal().getWidth() - this.filler.getLayoutBounds().getWidth();
/* 400 */     double d6 = this.tableWidth - d2 + d5;
/* 401 */     d6 -= (this.table.isTableMenuButtonVisible() ? d4 : 0.0D);
/* 402 */     this.filler.setVisible(d6 > 0.0D);
/* 403 */     if (d6 > 0.0D) {
/* 404 */       this.filler.resizeRelocate(d1 + d2, getInsets().getTop(), d6, d3);
/*     */     }
/*     */ 
/* 408 */     this.cornerRegion.resizeRelocate(this.tableWidth - d4, getInsets().getTop(), d4, d3);
/*     */   }
/*     */ 
/*     */   protected double computePrefWidth(double paramDouble) {
/* 412 */     return this.header.prefWidth(paramDouble);
/*     */   }
/*     */ 
/*     */   protected double computeMinHeight(double paramDouble) {
/* 416 */     return computePrefHeight(paramDouble);
/*     */   }
/*     */ 
/*     */   protected double computePrefHeight(double paramDouble) {
/* 420 */     return getInsets().getTop() + this.header.prefHeight(paramDouble) + getInsets().getBottom();
/*     */   }
/*     */ 
/*     */   private String getText(String paramString, TableColumn paramTableColumn)
/*     */   {
/* 444 */     String str = paramString;
/* 445 */     TableColumn localTableColumn = paramTableColumn.getParentColumn();
/* 446 */     while (localTableColumn != null) {
/* 447 */       if (isColumnVisibleInHeader(localTableColumn, this.table.getColumns())) {
/* 448 */         str = localTableColumn.getText() + MENU_SEPARATOR + str;
/*     */       }
/* 450 */       localTableColumn = localTableColumn.getParentColumn();
/*     */     }
/* 452 */     return str;
/*     */   }
/*     */ 
/*     */   private boolean isColumnVisibleInHeader(TableColumn paramTableColumn, List paramList)
/*     */   {
/* 460 */     if (paramTableColumn == null) return false;
/*     */ 
/* 462 */     for (int i = 0; i < paramList.size(); i++) {
/* 463 */       TableColumn localTableColumn = (TableColumn)paramList.get(i);
/* 464 */       if (paramTableColumn.equals(localTableColumn)) return true;
/*     */ 
/* 466 */       if (!localTableColumn.getColumns().isEmpty()) {
/* 467 */         boolean bool = isColumnVisibleInHeader(paramTableColumn, localTableColumn.getColumns());
/* 468 */         if (bool) return true;
/*     */       }
/*     */     }
/*     */ 
/* 472 */     return false;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.TableHeaderRow
 * JD-Core Version:    0.6.2
 */