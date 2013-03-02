/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import com.sun.webpane.platform.Disposer;
/*    */ import com.sun.webpane.platform.DisposerRecord;
/*    */ import org.w3c.dom.DOMException;
/*    */ import org.w3c.dom.Node;
/*    */ import org.w3c.dom.xpath.XPathExpression;
/*    */ import org.w3c.dom.xpath.XPathResult;
/*    */ 
/*    */ public class XPathExpressionImpl
/*    */   implements XPathExpression
/*    */ {
/*    */   protected final long contextPeer;
/*    */   protected final long rootPeer;
/*    */   protected final long peer;
/*    */ 
/*    */   XPathExpressionImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/* 24 */     this.peer = peer;
/* 25 */     this.contextPeer = contextPeer;
/* 26 */     this.rootPeer = rootPeer;
/* 27 */     Disposer.addRecord(this, new SelfDisposer(peer));
/*    */   }
/*    */ 
/*    */   static XPathExpression create(long peer, long contextPeer, long rootPeer) {
/* 31 */     if (peer == 0L) return null;
/* 32 */     return new XPathExpressionImpl(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   long getPeer()
/*    */   {
/* 40 */     return this.peer;
/*    */   }
/*    */ 
/*    */   static long getPeer(XPathExpression arg) {
/* 44 */     return arg == null ? 0L : ((XPathExpressionImpl)arg).getPeer();
/*    */   }
/*    */ 
/*    */   public boolean equals(Object that) {
/* 48 */     return ((that instanceof XPathExpressionImpl)) && (this.peer == ((XPathExpressionImpl)that).peer);
/*    */   }
/*    */ 
/*    */   public int hashCode() {
/* 52 */     long p = this.peer;
/* 53 */     return (int)(p ^ p >> 17);
/*    */   }
/*    */ 
/*    */   private static native void dispose(long paramLong);
/*    */ 
/*    */   static XPathExpression getImpl(long peer, long contextPeer, long rootPeer) {
/* 59 */     return create(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   public Object evaluate(Node contextNode, short type, Object result) throws DOMException {
/* 63 */     return evaluate(contextNode, type, (XPathResult)result);
/*    */   }
/*    */ 
/*    */   public XPathResult evaluate(Node contextNode, short type, XPathResult inResult)
/*    */     throws DOMException
/*    */   {
/* 71 */     return XPathResultImpl.getImpl(evaluateImpl(getPeer(), NodeImpl.getPeer(contextNode), type, XPathResultImpl.getPeer(inResult)), this.contextPeer, this.rootPeer);
/*    */   }
/*    */ 
/*    */   static native long evaluateImpl(long paramLong1, long paramLong2, short paramShort, long paramLong3);
/*    */ 
/*    */   static class SelfDisposer
/*    */     implements DisposerRecord
/*    */   {
/*    */     private final long peer;
/*    */ 
/*    */     SelfDisposer(long peer)
/*    */     {
/* 16 */       this.peer = peer;
/*    */     }
/*    */     public void dispose() {
/* 19 */       XPathExpressionImpl.dispose(this.peer);
/*    */     }
/*    */   }
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.XPathExpressionImpl
 * JD-Core Version:    0.6.2
 */