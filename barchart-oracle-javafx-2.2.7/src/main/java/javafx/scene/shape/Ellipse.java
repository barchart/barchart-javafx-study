/*     */ package javafx.scene.shape;
/*     */ 
/*     */ import com.sun.javafx.geom.BaseBounds;
/*     */ import com.sun.javafx.geom.Ellipse2D;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.javafx.scene.DirtyBits;
/*     */ import com.sun.javafx.sg.PGEllipse;
/*     */ import com.sun.javafx.sg.PGNode;
/*     */ import com.sun.javafx.sg.PGShape.Mode;
/*     */ import com.sun.javafx.sg.PGShape.StrokeLineJoin;
/*     */ import com.sun.javafx.tk.Toolkit;
/*     */ import javafx.beans.property.DoubleProperty;
/*     */ import javafx.beans.property.DoublePropertyBase;
/*     */ 
/*     */ public class Ellipse extends Shape
/*     */ {
/*  59 */   private final Ellipse2D shape = new Ellipse2D();
/*     */   private static final int NON_RECTILINEAR_TYPE_MASK = -72;
/*     */   private DoubleProperty centerX;
/*     */   private DoubleProperty centerY;
/* 183 */   private DoubleProperty radiusX = new DoublePropertyBase()
/*     */   {
/*     */     public void invalidated()
/*     */     {
/* 187 */       Ellipse.this.impl_markDirty(DirtyBits.NODE_GEOMETRY);
/* 188 */       Ellipse.this.impl_geomChanged();
/*     */     }
/*     */ 
/*     */     public Object getBean()
/*     */     {
/* 193 */       return Ellipse.this;
/*     */     }
/*     */ 
/*     */     public String getName()
/*     */     {
/* 198 */       return "radiusX";
/*     */     }
/* 183 */   };
/*     */ 
/* 219 */   private DoubleProperty radiusY = new DoublePropertyBase()
/*     */   {
/*     */     public void invalidated()
/*     */     {
/* 223 */       Ellipse.this.impl_markDirty(DirtyBits.NODE_GEOMETRY);
/* 224 */       Ellipse.this.impl_geomChanged();
/*     */     }
/*     */ 
/*     */     public Object getBean()
/*     */     {
/* 229 */       return Ellipse.this;
/*     */     }
/*     */ 
/*     */     public String getName()
/*     */     {
/* 234 */       return "radiusY";
/*     */     }
/* 219 */   };
/*     */ 
/*     */   public Ellipse()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Ellipse(double paramDouble1, double paramDouble2)
/*     */   {
/*  79 */     setRadiusX(paramDouble1);
/*  80 */     setRadiusY(paramDouble2);
/*     */   }
/*     */ 
/*     */   public Ellipse(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*     */   {
/*  91 */     this(paramDouble3, paramDouble4);
/*  92 */     setCenterX(paramDouble1);
/*  93 */     setCenterY(paramDouble2);
/*     */   }
/*     */ 
/*     */   public final void setCenterX(double paramDouble)
/*     */   {
/* 104 */     if ((this.centerX != null) || (paramDouble != 0.0D))
/* 105 */       centerXProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getCenterX()
/*     */   {
/* 110 */     return this.centerX == null ? 0.0D : this.centerX.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty centerXProperty() {
/* 114 */     if (this.centerX == null) {
/* 115 */       this.centerX = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 119 */           Ellipse.this.impl_markDirty(DirtyBits.NODE_GEOMETRY);
/* 120 */           Ellipse.this.impl_geomChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 125 */           return Ellipse.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 130 */           return "centerX";
/*     */         }
/*     */       };
/*     */     }
/* 134 */     return this.centerX;
/*     */   }
/*     */ 
/*     */   public final void setCenterY(double paramDouble)
/*     */   {
/* 145 */     if ((this.centerY != null) || (paramDouble != 0.0D))
/* 146 */       centerYProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getCenterY()
/*     */   {
/* 151 */     return this.centerY == null ? 0.0D : this.centerY.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty centerYProperty() {
/* 155 */     if (this.centerY == null) {
/* 156 */       this.centerY = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 160 */           Ellipse.this.impl_markDirty(DirtyBits.NODE_GEOMETRY);
/* 161 */           Ellipse.this.impl_geomChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 166 */           return Ellipse.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 171 */           return "centerY";
/*     */         }
/*     */       };
/*     */     }
/* 175 */     return this.centerY;
/*     */   }
/*     */ 
/*     */   public final void setRadiusX(double paramDouble)
/*     */   {
/* 203 */     this.radiusX.set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getRadiusX() {
/* 207 */     return this.radiusX.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty radiusXProperty() {
/* 211 */     return this.radiusX;
/*     */   }
/*     */ 
/*     */   public final void setRadiusY(double paramDouble)
/*     */   {
/* 239 */     this.radiusY.set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getRadiusY() {
/* 243 */     return this.radiusY.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty radiusYProperty() {
/* 247 */     return this.radiusY;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   protected PGNode impl_createPGNode()
/*     */   {
/* 257 */     return Toolkit.getToolkit().createPGEllipse();
/*     */   }
/*     */ 
/*     */   PGEllipse getPGEllipse() {
/* 261 */     return (PGEllipse)impl_getPGNode();
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   protected PGShape.StrokeLineJoin toPGLineJoin(StrokeLineJoin paramStrokeLineJoin)
/*     */   {
/* 281 */     return PGShape.StrokeLineJoin.BEVEL;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public BaseBounds impl_computeGeomBounds(BaseBounds paramBaseBounds, BaseTransform paramBaseTransform)
/*     */   {
/* 294 */     if (this.impl_mode == PGShape.Mode.EMPTY) {
/* 295 */       return paramBaseBounds.makeEmpty();
/*     */     }
/* 297 */     if ((paramBaseTransform.getType() & 0xFFFFFFB8) != 0) {
/* 298 */       return computeShapeBounds(paramBaseBounds, paramBaseTransform, impl_configShape());
/*     */     }
/*     */ 
/* 302 */     double d1 = getCenterX() - getRadiusX();
/* 303 */     double d2 = getCenterY() - getRadiusY();
/* 304 */     double d3 = 2.0D * getRadiusX();
/* 305 */     double d4 = 2.0D * getRadiusY();
/*     */     double d6;
/*     */     double d5;
/* 308 */     if ((this.impl_mode == PGShape.Mode.FILL) || (getStrokeType() == StrokeType.INSIDE)) {
/* 309 */       d5 = d6 = 0.0D;
/*     */     } else {
/* 311 */       d5 = getStrokeWidth();
/* 312 */       if (getStrokeType() == StrokeType.CENTERED) {
/* 313 */         d5 /= 2.0D;
/*     */       }
/* 315 */       d6 = 0.0D;
/*     */     }
/* 317 */     return computeBounds(paramBaseBounds, paramBaseTransform, d5, d6, d1, d2, d3, d4);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public Ellipse2D impl_configShape()
/*     */   {
/* 327 */     this.shape.setFrame((float)(getCenterX() - getRadiusX()), (float)(getCenterY() - getRadiusY()), (float)(getRadiusX() * 2.0D), (float)(getRadiusY() * 2.0D));
/*     */ 
/* 332 */     return this.shape;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void impl_updatePG()
/*     */   {
/* 342 */     super.impl_updatePG();
/*     */ 
/* 344 */     if (impl_isDirty(DirtyBits.NODE_GEOMETRY)) {
/* 345 */       PGEllipse localPGEllipse = getPGEllipse();
/* 346 */       localPGEllipse.updateEllipse((float)getCenterX(), (float)getCenterY(), (float)getRadiusX(), (float)getRadiusY());
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.shape.Ellipse
 * JD-Core Version:    0.6.2
 */