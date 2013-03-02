/*     */ package javafx.scene.control;
/*     */ 
/*     */ import com.sun.javafx.property.PropertyReference;
/*     */ import com.sun.javafx.scene.control.WeakListChangeListener;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.Map;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.Observable;
/*     */ import javafx.beans.WeakInvalidationListener;
/*     */ import javafx.beans.property.ReadOnlyIntegerProperty;
/*     */ import javafx.beans.property.ReadOnlyObjectProperty;
/*     */ import javafx.beans.property.ReadOnlyObjectWrapper;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ import javafx.collections.ListChangeListener;
/*     */ import javafx.collections.ListChangeListener.Change;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.event.Event;
/*     */ 
/*     */ public class TableCell<S, T> extends IndexedCell<T>
/*     */ {
/*  89 */   private boolean itemDirty = false;
/*     */ 
/*  91 */   private InvalidationListener indexListener = new InvalidationListener() {
/*     */     public void invalidated(Observable paramAnonymousObservable) {
/*  93 */       TableCell.this.indexChanged();
/*  94 */       TableCell.this.updateSelection();
/*  95 */       TableCell.this.updateFocus();
/*     */     }
/*  91 */   };
/*     */ 
/* 114 */   private ListChangeListener<TablePosition> selectedListener = new ListChangeListener() {
/*     */     public void onChanged(ListChangeListener.Change<? extends TablePosition> paramAnonymousChange) {
/* 116 */       TableCell.this.updateSelection();
/*     */     }
/* 114 */   };
/*     */ 
/* 121 */   private final InvalidationListener focusedListener = new InvalidationListener() {
/*     */     public void invalidated(Observable paramAnonymousObservable) {
/* 123 */       TableCell.this.updateFocus();
/*     */     }
/* 121 */   };
/*     */ 
/* 128 */   private final InvalidationListener tableRowUpdateObserver = new InvalidationListener() {
/*     */     public void invalidated(Observable paramAnonymousObservable) {
/* 130 */       TableCell.this.itemDirty = true;
/* 131 */       TableCell.this.requestLayout();
/*     */     }
/* 128 */   };
/*     */ 
/* 135 */   private final InvalidationListener editingListener = new InvalidationListener() {
/*     */     public void invalidated(Observable paramAnonymousObservable) {
/* 137 */       TableCell.this.updateEditing();
/*     */     }
/* 135 */   };
/*     */ 
/* 141 */   private ListChangeListener<TableColumn<S, T>> visibleLeafColumnsListener = new ListChangeListener() {
/*     */     public void onChanged(ListChangeListener.Change<? extends TableColumn<S, T>> paramAnonymousChange) {
/* 143 */       TableCell.this.updateColumnIndex();
/*     */     }
/* 141 */   };
/*     */ 
/* 147 */   private final WeakListChangeListener weakSelectedListener = new WeakListChangeListener(this.selectedListener);
/*     */ 
/* 149 */   private final WeakInvalidationListener weakFocusedListener = new WeakInvalidationListener(this.focusedListener);
/*     */ 
/* 151 */   private final WeakInvalidationListener weaktableRowUpdateObserver = new WeakInvalidationListener(this.tableRowUpdateObserver);
/*     */ 
/* 153 */   private final WeakInvalidationListener weakEditingListener = new WeakInvalidationListener(this.editingListener);
/*     */ 
/* 155 */   private final WeakListChangeListener weakVisibleLeafColumnsListener = new WeakListChangeListener(this.visibleLeafColumnsListener);
/*     */ 
/* 166 */   private ReadOnlyObjectWrapper<TableColumn<S, T>> tableColumn = new ReadOnlyObjectWrapper() {
/*     */     protected void invalidated() {
/* 168 */       TableCell.this.updateColumnIndex();
/*     */     }
/*     */ 
/*     */     public Object getBean() {
/* 172 */       return TableCell.this;
/*     */     }
/*     */ 
/*     */     public String getName() {
/* 176 */       return "tableColumn";
/*     */     }
/* 166 */   };
/*     */   private ReadOnlyObjectWrapper<TableView<S>> tableView;
/* 268 */   private ReadOnlyObjectWrapper<TableRow> tableRow = new ReadOnlyObjectWrapper(this, "tableRow");
/*     */   private Map<String, PropertyReference> observablePropertyReferences;
/* 398 */   private boolean isLastVisibleColumn = false;
/* 399 */   private int columnIndex = -1;
/*     */ 
/* 469 */   private boolean updateEditingIndex = true;
/*     */ 
/* 488 */   private ObservableValue<T> currentObservableValue = null;
/*     */   private static final String DEFAULT_STYLE_CLASS = "table-cell";
/*     */   private static final String PSEUDO_CLASS_LAST_VISIBLE = "last-visible";
/*     */ 
/*     */   public TableCell()
/*     */   {
/*  72 */     getStyleClass().addAll(new String[] { "table-cell" });
/*     */ 
/*  74 */     updateColumnIndex();
/*     */ 
/*  78 */     indexProperty().addListener(this.indexListener);
/*     */   }
/*     */ 
/*     */   void indexChanged()
/*     */   {
/* 105 */     updateItem();
/*     */   }
/*     */ 
/*     */   public final ReadOnlyObjectProperty<TableColumn<S, T>> tableColumnProperty()
/*     */   {
/* 182 */     return this.tableColumn.getReadOnlyProperty(); } 
/* 183 */   private void setTableColumn(TableColumn<S, T> paramTableColumn) { this.tableColumn.set(paramTableColumn); } 
/* 184 */   public final TableColumn<S, T> getTableColumn() { return (TableColumn)this.tableColumn.get(); }
/*     */ 
/*     */ 
/*     */   private void setTableView(TableView<S> paramTableView)
/*     */   {
/* 190 */     tableViewPropertyImpl().set(paramTableView);
/*     */   }
/*     */   public final TableView<S> getTableView() {
/* 193 */     return this.tableView == null ? null : (TableView)this.tableView.get();
/*     */   }
/*     */ 
/*     */   public final ReadOnlyObjectProperty<TableView<S>> tableViewProperty()
/*     */   {
/* 200 */     return tableViewPropertyImpl().getReadOnlyProperty();
/*     */   }
/*     */ 
/*     */   private ReadOnlyObjectWrapper<TableView<S>> tableViewPropertyImpl() {
/* 204 */     if (this.tableView == null) {
/* 205 */       this.tableView = new ReadOnlyObjectWrapper()
/*     */       {
/*     */         private WeakReference<TableView<S>> weakTableViewRef;
/*     */ 
/*     */         protected void invalidated()
/*     */         {
/*     */           TableView.TableViewSelectionModel localTableViewSelectionModel;
/*     */           TableView.TableViewFocusModel localTableViewFocusModel;
/* 211 */           if (this.weakTableViewRef != null) {
/* 212 */             TableView localTableView = (TableView)this.weakTableViewRef.get();
/* 213 */             if (localTableView != null) {
/* 214 */               localTableViewSelectionModel = localTableView.getSelectionModel();
/* 215 */               if (localTableViewSelectionModel != null) {
/* 216 */                 localTableViewSelectionModel.getSelectedCells().removeListener(TableCell.this.weakSelectedListener);
/*     */               }
/*     */ 
/* 219 */               localTableViewFocusModel = localTableView.getFocusModel();
/* 220 */               if (localTableViewFocusModel != null) {
/* 221 */                 localTableViewFocusModel.focusedCellProperty().removeListener(TableCell.this.weakFocusedListener);
/*     */               }
/*     */ 
/* 224 */               localTableView.editingCellProperty().removeListener(TableCell.this.weakEditingListener);
/* 225 */               localTableView.getVisibleLeafColumns().removeListener(TableCell.this.weakVisibleLeafColumnsListener);
/*     */             }
/*     */           }
/*     */ 
/* 229 */           if (get() != null) {
/* 230 */             localTableViewSelectionModel = ((TableView)get()).getSelectionModel();
/* 231 */             if (localTableViewSelectionModel != null) {
/* 232 */               localTableViewSelectionModel.getSelectedCells().addListener(TableCell.this.weakSelectedListener);
/*     */             }
/*     */ 
/* 235 */             localTableViewFocusModel = ((TableView)get()).getFocusModel();
/* 236 */             if (localTableViewFocusModel != null) {
/* 237 */               localTableViewFocusModel.focusedCellProperty().addListener(TableCell.this.weakFocusedListener);
/*     */             }
/*     */ 
/* 240 */             ((TableView)get()).editingCellProperty().addListener(TableCell.this.weakEditingListener);
/* 241 */             ((TableView)get()).getVisibleLeafColumns().addListener(TableCell.this.weakVisibleLeafColumnsListener);
/*     */ 
/* 243 */             this.weakTableViewRef = new WeakReference(get());
/*     */           }
/*     */ 
/* 246 */           TableCell.this.updateColumnIndex();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 251 */           return TableCell.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 256 */           return "tableView";
/*     */         }
/*     */       };
/*     */     }
/* 260 */     return this.tableView;
/*     */   }
/*     */ 
/*     */   private void setTableRow(TableRow paramTableRow)
/*     */   {
/* 269 */     this.tableRow.set(paramTableRow); } 
/* 270 */   public final TableRow getTableRow() { return (TableRow)this.tableRow.get(); } 
/* 271 */   public final ReadOnlyObjectProperty<TableRow> tableRowProperty() { return this.tableRow; }
/*     */ 
/*     */ 
/*     */   public void startEdit()
/*     */   {
/* 283 */     TableView localTableView = getTableView();
/* 284 */     TableColumn localTableColumn = getTableColumn();
/* 285 */     if ((!isEditable()) || ((localTableView != null) && (!localTableView.isEditable())) || ((localTableColumn != null) && (!getTableColumn().isEditable())))
/*     */     {
/* 296 */       return;
/*     */     }
/*     */ 
/* 302 */     super.startEdit();
/*     */ 
/* 304 */     if (localTableColumn != null) {
/* 305 */       TableColumn.CellEditEvent localCellEditEvent = new TableColumn.CellEditEvent(localTableView, localTableView.getEditingCell(), TableColumn.editStartEvent(), null);
/*     */ 
/* 312 */       Event.fireEvent(localTableColumn, localCellEditEvent);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void commitEdit(T paramT)
/*     */   {
/* 318 */     if (!isEditing()) return;
/*     */ 
/* 320 */     TableView localTableView = getTableView();
/* 321 */     if (localTableView != null)
/*     */     {
/* 323 */       TableColumn.CellEditEvent localCellEditEvent = new TableColumn.CellEditEvent(localTableView, localTableView.getEditingCell(), TableColumn.editCommitEvent(), paramT);
/*     */ 
/* 330 */       Event.fireEvent(getTableColumn(), localCellEditEvent);
/*     */     }
/*     */ 
/* 334 */     updateItem(paramT, false);
/*     */ 
/* 338 */     super.commitEdit(paramT);
/*     */ 
/* 340 */     if (localTableView != null)
/*     */     {
/* 342 */       localTableView.edit(-1, null);
/* 343 */       localTableView.requestFocus();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void cancelEdit()
/*     */   {
/* 349 */     if (!isEditing()) return;
/*     */ 
/* 351 */     TableView localTableView = getTableView();
/*     */ 
/* 353 */     super.cancelEdit();
/*     */ 
/* 356 */     if (localTableView != null) {
/* 357 */       TablePosition localTablePosition = localTableView.getEditingCell();
/* 358 */       if (this.updateEditingIndex) localTableView.edit(-1, null);
/*     */ 
/* 360 */       TableColumn.CellEditEvent localCellEditEvent = new TableColumn.CellEditEvent(localTableView, localTablePosition, TableColumn.editCancelEvent(), null);
/*     */ 
/* 367 */       Event.fireEvent(getTableColumn(), localCellEditEvent);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void updateSelected(boolean paramBoolean)
/*     */   {
/* 385 */     if ((getTableRow() == null) || (getTableRow().isEmpty())) return;
/* 386 */     setSelected(paramBoolean);
/*     */   }
/*     */ 
/*     */   private void updateColumnIndex()
/*     */   {
/* 402 */     TableView localTableView = getTableView();
/* 403 */     TableColumn localTableColumn = getTableColumn();
/* 404 */     this.columnIndex = ((localTableView == null) || (localTableColumn == null) ? -1 : localTableView.getVisibleLeafIndex(localTableColumn));
/*     */ 
/* 408 */     boolean bool = this.isLastVisibleColumn;
/* 409 */     this.isLastVisibleColumn = ((getTableColumn() != null) && (this.columnIndex != -1) && (this.columnIndex == getTableView().getVisibleLeafColumns().size() - 1));
/*     */ 
/* 412 */     if (bool != this.isLastVisibleColumn)
/* 413 */       impl_pseudoClassStateChanged("last-visible");
/*     */   }
/*     */ 
/*     */   private void updateSelection()
/*     */   {
/* 427 */     if (isEmpty()) return;
/* 428 */     if ((getIndex() == -1) || (getTableView() == null)) return;
/* 429 */     if (getTableView().getSelectionModel() == null) return;
/*     */ 
/* 431 */     boolean bool = (isInCellSelectionMode()) && (getTableView().getSelectionModel().isSelected(getIndex(), getTableColumn()));
/*     */ 
/* 433 */     if (isSelected() == bool) return;
/*     */ 
/* 435 */     updateSelected(bool);
/*     */   }
/*     */ 
/*     */   private void updateFocus() {
/* 439 */     if ((getIndex() == -1) || (getTableView() == null)) return;
/* 440 */     if (getTableView().getFocusModel() == null) return;
/*     */ 
/* 442 */     boolean bool = (isInCellSelectionMode()) && (getTableView().getFocusModel() != null) && (getTableView().getFocusModel().isFocused(getIndex(), getTableColumn()));
/*     */ 
/* 446 */     setFocused(bool);
/*     */   }
/*     */ 
/*     */   private void updateEditing() {
/* 450 */     if ((getIndex() == -1) || (getTableView() == null)) return;
/*     */ 
/* 452 */     TablePosition localTablePosition = getTableView().getEditingCell();
/* 453 */     boolean bool = match(localTablePosition);
/*     */ 
/* 455 */     if ((bool) && (!isEditing())) {
/* 456 */       startEdit();
/* 457 */     } else if ((!bool) && (isEditing()))
/*     */     {
/* 464 */       this.updateEditingIndex = false;
/* 465 */       cancelEdit();
/* 466 */       this.updateEditingIndex = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean match(TablePosition paramTablePosition)
/*     */   {
/* 472 */     return (paramTablePosition != null) && (paramTablePosition.getRow() == getIndex()) && (paramTablePosition.getTableColumn() == getTableColumn());
/*     */   }
/*     */ 
/*     */   private boolean isInCellSelectionMode() {
/* 476 */     return (getTableView() != null) && (getTableView().getSelectionModel() != null) && (getTableView().getSelectionModel().isCellSelectionEnabled());
/*     */   }
/*     */ 
/*     */   private void updateItem()
/*     */   {
/* 521 */     if (this.currentObservableValue != null) {
/* 522 */       this.currentObservableValue.removeListener(this.weaktableRowUpdateObserver);
/*     */     }
/*     */ 
/* 526 */     if ((getIndex() < 0) || (this.columnIndex < 0) || (!isVisible()) || (getTableColumn() == null) || (!getTableColumn().isVisible()) || (getTableView().getItems() == null))
/*     */     {
/* 532 */       return;
/*     */     }
/*     */ 
/* 536 */     int i = getTableView().getItems().size();
/*     */ 
/* 538 */     if (getIndex() >= i) {
/* 539 */       updateItem(null, true);
/* 540 */       return;
/*     */     }
/* 542 */     if (getIndex() < i) {
/* 543 */       this.currentObservableValue = getTableColumn().getCellObservableValue(getIndex());
/*     */     }
/*     */ 
/* 546 */     Object localObject = this.currentObservableValue == null ? null : this.currentObservableValue.getValue();
/*     */ 
/* 549 */     updateItem(localObject, localObject == null);
/*     */ 
/* 552 */     if (this.currentObservableValue == null) {
/* 553 */       return;
/*     */     }
/*     */ 
/* 557 */     this.currentObservableValue.addListener(this.weaktableRowUpdateObserver);
/*     */   }
/*     */ 
/*     */   protected void layoutChildren() {
/* 561 */     if (this.itemDirty) {
/* 562 */       updateItem();
/* 563 */       this.itemDirty = false;
/*     */     }
/* 565 */     super.layoutChildren();
/*     */   }
/*     */ 
/*     */   public final void updateTableView(TableView paramTableView)
/*     */   {
/* 586 */     setTableView(paramTableView);
/*     */   }
/*     */ 
/*     */   public final void updateTableRow(TableRow paramTableRow)
/*     */   {
/* 597 */     setTableRow(paramTableRow);
/*     */   }
/*     */ 
/*     */   public final void updateTableColumn(TableColumn paramTableColumn)
/*     */   {
/* 608 */     setTableColumn(paramTableColumn);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.TableCell
 * JD-Core Version:    0.6.2
 */