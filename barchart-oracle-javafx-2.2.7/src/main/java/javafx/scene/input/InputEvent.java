/*    */ package javafx.scene.input;
/*    */ 
/*    */ import javafx.event.Event;
/*    */ import javafx.event.EventTarget;
/*    */ import javafx.event.EventType;
/*    */ 
/*    */ public class InputEvent extends Event
/*    */ {
/* 39 */   public static final EventType<InputEvent> ANY = new EventType(Event.ANY, "INPUT");
/*    */ 
/*    */   public InputEvent(EventType<? extends InputEvent> paramEventType)
/*    */   {
/* 47 */     super(paramEventType);
/*    */   }
/*    */ 
/*    */   public InputEvent(Object paramObject, EventTarget paramEventTarget, EventType<? extends InputEvent> paramEventType)
/*    */   {
/* 59 */     super(paramObject, paramEventTarget, paramEventType);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.input.InputEvent
 * JD-Core Version:    0.6.2
 */