/*     */ package com.sun.media.jfxmedia.events;
/*     */ 
/*     */ public class MarkerEvent extends PlayerEvent
/*     */ {
/*     */   private String markerName;
/*     */   private double presentationTime;
/*     */ 
/*     */   public MarkerEvent(String name, double time)
/*     */   {
/*  83 */     if (name == null)
/*  84 */       throw new IllegalArgumentException("name == null!");
/*  85 */     if (time < 0.0D) {
/*  86 */       throw new IllegalArgumentException("time < 0.0!");
/*     */     }
/*     */ 
/*  89 */     this.markerName = name;
/*  90 */     this.presentationTime = time;
/*     */   }
/*     */ 
/*     */   public String getMarkerName()
/*     */   {
/* 100 */     return this.markerName;
/*     */   }
/*     */ 
/*     */   public double getPresentationTime()
/*     */   {
/* 110 */     return this.presentationTime;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.media.jfxmedia.events.MarkerEvent
 * JD-Core Version:    0.6.2
 */