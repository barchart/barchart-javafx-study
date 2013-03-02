/*     */ package javafx.concurrent;
/*     */ 
/*     */ import com.sun.javafx.logging.PlatformLogger;
/*     */ import com.sun.javafx.tk.Toolkit;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.concurrent.BlockingQueue;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.LinkedBlockingQueue;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.ThreadPoolExecutor;
/*     */ import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import javafx.application.Platform;
/*     */ import javafx.beans.property.BooleanProperty;
/*     */ import javafx.beans.property.DoubleProperty;
/*     */ import javafx.beans.property.ObjectProperty;
/*     */ import javafx.beans.property.ReadOnlyBooleanProperty;
/*     */ import javafx.beans.property.ReadOnlyDoubleProperty;
/*     */ import javafx.beans.property.ReadOnlyObjectProperty;
/*     */ import javafx.beans.property.ReadOnlyStringProperty;
/*     */ import javafx.beans.property.SimpleBooleanProperty;
/*     */ import javafx.beans.property.SimpleDoubleProperty;
/*     */ import javafx.beans.property.SimpleObjectProperty;
/*     */ import javafx.beans.property.SimpleStringProperty;
/*     */ import javafx.beans.property.StringProperty;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ import javafx.event.Event;
/*     */ import javafx.event.EventDispatchChain;
/*     */ import javafx.event.EventHandler;
/*     */ import javafx.event.EventTarget;
/*     */ import javafx.event.EventType;
/*     */ 
/*     */ public abstract class Service<V>
/*     */   implements Worker<V>, EventTarget
/*     */ {
/* 105 */   private static final PlatformLogger LOG = Toolkit.getToolkit().getLogger(Service.class.getName());
/*     */   private static final int THREAD_POOL_SIZE = 32;
/*     */   private static final long THREAD_TIME_OUT = 1000L;
/* 119 */   private static final BlockingQueue<Runnable> IO_QUEUE = new LinkedBlockingQueue();
/*     */ 
/* 121 */   private static final ThreadGroup THREAD_GROUP = (ThreadGroup)AccessController.doPrivileged(new PrivilegedAction() {
/*     */     public ThreadGroup run() {
/* 123 */       return new ThreadGroup("javafx concurrent thread pool");
/*     */     }
/*     */   });
/*     */ 
/* 126 */   private static final Thread.UncaughtExceptionHandler UNCAUGHT_HANDLER = new Thread.UncaughtExceptionHandler()
/*     */   {
/*     */     public void uncaughtException(Thread paramAnonymousThread, Throwable paramAnonymousThrowable)
/*     */     {
/* 131 */       if (!(paramAnonymousThrowable instanceof IllegalMonitorStateException))
/* 132 */         Service.LOG.warning("Uncaught throwable in " + Service.THREAD_GROUP.getName(), paramAnonymousThrowable);
/*     */     }
/* 126 */   };
/*     */ 
/* 137 */   private static final ThreadFactory THREAD_FACTORY = new ThreadFactory()
/*     */   {
/*     */     public Thread newThread(final Runnable paramAnonymousRunnable) {
/* 140 */       return (Thread)AccessController.doPrivileged(new PrivilegedAction() {
/*     */         public Thread run() {
/* 142 */           Thread localThread = new Thread(Service.THREAD_GROUP, paramAnonymousRunnable);
/* 143 */           localThread.setUncaughtExceptionHandler(Service.UNCAUGHT_HANDLER);
/* 144 */           localThread.setPriority(1);
/* 145 */           localThread.setDaemon(true);
/* 146 */           return localThread;
/*     */         }
/*     */       });
/*     */     }
/* 137 */   };
/*     */ 
/* 152 */   private static final ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(2, 32, 1000L, TimeUnit.MILLISECONDS, IO_QUEUE, THREAD_FACTORY, new ThreadPoolExecutor.AbortPolicy());
/*     */ 
/* 161 */   private ObjectProperty<Worker.State> state = new SimpleObjectProperty(this, "state", Worker.State.READY);
/*     */ 
/* 165 */   private ObjectProperty<V> value = new SimpleObjectProperty(this, "value");
/*     */ 
/* 169 */   private ObjectProperty<Throwable> exception = new SimpleObjectProperty(this, "exception");
/*     */ 
/* 173 */   private DoubleProperty workDone = new SimpleDoubleProperty(this, "workDone", -1.0D);
/*     */ 
/* 177 */   private DoubleProperty totalWorkToBeDone = new SimpleDoubleProperty(this, "totalWork", -1.0D);
/*     */ 
/* 181 */   private DoubleProperty progress = new SimpleDoubleProperty(this, "progress", -1.0D);
/*     */ 
/* 185 */   private BooleanProperty running = new SimpleBooleanProperty(this, "running", false);
/*     */ 
/* 189 */   private StringProperty message = new SimpleStringProperty(this, "message", "");
/*     */ 
/* 193 */   private StringProperty title = new SimpleStringProperty(this, "title", "");
/*     */ 
/* 202 */   private ObjectProperty<Executor> executor = new SimpleObjectProperty(this, "executor");
/*     */   private Task<V> task;
/* 623 */   private EventHelper eventHelper = null;
/*     */ 
/*     */   public final Worker.State getState()
/*     */   {
/* 162 */     checkThread(); return (Worker.State)this.state.get(); } 
/* 163 */   public final ReadOnlyObjectProperty<Worker.State> stateProperty() { checkThread(); return this.state; }
/*     */ 
/*     */   public final V getValue() {
/* 166 */     checkThread(); return this.value.get(); } 
/* 167 */   public final ReadOnlyObjectProperty<V> valueProperty() { checkThread(); return this.value; }
/*     */ 
/*     */   public final Throwable getException() {
/* 170 */     checkThread(); return (Throwable)this.exception.get(); } 
/* 171 */   public final ReadOnlyObjectProperty<Throwable> exceptionProperty() { checkThread(); return this.exception; }
/*     */ 
/*     */   public final double getWorkDone() {
/* 174 */     checkThread(); return this.workDone.get(); } 
/* 175 */   public final ReadOnlyDoubleProperty workDoneProperty() { checkThread(); return this.workDone; }
/*     */ 
/*     */   public final double getTotalWork() {
/* 178 */     checkThread(); return this.totalWorkToBeDone.get(); } 
/* 179 */   public final ReadOnlyDoubleProperty totalWorkProperty() { checkThread(); return this.totalWorkToBeDone; }
/*     */ 
/*     */   public final double getProgress() {
/* 182 */     checkThread(); return this.progress.get(); } 
/* 183 */   public final ReadOnlyDoubleProperty progressProperty() { checkThread(); return this.progress; }
/*     */ 
/*     */   public final boolean isRunning() {
/* 186 */     checkThread(); return this.running.get(); } 
/* 187 */   public final ReadOnlyBooleanProperty runningProperty() { checkThread(); return this.running; }
/*     */ 
/*     */   public final String getMessage() {
/* 190 */     return (String)this.message.get(); } 
/* 191 */   public final ReadOnlyStringProperty messageProperty() { return this.message; }
/*     */ 
/*     */   public final String getTitle() {
/* 194 */     return (String)this.title.get(); } 
/* 195 */   public final ReadOnlyStringProperty titleProperty() { return this.title; }
/*     */ 
/*     */ 
/*     */   public final void setExecutor(Executor paramExecutor)
/*     */   {
/* 203 */     checkThread(); this.executor.set(paramExecutor); } 
/* 204 */   public final Executor getExecutor() { checkThread(); return (Executor)this.executor.get(); } 
/* 205 */   public final ObjectProperty<Executor> executorProperty() { return this.executor; }
/*     */ 
/*     */ 
/*     */   public final ObjectProperty<EventHandler<WorkerStateEvent>> onReadyProperty()
/*     */   {
/* 214 */     return getEventHelper().onReadyProperty();
/*     */   }
/*     */ 
/*     */   public final EventHandler<WorkerStateEvent> getOnReady()
/*     */   {
/* 224 */     return this.eventHelper == null ? null : this.eventHelper.getOnReady();
/*     */   }
/*     */ 
/*     */   public final void setOnReady(EventHandler<WorkerStateEvent> paramEventHandler)
/*     */   {
/* 234 */     getEventHelper().setOnReady(paramEventHandler);
/*     */   }
/*     */ 
/*     */   protected void ready()
/*     */   {
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<EventHandler<WorkerStateEvent>> onScheduledProperty()
/*     */   {
/* 252 */     return getEventHelper().onScheduledProperty();
/*     */   }
/*     */ 
/*     */   public final EventHandler<WorkerStateEvent> getOnScheduled()
/*     */   {
/* 262 */     return this.eventHelper == null ? null : this.eventHelper.getOnScheduled();
/*     */   }
/*     */ 
/*     */   public final void setOnScheduled(EventHandler<WorkerStateEvent> paramEventHandler)
/*     */   {
/* 272 */     getEventHelper().setOnScheduled(paramEventHandler);
/*     */   }
/*     */ 
/*     */   protected void scheduled()
/*     */   {
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<EventHandler<WorkerStateEvent>> onRunningProperty()
/*     */   {
/* 290 */     return getEventHelper().onRunningProperty();
/*     */   }
/*     */ 
/*     */   public final EventHandler<WorkerStateEvent> getOnRunning()
/*     */   {
/* 300 */     return this.eventHelper == null ? null : this.eventHelper.getOnRunning();
/*     */   }
/*     */ 
/*     */   public final void setOnRunning(EventHandler<WorkerStateEvent> paramEventHandler)
/*     */   {
/* 310 */     getEventHelper().setOnRunning(paramEventHandler);
/*     */   }
/*     */ 
/*     */   protected void running()
/*     */   {
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<EventHandler<WorkerStateEvent>> onSucceededProperty()
/*     */   {
/* 328 */     return getEventHelper().onSucceededProperty();
/*     */   }
/*     */ 
/*     */   public final EventHandler<WorkerStateEvent> getOnSucceeded()
/*     */   {
/* 338 */     return this.eventHelper == null ? null : this.eventHelper.getOnSucceeded();
/*     */   }
/*     */ 
/*     */   public final void setOnSucceeded(EventHandler<WorkerStateEvent> paramEventHandler)
/*     */   {
/* 348 */     getEventHelper().setOnSucceeded(paramEventHandler);
/*     */   }
/*     */ 
/*     */   protected void succeeded()
/*     */   {
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<EventHandler<WorkerStateEvent>> onCancelledProperty()
/*     */   {
/* 366 */     return getEventHelper().onCancelledProperty();
/*     */   }
/*     */ 
/*     */   public final EventHandler<WorkerStateEvent> getOnCancelled()
/*     */   {
/* 376 */     return this.eventHelper == null ? null : this.eventHelper.getOnCancelled();
/*     */   }
/*     */ 
/*     */   public final void setOnCancelled(EventHandler<WorkerStateEvent> paramEventHandler)
/*     */   {
/* 386 */     getEventHelper().setOnCancelled(paramEventHandler);
/*     */   }
/*     */ 
/*     */   protected void cancelled()
/*     */   {
/*     */   }
/*     */ 
/*     */   public final ObjectProperty<EventHandler<WorkerStateEvent>> onFailedProperty()
/*     */   {
/* 404 */     return getEventHelper().onFailedProperty();
/*     */   }
/*     */ 
/*     */   public final EventHandler<WorkerStateEvent> getOnFailed()
/*     */   {
/* 414 */     return this.eventHelper == null ? null : this.eventHelper.getOnFailed();
/*     */   }
/*     */ 
/*     */   public final void setOnFailed(EventHandler<WorkerStateEvent> paramEventHandler)
/*     */   {
/* 424 */     getEventHelper().setOnFailed(paramEventHandler);
/*     */   }
/*     */ 
/*     */   protected void failed()
/*     */   {
/*     */   }
/*     */ 
/*     */   protected Service()
/*     */   {
/* 448 */     this.state.addListener(new ChangeListener()
/*     */     {
/*     */       public void changed(ObservableValue<? extends Worker.State> paramAnonymousObservableValue, Worker.State paramAnonymousState1, Worker.State paramAnonymousState2)
/*     */       {
/* 452 */         switch (Service.5.$SwitchMap$javafx$concurrent$Worker$State[((Worker.State)Service.this.state.get()).ordinal()]) {
/*     */         case 1:
/* 454 */           Service.this.fireEvent(new WorkerStateEvent(Service.this, WorkerStateEvent.WORKER_STATE_CANCELLED));
/* 455 */           Service.this.cancelled();
/* 456 */           break;
/*     */         case 2:
/* 458 */           Service.this.fireEvent(new WorkerStateEvent(Service.this, WorkerStateEvent.WORKER_STATE_FAILED));
/* 459 */           Service.this.failed();
/* 460 */           break;
/*     */         case 3:
/* 462 */           Service.this.fireEvent(new WorkerStateEvent(Service.this, WorkerStateEvent.WORKER_STATE_READY));
/* 463 */           Service.this.ready();
/* 464 */           break;
/*     */         case 4:
/* 466 */           Service.this.fireEvent(new WorkerStateEvent(Service.this, WorkerStateEvent.WORKER_STATE_RUNNING));
/* 467 */           Service.this.running();
/* 468 */           break;
/*     */         case 5:
/* 470 */           Service.this.fireEvent(new WorkerStateEvent(Service.this, WorkerStateEvent.WORKER_STATE_SCHEDULED));
/* 471 */           Service.this.scheduled();
/* 472 */           break;
/*     */         case 6:
/* 474 */           Service.this.fireEvent(new WorkerStateEvent(Service.this, WorkerStateEvent.WORKER_STATE_SUCCEEDED));
/* 475 */           Service.this.succeeded();
/* 476 */           break;
/*     */         default:
/* 477 */           throw new AssertionError("Should be unreachable");
/*     */         }
/*     */       }
/*     */     });
/*     */   }
/*     */ 
/*     */   public final boolean cancel() {
/* 484 */     checkThread();
/* 485 */     if (this.task == null) {
/* 486 */       if ((this.state.get() == Worker.State.CANCELLED) || (this.state.get() == Worker.State.SUCCEEDED)) {
/* 487 */         return false;
/*     */       }
/* 489 */       this.state.set(Worker.State.CANCELLED);
/* 490 */       return true;
/*     */     }
/* 492 */     return this.task.cancel(true);
/*     */   }
/*     */ 
/*     */   public void restart()
/*     */   {
/* 502 */     checkThread();
/*     */ 
/* 505 */     if (this.task != null) {
/* 506 */       this.task.cancel();
/* 507 */       this.task = null;
/*     */ 
/* 518 */       this.state.unbind();
/* 519 */       this.state.setValue(Worker.State.CANCELLED);
/*     */     }
/*     */ 
/* 523 */     reset();
/*     */ 
/* 526 */     start();
/*     */   }
/*     */ 
/*     */   public void reset()
/*     */   {
/* 535 */     checkThread();
/* 536 */     Worker.State localState = getState();
/* 537 */     if ((localState == Worker.State.SCHEDULED) || (localState == Worker.State.RUNNING)) {
/* 538 */       throw new IllegalStateException();
/*     */     }
/*     */ 
/* 541 */     this.task = null;
/* 542 */     this.state.unbind();
/* 543 */     this.state.set(Worker.State.READY);
/* 544 */     this.value.unbind();
/* 545 */     this.value.set(null);
/* 546 */     this.exception.unbind();
/* 547 */     this.exception.set(null);
/* 548 */     this.workDone.unbind();
/* 549 */     this.workDone.set(-1.0D);
/* 550 */     this.totalWorkToBeDone.unbind();
/* 551 */     this.totalWorkToBeDone.set(-1.0D);
/* 552 */     this.progress.unbind();
/* 553 */     this.progress.set(-1.0D);
/* 554 */     this.running.unbind();
/* 555 */     this.running.set(false);
/* 556 */     this.message.unbind();
/* 557 */     this.message.set("");
/* 558 */     this.title.unbind();
/* 559 */     this.title.set("");
/*     */   }
/*     */ 
/*     */   public void start()
/*     */   {
/* 567 */     checkThread();
/*     */ 
/* 569 */     if (getState() != Worker.State.READY) {
/* 570 */       throw new IllegalStateException("Can only start a Service in the READY state. Was in state " + getState());
/*     */     }
/*     */ 
/* 575 */     this.task = createTask();
/*     */ 
/* 578 */     this.state.bind(this.task.stateProperty());
/* 579 */     this.value.bind(this.task.valueProperty());
/* 580 */     this.exception.bind(this.task.exceptionProperty());
/* 581 */     this.workDone.bind(this.task.workDoneProperty());
/* 582 */     this.totalWorkToBeDone.bind(this.task.totalWorkProperty());
/* 583 */     this.progress.bind(this.task.progressProperty());
/* 584 */     this.running.bind(this.task.runningProperty());
/* 585 */     this.message.bind(this.task.messageProperty());
/* 586 */     this.title.bind(this.task.titleProperty());
/*     */ 
/* 589 */     this.task.setState(Worker.State.SCHEDULED);
/*     */ 
/* 592 */     executeTask(this.task);
/*     */   }
/*     */ 
/*     */   protected void executeTask(Task<V> paramTask)
/*     */   {
/* 609 */     Executor localExecutor = getExecutor();
/* 610 */     if (localExecutor != null)
/* 611 */       localExecutor.execute(paramTask);
/*     */     else
/* 613 */       EXECUTOR.execute(paramTask);
/*     */   }
/*     */ 
/*     */   private EventHelper getEventHelper()
/*     */   {
/* 625 */     if (this.eventHelper == null) {
/* 626 */       this.eventHelper = new EventHelper(this);
/*     */     }
/* 628 */     return this.eventHelper;
/*     */   }
/*     */ 
/*     */   public final <T extends Event> void addEventHandler(EventType<T> paramEventType, EventHandler<? super T> paramEventHandler)
/*     */   {
/* 646 */     getEventHelper().addEventHandler(paramEventType, paramEventHandler);
/*     */   }
/*     */ 
/*     */   public final <T extends Event> void removeEventHandler(EventType<T> paramEventType, EventHandler<? super T> paramEventHandler)
/*     */   {
/* 663 */     getEventHelper().removeEventHandler(paramEventType, paramEventHandler);
/*     */   }
/*     */ 
/*     */   public final <T extends Event> void addEventFilter(EventType<T> paramEventType, EventHandler<? super T> paramEventHandler)
/*     */   {
/* 678 */     getEventHelper().addEventFilter(paramEventType, paramEventHandler);
/*     */   }
/*     */ 
/*     */   public final <T extends Event> void removeEventFilter(EventType<T> paramEventType, EventHandler<? super T> paramEventHandler)
/*     */   {
/* 695 */     getEventHelper().removeEventFilter(paramEventType, paramEventHandler);
/*     */   }
/*     */ 
/*     */   protected final <T extends Event> void setEventHandler(EventType<T> paramEventType, EventHandler<? super T> paramEventHandler)
/*     */   {
/* 712 */     getEventHelper().setEventHandler(paramEventType, paramEventHandler);
/*     */   }
/*     */ 
/*     */   protected final void fireEvent(Event paramEvent)
/*     */   {
/* 727 */     checkThread();
/* 728 */     getEventHelper().fireEvent(paramEvent);
/*     */   }
/*     */ 
/*     */   public EventDispatchChain buildEventDispatchChain(EventDispatchChain paramEventDispatchChain)
/*     */   {
/* 733 */     return getEventHelper().buildEventDispatchChain(paramEventDispatchChain);
/*     */   }
/*     */ 
/*     */   protected abstract Task<V> createTask();
/*     */ 
/*     */   void checkThread()
/*     */   {
/* 779 */     if (!Platform.isFxApplicationThread())
/* 780 */       throw new IllegalStateException("Service must only be used from the FX Application Thread");
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/* 158 */     EXECUTOR.allowCoreThreadTimeOut(true);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.concurrent.Service
 * JD-Core Version:    0.6.2
 */