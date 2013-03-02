/*    */ package com.sun.javafx.scene.control;
/*    */ 
/*    */ import java.lang.ref.WeakReference;
/*    */ import java.lang.reflect.Method;
/*    */ import javafx.event.Event;
/*    */ import javafx.event.EventHandler;
/*    */ import javafx.event.EventTarget;
/*    */ import javafx.event.EventType;
/*    */ 
/*    */ public class WeakEventHandler<T extends Event>
/*    */   implements EventHandler<T>
/*    */ {
/*    */   private final WeakReference<EventHandler<T>> ref;
/*    */   private final EventTarget target;
/*    */   private final EventType type;
/*    */ 
/*    */   public WeakEventHandler(EventTarget paramEventTarget, EventType paramEventType, EventHandler<T> paramEventHandler)
/*    */   {
/* 51 */     if (paramEventHandler == null) {
/* 52 */       throw new NullPointerException("Listener must be specified.");
/*    */     }
/* 54 */     this.target = paramEventTarget;
/* 55 */     this.type = paramEventType;
/* 56 */     this.ref = new WeakReference(paramEventHandler);
/*    */   }
/*    */ 
/*    */   public void handle(T paramT) {
/* 60 */     EventHandler localEventHandler = (EventHandler)this.ref.get();
/* 61 */     if (localEventHandler != null) {
/* 62 */       localEventHandler.handle(paramT);
/*    */     }
/*    */     else
/*    */     {
/*    */       try
/*    */       {
/* 68 */         Method localMethod = this.target.getClass().getMethod("removeEventHandler", new Class[] { EventType.class, EventHandler.class });
/* 69 */         localMethod.invoke(this.target, new Object[] { this.type, this });
/*    */       } catch (Exception localException) {
/* 71 */         localException.printStackTrace();
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.control.WeakEventHandler
 * JD-Core Version:    0.6.2
 */