/*    */ package javafx.scene;
/*    */ 
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class PerspectiveCameraBuilder<B extends PerspectiveCameraBuilder<B>>
/*    */   implements Builder<PerspectiveCamera>
/*    */ {
/*    */   private boolean __set;
/*    */   private double fieldOfView;
/*    */ 
/*    */   public static PerspectiveCameraBuilder<?> create()
/*    */   {
/* 15 */     return new PerspectiveCameraBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(PerspectiveCamera paramPerspectiveCamera)
/*    */   {
/* 20 */     if (this.__set) paramPerspectiveCamera.setFieldOfView(this.fieldOfView);
/*    */   }
/*    */ 
/*    */   public B fieldOfView(double paramDouble)
/*    */   {
/* 29 */     this.fieldOfView = paramDouble;
/* 30 */     this.__set = true;
/* 31 */     return this;
/*    */   }
/*    */ 
/*    */   public PerspectiveCamera build()
/*    */   {
/* 38 */     PerspectiveCamera localPerspectiveCamera = new PerspectiveCamera();
/* 39 */     applyTo(localPerspectiveCamera);
/* 40 */     return localPerspectiveCamera;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.PerspectiveCameraBuilder
 * JD-Core Version:    0.6.2
 */