/*     */ package javafx.scene.input;
/*     */ 
/*     */ import javafx.event.EventTarget;
/*     */ import javafx.event.EventType;
/*     */ 
/*     */ public class MouseDragEvent extends MouseEvent
/*     */ {
/*  60 */   public static final EventType<MouseDragEvent> ANY = new EventType(MouseEvent.ANY, "MOUSE-DRAG");
/*     */ 
/*  66 */   public static final EventType<MouseDragEvent> MOUSE_DRAG_OVER = new EventType(ANY, "MOUSE-DRAG_OVER");
/*     */ 
/*  73 */   public static final EventType<MouseDragEvent> MOUSE_DRAG_RELEASED = new EventType(ANY, "MOUSE-DRAG_RELEASED");
/*     */ 
/*  86 */   public static final EventType<MouseDragEvent> MOUSE_DRAG_ENTERED_TARGET = new EventType(ANY, "MOUSE-DRAG_ENTERED_TARGET");
/*     */ 
/*  97 */   public static final EventType<MouseDragEvent> MOUSE_DRAG_ENTERED = new EventType(MOUSE_DRAG_ENTERED_TARGET, "MOUSE-DRAG_ENTERED");
/*     */ 
/* 111 */   public static final EventType<MouseDragEvent> MOUSE_DRAG_EXITED_TARGET = new EventType(ANY, "MOUSE-DRAG_EXITED_TARGET");
/*     */ 
/* 122 */   public static final EventType<MouseDragEvent> MOUSE_DRAG_EXITED = new EventType(MOUSE_DRAG_EXITED_TARGET, "MOUSE-DRAG_EXITED");
/*     */   private Object gestureSource;
/*     */ 
/*     */   private MouseDragEvent(Object paramObject, EventTarget paramEventTarget, EventType<? extends MouseEvent> paramEventType)
/*     */   {
/* 128 */     super(paramObject, paramEventTarget, paramEventType);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public static MouseEvent impl_copy(Object paramObject1, EventTarget paramEventTarget, Object paramObject2, MouseEvent paramMouseEvent, EventType<? extends MouseEvent> paramEventType)
/*     */   {
/* 140 */     MouseDragEvent localMouseDragEvent = new MouseDragEvent(paramObject1, paramEventTarget, paramEventType);
/* 141 */     MouseEvent.copyFields(paramMouseEvent, localMouseDragEvent, paramObject1, paramEventTarget);
/* 142 */     localMouseDragEvent.gestureSource = paramObject2;
/*     */ 
/* 144 */     return localMouseDragEvent;
/*     */   }
/*     */ 
/*     */   public Object getGestureSource()
/*     */   {
/* 156 */     return this.gestureSource;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.input.MouseDragEvent
 * JD-Core Version:    0.6.2
 */