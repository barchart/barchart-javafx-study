/*      */ package javafx.scene.media;
/*      */ 
/*      */ import com.sun.javafx.tk.TKPulseListener;
/*      */ import com.sun.javafx.tk.Toolkit;
/*      */ import com.sun.media.jfxmedia.MediaManager;
/*      */ import com.sun.media.jfxmedia.control.VideoDataBuffer;
/*      */ import com.sun.media.jfxmedia.control.VideoRenderControl;
/*      */ import com.sun.media.jfxmedia.effects.AudioSpectrum;
/*      */ import com.sun.media.jfxmedia.events.AudioSpectrumEvent;
/*      */ import com.sun.media.jfxmedia.events.BufferListener;
/*      */ import com.sun.media.jfxmedia.events.BufferProgressEvent;
/*      */ import com.sun.media.jfxmedia.events.MarkerEvent;
/*      */ import com.sun.media.jfxmedia.events.MarkerListener;
/*      */ import com.sun.media.jfxmedia.events.MediaErrorListener;
/*      */ import com.sun.media.jfxmedia.events.NewFrameEvent;
/*      */ import com.sun.media.jfxmedia.events.PlayerStateEvent;
/*      */ import com.sun.media.jfxmedia.events.PlayerStateListener;
/*      */ import com.sun.media.jfxmedia.events.PlayerTimeListener;
/*      */ import com.sun.media.jfxmedia.events.VideoRendererListener;
/*      */ import com.sun.media.jfxmedia.events.VideoTrackSizeListener;
/*      */ import com.sun.media.jfxmedia.locator.Locator;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.Set;
/*      */ import java.util.Timer;
/*      */ import javafx.application.Platform;
/*      */ import javafx.beans.property.BooleanProperty;
/*      */ import javafx.beans.property.BooleanPropertyBase;
/*      */ import javafx.beans.property.DoubleProperty;
/*      */ import javafx.beans.property.DoublePropertyBase;
/*      */ import javafx.beans.property.IntegerProperty;
/*      */ import javafx.beans.property.IntegerPropertyBase;
/*      */ import javafx.beans.property.ObjectProperty;
/*      */ import javafx.beans.property.ObjectPropertyBase;
/*      */ import javafx.beans.property.ReadOnlyDoubleProperty;
/*      */ import javafx.beans.property.ReadOnlyDoubleWrapper;
/*      */ import javafx.beans.property.ReadOnlyIntegerProperty;
/*      */ import javafx.beans.property.ReadOnlyIntegerWrapper;
/*      */ import javafx.beans.property.ReadOnlyObjectProperty;
/*      */ import javafx.beans.property.ReadOnlyObjectWrapper;
/*      */ import javafx.beans.property.SimpleObjectProperty;
/*      */ import javafx.collections.MapChangeListener;
/*      */ import javafx.collections.MapChangeListener.Change;
/*      */ import javafx.collections.ObservableMap;
/*      */ import javafx.event.EventHandler;
/*      */ import javafx.util.Duration;
/*      */ import javafx.util.Pair;
/*      */ 
/*      */ public final class MediaPlayer
/*      */ {
/*      */   public static final int INDEFINITE = -1;
/*      */   private static final double RATE_MIN = 0.0D;
/*      */   private static final double RATE_MAX = 8.0D;
/*      */   private static final int AUDIOSPECTRUM_THRESHOLD_MAX = 0;
/*      */   private static final double AUDIOSPECTRUM_INTERVAL_MIN = 1.E-09D;
/*      */   private static final int AUDIOSPECTRUM_NUMBANDS_MIN = 2;
/*      */   private com.sun.media.jfxmedia.MediaPlayer jfxPlayer;
/*  252 */   private MapChangeListener<String, Duration> markerMapListener = null;
/*  253 */   private MarkerListener markerEventListener = null;
/*      */ 
/*  255 */   private PlayerStateListener stateListener = null;
/*  256 */   private PlayerTimeListener timeListener = null;
/*  257 */   private VideoTrackSizeListener sizeListener = null;
/*  258 */   private MediaErrorListener errorListener = null;
/*  259 */   private BufferListener bufferListener = null;
/*  260 */   private com.sun.media.jfxmedia.events.AudioSpectrumListener spectrumListener = null;
/*  261 */   private RendererListener rendererListener = null;
/*      */ 
/*  264 */   private boolean rateChangeRequested = false;
/*  265 */   private boolean volumeChangeRequested = false;
/*  266 */   private boolean balanceChangeRequested = false;
/*  267 */   private boolean startTimeChangeRequested = false;
/*  268 */   private boolean stopTimeChangeRequested = false;
/*  269 */   private boolean muteChangeRequested = false;
/*  270 */   private boolean playRequested = false;
/*  271 */   private boolean audioSpectrumNumBandsChangeRequested = false;
/*  272 */   private boolean audioSpectrumIntervalChangeRequested = false;
/*  273 */   private boolean audioSpectrumThresholdChangeRequested = false;
/*  274 */   private boolean audioSpectrumEnabledChangeRequested = false;
/*      */ 
/*  276 */   private Timer mediaTimer = null;
/*  277 */   private double prevTimeMs = -1.0D;
/*  278 */   private boolean isUpdateTimeEnabled = false;
/*  279 */   private BufferProgressEvent lastBufferEvent = null;
/*      */   private static final int DEFAULT_SPECTRUM_BAND_COUNT = 128;
/*      */   private static final double DEFAULT_SPECTRUM_INTERVAL = 0.1D;
/*      */   private static final int DEFAULT_SPECTRUM_THRESHOLD = -60;
/*  286 */   private final Set<WeakReference<MediaView>> viewRefs = new HashSet();
/*      */   private AudioEqualizer audioEqualizer;
/*      */   private ReadOnlyObjectWrapper<MediaException> error;
/*      */   private ObjectProperty<Runnable> onError;
/*      */   private Media media;
/*      */   private BooleanProperty autoPlay;
/*      */   private boolean playerReady;
/*      */   private DoubleProperty rate;
/*      */   private ReadOnlyDoubleWrapper currentRate;
/*      */   private DoubleProperty volume;
/*      */   private DoubleProperty balance;
/*      */   private ObjectProperty<Duration> startTime;
/*      */   private ObjectProperty<Duration> stopTime;
/*      */   private ReadOnlyObjectWrapper<Duration> cycleDuration;
/*      */   private ReadOnlyObjectWrapper<Duration> totalDuration;
/*      */   private ReadOnlyObjectWrapper<Duration> currentTime;
/*      */   private ReadOnlyObjectWrapper<Status> status;
/*      */   private ReadOnlyObjectWrapper<Duration> bufferProgressTime;
/*      */   private IntegerProperty cycleCount;
/*      */   private ReadOnlyIntegerWrapper currentCount;
/*      */   private BooleanProperty mute;
/*      */   private ObjectProperty<EventHandler<MediaMarkerEvent>> onMarker;
/* 1588 */   private final Object timerLock = new Object();
/*      */   private ObjectProperty<Runnable> onEndOfMedia;
/*      */   private ObjectProperty<Runnable> onReady;
/*      */   private ObjectProperty<Runnable> onPlaying;
/*      */   private ObjectProperty<Runnable> onPaused;
/*      */   private ObjectProperty<Runnable> onStopped;
/*      */   private ObjectProperty<Runnable> onHalted;
/*      */   private ObjectProperty<Runnable> onRepeat;
/*      */   private ObjectProperty<Runnable> onStalled;
/*      */   private IntegerProperty audioSpectrumNumBands;
/*      */   private DoubleProperty audioSpectrumInterval;
/*      */   private IntegerProperty audioSpectrumThreshold;
/*      */   private ObjectProperty<AudioSpectrumListener> audioSpectrumListener;
/* 2534 */   private final Object renderLock = new Object();
/*      */   private VideoDataBuffer currentRenderFrame;
/*      */   private VideoDataBuffer nextRenderFrame;
/*      */ 
/*      */   com.sun.media.jfxmedia.MediaPlayer retrieveJfxPlayer()
/*      */   {
/*  249 */     return this.jfxPlayer;
/*      */   }
/*      */ 
/*      */   private static double clamp(double paramDouble1, double paramDouble2, double paramDouble3)
/*      */   {
/*  296 */     if ((paramDouble2 != 4.9E-324D) && (paramDouble1 < paramDouble2))
/*  297 */       return paramDouble2;
/*  298 */     if ((paramDouble3 != 1.7976931348623157E+308D) && (paramDouble1 > paramDouble3)) {
/*  299 */       return paramDouble3;
/*      */     }
/*  301 */     return paramDouble1;
/*      */   }
/*      */ 
/*      */   private static int clamp(int paramInt1, int paramInt2, int paramInt3)
/*      */   {
/*  306 */     if ((paramInt2 != -2147483648) && (paramInt1 < paramInt2))
/*  307 */       return paramInt2;
/*  308 */     if ((paramInt3 != 2147483647) && (paramInt1 > paramInt3)) {
/*  309 */       return paramInt3;
/*      */     }
/*  311 */     return paramInt1;
/*      */   }
/*      */ 
/*      */   public final AudioEqualizer getAudioEqualizer()
/*      */   {
/*  320 */     if (this.audioEqualizer == null) {
/*  321 */       this.audioEqualizer = new AudioEqualizer();
/*  322 */       if (this.jfxPlayer != null) {
/*  323 */         this.audioEqualizer.setAudioEqualizer(this.jfxPlayer.getEqualizer());
/*      */       }
/*  325 */       this.audioEqualizer.setEnabled(true);
/*      */     }
/*  327 */     return this.audioEqualizer;
/*      */   }
/*      */ 
/*      */   public MediaPlayer(Media paramMedia)
/*      */   {
/*  351 */     if (null == paramMedia) {
/*  352 */       throw new NullPointerException("media == null!");
/*      */     }
/*      */ 
/*  355 */     this.media = paramMedia;
/*      */     try
/*      */     {
/*  358 */       Locator localLocator = paramMedia.retrieveJfxLocator();
/*  359 */       if (localLocator.canBlock()) {
/*  360 */         InitMediaPlayer localInitMediaPlayer = new InitMediaPlayer(null);
/*  361 */         Thread localThread = new Thread(localInitMediaPlayer);
/*  362 */         localThread.setDaemon(true);
/*  363 */         localThread.start();
/*      */       } else {
/*  365 */         init();
/*      */       }
/*      */     } catch (com.sun.media.jfxmedia.MediaException localMediaException) {
/*  368 */       throw MediaException.exceptionToMediaException(localMediaException);
/*      */     } catch (MediaException localMediaException1) {
/*  370 */       throw localMediaException1;
/*      */     }
/*      */   }
/*      */ 
/*      */   void registerListeners() {
/*  375 */     if (this.jfxPlayer != null)
/*      */     {
/*  378 */       MediaManager.registerMediaPlayerForDispose(this, this.jfxPlayer);
/*      */ 
/*  380 */       MediaManager.addMediaErrorListener(this.errorListener);
/*  381 */       this.jfxPlayer.addMediaErrorListener(this.errorListener);
/*      */ 
/*  383 */       this.jfxPlayer.addMediaPlayerListener(this.stateListener);
/*  384 */       this.jfxPlayer.addMediaTimeListener(this.timeListener);
/*  385 */       this.jfxPlayer.addVideoTrackSizeListener(this.sizeListener);
/*  386 */       this.jfxPlayer.addBufferListener(this.bufferListener);
/*  387 */       this.jfxPlayer.addMarkerListener(this.markerEventListener);
/*  388 */       this.jfxPlayer.addAudioSpectrumListener(this.spectrumListener);
/*  389 */       this.jfxPlayer.getVideoRenderControl().addVideoRendererListener(this.rendererListener);
/*      */     }
/*      */ 
/*  392 */     if (null != this.rendererListener)
/*      */     {
/*  395 */       Toolkit.getToolkit().addStageTkPulseListener(this.rendererListener);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void init() throws MediaException
/*      */   {
/*      */     try {
/*  402 */       Locator localLocator = this.media.retrieveJfxLocator();
/*  403 */       this.jfxPlayer = MediaManager.getPlayer(localLocator);
/*      */ 
/*  405 */       if (this.jfxPlayer != null)
/*      */       {
/*  407 */         MediaPlayerShutdownHook.addMediaPlayer(this);
/*      */ 
/*  410 */         this.jfxPlayer.setBalance((float)getBalance());
/*  411 */         this.jfxPlayer.setMute(isMute());
/*  412 */         this.jfxPlayer.setVolume((float)getVolume());
/*      */ 
/*  415 */         this.errorListener = new _MediaErrorListener(null);
/*  416 */         this.sizeListener = new _VideoTrackSizeListener(null);
/*  417 */         this.stateListener = new _PlayerStateListener(null);
/*  418 */         this.timeListener = new _PlayerTimeListener(null);
/*  419 */         this.bufferListener = new _BufferListener(null);
/*  420 */         this.markerEventListener = new _MarkerListener(null);
/*  421 */         this.spectrumListener = new _SpectrumListener(null);
/*  422 */         this.rendererListener = new RendererListener(null);
/*      */       }
/*      */ 
/*  427 */       this.markerMapListener = new MarkerMapChangeListener(null);
/*  428 */       localObservableMap = this.media.getMarkers();
/*  429 */       localObservableMap.addListener(this.markerMapListener);
/*      */ 
/*  433 */       localMedia = this.jfxPlayer.getMedia();
/*  434 */       for (String str : localObservableMap.keySet())
/*  435 */         if (str != null) {
/*  436 */           Duration localDuration = (Duration)localObservableMap.get(str);
/*  437 */           if (localDuration != null) {
/*  438 */             double d = localDuration.toMillis();
/*  439 */             if (d >= 0.0D)
/*  440 */               localMedia.addMarker(str, d / 1000.0D);
/*      */           }
/*      */         }
/*      */     }
/*      */     catch (com.sun.media.jfxmedia.MediaException localMediaException)
/*      */     {
/*      */       ObservableMap localObservableMap;
/*      */       com.sun.media.jfxmedia.Media localMedia;
/*  446 */       throw MediaException.exceptionToMediaException(localMediaException);
/*      */     }
/*      */ 
/*  450 */     Platform.runLater(new Runnable() {
/*      */       public void run() {
/*  452 */         MediaPlayer.this.registerListeners();
/*      */       }
/*      */     });
/*      */   }
/*      */ 
/*      */   private void setError(MediaException paramMediaException)
/*      */   {
/*  479 */     errorPropertyImpl().set(paramMediaException);
/*      */   }
/*      */ 
/*      */   public final MediaException getError()
/*      */   {
/*  488 */     return this.error == null ? null : (MediaException)this.error.get();
/*      */   }
/*      */ 
/*      */   public ReadOnlyObjectProperty<MediaException> errorProperty() {
/*  492 */     return errorPropertyImpl().getReadOnlyProperty();
/*      */   }
/*      */ 
/*      */   private ReadOnlyObjectWrapper<MediaException> errorPropertyImpl() {
/*  496 */     if (this.error == null) {
/*  497 */       this.error = new ReadOnlyObjectWrapper()
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/*  501 */           if (MediaPlayer.this.getOnError() != null)
/*  502 */             Platform.runLater(MediaPlayer.this.getOnError());
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/*  508 */           return MediaPlayer.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/*  513 */           return "error";
/*      */         }
/*      */       };
/*      */     }
/*  517 */     return this.error;
/*      */   }
/*      */ 
/*      */   public final void setOnError(Runnable paramRunnable)
/*      */   {
/*  530 */     onErrorProperty().set(paramRunnable);
/*      */   }
/*      */ 
/*      */   public final Runnable getOnError()
/*      */   {
/*  538 */     return this.onError == null ? null : (Runnable)this.onError.get();
/*      */   }
/*      */ 
/*      */   public ObjectProperty<Runnable> onErrorProperty() {
/*  542 */     if (this.onError == null) {
/*  543 */       this.onError = new ObjectPropertyBase()
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/*  552 */           if ((get() != null) && (MediaPlayer.this.getError() != null))
/*  553 */             Platform.runLater((Runnable)get());
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/*  559 */           return MediaPlayer.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/*  564 */           return "onError";
/*      */         }
/*      */       };
/*      */     }
/*  568 */     return this.onError;
/*      */   }
/*      */ 
/*      */   public final Media getMedia()
/*      */   {
/*  583 */     return this.media;
/*      */   }
/*      */ 
/*      */   public final void setAutoPlay(boolean paramBoolean)
/*      */   {
/*  600 */     autoPlayProperty().set(paramBoolean);
/*      */   }
/*      */ 
/*      */   public final boolean isAutoPlay()
/*      */   {
/*  608 */     return this.autoPlay == null ? false : this.autoPlay.get();
/*      */   }
/*      */ 
/*      */   public BooleanProperty autoPlayProperty() {
/*  612 */     if (this.autoPlay == null) {
/*  613 */       this.autoPlay = new BooleanPropertyBase()
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/*  617 */           if (MediaPlayer.this.autoPlay.get()) {
/*  618 */             if (MediaPlayer.this.playerReady)
/*  619 */               MediaPlayer.this.jfxPlayer.play();
/*      */             else
/*  621 */               MediaPlayer.this.playRequested = true;
/*      */           }
/*      */           else
/*  624 */             MediaPlayer.this.playRequested = false;
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/*  630 */           return MediaPlayer.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/*  635 */           return "autoPlay";
/*      */         }
/*      */       };
/*      */     }
/*  639 */     return this.autoPlay;
/*      */   }
/*      */ 
/*      */   public void play()
/*      */   {
/*  651 */     if (this.playerReady) {
/*  652 */       this.jfxPlayer.play();
/*      */     }
/*      */     else
/*  655 */       this.playRequested = true;
/*      */   }
/*      */ 
/*      */   public void pause()
/*      */   {
/*  663 */     if (this.playerReady)
/*  664 */       this.jfxPlayer.pause();
/*      */     else
/*  666 */       this.playRequested = false;
/*      */   }
/*      */ 
/*      */   public void stop()
/*      */   {
/*  681 */     if (this.playerReady) {
/*  682 */       this.jfxPlayer.stop();
/*  683 */       setCurrentCount(0);
/*  684 */       destroyMediaTimer();
/*      */     } else {
/*  686 */       this.playRequested = false;
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void setRate(double paramDouble)
/*      */   {
/*  706 */     rateProperty().set(paramDouble);
/*      */   }
/*      */ 
/*      */   public final double getRate()
/*      */   {
/*  714 */     return this.rate == null ? 1.0D : this.rate.get();
/*      */   }
/*      */ 
/*      */   public DoubleProperty rateProperty() {
/*  718 */     if (this.rate == null) {
/*  719 */       this.rate = new DoublePropertyBase(1.0D)
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/*  723 */           if (MediaPlayer.this.playerReady) {
/*  724 */             if (MediaPlayer.this.jfxPlayer.getDuration() != (1.0D / 0.0D))
/*  725 */               MediaPlayer.this.jfxPlayer.setRate((float)MediaPlayer.clamp(MediaPlayer.this.rate.get(), 0.0D, 8.0D));
/*      */           }
/*      */           else
/*  728 */             MediaPlayer.this.rateChangeRequested = true;
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/*  734 */           return MediaPlayer.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/*  739 */           return "rate";
/*      */         }
/*      */       };
/*      */     }
/*  743 */     return this.rate;
/*      */   }
/*      */ 
/*      */   private void setCurrentRate(double paramDouble)
/*      */   {
/*  755 */     currentRatePropertyImpl().set(paramDouble);
/*      */   }
/*      */ 
/*      */   public final double getCurrentRate()
/*      */   {
/*  763 */     return this.currentRate == null ? 0.0D : this.currentRate.get();
/*      */   }
/*      */ 
/*      */   public ReadOnlyDoubleProperty currentRateProperty() {
/*  767 */     return currentRatePropertyImpl().getReadOnlyProperty();
/*      */   }
/*      */ 
/*      */   private ReadOnlyDoubleWrapper currentRatePropertyImpl() {
/*  771 */     if (this.currentRate == null) {
/*  772 */       this.currentRate = new ReadOnlyDoubleWrapper(this, "currentRate");
/*      */     }
/*  774 */     return this.currentRate;
/*      */   }
/*      */ 
/*      */   public final void setVolume(double paramDouble)
/*      */   {
/*  791 */     volumeProperty().set(paramDouble);
/*      */   }
/*      */ 
/*      */   public final double getVolume()
/*      */   {
/*  799 */     return this.volume == null ? 1.0D : this.volume.get();
/*      */   }
/*      */ 
/*      */   public DoubleProperty volumeProperty() {
/*  803 */     if (this.volume == null) {
/*  804 */       this.volume = new DoublePropertyBase(1.0D)
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/*  808 */           if (MediaPlayer.this.playerReady)
/*  809 */             MediaPlayer.this.jfxPlayer.setVolume((float)MediaPlayer.clamp(MediaPlayer.this.volume.get(), 0.0D, 1.0D));
/*      */           else
/*  811 */             MediaPlayer.this.volumeChangeRequested = true;
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/*  817 */           return MediaPlayer.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/*  822 */           return "volume";
/*      */         }
/*      */       };
/*      */     }
/*  826 */     return this.volume;
/*      */   }
/*      */ 
/*      */   public final void setBalance(double paramDouble)
/*      */   {
/*  843 */     balanceProperty().set(paramDouble);
/*      */   }
/*      */ 
/*      */   public final double getBalance()
/*      */   {
/*  851 */     return this.balance == null ? 0.0D : this.balance.get();
/*      */   }
/*      */ 
/*      */   public DoubleProperty balanceProperty() {
/*  855 */     if (this.balance == null) {
/*  856 */       this.balance = new DoublePropertyBase()
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/*  860 */           if (MediaPlayer.this.playerReady)
/*  861 */             MediaPlayer.this.jfxPlayer.setBalance((float)MediaPlayer.clamp(MediaPlayer.this.balance.get(), -1.0D, 1.0D));
/*      */           else
/*  863 */             MediaPlayer.this.balanceChangeRequested = true;
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/*  869 */           return MediaPlayer.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/*  874 */           return "balance";
/*      */         }
/*      */       };
/*      */     }
/*  878 */     return this.balance;
/*      */   }
/*      */ 
/*      */   private double[] calculateStartStopTimes(Duration paramDuration1, Duration paramDuration2)
/*      */   {
/*      */     double d1;
/*  897 */     if ((paramDuration1 == null) || (paramDuration1.lessThan(Duration.ZERO)) || (paramDuration1.equals(Duration.UNKNOWN)))
/*      */     {
/*  899 */       d1 = 0.0D;
/*  900 */     } else if (paramDuration1.equals(Duration.INDEFINITE))
/*  901 */       d1 = 1.7976931348623157E+308D;
/*      */     else
/*  903 */       d1 = paramDuration1.toMillis() / 1000.0D;
/*      */     double d2;
/*  908 */     if ((paramDuration2 == null) || (paramDuration2.equals(Duration.UNKNOWN)) || (paramDuration2.equals(Duration.INDEFINITE)))
/*      */     {
/*  910 */       d2 = 1.7976931348623157E+308D;
/*  911 */     } else if (paramDuration2.lessThan(Duration.ZERO))
/*  912 */       d2 = 0.0D;
/*      */     else {
/*  914 */       d2 = paramDuration2.toMillis() / 1000.0D;
/*      */     }
/*      */ 
/*  918 */     Duration localDuration = this.media.getDuration();
/*  919 */     double d3 = localDuration == Duration.UNKNOWN ? 1.7976931348623157E+308D : localDuration.toMillis() / 1000.0D;
/*      */ 
/*  923 */     double d4 = clamp(d1, 0.0D, d3);
/*  924 */     double d5 = clamp(d2, 0.0D, d3);
/*      */ 
/*  927 */     if (d4 > d5) {
/*  928 */       d5 = d4;
/*      */     }
/*      */ 
/*  931 */     return new double[] { d4, d5 };
/*      */   }
/*      */ 
/*      */   private void setStartStopTimes(Duration paramDuration1, Duration paramDuration2)
/*      */   {
/*  942 */     if (this.jfxPlayer.getDuration() == (1.0D / 0.0D)) {
/*  943 */       return;
/*      */     }
/*      */ 
/*  947 */     double[] arrayOfDouble = calculateStartStopTimes(paramDuration1, paramDuration2);
/*      */ 
/*  950 */     this.jfxPlayer.setStartTime(arrayOfDouble[0]);
/*  951 */     this.jfxPlayer.setStopTime(arrayOfDouble[1]);
/*      */   }
/*      */ 
/*      */   public final void setStartTime(Duration paramDuration)
/*      */   {
/*  978 */     startTimeProperty().set(paramDuration);
/*      */   }
/*      */ 
/*      */   public final Duration getStartTime()
/*      */   {
/*  986 */     return this.startTime == null ? Duration.ZERO : (Duration)this.startTime.get();
/*      */   }
/*      */ 
/*      */   public ObjectProperty<Duration> startTimeProperty() {
/*  990 */     if (this.startTime == null) {
/*  991 */       this.startTime = new ObjectPropertyBase()
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/*  995 */           if (MediaPlayer.this.playerReady)
/*  996 */             MediaPlayer.this.setStartStopTimes((Duration)MediaPlayer.this.startTime.get(), MediaPlayer.this.getStopTime());
/*      */           else {
/*  998 */             MediaPlayer.this.startTimeChangeRequested = true;
/*      */           }
/* 1000 */           MediaPlayer.this.calculateCycleDuration();
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/* 1005 */           return MediaPlayer.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/* 1010 */           return "startTime";
/*      */         }
/*      */       };
/*      */     }
/* 1014 */     return this.startTime;
/*      */   }
/*      */ 
/*      */   public final void setStopTime(Duration paramDuration)
/*      */   {
/* 1032 */     stopTimeProperty().set(paramDuration);
/*      */   }
/*      */ 
/*      */   public final Duration getStopTime()
/*      */   {
/* 1043 */     return this.stopTime == null ? this.media.getDuration() : (Duration)this.stopTime.get();
/*      */   }
/*      */ 
/*      */   public ObjectProperty<Duration> stopTimeProperty() {
/* 1047 */     if (this.stopTime == null) {
/* 1048 */       this.stopTime = new ObjectPropertyBase()
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/* 1052 */           if (MediaPlayer.this.playerReady)
/* 1053 */             MediaPlayer.this.setStartStopTimes(MediaPlayer.this.getStartTime(), (Duration)MediaPlayer.this.stopTime.get());
/*      */           else {
/* 1055 */             MediaPlayer.this.stopTimeChangeRequested = true;
/*      */           }
/* 1057 */           MediaPlayer.this.calculateCycleDuration();
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/* 1062 */           return MediaPlayer.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/* 1067 */           return "stopTime";
/*      */         }
/*      */       };
/*      */     }
/* 1071 */     return this.stopTime;
/*      */   }
/*      */ 
/*      */   private void setCycleDuration(Duration paramDuration)
/*      */   {
/* 1084 */     cycleDurationPropertyImpl().set(paramDuration);
/*      */   }
/*      */ 
/*      */   public final Duration getCycleDuration()
/*      */   {
/* 1092 */     return this.cycleDuration == null ? Duration.UNKNOWN : (Duration)this.cycleDuration.get();
/*      */   }
/*      */ 
/*      */   public ReadOnlyObjectProperty<Duration> cycleDurationProperty() {
/* 1096 */     return cycleDurationPropertyImpl().getReadOnlyProperty();
/*      */   }
/*      */ 
/*      */   private ReadOnlyObjectWrapper<Duration> cycleDurationPropertyImpl() {
/* 1100 */     if (this.cycleDuration == null) {
/* 1101 */       this.cycleDuration = new ReadOnlyObjectWrapper(this, "cycleDuration");
/*      */     }
/* 1103 */     return this.cycleDuration;
/*      */   }
/*      */ 
/*      */   private void calculateCycleDuration()
/*      */   {
/* 1110 */     Duration localDuration2 = this.media.getDuration();
/*      */     Duration localDuration1;
/* 1112 */     if (!getStopTime().isUnknown())
/* 1113 */       localDuration1 = getStopTime();
/*      */     else {
/* 1115 */       localDuration1 = localDuration2;
/*      */     }
/* 1117 */     if (localDuration1.greaterThan(localDuration2)) {
/* 1118 */       localDuration1 = localDuration2;
/*      */     }
/*      */ 
/* 1122 */     if (((localDuration1.isUnknown()) || (getStartTime().isUnknown()) || (getStartTime().isIndefinite())) && 
/* 1123 */       (!getCycleDuration().isUnknown())) {
/* 1124 */       setCycleDuration(Duration.UNKNOWN);
/*      */     }
/*      */ 
/* 1127 */     setCycleDuration(localDuration1.subtract(getStartTime()));
/* 1128 */     calculateTotalDuration();
/*      */   }
/*      */ 
/*      */   private void setTotalDuration(Duration paramDuration)
/*      */   {
/* 1141 */     totalDurationPropertyImpl().set(paramDuration);
/*      */   }
/*      */ 
/*      */   public final Duration getTotalDuration()
/*      */   {
/* 1149 */     return this.totalDuration == null ? Duration.UNKNOWN : (Duration)this.totalDuration.get();
/*      */   }
/*      */ 
/*      */   public ReadOnlyObjectProperty<Duration> totalDurationProperty() {
/* 1153 */     return totalDurationPropertyImpl().getReadOnlyProperty();
/*      */   }
/*      */ 
/*      */   private ReadOnlyObjectWrapper<Duration> totalDurationPropertyImpl() {
/* 1157 */     if (this.totalDuration == null) {
/* 1158 */       this.totalDuration = new ReadOnlyObjectWrapper(this, "totalDuration");
/*      */     }
/* 1160 */     return this.totalDuration;
/*      */   }
/*      */   private void calculateTotalDuration() {
/* 1163 */     if (getCycleCount() == -1)
/* 1164 */       setTotalDuration(Duration.INDEFINITE);
/* 1165 */     else if (getCycleDuration().isUnknown())
/* 1166 */       setTotalDuration(Duration.UNKNOWN);
/*      */     else
/* 1168 */       setTotalDuration(getCycleDuration().multiply(getCycleCount()));
/*      */   }
/*      */ 
/*      */   private void setCurrentTime(Duration paramDuration)
/*      */   {
/* 1182 */     currentTimePropertyImpl().set(paramDuration);
/*      */   }
/*      */ 
/*      */   public final Duration getCurrentTime()
/*      */   {
/* 1195 */     Duration localDuration = (Duration)currentTimeProperty().get();
/*      */ 
/* 1200 */     if (this.playerReady) {
/* 1201 */       double d = this.jfxPlayer.getPresentationTime();
/* 1202 */       if (d >= 0.0D) {
/* 1203 */         localDuration = Duration.seconds(d);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1212 */     return localDuration;
/*      */   }
/*      */ 
/*      */   public ReadOnlyObjectProperty<Duration> currentTimeProperty() {
/* 1216 */     return currentTimePropertyImpl().getReadOnlyProperty();
/*      */   }
/*      */ 
/*      */   private ReadOnlyObjectWrapper<Duration> currentTimePropertyImpl() {
/* 1220 */     if (this.currentTime == null) {
/* 1221 */       this.currentTime = new ReadOnlyObjectWrapper(this, "currentTime");
/* 1222 */       this.currentTime.setValue(Duration.ZERO);
/* 1223 */       updateTime();
/*      */     }
/* 1225 */     return this.currentTime;
/*      */   }
/*      */ 
/*      */   public void seek(Duration paramDuration)
/*      */   {
/* 1250 */     if ((this.playerReady) && (paramDuration != null) && (!paramDuration.isUnknown())) {
/* 1251 */       if (this.jfxPlayer.getDuration() == (1.0D / 0.0D))
/*      */         return;
/*      */       Object localObject;
/*      */       double d;
/* 1259 */       if (paramDuration.isIndefinite())
/*      */       {
/* 1261 */         localObject = this.media.getDuration();
/* 1262 */         if ((localObject == null) || (((Duration)localObject).isUnknown()) || (((Duration)localObject).isIndefinite()))
/*      */         {
/* 1265 */           localObject = Duration.millis(1.7976931348623157E+308D);
/*      */         }
/*      */ 
/* 1269 */         d = ((Duration)localObject).toMillis() / 1000.0D;
/*      */       }
/*      */       else {
/* 1272 */         d = paramDuration.toMillis() / 1000.0D;
/*      */ 
/* 1275 */         localObject = calculateStartStopTimes(getStartTime(), getStopTime());
/* 1276 */         if (d < localObject[0])
/* 1277 */           d = localObject[0];
/* 1278 */         else if (d > localObject[1]) {
/* 1279 */           d = localObject[1];
/*      */         }
/*      */       }
/*      */ 
/* 1283 */       if (!this.isUpdateTimeEnabled)
/*      */       {
/* 1286 */         localObject = getStatus();
/* 1287 */         if (((localObject == Status.PLAYING) || (localObject == Status.PAUSED)) && (getStartTime().toSeconds() <= d) && (d <= getStopTime().toSeconds()))
/*      */         {
/* 1291 */           this.isUpdateTimeEnabled = true;
/* 1292 */           setCurrentRate(getRate());
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1297 */       this.jfxPlayer.seek(d);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void setStatus(Status paramStatus)
/*      */   {
/* 1306 */     statusPropertyImpl().set(paramStatus);
/*      */   }
/*      */ 
/*      */   public final Status getStatus()
/*      */   {
/* 1314 */     return this.status == null ? Status.UNKNOWN : (Status)this.status.get();
/*      */   }
/*      */ 
/*      */   public ReadOnlyObjectProperty<Status> statusProperty() {
/* 1318 */     return statusPropertyImpl().getReadOnlyProperty();
/*      */   }
/*      */ 
/*      */   private ReadOnlyObjectWrapper<Status> statusPropertyImpl() {
/* 1322 */     if (this.status == null) {
/* 1323 */       this.status = new ReadOnlyObjectWrapper()
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/* 1328 */           if (get() == MediaPlayer.Status.PLAYING)
/* 1329 */             MediaPlayer.this.setCurrentRate(MediaPlayer.this.getRate());
/*      */           else
/* 1331 */             MediaPlayer.this.setCurrentRate(0.0D);
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/* 1337 */           return MediaPlayer.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/* 1342 */           return "status";
/*      */         }
/*      */       };
/*      */     }
/* 1346 */     return this.status;
/*      */   }
/*      */ 
/*      */   private void setBufferProgressTime(Duration paramDuration)
/*      */   {
/* 1361 */     bufferProgressTimePropertyImpl().set(paramDuration);
/*      */   }
/*      */ 
/*      */   public final Duration getBufferProgressTime()
/*      */   {
/* 1369 */     return this.bufferProgressTime == null ? null : (Duration)this.bufferProgressTime.get();
/*      */   }
/*      */ 
/*      */   public ReadOnlyObjectProperty<Duration> bufferProgressTimeProperty() {
/* 1373 */     return bufferProgressTimePropertyImpl().getReadOnlyProperty();
/*      */   }
/*      */ 
/*      */   private ReadOnlyObjectWrapper<Duration> bufferProgressTimePropertyImpl() {
/* 1377 */     if (this.bufferProgressTime == null) {
/* 1378 */       this.bufferProgressTime = new ReadOnlyObjectWrapper(this, "bufferProgressTime");
/*      */     }
/* 1380 */     return this.bufferProgressTime;
/*      */   }
/*      */ 
/*      */   public final void setCycleCount(int paramInt)
/*      */   {
/* 1401 */     cycleCountProperty().set(paramInt);
/*      */   }
/*      */ 
/*      */   public final int getCycleCount()
/*      */   {
/* 1409 */     return this.cycleCount == null ? 1 : this.cycleCount.get();
/*      */   }
/*      */ 
/*      */   public IntegerProperty cycleCountProperty() {
/* 1413 */     if (this.cycleCount == null) {
/* 1414 */       this.cycleCount = new IntegerPropertyBase(1)
/*      */       {
/*      */         public Object getBean()
/*      */         {
/* 1427 */           return MediaPlayer.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/* 1432 */           return "cycleCount";
/*      */         }
/*      */       };
/*      */     }
/* 1436 */     return this.cycleCount;
/*      */   }
/*      */ 
/*      */   private void setCurrentCount(int paramInt)
/*      */   {
/* 1449 */     currentCountPropertyImpl().set(paramInt);
/*      */   }
/*      */ 
/*      */   public final int getCurrentCount()
/*      */   {
/* 1457 */     return this.currentCount == null ? 0 : this.currentCount.get();
/*      */   }
/*      */ 
/*      */   public ReadOnlyIntegerProperty currentCountProperty() {
/* 1461 */     return currentCountPropertyImpl().getReadOnlyProperty();
/*      */   }
/*      */ 
/*      */   private ReadOnlyIntegerWrapper currentCountPropertyImpl() {
/* 1465 */     if (this.currentCount == null) {
/* 1466 */       this.currentCount = new ReadOnlyIntegerWrapper(this, "currentCount");
/*      */     }
/* 1468 */     return this.currentCount;
/*      */   }
/*      */ 
/*      */   public final void setMute(boolean paramBoolean)
/*      */   {
/* 1486 */     muteProperty().set(paramBoolean);
/*      */   }
/*      */ 
/*      */   public final boolean isMute()
/*      */   {
/* 1494 */     return this.mute == null ? false : this.mute.get();
/*      */   }
/*      */ 
/*      */   public BooleanProperty muteProperty() {
/* 1498 */     if (this.mute == null) {
/* 1499 */       this.mute = new BooleanPropertyBase()
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/* 1503 */           if (MediaPlayer.this.playerReady)
/* 1504 */             MediaPlayer.this.jfxPlayer.setMute(get());
/*      */           else
/* 1506 */             MediaPlayer.this.muteChangeRequested = true;
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/* 1512 */           return MediaPlayer.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/* 1517 */           return "mute";
/*      */         }
/*      */       };
/*      */     }
/* 1521 */     return this.mute;
/*      */   }
/*      */ 
/*      */   public final void setOnMarker(EventHandler<MediaMarkerEvent> paramEventHandler)
/*      */   {
/* 1535 */     onMarkerProperty().set(paramEventHandler);
/*      */   }
/*      */ 
/*      */   public final EventHandler<MediaMarkerEvent> getOnMarker()
/*      */   {
/* 1543 */     return this.onMarker == null ? null : (EventHandler)this.onMarker.get();
/*      */   }
/*      */ 
/*      */   public ObjectProperty<EventHandler<MediaMarkerEvent>> onMarkerProperty() {
/* 1547 */     if (this.onMarker == null) {
/* 1548 */       this.onMarker = new SimpleObjectProperty(this, "onMarker");
/*      */     }
/* 1550 */     return this.onMarker;
/*      */   }
/*      */ 
/*      */   void addView(MediaView paramMediaView) {
/* 1554 */     WeakReference localWeakReference = new WeakReference(paramMediaView);
/* 1555 */     synchronized (this.viewRefs) {
/* 1556 */       this.viewRefs.add(localWeakReference);
/*      */     }
/*      */   }
/*      */ 
/*      */   void removeView(MediaView paramMediaView) {
/* 1561 */     synchronized (this.viewRefs) {
/* 1562 */       for (WeakReference localWeakReference : this.viewRefs) {
/* 1563 */         MediaView localMediaView = (MediaView)localWeakReference.get();
/* 1564 */         if ((localMediaView != null) && (localMediaView.equals(paramMediaView)))
/* 1565 */           this.viewRefs.remove(localWeakReference);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   void handleError(final MediaException paramMediaException)
/*      */   {
/* 1573 */     Platform.runLater(new Runnable() {
/*      */       public void run() {
/* 1575 */         MediaPlayer.this.setError(paramMediaException);
/*      */ 
/* 1578 */         if ((paramMediaException.getType() == MediaException.Type.MEDIA_CORRUPTED) || (paramMediaException.getType() == MediaException.Type.MEDIA_UNSUPPORTED) || (paramMediaException.getType() == MediaException.Type.MEDIA_INACCESSIBLE) || (paramMediaException.getType() == MediaException.Type.MEDIA_UNAVAILABLE))
/*      */         {
/* 1582 */           MediaPlayer.this.media._setError(paramMediaException.getType(), paramMediaException.getMessage());
/*      */         }
/*      */       }
/*      */     });
/*      */   }
/*      */ 
/*      */   void createMediaTimer()
/*      */   {
/* 1591 */     synchronized (this.timerLock) {
/* 1592 */       if (this.mediaTimer == null) {
/* 1593 */         this.mediaTimer = new Timer(true);
/* 1594 */         this.mediaTimer.scheduleAtFixedRate(new MediaTimerTask(this), 0L, 100L);
/*      */       }
/* 1596 */       this.isUpdateTimeEnabled = true;
/*      */     }
/*      */   }
/*      */ 
/*      */   void destroyMediaTimer() {
/* 1601 */     synchronized (this.timerLock) {
/* 1602 */       if (this.mediaTimer != null) {
/* 1603 */         this.mediaTimer.cancel();
/* 1604 */         this.mediaTimer = null;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   void updateTime()
/*      */   {
/* 1611 */     if ((this.playerReady) && (this.isUpdateTimeEnabled)) {
/* 1612 */       double d1 = this.jfxPlayer.getPresentationTime();
/* 1613 */       if (d1 >= 0.0D) {
/* 1614 */         double d2 = d1 * 1000.0D;
/*      */ 
/* 1616 */         if (d2 != this.prevTimeMs) {
/* 1617 */           setCurrentTime(Duration.millis(d2));
/* 1618 */           this.prevTimeMs = d2;
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   void loopPlayback() {
/* 1625 */     seek(getStartTime());
/*      */   }
/*      */ 
/*      */   void handleRequestedChanges()
/*      */   {
/* 1634 */     if (this.rateChangeRequested) {
/* 1635 */       if (this.jfxPlayer.getDuration() != (1.0D / 0.0D)) {
/* 1636 */         this.jfxPlayer.setRate((float)clamp(getRate(), 0.0D, 8.0D));
/*      */       }
/* 1638 */       this.rateChangeRequested = false;
/*      */     }
/*      */ 
/* 1641 */     if (this.volumeChangeRequested) {
/* 1642 */       this.jfxPlayer.setVolume((float)clamp(getVolume(), 0.0D, 1.0D));
/* 1643 */       this.volumeChangeRequested = false;
/*      */     }
/*      */ 
/* 1646 */     if (this.balanceChangeRequested) {
/* 1647 */       this.jfxPlayer.setBalance((float)clamp(getBalance(), -1.0D, 1.0D));
/* 1648 */       this.balanceChangeRequested = false;
/*      */     }
/*      */ 
/* 1651 */     if ((this.startTimeChangeRequested) || (this.stopTimeChangeRequested)) {
/* 1652 */       setStartStopTimes(getStartTime(), getStopTime());
/* 1653 */       this.startTimeChangeRequested = (this.stopTimeChangeRequested = 0);
/*      */     }
/*      */ 
/* 1656 */     if (this.muteChangeRequested) {
/* 1657 */       this.jfxPlayer.setMute(isMute());
/* 1658 */       this.muteChangeRequested = false;
/*      */     }
/*      */ 
/* 1661 */     if (this.audioSpectrumNumBandsChangeRequested) {
/* 1662 */       this.jfxPlayer.getAudioSpectrum().setBandCount(clamp(getAudioSpectrumNumBands(), 2, 2147483647));
/* 1663 */       this.audioSpectrumNumBandsChangeRequested = false;
/*      */     }
/*      */ 
/* 1666 */     if (this.audioSpectrumIntervalChangeRequested) {
/* 1667 */       this.jfxPlayer.getAudioSpectrum().setInterval(clamp(getAudioSpectrumInterval(), 1.E-09D, 1.7976931348623157E+308D));
/* 1668 */       this.audioSpectrumIntervalChangeRequested = false;
/*      */     }
/*      */ 
/* 1671 */     if (this.audioSpectrumThresholdChangeRequested) {
/* 1672 */       this.jfxPlayer.getAudioSpectrum().setSensitivityThreshold(clamp(getAudioSpectrumThreshold(), -2147483648, 0));
/* 1673 */       this.audioSpectrumThresholdChangeRequested = false;
/*      */     }
/*      */ 
/* 1676 */     if (this.audioSpectrumEnabledChangeRequested) {
/* 1677 */       boolean bool = getAudioSpectrumListener() != null;
/* 1678 */       this.jfxPlayer.getAudioSpectrum().setEnabled(bool);
/* 1679 */       this.audioSpectrumEnabledChangeRequested = false;
/*      */     }
/*      */ 
/* 1682 */     if (this.playRequested) {
/* 1683 */       this.jfxPlayer.play();
/* 1684 */       this.playRequested = false;
/*      */     }
/*      */   }
/*      */ 
/*      */   void preReady()
/*      */   {
/*      */     Iterator localIterator;
/*      */     Object localObject1;
/* 1694 */     synchronized (this.viewRefs) {
/* 1695 */       for (localIterator = this.viewRefs.iterator(); localIterator.hasNext(); ) { localObject1 = (WeakReference)localIterator.next();
/* 1696 */         MediaView localMediaView = (MediaView)((WeakReference)localObject1).get();
/* 1697 */         if (localMediaView != null) {
/* 1698 */           localMediaView._mediaPlayerOnReady();
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1704 */     if (this.audioEqualizer != null) {
/* 1705 */       this.audioEqualizer.setAudioEqualizer(this.jfxPlayer.getEqualizer());
/*      */     }
/*      */ 
/* 1709 */     double d1 = this.jfxPlayer.getDuration();
/*      */ 
/* 1711 */     if ((d1 >= 0.0D) && (!Double.isNaN(d1)))
/* 1712 */       localObject1 = Duration.millis(d1 * 1000.0D);
/*      */     else {
/* 1714 */       localObject1 = Duration.UNKNOWN;
/*      */     }
/*      */ 
/* 1717 */     this.playerReady = true;
/*      */ 
/* 1719 */     this.media.setDuration((Duration)localObject1);
/* 1720 */     this.media._updateMedia(this.jfxPlayer.getMedia());
/*      */ 
/* 1724 */     handleRequestedChanges();
/*      */ 
/* 1727 */     calculateCycleDuration();
/*      */ 
/* 1730 */     if ((this.lastBufferEvent != null) && (((Duration)localObject1).toMillis() > 0.0D)) {
/* 1731 */       double d2 = this.lastBufferEvent.getBufferPosition();
/* 1732 */       double d3 = this.lastBufferEvent.getBufferStop();
/* 1733 */       double d4 = d2 / d3 * ((Duration)localObject1).toMillis();
/* 1734 */       this.lastBufferEvent = null;
/* 1735 */       setBufferProgressTime(Duration.millis(d4));
/*      */     }
/*      */ 
/* 1739 */     if (getOnReady() != null)
/* 1740 */       Platform.runLater(getOnReady());
/*      */   }
/*      */ 
/*      */   public final void setOnEndOfMedia(Runnable paramRunnable)
/*      */   {
/* 1754 */     onEndOfMediaProperty().set(paramRunnable);
/*      */   }
/*      */ 
/*      */   public final Runnable getOnEndOfMedia()
/*      */   {
/* 1762 */     return this.onEndOfMedia == null ? null : (Runnable)this.onEndOfMedia.get();
/*      */   }
/*      */ 
/*      */   public ObjectProperty<Runnable> onEndOfMediaProperty() {
/* 1766 */     if (this.onEndOfMedia == null) {
/* 1767 */       this.onEndOfMedia = new SimpleObjectProperty(this, "onEndOfMedia");
/*      */     }
/* 1769 */     return this.onEndOfMedia;
/*      */   }
/*      */ 
/*      */   public final void setOnReady(Runnable paramRunnable)
/*      */   {
/* 1783 */     onReadyProperty().set(paramRunnable);
/*      */   }
/*      */ 
/*      */   public final Runnable getOnReady()
/*      */   {
/* 1791 */     return this.onReady == null ? null : (Runnable)this.onReady.get();
/*      */   }
/*      */ 
/*      */   public ObjectProperty<Runnable> onReadyProperty() {
/* 1795 */     if (this.onReady == null) {
/* 1796 */       this.onReady = new SimpleObjectProperty(this, "onReady");
/*      */     }
/* 1798 */     return this.onReady;
/*      */   }
/*      */ 
/*      */   public final void setOnPlaying(Runnable paramRunnable)
/*      */   {
/* 1812 */     onPlayingProperty().set(paramRunnable);
/*      */   }
/*      */ 
/*      */   public final Runnable getOnPlaying()
/*      */   {
/* 1820 */     return this.onPlaying == null ? null : (Runnable)this.onPlaying.get();
/*      */   }
/*      */ 
/*      */   public ObjectProperty<Runnable> onPlayingProperty() {
/* 1824 */     if (this.onPlaying == null) {
/* 1825 */       this.onPlaying = new SimpleObjectProperty(this, "onPlaying");
/*      */     }
/* 1827 */     return this.onPlaying;
/*      */   }
/*      */ 
/*      */   public final void setOnPaused(Runnable paramRunnable)
/*      */   {
/* 1840 */     onPausedProperty().set(paramRunnable);
/*      */   }
/*      */ 
/*      */   public final Runnable getOnPaused()
/*      */   {
/* 1848 */     return this.onPaused == null ? null : (Runnable)this.onPaused.get();
/*      */   }
/*      */ 
/*      */   public ObjectProperty<Runnable> onPausedProperty() {
/* 1852 */     if (this.onPaused == null) {
/* 1853 */       this.onPaused = new SimpleObjectProperty(this, "onPaused");
/*      */     }
/* 1855 */     return this.onPaused;
/*      */   }
/*      */ 
/*      */   public final void setOnStopped(Runnable paramRunnable)
/*      */   {
/* 1869 */     onStoppedProperty().set(paramRunnable);
/*      */   }
/*      */ 
/*      */   public final Runnable getOnStopped()
/*      */   {
/* 1877 */     return this.onStopped == null ? null : (Runnable)this.onStopped.get();
/*      */   }
/*      */ 
/*      */   public ObjectProperty<Runnable> onStoppedProperty() {
/* 1881 */     if (this.onStopped == null) {
/* 1882 */       this.onStopped = new SimpleObjectProperty(this, "onStopped");
/*      */     }
/* 1884 */     return this.onStopped;
/*      */   }
/*      */ 
/*      */   public final void setOnHalted(Runnable paramRunnable)
/*      */   {
/* 1897 */     onHaltedProperty().set(paramRunnable);
/*      */   }
/*      */ 
/*      */   public final Runnable getOnHalted()
/*      */   {
/* 1905 */     return this.onHalted == null ? null : (Runnable)this.onHalted.get();
/*      */   }
/*      */ 
/*      */   public ObjectProperty<Runnable> onHaltedProperty() {
/* 1909 */     if (this.onHalted == null) {
/* 1910 */       this.onHalted = new SimpleObjectProperty(this, "onHalted");
/*      */     }
/* 1912 */     return this.onHalted;
/*      */   }
/*      */ 
/*      */   public final void setOnRepeat(Runnable paramRunnable)
/*      */   {
/* 1928 */     onRepeatProperty().set(paramRunnable);
/*      */   }
/*      */ 
/*      */   public final Runnable getOnRepeat()
/*      */   {
/* 1936 */     return this.onRepeat == null ? null : (Runnable)this.onRepeat.get();
/*      */   }
/*      */ 
/*      */   public ObjectProperty<Runnable> onRepeatProperty() {
/* 1940 */     if (this.onRepeat == null) {
/* 1941 */       this.onRepeat = new SimpleObjectProperty(this, "onRepeat");
/*      */     }
/* 1943 */     return this.onRepeat;
/*      */   }
/*      */ 
/*      */   public final void setOnStalled(Runnable paramRunnable)
/*      */   {
/* 1957 */     onStalledProperty().set(paramRunnable);
/*      */   }
/*      */ 
/*      */   public final Runnable getOnStalled()
/*      */   {
/* 1965 */     return this.onStalled == null ? null : (Runnable)this.onStalled.get();
/*      */   }
/*      */ 
/*      */   public ObjectProperty<Runnable> onStalledProperty() {
/* 1969 */     if (this.onStalled == null) {
/* 1970 */       this.onStalled = new SimpleObjectProperty(this, "onStalled");
/*      */     }
/* 1972 */     return this.onStalled;
/*      */   }
/*      */ 
/*      */   public final void setAudioSpectrumNumBands(int paramInt)
/*      */   {
/* 1995 */     audioSpectrumNumBandsProperty().setValue(Integer.valueOf(paramInt));
/*      */   }
/*      */ 
/*      */   public final int getAudioSpectrumNumBands()
/*      */   {
/* 2003 */     return audioSpectrumNumBandsProperty().getValue().intValue();
/*      */   }
/*      */ 
/*      */   public IntegerProperty audioSpectrumNumBandsProperty() {
/* 2007 */     if (this.audioSpectrumNumBands == null) {
/* 2008 */       this.audioSpectrumNumBands = new IntegerPropertyBase(128)
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/* 2012 */           if (MediaPlayer.this.playerReady)
/* 2013 */             MediaPlayer.this.jfxPlayer.getAudioSpectrum().setBandCount(MediaPlayer.clamp(MediaPlayer.this.audioSpectrumNumBands.get(), 2, 2147483647));
/*      */           else
/* 2015 */             MediaPlayer.this.audioSpectrumNumBandsChangeRequested = true;
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/* 2021 */           return MediaPlayer.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/* 2026 */           return "audioSpectrumNumBands";
/*      */         }
/*      */       };
/*      */     }
/* 2030 */     return this.audioSpectrumNumBands;
/*      */   }
/*      */ 
/*      */   public final void setAudioSpectrumInterval(double paramDouble)
/*      */   {
/* 2044 */     audioSpectrumIntervalProperty().set(paramDouble);
/*      */   }
/*      */ 
/*      */   public final double getAudioSpectrumInterval()
/*      */   {
/* 2052 */     return audioSpectrumIntervalProperty().get();
/*      */   }
/*      */ 
/*      */   public DoubleProperty audioSpectrumIntervalProperty() {
/* 2056 */     if (this.audioSpectrumInterval == null) {
/* 2057 */       this.audioSpectrumInterval = new DoublePropertyBase(0.1D)
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/* 2061 */           if (MediaPlayer.this.playerReady)
/* 2062 */             MediaPlayer.this.jfxPlayer.getAudioSpectrum().setInterval(MediaPlayer.clamp(MediaPlayer.this.audioSpectrumInterval.get(), 1.E-09D, 1.7976931348623157E+308D));
/*      */           else
/* 2064 */             MediaPlayer.this.audioSpectrumIntervalChangeRequested = true;
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/* 2070 */           return MediaPlayer.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/* 2075 */           return "audioSpectrumInterval";
/*      */         }
/*      */       };
/*      */     }
/* 2079 */     return this.audioSpectrumInterval;
/*      */   }
/*      */ 
/*      */   public final void setAudioSpectrumThreshold(int paramInt)
/*      */   {
/* 2095 */     audioSpectrumThresholdProperty().set(paramInt);
/*      */   }
/*      */ 
/*      */   public final int getAudioSpectrumThreshold()
/*      */   {
/* 2103 */     return audioSpectrumThresholdProperty().get();
/*      */   }
/*      */ 
/*      */   public IntegerProperty audioSpectrumThresholdProperty() {
/* 2107 */     if (this.audioSpectrumThreshold == null) {
/* 2108 */       this.audioSpectrumThreshold = new IntegerPropertyBase(-60)
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/* 2112 */           if (MediaPlayer.this.playerReady)
/* 2113 */             MediaPlayer.this.jfxPlayer.getAudioSpectrum().setSensitivityThreshold(MediaPlayer.clamp(MediaPlayer.this.audioSpectrumThreshold.get(), -2147483648, 0));
/*      */           else
/* 2115 */             MediaPlayer.this.audioSpectrumThresholdChangeRequested = true;
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/* 2121 */           return MediaPlayer.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/* 2126 */           return "audioSpectrumThreshold";
/*      */         }
/*      */       };
/*      */     }
/* 2130 */     return this.audioSpectrumThreshold;
/*      */   }
/*      */ 
/*      */   public final void setAudioSpectrumListener(AudioSpectrumListener paramAudioSpectrumListener)
/*      */   {
/* 2150 */     audioSpectrumListenerProperty().set(paramAudioSpectrumListener);
/*      */   }
/*      */ 
/*      */   public final AudioSpectrumListener getAudioSpectrumListener()
/*      */   {
/* 2158 */     return (AudioSpectrumListener)audioSpectrumListenerProperty().get();
/*      */   }
/*      */ 
/*      */   public ObjectProperty<AudioSpectrumListener> audioSpectrumListenerProperty() {
/* 2162 */     if (this.audioSpectrumListener == null) {
/* 2163 */       this.audioSpectrumListener = new ObjectPropertyBase()
/*      */       {
/*      */         protected void invalidated()
/*      */         {
/* 2167 */           if (MediaPlayer.this.playerReady) {
/* 2168 */             boolean bool = MediaPlayer.this.audioSpectrumListener.get() != null;
/* 2169 */             MediaPlayer.this.jfxPlayer.getAudioSpectrum().setEnabled(bool);
/*      */           } else {
/* 2171 */             MediaPlayer.this.audioSpectrumEnabledChangeRequested = true;
/*      */           }
/*      */         }
/*      */ 
/*      */         public Object getBean()
/*      */         {
/* 2177 */           return MediaPlayer.this;
/*      */         }
/*      */ 
/*      */         public String getName()
/*      */         {
/* 2182 */           return "audioSpectrumListener";
/*      */         }
/*      */       };
/*      */     }
/* 2186 */     return this.audioSpectrumListener;
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public VideoDataBuffer impl_getLatestFrame()
/*      */   {
/* 2547 */     return this.currentRenderFrame;
/*      */   }
/*      */ 
/*      */   private class RendererListener implements VideoRendererListener, TKPulseListener
/*      */   {
/*      */     boolean updateMediaViews;
/*      */ 
/*      */     private RendererListener() {
/*      */     }
/*      */ 
/*      */     public void videoFrameUpdated(NewFrameEvent paramNewFrameEvent) {
/* 2558 */       VideoDataBuffer localVideoDataBuffer = paramNewFrameEvent.getFrameData();
/* 2559 */       if (null != localVideoDataBuffer) {
/* 2560 */         this.updateMediaViews = true;
/*      */ 
/* 2562 */         synchronized (MediaPlayer.this.renderLock) {
/* 2563 */           localVideoDataBuffer.holdFrame();
/*      */ 
/* 2566 */           if (null != MediaPlayer.this.nextRenderFrame) {
/* 2567 */             MediaPlayer.this.nextRenderFrame.releaseFrame();
/*      */           }
/* 2569 */           MediaPlayer.this.nextRenderFrame = localVideoDataBuffer;
/*      */         }
/*      */ 
/* 2572 */         Toolkit.getToolkit().requestNextPulse();
/*      */       }
/*      */     }
/*      */ 
/*      */     public void releaseVideoFrames()
/*      */     {
/* 2578 */       synchronized (MediaPlayer.this.renderLock) {
/* 2579 */         if (null != MediaPlayer.this.currentRenderFrame) {
/* 2580 */           MediaPlayer.this.currentRenderFrame.releaseFrame();
/* 2581 */           MediaPlayer.this.currentRenderFrame = null;
/*      */         }
/*      */ 
/* 2584 */         if (null != MediaPlayer.this.nextRenderFrame) {
/* 2585 */           MediaPlayer.this.nextRenderFrame.releaseFrame();
/* 2586 */           MediaPlayer.this.nextRenderFrame = null;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*      */     public void pulse()
/*      */     {
/* 2593 */       if (this.updateMediaViews) {
/* 2594 */         this.updateMediaViews = false;
/*      */ 
/* 2600 */         synchronized (MediaPlayer.this.renderLock) {
/* 2601 */           if (null != MediaPlayer.this.nextRenderFrame) {
/* 2602 */             if (null != MediaPlayer.this.currentRenderFrame) {
/* 2603 */               MediaPlayer.this.currentRenderFrame.releaseFrame();
/*      */             }
/* 2605 */             MediaPlayer.this.currentRenderFrame = MediaPlayer.this.nextRenderFrame;
/* 2606 */             MediaPlayer.this.nextRenderFrame = null;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 2611 */         synchronized (MediaPlayer.this.viewRefs) {
/* 2612 */           Iterator localIterator = MediaPlayer.this.viewRefs.iterator();
/* 2613 */           while (localIterator.hasNext()) {
/* 2614 */             MediaView localMediaView = (MediaView)((WeakReference)localIterator.next()).get();
/* 2615 */             if (null != localMediaView)
/* 2616 */               localMediaView.notifyMediaFrameUpdated();
/*      */             else
/* 2618 */               localIterator.remove();
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private class _SpectrumListener
/*      */     implements com.sun.media.jfxmedia.events.AudioSpectrumListener
/*      */   {
/*      */     private _SpectrumListener()
/*      */     {
/*      */     }
/*      */ 
/*      */     public void onAudioSpectrumEvent(final AudioSpectrumEvent paramAudioSpectrumEvent)
/*      */     {
/* 2522 */       Platform.runLater(new Runnable() {
/*      */         public void run() {
/* 2524 */           AudioSpectrumListener localAudioSpectrumListener = MediaPlayer.this.getAudioSpectrumListener();
/* 2525 */           if (localAudioSpectrumListener != null)
/* 2526 */             localAudioSpectrumListener.spectrumDataUpdate(paramAudioSpectrumEvent.getTimestamp(), paramAudioSpectrumEvent.getDuration(), paramAudioSpectrumEvent.getSource().getMagnitudes(), paramAudioSpectrumEvent.getSource().getPhases());
/*      */         }
/*      */       });
/*      */     }
/*      */   }
/*      */ 
/*      */   private class _BufferListener
/*      */     implements BufferListener
/*      */   {
/*      */     double bufferedTime;
/*      */ 
/*      */     private _BufferListener()
/*      */     {
/*      */     }
/*      */ 
/*      */     public void onBufferProgress(BufferProgressEvent paramBufferProgressEvent)
/*      */     {
/* 2501 */       if (MediaPlayer.this.media != null)
/* 2502 */         if (paramBufferProgressEvent.getDuration() > 0.0D) {
/* 2503 */           double d1 = paramBufferProgressEvent.getBufferPosition();
/* 2504 */           double d2 = paramBufferProgressEvent.getBufferStop();
/* 2505 */           this.bufferedTime = (d1 / d2 * paramBufferProgressEvent.getDuration() * 1000.0D);
/* 2506 */           MediaPlayer.this.lastBufferEvent = null;
/*      */ 
/* 2508 */           Platform.runLater(new Runnable() {
/*      */             public void run() {
/* 2510 */               MediaPlayer.this.setBufferProgressTime(Duration.millis(MediaPlayer._BufferListener.this.bufferedTime));
/*      */             } } );
/*      */         }
/*      */         else {
/* 2514 */           MediaPlayer.this.lastBufferEvent = paramBufferProgressEvent;
/*      */         }
/*      */     }
/*      */   }
/*      */ 
/*      */   private class _MediaErrorListener
/*      */     implements MediaErrorListener
/*      */   {
/*      */     private _MediaErrorListener()
/*      */     {
/*      */     }
/*      */ 
/*      */     public void onError(Object paramObject, int paramInt, String paramString)
/*      */     {
/* 2490 */       MediaException localMediaException = MediaException.getMediaException(paramObject, paramInt, paramString);
/*      */ 
/* 2492 */       MediaPlayer.this.handleError(localMediaException);
/*      */     }
/*      */   }
/*      */ 
/*      */   private class _VideoTrackSizeListener
/*      */     implements VideoTrackSizeListener
/*      */   {
/*      */     int trackWidth;
/*      */     int trackHeight;
/*      */ 
/*      */     private _VideoTrackSizeListener()
/*      */     {
/*      */     }
/*      */ 
/*      */     public void onSizeChanged(final int paramInt1, final int paramInt2)
/*      */     {
/* 2459 */       Platform.runLater(new Runnable()
/*      */       {
/*      */         public void run()
/*      */         {
/* 2463 */           if (MediaPlayer.this.media != null) {
/* 2464 */             MediaPlayer._VideoTrackSizeListener.this.trackWidth = paramInt1;
/* 2465 */             MediaPlayer._VideoTrackSizeListener.this.trackHeight = paramInt2;
/* 2466 */             MediaPlayer._VideoTrackSizeListener.this.setSize();
/*      */           }
/*      */         }
/*      */       });
/*      */     }
/*      */ 
/*      */     void setSize() {
/* 2473 */       MediaPlayer.this.media.setWidth(this.trackWidth);
/* 2474 */       MediaPlayer.this.media.setHeight(this.trackHeight);
/*      */ 
/* 2476 */       synchronized (MediaPlayer.this.viewRefs) {
/* 2477 */         for (WeakReference localWeakReference : MediaPlayer.this.viewRefs) {
/* 2478 */           MediaView localMediaView = (MediaView)localWeakReference.get();
/* 2479 */           if (localMediaView != null)
/* 2480 */             localMediaView.notifyMediaSizeChange();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private class _PlayerTimeListener
/*      */     implements PlayerTimeListener
/*      */   {
/*      */     double theDuration;
/*      */ 
/*      */     private _PlayerTimeListener()
/*      */     {
/*      */     }
/*      */ 
/*      */     void handleStopReached()
/*      */     {
/* 2397 */       MediaPlayer.this.setCurrentCount(MediaPlayer.this.getCurrentCount() + 1);
/*      */ 
/* 2401 */       if ((MediaPlayer.this.getCurrentCount() < MediaPlayer.this.getCycleCount()) || (MediaPlayer.this.getCycleCount() == -1)) {
/* 2402 */         if (MediaPlayer.this.getOnEndOfMedia() != null) {
/* 2403 */           Platform.runLater(MediaPlayer.this.getOnEndOfMedia());
/*      */         }
/*      */ 
/* 2406 */         MediaPlayer.this.loopPlayback();
/*      */ 
/* 2408 */         if (MediaPlayer.this.getOnRepeat() != null) {
/* 2409 */           Platform.runLater(MediaPlayer.this.getOnRepeat());
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 2415 */         MediaPlayer.this.isUpdateTimeEnabled = false;
/*      */ 
/* 2417 */         if (MediaPlayer.this.getOnEndOfMedia() != null)
/* 2418 */           Platform.runLater(MediaPlayer.this.getOnEndOfMedia());
/*      */       }
/*      */     }
/*      */ 
/*      */     void handleDurationChanged()
/*      */     {
/* 2424 */       MediaPlayer.this.media.setDuration(Duration.millis(this.theDuration * 1000.0D));
/*      */     }
/*      */ 
/*      */     public void onStopTimeReached(double paramDouble)
/*      */     {
/* 2430 */       Platform.runLater(new Runnable()
/*      */       {
/*      */         public void run()
/*      */         {
/* 2434 */           MediaPlayer._PlayerTimeListener.this.handleStopReached();
/*      */         }
/*      */       });
/*      */     }
/*      */ 
/*      */     public void onDurationChanged(final double paramDouble)
/*      */     {
/* 2442 */       Platform.runLater(new Runnable()
/*      */       {
/*      */         public void run()
/*      */         {
/* 2446 */           MediaPlayer._PlayerTimeListener.this.theDuration = paramDouble;
/* 2447 */           MediaPlayer._PlayerTimeListener.this.handleDurationChanged();
/*      */         }
/*      */       });
/*      */     }
/*      */   }
/*      */ 
/*      */   private class _PlayerStateListener
/*      */     implements PlayerStateListener
/*      */   {
/*      */     private _PlayerStateListener()
/*      */     {
/*      */     }
/*      */ 
/*      */     public void onReady(PlayerStateEvent paramPlayerStateEvent)
/*      */     {
/* 2248 */       Platform.runLater(new Runnable() {
/*      */         public void run() {
/* 2250 */           MediaPlayer.this.setStatus(MediaPlayer.Status.READY);
/* 2251 */           MediaPlayer.this.preReady();
/*      */         }
/*      */       });
/*      */     }
/*      */ 
/*      */     public void onPlaying(PlayerStateEvent paramPlayerStateEvent)
/*      */     {
/* 2259 */       Platform.runLater(new Runnable() {
/*      */         public void run() {
/* 2261 */           MediaPlayer.this.setStatus(MediaPlayer.Status.PLAYING);
/* 2262 */           MediaPlayer.this.createMediaTimer();
/* 2263 */           if (MediaPlayer.this.getOnPlaying() != null)
/* 2264 */             MediaPlayer.this.getOnPlaying().run();
/*      */         }
/*      */       });
/*      */     }
/*      */ 
/*      */     public void onPause(PlayerStateEvent paramPlayerStateEvent)
/*      */     {
/* 2272 */       Platform.runLater(new Runnable()
/*      */       {
/*      */         public void run()
/*      */         {
/* 2276 */           MediaPlayer.this.setStatus(MediaPlayer.Status.PAUSED);
/*      */ 
/* 2279 */           MediaPlayer.this.isUpdateTimeEnabled = false;
/*      */ 
/* 2281 */           if (MediaPlayer.this.getOnPaused() != null)
/* 2282 */             Platform.runLater(MediaPlayer.this.getOnPaused());
/*      */         }
/*      */       });
/*      */     }
/*      */ 
/*      */     public void onStop(PlayerStateEvent paramPlayerStateEvent)
/*      */     {
/* 2291 */       Platform.runLater(new Runnable()
/*      */       {
/*      */         public void run()
/*      */         {
/* 2295 */           MediaPlayer.this.setStatus(MediaPlayer.Status.STOPPED);
/*      */ 
/* 2298 */           MediaPlayer.this.destroyMediaTimer();
/*      */ 
/* 2300 */           MediaPlayer.this.isUpdateTimeEnabled = true;
/* 2301 */           MediaPlayer.this.updateTime();
/*      */ 
/* 2303 */           MediaPlayer.this.isUpdateTimeEnabled = false;
/*      */ 
/* 2305 */           if (MediaPlayer.this.getOnStopped() != null)
/* 2306 */             Platform.runLater(MediaPlayer.this.getOnStopped());
/*      */         }
/*      */       });
/*      */     }
/*      */ 
/*      */     public void onStall(PlayerStateEvent paramPlayerStateEvent)
/*      */     {
/* 2315 */       Platform.runLater(new Runnable()
/*      */       {
/*      */         public void run()
/*      */         {
/* 2319 */           MediaPlayer.this.setStatus(MediaPlayer.Status.STALLED);
/*      */ 
/* 2322 */           MediaPlayer.this.isUpdateTimeEnabled = false;
/*      */ 
/* 2324 */           if (MediaPlayer.this.getOnStalled() != null)
/* 2325 */             Platform.runLater(MediaPlayer.this.getOnStalled());
/*      */         }
/*      */       });
/*      */     }
/*      */ 
/*      */     void handleFinish()
/*      */     {
/* 2335 */       MediaPlayer.this.setCurrentCount(MediaPlayer.this.getCurrentCount() + 1);
/*      */ 
/* 2339 */       if ((MediaPlayer.this.getCurrentCount() < MediaPlayer.this.getCycleCount()) || (MediaPlayer.this.getCycleCount() == -1)) {
/* 2340 */         if (MediaPlayer.this.getOnEndOfMedia() != null) {
/* 2341 */           Platform.runLater(MediaPlayer.this.getOnEndOfMedia());
/*      */         }
/*      */ 
/* 2344 */         MediaPlayer.this.loopPlayback();
/*      */ 
/* 2346 */         if (MediaPlayer.this.getOnRepeat() != null) {
/* 2347 */           Platform.runLater(MediaPlayer.this.getOnRepeat());
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 2353 */         MediaPlayer.this.isUpdateTimeEnabled = false;
/*      */ 
/* 2356 */         MediaPlayer.this.setCurrentRate(0.0D);
/*      */ 
/* 2358 */         if (MediaPlayer.this.getOnEndOfMedia() != null)
/* 2359 */           Platform.runLater(MediaPlayer.this.getOnEndOfMedia());
/*      */       }
/*      */     }
/*      */ 
/*      */     public void onFinish(PlayerStateEvent paramPlayerStateEvent)
/*      */     {
/* 2367 */       Platform.runLater(new Runnable()
/*      */       {
/*      */         public void run()
/*      */         {
/* 2371 */           MediaPlayer._PlayerStateListener.this.handleFinish();
/*      */         }
/*      */       });
/*      */     }
/*      */ 
/*      */     public void onHalt(final PlayerStateEvent paramPlayerStateEvent)
/*      */     {
/* 2378 */       Platform.runLater(new Runnable()
/*      */       {
/*      */         public void run()
/*      */         {
/* 2382 */           MediaPlayer.this.setStatus(MediaPlayer.Status.HALTED);
/* 2383 */           MediaPlayer.this.handleError(MediaException.haltException(paramPlayerStateEvent.getMessage()));
/*      */ 
/* 2386 */           MediaPlayer.this.isUpdateTimeEnabled = false;
/*      */         }
/*      */       });
/*      */     }
/*      */   }
/*      */ 
/*      */   private class _MarkerListener
/*      */     implements MarkerListener
/*      */   {
/*      */     private _MarkerListener()
/*      */     {
/*      */     }
/*      */ 
/*      */     public void onMarker(final MarkerEvent paramMarkerEvent)
/*      */     {
/* 2231 */       Platform.runLater(new Runnable()
/*      */       {
/*      */         public void run()
/*      */         {
/* 2235 */           Duration localDuration = Duration.millis(paramMarkerEvent.getPresentationTime() * 1000.0D);
/* 2236 */           if (MediaPlayer.this.getOnMarker() != null)
/* 2237 */             MediaPlayer.this.getOnMarker().handle(new MediaMarkerEvent(new Pair(paramMarkerEvent.getMarkerName(), localDuration)));
/*      */         }
/*      */       });
/*      */     }
/*      */   }
/*      */ 
/*      */   private class MarkerMapChangeListener
/*      */     implements MapChangeListener<String, Duration>
/*      */   {
/*      */     private MarkerMapChangeListener()
/*      */     {
/*      */     }
/*      */ 
/*      */     public void onChanged(MapChangeListener.Change<? extends String, ? extends Duration> paramChange)
/*      */     {
/* 2198 */       String str = (String)paramChange.getKey();
/*      */ 
/* 2200 */       if (str == null) {
/* 2201 */         return;
/*      */       }
/* 2203 */       com.sun.media.jfxmedia.Media localMedia = MediaPlayer.this.jfxPlayer.getMedia();
/* 2204 */       if (paramChange.wasAdded()) {
/* 2205 */         if (paramChange.wasRemoved())
/*      */         {
/* 2210 */           localMedia.removeMarker(str);
/*      */         }
/* 2212 */         Duration localDuration = (Duration)paramChange.getValueAdded();
/*      */ 
/* 2214 */         if ((localDuration != null) && (localDuration.greaterThanOrEqualTo(Duration.ZERO)))
/* 2215 */           localMedia.addMarker(str, ((Duration)paramChange.getValueAdded()).toMillis() / 1000.0D);
/*      */       }
/* 2217 */       else if (paramChange.wasRemoved()) {
/* 2218 */         localMedia.removeMarker(str);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private class InitMediaPlayer
/*      */     implements Runnable
/*      */   {
/*      */     private InitMediaPlayer()
/*      */     {
/*      */     }
/*      */ 
/*      */     public void run()
/*      */     {
/*      */       try
/*      */       {
/*  462 */         MediaPlayer.this.init();
/*      */       } catch (com.sun.media.jfxmedia.MediaException localMediaException) {
/*  464 */         MediaPlayer.this.handleError(MediaException.exceptionToMediaException(localMediaException));
/*      */       } catch (MediaException localMediaException1) {
/*  466 */         MediaPlayer.this.handleError(localMediaException1);
/*      */       } catch (Exception localException) {
/*  468 */         MediaPlayer.this.handleError(new MediaException(MediaException.Type.UNKNOWN, localException.getMessage()));
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public static enum Status
/*      */   {
/*  192 */     UNKNOWN, 
/*      */ 
/*  197 */     READY, 
/*      */ 
/*  202 */     PAUSED, 
/*      */ 
/*  206 */     PLAYING, 
/*      */ 
/*  211 */     STOPPED, 
/*      */ 
/*  220 */     STALLED, 
/*      */ 
/*  226 */     HALTED;
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.media.MediaPlayer
 * JD-Core Version:    0.6.2
 */