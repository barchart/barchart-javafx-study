/*    */ package javafx.animation;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.Collection;
/*    */ import javafx.collections.ObservableList;
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public final class TimelineBuilder extends AnimationBuilder<TimelineBuilder>
/*    */   implements Builder<Timeline>
/*    */ {
/*    */   private boolean __set;
/*    */   private Collection<? extends KeyFrame> keyFrames;
/*    */   private double targetFramerate;
/*    */ 
/*    */   public static TimelineBuilder create()
/*    */   {
/* 15 */     return new TimelineBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(Timeline paramTimeline)
/*    */   {
/* 20 */     super.applyTo(paramTimeline);
/* 21 */     if (this.__set) paramTimeline.getKeyFrames().addAll(this.keyFrames);
/*    */   }
/*    */ 
/*    */   public TimelineBuilder keyFrames(Collection<? extends KeyFrame> paramCollection)
/*    */   {
/* 29 */     this.keyFrames = paramCollection;
/* 30 */     this.__set = true;
/* 31 */     return this;
/*    */   }
/*    */ 
/*    */   public TimelineBuilder keyFrames(KeyFrame[] paramArrayOfKeyFrame)
/*    */   {
/* 38 */     return keyFrames(Arrays.asList(paramArrayOfKeyFrame));
/*    */   }
/*    */ 
/*    */   public TimelineBuilder targetFramerate(double paramDouble)
/*    */   {
/* 46 */     this.targetFramerate = paramDouble;
/* 47 */     return this;
/*    */   }
/*    */ 
/*    */   public Timeline build()
/*    */   {
/* 54 */     Timeline localTimeline = new Timeline(this.targetFramerate);
/* 55 */     applyTo(localTimeline);
/* 56 */     return localTimeline;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.animation.TimelineBuilder
 * JD-Core Version:    0.6.2
 */