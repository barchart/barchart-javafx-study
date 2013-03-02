/*      */ package javafx.animation;
/*      */ 
/*      */ import com.sun.javafx.animation.TickCalculation;
/*      */ import com.sun.scenario.ToolkitAccessor;
/*      */ import com.sun.scenario.animation.AbstractMasterTimer;
/*      */ import com.sun.scenario.animation.shared.AnimationPulseReceiver;
/*      */ import com.sun.scenario.animation.shared.ClipEnvelope;
/*      */ import com.sun.scenario.animation.shared.ClipEnvelopeFactory;
/*      */ import java.util.HashMap;
/*      */ import javafx.beans.property.BooleanProperty;
/*      */ import javafx.beans.property.DoubleProperty;
/*      */ import javafx.beans.property.DoublePropertyBase;
/*      */ import javafx.beans.property.IntegerProperty;
/*      */ import javafx.beans.property.IntegerPropertyBase;
/*      */ import javafx.beans.property.ObjectProperty;
/*      */ import javafx.beans.property.ReadOnlyDoubleProperty;
/*      */ import javafx.beans.property.ReadOnlyDoublePropertyBase;
/*      */ import javafx.beans.property.ReadOnlyObjectProperty;
/*      */ import javafx.beans.property.ReadOnlyObjectPropertyBase;
/*      */ import javafx.beans.property.SimpleBooleanProperty;
/*      */ import javafx.beans.property.SimpleObjectProperty;
/*      */ import javafx.collections.FXCollections;
/*      */ import javafx.collections.ObservableMap;
/*      */ import javafx.event.ActionEvent;
/*      */ import javafx.event.EventHandler;
/*      */ import javafx.util.Duration;
/*      */ 
/*      */ public abstract class Animation
/*      */ {
/*      */   public static final int INDEFINITE = -1;
/*      */   private static final double EPSILON = 1.0E-12D;
/*      */   private final AnimationPulseReceiver pulseReceiver;
/*      */   ClipEnvelope clipEnvelope;
/*  176 */   private boolean lastPlayedFinished = false;
/*      */ 
/*  178 */   private boolean lastPlayedForward = true;
/*      */   private DoubleProperty rate;
/*      */   private static final double DEFAULT_RATE = 1.0D;
/*  256 */   private double oldRate = 1.0D;
/*      */   private ReadOnlyDoubleProperty currentRate;
/*      */   private static final double DEFAULT_CURRENT_RATE = 0.0D;
/*      */   private ReadOnlyObjectProperty<Duration> cycleDuration;
/*  300 */   private static final Duration DEFAULT_CYCLE_DURATION = Duration.ZERO;
/*      */   private ReadOnlyObjectProperty<Duration> totalDuration;
/*  332 */   private static final Duration DEFAULT_TOTAL_DURATION = Duration.ZERO;
/*      */   private CurrentTimeProperty currentTime;
/*      */   private long currentTicks;
/*      */   private ObjectProperty<Duration> delay;
/*  410 */   private static final Duration DEFAULT_DELAY = Duration.ZERO;
/*      */   private IntegerProperty cycleCount;
/*      */   private static final int DEFAULT_CYCLE_COUNT = 1;
/*      */   private BooleanProperty autoReverse;
/*      */   private static final boolean DEFAULT_AUTO_REVERSE = false;
/*      */   private ReadOnlyObjectProperty<Status> status;
/*  520 */   private static final Status DEFAULT_STATUS = Status.STOPPED;
/*      */   private final double targetFramerate;
/*      */   private final int resolution;
/*      */   private long lastPulse;
/*      */   private ObjectProperty<EventHandler<ActionEvent>> onFinished;
/*  562 */   private static final EventHandler<ActionEvent> DEFAULT_ON_FINISHED = null;
/*      */ 
/*  581 */   private final ObservableMap<String, Duration> cuePoints = FXCollections.observableMap(new HashMap(0));
/*      */ 
/*      */   public final void setRate(double paramDouble)
/*      */   {
/*  203 */     if ((this.rate != null) || (Math.abs(paramDouble - 1.0D) > 1.0E-12D))
/*  204 */       rateProperty().set(paramDouble);
/*      */   }
/*      */ 
/*      */   public final double getRate()
/*      */   {
/*  209 */     return this.rate == null ? 1.0D : this.rate.get();
/*      */   }
/*      */ 
/*      */   public final DoubleProperty rateProperty() {
/*  213 */     if (this.rate == null) {
/*  214 */       this.rate = new DoublePropertyBase(1.0D)
/*      */       {
/*      */         public void invalidated()
/*      */         {
/*  218 */           double d1 = Animation.this.getRate();
/*  219 */           if (Math.abs(d1) < 1.0E-12D) {
/*  220 */             if (Animation.this.getStatus() == Animation.Status.RUNNING) {
/*  221 */               Animation.this.lastPlayedForward = (Math.abs(Animation.this.getCurrentRate() - Animation.this.oldRate) < 1.0E-12D);
/*      */             }
/*      */ 
/*  224 */             Animation.this.setCurrentRate(0.0D);
/*  225 */             Animation.this.pulseReceiver.pause();
/*      */           } else {
/*  227 */             if (Animation.this.getStatus() == Animation.Status.RUNNING) {
/*  228 */               double d2 = Animation.this.getCurrentRate();
/*  229 */               if (Math.abs(d2) < 1.0E-12D) {
/*  230 */                 Animation.this.setCurrentRate(Animation.this.lastPlayedForward ? d1 : -d1);
/*  231 */                 Animation.this.pulseReceiver.resume();
/*      */               } else {
/*  233 */                 int i = Math.abs(d2 - Animation.this.oldRate) < 1.0E-12D ? 1 : 0;
/*  234 */                 Animation.this.setCurrentRate(i != 0 ? d1 : -d1);
/*      */               }
/*      */             }
/*  237 */             Animation.this.oldRate = d1;
/*      */           }
/*  239 */           Animation.this.clipEnvelope.setRate(d1);
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/*  244 */           return Animation.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/*  249 */           return "rate";
/*      */         }
/*      */       };
/*      */     }
/*  253 */     return this.rate;
/*      */   }
/*      */ 
/*      */   private void setCurrentRate(double paramDouble)
/*      */   {
/*  272 */     if ((this.currentRate != null) || (Math.abs(paramDouble - 0.0D) > 1.0E-12D))
/*  273 */       ((CurrentRateProperty)currentRateProperty()).set(paramDouble);
/*      */   }
/*      */ 
/*      */   public final double getCurrentRate()
/*      */   {
/*  278 */     return this.currentRate == null ? 0.0D : this.currentRate.get();
/*      */   }
/*      */ 
/*      */   public final ReadOnlyDoubleProperty currentRateProperty() {
/*  282 */     if (this.currentRate == null) {
/*  283 */       this.currentRate = new CurrentRateProperty(null);
/*      */     }
/*  285 */     return this.currentRate;
/*      */   }
/*      */ 
/*      */   protected final void setCycleDuration(Duration paramDuration)
/*      */   {
/*  303 */     if ((this.cycleDuration != null) || (!DEFAULT_CYCLE_DURATION.equals(paramDuration))) {
/*  304 */       ((AnimationReadOnlyProperty)cycleDurationProperty()).set(paramDuration);
/*  305 */       updateTotalDuration();
/*      */     }
/*      */   }
/*      */ 
/*      */   public final Duration getCycleDuration() {
/*  310 */     return this.cycleDuration == null ? DEFAULT_CYCLE_DURATION : (Duration)this.cycleDuration.get();
/*      */   }
/*      */ 
/*      */   public final ReadOnlyObjectProperty<Duration> cycleDurationProperty() {
/*  314 */     if (this.cycleDuration == null) {
/*  315 */       this.cycleDuration = new AnimationReadOnlyProperty("cycleDuration", DEFAULT_CYCLE_DURATION, null);
/*      */     }
/*  317 */     return this.cycleDuration;
/*      */   }
/*      */ 
/*      */   public final Duration getTotalDuration()
/*      */   {
/*  335 */     return this.totalDuration == null ? DEFAULT_TOTAL_DURATION : (Duration)this.totalDuration.get();
/*      */   }
/*      */ 
/*      */   public final ReadOnlyObjectProperty<Duration> totalDurationProperty() {
/*  339 */     if (this.totalDuration == null) {
/*  340 */       this.totalDuration = new AnimationReadOnlyProperty("totalDuration", DEFAULT_TOTAL_DURATION, null);
/*      */     }
/*  342 */     return this.totalDuration;
/*      */   }
/*      */ 
/*      */   private void updateTotalDuration()
/*      */   {
/*  348 */     int i = getCycleCount();
/*  349 */     Duration localDuration1 = getCycleDuration();
/*  350 */     Duration localDuration2 = i <= 1 ? localDuration1 : i == -1 ? Duration.INDEFINITE : Duration.ZERO.equals(localDuration1) ? Duration.ZERO : localDuration1.multiply(i);
/*      */ 
/*  354 */     if ((localDuration2 != null) || (!DEFAULT_TOTAL_DURATION.equals(localDuration2))) {
/*  355 */       ((AnimationReadOnlyProperty)totalDurationProperty()).set(localDuration2);
/*      */     }
/*  357 */     if (localDuration2.lessThan(getCurrentTime()))
/*  358 */       jumpTo(localDuration2);
/*      */   }
/*      */ 
/*      */   public final Duration getCurrentTime()
/*      */   {
/*  394 */     return Duration.millis(this.currentTicks / 6.0D);
/*      */   }
/*      */ 
/*      */   public final ReadOnlyObjectProperty<Duration> currentTimeProperty() {
/*  398 */     if (this.currentTime == null) {
/*  399 */       this.currentTime = new CurrentTimeProperty(null);
/*      */     }
/*  401 */     return this.currentTime;
/*      */   }
/*      */ 
/*      */   public final void setDelay(Duration paramDuration)
/*      */   {
/*  413 */     if ((this.delay != null) || (!DEFAULT_DELAY.equals(paramDuration)))
/*  414 */       delayProperty().set(paramDuration);
/*      */   }
/*      */ 
/*      */   public final Duration getDelay()
/*      */   {
/*  419 */     return this.delay == null ? DEFAULT_DELAY : (Duration)this.delay.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<Duration> delayProperty() {
/*  423 */     if (this.delay == null) {
/*  424 */       this.delay = new SimpleObjectProperty(this, "delay", DEFAULT_DELAY);
/*      */     }
/*  426 */     return this.delay;
/*      */   }
/*      */ 
/*      */   public final void setCycleCount(int paramInt)
/*      */   {
/*  446 */     if ((this.cycleCount != null) || (paramInt != 1))
/*  447 */       cycleCountProperty().set(paramInt);
/*      */   }
/*      */ 
/*      */   public final int getCycleCount()
/*      */   {
/*  452 */     return this.cycleCount == null ? 1 : this.cycleCount.get();
/*      */   }
/*      */ 
/*      */   public final IntegerProperty cycleCountProperty() {
/*  456 */     if (this.cycleCount == null) {
/*  457 */       this.cycleCount = new IntegerPropertyBase(1)
/*      */       {
/*      */         public void invalidated()
/*      */         {
/*  461 */           Animation.this.updateTotalDuration();
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/*  466 */           return Animation.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/*  471 */           return "cycleCount";
/*      */         }
/*      */       };
/*      */     }
/*  475 */     return this.cycleCount;
/*      */   }
/*      */ 
/*      */   public final void setAutoReverse(boolean paramBoolean)
/*      */   {
/*  497 */     if ((this.autoReverse != null) || (paramBoolean))
/*  498 */       autoReverseProperty().set(paramBoolean);
/*      */   }
/*      */ 
/*      */   public final boolean isAutoReverse()
/*      */   {
/*  503 */     return this.autoReverse == null ? false : this.autoReverse.get();
/*      */   }
/*      */ 
/*      */   public final BooleanProperty autoReverseProperty() {
/*  507 */     if (this.autoReverse == null) {
/*  508 */       this.autoReverse = new SimpleBooleanProperty(this, "autoReverse", false);
/*      */     }
/*  510 */     return this.autoReverse;
/*      */   }
/*      */ 
/*      */   protected final void setStatus(Status paramStatus)
/*      */   {
/*  523 */     if ((this.status != null) || (!DEFAULT_STATUS.equals(paramStatus)))
/*  524 */       ((AnimationReadOnlyProperty)statusProperty()).set(paramStatus);
/*      */   }
/*      */ 
/*      */   public final Status getStatus()
/*      */   {
/*  529 */     return this.status == null ? DEFAULT_STATUS : (Status)this.status.get();
/*      */   }
/*      */ 
/*      */   public final ReadOnlyObjectProperty<Status> statusProperty() {
/*  533 */     if (this.status == null) {
/*  534 */       this.status = new AnimationReadOnlyProperty("status", Status.STOPPED, null);
/*      */     }
/*  536 */     return this.status;
/*      */   }
/*      */ 
/*      */   public final double getTargetFramerate()
/*      */   {
/*  553 */     return this.targetFramerate;
/*      */   }
/*      */ 
/*      */   public final void setOnFinished(EventHandler<ActionEvent> paramEventHandler)
/*      */   {
/*  565 */     if ((this.onFinished != null) || (paramEventHandler != null))
/*  566 */       onFinishedProperty().set(paramEventHandler);
/*      */   }
/*      */ 
/*      */   public final EventHandler<ActionEvent> getOnFinished()
/*      */   {
/*  571 */     return this.onFinished == null ? DEFAULT_ON_FINISHED : (EventHandler)this.onFinished.get();
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<EventHandler<ActionEvent>> onFinishedProperty() {
/*  575 */     if (this.onFinished == null) {
/*  576 */       this.onFinished = new SimpleObjectProperty(this, "onFinished", DEFAULT_ON_FINISHED);
/*      */     }
/*  578 */     return this.onFinished;
/*      */   }
/*      */ 
/*      */   public final ObservableMap<String, Duration> getCuePoints()
/*      */   {
/*  602 */     return this.cuePoints;
/*      */   }
/*      */ 
/*      */   public void jumpTo(Duration paramDuration)
/*      */   {
/*  620 */     if (paramDuration == null) {
/*  621 */       throw new NullPointerException("Time needs to be specified.");
/*      */     }
/*  623 */     if (paramDuration.isUnknown()) {
/*  624 */       throw new IllegalArgumentException("The time is invalid");
/*      */     }
/*      */ 
/*  627 */     this.lastPlayedFinished = false;
/*      */ 
/*  629 */     Duration localDuration1 = getCycleDuration();
/*  630 */     long l1 = TickCalculation.fromDuration(localDuration1);
/*      */ 
/*  632 */     Duration localDuration2 = getTotalDuration();
/*  633 */     paramDuration = paramDuration.greaterThan(localDuration2) ? localDuration2 : paramDuration.lessThan(Duration.ZERO) ? Duration.ZERO : paramDuration;
/*      */ 
/*  635 */     long l2 = TickCalculation.fromDuration(paramDuration);
/*      */ 
/*  637 */     long l3 = l1 == 0L ? 0L : l2 % l1;
/*  638 */     impl_setCurrentTicks((l3 == 0L) && (l2 > 0L) ? l1 : l3);
/*      */ 
/*  640 */     if (getStatus() == Status.STOPPED) {
/*  641 */       syncClipEnvelope();
/*      */     }
/*  643 */     this.clipEnvelope.jumpTo(l2);
/*      */   }
/*      */ 
/*      */   public void jumpTo(String paramString)
/*      */   {
/*  669 */     if (paramString == null) {
/*  670 */       throw new NullPointerException("CuePoint needs to be specified");
/*      */     }
/*  672 */     if ("start".equalsIgnoreCase(paramString)) {
/*  673 */       jumpTo(Duration.ZERO);
/*  674 */     } else if ("end".equalsIgnoreCase(paramString)) {
/*  675 */       jumpTo(getTotalDuration());
/*      */     } else {
/*  677 */       Duration localDuration = (Duration)getCuePoints().get(paramString);
/*  678 */       if (localDuration != null)
/*  679 */         jumpTo(localDuration);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void playFrom(String paramString)
/*      */   {
/*  706 */     jumpTo(paramString);
/*  707 */     play();
/*      */   }
/*      */ 
/*      */   public void playFrom(Duration paramDuration)
/*      */   {
/*  732 */     jumpTo(paramDuration);
/*  733 */     play();
/*      */   }
/*      */ 
/*      */   public void play()
/*      */   {
/*  762 */     play(true);
/*      */   }
/*      */ 
/*      */   private void play(boolean paramBoolean) {
/*  766 */     switch (3.$SwitchMap$javafx$animation$Animation$Status[getStatus().ordinal()]) {
/*      */     case 1:
/*  768 */       if (impl_startable(paramBoolean)) {
/*  769 */         double d = getRate();
/*  770 */         if (this.lastPlayedFinished) {
/*  771 */           jumpTo(d < 0.0D ? getTotalDuration() : Duration.ZERO);
/*      */         }
/*  773 */         this.lastPlayedFinished = false;
/*  774 */         impl_start(paramBoolean);
/*  775 */         this.pulseReceiver.start(Math.round(6.0D * getDelay().toMillis()));
/*  776 */         if (Math.abs(d) < 1.0E-12D) {
/*  777 */           this.pulseReceiver.pause();
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  782 */         EventHandler localEventHandler = getOnFinished();
/*  783 */         if (localEventHandler != null) {
/*  784 */           localEventHandler.handle(new ActionEvent(this, null));
/*      */         }
/*      */       }
/*  787 */       break;
/*      */     case 2:
/*  789 */       impl_resume();
/*  790 */       if (Math.abs(getRate()) >= 1.0E-12D)
/*  791 */         this.pulseReceiver.resume();
/*      */       break;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void playFromStart()
/*      */   {
/*  816 */     stop();
/*  817 */     setRate(Math.abs(getRate()));
/*  818 */     jumpTo(Duration.ZERO);
/*  819 */     play(true);
/*      */   }
/*      */ 
/*      */   public void stop()
/*      */   {
/*  831 */     if (getStatus() != Status.STOPPED) {
/*  832 */       this.clipEnvelope.abortCurrentPulse();
/*  833 */       impl_stop();
/*  834 */       jumpTo(Duration.ZERO);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void pause()
/*      */   {
/*  847 */     if (getStatus() == Status.RUNNING) {
/*  848 */       this.clipEnvelope.abortCurrentPulse();
/*  849 */       this.pulseReceiver.pause();
/*  850 */       impl_pause();
/*      */     }
/*      */   }
/*      */ 
/*      */   protected Animation(double paramDouble)
/*      */   {
/*  864 */     this.targetFramerate = paramDouble;
/*  865 */     this.resolution = ((int)Math.max(1L, Math.round(6000.0D / paramDouble)));
/*  866 */     this.pulseReceiver = new AnimationPulseReceiver(this, ToolkitAccessor.getMasterTimer());
/*      */ 
/*  868 */     this.clipEnvelope = ClipEnvelopeFactory.create(this);
/*      */   }
/*      */ 
/*      */   protected Animation()
/*      */   {
/*  875 */     this.resolution = 0;
/*  876 */     this.targetFramerate = (6000.0D / ToolkitAccessor.getMasterTimer().getDefaultResolution());
/*  877 */     this.pulseReceiver = new AnimationPulseReceiver(this, ToolkitAccessor.getMasterTimer());
/*      */ 
/*  879 */     this.clipEnvelope = ClipEnvelopeFactory.create(this);
/*      */   }
/*      */ 
/*      */   Animation(AnimationPulseReceiver paramAnimationPulseReceiver, ClipEnvelope paramClipEnvelope)
/*      */   {
/*  884 */     this.resolution = 0;
/*  885 */     this.targetFramerate = (6000.0D / ToolkitAccessor.getMasterTimer().getDefaultResolution());
/*  886 */     this.pulseReceiver = paramAnimationPulseReceiver;
/*  887 */     this.clipEnvelope = paramClipEnvelope;
/*      */   }
/*      */ 
/*      */   Animation(AnimationPulseReceiver paramAnimationPulseReceiver, ClipEnvelope paramClipEnvelope, int paramInt)
/*      */   {
/*  892 */     this.resolution = paramInt;
/*  893 */     this.targetFramerate = (6000.0D / paramInt);
/*  894 */     this.pulseReceiver = paramAnimationPulseReceiver;
/*  895 */     this.clipEnvelope = paramClipEnvelope;
/*      */   }
/*      */ 
/*      */   boolean impl_startable(boolean paramBoolean) {
/*  899 */     return (TickCalculation.fromDuration(getCycleDuration()) > 0L) || ((!paramBoolean) && (this.clipEnvelope.wasSynched()));
/*      */   }
/*      */ 
/*      */   void impl_sync(boolean paramBoolean)
/*      */   {
/*  904 */     if ((paramBoolean) || (!this.clipEnvelope.wasSynched()))
/*  905 */       syncClipEnvelope();
/*      */   }
/*      */ 
/*      */   private void syncClipEnvelope()
/*      */   {
/*  910 */     int i = getCycleCount();
/*  911 */     int j = (i <= 0) && (i != -1) ? 1 : i;
/*      */ 
/*  913 */     this.clipEnvelope = this.clipEnvelope.setCycleCount(j);
/*  914 */     this.clipEnvelope.setCycleDuration(getCycleDuration());
/*  915 */     this.clipEnvelope.setAutoReverse(isAutoReverse());
/*      */   }
/*      */ 
/*      */   void impl_start(boolean paramBoolean) {
/*  919 */     impl_sync(paramBoolean);
/*  920 */     setStatus(Status.RUNNING);
/*  921 */     this.clipEnvelope.start();
/*  922 */     setCurrentRate(this.clipEnvelope.getCurrentRate());
/*  923 */     this.lastPulse = 0L;
/*      */   }
/*      */ 
/*      */   void impl_pause() {
/*  927 */     double d = getCurrentRate();
/*  928 */     if (Math.abs(d) >= 1.0E-12D) {
/*  929 */       this.lastPlayedForward = (Math.abs(getCurrentRate() - getRate()) < 1.0E-12D);
/*      */     }
/*  931 */     setCurrentRate(0.0D);
/*  932 */     setStatus(Status.PAUSED);
/*      */   }
/*      */ 
/*      */   void impl_resume() {
/*  936 */     setStatus(Status.RUNNING);
/*  937 */     setCurrentRate(this.lastPlayedForward ? getRate() : -getRate());
/*      */   }
/*      */ 
/*      */   void impl_stop() {
/*  941 */     this.pulseReceiver.stop();
/*  942 */     setStatus(Status.STOPPED);
/*  943 */     setCurrentRate(0.0D);
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public void impl_timePulse(long paramLong)
/*      */   {
/*  952 */     if (this.resolution == 0) {
/*  953 */       this.clipEnvelope.timePulse(paramLong);
/*  954 */     } else if (paramLong - this.lastPulse >= this.resolution) {
/*  955 */       this.lastPulse = (paramLong / this.resolution * this.resolution);
/*  956 */       this.clipEnvelope.timePulse(paramLong);
/*      */     }
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public abstract void impl_playTo(long paramLong1, long paramLong2);
/*      */ 
/*      */   @Deprecated
/*      */   public abstract void impl_jumpTo(long paramLong1, long paramLong2);
/*      */ 
/*      */   @Deprecated
/*      */   public void impl_setCurrentTicks(long paramLong)
/*      */   {
/*  980 */     this.currentTicks = paramLong;
/*  981 */     if (this.currentTime != null)
/*  982 */       this.currentTime.fireValueChangedEvent();
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public void impl_setCurrentRate(double paramDouble)
/*      */   {
/*  993 */     setCurrentRate(paramDouble);
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public void impl_finished()
/*      */   {
/* 1003 */     this.lastPlayedFinished = true;
/* 1004 */     impl_stop();
/* 1005 */     EventHandler localEventHandler = getOnFinished();
/* 1006 */     if (localEventHandler != null)
/*      */       try {
/* 1008 */         localEventHandler.handle(new ActionEvent(this, null));
/*      */       } catch (Exception localException) {
/* 1010 */         localException.printStackTrace();
/*      */       }
/*      */   }
/*      */ 
/*      */   private class CurrentTimeProperty extends ReadOnlyObjectPropertyBase<Duration>
/*      */   {
/*      */     private CurrentTimeProperty()
/*      */     {
/*      */     }
/*      */ 
/*      */     public Object getBean()
/*      */     {
/*  373 */       return Animation.this;
/*      */     }
/*      */ 
/*      */     public String getName()
/*      */     {
/*  378 */       return "currentTime";
/*      */     }
/*      */ 
/*      */     public Duration get()
/*      */     {
/*  383 */       return Animation.this.getCurrentTime();
/*      */     }
/*      */ 
/*      */     public void fireValueChangedEvent()
/*      */     {
/*  388 */       super.fireValueChangedEvent();
/*      */     }
/*      */   }
/*      */ 
/*      */   private class AnimationReadOnlyProperty<T> extends ReadOnlyObjectPropertyBase<T>
/*      */   {
/*      */     private final String name;
/*      */     private T value;
/*      */ 
/*      */     private AnimationReadOnlyProperty(T arg2)
/*      */     {
/*      */       Object localObject1;
/*  145 */       this.name = localObject1;
/*      */       Object localObject2;
/*  146 */       this.value = localObject2;
/*      */     }
/*      */ 
/*      */     public Object getBean()
/*      */     {
/*  151 */       return Animation.this;
/*      */     }
/*      */ 
/*      */     public String getName()
/*      */     {
/*  156 */       return this.name;
/*      */     }
/*      */ 
/*      */     public T get()
/*      */     {
/*  161 */       return this.value;
/*      */     }
/*      */ 
/*      */     private void set(T paramT) {
/*  165 */       this.value = paramT;
/*  166 */       fireValueChangedEvent();
/*      */     }
/*      */   }
/*      */ 
/*      */   private class CurrentRateProperty extends ReadOnlyDoublePropertyBase
/*      */   {
/*      */     private double value;
/*      */ 
/*      */     private CurrentRateProperty()
/*      */     {
/*      */     }
/*      */ 
/*      */     public Object getBean()
/*      */     {
/*  120 */       return Animation.this;
/*      */     }
/*      */ 
/*      */     public String getName()
/*      */     {
/*  125 */       return "currentRate";
/*      */     }
/*      */ 
/*      */     public double get()
/*      */     {
/*  130 */       return this.value;
/*      */     }
/*      */ 
/*      */     private void set(double paramDouble) {
/*  134 */       this.value = paramDouble;
/*  135 */       fireValueChangedEvent();
/*      */     }
/*      */   }
/*      */ 
/*      */   public static enum Status
/*      */   {
/*  102 */     PAUSED, 
/*      */ 
/*  106 */     RUNNING, 
/*      */ 
/*  110 */     STOPPED;
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.animation.Animation
 * JD-Core Version:    0.6.2
 */