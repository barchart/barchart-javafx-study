/*     */ package com.sun.webpane.platform.graphics;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.HashMap;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ 
/*     */ class BufferData
/*     */ {
/* 146 */   private AtomicInteger idCount = new AtomicInteger(0);
/* 147 */   private HashMap<Integer, String> strMap = new HashMap();
/* 148 */   private HashMap<Integer, int[]> intArrMap = new HashMap();
/* 149 */   private HashMap<Integer, float[]> floatArrMap = new HashMap();
/*     */   private ByteBuffer buffer;
/*     */ 
/*     */   private int createID()
/*     */   {
/* 154 */     return this.idCount.incrementAndGet();
/*     */   }
/*     */ 
/*     */   int addIntArray(int[] paramArrayOfInt) {
/* 158 */     int i = createID();
/* 159 */     this.intArrMap.put(Integer.valueOf(i), paramArrayOfInt);
/* 160 */     return i;
/*     */   }
/*     */ 
/*     */   int[] getIntArray(int paramInt) {
/* 164 */     return (int[])this.intArrMap.get(Integer.valueOf(paramInt));
/*     */   }
/*     */ 
/*     */   int addFloatArray(float[] paramArrayOfFloat) {
/* 168 */     int i = createID();
/* 169 */     this.floatArrMap.put(Integer.valueOf(i), paramArrayOfFloat);
/* 170 */     return i;
/*     */   }
/*     */ 
/*     */   float[] getFloatArray(int paramInt) {
/* 174 */     return (float[])this.floatArrMap.get(Integer.valueOf(paramInt));
/*     */   }
/*     */ 
/*     */   int addString(String paramString) {
/* 178 */     int i = createID();
/* 179 */     this.strMap.put(Integer.valueOf(i), paramString);
/* 180 */     return i;
/*     */   }
/*     */ 
/*     */   String getString(int paramInt) {
/* 184 */     return (String)this.strMap.get(Integer.valueOf(paramInt));
/*     */   }
/*     */ 
/*     */   ByteBuffer getBuffer() {
/* 188 */     return this.buffer;
/*     */   }
/*     */ 
/*     */   void setBuffer(ByteBuffer paramByteBuffer) {
/* 192 */     this.buffer = paramByteBuffer;
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.platform.graphics.BufferData
 * JD-Core Version:    0.6.2
 */