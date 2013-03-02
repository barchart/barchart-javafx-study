/*     */ package javafx.animation;
/*     */ 
/*     */ import javafx.scene.Node;
/*     */ import javafx.util.Builder;
/*     */ import javafx.util.Duration;
/*     */ 
/*     */ public final class TranslateTransitionBuilder extends TransitionBuilder<TranslateTransitionBuilder>
/*     */   implements Builder<TranslateTransition>
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
/*     */   public static TranslateTransitionBuilder create()
/*     */   {
/*  15 */     return new TranslateTransitionBuilder();
/*     */   }
/*     */ 
/*     */   private void __set(int paramInt)
/*     */   {
/*  20 */     this.__set |= 1 << paramInt;
/*     */   }
/*     */   public void applyTo(TranslateTransition paramTranslateTransition) {
/*  23 */     super.applyTo(paramTranslateTransition);
/*  24 */     int i = this.__set;
/*  25 */     while (i != 0) {
/*  26 */       int j = Integer.numberOfTrailingZeros(i);
/*  27 */       i &= (1 << j ^ 0xFFFFFFFF);
/*  28 */       switch (j) { case 0:
/*  29 */         paramTranslateTransition.setByX(this.byX); break;
/*     */       case 1:
/*  30 */         paramTranslateTransition.setByY(this.byY); break;
/*     */       case 2:
/*  31 */         paramTranslateTransition.setByZ(this.byZ); break;
/*     */       case 3:
/*  32 */         paramTranslateTransition.setDuration(this.duration); break;
/*     */       case 4:
/*  33 */         paramTranslateTransition.setFromX(this.fromX); break;
/*     */       case 5:
/*  34 */         paramTranslateTransition.setFromY(this.fromY); break;
/*     */       case 6:
/*  35 */         paramTranslateTransition.setFromZ(this.fromZ); break;
/*     */       case 7:
/*  36 */         paramTranslateTransition.setNode(this.node); break;
/*     */       case 8:
/*  37 */         paramTranslateTransition.setToX(this.toX); break;
/*     */       case 9:
/*  38 */         paramTranslateTransition.setToY(this.toY); break;
/*     */       case 10:
/*  39 */         paramTranslateTransition.setToZ(this.toZ);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public TranslateTransitionBuilder byX(double paramDouble)
/*     */   {
/*  49 */     this.byX = paramDouble;
/*  50 */     __set(0);
/*  51 */     return this;
/*     */   }
/*     */ 
/*     */   public TranslateTransitionBuilder byY(double paramDouble)
/*     */   {
/*  59 */     this.byY = paramDouble;
/*  60 */     __set(1);
/*  61 */     return this;
/*     */   }
/*     */ 
/*     */   public TranslateTransitionBuilder byZ(double paramDouble)
/*     */   {
/*  69 */     this.byZ = paramDouble;
/*  70 */     __set(2);
/*  71 */     return this;
/*     */   }
/*     */ 
/*     */   public TranslateTransitionBuilder duration(Duration paramDuration)
/*     */   {
/*  79 */     this.duration = paramDuration;
/*  80 */     __set(3);
/*  81 */     return this;
/*     */   }
/*     */ 
/*     */   public TranslateTransitionBuilder fromX(double paramDouble)
/*     */   {
/*  89 */     this.fromX = paramDouble;
/*  90 */     __set(4);
/*  91 */     return this;
/*     */   }
/*     */ 
/*     */   public TranslateTransitionBuilder fromY(double paramDouble)
/*     */   {
/*  99 */     this.fromY = paramDouble;
/* 100 */     __set(5);
/* 101 */     return this;
/*     */   }
/*     */ 
/*     */   public TranslateTransitionBuilder fromZ(double paramDouble)
/*     */   {
/* 109 */     this.fromZ = paramDouble;
/* 110 */     __set(6);
/* 111 */     return this;
/*     */   }
/*     */ 
/*     */   public TranslateTransitionBuilder node(Node paramNode)
/*     */   {
/* 119 */     this.node = paramNode;
/* 120 */     __set(7);
/* 121 */     return this;
/*     */   }
/*     */ 
/*     */   public TranslateTransitionBuilder toX(double paramDouble)
/*     */   {
/* 129 */     this.toX = paramDouble;
/* 130 */     __set(8);
/* 131 */     return this;
/*     */   }
/*     */ 
/*     */   public TranslateTransitionBuilder toY(double paramDouble)
/*     */   {
/* 139 */     this.toY = paramDouble;
/* 140 */     __set(9);
/* 141 */     return this;
/*     */   }
/*     */ 
/*     */   public TranslateTransitionBuilder toZ(double paramDouble)
/*     */   {
/* 149 */     this.toZ = paramDouble;
/* 150 */     __set(10);
/* 151 */     return this;
/*     */   }
/*     */ 
/*     */   public TranslateTransition build()
/*     */   {
/* 158 */     TranslateTransition localTranslateTransition = new TranslateTransition();
/* 159 */     applyTo(localTranslateTransition);
/* 160 */     return localTranslateTransition;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.animation.TranslateTransitionBuilder
 * JD-Core Version:    0.6.2
 */