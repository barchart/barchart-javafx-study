/*    */ package javafx.concurrent;
/*    */ 
/*    */ import javafx.event.Event;
/*    */ import javafx.event.EventTarget;
/*    */ import javafx.event.EventType;
/*    */ 
/*    */ public class WorkerStateEvent extends Event
/*    */ {
/* 19 */   public static final EventType<WorkerStateEvent> ANY = new EventType(Event.ANY, "WORKER_STATE");
/*    */ 
/* 26 */   public static final EventType<WorkerStateEvent> WORKER_STATE_READY = new EventType(ANY, "WORKER_STATE_READY");
/*    */ 
/* 33 */   public static final EventType<WorkerStateEvent> WORKER_STATE_SCHEDULED = new EventType(ANY, "WORKER_STATE_SCHEDULED");
/*    */ 
/* 40 */   public static final EventType<WorkerStateEvent> WORKER_STATE_RUNNING = new EventType(ANY, "WORKER_STATE_RUNNING");
/*    */ 
/* 47 */   public static final EventType<WorkerStateEvent> WORKER_STATE_SUCCEEDED = new EventType(ANY, "WORKER_STATE_SUCCEEDED");
/*    */ 
/* 54 */   public static final EventType<WorkerStateEvent> WORKER_STATE_CANCELLED = new EventType(ANY, "WORKER_STATE_CANCELLED");
/*    */ 
/* 61 */   public static final EventType<WorkerStateEvent> WORKER_STATE_FAILED = new EventType(ANY, "WORKER_STATE_FAILED");
/*    */ 
/*    */   public WorkerStateEvent(Worker paramWorker, EventType<? extends WorkerStateEvent> paramEventType)
/*    */   {
/* 73 */     super(paramWorker, (paramWorker instanceof EventTarget) ? (EventTarget)paramWorker : null, paramEventType);
/*    */   }
/*    */ 
/*    */   public Worker getSource() {
/* 77 */     return (Worker)super.getSource();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.concurrent.WorkerStateEvent
 * JD-Core Version:    0.6.2
 */