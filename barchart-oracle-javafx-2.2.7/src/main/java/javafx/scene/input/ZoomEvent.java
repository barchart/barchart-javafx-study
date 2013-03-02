/*     */ package javafx.scene.input;
/*     */ 
/*     */ import javafx.event.EventTarget;
/*     */ import javafx.event.EventType;
/*     */ 
/*     */ public class ZoomEvent extends GestureEvent
/*     */ {
/*  64 */   public static final EventType<ZoomEvent> ANY = new EventType(GestureEvent.ANY, "ANY_ZOOM");
/*     */ 
/*  71 */   public static final EventType<ZoomEvent> ZOOM = new EventType(ANY, "ZOOM");
/*     */ 
/*  77 */   public static final EventType<ZoomEvent> ZOOM_STARTED = new EventType(ANY, "ZOOM_STARTED");
/*     */ 
/*  83 */   public static final EventType<ZoomEvent> ZOOM_FINISHED = new EventType(ANY, "ZOOM_FINISHED");
/*     */   private double zoomFactor;
/*     */   private double totalZoomFactor;
/*     */ 
/*     */   private ZoomEvent(EventType<? extends ZoomEvent> paramEventType)
/*     */   {
/*  87 */     super(paramEventType);
/*     */   }
/*     */ 
/*     */   private ZoomEvent(Object paramObject, EventTarget paramEventTarget, EventType<? extends ZoomEvent> paramEventType)
/*     */   {
/*  92 */     super(paramObject, paramEventTarget, paramEventType);
/*     */   }
/*     */ 
/*     */   private ZoomEvent(EventType<? extends ZoomEvent> paramEventType, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, boolean paramBoolean6)
/*     */   {
/* 107 */     super(paramEventType, paramDouble3, paramDouble4, paramDouble5, paramDouble6, paramBoolean1, paramBoolean2, paramBoolean3, paramBoolean4, paramBoolean5, paramBoolean6);
/*     */ 
/* 109 */     this.zoomFactor = paramDouble1;
/* 110 */     this.totalZoomFactor = paramDouble2;
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public static ZoomEvent impl_zoomEvent(EventType<? extends ZoomEvent> paramEventType, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, boolean paramBoolean6)
/*     */   {
/* 128 */     return new ZoomEvent(paramEventType, paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6, paramBoolean1, paramBoolean2, paramBoolean3, paramBoolean4, paramBoolean5, paramBoolean6);
/*     */   }
/*     */ 
/*     */   public double getZoomFactor()
/*     */   {
/* 144 */     return this.zoomFactor;
/*     */   }
/*     */ 
/*     */   public double getTotalZoomFactor()
/*     */   {
/* 157 */     return this.totalZoomFactor;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 165 */     StringBuilder localStringBuilder = new StringBuilder("ZoomEvent [");
/*     */ 
/* 167 */     localStringBuilder.append("source = ").append(getSource());
/* 168 */     localStringBuilder.append(", target = ").append(getTarget());
/* 169 */     localStringBuilder.append(", eventType = ").append(getEventType());
/* 170 */     localStringBuilder.append(", consumed = ").append(isConsumed());
/*     */ 
/* 172 */     localStringBuilder.append(", zoomFactor = ").append(getZoomFactor());
/* 173 */     localStringBuilder.append(", totalZoomFactor = ").append(getTotalZoomFactor());
/* 174 */     localStringBuilder.append(", x = ").append(getX()).append(", y = ").append(getY());
/* 175 */     localStringBuilder.append(isDirect() ? ", direct" : ", indirect");
/*     */ 
/* 177 */     if (isShiftDown()) {
/* 178 */       localStringBuilder.append(", shiftDown");
/*     */     }
/* 180 */     if (isControlDown()) {
/* 181 */       localStringBuilder.append(", controlDown");
/*     */     }
/* 183 */     if (isAltDown()) {
/* 184 */       localStringBuilder.append(", altDown");
/*     */     }
/* 186 */     if (isMetaDown()) {
/* 187 */       localStringBuilder.append(", metaDown");
/*     */     }
/* 189 */     if (isShortcutDown()) {
/* 190 */       localStringBuilder.append(", shortcutDown");
/*     */     }
/*     */ 
/* 193 */     return localStringBuilder.append("]").toString();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.scene.input.ZoomEvent
 * JD-Core Version:    0.6.2
 */