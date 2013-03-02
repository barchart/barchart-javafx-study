/*    */ package javafx.scene;
/*    */ 
/*    */ import javafx.geometry.Rectangle2D;
/*    */ import javafx.scene.paint.Paint;
/*    */ import javafx.scene.transform.Transform;
/*    */ import javafx.util.Builder;
/*    */ 
/*    */ public class SnapshotParametersBuilder<B extends SnapshotParametersBuilder<B>>
/*    */   implements Builder<SnapshotParameters>
/*    */ {
/*    */   private int __set;
/*    */   private Camera camera;
/*    */   private boolean depthBuffer;
/*    */   private Paint fill;
/*    */   private Transform transform;
/*    */   private Rectangle2D viewport;
/*    */ 
/*    */   public static SnapshotParametersBuilder<?> create()
/*    */   {
/* 15 */     return new SnapshotParametersBuilder();
/*    */   }
/*    */ 
/*    */   public void applyTo(SnapshotParameters paramSnapshotParameters)
/*    */   {
/* 20 */     int i = this.__set;
/* 21 */     if ((i & 0x1) != 0) paramSnapshotParameters.setCamera(this.camera);
/* 22 */     if ((i & 0x2) != 0) paramSnapshotParameters.setDepthBuffer(this.depthBuffer);
/* 23 */     if ((i & 0x4) != 0) paramSnapshotParameters.setFill(this.fill);
/* 24 */     if ((i & 0x8) != 0) paramSnapshotParameters.setTransform(this.transform);
/* 25 */     if ((i & 0x10) != 0) paramSnapshotParameters.setViewport(this.viewport);
/*    */   }
/*    */ 
/*    */   public B camera(Camera paramCamera)
/*    */   {
/* 34 */     this.camera = paramCamera;
/* 35 */     this.__set |= 1;
/* 36 */     return this;
/*    */   }
/*    */ 
/*    */   public B depthBuffer(boolean paramBoolean)
/*    */   {
/* 45 */     this.depthBuffer = paramBoolean;
/* 46 */     this.__set |= 2;
/* 47 */     return this;
/*    */   }
/*    */ 
/*    */   public B fill(Paint paramPaint)
/*    */   {
/* 56 */     this.fill = paramPaint;
/* 57 */     this.__set |= 4;
/* 58 */     return this;
/*    */   }
/*    */ 
/*    */   public B transform(Transform paramTransform)
/*    */   {
/* 67 */     this.transform = paramTransform;
/* 68 */     this.__set |= 8;
/* 69 */     return this;
/*    */   }
/*    */ 
/*    */   public B viewport(Rectangle2D paramRectangle2D)
/*    */   {
/* 78 */     this.viewport = paramRectangle2D;
/* 79 */     this.__set |= 16;
/* 80 */     return this;
/*    */   }
/*    */ 
/*    */   public SnapshotParameters build()
/*    */   {
/* 87 */     SnapshotParameters localSnapshotParameters = new SnapshotParameters();
/* 88 */     applyTo(localSnapshotParameters);
/* 89 */     return localSnapshotParameters;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.SnapshotParametersBuilder
 * JD-Core Version:    0.6.2
 */