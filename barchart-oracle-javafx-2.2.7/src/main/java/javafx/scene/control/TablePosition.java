/*     */ package javafx.scene.control;
/*     */ 
/*     */ public class TablePosition<S, T>
/*     */ {
/*     */   private final int row;
/*     */   private final TableColumn<S, T> tableColumn;
/*     */   private final TableView<S> tableView;
/*     */ 
/*     */   public TablePosition(TableView<S> paramTableView, int paramInt, TableColumn<S, T> paramTableColumn)
/*     */   {
/*  61 */     this.row = paramInt;
/*  62 */     this.tableColumn = paramTableColumn;
/*  63 */     this.tableView = paramTableView;
/*     */   }
/*     */ 
/*     */   public final int getRow()
/*     */   {
/*  90 */     return this.row;
/*     */   }
/*     */ 
/*     */   public final int getColumn()
/*     */   {
/*  98 */     return (this.tableView == null) || (this.tableColumn == null) ? -1 : this.tableView.getVisibleLeafIndex(this.tableColumn);
/*     */   }
/*     */ 
/*     */   public final TableView<S> getTableView()
/*     */   {
/* 106 */     return this.tableView;
/*     */   }
/*     */ 
/*     */   public final TableColumn<S, T> getTableColumn()
/*     */   {
/* 113 */     return this.tableColumn;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 122 */     if (paramObject == null) {
/* 123 */       return false;
/*     */     }
/* 125 */     if (getClass() != paramObject.getClass()) {
/* 126 */       return false;
/*     */     }
/*     */ 
/* 129 */     TablePosition localTablePosition = (TablePosition)paramObject;
/* 130 */     if (this.row != localTablePosition.row) {
/* 131 */       return false;
/*     */     }
/* 133 */     if ((this.tableColumn != localTablePosition.tableColumn) && ((this.tableColumn == null) || (!this.tableColumn.equals(localTablePosition.tableColumn)))) {
/* 134 */       return false;
/*     */     }
/* 136 */     if ((this.tableView != localTablePosition.tableView) && ((this.tableView == null) || (!this.tableView.equals(localTablePosition.tableView)))) {
/* 137 */       return false;
/*     */     }
/* 139 */     return true;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 147 */     int i = 5;
/* 148 */     i = 79 * i + this.row;
/* 149 */     i = 79 * i + (this.tableColumn != null ? this.tableColumn.hashCode() : 0);
/* 150 */     i = 79 * i + (this.tableView != null ? this.tableView.hashCode() : 0);
/* 151 */     return i;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 159 */     return "TablePosition [ row: " + this.row + ", column: " + this.tableColumn + ", " + "tableView: " + this.tableView + " ]";
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.TablePosition
 * JD-Core Version:    0.6.2
 */