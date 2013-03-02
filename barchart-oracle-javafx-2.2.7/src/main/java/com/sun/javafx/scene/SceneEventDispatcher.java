/*    */ package com.sun.javafx.scene;
/*    */ 
/*    */ import com.sun.javafx.event.BasicEventDispatcher;
/*    */ import com.sun.javafx.event.CompositeEventDispatcher;
/*    */ import com.sun.javafx.event.EventHandlerManager;
/*    */ 
/*    */ public class SceneEventDispatcher extends CompositeEventDispatcher
/*    */ {
/*    */   private final KeyboardShortcutsHandler keyboardShortcutsHandler;
/*    */   private final EnteredExitedHandler enteredExitedHandler;
/*    */   private final EventHandlerManager eventHandlerManager;
/*    */ 
/*    */   public SceneEventDispatcher(Object paramObject)
/*    */   {
/* 44 */     this(new KeyboardShortcutsHandler(), new EnteredExitedHandler(paramObject), new EventHandlerManager(paramObject));
/*    */   }
/*    */ 
/*    */   public SceneEventDispatcher(KeyboardShortcutsHandler paramKeyboardShortcutsHandler, EnteredExitedHandler paramEnteredExitedHandler, EventHandlerManager paramEventHandlerManager)
/*    */   {
/* 54 */     this.keyboardShortcutsHandler = paramKeyboardShortcutsHandler;
/* 55 */     this.enteredExitedHandler = paramEnteredExitedHandler;
/* 56 */     this.eventHandlerManager = paramEventHandlerManager;
/*    */ 
/* 58 */     paramKeyboardShortcutsHandler.insertNextDispatcher(paramEnteredExitedHandler);
/* 59 */     paramEnteredExitedHandler.insertNextDispatcher(paramEventHandlerManager);
/*    */   }
/*    */ 
/*    */   public final KeyboardShortcutsHandler getKeyboardShortcutsHandler()
/*    */   {
/* 64 */     return this.keyboardShortcutsHandler;
/*    */   }
/*    */ 
/*    */   public final EnteredExitedHandler getEnteredExitedHandler() {
/* 68 */     return this.enteredExitedHandler;
/*    */   }
/*    */ 
/*    */   public final EventHandlerManager getEventHandlerManager() {
/* 72 */     return this.eventHandlerManager;
/*    */   }
/*    */ 
/*    */   public BasicEventDispatcher getFirstDispatcher()
/*    */   {
/* 77 */     return this.keyboardShortcutsHandler;
/*    */   }
/*    */ 
/*    */   public BasicEventDispatcher getLastDispatcher()
/*    */   {
/* 82 */     return this.eventHandlerManager;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.scene.SceneEventDispatcher
 * JD-Core Version:    0.6.2
 */