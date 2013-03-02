/*     */ package com.sun.javafx.event;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javafx.event.Event;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.event.EventType;
/*     */ 
/*     */ public class EventHandlerManager extends BasicEventDispatcher
/*     */ {
/*     */   private final Map<EventType<? extends Event>, CompositeEventHandler<? extends Event>> eventHandlerMap;
/*     */   private final Object eventSource;
/*     */ 
/*     */   public EventHandlerManager(Object paramObject)
/*     */   {
/*  26 */     this.eventSource = paramObject;
/*     */ 
/*  28 */     this.eventHandlerMap = new HashMap();
/*     */   }
/*     */ 
/*     */   public final <T extends Event> void addEventHandler(EventType<T> paramEventType, EventHandler<? super T> paramEventHandler)
/*     */   {
/*  44 */     validateEventType(paramEventType);
/*  45 */     validateEventHandler(paramEventHandler);
/*     */ 
/*  47 */     CompositeEventHandler localCompositeEventHandler = createGetCompositeEventHandler(paramEventType);
/*     */ 
/*  50 */     localCompositeEventHandler.addEventHandler(paramEventHandler);
/*     */   }
/*     */ 
/*     */   public final <T extends Event> void removeEventHandler(EventType<T> paramEventType, EventHandler<? super T> paramEventHandler)
/*     */   {
/*  64 */     validateEventType(paramEventType);
/*  65 */     validateEventHandler(paramEventHandler);
/*     */ 
/*  67 */     CompositeEventHandler localCompositeEventHandler = (CompositeEventHandler)this.eventHandlerMap.get(paramEventType);
/*     */ 
/*  70 */     if (localCompositeEventHandler != null)
/*  71 */       localCompositeEventHandler.removeEventHandler(paramEventHandler);
/*     */   }
/*     */ 
/*     */   public final <T extends Event> void addEventFilter(EventType<T> paramEventType, EventHandler<? super T> paramEventHandler)
/*     */   {
/*  86 */     validateEventType(paramEventType);
/*  87 */     validateEventFilter(paramEventHandler);
/*     */ 
/*  89 */     CompositeEventHandler localCompositeEventHandler = createGetCompositeEventHandler(paramEventType);
/*     */ 
/*  92 */     localCompositeEventHandler.addEventFilter(paramEventHandler);
/*     */   }
/*     */ 
/*     */   public final <T extends Event> void removeEventFilter(EventType<T> paramEventType, EventHandler<? super T> paramEventHandler)
/*     */   {
/* 106 */     validateEventType(paramEventType);
/* 107 */     validateEventFilter(paramEventHandler);
/*     */ 
/* 109 */     CompositeEventHandler localCompositeEventHandler = (CompositeEventHandler)this.eventHandlerMap.get(paramEventType);
/*     */ 
/* 112 */     if (localCompositeEventHandler != null)
/* 113 */       localCompositeEventHandler.removeEventFilter(paramEventHandler);
/*     */   }
/*     */ 
/*     */   public final <T extends Event> void setEventHandler(EventType<T> paramEventType, EventHandler<? super T> paramEventHandler)
/*     */   {
/* 129 */     validateEventType(paramEventType);
/*     */ 
/* 131 */     CompositeEventHandler localCompositeEventHandler = (CompositeEventHandler)this.eventHandlerMap.get(paramEventType);
/*     */ 
/* 134 */     if (localCompositeEventHandler == null) {
/* 135 */       if (paramEventHandler == null) {
/* 136 */         return;
/*     */       }
/* 138 */       localCompositeEventHandler = new CompositeEventHandler();
/* 139 */       this.eventHandlerMap.put(paramEventType, localCompositeEventHandler);
/*     */     }
/*     */ 
/* 142 */     localCompositeEventHandler.setEventHandler(paramEventHandler);
/*     */   }
/*     */ 
/*     */   public final <T extends Event> EventHandler<? super T> getEventHandler(EventType<T> paramEventType)
/*     */   {
/* 147 */     CompositeEventHandler localCompositeEventHandler = (CompositeEventHandler)this.eventHandlerMap.get(paramEventType);
/*     */ 
/* 150 */     return localCompositeEventHandler != null ? localCompositeEventHandler.getEventHandler() : null;
/*     */   }
/*     */ 
/*     */   public final Event dispatchCapturingEvent(Event paramEvent)
/*     */   {
/* 157 */     EventType localEventType = paramEvent.getEventType();
/*     */     do {
/* 159 */       paramEvent = dispatchCapturingEvent(localEventType, paramEvent);
/* 160 */       localEventType = localEventType.getSuperType();
/* 161 */     }while (localEventType != null);
/*     */ 
/* 163 */     return paramEvent;
/*     */   }
/*     */ 
/*     */   public final Event dispatchBubblingEvent(Event paramEvent)
/*     */   {
/* 168 */     EventType localEventType = paramEvent.getEventType();
/*     */     do {
/* 170 */       paramEvent = dispatchBubblingEvent(localEventType, paramEvent);
/* 171 */       localEventType = localEventType.getSuperType();
/* 172 */     }while (localEventType != null);
/*     */ 
/* 174 */     return paramEvent;
/*     */   }
/*     */ 
/*     */   private <T extends Event> CompositeEventHandler<T> createGetCompositeEventHandler(EventType<T> paramEventType)
/*     */   {
/* 179 */     CompositeEventHandler localCompositeEventHandler = (CompositeEventHandler)this.eventHandlerMap.get(paramEventType);
/*     */ 
/* 181 */     if (localCompositeEventHandler == null) {
/* 182 */       localCompositeEventHandler = new CompositeEventHandler();
/* 183 */       this.eventHandlerMap.put(paramEventType, localCompositeEventHandler);
/*     */     }
/*     */ 
/* 186 */     return localCompositeEventHandler;
/*     */   }
/*     */ 
/*     */   protected Object getEventSource() {
/* 190 */     return this.eventSource;
/*     */   }
/*     */ 
/*     */   private Event dispatchCapturingEvent(EventType<? extends Event> paramEventType, Event paramEvent)
/*     */   {
/* 195 */     CompositeEventHandler localCompositeEventHandler = (CompositeEventHandler)this.eventHandlerMap.get(paramEventType);
/*     */ 
/* 198 */     if (localCompositeEventHandler != null)
/*     */     {
/* 201 */       paramEvent = fixEventSource(paramEvent, this.eventSource);
/* 202 */       localCompositeEventHandler.dispatchCapturingEvent(paramEvent);
/*     */     }
/*     */ 
/* 205 */     return paramEvent;
/*     */   }
/*     */ 
/*     */   private Event dispatchBubblingEvent(EventType<? extends Event> paramEventType, Event paramEvent)
/*     */   {
/* 210 */     CompositeEventHandler localCompositeEventHandler = (CompositeEventHandler)this.eventHandlerMap.get(paramEventType);
/*     */ 
/* 213 */     if (localCompositeEventHandler != null)
/*     */     {
/* 216 */       paramEvent = fixEventSource(paramEvent, this.eventSource);
/* 217 */       localCompositeEventHandler.dispatchBubblingEvent(paramEvent);
/*     */     }
/*     */ 
/* 220 */     return paramEvent;
/*     */   }
/*     */ 
/*     */   private static Event fixEventSource(Event paramEvent, Object paramObject)
/*     */   {
/* 225 */     return paramEvent.getSource() != paramObject ? paramEvent.copyFor(paramObject, paramEvent.getTarget()) : paramEvent;
/*     */   }
/*     */ 
/*     */   private static void validateEventType(EventType<?> paramEventType)
/*     */   {
/* 231 */     if (paramEventType == null)
/* 232 */       throw new NullPointerException("Event type must not be null");
/*     */   }
/*     */ 
/*     */   private static void validateEventHandler(EventHandler<?> paramEventHandler)
/*     */   {
/* 238 */     if (paramEventHandler == null)
/* 239 */       throw new NullPointerException("Event handler must not be null");
/*     */   }
/*     */ 
/*     */   private static void validateEventFilter(EventHandler<?> paramEventHandler)
/*     */   {
/* 245 */     if (paramEventHandler == null)
/* 246 */       throw new NullPointerException("Event filter must not be null");
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.event.EventHandlerManager
 * JD-Core Version:    0.6.2
 */