/*     */ package javafx.scene.shape;
/*     */ 
/*     */ import com.sun.javafx.geom.Path2D;
/*     */ import com.sun.javafx.sg.PGPath;
/*     */ import javafx.beans.property.DoubleProperty;
/*     */ import javafx.beans.property.DoublePropertyBase;
/*     */ 
/*     */ public class QuadCurveTo extends PathElement
/*     */ {
/*  97 */   private DoubleProperty controlX = new DoublePropertyBase()
/*     */   {
/*     */     public void invalidated()
/*     */     {
/* 101 */       QuadCurveTo.this.u();
/*     */     }
/*     */ 
/*     */     public Object getBean()
/*     */     {
/* 106 */       return QuadCurveTo.this;
/*     */     }
/*     */ 
/*     */     public String getName()
/*     */     {
/* 111 */       return "controlX";
/*     */     }
/*  97 */   };
/*     */ 
/* 133 */   private DoubleProperty controlY = new DoublePropertyBase()
/*     */   {
/*     */     public void invalidated()
/*     */     {
/* 137 */       QuadCurveTo.this.u();
/*     */     }
/*     */ 
/*     */     public Object getBean()
/*     */     {
/* 142 */       return QuadCurveTo.this;
/*     */     }
/*     */ 
/*     */     public String getName()
/*     */     {
/* 147 */       return "controlY";
/*     */     }
/* 133 */   };
/*     */   private DoubleProperty x;
/*     */   private DoubleProperty y;
/*     */ 
/*     */   public QuadCurveTo()
/*     */   {
/*     */   }
/*     */ 
/*     */   public QuadCurveTo(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*     */   {
/*  86 */     setControlX(paramDouble1);
/*  87 */     setControlY(paramDouble2);
/*  88 */     setX(paramDouble3);
/*  89 */     setY(paramDouble4);
/*     */   }
/*     */ 
/*     */   public final void setControlX(double paramDouble)
/*     */   {
/* 117 */     this.controlX.set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getControlX() {
/* 121 */     return this.controlX.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty controlXProperty() {
/* 125 */     return this.controlX;
/*     */   }
/*     */ 
/*     */   public final void setControlY(double paramDouble)
/*     */   {
/* 153 */     this.controlY.set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getControlY() {
/* 157 */     return this.controlY.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty controlYProperty() {
/* 161 */     return this.controlY;
/*     */   }
/*     */ 
/*     */   public final void setX(double paramDouble)
/*     */   {
/* 172 */     if ((this.x != null) || (paramDouble != 0.0D))
/* 173 */       xProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getX()
/*     */   {
/* 178 */     return this.x == null ? 0.0D : this.x.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty xProperty() {
/* 182 */     if (this.x == null) {
/* 183 */       this.x = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 187 */           QuadCurveTo.this.u();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 192 */           return QuadCurveTo.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 197 */           return "x";
/*     */         }
/*     */       };
/*     */     }
/* 201 */     return this.x;
/*     */   }
/*     */ 
/*     */   public final void setY(double paramDouble)
/*     */   {
/* 212 */     if ((this.y != null) || (paramDouble != 0.0D))
/* 213 */       yProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getY()
/*     */   {
/* 218 */     return this.y == null ? 0.0D : this.y.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty yProperty() {
/* 222 */     if (this.y == null) {
/* 223 */       this.y = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 227 */           QuadCurveTo.this.u();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 232 */           return QuadCurveTo.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 237 */           return "y";
/*     */         }
/*     */       };
/*     */     }
/* 241 */     return this.y;
/*     */   }
/*     */ 
/*     */   void addTo(PGPath paramPGPath)
/*     */   {
/* 246 */     if (isAbsolute()) {
/* 247 */       paramPGPath.addQuadTo((float)getControlX(), (float)getControlY(), (float)getX(), (float)getY());
/*     */     }
/*     */     else
/*     */     {
/* 253 */       double d1 = paramPGPath.getCurrentX();
/* 254 */       double d2 = paramPGPath.getCurrentY();
/* 255 */       paramPGPath.addQuadTo((float)(getControlX() + d1), (float)(getControlY() + d2), (float)(getX() + d1), (float)(getY() + d2));
/*     */     }
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void impl_addTo(Path2D paramPath2D)
/*     */   {
/* 270 */     if (isAbsolute()) {
/* 271 */       paramPath2D.quadTo((float)getControlX(), (float)getControlY(), (float)getX(), (float)getY());
/*     */     }
/*     */     else
/*     */     {
/* 277 */       double d1 = paramPath2D.getCurrentX();
/* 278 */       double d2 = paramPath2D.getCurrentY();
/* 279 */       paramPath2D.quadTo((float)(getControlX() + d1), (float)(getControlY() + d2), (float)(getX() + d1), (float)(getY() + d2));
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.shape.QuadCurveTo
 * JD-Core Version:    0.6.2
 */