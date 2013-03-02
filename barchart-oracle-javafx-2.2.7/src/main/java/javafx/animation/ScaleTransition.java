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
/*     */ public final class ScaleTransition extends Transition
/*     */ {
/*     */   private static final double EPSILON = 1.0E-12D;
/*     */   private double startX;
/*     */   private double startY;
/*     */   private double startZ;
/*     */   private double deltaX;
/*     */   private double deltaY;
/*     */   private double deltaZ;
/*     */   private ObjectProperty<Node> node;
/* 106 */   private static final Node DEFAULT_NODE = null;
/*     */   private Node cachedNode;
/*     */   private ObjectProperty<Duration> duration;
/* 143 */   private static final Duration DEFAULT_DURATION = Duration.millis(400.0D);
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
/* 109 */     if ((this.node != null) || (paramNode != null))
/* 110 */       nodeProperty().set(paramNode);
/*     */   }
/*     */ 
/*     */   public final Node getNode()
/*     */   {
/* 115 */     return this.node == null ? DEFAULT_NODE : (Node)this.node.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<Node> nodeProperty() {
/* 119 */     if (this.node == null) {
/* 120 */       this.node = new SimpleObjectProperty(this, "node", DEFAULT_NODE);
/*     */     }
/* 122 */     return this.node;
/*     */   }
/*     */ 
/*     */   public final void setDuration(Duration paramDuration)
/*     */   {
/* 146 */     if ((this.duration != null) || (!DEFAULT_DURATION.equals(paramDuration)))
/* 147 */       durationProperty().set(paramDuration);
/*     */   }
/*     */ 
/*     */   public final Duration getDuration()
/*     */   {
/* 152 */     return this.duration == null ? DEFAULT_DURATION : (Duration)this.duration.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<Duration> durationProperty() {
/* 156 */     if (this.duration == null) {
/* 157 */       this.duration = new ObjectPropertyBase(DEFAULT_DURATION)
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 161 */           ScaleTransition.this.setCycleDuration(ScaleTransition.this.getDuration());
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 166 */           return ScaleTransition.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 171 */           return "duration";
/*     */         }
/*     */       };
/*     */     }
/* 175 */     return this.duration;
/*     */   }
/*     */ 
/*     */   public final void setFromX(double paramDouble)
/*     */   {
/* 192 */     if ((this.fromX != null) || (!Double.isNaN(paramDouble)))
/* 193 */       fromXProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getFromX()
/*     */   {
/* 198 */     return this.fromX == null ? (0.0D / 0.0D) : this.fromX.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty fromXProperty() {
/* 202 */     if (this.fromX == null) {
/* 203 */       this.fromX = new SimpleDoubleProperty(this, "fromX", (0.0D / 0.0D));
/*     */     }
/* 205 */     return this.fromX;
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
/* 252 */     if ((this.fromZ != null) || (!Double.isNaN(paramDouble)))
/* 253 */       fromZProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getFromZ()
/*     */   {
/* 258 */     return this.fromZ == null ? (0.0D / 0.0D) : this.fromZ.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty fromZProperty() {
/* 262 */     if (this.fromZ == null) {
/* 263 */       this.fromZ = new SimpleDoubleProperty(this, "fromZ", (0.0D / 0.0D));
/*     */     }
/* 265 */     return this.fromZ;
/*     */   }
/*     */ 
/*     */   public final void setToX(double paramDouble)
/*     */   {
/* 282 */     if ((this.toX != null) || (!Double.isNaN(paramDouble)))
/* 283 */       toXProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getToX()
/*     */   {
/* 288 */     return this.toX == null ? (0.0D / 0.0D) : this.toX.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty toXProperty() {
/* 292 */     if (this.toX == null) {
/* 293 */       this.toX = new SimpleDoubleProperty(this, "toX", (0.0D / 0.0D));
/*     */     }
/* 295 */     return this.toX;
/*     */   }
/*     */ 
/*     */   public final void setToY(double paramDouble)
/*     */   {
/* 312 */     if ((this.toY != null) || (!Double.isNaN(paramDouble)))
/* 313 */       toYProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getToY()
/*     */   {
/* 318 */     return this.toY == null ? (0.0D / 0.0D) : this.toY.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty toYProperty() {
/* 322 */     if (this.toY == null) {
/* 323 */       this.toY = new SimpleDoubleProperty(this, "toY", (0.0D / 0.0D));
/*     */     }
/* 325 */     return this.toY;
/*     */   }
/*     */ 
/*     */   public final void setToZ(double paramDouble)
/*     */   {
/* 342 */     if ((this.toZ != null) || (!Double.isNaN(paramDouble)))
/* 343 */       toZProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getToZ()
/*     */   {
/* 348 */     return this.toZ == null ? (0.0D / 0.0D) : this.toZ.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty toZProperty() {
/* 352 */     if (this.toZ == null) {
/* 353 */       this.toZ = new SimpleDoubleProperty(this, "toZ", (0.0D / 0.0D));
/*     */     }
/* 355 */     return this.toZ;
/*     */   }
/*     */ 
/*     */   public final void setByX(double paramDouble)
/*     */   {
/* 371 */     if ((this.byX != null) || (Math.abs(paramDouble - 0.0D) > 1.0E-12D))
/* 372 */       byXProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getByX()
/*     */   {
/* 377 */     return this.byX == null ? 0.0D : this.byX.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty byXProperty() {
/* 381 */     if (this.byX == null) {
/* 382 */       this.byX = new SimpleDoubleProperty(this, "byX", 0.0D);
/*     */     }
/* 384 */     return this.byX;
/*     */   }
/*     */ 
/*     */   public final void setByY(double paramDouble)
/*     */   {
/* 400 */     if ((this.byY != null) || (Math.abs(paramDouble - 0.0D) > 1.0E-12D))
/* 401 */       byYProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getByY()
/*     */   {
/* 406 */     return this.byY == null ? 0.0D : this.byY.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty byYProperty() {
/* 410 */     if (this.byY == null) {
/* 411 */       this.byY = new SimpleDoubleProperty(this, "byY", 0.0D);
/*     */     }
/* 413 */     return this.byY;
/*     */   }
/*     */ 
/*     */   public final void setByZ(double paramDouble)
/*     */   {
/* 429 */     if ((this.byZ != null) || (Math.abs(paramDouble - 0.0D) > 1.0E-12D))
/* 430 */       byZProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getByZ()
/*     */   {
/* 435 */     return this.byZ == null ? 0.0D : this.byZ.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty byZProperty() {
/* 439 */     if (this.byZ == null) {
/* 440 */       this.byZ = new SimpleDoubleProperty(this, "byZ", 0.0D);
/*     */     }
/* 442 */     return this.byZ;
/*     */   }
/*     */ 
/*     */   public ScaleTransition(Duration paramDuration, Node paramNode)
/*     */   {
/* 454 */     setDuration(paramDuration);
/* 455 */     setNode(paramNode);
/* 456 */     setCycleDuration(paramDuration);
/*     */   }
/*     */ 
/*     */   public ScaleTransition(Duration paramDuration)
/*     */   {
/* 466 */     this(paramDuration, null);
/*     */   }
/*     */ 
/*     */   public ScaleTransition()
/*     */   {
/* 473 */     this(DEFAULT_DURATION, null);
/*     */   }
/*     */ 
/*     */   public void interpolate(double paramDouble)
/*     */   {
/* 481 */     if (!Double.isNaN(this.startX)) {
/* 482 */       this.cachedNode.setScaleX(this.startX + paramDouble * this.deltaX);
/*     */     }
/* 484 */     if (!Double.isNaN(this.startY)) {
/* 485 */       this.cachedNode.setScaleY(this.startY + paramDouble * this.deltaY);
/*     */     }
/* 487 */     if (!Double.isNaN(this.startZ))
/* 488 */       this.cachedNode.setScaleZ(this.startZ + paramDouble * this.deltaZ);
/*     */   }
/*     */ 
/*     */   private Node getTargetNode()
/*     */   {
/* 493 */     Node localNode = getNode();
/* 494 */     return localNode != null ? localNode : getParentTargetNode();
/*     */   }
/*     */ 
/*     */   boolean impl_startable(boolean paramBoolean)
/*     */   {
/* 499 */     return (super.impl_startable(paramBoolean)) && ((getTargetNode() != null) || ((!paramBoolean) && (this.cachedNode != null)));
/*     */   }
/*     */ 
/*     */   void impl_sync(boolean paramBoolean)
/*     */   {
/* 505 */     super.impl_sync(paramBoolean);
/* 506 */     if ((paramBoolean) || (this.cachedNode == null)) {
/* 507 */       this.cachedNode = getTargetNode();
/*     */ 
/* 509 */       double d1 = getFromX();
/* 510 */       double d2 = getFromY();
/* 511 */       double d3 = getFromZ();
/*     */ 
/* 513 */       double d4 = getToX();
/* 514 */       double d5 = getToY();
/* 515 */       double d6 = getToZ();
/*     */ 
/* 517 */       double d7 = getByX();
/* 518 */       double d8 = getByY();
/* 519 */       double d9 = getByZ();
/*     */ 
/* 521 */       if ((Double.isNaN(d1)) && (Double.isNaN(d4)) && (Math.abs(d7) < 1.0E-12D)) {
/* 522 */         this.startX = (0.0D / 0.0D);
/*     */       } else {
/* 524 */         this.startX = (!Double.isNaN(d1) ? d1 : this.cachedNode.getScaleX());
/* 525 */         this.deltaX = (!Double.isNaN(d4) ? d4 - this.startX : getByX());
/*     */       }
/*     */ 
/* 528 */       if ((Double.isNaN(d2)) && (Double.isNaN(d5)) && (Math.abs(d8) < 1.0E-12D)) {
/* 529 */         this.startY = (0.0D / 0.0D);
/*     */       } else {
/* 531 */         this.startY = (!Double.isNaN(d2) ? d2 : this.cachedNode.getScaleY());
/* 532 */         this.deltaY = (!Double.isNaN(d5) ? d5 - this.startY : getByY());
/*     */       }
/*     */ 
/* 535 */       if ((Double.isNaN(d3)) && (Double.isNaN(d6)) && (Math.abs(d9) < 1.0E-12D)) {
/* 536 */         this.startZ = (0.0D / 0.0D);
/*     */       } else {
/* 538 */         this.startZ = (!Double.isNaN(d3) ? d3 : this.cachedNode.getScaleZ());
/* 539 */         this.deltaZ = (!Double.isNaN(d6) ? d6 - this.startZ : getByZ());
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.animation.ScaleTransition
 * JD-Core Version:    0.6.2
 */