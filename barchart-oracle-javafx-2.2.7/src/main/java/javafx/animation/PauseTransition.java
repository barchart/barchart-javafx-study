/*     */ package javafx.animation;
/*     */ 
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.ObjectPropertyBase;
/*     */ import javafx.util.Duration;
/*     */ 
/*     */ public final class PauseTransition extends Transition
/*     */ {
/*     */   private ObjectProperty<Duration> duration;
/*  89 */   private static final Duration DEFAULT_DURATION = Duration.millis(400.0D);
/*     */ 
/*     */   public final void setDuration(Duration paramDuration) {
/*  92 */     if ((this.duration != null) || (!DEFAULT_DURATION.equals(paramDuration)))
/*  93 */       durationProperty().set(paramDuration);
/*     */   }
/*     */ 
/*     */   public final Duration getDuration()
/*     */   {
/*  98 */     return this.duration == null ? DEFAULT_DURATION : (Duration)this.duration.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<Duration> durationProperty() {
/* 102 */     if (this.duration == null) {
/* 103 */       this.duration = new ObjectPropertyBase(DEFAULT_DURATION)
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 107 */           PauseTransition.this.setCycleDuration(PauseTransition.this.getDuration());
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 112 */           return PauseTransition.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 117 */           return "duration";
/*     */         }
/*     */       };
/*     */     }
/* 121 */     return this.duration;
/*     */   }
/*     */ 
/*     */   public PauseTransition(Duration paramDuration)
/*     */   {
/* 131 */     setDuration(paramDuration);
/* 132 */     setCycleDuration(paramDuration);
/*     */   }
/*     */ 
/*     */   public PauseTransition()
/*     */   {
/* 139 */     this(DEFAULT_DURATION);
/*     */   }
/*     */ 
/*     */   public void interpolate(double paramDouble)
/*     */   {
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.animation.PauseTransition
 * JD-Core Version:    0.6.2
 */