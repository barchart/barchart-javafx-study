/*     */ package javafx.scene.shape;
/*     */ 
/*     */ import com.sun.javafx.geom.Path2D;
/*     */ import com.sun.javafx.sg.PGPath;
/*     */ import javafx.beans.property.DoubleProperty;
/*     */ import javafx.beans.property.DoublePropertyBase;
/*     */ 
/*     */ public class HLineTo extends PathElement
/*     */ {
/*  74 */   private DoubleProperty x = new DoublePropertyBase()
/*     */   {
/*     */     public void invalidated()
/*     */     {
/*  78 */       HLineTo.this.u();
/*     */     }
/*     */ 
/*     */     public Object getBean()
/*     */     {
/*  83 */       return HLineTo.this;
/*     */     }
/*     */ 
/*     */     public String getName()
/*     */     {
/*  88 */       return "x";
/*     */     }
/*  74 */   };
/*     */ 
/*     */   public HLineTo()
/*     */   {
/*     */   }
/*     */ 
/*     */   public HLineTo(double paramDouble)
/*     */   {
/*  66 */     setX(paramDouble);
/*     */   }
/*     */ 
/*     */   public final void setX(double paramDouble)
/*     */   {
/*  94 */     this.x.set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getX() {
/*  98 */     return this.x.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty xProperty() {
/* 102 */     return this.x;
/*     */   }
/*     */ 
/*     */   void addTo(PGPath paramPGPath)
/*     */   {
/* 107 */     if (isAbsolute())
/* 108 */       paramPGPath.addLineTo((float)getX(), paramPGPath.getCurrentY());
/*     */     else
/* 110 */       paramPGPath.addLineTo((float)(paramPGPath.getCurrentX() + getX()), paramPGPath.getCurrentY());
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void impl_addTo(Path2D paramPath2D)
/*     */   {
/* 121 */     if (isAbsolute())
/* 122 */       paramPath2D.lineTo((float)getX(), paramPath2D.getCurrentY());
/*     */     else
/* 124 */       paramPath2D.lineTo((float)(paramPath2D.getCurrentX() + getX()), paramPath2D.getCurrentY());
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.shape.HLineTo
 * JD-Core Version:    0.6.2
 */