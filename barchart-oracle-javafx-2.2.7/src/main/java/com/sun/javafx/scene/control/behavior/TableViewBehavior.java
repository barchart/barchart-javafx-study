/*      */ package com.sun.javafx.scene.control.behavior;
/*      */ 
/*      */ import com.sun.javafx.PlatformUtil;
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
/*      */ import javafx.beans.property.ObjectProperty;
/*      */ import javafx.beans.value.ChangeListener;
/*      */ import javafx.beans.value.ObservableValue;
/*      */ import javafx.collections.ListChangeListener;
/*      */ import javafx.collections.ListChangeListener.Change;
/*      */ import javafx.collections.ObservableList;
/*      */ import javafx.scene.control.FocusModel;
/*      */ import javafx.scene.control.SelectionMode;
/*      */ import javafx.scene.control.TableColumn;
/*      */ import javafx.scene.control.TablePosition;
/*      */ import javafx.scene.control.TableView;
/*      */ import javafx.scene.control.TableView.TableViewFocusModel;
/*      */ import javafx.scene.control.TableView.TableViewSelectionModel;
/*      */ import javafx.scene.input.KeyCode;
/*      */ import javafx.scene.input.KeyEvent;
/*      */ import javafx.scene.input.MouseEvent;
/*      */ import javafx.util.Callback;
/*      */ 
/*      */ public class TableViewBehavior<T> extends BehaviorBase<TableView<T>>
/*      */ {
/*   70 */   protected static final List<KeyBinding> TABLE_VIEW_BINDINGS = new ArrayList();
/*      */ 
/*  242 */   private boolean selectionChanging = false;
/*      */ 
/*  244 */   private ListChangeListener<TablePosition> selectedCellsListener = new ListChangeListener() {
/*      */     public void onChanged(ListChangeListener.Change paramAnonymousChange) {
/*  246 */       while (paramAnonymousChange.next()) {
/*  247 */         TableView.TableViewSelectionModel localTableViewSelectionModel = ((TableView)TableViewBehavior.this.getControl()).getSelectionModel();
/*  248 */         if (localTableViewSelectionModel == null) return;
/*      */ 
/*  250 */         TablePosition localTablePosition1 = TableViewBehavior.this.getAnchor();
/*  251 */         boolean bool = localTableViewSelectionModel.isCellSelectionEnabled();
/*      */ 
/*  253 */         if (!TableViewBehavior.this.selectionChanging)
/*      */         {
/*  255 */           if (paramAnonymousChange.getList().isEmpty())
/*  256 */             TableViewBehavior.this.setAnchor(null);
/*  257 */           else if (!paramAnonymousChange.getList().contains(TableViewBehavior.this.getAnchor())) {
/*  258 */             TableViewBehavior.this.setAnchor(null);
/*      */           }
/*      */         }
/*      */ 
/*  262 */         int i = paramAnonymousChange.getAddedSize();
/*  263 */         List localList = paramAnonymousChange.getAddedSubList();
/*      */         int j;
/*      */         TablePosition localTablePosition2;
/*  265 */         if ((!TableViewBehavior.this.hasAnchor()) && (i > 0)) {
/*  266 */           for (j = 0; j < i; j++) {
/*  267 */             localTablePosition2 = (TablePosition)localList.get(j);
/*  268 */             if (localTablePosition2.getRow() >= 0) {
/*  269 */               TableViewBehavior.this.setAnchor(localTablePosition2);
/*  270 */               break;
/*      */             }
/*      */           }
/*      */         }
/*      */ 
/*  275 */         if ((!TableViewBehavior.this.hasAnchor()) && (bool) && (!TableViewBehavior.this.selectionPathDeviated))
/*      */         {
/*  278 */           for (j = 0; j < i; j++) {
/*  279 */             localTablePosition2 = (TablePosition)localList.get(j);
/*  280 */             if ((localTablePosition1.getRow() != -1) && (localTablePosition2.getRow() != localTablePosition1.getRow()) && (localTablePosition2.getColumn() != localTablePosition1.getColumn())) {
/*  281 */               TableViewBehavior.this.selectionPathDeviated = true;
/*  282 */               break;
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  244 */   };
/*      */ 
/*  324 */   private boolean isCtrlDown = false;
/*  325 */   private boolean isShiftDown = false;
/*  326 */   private boolean selectionPathDeviated = false;
/*      */   private Callback<Void, Integer> onScrollPageUp;
/*      */   private Callback<Void, Integer> onScrollPageDown;
/*      */   private Runnable onFocusPreviousRow;
/*      */   private Runnable onFocusNextRow;
/*      */   private Runnable onSelectPreviousRow;
/*      */   private Runnable onSelectNextRow;
/*      */   private Runnable onMoveToFirstCell;
/*      */   private Runnable onMoveToLastCell;
/*      */   private Runnable onSelectRightCell;
/*      */   private Runnable onSelectLeftCell;
/*      */ 
/*      */   protected void callAction(String paramString)
/*      */   {
/*  183 */     if ("SelectPreviousRow".equals(paramString)) selectPreviousRow();
/*  184 */     else if ("SelectNextRow".equals(paramString)) selectNextRow();
/*  185 */     else if ("SelectLeftCell".equals(paramString)) selectLeftCell();
/*  186 */     else if ("SelectRightCell".equals(paramString)) selectRightCell();
/*  187 */     else if ("SelectFirstRow".equals(paramString)) selectFirstRow();
/*  188 */     else if ("SelectLastRow".equals(paramString)) selectLastRow();
/*  189 */     else if ("SelectAll".equals(paramString)) selectAll();
/*  190 */     else if ("SelectAllPageUp".equals(paramString)) selectAllPageUp();
/*  191 */     else if ("SelectAllPageDown".equals(paramString)) selectAllPageDown();
/*  192 */     else if ("SelectAllToFirstRow".equals(paramString)) selectAllToFirstRow();
/*  193 */     else if ("SelectAllToLastRow".equals(paramString)) selectAllToLastRow();
/*  194 */     else if ("AlsoSelectNext".equals(paramString)) alsoSelectNext();
/*  195 */     else if ("AlsoSelectPrevious".equals(paramString)) alsoSelectPrevious();
/*  196 */     else if ("AlsoSelectLeftCell".equals(paramString)) alsoSelectLeftCell();
/*  197 */     else if ("AlsoSelectRightCell".equals(paramString)) alsoSelectRightCell();
/*  198 */     else if ("ClearSelection".equals(paramString)) clearSelection();
/*  199 */     else if ("ScrollUp".equals(paramString)) scrollUp();
/*  200 */     else if ("ScrollDown".equals(paramString)) scrollDown();
/*  201 */     else if ("FocusPreviousRow".equals(paramString)) focusPreviousRow();
/*  202 */     else if ("FocusNextRow".equals(paramString)) focusNextRow();
/*  203 */     else if ("FocusLeftCell".equals(paramString)) focusLeftCell();
/*  204 */     else if ("FocusRightCell".equals(paramString)) focusRightCell();
/*  205 */     else if ("Activate".equals(paramString)) activate();
/*  206 */     else if ("CancelEdit".equals(paramString)) cancelEdit();
/*  207 */     else if ("FocusFirstRow".equals(paramString)) focusFirstRow();
/*  208 */     else if ("FocusLastRow".equals(paramString)) focusLastRow();
/*  209 */     else if ("toggleFocusOwnerSelection".equals(paramString)) toggleFocusOwnerSelection();
/*  210 */     else if ("SelectAllToFocus".equals(paramString)) selectAllToFocus();
/*  211 */     else if ("FocusPageUp".equals(paramString)) focusPageUp();
/*  212 */     else if ("FocusPageDown".equals(paramString)) focusPageDown();
/*  213 */     else if ("DiscontinuousSelectNextRow".equals(paramString)) discontinuousSelectNextRow();
/*  214 */     else if ("DiscontinuousSelectPreviousRow".equals(paramString)) discontinuousSelectPreviousRow();
/*  215 */     else if ("DiscontinuousSelectNextColumn".equals(paramString)) discontinuousSelectNextColumn();
/*  216 */     else if ("DiscontinuousSelectPreviousColumn".equals(paramString)) discontinuousSelectPreviousColumn();
/*  217 */     else if ("DiscontinuousSelectPageUp".equals(paramString)) discontinuousSelectPageUp();
/*  218 */     else if ("DiscontinuousSelectPageDown".equals(paramString)) discontinuousSelectPageDown();
/*  219 */     else if ("DiscontinuousSelectAllToLastRow".equals(paramString)) discontinuousSelectAllToLastRow();
/*  220 */     else if ("DiscontinuousSelectAllToFirstRow".equals(paramString)) discontinuousSelectAllToFirstRow(); else
/*  221 */       super.callAction(paramString);
/*      */   }
/*      */ 
/*      */   protected List<KeyBinding> createKeyBindings() {
/*  225 */     return TABLE_VIEW_BINDINGS;
/*      */   }
/*      */ 
/*      */   protected void callActionForEvent(KeyEvent paramKeyEvent)
/*      */   {
/*  232 */     this.isShiftDown = ((paramKeyEvent.getEventType() == KeyEvent.KEY_PRESSED) && (paramKeyEvent.isShiftDown()));
/*  233 */     this.isCtrlDown = ((paramKeyEvent.getEventType() == KeyEvent.KEY_PRESSED) && (paramKeyEvent.isControlDown()));
/*      */ 
/*  235 */     super.callActionForEvent(paramKeyEvent);
/*      */   }
/*      */ 
/*      */   public TableViewBehavior(TableView paramTableView)
/*      */   {
/*  291 */     super(paramTableView);
/*      */ 
/*  294 */     ((TableView)getControl()).selectionModelProperty().addListener(new ChangeListener()
/*      */     {
/*      */       public void changed(ObservableValue<? extends TableView.TableViewSelectionModel<T>> paramAnonymousObservableValue, TableView.TableViewSelectionModel<T> paramAnonymousTableViewSelectionModel1, TableView.TableViewSelectionModel<T> paramAnonymousTableViewSelectionModel2)
/*      */       {
/*  299 */         if (paramAnonymousTableViewSelectionModel1 != null) {
/*  300 */           paramAnonymousTableViewSelectionModel1.getSelectedCells().removeListener(TableViewBehavior.this.selectedCellsListener);
/*      */         }
/*  302 */         if (paramAnonymousTableViewSelectionModel2 != null)
/*  303 */           paramAnonymousTableViewSelectionModel2.getSelectedCells().addListener(TableViewBehavior.this.selectedCellsListener);
/*      */       }
/*      */     });
/*  307 */     if (((TableView)getControl()).getSelectionModel() != null)
/*  308 */       ((TableView)getControl()).getSelectionModel().getSelectedCells().addListener(this.selectedCellsListener);
/*      */   }
/*      */ 
/*      */   public void mousePressed(MouseEvent paramMouseEvent)
/*      */   {
/*  313 */     super.mousePressed(paramMouseEvent);
/*      */ 
/*  316 */     ObservableList localObservableList = ((TableView)getControl()).getSelectionModel().getSelectedCells();
/*  317 */     setAnchor(localObservableList.isEmpty() ? null : (TablePosition)localObservableList.get(0));
/*      */ 
/*  319 */     if ((!((TableView)getControl()).isFocused()) && (((TableView)getControl()).isFocusTraversable()))
/*  320 */       ((TableView)getControl()).requestFocus();
/*      */   }
/*      */ 
/*      */   private void setAnchor(int paramInt, TableColumn paramTableColumn)
/*      */   {
/*  354 */     setAnchor((paramInt == -1) && (paramTableColumn == null) ? null : new TablePosition((TableView)getControl(), paramInt, paramTableColumn));
/*      */ 
/*  357 */     this.selectionPathDeviated = false;
/*      */   }
/*      */   private void setAnchor(TablePosition paramTablePosition) {
/*  360 */     TableCellBehavior.setAnchor((TableView)getControl(), paramTablePosition);
/*  361 */     this.selectionPathDeviated = false;
/*      */   }
/*      */   private TablePosition getAnchor() {
/*  364 */     return TableCellBehavior.getAnchor((TableView)getControl());
/*      */   }
/*      */ 
/*      */   private boolean hasAnchor() {
/*  368 */     return TableCellBehavior.hasAnchor((TableView)getControl());
/*      */   }
/*      */ 
/*      */   private int getItemCount()
/*      */   {
/*  385 */     return ((TableView)getControl()).getItems() == null ? 0 : ((TableView)getControl()).getItems().size();
/*      */   }
/*      */ 
/*      */   public void setOnScrollPageUp(Callback<Void, Integer> paramCallback)
/*      */   {
/*  395 */     this.onScrollPageUp = paramCallback;
/*      */   }
/*      */   public void setOnScrollPageDown(Callback<Void, Integer> paramCallback) {
/*  398 */     this.onScrollPageDown = paramCallback;
/*      */   }
/*      */   public void setOnFocusPreviousRow(Runnable paramRunnable) {
/*  401 */     this.onFocusPreviousRow = paramRunnable;
/*      */   }
/*      */   public void setOnFocusNextRow(Runnable paramRunnable) {
/*  404 */     this.onFocusNextRow = paramRunnable;
/*      */   }
/*      */   public void setOnSelectPreviousRow(Runnable paramRunnable) {
/*  407 */     this.onSelectPreviousRow = paramRunnable;
/*      */   }
/*      */   public void setOnSelectNextRow(Runnable paramRunnable) {
/*  410 */     this.onSelectNextRow = paramRunnable;
/*      */   }
/*      */   public void setOnMoveToFirstCell(Runnable paramRunnable) {
/*  413 */     this.onMoveToFirstCell = paramRunnable;
/*      */   }
/*      */   public void setOnMoveToLastCell(Runnable paramRunnable) {
/*  416 */     this.onMoveToLastCell = paramRunnable;
/*      */   }
/*      */   public void setOnSelectRightCell(Runnable paramRunnable) {
/*  419 */     this.onSelectRightCell = paramRunnable;
/*      */   }
/*      */   public void setOnSelectLeftCell(Runnable paramRunnable) {
/*  422 */     this.onSelectLeftCell = paramRunnable;
/*      */   }
/*      */   private void scrollUp() {
/*  425 */     TableView.TableViewSelectionModel localTableViewSelectionModel = ((TableView)getControl()).getSelectionModel();
/*  426 */     if ((localTableViewSelectionModel == null) || (localTableViewSelectionModel.getSelectedCells().isEmpty())) return;
/*      */ 
/*  428 */     TablePosition localTablePosition = (TablePosition)localTableViewSelectionModel.getSelectedCells().get(0);
/*      */ 
/*  430 */     int i = -1;
/*  431 */     if (this.onScrollPageUp != null) {
/*  432 */       i = ((Integer)this.onScrollPageUp.call(null)).intValue();
/*      */     }
/*  434 */     if (i == -1) return;
/*      */ 
/*  436 */     localTableViewSelectionModel.clearAndSelect(i, localTablePosition.getTableColumn());
/*      */   }
/*      */ 
/*      */   private void scrollDown() {
/*  440 */     TableView.TableViewSelectionModel localTableViewSelectionModel = ((TableView)getControl()).getSelectionModel();
/*  441 */     if ((localTableViewSelectionModel == null) || (localTableViewSelectionModel.getSelectedCells().isEmpty())) return;
/*      */ 
/*  443 */     TablePosition localTablePosition = (TablePosition)localTableViewSelectionModel.getSelectedCells().get(0);
/*      */ 
/*  445 */     int i = -1;
/*  446 */     if (this.onScrollPageDown != null) {
/*  447 */       i = ((Integer)this.onScrollPageDown.call(null)).intValue();
/*      */     }
/*  449 */     if (i == -1) return;
/*      */ 
/*  451 */     localTableViewSelectionModel.clearAndSelect(i, localTablePosition.getTableColumn());
/*      */   }
/*      */ 
/*      */   private void focusFirstRow() {
/*  455 */     TableView.TableViewFocusModel localTableViewFocusModel = ((TableView)getControl()).getFocusModel();
/*  456 */     if (localTableViewFocusModel == null) return;
/*      */ 
/*  458 */     TableColumn localTableColumn = localTableViewFocusModel.getFocusedCell() == null ? null : localTableViewFocusModel.getFocusedCell().getTableColumn();
/*  459 */     localTableViewFocusModel.focus(0, localTableColumn);
/*      */ 
/*  461 */     if (this.onMoveToFirstCell != null) this.onMoveToFirstCell.run(); 
/*      */   }
/*      */ 
/*      */   private void focusLastRow()
/*      */   {
/*  465 */     TableView.TableViewFocusModel localTableViewFocusModel = ((TableView)getControl()).getFocusModel();
/*  466 */     if (localTableViewFocusModel == null) return;
/*      */ 
/*  468 */     TableColumn localTableColumn = localTableViewFocusModel.getFocusedCell() == null ? null : localTableViewFocusModel.getFocusedCell().getTableColumn();
/*  469 */     localTableViewFocusModel.focus(getItemCount() - 1, localTableColumn);
/*      */ 
/*  471 */     if (this.onMoveToLastCell != null) this.onMoveToLastCell.run(); 
/*      */   }
/*      */ 
/*      */   private void focusPreviousRow()
/*      */   {
/*  475 */     TableView.TableViewSelectionModel localTableViewSelectionModel = ((TableView)getControl()).getSelectionModel();
/*  476 */     if (localTableViewSelectionModel == null) return;
/*      */ 
/*  478 */     TableView.TableViewFocusModel localTableViewFocusModel = ((TableView)getControl()).getFocusModel();
/*  479 */     if (localTableViewFocusModel == null) return;
/*      */ 
/*  481 */     if (localTableViewSelectionModel.isCellSelectionEnabled())
/*  482 */       localTableViewFocusModel.focusAboveCell();
/*      */     else {
/*  484 */       localTableViewFocusModel.focusPrevious();
/*      */     }
/*      */ 
/*  487 */     if ((!this.isCtrlDown) || (getAnchor() == null)) {
/*  488 */       setAnchor(localTableViewFocusModel.getFocusedIndex(), null);
/*      */     }
/*      */ 
/*  491 */     if (this.onFocusPreviousRow != null) this.onFocusPreviousRow.run(); 
/*      */   }
/*      */ 
/*      */   private void focusNextRow()
/*      */   {
/*  495 */     TableView.TableViewSelectionModel localTableViewSelectionModel = ((TableView)getControl()).getSelectionModel();
/*  496 */     if (localTableViewSelectionModel == null) return;
/*      */ 
/*  498 */     TableView.TableViewFocusModel localTableViewFocusModel = ((TableView)getControl()).getFocusModel();
/*  499 */     if (localTableViewFocusModel == null) return;
/*      */ 
/*  501 */     if (localTableViewSelectionModel.isCellSelectionEnabled())
/*  502 */       localTableViewFocusModel.focusBelowCell();
/*      */     else {
/*  504 */       localTableViewFocusModel.focusNext();
/*      */     }
/*      */ 
/*  507 */     if ((!this.isCtrlDown) || (getAnchor() == null)) {
/*  508 */       setAnchor(localTableViewFocusModel.getFocusedIndex(), null);
/*      */     }
/*      */ 
/*  511 */     if (this.onFocusNextRow != null) this.onFocusNextRow.run(); 
/*      */   }
/*      */ 
/*      */   private void focusLeftCell()
/*      */   {
/*  515 */     TableView.TableViewSelectionModel localTableViewSelectionModel = ((TableView)getControl()).getSelectionModel();
/*  516 */     if (localTableViewSelectionModel == null) return;
/*      */ 
/*  518 */     TableView.TableViewFocusModel localTableViewFocusModel = ((TableView)getControl()).getFocusModel();
/*  519 */     if (localTableViewFocusModel == null) return;
/*      */ 
/*  521 */     localTableViewFocusModel.focusLeftCell();
/*  522 */     if (this.onFocusPreviousRow != null) this.onFocusPreviousRow.run(); 
/*      */   }
/*      */ 
/*      */   private void focusRightCell()
/*      */   {
/*  526 */     TableView.TableViewSelectionModel localTableViewSelectionModel = ((TableView)getControl()).getSelectionModel();
/*  527 */     if (localTableViewSelectionModel == null) return;
/*      */ 
/*  529 */     TableView.TableViewFocusModel localTableViewFocusModel = ((TableView)getControl()).getFocusModel();
/*  530 */     if (localTableViewFocusModel == null) return;
/*      */ 
/*  532 */     localTableViewFocusModel.focusRightCell();
/*  533 */     if (this.onFocusNextRow != null) this.onFocusNextRow.run(); 
/*      */   }
/*      */ 
/*      */   private void focusPageUp()
/*      */   {
/*  537 */     int i = ((Integer)this.onScrollPageUp.call(null)).intValue();
/*      */ 
/*  539 */     TableView.TableViewFocusModel localTableViewFocusModel = ((TableView)getControl()).getFocusModel();
/*  540 */     if (localTableViewFocusModel == null) return;
/*  541 */     TableColumn localTableColumn = localTableViewFocusModel.getFocusedCell() == null ? null : localTableViewFocusModel.getFocusedCell().getTableColumn();
/*  542 */     localTableViewFocusModel.focus(i, localTableColumn);
/*      */   }
/*      */ 
/*      */   private void focusPageDown() {
/*  546 */     int i = ((Integer)this.onScrollPageDown.call(null)).intValue();
/*      */ 
/*  548 */     TableView.TableViewFocusModel localTableViewFocusModel = ((TableView)getControl()).getFocusModel();
/*  549 */     if (localTableViewFocusModel == null) return;
/*  550 */     TableColumn localTableColumn = localTableViewFocusModel.getFocusedCell() == null ? null : localTableViewFocusModel.getFocusedCell().getTableColumn();
/*  551 */     localTableViewFocusModel.focus(i, localTableColumn);
/*      */   }
/*      */ 
/*      */   private void clearSelection() {
/*  555 */     TableView.TableViewSelectionModel localTableViewSelectionModel = ((TableView)getControl()).getSelectionModel();
/*  556 */     if (localTableViewSelectionModel == null) return;
/*      */ 
/*  558 */     localTableViewSelectionModel.clearSelection();
/*      */   }
/*      */ 
/*      */   private void clearSelectionOutsideRange(int paramInt1, int paramInt2) {
/*  562 */     TableView.TableViewSelectionModel localTableViewSelectionModel = ((TableView)getControl()).getSelectionModel();
/*  563 */     if (localTableViewSelectionModel == null) return;
/*      */ 
/*  565 */     int i = Math.min(paramInt1, paramInt2);
/*  566 */     int j = Math.max(paramInt1, paramInt2);
/*      */ 
/*  568 */     ArrayList localArrayList = new ArrayList(localTableViewSelectionModel.getSelectedIndices());
/*      */ 
/*  570 */     this.selectionChanging = true;
/*  571 */     for (int k = 0; k < localArrayList.size(); k++) {
/*  572 */       int m = ((Integer)localArrayList.get(k)).intValue();
/*  573 */       if ((m < i) || (m >= j)) {
/*  574 */         localTableViewSelectionModel.clearSelection(m);
/*      */       }
/*      */     }
/*  577 */     this.selectionChanging = false;
/*      */   }
/*      */ 
/*      */   private void alsoSelectPrevious() {
/*  581 */     TableView.TableViewSelectionModel localTableViewSelectionModel = ((TableView)getControl()).getSelectionModel();
/*  582 */     if ((localTableViewSelectionModel == null) || (localTableViewSelectionModel.getSelectionMode() == SelectionMode.SINGLE)) return;
/*      */ 
/*  584 */     TableView.TableViewFocusModel localTableViewFocusModel = ((TableView)getControl()).getFocusModel();
/*  585 */     if (localTableViewFocusModel == null) return;
/*      */ 
/*  587 */     if (localTableViewSelectionModel.isCellSelectionEnabled()) {
/*  588 */       updateCellVerticalSelection(-1, new Runnable() {
/*      */         public void run() {
/*  590 */           ((TableView)TableViewBehavior.this.getControl()).getSelectionModel().selectAboveCell();
/*      */         }
/*      */       });
/*      */     }
/*  594 */     else if ((this.isShiftDown) && (hasAnchor()))
/*  595 */       updateRowSelection(-1);
/*      */     else {
/*  597 */       localTableViewSelectionModel.selectPrevious();
/*      */     }
/*      */ 
/*  600 */     this.onSelectPreviousRow.run();
/*      */   }
/*      */ 
/*      */   private void alsoSelectNext() {
/*  604 */     TableView.TableViewSelectionModel localTableViewSelectionModel = ((TableView)getControl()).getSelectionModel();
/*  605 */     if ((localTableViewSelectionModel == null) || (localTableViewSelectionModel.getSelectionMode() == SelectionMode.SINGLE)) return;
/*      */ 
/*  607 */     TableView.TableViewFocusModel localTableViewFocusModel = ((TableView)getControl()).getFocusModel();
/*  608 */     if (localTableViewFocusModel == null) return;
/*      */ 
/*  610 */     if (localTableViewSelectionModel.isCellSelectionEnabled()) {
/*  611 */       updateCellVerticalSelection(1, new Runnable() {
/*      */         public void run() {
/*  613 */           ((TableView)TableViewBehavior.this.getControl()).getSelectionModel().selectBelowCell();
/*      */         }
/*      */       });
/*      */     }
/*  617 */     else if ((this.isShiftDown) && (hasAnchor()))
/*  618 */       updateRowSelection(1);
/*      */     else {
/*  620 */       localTableViewSelectionModel.selectNext();
/*      */     }
/*      */ 
/*  623 */     this.onSelectNextRow.run();
/*      */   }
/*      */ 
/*      */   private void alsoSelectLeftCell() {
/*  627 */     updateCellHorizontalSelection(-1, new Runnable() {
/*      */       public void run() {
/*  629 */         ((TableView)TableViewBehavior.this.getControl()).getSelectionModel().selectLeftCell();
/*      */       }
/*      */     });
/*      */   }
/*      */ 
/*      */   private void alsoSelectRightCell() {
/*  635 */     updateCellHorizontalSelection(1, new Runnable() {
/*      */       public void run() {
/*  637 */         ((TableView)TableViewBehavior.this.getControl()).getSelectionModel().selectRightCell();
/*      */       }
/*      */     });
/*      */   }
/*      */ 
/*      */   private void updateRowSelection(int paramInt) {
/*  643 */     TableView.TableViewSelectionModel localTableViewSelectionModel = ((TableView)getControl()).getSelectionModel();
/*  644 */     if ((localTableViewSelectionModel == null) || (localTableViewSelectionModel.getSelectionMode() == SelectionMode.SINGLE)) return;
/*      */ 
/*  646 */     TableView.TableViewFocusModel localTableViewFocusModel = ((TableView)getControl()).getFocusModel();
/*  647 */     if (localTableViewFocusModel == null) return;
/*      */ 
/*  649 */     int i = localTableViewFocusModel.getFocusedIndex() + paramInt;
/*  650 */     TablePosition localTablePosition = getAnchor();
/*      */ 
/*  652 */     if (!hasAnchor()) {
/*  653 */       setAnchor(localTableViewFocusModel.getFocusedCell());
/*      */     }
/*      */ 
/*  656 */     clearSelectionOutsideRange(localTablePosition.getRow(), i);
/*      */ 
/*  658 */     if (localTablePosition.getRow() > i)
/*  659 */       localTableViewSelectionModel.selectRange(localTablePosition.getRow(), i - 1);
/*      */     else
/*  661 */       localTableViewSelectionModel.selectRange(localTablePosition.getRow(), i + 1);
/*      */   }
/*      */ 
/*      */   private void updateCellVerticalSelection(int paramInt, Runnable paramRunnable)
/*      */   {
/*  666 */     TableView.TableViewSelectionModel localTableViewSelectionModel = ((TableView)getControl()).getSelectionModel();
/*  667 */     if ((localTableViewSelectionModel == null) || (localTableViewSelectionModel.getSelectionMode() == SelectionMode.SINGLE)) return;
/*      */ 
/*  669 */     TableView.TableViewFocusModel localTableViewFocusModel = ((TableView)getControl()).getFocusModel();
/*  670 */     if (localTableViewFocusModel == null) return;
/*      */ 
/*  672 */     TablePosition localTablePosition = localTableViewFocusModel.getFocusedCell();
/*      */     int i;
/*  673 */     if ((this.isShiftDown) && (localTableViewSelectionModel.isSelected(localTablePosition.getRow() + paramInt, localTablePosition.getTableColumn()))) {
/*  674 */       i = localTablePosition.getRow() + paramInt;
/*  675 */       localTableViewSelectionModel.clearSelection(this.selectionPathDeviated ? i : localTablePosition.getRow(), localTablePosition.getTableColumn());
/*  676 */       localTableViewFocusModel.focus(i, localTablePosition.getTableColumn());
/*  677 */     } else if ((this.isShiftDown) && (getAnchor() != null) && (!this.selectionPathDeviated)) {
/*  678 */       i = localTableViewFocusModel.getFocusedIndex() + paramInt;
/*      */ 
/*  680 */       int j = Math.min(getAnchor().getRow(), i);
/*  681 */       int k = Math.max(getAnchor().getRow(), i);
/*  682 */       for (int m = j; m <= k; m++) {
/*  683 */         localTableViewSelectionModel.select(m, localTablePosition.getTableColumn());
/*      */       }
/*  685 */       localTableViewFocusModel.focus(i, localTablePosition.getTableColumn());
/*      */     } else {
/*  687 */       i = localTableViewFocusModel.getFocusedIndex();
/*  688 */       if (!localTableViewSelectionModel.isSelected(i, localTablePosition.getTableColumn())) {
/*  689 */         localTableViewSelectionModel.select(i, localTablePosition.getTableColumn());
/*      */       }
/*  691 */       paramRunnable.run();
/*      */     }
/*      */   }
/*      */ 
/*      */   private void updateCellHorizontalSelection(int paramInt, Runnable paramRunnable) {
/*  696 */     TableView.TableViewSelectionModel localTableViewSelectionModel = ((TableView)getControl()).getSelectionModel();
/*  697 */     if ((localTableViewSelectionModel == null) || (localTableViewSelectionModel.getSelectionMode() == SelectionMode.SINGLE)) return;
/*      */ 
/*  699 */     TableView.TableViewFocusModel localTableViewFocusModel = ((TableView)getControl()).getFocusModel();
/*  700 */     if (localTableViewFocusModel == null) return;
/*      */ 
/*  702 */     TablePosition localTablePosition = localTableViewFocusModel.getFocusedCell();
/*  703 */     if ((localTablePosition == null) || (localTablePosition.getTableColumn() == null)) return;
/*      */ 
/*  705 */     TableColumn localTableColumn = getColumn(localTablePosition.getTableColumn(), paramInt);
/*  706 */     if (localTableColumn == null) return;
/*      */ 
/*  708 */     if ((this.isShiftDown) && (getAnchor() != null) && (localTableViewSelectionModel.isSelected(localTablePosition.getRow(), localTableColumn)) && ((localTablePosition.getRow() != getAnchor().getRow()) || (!localTablePosition.getTableColumn().equals(localTableColumn))))
/*      */     {
/*  711 */       localTableViewSelectionModel.clearSelection(localTablePosition.getRow(), this.selectionPathDeviated ? localTableColumn : localTablePosition.getTableColumn());
/*  712 */       localTableViewFocusModel.focus(localTablePosition.getRow(), localTableColumn);
/*  713 */     } else if ((this.isShiftDown) && (getAnchor() != null) && (!this.selectionPathDeviated)) {
/*  714 */       int i = localTablePosition.getColumn() + paramInt;
/*      */ 
/*  716 */       int j = Math.min(getAnchor().getColumn(), i);
/*  717 */       int k = Math.max(getAnchor().getColumn(), i);
/*  718 */       for (int m = j; m <= k; m++) {
/*  719 */         localTableViewSelectionModel.select(localTablePosition.getRow(), getColumn(m));
/*      */       }
/*  721 */       localTableViewFocusModel.focus(localTablePosition.getRow(), getColumn(i));
/*      */     } else {
/*  723 */       paramRunnable.run();
/*      */     }
/*      */   }
/*      */ 
/*      */   private TableColumn getColumn(int paramInt) {
/*  728 */     return ((TableView)getControl()).getVisibleLeafColumn(paramInt);
/*      */   }
/*      */ 
/*      */   private TableColumn getColumn(TableColumn paramTableColumn, int paramInt) {
/*  732 */     return ((TableView)getControl()).getVisibleLeafColumn(((TableView)getControl()).getVisibleLeafIndex(paramTableColumn) + paramInt);
/*      */   }
/*      */ 
/*      */   private void selectFirstRow() {
/*  736 */     TableView.TableViewSelectionModel localTableViewSelectionModel = ((TableView)getControl()).getSelectionModel();
/*  737 */     if (localTableViewSelectionModel == null) return;
/*      */ 
/*  739 */     ObservableList localObservableList = localTableViewSelectionModel.getSelectedCells();
/*  740 */     TableColumn localTableColumn = localObservableList.size() == 0 ? null : ((TablePosition)localObservableList.get(0)).getTableColumn();
/*  741 */     localTableViewSelectionModel.clearAndSelect(0, localTableColumn);
/*      */ 
/*  743 */     if (this.onMoveToFirstCell != null) this.onMoveToFirstCell.run(); 
/*      */   }
/*      */ 
/*      */   private void selectLastRow()
/*      */   {
/*  747 */     TableView.TableViewSelectionModel localTableViewSelectionModel = ((TableView)getControl()).getSelectionModel();
/*  748 */     if (localTableViewSelectionModel == null) return;
/*      */ 
/*  750 */     ObservableList localObservableList = localTableViewSelectionModel.getSelectedCells();
/*  751 */     TableColumn localTableColumn = localObservableList.size() == 0 ? null : ((TablePosition)localObservableList.get(0)).getTableColumn();
/*  752 */     localTableViewSelectionModel.clearAndSelect(getItemCount() - 1, localTableColumn);
/*      */ 
/*  754 */     if (this.onMoveToLastCell != null) this.onMoveToLastCell.run(); 
/*      */   }
/*      */ 
/*      */   private void selectPreviousRow()
/*      */   {
/*  758 */     selectCell(-1, 0);
/*  759 */     if (this.onSelectPreviousRow != null) this.onSelectPreviousRow.run(); 
/*      */   }
/*      */ 
/*      */   private void selectNextRow()
/*      */   {
/*  763 */     selectCell(1, 0);
/*  764 */     if (this.onSelectNextRow != null) this.onSelectNextRow.run(); 
/*      */   }
/*      */ 
/*      */   private void selectLeftCell()
/*      */   {
/*  768 */     selectCell(0, -1);
/*  769 */     if (this.onSelectLeftCell != null) this.onSelectLeftCell.run(); 
/*      */   }
/*      */ 
/*      */   private void selectRightCell()
/*      */   {
/*  773 */     selectCell(0, 1);
/*  774 */     if (this.onSelectRightCell != null) this.onSelectRightCell.run(); 
/*      */   }
/*      */ 
/*      */   private void selectCell(int paramInt1, int paramInt2)
/*      */   {
/*  778 */     TableView.TableViewSelectionModel localTableViewSelectionModel = ((TableView)getControl()).getSelectionModel();
/*  779 */     if (localTableViewSelectionModel == null) return;
/*      */ 
/*  781 */     TableView.TableViewFocusModel localTableViewFocusModel = ((TableView)getControl()).getFocusModel();
/*  782 */     if (localTableViewFocusModel == null) return;
/*      */ 
/*  784 */     TablePosition localTablePosition = localTableViewFocusModel.getFocusedCell();
/*  785 */     int i = localTablePosition.getRow();
/*  786 */     int j = localTablePosition.getColumn();
/*  787 */     if ((paramInt1 < 0) && (i == 0)) return;
/*  788 */     if ((paramInt1 > 0) && (i == getItemCount() - 1)) return;
/*  789 */     if ((paramInt2 < 0) && (j == 0)) return;
/*  790 */     if ((paramInt2 > 0) && (j == ((TableView)getControl()).getVisibleLeafColumns().size() - 1)) return;
/*      */ 
/*  792 */     TableColumn localTableColumn = localTablePosition.getTableColumn();
/*  793 */     localTableColumn = getColumn(localTableColumn, paramInt2);
/*      */ 
/*  795 */     int k = localTablePosition.getRow() + paramInt1;
/*  796 */     localTableViewSelectionModel.clearAndSelect(k, localTableColumn);
/*  797 */     setAnchor(k, localTableColumn);
/*      */   }
/*      */ 
/*      */   private void cancelEdit() {
/*  801 */     ((TableView)getControl()).edit(-1, null);
/*      */   }
/*      */ 
/*      */   private void activate() {
/*  805 */     TableView.TableViewSelectionModel localTableViewSelectionModel = ((TableView)getControl()).getSelectionModel();
/*  806 */     if (localTableViewSelectionModel == null) return;
/*      */ 
/*  808 */     TableView.TableViewFocusModel localTableViewFocusModel = ((TableView)getControl()).getFocusModel();
/*  809 */     if (localTableViewFocusModel == null) return;
/*      */ 
/*  811 */     TablePosition localTablePosition = localTableViewFocusModel.getFocusedCell();
/*  812 */     localTableViewSelectionModel.select(localTablePosition.getRow(), localTablePosition.getTableColumn());
/*      */ 
/*  815 */     ((TableView)getControl()).edit(localTablePosition.getRow(), localTablePosition.getTableColumn());
/*      */   }
/*      */ 
/*      */   private void selectAllToFocus() {
/*  819 */     TableView.TableViewSelectionModel localTableViewSelectionModel = ((TableView)getControl()).getSelectionModel();
/*  820 */     if (localTableViewSelectionModel == null) return;
/*      */ 
/*  822 */     TableView.TableViewFocusModel localTableViewFocusModel = ((TableView)getControl()).getFocusModel();
/*  823 */     if (localTableViewFocusModel == null) return;
/*      */ 
/*  825 */     TablePosition localTablePosition1 = localTableViewFocusModel.getFocusedCell();
/*  826 */     int i = localTablePosition1.getRow();
/*      */ 
/*  828 */     TablePosition localTablePosition2 = getAnchor();
/*  829 */     int j = localTablePosition2.getRow();
/*      */ 
/*  831 */     localTableViewSelectionModel.clearSelection();
/*      */     int k;
/*      */     int m;
/*  832 */     if (!localTableViewSelectionModel.isCellSelectionEnabled()) {
/*  833 */       k = j;
/*  834 */       m = j > i ? i - 1 : i + 1;
/*  835 */       localTableViewSelectionModel.selectRange(k, m);
/*      */     }
/*      */     else
/*      */     {
/*  841 */       k = Math.min(localTablePosition1.getRow(), j);
/*  842 */       m = Math.max(localTablePosition1.getRow(), j);
/*  843 */       int n = Math.min(localTablePosition1.getColumn(), localTablePosition2.getColumn());
/*  844 */       int i1 = Math.max(localTablePosition1.getColumn(), localTablePosition2.getColumn());
/*      */ 
/*  847 */       localTableViewSelectionModel.clearSelection();
/*      */ 
/*  850 */       for (int i2 = k; i2 <= m; i2++) {
/*  851 */         for (int i3 = n; i3 <= i1; i3++) {
/*  852 */           localTableViewSelectionModel.select(i2, ((TableView)getControl()).getVisibleLeafColumn(i3));
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  857 */     setAnchor(localTablePosition2);
/*      */   }
/*      */ 
/*      */   private void selectAll() {
/*  861 */     TableView.TableViewSelectionModel localTableViewSelectionModel = ((TableView)getControl()).getSelectionModel();
/*  862 */     if (localTableViewSelectionModel == null) return;
/*  863 */     localTableViewSelectionModel.selectAll();
/*      */   }
/*      */ 
/*      */   private void selectAllToFirstRow() {
/*  867 */     TableView.TableViewSelectionModel localTableViewSelectionModel = ((TableView)getControl()).getSelectionModel();
/*  868 */     if (localTableViewSelectionModel == null) return;
/*      */ 
/*  870 */     TableView.TableViewFocusModel localTableViewFocusModel = ((TableView)getControl()).getFocusModel();
/*  871 */     if (localTableViewFocusModel == null) return;
/*      */ 
/*  873 */     TablePosition localTablePosition = localTableViewFocusModel.getFocusedCell();
/*      */ 
/*  875 */     int i = localTablePosition.getRow();
/*      */ 
/*  877 */     if (this.isShiftDown) {
/*  878 */       i = getAnchor() == null ? i : getAnchor().getRow();
/*      */     }
/*      */ 
/*  881 */     localTableViewSelectionModel.clearSelection();
/*  882 */     if (!localTableViewSelectionModel.isCellSelectionEnabled())
/*      */     {
/*  885 */       localTableViewSelectionModel.selectRange(0, i + 1);
/*  886 */       ((TableView)getControl()).getFocusModel().focus(0);
/*      */     }
/*      */ 
/*  894 */     if (this.isShiftDown) {
/*  895 */       setAnchor(i, null);
/*      */     }
/*      */ 
/*  898 */     if (this.onMoveToFirstCell != null) this.onMoveToFirstCell.run(); 
/*      */   }
/*      */ 
/*      */   private void selectAllToLastRow()
/*      */   {
/*  902 */     TableView.TableViewSelectionModel localTableViewSelectionModel = ((TableView)getControl()).getSelectionModel();
/*  903 */     if (localTableViewSelectionModel == null) return;
/*      */ 
/*  905 */     TableView.TableViewFocusModel localTableViewFocusModel = ((TableView)getControl()).getFocusModel();
/*  906 */     if (localTableViewFocusModel == null) return;
/*      */ 
/*  908 */     TablePosition localTablePosition = localTableViewFocusModel.getFocusedCell();
/*      */ 
/*  910 */     int i = localTablePosition.getRow();
/*      */ 
/*  912 */     if (this.isShiftDown) {
/*  913 */       i = getAnchor() == null ? i : getAnchor().getRow();
/*      */     }
/*      */ 
/*  916 */     localTableViewSelectionModel.clearSelection();
/*  917 */     if (!localTableViewSelectionModel.isCellSelectionEnabled()) {
/*  918 */       localTableViewSelectionModel.selectRange(i, getItemCount());
/*      */     }
/*      */ 
/*  923 */     if (this.isShiftDown) {
/*  924 */       setAnchor(i, null);
/*      */     }
/*      */ 
/*  927 */     if (this.onMoveToLastCell != null) this.onMoveToLastCell.run(); 
/*      */   }
/*      */ 
/*      */   private void selectAllPageUp()
/*      */   {
/*  931 */     TableView.TableViewFocusModel localTableViewFocusModel = ((TableView)getControl()).getFocusModel();
/*  932 */     if (localTableViewFocusModel == null) return;
/*      */ 
/*  934 */     int i = localTableViewFocusModel.getFocusedIndex();
/*  935 */     if (this.isShiftDown) {
/*  936 */       i = getAnchor() == null ? i : getAnchor().getRow();
/*  937 */       setAnchor(i, null);
/*      */     }
/*      */ 
/*  940 */     int j = ((Integer)this.onScrollPageUp.call(null)).intValue();
/*      */ 
/*  942 */     TableView.TableViewSelectionModel localTableViewSelectionModel = ((TableView)getControl()).getSelectionModel();
/*  943 */     if (localTableViewSelectionModel == null) return;
/*      */ 
/*  945 */     this.selectionChanging = true;
/*  946 */     localTableViewSelectionModel.clearSelection();
/*  947 */     localTableViewSelectionModel.selectRange(j, i + 1);
/*  948 */     this.selectionChanging = false;
/*      */   }
/*      */ 
/*      */   private void selectAllPageDown() {
/*  952 */     TableView.TableViewFocusModel localTableViewFocusModel = ((TableView)getControl()).getFocusModel();
/*  953 */     if (localTableViewFocusModel == null) return;
/*      */ 
/*  955 */     int i = localTableViewFocusModel.getFocusedIndex();
/*  956 */     if (this.isShiftDown) {
/*  957 */       i = getAnchor() == null ? i : getAnchor().getRow();
/*  958 */       setAnchor(i, null);
/*      */     }
/*      */ 
/*  961 */     int j = ((Integer)this.onScrollPageDown.call(null)).intValue();
/*      */ 
/*  963 */     TableView.TableViewSelectionModel localTableViewSelectionModel = ((TableView)getControl()).getSelectionModel();
/*  964 */     if (localTableViewSelectionModel == null) return;
/*      */ 
/*  966 */     this.selectionChanging = true;
/*  967 */     localTableViewSelectionModel.clearSelection();
/*  968 */     localTableViewSelectionModel.selectRange(i, j + 1);
/*  969 */     this.selectionChanging = false;
/*      */   }
/*      */ 
/*      */   private void toggleFocusOwnerSelection() {
/*  973 */     TableView.TableViewSelectionModel localTableViewSelectionModel = ((TableView)getControl()).getSelectionModel();
/*  974 */     if (localTableViewSelectionModel == null) return;
/*      */ 
/*  976 */     TableView.TableViewFocusModel localTableViewFocusModel = ((TableView)getControl()).getFocusModel();
/*  977 */     if (localTableViewFocusModel == null) return;
/*      */ 
/*  979 */     TablePosition localTablePosition = localTableViewFocusModel.getFocusedCell();
/*      */ 
/*  981 */     if (localTableViewSelectionModel.isSelected(localTablePosition.getRow(), localTablePosition.getTableColumn())) {
/*  982 */       localTableViewSelectionModel.clearSelection(localTablePosition.getRow(), localTablePosition.getTableColumn());
/*  983 */       localTableViewFocusModel.focus(localTablePosition.getRow(), localTablePosition.getTableColumn());
/*      */     } else {
/*  985 */       localTableViewSelectionModel.select(localTablePosition.getRow(), localTablePosition.getTableColumn());
/*      */     }
/*      */ 
/*  988 */     setAnchor(localTablePosition.getRow(), localTablePosition.getTableColumn());
/*      */   }
/*      */ 
/*      */   private void discontinuousSelectPreviousRow()
/*      */   {
/* 1035 */     TableView.TableViewSelectionModel localTableViewSelectionModel = ((TableView)getControl()).getSelectionModel();
/* 1036 */     if (localTableViewSelectionModel == null) return;
/*      */ 
/* 1038 */     TableView.TableViewFocusModel localTableViewFocusModel = ((TableView)getControl()).getFocusModel();
/* 1039 */     if (localTableViewFocusModel == null) return;
/*      */ 
/* 1041 */     int i = localTableViewFocusModel.getFocusedIndex() - 1;
/* 1042 */     if (i < 0) return;
/*      */ 
/* 1044 */     if (!localTableViewSelectionModel.isCellSelectionEnabled())
/* 1045 */       localTableViewSelectionModel.select(i);
/*      */     else
/* 1047 */       localTableViewSelectionModel.select(i, localTableViewFocusModel.getFocusedCell().getTableColumn());
/*      */   }
/*      */ 
/*      */   private void discontinuousSelectNextRow()
/*      */   {
/* 1052 */     TableView.TableViewSelectionModel localTableViewSelectionModel = ((TableView)getControl()).getSelectionModel();
/* 1053 */     if (localTableViewSelectionModel == null) return;
/*      */ 
/* 1055 */     TableView.TableViewFocusModel localTableViewFocusModel = ((TableView)getControl()).getFocusModel();
/* 1056 */     if (localTableViewFocusModel == null) return;
/*      */ 
/* 1058 */     int i = localTableViewFocusModel.getFocusedIndex() + 1;
/*      */ 
/* 1060 */     if (!localTableViewSelectionModel.isCellSelectionEnabled())
/* 1061 */       localTableViewSelectionModel.select(i);
/*      */     else
/* 1063 */       localTableViewSelectionModel.select(i, localTableViewFocusModel.getFocusedCell().getTableColumn());
/*      */   }
/*      */ 
/*      */   private void discontinuousSelectPreviousColumn()
/*      */   {
/* 1068 */     TableView.TableViewSelectionModel localTableViewSelectionModel = ((TableView)getControl()).getSelectionModel();
/* 1069 */     if ((localTableViewSelectionModel == null) || (!localTableViewSelectionModel.isCellSelectionEnabled())) return;
/*      */ 
/* 1071 */     TableView.TableViewFocusModel localTableViewFocusModel = ((TableView)getControl()).getFocusModel();
/* 1072 */     if (localTableViewFocusModel == null) return;
/*      */ 
/* 1074 */     TableColumn localTableColumn = getColumn(localTableViewFocusModel.getFocusedCell().getTableColumn(), -1);
/* 1075 */     localTableViewSelectionModel.select(localTableViewFocusModel.getFocusedIndex(), localTableColumn);
/*      */   }
/*      */ 
/*      */   private void discontinuousSelectNextColumn() {
/* 1079 */     TableView.TableViewSelectionModel localTableViewSelectionModel = ((TableView)getControl()).getSelectionModel();
/* 1080 */     if ((localTableViewSelectionModel == null) || (!localTableViewSelectionModel.isCellSelectionEnabled())) return;
/*      */ 
/* 1082 */     TableView.TableViewFocusModel localTableViewFocusModel = ((TableView)getControl()).getFocusModel();
/* 1083 */     if (localTableViewFocusModel == null) return;
/*      */ 
/* 1085 */     TableColumn localTableColumn = getColumn(localTableViewFocusModel.getFocusedCell().getTableColumn(), 1);
/* 1086 */     localTableViewSelectionModel.select(localTableViewFocusModel.getFocusedIndex(), localTableColumn);
/*      */   }
/*      */ 
/*      */   private void discontinuousSelectPageUp() {
/* 1090 */     TableView.TableViewSelectionModel localTableViewSelectionModel = ((TableView)getControl()).getSelectionModel();
/* 1091 */     if (localTableViewSelectionModel == null) return;
/*      */ 
/* 1093 */     TableView.TableViewFocusModel localTableViewFocusModel = ((TableView)getControl()).getFocusModel();
/* 1094 */     if (localTableViewFocusModel == null) return;
/*      */ 
/* 1096 */     int i = localTableViewFocusModel.getFocusedIndex();
/* 1097 */     int j = ((Integer)this.onScrollPageUp.call(null)).intValue();
/*      */ 
/* 1099 */     if (!localTableViewSelectionModel.isCellSelectionEnabled())
/* 1100 */       localTableViewSelectionModel.selectRange(j, i + 1);
/*      */   }
/*      */ 
/*      */   private void discontinuousSelectPageDown()
/*      */   {
/* 1105 */     TableView.TableViewSelectionModel localTableViewSelectionModel = ((TableView)getControl()).getSelectionModel();
/* 1106 */     if (localTableViewSelectionModel == null) return;
/*      */ 
/* 1108 */     TableView.TableViewFocusModel localTableViewFocusModel = ((TableView)getControl()).getFocusModel();
/* 1109 */     if (localTableViewFocusModel == null) return;
/*      */ 
/* 1111 */     int i = localTableViewFocusModel.getFocusedIndex();
/* 1112 */     int j = ((Integer)this.onScrollPageDown.call(null)).intValue();
/*      */ 
/* 1114 */     if (!localTableViewSelectionModel.isCellSelectionEnabled())
/* 1115 */       localTableViewSelectionModel.selectRange(i, j + 1);
/*      */   }
/*      */ 
/*      */   private void discontinuousSelectAllToFirstRow()
/*      */   {
/* 1120 */     TableView.TableViewSelectionModel localTableViewSelectionModel = ((TableView)getControl()).getSelectionModel();
/* 1121 */     if (localTableViewSelectionModel == null) return;
/*      */ 
/* 1123 */     TableView.TableViewFocusModel localTableViewFocusModel = ((TableView)getControl()).getFocusModel();
/* 1124 */     if (localTableViewFocusModel == null) return;
/*      */ 
/* 1126 */     int i = localTableViewFocusModel.getFocusedIndex();
/*      */ 
/* 1128 */     if (!localTableViewSelectionModel.isCellSelectionEnabled())
/* 1129 */       localTableViewSelectionModel.selectRange(0, i);
/*      */     else {
/* 1131 */       for (int j = 0; j < i; j++) {
/* 1132 */         localTableViewSelectionModel.select(j, localTableViewFocusModel.getFocusedCell().getTableColumn());
/*      */       }
/*      */     }
/*      */ 
/* 1136 */     if (this.onMoveToFirstCell != null) this.onMoveToFirstCell.run(); 
/*      */   }
/*      */ 
/*      */   private void discontinuousSelectAllToLastRow()
/*      */   {
/* 1140 */     TableView.TableViewSelectionModel localTableViewSelectionModel = ((TableView)getControl()).getSelectionModel();
/* 1141 */     if (localTableViewSelectionModel == null) return;
/*      */ 
/* 1143 */     TableView.TableViewFocusModel localTableViewFocusModel = ((TableView)getControl()).getFocusModel();
/* 1144 */     if (localTableViewFocusModel == null) return;
/*      */ 
/* 1146 */     int i = localTableViewFocusModel.getFocusedIndex() + 1;
/*      */ 
/* 1148 */     if (!localTableViewSelectionModel.isCellSelectionEnabled())
/* 1149 */       localTableViewSelectionModel.selectRange(i, getItemCount());
/*      */     else {
/* 1151 */       for (int j = i; j < getItemCount(); j++) {
/* 1152 */         localTableViewSelectionModel.select(j, localTableViewFocusModel.getFocusedCell().getTableColumn());
/*      */       }
/*      */     }
/*      */ 
/* 1156 */     if (this.onMoveToLastCell != null) this.onMoveToLastCell.run();
/*      */   }
/*      */ 
/*      */   static
/*      */   {
/*   73 */     TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.TAB, "TraverseNext"));
/*   74 */     TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.TAB, "TraversePrevious").shift());
/*      */ 
/*   76 */     TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.HOME, "SelectFirstRow"));
/*   77 */     TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.END, "SelectLastRow"));
/*      */ 
/*   79 */     TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.PAGE_UP, "ScrollUp"));
/*   80 */     TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.PAGE_DOWN, "ScrollDown"));
/*      */ 
/*   82 */     TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.LEFT, "SelectLeftCell"));
/*   83 */     TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.KP_LEFT, "SelectLeftCell"));
/*   84 */     TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.RIGHT, "SelectRightCell"));
/*   85 */     TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.KP_RIGHT, "SelectRightCell"));
/*      */ 
/*   87 */     TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.UP, "SelectPreviousRow"));
/*   88 */     TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.KP_UP, "SelectPreviousRow"));
/*   89 */     TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.DOWN, "SelectNextRow"));
/*   90 */     TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.KP_DOWN, "SelectNextRow"));
/*      */ 
/*   92 */     TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.LEFT, "TraverseLeft"));
/*   93 */     TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.KP_LEFT, "TraverseLeft"));
/*   94 */     TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.RIGHT, "SelectNextRow"));
/*   95 */     TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.KP_RIGHT, "SelectNextRow"));
/*   96 */     TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.UP, "TraverseUp"));
/*   97 */     TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.KP_UP, "TraverseUp"));
/*   98 */     TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.DOWN, "TraverseDown"));
/*   99 */     TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.KP_DOWN, "TraverseDown"));
/*      */ 
/*  101 */     TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.HOME, "SelectAllToFirstRow").shift());
/*  102 */     TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.END, "SelectAllToLastRow").shift());
/*  103 */     TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.PAGE_UP, "SelectAllPageUp").shift());
/*  104 */     TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.PAGE_DOWN, "SelectAllPageDown").shift());
/*      */ 
/*  106 */     TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.UP, "AlsoSelectPrevious").shift());
/*  107 */     TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.KP_UP, "AlsoSelectPrevious").shift());
/*  108 */     TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.DOWN, "AlsoSelectNext").shift());
/*  109 */     TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.KP_DOWN, "AlsoSelectNext").shift());
/*      */ 
/*  111 */     TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.SPACE, "SelectAllToFocus").shift());
/*      */ 
/*  117 */     TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.LEFT, "AlsoSelectLeftCell").shift());
/*  118 */     TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.KP_LEFT, "AlsoSelectLeftCell").shift());
/*  119 */     TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.RIGHT, "AlsoSelectRightCell").shift());
/*  120 */     TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.KP_RIGHT, "AlsoSelectRightCell").shift());
/*      */ 
/*  122 */     if (PlatformUtil.isMac()) {
/*  123 */       TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.UP, "FocusPreviousRow").meta());
/*  124 */       TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.DOWN, "FocusNextRow").meta());
/*  125 */       TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.RIGHT, "FocusRightCell").meta());
/*  126 */       TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.KP_RIGHT, "FocusRightCell").meta());
/*  127 */       TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.LEFT, "FocusLeftCell").meta());
/*  128 */       TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.KP_LEFT, "FocusLeftCell").meta());
/*  129 */       TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.A, "SelectAll").meta());
/*  130 */       TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.HOME, "FocusFirstRow").meta());
/*  131 */       TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.END, "FocusLastRow").meta());
/*  132 */       TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.SPACE, "toggleFocusOwnerSelection").ctrl().meta());
/*  133 */       TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.PAGE_UP, "FocusPageUp").meta());
/*  134 */       TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.PAGE_DOWN, "FocusPageDown").meta());
/*      */ 
/*  136 */       TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.UP, "DiscontinuousSelectPreviousRow").meta().shift());
/*  137 */       TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.DOWN, "DiscontinuousSelectNextRow").meta().shift());
/*  138 */       TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.LEFT, "DiscontinuousSelectPreviousColumn").meta().shift());
/*  139 */       TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.RIGHT, "DiscontinuousSelectNextColumn").meta().shift());
/*  140 */       TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.PAGE_UP, "DiscontinuousSelectPageUp").meta().shift());
/*  141 */       TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.PAGE_DOWN, "DiscontinuousSelectPageDown").meta().shift());
/*  142 */       TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.HOME, "DiscontinuousSelectAllToFirstRow").meta().shift());
/*  143 */       TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.END, "DiscontinuousSelectAllToLastRow").meta().shift());
/*      */     } else {
/*  145 */       TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.UP, "FocusPreviousRow").ctrl());
/*  146 */       TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.DOWN, "FocusNextRow").ctrl());
/*  147 */       TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.RIGHT, "FocusRightCell").ctrl());
/*  148 */       TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.KP_RIGHT, "FocusRightCell").ctrl());
/*  149 */       TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.LEFT, "FocusLeftCell").ctrl());
/*  150 */       TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.KP_LEFT, "FocusLeftCell").ctrl());
/*  151 */       TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.A, "SelectAll").ctrl());
/*  152 */       TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.HOME, "FocusFirstRow").ctrl());
/*  153 */       TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.END, "FocusLastRow").ctrl());
/*  154 */       TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.SPACE, "toggleFocusOwnerSelection").ctrl());
/*  155 */       TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.PAGE_UP, "FocusPageUp").ctrl());
/*  156 */       TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.PAGE_DOWN, "FocusPageDown").ctrl());
/*      */ 
/*  158 */       TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.UP, "DiscontinuousSelectPreviousRow").ctrl().shift());
/*  159 */       TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.DOWN, "DiscontinuousSelectNextRow").ctrl().shift());
/*  160 */       TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.LEFT, "DiscontinuousSelectPreviousColumn").ctrl().shift());
/*  161 */       TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.RIGHT, "DiscontinuousSelectNextColumn").ctrl().shift());
/*  162 */       TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.PAGE_UP, "DiscontinuousSelectPageUp").ctrl().shift());
/*  163 */       TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.PAGE_DOWN, "DiscontinuousSelectPageDown").ctrl().shift());
/*  164 */       TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.HOME, "DiscontinuousSelectAllToFirstRow").ctrl().shift());
/*  165 */       TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.END, "DiscontinuousSelectAllToLastRow").ctrl().shift());
/*      */     }
/*      */ 
/*  168 */     TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.ENTER, "Activate"));
/*  169 */     TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.SPACE, "Activate"));
/*  170 */     TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.F2, "Activate"));
/*      */ 
/*  173 */     TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.ESCAPE, "CancelEdit"));
/*      */ 
/*  175 */     if (PlatformUtil.isMac())
/*  176 */       TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.BACK_SLASH, "ClearSelection").meta());
/*      */     else
/*  178 */       TABLE_VIEW_BINDINGS.add(new KeyBinding(KeyCode.BACK_SLASH, "ClearSelection").ctrl());
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.behavior.TableViewBehavior
 * JD-Core Version:    0.6.2
 */