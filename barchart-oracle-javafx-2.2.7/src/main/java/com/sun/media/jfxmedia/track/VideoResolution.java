/*     */ package com.sun.media.jfxmedia.track;
/*     */ 
/*     */ public class VideoResolution
/*     */ {
/*     */   public int width;
/*     */   public int height;
/*     */ 
/*     */   public VideoResolution(int width, int height)
/*     */   {
/*  86 */     if (width <= 0)
/*  87 */       throw new IllegalArgumentException("width <= 0");
/*  88 */     if (height <= 0)
/*  89 */       throw new IllegalArgumentException("height <= 0");
/*  90 */     this.width = width;
/*  91 */     this.height = height;
/*     */   }
/*     */ 
/*     */   public int getWidth()
/*     */   {
/*  98 */     return this.width;
/*     */   }
/*     */ 
/*     */   public int getHeight()
/*     */   {
/* 105 */     return this.height;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 112 */     return "VideoResolution {width: " + this.width + " height: " + this.height + "}";
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.media.jfxmedia.track.VideoResolution
 * JD-Core Version:    0.6.2
 */