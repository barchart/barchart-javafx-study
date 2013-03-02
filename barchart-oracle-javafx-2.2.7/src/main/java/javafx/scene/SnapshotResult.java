/*    */ package javafx.scene;
/*    */ 
/*    */ import javafx.scene.image.WritableImage;
/*    */ 
/*    */ public class SnapshotResult
/*    */ {
/*    */   private WritableImage image;
/*    */   private Object source;
/*    */   private SnapshotParameters params;
/*    */ 
/*    */   SnapshotResult(WritableImage paramWritableImage, Object paramObject, SnapshotParameters paramSnapshotParameters)
/*    */   {
/* 40 */     this.image = paramWritableImage;
/* 41 */     this.source = paramObject;
/* 42 */     this.params = paramSnapshotParameters;
/*    */   }
/*    */ 
/*    */   public WritableImage getImage()
/*    */   {
/* 51 */     return this.image;
/*    */   }
/*    */ 
/*    */   public Object getSource()
/*    */   {
/* 60 */     return this.source;
/*    */   }
/*    */ 
/*    */   public SnapshotParameters getSnapshotParameters()
/*    */   {
/* 71 */     return this.params;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.SnapshotResult
 * JD-Core Version:    0.6.2
 */