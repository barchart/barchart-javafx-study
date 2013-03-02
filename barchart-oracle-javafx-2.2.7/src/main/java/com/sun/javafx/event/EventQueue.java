/*    */ package com.sun.javafx.event;
/*    */ 
/*    */ import java.util.ArrayDeque;
/*    */ import java.util.Queue;
/*    */ import javafx.event.Event;
/*    */ 
/*    */ public final class EventQueue
/*    */ {
/* 12 */   private Queue<Event> queue = new ArrayDeque();
/*    */   private boolean inLoop;
/*    */ 
/*    */   public void postEvent(Event paramEvent)
/*    */   {
/* 16 */     this.queue.add(paramEvent);
/*    */   }
/*    */ 
/*    */   public void fire() {
/* 20 */     if (this.inLoop) {
/* 21 */       return;
/*    */     }
/* 23 */     this.inLoop = true;
/*    */     try {
/* 25 */       while (!this.queue.isEmpty()) {
/* 26 */         Event localEvent = (Event)this.queue.remove();
/* 27 */         Event.fireEvent(localEvent.getTarget(), localEvent);
/*    */       }
/*    */     } finally {
/* 30 */       this.inLoop = false;
/*    */     }
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.event.EventQueue
 * JD-Core Version:    0.6.2
 */