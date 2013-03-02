/*    */ package javafx.stage;
/*    */ 
/*    */ import javafx.event.Event;
/*    */ import javafx.event.EventType;
/*    */ 
/*    */ public class WindowEvent extends Event
/*    */ {
/* 38 */   public static final EventType<WindowEvent> ANY = new EventType(Event.ANY, "WINDOW");
/*    */ 
/* 44 */   public static final EventType<WindowEvent> WINDOW_SHOWING = new EventType(ANY, "WINDOW_SHOWING");
/*    */ 
/* 50 */   public static final EventType<WindowEvent> WINDOW_SHOWN = new EventType(ANY, "WINDOW_SHOWN");
/*    */ 
/* 56 */   public static final EventType<WindowEvent> WINDOW_HIDING = new EventType(ANY, "WINDOW_HIDING");
/*    */ 
/* 62 */   public static final EventType<WindowEvent> WINDOW_HIDDEN = new EventType(ANY, "WINDOW_HIDDEN");
/*    */ 
/* 71 */   public static final EventType<WindowEvent> WINDOW_CLOSE_REQUEST = new EventType(ANY, "WINDOW_CLOSE_REQUEST");
/*    */ 
/*    */   public WindowEvent(Window paramWindow, EventType<? extends Event> paramEventType)
/*    */   {
/* 83 */     super(paramWindow, paramWindow, paramEventType);
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 91 */     StringBuilder localStringBuilder = new StringBuilder("WindowEvent [");
/*    */ 
/* 93 */     localStringBuilder.append("source = ").append(getSource());
/* 94 */     localStringBuilder.append(", target = ").append(getTarget());
/* 95 */     localStringBuilder.append(", eventType = ").append(getEventType());
/* 96 */     localStringBuilder.append(", consumed = ").append(isConsumed());
/*    */ 
/* 98 */     return "]";
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.stage.WindowEvent
 * JD-Core Version:    0.6.2
 */