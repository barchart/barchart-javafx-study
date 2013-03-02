/*     */ package javafx.scene.shape;
/*     */ 
/*     */ import com.sun.javafx.geom.CubicCurve2D;
/*     */ import com.sun.javafx.scene.DirtyBits;
/*     */ import com.sun.javafx.sg.PGCubicCurve;
/*     */ import com.sun.javafx.sg.PGNode;
/*     */ import com.sun.javafx.tk.Toolkit;
/*     */ import javafx.beans.property.DoubleProperty;
/*     */ import javafx.beans.property.DoublePropertyBase;
/*     */ 
/*     */ public class CubicCurve extends Shape
/*     */ {
/*  65 */   private final CubicCurve2D shape = new CubicCurve2D();
/*     */   private DoubleProperty startX;
/*     */   private DoubleProperty startY;
/*     */   private DoubleProperty controlX1;
/*     */   private DoubleProperty controlY1;
/*     */   private DoubleProperty controlX2;
/*     */   private DoubleProperty controlY2;
/*     */   private DoubleProperty endX;
/*     */   private DoubleProperty endY;
/*     */ 
/*     */   public CubicCurve()
/*     */   {
/*     */   }
/*     */ 
/*     */   public CubicCurve(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8)
/*     */   {
/*  93 */     setStartX(paramDouble1);
/*  94 */     setStartY(paramDouble2);
/*  95 */     setControlX1(paramDouble3);
/*  96 */     setControlY1(paramDouble4);
/*  97 */     setControlX2(paramDouble5);
/*  98 */     setControlY2(paramDouble6);
/*  99 */     setEndX(paramDouble7);
/* 100 */     setEndY(paramDouble8);
/*     */   }
/*     */ 
/*     */   public final void setStartX(double paramDouble) {
/* 104 */     if ((this.startX != null) || (paramDouble != 0.0D))
/* 105 */       startXProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getStartX()
/*     */   {
/* 110 */     return this.startX == null ? 0.0D : this.startX.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty startXProperty() {
/* 114 */     if (this.startX == null) {
/* 115 */       this.startX = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 119 */           CubicCurve.this.impl_markDirty(DirtyBits.NODE_GEOMETRY);
/* 120 */           CubicCurve.this.impl_geomChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 125 */           return CubicCurve.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 130 */           return "startX";
/*     */         }
/*     */       };
/*     */     }
/* 134 */     return this.startX;
/*     */   }
/*     */ 
/*     */   public final void setStartY(double paramDouble)
/*     */   {
/* 145 */     if ((this.startY != null) || (paramDouble != 0.0D))
/* 146 */       startYProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getStartY()
/*     */   {
/* 151 */     return this.startY == null ? 0.0D : this.startY.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty startYProperty() {
/* 155 */     if (this.startY == null) {
/* 156 */       this.startY = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 160 */           CubicCurve.this.impl_markDirty(DirtyBits.NODE_GEOMETRY);
/* 161 */           CubicCurve.this.impl_geomChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 166 */           return CubicCurve.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 171 */           return "startY";
/*     */         }
/*     */       };
/*     */     }
/* 175 */     return this.startY;
/*     */   }
/*     */ 
/*     */   public final void setControlX1(double paramDouble)
/*     */   {
/* 187 */     if ((this.controlX1 != null) || (paramDouble != 0.0D))
/* 188 */       controlX1Property().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getControlX1()
/*     */   {
/* 193 */     return this.controlX1 == null ? 0.0D : this.controlX1.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty controlX1Property() {
/* 197 */     if (this.controlX1 == null) {
/* 198 */       this.controlX1 = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 202 */           CubicCurve.this.impl_markDirty(DirtyBits.NODE_GEOMETRY);
/* 203 */           CubicCurve.this.impl_geomChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 208 */           return CubicCurve.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 213 */           return "controlX1";
/*     */         }
/*     */       };
/*     */     }
/* 217 */     return this.controlX1;
/*     */   }
/*     */ 
/*     */   public final void setControlY1(double paramDouble)
/*     */   {
/* 229 */     if ((this.controlY1 != null) || (paramDouble != 0.0D))
/* 230 */       controlY1Property().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getControlY1()
/*     */   {
/* 235 */     return this.controlY1 == null ? 0.0D : this.controlY1.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty controlY1Property() {
/* 239 */     if (this.controlY1 == null) {
/* 240 */       this.controlY1 = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 244 */           CubicCurve.this.impl_markDirty(DirtyBits.NODE_GEOMETRY);
/* 245 */           CubicCurve.this.impl_geomChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 250 */           return CubicCurve.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 255 */           return "controlY1";
/*     */         }
/*     */       };
/*     */     }
/* 259 */     return this.controlY1;
/*     */   }
/*     */ 
/*     */   public final void setControlX2(double paramDouble)
/*     */   {
/* 271 */     if ((this.controlX2 != null) || (paramDouble != 0.0D))
/* 272 */       controlX2Property().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getControlX2()
/*     */   {
/* 277 */     return this.controlX2 == null ? 0.0D : this.controlX2.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty controlX2Property() {
/* 281 */     if (this.controlX2 == null) {
/* 282 */       this.controlX2 = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 286 */           CubicCurve.this.impl_markDirty(DirtyBits.NODE_GEOMETRY);
/* 287 */           CubicCurve.this.impl_geomChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 292 */           return CubicCurve.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 297 */           return "controlX2";
/*     */         }
/*     */       };
/*     */     }
/* 301 */     return this.controlX2;
/*     */   }
/*     */ 
/*     */   public final void setControlY2(double paramDouble)
/*     */   {
/* 313 */     if ((this.controlY2 != null) || (paramDouble != 0.0D))
/* 314 */       controlY2Property().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getControlY2()
/*     */   {
/* 319 */     return this.controlY2 == null ? 0.0D : this.controlY2.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty controlY2Property() {
/* 323 */     if (this.controlY2 == null) {
/* 324 */       this.controlY2 = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 328 */           CubicCurve.this.impl_markDirty(DirtyBits.NODE_GEOMETRY);
/* 329 */           CubicCurve.this.impl_geomChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 334 */           return CubicCurve.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 339 */           return "controlY2";
/*     */         }
/*     */       };
/*     */     }
/* 343 */     return this.controlY2;
/*     */   }
/*     */ 
/*     */   public final void setEndX(double paramDouble)
/*     */   {
/* 354 */     if ((this.endX != null) || (paramDouble != 0.0D))
/* 355 */       endXProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getEndX()
/*     */   {
/* 360 */     return this.endX == null ? 0.0D : this.endX.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty endXProperty() {
/* 364 */     if (this.endX == null) {
/* 365 */       this.endX = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 369 */           CubicCurve.this.impl_markDirty(DirtyBits.NODE_GEOMETRY);
/* 370 */           CubicCurve.this.impl_geomChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 375 */           return CubicCurve.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 380 */           return "endX";
/*     */         }
/*     */       };
/*     */     }
/* 384 */     return this.endX;
/*     */   }
/*     */ 
/*     */   public final void setEndY(double paramDouble)
/*     */   {
/* 395 */     if ((this.endY != null) || (paramDouble != 0.0D))
/* 396 */       endYProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getEndY()
/*     */   {
/* 401 */     return this.endY == null ? 0.0D : this.endY.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty endYProperty() {
/* 405 */     if (this.endY == null) {
/* 406 */       this.endY = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 410 */           CubicCurve.this.impl_markDirty(DirtyBits.NODE_GEOMETRY);
/* 411 */           CubicCurve.this.impl_geomChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 416 */           return CubicCurve.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 421 */           return "endY";
/*     */         }
/*     */       };
/*     */     }
/* 425 */     return this.endY;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public CubicCurve2D impl_configShape()
/*     */   {
/* 434 */     this.shape.x1 = ((float)getStartX());
/* 435 */     this.shape.y1 = ((float)getStartY());
/* 436 */     this.shape.ctrlx1 = ((float)getControlX1());
/* 437 */     this.shape.ctrly1 = ((float)getControlY1());
/* 438 */     this.shape.ctrlx2 = ((float)getControlX2());
/* 439 */     this.shape.ctrly2 = ((float)getControlY2());
/* 440 */     this.shape.x2 = ((float)getEndX());
/* 441 */     this.shape.y2 = ((float)getEndY());
/* 442 */     return this.shape;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   protected PGNode impl_createPGNode()
/*     */   {
/* 451 */     return Toolkit.getToolkit().createPGCubicCurve();
/*     */   }
/*     */ 
/*     */   PGCubicCurve getPGCubicCurve() {
/* 455 */     return (PGCubicCurve)impl_getPGNode();
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void impl_updatePG()
/*     */   {
/* 464 */     super.impl_updatePG();
/*     */ 
/* 466 */     if (impl_isDirty(DirtyBits.NODE_GEOMETRY)) {
/* 467 */       PGCubicCurve localPGCubicCurve = getPGCubicCurve();
/* 468 */       localPGCubicCurve.updateCubicCurve((float)getStartX(), (float)getStartY(), (float)getEndX(), (float)getEndY(), (float)getControlX1(), (float)getControlY1(), (float)getControlX2(), (float)getControlY2());
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.shape.CubicCurve
 * JD-Core Version:    0.6.2
 */