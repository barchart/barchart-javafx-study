/*    */ package com.sun.media.jfxmediaimpl.platform.gstreamer;
/*    */ 
/*    */ import com.sun.media.jfxmedia.effects.EqualizerBand;
/*    */ 
/*    */ public final class GSTEqualizerBand
/*    */   implements EqualizerBand
/*    */ {
/*    */   private long bandRef;
/*    */ 
/*    */   private native double gstGetCenterFrequency(long paramLong);
/*    */ 
/*    */   private native void gstSetCenterFrequency(long paramLong, double paramDouble);
/*    */ 
/*    */   private native double gstGetBandwidth(long paramLong);
/*    */ 
/*    */   private native void gstSetBandwidth(long paramLong, double paramDouble);
/*    */ 
/*    */   private native double gstGetGain(long paramLong);
/*    */ 
/*    */   private native void gstSetGain(long paramLong, double paramDouble);
/*    */ 
/*    */   private GSTEqualizerBand(long bandRef)
/*    */   {
/* 24 */     if (bandRef != 0L)
/* 25 */       this.bandRef = bandRef;
/*    */     else
/* 27 */       throw new IllegalArgumentException("bandRef == 0");
/*    */   }
/*    */ 
/*    */   public double getCenterFrequency()
/*    */   {
/* 32 */     return gstGetCenterFrequency(this.bandRef);
/*    */   }
/*    */ 
/*    */   public void setCenterFrequency(double centerFrequency) {
/* 36 */     gstSetCenterFrequency(this.bandRef, centerFrequency);
/*    */   }
/*    */ 
/*    */   public double getBandwidth() {
/* 40 */     return gstGetBandwidth(this.bandRef);
/*    */   }
/*    */ 
/*    */   public void setBandwidth(double bandwidth) {
/* 44 */     gstSetBandwidth(this.bandRef, bandwidth);
/*    */   }
/*    */ 
/*    */   public double getGain() {
/* 48 */     return gstGetGain(this.bandRef);
/*    */   }
/*    */ 
/*    */   public void setGain(double gain) {
/* 52 */     if ((gain >= -24.0D) && (gain <= 12.0D))
/* 53 */       gstSetGain(this.bandRef, gain);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.media.jfxmediaimpl.platform.gstreamer.GSTEqualizerBand
 * JD-Core Version:    0.6.2
 */