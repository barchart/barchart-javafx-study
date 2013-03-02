/*    */ package com.sun.glass.utils;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import java.lang.ref.Reference;
/*    */ import java.lang.ref.ReferenceQueue;
/*    */ import java.lang.ref.WeakReference;
/*    */ import java.util.Hashtable;
/*    */ 
/*    */ public class Disposer
/*    */   implements Runnable
/*    */ {
/* 27 */   private static final ReferenceQueue queue = new ReferenceQueue();
/* 28 */   private static final Hashtable records = new Hashtable();
/*    */ 
/* 32 */   private static Disposer disposerInstance = new Disposer();
/*    */ 
/*    */   public static void init()
/*    */   {
/*    */   }
/*    */ 
/*    */   public static void addRecord(Object target, DisposerRecord rec)
/*    */   {
/* 53 */     disposerInstance.add(target, rec);
/*    */   }
/*    */ 
/*    */   synchronized void add(Object target, DisposerRecord rec)
/*    */   {
/* 63 */     records.put(new WeakReference(target, queue), rec);
/*    */   }
/*    */ 
/*    */   public void run() {
/*    */     while (true)
/*    */       try {
/* 69 */         Object obj = queue.remove();
/* 70 */         ((Reference)obj).clear();
/* 71 */         DisposerRecord rec = (DisposerRecord)records.remove(obj);
/* 72 */         rec.dispose();
/* 73 */         obj = null;
/* 74 */         rec = null;
/*    */       } catch (Exception e) {
/* 76 */         System.out.println("Exception while removing reference: " + e);
/* 77 */         e.printStackTrace();
/*    */       }
/*    */   }
/*    */ 
/*    */   static
/*    */   {
/* 34 */     ThreadGroup tg = Thread.currentThread().getThreadGroup();
/* 35 */     Thread t = new Thread(tg, disposerInstance, "Disposer");
/* 36 */     t.setDaemon(true);
/* 37 */     t.setPriority(10);
/* 38 */     t.start();
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.glass.utils.Disposer
 * JD-Core Version:    0.6.2
 */