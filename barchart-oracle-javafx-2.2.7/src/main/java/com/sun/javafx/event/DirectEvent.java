/*    */ package com.sun.javafx.event;
/*    */ 
/*    */ import javafx.event.Event;
/*    */ import javafx.event.EventTarget;
/*    */ import javafx.event.EventType;
/*    */ 
/*    */ public class DirectEvent extends Event
/*    */ {
/* 17 */   public static final EventType<DirectEvent> DIRECT = new EventType(Event.ANY, "DIRECT");
/*    */   private final Event originalEvent;
/*    */ 
/*    */   public DirectEvent(Event paramEvent)
/*    */   {
/* 23 */     this(paramEvent, null, null);
/*    */   }
/*    */ 
/*    */   public DirectEvent(Event paramEvent, Object paramObject, EventTarget paramEventTarget)
/*    */   {
/* 29 */     super(paramObject, paramEventTarget, DIRECT);
/* 30 */     this.originalEvent = paramEvent;
/*    */   }
/*    */ 
/*    */   public Event getOriginalEvent() {
/* 34 */     return this.originalEvent;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.event.DirectEvent
 * JD-Core Version:    0.6.2
 */