/*     */ package javafx.scene.media;
/*     */ 
/*     */ import javafx.util.Builder;
/*     */ 
/*     */ public final class AudioClipBuilder
/*     */   implements Builder<AudioClip>
/*     */ {
/*     */   private int __set;
/*     */   private double balance;
/*     */   private int cycleCount;
/*     */   private double pan;
/*     */   private int priority;
/*     */   private double rate;
/*     */   private String source;
/*     */   private double volume;
/*     */ 
/*     */   public static AudioClipBuilder create()
/*     */   {
/*  15 */     return new AudioClipBuilder();
/*     */   }
/*     */ 
/*     */   public void applyTo(AudioClip paramAudioClip)
/*     */   {
/*  20 */     int i = this.__set;
/*  21 */     if ((i & 0x1) != 0) paramAudioClip.setBalance(this.balance);
/*  22 */     if ((i & 0x2) != 0) paramAudioClip.setCycleCount(this.cycleCount);
/*  23 */     if ((i & 0x4) != 0) paramAudioClip.setPan(this.pan);
/*  24 */     if ((i & 0x8) != 0) paramAudioClip.setPriority(this.priority);
/*  25 */     if ((i & 0x10) != 0) paramAudioClip.setRate(this.rate);
/*  26 */     if ((i & 0x20) != 0) paramAudioClip.setVolume(this.volume);
/*     */   }
/*     */ 
/*     */   public AudioClipBuilder balance(double paramDouble)
/*     */   {
/*  34 */     this.balance = paramDouble;
/*  35 */     this.__set |= 1;
/*  36 */     return this;
/*     */   }
/*     */ 
/*     */   public AudioClipBuilder cycleCount(int paramInt)
/*     */   {
/*  44 */     this.cycleCount = paramInt;
/*  45 */     this.__set |= 2;
/*  46 */     return this;
/*     */   }
/*     */ 
/*     */   public AudioClipBuilder pan(double paramDouble)
/*     */   {
/*  54 */     this.pan = paramDouble;
/*  55 */     this.__set |= 4;
/*  56 */     return this;
/*     */   }
/*     */ 
/*     */   public AudioClipBuilder priority(int paramInt)
/*     */   {
/*  64 */     this.priority = paramInt;
/*  65 */     this.__set |= 8;
/*  66 */     return this;
/*     */   }
/*     */ 
/*     */   public AudioClipBuilder rate(double paramDouble)
/*     */   {
/*  74 */     this.rate = paramDouble;
/*  75 */     this.__set |= 16;
/*  76 */     return this;
/*     */   }
/*     */ 
/*     */   public AudioClipBuilder source(String paramString)
/*     */   {
/*  84 */     this.source = paramString;
/*  85 */     return this;
/*     */   }
/*     */ 
/*     */   public AudioClipBuilder volume(double paramDouble)
/*     */   {
/*  93 */     this.volume = paramDouble;
/*  94 */     this.__set |= 32;
/*  95 */     return this;
/*     */   }
/*     */ 
/*     */   public AudioClip build()
/*     */   {
/* 102 */     AudioClip localAudioClip = new AudioClip(this.source);
/* 103 */     applyTo(localAudioClip);
/* 104 */     return localAudioClip;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.media.AudioClipBuilder
 * JD-Core Version:    0.6.2
 */