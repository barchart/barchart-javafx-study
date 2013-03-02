/*     */ package com.sun.prism.camera;
/*     */ 
/*     */ import com.sun.javafx.geom.PerspectiveCameraImpl;
/*     */ import com.sun.javafx.geom.PickRay;
/*     */ import com.sun.javafx.geom.Rectangle;
/*     */ import com.sun.javafx.geom.Vec3d;
/*     */ import com.sun.javafx.geom.transform.Affine3D;
/*     */ import com.sun.javafx.geom.transform.GeneralTransform3D;
/*     */ import com.sun.javafx.geom.transform.NoninvertibleTransformException;
/*     */ import com.sun.javafx.logging.PlatformLogger;
/*     */ 
/*     */ public class PrismPerspectiveCameraImpl extends PrismCameraImpl
/*     */   implements PerspectiveCameraImpl
/*     */ {
/*     */   private double fov;
/*  50 */   private static final Vec3d viewDir = new Vec3d(0.0D, 0.0D, 1.0D);
/*  51 */   private static final Vec3d up = new Vec3d(0.0D, -1.0D, 0.0D);
/*  52 */   private static GeneralTransform3D invProjTx = new GeneralTransform3D();
/*  53 */   private static Affine3D invViewTx = new Affine3D();
/*     */ 
/*     */   protected void computeProjection(GeneralTransform3D paramGeneralTransform3D)
/*     */   {
/*  63 */     paramGeneralTransform3D.perspective(this.fov, this.aspect, 0.1D, 100.0D);
/*  64 */     invProjTx.set(paramGeneralTransform3D);
/*  65 */     invProjTx.invert();
/*     */   }
/*     */ 
/*     */   protected void computeViewTransform(Affine3D paramAffine3D)
/*     */   {
/*  71 */     double d1 = Math.tan(this.fov / 2.0D);
/*  72 */     double d2 = 2.0D * d1 / this.viewport.height;
/*     */ 
/*  75 */     double d3 = -d1 * this.aspect;
/*  76 */     double d4 = d1;
/*     */ 
/*  78 */     paramAffine3D.setToTranslation(d3, d4, 0.0D);
/*     */ 
/*  83 */     Vec3d localVec3d1 = new Vec3d(this.zero);
/*  84 */     localVec3d1.mul(d2);
/*  85 */     Vec3d localVec3d2 = new Vec3d(viewDir);
/*  86 */     localVec3d2.mul(-1.0D);
/*  87 */     localVec3d2.normalize();
/*  88 */     Vec3d localVec3d3 = new Vec3d(localVec3d1);
/*  89 */     localVec3d3.add(localVec3d2);
/*  90 */     Affine3D localAffine3D1 = new Affine3D();
/*     */ 
/*  94 */     localAffine3D1.lookAt(localVec3d3, localVec3d1, up);
/*  95 */     paramAffine3D.concatenate(localAffine3D1);
/*     */ 
/*  98 */     Affine3D localAffine3D2 = new Affine3D();
/*  99 */     localAffine3D2.setToScale(d2, d2, d2);
/* 100 */     paramAffine3D.concatenate(localAffine3D2);
/* 101 */     invViewTx.setTransform(paramAffine3D);
/*     */     try
/*     */     {
/* 107 */       invViewTx.invert();
/*     */     } catch (NoninvertibleTransformException localNoninvertibleTransformException) {
/* 109 */       String str = PrismPerspectiveCameraImpl.class.getName();
/* 110 */       PlatformLogger.getLogger(str).severe("computeViewTransform", localNoninvertibleTransformException);
/*     */     }
/*     */   }
/*     */ 
/*     */   public PickRay computePickRay(float paramFloat1, float paramFloat2, PickRay paramPickRay) {
/* 115 */     if (paramPickRay == null) {
/* 116 */       paramPickRay = new PickRay();
/*     */     }
/* 118 */     Vec3d localVec3d1 = paramPickRay.getOriginNoClone();
/* 119 */     Vec3d localVec3d2 = paramPickRay.getDirectionNoClone();
/*     */ 
/* 121 */     double d1 = paramFloat1;
/* 122 */     double d2 = paramFloat2;
/* 123 */     double d3 = d1 / this.viewport.width * 2.0D - 1.0D;
/* 124 */     double d4 = d2 / this.viewport.height * -2.0D + 1.0D;
/*     */ 
/* 127 */     double d5 = this.projTx.computeClipZCoord();
/* 128 */     localVec3d2.set(d3, d4, d5);
/*     */ 
/* 131 */     invProjTx.transform(localVec3d2, localVec3d2);
/* 132 */     invViewTx.transform(localVec3d2, localVec3d2);
/*     */ 
/* 134 */     localVec3d1.set(0.0D, 0.0D, 0.0D);
/* 135 */     invViewTx.transform(localVec3d1, localVec3d1);
/*     */ 
/* 137 */     localVec3d2.sub(localVec3d1);
/*     */ 
/* 139 */     return paramPickRay;
/*     */   }
/*     */ 
/*     */   public void setFieldOfView(float paramFloat) {
/* 143 */     this.fov = Math.toRadians(paramFloat);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.camera.PrismPerspectiveCameraImpl
 * JD-Core Version:    0.6.2
 */