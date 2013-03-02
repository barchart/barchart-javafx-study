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
/*     */ public final class FillTransition extends Transition
/*     */ {
/*     */   private Color start;
/*     */   private Color end;
/*     */   private ObjectProperty<Shape> shape;
/*  90 */   private static final Shape DEFAULT_SHAPE = null;
/*     */   private Shape cachedShape;
/*     */   private ObjectProperty<Duration> duration;
/* 127 */   private static final Duration DEFAULT_DURATION = Duration.millis(400.0D);
/*     */   private ObjectProperty<Color> fromValue;
/* 173 */   private static final Color DEFAULT_FROM_VALUE = null;
/*     */   private ObjectProperty<Color> toValue;
/* 203 */   private static final Color DEFAULT_TO_VALUE = null;
/*     */ 
/*     */   public final void setShape(Shape paramShape)
/*     */   {
/*  93 */     if ((this.shape != null) || (paramShape != null))
/*  94 */       shapeProperty().set(paramShape);
/*     */   }
/*     */ 
/*     */   public final Shape getShape()
/*     */   {
/*  99 */     return this.shape == null ? DEFAULT_SHAPE : (Shape)this.shape.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<Shape> shapeProperty() {
/* 103 */     if (this.shape == null) {
/* 104 */       this.shape = new SimpleObjectProperty(this, "shape", DEFAULT_SHAPE);
/*     */     }
/* 106 */     return this.shape;
/*     */   }
/*     */ 
/*     */   public final void setDuration(Duration paramDuration)
/*     */   {
/* 130 */     if ((this.duration != null) || (!DEFAULT_DURATION.equals(paramDuration)))
/* 131 */       durationProperty().set(paramDuration);
/*     */   }
/*     */ 
/*     */   public final Duration getDuration()
/*     */   {
/* 136 */     return this.duration == null ? DEFAULT_DURATION : (Duration)this.duration.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<Duration> durationProperty() {
/* 140 */     if (this.duration == null) {
/* 141 */       this.duration = new ObjectPropertyBase(DEFAULT_DURATION)
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 145 */           FillTransition.this.setCycleDuration(FillTransition.this.getDuration());
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 150 */           return FillTransition.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 155 */           return "duration";
/*     */         }
/*     */       };
/*     */     }
/* 159 */     return this.duration;
/*     */   }
/*     */ 
/*     */   public final void setFromValue(Color paramColor)
/*     */   {
/* 176 */     if ((this.fromValue != null) || (paramColor != null))
/* 177 */       fromValueProperty().set(paramColor);
/*     */   }
/*     */ 
/*     */   public final Color getFromValue()
/*     */   {
/* 182 */     return this.fromValue == null ? DEFAULT_FROM_VALUE : (Color)this.fromValue.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<Color> fromValueProperty() {
/* 186 */     if (this.fromValue == null) {
/* 187 */       this.fromValue = new SimpleObjectProperty(this, "fromValue", DEFAULT_FROM_VALUE);
/*     */     }
/* 189 */     return this.fromValue;
/*     */   }
/*     */ 
/*     */   public final void setToValue(Color paramColor)
/*     */   {
/* 206 */     if ((this.toValue != null) || (paramColor != null))
/* 207 */       toValueProperty().set(paramColor);
/*     */   }
/*     */ 
/*     */   public final Color getToValue()
/*     */   {
/* 212 */     return this.toValue == null ? DEFAULT_TO_VALUE : (Color)this.toValue.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<Color> toValueProperty() {
/* 216 */     if (this.toValue == null) {
/* 217 */       this.toValue = new SimpleObjectProperty(this, "toValue", DEFAULT_TO_VALUE);
/*     */     }
/* 219 */     return this.toValue;
/*     */   }
/*     */ 
/*     */   public FillTransition(Duration paramDuration, Shape paramShape, Color paramColor1, Color paramColor2)
/*     */   {
/* 231 */     setDuration(paramDuration);
/* 232 */     setShape(paramShape);
/* 233 */     setFromValue(paramColor1);
/* 234 */     setToValue(paramColor2);
/* 235 */     setCycleDuration(paramDuration);
/*     */   }
/*     */ 
/*     */   public FillTransition(Duration paramDuration, Color paramColor1, Color paramColor2)
/*     */   {
/* 245 */     this(paramDuration, null, paramColor1, paramColor2);
/*     */   }
/*     */ 
/*     */   public FillTransition(Duration paramDuration, Shape paramShape)
/*     */   {
/* 257 */     this(paramDuration, paramShape, null, null);
/*     */   }
/*     */ 
/*     */   public FillTransition(Duration paramDuration)
/*     */   {
/* 267 */     this(paramDuration, null, null, null);
/*     */   }
/*     */ 
/*     */   public FillTransition()
/*     */   {
/* 274 */     this(DEFAULT_DURATION, null);
/*     */   }
/*     */ 
/*     */   protected void interpolate(double paramDouble)
/*     */   {
/* 282 */     Color localColor = this.start.interpolate(this.end, paramDouble);
/* 283 */     this.cachedShape.setFill(localColor);
/*     */   }
/*     */ 
/*     */   private Shape getTargetShape() {
/* 287 */     Shape localShape = getShape();
/* 288 */     if (localShape == null) {
/* 289 */       Node localNode = getParentTargetNode();
/* 290 */       if ((localNode instanceof Shape)) {
/* 291 */         localShape = (Shape)localNode;
/*     */       }
/*     */     }
/* 294 */     return localShape;
/*     */   }
/*     */ 
/*     */   boolean impl_startable(boolean paramBoolean)
/*     */   {
/* 299 */     if (!super.impl_startable(paramBoolean)) {
/* 300 */       return false;
/*     */     }
/*     */ 
/* 303 */     if ((!paramBoolean) && (this.cachedShape != null)) {
/* 304 */       return true;
/*     */     }
/*     */ 
/* 308 */     Shape localShape = getTargetShape();
/* 309 */     return (localShape != null) && ((getFromValue() != null) || ((localShape.getFill() instanceof Color))) && (getToValue() != null);
/*     */   }
/*     */ 
/*     */   void impl_sync(boolean paramBoolean)
/*     */   {
/* 322 */     super.impl_sync(paramBoolean);
/* 323 */     if ((paramBoolean) || (this.cachedShape == null)) {
/* 324 */       this.cachedShape = getTargetShape();
/* 325 */       Color localColor = getFromValue();
/* 326 */       this.start = (localColor != null ? localColor : (Color)this.cachedShape.getFill());
/*     */ 
/* 328 */       this.end = getToValue();
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.animation.FillTransition
 * JD-Core Version:    0.6.2
 */