/*    */ package javafx.animation;
/*    */ 
/*    */ import javafx.scene.paint.Color;
/*    */ import javafx.scene.shape.Shape;
/*    */ import javafx.util.Builder;
/*    */ import javafx.util.Duration;
/*    */ 
/*    */ public final class StrokeTransitionBuilder extends TransitionBuilder<StrokeTransitionBuilder>
/*    */   implements Builder<StrokeTransition>
/*    */ {
/*    */   private int __set;
/*    */   private Duration duration;
/*    */   private Color fromValue;
/*    */   private Shape shape;
/*    */   private Color toValue;
/*    */ 
/*    */   public static StrokeTransitionBuilder create()
/*    */   {
/* 15 */     return new StrokeTransitionBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(StrokeTransition paramStrokeTransition)
/*    */   {
/* 20 */     super.applyTo(paramStrokeTransition);
/* 21 */     int i = this.__set;
/* 22 */     if ((i & 0x1) != 0) paramStrokeTransition.setDuration(this.duration);
/* 23 */     if ((i & 0x2) != 0) paramStrokeTransition.setFromValue(this.fromValue);
/* 24 */     if ((i & 0x4) != 0) paramStrokeTransition.setShape(this.shape);
/* 25 */     if ((i & 0x8) != 0) paramStrokeTransition.setToValue(this.toValue);
/*    */   }
/*    */ 
/*    */   public StrokeTransitionBuilder duration(Duration paramDuration)
/*    */   {
/* 33 */     this.duration = paramDuration;
/* 34 */     this.__set |= 1;
/* 35 */     return this;
/*    */   }
/*    */ 
/*    */   public StrokeTransitionBuilder fromValue(Color paramColor)
/*    */   {
/* 43 */     this.fromValue = paramColor;
/* 44 */     this.__set |= 2;
/* 45 */     return this;
/*    */   }
/*    */ 
/*    */   public StrokeTransitionBuilder shape(Shape paramShape)
/*    */   {
/* 53 */     this.shape = paramShape;
/* 54 */     this.__set |= 4;
/* 55 */     return this;
/*    */   }
/*    */ 
/*    */   public StrokeTransitionBuilder toValue(Color paramColor)
/*    */   {
/* 63 */     this.toValue = paramColor;
/* 64 */     this.__set |= 8;
/* 65 */     return this;
/*    */   }
/*    */ 
/*    */   public StrokeTransition build()
/*    */   {
/* 72 */     StrokeTransition localStrokeTransition = new StrokeTransition();
/* 73 */     applyTo(localStrokeTransition);
/* 74 */     return localStrokeTransition;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.animation.StrokeTransitionBuilder
 * JD-Core Version:    0.6.2
 */