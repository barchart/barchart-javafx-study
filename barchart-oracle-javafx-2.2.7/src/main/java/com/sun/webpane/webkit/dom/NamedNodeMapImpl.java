/*     */ package com.sun.webpane.webkit.dom;
/*     */ 
/*     */ import com.sun.webpane.platform.Disposer;
/*     */ import com.sun.webpane.platform.DisposerRecord;
/*     */ import org.w3c.dom.DOMException;
/*     */ import org.w3c.dom.NamedNodeMap;
/*     */ import org.w3c.dom.Node;
/*     */ 
/*     */ public class NamedNodeMapImpl
/*     */   implements NamedNodeMap
/*     */ {
/*     */   protected final long contextPeer;
/*     */   protected final long rootPeer;
/*     */   protected final long peer;
/*     */ 
/*     */   NamedNodeMapImpl(long peer, long contextPeer, long rootPeer)
/*     */   {
/*  23 */     this.peer = peer;
/*  24 */     this.contextPeer = contextPeer;
/*  25 */     this.rootPeer = rootPeer;
/*  26 */     Disposer.addRecord(this, new SelfDisposer(peer));
/*     */   }
/*     */ 
/*     */   static NamedNodeMap create(long peer, long contextPeer, long rootPeer) {
/*  30 */     if (peer == 0L) return null;
/*  31 */     return new NamedNodeMapImpl(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   long getPeer()
/*     */   {
/*  39 */     return this.peer;
/*     */   }
/*     */ 
/*     */   static long getPeer(NamedNodeMap arg) {
/*  43 */     return arg == null ? 0L : ((NamedNodeMapImpl)arg).getPeer();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object that) {
/*  47 */     return ((that instanceof NamedNodeMapImpl)) && (this.peer == ((NamedNodeMapImpl)that).peer);
/*     */   }
/*     */ 
/*     */   public int hashCode() {
/*  51 */     long p = this.peer;
/*  52 */     return (int)(p ^ p >> 17);
/*     */   }
/*     */ 
/*     */   private static native void dispose(long paramLong);
/*     */ 
/*     */   static NamedNodeMap getImpl(long peer, long contextPeer, long rootPeer) {
/*  58 */     return create(peer, contextPeer, rootPeer);
/*     */   }
/*     */ 
/*     */   public int getLength()
/*     */   {
/*  64 */     return getLengthImpl(getPeer());
/*     */   }
/*     */ 
/*     */   static native int getLengthImpl(long paramLong);
/*     */ 
/*     */   public Node getNamedItem(String name)
/*     */   {
/*  72 */     return NodeImpl.getImpl(getNamedItemImpl(getPeer(), name), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long getNamedItemImpl(long paramLong, String paramString);
/*     */ 
/*     */   public Node setNamedItem(Node node)
/*     */     throws DOMException
/*     */   {
/*  81 */     return NodeImpl.getImpl(setNamedItemImpl(getPeer(), NodeImpl.getPeer(node)), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long setNamedItemImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public Node removeNamedItem(String name)
/*     */     throws DOMException
/*     */   {
/*  90 */     return NodeImpl.getImpl(removeNamedItemImpl(getPeer(), name), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long removeNamedItemImpl(long paramLong, String paramString);
/*     */ 
/*     */   public Node item(int index)
/*     */   {
/*  99 */     return NodeImpl.getImpl(itemImpl(getPeer(), index), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long itemImpl(long paramLong, int paramInt);
/*     */ 
/*     */   public Node getNamedItemNS(String namespaceURI, String localName)
/*     */   {
/* 109 */     return NodeImpl.getImpl(getNamedItemNSImpl(getPeer(), namespaceURI, localName), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long getNamedItemNSImpl(long paramLong, String paramString1, String paramString2);
/*     */ 
/*     */   public Node setNamedItemNS(Node node)
/*     */     throws DOMException
/*     */   {
/* 120 */     return NodeImpl.getImpl(setNamedItemNSImpl(getPeer(), NodeImpl.getPeer(node)), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long setNamedItemNSImpl(long paramLong1, long paramLong2);
/*     */ 
/*     */   public Node removeNamedItemNS(String namespaceURI, String localName)
/*     */     throws DOMException
/*     */   {
/* 130 */     return NodeImpl.getImpl(removeNamedItemNSImpl(getPeer(), namespaceURI, localName), this.contextPeer, this.rootPeer);
/*     */   }
/*     */ 
/*     */   static native long removeNamedItemNSImpl(long paramLong, String paramString1, String paramString2);
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
/*  18 */       NamedNodeMapImpl.dispose(this.peer);
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.NamedNodeMapImpl
 * JD-Core Version:    0.6.2
 */