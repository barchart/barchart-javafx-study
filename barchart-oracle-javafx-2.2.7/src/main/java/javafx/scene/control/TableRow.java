/*     */ package javafx.scene.control;
/*     */ 
/*     */ import com.sun.javafx.scene.control.WeakListChangeListener;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.List;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.Observable;
/*     */ import javafx.beans.WeakInvalidationListener;
/*     */ import javafx.beans.property.ReadOnlyIntegerProperty;
/*     */ import javafx.beans.property.ReadOnlyObjectProperty;
/*     */ import javafx.beans.property.ReadOnlyObjectWrapper;
/*     */ import javafx.collections.ListChangeListener;
/*     */ import javafx.collections.ListChangeListener.Change;
/*     */ import javafx.collections.ObservableList;
/*     */ 
/*     */ public class TableRow<T> extends IndexedCell<T>
/*     */ {
/*  83 */   private InvalidationListener indexInvalidationListener = new InvalidationListener() {
/*     */     public void invalidated(Observable paramAnonymousObservable) {
/*  85 */       TableRow.this.indexChanged();
/*  86 */       TableRow.this.updateSelection();
/*  87 */       TableRow.this.updateFocus();
/*     */     }
/*  83 */   };
/*     */ 
/* 108 */   private ListChangeListener<TablePosition> selectedListener = new ListChangeListener()
/*     */   {
/*     */     public void onChanged(ListChangeListener.Change<? extends TablePosition> paramAnonymousChange) {
/* 111 */       TableRow.this.updateSelection();
/*     */     }
/* 108 */   };
/*     */ 
/* 116 */   private final InvalidationListener focusedListener = new InvalidationListener() {
/*     */     public void invalidated(Observable paramAnonymousObservable) {
/* 118 */       TableRow.this.updateFocus();
/*     */     }
/* 116 */   };
/*     */ 
/* 123 */   private final InvalidationListener editingListener = new InvalidationListener() {
/*     */     public void invalidated(Observable paramAnonymousObservable) {
/* 125 */       TableRow.this.updateEditing();
/*     */     }
/* 123 */   };
/*     */ 
/* 129 */   private final WeakListChangeListener weakSelectedListener = new WeakListChangeListener(this.selectedListener);
/* 130 */   private final WeakInvalidationListener weakFocusedListener = new WeakInvalidationListener(this.focusedListener);
/* 131 */   private final WeakInvalidationListener weakEditingListener = new WeakInvalidationListener(this.editingListener);
/*     */   private ReadOnlyObjectWrapper<TableView<T>> tableView;
/*     */   private static final String DEFAULT_STYLE_CLASS = "table-row-cell";
/*     */ 
/*     */   public TableRow()
/*     */   {
/*  70 */     getStyleClass().addAll(new String[] { "table-row-cell" });
/*     */ 
/*  72 */     indexProperty().addListener(this.indexInvalidationListener);
/*     */   }
/*     */ 
/*     */   void indexChanged()
/*     */   {
/*  92 */     updateItem();
/*     */   }
/*     */ 
/*     */   private void setTableView(TableView<T> paramTableView)
/*     */   {
/* 142 */     tableViewPropertyImpl().set(paramTableView);
/*     */   }
/*     */ 
/*     */   public final TableView<T> getTableView() {
/* 146 */     return this.tableView == null ? null : (TableView)this.tableView.get();
/*     */   }
/*     */ 
/*     */   public final ReadOnlyObjectProperty<TableView<T>> tableViewProperty()
/*     */   {
/* 153 */     return tableViewPropertyImpl().getReadOnlyProperty();
/*     */   }
/*     */ 
/*     */   private ReadOnlyObjectWrapper<TableView<T>> tableViewPropertyImpl() {
/* 157 */     if (this.tableView == null) {
/* 158 */       this.tableView = new ReadOnlyObjectWrapper()
/*     */       {
/*     */         private WeakReference<TableView<T>> weakTableViewRef;
/*     */ 
/*     */         protected void invalidated()
/*     */         {
/*     */           TableView.TableViewSelectionModel localTableViewSelectionModel;
/*     */           TableView.TableViewFocusModel localTableViewFocusModel;
/* 164 */           if (this.weakTableViewRef != null) {
/* 165 */             TableView localTableView = (TableView)this.weakTableViewRef.get();
/* 166 */             if (localTableView != null) {
/* 167 */               localTableViewSelectionModel = localTableView.getSelectionModel();
/* 168 */               if (localTableViewSelectionModel != null) {
/* 169 */                 localTableViewSelectionModel.getSelectedCells().removeListener(TableRow.this.weakSelectedListener);
/*     */               }
/*     */ 
/* 172 */               localTableViewFocusModel = localTableView.getFocusModel();
/* 173 */               if (localTableViewFocusModel != null) {
/* 174 */                 localTableViewFocusModel.focusedCellProperty().removeListener(TableRow.this.weakFocusedListener);
/*     */               }
/*     */ 
/* 177 */               localTableView.editingCellProperty().removeListener(TableRow.this.weakEditingListener);
/*     */             }
/*     */ 
/* 180 */             this.weakTableViewRef = null;
/*     */           }
/*     */ 
/* 183 */           if (TableRow.this.getTableView() != null) {
/* 184 */             localTableViewSelectionModel = TableRow.this.getTableView().getSelectionModel();
/* 185 */             if (localTableViewSelectionModel != null) {
/* 186 */               localTableViewSelectionModel.getSelectedCells().addListener(TableRow.this.weakSelectedListener);
/*     */             }
/*     */ 
/* 189 */             localTableViewFocusModel = TableRow.this.getTableView().getFocusModel();
/* 190 */             if (localTableViewFocusModel != null) {
/* 191 */               localTableViewFocusModel.focusedCellProperty().addListener(TableRow.this.weakFocusedListener);
/*     */             }
/*     */ 
/* 194 */             TableRow.this.getTableView().editingCellProperty().addListener(TableRow.this.weakEditingListener);
/*     */ 
/* 196 */             this.weakTableViewRef = new WeakReference(get());
/*     */           }
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 202 */           return TableRow.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 207 */           return "tableView";
/*     */         }
/*     */       };
/*     */     }
/* 211 */     return this.tableView;
/*     */   }
/*     */ 
/*     */   private void updateItem()
/*     */   {
/* 231 */     TableView localTableView = getTableView();
/* 232 */     if ((localTableView == null) || (localTableView.getItems() == null)) return;
/*     */ 
/* 234 */     ObservableList localObservableList = localTableView.getItems();
/*     */ 
/* 237 */     int i = (getIndex() >= 0) && (getIndex() < localObservableList.size()) ? 1 : 0;
/*     */ 
/* 240 */     if (i != 0) {
/* 241 */       Object localObject = localObservableList.get(getIndex());
/* 242 */       if ((localObject == null) || (!localObject.equals(getItem())))
/* 243 */         updateItem(localObject, false);
/*     */     }
/*     */     else {
/* 246 */       updateItem(null, true);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void updateSelection()
/*     */   {
/* 259 */     if (getIndex() == -1) return;
/*     */ 
/* 261 */     TableView localTableView = getTableView();
/* 262 */     boolean bool = (localTableView != null) && (localTableView.getSelectionModel() != null) && (!localTableView.getSelectionModel().isCellSelectionEnabled()) && (localTableView.getSelectionModel().isSelected(getIndex()));
/*     */ 
/* 267 */     updateSelected(bool);
/*     */   }
/*     */ 
/*     */   private void updateFocus() {
/* 271 */     if (getIndex() == -1) return;
/*     */ 
/* 273 */     TableView localTableView = getTableView();
/* 274 */     boolean bool = (localTableView != null) && (localTableView.getSelectionModel() != null) && (!localTableView.getSelectionModel().isCellSelectionEnabled()) && (localTableView.getFocusModel() != null) && (localTableView.getFocusModel().isFocused(getIndex()));
/*     */ 
/* 280 */     setFocused(bool);
/*     */   }
/*     */ 
/*     */   private void updateEditing() {
/* 284 */     if (getIndex() == -1) return;
/*     */ 
/* 286 */     TableView localTableView = getTableView();
/* 287 */     if (localTableView == null) return;
/*     */ 
/* 289 */     TableView.TableViewSelectionModel localTableViewSelectionModel = getTableView().getSelectionModel();
/* 290 */     if ((localTableViewSelectionModel == null) || (localTableViewSelectionModel.isCellSelectionEnabled())) return;
/*     */ 
/* 292 */     TablePosition localTablePosition = localTableView.getEditingCell();
/* 293 */     int i = localTablePosition.getRow() == getIndex() ? 1 : 0;
/*     */ 
/* 295 */     if ((!isEditing()) && (i != 0))
/* 296 */       startEdit();
/* 297 */     else if ((isEditing()) && (i == 0))
/* 298 */       cancelEdit();
/*     */   }
/*     */ 
/*     */   public final void updateTableView(TableView<T> paramTableView)
/*     */   {
/* 319 */     setTableView(paramTableView);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.TableRow
 * JD-Core Version:    0.6.2
 */