/*     */ package javafx.concurrent;
/*     */ 
/*     */ import javafx.beans.property.ReadOnlyBooleanProperty;
/*     */ import javafx.beans.property.ReadOnlyDoubleProperty;
/*     */ import javafx.beans.property.ReadOnlyObjectProperty;
/*     */ import javafx.beans.property.ReadOnlyStringProperty;
/*     */ 
/*     */ public abstract interface Worker<V>
/*     */ {
/*     */   public abstract State getState();
/*     */ 
/*     */   public abstract ReadOnlyObjectProperty<State> stateProperty();
/*     */ 
/*     */   public abstract V getValue();
/*     */ 
/*     */   public abstract ReadOnlyObjectProperty<V> valueProperty();
/*     */ 
/*     */   public abstract Throwable getException();
/*     */ 
/*     */   public abstract ReadOnlyObjectProperty<Throwable> exceptionProperty();
/*     */ 
/*     */   public abstract double getWorkDone();
/*     */ 
/*     */   public abstract ReadOnlyDoubleProperty workDoneProperty();
/*     */ 
/*     */   public abstract double getTotalWork();
/*     */ 
/*     */   public abstract ReadOnlyDoubleProperty totalWorkProperty();
/*     */ 
/*     */   public abstract double getProgress();
/*     */ 
/*     */   public abstract ReadOnlyDoubleProperty progressProperty();
/*     */ 
/*     */   public abstract boolean isRunning();
/*     */ 
/*     */   public abstract ReadOnlyBooleanProperty runningProperty();
/*     */ 
/*     */   public abstract String getMessage();
/*     */ 
/*     */   public abstract ReadOnlyStringProperty messageProperty();
/*     */ 
/*     */   public abstract String getTitle();
/*     */ 
/*     */   public abstract ReadOnlyStringProperty titleProperty();
/*     */ 
/*     */   public abstract boolean cancel();
/*     */ 
/*     */   public static enum State
/*     */   {
/* 129 */     READY, 
/*     */ 
/* 136 */     SCHEDULED, 
/*     */ 
/* 141 */     RUNNING, 
/*     */ 
/* 146 */     SUCCEEDED, 
/*     */ 
/* 151 */     CANCELLED, 
/*     */ 
/* 157 */     FAILED;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     javafx.concurrent.Worker
 * JD-Core Version:    0.6.2
 */