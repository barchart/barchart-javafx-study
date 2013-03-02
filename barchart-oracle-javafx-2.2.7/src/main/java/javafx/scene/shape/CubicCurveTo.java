/*     */ package javafx.scene.shape;
/*     */ 
/*     */ import com.sun.javafx.geom.Path2D;
/*     */ import com.sun.javafx.sg.PGPath;
/*     */ import javafx.beans.property.DoubleProperty;
/*     */ import javafx.beans.property.DoublePropertyBase;
/*     */ 
/*     */ public class CubicCurveTo extends PathElement
/*     */ {
/*     */   private DoubleProperty controlX1;
/*     */   private DoubleProperty controlY1;
/*     */   private DoubleProperty controlX2;
/*     */   private DoubleProperty controlY2;
/*     */   private DoubleProperty x;
/*     */   private DoubleProperty y;
/*     */ 
/*     */   public CubicCurveTo()
/*     */   {
/*     */   }
/*     */ 
/*     */   public CubicCurveTo(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6)
/*     */   {
/*  90 */     setControlX1(paramDouble1);
/*  91 */     setControlY1(paramDouble2);
/*  92 */     setControlX2(paramDouble3);
/*  93 */     setControlY2(paramDouble4);
/*  94 */     setX(paramDouble5);
/*  95 */     setY(paramDouble6);
/*     */   }
/*     */ 
/*     */   public final void setControlX1(double paramDouble)
/*     */   {
/* 107 */     if ((this.controlX1 != null) || (paramDouble != 0.0D))
/* 108 */       controlX1Property().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getControlX1()
/*     */   {
/* 113 */     return this.controlX1 == null ? 0.0D : this.controlX1.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty controlX1Property() {
/* 117 */     if (this.controlX1 == null) {
/* 118 */       this.controlX1 = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 122 */           CubicCurveTo.this.u();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 127 */           return CubicCurveTo.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 132 */           return "controlX1";
/*     */         }
/*     */       };
/*     */     }
/* 136 */     return this.controlX1;
/*     */   }
/*     */ 
/*     */   public final void setControlY1(double paramDouble)
/*     */   {
/* 148 */     if ((this.controlY1 != null) || (paramDouble != 0.0D))
/* 149 */       controlY1Property().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getControlY1()
/*     */   {
/* 154 */     return this.controlY1 == null ? 0.0D : this.controlY1.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty controlY1Property() {
/* 158 */     if (this.controlY1 == null) {
/* 159 */       this.controlY1 = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 163 */           CubicCurveTo.this.u();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 168 */           return CubicCurveTo.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 173 */           return "controlY1";
/*     */         }
/*     */       };
/*     */     }
/* 177 */     return this.controlY1;
/*     */   }
/*     */ 
/*     */   public final void setControlX2(double paramDouble)
/*     */   {
/* 189 */     if ((this.controlX2 != null) || (paramDouble != 0.0D))
/* 190 */       controlX2Property().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getControlX2()
/*     */   {
/* 195 */     return this.controlX2 == null ? 0.0D : this.controlX2.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty controlX2Property() {
/* 199 */     if (this.controlX2 == null) {
/* 200 */       this.controlX2 = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 204 */           CubicCurveTo.this.u();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 209 */           return CubicCurveTo.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 214 */           return "controlX2";
/*     */         }
/*     */       };
/*     */     }
/* 218 */     return this.controlX2;
/*     */   }
/*     */ 
/*     */   public final void setControlY2(double paramDouble)
/*     */   {
/* 230 */     if ((this.controlY2 != null) || (paramDouble != 0.0D))
/* 231 */       controlY2Property().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getControlY2()
/*     */   {
/* 236 */     return this.controlY2 == null ? 0.0D : this.controlY2.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty controlY2Property() {
/* 240 */     if (this.controlY2 == null) {
/* 241 */       this.controlY2 = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 245 */           CubicCurveTo.this.u();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 250 */           return CubicCurveTo.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 255 */           return "controlY2";
/*     */         }
/*     */       };
/*     */     }
/* 259 */     return this.controlY2;
/*     */   }
/*     */ 
/*     */   public final void setX(double paramDouble)
/*     */   {
/* 270 */     if ((this.x != null) || (paramDouble != 0.0D))
/* 271 */       xProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getX()
/*     */   {
/* 276 */     return this.x == null ? 0.0D : this.x.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty xProperty() {
/* 280 */     if (this.x == null) {
/* 281 */       this.x = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 285 */           CubicCurveTo.this.u();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 290 */           return CubicCurveTo.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 295 */           return "x";
/*     */         }
/*     */       };
/*     */     }
/* 299 */     return this.x;
/*     */   }
/*     */ 
/*     */   public final void setY(double paramDouble)
/*     */   {
/* 311 */     if ((this.y != null) || (paramDouble != 0.0D))
/* 312 */       yProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getY()
/*     */   {
/* 317 */     return this.y == null ? 0.0D : this.y.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty yProperty() {
/* 321 */     if (this.y == null) {
/* 322 */       this.y = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 326 */           CubicCurveTo.this.u();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 331 */           return CubicCurveTo.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 336 */           return "y";
/*     */         }
/*     */       };
/*     */     }
/* 340 */     return this.y;
/*     */   }
/*     */ 
/*     */   void addTo(PGPath paramPGPath)
/*     */   {
/* 348 */     if (isAbsolute()) {
/* 349 */       paramPGPath.addCubicTo((float)getControlX1(), (float)getControlY1(), (float)getControlX2(), (float)getControlY2(), (float)getX(), (float)getY());
/*     */     }
/*     */     else
/*     */     {
/* 353 */       double d1 = paramPGPath.getCurrentX();
/* 354 */       double d2 = paramPGPath.getCurrentY();
/* 355 */       paramPGPath.addCubicTo((float)(getControlX1() + d1), (float)(getControlY1() + d2), (float)(getControlX2() + d1), (float)(getControlY2() + d2), (float)(getX() + d1), (float)(getY() + d2));
/*     */     }
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void impl_addTo(Path2D paramPath2D)
/*     */   {
/* 367 */     if (isAbsolute()) {
/* 368 */       paramPath2D.curveTo((float)getControlX1(), (float)getControlY1(), (float)getControlX2(), (float)getControlY2(), (float)getX(), (float)getY());
/*     */     }
/*     */     else
/*     */     {
/* 372 */       double d1 = paramPath2D.getCurrentX();
/* 373 */       double d2 = paramPath2D.getCurrentY();
/* 374 */       paramPath2D.curveTo((float)(getControlX1() + d1), (float)(getControlY1() + d2), (float)(getControlX2() + d1), (float)(getControlY2() + d2), (float)(getX() + d1), (float)(getY() + d2));
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.shape.CubicCurveTo
 * JD-Core Version:    0.6.2
 */