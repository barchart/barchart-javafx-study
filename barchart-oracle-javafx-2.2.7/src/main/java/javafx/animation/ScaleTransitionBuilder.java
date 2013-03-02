/*     */ package javafx.animation;
/*     */ 
/*     */ import javafx.scene.Node;
/*     */ import javafx.util.Builder;
/*     */ import javafx.util.Duration;
/*     */ 
/*     */ public final class ScaleTransitionBuilder extends TransitionBuilder<ScaleTransitionBuilder>
/*     */   implements Builder<ScaleTransition>
/*     */ {
/*     */   private int __set;
/*     */   private double byX;
/*     */   private double byY;
/*     */   private double byZ;
/*     */   private Duration duration;
/*     */   private double fromX;
/*     */   private double fromY;
/*     */   private double fromZ;
/*     */   private Node node;
/*     */   private double toX;
/*     */   private double toY;
/*     */   private double toZ;
/*     */ 
/*     */   public static ScaleTransitionBuilder create()
/*     */   {
/*  15 */     return new ScaleTransitionBuilder();
/*     */   }
/*     */ 
/*     */   private void __set(int paramInt)
/*     */   {
/*  20 */     this.__set |= 1 << paramInt;
/*     */   }
/*     */   public void applyTo(ScaleTransition paramScaleTransition) {
/*  23 */     super.applyTo(paramScaleTransition);
/*  24 */     int i = this.__set;
/*  25 */     while (i != 0) {
/*  26 */       int j = Integer.numberOfTrailingZeros(i);
/*  27 */       i &= (1 << j ^ 0xFFFFFFFF);
/*  28 */       switch (j) { case 0:
/*  29 */         paramScaleTransition.setByX(this.byX); break;
/*     */       case 1:
/*  30 */         paramScaleTransition.setByY(this.byY); break;
/*     */       case 2:
/*  31 */         paramScaleTransition.setByZ(this.byZ); break;
/*     */       case 3:
/*  32 */         paramScaleTransition.setDuration(this.duration); break;
/*     */       case 4:
/*  33 */         paramScaleTransition.setFromX(this.fromX); break;
/*     */       case 5:
/*  34 */         paramScaleTransition.setFromY(this.fromY); break;
/*     */       case 6:
/*  35 */         paramScaleTransition.setFromZ(this.fromZ); break;
/*     */       case 7:
/*  36 */         paramScaleTransition.setNode(this.node); break;
/*     */       case 8:
/*  37 */         paramScaleTransition.setToX(this.toX); break;
/*     */       case 9:
/*  38 */         paramScaleTransition.setToY(this.toY); break;
/*     */       case 10:
/*  39 */         paramScaleTransition.setToZ(this.toZ);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public ScaleTransitionBuilder byX(double paramDouble)
/*     */   {
/*  49 */     this.byX = paramDouble;
/*  50 */     __set(0);
/*  51 */     return this;
/*     */   }
/*     */ 
/*     */   public ScaleTransitionBuilder byY(double paramDouble)
/*     */   {
/*  59 */     this.byY = paramDouble;
/*  60 */     __set(1);
/*  61 */     return this;
/*     */   }
/*     */ 
/*     */   public ScaleTransitionBuilder byZ(double paramDouble)
/*     */   {
/*  69 */     this.byZ = paramDouble;
/*  70 */     __set(2);
/*  71 */     return this;
/*     */   }
/*     */ 
/*     */   public ScaleTransitionBuilder duration(Duration paramDuration)
/*     */   {
/*  79 */     this.duration = paramDuration;
/*  80 */     __set(3);
/*  81 */     return this;
/*     */   }
/*     */ 
/*     */   public ScaleTransitionBuilder fromX(double paramDouble)
/*     */   {
/*  89 */     this.fromX = paramDouble;
/*  90 */     __set(4);
/*  91 */     return this;
/*     */   }
/*     */ 
/*     */   public ScaleTransitionBuilder fromY(double paramDouble)
/*     */   {
/*  99 */     this.fromY = paramDouble;
/* 100 */     __set(5);
/* 101 */     return this;
/*     */   }
/*     */ 
/*     */   public ScaleTransitionBuilder fromZ(double paramDouble)
/*     */   {
/* 109 */     this.fromZ = paramDouble;
/* 110 */     __set(6);
/* 111 */     return this;
/*     */   }
/*     */ 
/*     */   public ScaleTransitionBuilder node(Node paramNode)
/*     */   {
/* 119 */     this.node = paramNode;
/* 120 */     __set(7);
/* 121 */     return this;
/*     */   }
/*     */ 
/*     */   public ScaleTransitionBuilder toX(double paramDouble)
/*     */   {
/* 129 */     this.toX = paramDouble;
/* 130 */     __set(8);
/* 131 */     return this;
/*     */   }
/*     */ 
/*     */   public ScaleTransitionBuilder toY(double paramDouble)
/*     */   {
/* 139 */     this.toY = paramDouble;
/* 140 */     __set(9);
/* 141 */     return this;
/*     */   }
/*     */ 
/*     */   public ScaleTransitionBuilder toZ(double paramDouble)
/*     */   {
/* 149 */     this.toZ = paramDouble;
/* 150 */     __set(10);
/* 151 */     return this;
/*     */   }
/*     */ 
/*     */   public ScaleTransition build()
/*     */   {
/* 158 */     ScaleTransition localScaleTransition = new ScaleTransition();
/* 159 */     applyTo(localScaleTransition);
/* 160 */     return localScaleTransition;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.animation.ScaleTransitionBuilder
 * JD-Core Version:    0.6.2
 */