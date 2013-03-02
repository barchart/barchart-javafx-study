/*     */ package com.sun.javafx.scene.control.skin;
/*     */ 
/*     */ import com.sun.javafx.scene.control.WeakEventHandler;
/*     */ import com.sun.javafx.scene.control.behavior.TreeViewBehavior;
/*     */ import java.lang.ref.WeakReference;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.event.EventType;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.control.FocusModel;
/*     */ import javafx.scene.control.IndexedCell;
/*     */ import javafx.scene.control.MultipleSelectionModel;
/*     */ import javafx.scene.control.TreeCell;
/*     */ import javafx.scene.control.TreeItem;
/*     */ import javafx.scene.control.TreeItem.TreeModificationEvent;
/*     */ import javafx.scene.control.TreeView;
/*     */ import javafx.scene.input.MouseEvent;
/*     */ import javafx.scene.layout.HBox;
/*     */ import javafx.scene.layout.StackPane;
/*     */ import javafx.util.Callback;
/*     */ 
/*     */ public class TreeViewSkin<T> extends VirtualContainerBase<TreeView<T>, TreeViewBehavior<T>, TreeCell<T>>
/*     */ {
/* 141 */   private boolean needItemCountUpdate = false;
/* 142 */   private boolean needCellsRecreated = false;
/*     */ 
/* 144 */   private EventHandler<TreeItem.TreeModificationEvent> rootListener = new EventHandler() {
/*     */     public void handle(TreeItem.TreeModificationEvent paramAnonymousTreeModificationEvent) {
/* 146 */       if ((paramAnonymousTreeModificationEvent.wasAdded()) && (paramAnonymousTreeModificationEvent.wasRemoved()) && (paramAnonymousTreeModificationEvent.getAddedSize() == paramAnonymousTreeModificationEvent.getRemovedSize()))
/*     */       {
/* 152 */         TreeViewSkin.this.needItemCountUpdate = true;
/* 153 */         TreeViewSkin.this.requestLayout();
/* 154 */       } else if (((paramAnonymousTreeModificationEvent.wasAdded()) && (paramAnonymousTreeModificationEvent.getAddedSize() == paramAnonymousTreeModificationEvent.getTreeItem().getChildren().size())) || ((paramAnonymousTreeModificationEvent.wasRemoved()) && (paramAnonymousTreeModificationEvent.getTreeItem().getChildren().isEmpty())))
/*     */       {
/* 166 */         TreeViewSkin.this.needCellsRecreated = true;
/* 167 */         TreeViewSkin.this.requestLayout();
/* 168 */       } else if (paramAnonymousTreeModificationEvent.getEventType().equals(TreeItem.valueChangedEvent()))
/*     */       {
/* 170 */         TreeViewSkin.this.needCellsRecreated = true;
/* 171 */         TreeViewSkin.this.requestLayout();
/*     */       }
/*     */       else
/*     */       {
/* 175 */         EventType localEventType = paramAnonymousTreeModificationEvent.getEventType();
/* 176 */         while (localEventType != null) {
/* 177 */           if (localEventType.equals(TreeItem.treeItemCountChangeEvent())) {
/* 178 */             TreeViewSkin.this.needItemCountUpdate = true;
/* 179 */             TreeViewSkin.this.requestLayout();
/* 180 */             break;
/*     */           }
/* 182 */           localEventType = localEventType.getSuperType();
/*     */         }
/*     */       }
/*     */     }
/* 144 */   };
/*     */   private WeakEventHandler weakRootListener;
/*     */   private WeakReference<TreeItem> weakRoot;
/*     */ 
/*     */   public TreeViewSkin(final TreeView paramTreeView)
/*     */   {
/*  49 */     super(paramTreeView, new TreeViewBehavior(paramTreeView));
/*     */ 
/*  52 */     this.flow.setPannable(false);
/*  53 */     this.flow.setFocusTraversable(((TreeView)getSkinnable()).isFocusTraversable());
/*  54 */     this.flow.setCreateCell(new Callback() {
/*     */       public TreeCell call(VirtualFlow paramAnonymousVirtualFlow) {
/*  56 */         return TreeViewSkin.this.createCell();
/*     */       }
/*     */     });
/*  59 */     getChildren().add(this.flow);
/*     */ 
/*  61 */     setRoot(((TreeView)getSkinnable()).getRoot());
/*     */ 
/*  63 */     EventHandler local2 = new EventHandler()
/*     */     {
/*     */       public void handle(MouseEvent paramAnonymousMouseEvent)
/*     */       {
/*  68 */         if (paramTreeView.getEditingItem() != null) {
/*  69 */           paramTreeView.edit(null);
/*     */         }
/*     */ 
/*  77 */         paramTreeView.requestFocus();
/*     */       }
/*     */     };
/*  79 */     this.flow.getVbar().addEventFilter(MouseEvent.MOUSE_PRESSED, local2);
/*  80 */     this.flow.getHbar().addEventFilter(MouseEvent.MOUSE_PRESSED, local2);
/*     */ 
/*  83 */     ((TreeViewBehavior)getBehavior()).setOnFocusPreviousRow(new Runnable() {
/*  84 */       public void run() { TreeViewSkin.this.onFocusPreviousCell(); }
/*     */ 
/*     */     });
/*  86 */     ((TreeViewBehavior)getBehavior()).setOnFocusNextRow(new Runnable() {
/*  87 */       public void run() { TreeViewSkin.this.onFocusNextCell(); }
/*     */ 
/*     */     });
/*  89 */     ((TreeViewBehavior)getBehavior()).setOnMoveToFirstCell(new Runnable() {
/*  90 */       public void run() { TreeViewSkin.this.onMoveToFirstCell(); }
/*     */ 
/*     */     });
/*  92 */     ((TreeViewBehavior)getBehavior()).setOnMoveToLastCell(new Runnable() {
/*  93 */       public void run() { TreeViewSkin.this.onMoveToLastCell(); }
/*     */ 
/*     */     });
/*  95 */     ((TreeViewBehavior)getBehavior()).setOnScrollPageDown(new Callback() {
/*  96 */       public Integer call(Integer paramAnonymousInteger) { return Integer.valueOf(TreeViewSkin.this.onScrollPageDown(paramAnonymousInteger.intValue())); }
/*     */ 
/*     */     });
/*  98 */     ((TreeViewBehavior)getBehavior()).setOnScrollPageUp(new Callback() {
/*  99 */       public Integer call(Integer paramAnonymousInteger) { return Integer.valueOf(TreeViewSkin.this.onScrollPageUp(paramAnonymousInteger.intValue())); }
/*     */ 
/*     */     });
/* 101 */     ((TreeViewBehavior)getBehavior()).setOnSelectPreviousRow(new Runnable() {
/* 102 */       public void run() { TreeViewSkin.this.onSelectPreviousCell(); }
/*     */ 
/*     */     });
/* 104 */     ((TreeViewBehavior)getBehavior()).setOnSelectNextRow(new Runnable() {
/* 105 */       public void run() { TreeViewSkin.this.onSelectNextCell(); }
/*     */ 
/*     */     });
/* 108 */     registerChangeListener(paramTreeView.rootProperty(), "ROOT");
/* 109 */     registerChangeListener(paramTreeView.showRootProperty(), "SHOW_ROOT");
/* 110 */     registerChangeListener(paramTreeView.cellFactoryProperty(), "CELL_FACTORY");
/* 111 */     registerChangeListener(paramTreeView.impl_treeItemCountProperty(), "TREE_ITEM_COUNT");
/* 112 */     registerChangeListener(paramTreeView.focusTraversableProperty(), "FOCUS_TRAVERSABLE");
/*     */ 
/* 114 */     updateItemCount();
/*     */   }
/*     */ 
/*     */   protected void handleControlPropertyChanged(String paramString) {
/* 118 */     super.handleControlPropertyChanged(paramString);
/*     */ 
/* 120 */     if (paramString == "ROOT")
/* 121 */       setRoot(((TreeView)getSkinnable()).getRoot());
/* 122 */     else if (paramString == "SHOW_ROOT")
/*     */     {
/* 126 */       if ((!((TreeView)getSkinnable()).isShowRoot()) && (getRoot() != null)) {
/* 127 */         getRoot().setExpanded(true);
/*     */ 
/* 130 */         updateItemCount();
/*     */       }
/* 132 */     } else if (paramString == "CELL_FACTORY")
/* 133 */       this.flow.recreateCells();
/* 134 */     else if (paramString == "TREE_ITEM_COUNT")
/* 135 */       updateItemCount();
/* 136 */     else if (paramString == "FOCUS_TRAVERSABLE")
/* 137 */       this.flow.setFocusTraversable(((TreeView)getSkinnable()).isFocusTraversable());
/*     */   }
/*     */ 
/*     */   private TreeItem getRoot()
/*     */   {
/* 193 */     return this.weakRoot == null ? null : (TreeItem)this.weakRoot.get();
/*     */   }
/*     */   private void setRoot(TreeItem paramTreeItem) {
/* 196 */     if ((getRoot() != null) && (this.weakRootListener != null)) {
/* 197 */       getRoot().removeEventHandler(TreeItem.treeNotificationEvent(), this.weakRootListener);
/*     */     }
/* 199 */     this.weakRoot = new WeakReference(paramTreeItem);
/* 200 */     if (getRoot() != null) {
/* 201 */       this.weakRootListener = new WeakEventHandler(getRoot(), TreeItem.treeNotificationEvent(), this.rootListener);
/* 202 */       getRoot().addEventHandler(TreeItem.treeNotificationEvent(), this.weakRootListener);
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getItemCount() {
/* 207 */     return ((TreeView)getSkinnable()).impl_getTreeItemCount();
/*     */   }
/*     */ 
/*     */   private void updateItemCount()
/*     */   {
/* 215 */     for (int i = 0; i < this.flow.cells.size(); i++) {
/* 216 */       ((TreeCell)this.flow.cells.get(i)).updateTreeView(null);
/*     */     }
/*     */ 
/* 219 */     i = this.flow.getCellCount();
/* 220 */     int j = getItemCount();
/*     */ 
/* 225 */     this.flow.setCellCount(j);
/* 226 */     this.flow.recreateCells();
/*     */   }
/*     */ 
/*     */   public TreeCell<T> createCell()
/*     */   {
/*     */     TreeCell localTreeCell;
/* 231 */     if (((TreeView)getSkinnable()).getCellFactory() != null)
/* 232 */       localTreeCell = (TreeCell)((TreeView)getSkinnable()).getCellFactory().call(getSkinnable());
/*     */     else {
/* 234 */       localTreeCell = createDefaultCellImpl();
/*     */     }
/*     */ 
/* 238 */     if (localTreeCell.getDisclosureNode() == null) {
/* 239 */       StackPane localStackPane1 = new StackPane();
/* 240 */       localStackPane1.getStyleClass().setAll(new String[] { "tree-disclosure-node" });
/*     */ 
/* 242 */       StackPane localStackPane2 = new StackPane();
/* 243 */       localStackPane2.getStyleClass().setAll(new String[] { "arrow" });
/* 244 */       localStackPane1.getChildren().add(localStackPane2);
/*     */ 
/* 246 */       localTreeCell.setDisclosureNode(localStackPane1);
/*     */     }
/*     */ 
/* 249 */     localTreeCell.updateTreeView((TreeView)getSkinnable());
/*     */ 
/* 251 */     return localTreeCell;
/*     */   }
/*     */ 
/*     */   private TreeCell<T> createDefaultCellImpl() {
/* 255 */     return new TreeCell() {
/*     */       private HBox hbox;
/*     */ 
/*     */       public void updateItem(Object paramAnonymousObject, boolean paramAnonymousBoolean) {
/* 259 */         super.updateItem(paramAnonymousObject, paramAnonymousBoolean);
/*     */ 
/* 261 */         if ((paramAnonymousObject == null) || (paramAnonymousBoolean)) {
/* 262 */           this.hbox = null;
/* 263 */           setText(null);
/* 264 */           setGraphic(null);
/*     */         }
/*     */         else {
/* 267 */           TreeItem localTreeItem = getTreeItem();
/* 268 */           if ((localTreeItem != null) && (localTreeItem.getGraphic() != null)) {
/* 269 */             if ((paramAnonymousObject instanceof Node)) {
/* 270 */               setText(null);
/*     */ 
/* 275 */               if (this.hbox == null) {
/* 276 */                 this.hbox = new HBox(3.0D);
/*     */               }
/* 278 */               this.hbox.getChildren().setAll(new Node[] { localTreeItem.getGraphic(), (Node)paramAnonymousObject });
/* 279 */               setGraphic(this.hbox);
/*     */             } else {
/* 281 */               this.hbox = null;
/* 282 */               setText(paramAnonymousObject.toString());
/* 283 */               setGraphic(localTreeItem.getGraphic());
/*     */             }
/*     */           } else {
/* 286 */             this.hbox = null;
/* 287 */             if ((paramAnonymousObject instanceof Node)) {
/* 288 */               setText(null);
/* 289 */               setGraphic((Node)paramAnonymousObject);
/*     */             } else {
/* 291 */               setText(paramAnonymousObject.toString());
/* 292 */               setGraphic(null);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   protected double computePrefWidth(double paramDouble) {
/* 301 */     return computePrefHeight(-1.0D) * 0.618033987D;
/*     */   }
/*     */ 
/*     */   protected double computePrefHeight(double paramDouble) {
/* 305 */     return 400.0D;
/*     */   }
/*     */ 
/*     */   protected void layoutChildren()
/*     */   {
/* 310 */     if (this.needCellsRecreated) {
/* 311 */       this.flow.recreateCells();
/* 312 */       this.needCellsRecreated = false;
/* 313 */     } else if (this.needItemCountUpdate) {
/* 314 */       updateItemCount();
/* 315 */       this.needItemCountUpdate = false;
/*     */     }
/* 317 */     super.layoutChildren();
/*     */   }
/*     */ 
/*     */   private void onFocusPreviousCell() {
/* 321 */     FocusModel localFocusModel = ((TreeView)getSkinnable()).getFocusModel();
/* 322 */     if (localFocusModel == null) return;
/* 323 */     this.flow.show(localFocusModel.getFocusedIndex());
/*     */   }
/*     */ 
/*     */   private void onFocusNextCell() {
/* 327 */     FocusModel localFocusModel = ((TreeView)getSkinnable()).getFocusModel();
/* 328 */     if (localFocusModel == null) return;
/* 329 */     this.flow.show(localFocusModel.getFocusedIndex());
/*     */   }
/*     */ 
/*     */   private void onSelectPreviousCell() {
/* 333 */     int i = ((TreeView)getSkinnable()).getSelectionModel().getSelectedIndex();
/* 334 */     this.flow.show(i);
/*     */   }
/*     */ 
/*     */   private void onSelectNextCell() {
/* 338 */     int i = ((TreeView)getSkinnable()).getSelectionModel().getSelectedIndex();
/* 339 */     this.flow.show(i);
/*     */   }
/*     */ 
/*     */   private void onMoveToFirstCell() {
/* 343 */     this.flow.show(0);
/* 344 */     this.flow.setPosition(0.0D);
/*     */   }
/*     */ 
/*     */   private void onMoveToLastCell() {
/* 348 */     this.flow.show(getItemCount());
/* 349 */     this.flow.setPosition(1.0D);
/*     */   }
/*     */ 
/*     */   public int onScrollPageDown(int paramInt)
/*     */   {
/* 357 */     IndexedCell localIndexedCell = this.flow.getLastVisibleCellWithinViewPort();
/* 358 */     if (localIndexedCell == null) return -1;
/*     */ 
/* 360 */     int i = -1;
/* 361 */     int j = localIndexedCell.getIndex();
/* 362 */     if (((!localIndexedCell.isSelected()) && (!localIndexedCell.isFocused())) || (j != paramInt))
/*     */     {
/* 365 */       i = localIndexedCell.getIndex();
/*     */     }
/*     */     else
/*     */     {
/* 369 */       this.flow.showAsFirst(localIndexedCell);
/*     */ 
/* 371 */       localIndexedCell = this.flow.getLastVisibleCellWithinViewPort();
/* 372 */       i = localIndexedCell.getIndex();
/*     */     }
/*     */ 
/* 375 */     this.flow.show(localIndexedCell);
/*     */ 
/* 377 */     return i;
/*     */   }
/*     */ 
/*     */   public int onScrollPageUp(int paramInt)
/*     */   {
/* 385 */     IndexedCell localIndexedCell = this.flow.getFirstVisibleCellWithinViewPort();
/* 386 */     if (localIndexedCell == null) return -1;
/*     */ 
/* 388 */     int i = -1;
/* 389 */     int j = localIndexedCell.getIndex();
/* 390 */     if (((!localIndexedCell.isSelected()) && (!localIndexedCell.isFocused())) || (j != paramInt))
/*     */     {
/* 393 */       i = localIndexedCell.getIndex();
/*     */     }
/*     */     else
/*     */     {
/* 397 */       this.flow.showAsLast(localIndexedCell);
/*     */ 
/* 399 */       localIndexedCell = this.flow.getFirstVisibleCellWithinViewPort();
/* 400 */       i = localIndexedCell.getIndex();
/*     */     }
/*     */ 
/* 403 */     this.flow.show(localIndexedCell);
/*     */ 
/* 405 */     return i;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.TreeViewSkin
 * JD-Core Version:    0.6.2
 */