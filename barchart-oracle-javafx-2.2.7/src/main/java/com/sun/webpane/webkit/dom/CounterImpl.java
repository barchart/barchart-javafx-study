/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import com.sun.webpane.platform.Disposer;
/*    */ import com.sun.webpane.platform.DisposerRecord;
/*    */ import org.w3c.dom.css.Counter;
/*    */ 
/*    */ public class CounterImpl
/*    */   implements Counter
/*    */ {
/*    */   protected final long contextPeer;
/*    */   protected final long rootPeer;
/*    */   protected final long peer;
/*    */ 
/*    */   CounterImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/* 21 */     this.peer = peer;
/* 22 */     this.contextPeer = contextPeer;
/* 23 */     this.rootPeer = rootPeer;
/* 24 */     Disposer.addRecord(this, new SelfDisposer(peer));
/*    */   }
/*    */ 
/*    */   static Counter create(long peer, long contextPeer, long rootPeer) {
/* 28 */     if (peer == 0L) return null;
/* 29 */     return new CounterImpl(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   long getPeer()
/*    */   {
/* 37 */     return this.peer;
/*    */   }
/*    */ 
/*    */   static long getPeer(Counter arg) {
/* 41 */     return arg == null ? 0L : ((CounterImpl)arg).getPeer();
/*    */   }
/*    */ 
/*    */   public boolean equals(Object that) {
/* 45 */     return ((that instanceof CounterImpl)) && (this.peer == ((CounterImpl)that).peer);
/*    */   }
/*    */ 
/*    */   public int hashCode() {
/* 49 */     long p = this.peer;
/* 50 */     return (int)(p ^ p >> 17);
/*    */   }
/*    */ 
/*    */   private static native void dispose(long paramLong);
/*    */ 
/*    */   static Counter getImpl(long peer, long contextPeer, long rootPeer) {
/* 56 */     return create(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   public String getIdentifier()
/*    */   {
/* 62 */     return getIdentifierImpl(getPeer());
/*    */   }
/*    */   static native String getIdentifierImpl(long paramLong);
/*    */ 
/*    */   public String getListStyle() {
/* 67 */     return getListStyleImpl(getPeer());
/*    */   }
/*    */   static native String getListStyleImpl(long paramLong);
/*    */ 
/*    */   public String getSeparator() {
/* 72 */     return getSeparatorImpl(getPeer());
/*    */   }
/*    */ 
/*    */   static native String getSeparatorImpl(long paramLong);
/*    */ 
/*    */   static class SelfDisposer
/*    */     implements DisposerRecord
/*    */   {
/*    */     private final long peer;
/*    */ 
/*    */     SelfDisposer(long peer)
/*    */     {
/* 13 */       this.peer = peer;
/*    */     }
/*    */     public void dispose() {
/* 16 */       CounterImpl.dispose(this.peer);
/*    */     }
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.CounterImpl
 * JD-Core Version:    0.6.2
 */