/*    */ package javafx.animation;
/*    */ 
/*    */ import javafx.scene.Node;
/*    */ import javafx.scene.shape.Shape;
/*    */ import javafx.util.Builder;
/*    */ import javafx.util.Duration;
/*    */ 
/*    */ public final class PathTransitionBuilder extends TransitionBuilder<PathTransitionBuilder>
/*    */   implements Builder<PathTransition>
/*    */ {
/*    */   private int __set;
/*    */   private Duration duration;
/*    */   private Node node;
/*    */   private PathTransition.OrientationType orientation;
/*    */   private Shape path;
/*    */ 
/*    */   public static PathTransitionBuilder create()
/*    */   {
/* 15 */     return new PathTransitionBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(PathTransition paramPathTransition)
/*    */   {
/* 20 */     super.applyTo(paramPathTransition);
/* 21 */     int i = this.__set;
/* 22 */     if ((i & 0x1) != 0) paramPathTransition.setDuration(this.duration);
/* 23 */     if ((i & 0x2) != 0) paramPathTransition.setNode(this.node);
/* 24 */     if ((i & 0x4) != 0) paramPathTransition.setOrientation(this.orientation);
/* 25 */     if ((i & 0x8) != 0) paramPathTransition.setPath(this.path);
/*    */   }
/*    */ 
/*    */   public PathTransitionBuilder duration(Duration paramDuration)
/*    */   {
/* 33 */     this.duration = paramDuration;
/* 34 */     this.__set |= 1;
/* 35 */     return this;
/*    */   }
/*    */ 
/*    */   public PathTransitionBuilder node(Node paramNode)
/*    */   {
/* 43 */     this.node = paramNode;
/* 44 */     this.__set |= 2;
/* 45 */     return this;
/*    */   }
/*    */ 
/*    */   public PathTransitionBuilder orientation(PathTransition.OrientationType paramOrientationType)
/*    */   {
/* 53 */     this.orientation = paramOrientationType;
/* 54 */     this.__set |= 4;
/* 55 */     return this;
/*    */   }
/*    */ 
/*    */   public PathTransitionBuilder path(Shape paramShape)
/*    */   {
/* 63 */     this.path = paramShape;
/* 64 */     this.__set |= 8;
/* 65 */     return this;
/*    */   }
/*    */ 
/*    */   public PathTransition build()
/*    */   {
/* 72 */     PathTransition localPathTransition = new PathTransition();
/* 73 */     applyTo(localPathTransition);
/* 74 */     return localPathTransition;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.animation.PathTransitionBuilder
 * JD-Core Version:    0.6.2
 */