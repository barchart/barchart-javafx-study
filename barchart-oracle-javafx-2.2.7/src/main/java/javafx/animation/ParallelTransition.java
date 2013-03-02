/*     */ package javafx.animation;
/*     */ 
/*     */ import com.sun.javafx.animation.TickCalculation;
/*     */ import com.sun.javafx.collections.TrackableObservableList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javafx.beans.InvalidationListener;
/*     */ import javafx.beans.Observable;
/*     */ import javafx.beans.property.DoubleProperty;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.ReadOnlyObjectProperty;
/*     */ import javafx.beans.property.SimpleObjectProperty;
/*     */ import javafx.collections.ListChangeListener.Change;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.event.ActionEvent;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.scene.Node;
/*     */ import javafx.util.Duration;
/*     */ 
/*     */ public final class ParallelTransition extends Transition
/*     */ {
/*  94 */   private static final Animation[] EMPTY_ANIMATION_ARRAY = new Animation[0];
/*     */   private static final double EPSILON = 1.0E-12D;
/*  97 */   private Animation[] cachedChildren = EMPTY_ANIMATION_ARRAY;
/*     */   private long[] durations;
/*     */   private long[] delays;
/*     */   private double[] rates;
/*     */   private long[] offsetTicks;
/*     */   private boolean[] forceChildSync;
/*     */   private long oldTicks;
/* 104 */   private boolean childrenChanged = true;
/*     */ 
/* 106 */   private final InvalidationListener childrenListener = new InvalidationListener()
/*     */   {
/*     */     public void invalidated(Observable paramAnonymousObservable) {
/* 109 */       ParallelTransition.this.childrenChanged = true;
/* 110 */       if (ParallelTransition.this.getStatus() == Animation.Status.STOPPED)
/* 111 */         ParallelTransition.this.setCycleDuration(ParallelTransition.this.computeCycleDuration());
/*     */     }
/* 106 */   };
/*     */   private ObjectProperty<Node> node;
/* 128 */   private static final Node DEFAULT_NODE = null;
/*     */ 
/* 147 */   private final ObservableList<Animation> children = new TrackableObservableList()
/*     */   {
/*     */     protected void onChanged(ListChangeListener.Change<Animation> paramAnonymousChange)
/*     */     {
/*     */       Iterator localIterator;
/*     */       Animation localAnimation;
/* 150 */       while (paramAnonymousChange.next()) {
/* 151 */         for (localIterator = paramAnonymousChange.getRemoved().iterator(); localIterator.hasNext(); ) { localAnimation = (Animation)localIterator.next();
/* 152 */           if ((localAnimation instanceof Transition)) {
/* 153 */             Transition localTransition = (Transition)localAnimation;
/* 154 */             if (localTransition.parent == ParallelTransition.this) {
/* 155 */               localTransition.parent = null;
/*     */             }
/*     */           }
/* 158 */           localAnimation.rateProperty().removeListener(ParallelTransition.this.childrenListener);
/* 159 */           localAnimation.totalDurationProperty().removeListener(ParallelTransition.this.childrenListener);
/* 160 */           localAnimation.delayProperty().removeListener(ParallelTransition.this.childrenListener);
/*     */         }
/* 162 */         for (localIterator = paramAnonymousChange.getAddedSubList().iterator(); localIterator.hasNext(); ) { localAnimation = (Animation)localIterator.next();
/* 163 */           if ((localAnimation instanceof Transition)) {
/* 164 */             ((Transition)localAnimation).parent = ParallelTransition.this;
/*     */           }
/* 166 */           localAnimation.rateProperty().addListener(ParallelTransition.this.childrenListener);
/* 167 */           localAnimation.totalDurationProperty().addListener(ParallelTransition.this.childrenListener);
/* 168 */           localAnimation.delayProperty().addListener(ParallelTransition.this.childrenListener);
/*     */         }
/*     */       }
/* 171 */       ParallelTransition.this.childrenListener.invalidated(ParallelTransition.this.children);
/*     */     }
/* 147 */   };
/*     */ 
/*     */   public final void setNode(Node paramNode)
/*     */   {
/* 131 */     if ((this.node != null) || (paramNode != null))
/* 132 */       nodeProperty().set(paramNode);
/*     */   }
/*     */ 
/*     */   public final Node getNode()
/*     */   {
/* 137 */     return this.node == null ? DEFAULT_NODE : (Node)this.node.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<Node> nodeProperty() {
/* 141 */     if (this.node == null) {
/* 142 */       this.node = new SimpleObjectProperty(this, "node", DEFAULT_NODE);
/*     */     }
/* 144 */     return this.node;
/*     */   }
/*     */ 
/*     */   public final ObservableList<Animation> getChildren()
/*     */   {
/* 187 */     return this.children;
/*     */   }
/*     */ 
/*     */   public ParallelTransition(Node paramNode, Animation[] paramArrayOfAnimation)
/*     */   {
/* 202 */     setInterpolator(Interpolator.LINEAR);
/* 203 */     setNode(paramNode);
/* 204 */     getChildren().setAll(paramArrayOfAnimation);
/*     */   }
/*     */ 
/*     */   public ParallelTransition(Animation[] paramArrayOfAnimation)
/*     */   {
/* 215 */     this(null, paramArrayOfAnimation);
/*     */   }
/*     */ 
/*     */   public ParallelTransition(Node paramNode)
/*     */   {
/* 227 */     setInterpolator(Interpolator.LINEAR);
/* 228 */     setNode(paramNode);
/*     */   }
/*     */ 
/*     */   public ParallelTransition()
/*     */   {
/* 235 */     this((Node)null);
/*     */   }
/*     */ 
/*     */   protected Node getParentTargetNode()
/*     */   {
/* 243 */     Node localNode = getNode();
/* 244 */     return this.parent != null ? this.parent.getParentTargetNode() : localNode != null ? localNode : null;
/*     */   }
/*     */ 
/*     */   private Duration computeCycleDuration()
/*     */   {
/* 249 */     Object localObject = Duration.ZERO;
/* 250 */     for (Animation localAnimation : getChildren()) {
/* 251 */       double d = Math.abs(localAnimation.getRate());
/* 252 */       Duration localDuration1 = d < 1.0E-12D ? localAnimation.getTotalDuration() : localAnimation.getTotalDuration().divide(d);
/*     */ 
/* 254 */       Duration localDuration2 = localDuration1.add(localAnimation.getDelay());
/* 255 */       if (localDuration2.isIndefinite()) {
/* 256 */         return Duration.INDEFINITE;
/*     */       }
/* 258 */       if (localDuration2.greaterThan((Duration)localObject)) {
/* 259 */         localObject = localDuration2;
/*     */       }
/*     */     }
/*     */ 
/* 263 */     return localObject;
/*     */   }
/*     */ 
/*     */   private double calculateFraction(long paramLong1, long paramLong2) {
/* 267 */     double d = paramLong1 / paramLong2;
/* 268 */     return d >= 1.0D ? 1.0D : d <= 0.0D ? 0.0D : d;
/*     */   }
/*     */ 
/*     */   private boolean startChild(Animation paramAnimation, int paramInt) {
/* 272 */     int i = this.forceChildSync[paramInt];
/* 273 */     if (paramAnimation.impl_startable(i)) {
/* 274 */       paramAnimation.setRate(this.rates[paramInt] * Math.signum(getCurrentRate()));
/* 275 */       paramAnimation.impl_start(i);
/* 276 */       this.forceChildSync[paramInt] = false;
/* 277 */       return true;
/*     */     }
/* 279 */     return false;
/*     */   }
/*     */ 
/*     */   void impl_sync(boolean paramBoolean)
/*     */   {
/* 284 */     super.impl_sync(paramBoolean);
/*     */     int i;
/*     */     int j;
/* 285 */     if (((paramBoolean) && (this.childrenChanged)) || (this.durations == null)) {
/* 286 */       this.cachedChildren = ((Animation[])getChildren().toArray(EMPTY_ANIMATION_ARRAY));
/* 287 */       i = this.cachedChildren.length;
/* 288 */       this.durations = new long[i];
/* 289 */       this.delays = new long[i];
/* 290 */       this.rates = new double[i];
/* 291 */       this.offsetTicks = new long[i];
/* 292 */       this.forceChildSync = new boolean[i];
/* 293 */       j = 0;
/* 294 */       for (Animation localAnimation : this.cachedChildren) {
/* 295 */         this.rates[j] = localAnimation.getRate();
/* 296 */         if (this.rates[j] < 1.0E-12D) {
/* 297 */           this.rates[j] = 1.0D;
/*     */         }
/* 299 */         this.durations[j] = TickCalculation.fromDuration(localAnimation.getTotalDuration(), this.rates[j]);
/* 300 */         this.delays[j] = TickCalculation.fromDuration(localAnimation.getDelay());
/* 301 */         this.forceChildSync[j] = true;
/* 302 */         j++;
/*     */       }
/* 304 */       this.childrenChanged = false;
/* 305 */     } else if (paramBoolean) {
/* 306 */       i = this.forceChildSync.length;
/* 307 */       for (j = 0; j < i; j++)
/* 308 */         this.forceChildSync[j] = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   void impl_pause()
/*     */   {
/* 315 */     super.impl_pause();
/* 316 */     for (Animation localAnimation : this.cachedChildren)
/* 317 */       if (localAnimation.getStatus() == Animation.Status.RUNNING)
/* 318 */         localAnimation.impl_pause();
/*     */   }
/*     */ 
/*     */   void impl_resume()
/*     */   {
/* 325 */     super.impl_resume();
/* 326 */     for (Animation localAnimation : this.cachedChildren)
/* 327 */       if (localAnimation.getStatus() == Animation.Status.PAUSED)
/* 328 */         localAnimation.impl_resume();
/*     */   }
/*     */ 
/*     */   void impl_stop()
/*     */   {
/* 335 */     super.impl_stop();
/* 336 */     for (Animation localAnimation : this.cachedChildren) {
/* 337 */       if (localAnimation.getStatus() != Animation.Status.STOPPED) {
/* 338 */         localAnimation.impl_stop();
/*     */       }
/*     */     }
/* 341 */     if (this.childrenChanged)
/* 342 */       setCycleDuration(computeCycleDuration());
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void impl_playTo(long paramLong1, long paramLong2)
/*     */   {
/* 352 */     impl_setCurrentTicks(paramLong1);
/* 353 */     double d = calculateFraction(paramLong1, paramLong2);
/* 354 */     long l = Math.max(0L, Math.min(getCachedInterpolator().interpolate(0L, paramLong2, d), paramLong2));
/*     */     int i;
/*     */     Animation localAnimation;
/*     */     int m;
/*     */     EventHandler localEventHandler;
/* 355 */     if (getCurrentRate() > 0.0D) {
/* 356 */       i = 0;
/* 357 */       for (localAnimation : this.cachedChildren) {
/* 358 */         if ((l >= this.delays[i]) && ((this.oldTicks <= this.delays[i]) || ((this.oldTicks < TickCalculation.add(this.delays[i], this.durations[i])) && (localAnimation.getStatus() == Animation.Status.STOPPED)))) {
/* 359 */           m = this.oldTicks <= this.delays[i] ? 1 : 0;
/* 360 */           if (m != 0) {
/* 361 */             localAnimation.jumpTo(Duration.ZERO);
/*     */           }
/* 363 */           if (!startChild(localAnimation, i)) {
/* 364 */             if (m == 0) continue;
/* 365 */             localEventHandler = localAnimation.getOnFinished();
/* 366 */             if (localEventHandler != null) {
/* 367 */               localEventHandler.handle(new ActionEvent(this, null));
/*     */             }
/* 369 */             continue;
/*     */           }
/*     */         }
/*     */ 
/* 373 */         if (l >= TickCalculation.add(this.durations[i], this.delays[i])) {
/* 374 */           if (localAnimation.getStatus() == Animation.Status.RUNNING) {
/* 375 */             localAnimation.impl_timePulse(calcTimePulse(this.durations[i], i));
/* 376 */             this.offsetTicks[i] = 0L;
/*     */           }
/* 378 */         } else if (l > this.delays[i]) {
/* 379 */           localAnimation.impl_timePulse(calcTimePulse(TickCalculation.sub(l, this.delays[i]), i));
/*     */         }
/* 381 */         i++;
/*     */       }
/*     */     } else {
/* 384 */       i = 0;
/* 385 */       for (localAnimation : this.cachedChildren) {
/* 386 */         if (l < TickCalculation.add(this.durations[i], this.delays[i])) {
/* 387 */           if ((this.oldTicks >= TickCalculation.add(this.durations[i], this.delays[i])) || ((this.oldTicks >= this.delays[i]) && (localAnimation.getStatus() == Animation.Status.STOPPED))) {
/* 388 */             m = this.oldTicks >= TickCalculation.add(this.durations[i], this.delays[i]) ? 1 : 0;
/* 389 */             if (m != 0) {
/* 390 */               localAnimation.jumpTo(TickCalculation.toDuration(this.durations[i], this.rates[i]));
/*     */             }
/* 392 */             if (!startChild(localAnimation, i)) {
/* 393 */               if (m == 0) continue;
/* 394 */               localEventHandler = localAnimation.getOnFinished();
/* 395 */               if (localEventHandler != null) {
/* 396 */                 localEventHandler.handle(new ActionEvent(this, null));
/*     */               }
/* 398 */               continue;
/*     */             }
/*     */           }
/*     */ 
/* 402 */           if (l <= this.delays[i]) {
/* 403 */             if (localAnimation.getStatus() == Animation.Status.RUNNING) {
/* 404 */               localAnimation.impl_timePulse(calcTimePulse(this.durations[i], i));
/* 405 */               this.offsetTicks[i] = 0L;
/*     */             }
/*     */           }
/* 408 */           else localAnimation.impl_timePulse(calcTimePulse(TickCalculation.sub(TickCalculation.add(this.durations[i], this.delays[i]), l), i));
/*     */         }
/*     */ 
/* 411 */         i++;
/*     */       }
/*     */     }
/* 414 */     this.oldTicks = l;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void impl_jumpTo(long paramLong1, long paramLong2)
/*     */   {
/* 423 */     impl_sync(false);
/* 424 */     double d = calculateFraction(paramLong1, paramLong2);
/* 425 */     long l = Math.max(0L, Math.min(getCachedInterpolator().interpolate(0L, paramLong2, d), paramLong2));
/* 426 */     int i = 0;
/* 427 */     for (Animation localAnimation : this.cachedChildren) {
/* 428 */       if (l <= this.delays[i]) {
/* 429 */         this.offsetTicks[i] = 0L;
/* 430 */         localAnimation.jumpTo(Duration.ZERO);
/* 431 */         if ((localAnimation.getStatus() != Animation.Status.STOPPED) && (getCurrentRate() < 0.0D))
/* 432 */           localAnimation.impl_finished();
/*     */       }
/* 434 */       else if (l >= TickCalculation.add(this.durations[i], this.delays[i])) {
/* 435 */         this.offsetTicks[i] = 0L;
/* 436 */         localAnimation.jumpTo(TickCalculation.toDuration(this.durations[i], this.rates[i]));
/* 437 */         if ((localAnimation.getStatus() != Animation.Status.STOPPED) && (getCurrentRate() > 0.0D)) {
/* 438 */           localAnimation.impl_finished();
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 443 */         this.offsetTicks[i] = (getCurrentRate() < 0.0D ? TickCalculation.sub(TickCalculation.add(this.durations[i], this.delays[i]), l) : TickCalculation.sub(l, this.delays[i]));
/* 444 */         localAnimation.jumpTo(TickCalculation.toDuration(TickCalculation.sub(l, this.delays[i]), this.rates[i]));
/*     */       }
/* 446 */       i++;
/*     */     }
/* 448 */     this.oldTicks = l;
/*     */   }
/*     */ 
/*     */   protected void interpolate(double paramDouble)
/*     */   {
/*     */   }
/*     */ 
/*     */   private long calcTimePulse(long paramLong, int paramInt)
/*     */   {
/* 460 */     return TickCalculation.sub(Math.round(paramLong * Math.abs(this.rates[paramInt])), this.offsetTicks[paramInt]);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.animation.ParallelTransition
 * JD-Core Version:    0.6.2
 */