/*    */ package javafx.scene.control;
/*    */ 
/*    */ public class ListCellBuilder<T, B extends ListCellBuilder<T, B>> extends IndexedCellBuilder<T, B>
/*    */ {
/*    */   public static <T> ListCellBuilder<T, ?> create()
/*    */   {
/* 15 */     return new ListCellBuilder();
/*    */   }
/*    */ 
/*    */   public ListCell<T> build()
/*    */   {
/* 22 */     ListCell localListCell = new ListCell();
/* 23 */     applyTo(localListCell);
/* 24 */     return localListCell;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.ListCellBuilder
 * JD-Core Version:    0.6.2
 */