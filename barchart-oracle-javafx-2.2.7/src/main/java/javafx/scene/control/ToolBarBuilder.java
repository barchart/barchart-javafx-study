/*    */ package javafx.scene.control;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import javafx.collections.ObservableList;
/*    */ import javafx.geometry.Orientation;
/*    */ import javafx.scene.Node;
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class ToolBarBuilder<B extends ToolBarBuilder<B>> extends ControlBuilder<B>
/*    */   implements Builder<ToolBar>
/*    */ {
/*    */   private int __set;
/*    */   private Collection<? extends Node> items;
/*    */   private Orientation orientation;
/*    */ 
/*    */   public static ToolBarBuilder<?> create()
/*    */   {
/* 15 */     return new ToolBarBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(ToolBar paramToolBar)
/*    */   {
/* 20 */     super.applyTo(paramToolBar);
/* 21 */     int i = this.__set;
/* 22 */     if ((i & 0x1) != 0) paramToolBar.getItems().addAll(this.items);
/* 23 */     if ((i & 0x2) != 0) paramToolBar.setOrientation(this.orientation);
/*    */   }
/*    */ 
/*    */   public B items(Collection<? extends Node> paramCollection)
/*    */   {
/* 32 */     this.items = paramCollection;
/* 33 */     this.__set |= 1;
/* 34 */     return this;
/*    */   }
/*    */ 
/*    */   public B items(Node[] paramArrayOfNode)
/*    */   {
/* 41 */     return items(Arrays.asList(paramArrayOfNode));
/*    */   }
/*    */ 
/*    */   public B orientation(Orientation paramOrientation)
/*    */   {
/* 50 */     this.orientation = paramOrientation;
/* 51 */     this.__set |= 2;
/* 52 */     return this;
/*    */   }
/*    */ 
/*    */   public ToolBar build()
/*    */   {
/* 59 */     ToolBar localToolBar = new ToolBar();
/* 60 */     applyTo(localToolBar);
/* 61 */     return localToolBar;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.control.ToolBarBuilder
 * JD-Core Version:    0.6.2
 */