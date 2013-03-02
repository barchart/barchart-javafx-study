/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import com.sun.webpane.platform.Disposer;
/*    */ import com.sun.webpane.platform.DisposerRecord;
/*    */ import org.w3c.dom.Node;
/*    */ import org.w3c.dom.traversal.NodeFilter;
/*    */ 
/*    */ public class NodeFilterImpl
/*    */   implements NodeFilter
/*    */ {
/*    */   protected final long contextPeer;
/*    */   protected final long rootPeer;
/*    */   protected final long peer;
/*    */   public static final int FILTER_ACCEPT = 1;
/*    */   public static final int FILTER_REJECT = 2;
/*    */   public static final int FILTER_SKIP = 3;
/*    */   public static final int SHOW_ALL = -1;
/*    */   public static final int SHOW_ELEMENT = 1;
/*    */   public static final int SHOW_ATTRIBUTE = 2;
/*    */   public static final int SHOW_TEXT = 4;
/*    */   public static final int SHOW_CDATA_SECTION = 8;
/*    */   public static final int SHOW_ENTITY_REFERENCE = 16;
/*    */   public static final int SHOW_ENTITY = 32;
/*    */   public static final int SHOW_PROCESSING_INSTRUCTION = 64;
/*    */   public static final int SHOW_COMMENT = 128;
/*    */   public static final int SHOW_DOCUMENT = 256;
/*    */   public static final int SHOW_DOCUMENT_TYPE = 512;
/*    */   public static final int SHOW_DOCUMENT_FRAGMENT = 1024;
/*    */   public static final int SHOW_NOTATION = 2048;
/*    */ 
/*    */   NodeFilterImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/* 22 */     this.peer = peer;
/* 23 */     this.contextPeer = contextPeer;
/* 24 */     this.rootPeer = rootPeer;
/* 25 */     Disposer.addRecord(this, new SelfDisposer(peer));
/*    */   }
/*    */ 
/*    */   static NodeFilter create(long peer, long contextPeer, long rootPeer) {
/* 29 */     if (peer == 0L) return null;
/* 30 */     return new NodeFilterImpl(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   long getPeer()
/*    */   {
/* 38 */     return this.peer;
/*    */   }
/*    */ 
/*    */   static long getPeer(NodeFilter arg) {
/* 42 */     return arg == null ? 0L : ((NodeFilterImpl)arg).getPeer();
/*    */   }
/*    */ 
/*    */   public boolean equals(Object that) {
/* 46 */     return ((that instanceof NodeFilterImpl)) && (this.peer == ((NodeFilterImpl)that).peer);
/*    */   }
/*    */ 
/*    */   public int hashCode() {
/* 50 */     long p = this.peer;
/* 51 */     return (int)(p ^ p >> 17);
/*    */   }
/*    */ 
/*    */   private static native void dispose(long paramLong);
/*    */ 
/*    */   static NodeFilter getImpl(long peer, long contextPeer, long rootPeer) {
/* 57 */     return create(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   public short acceptNode(Node n)
/*    */   {
/* 82 */     return acceptNodeImpl(getPeer(), NodeImpl.getPeer(n));
/*    */   }
/*    */ 
/*    */   static native short acceptNodeImpl(long paramLong1, long paramLong2);
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
/* 17 */       NodeFilterImpl.dispose(this.peer);
/*    */     }
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.NodeFilterImpl
 * JD-Core Version:    0.6.2
 */