/*    */ package com.sun.javafx.event;
/*    */ 
/*    */ import javafx.event.Event;
/*    */ import javafx.event.EventDispatchChain;
/*    */ import javafx.event.EventDispatcher;
/*    */ 
/*    */ public abstract class BasicEventDispatcher
/*    */   implements EventDispatcher
/*    */ {
/*    */   private BasicEventDispatcher previousDispatcher;
/*    */   private BasicEventDispatcher nextDispatcher;
/*    */ 
/*    */   public Event dispatchEvent(Event paramEvent, EventDispatchChain paramEventDispatchChain)
/*    */   {
/* 31 */     paramEvent = dispatchCapturingEvent(paramEvent);
/* 32 */     if (paramEvent.isConsumed()) {
/* 33 */       return null;
/*    */     }
/* 35 */     paramEvent = paramEventDispatchChain.dispatchEvent(paramEvent);
/* 36 */     if (paramEvent != null) {
/* 37 */       paramEvent = dispatchBubblingEvent(paramEvent);
/* 38 */       if (paramEvent.isConsumed()) {
/* 39 */         return null;
/*    */       }
/*    */     }
/*    */ 
/* 43 */     return paramEvent;
/*    */   }
/*    */ 
/*    */   public Event dispatchCapturingEvent(Event paramEvent) {
/* 47 */     return paramEvent;
/*    */   }
/*    */ 
/*    */   public Event dispatchBubblingEvent(Event paramEvent) {
/* 51 */     return paramEvent;
/*    */   }
/*    */ 
/*    */   public final BasicEventDispatcher getPreviousDispatcher() {
/* 55 */     return this.previousDispatcher;
/*    */   }
/*    */ 
/*    */   public final BasicEventDispatcher getNextDispatcher() {
/* 59 */     return this.nextDispatcher;
/*    */   }
/*    */ 
/*    */   public final void insertNextDispatcher(BasicEventDispatcher paramBasicEventDispatcher)
/*    */   {
/* 64 */     if (this.nextDispatcher != null) {
/* 65 */       this.nextDispatcher.previousDispatcher = paramBasicEventDispatcher;
/*    */     }
/* 67 */     paramBasicEventDispatcher.nextDispatcher = this.nextDispatcher;
/* 68 */     paramBasicEventDispatcher.previousDispatcher = this;
/* 69 */     this.nextDispatcher = paramBasicEventDispatcher;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.event.BasicEventDispatcher
 * JD-Core Version:    0.6.2
 */