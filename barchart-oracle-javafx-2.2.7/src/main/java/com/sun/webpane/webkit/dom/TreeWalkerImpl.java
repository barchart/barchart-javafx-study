/*     */ package com.sun.webpane.webkit.dom;
/*     */ 
/*     */ import com.sun.webpane.platform.Disposer;
/*     */ import com.sun.webpane.platform.DisposerRecord;
/*     */ import org.w3c.dom.DOMException;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.traversal.NodeFilter;
/*     */ import org.w3c.dom.traversal.TreeWalker;
/*     */ 
/*     */ public class TreeWalkerImpl
/*     */   implements TreeWalker
/*     */ {
/*     */   protected final long contextPeer;
/*     */   protected final long rootPeer;
/*     */   protected final long peer;
/*     */ 
/*     */   TreeWalkerImpl(long peer, long contextPeer, long rootPeer)
/*     */   {
/*  24 */     this.peer = peer;
/*  25 */     this.contextPeer = contextPeer;
/*  26 */     this.rootPeer = rootPeer;
/*  27 */     Disposer.addRecord(this, new SelfDisposer(peer));
/*     */   }
/*     */ 
/*     */   static TreeWalker create(long peer, long contextPeer, long rootPeer) {
/*  31 */     if (peer == 0L) return null;
/*  32 */     return new TreeWalkerImpl(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   long getPeer()
/*     */   {
/*  40 */     return this.peer;
/*     */   }
/*     */ 
/*     */   static long getPeer(TreeWalker arg) {
/*  44 */     return arg == null ? 0L : ((TreeWalkerImpl)arg).getPeer();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object that) {
/*  48 */     return ((that instanceof TreeWalkerImpl)) && (this.peer == ((TreeWalkerImpl)that).peer);
/*     */   }
/*     */ 
/*     */   public int hashCode() {
/*  52 */     long p = this.peer;
/*  53 */     return (int)(p ^ p >> 17);
/*     */   }
/*     */ 
/*     */   private static native void dispose(long paramLong);
/*     */ 
/*     */   static TreeWalker getImpl(long peer, long contextPeer, long rootPeer) {
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
/*     */   public Node getCurrentNode() {
/*  85 */     return NodeImpl.getImpl(getCurrentNodeImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getCurrentNodeImpl(long paramLong);
/*     */ 
/*     */   public void setCurrentNode(Node value) throws DOMException {
/*  90 */     setCurrentNodeImpl(getPeer(), NodeImpl.getPeer(value));
/*     */   }
/*     */ 
/*     */   static native void setCurrentNodeImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public Node parentNode()
/*     */   {
/*  98 */     return NodeImpl.getImpl(parentNodeImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long parentNodeImpl(long paramLong);
/*     */ 
/*     */   public Node firstChild()
/*     */   {
/* 105 */     return NodeImpl.getImpl(firstChildImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long firstChildImpl(long paramLong);
/*     */ 
/*     */   public Node lastChild()
/*     */   {
/* 112 */     return NodeImpl.getImpl(lastChildImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long lastChildImpl(long paramLong);
/*     */ 
/*     */   public Node previousSibling()
/*     */   {
/* 119 */     return NodeImpl.getImpl(previousSiblingImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long previousSiblingImpl(long paramLong);
/*     */ 
/*     */   public Node nextSibling()
/*     */   {
/* 126 */     return NodeImpl.getImpl(nextSiblingImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long nextSiblingImpl(long paramLong);
/*     */ 
/*     */   public Node previousNode()
/*     */   {
/* 133 */     return NodeImpl.getImpl(previousNodeImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long previousNodeImpl(long paramLong);
/*     */ 
/*     */   public Node nextNode()
/*     */   {
/* 140 */     return NodeImpl.getImpl(nextNodeImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long nextNodeImpl(long paramLong);
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
/*  19 */       TreeWalkerImpl.dispose(this.peer);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.TreeWalkerImpl
 * JD-Core Version:    0.6.2
 */