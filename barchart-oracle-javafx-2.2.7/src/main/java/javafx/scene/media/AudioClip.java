/*     */ package javafx.scene.media;
/*     */ 
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import javafx.beans.property.DoubleProperty;
/*     */ import javafx.beans.property.DoublePropertyBase;
/*     */ import javafx.beans.property.IntegerProperty;
/*     */ import javafx.beans.property.IntegerPropertyBase;
/*     */ 
/*     */ public final class AudioClip
/*     */ {
/*     */   private String sourceURL;
/*     */   private com.sun.media.jfxmedia.AudioClip audioClip;
/*     */   private DoubleProperty volume;
/*     */   private DoubleProperty balance;
/*     */   private DoubleProperty rate;
/*     */   private DoubleProperty pan;
/*     */   private IntegerProperty priority;
/*     */   public static final int INDEFINITE = -1;
/*     */   private IntegerProperty cycleCount;
/*     */ 
/*     */   public AudioClip(String paramString)
/*     */   {
/*  58 */     URI localURI = URI.create(paramString);
/*  59 */     this.sourceURL = paramString;
/*     */     try {
/*  61 */       this.audioClip = com.sun.media.jfxmedia.AudioClip.load(localURI);
/*     */     } catch (URISyntaxException localURISyntaxException) {
/*  63 */       throw new IllegalArgumentException(localURISyntaxException);
/*     */     } catch (FileNotFoundException localFileNotFoundException) {
/*  65 */       throw new MediaException(MediaException.Type.MEDIA_UNAVAILABLE, localFileNotFoundException.getMessage());
/*     */     } catch (IOException localIOException) {
/*  67 */       throw new MediaException(MediaException.Type.MEDIA_INACCESSIBLE, localIOException.getMessage());
/*     */     } catch (com.sun.media.jfxmedia.MediaException localMediaException) {
/*  69 */       throw new MediaException(MediaException.Type.MEDIA_UNSUPPORTED, localMediaException.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getSource()
/*     */   {
/*  78 */     return this.sourceURL;
/*     */   }
/*     */ 
/*     */   public final void setVolume(double paramDouble)
/*     */   {
/*  97 */     volumeProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getVolume()
/*     */   {
/* 106 */     return null == this.volume ? 1.0D : this.volume.get();
/*     */   }
/*     */   public DoubleProperty volumeProperty() {
/* 109 */     if (this.volume == null) {
/* 110 */       this.volume = new DoublePropertyBase(1.0D)
/*     */       {
/*     */         protected void invalidated() {
/* 113 */           if (null != AudioClip.this.audioClip)
/* 114 */             AudioClip.this.audioClip.setVolume(AudioClip.this.volume.get());
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 120 */           return AudioClip.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 125 */           return "volume";
/*     */         }
/*     */       };
/*     */     }
/* 129 */     return this.volume;
/*     */   }
/*     */ 
/*     */   public void setBalance(double paramDouble)
/*     */   {
/* 148 */     balanceProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public double getBalance()
/*     */   {
/* 157 */     return null != this.balance ? this.balance.get() : 0.0D;
/*     */   }
/*     */   public DoubleProperty balanceProperty() {
/* 160 */     if (null == this.balance) {
/* 161 */       this.balance = new DoublePropertyBase(0.0D)
/*     */       {
/*     */         protected void invalidated() {
/* 164 */           if (null != AudioClip.this.audioClip)
/* 165 */             AudioClip.this.audioClip.setBalance(AudioClip.this.balance.get());
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 171 */           return AudioClip.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 176 */           return "balance";
/*     */         }
/*     */       };
/*     */     }
/* 180 */     return this.balance;
/*     */   }
/*     */ 
/*     */   public void setRate(double paramDouble)
/*     */   {
/* 198 */     rateProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public double getRate()
/*     */   {
/* 207 */     return null != this.rate ? this.rate.get() : 1.0D;
/*     */   }
/*     */   public DoubleProperty rateProperty() {
/* 210 */     if (null == this.rate) {
/* 211 */       this.rate = new DoublePropertyBase(1.0D)
/*     */       {
/*     */         protected void invalidated() {
/* 214 */           if (null != AudioClip.this.audioClip)
/* 215 */             AudioClip.this.audioClip.setPlaybackRate(AudioClip.this.rate.get());
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 221 */           return AudioClip.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 226 */           return "rate";
/*     */         }
/*     */       };
/*     */     }
/* 230 */     return this.rate;
/*     */   }
/*     */ 
/*     */   public void setPan(double paramDouble)
/*     */   {
/* 251 */     panProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public double getPan()
/*     */   {
/* 260 */     return null != this.pan ? this.pan.get() : 0.0D;
/*     */   }
/*     */   public DoubleProperty panProperty() {
/* 263 */     if (null == this.pan) {
/* 264 */       this.pan = new DoublePropertyBase(0.0D)
/*     */       {
/*     */         protected void invalidated() {
/* 267 */           if (null != AudioClip.this.audioClip)
/* 268 */             AudioClip.this.audioClip.setPan(AudioClip.this.pan.get());
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 274 */           return AudioClip.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 279 */           return "pan";
/*     */         }
/*     */       };
/*     */     }
/* 283 */     return this.pan;
/*     */   }
/*     */ 
/*     */   public void setPriority(int paramInt)
/*     */   {
/* 304 */     priorityProperty().set(paramInt);
/*     */   }
/*     */ 
/*     */   public int getPriority()
/*     */   {
/* 313 */     return null != this.priority ? this.priority.get() : 0;
/*     */   }
/*     */   public IntegerProperty priorityProperty() {
/* 316 */     if (null == this.priority) {
/* 317 */       this.priority = new IntegerPropertyBase(0)
/*     */       {
/*     */         protected void invalidated() {
/* 320 */           if (null != AudioClip.this.audioClip)
/* 321 */             AudioClip.this.audioClip.setPriority(AudioClip.this.priority.get());
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 327 */           return AudioClip.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 332 */           return "priority";
/*     */         }
/*     */       };
/*     */     }
/* 336 */     return this.priority;
/*     */   }
/*     */ 
/*     */   public void setCycleCount(int paramInt)
/*     */   {
/* 364 */     cycleCountProperty().set(paramInt);
/*     */   }
/*     */ 
/*     */   public int getCycleCount()
/*     */   {
/* 373 */     return null != this.cycleCount ? this.cycleCount.get() : 1;
/*     */   }
/*     */   public IntegerProperty cycleCountProperty() {
/* 376 */     if (null == this.cycleCount) {
/* 377 */       this.cycleCount = new IntegerPropertyBase(1)
/*     */       {
/*     */         protected void invalidated() {
/* 380 */           if (null != AudioClip.this.audioClip) {
/* 381 */             int i = AudioClip.this.cycleCount.get();
/* 382 */             if (-1 != i) {
/* 383 */               i = Math.max(1, i);
/* 384 */               AudioClip.this.audioClip.setLoopCount(i - 1);
/*     */             } else {
/* 386 */               AudioClip.this.audioClip.setLoopCount(i);
/*     */             }
/*     */           }
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 393 */           return AudioClip.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 398 */           return "cycleCount";
/*     */         }
/*     */       };
/*     */     }
/* 402 */     return this.cycleCount;
/*     */   }
/*     */ 
/*     */   public void play()
/*     */   {
/* 408 */     if (null != this.audioClip)
/* 409 */       this.audioClip.play();
/*     */   }
/*     */ 
/*     */   public void play(double paramDouble)
/*     */   {
/* 419 */     if (null != this.audioClip)
/* 420 */       this.audioClip.play(paramDouble);
/*     */   }
/*     */ 
/*     */   public void play(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, int paramInt)
/*     */   {
/* 442 */     if (null != this.audioClip)
/* 443 */       this.audioClip.play(paramDouble1, paramDouble2, paramDouble3, paramDouble4, this.audioClip.loopCount(), paramInt);
/*     */   }
/*     */ 
/*     */   public boolean isPlaying()
/*     */   {
/* 453 */     return (null != this.audioClip) && (this.audioClip.isPlaying());
/*     */   }
/*     */ 
/*     */   public void stop()
/*     */   {
/* 460 */     if (null != this.audioClip)
/* 461 */       this.audioClip.stop();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.media.AudioClip
 * JD-Core Version:    0.6.2
 */