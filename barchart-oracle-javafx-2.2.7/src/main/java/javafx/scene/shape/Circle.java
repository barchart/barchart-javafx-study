/*     */ package javafx.scene.shape;
/*     */ 
/*     */ import com.sun.javafx.geom.BaseBounds;
/*     */ import com.sun.javafx.geom.Ellipse2D;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.javafx.scene.DirtyBits;
/*     */ import com.sun.javafx.sg.PGCircle;
/*     */ import com.sun.javafx.sg.PGNode;
/*     */ import com.sun.javafx.sg.PGShape.Mode;
/*     */ import com.sun.javafx.sg.PGShape.StrokeLineJoin;
/*     */ import com.sun.javafx.tk.Toolkit;
/*     */ import javafx.beans.property.DoubleProperty;
/*     */ import javafx.beans.property.DoublePropertyBase;
/*     */ import javafx.scene.paint.Paint;
/*     */ 
/*     */ public class Circle extends Shape
/*     */ {
/*  64 */   private final Ellipse2D shape = new Ellipse2D();
/*     */   private static final int NON_RECTILINEAR_TYPE_MASK = -72;
/*     */   private DoubleProperty centerX;
/*     */   private DoubleProperty centerY;
/* 212 */   private DoubleProperty radius = new DoublePropertyBase()
/*     */   {
/*     */     public void invalidated()
/*     */     {
/* 216 */       Circle.this.impl_markDirty(DirtyBits.NODE_GEOMETRY);
/* 217 */       Circle.this.impl_geomChanged();
/*     */     }
/*     */ 
/*     */     public Object getBean()
/*     */     {
/* 222 */       return Circle.this;
/*     */     }
/*     */ 
/*     */     public String getName()
/*     */     {
/* 227 */       return "radius";
/*     */     }
/* 212 */   };
/*     */ 
/*     */   public Circle(double paramDouble)
/*     */   {
/*  76 */     setRadius(paramDouble);
/*     */   }
/*     */ 
/*     */   public Circle(double paramDouble, Paint paramPaint)
/*     */   {
/*  85 */     setRadius(paramDouble);
/*  86 */     setFill(paramPaint);
/*     */   }
/*     */ 
/*     */   public Circle()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Circle(double paramDouble1, double paramDouble2, double paramDouble3)
/*     */   {
/* 102 */     setCenterX(paramDouble1);
/* 103 */     setCenterY(paramDouble2);
/* 104 */     setRadius(paramDouble3);
/*     */   }
/*     */ 
/*     */   public Circle(double paramDouble1, double paramDouble2, double paramDouble3, Paint paramPaint)
/*     */   {
/* 115 */     setCenterX(paramDouble1);
/* 116 */     setCenterY(paramDouble2);
/* 117 */     setRadius(paramDouble3);
/* 118 */     setFill(paramPaint);
/*     */   }
/*     */ 
/*     */   public final void setCenterX(double paramDouble)
/*     */   {
/* 131 */     if ((this.centerX != null) || (paramDouble != 0.0D))
/* 132 */       centerXProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getCenterX()
/*     */   {
/* 137 */     return this.centerX == null ? 0.0D : this.centerX.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty centerXProperty() {
/* 141 */     if (this.centerX == null) {
/* 142 */       this.centerX = new DoublePropertyBase(0.0D)
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 146 */           Circle.this.impl_markDirty(DirtyBits.NODE_GEOMETRY);
/* 147 */           Circle.this.impl_geomChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 152 */           return Circle.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 157 */           return "centerX";
/*     */         }
/*     */       };
/*     */     }
/* 161 */     return this.centerX;
/*     */   }
/*     */ 
/*     */   public final void setCenterY(double paramDouble)
/*     */   {
/* 174 */     if ((this.centerY != null) || (paramDouble != 0.0D))
/* 175 */       centerYProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getCenterY()
/*     */   {
/* 180 */     return this.centerY == null ? 0.0D : this.centerY.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty centerYProperty() {
/* 184 */     if (this.centerY == null) {
/* 185 */       this.centerY = new DoublePropertyBase(0.0D)
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 189 */           Circle.this.impl_markDirty(DirtyBits.NODE_GEOMETRY);
/* 190 */           Circle.this.impl_geomChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 195 */           return Circle.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 200 */           return "centerY";
/*     */         }
/*     */       };
/*     */     }
/* 204 */     return this.centerY;
/*     */   }
/*     */ 
/*     */   public final void setRadius(double paramDouble)
/*     */   {
/* 232 */     this.radius.set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getRadius() {
/* 236 */     return this.radius.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty radiusProperty() {
/* 240 */     return this.radius;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   protected PGNode impl_createPGNode()
/*     */   {
/* 249 */     return Toolkit.getToolkit().createPGCircle();
/*     */   }
/*     */ 
/*     */   PGCircle getPGCircle() {
/* 253 */     return (PGCircle)impl_getPGNode();
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   protected PGShape.StrokeLineJoin toPGLineJoin(StrokeLineJoin paramStrokeLineJoin)
/*     */   {
/* 276 */     return PGShape.StrokeLineJoin.BEVEL;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public BaseBounds impl_computeGeomBounds(BaseBounds paramBaseBounds, BaseTransform paramBaseTransform)
/*     */   {
/* 288 */     if (this.impl_mode == PGShape.Mode.EMPTY) {
/* 289 */       return paramBaseBounds.makeEmpty();
/*     */     }
/* 291 */     if ((paramBaseTransform.getType() & 0xFFFFFFB8) != 0) {
/* 292 */       return computeShapeBounds(paramBaseBounds, paramBaseTransform, impl_configShape());
/*     */     }
/*     */ 
/* 296 */     double d1 = getRadius();
/* 297 */     double d2 = getCenterX() - d1;
/* 298 */     double d3 = getCenterY() - d1;
/* 299 */     double d4 = 2.0D * d1;
/* 300 */     double d5 = d4;
/*     */     double d7;
/*     */     double d6;
/* 303 */     if ((this.impl_mode == PGShape.Mode.FILL) || (getStrokeType() == StrokeType.INSIDE)) {
/* 304 */       d6 = d7 = 0.0D;
/*     */     } else {
/* 306 */       d6 = getStrokeWidth();
/* 307 */       if (getStrokeType() == StrokeType.CENTERED) {
/* 308 */         d6 /= 2.0D;
/*     */       }
/* 310 */       d7 = 0.0D;
/*     */     }
/* 312 */     return computeBounds(paramBaseBounds, paramBaseTransform, d6, d7, d2, d3, d4, d5);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public Ellipse2D impl_configShape()
/*     */   {
/* 321 */     double d = getRadius();
/* 322 */     this.shape.setFrame((float)(getCenterX() - d), (float)(getCenterY() - d), (float)(d * 2.0D), (float)(d * 2.0D));
/*     */ 
/* 327 */     return this.shape;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void impl_updatePG()
/*     */   {
/* 336 */     super.impl_updatePG();
/*     */ 
/* 338 */     if (impl_isDirty(DirtyBits.NODE_GEOMETRY)) {
/* 339 */       PGCircle localPGCircle = getPGCircle();
/* 340 */       localPGCircle.updateCircle((float)getCenterX(), (float)getCenterY(), (float)getRadius());
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.shape.Circle
 * JD-Core Version:    0.6.2
 */