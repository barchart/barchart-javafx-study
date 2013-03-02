/*     */ package com.sun.media.jfxmediaimpl;
/*     */ 
/*     */ import com.sun.media.jfxmedia.logging.Logger;
/*     */ import java.lang.ref.PhantomReference;
/*     */ import java.lang.ref.Reference;
/*     */ import java.lang.ref.ReferenceQueue;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class MediaDisposer
/*     */ {
/*     */   private final ReferenceQueue<Object> purgatory;
/*     */   private final Map<Reference, Disposable> disposers;
/*     */   private static MediaDisposer theDisposinator;
/*     */ 
/*     */   public static void addResourceDisposer(Object referent, Object resource, ResourceDisposer disposer)
/*     */   {
/*  54 */     disposinator().implAddResourceDisposer(referent, resource, disposer);
/*     */   }
/*     */ 
/*     */   public static void removeResourceDisposer(Object resource)
/*     */   {
/*  65 */     disposinator().implRemoveResourceDisposer(resource);
/*     */   }
/*     */ 
/*     */   public static void addDisposable(Object referent, Disposable disposable)
/*     */   {
/*  75 */     disposinator().implAddDisposable(referent, disposable);
/*     */   }
/*     */ 
/*     */   private static synchronized MediaDisposer disposinator()
/*     */   {
/*  83 */     if (null == theDisposinator) {
/*  84 */       theDisposinator = new MediaDisposer();
/*     */ 
/*  87 */       Thread disposerThread = new Thread(new Runnable()
/*     */       {
/*     */         public void run() {
/*  90 */           MediaDisposer.theDisposinator.disposerLoop();
/*     */         }
/*     */       }
/*     */       , "Media Resource Disposer");
/*     */ 
/*  94 */       disposerThread.setDaemon(true);
/*  95 */       disposerThread.start();
/*     */     }
/*  97 */     return theDisposinator;
/*     */   }
/*     */ 
/*     */   private MediaDisposer() {
/* 101 */     this.purgatory = new ReferenceQueue();
/*     */ 
/* 104 */     this.disposers = new HashMap();
/*     */   }
/*     */ 
/*     */   private void disposerLoop()
/*     */   {
/*     */     while (true)
/*     */       try {
/* 111 */         Reference denizen = this.purgatory.remove();
/*     */ 
/* 114 */         synchronized (this.disposers) {
/* 115 */           disposer = (Disposable)this.disposers.remove(denizen);
/*     */         }
/*     */ 
/* 118 */         denizen.clear();
/* 119 */         if (null != disposer) {
/* 120 */           disposer.dispose();
/*     */         }
/* 122 */         denizen = null;
/* 123 */         Disposable disposer = null;
/*     */       } catch (InterruptedException ex) {
/* 125 */         if (Logger.canLog(1))
/* 126 */           Logger.logMsg(1, MediaDisposer.class.getName(), "disposerLoop", "Disposer loop interrupted, terminating");
/*     */       }
/*     */   }
/*     */ 
/*     */   private void implAddResourceDisposer(Object referent, Object resource, ResourceDisposer disposer)
/*     */   {
/* 134 */     Reference denizen = new PhantomReference(referent, this.purgatory);
/* 135 */     synchronized (this.disposers) {
/* 136 */       this.disposers.put(denizen, new ResourceDisposerRecord(resource, disposer));
/*     */     }
/*     */   }
/*     */ 
/*     */   private void implRemoveResourceDisposer(Object resource) {
/* 141 */     Reference resourceKey = null;
/*     */ 
/* 143 */     synchronized (this.disposers) {
/* 144 */       for (Iterator i$ = this.disposers.entrySet().iterator(); i$.hasNext(); ) { entry = (Map.Entry)i$.next();
/* 145 */         Disposable disposer = (Disposable)entry.getValue();
/* 146 */         if ((disposer instanceof ResourceDisposerRecord)) {
/* 147 */           ResourceDisposerRecord rd = (ResourceDisposerRecord)disposer;
/* 148 */           if (rd.resource.equals(resource)) {
/* 149 */             resourceKey = (Reference)entry.getKey();
/* 150 */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */       Map.Entry entry;
/* 155 */       if (null != resourceKey)
/* 156 */         this.disposers.remove(resourceKey);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void implAddDisposable(Object referent, Disposable disposer)
/*     */   {
/* 162 */     Reference denizen = new PhantomReference(referent, this.purgatory);
/* 163 */     synchronized (this.disposers) {
/* 164 */       this.disposers.put(denizen, disposer);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class ResourceDisposerRecord implements MediaDisposer.Disposable {
/*     */     Object resource;
/*     */     MediaDisposer.ResourceDisposer disposer;
/*     */ 
/* 173 */     public ResourceDisposerRecord(Object resource, MediaDisposer.ResourceDisposer disposer) { this.resource = resource;
/* 174 */       this.disposer = disposer; }
/*     */ 
/*     */     public void dispose()
/*     */     {
/* 178 */       this.disposer.disposeResource(this.resource);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static abstract interface ResourceDisposer
/*     */   {
/*     */     public abstract void disposeResource(Object paramObject);
/*     */   }
/*     */ 
/*     */   public static abstract interface Disposable
/*     */   {
/*     */     public abstract void dispose();
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.media.jfxmediaimpl.MediaDisposer
 * JD-Core Version:    0.6.2
 */