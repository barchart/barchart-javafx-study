/*    */ package javafx.scene.layout;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import javafx.collections.ObservableList;
/*    */ import javafx.scene.Node;
/*    */ 
/*    */ public class PaneBuilder<B extends PaneBuilder<B>> extends RegionBuilder<B>
/*    */ {
/*    */   private boolean __set;
/*    */   private Collection<? extends Node> children;
/*    */ 
/*    */   public static PaneBuilder<?> create()
/*    */   {
/* 15 */     return new PaneBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(Pane paramPane)
/*    */   {
/* 20 */     super.applyTo(paramPane);
/* 21 */     if (this.__set) paramPane.getChildren().addAll(this.children);
/*    */   }
/*    */ 
/*    */   public B children(Collection<? extends Node> paramCollection)
/*    */   {
/* 30 */     this.children = paramCollection;
/* 31 */     this.__set = true;
/* 32 */     return this;
/*    */   }
/*    */ 
/*    */   public B children(Node[] paramArrayOfNode)
/*    */   {
/* 39 */     return children(Arrays.asList(paramArrayOfNode));
/*    */   }
/*    */ 
/*    */   public Pane build()
/*    */   {
/* 46 */     Pane localPane = new Pane();
/* 47 */     applyTo(localPane);
/* 48 */     return localPane;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.layout.PaneBuilder
 * JD-Core Version:    0.6.2
 */