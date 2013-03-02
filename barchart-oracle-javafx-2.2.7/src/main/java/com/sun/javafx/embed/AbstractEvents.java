/*     */ package com.sun.javafx.embed;
/*     */ 
/*     */ import com.sun.javafx.tk.FocusCause;
/*     */ import javafx.event.EventType;
/*     */ import javafx.scene.input.InputEvent;
/*     */ import javafx.scene.input.KeyEvent;
/*     */ import javafx.scene.input.MouseButton;
/*     */ import javafx.scene.input.MouseEvent;
/*     */ import javafx.scene.input.ScrollEvent;
/*     */ 
/*     */ public class AbstractEvents
/*     */ {
/*     */   public static final int MOUSEEVENT_PRESSED = 0;
/*     */   public static final int MOUSEEVENT_RELEASED = 1;
/*     */   public static final int MOUSEEVENT_CLICKED = 2;
/*     */   public static final int MOUSEEVENT_ENTERED = 3;
/*     */   public static final int MOUSEEVENT_EXITED = 4;
/*     */   public static final int MOUSEEVENT_MOVED = 5;
/*     */   public static final int MOUSEEVENT_DRAGGED = 6;
/*     */   public static final int MOUSEEVENT_WHEEL = 7;
/*     */   public static final int MOUSEEVENT_NONE_BUTTON = 0;
/*     */   public static final int MOUSEEVENT_PRIMARY_BUTTON = 1;
/*     */   public static final int MOUSEEVENT_SECONDARY_BUTTON = 2;
/*     */   public static final int MOUSEEVENT_MIDDLE_BUTTON = 4;
/*     */   public static final int KEYEVENT_PRESSED = 0;
/*     */   public static final int KEYEVENT_RELEASED = 1;
/*     */   public static final int KEYEVENT_TYPED = 2;
/*     */   public static final int FOCUSEVENT_ACTIVATED = 0;
/*     */   public static final int FOCUSEVENT_TRAVERSED_FORWARD = 1;
/*     */   public static final int FOCUSEVENT_TRAVERSED_BACKWARD = 2;
/*     */   public static final int FOCUSEVENT_DEACTIVATED = 3;
/*     */   public static final int MODIFIER_SHIFT = 1;
/*     */   public static final int MODIFIER_CONTROL = 2;
/*     */   public static final int MODIFIER_ALT = 4;
/*     */   public static final int MODIFIER_META = 8;
/*     */ 
/*     */   public static EventType<? extends InputEvent> mouseIDToFXEventID(int paramInt)
/*     */   {
/*  73 */     switch (paramInt) {
/*     */     case 0:
/*  75 */       return MouseEvent.MOUSE_PRESSED;
/*     */     case 1:
/*  77 */       return MouseEvent.MOUSE_RELEASED;
/*     */     case 2:
/*  79 */       return MouseEvent.MOUSE_CLICKED;
/*     */     case 3:
/*  81 */       return MouseEvent.MOUSE_ENTERED;
/*     */     case 4:
/*  83 */       return MouseEvent.MOUSE_EXITED;
/*     */     case 5:
/*  85 */       return MouseEvent.MOUSE_MOVED;
/*     */     case 6:
/*  87 */       return MouseEvent.MOUSE_DRAGGED;
/*     */     case 7:
/*  89 */       return ScrollEvent.SCROLL;
/*     */     }
/*     */ 
/*  92 */     return MouseEvent.MOUSE_MOVED;
/*     */   }
/*     */ 
/*     */   public static MouseButton mouseButtonToFXMouseButton(int paramInt) {
/*  96 */     switch (paramInt) {
/*     */     case 1:
/*  98 */       return MouseButton.PRIMARY;
/*     */     case 2:
/* 100 */       return MouseButton.SECONDARY;
/*     */     case 4:
/* 102 */       return MouseButton.MIDDLE;
/*     */     case 3:
/*     */     }
/* 105 */     return MouseButton.NONE;
/*     */   }
/*     */ 
/*     */   public static EventType<KeyEvent> keyIDToFXEventType(int paramInt) {
/* 109 */     switch (paramInt) {
/*     */     case 0:
/* 111 */       return KeyEvent.KEY_PRESSED;
/*     */     case 1:
/* 113 */       return KeyEvent.KEY_RELEASED;
/*     */     case 2:
/* 115 */       return KeyEvent.KEY_TYPED;
/*     */     }
/*     */ 
/* 118 */     return KeyEvent.KEY_TYPED;
/*     */   }
/*     */ 
/*     */   public static FocusCause focusCauseToPeerFocusCause(int paramInt) {
/* 122 */     switch (paramInt) {
/*     */     case 0:
/* 124 */       return FocusCause.ACTIVATED;
/*     */     case 1:
/* 126 */       return FocusCause.TRAVERSED_FORWARD;
/*     */     case 2:
/* 128 */       return FocusCause.TRAVERSED_BACKWARD;
/*     */     case 3:
/* 130 */       return FocusCause.DEACTIVATED;
/*     */     }
/*     */ 
/* 133 */     return FocusCause.ACTIVATED;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.javafx.embed.AbstractEvents
 * JD-Core Version:    0.6.2
 */