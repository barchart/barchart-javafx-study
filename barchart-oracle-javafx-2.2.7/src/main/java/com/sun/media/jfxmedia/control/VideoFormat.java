/*     */ package com.sun.media.jfxmedia.control;
/*     */ 
/*     */ import java.util.EnumSet;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ public enum VideoFormat
/*     */ {
/*  71 */   ARGB(1), 
/*     */ 
/*  74 */   BGRA_PRE(2), 
/*     */ 
/*  77 */   YCbCr_420p(100), 
/*     */ 
/*  80 */   YCbCr_422(101);
/*     */ 
/*     */   private int nativeType;
/*     */   private static final Map<Integer, VideoFormat> lookupMap;
/*     */ 
/*     */   private VideoFormat(int ntype)
/*     */   {
/*  91 */     this.nativeType = ntype;
/*     */   }
/*     */ 
/*     */   public int getNativeType() {
/*  95 */     return this.nativeType;
/*     */   }
/*     */ 
/*     */   public boolean isRGB() {
/*  99 */     return (this == ARGB) || (this == BGRA_PRE);
/*     */   }
/*     */ 
/*     */   public boolean isEqualTo(int ntype) {
/* 103 */     return this.nativeType == ntype;
/*     */   }
/*     */ 
/*     */   public static VideoFormat formatForType(int ntype) {
/* 107 */     return (VideoFormat)lookupMap.get(Integer.valueOf(ntype));
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  83 */     lookupMap = new HashMap();
/*     */ 
/*  85 */     for (VideoFormat fmt : EnumSet.allOf(VideoFormat.class))
/*  86 */       lookupMap.put(Integer.valueOf(fmt.getNativeType()), fmt);
/*     */   }
/*     */ 
/*     */   public static class FormatTypes
/*     */   {
/*     */     public static final int FORMAT_TYPE_ARGB = 1;
/*     */     public static final int FORMAT_TYPE_BGRA_PRE = 2;
/*     */     public static final int FORMAT_TYPE_YCBCR_420P = 100;
/*     */     public static final int FORMAT_TYPE_YCBCR_422 = 101;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.media.jfxmedia.control.VideoFormat
 * JD-Core Version:    0.6.2
 */