/*     */ package javafx.scene.media;
/*     */ 
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.util.Builder;
/*     */ import javafx.util.Duration;
/*     */ 
/*     */ public final class MediaPlayerBuilder
/*     */   implements Builder<MediaPlayer>
/*     */ {
/*     */   private int __set;
/*     */   private double audioSpectrumInterval;
/*     */   private AudioSpectrumListener audioSpectrumListener;
/*     */   private int audioSpectrumNumBands;
/*     */   private int audioSpectrumThreshold;
/*     */   private boolean autoPlay;
/*     */   private double balance;
/*     */   private int cycleCount;
/*     */   private Media media;
/*     */   private boolean mute;
/*     */   private Runnable onEndOfMedia;
/*     */   private Runnable onError;
/*     */   private Runnable onHalted;
/*     */   private EventHandler<MediaMarkerEvent> onMarker;
/*     */   private Runnable onPaused;
/*     */   private Runnable onPlaying;
/*     */   private Runnable onReady;
/*     */   private Runnable onRepeat;
/*     */   private Runnable onStalled;
/*     */   private Runnable onStopped;
/*     */   private double rate;
/*     */   private Duration startTime;
/*     */   private Duration stopTime;
/*     */   private double volume;
/*     */ 
/*     */   public static MediaPlayerBuilder create()
/*     */   {
/*  15 */     return new MediaPlayerBuilder();
/*     */   }
/*     */ 
/*     */   private void __set(int paramInt)
/*     */   {
/*  20 */     this.__set |= 1 << paramInt;
/*     */   }
/*     */   public void applyTo(MediaPlayer paramMediaPlayer) {
/*  23 */     int i = this.__set;
/*  24 */     while (i != 0) {
/*  25 */       int j = Integer.numberOfTrailingZeros(i);
/*  26 */       i &= (1 << j ^ 0xFFFFFFFF);
/*  27 */       switch (j) { case 0:
/*  28 */         paramMediaPlayer.setAudioSpectrumInterval(this.audioSpectrumInterval); break;
/*     */       case 1:
/*  29 */         paramMediaPlayer.setAudioSpectrumListener(this.audioSpectrumListener); break;
/*     */       case 2:
/*  30 */         paramMediaPlayer.setAudioSpectrumNumBands(this.audioSpectrumNumBands); break;
/*     */       case 3:
/*  31 */         paramMediaPlayer.setAudioSpectrumThreshold(this.audioSpectrumThreshold); break;
/*     */       case 4:
/*  32 */         paramMediaPlayer.setAutoPlay(this.autoPlay); break;
/*     */       case 5:
/*  33 */         paramMediaPlayer.setBalance(this.balance); break;
/*     */       case 6:
/*  34 */         paramMediaPlayer.setCycleCount(this.cycleCount); break;
/*     */       case 7:
/*  35 */         paramMediaPlayer.setMute(this.mute); break;
/*     */       case 8:
/*  36 */         paramMediaPlayer.setOnEndOfMedia(this.onEndOfMedia); break;
/*     */       case 9:
/*  37 */         paramMediaPlayer.setOnError(this.onError); break;
/*     */       case 10:
/*  38 */         paramMediaPlayer.setOnHalted(this.onHalted); break;
/*     */       case 11:
/*  39 */         paramMediaPlayer.setOnMarker(this.onMarker); break;
/*     */       case 12:
/*  40 */         paramMediaPlayer.setOnPaused(this.onPaused); break;
/*     */       case 13:
/*  41 */         paramMediaPlayer.setOnPlaying(this.onPlaying); break;
/*     */       case 14:
/*  42 */         paramMediaPlayer.setOnReady(this.onReady); break;
/*     */       case 15:
/*  43 */         paramMediaPlayer.setOnRepeat(this.onRepeat); break;
/*     */       case 16:
/*  44 */         paramMediaPlayer.setOnStalled(this.onStalled); break;
/*     */       case 17:
/*  45 */         paramMediaPlayer.setOnStopped(this.onStopped); break;
/*     */       case 18:
/*  46 */         paramMediaPlayer.setRate(this.rate); break;
/*     */       case 19:
/*  47 */         paramMediaPlayer.setStartTime(this.startTime); break;
/*     */       case 20:
/*  48 */         paramMediaPlayer.setStopTime(this.stopTime); break;
/*     */       case 21:
/*  49 */         paramMediaPlayer.setVolume(this.volume);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public MediaPlayerBuilder audioSpectrumInterval(double paramDouble)
/*     */   {
/*  59 */     this.audioSpectrumInterval = paramDouble;
/*  60 */     __set(0);
/*  61 */     return this;
/*     */   }
/*     */ 
/*     */   public MediaPlayerBuilder audioSpectrumListener(AudioSpectrumListener paramAudioSpectrumListener)
/*     */   {
/*  69 */     this.audioSpectrumListener = paramAudioSpectrumListener;
/*  70 */     __set(1);
/*  71 */     return this;
/*     */   }
/*     */ 
/*     */   public MediaPlayerBuilder audioSpectrumNumBands(int paramInt)
/*     */   {
/*  79 */     this.audioSpectrumNumBands = paramInt;
/*  80 */     __set(2);
/*  81 */     return this;
/*     */   }
/*     */ 
/*     */   public MediaPlayerBuilder audioSpectrumThreshold(int paramInt)
/*     */   {
/*  89 */     this.audioSpectrumThreshold = paramInt;
/*  90 */     __set(3);
/*  91 */     return this;
/*     */   }
/*     */ 
/*     */   public MediaPlayerBuilder autoPlay(boolean paramBoolean)
/*     */   {
/*  99 */     this.autoPlay = paramBoolean;
/* 100 */     __set(4);
/* 101 */     return this;
/*     */   }
/*     */ 
/*     */   public MediaPlayerBuilder balance(double paramDouble)
/*     */   {
/* 109 */     this.balance = paramDouble;
/* 110 */     __set(5);
/* 111 */     return this;
/*     */   }
/*     */ 
/*     */   public MediaPlayerBuilder cycleCount(int paramInt)
/*     */   {
/* 119 */     this.cycleCount = paramInt;
/* 120 */     __set(6);
/* 121 */     return this;
/*     */   }
/*     */ 
/*     */   public MediaPlayerBuilder media(Media paramMedia)
/*     */   {
/* 129 */     this.media = paramMedia;
/* 130 */     return this;
/*     */   }
/*     */ 
/*     */   public MediaPlayerBuilder mute(boolean paramBoolean)
/*     */   {
/* 138 */     this.mute = paramBoolean;
/* 139 */     __set(7);
/* 140 */     return this;
/*     */   }
/*     */ 
/*     */   public MediaPlayerBuilder onEndOfMedia(Runnable paramRunnable)
/*     */   {
/* 148 */     this.onEndOfMedia = paramRunnable;
/* 149 */     __set(8);
/* 150 */     return this;
/*     */   }
/*     */ 
/*     */   public MediaPlayerBuilder onError(Runnable paramRunnable)
/*     */   {
/* 158 */     this.onError = paramRunnable;
/* 159 */     __set(9);
/* 160 */     return this;
/*     */   }
/*     */ 
/*     */   public MediaPlayerBuilder onHalted(Runnable paramRunnable)
/*     */   {
/* 168 */     this.onHalted = paramRunnable;
/* 169 */     __set(10);
/* 170 */     return this;
/*     */   }
/*     */ 
/*     */   public MediaPlayerBuilder onMarker(EventHandler<MediaMarkerEvent> paramEventHandler)
/*     */   {
/* 178 */     this.onMarker = paramEventHandler;
/* 179 */     __set(11);
/* 180 */     return this;
/*     */   }
/*     */ 
/*     */   public MediaPlayerBuilder onPaused(Runnable paramRunnable)
/*     */   {
/* 188 */     this.onPaused = paramRunnable;
/* 189 */     __set(12);
/* 190 */     return this;
/*     */   }
/*     */ 
/*     */   public MediaPlayerBuilder onPlaying(Runnable paramRunnable)
/*     */   {
/* 198 */     this.onPlaying = paramRunnable;
/* 199 */     __set(13);
/* 200 */     return this;
/*     */   }
/*     */ 
/*     */   public MediaPlayerBuilder onReady(Runnable paramRunnable)
/*     */   {
/* 208 */     this.onReady = paramRunnable;
/* 209 */     __set(14);
/* 210 */     return this;
/*     */   }
/*     */ 
/*     */   public MediaPlayerBuilder onRepeat(Runnable paramRunnable)
/*     */   {
/* 218 */     this.onRepeat = paramRunnable;
/* 219 */     __set(15);
/* 220 */     return this;
/*     */   }
/*     */ 
/*     */   public MediaPlayerBuilder onStalled(Runnable paramRunnable)
/*     */   {
/* 228 */     this.onStalled = paramRunnable;
/* 229 */     __set(16);
/* 230 */     return this;
/*     */   }
/*     */ 
/*     */   public MediaPlayerBuilder onStopped(Runnable paramRunnable)
/*     */   {
/* 238 */     this.onStopped = paramRunnable;
/* 239 */     __set(17);
/* 240 */     return this;
/*     */   }
/*     */ 
/*     */   public MediaPlayerBuilder rate(double paramDouble)
/*     */   {
/* 248 */     this.rate = paramDouble;
/* 249 */     __set(18);
/* 250 */     return this;
/*     */   }
/*     */ 
/*     */   public MediaPlayerBuilder startTime(Duration paramDuration)
/*     */   {
/* 258 */     this.startTime = paramDuration;
/* 259 */     __set(19);
/* 260 */     return this;
/*     */   }
/*     */ 
/*     */   public MediaPlayerBuilder stopTime(Duration paramDuration)
/*     */   {
/* 268 */     this.stopTime = paramDuration;
/* 269 */     __set(20);
/* 270 */     return this;
/*     */   }
/*     */ 
/*     */   public MediaPlayerBuilder volume(double paramDouble)
/*     */   {
/* 278 */     this.volume = paramDouble;
/* 279 */     __set(21);
/* 280 */     return this;
/*     */   }
/*     */ 
/*     */   public MediaPlayer build()
/*     */   {
/* 287 */     MediaPlayer localMediaPlayer = new MediaPlayer(this.media);
/* 288 */     applyTo(localMediaPlayer);
/* 289 */     return localMediaPlayer;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.media.MediaPlayerBuilder
 * JD-Core Version:    0.6.2
 */