/*     */ package javafx.scene.transform;
/*     */ 
/*     */ import com.sun.javafx.geom.transform.Affine3D;
/*     */ import javafx.beans.property.DoubleProperty;
/*     */ import javafx.beans.property.DoublePropertyBase;
/*     */ 
/*     */ public class Translate extends Transform
/*     */ {
/*     */   private DoubleProperty x;
/*     */   private DoubleProperty y;
/*     */   private DoubleProperty z;
/*     */ 
/*     */   public Translate()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Translate(double paramDouble1, double paramDouble2)
/*     */   {
/*  60 */     setX(paramDouble1);
/*  61 */     setY(paramDouble2);
/*     */   }
/*     */ 
/*     */   public Translate(double paramDouble1, double paramDouble2, double paramDouble3)
/*     */   {
/*  74 */     this(paramDouble1, paramDouble2);
/*  75 */     setZ(paramDouble3);
/*     */   }
/*     */ 
/*     */   public final void setX(double paramDouble)
/*     */   {
/*  86 */     xProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getX() {
/*  90 */     return this.x == null ? 0.0D : this.x.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty xProperty() {
/*  94 */     if (this.x == null) {
/*  95 */       this.x = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/*  99 */           Translate.this.transformChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 104 */           return Translate.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 109 */           return "x";
/*     */         }
/*     */       };
/*     */     }
/* 113 */     return this.x;
/*     */   }
/*     */ 
/*     */   public final void setY(double paramDouble)
/*     */   {
/* 124 */     yProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getY() {
/* 128 */     return this.y == null ? 0.0D : this.y.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty yProperty() {
/* 132 */     if (this.y == null) {
/* 133 */       this.y = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 137 */           Translate.this.transformChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 142 */           return Translate.this;
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
/*     */   public final void setZ(double paramDouble)
/*     */   {
/* 162 */     zProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getZ() {
/* 166 */     return this.z == null ? 0.0D : this.z.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty zProperty() {
/* 170 */     if (this.z == null) {
/* 171 */       this.z = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 175 */           Translate.this.transformChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 180 */           return Translate.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 185 */           return "z";
/*     */         }
/*     */       };
/*     */     }
/* 189 */     return this.z;
/*     */   }
/*     */ 
/*     */   public double getTx()
/*     */   {
/* 194 */     return getX();
/*     */   }
/*     */ 
/*     */   public double getTy()
/*     */   {
/* 199 */     return getY();
/*     */   }
/*     */ 
/*     */   public double getTz()
/*     */   {
/* 204 */     return getZ();
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void impl_apply(Affine3D paramAffine3D)
/*     */   {
/* 214 */     paramAffine3D.translate(getX(), getY(), getZ());
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public Transform impl_copy()
/*     */   {
/* 224 */     return new Translate(getX(), getY(), getZ());
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 233 */     StringBuilder localStringBuilder = new StringBuilder("Translate [");
/*     */ 
/* 235 */     localStringBuilder.append("x=").append(getX());
/* 236 */     localStringBuilder.append(", y=").append(getY());
/* 237 */     localStringBuilder.append(", z=").append(getZ());
/*     */ 
/* 239 */     return "]";
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.transform.Translate
 * JD-Core Version:    0.6.2
 */