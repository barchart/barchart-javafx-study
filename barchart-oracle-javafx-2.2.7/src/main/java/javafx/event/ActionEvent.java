/*    */ package javafx.event;
/*    */ 
/*    */ public class ActionEvent extends Event
/*    */ {
/* 16 */   public static final EventType<ActionEvent> ACTION = new EventType(Event.ANY, "ACTION");
/*    */ 
/*    */   public ActionEvent()
/*    */   {
/* 24 */     super(ACTION);
/*    */   }
/*    */ 
/*    */   public ActionEvent(Object paramObject, EventTarget paramEventTarget)
/*    */   {
/* 37 */     super(paramObject, paramEventTarget, ACTION);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.event.ActionEvent
 * JD-Core Version:    0.6.2
 */