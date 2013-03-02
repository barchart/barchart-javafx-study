/*     */ package javafx.scene.transform;
/*     */ 
/*     */ import com.sun.javafx.geom.transform.Affine3D;
/*     */ import javafx.beans.property.DoubleProperty;
/*     */ import javafx.beans.property.DoublePropertyBase;
/*     */ 
/*     */ public class Scale extends Transform
/*     */ {
/*     */   private DoubleProperty x;
/*     */   private DoubleProperty y;
/*     */   private DoubleProperty z;
/*     */   private DoubleProperty pivotX;
/*     */   private DoubleProperty pivotY;
/*     */   private DoubleProperty pivotZ;
/*     */ 
/*     */   public Scale()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Scale(double paramDouble1, double paramDouble2)
/*     */   {
/*  56 */     setX(paramDouble1);
/*  57 */     setY(paramDouble2);
/*     */   }
/*     */ 
/*     */   public Scale(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
/*     */   {
/*  68 */     this(paramDouble1, paramDouble2);
/*  69 */     setPivotX(paramDouble3);
/*  70 */     setPivotY(paramDouble4);
/*     */   }
/*     */ 
/*     */   public Scale(double paramDouble1, double paramDouble2, double paramDouble3)
/*     */   {
/*  80 */     this(paramDouble1, paramDouble2);
/*  81 */     setZ(paramDouble3);
/*     */   }
/*     */ 
/*     */   public Scale(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6)
/*     */   {
/*  94 */     this(paramDouble1, paramDouble2, paramDouble4, paramDouble5);
/*  95 */     setZ(paramDouble3);
/*  96 */     setPivotZ(paramDouble6);
/*     */   }
/*     */ 
/*     */   public final void setX(double paramDouble)
/*     */   {
/* 107 */     xProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getX() {
/* 111 */     return this.x == null ? 1.0D : this.x.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty xProperty() {
/* 115 */     if (this.x == null) {
/* 116 */       this.x = new DoublePropertyBase(1.0D)
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 120 */           Scale.this.transformChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 125 */           return Scale.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 130 */           return "x";
/*     */         }
/*     */       };
/*     */     }
/* 134 */     return this.x;
/*     */   }
/*     */ 
/*     */   public final void setY(double paramDouble)
/*     */   {
/* 145 */     yProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getY() {
/* 149 */     return this.y == null ? 1.0D : this.y.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty yProperty() {
/* 153 */     if (this.y == null) {
/* 154 */       this.y = new DoublePropertyBase(1.0D)
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 158 */           Scale.this.transformChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 163 */           return Scale.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 168 */           return "y";
/*     */         }
/*     */       };
/*     */     }
/* 172 */     return this.y;
/*     */   }
/*     */ 
/*     */   public final void setZ(double paramDouble)
/*     */   {
/* 183 */     zProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getZ() {
/* 187 */     return this.z == null ? 1.0D : this.z.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty zProperty() {
/* 191 */     if (this.z == null) {
/* 192 */       this.z = new DoublePropertyBase(1.0D)
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 196 */           Scale.this.transformChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 201 */           return Scale.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 206 */           return "z";
/*     */         }
/*     */       };
/*     */     }
/* 210 */     return this.z;
/*     */   }
/*     */ 
/*     */   public final void setPivotX(double paramDouble)
/*     */   {
/* 220 */     pivotXProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getPivotX() {
/* 224 */     return this.pivotX == null ? 0.0D : this.pivotX.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty pivotXProperty() {
/* 228 */     if (this.pivotX == null) {
/* 229 */       this.pivotX = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 233 */           Scale.this.transformChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 238 */           return Scale.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 243 */           return "pivotX";
/*     */         }
/*     */       };
/*     */     }
/* 247 */     return this.pivotX;
/*     */   }
/*     */ 
/*     */   public final void setPivotY(double paramDouble)
/*     */   {
/* 257 */     pivotYProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getPivotY() {
/* 261 */     return this.pivotY == null ? 0.0D : this.pivotY.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty pivotYProperty() {
/* 265 */     if (this.pivotY == null) {
/* 266 */       this.pivotY = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 270 */           Scale.this.transformChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 275 */           return Scale.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 280 */           return "pivotY";
/*     */         }
/*     */       };
/*     */     }
/* 284 */     return this.pivotY;
/*     */   }
/*     */ 
/*     */   public final void setPivotZ(double paramDouble)
/*     */   {
/* 294 */     pivotZProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getPivotZ() {
/* 298 */     return this.pivotZ == null ? 0.0D : this.pivotZ.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty pivotZProperty() {
/* 302 */     if (this.pivotZ == null) {
/* 303 */       this.pivotZ = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 307 */           Scale.this.transformChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 312 */           return Scale.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 317 */           return "pivotZ";
/*     */         }
/*     */       };
/*     */     }
/* 321 */     return this.pivotZ;
/*     */   }
/*     */ 
/*     */   public double getMxx()
/*     */   {
/* 326 */     return getX();
/*     */   }
/*     */ 
/*     */   public double getMyy()
/*     */   {
/* 331 */     return getY();
/*     */   }
/*     */ 
/*     */   public double getMzz()
/*     */   {
/* 336 */     return getZ();
/*     */   }
/*     */ 
/*     */   public double getTx()
/*     */   {
/* 341 */     return (1.0D - getX()) * getPivotX();
/*     */   }
/*     */ 
/*     */   public double getTy()
/*     */   {
/* 346 */     return (1.0D - getY()) * getPivotY();
/*     */   }
/*     */ 
/*     */   public double getTz()
/*     */   {
/* 351 */     return (1.0D - getZ()) * getPivotZ();
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void impl_apply(Affine3D paramAffine3D)
/*     */   {
/* 363 */     if ((getPivotX() != 0.0D) || (getPivotY() != 0.0D) || (getPivotZ() != 0.0D)) {
/* 364 */       paramAffine3D.translate(getPivotX(), getPivotY(), getPivotZ());
/* 365 */       paramAffine3D.scale(getX(), getY(), getZ());
/* 366 */       paramAffine3D.translate(-getPivotX(), -getPivotY(), -getPivotZ());
/*     */     } else {
/* 368 */       paramAffine3D.scale(getX(), getY(), getZ());
/*     */     }
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public Transform impl_copy()
/*     */   {
/* 379 */     return new Scale(getX(), getY(), getZ(), getPivotX(), getPivotY(), getPivotZ());
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 389 */     StringBuilder localStringBuilder = new StringBuilder("Scale [");
/*     */ 
/* 391 */     localStringBuilder.append("x=").append(getX());
/* 392 */     localStringBuilder.append(", y=").append(getY());
/* 393 */     localStringBuilder.append(", z=").append(getZ());
/* 394 */     localStringBuilder.append(", pivotX=").append(getPivotX());
/* 395 */     localStringBuilder.append(", pivotY=").append(getPivotY());
/* 396 */     localStringBuilder.append(", pivotZ=").append(getPivotZ());
/*     */ 
/* 398 */     return "]";
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.transform.Scale
 * JD-Core Version:    0.6.2
 */