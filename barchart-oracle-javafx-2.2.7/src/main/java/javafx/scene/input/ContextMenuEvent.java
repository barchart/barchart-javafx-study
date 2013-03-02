/*     */ package javafx.scene.input;
/*     */ 
/*     */ import com.sun.javafx.scene.input.InputEventUtils;
/*     */ import javafx.event.Event;
/*     */ import javafx.event.EventTarget;
/*     */ import javafx.event.EventType;
/*     */ import javafx.geometry.Point2D;
/*     */ 
/*     */ public class ContextMenuEvent extends InputEvent
/*     */ {
/*  53 */   public static final EventType<ContextMenuEvent> CONTEXT_MENU_REQUESTED = new EventType(ANY, "CONTEXT_MENU_REQUESTED");
/*     */   private boolean keyboardTrigger;
/*     */   private double x;
/*     */   private double y;
/*     */   private double screenX;
/*     */   private double screenY;
/*     */   private double sceneX;
/*     */   private double sceneY;
/*     */ 
/*     */   private ContextMenuEvent(EventType<? extends ContextMenuEvent> paramEventType)
/*     */   {
/*  57 */     super(paramEventType);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public static ContextMenuEvent impl_contextEvent(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, boolean paramBoolean, EventType<? extends ContextMenuEvent> paramEventType)
/*     */   {
/*  70 */     ContextMenuEvent localContextMenuEvent = new ContextMenuEvent(paramEventType);
/*  71 */     localContextMenuEvent.x = paramDouble1;
/*  72 */     localContextMenuEvent.y = paramDouble2;
/*  73 */     localContextMenuEvent.screenX = paramDouble3;
/*  74 */     localContextMenuEvent.screenY = paramDouble4;
/*  75 */     localContextMenuEvent.sceneX = paramDouble1;
/*  76 */     localContextMenuEvent.sceneY = paramDouble2;
/*  77 */     localContextMenuEvent.keyboardTrigger = paramBoolean;
/*  78 */     return localContextMenuEvent;
/*     */   }
/*     */ 
/*     */   private void recomputeCoordinatesToSource(ContextMenuEvent paramContextMenuEvent, Object paramObject)
/*     */   {
/*  89 */     Point2D localPoint2D = InputEventUtils.recomputeCoordinates(new Point2D(this.sceneX, this.sceneY), null, paramObject);
/*     */ 
/*  92 */     paramContextMenuEvent.x = localPoint2D.getX();
/*  93 */     paramContextMenuEvent.y = localPoint2D.getY();
/*     */   }
/*     */ 
/*     */   public Event copyFor(Object paramObject, EventTarget paramEventTarget)
/*     */   {
/*  98 */     ContextMenuEvent localContextMenuEvent = (ContextMenuEvent)super.copyFor(paramObject, paramEventTarget);
/*  99 */     recomputeCoordinatesToSource(localContextMenuEvent, paramObject);
/* 100 */     return localContextMenuEvent;
/*     */   }
/*     */ 
/*     */   public boolean isKeyboardTrigger()
/*     */   {
/* 114 */     return this.keyboardTrigger;
/*     */   }
/*     */ 
/*     */   public final double getX()
/*     */   {
/* 133 */     return this.x;
/*     */   }
/*     */ 
/*     */   public final double getY()
/*     */   {
/* 152 */     return this.y;
/*     */   }
/*     */ 
/*     */   public final double getScreenX()
/*     */   {
/* 167 */     return this.screenX;
/*     */   }
/*     */ 
/*     */   public final double getScreenY()
/*     */   {
/* 182 */     return this.screenY;
/*     */   }
/*     */ 
/*     */   public final double getSceneX()
/*     */   {
/* 205 */     return this.sceneX;
/*     */   }
/*     */ 
/*     */   public final double getSceneY()
/*     */   {
/* 228 */     return this.sceneY;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 236 */     StringBuilder localStringBuilder = new StringBuilder("ContextMenuEvent [");
/*     */ 
/* 238 */     localStringBuilder.append("source = ").append(getSource());
/* 239 */     localStringBuilder.append(", target = ").append(getTarget());
/* 240 */     localStringBuilder.append(", eventType = ").append(getEventType());
/* 241 */     localStringBuilder.append(", consumed = ").append(isConsumed());
/*     */ 
/* 243 */     localStringBuilder.append(", x = ").append(getX()).append(", y = ").append(getY());
/*     */ 
/* 245 */     return "]";
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.input.ContextMenuEvent
 * JD-Core Version:    0.6.2
 */