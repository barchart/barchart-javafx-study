/*     */ package javafx.animation;
/*     */ 
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.ObjectPropertyBase;
/*     */ import javafx.beans.property.SimpleObjectProperty;
/*     */ import javafx.scene.Node;
/*     */ import javafx.scene.paint.Color;
/*     */ import javafx.scene.shape.Shape;
/*     */ import javafx.util.Duration;
/*     */ 
/*     */ public final class StrokeTransition extends Transition
/*     */ {
/*     */   private Color start;
/*     */   private Color end;
/*     */   private ObjectProperty<Shape> shape;
/*  91 */   private static final Shape DEFAULT_SHAPE = null;
/*     */   private Shape cachedShape;
/*     */   private ObjectProperty<Duration> duration;
/* 128 */   private static final Duration DEFAULT_DURATION = Duration.millis(400.0D);
/*     */   private ObjectProperty<Color> fromValue;
/* 174 */   private static final Color DEFAULT_FROM_VALUE = null;
/*     */   private ObjectProperty<Color> toValue;
/* 204 */   private static final Color DEFAULT_TO_VALUE = null;
/*     */ 
/*     */   public final void setShape(Shape paramShape)
/*     */   {
/*  94 */     if ((this.shape != null) || (paramShape != null))
/*  95 */       shapeProperty().set(paramShape);
/*     */   }
/*     */ 
/*     */   public final Shape getShape()
/*     */   {
/* 100 */     return this.shape == null ? DEFAULT_SHAPE : (Shape)this.shape.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<Shape> shapeProperty() {
/* 104 */     if (this.shape == null) {
/* 105 */       this.shape = new SimpleObjectProperty(this, "shape", DEFAULT_SHAPE);
/*     */     }
/* 107 */     return this.shape;
/*     */   }
/*     */ 
/*     */   public final void setDuration(Duration paramDuration)
/*     */   {
/* 131 */     if ((this.duration != null) || (!DEFAULT_DURATION.equals(paramDuration)))
/* 132 */       durationProperty().set(paramDuration);
/*     */   }
/*     */ 
/*     */   public final Duration getDuration()
/*     */   {
/* 137 */     return this.duration == null ? DEFAULT_DURATION : (Duration)this.duration.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<Duration> durationProperty() {
/* 141 */     if (this.duration == null) {
/* 142 */       this.duration = new ObjectPropertyBase(DEFAULT_DURATION)
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 146 */           StrokeTransition.this.setCycleDuration(StrokeTransition.this.getDuration());
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 151 */           return StrokeTransition.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 156 */           return "duration";
/*     */         }
/*     */       };
/*     */     }
/* 160 */     return this.duration;
/*     */   }
/*     */ 
/*     */   public final void setFromValue(Color paramColor)
/*     */   {
/* 177 */     if ((this.fromValue != null) || (paramColor != null))
/* 178 */       fromValueProperty().set(paramColor);
/*     */   }
/*     */ 
/*     */   public final Color getFromValue()
/*     */   {
/* 183 */     return this.fromValue == null ? DEFAULT_FROM_VALUE : (Color)this.fromValue.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<Color> fromValueProperty() {
/* 187 */     if (this.fromValue == null) {
/* 188 */       this.fromValue = new SimpleObjectProperty(this, "fromValue", DEFAULT_FROM_VALUE);
/*     */     }
/* 190 */     return this.fromValue;
/*     */   }
/*     */ 
/*     */   public final void setToValue(Color paramColor)
/*     */   {
/* 207 */     if ((this.toValue != null) || (paramColor != null))
/* 208 */       toValueProperty().set(paramColor);
/*     */   }
/*     */ 
/*     */   public final Color getToValue()
/*     */   {
/* 213 */     return this.toValue == null ? DEFAULT_TO_VALUE : (Color)this.toValue.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<Color> toValueProperty() {
/* 217 */     if (this.toValue == null) {
/* 218 */       this.toValue = new SimpleObjectProperty(this, "toValue", DEFAULT_TO_VALUE);
/*     */     }
/* 220 */     return this.toValue;
/*     */   }
/*     */ 
/*     */   public StrokeTransition(Duration paramDuration, Shape paramShape, Color paramColor1, Color paramColor2)
/*     */   {
/* 232 */     setDuration(paramDuration);
/* 233 */     setShape(paramShape);
/* 234 */     setFromValue(paramColor1);
/* 235 */     setToValue(paramColor2);
/* 236 */     setCycleDuration(paramDuration);
/*     */   }
/*     */ 
/*     */   public StrokeTransition(Duration paramDuration, Color paramColor1, Color paramColor2)
/*     */   {
/* 246 */     this(paramDuration, null, paramColor1, paramColor2);
/*     */   }
/*     */ 
/*     */   public StrokeTransition(Duration paramDuration, Shape paramShape)
/*     */   {
/* 258 */     this(paramDuration, paramShape, null, null);
/*     */   }
/*     */ 
/*     */   public StrokeTransition(Duration paramDuration)
/*     */   {
/* 268 */     this(paramDuration, null);
/*     */   }
/*     */ 
/*     */   public StrokeTransition()
/*     */   {
/* 275 */     this(DEFAULT_DURATION, null);
/*     */   }
/*     */ 
/*     */   protected void interpolate(double paramDouble)
/*     */   {
/* 283 */     Color localColor = this.start.interpolate(this.end, paramDouble);
/* 284 */     this.cachedShape.setStroke(localColor);
/*     */   }
/*     */ 
/*     */   private Shape getTargetShape() {
/* 288 */     Shape localShape = getShape();
/* 289 */     if (localShape == null) {
/* 290 */       Node localNode = getParentTargetNode();
/* 291 */       if ((localNode instanceof Shape)) {
/* 292 */         localShape = (Shape)localNode;
/*     */       }
/*     */     }
/* 295 */     return localShape;
/*     */   }
/*     */ 
/*     */   boolean impl_startable(boolean paramBoolean)
/*     */   {
/* 300 */     if (!super.impl_startable(paramBoolean)) {
/* 301 */       return false;
/*     */     }
/*     */ 
/* 304 */     if ((!paramBoolean) && (this.cachedShape != null)) {
/* 305 */       return true;
/*     */     }
/*     */ 
/* 309 */     Shape localShape = getTargetShape();
/* 310 */     return (localShape != null) && ((getFromValue() != null) || ((localShape.getStroke() instanceof Color))) && (getToValue() != null);
/*     */   }
/*     */ 
/*     */   void impl_sync(boolean paramBoolean)
/*     */   {
/* 324 */     super.impl_sync(paramBoolean);
/* 325 */     if ((paramBoolean) || (this.cachedShape == null)) {
/* 326 */       this.cachedShape = getTargetShape();
/* 327 */       Color localColor = getFromValue();
/* 328 */       this.start = (localColor != null ? localColor : (Color)this.cachedShape.getStroke());
/*     */ 
/* 330 */       this.end = getToValue();
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.animation.StrokeTransition
 * JD-Core Version:    0.6.2
 */