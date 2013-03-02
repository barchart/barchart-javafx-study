/*     */ package javafx.scene.transform;
/*     */ 
/*     */ import com.sun.javafx.geom.transform.Affine3D;
/*     */ import javafx.beans.property.DoubleProperty;
/*     */ import javafx.beans.property.DoublePropertyBase;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.ObjectPropertyBase;
/*     */ import javafx.geometry.Point3D;
/*     */ 
/*     */ public class Rotate extends Transform
/*     */ {
/*  72 */   public static final Point3D X_AXIS = new Point3D(1.0D, 0.0D, 0.0D);
/*     */ 
/*  77 */   public static final Point3D Y_AXIS = new Point3D(0.0D, 1.0D, 0.0D);
/*     */ 
/*  82 */   public static final Point3D Z_AXIS = new Point3D(0.0D, 0.0D, 1.0D);
/*     */   private DoubleProperty angle;
/*     */   private DoubleProperty pivotX;
/*     */   private DoubleProperty pivotY;
/*     */   private DoubleProperty pivotZ;
/*     */   private ObjectProperty<Point3D> axis;
/*     */ 
/*     */   public Rotate()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Rotate(double paramDouble)
/*     */   {
/*  95 */     setAngle(paramDouble);
/*     */   }
/*     */ 
/*     */   public Rotate(double paramDouble, Point3D paramPoint3D)
/*     */   {
/* 104 */     setAngle(paramDouble);
/* 105 */     setAxis(paramPoint3D);
/*     */   }
/*     */ 
/*     */   public Rotate(double paramDouble1, double paramDouble2, double paramDouble3)
/*     */   {
/* 115 */     setAngle(paramDouble1);
/* 116 */     setPivotX(paramDouble2);
/* 117 */     setPivotY(paramDouble3);
/*     */   }
/*     */ 
/*     */   public Rotate(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*     */   {
/* 128 */     this(paramDouble1, paramDouble2, paramDouble3);
/* 129 */     setPivotZ(paramDouble4);
/*     */   }
/*     */ 
/*     */   public Rotate(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, Point3D paramPoint3D)
/*     */   {
/* 141 */     this(paramDouble1, paramDouble2, paramDouble3);
/* 142 */     setPivotZ(paramDouble4);
/* 143 */     setAxis(paramPoint3D);
/*     */   }
/*     */ 
/*     */   public final void setAngle(double paramDouble)
/*     */   {
/* 153 */     angleProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getAngle() {
/* 157 */     return this.angle == null ? 0.0D : this.angle.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty angleProperty() {
/* 161 */     if (this.angle == null) {
/* 162 */       this.angle = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 166 */           Rotate.this.transformChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 171 */           return Rotate.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 176 */           return "angle";
/*     */         }
/*     */       };
/*     */     }
/* 180 */     return this.angle;
/*     */   }
/*     */ 
/*     */   public final void setPivotX(double paramDouble)
/*     */   {
/* 190 */     pivotXProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getPivotX() {
/* 194 */     return this.pivotX == null ? 0.0D : this.pivotX.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty pivotXProperty() {
/* 198 */     if (this.pivotX == null) {
/* 199 */       this.pivotX = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 203 */           Rotate.this.transformChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 208 */           return Rotate.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 213 */           return "pivotX";
/*     */         }
/*     */       };
/*     */     }
/* 217 */     return this.pivotX;
/*     */   }
/*     */ 
/*     */   public final void setPivotY(double paramDouble)
/*     */   {
/* 227 */     pivotYProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getPivotY() {
/* 231 */     return this.pivotY == null ? 0.0D : this.pivotY.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty pivotYProperty() {
/* 235 */     if (this.pivotY == null) {
/* 236 */       this.pivotY = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 240 */           Rotate.this.transformChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 245 */           return Rotate.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 250 */           return "pivotY";
/*     */         }
/*     */       };
/*     */     }
/* 254 */     return this.pivotY;
/*     */   }
/*     */ 
/*     */   public final void setPivotZ(double paramDouble)
/*     */   {
/* 264 */     pivotZProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getPivotZ() {
/* 268 */     return this.pivotZ == null ? 0.0D : this.pivotZ.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty pivotZProperty() {
/* 272 */     if (this.pivotZ == null) {
/* 273 */       this.pivotZ = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 277 */           Rotate.this.transformChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 282 */           return Rotate.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 287 */           return "pivotZ";
/*     */         }
/*     */       };
/*     */     }
/* 291 */     return this.pivotZ;
/*     */   }
/*     */ 
/*     */   public final void setAxis(Point3D paramPoint3D)
/*     */   {
/* 301 */     axisProperty().set(paramPoint3D);
/*     */   }
/*     */ 
/*     */   public final Point3D getAxis() {
/* 305 */     return this.axis == null ? Z_AXIS : (Point3D)this.axis.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<Point3D> axisProperty() {
/* 309 */     if (this.axis == null) {
/* 310 */       this.axis = new ObjectPropertyBase(Z_AXIS)
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 314 */           Rotate.this.transformChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 319 */           return Rotate.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 324 */           return "axis";
/*     */         }
/*     */       };
/*     */     }
/* 328 */     return this.axis;
/*     */   }
/*     */ 
/*     */   private double cos() {
/* 332 */     return Math.cos(Math.toRadians(getAngle()));
/*     */   }
/*     */ 
/*     */   private double sin() {
/* 336 */     return Math.sin(Math.toRadians(getAngle()));
/*     */   }
/*     */ 
/*     */   private Point3D normalizedAxis() {
/* 340 */     Point3D localPoint3D = getAxis();
/* 341 */     double d = Math.sqrt(localPoint3D.getX() * localPoint3D.getX() + localPoint3D.getY() * localPoint3D.getY() + localPoint3D.getZ() * localPoint3D.getZ());
/*     */ 
/* 344 */     if (d == 0.0D) {
/* 345 */       return Z_AXIS;
/*     */     }
/*     */ 
/* 348 */     return new Point3D(localPoint3D.getX() / d, localPoint3D.getY() / d, localPoint3D.getZ() / d);
/*     */   }
/*     */ 
/*     */   public double getMxx()
/*     */   {
/* 353 */     double d1 = normalizedAxis().getX();
/* 354 */     double d2 = cos();
/* 355 */     return d2 + d1 * d1 * (1.0D - d2);
/*     */   }
/*     */ 
/*     */   public double getMxy()
/*     */   {
/* 360 */     Point3D localPoint3D = normalizedAxis();
/* 361 */     return localPoint3D.getX() * localPoint3D.getY() * (1.0D - cos()) - localPoint3D.getZ() * sin();
/*     */   }
/*     */ 
/*     */   public double getMxz()
/*     */   {
/* 366 */     Point3D localPoint3D = normalizedAxis();
/* 367 */     return localPoint3D.getX() * localPoint3D.getZ() * (1.0D - cos()) + localPoint3D.getY() * sin();
/*     */   }
/*     */ 
/*     */   public double getTx()
/*     */   {
/* 372 */     return getPivotX() - getPivotX() * getMxx() - getPivotY() * getMxy() - getPivotZ() * getMxz();
/*     */   }
/*     */ 
/*     */   public double getMyx()
/*     */   {
/* 380 */     Point3D localPoint3D = normalizedAxis();
/* 381 */     return localPoint3D.getY() * localPoint3D.getX() * (1.0D - cos()) + localPoint3D.getZ() * sin();
/*     */   }
/*     */ 
/*     */   public double getMyy()
/*     */   {
/* 386 */     double d1 = normalizedAxis().getY();
/* 387 */     double d2 = cos();
/* 388 */     return d2 + d1 * d1 * (1.0D - d2);
/*     */   }
/*     */ 
/*     */   public double getMyz()
/*     */   {
/* 393 */     Point3D localPoint3D = normalizedAxis();
/* 394 */     return localPoint3D.getY() * localPoint3D.getZ() * (1.0D - cos()) - localPoint3D.getX() * sin();
/*     */   }
/*     */ 
/*     */   public double getTy()
/*     */   {
/* 399 */     return getPivotY() - getPivotX() * getMyx() - getPivotY() * getMyy() - getPivotZ() * getMyz();
/*     */   }
/*     */ 
/*     */   public double getMzx()
/*     */   {
/* 407 */     Point3D localPoint3D = normalizedAxis();
/* 408 */     return localPoint3D.getZ() * localPoint3D.getX() * (1.0D - cos()) - localPoint3D.getY() * sin();
/*     */   }
/*     */ 
/*     */   public double getMzy()
/*     */   {
/* 413 */     Point3D localPoint3D = normalizedAxis();
/* 414 */     return localPoint3D.getZ() * localPoint3D.getY() * (1.0D - cos()) + localPoint3D.getX() * sin();
/*     */   }
/*     */ 
/*     */   public double getMzz()
/*     */   {
/* 419 */     double d1 = normalizedAxis().getZ();
/* 420 */     double d2 = cos();
/* 421 */     return d2 + d1 * d1 * (1.0D - d2);
/*     */   }
/*     */ 
/*     */   public double getTz()
/*     */   {
/* 426 */     return getPivotZ() - getPivotX() * getMzx() - getPivotY() * getMzy() - getPivotZ() * getMzz();
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void impl_apply(Affine3D paramAffine3D)
/*     */   {
/* 439 */     double d1 = getPivotX();
/* 440 */     double d2 = getPivotY();
/* 441 */     double d3 = getPivotZ();
/* 442 */     double d4 = getAngle();
/*     */ 
/* 444 */     if ((d1 != 0.0D) || (d2 != 0.0D) || (d3 != 0.0D)) {
/* 445 */       paramAffine3D.translate(d1, d2, d3);
/* 446 */       paramAffine3D.rotate(Math.toRadians(d4), getAxis().getX(), getAxis().getY(), getAxis().getZ());
/*     */ 
/* 448 */       paramAffine3D.translate(-d1, -d2, -d3);
/*     */     } else {
/* 450 */       paramAffine3D.rotate(Math.toRadians(d4), getAxis().getX(), getAxis().getY(), getAxis().getZ());
/*     */     }
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public Transform impl_copy()
/*     */   {
/* 462 */     return new Rotate(getAngle(), getPivotX(), getPivotY(), getPivotZ(), getAxis());
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 472 */     StringBuilder localStringBuilder = new StringBuilder("Rotate [");
/*     */ 
/* 474 */     localStringBuilder.append("angle=").append(getAngle());
/* 475 */     localStringBuilder.append(", pivotX=").append(getPivotX());
/* 476 */     localStringBuilder.append(", pivotY=").append(getPivotY());
/* 477 */     localStringBuilder.append(", pivotZ=").append(getPivotZ());
/* 478 */     localStringBuilder.append(", axis=").append(getAxis());
/*     */ 
/* 480 */     return "]";
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.transform.Rotate
 * JD-Core Version:    0.6.2
 */