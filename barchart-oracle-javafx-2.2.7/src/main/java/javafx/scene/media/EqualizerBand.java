/*     */ package javafx.scene.media;
/*     */ 
/*     */ import javafx.beans.property.DoubleProperty;
/*     */ import javafx.beans.property.DoublePropertyBase;
/*     */ 
/*     */ public final class EqualizerBand
/*     */ {
/*     */   public static final double MIN_GAIN = -24.0D;
/*     */   public static final double MAX_GAIN = 12.0D;
/*     */   private com.sun.media.jfxmedia.effects.EqualizerBand jfxBand;
/*     */   private DoubleProperty centerFrequency;
/*     */   private DoubleProperty bandwidth;
/*     */   private DoubleProperty gain;
/*     */ 
/*     */   public EqualizerBand()
/*     */   {
/*     */   }
/*     */ 
/*     */   public EqualizerBand(double paramDouble1, double paramDouble2, double paramDouble3)
/*     */   {
/*  54 */     setCenterFrequency(paramDouble1);
/*  55 */     setBandwidth(paramDouble2);
/*  56 */     setGain(paramDouble3);
/*     */   }
/*     */ 
/*     */   void setJfxBand(com.sun.media.jfxmedia.effects.EqualizerBand paramEqualizerBand)
/*     */   {
/*  65 */     if (paramEqualizerBand == null) {
/*  66 */       throw new NullPointerException("jfxBand == null!");
/*     */     }
/*  68 */     this.jfxBand = paramEqualizerBand;
/*     */   }
/*     */ 
/*     */   public final void setCenterFrequency(double paramDouble)
/*     */   {
/*  83 */     centerFrequencyProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getCenterFrequency()
/*     */   {
/*  91 */     return this.centerFrequency == null ? 0.0D : this.centerFrequency.get();
/*     */   }
/*     */ 
/*     */   public DoubleProperty centerFrequencyProperty() {
/*  95 */     if (this.centerFrequency == null) {
/*  96 */       this.centerFrequency = new DoublePropertyBase()
/*     */       {
/*     */         protected void invalidated()
/*     */         {
/* 100 */           double d = EqualizerBand.this.centerFrequency.get();
/* 101 */           if ((EqualizerBand.this.jfxBand != null) && (d > 0.0D))
/* 102 */             EqualizerBand.this.jfxBand.setCenterFrequency(d);
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 108 */           return EqualizerBand.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 113 */           return "centerFrequency";
/*     */         }
/*     */       };
/*     */     }
/* 117 */     return this.centerFrequency;
/*     */   }
/*     */ 
/*     */   public final void setBandwidth(double paramDouble)
/*     */   {
/* 132 */     bandwidthProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getBandwidth()
/*     */   {
/* 140 */     return this.bandwidth == null ? 0.0D : this.bandwidth.get();
/*     */   }
/*     */ 
/*     */   public DoubleProperty bandwidthProperty() {
/* 144 */     if (this.bandwidth == null) {
/* 145 */       this.bandwidth = new DoublePropertyBase()
/*     */       {
/*     */         protected void invalidated()
/*     */         {
/* 149 */           double d = EqualizerBand.this.bandwidth.get();
/* 150 */           if ((EqualizerBand.this.jfxBand != null) && (d > 0.0D))
/* 151 */             EqualizerBand.this.jfxBand.setBandwidth(d);
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 157 */           return EqualizerBand.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 162 */           return "bandwidth";
/*     */         }
/*     */       };
/*     */     }
/* 166 */     return this.bandwidth;
/*     */   }
/*     */ 
/*     */   public final void setGain(double paramDouble)
/*     */   {
/* 182 */     gainProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getGain()
/*     */   {
/* 190 */     return this.gain == null ? 0.0D : this.gain.get();
/*     */   }
/*     */ 
/*     */   public DoubleProperty gainProperty() {
/* 194 */     if (this.gain == null) {
/* 195 */       this.gain = new DoublePropertyBase()
/*     */       {
/*     */         protected void invalidated()
/*     */         {
/* 199 */           if (EqualizerBand.this.jfxBand != null)
/* 200 */             EqualizerBand.this.jfxBand.setGain(EqualizerBand.this.gain.get());
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 206 */           return EqualizerBand.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 211 */           return "gain";
/*     */         }
/*     */       };
/*     */     }
/* 215 */     return this.gain;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.media.EqualizerBand
 * JD-Core Version:    0.6.2
 */