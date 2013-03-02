/*    */ package javafx.scene;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import javafx.collections.ObservableList;
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class GroupBuilder<B extends GroupBuilder<B>> extends ParentBuilder<B>
/*    */   implements Builder<Group>
/*    */ {
/*    */   private int __set;
/*    */   private boolean autoSizeChildren;
/*    */   private Collection<? extends Node> children;
/*    */ 
/*    */   public static GroupBuilder<?> create()
/*    */   {
/* 15 */     return new GroupBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(Group paramGroup)
/*    */   {
/* 20 */     super.applyTo(paramGroup);
/* 21 */     int i = this.__set;
/* 22 */     if ((i & 0x1) != 0) paramGroup.setAutoSizeChildren(this.autoSizeChildren);
/* 23 */     if ((i & 0x2) != 0) paramGroup.getChildren().addAll(this.children);
/*    */   }
/*    */ 
/*    */   public B autoSizeChildren(boolean paramBoolean)
/*    */   {
/* 32 */     this.autoSizeChildren = paramBoolean;
/* 33 */     this.__set |= 1;
/* 34 */     return this;
/*    */   }
/*    */ 
/*    */   public B children(Collection<? extends Node> paramCollection)
/*    */   {
/* 43 */     this.children = paramCollection;
/* 44 */     this.__set |= 2;
/* 45 */     return this;
/*    */   }
/*    */ 
/*    */   public B children(Node[] paramArrayOfNode)
/*    */   {
/* 52 */     return children(Arrays.asList(paramArrayOfNode));
/*    */   }
/*    */ 
/*    */   public Group build()
/*    */   {
/* 59 */     Group localGroup = new Group();
/* 60 */     applyTo(localGroup);
/* 61 */     return localGroup;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.GroupBuilder
 * JD-Core Version:    0.6.2
 */