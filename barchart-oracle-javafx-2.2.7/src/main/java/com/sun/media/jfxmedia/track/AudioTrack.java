/*     */ package com.sun.media.jfxmedia.track;
/*     */ 
/*     */ import com.sun.media.jfxmedia.locator.Locator;
/*     */ import java.util.Locale;
/*     */ 
/*     */ public class AudioTrack extends Track
/*     */ {
/*     */   public static final int UNKNOWN = 0;
/*     */   public static final int FRONT_LEFT = 1;
/*     */   public static final int FRONT_RIGHT = 2;
/*     */   public static final int FRONT_CENTER = 4;
/*     */   public static final int REAR_LEFT = 8;
/*     */   public static final int REAR_RIGHT = 16;
/*     */   public static final int REAR_CENTER = 32;
/*     */   private Locale language;
/*     */   private int numChannels;
/*     */   private int channelMask;
/*     */   private float encodedSampleRate;
/*     */ 
/*     */   public AudioTrack(Locator locator, String name, Track.Encoding encoding, Locale language, int numChannels, int channelMask, float encodedSampleRate)
/*     */   {
/* 132 */     super(locator, name, encoding);
/*     */ 
/* 134 */     if (language == null) {
/* 135 */       throw new IllegalArgumentException("language == null!");
/*     */     }
/*     */ 
/* 138 */     if (numChannels < 1) {
/* 139 */       throw new IllegalArgumentException("numChannels < 1!");
/*     */     }
/*     */ 
/* 142 */     if (encodedSampleRate <= 0.0F) {
/* 143 */       throw new IllegalArgumentException("encodedSampleRate <= 0.0");
/*     */     }
/*     */ 
/* 146 */     this.language = language;
/* 147 */     this.numChannels = numChannels;
/* 148 */     this.channelMask = channelMask;
/* 149 */     this.encodedSampleRate = encodedSampleRate;
/*     */   }
/*     */ 
/*     */   public int getNumChannels()
/*     */   {
/* 158 */     return this.numChannels;
/*     */   }
/*     */ 
/*     */   public int getChannelMask()
/*     */   {
/* 167 */     return this.channelMask;
/*     */   }
/*     */ 
/*     */   public float getEncodedSampleRate()
/*     */   {
/* 176 */     return this.encodedSampleRate;
/*     */   }
/*     */ 
/*     */   public Locale getLocale()
/*     */   {
/* 185 */     return this.language;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 190 */     return "AudioTrack {\n    locator: " + getLocator() + "\n" + "    name: " + getName() + "\n" + "    encoding: " + getEncodingType() + "\n" + "    language: " + this.language + "\n" + "    numChannels: " + this.numChannels + "\n" + "    channelMask: " + this.channelMask + "\n" + "    encodedSampleRate: " + this.encodedSampleRate + "\n" + "}";
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.media.jfxmedia.track.AudioTrack
 * JD-Core Version:    0.6.2
 */