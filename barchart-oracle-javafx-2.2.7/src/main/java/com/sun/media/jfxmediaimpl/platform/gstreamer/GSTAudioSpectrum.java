/*     */ package com.sun.media.jfxmediaimpl.platform.gstreamer;
/*     */ 
/*     */ import com.sun.media.jfxmedia.effects.AudioSpectrum;
/*     */ 
/*     */ public final class GSTAudioSpectrum
/*     */   implements AudioSpectrum
/*     */ {
/*  14 */   private static float[] EMPTY_FLOAT_ARRAY = new float[0];
/*     */   public static final int DEFAULT_THRESHOLD = -60;
/*     */   public static final int DEFAULT_BANDS = 128;
/*     */   public static final double DEFAULT_INTERVAL = 0.1D;
/*     */   private long refMedia;
/*  24 */   private float[] magnitudes = EMPTY_FLOAT_ARRAY;
/*  25 */   private float[] phases = EMPTY_FLOAT_ARRAY;
/*     */ 
/*     */   GSTAudioSpectrum(long refMedia)
/*     */   {
/*  36 */     if (refMedia == 0L) {
/*  37 */       throw new IllegalArgumentException("Invalid native media reference");
/*     */     }
/*     */ 
/*  40 */     this.refMedia = refMedia;
/*  41 */     setBandCount(128);
/*     */   }
/*     */ 
/*     */   public boolean getEnabled()
/*     */   {
/*  48 */     return gstGetEnabled(this.refMedia);
/*     */   }
/*     */ 
/*     */   public void setEnabled(boolean enabled) {
/*  52 */     gstSetEnabled(this.refMedia, enabled);
/*     */   }
/*     */ 
/*     */   public int getBandCount()
/*     */   {
/*  57 */     return this.phases.length;
/*     */   }
/*     */ 
/*     */   public void setBandCount(int bands) {
/*  61 */     if (bands > 1) {
/*  62 */       this.magnitudes = new float[bands];
/*  63 */       for (int i = 0; i < this.magnitudes.length; i++) {
/*  64 */         this.magnitudes[i] = (1.0F / -1.0F);
/*     */       }
/*     */ 
/*  67 */       this.phases = new float[bands];
/*  68 */       gstSetBands(this.refMedia, bands, this.magnitudes, this.phases);
/*     */     } else {
/*  70 */       this.magnitudes = EMPTY_FLOAT_ARRAY;
/*  71 */       this.phases = EMPTY_FLOAT_ARRAY;
/*     */ 
/*  73 */       throw new IllegalArgumentException("Number of bands must at least be 2");
/*     */     }
/*     */   }
/*     */ 
/*     */   public double getInterval() {
/*  78 */     return gstGetInterval(this.refMedia);
/*     */   }
/*     */ 
/*     */   public void setInterval(double interval) {
/*  82 */     if (interval * 1000000000.0D >= 1.0D)
/*  83 */       gstSetInterval(this.refMedia, interval);
/*     */     else
/*  85 */       throw new IllegalArgumentException("Interval can't be less that 1 nanosecond");
/*     */   }
/*     */ 
/*     */   public int getSensitivityThreshold()
/*     */   {
/*  90 */     return gstGetThreshold(this.refMedia);
/*     */   }
/*     */ 
/*     */   public void setSensitivityThreshold(int threshold) {
/*  94 */     if (threshold <= 0)
/*  95 */       gstSetThreshold(this.refMedia, threshold);
/*     */     else
/*  97 */       throw new IllegalArgumentException(String.format("Sensitivity threshold must be less than 0: %d", new Object[] { Integer.valueOf(threshold) }));
/*     */   }
/*     */ 
/*     */   public float[] getMagnitudes()
/*     */   {
/* 102 */     return this.magnitudes;
/*     */   }
/*     */ 
/*     */   public float[] getPhases() {
/* 106 */     return this.phases;
/*     */   }
/*     */ 
/*     */   private native boolean gstGetEnabled(long paramLong);
/*     */ 
/*     */   private native void gstSetEnabled(long paramLong, boolean paramBoolean);
/*     */ 
/*     */   private native void gstSetBands(long paramLong, int paramInt, float[] paramArrayOfFloat1, float[] paramArrayOfFloat2);
/*     */ 
/*     */   private native double gstGetInterval(long paramLong);
/*     */ 
/*     */   private native void gstSetInterval(long paramLong, double paramDouble);
/*     */ 
/*     */   private native int gstGetThreshold(long paramLong);
/*     */ 
/*     */   private native void gstSetThreshold(long paramLong, int paramInt);
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.media.jfxmediaimpl.platform.gstreamer.GSTAudioSpectrum
 * JD-Core Version:    0.6.2
 */