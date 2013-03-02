/*     */ package javafx.animation;
/*     */ 
/*     */ import com.sun.javafx.animation.transition.AnimationPathHelper;
/*     */ import com.sun.javafx.animation.transition.Position2D;
/*     */ import com.sun.javafx.geom.Path2D;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.ObjectPropertyBase;
/*     */ import javafx.beans.property.SimpleObjectProperty;
/*     */ import javafx.geometry.Bounds;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.shape.Shape;
/*     */ import javafx.util.Duration;
/*     */ 
/*     */ public final class PathTransition extends Transition
/*     */ {
/*     */   private ObjectProperty<Node> node;
/*  99 */   private static final Node DEFAULT_NODE = null;
/*     */   private Node cachedNode;
/*     */   private ObjectProperty<Duration> duration;
/* 136 */   private static final Duration DEFAULT_DURATION = Duration.millis(400.0D);
/*     */   private ObjectProperty<Shape> path;
/* 182 */   private static final Shape DEFAULT_PATH = null;
/*     */   private ObjectProperty<OrientationType> orientation;
/* 231 */   private static final OrientationType DEFAULT_ORIENTATION = OrientationType.NONE;
/*     */   private boolean cachedIsNormalRequired;
/* 252 */   private final Position2D posResult = new Position2D();
/*     */   private AnimationPathHelper apHelper;
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
/* 154 */           PathTransition.this.setCycleDuration(PathTransition.this.getDuration());
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 159 */           return PathTransition.this;
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
/*     */   public final void setPath(Shape paramShape)
/*     */   {
/* 185 */     if ((this.path != null) || (paramShape != null))
/* 186 */       pathProperty().set(paramShape);
/*     */   }
/*     */ 
/*     */   public final Shape getPath()
/*     */   {
/* 191 */     return this.path == null ? DEFAULT_PATH : (Shape)this.path.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<Shape> pathProperty() {
/* 195 */     if (this.path == null) {
/* 196 */       this.path = new SimpleObjectProperty(this, "path", DEFAULT_PATH);
/*     */     }
/* 198 */     return this.path;
/*     */   }
/*     */ 
/*     */   public final void setOrientation(OrientationType paramOrientationType)
/*     */   {
/* 234 */     if ((this.orientation != null) || (!DEFAULT_ORIENTATION.equals(paramOrientationType)))
/* 235 */       orientationProperty().set(paramOrientationType);
/*     */   }
/*     */ 
/*     */   public final OrientationType getOrientation()
/*     */   {
/* 240 */     return this.orientation == null ? OrientationType.NONE : (OrientationType)this.orientation.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<OrientationType> orientationProperty() {
/* 244 */     if (this.orientation == null) {
/* 245 */       this.orientation = new SimpleObjectProperty(this, "orientation", DEFAULT_ORIENTATION);
/*     */     }
/* 247 */     return this.orientation;
/*     */   }
/*     */ 
/*     */   public PathTransition(Duration paramDuration, Shape paramShape, Node paramNode)
/*     */   {
/* 266 */     setDuration(paramDuration);
/* 267 */     setPath(paramShape);
/* 268 */     setNode(paramNode);
/* 269 */     setCycleDuration(paramDuration);
/*     */   }
/*     */ 
/*     */   public PathTransition(Duration paramDuration, Shape paramShape)
/*     */   {
/* 281 */     this(paramDuration, paramShape, null);
/*     */   }
/*     */ 
/*     */   public PathTransition()
/*     */   {
/* 288 */     this(DEFAULT_DURATION, null, null);
/*     */   }
/*     */ 
/*     */   public void interpolate(double paramDouble)
/*     */   {
/* 296 */     this.apHelper.getPosition2D(paramDouble, this.cachedIsNormalRequired, this.posResult);
/* 297 */     this.cachedNode.setTranslateX(this.posResult.x - this.cachedNode.impl_getPivotX());
/* 298 */     this.cachedNode.setTranslateY(this.posResult.y - this.cachedNode.impl_getPivotY());
/*     */ 
/* 300 */     if (this.cachedIsNormalRequired)
/* 301 */       this.cachedNode.setRotate(this.posResult.rotateAngle);
/*     */   }
/*     */ 
/*     */   private Node getTargetNode()
/*     */   {
/* 306 */     Node localNode = getNode();
/* 307 */     return localNode != null ? localNode : getParentTargetNode();
/*     */   }
/*     */ 
/*     */   boolean impl_startable(boolean paramBoolean)
/*     */   {
/* 312 */     return (super.impl_startable(paramBoolean)) && (((getTargetNode() != null) && (getPath() != null) && (!getPath().getLayoutBounds().isEmpty())) || ((!paramBoolean) && (this.cachedNode != null) && (this.apHelper != null)));
/*     */   }
/*     */ 
/*     */   void impl_sync(boolean paramBoolean)
/*     */   {
/* 319 */     super.impl_sync(paramBoolean);
/* 320 */     if ((paramBoolean) || (this.cachedNode == null)) {
/* 321 */       this.cachedNode = getTargetNode();
/* 322 */       Shape localShape = getPath();
/* 323 */       Path2D localPath2D = new Path2D(localShape.impl_configShape());
/* 324 */       BaseTransform localBaseTransform = localShape.impl_getLeafTransform();
/* 325 */       this.apHelper = new AnimationPathHelper(localPath2D, localBaseTransform, 1.0D);
/* 326 */       this.cachedIsNormalRequired = (getOrientation() == OrientationType.ORTHOGONAL_TO_TANGENT);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static enum OrientationType
/*     */   {
/* 210 */     NONE, 
/*     */ 
/* 216 */     ORTHOGONAL_TO_TANGENT;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.animation.PathTransition
 * JD-Core Version:    0.6.2
 */