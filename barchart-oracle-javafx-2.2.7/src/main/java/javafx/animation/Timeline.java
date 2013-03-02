/*     */ package javafx.animation;
/*     */ 
/*     */ import com.sun.javafx.collections.TrackableObservableList;
/*     */ import com.sun.scenario.animation.shared.TimelineClipCore;
/*     */ import java.util.Arrays;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javafx.collections.ListChangeListener.Change;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.collections.ObservableMap;
/*     */ import javafx.util.Duration;
/*     */ 
/*     */ public final class Timeline extends Animation
/*     */ {
/*     */   final TimelineClipCore clipCore;
/*  78 */   private final ObservableList<KeyFrame> keyFrames = new TrackableObservableList()
/*     */   {
/*     */     protected void onChanged(ListChangeListener.Change<KeyFrame> paramAnonymousChange) {
/*  81 */       while (paramAnonymousChange.next())
/*  82 */         if (!paramAnonymousChange.wasPermutated()) {
/*  83 */           for (Iterator localIterator = paramAnonymousChange.getRemoved().iterator(); localIterator.hasNext(); ) { localKeyFrame = (KeyFrame)localIterator.next();
/*  84 */             str = localKeyFrame.getName();
/*  85 */             if (str != null)
/*  86 */               Timeline.this.getCuePoints().remove(str);
/*     */           }
/*  89 */           KeyFrame localKeyFrame;
/*     */           String str;
/*  89 */           for (localIterator = paramAnonymousChange.getAddedSubList().iterator(); localIterator.hasNext(); ) { localKeyFrame = (KeyFrame)localIterator.next();
/*  90 */             str = localKeyFrame.getName();
/*  91 */             if (str != null) {
/*  92 */               Timeline.this.getCuePoints().put(str, localKeyFrame.getTime());
/*     */             }
/*     */           }
/*  95 */           Timeline.this.updateKeyFrames();
/*     */         }
/*     */     }
/*  78 */   };
/*     */ 
/* 159 */   private static final Comparator<KeyFrame> KEY_FRAME_COMPARATOR = new Comparator()
/*     */   {
/*     */     public int compare(KeyFrame paramAnonymousKeyFrame1, KeyFrame paramAnonymousKeyFrame2) {
/* 162 */       return paramAnonymousKeyFrame1.getTime().compareTo(paramAnonymousKeyFrame2.getTime());
/*     */     }
/* 159 */   };
/*     */ 
/*     */   public final ObservableList<KeyFrame> getKeyFrames()
/*     */   {
/*  76 */     return this.keyFrames;
/*     */   }
/*     */ 
/*     */   public Timeline(double paramDouble, KeyFrame[] paramArrayOfKeyFrame)
/*     */   {
/* 112 */     super(paramDouble);
/* 113 */     this.clipCore = new TimelineClipCore(this);
/* 114 */     getKeyFrames().setAll(paramArrayOfKeyFrame);
/*     */   }
/*     */ 
/*     */   public Timeline(KeyFrame[] paramArrayOfKeyFrame)
/*     */   {
/* 125 */     this.clipCore = new TimelineClipCore(this);
/* 126 */     getKeyFrames().setAll(paramArrayOfKeyFrame);
/*     */   }
/*     */ 
/*     */   public Timeline(double paramDouble)
/*     */   {
/* 138 */     super(paramDouble);
/* 139 */     this.clipCore = new TimelineClipCore(this);
/*     */   }
/*     */ 
/*     */   public Timeline()
/*     */   {
/* 147 */     this.clipCore = new TimelineClipCore(this);
/*     */   }
/*     */ 
/*     */   Timeline(TimelineClipCore paramTimelineClipCore)
/*     */   {
/* 152 */     this.clipCore = paramTimelineClipCore;
/*     */   }
/*     */ 
/*     */   private void updateKeyFrames()
/*     */   {
/* 167 */     KeyFrame[] arrayOfKeyFrame = new KeyFrame[getKeyFrames().size()];
/* 168 */     this.keyFrames.toArray(arrayOfKeyFrame);
/* 169 */     Arrays.sort(arrayOfKeyFrame, KEY_FRAME_COMPARATOR);
/* 170 */     Duration localDuration = arrayOfKeyFrame.length == 0 ? Duration.ZERO : arrayOfKeyFrame[(arrayOfKeyFrame.length - 1)].getTime();
/*     */ 
/* 172 */     setCycleDuration(localDuration);
/* 173 */     this.clipCore.setKeyFrames(arrayOfKeyFrame);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void impl_playTo(long paramLong1, long paramLong2)
/*     */   {
/* 182 */     this.clipCore.playTo(paramLong1);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void impl_jumpTo(long paramLong1, long paramLong2)
/*     */   {
/* 191 */     this.clipCore.jumpTo(paramLong1);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void impl_setCurrentRate(double paramDouble)
/*     */   {
/* 200 */     super.impl_setCurrentRate(paramDouble);
/* 201 */     this.clipCore.notifyCurrentRateChanged();
/*     */   }
/*     */ 
/*     */   void impl_start(boolean paramBoolean) {
/* 205 */     super.impl_start(paramBoolean);
/* 206 */     this.clipCore.start(paramBoolean);
/*     */   }
/*     */ 
/*     */   public void stop()
/*     */   {
/* 214 */     if (getStatus() == Animation.Status.RUNNING) {
/* 215 */       this.clipCore.abort();
/*     */     }
/* 217 */     super.stop();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.animation.Timeline
 * JD-Core Version:    0.6.2
 */