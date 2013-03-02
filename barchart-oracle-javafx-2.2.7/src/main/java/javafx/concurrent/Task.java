/*      */ package javafx.concurrent;
/*      */ 
/*      */ import java.util.concurrent.Callable;
/*      */ import java.util.concurrent.FutureTask;
/*      */ import java.util.concurrent.atomic.AtomicReference;
/*      */ import javafx.application.Platform;
/*      */ import javafx.beans.property.BooleanProperty;
/*      */ import javafx.beans.property.DoubleProperty;
/*      */ import javafx.beans.property.ObjectProperty;
/*      */ import javafx.beans.property.ReadOnlyBooleanProperty;
/*      */ import javafx.beans.property.ReadOnlyDoubleProperty;
/*      */ import javafx.beans.property.ReadOnlyObjectProperty;
/*      */ import javafx.beans.property.ReadOnlyStringProperty;
/*      */ import javafx.beans.property.SimpleBooleanProperty;
/*      */ import javafx.beans.property.SimpleDoubleProperty;
/*      */ import javafx.beans.property.SimpleObjectProperty;
/*      */ import javafx.beans.property.SimpleStringProperty;
/*      */ import javafx.beans.property.StringProperty;
/*      */ import javafx.event.Event;
/*      */ import javafx.event.EventDispatchChain;
/*      */ import javafx.event.EventHandler;
/*      */ import javafx.event.EventTarget;
/*      */ import javafx.event.EventType;
/*      */ 
/*      */ public abstract class Task<V> extends FutureTask<V>
/*      */   implements Worker<V>, EventTarget
/*      */ {
/*  553 */   private AtomicReference<ProgressUpdate> progressUpdate = new AtomicReference();
/*      */ 
/*  560 */   private AtomicReference<String> messageUpdate = new AtomicReference();
/*      */ 
/*  567 */   private AtomicReference<String> titleUpdate = new AtomicReference();
/*      */ 
/*  606 */   private ObjectProperty<Worker.State> state = new SimpleObjectProperty(this, "state", Worker.State.READY);
/*      */ 
/*  843 */   private ObjectProperty<V> value = new SimpleObjectProperty(this, "value");
/*      */ 
/*  848 */   private ObjectProperty<Throwable> exception = new SimpleObjectProperty(this, "exception");
/*      */ 
/*  853 */   private DoubleProperty workDone = new SimpleDoubleProperty(this, "workDone", -1.0D);
/*      */ 
/*  858 */   private DoubleProperty totalWork = new SimpleDoubleProperty(this, "totalWork", -1.0D);
/*      */ 
/*  863 */   private DoubleProperty progress = new SimpleDoubleProperty(this, "progress", -1.0D);
/*      */ 
/*  868 */   private BooleanProperty running = new SimpleBooleanProperty(this, "running", false);
/*      */ 
/*  873 */   private StringProperty message = new SimpleStringProperty(this, "message", "");
/*      */ 
/*  877 */   private StringProperty title = new SimpleStringProperty(this, "title", "");
/*      */ 
/* 1087 */   private EventHelper eventHelper = null;
/*      */ 
/*      */   public Task()
/*      */   {
/*  573 */     this(new TaskCallable(null));
/*      */   }
/*      */ 
/*      */   private Task(TaskCallable<V> paramTaskCallable)
/*      */   {
/*  588 */     super(paramTaskCallable);
/*  589 */     paramTaskCallable.task = this;
/*      */   }
/*      */ 
/*      */   protected abstract V call()
/*      */     throws Exception;
/*      */ 
/*      */   final void setState(Worker.State paramState)
/*      */   {
/*  608 */     checkThread();
/*  609 */     Worker.State localState = getState();
/*  610 */     if (localState != Worker.State.CANCELLED) {
/*  611 */       this.state.set(paramState);
/*      */ 
/*  613 */       setRunning((paramState == Worker.State.SCHEDULED) || (paramState == Worker.State.RUNNING));
/*      */ 
/*  616 */       switch (5.$SwitchMap$javafx$concurrent$Worker$State[((Worker.State)this.state.get()).ordinal()]) {
/*      */       case 1:
/*  618 */         fireEvent(new WorkerStateEvent(this, WorkerStateEvent.WORKER_STATE_CANCELLED));
/*  619 */         cancelled();
/*  620 */         break;
/*      */       case 2:
/*  622 */         fireEvent(new WorkerStateEvent(this, WorkerStateEvent.WORKER_STATE_FAILED));
/*  623 */         failed();
/*  624 */         break;
/*      */       case 3:
/*  628 */         break;
/*      */       case 4:
/*  630 */         fireEvent(new WorkerStateEvent(this, WorkerStateEvent.WORKER_STATE_RUNNING));
/*  631 */         running();
/*  632 */         break;
/*      */       case 5:
/*  634 */         fireEvent(new WorkerStateEvent(this, WorkerStateEvent.WORKER_STATE_SCHEDULED));
/*  635 */         scheduled();
/*  636 */         break;
/*      */       case 6:
/*  638 */         fireEvent(new WorkerStateEvent(this, WorkerStateEvent.WORKER_STATE_SUCCEEDED));
/*  639 */         succeeded();
/*  640 */         break;
/*      */       default:
/*  641 */         throw new AssertionError("Should be unreachable"); } 
/*      */     }
/*      */   }
/*      */ 
/*  645 */   public final Worker.State getState() { checkThread(); return (Worker.State)this.state.get(); } 
/*  646 */   public final ReadOnlyObjectProperty<Worker.State> stateProperty() { checkThread(); return this.state;
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<EventHandler<WorkerStateEvent>> onScheduledProperty()
/*      */   {
/*  655 */     return getEventHelper().onScheduledProperty();
/*      */   }
/*      */ 
/*      */   public final EventHandler<WorkerStateEvent> getOnScheduled()
/*      */   {
/*  665 */     return this.eventHelper == null ? null : this.eventHelper.getOnScheduled();
/*      */   }
/*      */ 
/*      */   public final void setOnScheduled(EventHandler<WorkerStateEvent> paramEventHandler)
/*      */   {
/*  675 */     getEventHelper().setOnScheduled(paramEventHandler);
/*      */   }
/*      */ 
/*      */   protected void scheduled()
/*      */   {
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<EventHandler<WorkerStateEvent>> onRunningProperty()
/*      */   {
/*  694 */     return getEventHelper().onRunningProperty();
/*      */   }
/*      */ 
/*      */   public final EventHandler<WorkerStateEvent> getOnRunning()
/*      */   {
/*  704 */     return this.eventHelper == null ? null : this.eventHelper.getOnRunning();
/*      */   }
/*      */ 
/*      */   public final void setOnRunning(EventHandler<WorkerStateEvent> paramEventHandler)
/*      */   {
/*  714 */     getEventHelper().setOnRunning(paramEventHandler);
/*      */   }
/*      */ 
/*      */   protected void running()
/*      */   {
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<EventHandler<WorkerStateEvent>> onSucceededProperty()
/*      */   {
/*  733 */     return getEventHelper().onSucceededProperty();
/*      */   }
/*      */ 
/*      */   public final EventHandler<WorkerStateEvent> getOnSucceeded()
/*      */   {
/*  743 */     return this.eventHelper == null ? null : this.eventHelper.getOnSucceeded();
/*      */   }
/*      */ 
/*      */   public final void setOnSucceeded(EventHandler<WorkerStateEvent> paramEventHandler)
/*      */   {
/*  753 */     getEventHelper().setOnSucceeded(paramEventHandler);
/*      */   }
/*      */ 
/*      */   protected void succeeded()
/*      */   {
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<EventHandler<WorkerStateEvent>> onCancelledProperty()
/*      */   {
/*  772 */     return getEventHelper().onCancelledProperty();
/*      */   }
/*      */ 
/*      */   public final EventHandler<WorkerStateEvent> getOnCancelled()
/*      */   {
/*  782 */     return this.eventHelper == null ? null : this.eventHelper.getOnCancelled();
/*      */   }
/*      */ 
/*      */   public final void setOnCancelled(EventHandler<WorkerStateEvent> paramEventHandler)
/*      */   {
/*  792 */     getEventHelper().setOnCancelled(paramEventHandler);
/*      */   }
/*      */ 
/*      */   protected void cancelled()
/*      */   {
/*      */   }
/*      */ 
/*      */   public final ObjectProperty<EventHandler<WorkerStateEvent>> onFailedProperty()
/*      */   {
/*  811 */     return getEventHelper().onFailedProperty();
/*      */   }
/*      */ 
/*      */   public final EventHandler<WorkerStateEvent> getOnFailed()
/*      */   {
/*  821 */     return this.eventHelper == null ? null : this.eventHelper.getOnFailed();
/*      */   }
/*      */ 
/*      */   public final void setOnFailed(EventHandler<WorkerStateEvent> paramEventHandler)
/*      */   {
/*  831 */     getEventHelper().setOnFailed(paramEventHandler);
/*      */   }
/*      */ 
/*      */   protected void failed()
/*      */   {
/*      */   }
/*      */ 
/*      */   private void setValue(V paramV)
/*      */   {
/*  844 */     checkThread(); this.value.set(paramV); } 
/*  845 */   public final V getValue() { checkThread(); return this.value.get(); } 
/*  846 */   public final ReadOnlyObjectProperty<V> valueProperty() { checkThread(); return this.value; }
/*      */ 
/*      */   private void _setException(Throwable paramThrowable) {
/*  849 */     checkThread(); this.exception.set(paramThrowable); } 
/*  850 */   public final Throwable getException() { checkThread(); return (Throwable)this.exception.get(); } 
/*  851 */   public final ReadOnlyObjectProperty<Throwable> exceptionProperty() { checkThread(); return this.exception; }
/*      */ 
/*      */   private void setWorkDone(double paramDouble) {
/*  854 */     checkThread(); this.workDone.set(paramDouble); } 
/*  855 */   public final double getWorkDone() { checkThread(); return this.workDone.get(); } 
/*  856 */   public final ReadOnlyDoubleProperty workDoneProperty() { checkThread(); return this.workDone; }
/*      */ 
/*      */   private void setTotalWork(double paramDouble) {
/*  859 */     checkThread(); this.totalWork.set(paramDouble); } 
/*  860 */   public final double getTotalWork() { checkThread(); return this.totalWork.get(); } 
/*  861 */   public final ReadOnlyDoubleProperty totalWorkProperty() { checkThread(); return this.totalWork; }
/*      */ 
/*      */   private void setProgress(double paramDouble) {
/*  864 */     checkThread(); this.progress.set(paramDouble); } 
/*  865 */   public final double getProgress() { checkThread(); return this.progress.get(); } 
/*  866 */   public final ReadOnlyDoubleProperty progressProperty() { checkThread(); return this.progress; }
/*      */ 
/*      */   private void setRunning(boolean paramBoolean) {
/*  869 */     checkThread(); this.running.set(paramBoolean); } 
/*  870 */   public final boolean isRunning() { checkThread(); return this.running.get(); } 
/*  871 */   public final ReadOnlyBooleanProperty runningProperty() { checkThread(); return this.running; }
/*      */ 
/*      */   public final String getMessage() {
/*  874 */     return (String)this.message.get(); } 
/*  875 */   public final ReadOnlyStringProperty messageProperty() { return this.message; }
/*      */ 
/*      */   public final String getTitle() {
/*  878 */     return (String)this.title.get(); } 
/*  879 */   public final ReadOnlyStringProperty titleProperty() { return this.title; }
/*      */ 
/*      */   public final boolean cancel() {
/*  882 */     return cancel(true);
/*      */   }
/*      */ 
/*      */   public boolean cancel(boolean paramBoolean)
/*      */   {
/*  887 */     boolean bool = super.cancel(paramBoolean);
/*      */ 
/*  890 */     if (bool)
/*      */     {
/*  898 */       if (isFxApplicationThread())
/*  899 */         setState(Worker.State.CANCELLED);
/*      */       else {
/*  901 */         runLater(new Runnable() {
/*      */           public void run() {
/*  903 */             Task.this.setState(Worker.State.CANCELLED);
/*      */           }
/*      */         });
/*      */       }
/*      */     }
/*      */ 
/*  909 */     return bool;
/*      */   }
/*      */ 
/*      */   protected void updateProgress(long paramLong1, long paramLong2)
/*      */   {
/*  934 */     updateProgress(paramLong1, paramLong2);
/*      */   }
/*      */ 
/*      */   protected void updateProgress(double paramDouble1, double paramDouble2)
/*      */   {
/*  960 */     if (paramDouble1 > paramDouble2) {
/*  961 */       throw new IllegalArgumentException("The workDone must be <= the max");
/*      */     }
/*      */ 
/*  965 */     if ((paramDouble1 < -1.0D) || (paramDouble2 < -1.0D)) {
/*  966 */       throw new IllegalArgumentException("The workDone and max cannot be less than -1");
/*      */     }
/*      */ 
/*  969 */     if ((Double.isInfinite(paramDouble2)) || (Double.isNaN(paramDouble2))) {
/*  970 */       throw new IllegalArgumentException("The max value must not be infinite or NaN");
/*      */     }
/*      */ 
/*  973 */     if (isFxApplicationThread())
/*  974 */       _updateProgress(paramDouble1, paramDouble2);
/*  975 */     else if (this.progressUpdate.getAndSet(new ProgressUpdate(paramDouble1, paramDouble2, null)) == null)
/*  976 */       runLater(new Runnable() {
/*      */         public void run() {
/*  978 */           Task.ProgressUpdate localProgressUpdate = (Task.ProgressUpdate)Task.this.progressUpdate.getAndSet(null);
/*  979 */           Task.this._updateProgress(localProgressUpdate.workDone, localProgressUpdate.totalWork);
/*      */         }
/*      */       });
/*      */   }
/*      */ 
/*      */   private void _updateProgress(double paramDouble1, double paramDouble2)
/*      */   {
/*  986 */     setTotalWork(paramDouble2);
/*  987 */     setWorkDone(paramDouble1);
/*  988 */     if (paramDouble1 == -1.0D)
/*  989 */       setProgress(-1.0D);
/*      */     else
/*  991 */       setProgress(paramDouble1 / paramDouble2);
/*      */   }
/*      */ 
/*      */   protected void updateMessage(String paramString)
/*      */   {
/* 1009 */     if (isFxApplicationThread()) {
/* 1010 */       this.message.set(paramString);
/*      */     }
/* 1016 */     else if (this.messageUpdate.getAndSet(paramString) == null)
/* 1017 */       runLater(new Runnable() {
/*      */         public void run() {
/* 1019 */           String str = (String)Task.this.messageUpdate.getAndSet(null);
/* 1020 */           Task.this.message.set(str);
/*      */         }
/*      */       });
/*      */   }
/*      */ 
/*      */   protected void updateTitle(String paramString)
/*      */   {
/* 1041 */     if (isFxApplicationThread()) {
/* 1042 */       this.title.set(paramString);
/*      */     }
/* 1048 */     else if (this.titleUpdate.getAndSet(paramString) == null)
/* 1049 */       runLater(new Runnable() {
/*      */         public void run() {
/* 1051 */           String str = (String)Task.this.titleUpdate.getAndSet(null);
/* 1052 */           Task.this.title.set(str);
/*      */         }
/*      */       });
/*      */   }
/*      */ 
/*      */   private void checkThread()
/*      */   {
/* 1064 */     if (!isFxApplicationThread())
/* 1065 */       throw new IllegalStateException("Task must only be used from the FX Application Thread");
/*      */   }
/*      */ 
/*      */   void runLater(Runnable paramRunnable)
/*      */   {
/* 1072 */     Platform.runLater(paramRunnable);
/*      */   }
/*      */ 
/*      */   boolean isFxApplicationThread()
/*      */   {
/* 1078 */     return Platform.isFxApplicationThread();
/*      */   }
/*      */ 
/*      */   private EventHelper getEventHelper()
/*      */   {
/* 1089 */     if (this.eventHelper == null) {
/* 1090 */       this.eventHelper = new EventHelper(this);
/*      */     }
/* 1092 */     return this.eventHelper;
/*      */   }
/*      */ 
/*      */   public final <T extends Event> void addEventHandler(EventType<T> paramEventType, EventHandler<? super T> paramEventHandler)
/*      */   {
/* 1110 */     getEventHelper().addEventHandler(paramEventType, paramEventHandler);
/*      */   }
/*      */ 
/*      */   public final <T extends Event> void removeEventHandler(EventType<T> paramEventType, EventHandler<? super T> paramEventHandler)
/*      */   {
/* 1127 */     getEventHelper().removeEventHandler(paramEventType, paramEventHandler);
/*      */   }
/*      */ 
/*      */   public final <T extends Event> void addEventFilter(EventType<T> paramEventType, EventHandler<? super T> paramEventHandler)
/*      */   {
/* 1142 */     getEventHelper().addEventFilter(paramEventType, paramEventHandler);
/*      */   }
/*      */ 
/*      */   public final <T extends Event> void removeEventFilter(EventType<T> paramEventType, EventHandler<? super T> paramEventHandler)
/*      */   {
/* 1159 */     getEventHelper().removeEventFilter(paramEventType, paramEventHandler);
/*      */   }
/*      */ 
/*      */   protected final <T extends Event> void setEventHandler(EventType<T> paramEventType, EventHandler<? super T> paramEventHandler)
/*      */   {
/* 1176 */     getEventHelper().setEventHandler(paramEventType, paramEventHandler);
/*      */   }
/*      */ 
/*      */   public final void fireEvent(Event paramEvent)
/*      */   {
/* 1191 */     checkThread();
/* 1192 */     getEventHelper().fireEvent(paramEvent);
/*      */   }
/*      */ 
/*      */   public EventDispatchChain buildEventDispatchChain(EventDispatchChain paramEventDispatchChain)
/*      */   {
/* 1197 */     return getEventHelper().buildEventDispatchChain(paramEventDispatchChain);
/*      */   }
/*      */ 
/*      */   private static final class TaskCallable<V>
/*      */     implements Callable<V>
/*      */   {
/*      */     private Task<V> task;
/*      */ 
/*      */     public V call()
/*      */       throws Exception
/*      */     {
/* 1251 */       this.task.runLater(new Runnable() {
/*      */         public void run() {
/* 1253 */           Task.this.setState(Worker.State.SCHEDULED);
/* 1254 */           Task.this.setState(Worker.State.RUNNING);
/*      */         }
/*      */       });
/*      */       try
/*      */       {
/* 1259 */         final Object localObject = this.task.call();
/* 1260 */         if (!this.task.isCancelled())
/*      */         {
/* 1263 */           this.task.runLater(new Runnable()
/*      */           {
/*      */             public void run()
/*      */             {
/* 1270 */               Task.this.setValue(localObject);
/* 1271 */               Task.this.setState(Worker.State.SUCCEEDED);
/*      */             }
/*      */           });
/* 1274 */           return localObject;
/*      */         }
/*      */ 
/* 1279 */         return this.task.getValue();
/*      */       }
/*      */       catch (Throwable localThrowable)
/*      */       {
/* 1290 */         this.task.runLater(new Runnable() {
/*      */           public void run() {
/* 1292 */             Task.this._setException(localThrowable);
/* 1293 */             Task.this.setState(Worker.State.FAILED);
/*      */           }
/*      */         });
/* 1303 */         if ((localThrowable instanceof Exception)) {
/* 1304 */           throw ((Exception)localThrowable);
/*      */         }
/* 1306 */         throw new Exception(localThrowable);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private static final class ProgressUpdate
/*      */   {
/*      */     private double workDone;
/*      */     private double totalWork;
/*      */ 
/*      */     private ProgressUpdate(double paramDouble1, double paramDouble2)
/*      */     {
/* 1212 */       this.workDone = paramDouble1;
/* 1213 */       this.totalWork = paramDouble2;
/*      */     }
/*      */   }
/*      */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.concurrent.Task
 * JD-Core Version:    0.6.2
 */