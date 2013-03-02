/*    */ package com.sun.javafx.event;
/*    */ 
/*    */ import javafx.event.Event;
/*    */ 
/*    */ public abstract class CompositeEventDispatcher extends BasicEventDispatcher
/*    */ {
/*    */   public abstract BasicEventDispatcher getFirstDispatcher();
/*    */ 
/*    */   public abstract BasicEventDispatcher getLastDispatcher();
/*    */ 
/*    */   public final Event dispatchCapturingEvent(Event paramEvent)
/*    */   {
/* 20 */     BasicEventDispatcher localBasicEventDispatcher = getFirstDispatcher();
/* 21 */     while (localBasicEventDispatcher != null) {
/* 22 */       paramEvent = localBasicEventDispatcher.dispatchCapturingEvent(paramEvent);
/* 23 */       if (paramEvent.isConsumed())
/*    */       {
/*    */         break;
/*    */       }
/* 27 */       localBasicEventDispatcher = localBasicEventDispatcher.getNextDispatcher();
/*    */     }
/*    */ 
/* 30 */     return paramEvent;
/*    */   }
/*    */ 
/*    */   public final Event dispatchBubblingEvent(Event paramEvent)
/*    */   {
/* 36 */     BasicEventDispatcher localBasicEventDispatcher = getLastDispatcher();
/* 37 */     while (localBasicEventDispatcher != null) {
/* 38 */       paramEvent = localBasicEventDispatcher.dispatchBubblingEvent(paramEvent);
/* 39 */       if (paramEvent.isConsumed())
/*    */       {
/*    */         break;
/*    */       }
/* 43 */       localBasicEventDispatcher = localBasicEventDispatcher.getPreviousDispatcher();
/*    */     }
/*    */ 
/* 46 */     return paramEvent;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.event.CompositeEventDispatcher
 * JD-Core Version:    0.6.2
 */