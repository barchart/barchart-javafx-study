/*     */ package com.sun.media.jfxmediaimpl.platform.osx;
/*     */ 
/*     */ import com.sun.media.jfxmedia.MediaException;
/*     */ import com.sun.media.jfxmedia.effects.AudioEqualizer;
/*     */ import com.sun.media.jfxmedia.effects.AudioSpectrum;
/*     */ import com.sun.media.jfxmedia.effects.EqualizerBand;
/*     */ import com.sun.media.jfxmedia.locator.Locator;
/*     */ import com.sun.media.jfxmediaimpl.NativeMedia;
/*     */ import com.sun.media.jfxmediaimpl.NativeMediaPlayer;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ public final class OSXMediaPlayer extends NativeMediaPlayer
/*     */ {
/*     */   private NullAudioEQ audioEq;
/*     */   private NullAudioSpectrum audioSpectrum;
/*     */   private final Locator mediaLocator;
/*     */ 
/*     */   OSXMediaPlayer(NativeMedia sourceMedia)
/*     */   {
/*  26 */     super(sourceMedia);
/*  27 */     this.mediaLocator = sourceMedia.getLocator();
/*  28 */     this.audioEq = new NullAudioEQ(null);
/*  29 */     this.audioSpectrum = new NullAudioSpectrum(null);
/*     */   }
/*     */ 
/*     */   OSXMediaPlayer(Locator source) {
/*  33 */     this(new OSXMedia(source));
/*     */   }
/*     */ 
/*     */   public AudioEqualizer getEqualizer()
/*     */   {
/*  38 */     return this.audioEq;
/*     */   }
/*     */ 
/*     */   public AudioSpectrum getAudioSpectrum()
/*     */   {
/*  43 */     return this.audioSpectrum;
/*     */   }
/*     */ 
/*     */   void initializePlayer() {
/*  47 */     createPlayer(this.mediaLocator.getStringLocation());
/*     */   }
/*     */ 
/*     */   private native void createPlayer(String paramString)
/*     */     throws MediaException;
/*     */ 
/*     */   public native long playerGetAudioSyncDelay()
/*     */     throws MediaException;
/*     */ 
/*     */   public native void playerSetAudioSyncDelay(long paramLong)
/*     */     throws MediaException;
/*     */ 
/*     */   public native void playerPlay()
/*     */     throws MediaException;
/*     */ 
/*     */   public native void playerStop()
/*     */     throws MediaException;
/*     */ 
/*     */   public native void playerPause()
/*     */     throws MediaException;
/*     */ 
/*     */   public native float playerGetRate()
/*     */     throws MediaException;
/*     */ 
/*     */   public native void playerSetRate(float paramFloat)
/*     */     throws MediaException;
/*     */ 
/*     */   public native double playerGetPresentationTime()
/*     */     throws MediaException;
/*     */ 
/*     */   public native boolean playerGetMute()
/*     */     throws MediaException;
/*     */ 
/*     */   public native void playerSetMute(boolean paramBoolean)
/*     */     throws MediaException;
/*     */ 
/*     */   public native float playerGetVolume()
/*     */     throws MediaException;
/*     */ 
/*     */   public native void playerSetVolume(float paramFloat)
/*     */     throws MediaException;
/*     */ 
/*     */   public native float playerGetBalance()
/*     */     throws MediaException;
/*     */ 
/*     */   public native void playerSetBalance(float paramFloat)
/*     */     throws MediaException;
/*     */ 
/*     */   public native double playerGetDuration()
/*     */     throws MediaException;
/*     */ 
/*     */   public native double playerGetStartTime()
/*     */     throws MediaException;
/*     */ 
/*     */   public native void playerSetStartTime(double paramDouble)
/*     */     throws MediaException;
/*     */ 
/*     */   public native double playerGetStopTime()
/*     */     throws MediaException;
/*     */ 
/*     */   public native void playerSetStopTime(double paramDouble)
/*     */     throws MediaException;
/*     */ 
/*     */   public native void playerSeek(double paramDouble)
/*     */     throws MediaException;
/*     */ 
/*     */   public native void playerDispose();
/*     */ 
/*     */   public void playerInit()
/*     */     throws MediaException
/*     */   {
/*     */   }
/*     */ 
/*     */   private static final class NullEQBand
/*     */     implements EqualizerBand
/*     */   {
/*     */     private double center;
/*     */     private double bandwidth;
/*     */     private double gain;
/*     */ 
/*     */     NullEQBand(double center, double bandwidth, double gain)
/*     */     {
/* 167 */       this.center = center;
/* 168 */       this.bandwidth = bandwidth;
/* 169 */       this.gain = gain;
/*     */     }
/*     */ 
/*     */     public double getCenterFrequency() {
/* 173 */       return this.center;
/*     */     }
/*     */ 
/*     */     public void setCenterFrequency(double centerFrequency) {
/* 177 */       this.center = centerFrequency;
/*     */     }
/*     */ 
/*     */     public double getBandwidth() {
/* 181 */       return this.bandwidth;
/*     */     }
/*     */ 
/*     */     public void setBandwidth(double bandwidth) {
/* 185 */       this.bandwidth = bandwidth;
/*     */     }
/*     */ 
/*     */     public double getGain() {
/* 189 */       return this.gain;
/*     */     }
/*     */ 
/*     */     public void setGain(double gain) {
/* 193 */       this.gain = gain;
/*     */     }
/*     */   }
/*     */ 
/*     */   private final class NullAudioSpectrum
/*     */     implements AudioSpectrum
/*     */   {
/* 113 */     private boolean enabled = false;
/* 114 */     private int bandCount = 128;
/* 115 */     private double interval = 0.1D;
/* 116 */     private int threshold = 60;
/*     */     private float[] fakeData;
/*     */ 
/*     */     private NullAudioSpectrum()
/*     */     {
/*     */     }
/*     */ 
/*     */     public boolean getEnabled()
/*     */     {
/* 120 */       return this.enabled;
/*     */     }
/*     */ 
/*     */     public void setEnabled(boolean enabled) {
/* 124 */       this.enabled = enabled;
/*     */     }
/*     */ 
/*     */     public int getBandCount() {
/* 128 */       return this.bandCount;
/*     */     }
/*     */ 
/*     */     public void setBandCount(int bands) {
/* 132 */       this.bandCount = bands;
/* 133 */       this.fakeData = new float[this.bandCount];
/*     */     }
/*     */ 
/*     */     public double getInterval() {
/* 137 */       return this.interval;
/*     */     }
/*     */ 
/*     */     public void setInterval(double interval) {
/* 141 */       this.interval = interval;
/*     */     }
/*     */ 
/*     */     public int getSensitivityThreshold() {
/* 145 */       return this.threshold;
/*     */     }
/*     */ 
/*     */     public void setSensitivityThreshold(int threshold) {
/* 149 */       this.threshold = threshold;
/*     */     }
/*     */ 
/*     */     public float[] getMagnitudes() {
/* 153 */       return this.fakeData;
/*     */     }
/*     */ 
/*     */     public float[] getPhases() {
/* 157 */       return this.fakeData;
/*     */     }
/*     */   }
/*     */ 
/*     */   private final class NullAudioEQ
/*     */     implements AudioEqualizer
/*     */   {
/*  79 */     private boolean enabled = false;
/*  80 */     private Map<Double, EqualizerBand> bands = new HashMap();
/*     */ 
/*     */     private NullAudioEQ() {
/*     */     }
/*  84 */     public boolean getEnabled() { return this.enabled; }
/*     */ 
/*     */     public void setEnabled(boolean bEnable)
/*     */     {
/*  88 */       this.enabled = bEnable;
/*     */     }
/*     */ 
/*     */     public EqualizerBand addBand(double centerFrequency, double bandwidth, double gain) {
/*  92 */       Double key = new Double(centerFrequency);
/*  93 */       if (this.bands.containsKey(key)) {
/*  94 */         removeBand(centerFrequency);
/*     */       }
/*     */ 
/*  97 */       EqualizerBand newBand = new OSXMediaPlayer.NullEQBand(centerFrequency, bandwidth, gain);
/*  98 */       this.bands.put(key, newBand);
/*  99 */       return newBand;
/*     */     }
/*     */ 
/*     */     public boolean removeBand(double centerFrequency) {
/* 103 */       Double key = new Double(centerFrequency);
/* 104 */       if (this.bands.containsKey(key)) {
/* 105 */         this.bands.remove(key);
/* 106 */         return true;
/*     */       }
/* 108 */       return false;
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.media.jfxmediaimpl.platform.osx.OSXMediaPlayer
 * JD-Core Version:    0.6.2
 */