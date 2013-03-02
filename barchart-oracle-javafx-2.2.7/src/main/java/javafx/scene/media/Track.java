/*    */ package javafx.scene.media;
/*    */ 
/*    */ public abstract class Track
/*    */ {
/*    */   private String name;
/*    */   private com.sun.media.jfxmedia.track.Track mediaTrack;
/*    */ 
/*    */   public final String getName()
/*    */   {
/* 33 */     return this.name;
/*    */   }
/*    */ 
/*    */   com.sun.media.jfxmedia.track.Track getMediaTrack()
/*    */   {
/* 39 */     return this.mediaTrack;
/*    */   }
/*    */ 
/*    */   Track(com.sun.media.jfxmedia.track.Track paramTrack)
/*    */   {
/* 49 */     this.mediaTrack = paramTrack;
/* 50 */     this.name = paramTrack.getName();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.media.Track
 * JD-Core Version:    0.6.2
 */