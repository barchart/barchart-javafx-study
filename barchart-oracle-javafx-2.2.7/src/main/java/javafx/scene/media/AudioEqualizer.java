/*     */ package javafx.scene.media;
/*     */ 
/*     */ import com.sun.javafx.collections.VetoableObservableList;
/*     */ import com.sun.media.jfxmedia.logging.Logger;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javafx.beans.property.BooleanProperty;
/*     */ import javafx.beans.property.BooleanPropertyBase;
/*     */ import javafx.collections.ObservableList;
/*     */ 
/*     */ public final class AudioEqualizer
/*     */ {
/*     */   public static final int MAX_NUM_BANDS = 64;
/*  34 */   private com.sun.media.jfxmedia.effects.AudioEqualizer jfxEqualizer = null;
/*     */   private final ObservableList<EqualizerBand> bands;
/*     */   private BooleanProperty enabled;
/*     */ 
/*     */   public final ObservableList<EqualizerBand> getBands()
/*     */   {
/*  75 */     return this.bands;
/*     */   }
/*     */ 
/*     */   AudioEqualizer() {
/*  79 */     this.bands = new Bands(null);
/*     */ 
/*  82 */     this.bands.addAll(new EqualizerBand[] { new EqualizerBand(32.0D, 19.0D, 0.0D), new EqualizerBand(64.0D, 39.0D, 0.0D), new EqualizerBand(125.0D, 78.0D, 0.0D), new EqualizerBand(250.0D, 156.0D, 0.0D), new EqualizerBand(500.0D, 312.0D, 0.0D), new EqualizerBand(1000.0D, 625.0D, 0.0D), new EqualizerBand(2000.0D, 1250.0D, 0.0D), new EqualizerBand(4000.0D, 2500.0D, 0.0D), new EqualizerBand(8000.0D, 5000.0D, 0.0D), new EqualizerBand(16000.0D, 10000.0D, 0.0D) });
/*     */   }
/*     */ 
/*     */   void setAudioEqualizer(com.sun.media.jfxmedia.effects.AudioEqualizer paramAudioEqualizer)
/*     */   {
/* 100 */     if (paramAudioEqualizer == null) {
/* 101 */       throw new NullPointerException("jfxEqualizer == null!");
/*     */     }
/*     */ 
/* 104 */     if (this.jfxEqualizer != null) {
/* 105 */       return;
/*     */     }
/*     */ 
/* 108 */     this.jfxEqualizer = paramAudioEqualizer;
/*     */ 
/* 110 */     paramAudioEqualizer.setEnabled(isEnabled());
/*     */ 
/* 112 */     for (EqualizerBand localEqualizerBand : this.bands)
/* 113 */       if ((localEqualizerBand.getCenterFrequency() > 0.0D) && (localEqualizerBand.getBandwidth() > 0.0D)) {
/* 114 */         com.sun.media.jfxmedia.effects.EqualizerBand localEqualizerBand1 = paramAudioEqualizer.addBand(localEqualizerBand.getCenterFrequency(), localEqualizerBand.getBandwidth(), localEqualizerBand.getGain());
/*     */ 
/* 120 */         localEqualizerBand.setJfxBand(localEqualizerBand1);
/*     */       } else {
/* 122 */         Logger.logMsg(4, "Center frequency [" + localEqualizerBand.getCenterFrequency() + "] and bandwidth [" + localEqualizerBand.getBandwidth() + "] must be greater than 0.");
/*     */       }
/*     */   }
/*     */ 
/*     */   public final void setEnabled(boolean paramBoolean)
/*     */   {
/* 141 */     enabledProperty().set(paramBoolean);
/*     */   }
/*     */ 
/*     */   public final boolean isEnabled()
/*     */   {
/* 149 */     return this.enabled == null ? false : this.enabled.get();
/*     */   }
/*     */ 
/*     */   public BooleanProperty enabledProperty() {
/* 153 */     if (this.enabled == null) {
/* 154 */       this.enabled = new BooleanPropertyBase()
/*     */       {
/*     */         protected void invalidated()
/*     */         {
/* 158 */           if (AudioEqualizer.this.jfxEqualizer != null)
/* 159 */             AudioEqualizer.this.jfxEqualizer.setEnabled(AudioEqualizer.this.enabled.get());
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 165 */           return AudioEqualizer.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 170 */           return "enabled";
/*     */         }
/*     */       };
/*     */     }
/* 174 */     return this.enabled;
/*     */   }
/*     */ 
/*     */   private class Bands extends VetoableObservableList<EqualizerBand>
/*     */   {
/*     */     private Bands()
/*     */     {
/*     */     }
/*     */ 
/*     */     protected void onProposedChange(List<EqualizerBand> paramList, int[] paramArrayOfInt)
/*     */     {
/*     */       Object localObject1;
/*     */       Object localObject2;
/*     */       Iterator localIterator;
/* 181 */       if (AudioEqualizer.this.jfxEqualizer != null) {
/* 182 */         for (int i = 0; i < paramArrayOfInt.length; i += 2) {
/* 183 */           for (localObject1 = subList(paramArrayOfInt[i], paramArrayOfInt[(i + 1)]).iterator(); ((Iterator)localObject1).hasNext(); ) { localObject2 = (EqualizerBand)((Iterator)localObject1).next();
/* 184 */             AudioEqualizer.this.jfxEqualizer.removeBand(((EqualizerBand)localObject2).getCenterFrequency());
/*     */           }
/*     */         }
/*     */ 
/* 188 */         for (localIterator = paramList.iterator(); localIterator.hasNext(); ) { localObject1 = (EqualizerBand)localIterator.next();
/* 189 */           if ((((EqualizerBand)localObject1).getCenterFrequency() > 0.0D) && (((EqualizerBand)localObject1).getBandwidth() > 0.0D)) {
/* 190 */             localObject2 = AudioEqualizer.this.jfxEqualizer.addBand(((EqualizerBand)localObject1).getCenterFrequency(), ((EqualizerBand)localObject1).getBandwidth(), ((EqualizerBand)localObject1).getGain());
/*     */ 
/* 196 */             ((EqualizerBand)localObject1).setJfxBand((com.sun.media.jfxmedia.effects.EqualizerBand)localObject2);
/*     */           } else {
/* 198 */             Logger.logMsg(4, "Center frequency [" + ((EqualizerBand)localObject1).getCenterFrequency() + "] and bandwidth [" + ((EqualizerBand)localObject1).getBandwidth() + "] must be greater than 0.");
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.media.AudioEqualizer
 * JD-Core Version:    0.6.2
 */