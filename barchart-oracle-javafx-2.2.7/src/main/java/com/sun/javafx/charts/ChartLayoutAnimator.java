/*     */ package com.sun.javafx.charts;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javafx.animation.Animation;
/*     */ import javafx.animation.AnimationTimer;
/*     */ import javafx.animation.KeyFrame;
/*     */ import javafx.animation.SequentialTransition;
/*     */ import javafx.animation.Timeline;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.event.ActionEvent;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.scene.Parent;
/*     */ import javafx.scene.chart.Axis;
/*     */ 
/*     */ public final class ChartLayoutAnimator extends AnimationTimer
/*     */   implements EventHandler<ActionEvent>
/*     */ {
/*     */   private Parent nodeToLayout;
/*  46 */   private final Map<Object, Animation> activeTimeLines = new HashMap();
/*     */   private final boolean isAxis;
/*     */ 
/*     */   public ChartLayoutAnimator(Parent paramParent)
/*     */   {
/*  50 */     this.nodeToLayout = paramParent;
/*  51 */     this.isAxis = (paramParent instanceof Axis);
/*     */   }
/*     */ 
/*     */   public void handle(long paramLong) {
/*  55 */     if (this.isAxis)
/*  56 */       ((Axis)this.nodeToLayout).requestAxisLayout();
/*     */     else
/*  58 */       this.nodeToLayout.requestLayout();
/*     */   }
/*     */ 
/*     */   public void handle(ActionEvent paramActionEvent)
/*     */   {
/*  63 */     Object localObject = this.activeTimeLines.remove(paramActionEvent.getSource());
/*  64 */     if (this.activeTimeLines.isEmpty()) stop();
/*     */ 
/*  66 */     handle(0L);
/*     */   }
/*     */ 
/*     */   public void stop(Object paramObject)
/*     */   {
/*  75 */     Animation localAnimation = (Animation)this.activeTimeLines.remove(paramObject);
/*  76 */     if (localAnimation != null) localAnimation.stop();
/*  77 */     if (this.activeTimeLines.isEmpty()) stop();
/*     */   }
/*     */ 
/*     */   public Object animate(KeyFrame[] paramArrayOfKeyFrame)
/*     */   {
/*  87 */     Timeline localTimeline = new Timeline();
/*  88 */     localTimeline.setAutoReverse(false);
/*  89 */     localTimeline.setCycleCount(1);
/*  90 */     localTimeline.getKeyFrames().addAll(paramArrayOfKeyFrame);
/*  91 */     localTimeline.setOnFinished(this);
/*     */ 
/*  93 */     if (this.activeTimeLines.isEmpty()) start();
/*     */ 
/*  95 */     this.activeTimeLines.put(localTimeline, localTimeline);
/*     */ 
/*  97 */     localTimeline.play();
/*  98 */     return localTimeline;
/*     */   }
/*     */ 
/*     */   public Object animate(Animation paramAnimation)
/*     */   {
/* 108 */     SequentialTransition localSequentialTransition = new SequentialTransition();
/* 109 */     localSequentialTransition.getChildren().add(paramAnimation);
/* 110 */     localSequentialTransition.setOnFinished(this);
/*     */ 
/* 112 */     if (this.activeTimeLines.isEmpty()) start();
/*     */ 
/* 114 */     this.activeTimeLines.put(localSequentialTransition, localSequentialTransition);
/*     */ 
/* 116 */     localSequentialTransition.play();
/* 117 */     return localSequentialTransition;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.charts.ChartLayoutAnimator
 * JD-Core Version:    0.6.2
 */