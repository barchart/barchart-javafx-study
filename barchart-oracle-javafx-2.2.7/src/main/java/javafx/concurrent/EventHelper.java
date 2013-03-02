/*     */ package javafx.concurrent;
/*     */ 
/*     */ import com.sun.javafx.event.EventHandlerManager;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.SimpleObjectProperty;
/*     */ import javafx.event.Event;
/*     */ import javafx.event.EventDispatchChain;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.event.EventTarget;
/*     */ import javafx.event.EventType;
/*     */ 
/*     */ class EventHelper
/*     */ {
/*     */   private final EventTarget target;
/*     */   private final ObjectProperty<EventHandler<WorkerStateEvent>> onReady;
/*     */   private final ObjectProperty<EventHandler<WorkerStateEvent>> onScheduled;
/*     */   private final ObjectProperty<EventHandler<WorkerStateEvent>> onRunning;
/*     */   private final ObjectProperty<EventHandler<WorkerStateEvent>> onSucceeded;
/*     */   private final ObjectProperty<EventHandler<WorkerStateEvent>> onCancelled;
/*     */   private final ObjectProperty<EventHandler<WorkerStateEvent>> onFailed;
/*     */   private EventHandlerManager internalEventDispatcher;
/*     */ 
/*     */   final ObjectProperty<EventHandler<WorkerStateEvent>> onReadyProperty()
/*     */   {
/*  45 */     return this.onReady; } 
/*  46 */   final EventHandler<WorkerStateEvent> getOnReady() { return (EventHandler)this.onReady.get(); } 
/*  47 */   final void setOnReady(EventHandler<WorkerStateEvent> paramEventHandler) { this.onReady.set(paramEventHandler); }
/*     */ 
/*     */   final ObjectProperty<EventHandler<WorkerStateEvent>> onScheduledProperty() {
/*  50 */     return this.onScheduled; } 
/*  51 */   final EventHandler<WorkerStateEvent> getOnScheduled() { return (EventHandler)this.onScheduled.get(); } 
/*  52 */   final void setOnScheduled(EventHandler<WorkerStateEvent> paramEventHandler) { this.onScheduled.set(paramEventHandler); }
/*     */ 
/*     */   final ObjectProperty<EventHandler<WorkerStateEvent>> onRunningProperty() {
/*  55 */     return this.onRunning; } 
/*  56 */   final EventHandler<WorkerStateEvent> getOnRunning() { return (EventHandler)this.onRunning.get(); } 
/*  57 */   final void setOnRunning(EventHandler<WorkerStateEvent> paramEventHandler) { this.onRunning.set(paramEventHandler); }
/*     */ 
/*     */   final ObjectProperty<EventHandler<WorkerStateEvent>> onSucceededProperty() {
/*  60 */     return this.onSucceeded; } 
/*  61 */   final EventHandler<WorkerStateEvent> getOnSucceeded() { return (EventHandler)this.onSucceeded.get(); } 
/*  62 */   final void setOnSucceeded(EventHandler<WorkerStateEvent> paramEventHandler) { this.onSucceeded.set(paramEventHandler); }
/*     */ 
/*     */   final ObjectProperty<EventHandler<WorkerStateEvent>> onCancelledProperty() {
/*  65 */     return this.onCancelled; } 
/*  66 */   final EventHandler<WorkerStateEvent> getOnCancelled() { return (EventHandler)this.onCancelled.get(); } 
/*  67 */   final void setOnCancelled(EventHandler<WorkerStateEvent> paramEventHandler) { this.onCancelled.set(paramEventHandler); }
/*     */ 
/*     */   final ObjectProperty<EventHandler<WorkerStateEvent>> onFailedProperty() {
/*  70 */     return this.onFailed; } 
/*  71 */   final EventHandler<WorkerStateEvent> getOnFailed() { return (EventHandler)this.onFailed.get(); } 
/*  72 */   final void setOnFailed(EventHandler<WorkerStateEvent> paramEventHandler) { this.onFailed.set(paramEventHandler); }
/*     */ 
/*     */ 
/*     */   EventHelper(EventTarget paramEventTarget)
/*     */   {
/*  77 */     this.target = paramEventTarget;
/*  78 */     this.onReady = new SimpleObjectProperty(paramEventTarget, "onReady") {
/*     */       protected void invalidated() {
/*  80 */         EventHandler localEventHandler = (EventHandler)get();
/*  81 */         EventHelper.this.setEventHandler(WorkerStateEvent.WORKER_STATE_READY, localEventHandler);
/*     */       }
/*     */     };
/*  84 */     this.onScheduled = new SimpleObjectProperty(paramEventTarget, "onScheduled") {
/*     */       protected void invalidated() {
/*  86 */         EventHandler localEventHandler = (EventHandler)get();
/*  87 */         EventHelper.this.setEventHandler(WorkerStateEvent.WORKER_STATE_SCHEDULED, localEventHandler);
/*     */       }
/*     */     };
/*  90 */     this.onRunning = new SimpleObjectProperty(paramEventTarget, "onRunning") {
/*     */       protected void invalidated() {
/*  92 */         EventHandler localEventHandler = (EventHandler)get();
/*  93 */         EventHelper.this.setEventHandler(WorkerStateEvent.WORKER_STATE_RUNNING, localEventHandler);
/*     */       }
/*     */     };
/*  96 */     this.onSucceeded = new SimpleObjectProperty(paramEventTarget, "onSucceeded") {
/*     */       protected void invalidated() {
/*  98 */         EventHandler localEventHandler = (EventHandler)get();
/*  99 */         EventHelper.this.setEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, localEventHandler);
/*     */       }
/*     */     };
/* 102 */     this.onCancelled = new SimpleObjectProperty(paramEventTarget, "onCancelled") {
/*     */       protected void invalidated() {
/* 104 */         EventHandler localEventHandler = (EventHandler)get();
/* 105 */         EventHelper.this.setEventHandler(WorkerStateEvent.WORKER_STATE_CANCELLED, localEventHandler);
/*     */       }
/*     */     };
/* 108 */     this.onFailed = new SimpleObjectProperty(paramEventTarget, "onFailed") {
/*     */       protected void invalidated() {
/* 110 */         EventHandler localEventHandler = (EventHandler)get();
/* 111 */         EventHelper.this.setEventHandler(WorkerStateEvent.WORKER_STATE_FAILED, localEventHandler);
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   final <T extends Event> void addEventHandler(EventType<T> paramEventType, EventHandler<? super T> paramEventHandler)
/*     */   {
/* 130 */     getInternalEventDispatcher().addEventHandler(paramEventType, paramEventHandler);
/*     */   }
/*     */ 
/*     */   final <T extends Event> void removeEventHandler(EventType<T> paramEventType, EventHandler<? super T> paramEventHandler)
/*     */   {
/* 147 */     getInternalEventDispatcher().removeEventHandler(paramEventType, paramEventHandler);
/*     */   }
/*     */ 
/*     */   final <T extends Event> void addEventFilter(EventType<T> paramEventType, EventHandler<? super T> paramEventHandler)
/*     */   {
/* 162 */     getInternalEventDispatcher().addEventFilter(paramEventType, paramEventHandler);
/*     */   }
/*     */ 
/*     */   final <T extends Event> void removeEventFilter(EventType<T> paramEventType, EventHandler<? super T> paramEventHandler)
/*     */   {
/* 179 */     getInternalEventDispatcher().removeEventFilter(paramEventType, paramEventHandler);
/*     */   }
/*     */ 
/*     */   final <T extends Event> void setEventHandler(EventType<T> paramEventType, EventHandler<? super T> paramEventHandler)
/*     */   {
/* 196 */     getInternalEventDispatcher().setEventHandler(paramEventType, paramEventHandler);
/*     */   }
/*     */ 
/*     */   private EventHandlerManager getInternalEventDispatcher()
/*     */   {
/* 201 */     if (this.internalEventDispatcher == null) {
/* 202 */       this.internalEventDispatcher = new EventHandlerManager(this.target);
/*     */     }
/* 204 */     return this.internalEventDispatcher;
/*     */   }
/*     */ 
/*     */   final void fireEvent(Event paramEvent)
/*     */   {
/* 219 */     Event.fireEvent(this.target, paramEvent);
/*     */   }
/*     */ 
/*     */   EventDispatchChain buildEventDispatchChain(EventDispatchChain paramEventDispatchChain) {
/* 223 */     return this.internalEventDispatcher == null ? paramEventDispatchChain : paramEventDispatchChain.append(getInternalEventDispatcher());
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.concurrent.EventHelper
 * JD-Core Version:    0.6.2
 */