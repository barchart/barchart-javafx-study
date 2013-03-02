/*     */ package com.sun.webpane.sg.prism;
/*     */ 
/*     */ import com.sun.javafx.sg.prism.PrismMediaFrameHandler;
/*     */ import com.sun.media.jfxmedia.Media;
/*     */ import com.sun.media.jfxmedia.MediaManager;
/*     */ import com.sun.media.jfxmedia.MediaPlayer;
/*     */ import com.sun.media.jfxmedia.control.VideoDataBuffer;
/*     */ import com.sun.media.jfxmedia.control.VideoRenderControl;
/*     */ import com.sun.media.jfxmedia.events.BufferListener;
/*     */ import com.sun.media.jfxmedia.events.BufferProgressEvent;
/*     */ import com.sun.media.jfxmedia.events.MediaErrorListener;
/*     */ import com.sun.media.jfxmedia.events.NewFrameEvent;
/*     */ import com.sun.media.jfxmedia.events.PlayerStateEvent;
/*     */ import com.sun.media.jfxmedia.events.PlayerStateListener;
/*     */ import com.sun.media.jfxmedia.events.PlayerTimeListener;
/*     */ import com.sun.media.jfxmedia.events.VideoRendererListener;
/*     */ import com.sun.media.jfxmedia.events.VideoTrackSizeListener;
/*     */ import com.sun.media.jfxmedia.locator.Locator;
/*     */ import com.sun.media.jfxmedia.track.AudioTrack;
/*     */ import com.sun.media.jfxmedia.track.Track;
/*     */ import com.sun.media.jfxmedia.track.VideoTrack;
/*     */ import com.sun.prism.Graphics;
/*     */ import com.sun.prism.Texture;
/*     */ import com.sun.webpane.platform.graphics.WCGraphicsContext;
/*     */ import com.sun.webpane.platform.graphics.WCMediaPlayer;
/*     */ import java.net.URI;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ public class WCMediaPlayerImpl extends WCMediaPlayer
/*     */   implements PlayerStateListener, MediaErrorListener, VideoTrackSizeListener, BufferListener, PlayerTimeListener
/*     */ {
/*  39 */   private final Object lock = new Object();
/*     */   private volatile MediaPlayer player;
/*     */   private volatile CreateThread createThread;
/*     */   private volatile PrismMediaFrameHandler frameHandler;
/*     */   private final MediaFrameListener frameListener;
/*  48 */   private boolean gotFirstFrame = false;
/*     */ 
/*  51 */   private int finished = 0;
/*     */ 
/* 476 */   float bufferedStart = 0.0F;
/* 477 */   float bufferedEnd = 0.0F;
/* 478 */   boolean buffering = false;
/*     */ 
/*     */   protected WCMediaPlayerImpl()
/*     */   {
/*  54 */     this.frameListener = new MediaFrameListener(null);
/*     */   }
/*     */ 
/*     */   private MediaPlayer getPlayer() {
/*  58 */     synchronized (this.lock) {
/*  59 */       if (this.createThread != null) {
/*  60 */         return null;
/*     */       }
/*  62 */       return this.player;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void setPlayer(MediaPlayer paramMediaPlayer) {
/*  67 */     synchronized (this.lock) {
/*  68 */       this.player = paramMediaPlayer;
/*  69 */       installListeners();
/*  70 */       this.frameHandler = PrismMediaFrameHandler.getHandler(this.player);
/*     */     }
/*     */ 
/*  73 */     this.finished = 0;
/*     */   }
/*     */ 
/*     */   protected void load(String paramString1, String paramString2)
/*     */   {
/* 136 */     synchronized (this.lock) {
/* 137 */       if (this.createThread != null) {
/* 138 */         this.createThread.cancel();
/*     */       }
/* 140 */       disposePlayer();
/* 141 */       this.createThread = new CreateThread(paramString1, paramString2);
/*     */     }
/*     */ 
/* 145 */     if (getPreload() != 0)
/* 146 */       this.createThread.start();
/*     */   }
/*     */ 
/*     */   protected void cancelLoad()
/*     */   {
/* 151 */     synchronized (this.lock) {
/* 152 */       if (this.createThread != null) {
/* 153 */         this.createThread.cancel();
/*     */       }
/*     */     }
/* 156 */     ??? = getPlayer();
/* 157 */     if (??? != null) {
/* 158 */       ((MediaPlayer)???).stop();
/*     */     }
/* 160 */     notifyNetworkStateChanged(0);
/* 161 */     notifyReadyStateChanged(0);
/*     */   }
/*     */ 
/*     */   protected void disposePlayer()
/*     */   {
/*     */     MediaPlayer localMediaPlayer;
/* 166 */     synchronized (this.lock) {
/* 167 */       removeListeners();
/* 168 */       localMediaPlayer = this.player;
/* 169 */       this.player = null;
/* 170 */       if (this.frameHandler != null) {
/* 171 */         this.frameHandler.releaseTextures();
/* 172 */         this.frameHandler = null;
/*     */       }
/*     */     }
/* 175 */     if (localMediaPlayer != null) {
/* 176 */       localMediaPlayer.stop();
/* 177 */       localMediaPlayer.dispose();
/* 178 */       localMediaPlayer = null;
/* 179 */       if (this.frameListener != null)
/* 180 */         this.frameListener.releaseVideoFrames();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void installListeners()
/*     */   {
/* 186 */     if (null != this.player) {
/* 187 */       this.player.addMediaPlayerListener(this);
/* 188 */       this.player.addMediaErrorListener(this);
/* 189 */       this.player.addVideoTrackSizeListener(this);
/* 190 */       this.player.addBufferListener(this);
/* 191 */       this.player.getVideoRenderControl().addVideoRendererListener(this.frameListener);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void removeListeners() {
/* 196 */     if (null != this.player) {
/* 197 */       this.player.removeMediaPlayerListener(this);
/* 198 */       this.player.removeMediaErrorListener(this);
/* 199 */       this.player.removeVideoTrackSizeListener(this);
/* 200 */       this.player.removeBufferListener(this);
/* 201 */       this.player.getVideoRenderControl().removeVideoRendererListener(this.frameListener);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void prepareToPlay() {
/* 206 */     synchronized (this.lock) {
/* 207 */       if ((this.player == null) && (this.createThread != null) && (!this.createThread.isAlive()))
/* 208 */         this.createThread.start();
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void play()
/*     */   {
/* 214 */     MediaPlayer localMediaPlayer = getPlayer();
/* 215 */     if (localMediaPlayer != null) {
/* 216 */       localMediaPlayer.play();
/*     */ 
/* 218 */       notifyPaused(false);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void pause() {
/* 223 */     MediaPlayer localMediaPlayer = getPlayer();
/* 224 */     if (localMediaPlayer != null) {
/* 225 */       localMediaPlayer.pause();
/*     */ 
/* 227 */       notifyPaused(true);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected float getCurrentTime() {
/* 232 */     MediaPlayer localMediaPlayer = getPlayer();
/* 233 */     if (localMediaPlayer == null) {
/* 234 */       return 0.0F;
/*     */     }
/* 236 */     return this.finished > 0 ? (float)localMediaPlayer.getDuration() : this.finished == 0 ? (float)localMediaPlayer.getPresentationTime() : 0.0F;
/*     */   }
/*     */ 
/*     */   protected void seek(float paramFloat)
/*     */   {
/* 242 */     MediaPlayer localMediaPlayer = getPlayer();
/* 243 */     if (localMediaPlayer != null) {
/* 244 */       this.finished = 0;
/* 245 */       if (getReadyState() >= 1)
/* 246 */         notifySeeking(true, 1);
/*     */       else {
/* 248 */         notifySeeking(true, 0);
/*     */       }
/* 250 */       localMediaPlayer.seek(paramFloat);
/*     */ 
/* 254 */       final float f = paramFloat;
/* 255 */       Thread localThread = new Thread(new Runnable() {
/*     */         public void run() {
/* 257 */           while (WCMediaPlayerImpl.this.isSeeking()) {
/* 258 */             MediaPlayer localMediaPlayer = WCMediaPlayerImpl.this.getPlayer();
/* 259 */             if (localMediaPlayer == null) {
/*     */               break;
/*     */             }
/* 262 */             double d = localMediaPlayer.getPresentationTime();
/* 263 */             if ((f < 0.01D) || (Math.abs(d) >= 0.01D)) {
/* 264 */               WCMediaPlayerImpl.this.notifySeeking(false, 4);
/* 265 */               break;
/*     */             }
/*     */             try {
/* 268 */               Thread.sleep(10L);
/*     */             }
/*     */             catch (InterruptedException localInterruptedException)
/*     */             {
/*     */             }
/*     */           }
/*     */         }
/*     */       });
/* 274 */       localThread.setDaemon(true);
/* 275 */       localThread.start();
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void setRate(float paramFloat) {
/* 280 */     MediaPlayer localMediaPlayer = getPlayer();
/* 281 */     if (localMediaPlayer != null)
/* 282 */       localMediaPlayer.setRate(paramFloat);
/*     */   }
/*     */ 
/*     */   protected void setVolume(float paramFloat)
/*     */   {
/* 287 */     MediaPlayer localMediaPlayer = getPlayer();
/* 288 */     if (localMediaPlayer != null)
/* 289 */       localMediaPlayer.setVolume(paramFloat);
/*     */   }
/*     */ 
/*     */   protected void setMute(boolean paramBoolean)
/*     */   {
/* 294 */     MediaPlayer localMediaPlayer = getPlayer();
/* 295 */     if (localMediaPlayer != null)
/* 296 */       localMediaPlayer.setMute(paramBoolean);
/*     */   }
/*     */ 
/*     */   protected void setSize(int paramInt1, int paramInt2)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void setPreservesPitch(boolean paramBoolean)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void renderCurrentFrame(WCGraphicsContext paramWCGraphicsContext, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */   {
/* 310 */     synchronized (this.lock) {
/* 311 */       renderImpl(paramWCGraphicsContext, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void renderImpl(WCGraphicsContext paramWCGraphicsContext, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */   {
/* 317 */     if (verbose) log.log(Level.FINER, ">>(Prism)renderImpl");
/* 318 */     Graphics localGraphics = (Graphics)paramWCGraphicsContext.getPlatformGraphics();
/*     */ 
/* 320 */     Texture localTexture = null;
/* 321 */     VideoDataBuffer localVideoDataBuffer = this.frameListener.getLatestFrame();
/*     */ 
/* 323 */     if (null != localVideoDataBuffer) {
/* 324 */       if (null != this.frameHandler) {
/* 325 */         localTexture = this.frameHandler.getTexture(localGraphics, localVideoDataBuffer);
/*     */       }
/* 327 */       localVideoDataBuffer.releaseFrame();
/*     */     }
/*     */ 
/* 330 */     if (localTexture != null) {
/* 331 */       localGraphics.drawTexture(localTexture, paramInt1, paramInt2, paramInt1 + paramInt3, paramInt2 + paramInt4, 0.0F, 0.0F, localTexture.getContentWidth(), localTexture.getContentHeight());
/*     */     }
/*     */     else
/*     */     {
/* 335 */       if (verbose) log.log(Level.FINEST, "  (Prism)renderImpl, texture is null, draw black rect");
/* 336 */       paramWCGraphicsContext.fillRect(paramInt1, paramInt2, paramInt3, paramInt4, Integer.valueOf(-16777216));
/*     */     }
/* 338 */     if (verbose) log.log(Level.FINER, "<<(Prism)renderImpl");
/*     */   }
/*     */ 
/*     */   public void onReady(PlayerStateEvent paramPlayerStateEvent)
/*     */   {
/* 344 */     MediaPlayer localMediaPlayer = getPlayer();
/* 345 */     if (verbose) log.log(Level.FINE, "onReady");
/* 346 */     Media localMedia = localMediaPlayer.getMedia();
/* 347 */     boolean bool1 = false;
/* 348 */     boolean bool2 = false;
/* 349 */     if (localMedia != null) {
/* 350 */       List localList = localMedia.getTracks();
/* 351 */       if (localList != null) {
/* 352 */         if (verbose) log.log(Level.INFO, "{0} track(s) detected:", Integer.valueOf(localList.size()));
/* 353 */         for (Track localTrack : localList) {
/* 354 */           if ((localTrack instanceof VideoTrack))
/* 355 */             bool1 = true;
/* 356 */           else if ((localTrack instanceof AudioTrack)) {
/* 357 */             bool2 = true;
/*     */           }
/* 359 */           if (verbose) log.log(Level.INFO, "track: {0}", localTrack);
/*     */         }
/*     */       }
/* 362 */       else if (verbose) { log.log(Level.WARNING, "onReady, tracks IS NULL"); }
/*     */ 
/*     */     }
/* 365 */     else if (verbose) { log.log(Level.WARNING, "onReady, media IS NULL"); }
/*     */ 
/* 367 */     if (verbose) {
/* 368 */       log.log(Level.FINE, "onReady, hasVideo:{0}, hasAudio: {1}", new Object[] { Boolean.valueOf(bool1), Boolean.valueOf(bool2) });
/*     */     }
/*     */ 
/* 371 */     notifyReady(bool1, bool2, (float)localMediaPlayer.getDuration());
/*     */ 
/* 374 */     if (!bool1) {
/* 375 */       notifyReadyStateChanged(4);
/*     */     }
/* 377 */     else if (getReadyState() < 1)
/* 378 */       if (this.gotFirstFrame)
/* 379 */         notifyReadyStateChanged(4);
/*     */       else
/* 381 */         notifyReadyStateChanged(1);
/*     */   }
/*     */ 
/*     */   public void onPlaying(PlayerStateEvent paramPlayerStateEvent)
/*     */   {
/* 389 */     if (verbose) log.log(Level.FINE, "onPlaying");
/* 390 */     notifyPaused(false);
/*     */   }
/*     */ 
/*     */   public void onPause(PlayerStateEvent paramPlayerStateEvent)
/*     */   {
/* 395 */     if (verbose) log.log(Level.FINE, "onPause, time: {0}", Double.valueOf(paramPlayerStateEvent.getTime()));
/* 396 */     notifyPaused(true);
/*     */   }
/*     */ 
/*     */   public void onStop(PlayerStateEvent paramPlayerStateEvent)
/*     */   {
/* 401 */     if (verbose) log.log(Level.FINE, "onStop");
/* 402 */     notifyPaused(true);
/*     */   }
/*     */ 
/*     */   public void onStall(PlayerStateEvent paramPlayerStateEvent)
/*     */   {
/* 407 */     if (verbose) log.log(Level.FINE, "onStall");
/*     */   }
/*     */ 
/*     */   public void onFinish(PlayerStateEvent paramPlayerStateEvent)
/*     */   {
/* 412 */     MediaPlayer localMediaPlayer = getPlayer();
/* 413 */     if (localMediaPlayer != null) {
/* 414 */       this.finished = (localMediaPlayer.getRate() > 0.0F ? 1 : -1);
/* 415 */       if (verbose) log.log(Level.FINE, "onFinish, time: {0}", Double.valueOf(paramPlayerStateEvent.getTime()));
/* 416 */       notifyFinished();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void onHalt(PlayerStateEvent paramPlayerStateEvent)
/*     */   {
/* 422 */     if (verbose) log.log(Level.FINE, "onHalt");
/*     */   }
/*     */ 
/*     */   public void onError(Object paramObject, int paramInt, String paramString)
/*     */   {
/* 429 */     if (verbose) {
/* 430 */       log.log(Level.WARNING, "onError, errCode={0}, msg={1}", new Object[] { Integer.valueOf(paramInt), paramString });
/*     */     }
/*     */ 
/* 435 */     notifyNetworkStateChanged(5);
/* 436 */     notifyReadyStateChanged(0);
/*     */   }
/*     */ 
/*     */   public void onStopTimeReached(double paramDouble)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void onDurationChanged(double paramDouble)
/*     */   {
/* 446 */     if (verbose) log.log(Level.FINE, "onDurationChanged, duration={0}", Double.valueOf(paramDouble));
/* 447 */     notifyDurationChanged((float)paramDouble);
/*     */   }
/*     */ 
/*     */   public void onSizeChanged(int paramInt1, int paramInt2)
/*     */   {
/* 454 */     if (verbose) {
/* 455 */       log.log(Level.FINE, "onSizeChanged, new size = {0} x {1}", new Object[] { Integer.valueOf(paramInt1), Integer.valueOf(paramInt2) });
/*     */     }
/*     */ 
/* 458 */     notifySizeChanged(paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   private void notifyFrameArrived() {
/* 462 */     if (!this.gotFirstFrame)
/*     */     {
/* 465 */       if (getReadyState() >= 1) {
/* 466 */         notifyReadyStateChanged(4);
/*     */       }
/* 468 */       this.gotFirstFrame = true;
/*     */     }
/* 470 */     if ((verbose) && (this.finished != 0)) {
/* 471 */       log.log(Level.FINE, "notifyFrameArrived (after finished) time: {0}", Double.valueOf(getPlayer().getPresentationTime()));
/*     */     }
/* 473 */     notifyNewFrame();
/*     */   }
/*     */ 
/*     */   void updateBufferingStatus()
/*     */   {
/* 481 */     int i = this.bufferedStart > 0.0F ? 1 : this.buffering ? 2 : 3;
/*     */ 
/* 484 */     if (verbose) {
/* 485 */       log.log(Level.FINE, "updateBufferingStatus, buffered: [{0} - {1}], buffering = {2}", new Object[] { Float.valueOf(this.bufferedStart), Float.valueOf(this.bufferedEnd), Boolean.valueOf(this.buffering) });
/*     */     }
/*     */ 
/* 488 */     notifyNetworkStateChanged(i);
/*     */   }
/*     */ 
/*     */   public void onBufferProgress(BufferProgressEvent paramBufferProgressEvent)
/*     */   {
/* 501 */     if (paramBufferProgressEvent.getDuration() < 0.0D) {
/* 502 */       return;
/*     */     }
/* 504 */     double d = paramBufferProgressEvent.getDuration() / paramBufferProgressEvent.getBufferStop();
/* 505 */     this.bufferedStart = ((float)(d * paramBufferProgressEvent.getBufferStart()));
/* 506 */     this.bufferedEnd = ((float)(d * paramBufferProgressEvent.getBufferPosition()));
/* 507 */     this.buffering = (paramBufferProgressEvent.getBufferPosition() < paramBufferProgressEvent.getBufferStop());
/*     */ 
/* 509 */     float[] arrayOfFloat = new float[2];
/* 510 */     arrayOfFloat[0] = this.bufferedStart;
/* 511 */     arrayOfFloat[1] = this.bufferedEnd;
/* 512 */     int i = (int)(paramBufferProgressEvent.getBufferPosition() - paramBufferProgressEvent.getBufferStart());
/* 513 */     if (verbose) {
/* 514 */       log.log(Level.FINER, "onBufferProgress, bufferStart={0}, bufferStop={1}, bufferPos={2}, duration={3}; notify range [{4},[5]], bytesLoaded: {6}", new Object[] { Long.valueOf(paramBufferProgressEvent.getBufferStart()), Long.valueOf(paramBufferProgressEvent.getBufferStop()), Long.valueOf(paramBufferProgressEvent.getBufferPosition()), Double.valueOf(paramBufferProgressEvent.getDuration()), Float.valueOf(arrayOfFloat[0]), Float.valueOf(arrayOfFloat[1]), Integer.valueOf(i) });
/*     */     }
/*     */ 
/* 521 */     notifyBufferChanged(arrayOfFloat, i);
/* 522 */     updateBufferingStatus();
/*     */   }
/*     */ 
/*     */   private class MediaFrameListener implements VideoRendererListener
/*     */   {
/* 532 */     private final Object frameLock = new Object();
/*     */     private VideoDataBuffer currentFrame;
/*     */     private VideoDataBuffer nextFrame;
/*     */ 
/*     */     private MediaFrameListener() {
/*     */     }
/*     */ 
/*     */     public void videoFrameUpdated(NewFrameEvent paramNewFrameEvent) {
/* 537 */       synchronized (this.frameLock) {
/* 538 */         if (null != this.nextFrame) {
/* 539 */           this.nextFrame.releaseFrame();
/*     */         }
/* 541 */         this.nextFrame = paramNewFrameEvent.getFrameData();
/* 542 */         if (null != this.nextFrame) {
/* 543 */           this.nextFrame.holdFrame();
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 548 */       WCMediaPlayerImpl.this.notifyFrameArrived();
/*     */     }
/*     */ 
/*     */     public void releaseVideoFrames() {
/* 552 */       synchronized (this.frameLock) {
/* 553 */         if (null != this.nextFrame) {
/* 554 */           this.nextFrame.releaseFrame();
/* 555 */           this.nextFrame = null;
/*     */         }
/*     */ 
/* 558 */         if (null != this.currentFrame) {
/* 559 */           this.currentFrame.releaseFrame();
/* 560 */           this.currentFrame = null;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     public VideoDataBuffer getLatestFrame() {
/* 566 */       synchronized (this.frameLock) {
/* 567 */         if (null != this.nextFrame) {
/* 568 */           if (null != this.currentFrame) {
/* 569 */             this.currentFrame.releaseFrame();
/*     */           }
/* 571 */           this.currentFrame = this.nextFrame;
/* 572 */           this.nextFrame = null;
/*     */         }
/*     */ 
/* 576 */         if (null != this.currentFrame) {
/* 577 */           this.currentFrame.holdFrame();
/*     */         }
/* 579 */         return this.currentFrame;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private class CreateThread extends Thread
/*     */   {
/*  77 */     private boolean cancelled = false;
/*     */     private String url;
/*     */     private String userAgent;
/*     */ 
/*     */     public CreateThread(String paramString1, String arg3)
/*     */     {
/*  81 */       this.url = paramString1;
/*     */       Object localObject;
/*  82 */       this.userAgent = localObject;
/*  83 */       WCMediaPlayerImpl.this.gotFirstFrame = false;
/*     */     }
/*     */ 
/*     */     public void run()
/*     */     {
/*  88 */       if (WCMediaPlayerImpl.verbose) WCMediaPlayerImpl.log.log(Level.FINE, "CreateThread: started, url={0}", this.url);
/*     */ 
/*  90 */       WCMediaPlayerImpl.this.notifyNetworkStateChanged(2);
/*  91 */       WCMediaPlayerImpl.this.notifyReadyStateChanged(0);
/*     */ 
/*  93 */       MediaPlayer localMediaPlayer = null;
/*     */       try
/*     */       {
/*  96 */         Locator localLocator = new Locator(new URI(this.url));
/*  97 */         if (this.userAgent != null) {
/*  98 */           localLocator.setConnectionProperty("User-Agent", this.userAgent);
/*     */         }
/* 100 */         localLocator.init();
/* 101 */         if (WCMediaPlayerImpl.verbose) {
/* 102 */           WCMediaPlayerImpl.log.fine("CreateThread: locator created");
/*     */         }
/*     */ 
/* 105 */         localMediaPlayer = MediaManager.getPlayer(localLocator);
/*     */       } catch (Exception localException) {
/* 107 */         if (WCMediaPlayerImpl.verbose) {
/* 108 */           WCMediaPlayerImpl.log.log(Level.WARNING, "CreateThread ERROR: {0}", localException.toString());
/* 109 */           localException.printStackTrace(System.out);
/*     */         }
/* 111 */         WCMediaPlayerImpl.this.onError(this, 0, localException.getMessage());
/* 112 */         return;
/*     */       }
/*     */ 
/* 115 */       synchronized (WCMediaPlayerImpl.this.lock) {
/* 116 */         if (this.cancelled) {
/* 117 */           if (WCMediaPlayerImpl.verbose) WCMediaPlayerImpl.log.log(Level.FINE, "CreateThread: cancelled");
/* 118 */           localMediaPlayer.dispose();
/* 119 */           return;
/*     */         }
/* 121 */         WCMediaPlayerImpl.this.createThread = null;
/* 122 */         WCMediaPlayerImpl.this.setPlayer(localMediaPlayer);
/*     */       }
/* 124 */       if (WCMediaPlayerImpl.verbose) WCMediaPlayerImpl.log.log(Level.FINE, "CreateThread: completed"); 
/*     */     }
/*     */ 
/*     */     public void cancel()
/*     */     {
/* 128 */       synchronized (WCMediaPlayerImpl.this.lock) {
/* 129 */         this.cancelled = true;
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.sg.prism.WCMediaPlayerImpl
 * JD-Core Version:    0.6.2
 */