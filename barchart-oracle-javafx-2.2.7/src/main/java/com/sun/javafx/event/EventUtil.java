/*    */ package com.sun.javafx.event;
/*    */ 
/*    */ import java.util.concurrent.atomic.AtomicBoolean;
/*    */ import javafx.event.Event;
/*    */ import javafx.event.EventDispatchChain;
/*    */ import javafx.event.EventTarget;
/*    */ 
/*    */ public final class EventUtil
/*    */ {
/* 14 */   private static final EventDispatchChainImpl eventDispatchChain = new EventDispatchChainImpl();
/*    */ 
/* 17 */   private static final AtomicBoolean eventDispatchChainInUse = new AtomicBoolean();
/*    */ 
/*    */   public static Event fireEvent(EventTarget paramEventTarget, Event paramEvent)
/*    */   {
/* 21 */     if (paramEvent.getTarget() != paramEventTarget) {
/* 22 */       paramEvent = paramEvent.copyFor(paramEvent.getSource(), paramEventTarget);
/*    */     }
/*    */ 
/* 25 */     if (eventDispatchChainInUse.getAndSet(true))
/*    */     {
/* 28 */       return fireEventImpl(new EventDispatchChainImpl(), paramEventTarget, paramEvent);
/*    */     }
/*    */ 
/*    */     try
/*    */     {
/* 33 */       return fireEventImpl(eventDispatchChain, paramEventTarget, paramEvent);
/*    */     }
/*    */     finally
/*    */     {
/* 37 */       eventDispatchChain.reset();
/* 38 */       eventDispatchChainInUse.set(false);
/*    */     }
/*    */   }
/*    */ 
/*    */   public static Event fireEvent(Event paramEvent, EventTarget[] paramArrayOfEventTarget) {
/* 43 */     return fireEventImpl(new EventDispatchTreeImpl(), new CompositeEventTargetImpl(paramArrayOfEventTarget), paramEvent);
/*    */   }
/*    */ 
/*    */   private static Event fireEventImpl(EventDispatchChain paramEventDispatchChain, EventTarget paramEventTarget, Event paramEvent)
/*    */   {
/* 51 */     EventDispatchChain localEventDispatchChain = paramEventTarget.buildEventDispatchChain(paramEventDispatchChain);
/*    */ 
/* 53 */     return localEventDispatchChain.dispatchEvent(paramEvent);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.event.EventUtil
 * JD-Core Version:    0.6.2
 */