/*     */ package com.sun.javafx.scene.transform;
/*     */ 
/*     */ import com.sun.javafx.geom.transform.Affine3D;
/*     */ import javafx.scene.transform.Transform;
/*     */ 
/*     */ public class TransformUtils
/*     */ {
/*     */   public static Transform immutableTransform(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, double paramDouble9, double paramDouble10, double paramDouble11, double paramDouble12)
/*     */   {
/*  45 */     return new ImmutableTransform(paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6, paramDouble7, paramDouble8, paramDouble9, paramDouble10, paramDouble11, paramDouble12); } 
/*     */   private static class ImmutableTransform extends Transform { private final double mxx;
/*     */     private final double mxy;
/*     */     private final double mxz;
/*     */     private final double tx;
/*     */     private final double myx;
/*     */     private final double myy;
/*     */     private final double myz;
/*     */     private final double ty;
/*     */     private final double mzx;
/*     */     private final double mzy;
/*     */     private final double mzz;
/*     */     private final double tz;
/*     */ 
/*  61 */     public ImmutableTransform(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, double paramDouble9, double paramDouble10, double paramDouble11, double paramDouble12) { this.mxx = paramDouble1;
/*  62 */       this.mxy = paramDouble2;
/*  63 */       this.mxz = paramDouble3;
/*  64 */       this.tx = paramDouble4;
/*  65 */       this.myx = paramDouble5;
/*  66 */       this.myy = paramDouble6;
/*  67 */       this.myz = paramDouble7;
/*  68 */       this.ty = paramDouble8;
/*  69 */       this.mzx = paramDouble9;
/*  70 */       this.mzy = paramDouble10;
/*  71 */       this.mzz = paramDouble11;
/*  72 */       this.tz = paramDouble12;
/*     */     }
/*     */ 
/*     */     public double getMxx()
/*     */     {
/*  77 */       return this.mxx;
/*     */     }
/*     */ 
/*     */     public double getMxy()
/*     */     {
/*  82 */       return this.mxy;
/*     */     }
/*     */ 
/*     */     public double getMxz()
/*     */     {
/*  87 */       return this.mxz;
/*     */     }
/*     */ 
/*     */     public double getTx()
/*     */     {
/*  92 */       return this.tx;
/*     */     }
/*     */ 
/*     */     public double getMyx()
/*     */     {
/*  97 */       return this.myx;
/*     */     }
/*     */ 
/*     */     public double getMyy()
/*     */     {
/* 102 */       return this.myy;
/*     */     }
/*     */ 
/*     */     public double getMyz()
/*     */     {
/* 107 */       return this.myz;
/*     */     }
/*     */ 
/*     */     public double getTy()
/*     */     {
/* 112 */       return this.ty;
/*     */     }
/*     */ 
/*     */     public double getMzx()
/*     */     {
/* 117 */       return this.mzx;
/*     */     }
/*     */ 
/*     */     public double getMzy()
/*     */     {
/* 122 */       return this.mzy;
/*     */     }
/*     */ 
/*     */     public double getMzz()
/*     */     {
/* 127 */       return this.mzz;
/*     */     }
/*     */ 
/*     */     public double getTz()
/*     */     {
/* 132 */       return this.tz;
/*     */     }
/*     */ 
/*     */     public void impl_apply(Affine3D paramAffine3D)
/*     */     {
/* 137 */       paramAffine3D.concatenate(getMxx(), getMxy(), getMxz(), getTx(), getMyx(), getMyy(), getMyz(), getTy(), getMzx(), getMzy(), getMzz(), getTz());
/*     */     }
/*     */ 
/*     */     public Transform impl_copy()
/*     */     {
/* 145 */       return new ImmutableTransform(getMxx(), getMxy(), getMxz(), getTx(), getMyx(), getMyy(), getMyz(), getTy(), getMzx(), getMzy(), getMzz(), getTz());
/*     */     }
/*     */ 
/*     */     public String toString()
/*     */     {
/* 157 */       StringBuilder localStringBuilder = new StringBuilder("Transform [");
/*     */ 
/* 159 */       localStringBuilder.append("mxx=").append(getMxx());
/* 160 */       localStringBuilder.append(", mxy=").append(getMxy());
/* 161 */       localStringBuilder.append(", mxz=").append(getMxz());
/* 162 */       localStringBuilder.append(", tx=").append(getTx());
/*     */ 
/* 164 */       localStringBuilder.append(", myx=").append(getMyx());
/* 165 */       localStringBuilder.append(", myy=").append(getMyy());
/* 166 */       localStringBuilder.append(", myz=").append(getMyz());
/* 167 */       localStringBuilder.append(", ty=").append(getTy());
/*     */ 
/* 169 */       localStringBuilder.append(", mzx=").append(getMzx());
/* 170 */       localStringBuilder.append(", mzy=").append(getMzy());
/* 171 */       localStringBuilder.append(", mzz=").append(getMzz());
/* 172 */       localStringBuilder.append(", tz=").append(getTz());
/*     */ 
/* 174 */       return "]";
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.transform.TransformUtils
 * JD-Core Version:    0.6.2
 */