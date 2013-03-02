/*     */ package com.sun.webpane.platform;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.lang.ref.ReferenceQueue;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.LinkedBlockingQueue;
/*     */ 
/*     */ public class Disposer
/*     */   implements Runnable
/*     */ {
/*  31 */   private static final ReferenceQueue queue = new ReferenceQueue();
/*  32 */   private static final Disposer disposerInstance = new Disposer();
/*  33 */   private static final Set<WeakDisposerRecord> records = new HashSet();
/*     */ 
/*     */   public static void addRecord(Object paramObject, DisposerRecord paramDisposerRecord)
/*     */   {
/*  50 */     disposerInstance.add(paramObject, paramDisposerRecord);
/*     */   }
/*     */ 
/*     */   synchronized void add(Object paramObject, DisposerRecord paramDisposerRecord)
/*     */   {
/*  60 */     records.add(new WeakDisposerRecord(paramObject, paramDisposerRecord));
/*     */   }
/*     */ 
/*     */   public void run() {
/*     */     while (true)
/*     */       try {
/*  66 */         WeakDisposerRecord localWeakDisposerRecord = (WeakDisposerRecord)queue.remove();
/*  67 */         localWeakDisposerRecord.clear();
/*  68 */         DisposerRunnable.access$000().enqueue(localWeakDisposerRecord);
/*     */       } catch (Exception localException) {
/*  70 */         System.out.println("Exception while removing reference: " + localException);
/*  71 */         localException.printStackTrace();
/*     */       }
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  37 */     Thread localThread = new Thread(disposerInstance, "Disposer");
/*  38 */     localThread.setDaemon(true);
/*  39 */     localThread.setPriority(10);
/*  40 */     localThread.start();
/*     */   }
/*     */ 
/*     */   public static class WeakDisposerRecord extends WeakReference
/*     */     implements DisposerRecord, Runnable
/*     */   {
/*     */     DisposerRecord record;
/*     */ 
/*     */     public WeakDisposerRecord(Object paramObject)
/*     */     {
/* 116 */       super(Disposer.queue);
/*     */     }
/*     */ 
/*     */     public WeakDisposerRecord(Object paramObject, DisposerRecord paramDisposerRecord) {
/* 120 */       super(Disposer.queue);
/* 121 */       this.record = paramDisposerRecord;
/*     */     }
/*     */ 
/*     */     public void dispose()
/*     */     {
/* 128 */       this.record.dispose();
/*     */     }
/*     */ 
/*     */     public void run() {
/* 132 */       Disposer.records.remove(this);
/* 133 */       dispose();
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class DisposerRunnable
/*     */     implements Runnable
/*     */   {
/*  77 */     private static final DisposerRunnable theInstance = new DisposerRunnable();
/*     */ 
/*  83 */     private boolean isRunning = false;
/*  84 */     private final Object disposerLock = new Object();
/*  85 */     private final LinkedBlockingQueue<Disposer.WeakDisposerRecord> disposerQueue = new LinkedBlockingQueue();
/*     */ 
/*     */     private static DisposerRunnable getInstance()
/*     */     {
/*  80 */       return theInstance;
/*     */     }
/*     */ 
/*     */     private void enqueue(Disposer.WeakDisposerRecord paramWeakDisposerRecord)
/*     */     {
/*  89 */       synchronized (this.disposerLock) {
/*  90 */         this.disposerQueue.add(paramWeakDisposerRecord);
/*  91 */         if (!this.isRunning) {
/*  92 */           Invoker.getInvoker().invokeOnEventThread(this);
/*  93 */           this.isRunning = true;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     public void run()
/*     */     {
/*     */       while (true)
/*     */       {
/*     */         Disposer.WeakDisposerRecord localWeakDisposerRecord;
/* 101 */         synchronized (this.disposerLock) {
/* 102 */           localWeakDisposerRecord = (Disposer.WeakDisposerRecord)this.disposerQueue.poll();
/* 103 */           if (localWeakDisposerRecord == null) {
/* 104 */             this.isRunning = false;
/* 105 */             break;
/*     */           }
/*     */         }
/* 108 */         localWeakDisposerRecord.run();
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.platform.Disposer
 * JD-Core Version:    0.6.2
 */