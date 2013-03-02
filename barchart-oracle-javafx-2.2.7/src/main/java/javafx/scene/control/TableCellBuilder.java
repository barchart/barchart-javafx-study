/*    */ package javafx.scene.control;
/*    */ 
/*    */ public class TableCellBuilder<S, T, B extends TableCellBuilder<S, T, B>> extends IndexedCellBuilder<T, B>
/*    */ {
/*    */   public static <S, T> TableCellBuilder<S, T, ?> create()
/*    */   {
/* 15 */     return new TableCellBuilder();
/*    */   }
/*    */ 
/*    */   public TableCell<S, T> build()
/*    */   {
/* 22 */     TableCell localTableCell = new TableCell();
/* 23 */     applyTo(localTableCell);
/* 24 */     return localTableCell;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.TableCellBuilder
 * JD-Core Version:    0.6.2
 */