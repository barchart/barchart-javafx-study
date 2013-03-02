/*    */ package com.sun.javafx.scene;
/*    */ 
/*    */ import com.sun.javafx.event.BasicEventDispatcher;
/*    */ import com.sun.javafx.event.CompositeEventDispatcher;
/*    */ import com.sun.javafx.event.EventHandlerManager;
/*    */ 
/*    */ public class NodeEventDispatcher extends CompositeEventDispatcher
/*    */ {
/*    */   private final EnteredExitedHandler enteredExitedHandler;
/*    */   private final EventHandlerManager eventHandlerManager;
/*    */ 
/*    */   public NodeEventDispatcher(Object paramObject)
/*    */   {
/* 42 */     this(new EnteredExitedHandler(paramObject), new EventHandlerManager(paramObject));
/*    */   }
/*    */ 
/*    */   public NodeEventDispatcher(EnteredExitedHandler paramEnteredExitedHandler, EventHandlerManager paramEventHandlerManager)
/*    */   {
/* 49 */     this.enteredExitedHandler = paramEnteredExitedHandler;
/* 50 */     this.eventHandlerManager = paramEventHandlerManager;
/*    */ 
/* 52 */     paramEnteredExitedHandler.insertNextDispatcher(paramEventHandlerManager);
/*    */   }
/*    */ 
/*    */   public final EnteredExitedHandler getEnteredExitedHandler() {
/* 56 */     return this.enteredExitedHandler;
/*    */   }
/*    */ 
/*    */   public final EventHandlerManager getEventHandlerManager() {
/* 60 */     return this.eventHandlerManager;
/*    */   }
/*    */ 
/*    */   public BasicEventDispatcher getFirstDispatcher()
/*    */   {
/* 65 */     return this.enteredExitedHandler;
/*    */   }
/*    */ 
/*    */   public BasicEventDispatcher getLastDispatcher()
/*    */   {
/* 70 */     return this.eventHandlerManager;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.NodeEventDispatcher
 * JD-Core Version:    0.6.2
 */