/*     */ package javafx.scene.input;
/*     */ 
/*     */ import javafx.event.EventTarget;
/*     */ import javafx.event.EventType;
/*     */ 
/*     */ public class RotateEvent extends GestureEvent
/*     */ {
/*  64 */   public static final EventType<RotateEvent> ANY = new EventType(GestureEvent.ANY, "ANY_ROTATE");
/*     */ 
/*  71 */   public static final EventType<RotateEvent> ROTATE = new EventType(ANY, "ROTATE");
/*     */ 
/*  77 */   public static final EventType<RotateEvent> ROTATION_STARTED = new EventType(ANY, "ROTATION_STARTED");
/*     */ 
/*  83 */   public static final EventType<RotateEvent> ROTATION_FINISHED = new EventType(ANY, "ROTATION_FINISHED");
/*     */   private double angle;
/*     */   private double totalAngle;
/*     */ 
/*     */   private RotateEvent(EventType<? extends RotateEvent> paramEventType)
/*     */   {
/*  87 */     super(paramEventType);
/*     */   }
/*     */ 
/*     */   private RotateEvent(Object paramObject, EventTarget paramEventTarget, EventType<? extends RotateEvent> paramEventType)
/*     */   {
/*  92 */     super(paramObject, paramEventTarget, paramEventType);
/*     */   }
/*     */ 
/*     */   private RotateEvent(EventType<? extends RotateEvent> paramEventType, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, boolean paramBoolean6)
/*     */   {
/* 106 */     super(paramEventType, paramDouble3, paramDouble4, paramDouble5, paramDouble6, paramBoolean1, paramBoolean2, paramBoolean3, paramBoolean4, paramBoolean5, paramBoolean6);
/*     */ 
/* 108 */     this.angle = paramDouble1;
/* 109 */     this.totalAngle = paramDouble2;
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public static RotateEvent impl_rotateEvent(EventType<? extends RotateEvent> paramEventType, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, boolean paramBoolean6)
/*     */   {
/* 126 */     return new RotateEvent(paramEventType, paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6, paramBoolean1, paramBoolean2, paramBoolean3, paramBoolean4, paramBoolean5, paramBoolean6);
/*     */   }
/*     */ 
/*     */   public double getAngle()
/*     */   {
/* 140 */     return this.angle;
/*     */   }
/*     */ 
/*     */   public double getTotalAngle()
/*     */   {
/* 152 */     return this.totalAngle;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 160 */     StringBuilder localStringBuilder = new StringBuilder("RotateEvent [");
/*     */ 
/* 162 */     localStringBuilder.append("source = ").append(getSource());
/* 163 */     localStringBuilder.append(", target = ").append(getTarget());
/* 164 */     localStringBuilder.append(", eventType = ").append(getEventType());
/* 165 */     localStringBuilder.append(", consumed = ").append(isConsumed());
/*     */ 
/* 167 */     localStringBuilder.append(", angle = ").append(getAngle());
/* 168 */     localStringBuilder.append(", totalAngle = ").append(getTotalAngle());
/* 169 */     localStringBuilder.append(", x = ").append(getX()).append(", y = ").append(getY());
/* 170 */     localStringBuilder.append(isDirect() ? ", direct" : ", indirect");
/*     */ 
/* 172 */     if (isShiftDown()) {
/* 173 */       localStringBuilder.append(", shiftDown");
/*     */     }
/* 175 */     if (isControlDown()) {
/* 176 */       localStringBuilder.append(", controlDown");
/*     */     }
/* 178 */     if (isAltDown()) {
/* 179 */       localStringBuilder.append(", altDown");
/*     */     }
/* 181 */     if (isMetaDown()) {
/* 182 */       localStringBuilder.append(", metaDown");
/*     */     }
/* 184 */     if (isShortcutDown()) {
/* 185 */       localStringBuilder.append(", shortcutDown");
/*     */     }
/*     */ 
/* 188 */     return localStringBuilder.append("]").toString();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.input.RotateEvent
 * JD-Core Version:    0.6.2
 */