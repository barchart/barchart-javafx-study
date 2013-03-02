/*     */ package com.sun.media.jfxmediaimpl;
/*     */ 
/*     */ import com.sun.media.jfxmedia.Media;
/*     */ import com.sun.media.jfxmedia.locator.Locator;
/*     */ import com.sun.media.jfxmedia.track.Track;
/*     */ import com.sun.media.jfxmediaimpl.platform.Platform;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.NavigableMap;
/*     */ import java.util.TreeMap;
/*     */ import java.util.WeakHashMap;
/*     */ import java.util.concurrent.locks.Lock;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ 
/*     */ public abstract class NativeMedia extends Media
/*     */ {
/*  78 */   protected final Lock markerLock = new ReentrantLock();
/*  79 */   protected final Lock listenerLock = new ReentrantLock();
/*     */   protected Map<String, Double> markersByName;
/*     */   protected NavigableMap<Double, String> markersByTime;
/*     */   protected WeakHashMap<MarkerStateListener, Boolean> markerListeners;
/*     */ 
/*     */   protected NativeMedia(Locator locator)
/*     */   {
/*  92 */     super(locator);
/*     */   }
/*     */ 
/*     */   public abstract Platform getPlatform();
/*     */ 
/*     */   public void addTrack(Track track)
/*     */   {
/* 102 */     super.addTrack(track);
/*     */   }
/*     */ 
/*     */   public void addMarker(String markerName, double presentationTime)
/*     */   {
/* 108 */     if (markerName == null)
/* 109 */       throw new IllegalArgumentException("markerName == null!");
/* 110 */     if (presentationTime < 0.0D) {
/* 111 */       throw new IllegalArgumentException("presentationTime < 0");
/*     */     }
/* 113 */     this.markerLock.lock();
/*     */     try {
/* 115 */       if (this.markersByName == null) {
/* 116 */         this.markersByName = new HashMap();
/* 117 */         this.markersByTime = new TreeMap();
/*     */       }
/* 119 */       this.markersByName.put(markerName, Double.valueOf(presentationTime));
/* 120 */       this.markersByTime.put(Double.valueOf(presentationTime), markerName);
/* 121 */       fireMarkerStateEvent(true);
/*     */     } finally {
/* 123 */       this.markerLock.unlock();
/*     */     }
/*     */   }
/*     */ 
/*     */   public Map<String, Double> getMarkers() {
/* 128 */     Map markers = null;
/* 129 */     this.markerLock.lock();
/*     */     try {
/* 131 */       if ((this.markersByName != null) && (!this.markersByName.isEmpty()))
/* 132 */         markers = Collections.unmodifiableMap(this.markersByName);
/*     */     }
/*     */     finally {
/* 135 */       this.markerLock.unlock();
/*     */     }
/* 137 */     return markers;
/*     */   }
/*     */ 
/*     */   public double removeMarker(String markerName) {
/* 141 */     if (markerName == null) {
/* 142 */       throw new IllegalArgumentException("markerName == null!");
/*     */     }
/* 144 */     double time = -1.0D;
/* 145 */     this.markerLock.lock();
/*     */     try {
/* 147 */       if (this.markersByName.containsKey(markerName)) {
/* 148 */         time = ((Double)this.markersByName.get(markerName)).doubleValue();
/* 149 */         this.markersByName.remove(markerName);
/* 150 */         this.markersByTime.remove(Double.valueOf(time));
/* 151 */         fireMarkerStateEvent(this.markersByName.size() > 0);
/*     */       }
/*     */     } finally {
/* 154 */       this.markerLock.unlock();
/*     */     }
/* 156 */     return time;
/*     */   }
/*     */ 
/*     */   public void removeAllMarkers() {
/* 160 */     this.markerLock.lock();
/*     */     try {
/* 162 */       this.markersByName.clear();
/* 163 */       this.markersByTime.clear();
/* 164 */       fireMarkerStateEvent(false);
/*     */     } finally {
/* 166 */       this.markerLock.unlock();
/*     */     }
/*     */   }
/*     */ 
/*     */   public abstract void dispose();
/*     */ 
/*     */   Map.Entry<Double, String> getNextMarker(double time, boolean inclusive) {
/* 173 */     Map.Entry entry = null;
/* 174 */     this.markerLock.lock();
/*     */     try {
/* 176 */       if (this.markersByTime != null)
/* 177 */         if (inclusive)
/* 178 */           entry = this.markersByTime.ceilingEntry(Double.valueOf(time));
/*     */         else
/* 180 */           entry = this.markersByTime.higherEntry(Double.valueOf(time));
/*     */     }
/*     */     finally
/*     */     {
/* 184 */       this.markerLock.unlock();
/*     */     }
/* 186 */     return entry;
/*     */   }
/*     */ 
/*     */   void addMarkerStateListener(MarkerStateListener listener) {
/* 190 */     if (listener != null) {
/* 191 */       this.listenerLock.lock();
/*     */       try {
/* 193 */         if (this.markerListeners == null) {
/* 194 */           this.markerListeners = new WeakHashMap();
/*     */         }
/* 196 */         this.markerListeners.put(listener, Boolean.TRUE);
/*     */       } finally {
/* 198 */         this.listenerLock.unlock();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   void removeMarkerStateListener(MarkerStateListener listener)
/*     */   {
/* 205 */     if (listener != null) {
/* 206 */       this.listenerLock.lock();
/*     */       try {
/* 208 */         if (this.markerListeners != null)
/* 209 */           this.markerListeners.remove(listener);
/*     */       }
/*     */       finally {
/* 212 */         this.listenerLock.unlock();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   void fireMarkerStateEvent(boolean hasMarkers) {
/* 218 */     this.listenerLock.lock();
/*     */     try {
/* 220 */       if ((this.markerListeners != null) && (!this.markerListeners.isEmpty())) {
/* 221 */         for (MarkerStateListener listener : this.markerListeners.keySet())
/* 222 */           if (listener != null)
/* 223 */             listener.markerStateChanged(hasMarkers);
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/* 228 */       this.listenerLock.unlock();
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.media.jfxmediaimpl.NativeMedia
 * JD-Core Version:    0.6.2
 */