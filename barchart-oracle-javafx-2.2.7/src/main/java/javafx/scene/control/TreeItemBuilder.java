/*    */ package javafx.scene.control;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import javafx.collections.ObservableList;
/*    */ import javafx.scene.Node;
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class TreeItemBuilder<T, B extends TreeItemBuilder<T, B>>
/*    */   implements Builder<TreeItem<T>>
/*    */ {
/*    */   private int __set;
/*    */   private Collection<? extends TreeItem<T>> children;
/*    */   private boolean expanded;
/*    */   private Node graphic;
/*    */   private T value;
/*    */ 
/*    */   public static <T> TreeItemBuilder<T, ?> create()
/*    */   {
/* 15 */     return new TreeItemBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(TreeItem<T> paramTreeItem)
/*    */   {
/* 20 */     int i = this.__set;
/* 21 */     if ((i & 0x1) != 0) paramTreeItem.getChildren().addAll(this.children);
/* 22 */     if ((i & 0x2) != 0) paramTreeItem.setExpanded(this.expanded);
/* 23 */     if ((i & 0x4) != 0) paramTreeItem.setGraphic(this.graphic);
/* 24 */     if ((i & 0x8) != 0) paramTreeItem.setValue(this.value);
/*    */   }
/*    */ 
/*    */   public B children(Collection<? extends TreeItem<T>> paramCollection)
/*    */   {
/* 33 */     this.children = paramCollection;
/* 34 */     this.__set |= 1;
/* 35 */     return this;
/*    */   }
/*    */ 
/*    */   public B children(TreeItem<T>[] paramArrayOfTreeItem)
/*    */   {
/* 42 */     return children(Arrays.asList(paramArrayOfTreeItem));
/*    */   }
/*    */ 
/*    */   public B expanded(boolean paramBoolean)
/*    */   {
/* 51 */     this.expanded = paramBoolean;
/* 52 */     this.__set |= 2;
/* 53 */     return this;
/*    */   }
/*    */ 
/*    */   public B graphic(Node paramNode)
/*    */   {
/* 62 */     this.graphic = paramNode;
/* 63 */     this.__set |= 4;
/* 64 */     return this;
/*    */   }
/*    */ 
/*    */   public B value(T paramT)
/*    */   {
/* 73 */     this.value = paramT;
/* 74 */     this.__set |= 8;
/* 75 */     return this;
/*    */   }
/*    */ 
/*    */   public TreeItem<T> build()
/*    */   {
/* 82 */     TreeItem localTreeItem = new TreeItem();
/* 83 */     applyTo(localTreeItem);
/* 84 */     return localTreeItem;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.TreeItemBuilder
 * JD-Core Version:    0.6.2
 */