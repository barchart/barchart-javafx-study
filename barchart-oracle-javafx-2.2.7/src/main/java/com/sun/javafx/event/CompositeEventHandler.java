/*    */ package com.sun.javafx.event;
/*    */ 
/*    */ import java.util.List;
/*    */ import java.util.concurrent.CopyOnWriteArrayList;
/*    */ import javafx.event.Event;
/*    */ import javafx.event.EventHandler;
/*    */ 
/*    */ public final class CompositeEventHandler<T extends Event>
/*    */ {
/*    */   private List<EventHandler<? super T>> eventHandlers;
/*    */   private List<EventHandler<? super T>> eventFilters;
/*    */   private EventHandler<? super T> eventHandler;
/*    */ 
/*    */   public void setEventHandler(EventHandler<? super T> paramEventHandler)
/*    */   {
/* 20 */     this.eventHandler = paramEventHandler;
/*    */   }
/*    */ 
/*    */   public EventHandler<? super T> getEventHandler() {
/* 24 */     return this.eventHandler;
/*    */   }
/*    */ 
/*    */   public void addEventHandler(EventHandler<? super T> paramEventHandler) {
/* 28 */     if (this.eventHandlers == null) {
/* 29 */       this.eventHandlers = new CopyOnWriteArrayList();
/*    */     }
/*    */ 
/* 32 */     if (!this.eventHandlers.contains(paramEventHandler))
/* 33 */       this.eventHandlers.add(paramEventHandler);
/*    */   }
/*    */ 
/*    */   public void removeEventHandler(EventHandler<? super T> paramEventHandler)
/*    */   {
/* 38 */     if (this.eventHandlers != null)
/* 39 */       this.eventHandlers.remove(paramEventHandler);
/*    */   }
/*    */ 
/*    */   public void addEventFilter(EventHandler<? super T> paramEventHandler)
/*    */   {
/* 44 */     if (this.eventFilters == null) {
/* 45 */       this.eventFilters = new CopyOnWriteArrayList();
/*    */     }
/*    */ 
/* 48 */     if (!this.eventFilters.contains(paramEventHandler))
/* 49 */       this.eventFilters.add(paramEventHandler);
/*    */   }
/*    */ 
/*    */   public void removeEventFilter(EventHandler<? super T> paramEventHandler)
/*    */   {
/* 54 */     if (this.eventFilters != null)
/* 55 */       this.eventFilters.remove(paramEventHandler);
/*    */   }
/*    */ 
/*    */   public void dispatchBubblingEvent(Event paramEvent)
/*    */   {
/* 60 */     Event localEvent = paramEvent;
/*    */ 
/* 62 */     if (this.eventHandlers != null) {
/* 63 */       for (EventHandler localEventHandler : this.eventHandlers) {
/* 64 */         localEventHandler.handle(localEvent);
/*    */       }
/*    */     }
/*    */ 
/* 68 */     if (this.eventHandler != null)
/* 69 */       this.eventHandler.handle(localEvent);
/*    */   }
/*    */ 
/*    */   public void dispatchCapturingEvent(Event paramEvent)
/*    */   {
/* 74 */     Event localEvent = paramEvent;
/*    */ 
/* 76 */     if (this.eventFilters != null)
/* 77 */       for (EventHandler localEventHandler : this.eventFilters)
/* 78 */         localEventHandler.handle(localEvent);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.event.CompositeEventHandler
 * JD-Core Version:    0.6.2
 */