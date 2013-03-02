/*    */ package javafx.animation;
/*    */ 
/*    */ import javafx.event.ActionEvent;
/*    */ import javafx.event.EventHandler;
/*    */ import javafx.util.Duration;
/*    */ 
/*    */ public abstract class AnimationBuilder<B extends AnimationBuilder<B>>
/*    */ {
/*    */   private int __set;
/*    */   private boolean autoReverse;
/*    */   private int cycleCount;
/*    */   private Duration delay;
/*    */   private EventHandler<ActionEvent> onFinished;
/*    */   private double rate;
/*    */   private double targetFramerate;
/*    */ 
/*    */   public void applyTo(Animation paramAnimation)
/*    */   {
/* 15 */     int i = this.__set;
/* 16 */     if ((i & 0x1) != 0) paramAnimation.setAutoReverse(this.autoReverse);
/* 17 */     if ((i & 0x2) != 0) paramAnimation.setCycleCount(this.cycleCount);
/* 18 */     if ((i & 0x4) != 0) paramAnimation.setDelay(this.delay);
/* 19 */     if ((i & 0x8) != 0) paramAnimation.setOnFinished(this.onFinished);
/* 20 */     if ((i & 0x10) != 0) paramAnimation.setRate(this.rate);
/*    */   }
/*    */ 
/*    */   public B autoReverse(boolean paramBoolean)
/*    */   {
/* 29 */     this.autoReverse = paramBoolean;
/* 30 */     this.__set |= 1;
/* 31 */     return this;
/*    */   }
/*    */ 
/*    */   public B cycleCount(int paramInt)
/*    */   {
/* 40 */     this.cycleCount = paramInt;
/* 41 */     this.__set |= 2;
/* 42 */     return this;
/*    */   }
/*    */ 
/*    */   public B delay(Duration paramDuration)
/*    */   {
/* 51 */     this.delay = paramDuration;
/* 52 */     this.__set |= 4;
/* 53 */     return this;
/*    */   }
/*    */ 
/*    */   public B onFinished(EventHandler<ActionEvent> paramEventHandler)
/*    */   {
/* 62 */     this.onFinished = paramEventHandler;
/* 63 */     this.__set |= 8;
/* 64 */     return this;
/*    */   }
/*    */ 
/*    */   public B rate(double paramDouble)
/*    */   {
/* 73 */     this.rate = paramDouble;
/* 74 */     this.__set |= 16;
/* 75 */     return this;
/*    */   }
/*    */ 
/*    */   public B targetFramerate(double paramDouble)
/*    */   {
/* 84 */     this.targetFramerate = paramDouble;
/* 85 */     return this;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.animation.AnimationBuilder
 * JD-Core Version:    0.6.2
 */