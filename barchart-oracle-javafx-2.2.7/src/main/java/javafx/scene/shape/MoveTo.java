/*     */ package javafx.scene.shape;
/*     */ 
/*     */ import com.sun.javafx.geom.Path2D;
/*     */ import com.sun.javafx.sg.PGPath;
/*     */ import javafx.beans.property.DoubleProperty;
/*     */ import javafx.beans.property.DoublePropertyBase;
/*     */ 
/*     */ public class MoveTo extends PathElement
/*     */ {
/*     */   private DoubleProperty x;
/*     */   private DoubleProperty y;
/*     */ 
/*     */   public MoveTo()
/*     */   {
/*     */   }
/*     */ 
/*     */   public MoveTo(double paramDouble1, double paramDouble2)
/*     */   {
/*  68 */     setX(paramDouble1);
/*  69 */     setY(paramDouble2);
/*     */   }
/*     */ 
/*     */   public final void setX(double paramDouble)
/*     */   {
/*  80 */     if ((this.x != null) || (paramDouble != 0.0D))
/*  81 */       xProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getX()
/*     */   {
/*  86 */     return this.x == null ? 0.0D : this.x.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty xProperty() {
/*  90 */     if (this.x == null) {
/*  91 */       this.x = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/*  95 */           MoveTo.this.u();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 100 */           return MoveTo.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 105 */           return "x";
/*     */         }
/*     */       };
/*     */     }
/* 109 */     return this.x;
/*     */   }
/*     */ 
/*     */   public final void setY(double paramDouble)
/*     */   {
/* 120 */     if ((this.y != null) || (paramDouble != 0.0D))
/* 121 */       yProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getY()
/*     */   {
/* 126 */     return this.y == null ? 0.0D : this.y.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty yProperty() {
/* 130 */     if (this.y == null) {
/* 131 */       this.y = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 135 */           MoveTo.this.u();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 140 */           return MoveTo.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 145 */           return "y";
/*     */         }
/*     */       };
/*     */     }
/* 149 */     return this.y;
/*     */   }
/*     */ 
/*     */   void addTo(PGPath paramPGPath)
/*     */   {
/* 154 */     if (isAbsolute())
/* 155 */       paramPGPath.addMoveTo((float)getX(), (float)getY());
/*     */     else
/* 157 */       paramPGPath.addMoveTo((float)(paramPGPath.getCurrentX() + getX()), (float)(paramPGPath.getCurrentY() + getY()));
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void impl_addTo(Path2D paramPath2D)
/*     */   {
/* 169 */     if (isAbsolute())
/* 170 */       paramPath2D.moveTo((float)getX(), (float)getY());
/*     */     else
/* 172 */       paramPath2D.moveTo((float)(paramPath2D.getCurrentX() + getX()), (float)(paramPath2D.getCurrentY() + getY()));
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.shape.MoveTo
 * JD-Core Version:    0.6.2
 */