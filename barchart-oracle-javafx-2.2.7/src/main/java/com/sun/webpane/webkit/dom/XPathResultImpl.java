/*     */ package com.sun.webpane.webkit.dom;
/*     */ 
/*     */ import com.sun.webpane.platform.Disposer;
/*     */ import com.sun.webpane.platform.DisposerRecord;
/*     */ import org.w3c.dom.DOMException;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.xpath.XPathResult;
/*     */ 
/*     */ public class XPathResultImpl
/*     */   implements XPathResult
/*     */ {
/*     */   protected final long contextPeer;
/*     */   protected final long rootPeer;
/*     */   protected final long peer;
/*     */   public static final int ANY_TYPE = 0;
/*     */   public static final int NUMBER_TYPE = 1;
/*     */   public static final int STRING_TYPE = 2;
/*     */   public static final int BOOLEAN_TYPE = 3;
/*     */   public static final int UNORDERED_NODE_ITERATOR_TYPE = 4;
/*     */   public static final int ORDERED_NODE_ITERATOR_TYPE = 5;
/*     */   public static final int UNORDERED_NODE_SNAPSHOT_TYPE = 6;
/*     */   public static final int ORDERED_NODE_SNAPSHOT_TYPE = 7;
/*     */   public static final int ANY_UNORDERED_NODE_TYPE = 8;
/*     */   public static final int FIRST_ORDERED_NODE_TYPE = 9;
/*     */ 
/*     */   XPathResultImpl(long peer, long contextPeer, long rootPeer)
/*     */   {
/*  23 */     this.peer = peer;
/*  24 */     this.contextPeer = contextPeer;
/*  25 */     this.rootPeer = rootPeer;
/*  26 */     Disposer.addRecord(this, new SelfDisposer(peer));
/*     */   }
/*     */ 
/*     */   static XPathResult create(long peer, long contextPeer, long rootPeer) {
/*  30 */     if (peer == 0L) return null;
/*  31 */     return new XPathResultImpl(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   long getPeer()
/*     */   {
/*  39 */     return this.peer;
/*     */   }
/*     */ 
/*     */   static long getPeer(XPathResult arg) {
/*  43 */     return arg == null ? 0L : ((XPathResultImpl)arg).getPeer();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object that) {
/*  47 */     return ((that instanceof XPathResultImpl)) && (this.peer == ((XPathResultImpl)that).peer);
/*     */   }
/*     */ 
/*     */   public int hashCode() {
/*  51 */     long p = this.peer;
/*  52 */     return (int)(p ^ p >> 17);
/*     */   }
/*     */ 
/*     */   private static native void dispose(long paramLong);
/*     */ 
/*     */   static XPathResult getImpl(long peer, long contextPeer, long rootPeer) {
/*  58 */     return create(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   public short getResultType()
/*     */   {
/*  76 */     return getResultTypeImpl(getPeer());
/*     */   }
/*     */   static native short getResultTypeImpl(long paramLong);
/*     */ 
/*     */   public double getNumberValue() throws DOMException {
/*  81 */     return getNumberValueImpl(getPeer());
/*     */   }
/*     */   static native double getNumberValueImpl(long paramLong);
/*     */ 
/*     */   public String getStringValue() throws DOMException {
/*  86 */     return getStringValueImpl(getPeer());
/*     */   }
/*     */   static native String getStringValueImpl(long paramLong);
/*     */ 
/*     */   public boolean getBooleanValue() throws DOMException {
/*  91 */     return getBooleanValueImpl(getPeer());
/*     */   }
/*     */   static native boolean getBooleanValueImpl(long paramLong);
/*     */ 
/*     */   public Node getSingleNodeValue() throws DOMException {
/*  96 */     return NodeImpl.getImpl(getSingleNodeValueImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */   static native long getSingleNodeValueImpl(long paramLong);
/*     */ 
/*     */   public boolean getInvalidIteratorState() {
/* 101 */     return getInvalidIteratorStateImpl(getPeer());
/*     */   }
/*     */   static native boolean getInvalidIteratorStateImpl(long paramLong);
/*     */ 
/*     */   public int getSnapshotLength() throws DOMException {
/* 106 */     return getSnapshotLengthImpl(getPeer());
/*     */   }
/*     */ 
/*     */   static native int getSnapshotLengthImpl(long paramLong);
/*     */ 
/*     */   public Node iterateNext()
/*     */     throws DOMException
/*     */   {
/* 114 */     return NodeImpl.getImpl(iterateNextImpl(getPeer()), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long iterateNextImpl(long paramLong);
/*     */ 
/*     */   public Node snapshotItem(int index) throws DOMException
/*     */   {
/* 121 */     return NodeImpl.getImpl(snapshotItemImpl(getPeer(), index), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long snapshotItemImpl(long paramLong, int paramInt);
/*     */ 
/*     */   static class SelfDisposer
/*     */     implements DisposerRecord
/*     */   {
/*     */     private final long peer;
/*     */ 
/*     */     SelfDisposer(long peer)
/*     */     {
/*  15 */       this.peer = peer;
/*     */     }
/*     */     public void dispose() {
/*  18 */       XPathResultImpl.dispose(this.peer);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.XPathResultImpl
 * JD-Core Version:    0.6.2
 */