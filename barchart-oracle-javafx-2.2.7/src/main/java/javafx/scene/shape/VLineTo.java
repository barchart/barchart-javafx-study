/*     */ package javafx.scene.shape;
/*     */ 
/*     */ import com.sun.javafx.geom.Path2D;
/*     */ import com.sun.javafx.sg.PGPath;
/*     */ import javafx.beans.property.DoubleProperty;
/*     */ import javafx.beans.property.DoublePropertyBase;
/*     */ 
/*     */ public class VLineTo extends PathElement
/*     */ {
/*  74 */   private DoubleProperty y = new DoublePropertyBase()
/*     */   {
/*     */     public void invalidated()
/*     */     {
/*  78 */       VLineTo.this.u();
/*     */     }
/*     */ 
/*     */     public Object getBean()
/*     */     {
/*  83 */       return VLineTo.this;
/*     */     }
/*     */ 
/*     */     public String getName()
/*     */     {
/*  88 */       return "y";
/*     */     }
/*  74 */   };
/*     */ 
/*     */   public VLineTo()
/*     */   {
/*     */   }
/*     */ 
/*     */   public VLineTo(double paramDouble)
/*     */   {
/*  66 */     setY(paramDouble);
/*     */   }
/*     */ 
/*     */   public final void setY(double paramDouble)
/*     */   {
/*  94 */     this.y.set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getY() {
/*  98 */     return this.y.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty yProperty() {
/* 102 */     return this.y;
/*     */   }
/*     */ 
/*     */   void addTo(PGPath paramPGPath)
/*     */   {
/* 107 */     if (isAbsolute())
/* 108 */       paramPGPath.addLineTo(paramPGPath.getCurrentX(), (float)getY());
/*     */     else
/* 110 */       paramPGPath.addLineTo(paramPGPath.getCurrentX(), (float)(paramPGPath.getCurrentY() + getY()));
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void impl_addTo(Path2D paramPath2D)
/*     */   {
/* 121 */     if (isAbsolute())
/* 122 */       paramPath2D.lineTo(paramPath2D.getCurrentX(), (float)getY());
/*     */     else
/* 124 */       paramPath2D.lineTo(paramPath2D.getCurrentX(), (float)(paramPath2D.getCurrentY() + getY()));
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.shape.VLineTo
 * JD-Core Version:    0.6.2
 */