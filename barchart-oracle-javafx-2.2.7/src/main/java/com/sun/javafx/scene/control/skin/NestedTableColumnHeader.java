/*     */ package com.sun.javafx.scene.control.skin;
/*     */ 
/*     */ import com.sun.javafx.PlatformUtil;
/*     */ import com.sun.javafx.scene.control.WeakListChangeListener;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.Observable;
/*     */ import javafx.beans.WeakInvalidationListener;
/*     */ import javafx.beans.property.BooleanProperty;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.StringProperty;
/*     */ import javafx.collections.FXCollections;
/*     */ import javafx.collections.ListChangeListener;
/*     */ import javafx.collections.ListChangeListener.Change;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.collections.ObservableMap;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.geometry.Bounds;
/*     */ import javafx.geometry.Insets;
/*     */ import javafx.scene.Cursor;
/*     */ import javafx.scene.control.TableColumn;
/*     */ import javafx.scene.control.TableView;
/*     */ import javafx.scene.input.MouseEvent;
/*     */ import javafx.scene.layout.Region;
/*     */ import javafx.scene.paint.Color;
/*     */ import javafx.scene.shape.Rectangle;
/*     */ 
/*     */ public class NestedTableColumnHeader extends TableColumnHeader
/*     */ {
/*  82 */   private final ListChangeListener<TableColumn> columnsListener = new ListChangeListener() {
/*     */     public void onChanged(ListChangeListener.Change<? extends TableColumn> paramAnonymousChange) {
/*  84 */       NestedTableColumnHeader.this.updateTableColumnHeaders();
/*     */     }
/*  82 */   };
/*     */ 
/*  88 */   private final InvalidationListener columnTextListener = new InvalidationListener() {
/*     */     public void invalidated(Observable paramAnonymousObservable) {
/*  90 */       NestedTableColumnHeader.this.label.setVisible((NestedTableColumnHeader.this.getTableColumn().getText() != null) && (!NestedTableColumnHeader.this.getTableColumn().getText().isEmpty()));
/*     */     }
/*  88 */   };
/*     */ 
/*  94 */   private final InvalidationListener resizePolicyListener = new InvalidationListener() {
/*     */     public void invalidated(Observable paramAnonymousObservable) {
/*  96 */       NestedTableColumnHeader.this.updateContent();
/*     */     }
/*  94 */   };
/*     */ 
/* 100 */   private final WeakListChangeListener weakColumnsListener = new WeakListChangeListener(this.columnsListener);
/*     */ 
/* 103 */   private final WeakInvalidationListener weakColumnTextListener = new WeakInvalidationListener(this.columnTextListener);
/*     */ 
/* 106 */   private final WeakInvalidationListener weakResizePolicyListener = new WeakInvalidationListener(this.resizePolicyListener);
/*     */   private static final int DRAG_RECT_WIDTH = 4;
/*     */   private ObservableList<? extends TableColumn> columns;
/*     */   private TableColumnHeader label;
/*     */   private ObservableList<TableColumnHeader> columnHeaders;
/*     */   private static final String TABLE_COLUMN_KEY = "TableColumn";
/*     */   private static final String TABLE_COLUMN_HEADER_KEY = "TableColumnHeader";
/* 268 */   private static final EventHandler<MouseEvent> rectMousePressed = new EventHandler() {
/*     */     public void handle(MouseEvent paramAnonymousMouseEvent) {
/* 270 */       Rectangle localRectangle1 = (Rectangle)paramAnonymousMouseEvent.getSource();
/* 271 */       TableColumn localTableColumn = (TableColumn)localRectangle1.getProperties().get("TableColumn");
/* 272 */       NestedTableColumnHeader localNestedTableColumnHeader = (NestedTableColumnHeader)localRectangle1.getProperties().get("TableColumnHeader");
/*     */ 
/* 274 */       if (!localNestedTableColumnHeader.isColumnResizingEnabled()) return;
/*     */ 
/* 276 */       if ((paramAnonymousMouseEvent.getClickCount() == 2) && (paramAnonymousMouseEvent.isPrimaryButtonDown()))
/*     */       {
/* 279 */         localNestedTableColumnHeader.resizeToFit(localTableColumn, -1);
/*     */       }
/*     */       else
/*     */       {
/* 283 */         Rectangle localRectangle2 = (Rectangle)paramAnonymousMouseEvent.getSource();
/* 284 */         double d = localNestedTableColumnHeader.getTableHeaderRow().sceneToLocal(localRectangle2.localToScene(localRectangle2.getBoundsInLocal())).getMinX() + 2.0D;
/* 285 */         localNestedTableColumnHeader.dragAnchorX = paramAnonymousMouseEvent.getSceneX();
/* 286 */         localNestedTableColumnHeader.columnResizingStarted(d);
/*     */       }
/* 288 */       paramAnonymousMouseEvent.consume();
/*     */     }
/* 268 */   };
/*     */ 
/* 292 */   private static final EventHandler<MouseEvent> rectMouseDragged = new EventHandler() {
/*     */     public void handle(MouseEvent paramAnonymousMouseEvent) {
/* 294 */       Rectangle localRectangle = (Rectangle)paramAnonymousMouseEvent.getSource();
/* 295 */       TableColumn localTableColumn = (TableColumn)localRectangle.getProperties().get("TableColumn");
/* 296 */       NestedTableColumnHeader localNestedTableColumnHeader = (NestedTableColumnHeader)localRectangle.getProperties().get("TableColumnHeader");
/*     */ 
/* 298 */       if (!localNestedTableColumnHeader.isColumnResizingEnabled()) return;
/*     */ 
/* 300 */       localNestedTableColumnHeader.columnResizing(localTableColumn, paramAnonymousMouseEvent);
/* 301 */       paramAnonymousMouseEvent.consume();
/*     */     }
/* 292 */   };
/*     */ 
/* 305 */   private static final EventHandler<MouseEvent> rectMouseReleased = new EventHandler() {
/*     */     public void handle(MouseEvent paramAnonymousMouseEvent) {
/* 307 */       Rectangle localRectangle = (Rectangle)paramAnonymousMouseEvent.getSource();
/* 308 */       TableColumn localTableColumn = (TableColumn)localRectangle.getProperties().get("TableColumn");
/* 309 */       NestedTableColumnHeader localNestedTableColumnHeader = (NestedTableColumnHeader)localRectangle.getProperties().get("TableColumnHeader");
/*     */ 
/* 311 */       if (!localNestedTableColumnHeader.isColumnResizingEnabled()) return;
/*     */ 
/* 313 */       localNestedTableColumnHeader.columnResizingComplete(localTableColumn, paramAnonymousMouseEvent);
/* 314 */       paramAnonymousMouseEvent.consume();
/*     */     }
/* 305 */   };
/*     */ 
/* 318 */   private static final EventHandler<MouseEvent> rectCursorChangeListener = new EventHandler() {
/*     */     public void handle(MouseEvent paramAnonymousMouseEvent) {
/* 320 */       Rectangle localRectangle = (Rectangle)paramAnonymousMouseEvent.getSource();
/* 321 */       TableColumn localTableColumn = (TableColumn)localRectangle.getProperties().get("TableColumn");
/* 322 */       NestedTableColumnHeader localNestedTableColumnHeader = (NestedTableColumnHeader)localRectangle.getProperties().get("TableColumnHeader");
/*     */ 
/* 324 */       localRectangle.setCursor((localNestedTableColumnHeader.isColumnResizingEnabled()) && (localRectangle.isHover()) && (localTableColumn.isResizable()) ? Cursor.H_RESIZE : Cursor.DEFAULT);
/*     */     }
/* 318 */   };
/*     */ 
/* 370 */   private double lastX = 0.0D;
/*     */ 
/* 372 */   private double dragAnchorX = 0.0D;
/*     */ 
/* 375 */   private List<Rectangle> dragRects = new ArrayList();
/*     */ 
/*     */   NestedTableColumnHeader(TableView paramTableView, TableColumn paramTableColumn)
/*     */   {
/*  64 */     super(paramTableView, paramTableColumn);
/*     */ 
/*  66 */     getStyleClass().setAll(new String[] { "nested-column-header" });
/*  67 */     setFocusTraversable(false);
/*     */ 
/*  69 */     initUI();
/*     */ 
/*  71 */     updateTableColumnHeaders();
/*     */   }
/*     */ 
/*     */   public void setTableHeaderRow(TableHeaderRow paramTableHeaderRow)
/*     */   {
/* 114 */     super.setTableHeaderRow(paramTableHeaderRow);
/*     */ 
/* 116 */     this.label.setTableHeaderRow(paramTableHeaderRow);
/*     */ 
/* 119 */     for (TableColumnHeader localTableColumnHeader : getColumnHeaders())
/* 120 */       localTableColumnHeader.setTableHeaderRow(paramTableHeaderRow);
/*     */   }
/*     */ 
/*     */   public void setParentHeader(NestedTableColumnHeader paramNestedTableColumnHeader)
/*     */   {
/* 125 */     super.setParentHeader(paramNestedTableColumnHeader);
/* 126 */     this.label.setParentHeader(paramNestedTableColumnHeader);
/*     */   }
/*     */ 
/*     */   ObservableList<? extends TableColumn> getColumns()
/*     */   {
/* 134 */     return this.columns;
/*     */   }
/* 136 */   void setColumns(ObservableList<? extends TableColumn> paramObservableList) { if (this.columns != null) {
/* 137 */       this.columns.removeListener(this.weakColumnsListener);
/*     */     }
/*     */ 
/* 140 */     this.columns = paramObservableList;
/*     */ 
/* 142 */     if (this.columns != null)
/* 143 */       this.columns.addListener(this.weakColumnsListener);
/*     */   }
/*     */ 
/*     */   void updateTableColumnHeaders()
/*     */   {
/* 149 */     if ((getTableColumn() == null) && (getTableView() != null))
/* 150 */       setColumns(getTableView().getColumns());
/* 151 */     else if (getTableColumn() != null)
/* 152 */       setColumns(getTableColumn().getColumns());
/*     */     TableColumnHeader localTableColumnHeader1;
/* 158 */     for (int i = 0; i < getColumnHeaders().size(); i++) {
/* 159 */       localTableColumnHeader1 = (TableColumnHeader)getColumnHeaders().get(i);
/* 160 */       localTableColumnHeader1.dispose();
/*     */     }
/*     */     Object localObject1;
/*     */     Object localObject2;
/* 166 */     if (getColumns().isEmpty())
/*     */     {
/* 168 */       localObject1 = getParentHeader();
/* 169 */       if (localObject1 != null) {
/* 170 */         localTableColumnHeader1 = createColumnHeader(getTableColumn());
/* 171 */         localObject2 = ((NestedTableColumnHeader)localObject1).getColumnHeaders();
/* 172 */         int k = ((List)localObject2).indexOf(this);
/* 173 */         if ((k >= 0) && (k < ((List)localObject2).size()))
/* 174 */           ((List)localObject2).set(k, localTableColumnHeader1);
/*     */       }
/*     */     }
/*     */     else {
/* 178 */       localObject1 = new ArrayList();
/*     */ 
/* 180 */       for (int j = 0; j < getColumns().size(); j++) {
/* 181 */         localObject2 = (TableColumn)getColumns().get(j);
/*     */ 
/* 183 */         if (localObject2 != null)
/*     */         {
/* 185 */           TableColumnHeader localTableColumnHeader2 = createColumnHeader((TableColumn)localObject2);
/* 186 */           ((List)localObject1).add(localTableColumnHeader2);
/*     */         }
/*     */       }
/* 189 */       getColumnHeaders().setAll((Collection)localObject1);
/*     */     }
/*     */ 
/* 193 */     updateContent();
/*     */   }
/*     */ 
/*     */   void dispose() {
/* 197 */     super.dispose();
/*     */ 
/* 199 */     if (this.label != null) this.label.dispose();
/*     */ 
/* 201 */     getColumns().removeListener(this.weakColumnsListener);
/*     */ 
/* 203 */     getTableColumn().textProperty().removeListener(this.weakColumnTextListener);
/*     */ 
/* 205 */     getTableView().columnResizePolicyProperty().removeListener(this.weakResizePolicyListener);
/*     */     Object localObject;
/* 207 */     for (int i = 0; i < getColumnHeaders().size(); i++) {
/* 208 */       localObject = (TableColumnHeader)getColumnHeaders().get(i);
/* 209 */       ((TableColumnHeader)localObject).dispose();
/*     */     }
/*     */ 
/* 212 */     for (i = 0; i < this.dragRects.size(); i++) {
/* 213 */       localObject = (Rectangle)this.dragRects.get(i);
/* 214 */       ((Rectangle)localObject).visibleProperty().unbind();
/*     */     }
/* 216 */     this.dragRects.clear();
/* 217 */     getChildren().clear();
/*     */   }
/*     */ 
/*     */   public ObservableList<TableColumnHeader> getColumnHeaders()
/*     */   {
/* 224 */     if (this.columnHeaders == null) this.columnHeaders = FXCollections.observableArrayList();
/* 225 */     return this.columnHeaders;
/*     */   }
/*     */ 
/*     */   private void initUI() {
/* 229 */     this.label = new TableColumnHeader(getTableView(), getTableColumn());
/* 230 */     this.label.setTableHeaderRow(getTableHeaderRow());
/* 231 */     this.label.setParentHeader(getParentHeader());
/* 232 */     this.label.setNestedColumnHeader(this);
/*     */ 
/* 234 */     if (getTableColumn() != null) {
/* 235 */       getTableColumn().textProperty().addListener(this.weakColumnTextListener);
/*     */     }
/*     */ 
/* 238 */     getTableView().columnResizePolicyProperty().addListener(this.weakResizePolicyListener);
/*     */ 
/* 240 */     updateContent();
/*     */   }
/*     */ 
/*     */   private void updateContent()
/*     */   {
/* 246 */     ArrayList localArrayList = new ArrayList();
/*     */ 
/* 249 */     localArrayList.add(this.label);
/*     */ 
/* 252 */     localArrayList.addAll(getColumnHeaders());
/*     */ 
/* 256 */     if (isColumnResizingEnabled()) {
/* 257 */       rebuildDragRects();
/* 258 */       localArrayList.addAll(this.dragRects);
/*     */     }
/*     */ 
/* 261 */     getChildren().setAll(localArrayList);
/* 262 */     requestLayout();
/*     */   }
/*     */ 
/*     */   private void rebuildDragRects()
/*     */   {
/* 331 */     if (!isColumnResizingEnabled()) return;
/*     */ 
/* 333 */     getChildren().removeAll(this.dragRects);
/* 334 */     this.dragRects.clear();
/*     */ 
/* 336 */     if (getColumns() == null) {
/* 337 */       return;
/*     */     }
/*     */ 
/* 340 */     boolean bool = TableView.CONSTRAINED_RESIZE_POLICY.equals(getTableView().getColumnResizePolicy());
/*     */ 
/* 343 */     for (int i = 0; (i < getColumns().size()) && (
/* 344 */       (!bool) || (i != getColumns().size() - 1)); i++)
/*     */     {
/* 348 */       TableColumn localTableColumn = (TableColumn)getColumns().get(i);
/* 349 */       Rectangle localRectangle = new Rectangle();
/* 350 */       localRectangle.getProperties().put("TableColumn", localTableColumn);
/* 351 */       localRectangle.getProperties().put("TableColumnHeader", this);
/* 352 */       localRectangle.setWidth(4.0D);
/* 353 */       localRectangle.setHeight(getHeight() - this.label.getHeight());
/* 354 */       localRectangle.setFill(Color.TRANSPARENT);
/* 355 */       localRectangle.visibleProperty().bind(localTableColumn.visibleProperty());
/* 356 */       localRectangle.setSmooth(false);
/* 357 */       localRectangle.setOnMousePressed(rectMousePressed);
/* 358 */       localRectangle.setOnMouseDragged(rectMouseDragged);
/* 359 */       localRectangle.setOnMouseReleased(rectMouseReleased);
/* 360 */       localRectangle.setOnMouseEntered(rectCursorChangeListener);
/* 361 */       localRectangle.setOnMouseExited(rectCursorChangeListener);
/*     */ 
/* 363 */       this.dragRects.add(localRectangle);
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean isColumnResizingEnabled()
/*     */   {
/* 378 */     return !PlatformUtil.isEmbedded();
/*     */   }
/*     */ 
/*     */   private void columnResizingStarted(double paramDouble) {
/* 382 */     getTableHeaderRow().getColumnReorderLine().setLayoutX(paramDouble);
/*     */   }
/*     */ 
/*     */   private void columnResizing(TableColumn paramTableColumn, MouseEvent paramMouseEvent) {
/* 386 */     double d1 = paramMouseEvent.getSceneX() - this.dragAnchorX;
/* 387 */     double d2 = d1 - this.lastX;
/* 388 */     boolean bool = getTableView().resizeColumn(paramTableColumn, d2);
/* 389 */     if (bool)
/* 390 */       this.lastX = d1;
/*     */   }
/*     */ 
/*     */   private void columnResizingComplete(TableColumn paramTableColumn, MouseEvent paramMouseEvent)
/*     */   {
/* 396 */     getTableHeaderRow().getColumnReorderLine().setTranslateX(0.0D);
/* 397 */     getTableHeaderRow().getColumnReorderLine().setLayoutX(0.0D);
/* 398 */     this.lastX = 0.0D;
/*     */   }
/*     */ 
/*     */   protected void layoutChildren()
/*     */   {
/* 407 */     double d1 = getWidth() - getInsets().getLeft() - getInsets().getRight();
/* 408 */     double d2 = getHeight() - getInsets().getTop() - getInsets().getBottom();
/*     */ 
/* 410 */     int i = (int)this.label.prefHeight(-1.0D);
/*     */ 
/* 412 */     if (this.label.isVisible())
/*     */     {
/* 414 */       this.label.resize(d1, i);
/* 415 */       this.label.relocate(getInsets().getLeft(), getInsets().getTop());
/*     */     }
/*     */ 
/* 419 */     double d3 = getInsets().getLeft();
/* 420 */     int j = 0;
/* 421 */     for (TableColumnHeader localTableColumnHeader : getColumnHeaders())
/* 422 */       if (localTableColumnHeader.isVisible())
/*     */       {
/* 424 */         double d4 = snapSize(localTableColumnHeader.prefWidth(-1.0D));
/*     */ 
/* 428 */         localTableColumnHeader.resize(d4, snapSize(d2 - i));
/* 429 */         localTableColumnHeader.relocate(d3, i + getInsets().getTop());
/*     */ 
/* 441 */         d3 += d4;
/*     */ 
/* 444 */         if ((this.dragRects != null) && (j < this.dragRects.size())) {
/* 445 */           Rectangle localRectangle = (Rectangle)this.dragRects.get(j++);
/* 446 */           localRectangle.setHeight(getHeight() - this.label.getHeight());
/* 447 */           localRectangle.relocate(d3 - 2.0D, getInsets().getTop() + i);
/*     */         }
/*     */       }
/*     */   }
/*     */ 
/*     */   protected double computePrefWidth(double paramDouble)
/*     */   {
/* 454 */     double d = 0.0D;
/*     */ 
/* 456 */     if (getColumns() != null) {
/* 457 */       for (TableColumnHeader localTableColumnHeader : getColumnHeaders()) {
/* 458 */         if (localTableColumnHeader.isVisible()) {
/* 459 */           d += snapSize(localTableColumnHeader.computePrefWidth(paramDouble));
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 464 */     return d;
/*     */   }
/*     */ 
/*     */   protected double computePrefHeight(double paramDouble) {
/* 468 */     double d = 0.0D;
/*     */ 
/* 470 */     if (getColumnHeaders() != null) {
/* 471 */       for (TableColumnHeader localTableColumnHeader : getColumnHeaders()) {
/* 472 */         d = Math.max(d, localTableColumnHeader.prefHeight(-1.0D));
/*     */       }
/*     */     }
/*     */ 
/* 476 */     return d + this.label.prefHeight(-1.0D) + getInsets().getTop() + getInsets().getBottom();
/*     */   }
/*     */ 
/*     */   private TableColumnHeader createColumnHeader(TableColumn paramTableColumn) {
/* 480 */     NestedTableColumnHeader localNestedTableColumnHeader = paramTableColumn.getColumns().isEmpty() ? new TableColumnHeader(getTableView(), paramTableColumn) : new NestedTableColumnHeader(getTableView(), paramTableColumn);
/*     */ 
/* 484 */     localNestedTableColumnHeader.setTableHeaderRow(getTableHeaderRow());
/* 485 */     localNestedTableColumnHeader.setParentHeader(this);
/*     */ 
/* 487 */     return localNestedTableColumnHeader;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.NestedTableColumnHeader
 * JD-Core Version:    0.6.2
 */