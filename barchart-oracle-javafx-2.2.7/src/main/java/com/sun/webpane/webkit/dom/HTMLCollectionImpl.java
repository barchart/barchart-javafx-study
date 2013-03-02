/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import com.sun.webpane.platform.Disposer;
/*    */ import com.sun.webpane.platform.DisposerRecord;
/*    */ import org.w3c.dom.Node;
/*    */ import org.w3c.dom.html.HTMLCollection;
/*    */ 
/*    */ public class HTMLCollectionImpl
/*    */   implements HTMLCollection
/*    */ {
/*    */   protected final long contextPeer;
/*    */   protected final long rootPeer;
/*    */   protected final long peer;
/*    */   private static final int TYPE_HTMLOptionsCollection = 1;
/*    */ 
/*    */   HTMLCollectionImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/* 22 */     this.peer = peer;
/* 23 */     this.contextPeer = contextPeer;
/* 24 */     this.rootPeer = rootPeer;
/* 25 */     Disposer.addRecord(this, new SelfDisposer(peer));
/*    */   }
/*    */ 
/*    */   static HTMLCollection create(long peer, long contextPeer, long rootPeer) {
/* 29 */     if (peer == 0L) return null;
/* 30 */     switch (getCPPTypeImpl(peer)) { case 1:
/* 31 */       return new HTMLOptionsCollectionImpl(peer, contextPeer, rootPeer);
/*    */     }
/* 33 */     return new HTMLCollectionImpl(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   long getPeer()
/*    */   {
/* 41 */     return this.peer;
/*    */   }
/*    */ 
/*    */   static long getPeer(HTMLCollection arg) {
/* 45 */     return arg == null ? 0L : ((HTMLCollectionImpl)arg).getPeer();
/*    */   }
/*    */ 
/*    */   public boolean equals(Object that) {
/* 49 */     return ((that instanceof HTMLCollectionImpl)) && (this.peer == ((HTMLCollectionImpl)that).peer);
/*    */   }
/*    */ 
/*    */   public int hashCode() {
/* 53 */     long p = this.peer;
/* 54 */     return (int)(p ^ p >> 17);
/*    */   }
/*    */ 
/*    */   private static native void dispose(long paramLong);
/*    */ 
/*    */   private static native int getCPPTypeImpl(long paramLong);
/*    */ 
/*    */   static HTMLCollection getImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/* 63 */     return create(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   public int getLength()
/*    */   {
/* 69 */     return getLengthImpl(getPeer());
/*    */   }
/*    */ 
/*    */   static native int getLengthImpl(long paramLong);
/*    */ 
/*    */   public Node item(int index)
/*    */   {
/* 77 */     return NodeImpl.getImpl(itemImpl(getPeer(), index), this.contextPeer, this.rootPeer);
/*    */   }
/*    */ 
/*    */   static native long itemImpl(long paramLong, int paramInt);
/*    */ 
/*    */   public Node namedItem(String name)
/*    */   {
/* 86 */     return NodeImpl.getImpl(namedItemImpl(getPeer(), name), this.contextPeer, this.rootPeer);
/*    */   }
/*    */ 
/*    */   static native long namedItemImpl(long paramLong, String paramString);
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
/* 17 */       HTMLCollectionImpl.dispose(this.peer);
/*    */     }
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.HTMLCollectionImpl
 * JD-Core Version:    0.6.2
 */