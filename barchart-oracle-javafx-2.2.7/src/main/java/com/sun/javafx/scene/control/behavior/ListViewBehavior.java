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
/*     */ import javafx.event.EventType;
/*     */ import javafx.geometry.Orientation;
/*     */ import javafx.scene.control.Control;
/*     */ import javafx.scene.control.FocusModel;
/*     */ import javafx.scene.control.ListView;
/*     */ import javafx.scene.control.MultipleSelectionModel;
/*     */ import javafx.scene.input.KeyCode;
/*     */ import javafx.scene.input.KeyEvent;
/*     */ import javafx.scene.input.MouseEvent;
/*     */ import javafx.util.Callback;
/*     */ 
/*     */ public class ListViewBehavior<T> extends BehaviorBase<ListView<T>>
/*     */ {
/*  78 */   protected static final List<KeyBinding> LIST_VIEW_BINDINGS = new ArrayList();
/*     */ 
/* 244 */   private boolean isShiftDown = false;
/* 245 */   private boolean isCtrlDown = false;
/*     */   private Callback<Integer, Integer> onScrollPageUp;
/*     */   private Callback<Integer, Integer> onScrollPageDown;
/*     */   private Runnable onFocusPreviousRow;
/*     */   private Runnable onFocusNextRow;
/*     */   private Runnable onSelectPreviousRow;
/*     */   private Runnable onSelectNextRow;
/*     */   private Runnable onMoveToFirstCell;
/*     */   private Runnable onMoveToLastCell;
/* 265 */   private boolean selectionChanging = false;
/*     */ 
/* 267 */   private ListChangeListener<Integer> selectedIndicesListener = new ListChangeListener() {
/*     */     public void onChanged(ListChangeListener.Change paramAnonymousChange) {
/* 269 */       while (paramAnonymousChange.next())
/*     */       {
/* 271 */         if (!ListViewBehavior.this.selectionChanging) {
/* 272 */           if (paramAnonymousChange.getList().isEmpty())
/* 273 */             ListViewBehavior.this.setAnchor(-1);
/* 274 */           else if (!paramAnonymousChange.getList().contains(Integer.valueOf(ListViewBehavior.this.getAnchor()))) {
/* 275 */             ListViewBehavior.this.setAnchor(-1);
/*     */           }
/*     */         }
/*     */ 
/* 279 */         int i = paramAnonymousChange.getAddedSize();
/* 280 */         if ((!ListViewBehavior.this.hasAnchor()) && (i > 0)) {
/* 281 */           List localList = paramAnonymousChange.getAddedSubList();
/* 282 */           for (int j = 0; j < i; j++) {
/* 283 */             int k = ((Integer)localList.get(j)).intValue();
/* 284 */             if (k >= 0) {
/* 285 */               ListViewBehavior.this.setAnchor(k);
/* 286 */               break;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 267 */   };
/*     */ 
/* 294 */   private final ListChangeListener itemsListListener = new ListChangeListener() {
/*     */     public void onChanged(ListChangeListener.Change paramAnonymousChange) {
/* 296 */       while (paramAnonymousChange.next())
/* 297 */         if ((paramAnonymousChange.wasAdded()) && (paramAnonymousChange.getFrom() <= ListViewBehavior.this.getAnchor()))
/* 298 */           ListViewBehavior.this.setAnchor(ListViewBehavior.access$200(ListViewBehavior.this) + paramAnonymousChange.getAddedSize());
/* 299 */         else if ((paramAnonymousChange.wasRemoved()) && (paramAnonymousChange.getFrom() <= ListViewBehavior.this.getAnchor()))
/* 300 */           ListViewBehavior.this.setAnchor(ListViewBehavior.access$200(ListViewBehavior.this) - paramAnonymousChange.getRemovedSize());
/*     */     }
/* 294 */   };
/*     */ 
/*     */   protected void callAction(String paramString)
/*     */   {
/* 193 */     if ("SelectPreviousRow".equals(paramString)) selectPreviousRow();
/* 194 */     else if ("SelectNextRow".equals(paramString)) selectNextRow();
/* 195 */     else if ("SelectFirstRow".equals(paramString)) selectFirstRow();
/* 196 */     else if ("SelectLastRow".equals(paramString)) selectLastRow();
/* 197 */     else if ("SelectAllToFirstRow".equals(paramString)) selectAllToFirstRow();
/* 198 */     else if ("SelectAllToLastRow".equals(paramString)) selectAllToLastRow();
/* 199 */     else if ("SelectAllPageUp".equals(paramString)) selectAllPageUp();
/* 200 */     else if ("SelectAllPageDown".equals(paramString)) selectAllPageDown();
/* 201 */     else if ("AlsoSelectNextRow".equals(paramString)) alsoSelectNextRow();
/* 202 */     else if ("AlsoSelectPreviousRow".equals(paramString)) alsoSelectPreviousRow();
/* 203 */     else if ("ClearSelection".equals(paramString)) clearSelection();
/* 204 */     else if ("SelectAll".equals(paramString)) selectAll();
/* 205 */     else if ("ScrollUp".equals(paramString)) scrollPageUp();
/* 206 */     else if ("ScrollDown".equals(paramString)) scrollPageDown();
/* 207 */     else if ("FocusPreviousRow".equals(paramString)) focusPreviousRow();
/* 208 */     else if ("FocusNextRow".equals(paramString)) focusNextRow();
/* 209 */     else if ("FocusPageUp".equals(paramString)) focusPageUp();
/* 210 */     else if ("FocusPageDown".equals(paramString)) focusPageDown();
/* 211 */     else if ("Activate".equals(paramString)) activate();
/* 212 */     else if ("CancelEdit".equals(paramString)) cancelEdit();
/* 213 */     else if ("FocusFirstRow".equals(paramString)) focusFirstRow();
/* 214 */     else if ("FocusLastRow".equals(paramString)) focusLastRow();
/* 215 */     else if ("toggleFocusOwnerSelection".equals(paramString)) toggleFocusOwnerSelection();
/* 216 */     else if ("SelectAllToFocus".equals(paramString)) selectAllToFocus();
/* 217 */     else if ("DiscontinuousSelectNextRow".equals(paramString)) discontinuousSelectNextRow();
/* 218 */     else if ("DiscontinuousSelectPreviousRow".equals(paramString)) discontinuousSelectPreviousRow();
/* 219 */     else if ("DiscontinuousSelectPageUp".equals(paramString)) discontinuousSelectPageUp();
/* 220 */     else if ("DiscontinuousSelectPageDown".equals(paramString)) discontinuousSelectPageDown();
/* 221 */     else if ("DiscontinuousSelectAllToLastRow".equals(paramString)) discontinuousSelectAllToLastRow();
/* 222 */     else if ("DiscontinuousSelectAllToFirstRow".equals(paramString)) discontinuousSelectAllToFirstRow(); else
/* 223 */       super.callAction(paramString);
/*     */   }
/*     */ 
/*     */   protected List<KeyBinding> createKeyBindings() {
/* 227 */     return LIST_VIEW_BINDINGS;
/*     */   }
/*     */ 
/*     */   protected void callActionForEvent(KeyEvent paramKeyEvent)
/*     */   {
/* 234 */     this.isShiftDown = ((paramKeyEvent.getEventType() == KeyEvent.KEY_PRESSED) && (paramKeyEvent.isShiftDown()));
/* 235 */     this.isCtrlDown = ((paramKeyEvent.getEventType() == KeyEvent.KEY_PRESSED) && (paramKeyEvent.isControlDown()));
/*     */ 
/* 237 */     super.callActionForEvent(paramKeyEvent);
/*     */   }
/*     */ 
/*     */   public void setOnScrollPageUp(Callback<Integer, Integer> paramCallback)
/*     */   {
/* 256 */     this.onScrollPageUp = paramCallback; } 
/* 257 */   public void setOnScrollPageDown(Callback<Integer, Integer> paramCallback) { this.onScrollPageDown = paramCallback; } 
/* 258 */   public void setOnFocusPreviousRow(Runnable paramRunnable) { this.onFocusPreviousRow = paramRunnable; } 
/* 259 */   public void setOnFocusNextRow(Runnable paramRunnable) { this.onFocusNextRow = paramRunnable; } 
/* 260 */   public void setOnSelectPreviousRow(Runnable paramRunnable) { this.onSelectPreviousRow = paramRunnable; } 
/* 261 */   public void setOnSelectNextRow(Runnable paramRunnable) { this.onSelectNextRow = paramRunnable; } 
/* 262 */   public void setOnMoveToFirstCell(Runnable paramRunnable) { this.onMoveToFirstCell = paramRunnable; } 
/* 263 */   public void setOnMoveToLastCell(Runnable paramRunnable) { this.onMoveToLastCell = paramRunnable; }
/*     */ 
/*     */ 
/*     */   public ListViewBehavior(ListView paramListView)
/*     */   {
/* 307 */     super(paramListView);
/*     */ 
/* 309 */     paramListView.itemsProperty().addListener(new ChangeListener()
/*     */     {
/*     */       public void changed(ObservableValue paramAnonymousObservableValue, ObservableList paramAnonymousObservableList1, ObservableList paramAnonymousObservableList2)
/*     */       {
/* 313 */         if (paramAnonymousObservableList1 != null)
/* 314 */           paramAnonymousObservableList1.removeListener(ListViewBehavior.this.itemsListListener);
/* 315 */         if (paramAnonymousObservableList2 != null)
/* 316 */           paramAnonymousObservableList2.addListener(ListViewBehavior.this.itemsListListener);
/*     */       }
/*     */     });
/* 320 */     if (paramListView.getItems() != null) {
/* 321 */       paramListView.getItems().addListener(this.itemsListListener);
/*     */     }
/*     */ 
/* 325 */     ((ListView)getControl()).selectionModelProperty().addListener(new ChangeListener()
/*     */     {
/*     */       public void changed(ObservableValue<? extends MultipleSelectionModel<T>> paramAnonymousObservableValue, MultipleSelectionModel<T> paramAnonymousMultipleSelectionModel1, MultipleSelectionModel<T> paramAnonymousMultipleSelectionModel2)
/*     */       {
/* 330 */         if (paramAnonymousMultipleSelectionModel1 != null) {
/* 331 */           paramAnonymousMultipleSelectionModel1.getSelectedIndices().removeListener(ListViewBehavior.this.selectedIndicesListener);
/*     */         }
/* 333 */         if (paramAnonymousMultipleSelectionModel2 != null)
/* 334 */           paramAnonymousMultipleSelectionModel2.getSelectedIndices().addListener(ListViewBehavior.this.selectedIndicesListener);
/*     */       }
/*     */     });
/* 338 */     if (paramListView.getSelectionModel() != null)
/* 339 */       paramListView.getSelectionModel().getSelectedIndices().addListener(this.selectedIndicesListener);
/*     */   }
/*     */ 
/*     */   private void setAnchor(int paramInt)
/*     */   {
/* 344 */     ListCellBehavior.setAnchor((ListView)getControl(), paramInt);
/*     */   }
/*     */ 
/*     */   private int getAnchor() {
/* 348 */     return ListCellBehavior.getAnchor((ListView)getControl());
/*     */   }
/*     */ 
/*     */   private boolean hasAnchor() {
/* 352 */     return ListCellBehavior.hasAnchor((ListView)getControl());
/*     */   }
/*     */ 
/*     */   public void mousePressed(MouseEvent paramMouseEvent) {
/* 356 */     super.mousePressed(paramMouseEvent);
/*     */ 
/* 358 */     if (!paramMouseEvent.isShiftDown()) {
/* 359 */       int i = ((ListView)getControl()).getSelectionModel().getSelectedIndex();
/* 360 */       setAnchor(i);
/*     */     }
/*     */ 
/* 363 */     if ((!((ListView)getControl()).isFocused()) && (((ListView)getControl()).isFocusTraversable()))
/* 364 */       ((ListView)getControl()).requestFocus();
/*     */   }
/*     */ 
/*     */   private int getRowCount()
/*     */   {
/* 369 */     ObservableList localObservableList = ((ListView)getControl()).getItems();
/* 370 */     if (localObservableList == null) return 0;
/*     */ 
/* 372 */     return localObservableList.size();
/*     */   }
/*     */ 
/*     */   private void clearSelection() {
/* 376 */     ((ListView)getControl()).getSelectionModel().clearSelection();
/*     */   }
/*     */ 
/*     */   private void scrollPageUp() {
/* 380 */     int i = -1;
/* 381 */     if (this.onScrollPageUp != null) {
/* 382 */       i = ((Integer)this.onScrollPageUp.call(Integer.valueOf(getAnchor()))).intValue();
/*     */     }
/* 384 */     if (i == -1) return;
/*     */ 
/* 386 */     MultipleSelectionModel localMultipleSelectionModel = ((ListView)getControl()).getSelectionModel();
/* 387 */     if (localMultipleSelectionModel == null) return;
/* 388 */     localMultipleSelectionModel.clearAndSelect(i);
/*     */   }
/*     */ 
/*     */   private void scrollPageDown() {
/* 392 */     int i = -1;
/* 393 */     if (this.onScrollPageDown != null) {
/* 394 */       i = ((Integer)this.onScrollPageDown.call(Integer.valueOf(getAnchor()))).intValue();
/*     */     }
/* 396 */     if (i == -1) return;
/*     */ 
/* 398 */     MultipleSelectionModel localMultipleSelectionModel = ((ListView)getControl()).getSelectionModel();
/* 399 */     if (localMultipleSelectionModel == null) return;
/* 400 */     localMultipleSelectionModel.clearAndSelect(i);
/*     */   }
/*     */ 
/*     */   private void focusFirstRow() {
/* 404 */     FocusModel localFocusModel = ((ListView)getControl()).getFocusModel();
/* 405 */     if (localFocusModel == null) return;
/* 406 */     localFocusModel.focus(0);
/*     */ 
/* 408 */     if (this.onMoveToFirstCell != null) this.onMoveToFirstCell.run(); 
/*     */   }
/*     */ 
/*     */   private void focusLastRow()
/*     */   {
/* 412 */     FocusModel localFocusModel = ((ListView)getControl()).getFocusModel();
/* 413 */     if (localFocusModel == null) return;
/* 414 */     localFocusModel.focus(getRowCount() - 1);
/*     */ 
/* 416 */     if (this.onMoveToLastCell != null) this.onMoveToLastCell.run(); 
/*     */   }
/*     */ 
/*     */   private void focusPreviousRow()
/*     */   {
/* 420 */     FocusModel localFocusModel = ((ListView)getControl()).getFocusModel();
/* 421 */     if (localFocusModel == null) return;
/*     */ 
/* 423 */     MultipleSelectionModel localMultipleSelectionModel = ((ListView)getControl()).getSelectionModel();
/* 424 */     if (localMultipleSelectionModel == null) return;
/*     */ 
/* 426 */     localFocusModel.focusPrevious();
/*     */ 
/* 428 */     if ((!this.isCtrlDown) || (getAnchor() == -1)) {
/* 429 */       setAnchor(localFocusModel.getFocusedIndex());
/*     */     }
/*     */ 
/* 432 */     if (this.onFocusPreviousRow != null) this.onFocusPreviousRow.run(); 
/*     */   }
/*     */ 
/*     */   private void focusNextRow()
/*     */   {
/* 436 */     FocusModel localFocusModel = ((ListView)getControl()).getFocusModel();
/* 437 */     if (localFocusModel == null) return;
/*     */ 
/* 439 */     MultipleSelectionModel localMultipleSelectionModel = ((ListView)getControl()).getSelectionModel();
/* 440 */     if (localMultipleSelectionModel == null) return;
/*     */ 
/* 442 */     localFocusModel.focusNext();
/*     */ 
/* 444 */     if ((!this.isCtrlDown) || (getAnchor() == -1)) {
/* 445 */       setAnchor(localFocusModel.getFocusedIndex());
/*     */     }
/*     */ 
/* 448 */     if (this.onFocusNextRow != null) this.onFocusNextRow.run(); 
/*     */   }
/*     */ 
/*     */   private void focusPageUp()
/*     */   {
/* 452 */     int i = ((Integer)this.onScrollPageUp.call(Integer.valueOf(getAnchor()))).intValue();
/*     */ 
/* 454 */     FocusModel localFocusModel = ((ListView)getControl()).getFocusModel();
/* 455 */     if (localFocusModel == null) return;
/* 456 */     localFocusModel.focus(i);
/*     */   }
/*     */ 
/*     */   private void focusPageDown() {
/* 460 */     int i = ((Integer)this.onScrollPageDown.call(Integer.valueOf(getAnchor()))).intValue();
/*     */ 
/* 462 */     FocusModel localFocusModel = ((ListView)getControl()).getFocusModel();
/* 463 */     if (localFocusModel == null) return;
/* 464 */     localFocusModel.focus(i);
/*     */   }
/*     */ 
/*     */   private void alsoSelectPreviousRow() {
/* 468 */     FocusModel localFocusModel = ((ListView)getControl()).getFocusModel();
/* 469 */     if (localFocusModel == null) return;
/*     */ 
/* 471 */     MultipleSelectionModel localMultipleSelectionModel = ((ListView)getControl()).getSelectionModel();
/* 472 */     if (localMultipleSelectionModel == null) return;
/*     */ 
/* 474 */     if ((this.isShiftDown) && (getAnchor() != -1)) {
/* 475 */       int i = localFocusModel.getFocusedIndex() - 1;
/* 476 */       int j = getAnchor();
/*     */ 
/* 478 */       if (!hasAnchor()) {
/* 479 */         setAnchor(localFocusModel.getFocusedIndex());
/*     */       }
/*     */ 
/* 482 */       clearSelectionOutsideRange(j, i);
/*     */ 
/* 484 */       if (j > i)
/* 485 */         localMultipleSelectionModel.selectRange(j, i - 1);
/*     */       else
/* 487 */         localMultipleSelectionModel.selectRange(j, i + 1);
/*     */     }
/*     */     else {
/* 490 */       localMultipleSelectionModel.selectPrevious();
/*     */     }
/*     */ 
/* 493 */     this.onSelectPreviousRow.run();
/*     */   }
/*     */ 
/*     */   private void alsoSelectNextRow() {
/* 497 */     FocusModel localFocusModel = ((ListView)getControl()).getFocusModel();
/* 498 */     if (localFocusModel == null) return;
/*     */ 
/* 500 */     MultipleSelectionModel localMultipleSelectionModel = ((ListView)getControl()).getSelectionModel();
/* 501 */     if (localMultipleSelectionModel == null) return;
/*     */ 
/* 503 */     if ((this.isShiftDown) && (getAnchor() != -1)) {
/* 504 */       int i = localFocusModel.getFocusedIndex() + 1;
/* 505 */       int j = getAnchor();
/*     */ 
/* 507 */       if (!hasAnchor()) {
/* 508 */         setAnchor(localFocusModel.getFocusedIndex());
/*     */       }
/*     */ 
/* 511 */       clearSelectionOutsideRange(j, i);
/*     */ 
/* 513 */       if (j > i)
/* 514 */         localMultipleSelectionModel.selectRange(j, i - 1);
/*     */       else
/* 516 */         localMultipleSelectionModel.selectRange(j, i + 1);
/*     */     }
/*     */     else {
/* 519 */       localMultipleSelectionModel.selectNext();
/*     */     }
/*     */ 
/* 522 */     this.onSelectNextRow.run();
/*     */   }
/*     */ 
/*     */   private void clearSelectionOutsideRange(int paramInt1, int paramInt2) {
/* 526 */     MultipleSelectionModel localMultipleSelectionModel = ((ListView)getControl()).getSelectionModel();
/* 527 */     if (localMultipleSelectionModel == null) return;
/*     */ 
/* 529 */     int i = Math.min(paramInt1, paramInt2);
/* 530 */     int j = Math.max(paramInt1, paramInt2);
/*     */ 
/* 532 */     ArrayList localArrayList = new ArrayList(localMultipleSelectionModel.getSelectedIndices());
/*     */ 
/* 534 */     this.selectionChanging = true;
/* 535 */     for (int k = 0; k < localArrayList.size(); k++) {
/* 536 */       int m = ((Integer)localArrayList.get(k)).intValue();
/* 537 */       if ((m < i) || (m >= j)) {
/* 538 */         localMultipleSelectionModel.clearSelection(m);
/*     */       }
/*     */     }
/* 541 */     this.selectionChanging = false;
/*     */   }
/*     */ 
/*     */   private void selectPreviousRow() {
/* 545 */     FocusModel localFocusModel = ((ListView)getControl()).getFocusModel();
/* 546 */     if (localFocusModel == null) return;
/*     */ 
/* 548 */     int i = localFocusModel.getFocusedIndex();
/* 549 */     if (i <= 0) {
/* 550 */       return;
/*     */     }
/*     */ 
/* 553 */     setAnchor(i - 1);
/* 554 */     ((ListView)getControl()).getSelectionModel().clearAndSelect(i - 1);
/* 555 */     this.onSelectPreviousRow.run();
/*     */   }
/*     */ 
/*     */   private void selectNextRow() {
/* 559 */     FocusModel localFocusModel = ((ListView)getControl()).getFocusModel();
/* 560 */     if (localFocusModel == null) return;
/*     */ 
/* 562 */     int i = localFocusModel.getFocusedIndex();
/* 563 */     if (i == getRowCount() - 1) {
/* 564 */       return;
/*     */     }
/*     */ 
/* 567 */     MultipleSelectionModel localMultipleSelectionModel = ((ListView)getControl()).getSelectionModel();
/* 568 */     if (localMultipleSelectionModel == null) return;
/*     */ 
/* 570 */     setAnchor(i + 1);
/* 571 */     localMultipleSelectionModel.clearAndSelect(i + 1);
/* 572 */     if (this.onSelectNextRow != null) this.onSelectNextRow.run(); 
/*     */   }
/*     */ 
/*     */   private void selectFirstRow()
/*     */   {
/* 576 */     if (getRowCount() > 0) {
/* 577 */       ((ListView)getControl()).getSelectionModel().clearAndSelect(0);
/* 578 */       if (this.onMoveToFirstCell != null) this.onMoveToFirstCell.run(); 
/*     */     }
/*     */   }
/*     */ 
/*     */   private void selectLastRow()
/*     */   {
/* 583 */     ((ListView)getControl()).getSelectionModel().clearAndSelect(getRowCount() - 1);
/* 584 */     if (this.onMoveToLastCell != null) this.onMoveToLastCell.run(); 
/*     */   }
/*     */ 
/*     */   private void selectAllPageUp()
/*     */   {
/* 588 */     FocusModel localFocusModel = ((ListView)getControl()).getFocusModel();
/* 589 */     if (localFocusModel == null) return;
/*     */ 
/* 591 */     int i = localFocusModel.getFocusedIndex();
/* 592 */     if (this.isShiftDown) {
/* 593 */       i = getAnchor() == -1 ? i : getAnchor();
/* 594 */       setAnchor(i);
/*     */     }
/*     */ 
/* 597 */     int j = ((Integer)this.onScrollPageUp.call(Integer.valueOf(getAnchor()))).intValue();
/*     */ 
/* 599 */     MultipleSelectionModel localMultipleSelectionModel = ((ListView)getControl()).getSelectionModel();
/* 600 */     if (localMultipleSelectionModel == null) return;
/*     */ 
/* 602 */     this.selectionChanging = true;
/* 603 */     localMultipleSelectionModel.clearSelection();
/* 604 */     localMultipleSelectionModel.selectRange(j, i + 1);
/* 605 */     this.selectionChanging = false;
/*     */   }
/*     */ 
/*     */   private void selectAllPageDown() {
/* 609 */     FocusModel localFocusModel = ((ListView)getControl()).getFocusModel();
/* 610 */     if (localFocusModel == null) return;
/*     */ 
/* 612 */     int i = localFocusModel.getFocusedIndex();
/* 613 */     if (this.isShiftDown) {
/* 614 */       i = getAnchor() == -1 ? i : getAnchor();
/* 615 */       setAnchor(i);
/*     */     }
/*     */ 
/* 618 */     int j = ((Integer)this.onScrollPageDown.call(Integer.valueOf(getAnchor()))).intValue();
/*     */ 
/* 620 */     MultipleSelectionModel localMultipleSelectionModel = ((ListView)getControl()).getSelectionModel();
/* 621 */     if (localMultipleSelectionModel == null) return;
/*     */ 
/* 623 */     this.selectionChanging = true;
/* 624 */     localMultipleSelectionModel.clearSelection();
/* 625 */     localMultipleSelectionModel.selectRange(i, j + 1);
/* 626 */     this.selectionChanging = false;
/*     */   }
/*     */ 
/*     */   private void selectAllToFirstRow() {
/* 630 */     MultipleSelectionModel localMultipleSelectionModel = ((ListView)getControl()).getSelectionModel();
/* 631 */     if (localMultipleSelectionModel == null) return;
/*     */ 
/* 633 */     int i = localMultipleSelectionModel.getSelectedIndex();
/*     */ 
/* 635 */     if (this.isShiftDown) {
/* 636 */       i = hasAnchor() ? localMultipleSelectionModel.getSelectedIndex() : getAnchor();
/*     */     }
/*     */ 
/* 639 */     localMultipleSelectionModel.clearSelection();
/* 640 */     localMultipleSelectionModel.selectRange(0, i + 1);
/*     */ 
/* 642 */     if (this.isShiftDown) {
/* 643 */       setAnchor(i);
/*     */     }
/*     */ 
/* 647 */     ((ListView)getControl()).getFocusModel().focus(0);
/*     */ 
/* 649 */     if (this.onMoveToFirstCell != null) this.onMoveToFirstCell.run(); 
/*     */   }
/*     */ 
/*     */   private void selectAllToLastRow()
/*     */   {
/* 653 */     MultipleSelectionModel localMultipleSelectionModel = ((ListView)getControl()).getSelectionModel();
/* 654 */     if (localMultipleSelectionModel == null) return;
/*     */ 
/* 656 */     int i = localMultipleSelectionModel.getSelectedIndex();
/*     */ 
/* 658 */     if (this.isShiftDown) {
/* 659 */       i = hasAnchor() ? localMultipleSelectionModel.getSelectedIndex() : getAnchor();
/*     */     }
/*     */ 
/* 662 */     localMultipleSelectionModel.clearSelection();
/* 663 */     localMultipleSelectionModel.selectRange(i, getRowCount());
/*     */ 
/* 665 */     if (this.isShiftDown) {
/* 666 */       setAnchor(i);
/*     */     }
/*     */ 
/* 669 */     if (this.onMoveToLastCell != null) this.onMoveToLastCell.run(); 
/*     */   }
/*     */ 
/*     */   private void selectAll()
/*     */   {
/* 673 */     MultipleSelectionModel localMultipleSelectionModel = ((ListView)getControl()).getSelectionModel();
/* 674 */     if (localMultipleSelectionModel == null) return;
/* 675 */     localMultipleSelectionModel.selectAll();
/*     */   }
/*     */ 
/*     */   private void selectAllToFocus() {
/* 679 */     MultipleSelectionModel localMultipleSelectionModel = ((ListView)getControl()).getSelectionModel();
/* 680 */     if (localMultipleSelectionModel == null) return;
/*     */ 
/* 682 */     FocusModel localFocusModel = ((ListView)getControl()).getFocusModel();
/* 683 */     if (localFocusModel == null) return;
/*     */ 
/* 685 */     int i = localFocusModel.getFocusedIndex();
/* 686 */     int j = getAnchor();
/*     */ 
/* 688 */     localMultipleSelectionModel.clearSelection();
/* 689 */     int k = j;
/* 690 */     int m = j > i ? i - 1 : i + 1;
/* 691 */     localMultipleSelectionModel.selectRange(k, m);
/* 692 */     setAnchor(j);
/*     */   }
/*     */ 
/*     */   private void cancelEdit() {
/* 696 */     ((ListView)getControl()).edit(-1);
/*     */   }
/*     */ 
/*     */   private void activate() {
/* 700 */     int i = ((ListView)getControl()).getFocusModel().getFocusedIndex();
/* 701 */     ((ListView)getControl()).getSelectionModel().select(i);
/*     */ 
/* 704 */     ((ListView)getControl()).edit(i);
/*     */   }
/*     */ 
/*     */   private void toggleFocusOwnerSelection() {
/* 708 */     MultipleSelectionModel localMultipleSelectionModel = ((ListView)getControl()).getSelectionModel();
/* 709 */     if (localMultipleSelectionModel == null) return;
/*     */ 
/* 711 */     FocusModel localFocusModel = ((ListView)getControl()).getFocusModel();
/* 712 */     if (localFocusModel == null) return;
/*     */ 
/* 714 */     int i = localFocusModel.getFocusedIndex();
/*     */ 
/* 716 */     if (localMultipleSelectionModel.isSelected(i)) {
/* 717 */       localMultipleSelectionModel.clearSelection(i);
/* 718 */       localFocusModel.focus(i);
/*     */     } else {
/* 720 */       localMultipleSelectionModel.select(i);
/*     */     }
/*     */ 
/* 723 */     setAnchor(i);
/*     */   }
/*     */ 
/*     */   private void discontinuousSelectPreviousRow()
/*     */   {
/* 731 */     MultipleSelectionModel localMultipleSelectionModel = ((ListView)getControl()).getSelectionModel();
/* 732 */     if (localMultipleSelectionModel == null) return;
/*     */ 
/* 734 */     FocusModel localFocusModel = ((ListView)getControl()).getFocusModel();
/* 735 */     if (localFocusModel == null) return;
/*     */ 
/* 737 */     int i = localFocusModel.getFocusedIndex() - 1;
/* 738 */     if (i < 0) return;
/* 739 */     localMultipleSelectionModel.select(i);
/*     */   }
/*     */ 
/*     */   private void discontinuousSelectNextRow() {
/* 743 */     MultipleSelectionModel localMultipleSelectionModel = ((ListView)getControl()).getSelectionModel();
/* 744 */     if (localMultipleSelectionModel == null) return;
/*     */ 
/* 746 */     FocusModel localFocusModel = ((ListView)getControl()).getFocusModel();
/* 747 */     if (localFocusModel == null) return;
/*     */ 
/* 749 */     int i = localFocusModel.getFocusedIndex() + 1;
/* 750 */     localMultipleSelectionModel.select(i);
/*     */   }
/*     */ 
/*     */   private void discontinuousSelectPageUp() {
/* 754 */     MultipleSelectionModel localMultipleSelectionModel = ((ListView)getControl()).getSelectionModel();
/* 755 */     if (localMultipleSelectionModel == null) return;
/*     */ 
/* 757 */     FocusModel localFocusModel = ((ListView)getControl()).getFocusModel();
/* 758 */     if (localFocusModel == null) return;
/*     */ 
/* 760 */     int i = localFocusModel.getFocusedIndex();
/* 761 */     int j = ((Integer)this.onScrollPageUp.call(Integer.valueOf(getAnchor()))).intValue();
/* 762 */     localMultipleSelectionModel.selectRange(j, i + 1);
/*     */   }
/*     */ 
/*     */   private void discontinuousSelectPageDown() {
/* 766 */     MultipleSelectionModel localMultipleSelectionModel = ((ListView)getControl()).getSelectionModel();
/* 767 */     if (localMultipleSelectionModel == null) return;
/*     */ 
/* 769 */     FocusModel localFocusModel = ((ListView)getControl()).getFocusModel();
/* 770 */     if (localFocusModel == null) return;
/*     */ 
/* 772 */     int i = localFocusModel.getFocusedIndex();
/* 773 */     int j = ((Integer)this.onScrollPageDown.call(Integer.valueOf(getAnchor()))).intValue();
/* 774 */     localMultipleSelectionModel.selectRange(i, j + 1);
/*     */   }
/*     */ 
/*     */   private void discontinuousSelectAllToFirstRow() {
/* 778 */     MultipleSelectionModel localMultipleSelectionModel = ((ListView)getControl()).getSelectionModel();
/* 779 */     if (localMultipleSelectionModel == null) return;
/*     */ 
/* 781 */     FocusModel localFocusModel = ((ListView)getControl()).getFocusModel();
/* 782 */     if (localFocusModel == null) return;
/*     */ 
/* 784 */     int i = localFocusModel.getFocusedIndex();
/* 785 */     localMultipleSelectionModel.selectRange(0, i);
/*     */ 
/* 787 */     if (this.onMoveToFirstCell != null) this.onMoveToFirstCell.run(); 
/*     */   }
/*     */ 
/*     */   private void discontinuousSelectAllToLastRow()
/*     */   {
/* 791 */     MultipleSelectionModel localMultipleSelectionModel = ((ListView)getControl()).getSelectionModel();
/* 792 */     if (localMultipleSelectionModel == null) return;
/*     */ 
/* 794 */     FocusModel localFocusModel = ((ListView)getControl()).getFocusModel();
/* 795 */     if (localFocusModel == null) return;
/*     */ 
/* 797 */     int i = localFocusModel.getFocusedIndex() + 1;
/* 798 */     localMultipleSelectionModel.selectRange(i, getRowCount());
/*     */ 
/* 800 */     if (this.onMoveToLastCell != null) this.onMoveToLastCell.run();
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  81 */     LIST_VIEW_BINDINGS.add(new KeyBinding(KeyCode.TAB, "TraverseNext"));
/*  82 */     LIST_VIEW_BINDINGS.add(new KeyBinding(KeyCode.TAB, "TraversePrevious").shift());
/*     */ 
/*  84 */     LIST_VIEW_BINDINGS.add(new KeyBinding(KeyCode.HOME, "SelectFirstRow"));
/*  85 */     LIST_VIEW_BINDINGS.add(new KeyBinding(KeyCode.END, "SelectLastRow"));
/*  86 */     LIST_VIEW_BINDINGS.add(new KeyBinding(KeyCode.HOME, "SelectAllToFirstRow").shift());
/*  87 */     LIST_VIEW_BINDINGS.add(new KeyBinding(KeyCode.END, "SelectAllToLastRow").shift());
/*  88 */     LIST_VIEW_BINDINGS.add(new KeyBinding(KeyCode.PAGE_UP, "SelectAllPageUp").shift());
/*  89 */     LIST_VIEW_BINDINGS.add(new KeyBinding(KeyCode.PAGE_DOWN, "SelectAllPageDown").shift());
/*     */ 
/*  91 */     LIST_VIEW_BINDINGS.add(new KeyBinding(KeyCode.SPACE, "SelectAllToFocus").shift());
/*     */ 
/*  93 */     LIST_VIEW_BINDINGS.add(new KeyBinding(KeyCode.PAGE_UP, "ScrollUp"));
/*  94 */     LIST_VIEW_BINDINGS.add(new KeyBinding(KeyCode.PAGE_DOWN, "ScrollDown"));
/*     */ 
/*  96 */     LIST_VIEW_BINDINGS.add(new KeyBinding(KeyCode.ENTER, "Activate"));
/*  97 */     LIST_VIEW_BINDINGS.add(new KeyBinding(KeyCode.SPACE, "Activate"));
/*  98 */     LIST_VIEW_BINDINGS.add(new KeyBinding(KeyCode.F2, "Activate"));
/*  99 */     LIST_VIEW_BINDINGS.add(new KeyBinding(KeyCode.ESCAPE, "CancelEdit"));
/*     */ 
/* 101 */     if (PlatformUtil.isMac()) {
/* 102 */       LIST_VIEW_BINDINGS.add(new KeyBinding(KeyCode.A, "SelectAll").meta());
/* 103 */       LIST_VIEW_BINDINGS.add(new KeyBinding(KeyCode.HOME, "FocusFirstRow").meta());
/* 104 */       LIST_VIEW_BINDINGS.add(new KeyBinding(KeyCode.END, "FocusLastRow").meta());
/* 105 */       LIST_VIEW_BINDINGS.add(new KeyBinding(KeyCode.SPACE, "toggleFocusOwnerSelection").ctrl().meta());
/* 106 */       LIST_VIEW_BINDINGS.add(new KeyBinding(KeyCode.PAGE_UP, "FocusPageUp").meta());
/* 107 */       LIST_VIEW_BINDINGS.add(new KeyBinding(KeyCode.PAGE_DOWN, "FocusPageDown").meta());
/*     */     } else {
/* 109 */       LIST_VIEW_BINDINGS.add(new KeyBinding(KeyCode.A, "SelectAll").ctrl());
/* 110 */       LIST_VIEW_BINDINGS.add(new KeyBinding(KeyCode.HOME, "FocusFirstRow").ctrl());
/* 111 */       LIST_VIEW_BINDINGS.add(new KeyBinding(KeyCode.END, "FocusLastRow").ctrl());
/* 112 */       LIST_VIEW_BINDINGS.add(new KeyBinding(KeyCode.SPACE, "toggleFocusOwnerSelection").ctrl());
/* 113 */       LIST_VIEW_BINDINGS.add(new KeyBinding(KeyCode.PAGE_UP, "FocusPageUp").ctrl());
/* 114 */       LIST_VIEW_BINDINGS.add(new KeyBinding(KeyCode.PAGE_DOWN, "FocusPageDown").ctrl());
/*     */     }
/*     */ 
/* 119 */     LIST_VIEW_BINDINGS.add(new ListViewKeyBinding(KeyCode.UP, "SelectPreviousRow").vertical());
/* 120 */     LIST_VIEW_BINDINGS.add(new ListViewKeyBinding(KeyCode.KP_UP, "SelectPreviousRow").vertical());
/* 121 */     LIST_VIEW_BINDINGS.add(new ListViewKeyBinding(KeyCode.DOWN, "SelectNextRow").vertical());
/* 122 */     LIST_VIEW_BINDINGS.add(new ListViewKeyBinding(KeyCode.KP_DOWN, "SelectNextRow").vertical());
/*     */ 
/* 124 */     LIST_VIEW_BINDINGS.add(new ListViewKeyBinding(KeyCode.UP, "AlsoSelectPreviousRow").vertical().shift());
/* 125 */     LIST_VIEW_BINDINGS.add(new ListViewKeyBinding(KeyCode.KP_UP, "AlsoSelectPreviousRow").vertical().shift());
/* 126 */     LIST_VIEW_BINDINGS.add(new ListViewKeyBinding(KeyCode.DOWN, "AlsoSelectNextRow").vertical().shift());
/* 127 */     LIST_VIEW_BINDINGS.add(new ListViewKeyBinding(KeyCode.KP_DOWN, "AlsoSelectNextRow").vertical().shift());
/*     */ 
/* 129 */     LIST_VIEW_BINDINGS.add(new ListViewKeyBinding(KeyCode.LEFT, "TraverseLeft").vertical());
/* 130 */     LIST_VIEW_BINDINGS.add(new ListViewKeyBinding(KeyCode.KP_LEFT, "TraverseLeft").vertical());
/* 131 */     LIST_VIEW_BINDINGS.add(new ListViewKeyBinding(KeyCode.RIGHT, "TraverseRight").vertical());
/* 132 */     LIST_VIEW_BINDINGS.add(new ListViewKeyBinding(KeyCode.KP_RIGHT, "TraverseRight").vertical());
/*     */ 
/* 134 */     if (PlatformUtil.isMac()) {
/* 135 */       LIST_VIEW_BINDINGS.add(new ListViewKeyBinding(KeyCode.UP, "FocusPreviousRow").vertical().meta());
/* 136 */       LIST_VIEW_BINDINGS.add(new ListViewKeyBinding(KeyCode.DOWN, "FocusNextRow").vertical().meta());
/*     */ 
/* 138 */       LIST_VIEW_BINDINGS.add(new ListViewKeyBinding(KeyCode.UP, "DiscontinuousSelectPreviousRow").vertical().meta().shift());
/* 139 */       LIST_VIEW_BINDINGS.add(new ListViewKeyBinding(KeyCode.DOWN, "DiscontinuousSelectNextRow").vertical().meta().shift());
/* 140 */       LIST_VIEW_BINDINGS.add(new ListViewKeyBinding(KeyCode.PAGE_UP, "DiscontinuousSelectPageUp").vertical().meta().shift());
/* 141 */       LIST_VIEW_BINDINGS.add(new ListViewKeyBinding(KeyCode.PAGE_DOWN, "DiscontinuousSelectPageDown").vertical().meta().shift());
/* 142 */       LIST_VIEW_BINDINGS.add(new ListViewKeyBinding(KeyCode.HOME, "DiscontinuousSelectAllToFirstRow").vertical().meta().shift());
/* 143 */       LIST_VIEW_BINDINGS.add(new ListViewKeyBinding(KeyCode.END, "DiscontinuousSelectAllToLastRow").vertical().meta().shift());
/*     */     } else {
/* 145 */       LIST_VIEW_BINDINGS.add(new ListViewKeyBinding(KeyCode.UP, "FocusPreviousRow").vertical().ctrl());
/* 146 */       LIST_VIEW_BINDINGS.add(new ListViewKeyBinding(KeyCode.DOWN, "FocusNextRow").vertical().ctrl());
/*     */ 
/* 148 */       LIST_VIEW_BINDINGS.add(new ListViewKeyBinding(KeyCode.UP, "DiscontinuousSelectPreviousRow").vertical().ctrl().shift());
/* 149 */       LIST_VIEW_BINDINGS.add(new ListViewKeyBinding(KeyCode.DOWN, "DiscontinuousSelectNextRow").vertical().ctrl().shift());
/* 150 */       LIST_VIEW_BINDINGS.add(new ListViewKeyBinding(KeyCode.PAGE_UP, "DiscontinuousSelectPageUp").vertical().ctrl().shift());
/* 151 */       LIST_VIEW_BINDINGS.add(new ListViewKeyBinding(KeyCode.PAGE_DOWN, "DiscontinuousSelectPageDown").vertical().ctrl().shift());
/* 152 */       LIST_VIEW_BINDINGS.add(new ListViewKeyBinding(KeyCode.HOME, "DiscontinuousSelectAllToFirstRow").vertical().ctrl().shift());
/* 153 */       LIST_VIEW_BINDINGS.add(new ListViewKeyBinding(KeyCode.END, "DiscontinuousSelectAllToLastRow").vertical().ctrl().shift());
/*     */     }
/*     */ 
/* 160 */     LIST_VIEW_BINDINGS.add(new ListViewKeyBinding(KeyCode.LEFT, "SelectPreviousRow"));
/* 161 */     LIST_VIEW_BINDINGS.add(new ListViewKeyBinding(KeyCode.KP_LEFT, "SelectPreviousRow"));
/* 162 */     LIST_VIEW_BINDINGS.add(new ListViewKeyBinding(KeyCode.RIGHT, "SelectNextRow"));
/* 163 */     LIST_VIEW_BINDINGS.add(new ListViewKeyBinding(KeyCode.KP_RIGHT, "SelectNextRow"));
/*     */ 
/* 165 */     LIST_VIEW_BINDINGS.add(new ListViewKeyBinding(KeyCode.LEFT, "AlsoSelectPreviousRow").shift());
/* 166 */     LIST_VIEW_BINDINGS.add(new ListViewKeyBinding(KeyCode.KP_LEFT, "AlsoSelectPreviousRow").shift());
/* 167 */     LIST_VIEW_BINDINGS.add(new ListViewKeyBinding(KeyCode.RIGHT, "AlsoSelectNextRow").shift());
/* 168 */     LIST_VIEW_BINDINGS.add(new ListViewKeyBinding(KeyCode.KP_RIGHT, "AlsoSelectNextRow").shift());
/*     */ 
/* 170 */     LIST_VIEW_BINDINGS.add(new ListViewKeyBinding(KeyCode.UP, "TraverseUp"));
/* 171 */     LIST_VIEW_BINDINGS.add(new ListViewKeyBinding(KeyCode.KP_UP, "TraverseUp"));
/* 172 */     LIST_VIEW_BINDINGS.add(new ListViewKeyBinding(KeyCode.DOWN, "TraverseDown"));
/* 173 */     LIST_VIEW_BINDINGS.add(new ListViewKeyBinding(KeyCode.KP_DOWN, "TraverseDown"));
/*     */ 
/* 175 */     if (PlatformUtil.isMac()) {
/* 176 */       LIST_VIEW_BINDINGS.add(new ListViewKeyBinding(KeyCode.LEFT, "FocusPreviousRow").meta());
/* 177 */       LIST_VIEW_BINDINGS.add(new ListViewKeyBinding(KeyCode.RIGHT, "FocusNextRow").meta());
/*     */     } else {
/* 179 */       LIST_VIEW_BINDINGS.add(new ListViewKeyBinding(KeyCode.LEFT, "FocusPreviousRow").ctrl());
/* 180 */       LIST_VIEW_BINDINGS.add(new ListViewKeyBinding(KeyCode.RIGHT, "FocusNextRow").ctrl());
/*     */     }
/*     */ 
/* 185 */     if (PlatformUtil.isMac())
/* 186 */       LIST_VIEW_BINDINGS.add(new KeyBinding(KeyCode.BACK_SLASH, "ClearSelection").meta());
/*     */     else
/* 188 */       LIST_VIEW_BINDINGS.add(new KeyBinding(KeyCode.BACK_SLASH, "ClearSelection").ctrl());
/*     */   }
/*     */ 
/*     */   private static class ListViewKeyBinding extends OrientedKeyBinding
/*     */   {
/*     */     public ListViewKeyBinding(KeyCode paramKeyCode, String paramString)
/*     */     {
/* 806 */       super(paramString);
/*     */     }
/*     */ 
/*     */     public ListViewKeyBinding(KeyCode paramKeyCode, EventType<KeyEvent> paramEventType, String paramString) {
/* 810 */       super(paramEventType, paramString);
/*     */     }
/*     */ 
/*     */     public boolean getVertical(Control paramControl) {
/* 814 */       return ((ListView)paramControl).getOrientation() == Orientation.VERTICAL;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.behavior.ListViewBehavior
 * JD-Core Version:    0.6.2
 */