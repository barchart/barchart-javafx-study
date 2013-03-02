/*    */ package com.sun.media.jfxmediaimpl;
/*    */ 
/*    */ import com.sun.media.jfxmedia.AudioClip;
/*    */ import com.sun.media.jfxmedia.logging.Logger;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.IOException;
/*    */ import java.net.URI;
/*    */ import java.net.URISyntaxException;
/*    */ 
/*    */ public class AudioClipProvider
/*    */ {
/*    */   private static AudioClipProvider primaDonna;
/*    */   private boolean useNative;
/*    */ 
/*    */   public static synchronized AudioClipProvider getProvider()
/*    */   {
/* 24 */     if (null == primaDonna) {
/* 25 */       primaDonna = new AudioClipProvider();
/*    */     }
/* 27 */     return primaDonna;
/*    */   }
/*    */ 
/*    */   private AudioClipProvider()
/*    */   {
/* 33 */     this.useNative = false;
/*    */     try {
/* 35 */       this.useNative = NativeAudioClip.init();
/*    */     } catch (UnsatisfiedLinkError ule) {
/* 37 */       Logger.logMsg(1, "JavaFX AudioClip native methods not linked, using NativeMedia implementation");
/*    */     } catch (Exception t) {
/* 39 */       Logger.logMsg(4, "Exception while loading native AudioClip library: " + t);
/*    */     }
/*    */   }
/*    */ 
/*    */   public AudioClip load(URI source) throws URISyntaxException, FileNotFoundException, IOException {
/* 44 */     if (this.useNative) {
/* 45 */       return NativeAudioClip.load(source);
/*    */     }
/* 47 */     return NativeMediaAudioClip.load(source);
/*    */   }
/*    */ 
/*    */   public AudioClip create(byte[] data, int dataOffset, int sampleCount, int sampleFormat, int channels, int sampleRate)
/*    */     throws IllegalArgumentException
/*    */   {
/* 53 */     if (this.useNative) {
/* 54 */       return NativeAudioClip.create(data, dataOffset, sampleCount, sampleFormat, channels, sampleRate);
/*    */     }
/* 56 */     return NativeMediaAudioClip.create(data, dataOffset, sampleCount, sampleFormat, channels, sampleRate);
/*    */   }
/*    */ 
/*    */   public void stopAllClips() {
/* 60 */     if (this.useNative) {
/* 61 */       NativeAudioClip.stopAllClips();
/*    */     }
/* 63 */     NativeMediaAudioClip.stopAllClips();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.media.jfxmediaimpl.AudioClipProvider
 * JD-Core Version:    0.6.2
 */