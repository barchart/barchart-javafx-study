/*    */ package javafx.scene.media;
/*    */ 
/*    */ import javafx.event.ActionEvent;
/*    */ import javafx.util.Duration;
/*    */ import javafx.util.Pair;
/*    */ 
/*    */ public class MediaMarkerEvent extends ActionEvent
/*    */ {
/*    */   private Pair<String, Duration> marker;
/*    */ 
/*    */   MediaMarkerEvent(Pair<String, Duration> paramPair)
/*    */   {
/* 23 */     this.marker = paramPair;
/*    */   }
/*    */ 
/*    */   public Pair<String, Duration> getMarker()
/*    */   {
/* 32 */     return this.marker;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.media.MediaMarkerEvent
 * JD-Core Version:    0.6.2
 */