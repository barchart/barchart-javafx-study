/*     */ package javafx.animation;
/*     */ 
/*     */ import javafx.beans.property.DoubleProperty;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.ObjectPropertyBase;
/*     */ import javafx.beans.property.SimpleDoubleProperty;
/*     */ import javafx.beans.property.SimpleObjectProperty;
/*     */ import javafx.scene.Node;
/*     */ import javafx.util.Duration;
/*     */ 
/*     */ public final class TranslateTransition extends Transition
/*     */ {
/*     */   private static final double EPSILON = 1.0E-12D;
/*     */   private double startX;
/*     */   private double startY;
/*     */   private double startZ;
/*     */   private double deltaX;
/*     */   private double deltaY;
/*     */   private double deltaZ;
/*     */   private ObjectProperty<Node> node;
/* 104 */   private static final Node DEFAULT_NODE = null;
/*     */   private Node cachedNode;
/*     */   private ObjectProperty<Duration> duration;
/* 141 */   private static final Duration DEFAULT_DURATION = Duration.millis(400.0D);
/*     */   private DoubleProperty fromX;
/*     */   private static final double DEFAULT_FROM_X = (0.0D / 0.0D);
/*     */   private DoubleProperty fromY;
/*     */   private static final double DEFAULT_FROM_Y = (0.0D / 0.0D);
/*     */   private DoubleProperty fromZ;
/*     */   private static final double DEFAULT_FROM_Z = (0.0D / 0.0D);
/*     */   private DoubleProperty toX;
/*     */   private static final double DEFAULT_TO_X = (0.0D / 0.0D);
/*     */   private DoubleProperty toY;
/*     */   private static final double DEFAULT_TO_Y = (0.0D / 0.0D);
/*     */   private DoubleProperty toZ;
/*     */   private static final double DEFAULT_TO_Z = (0.0D / 0.0D);
/*     */   private DoubleProperty byX;
/*     */   private static final double DEFAULT_BY_X = 0.0D;
/*     */   private DoubleProperty byY;
/*     */   private static final double DEFAULT_BY_Y = 0.0D;
/*     */   private DoubleProperty byZ;
/*     */   private static final double DEFAULT_BY_Z = 0.0D;
/*     */ 
/*     */   public final void setNode(Node paramNode)
/*     */   {
/* 107 */     if ((this.node != null) || (paramNode != null))
/* 108 */       nodeProperty().set(paramNode);
/*     */   }
/*     */ 
/*     */   public final Node getNode()
/*     */   {
/* 113 */     return this.node == null ? DEFAULT_NODE : (Node)this.node.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<Node> nodeProperty() {
/* 117 */     if (this.node == null) {
/* 118 */       this.node = new SimpleObjectProperty(this, "node", DEFAULT_NODE);
/*     */     }
/* 120 */     return this.node;
/*     */   }
/*     */ 
/*     */   public final void setDuration(Duration paramDuration)
/*     */   {
/* 144 */     if ((this.duration != null) || (!DEFAULT_DURATION.equals(paramDuration)))
/* 145 */       durationProperty().set(paramDuration);
/*     */   }
/*     */ 
/*     */   public final Duration getDuration()
/*     */   {
/* 150 */     return this.duration == null ? DEFAULT_DURATION : (Duration)this.duration.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<Duration> durationProperty() {
/* 154 */     if (this.duration == null) {
/* 155 */       this.duration = new ObjectPropertyBase(DEFAULT_DURATION)
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 159 */           TranslateTransition.this.setCycleDuration(TranslateTransition.this.getDuration());
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 164 */           return TranslateTransition.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 169 */           return "duration";
/*     */         }
/*     */       };
/*     */     }
/* 173 */     return this.duration;
/*     */   }
/*     */ 
/*     */   public final void setFromX(double paramDouble)
/*     */   {
/* 191 */     if ((this.fromX != null) || (!Double.isNaN(paramDouble)))
/* 192 */       fromXProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getFromX()
/*     */   {
/* 197 */     return this.fromX == null ? (0.0D / 0.0D) : this.fromX.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty fromXProperty() {
/* 201 */     if (this.fromX == null) {
/* 202 */       this.fromX = new SimpleDoubleProperty(this, "fromX", (0.0D / 0.0D));
/*     */     }
/* 204 */     return this.fromX;
/*     */   }
/*     */ 
/*     */   public final void setFromY(double paramDouble)
/*     */   {
/* 222 */     if ((this.fromY != null) || (!Double.isNaN(paramDouble)))
/* 223 */       fromYProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getFromY()
/*     */   {
/* 228 */     return this.fromY == null ? (0.0D / 0.0D) : this.fromY.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty fromYProperty() {
/* 232 */     if (this.fromY == null) {
/* 233 */       this.fromY = new SimpleDoubleProperty(this, "fromY", (0.0D / 0.0D));
/*     */     }
/* 235 */     return this.fromY;
/*     */   }
/*     */ 
/*     */   public final void setFromZ(double paramDouble)
/*     */   {
/* 253 */     if ((this.fromZ != null) || (!Double.isNaN(paramDouble)))
/* 254 */       fromZProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getFromZ()
/*     */   {
/* 259 */     return this.fromZ == null ? (0.0D / 0.0D) : this.fromZ.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty fromZProperty() {
/* 263 */     if (this.fromZ == null) {
/* 264 */       this.fromZ = new SimpleDoubleProperty(this, "fromZ", (0.0D / 0.0D));
/*     */     }
/* 266 */     return this.fromZ;
/*     */   }
/*     */ 
/*     */   public final void setToX(double paramDouble)
/*     */   {
/* 283 */     if ((this.toX != null) || (!Double.isNaN(paramDouble)))
/* 284 */       toXProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getToX()
/*     */   {
/* 289 */     return this.toX == null ? (0.0D / 0.0D) : this.toX.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty toXProperty() {
/* 293 */     if (this.toX == null) {
/* 294 */       this.toX = new SimpleDoubleProperty(this, "toX", (0.0D / 0.0D));
/*     */     }
/* 296 */     return this.toX;
/*     */   }
/*     */ 
/*     */   public final void setToY(double paramDouble)
/*     */   {
/* 313 */     if ((this.toY != null) || (!Double.isNaN(paramDouble)))
/* 314 */       toYProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getToY()
/*     */   {
/* 319 */     return this.toY == null ? (0.0D / 0.0D) : this.toY.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty toYProperty() {
/* 323 */     if (this.toY == null) {
/* 324 */       this.toY = new SimpleDoubleProperty(this, "toY", (0.0D / 0.0D));
/*     */     }
/* 326 */     return this.toY;
/*     */   }
/*     */ 
/*     */   public final void setToZ(double paramDouble)
/*     */   {
/* 344 */     if ((this.toZ != null) || (!Double.isNaN(paramDouble)))
/* 345 */       toZProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getToZ()
/*     */   {
/* 350 */     return this.toZ == null ? (0.0D / 0.0D) : this.toZ.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty toZProperty() {
/* 354 */     if (this.toZ == null) {
/* 355 */       this.toZ = new SimpleDoubleProperty(this, "toZ", (0.0D / 0.0D));
/*     */     }
/* 357 */     return this.toZ;
/*     */   }
/*     */ 
/*     */   public final void setByX(double paramDouble)
/*     */   {
/* 373 */     if ((this.byX != null) || (Math.abs(paramDouble - 0.0D) > 1.0E-12D))
/* 374 */       byXProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getByX()
/*     */   {
/* 379 */     return this.byX == null ? 0.0D : this.byX.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty byXProperty() {
/* 383 */     if (this.byX == null) {
/* 384 */       this.byX = new SimpleDoubleProperty(this, "byX", 0.0D);
/*     */     }
/* 386 */     return this.byX;
/*     */   }
/*     */ 
/*     */   public final void setByY(double paramDouble)
/*     */   {
/* 402 */     if ((this.byY != null) || (Math.abs(paramDouble - 0.0D) > 1.0E-12D))
/* 403 */       byYProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getByY()
/*     */   {
/* 408 */     return this.byY == null ? 0.0D : this.byY.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty byYProperty() {
/* 412 */     if (this.byY == null) {
/* 413 */       this.byY = new SimpleDoubleProperty(this, "byY", 0.0D);
/*     */     }
/* 415 */     return this.byY;
/*     */   }
/*     */ 
/*     */   public final void setByZ(double paramDouble)
/*     */   {
/* 431 */     if ((this.byZ != null) || (Math.abs(paramDouble - 0.0D) > 1.0E-12D))
/* 432 */       byZProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getByZ()
/*     */   {
/* 437 */     return this.byZ == null ? 0.0D : this.byZ.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty byZProperty() {
/* 441 */     if (this.byZ == null) {
/* 442 */       this.byZ = new SimpleDoubleProperty(this, "byZ", 0.0D);
/*     */     }
/* 444 */     return this.byZ;
/*     */   }
/*     */ 
/*     */   public TranslateTransition(Duration paramDuration, Node paramNode)
/*     */   {
/* 456 */     setDuration(paramDuration);
/* 457 */     setNode(paramNode);
/* 458 */     setCycleDuration(paramDuration);
/*     */   }
/*     */ 
/*     */   public TranslateTransition(Duration paramDuration)
/*     */   {
/* 468 */     this(paramDuration, null);
/*     */   }
/*     */ 
/*     */   public TranslateTransition()
/*     */   {
/* 475 */     this(DEFAULT_DURATION, null);
/*     */   }
/*     */ 
/*     */   public void interpolate(double paramDouble)
/*     */   {
/* 483 */     if (!Double.isNaN(this.startX)) {
/* 484 */       this.cachedNode.setTranslateX(this.startX + paramDouble * this.deltaX);
/*     */     }
/* 486 */     if (!Double.isNaN(this.startY)) {
/* 487 */       this.cachedNode.setTranslateY(this.startY + paramDouble * this.deltaY);
/*     */     }
/* 489 */     if (!Double.isNaN(this.startZ))
/* 490 */       this.cachedNode.setTranslateZ(this.startZ + paramDouble * this.deltaZ);
/*     */   }
/*     */ 
/*     */   private Node getTargetNode()
/*     */   {
/* 495 */     Node localNode = getNode();
/* 496 */     return localNode != null ? localNode : getParentTargetNode();
/*     */   }
/*     */ 
/*     */   boolean impl_startable(boolean paramBoolean)
/*     */   {
/* 501 */     return (super.impl_startable(paramBoolean)) && ((getTargetNode() != null) || ((!paramBoolean) && (this.cachedNode != null)));
/*     */   }
/*     */ 
/*     */   void impl_sync(boolean paramBoolean)
/*     */   {
/* 507 */     super.impl_sync(paramBoolean);
/* 508 */     if ((paramBoolean) || (this.cachedNode == null)) {
/* 509 */       this.cachedNode = getTargetNode();
/*     */ 
/* 511 */       double d1 = getFromX();
/* 512 */       double d2 = getFromY();
/* 513 */       double d3 = getFromZ();
/*     */ 
/* 515 */       double d4 = getToX();
/* 516 */       double d5 = getToY();
/* 517 */       double d6 = getToZ();
/*     */ 
/* 519 */       double d7 = getByX();
/* 520 */       double d8 = getByY();
/* 521 */       double d9 = getByZ();
/*     */ 
/* 523 */       if ((Double.isNaN(d1)) && (Double.isNaN(d4)) && (Math.abs(d7) < 1.0E-12D)) {
/* 524 */         this.startX = (0.0D / 0.0D);
/*     */       } else {
/* 526 */         this.startX = (!Double.isNaN(d1) ? d1 : this.cachedNode.getTranslateX());
/* 527 */         this.deltaX = (!Double.isNaN(d4) ? d4 - this.startX : d7);
/*     */       }
/*     */ 
/* 530 */       if ((Double.isNaN(d2)) && (Double.isNaN(d5)) && (Math.abs(d8) < 1.0E-12D)) {
/* 531 */         this.startY = (0.0D / 0.0D);
/*     */       } else {
/* 533 */         this.startY = (!Double.isNaN(d2) ? d2 : this.cachedNode.getTranslateY());
/* 534 */         this.deltaY = (!Double.isNaN(d5) ? d5 - this.startY : getByY());
/*     */       }
/*     */ 
/* 537 */       if ((Double.isNaN(d3)) && (Double.isNaN(d6)) && (Math.abs(d9) < 1.0E-12D)) {
/* 538 */         this.startZ = (0.0D / 0.0D);
/*     */       } else {
/* 540 */         this.startZ = (!Double.isNaN(d3) ? d3 : this.cachedNode.getTranslateZ());
/* 541 */         this.deltaZ = (!Double.isNaN(d6) ? d6 - this.startZ : getByZ());
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.animation.TranslateTransition
 * JD-Core Version:    0.6.2
 */