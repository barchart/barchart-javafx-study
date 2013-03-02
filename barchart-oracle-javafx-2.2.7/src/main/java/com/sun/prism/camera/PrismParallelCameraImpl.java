/*    */ package com.sun.prism.camera;
/*    */ 
/*    */ import com.sun.javafx.geom.ParallelCameraImpl;
/*    */ import com.sun.javafx.geom.PickRay;
/*    */ import com.sun.javafx.geom.Rectangle;
/*    */ import com.sun.javafx.geom.Vec3d;
/*    */ import com.sun.javafx.geom.transform.Affine3D;
/*    */ import com.sun.javafx.geom.transform.GeneralTransform3D;
/*    */ 
/*    */ public class PrismParallelCameraImpl extends PrismCameraImpl
/*    */   implements ParallelCameraImpl
/*    */ {
/* 38 */   private static final PrismParallelCameraImpl theInstance = new PrismParallelCameraImpl();
/*    */ 
/*    */   public static PrismParallelCameraImpl getInstance() {
/* 41 */     return theInstance;
/*    */   }
/*    */ 
/*    */   protected void computeProjection(GeneralTransform3D paramGeneralTransform3D)
/*    */   {
/* 51 */     double d1 = this.viewport.width;
/* 52 */     double d2 = this.viewport.height;
/* 53 */     double d3 = d1 > d2 ? d1 / 2.0D : d2 / 2.0D;
/* 54 */     paramGeneralTransform3D.ortho(0.0D, d1, d2, 0.0D, -d3, d3);
/*    */   }
/*    */ 
/*    */   protected void computeViewTransform(Affine3D paramAffine3D)
/*    */   {
/* 59 */     paramAffine3D.setToTranslation(-this.zero.x, -this.zero.y, 0.0D);
/*    */   }
/*    */ 
/*    */   public PickRay computePickRay(float paramFloat1, float paramFloat2, PickRay paramPickRay) {
/* 63 */     if (paramPickRay == null) {
/* 64 */       paramPickRay = new PickRay();
/*    */     }
/* 66 */     paramPickRay.getOriginNoClone().set(paramFloat1, paramFloat2, 0.0D);
/* 67 */     paramPickRay.getDirectionNoClone().set(0.0D, 0.0D, 1.0D);
/* 68 */     return paramPickRay;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.camera.PrismParallelCameraImpl
 * JD-Core Version:    0.6.2
 */