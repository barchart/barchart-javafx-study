/*     */ package com.sun.media.jfxmedia.events;
/*     */ 
/*     */ public class BufferProgressEvent extends PlayerEvent
/*     */ {
/*     */   private double duration;
/*     */   private long start;
/*     */   private long stop;
/*     */   private long position;
/*     */ 
/*     */   public BufferProgressEvent(double duration, long start, long stop, long position)
/*     */   {
/*  82 */     this.duration = duration;
/*  83 */     this.start = start;
/*  84 */     this.stop = stop;
/*  85 */     this.position = position;
/*     */   }
/*     */ 
/*     */   public double getDuration()
/*     */   {
/*  90 */     return this.duration;
/*     */   }
/*     */ 
/*     */   public long getBufferStart()
/*     */   {
/*  99 */     return this.start;
/*     */   }
/*     */ 
/*     */   public long getBufferStop()
/*     */   {
/* 109 */     return this.stop;
/*     */   }
/*     */ 
/*     */   public long getBufferPosition()
/*     */   {
/* 119 */     return this.position;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.media.jfxmedia.events.BufferProgressEvent
 * JD-Core Version:    0.6.2
 */