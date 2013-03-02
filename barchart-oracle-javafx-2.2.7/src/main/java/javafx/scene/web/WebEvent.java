/*    */ package javafx.scene.web;
/*    */ 
/*    */ import javafx.event.Event;
/*    */ import javafx.event.EventType;
/*    */ 
/*    */ public final class WebEvent<T> extends Event
/*    */ {
/* 25 */   public static final EventType<WebEvent> ANY = new EventType(Event.ANY, "WEB");
/*    */ 
/* 32 */   public static final EventType<WebEvent> RESIZED = new EventType(ANY, "WEB_RESIZED");
/*    */ 
/* 38 */   public static final EventType<WebEvent> STATUS_CHANGED = new EventType(ANY, "WEB_STATUS_CHANGED");
/*    */ 
/* 45 */   public static final EventType<WebEvent> VISIBILITY_CHANGED = new EventType(ANY, "WEB_VISIBILITY_CHANGED");
/*    */ 
/* 52 */   public static final EventType<WebEvent> ALERT = new EventType(ANY, "WEB_ALERT");
/*    */   private T data;
/*    */ 
/*    */   public WebEvent(Object paramObject, EventType<WebEvent> paramEventType, T paramT)
/*    */   {
/* 61 */     super(paramObject, null, paramEventType);
/* 62 */     this.data = paramT;
/*    */   }
/*    */ 
/*    */   public T getData()
/*    */   {
/* 69 */     return this.data;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 77 */     return String.format("WebEvent [source = %s, eventType = %s, data = %s]", new Object[] { getSource(), getEventType(), getData() });
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.web.WebEvent
 * JD-Core Version:    0.6.2
 */