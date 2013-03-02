/*     */ package com.sun.webpane.platform.graphics;
/*     */ 
/*     */ import com.sun.webpane.platform.Invoker;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.LinkedList;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ public class WCRenderQueue extends Ref
/*     */ {
/*  15 */   private static final AtomicInteger idCountObj = new AtomicInteger(0);
/*  16 */   private static final Logger log = Logger.getLogger(WCRenderQueue.class.getName());
/*     */   public static final int MAX_QUEUE_SIZE = 524288;
/*  20 */   private LinkedList<BufferData> buffers = new LinkedList();
/*  21 */   private BufferData currentBuffer = new BufferData();
/*     */   private WCRectangle clip;
/*  23 */   private int size = 0;
/*     */   private WCGraphicsContext gc;
/*     */ 
/*     */   public WCRenderQueue(WCGraphicsContext paramWCGraphicsContext)
/*     */   {
/*  29 */     this.gc = paramWCGraphicsContext;
/*     */   }
/*     */ 
/*     */   public WCRenderQueue(WCRectangle paramWCRectangle) {
/*  33 */     this.clip = paramWCRectangle;
/*     */   }
/*     */ 
/*     */   public synchronized int getSize() {
/*  37 */     return this.size;
/*     */   }
/*     */ 
/*     */   public void addBuffer(ByteBuffer paramByteBuffer)
/*     */   {
/*  42 */     synchronized (this) {
/*  43 */       if ((log.isLoggable(Level.FINE)) && (this.buffers.isEmpty())) {
/*  44 */         log.log(Level.FINE, "'{'WCRenderQueue{0}[{1}]", new Object[] { Integer.valueOf(hashCode()), Integer.valueOf(idCountObj.incrementAndGet()) });
/*     */       }
/*     */ 
/*  47 */       this.currentBuffer.setBuffer(paramByteBuffer);
/*  48 */       this.buffers.addLast(this.currentBuffer);
/*  49 */       this.currentBuffer = new BufferData();
/*  50 */       this.size += paramByteBuffer.capacity();
/*     */     }
/*  52 */     if ((this.size > 524288) && (this.gc != null))
/*     */     {
/*  56 */       flush();
/*     */     }
/*     */   }
/*     */ 
/*     */   public synchronized boolean isEmpty() {
/*  61 */     return this.buffers.isEmpty();
/*     */   }
/*     */ 
/*     */   public synchronized void decode(WCGraphicsContext paramWCGraphicsContext) {
/*  65 */     for (BufferData localBufferData : this.buffers) {
/*     */       try {
/*  67 */         GraphicsDecoder.decode(WCGraphicsManager.getGraphicsManager(), paramWCGraphicsContext, localBufferData);
/*     */       }
/*     */       catch (RuntimeException localRuntimeException) {
/*  70 */         localRuntimeException.printStackTrace();
/*     */       }
/*     */     }
/*  73 */     dispose();
/*     */   }
/*     */ 
/*     */   public synchronized void decode() {
/*  77 */     assert (this.gc != null);
/*  78 */     decode(this.gc);
/*     */   }
/*     */ 
/*     */   public void flush() {
/*  82 */     if (!isEmpty()) {
/*  83 */       Invoker.getInvoker().runOnRenderThread(new Runnable() {
/*     */         public void run() {
/*  85 */           WCRenderQueue.this.decode();
/*     */         }
/*     */       });
/*  88 */       this.size = 0;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void fwkFlush() {
/*  93 */     flush();
/*     */   }
/*     */ 
/*     */   private void fwkAddBuffer(ByteBuffer paramByteBuffer) {
/*  97 */     addBuffer(paramByteBuffer);
/*     */   }
/*     */ 
/*     */   public WCRectangle getClip() {
/* 101 */     return this.clip;
/*     */   }
/*     */ 
/*     */   public synchronized void dispose() {
/* 105 */     int i = this.buffers.size();
/* 106 */     if (i > 0) {
/* 107 */       int j = 0;
/* 108 */       final Object[] arrayOfObject = new Object[i];
/* 109 */       for (BufferData localBufferData : this.buffers) {
/* 110 */         arrayOfObject[(j++)] = localBufferData.getBuffer();
/*     */       }
/* 112 */       this.buffers.clear();
/* 113 */       Invoker.getInvoker().invokeOnEventThread(new Runnable() {
/*     */         public void run() {
/* 115 */           WCRenderQueue.this.twkRelease(arrayOfObject);
/*     */         }
/*     */       });
/* 118 */       this.size = 0;
/* 119 */       if (log.isLoggable(Level.FINE))
/* 120 */         log.log(Level.FINE, "'}'WCRenderQueue{0}[{1}]", new Object[] { Integer.valueOf(hashCode()), Integer.valueOf(idCountObj.decrementAndGet()) });
/*     */     }
/*     */   }
/*     */ 
/*     */   private native void twkRelease(Object[] paramArrayOfObject);
/*     */ 
/*     */   private int refString(String paramString)
/*     */   {
/* 130 */     return this.currentBuffer.addString(paramString);
/*     */   }
/*     */ 
/*     */   private int refIntArr(int[] paramArrayOfInt)
/*     */   {
/* 135 */     return this.currentBuffer.addIntArray(paramArrayOfInt);
/*     */   }
/*     */ 
/*     */   private int refFloatArr(float[] paramArrayOfFloat)
/*     */   {
/* 140 */     return this.currentBuffer.addFloatArray(paramArrayOfFloat);
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.platform.graphics.WCRenderQueue
 * JD-Core Version:    0.6.2
 */