/*    */ package javafx.scene.control;
/*    */ 
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class TablePositionBuilder<S, T, B extends TablePositionBuilder<S, T, B>>
/*    */   implements Builder<TablePosition<S, T>>
/*    */ {
/*    */   private int row;
/*    */   private TableColumn<S, T> tableColumn;
/*    */   private TableView<S> tableView;
/*    */ 
/*    */   public static <S, T> TablePositionBuilder<S, T, ?> create()
/*    */   {
/* 15 */     return new TablePositionBuilder();
/*    */   }
/*    */ 
/*    */   public B row(int paramInt)
/*    */   {
/* 24 */     this.row = paramInt;
/* 25 */     return this;
/*    */   }
/*    */ 
/*    */   public B tableColumn(TableColumn<S, T> paramTableColumn)
/*    */   {
/* 34 */     this.tableColumn = paramTableColumn;
/* 35 */     return this;
/*    */   }
/*    */ 
/*    */   public B tableView(TableView<S> paramTableView)
/*    */   {
/* 44 */     this.tableView = paramTableView;
/* 45 */     return this;
/*    */   }
/*    */ 
/*    */   public TablePosition<S, T> build()
/*    */   {
/* 52 */     TablePosition localTablePosition = new TablePosition(this.tableView, this.row, this.tableColumn);
/* 53 */     return localTablePosition;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.TablePositionBuilder
 * JD-Core Version:    0.6.2
 */