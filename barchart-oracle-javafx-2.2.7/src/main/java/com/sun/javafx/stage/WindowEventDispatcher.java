/*    */ package com.sun.javafx.stage;
/*    */ 
/*    */ import com.sun.javafx.event.BasicEventDispatcher;
/*    */ import com.sun.javafx.event.CompositeEventDispatcher;
/*    */ import com.sun.javafx.event.EventHandlerManager;
/*    */ import com.sun.javafx.event.EventRedirector;
/*    */ import javafx.stage.Window;
/*    */ 
/*    */ public class WindowEventDispatcher extends CompositeEventDispatcher
/*    */ {
/*    */   private final EventRedirector eventRedirector;
/*    */   private final WindowCloseRequestHandler windowCloseRequestHandler;
/*    */   private final EventHandlerManager eventHandlerManager;
/*    */ 
/*    */   public WindowEventDispatcher(Window paramWindow)
/*    */   {
/* 48 */     this(new EventRedirector(paramWindow), new WindowCloseRequestHandler(paramWindow), new EventHandlerManager(paramWindow));
/*    */   }
/*    */ 
/*    */   public WindowEventDispatcher(EventRedirector paramEventRedirector, WindowCloseRequestHandler paramWindowCloseRequestHandler, EventHandlerManager paramEventHandlerManager)
/*    */   {
/* 58 */     this.eventRedirector = paramEventRedirector;
/* 59 */     this.windowCloseRequestHandler = paramWindowCloseRequestHandler;
/* 60 */     this.eventHandlerManager = paramEventHandlerManager;
/*    */ 
/* 62 */     paramEventRedirector.insertNextDispatcher(paramWindowCloseRequestHandler);
/* 63 */     paramWindowCloseRequestHandler.insertNextDispatcher(paramEventHandlerManager);
/*    */   }
/*    */ 
/*    */   public final EventRedirector getEventRedirector() {
/* 67 */     return this.eventRedirector;
/*    */   }
/*    */ 
/*    */   public final WindowCloseRequestHandler getWindowCloseRequestHandler() {
/* 71 */     return this.windowCloseRequestHandler;
/*    */   }
/*    */ 
/*    */   public final EventHandlerManager getEventHandlerManager() {
/* 75 */     return this.eventHandlerManager;
/*    */   }
/*    */ 
/*    */   public BasicEventDispatcher getFirstDispatcher()
/*    */   {
/* 80 */     return this.eventRedirector;
/*    */   }
/*    */ 
/*    */   public BasicEventDispatcher getLastDispatcher()
/*    */   {
/* 85 */     return this.eventHandlerManager;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.stage.WindowEventDispatcher
 * JD-Core Version:    0.6.2
 */