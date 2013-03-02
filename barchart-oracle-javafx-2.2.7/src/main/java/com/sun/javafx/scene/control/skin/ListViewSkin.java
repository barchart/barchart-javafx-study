/*     */ package com.sun.javafx.scene.control.skin;
/*     */ 
/*     */ import com.sun.javafx.scene.control.WeakListChangeListener;
/*     */ import com.sun.javafx.scene.control.behavior.ListViewBehavior;
/*     */ import javafx.collections.ListChangeListener;
/*     */ import javafx.collections.ListChangeListener.Change;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.geometry.Insets;
/*     */ import javafx.geometry.Orientation;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.control.FocusModel;
/*     */ import javafx.scene.control.IndexedCell;
/*     */ import javafx.scene.control.ListCell;
/*     */ import javafx.scene.control.ListView;
/*     */ import javafx.scene.control.MultipleSelectionModel;
/*     */ import javafx.scene.control.SelectionModel;
/*     */ import javafx.scene.input.MouseEvent;
/*     */ import javafx.util.Callback;
/*     */ 
/*     */ public class ListViewSkin<T> extends VirtualContainerBase<ListView<T>, ListViewBehavior<T>, ListCell<T>>
/*     */ {
/*     */   private ObservableList<T> listViewItems;
/*     */   private boolean itemCountDirty;
/* 142 */   private final ListChangeListener listViewItemsListener = new ListChangeListener() {
/*     */     public void onChanged(ListChangeListener.Change paramAnonymousChange) {
/* 144 */       ListViewSkin.this.itemCountDirty = true;
/* 145 */       ListViewSkin.this.requestLayout();
/*     */     }
/* 142 */   };
/*     */ 
/* 149 */   private final WeakListChangeListener weakListViewItemsListener = new WeakListChangeListener(this.listViewItemsListener);
/*     */ 
/*     */   public ListViewSkin(final ListView<T> paramListView)
/*     */   {
/*  52 */     super(paramListView, new ListViewBehavior(paramListView));
/*     */ 
/*  54 */     updateListViewItems();
/*     */ 
/*  57 */     this.flow.setId("virtual-flow");
/*  58 */     this.flow.setPannable(false);
/*  59 */     this.flow.setVertical(((ListView)getSkinnable()).getOrientation() == Orientation.VERTICAL);
/*  60 */     this.flow.setFocusTraversable(((ListView)getSkinnable()).isFocusTraversable());
/*  61 */     this.flow.setCreateCell(new Callback() {
/*     */       public ListCell call(VirtualFlow paramAnonymousVirtualFlow) {
/*  63 */         return ListViewSkin.this.createCell();
/*     */       }
/*     */     });
/*  66 */     getChildren().add(this.flow);
/*     */ 
/*  68 */     EventHandler local2 = new EventHandler()
/*     */     {
/*     */       public void handle(MouseEvent paramAnonymousMouseEvent)
/*     */       {
/*  73 */         if (paramListView.getEditingIndex() > -1) {
/*  74 */           paramListView.edit(-1);
/*     */         }
/*     */ 
/*  82 */         paramListView.requestFocus();
/*     */       }
/*     */     };
/*  85 */     this.flow.getVbar().addEventFilter(MouseEvent.MOUSE_PRESSED, local2);
/*  86 */     this.flow.getHbar().addEventFilter(MouseEvent.MOUSE_PRESSED, local2);
/*     */ 
/*  88 */     updateCellCount();
/*     */ 
/*  91 */     ((ListViewBehavior)getBehavior()).setOnFocusPreviousRow(new Runnable() {
/*  92 */       public void run() { ListViewSkin.this.onFocusPreviousCell(); }
/*     */ 
/*     */     });
/*  94 */     ((ListViewBehavior)getBehavior()).setOnFocusNextRow(new Runnable() {
/*  95 */       public void run() { ListViewSkin.this.onFocusNextCell(); }
/*     */ 
/*     */     });
/*  97 */     ((ListViewBehavior)getBehavior()).setOnMoveToFirstCell(new Runnable() {
/*  98 */       public void run() { ListViewSkin.this.onMoveToFirstCell(); }
/*     */ 
/*     */     });
/* 100 */     ((ListViewBehavior)getBehavior()).setOnMoveToLastCell(new Runnable() {
/* 101 */       public void run() { ListViewSkin.this.onMoveToLastCell(); }
/*     */ 
/*     */     });
/* 103 */     ((ListViewBehavior)getBehavior()).setOnScrollPageDown(new Callback() {
/* 104 */       public Integer call(Integer paramAnonymousInteger) { return Integer.valueOf(ListViewSkin.this.onScrollPageDown(paramAnonymousInteger.intValue())); }
/*     */ 
/*     */     });
/* 106 */     ((ListViewBehavior)getBehavior()).setOnScrollPageUp(new Callback() {
/* 107 */       public Integer call(Integer paramAnonymousInteger) { return Integer.valueOf(ListViewSkin.this.onScrollPageUp(paramAnonymousInteger.intValue())); }
/*     */ 
/*     */     });
/* 109 */     ((ListViewBehavior)getBehavior()).setOnSelectPreviousRow(new Runnable() {
/* 110 */       public void run() { ListViewSkin.this.onSelectPreviousCell(); }
/*     */ 
/*     */     });
/* 112 */     ((ListViewBehavior)getBehavior()).setOnSelectNextRow(new Runnable() {
/* 113 */       public void run() { ListViewSkin.this.onSelectNextCell(); }
/*     */ 
/*     */     });
/* 117 */     registerChangeListener(paramListView.itemsProperty(), "ITEMS");
/* 118 */     registerChangeListener(paramListView.orientationProperty(), "ORIENTATION");
/* 119 */     registerChangeListener(paramListView.cellFactoryProperty(), "CELL_FACTORY");
/* 120 */     registerChangeListener(paramListView.parentProperty(), "PARENT");
/* 121 */     registerChangeListener(paramListView.focusTraversableProperty(), "FOCUS_TRAVERSABLE");
/*     */   }
/*     */ 
/*     */   protected void handleControlPropertyChanged(String paramString) {
/* 125 */     super.handleControlPropertyChanged(paramString);
/* 126 */     if (paramString == "ITEMS")
/* 127 */       updateListViewItems();
/* 128 */     else if (paramString == "ORIENTATION")
/* 129 */       this.flow.setVertical(((ListView)getSkinnable()).getOrientation() == Orientation.VERTICAL);
/* 130 */     else if (paramString == "CELL_FACTORY")
/* 131 */       this.flow.recreateCells();
/* 132 */     else if (paramString == "PARENT") {
/* 133 */       if ((((ListView)getSkinnable()).getParent() != null) && (((ListView)getSkinnable()).isVisible()))
/* 134 */         requestLayout();
/*     */     }
/* 136 */     else if (paramString == "FOCUS_TRAVERSABLE")
/* 137 */       this.flow.setFocusTraversable(((ListView)getSkinnable()).isFocusTraversable());
/*     */   }
/*     */ 
/*     */   public void updateListViewItems()
/*     */   {
/* 153 */     if (this.listViewItems != null) {
/* 154 */       this.listViewItems.removeListener(this.weakListViewItemsListener);
/*     */     }
/*     */ 
/* 157 */     this.listViewItems = ((ListView)getSkinnable()).getItems();
/*     */ 
/* 159 */     if (this.listViewItems != null) {
/* 160 */       this.listViewItems.addListener(this.weakListViewItemsListener);
/*     */     }
/*     */ 
/* 163 */     this.itemCountDirty = true;
/* 164 */     requestLayout();
/*     */   }
/*     */ 
/*     */   public int getItemCount() {
/* 168 */     return this.listViewItems == null ? 0 : this.listViewItems.size();
/*     */   }
/*     */ 
/*     */   void updateCellCount() {
/* 172 */     if (this.flow == null) return;
/*     */ 
/* 174 */     int i = this.flow.getCellCount();
/* 175 */     int j = getItemCount();
/*     */ 
/* 177 */     this.flow.setCellCount(getItemCount());
/*     */ 
/* 179 */     if (j != i)
/* 180 */       this.flow.recreateCells();
/*     */     else
/* 182 */       this.flow.reconfigureCells();
/*     */   }
/*     */ 
/*     */   public ListCell<T> createCell()
/*     */   {
/*     */     ListCell localListCell;
/* 188 */     if (((ListView)getSkinnable()).getCellFactory() != null)
/* 189 */       localListCell = (ListCell)((ListView)getSkinnable()).getCellFactory().call(getSkinnable());
/*     */     else {
/* 191 */       localListCell = createDefaultCellImpl();
/*     */     }
/*     */ 
/* 194 */     localListCell.updateListView((ListView)getSkinnable());
/*     */ 
/* 196 */     return localListCell;
/*     */   }
/*     */ 
/*     */   private ListCell createDefaultCellImpl() {
/* 200 */     return new ListCell() {
/*     */       public void updateItem(Object paramAnonymousObject, boolean paramAnonymousBoolean) {
/* 202 */         super.updateItem(paramAnonymousObject, paramAnonymousBoolean);
/*     */ 
/* 204 */         if (paramAnonymousBoolean) {
/* 205 */           setText(null);
/* 206 */           setGraphic(null);
/* 207 */         } else if ((paramAnonymousObject instanceof Node)) {
/* 208 */           setText(null);
/* 209 */           Node localNode1 = getGraphic();
/* 210 */           Node localNode2 = (Node)paramAnonymousObject;
/* 211 */           if ((localNode1 == null) || (!localNode1.equals(localNode2))) {
/* 212 */             setGraphic(localNode2);
/*     */           }
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 221 */           setText(paramAnonymousObject == null ? "null" : paramAnonymousObject.toString());
/* 222 */           setGraphic(null);
/*     */         }
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   protected void layoutChildren()
/*     */   {
/* 247 */     if (this.itemCountDirty) {
/* 248 */       updateCellCount();
/* 249 */       this.itemCountDirty = false;
/*     */     }
/*     */ 
/* 252 */     double d1 = getInsets().getLeft();
/* 253 */     double d2 = getInsets().getTop();
/* 254 */     double d3 = getWidth() - (getInsets().getLeft() + getInsets().getRight());
/* 255 */     double d4 = getHeight() - (getInsets().getTop() + getInsets().getBottom());
/*     */ 
/* 257 */     this.flow.resizeRelocate(d1, d2, d3, d4);
/*     */   }
/*     */ 
/*     */   protected double computePrefWidth(double paramDouble)
/*     */   {
/* 262 */     return computePrefHeight(-1.0D) * 0.618033987D;
/*     */   }
/*     */ 
/*     */   protected double computePrefHeight(double paramDouble)
/*     */   {
/* 267 */     return 400.0D;
/*     */   }
/*     */ 
/*     */   private void onFocusPreviousCell() {
/* 271 */     FocusModel localFocusModel = ((ListView)getSkinnable()).getFocusModel();
/* 272 */     if (localFocusModel == null) return;
/* 273 */     this.flow.show(localFocusModel.getFocusedIndex());
/*     */   }
/*     */ 
/*     */   private void onFocusNextCell() {
/* 277 */     FocusModel localFocusModel = ((ListView)getSkinnable()).getFocusModel();
/* 278 */     if (localFocusModel == null) return;
/* 279 */     this.flow.show(localFocusModel.getFocusedIndex());
/*     */   }
/*     */ 
/*     */   private void onSelectPreviousCell() {
/* 283 */     MultipleSelectionModel localMultipleSelectionModel = ((ListView)getSkinnable()).getSelectionModel();
/* 284 */     if (localMultipleSelectionModel == null) return;
/*     */ 
/* 286 */     int i = localMultipleSelectionModel.getSelectedIndex();
/* 287 */     this.flow.show(i);
/*     */ 
/* 290 */     IndexedCell localIndexedCell = this.flow.getFirstVisibleCell();
/* 291 */     if ((localIndexedCell == null) || (i < localIndexedCell.getIndex()))
/* 292 */       this.flow.setPosition(i / getItemCount());
/*     */   }
/*     */ 
/*     */   private void onSelectNextCell()
/*     */   {
/* 297 */     MultipleSelectionModel localMultipleSelectionModel = ((ListView)getSkinnable()).getSelectionModel();
/* 298 */     if (localMultipleSelectionModel == null) return;
/*     */ 
/* 300 */     int i = localMultipleSelectionModel.getSelectedIndex();
/* 301 */     this.flow.show(i);
/*     */ 
/* 304 */     IndexedCell localIndexedCell = this.flow.getLastVisibleCell();
/* 305 */     if ((localIndexedCell == null) || (localIndexedCell.getIndex() < i))
/* 306 */       this.flow.setPosition(i / getItemCount());
/*     */   }
/*     */ 
/*     */   private void onMoveToFirstCell()
/*     */   {
/* 311 */     this.flow.show(0);
/* 312 */     this.flow.setPosition(0.0D);
/*     */   }
/*     */ 
/*     */   private void onMoveToLastCell()
/*     */   {
/* 319 */     int i = getItemCount() - 1;
/*     */ 
/* 321 */     this.flow.show(i);
/* 322 */     this.flow.setPosition(1.0D);
/*     */   }
/*     */ 
/*     */   private int onScrollPageDown(int paramInt)
/*     */   {
/* 330 */     IndexedCell localIndexedCell = this.flow.getLastVisibleCellWithinViewPort();
/* 331 */     if (localIndexedCell == null) return -1;
/*     */ 
/* 333 */     int i = -1;
/* 334 */     int j = localIndexedCell.getIndex();
/* 335 */     if (((!localIndexedCell.isSelected()) && (!localIndexedCell.isFocused())) || (j != paramInt))
/*     */     {
/* 338 */       i = localIndexedCell.getIndex();
/*     */     }
/*     */     else
/*     */     {
/* 342 */       this.flow.showAsFirst(localIndexedCell);
/*     */ 
/* 344 */       localIndexedCell = this.flow.getLastVisibleCellWithinViewPort();
/* 345 */       i = localIndexedCell.getIndex();
/*     */     }
/*     */ 
/* 348 */     this.flow.show(localIndexedCell);
/*     */ 
/* 350 */     return i;
/*     */   }
/*     */ 
/*     */   private int onScrollPageUp(int paramInt)
/*     */   {
/* 358 */     IndexedCell localIndexedCell = this.flow.getFirstVisibleCellWithinViewPort();
/* 359 */     if (localIndexedCell == null) return -1;
/*     */ 
/* 361 */     int i = -1;
/* 362 */     int j = localIndexedCell.getIndex();
/* 363 */     if (((!localIndexedCell.isSelected()) && (!localIndexedCell.isFocused())) || (j != paramInt))
/*     */     {
/* 366 */       i = localIndexedCell.getIndex();
/*     */     }
/*     */     else
/*     */     {
/* 370 */       this.flow.showAsLast(localIndexedCell);
/*     */ 
/* 372 */       localIndexedCell = this.flow.getFirstVisibleCellWithinViewPort();
/* 373 */       i = localIndexedCell.getIndex();
/*     */     }
/*     */ 
/* 376 */     this.flow.show(localIndexedCell);
/*     */ 
/* 378 */     return i;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.ListViewSkin
 * JD-Core Version:    0.6.2
 */