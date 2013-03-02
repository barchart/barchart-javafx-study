/*    */ package javafx.animation;
/*    */ 
/*    */ public abstract class TransitionBuilder<B extends TransitionBuilder<B>> extends AnimationBuilder<B>
/*    */ {
/*    */   private boolean __set;
/*    */   private Interpolator interpolator;
/*    */   private double targetFramerate;
/*    */ 
/*    */   public void applyTo(Transition paramTransition)
/*    */   {
/* 15 */     super.applyTo(paramTransition);
/* 16 */     if (this.__set) paramTransition.setInterpolator(this.interpolator);
/*    */   }
/*    */ 
/*    */   public B interpolator(Interpolator paramInterpolator)
/*    */   {
/* 25 */     this.interpolator = paramInterpolator;
/* 26 */     this.__set = true;
/* 27 */     return this;
/*    */   }
/*    */ 
/*    */   public B targetFramerate(double paramDouble)
/*    */   {
/* 36 */     this.targetFramerate = paramDouble;
/* 37 */     return this;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.animation.TransitionBuilder
 * JD-Core Version:    0.6.2
 */