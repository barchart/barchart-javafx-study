/*     */ package com.sun.javafx.scene.control.behavior;
/*     */ 
/*     */ import com.sun.javafx.PlatformUtil;
/*     */ import java.util.List;
/*     */ import java.util.WeakHashMap;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.scene.control.SelectionMode;
/*     */ import javafx.scene.control.TableCell;
/*     */ import javafx.scene.control.TableColumn;
/*     */ import javafx.scene.control.TablePosition;
/*     */ import javafx.scene.control.TableRow;
/*     */ import javafx.scene.control.TableView;
/*     */ import javafx.scene.control.TableView.TableViewFocusModel;
/*     */ import javafx.scene.control.TableView.TableViewSelectionModel;
/*     */ import javafx.scene.input.MouseButton;
/*     */ import javafx.scene.input.MouseEvent;
/*     */ 
/*     */ public class TableCellBehavior extends CellBehaviorBase<TableCell>
/*     */ {
/*  49 */   private static final WeakHashMap<TableView, TablePosition> map = new WeakHashMap();
/*     */ 
/*  87 */   private boolean latePress = false;
/*  88 */   private final boolean isEmbedded = PlatformUtil.isEmbedded();
/*  89 */   private boolean wasSelected = false;
/*     */ 
/*     */   static TablePosition getAnchor(TableView paramTableView)
/*     */   {
/*  52 */     TableView.TableViewFocusModel localTableViewFocusModel = paramTableView.getFocusModel();
/*  53 */     if (localTableViewFocusModel == null) return null;
/*     */ 
/*  55 */     return hasAnchor(paramTableView) ? (TablePosition)map.get(paramTableView) : localTableViewFocusModel.getFocusedCell();
/*     */   }
/*     */ 
/*     */   static void setAnchor(TableView paramTableView, TablePosition paramTablePosition) {
/*  59 */     if ((paramTableView != null) && (paramTablePosition == null))
/*  60 */       map.remove(paramTableView);
/*     */     else
/*  62 */       map.put(paramTableView, paramTablePosition);
/*     */   }
/*     */ 
/*     */   static boolean hasAnchor(TableView paramTableView)
/*     */   {
/*  67 */     return (map.containsKey(paramTableView)) && (map.get(paramTableView) != null);
/*     */   }
/*     */ 
/*     */   public TableCellBehavior(TableCell paramTableCell)
/*     */   {
/* 100 */     super(paramTableCell);
/*     */   }
/*     */ 
/*     */   public void mousePressed(MouseEvent paramMouseEvent)
/*     */   {
/* 111 */     boolean bool = ((TableCell)getControl()).isSelected();
/*     */ 
/* 113 */     if (((TableCell)getControl()).isSelected()) {
/* 114 */       this.latePress = true;
/* 115 */       return;
/*     */     }
/*     */ 
/* 118 */     doSelect(paramMouseEvent);
/*     */ 
/* 120 */     if ((this.isEmbedded) && (bool))
/* 121 */       this.wasSelected = ((TableCell)getControl()).isSelected();
/*     */   }
/*     */ 
/*     */   public void mouseReleased(MouseEvent paramMouseEvent)
/*     */   {
/* 126 */     if (this.latePress) {
/* 127 */       this.latePress = false;
/* 128 */       doSelect(paramMouseEvent);
/*     */     }
/*     */ 
/* 131 */     this.wasSelected = false;
/*     */   }
/*     */ 
/*     */   public void mouseDragged(MouseEvent paramMouseEvent) {
/* 135 */     this.latePress = false;
/*     */ 
/* 140 */     if ((this.isEmbedded) && (!this.wasSelected) && (((TableCell)getControl()).isSelected()))
/* 141 */       ((TableCell)getControl()).getTableView().getSelectionModel().clearSelection(((TableCell)getControl()).getIndex());
/*     */   }
/*     */ 
/*     */   private void doSelect(MouseEvent paramMouseEvent)
/*     */   {
/* 156 */     TableCell localTableCell = (TableCell)getControl();
/*     */ 
/* 160 */     if (!localTableCell.contains(paramMouseEvent.getX(), paramMouseEvent.getY())) return;
/*     */ 
/* 162 */     TableView localTableView = localTableCell.getTableView();
/* 163 */     if (localTableView == null) return;
/*     */ 
/* 165 */     ObservableList localObservableList = localTableView.getItems();
/* 166 */     if ((localObservableList == null) || (localTableCell.getIndex() >= localObservableList.size())) return;
/*     */ 
/* 168 */     TableView.TableViewSelectionModel localTableViewSelectionModel = localTableView.getSelectionModel();
/* 169 */     if (localTableViewSelectionModel == null) return;
/*     */ 
/* 171 */     boolean bool = !localTableViewSelectionModel.isCellSelectionEnabled() ? localTableCell.getTableRow().isSelected() : localTableCell.isSelected();
/* 172 */     int i = localTableCell.getIndex();
/* 173 */     int j = getColumn();
/* 174 */     TableColumn localTableColumn = ((TableCell)getControl()).getTableColumn();
/*     */ 
/* 176 */     TableView.TableViewFocusModel localTableViewFocusModel = localTableView.getFocusModel();
/* 177 */     if (localTableViewFocusModel == null) return;
/*     */ 
/* 183 */     if (paramMouseEvent.isShiftDown()) {
/* 184 */       if (!map.containsKey(localTableView))
/* 185 */         setAnchor(localTableView, localTableViewFocusModel.getFocusedCell());
/*     */     }
/*     */     else {
/* 188 */       map.remove(localTableView);
/*     */     }
/*     */ 
/* 193 */     MouseButton localMouseButton = paramMouseEvent.getButton();
/* 194 */     if ((localMouseButton == MouseButton.PRIMARY) || ((localMouseButton == MouseButton.SECONDARY) && (!bool)))
/* 195 */       if (localTableViewSelectionModel.getSelectionMode() == SelectionMode.SINGLE) {
/* 196 */         simpleSelect(paramMouseEvent);
/*     */       }
/* 198 */       else if ((paramMouseEvent.isControlDown()) || (paramMouseEvent.isMetaDown())) {
/* 199 */         if (bool)
/*     */         {
/* 201 */           localTableViewSelectionModel.clearSelection(i, localTableColumn);
/* 202 */           localTableViewFocusModel.focus(i, localTableColumn);
/*     */         }
/*     */         else {
/* 205 */           localTableViewSelectionModel.select(i, localTableColumn);
/*     */         }
/* 207 */       } else if (paramMouseEvent.isShiftDown())
/*     */       {
/* 210 */         TablePosition localTablePosition = map.containsKey(localTableView) ? (TablePosition)map.get(localTableView) : localTableViewFocusModel.getFocusedCell();
/*     */ 
/* 213 */         int k = Math.min(localTablePosition.getRow(), i);
/* 214 */         int m = Math.max(localTablePosition.getRow(), i);
/* 215 */         int n = Math.min(localTablePosition.getColumn(), j);
/* 216 */         int i1 = Math.max(localTablePosition.getColumn(), j);
/*     */ 
/* 219 */         localTableViewSelectionModel.clearSelection();
/*     */ 
/* 222 */         if (localTableViewSelectionModel.isCellSelectionEnabled()) {
/* 223 */           for (int i2 = k; i2 <= m; i2++) {
/* 224 */             for (int i3 = n; i3 <= i1; i3++)
/* 225 */               localTableViewSelectionModel.select(i2, localTableView.getVisibleLeafColumn(i3));
/*     */           }
/*     */         }
/*     */         else {
/* 229 */           localTableViewSelectionModel.selectRange(k, m + 1);
/*     */         }
/*     */ 
/* 233 */         localTableViewFocusModel.focus(new TablePosition(localTableView, i, localTableColumn));
/*     */       } else {
/* 235 */         simpleSelect(paramMouseEvent);
/*     */       }
/*     */   }
/*     */ 
/*     */   private void simpleSelect(MouseEvent paramMouseEvent)
/*     */   {
/* 242 */     TableView localTableView = ((TableCell)getControl()).getTableView();
/* 243 */     TableView.TableViewSelectionModel localTableViewSelectionModel = localTableView.getSelectionModel();
/* 244 */     int i = ((TableCell)getControl()).getIndex();
/* 245 */     boolean bool = localTableViewSelectionModel.isSelected(i, ((TableCell)getControl()).getTableColumn());
/*     */ 
/* 247 */     localTableView.getSelectionModel().clearAndSelect(i, ((TableCell)getControl()).getTableColumn());
/*     */ 
/* 250 */     if (paramMouseEvent.getButton() == MouseButton.PRIMARY)
/* 251 */       if ((paramMouseEvent.getClickCount() == 1) && (bool))
/* 252 */         localTableView.edit(i, ((TableCell)getControl()).getTableColumn());
/* 253 */       else if (paramMouseEvent.getClickCount() == 1)
/*     */       {
/* 255 */         localTableView.edit(-1, null);
/* 256 */       } else if ((paramMouseEvent.getClickCount() == 2) && (((TableCell)getControl()).isEditable()))
/*     */       {
/* 258 */         localTableView.edit(i, ((TableCell)getControl()).getTableColumn());
/*     */       }
/*     */   }
/*     */ 
/*     */   private int getColumn()
/*     */   {
/* 264 */     if (((TableCell)getControl()).getTableView().getSelectionModel().isCellSelectionEnabled()) {
/* 265 */       TableColumn localTableColumn = ((TableCell)getControl()).getTableColumn();
/* 266 */       TableView localTableView = ((TableCell)getControl()).getTableView();
/* 267 */       if ((localTableView == null) || (localTableColumn == null)) {
/* 268 */         return -1;
/*     */       }
/* 270 */       return localTableView.getVisibleLeafColumns().indexOf(localTableColumn);
/*     */     }
/*     */ 
/* 273 */     return -1;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.behavior.TableCellBehavior
 * JD-Core Version:    0.6.2
 */