/*     */ package com.sun.media.jfxmedia;
/*     */ 
/*     */ import com.sun.media.jfxmedia.locator.Locator;
/*     */ import com.sun.media.jfxmedia.track.Track;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public abstract class Media
/*     */ {
/*     */   private Locator locator;
/*  81 */   private final List<Track> tracks = new ArrayList();
/*     */ 
/*     */   protected Media(Locator locator)
/*     */   {
/*  91 */     if (locator == null) {
/*  92 */       throw new IllegalArgumentException("locator == null!");
/*     */     }
/*     */ 
/*  95 */     this.locator = locator;
/*     */   }
/*     */ 
/*     */   public abstract void addMarker(String paramString, double paramDouble);
/*     */ 
/*     */   public abstract double removeMarker(String paramString);
/*     */ 
/*     */   public abstract void removeAllMarkers();
/*     */ 
/*     */   public List<Track> getTracks()
/*     */   {
/*     */     List returnValue;
/* 134 */     synchronized (this.tracks)
/*     */     {
/*     */       List returnValue;
/* 135 */       if (this.tracks.isEmpty())
/* 136 */         returnValue = null;
/*     */       else {
/* 138 */         returnValue = Collections.unmodifiableList(new ArrayList(this.tracks));
/*     */       }
/*     */     }
/* 141 */     return returnValue;
/*     */   }
/*     */ 
/*     */   public abstract Map<String, Double> getMarkers();
/*     */ 
/*     */   public Locator getLocator()
/*     */   {
/* 158 */     return this.locator;
/*     */   }
/*     */ 
/*     */   protected void addTrack(Track track)
/*     */   {
/* 167 */     if (track == null) {
/* 168 */       throw new IllegalArgumentException("track == null!");
/*     */     }
/* 170 */     synchronized (this.tracks) {
/* 171 */       this.tracks.add(track);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 177 */     StringBuffer buffer = new StringBuffer();
/*     */ 
/* 179 */     if ((this.tracks != null) && (!this.tracks.isEmpty())) {
/* 180 */       for (Track track : this.tracks) {
/* 181 */         buffer.append(track);
/* 182 */         buffer.append("\n");
/*     */       }
/*     */     }
/*     */ 
/* 186 */     return buffer.toString();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.media.jfxmedia.Media
 * JD-Core Version:    0.6.2
 */