/*     */ package javafx.scene.input;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javafx.event.Event;
/*     */ import javafx.event.EventTarget;
/*     */ import javafx.event.EventType;
/*     */ 
/*     */ public final class TouchEvent extends InputEvent
/*     */ {
/*  64 */   public static final EventType<TouchEvent> ANY = new EventType(InputEvent.ANY);
/*     */ 
/*  71 */   public static final EventType<TouchEvent> TOUCH_PRESSED = new EventType(ANY, "TOUCH_PRESSED");
/*     */ 
/*  77 */   public static final EventType<TouchEvent> TOUCH_MOVED = new EventType(ANY, "TOUCH_MOVED");
/*     */ 
/*  83 */   public static final EventType<TouchEvent> TOUCH_RELEASED = new EventType(ANY, "TOUCH_RELEASED");
/*     */ 
/*  90 */   public static final EventType<TouchEvent> TOUCH_STATIONARY = new EventType(ANY, "TOUCH_STATIONARY");
/*     */   private boolean isDirect;
/*     */   private int eventSetId;
/*     */   private boolean shiftDown;
/*     */   private boolean controlDown;
/*     */   private boolean altDown;
/*     */   private boolean metaDown;
/*     */   private TouchPoint touchPoint;
/*     */   private List<TouchPoint> touchPoints;
/*     */ 
/*     */   private TouchEvent(EventType<? extends TouchEvent> paramEventType)
/*     */   {
/*  94 */     super(paramEventType);
/*     */   }
/*     */ 
/*     */   private TouchEvent(EventType<? extends TouchEvent> paramEventType, TouchPoint paramTouchPoint, List<TouchPoint> paramList, int paramInt, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
/*     */   {
/* 101 */     super(paramEventType);
/* 102 */     if (paramList != null) {
/* 103 */       this.touchPoints = Collections.unmodifiableList(paramList);
/*     */     }
/* 105 */     this.eventSetId = paramInt;
/* 106 */     this.shiftDown = paramBoolean1;
/* 107 */     this.controlDown = paramBoolean2;
/* 108 */     this.altDown = paramBoolean3;
/* 109 */     this.metaDown = paramBoolean4;
/* 110 */     this.touchPoint = paramTouchPoint;
/*     */   }
/*     */ 
/*     */   public int getTouchCount()
/*     */   {
/* 119 */     return this.touchPoints.size();
/*     */   }
/*     */ 
/*     */   private static void recomputeToSource(TouchEvent paramTouchEvent, Object paramObject1, Object paramObject2)
/*     */   {
/* 131 */     for (TouchPoint localTouchPoint : paramTouchEvent.touchPoints)
/* 132 */       localTouchPoint.recomputeToSource(paramObject1, paramObject2);
/*     */   }
/*     */ 
/*     */   public Event copyFor(Object paramObject, EventTarget paramEventTarget)
/*     */   {
/* 142 */     TouchEvent localTouchEvent = (TouchEvent)super.copyFor(paramObject, paramEventTarget);
/* 143 */     recomputeToSource(localTouchEvent, getSource(), paramObject);
/*     */ 
/* 145 */     return localTouchEvent;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public boolean impl_isDirect()
/*     */   {
/* 159 */     return this.isDirect;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void impl_setDirect(boolean paramBoolean)
/*     */   {
/* 168 */     this.isDirect = paramBoolean;
/*     */   }
/*     */ 
/*     */   public final int getEventSetId()
/*     */   {
/* 186 */     return this.eventSetId;
/*     */   }
/*     */ 
/*     */   public final boolean isShiftDown()
/*     */   {
/* 200 */     return this.shiftDown;
/*     */   }
/*     */ 
/*     */   public final boolean isControlDown()
/*     */   {
/* 213 */     return this.controlDown;
/*     */   }
/*     */ 
/*     */   public final boolean isAltDown()
/*     */   {
/* 226 */     return this.altDown;
/*     */   }
/*     */ 
/*     */   public final boolean isMetaDown()
/*     */   {
/* 239 */     return this.metaDown;
/*     */   }
/*     */ 
/*     */   public TouchPoint getTouchPoint()
/*     */   {
/* 249 */     return this.touchPoint;
/*     */   }
/*     */ 
/*     */   public List<TouchPoint> getTouchPoints()
/*     */   {
/* 265 */     return this.touchPoints;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 273 */     StringBuilder localStringBuilder = new StringBuilder("TouchEvent [");
/*     */ 
/* 275 */     localStringBuilder.append("source = ").append(getSource());
/* 276 */     localStringBuilder.append(", target = ").append(getTarget());
/* 277 */     localStringBuilder.append(", eventType = ").append(getEventType());
/* 278 */     localStringBuilder.append(", consumed = ").append(isConsumed());
/* 279 */     localStringBuilder.append(", touchCount = ").append(getTouchCount());
/* 280 */     localStringBuilder.append(", eventSetId = ").append(getEventSetId());
/*     */ 
/* 282 */     localStringBuilder.append(", touchPoint = ").append(getTouchPoint().toString());
/*     */ 
/* 284 */     return "]";
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public static TouchEvent impl_touchEvent(EventType<? extends TouchEvent> paramEventType, TouchPoint paramTouchPoint, List<TouchPoint> paramList, int paramInt, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
/*     */   {
/* 296 */     return new TouchEvent(paramEventType, paramTouchPoint, paramList, paramInt, paramBoolean1, paramBoolean2, paramBoolean3, paramBoolean4);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.input.TouchEvent
 * JD-Core Version:    0.6.2
 */