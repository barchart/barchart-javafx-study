/*     */ package com.sun.media.jfxmediaimpl;
/*     */ 
/*     */ import com.sun.media.jfxmedia.MediaManager;
/*     */ import com.sun.media.jfxmedia.MediaPlayer;
/*     */ import com.sun.media.jfxmedia.events.MediaErrorListener;
/*     */ import com.sun.media.jfxmedia.events.PlayerStateEvent;
/*     */ import com.sun.media.jfxmedia.events.PlayerStateListener;
/*     */ import com.sun.media.jfxmedia.locator.Locator;
/*     */ import com.sun.media.jfxmedia.logging.Logger;
/*     */ import java.net.URI;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.CountDownLatch;
/*     */ import java.util.concurrent.LinkedBlockingQueue;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ 
/*     */ public final class NativeMediaAudioClipPlayer
/*     */   implements PlayerStateListener, MediaErrorListener
/*     */ {
/*     */   private MediaPlayer mediaPlayer;
/*     */   private int playCount;
/*     */   private int loopCount;
/*     */   private boolean playing;
/*     */   private boolean ready;
/*     */   private NativeMediaAudioClip sourceClip;
/*     */   private double volume;
/*     */   private double balance;
/*     */   private double pan;
/*     */   private double rate;
/*     */   private int priority;
/*  43 */   private final ReentrantLock playerStateLock = new ReentrantLock();
/*     */   private static final int MAX_PLAYER_COUNT = 16;
/*  47 */   private static final List<NativeMediaAudioClipPlayer> activePlayers = new ArrayList(16);
/*     */ 
/*  49 */   private static final ReentrantLock playerListLock = new ReentrantLock();
/*     */   private static Thread schedulerThread;
/*  60 */   private static final LinkedBlockingQueue<SchedulerEntry> schedule = new LinkedBlockingQueue();
/*     */ 
/*     */   public static int getPlayerLimit()
/*     */   {
/*  52 */     return 16;
/*     */   }
/*     */ 
/*     */   public static int getPlayerCount() {
/*  56 */     return activePlayers.size();
/*     */   }
/*     */ 
/*     */   private static void clipScheduler()
/*     */   {
/*     */     while (true)
/*     */     {
/*  65 */       SchedulerEntry entry = null;
/*     */       try {
/*  67 */         entry = (SchedulerEntry)schedule.take();
/*     */       } catch (InterruptedException ie) {
/*     */       }
/*  70 */       if (null != entry) {
/*  71 */         if (entry.getCommand() == 0) {
/*  72 */           NativeMediaAudioClipPlayer player = entry.getPlayer();
/*  73 */           if (null != player)
/*     */           {
/*  75 */             if (addPlayer(player))
/*  76 */               player.play();
/*     */             else
/*  78 */               player.sourceClip.playFinished();
/*     */           }
/*     */         }
/*  81 */         else if (entry.getCommand() == 1)
/*     */         {
/*  84 */           List killList = null;
/*  85 */           URI sourceURI = entry.getClipURI();
/*     */ 
/*  87 */           playerListLock.lock();
/*     */           try
/*     */           {
/*  90 */             NativeMediaAudioClipPlayer[] players = new NativeMediaAudioClipPlayer[16];
/*  91 */             players = (NativeMediaAudioClipPlayer[])activePlayers.toArray(players);
/*  92 */             if (null != players)
/*  93 */               for (int index = 0; index < players.length; index++)
/*  94 */                 if ((null != players[index]) && ((null == sourceURI) || (players[index].source().getURI().equals(sourceURI))))
/*     */                 {
/*  97 */                   players[index].invalidate();
/*     */                 }
/*     */           }
/*     */           finally
/*     */           {
/* 102 */             playerListLock.unlock();
/*     */           }
/*     */ 
/* 106 */           if (null != sourceURI) {
/* 107 */             killList = new ArrayList();
/*     */           }
/* 109 */           synchronized (schedule) {
/* 110 */             for (SchedulerEntry killEntry : schedule) {
/* 111 */               NativeMediaAudioClipPlayer player = killEntry.getPlayer();
/*     */ 
/* 113 */               if ((sourceURI == null) || (player.sourceClip.getLocator().getURI().equals(sourceURI)))
/*     */               {
/* 116 */                 if (null != killList) {
/* 117 */                   killList.add(entry);
/*     */                 }
/*     */ 
/* 120 */                 player.sourceClip.playFinished();
/*     */               }
/*     */             }
/* 123 */             if (null != killList)
/* 124 */               schedule.removeAll(killList);
/*     */             else {
/* 126 */               schedule.clear();
/*     */             }
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 132 */         entry.signal();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void playClip(NativeMediaAudioClip clip, double volume, double balance, double rate, double pan, int loopCount, int priority)
/*     */   {
/* 142 */     synchronized (schedule) {
/* 143 */       if (null == schedulerThread)
/*     */       {
/* 145 */         schedulerThread = new Thread(new Runnable() {
/*     */           public void run() {
/* 147 */             NativeMediaAudioClipPlayer.access$000();
/*     */           }
/*     */         });
/* 150 */         schedulerThread.setDaemon(true);
/* 151 */         schedulerThread.start();
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 157 */     NativeMediaAudioClipPlayer newPlayer = new NativeMediaAudioClipPlayer(clip, volume, balance, rate, pan, loopCount, priority);
/* 158 */     SchedulerEntry entry = new SchedulerEntry(newPlayer);
/* 159 */     if (!schedule.contains(entry)) {
/* 160 */       schedule.offer(entry);
/*     */     }
/*     */     else
/* 163 */       clip.playFinished();
/*     */   }
/*     */ 
/*     */   private static boolean addPlayer(NativeMediaAudioClipPlayer newPlayer)
/*     */   {
/* 170 */     playerListLock.lock();
/*     */     try {
/* 172 */       int priority = newPlayer.priority();
/* 173 */       while (activePlayers.size() >= 16)
/*     */       {
/* 175 */         NativeMediaAudioClipPlayer target = null;
/* 176 */         for (NativeMediaAudioClipPlayer player : activePlayers) {
/* 177 */           if ((player.priority() <= priority) && ((target == null) || ((target.isReady()) && (player.priority() < target.priority()))))
/*     */           {
/* 181 */             target = player;
/*     */           }
/*     */         }
/* 184 */         if (null != target)
/*     */         {
/* 186 */           target.invalidate();
/*     */         }
/*     */         else {
/* 189 */           return 0;
/*     */         }
/*     */       }
/* 192 */       activePlayers.add(newPlayer);
/*     */     } finally {
/* 194 */       playerListLock.unlock();
/*     */     }
/* 196 */     return true;
/*     */   }
/*     */ 
/*     */   public static void stopPlayers(Locator source)
/*     */   {
/* 201 */     URI sourceURI = source != null ? source.getURI() : null;
/*     */ 
/* 205 */     if (null != schedulerThread)
/*     */     {
/* 209 */       CountDownLatch stopSignal = new CountDownLatch(1);
/* 210 */       SchedulerEntry entry = new SchedulerEntry(sourceURI, stopSignal);
/* 211 */       if (schedule.offer(entry))
/*     */       {
/*     */         try
/*     */         {
/* 215 */           stopSignal.await(5L, TimeUnit.SECONDS);
/*     */         }
/*     */         catch (InterruptedException ie) {
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private NativeMediaAudioClipPlayer(NativeMediaAudioClip clip, double volume, double balance, double rate, double pan, int loopCount, int priority) {
/* 224 */     this.sourceClip = clip;
/* 225 */     this.volume = volume;
/* 226 */     this.balance = balance;
/* 227 */     this.pan = pan;
/* 228 */     this.rate = rate;
/* 229 */     this.loopCount = loopCount;
/* 230 */     this.priority = priority;
/* 231 */     this.ready = false;
/*     */   }
/*     */ 
/*     */   private Locator source()
/*     */   {
/* 236 */     return this.sourceClip.getLocator();
/*     */   }
/*     */ 
/*     */   public double volume() {
/* 240 */     return this.volume;
/*     */   }
/*     */ 
/*     */   public void setVolume(double volume) {
/* 244 */     this.volume = volume;
/*     */   }
/*     */ 
/*     */   public double balance() {
/* 248 */     return this.balance;
/*     */   }
/*     */ 
/*     */   public void setBalance(double balance) {
/* 252 */     this.balance = balance;
/*     */   }
/*     */ 
/*     */   public double pan() {
/* 256 */     return this.pan;
/*     */   }
/*     */ 
/*     */   public void setPan(double pan) {
/* 260 */     this.pan = pan;
/*     */   }
/*     */ 
/*     */   public double playbackRate() {
/* 264 */     return this.rate;
/*     */   }
/*     */ 
/*     */   public void setPlaybackRate(double rate) {
/* 268 */     this.rate = rate;
/*     */   }
/*     */ 
/*     */   public int priority() {
/* 272 */     return this.priority;
/*     */   }
/*     */ 
/*     */   public void setPriority(int priority) {
/* 276 */     this.priority = priority;
/*     */   }
/*     */ 
/*     */   public int loopCount() {
/* 280 */     return this.loopCount;
/*     */   }
/*     */ 
/*     */   public void setLoopCount(int loopCount) {
/* 284 */     this.loopCount = loopCount;
/*     */   }
/*     */ 
/*     */   public boolean isPlaying() {
/* 288 */     return this.playing;
/*     */   }
/*     */ 
/*     */   private boolean isReady() {
/* 292 */     return this.ready;
/*     */   }
/*     */ 
/*     */   public synchronized void play() {
/* 296 */     this.playerStateLock.lock();
/*     */     try {
/* 298 */       this.playing = true;
/* 299 */       this.playCount = 0;
/*     */ 
/* 301 */       if (null == this.mediaPlayer) {
/* 302 */         this.mediaPlayer = MediaManager.getPlayer(source());
/* 303 */         this.mediaPlayer.addMediaPlayerListener(this);
/* 304 */         this.mediaPlayer.addMediaErrorListener(this);
/*     */       } else {
/* 306 */         this.mediaPlayer.play();
/*     */       }
/*     */     } finally {
/* 309 */       this.playerStateLock.unlock();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void stop() {
/* 314 */     invalidate();
/*     */   }
/*     */ 
/*     */   public synchronized void invalidate() {
/* 318 */     this.playerStateLock.lock();
/* 319 */     playerListLock.lock();
/*     */     try
/*     */     {
/* 322 */       this.playing = false;
/* 323 */       this.playCount = 0;
/* 324 */       this.ready = false;
/*     */ 
/* 326 */       activePlayers.remove(this);
/* 327 */       this.sourceClip.playFinished();
/*     */ 
/* 329 */       if (null != this.mediaPlayer) {
/* 330 */         this.mediaPlayer.dispose();
/* 331 */         this.mediaPlayer = null;
/*     */       }
/*     */     }
/*     */     catch (Throwable t) {
/*     */     }
/*     */     finally {
/* 337 */       this.playerStateLock.unlock();
/* 338 */       playerListLock.unlock();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void onReady(PlayerStateEvent evt) {
/* 343 */     this.playerStateLock.lock();
/*     */     try {
/* 345 */       this.ready = true;
/* 346 */       if (this.playing) {
/* 347 */         this.mediaPlayer.setVolume((float)this.volume);
/* 348 */         this.mediaPlayer.setBalance((float)this.balance);
/* 349 */         this.mediaPlayer.setRate((float)this.rate);
/* 350 */         this.mediaPlayer.play();
/*     */       }
/*     */     } finally {
/* 353 */       this.playerStateLock.unlock();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void onPlaying(PlayerStateEvent evt) {
/*     */   }
/*     */ 
/*     */   public void onPause(PlayerStateEvent evt) {
/*     */   }
/*     */ 
/*     */   public void onStop(PlayerStateEvent evt) {
/* 364 */     invalidate();
/*     */   }
/*     */ 
/*     */   public void onStall(PlayerStateEvent evt) {
/*     */   }
/*     */ 
/*     */   public void onFinish(PlayerStateEvent evt) {
/* 371 */     this.playerStateLock.lock();
/*     */     try {
/* 373 */       if (this.playing)
/* 374 */         if (this.loopCount != -1) {
/* 375 */           this.playCount += 1;
/* 376 */           if (this.playCount <= this.loopCount)
/* 377 */             this.mediaPlayer.seek(0.0D);
/*     */           else
/* 379 */             invalidate();
/*     */         }
/*     */         else {
/* 382 */           this.mediaPlayer.seek(0.0D);
/*     */         }
/*     */     }
/*     */     finally {
/* 386 */       this.playerStateLock.unlock();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void onHalt(PlayerStateEvent evt) {
/* 391 */     invalidate();
/*     */   }
/*     */ 
/*     */   public void onWarning(Object source, String message) {
/*     */   }
/*     */ 
/*     */   public void onError(Object source, int errorCode, String message) {
/* 398 */     if (Logger.canLog(4)) {
/* 399 */       Logger.logMsg(4, "Error with AudioClip player: code " + errorCode + " : " + message);
/*     */     }
/* 401 */     invalidate();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object other)
/*     */   {
/* 406 */     if ((other == null) || (!(other instanceof NativeMediaAudioClipPlayer))) {
/* 407 */       return false;
/*     */     }
/*     */ 
/* 410 */     NativeMediaAudioClipPlayer otherPlayer = (NativeMediaAudioClipPlayer)other;
/* 411 */     URI myURI = this.sourceClip.getLocator().getURI();
/* 412 */     URI otherURI = otherPlayer.sourceClip.getLocator().getURI();
/*     */ 
/* 414 */     return (myURI.equals(otherURI)) && (this.priority == otherPlayer.priority) && (this.loopCount == otherPlayer.loopCount) && (this.volume == otherPlayer.volume) && (this.balance == otherPlayer.balance) && (this.rate == otherPlayer.rate) && (this.pan == otherPlayer.pan);
/*     */   }
/*     */ 
/*     */   private static class SchedulerEntry
/*     */   {
/*     */     private final int command;
/*     */     private final NativeMediaAudioClipPlayer player;
/*     */     private final URI clipURI;
/*     */     private final CountDownLatch commandSignal;
/*     */ 
/*     */     public SchedulerEntry(NativeMediaAudioClipPlayer player)
/*     */     {
/* 431 */       this.command = 0;
/* 432 */       this.player = player;
/* 433 */       this.clipURI = null;
/* 434 */       this.commandSignal = null;
/*     */     }
/*     */ 
/*     */     public SchedulerEntry(URI sourceURI, CountDownLatch signal)
/*     */     {
/* 439 */       this.command = 1;
/* 440 */       this.player = null;
/* 441 */       this.clipURI = sourceURI;
/* 442 */       this.commandSignal = signal;
/*     */     }
/*     */ 
/*     */     public int getCommand() {
/* 446 */       return this.command;
/*     */     }
/*     */ 
/*     */     public NativeMediaAudioClipPlayer getPlayer() {
/* 450 */       return this.player;
/*     */     }
/*     */ 
/*     */     public URI getClipURI() {
/* 454 */       return this.clipURI;
/*     */     }
/*     */ 
/*     */     public void signal() {
/* 458 */       if (null != this.commandSignal)
/* 459 */         this.commandSignal.countDown();
/*     */     }
/*     */ 
/*     */     public boolean equals(Object other)
/*     */     {
/* 466 */       if (((other instanceof SchedulerEntry)) && 
/* 467 */         (null != this.player)) {
/* 468 */         return this.player.equals(((SchedulerEntry)other).getPlayer());
/*     */       }
/*     */ 
/* 471 */       return false;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.media.jfxmediaimpl.NativeMediaAudioClipPlayer
 * JD-Core Version:    0.6.2
 */