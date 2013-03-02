/*    */ package javafx.scene.control;
/*    */ 
/*    */ import javafx.scene.Node;
/*    */ 
/*    */ public class TreeCellBuilder<T, B extends TreeCellBuilder<T, B>> extends IndexedCellBuilder<T, B>
/*    */ {
/*    */   private boolean __set;
/*    */   private Node disclosureNode;
/*    */ 
/*    */   public static <T> TreeCellBuilder<T, ?> create()
/*    */   {
/* 15 */     return new TreeCellBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(TreeCell<T> paramTreeCell)
/*    */   {
/* 20 */     super.applyTo(paramTreeCell);
/* 21 */     if (this.__set) paramTreeCell.setDisclosureNode(this.disclosureNode);
/*    */   }
/*    */ 
/*    */   public B disclosureNode(Node paramNode)
/*    */   {
/* 30 */     this.disclosureNode = paramNode;
/* 31 */     this.__set = true;
/* 32 */     return this;
/*    */   }
/*    */ 
/*    */   public TreeCell<T> build()
/*    */   {
/* 39 */     TreeCell localTreeCell = new TreeCell();
/* 40 */     applyTo(localTreeCell);
/* 41 */     return localTreeCell;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.TreeCellBuilder
 * JD-Core Version:    0.6.2
 */