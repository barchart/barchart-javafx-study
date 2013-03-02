/*     */ package com.sun.media.jfxmediaimpl;
/*     */ 
/*     */ import com.sun.media.jfxmedia.AudioClip;
/*     */ import com.sun.media.jfxmedia.MediaException;
/*     */ import com.sun.media.jfxmedia.locator.Locator;
/*     */ import java.io.IOException;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ 
/*     */ public class NativeAudioClip extends AudioClip
/*     */ {
/*     */   private final Locator mediaSource;
/*  20 */   private long nativeHandle = 0L;
/*     */ 
/*  22 */   private static NativeAudioClipDisposer clipDisposer = new NativeAudioClipDisposer(null);
/*     */ 
/*     */   private static native boolean nacInit();
/*     */ 
/*     */   private static native long nacLoad(Locator paramLocator);
/*     */ 
/*     */   private static native long nacCreate(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);
/*     */ 
/*     */   private static native void nacUnload(long paramLong);
/*     */ 
/*     */   private static native void nacStopAll();
/*     */ 
/*  31 */   public static synchronized boolean init() { return nacInit(); }
/*     */ 
/*     */   public static AudioClip load(URI source)
/*     */   {
/*  35 */     NativeAudioClip newClip = null;
/*     */     try {
/*  37 */       Locator locator = new Locator(source);
/*  38 */       locator.init();
/*  39 */       newClip = new NativeAudioClip(locator);
/*     */     } catch (URISyntaxException ex) {
/*  41 */       throw new MediaException("Non-compliant URI", ex);
/*     */     } catch (IOException ex) {
/*  43 */       throw new MediaException("Cannot connect to media", ex);
/*     */     }
/*  45 */     if ((null != newClip) && (0L != newClip.getNativeHandle())) {
/*  46 */       MediaDisposer.addResourceDisposer(newClip, new Long(newClip.getNativeHandle()), clipDisposer);
/*     */     } else {
/*  48 */       newClip = null;
/*  49 */       throw new MediaException("Cannot create audio clip");
/*     */     }
/*  51 */     return newClip;
/*     */   }
/*     */ 
/*     */   public static AudioClip create(byte[] data, int dataOffset, int sampleCount, int sampleFormat, int channels, int sampleRate) {
/*  55 */     NativeAudioClip newClip = new NativeAudioClip(data, dataOffset, sampleCount, sampleFormat, channels, sampleRate);
/*  56 */     if ((null != newClip) && (0L != newClip.getNativeHandle())) {
/*  57 */       MediaDisposer.addResourceDisposer(newClip, new Long(newClip.getNativeHandle()), clipDisposer);
/*     */     } else {
/*  59 */       newClip = null;
/*  60 */       throw new MediaException("Cannot create audio clip");
/*     */     }
/*  62 */     return newClip; } 
/*     */   private native NativeAudioClip nacCreateSegment(long paramLong, double paramDouble1, double paramDouble2);
/*     */ 
/*     */   private native NativeAudioClip nacCreateSegment(long paramLong, int paramInt1, int paramInt2);
/*     */ 
/*     */   private native NativeAudioClip nacResample(long paramLong, int paramInt1, int paramInt2, int paramInt3);
/*     */ 
/*     */   private native NativeAudioClip nacAppend(long paramLong1, long paramLong2);
/*     */ 
/*     */   private native boolean nacIsPlaying(long paramLong);
/*     */ 
/*     */   private native void nacPlay(long paramLong, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, int paramInt1, int paramInt2);
/*     */ 
/*     */   private native void nacStop(long paramLong);
/*     */ 
/*  76 */   private NativeAudioClip(Locator source) { this.mediaSource = source;
/*  77 */     this.nativeHandle = nacLoad(this.mediaSource); }
/*     */ 
/*     */   private NativeAudioClip(byte[] data, int dataOffset, int sampleCount, int sampleFormat, int channels, int sampleRate)
/*     */   {
/*  81 */     this.mediaSource = null;
/*  82 */     this.nativeHandle = nacCreate(data, dataOffset, sampleCount, sampleFormat, channels, sampleRate);
/*     */   }
/*     */ 
/*     */   long getNativeHandle() {
/*  86 */     return this.nativeHandle;
/*     */   }
/*     */ 
/*     */   public AudioClip createSegment(double startTime, double stopTime)
/*     */   {
/*  91 */     return nacCreateSegment(this.nativeHandle, startTime, stopTime);
/*     */   }
/*     */ 
/*     */   public AudioClip createSegment(int startSample, int endSample)
/*     */   {
/*  96 */     return nacCreateSegment(this.nativeHandle, startSample, endSample);
/*     */   }
/*     */ 
/*     */   public AudioClip resample(int startSample, int endSample, int newSampleRate)
/*     */   {
/* 101 */     return nacResample(this.nativeHandle, startSample, endSample, newSampleRate);
/*     */   }
/*     */ 
/*     */   public AudioClip append(AudioClip clip)
/*     */   {
/* 107 */     if (!(clip instanceof NativeAudioClip)) {
/* 108 */       throw new IllegalArgumentException("AudioClip type mismatch, cannot append");
/*     */     }
/* 110 */     return nacAppend(this.nativeHandle, ((NativeAudioClip)clip).getNativeHandle());
/*     */   }
/*     */ 
/*     */   public AudioClip flatten()
/*     */   {
/* 116 */     return this;
/*     */   }
/*     */ 
/*     */   public boolean isPlaying()
/*     */   {
/* 121 */     return nacIsPlaying(this.nativeHandle);
/*     */   }
/*     */ 
/*     */   public void play()
/*     */   {
/* 126 */     nacPlay(this.nativeHandle, this.clipVolume, this.clipBalance, this.clipPan, this.clipRate, this.loopCount, this.clipPriority);
/*     */   }
/*     */ 
/*     */   public void play(double volume)
/*     */   {
/* 131 */     nacPlay(this.nativeHandle, volume, this.clipBalance, this.clipPan, this.clipRate, this.loopCount, this.clipPriority);
/*     */   }
/*     */ 
/*     */   public void play(double volume, double balance, double rate, double pan, int loopCount, int priority)
/*     */   {
/* 136 */     nacPlay(this.nativeHandle, volume, balance, pan, rate, loopCount, priority);
/*     */   }
/*     */ 
/*     */   public void stop()
/*     */   {
/* 141 */     nacStop(this.nativeHandle);
/*     */   }
/*     */ 
/*     */   public static void stopAllClips() {
/* 145 */     nacStopAll();
/*     */   }
/*     */ 
/*     */   private static class NativeAudioClipDisposer implements MediaDisposer.ResourceDisposer
/*     */   {
/*     */     public void disposeResource(Object resource) {
/* 151 */       long nativeHandle = ((Long)resource).longValue();
/* 152 */       if (0L != nativeHandle)
/* 153 */         NativeAudioClip.nacUnload(nativeHandle);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.media.jfxmediaimpl.NativeAudioClip
 * JD-Core Version:    0.6.2
 */