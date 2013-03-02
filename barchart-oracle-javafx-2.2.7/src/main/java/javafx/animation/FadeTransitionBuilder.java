/*    */ package javafx.animation;
/*    */ 
/*    */ import javafx.scene.Node;
/*    */ import javafx.util.Builder;
/*    */ import javafx.util.Duration;
/*    */ 
/*    */ public final class FadeTransitionBuilder extends TransitionBuilder<FadeTransitionBuilder>
/*    */   implements Builder<FadeTransition>
/*    */ {
/*    */   private int __set;
/*    */   private double byValue;
/*    */   private Duration duration;
/*    */   private double fromValue;
/*    */   private Node node;
/*    */   private double toValue;
/*    */ 
/*    */   public static FadeTransitionBuilder create()
/*    */   {
/* 15 */     return new FadeTransitionBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(FadeTransition paramFadeTransition)
/*    */   {
/* 20 */     super.applyTo(paramFadeTransition);
/* 21 */     int i = this.__set;
/* 22 */     if ((i & 0x1) != 0) paramFadeTransition.setByValue(this.byValue);
/* 23 */     if ((i & 0x2) != 0) paramFadeTransition.setDuration(this.duration);
/* 24 */     if ((i & 0x4) != 0) paramFadeTransition.setFromValue(this.fromValue);
/* 25 */     if ((i & 0x8) != 0) paramFadeTransition.setNode(this.node);
/* 26 */     if ((i & 0x10) != 0) paramFadeTransition.setToValue(this.toValue);
/*    */   }
/*    */ 
/*    */   public FadeTransitionBuilder byValue(double paramDouble)
/*    */   {
/* 34 */     this.byValue = paramDouble;
/* 35 */     this.__set |= 1;
/* 36 */     return this;
/*    */   }
/*    */ 
/*    */   public FadeTransitionBuilder duration(Duration paramDuration)
/*    */   {
/* 44 */     this.duration = paramDuration;
/* 45 */     this.__set |= 2;
/* 46 */     return this;
/*    */   }
/*    */ 
/*    */   public FadeTransitionBuilder fromValue(double paramDouble)
/*    */   {
/* 54 */     this.fromValue = paramDouble;
/* 55 */     this.__set |= 4;
/* 56 */     return this;
/*    */   }
/*    */ 
/*    */   public FadeTransitionBuilder node(Node paramNode)
/*    */   {
/* 64 */     this.node = paramNode;
/* 65 */     this.__set |= 8;
/* 66 */     return this;
/*    */   }
/*    */ 
/*    */   public FadeTransitionBuilder toValue(double paramDouble)
/*    */   {
/* 74 */     this.toValue = paramDouble;
/* 75 */     this.__set |= 16;
/* 76 */     return this;
/*    */   }
/*    */ 
/*    */   public FadeTransition build()
/*    */   {
/* 83 */     FadeTransition localFadeTransition = new FadeTransition();
/* 84 */     applyTo(localFadeTransition);
/* 85 */     return localFadeTransition;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.animation.FadeTransitionBuilder
 * JD-Core Version:    0.6.2
 */