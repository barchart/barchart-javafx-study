/*    */ package com.sun.javafx.util;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import javafx.event.EventHandler;
/*    */ 
/*    */ public class MessageBus
/*    */ {
/* 15 */   private static HashMap<Class<?>, ArrayList<?>> topics = new HashMap();
/*    */ 
/*    */   public static <M> void subscribe(Class<M> paramClass, EventHandler<MessageBusEvent<M>> paramEventHandler)
/*    */   {
/* 25 */     ArrayList localArrayList = (ArrayList)topics.get(paramClass);
/*    */ 
/* 28 */     if (localArrayList == null) {
/* 29 */       localArrayList = new ArrayList();
/* 30 */       topics.put(paramClass, localArrayList);
/*    */     }
/*    */ 
/* 33 */     localArrayList.add(paramEventHandler);
/*    */   }
/*    */ 
/*    */   public static <M> void unsubscribe(Class<M> paramClass, EventHandler<MessageBusEvent<M>> paramEventHandler)
/*    */   {
/* 44 */     ArrayList localArrayList = (ArrayList)topics.get(paramClass);
/*    */ 
/* 47 */     if (localArrayList == null) {
/* 48 */       throw new IllegalArgumentException(paramClass.getName() + " does not exist.");
/*    */     }
/*    */ 
/* 51 */     localArrayList.remove(paramEventHandler);
/*    */ 
/* 53 */     if (localArrayList.isEmpty())
/* 54 */       topics.remove(paramClass);
/*    */   }
/*    */ 
/*    */   public static <M> void sendMessage(M paramM)
/*    */   {
/* 65 */     Class localClass = paramM.getClass();
/*    */ 
/* 67 */     while (localClass != null) {
/* 68 */       ArrayList localArrayList = (ArrayList)topics.get(localClass);
/*    */ 
/* 71 */       MessageBusEvent localMessageBusEvent = new MessageBusEvent(paramM);
/*    */ 
/* 73 */       if (localArrayList != null) {
/* 74 */         for (EventHandler localEventHandler : localArrayList) {
/* 75 */           localEventHandler.handle(localMessageBusEvent);
/*    */         }
/*    */       }
/*    */ 
/* 79 */       localClass = localClass.getSuperclass();
/*    */     }
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.util.MessageBus
 * JD-Core Version:    0.6.2
 */