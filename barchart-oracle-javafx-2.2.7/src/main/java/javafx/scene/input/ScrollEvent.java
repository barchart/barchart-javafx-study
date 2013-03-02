/*     */ package javafx.scene.input;
/*     */ 
/*     */ import javafx.event.EventTarget;
/*     */ import javafx.event.EventType;
/*     */ 
/*     */ public class ScrollEvent extends GestureEvent
/*     */ {
/* 104 */   public static final EventType<ScrollEvent> ANY = new EventType(GestureEvent.ANY, "ANY_SCROLL");
/*     */ 
/* 111 */   public static final EventType<ScrollEvent> SCROLL = new EventType(ANY, "SCROLL");
/*     */ 
/* 118 */   public static final EventType<ScrollEvent> SCROLL_STARTED = new EventType(ANY, "SCROLL_STARTED");
/*     */ 
/* 125 */   public static final EventType<ScrollEvent> SCROLL_FINISHED = new EventType(ANY, "SCROLL_FINISHED");
/*     */   private double deltaX;
/*     */   private double deltaY;
/*     */   private double totalDeltaX;
/*     */   private double totalDeltaY;
/*     */   private HorizontalTextScrollUnits textDeltaXUnits;
/*     */   private VerticalTextScrollUnits textDeltaYUnits;
/*     */   private double textDeltaX;
/*     */   private double textDeltaY;
/*     */   private int touchCount;
/*     */ 
/*     */   private ScrollEvent(EventType<? extends ScrollEvent> paramEventType)
/*     */   {
/* 130 */     super(paramEventType);
/*     */   }
/*     */ 
/*     */   private ScrollEvent(Object paramObject, EventTarget paramEventTarget, EventType<? extends ScrollEvent> paramEventType)
/*     */   {
/* 135 */     super(paramObject, paramEventTarget, paramEventType);
/*     */   }
/*     */ 
/*     */   private ScrollEvent(EventType<? extends ScrollEvent> paramEventType, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, HorizontalTextScrollUnits paramHorizontalTextScrollUnits, double paramDouble5, VerticalTextScrollUnits paramVerticalTextScrollUnits, double paramDouble6, int paramInt, double paramDouble7, double paramDouble8, double paramDouble9, double paramDouble10, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, boolean paramBoolean6)
/*     */   {
/* 153 */     super(paramEventType, paramDouble7, paramDouble8, paramDouble9, paramDouble10, paramBoolean1, paramBoolean2, paramBoolean3, paramBoolean4, paramBoolean5, paramBoolean6);
/*     */ 
/* 155 */     this.deltaX = paramDouble1;
/* 156 */     this.deltaY = paramDouble2;
/* 157 */     this.totalDeltaX = paramDouble3;
/* 158 */     this.totalDeltaY = paramDouble4;
/* 159 */     this.textDeltaXUnits = paramHorizontalTextScrollUnits;
/* 160 */     this.textDeltaX = paramDouble5;
/* 161 */     this.textDeltaYUnits = paramVerticalTextScrollUnits;
/* 162 */     this.textDeltaY = paramDouble6;
/* 163 */     this.touchCount = paramInt;
/*     */   }
/*     */ 
/*     */   public double getDeltaX()
/*     */   {
/* 182 */     return this.deltaX;
/*     */   }
/*     */ 
/*     */   public double getDeltaY()
/*     */   {
/* 201 */     return this.deltaY;
/*     */   }
/*     */ 
/*     */   public double getTotalDeltaX()
/*     */   {
/* 220 */     return this.totalDeltaX;
/*     */   }
/*     */ 
/*     */   public double getTotalDeltaY()
/*     */   {
/* 239 */     return this.totalDeltaY;
/*     */   }
/*     */ 
/*     */   public HorizontalTextScrollUnits getTextDeltaXUnits()
/*     */   {
/* 254 */     return this.textDeltaXUnits;
/*     */   }
/*     */ 
/*     */   public VerticalTextScrollUnits getTextDeltaYUnits()
/*     */   {
/* 269 */     return this.textDeltaYUnits;
/*     */   }
/*     */ 
/*     */   public double getTextDeltaX()
/*     */   {
/* 283 */     return this.textDeltaX;
/*     */   }
/*     */ 
/*     */   public double getTextDeltaY()
/*     */   {
/* 297 */     return this.textDeltaY;
/*     */   }
/*     */ 
/*     */   public int getTouchCount()
/*     */   {
/* 309 */     return this.touchCount;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public static ScrollEvent impl_scrollEvent(EventType<ScrollEvent> paramEventType, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, HorizontalTextScrollUnits paramHorizontalTextScrollUnits, double paramDouble5, VerticalTextScrollUnits paramVerticalTextScrollUnits, double paramDouble6, int paramInt, double paramDouble7, double paramDouble8, double paramDouble9, double paramDouble10, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, boolean paramBoolean6)
/*     */   {
/* 333 */     return new ScrollEvent(paramEventType, paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramHorizontalTextScrollUnits, paramDouble5, paramVerticalTextScrollUnits, paramDouble6, paramInt, paramDouble7, paramDouble8, paramDouble9, paramDouble10, paramBoolean1, paramBoolean2, paramBoolean3, paramBoolean4, paramBoolean5, paramBoolean6);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 348 */     StringBuilder localStringBuilder = new StringBuilder("ScrollEvent [");
/*     */ 
/* 350 */     localStringBuilder.append("source = ").append(getSource());
/* 351 */     localStringBuilder.append(", target = ").append(getTarget());
/* 352 */     localStringBuilder.append(", eventType = ").append(getEventType());
/* 353 */     localStringBuilder.append(", consumed = ").append(isConsumed());
/*     */ 
/* 355 */     localStringBuilder.append(", deltaX = ").append(getDeltaX()).append(", deltaY = ").append(getDeltaY());
/*     */ 
/* 357 */     localStringBuilder.append(", totalDeltaX = ").append(getTotalDeltaX()).append(", totalDeltaY = ").append(getTotalDeltaY());
/*     */ 
/* 359 */     localStringBuilder.append(", textDeltaXUnits = ").append(getTextDeltaXUnits()).append(", textDeltaX = ").append(getTextDeltaX());
/*     */ 
/* 361 */     localStringBuilder.append(", textDeltaYUnits = ").append(getTextDeltaYUnits()).append(", textDeltaY = ").append(getTextDeltaY());
/*     */ 
/* 363 */     localStringBuilder.append(", touchCount = ").append(getTouchCount());
/* 364 */     localStringBuilder.append(", x = ").append(getX()).append(", y = ").append(getY());
/* 365 */     localStringBuilder.append(isDirect() ? ", direct" : ", indirect");
/*     */ 
/* 367 */     if (isShiftDown()) {
/* 368 */       localStringBuilder.append(", shiftDown");
/*     */     }
/* 370 */     if (isControlDown()) {
/* 371 */       localStringBuilder.append(", controlDown");
/*     */     }
/* 373 */     if (isAltDown()) {
/* 374 */       localStringBuilder.append(", altDown");
/*     */     }
/* 376 */     if (isMetaDown()) {
/* 377 */       localStringBuilder.append(", metaDown");
/*     */     }
/* 379 */     if (isShortcutDown()) {
/* 380 */       localStringBuilder.append(", shortcutDown");
/*     */     }
/*     */ 
/* 383 */     return localStringBuilder.append("]").toString();
/*     */   }
/*     */ 
/*     */   public static enum VerticalTextScrollUnits
/*     */   {
/* 411 */     NONE, 
/*     */ 
/* 417 */     LINES, 
/*     */ 
/* 423 */     PAGES;
/*     */   }
/*     */ 
/*     */   public static enum HorizontalTextScrollUnits
/*     */   {
/* 394 */     NONE, 
/*     */ 
/* 400 */     CHARACTERS;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.input.ScrollEvent
 * JD-Core Version:    0.6.2
 */