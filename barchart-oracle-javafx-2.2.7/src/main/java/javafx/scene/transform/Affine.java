/*     */ package javafx.scene.transform;
/*     */ 
/*     */ import com.sun.javafx.geom.transform.Affine3D;
/*     */ import javafx.beans.property.DoubleProperty;
/*     */ import javafx.beans.property.DoublePropertyBase;
/*     */ 
/*     */ public class Affine extends Transform
/*     */ {
/*     */   private DoubleProperty mxx;
/*     */   private DoubleProperty mxy;
/*     */   private DoubleProperty mxz;
/*     */   private DoubleProperty tx;
/*     */   private DoubleProperty myx;
/*     */   private DoubleProperty myy;
/*     */   private DoubleProperty myz;
/*     */   private DoubleProperty ty;
/*     */   private DoubleProperty mzx;
/*     */   private DoubleProperty mzy;
/*     */   private DoubleProperty mzz;
/*     */   private DoubleProperty tz;
/*     */ 
/*     */   public Affine()
/*     */   {
/*     */   }
/*     */ 
/*     */   Affine(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, double paramDouble9, double paramDouble10, double paramDouble11, double paramDouble12)
/*     */   {
/*  72 */     if (paramDouble1 != 1.0D) mxxProperty().set(paramDouble1);
/*  73 */     if (paramDouble2 != 0.0D) mxyProperty().set(paramDouble2);
/*  74 */     if (paramDouble3 != 0.0D) mxzProperty().set(paramDouble3);
/*  75 */     if (paramDouble4 != 0.0D) txProperty().set(paramDouble4);
/*  76 */     if (paramDouble5 != 0.0D) myxProperty().set(paramDouble5);
/*  77 */     if (paramDouble6 != 1.0D) myyProperty().set(paramDouble6);
/*  78 */     if (paramDouble7 != 0.0D) myzProperty().set(paramDouble7);
/*  79 */     if (paramDouble8 != 0.0D) tyProperty().set(paramDouble8);
/*  80 */     if (paramDouble9 != 0.0D) mzxProperty().set(paramDouble9);
/*  81 */     if (paramDouble10 != 0.0D) mzyProperty().set(paramDouble10);
/*  82 */     if (paramDouble11 != 1.0D) mzzProperty().set(paramDouble11);
/*  83 */     if (paramDouble12 != 0.0D) tzProperty().set(paramDouble12);
/*     */   }
/*     */ 
/*     */   void setTransform(Transform paramTransform)
/*     */   {
/*  88 */     setMxx(paramTransform.getMxx());
/*  89 */     setMxy(paramTransform.getMxy());
/*  90 */     setMxz(paramTransform.getMxz());
/*  91 */     setTx(paramTransform.getTx());
/*  92 */     setMyx(paramTransform.getMyx());
/*  93 */     setMyy(paramTransform.getMyy());
/*  94 */     setMyz(paramTransform.getMyz());
/*  95 */     setTy(paramTransform.getTy());
/*  96 */     setMzx(paramTransform.getMzx());
/*  97 */     setMzy(paramTransform.getMzy());
/*  98 */     setMzz(paramTransform.getMzz());
/*  99 */     setTz(paramTransform.getTz());
/*     */   }
/*     */ 
/*     */   public final void setMxx(double paramDouble)
/*     */   {
/* 109 */     mxxProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getMxx() {
/* 113 */     return this.mxx == null ? 1.0D : this.mxx.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty mxxProperty() {
/* 117 */     if (this.mxx == null) {
/* 118 */       this.mxx = new DoublePropertyBase(1.0D)
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 122 */           Affine.this.transformChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 127 */           return Affine.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 132 */           return "mxx";
/*     */         }
/*     */       };
/*     */     }
/* 136 */     return this.mxx;
/*     */   }
/*     */ 
/*     */   public final void setMxy(double paramDouble)
/*     */   {
/* 146 */     mxyProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getMxy() {
/* 150 */     return this.mxy == null ? 0.0D : this.mxy.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty mxyProperty() {
/* 154 */     if (this.mxy == null) {
/* 155 */       this.mxy = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 159 */           Affine.this.transformChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 164 */           return Affine.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 169 */           return "mxy";
/*     */         }
/*     */       };
/*     */     }
/* 173 */     return this.mxy;
/*     */   }
/*     */ 
/*     */   public final void setMxz(double paramDouble)
/*     */   {
/* 183 */     mxzProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getMxz() {
/* 187 */     return this.mxz == null ? 0.0D : this.mxz.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty mxzProperty() {
/* 191 */     if (this.mxz == null) {
/* 192 */       this.mxz = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 196 */           Affine.this.transformChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 201 */           return Affine.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 206 */           return "mxz";
/*     */         }
/*     */       };
/*     */     }
/* 210 */     return this.mxz;
/*     */   }
/*     */ 
/*     */   public final void setTx(double paramDouble)
/*     */   {
/* 220 */     txProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getTx() {
/* 224 */     return this.tx == null ? 0.0D : this.tx.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty txProperty() {
/* 228 */     if (this.tx == null) {
/* 229 */       this.tx = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 233 */           Affine.this.transformChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 238 */           return Affine.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 243 */           return "tx";
/*     */         }
/*     */       };
/*     */     }
/* 247 */     return this.tx;
/*     */   }
/*     */ 
/*     */   public final void setMyx(double paramDouble)
/*     */   {
/* 257 */     myxProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getMyx() {
/* 261 */     return this.myx == null ? 0.0D : this.myx.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty myxProperty() {
/* 265 */     if (this.myx == null) {
/* 266 */       this.myx = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 270 */           Affine.this.transformChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 275 */           return Affine.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 280 */           return "myx";
/*     */         }
/*     */       };
/*     */     }
/* 284 */     return this.myx;
/*     */   }
/*     */ 
/*     */   public final void setMyy(double paramDouble)
/*     */   {
/* 294 */     myyProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getMyy() {
/* 298 */     return this.myy == null ? 1.0D : this.myy.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty myyProperty() {
/* 302 */     if (this.myy == null) {
/* 303 */       this.myy = new DoublePropertyBase(1.0D)
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 307 */           Affine.this.transformChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 312 */           return Affine.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 317 */           return "myy";
/*     */         }
/*     */       };
/*     */     }
/* 321 */     return this.myy;
/*     */   }
/*     */ 
/*     */   public final void setMyz(double paramDouble)
/*     */   {
/* 331 */     myzProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getMyz() {
/* 335 */     return this.myz == null ? 0.0D : this.myz.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty myzProperty() {
/* 339 */     if (this.myz == null) {
/* 340 */       this.myz = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 344 */           Affine.this.transformChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 349 */           return Affine.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 354 */           return "myz";
/*     */         }
/*     */       };
/*     */     }
/* 358 */     return this.myz;
/*     */   }
/*     */ 
/*     */   public final void setTy(double paramDouble)
/*     */   {
/* 368 */     tyProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getTy() {
/* 372 */     return this.ty == null ? 0.0D : this.ty.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty tyProperty() {
/* 376 */     if (this.ty == null) {
/* 377 */       this.ty = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 381 */           Affine.this.transformChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 386 */           return Affine.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 391 */           return "ty";
/*     */         }
/*     */       };
/*     */     }
/* 395 */     return this.ty;
/*     */   }
/*     */ 
/*     */   public final void setMzx(double paramDouble)
/*     */   {
/* 405 */     mzxProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getMzx() {
/* 409 */     return this.mzx == null ? 0.0D : this.mzx.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty mzxProperty() {
/* 413 */     if (this.mzx == null) {
/* 414 */       this.mzx = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 418 */           Affine.this.transformChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 423 */           return Affine.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 428 */           return "mzx";
/*     */         }
/*     */       };
/*     */     }
/* 432 */     return this.mzx;
/*     */   }
/*     */ 
/*     */   public final void setMzy(double paramDouble)
/*     */   {
/* 442 */     mzyProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getMzy() {
/* 446 */     return this.mzy == null ? 0.0D : this.mzy.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty mzyProperty() {
/* 450 */     if (this.mzy == null) {
/* 451 */       this.mzy = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 455 */           Affine.this.transformChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 460 */           return Affine.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 465 */           return "mzy";
/*     */         }
/*     */       };
/*     */     }
/* 469 */     return this.mzy;
/*     */   }
/*     */ 
/*     */   public final void setMzz(double paramDouble)
/*     */   {
/* 479 */     mzzProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getMzz() {
/* 483 */     return this.mzz == null ? 1.0D : this.mzz.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty mzzProperty() {
/* 487 */     if (this.mzz == null) {
/* 488 */       this.mzz = new DoublePropertyBase(1.0D)
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 492 */           Affine.this.transformChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 497 */           return Affine.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 502 */           return "mzz";
/*     */         }
/*     */       };
/*     */     }
/* 506 */     return this.mzz;
/*     */   }
/*     */ 
/*     */   public final void setTz(double paramDouble)
/*     */   {
/* 516 */     tzProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getTz() {
/* 520 */     return this.tz == null ? 0.0D : this.tz.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty tzProperty() {
/* 524 */     if (this.tz == null) {
/* 525 */       this.tz = new DoublePropertyBase()
/*     */       {
/*     */         public void invalidated()
/*     */         {
/* 529 */           Affine.this.transformChanged();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/* 534 */           return Affine.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/* 539 */           return "tz";
/*     */         }
/*     */       };
/*     */     }
/* 543 */     return this.tz;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void impl_apply(Affine3D paramAffine3D)
/*     */   {
/* 554 */     paramAffine3D.concatenate(getMxx(), getMxy(), getMxz(), getTx(), getMyx(), getMyy(), getMyz(), getTy(), getMzx(), getMzy(), getMzz(), getTz());
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public Transform impl_copy()
/*     */   {
/* 566 */     return affine(getMxx(), getMxy(), getMxz(), getTx(), getMyx(), getMyy(), getMyz(), getTy(), getMzx(), getMzy(), getMzz(), getTz());
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 577 */     StringBuilder localStringBuilder = new StringBuilder("Affine [");
/*     */ 
/* 579 */     localStringBuilder.append("mxx=").append(getMxx());
/* 580 */     localStringBuilder.append(", mxy=").append(getMxy());
/* 581 */     localStringBuilder.append(", mxz=").append(getMxz());
/* 582 */     localStringBuilder.append(", tx=").append(getTx());
/*     */ 
/* 584 */     localStringBuilder.append(", myx=").append(getMyx());
/* 585 */     localStringBuilder.append(", myy=").append(getMyy());
/* 586 */     localStringBuilder.append(", myz=").append(getMyz());
/* 587 */     localStringBuilder.append(", ty=").append(getTy());
/*     */ 
/* 589 */     localStringBuilder.append(", mzx=").append(getMzx());
/* 590 */     localStringBuilder.append(", mzy=").append(getMzy());
/* 591 */     localStringBuilder.append(", mzz=").append(getMzz());
/* 592 */     localStringBuilder.append(", tz=").append(getTz());
/*     */ 
/* 594 */     return "]";
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.transform.Affine
 * JD-Core Version:    0.6.2
 */