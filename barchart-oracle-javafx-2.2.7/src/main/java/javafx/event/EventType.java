/*     */ package javafx.event;
/*     */ 
/*     */ public class EventType<T extends Event>
/*     */ {
/*  24 */   public static final EventType<Event> ROOT = new EventType("EVENT", null);
/*     */   private final EventType<? super T> superType;
/*     */   private final String name;
/*     */ 
/*     */   public EventType()
/*     */   {
/*  36 */     this(ROOT, null);
/*     */   }
/*     */ 
/*     */   public EventType(String paramString)
/*     */   {
/*  46 */     this(ROOT, paramString);
/*     */   }
/*     */ 
/*     */   public EventType(EventType<? super T> paramEventType)
/*     */   {
/*  56 */     this(paramEventType, null);
/*     */   }
/*     */ 
/*     */   public EventType(EventType<? super T> paramEventType, String paramString)
/*     */   {
/*  68 */     if (paramEventType == null) {
/*  69 */       throw new NullPointerException("Event super type must not be null!");
/*     */     }
/*     */ 
/*  73 */     this.superType = paramEventType;
/*  74 */     this.name = paramString;
/*     */   }
/*     */ 
/*     */   private EventType(String paramString, EventType<? super T> paramEventType)
/*     */   {
/*  79 */     this.superType = paramEventType;
/*  80 */     this.name = paramString;
/*     */   }
/*     */ 
/*     */   public final EventType<? super T> getSuperType()
/*     */   {
/*  90 */     return this.superType;
/*     */   }
/*     */ 
/*     */   public final String getName()
/*     */   {
/*  99 */     return this.name;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 108 */     return this.name != null ? this.name : super.toString();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.event.EventType
 * JD-Core Version:    0.6.2
 */