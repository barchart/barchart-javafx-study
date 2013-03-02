/*     */ package javafx.animation;
/*     */ 
/*     */ import com.sun.scenario.ToolkitAccessor;
/*     */ import com.sun.scenario.animation.AbstractMasterTimer;
/*     */ 
/*     */ public abstract class AnimationTimer
/*     */ {
/*     */   private final AbstractMasterTimer timer;
/*     */ 
/*     */   public AnimationTimer()
/*     */   {
/*  68 */     this.timer = ToolkitAccessor.getMasterTimer();
/*     */   }
/*     */ 
/*     */   AnimationTimer(AbstractMasterTimer paramAbstractMasterTimer)
/*     */   {
/*  73 */     this.timer = paramAbstractMasterTimer;
/*     */   }
/*     */ 
/*     */   public abstract void handle(long paramLong);
/*     */ 
/*     */   public void start()
/*     */   {
/*  95 */     this.timer.addAnimationTimer(this);
/*     */   }
/*     */ 
/*     */   public void stop()
/*     */   {
/* 103 */     this.timer.removeAnimationTimer(this);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.animation.AnimationTimer
 * JD-Core Version:    0.6.2
 */