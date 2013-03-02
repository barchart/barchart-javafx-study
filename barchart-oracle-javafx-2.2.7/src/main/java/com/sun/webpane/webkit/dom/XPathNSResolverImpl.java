/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import com.sun.webpane.platform.Disposer;
/*    */ import com.sun.webpane.platform.DisposerRecord;
/*    */ import org.w3c.dom.xpath.XPathNSResolver;
/*    */ 
/*    */ public class XPathNSResolverImpl
/*    */   implements XPathNSResolver
/*    */ {
/*    */   protected final long contextPeer;
/*    */   protected final long rootPeer;
/*    */   protected final long peer;
/*    */ 
/*    */   XPathNSResolverImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/* 21 */     this.peer = peer;
/* 22 */     this.contextPeer = contextPeer;
/* 23 */     this.rootPeer = rootPeer;
/* 24 */     Disposer.addRecord(this, new SelfDisposer(peer));
/*    */   }
/*    */ 
/*    */   static XPathNSResolver create(long peer, long contextPeer, long rootPeer) {
/* 28 */     if (peer == 0L) return null;
/* 29 */     return new XPathNSResolverImpl(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   long getPeer()
/*    */   {
/* 37 */     return this.peer;
/*    */   }
/*    */ 
/*    */   static long getPeer(XPathNSResolver arg) {
/* 41 */     return arg == null ? 0L : ((XPathNSResolverImpl)arg).getPeer();
/*    */   }
/*    */ 
/*    */   public boolean equals(Object that) {
/* 45 */     return ((that instanceof XPathNSResolverImpl)) && (this.peer == ((XPathNSResolverImpl)that).peer);
/*    */   }
/*    */ 
/*    */   public int hashCode() {
/* 49 */     long p = this.peer;
/* 50 */     return (int)(p ^ p >> 17);
/*    */   }
/*    */ 
/*    */   private static native void dispose(long paramLong);
/*    */ 
/*    */   static XPathNSResolver getImpl(long peer, long contextPeer, long rootPeer) {
/* 56 */     return create(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   public String lookupNamespaceURI(String prefix)
/*    */   {
/* 63 */     return lookupNamespaceURIImpl(getPeer(), prefix);
/*    */   }
/*    */ 
/*    */   static native String lookupNamespaceURIImpl(long paramLong, String paramString);
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
/* 16 */       XPathNSResolverImpl.dispose(this.peer);
/*    */     }
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.XPathNSResolverImpl
 * JD-Core Version:    0.6.2
 */