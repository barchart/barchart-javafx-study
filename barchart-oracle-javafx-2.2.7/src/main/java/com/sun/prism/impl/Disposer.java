/*     */ package com.sun.prism.impl;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.lang.ref.PhantomReference;
/*     */ import java.lang.ref.Reference;
/*     */ import java.lang.ref.ReferenceQueue;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.Hashtable;
/*     */ import java.util.LinkedList;
/*     */ 
/*     */ public class Disposer
/*     */ {
/*  70 */   private static Disposer disposerInstance = new Disposer();
/*     */   private static final int WEAK = 0;
/*     */   private static final int PHANTOM = 1;
/*     */   private static final int SOFT = 2;
/*  54 */   private static int refType = 1;
/*     */ 
/*  73 */   private final ReferenceQueue queue = new ReferenceQueue();
/*  74 */   private final Hashtable records = new Hashtable();
/*  75 */   private final LinkedList<Record> disposalQueue = new LinkedList();
/*     */ 
/*     */   public static void addRecord(Object paramObject, Record paramRecord)
/*     */   {
/* 106 */     disposerInstance.add(paramObject, paramRecord);
/*     */   }
/*     */ 
/*     */   public static void disposeRecord(Record paramRecord)
/*     */   {
/* 116 */     disposerInstance.addToDisposalQueue(paramRecord);
/*     */   }
/*     */ 
/*     */   public static void cleanUp()
/*     */   {
/* 129 */     disposerInstance.disposeUnreachables();
/* 130 */     disposerInstance.processDisposalQueue();
/*     */   }
/*     */ 
/*     */   private synchronized void add(Object paramObject, Record paramRecord)
/*     */   {
/* 142 */     if ((paramObject instanceof Target))
/* 143 */       paramObject = ((Target)paramObject).getDisposerReferent();
/*     */     Object localObject;
/* 146 */     if (refType == 1)
/* 147 */       localObject = new PhantomReference(paramObject, this.queue);
/* 148 */     else if (refType == 2)
/* 149 */       localObject = new SoftReference(paramObject, this.queue);
/*     */     else {
/* 151 */       localObject = new WeakReference(paramObject, this.queue);
/*     */     }
/* 153 */     this.records.put(localObject, paramRecord);
/*     */   }
/*     */ 
/*     */   private synchronized void addToDisposalQueue(Record paramRecord) {
/* 157 */     this.disposalQueue.add(paramRecord);
/*     */   }
/*     */ 
/*     */   private synchronized void disposeUnreachables()
/*     */   {
/*     */     Reference localReference;
/* 167 */     while ((localReference = this.queue.poll()) != null)
/*     */       try {
/* 169 */         ((Reference)localReference).clear();
/* 170 */         Record localRecord = (Record)this.records.remove(localReference);
/* 171 */         localRecord.dispose();
/* 172 */         localReference = null;
/* 173 */         localRecord = null;
/*     */       } catch (Exception localException) {
/* 175 */         System.out.println("Exception while removing reference: " + localException);
/* 176 */         localException.printStackTrace();
/*     */       }
/*     */   }
/*     */ 
/*     */   private synchronized void processDisposalQueue()
/*     */   {
/* 183 */     while (!this.disposalQueue.isEmpty())
/* 184 */       ((Record)this.disposalQueue.remove()).dispose();
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  57 */     String str = PrismSettings.refType;
/*  58 */     if (str != null)
/*  59 */       if (str.equals("weak")) {
/*  60 */         refType = 0;
/*  61 */         if (PrismSettings.verbose) System.err.println("Using WEAK refs"); 
/*     */       }
/*  62 */       else if (str.equals("soft")) {
/*  63 */         refType = 2;
/*  64 */         if (PrismSettings.verbose) System.err.println("Using SOFT refs"); 
/*     */       }
/*  66 */       else { refType = 1;
/*  67 */         if (PrismSettings.verbose) System.err.println("Using PHANTOM refs");
/*     */       }
/*     */   }
/*     */ 
/*     */   public static abstract interface Target
/*     */   {
/*     */     public abstract Object getDisposerReferent();
/*     */   }
/*     */ 
/*     */   public static abstract interface Record
/*     */   {
/*     */     public abstract void dispose();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.impl.Disposer
 * JD-Core Version:    0.6.2
 */