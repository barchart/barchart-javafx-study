/*    */ package com.sun.media.jfxmediaimpl.platform.gstreamer;
/*    */ 
/*    */ import com.sun.media.jfxmedia.effects.AudioEqualizer;
/*    */ import com.sun.media.jfxmedia.effects.EqualizerBand;
/*    */ 
/*    */ public final class GSTAudioEqualizer
/*    */   implements AudioEqualizer
/*    */ {
/*    */   private long refMedia;
/*    */ 
/*    */   GSTAudioEqualizer(long refMedia)
/*    */   {
/* 28 */     if (refMedia == 0L) {
/* 29 */       throw new IllegalArgumentException("Invalid native media reference");
/*    */     }
/*    */ 
/* 32 */     this.refMedia = refMedia;
/*    */   }
/*    */ 
/*    */   public boolean getEnabled()
/*    */   {
/* 40 */     return gstGetEnabled(this.refMedia);
/*    */   }
/*    */ 
/*    */   public void setEnabled(boolean enable) {
/* 44 */     gstSetEnabled(this.refMedia, enable);
/*    */   }
/*    */ 
/*    */   public EqualizerBand addBand(double centerFrequency, double bandwidth, double gain) {
/* 48 */     return (gstGetNumBands(this.refMedia) >= 64) && (gain >= -24.0D) && (gain <= 12.0D) ? null : gstAddBand(this.refMedia, centerFrequency, bandwidth, gain);
/*    */   }
/*    */ 
/*    */   public boolean removeBand(double centerFrequency)
/*    */   {
/* 54 */     return centerFrequency > 0.0D ? gstRemoveBand(this.refMedia, centerFrequency) : false;
/*    */   }
/*    */ 
/*    */   private native boolean gstGetEnabled(long paramLong);
/*    */ 
/*    */   private native void gstSetEnabled(long paramLong, boolean paramBoolean);
/*    */ 
/*    */   private native int gstGetNumBands(long paramLong);
/*    */ 
/*    */   private native EqualizerBand gstAddBand(long paramLong, double paramDouble1, double paramDouble2, double paramDouble3);
/*    */ 
/*    */   private native boolean gstRemoveBand(long paramLong, double paramDouble);
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.media.jfxmediaimpl.platform.gstreamer.GSTAudioEqualizer
 * JD-Core Version:    0.6.2
 */