/*     */ package com.sun.media.jfxmedia.events;
/*     */ 
/*     */ public class PlayerStateEvent extends PlayerEvent
/*     */ {
/*     */   private PlayerState playerState;
/*     */   private double playerTime;
/*     */   private String message;
/*     */ 
/*     */   public PlayerStateEvent(PlayerState state, double time)
/*     */   {
/*  87 */     if (state == null)
/*  88 */       throw new IllegalArgumentException("state == null!");
/*  89 */     if (time < 0.0D) {
/*  90 */       throw new IllegalArgumentException("time < 0.0!");
/*     */     }
/*     */ 
/*  93 */     this.playerState = state;
/*  94 */     this.playerTime = time;
/*     */   }
/*     */ 
/*     */   public PlayerStateEvent(PlayerState state, double time, String message)
/*     */   {
/* 107 */     this(state, time);
/* 108 */     this.message = message;
/*     */   }
/*     */ 
/*     */   public PlayerState getState()
/*     */   {
/* 117 */     return this.playerState;
/*     */   }
/*     */ 
/*     */   public double getTime()
/*     */   {
/* 126 */     return this.playerTime;
/*     */   }
/*     */ 
/*     */   public String getMessage()
/*     */   {
/* 135 */     return this.message;
/*     */   }
/*     */ 
/*     */   public static enum PlayerState
/*     */   {
/*  71 */     UNKNOWN, READY, PLAYING, PAUSED, STOPPED, STALLED, FINISHED, HALTED;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.media.jfxmedia.events.PlayerStateEvent
 * JD-Core Version:    0.6.2
 */