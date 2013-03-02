/*     */ package javafx.scene;
/*     */ 
/*     */ import javafx.geometry.Rectangle2D;
/*     */ import javafx.scene.paint.Paint;
/*     */ import javafx.scene.transform.Transform;
/*     */ 
/*     */ public class SnapshotParameters
/*     */ {
/*     */   private boolean depthBuffer;
/*     */   private Camera camera;
/*     */   private Transform transform;
/*     */   private Paint fill;
/*     */   private Rectangle2D viewport;
/*     */ 
/*     */   public boolean isDepthBuffer()
/*     */   {
/*  56 */     return this.depthBuffer;
/*     */   }
/*     */ 
/*     */   public void setDepthBuffer(boolean paramBoolean)
/*     */   {
/*  66 */     this.depthBuffer = paramBoolean;
/*     */   }
/*     */ 
/*     */   public Camera getCamera()
/*     */   {
/*  75 */     return this.camera;
/*     */   }
/*     */ 
/*     */   public void setCamera(Camera paramCamera)
/*     */   {
/*  85 */     this.camera = paramCamera;
/*     */   }
/*     */ 
/*     */   public Transform getTransform()
/*     */   {
/*  94 */     return this.transform;
/*     */   }
/*     */ 
/*     */   public void setTransform(Transform paramTransform)
/*     */   {
/* 106 */     this.transform = paramTransform;
/*     */   }
/*     */ 
/*     */   public Paint getFill()
/*     */   {
/* 115 */     return this.fill;
/*     */   }
/*     */ 
/*     */   public void setFill(Paint paramPaint)
/*     */   {
/* 127 */     this.fill = paramPaint;
/*     */   }
/*     */ 
/*     */   public Rectangle2D getViewport()
/*     */   {
/* 136 */     return this.viewport;
/*     */   }
/*     */ 
/*     */   public void setViewport(Rectangle2D paramRectangle2D)
/*     */   {
/* 158 */     this.viewport = paramRectangle2D;
/*     */   }
/*     */ 
/*     */   SnapshotParameters copy()
/*     */   {
/* 167 */     SnapshotParameters localSnapshotParameters = new SnapshotParameters();
/* 168 */     localSnapshotParameters.camera = (this.camera == null ? null : this.camera.copy());
/* 169 */     localSnapshotParameters.depthBuffer = this.depthBuffer;
/* 170 */     localSnapshotParameters.fill = this.fill;
/* 171 */     localSnapshotParameters.viewport = this.viewport;
/* 172 */     localSnapshotParameters.transform = (this.transform == null ? null : this.transform.impl_copy());
/* 173 */     return localSnapshotParameters;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.SnapshotParameters
 * JD-Core Version:    0.6.2
 */