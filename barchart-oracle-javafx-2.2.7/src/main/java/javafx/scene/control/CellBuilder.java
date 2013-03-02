/*    */ package javafx.scene.control;
/*    */ 
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class CellBuilder<T, B extends CellBuilder<T, B>> extends LabeledBuilder<B>
/*    */   implements Builder<Cell<T>>
/*    */ {
/*    */   private int __set;
/*    */   private boolean editable;
/*    */   private T item;
/*    */ 
/*    */   public static <T> CellBuilder<T, ?> create()
/*    */   {
/* 15 */     return new CellBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(Cell<T> paramCell)
/*    */   {
/* 20 */     super.applyTo(paramCell);
/* 21 */     int i = this.__set;
/* 22 */     if ((i & 0x1) != 0) paramCell.setEditable(this.editable);
/* 23 */     if ((i & 0x2) != 0) paramCell.setItem(this.item);
/*    */   }
/*    */ 
/*    */   public B editable(boolean paramBoolean)
/*    */   {
/* 32 */     this.editable = paramBoolean;
/* 33 */     this.__set |= 1;
/* 34 */     return this;
/*    */   }
/*    */ 
/*    */   public B item(T paramT)
/*    */   {
/* 43 */     this.item = paramT;
/* 44 */     this.__set |= 2;
/* 45 */     return this;
/*    */   }
/*    */ 
/*    */   public Cell<T> build()
/*    */   {
/* 52 */     Cell localCell = new Cell();
/* 53 */     applyTo(localCell);
/* 54 */     return localCell;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.CellBuilder
 * JD-Core Version:    0.6.2
 */