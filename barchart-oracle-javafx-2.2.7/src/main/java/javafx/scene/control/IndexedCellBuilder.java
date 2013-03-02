/*    */ package javafx.scene.control;
/*    */ 
/*    */ public class IndexedCellBuilder<T, B extends IndexedCellBuilder<T, B>> extends CellBuilder<T, B>
/*    */ {
/*    */   public static <T> IndexedCellBuilder<T, ?> create()
/*    */   {
/* 15 */     return new IndexedCellBuilder();
/*    */   }
/*    */ 
/*    */   public IndexedCell<T> build()
/*    */   {
/* 22 */     IndexedCell localIndexedCell = new IndexedCell();
/* 23 */     applyTo(localIndexedCell);
/* 24 */     return localIndexedCell;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.IndexedCellBuilder
 * JD-Core Version:    0.6.2
 */