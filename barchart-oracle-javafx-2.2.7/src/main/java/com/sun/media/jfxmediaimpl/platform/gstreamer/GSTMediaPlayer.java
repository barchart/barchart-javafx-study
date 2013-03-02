/*     */ package com.sun.media.jfxmediaimpl.platform.gstreamer;
/*     */ 
/*     */ import com.sun.media.jfxmedia.MediaError;
/*     */ import com.sun.media.jfxmedia.MediaException;
/*     */ import com.sun.media.jfxmedia.effects.AudioEqualizer;
/*     */ import com.sun.media.jfxmedia.effects.AudioSpectrum;
/*     */ import com.sun.media.jfxmedia.locator.Locator;
/*     */ import com.sun.media.jfxmediaimpl.NativeMediaPlayer;
/*     */ 
/*     */ final class GSTMediaPlayer extends NativeMediaPlayer
/*     */ {
/*  18 */   private GSTMedia gstMedia = null;
/*  19 */   private float mutedVolume = 1.0F;
/*  20 */   private boolean muteEnabled = false;
/*  21 */   private boolean isStopTimeSet = false;
/*     */   private AudioEqualizer audioEqualizer;
/*     */   private AudioSpectrum audioSpectrum;
/*     */ 
/*     */   private GSTMediaPlayer(GSTMedia sourceMedia)
/*     */   {
/*  26 */     super(sourceMedia);
/*  27 */     this.gstMedia = sourceMedia;
/*     */ 
/*  29 */     int rc = gstInitPlayer(this.gstMedia.getNativeMediaRef());
/*  30 */     if (0 != rc) {
/*  31 */       throwMediaErrorException(rc, null);
/*     */     }
/*     */ 
/*  34 */     this.audioSpectrum = new GSTAudioSpectrum(this.gstMedia.getNativeMediaRef());
/*  35 */     this.audioEqualizer = new GSTAudioEqualizer(this.gstMedia.getNativeMediaRef());
/*     */   }
/*     */ 
/*     */   GSTMediaPlayer(Locator source) {
/*  39 */     this(new GSTMedia(source));
/*     */   }
/*     */ 
/*     */   public AudioEqualizer getEqualizer()
/*     */   {
/*  44 */     return this.audioEqualizer;
/*     */   }
/*     */ 
/*     */   public AudioSpectrum getAudioSpectrum()
/*     */   {
/*  49 */     return this.audioSpectrum;
/*     */   }
/*     */ 
/*     */   private void throwMediaErrorException(int code, String message)
/*     */     throws MediaException
/*     */   {
/*  56 */     MediaError me = MediaError.getFromCode(code);
/*  57 */     throw new MediaException(message, null, me);
/*     */   }
/*     */ 
/*     */   protected long playerGetAudioSyncDelay() throws MediaException {
/*  61 */     long[] audioSyncDelay = new long[1];
/*  62 */     int rc = gstGetAudioSyncDelay(this.gstMedia.getNativeMediaRef(), audioSyncDelay);
/*  63 */     if (0 != rc) {
/*  64 */       throwMediaErrorException(rc, null);
/*     */     }
/*  66 */     return audioSyncDelay[0];
/*     */   }
/*     */ 
/*     */   protected void playerSetAudioSyncDelay(long delay) throws MediaException {
/*  70 */     int rc = gstSetAudioSyncDelay(this.gstMedia.getNativeMediaRef(), delay);
/*  71 */     if (0 != rc)
/*  72 */       throwMediaErrorException(rc, null);
/*     */   }
/*     */ 
/*     */   protected void playerPlay() throws MediaException
/*     */   {
/*  77 */     int rc = gstPlay(this.gstMedia.getNativeMediaRef());
/*  78 */     if (0 != rc)
/*  79 */       throwMediaErrorException(rc, null);
/*     */   }
/*     */ 
/*     */   protected void playerStop() throws MediaException
/*     */   {
/*  84 */     int rc = gstStop(this.gstMedia.getNativeMediaRef());
/*  85 */     if (0 != rc)
/*  86 */       throwMediaErrorException(rc, null);
/*     */   }
/*     */ 
/*     */   protected void playerPause() throws MediaException
/*     */   {
/*  91 */     int rc = gstPause(this.gstMedia.getNativeMediaRef());
/*  92 */     if (0 != rc)
/*  93 */       throwMediaErrorException(rc, null);
/*     */   }
/*     */ 
/*     */   protected float playerGetRate() throws MediaException
/*     */   {
/*  98 */     float[] rate = new float[1];
/*  99 */     int rc = gstGetRate(this.gstMedia.getNativeMediaRef(), rate);
/* 100 */     if (0 != rc) {
/* 101 */       throwMediaErrorException(rc, null);
/*     */     }
/* 103 */     return rate[0];
/*     */   }
/*     */ 
/*     */   protected void playerSetRate(float rate) throws MediaException {
/* 107 */     int rc = gstSetRate(this.gstMedia.getNativeMediaRef(), rate);
/* 108 */     if (0 != rc)
/* 109 */       throwMediaErrorException(rc, null);
/*     */   }
/*     */ 
/*     */   protected double playerGetPresentationTime() throws MediaException
/*     */   {
/* 114 */     double[] presentationTime = new double[1];
/* 115 */     int rc = gstGetPresentationTime(this.gstMedia.getNativeMediaRef(), presentationTime);
/* 116 */     if (0 != rc) {
/* 117 */       throwMediaErrorException(rc, null);
/*     */     }
/* 119 */     return presentationTime[0];
/*     */   }
/*     */ 
/*     */   protected boolean playerGetMute() throws MediaException {
/* 123 */     return this.muteEnabled;
/*     */   }
/*     */ 
/*     */   protected synchronized void playerSetMute(boolean enable) throws MediaException {
/* 127 */     if (enable != this.muteEnabled)
/* 128 */       if (enable)
/*     */       {
/* 130 */         float currentVolume = getVolume();
/*     */ 
/* 133 */         playerSetVolume(0.0F);
/*     */ 
/* 138 */         this.muteEnabled = true;
/*     */ 
/* 141 */         this.mutedVolume = currentVolume;
/*     */       }
/*     */       else
/*     */       {
/* 147 */         this.muteEnabled = false;
/*     */ 
/* 150 */         playerSetVolume(this.mutedVolume);
/*     */       }
/*     */   }
/*     */ 
/*     */   protected float playerGetVolume() throws MediaException
/*     */   {
/* 156 */     synchronized (this) {
/* 157 */       if (this.muteEnabled)
/* 158 */         return this.mutedVolume;
/*     */     }
/* 160 */     float[] volume = new float[1];
/* 161 */     int rc = gstGetVolume(this.gstMedia.getNativeMediaRef(), volume);
/* 162 */     if (0 != rc) {
/* 163 */       throwMediaErrorException(rc, null);
/*     */     }
/* 165 */     return volume[0];
/*     */   }
/*     */ 
/*     */   protected synchronized void playerSetVolume(float volume) throws MediaException {
/* 169 */     if (!this.muteEnabled) {
/* 170 */       int rc = gstSetVolume(this.gstMedia.getNativeMediaRef(), volume);
/* 171 */       if (0 != rc)
/* 172 */         throwMediaErrorException(rc, null);
/*     */       else
/* 174 */         this.mutedVolume = volume;
/*     */     }
/*     */     else {
/* 177 */       this.mutedVolume = volume;
/*     */     }
/*     */   }
/*     */ 
/*     */   protected float playerGetBalance() throws MediaException {
/* 182 */     float[] balance = new float[1];
/* 183 */     int rc = gstGetBalance(this.gstMedia.getNativeMediaRef(), balance);
/* 184 */     if (0 != rc) {
/* 185 */       throwMediaErrorException(rc, null);
/*     */     }
/* 187 */     return balance[0];
/*     */   }
/*     */ 
/*     */   protected void playerSetBalance(float balance) throws MediaException {
/* 191 */     int rc = gstSetBalance(this.gstMedia.getNativeMediaRef(), balance);
/* 192 */     if (0 != rc)
/* 193 */       throwMediaErrorException(rc, null);
/*     */   }
/*     */ 
/*     */   protected double playerGetDuration() throws MediaException
/*     */   {
/* 198 */     double[] duration = new double[1];
/* 199 */     int rc = gstGetDuration(this.gstMedia.getNativeMediaRef(), duration);
/* 200 */     if (0 != rc) {
/* 201 */       throwMediaErrorException(rc, null);
/*     */     }
/* 203 */     if (duration[0] == -1.0D) {
/* 204 */       return (1.0D / 0.0D);
/*     */     }
/* 206 */     return duration[0];
/*     */   }
/*     */ 
/*     */   protected synchronized double playerGetStartTime() throws MediaException
/*     */   {
/* 211 */     double[] startTime = new double[1];
/* 212 */     int rc = gstGetStartTime(this.gstMedia.getNativeMediaRef(), startTime);
/* 213 */     if (0 != rc) {
/* 214 */       throwMediaErrorException(rc, null);
/* 215 */       startTime[0] = 0.0D;
/*     */     }
/* 217 */     return startTime[0];
/*     */   }
/*     */ 
/*     */   protected synchronized void playerSetStartTime(double startTime) throws MediaException {
/* 221 */     int rc = gstSetStartTime(this.gstMedia.getNativeMediaRef(), startTime);
/* 222 */     if (0 != rc)
/* 223 */       throwMediaErrorException(rc, null);
/*     */   }
/*     */ 
/*     */   protected synchronized double playerGetStopTime() throws MediaException
/*     */   {
/* 228 */     double[] stopTime = new double[1];
/* 229 */     int rc = gstGetStopTime(this.gstMedia.getNativeMediaRef(), stopTime);
/* 230 */     if (0 != rc) {
/* 231 */       throwMediaErrorException(rc, null);
/* 232 */       stopTime[0] = 1.7976931348623157E+308D;
/*     */     }
/* 234 */     return stopTime[0];
/*     */   }
/*     */ 
/*     */   protected synchronized void playerSetStopTime(double stopTime) throws MediaException {
/* 238 */     int rc = gstSetStopTime(this.gstMedia.getNativeMediaRef(), stopTime);
/* 239 */     if (0 != rc) {
/* 240 */       throwMediaErrorException(rc, null);
/*     */     }
/* 242 */     this.isStopTimeSet = true;
/*     */   }
/*     */ 
/*     */   protected void playerSeek(double streamTime) throws MediaException {
/* 246 */     int rc = gstSeek(this.gstMedia.getNativeMediaRef(), streamTime);
/* 247 */     if (0 != rc)
/* 248 */       throwMediaErrorException(rc, null);
/*     */   }
/*     */ 
/*     */   protected void playerInit()
/*     */     throws MediaException
/*     */   {
/* 255 */     if (!this.isStopTimeSet) {
/* 256 */       playerSetStopTime(1.7976931348623157E+308D);
/* 257 */       this.isStopTimeSet = true;
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void playerDispose() {
/* 262 */     this.audioEqualizer = null;
/* 263 */     this.audioSpectrum = null;
/* 264 */     gstDispose(this.gstMedia.getNativeMediaRef());
/* 265 */     this.gstMedia = null;
/*     */   }
/*     */ 
/*     */   private native int gstInitPlayer(long paramLong);
/*     */ 
/*     */   private native int gstGetAudioSyncDelay(long paramLong, long[] paramArrayOfLong);
/*     */ 
/*     */   private native int gstSetAudioSyncDelay(long paramLong1, long paramLong2);
/*     */ 
/*     */   private native int gstPlay(long paramLong);
/*     */ 
/*     */   private native int gstPause(long paramLong);
/*     */ 
/*     */   private native int gstStop(long paramLong);
/*     */ 
/*     */   private native int gstGetRate(long paramLong, float[] paramArrayOfFloat);
/*     */ 
/*     */   private native int gstSetRate(long paramLong, float paramFloat);
/*     */ 
/*     */   private native int gstGetPresentationTime(long paramLong, double[] paramArrayOfDouble);
/*     */ 
/*     */   private native int gstGetVolume(long paramLong, float[] paramArrayOfFloat);
/*     */ 
/*     */   private native int gstSetVolume(long paramLong, float paramFloat);
/*     */ 
/*     */   private native int gstGetBalance(long paramLong, float[] paramArrayOfFloat);
/*     */ 
/*     */   private native int gstSetBalance(long paramLong, float paramFloat);
/*     */ 
/*     */   private native int gstGetStartTime(long paramLong, double[] paramArrayOfDouble);
/*     */ 
/*     */   private native int gstSetStartTime(long paramLong, double paramDouble);
/*     */ 
/*     */   private native int gstGetStopTime(long paramLong, double[] paramArrayOfDouble);
/*     */ 
/*     */   private native int gstSetStopTime(long paramLong, double paramDouble);
/*     */ 
/*     */   private native int gstGetDuration(long paramLong, double[] paramArrayOfDouble);
/*     */ 
/*     */   private native int gstSeek(long paramLong, double paramDouble);
/*     */ 
/*     */   private native void gstDispose(long paramLong);
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.media.jfxmediaimpl.platform.gstreamer.GSTMediaPlayer
 * JD-Core Version:    0.6.2
 */