/*    */ package javafx.scene.media;
/*    */ 
/*    */ import javafx.event.Event;
/*    */ import javafx.event.EventTarget;
/*    */ import javafx.event.EventType;
/*    */ 
/*    */ public class MediaErrorEvent extends Event
/*    */ {
/* 25 */   public static final EventType<MediaErrorEvent> MEDIA_ERROR = new EventType("Media Error Event");
/*    */   private MediaException error;
/*    */ 
/*    */   MediaErrorEvent(Object paramObject, EventTarget paramEventTarget, MediaException paramMediaException)
/*    */   {
/* 43 */     super(paramObject, paramEventTarget, MEDIA_ERROR);
/*    */ 
/* 45 */     if (paramMediaException == null) {
/* 46 */       throw new IllegalArgumentException("error == null!");
/*    */     }
/*    */ 
/* 49 */     this.error = paramMediaException;
/*    */   }
/*    */ 
/*    */   public MediaException getMediaError()
/*    */   {
/* 58 */     return this.error;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 66 */     return super.toString() + ": source " + getSource() + "; target " + getTarget() + "; error " + this.error;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.media.MediaErrorEvent
 * JD-Core Version:    0.6.2
 */