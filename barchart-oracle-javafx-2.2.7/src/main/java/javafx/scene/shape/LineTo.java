/*     */ package javafx.scene.shape;
/*     */ 
/*     */ import com.sun.javafx.geom.Path2D;
/*     */ import com.sun.javafx.sg.PGPath;
/*     */ import javafx.beans.property.DoubleProperty;
/*     */ import javafx.beans.property.DoublePropertyBase;
/*     */ 
/*     */ public class LineTo extends PathElement
/*     */ {
/*     */   private DoubleProperty x;
/*     */   private DoubleProperty y;
/*     */ 
/*     */   public LineTo()
/*     */   {
/*     */   }
/*     */ 
/*     */   public LineTo(double paramDouble1, double paramDouble2)
/*     */   {
/*  68 */     setX(paramDouble1);
/*  69 */     setY(paramDouble2);
/*     */   }
/*     */ 
/*     */   public final void setX(double paramDouble)
/*     */   {
/*  82 */     if ((this.x != null) || (paramDouble != 0.0D))
/*  83 */       xProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getX()
/*     */   {
/*  88 */     return this.x == null ? 0.0D : this.x.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty xProperty() {
/*  92 */     if (this.x == null) {
/*  93 */       this.x = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/*  97 */           LineTo.this.u();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 102 */           return LineTo.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 107 */           return "x";
/*     */         }
/*     */       };
/*     */     }
/* 111 */     return this.x;
/*     */   }
/*     */ 
/*     */   public final void setY(double paramDouble)
/*     */   {
/* 122 */     if ((this.y != null) || (paramDouble != 0.0D))
/* 123 */       yProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getY()
/*     */   {
/* 128 */     return this.y == null ? 0.0D : this.y.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty yProperty() {
/* 132 */     if (this.y == null) {
/* 133 */       this.y = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 137 */           LineTo.this.u();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 142 */           return LineTo.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 147 */           return "y";
/*     */         }
/*     */       };
/*     */     }
/* 151 */     return this.y;
/*     */   }
/*     */ 
/*     */   void addTo(PGPath paramPGPath)
/*     */   {
/* 156 */     if (isAbsolute())
/* 157 */       paramPGPath.addLineTo((float)getX(), (float)getY());
/*     */     else
/* 159 */       paramPGPath.addLineTo((float)(paramPGPath.getCurrentX() + getX()), (float)(paramPGPath.getCurrentY() + getY()));
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void impl_addTo(Path2D paramPath2D)
/*     */   {
/* 171 */     if (isAbsolute())
/* 172 */       paramPath2D.lineTo((float)getX(), (float)getY());
/*     */     else
/* 174 */       paramPath2D.lineTo((float)(paramPath2D.getCurrentX() + getX()), (float)(paramPath2D.getCurrentY() + getY()));
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.shape.LineTo
 * JD-Core Version:    0.6.2
 */