/*    */ package com.sun.javafx.util;
/*    */ 
/*    */ import javafx.event.Event;
/*    */ import javafx.event.EventType;
/*    */ 
/*    */ public class MessageBusEvent<M> extends Event
/*    */ {
/*    */   private static final long serialVersionUID = 0L;
/*    */   private M message;
/* 18 */   public static final EventType<MessageBusEvent> MESSAGE = new EventType();
/*    */ 
/*    */   public MessageBusEvent(M paramM)
/*    */   {
/* 22 */     super(MessageBus.class, null, MESSAGE);
/*    */ 
/* 24 */     this.message = paramM;
/*    */   }
/*    */ 
/*    */   public M getMessage() {
/* 28 */     return this.message;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.util.MessageBusEvent
 * JD-Core Version:    0.6.2
 */