/*    */ package javafx.animation;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import javafx.collections.ObservableList;
/*    */ import javafx.scene.Node;
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public final class SequentialTransitionBuilder extends TransitionBuilder<SequentialTransitionBuilder>
/*    */   implements Builder<SequentialTransition>
/*    */ {
/*    */   private int __set;
/*    */   private Collection<? extends Animation> children;
/*    */   private Node node;
/*    */ 
/*    */   public static SequentialTransitionBuilder create()
/*    */   {
/* 15 */     return new SequentialTransitionBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(SequentialTransition paramSequentialTransition)
/*    */   {
/* 20 */     super.applyTo(paramSequentialTransition);
/* 21 */     int i = this.__set;
/* 22 */     if ((i & 0x1) != 0) paramSequentialTransition.getChildren().addAll(this.children);
/* 23 */     if ((i & 0x2) != 0) paramSequentialTransition.setNode(this.node);
/*    */   }
/*    */ 
/*    */   public SequentialTransitionBuilder children(Collection<? extends Animation> paramCollection)
/*    */   {
/* 31 */     this.children = paramCollection;
/* 32 */     this.__set |= 1;
/* 33 */     return this;
/*    */   }
/*    */ 
/*    */   public SequentialTransitionBuilder children(Animation[] paramArrayOfAnimation)
/*    */   {
/* 40 */     return children(Arrays.asList(paramArrayOfAnimation));
/*    */   }
/*    */ 
/*    */   public SequentialTransitionBuilder node(Node paramNode)
/*    */   {
/* 48 */     this.node = paramNode;
/* 49 */     this.__set |= 2;
/* 50 */     return this;
/*    */   }
/*    */ 
/*    */   public SequentialTransition build()
/*    */   {
/* 57 */     SequentialTransition localSequentialTransition = new SequentialTransition();
/* 58 */     applyTo(localSequentialTransition);
/* 59 */     return localSequentialTransition;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.animation.SequentialTransitionBuilder
 * JD-Core Version:    0.6.2
 */