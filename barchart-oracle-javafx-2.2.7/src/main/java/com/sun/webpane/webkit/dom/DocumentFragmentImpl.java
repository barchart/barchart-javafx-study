/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import org.w3c.dom.DOMException;
/*    */ import org.w3c.dom.DocumentFragment;
/*    */ import org.w3c.dom.Element;
/*    */ import org.w3c.dom.NodeList;
/*    */ 
/*    */ public class DocumentFragmentImpl extends NodeImpl
/*    */   implements DocumentFragment
/*    */ {
/*    */   DocumentFragmentImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/* 12 */     super(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   static DocumentFragment getImpl(long peer, long contextPeer, long rootPeer) {
/* 16 */     return (DocumentFragment)create(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   public Element querySelector(String selectors)
/*    */     throws DOMException
/*    */   {
/* 23 */     return ElementImpl.getImpl(querySelectorImpl(getPeer(), selectors), this.contextPeer, this.rootPeer);
/*    */   }
/*    */ 
/*    */   static native long querySelectorImpl(long paramLong, String paramString);
/*    */ 
/*    */   public NodeList querySelectorAll(String selectors)
/*    */     throws DOMException
/*    */   {
/* 32 */     return NodeListImpl.getImpl(querySelectorAllImpl(getPeer(), selectors), this.contextPeer, this.rootPeer);
/*    */   }
/*    */ 
/*    */   static native long querySelectorAllImpl(long paramLong, String paramString);
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.DocumentFragmentImpl
 * JD-Core Version:    0.6.2
 */