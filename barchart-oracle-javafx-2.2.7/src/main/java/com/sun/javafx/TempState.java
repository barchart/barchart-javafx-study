/*    */ package com.sun.javafx;
/*    */ 
/*    */ import com.sun.javafx.geom.BaseBounds;
/*    */ import com.sun.javafx.geom.Point2D;
/*    */ import com.sun.javafx.geom.RectBounds;
/*    */ import com.sun.javafx.geom.Vec3d;
/*    */ import com.sun.javafx.geom.transform.Affine3D;
/*    */ import com.sun.javafx.geom.transform.BaseTransform;
/*    */ 
/*    */ public final class TempState
/*    */ {
/* 47 */   public BaseBounds bounds = new RectBounds(0.0F, 0.0F, -1.0F, -1.0F);
/*    */ 
/* 53 */   public final BaseTransform pickTx = new Affine3D();
/*    */ 
/* 59 */   public final Affine3D leafTx = new Affine3D();
/*    */ 
/* 65 */   public final Affine3D snapshotTx = new Affine3D();
/*    */ 
/* 70 */   public final Point2D point = new Point2D(0.0F, 0.0F);
/*    */ 
/* 73 */   public final Vec3d vec3d = new Vec3d(0.0D, 0.0D, 0.0D);
/*    */ 
/* 77 */   private static final ThreadLocal<TempState> tempStateRef = new ThreadLocal()
/*    */   {
/*    */     protected TempState initialValue()
/*    */     {
/* 81 */       return new TempState(null);
/*    */     }
/* 77 */   };
/*    */ 
/*    */   public static TempState getInstance()
/*    */   {
/* 89 */     return (TempState)tempStateRef.get();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.TempState
 * JD-Core Version:    0.6.2
 */