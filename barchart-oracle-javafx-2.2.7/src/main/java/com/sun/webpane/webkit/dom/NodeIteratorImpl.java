/*     */ package com.sun.webpane.webkit.dom;
/*     */ 
/*     */ import com.sun.webpane.platform.Disposer;
/*     */ import com.sun.webpane.platform.DisposerRecord;
/*     */ import org.w3c.dom.DOMException;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.traversal.NodeFilter;
/*     */ import org.w3c.dom.traversal.NodeIterator;
/*     */ 
/*     */ public class NodeIteratorImpl
/*     */   implements NodeIterator
/*     */ {
/*     */   protected final long contextPeer;
/*     */   protected final long rootPeer;
/*     */   protected final long peer;
/*     */ 
/*     */   NodeIteratorImpl(long peer, long contextPeer, long rootPeer)
/*     */   {
/*  24 */     this.peer = peer;
/*  25 */     this.contextPeer = contextPeer;
/*  26 */     this.rootPeer = rootPeer;
/*  27 */     Disposer.addRecord(this, new SelfDisposer(peer));
/*     */   }
/*     */ 
/*     */   static NodeIterator create(long peer, long contextPeer, long rootPeer) {
/*  31 */     if (peer == 0L) return null;
/*  32 */     return new NodeIteratorImpl(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   long getPeer()
/*     */   {
/*  40 */     return this.peer;
/*     */   }
/*     */ 
/*     */   static long getPeer(NodeIterator arg) {
/*  44 */     return arg == null ? 0L : ((NodeIteratorImpl)arg).getPeer();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object that) {
/*  48 */     return ((that instanceof NodeIteratorImpl)) && (this.peer == ((NodeIteratorImpl)that).peer);
/*     */   }
/*     */ 
/*     */   public int hashCode() {
/*  52 */     long p = this.peer;
/*  53 */     return (int)(p ^ p >> 17);
/*     */   }
/*     */ 
/*     */   private static native void dispose(long paramLong);
/*     */ 
/*     */   static NodeIterator getImpl(long peer, long contextPeer, long rootPeer) {
/*  59 */     return create(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   public Node getRoot()
/*     */   {
/*  65 */     return NodeImpl.getImpl(getRootImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getRootImpl(long paramLong);
/*     */ 
/*     */   public int getWhatToShow() {
/*  70 */     return getWhatToShowImpl(getPeer());
/*     */   }
/*     */   static native int getWhatToShowImpl(long paramLong);
/*     */ 
/*     */   public NodeFilter getFilter() {
/*  75 */     return NodeFilterImpl.getImpl(getFilterImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getFilterImpl(long paramLong);
/*     */ 
/*     */   public boolean getExpandEntityReferences() {
/*  80 */     return getExpandEntityReferencesImpl(getPeer());
/*     */   }
/*     */   static native boolean getExpandEntityReferencesImpl(long paramLong);
/*     */ 
/*     */   public Node getReferenceNode() {
/*  85 */     return NodeImpl.getImpl(getReferenceNodeImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getReferenceNodeImpl(long paramLong);
/*     */ 
/*     */   public boolean getPointerBeforeReferenceNode() {
/*  90 */     return getPointerBeforeReferenceNodeImpl(getPeer());
/*     */   }
/*     */ 
/*     */   static native boolean getPointerBeforeReferenceNodeImpl(long paramLong);
/*     */ 
/*     */   public Node nextNode()
/*     */     throws DOMException
/*     */   {
/*  98 */     return NodeImpl.getImpl(nextNodeImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long nextNodeImpl(long paramLong);
/*     */ 
/*     */   public Node previousNode() throws DOMException
/*     */   {
/* 105 */     return NodeImpl.getImpl(previousNodeImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long previousNodeImpl(long paramLong);
/*     */ 
/*     */   public void detach()
/*     */   {
/* 112 */     detachImpl(getPeer());
/*     */   }
/*     */ 
/*     */   static native void detachImpl(long paramLong);
/*     */ 
/*     */   static class SelfDisposer
/*     */     implements DisposerRecord
/*     */   {
/*     */     private final long peer;
/*     */ 
/*     */     SelfDisposer(long peer)
/*     */     {
/*  16 */       this.peer = peer;
/*     */     }
/*     */     public void dispose() {
/*  19 */       NodeIteratorImpl.dispose(this.peer);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.NodeIteratorImpl
 * JD-Core Version:    0.6.2
 */