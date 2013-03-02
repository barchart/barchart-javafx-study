/*     */ package javafx.animation;
/*     */ 
/*     */ import javafx.beans.property.DoubleProperty;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.ObjectPropertyBase;
/*     */ import javafx.beans.property.SimpleDoubleProperty;
/*     */ import javafx.beans.property.SimpleObjectProperty;
/*     */ import javafx.geometry.Point3D;
/*     */ import javafx.scene.Node;
/*     */ import javafx.util.Duration;
/*     */ 
/*     */ public final class RotateTransition extends Transition
/*     */ {
/*     */   private static final double EPSILON = 1.0E-12D;
/*     */   private double start;
/*     */   private double delta;
/*     */   private ObjectProperty<Node> node;
/*  99 */   private static final Node DEFAULT_NODE = null;
/*     */   private Node cachedNode;
/*     */   private ObjectProperty<Duration> duration;
/* 136 */   private static final Duration DEFAULT_DURATION = Duration.millis(400.0D);
/*     */   private ObjectProperty<Point3D> axis;
/* 184 */   private static final Point3D DEFAULT_AXIS = null;
/*     */   private DoubleProperty fromAngle;
/*     */   private static final double DEFAULT_FROM_ANGLE = (0.0D / 0.0D);
/*     */   private DoubleProperty toAngle;
/*     */   private static final double DEFAULT_TO_ANGLE = (0.0D / 0.0D);
/*     */   private DoubleProperty byAngle;
/*     */   private static final double DEFAULT_BY_ANGLE = 0.0D;
/*     */ 
/*     */   public final void setNode(Node paramNode)
/*     */   {
/* 102 */     if ((this.node != null) || (paramNode != null))
/* 103 */       nodeProperty().set(paramNode);
/*     */   }
/*     */ 
/*     */   public final Node getNode()
/*     */   {
/* 108 */     return this.node == null ? DEFAULT_NODE : (Node)this.node.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<Node> nodeProperty() {
/* 112 */     if (this.node == null) {
/* 113 */       this.node = new SimpleObjectProperty(this, "node", DEFAULT_NODE);
/*     */     }
/* 115 */     return this.node;
/*     */   }
/*     */ 
/*     */   public final void setDuration(Duration paramDuration)
/*     */   {
/* 139 */     if ((this.duration != null) || (!DEFAULT_DURATION.equals(paramDuration)))
/* 140 */       durationProperty().set(paramDuration);
/*     */   }
/*     */ 
/*     */   public final Duration getDuration()
/*     */   {
/* 145 */     return this.duration == null ? DEFAULT_DURATION : (Duration)this.duration.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<Duration> durationProperty() {
/* 149 */     if (this.duration == null) {
/* 150 */       this.duration = new ObjectPropertyBase(DEFAULT_DURATION)
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 154 */           RotateTransition.this.setCycleDuration(RotateTransition.this.getDuration());
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 159 */           return RotateTransition.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 164 */           return "duration";
/*     */         }
/*     */       };
/*     */     }
/* 168 */     return this.duration;
/*     */   }
/*     */ 
/*     */   public final void setAxis(Point3D paramPoint3D)
/*     */   {
/* 187 */     if ((this.axis != null) || (paramPoint3D != null))
/* 188 */       axisProperty().set(paramPoint3D);
/*     */   }
/*     */ 
/*     */   public final Point3D getAxis()
/*     */   {
/* 193 */     return this.axis == null ? DEFAULT_AXIS : (Point3D)this.axis.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<Point3D> axisProperty() {
/* 197 */     if (this.axis == null) {
/* 198 */       this.axis = new SimpleObjectProperty(this, "axis", DEFAULT_AXIS);
/*     */     }
/* 200 */     return this.axis;
/*     */   }
/*     */ 
/*     */   public final void setFromAngle(double paramDouble)
/*     */   {
/* 217 */     if ((this.fromAngle != null) || (!Double.isNaN(paramDouble)))
/* 218 */       fromAngleProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getFromAngle()
/*     */   {
/* 223 */     return this.fromAngle == null ? (0.0D / 0.0D) : this.fromAngle.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty fromAngleProperty() {
/* 227 */     if (this.fromAngle == null) {
/* 228 */       this.fromAngle = new SimpleDoubleProperty(this, "fromAngle", (0.0D / 0.0D));
/*     */     }
/* 230 */     return this.fromAngle;
/*     */   }
/*     */ 
/*     */   public final void setToAngle(double paramDouble)
/*     */   {
/* 247 */     if ((this.toAngle != null) || (!Double.isNaN(paramDouble)))
/* 248 */       toAngleProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getToAngle()
/*     */   {
/* 253 */     return this.toAngle == null ? (0.0D / 0.0D) : this.toAngle.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty toAngleProperty() {
/* 257 */     if (this.toAngle == null) {
/* 258 */       this.toAngle = new SimpleDoubleProperty(this, "toAngle", (0.0D / 0.0D));
/*     */     }
/* 260 */     return this.toAngle;
/*     */   }
/*     */ 
/*     */   public final void setByAngle(double paramDouble)
/*     */   {
/* 276 */     if ((this.byAngle != null) || (Math.abs(paramDouble - 0.0D) > 1.0E-12D))
/* 277 */       byAngleProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getByAngle()
/*     */   {
/* 282 */     return this.byAngle == null ? 0.0D : this.byAngle.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty byAngleProperty() {
/* 286 */     if (this.byAngle == null) {
/* 287 */       this.byAngle = new SimpleDoubleProperty(this, "byAngle", 0.0D);
/*     */     }
/* 289 */     return this.byAngle;
/*     */   }
/*     */ 
/*     */   public RotateTransition(Duration paramDuration, Node paramNode)
/*     */   {
/* 301 */     setDuration(paramDuration);
/* 302 */     setNode(paramNode);
/* 303 */     setCycleDuration(paramDuration);
/*     */   }
/*     */ 
/*     */   public RotateTransition(Duration paramDuration)
/*     */   {
/* 313 */     this(paramDuration, null);
/*     */   }
/*     */ 
/*     */   public RotateTransition()
/*     */   {
/* 321 */     this(DEFAULT_DURATION, null);
/*     */   }
/*     */ 
/*     */   protected void interpolate(double paramDouble)
/*     */   {
/* 329 */     this.cachedNode.setRotate(this.start + paramDouble * this.delta);
/*     */   }
/*     */ 
/*     */   private Node getTargetNode() {
/* 333 */     Node localNode = getNode();
/* 334 */     return localNode != null ? localNode : getParentTargetNode();
/*     */   }
/*     */ 
/*     */   boolean impl_startable(boolean paramBoolean)
/*     */   {
/* 339 */     return (super.impl_startable(paramBoolean)) && ((getTargetNode() != null) || ((!paramBoolean) && (this.cachedNode != null)));
/*     */   }
/*     */ 
/*     */   void impl_sync(boolean paramBoolean)
/*     */   {
/* 345 */     super.impl_sync(paramBoolean);
/* 346 */     if ((paramBoolean) || (this.cachedNode == null)) {
/* 347 */       this.cachedNode = getTargetNode();
/* 348 */       double d1 = getFromAngle();
/* 349 */       double d2 = getToAngle();
/* 350 */       this.start = (!Double.isNaN(d1) ? d1 : this.cachedNode.getRotate());
/*     */ 
/* 352 */       this.delta = (!Double.isNaN(d2) ? d2 - this.start : getByAngle());
/* 353 */       Point3D localPoint3D = getAxis();
/* 354 */       if (localPoint3D != null)
/* 355 */         ((Node)this.node.get()).setRotationAxis(localPoint3D);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.animation.RotateTransition
 * JD-Core Version:    0.6.2
 */