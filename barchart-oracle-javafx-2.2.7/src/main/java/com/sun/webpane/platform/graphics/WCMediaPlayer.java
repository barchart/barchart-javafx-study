/*     */ package com.sun.webpane.platform.graphics;
/*     */ 
/*     */ import com.sun.webpane.platform.Invoker;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ public abstract class WCMediaPlayer extends Ref
/*     */ {
/*  16 */   protected static final Logger log = Logger.getLogger("webkit.mediaplayer");
/*     */   protected static final boolean verbose;
/*     */   private long nPtr;
/*     */   protected static final int NETWORK_STATE_EMPTY = 0;
/*     */   protected static final int NETWORK_STATE_IDLE = 1;
/*     */   protected static final int NETWORK_STATE_LOADING = 2;
/*     */   protected static final int NETWORK_STATE_LOADED = 3;
/*     */   protected static final int NETWORK_STATE_FORMAT_ERROR = 4;
/*     */   protected static final int NETWORK_STATE_NETWORK_ERROR = 5;
/*     */   protected static final int NETWORK_STATE_DECODE_ERROR = 6;
/*     */   protected static final int READY_STATE_HAVE_NOTHING = 0;
/*     */   protected static final int READY_STATE_HAVE_METADATA = 1;
/*     */   protected static final int READY_STATE_HAVE_CURRENT_DATA = 2;
/*     */   protected static final int READY_STATE_HAVE_FUTURE_DATA = 3;
/*     */   protected static final int READY_STATE_HAVE_ENOUGH_DATA = 4;
/*     */   protected static final int PRELOAD_NONE = 0;
/*     */   protected static final int PRELOAD_METADATA = 1;
/*     */   protected static final int PRELOAD_AUTO = 2;
/* 119 */   private int networkState = 0;
/* 120 */   private int readyState = 0;
/* 121 */   private int preload = 2;
/* 122 */   private boolean paused = true;
/* 123 */   private boolean seeking = false;
/*     */ 
/* 245 */   private Runnable newFrameNotifier = new Runnable()
/*     */   {
/*     */     public void run() {
/* 248 */       if (WCMediaPlayer.this.nPtr != 0L)
/* 249 */         WCMediaPlayer.this.notifyNewFrame(WCMediaPlayer.this.nPtr);
/*     */     }
/* 245 */   };
/*     */ 
/* 341 */   private boolean preserve = true;
/*     */ 
/*     */   void setNativePointer(long paramLong)
/*     */   {
/*  37 */     if (paramLong == 0L) {
/*  38 */       throw new IllegalArgumentException("nativePointer is 0");
/*     */     }
/*  40 */     if (this.nPtr != 0L) {
/*  41 */       throw new IllegalStateException("nPtr is not 0");
/*     */     }
/*  43 */     this.nPtr = paramLong;
/*     */   }
/*     */   protected abstract void load(String paramString1, String paramString2);
/*     */ 
/*     */   protected abstract void cancelLoad();
/*     */ 
/*     */   protected abstract void disposePlayer();
/*     */ 
/*     */   protected abstract void prepareToPlay();
/*     */ 
/*     */   protected abstract void play();
/*     */ 
/*     */   protected abstract void pause();
/*     */ 
/*     */   protected abstract float getCurrentTime();
/*     */ 
/*     */   protected abstract void seek(float paramFloat);
/*     */ 
/*     */   protected abstract void setRate(float paramFloat);
/*     */ 
/*     */   protected abstract void setVolume(float paramFloat);
/*     */ 
/*     */   protected abstract void setMute(boolean paramBoolean);
/*     */ 
/*     */   protected abstract void setSize(int paramInt1, int paramInt2);
/*     */ 
/*     */   protected abstract void setPreservesPitch(boolean paramBoolean);
/*     */ 
/*     */   protected abstract void renderCurrentFrame(WCGraphicsContext paramWCGraphicsContext, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*     */ 
/*  73 */   protected boolean getPreservesPitch() { return this.preserve; }
/*     */ 
/*     */   protected int getNetworkState()
/*     */   {
/*  77 */     return this.networkState;
/*     */   }
/*     */ 
/*     */   protected int getReadyState() {
/*  81 */     return this.readyState;
/*     */   }
/*     */ 
/*     */   protected int getPreload() {
/*  85 */     return this.preload;
/*     */   }
/*     */ 
/*     */   protected boolean isPaused() {
/*  89 */     return this.paused;
/*     */   }
/*     */ 
/*     */   protected boolean isSeeking() {
/*  93 */     return this.seeking;
/*     */   }
/*     */ 
/*     */   protected void notifyNetworkStateChanged(int paramInt)
/*     */   {
/* 127 */     if (this.networkState != paramInt) {
/* 128 */       this.networkState = paramInt;
/* 129 */       final int i = paramInt;
/* 130 */       Invoker.getInvoker().invokeOnEventThread(new Runnable()
/*     */       {
/*     */         public void run() {
/* 133 */           if (WCMediaPlayer.this.nPtr != 0L)
/* 134 */             WCMediaPlayer.this.notifyNetworkStateChanged(WCMediaPlayer.this.nPtr, i);
/*     */         }
/*     */       });
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void notifyReadyStateChanged(int paramInt)
/*     */   {
/* 142 */     if (this.readyState != paramInt) {
/* 143 */       this.readyState = paramInt;
/* 144 */       final int i = paramInt;
/* 145 */       Invoker.getInvoker().invokeOnEventThread(new Runnable()
/*     */       {
/*     */         public void run() {
/* 148 */           if (WCMediaPlayer.this.nPtr != 0L)
/* 149 */             WCMediaPlayer.this.notifyReadyStateChanged(WCMediaPlayer.this.nPtr, i);
/*     */         }
/*     */       });
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void notifyPaused(boolean paramBoolean)
/*     */   {
/* 157 */     if (verbose) log.log(Level.FINE, "notifyPaused, {0} => {1}", new Object[] { Boolean.valueOf(this.paused), Boolean.valueOf(paramBoolean) });
/*     */ 
/* 159 */     if (this.paused != paramBoolean) {
/* 160 */       this.paused = paramBoolean;
/* 161 */       final boolean bool = paramBoolean;
/* 162 */       Invoker.getInvoker().invokeOnEventThread(new Runnable()
/*     */       {
/*     */         public void run() {
/* 165 */           if (WCMediaPlayer.this.nPtr != 0L)
/* 166 */             WCMediaPlayer.this.notifyPaused(WCMediaPlayer.this.nPtr, bool);
/*     */         }
/*     */       });
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void notifySeeking(boolean paramBoolean, int paramInt)
/*     */   {
/* 175 */     if (verbose) log.log(Level.FINE, "notifySeeking, {0} => {1}", new Object[] { Boolean.valueOf(this.seeking), Boolean.valueOf(paramBoolean) });
/*     */ 
/* 177 */     if ((this.seeking != paramBoolean) || (this.readyState != paramInt)) {
/* 178 */       this.seeking = paramBoolean;
/* 179 */       this.readyState = paramInt;
/* 180 */       final boolean bool = paramBoolean;
/* 181 */       final int i = paramInt;
/* 182 */       Invoker.getInvoker().invokeOnEventThread(new Runnable()
/*     */       {
/*     */         public void run() {
/* 185 */           if (WCMediaPlayer.this.nPtr != 0L)
/* 186 */             WCMediaPlayer.this.notifySeeking(WCMediaPlayer.this.nPtr, bool, i);
/*     */         }
/*     */       });
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void notifyFinished()
/*     */   {
/* 194 */     Invoker.getInvoker().invokeOnEventThread(new Runnable()
/*     */     {
/*     */       public void run() {
/* 197 */         if (WCMediaPlayer.this.nPtr != 0L)
/* 198 */           WCMediaPlayer.this.notifyFinished(WCMediaPlayer.this.nPtr);
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   protected void notifyReady(boolean paramBoolean1, boolean paramBoolean2, float paramFloat)
/*     */   {
/* 206 */     final boolean bool1 = paramBoolean1;
/* 207 */     final boolean bool2 = paramBoolean2;
/* 208 */     final float f = paramFloat;
/* 209 */     Invoker.getInvoker().invokeOnEventThread(new Runnable()
/*     */     {
/*     */       public void run() {
/* 212 */         if (WCMediaPlayer.this.nPtr != 0L)
/* 213 */           WCMediaPlayer.this.notifyReady(WCMediaPlayer.this.nPtr, bool1, bool2, f);
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   protected void notifyDurationChanged(float paramFloat)
/*     */   {
/* 220 */     final float f = paramFloat;
/* 221 */     Invoker.getInvoker().invokeOnEventThread(new Runnable()
/*     */     {
/*     */       public void run() {
/* 224 */         if (WCMediaPlayer.this.nPtr != 0L)
/* 225 */           WCMediaPlayer.this.notifyDurationChanged(WCMediaPlayer.this.nPtr, f);
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   protected void notifySizeChanged(int paramInt1, int paramInt2)
/*     */   {
/* 233 */     final int i = paramInt1;
/* 234 */     final int j = paramInt2;
/* 235 */     Invoker.getInvoker().invokeOnEventThread(new Runnable()
/*     */     {
/*     */       public void run() {
/* 238 */         if (WCMediaPlayer.this.nPtr != 0L)
/* 239 */           WCMediaPlayer.this.notifySizeChanged(WCMediaPlayer.this.nPtr, i, j);
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   protected void notifyNewFrame()
/*     */   {
/* 255 */     Invoker.getInvoker().invokeOnEventThread(this.newFrameNotifier);
/*     */   }
/*     */ 
/*     */   protected void notifyBufferChanged(float[] paramArrayOfFloat, int paramInt)
/*     */   {
/* 261 */     final float[] arrayOfFloat = paramArrayOfFloat;
/* 262 */     final int i = paramInt;
/* 263 */     Invoker.getInvoker().invokeOnEventThread(new Runnable()
/*     */     {
/*     */       public void run() {
/* 266 */         if (WCMediaPlayer.this.nPtr != 0L)
/* 267 */           WCMediaPlayer.this.notifyBufferChanged(WCMediaPlayer.this.nPtr, arrayOfFloat, i);
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   private void fwkLoad(String paramString1, String paramString2)
/*     */   {
/* 279 */     if (verbose) log.log(Level.FINE, "fwkLoad, url={0}, userAgent={1}", new Object[] { paramString1, paramString2 });
/* 280 */     load(paramString1, paramString2);
/*     */   }
/*     */ 
/*     */   private void fwkCancelLoad() {
/* 284 */     if (verbose) log.log(Level.FINE, "fwkCancelLoad");
/* 285 */     cancelLoad();
/*     */   }
/*     */ 
/*     */   private void fwkPrepareToPlay() {
/* 289 */     if (verbose) log.log(Level.FINE, "fwkPrepareToPlay");
/* 290 */     prepareToPlay();
/*     */   }
/*     */ 
/*     */   private void fwkDispose() {
/* 294 */     if (verbose) log.log(Level.FINE, "fwkDispose");
/* 295 */     this.nPtr = 0L;
/* 296 */     cancelLoad();
/* 297 */     disposePlayer();
/*     */   }
/*     */ 
/*     */   private void fwkPlay() {
/* 301 */     if (verbose) log.log(Level.FINE, "fwkPlay");
/* 302 */     play();
/*     */   }
/*     */ 
/*     */   private void fwkPause() {
/* 306 */     if (verbose) log.log(Level.FINE, "fwkPause");
/* 307 */     pause();
/*     */   }
/*     */ 
/*     */   private float fwkGetCurrentTime() {
/* 311 */     float f = getCurrentTime();
/* 312 */     if (verbose) log.log(Level.FINER, "fwkGetCurrentTime(), return {0}", Float.valueOf(f));
/* 313 */     return f;
/*     */   }
/*     */ 
/*     */   private void fwkSeek(float paramFloat) {
/* 317 */     if (verbose) log.log(Level.FINE, "fwkSeek({0})", Float.valueOf(paramFloat));
/* 318 */     seek(paramFloat);
/*     */   }
/*     */ 
/*     */   private void fwkSetRate(float paramFloat) {
/* 322 */     if (verbose) log.log(Level.FINE, "fwkSetRate({0})", Float.valueOf(paramFloat));
/* 323 */     setRate(paramFloat);
/*     */   }
/*     */ 
/*     */   private void fwkSetVolume(float paramFloat) {
/* 327 */     if (verbose) log.log(Level.FINE, "fwkSetVolume({0})", Float.valueOf(paramFloat));
/* 328 */     setVolume(paramFloat);
/*     */   }
/*     */ 
/*     */   private void fwkSetMute(boolean paramBoolean) {
/* 332 */     if (verbose) log.log(Level.FINE, "fwkSetMute({0})", Boolean.valueOf(paramBoolean));
/* 333 */     setMute(paramBoolean);
/*     */   }
/*     */ 
/*     */   private void fwkSetSize(int paramInt1, int paramInt2)
/*     */   {
/* 338 */     setSize(paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   private void fwkSetPreservesPitch(boolean paramBoolean)
/*     */   {
/* 344 */     if (verbose) log.log(Level.FINE, "setPreservesPitch({0})", Boolean.valueOf(paramBoolean));
/*     */ 
/* 346 */     this.preserve = paramBoolean;
/* 347 */     setPreservesPitch(paramBoolean);
/*     */   }
/*     */ 
/*     */   private void fwkSetPreload(int paramInt)
/*     */   {
/* 352 */     if (verbose) {
/* 353 */       log.log(Level.FINE, "fwkSetPreload({0})", "INVALID VALUE: " + paramInt);
/*     */     }
/*     */ 
/* 359 */     this.preload = paramInt;
/*     */   }
/*     */ 
/*     */   public void render(WCGraphicsContext paramWCGraphicsContext, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
/*     */   {
/* 364 */     if (verbose) {
/* 365 */       log.log(Level.FINER, "render(x={0}, y={1}, w={2}, h={3}", new Object[] { Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), Integer.valueOf(paramInt3), Integer.valueOf(paramInt4) });
/*     */     }
/*     */ 
/* 368 */     renderCurrentFrame(paramWCGraphicsContext, paramInt1, paramInt2, paramInt3, paramInt4);
/*     */   }
/*     */ 
/*     */   private native void notifyNetworkStateChanged(long paramLong, int paramInt);
/*     */ 
/*     */   private native void notifyReadyStateChanged(long paramLong, int paramInt);
/*     */ 
/*     */   private native void notifyPaused(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   private native void notifySeeking(long paramLong, boolean paramBoolean, int paramInt);
/*     */ 
/*     */   private native void notifyFinished(long paramLong);
/*     */ 
/*     */   private native void notifyReady(long paramLong, boolean paramBoolean1, boolean paramBoolean2, float paramFloat);
/*     */ 
/*     */   private native void notifyDurationChanged(long paramLong, float paramFloat);
/*     */ 
/*     */   private native void notifySizeChanged(long paramLong, int paramInt1, int paramInt2);
/*     */ 
/*     */   private native void notifyNewFrame(long paramLong);
/*     */ 
/*     */   private native void notifyBufferChanged(long paramLong, float[] paramArrayOfFloat, int paramInt);
/*     */ 
/*     */   static
/*     */   {
/*  17 */     if (log.getLevel() == null)
/*     */     {
/*  20 */       verbose = false;
/*  21 */       log.setLevel(Level.OFF);
/*     */     } else {
/*  23 */       verbose = true;
/*  24 */       log.log(Level.CONFIG, "webkit.mediaplayer logging is ON, level: {0}", log.getLevel());
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.platform.graphics.WCMediaPlayer
 * JD-Core Version:    0.6.2
 */