/*    */ package com.sun.javafx.stage;
/*    */ 
/*    */ import javafx.event.Event;
/*    */ import javafx.event.EventTarget;
/*    */ import javafx.event.EventType;
/*    */ 
/*    */ public final class FocusUngrabEvent extends Event
/*    */ {
/* 33 */   public static final EventType<FocusUngrabEvent> FOCUS_UNGRAB = new EventType();
/*    */ 
/*    */   public FocusUngrabEvent()
/*    */   {
/* 37 */     super(FOCUS_UNGRAB);
/*    */   }
/*    */ 
/*    */   public FocusUngrabEvent(Object paramObject, EventTarget paramEventTarget)
/*    */   {
/* 42 */     super(paramObject, paramEventTarget, FOCUS_UNGRAB);
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.stage.FocusUngrabEvent
 * JD-Core Version:    0.6.2
 */