/*     */ package com.sun.media.jfxmedia;
/*     */ 
/*     */ import com.sun.media.jfxmediaimpl.AudioClipProvider;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ 
/*     */ public abstract class AudioClip
/*     */ {
/* 136 */   protected int clipPriority = 0;
/* 137 */   protected int loopCount = 0;
/* 138 */   protected double clipVolume = 1.0D;
/* 139 */   protected double clipBalance = 0.0D;
/* 140 */   protected double clipRate = 1.0D;
/* 141 */   protected double clipPan = 0.0D;
/*     */   public static final int SAMPLE_FORMAT_S8 = 0;
/*     */   public static final int SAMPLE_FORMAT_U8 = 1;
/*     */   public static final int SAMPLE_FORMAT_S16BE = 2;
/*     */   public static final int SAMPLE_FORMAT_U16BE = 3;
/*     */   public static final int SAMPLE_FORMAT_S16LE = 4;
/*     */   public static final int SAMPLE_FORMAT_U16LE = 5;
/*     */   public static final int SAMPLE_FORMAT_S24BE = 6;
/*     */   public static final int SAMPLE_FORMAT_U24BE = 7;
/*     */   public static final int SAMPLE_FORMAT_S24LE = 8;
/*     */   public static final int SAMPLE_FORMAT_U24LE = 9;
/*     */ 
/*     */   public static AudioClip load(URI source)
/*     */     throws URISyntaxException, FileNotFoundException, IOException
/*     */   {
/* 172 */     return AudioClipProvider.getProvider().load(source);
/*     */   }
/*     */ 
/*     */   public static AudioClip create(byte[] data, int dataOffset, int sampleCount, int sampleFormat, int channels, int sampleRate)
/*     */     throws IllegalArgumentException
/*     */   {
/* 197 */     return AudioClipProvider.getProvider().create(data, dataOffset, sampleCount, sampleFormat, channels, sampleRate);
/*     */   }
/*     */ 
/*     */   public static void stopAllClips()
/*     */   {
/* 204 */     AudioClipProvider.getProvider().stopAllClips();
/*     */   }
/*     */ 
/*     */   public abstract AudioClip createSegment(double paramDouble1, double paramDouble2)
/*     */     throws IllegalArgumentException;
/*     */ 
/*     */   public abstract AudioClip createSegment(int paramInt1, int paramInt2)
/*     */     throws IllegalArgumentException;
/*     */ 
/*     */   public abstract AudioClip resample(int paramInt1, int paramInt2, int paramInt3)
/*     */     throws IllegalArgumentException, IOException;
/*     */ 
/*     */   public abstract AudioClip append(AudioClip paramAudioClip)
/*     */     throws IOException;
/*     */ 
/*     */   public abstract AudioClip flatten();
/*     */ 
/*     */   public int priority()
/*     */   {
/* 296 */     return this.clipPriority;
/*     */   }
/*     */   public void setPriority(int prio) {
/* 299 */     this.clipPriority = prio;
/*     */   }
/*     */ 
/*     */   public int loopCount()
/*     */   {
/* 310 */     return this.loopCount;
/*     */   }
/*     */ 
/*     */   public void setLoopCount(int loopCount)
/*     */   {
/* 325 */     this.loopCount = loopCount;
/*     */   }
/*     */ 
/*     */   public double volume()
/*     */   {
/* 334 */     return this.clipVolume;
/*     */   }
/*     */ 
/*     */   public void setVolume(double vol)
/*     */   {
/* 344 */     this.clipVolume = vol;
/*     */   }
/*     */ 
/*     */   public double balance()
/*     */   {
/* 353 */     return this.clipBalance;
/*     */   }
/*     */ 
/*     */   public void setBalance(double bal)
/*     */   {
/* 363 */     this.clipBalance = bal;
/*     */   }
/*     */ 
/*     */   public double playbackRate()
/*     */   {
/* 375 */     return this.clipRate;
/*     */   }
/*     */ 
/*     */   public void setPlaybackRate(double rate)
/*     */   {
/* 392 */     this.clipRate = rate;
/*     */   }
/*     */ 
/*     */   public double pan()
/*     */   {
/* 401 */     return this.clipPan;
/*     */   }
/*     */ 
/*     */   public void setPan(double pan)
/*     */   {
/* 415 */     this.clipPan = pan;
/*     */   }
/*     */ 
/*     */   public abstract boolean isPlaying();
/*     */ 
/*     */   public abstract void play();
/*     */ 
/*     */   public abstract void play(double paramDouble);
/*     */ 
/*     */   public abstract void play(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, int paramInt1, int paramInt2);
/*     */ 
/*     */   public abstract void stop();
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.media.jfxmedia.AudioClip
 * JD-Core Version:    0.6.2
 */