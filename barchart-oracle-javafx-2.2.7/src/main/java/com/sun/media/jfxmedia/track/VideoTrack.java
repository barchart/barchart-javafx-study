/*     */ package com.sun.media.jfxmedia.track;
/*     */ 
/*     */ import com.sun.media.jfxmedia.locator.Locator;
/*     */ 
/*     */ public class VideoTrack extends Track
/*     */ {
/*     */   private VideoResolution frameSize;
/*     */   private float encodedFrameRate;
/*     */   private boolean hasAlphaChannel;
/*     */ 
/*     */   public VideoTrack(Locator locator, String name, Track.Encoding encoding, VideoResolution frameSize, float encodedFrameRate, boolean hasAlphaChannel)
/*     */   {
/*  96 */     super(locator, name, encoding);
/*     */ 
/*  98 */     if (frameSize == null) {
/*  99 */       throw new IllegalArgumentException("frameSize == null!");
/*     */     }
/* 101 */     if (frameSize.width <= 0) {
/* 102 */       throw new IllegalArgumentException("frameSize.width <= 0!");
/*     */     }
/* 104 */     if (frameSize.height <= 0) {
/* 105 */       throw new IllegalArgumentException("frameSize.height <= 0!");
/*     */     }
/* 107 */     if (encodedFrameRate <= 0.0F) {
/* 108 */       throw new IllegalArgumentException("encodedFrameRate <= 0.0!");
/*     */     }
/*     */ 
/* 111 */     this.frameSize = frameSize;
/* 112 */     this.encodedFrameRate = encodedFrameRate;
/* 113 */     this.hasAlphaChannel = hasAlphaChannel;
/*     */   }
/*     */ 
/*     */   public boolean hasAlphaChannel()
/*     */   {
/* 122 */     return this.hasAlphaChannel;
/*     */   }
/*     */ 
/*     */   public float getEncodedFrameRate()
/*     */   {
/* 132 */     return this.encodedFrameRate;
/*     */   }
/*     */ 
/*     */   public VideoResolution getFrameSize()
/*     */   {
/* 141 */     return this.frameSize;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 146 */     return "VideoTrack {\n    locator: " + getLocator() + "\n" + "    name: " + getName() + "\n" + "    encoding: " + getEncodingType() + "\n" + "    frameSize: " + this.frameSize + "\n" + "    encodedFrameRate: " + this.encodedFrameRate + "\n" + "    hasAlphaChannel: " + this.hasAlphaChannel + "\n" + "}";
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.media.jfxmedia.track.VideoTrack
 * JD-Core Version:    0.6.2
 */