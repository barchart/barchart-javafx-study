/*     */ package javafx.scene.shape;
/*     */ 
/*     */ import com.sun.javafx.geom.Arc2D;
/*     */ import com.sun.javafx.scene.DirtyBits;
/*     */ import com.sun.javafx.sg.PGArc;
/*     */ import com.sun.javafx.sg.PGArc.ArcType;
/*     */ import com.sun.javafx.sg.PGNode;
/*     */ import com.sun.javafx.tk.Toolkit;
/*     */ import javafx.beans.property.DoubleProperty;
/*     */ import javafx.beans.property.DoublePropertyBase;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.ObjectPropertyBase;
/*     */ 
/*     */ public class Arc extends Shape
/*     */ {
/*  67 */   private final Arc2D shape = new Arc2D();
/*     */   private DoubleProperty centerX;
/*     */   private DoubleProperty centerY;
/* 194 */   private DoubleProperty radiusX = new DoublePropertyBase()
/*     */   {
/*     */     public void invalidated()
/*     */     {
/* 198 */       Arc.this.impl_markDirty(DirtyBits.NODE_GEOMETRY);
/* 199 */       Arc.this.impl_geomChanged();
/*     */     }
/*     */ 
/*     */     public Object getBean()
/*     */     {
/* 204 */       return Arc.this;
/*     */     }
/*     */ 
/*     */     public String getName()
/*     */     {
/* 209 */       return "radiusX";
/*     */     }
/* 194 */   };
/*     */ 
/* 231 */   private DoubleProperty radiusY = new DoublePropertyBase()
/*     */   {
/*     */     public void invalidated()
/*     */     {
/* 235 */       Arc.this.impl_markDirty(DirtyBits.NODE_GEOMETRY);
/* 236 */       Arc.this.impl_geomChanged();
/*     */     }
/*     */ 
/*     */     public Object getBean()
/*     */     {
/* 241 */       return Arc.this;
/*     */     }
/*     */ 
/*     */     public String getName()
/*     */     {
/* 246 */       return "radiusY";
/*     */     }
/* 231 */   };
/*     */   private DoubleProperty startAngle;
/* 308 */   private DoubleProperty length = new DoublePropertyBase()
/*     */   {
/*     */     public void invalidated()
/*     */     {
/* 312 */       Arc.this.impl_markDirty(DirtyBits.NODE_GEOMETRY);
/* 313 */       Arc.this.impl_geomChanged();
/*     */     }
/*     */ 
/*     */     public Object getBean()
/*     */     {
/* 318 */       return Arc.this;
/*     */     }
/*     */ 
/*     */     public String getName()
/*     */     {
/* 323 */       return "length";
/*     */     }
/* 308 */   };
/*     */   private ObjectProperty<ArcType> type;
/*     */ 
/*     */   static PGArc.ArcType toPGArcType(ArcType paramArcType)
/*     */   {
/*  70 */     switch (8.$SwitchMap$javafx$scene$shape$ArcType[paramArcType.ordinal()]) {
/*     */     case 1:
/*  72 */       return PGArc.ArcType.OPEN;
/*     */     case 2:
/*  74 */       return PGArc.ArcType.CHORD;
/*     */     }
/*  76 */     return PGArc.ArcType.ROUND;
/*     */   }
/*     */ 
/*     */   public Arc()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Arc(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6)
/*     */   {
/*  98 */     setCenterX(paramDouble1);
/*  99 */     setCenterY(paramDouble2);
/* 100 */     setRadiusX(paramDouble3);
/* 101 */     setRadiusY(paramDouble4);
/* 102 */     setStartAngle(paramDouble5);
/* 103 */     setLength(paramDouble6);
/*     */   }
/*     */ 
/*     */   public final void setCenterX(double paramDouble)
/*     */   {
/* 114 */     if ((this.centerX != null) || (paramDouble != 0.0D))
/* 115 */       centerXProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getCenterX()
/*     */   {
/* 120 */     return this.centerX == null ? 0.0D : this.centerX.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty centerXProperty() {
/* 124 */     if (this.centerX == null) {
/* 125 */       this.centerX = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 129 */           Arc.this.impl_markDirty(DirtyBits.NODE_GEOMETRY);
/* 130 */           Arc.this.impl_geomChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 135 */           return Arc.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 140 */           return "centerX";
/*     */         }
/*     */       };
/*     */     }
/* 144 */     return this.centerX;
/*     */   }
/*     */ 
/*     */   public final void setCenterY(double paramDouble)
/*     */   {
/* 155 */     if ((this.centerY != null) || (paramDouble != 0.0D))
/* 156 */       centerYProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getCenterY()
/*     */   {
/* 161 */     return this.centerY == null ? 0.0D : this.centerY.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty centerYProperty() {
/* 165 */     if (this.centerY == null) {
/* 166 */       this.centerY = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 170 */           Arc.this.impl_markDirty(DirtyBits.NODE_GEOMETRY);
/* 171 */           Arc.this.impl_geomChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 176 */           return Arc.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 181 */           return "centerY";
/*     */         }
/*     */       };
/*     */     }
/* 185 */     return this.centerY;
/*     */   }
/*     */ 
/*     */   public final void setRadiusX(double paramDouble)
/*     */   {
/* 214 */     this.radiusX.set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getRadiusX() {
/* 218 */     return this.radiusX.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty radiusXProperty() {
/* 222 */     return this.radiusX;
/*     */   }
/*     */ 
/*     */   public final void setRadiusY(double paramDouble)
/*     */   {
/* 251 */     this.radiusY.set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getRadiusY() {
/* 255 */     return this.radiusY.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty radiusYProperty() {
/* 259 */     return this.radiusY;
/*     */   }
/*     */ 
/*     */   public final void setStartAngle(double paramDouble)
/*     */   {
/* 270 */     if ((this.startAngle != null) || (paramDouble != 0.0D))
/* 271 */       startAngleProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getStartAngle()
/*     */   {
/* 276 */     return this.startAngle == null ? 0.0D : this.startAngle.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty startAngleProperty() {
/* 280 */     if (this.startAngle == null) {
/* 281 */       this.startAngle = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 285 */           Arc.this.impl_markDirty(DirtyBits.NODE_GEOMETRY);
/* 286 */           Arc.this.impl_geomChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 291 */           return Arc.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 296 */           return "startAngle";
/*     */         }
/*     */       };
/*     */     }
/* 300 */     return this.startAngle;
/*     */   }
/*     */ 
/*     */   public final void setLength(double paramDouble)
/*     */   {
/* 328 */     this.length.set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getLength() {
/* 332 */     return this.length.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty lengthProperty() {
/* 336 */     return this.length;
/*     */   }
/*     */ 
/*     */   public final void setType(ArcType paramArcType)
/*     */   {
/* 350 */     if ((this.type != null) || (paramArcType != ArcType.OPEN))
/* 351 */       typeProperty().set(paramArcType);
/*     */   }
/*     */ 
/*     */   public final ArcType getType()
/*     */   {
/* 356 */     return this.type == null ? ArcType.OPEN : (ArcType)this.type.get();
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<ArcType> typeProperty() {
/* 360 */     if (this.type == null) {
/* 361 */       this.type = new ObjectPropertyBase(ArcType.OPEN)
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 365 */           Arc.this.impl_markDirty(DirtyBits.NODE_GEOMETRY);
/* 366 */           Arc.this.impl_geomChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 371 */           return Arc.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 376 */           return "type";
/*     */         }
/*     */       };
/*     */     }
/* 380 */     return this.type;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   protected PGNode impl_createPGNode()
/*     */   {
/* 389 */     return Toolkit.getToolkit().createPGArc();
/*     */   }
/*     */ 
/*     */   PGArc getPGArc() {
/* 393 */     return (PGArc)impl_getPGNode();
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public Arc2D impl_configShape()
/*     */   {
/*     */     int i;
/* 403 */     switch (8.$SwitchMap$javafx$scene$shape$ArcType[getType().ordinal()]) {
/*     */     case 1:
/* 405 */       i = 0;
/* 406 */       break;
/*     */     case 2:
/* 408 */       i = 1;
/* 409 */       break;
/*     */     default:
/* 411 */       i = 2;
/*     */     }
/*     */ 
/* 415 */     this.shape.setArc((float)(getCenterX() - getRadiusX()), (float)(getCenterY() - getRadiusY()), (float)(getRadiusX() * 2.0D), (float)(getRadiusY() * 2.0D), (float)getStartAngle(), (float)getLength(), i);
/*     */ 
/* 424 */     return this.shape;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void impl_updatePG()
/*     */   {
/* 433 */     super.impl_updatePG();
/*     */ 
/* 435 */     if (impl_isDirty(DirtyBits.NODE_GEOMETRY)) {
/* 436 */       PGArc localPGArc = getPGArc();
/* 437 */       localPGArc.updateArc((float)getCenterX(), (float)getCenterY(), (float)getRadiusX(), (float)getRadiusY(), (float)getStartAngle(), (float)getLength(), toPGArcType(getType()));
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.shape.Arc
 * JD-Core Version:    0.6.2
 */