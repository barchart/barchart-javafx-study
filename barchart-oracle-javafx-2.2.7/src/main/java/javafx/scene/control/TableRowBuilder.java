/*    */ package javafx.scene.control;
/*    */ 
/*    */ public class TableRowBuilder<T, B extends TableRowBuilder<T, B>> extends IndexedCellBuilder<T, B>
/*    */ {
/*    */   public static <T> TableRowBuilder<T, ?> create()
/*    */   {
/* 15 */     return new TableRowBuilder();
/*    */   }
/*    */ 
/*    */   public TableRow<T> build()
/*    */   {
/* 22 */     TableRow localTableRow = new TableRow();
/* 23 */     applyTo(localTableRow);
/* 24 */     return localTableRow;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.TableRowBuilder
 * JD-Core Version:    0.6.2
 */