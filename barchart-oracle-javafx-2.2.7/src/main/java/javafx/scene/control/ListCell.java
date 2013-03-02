/*     */ package javafx.scene.control;
/*     */ 
/*     */ import com.sun.javafx.scene.control.WeakListChangeListener;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.List;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.Observable;
/*     */ import javafx.beans.WeakInvalidationListener;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.ReadOnlyIntegerProperty;
/*     */ import javafx.beans.property.ReadOnlyObjectProperty;
/*     */ import javafx.beans.property.ReadOnlyObjectWrapper;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ import javafx.beans.value.WeakChangeListener;
/*     */ import javafx.collections.ListChangeListener;
/*     */ import javafx.collections.ListChangeListener.Change;
/*     */ import javafx.collections.ObservableList;
/*     */ 
/*     */ public class ListCell<T> extends IndexedCell<T>
/*     */ {
/* 106 */   private InvalidationListener indexListener = new InvalidationListener() {
/*     */     public void invalidated(Observable paramAnonymousObservable) {
/* 108 */       ListCell.this.indexChanged();
/*     */     }
/* 106 */   };
/*     */ 
/* 124 */   private final InvalidationListener editingListener = new InvalidationListener() {
/*     */     public void invalidated(Observable paramAnonymousObservable) {
/* 126 */       int i = ListCell.this.getIndex();
/* 127 */       ListView localListView = ListCell.this.getListView();
/* 128 */       int j = localListView == null ? -1 : localListView.getEditingIndex();
/* 129 */       boolean bool = ListCell.this.isEditing();
/*     */ 
/* 132 */       if ((i != -1) && (localListView != null))
/*     */       {
/* 135 */         if ((i == j) && (!bool)) {
/* 136 */           ListCell.this.startEdit();
/* 137 */         } else if ((i != j) && (bool))
/*     */         {
/* 144 */           ListCell.this.updateEditingIndex = false;
/* 145 */           ListCell.this.cancelEdit();
/* 146 */           ListCell.this.updateEditingIndex = true;
/*     */         }
/*     */       }
/*     */     }
/* 124 */   };
/*     */ 
/* 151 */   private boolean updateEditingIndex = true;
/*     */ 
/* 157 */   private final ListChangeListener selectedListener = new ListChangeListener() {
/*     */     public void onChanged(ListChangeListener.Change paramAnonymousChange) {
/* 159 */       ListCell.this.updateSelection();
/*     */     }
/* 157 */   };
/*     */ 
/* 167 */   private final ChangeListener selectionModelPropertyListener = new ChangeListener()
/*     */   {
/*     */     public void changed(ObservableValue paramAnonymousObservableValue, MultipleSelectionModel paramAnonymousMultipleSelectionModel1, MultipleSelectionModel paramAnonymousMultipleSelectionModel2)
/*     */     {
/* 171 */       if (paramAnonymousMultipleSelectionModel1 != null) {
/* 172 */         paramAnonymousMultipleSelectionModel1.getSelectedIndices().removeListener(ListCell.this.weakSelectedListener);
/*     */       }
/* 174 */       if (paramAnonymousMultipleSelectionModel2 != null) {
/* 175 */         paramAnonymousMultipleSelectionModel2.getSelectedIndices().addListener(ListCell.this.weakSelectedListener);
/*     */       }
/* 177 */       ListCell.this.updateSelection();
/*     */     }
/* 167 */   };
/*     */ 
/* 185 */   private final ListChangeListener itemsListener = new ListChangeListener() {
/*     */     public void onChanged(ListChangeListener.Change paramAnonymousChange) {
/* 187 */       ListCell.this.updateItem();
/*     */     }
/* 185 */   };
/*     */ 
/* 195 */   private final ChangeListener itemsPropertyListener = new ChangeListener()
/*     */   {
/*     */     public void changed(ObservableValue paramAnonymousObservableValue, ObservableList paramAnonymousObservableList1, ObservableList paramAnonymousObservableList2)
/*     */     {
/* 199 */       if (paramAnonymousObservableList1 != null) {
/* 200 */         paramAnonymousObservableList1.removeListener(ListCell.this.weakItemsListener);
/*     */       }
/* 202 */       if (paramAnonymousObservableList2 != null) {
/* 203 */         paramAnonymousObservableList2.addListener(ListCell.this.weakItemsListener);
/*     */       }
/* 205 */       ListCell.this.updateItem();
/*     */     }
/* 195 */   };
/*     */ 
/* 213 */   private final InvalidationListener focusedListener = new InvalidationListener() {
/*     */     public void invalidated(Observable paramAnonymousObservable) {
/* 215 */       ListCell.this.updateFocus();
/*     */     }
/* 213 */   };
/*     */ 
/* 223 */   private final ChangeListener focusModelPropertyListener = new ChangeListener()
/*     */   {
/*     */     public void changed(ObservableValue paramAnonymousObservableValue, FocusModel paramAnonymousFocusModel1, FocusModel paramAnonymousFocusModel2)
/*     */     {
/* 227 */       if (paramAnonymousFocusModel1 != null) {
/* 228 */         paramAnonymousFocusModel1.focusedIndexProperty().removeListener(ListCell.this.weakFocusedListener);
/*     */       }
/* 230 */       if (paramAnonymousFocusModel2 != null) {
/* 231 */         paramAnonymousFocusModel2.focusedIndexProperty().addListener(ListCell.this.weakFocusedListener);
/*     */       }
/* 233 */       ListCell.this.updateFocus();
/*     */     }
/* 223 */   };
/*     */ 
/* 238 */   private final WeakInvalidationListener weakEditingListener = new WeakInvalidationListener(this.editingListener);
/* 239 */   private final WeakListChangeListener weakSelectedListener = new WeakListChangeListener(this.selectedListener);
/* 240 */   private final WeakChangeListener weakSelectionModelPropertyListener = new WeakChangeListener(this.selectionModelPropertyListener);
/* 241 */   private final WeakListChangeListener weakItemsListener = new WeakListChangeListener(this.itemsListener);
/* 242 */   private final WeakChangeListener weakItemsPropertyListener = new WeakChangeListener(this.itemsPropertyListener);
/* 243 */   private final WeakInvalidationListener weakFocusedListener = new WeakInvalidationListener(this.focusedListener);
/* 244 */   private final WeakChangeListener weakFocusModelPropertyListener = new WeakChangeListener(this.focusModelPropertyListener);
/*     */ 
/* 255 */   private ReadOnlyObjectWrapper<ListView<T>> listView = new ReadOnlyObjectWrapper(this, "listView")
/*     */   {
/* 259 */     private WeakReference<ListView<T>> weakListViewRef = new WeakReference(null);
/*     */ 
/*     */     protected void invalidated()
/*     */     {
/* 263 */       ListView localListView1 = (ListView)get();
/* 264 */       ListView localListView2 = (ListView)this.weakListViewRef.get();
/*     */ 
/* 268 */       if (localListView1 == localListView2)
/*     */         return;
/*     */       MultipleSelectionModel localMultipleSelectionModel;
/*     */       FocusModel localFocusModel;
/*     */       ObservableList localObservableList;
/* 271 */       if (localListView2 != null)
/*     */       {
/* 273 */         localMultipleSelectionModel = localListView2.getSelectionModel();
/* 274 */         if (localMultipleSelectionModel != null) {
/* 275 */           localMultipleSelectionModel.getSelectedIndices().removeListener(ListCell.this.weakSelectedListener);
/*     */         }
/*     */ 
/* 279 */         localFocusModel = localListView2.getFocusModel();
/* 280 */         if (localFocusModel != null) {
/* 281 */           localFocusModel.focusedIndexProperty().removeListener(ListCell.this.weakFocusedListener);
/*     */         }
/*     */ 
/* 285 */         localObservableList = localListView2.getItems();
/* 286 */         if (localObservableList != null) {
/* 287 */           localObservableList.removeListener(ListCell.this.weakItemsListener);
/*     */         }
/*     */ 
/* 291 */         localListView2.editingIndexProperty().removeListener(ListCell.this.weakEditingListener);
/* 292 */         localListView2.itemsProperty().removeListener(ListCell.this.weakItemsPropertyListener);
/* 293 */         localListView2.focusModelProperty().removeListener(ListCell.this.weakFocusModelPropertyListener);
/* 294 */         localListView2.selectionModelProperty().removeListener(ListCell.this.weakSelectionModelPropertyListener);
/*     */       }
/*     */ 
/* 297 */       if (localListView1 != null) {
/* 298 */         localMultipleSelectionModel = localListView1.getSelectionModel();
/* 299 */         if (localMultipleSelectionModel != null) {
/* 300 */           localMultipleSelectionModel.getSelectedIndices().addListener(ListCell.this.weakSelectedListener);
/*     */         }
/*     */ 
/* 303 */         localFocusModel = localListView1.getFocusModel();
/* 304 */         if (localFocusModel != null) {
/* 305 */           localFocusModel.focusedIndexProperty().addListener(ListCell.this.weakFocusedListener);
/*     */         }
/*     */ 
/* 308 */         localObservableList = localListView1.getItems();
/* 309 */         if (localObservableList != null) {
/* 310 */           localObservableList.addListener(ListCell.this.weakItemsListener);
/*     */         }
/*     */ 
/* 313 */         localListView1.editingIndexProperty().addListener(ListCell.this.weakEditingListener);
/* 314 */         localListView1.itemsProperty().addListener(ListCell.this.weakItemsPropertyListener);
/* 315 */         localListView1.focusModelProperty().addListener(ListCell.this.weakFocusModelPropertyListener);
/* 316 */         localListView1.selectionModelProperty().addListener(ListCell.this.weakSelectionModelPropertyListener);
/*     */ 
/* 318 */         this.weakListViewRef = new WeakReference(localListView1);
/*     */       }
/*     */ 
/* 321 */       ListCell.this.updateItem();
/* 322 */       ListCell.this.updateSelection();
/* 323 */       ListCell.this.updateFocus();
/* 324 */       ListCell.this.requestLayout();
/*     */     }
/* 255 */   };
/*     */   private static final String DEFAULT_STYLE_CLASS = "list-cell";
/*     */ 
/*     */   public ListCell()
/*     */   {
/*  84 */     getStyleClass().addAll(new String[] { "list-cell" });
/*  85 */     indexProperty().addListener(this.indexListener);
/*     */   }
/*     */ 
/*     */   void indexChanged()
/*     */   {
/* 113 */     updateItem();
/* 114 */     updateSelection();
/* 115 */     updateFocus();
/*     */   }
/*     */ 
/*     */   private void setListView(ListView<T> paramListView)
/*     */   {
/* 327 */     this.listView.set(paramListView); } 
/* 328 */   public final ListView<T> getListView() { return (ListView)this.listView.get(); } 
/* 329 */   public final ReadOnlyObjectProperty<ListView<T>> listViewProperty() { return this.listView.getReadOnlyProperty(); }
/*     */ 
/*     */ 
/*     */   public void startEdit()
/*     */   {
/* 340 */     ListView localListView = getListView();
/* 341 */     if ((!isEditable()) || ((localListView != null) && (!localListView.isEditable()))) {
/* 342 */       return;
/*     */     }
/*     */ 
/* 348 */     super.startEdit();
/*     */ 
/* 351 */     if (localListView != null) {
/* 352 */       localListView.fireEvent(new ListView.EditEvent(localListView, ListView.editStartEvent(), null, localListView.getEditingIndex()));
/*     */ 
/* 356 */       localListView.edit(getIndex());
/* 357 */       localListView.requestFocus();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void commitEdit(T paramT)
/*     */   {
/* 363 */     if (!isEditing()) return;
/* 364 */     ListView localListView = getListView();
/*     */ 
/* 366 */     if (localListView != null)
/*     */     {
/* 368 */       localListView.fireEvent(new ListView.EditEvent(localListView, ListView.editCommitEvent(), paramT, localListView.getEditingIndex()));
/*     */     }
/*     */ 
/* 375 */     updateItem(paramT, false);
/*     */ 
/* 379 */     super.commitEdit(paramT);
/*     */ 
/* 381 */     if (localListView != null)
/*     */     {
/* 386 */       localListView.edit(-1);
/* 387 */       localListView.requestFocus();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void cancelEdit()
/*     */   {
/* 393 */     if (!isEditing()) return;
/*     */ 
/* 396 */     ListView localListView = getListView();
/*     */ 
/* 398 */     super.cancelEdit();
/*     */ 
/* 400 */     if (localListView != null) {
/* 401 */       int i = localListView.getEditingIndex();
/*     */ 
/* 404 */       if (this.updateEditingIndex) localListView.edit(-1);
/* 405 */       localListView.requestFocus();
/*     */ 
/* 407 */       localListView.fireEvent(new ListView.EditEvent(localListView, ListView.editCancelEvent(), null, i));
/*     */     }
/*     */   }
/*     */ 
/*     */   private void updateItem()
/*     */   {
/* 422 */     ListView localListView = getListView();
/* 423 */     ObservableList localObservableList = localListView == null ? null : localListView.getItems();
/*     */ 
/* 426 */     int i = (localObservableList != null) && (getIndex() >= 0) && (getIndex() < localObservableList.size()) ? 1 : 0;
/*     */ 
/* 429 */     if (i != 0) {
/* 430 */       Object localObject = localObservableList.get(getIndex());
/* 431 */       if ((localObject == null) || (!localObject.equals(getItem())))
/* 432 */         updateItem(localObject, false);
/*     */     }
/*     */     else {
/* 435 */       updateItem(null, true);
/*     */     }
/*     */   }
/*     */ 
/*     */   public final void updateListView(ListView<T> paramListView)
/*     */   {
/* 447 */     setListView(paramListView);
/*     */   }
/*     */ 
/*     */   private void updateSelection() {
/* 451 */     if (isEmpty()) return;
/* 452 */     if ((getIndex() == -1) || (getListView() == null)) return;
/* 453 */     if (getListView().getSelectionModel() == null) return;
/*     */ 
/* 455 */     boolean bool = getListView().getSelectionModel().isSelected(getIndex());
/* 456 */     if (isSelected() == bool) return;
/*     */ 
/* 458 */     updateSelected(getListView().getSelectionModel().isSelected(getIndex()));
/*     */   }
/*     */ 
/*     */   private void updateFocus() {
/* 462 */     if ((getIndex() == -1) || (getListView() == null)) return;
/* 463 */     if (getListView().getFocusModel() == null) return;
/*     */ 
/* 465 */     setFocused(getListView().getFocusModel().isFocused(getIndex()));
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.ListCell
 * JD-Core Version:    0.6.2
 */