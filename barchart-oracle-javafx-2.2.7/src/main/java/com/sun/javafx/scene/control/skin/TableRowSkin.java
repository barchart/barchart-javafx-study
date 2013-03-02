/*     */ package com.sun.javafx.scene.control.skin;
/*     */ 
/*     */ import com.sun.javafx.scene.control.WeakListChangeListener;
/*     */ import com.sun.javafx.scene.control.behavior.CellBehaviorBase;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.WeakHashMap;
/*     */ import javafx.collections.ListChangeListener;
/*     */ import javafx.collections.ListChangeListener.Change;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.geometry.Insets;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.control.TableCell;
/*     */ import javafx.scene.control.TableColumn;
/*     */ import javafx.scene.control.TableRow;
/*     */ import javafx.scene.control.TableView;
/*     */ import javafx.util.Callback;
/*     */ 
/*     */ public class TableRowSkin<T> extends CellSkinBase<TableRow<T>, CellBehaviorBase<TableRow<T>>>
/*     */ {
/*     */   private static final int DEFAULT_FULL_REFRESH_COUNTER = 100;
/*     */   private WeakHashMap<TableColumn, TableCell> cellsMap;
/*  68 */   private final List<TableCell> cells = new ArrayList();
/*     */ 
/*  70 */   private int fullRefreshCounter = 100;
/*     */ 
/*  72 */   private boolean showColumns = true;
/*     */ 
/*  74 */   private boolean isDirty = false;
/*  75 */   private boolean updateCells = false;
/*     */ 
/*  77 */   private ListChangeListener visibleLeafColumnsListener = new ListChangeListener() {
/*     */     public void onChanged(ListChangeListener.Change paramAnonymousChange) {
/*  79 */       TableRowSkin.this.isDirty = true;
/*  80 */       TableRowSkin.this.requestLayout();
/*     */     }
/*  77 */   };
/*     */ 
/* 190 */   private int columnCount = 0;
/*     */ 
/*     */   public TableRowSkin(TableRow<T> paramTableRow)
/*     */   {
/*  85 */     super(paramTableRow, new CellBehaviorBase(paramTableRow));
/*     */ 
/*  87 */     recreateCells();
/*  88 */     updateCells(true);
/*     */ 
/*  90 */     initBindings();
/*     */ 
/*  92 */     registerChangeListener(paramTableRow.itemProperty(), "ITEM");
/*  93 */     registerChangeListener(paramTableRow.editingProperty(), "EDITING");
/*  94 */     registerChangeListener(paramTableRow.tableViewProperty(), "TABLE_VIEW");
/*     */   }
/*     */ 
/*     */   protected void handleControlPropertyChanged(String paramString)
/*     */   {
/* 101 */     if ((paramString == "TEXT") || (paramString == "GRAPHIC") || (paramString == "EDITING")) {
/* 102 */       updateShowColumns();
/*     */     }
/*     */ 
/* 105 */     super.handleControlPropertyChanged(paramString);
/*     */ 
/* 107 */     if (paramString == "ITEM") {
/* 108 */       this.updateCells = true;
/* 109 */       requestLayout();
/* 110 */       layout();
/* 111 */     } else if (paramString == "TABLE_VIEW") {
/* 112 */       for (int i = 0; i < getChildren().size(); i++) {
/* 113 */         Node localNode = (Node)getChildren().get(i);
/* 114 */         if ((localNode instanceof TableCell))
/* 115 */           ((TableCell)localNode).updateTableView(((TableRow)getSkinnable()).getTableView());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void updateShowColumns()
/*     */   {
/* 122 */     boolean bool = (isIgnoreText()) && (isIgnoreGraphic());
/* 123 */     if (this.showColumns == bool) return;
/*     */ 
/* 125 */     this.showColumns = bool;
/*     */ 
/* 127 */     requestLayout();
/*     */   }
/*     */ 
/*     */   private void initBindings()
/*     */   {
/* 134 */     if (getSkinnable() == null) {
/* 135 */       throw new IllegalStateException("TableRowSkin does not have a Skinnable set to a TableRow instance");
/*     */     }
/* 137 */     if (((TableRow)getSkinnable()).getTableView() == null) {
/* 138 */       throw new IllegalStateException("TableRow not have the TableView property set");
/*     */     }
/*     */ 
/* 141 */     ((TableRow)getSkinnable()).getTableView().getVisibleLeafColumns().addListener(new WeakListChangeListener(this.visibleLeafColumnsListener));
/*     */   }
/*     */ 
/*     */   private void doUpdateCheck()
/*     */   {
/* 146 */     if (this.isDirty) {
/* 147 */       recreateCells();
/* 148 */       updateCells(true);
/* 149 */       this.isDirty = false;
/* 150 */     } else if (this.updateCells) {
/* 151 */       updateCells(false);
/* 152 */       this.updateCells = false;
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void layoutChildren() {
/* 157 */     doUpdateCheck();
/*     */ 
/* 159 */     TableView localTableView = ((TableRow)getSkinnable()).getTableView();
/* 160 */     if (localTableView == null) return;
/* 161 */     if (this.cellsMap.isEmpty()) return;
/*     */ 
/* 163 */     if ((this.showColumns) && (!localTableView.getVisibleLeafColumns().isEmpty()))
/*     */     {
/* 165 */       double d1 = getInsets().getLeft();
/*     */ 
/* 169 */       double d4 = getInsets().getTop() + getInsets().getBottom();
/* 170 */       double d5 = getInsets().getLeft() + getInsets().getRight();
/*     */ 
/* 172 */       for (int i = 0; i < getChildren().size(); i++)
/*     */       {
/* 175 */         Node localNode = (Node)getChildren().get(i);
/*     */ 
/* 177 */         double d2 = snapSize(localNode.prefWidth(-1.0D) - d5);
/* 178 */         double d3 = Math.max(getHeight(), localNode.prefHeight(-1.0D));
/* 179 */         d3 = snapSize(d3 - d4);
/*     */ 
/* 181 */         localNode.resize(d2, d3);
/* 182 */         localNode.relocate(d1, getInsets().getTop());
/* 183 */         d1 += d2;
/*     */       }
/*     */     } else {
/* 186 */       super.layoutChildren();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void recreateCells()
/*     */   {
/* 201 */     TableView localTableView = ((TableRow)getSkinnable()).getTableView();
/* 202 */     if (localTableView == null) {
/* 203 */       clearCellsMap();
/* 204 */       return;
/*     */     }
/*     */ 
/* 207 */     ObservableList localObservableList = localTableView.getVisibleLeafColumns();
/*     */ 
/* 209 */     if ((localObservableList.size() != this.columnCount) || (this.fullRefreshCounter == 0) || (this.cellsMap == null)) {
/* 210 */       clearCellsMap();
/* 211 */       this.cellsMap = new WeakHashMap(localObservableList.size());
/* 212 */       this.fullRefreshCounter = 100;
/* 213 */       getChildren().clear();
/*     */     }
/* 215 */     this.columnCount = localObservableList.size();
/* 216 */     this.fullRefreshCounter -= 1;
/*     */ 
/* 218 */     for (TableColumn localTableColumn : localObservableList)
/* 219 */       if (!this.cellsMap.containsKey(localTableColumn))
/*     */       {
/* 224 */         TableCell localTableCell = (TableCell)localTableColumn.getCellFactory().call(localTableColumn);
/*     */ 
/* 227 */         localTableCell.updateTableColumn(localTableColumn);
/* 228 */         localTableCell.updateTableView(localTableView);
/* 229 */         localTableCell.updateTableRow((TableRow)getSkinnable());
/*     */ 
/* 232 */         this.cellsMap.put(localTableColumn, localTableCell);
/*     */       }
/*     */   }
/*     */ 
/*     */   private void clearCellsMap() {
/* 237 */     if (this.cellsMap != null) this.cellsMap.clear();
/*     */   }
/*     */ 
/*     */   private void updateCells(boolean paramBoolean)
/*     */   {
/* 243 */     this.cells.clear();
/*     */ 
/* 245 */     TableView localTableView = ((TableRow)getSkinnable()).getTableView();
/* 246 */     if (localTableView != null) {
/* 247 */       for (TableColumn localTableColumn : localTableView.getVisibleLeafColumns()) {
/* 248 */         TableCell localTableCell = (TableCell)this.cellsMap.get(localTableColumn);
/* 249 */         if (localTableCell != null)
/*     */         {
/* 251 */           localTableCell.updateIndex(((TableRow)getSkinnable()).getIndex());
/* 252 */           localTableCell.updateTableRow((TableRow)getSkinnable());
/* 253 */           this.cells.add(localTableCell);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 258 */     if (paramBoolean)
/* 259 */       if (this.showColumns) {
/* 260 */         if (this.cells.isEmpty()) {
/* 261 */           getChildren().clear();
/*     */         }
/*     */         else
/*     */         {
/* 266 */           getChildren().setAll(this.cells);
/*     */         }
/*     */       } else {
/* 269 */         getChildren().clear();
/*     */ 
/* 271 */         if ((!isIgnoreText()) || (!isIgnoreGraphic()))
/* 272 */           getChildren().add(getSkinnable());
/*     */       }
/*     */   }
/*     */ 
/*     */   protected double computePrefWidth(double paramDouble)
/*     */   {
/* 279 */     doUpdateCheck();
/*     */ 
/* 281 */     if (this.showColumns) {
/* 282 */       double d = 0.0D;
/*     */ 
/* 284 */       if (((TableRow)getSkinnable()).getTableView() != null) {
/* 285 */         for (TableColumn localTableColumn : ((TableRow)getSkinnable()).getTableView().getVisibleLeafColumns()) {
/* 286 */           d += localTableColumn.getWidth();
/*     */         }
/*     */       }
/*     */ 
/* 290 */       return d;
/*     */     }
/* 292 */     return super.computePrefWidth(paramDouble);
/*     */   }
/*     */ 
/*     */   protected double computePrefHeight(double paramDouble)
/*     */   {
/* 297 */     doUpdateCheck();
/*     */ 
/* 299 */     if (this.showColumns)
/*     */     {
/* 304 */       if (getCellSize() < 24.0D) {
/* 305 */         return getCellSize();
/*     */       }
/*     */ 
/* 310 */       double d = 0.0D;
/* 311 */       int i = this.cells.size();
/* 312 */       for (int j = 0; j < i; j++) {
/* 313 */         TableCell localTableCell = (TableCell)this.cells.get(j);
/* 314 */         d = Math.max(d, localTableCell.prefHeight(-1.0D));
/*     */       }
/* 316 */       return Math.max(d, Math.max(getCellSize(), minHeight(-1.0D)));
/*     */     }
/* 318 */     return super.computePrefHeight(paramDouble);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.skin.TableRowSkin
 * JD-Core Version:    0.6.2
 */