/*    */ package com.sun.t2k;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import java.lang.ref.Reference;
/*    */ import java.lang.ref.ReferenceQueue;
/*    */ import java.lang.ref.WeakReference;
/*    */ import java.security.AccessController;
/*    */ import java.security.PrivilegedAction;
/*    */ import java.util.Hashtable;
/*    */ 
/*    */ public class Disposer
/*    */   implements Runnable
/*    */ {
/* 25 */   private static final ReferenceQueue queue = new ReferenceQueue();
/* 26 */   private static final Hashtable records = new Hashtable();
/*    */ 
/* 30 */   private static Disposer disposerInstance = new Disposer();
/*    */ 
/*    */   public static WeakReference addRecord(Object paramObject, DisposerRecord paramDisposerRecord)
/*    */   {
/* 63 */     WeakReference localWeakReference = new WeakReference(paramObject, queue);
/* 64 */     records.put(localWeakReference, paramDisposerRecord);
/* 65 */     return localWeakReference;
/*    */   }
/*    */ 
/*    */   public void run() {
/*    */     while (true)
/*    */       try {
/* 71 */         Reference localReference = queue.remove();
/* 72 */         ((Reference)localReference).clear();
/* 73 */         DisposerRecord localDisposerRecord = (DisposerRecord)records.remove(localReference);
/* 74 */         localDisposerRecord.dispose();
/* 75 */         localReference = null;
/* 76 */         localDisposerRecord = null;
/*    */       } catch (Exception localException) {
/* 78 */         System.out.println("Exception while removing reference: " + localException);
/* 79 */         localException.printStackTrace();
/*    */       }
/*    */   }
/*    */ 
/*    */   static
/*    */   {
/* 32 */     ThreadGroup localThreadGroup = Thread.currentThread().getThreadGroup();
/* 33 */     AccessController.doPrivileged(new PrivilegedAction()
/*    */     {
/*    */       public Object run()
/*    */       {
/* 40 */         Object localObject1 = Thread.currentThread().getThreadGroup();
/* 41 */         for (Object localObject2 = localObject1; 
/* 42 */           localObject2 != null; 
/* 43 */           localObject2 = ((ThreadGroup)localObject1).getParent()) localObject1 = localObject2;
/* 44 */         localObject2 = new Thread((ThreadGroup)localObject1, Disposer.disposerInstance, "Prism Font Disposer");
/*    */ 
/* 46 */         ((Thread)localObject2).setContextClassLoader(null);
/* 47 */         ((Thread)localObject2).setDaemon(true);
/* 48 */         ((Thread)localObject2).setPriority(10);
/* 49 */         ((Thread)localObject2).start();
/* 50 */         return null;
/*    */       }
/*    */     });
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.t2k.Disposer
 * JD-Core Version:    0.6.2
 */