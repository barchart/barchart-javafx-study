/*    */ package com.sun.webpane.platform.graphics;
/*    */ 
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ public abstract class WCImage extends Ref
/*    */ {
/*    */   private WCRenderQueue rq;
/*    */ 
/*    */   public abstract int getWidth();
/*    */ 
/*    */   public abstract int getHeight();
/*    */ 
/*    */   public abstract String toDataURL(String paramString);
/*    */ 
/*    */   public ByteBuffer getPixelBuffer()
/*    */   {
/* 17 */     return null;
/*    */   }
/*    */   public void drawPixelBuffer() {
/*    */   }
/*    */   public void setRQ(WCRenderQueue paramWCRenderQueue) {
/* 22 */     this.rq = paramWCRenderQueue;
/*    */   }
/*    */ 
/*    */   public synchronized void flushRQ() {
/* 26 */     if (this.rq != null)
/* 27 */       this.rq.flush();
/*    */   }
/*    */ 
/*    */   public synchronized boolean isDirty()
/*    */   {
/* 32 */     return this.rq != null;
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.platform.graphics.WCImage
 * JD-Core Version:    0.6.2
 */