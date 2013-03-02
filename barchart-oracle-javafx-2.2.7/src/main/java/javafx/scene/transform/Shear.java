/*     */ package javafx.scene.transform;
/*     */ 
/*     */ import com.sun.javafx.geom.transform.Affine3D;
/*     */ import javafx.beans.property.DoubleProperty;
/*     */ import javafx.beans.property.DoublePropertyBase;
/*     */ 
/*     */ public class Shear extends Transform
/*     */ {
/*     */   private DoubleProperty x;
/*     */   private DoubleProperty y;
/*     */   private DoubleProperty pivotX;
/*     */   private DoubleProperty pivotY;
/*     */ 
/*     */   public Shear()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Shear(double paramDouble1, double paramDouble2)
/*     */   {
/*  71 */     setX(paramDouble1);
/*  72 */     setY(paramDouble2);
/*     */   }
/*     */ 
/*     */   public Shear(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*     */   {
/*  85 */     setX(paramDouble1);
/*  86 */     setY(paramDouble2);
/*  87 */     setPivotX(paramDouble3);
/*  88 */     setPivotY(paramDouble4);
/*     */   }
/*     */ 
/*     */   public final void setX(double paramDouble)
/*     */   {
/* 102 */     xProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getX() {
/* 106 */     return this.x == null ? 0.0D : this.x.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty xProperty() {
/* 110 */     if (this.x == null) {
/* 111 */       this.x = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 115 */           Shear.this.transformChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 120 */           return Shear.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 125 */           return "x";
/*     */         }
/*     */       };
/*     */     }
/* 129 */     return this.x;
/*     */   }
/*     */ 
/*     */   public final void setY(double paramDouble)
/*     */   {
/* 143 */     yProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getY() {
/* 147 */     return this.y == null ? 0.0D : this.y.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty yProperty() {
/* 151 */     if (this.y == null) {
/* 152 */       this.y = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 156 */           Shear.this.transformChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 161 */           return Shear.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 166 */           return "y";
/*     */         }
/*     */       };
/*     */     }
/* 170 */     return this.y;
/*     */   }
/*     */ 
/*     */   public final void setPivotX(double paramDouble)
/*     */   {
/* 180 */     pivotXProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getPivotX() {
/* 184 */     return this.pivotX == null ? 0.0D : this.pivotX.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty pivotXProperty() {
/* 188 */     if (this.pivotX == null) {
/* 189 */       this.pivotX = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 193 */           Shear.this.transformChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 198 */           return Shear.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 203 */           return "pivotX";
/*     */         }
/*     */       };
/*     */     }
/* 207 */     return this.pivotX;
/*     */   }
/*     */ 
/*     */   public final void setPivotY(double paramDouble)
/*     */   {
/* 217 */     pivotYProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getPivotY() {
/* 221 */     return this.pivotY == null ? 0.0D : this.pivotY.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty pivotYProperty() {
/* 225 */     if (this.pivotY == null) {
/* 226 */       this.pivotY = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 230 */           Shear.this.transformChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 235 */           return Shear.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 240 */           return "pivotY";
/*     */         }
/*     */       };
/*     */     }
/* 244 */     return this.pivotY;
/*     */   }
/*     */ 
/*     */   public double getMxy()
/*     */   {
/* 249 */     return getX();
/*     */   }
/*     */ 
/*     */   public double getMyx()
/*     */   {
/* 254 */     return getY();
/*     */   }
/*     */ 
/*     */   public double getTx()
/*     */   {
/* 259 */     return -getX() * getPivotY();
/*     */   }
/*     */ 
/*     */   public double getTy()
/*     */   {
/* 264 */     return -getY() * getPivotX();
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void impl_apply(Affine3D paramAffine3D)
/*     */   {
/* 274 */     if ((getPivotX() != 0.0D) || (getPivotY() != 0.0D)) {
/* 275 */       paramAffine3D.translate(getPivotX(), getPivotY());
/* 276 */       paramAffine3D.shear(getX(), getY());
/* 277 */       paramAffine3D.translate(-getPivotX(), -getPivotY());
/*     */     } else {
/* 279 */       paramAffine3D.shear(getX(), getY());
/*     */     }
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public Transform impl_copy()
/*     */   {
/* 290 */     return new Shear(getX(), getY(), getPivotX(), getPivotY());
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 299 */     StringBuilder localStringBuilder = new StringBuilder("Shear [");
/*     */ 
/* 301 */     localStringBuilder.append("x=").append(getX());
/* 302 */     localStringBuilder.append(", y=").append(getY());
/* 303 */     localStringBuilder.append(", pivotX=").append(getPivotX());
/* 304 */     localStringBuilder.append(", pivotY=").append(getPivotY());
/*     */ 
/* 306 */     return "]";
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.transform.Shear
 * JD-Core Version:    0.6.2
 */