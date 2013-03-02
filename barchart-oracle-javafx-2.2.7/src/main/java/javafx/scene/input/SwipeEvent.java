/*     */ package javafx.scene.input;
/*     */ 
/*     */ import javafx.event.EventTarget;
/*     */ import javafx.event.EventType;
/*     */ 
/*     */ public class SwipeEvent extends GestureEvent
/*     */ {
/*  63 */   public static final EventType<SwipeEvent> ANY = new EventType(GestureEvent.ANY, "ANY_SWIPE");
/*     */ 
/*  69 */   public static final EventType<SwipeEvent> SWIPE_LEFT = new EventType(ANY, "SWIPE_LEFT");
/*     */ 
/*  75 */   public static final EventType<SwipeEvent> SWIPE_RIGHT = new EventType(ANY, "SWIPE_RIGHT");
/*     */ 
/*  81 */   public static final EventType<SwipeEvent> SWIPE_UP = new EventType(ANY, "SWIPE_UP");
/*     */ 
/*  87 */   public static final EventType<SwipeEvent> SWIPE_DOWN = new EventType(ANY, "SWIPE_DOWN");
/*     */   private int touchCount;
/*     */ 
/*     */   private SwipeEvent(EventType<? extends SwipeEvent> paramEventType)
/*     */   {
/*  91 */     super(paramEventType);
/*     */   }
/*     */ 
/*     */   private SwipeEvent(Object paramObject, EventTarget paramEventTarget, EventType<? extends SwipeEvent> paramEventType)
/*     */   {
/*  96 */     super(paramObject, paramEventTarget, paramEventType);
/*     */   }
/*     */ 
/*     */   private SwipeEvent(EventType<? extends SwipeEvent> paramEventType, int paramInt, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5)
/*     */   {
/* 109 */     super(paramEventType, paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramBoolean1, paramBoolean2, paramBoolean3, paramBoolean4, paramBoolean5, false);
/*     */ 
/* 111 */     this.touchCount = paramInt;
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public static SwipeEvent impl_swipeEvent(EventType<? extends SwipeEvent> paramEventType, int paramInt, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5)
/*     */   {
/* 127 */     return new SwipeEvent(paramEventType, paramInt, paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramBoolean1, paramBoolean2, paramBoolean3, paramBoolean4, paramBoolean5);
/*     */   }
/*     */ 
/*     */   public int getTouchCount()
/*     */   {
/* 140 */     return this.touchCount;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 148 */     StringBuilder localStringBuilder = new StringBuilder("SwipeEvent [");
/*     */ 
/* 150 */     localStringBuilder.append("source = ").append(getSource());
/* 151 */     localStringBuilder.append(", target = ").append(getTarget());
/* 152 */     localStringBuilder.append(", eventType = ").append(getEventType());
/* 153 */     localStringBuilder.append(", consumed = ").append(isConsumed());
/* 154 */     localStringBuilder.append(", touchCount = ").append(getTouchCount());
/*     */ 
/* 156 */     localStringBuilder.append(", x = ").append(getX()).append(", y = ").append(getY());
/* 157 */     localStringBuilder.append(isDirect() ? ", direct" : ", indirect");
/*     */ 
/* 159 */     if (isShiftDown()) {
/* 160 */       localStringBuilder.append(", shiftDown");
/*     */     }
/* 162 */     if (isControlDown()) {
/* 163 */       localStringBuilder.append(", controlDown");
/*     */     }
/* 165 */     if (isAltDown()) {
/* 166 */       localStringBuilder.append(", altDown");
/*     */     }
/* 168 */     if (isMetaDown()) {
/* 169 */       localStringBuilder.append(", metaDown");
/*     */     }
/* 171 */     if (isShortcutDown()) {
/* 172 */       localStringBuilder.append(", shortcutDown");
/*     */     }
/*     */ 
/* 175 */     return localStringBuilder.append("]").toString();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.input.SwipeEvent
 * JD-Core Version:    0.6.2
 */