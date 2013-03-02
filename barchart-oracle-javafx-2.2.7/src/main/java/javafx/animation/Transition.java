/*     */ package javafx.animation;
/*     */ 
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.SimpleObjectProperty;
/*     */ import javafx.scene.Node;
/*     */ 
/*     */ public abstract class Transition extends Animation
/*     */ {
/*     */   private ObjectProperty<Interpolator> interpolator;
/*  95 */   private static final Interpolator DEFAULT_INTERPOLATOR = Interpolator.EASE_BOTH;
/*     */   private Interpolator cachedInterpolator;
/* 141 */   Transition parent = null;
/*     */ 
/*     */   public final void setInterpolator(Interpolator paramInterpolator)
/*     */   {
/*  98 */     if ((this.interpolator != null) || (!DEFAULT_INTERPOLATOR.equals(paramInterpolator)))
/*  99 */       interpolatorProperty().set(paramInterpolator);
/*     */   }
/*     */ 
/*     */   public final Interpolator getInterpolator()
/*     */   {
/* 104 */     return this.interpolator == null ? DEFAULT_INTERPOLATOR : (Interpolator)this.interpolator.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<Interpolator> interpolatorProperty() {
/* 108 */     if (this.interpolator == null) {
/* 109 */       this.interpolator = new SimpleObjectProperty(this, "interpolator", DEFAULT_INTERPOLATOR);
/*     */     }
/*     */ 
/* 113 */     return this.interpolator;
/*     */   }
/*     */ 
/*     */   protected Interpolator getCachedInterpolator()
/*     */   {
/* 131 */     return this.cachedInterpolator;
/*     */   }
/*     */ 
/*     */   public Transition(double paramDouble)
/*     */   {
/* 152 */     super(paramDouble);
/*     */   }
/*     */ 
/*     */   public Transition()
/*     */   {
/*     */   }
/*     */ 
/*     */   protected Node getParentTargetNode()
/*     */   {
/* 167 */     return this.parent != null ? this.parent.getParentTargetNode() : null;
/*     */   }
/*     */ 
/*     */   protected abstract void interpolate(double paramDouble);
/*     */ 
/*     */   private double calculateFraction(long paramLong1, long paramLong2)
/*     */   {
/* 190 */     double d = paramLong1 / paramLong2;
/* 191 */     return this.cachedInterpolator.interpolate(0.0D, 1.0D, d);
/*     */   }
/*     */ 
/*     */   boolean impl_startable(boolean paramBoolean)
/*     */   {
/* 196 */     return (super.impl_startable(paramBoolean)) && ((getInterpolator() != null) || ((!paramBoolean) && (this.cachedInterpolator != null)));
/*     */   }
/*     */ 
/*     */   void impl_sync(boolean paramBoolean)
/*     */   {
/* 202 */     super.impl_sync(paramBoolean);
/* 203 */     if ((paramBoolean) || (this.cachedInterpolator == null))
/* 204 */       this.cachedInterpolator = getInterpolator();
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void impl_playTo(long paramLong1, long paramLong2)
/*     */   {
/* 216 */     impl_setCurrentTicks(paramLong1);
/* 217 */     interpolate(calculateFraction(paramLong1, paramLong2));
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void impl_jumpTo(long paramLong1, long paramLong2)
/*     */   {
/* 228 */     if (getStatus() != Animation.Status.STOPPED)
/* 229 */       interpolate(calculateFraction(paramLong1, paramLong2));
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.animation.Transition
 * JD-Core Version:    0.6.2
 */