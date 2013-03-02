/*    */ package javafx.animation;
/*    */ 
/*    */ import javafx.util.Builder;
/*    */ import javafx.util.Duration;
/*    */ 
/*    */ public final class PauseTransitionBuilder extends TransitionBuilder<PauseTransitionBuilder>
/*    */   implements Builder<PauseTransition>
/*    */ {
/*    */   private boolean __set;
/*    */   private Duration duration;
/*    */ 
/*    */   public static PauseTransitionBuilder create()
/*    */   {
/* 15 */     return new PauseTransitionBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(PauseTransition paramPauseTransition)
/*    */   {
/* 20 */     super.applyTo(paramPauseTransition);
/* 21 */     if (this.__set) paramPauseTransition.setDuration(this.duration);
/*    */   }
/*    */ 
/*    */   public PauseTransitionBuilder duration(Duration paramDuration)
/*    */   {
/* 29 */     this.duration = paramDuration;
/* 30 */     this.__set = true;
/* 31 */     return this;
/*    */   }
/*    */ 
/*    */   public PauseTransition build()
/*    */   {
/* 38 */     PauseTransition localPauseTransition = new PauseTransition();
/* 39 */     applyTo(localPauseTransition);
/* 40 */     return localPauseTransition;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.animation.PauseTransitionBuilder
 * JD-Core Version:    0.6.2
 */