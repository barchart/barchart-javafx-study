/*    */ package com.sun.javafx.stage;
/*    */ 
/*    */ import com.sun.javafx.event.BasicEventDispatcher;
/*    */ import javafx.event.Event;
/*    */ import javafx.stage.Window;
/*    */ import javafx.stage.WindowEvent;
/*    */ 
/*    */ public final class WindowCloseRequestHandler extends BasicEventDispatcher
/*    */ {
/*    */   private final Window window;
/*    */ 
/*    */   public WindowCloseRequestHandler(Window paramWindow)
/*    */   {
/* 37 */     this.window = paramWindow;
/*    */   }
/*    */ 
/*    */   public Event dispatchBubblingEvent(Event paramEvent)
/*    */   {
/* 42 */     if (paramEvent.getEventType() == WindowEvent.WINDOW_CLOSE_REQUEST)
/*    */     {
/* 45 */       this.window.hide();
/* 46 */       paramEvent.consume();
/*    */     }
/*    */ 
/* 49 */     return paramEvent;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.stage.WindowCloseRequestHandler
 * JD-Core Version:    0.6.2
 */