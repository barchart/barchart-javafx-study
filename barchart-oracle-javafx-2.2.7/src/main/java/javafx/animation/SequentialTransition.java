/*     */ package javafx.animation;
/*     */ 
/*     */ import com.sun.javafx.animation.TickCalculation;
/*     */ import com.sun.javafx.collections.TrackableObservableList;
/*     */ import java.util.Arrays;
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
/*     */ public final class SequentialTransition extends Transition
/*     */ {
/*  97 */   private static final Animation[] EMPTY_ANIMATION_ARRAY = new Animation[0];
/*     */   private static final int BEFORE = -1;
/*     */   private static final double EPSILON = 1.0E-12D;
/* 101 */   private Animation[] cachedChildren = EMPTY_ANIMATION_ARRAY;
/*     */   private long[] startTimes;
/*     */   private long[] durations;
/*     */   private long[] delays;
/*     */   private double[] rates;
/*     */   private boolean[] forceChildSync;
/*     */   private int end;
/*     */   private int curIndex;
/* 109 */   private long oldTicks = -1L;
/*     */   private long offsetTicks;
/* 111 */   private boolean childrenChanged = true;
/*     */ 
/* 113 */   private final InvalidationListener childrenListener = new InvalidationListener()
/*     */   {
/*     */     public void invalidated(Observable paramAnonymousObservable) {
/* 116 */       SequentialTransition.this.childrenChanged = true;
/* 117 */       if (SequentialTransition.this.getStatus() == Animation.Status.STOPPED)
/* 118 */         SequentialTransition.this.setCycleDuration(SequentialTransition.this.computeCycleDuration());
/*     */     }
/* 113 */   };
/*     */   private ObjectProperty<Node> node;
/* 135 */   private static final Node DEFAULT_NODE = null;
/*     */ 
/* 154 */   private final ObservableList<Animation> children = new TrackableObservableList()
/*     */   {
/*     */     protected void onChanged(ListChangeListener.Change<Animation> paramAnonymousChange)
/*     */     {
/*     */       Iterator localIterator;
/*     */       Animation localAnimation;
/* 157 */       while (paramAnonymousChange.next()) {
/* 158 */         for (localIterator = paramAnonymousChange.getRemoved().iterator(); localIterator.hasNext(); ) { localAnimation = (Animation)localIterator.next();
/* 159 */           if ((localAnimation instanceof Transition)) {
/* 160 */             Transition localTransition = (Transition)localAnimation;
/* 161 */             if (localTransition.parent == SequentialTransition.this) {
/* 162 */               localTransition.parent = null;
/*     */             }
/*     */           }
/* 165 */           localAnimation.rateProperty().removeListener(SequentialTransition.this.childrenListener);
/* 166 */           localAnimation.totalDurationProperty().removeListener(SequentialTransition.this.childrenListener);
/* 167 */           localAnimation.delayProperty().removeListener(SequentialTransition.this.childrenListener);
/*     */         }
/* 169 */         for (localIterator = paramAnonymousChange.getAddedSubList().iterator(); localIterator.hasNext(); ) { localAnimation = (Animation)localIterator.next();
/* 170 */           if ((localAnimation instanceof Transition)) {
/* 171 */             ((Transition)localAnimation).parent = SequentialTransition.this;
/*     */           }
/* 173 */           localAnimation.rateProperty().addListener(SequentialTransition.this.childrenListener);
/* 174 */           localAnimation.totalDurationProperty().addListener(SequentialTransition.this.childrenListener);
/* 175 */           localAnimation.delayProperty().addListener(SequentialTransition.this.childrenListener);
/*     */         }
/*     */       }
/* 178 */       SequentialTransition.this.childrenListener.invalidated(SequentialTransition.this.children);
/*     */     }
/* 154 */   };
/*     */ 
/*     */   public final void setNode(Node paramNode)
/*     */   {
/* 138 */     if ((this.node != null) || (paramNode != null))
/* 139 */       nodeProperty().set(paramNode);
/*     */   }
/*     */ 
/*     */   public final Node getNode()
/*     */   {
/* 144 */     return this.node == null ? DEFAULT_NODE : (Node)this.node.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<Node> nodeProperty() {
/* 148 */     if (this.node == null) {
/* 149 */       this.node = new SimpleObjectProperty(this, "node", DEFAULT_NODE);
/*     */     }
/* 151 */     return this.node;
/*     */   }
/*     */ 
/*     */   public final ObservableList<Animation> getChildren()
/*     */   {
/* 192 */     return this.children;
/*     */   }
/*     */ 
/*     */   public SequentialTransition(Node paramNode, Animation[] paramArrayOfAnimation)
/*     */   {
/* 207 */     setInterpolator(Interpolator.LINEAR);
/* 208 */     setNode(paramNode);
/* 209 */     getChildren().setAll(paramArrayOfAnimation);
/*     */   }
/*     */ 
/*     */   public SequentialTransition(Animation[] paramArrayOfAnimation)
/*     */   {
/* 220 */     this(null, paramArrayOfAnimation);
/*     */   }
/*     */ 
/*     */   public SequentialTransition(Node paramNode)
/*     */   {
/* 232 */     setInterpolator(Interpolator.LINEAR);
/* 233 */     setNode(paramNode);
/*     */   }
/*     */ 
/*     */   public SequentialTransition()
/*     */   {
/* 240 */     this((Node)null);
/*     */   }
/*     */ 
/*     */   protected Node getParentTargetNode()
/*     */   {
/* 248 */     Node localNode = getNode();
/* 249 */     return this.parent != null ? this.parent.getParentTargetNode() : localNode != null ? localNode : null;
/*     */   }
/*     */ 
/*     */   private Duration computeCycleDuration() {
/* 253 */     Duration localDuration = Duration.ZERO;
/*     */ 
/* 255 */     for (Animation localAnimation : getChildren()) {
/* 256 */       localDuration = localDuration.add(localAnimation.getDelay());
/* 257 */       double d = Math.abs(localAnimation.getRate());
/* 258 */       localDuration = localDuration.add(d < 1.0E-12D ? localAnimation.getTotalDuration() : localAnimation.getTotalDuration().divide(d));
/*     */ 
/* 260 */       if (localDuration.isIndefinite()) {
/*     */         break;
/*     */       }
/*     */     }
/* 264 */     return localDuration;
/*     */   }
/*     */ 
/*     */   private double calculateFraction(long paramLong1, long paramLong2) {
/* 268 */     double d = paramLong1 / paramLong2;
/* 269 */     return d >= 1.0D ? 1.0D : d <= 0.0D ? 0.0D : d;
/*     */   }
/*     */ 
/*     */   private int findNewIndex(long paramLong) {
/* 273 */     if ((this.curIndex != -1) && (this.curIndex != this.end) && (this.startTimes[this.curIndex] <= paramLong) && (paramLong <= this.startTimes[(this.curIndex + 1)]))
/*     */     {
/* 277 */       return this.curIndex;
/*     */     }
/*     */ 
/* 280 */     int i = (this.curIndex == -1) || (this.curIndex == this.end) ? 1 : 0;
/* 281 */     int j = (i != 0) || (paramLong < this.oldTicks) ? 0 : this.curIndex + 1;
/* 282 */     int k = (i != 0) || (this.oldTicks < paramLong) ? this.end : this.curIndex;
/* 283 */     int m = Arrays.binarySearch(this.startTimes, j, k, paramLong);
/* 284 */     return m > 0 ? m - 1 : m < 0 ? -m - 2 : 0;
/*     */   }
/*     */ 
/*     */   void impl_sync(boolean paramBoolean)
/*     */   {
/* 289 */     super.impl_sync(paramBoolean);
/*     */ 
/* 291 */     if (((paramBoolean) && (this.childrenChanged)) || (this.startTimes == null)) {
/* 292 */       this.cachedChildren = ((Animation[])getChildren().toArray(EMPTY_ANIMATION_ARRAY));
/* 293 */       this.end = this.cachedChildren.length;
/* 294 */       this.startTimes = new long[this.end + 1];
/* 295 */       this.durations = new long[this.end];
/* 296 */       this.delays = new long[this.end];
/* 297 */       this.rates = new double[this.end];
/* 298 */       this.forceChildSync = new boolean[this.end];
/* 299 */       long l = 0L;
/* 300 */       int k = 0;
/* 301 */       for (Animation localAnimation : this.cachedChildren) {
/* 302 */         this.startTimes[k] = l;
/* 303 */         this.rates[k] = localAnimation.getRate();
/* 304 */         if (this.rates[k] < 1.0E-12D) {
/* 305 */           this.rates[k] = 1.0D;
/*     */         }
/* 307 */         this.durations[k] = TickCalculation.fromDuration(localAnimation.getTotalDuration(), this.rates[k]);
/* 308 */         this.delays[k] = TickCalculation.fromDuration(localAnimation.getDelay());
/* 309 */         if ((this.durations[k] == 9223372036854775807L) || (this.delays[k] == 9223372036854775807L) || (l == 9223372036854775807L))
/* 310 */           l = 9223372036854775807L;
/*     */         else {
/* 312 */           l = TickCalculation.add(l, TickCalculation.add(this.durations[k], this.delays[k]));
/*     */         }
/* 314 */         this.forceChildSync[k] = true;
/* 315 */         k++;
/*     */       }
/* 317 */       this.startTimes[this.end] = l;
/* 318 */       this.childrenChanged = false;
/* 319 */     } else if (paramBoolean) {
/* 320 */       int i = this.forceChildSync.length;
/* 321 */       for (int j = 0; j < i; j++)
/* 322 */         this.forceChildSync[j] = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   void impl_start(boolean paramBoolean)
/*     */   {
/* 329 */     super.impl_start(paramBoolean);
/* 330 */     this.curIndex = (getCurrentRate() > 0.0D ? -1 : this.end);
/* 331 */     this.offsetTicks = 0L;
/*     */   }
/*     */ 
/*     */   void impl_pause()
/*     */   {
/* 336 */     super.impl_pause();
/* 337 */     if ((this.curIndex != -1) && (this.curIndex != this.end)) {
/* 338 */       Animation localAnimation = this.cachedChildren[this.curIndex];
/* 339 */       if (localAnimation.getStatus() == Animation.Status.RUNNING)
/* 340 */         localAnimation.impl_pause();
/*     */     }
/*     */   }
/*     */ 
/*     */   void impl_resume()
/*     */   {
/* 347 */     super.impl_resume();
/* 348 */     if ((this.curIndex != -1) && (this.curIndex != this.end)) {
/* 349 */       Animation localAnimation = this.cachedChildren[this.curIndex];
/* 350 */       if (localAnimation.getStatus() == Animation.Status.PAUSED)
/* 351 */         localAnimation.impl_resume();
/*     */     }
/*     */   }
/*     */ 
/*     */   void impl_stop()
/*     */   {
/* 358 */     super.impl_stop();
/* 359 */     if ((this.curIndex != -1) && (this.curIndex != this.end)) {
/* 360 */       Animation localAnimation = this.cachedChildren[this.curIndex];
/* 361 */       if (localAnimation.getStatus() != Animation.Status.STOPPED) {
/* 362 */         localAnimation.impl_stop();
/*     */       }
/*     */     }
/* 365 */     if (this.childrenChanged)
/* 366 */       setCycleDuration(computeCycleDuration());
/*     */   }
/*     */ 
/*     */   private boolean startChild(Animation paramAnimation, int paramInt)
/*     */   {
/* 371 */     int i = this.forceChildSync[paramInt];
/* 372 */     if (paramAnimation.impl_startable(i)) {
/* 373 */       paramAnimation.setRate(this.rates[paramInt] * Math.signum(getCurrentRate()));
/* 374 */       paramAnimation.impl_start(i);
/* 375 */       this.forceChildSync[paramInt] = false;
/* 376 */       return true;
/*     */     }
/* 378 */     return false;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void impl_playTo(long paramLong1, long paramLong2)
/*     */   {
/* 387 */     impl_setCurrentTicks(paramLong1);
/* 388 */     double d = calculateFraction(paramLong1, paramLong2);
/* 389 */     long l1 = Math.max(0L, Math.min(getCachedInterpolator().interpolate(0L, paramLong2, d), paramLong2));
/* 390 */     int i = findNewIndex(l1);
/* 391 */     Animation localAnimation1 = (this.curIndex == -1) || (this.curIndex == this.end) ? null : this.cachedChildren[this.curIndex];
/*     */     long l2;
/*     */     EventHandler localEventHandler4;
/* 392 */     if (this.curIndex == i) {
/* 393 */       if (getCurrentRate() > 0.0D) {
/* 394 */         l2 = TickCalculation.add(this.startTimes[this.curIndex], this.delays[this.curIndex]);
/* 395 */         if (l1 >= l2) {
/* 396 */           if ((this.oldTicks <= l2) || (localAnimation1.getStatus() == Animation.Status.STOPPED)) {
/* 397 */             int j = this.oldTicks <= l2 ? 1 : 0;
/* 398 */             if (j != 0) {
/* 399 */               localAnimation1.jumpTo(Duration.ZERO);
/*     */             }
/* 401 */             if (!startChild(localAnimation1, this.curIndex)) {
/* 402 */               if (j != 0) {
/* 403 */                 localEventHandler4 = localAnimation1.getOnFinished();
/* 404 */                 if (localEventHandler4 != null) {
/* 405 */                   localEventHandler4.handle(new ActionEvent(this, null));
/*     */                 }
/*     */               }
/* 408 */               this.oldTicks = l1;
/* 409 */               return;
/*     */             }
/*     */           }
/* 412 */           long l6 = TickCalculation.sub(l1, l2);
/* 413 */           if (l1 >= this.startTimes[(this.curIndex + 1)]) {
/* 414 */             localAnimation1.impl_timePulse(9223372036854775807L);
/* 415 */             if (l1 == paramLong2)
/* 416 */               this.curIndex = this.end;
/*     */           }
/*     */           else {
/* 419 */             localAnimation1.impl_timePulse(calcTimePulse(l6));
/*     */           }
/*     */         }
/*     */       } else {
/* 423 */         l2 = TickCalculation.add(this.startTimes[this.curIndex], this.delays[this.curIndex]);
/* 424 */         if ((this.oldTicks >= this.startTimes[(this.curIndex + 1)]) || ((this.oldTicks >= l2) && (localAnimation1.getStatus() == Animation.Status.STOPPED))) {
/* 425 */           int k = this.oldTicks >= this.startTimes[(this.curIndex + 1)] ? 1 : 0;
/* 426 */           if (k != 0) {
/* 427 */             localAnimation1.jumpTo("end");
/*     */           }
/* 429 */           if (!startChild(localAnimation1, this.curIndex)) {
/* 430 */             if (k != 0) {
/* 431 */               localEventHandler4 = localAnimation1.getOnFinished();
/* 432 */               if (localEventHandler4 != null) {
/* 433 */                 localEventHandler4.handle(new ActionEvent(this, null));
/*     */               }
/*     */             }
/* 436 */             this.oldTicks = l1;
/* 437 */             return;
/*     */           }
/*     */         }
/* 440 */         if (l1 <= l2) {
/* 441 */           localAnimation1.impl_timePulse(9223372036854775807L);
/* 442 */           if (l1 == 0L)
/* 443 */             this.curIndex = -1;
/*     */         }
/*     */         else {
/* 446 */           long l7 = TickCalculation.sub(this.startTimes[(this.curIndex + 1)], l1);
/* 447 */           localAnimation1.impl_timePulse(calcTimePulse(l7));
/*     */         }
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/*     */       int m;
/*     */       EventHandler localEventHandler2;
/* 451 */       if (this.curIndex < i) {
/* 452 */         if (localAnimation1 != null) {
/* 453 */           l2 = TickCalculation.add(this.startTimes[this.curIndex], this.delays[this.curIndex]);
/* 454 */           if ((this.oldTicks <= l2) || ((localAnimation1.getStatus() == Animation.Status.STOPPED) && (this.oldTicks != this.startTimes[(this.curIndex + 1)]))) {
/* 455 */             m = this.oldTicks <= l2 ? 1 : 0;
/* 456 */             if (m != 0) {
/* 457 */               localAnimation1.jumpTo(Duration.ZERO);
/*     */             }
/* 459 */             if ((!startChild(localAnimation1, this.curIndex)) && 
/* 460 */               (m != 0)) {
/* 461 */               localEventHandler4 = localAnimation1.getOnFinished();
/* 462 */               if (localEventHandler4 != null) {
/* 463 */                 localEventHandler4.handle(new ActionEvent(this, null));
/*     */               }
/*     */             }
/*     */           }
/*     */ 
/* 468 */           if (localAnimation1.getStatus() == Animation.Status.RUNNING) {
/* 469 */             localAnimation1.impl_timePulse(9223372036854775807L);
/*     */           }
/* 471 */           this.oldTicks = this.startTimes[(this.curIndex + 1)];
/*     */         }
/* 473 */         this.offsetTicks = 0L;
/* 474 */         for (this.curIndex += 1; 
/* 475 */           this.curIndex < i; this.curIndex += 1) {
/* 476 */           localAnimation2 = this.cachedChildren[this.curIndex];
/* 477 */           localAnimation2.jumpTo(Duration.ZERO);
/* 478 */           if (startChild(localAnimation2, this.curIndex)) {
/* 479 */             localAnimation2.impl_timePulse(9223372036854775807L);
/*     */           } else {
/* 481 */             EventHandler localEventHandler1 = localAnimation2.getOnFinished();
/* 482 */             if (localEventHandler1 != null) {
/* 483 */               localEventHandler1.handle(new ActionEvent(this, null));
/*     */             }
/*     */           }
/* 486 */           this.oldTicks = this.startTimes[(this.curIndex + 1)];
/*     */         }
/* 488 */         Animation localAnimation2 = this.cachedChildren[this.curIndex];
/* 489 */         localAnimation2.jumpTo(Duration.ZERO);
/* 490 */         if (startChild(localAnimation2, this.curIndex)) {
/* 491 */           if (l1 >= this.startTimes[(this.curIndex + 1)]) {
/* 492 */             localAnimation2.impl_timePulse(9223372036854775807L);
/* 493 */             if (l1 == paramLong2)
/* 494 */               this.curIndex = this.end;
/*     */           }
/*     */           else {
/* 497 */             long l4 = TickCalculation.sub(l1, TickCalculation.add(this.startTimes[this.curIndex], this.delays[this.curIndex]));
/* 498 */             localAnimation2.impl_timePulse(calcTimePulse(l4));
/*     */           }
/*     */         } else {
/* 501 */           localEventHandler2 = localAnimation2.getOnFinished();
/* 502 */           if (localEventHandler2 != null)
/* 503 */             localEventHandler2.handle(new ActionEvent(this, null));
/*     */         }
/*     */       }
/*     */       else {
/* 507 */         if (localAnimation1 != null) {
/* 508 */           long l3 = TickCalculation.add(this.startTimes[this.curIndex], this.delays[this.curIndex]);
/* 509 */           if ((this.oldTicks >= this.startTimes[(this.curIndex + 1)]) || ((this.oldTicks > l3) && (localAnimation1.getStatus() == Animation.Status.STOPPED))) {
/* 510 */             m = this.oldTicks >= this.startTimes[(this.curIndex + 1)] ? 1 : 0;
/* 511 */             if (m != 0) {
/* 512 */               localAnimation1.jumpTo("end");
/*     */             }
/* 514 */             if ((!startChild(localAnimation1, this.curIndex)) && 
/* 515 */               (m != 0)) {
/* 516 */               localEventHandler4 = localAnimation1.getOnFinished();
/* 517 */               if (localEventHandler4 != null) {
/* 518 */                 localEventHandler4.handle(new ActionEvent(this, null));
/*     */               }
/*     */             }
/*     */           }
/*     */ 
/* 523 */           if (localAnimation1.getStatus() == Animation.Status.RUNNING) {
/* 524 */             localAnimation1.impl_timePulse(9223372036854775807L);
/*     */           }
/* 526 */           this.oldTicks = this.startTimes[this.curIndex];
/*     */         }
/* 528 */         this.offsetTicks = 0L;
/* 529 */         for (this.curIndex -= 1; 
/* 530 */           this.curIndex > i; this.curIndex -= 1) {
/* 531 */           localAnimation3 = this.cachedChildren[this.curIndex];
/* 532 */           localAnimation3.jumpTo(TickCalculation.toDuration(this.durations[this.curIndex], this.rates[this.curIndex]));
/* 533 */           if (startChild(localAnimation3, this.curIndex)) {
/* 534 */             localAnimation3.impl_timePulse(9223372036854775807L);
/*     */           } else {
/* 536 */             localEventHandler2 = localAnimation3.getOnFinished();
/* 537 */             if (localEventHandler2 != null) {
/* 538 */               localEventHandler2.handle(new ActionEvent(this, null));
/*     */             }
/*     */           }
/* 541 */           this.oldTicks = this.startTimes[this.curIndex];
/*     */         }
/* 543 */         Animation localAnimation3 = this.cachedChildren[this.curIndex];
/* 544 */         localAnimation3.jumpTo("end");
/* 545 */         if (startChild(localAnimation3, this.curIndex)) {
/* 546 */           if (l1 <= TickCalculation.add(this.startTimes[this.curIndex], this.delays[this.curIndex])) {
/* 547 */             localAnimation3.impl_timePulse(9223372036854775807L);
/* 548 */             if (l1 == 0L)
/* 549 */               this.curIndex = -1;
/*     */           }
/*     */           else {
/* 552 */             long l5 = TickCalculation.sub(this.startTimes[(this.curIndex + 1)], l1);
/* 553 */             localAnimation3.impl_timePulse(calcTimePulse(l5));
/*     */           }
/*     */         } else {
/* 556 */           EventHandler localEventHandler3 = localAnimation3.getOnFinished();
/* 557 */           if (localEventHandler3 != null) {
/* 558 */             localEventHandler3.handle(new ActionEvent(this, null));
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 563 */     this.oldTicks = l1;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void impl_jumpTo(long paramLong1, long paramLong2)
/*     */   {
/* 572 */     impl_sync(false);
/* 573 */     Animation.Status localStatus = getStatus();
/* 574 */     double d = calculateFraction(paramLong1, paramLong2);
/* 575 */     long l = Math.max(0L, Math.min(getCachedInterpolator().interpolate(0L, paramLong2, d), paramLong2));
/* 576 */     int i = this.curIndex;
/* 577 */     this.curIndex = findNewIndex(l);
/* 578 */     Animation localAnimation1 = this.cachedChildren[this.curIndex];
/* 579 */     if ((this.curIndex != i) && 
/* 580 */       (localStatus != Animation.Status.STOPPED)) {
/* 581 */       if ((i != -1) && (i != this.end)) {
/* 582 */         Animation localAnimation2 = this.cachedChildren[i];
/* 583 */         if (localAnimation2.getStatus() != Animation.Status.STOPPED) {
/* 584 */           this.cachedChildren[i].impl_stop();
/*     */         }
/*     */       }
/* 587 */       startChild(localAnimation1, this.curIndex);
/* 588 */       if (localStatus == Animation.Status.PAUSED) {
/* 589 */         localAnimation1.impl_pause();
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 594 */     this.offsetTicks = (getCurrentRate() < 0.0D ? TickCalculation.sub(this.startTimes[(this.curIndex + 1)], l) : TickCalculation.sub(l, TickCalculation.add(this.startTimes[this.curIndex], this.delays[this.curIndex])));
/* 595 */     localAnimation1.jumpTo(TickCalculation.toDuration(TickCalculation.sub(l, TickCalculation.add(this.startTimes[this.curIndex], this.delays[this.curIndex])), this.rates[this.curIndex]));
/* 596 */     this.oldTicks = l;
/*     */   }
/*     */ 
/*     */   protected void interpolate(double paramDouble)
/*     */   {
/*     */   }
/*     */ 
/*     */   private long calcTimePulse(long paramLong)
/*     */   {
/* 608 */     return TickCalculation.sub(Math.round(paramLong * Math.abs(this.rates[this.curIndex])), this.offsetTicks);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.animation.SequentialTransition
 * JD-Core Version:    0.6.2
 */