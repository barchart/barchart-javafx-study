/*    */ package com.sun.javafx.event;
/*    */ 
/*    */ import javafx.event.Event;
/*    */ import javafx.event.EventTarget;
/*    */ import javafx.event.EventType;
/*    */ 
/*    */ public class RedirectedEvent extends Event
/*    */ {
/* 16 */   public static final EventType<RedirectedEvent> REDIRECTED = new EventType(Event.ANY, "REDIRECTED");
/*    */   private final Event originalEvent;
/*    */ 
/*    */   public RedirectedEvent(Event paramEvent)
/*    */   {
/* 22 */     this(paramEvent, null, null);
/*    */   }
/*    */ 
/*    */   public RedirectedEvent(Event paramEvent, Object paramObject, EventTarget paramEventTarget)
/*    */   {
/* 28 */     super(paramObject, paramEventTarget, REDIRECTED);
/* 29 */     this.originalEvent = paramEvent;
/*    */   }
/*    */ 
/*    */   public Event getOriginalEvent() {
/* 33 */     return this.originalEvent;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.event.RedirectedEvent
 * JD-Core Version:    0.6.2
 */