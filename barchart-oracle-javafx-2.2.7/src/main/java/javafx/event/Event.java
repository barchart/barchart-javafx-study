/*     */ package javafx.event;
/*     */ 
/*     */ import com.sun.javafx.event.EventUtil;
/*     */ import java.util.EventObject;
/*     */ 
/*     */ public class Event extends EventObject
/*     */   implements Cloneable
/*     */ {
/*  24 */   public static final EventTarget NULL_SOURCE_TARGET = new EventTarget()
/*     */   {
/*     */     public EventDispatchChain buildEventDispatchChain(EventDispatchChain paramAnonymousEventDispatchChain)
/*     */     {
/*  28 */       return paramAnonymousEventDispatchChain;
/*     */     }
/*  24 */   };
/*     */ 
/*  35 */   public static final EventType<Event> ANY = EventType.ROOT;
/*     */   protected EventType<? extends Event> eventType;
/*     */   protected EventTarget target;
/*     */   protected boolean consumed;
/*     */ 
/*     */   public Event(EventType<? extends Event> paramEventType)
/*     */   {
/*  60 */     this(null, null, paramEventType);
/*     */   }
/*     */ 
/*     */   public Event(Object paramObject, EventTarget paramEventTarget, EventType<? extends Event> paramEventType)
/*     */   {
/*  75 */     super(paramObject != null ? paramObject : NULL_SOURCE_TARGET);
/*  76 */     this.target = (paramEventTarget != null ? paramEventTarget : NULL_SOURCE_TARGET);
/*  77 */     this.eventType = paramEventType;
/*     */   }
/*     */ 
/*     */   public EventTarget getTarget()
/*     */   {
/*  87 */     return this.target;
/*     */   }
/*     */ 
/*     */   public EventType<? extends Event> getEventType()
/*     */   {
/*  98 */     return this.eventType;
/*     */   }
/*     */ 
/*     */   public Event copyFor(Object paramObject, EventTarget paramEventTarget)
/*     */   {
/* 111 */     Event localEvent = (Event)clone();
/*     */ 
/* 113 */     localEvent.source = (paramObject != null ? paramObject : NULL_SOURCE_TARGET);
/* 114 */     localEvent.target = (paramEventTarget != null ? paramEventTarget : NULL_SOURCE_TARGET);
/* 115 */     localEvent.consumed = false;
/*     */ 
/* 117 */     return localEvent;
/*     */   }
/*     */ 
/*     */   public boolean isConsumed()
/*     */   {
/* 128 */     return this.consumed;
/*     */   }
/*     */ 
/*     */   public void consume()
/*     */   {
/* 135 */     this.consumed = true;
/*     */   }
/*     */ 
/*     */   public Object clone()
/*     */   {
/*     */     try
/*     */     {
/* 146 */       return super.clone();
/*     */     } catch (CloneNotSupportedException localCloneNotSupportedException) {
/*     */     }
/* 149 */     throw new RuntimeException("Can't clone Event");
/*     */   }
/*     */ 
/*     */   public static void fireEvent(EventTarget paramEventTarget, Event paramEvent)
/*     */   {
/* 163 */     if (paramEventTarget == null) {
/* 164 */       throw new NullPointerException("Event target must not be null!");
/*     */     }
/*     */ 
/* 167 */     if (paramEvent == null) {
/* 168 */       throw new NullPointerException("Event must not be null!");
/*     */     }
/*     */ 
/* 171 */     EventUtil.fireEvent(paramEventTarget, paramEvent);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.event.Event
 * JD-Core Version:    0.6.2
 */