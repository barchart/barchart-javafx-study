/*      */ package com.sun.media.jfxmediaimpl;
/*      */ 
/*      */ import com.sun.media.jfxmedia.Media;
/*      */ import com.sun.media.jfxmedia.MediaError;
/*      */ import com.sun.media.jfxmedia.MediaException;
/*      */ import com.sun.media.jfxmedia.MediaPlayer;
/*      */ import com.sun.media.jfxmedia.control.VideoDataBuffer;
/*      */ import com.sun.media.jfxmedia.control.VideoRenderControl;
/*      */ import com.sun.media.jfxmedia.effects.AudioEqualizer;
/*      */ import com.sun.media.jfxmedia.effects.AudioSpectrum;
/*      */ import com.sun.media.jfxmedia.events.AudioSpectrumEvent;
/*      */ import com.sun.media.jfxmedia.events.AudioSpectrumListener;
/*      */ import com.sun.media.jfxmedia.events.BufferListener;
/*      */ import com.sun.media.jfxmedia.events.BufferProgressEvent;
/*      */ import com.sun.media.jfxmedia.events.MarkerEvent;
/*      */ import com.sun.media.jfxmedia.events.MarkerListener;
/*      */ import com.sun.media.jfxmedia.events.MediaErrorListener;
/*      */ import com.sun.media.jfxmedia.events.NewFrameEvent;
/*      */ import com.sun.media.jfxmedia.events.PlayerEvent;
/*      */ import com.sun.media.jfxmedia.events.PlayerStateEvent;
/*      */ import com.sun.media.jfxmedia.events.PlayerStateEvent.PlayerState;
/*      */ import com.sun.media.jfxmedia.events.PlayerStateListener;
/*      */ import com.sun.media.jfxmedia.events.PlayerTimeListener;
/*      */ import com.sun.media.jfxmedia.events.VideoFrameRateListener;
/*      */ import com.sun.media.jfxmedia.events.VideoRendererListener;
/*      */ import com.sun.media.jfxmedia.events.VideoTrackSizeListener;
/*      */ import com.sun.media.jfxmedia.locator.Locator;
/*      */ import com.sun.media.jfxmedia.logging.Logger;
/*      */ import com.sun.media.jfxmedia.track.AudioTrack;
/*      */ import com.sun.media.jfxmedia.track.Track;
/*      */ import com.sun.media.jfxmedia.track.Track.Encoding;
/*      */ import com.sun.media.jfxmedia.track.VideoResolution;
/*      */ import com.sun.media.jfxmedia.track.VideoTrack;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.ListIterator;
/*      */ import java.util.Locale;
/*      */ import java.util.Map.Entry;
/*      */ import java.util.Timer;
/*      */ import java.util.concurrent.BlockingQueue;
/*      */ import java.util.concurrent.LinkedBlockingQueue;
/*      */ import java.util.concurrent.atomic.AtomicBoolean;
/*      */ import java.util.concurrent.locks.Lock;
/*      */ import java.util.concurrent.locks.ReentrantLock;
/*      */ 
/*      */ public abstract class NativeMediaPlayer
/*      */   implements MediaPlayer, MarkerStateListener
/*      */ {
/*      */   public static final int eventPlayerUnknown = 100;
/*      */   public static final int eventPlayerReady = 101;
/*      */   public static final int eventPlayerPlaying = 102;
/*      */   public static final int eventPlayerPaused = 103;
/*      */   public static final int eventPlayerStopped = 104;
/*      */   public static final int eventPlayerStalled = 105;
/*      */   public static final int eventPlayerFinished = 106;
/*      */   public static final int eventPlayerError = 107;
/*      */   private static final int NOMINAL_VIDEO_FPS = 30;
/*      */   public static final long ONE_SECOND = 1000000000L;
/*      */   private NativeMedia media;
/*      */   private VideoRenderControl videoRenderControl;
/*  130 */   private final List<WeakReference<MediaErrorListener>> errorListeners = new ArrayList();
/*  131 */   private final List<WeakReference<PlayerStateListener>> playerStateListeners = new ArrayList();
/*  132 */   private final List<WeakReference<PlayerTimeListener>> playerTimeListeners = new ArrayList();
/*  133 */   private final List<WeakReference<VideoTrackSizeListener>> videoTrackSizeListeners = new ArrayList();
/*  134 */   private final List<WeakReference<VideoRendererListener>> videoUpdateListeners = new ArrayList();
/*  135 */   private final List<WeakReference<VideoFrameRateListener>> videoFrameRateListeners = new ArrayList();
/*  136 */   private final List<WeakReference<MarkerListener>> markerListeners = new ArrayList();
/*  137 */   private final List<WeakReference<BufferListener>> bufferListeners = new ArrayList();
/*  138 */   private final List<WeakReference<AudioSpectrumListener>> audioSpectrumListeners = new ArrayList();
/*  139 */   private final List<PlayerStateEvent> cachedStateEvents = new ArrayList();
/*  140 */   private final List<PlayerTimeEvent> cachedTimeEvents = new ArrayList();
/*  141 */   private final List<BufferProgressEvent> cachedBufferEvents = new ArrayList();
/*  142 */   private final List<MediaErrorEvent> cachedErrorEvents = new ArrayList();
/*  143 */   private boolean isFirstFrame = true;
/*  144 */   private NewFrameEvent firstFrameEvent = null;
/*      */   private double firstFrameTime;
/*  146 */   private final Object firstFrameLock = new Object();
/*  147 */   private EventQueueThread eventLoop = new EventQueueThread();
/*  148 */   private int frameWidth = -1;
/*  149 */   private int frameHeight = -1;
/*  150 */   private AtomicBoolean isMediaPulseEnabled = new AtomicBoolean(false);
/*  151 */   private final Lock mediaPulseLock = new ReentrantLock();
/*      */   private Timer mediaPulseTimer;
/*  153 */   private Lock markerLock = new ReentrantLock();
/*  154 */   private boolean checkSeek = false;
/*  155 */   private double timeBeforeSeek = 0.0D;
/*  156 */   private double timeAfterSeek = 0.0D;
/*  157 */   private double previousTime = 0.0D;
/*  158 */   private double firedMarkerTime = -1.0D;
/*      */ 
/*  160 */   private double encodedFrameRate = 0.0D;
/*  161 */   private boolean recomputeFrameRate = true;
/*      */   private double previousFrameTime;
/*      */   private long numFramesSincePlaying;
/*      */   private double meanFrameDuration;
/*      */   private double decodedFrameRate;
/*  167 */   private PlayerStateEvent.PlayerState playerState = PlayerStateEvent.PlayerState.UNKNOWN;
/*  168 */   private Lock disposeLock = new ReentrantLock();
/*  169 */   private boolean isDisposed = false;
/*      */   private Runnable onDispose;
/*      */ 
/*      */   protected NativeMediaPlayer(NativeMedia clip)
/*      */   {
/*  184 */     if (clip == null) {
/*  185 */       throw new IllegalArgumentException("clip == null!");
/*      */     }
/*  187 */     this.media = clip;
/*  188 */     this.media.addMarkerStateListener(this);
/*  189 */     this.videoRenderControl = new VideoRenderer(null);
/*  190 */     this.eventLoop.start();
/*      */   }
/*      */ 
/*      */   void setOnDispose(Runnable onDispose)
/*      */   {
/*  200 */     this.disposeLock.lock();
/*      */     try {
/*  202 */       if (!this.isDisposed)
/*  203 */         this.onDispose = onDispose;
/*      */     }
/*      */     finally {
/*  206 */       this.disposeLock.unlock();
/*      */     }
/*      */   }
/*      */ 
/*      */   private synchronized void onNativeInit()
/*      */   {
/*      */     try
/*      */     {
/*  778 */       playerInit();
/*      */     }
/*      */     catch (MediaException me)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public void addMediaErrorListener(MediaErrorListener listener)
/*      */   {
/*  789 */     if (listener != null) {
/*  790 */       this.errorListeners.add(new WeakReference(listener));
/*      */ 
/*  792 */       synchronized (this.cachedErrorEvents) {
/*  793 */         if ((!this.cachedErrorEvents.isEmpty()) && (!this.errorListeners.isEmpty())) {
/*  794 */           for (MediaErrorEvent evt : this.cachedErrorEvents)
/*      */           {
/*  797 */             sendPlayerEvent(evt);
/*      */           }
/*  799 */           this.cachedErrorEvents.clear();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void removeMediaErrorListener(MediaErrorListener listener)
/*      */   {
/*      */     ListIterator it;
/*  806 */     if (listener != null)
/*  807 */       for (it = this.errorListeners.listIterator(); it.hasNext(); ) {
/*  808 */         MediaErrorListener l = (MediaErrorListener)((WeakReference)it.next()).get();
/*  809 */         if ((l == null) || (l == listener))
/*  810 */           it.remove();
/*      */       }
/*      */   }
/*      */ 
/*      */   public void addMediaPlayerListener(PlayerStateListener listener)
/*      */   {
/*  817 */     if (listener != null)
/*  818 */       synchronized (this.cachedStateEvents) {
/*  819 */         if ((!this.cachedStateEvents.isEmpty()) && (this.playerStateListeners.isEmpty()))
/*      */         {
/*  821 */           Iterator events = this.cachedStateEvents.iterator();
/*  822 */           while (events.hasNext()) {
/*  823 */             PlayerStateEvent evt = (PlayerStateEvent)events.next();
/*  824 */             switch (1.$SwitchMap$com$sun$media$jfxmedia$events$PlayerStateEvent$PlayerState[evt.getState().ordinal()]) {
/*      */             case 1:
/*  826 */               listener.onReady(evt);
/*  827 */               break;
/*      */             case 2:
/*  829 */               listener.onPlaying(evt);
/*  830 */               break;
/*      */             case 5:
/*  832 */               listener.onPause(evt);
/*  833 */               break;
/*      */             case 3:
/*  835 */               listener.onStop(evt);
/*  836 */               break;
/*      */             case 6:
/*  838 */               listener.onStall(evt);
/*  839 */               break;
/*      */             case 4:
/*  841 */               listener.onFinish(evt);
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*  847 */           this.cachedStateEvents.clear();
/*      */         }
/*      */ 
/*  850 */         this.playerStateListeners.add(new WeakReference(listener));
/*      */       }
/*      */   }
/*      */ 
/*      */   public void removeMediaPlayerListener(PlayerStateListener listener)
/*      */   {
/*      */     ListIterator it;
/*  856 */     if (listener != null)
/*  857 */       for (it = this.playerStateListeners.listIterator(); it.hasNext(); ) {
/*  858 */         PlayerStateListener l = (PlayerStateListener)((WeakReference)it.next()).get();
/*  859 */         if ((l == null) || (l == listener))
/*  860 */           it.remove();
/*      */       }
/*      */   }
/*      */ 
/*      */   public void addMediaTimeListener(PlayerTimeListener listener)
/*      */   {
/*  867 */     if (listener != null)
/*  868 */       synchronized (this.cachedTimeEvents) {
/*  869 */         if ((!this.cachedTimeEvents.isEmpty()) && (this.playerTimeListeners.isEmpty()))
/*      */         {
/*  871 */           Iterator events = this.cachedTimeEvents.iterator();
/*  872 */           while (events.hasNext()) {
/*  873 */             PlayerTimeEvent evt = (PlayerTimeEvent)events.next();
/*  874 */             switch (evt.getType()) {
/*      */             case STOP_TIME:
/*  876 */               listener.onStopTimeReached(evt.getTime());
/*  877 */               break;
/*      */             case DURATION:
/*  879 */               listener.onDurationChanged(evt.getTime());
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*  885 */           this.cachedTimeEvents.clear();
/*      */         }
/*      */         else {
/*  888 */           double duration = getDuration();
/*  889 */           if (duration != (1.0D / 0.0D)) {
/*  890 */             listener.onDurationChanged(duration);
/*      */           }
/*      */         }
/*      */ 
/*  894 */         this.playerTimeListeners.add(new WeakReference(listener));
/*      */       }
/*      */   }
/*      */ 
/*      */   public void removeMediaTimeListener(PlayerTimeListener listener)
/*      */   {
/*      */     ListIterator it;
/*  900 */     if (listener != null)
/*  901 */       for (it = this.playerTimeListeners.listIterator(); it.hasNext(); ) {
/*  902 */         PlayerTimeListener l = (PlayerTimeListener)((WeakReference)it.next()).get();
/*  903 */         if ((l == null) || (l == listener))
/*  904 */           it.remove();
/*      */       }
/*      */   }
/*      */ 
/*      */   public void addVideoTrackSizeListener(VideoTrackSizeListener listener)
/*      */   {
/*  911 */     if (listener != null) {
/*  912 */       if ((this.frameWidth != -1) && (this.frameHeight != -1)) {
/*  913 */         listener.onSizeChanged(this.frameWidth, this.frameHeight);
/*      */       }
/*  915 */       this.videoTrackSizeListeners.add(new WeakReference(listener));
/*      */     }
/*      */   }
/*      */ 
/*      */   public void removeVideoTrackSizeListener(VideoTrackSizeListener listener)
/*      */   {
/*      */     ListIterator it;
/*  920 */     if (listener != null)
/*  921 */       for (it = this.videoTrackSizeListeners.listIterator(); it.hasNext(); ) {
/*  922 */         VideoTrackSizeListener l = (VideoTrackSizeListener)((WeakReference)it.next()).get();
/*  923 */         if ((l == null) || (l == listener))
/*  924 */           it.remove();
/*      */       }
/*      */   }
/*      */ 
/*      */   public void addMarkerListener(MarkerListener listener)
/*      */   {
/*  931 */     if (listener != null)
/*  932 */       this.markerListeners.add(new WeakReference(listener));
/*      */   }
/*      */ 
/*      */   public void removeMarkerListener(MarkerListener listener)
/*      */   {
/*      */     ListIterator it;
/*  937 */     if (listener != null)
/*  938 */       for (it = this.markerListeners.listIterator(); it.hasNext(); ) {
/*  939 */         MarkerListener l = (MarkerListener)((WeakReference)it.next()).get();
/*  940 */         if ((l == null) || (l == listener))
/*  941 */           it.remove();
/*      */       }
/*      */   }
/*      */ 
/*      */   public void addBufferListener(BufferListener listener)
/*      */   {
/*  948 */     if (listener != null)
/*  949 */       synchronized (this.cachedBufferEvents) {
/*  950 */         if ((!this.cachedBufferEvents.isEmpty()) && (this.bufferListeners.isEmpty()))
/*      */         {
/*  952 */           for (Iterator it = this.cachedBufferEvents.iterator(); it.hasNext(); ) {
/*  953 */             BufferProgressEvent evt = (BufferProgressEvent)it.next();
/*  954 */             listener.onBufferProgress(evt);
/*      */           }
/*      */ 
/*  957 */           this.cachedBufferEvents.clear();
/*      */         }
/*      */ 
/*  960 */         this.bufferListeners.add(new WeakReference(listener));
/*      */       }
/*      */   }
/*      */ 
/*      */   public void removeBufferListener(BufferListener listener)
/*      */   {
/*      */     ListIterator it;
/*  966 */     if (listener != null)
/*  967 */       for (it = this.bufferListeners.listIterator(); it.hasNext(); ) {
/*  968 */         BufferListener l = (BufferListener)((WeakReference)it.next()).get();
/*  969 */         if ((l == null) || (l == listener))
/*  970 */           it.remove();
/*      */       }
/*      */   }
/*      */ 
/*      */   public void addAudioSpectrumListener(AudioSpectrumListener listener)
/*      */   {
/*  977 */     if (listener != null)
/*  978 */       this.audioSpectrumListeners.add(new WeakReference(listener));
/*      */   }
/*      */ 
/*      */   public void removeAudioSpectrumListener(AudioSpectrumListener listener)
/*      */   {
/*      */     ListIterator it;
/*  983 */     if (listener != null)
/*  984 */       for (it = this.audioSpectrumListeners.listIterator(); it.hasNext(); ) {
/*  985 */         AudioSpectrumListener l = (AudioSpectrumListener)((WeakReference)it.next()).get();
/*  986 */         if ((l == null) || (l == listener))
/*  987 */           it.remove();
/*      */       }
/*      */   }
/*      */ 
/*      */   public VideoRenderControl getVideoRenderControl()
/*      */   {
/*  995 */     return this.videoRenderControl;
/*      */   }
/*      */ 
/*      */   public Media getMedia() {
/*  999 */     return this.media;
/*      */   }
/*      */ 
/*      */   public void setAudioSyncDelay(long delay) {
/*      */     try {
/* 1004 */       playerSetAudioSyncDelay(delay);
/*      */     } catch (MediaException me) {
/* 1006 */       sendPlayerEvent(new MediaErrorEvent(this, me.getMediaError()));
/*      */     }
/*      */   }
/*      */ 
/*      */   public long getAudioSyncDelay() {
/*      */     try {
/* 1012 */       return playerGetAudioSyncDelay();
/*      */     } catch (MediaException me) {
/* 1014 */       sendPlayerEvent(new MediaErrorEvent(this, me.getMediaError()));
/*      */     }
/* 1016 */     return 0L;
/*      */   }
/*      */ 
/*      */   public void play() {
/*      */     try {
/* 1021 */       playerPlay();
/*      */     } catch (MediaException me) {
/* 1023 */       sendPlayerEvent(new MediaErrorEvent(this, me.getMediaError()));
/*      */     }
/*      */   }
/*      */ 
/*      */   public void stop() {
/*      */     try {
/* 1029 */       playerStop();
/*      */     }
/*      */     catch (MediaException me) {
/* 1032 */       MediaUtils.warning(this, "stop() failed!");
/*      */     }
/*      */   }
/*      */ 
/*      */   public void pause() {
/*      */     try {
/* 1038 */       playerPause();
/*      */     } catch (MediaException me) {
/* 1040 */       sendPlayerEvent(new MediaErrorEvent(this, me.getMediaError()));
/*      */     }
/*      */   }
/*      */ 
/*      */   public float getRate() {
/*      */     try {
/* 1046 */       return playerGetRate();
/*      */     } catch (MediaException me) {
/* 1048 */       sendPlayerEvent(new MediaErrorEvent(this, me.getMediaError()));
/*      */     }
/* 1050 */     return 0.0F;
/*      */   }
/*      */ 
/*      */   public void setRate(float rate)
/*      */   {
/*      */     try {
/* 1056 */       playerSetRate(rate);
/*      */     }
/*      */     catch (MediaException me) {
/* 1059 */       MediaUtils.warning(this, "setRate(" + rate + ") failed!");
/*      */     }
/*      */   }
/*      */ 
/*      */   public double getPresentationTime() {
/*      */     try {
/* 1065 */       return playerGetPresentationTime();
/*      */     }
/*      */     catch (MediaException me) {
/*      */     }
/* 1069 */     return -1.0D;
/*      */   }
/*      */ 
/*      */   public float getVolume() {
/*      */     try {
/* 1074 */       return playerGetVolume();
/*      */     } catch (MediaException me) {
/* 1076 */       sendPlayerEvent(new MediaErrorEvent(this, me.getMediaError()));
/*      */     }
/* 1078 */     return 0.0F;
/*      */   }
/*      */ 
/*      */   public void setVolume(float vol) {
/* 1082 */     if (vol < 0.0F)
/* 1083 */       vol = 0.0F;
/* 1084 */     else if (vol > 1.0F) {
/* 1085 */       vol = 1.0F;
/*      */     }
/*      */     try
/*      */     {
/* 1089 */       playerSetVolume(vol);
/*      */     } catch (MediaException me) {
/* 1091 */       sendPlayerEvent(new MediaErrorEvent(this, me.getMediaError()));
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean getMute() {
/*      */     try {
/* 1097 */       return playerGetMute();
/*      */     } catch (MediaException me) {
/* 1099 */       sendPlayerEvent(new MediaErrorEvent(this, me.getMediaError()));
/*      */     }
/* 1101 */     return false;
/*      */   }
/*      */ 
/*      */   public synchronized void setMute(boolean enable)
/*      */   {
/*      */     try
/*      */     {
/* 1110 */       playerSetMute(enable);
/*      */     } catch (MediaException me) {
/* 1112 */       sendPlayerEvent(new MediaErrorEvent(this, me.getMediaError()));
/*      */     }
/*      */   }
/*      */ 
/*      */   public float getBalance() {
/*      */     try {
/* 1118 */       return playerGetBalance();
/*      */     } catch (MediaException me) {
/* 1120 */       sendPlayerEvent(new MediaErrorEvent(this, me.getMediaError()));
/*      */     }
/* 1122 */     return 0.0F;
/*      */   }
/*      */ 
/*      */   public void setBalance(float bal) {
/* 1126 */     if (bal < -1.0F)
/* 1127 */       bal = -1.0F;
/* 1128 */     else if (bal > 1.0F) {
/* 1129 */       bal = 1.0F;
/*      */     }
/*      */     try
/*      */     {
/* 1133 */       playerSetBalance(bal);
/*      */     } catch (MediaException me) {
/* 1135 */       sendPlayerEvent(new MediaErrorEvent(this, me.getMediaError()));
/*      */     }
/*      */   }
/*      */ 
/*      */   public abstract AudioEqualizer getEqualizer();
/*      */ 
/*      */   public abstract AudioSpectrum getAudioSpectrum();
/*      */ 
/*      */   public double getDuration() {
/*      */     try {
/* 1145 */       return playerGetDuration();
/*      */     }
/*      */     catch (MediaException me) {
/*      */     }
/* 1149 */     return (1.0D / 0.0D);
/*      */   }
/*      */ 
/*      */   public double getStartTime()
/*      */   {
/*      */     try
/*      */     {
/* 1157 */       return playerGetStartTime();
/*      */     } catch (MediaException me) {
/* 1159 */       sendPlayerEvent(new MediaErrorEvent(this, me.getMediaError()));
/*      */     }
/* 1161 */     return 0.0D;
/*      */   }
/*      */ 
/*      */   public void setStartTime(double streamTime)
/*      */   {
/*      */     try
/*      */     {
/* 1169 */       this.markerLock.lock();
/*      */       try {
/* 1171 */         playerSetStartTime(streamTime);
/* 1172 */         seek(streamTime);
/*      */       } finally {
/* 1174 */         this.markerLock.unlock();
/*      */       }
/*      */     } catch (MediaException me) {
/* 1177 */       sendPlayerEvent(new MediaErrorEvent(this, me.getMediaError()));
/*      */     }
/*      */   }
/*      */ 
/*      */   public double getStopTime()
/*      */   {
/*      */     try
/*      */     {
/* 1186 */       return playerGetStopTime();
/*      */     } catch (MediaException me) {
/* 1188 */       sendPlayerEvent(new MediaErrorEvent(this, me.getMediaError()));
/*      */     }
/* 1190 */     return 1.7976931348623157E+308D;
/*      */   }
/*      */ 
/*      */   public void setStopTime(double streamTime)
/*      */   {
/*      */     try
/*      */     {
/* 1198 */       this.markerLock.lock();
/*      */       try {
/* 1200 */         playerSetStopTime(streamTime);
/*      */       } finally {
/* 1202 */         this.markerLock.unlock();
/*      */       }
/*      */     } catch (MediaException me) {
/* 1205 */       sendPlayerEvent(new MediaErrorEvent(this, me.getMediaError()));
/*      */     }
/*      */   }
/*      */ 
/*      */   public void seek(double streamTime) {
/* 1210 */     if (streamTime < 0.0D) {
/* 1211 */       streamTime = 0.0D;
/*      */     } else {
/* 1213 */       double duration = getDuration();
/* 1214 */       if ((duration >= 0.0D) && (streamTime > duration)) {
/* 1215 */         streamTime = duration;
/*      */       }
/*      */     }
/*      */ 
/* 1219 */     if ((!this.isMediaPulseEnabled.get()) && 
/* 1220 */       ((this.playerState == PlayerStateEvent.PlayerState.PLAYING) || (this.playerState == PlayerStateEvent.PlayerState.PAUSED) || (this.playerState == PlayerStateEvent.PlayerState.FINISHED)) && (getStartTime() <= streamTime) && (streamTime <= getStopTime()))
/*      */     {
/* 1224 */       this.isMediaPulseEnabled.set(true);
/*      */     }
/*      */ 
/* 1228 */     this.markerLock.lock();
/*      */     try {
/* 1230 */       this.timeBeforeSeek = getPresentationTime();
/* 1231 */       this.timeAfterSeek = streamTime;
/* 1232 */       this.checkSeek = (this.timeBeforeSeek != this.timeAfterSeek);
/* 1233 */       this.previousTime = streamTime;
/* 1234 */       this.firedMarkerTime = -1.0D;
/*      */       try
/*      */       {
/* 1239 */         playerSeek(streamTime);
/*      */       }
/*      */       catch (MediaException me) {
/* 1242 */         MediaUtils.warning(this, "seek(" + streamTime + ") failed!");
/*      */       }
/*      */     } finally {
/* 1245 */       this.markerLock.unlock();
/*      */     }
/*      */   }
/*      */ 
/*      */   protected abstract long playerGetAudioSyncDelay()
/*      */     throws MediaException;
/*      */ 
/*      */   protected abstract void playerSetAudioSyncDelay(long paramLong)
/*      */     throws MediaException;
/*      */ 
/*      */   protected abstract void playerPlay()
/*      */     throws MediaException;
/*      */ 
/*      */   protected abstract void playerStop()
/*      */     throws MediaException;
/*      */ 
/*      */   protected abstract void playerPause() throws MediaException;
/*      */ 
/*      */   protected abstract float playerGetRate() throws MediaException;
/*      */ 
/*      */   protected abstract void playerSetRate(float paramFloat) throws MediaException;
/*      */ 
/*      */   protected abstract double playerGetPresentationTime() throws MediaException;
/*      */ 
/*      */   protected abstract boolean playerGetMute() throws MediaException;
/*      */ 
/*      */   protected abstract void playerSetMute(boolean paramBoolean) throws MediaException;
/*      */ 
/*      */   protected abstract float playerGetVolume() throws MediaException;
/*      */ 
/*      */   protected abstract void playerSetVolume(float paramFloat) throws MediaException;
/*      */ 
/*      */   protected abstract float playerGetBalance() throws MediaException;
/*      */ 
/*      */   protected abstract void playerSetBalance(float paramFloat) throws MediaException;
/*      */ 
/*      */   protected abstract double playerGetDuration() throws MediaException;
/*      */ 
/*      */   protected abstract double playerGetStartTime() throws MediaException;
/*      */ 
/*      */   protected abstract void playerSetStartTime(double paramDouble) throws MediaException;
/*      */ 
/*      */   protected abstract double playerGetStopTime() throws MediaException;
/*      */ 
/*      */   protected abstract void playerSetStopTime(double paramDouble) throws MediaException;
/*      */ 
/*      */   protected abstract void playerSeek(double paramDouble) throws MediaException;
/*      */ 
/*      */   protected abstract void playerInit() throws MediaException;
/*      */ 
/*      */   protected abstract void playerDispose();
/*      */ 
/*      */   public PlayerStateEvent.PlayerState getState()
/*      */   {
/* 1299 */     return this.playerState;
/*      */   }
/*      */ 
/*      */   public void dispose() {
/* 1303 */     this.disposeLock.lock();
/*      */     try {
/* 1305 */       if (!this.isDisposed)
/*      */       {
/* 1307 */         destroyMediaPulse();
/*      */ 
/* 1309 */         if (this.eventLoop != null) {
/* 1310 */           this.eventLoop.terminateLoop();
/*      */         }
/*      */ 
/* 1314 */         playerDispose();
/*      */ 
/* 1317 */         if (this.media != null) {
/* 1318 */           this.media.dispose();
/* 1319 */           this.media = null;
/*      */         }
/*      */ 
/* 1323 */         if (this.eventLoop != null) {
/* 1324 */           this.eventLoop.eventQueue.clear();
/* 1325 */           this.eventLoop = null;
/*      */         }
/*      */ 
/* 1328 */         for (ListIterator it = this.videoUpdateListeners.listIterator(); it.hasNext(); ) {
/* 1329 */           VideoRendererListener l = (VideoRendererListener)((WeakReference)it.next()).get();
/* 1330 */           if (l != null)
/* 1331 */             l.releaseVideoFrames();
/*      */           else {
/* 1333 */             it.remove();
/*      */           }
/*      */         }
/*      */ 
/* 1337 */         if (this.videoUpdateListeners != null) {
/* 1338 */           this.videoUpdateListeners.clear();
/*      */         }
/*      */ 
/* 1341 */         if (this.playerStateListeners != null) {
/* 1342 */           this.playerStateListeners.clear();
/*      */         }
/*      */ 
/* 1345 */         if (this.videoTrackSizeListeners != null) {
/* 1346 */           this.videoTrackSizeListeners.clear();
/*      */         }
/*      */ 
/* 1349 */         if (this.videoFrameRateListeners != null) {
/* 1350 */           this.videoFrameRateListeners.clear();
/*      */         }
/*      */ 
/* 1353 */         if (this.cachedStateEvents != null) {
/* 1354 */           this.cachedStateEvents.clear();
/*      */         }
/*      */ 
/* 1357 */         if (this.cachedTimeEvents != null) {
/* 1358 */           this.cachedTimeEvents.clear();
/*      */         }
/*      */ 
/* 1361 */         if (this.cachedBufferEvents != null) {
/* 1362 */           this.cachedBufferEvents.clear();
/*      */         }
/*      */ 
/* 1365 */         if (this.errorListeners != null) {
/* 1366 */           this.errorListeners.clear();
/*      */         }
/*      */ 
/* 1369 */         if (this.playerTimeListeners != null) {
/* 1370 */           this.playerTimeListeners.clear();
/*      */         }
/*      */ 
/* 1373 */         if (this.markerListeners != null) {
/* 1374 */           this.markerListeners.clear();
/*      */         }
/*      */ 
/* 1377 */         if (this.bufferListeners != null) {
/* 1378 */           this.bufferListeners.clear();
/*      */         }
/*      */ 
/* 1381 */         if (this.audioSpectrumListeners != null) {
/* 1382 */           this.audioSpectrumListeners.clear();
/*      */         }
/*      */ 
/* 1385 */         if (this.videoRenderControl != null) {
/* 1386 */           this.videoRenderControl = null;
/*      */         }
/*      */ 
/* 1389 */         if (this.onDispose != null) {
/* 1390 */           this.onDispose.run();
/*      */         }
/*      */ 
/* 1393 */         this.isDisposed = true;
/*      */       }
/*      */     } finally {
/* 1396 */       this.disposeLock.unlock();
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void sendWarning(int warningCode, String warningMessage)
/*      */   {
/* 1408 */     if (this.eventLoop != null) {
/* 1409 */       String message = String.format("Internal media warning: %d", new Object[] { Integer.valueOf(warningCode) });
/*      */ 
/* 1411 */       if (warningMessage != null) {
/* 1412 */         message = message + ": " + warningMessage;
/*      */       }
/* 1414 */       this.eventLoop.postEvent(new WarningEvent(this, message));
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void sendPlayerEvent(PlayerEvent evt) {
/* 1419 */     if (this.eventLoop != null)
/* 1420 */       this.eventLoop.postEvent(evt);
/*      */   }
/*      */ 
/*      */   protected void sendPlayerHaltEvent(String message, double time)
/*      */   {
/* 1427 */     Logger.logMsg(4, message);
/*      */ 
/* 1429 */     if (this.eventLoop != null)
/* 1430 */       this.eventLoop.postEvent(new PlayerStateEvent(PlayerStateEvent.PlayerState.HALTED, time, message));
/*      */   }
/*      */ 
/*      */   protected void sendPlayerMediaErrorEvent(int errorCode)
/*      */   {
/* 1435 */     sendPlayerEvent(new MediaErrorEvent(this, MediaError.getFromCode(errorCode)));
/*      */   }
/*      */ 
/*      */   protected void sendPlayerStateEvent(int eventID, double time) {
/* 1439 */     switch (eventID) {
/*      */     case 101:
/* 1441 */       sendPlayerEvent(new PlayerStateEvent(PlayerStateEvent.PlayerState.READY, time));
/* 1442 */       break;
/*      */     case 102:
/* 1444 */       sendPlayerEvent(new PlayerStateEvent(PlayerStateEvent.PlayerState.PLAYING, time));
/* 1445 */       break;
/*      */     case 103:
/* 1447 */       sendPlayerEvent(new PlayerStateEvent(PlayerStateEvent.PlayerState.PAUSED, time));
/* 1448 */       break;
/*      */     case 104:
/* 1450 */       sendPlayerEvent(new PlayerStateEvent(PlayerStateEvent.PlayerState.STOPPED, time));
/* 1451 */       break;
/*      */     case 105:
/* 1453 */       sendPlayerEvent(new PlayerStateEvent(PlayerStateEvent.PlayerState.STALLED, time));
/* 1454 */       break;
/*      */     case 106:
/* 1456 */       sendPlayerEvent(new PlayerStateEvent(PlayerStateEvent.PlayerState.FINISHED, time));
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void sendNewFrameEvent(long nativeRef)
/*      */   {
/* 1462 */     NativeVideoBuffer newFrameData = NativeVideoBuffer.createVideoBuffer(nativeRef);
/*      */ 
/* 1465 */     sendPlayerEvent(new NewFrameEvent(newFrameData));
/*      */   }
/*      */ 
/*      */   protected void sendFrameSizeChangedEvent(int width, int height) {
/* 1469 */     sendPlayerEvent(new FrameSizeChangedEvent(width, height));
/*      */   }
/*      */ 
/*      */   protected void sendAudioTrack(String name, int encoding, String language, int numChannels, int channelMask, float sampleRate)
/*      */   {
/* 1475 */     Locator locator = getMedia().getLocator();
/*      */ 
/* 1477 */     Track track = new AudioTrack(locator, name, Track.Encoding.toEncoding(encoding), new Locale(language), numChannels, channelMask, sampleRate);
/*      */ 
/* 1480 */     TrackEvent evt = new TrackEvent(track);
/*      */ 
/* 1482 */     sendPlayerEvent(evt);
/*      */   }
/*      */ 
/*      */   protected void sendVideoTrack(String name, int encoding, int width, int height, float frameRate, boolean hasAlphaChannel)
/*      */   {
/* 1488 */     Locator locator = getMedia().getLocator();
/*      */ 
/* 1490 */     Track track = new VideoTrack(locator, name, Track.Encoding.toEncoding(encoding), new VideoResolution(width, height), frameRate, hasAlphaChannel);
/*      */ 
/* 1493 */     TrackEvent evt = new TrackEvent(track);
/*      */ 
/* 1495 */     sendPlayerEvent(evt);
/*      */   }
/*      */ 
/*      */   protected void sendMarkerEvent(String name, double time) {
/* 1499 */     sendPlayerEvent(new MarkerEvent(name, time));
/*      */   }
/*      */ 
/*      */   protected void sendStopReachedEvent(double presentationTime) {
/* 1503 */     sendPlayerEvent(new PlayerTimeEvent(PlayerTimeEventType.STOP_TIME, presentationTime));
/*      */   }
/*      */ 
/*      */   protected void sendDurationUpdateEvent(double duration) {
/* 1507 */     sendPlayerEvent(new PlayerTimeEvent(PlayerTimeEventType.DURATION, duration));
/*      */   }
/*      */ 
/*      */   protected void sendBufferProgressEvent(double clipDuration, long bufferStart, long bufferStop, long bufferPosition) {
/* 1511 */     sendPlayerEvent(new BufferProgressEvent(clipDuration, bufferStart, bufferStop, bufferPosition));
/*      */   }
/*      */ 
/*      */   protected void sendAudioSpectrumEvent(double timestamp, double duration) {
/* 1515 */     sendPlayerEvent(new AudioSpectrumEvent(getAudioSpectrum(), timestamp, duration));
/*      */   }
/*      */ 
/*      */   public void markerStateChanged(boolean hasMarkers) {
/* 1519 */     if (hasMarkers) {
/* 1520 */       this.markerLock.lock();
/*      */       try {
/* 1522 */         this.previousTime = getPresentationTime();
/*      */       } finally {
/* 1524 */         this.markerLock.unlock();
/*      */       }
/* 1526 */       createMediaPulse();
/*      */     } else {
/* 1528 */       destroyMediaPulse();
/*      */     }
/*      */   }
/*      */ 
/*      */   private void createMediaPulse() {
/* 1533 */     this.mediaPulseLock.lock();
/*      */     try {
/* 1535 */       if (this.mediaPulseTimer == null) {
/* 1536 */         this.mediaPulseTimer = new Timer(true);
/* 1537 */         this.mediaPulseTimer.scheduleAtFixedRate(new MediaPulseTask(this), 0L, 40L);
/*      */       }
/*      */     }
/*      */     finally
/*      */     {
/* 1542 */       this.mediaPulseLock.unlock();
/*      */     }
/*      */   }
/*      */ 
/*      */   private void destroyMediaPulse() {
/* 1547 */     this.mediaPulseLock.lock();
/*      */     try {
/* 1549 */       if (this.mediaPulseTimer != null) {
/* 1550 */         this.mediaPulseTimer.cancel();
/* 1551 */         this.mediaPulseTimer = null;
/*      */       }
/*      */     } finally {
/* 1554 */       this.mediaPulseLock.unlock();
/*      */     }
/*      */   }
/*      */ 
/*      */   boolean doMediaPulseTask() {
/* 1559 */     if (this.isMediaPulseEnabled.get()) {
/* 1560 */       this.markerLock.lock();
/* 1561 */       this.disposeLock.lock();
/*      */ 
/* 1563 */       if (this.isDisposed) {
/* 1564 */         return false;
/*      */       }
/*      */       try
/*      */       {
/* 1568 */         double thisTime = getPresentationTime();
/*      */ 
/* 1571 */         if (this.checkSeek)
/*      */         {
/*      */           boolean bool;
/* 1572 */           if (this.timeAfterSeek > this.timeBeforeSeek)
/*      */           {
/* 1574 */             if (thisTime >= this.timeAfterSeek)
/*      */             {
/* 1576 */               this.checkSeek = false;
/*      */             }
/* 1578 */             else return true;
/*      */           }
/* 1580 */           else if (this.timeAfterSeek < this.timeBeforeSeek)
/*      */           {
/* 1582 */             if (thisTime >= this.timeBeforeSeek)
/*      */             {
/* 1584 */               return true;
/*      */             }
/* 1586 */             this.checkSeek = false;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 1591 */         Map.Entry marker = this.media.getNextMarker(this.previousTime, true);
/*      */ 
/* 1597 */         while (marker != null) {
/* 1598 */           double nextMarkerTime = ((Double)marker.getKey()).doubleValue();
/* 1599 */           if (nextMarkerTime > thisTime)
/*      */             break;
/* 1601 */           if ((nextMarkerTime != this.firedMarkerTime) && (nextMarkerTime >= this.previousTime) && (nextMarkerTime >= getStartTime()) && (nextMarkerTime <= getStopTime()))
/*      */           {
/* 1608 */             MarkerEvent evt = new MarkerEvent((String)marker.getValue(), nextMarkerTime);
/* 1609 */             for (ListIterator it = this.markerListeners.listIterator(); it.hasNext(); ) {
/* 1610 */               MarkerListener listener = (MarkerListener)((WeakReference)it.next()).get();
/* 1611 */               if (listener != null)
/* 1612 */                 listener.onMarker(evt);
/*      */               else {
/* 1614 */                 it.remove();
/*      */               }
/*      */             }
/* 1617 */             this.firedMarkerTime = nextMarkerTime;
/*      */           }
/* 1619 */           marker = this.media.getNextMarker(nextMarkerTime, false);
/*      */         }
/*      */ 
/* 1622 */         this.previousTime = thisTime;
/*      */       } finally {
/* 1624 */         this.disposeLock.unlock();
/* 1625 */         this.markerLock.unlock();
/*      */       }
/*      */     }
/*      */ 
/* 1629 */     return true;
/*      */   }
/*      */ 
/*      */   private class EventQueueThread extends Thread
/*      */   {
/*  407 */     private BlockingQueue<PlayerEvent> eventQueue = new LinkedBlockingQueue();
/*      */ 
/*  409 */     private volatile boolean stopped = false;
/*      */ 
/*      */     EventQueueThread() {
/*  412 */       setName("JFXMedia Player EventQueueThread");
/*  413 */       setDaemon(true);
/*      */     }
/*      */ 
/*      */     public void run()
/*      */     {
/*  418 */       while (!this.stopped)
/*      */       {
/*      */         try
/*      */         {
/*  422 */           PlayerEvent evt = (PlayerEvent)this.eventQueue.take();
/*      */ 
/*  424 */           if (!this.stopped) {
/*  425 */             if ((evt instanceof NewFrameEvent)) {
/*      */               try {
/*  427 */                 HandleRendererEvents((NewFrameEvent)evt);
/*      */               } catch (Throwable t) {
/*  429 */                 if (Logger.canLog(4)) {
/*  430 */                   Logger.logMsg(4, "Caught exception in HandleRendererEvents: " + t.toString());
/*      */                 }
/*      */               }
/*      */             }
/*  434 */             else if ((evt instanceof PlayerStateEvent))
/*  435 */               HandleStateEvents((PlayerStateEvent)evt);
/*  436 */             else if ((evt instanceof NativeMediaPlayer.FrameSizeChangedEvent))
/*  437 */               HandleFrameSizeChangedEvents((NativeMediaPlayer.FrameSizeChangedEvent)evt);
/*  438 */             else if ((evt instanceof NativeMediaPlayer.TrackEvent))
/*  439 */               HandleTrackEvents((NativeMediaPlayer.TrackEvent)evt);
/*  440 */             else if ((evt instanceof MarkerEvent))
/*  441 */               HandleMarkerEvents((MarkerEvent)evt);
/*  442 */             else if ((evt instanceof NativeMediaPlayer.WarningEvent))
/*  443 */               HandleWarningEvents((NativeMediaPlayer.WarningEvent)evt);
/*  444 */             else if ((evt instanceof NativeMediaPlayer.PlayerTimeEvent))
/*  445 */               HandlePlayerTimeEvents((NativeMediaPlayer.PlayerTimeEvent)evt);
/*  446 */             else if ((evt instanceof BufferProgressEvent))
/*  447 */               HandleBufferEvents((BufferProgressEvent)evt);
/*  448 */             else if ((evt instanceof AudioSpectrumEvent))
/*  449 */               HandleAudioSpectrumEvents((AudioSpectrumEvent)evt);
/*  450 */             else if ((evt instanceof NativeMediaPlayer.MediaErrorEvent)) {
/*  451 */               HandleErrorEvents((NativeMediaPlayer.MediaErrorEvent)evt);
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  463 */       this.eventQueue.clear();
/*      */     }
/*      */ 
/*      */     private void HandleRendererEvents(NewFrameEvent evt) {
/*  467 */       if (NativeMediaPlayer.this.isFirstFrame)
/*      */       {
/*  470 */         NativeMediaPlayer.this.isFirstFrame = false;
/*  471 */         synchronized (NativeMediaPlayer.this.firstFrameLock) {
/*  472 */           NativeMediaPlayer.this.firstFrameEvent = evt;
/*  473 */           NativeMediaPlayer.this.firstFrameTime = NativeMediaPlayer.this.firstFrameEvent.getFrameData().getTimestamp();
/*  474 */           NativeMediaPlayer.this.firstFrameEvent.getFrameData().holdFrame();
/*      */         }
/*  476 */       } else if ((NativeMediaPlayer.this.firstFrameEvent != null) && (NativeMediaPlayer.this.firstFrameTime != evt.getFrameData().getTimestamp()))
/*      */       {
/*  486 */         synchronized (NativeMediaPlayer.this.firstFrameLock) {
/*  487 */           NativeMediaPlayer.this.firstFrameEvent.getFrameData().releaseFrame();
/*  488 */           NativeMediaPlayer.this.firstFrameEvent = null;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  493 */       for (ListIterator it = NativeMediaPlayer.this.videoUpdateListeners.listIterator(); it.hasNext(); ) {
/*  494 */         VideoRendererListener l = (VideoRendererListener)((WeakReference)it.next()).get();
/*  495 */         if (l != null)
/*  496 */           l.videoFrameUpdated(evt);
/*      */         else {
/*  498 */           it.remove();
/*      */         }
/*      */       }
/*      */ 
/*  502 */       evt.getFrameData().releaseFrame();
/*      */ 
/*  504 */       if (!NativeMediaPlayer.this.videoFrameRateListeners.isEmpty())
/*      */       {
/*  506 */         double currentFrameTime = System.nanoTime() / 1000000000.0D;
/*      */ 
/*  508 */         if (NativeMediaPlayer.this.recomputeFrameRate)
/*      */         {
/*  510 */           NativeMediaPlayer.this.recomputeFrameRate = false;
/*  511 */           NativeMediaPlayer.this.previousFrameTime = currentFrameTime;
/*  512 */           NativeMediaPlayer.this.numFramesSincePlaying = 1L;
/*      */         } else {
/*  514 */           boolean fireFrameRateEvent = false;
/*      */ 
/*  516 */           if (NativeMediaPlayer.this.numFramesSincePlaying == 1L)
/*      */           {
/*  519 */             NativeMediaPlayer.this.meanFrameDuration = (currentFrameTime - NativeMediaPlayer.this.previousFrameTime);
/*  520 */             if (NativeMediaPlayer.this.meanFrameDuration > 0.0D) {
/*  521 */               NativeMediaPlayer.this.decodedFrameRate = (1.0D / NativeMediaPlayer.this.meanFrameDuration);
/*  522 */               fireFrameRateEvent = true;
/*      */             }
/*      */           }
/*      */           else
/*      */           {
/*  527 */             double previousMeanFrameDuration = NativeMediaPlayer.this.meanFrameDuration;
/*      */ 
/*  530 */             int movingAverageLength = NativeMediaPlayer.this.encodedFrameRate != 0.0D ? (int)(NativeMediaPlayer.this.encodedFrameRate + 0.5D) : 30;
/*      */ 
/*  534 */             long numFrames = NativeMediaPlayer.this.numFramesSincePlaying < movingAverageLength ? NativeMediaPlayer.this.numFramesSincePlaying : movingAverageLength;
/*      */ 
/*  538 */             NativeMediaPlayer.this.meanFrameDuration = (((numFrames - 1L) * previousMeanFrameDuration + currentFrameTime - NativeMediaPlayer.this.previousFrameTime) / numFrames);
/*      */ 
/*  543 */             if ((NativeMediaPlayer.this.meanFrameDuration > 0.0D) && (Math.abs(NativeMediaPlayer.this.decodedFrameRate - 1.0D / NativeMediaPlayer.this.meanFrameDuration) > 0.5D))
/*      */             {
/*  545 */               NativeMediaPlayer.this.decodedFrameRate = (1.0D / NativeMediaPlayer.this.meanFrameDuration);
/*  546 */               fireFrameRateEvent = true;
/*      */             }
/*      */           }
/*      */           ListIterator it;
/*  550 */           if (fireFrameRateEvent)
/*      */           {
/*  552 */             for (it = NativeMediaPlayer.this.videoFrameRateListeners.listIterator(); it.hasNext(); ) {
/*  553 */               VideoFrameRateListener l = (VideoFrameRateListener)((WeakReference)it.next()).get();
/*  554 */               if (l != null)
/*  555 */                 l.onFrameRateChanged(NativeMediaPlayer.this.decodedFrameRate);
/*      */               else {
/*  557 */                 it.remove();
/*      */               }
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*  563 */           NativeMediaPlayer.this.previousFrameTime = currentFrameTime;
/*  564 */           NativeMediaPlayer.access$1108(NativeMediaPlayer.this);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*      */     private void HandleStateEvents(PlayerStateEvent evt) {
/*  570 */       NativeMediaPlayer.this.playerState = evt.getState();
/*      */ 
/*  572 */       synchronized (NativeMediaPlayer.this.cachedStateEvents) {
/*  573 */         NativeMediaPlayer.this.recomputeFrameRate = (PlayerStateEvent.PlayerState.PLAYING == evt.getState());
/*      */ 
/*  575 */         switch (NativeMediaPlayer.1.$SwitchMap$com$sun$media$jfxmedia$events$PlayerStateEvent$PlayerState[NativeMediaPlayer.this.playerState.ordinal()]) {
/*      */         case 1:
/*  577 */           break;
/*      */         case 2:
/*  579 */           NativeMediaPlayer.this.isMediaPulseEnabled.set(true);
/*  580 */           break;
/*      */         case 3:
/*      */         case 4:
/*  585 */           NativeMediaPlayer.this.doMediaPulseTask();
/*      */         case 5:
/*      */         case 6:
/*      */         case 7:
/*  589 */           NativeMediaPlayer.this.isMediaPulseEnabled.set(false);
/*      */         }
/*      */ 
/*  593 */         if (NativeMediaPlayer.this.playerStateListeners.isEmpty())
/*      */         {
/*  595 */           NativeMediaPlayer.this.cachedStateEvents.add(evt);
/*  596 */           return;
/*      */         }
/*      */       }
/*      */ 
/*  600 */       for (ListIterator it = NativeMediaPlayer.this.playerStateListeners.listIterator(); it.hasNext(); ) {
/*  601 */         PlayerStateListener listener = (PlayerStateListener)((WeakReference)it.next()).get();
/*  602 */         if (listener != null) {
/*  603 */           switch (NativeMediaPlayer.1.$SwitchMap$com$sun$media$jfxmedia$events$PlayerStateEvent$PlayerState[NativeMediaPlayer.this.playerState.ordinal()]) {
/*      */           case 1:
/*  605 */             NativeMediaPlayer.this.onNativeInit();
/*  606 */             listener.onReady(evt);
/*  607 */             break;
/*      */           case 2:
/*  610 */             listener.onPlaying(evt);
/*  611 */             break;
/*      */           case 5:
/*  614 */             listener.onPause(evt);
/*  615 */             break;
/*      */           case 3:
/*  618 */             listener.onStop(evt);
/*  619 */             break;
/*      */           case 6:
/*  622 */             listener.onStall(evt);
/*  623 */             break;
/*      */           case 4:
/*  626 */             listener.onFinish(evt);
/*  627 */             break;
/*      */           case 7:
/*  630 */             listener.onHalt(evt);
/*      */           }
/*      */         }
/*      */         else
/*  634 */           it.remove();
/*      */       }
/*      */     }
/*      */ 
/*      */     private void HandlePlayerTimeEvents(NativeMediaPlayer.PlayerTimeEvent evt)
/*      */     {
/*  640 */       synchronized (NativeMediaPlayer.this.cachedTimeEvents) {
/*  641 */         if (NativeMediaPlayer.this.playerTimeListeners.isEmpty())
/*      */         {
/*  643 */           NativeMediaPlayer.this.cachedTimeEvents.add(evt);
/*  644 */           return;
/*      */         }
/*      */       }
/*      */ 
/*  648 */       for (ListIterator it = NativeMediaPlayer.this.playerTimeListeners.listIterator(); it.hasNext(); ) {
/*  649 */         PlayerTimeListener listener = (PlayerTimeListener)((WeakReference)it.next()).get();
/*  650 */         if (listener != null) {
/*  651 */           switch (NativeMediaPlayer.1.$SwitchMap$com$sun$media$jfxmediaimpl$NativeMediaPlayer$PlayerTimeEventType[evt.getType().ordinal()]) {
/*      */           case 1:
/*  653 */             listener.onStopTimeReached(evt.getTime());
/*  654 */             break;
/*      */           case 2:
/*  657 */             listener.onDurationChanged(evt.getTime());
/*      */           }
/*      */         }
/*      */         else
/*  661 */           it.remove();
/*      */       }
/*      */     }
/*      */ 
/*      */     private void HandleFrameSizeChangedEvents(NativeMediaPlayer.FrameSizeChangedEvent evt)
/*      */     {
/*  667 */       NativeMediaPlayer.this.frameWidth = evt.getWidth();
/*  668 */       NativeMediaPlayer.this.frameHeight = evt.getHeight();
/*  669 */       Logger.logMsg(1, "** Frame size changed (" + NativeMediaPlayer.this.frameWidth + ", " + NativeMediaPlayer.this.frameHeight + ")");
/*  670 */       for (ListIterator it = NativeMediaPlayer.this.videoTrackSizeListeners.listIterator(); it.hasNext(); ) {
/*  671 */         VideoTrackSizeListener listener = (VideoTrackSizeListener)((WeakReference)it.next()).get();
/*  672 */         if (listener != null)
/*  673 */           listener.onSizeChanged(NativeMediaPlayer.this.frameWidth, NativeMediaPlayer.this.frameHeight);
/*      */         else
/*  675 */           it.remove();
/*      */       }
/*      */     }
/*      */ 
/*      */     private void HandleTrackEvents(NativeMediaPlayer.TrackEvent evt)
/*      */     {
/*  681 */       NativeMediaPlayer.this.media.addTrack(evt.getTrack());
/*      */ 
/*  683 */       if ((evt.getTrack() instanceof VideoTrack))
/*  684 */         NativeMediaPlayer.this.encodedFrameRate = ((VideoTrack)evt.getTrack()).getEncodedFrameRate();
/*      */     }
/*      */ 
/*      */     private void HandleMarkerEvents(MarkerEvent evt)
/*      */     {
/*  689 */       for (ListIterator it = NativeMediaPlayer.this.markerListeners.listIterator(); it.hasNext(); ) {
/*  690 */         MarkerListener listener = (MarkerListener)((WeakReference)it.next()).get();
/*  691 */         if (listener != null)
/*  692 */           listener.onMarker(evt);
/*      */         else
/*  694 */           it.remove();
/*      */       }
/*      */     }
/*      */ 
/*      */     private void HandleWarningEvents(NativeMediaPlayer.WarningEvent evt)
/*      */     {
/*  700 */       Logger.logMsg(3, evt.getSource() + evt.getMessage());
/*      */     }
/*      */ 
/*      */     private void HandleErrorEvents(NativeMediaPlayer.MediaErrorEvent evt) {
/*  704 */       Logger.logMsg(4, evt.getMessage());
/*      */ 
/*  706 */       synchronized (NativeMediaPlayer.this.cachedErrorEvents) {
/*  707 */         if (NativeMediaPlayer.this.errorListeners.isEmpty())
/*      */         {
/*  709 */           NativeMediaPlayer.this.cachedErrorEvents.add(evt);
/*  710 */           return;
/*      */         }
/*      */       }
/*      */ 
/*  714 */       for (ListIterator it = NativeMediaPlayer.this.errorListeners.listIterator(); it.hasNext(); ) {
/*  715 */         MediaErrorListener l = (MediaErrorListener)((WeakReference)it.next()).get();
/*  716 */         if (l != null)
/*  717 */           l.onError(evt.getSource(), evt.getErrorCode(), evt.getMessage());
/*      */         else
/*  719 */           it.remove();
/*      */       }
/*      */     }
/*      */ 
/*      */     private void HandleBufferEvents(BufferProgressEvent evt)
/*      */     {
/*  725 */       synchronized (NativeMediaPlayer.this.cachedBufferEvents) {
/*  726 */         if (NativeMediaPlayer.this.bufferListeners.isEmpty())
/*      */         {
/*  728 */           NativeMediaPlayer.this.cachedBufferEvents.add(evt);
/*  729 */           return;
/*      */         }
/*      */       }
/*      */ 
/*  733 */       for (ListIterator it = NativeMediaPlayer.this.bufferListeners.listIterator(); it.hasNext(); ) {
/*  734 */         BufferListener listener = (BufferListener)((WeakReference)it.next()).get();
/*  735 */         if (listener != null)
/*  736 */           listener.onBufferProgress(evt);
/*      */         else
/*  738 */           it.remove();
/*      */       }
/*      */     }
/*      */ 
/*      */     private void HandleAudioSpectrumEvents(AudioSpectrumEvent evt)
/*      */     {
/*  744 */       for (ListIterator it = NativeMediaPlayer.this.audioSpectrumListeners.listIterator(); it.hasNext(); ) {
/*  745 */         AudioSpectrumListener listener = (AudioSpectrumListener)((WeakReference)it.next()).get();
/*  746 */         if (listener != null)
/*  747 */           listener.onAudioSpectrumEvent(evt);
/*      */         else
/*  749 */           it.remove();
/*      */       }
/*      */     }
/*      */ 
/*      */     public void postEvent(PlayerEvent event)
/*      */     {
/*  758 */       if (this.eventQueue != null)
/*  759 */         this.eventQueue.offer(event);
/*      */     }
/*      */ 
/*      */     public void terminateLoop()
/*      */     {
/*  767 */       this.stopped = true;
/*      */ 
/*  769 */       this.eventQueue.offer(new PlayerEvent());
/*      */     }
/*      */   }
/*      */ 
/*      */   private class VideoRenderer
/*      */     implements VideoRenderControl
/*      */   {
/*      */     private VideoRenderer()
/*      */     {
/*      */     }
/*      */ 
/*      */     public void addVideoRendererListener(VideoRendererListener listener)
/*      */     {
/*  340 */       if (listener != null) {
/*  341 */         synchronized (NativeMediaPlayer.this.firstFrameLock)
/*      */         {
/*  346 */           if (NativeMediaPlayer.this.firstFrameEvent != null) {
/*  347 */             listener.videoFrameUpdated(NativeMediaPlayer.this.firstFrameEvent);
/*      */           }
/*      */         }
/*  350 */         NativeMediaPlayer.this.videoUpdateListeners.add(new WeakReference(listener));
/*      */       }
/*      */     }
/*      */ 
/*      */     public void removeVideoRendererListener(VideoRendererListener listener)
/*      */     {
/*      */       ListIterator it;
/*  360 */       if (listener != null)
/*  361 */         for (it = NativeMediaPlayer.this.videoUpdateListeners.listIterator(); it.hasNext(); ) {
/*  362 */           VideoRendererListener l = (VideoRendererListener)((WeakReference)it.next()).get();
/*  363 */           if ((l == null) || (l == listener))
/*  364 */             it.remove();
/*      */         }
/*      */     }
/*      */ 
/*      */     public void addVideoFrameRateListener(VideoFrameRateListener listener)
/*      */     {
/*  371 */       if (listener != null)
/*  372 */         NativeMediaPlayer.this.videoFrameRateListeners.add(new WeakReference(listener));
/*      */     }
/*      */ 
/*      */     public void removeVideoFrameRateListener(VideoFrameRateListener listener)
/*      */     {
/*      */       ListIterator it;
/*  377 */       if (listener != null)
/*  378 */         for (it = NativeMediaPlayer.this.videoFrameRateListeners.listIterator(); it.hasNext(); ) {
/*  379 */           VideoFrameRateListener l = (VideoFrameRateListener)((WeakReference)it.next()).get();
/*  380 */           if ((l == null) || (l == listener))
/*  381 */             it.remove();
/*      */         }
/*      */     }
/*      */ 
/*      */     public int getFrameWidth()
/*      */     {
/*  388 */       return NativeMediaPlayer.this.frameWidth;
/*      */     }
/*      */ 
/*      */     public int getFrameHeight() {
/*  392 */       return NativeMediaPlayer.this.frameHeight;
/*      */     }
/*      */   }
/*      */ 
/*      */   private class FrameSizeChangedEvent extends PlayerEvent
/*      */   {
/*      */     private int width;
/*      */     private int height;
/*      */ 
/*      */     public FrameSizeChangedEvent(int width, int height)
/*      */     {
/*  302 */       if (width > 0)
/*  303 */         this.width = width;
/*      */       else {
/*  305 */         this.width = 0;
/*      */       }
/*      */ 
/*  308 */       if (height > 0)
/*  309 */         this.height = height;
/*      */       else
/*  311 */         this.height = 0;
/*      */     }
/*      */ 
/*      */     public int getWidth()
/*      */     {
/*  316 */       return this.width;
/*      */     }
/*      */ 
/*      */     public int getHeight() {
/*  320 */       return this.height;
/*      */     }
/*      */   }
/*      */ 
/*      */   private class TrackEvent extends PlayerEvent
/*      */   {
/*      */     private Track track;
/*      */ 
/*      */     TrackEvent(Track track)
/*      */     {
/*  285 */       this.track = track;
/*      */     }
/*      */ 
/*      */     public Track getTrack() {
/*  289 */       return this.track;
/*      */     }
/*      */   }
/*      */ 
/*      */   private class PlayerTimeEvent extends PlayerEvent
/*      */   {
/*      */     private NativeMediaPlayer.PlayerTimeEventType type;
/*      */     private double time;
/*      */ 
/*      */     public PlayerTimeEvent(NativeMediaPlayer.PlayerTimeEventType type, double time)
/*      */     {
/*  264 */       this.type = type;
/*  265 */       this.time = time;
/*      */     }
/*      */ 
/*      */     public NativeMediaPlayer.PlayerTimeEventType getType() {
/*  269 */       return this.type;
/*      */     }
/*      */ 
/*      */     public double getTime() {
/*  273 */       return this.time;
/*      */     }
/*      */   }
/*      */ 
/*      */   public class MediaErrorEvent extends PlayerEvent
/*      */   {
/*      */     private Object source;
/*      */     private MediaError error;
/*      */ 
/*      */     public MediaErrorEvent(Object source, MediaError error)
/*      */     {
/*  241 */       this.source = source;
/*  242 */       this.error = error;
/*      */     }
/*      */ 
/*      */     public Object getSource() {
/*  246 */       return this.source;
/*      */     }
/*      */ 
/*      */     public String getMessage() {
/*  250 */       return this.error.description();
/*      */     }
/*      */ 
/*      */     public int getErrorCode() {
/*  254 */       return this.error.code();
/*      */     }
/*      */   }
/*      */ 
/*      */   private class WarningEvent extends PlayerEvent
/*      */   {
/*      */     private Object source;
/*      */     private String message;
/*      */ 
/*      */     WarningEvent(Object source, String message)
/*      */     {
/*  219 */       this.source = source;
/*  220 */       this.message = message;
/*      */     }
/*      */ 
/*      */     public Object getSource() {
/*  224 */       return this.source;
/*      */     }
/*      */ 
/*      */     public String getMessage() {
/*  228 */       return this.message;
/*      */     }
/*      */   }
/*      */ 
/*      */   private static enum PlayerTimeEventType
/*      */   {
/*  122 */     STOP_TIME, DURATION;
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.media.jfxmediaimpl.NativeMediaPlayer
 * JD-Core Version:    0.6.2
 */