/*     */ package com.sun.scenario.effect.impl;
/*     */ 
/*     */ import com.sun.scenario.effect.Filterable;
/*     */ import java.io.PrintStream;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ public class ImagePool
/*     */ {
/*     */   public static long numEffects;
/*     */   static long numCreated;
/*     */   static long pixelsCreated;
/*     */   static long numAccessed;
/*     */   static long pixelsAccessed;
/*     */   static final int QUANT = 32;
/*  81 */   private final List<SoftReference<Filterable>> unlocked = new ArrayList();
/*     */ 
/*  83 */   private final List<SoftReference<Filterable>> locked = new ArrayList();
/*     */ 
/* 100 */   private final boolean usePurgatory = Boolean.getBoolean("decora.purgatory");
/* 101 */   private final List<Filterable> hardPurgatory = new ArrayList();
/* 102 */   private final List<SoftReference<Filterable>> softPurgatory = new ArrayList();
/*     */ 
/*     */   static void printStats()
/*     */   {
/*  66 */     System.out.println("effects executed:  " + numEffects);
/*  67 */     System.out.println("images created:    " + numCreated);
/*  68 */     System.out.println("pixels created:    " + pixelsCreated);
/*  69 */     System.out.println("images accessed:   " + numAccessed);
/*  70 */     System.out.println("pixels accessed:   " + pixelsAccessed);
/*  71 */     if (numEffects != 0L) {
/*  72 */       double d1 = numAccessed / numEffects;
/*  73 */       double d2 = pixelsAccessed / numEffects;
/*  74 */       System.out.println("images per effect: " + d1);
/*  75 */       System.out.println("pixels per effect: " + d2);
/*     */     }
/*     */   }
/*     */ 
/*     */   public synchronized Filterable checkOut(Renderer paramRenderer, int paramInt1, int paramInt2)
/*     */   {
/* 112 */     if ((paramInt1 <= 0) || (paramInt2 <= 0))
/*     */     {
/* 114 */       paramInt1 = paramInt2 = 1;
/*     */     }
/*     */ 
/* 117 */     paramInt1 = (paramInt1 + 32 - 1) / 32 * 32;
/* 118 */     paramInt2 = (paramInt2 + 32 - 1) / 32 * 32;
/* 119 */     numAccessed += 1L;
/* 120 */     pixelsAccessed += paramInt1 * paramInt2;
/*     */ 
/* 123 */     Object localObject1 = null;
/* 124 */     Object localObject2 = null;
/* 125 */     int i = 2147483647;
/* 126 */     Iterator localIterator = this.unlocked.iterator();
/*     */     Filterable localFilterable;
/* 127 */     while (localIterator.hasNext()) {
/* 128 */       localObject3 = (SoftReference)localIterator.next();
/* 129 */       localFilterable = (Filterable)((SoftReference)localObject3).get();
/* 130 */       if (localFilterable == null) {
/* 131 */         localIterator.remove();
/*     */       }
/*     */       else {
/* 134 */         int j = localFilterable.getPhysicalWidth();
/* 135 */         int k = localFilterable.getPhysicalHeight();
/* 136 */         if ((j >= paramInt1) && (k >= paramInt2)) {
/* 137 */           int m = (j - paramInt1) * (k - paramInt2);
/* 138 */           if ((localObject1 == null) || (m < i)) {
/* 139 */             localObject1 = localObject3;
/* 140 */             localObject2 = localFilterable;
/* 141 */             i = m;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 146 */     if (localObject1 != null) {
/* 147 */       this.unlocked.remove(localObject1);
/* 148 */       this.locked.add(localObject1);
/* 149 */       paramRenderer.clearImage(localObject2);
/* 150 */       return localObject2;
/*     */     }
/*     */ 
/* 154 */     localIterator = this.locked.iterator();
/* 155 */     while (localIterator.hasNext()) {
/* 156 */       localObject3 = (SoftReference)localIterator.next();
/* 157 */       localFilterable = (Filterable)((SoftReference)localObject3).get();
/* 158 */       if (localFilterable == null) {
/* 159 */         localIterator.remove();
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 164 */     Object localObject3 = null;
/*     */     try {
/* 166 */       localObject3 = paramRenderer.createCompatibleImage(paramInt1, paramInt2);
/*     */     } catch (OutOfMemoryError localOutOfMemoryError1) {
/*     */     }
/* 169 */     if (localObject3 == null)
/*     */     {
/* 171 */       pruneCache();
/*     */       try {
/* 173 */         localObject3 = paramRenderer.createCompatibleImage(paramInt1, paramInt2); } catch (OutOfMemoryError localOutOfMemoryError2) {
/*     */       }
/*     */     }
/* 176 */     if (localObject3 != null) {
/* 177 */       this.locked.add(new SoftReference(localObject3));
/* 178 */       numCreated += 1L;
/* 179 */       pixelsCreated += paramInt1 * paramInt2;
/*     */     }
/* 181 */     return localObject3;
/*     */   }
/*     */ 
/*     */   public synchronized void checkIn(Filterable paramFilterable) {
/* 185 */     Object localObject1 = null;
/* 186 */     Object localObject2 = null;
/* 187 */     Iterator localIterator = this.locked.iterator();
/* 188 */     while (localIterator.hasNext()) {
/* 189 */       SoftReference localSoftReference = (SoftReference)localIterator.next();
/* 190 */       Filterable localFilterable = (Filterable)localSoftReference.get();
/* 191 */       if (localFilterable == null) {
/* 192 */         localIterator.remove();
/* 193 */       } else if (localFilterable == paramFilterable) {
/* 194 */         localObject1 = localSoftReference;
/* 195 */         localObject2 = localFilterable;
/* 196 */         break;
/*     */       }
/*     */     }
/*     */ 
/* 200 */     if (localObject1 != null) {
/* 201 */       this.locked.remove(localObject1);
/* 202 */       if (this.usePurgatory)
/*     */       {
/* 209 */         this.hardPurgatory.add(localObject2);
/* 210 */         this.softPurgatory.add(localObject1);
/*     */       } else {
/* 212 */         this.unlocked.add(localObject1);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public synchronized void releasePurgatory() {
/* 218 */     if ((this.usePurgatory) && (!this.softPurgatory.isEmpty()))
/*     */     {
/* 221 */       this.unlocked.addAll(this.softPurgatory);
/* 222 */       this.softPurgatory.clear();
/* 223 */       this.hardPurgatory.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void pruneCache()
/*     */   {
/* 229 */     for (SoftReference localSoftReference : this.unlocked) {
/* 230 */       Filterable localFilterable = (Filterable)localSoftReference.get();
/* 231 */       if (localFilterable != null) {
/* 232 */         localFilterable.flush();
/*     */       }
/*     */     }
/* 235 */     this.unlocked.clear();
/*     */ 
/* 238 */     System.gc();
/* 239 */     System.runFinalization();
/* 240 */     System.gc();
/* 241 */     System.runFinalization();
/*     */   }
/*     */ 
/*     */   public synchronized void dispose() {
/* 245 */     for (SoftReference localSoftReference : this.unlocked) {
/* 246 */       Filterable localFilterable = (Filterable)localSoftReference.get();
/* 247 */       if (localFilterable != null) {
/* 248 */         localFilterable.flush();
/*     */       }
/*     */     }
/* 251 */     this.unlocked.clear();
/*     */ 
/* 253 */     this.locked.clear();
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  51 */     AccessController.doPrivileged(new PrivilegedAction() {
/*     */       public Object run() {
/*  53 */         if (System.getProperty("decora.showstats") != null) {
/*  54 */           Runtime.getRuntime().addShutdownHook(new Thread() {
/*     */             public void run() {
/*  56 */               ImagePool.printStats();
/*     */             }
/*     */           });
/*     */         }
/*  60 */         return null;
/*     */       }
/*     */     });
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.scenario.effect.impl.ImagePool
 * JD-Core Version:    0.6.2
 */