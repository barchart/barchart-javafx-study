/*    */ package com.sun.javafx.event;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.concurrent.CopyOnWriteArrayList;
/*    */ import javafx.event.Event;
/*    */ import javafx.event.EventDispatcher;
/*    */ import javafx.event.EventType;
/*    */ 
/*    */ public class EventRedirector extends BasicEventDispatcher
/*    */ {
/*    */   private final EventDispatchChainImpl eventDispatchChain;
/*    */   private final List<EventDispatcher> eventDispatchers;
/*    */   private final Object eventSource;
/*    */ 
/*    */   public EventRedirector(Object paramObject)
/*    */   {
/* 48 */     this.eventDispatchers = new CopyOnWriteArrayList();
/* 49 */     this.eventDispatchChain = new EventDispatchChainImpl();
/* 50 */     this.eventSource = paramObject;
/*    */   }
/*    */ 
/*    */   protected void handleRedirectedEvent(Object paramObject, Event paramEvent)
/*    */   {
/*    */   }
/*    */ 
/*    */   public final void addEventDispatcher(EventDispatcher paramEventDispatcher)
/*    */   {
/* 66 */     this.eventDispatchers.add(paramEventDispatcher);
/*    */   }
/*    */ 
/*    */   public final void removeEventDispatcher(EventDispatcher paramEventDispatcher)
/*    */   {
/* 71 */     this.eventDispatchers.remove(paramEventDispatcher);
/*    */   }
/*    */ 
/*    */   public final Event dispatchCapturingEvent(Event paramEvent)
/*    */   {
/* 76 */     EventType localEventType = paramEvent.getEventType();
/*    */ 
/* 78 */     if (localEventType == DirectEvent.DIRECT)
/*    */     {
/* 80 */       paramEvent = ((DirectEvent)paramEvent).getOriginalEvent();
/*    */     } else {
/* 82 */       redirectEvent(paramEvent);
/*    */ 
/* 84 */       if (localEventType == RedirectedEvent.REDIRECTED) {
/* 85 */         handleRedirectedEvent(paramEvent.getSource(), ((RedirectedEvent)paramEvent).getOriginalEvent());
/*    */       }
/*    */ 
/*    */     }
/*    */ 
/* 91 */     return paramEvent;
/*    */   }
/*    */ 
/*    */   private void redirectEvent(Event paramEvent)
/*    */   {
/*    */     RedirectedEvent localRedirectedEvent;
/* 95 */     if (!this.eventDispatchers.isEmpty()) {
/* 96 */       localRedirectedEvent = paramEvent.getEventType() == RedirectedEvent.REDIRECTED ? (RedirectedEvent)paramEvent : new RedirectedEvent(paramEvent, this.eventSource, null);
/*    */ 
/* 101 */       for (EventDispatcher localEventDispatcher : this.eventDispatchers) {
/* 102 */         this.eventDispatchChain.reset();
/* 103 */         localEventDispatcher.dispatchEvent(localRedirectedEvent, this.eventDispatchChain);
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.event.EventRedirector
 * JD-Core Version:    0.6.2
 */