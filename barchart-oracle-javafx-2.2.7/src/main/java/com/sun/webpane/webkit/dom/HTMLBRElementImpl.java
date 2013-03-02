/*    */ package com.sun.webpane.webkit.dom;
/*    */ 
/*    */ import org.w3c.dom.html.HTMLBRElement;
/*    */ 
/*    */ public class HTMLBRElementImpl extends HTMLElementImpl
/*    */   implements HTMLBRElement
/*    */ {
/*    */   HTMLBRElementImpl(long peer, long contextPeer, long rootPeer)
/*    */   {
/*  9 */     super(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   static HTMLBRElement getImpl(long peer, long contextPeer, long rootPeer) {
/* 13 */     return (HTMLBRElement)create(peer, contextPeer, rootPeer);
/*    */   }
/*    */ 
/*    */   public String getClear()
/*    */   {
/* 19 */     return getClearImpl(getPeer());
/*    */   }
/*    */   static native String getClearImpl(long paramLong);
/*    */ 
/*    */   public void setClear(String value) {
/* 24 */     setClearImpl(getPeer(), value);
/*    */   }
/*    */ 
/*    */   static native void setClearImpl(long paramLong, String paramString);
/*    */ }

/* Location:           /home/user1/Temp/jfxrt/jfxrt.jar
 * Qualified Name:     com.sun.webpane.webkit.dom.HTMLBRElementImpl
 * JD-Core Version:    0.6.2
 */