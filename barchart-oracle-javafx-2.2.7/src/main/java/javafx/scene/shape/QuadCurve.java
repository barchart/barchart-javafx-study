/*     */ package javafx.scene.shape;
/*     */ 
/*     */ import com.sun.javafx.geom.QuadCurve2D;
/*     */ import com.sun.javafx.scene.DirtyBits;
/*     */ import com.sun.javafx.sg.PGNode;
/*     */ import com.sun.javafx.sg.PGQuadCurve;
/*     */ import com.sun.javafx.tk.Toolkit;
/*     */ import javafx.beans.property.DoubleProperty;
/*     */ import javafx.beans.property.DoublePropertyBase;
/*     */ 
/*     */ public class QuadCurve extends Shape
/*     */ {
/*  62 */   private final QuadCurve2D shape = new QuadCurve2D();
/*     */   private DoubleProperty startX;
/*     */   private DoubleProperty startY;
/* 178 */   private DoubleProperty controlX = new DoublePropertyBase()
/*     */   {
/*     */     public void invalidated()
/*     */     {
/* 182 */       QuadCurve.this.impl_markDirty(DirtyBits.NODE_GEOMETRY);
/* 183 */       QuadCurve.this.impl_geomChanged();
/*     */     }
/*     */ 
/*     */     public Object getBean()
/*     */     {
/* 188 */       return QuadCurve.this;
/*     */     }
/*     */ 
/*     */     public String getName()
/*     */     {
/* 193 */       return "controlX";
/*     */     }
/* 178 */   };
/*     */ 
/* 215 */   private DoubleProperty controlY = new DoublePropertyBase()
/*     */   {
/*     */     public void invalidated()
/*     */     {
/* 219 */       QuadCurve.this.impl_markDirty(DirtyBits.NODE_GEOMETRY);
/* 220 */       QuadCurve.this.impl_geomChanged();
/*     */     }
/*     */ 
/*     */     public Object getBean()
/*     */     {
/* 225 */       return QuadCurve.this;
/*     */     }
/*     */ 
/*     */     public String getName()
/*     */     {
/* 230 */       return "controlY";
/*     */     }
/* 215 */   };
/*     */   private DoubleProperty endX;
/*     */   private DoubleProperty endY;
/*     */ 
/*     */   public QuadCurve()
/*     */   {
/*     */   }
/*     */ 
/*     */   public QuadCurve(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6)
/*     */   {
/*  80 */     setStartX(paramDouble1);
/*  81 */     setStartY(paramDouble2);
/*  82 */     setControlX(paramDouble3);
/*  83 */     setControlY(paramDouble4);
/*  84 */     setEndX(paramDouble5);
/*  85 */     setEndY(paramDouble6);
/*     */   }
/*     */ 
/*     */   public final void setStartX(double paramDouble)
/*     */   {
/*  97 */     if ((this.startX != null) || (paramDouble != 0.0D))
/*  98 */       startXProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getStartX()
/*     */   {
/* 103 */     return this.startX == null ? 0.0D : this.startX.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty startXProperty() {
/* 107 */     if (this.startX == null) {
/* 108 */       this.startX = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 112 */           QuadCurve.this.impl_markDirty(DirtyBits.NODE_GEOMETRY);
/* 113 */           QuadCurve.this.impl_geomChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 118 */           return QuadCurve.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 123 */           return "startX";
/*     */         }
/*     */       };
/*     */     }
/* 127 */     return this.startX;
/*     */   }
/*     */ 
/*     */   public final void setStartY(double paramDouble)
/*     */   {
/* 139 */     if ((this.startY != null) || (paramDouble != 0.0D))
/* 140 */       startYProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getStartY()
/*     */   {
/* 145 */     return this.startY == null ? 0.0D : this.startY.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty startYProperty() {
/* 149 */     if (this.startY == null) {
/* 150 */       this.startY = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 154 */           QuadCurve.this.impl_markDirty(DirtyBits.NODE_GEOMETRY);
/* 155 */           QuadCurve.this.impl_geomChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 160 */           return QuadCurve.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 165 */           return "startY";
/*     */         }
/*     */       };
/*     */     }
/* 169 */     return this.startY;
/*     */   }
/*     */ 
/*     */   public final void setControlX(double paramDouble)
/*     */   {
/* 198 */     this.controlX.set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getControlX() {
/* 202 */     return this.controlX.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty controlXProperty() {
/* 206 */     return this.controlX;
/*     */   }
/*     */ 
/*     */   public final void setControlY(double paramDouble)
/*     */   {
/* 236 */     this.controlY.set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getControlY() {
/* 240 */     return this.controlY.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty controlYProperty() {
/* 244 */     return this.controlY;
/*     */   }
/*     */ 
/*     */   public final void setEndX(double paramDouble)
/*     */   {
/* 257 */     if ((this.endX != null) || (paramDouble != 0.0D))
/* 258 */       endXProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getEndX()
/*     */   {
/* 263 */     return this.endX == null ? 0.0D : this.endX.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty endXProperty() {
/* 267 */     if (this.endX == null) {
/* 268 */       this.endX = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 272 */           QuadCurve.this.impl_markDirty(DirtyBits.NODE_GEOMETRY);
/* 273 */           QuadCurve.this.impl_geomChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 278 */           return QuadCurve.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 283 */           return "endX";
/*     */         }
/*     */       };
/*     */     }
/* 287 */     return this.endX;
/*     */   }
/*     */ 
/*     */   public final void setEndY(double paramDouble)
/*     */   {
/* 299 */     if ((this.endY != null) || (paramDouble != 0.0D))
/* 300 */       endYProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getEndY()
/*     */   {
/* 305 */     return this.endY == null ? 0.0D : this.endY.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty endYProperty() {
/* 309 */     if (this.endY == null) {
/* 310 */       this.endY = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 314 */           QuadCurve.this.impl_markDirty(DirtyBits.NODE_GEOMETRY);
/* 315 */           QuadCurve.this.impl_geomChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 320 */           return QuadCurve.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 325 */           return "endY";
/*     */         }
/*     */       };
/*     */     }
/* 329 */     return this.endY;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   protected PGNode impl_createPGNode()
/*     */   {
/* 339 */     return Toolkit.getToolkit().createPGQuadCurve();
/*     */   }
/*     */ 
/*     */   PGQuadCurve getPGQuadCurve() {
/* 343 */     return (PGQuadCurve)impl_getPGNode();
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public QuadCurve2D impl_configShape()
/*     */   {
/* 353 */     this.shape.x1 = ((float)getStartX());
/* 354 */     this.shape.y1 = ((float)getStartY());
/* 355 */     this.shape.ctrlx = ((float)getControlX());
/* 356 */     this.shape.ctrly = ((float)getControlY());
/* 357 */     this.shape.x2 = ((float)getEndX());
/* 358 */     this.shape.y2 = ((float)getEndY());
/* 359 */     return this.shape;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void impl_updatePG()
/*     */   {
/* 369 */     super.impl_updatePG();
/*     */ 
/* 371 */     if (impl_isDirty(DirtyBits.NODE_GEOMETRY)) {
/* 372 */       PGQuadCurve localPGQuadCurve = getPGQuadCurve();
/* 373 */       localPGQuadCurve.updateQuadCurve((float)getStartX(), (float)getStartY(), (float)getEndX(), (float)getEndY(), (float)getControlX(), (float)getControlY());
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.shape.QuadCurve
 * JD-Core Version:    0.6.2
 */