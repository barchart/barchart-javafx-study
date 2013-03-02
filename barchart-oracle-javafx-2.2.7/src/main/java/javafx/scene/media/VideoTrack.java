/*    */ package javafx.scene.media;
/*    */ 
/*    */ import com.sun.media.jfxmedia.track.VideoResolution;
/*    */ 
/*    */ public final class VideoTrack extends Track
/*    */ {
/*    */   private int width;
/*    */   private int height;
/*    */ 
/*    */   public final int getWidth()
/*    */   {
/* 29 */     return this.width;
/*    */   }
/*    */ 
/*    */   public final int getHeight()
/*    */   {
/* 42 */     return this.height;
/*    */   }
/*    */ 
/*    */   VideoTrack(com.sun.media.jfxmedia.track.VideoTrack paramVideoTrack)
/*    */   {
/* 52 */     super(paramVideoTrack);
/* 53 */     VideoResolution localVideoResolution = paramVideoTrack.getFrameSize();
/* 54 */     this.width = localVideoResolution.width;
/* 55 */     this.height = localVideoResolution.height;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 64 */     return "Video Track: name=" + super.getName() + ", resolution=(" + this.width + "," + this.height + ")";
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.media.VideoTrack
 * JD-Core Version:    0.6.2
 */