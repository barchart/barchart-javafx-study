/*     */ package com.sun.javafx.scene.control.behavior;
/*     */ 
/*     */ import com.sun.javafx.PlatformUtil;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ import javafx.collections.ListChangeListener;
/*     */ import javafx.collections.ListChangeListener.Change;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.scene.control.FocusModel;
/*     */ import javafx.scene.control.MultipleSelectionModel;
/*     */ import javafx.scene.control.TreeItem;
/*     */ import javafx.scene.control.TreeView;
/*     */ import javafx.scene.input.KeyCode;
/*     */ import javafx.scene.input.KeyEvent;
/*     */ import javafx.scene.input.MouseEvent;
/*     */ import javafx.util.Callback;
/*     */ 
/*     */ public class TreeViewBehavior<T> extends BehaviorBase<TreeView<T>>
/*     */ {
/*  52 */   protected static final List<KeyBinding> TREE_VIEW_BINDINGS = new ArrayList();
/*     */ 
/* 183 */   private boolean isShiftDown = false;
/* 184 */   private boolean isCtrlDown = false;
/*     */   private Callback<Integer, Integer> onScrollPageUp;
/*     */   private Callback<Integer, Integer> onScrollPageDown;
/*     */   private Runnable onSelectPreviousRow;
/*     */   private Runnable onSelectNextRow;
/*     */   private Runnable onMoveToFirstCell;
/*     */   private Runnable onMoveToLastCell;
/*     */   private Runnable onFocusPreviousRow;
/*     */   private Runnable onFocusNextRow;
/* 214 */   private boolean selectionChanging = false;
/*     */ 
/* 216 */   private ListChangeListener<Integer> selectedIndicesListener = new ListChangeListener() {
/*     */     public void onChanged(ListChangeListener.Change paramAnonymousChange) {
/* 218 */       while (paramAnonymousChange.next())
/*     */       {
/* 220 */         if (!TreeViewBehavior.this.selectionChanging) {
/* 221 */           if (paramAnonymousChange.getList().isEmpty())
/* 222 */             TreeViewBehavior.this.setAnchor(-1);
/* 223 */           else if (!paramAnonymousChange.getList().contains(Integer.valueOf(TreeViewBehavior.this.getAnchor()))) {
/* 224 */             TreeViewBehavior.this.setAnchor(-1);
/*     */           }
/*     */         }
/*     */ 
/* 228 */         int i = paramAnonymousChange.getAddedSize();
/* 229 */         if ((!TreeViewBehavior.this.hasAnchor()) && (i > 0)) {
/* 230 */           List localList = paramAnonymousChange.getAddedSubList();
/* 231 */           for (int j = 0; j < i; j++) {
/* 232 */             int k = ((Integer)localList.get(j)).intValue();
/* 233 */             if (k >= 0) {
/* 234 */               TreeViewBehavior.this.setAnchor(k);
/* 235 */               break;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 216 */   };
/*     */ 
/*     */   protected void callAction(String paramString)
/*     */   {
/* 128 */     if ("SelectPreviousRow".equals(paramString)) selectPreviousRow();
/* 129 */     else if ("SelectNextRow".equals(paramString)) selectNextRow();
/* 130 */     else if ("SelectFirstRow".equals(paramString)) selectFirstRow();
/* 131 */     else if ("SelectLastRow".equals(paramString)) selectLastRow();
/* 132 */     else if ("SelectAllPageUp".equals(paramString)) selectAllPageUp();
/* 133 */     else if ("SelectAllPageDown".equals(paramString)) selectAllPageDown();
/* 134 */     else if ("SelectAllToFirstRow".equals(paramString)) selectAllToFirstRow();
/* 135 */     else if ("SelectAllToLastRow".equals(paramString)) selectAllToLastRow();
/* 136 */     else if ("AlsoSelectNextRow".equals(paramString)) alsoSelectNextRow();
/* 137 */     else if ("AlsoSelectPreviousRow".equals(paramString)) alsoSelectPreviousRow();
/* 138 */     else if ("ClearSelection".equals(paramString)) clearSelection();
/* 139 */     else if ("SelectAll".equals(paramString)) selectAll();
/* 140 */     else if ("ScrollUp".equals(paramString)) scrollUp();
/* 141 */     else if ("ScrollDown".equals(paramString)) scrollDown();
/* 142 */     else if ("ExpandRow".equals(paramString)) expandRow();
/* 143 */     else if ("CollapseRow".equals(paramString)) collapseRow();
/* 144 */     else if ("ExpandAll".equals(paramString)) expandAll();
/* 146 */     else if ("Edit".equals(paramString)) edit();
/* 147 */     else if ("CancelEdit".equals(paramString)) cancelEdit();
/* 148 */     else if ("FocusFirstRow".equals(paramString)) focusFirstRow();
/* 149 */     else if ("FocusLastRow".equals(paramString)) focusLastRow();
/* 150 */     else if ("toggleFocusOwnerSelection".equals(paramString)) toggleFocusOwnerSelection();
/* 151 */     else if ("SelectAllToFocus".equals(paramString)) selectAllToFocus();
/* 152 */     else if ("FocusPageUp".equals(paramString)) focusPageUp();
/* 153 */     else if ("FocusPageDown".equals(paramString)) focusPageDown();
/* 154 */     else if ("FocusPreviousRow".equals(paramString)) focusPreviousRow();
/* 155 */     else if ("FocusNextRow".equals(paramString)) focusNextRow();
/* 156 */     else if ("DiscontinuousSelectNextRow".equals(paramString)) discontinuousSelectNextRow();
/* 157 */     else if ("DiscontinuousSelectPreviousRow".equals(paramString)) discontinuousSelectPreviousRow();
/* 158 */     else if ("DiscontinuousSelectPageUp".equals(paramString)) discontinuousSelectPageUp();
/* 159 */     else if ("DiscontinuousSelectPageDown".equals(paramString)) discontinuousSelectPageDown();
/* 160 */     else if ("DiscontinuousSelectAllToLastRow".equals(paramString)) discontinuousSelectAllToLastRow();
/* 161 */     else if ("DiscontinuousSelectAllToFirstRow".equals(paramString)) discontinuousSelectAllToFirstRow(); else
/* 162 */       super.callAction(paramString);
/*     */   }
/*     */ 
/*     */   protected List<KeyBinding> createKeyBindings() {
/* 166 */     return TREE_VIEW_BINDINGS;
/*     */   }
/*     */ 
/*     */   protected void callActionForEvent(KeyEvent paramKeyEvent)
/*     */   {
/* 173 */     this.isShiftDown = ((paramKeyEvent.getEventType() == KeyEvent.KEY_PRESSED) && (paramKeyEvent.isShiftDown()));
/* 174 */     this.isCtrlDown = ((paramKeyEvent.getEventType() == KeyEvent.KEY_PRESSED) && (paramKeyEvent.isControlDown()));
/*     */ 
/* 176 */     super.callActionForEvent(paramKeyEvent);
/*     */   }
/*     */ 
/*     */   public void setOnScrollPageUp(Callback<Integer, Integer> paramCallback)
/*     */   {
/* 191 */     this.onScrollPageUp = paramCallback;
/*     */   }
/*     */   public void setOnScrollPageDown(Callback<Integer, Integer> paramCallback) {
/* 194 */     this.onScrollPageDown = paramCallback;
/*     */   }
/*     */   public void setOnSelectPreviousRow(Runnable paramRunnable) {
/* 197 */     this.onSelectPreviousRow = paramRunnable;
/*     */   }
/*     */   public void setOnSelectNextRow(Runnable paramRunnable) {
/* 200 */     this.onSelectNextRow = paramRunnable;
/*     */   }
/*     */   public void setOnMoveToFirstCell(Runnable paramRunnable) {
/* 203 */     this.onMoveToFirstCell = paramRunnable;
/*     */   }
/*     */   public void setOnMoveToLastCell(Runnable paramRunnable) {
/* 206 */     this.onMoveToLastCell = paramRunnable;
/*     */   }
/*     */   public void setOnFocusPreviousRow(Runnable paramRunnable) {
/* 209 */     this.onFocusPreviousRow = paramRunnable;
/*     */   }
/*     */   public void setOnFocusNextRow(Runnable paramRunnable) {
/* 212 */     this.onFocusNextRow = paramRunnable;
/*     */   }
/*     */ 
/*     */   public TreeViewBehavior(TreeView paramTreeView)
/*     */   {
/* 244 */     super(paramTreeView);
/*     */ 
/* 247 */     ((TreeView)getControl()).selectionModelProperty().addListener(new ChangeListener()
/*     */     {
/*     */       public void changed(ObservableValue<? extends MultipleSelectionModel<TreeItem<T>>> paramAnonymousObservableValue, MultipleSelectionModel<TreeItem<T>> paramAnonymousMultipleSelectionModel1, MultipleSelectionModel<TreeItem<T>> paramAnonymousMultipleSelectionModel2)
/*     */       {
/* 252 */         if (paramAnonymousMultipleSelectionModel1 != null) {
/* 253 */           paramAnonymousMultipleSelectionModel1.getSelectedIndices().removeListener(TreeViewBehavior.this.selectedIndicesListener);
/*     */         }
/* 255 */         if (paramAnonymousMultipleSelectionModel2 != null)
/* 256 */           paramAnonymousMultipleSelectionModel2.getSelectedIndices().addListener(TreeViewBehavior.this.selectedIndicesListener);
/*     */       }
/*     */     });
/* 260 */     if (paramTreeView.getSelectionModel() != null)
/* 261 */       paramTreeView.getSelectionModel().getSelectedIndices().addListener(this.selectedIndicesListener);
/*     */   }
/*     */ 
/*     */   private void setAnchor(int paramInt)
/*     */   {
/* 266 */     TreeCellBehavior.setAnchor((TreeView)getControl(), paramInt);
/*     */   }
/*     */ 
/*     */   private int getAnchor() {
/* 270 */     return TreeCellBehavior.getAnchor((TreeView)getControl());
/*     */   }
/*     */ 
/*     */   private boolean hasAnchor() {
/* 274 */     return TreeCellBehavior.hasAnchor((TreeView)getControl());
/*     */   }
/*     */ 
/*     */   public void mousePressed(MouseEvent paramMouseEvent) {
/* 278 */     super.mousePressed(paramMouseEvent);
/*     */ 
/* 280 */     if (!paramMouseEvent.isShiftDown()) {
/* 281 */       int i = ((TreeView)getControl()).getSelectionModel().getSelectedIndex();
/* 282 */       setAnchor(i);
/*     */     }
/*     */ 
/* 285 */     if ((!((TreeView)getControl()).isFocused()) && (((TreeView)getControl()).isFocusTraversable()))
/* 286 */       ((TreeView)getControl()).requestFocus();
/*     */   }
/*     */ 
/*     */   private void clearSelection()
/*     */   {
/* 291 */     ((TreeView)getControl()).getSelectionModel().clearSelection();
/*     */   }
/*     */ 
/*     */   private void scrollUp()
/*     */   {
/* 296 */     int i = -1;
/* 297 */     if (this.onScrollPageUp != null) {
/* 298 */       i = ((Integer)this.onScrollPageUp.call(Integer.valueOf(getAnchor()))).intValue();
/*     */     }
/* 300 */     if (i == -1) return;
/*     */ 
/* 302 */     MultipleSelectionModel localMultipleSelectionModel = ((TreeView)getControl()).getSelectionModel();
/* 303 */     if (localMultipleSelectionModel == null) return;
/* 304 */     localMultipleSelectionModel.clearAndSelect(i);
/*     */   }
/*     */ 
/*     */   private void scrollDown() {
/* 308 */     int i = -1;
/* 309 */     if (this.onScrollPageDown != null) {
/* 310 */       i = ((Integer)this.onScrollPageDown.call(Integer.valueOf(getAnchor()))).intValue();
/*     */     }
/* 312 */     if (i == -1) return;
/*     */ 
/* 314 */     MultipleSelectionModel localMultipleSelectionModel = ((TreeView)getControl()).getSelectionModel();
/* 315 */     if (localMultipleSelectionModel == null) return;
/* 316 */     localMultipleSelectionModel.clearAndSelect(i);
/*     */   }
/*     */ 
/*     */   private void focusFirstRow() {
/* 320 */     FocusModel localFocusModel = ((TreeView)getControl()).getFocusModel();
/* 321 */     if (localFocusModel == null) return;
/* 322 */     localFocusModel.focus(0);
/*     */ 
/* 324 */     if (this.onMoveToFirstCell != null) this.onMoveToFirstCell.run(); 
/*     */   }
/*     */ 
/*     */   private void focusLastRow()
/*     */   {
/* 328 */     FocusModel localFocusModel = ((TreeView)getControl()).getFocusModel();
/* 329 */     if (localFocusModel == null) return;
/* 330 */     localFocusModel.focus(((TreeView)getControl()).impl_getTreeItemCount() - 1);
/*     */ 
/* 332 */     if (this.onMoveToLastCell != null) this.onMoveToLastCell.run(); 
/*     */   }
/*     */ 
/*     */   private void focusPreviousRow()
/*     */   {
/* 336 */     FocusModel localFocusModel = ((TreeView)getControl()).getFocusModel();
/* 337 */     if (localFocusModel == null) return;
/*     */ 
/* 339 */     MultipleSelectionModel localMultipleSelectionModel = ((TreeView)getControl()).getSelectionModel();
/* 340 */     if (localMultipleSelectionModel == null) return;
/*     */ 
/* 342 */     localFocusModel.focusPrevious();
/*     */ 
/* 344 */     if ((!this.isCtrlDown) || (getAnchor() == -1)) {
/* 345 */       setAnchor(localFocusModel.getFocusedIndex());
/*     */     }
/*     */ 
/* 348 */     if (this.onFocusPreviousRow != null) this.onFocusPreviousRow.run(); 
/*     */   }
/*     */ 
/*     */   private void focusNextRow()
/*     */   {
/* 352 */     FocusModel localFocusModel = ((TreeView)getControl()).getFocusModel();
/* 353 */     if (localFocusModel == null) return;
/*     */ 
/* 355 */     MultipleSelectionModel localMultipleSelectionModel = ((TreeView)getControl()).getSelectionModel();
/* 356 */     if (localMultipleSelectionModel == null) return;
/*     */ 
/* 358 */     localFocusModel.focusNext();
/*     */ 
/* 360 */     if ((!this.isCtrlDown) || (getAnchor() == -1)) {
/* 361 */       setAnchor(localFocusModel.getFocusedIndex());
/*     */     }
/*     */ 
/* 364 */     if (this.onFocusNextRow != null) this.onFocusNextRow.run(); 
/*     */   }
/*     */ 
/*     */   private void focusPageUp()
/*     */   {
/* 368 */     int i = ((Integer)this.onScrollPageUp.call(Integer.valueOf(getAnchor()))).intValue();
/*     */ 
/* 370 */     FocusModel localFocusModel = ((TreeView)getControl()).getFocusModel();
/* 371 */     if (localFocusModel == null) return;
/* 372 */     localFocusModel.focus(i);
/*     */   }
/*     */ 
/*     */   private void focusPageDown() {
/* 376 */     int i = ((Integer)this.onScrollPageDown.call(Integer.valueOf(getAnchor()))).intValue();
/*     */ 
/* 378 */     FocusModel localFocusModel = ((TreeView)getControl()).getFocusModel();
/* 379 */     if (localFocusModel == null) return;
/* 380 */     localFocusModel.focus(i);
/*     */   }
/*     */ 
/*     */   private void alsoSelectPreviousRow() {
/* 384 */     FocusModel localFocusModel = ((TreeView)getControl()).getFocusModel();
/* 385 */     if (localFocusModel == null) return;
/*     */ 
/* 387 */     MultipleSelectionModel localMultipleSelectionModel = ((TreeView)getControl()).getSelectionModel();
/* 388 */     if (localMultipleSelectionModel == null) return;
/*     */ 
/* 392 */     if ((this.isShiftDown) && (getAnchor() != -1)) {
/* 393 */       int i = localFocusModel.getFocusedIndex() - 1;
/* 394 */       int j = getAnchor();
/*     */ 
/* 396 */       if (!hasAnchor()) {
/* 397 */         setAnchor(localFocusModel.getFocusedIndex());
/*     */       }
/*     */ 
/* 400 */       clearSelectionOutsideRange(j, i);
/*     */ 
/* 402 */       if (j > i)
/* 403 */         localMultipleSelectionModel.selectRange(j, i - 1);
/*     */       else
/* 405 */         localMultipleSelectionModel.selectRange(j, i + 1);
/*     */     }
/*     */     else {
/* 408 */       localMultipleSelectionModel.selectPrevious();
/*     */     }
/*     */ 
/* 411 */     this.onSelectPreviousRow.run();
/*     */   }
/*     */ 
/*     */   private void alsoSelectNextRow() {
/* 415 */     FocusModel localFocusModel = ((TreeView)getControl()).getFocusModel();
/* 416 */     if (localFocusModel == null) return;
/*     */ 
/* 418 */     MultipleSelectionModel localMultipleSelectionModel = ((TreeView)getControl()).getSelectionModel();
/* 419 */     if (localMultipleSelectionModel == null) return;
/*     */ 
/* 421 */     if ((this.isShiftDown) && (getAnchor() != -1)) {
/* 422 */       int i = localFocusModel.getFocusedIndex() + 1;
/* 423 */       int j = getAnchor();
/*     */ 
/* 425 */       if (!hasAnchor()) {
/* 426 */         setAnchor(localFocusModel.getFocusedIndex());
/*     */       }
/*     */ 
/* 429 */       clearSelectionOutsideRange(j, i);
/*     */ 
/* 431 */       if (j > i)
/* 432 */         localMultipleSelectionModel.selectRange(j, i - 1);
/*     */       else
/* 434 */         localMultipleSelectionModel.selectRange(j, i + 1);
/*     */     }
/*     */     else {
/* 437 */       localMultipleSelectionModel.selectNext();
/*     */     }
/*     */ 
/* 440 */     this.onSelectNextRow.run();
/*     */   }
/*     */ 
/*     */   private void clearSelectionOutsideRange(int paramInt1, int paramInt2) {
/* 444 */     MultipleSelectionModel localMultipleSelectionModel = ((TreeView)getControl()).getSelectionModel();
/* 445 */     if (localMultipleSelectionModel == null) return;
/*     */ 
/* 447 */     int i = Math.min(paramInt1, paramInt2);
/* 448 */     int j = Math.max(paramInt1, paramInt2);
/*     */ 
/* 450 */     ArrayList localArrayList = new ArrayList(localMultipleSelectionModel.getSelectedIndices());
/*     */ 
/* 452 */     this.selectionChanging = true;
/* 453 */     for (int k = 0; k < localArrayList.size(); k++) {
/* 454 */       int m = ((Integer)localArrayList.get(k)).intValue();
/* 455 */       if ((m < i) || (m >= j)) {
/* 456 */         localMultipleSelectionModel.clearSelection(m);
/*     */       }
/*     */     }
/* 459 */     this.selectionChanging = false;
/*     */   }
/*     */ 
/*     */   private void selectPreviousRow() {
/* 463 */     FocusModel localFocusModel = ((TreeView)getControl()).getFocusModel();
/* 464 */     if (localFocusModel == null) return;
/*     */ 
/* 466 */     int i = localFocusModel.getFocusedIndex();
/* 467 */     if (i <= 0) {
/* 468 */       return;
/*     */     }
/*     */ 
/* 471 */     setAnchor(i - 1);
/* 472 */     ((TreeView)getControl()).getSelectionModel().clearAndSelect(i - 1);
/* 473 */     this.onSelectPreviousRow.run();
/*     */   }
/*     */ 
/*     */   private void selectNextRow() {
/* 477 */     FocusModel localFocusModel = ((TreeView)getControl()).getFocusModel();
/* 478 */     if (localFocusModel == null) return;
/*     */ 
/* 480 */     int i = localFocusModel.getFocusedIndex();
/* 481 */     if (i == ((TreeView)getControl()).impl_getTreeItemCount() - 1) {
/* 482 */       return;
/*     */     }
/*     */ 
/* 485 */     setAnchor(i + 1);
/* 486 */     ((TreeView)getControl()).getSelectionModel().clearAndSelect(i + 1);
/* 487 */     this.onSelectNextRow.run();
/*     */   }
/*     */ 
/*     */   private void selectFirstRow() {
/* 491 */     if (((TreeView)getControl()).impl_getTreeItemCount() > 0) {
/* 492 */       ((TreeView)getControl()).getSelectionModel().clearAndSelect(0);
/* 493 */       if (this.onMoveToFirstCell != null) this.onMoveToFirstCell.run(); 
/*     */     }
/*     */   }
/*     */ 
/*     */   private void selectLastRow()
/*     */   {
/* 498 */     ((TreeView)getControl()).getSelectionModel().clearAndSelect(((TreeView)getControl()).impl_getTreeItemCount() - 1);
/* 499 */     this.onMoveToLastCell.run();
/*     */   }
/*     */ 
/*     */   private void selectAllToFirstRow() {
/* 503 */     MultipleSelectionModel localMultipleSelectionModel = ((TreeView)getControl()).getSelectionModel();
/* 504 */     if (localMultipleSelectionModel == null) return;
/*     */ 
/* 506 */     int i = localMultipleSelectionModel.getSelectedIndex();
/*     */ 
/* 508 */     if (this.isShiftDown) {
/* 509 */       i = getAnchor() == -1 ? localMultipleSelectionModel.getSelectedIndex() : getAnchor();
/*     */     }
/*     */ 
/* 512 */     localMultipleSelectionModel.clearSelection();
/* 513 */     localMultipleSelectionModel.selectRange(0, i + 1);
/*     */ 
/* 515 */     if (this.isShiftDown) {
/* 516 */       setAnchor(i);
/*     */     }
/*     */ 
/* 519 */     if (this.onMoveToFirstCell != null) this.onMoveToFirstCell.run(); 
/*     */   }
/*     */ 
/*     */   private void selectAllToLastRow()
/*     */   {
/* 523 */     MultipleSelectionModel localMultipleSelectionModel = ((TreeView)getControl()).getSelectionModel();
/* 524 */     if (localMultipleSelectionModel == null) return;
/*     */ 
/* 526 */     int i = localMultipleSelectionModel.getSelectedIndex();
/*     */ 
/* 528 */     if (this.isShiftDown) {
/* 529 */       i = getAnchor() == -1 ? localMultipleSelectionModel.getSelectedIndex() : getAnchor();
/*     */     }
/*     */ 
/* 532 */     localMultipleSelectionModel.clearSelection();
/* 533 */     localMultipleSelectionModel.selectRange(i, ((TreeView)getControl()).impl_getTreeItemCount() - 1);
/*     */ 
/* 535 */     if (this.isShiftDown) {
/* 536 */       setAnchor(i);
/*     */     }
/*     */ 
/* 539 */     if (this.onMoveToLastCell != null) this.onMoveToLastCell.run(); 
/*     */   }
/*     */ 
/*     */   private void selectAll()
/*     */   {
/* 543 */     ((TreeView)getControl()).getSelectionModel().selectAll();
/*     */   }
/*     */ 
/*     */   private void selectAllPageUp() {
/* 547 */     FocusModel localFocusModel = ((TreeView)getControl()).getFocusModel();
/* 548 */     if (localFocusModel == null) return;
/*     */ 
/* 550 */     int i = localFocusModel.getFocusedIndex();
/* 551 */     if (this.isShiftDown) {
/* 552 */       i = getAnchor() == -1 ? i : getAnchor();
/* 553 */       setAnchor(i);
/*     */     }
/*     */ 
/* 556 */     int j = ((Integer)this.onScrollPageUp.call(Integer.valueOf(getAnchor()))).intValue();
/*     */ 
/* 558 */     MultipleSelectionModel localMultipleSelectionModel = ((TreeView)getControl()).getSelectionModel();
/* 559 */     if (localMultipleSelectionModel == null) return;
/*     */ 
/* 561 */     this.selectionChanging = true;
/* 562 */     localMultipleSelectionModel.clearSelection();
/* 563 */     localMultipleSelectionModel.selectRange(j, i + 1);
/* 564 */     this.selectionChanging = false;
/*     */   }
/*     */ 
/*     */   private void selectAllPageDown() {
/* 568 */     FocusModel localFocusModel = ((TreeView)getControl()).getFocusModel();
/* 569 */     if (localFocusModel == null) return;
/*     */ 
/* 571 */     int i = localFocusModel.getFocusedIndex();
/* 572 */     if (this.isShiftDown) {
/* 573 */       i = getAnchor() == -1 ? i : getAnchor();
/* 574 */       setAnchor(i);
/*     */     }
/*     */ 
/* 577 */     int j = ((Integer)this.onScrollPageDown.call(Integer.valueOf(getAnchor()))).intValue();
/*     */ 
/* 579 */     MultipleSelectionModel localMultipleSelectionModel = ((TreeView)getControl()).getSelectionModel();
/* 580 */     if (localMultipleSelectionModel == null) return;
/*     */ 
/* 582 */     this.selectionChanging = true;
/* 583 */     localMultipleSelectionModel.clearSelection();
/* 584 */     localMultipleSelectionModel.selectRange(i, j + 1);
/* 585 */     this.selectionChanging = false;
/*     */   }
/*     */ 
/*     */   private void selectAllToFocus() {
/* 589 */     MultipleSelectionModel localMultipleSelectionModel = ((TreeView)getControl()).getSelectionModel();
/* 590 */     if (localMultipleSelectionModel == null) return;
/*     */ 
/* 592 */     FocusModel localFocusModel = ((TreeView)getControl()).getFocusModel();
/* 593 */     if (localFocusModel == null) return;
/*     */ 
/* 595 */     int i = localFocusModel.getFocusedIndex();
/* 596 */     int j = getAnchor();
/*     */ 
/* 598 */     localMultipleSelectionModel.clearSelection();
/* 599 */     int k = j;
/* 600 */     int m = j > i ? i - 1 : i + 1;
/* 601 */     localMultipleSelectionModel.selectRange(k, m);
/* 602 */     setAnchor(j);
/*     */   }
/*     */ 
/*     */   private void expandRow() {
/* 606 */     MultipleSelectionModel localMultipleSelectionModel = ((TreeView)getControl()).getSelectionModel();
/* 607 */     if (localMultipleSelectionModel == null) return;
/*     */ 
/* 609 */     TreeItem localTreeItem = (TreeItem)localMultipleSelectionModel.getSelectedItem();
/* 610 */     if ((localTreeItem == null) || (localTreeItem.isLeaf())) return;
/*     */ 
/* 612 */     if (localTreeItem.isExpanded())
/*     */     {
/* 614 */       ObservableList localObservableList = localTreeItem.getChildren();
/* 615 */       if (!localObservableList.isEmpty())
/* 616 */         localMultipleSelectionModel.clearAndSelect(((TreeView)getControl()).getRow((TreeItem)localObservableList.get(0)));
/*     */     }
/*     */     else {
/* 619 */       localTreeItem.setExpanded(true);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void expandAll() {
/* 624 */     TreeItem localTreeItem = ((TreeView)getControl()).getRoot();
/* 625 */     if (localTreeItem == null) return;
/*     */ 
/* 627 */     localTreeItem.setExpanded(true);
/* 628 */     expandChildren(localTreeItem);
/*     */   }
/*     */ 
/*     */   private void expandChildren(TreeItem paramTreeItem) {
/* 632 */     if (paramTreeItem == null) return;
/* 633 */     ObservableList localObservableList = paramTreeItem.getChildren();
/* 634 */     if (localObservableList == null) return;
/*     */ 
/* 636 */     for (int i = 0; i < localObservableList.size(); i++) {
/* 637 */       TreeItem localTreeItem = (TreeItem)localObservableList.get(i);
/* 638 */       if ((localTreeItem != null) && (!localTreeItem.isLeaf()))
/*     */       {
/* 640 */         localTreeItem.setExpanded(true);
/* 641 */         expandChildren(localTreeItem);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/* 646 */   private void collapseRow() { TreeItem localTreeItem1 = (TreeItem)((TreeView)getControl()).getSelectionModel().getSelectedItem();
/* 647 */     if (localTreeItem1 == null) return;
/*     */ 
/* 649 */     TreeItem localTreeItem2 = ((TreeView)getControl()).getRoot();
/* 650 */     if (localTreeItem2 == null) return;
/*     */ 
/* 654 */     if ((!((TreeView)getControl()).isShowRoot()) && (!localTreeItem1.isExpanded()) && (localTreeItem2.equals(localTreeItem1.getParent()))) {
/* 655 */       return;
/*     */     }
/*     */ 
/* 660 */     if ((localTreeItem2.equals(localTreeItem1)) && ((!localTreeItem2.isExpanded()) || (localTreeItem2.getChildren().isEmpty()))) {
/* 661 */       return;
/*     */     }
/*     */ 
/* 666 */     if ((localTreeItem1.isLeaf()) || (!localTreeItem1.isExpanded())) {
/* 667 */       ((TreeView)getControl()).getSelectionModel().clearSelection();
/* 668 */       ((TreeView)getControl()).getSelectionModel().select(localTreeItem1.getParent());
/*     */     } else {
/* 670 */       localTreeItem1.setExpanded(false);
/*     */     } }
/*     */ 
/*     */   private void cancelEdit()
/*     */   {
/* 675 */     ((TreeView)getControl()).edit(null);
/*     */   }
/*     */ 
/*     */   private void edit() {
/* 679 */     TreeItem localTreeItem = (TreeItem)((TreeView)getControl()).getSelectionModel().getSelectedItem();
/* 680 */     if (localTreeItem == null) return;
/*     */ 
/* 682 */     ((TreeView)getControl()).edit(localTreeItem);
/*     */   }
/*     */ 
/*     */   private void toggleFocusOwnerSelection() {
/* 686 */     MultipleSelectionModel localMultipleSelectionModel = ((TreeView)getControl()).getSelectionModel();
/* 687 */     if (localMultipleSelectionModel == null) return;
/*     */ 
/* 689 */     FocusModel localFocusModel = ((TreeView)getControl()).getFocusModel();
/* 690 */     if (localFocusModel == null) return;
/*     */ 
/* 692 */     int i = localFocusModel.getFocusedIndex();
/*     */ 
/* 694 */     if (localMultipleSelectionModel.isSelected(i)) {
/* 695 */       localMultipleSelectionModel.clearSelection(i);
/* 696 */       localFocusModel.focus(i);
/*     */     } else {
/* 698 */       localMultipleSelectionModel.select(i);
/*     */     }
/*     */ 
/* 701 */     setAnchor(i);
/*     */   }
/*     */ 
/*     */   private void discontinuousSelectPreviousRow()
/*     */   {
/* 709 */     MultipleSelectionModel localMultipleSelectionModel = ((TreeView)getControl()).getSelectionModel();
/* 710 */     if (localMultipleSelectionModel == null) return;
/*     */ 
/* 712 */     FocusModel localFocusModel = ((TreeView)getControl()).getFocusModel();
/* 713 */     if (localFocusModel == null) return;
/*     */ 
/* 715 */     int i = localFocusModel.getFocusedIndex() - 1;
/* 716 */     if (i < 0) return;
/* 717 */     localMultipleSelectionModel.select(i);
/*     */   }
/*     */ 
/*     */   private void discontinuousSelectNextRow() {
/* 721 */     MultipleSelectionModel localMultipleSelectionModel = ((TreeView)getControl()).getSelectionModel();
/* 722 */     if (localMultipleSelectionModel == null) return;
/*     */ 
/* 724 */     FocusModel localFocusModel = ((TreeView)getControl()).getFocusModel();
/* 725 */     if (localFocusModel == null) return;
/*     */ 
/* 727 */     int i = localFocusModel.getFocusedIndex() + 1;
/* 728 */     localMultipleSelectionModel.select(i);
/*     */   }
/*     */ 
/*     */   private void discontinuousSelectPageUp() {
/* 732 */     MultipleSelectionModel localMultipleSelectionModel = ((TreeView)getControl()).getSelectionModel();
/* 733 */     if (localMultipleSelectionModel == null) return;
/*     */ 
/* 735 */     FocusModel localFocusModel = ((TreeView)getControl()).getFocusModel();
/* 736 */     if (localFocusModel == null) return;
/*     */ 
/* 738 */     int i = localFocusModel.getFocusedIndex();
/* 739 */     int j = ((Integer)this.onScrollPageUp.call(Integer.valueOf(getAnchor()))).intValue();
/* 740 */     localMultipleSelectionModel.selectRange(j, i + 1);
/*     */   }
/*     */ 
/*     */   private void discontinuousSelectPageDown() {
/* 744 */     MultipleSelectionModel localMultipleSelectionModel = ((TreeView)getControl()).getSelectionModel();
/* 745 */     if (localMultipleSelectionModel == null) return;
/*     */ 
/* 747 */     FocusModel localFocusModel = ((TreeView)getControl()).getFocusModel();
/* 748 */     if (localFocusModel == null) return;
/*     */ 
/* 750 */     int i = localFocusModel.getFocusedIndex();
/* 751 */     int j = ((Integer)this.onScrollPageDown.call(Integer.valueOf(getAnchor()))).intValue();
/* 752 */     localMultipleSelectionModel.selectRange(i, j + 1);
/*     */   }
/*     */ 
/*     */   private void discontinuousSelectAllToFirstRow() {
/* 756 */     MultipleSelectionModel localMultipleSelectionModel = ((TreeView)getControl()).getSelectionModel();
/* 757 */     if (localMultipleSelectionModel == null) return;
/*     */ 
/* 759 */     FocusModel localFocusModel = ((TreeView)getControl()).getFocusModel();
/* 760 */     if (localFocusModel == null) return;
/*     */ 
/* 762 */     int i = localFocusModel.getFocusedIndex();
/* 763 */     localMultipleSelectionModel.selectRange(0, i);
/*     */ 
/* 765 */     if (this.onMoveToFirstCell != null) this.onMoveToFirstCell.run(); 
/*     */   }
/*     */ 
/*     */   private void discontinuousSelectAllToLastRow()
/*     */   {
/* 769 */     MultipleSelectionModel localMultipleSelectionModel = ((TreeView)getControl()).getSelectionModel();
/* 770 */     if (localMultipleSelectionModel == null) return;
/*     */ 
/* 772 */     FocusModel localFocusModel = ((TreeView)getControl()).getFocusModel();
/* 773 */     if (localFocusModel == null) return;
/*     */ 
/* 775 */     int i = localFocusModel.getFocusedIndex() + 1;
/* 776 */     localMultipleSelectionModel.selectRange(i, ((TreeView)getControl()).impl_getTreeItemCount());
/*     */ 
/* 778 */     if (this.onMoveToLastCell != null) this.onMoveToLastCell.run();
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  55 */     TREE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.TAB, "TraverseNext"));
/*  56 */     TREE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.TAB, "TraversePrevious").shift());
/*     */ 
/*  58 */     TREE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.HOME, "SelectFirstRow"));
/*  59 */     TREE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.END, "SelectLastRow"));
/*  60 */     TREE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.HOME, "SelectAllToFirstRow").shift());
/*  61 */     TREE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.END, "SelectAllToLastRow").shift());
/*  62 */     TREE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.PAGE_UP, "SelectAllPageUp").shift());
/*  63 */     TREE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.PAGE_DOWN, "SelectAllPageDown").shift());
/*     */ 
/*  65 */     TREE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.SPACE, "SelectAllToFocus").shift());
/*     */ 
/*  67 */     TREE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.HOME, "FocusFirstRow").ctrl());
/*  68 */     TREE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.END, "FocusLastRow").ctrl());
/*     */ 
/*  70 */     TREE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.PAGE_UP, "ScrollUp"));
/*  71 */     TREE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.PAGE_DOWN, "ScrollDown"));
/*     */ 
/*  73 */     if (PlatformUtil.isMac()) {
/*  74 */       TREE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.A, "SelectAll").meta());
/*  75 */       TREE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.SPACE, "toggleFocusOwnerSelection").ctrl().meta());
/*  76 */       TREE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.PAGE_UP, "FocusPageUp").meta());
/*  77 */       TREE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.PAGE_DOWN, "FocusPageDown").meta());
/*  78 */       TREE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.UP, "FocusPreviousRow").meta());
/*  79 */       TREE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.DOWN, "FocusNextRow").meta());
/*     */ 
/*  81 */       TREE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.UP, "DiscontinuousSelectPreviousRow").meta().shift());
/*  82 */       TREE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.DOWN, "DiscontinuousSelectNextRow").meta().shift());
/*  83 */       TREE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.PAGE_UP, "DiscontinuousSelectPageUp").meta().shift());
/*  84 */       TREE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.PAGE_DOWN, "DiscontinuousSelectPageDown").meta().shift());
/*  85 */       TREE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.HOME, "DiscontinuousSelectAllToFirstRow").meta().shift());
/*  86 */       TREE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.END, "DiscontinuousSelectAllToLastRow").meta().shift());
/*     */     } else {
/*  88 */       TREE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.A, "SelectAll").ctrl());
/*  89 */       TREE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.SPACE, "toggleFocusOwnerSelection").ctrl());
/*  90 */       TREE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.PAGE_UP, "FocusPageUp").ctrl());
/*  91 */       TREE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.PAGE_DOWN, "FocusPageDown").ctrl());
/*  92 */       TREE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.UP, "FocusPreviousRow").ctrl());
/*  93 */       TREE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.DOWN, "FocusNextRow").ctrl());
/*     */ 
/*  95 */       TREE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.UP, "DiscontinuousSelectPreviousRow").ctrl().shift());
/*  96 */       TREE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.DOWN, "DiscontinuousSelectNextRow").ctrl().shift());
/*  97 */       TREE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.PAGE_UP, "DiscontinuousSelectPageUp").ctrl().shift());
/*  98 */       TREE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.PAGE_DOWN, "DiscontinuousSelectPageDown").ctrl().shift());
/*  99 */       TREE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.HOME, "DiscontinuousSelectAllToFirstRow").ctrl().shift());
/* 100 */       TREE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.END, "DiscontinuousSelectAllToLastRow").ctrl().shift());
/*     */     }
/*     */ 
/* 103 */     TREE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.LEFT, "CollapseRow"));
/* 104 */     TREE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.KP_LEFT, "CollapseRow"));
/* 105 */     TREE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.RIGHT, "ExpandRow"));
/* 106 */     TREE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.KP_RIGHT, "ExpandRow"));
/*     */ 
/* 108 */     TREE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.MULTIPLY, "ExpandAll"));
/* 109 */     TREE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.ADD, "ExpandRow"));
/* 110 */     TREE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.SUBTRACT, "CollapseRow"));
/*     */ 
/* 112 */     TREE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.UP, "SelectPreviousRow"));
/* 113 */     TREE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.KP_UP, "SelectPreviousRow"));
/* 114 */     TREE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.DOWN, "SelectNextRow"));
/* 115 */     TREE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.KP_DOWN, "SelectNextRow"));
/*     */ 
/* 117 */     TREE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.UP, "AlsoSelectPreviousRow").shift());
/* 118 */     TREE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.KP_UP, "AlsoSelectPreviousRow").shift());
/* 119 */     TREE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.DOWN, "AlsoSelectNextRow").shift());
/* 120 */     TREE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.KP_DOWN, "AlsoSelectNextRow").shift());
/*     */ 
/* 122 */     TREE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.ENTER, "Edit"));
/* 123 */     TREE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.F2, "Edit"));
/* 124 */     TREE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.ESCAPE, "CancelEdit"));
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.behavior.TreeViewBehavior
 * JD-Core Version:    0.6.2
 */