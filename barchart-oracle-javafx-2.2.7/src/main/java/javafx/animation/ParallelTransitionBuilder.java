/*    */ package javafx.animation;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import javafx.collections.ObservableList;
/*    */ import javafx.scene.Node;
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public final class ParallelTransitionBuilder extends TransitionBuilder<ParallelTransitionBuilder>
/*    */   implements Builder<ParallelTransition>
/*    */ {
/*    */   private int __set;
/*    */   private Collection<? extends Animation> children;
/*    */   private Node node;
/*    */ 
/*    */   public static ParallelTransitionBuilder create()
/*    */   {
/* 15 */     return new ParallelTransitionBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(ParallelTransition paramParallelTransition)
/*    */   {
/* 20 */     super.applyTo(paramParallelTransition);
/* 21 */     int i = this.__set;
/* 22 */     if ((i & 0x1) != 0) paramParallelTransition.getChildren().addAll(this.children);
/* 23 */     if ((i & 0x2) != 0) paramParallelTransition.setNode(this.node);
/*    */   }
/*    */ 
/*    */   public ParallelTransitionBuilder children(Collection<? extends Animation> paramCollection)
/*    */   {
/* 31 */     this.children = paramCollection;
/* 32 */     this.__set |= 1;
/* 33 */     return this;
/*    */   }
/*    */ 
/*    */   public ParallelTransitionBuilder children(Animation[] paramArrayOfAnimation)
/*    */   {
/* 40 */     return children(Arrays.asList(paramArrayOfAnimation));
/*    */   }
/*    */ 
/*    */   public ParallelTransitionBuilder node(Node paramNode)
/*    */   {
/* 48 */     this.node = paramNode;
/* 49 */     this.__set |= 2;
/* 50 */     return this;
/*    */   }
/*    */ 
/*    */   public ParallelTransition build()
/*    */   {
/* 57 */     ParallelTransition localParallelTransition = new ParallelTransition();
/* 58 */     applyTo(localParallelTransition);
/* 59 */     return localParallelTransition;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.animation.ParallelTransitionBuilder
 * JD-Core Version:    0.6.2
 */