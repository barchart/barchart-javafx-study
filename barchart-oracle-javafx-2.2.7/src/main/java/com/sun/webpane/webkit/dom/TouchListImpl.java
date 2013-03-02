/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import com.sun.webpane.platform.Disposer;
/*    */ import com.sun.webpane.platform.DisposerRecord;
/*    */ 
/*    */ public class TouchListImpl
/*    */ {
/*    */   protected final long contextPeer;
/*    */   protected final long rootPeer;
/*    */   protected final long peer;
/*    */ 
/*    */   TouchListImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/* 20 */     this.peer = peer;
/* 21 */     this.contextPeer = contextPeer;
/* 22 */     this.rootPeer = rootPeer;
/* 23 */     Disposer.addRecord(this, new SelfDisposer(peer));
/*    */   }
/*    */ 
/*    */   static TouchListImpl create(long peer, long contextPeer, long rootPeer) {
/* 27 */     if (peer == 0L) return null;
/* 28 */     return new TouchListImpl(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   long getPeer()
/*    */   {
/* 36 */     return this.peer;
/*    */   }
/*    */ 
/*    */   static long getPeer(TouchListImpl arg) {
/* 40 */     return arg == null ? 0L : arg.getPeer();
/*    */   }
/*    */ 
/*    */   public boolean equals(Object that) {
/* 44 */     return ((that instanceof TouchListImpl)) && (this.peer == ((TouchListImpl)that).peer);
/*    */   }
/*    */ 
/*    */   public int hashCode() {
/* 48 */     long p = this.peer;
/* 49 */     return (int)(p ^ p >> 17);
/*    */   }
/*    */ 
/*    */   private static native void dispose(long paramLong);
/*    */ 
/*    */   static TouchListImpl getImpl(long peer, long contextPeer, long rootPeer) {
/* 55 */     return create(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   public int getLength()
/*    */   {
/* 61 */     return getLengthImpl(getPeer());
/*    */   }
/*    */ 
/*    */   static native int getLengthImpl(long paramLong);
/*    */ 
/*    */   public TouchImpl item(int index)
/*    */   {
/* 69 */     return TouchImpl.getImpl(itemImpl(getPeer(), index), this.contextPeer, this.rootPeer);
/*    */   }
/*    */ 
/*    */   static native long itemImpl(long paramLong, int paramInt);
/*    */ 
/*    */   static class SelfDisposer
/*    */     implements DisposerRecord
/*    */   {
/*    */     private final long peer;
/*    */ 
/*    */     SelfDisposer(long peer)
/*    */     {
/* 12 */       this.peer = peer;
/*    */     }
/*    */     public void dispose() {
/* 15 */       TouchListImpl.dispose(this.peer);
/*    */     }
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.TouchListImpl
 * JD-Core Version:    0.6.2
 */