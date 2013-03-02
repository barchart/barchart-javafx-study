/*     */ package javafx.scene.input;
/*     */ 
/*     */ import com.sun.javafx.scene.input.InputEventUtils;
/*     */ import javafx.event.Event;
/*     */ import javafx.event.EventTarget;
/*     */ import javafx.event.EventType;
/*     */ import javafx.geometry.Point2D;
/*     */ 
/*     */ public class GestureEvent extends InputEvent
/*     */ {
/*  51 */   public static final EventType<GestureEvent> ANY = new EventType(InputEvent.ANY, "GESTURE");
/*     */   private double x;
/*     */   private double y;
/*     */   private double screenX;
/*     */   private double screenY;
/*     */   private double sceneX;
/*     */   private double sceneY;
/*     */   private boolean shiftDown;
/*     */   private boolean controlDown;
/*     */   private boolean altDown;
/*     */   private boolean metaDown;
/*     */   private boolean direct;
/*     */   private boolean inertia;
/*     */ 
/*     */   protected GestureEvent(EventType<? extends GestureEvent> paramEventType)
/*     */   {
/*  59 */     super(paramEventType);
/*     */   }
/*     */ 
/*     */   protected GestureEvent(Object paramObject, EventTarget paramEventTarget, EventType<? extends GestureEvent> paramEventType)
/*     */   {
/*  70 */     super(paramObject, paramEventTarget, paramEventType);
/*     */   }
/*     */ 
/*     */   GestureEvent(EventType<? extends GestureEvent> paramEventType, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, boolean paramBoolean6)
/*     */   {
/*  77 */     super(paramEventType);
/*  78 */     this.x = paramDouble1;
/*  79 */     this.y = paramDouble2;
/*  80 */     this.screenX = paramDouble3;
/*  81 */     this.screenY = paramDouble4;
/*  82 */     this.sceneX = paramDouble1;
/*  83 */     this.sceneY = paramDouble2;
/*  84 */     this.shiftDown = paramBoolean1;
/*  85 */     this.controlDown = paramBoolean2;
/*  86 */     this.altDown = paramBoolean3;
/*  87 */     this.metaDown = paramBoolean4;
/*  88 */     this.direct = paramBoolean5;
/*  89 */     this.inertia = paramBoolean6;
/*     */   }
/*     */ 
/*     */   private void recomputeCoordinatesToSource(GestureEvent paramGestureEvent, Object paramObject)
/*     */   {
/* 100 */     Point2D localPoint2D = InputEventUtils.recomputeCoordinates(new Point2D(this.sceneX, this.sceneY), null, paramObject);
/*     */ 
/* 103 */     paramGestureEvent.x = localPoint2D.getX();
/* 104 */     paramGestureEvent.y = localPoint2D.getY();
/*     */   }
/*     */ 
/*     */   public Event copyFor(Object paramObject, EventTarget paramEventTarget)
/*     */   {
/* 112 */     GestureEvent localGestureEvent = (GestureEvent)super.copyFor(paramObject, paramEventTarget);
/* 113 */     recomputeCoordinatesToSource(localGestureEvent, paramObject);
/* 114 */     return localGestureEvent;
/*     */   }
/*     */ 
/*     */   static void copyFields(GestureEvent paramGestureEvent1, GestureEvent paramGestureEvent2, Object paramObject, EventTarget paramEventTarget)
/*     */   {
/* 123 */     paramGestureEvent2.x = paramGestureEvent1.x;
/* 124 */     paramGestureEvent2.y = paramGestureEvent1.y;
/* 125 */     paramGestureEvent2.screenX = paramGestureEvent1.screenX;
/* 126 */     paramGestureEvent2.screenY = paramGestureEvent1.screenY;
/* 127 */     paramGestureEvent2.sceneX = paramGestureEvent1.sceneX;
/* 128 */     paramGestureEvent2.sceneY = paramGestureEvent1.sceneY;
/* 129 */     paramGestureEvent2.shiftDown = paramGestureEvent1.shiftDown;
/* 130 */     paramGestureEvent2.controlDown = paramGestureEvent1.controlDown;
/* 131 */     paramGestureEvent2.altDown = paramGestureEvent1.altDown;
/* 132 */     paramGestureEvent2.metaDown = paramGestureEvent1.metaDown;
/* 133 */     paramGestureEvent2.source = paramObject;
/* 134 */     paramGestureEvent2.target = paramEventTarget;
/*     */ 
/* 136 */     paramGestureEvent1.recomputeCoordinatesToSource(paramGestureEvent2, paramObject);
/*     */   }
/*     */ 
/*     */   public final double getX()
/*     */   {
/* 151 */     return this.x;
/*     */   }
/*     */ 
/*     */   public final double getY()
/*     */   {
/* 166 */     return this.y;
/*     */   }
/*     */ 
/*     */   public final double getScreenX()
/*     */   {
/* 178 */     return this.screenX;
/*     */   }
/*     */ 
/*     */   public final double getScreenY()
/*     */   {
/* 190 */     return this.screenY;
/*     */   }
/*     */ 
/*     */   public final double getSceneX()
/*     */   {
/* 207 */     return this.sceneX;
/*     */   }
/*     */ 
/*     */   public final double getSceneY()
/*     */   {
/* 224 */     return this.sceneY;
/*     */   }
/*     */ 
/*     */   public final boolean isShiftDown()
/*     */   {
/* 234 */     return this.shiftDown;
/*     */   }
/*     */ 
/*     */   public final boolean isControlDown()
/*     */   {
/* 244 */     return this.controlDown;
/*     */   }
/*     */ 
/*     */   public final boolean isAltDown()
/*     */   {
/* 254 */     return this.altDown;
/*     */   }
/*     */ 
/*     */   public final boolean isMetaDown()
/*     */   {
/* 264 */     return this.metaDown;
/*     */   }
/*     */ 
/*     */   public final boolean isDirect()
/*     */   {
/* 279 */     return this.direct;
/*     */   }
/*     */ 
/*     */   public boolean isInertia()
/*     */   {
/* 290 */     return this.inertia;
/*     */   }
/*     */ 
/*     */   public final boolean isShortcutDown()
/*     */   {
/* 303 */     switch (1.$SwitchMap$javafx$scene$input$KeyCode[com.sun.javafx.tk.Toolkit.getToolkit().getPlatformShortcutKey().ordinal()]) {
/*     */     case 1:
/* 305 */       return this.shiftDown;
/*     */     case 2:
/* 308 */       return this.controlDown;
/*     */     case 3:
/* 311 */       return this.altDown;
/*     */     case 4:
/* 314 */       return this.metaDown;
/*     */     }
/*     */ 
/* 317 */     return false;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 326 */     StringBuilder localStringBuilder = new StringBuilder("GestureEvent [");
/*     */ 
/* 328 */     localStringBuilder.append("source = ").append(getSource());
/* 329 */     localStringBuilder.append(", target = ").append(getTarget());
/* 330 */     localStringBuilder.append(", eventType = ").append(getEventType());
/* 331 */     localStringBuilder.append(", consumed = ").append(isConsumed());
/*     */ 
/* 333 */     localStringBuilder.append(", x = ").append(getX()).append(", y = ").append(getY());
/* 334 */     localStringBuilder.append(isDirect() ? ", direct" : ", indirect");
/*     */ 
/* 336 */     if (isShiftDown()) {
/* 337 */       localStringBuilder.append(", shiftDown");
/*     */     }
/* 339 */     if (isControlDown()) {
/* 340 */       localStringBuilder.append(", controlDown");
/*     */     }
/* 342 */     if (isAltDown()) {
/* 343 */       localStringBuilder.append(", altDown");
/*     */     }
/* 345 */     if (isMetaDown()) {
/* 346 */       localStringBuilder.append(", metaDown");
/*     */     }
/* 348 */     if (isShortcutDown()) {
/* 349 */       localStringBuilder.append(", shortcutDown");
/*     */     }
/*     */ 
/* 352 */     return localStringBuilder.append("]").toString();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.input.GestureEvent
 * JD-Core Version:    0.6.2
 */