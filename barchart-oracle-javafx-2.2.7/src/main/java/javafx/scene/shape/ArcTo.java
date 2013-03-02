/*     */ package javafx.scene.shape;
/*     */ 
/*     */ import com.sun.javafx.geom.Arc2D;
/*     */ import com.sun.javafx.geom.Path2D;
/*     */ import com.sun.javafx.geom.PathIterator;
/*     */ import com.sun.javafx.geom.transform.BaseTransform;
/*     */ import com.sun.javafx.sg.PGPath;
/*     */ import javafx.beans.property.BooleanProperty;
/*     */ import javafx.beans.property.BooleanPropertyBase;
/*     */ import javafx.beans.property.DoubleProperty;
/*     */ import javafx.beans.property.DoublePropertyBase;
/*     */ 
/*     */ public class ArcTo extends PathElement
/*     */ {
/* 106 */   private DoubleProperty radiusX = new DoublePropertyBase()
/*     */   {
/*     */     public void invalidated()
/*     */     {
/* 110 */       ArcTo.this.u();
/*     */     }
/*     */ 
/*     */     public Object getBean()
/*     */     {
/* 115 */       return ArcTo.this;
/*     */     }
/*     */ 
/*     */     public String getName()
/*     */     {
/* 120 */       return "radiusX";
/*     */     }
/* 106 */   };
/*     */ 
/* 141 */   private DoubleProperty radiusY = new DoublePropertyBase()
/*     */   {
/*     */     public void invalidated()
/*     */     {
/* 145 */       ArcTo.this.u();
/*     */     }
/*     */ 
/*     */     public Object getBean()
/*     */     {
/* 150 */       return ArcTo.this;
/*     */     }
/*     */ 
/*     */     public String getName()
/*     */     {
/* 155 */       return "radiusY";
/*     */     }
/* 141 */   };
/*     */   private DoubleProperty xAxisRotation;
/*     */   private BooleanProperty largeArcFlag;
/*     */   private BooleanProperty sweepFlag;
/*     */   private DoubleProperty x;
/*     */   private DoubleProperty y;
/*     */ 
/*     */   public ArcTo()
/*     */   {
/*     */   }
/*     */ 
/*     */   public ArcTo(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, boolean paramBoolean1, boolean paramBoolean2)
/*     */   {
/*  92 */     setRadiusX(paramDouble1);
/*  93 */     setRadiusY(paramDouble2);
/*  94 */     setXAxisRotation(paramDouble3);
/*  95 */     setX(paramDouble4);
/*  96 */     setY(paramDouble5);
/*  97 */     setLargeArcFlag(paramBoolean1);
/*  98 */     setSweepFlag(paramBoolean2);
/*     */   }
/*     */ 
/*     */   public final void setRadiusX(double paramDouble)
/*     */   {
/* 125 */     this.radiusX.set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getRadiusX() {
/* 129 */     return this.radiusX.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty radiusXProperty() {
/* 133 */     return this.radiusX;
/*     */   }
/*     */ 
/*     */   public final void setRadiusY(double paramDouble)
/*     */   {
/* 160 */     this.radiusY.set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getRadiusY() {
/* 164 */     return this.radiusY.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty radiusYProperty() {
/* 168 */     return this.radiusY;
/*     */   }
/*     */ 
/*     */   public final void setXAxisRotation(double paramDouble)
/*     */   {
/* 183 */     if ((this.xAxisRotation != null) || (paramDouble != 0.0D))
/* 184 */       XAxisRotationProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getXAxisRotation()
/*     */   {
/* 193 */     return this.xAxisRotation == null ? 0.0D : this.xAxisRotation.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty XAxisRotationProperty()
/*     */   {
/* 201 */     if (this.xAxisRotation == null) {
/* 202 */       this.xAxisRotation = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 206 */           ArcTo.this.u();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 211 */           return ArcTo.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 216 */           return "XAxisRotation";
/*     */         }
/*     */       };
/*     */     }
/* 220 */     return this.xAxisRotation;
/*     */   }
/*     */ 
/*     */   public final void setLargeArcFlag(boolean paramBoolean)
/*     */   {
/* 231 */     if ((this.largeArcFlag != null) || (paramBoolean))
/* 232 */       largeArcFlagProperty().set(paramBoolean);
/*     */   }
/*     */ 
/*     */   public final boolean isLargeArcFlag()
/*     */   {
/* 237 */     return this.largeArcFlag == null ? false : this.largeArcFlag.get();
/*     */   }
/*     */ 
/*     */   public final BooleanProperty largeArcFlagProperty() {
/* 241 */     if (this.largeArcFlag == null) {
/* 242 */       this.largeArcFlag = new BooleanPropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 246 */           ArcTo.this.u();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 251 */           return ArcTo.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 256 */           return "largeArcFlag";
/*     */         }
/*     */       };
/*     */     }
/* 260 */     return this.largeArcFlag;
/*     */   }
/*     */ 
/*     */   public final void setSweepFlag(boolean paramBoolean)
/*     */   {
/* 271 */     if ((this.sweepFlag != null) || (paramBoolean))
/* 272 */       sweepFlagProperty().set(paramBoolean);
/*     */   }
/*     */ 
/*     */   public final boolean isSweepFlag()
/*     */   {
/* 277 */     return this.sweepFlag == null ? false : this.sweepFlag.get();
/*     */   }
/*     */ 
/*     */   public final BooleanProperty sweepFlagProperty() {
/* 281 */     if (this.sweepFlag == null) {
/* 282 */       this.sweepFlag = new BooleanPropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 286 */           ArcTo.this.u();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 291 */           return ArcTo.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 296 */           return "sweepFlag";
/*     */         }
/*     */       };
/*     */     }
/* 300 */     return this.sweepFlag;
/*     */   }
/*     */ 
/*     */   public final void setX(double paramDouble)
/*     */   {
/* 313 */     if ((this.x != null) || (paramDouble != 0.0D))
/* 314 */       xProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getX()
/*     */   {
/* 319 */     return this.x == null ? 0.0D : this.x.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty xProperty() {
/* 323 */     if (this.x == null) {
/* 324 */       this.x = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 328 */           ArcTo.this.u();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 333 */           return ArcTo.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 338 */           return "x";
/*     */         }
/*     */       };
/*     */     }
/* 342 */     return this.x;
/*     */   }
/*     */ 
/*     */   public final void setY(double paramDouble)
/*     */   {
/* 355 */     if ((this.y != null) || (paramDouble != 0.0D))
/* 356 */       yProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getY()
/*     */   {
/* 361 */     return this.y == null ? 0.0D : this.y.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty yProperty() {
/* 365 */     if (this.y == null) {
/* 366 */       this.y = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 370 */           ArcTo.this.u();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 375 */           return ArcTo.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 380 */           return "y";
/*     */         }
/*     */       };
/*     */     }
/* 384 */     return this.y;
/*     */   }
/*     */ 
/*     */   void addTo(PGPath paramPGPath)
/*     */   {
/* 389 */     addArcTo(paramPGPath, null, paramPGPath.getCurrentX(), paramPGPath.getCurrentY());
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void impl_addTo(Path2D paramPath2D)
/*     */   {
/* 398 */     addArcTo(null, paramPath2D, paramPath2D.getCurrentX(), paramPath2D.getCurrentY());
/*     */   }
/*     */ 
/*     */   private void addArcTo(PGPath paramPGPath, Path2D paramPath2D, double paramDouble1, double paramDouble2)
/*     */   {
/* 407 */     double d1 = getX();
/* 408 */     double d2 = getY();
/* 409 */     boolean bool1 = isSweepFlag();
/* 410 */     boolean bool2 = isLargeArcFlag();
/*     */ 
/* 413 */     double d3 = isAbsolute() ? d1 : d1 + paramDouble1;
/* 414 */     double d4 = isAbsolute() ? d2 : d2 + paramDouble2;
/*     */ 
/* 416 */     double d5 = (paramDouble1 - d3) / 2.0D;
/* 417 */     double d6 = (paramDouble2 - d4) / 2.0D;
/*     */ 
/* 419 */     double d7 = Math.toRadians(getXAxisRotation());
/* 420 */     double d8 = Math.cos(d7);
/* 421 */     double d9 = Math.sin(d7);
/*     */ 
/* 426 */     double d10 = d8 * d5 + d9 * d6;
/* 427 */     double d11 = -d9 * d5 + d8 * d6;
/*     */ 
/* 429 */     double d12 = Math.abs(getRadiusX());
/* 430 */     double d13 = Math.abs(getRadiusY());
/* 431 */     double d14 = d12 * d12;
/* 432 */     double d15 = d13 * d13;
/* 433 */     double d16 = d10 * d10;
/* 434 */     double d17 = d11 * d11;
/*     */ 
/* 436 */     double d18 = d16 / d14 + d17 / d15;
/* 437 */     if (d18 > 1.0D) {
/* 438 */       d12 = Math.sqrt(d18) * d12;
/* 439 */       d13 = Math.sqrt(d18) * d13;
/* 440 */       if ((d12 != d12) || (d13 != d13)) {
/* 441 */         if (paramPGPath == null)
/* 442 */           paramPath2D.lineTo((float)d3, (float)d4);
/*     */         else {
/* 444 */           paramPGPath.addLineTo((float)d3, (float)d4);
/*     */         }
/* 446 */         return;
/*     */       }
/* 448 */       d14 = d12 * d12;
/* 449 */       d15 = d13 * d13;
/*     */     }
/*     */ 
/* 455 */     double d19 = bool2 == bool1 ? -1.0D : 1.0D;
/* 456 */     double d20 = (d14 * d15 - d14 * d17 - d15 * d16) / (d14 * d17 + d15 * d16);
/* 457 */     d20 = d20 < 0.0D ? 0.0D : d20;
/* 458 */     double d21 = d19 * Math.sqrt(d20);
/* 459 */     double d22 = d21 * (d12 * d11 / d13);
/* 460 */     double d23 = d21 * -(d13 * d10 / d12);
/*     */ 
/* 465 */     double d24 = (paramDouble1 + d3) / 2.0D;
/* 466 */     double d25 = (paramDouble2 + d4) / 2.0D;
/* 467 */     double d26 = d24 + (d8 * d22 - d9 * d23);
/* 468 */     double d27 = d25 + (d9 * d22 + d8 * d23);
/*     */ 
/* 473 */     double d28 = (d10 - d22) / d12;
/* 474 */     double d29 = (d11 - d23) / d13;
/* 475 */     double d30 = (-d10 - d22) / d12;
/* 476 */     double d31 = (-d11 - d23) / d13;
/*     */ 
/* 478 */     double d32 = Math.sqrt(d28 * d28 + d29 * d29);
/* 479 */     double d33 = d28;
/* 480 */     d19 = d29 < 0.0D ? -1.0D : 1.0D;
/* 481 */     double d34 = Math.toDegrees(d19 * Math.acos(d33 / d32));
/*     */ 
/* 484 */     d32 = Math.sqrt((d28 * d28 + d29 * d29) * (d30 * d30 + d31 * d31));
/* 485 */     d33 = d28 * d30 + d29 * d31;
/* 486 */     d19 = d28 * d31 - d29 * d30 < 0.0D ? -1.0D : 1.0D;
/* 487 */     double d35 = Math.toDegrees(d19 * Math.acos(d33 / d32));
/* 488 */     if ((!bool1) && (d35 > 0.0D))
/* 489 */       d35 -= 360.0D;
/* 490 */     else if ((bool1) && (d35 < 0.0D)) {
/* 491 */       d35 += 360.0D;
/*     */     }
/* 493 */     d35 %= 360.0D;
/* 494 */     d34 %= 360.0D;
/*     */ 
/* 499 */     float f1 = (float)(d26 - d12);
/* 500 */     float f2 = (float)(d27 - d13);
/* 501 */     float f3 = (float)(d12 * 2.0D);
/* 502 */     float f4 = (float)(d13 * 2.0D);
/* 503 */     float f5 = (float)-d34;
/* 504 */     float f6 = (float)-d35;
/*     */ 
/* 506 */     if (paramPGPath == null) {
/* 507 */       Arc2D localArc2D = new Arc2D(f1, f2, f3, f4, f5, f6, 0);
/*     */ 
/* 510 */       BaseTransform localBaseTransform = d7 == 0.0D ? null : BaseTransform.getRotateInstance(d7, d26, d27);
/*     */ 
/* 512 */       PathIterator localPathIterator = localArc2D.getPathIterator(localBaseTransform);
/*     */ 
/* 516 */       localPathIterator.next();
/* 517 */       paramPath2D.append(localPathIterator, true);
/*     */     } else {
/* 519 */       paramPGPath.addArcTo(f1, f2, f3, f4, f5, f6, (float)d7);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.shape.ArcTo
 * JD-Core Version:    0.6.2
 */