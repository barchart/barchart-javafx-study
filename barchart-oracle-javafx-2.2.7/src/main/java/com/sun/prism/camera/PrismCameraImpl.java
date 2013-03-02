/*     */ package com.sun.prism.camera;
/*     */ 
/*     */ import com.sun.javafx.geom.CameraImpl;
/*     */ import com.sun.javafx.geom.PickRay;
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.Vec3d;
/*     */ import com.sun.javafx.geom.transform.Affine3D;
/*     */ import com.sun.javafx.geom.transform.GeneralTransform3D;
/*     */ 
/*     */ public abstract class PrismCameraImpl
/*     */   implements CameraImpl
/*     */ {
/*  42 */   private Affine3D viewTx = new Affine3D();
/*     */ 
/*  45 */   protected GeneralTransform3D projTx = new GeneralTransform3D();
/*     */ 
/*  49 */   protected Rectangle viewport = new Rectangle(1, 1);
/*     */ 
/*  51 */   protected Vec3d zero = new Vec3d(0.0D, 0.0D, 0.0D);
/*     */   protected double aspect;
/*     */   protected static final double zNear = 0.1D;
/*     */   protected static final double zFar = 100.0D;
/*     */ 
/*     */   public Affine3D getViewTransform(Affine3D paramAffine3D)
/*     */   {
/*  84 */     if (paramAffine3D == null) {
/*  85 */       paramAffine3D = new Affine3D();
/*     */     }
/*  87 */     paramAffine3D.setTransform(this.viewTx);
/*  88 */     return paramAffine3D;
/*     */   }
/*     */ 
/*     */   public GeneralTransform3D getProjectionTransform(GeneralTransform3D paramGeneralTransform3D)
/*     */   {
/* 114 */     if (paramGeneralTransform3D == null) {
/* 115 */       paramGeneralTransform3D = new GeneralTransform3D();
/*     */     }
/* 117 */     paramGeneralTransform3D.set(this.projTx);
/* 118 */     return paramGeneralTransform3D;
/*     */   }
/*     */ 
/*     */   public Rectangle getViewport(Rectangle paramRectangle)
/*     */   {
/* 126 */     if (paramRectangle == null) {
/* 127 */       paramRectangle = new Rectangle();
/*     */     }
/* 129 */     paramRectangle.setBounds(this.viewport);
/* 130 */     return paramRectangle;
/*     */   }
/*     */ 
/*     */   public void setZero(Vec3d paramVec3d)
/*     */   {
/* 140 */     this.zero.set(paramVec3d);
/*     */   }
/*     */ 
/*     */   public Vec3d getZero(Vec3d paramVec3d)
/*     */   {
/* 149 */     if (paramVec3d == null) {
/* 150 */       paramVec3d = new Vec3d();
/*     */     }
/* 152 */     paramVec3d.set(paramVec3d);
/* 153 */     return paramVec3d;
/*     */   }
/*     */ 
/*     */   public GeneralTransform3D getProjViewTx(GeneralTransform3D paramGeneralTransform3D, int paramInt1, int paramInt2)
/*     */   {
/* 159 */     if (paramGeneralTransform3D == null) {
/* 160 */       paramGeneralTransform3D = new GeneralTransform3D();
/*     */     }
/* 162 */     this.aspect = (paramInt1 / paramInt2);
/* 163 */     this.viewport.setBounds(0, 0, paramInt1, paramInt2);
/* 164 */     computeProjection(this.projTx);
/* 165 */     computeViewTransform(this.viewTx);
/* 166 */     paramGeneralTransform3D.set(this.projTx);
/* 167 */     paramGeneralTransform3D.mul(this.viewTx);
/* 168 */     return paramGeneralTransform3D;
/*     */   }
/*     */ 
/*     */   protected abstract void computeProjection(GeneralTransform3D paramGeneralTransform3D);
/*     */ 
/*     */   protected abstract void computeViewTransform(Affine3D paramAffine3D);
/*     */ 
/*     */   public abstract PickRay computePickRay(float paramFloat1, float paramFloat2, PickRay paramPickRay);
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.camera.PrismCameraImpl
 * JD-Core Version:    0.6.2
 */