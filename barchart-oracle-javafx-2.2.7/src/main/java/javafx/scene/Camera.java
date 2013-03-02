/*    */ package javafx.scene;
/*    */ 
/*    */ import com.sun.javafx.geom.CameraImpl;
/*    */ import javafx.beans.property.BooleanProperty;
/*    */ import javafx.beans.property.SimpleBooleanProperty;
/*    */ import javafx.beans.value.ObservableBooleanValue;
/*    */ 
/*    */ public abstract class Camera
/*    */ {
/*    */   private CameraImpl platformCamera;
/* 53 */   private BooleanProperty dirty = new SimpleBooleanProperty(this, "dirty");
/*    */ 
/*    */   CameraImpl getPlatformCamera()
/*    */   {
/* 44 */     if (this.platformCamera == null) {
/* 45 */       this.platformCamera = createPlatformCamera();
/*    */     }
/* 47 */     return this.platformCamera;
/*    */   }
/*    */ 
/*    */   abstract CameraImpl createPlatformCamera();
/*    */ 
/*    */   abstract void update();
/*    */ 
/*    */   final ObservableBooleanValue dirtyProperty() {
/* 55 */     return this.dirty;
/*    */   }
/*    */   final boolean isDirty() {
/* 58 */     return this.dirty.get();
/*    */   }
/*    */ 
/*    */   final void markDirty() {
/* 62 */     this.dirty.set(true);
/*    */   }
/*    */ 
/*    */   final void clearDirty() {
/* 66 */     this.dirty.set(false);
/*    */   }
/*    */ 
/*    */   Camera copy() {
/* 70 */     return this;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.Camera
 * JD-Core Version:    0.6.2
 */