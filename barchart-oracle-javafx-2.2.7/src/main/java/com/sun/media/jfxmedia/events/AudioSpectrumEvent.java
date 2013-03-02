/*    */ package com.sun.media.jfxmedia.events;
/*    */ 
/*    */ import com.sun.media.jfxmedia.effects.AudioSpectrum;
/*    */ 
/*    */ public class AudioSpectrumEvent extends PlayerEvent
/*    */ {
/*    */   private AudioSpectrum source;
/*    */   private double timestamp;
/*    */   private double duration;
/*    */ 
/*    */   public AudioSpectrumEvent(AudioSpectrum source, double timestamp, double duration)
/*    */   {
/* 19 */     this.source = source;
/* 20 */     this.timestamp = timestamp;
/* 21 */     this.duration = duration;
/*    */   }
/*    */ 
/*    */   public final AudioSpectrum getSource() {
/* 25 */     return this.source;
/*    */   }
/*    */ 
/*    */   public final double getTimestamp() {
/* 29 */     return this.timestamp;
/*    */   }
/*    */ 
/*    */   public final double getDuration() {
/* 33 */     return this.duration;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.media.jfxmedia.events.AudioSpectrumEvent
 * JD-Core Version:    0.6.2
 */