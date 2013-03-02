/*     */ package com.sun.deploy.uitoolkit.impl.fx;
/*     */ 
/*     */ import com.sun.javafx.scene.control.skin.ProgressBarSkin;
/*     */ import javafx.collections.ObservableList;
/*     */ import javafx.geometry.Insets;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.control.ProgressBar;
/*     */ import javafx.scene.layout.StackPane;
/*     */ import javafx.scene.paint.Color;
/*     */ import javafx.scene.paint.CycleMethod;
/*     */ import javafx.scene.paint.LinearGradient;
/*     */ import javafx.scene.paint.RadialGradient;
/*     */ import javafx.scene.paint.Stop;
/*     */ import javafx.scene.shape.Rectangle;
/*     */ 
/*     */ public class FXProgressBarSkin extends ProgressBarSkin
/*     */ {
/*     */   Rectangle topGradient;
/*     */   Rectangle bottomGradient;
/*     */   Rectangle verticalLines;
/*  18 */   double gradientMargin = 4.0D;
/*  19 */   double gradientRadius = 0.55D;
/*  20 */   double gradientTweak = 1.4D;
/*     */ 
/*     */   public FXProgressBarSkin(ProgressBar control) {
/*  23 */     super(control);
/*     */ 
/*  25 */     this.topGradient = new Rectangle(0.0D, 0.0D, new RadialGradient(0.05D, 0.0D, 0.5D, 0.0D, this.gradientRadius, true, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.rgb(255, 255, 255, 0.82D)), new Stop(0.13D, Color.rgb(255, 255, 255, 0.82D)), new Stop(0.98D, Color.rgb(255, 255, 255, 0.0D)) }));
/*     */ 
/*  30 */     this.bottomGradient = new Rectangle(0.0D, 0.0D, new RadialGradient(0.05D, 0.0D, 0.5D, 1.0D, this.gradientRadius, true, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.rgb(255, 255, 255, 0.82D)), new Stop(0.13D, Color.rgb(255, 255, 255, 0.82D)), new Stop(0.98D, Color.rgb(255, 255, 255, 0.0D)) }));
/*     */ 
/*  35 */     this.topGradient.setManaged(false);
/*  36 */     this.bottomGradient.setManaged(false);
/*     */ 
/*  38 */     ((StackPane)getChildren().get(1)).getChildren().addAll(new Node[] { this.topGradient, this.bottomGradient });
/*     */ 
/*  40 */     this.verticalLines = new Rectangle(0.0D, 0.0D, new LinearGradient(0.0D, 0.0D, 14.0D, 0.0D, false, CycleMethod.REPEAT, new Stop[] { new Stop(0.0D, Color.TRANSPARENT), new Stop(0.9300000000000001D, Color.TRANSPARENT), new Stop(0.9300000000000001D, Color.rgb(184, 184, 184, 0.2D)), new Stop(1.0D, Color.rgb(184, 184, 184, 0.2D)) }));
/*     */ 
/*  45 */     this.verticalLines.setManaged(false);
/*  46 */     getChildren().add(this.verticalLines);
/*     */   }
/*     */ 
/*     */   protected void layoutChildren() {
/*  50 */     super.layoutChildren();
/*     */ 
/*  52 */     if (((ProgressBar)getSkinnable()).isIndeterminate()) {
/*  53 */       return;
/*     */     }
/*     */ 
/*  56 */     StackPane track = (StackPane)getChildren().get(0);
/*  57 */     StackPane bar = (StackPane)getChildren().get(1);
/*     */ 
/*  59 */     if (!bar.getChildren().contains(this.topGradient)) {
/*  60 */       bar.getChildren().add(this.topGradient);
/*     */     }
/*     */ 
/*  63 */     if (!bar.getChildren().contains(this.bottomGradient)) {
/*  64 */       bar.getChildren().add(this.bottomGradient);
/*     */     }
/*     */ 
/*  67 */     if (!getChildren().contains(this.verticalLines)) {
/*  68 */       getChildren().add(this.verticalLines);
/*     */     }
/*     */ 
/*  71 */     Insets insets = getInsets();
/*  72 */     double x = insets.getLeft();
/*  73 */     double y = insets.getTop();
/*  74 */     double w = getWidth() - (insets.getLeft() + insets.getRight());
/*  75 */     double h = getHeight() - (insets.getTop() + insets.getBottom());
/*     */ 
/*  78 */     double scale = Math.floor(w / 14.0D) * 14.0D / w;
/*     */ 
/*  80 */     double barWidth = bar.getWidth() * scale;
/*  81 */     double barHeight = bar.getHeight();
/*     */ 
/*  83 */     track.resize(track.getWidth() * scale, track.getHeight());
/*  84 */     bar.resize(barWidth, barHeight);
/*     */ 
/*  86 */     this.topGradient.setX(x + this.gradientMargin);
/*  87 */     this.topGradient.setY(y + 0.5D);
/*  88 */     this.topGradient.setWidth(barWidth - 2.0D * this.gradientMargin);
/*  89 */     this.topGradient.setHeight(barHeight * 0.3D / this.gradientRadius * this.gradientTweak);
/*     */ 
/*  91 */     this.bottomGradient.setX(x + this.gradientMargin);
/*  92 */     this.bottomGradient.setWidth(barWidth - 2.0D * this.gradientMargin);
/*  93 */     double gh = barHeight * 0.21D / this.gradientRadius * this.gradientTweak;
/*  94 */     this.bottomGradient.setY(barHeight - gh - 0.5D);
/*  95 */     this.bottomGradient.setHeight(gh);
/*     */ 
/*  97 */     this.verticalLines.setX(x);
/*  98 */     this.verticalLines.setY(y);
/*  99 */     this.verticalLines.setWidth(w * scale);
/* 100 */     this.verticalLines.setHeight(h);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.deploy.uitoolkit.impl.fx.FXProgressBarSkin
 * JD-Core Version:    0.6.2
 */