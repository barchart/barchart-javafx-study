/*     */ package com.sun.prism.impl;
/*     */ 
/*     */ import com.sun.prism.RenderingContext;
/*     */ import java.io.PrintStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class PrismTrace
/*     */ {
/*  16 */   private static final boolean enabled = PrismSettings.printAllocs;
/*     */   private static Map<Long, Long> texData;
/*     */   private static long texBytes;
/*     */   private static Map<Long, Long> rttData;
/*     */   private static long rttBytes;
/*     */ 
/*     */   private static String summary(long paramLong1, long paramLong2, String paramString)
/*     */   {
/*  41 */     return String.format("%s=%d@%,d", new Object[] { paramString, Long.valueOf(paramLong1), Long.valueOf(paramLong2) });
/*     */   }
/*     */   private static String summary(SummaryType paramSummaryType) {
/*  44 */     switch (2.$SwitchMap$com$sun$prism$impl$PrismTrace$SummaryType[paramSummaryType.ordinal()]) {
/*     */     case 1:
/*  46 */       return summary(texData.size(), texBytes >> 10, " tex");
/*     */     case 2:
/*  48 */       return summary(rttData.size(), rttBytes >> 10, " rtt");
/*     */     case 3:
/*  50 */       return summary(texData.size() + rttData.size(), texBytes + rttBytes >> 10, " combined");
/*     */     }
/*     */ 
/*  53 */     return "ERROR";
/*     */   }
/*     */ 
/*     */   private static long computeSize(int paramInt1, int paramInt2, int paramInt3) {
/*  57 */     long l = paramInt1;
/*  58 */     l *= paramInt2;
/*  59 */     l *= paramInt3;
/*  60 */     return l;
/*     */   }
/*     */ 
/*     */   public static void textureCreated(long paramLong, int paramInt1, int paramInt2, int paramInt3) {
/*  64 */     if (!enabled) return;
/*     */ 
/*  66 */     long l = computeSize(paramInt1, paramInt2, paramInt3);
/*  67 */     texData.put(Long.valueOf(paramLong), Long.valueOf(l));
/*  68 */     texBytes += l;
/*  69 */     System.out.println("Created Texture: id=" + paramLong + " w=" + paramInt1 + " h=" + paramInt2 + " size=" + l + summary(SummaryType.TYPE_TEX) + summary(SummaryType.TYPE_ALL));
/*     */   }
/*     */ 
/*     */   public static void textureDisposed(long paramLong)
/*     */   {
/*  77 */     if (!enabled) return;
/*     */ 
/*  79 */     Long localLong = (Long)texData.remove(Long.valueOf(paramLong));
/*  80 */     if (localLong == null) {
/*  81 */       throw new InternalError("Disposing unknown Texture " + paramLong);
/*     */     }
/*  83 */     texBytes -= localLong.longValue();
/*  84 */     System.out.println("Disposed Texture: id=" + paramLong + " size=" + localLong + summary(SummaryType.TYPE_TEX) + summary(SummaryType.TYPE_ALL));
/*     */   }
/*     */ 
/*     */   public static void rttCreated(long paramLong, int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/*  91 */     if (!enabled) return;
/*     */ 
/*  93 */     long l = computeSize(paramInt1, paramInt2, paramInt3);
/*  94 */     rttData.put(Long.valueOf(paramLong), Long.valueOf(l));
/*  95 */     rttBytes += l;
/*  96 */     System.out.println("Created RTTexture: id=" + paramLong + " w=" + paramInt1 + " h=" + paramInt2 + " size=" + l + summary(SummaryType.TYPE_RTT) + summary(SummaryType.TYPE_ALL));
/*     */   }
/*     */ 
/*     */   public static void rttDisposed(long paramLong)
/*     */   {
/* 103 */     if (!enabled) return;
/*     */ 
/* 105 */     Long localLong = (Long)rttData.remove(Long.valueOf(paramLong));
/* 106 */     if (localLong == null) {
/* 107 */       throw new InternalError("Disposing unknown RTTexture " + paramLong);
/*     */     }
/* 109 */     rttBytes -= localLong.longValue();
/* 110 */     System.out.println("Disposed RTTexture: id=" + paramLong + " size=" + localLong + summary(SummaryType.TYPE_RTT) + summary(SummaryType.TYPE_ALL));
/*     */   }
/*     */ 
/*     */   public static void checkRenderingContext(RenderingContext paramRenderingContext1, RenderingContext paramRenderingContext2)
/*     */   {
/* 118 */     if (!enabled) return;
/*     */ 
/* 120 */     if ((paramRenderingContext1 == null) || (paramRenderingContext1 != paramRenderingContext2)) {
/* 121 */       System.out.println("RT accessed from the wrong context");
/* 122 */       Thread.dumpStack();
/*     */     }
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  24 */     if (enabled) {
/*  25 */       texData = new HashMap();
/*  26 */       rttData = new HashMap();
/*  27 */       Runtime.getRuntime().addShutdownHook(new Thread("RTT printAlloc shutdown hook")
/*     */       {
/*     */         public void run() {
/*  30 */           System.out.println("Final Texture resources:" + PrismTrace.summary(PrismTrace.SummaryType.TYPE_TEX) + PrismTrace.summary(PrismTrace.SummaryType.TYPE_RTT) + PrismTrace.summary(PrismTrace.SummaryType.TYPE_ALL));
/*     */         }
/*     */       });
/*     */     }
/*     */   }
/*     */ 
/*     */   private static enum SummaryType
/*     */   {
/*  39 */     TYPE_TEX, TYPE_RTT, TYPE_ALL;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.prism.impl.PrismTrace
 * JD-Core Version:    0.6.2
 */