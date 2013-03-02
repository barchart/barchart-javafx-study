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
/*     */ public final class FadeTransition extends Transition
/*     */ {
/*     */   private static final double EPSILON = 1.0E-12D;
/*     */   private double start;
/*     */   private double delta;
/*     */   private ObjectProperty<Node> node;
/*  98 */   private static final Node DEFAULT_NODE = null;
/*     */   private Node cachedNode;
/*     */   private ObjectProperty<Duration> duration;
/* 135 */   private static final Duration DEFAULT_DURATION = Duration.millis(400.0D);
/*     */   private DoubleProperty fromValue;
/*     */   private static final double DEFAULT_FROM_VALUE = (0.0D / 0.0D);
/*     */   private DoubleProperty toValue;
/*     */   private static final double DEFAULT_TO_VALUE = (0.0D / 0.0D);
/*     */   private DoubleProperty byValue;
/*     */   private static final double DEFAULT_BY_VALUE = 0.0D;
/*     */ 
/*     */   public final void setNode(Node paramNode)
/*     */   {
/* 101 */     if ((this.node != null) || (paramNode != null))
/* 102 */       nodeProperty().set(paramNode);
/*     */   }
/*     */ 
/*     */   public final Node getNode()
/*     */   {
/* 107 */     return this.node == null ? DEFAULT_NODE : (Node)this.node.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<Node> nodeProperty() {
/* 111 */     if (this.node == null) {
/* 112 */       this.node = new SimpleObjectProperty(this, "node", DEFAULT_NODE);
/*     */     }
/* 114 */     return this.node;
/*     */   }
/*     */ 
/*     */   public final void setDuration(Duration paramDuration)
/*     */   {
/* 138 */     if ((this.duration != null) || (!DEFAULT_DURATION.equals(paramDuration)))
/* 139 */       durationProperty().set(paramDuration);
/*     */   }
/*     */ 
/*     */   public final Duration getDuration()
/*     */   {
/* 144 */     return this.duration == null ? DEFAULT_DURATION : (Duration)this.duration.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<Duration> durationProperty() {
/* 148 */     if (this.duration == null) {
/* 149 */       this.duration = new ObjectPropertyBase(DEFAULT_DURATION)
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 153 */           FadeTransition.this.setCycleDuration(FadeTransition.this.getDuration());
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 158 */           return FadeTransition.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 163 */           return "duration";
/*     */         }
/*     */       };
/*     */     }
/* 167 */     return this.duration;
/*     */   }
/*     */ 
/*     */   public final void setFromValue(double paramDouble)
/*     */   {
/* 184 */     if ((this.fromValue != null) || (!Double.isNaN(paramDouble)))
/* 185 */       fromValueProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getFromValue()
/*     */   {
/* 190 */     return this.fromValue == null ? (0.0D / 0.0D) : this.fromValue.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty fromValueProperty() {
/* 194 */     if (this.fromValue == null) {
/* 195 */       this.fromValue = new SimpleDoubleProperty(this, "fromValue", (0.0D / 0.0D));
/*     */     }
/* 197 */     return this.fromValue;
/*     */   }
/*     */ 
/*     */   public final void setToValue(double paramDouble)
/*     */   {
/* 214 */     if ((this.toValue != null) || (!Double.isNaN(paramDouble)))
/* 215 */       toValueProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getToValue()
/*     */   {
/* 220 */     return this.toValue == null ? (0.0D / 0.0D) : this.toValue.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty toValueProperty() {
/* 224 */     if (this.toValue == null) {
/* 225 */       this.toValue = new SimpleDoubleProperty(this, "toValue", (0.0D / 0.0D));
/*     */     }
/* 227 */     return this.toValue;
/*     */   }
/*     */ 
/*     */   public final void setByValue(double paramDouble)
/*     */   {
/* 243 */     if ((this.byValue != null) || (Math.abs(paramDouble - 0.0D) > 1.0E-12D))
/* 244 */       byValueProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getByValue()
/*     */   {
/* 249 */     return this.byValue == null ? 0.0D : this.byValue.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty byValueProperty() {
/* 253 */     if (this.byValue == null) {
/* 254 */       this.byValue = new SimpleDoubleProperty(this, "byValue", 0.0D);
/*     */     }
/* 256 */     return this.byValue;
/*     */   }
/*     */ 
/*     */   public FadeTransition(Duration paramDuration, Node paramNode)
/*     */   {
/* 268 */     setDuration(paramDuration);
/* 269 */     setNode(paramNode);
/* 270 */     setCycleDuration(paramDuration);
/*     */   }
/*     */ 
/*     */   public FadeTransition(Duration paramDuration)
/*     */   {
/* 280 */     this(paramDuration, null);
/*     */   }
/*     */ 
/*     */   public FadeTransition()
/*     */   {
/* 287 */     this(DEFAULT_DURATION, null);
/*     */   }
/*     */ 
/*     */   protected void interpolate(double paramDouble)
/*     */   {
/* 295 */     double d = Math.max(0.0D, Math.min(this.start + paramDouble * this.delta, 1.0D));
/*     */ 
/* 297 */     this.cachedNode.setOpacity(d);
/*     */   }
/*     */ 
/*     */   private Node getTargetNode() {
/* 301 */     Node localNode = getNode();
/* 302 */     return localNode != null ? localNode : getParentTargetNode();
/*     */   }
/*     */ 
/*     */   boolean impl_startable(boolean paramBoolean)
/*     */   {
/* 307 */     return (super.impl_startable(paramBoolean)) && ((getTargetNode() != null) || ((!paramBoolean) && (this.cachedNode != null)));
/*     */   }
/*     */ 
/*     */   void impl_sync(boolean paramBoolean)
/*     */   {
/* 313 */     super.impl_sync(paramBoolean);
/* 314 */     if ((paramBoolean) || (this.cachedNode == null)) {
/* 315 */       this.cachedNode = getTargetNode();
/* 316 */       double d1 = getFromValue();
/* 317 */       double d2 = getToValue();
/* 318 */       this.start = (!Double.isNaN(d1) ? Math.max(0.0D, Math.min(d1, 1.0D)) : this.cachedNode.getOpacity());
/*     */ 
/* 320 */       this.delta = (!Double.isNaN(d2) ? d2 - this.start : getByValue());
/* 321 */       if (this.start + this.delta > 1.0D)
/* 322 */         this.delta = (1.0D - this.start);
/* 323 */       else if (this.start + this.delta < 0.0D)
/* 324 */         this.delta = (-this.start);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.animation.FadeTransition
 * JD-Core Version:    0.6.2
 */