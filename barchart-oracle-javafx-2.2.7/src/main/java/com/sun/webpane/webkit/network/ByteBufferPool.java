/*    */ package com.sun.webpane.webkit.network;
/*    */ 
/*    */ import java.nio.ByteBuffer;
/*    */ import java.util.Queue;
/*    */ import java.util.concurrent.ConcurrentLinkedQueue;
/*    */ import java.util.concurrent.Semaphore;
/*    */ 
/*    */ final class ByteBufferPool
/*    */ {
/* 20 */   private final Queue<ByteBuffer> byteBuffers = new ConcurrentLinkedQueue();
/*    */   private final int bufferSize;
/*    */ 
/*    */   private ByteBufferPool(int paramInt)
/*    */   {
/* 33 */     this.bufferSize = paramInt;
/*    */   }
/*    */ 
/*    */   static ByteBufferPool newInstance(int paramInt)
/*    */   {
/* 41 */     return new ByteBufferPool(paramInt);
/*    */   }
/*    */ 
/*    */   ByteBufferAllocator newAllocator(int paramInt)
/*    */   {
/* 52 */     return new ByteBufferAllocatorImpl(paramInt, null);
/*    */   }
/*    */ 
/*    */   private class ByteBufferAllocatorImpl
/*    */     implements ByteBufferAllocator
/*    */   {
/*    */     private final Semaphore semaphore;
/*    */ 
/*    */     private ByteBufferAllocatorImpl(int arg2)
/*    */     {
/*    */       int i;
/* 71 */       this.semaphore = new Semaphore(i);
/*    */     }
/*    */ 
/*    */     public ByteBuffer allocate()
/*    */       throws InterruptedException
/*    */     {
/* 80 */       this.semaphore.acquire();
/* 81 */       ByteBuffer localByteBuffer = (ByteBuffer)ByteBufferPool.this.byteBuffers.poll();
/* 82 */       if (localByteBuffer == null) {
/* 83 */         localByteBuffer = ByteBuffer.allocateDirect(ByteBufferPool.this.bufferSize);
/*    */       }
/* 85 */       return localByteBuffer;
/*    */     }
/*    */ 
/*    */     public void release(ByteBuffer paramByteBuffer)
/*    */     {
/* 93 */       ByteBufferPool.this.byteBuffers.add(paramByteBuffer);
/* 94 */       this.semaphore.release();
/*    */     }
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.network.ByteBufferPool
 * JD-Core Version:    0.6.2
 */