/*    */ package javafx.animation;
/*    */ 
/*    */ import javafx.scene.paint.Color;
/*    */ import javafx.scene.shape.Shape;
/*    */ import javafx.util.Builder;
/*    */ import javafx.util.Duration;
/*    */ 
/*    */ public final class FillTransitionBuilder extends TransitionBuilder<FillTransitionBuilder>
/*    */   implements Builder<FillTransition>
/*    */ {
/*    */   private int __set;
/*    */   private Duration duration;
/*    */   private Color fromValue;
/*    */   private Shape shape;
/*    */   private Color toValue;
/*    */ 
/*    */   public static FillTransitionBuilder create()
/*    */   {
/* 15 */     return new FillTransitionBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(FillTransition paramFillTransition)
/*    */   {
/* 20 */     super.applyTo(paramFillTransition);
/* 21 */     int i = this.__set;
/* 22 */     if ((i & 0x1) != 0) paramFillTransition.setDuration(this.duration);
/* 23 */     if ((i & 0x2) != 0) paramFillTransition.setFromValue(this.fromValue);
/* 24 */     if ((i & 0x4) != 0) paramFillTransition.setShape(this.shape);
/* 25 */     if ((i & 0x8) != 0) paramFillTransition.setToValue(this.toValue);
/*    */   }
/*    */ 
/*    */   public FillTransitionBuilder duration(Duration paramDuration)
/*    */   {
/* 33 */     this.duration = paramDuration;
/* 34 */     this.__set |= 1;
/* 35 */     return this;
/*    */   }
/*    */ 
/*    */   public FillTransitionBuilder fromValue(Color paramColor)
/*    */   {
/* 43 */     this.fromValue = paramColor;
/* 44 */     this.__set |= 2;
/* 45 */     return this;
/*    */   }
/*    */ 
/*    */   public FillTransitionBuilder shape(Shape paramShape)
/*    */   {
/* 53 */     this.shape = paramShape;
/* 54 */     this.__set |= 4;
/* 55 */     return this;
/*    */   }
/*    */ 
/*    */   public FillTransitionBuilder toValue(Color paramColor)
/*    */   {
/* 63 */     this.toValue = paramColor;
/* 64 */     this.__set |= 8;
/* 65 */     return this;
/*    */   }
/*    */ 
/*    */   public FillTransition build()
/*    */   {
/* 72 */     FillTransition localFillTransition = new FillTransition();
/* 73 */     applyTo(localFillTransition);
/* 74 */     return localFillTransition;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.animation.FillTransitionBuilder
 * JD-Core Version:    0.6.2
 */