/*     */ package com.sun.media.jfxmediaimpl;
/*     */ 
/*     */ import com.sun.media.jfxmedia.AudioClip;
/*     */ import com.sun.media.jfxmedia.locator.Locator;
/*     */ import com.sun.media.jfxmedia.logging.Logger;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ 
/*     */ public final class NativeMediaAudioClip extends AudioClip
/*     */ {
/*     */   private URI sourceURI;
/*     */   private Locator mediaLocator;
/*     */   private AtomicInteger playCount;
/*     */ 
/*     */   private NativeMediaAudioClip(URI source)
/*     */     throws URISyntaxException, FileNotFoundException, IOException
/*     */   {
/*  25 */     this.sourceURI = source;
/*  26 */     this.playCount = new AtomicInteger(0);
/*     */ 
/*  28 */     if (Logger.canLog(1)) {
/*  29 */       Logger.logMsg(1, "Creating AudioClip for URI " + source);
/*     */     }
/*     */ 
/*  32 */     this.mediaLocator = new Locator(this.sourceURI);
/*  33 */     this.mediaLocator.init();
/*  34 */     this.mediaLocator.cacheMedia();
/*     */   }
/*     */ 
/*     */   Locator getLocator() {
/*  38 */     return this.mediaLocator;
/*     */   }
/*     */ 
/*     */   public static AudioClip load(URI source) throws URISyntaxException, FileNotFoundException, IOException {
/*  42 */     return new NativeMediaAudioClip(source);
/*     */   }
/*     */ 
/*     */   public static AudioClip create(byte[] data, int dataOffset, int sampleCount, int sampleFormat, int channels, int sampleRate)
/*     */   {
/*  47 */     throw new UnsupportedOperationException("NativeMediaAudioClip does not support creating clips from raw sample data");
/*     */   }
/*     */ 
/*     */   public AudioClip createSegment(double startTime, double stopTime) throws IllegalArgumentException
/*     */   {
/*  52 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public AudioClip createSegment(int startSample, int endSample) throws IllegalArgumentException
/*     */   {
/*  57 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public AudioClip resample(int startSample, int endSample, int newSampleRate) throws IllegalArgumentException, IOException
/*     */   {
/*  62 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public AudioClip append(AudioClip clip) throws IOException
/*     */   {
/*  67 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public AudioClip flatten()
/*     */   {
/*  72 */     throw new UnsupportedOperationException("Not supported yet.");
/*     */   }
/*     */ 
/*     */   public boolean isPlaying()
/*     */   {
/*  77 */     return this.playCount.get() > 0;
/*     */   }
/*     */ 
/*     */   public void play()
/*     */   {
/*  82 */     play(this.clipVolume, this.clipBalance, this.clipRate, this.clipPan, this.loopCount, this.clipPriority);
/*     */   }
/*     */ 
/*     */   public void play(double volume)
/*     */   {
/*  87 */     play(volume, this.clipBalance, this.clipRate, this.clipPan, this.loopCount, this.clipPriority);
/*     */   }
/*     */ 
/*     */   public void play(double volume, double balance, double rate, double pan, int loopCount, int priority)
/*     */   {
/*  93 */     this.playCount.getAndIncrement();
/*  94 */     NativeMediaAudioClipPlayer.playClip(this, volume, balance, rate, pan, loopCount, priority);
/*     */   }
/*     */ 
/*     */   public void stop()
/*     */   {
/*  99 */     NativeMediaAudioClipPlayer.stopPlayers(this.mediaLocator);
/*     */   }
/*     */ 
/*     */   public static void stopAllClips() {
/* 103 */     NativeMediaAudioClipPlayer.stopPlayers(null);
/*     */   }
/*     */ 
/*     */   void playFinished()
/*     */   {
/* 109 */     this.playCount.decrementAndGet();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.media.jfxmediaimpl.NativeMediaAudioClip
 * JD-Core Version:    0.6.2
 */