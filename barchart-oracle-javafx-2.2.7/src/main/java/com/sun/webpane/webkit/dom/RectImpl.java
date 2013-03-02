/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import com.sun.webpane.platform.Disposer;
/*    */ import com.sun.webpane.platform.DisposerRecord;
/*    */ import org.w3c.dom.css.CSSPrimitiveValue;
/*    */ import org.w3c.dom.css.Rect;
/*    */ 
/*    */ public class RectImpl
/*    */   implements Rect
/*    */ {
/*    */   protected final long contextPeer;
/*    */   protected final long rootPeer;
/*    */   protected final long peer;
/*    */ 
/*    */   RectImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/* 22 */     this.peer = peer;
/* 23 */     this.contextPeer = contextPeer;
/* 24 */     this.rootPeer = rootPeer;
/* 25 */     Disposer.addRecord(this, new SelfDisposer(peer));
/*    */   }
/*    */ 
/*    */   static Rect create(long peer, long contextPeer, long rootPeer) {
/* 29 */     if (peer == 0L) return null;
/* 30 */     return new RectImpl(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   long getPeer()
/*    */   {
/* 38 */     return this.peer;
/*    */   }
/*    */ 
/*    */   static long getPeer(Rect arg) {
/* 42 */     return arg == null ? 0L : ((RectImpl)arg).getPeer();
/*    */   }
/*    */ 
/*    */   public boolean equals(Object that) {
/* 46 */     return ((that instanceof RectImpl)) && (this.peer == ((RectImpl)that).peer);
/*    */   }
/*    */ 
/*    */   public int hashCode() {
/* 50 */     long p = this.peer;
/* 51 */     return (int)(p ^ p >> 17);
/*    */   }
/*    */ 
/*    */   private static native void dispose(long paramLong);
/*    */ 
/*    */   static Rect getImpl(long peer, long contextPeer, long rootPeer) {
/* 57 */     return create(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   public CSSPrimitiveValue getTop()
/*    */   {
/* 63 */     return CSSPrimitiveValueImpl.getImpl(getTopImpl(getPeer()), this.contextPeer, this.rootPeer);
/*    */   }
/*    */   static native long getTopImpl(long paramLong);
/*    */ 
/*    */   public CSSPrimitiveValue getRight() {
/* 68 */     return CSSPrimitiveValueImpl.getImpl(getRightImpl(getPeer()), this.contextPeer, this.rootPeer);
/*    */   }
/*    */   static native long getRightImpl(long paramLong);
/*    */ 
/*    */   public CSSPrimitiveValue getBottom() {
/* 73 */     return CSSPrimitiveValueImpl.getImpl(getBottomImpl(getPeer()), this.contextPeer, this.rootPeer);
/*    */   }
/*    */   static native long getBottomImpl(long paramLong);
/*    */ 
/*    */   public CSSPrimitiveValue getLeft() {
/* 78 */     return CSSPrimitiveValueImpl.getImpl(getLeftImpl(getPeer()), this.contextPeer, this.rootPeer);
/*    */   }
/*    */ 
/*    */   static native long getLeftImpl(long paramLong);
/*    */ 
/*    */   static class SelfDisposer
/*    */     implements DisposerRecord
/*    */   {
/*    */     private final long peer;
/*    */ 
/*    */     SelfDisposer(long peer)
/*    */     {
/* 14 */       this.peer = peer;
/*    */     }
/*    */     public void dispose() {
/* 17 */       RectImpl.dispose(this.peer);
/*    */     }
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.RectImpl
 * JD-Core Version:    0.6.2
 */