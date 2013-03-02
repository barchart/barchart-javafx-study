/*     */ package javafx.scene;
/*     */ 
/*     */ import com.sun.javafx.geom.CameraImpl;
/*     */ import com.sun.javafx.geom.PerspectiveCameraImpl;
/*     */ import com.sun.javafx.tk.Toolkit;
/*     */ import javafx.beans.property.DoubleProperty;
/*     */ import javafx.beans.property.DoublePropertyBase;
/*     */ 
/*     */ public class PerspectiveCamera extends Camera
/*     */ {
/*     */   private DoubleProperty fieldOfView;
/*     */ 
/*     */   public final void setFieldOfView(double paramDouble)
/*     */   {
/*  60 */     fieldOfViewProperty().set(paramDouble);
/*     */   }
/*     */ 
/*     */   public final double getFieldOfView() {
/*  64 */     return this.fieldOfView == null ? 30.0D : this.fieldOfView.get();
/*     */   }
/*     */ 
/*     */   public final DoubleProperty fieldOfViewProperty() {
/*  68 */     if (this.fieldOfView == null) {
/*  69 */       this.fieldOfView = new DoublePropertyBase(30.0D)
/*     */       {
/*     */         protected void invalidated()
/*     */         {
/*  73 */           PerspectiveCamera.this.markDirty();
/*     */         }
/*     */ 
/*     */         public Object getBean()
/*     */         {
/*  78 */           return PerspectiveCamera.this;
/*     */         }
/*     */ 
/*     */         public String getName()
/*     */         {
/*  83 */           return "fieldOfView";
/*     */         }
/*     */       };
/*     */     }
/*  87 */     return this.fieldOfView;
/*     */   }
/*     */ 
/*     */   public PerspectiveCamera() {
/*  91 */     markDirty();
/*     */   }
/*     */ 
/*     */   CameraImpl createPlatformCamera()
/*     */   {
/*  96 */     return Toolkit.getToolkit().createPerspectiveCamera();
/*     */   }
/*     */ 
/*     */   void update() {
/* 100 */     if (isDirty()) {
/* 101 */       PerspectiveCameraImpl localPerspectiveCameraImpl = (PerspectiveCameraImpl)getPlatformCamera();
/* 102 */       localPerspectiveCameraImpl.setFieldOfView((float)getFieldOfView());
/* 103 */       clearDirty();
/*     */     }
/*     */   }
/*     */ 
/*     */   Camera copy() {
/* 108 */     PerspectiveCamera localPerspectiveCamera = new PerspectiveCamera();
/* 109 */     localPerspectiveCamera.setFieldOfView(getFieldOfView());
/* 110 */     return localPerspectiveCamera;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.PerspectiveCamera
 * JD-Core Version:    0.6.2
 */