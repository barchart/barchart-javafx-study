/*    */ package javafx.scene.control;
/*    */ 
/*    */ public class CheckBoxTreeItemBuilder<T, B extends CheckBoxTreeItemBuilder<T, B>> extends TreeItemBuilder<T, B>
/*    */ {
/*    */   public static <T> CheckBoxTreeItemBuilder<T, ?> create()
/*    */   {
/* 15 */     return new CheckBoxTreeItemBuilder();
/*    */   }
/*    */ 
/*    */   public CheckBoxTreeItem<T> build()
/*    */   {
/* 22 */     CheckBoxTreeItem localCheckBoxTreeItem = new CheckBoxTreeItem();
/* 23 */     applyTo(localCheckBoxTreeItem);
/* 24 */     return localCheckBoxTreeItem;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.CheckBoxTreeItemBuilder
 * JD-Core Version:    0.6.2
 */